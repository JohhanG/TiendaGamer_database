package Models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;

public class CustomersDao {

    ConnectionMySQL cn = new ConnectionMySQL();
    Connection conn;
    PreparedStatement pst;
    ResultSet rs;

    // =========================
    // REGISTRAR CLIENTE
    // =========================
    public boolean registerCustomersQuery(Customers customer) {

        String query = "INSERT INTO customers("
                + "id, full_name, address, telephone, "
                + "email, created, updated_at"
                + ") VALUES (?,?,?,?,?,?,?)";

        Timestamp dateTime = new Timestamp(new Date().getTime());

        try {
            conn = cn.getConnection();
            pst  = conn.prepareStatement(query);

            pst.setInt(1,       customer.getId());
            pst.setString(2,    customer.getFull_name());
            pst.setString(3,    customer.getAddress());
            pst.setString(4,    customer.getTelephone());
            pst.setString(5,    customer.getEmail());
            pst.setTimestamp(6, dateTime);
            pst.setTimestamp(7, dateTime);

            pst.execute();
            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al registrar cliente: " + e);
            return false;
        }
    }

    // =========================
    // LISTAR CLIENTES
    // Busca por ID o por nombre completo
    // =========================
    public List<Customers> listCustomersQuery(String value) {

        List<Customers> list = new ArrayList<>();

        String query;

        if (value == null || value.trim().isEmpty()) {
            // Sin filtro: traer todos
            query = "SELECT * FROM customers ORDER BY full_name ASC";
        } else {
            // Con filtro: buscar por ID o nombre
            query = "SELECT * FROM customers "
                  + "WHERE CAST(id AS CHAR) LIKE ? "
                  + "OR full_name LIKE ? "
                  + "ORDER BY full_name ASC";
        }

        try {
            conn = cn.getConnection();
            pst  = conn.prepareStatement(query);

            if (value != null && !value.trim().isEmpty()) {
                pst.setString(1, "%" + value.trim() + "%");
                pst.setString(2, "%" + value.trim() + "%");
            }

            rs = pst.executeQuery();

            while (rs.next()) {
                Customers c = new Customers();
                c.setId(rs.getInt("id"));
                c.setFull_name(rs.getString("full_name"));
                c.setAddress(rs.getString("address"));
                c.setTelephone(rs.getString("telephone"));
                c.setEmail(rs.getString("email"));
                list.add(c);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al listar clientes: " + e);
        }

        return list;
    }

    // =========================
    // BUSCAR CLIENTE POR ID
    // =========================
    public Customers searchCustomer(int id) {

        Customers customer = null;
        String query = "SELECT * FROM customers WHERE id = ?";

        try {
            conn = cn.getConnection();
            pst  = conn.prepareStatement(query);
            pst.setInt(1, id);
            rs = pst.executeQuery();

            if (rs.next()) {
                customer = new Customers();
                customer.setId(rs.getInt("id"));
                customer.setFull_name(rs.getString("full_name"));
                customer.setAddress(rs.getString("address"));
                customer.setTelephone(rs.getString("telephone"));
                customer.setEmail(rs.getString("email"));
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar cliente: " + e);
        }

        return customer;
    }

    // =========================
    // MODIFICAR CLIENTE
    // =========================
    public boolean updateCustomersQuery(Customers customer) {

        String query = "UPDATE customers SET "
                + "full_name = ?, address = ?, telephone = ?, "
                + "email = ?, updated_at = ? "
                + "WHERE id = ?";

        Timestamp dateTime = new Timestamp(new Date().getTime());

        try {
            conn = cn.getConnection();
            pst  = conn.prepareStatement(query);

            pst.setString(1,    customer.getFull_name());
            pst.setString(2,    customer.getAddress());
            pst.setString(3,    customer.getTelephone());
            pst.setString(4,    customer.getEmail());
            pst.setTimestamp(5, dateTime);
            pst.setInt(6,       customer.getId());

            pst.execute();
            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al modificar cliente: " + e);
            return false;
        }
    }

    // =========================
    // ELIMINAR CLIENTE
    // =========================
    public boolean deleteCustomersQuery(int id) {

        String query = "DELETE FROM customers WHERE id = ?";

        try {
            conn = cn.getConnection();
            pst  = conn.prepareStatement(query);
            pst.setInt(1, id);
            pst.execute();
            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "No se puede eliminar: el cliente está relacionado con otra tabla.\n" + e);
            return false;
        }
    }
}