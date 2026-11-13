package Models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class KardexDao {
    
    ConnectionMySQL cn = new ConnectionMySQL();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    // Asegurar que existan los tipos de movimiento en la base de datos
    private void asegurarTiposMovimiento(Connection con) {
        String insertSql = "INSERT INTO tipos_movimiento (idTipoMov, nombre, efecto) VALUES "
                + "(1, 'VENTA', 'SALIDA'), "
                + "(2, 'COMPRA', 'ENTRADA'), "
                + "(3, 'DEVOLUCION CLIENTE', 'ENTRADA'), "
                + "(4, 'DEVOLUCION PROVEEDOR', 'SALIDA'), "
                + "(5, 'AJUSTE INVENTARIO', 'AJUSTE') "
                + "ON DUPLICATE KEY UPDATE nombre=VALUES(nombre), efecto=VALUES(efecto)";
        try (java.sql.Statement st = con.createStatement()) {
            st.executeUpdate(insertSql);
        } catch (SQLException ignored) {}
    }

    // Registrar un nuevo movimiento en el Kardex
    public boolean registrarMovimiento(Kardex k) {
        String sql = "INSERT INTO kardex (idProducto, idTipoMov, idEmpleado, cantidad, efecto, saldoAnterior, saldoResultante, observacion) VALUES (?,?,?,?,?,?,?,?)";
        try {
            con = cn.getConnection();
            asegurarTiposMovimiento(con);

            // Validar empleado para no violar la clave foránea kardex_ibfk_3
            int empId = k.getIdEmpleado();
            if (empId <= 0) {
                empId = EmployeesDao.id_user;
            }
            if (empId <= 0) {
                try (java.sql.Statement st = con.createStatement();
                     ResultSet rsEmp = st.executeQuery("SELECT id FROM employees LIMIT 1")) {
                    if (rsEmp.next()) {
                        empId = rsEmp.getInt("id");
                    }
                }
            }

            // Validar tipo de movimiento para no violar la clave foránea kardex_ibfk_2
            int tipoMov = k.getIdTipoMov();
            if (tipoMov <= 0) {
                tipoMov = ("SALIDA".equalsIgnoreCase(k.getEfecto())) ? 1 : 3;
            }

            ps = con.prepareStatement(sql);
            ps.setInt(1, k.getIdProducto());
            ps.setInt(2, tipoMov);
            ps.setInt(3, empId);
            ps.setInt(4, k.getCantidad());
            ps.setString(5, k.getEfecto());
            ps.setInt(6, k.getSaldoAnterior());
            ps.setInt(7, k.getSaldoResultante());
            ps.setString(8, k.getObservacion());
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al registrar en Kardex: " + e.toString());
            return false;
        } finally {
            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
    }

    // Consultar el historial de movimientos de un producto por su ID
    public List<Kardex> listarKardexPorProducto(int idProducto) {
        List<Kardex> lista = new ArrayList<>();
        String sql = "SELECT * FROM kardex WHERE idProducto = ? ORDER BY fecha DESC";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, idProducto);
            rs = ps.executeQuery();
            while (rs.next()) {
                Kardex k = new Kardex();
                k.setIdKardex(rs.getInt("idKardex"));
                k.setIdProducto(rs.getInt("idProducto"));
                k.setIdTipoMov(rs.getInt("idTipoMov"));
                k.setIdEmpleado(rs.getInt("idEmpleado"));
                k.setCantidad(rs.getInt("cantidad"));
                k.setEfecto(rs.getString("efecto"));
                k.setSaldoAnterior(rs.getInt("saldoAnterior"));
                k.setSaldoResultante(rs.getInt("saldoResultante"));
                k.setFecha(rs.getTimestamp("fecha"));
                k.setObservacion(rs.getString("observacion"));
                lista.add(k);
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar Kardex: " + e.toString());
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
        return lista;
    }

    // Listar todos los movimientos con datos enriquecidos de producto, tipo y empleado
    public List<Kardex> listarTodoElKardex(String search, String tipoFiltro) {
        List<Kardex> lista = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT k.idKardex, k.idProducto, k.idTipoMov, k.idEmpleado, k.cantidad, k.efecto, ")
           .append("k.saldoAnterior, k.saldoResultante, k.fecha, k.observacion, ")
           .append("p.code AS prod_code, p.name AS prod_name, tm.nombre AS tipo_nombre, e.full_name AS emp_nombre ")
           .append("FROM kardex k ")
           .append("LEFT JOIN products p ON k.idProducto = p.id ")
           .append("LEFT JOIN tipos_movimiento tm ON k.idTipoMov = tm.idTipoMov ")
           .append("LEFT JOIN employees e ON k.idEmpleado = e.id ")
           .append("WHERE 1=1 ");

        boolean hasSearch = (search != null && !search.trim().isEmpty());
        if (hasSearch) {
            sql.append("AND (p.name LIKE ? OR CAST(p.code AS CHAR) LIKE ? OR k.observacion LIKE ?) ");
        }

        boolean hasFilter = (tipoFiltro != null && !tipoFiltro.trim().isEmpty() && !"Todos".equalsIgnoreCase(tipoFiltro));
        if (hasFilter) {
            sql.append("AND tm.nombre LIKE ? ");
        }

        sql.append("ORDER BY k.fecha DESC, k.idKardex DESC");

        try {
            con = cn.getConnection();
            asegurarTiposMovimiento(con);
            ps = con.prepareStatement(sql.toString());
            int paramIdx = 1;
            if (hasSearch) {
                String term = "%" + search.trim() + "%";
                ps.setString(paramIdx++, term);
                ps.setString(paramIdx++, term);
                ps.setString(paramIdx++, term);
            }
            if (hasFilter) {
                ps.setString(paramIdx++, "%" + tipoFiltro.trim() + "%");
            }

            rs = ps.executeQuery();
            while (rs.next()) {
                Kardex k = new Kardex();
                k.setIdKardex(rs.getInt("idKardex"));
                k.setIdProducto(rs.getInt("idProducto"));
                k.setIdTipoMov(rs.getInt("idTipoMov"));
                k.setIdEmpleado(rs.getInt("idEmpleado"));
                k.setCantidad(rs.getInt("cantidad"));
                k.setEfecto(rs.getString("efecto"));
                k.setSaldoAnterior(rs.getInt("saldoAnterior"));
                k.setSaldoResultante(rs.getInt("saldoResultante"));
                k.setFecha(rs.getTimestamp("fecha"));
                k.setObservacion(rs.getString("observacion"));

                k.setProductCode(rs.getInt("prod_code"));
                k.setProductName(rs.getString("prod_name") != null ? rs.getString("prod_name") : "Producto #" + k.getIdProducto());
                k.setTipoMovNombre(rs.getString("tipo_nombre") != null ? rs.getString("tipo_nombre") : "MOVIMIENTO #" + k.getIdTipoMov());
                k.setEmployeeName(rs.getString("emp_nombre") != null ? rs.getString("emp_nombre") : "Empleado #" + k.getIdEmpleado());

                lista.add(k);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar todo el Kardex: " + e.toString());
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
        return lista;
    }

    // Registrar un ajuste por inventario físico (+/-) actualizando stock y kardex bajo Transacción ACID (RNF-003)
    public boolean registrarAjusteInventario(int idProducto, int idEmpleado, int nuevoStock, String motivo) {
        String queryStock = "SELECT product_quantity FROM products WHERE id = ? FOR UPDATE";
        String updateStock = "UPDATE products SET product_quantity = ? WHERE id = ?";
        String insertKardex = "INSERT INTO kardex (idProducto, idTipoMov, idEmpleado, cantidad, efecto, saldoAnterior, saldoResultante, observacion) VALUES (?,?,?,?,?,?,?,?)";
        try {
            con = cn.getConnection();
            if (con == null) return false;
            asegurarTiposMovimiento(con);

            // RNF-003: INICIO DE TRANSACCIÓN ACID
            con.setAutoCommit(false);

            int stockAnterior = 0;
            try (PreparedStatement psQ = con.prepareStatement(queryStock)) {
                psQ.setInt(1, idProducto);
                try (ResultSet rsQ = psQ.executeQuery()) {
                    if (rsQ.next()) {
                        stockAnterior = rsQ.getInt("product_quantity");
                    }
                }
            }

            int diferencia = nuevoStock - stockAnterior;
            int cantidad = Math.abs(diferencia);
            String efecto = (diferencia >= 0) ? "ENTRADA" : "SALIDA";

            // 1. Actualizar el stock en la tabla products
            try (PreparedStatement psU = con.prepareStatement(updateStock)) {
                psU.setInt(1, nuevoStock);
                psU.setInt(2, idProducto);
                psU.executeUpdate();
            }

            // 2. Registrar el movimiento en Kardex con tipo 5 (AJUSTE INVENTARIO) en la misma conexión
            int empId = idEmpleado > 0 ? idEmpleado : EmployeesDao.id_user;
            if (empId <= 0) empId = 1;

            try (PreparedStatement psK = con.prepareStatement(insertKardex)) {
                psK.setInt(1, idProducto);
                psK.setInt(2, 5); // 5: AJUSTE INVENTARIO
                psK.setInt(3, empId);
                psK.setInt(4, cantidad);
                psK.setString(5, efecto);
                psK.setInt(6, stockAnterior);
                psK.setInt(7, nuevoStock);
                psK.setString(8, motivo != null && !motivo.trim().isEmpty() ? motivo.trim() : "Ajuste por inventario físico");
                psK.executeUpdate();
            }

            // CONFIRMACIÓN TRANSACCIONAL
            con.commit();
            return true;

        } catch (SQLException e) {
            // ROLLBACK EN CASO DE FALLO (RNF-003)
            try {
                if (con != null) {
                    con.rollback();
                    System.err.println("¡ROLLBACK ejecutado en ajuste de inventario!: " + e.getMessage());
                }
            } catch (SQLException ignored) {}
            System.out.println("Error transaccional al registrar ajuste en Kardex: " + e.getMessage());
            return false;
        } finally {
            try {
                if (con != null) {
                    con.setAutoCommit(true);
                    con.close();
                }
            } catch (SQLException ignored) {}
        }
    }
}