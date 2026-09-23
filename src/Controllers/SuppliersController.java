package Controllers;

import Models.Suppliers;
import Models.SuppliersDao;
import Views.SystemView;

import java.awt.event.*;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class SuppliersController implements ActionListener, MouseListener, KeyListener {

    private Suppliers supplier;
    private SuppliersDao supplierDao;
    private SystemView views;
    DefaultTableModel model;

    public SuppliersController(Suppliers supplier, SuppliersDao supplierDao, SystemView views) {

        this.supplier    = supplier;
        this.supplierDao = supplierDao;
        this.views       = views;

        model = (DefaultTableModel) views.suppliers_table.getModel();

        views.btn_register_supplier.addActionListener(this);
        views.btn_update_supplier.addActionListener(this);
        views.btn_delete_supplier.addActionListener(this);
        views.btn_cancel_supplier.addActionListener(this);
        views.suppliers_table.addMouseListener(this);
        views.txt_search_supplier.addKeyListener(this);

        listAllSuppliers();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == views.btn_register_supplier) registerSupplier();
        if (e.getSource() == views.btn_update_supplier)   updateSupplier();
        if (e.getSource() == views.btn_delete_supplier)   deleteSupplier();
        if (e.getSource() == views.btn_cancel_supplier)   cleanFields();
    }

    // =====================================
    // REGISTRAR
    // =====================================
    private void registerSupplier() {

        if (emptyFields()) {
            JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");
            return;
        }

        supplier.setName(views.txt_supplier_name.getText().trim());
        supplier.setDescription(views.txt_supplier_description.getText().trim());
        supplier.setAddress(views.txt_supplier_addres.getText().trim());  // vista: addres, modelo: address
        supplier.setTelephone(views.txt_supplier_telephone.getText().trim());
        supplier.setEmail(views.txt_supplier_email.getText().trim());
        supplier.setCity(views.cmb_supplier_city.getSelectedItem().toString());

        if (supplierDao.registerSuppliersQuery(supplier)) {
            JOptionPane.showMessageDialog(null, "Proveedor registrado con éxito");
            cleanFields();
            listAllSuppliers();
        } else {
            JOptionPane.showMessageDialog(null, "Error al registrar proveedor");
        }
    }

    // =====================================
    // MODIFICAR
    // =====================================
    private void updateSupplier() {

        if (views.txt_supplier_id.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Selecciona un proveedor");
            return;
        }

        supplier.setId(Integer.parseInt(views.txt_supplier_id.getText().trim()));
        supplier.setName(views.txt_supplier_name.getText().trim());
        supplier.setDescription(views.txt_supplier_description.getText().trim());
        supplier.setAddress(views.txt_supplier_addres.getText().trim());
        supplier.setTelephone(views.txt_supplier_telephone.getText().trim());
        supplier.setEmail(views.txt_supplier_email.getText().trim());
        supplier.setCity(views.cmb_supplier_city.getSelectedItem().toString());

        if (supplierDao.updateSuppliersQuery(supplier)) {
            JOptionPane.showMessageDialog(null, "Proveedor modificado con éxito");
            cleanFields();
            listAllSuppliers();
        } else {
            JOptionPane.showMessageDialog(null, "Error al modificar proveedor");
        }
    }

    // =====================================
    // ELIMINAR
    // =====================================
    private void deleteSupplier() {

        if (views.txt_supplier_id.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Selecciona un proveedor");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(null,
                "¿Seguro que deseas eliminar este proveedor?");

        if (confirm == 0) {
            int id = Integer.parseInt(views.txt_supplier_id.getText().trim());
            if (supplierDao.deleteSuppliersQuery(id)) {
                JOptionPane.showMessageDialog(null, "Proveedor eliminado");
                cleanFields();
                listAllSuppliers();
            } else {
                JOptionPane.showMessageDialog(null, "Error al eliminar proveedor");
            }
        }
    }

    // =====================================
    // LISTAR
    // =====================================
    public void listAllSuppliers() {

        List<Suppliers> list = supplierDao.listSuppliersQuery(
                views.txt_search_supplier.getText().trim()
        );

        model.setRowCount(0);

        for (Suppliers s : list) {
            Object[] row = {
                s.getId(),
                s.getName() != null ? s.getName() : "",
                s.getDescription() != null ? s.getDescription() : "",
                s.getAddress() != null ? s.getAddress() : "",      // modelo: address
                s.getTelephone() != null ? s.getTelephone() : "",
                s.getEmail() != null ? s.getEmail() : "",
                s.getCity() != null ? s.getCity() : ""
            };
            model.addRow(row);
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

        int row = views.suppliers_table.getSelectedRow();

        if (row >= 0) {
            views.txt_supplier_id.setText(getSafeValue(row, 0));
            views.txt_supplier_name.setText(getSafeValue(row, 1));
            views.txt_supplier_description.setText(getSafeValue(row, 2));
            views.txt_supplier_addres.setText(getSafeValue(row, 3));  // vista: addres
            views.txt_supplier_telephone.setText(getSafeValue(row, 4));
            views.txt_supplier_email.setText(getSafeValue(row, 5));
            String city = getSafeValue(row, 6);
            if (!city.isEmpty()) {
                views.cmb_supplier_city.setSelectedItem(city);
            }
        }
    }

    // =====================================
    // BUSCADOR
    // =====================================
    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getSource() == views.txt_search_supplier) {
            listAllSuppliers();
        }
    }

    // =====================================
    // HELPERS
    // =====================================
    private boolean emptyFields() {
        return views.txt_supplier_name.getText().trim().isEmpty()
                || views.txt_supplier_description.getText().trim().isEmpty()
                || views.txt_supplier_addres.getText().trim().isEmpty()
                || views.txt_supplier_telephone.getText().trim().isEmpty()
                || views.txt_supplier_email.getText().trim().isEmpty();
    }

    public void cleanFields() {
        views.txt_supplier_id.setText("");
        views.txt_supplier_name.setText("");
        views.txt_supplier_description.setText("");
        views.txt_supplier_addres.setText("");
        views.txt_supplier_telephone.setText("");
        views.txt_supplier_email.setText("");
        if (views.cmb_supplier_city.getItemCount() > 0)
            views.cmb_supplier_city.setSelectedIndex(0);
    }

    @Override public void mousePressed(MouseEvent e)  {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e)  {}
    @Override public void mouseExited(MouseEvent e)   {}
    @Override public void keyTyped(KeyEvent e)        {}
    @Override public void keyPressed(KeyEvent e)      {}
}