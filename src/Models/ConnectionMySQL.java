package Models;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * Gestor de Conexión a Base de Datos con Conmutación por Error Automática (Dual Failover).
 * 
 * Estrategia de Conexión:
 * 1. Intenta conectarse a la Base de Datos Nube / Web (Aiven / Servidor Remoto).
 * 2. Si la nube está apagada, suspendida o sin internet, conmuta automáticamente
 *    a la Base de Datos Local (localhost:3306) en menos de 1.5 segundos.
 * 3. Mantiene una conexión física compartida reutilizable con envoltorio transparente
 *    para máxima velocidad (0 ms de sobrecarga en consultas posteriores).
 */
public class ConnectionMySQL {

    // Configuración Principal (Nube / Web)
    private String host = "mysql-18f6edfe-tiendagamer4.a.aivencloud.com";
    private String port = "20448";
    private String database_name = "defaultdb";
    private String user = "avnadmin";
    private String password = "";
    private String ssl = "true";

    // Configuración de Respaldo Automático (Local)
    private boolean fallbackEnabled = true;
    private String fallbackHost = "localhost";
    private String fallbackPort = "3306";
    private String fallbackDatabase = "tiendagamer_database";
    private String fallbackUser = "root";
    private String fallbackPassword = "Familia22@#";
    private String fallbackSsl = "false";

    // Estado público de la conexión activa
    private static volatile String activeConnectionSource = "Desconectado";
    private static Connection sharedPhysicalConnection = null;
    private static String lastConfigKey = "";

    public ConnectionMySQL() {
        loadConfiguration();
    }

    public static String getActiveConnectionSource() {
        return activeConnectionSource;
    }

    public static boolean isConnectedToCloud() {
        return activeConnectionSource != null && activeConnectionSource.startsWith("Nube");
    }

    private void loadConfiguration() {
        Properties prop = new Properties();
        File configFile = new File("db.properties");

        try {
            if (configFile.exists()) {
                try (InputStream is = new FileInputStream(configFile)) {
                    prop.load(is);
                }
            } else {
                try (InputStream is = getClass().getResourceAsStream("/db.properties")) {
                    if (is != null) {
                        prop.load(is);
                    }
                }
            }

            // 1. Cargar Configuración Principal
            if (prop.getProperty("db.host") != null && !prop.getProperty("db.host").trim().isEmpty()) {
                host = prop.getProperty("db.host").trim();
            }
            if (prop.getProperty("db.port") != null && !prop.getProperty("db.port").trim().isEmpty()) {
                port = prop.getProperty("db.port").trim();
            }
            if (prop.getProperty("db.name") != null && !prop.getProperty("db.name").trim().isEmpty()) {
                database_name = prop.getProperty("db.name").trim();
            }
            if (prop.getProperty("db.user") != null && !prop.getProperty("db.user").trim().isEmpty()) {
                user = prop.getProperty("db.user").trim();
            }
            if (prop.getProperty("db.password") != null) {
                password = prop.getProperty("db.password").trim();
            }
            if (prop.getProperty("db.ssl") != null && !prop.getProperty("db.ssl").trim().isEmpty()) {
                ssl = prop.getProperty("db.ssl").trim();
            } else if (!"localhost".equalsIgnoreCase(host) && !"127.0.0.1".equals(host)) {
                ssl = "true";
            }

            // 2. Cargar Configuración de Respaldo Local (Fallback)
            if (prop.getProperty("db.fallback.enabled") != null) {
                fallbackEnabled = Boolean.parseBoolean(prop.getProperty("db.fallback.enabled").trim());
            }
            if (prop.getProperty("db.fallback.host") != null && !prop.getProperty("db.fallback.host").trim().isEmpty()) {
                fallbackHost = prop.getProperty("db.fallback.host").trim();
            }
            if (prop.getProperty("db.fallback.port") != null && !prop.getProperty("db.fallback.port").trim().isEmpty()) {
                fallbackPort = prop.getProperty("db.fallback.port").trim();
            }
            if (prop.getProperty("db.fallback.name") != null && !prop.getProperty("db.fallback.name").trim().isEmpty()) {
                fallbackDatabase = prop.getProperty("db.fallback.name").trim();
            }
            if (prop.getProperty("db.fallback.user") != null && !prop.getProperty("db.fallback.user").trim().isEmpty()) {
                fallbackUser = prop.getProperty("db.fallback.user").trim();
            }
            if (prop.getProperty("db.fallback.password") != null) {
                fallbackPassword = prop.getProperty("db.fallback.password").trim();
            }
            if (prop.getProperty("db.fallback.ssl") != null && !prop.getProperty("db.fallback.ssl").trim().isEmpty()) {
                fallbackSsl = prop.getProperty("db.fallback.ssl").trim();
            }

        } catch (Exception e) {
            System.err.println("Aviso: No se pudo leer db.properties, usando valores por defecto: " + e.getMessage());
        }
    }

