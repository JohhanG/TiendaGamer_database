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
 * Renderer visual generico y moderno para todas las tablas del sistema.
 * Aplica:
 * - Zebra striping elegante (filas pares e impares).
 * - Alineacion inteligente automatica segun el encabezado (Centro, Derecha o Izquierda).
 * - Margen interior de 8px para una lectura comoda.
 * - Resaltado visual para estados (Activo, Inactivo, Completada, Cancelada, etc.).
 */
public class GenericTableRenderer extends DefaultTableCellRenderer {

    private static final Font FONT_NORMAL = new Font("Segoe UI", Font.PLAIN, 13);
    private static final Font FONT_BOLD   = new Font("Segoe UI", Font.BOLD, 13);

    private static final Color COLOR_BG_EVEN   = Color.WHITE;
    private static final Color COLOR_BG_ODD    = new Color(248, 250, 252); // Slate 50
    private static final Color COLOR_FG_NORMAL = new Color(30, 41, 59);    // Slate 800

    private static final Color COLOR_SEL_BG = new Color(224, 231, 255); // Indigo 100
    private static final Color COLOR_SEL_FG = new Color(30, 27, 75);    // Indigo 950

    private static final Color COLOR_SUCCESS = new Color(5, 150, 105);   // Emerald 600
    private static final Color COLOR_DANGER  = new Color(225, 29, 72);   // Rose 600

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

            String colName = "";
            try {
                colName = table.getColumnName(column).toLowerCase();
            } catch (Exception ignored) {}

            // 1. Alineacion inteligente segun encabezado
            if (isCenterColumn(colName)) {
                lbl.setHorizontalAlignment(SwingConstants.CENTER);
            } else if (isRightColumn(colName)) {
                lbl.setHorizontalAlignment(SwingConstants.RIGHT);
            } else {
                lbl.setHorizontalAlignment(SwingConstants.LEFT);
            }

            // 2. Colores de fila y seleccion
            if (isSelected) {
                lbl.setBackground(COLOR_SEL_BG);
                lbl.setForeground(COLOR_SEL_FG);
            } else {
                lbl.setBackground(row % 2 == 0 ? COLOR_BG_EVEN : COLOR_BG_ODD);
                lbl.setForeground(COLOR_FG_NORMAL);
            }

            // 3. Resaltado de estados
            String strVal = (value != null) ? value.toString().trim() : "";
            if (isSuccessStatus(strVal, colName)) {
                lbl.setFont(FONT_BOLD);
                if (!isSelected) {
                    lbl.setForeground(COLOR_SUCCESS);
                }
            } else if (isDangerStatus(strVal, colName)) {
                lbl.setFont(FONT_BOLD);
                if (!isSelected) {
                    lbl.setForeground(COLOR_DANGER);
                }
            }
        }

        return cell;
    }

    private boolean isCenterColumn(String col) {
        return col.contains("id")
                || col.contains("codigo")
                || col.contains("código")
                || col.contains("cantidad")
                || col.contains("cant")
                || col.contains("telefono")
                || col.contains("teléfono")
                || col.contains("fecha")
                || col.contains("factura")
                || col.contains("rol")
                || col.contains("estado")
                || col.contains("status")
                || col.contains("ciudad");
    }

    private boolean isRightColumn(String col) {
        return col.contains("precio")
                || col.contains("subtotal")
                || col.contains("total")
                || col.contains("sueldo")
                || col.contains("monto")
                || col.contains("valor");
    }

    private boolean isSuccessStatus(String val, String col) {
        String upper = val.toUpperCase();
        return upper.equals("ACTIVO")
                || upper.equals("COMPLETADA")
                || upper.equals("ENTRADA")
                || upper.equals("PAGADO")
                || upper.equals("EMITIDA")
                || upper.equals("1");
    }

    private boolean isDangerStatus(String val, String col) {
        String upper = val.toUpperCase();
        return upper.equals("INACTIVO")
                || upper.equals("CANCELADA")
                || upper.equals("ANULADA")
                || upper.equals("SALIDA")
                || upper.equals("PENDIENTE")
                || upper.equals("0");
    }
}
