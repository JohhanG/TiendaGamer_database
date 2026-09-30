package Controllers;

import Models.Employees;
import Models.EmployeesDao;
import Views.SystemView;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.border.AbstractBorder;

public class SettingsControllers {

    private SystemView views;
    private Employees loggedEmployee;
    private EmployeesDao employeesDao;

    private final Color TEXT_NORMAL = new Color(203, 213, 225); // Slate 300
    private final Color TEXT_ACTIVE = Color.WHITE;
    private final Color TEXT_LOCKED = new Color(148, 163, 184, 90);

    private javax.swing.JPanel activePanel = null;
    private JLabel activeLabel = null;

    public static final int TAB_PURCHASES  = 0;
    public static final int TAB_SALES      = 1;
    public static final int TAB_CUSTOMERS  = 2;
    public static final int TAB_EMPLOYEES  = 3;
    public static final int TAB_SUPPLIERS  = 4;
    public static final int TAB_CATEGORIES = 5;
    public static final int TAB_REPORTS    = 6;
    public static final int TAB_SETTINGS   = 7;
    public static final int TAB_PRODUCTS   = 8;
    public static final int TAB_KARDEX     = 9;

    public SettingsControllers(SystemView views, Employees loggedEmployee) {
        this.views           = views;
        this.loggedEmployee  = loggedEmployee;
        this.employeesDao    = new EmployeesDao();

        // 1. Configurar navegación de todos los 10 módulos con control RBAC (Figura 2 / Pág. 11)
        addNavListener(views.jPanelProducts,   views.jLabelProducts,   TAB_PRODUCTS);
        addNavListener(views.jPanelPurchases,  views.jLabelPurchases,  TAB_PURCHASES);
        addNavListener(views.jPanelSales,      views.jLabelSales,      TAB_SALES);
        addNavListener(views.jPanelKardex,     views.jLabelKardex,     TAB_KARDEX);
        addNavListener(views.jPanelCustomers,  views.jLabelCustomers,  TAB_CUSTOMERS);
        addNavListener(views.jPanelEmployees,  views.jLabelEmployees,  TAB_EMPLOYEES);
        addNavListener(views.jPanelSupplimers, views.jLabelSupplimers, TAB_SUPPLIERS);
        addNavListener(views.jPanelCategories, views.jLabelCategories, TAB_CATEGORIES);
        addNavListener(views.jPanelReports,    views.jLabelReports,    TAB_REPORTS);
        addNavListener(views.jPanelSettings,   views.jLabelSettings,   TAB_SETTINGS);

        // 2. Determinar pestaña inicial según el rol del usuario logueado
        int initialTab = TAB_PRODUCTS;
        javax.swing.JPanel initialPanel = views.jPanelProducts;
        JLabel initialLabel = views.jLabelProducts;

        if (isJefeCompras()) {
            initialTab = TAB_PURCHASES;
            initialPanel = views.jPanelPurchases;
            initialLabel = views.jLabelPurchases;
        } else if (isAlmacenista()) {
            initialTab = TAB_KARDEX;
            initialPanel = views.jPanelKardex;
            initialLabel = views.jLabelKardex;
        } else if (isVendedor()) {
            initialTab = TAB_SALES;
            initialPanel = views.jPanelSales;
            initialLabel = views.jLabelSales;
        }

        views.jTabbedPane1.setSelectedIndex(initialTab);
        setActive(initialPanel, initialLabel);

        // 3. Bloqueos de acciones para usuarios no administradores (modo solo lectura para catálogo)
        if (!isAdmin()) {
            lockProductButtons();
            lockSalaryButtons();
        }

        // 4. Cargar datos del perfil al iniciar
        loadProfileData();

        // 5. Botón Modificar — solo cambia la contraseña
        views.btn_modify_data.addActionListener((ActionEvent e) -> updatePassword());
    }

    // Normalizar rol del usuario
    public String getRoleNormalized() {
        if (loggedEmployee == null || loggedEmployee.getRol() == null) return "vendedor";
        String r = loggedEmployee.getRol().toLowerCase().trim();
        if (r.contains("admin") || r.contains("gerente")) return "admin";
        if (r.contains("compra")) return "compras";
        if (r.contains("almacen") || r.contains("bodega")) return "almacen";
        return "vendedor";
    }

    public boolean isAdmin() {
        return "admin".equals(getRoleNormalized());
    }

    public boolean isJefeCompras() {
        return "compras".equals(getRoleNormalized());
    }

    public boolean isAlmacenista() {
        return "almacen".equals(getRoleNormalized());
    }

    public boolean isVendedor() {
        return "vendedor".equals(getRoleNormalized());
    }

