package Controllers;

import Models.Employees;
import Models.Purchases;
import Models.PurchasesDao;
import Models.Products;
import Models.ProductsDao;
import Models.Suppliers;
import Models.SuppliersDao;
import Views.SystemView;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

public class PurchasesController implements ActionListener, MouseListener, KeyListener {

    private Purchases purchase;
    private PurchasesDao purchaseDao;
    private SystemView views;
    private Employees loggedEmployee;

    private ProductsDao productsDao;

    private SuppliersDao suppliersDao;
    private List<Suppliers> supplierList;

    // ✅ NUEVO: referencia al controller de Productos, para poder refrescar
    // su tabla (y por lo tanto el stock visible) apenas se registre la compra.
    private ProductsController productsController;

    private DefaultTableModel model;

    private final List<Integer> rowCodes = new ArrayList<>();
    private final List<Integer> rowProductIds = new ArrayList<>();

    double totalPagar = 0.0;
    int itemPerRow = 0;

    public PurchasesController(Purchases purchase, PurchasesDao purchaseDao, SystemView views,
                                Employees loggedEmployee, ProductsController productsController) {
        this.purchase = purchase;
        this.purchaseDao = purchaseDao;
        this.views = views;
        this.loggedEmployee = loggedEmployee;
        this.productsController = productsController; // ✅ NUEVO
        this.productsDao = new ProductsDao();
        this.suppliersDao = new SuppliersDao();
        this.model = (DefaultTableModel) views.purchases_table.getModel();

        // Listener de teclado: búsqueda de producto al presionar Enter, igual que en ventas
        this.views.txt_purchase_product_code.addKeyListener(this);

        // Listeners de botones
        this.views.btn_add_product_to_buy.addActionListener(this);
        this.views.btn_confirm_purchase.addActionListener(this);
        this.views.btn_remove_purchase.addActionListener(this);
        this.views.btn_new_purchase.addActionListener(this);
        this.views.purchases_table.addMouseListener(this);

        // Cargar proveedores en el combo
        loadSuppliers();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == views.btn_add_product_to_buy) {
            addProductToPurchaseTable();
        } else if (e.getSource() == views.btn_confirm_purchase) {
            insertPurchase();
        } else if (e.getSource() == views.btn_remove_purchase) {
            removeProductFromTable();
        } else if (e.getSource() == views.btn_new_purchase) {
            cleanPurchasesForm();
        }
    }

    // =========================================
    // CARGAR PROVEEDORES EN EL COMBO
    // =========================================
    public void loadSuppliers() {
        supplierList = suppliersDao.listSuppliersQuery(""); // "" trae todos

        views.cmb_purchase_supplier.removeAllItems();

        if (supplierList == null || supplierList.isEmpty()) {
            return;
        }

        for (Suppliers s : supplierList) {
            views.cmb_purchase_supplier.addItem(s.getName());
        }
    }

    // =========================================
    // BUSCAR PRODUCTO POR CÓDIGO (ENTER) — igual que en ventas
    // =========================================
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getSource() == views.txt_purchase_product_code && e.getKeyCode() == KeyEvent.VK_ENTER) {
            String codeText = views.txt_purchase_product_code.getText().trim();
            if (codeText.isEmpty()) return;

            int code;
            try {
                code = Integer.parseInt(codeText);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(views, "Ingrese un código de producto válido (número entero)");
                return;
            }

            Products product = productsDao.searchCode(code);

            if (product != null && product.getId() > 0) {
                views.txt_purchase_product_name.setText(product.getName());
                views.txt_purchase_price.setText(String.format("%.2f", product.getUnit_price()));
                views.txt_purchase_amount.requestFocus();
            } else {
                JOptionPane.showMessageDialog(views, "El producto no existe o está inactivo");
                cleanPurchaseInputs();
                views.txt_purchase_product_code.requestFocus();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // No se usa por ahora (sin vista previa de subtotal antes de agregar)
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    public void addProductToPurchaseTable() {
        try {
            int amount = Integer.parseInt(views.txt_purchase_amount.getText());
            int code = Integer.parseInt(views.txt_purchase_product_code.getText());

            if (amount <= 0) {
                JOptionPane.showMessageDialog(views, "La cantidad debe ser mayor a 0");
                return;
            }

            Products product = productsDao.searchCode(code);

            if (product == null || product.getId() == 0) {
                JOptionPane.showMessageDialog(views, "El producto no existe o está inactivo");
                return;
            }

            int productId = product.getId();
            String productName = product.getName();
            double price = product.getUnit_price();
            double subtotal = amount * price;

            for (int i = 0; i < rowCodes.size(); i++) {
                if (rowCodes.get(i) == code) {
                    int existingAmount = Integer.parseInt(model.getValueAt(i, 2).toString());
                    int newAmount = existingAmount + amount;
                    double newSubtotal = newAmount * price;

                    model.setValueAt(newAmount, i, 2);
                    model.setValueAt(String.format("%.2f", newSubtotal), i, 4);
                    calculateTotalPurchase();
                    cleanPurchaseInputs();
                    views.txt_purchase_product_code.requestFocus();
                    return;
                }
            }

            itemPerRow++;

            String supplierName = (views.cmb_purchase_supplier.getSelectedItem() != null)
                    ? views.cmb_purchase_supplier.getSelectedItem().toString()
                    : "";

            Object[] item = new Object[]{
                itemPerRow,
                productName,
                amount,
                String.format("%.2f", price),
                String.format("%.2f", subtotal),
                supplierName
            };

            model.addRow(item);
            rowCodes.add(code);
            rowProductIds.add(productId);

            calculateTotalPurchase();
            cleanPurchaseInputs();
            views.txt_purchase_product_code.requestFocus();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(views, "Ingrese un código y cantidad válidos (números enteros)");
        }
    }

    public void insertPurchase() {

        if (supplierList == null || supplierList.isEmpty()) {
            JOptionPane.showMessageDialog(views, "No hay proveedores registrados. Registra uno antes de comprar.");
            return;
        }

        int selectedIndex = views.cmb_purchase_supplier.getSelectedIndex();
        if (selectedIndex < 0 || selectedIndex >= supplierList.size()) {
            JOptionPane.showMessageDialog(views, "Selecciona un proveedor válido");
            return;
        }

        if (model.getRowCount() == 0) {
            JOptionPane.showMessageDialog(views, "No hay productos en la lista de compra");
            return;
        }

        int supplierId = supplierList.get(selectedIndex).getId();

        double total = totalPagar;
        String estado = "COMPLETADA";

        purchase.setSupplier_id(supplierId);

        if (loggedEmployee != null) {
            purchase.setEmployee_id(loggedEmployee.getId());
        } else {
            purchase.setEmployee_id(1);
        }

        purchase.setTotal(total);
        purchase.setEstado(estado);

        int purchaseId = purchaseDao.registerPurchaseQuery(purchase);

        if (purchaseId > 0) {
            for (int i = 0; i < model.getRowCount(); i++) {
                int productId = rowProductIds.get(i);
                int amount = Integer.parseInt(model.getValueAt(i, 2).toString());
                double price = Double.parseDouble(model.getValueAt(i, 3).toString().replace(",", "."));
                double subtotal = Double.parseDouble(model.getValueAt(i, 4).toString().replace(",", "."));

                purchaseDao.registerPurchaseDetailsQuery(purchaseId, productId, amount, price, subtotal);

                ProductsDao prodDao = new ProductsDao();
                prodDao.updatePurchaseStockQuery(amount, productId);
            }

            JOptionPane.showMessageDialog(views, "¡Compra registrada exitosamente!");
            cleanPurchasesForm();
            cleanTablePurchases();

            // ✅ NUEVO: refresca la tabla de Productos para que el stock
            // actualizado se vea de inmediato, sin reiniciar el programa.
            if (productsController != null) {
                productsController.refreshTable();
            }
        } else {
            JOptionPane.showMessageDialog(views, "Error al registrar la compra.");
        }
    }

    public void calculateTotalPurchase() {
        totalPagar = 0.0;
        int numRow = model.getRowCount();
        for (int i = 0; i < numRow; i++) {
            totalPagar += Double.parseDouble(String.valueOf(model.getValueAt(i, 4)).replace(",", "."));
        }
        views.txt_purchase_total_to_pay.setText(String.format("%.2f", totalPagar));
    }

    public void cleanPurchaseInputs() {
        views.txt_purchase_product_code.setText("");
        views.txt_purchase_product_name.setText("");
        views.txt_purchase_amount.setText("");
        views.txt_purchase_price.setText("");
    }

    public void cleanPurchasesForm() {
        totalPagar = 0.0;
        itemPerRow = 0;
        views.txt_purchase_total_to_pay.setText("");
        cleanPurchaseInputs();
    }

    public void cleanTablePurchases() {
        model.setRowCount(0);
        rowCodes.clear();
        rowProductIds.clear();
    }

    public void removeProductFromTable() {
        try {
            int row = views.purchases_table.getSelectedRow();
            if (row >= 0) {
                model.removeRow(row);
                rowCodes.remove(row);
                rowProductIds.remove(row);
                calculateTotalPurchase();
                itemPerRow--;
            } else {
                JOptionPane.showMessageDialog(views, "Seleccione un producto de la tabla para eliminar");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(views, "Error al eliminar el producto de la lista");
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}