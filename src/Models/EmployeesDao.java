package Models;

import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.swing.JOptionPane;

public class EmployeesDao {

    ConnectionMySQL cn = new ConnectionMySQL();
    Connection conn;
    PreparedStatement pst;
    ResultSet rs;

    public static int id_user = 0;
    public static String full_name_user = "";
    public static String username_user = "";
    public static String address_user = "";
    public static String telephone_user = "";
    public static String email_user = "";
    public static String rol_user = "";

    // ✅ Encriptar con MD5
    public static String encryptMD5(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            JOptionPane.showMessageDialog(null,
                    "Error al encriptar contraseña: " + e);
            return password;
        }
    }

    // ✅ Login con MD5
    public Employees loginQuery(String user, String password) {

        String encryptedPassword = encryptMD5(password); // ✅

        String query = "SELECT * FROM employees WHERE username = ? AND password = ?";
        Employees employee = new Employees();

        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setString(1, user);
            pst.setString(2, encryptedPassword); // ✅

            rs = pst.executeQuery();

            if (rs.next()) {
                employee.setId(rs.getInt("id"));
                id_user = employee.getId();

                employee.setFull_name(rs.getString("full_name"));
                full_name_user = employee.getFull_name();

                employee.setUsername(rs.getString("username"));
                username_user = employee.getUsername();

                employee.setAddress(rs.getString("address"));
                address_user = employee.getAddress();

                employee.setTelephone(rs.getString("telephone"));
                telephone_user = employee.getTelephone();

                employee.setEmail(rs.getString("email"));
                email_user = employee.getEmail();

                employee.setRol(rs.getString("rol"));
                rol_user = employee.getRol();
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "Error al obtener el empleado " + e);
        }

        return employee;
    }

    // ✅ Registrar con MD5 y Sueldo
    public boolean registerEmployeeQuery(Employees employee) {

        String query = "INSERT INTO employees(id, full_name, username, address,"
                + "telephone, email, password, rol, salary, created, updated_at)"
                + "VALUES(?,?,?,?,?,?,?,?,?,?,?)";
        Timestamp dateTime = new Timestamp(new Date().getTime());

        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);

            pst.setInt(1, employee.getId());
            pst.setString(2, employee.getFull_name());
            pst.setString(3, employee.getUsername());
            pst.setString(4, employee.getAddress());
            pst.setString(5, employee.getTelephone());
            pst.setString(6, employee.getEmail());
            pst.setString(7, encryptMD5(employee.getPassword())); // ✅
            pst.setString(8, employee.getRol());
            pst.setDouble(9, employee.getSalary());
            pst.setTimestamp(10, dateTime);
            pst.setTimestamp(11, dateTime);

            pst.execute();
            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "Error al registrar empleado" + e);
            return false;
        }
    }

    // Listar Empleados
    public List<Employees> listEmployeesQuery(String value) {

        List<Employees> list_employees = new ArrayList<>();
        String query = "SELECT * FROM employees ORDER BY rol ASC";
        String query_search = "SELECT * FROM employees WHERE id LIKE '%"
                + value + "%'";

        try {
            conn = cn.getConnection();

            if (value.equalsIgnoreCase("")) {
                pst = conn.prepareStatement(query);
            } else {
                pst = conn.prepareStatement(query_search);
            }

            rs = pst.executeQuery();

            while (rs.next()) {
                Employees emp = new Employees();
                emp.setId(rs.getInt("id"));
                emp.setFull_name(rs.getString("full_name"));
                emp.setUsername(rs.getString("username"));
                emp.setAddress(rs.getString("address"));
                emp.setTelephone(rs.getString("telephone"));
                emp.setEmail(rs.getString("email"));
                emp.setRol(rs.getString("rol"));
                emp.setSalary(rs.getDouble("salary"));
                list_employees.add(emp);
            }

            if (list_employees.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Empleado no encontrado");
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }

        return list_employees;
    }

    // Modificar Empleado
    public boolean updateEmployeeQuery(Employees employee) {

        String query = "UPDATE employees SET full_name = ?, username = ?, "
                + "address = ?, telephone = ?, email = ?, rol = ?, salary = ?, updated_at = ? "
                + "WHERE id = ?";
        Timestamp dateTime = new Timestamp(new Date().getTime());

        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setString(1, employee.getFull_name());
            pst.setString(2, employee.getUsername());
            pst.setString(3, employee.getAddress());
            pst.setString(4, employee.getTelephone());
            pst.setString(5, employee.getEmail());
            pst.setString(6, employee.getRol());
            pst.setDouble(7, employee.getSalary());
            pst.setTimestamp(8, dateTime);
            pst.setInt(9, employee.getId());

            pst.execute();
            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "Error al modificar el empleado:" + e);
            return false;
        }
    }

    // Modificar exclusivamente el Sueldo (Solo Administrador)
    public boolean updateSalary(int id, double salary) {
        String query = "UPDATE employees SET salary = ?, updated_at = ? WHERE id = ?";
        Timestamp dateTime = new Timestamp(new Date().getTime());
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setDouble(1, salary);
            pst.setTimestamp(2, dateTime);
            pst.setInt(3, id);
            int rows = pst.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "Error al modificar el sueldo del empleado: " + e.getMessage());
            return false;
        } finally {
            try { if (pst != null) pst.close(); } catch (SQLException ignored) {}
            try { if (conn != null) conn.close(); } catch (SQLException ignored) {}
        }
    }

    // Total nómina de sueldos
    public double getTotalSalariesQuery() {
        double total = 0.0;
        String query = "SELECT SUM(salary) FROM employees";
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            rs = pst.executeQuery();
            if (rs.next()) {
                total = rs.getDouble(1);
            }
        } catch (SQLException e) {
            System.err.println("Error al calcular total sueldos: " + e.getMessage());
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException ignored) {}
            try { if (pst != null) pst.close(); } catch (SQLException ignored) {}
            try { if (conn != null) conn.close(); } catch (SQLException ignored) {}
        }
        return total;
    }

    // Eliminar Empleado
    public boolean deleteEmployeeQuery(int id) {
        String query = "DELETE FROM employees WHERE id = " + id;
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "No se puede eliminar un empleado que tenga relacion "
                    + "con otra tabla" + e);
            return false;
        }
    }

    // ✅ Cambiar Password con MD5
    public boolean updateEmployeePassword(Employees employee) {
        String query = "UPDATE employees SET password = ? WHERE username = '"
                + username_user + "'";
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setString(1, encryptMD5(employee.getPassword())); // ✅
            pst.executeUpdate();
            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "Ha ocurrido un error al modificar el password" + e);
            return false;
        }
    }
    // ✅ Verificar si cuenta está bloqueada
