package Controllers;

import Models.DynamicComboBox;
import static Models.EmployeesDao.id_user;
import Models.Products;
import Models.ProductsDao;
import Models.Purchases;
import Models.PurchasesDao;
import Views.SystemView;

import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class PurchasesController implements ActionListener,
        MouseListener,
        KeyListener {

    private Purchases purchase;
    private PurchasesDao purchaseDao;
    private SystemView views;

    private Products product;
    private ProductsDao productsDao;

    private int getIdSupplier = 0;

    DefaultTableModel model;

    public PurchasesController(
            Purchases purchase,
            PurchasesDao purchaseDao,
            SystemView views) {

        this.purchase    = purchase;
        this.purchaseDao = purchaseDao;
        this.views       = views;

        this.product     = new Products();
        this.productsDao = new ProductsDao();

        model = (DefaultTableModel) views.purchases_table.getModel();

        // Eventos teclado
        views.txt_purchase_product_code.addKeyListener(this);
        views.txt_purchase_amount.addKeyListener(this);
        views.txt_purchase_price.addKeyListener(this);

        // Botones
        views.btn_add_product_to_buy.addActionListener(this);
        views.btn_confirm_purchase.addActionListener(this);
        views.btn_remove_purchase.addActionListener(this);
        views.btn_new_purchase.addActionListener(this);   // botón Nuevo

        views.purchases_table.addMouseListener(this);
    }

    // =========================================
    // BOTONES
    // =========================================
    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == views.btn_add_product_to_buy) {
            addProductToTable();
        }
        if (e.getSource() == views.btn_remove_purchase) {
            removeProductFromTable();
        }
        if (e.getSource() == views.btn_confirm_purchase) {
            insertPurchase();
        }
        if (e.getSource() == views.btn_new_purchase) {
            newPurchase();
        }
    }

    // =========================================
    // BUSCAR PRODUCTO POR CÓDIGO — al presionar ENTER
    // Llena: nombre, ID, precio de compra (unit_price)
    // El precio es editable por si el proveedor cambia el valor
    // =========================================
    @Override
    public void keyPressed(KeyEvent e) {

        if (e.getSource() == views.txt_purchase_product_code
                && e.getKeyCode() == KeyEvent.VK_ENTER) {

            String codeText = views.txt_purchase_product_code.getText().trim();
            if (codeText.isEmpty()) return;

            int code = parseIntSafe(codeText);
            product = productsDao.searchCode(code);

            if (product != null) {

                views.txt_purchase_product_name.setText(product.getName());
                views.txt_purchase_id.setText(String.valueOf(product.getId()));

                // AUTO-LLENADO del precio de compra (unit_price del producto)
                views.txt_purchase_price.setText(
                        String.format("%.2f", product.getUnit_price())
                );

                // Enfocar cantidad para agilizar ingreso
                views.txt_purchase_amount.requestFocus();

            } else {
                JOptionPane.showMessageDialog(
                        null,
                        "Producto no encontrado o inactivo"
                );
                cleanFieldsPurchases();
            }
        }
    }

    // =========================================
    // CALCULAR SUBTOTAL — al escribir cantidad o cambiar precio
    // =========================================
    @Override
    public void keyReleased(KeyEvent e) {

        if (e.getSource() == views.txt_purchase_amount
                || e.getSource() == views.txt_purchase_price) {

            int amount = parseIntSafe(views.txt_purchase_amount.getText());
            double price = parseDoubleSafe(views.txt_purchase_price.getText());

            double subtotal = amount * price;

            views.txt_purchase_subtotal.setText(
                    String.format("%.2f", subtotal)
            );
        }
    }

    // =========================================
    // AGREGAR PRODUCTO A LA TABLA
    // =========================================
    private void addProductToTable() {

        if (views.txt_purchase_id.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Busca un producto primero (ingresa el código y presiona Enter)");
            return;
        }

        int productId = parseIntSafe(views.txt_purchase_id.getText());
        int amount    = parseIntSafe(views.txt_purchase_amount.getText());
        double price  = parseDoubleSafe(views.txt_purchase_price.getText());

        if (amount <= 0) {
            JOptionPane.showMessageDialog(null, "La cantidad debe ser mayor a 0");
            return;
        }

        if (price <= 0) {
            JOptionPane.showMessageDialog(null, "El precio no puede ser 0");
            return;
        }

        double subtotal = amount * price;

        DynamicComboBox supplier =
                (DynamicComboBox) views.cmb_purchase_supplier.getSelectedItem();

        if (supplier == null) {
            JOptionPane.showMessageDialog(null, "Selecciona un proveedor");
            return;
        }

        // Bloquear cambio de proveedor dentro de la misma compra
        if (getIdSupplier == 0) {
            getIdSupplier = supplier.getId();
            views.cmb_purchase_supplier.setEnabled(false);
        } else if (getIdSupplier != supplier.getId()) {
            JOptionPane.showMessageDialog(null,
                    "No puedes mezclar proveedores en la misma compra.");
            return;
        }

        // Verificar si el producto ya fue agregado
        for (int i = 0; i < model.getRowCount(); i++) {
            int idTable = Integer.parseInt(model.getValueAt(i, 0).toString());
            if (idTable == productId) {
                JOptionPane.showMessageDialog(null,
                        "Este producto ya fue agregado a la lista.");
                return;
            }
        }

        ArrayList<Object> row = new ArrayList<>();
        row.add(productId);
        row.add(views.txt_purchase_product_name.getText());
        row.add(amount);
        row.add(String.format("%.2f", price));
        row.add(String.format("%.2f", subtotal));
        row.add(supplier.getName());

        model.addRow(row.toArray());

        calculateTotal();
        cleanFieldsPurchases();

        // Enfocar código para ingresar siguiente producto rápido
        views.txt_purchase_product_code.requestFocus();
    }

    // =========================================
    // ELIMINAR FILA DE LA TABLA
    // =========================================
    private void removeProductFromTable() {

        int row = views.purchases_table.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(null, "Selecciona una fila para eliminar");
            return;
        }

        model.removeRow(row);
        calculateTotal();

        // Si la tabla quedó vacía, liberar proveedor
        if (model.getRowCount() == 0) {
            getIdSupplier = 0;
            views.cmb_purchase_supplier.setEnabled(true);
            views.txt_purchase_total_to_pay.setText("");
        }
    }

    // =========================================
    // CONFIRMAR COMPRA
    // =========================================
    private void insertPurchase() {

        if (model.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "No hay productos en la lista");
            return;
        }

        double total = parseDoubleSafe(views.txt_purchase_total_to_pay.getText());

        int confirm = JOptionPane.showConfirmDialog(
                null,
                "¿Confirmar compra por un total de $" + String.format("%.2f", total) + "?",
                "Confirmar compra",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (confirm != JOptionPane.YES_OPTION) return;

        boolean ok = purchaseDao.registerPurchaseQuery(
                getIdSupplier,
                id_user,
                total
        );

        if (ok) {

            int purchaseId = purchaseDao.purchaseId();

            for (int i = 0; i < model.getRowCount(); i++) {

                int productId    = Integer.parseInt(model.getValueAt(i, 0).toString());
                int amount       = Integer.parseInt(model.getValueAt(i, 2).toString());
                double unitPrice = parseDoubleSafe(model.getValueAt(i, 3).toString());
                double subtotal  = parseDoubleSafe(model.getValueAt(i, 4).toString());

                purchaseDao.registerPurchaseDetailQuery(
                        purchaseId,
                        productId,
                        amount,
                        unitPrice,
                        subtotal
                );

                productsDao.updatePurchaseStockQuery(amount, productId);
            }

            cleanTableTemp();
            cleanFieldsPurchases();

            views.txt_purchase_total_to_pay.setText("");
            views.cmb_purchase_supplier.setEnabled(true);
            getIdSupplier = 0;

            JOptionPane.showMessageDialog(null, "¡Compra registrada correctamente!");
        }
    }

    // =========================================
    // NUEVA COMPRA — limpia todo
    // =========================================
    private void newPurchase() {
        cleanTableTemp();
        cleanFieldsPurchases();
        views.txt_purchase_total_to_pay.setText("");
        views.cmb_purchase_supplier.setEnabled(true);
        getIdSupplier = 0;
    }

    // =========================================
    // CALCULAR TOTAL
    // =========================================
    private void calculateTotal() {

        double total = 0;

        for (int i = 0; i < model.getRowCount(); i++) {
            total += parseDoubleSafe(model.getValueAt(i, 4).toString());
        }

        views.txt_purchase_total_to_pay.setText(String.format("%.2f", total));
    }

    // =========================================
    // CLICK TABLA — seleccionar fila
    // =========================================
    @Override
    public void mouseClicked(MouseEvent e) {
        // Solo selección visual; no llena campos de búsqueda
    }

    // =========================================
    // LIMPIAR CAMPOS DEL FORMULARIO
    // =========================================
    private void cleanFieldsPurchases() {
        views.txt_purchase_product_code.setText("");
        views.txt_purchase_product_name.setText("");
        views.txt_purchase_id.setText("");
        views.txt_purchase_price.setText("");
        views.txt_purchase_amount.setText("");
        views.txt_purchase_subtotal.setText("");
    }

    private void cleanTableTemp() {
        model.setRowCount(0);
    }

    // =========================================
    // PARSE SAFE
    // =========================================
    private int parseIntSafe(String text) {
        try {
            return Integer.parseInt(text.trim());
        } catch (Exception e) {
            return 0;
        }
    }

    private double parseDoubleSafe(String text) {
        try {
            return Double.parseDouble(text.trim().replace(",", "."));
        } catch (Exception e) {
            return 0;
        }
    }

    @Override public void mousePressed(MouseEvent e)  {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e)  {}
    @Override public void mouseExited(MouseEvent e)   {}
    @Override public void keyTyped(KeyEvent e)        {}
}