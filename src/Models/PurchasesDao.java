package Models;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class PurchasesDao {

    ConnectionMySQL cn = new ConnectionMySQL();

    Connection conn;

    PreparedStatement pst;

    ResultSet rs;

    // =========================
    // REGISTRAR COMPRA
    // =========================

    public boolean registerPurchaseQuery(int supplierId,
                                         int employeeId,
                                         double total) {

        String sql =
                "INSERT INTO purchases " +
                "(supplier_id, employee_id, total, created) " +
                "VALUES (?,?,?,NOW())";

        try {

            conn = cn.getConnection();

            pst = conn.prepareStatement(sql);

            pst.setInt(1, supplierId);

            pst.setInt(2, employeeId);

            pst.setDouble(3, total);

            pst.executeUpdate();

            return true;

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    null,
                    e.getMessage()
            );

            return false;
        }
    }

    // =========================
    // OBTENER ID COMPRA
    // =========================

    public int purchaseId() {

        String sql =
                "SELECT MAX(id) AS id FROM purchases";

        try {

            conn = cn.getConnection();

            pst = conn.prepareStatement(sql);

            rs = pst.executeQuery();

            if (rs.next()) {

                return rs.getInt("id");
            }

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    null,
                    e.getMessage()
            );
        }

        return 0;
    }

    // =========================
    // DETALLE COMPRA
    // =========================

    public boolean registerPurchaseDetailQuery(int purchaseId,
                                               int productId,
                                               int amount,
                                               double price,
                                               double subtotal) {

        String sql =
                "INSERT INTO purchase_details " +
                "(purchase_id, product_id, purchase_amount, " +
                "purchase_price, purchase_subtotal, purchase_date) " +
                "VALUES (?,?,?,?,?,NOW())";

        try {

            conn = cn.getConnection();

            pst = conn.prepareStatement(sql);

            pst.setInt(1, purchaseId);

            pst.setInt(2, productId);

            pst.setInt(3, amount);

            pst.setDouble(4, price);

            pst.setDouble(5, subtotal);

            pst.executeUpdate();

            return true;

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    null,
                    e.getMessage()
            );

            return false;
        }
    }

    // =========================
    // LISTAR COMPRAS
    // =========================

    public List<Purchases> listAllPurchasesQuery() {

        List<Purchases> listPurchases =
                new ArrayList<>();

        String sql =
                "SELECT p.id, " +
                "s.name AS supplier, " +
                "p.total, " +
                "p.created, " +
                "e.full_name AS employee " +
                "FROM purchases p " +
                "INNER JOIN suppliers s " +
                "ON p.supplier_id = s.id " +
                "INNER JOIN employees e " +
                "ON p.employee_id = e.id " +
                "ORDER BY p.id DESC";

        try {

            conn = cn.getConnection();

            pst = conn.prepareStatement(sql);

            rs = pst.executeQuery();

            while (rs.next()) {

                Purchases purchase =
                        new Purchases();

                purchase.setId(
                        rs.getInt("id")
                );

                purchase.setSupplier_name(
                        rs.getString("supplier")
                );

                purchase.setTotal(
                        rs.getDouble("total")
                );

                purchase.setCreated(
                        rs.getString("created")
                );

                purchase.setEmployee_name(
                        rs.getString("employee")
                );

                listPurchases.add(purchase);
            }

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    null,
                    e.getMessage()
            );
        }

        return listPurchases;
    }
}