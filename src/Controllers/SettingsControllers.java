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

    private static final int TAB_PURCHASES  = 0;
    private static final int TAB_SALES      = 1;
    private static final int TAB_CUSTOMERS  = 2;
    private static final int TAB_EMPLOYEES  = 3;
    private static final int TAB_SUPPLIERS  = 4;
    private static final int TAB_CATEGORIES = 5;
    private static final int TAB_REPORTS    = 6;
    private static final int TAB_SETTINGS   = 7;
    private static final int TAB_PRODUCTS   = 8;

    public SettingsControllers(SystemView views, Employees loggedEmployee) {
        this.views           = views;
        this.loggedEmployee  = loggedEmployee;
        this.employeesDao    = new EmployeesDao();

        views.jTabbedPane1.setSelectedIndex(TAB_PRODUCTS);
        setActive(views.jPanelProducts, views.jLabelProducts);

        addNavListener(views.jPanelProducts,   views.jLabelProducts,   TAB_PRODUCTS,   false);
        addNavListener(views.jPanelPurchases,  views.jLabelPurchases,  TAB_PURCHASES,  false);
        addNavListener(views.jPanelSales,      views.jLabelSales,      TAB_SALES,      false);
        addNavListener(views.jPanelCustomers,  views.jLabelCustomers,  TAB_CUSTOMERS,  false);
        addNavListener(views.jPanelEmployees,  views.jLabelEmployees,  TAB_EMPLOYEES,  true);
        addNavListener(views.jPanelSupplimers, views.jLabelSupplimers, TAB_SUPPLIERS,  true);
        addNavListener(views.jPanelCategories, views.jLabelCategories, TAB_CATEGORIES, true);
        addNavListener(views.jPanelReports,    views.jLabelReports,    TAB_REPORTS,    false);
        addNavListener(views.jPanelSettings,   views.jLabelSettings,   TAB_SETTINGS,   false);

        if (isAuxiliar()) {
            lockItem(views.jPanelEmployees, views.jLabelEmployees);
            lockItem(views.jPanelSupplimers, views.jLabelSupplimers);
            lockItem(views.jPanelCategories, views.jLabelCategories);
            lockProductButtons();
        }

        // ✅ Cargar datos del perfil al iniciar
        loadProfileData();

        // ✅ Botón Modificar — solo cambia la contraseña
        views.btn_modify_data.addActionListener((ActionEvent e) -> updatePassword());
    }

    // ✅ Carga los datos del empleado logueado en los campos de perfil
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

    // ✅ Solo actualiza la contraseña
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

        // ✅ Usa el método existente en EmployeesDao
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

    private void addNavListener(javax.swing.JPanel panel, JLabel label, int tabIndex, boolean adminOnly) {
        MouseAdapter adapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (adminOnly && isAuxiliar()) {
                    showAccessDenied();
                    return;
                }
                views.jTabbedPane1.setSelectedIndex(tabIndex);
                setActive(panel, label);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if (adminOnly && isAuxiliar()) return;
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
                if (adminOnly && isAuxiliar()) return;
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
        label.setForeground(TEXT_LOCKED);
        label.setEnabled(false);
        label.setToolTipText("Solo Administrador");
        panel.setToolTipText("Solo Administrador");
        label.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        panel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        panel.repaint();
    }

    private void lockLabel(JLabel label) {
        lockItem(null, label);
    }

    private void lockProductButtons() {
        views.btn_register_product.setEnabled(false);
        views.btn_update_product.setEnabled(false);
        views.btn_delete_product.setEnabled(false);
        views.btn_activate_product.setEnabled(false);
    }

    public boolean isAuxiliar() {
        return loggedEmployee.getRol().equalsIgnoreCase("auxiliar");
    }

    private void showAccessDenied() {
        JOptionPane.showMessageDialog(
                null,
                "No tienes permisos de Administrador",
                "Acceso denegado",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
}