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

    private void registerCustomer() {
        if (emptyFields()) {
            JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");
            return;
        }
        customer.setId(Integer.parseInt(views.txt_customer_id.getText().trim()));
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
        customer.setId(Integer.parseInt(views.txt_customer_id.getText().trim()));
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
            JOptionPane.showMessageDialog(null, "Selecciona un cliente para eliminarlo");
            return;
        }
        String nombre = views.custormers_table.getValueAt(row, 1).toString();
        int id = Integer.parseInt(views.custormers_table.getValueAt(row, 0).toString());

        int confirm = JOptionPane.showConfirmDialog(
                null,
                "¿Estás seguro de que deseas eliminar al cliente \"" + nombre + "\"?\nEsta acción no se puede deshacer.",
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

    // Orden columnas tabla: Identificacion | Nombre | Telefono | Direccion | Correo
    public void listAllCustomers() {
        List<Customers> list = customersDao.listCustomersQuery(
                views.txt_search_customers.getText().trim()
        );
        for (Customers c : list) {
            Object[] row = {
                c.getId(),           // col 0: Identificacion
                c.getFull_name(),    // col 1: Nombre
                c.getTelephone(),    // col 2: Telefono
                c.getAddress(),      // col 3: Direccion
                c.getEmail()         // col 4: Correo
            };
            model.addRow(row);
        }
    }

    private void refreshTable() {
        model.setRowCount(0);
        listAllCustomers();
    }

    // col0=Identificacion, col1=Nombre, col2=Telefono, col3=Direccion, col4=Correo
    @Override
    public void mouseClicked(MouseEvent me) {
        if (me.getSource() == views.custormers_table) {
            int row = views.custormers_table.rowAtPoint(me.getPoint());
            if (row >= 0) {
                views.txt_customer_id.setText(views.custormers_table.getValueAt(row, 0).toString());
                views.txt_customer_fullname.setText(views.custormers_table.getValueAt(row, 1).toString());
                views.txt_customer_telephone.setText(views.custormers_table.getValueAt(row, 2).toString());
                views.txt_customer_address.setText(views.custormers_table.getValueAt(row, 3).toString());
                views.txt_customer_email.setText(views.custormers_table.getValueAt(row, 4).toString());
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