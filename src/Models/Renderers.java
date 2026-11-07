package Models;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * Renderer visual para la tabla de Productos.
 * Aplica alertas visuales segun RF-002 y RF-007:
 * - Productos inactivos: Fondo rojo suave.
 * - Alerta de stock critico (stock <= 5 o agotado): Fondo amarillo / ambar suave.
 * - Productos normales activos: Fondo alternado limpio (zebra striping).
 * - Alineacion inteligente por tipo de columna (ID/Cod/Cant/Estado centrados, Precio derecha).
 */
public class Renderers extends DefaultTableCellRenderer {

    private static final Font FONT_NORMAL = new Font("Segoe UI", Font.PLAIN, 13);
    private static final Font FONT_BOLD   = new Font("Segoe UI", Font.BOLD, 13);

    private static final Color COLOR_BG_EVEN   = Color.WHITE;
    private static final Color COLOR_BG_ODD    = new Color(248, 250, 252); // Slate 50
    private static final Color COLOR_FG_NORMAL = new Color(30, 41, 59);    // Slate 800

    private static final Color COLOR_SEL_BG = new Color(224, 231, 255); // Indigo 100
    private static final Color COLOR_SEL_FG = new Color(30, 27, 75);    // Indigo 950

    private static final Color COLOR_INACTIVE_BG = new Color(254, 226, 226); // Red 100
    private static final Color COLOR_INACTIVE_FG = new Color(153, 27, 27);    // Red 800

    private static final Color COLOR_OUT_STOCK_BG = new Color(254, 202, 202); // Red 200
    private static final Color COLOR_OUT_STOCK_FG = new Color(185, 28, 28);    // Red 700

    private static final Color COLOR_LOW_STOCK_BG = new Color(254, 243, 199); // Amber 100
    private static final Color COLOR_LOW_STOCK_FG = new Color(146, 64, 14);    // Amber 800

    @Override
    public Component getTableCellRendererComponent(
            JTable table,
            Object value,
            boolean isSelected,
            boolean hasFocus,
            int row,
            int column) {

        Component cell = super.getTableCellRendererComponent(
                table, value, isSelected, hasFocus, row, column);

        if (cell instanceof JLabel) {
            JLabel lbl = (JLabel) cell;
            lbl.setFont(FONT_NORMAL);
            lbl.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 8));

            // Alineacion por columna:
            // 0: Id, 1: Codigo, 5: Cantidad, 7: Status -> Centrados
            // 4: Precio -> Derecha
            // 2: Nombre, 3: Descripcion, 6: Categoria -> Izquierda
            if (column == 0 || column == 1 || column == 5 || column == 7) {
                lbl.setHorizontalAlignment(SwingConstants.CENTER);
            } else if (column == 4) {
                lbl.setHorizontalAlignment(SwingConstants.RIGHT);
            } else {
                lbl.setHorizontalAlignment(SwingConstants.LEFT);
            }

            if (column == 7) {
                lbl.setFont(FONT_BOLD);
            }
        }

        try {
            int statusColumn = 7; // Columna "Estado"
            int stockColumn  = 5; // Columna "Cantidad"

            Object statusValue = table.getValueAt(row, statusColumn);
            Object stockValue  = table.getValueAt(row, stockColumn);

            int stock = 999;
            if (stockValue != null) {
                try {
                    stock = Integer.parseInt(stockValue.toString().trim());
                } catch (NumberFormatException ignored) {}
            }

            boolean isInactive = (statusValue != null && statusValue.toString().equalsIgnoreCase("Inactivo"));

            if (isInactive) {
                cell.setBackground(COLOR_INACTIVE_BG);
                cell.setForeground(COLOR_INACTIVE_FG);
            } else if (stock <= 0) {
                cell.setBackground(COLOR_OUT_STOCK_BG);
                cell.setForeground(COLOR_OUT_STOCK_FG);
            } else if (stock <= 5) {
                cell.setBackground(COLOR_LOW_STOCK_BG);
                cell.setForeground(COLOR_LOW_STOCK_FG);
            } else {
                // Zebra striping
                cell.setBackground(row % 2 == 0 ? COLOR_BG_EVEN : COLOR_BG_ODD);
                cell.setForeground(COLOR_FG_NORMAL);
            }

            if (isSelected) {
                cell.setBackground(COLOR_SEL_BG);
                cell.setForeground(COLOR_SEL_FG);
            }

        } catch (Exception e) {
            cell.setBackground(Color.WHITE);
            cell.setForeground(Color.BLACK);
        }

        return cell;
    }
}
