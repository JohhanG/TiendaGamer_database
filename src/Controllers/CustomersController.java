package Controllers;

import Models.Customers;
import Models.CustomersDao;
import Views.SystemView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class CustomersController implements ActionListener, MouseListener, KeyListener {

    private Customers customer;
    private CustomersDao customersDao;
    private SystemView views;
    DefaultTableModel model;

    public CustomersController(Customers customer, CustomersDao customersDao, SystemView views) {

        this.customer     = customer;
        this.customersDao = customersDao;
        this.views        = views;

        model = (DefaultTableModel) views.custormers_table.getModel();

        this.views.btn_register_customer.addActionListener(this);
        this.views.btn_update_customer.addActionListener(this);
        this.views.btn_delete_customer.addActionListener(this);
        this.views.btn_cancel_customer.addActionListener(this);
        this.views.custormers_table.addMouseListener(this);
        this.views.txt_search_customers.addKeyListener(this);

        refreshTable();
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == views.btn_register_customer) {
            registerCustomer();
        } else if (ae.getSource() == views.btn_update_customer) {
            updateCustomer();
        } else if (ae.getSource() == views.btn_delete_customer) {
            deleteCustomer();
        } else if (ae.getSource() == views.btn_cancel_customer) {
            cleanFields();
            views.btn_register_customer.setEnabled(true);
        }
    }

    // ✅ Validar que la cédula sea numérica y no exceda 10 dígitos
    private boolean validarCedula(String cedula) {
        if (!cedula.matches("\\d+")) {
            JOptionPane.showMessageDialog(null,
                    "La identificación solo puede contener números");
            return false;
        }
        if (cedula.length() > 10) {
            JOptionPane.showMessageDialog(null,
                    "La identificación no puede tener más de 10 dígitos");
            return false;
        }
        return true;
    }

    private void registerCustomer() {
        if (emptyFields()) {
            JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");
            return;
        }

        String cedula = views.txt_customer_id.getText().trim();
        if (!validarCedula(cedula)) return; // ✅ Validación

        customer.setId(Integer.parseInt(cedula));
        customer.setFull_name(views.txt_customer_fullname.getText().trim());
        customer.setAddress(views.txt_customer_address.getText().trim());
        customer.setEmail(views.txt_customer_email.getText().trim());
        customer.setTelephone(views.txt_customer_telephone.getText().trim());

        if (customersDao.registerCustomersQuery(customer)) {
            JOptionPane.showMessageDialog(null, "Cliente registrado con éxito");
            refreshTable();
            cleanFields();
        } else {
            JOptionPane.showMessageDialog(null, "Error al registrar cliente");
        }
    }

    private void updateCustomer() {
        if (views.txt_customer_id.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Selecciona una fila para continuar");
            return;
        }
        if (emptyFields()) {
            JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");
            return;
        }

        String cedula = views.txt_customer_id.getText().trim();
        if (!validarCedula(cedula)) return; // ✅ Validación

        customer.setId(Integer.parseInt(cedula));
        customer.setFull_name(views.txt_customer_fullname.getText().trim());
        customer.setAddress(views.txt_customer_address.getText().trim());
        customer.setEmail(views.txt_customer_email.getText().trim());
        customer.setTelephone(views.txt_customer_telephone.getText().trim());

        if (customersDao.updateCustomersQuery(customer)) {
            JOptionPane.showMessageDialog(null, "Cliente modificado con éxito");
            refreshTable();
            cleanFields();
            views.btn_register_customer.setEnabled(true);
            views.txt_customer_id.setEditable(true);
        } else {
            JOptionPane.showMessageDialog(null, "Error al modificar cliente");
        }
    }

    private void deleteCustomer() {
        int row = views.custormers_table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(null,
                    "Selecciona un cliente para eliminarlo");
            return;
        }
        String nombre = getSafeValue(row, 1);
        String idStr = getSafeValue(row, 0);
        if (idStr.isEmpty()) return;
        int id = Integer.parseInt(idStr);

        int confirm = JOptionPane.showConfirmDialog(
                null,
                "¿Estás seguro de que deseas eliminar al cliente \""
                        + nombre + "\"?\nEsta acción no se puede deshacer.",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );
        if (confirm != JOptionPane.YES_OPTION) return;

        if (customersDao.deleteCustomersQuery(id)) {
            JOptionPane.showMessageDialog(null, "Cliente eliminado con éxito");
            refreshTable();
            cleanFields();
            views.btn_register_customer.setEnabled(true);
        } else {
            JOptionPane.showMessageDialog(null, "Error al eliminar cliente");
        }
    }

    public void listAllCustomers() {
        List<Customers> list = customersDao.listCustomersQuery(
                views.txt_search_customers.getText().trim()
        );
        for (Customers c : list) {
            model.addRow(new Object[]{
                c.getId(),
                c.getFull_name() != null ? c.getFull_name() : "",
                c.getTelephone() != null ? c.getTelephone() : "",
                c.getAddress() != null ? c.getAddress() : "",
                c.getEmail() != null ? c.getEmail() : ""
            });
        }
    }

    private String getSafeValue(int row, int col) {
        if (row < 0 || row >= model.getRowCount() || col < 0 || col >= model.getColumnCount()) {
            return "";
        }
        Object val = views.custormers_table.getValueAt(row, col);
        return val != null ? val.toString().trim() : "";
    }

    private void refreshTable() {
        model.setRowCount(0);
        listAllCustomers();
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        if (me.getSource() == views.custormers_table) {
            int row = views.custormers_table.rowAtPoint(me.getPoint());
            if (row >= 0) {
                views.txt_customer_id.setText(getSafeValue(row, 0));
                views.txt_customer_fullname.setText(getSafeValue(row, 1));
                views.txt_customer_telephone.setText(getSafeValue(row, 2));
                views.txt_customer_address.setText(getSafeValue(row, 3));
                views.txt_customer_email.setText(getSafeValue(row, 4));
                views.btn_register_customer.setEnabled(false);
                views.txt_customer_id.setEditable(false);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        if (ke.getSource() == views.txt_search_customers) {
            refreshTable();
        }
    }

    private boolean emptyFields() {
        return views.txt_customer_id.getText().trim().isEmpty()
                || views.txt_customer_fullname.getText().trim().isEmpty()
                || views.txt_customer_address.getText().trim().isEmpty()
                || views.txt_customer_telephone.getText().trim().isEmpty()
                || views.txt_customer_email.getText().trim().isEmpty();
    }

    public void cleanFields() {
        views.txt_customer_id.setText("");
        views.txt_customer_id.setEditable(true);
        views.txt_customer_fullname.setText("");
        views.txt_customer_address.setText("");
        views.txt_customer_telephone.setText("");
        views.txt_customer_email.setText("");
    }

    public void cleanTable() { model.setRowCount(0); }

    @Override public void mousePressed(MouseEvent me)  {}
    @Override public void mouseReleased(MouseEvent me) {}
    @Override public void mouseEntered(MouseEvent me)  {}
    @Override public void mouseExited(MouseEvent me)   {}
    @Override public void keyTyped(KeyEvent ke)        {}
    @Override public void keyPressed(KeyEvent ke)      {}
}