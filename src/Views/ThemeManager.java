package Views;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

public class ThemeManager {

    private static boolean darkMode = false;

    // Guarda los colores originales antes del primer cambio
    private static final Map<String, Color> originalColors = new HashMap<>();

    private static final Color DARK_BG        = new Color(60, 63, 65);
    private static final Color DARK_FIELD_BG  = new Color(69, 73, 74);
    private static final Color DARK_FG        = Color.WHITE;
    private static final Color DARK_GRID      = new Color(80, 80, 80);
    private static final Color DARK_SEL_BG    = new Color(75, 110, 175);
    private static final Color DARK_HEADER_BG = new Color(50, 52, 54);
    private static final Color DARK_MENU_BG   = new Color(24, 24, 38);
    private static final Color DARK_CAB_BG    = new Color(30, 27, 75);

    public static void apply(JFrame frame, boolean dark) {
        darkMode = dark;
        try {
            // Guardar colores originales la primera vez
            if (originalColors.isEmpty()) {
                saveOriginalColors(frame);
            }

            if (dark) {
                UIManager.setLookAndFeel(new FlatDarkLaf());
            } else {
                UIManager.setLookAndFeel(new FlatLightLaf());
            }

            SwingUtilities.updateComponentTreeUI(frame);
            fixComponents(frame, dark);

            frame.setSize(1208, 680);
            frame.revalidate();
            frame.repaint();

        } catch (Exception e) {
            System.out.println("Error al cambiar tema: " + e.getMessage());
        }
    }

    // Guarda colores originales de paneles con nombre
    private static void saveOriginalColors(Container container) {
        for (Component c : container.getComponents()) {
            if (c instanceof JPanel) {
                String name = ((JPanel) c).getName();
                if (name != null && !name.isEmpty()) {
                    originalColors.put(name, c.getBackground());
                }
            }
            if (c instanceof Container) {
                saveOriginalColors((Container) c);
            }
        }
    }

    private static void fixComponents(Container container, boolean dark) {
        for (Component c : container.getComponents()) {

            // Campos de texto
            if (c instanceof JTextField || c instanceof JPasswordField) {
                if (dark) {
                    c.setBackground(DARK_FIELD_BG);
                    c.setForeground(DARK_FG);
                } else {
                    c.setBackground(Color.WHITE);
                    c.setForeground(Color.BLACK);
                }
            }

            // ComboBox
            if (c instanceof JComboBox) {
                if (dark) {
                    c.setBackground(DARK_FIELD_BG);
                    c.setForeground(DARK_FG);
                } else {
                    c.setBackground(Color.WHITE);
                    c.setForeground(Color.BLACK);
                }
            }

            // Tablas
            if (c instanceof JTable) {
                JTable table = (JTable) c;
                if (dark) {
                    table.setBackground(DARK_BG);
                    table.setForeground(DARK_FG);
                    table.setGridColor(DARK_GRID);
                    table.setSelectionForeground(DARK_FG);
                    table.setSelectionBackground(DARK_SEL_BG);
                    DefaultTableCellRenderer r = new DefaultTableCellRenderer();
                    r.setBackground(DARK_BG);
                    r.setForeground(DARK_FG);
                    for (int i = 0; i < table.getColumnCount(); i++)
                        table.getColumnModel().getColumn(i).setCellRenderer(r);
                    JTableHeader h = table.getTableHeader();
                    if (h != null) { h.setBackground(DARK_HEADER_BG); h.setForeground(DARK_FG); }
                } else {
                    table.setBackground(null); table.setForeground(null);
                    table.setGridColor(new Color(200, 200, 200));
                    table.setSelectionForeground(null); table.setSelectionBackground(null);
                    DefaultTableCellRenderer r = new DefaultTableCellRenderer();
                    r.setBackground(null); r.setForeground(null);
                    for (int i = 0; i < table.getColumnCount(); i++)
                        table.getColumnModel().getColumn(i).setCellRenderer(r);
                    JTableHeader h = table.getTableHeader();
                    if (h != null) { h.setBackground(null); h.setForeground(null); }
                }
            }

            // Paneles
            if (c instanceof JPanel) {
                JPanel panel = (JPanel) c;
                String name = panel.getName();

                if (name != null && name.equals("Logo")) {
                    // Logo nunca se toca

                } else if (name != null && name.equals("Cabecera")) {
                    if (dark) {
                        panel.setBackground(DARK_CAB_BG);
                    } else {
                        // Restaurar color morado/índigo original guardado
                        Color orig = originalColors.get("Cabecera");
                        panel.setBackground(orig != null ? orig : new Color(79, 70, 229));
                    }

                } else if (name != null && name.equals("Menu")) {
                    if (dark) {
                        panel.setBackground(DARK_MENU_BG);
                    } else {
                        // Restaurar color original guardado
                        Color orig = originalColors.get("Menu");
                        panel.setBackground(orig != null ? orig : new Color(24, 24, 38));
                    }

                } else {
                    if (dark) {
                        panel.setBackground(DARK_BG);
                    } else {
                        panel.setBackground(null);
                    }
                }
            }

            if (c instanceof Container) {
                fixComponents((Container) c, dark);
            }
        }
    }

    public static boolean isDark() {
        return darkMode;
    }
}