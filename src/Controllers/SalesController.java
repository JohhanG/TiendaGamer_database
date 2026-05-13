package Controllers;

import Models.Customers;
import Models.CustomersDao;
import Models.Products;
import Models.ProductsDao;
import Models.Sales;
import Models.SalesDao;
import static Models.EmployeesDao.id_user;
import Views.SystemView;

import java.awt.event.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class SalesController implements ActionListener, KeyListener, MouseListener {

    private Sales sale;
    private SalesDao saleDao;
    private SystemView views;

    private Products product;
    private ProductsDao productsDao;

    private Customers customer;
    private CustomersDao customersDao;

    DefaultTableModel model;

    public SalesController(Sales sale, SalesDao saleDao, SystemView views) {

        this.sale         = sale;
        this.saleDao      = saleDao;
        this.views        = views;
        this.product      = new Products();
        this.productsDao  = new ProductsDao();
        this.customer     = new Customers();
        this.customersDao = new CustomersDao();

        model = (DefaultTableModel) views.sales_table.getModel();

        views.txt_sale_product_code.addKeyListener(this);
        views.txt_sale_quantity.addKeyListener(this);
        views.txt_sale_customer_id.addKeyListener(this);

        views.btn_add_product_sale.addActionListener(this);
        views.btn_remove_sale.addActionListener(this);
        views.btn_confirm_sale.addActionListener(this);
        views.btn_new_sale.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == views.btn_add_product_sale) addProductToSale();
        if (e.getSource() == views.btn_remove_sale)      removeProduct();
        if (e.getSource() == views.btn_confirm_sale)     registerSale();
        if (e.getSource() == views.btn_new_sale)         newSale();
    }

    // =========================
    // AGREGAR PRODUCTO
    // =========================
    public void addProductToSale() {

        if (views.txt_sale_product_id.getText().isEmpty()
                || views.txt_sale_quantity.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "Debes buscar un producto e ingresar la cantidad");
            return;
        }

        int quantity = Integer.parseInt(views.txt_sale_quantity.getText().trim());
        int stock    = Integer.parseInt(views.txt_sale_stock.getText().trim());

        if (quantity <= 0) {
            JOptionPane.showMessageDialog(null, "La cantidad debe ser mayor a 0");
            return;
        }

        if (quantity > stock) {
            JOptionPane.showMessageDialog(null,
                    "Stock insuficiente. Disponible: " + stock);
            return;
        }

        double price    = parseDouble(views.txt_sale_price.getText());
        double subtotal = quantity * price;

        model.addRow(new Object[]{
            views.txt_sale_product_id.getText(),   // col 0: ID Producto
            views.txt_sale_product_name.getText(),  // col 1: Nombre
            quantity,                               // col 2: Cantidad
            String.format("%.2f", price),           // col 3: Precio de Venta
            String.format("%.2f", subtotal)         // col 4: SubTotal
            // col 5: Nombre del Cliente — se llena al registrar
        });

        calculateTotal();
        clearProductFields();
    }

    // =========================
    // REGISTRAR VENTA
    // =========================
    public void registerSale() {

        if (model.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "No hay productos en la lista");
            return;
        }

        if (views.txt_sale_customer_id.getText().trim().isEmpty()
                || views.txt_sale_customer_name.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "Ingresa la cédula del cliente y presiona Enter");
            return;
        }

        double total = parseDouble(views.txt_sale_total_to_pay.getText());

        int confirm = JOptionPane.showConfirmDialog(
                null,
                "¿Confirmar venta por $" + String.format("%.2f", total)
                + " al cliente " + views.txt_sale_customer_name.getText() + "?",
                "Confirmar venta",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );
        if (confirm != JOptionPane.YES_OPTION) return;

        sale.setEmployee_id(id_user);
        sale.setTotal_to_pay(total);
        sale.setCustomer_id(Integer.parseInt(
                views.txt_sale_customer_id.getText().trim()));  // ← FK requerida

        int saleId = saleDao.registerSaleQuery(sale);

        if (saleId > 0) {

            for (int i = 0; i < model.getRowCount(); i++) {

                int productId   = Integer.parseInt(model.getValueAt(i, 0).toString());
                int quantity    = Integer.parseInt(model.getValueAt(i, 2).toString());
                double price    = parseDouble(model.getValueAt(i, 3).toString());
                double subtotal = parseDouble(model.getValueAt(i, 4).toString());

                // Registrar detalle de venta
                saleDao.registerSaleDetailsQuery(productId, saleId, quantity, price, subtotal);

                // Descontar stock
                boolean stockOk = productsDao.updateStockQuery(quantity, productId);

                System.out.println("Producto ID=" + productId
                        + " | Cantidad=" + quantity
                        + " | Stock actualizado=" + stockOk);
            }

            JOptionPane.showMessageDialog(null, "¡Venta registrada con éxito!");
            newSale();
            refreshProductsTable();  // ← refresca stock en tabla de productos

        } else {
            JOptionPane.showMessageDialog(null, "Error al registrar la venta");
        }
    }

    // =========================
    // QUITAR PRODUCTO
    // =========================
    public void removeProduct() {
        int row = views.sales_table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(null, "Selecciona un producto para eliminar");
            return;
        }
        model.removeRow(row);
        calculateTotal();
    }

    // =========================
    // NUEVA VENTA
    // =========================
    public void newSale() {
        model.setRowCount(0);
        clearProductFields();
        views.txt_sale_total_to_pay.setText("");
        views.txt_sale_customer_id.setText("");
        views.txt_sale_customer_name.setText("");
    }

    // =========================
    // CALCULAR TOTAL
    // =========================
    public void calculateTotal() {
        double total = 0;
        for (int i = 0; i < model.getRowCount(); i++) {
            total += parseDouble(model.getValueAt(i, 4).toString());
        }
        views.txt_sale_total_to_pay.setText(String.format("%.2f", total));
    }

    // =========================
    // LIMPIAR CAMPOS PRODUCTO
    // =========================
    public void clearProductFields() {
        views.txt_sale_product_id.setText("");
        views.txt_sale_product_code.setText("");
        views.txt_sale_product_name.setText("");
        views.txt_sale_quantity.setText("");
        views.txt_sale_price.setText("");
        views.txt_sale_stock.setText("");
        views.txt_sale_subtotal.setText("");
    }

    // =========================
    // TECLADO — keyReleased
    // =========================
    @Override
    public void keyReleased(KeyEvent e) {

        // Buscar producto por código mientras escribe
        if (e.getSource() == views.txt_sale_product_code) {
            String codeText = views.txt_sale_product_code.getText().trim();
            if (!codeText.isEmpty()) {
                try {
                    int code = Integer.parseInt(codeText);
                    product = productsDao.searchCode(code);
                    if (product != null) {
                        views.txt_sale_product_id.setText(String.valueOf(product.getId()));
                        views.txt_sale_product_name.setText(product.getName());
                        views.txt_sale_price.setText(String.format("%.2f", product.getUnit_price()));
                        views.txt_sale_stock.setText(String.valueOf(product.getProduct_quantity()));
                    } else {
                        views.txt_sale_product_id.setText("");
                        views.txt_sale_product_name.setText("");
                        views.txt_sale_price.setText("");
                        views.txt_sale_stock.setText("");
                    }
                } catch (NumberFormatException ex) { }
            }
        }

        // Calcular subtotal al escribir cantidad
        // Usa parseDouble para manejar tanto "1000000.00" como "1000000,00"
        if (e.getSource() == views.txt_sale_quantity) {
            String qtyText   = views.txt_sale_quantity.getText().trim();
            String priceText = views.txt_sale_price.getText().trim();

            if (!qtyText.isEmpty() && !priceText.isEmpty()) {
                try {
                    int qty         = Integer.parseInt(qtyText);
                    double price    = parseDouble(priceText);
                    double subtotal = qty * price;
                    views.txt_sale_subtotal.setText(String.format("%.2f", subtotal));
                } catch (NumberFormatException ex) { }
            }
        }
    }

    // =========================
    // TECLADO — keyPressed
    // Buscar cliente por cédula al presionar ENTER
    // =========================
    @Override
    public void keyPressed(KeyEvent e) {

        if (e.getSource() == views.txt_sale_customer_id
                && e.getKeyCode() == KeyEvent.VK_ENTER) {

            String cedulaText = views.txt_sale_customer_id.getText().trim();

            if (cedulaText.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Ingresa la cédula del cliente");
                return;
            }

            try {
                int cedula = Integer.parseInt(cedulaText);
                Customers found = customersDao.searchCustomer(cedula);

                if (found != null) {
                    views.txt_sale_customer_name.setText(found.getFull_name());
                } else {
                    views.txt_sale_customer_name.setText("");
                    JOptionPane.showMessageDialog(null,
                            "Cliente no encontrado. Verifica la cédula o regístralo primero.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "La cédula debe ser un número");
            }
        }
    }

    // =========================
    // REFRESCAR TABLA DE PRODUCTOS
    // Actualiza el stock visible sin necesidad de reabrir el sistema
    // =========================
    private void refreshProductsTable() {
        try {
            javax.swing.table.DefaultTableModel productModel =
                    (javax.swing.table.DefaultTableModel) views.products_table.getModel();

            productModel.setRowCount(0);

            java.util.List<Products> list = productsDao.listProductsQuery("");

            for (Products p : list) {
                productModel.addRow(new Object[]{
                    p.getId(),
                    p.getCode(),
                    p.getName(),
                    p.getDescription(),
                    p.getUnit_price(),
                    p.getProduct_quantity(),
                    p.getCategory_name(),
                    p.getStatus() == 1 ? "Activo" : "Inactivo"
                });
            }
        } catch (Exception ex) {
            System.out.println("Error al refrescar productos: " + ex.getMessage());
        }
    }

    // =========================
    // PARSE DOUBLE — maneja coma y punto como decimal
    // =========================
    private double parseDouble(String text) {
        try {
            return Double.parseDouble(text.trim().replace(",", "."));
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    @Override public void mouseClicked(MouseEvent e)  {}
    @Override public void mousePressed(MouseEvent e)  {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e)  {}
    @Override public void mouseExited(MouseEvent e)   {}
    @Override public void keyTyped(KeyEvent e)        {}
}