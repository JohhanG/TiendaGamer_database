/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Views;
 
/**
 *
 * @author Richa
 */
public class SystemView extends javax.swing.JFrame {
 
    private static final java.util.logging.Logger logger =
            java.util.logging.Logger.getLogger(SystemView.class.getName());
 
    /**
     * Creates new form SystemView
     * Recibe el empleado logueado para navegación y control de permisos
     */
   public SystemView(Models.Employees loggedEmployee) {
    initComponents();
    setSize(1208, 680);
    setResizable(false);
    setTitle("Panel de Administracion");
    setLocationRelativeTo(null);

    // MODELOS
    Models.Employees employee         = new Models.Employees();
    Models.EmployeesDao employeesDao  = new Models.EmployeesDao();
    Models.Suppliers supplier         = new Models.Suppliers();
    Models.SuppliersDao suppliersDao  = new Models.SuppliersDao();
    Models.Categories category        = new Models.Categories();
    Models.CategoriesDao categoryDao  = new Models.CategoriesDao();
    Models.Products product           = new Models.Products();
    Models.ProductsDao productsDao    = new Models.ProductsDao();
    Models.Sales sale                 = new Models.Sales();
    Models.SalesDao salesDao          = new Models.SalesDao();

    // CONTROLLERS
    Controllers.EmployeesController employeeController =
            new Controllers.EmployeesController(employee, employeesDao, this);
    // ← SIN llamada extra a listAllEmployees() aquí

    Controllers.SuppliersController supplierController =
            new Controllers.SuppliersController(supplier, suppliersDao, this);

    Controllers.CategoriesController categoryController =
            new Controllers.CategoriesController(category, categoryDao, this);
    // ← SIN llamada extra a listAllCategories() aquí

    Controllers.ProductsController productController =
            new Controllers.ProductsController(product, productsDao, this);
    // ← SIN llamada extra a listAllProducts() aquí ← ESTE ERA EL BUG

    Controllers.SalesController salesController =
            new Controllers.SalesController(sale, salesDao, this);

    // Settings: navegación lateral + restricciones por rol
    Controllers.SettingsControllers setting =
            new Controllers.SettingsControllers(this, loggedEmployee);

    this.repaint();
}
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane3 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        Menu = new javax.swing.JPanel();
        jPanelProducts = new javax.swing.JPanel();
        jLabelProducts = new javax.swing.JLabel();
        jPanelPurchases = new javax.swing.JPanel();
        jLabelPurchases = new javax.swing.JLabel();
        jPanelCustomers = new javax.swing.JPanel();
        jLabelCustomers = new javax.swing.JLabel();
        jPanelEmployees = new javax.swing.JPanel();
        jLabelEmployees = new javax.swing.JLabel();
        jPanelSupplimers = new javax.swing.JPanel();
        jLabelSupplimers = new javax.swing.JLabel();
        jPanelCategories = new javax.swing.JPanel();
        jLabelCategories = new javax.swing.JLabel();
        jPanelReports = new javax.swing.JPanel();
        jLabelReports = new javax.swing.JLabel();
        jPanelSettings = new javax.swing.JPanel();
        jLabelSettings = new javax.swing.JLabel();
        jPanelSales = new javax.swing.JPanel();
        jLabelSales = new javax.swing.JLabel();
        Logo = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        Cabecera = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        btn_photo = new javax.swing.JButton();
        btn_LoginOut = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txt_product_code = new javax.swing.JTextField();
        txt_product_name = new javax.swing.JTextField();
        txt_product_unit_price = new javax.swing.JTextField();
        txt_product_description = new javax.swing.JTextField();
        txt_product_id = new javax.swing.JTextField();
        cmd_categories = new javax.swing.JComboBox<>();
        btn_register_product = new javax.swing.JButton();
        btn_update_product = new javax.swing.JButton();
        btn_delete_product = new javax.swing.JButton();
        btn_cancel_product = new javax.swing.JButton();
        btn_activate_product = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        products_table = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        txt_search_product = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txt_purchase_product_code = new javax.swing.JTextField();
        txt_purchase_product_name = new javax.swing.JTextField();
        txt_purchase_amount = new javax.swing.JTextField();
        cmb_purchase_supplier = new javax.swing.JComboBox<>();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        txt_purchase_price = new javax.swing.JTextField();
        txt_purchase_subtotal = new javax.swing.JTextField();
        txt_purchase_id = new javax.swing.JTextField();
        txt_purchase_total_to_pay = new javax.swing.JTextField();
        btn_add_product_to_buy = new javax.swing.JButton();
        btn_confirm_purchase = new javax.swing.JButton();
        btn_remove_purchase = new javax.swing.JButton();
        btn_new_purchase = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        purchases_table = new javax.swing.JTable();
        jPanel16 = new javax.swing.JPanel();
        jPanel17 = new javax.swing.JPanel();
        jLabel52 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        jLabel55 = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        jLabel58 = new javax.swing.JLabel();
        jLabel59 = new javax.swing.JLabel();
        jLabel60 = new javax.swing.JLabel();
        jLabel61 = new javax.swing.JLabel();
        txt_sale_product_code = new javax.swing.JTextField();
        txt_sale_product_name = new javax.swing.JTextField();
        txt_sale_quantity = new javax.swing.JTextField();
        txt_sale_customer_id = new javax.swing.JTextField();
        txt_sale_total_to_pay = new javax.swing.JTextField();
        txt_sale_product_id = new javax.swing.JTextField();
        txt_sale_price = new javax.swing.JTextField();
        txt_sale_subtotal = new javax.swing.JTextField();
        txt_sale_stock = new javax.swing.JTextField();
        txt_sale_customer_name = new javax.swing.JTextField();
        btn_add_product_sale = new javax.swing.JButton();
        btn_confirm_sale = new javax.swing.JButton();
        btn_remove_sale = new javax.swing.JButton();
        btn_new_sale = new javax.swing.JButton();
        jScrollPane10 = new javax.swing.JScrollPane();
        sales_table = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        txt_customer_id = new javax.swing.JTextField();
        txt_customer_fullname = new javax.swing.JTextField();
        txt_customer_address = new javax.swing.JTextField();
        txt_customer_telephone = new javax.swing.JTextField();
        txt_customer_email = new javax.swing.JTextField();
        btn_register_customer = new javax.swing.JButton();
        btn_update_customer = new javax.swing.JButton();
        btn_delete_customer = new javax.swing.JButton();
        btn_cancel_customer = new javax.swing.JButton();
        jLabel23 = new javax.swing.JLabel();
        txt_search_customers = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        custormers_table = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        txt_employee_id = new javax.swing.JTextField();
        txt_employee_fullname = new javax.swing.JTextField();
        txt_employee_username = new javax.swing.JTextField();
        cmb_rol = new javax.swing.JComboBox<>();
        txt_employee_address = new javax.swing.JTextField();
        txt_employee_telephone = new javax.swing.JTextField();
        txt_employee_email = new javax.swing.JTextField();
        txt_employee_password = new javax.swing.JPasswordField();
        btn_register_employee = new javax.swing.JButton();
        btn_update_employee = new javax.swing.JButton();
        btn_delete_employee = new javax.swing.JButton();
        btn_cancel_employee = new javax.swing.JButton();
        jLabel32 = new javax.swing.JLabel();
        txt_search_employee = new javax.swing.JTextField();
        jScrollPane5 = new javax.swing.JScrollPane();
        employees_table = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        txt_supplier_name = new javax.swing.JTextField();
        txt_supplier_addres = new javax.swing.JTextField();
        txt_supplier_telephone = new javax.swing.JTextField();
        txt_supplier_email = new javax.swing.JTextField();
        txt_supplier_description = new javax.swing.JTextField();
        cmb_supplier_city = new javax.swing.JComboBox<>();
        txt_supplier_id = new javax.swing.JTextField();
        btn_register_supplier = new javax.swing.JButton();
        btn_update_supplier = new javax.swing.JButton();
        btn_delete_supplier = new javax.swing.JButton();
        btn_cancel_supplier = new javax.swing.JButton();
        jLabel40 = new javax.swing.JLabel();
        txt_search_supplier = new javax.swing.JTextField();
        jScrollPane6 = new javax.swing.JScrollPane();
        suppliers_table = new javax.swing.JTable();
        jPanel11 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        txt_category_id = new javax.swing.JTextField();
        txt_category_name = new javax.swing.JTextField();
        btn_register_category = new javax.swing.JButton();
        btn_update_category = new javax.swing.JButton();
        btn_delete_category = new javax.swing.JButton();
        jLabel43 = new javax.swing.JLabel();
        txt_search_category = new javax.swing.JTextField();
        jScrollPane7 = new javax.swing.JScrollPane();
        categories_table = new javax.swing.JTable();
        jPanel13 = new javax.swing.JPanel();
        jLabel44 = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        table_all_purchases = new javax.swing.JTable();
        jLabel62 = new javax.swing.JLabel();
        jScrollPane9 = new javax.swing.JScrollPane();
        table_all_sales = new javax.swing.JTable();
        jPanel14 = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        jLabel45 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        txt_id_profile = new javax.swing.JTextField();
        txt_name_profile = new javax.swing.JTextField();
        txt_address_profile = new javax.swing.JTextField();
        txt_phone_profile = new javax.swing.JTextField();
        txt_email_profile = new javax.swing.JTextField();
        txt_password_modify = new javax.swing.JPasswordField();
        txt_password_modify_confirm = new javax.swing.JPasswordField();
        btn_modify_data = new javax.swing.JButton();

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane3.setViewportView(jTable2);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Menu.setBackground(new java.awt.Color(153, 153, 255));
        Menu.setAutoscrolls(true);
        Menu.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanelProducts.setBackground(new java.awt.Color(153, 153, 255));

