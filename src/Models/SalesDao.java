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

public class SalesDao {

    ConnectionMySQL cn = new ConnectionMySQL();
    Connection conn;
    PreparedStatement pst;
    ResultSet rs;

    // =========================
    // REGISTRAR VENTA
    // =========================
    public int registerSaleQuery(int customer_id, int employee_id, double total) {
        String query = "INSERT INTO sales(customer_id, employee_id, total, sale_date, estado) VALUES(?,?,?,?,?)";
        Timestamp dateTime = new Timestamp(new Date().getTime());

        try {
            conn = cn.getConnection();
            pst  = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            pst.setInt(1, customer_id);
            pst.setInt(2, employee_id);
            pst.setDouble(3, total);
            pst.setTimestamp(4, dateTime);
            pst.setString(5, "COMPLETADA");

            pst.executeUpdate();

            rs = pst.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } finally {
            try { if (rs   != null) rs.close();   } catch (SQLException ignored) {}
            try { if (pst  != null) pst.close();  } catch (SQLException ignored) {}
            try { if (conn != null) conn.close(); } catch (SQLException ignored) {}
        }

        return 0;
    }

    // =========================
    // REGISTRAR DETALLE VENTA
    // =========================
    public boolean registerSaleDetailQuery(int sale_id, int product_id, int sale_quantity, double sale_price, double sale_subtotal) {
        String query = "INSERT INTO sale_details(sale_id, product_id, sale_quantity, sale_price, sale_subtotal) VALUES(?,?,?,?,?)";

        try {
            conn = cn.getConnection();
            pst  = conn.prepareStatement(query);

            pst.setInt(1, sale_id);
            pst.setInt(2, product_id);
            pst.setInt(3, sale_quantity);
            pst.setDouble(4, sale_price);
            pst.setDouble(5, sale_subtotal);

            pst.execute();
            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return false;
        } finally {
            try { if (pst  != null) pst.close();  } catch (SQLException ignored) {}
            try { if (conn != null) conn.close(); } catch (SQLException ignored) {}
        }
    }

