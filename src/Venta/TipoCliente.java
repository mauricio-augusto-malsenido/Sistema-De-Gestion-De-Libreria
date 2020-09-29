/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Venta;

/**
 *
 * @author OLGA
 */
public class TipoCliente {

    private int IdTipoCliente;
    private String NombreTipoCliente;
    private int IdTipoFacturaVenta;
    
    public TipoCliente(int IdTipoCliente, String NombreTipoCliente, int IdTipoFacturaVenta)
    {
        this.IdTipoCliente = IdTipoCliente;
        this.NombreTipoCliente = NombreTipoCliente;
        this.IdTipoFacturaVenta = IdTipoFacturaVenta;
    }

    /**
     * @return the IdTipoCliente
     */
    public int getIdTipoCliente() {
        return IdTipoCliente;
    }

    /**
     * @param IdTipoCliente the IdTipoCliente to set
     */
    public void setIdTipoCliente(int IdTipoCliente) {
        this.IdTipoCliente = IdTipoCliente;
    }

    /**
     * @return the NombreTipoCliente
     */
    public String getNombreTipoCliente() {
        return NombreTipoCliente;
    }

    /**
     * @param NombreTipoCliente the NombreTipoCliente to set
     */
    public void setNombreTipoCliente(String NombreTipoCliente) {
        this.NombreTipoCliente = NombreTipoCliente;
    }

    /**
     * @return the IdTipoFacturaVenta
     */
    public int isIdTipoFacturaVenta() {
        return IdTipoFacturaVenta;
    }

    /**
     * @param IdTipoFacturaVenta the IdTipoFacturaVenta to set
     */
    public void setIdTipoFacturaVenta(int IdTipoFacturaVenta) {
        this.IdTipoFacturaVenta = IdTipoFacturaVenta;
    }
    
    public String toString()
    {
     return NombreTipoCliente;}
   
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (this == o) {
            return true;
        }
        if (!(o instanceof TipoCliente)) {
            return false;
        }
        TipoCliente tipoCliente = (TipoCliente) o;

        if (IdTipoCliente != tipoCliente.IdTipoCliente) {
            return false;
        }
        if (NombreTipoCliente != null ? !NombreTipoCliente.equals(tipoCliente.NombreTipoCliente) : tipoCliente.NombreTipoCliente != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + (this.NombreTipoCliente != null ? this.NombreTipoCliente.hashCode() : 0);
        hash = 89 * hash + this.IdTipoCliente;
        return hash;
    }
}
