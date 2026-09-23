package Controllers;

import Models.Employees;
import Models.EmployeesDao;
import Models.Purchases;
import Models.PurchasesDao;
import Models.Sales;
import Models.SalesDao;
import Views.SystemView;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

public class ReportsController implements MouseListener {

    private SystemView views;
    private SalesDao saleDao;
    private PurchasesDao purchaseDao;
    private EmployeesDao employeesDao;

    private ProductsController productsController;
    private Employees loggedEmployee;
    private EmployeesController employeeController;

    DefaultTableModel salesModel;
    DefaultTableModel purchasesModel;
    DefaultTableModel salariesModel;

    // ✅ NUEVO: guardamos la última lista cargada de cada reporte, para poder
    // buscar el motivo de cancelación de una fila sin volver a consultar la
    // base de datos cada vez que hacen clic.
    private List<Sales> salesList;
    private List<Purchases> purchasesList;

    public ReportsController(SystemView views, ProductsController productsController) {
        this(views, productsController, null, null);
    }

    public ReportsController(SystemView views, ProductsController productsController,
                             Employees loggedEmployee, EmployeesController employeeController) {

        this.views              = views;
        this.saleDao            = new SalesDao();
        this.purchaseDao        = new PurchasesDao();
        this.employeesDao       = new EmployeesDao();
        this.productsController = productsController;
        this.loggedEmployee     = loggedEmployee;
        this.employeeController = employeeController;

        salesModel     = (DefaultTableModel) views.table_all_sales.getModel();
        purchasesModel = (DefaultTableModel) views.table_all_purchases.getModel();

        // Escuchar clics en las tablas para procesar/consultar devoluciones y salarios
        views.table_all_sales.addMouseListener(this);
        views.table_all_purchases.addMouseListener(this);

        if (views.table_employee_salaries != null) {
            views.table_employee_salaries.addMouseListener(this);
        }

        if (views.btn_edit_salary != null) {
            views.btn_edit_salary.addActionListener(e -> modifySelectedEmployeeSalary());
        }

        loadSales();
        loadPurchases();
        loadEmployeeSalaries();
    }

    // =========================================
    // CARGAR VENTAS EN LA TABLA
    // =========================================
    public void loadSales() {

        salesModel.setColumnIdentifiers(new Object[]{
            "Factura Venta", "Cliente", "Empleado", "Total", "Fecha de Venta", "Estado"
        });
        if (views.table_all_sales.getTableHeader() != null) {
            views.table_all_sales.getTableHeader().setReorderingAllowed(false);
        }

        salesModel.setRowCount(0);

        salesList = saleDao.listAllSalesQuery(); // ✅ ahora se guarda en el campo de la clase

        for (Sales s : salesList) {
            salesModel.addRow(new Object[]{
                s.getId(),
                s.getCustomer_name(),
                s.getEmployee_name(),
                String.format("%.2f", s.getTotal()),
                s.getSale_date(),
                s.getEstado()
            });
        }
    }

    // =========================================
    // CARGAR COMPRAS EN LA TABLA
    // =========================================
    public void loadPurchases() {

        purchasesModel.setColumnIdentifiers(new Object[]{
            "Factura", "Proveedor", "Total de Compra", "Fecha de Compra", "Estado"
        });
        if (views.table_all_purchases.getTableHeader() != null) {
            views.table_all_purchases.getTableHeader().setReorderingAllowed(false);
        }

        purchasesModel.setRowCount(0);

        purchasesList = purchaseDao.listAllPurchasesQuery(); // ✅ ahora se guarda en el campo de la clase

        for (Purchases p : purchasesList) {
            purchasesModel.addRow(new Object[]{
                p.getId(),
                p.getSupplier_name(),
                String.format("%.2f", p.getTotal()),
                p.getCreated(),
                p.getEstado()
            });
        }
    }