    // Compatibilidad con código previo
    public boolean isAuxiliar() {
        return !isAdmin();
    }

    // Matriz Oficial RBAC (Figura 2 / Pág. 11 del Proyecto)
    public boolean isTabAllowed(int tabIndex) {
        if (isAdmin()) {
            return true; // Administrador General: acceso total
        }

        if (isJefeCompras()) {
            // Jefe de Compras: Proveedores, Compras, Detalle, Productos (Catálogo), Reportes (Compras), Perfil
            return tabIndex == TAB_PURCHASES
                || tabIndex == TAB_SUPPLIERS
                || tabIndex == TAB_PRODUCTS
                || tabIndex == TAB_REPORTS
                || tabIndex == TAB_SETTINGS;
        }

        if (isAlmacenista()) {
            // Almacenista / Bodega: Kardex, Ajuste Físico, Productos (Catálogo), Compras (Recepción), Categorías, Perfil
            return tabIndex == TAB_KARDEX
                || tabIndex == TAB_PRODUCTS
                || tabIndex == TAB_PURCHASES
                || tabIndex == TAB_CATEGORIES
                || tabIndex == TAB_SETTINGS;
        }

        if (isVendedor()) {
            // Vendedor / Cajero / Auxiliar: Ventas, Clientes, Productos (Precios/Stock), Perfil
            return tabIndex == TAB_SALES
                || tabIndex == TAB_CUSTOMERS
                || tabIndex == TAB_PRODUCTS
                || tabIndex == TAB_SETTINGS;
        }

        return false;
    }

    // Carga los datos del empleado logueado en los campos de perfil
    private void loadProfileData() {
        try {
            views.txt_id_profile.setText(
                    String.valueOf(loggedEmployee.getId()));
            views.txt_name_profile.setText(
                    loggedEmployee.getFull_name());
            views.txt_address_profile.setText(
                    loggedEmployee.getAddress());
            views.txt_phone_profile.setText(
                    loggedEmployee.getTelephone());
            views.txt_email_profile.setText(
                    loggedEmployee.getEmail());
        } catch (Exception e) {
            System.out.println("Error cargando perfil: " + e.getMessage());
        }
    }

    // Solo actualiza la contraseña
    private void updatePassword() {
        String newPass     = new String(
                views.txt_password_modify.getPassword()).trim();
        String confirmPass = new String(
                views.txt_password_modify_confirm.getPassword()).trim();

        if (newPass.isEmpty() || confirmPass.isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "Ingresa y confirma la nueva contraseña");
            return;
        }

        if (!newPass.equals(confirmPass)) {
            JOptionPane.showMessageDialog(null,
                    "Las contraseñas no coinciden");
            return;
        }

        if (newPass.length() < 4) {
            JOptionPane.showMessageDialog(null,
                    "La contraseña debe tener al menos 4 caracteres");
            return;
        }

        loggedEmployee.setPassword(newPass);
        boolean ok = employeesDao.updateEmployeePassword(loggedEmployee);

