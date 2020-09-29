/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Venta;

/**
 *
 * @author Mauricio
 */
public class ListaFacturas {
    private String codigo;
    private String fecha;
    private String cliente;
    private String empleado;
    private String total;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getEmpleado() {
        return empleado;
    }

    public void setEmpleado(String empleado) {
        this.empleado = empleado;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public ListaFacturas(String codigo, String fecha, String cliente, String empleado, String total) {
        this.codigo = codigo;
        this.fecha = fecha;
        this.cliente = cliente;
        this.empleado = empleado;
        this.total = total;
    }
    
}