    // =========================
    // LISTAR VENTAS
    // =========================
    public List<Sales> listAllSalesQuery() {
        List<Sales> listSales = new ArrayList<>();

        // ✅ Se agregó "s.motivo_cancelacion" al SELECT para poder mostrarlo
        // en el banner de detalle de la devolución.
        String sql = "SELECT s.id, "
                   + "c.full_name AS customer, "
                   + "e.full_name AS employee, "
                   + "s.total, "
                   + "s.sale_date, "
                   + "s.estado, "
                   + "s.motivo_cancelacion "
                   + "FROM sales s "
                   + "INNER JOIN customers c ON s.customer_id = c.id "
                   + "INNER JOIN employees e ON s.employee_id = e.id "
                   + "ORDER BY s.id DESC";

        try {
            conn = cn.getConnection();
            pst  = conn.prepareStatement(sql);
            rs   = pst.executeQuery();

            while (rs.next()) {
                Sales sale = new Sales();
                sale.setId(rs.getInt("id"));
                sale.setCustomer_name(rs.getString("customer"));
                sale.setEmployee_name(rs.getString("employee"));
                sale.setTotal(rs.getDouble("total"));
                sale.setSale_date(rs.getString("sale_date"));
                sale.setEstado(rs.getString("estado"));
                sale.setMotivoCancelacion(rs.getString("motivo_cancelacion")); // ✅ NUEVO
                listSales.add(sale);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } finally {
            try { if (rs   != null) rs.close();   } catch (SQLException ignored) {}
            try { if (pst  != null) pst.close();  } catch (SQLException ignored) {}
            try { if (conn != null) conn.close(); } catch (SQLException ignored) {}
        }

        return listSales;
    }

    // =========================================
    // CANCELAR VENTA (DEVOLUCIÓN DE CLIENTE) Y REINTEGRAR STOCK
    // =========================================
    // ✅ CORREGIDO: ahora además de cambiar el estado y guardar el motivo,
    // consulta los detalles de la venta y reintegra el stock de los productos devueltos.
    public boolean cancelSaleQuery(int id, String motivo) {
        String updateSale = "UPDATE sales SET estado = 'CANCELADA', motivo_cancelacion = ? WHERE id = ?";
        String getSaleInfo = "SELECT employee_id FROM sales WHERE id = ?";
        String getDetails = "SELECT product_id, sale_quantity FROM sale_details WHERE sale_id = ?";
        String updateStock = "UPDATE products SET product_quantity = product_quantity + ? WHERE id = ?";

        try {
            conn = cn.getConnection();

            // 0. Obtener employee_id de la venta original para Kardex
            int employeeId = 0;
            PreparedStatement pstInfo = conn.prepareStatement(getSaleInfo);
            pstInfo.setInt(1, id);
            ResultSet rsInfo = pstInfo.executeQuery();
            if (rsInfo.next()) {
                employeeId = rsInfo.getInt("employee_id");
            }
            rsInfo.close();
            pstInfo.close();

            // 1. Cambiar estado de la venta a CANCELADA y guardar el motivo
            pst = conn.prepareStatement(updateSale);
            pst.setString(1, motivo);
            pst.setInt(2, id);
            pst.executeUpdate();
            pst.close();

            // 2. Obtener los detalles de la venta para reintegrar el stock al almacén
            PreparedStatement pstDetails = conn.prepareStatement(getDetails);
            pstDetails.setInt(1, id);
            ResultSet rsDetails = pstDetails.executeQuery();

            while (rsDetails.next()) {
                int productId = rsDetails.getInt("product_id");
                int quantity  = rsDetails.getInt("sale_quantity");

                // Reintegrar al stock de productos
                PreparedStatement pstStock = conn.prepareStatement(updateStock);
                pstStock.setInt(1, quantity);
                pstStock.setInt(2, productId);
                pstStock.executeUpdate();
                pstStock.close();

                // 3. Registrar movimiento en Kardex (ENTRADA por devolución)
                try {
                    KardexDao kardexDao = new KardexDao();
                    ProductsDao productsDao = new ProductsDao();
                    Products prod = productsDao.searchCode(productId);
                    int stockActual = (prod != null) ? prod.getProduct_quantity() : quantity;
                    int stockAnterior = stockActual - quantity;

                    Kardex k = new Kardex();
                    k.setIdProducto(productId);
                    k.setIdTipoMov(3); // 3: DEVOLUCION CLIENTE (ENTRADA)
                    k.setIdEmpleado(employeeId);
                    k.setCantidad(quantity);
                    k.setEfecto("ENTRADA");
                    k.setSaldoAnterior(stockAnterior);
                    k.setSaldoResultante(stockActual);
                    k.setObservacion("Devolución Venta N° " + id + " - Motivo: " + motivo);
                    kardexDao.registrarMovimiento(k);
                } catch (Exception ignored) {}
            }
            rsDetails.close();
            pstDetails.close();

            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al procesar la devolución de la venta: " + e.getMessage());
            return false;
        } finally {
            try { if (pst  != null) pst.close();  } catch (SQLException ignored) {}
            try { if (conn != null) conn.close(); } catch (SQLException ignored) {}
        }
    }

    // =========================================
    // REINTEGRAR STOCK AL RECIBIR DEVOLUCIÓN (MÉTODO AUXILIAR)
    // =========================================
    public boolean reverseSaleStockQuery(int amount, int product_id) {
        String sql = "UPDATE products SET product_quantity = product_quantity + ? WHERE id = ?";
        try {
            conn = cn.getConnection();
            pst  = conn.prepareStatement(sql);
            pst.setInt(1, amount);
            pst.setInt(2, product_id);
            pst.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return false;
        } finally {
            try { if (pst  != null) pst.close();  } catch (SQLException ignored) {}
            try { if (conn != null) conn.close(); } catch (SQLException ignored) {}
        }
    }
}