        jLabelProducts.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabelProducts.setForeground(new java.awt.Color(255, 255, 255));
        jLabelProducts.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/comercio-electronico.png"))); // NOI18N
        jLabelProducts.setText("Productos");

        javax.swing.GroupLayout jPanelProductsLayout = new javax.swing.GroupLayout(jPanelProducts);
        jPanelProducts.setLayout(jPanelProductsLayout);
        jPanelProductsLayout.setHorizontalGroup(
            jPanelProductsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelProductsLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jLabelProducts)
                .addContainerGap(53, Short.MAX_VALUE))
        );
        jPanelProductsLayout.setVerticalGroup(
            jPanelProductsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelProductsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelProducts, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        Menu.add(jPanelProducts, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 200, 40));

        jPanelPurchases.setBackground(new java.awt.Color(153, 153, 255));

        jLabelPurchases.setBackground(new java.awt.Color(18, 45, 61));
        jLabelPurchases.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabelPurchases.setForeground(new java.awt.Color(255, 255, 255));
        jLabelPurchases.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/tienda-online.png"))); // NOI18N
        jLabelPurchases.setText("Compras");

        javax.swing.GroupLayout jPanelPurchasesLayout = new javax.swing.GroupLayout(jPanelPurchases);
        jPanelPurchases.setLayout(jPanelPurchasesLayout);
        jPanelPurchasesLayout.setHorizontalGroup(
            jPanelPurchasesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelPurchasesLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jLabelPurchases, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40))
        );
        jPanelPurchasesLayout.setVerticalGroup(
            jPanelPurchasesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelPurchasesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelPurchases, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        Menu.add(jPanelPurchases, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 200, 40));

        jPanelCustomers.setBackground(new java.awt.Color(153, 153, 255));

        jLabelCustomers.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabelCustomers.setForeground(new java.awt.Color(255, 255, 255));
        jLabelCustomers.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/nueva-cuenta.png"))); // NOI18N
        jLabelCustomers.setText("Clientes");

        javax.swing.GroupLayout jPanelCustomersLayout = new javax.swing.GroupLayout(jPanelCustomers);
        jPanelCustomers.setLayout(jPanelCustomersLayout);
        jPanelCustomersLayout.setHorizontalGroup(
            jPanelCustomersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelCustomersLayout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(jLabelCustomers, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(52, Short.MAX_VALUE))
        );
        jPanelCustomersLayout.setVerticalGroup(
            jPanelCustomersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelCustomersLayout.createSequentialGroup()
                .addGap(0, 8, Short.MAX_VALUE)
                .addComponent(jLabelCustomers))
        );

        Menu.add(jPanelCustomers, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 180, 200, 40));

        jPanelEmployees.setBackground(new java.awt.Color(153, 153, 255));

        jLabelEmployees.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabelEmployees.setForeground(new java.awt.Color(255, 255, 255));
        jLabelEmployees.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/contratacion.png"))); // NOI18N
        jLabelEmployees.setText("Empleados");

        javax.swing.GroupLayout jPanelEmployeesLayout = new javax.swing.GroupLayout(jPanelEmployees);
        jPanelEmployees.setLayout(jPanelEmployeesLayout);
        jPanelEmployeesLayout.setHorizontalGroup(
            jPanelEmployeesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelEmployeesLayout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addComponent(jLabelEmployees, javax.swing.GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanelEmployeesLayout.setVerticalGroup(
            jPanelEmployeesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelEmployeesLayout.createSequentialGroup()
                .addComponent(jLabelEmployees)
                .addGap(0, 8, Short.MAX_VALUE))
        );

        Menu.add(jPanelEmployees, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 240, 200, 40));

        jPanelSupplimers.setBackground(new java.awt.Color(153, 153, 255));

        jLabelSupplimers.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabelSupplimers.setForeground(new java.awt.Color(255, 255, 255));
        jLabelSupplimers.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/proveedores.png"))); // NOI18N
        jLabelSupplimers.setText("Proveedores");

        javax.swing.GroupLayout jPanelSupplimersLayout = new javax.swing.GroupLayout(jPanelSupplimers);
        jPanelSupplimers.setLayout(jPanelSupplimersLayout);
        jPanelSupplimersLayout.setHorizontalGroup(
            jPanelSupplimersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSupplimersLayout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(jLabelSupplimers)
                .addContainerGap(40, Short.MAX_VALUE))
        );
        jPanelSupplimersLayout.setVerticalGroup(
            jPanelSupplimersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSupplimersLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelSupplimers, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(17, 17, 17))
        );

        Menu.add(jPanelSupplimers, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 290, 200, 40));

        jPanelCategories.setBackground(new java.awt.Color(153, 153, 255));

        jLabelCategories.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabelCategories.setForeground(new java.awt.Color(255, 255, 255));
        jLabelCategories.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/subcarpeta.png"))); // NOI18N
        jLabelCategories.setText("Categorias");

        javax.swing.GroupLayout jPanelCategoriesLayout = new javax.swing.GroupLayout(jPanelCategories);
        jPanelCategories.setLayout(jPanelCategoriesLayout);
        jPanelCategoriesLayout.setHorizontalGroup(
            jPanelCategoriesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelCategoriesLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jLabelCategories, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(34, Short.MAX_VALUE))
        );
        jPanelCategoriesLayout.setVerticalGroup(
            jPanelCategoriesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelCategoriesLayout.createSequentialGroup()
                .addComponent(jLabelCategories, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 2, Short.MAX_VALUE))
        );

        Menu.add(jPanelCategories, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 350, 200, 40));

        jPanelReports.setBackground(new java.awt.Color(153, 153, 255));

        jLabelReports.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabelReports.setForeground(new java.awt.Color(255, 255, 255));
        jLabelReports.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/grafico-de-barras.png"))); // NOI18N
        jLabelReports.setText("Reportes");

        javax.swing.GroupLayout jPanelReportsLayout = new javax.swing.GroupLayout(jPanelReports);
        jPanelReports.setLayout(jPanelReportsLayout);
        jPanelReportsLayout.setHorizontalGroup(
            jPanelReportsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelReportsLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jLabelReports, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(45, Short.MAX_VALUE))
        );
        jPanelReportsLayout.setVerticalGroup(
            jPanelReportsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabelReports, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        Menu.add(jPanelReports, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 410, 200, 40));

        jPanelSettings.setBackground(new java.awt.Color(153, 153, 255));

        jLabelSettings.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabelSettings.setForeground(new java.awt.Color(255, 255, 255));
        jLabelSettings.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/configuraciones-web.png"))); // NOI18N
        jLabelSettings.setText("Configuraciones ");

        javax.swing.GroupLayout jPanelSettingsLayout = new javax.swing.GroupLayout(jPanelSettings);
        jPanelSettings.setLayout(jPanelSettingsLayout);
        jPanelSettingsLayout.setHorizontalGroup(
            jPanelSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSettingsLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jLabelSettings)
                .addContainerGap(9, Short.MAX_VALUE))
        );
        jPanelSettingsLayout.setVerticalGroup(
            jPanelSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabelSettings, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        Menu.add(jPanelSettings, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 470, 200, 40));

        jPanelSales.setBackground(new java.awt.Color(153, 153, 255));

        jLabelSales.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabelSales.setForeground(new java.awt.Color(255, 255, 255));
        jLabelSales.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/ventas.png"))); // NOI18N
        jLabelSales.setText("Ventas");

        javax.swing.GroupLayout jPanelSalesLayout = new javax.swing.GroupLayout(jPanelSales);
        jPanelSales.setLayout(jPanelSalesLayout);
        jPanelSalesLayout.setHorizontalGroup(
            jPanelSalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSalesLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jLabelSales, javax.swing.GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanelSalesLayout.setVerticalGroup(
            jPanelSalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelSalesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelSales, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabelSales.getAccessibleContext().setAccessibleName("   Ventas");

        Menu.add(jPanelSales, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 130, 200, 35));

        getContentPane().add(Menu, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 110, 200, 590));

        Logo.setBackground(new java.awt.Color(255, 255, 255));
        Logo.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/almohadilla-de-juego.png"))); // NOI18N
        Logo.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 200, 110));

        getContentPane().add(Logo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 200, 100));

        Cabecera.setBackground(new java.awt.Color(153, 153, 255));
        Cabecera.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setBackground(new java.awt.Color(18, 45, 61));
        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("TIENDA GAMER");
        Cabecera.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 0, 340, 100));

        btn_photo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/perfil (1).png"))); // NOI18N
        btn_photo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_photo.setInheritsPopupMenu(true);
        btn_photo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_photoActionPerformed(evt);
            }
        });
        Cabecera.add(btn_photo, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 10, 70, 80));

        btn_LoginOut.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btn_LoginOut.setText("Salir");
        btn_LoginOut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_LoginOutActionPerformed(evt);
            }
        });
        Cabecera.add(btn_LoginOut, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 40, -1, 20));

        getContentPane().add(Cabecera, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 0, 1010, -1));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Productos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText("Codigo:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("Nombre:");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setText("Precio de Venta:");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setText("Descripcion:");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setText("Categoria:");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setText("ID:");

        txt_product_code.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt_product_code.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_product_codeActionPerformed(evt);
            }
        });

        txt_product_name.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt_product_name.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_product_nameActionPerformed(evt);
            }
        });

        txt_product_unit_price.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt_product_unit_price.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_product_unit_priceActionPerformed(evt);
            }
        });

        txt_product_description.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        txt_product_id.setEditable(false);
        txt_product_id.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt_product_id.setEnabled(false);
        txt_product_id.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_product_idActionPerformed(evt);
            }
        });

        cmd_categories.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        btn_register_product.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_register_product.setText("Registrar");
        btn_register_product.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_register_productActionPerformed(evt);
            }
        });

        btn_update_product.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_update_product.setText("Modificar");

        btn_delete_product.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_delete_product.setText("Desactivar");

        btn_cancel_product.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_cancel_product.setText("Cancelar");

        btn_activate_product.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_activate_product.setText("Activar Producto");
        btn_activate_product.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_activate_productActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(52, 52, 52)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(15, 15, 15))
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(18, 18, 18)))
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addComponent(txt_product_code, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(55, 55, 55)
                                .addComponent(jLabel6))
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addComponent(txt_product_name, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(52, 52, 52)
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel7)))))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel9Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15)
                        .addComponent(txt_product_unit_price, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(15, 15, 15)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_product_description, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmd_categories, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_product_id, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addComponent(btn_register_product, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_update_product, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)))
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_cancel_product, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_delete_product, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(26, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btn_activate_product, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(60, 60, 60))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel6)
                    .addComponent(txt_product_code, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_product_description, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_register_product, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_delete_product, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel7)
                                .addComponent(cmd_categories, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txt_product_name, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4))
                                .addGap(30, 30, 30)
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel5)
                                    .addComponent(txt_product_unit_price, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel8)
                                    .addComponent(txt_product_id, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(2, 2, 2))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btn_cancel_product, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_update_product, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(37, 37, 37)))
                .addComponent(btn_activate_product, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(36, Short.MAX_VALUE))
        );

        jPanel3.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 940, 260));

        products_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Codigo", "Nombre", "Descripcion", "Precio de venta", "Cantidad", "Categoria", "status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(products_table);

        jPanel3.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 330, 950, 110));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setText("Buscar:");
        jPanel3.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 300, -1, -1));

        txt_search_product.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txt_search_product.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_search_productActionPerformed(evt);
            }
        });
        jPanel3.add(txt_search_product, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 290, 160, 30));

        jTabbedPane1.addTab("Productos", jPanel3);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Nueva Compra", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel10.setText("Codigo del Producto:");

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel11.setText("Nombre del Producto:");

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel12.setText("Cantidad:");

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel13.setText("Proveedor:");

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel14.setText("Precio de Compra:");

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel15.setText("Subtotal:");

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel16.setText("ID:");

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel17.setText("Total a Pagar:");

        txt_purchase_subtotal.setEditable(false);

        txt_purchase_id.setEditable(false);
        txt_purchase_id.setEnabled(false);
        txt_purchase_id.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_purchase_idActionPerformed(evt);
            }
        });

        txt_purchase_total_to_pay.setEditable(false);

        btn_add_product_to_buy.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_add_product_to_buy.setText("Agregar");

        btn_confirm_purchase.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_confirm_purchase.setText("Comprar");
        btn_confirm_purchase.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_confirm_purchaseActionPerformed(evt);
            }
        });

        btn_remove_purchase.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_remove_purchase.setText("Eliminar");

        btn_new_purchase.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_new_purchase.setText("Nuevo");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel11)
                        .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(txt_purchase_product_code, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txt_purchase_product_name, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txt_purchase_amount, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(cmb_purchase_supplier, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(55, 55, 55)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE))
                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_purchase_subtotal, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_purchase_price, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_purchase_id, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_purchase_total_to_pay, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(45, 45, 45)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btn_new_purchase, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_remove_purchase, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_add_product_to_buy, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_confirm_purchase, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txt_purchase_product_code, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14)
                    .addComponent(txt_purchase_price, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_add_product_to_buy, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txt_purchase_product_name, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15)
                    .addComponent(txt_purchase_subtotal, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_confirm_purchase, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(txt_purchase_amount, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16)
                    .addComponent(txt_purchase_id, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_remove_purchase, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(cmb_purchase_supplier, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17)
                    .addComponent(txt_purchase_total_to_pay, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_new_purchase, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(77, 77, 77))
        );

        jPanel5.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(35, 20, 920, 310));

        purchases_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nombre del Producto", "Cantidad", "Precio", "Subtotal", "Proveedor"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(purchases_table);

        jPanel5.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 350, 920, 120));

        jTabbedPane1.addTab("Compras", jPanel5);

        jPanel16.setBackground(new java.awt.Color(255, 255, 255));
        jPanel16.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel17.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Nueva Venta", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N
        jPanel17.setVerifyInputWhenFocusTarget(false);

        jLabel52.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel52.setText("Codigo del Producto:");

        jLabel53.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel53.setText("Nombre del Producto:");

        jLabel54.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel54.setText("Cantidad:");

        jLabel55.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel55.setText("Cedula del Cliente:");

        jLabel56.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel56.setText("Total a Pagar:");

        jLabel57.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel57.setText("ID:");

        jLabel58.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel58.setText("Precio:");

        jLabel59.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel59.setText("SubTotal:");

        jLabel60.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel60.setText("Stock:");

        jLabel61.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel61.setText("Nombre del Cliente:");

        txt_sale_product_code.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt_sale_product_code.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_sale_product_codeActionPerformed(evt);
            }
        });

        txt_sale_product_name.setEditable(false);
        txt_sale_product_name.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        txt_sale_quantity.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        txt_sale_customer_id.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        txt_sale_total_to_pay.setEditable(false);
        txt_sale_total_to_pay.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt_sale_total_to_pay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_sale_total_to_payActionPerformed(evt);
            }
        });

        txt_sale_product_id.setEditable(false);
        txt_sale_product_id.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        txt_sale_price.setEditable(false);
        txt_sale_price.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt_sale_price.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_sale_priceActionPerformed(evt);
            }
        });

        txt_sale_subtotal.setEditable(false);
        txt_sale_subtotal.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        txt_sale_stock.setEditable(false);
        txt_sale_stock.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        txt_sale_customer_name.setEditable(false);
        txt_sale_customer_name.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        btn_add_product_sale.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_add_product_sale.setText("Agregar");

        btn_confirm_sale.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_confirm_sale.setText("Vender");
        btn_confirm_sale.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_confirm_saleActionPerformed(evt);
            }
        });

        btn_remove_sale.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_remove_sale.setText("Eliminar");

        btn_new_sale.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_new_sale.setText("Nuevo");
        btn_new_sale.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_new_saleActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel55, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel54, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel52, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel53, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE)
                    .addComponent(jLabel56, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(15, 15, 15)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(txt_sale_customer_id, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txt_sale_quantity, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txt_sale_total_to_pay, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(txt_sale_product_code, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txt_sale_product_name, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(30, 30, 30)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel60, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel59, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel58, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel57, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel61, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_sale_customer_name, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_sale_subtotal, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_sale_stock, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_sale_price, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_sale_product_id, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_add_product_sale, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_confirm_sale, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_remove_sale, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_new_sale, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(42, 42, 42))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel52)
                    .addComponent(txt_sale_product_code, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_sale_product_id, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_add_product_sale, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel57))
                .addGap(20, 20, 20)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel53)
                    .addComponent(txt_sale_product_name, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_sale_price, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_confirm_sale, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel58))
                .addGap(20, 20, 20)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel54)
                    .addComponent(txt_sale_quantity, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_sale_subtotal, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_remove_sale, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel59))
                .addGap(20, 20, 20)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel55)
                    .addComponent(txt_sale_customer_id, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_sale_stock, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_new_sale, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel60))
                .addGap(20, 20, 20)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel56)
                    .addComponent(txt_sale_total_to_pay, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_sale_customer_name, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel61))
                .addGap(20, 20, 20))
        );

        jPanel16.add(jPanel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(35, 20, 920, 315));

        sales_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID Producto", "Nombre", "Cantidad", "Precio de Venta", "SubTotal", "Nombre del Cliente"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane10.setViewportView(sales_table);

        jPanel16.add(jScrollPane10, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 350, 910, 140));

        jTabbedPane1.addTab("Ventas", jPanel16);

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Clientes", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel18.setText("Identificacion:");

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel19.setText("Nombre Completo:");

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel20.setText("Direccion:");

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel21.setText("Telefono:");

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel22.setText("Correo:");

        txt_customer_id.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txt_customer_id.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_customer_idActionPerformed(evt);
            }
        });

        txt_customer_fullname.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        txt_customer_address.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txt_customer_address.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_customer_addressActionPerformed(evt);
            }
        });

        txt_customer_telephone.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txt_customer_telephone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_customer_telephoneActionPerformed(evt);
            }
        });

        txt_customer_email.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txt_customer_email.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_customer_emailActionPerformed(evt);
            }
        });

        btn_register_customer.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_register_customer.setText("Registrar");
        btn_register_customer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_register_customerActionPerformed(evt);
            }
        });

        btn_update_customer.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_update_customer.setText("Modificar");

        btn_delete_customer.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_delete_customer.setText("Eliminar");

        btn_cancel_customer.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_cancel_customer.setText("Cancelar");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_cancel_customer, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txt_customer_id, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(72, 72, 72)
                                .addComponent(txt_customer_address, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txt_customer_fullname, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(55, 55, 55)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(17, 17, 17)
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel10Layout.createSequentialGroup()
                                        .addComponent(txt_customer_email, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(55, 55, 55)
                                        .addComponent(btn_update_customer, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel10Layout.createSequentialGroup()
                                        .addComponent(txt_customer_telephone, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(55, 55, 55)
                                        .addComponent(btn_register_customer, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(2, 2, 2))))
                            .addComponent(btn_delete_customer, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(255, 255, 255))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(jLabel21)
                    .addComponent(txt_customer_id, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                    .addComponent(txt_customer_telephone, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_register_customer, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addGap(31, 31, 31)
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel19)
                                    .addComponent(jLabel22)
                                    .addComponent(txt_customer_fullname, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addComponent(btn_update_customer, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                        .addGap(35, 40, Short.MAX_VALUE)
                        .addComponent(txt_customer_email, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel20)
                            .addComponent(txt_customer_address, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(76, Short.MAX_VALUE))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(btn_delete_customer, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                        .addComponent(btn_cancel_customer, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(21, 21, 21))))
        );

        jPanel6.add(jPanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(35, 20, 920, 282));

        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel23.setText("Buscar");
        jPanel6.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 320, -1, -1));

        txt_search_customers.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jPanel6.add(txt_search_customers, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 310, 160, 30));

        custormers_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Identificacion", "Nombre", "Telefono", "Direccion", "Correo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane4.setViewportView(custormers_table);

        jPanel6.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 350, 910, 150));

        jTabbedPane1.addTab("Clientes", jPanel6);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Empleados", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N
        jPanel7.setVerifyInputWhenFocusTarget(false);

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel24.setText("Identificacion: ");

        jLabel25.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel25.setText("Nombre Completo:");

        jLabel26.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel26.setText("Nombre de Usuario:");

        jLabel27.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel27.setText("Rol:");

        jLabel28.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel28.setText("Direccion:");

        jLabel29.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel29.setText("Telefono:");

        jLabel30.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel30.setText("Correo:");

        jLabel31.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel31.setText("Password:");

        txt_employee_id.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        txt_employee_fullname.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        txt_employee_username.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txt_employee_username.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_employee_usernameActionPerformed(evt);
            }
        });

        cmb_rol.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        cmb_rol.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Administrador", "Auxiliar" }));
        cmb_rol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmb_rolActionPerformed(evt);
            }
        });

        txt_employee_address.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        txt_employee_telephone.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txt_employee_telephone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_employee_telephoneActionPerformed(evt);
            }
        });

        txt_employee_email.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txt_employee_email.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_employee_emailActionPerformed(evt);
            }
        });

        txt_employee_password.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        btn_register_employee.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_register_employee.setText("Registrar");
        btn_register_employee.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_register_employeeActionPerformed(evt);
            }
        });

        btn_update_employee.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_update_employee.setText("Modificar");

        btn_delete_employee.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_delete_employee.setText("Eliminar");

        btn_cancel_employee.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_cancel_employee.setText("Cancelar");
        btn_cancel_employee.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cancel_employeeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(txt_employee_username, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txt_employee_fullname, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txt_employee_id, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(cmb_rol, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(55, 55, 55)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel28, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
                    .addComponent(jLabel30, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel29, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(15, 15, 15)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txt_employee_address, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_employee_telephone, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_employee_email, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(77, 77, 77)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(btn_register_employee)
                            .addComponent(btn_update_employee)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGap(4, 4, 4)
                                .addComponent(btn_delete_employee, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(txt_employee_password, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_cancel_employee, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(66, 66, 66))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(jLabel28)
                    .addComponent(txt_employee_id, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_employee_address, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_register_employee))
                .addGap(30, 30, 30)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25)
                    .addComponent(jLabel29)
                    .addComponent(txt_employee_fullname, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_employee_telephone, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_update_employee))
                .addGap(30, 30, 30)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(jLabel30)
                    .addComponent(txt_employee_username, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_employee_email, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_delete_employee))
                .addGap(30, 30, 30)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27)
                    .addComponent(jLabel31)
                    .addComponent(cmb_rol, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_employee_password, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_cancel_employee))
                .addContainerGap(27, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(35, 20, 920, 280));

        jLabel32.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel32.setText("Buscar:");
        jPanel1.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 317, -1, 20));
        jPanel1.add(txt_search_employee, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 310, 160, 30));

        employees_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Identificacion", "Nombre", "Usuario", "Direccion", "Telefono", "Correo", "Rol"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane5.setViewportView(employees_table);

        jPanel1.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 360, 910, 120));

        jTabbedPane1.addTab("Empleados", jPanel1);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Proveedores", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        jLabel33.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel33.setText("Nombre:");

        jLabel34.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel34.setText("Direccion:");

        jLabel35.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel35.setText("Telefono:");

        jLabel36.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel36.setText("Correo:");

        jLabel37.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel37.setText("Descripcion:");

        jLabel38.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel38.setText("Ciudad:");

        jLabel39.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel39.setText("ID:");

        txt_supplier_name.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt_supplier_name.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_supplier_nameActionPerformed(evt);
            }
        });

        txt_supplier_addres.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        txt_supplier_telephone.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        txt_supplier_email.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt_supplier_email.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_supplier_emailActionPerformed(evt);
            }
        });

        txt_supplier_description.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt_supplier_description.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_supplier_descriptionActionPerformed(evt);
            }
        });

        cmb_supplier_city.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cmb_supplier_city.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Cali", "Pereira", "Medellin", "Bogota" }));

        txt_supplier_id.setEditable(false);
        txt_supplier_id.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt_supplier_id.setEnabled(false);
        txt_supplier_id.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_supplier_idActionPerformed(evt);
            }
        });

        btn_register_supplier.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_register_supplier.setText("Registrar");
        btn_register_supplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_register_supplierActionPerformed(evt);
            }
        });

        btn_update_supplier.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_update_supplier.setText("Modificar");

        btn_delete_supplier.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_delete_supplier.setText("Eliminar");

        btn_cancel_supplier.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_cancel_supplier.setText("Cancelar");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel8Layout.createSequentialGroup()
                            .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(15, 15, 15))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                            .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel35, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(15, 15, 15)
                            .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(txt_supplier_email, javax.swing.GroupLayout.DEFAULT_SIZE, 164, Short.MAX_VALUE)
                                .addComponent(txt_supplier_telephone))
                            .addGap(70, 70, 70)))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txt_supplier_addres, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel8Layout.createSequentialGroup()
                                .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(15, 15, 15)
                                .addComponent(txt_supplier_name, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(70, 70, 70)))
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel37, javax.swing.GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE)
                    .addComponent(jLabel38, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel39, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(38, 38, 38)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmb_supplier_city, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_supplier_description, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_supplier_id, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(70, 70, 70)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_cancel_supplier, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_register_supplier, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_update_supplier, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_delete_supplier, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(143, 143, 143))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel33)
                    .addComponent(jLabel37)
                    .addComponent(txt_supplier_name, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_supplier_description, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_register_supplier, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel34)
                    .addComponent(txt_supplier_addres, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel38)
                    .addComponent(cmb_supplier_city, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_update_supplier, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel35)
                    .addComponent(txt_supplier_telephone, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel39)
                    .addComponent(txt_supplier_id, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_delete_supplier, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel36)
                    .addComponent(txt_supplier_email, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_cancel_supplier, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18))
        );

        jPanel2.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(35, 20, 920, 270));

        jLabel40.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel40.setText("Buscar:");
        jPanel2.add(jLabel40, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 320, -1, -1));

        txt_search_supplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_search_supplierActionPerformed(evt);
            }
        });
        jPanel2.add(txt_search_supplier, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 310, 160, 30));

        suppliers_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nombre", "Descripcion", "Direccion", "Telefono", "Correo", "Ciudad"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane6.setViewportView(suppliers_table);

        jPanel2.add(jScrollPane6, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 360, 910, 130));

        jTabbedPane1.addTab("Proveedores", jPanel2);

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));
        jPanel11.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Categoria", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        jLabel41.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel41.setText("ID:");

        jLabel42.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel42.setText("Nombre:");

        txt_category_id.setEditable(false);
        txt_category_id.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txt_category_id.setEnabled(false);
        txt_category_id.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_category_idActionPerformed(evt);
            }
        });

        txt_category_name.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        btn_register_category.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_register_category.setText("Registrar");

        btn_update_category.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_update_category.setText("Modificar");
        btn_update_category.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_update_categoryActionPerformed(evt);
            }
        });

        btn_delete_category.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_delete_category.setText("Eliminar");
        btn_delete_category.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_delete_categoryActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(txt_category_id, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txt_category_name, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 92, Short.MAX_VALUE)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_delete_category, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_register_category, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_update_category, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(48, 48, 48))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel41)
                    .addComponent(txt_category_id, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_register_category, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel42)
                            .addComponent(txt_category_name, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(btn_update_category, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(30, 30, 30)
                .addComponent(btn_delete_category, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(69, Short.MAX_VALUE))
        );

        jPanel11.add(jPanel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(35, 20, 520, 270));

        jLabel43.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel43.setText("Buscar:");
        jPanel11.add(jLabel43, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 30, -1, -1));

        txt_search_category.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jPanel11.add(txt_search_category, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 20, 290, 30));

        categories_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nombre"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane7.setViewportView(categories_table);

        jPanel11.add(jScrollPane7, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 60, 370, 220));

        jTabbedPane1.addTab("Categorias", jPanel11);

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));
        jPanel13.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel44.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel44.setText("Compras Realizadas");
        jPanel13.add(jLabel44, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 250, -1, -1));

        table_all_purchases.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Factura", "Compra", "Total de Compra", "Fecha de Compra"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane8.setViewportView(table_all_purchases);

        jPanel13.add(jScrollPane8, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 300, 920, 170));

        jLabel62.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel62.setText("Ventas Realizadas");
        jPanel13.add(jLabel62, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 20, -1, -1));

        table_all_sales.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Factura Venta", "Cliente", "Empleado", "Total", "Fecha de Venta"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane9.setViewportView(table_all_sales);

        jPanel13.add(jScrollPane9, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 70, 920, 160));

        jTabbedPane1.addTab("Reportes", jPanel13);

        jPanel14.setBackground(new java.awt.Color(255, 255, 255));
        jPanel14.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel15.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Editar Perfil", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        jLabel45.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel45.setText("Identificacion:");

        jLabel46.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel46.setText("Nombre:");

        jLabel47.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel47.setText("Direccion:");

        jLabel48.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel48.setText("Telefono:");

        jLabel49.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel49.setText("Correo:");

        jLabel50.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel50.setText("Nueva Contraseña");

        jLabel51.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel51.setText("Confirmar Contraseña");

        txt_id_profile.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txt_id_profile.setEnabled(false);
        txt_id_profile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_id_profileActionPerformed(evt);
            }
        });

        txt_name_profile.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txt_name_profile.setEnabled(false);

        txt_address_profile.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txt_address_profile.setEnabled(false);
        txt_address_profile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_address_profileActionPerformed(evt);
            }
        });

        txt_phone_profile.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txt_phone_profile.setEnabled(false);
        txt_phone_profile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_phone_profileActionPerformed(evt);
            }
        });

        txt_email_profile.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txt_email_profile.setEnabled(false);
        txt_email_profile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_email_profileActionPerformed(evt);
            }
        });

        txt_password_modify.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        txt_password_modify_confirm.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        btn_modify_data.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_modify_data.setText("Modificar");
        btn_modify_data.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_modify_dataActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel46, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel47, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel48, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel49, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel45, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(15, 15, 15)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_name_profile, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_id_profile, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(60, 60, 60)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel50)
                            .addComponent(jLabel51))
                        .addGap(15, 15, 15)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_password_modify_confirm, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel15Layout.createSequentialGroup()
                                .addComponent(txt_password_modify, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 64, Short.MAX_VALUE)
                                .addComponent(btn_modify_data, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(txt_address_profile, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_phone_profile, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_email_profile, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(50, 50, 50))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel45)
                        .addComponent(jLabel50)
                        .addComponent(txt_password_modify, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btn_modify_data, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(txt_id_profile, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(30, 30, 30)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel46)
                    .addComponent(txt_name_profile, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(jLabel51)
                    .addComponent(txt_password_modify_confirm, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE))
                .addGap(30, 30, 30)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel47)
                    .addComponent(txt_address_profile, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE))
                .addGap(30, 30, 30)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel48)
                    .addComponent(txt_phone_profile, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE))
                .addGap(30, 30, 30)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel49)
                    .addComponent(txt_email_profile, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE))
                .addGap(30, 30, 30))
        );

        jPanel14.add(jPanel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(35, 20, 920, 390));

        jTabbedPane1.addTab("Perfil", jPanel14);

        getContentPane().add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 100, 1010, 580));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_photoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_photoActionPerformed

    }//GEN-LAST:event_btn_photoActionPerformed

    private void btn_LoginOutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_LoginOutActionPerformed
         if(evt.getSource()== btn_LoginOut){
           dispose();
           LoginView login = new LoginView();
           login.setVisible(true);
         }
    }//GEN-LAST:event_btn_LoginOutActionPerformed

    private void txt_product_nameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_product_nameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_product_nameActionPerformed

    private void txt_product_unit_priceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_product_unit_priceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_product_unit_priceActionPerformed

    private void txt_product_idActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_product_idActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_product_idActionPerformed

    private void txt_product_codeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_product_codeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_product_codeActionPerformed

    private void btn_register_productActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_register_productActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_register_productActionPerformed

    private void txt_search_productActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_search_productActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_search_productActionPerformed

    private void btn_confirm_purchaseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_confirm_purchaseActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_confirm_purchaseActionPerformed

    private void txt_purchase_idActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_purchase_idActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_purchase_idActionPerformed

    private void btn_register_customerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_register_customerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_register_customerActionPerformed

    private void txt_customer_telephoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_customer_telephoneActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_customer_telephoneActionPerformed

    private void txt_customer_emailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_customer_emailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_customer_emailActionPerformed

    private void btn_register_employeeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_register_employeeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_register_employeeActionPerformed

    private void btn_cancel_employeeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cancel_employeeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_cancel_employeeActionPerformed

    private void txt_employee_usernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_employee_usernameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_employee_usernameActionPerformed

    private void txt_employee_telephoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_employee_telephoneActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_employee_telephoneActionPerformed

    private void txt_employee_emailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_employee_emailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_employee_emailActionPerformed

    private void btn_register_supplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_register_supplierActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_register_supplierActionPerformed

    private void txt_supplier_nameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_supplier_nameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_supplier_nameActionPerformed

    private void txt_supplier_emailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_supplier_emailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_supplier_emailActionPerformed

    private void txt_supplier_idActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_supplier_idActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_supplier_idActionPerformed

    private void txt_supplier_descriptionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_supplier_descriptionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_supplier_descriptionActionPerformed

    private void txt_search_supplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_search_supplierActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_search_supplierActionPerformed

    private void btn_update_categoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_update_categoryActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_update_categoryActionPerformed

    private void txt_category_idActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_category_idActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_category_idActionPerformed

    private void btn_delete_categoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_delete_categoryActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_delete_categoryActionPerformed

    private void txt_customer_addressActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_customer_addressActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_customer_addressActionPerformed

    private void txt_customer_idActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_customer_idActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_customer_idActionPerformed

    private void cmb_rolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmb_rolActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmb_rolActionPerformed

    private void btn_modify_dataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_modify_dataActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_modify_dataActionPerformed

    private void txt_id_profileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_id_profileActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_id_profileActionPerformed

    private void txt_address_profileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_address_profileActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_address_profileActionPerformed

    private void txt_phone_profileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_phone_profileActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_phone_profileActionPerformed

    private void txt_email_profileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_email_profileActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_email_profileActionPerformed

    private void btn_confirm_saleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_confirm_saleActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_confirm_saleActionPerformed

    private void btn_new_saleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_new_saleActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_new_saleActionPerformed

    private void txt_sale_product_codeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_sale_product_codeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_sale_product_codeActionPerformed

    private void txt_sale_total_to_payActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_sale_total_to_payActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_sale_total_to_payActionPerformed

    private void txt_sale_priceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_sale_priceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_sale_priceActionPerformed

    private void btn_activate_productActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_activate_productActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_activate_productActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
        Models.Employees guest = new Models.Employees();
        guest.setRol("administrador");
        new SystemView(guest).setVisible(true);
    });
}   
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Cabecera;
    private javax.swing.JPanel Logo;
    private javax.swing.JPanel Menu;
    public javax.swing.JButton btn_LoginOut;
    public javax.swing.JButton btn_activate_product;
    public javax.swing.JButton btn_add_product_sale;
    public javax.swing.JButton btn_add_product_to_buy;
    public javax.swing.JButton btn_cancel_customer;
    public javax.swing.JButton btn_cancel_employee;
    public javax.swing.JButton btn_cancel_product;
    public javax.swing.JButton btn_cancel_supplier;
    public javax.swing.JButton btn_confirm_purchase;
    public javax.swing.JButton btn_confirm_sale;
    public javax.swing.JButton btn_delete_category;
    public javax.swing.JButton btn_delete_customer;
    public javax.swing.JButton btn_delete_employee;
    public javax.swing.JButton btn_delete_product;
    public javax.swing.JButton btn_delete_supplier;
    public javax.swing.JButton btn_modify_data;
    public javax.swing.JButton btn_new_purchase;
    public javax.swing.JButton btn_new_sale;
    private javax.swing.JButton btn_photo;
    public javax.swing.JButton btn_register_category;
    public javax.swing.JButton btn_register_customer;
    public javax.swing.JButton btn_register_employee;
    public javax.swing.JButton btn_register_product;
    public javax.swing.JButton btn_register_supplier;
    public javax.swing.JButton btn_remove_purchase;
    public javax.swing.JButton btn_remove_sale;
    public javax.swing.JButton btn_update_category;
    public javax.swing.JButton btn_update_customer;
    public javax.swing.JButton btn_update_employee;
    public javax.swing.JButton btn_update_product;
    public javax.swing.JButton btn_update_supplier;
    public javax.swing.JTable categories_table;
    public javax.swing.JComboBox<String> cmb_purchase_supplier;
    public javax.swing.JComboBox<String> cmb_rol;
    public javax.swing.JComboBox<String> cmb_supplier_city;
    public javax.swing.JComboBox<Object> cmd_categories;
    public javax.swing.JTable custormers_table;
    public javax.swing.JTable employees_table;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    public javax.swing.JLabel jLabelCategories;
    public javax.swing.JLabel jLabelCustomers;
    public javax.swing.JLabel jLabelEmployees;
    public javax.swing.JLabel jLabelProducts;
    public javax.swing.JLabel jLabelPurchases;
    public javax.swing.JLabel jLabelReports;
    public javax.swing.JLabel jLabelSales;
    public javax.swing.JLabel jLabelSettings;
    public javax.swing.JLabel jLabelSupplimers;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    public javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel2;
    public javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    public javax.swing.JPanel jPanelCategories;
    public javax.swing.JPanel jPanelCustomers;
    public javax.swing.JPanel jPanelEmployees;
    public javax.swing.JPanel jPanelProducts;
    public javax.swing.JPanel jPanelPurchases;
    public javax.swing.JPanel jPanelReports;
    public javax.swing.JPanel jPanelSales;
    public javax.swing.JPanel jPanelSettings;
    public javax.swing.JPanel jPanelSupplimers;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    public javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable2;
    public javax.swing.JTable products_table;
    public javax.swing.JTable purchases_table;
    public javax.swing.JTable sales_table;
    public javax.swing.JTable suppliers_table;
    public javax.swing.JTable table_all_purchases;
    public javax.swing.JTable table_all_sales;
    public javax.swing.JTextField txt_address_profile;
    public javax.swing.JTextField txt_category_id;
    public javax.swing.JTextField txt_category_name;
    public javax.swing.JTextField txt_customer_address;
    public javax.swing.JTextField txt_customer_email;
    public javax.swing.JTextField txt_customer_fullname;
    public javax.swing.JTextField txt_customer_id;
    public javax.swing.JTextField txt_customer_telephone;
    public javax.swing.JTextField txt_email_profile;
    public javax.swing.JTextField txt_employee_address;
    public javax.swing.JTextField txt_employee_email;
    public javax.swing.JTextField txt_employee_fullname;
    public javax.swing.JTextField txt_employee_id;
    public javax.swing.JPasswordField txt_employee_password;
    public javax.swing.JTextField txt_employee_telephone;
    public javax.swing.JTextField txt_employee_username;
    public javax.swing.JTextField txt_id_profile;
    public javax.swing.JTextField txt_name_profile;
    public javax.swing.JPasswordField txt_password_modify;
    public javax.swing.JPasswordField txt_password_modify_confirm;
    public javax.swing.JTextField txt_phone_profile;
    public javax.swing.JTextField txt_product_code;
    public javax.swing.JTextField txt_product_description;
    public javax.swing.JTextField txt_product_id;
    public javax.swing.JTextField txt_product_name;
    public javax.swing.JTextField txt_product_unit_price;
    public javax.swing.JTextField txt_purchase_amount;
    public javax.swing.JTextField txt_purchase_id;
    public javax.swing.JTextField txt_purchase_price;
    public javax.swing.JTextField txt_purchase_product_code;
    public javax.swing.JTextField txt_purchase_product_name;
    public javax.swing.JTextField txt_purchase_subtotal;
    public javax.swing.JTextField txt_purchase_total_to_pay;
    public javax.swing.JTextField txt_sale_customer_id;
    public javax.swing.JTextField txt_sale_customer_name;
    public javax.swing.JTextField txt_sale_price;
    public javax.swing.JTextField txt_sale_product_code;
    public javax.swing.JTextField txt_sale_product_id;
    public javax.swing.JTextField txt_sale_product_name;
    public javax.swing.JTextField txt_sale_quantity;
    public javax.swing.JTextField txt_sale_stock;
    public javax.swing.JTextField txt_sale_subtotal;
    public javax.swing.JTextField txt_sale_total_to_pay;
    public javax.swing.JTextField txt_search_category;
    public javax.swing.JTextField txt_search_customers;
    public javax.swing.JTextField txt_search_employee;
    public javax.swing.JTextField txt_search_product;
    public javax.swing.JTextField txt_search_supplier;
    public javax.swing.JTextField txt_supplier_addres;
    public javax.swing.JTextField txt_supplier_description;
    public javax.swing.JTextField txt_supplier_email;
    public javax.swing.JTextField txt_supplier_id;
    public javax.swing.JTextField txt_supplier_name;
    public javax.swing.JTextField txt_supplier_telephone;
    // End of variables declaration//GEN-END:variables

    public class jLabelSettings {

        public jLabelSettings() {
        }
    }
}
