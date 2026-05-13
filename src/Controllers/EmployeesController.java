package Controllers;

import Models.Employees;
import Models.EmployeesDao;
import static Models.EmployeesDao.rol_user;
import Views.SystemView;

import java.awt.event.*;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class EmployeesController implements ActionListener, MouseListener, KeyListener {

    private Employees employee;
    private EmployeesDao employeeDao;
    private SystemView views;
    DefaultTableModel model;

    public EmployeesController(Employees employee, EmployeesDao employeeDao, SystemView views) {

        this.employee    = employee;
        this.employeeDao = employeeDao;
        this.views       = views;

        model = (DefaultTableModel) views.employees_table.getModel();

        views.btn_register_employee.addActionListener(this);
        views.btn_update_employee.addActionListener(this);
        views.btn_delete_employee.addActionListener(this);   // NUEVO
        views.btn_cancel_employee.addActionListener(this);   // NUEVO
        views.employees_table.addMouseListener(this);
        views.txt_search_employee.addKeyListener(this);

        listAllEmployees();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == views.btn_register_employee) registerEmployee();
        if (e.getSource() == views.btn_update_employee)   updateEmployee();
        if (e.getSource() == views.btn_delete_employee)   deleteEmployee();   // NUEVO
        if (e.getSource() == views.btn_cancel_employee)   cancelEmployee();   // NUEVO
    }

    // =====================================
    // REGISTRAR
    // =====================================
    private void registerEmployee() {

        if (emptyFields()) {
            JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");
            return;
        }

        employee.setFull_name(views.txt_employee_fullname.getText().trim());
        employee.setUsername(views.txt_employee_username.getText().trim());
        employee.setAddress(views.txt_employee_address.getText().trim());
        employee.setTelephone(views.txt_employee_telephone.getText().trim());
        employee.setEmail(views.txt_employee_email.getText().trim());
        employee.setPassword(String.valueOf(views.txt_employee_password.getPassword()));
        employee.setRol(views.cmb_rol.getSelectedItem().toString());

        if (employeeDao.registerEmployeeQuery(employee)) {
            JOptionPane.showMessageDialog(null, "Empleado registrado con éxito");
            cleanFields();
            listAllEmployees();
        } else {
            JOptionPane.showMessageDialog(null, "Error al registrar empleado");
        }
    }

    // =====================================
    // MODIFICAR
    // =====================================
    private void updateEmployee() {

        if (views.txt_employee_id.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Selecciona un empleado primero");
            return;
        }

        employee.setId(Integer.parseInt(views.txt_employee_id.getText().trim()));
        employee.setFull_name(views.txt_employee_fullname.getText().trim());
        employee.setUsername(views.txt_employee_username.getText().trim());
        employee.setAddress(views.txt_employee_address.getText().trim());
        employee.setTelephone(views.txt_employee_telephone.getText().trim());
        employee.setEmail(views.txt_employee_email.getText().trim());
        employee.setRol(views.cmb_rol.getSelectedItem().toString());

        if (employeeDao.updateEmployeeQuery(employee)) {
            JOptionPane.showMessageDialog(null, "Empleado actualizado correctamente");
            cleanFields();
            listAllEmployees();
        } else {
            JOptionPane.showMessageDialog(null, "Error al actualizar empleado");
        }
    }

    // =====================================
    // ELIMINAR — con confirmación
    // =====================================
    private void deleteEmployee() {

        int row = views.employees_table.getSelectedRow();

        if (row == -1 || views.txt_employee_id.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Selecciona un empleado para eliminarlo");
            return;
        }

        String nombre = model.getValueAt(row, 1).toString();

        int confirm = JOptionPane.showConfirmDialog(
                null,
                "¿Estás seguro de que deseas eliminar al empleado \"" + nombre + "\"?\n"
                + "Esta acción no se puede deshacer.",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        int id = Integer.parseInt(views.txt_employee_id.getText().trim());

        if (employeeDao.deleteEmployeeQuery(id)) {
            JOptionPane.showMessageDialog(null, "Empleado eliminado con éxito");
            cleanFields();
            listAllEmployees();
        } else {
            JOptionPane.showMessageDialog(null, "Error al eliminar empleado");
        }
    }

    // =====================================
    // CANCELAR — limpia campos y restaura estado
    // =====================================
    private void cancelEmployee() {
        cleanFields();
    }

    // =====================================
    // LISTAR
    // =====================================
    public void listAllEmployees() {

        List<Employees> list = employeeDao.listEmployeesQuery(
                views.txt_search_employee.getText().trim()
        );

        model.setRowCount(0);

        for (Employees emp : list) {
            Object[] row = {
                emp.getId(),
                emp.getFull_name(),
                emp.getUsername(),
                emp.getAddress(),
                emp.getTelephone(),
                emp.getEmail(),
                emp.getRol()
            };
            model.addRow(row);
        }
    }

    // =====================================
    // CLICK TABLA
    // =====================================
    @Override
    public void mouseClicked(MouseEvent e) {

        int row = views.employees_table.getSelectedRow();

        if (row >= 0) {
            views.txt_employee_id.setText(model.getValueAt(row, 0).toString());
            views.txt_employee_fullname.setText(model.getValueAt(row, 1).toString());
            views.txt_employee_username.setText(model.getValueAt(row, 2).toString());
            views.txt_employee_address.setText(model.getValueAt(row, 3).toString());
            views.txt_employee_telephone.setText(model.getValueAt(row, 4).toString());
            views.txt_employee_email.setText(model.getValueAt(row, 5).toString());
            views.cmb_rol.setSelectedItem(model.getValueAt(row, 6).toString());

            views.txt_employee_id.setEditable(false);
            views.txt_employee_password.setEnabled(false); // no editar contraseña al modificar
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
                || views.txt_employee_email.getText().trim().isEmpty()
                || views.txt_employee_password.getPassword().length == 0;
    }

    public void cleanFields() {
        views.txt_employee_id.setText("");
        views.txt_employee_fullname.setText("");
        views.txt_employee_username.setText("");
        views.txt_employee_address.setText("");
        views.txt_employee_telephone.setText("");
        views.txt_employee_email.setText("");
        views.txt_employee_password.setText("");
        if (views.cmb_rol.getItemCount() > 0) views.cmb_rol.setSelectedIndex(0);
        views.txt_employee_id.setEditable(true);
        views.txt_employee_password.setEnabled(true);
        views.btn_register_employee.setEnabled(true);
    }

    @Override public void mousePressed(MouseEvent e)  {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e)  {}
    @Override public void mouseExited(MouseEvent e)   {}
    @Override public void keyTyped(KeyEvent e)        {}
    @Override public void keyPressed(KeyEvent e)      {}
}