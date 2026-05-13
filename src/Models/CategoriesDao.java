package Models;

import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;

public class CategoriesDao {

    ConnectionMySQL cn = new ConnectionMySQL();
    Connection conn;
    PreparedStatement pst;
    ResultSet rs;

    // Registrar Categoria
    public boolean registerCategoryQuery(Categories categories) {

        String query = "INSERT INTO categories(name, created, updated_at) VALUES (?,?,?)";

        Timestamp dateTime = new Timestamp(new Date().getTime());

        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);

            pst.setString(1, categories.getName());
            pst.setTimestamp(2, dateTime);
            pst.setTimestamp(3, dateTime);

            pst.execute();
            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al registrar la categoría: " + e);
            return false;
        }
    }

    //Listar Categorias
    public List listCategoriesQuery(String value) {
        List<Categories> list_categories = new ArrayList();
        String query = "SELECT * FROM categories";
        String query_search_category = "SELECT * FROM categories WHERE name LIKE'%" + value + "%'";
        try {
            conn = cn.getConnection();
            if (value.equalsIgnoreCase("")) {
                pst = conn.prepareStatement(query);
                rs = pst.executeQuery();
            } else {
                pst = conn.prepareStatement(query_search_category);
                rs = pst.executeQuery();
            }
             while(rs.next()){
                Categories category = new Categories();
                category.setId(rs.getInt("id"));
                category.setName(rs.getString("name"));
                list_categories.add(category);
             }
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return list_categories;
    }
    
    //Modificar Categoria
     public boolean updateCategoryQuery(Categories categories) {

        String query = "UPDATE categories SET name = ?,updated_at = ? WHERE id = ?";
        Timestamp dateTime = new Timestamp(new Date().getTime());

        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);

            pst.setString(1, categories.getName());
            pst.setTimestamp(2, dateTime);
            pst.setInt(3, categories.getId());

            pst.execute();
            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al registrar la categoría: " + e);
            return false;
        }
    }
     
     // Eliminar Categoria
     public boolean deleteCategoriesQuery(int id){
         String query = "DELETE FROM categories WHERE id = "+id;
         try{
             conn = cn.getConnection();
             pst = conn.prepareStatement(query);
             pst.execute();
             return true;
         }catch(SQLException e){
             JOptionPane.showMessageDialog(null,"Error al eliminar la categoria"+e);
             return false;  
         }
     }
}
