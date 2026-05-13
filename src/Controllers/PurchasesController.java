package Controllers;

import static Models.EmployeesDao.id_user;
import Models.Products;
import Models.ProductsDao;
import Models.Purchases;
import Models.PurchasesDao;
import Models.Suppliers;
import Models.SuppliersDao;
import Views.SystemView;

import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
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
    private SuppliersDao suppliersDao;

    private int getIdSupplier = 0;

    DefaultTableModel model;

    public PurchasesController(
            Purchases purchase,
            PurchasesDao purchaseDao,
            SystemView views) {

        this.purchase     = purchase;
        this.purchaseDao  = purchaseDao;
        this.views        = views;
        this.product      = new Products();
        this.productsDao  = new ProductsDao();
        this.suppliersDao = new SuppliersDao();

        model = (DefaultTableModel) views.purchases_table.getModel();

        views.txt_purchase_product_code.addKeyListener(this);
        views.txt_purchase_amount.addKeyListener(this);
        views.txt_purchase_price.addKeyListener(this);

        views.btn_add_product_to_buy.addActionListener(this);
        views.btn_confirm_purchase.addActionListener(this);
        views.btn_remove_purchase.addActionListener(this);
        views.btn_new_purchase.addActionListener(this);

        views.purchases_table.addMouseListener(this);

        loadSuppliers();
    }

    // =========================================
    // CARGAR PROVEEDORES
    // =========================================
    public void loadSuppliers() {
        views.cmb_purchase_supplier.removeAllItems();
        List<Suppliers> list = suppliersDao.listSuppliersQuery("");
        for (Suppliers s : list) {
            views.cmb_purchase_supplier.addItem(s.getName());
        }
    }

    // =========================================
    // BOTONES
    // =========================================
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == views.btn_add_product_to_buy) addProductToTable();
        if (e.getSource() == views.btn_remove_purchase)    removeProductFromTable();
        if (e.getSource() == views.btn_confirm_purchase)   insertPurchase();
        if (e.getSource() == views.btn_new_purchase)       newPurchase();
    }

    // =========================================
    // BUSCAR PRODUCTO POR CÓDIGO — ENTER
    // =========================================
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getSource() == views.txt_purchase_product_code
                && e.getKeyCode() == KeyEvent.VK_ENTER) {

            String codeText = views.txt_purchase_product_code.getText().trim();
            if (codeText.isEmpty()) return;

            product = productsDao.searchCode(parseIntSafe(codeText));

            if (product != null) {
                views.txt_purchase_product_name.setText(product.getName());
                views.txt_purchase_id.setText(String.valueOf(product.getId()));
                views.txt_purchase_price.setText(
                        String.format("%.2f", product.getUnit_price()));
                views.txt_purchase_amount.requestFocus();
            } else {
                JOptionPane.showMessageDialog(null, "Producto no encontrado o inactivo");
                cleanFieldsPurchases();
            }
        }
    }

    // =========================================
    // CALCULAR SUBTOTAL
    // =========================================
    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getSource() == views.txt_purchase_amount
                || e.getSource() == views.txt_purchase_price) {

            int amount      = parseIntSafe(views.txt_purchase_amount.getText());
            double price    = parseDoubleSafe(views.txt_purchase_price.getText());
            double subtotal = amount * price;
            views.txt_purchase_subtotal.setText(String.format("%.2f", subtotal));
        }
    }

    // =========================================
    // AGREGAR PRODUCTO A LA TABLA
    // =========================================
    private void addProductToTable() {

        if (views.txt_purchase_id.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "Busca un producto primero (ingresa el código y presiona Enter)");
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

        String supplierName = (String) views.cmb_purchase_supplier.getSelectedItem();
        if (supplierName == null || supplierName.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Selecciona un proveedor");
            return;
        }

        // Buscar ID del proveedor por nombre
        int supplierId = 0;
        List<Suppliers> allSuppliers = suppliersDao.listSuppliersQuery("");
        for (Suppliers s : allSuppliers) {
            if (s.getName().equals(supplierName)) {
                supplierId = s.getId();
                break;
            }
        }

        if (supplierId == 0) {
            JOptionPane.showMessageDialog(null, "Proveedor no válido");
            return;
        }

        // Bloquear cambio de proveedor dentro de la misma compra
        if (getIdSupplier == 0) {
            getIdSupplier = supplierId;
            views.cmb_purchase_supplier.setEnabled(false);
        } else if (getIdSupplier != supplierId) {
            JOptionPane.showMessageDialog(null,
                    "No puedes mezclar proveedores en la misma compra.");
            return;
        }

        // Verificar si el producto ya fue agregado
        for (int i = 0; i < model.getRowCount(); i++) {
            if (Integer.parseInt(model.getValueAt(i, 0).toString()) == productId) {
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
        row.add(supplierName);

        model.addRow(row.toArray());
        calculateTotal();
        cleanFieldsPurchases();
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

        if (model.getRowCount() == 0) {
            getIdSupplier = 0;
            views.cmb_purchase_supplier.setEnabled(true);
            views.txt_purchase_total_to_pay.setText("");
            loadSuppliers();
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

        // ✅ Retorna el ID directamente
        int purchaseId = purchaseDao.registerPurchaseQuery(getIdSupplier, id_user, total);

        if (purchaseId > 0) {
            for (int i = 0; i < model.getRowCount(); i++) {
                int pid          = Integer.parseInt(model.getValueAt(i, 0).toString());
                int amt          = Integer.parseInt(model.getValueAt(i, 2).toString());
                double unitPrice = parseDoubleSafe(model.getValueAt(i, 3).toString());
                double sub       = parseDoubleSafe(model.getValueAt(i, 4).toString());

                purchaseDao.registerPurchaseDetailQuery(purchaseId, pid, amt, unitPrice, sub);
                // ✅ Actualizar stock
                productsDao.updatePurchaseStockQuery(amt, pid);
            }

            cleanTableTemp();
            cleanFieldsPurchases();
            views.txt_purchase_total_to_pay.setText("");
            views.cmb_purchase_supplier.setEnabled(true);
            getIdSupplier = 0;
            loadSuppliers();

            // ✅ REFRESCAR TABLA DE PRODUCTOS INMEDIATAMENTE
            refreshProductsTable();

            JOptionPane.showMessageDialog(null, "¡Compra registrada correctamente!");
        } else {
            JOptionPane.showMessageDialog(null, "Error al registrar la compra");
        }
    }

    // =========================================
    // NUEVA COMPRA
    // =========================================
    private void newPurchase() {
        cleanTableTemp();
        cleanFieldsPurchases();
        views.txt_purchase_total_to_pay.setText("");
        views.cmb_purchase_supplier.setEnabled(true);
        getIdSupplier = 0;
        loadSuppliers();
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
    // ✅ REFRESCAR TABLA DE PRODUCTOS
    // =========================================
    private void refreshProductsTable() {
        try {
            DefaultTableModel productModel =
                    (DefaultTableModel) views.products_table.getModel();
            productModel.setRowCount(0);

            List<Products> list = productsDao.listProductsQuery("");

            for (Products p : list) {
                Object[] row = {
                    p.getId(),
                    p.getCode(),
                    p.getName(),
                    p.getDescription(),
                    p.getUnit_price(),
                    p.getProduct_quantity(),
                    p.getCategory_name(),
                    p.getStatus() == 1 ? "Activo" : "Inactivo"
                };
                productModel.addRow(row);
            }
        } catch (Exception e) {
            System.out.println("No se pudo refrescar tabla de productos: " + e.getMessage());
        }
    }

    // =========================================
    // LIMPIAR CAMPOS
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
        try { return Integer.parseInt(text.trim()); }
        catch (Exception e) { return 0; }
    }

    private double parseDoubleSafe(String text) {
        try { return Double.parseDouble(text.trim().replace(",", ".")); }
        catch (Exception e) { return 0; }
    }

    @Override public void mouseClicked(MouseEvent e)  {}
    @Override public void mousePressed(MouseEvent e)  {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e)  {}
    @Override public void mouseExited(MouseEvent e)   {}
    @Override public void keyTyped(KeyEvent e)        {}
}