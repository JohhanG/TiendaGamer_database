package Controllers;

import Models.Purchases;
import Models.PurchasesDao;
import Models.Sales;
import Models.SalesDao;
import Views.SystemView;

import java.util.List;
import javax.swing.table.DefaultTableModel;

public class ReportsController {

    private SystemView views;
    private SalesDao saleDao;
    private PurchasesDao purchaseDao;

    DefaultTableModel salesModel;
    DefaultTableModel purchasesModel;

    public ReportsController(SystemView views) {

        this.views       = views;
        this.saleDao     = new SalesDao();
        this.purchaseDao = new PurchasesDao();

        salesModel     = (DefaultTableModel) views.table_all_sales.getModel();
        purchasesModel = (DefaultTableModel) views.table_all_purchases.getModel();

        loadSales();
        loadPurchases();
    }

    // =========================
    // CARGAR VENTAS
    // Columnas: Factura Venta | Cliente | Empleado | Total | Fecha de Venta
    // =========================
    public void loadSales() {

        salesModel.setRowCount(0);

        List<Sales> list = saleDao.listAllSalesQuery();

        for (Sales s : list) {
            salesModel.addRow(new Object[]{
                s.getId(),
                s.getCustomer_name(),
                s.getEmployee_name(),
                String.format("%.2f", s.getTotal_to_pay()),
                s.getSale_date()
            });
        }
    }

    // =========================
    // CARGAR COMPRAS
    // Columnas: Factura | Compra (proveedor) | Total de Compra | Fecha de Compra
    // =========================
    public void loadPurchases() {

        purchasesModel.setRowCount(0);

        List<Purchases> list = purchaseDao.listAllPurchasesQuery();

        for (Purchases p : list) {
            purchasesModel.addRow(new Object[]{
                p.getId(),
                p.getSupplier_name(),
                String.format("%.2f", p.getTotal()),
                p.getCreated()
            });
        }
    }
}