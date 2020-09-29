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
public class FacturaVentaLibro {
    
    private int IdFacturaVentaLibro;
    private int Cantidad;
    private float Precio;
    private float Subtotal;
    private int IdFacturaVenta;
    private int IdLibro;
    
    public FacturaVentaLibro(int Cantidad, float Precio, float Subtotal, int IdFacturaVenta, int IdLibro)
    {
        this.Cantidad = Cantidad;
        this.Precio = Precio;
        this.Subtotal = Subtotal;
        this.IdFacturaVenta = IdFacturaVenta;
        this.IdLibro = IdLibro;
    }
    
    public FacturaVentaLibro(int IdLibro, int Cantidad, float Precio, float Subtotal)
    {
        this.Cantidad = Cantidad;
        this.Precio = Precio;
        this.Subtotal = Subtotal;
        this.IdLibro = IdLibro;
    }

    /**
     * @return the IdFacturaVentaLibro
     */
    public int getIdFacturaVentaLibro() {
        return IdFacturaVentaLibro;
    }

    /**
     * @param IdFacturaVentaLibro the IdFacturaVentaLibro to set
     */
    public void setIdFacturaVentaLibro(int IdFacturaVentaLibro) {
        this.IdFacturaVentaLibro = IdFacturaVentaLibro;
    }

    /**
     * @return the Cantidad
     */
    public int getCantidad() {
        return Cantidad;
    }

    /**
     * @param Cantidad the Cantidad to set
     */
    public void setCantidad(int Cantidad) {
        this.Cantidad = Cantidad;
    }

    /**
     * @return the Precio
     */
    public float getPrecio() {
        return Precio;
    }

    /**
     * @param Precio the Precio to set
     */
    public void setPrecio(float Precio) {
        this.Precio = Precio;
    }

    /**
     * @return the Subtotal
     */
    public float getSubtotal() {
        return Subtotal;
    }

    /**
     * @param Subtotal the Subtotal to set
     */
    public void setSubtotal(float Subtotal) {
        this.Subtotal = Subtotal;
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
     * @return the IdLibro
     */
    public int getIdLibro() {
        return IdLibro;
    }

    /**
     * @param IdLibro the IdLibro to set
     */
    public void setIdLibro(int IdLibro) {
        this.IdLibro = IdLibro;
    }
    
}
