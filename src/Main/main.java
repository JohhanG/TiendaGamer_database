package Main;

import Views.LoginView;
import Models.ConnectionMySQL;
import java.sql.Connection;

public class main {
    
    public static void main(String[] args) {
        // Mostrar la ventana de login de inmediato sin esperar la red
        LoginView login = new LoginView();
        login.setLocationRelativeTo(null);
        login.setVisible(true);

        // Precalentar la conexión SSL hacia la base de datos en segundo plano mientras el usuario escribe
        new Thread(() -> {
            try {
                ConnectionMySQL con = new ConnectionMySQL();
                Connection cn = con.getConnection();
                if (cn != null) {
                    System.out.println("Conexión a la base de datos precalentada exitosamente.");
                }
            } catch (Exception ignore) {}
        }).start();
    }
}