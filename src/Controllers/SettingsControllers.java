package Controllers;

import Models.Employees;
import Views.SystemView;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.border.AbstractBorder;

public class SettingsControllers {

    private SystemView views;
    private Employees loggedEmployee;

    private final Color COLOR_ACTIVE_BG = new Color(220, 218, 255);
    private final Color TEXT_NORMAL     = Color.WHITE;
    private final Color TEXT_ACTIVE     = new Color(60, 50, 180);
    private final Color TEXT_LOCKED     = new Color(255, 255, 255, 60);

    private JLabel activeLabel = null;

    private static final int TAB_PRODUCTS   = 0;
    private static final int TAB_PURCHASES  = 1;
    private static final int TAB_SALES      = 2;
    private static final int TAB_CUSTOMERS  = 3;
    private static final int TAB_EMPLOYEES  = 4;
     private static final int TAB_SUPPLIERS  = 5;
    private static final int TAB_CATEGORIES = 6;  
    private static final int TAB_REPORTS    = 7;
    private static final int TAB_SETTINGS   = 8;

    public SettingsControllers(SystemView views, Employees loggedEmployee) {
        this.views          = views;
        this.loggedEmployee = loggedEmployee;

        views.jTabbedPane1.setSelectedIndex(TAB_PRODUCTS);
        setActive(views.jLabelProducts);

        addNavListener(views.jLabelProducts,   TAB_PRODUCTS,   false);
        addNavListener(views.jLabelPurchases,  TAB_PURCHASES,  false);
        addNavListener(views.jLabelSales,      TAB_SALES,      false);
        addNavListener(views.jLabelCustomers,  TAB_CUSTOMERS,  false);
        addNavListener(views.jLabelEmployees,  TAB_EMPLOYEES,  true);
        addNavListener(views.jLabelSupplimers, TAB_SUPPLIERS,  true);  // ✅
        addNavListener(views.jLabelCategories, TAB_CATEGORIES, true);  // ✅
        addNavListener(views.jLabelReports,    TAB_REPORTS,    false);
        addNavListener(views.jLabelSettings,   TAB_SETTINGS,   false);

        // Bloquear visualmente si es auxiliar
        if (isAuxiliar()) {
            lockLabel(views.jLabelEmployees);
            lockLabel(views.jLabelSupplimers);
            lockLabel(views.jLabelCategories);
            lockProductButtons();
        }
    }

    // =====================================
    // BLOQUEAR BOTONES DE PRODUCTOS
    // =====================================
    private void lockProductButtons() {
        views.btn_register_product.setEnabled(false);
        views.btn_update_product.setEnabled(false);
        views.btn_delete_product.setEnabled(false);
        views.btn_activate_product.setEnabled(false);

        Color gris = new Color(180, 180, 180);
        views.btn_register_product.setBackground(gris);
        views.btn_update_product.setBackground(gris);
        views.btn_delete_product.setBackground(gris);
        views.btn_activate_product.setBackground(gris);

        Cursor def = new Cursor(Cursor.DEFAULT_CURSOR);
        views.btn_register_product.setCursor(def);
        views.btn_update_product.setCursor(def);
        views.btn_delete_product.setCursor(def);
        views.btn_activate_product.setCursor(def);
    }

    // =====================================
    // BLOQUEAR LABEL — texto apagado
    // =====================================
    private void lockLabel(JLabel label) {
        label.setForeground(TEXT_LOCKED);
        label.setEnabled(false);
        label.setToolTipText("Solo Administrador");
        label.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        label.repaint();
    }

    // =====================================
    // HOVER
    // =====================================
    private AbstractBorder createHoverBorder() {
        return new AbstractBorder() {
            @Override
            public void paintBorder(Component c, Graphics g,
                                    int x, int y, int w, int h) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(255, 255, 255, 70));
                g2.fillRoundRect(x, y, w, h, 12, 12);
                g2.dispose();
            }
        };
    }

    // =====================================
    // REGISTRAR EVENTOS NAV
    // =====================================
    private void addNavListener(JLabel label, int tabIndex, boolean adminOnly) {
        label.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if (adminOnly && isAuxiliar()) {
                    showAccessDenied();
                    return;
                }
                views.jTabbedPane1.setSelectedIndex(tabIndex);
                setActive(label);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if (adminOnly && isAuxiliar()) return;
                if (label != activeLabel) {
                    label.setBorder(createHoverBorder());
                    label.repaint();
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (adminOnly && isAuxiliar()) return;
                if (label != activeLabel) {
                    label.setBorder(null);
                    label.setOpaque(false);
                    label.repaint();
                }
            }
        });
    }

    // =====================================
    // MARCAR LABEL ACTIVO
    // =====================================
    private void setActive(JLabel label) {
        if (activeLabel != null) {
            activeLabel.setBorder(null);
            activeLabel.setOpaque(false);
            activeLabel.setForeground(TEXT_NORMAL);
            activeLabel.repaint();
        }
        activeLabel = label;
        activeLabel.setBorder(null);
        activeLabel.setOpaque(true);
        activeLabel.setBackground(COLOR_ACTIVE_BG);
        activeLabel.setForeground(TEXT_ACTIVE);
        activeLabel.repaint();
    }

    // =====================================
    // VERIFICAR ROL
    // =====================================
    public boolean isAuxiliar() {
        return loggedEmployee.getRol().equalsIgnoreCase("auxiliar");
    }

    // =====================================
    // MENSAJE ACCESO DENEGADO
    // =====================================
    private void showAccessDenied() {
        JOptionPane.showMessageDialog(
                null,
                "No tienes permisos de Administrador",
                "Message",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
}