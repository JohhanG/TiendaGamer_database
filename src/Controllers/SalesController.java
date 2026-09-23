package Controllers;

import Models.Customers;
import Models.CustomersDao;
import Models.Employees;
import Models.EmployeesDao;
import Models.Kardex;
import Models.KardexDao;
import Models.Products;
import Models.ProductsDao;
import Models.Sales;
import Models.SalesDao;
import Views.SystemView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class SalesController implements ActionListener, MouseListener, KeyListener {

    private Sales sale;
    private SalesDao saleDao;
    private SystemView views;

    private Products product;
    private ProductsDao productsDao;
    private CustomersDao customersDao;

    private Employees loggedEmployee;
    private ProductsController productsController;

    // Modelos y DAOs para la gestión del Kardex
    private Kardex kardex;
    private KardexDao kardexDao;

    private int id_user;
    private DefaultTableModel model;

    // =========================================================================
    // CONSTRUCTORES SOBRECARGADOS (COMPATIBILIDAD)
    // =========================================================================
    public SalesController(Sales sale, SalesDao saleDao, SystemView views) {
        this(sale, saleDao, views, null, null);
    }

    public SalesController(Sales sale, SalesDao saleDao, SystemView views, int id_user) {
        this(sale, saleDao, views, null, null);
        this.id_user = id_user;
    }

    public SalesController(Sales sale, SalesDao saleDao, SystemView views, Employees loggedEmployee) {
        this(sale, saleDao, views, loggedEmployee, null);
    }

    // =========================================================================
    // CONSTRUCTOR PRINCIPAL
    // =========================================================================
    public SalesController(Sales sale, SalesDao saleDao, SystemView views,
                           Employees loggedEmployee, ProductsController productsController) {
        this.sale = sale;
        this.saleDao = saleDao;
        this.views = views;
        this.loggedEmployee = loggedEmployee;
        this.productsController = productsController;
        this.id_user = (loggedEmployee != null && loggedEmployee.getId() > 0) ? loggedEmployee.getId() : 0;

        this.product = new Products();
        this.productsDao = new ProductsDao();
        this.customersDao = new CustomersDao();
        
        // Inicialización de Kardex
        this.kardex = new Kardex();
        this.kardexDao = new KardexDao();

        this.model = (DefaultTableModel) views.sales_table.getModel();

        // Listeners de teclado
        this.views.txt_sale_product_code.addKeyListener(this);
        this.views.txt_sale_quantity.addKeyListener(this);
        this.views.txt_sale_customer_id.addKeyListener(this);

        // Listener de foco: autocompleta el cliente si el usuario sale del campo sin presionar Enter
        this.views.txt_sale_customer_id.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                String idText = views.txt_sale_customer_id.getText().trim();
                String nameText = views.txt_sale_customer_name.getText().trim();
                if (!idText.isEmpty() && nameText.isEmpty()) {
                    searchCustomerForSale(false);
                }
            }
        });

        // Listeners de botones
        this.views.btn_add_product_sale.addActionListener(this);
        this.views.btn_confirm_sale.addActionListener(this);
        this.views.btn_remove_sale.addActionListener(this);
        this.views.btn_new_sale.addActionListener(this);

        this.views.sales_table.addMouseListener(this);
    }

    // =========================================
    // OBTENER ID DEL EMPLEADO ACTIVO
    // =========================================
    private int getActiveEmployeeId() {
        if (this.loggedEmployee != null && this.loggedEmployee.getId() > 0) {
            return this.loggedEmployee.getId();
        }
        if (this.id_user > 0) {
            return this.id_user;
        }
        if (EmployeesDao.id_user > 0) {
            return EmployeesDao.id_user;
        }

        // Fallback: Si no hay usuario logueado en memoria, buscar el primer empleado registrado en BD
        try {
            EmployeesDao empDao = new EmployeesDao();
            List<Employees> list = empDao.listEmployeesQuery("");
            if (list != null && !list.isEmpty()) {
                return list.get(0).getId();
            }
        } catch (Exception ignored) {}

        return 0;
    }

    // =========================================
    // ACCIONES DE BOTONES
    // =========================================
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == views.btn_add_product_sale) {
            addProductToTable();
        } else if (e.getSource() == views.btn_remove_sale) {
            removeProductFromTable();
        } else if (e.getSource() == views.btn_confirm_sale) {
            insertSale();
        } else if (e.getSource() == views.btn_new_sale) {
            newSale();
        }
    }

    // =========================================
    // BUSCAR CLIENTE POR CÉDULA
    // =========================================
    private boolean searchCustomerForSale(boolean showNotFoundDialog) {
        String idText = views.txt_sale_customer_id.getText().trim();
        if (idText.isEmpty()) {
            views.txt_sale_customer_name.setText("");
            return false;
        }

        int customerId;
        try {
            customerId = Integer.parseInt(idText);
        } catch (NumberFormatException ex) {
            if (showNotFoundDialog) {
                JOptionPane.showMessageDialog(null, "La identificación solo puede contener números");
            }
            views.txt_sale_customer_name.setText("");
            views.txt_sale_customer_id.requestFocus();
            return false;
        }

        Customers customer = customersDao.searchCustomer(customerId);
        if (customer != null && customer.getId() > 0) {
            views.txt_sale_customer_name.setText(customer.getFull_name());
            return true;
        } else {
            views.txt_sale_customer_name.setText("");
            if (showNotFoundDialog) {
                JOptionPane.showMessageDialog(null, "No se encontró ningún cliente con la cédula: " + customerId);
                views.txt_sale_customer_id.requestFocus();
            }
            return false;
        }
    }

    // =========================================
    // BUSCAR PRODUCTO O CLIENTE (ENTER)
    // =========================================
    @Override
    public void keyPressed(KeyEvent e) {
        // Buscar producto por código al presionar ENTER
        if (e.getSource() == views.txt_sale_product_code && e.getKeyCode() == KeyEvent.VK_ENTER) {
            String codeText = views.txt_sale_product_code.getText().trim();
            if (codeText.isEmpty()) return;

            product = productsDao.searchCode(parseIntSafe(codeText));

            if (product != null && product.getId() > 0) {
                views.txt_sale_product_name.setText(product.getName());
                views.txt_sale_product_id.setText(String.valueOf(product.getId()));
                views.txt_sale_price.setText(String.format("%.2f", product.getUnit_price()));
                views.txt_sale_stock.setText(String.valueOf(product.getProduct_quantity()));
                views.txt_sale_quantity.requestFocus();
            } else {
                JOptionPane.showMessageDialog(null, "Producto no encontrado o inactivo");
                cleanFieldsSales();
            }
        } 
        // Buscar cliente por cédula al presionar ENTER
        else if (e.getSource() == views.txt_sale_customer_id && e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (searchCustomerForSale(true)) {
                views.txt_sale_product_code.requestFocus();
            }
        }
    }

    // =========================================
    // CALCULAR SUBTOTAL EN TIEMPO REAL
    // =========================================
    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getSource() == views.txt_sale_quantity) {
            String qtyText = views.txt_sale_quantity.getText().trim();
            String priceText = views.txt_sale_price.getText().trim();

            if (!qtyText.isEmpty() && !priceText.isEmpty()) {
                int qty = parseIntSafe(qtyText);
                double price = parseDoubleSafe(priceText);
                double subtotal = qty * price;
                views.txt_sale_subtotal.setText(String.format("%.2f", subtotal));
            }
        }
    }

    // =========================================
    // AGREGAR PRODUCTO A LA TABLA
    // =========================================
    private void addProductToTable() {
        if (views.txt_sale_product_id.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Busca un producto primero (ingresa el código y presiona Enter)");
            return;
        }

        int productId = parseIntSafe(views.txt_sale_product_id.getText());
        int quantity = parseIntSafe(views.txt_sale_quantity.getText());
        int stock = parseIntSafe(views.txt_sale_stock.getText());
        double price = parseDoubleSafe(views.txt_sale_price.getText());

        if (quantity <= 0) {
            JOptionPane.showMessageDialog(null, "La cantidad debe ser mayor a 0");
            return;
        }

        if (quantity > stock) {
            JOptionPane.showMessageDialog(null, "Stock insuficiente. Disponible: " + stock);
            return;
        }

        double subtotal = quantity * price;

        // Verificar si el producto ya está en el carrito
        for (int i = 0; i < model.getRowCount(); i++) {
            if (Integer.parseInt(model.getValueAt(i, 0).toString()) == productId) {
                JOptionPane.showMessageDialog(null, "El producto ya está en la lista de venta.");
                return;
            }
        }

        model.addRow(new Object[]{
            productId,
            views.txt_sale_product_name.getText(),
            quantity,
            String.format("%.2f", price),
            String.format("%.2f", subtotal)
        });

        calculateTotal();
        cleanFieldsSales();
        views.txt_sale_product_code.requestFocus();
    }

    // =========================================
    // ELIMINAR ITEM DE LA TABLA
    // =========================================
    private void removeProductFromTable() {
        int row = views.sales_table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(null, "Selecciona una fila para eliminar");
            return;
        }
        model.removeRow(row);
        calculateTotal();
    }

    // =========================================
    // PROCESAR Y CONFIRMAR VENTA
    // =========================================
    private void insertSale() {
        if (model.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "No hay productos en la lista de venta");
            return;
        }

        String customerIdStr = views.txt_sale_customer_id.getText().trim();
        if (customerIdStr.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debes ingresar la cédula del cliente.");
            views.txt_sale_customer_id.requestFocus();
            return;
        }

        int customerId;
        try {
            customerId = Integer.parseInt(customerIdStr);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "La cédula del cliente debe ser numérica.");
            views.txt_sale_customer_id.requestFocus();
            return;
        }

        // Si el nombre aún no está cargado (no presionó Enter), validarlo y cargarlo
        if (views.txt_sale_customer_name.getText().trim().isEmpty()) {
            boolean found = searchCustomerForSale(true);
            if (!found) {
                return;
            }
        }

        int employeeId = getActiveEmployeeId();
        if (employeeId <= 0) {
            JOptionPane.showMessageDialog(null,
                    "No se encontró un empleado activo o registrado para procesar la venta.",
                    "Error de Empleado",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        double total = parseDoubleSafe(views.txt_sale_total_to_pay.getText());

        int confirm = JOptionPane.showConfirmDialog(
                null,
                "¿Confirmar venta por un total de $" + String.format("%.2f", total) + "?",
                "Confirmar Venta",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (confirm != JOptionPane.YES_OPTION) return;

        // Registrar encabezado de la venta en la base de datos
        int saleId = saleDao.registerSaleQuery(customerId, employeeId, total);

        if (saleId > 0) {
            boolean allOk = true;

            // Registrar detalle, ajustar inventario y registrar movimiento en Kardex
            for (int i = 0; i < model.getRowCount(); i++) {
                int productId = parseIntSafe(model.getValueAt(i, 0).toString());
                int quantity  = parseIntSafe(model.getValueAt(i, 2).toString());
                double price  = parseDoubleSafe(model.getValueAt(i, 3).toString());
                double sub    = parseDoubleSafe(model.getValueAt(i, 4).toString());

                // Obtener el stock actual antes de aplicar la venta
                Products currentProduct = productsDao.searchCode(productId);
                int stockAnterior = (currentProduct != null) ? currentProduct.getProduct_quantity() : quantity;
                int stockNuevo = stockAnterior - quantity;

                // 1. Insertar detalle de venta
                boolean detailOk = saleDao.registerSaleDetailQuery(saleId, productId, quantity, price, sub);
                
                // 2. Descontar del stock general
                boolean stockOk  = productsDao.updateStockQuery(quantity, productId);

                // 3. Registrar movimiento de SALIDA en el Kardex
                Kardex k = new Kardex();
                k.setIdProducto(productId);
                k.setIdTipoMov(1); // ID para "VENTA" en tu tabla tipo_movimiento
                k.setIdEmpleado(employeeId);
                k.setCantidad(quantity);
                k.setEfecto("SALIDA");
                k.setSaldoAnterior(stockAnterior);
                k.setSaldoResultante(stockNuevo);
                k.setObservacion("Venta realizada en Factura N° " + saleId);

                boolean kardexOk = kardexDao.registrarMovimiento(k);

                if (!detailOk || !stockOk || !kardexOk) {
                    allOk = false;
                }
            }

            cleanTableTemp();
            cleanFieldsSales();
            cleanCustomerFields();
            views.txt_sale_total_to_pay.setText("");

            // Refrescar inventario en la pestaña de Productos si el controlador está activo
            if (productsController != null) {
                productsController.refreshTable();
            }

            if (allOk) {
                JOptionPane.showMessageDialog(null, "¡Venta registrada y trazada en el Kardex con éxito!");
            } else {
                JOptionPane.showMessageDialog(null, "Venta procesada, pero se detectaron inconsistencias al registrar en el Kardex.");
            }

        } else {
            JOptionPane.showMessageDialog(null, "Error al procesar la venta en la base de datos");
        }
    }

    // =========================================
    // MÉTODOS AUXILIARES DE LIMPIEZA Y CÁLCULO
    // =========================================
    private void cleanCustomerFields() {
        views.txt_sale_customer_id.setText("");
        views.txt_sale_customer_name.setText("");
    }

    private void newSale() {
        cleanTableTemp();
        cleanFieldsSales();
        cleanCustomerFields();
        views.txt_sale_total_to_pay.setText("");
        views.txt_sale_customer_id.requestFocus();
    }

    private void calculateTotal() {
        double total = 0;
        for (int i = 0; i < model.getRowCount(); i++) {
            total += parseDoubleSafe(model.getValueAt(i, 4).toString());
        }
        views.txt_sale_total_to_pay.setText(String.format("%.2f", total));
    }

    private void cleanFieldsSales() {
        views.txt_sale_product_code.setText("");
        views.txt_sale_product_name.setText("");
        views.txt_sale_product_id.setText("");
        views.txt_sale_price.setText("");
        views.txt_sale_stock.setText("");
        views.txt_sale_quantity.setText("");
        views.txt_sale_subtotal.setText("");
    }

    private void cleanTableTemp() {
        model.setRowCount(0);
    }

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

    @Override public void keyTyped(KeyEvent e) {}
    @Override public void mouseClicked(MouseEvent e) {}
    @Override public void mousePressed(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
}