    // =========================================
    // CARGAR SUELDOS DE EMPLEADOS (NÓMINA)
    // =========================================
    public void loadEmployeeSalaries() {
        if (views.table_employee_salaries == null) return;

        salariesModel = (DefaultTableModel) views.table_employee_salaries.getModel();
        salariesModel.setColumnIdentifiers(new Object[]{
            "Identificación", "Nombre Completo", "Usuario", "Rol", "Teléfono", "Correo Electrónico", "Sueldo Asignado"
        });
        if (views.table_employee_salaries.getTableHeader() != null) {
            views.table_employee_salaries.getTableHeader().setReorderingAllowed(false);
        }

        salariesModel.setRowCount(0);

        List<Employees> list = employeesDao.listEmployeesQuery("");
        double totalSalaries = 0.0;

        for (Employees emp : list) {
            totalSalaries += emp.getSalary();
            salariesModel.addRow(new Object[]{
                emp.getId(),
                emp.getFull_name(),
                emp.getUsername(),
                emp.getRol(),
                emp.getTelephone(),
                emp.getEmail(),
                String.format("$ %,.2f", emp.getSalary())
            });
        }

        if (views.lbl_total_salaries != null) {
            views.lbl_total_salaries.setText(String.format("$ %,.2f", totalSalaries));
        }
        if (views.lbl_count_salaries != null) {
            views.lbl_count_salaries.setText(list.size() + " Empleados");
        }
    }

