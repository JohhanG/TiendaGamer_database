package Models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionMySQL {

    private final String database_name = "tiendagamer_database";
    private final String user = "root";
    private final String password = "Familia22@#";
    private final String url = "jdbc:mysql://localhost:3306/" + database_name;

    Connection conn = null;

    public Connection getConnection(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, password);
        }catch(ClassNotFoundException e){
            System.err.println("Ha ocurrido un ClassNotFoundException: " + e.getMessage());
        }catch(SQLException e){
            System.err.println("Ha ocurrido un SQLException: " + e.getMessage());
        }
        return conn;
    }
}