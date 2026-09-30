package Controllers;

import Models.Employees;
import Models.EmployeesDao;
import Models.Kardex;
import Models.KardexDao;
import Models.Products;
import Models.ProductsDao;
import Views.SystemView;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.DefaultTableModel;

public class KardexController implements ActionListener, KeyListener {

    private final KardexDao kardexDao;
    private final ProductsDao productsDao;
    private final SystemView views;
    private final Employees loggedEmployee;
    private final ProductsController productsController;
    private final SettingsControllers settingsController;

    private DefaultTableModel model;

    public KardexController(KardexDao kardexDao, SystemView views,
                            Employees loggedEmployee,
                            ProductsController productsController,
                            SettingsControllers settingsController) {
        this.kardexDao = kardexDao;
        this.productsDao = new ProductsDao();
        this.views = views;
        this.loggedEmployee = loggedEmployee;
        this.productsController = productsController;
        this.settingsController = settingsController;

        initEvents();
        cargarTablaKardex();
    }

    private void initEvents() {
        if (views.kardex_table != null) {
            model = (DefaultTableModel) views.kardex_table.getModel();
        }
        if (views.btn_refresh_kardex != null) {
            views.btn_refresh_kardex.addActionListener(this);
        }
        if (views.btn_ajuste_inventario != null) {
            views.btn_ajuste_inventario.addActionListener(this);
        }
        if (views.txt_search_kardex != null) {
            views.txt_search_kardex.addKeyListener(this);
        }
        if (views.cmb_filter_kardex != null) {
            views.cmb_filter_kardex.addActionListener(e -> cargarTablaKardex());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == views.btn_refresh_kardex) {
            cargarTablaKardex();
        } else if (e.getSource() == views.btn_ajuste_inventario) {
            verificarYAbrirAjusteInventario();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getSource() == views.txt_search_kardex) {
            cargarTablaKardex();
        }
    }

    @Override public void keyTyped(KeyEvent e) {}
    @Override public void keyPressed(KeyEvent e) {}

    public void cargarTablaKardex() {
        if (views.kardex_table == null || model == null) return;

        model.setRowCount(0);

        String search = (views.txt_search_kardex != null) ? views.txt_search_kardex.getText().trim() : "";
        String tipoFiltro = "Todos";
        if (views.cmb_filter_kardex != null && views.cmb_filter_kardex.getSelectedItem() != null) {
            tipoFiltro = views.cmb_filter_kardex.getSelectedItem().toString();
            if (tipoFiltro.toLowerCase().contains("todos")) {
                tipoFiltro = "Todos";
            }
        }

        List<Kardex> lista = kardexDao.listarTodoElKardex(search, tipoFiltro);

        int totalMovimientos = lista.size();
        int totalEntradas = 0;
        int totalSalidas = 0;
        int totalAjustes = 0;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        for (Kardex k : lista) {
            String efecto = k.getEfecto() != null ? k.getEfecto().toUpperCase() : "SALIDA";
            String tipo = k.getTipoMovNombre() != null ? k.getTipoMovNombre().toUpperCase() : "";

            if ("ENTRADA".equalsIgnoreCase(efecto)) {
                totalEntradas++;
            } else if ("SALIDA".equalsIgnoreCase(efecto)) {
                totalSalidas++;
            }

            if (tipo.contains("AJUSTE") || "AJUSTE".equalsIgnoreCase(efecto)) {
                totalAjustes++;
            }

            String fechaStr = (k.getFecha() != null) ? sdf.format(k.getFecha()) : "";
            String codigoStr = (k.getProductCode() > 0) ? String.valueOf(k.getProductCode()) : ("#" + k.getIdProducto());

            Object[] row = {
                k.getIdKardex(),
                fechaStr,
                codigoStr,
                k.getProductName(),
                k.getTipoMovNombre(),
                k.getEmployeeName(),
                k.getCantidad(),
                efecto,
                k.getSaldoAnterior(),
                k.getSaldoResultante(),
                k.getObservacion()
            };
            model.addRow(row);
        }

        // Actualizar tarjetas de resumen si existen
        if (views.lbl_kardex_total_mov != null) {
            views.lbl_kardex_total_mov.setText(String.valueOf(totalMovimientos));
        }
        if (views.lbl_kardex_entradas != null) {
            views.lbl_kardex_entradas.setText(String.valueOf(totalEntradas));
        }
        if (views.lbl_kardex_salidas != null) {
            views.lbl_kardex_salidas.setText(String.valueOf(totalSalidas));
        }
        if (views.lbl_kardex_ajustes != null) {
            views.lbl_kardex_ajustes.setText(String.valueOf(totalAjustes));
        }
    }

