package Controllers;

import Models.Categories;
import Models.CategoriesDao;
import static Models.EmployeesDao.rol_user;
import Views.SystemView;

import java.awt.event.*;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class CategoriesController implements ActionListener, KeyListener, MouseListener {

    private Categories category;
    private CategoriesDao categoryDao;
    private SystemView views;
    DefaultTableModel model;

    public CategoriesController(Categories category,
                                CategoriesDao categoryDao,
                                SystemView views) {
        this.category = category;
        this.categoryDao = categoryDao;
        this.views = views;

        model = (DefaultTableModel) views.categories_table.getModel();

        views.btn_register_category.addActionListener(this);
        views.btn_update_category.addActionListener(this);
        views.btn_delete_category.addActionListener(this);
        views.categories_table.addMouseListener(this);
        views.txt_search_category.addKeyListener(this);

        // Cargar al inicio
        listAllCategories();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == views.btn_register_category) {
            registerCategory();
        }
        if (e.getSource() == views.btn_update_category) {
            updateCategory();
        }
        if (e.getSource() == views.btn_delete_category) {
            deleteCategory();
        }
    }

    // =====================================
    // REGISTRAR
    // =====================================
    private void registerCategory() {

        String name = views.txt_category_name.getText().trim();

        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(null, "El campo nombre es obligatorio");
            return;
        }

        category.setName(name);

        if (categoryDao.registerCategoryQuery(category)) {
            JOptionPane.showMessageDialog(null, "Categoría registrada con éxito");
            cleanFields();
            listAllCategories();
        } else {
            JOptionPane.showMessageDialog(null, "Error al registrar categoría");
        }
    }

    // =====================================
    // MODIFICAR
    // =====================================
    private void updateCategory() {

        if (views.txt_category_id.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Selecciona una categoría");
            return;
        }

        category.setId(Integer.parseInt(views.txt_category_id.getText().trim()));
        category.setName(views.txt_category_name.getText().trim());

        if (categoryDao.updateCategoryQuery(category)) {
            JOptionPane.showMessageDialog(null, "Categoría modificada con éxito");
            cleanFields();
            listAllCategories();
        } else {
            JOptionPane.showMessageDialog(null, "Error al modificar categoría");
        }
    }

    // =====================================
    // ELIMINAR
    // =====================================
    private void deleteCategory() {

        if (views.txt_category_id.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Selecciona una categoría");
            return;
        }

        int id = Integer.parseInt(views.txt_category_id.getText().trim());

        int confirm = JOptionPane.showConfirmDialog(null,
                "¿Seguro que deseas eliminar esta categoría?");

        if (confirm == 0) {
            if (categoryDao.deleteCategoriesQuery(id)) {
                JOptionPane.showMessageDialog(null, "Categoría eliminada");
                cleanFields();
                listAllCategories();
            } else {
                JOptionPane.showMessageDialog(null, "Error al eliminar categoría");
            }
        }
    }

    // =====================================
    // LISTAR — lee rol_user en el momento, no al inicio
    // =====================================
    public void listAllCategories() {

        List<Categories> list = categoryDao.listCategoriesQuery("");

        model.setRowCount(0);

        for (Categories c : list) {
            Object[] row = { c.getId(), c.getName() };
            model.addRow(row);
        }
    }

    // =====================================
    // BUSCADOR
    // =====================================
    @Override
    public void keyReleased(KeyEvent e) {

        if (e.getSource() == views.txt_search_category) {

            String search = views.txt_search_category.getText().trim();
            List<Categories> list = categoryDao.listCategoriesQuery(search);

            model.setRowCount(0);

            for (Categories c : list) {
                Object[] row = { c.getId(), c.getName() };
                model.addRow(row);
            }
        }
    }

    // =====================================
    // CLICK TABLA
    // =====================================
    @Override
    public void mouseClicked(MouseEvent e) {

        int row = views.categories_table.getSelectedRow();

        if (row >= 0) {
            views.txt_category_id.setText(
                    views.categories_table.getValueAt(row, 0).toString());
            views.txt_category_name.setText(
                    views.categories_table.getValueAt(row, 1).toString());
        }
    }

    public void cleanTable()  { model.setRowCount(0); }
    public void cleanFields() {
        views.txt_category_id.setText("");
        views.txt_category_name.setText("");
    }

    @Override public void keyTyped(KeyEvent e)       {}
    @Override public void keyPressed(KeyEvent e)     {}
    @Override public void mousePressed(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e){}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e)  {}
}