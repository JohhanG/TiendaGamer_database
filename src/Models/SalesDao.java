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

    // =========================================================================
    // RNF-003: REGISTRAR VENTA COMPLETA BAJO TRANSACCIÓN ATÓMICA ACID (CON ROLLBACK)
    // =========================================================================
    public boolean registerSaleTransactional(int customer_id, int employee_id, double total, List<Object[]> items) {
        String insertSale = "INSERT INTO sales(customer_id, employee_id, total, sale_date, estado) VALUES(?,?,?,?,?)";
        String insertDetail = "INSERT INTO sale_details(sale_id, product_id, sale_quantity, sale_price, sale_subtotal) VALUES(?,?,?,?,?)";
        String selectStock = "SELECT product_quantity FROM products WHERE id = ? FOR UPDATE";
        String updateStock = "UPDATE products SET product_quantity = product_quantity - ? WHERE id = ?";
        String insertKardex = "INSERT INTO kardex (idProducto, idTipoMov, idEmpleado, cantidad, efecto, saldoAnterior, saldoResultante, observacion) VALUES (?,?,?,?,?,?,?,?)";

        Timestamp dateTime = new Timestamp(new Date().getTime());

        try {
            conn = cn.getConnection();
            if (conn == null) {
                JOptionPane.showMessageDialog(null, "No hay conexión disponible con la base de datos.");
                return false;
            }

            // INICIO DE LA TRANSACCIÓN ACID (RNF-003)
            conn.setAutoCommit(false);

            // 1. Insertar Cabecera de la Venta
            int saleId = 0;
            try (PreparedStatement pstSale = conn.prepareStatement(insertSale, Statement.RETURN_GENERATED_KEYS)) {
                pstSale.setInt(1, customer_id);
                pstSale.setInt(2, employee_id);
                pstSale.setDouble(3, total);
                pstSale.setTimestamp(4, dateTime);
                pstSale.setString(5, "COMPLETADA");
                pstSale.executeUpdate();

                try (ResultSet rsKeys = pstSale.getGeneratedKeys()) {
                    if (rsKeys.next()) {
                        saleId = rsKeys.getInt(1);
                    }
                }
            }

            if (saleId <= 0) {
                conn.rollback();
                throw new SQLException("No se pudo generar el ID único de la venta.");
            }

            // 2. Procesar cada ítem del carrito
            for (Object[] item : items) {
                int productId = (int) item[0];
                int quantity  = (int) item[1];
                double price  = (double) item[2];
                double sub    = (double) item[3];

                // 2.1 Verificar stock actual con bloqueo pesimista (FOR UPDATE)
                int stockAnterior = 0;
                try (PreparedStatement pstStockCheck = conn.prepareStatement(selectStock)) {
                    pstStockCheck.setInt(1, productId);
                    try (ResultSet rsStock = pstStockCheck.executeQuery()) {
                        if (rsStock.next()) {
                            stockAnterior = rsStock.getInt("product_quantity");
                        } else {
                            conn.rollback();
                            throw new SQLException("El producto ID " + productId + " no existe.");
                        }
                    }
                }

                if (stockAnterior < quantity) {
                    conn.rollback();
                    JOptionPane.showMessageDialog(null, 
                            "Transacción abortada (Rollback): Stock insuficiente para el producto ID " + productId + ".\n"
                            + "Disponible: " + stockAnterior + ", Solicitado: " + quantity,
                            "Error de Inventario", JOptionPane.ERROR_MESSAGE);
                    return false;
                }

                int stockNuevo = stockAnterior - quantity;

                // 2.2 Insertar detalle de venta
                try (PreparedStatement pstDetail = conn.prepareStatement(insertDetail)) {
                    pstDetail.setInt(1, saleId);
                    pstDetail.setInt(2, productId);
                    pstDetail.setInt(3, quantity);
                    pstDetail.setDouble(4, price);
                    pstDetail.setDouble(5, sub);
                    pstDetail.executeUpdate();
                }

                // 2.3 Descontar stock
                try (PreparedStatement pstUpdStock = conn.prepareStatement(updateStock)) {
                    pstUpdStock.setInt(1, quantity);
                    pstUpdStock.setInt(2, productId);
                    pstUpdStock.executeUpdate();
                }

                // 2.4 Registrar en Kardex
                try (PreparedStatement pstKardex = conn.prepareStatement(insertKardex)) {
                    pstKardex.setInt(1, productId);
                    pstKardex.setInt(2, 1); // 1: VENTA (SALIDA)
                    pstKardex.setInt(3, employee_id);
                    pstKardex.setInt(4, quantity);
                    pstKardex.setString(5, "SALIDA");
                    pstKardex.setInt(6, stockAnterior);
                    pstKardex.setInt(7, stockNuevo);
                    pstKardex.setString(8, "Venta realizada en Factura N° " + saleId);
                    pstKardex.executeUpdate();
                }
            }

            // TODAS LAS OPERACIONES FUERON EXITOSAS -> COMMIT TRANSACCIONAL
            conn.commit();
            return true;

        } catch (SQLException e) {
            // EN CASO DE CUALQUIER FALLO -> ROLLBACK INMEDIATO (RNF-003)
            try {
                if (conn != null) {
                    conn.rollback();
                    System.err.println("¡ROLLBACK ejecutado con éxito! Se revirtió la venta por: " + e.getMessage());
                }
            } catch (SQLException exRollback) {
                System.err.println("Error al ejecutar rollback: " + exRollback.getMessage());
            }
            JOptionPane.showMessageDialog(null, 
                    "Error transaccional al procesar la venta. Se ejecutó ROLLBACK:\n" + e.getMessage(),
                    "Error de Transacción (ACID)", JOptionPane.ERROR_MESSAGE);
            return false;

        } finally {
            try {
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (SQLException ignored) {}
        }
    }

    // =========================
    // REGISTRAR VENTA (COMPATIBILIDAD)
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
            if (conn == null) return false;

            // RNF-003: TRANSACCIÓN ACID CON ROLLBACK
            conn.setAutoCommit(false);

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

            // CONFIRMACIÓN DE LA TRANSACCIÓN
            conn.commit();
            return true;

        } catch (SQLException e) {
            // ROLLBACK EN CASO DE FALLO (RNF-003)
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ignored) {}
            JOptionPane.showMessageDialog(null, "Error al procesar la devolución de la venta (Rollback ejecutado): " + e.getMessage());
            return false;
        } finally {
            try {
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (SQLException ignored) {}
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