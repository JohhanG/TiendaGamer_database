package Models;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionMySQL {

    private String host = "localhost";
    private String port = "3306";
    private String database_name = "tiendagamer_database";
    private String user = "root";
    private String password = ""; // Contraseña por defecto para entornos estándar (XAMPP)

    public ConnectionMySQL() {
        loadConfiguration();
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
                // Intentar leer desde el classpath si está empaquetado
                try (InputStream is = getClass().getResourceAsStream("/db.properties")) {
                    if (is != null) {
                        prop.load(is);
                    }
                }
            }

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
        } catch (Exception e) {
            System.err.println("Aviso: No se pudo leer db.properties, usando valores por defecto: " + e.getMessage());
        }
    }

    public Connection getConnection() {
        Connection conn = null;
        String url = "jdbc:mysql://" + host + ":" + port + "/" + database_name 
                + "?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException e) {
            System.err.println("Ha ocurrido un ClassNotFoundException: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Ha ocurrido un SQLException al conectar a la base de datos (" + url + "): " + e.getMessage());
        }
        return conn;
    }
}