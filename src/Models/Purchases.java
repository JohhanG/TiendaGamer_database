package Models;
public class Purchases {
    private int id;
    private int supplier_id;
    private int employee_id;
    private double total;
    private String estado;
    private String created;
    private String supplier_name;
    private String motivoCancelacion; // ✅ NUEVO

    public Purchases() {
    }
    public Purchases(int id, int supplier_id, int employee_id, double total, String estado, String created) {
        this.id = id;
        this.supplier_id = supplier_id;
        this.employee_id = employee_id;
        this.total = total;
        this.estado = estado;
        this.created = created;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getSupplier_id() {
        return supplier_id;
    }
    public void setSupplier_id(int supplier_id) {
        this.supplier_id = supplier_id;
    }
    public int getEmployee_id() {
        return employee_id;
    }
    public void setEmployee_id(int employee_id) {
        this.employee_id = employee_id;
    }
    public double getTotal() {
        return total;
    }
    public void setTotal(double total) {
        this.total = total;
    }
    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }
    public String getCreated() {
        return created;
    }
    public void setCreated(String created) {
        this.created = created;
    }
    public String getSupplier_name() {
        return supplier_name;
    }
    public void setSupplier_name(String supplier_name) {
        this.supplier_name = supplier_name;
    }

    // ✅ NUEVO: motivo por el cual se canceló/devolvió la compra
    public String getMotivoCancelacion() {
        return motivoCancelacion;
    }
    public void setMotivoCancelacion(String motivoCancelacion) {
        this.motivoCancelacion = motivoCancelacion;
    }
}