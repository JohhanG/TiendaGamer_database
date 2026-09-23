package Models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;

public class PurchasesDao {

    ConnectionMySQL cn = new ConnectionMySQL();
    Connection conn;
    PreparedStatement pst;
    ResultSet rs;

    // =========================================
    // REGISTRAR ENCABEZADO DE LA COMPRA
    // =========================================
    public int registerPurchaseQuery(Purchases purchase) {
        String query = "INSERT INTO purchases (supplier_id, employee_id, total, estado, created) VALUES (?,?,?,?,?)";
        Timestamp dateTime = new Timestamp(new Date().getTime());
        int purchaseId = 0;

        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            pst.setInt(1, purchase.getSupplier_id());
            pst.setInt(2, purchase.getEmployee_id());
            pst.setDouble(3, purchase.getTotal());
            pst.setString(4, purchase.getEstado());
            pst.setTimestamp(5, dateTime);

            pst.executeUpdate();

            rs = pst.getGeneratedKeys();
            if (rs.next()) {
                purchaseId = rs.getInt(1);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al registrar la compra: " + e);
        } finally {
            try {
                if (rs != null) rs.close();
                if (pst != null) pst.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                System.out.println(ex.toString());
            }
        }
        return purchaseId;
    }

    // =========================================
    // REGISTRAR DETALLE DE LA COMPRA
    // =========================================
    public boolean registerPurchaseDetailsQuery(int purchaseId, int productId, int amount, double price, double subtotal) {
        String query = "INSERT INTO purchase_details (purchase_id, product_id, purchase_amount, purchase_price, purchase_subtotal, purchase_date) VALUES (?,?,?,?,?,?)";
        Timestamp dateTime = new Timestamp(new Date().getTime());

        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);

            pst.setInt(1, purchaseId);
            pst.setInt(2, productId);
            pst.setInt(3, amount);
            pst.setDouble(4, price);
            pst.setDouble(5, subtotal);
            pst.setTimestamp(6, dateTime);

            pst.execute();
            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al registrar el detalle de la compra: " + e);
            return false;
        } finally {
            try {
                if (pst != null) pst.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                System.out.println(ex.toString());
            }
        }
    }

    // =========================================
    // LISTAR TODAS LAS COMPRAS PARA REPORTES
    // =========================================
    public List<Purchases> listAllPurchasesQuery() {
        List<Purchases> listPurchases = new ArrayList<>();
        // ✅ Se agregó "p.motivo_cancelacion" al SELECT para poder mostrarlo
        // en el banner de detalle de la devolución.
        String query = "SELECT p.id, s.name AS supplier_name, p.total, p.created, p.estado, p.motivo_cancelacion "
                     + "FROM purchases p "
                     + "INNER JOIN suppliers s ON p.supplier_id = s.id "
                     + "ORDER BY p.created DESC";
        
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            rs = pst.executeQuery();

            while (rs.next()) {
                Purchases purchase = new Purchases();
                purchase.setId(rs.getInt("id"));
                purchase.setSupplier_name(rs.getString("supplier_name"));
                purchase.setTotal(rs.getDouble("total"));
                purchase.setCreated(rs.getString("created"));
                purchase.setEstado(rs.getString("estado"));
                purchase.setMotivoCancelacion(rs.getString("motivo_cancelacion")); // ✅ NUEVO
                listPurchases.add(purchase);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al listar las compras: " + e);
        } finally {
            try {
                if (rs != null) rs.close();
                if (pst != null) pst.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                System.out.println(ex.toString());
            }
        }
        return listPurchases;
    }

    // =========================================
    // CANCELAR COMPRA Y REVERTIR STOCK
    // =========================================
    // ✅ CORREGIDO: ahora recibe "motivo" y lo guarda junto con el estado.
    public boolean cancelPurchaseQuery(int purchaseId, String motivo) {
        String updatePurchase = "UPDATE purchases SET estado = 'CANCELADA', motivo_cancelacion = ? WHERE id = ?";
        String getDetails = "SELECT product_id, purchase_amount FROM purchase_details WHERE purchase_id = ?";
        String updateStock = "UPDATE products SET product_quantity = product_quantity - ? WHERE id = ?";

        try {
            conn = cn.getConnection();
            
            // 1. Cambiar estado de la compra a CANCELADA y guardar el motivo
            pst = conn.prepareStatement(updatePurchase);
            pst.setString(1, motivo);
            pst.setInt(2, purchaseId);
            pst.executeUpdate();
            pst.close();

            // 2. Obtener los detalles para descontar el stock del almacén
            PreparedStatement pstDetails = conn.prepareStatement(getDetails);
            pstDetails.setInt(1, purchaseId);
            ResultSet rsDetails = pstDetails.executeQuery();

            while (rsDetails.next()) {
                int productId = rsDetails.getInt("product_id");
                int amount = rsDetails.getInt("purchase_amount");

                PreparedStatement pstStock = conn.prepareStatement(updateStock);
                pstStock.setInt(1, amount);
                pstStock.setInt(2, productId);
                pstStock.executeUpdate();
                pstStock.close();
            }
            rsDetails.close();
            pstDetails.close();

            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al cancelar la compra: " + e);
            return false;
        } finally {
            try {
                if (pst != null) pst.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                System.out.println(ex.toString());
            }
        }
    }
}