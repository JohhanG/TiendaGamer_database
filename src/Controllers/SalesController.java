package Controllers;

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

    DefaultTableModel model;
    int item = 0;

    public SalesController(Sales sale, SalesDao saleDao, SystemView views) {

        this.sale = sale;
        this.saleDao = saleDao;
        this.views = views;

        this.product = new Products();
        this.productsDao = new ProductsDao();

        model = (DefaultTableModel) views.sales_table.getModel();

        views.txt_sale_product_code.addKeyListener(this);
        views.txt_sale_quantity.addKeyListener(this);

        views.btn_add_product_sale.addActionListener(this);
        views.btn_remove_sale.addActionListener(this);
        views.btn_confirm_sale.addActionListener(this);
        views.btn_new_sale.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == views.btn_add_product_sale) {
            addProductToSale();
        }

        if (e.getSource() == views.btn_remove_sale) {
            removeProduct();
        }

        if (e.getSource() == views.btn_confirm_sale) {
            registerSale();
        }

        if (e.getSource() == views.btn_new_sale) {
            newSale();
        }
    }

    // =========================
    // AGREGAR PRODUCTO
    // =========================
    public void addProductToSale() {

        if (views.txt_sale_product_id.getText().isEmpty()
                || views.txt_sale_quantity.getText().isEmpty()) {

            JOptionPane.showMessageDialog(null,
                    "Debes buscar producto y cantidad");
            return;
        }

        int quantity = Integer.parseInt(
                views.txt_sale_quantity.getText()
        );

        int stock = Integer.parseInt(
                views.txt_sale_stock.getText()
        );

        if (quantity > stock) {
            JOptionPane.showMessageDialog(null,
                    "Stock insuficiente");
            return;
        }

        double price = Double.parseDouble(
                views.txt_sale_price.getText()
        );

        double subtotal = quantity * price;

        item++;

        model.addRow(new Object[]{
            item,
            views.txt_sale_product_id.getText(),
            views.txt_sale_product_name.getText(),
            quantity,
            price,
            subtotal
        });

        calculateTotal();
        clearSaleFields();
    }

    // =========================
    // REGISTRAR VENTA
    // =========================
    public void registerSale() {

        if (model.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null,
                    "No hay productos");
            return;
        }

        sale.setEmployee_id(id_user);
        sale.setTotal_to_pay(
                Double.parseDouble(
                        views.txt_sale_total_to_pay.getText()
                )
        );

        if (saleDao.registerSaleQuery(sale)) {

            for (int i = 0; i < model.getRowCount(); i++) {

                int productId = Integer.parseInt(
                        model.getValueAt(i, 1).toString()
                );

                int quantity = Integer.parseInt(
                        model.getValueAt(i, 3).toString()
                );

                productsDao.updateStockQuery(quantity, productId);
            }

            JOptionPane.showMessageDialog(null,
                    "Venta registrada con éxito");

            newSale();
        }
    }

    // =========================
    // QUITAR PRODUCTO
    // =========================
    public void removeProduct() {

        int row = views.sales_table.getSelectedRow();

        if (row < 0) {
            JOptionPane.showMessageDialog(null,
                    "Selecciona producto");
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
        item = 0;

        clearSaleFields();

        views.txt_sale_total_to_pay.setText("");
    }

    // =========================
    // CALCULAR TOTAL
    // =========================
    public void calculateTotal() {

        double total = 0;

        for (int i = 0; i < model.getRowCount(); i++) {
            total += Double.parseDouble(
                    model.getValueAt(i, 5).toString()
            );
        }

        views.txt_sale_total_to_pay.setText(
                String.valueOf(total)
        );
    }

    // =========================
    // LIMPIAR CAMPOS
    // =========================
    public void clearSaleFields() {

        views.txt_sale_product_id.setText("");
        views.txt_sale_product_code.setText("");
        views.txt_sale_product_name.setText("");
        views.txt_sale_quantity.setText("");
        views.txt_sale_price.setText("");
        views.txt_sale_stock.setText("");
        views.txt_sale_subtotal.setText("");
    }

    // =========================
    // BUSCAR PRODUCTO
    // =========================
    @Override
    public void keyReleased(KeyEvent e) {

        if (e.getSource() == views.txt_sale_product_code) {

            if (!views.txt_sale_product_code.getText().isEmpty()) {

                int code = Integer.parseInt(
                        views.txt_sale_product_code.getText()
                );

                product = productsDao.searchCode(code);

                if (product != null) {

                    views.txt_sale_product_id.setText(
                            String.valueOf(product.getId())
                    );

                    views.txt_sale_product_name.setText(
                            product.getName()
                    );

                    views.txt_sale_price.setText(
                            String.valueOf(product.getUnit_price())
                    );

                    views.txt_sale_stock.setText(
                            String.valueOf(product.getProduct_quantity())
                    );
                }
            }
        }

        if (e.getSource() == views.txt_sale_quantity) {

            if (!views.txt_sale_quantity.getText().isEmpty()
                    && !views.txt_sale_price.getText().isEmpty()) {

                int quantity = Integer.parseInt(
                        views.txt_sale_quantity.getText()
                );

                double price = Double.parseDouble(
                        views.txt_sale_price.getText()
                );

                double subtotal = quantity * price;

                views.txt_sale_subtotal.setText(
                        String.valueOf(subtotal)
                );
            }
        }
    }

    @Override public void mouseClicked(MouseEvent e) {}
    @Override public void mousePressed(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
    @Override public void keyTyped(KeyEvent e) {}
    @Override public void keyPressed(KeyEvent e) {}
}