package Models;
public class Sales {
    private int id;
    private int customer_id;
    private String customer_name;
    private int employee_id;
    private String employee_name;
    private double total;
    private String sale_date;
    private String estado;
    private String motivoCancelacion; // ✅ NUEVO

    public Sales() {
    }
    public Sales(int id, int customer_id, String customer_name, int employee_id, String employee_name, double total, String sale_date, String estado) {
        this.id = id;
        this.customer_id = customer_id;
        this.customer_name = customer_name;
        this.employee_id = employee_id;
        this.employee_name = employee_name;
        this.total = total;
        this.sale_date = sale_date;
        this.estado = estado;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getCustomer_id() {
        return customer_id;
    }
    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }
    public String getCustomer_name() {
        return customer_name;
    }
    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }
    public int getEmployee_id() {
        return employee_id;
    }
    public void setEmployee_id(int employee_id) {
        this.employee_id = employee_id;
    }
    public String getEmployee_name() {
        return employee_name;
    }
    public void setEmployee_name(String employee_name) {
        this.employee_name = employee_name;
    }
    public double getTotal() {
        return total;
    }
    public void setTotal(double total) {
        this.total = total;
    }
    public String getSale_date() {
        return sale_date;
    }
    public void setSale_date(String sale_date) {
        this.sale_date = sale_date;
    }
    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }

    // ✅ NUEVO: motivo por el cual se canceló/devolvió la venta
    public String getMotivoCancelacion() {
        return motivoCancelacion;
    }
    public void setMotivoCancelacion(String motivoCancelacion) {
        this.motivoCancelacion = motivoCancelacion;
    }
}