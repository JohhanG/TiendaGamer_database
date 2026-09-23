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
        String checkSql = "SELECT COUNT(*) FROM tipos_movimiento";
        String insertSql = "INSERT INTO tipos_movimiento (idTipoMov, nombre, efecto) VALUES "
                + "(1, 'VENTA', 'SALIDA'), "
                + "(2, 'COMPRA', 'ENTRADA'), "
                + "(3, 'DEVOLUCION CLIENTE', 'ENTRADA'), "
                + "(4, 'DEVOLUCION PROVEEDOR', 'SALIDA') "
                + "ON DUPLICATE KEY UPDATE nombre=VALUES(nombre), efecto=VALUES(efecto)";
        try (java.sql.Statement st = con.createStatement();
             ResultSet rsCheck = st.executeQuery(checkSql)) {
            if (rsCheck.next() && rsCheck.getInt(1) == 0) {
                st.executeUpdate(insertSql);
            }
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
}