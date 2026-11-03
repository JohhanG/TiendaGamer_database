package Models;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * Renderer visual para la tabla de Productos.
 * Aplica alertas visuales según RF-002 y RF-007:
 * - Productos inactivos: Fondo rojo suave.
 * - Alerta de stock crítico (stock <= 5 o agotado): Fondo amarillo / ámbar suave.
 * - Productos normales activos: Fondo blanco / alternado limpio.
 */
public class Renderers extends DefaultTableCellRenderer {

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

        try {
            int statusColumn = 7; // Columna "Estado"
            int stockColumn  = 5; // Columna "Cantidad"

            Object statusValue = table.getValueAt(row, statusColumn);
            Object stockValue  = table.getValueAt(row, stockColumn);

            int stock = 999;
            if (stockValue != null) {
                try {
                    stock = Integer.parseInt(stockValue.toString().trim());
                } catch (NumberFormatException ignored) {
                }
            }

            boolean isInactive = (statusValue != null && statusValue.toString().equalsIgnoreCase("Inactivo"));

            if (isInactive) {
                // Producto dado de baja lógica (RF-002)
                cell.setBackground(new Color(254, 226, 226)); // Rojo suave #fee2e2
                cell.setForeground(new Color(153, 27, 27));    // Texto vino tinto
            } else if (stock <= 0) {
                // Stock agotado (Alerta crítica RF-007)
                cell.setBackground(new Color(254, 202, 202)); // Rojo pálido #fecaca
                cell.setForeground(new Color(185, 28, 28));
            } else if (stock <= 5) {
                // Stock mínimo de advertencia (RF-007)
                cell.setBackground(new Color(254, 243, 199)); // Ámbar suave #fef3c7
                cell.setForeground(new Color(146, 64, 14));    // Texto ámbar oscuro
            } else {
                // Estado normal
                cell.setBackground(Color.WHITE);
                cell.setForeground(new Color(30, 41, 59));
            }

            // Mantener visibilidad del registro seleccionado
            if (isSelected) {
                cell.setBackground(new Color(199, 210, 254)); // Indigo claro #c7d2fe
                cell.setForeground(new Color(30, 27, 75));
            }

        } catch (Exception e) {
            cell.setBackground(Color.WHITE);
            cell.setForeground(Color.BLACK);
        }

        return cell;
    }
}
