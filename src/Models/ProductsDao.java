package Models;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class ProductsDao {

    ConnectionMySQL cn = new ConnectionMySQL();
    Connection conn;
    PreparedStatement pst;
    ResultSet rs;

    // =========================
    // CERRAR RECURSOS
    // =========================
    private void closeResources() {
        try { if (rs   != null) rs.close();  } catch (SQLException ignored) {}
        try { if (pst  != null) pst.close(); } catch (SQLException ignored) {}
        try { if (conn != null) conn.close();} catch (SQLException ignored) {}
    }

    // =========================
    // REGISTRAR PRODUCTO
    // =========================
    public boolean registerProductsQuery(Products product) {

        String sql = "INSERT INTO products(code, name, description, unit_price, product_quantity, category_id, status) "
                   + "VALUES(?, ?, ?, ?, ?, ?, 1)";

        try {
            conn = cn.getConnection();
            pst  = conn.prepareStatement(sql);

            pst.setInt(1,    product.getCode());
            pst.setString(2, product.getName());
            pst.setString(3, product.getDescription());
            pst.setDouble(4, product.getUnit_price());
            pst.setInt(5,    product.getProduct_quantity());
            pst.setInt(6,    product.getCategory_id());

            pst.executeUpdate();
            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al registrar producto: " + e);
            return false;
        } finally {
            closeResources();
        }
    }

    // =========================
    // MODIFICAR PRODUCTO
    // =========================
    public boolean updateProductQuery(Products product) {

        String sql = "UPDATE products SET code = ?, name = ?, description = ?, unit_price = ?, "
                   + "product_quantity = ?, category_id = ? WHERE id = ?";

        try {
            conn = cn.getConnection();
            pst  = conn.prepareStatement(sql);

            pst.setInt(1,    product.getCode());
            pst.setString(2, product.getName());
            pst.setString(3, product.getDescription());
            pst.setDouble(4, product.getUnit_price());
            pst.setInt(5,    product.getProduct_quantity());
            pst.setInt(6,    product.getCategory_id());
            pst.setInt(7,    product.getId());

            pst.executeUpdate();
            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar producto: " + e);
            return false;
        } finally {
            closeResources();
        }
    }

    // =========================
    // DESACTIVAR PRODUCTO (status = 0)
    // =========================
    public boolean deleteProductQuery(int id) {

        String sql = "UPDATE products SET status = 0 WHERE id = ?";

        try {
            conn = cn.getConnection();
            pst  = conn.prepareStatement(sql);
            pst.setInt(1, id);

            pst.executeUpdate();
            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al desactivar producto: " + e);
            return false;
        } finally {
            closeResources();
        }
    }

    // =========================
    // ACTIVAR PRODUCTO (status = 1)
    // =========================
    public boolean activateProductQuery(int id) {

        String sql = "UPDATE products SET status = 1 WHERE id = ?";

        try {
            conn = cn.getConnection();
            pst  = conn.prepareStatement(sql);
            pst.setInt(1, id);

            pst.executeUpdate();
            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al activar producto: " + e);
            return false;
        } finally {
            closeResources();
        }
    }

    // =========================
    // BUSCAR PRODUCTO POR ID
    // =========================
    public Products searchProduct(int id) {

        Products product = new Products();

        String sql = "SELECT * FROM products WHERE id = ?";

        try {
            conn = cn.getConnection();
            pst  = conn.prepareStatement(sql);
            pst.setInt(1, id);

            rs = pst.executeQuery();

            if (rs.next()) {
                product.setId(rs.getInt("id"));
                product.setCode(rs.getInt("code"));
                product.setName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setUnit_price(rs.getDouble("unit_price"));
                product.setProduct_quantity(rs.getInt("product_quantity"));
                product.setCategory_id(rs.getInt("category_id"));
                product.setStatus(rs.getInt("status"));
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar producto: " + e);
        } finally {
            closeResources();
        }

        return product;
    }

    // =========================
    // BUSCAR POR CÓDIGO
    // Solo productos activos (para ventas/compras)
    // =========================
    public Products searchCode(int code) {

        Products product = null;

        String sql = "SELECT * FROM products WHERE code = ? AND status = 1";

        try {
            conn = cn.getConnection();
            pst  = conn.prepareStatement(sql);
            pst.setInt(1, code);

            rs = pst.executeQuery();

            if (rs.next()) {
                product = new Products();
                product.setId(rs.getInt("id"));
                product.setCode(rs.getInt("code"));
                product.setName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setUnit_price(rs.getDouble("unit_price"));
                product.setProduct_quantity(rs.getInt("product_quantity"));
                product.setCategory_id(rs.getInt("category_id"));
                product.setStatus(rs.getInt("status"));
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar por código: " + e);
        } finally {
            closeResources();
        }

        return product;
    }

    // =========================
    // LISTAR PRODUCTOS
    // Muestra TODOS: activos (1) e inactivos (0)
    // Los activos aparecen primero, luego los inactivos
    // =========================
    public List<Products> listProductsQuery(String value) {

        List<Products> list = new ArrayList<>();

        String sql = "SELECT DISTINCT p.id, p.code, p.name, p.description, p.unit_price, "
                   + "p.product_quantity, p.category_id, p.status, c.name AS category_name "
                   + "FROM products p "
                   + "INNER JOIN categories c ON p.category_id = c.id "
                   + "WHERE (CAST(p.code AS CHAR) LIKE ? OR p.name LIKE ?) "
                   + "ORDER BY p.status DESC, p.id ASC";
        //            ↑ sin filtro de status — DESC pone activos (1) primero, inactivos (0) abajo

        try {
            conn = cn.getConnection();
            pst  = conn.prepareStatement(sql);

            pst.setString(1, "%" + value + "%");
            pst.setString(2, "%" + value + "%");

            rs = pst.executeQuery();

            while (rs.next()) {
                Products p = new Products();
                p.setId(rs.getInt("id"));
                p.setCode(rs.getInt("code"));
                p.setName(rs.getString("name"));
                p.setDescription(rs.getString("description"));
                p.setUnit_price(rs.getDouble("unit_price"));
                p.setProduct_quantity(rs.getInt("product_quantity"));
                p.setCategory_id(rs.getInt("category_id"));
                p.setCategory_name(rs.getString("category_name"));
                p.setStatus(rs.getInt("status"));
                list.add(p);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al listar productos: " + e);
        } finally {
            closeResources();
        }

        return list;
    }

    // =========================
    // ACTUALIZAR STOCK (VENTA)
    // Descuenta cantidad
    // =========================
    public boolean updateStockQuery(int amount, int productId) {

        String sql = "UPDATE products SET product_quantity = product_quantity - ? WHERE id = ?";

        try {
            conn = cn.getConnection();
            pst  = conn.prepareStatement(sql);

            pst.setInt(1, amount);
            pst.setInt(2, productId);

            pst.executeUpdate();
            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar stock (venta): " + e);
            return false;
        } finally {
            closeResources();
        }
    }

    // =========================
    // ACTUALIZAR STOCK (COMPRA)
    // Suma cantidad
    // =========================
    public boolean updatePurchaseStockQuery(int amount, int productId) {

        String sql = "UPDATE products SET product_quantity = product_quantity + ? WHERE id = ?";

        try {
            conn = cn.getConnection();
            pst  = conn.prepareStatement(sql);

            pst.setInt(1, amount);
            pst.setInt(2, productId);

            pst.executeUpdate();
            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar stock (compra): " + e);
            return false;
        } finally {
            closeResources();
        }
    }
}