        if (ok) {
            JOptionPane.showMessageDialog(null,
                    "Contraseña actualizada correctamente");
            views.txt_password_modify.setText("");
            views.txt_password_modify_confirm.setText("");
        } else {
            JOptionPane.showMessageDialog(null,
                    "Error al actualizar la contraseña");
        }
    }

    private AbstractBorder activeBorder() {
        return new AbstractBorder() {
            @Override
            public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                // Fondo píldora activo índigo moderno
                g2.setColor(new Color(79, 70, 229)); // #4F46E5
                g2.fillRoundRect(x + 1, y + 1, w - 2, h - 2, 14, 14);
                // Borde suave
                g2.setColor(new Color(165, 180, 252, 180));
                g2.drawRoundRect(x + 1, y + 1, w - 3, h - 3, 14, 14);
                // Barra indicadora activa lateral luminosa
                g2.setColor(Color.WHITE);
                g2.fillRoundRect(x + 6, y + 10, 4, h - 20, 4, 4);
                g2.dispose();
            }

            @Override public Insets getBorderInsets(Component c) { return new Insets(0, 0, 0, 0); }
            @Override public Insets getBorderInsets(Component c, Insets insets) { insets.set(0, 0, 0, 0); return insets; }
        };
    }

    private AbstractBorder hoverBorder() {
        return new AbstractBorder() {
            @Override
            public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                // Fondo translúcido sutil al pasar el mouse
                g2.setColor(new Color(255, 255, 255, 25));
                g2.fillRoundRect(x + 1, y + 1, w - 2, h - 2, 14, 14);
                // Borde suave
                g2.setColor(new Color(255, 255, 255, 50));
                g2.drawRoundRect(x + 1, y + 1, w - 3, h - 3, 14, 14);
                g2.dispose();
            }

            @Override public Insets getBorderInsets(Component c) { return new Insets(0, 0, 0, 0); }
            @Override public Insets getBorderInsets(Component c, Insets insets) { insets.set(0, 0, 0, 0); return insets; }
        };
    }

    private void addNavListener(javax.swing.JPanel panel, JLabel label, int tabIndex) {
        if (panel == null || label == null) return;
        boolean allowed = isTabAllowed(tabIndex);

        if (!allowed) {
            lockItem(panel, label);
        }

        MouseAdapter adapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!isTabAllowed(tabIndex)) {
                    showAccessDenied();
                    return;
                }
                views.jTabbedPane1.setSelectedIndex(tabIndex);
                setActive(panel, label);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if (!isTabAllowed(tabIndex)) return;
                panel.setCursor(new Cursor(Cursor.HAND_CURSOR));
                label.setCursor(new Cursor(Cursor.HAND_CURSOR));
                if (panel != activePanel) {
                    panel.setBorder(hoverBorder());
                    label.setForeground(Color.WHITE);
                    panel.repaint();
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (!isTabAllowed(tabIndex)) return;
                java.awt.Point p = javax.swing.SwingUtilities.convertPoint((Component) e.getSource(), e.getPoint(), panel);
                if (!panel.contains(p)) {
                    if (panel != activePanel) {
                        panel.setBorder(null);
                        label.setForeground(TEXT_NORMAL);
                        panel.repaint();
                    }
                }
            }
        };

        panel.addMouseListener(adapter);
        label.addMouseListener(adapter);
    }

    public void setActive(javax.swing.JPanel panel, JLabel label) {
        if (panel == null && label == null) return;

        if (activePanel != null) {
            activePanel.setBorder(null);
            activePanel.repaint();
        }
        if (activeLabel != null) {
            activeLabel.setForeground(TEXT_NORMAL);
            activeLabel.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 13));
            activeLabel.repaint();
        }

        activePanel = panel;
        activeLabel = label;

        if (activePanel != null) {
            activePanel.setBorder(activeBorder());
            activePanel.repaint();
        }
        if (activeLabel != null) {
            activeLabel.setForeground(TEXT_ACTIVE);
            activeLabel.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 13));
            activeLabel.repaint();
        }
    }

    public void setActive(JLabel label) {
        setActive(null, label);
    }

    private void lockItem(javax.swing.JPanel panel, JLabel label) {
        if (label != null) {
            label.setForeground(TEXT_LOCKED);
            label.setEnabled(false);
            label.setToolTipText("Módulo restringido para tu perfil");
            label.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
        if (panel != null) {
            panel.setToolTipText("Módulo restringido para tu perfil");
            panel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            panel.repaint();
        }
    }

    private void lockProductButtons() {
        if (views.btn_register_product != null) {
            views.btn_register_product.setEnabled(false);
            views.btn_register_product.setToolTipText("Solo Administrador (Catálogo en solo lectura)");
        }
        if (views.btn_update_product != null) {
            views.btn_update_product.setEnabled(false);
            views.btn_update_product.setToolTipText("Solo Administrador (Catálogo en solo lectura)");
        }
        if (views.btn_delete_product != null) {
            views.btn_delete_product.setEnabled(false);
            views.btn_delete_product.setToolTipText("Solo Administrador (Catálogo en solo lectura)");
        }
        if (views.btn_activate_product != null) {
            views.btn_activate_product.setEnabled(false);
            views.btn_activate_product.setToolTipText("Solo Administrador (Catálogo en solo lectura)");
        }
    }

    private void lockSalaryButtons() {
        if (views.btn_edit_salary != null) {
            views.btn_edit_salary.setEnabled(false);
            views.btn_edit_salary.setToolTipText("Solo Administrador General");
        }
        if (views.txt_employee_salary != null) {
            views.txt_employee_salary.setEnabled(false);
            views.txt_employee_salary.setToolTipText("Solo Administrador General");
        }
    }

    private void showAccessDenied() {
        String rolActual = (loggedEmployee != null && loggedEmployee.getRol() != null)
                ? loggedEmployee.getRol() : "Usuario";
        JOptionPane.showMessageDialog(
                views,
                "Acceso Restringido:\nTu perfil actual ('" + rolActual + "') no tiene permisos autorizados\n" +
                "para acceder a este módulo según la matriz de seguridad RBAC del sistema.",
                "Acceso Denegado",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
}