    public synchronized Connection getConnection() {
        try {
            // Si la conexión existente sigue activa y válida, reutilizarla inmediatamente (0 ms)
            if (sharedPhysicalConnection != null) {
                try {
                    if (!sharedPhysicalConnection.isClosed() && sharedPhysicalConnection.isValid(2)) {
                        return wrapConnection(sharedPhysicalConnection);
                    }
                } catch (SQLException ignore) {
                }
                try { sharedPhysicalConnection.close(); } catch (Exception ignore) {}
                sharedPhysicalConnection = null;
            }

            Class.forName("com.mysql.cj.jdbc.Driver");

            // ==========================================================
            // INTENTO 1: Conexión Principal (Nube / Web)
            // ==========================================================
            boolean isCloudCandidate = !"localhost".equalsIgnoreCase(host) && !"127.0.0.1".equals(host);
            boolean cloudHostReachable = true;

            if (isCloudCandidate) {
                // Verificación ultrarrápida de puerto y DNS con límite de 1.5s
                cloudHostReachable = isHostReachableFast(host, parsePort(port, 3306), 1500);
            }

            if (cloudHostReachable) {
                String sslParams = "true".equalsIgnoreCase(ssl)
                        ? "?useSSL=true&trustServerCertificate=true&serverTimezone=UTC&allowPublicKeyRetrieval=true&connectTimeout=2500"
                        : "?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true&connectTimeout=2500";
                String urlPrimary = "jdbc:mysql://" + host + ":" + port + "/" + database_name + sslParams;

                try {
                    Connection conn = DriverManager.getConnection(urlPrimary, user, password);
                    if (conn != null && !conn.isClosed()) {
                        sharedPhysicalConnection = conn;
                        activeConnectionSource = "Nube (" + host + ")";
                        lastConfigKey = urlPrimary + "|" + user;
                        System.out.println("[DB Conectada] Modo NUBE activo: " + activeConnectionSource);
                        return wrapConnection(sharedPhysicalConnection);
                    }
                } catch (SQLException exPrimary) {
                    System.err.println("[Aviso Nube] Error conectando a Nube (" + host + "): " + exPrimary.getMessage());
                }
            } else {
                System.err.println("[Aviso Nube] Servidor en la nube inaccesible (" + host + "). Activando respaldo local...");
            }

            // ==========================================================
            // INTENTO 2: Conexión de Respaldo (Localhost / Failover)
            // ==========================================================
            if (fallbackEnabled) {
                System.out.println("[DB Failover] Conmutando automáticamente a Base de Datos Local (" + fallbackHost + ":" + fallbackPort + ")...");
                String sslParamsLocal = "true".equalsIgnoreCase(fallbackSsl)
                        ? "?useSSL=true&trustServerCertificate=true&serverTimezone=UTC&allowPublicKeyRetrieval=true"
                        : "?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
                String urlFallback = "jdbc:mysql://" + fallbackHost + ":" + fallbackPort + "/" + fallbackDatabase + sslParamsLocal;

                // Probar con contraseña configurada
                try {
                    Connection connLocal = DriverManager.getConnection(urlFallback, fallbackUser, fallbackPassword);
                    if (connLocal != null && !connLocal.isClosed()) {
                        sharedPhysicalConnection = connLocal;
                        activeConnectionSource = "Local (" + fallbackHost + ":" + fallbackPort + "/" + fallbackDatabase + ")";
                        lastConfigKey = urlFallback + "|" + fallbackUser;
                        System.out.println("[DB Conectada] Modo LOCAL activo: " + activeConnectionSource);
                        return wrapConnection(sharedPhysicalConnection);
                    }
                } catch (SQLException exLocal) {
                    // Si falla por contraseña, intentar con contraseña vacía (común en XAMPP)
                    if (!fallbackPassword.isEmpty()) {
                        try {
                            Connection connEmpty = DriverManager.getConnection(urlFallback, fallbackUser, "");
                            if (connEmpty != null && !connEmpty.isClosed()) {
                                sharedPhysicalConnection = connEmpty;
                                activeConnectionSource = "Local (" + fallbackHost + ":" + fallbackPort + "/" + fallbackDatabase + " sin clave)";
                                lastConfigKey = urlFallback + "|" + fallbackUser;
                                System.out.println("[DB Conectada] Modo LOCAL activo (XAMPP root): " + activeConnectionSource);
                                return wrapConnection(sharedPhysicalConnection);
                            }
                        } catch (SQLException ignored) {
                        }
                    }
                    System.err.println("[Error Local] Tampoco se pudo conectar a Base de Datos Local: " + exLocal.getMessage());
                }
            }

        } catch (ClassNotFoundException e) {
            System.err.println("Error de Driver JDBC: " + e.getMessage());
        }

        activeConnectionSource = "Error de Conexión";
        return null;
    }

    /**
     * Prueba rápida no bloqueante para verificar si el host y puerto responden.
     */
    private boolean isHostReachableFast(String hostName, int portNum, int timeoutMs) {
        ExecutorService executor = Executors.newSingleThreadExecutor(r -> {
            Thread t = new Thread(r);
            t.setDaemon(true);
            return t;
        });
        try {
            Future<Boolean> future = executor.submit(() -> {
                try {
                    InetAddress addr = InetAddress.getByName(hostName);
                    try (Socket s = new Socket()) {
                        s.connect(new InetSocketAddress(addr, portNum), timeoutMs);
                        return true;
                    }
                } catch (Exception e) {
                    return false;
                }
            });
            return future.get(timeoutMs, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            return false;
        } finally {
            executor.shutdownNow();
        }
    }

    private int parsePort(String portStr, int defaultPort) {
        try {
            return Integer.parseInt(portStr.trim());
        } catch (Exception e) {
            return defaultPort;
        }
    }

    // Wrapper transparente: intercepta close() para no destruir la conexión física compartida
    private Connection wrapConnection(final Connection physical) {
        return (Connection) java.lang.reflect.Proxy.newProxyInstance(
            Connection.class.getClassLoader(),
            new Class<?>[]{Connection.class},
            (proxy, method, args) -> {
                if ("close".equals(method.getName())) {
                    // Mantener viva la conexión compartida
                    return null;
                }
                if ("isClosed".equals(method.getName())) {
                    return physical.isClosed();
                }
                return method.invoke(physical, args);
            }
        );
    }
}