    public void verificarYAbrirAjusteInventario() {
        // Validación de RBAC (Figura 2 / Pág. 11):
        // Solo el Gerente/Administrador y el Almacenista/Encargado de Bodega pueden registrar ajustes.
        boolean permitido = false;
        if (settingsController != null) {
            permitido = settingsController.isAdmin() || settingsController.isAlmacenista();
        } else if (loggedEmployee != null && loggedEmployee.getRol() != null) {
            String r = loggedEmployee.getRol().toLowerCase();
            permitido = r.contains("admin") || r.contains("gerente") || r.contains("almacen") || r.contains("bodega");
        }

        if (!permitido) {
            JOptionPane.showMessageDialog(
                views,
                "Acceso Restringido: Únicamente el Administrador General o el Almacenista / Encargado de Bodega\n" +
                "están autorizados para registrar ajustes de inventario físico.",
                "Permisos Insuficientes",
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        abrirDialogoAjusteInventario();
    }

    private void abrirDialogoAjusteInventario() {
        JDialog dialog = new JDialog(views, "Ajuste por Inventario Físico de Bodega", true);
        dialog.setSize(530, 520);
        dialog.setLocationRelativeTo(views);
        dialog.setResizable(false);
        dialog.setLayout(new BorderLayout());

        JPanel pnlMain = new JPanel(new GridBagLayout());
        pnlMain.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));
        pnlMain.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Título informativo
        JLabel lblHeader = new JLabel("<html><b>Registro de Auditoría y Ajuste de Stock</b><br>"
                + "<span style='font-size:10px; color:#64748B;'>Permite regularizar discrepancias detectadas en conteos físicos de bodega.</span></html>");
        lblHeader.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        pnlMain.add(lblHeader, gbc);

        // Selector de Producto
        JLabel lblProd = new JLabel("Producto:");
        lblProd.setFont(new Font("Segoe UI", Font.BOLD, 12));
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 1;
        pnlMain.add(lblProd, gbc);

        JComboBox<ProductItem> cmbProducts = new JComboBox<>();
        List<Products> productList = productsDao.listProductsQuery("");
        for (Products p : productList) {
            if (p.getStatus() == 1) {
                cmbProducts.addItem(new ProductItem(p));
            }
        }
        gbc.gridx = 1; gbc.gridy = 1;
        pnlMain.add(cmbProducts, gbc);

        // Stock actual (solo lectura)
        JLabel lblStockActualTitle = new JLabel("Stock en Sistema:");
        lblStockActualTitle.setFont(new Font("Segoe UI", Font.BOLD, 12));
        gbc.gridx = 0; gbc.gridy = 2;
        pnlMain.add(lblStockActualTitle, gbc);

        JLabel lblStockActualVal = new JLabel("0 unidades");
        lblStockActualVal.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblStockActualVal.setForeground(new Color(30, 41, 59));
        gbc.gridx = 1; gbc.gridy = 2;
        pnlMain.add(lblStockActualVal, gbc);

        // Modalidad de ajuste (Radio Buttons)
        JLabel lblModo = new JLabel("Modo de Ajuste:");
        lblModo.setFont(new Font("Segoe UI", Font.BOLD, 12));
        gbc.gridx = 0; gbc.gridy = 3;
        pnlMain.add(lblModo, gbc);

        JPanel pnlRadios = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        pnlRadios.setOpaque(false);
        JRadioButton rbConteo = new JRadioButton("Conteo Físico Real", true);
        JRadioButton rbDiferencia = new JRadioButton("Diferencia (+ / -)");
        ButtonGroup bgModo = new ButtonGroup();
        bgModo.add(rbConteo);
        bgModo.add(rbDiferencia);
        pnlRadios.add(rbConteo);
        pnlRadios.add(rbDiferencia);
        gbc.gridx = 1; gbc.gridy = 3;
        pnlMain.add(pnlRadios, gbc);

        // Valor a ingresar
        JLabel lblValorInput = new JLabel("Nuevo Stock Físico:");
        lblValorInput.setFont(new Font("Segoe UI", Font.BOLD, 12));
        gbc.gridx = 0; gbc.gridy = 4;
        pnlMain.add(lblValorInput, gbc);

        JSpinner spnValor = new JSpinner(new SpinnerNumberModel(0, -99999, 99999, 1));
        spnValor.setFont(new Font("Segoe UI", Font.BOLD, 13));
        gbc.gridx = 1; gbc.gridy = 4;
        pnlMain.add(spnValor, gbc);

        // Resultado calculado
        JLabel lblResTitle = new JLabel("Stock Resultante:");
        lblResTitle.setFont(new Font("Segoe UI", Font.BOLD, 12));
        gbc.gridx = 0; gbc.gridy = 5;
        pnlMain.add(lblResTitle, gbc);

        JLabel lblResVal = new JLabel("0 unidades (Diferencia: 0)");
        lblResVal.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblResVal.setForeground(new Color(79, 70, 229));
        gbc.gridx = 1; gbc.gridy = 5;
        pnlMain.add(lblResVal, gbc);

        // Motivo / Justificación obligatoria
        JLabel lblMotivo = new JLabel("Motivo / Justificación:");
        lblMotivo.setFont(new Font("Segoe UI", Font.BOLD, 12));
        gbc.gridx = 0; gbc.gridy = 6;
        pnlMain.add(lblMotivo, gbc);

        JTextField txtMotivo = new JTextField();
        txtMotivo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txtMotivo.setToolTipText("Ej: Conteo físico general, Unidades dañadas en bodega, Descuadre de inventario");
        gbc.gridx = 1; gbc.gridy = 6;
        pnlMain.add(txtMotivo, gbc);

        // Lógica de cálculo dinámico
        Runnable actualizarCalculo = () -> {
            ProductItem item = (ProductItem) cmbProducts.getSelectedItem();
            if (item == null) return;
            int stockActual = item.product.getProduct_quantity();
            lblStockActualVal.setText(stockActual + " unidades");

            int inputVal = ((Number) spnValor.getValue()).intValue();
            int nuevoStock;
            int diferencia;

            if (rbConteo.isSelected()) {
                lblValorInput.setText("Nuevo Stock Físico:");
                nuevoStock = Math.max(0, inputVal);
                diferencia = nuevoStock - stockActual;
            } else {
                lblValorInput.setText("Diferencia (+ / -):");
                diferencia = inputVal;
                nuevoStock = stockActual + diferencia;
            }

            String efectoText = (diferencia > 0) ? "ENTRADA (+)" : (diferencia < 0 ? "SALIDA (-)" : "SIN CAMBIO");
            lblResVal.setText(nuevoStock + " unidades (" + efectoText + " " + Math.abs(diferencia) + ")");
            if (nuevoStock < 0) {
                lblResVal.setForeground(Color.RED);
            } else {
                lblResVal.setForeground(new Color(79, 70, 229));
            }
        };

        cmbProducts.addActionListener(e -> {
            ProductItem item = (ProductItem) cmbProducts.getSelectedItem();
            if (item != null && rbConteo.isSelected()) {
                spnValor.setValue(item.product.getProduct_quantity());
            }
            actualizarCalculo.run();
        });

        rbConteo.addActionListener(e -> {
            ProductItem item = (ProductItem) cmbProducts.getSelectedItem();
            if (item != null) {
                spnValor.setValue(item.product.getProduct_quantity());
            }
            actualizarCalculo.run();
        });

        rbDiferencia.addActionListener(e -> {
            spnValor.setValue(0);
            actualizarCalculo.run();
        });

        spnValor.addChangeListener(e -> actualizarCalculo.run());

        // Inicializar con el producto seleccionado
        if (cmbProducts.getItemCount() > 0) {
            ProductItem first = cmbProducts.getItemAt(0);
            spnValor.setValue(first.product.getProduct_quantity());
            actualizarCalculo.run();
        }

        // Panel de botones abajo
        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 12));
        pnlButtons.setBackground(new Color(248, 250, 252));
        pnlButtons.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(226, 232, 240)));

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btnCancelar.addActionListener(e -> dialog.dispose());

        JButton btnGuardar = new JButton("Guardar Ajuste");
        btnGuardar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnGuardar.setBackground(new Color(79, 70, 229));
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setFocusPainted(false);

        btnGuardar.addActionListener(e -> {
            ProductItem item = (ProductItem) cmbProducts.getSelectedItem();
            if (item == null) {
                JOptionPane.showMessageDialog(dialog, "Selecciona un producto", "Validación", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int stockActual = item.product.getProduct_quantity();
            int inputVal = ((Number) spnValor.getValue()).intValue();
            int nuevoStock = rbConteo.isSelected() ? inputVal : (stockActual + inputVal);

            if (nuevoStock < 0) {
                JOptionPane.showMessageDialog(dialog, "El stock resultante no puede ser negativo (" + nuevoStock + ").", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String motivo = txtMotivo.getText().trim();
            if (motivo.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Debe ingresar una justificación o motivo para el ajuste físico.", "Campo Obligatorio", JOptionPane.WARNING_MESSAGE);
                txtMotivo.requestFocus();
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(
                dialog,
                "¿Confirmas el ajuste de inventario para '" + item.product.getName() + "'?\n" +
                "Stock anterior: " + stockActual + "\n" +
                "Nuevo stock resultante: " + nuevoStock + "\n" +
                "Motivo: " + motivo,
                "Confirmación de Ajuste",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
            );

            if (confirm == JOptionPane.YES_OPTION) {
                int empId = (loggedEmployee != null && loggedEmployee.getId() > 0) ? loggedEmployee.getId() : EmployeesDao.id_user;
                boolean ok = kardexDao.registrarAjusteInventario(item.product.getId(), empId, nuevoStock, motivo);

                if (ok) {
                    JOptionPane.showMessageDialog(dialog, "Ajuste de inventario registrado correctamente en Kardex.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    dialog.dispose();
                    cargarTablaKardex();
                    if (productsController != null) {
                        productsController.refreshTable();
                    }
                } else {
                    JOptionPane.showMessageDialog(dialog, "Error al registrar el ajuste de inventario en la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        pnlButtons.add(btnCancelar);
        pnlButtons.add(btnGuardar);

        dialog.add(pnlMain, BorderLayout.CENTER);
        dialog.add(pnlButtons, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    private static class ProductItem {
        final Products product;

        ProductItem(Products product) {
            this.product = product;
        }

        @Override
        public String toString() {
            return "[" + product.getCode() + "] " + product.getName() + " (Stock actual: " + product.getProduct_quantity() + ")";
        }
    }
}