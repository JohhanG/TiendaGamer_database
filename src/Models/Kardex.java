package Models;

import java.sql.Timestamp;

public class Kardex {
    private int idKardex;
    private int idProducto;
    private int idTipoMov;
    private int idEmpleado;
    private int cantidad;
    private String efecto;
    private int saldoAnterior;
    private int saldoResultante;
    private Timestamp fecha;
    private String observacion;

    public Kardex() {
    }

    public Kardex(int idKardex, int idProducto, int idTipoMov, int idEmpleado, int cantidad, String efecto, int saldoAnterior, int saldoResultante, Timestamp fecha, String observacion) {
        this.idKardex = idKardex;
        this.idProducto = idProducto;
        this.idTipoMov = idTipoMov;
        this.idEmpleado = idEmpleado;
        this.cantidad = cantidad;
        this.efecto = efecto;
        this.saldoAnterior = saldoAnterior;
        this.saldoResultante = saldoResultante;
        this.fecha = fecha;
        this.observacion = observacion;
    }

    public int getIdKardex() {
        return idKardex;
    }

    public void setIdKardex(int idKardex) {
        this.idKardex = idKardex;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public int getIdTipoMov() {
        return idTipoMov;
    }

    public void setIdTipoMov(int idTipoMov) {
        this.idTipoMov = idTipoMov;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getEfecto() {
        return efecto;
    }

    public void setEfecto(String efecto) {
        this.efecto = efecto;
    }

    public int getSaldoAnterior() {
        return saldoAnterior;
    }

    public void setSaldoAnterior(int saldoAnterior) {
        this.saldoAnterior = saldoAnterior;
    }

    public int getSaldoResultante() {
        return saldoResultante;
    }

    public void setSaldoResultante(int saldoResultante) {
        this.saldoResultante = saldoResultante;
    }

    public Timestamp getFecha() {
        return fecha;
    }

    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }
}