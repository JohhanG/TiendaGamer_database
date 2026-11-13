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

    // =========================================================================
    // RNF-003: REGISTRAR COMPRA COMPLETA BAJO TRANSACCIÓN ATÓMICA ACID (CON ROLLBACK)
    // =========================================================================
    public boolean registerPurchaseTransactional(Purchases purchase, List<Object[]> items) {
        String insertPurchase = "INSERT INTO purchases (supplier_id, employee_id, total, estado, created) VALUES (?,?,?,?,?)";
        String insertDetail = "INSERT INTO purchase_details (purchase_id, product_id, purchase_amount, purchase_price, purchase_subtotal, purchase_date) VALUES (?,?,?,?,?,?)";
        String selectStock = "SELECT product_quantity FROM products WHERE id = ?";
        String updateStock = "UPDATE products SET product_quantity = product_quantity + ? WHERE id = ?";
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

            // 1. Insertar Cabecera de Compra
            int purchaseId = 0;
            try (PreparedStatement pstPur = conn.prepareStatement(insertPurchase, Statement.RETURN_GENERATED_KEYS)) {
                pstPur.setInt(1, purchase.getSupplier_id());
                pstPur.setInt(2, purchase.getEmployee_id());
                pstPur.setDouble(3, purchase.getTotal());
                pstPur.setString(4, purchase.getEstado());
                pstPur.setTimestamp(5, dateTime);
                pstPur.executeUpdate();

                try (ResultSet rsKeys = pstPur.getGeneratedKeys()) {
                    if (rsKeys.next()) {
                        purchaseId = rsKeys.getInt(1);
                    }
                }
            }

            if (purchaseId <= 0) {
                conn.rollback();
                throw new SQLException("No se pudo generar el ID único de la orden de compra.");
            }

            // 2. Procesar cada producto adquirido
            for (Object[] item : items) {
                int productId = (int) item[0];
                int amount    = (int) item[1];
                double price  = (double) item[2];
                double sub    = (double) item[3];

                // 2.1 Obtener stock anterior
                int stockAnterior = 0;
                try (PreparedStatement pstStock = conn.prepareStatement(selectStock)) {
                    pstStock.setInt(1, productId);
                    try (ResultSet rsStock = pstStock.executeQuery()) {
                        if (rsStock.next()) {
                            stockAnterior = rsStock.getInt("product_quantity");
                        }
                    }
                }

                int stockNuevo = stockAnterior + amount;

                // 2.2 Insertar detalle de compra
                try (PreparedStatement pstDet = conn.prepareStatement(insertDetail)) {
                    pstDet.setInt(1, purchaseId);
                    pstDet.setInt(2, productId);
                    pstDet.setInt(3, amount);
                    pstDet.setDouble(4, price);
                    pstDet.setDouble(5, sub);
                    pstDet.setTimestamp(6, dateTime);
                    pstDet.executeUpdate();
                }

                // 2.3 Incrementar stock en catálogo
                try (PreparedStatement pstUpd = conn.prepareStatement(updateStock)) {
                    pstUpd.setInt(1, amount);
                    pstUpd.setInt(2, productId);
                    pstUpd.executeUpdate();
                }

                // 2.4 Registrar movimiento de ENTRADA en el Kardex
                try (PreparedStatement pstKardex = conn.prepareStatement(insertKardex)) {
                    pstKardex.setInt(1, productId);
                    pstKardex.setInt(2, 2); // 2: COMPRA (ENTRADA)
                    pstKardex.setInt(3, purchase.getEmployee_id());
                    pstKardex.setInt(4, amount);
                    pstKardex.setString(5, "ENTRADA");
                    pstKardex.setInt(6, stockAnterior);
                    pstKardex.setInt(7, stockNuevo);
                    pstKardex.setString(8, "Abastecimiento Factura Compra N° " + purchaseId);
                    pstKardex.executeUpdate();
                }
            }

            // COMMIT TRANSACCIONAL
            conn.commit();
            return true;

        } catch (SQLException e) {
            // ROLLBACK EN CASO DE ERROR (RNF-003)
            try {
                if (conn != null) {
                    conn.rollback();
                    System.err.println("¡ROLLBACK ejecutado con éxito! Se revirtió la compra por: " + e.getMessage());
                }
            } catch (SQLException exRollback) {
                System.err.println("Error al ejecutar rollback en compra: " + exRollback.getMessage());
            }
            JOptionPane.showMessageDialog(null,
                    "Error transaccional al procesar la compra. Se ejecutó ROLLBACK:\n" + e.getMessage(),
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

    // =========================================
    // REGISTRAR ENCABEZADO DE LA COMPRA (COMPATIBILIDAD)
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
            if (conn == null) return false;

            // RNF-003: INICIO DE TRANSACCIÓN ACID
            conn.setAutoCommit(false);

            // 0. Obtener employee_id de la compra original para Kardex
            int employeeId = EmployeesDao.id_user;
            try (PreparedStatement pstEmp = conn.prepareStatement("SELECT employee_id FROM purchases WHERE id = ?")) {
                pstEmp.setInt(1, purchaseId);
                try (ResultSet rsEmp = pstEmp.executeQuery()) {
                    if (rsEmp.next()) {
                        int eId = rsEmp.getInt("employee_id");
                        if (eId > 0) employeeId = eId;
                    }
                }
            } catch (SQLException ignored) {}
            
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

                // 3. Registrar movimiento en Kardex (SALIDA por devolución a proveedor)
                try {
                    KardexDao kardexDao = new KardexDao();
                    ProductsDao prodDao = new ProductsDao();
                    Products prod = prodDao.searchCode(productId);
                    int stockActual = (prod != null) ? prod.getProduct_quantity() : 0;
                    int stockAnterior = stockActual + amount;

                    Kardex k = new Kardex();
                    k.setIdProducto(productId);
                    k.setIdTipoMov(4); // 4: DEVOLUCION PROVEEDOR (SALIDA)
                    k.setIdEmpleado(employeeId);
                    k.setCantidad(amount);
                    k.setEfecto("SALIDA");
                    k.setSaldoAnterior(stockAnterior);
                    k.setSaldoResultante(stockActual);
                    k.setObservacion("Devolución Compra N° " + purchaseId + " - Motivo: " + motivo);
                    kardexDao.registrarMovimiento(k);
                } catch (Exception ignored) {}
            }
            rsDetails.close();
            pstDetails.close();

            // COMMIT TRANSACCIONAL
            conn.commit();
            return true;
        } catch (SQLException e) {
            // ROLLBACK EN CASO DE FALLO (RNF-003)
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ignored) {}
            JOptionPane.showMessageDialog(null, "Error al cancelar la compra (Rollback ejecutado): " + e);
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
}