    // =========================================
    // ACCIÓN POR DOBLE CLIC EN LAS TABLAS
    // =========================================
    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) {
            if (e.getSource() == views.table_all_sales) {
                cancelSelectedSale();
            } else if (e.getSource() == views.table_all_purchases) {
                cancelSelectedPurchase();
            } else if (e.getSource() == views.table_employee_salaries) {
                modifySelectedEmployeeSalary();
            }
        }
    }

    // =========================================
    // MODIFICAR SUELDO (SOLO ADMINISTRADOR)
    // =========================================
    public void modifySelectedEmployeeSalary() {
        // 1. Validar que el usuario actual sea Administrador
        boolean isAdmin = loggedEmployee != null
                && "administrador".equalsIgnoreCase(loggedEmployee.getRol());

        if (!isAdmin) {
            JOptionPane.showMessageDialog(
                    views,
                    "Acceso Denegado:\nSolo los usuarios con rol de Administrador pueden modificar los salarios.",
                    "Permiso Insuficiente",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        // 2. Validar que haya un empleado seleccionado en la tabla
        int row = views.table_employee_salaries.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(
                    views,
                    "Por favor selecciona un empleado de la tabla de nómina para modificar su sueldo.",
                    "Ningún Empleado Seleccionado",
                    JOptionPane.INFORMATION_MESSAGE
            );
            return;
        }

        Object idVal = salariesModel.getValueAt(row, 0);
        Object nameVal = salariesModel.getValueAt(row, 1);
        Object salVal = salariesModel.getValueAt(row, 6);
        if (idVal == null) return;

        int empId = Integer.parseInt(idVal.toString());
        String empName = nameVal != null ? nameVal.toString() : "";
        String currentSalaryStr = salVal != null ? salVal.toString()
                .replace("$", "").replace(" ", "").replace(".", "").replace(",", ".") : "0";

        double currentSalary = 0.0;
        try {
            currentSalary = Double.parseDouble(currentSalaryStr);
        } catch (NumberFormatException ignored) {}

        // 3. Solicitar nuevo salario
        String input = (String) JOptionPane.showInputDialog(
                views,
                "Empleado: " + empName + " (ID: " + empId + ")\n\n"
                        + "Ingrese el nuevo sueldo mensual ($):",
                "Modificar Sueldo de Empleado",
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                String.format(java.util.Locale.US, "%.2f", currentSalary)
        );

        if (input == null) return; // Canceló

        input = input.trim().replace("$", "").replace(" ", "").replace(",", ".");
        if (input.isEmpty()) {
            JOptionPane.showMessageDialog(views, "Debes ingresar un valor numérico.", "Campo Requerido", JOptionPane.WARNING_MESSAGE);
            return;
        }

        double newSalary;
        try {
            newSalary = Double.parseDouble(input);
            if (newSalary < 0) {
                JOptionPane.showMessageDialog(views, "El sueldo no puede ser negativo.", "Valor Inválido", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(views, "Ingresa un número válido para el sueldo (ej. 2500000 o 2500000.50).", "Formato Inválido", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 4. Confirmación
        int confirm = JOptionPane.showConfirmDialog(
                views,
                "¿Confirmas actualizar el sueldo de \"" + empName + "\" a $ " + String.format(java.util.Locale.US, "%,.2f", newSalary) + "?\n"
                        + "Esto actualizará inmediatamente el total de nómina.",
                "Confirmar Modificación de Sueldo",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (confirm == JOptionPane.YES_OPTION) {
            if (employeesDao.updateSalary(empId, newSalary)) {
                JOptionPane.showMessageDialog(
                        views,
                        "¡Sueldo actualizado exitosamente!\n\n"
                                + "Empleado: " + empName + "\n"
                                + "Nuevo Sueldo: $ " + String.format(java.util.Locale.US, "%,.2f", newSalary),
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE
                );

                // ✅ Recargar tabla y actualizar total nómina en tiempo real
                loadEmployeeSalaries();

                // Si está sincronizado con EmployeesController, actualizar tabla general
                if (employeeController != null) {
                    employeeController.listAllEmployees();
                }
            } else {
                JOptionPane.showMessageDialog(views, "Error al guardar el nuevo sueldo en la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // =========================================
    // DEVOLUCIÓN DE CLIENTE (CANCELAR VENTA)
    // =========================================
    private void cancelSelectedSale() {
        int row = views.table_all_sales.getSelectedRow();
        if (row == -1) return;

        Object idVal = salesModel.getValueAt(row, 0);
        Object stVal = salesModel.getValueAt(row, 5);
        if (idVal == null) return;

        int saleId = Integer.parseInt(idVal.toString());
        String estado = stVal != null ? stVal.toString() : "";

        // ✅ NUEVO: si ya está cancelada, mostrar el banner con el detalle
        // en vez del mensaje genérico de antes.
        if ("CANCELADA".equalsIgnoreCase(estado)) {
            Sales sale = findSaleById(saleId);
            if (sale != null) {
                showReturnBanner(
                        "DEVOLUCIÓN DE CLIENTE",
                        sale.getId(),
                        "Cliente",
                        sale.getCustomer_name(),
                        sale.getSale_date(),
                        sale.getTotal(),
                        sale.getMotivoCancelacion()
                );
            }
            return;
        }

        // Pedir el motivo antes de confirmar
        String motivo = JOptionPane.showInputDialog(
                null,
                "Escribe el motivo de la devolución del cliente:",
                "Motivo de la Devolución",
                JOptionPane.QUESTION_MESSAGE
        );

        if (motivo == null) return; // canceló el diálogo
        if (motivo.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debes escribir un motivo para continuar.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                null,
                "¿Deseas procesar la devolución de la venta N° " + saleId + "?\n"
                        + "Esto actualizará el estado y reintegrará el stock.",
                "Confirmar Devolución de Cliente",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            if (saleDao.cancelSaleQuery(saleId, motivo.trim())) {
                JOptionPane.showMessageDialog(null, "Venta cancelada correctamente.");
                loadSales();

                if (productsController != null) {
                    productsController.refreshTable();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Error al procesar la devolución.");
            }
        }
    }

    // =========================================
    // DEVOLUCIÓN A PROVEEDOR (CANCELAR COMPRA)
    // =========================================
    private void cancelSelectedPurchase() {
        int row = views.table_all_purchases.getSelectedRow();
        if (row == -1) return;

        Object idVal = purchasesModel.getValueAt(row, 0);
        Object stVal = purchasesModel.getValueAt(row, 4);
        if (idVal == null) return;

        int purchaseId = Integer.parseInt(idVal.toString());
        String estado = stVal != null ? stVal.toString() : "";

        // ✅ NUEVO: si ya está cancelada, mostrar el banner con el detalle
        if ("CANCELADA".equalsIgnoreCase(estado)) {
            Purchases purchase = findPurchaseById(purchaseId);
            if (purchase != null) {
                showReturnBanner(
                        "DEVOLUCIÓN A PROVEEDOR",
                        purchase.getId(),
                        "Proveedor",
                        purchase.getSupplier_name(),
                        purchase.getCreated(),
                        purchase.getTotal(),
                        purchase.getMotivoCancelacion()
                );
            }
            return;
        }

        // Pedir el motivo antes de confirmar
        String motivo = JOptionPane.showInputDialog(
                null,
                "Escribe el motivo de la devolución al proveedor:",
                "Motivo de la Devolución",
                JOptionPane.QUESTION_MESSAGE
        );

        if (motivo == null) return; // canceló el diálogo
        if (motivo.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debes escribir un motivo para continuar.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                null,
                "¿Deseas procesar la devolución al proveedor de la compra N° " + purchaseId + "?\n"
                        + "Esto actualizará el estado y descontará el stock del almacén.",
                "Confirmar Devolución a Proveedor",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            if (purchaseDao.cancelPurchaseQuery(purchaseId, motivo.trim())) {
                JOptionPane.showMessageDialog(null, "Compra cancelada correctamente.");
                loadPurchases();

                if (productsController != null) {
                    productsController.refreshTable();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Error al procesar la devolución.");
            }
        }
    }

    // =========================================
    // BUSCAR EN LAS LISTAS YA CARGADAS (sin ir a la BD)
    // =========================================
    private Sales findSaleById(int id) {
        if (salesList == null) return null;
        for (Sales s : salesList) {
            if (s.getId() == id) return s;
        }
        return null;
    }

    private Purchases findPurchaseById(int id) {
        if (purchasesList == null) return null;
        for (Purchases p : purchasesList) {
            if (p.getId() == id) return p;
        }
        return null;
    }

    // =========================================
    // BANNER DE DETALLE DE LA DEVOLUCIÓN
    // =========================================
    private void showReturnBanner(String tipo, int folio, String contraparteLabel,
                                   String contraparte, String fecha, double total, String motivo) {

        JPanel panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(new Dimension(380, 210));
        panel.setBackground(java.awt.Color.WHITE);

        JLabel header = new JLabel(tipo, SwingConstants.CENTER);
        header.setOpaque(true);
        header.setBackground(new Color(198, 40, 40)); // rojo
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Tahoma", Font.BOLD, 15));
        header.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String motivoTexto = (motivo == null || motivo.trim().isEmpty())
                ? "<i>No se especificó un motivo.</i>"
                : motivo.replace("\n", "<br>");

        String html = "<html><div style='padding:14px; font-family:Tahoma; font-size:12px;'>"
                + "<b>Factura N&deg;:</b> " + folio + "<br>"
                + "<b>" + contraparteLabel + ":</b> " + contraparte + "<br>"
                + "<b>Fecha:</b> " + fecha + "<br>"
                + "<b>Total:</b> $" + String.format("%.2f", total) + "<br><br>"
                + "<b>Motivo de la devolución:</b><br>" + motivoTexto
                + "</div></html>";

        JLabel content = new JLabel(html);

        panel.add(header, BorderLayout.NORTH);
        panel.add(content, BorderLayout.CENTER);

        JOptionPane.showMessageDialog(null, panel, "Detalle de la Devolución", JOptionPane.PLAIN_MESSAGE);
    }

    @Override public void mousePressed(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
}