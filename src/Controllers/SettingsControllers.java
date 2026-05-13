package Controllers;

import Models.Employees;
import Views.SystemView;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.border.AbstractBorder;

public class SettingsControllers {

    private SystemView views;
    private Employees loggedEmployee;

    private final Color TEXT_NORMAL = Color.WHITE;
    private final Color TEXT_ACTIVE = Color.WHITE;
    private final Color TEXT_LOCKED = new Color(255, 255, 255, 80);
    private final Color COLOR_ACTIVE = new Color(255, 255, 255, 60);
    private final Color COLOR_HOVER  = new Color(255, 255, 255, 30);

    private JLabel activeLabel = null;

    // Orden real de las pestañas del JTabbedPane
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
        this.views          = views;
        this.loggedEmployee = loggedEmployee;

        // Mostrar Productos por defecto
        views.jTabbedPane1.setSelectedIndex(TAB_PRODUCTS);
        setActive(views.jLabelProducts);

        addNavListener(views.jLabelProducts,   TAB_PRODUCTS,   false);
        addNavListener(views.jLabelPurchases,  TAB_PURCHASES,  false);
        addNavListener(views.jLabelSales,      TAB_SALES,      false);
        addNavListener(views.jLabelCustomers,  TAB_CUSTOMERS,  false);
        addNavListener(views.jLabelEmployees,  TAB_EMPLOYEES,  true);
        addNavListener(views.jLabelSupplimers, TAB_SUPPLIERS,  true);
        addNavListener(views.jLabelCategories, TAB_CATEGORIES, true);
        addNavListener(views.jLabelReports,    TAB_REPORTS,    false);
        addNavListener(views.jLabelSettings,   TAB_SETTINGS,   false);

        if (isAuxiliar()) {
            lockLabel(views.jLabelEmployees);
            lockLabel(views.jLabelSupplimers);
            lockLabel(views.jLabelCategories);
            lockProductButtons();
        }
    }

    private AbstractBorder roundBorder(Color fillColor) {
        return new AbstractBorder() {
            @Override
            public void paintBorder(Component c, Graphics g,
                                    int x, int y, int w, int h) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(fillColor);
                g2.fillRoundRect(x + 2, y + 2, w - 4, h - 4, 14, 14);
                g2.dispose();
            }

            @Override
            public Insets getBorderInsets(Component c) {
                return new Insets(0, 0, 0, 0);
            }

            @Override
            public Insets getBorderInsets(Component c, Insets insets) {
                insets.set(0, 0, 0, 0);
                return insets;
            }
        };
    }

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
                    label.setBorder(roundBorder(COLOR_HOVER));
                    label.repaint();
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (adminOnly && isAuxiliar()) return;
                if (label != activeLabel) {
                    label.setBorder(null);
                    label.repaint();
                }
            }
        });
    }

    private void setActive(JLabel label) {
        if (activeLabel != null) {
            activeLabel.setBorder(null);
            activeLabel.setForeground(TEXT_NORMAL);
            activeLabel.repaint();
        }
        activeLabel = label;
        activeLabel.setBorder(roundBorder(COLOR_ACTIVE));
        activeLabel.setForeground(TEXT_ACTIVE);
        activeLabel.repaint();
    }

    private void lockLabel(JLabel label) {
        label.setForeground(TEXT_LOCKED);
        label.setEnabled(false);
        label.setToolTipText("Solo Administrador");
        label.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        label.repaint();
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