public boolean isAccountLocked(String username) {
    String query = "SELECT is_locked FROM employees WHERE username = ?";
    try {
        conn = cn.getConnection();
        pst = conn.prepareStatement(query);
        pst.setString(1, username);
        rs = pst.executeQuery();
        if (rs.next()) {
            return rs.getInt("is_locked") == 1;
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error: " + e);
    }
    return false;
}

// ✅ Incrementar intentos fallidos
public void incrementFailedAttempts(String username) {
    String query = "UPDATE employees SET failed_attempts = failed_attempts + 1 "
            + "WHERE username = ?";
    try {
        conn = cn.getConnection();
        pst = conn.prepareStatement(query);
        pst.setString(1, username);
        pst.executeUpdate();
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error: " + e);
    }
}

// ✅ Bloquear cuenta si llega a 3 intentos
public void checkAndLockAccount(String username) {
    String query = "SELECT failed_attempts FROM employees WHERE username = ?";
    try {
        conn = cn.getConnection();
        pst = conn.prepareStatement(query);
        pst.setString(1, username);
        rs = pst.executeQuery();
        if (rs.next()) {
            int attempts = rs.getInt("failed_attempts");
            if (attempts >= 3) {
                String lockQuery = "UPDATE employees SET is_locked = 1 WHERE username = ?";
                pst = conn.prepareStatement(lockQuery);
                pst.setString(1, username);
                pst.executeUpdate();
            }
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error: " + e);
    }
}

// ✅ Resetear intentos fallidos al login exitoso
public void resetFailedAttempts(String username) {
    String query = "UPDATE employees SET failed_attempts = 0 WHERE username = ?";
    try {
        conn = cn.getConnection();
        pst = conn.prepareStatement(query);
        pst.setString(1, username);
        pst.executeUpdate();
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error: " + e);
    }
}

// ✅ Registrar actividad
public void logActivity(int employeeId, String action) {
    String query = "INSERT INTO activity_log(employee_id, action) VALUES(?, ?)";
    try {
        conn = cn.getConnection();
        pst = conn.prepareStatement(query);
        pst.setInt(1, employeeId);
        pst.setString(2, action);
        pst.executeUpdate();
    } catch (SQLException e) {
        System.out.println("Error al registrar actividad: " + e.getMessage());
    }
}
}