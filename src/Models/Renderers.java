package Models;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

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

            // 🔴 columna donde está el estado (ajusta si cambia)
            int statusColumn = 7;

            Object statusValue = table.getValueAt(row, statusColumn);

            if (statusValue != null && statusValue.toString().equalsIgnoreCase("Inactivo")) {

                // 🔴 fila inactiva
                cell.setBackground(new Color(255, 200, 200)); // rojo suave
                cell.setForeground(Color.BLACK);

            } else {

                // 🟢 fila activa
                cell.setBackground(Color.WHITE);
                cell.setForeground(Color.BLACK);
            }

            // 🔵 mantener selección visible
            if (isSelected) {
                cell.setBackground(new Color(184, 207, 229));
            }

        } catch (Exception e) {
            // evita que se caiga la tabla por errores de índice
            cell.setBackground(Color.WHITE);
        }

        return cell;
    }
}