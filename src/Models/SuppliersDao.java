package Models;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import javax.swing.JOptionPane;

public class SuppliersDao {

    ConnectionMySQL cn = new ConnectionMySQL();
    Connection conn;
    PreparedStatement pst;
    ResultSet rs;

    // =========================
    // REGISTRAR PROVEEDOR
    // =========================
    public boolean registerSuppliersQuery(Suppliers suppliers) {

        String query = "INSERT INTO suppliers "
                + "(name, description, address, telephone, email, city, created, updated_at) "
                + "VALUES (?,?,?,?,?,?,?,?)";

        Timestamp dateTime = new Timestamp(new Date().getTime());

        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);

            pst.setString(1, suppliers.getName());
            pst.setString(2, suppliers.getDescription());
            pst.setString(3, suppliers.getAddress());
            pst.setString(4, suppliers.getTelephone());
            pst.setString(5, suppliers.getEmail());
            pst.setString(6, suppliers.getCity());
            pst.setTimestamp(7, dateTime);
            pst.setTimestamp(8, dateTime);

            pst.execute();
            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al registrar proveedor: " + e);
            return false;
        }
    }

    // =========================
    // LISTAR PROVEEDORES
    // =========================
    public List<Suppliers> listSuppliersQuery(String value) {

        List<Suppliers> list_suppliers = new ArrayList<>();

        String query = "SELECT * FROM suppliers";
        String query_search = "SELECT * FROM suppliers WHERE name LIKE ?";

        try {
            conn = cn.getConnection();

            if (value == null || value.trim().isEmpty()) {
                pst = conn.prepareStatement(query);
            } else {
                pst = conn.prepareStatement(query_search);
                pst.setString(1, "%" + value + "%");
            }

            rs = pst.executeQuery();

            while (rs.next()) {

                Suppliers suppliers = new Suppliers();

                suppliers.setId(rs.getInt("id"));
                suppliers.setName(rs.getString("name"));
                suppliers.setDescription(rs.getString("description"));
                suppliers.setAddress(rs.getString("address"));
                suppliers.setTelephone(rs.getString("telephone"));
                suppliers.setEmail(rs.getString("email"));
                suppliers.setCity(rs.getString("city"));

                list_suppliers.add(suppliers);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        return list_suppliers;
    }

    // =========================
    // 🔥 NUEVO: LISTA PARA COMBOBOX (CORREGIDO)
    // =========================
    public List<DynamicComboBox> listSuppliersCombo() {

        List<DynamicComboBox> list = new ArrayList<>();

        String sql = "SELECT id, name FROM suppliers";

        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {

                list.add(new DynamicComboBox(
                        rs.getInt("id"),
                        rs.getString("name")
                ));
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        return list;
    }

    // =========================
    // ACTUALIZAR PROVEEDOR
    // =========================
    public boolean updateSuppliersQuery(Suppliers suppliers) {

        String query = "UPDATE suppliers SET name=?, description=?, address=?, "
                + "telephone=?, email=?, city=?, updated_at=? WHERE id=?";

        Timestamp dateTime = new Timestamp(new Date().getTime());

        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);

            pst.setString(1, suppliers.getName());
            pst.setString(2, suppliers.getDescription());
            pst.setString(3, suppliers.getAddress());
            pst.setString(4, suppliers.getTelephone());
            pst.setString(5, suppliers.getEmail());
            pst.setString(6, suppliers.getCity());
            pst.setTimestamp(7, dateTime);
            pst.setInt(8, suppliers.getId());

            pst.executeUpdate();
            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error modificar proveedor: " + e);
            return false;
        }
    }

    // =========================
    // ELIMINAR PROVEEDOR
    // =========================
    public boolean deleteSuppliersQuery(int id) {

        String query = "DELETE FROM suppliers WHERE id = ?";

        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setInt(1, id);

            pst.executeUpdate();
            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error eliminar proveedor: " + e);
            return false;
        }
    }
}