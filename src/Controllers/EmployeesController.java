package Controllers;

import Models.Employees;
import Models.EmployeesDao;
import Views.SystemView;

import java.awt.event.*;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class EmployeesController implements ActionListener, MouseListener, KeyListener {

    private Employees employee;
    private EmployeesDao employeeDao;
    private SystemView views;
    private ReportsController reportsController;
    DefaultTableModel model;

    public EmployeesController(Employees employee, EmployeesDao employeeDao, SystemView views) {

        this.employee    = employee;
        this.employeeDao = employeeDao;
        this.views       = views;

        model = (DefaultTableModel) views.employees_table.getModel();

        views.btn_register_employee.addActionListener(this);
        views.btn_update_employee.addActionListener(this);
        views.btn_delete_employee.addActionListener(this);
        views.btn_cancel_employee.addActionListener(this);
        views.employees_table.addMouseListener(this);
        views.txt_search_employee.addKeyListener(this);

        listAllEmployees();
    }

    public void setReportsController(ReportsController reportsController) {
        this.reportsController = reportsController;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == views.btn_register_employee) registerEmployee();
        if (e.getSource() == views.btn_update_employee)   updateEmployee();
        if (e.getSource() == views.btn_delete_employee)   deleteEmployee();
        if (e.getSource() == views.btn_cancel_employee)   cancelEmployee();
    }

    // =====================================
    // REGISTRAR — encripta contraseña con MD5
    // =====================================
    private void registerEmployee() {

        if (emptyFields()) {
            JOptionPane.showMessageDialog(null,
                    "Todos los campos son obligatorios");
            return;
        }

        String rawPassword = "123456"; // Contraseña predeterminada para nuevos empleados
        if (views.txt_employee_password != null) {
            String p = String.valueOf(views.txt_employee_password.getPassword()).trim();
            if (!p.isEmpty()) rawPassword = p;
        }

        double salary = 0.0;
        try {
            String salText = views.txt_employee_salary.getText().trim().replace(",", ".");
            if (!salText.isEmpty()) {
                salary = Double.parseDouble(salText);
            }
        } catch (NumberFormatException ignored) {}

        employee.setFull_name(views.txt_employee_fullname.getText().trim());
        employee.setUsername(views.txt_employee_username.getText().trim());
        employee.setAddress(views.txt_employee_address.getText().trim());
        employee.setTelephone(views.txt_employee_telephone.getText().trim());
        employee.setEmail(views.txt_employee_email.getText().trim());
        employee.setPassword(rawPassword); // ✅ el DAO la encripta
        employee.setRol(views.cmb_rol.getSelectedItem().toString());
        employee.setSalary(salary);

        if (employeeDao.registerEmployeeQuery(employee)) {
            JOptionPane.showMessageDialog(null,
                    "Empleado registrado con éxito");
            cleanFields();
            listAllEmployees();
            if (reportsController != null) {
                reportsController.loadEmployeeSalaries();
            }
        } else {
            JOptionPane.showMessageDialog(null,
                    "Error al registrar empleado");
        }
    }

    // =====================================
    // MODIFICAR — permite cambiar contraseña opcionalmente
    // =====================================
    private void updateEmployee() {

        if (views.txt_employee_id.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "Selecciona un empleado primero");
            return;
        }

        double salary = 0.0;
        try {
            String salText = views.txt_employee_salary.getText().trim().replace(",", ".");
            if (!salText.isEmpty()) {
                salary = Double.parseDouble(salText);
            }
        } catch (NumberFormatException ignored) {}

        employee.setId(Integer.parseInt(
                views.txt_employee_id.getText().trim()));
        employee.setFull_name(
                views.txt_employee_fullname.getText().trim());
        employee.setUsername(
                views.txt_employee_username.getText().trim());
        employee.setAddress(
                views.txt_employee_address.getText().trim());
        employee.setTelephone(
                views.txt_employee_telephone.getText().trim());
        employee.setEmail(
                views.txt_employee_email.getText().trim());
        employee.setRol(
                views.cmb_rol.getSelectedItem().toString());
        employee.setSalary(salary);

        // ✅ Si escribió nueva contraseña la actualiza, si no la deja igual
        String newPassword = String.valueOf(
                views.txt_employee_password.getPassword()).trim();

        boolean dataOk = employeeDao.updateEmployeeQuery(employee);

        if (dataOk) {
            // ✅ Solo cambia contraseña si escribió algo
            if (!newPassword.isEmpty()) {
                employee.setPassword(newPassword);
                employeeDao.updateEmployeePassword(employee);
                JOptionPane.showMessageDialog(null,
                        "Empleado y contraseña actualizados correctamente");
            } else {
                JOptionPane.showMessageDialog(null,
                        "Empleado actualizado correctamente");
            }
            cleanFields();
            listAllEmployees();
            if (reportsController != null) {
                reportsController.loadEmployeeSalaries();
            }
        } else {
            JOptionPane.showMessageDialog(null,
                    "Error al actualizar empleado");
        }
    }

    // =====================================
    // ELIMINAR
    // =====================================
    private void deleteEmployee() {

        int row = views.employees_table.getSelectedRow();

        if (row == -1 || views.txt_employee_id.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "Selecciona un empleado para eliminarlo");
            return;
        }

        String nombre = getSafeValue(row, 1);

        int confirm = JOptionPane.showConfirmDialog(
                null,
                "¿Estás seguro de que deseas eliminar al empleado \""
                        + nombre + "\"?\nEsta acción no se puede deshacer.",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (confirm != JOptionPane.YES_OPTION) return;

        int id = Integer.parseInt(
                views.txt_employee_id.getText().trim());

        if (employeeDao.deleteEmployeeQuery(id)) {
            JOptionPane.showMessageDialog(null,
                    "Empleado eliminado con éxito");
            cleanFields();
            listAllEmployees();
            if (reportsController != null) {
                reportsController.loadEmployeeSalaries();
            }
        } else {
            JOptionPane.showMessageDialog(null,
                    "Error al eliminar empleado");
        }
    }

    // =====================================
    // CANCELAR
    // =====================================
    private void cancelEmployee() {
        cleanFields();
    }

    // =====================================
    // LISTAR
    // =====================================
    public void listAllEmployees() {

        List<Employees> list = employeeDao.listEmployeesQuery(
                views.txt_search_employee.getText().trim());

        model.setRowCount(0);

        for (Employees emp : list) {
            model.addRow(new Object[]{
                emp.getId(),
                emp.getFull_name() != null ? emp.getFull_name() : "",
                emp.getUsername() != null ? emp.getUsername() : "",
                emp.getAddress() != null ? emp.getAddress() : "",
                emp.getTelephone() != null ? emp.getTelephone() : "",
                emp.getEmail() != null ? emp.getEmail() : "",
                emp.getRol() != null ? emp.getRol() : "",
                String.format(java.util.Locale.US, "%.2f", emp.getSalary())
            });
        }
    }

    private String getSafeValue(int row, int col) {
        if (row < 0 || row >= model.getRowCount() || col < 0 || col >= model.getColumnCount()) {
            return "";
        }
        Object val = model.getValueAt(row, col);
        return val != null ? val.toString().trim() : "";
    }

    // =====================================
    // CLICK TABLA
    // =====================================
    @Override
    public void mouseClicked(MouseEvent e) {

        int row = views.employees_table.getSelectedRow();

        if (row >= 0) {
            views.txt_employee_id.setText(getSafeValue(row, 0));
            views.txt_employee_fullname.setText(getSafeValue(row, 1));
            views.txt_employee_username.setText(getSafeValue(row, 2));
            views.txt_employee_address.setText(getSafeValue(row, 3));
            views.txt_employee_telephone.setText(getSafeValue(row, 4));
            views.txt_employee_email.setText(getSafeValue(row, 5));
            
            String rol = getSafeValue(row, 6);
            if (!rol.isEmpty()) {
                views.cmb_rol.setSelectedItem(rol);
            }

            String salaryStr = getSafeValue(row, 7);
            if (salaryStr.isEmpty()) {
                salaryStr = "0.00";
            }
            views.txt_employee_salary.setText(salaryStr);

            if (views.txt_employee_password != null) {
                views.txt_employee_password.setText("");
            }
            views.txt_employee_id.setEditable(false);
            views.btn_register_employee.setEnabled(false);
        }
    }

    // =====================================
    // BUSCADOR
    // =====================================
    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getSource() == views.txt_search_employee) {
            listAllEmployees();
        }
    }

    // =====================================
    // HELPERS
    // =====================================
    private boolean emptyFields() {
        return views.txt_employee_fullname.getText().trim().isEmpty()
                || views.txt_employee_username.getText().trim().isEmpty()
                || views.txt_employee_address.getText().trim().isEmpty()
                || views.txt_employee_telephone.getText().trim().isEmpty()
                || views.txt_employee_email.getText().trim().isEmpty();
    }

    public void cleanFields() {
        views.txt_employee_id.setText("");
        views.txt_employee_fullname.setText("");
        views.txt_employee_username.setText("");
        views.txt_employee_address.setText("");
        views.txt_employee_telephone.setText("");
        views.txt_employee_email.setText("");
        if (views.txt_employee_password != null) {
            views.txt_employee_password.setText("");
        }
        views.txt_employee_salary.setText("");
        if (views.cmb_rol.getItemCount() > 0)
            views.cmb_rol.setSelectedIndex(0);
        views.txt_employee_id.setEditable(true);
        views.btn_register_employee.setEnabled(true);
    }

    @Override public void mousePressed(MouseEvent e)  {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e)  {}
    @Override public void mouseExited(MouseEvent e)   {}
    @Override public void keyTyped(KeyEvent e)        {}
    @Override public void keyPressed(KeyEvent e)      {}
}