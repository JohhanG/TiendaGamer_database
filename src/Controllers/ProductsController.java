package Controllers;

import Models.Categories;
import Models.CategoriesDao;
import Models.DynamicComboBox;
import Models.Products;
import Models.ProductsDao;
import Views.SystemView;

import java.awt.event.*;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class ProductsController implements ActionListener,
        MouseListener,
        KeyListener {

    private Products product;
    private ProductsDao productsDao;
    private SystemView views;

    DefaultTableModel model;

    public ProductsController(
            Products product,
            ProductsDao productsDao,
            SystemView views) {

        this.product     = product;
        this.productsDao = productsDao;
        this.views       = views;

        model = (DefaultTableModel) views.products_table.getModel();

        // BOTONES
        views.btn_register_product.addActionListener(this);
        views.btn_update_product.addActionListener(this);
        views.btn_delete_product.addActionListener(this);
        views.btn_activate_product.addActionListener(this);
        views.btn_cancel_product.addActionListener(this);

        // EVENTOS
        views.products_table.addMouseListener(this);
        views.txt_search_product.addKeyListener(this);
        views.txt_product_code.addKeyListener(this);

        loadCategories();
        refreshTable(); // ← usa refreshTable() en lugar de cleanTable()+listAllProducts()
    }

    // =====================================
    // CARGAR CATEGORIAS
    // =====================================
    public void loadCategories() {

        CategoriesDao categoriesDao = new CategoriesDao();

        views.cmd_categories.removeAllItems();

        List<Categories> list = categoriesDao.listCategoriesQuery("");

        for (Categories c : list) {
            DynamicComboBox item = new DynamicComboBox(c.getId(), c.getName());
            views.cmd_categories.addItem(item);
        }
    }

    // =====================================
    // REFRESH — limpia y recarga en un solo lugar
    // Usar SIEMPRE este método, nunca llamar
    // cleanTable() + listAllProducts() por separado
    // =====================================
    private void refreshTable() {
        cleanTable();
        listAllProducts();
    }

    // =====================================
    // ACTIONS
    // =====================================
    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == views.btn_register_product) {
            registerProduct();
        }

        if (e.getSource() == views.btn_update_product) {
            updateProduct();
        }

        if (e.getSource() == views.btn_delete_product) {
            deleteProduct();
        }

        if (e.getSource() == views.btn_activate_product) {
            activateProduct();
        }

        if (e.getSource() == views.btn_cancel_product) {
            cleanFields();
        }
    }

    // =====================================
    // REGISTRAR
    // =====================================
    private void registerProduct() {

        if (fieldsEmpty()) {
            JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");
            return;
        }

        DynamicComboBox category =
                (DynamicComboBox) views.cmd_categories.getSelectedItem();

        product.setCode(Integer.parseInt(views.txt_product_code.getText().trim()));
        product.setName(views.txt_product_name.getText().trim());
        product.setDescription(views.txt_product_description.getText().trim());
        product.setUnit_price(Double.parseDouble(views.txt_product_unit_price.getText().trim()));
        product.setProduct_quantity(0);
        product.setCategory_id(category.getId());

        if (productsDao.registerProductsQuery(product)) {
            JOptionPane.showMessageDialog(null, "Producto registrado correctamente");
            refreshTable();
            cleanFields();
        }
    }

    // =====================================
    // MODIFICAR
    // =====================================
    private void updateProduct() {

        if (views.txt_product_id.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Primero debes seleccionar un producto");
            return;
        }

        DynamicComboBox category =
                (DynamicComboBox) views.cmd_categories.getSelectedItem();

        product.setId(Integer.parseInt(views.txt_product_id.getText().trim()));
        product.setCode(Integer.parseInt(views.txt_product_code.getText().trim()));
        product.setName(views.txt_product_name.getText().trim());
        product.setDescription(views.txt_product_description.getText().trim());
        product.setUnit_price(Double.parseDouble(views.txt_product_unit_price.getText().trim()));

        Products current = productsDao.searchProduct(product.getId());
        product.setProduct_quantity(current.getProduct_quantity());
        product.setCategory_id(category.getId());

        if (productsDao.updateProductQuery(product)) {
            JOptionPane.showMessageDialog(null, "Producto modificado correctamente");
            refreshTable();
            cleanFields();
        }
    }

    // =====================================
    // DESACTIVAR
    // =====================================
    private void deleteProduct() {

        if (views.txt_product_id.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Selecciona un producto");
            return;
        }

        int id = Integer.parseInt(views.txt_product_id.getText().trim());

        if (productsDao.deleteProductQuery(id)) {
            JOptionPane.showMessageDialog(null, "Producto desactivado");
            refreshTable();
            cleanFields();
        }
    }

    // =====================================
    // ACTIVAR
    // =====================================
    private void activateProduct() {

        if (views.txt_product_id.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Selecciona un producto");
            return;
        }

        int id = Integer.parseInt(views.txt_product_id.getText().trim());

        if (productsDao.activateProductQuery(id)) {
            JOptionPane.showMessageDialog(null, "Producto activado correctamente");
            refreshTable();
            cleanFields();
        }
    }

    // =====================================
    // LISTAR
    // =====================================
    public void listAllProducts() {

        String search = views.txt_search_product.getText().trim();

        List<Products> list = productsDao.listProductsQuery(search);

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

            model.addRow(row);
        }
    }

    // =====================================
    // CLICK TABLA
    // =====================================
    @Override
    public void mouseClicked(MouseEvent e) {

        int row = views.products_table.getSelectedRow();

        if (row >= 0) {

            views.txt_product_id.setText(model.getValueAt(row, 0).toString());
            views.txt_product_code.setText(model.getValueAt(row, 1).toString());
            views.txt_product_name.setText(model.getValueAt(row, 2).toString());
            views.txt_product_description.setText(model.getValueAt(row, 3).toString());
            views.txt_product_unit_price.setText(model.getValueAt(row, 4).toString());

            String categoryName = model.getValueAt(row, 6).toString();

            for (int i = 0; i < views.cmd_categories.getItemCount(); i++) {
                DynamicComboBox item =
                        (DynamicComboBox) views.cmd_categories.getItemAt(i);
                if (item.getName().equals(categoryName)) {
                    views.cmd_categories.setSelectedIndex(i);
                    break;
                }
            }
        }
    }

    // =====================================
    // BUSCAR POR CÓDIGO (ENTER)
    // =====================================
    @Override
    public void keyPressed(KeyEvent e) {

        if (e.getSource() == views.txt_product_code
                && e.getKeyCode() == KeyEvent.VK_ENTER) {

            String code = views.txt_product_code.getText().trim();

            if (code.isEmpty()) return;

            Products found = productsDao.searchCode(Integer.parseInt(code));

            if (found == null) {
                JOptionPane.showMessageDialog(null, "Producto no encontrado o inactivo");
                cleanFields();
                return;
            }

            views.txt_product_id.setText(String.valueOf(found.getId()));
            views.txt_product_name.setText(found.getName());
            views.txt_product_description.setText(found.getDescription());
            views.txt_product_unit_price.setText(String.valueOf(found.getUnit_price()));
        }
    }

    // =====================================
    // BUSCADOR TABLA
    // =====================================
    @Override
    public void keyReleased(KeyEvent e) {

        if (e.getSource() == views.txt_search_product) {
            refreshTable();
        }
    }

    // =====================================
    // HELPERS
    // =====================================
    public void cleanFields() {

        views.txt_product_id.setText("");
        views.txt_product_code.setText("");
        views.txt_product_name.setText("");
        views.txt_product_description.setText("");
        views.txt_product_unit_price.setText("");

        if (views.cmd_categories.getItemCount() > 0) {
            views.cmd_categories.setSelectedIndex(0);
        }
    }

    public void cleanTable() {
        model.setRowCount(0);
    }

    public boolean fieldsEmpty() {
        return views.txt_product_code.getText().trim().isEmpty()
            || views.txt_product_name.getText().trim().isEmpty()
            || views.txt_product_description.getText().trim().isEmpty()
            || views.txt_product_unit_price.getText().trim().isEmpty();
    }

    @Override public void mousePressed(MouseEvent e)  {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e)  {}
    @Override public void mouseExited(MouseEvent e)   {}
    @Override public void keyTyped(KeyEvent e)        {}
}