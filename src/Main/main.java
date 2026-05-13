package Main;

import Views.LoginView;
import Models.ConnectionMySQL;
import java.sql.Connection;

public class main {
    
    public static void main(String[] args){
        
        // Probar conexión a la base de datos
        ConnectionMySQL con = new ConnectionMySQL();
        Connection cn = con.getConnection();

        if(cn != null){
            System.out.println("Conexión exitosa a la base de datos");
        }else{
            System.out.println("Error de conexión");
        }

        // Tu código original para abrir el login
        LoginView login = new LoginView();
        login.setVisible(true);
    }
}