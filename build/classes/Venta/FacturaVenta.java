/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Venta;

import java.sql.Date;

/**
 *
 * @author Mauricio
 */
public class FacturaVenta {
    
    private int IdFacturaVenta;
    private Date FechaFacturaVenta;
    private float TotalFacturaVenta;
    private float BrutoFacturaVenta;
    private float IvaFacturaVenta;
    private boolean Anulada;
    private int IdCliente;
    private int IdEmpleado;
    
    public FacturaVenta(int IdFacturaVenta, java.sql.Date fventa, float BrutoFacturaVenta, float IvaFacturaVenta, float TotalFacturaVenta, boolean Anulada, int IdCliente, int IdEmpleado) {
        this.IdFacturaVenta = IdFacturaVenta;
        this.FechaFacturaVenta = fventa;
        this.BrutoFacturaVenta = BrutoFacturaVenta;
        this.IvaFacturaVenta = IvaFacturaVenta;
        this.TotalFacturaVenta = TotalFacturaVenta;
        this.Anulada = Anulada;
        this.IdCliente = IdCliente;
        this.IdEmpleado = IdEmpleado;
    }

    /**
     * @return the IdFacturaVenta
     */
    public int getIdFacturaVenta() {
        return IdFacturaVenta;
    }

    /**
     * @param IdFacturaVenta the IdFacturaVenta to set
     */
    public void setIdFacturaVenta(int IdFacturaVenta) {
        this.IdFacturaVenta = IdFacturaVenta;
    }

    /**
     * @return the FechaFacturaVenta
     */
    public Date getFechaFacturaVenta() {
        return FechaFacturaVenta;
    }

    /**
     * @param FechaFacturaVenta the FechaFacturaVenta to set
     */
    public void setFechaFacturaVenta(Date FechaFacturaVenta) {
        this.FechaFacturaVenta = FechaFacturaVenta;
    }

    /**
     * @return the TotalFacturaVenta
     */
    public float getTotalFacturaVenta() {
        return TotalFacturaVenta;
    }

    /**
     * @param TotalFacturaVenta the TotalFacturaVenta to set
     */
    public void setTotalFacturaVenta(float TotalFacturaVenta) {
        this.TotalFacturaVenta = TotalFacturaVenta;
    }

    /**
     * @return the Anulada
     */
    public boolean isAnulada() {
        return Anulada;
    }

    /**
     * @param Anulada the Anulada to set
     */
    public void setAnulada(boolean Anulada) {
        this.Anulada = Anulada;
    }

    /**
     * @return the IdCliente
     */
    public int getIdCliente() {
        return IdCliente;
    }

    /**
     * @param IdCliente the IdCliente to set
     */
    public void setIdCliente(int IdCliente) {
        this.IdCliente = IdCliente;
    }

    /**
     * @return the IdEmpleado
     */
    public int getIdEmpleado() {
        return IdEmpleado;
    }

    /**
     * @param IdEmpleado the IdEmpleado to set
     */
    public void setIdEmpleado(int IdEmpleado) {
        this.IdEmpleado = IdEmpleado;
    }

    /**
     * @return the BrutoFacturaVenta
     */
    public float getBrutoFacturaVenta() {
        return BrutoFacturaVenta;
    }

    /**
     * @param BrutoFacturaVenta the BrutoFacturaVenta to set
     */
    public void setBrutoFacturaVenta(float BrutoFacturaVenta) {
        this.BrutoFacturaVenta = BrutoFacturaVenta;
    }

    /**
     * @return the IvaFacturaVenta
     */
    public float getIvaFacturaVenta() {
        return IvaFacturaVenta;
    }

    /**
     * @param IvaFacturaVenta the IvaFacturaVenta to set
     */
    public void setIvaFacturaVenta(float IvaFacturaVenta) {
        this.IvaFacturaVenta = IvaFacturaVenta;
    }
}
