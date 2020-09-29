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
public class TipoFacturaVenta {
    
    private int IdTipoFacturaVenta;
    private String NombreTipoFacturaVenta;
    private boolean DiscriminaIva;
    
    public TipoFacturaVenta(int IdTipoFacturaVenta, String NombreTipoFacturaVenta, boolean DiscriminaIva)
    {
        this.IdTipoFacturaVenta = IdTipoFacturaVenta;
        this.NombreTipoFacturaVenta = NombreTipoFacturaVenta;
        this.DiscriminaIva = DiscriminaIva;
    }

    /**
     * @return the IdTipoFacturaVenta
     */
    public int getIdTipoFacturaVenta() {
        return IdTipoFacturaVenta;
    }

    /**
     * @param IdTipoFacturaVenta the IdTipoFacturaVenta to set
     */
    public void setIdTipoFacturaVenta(int IdTipoFacturaVenta) {
        this.IdTipoFacturaVenta = IdTipoFacturaVenta;
    }

    /**
     * @return the NombreTipoFacturaVenta
     */
    public String getNombreTipoFacturaVenta() {
        return NombreTipoFacturaVenta;
    }

    /**
     * @param NombreTipoFacturaVenta the NombreTipoFacturaVenta to set
     */
    public void setNombreTipoFacturaVenta(String NombreTipoFacturaVenta) {
        this.NombreTipoFacturaVenta = NombreTipoFacturaVenta;
    }
    
    public String toString()
    {
     return NombreTipoFacturaVenta;}
   
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (this == o) {
            return true;
        }
        if (!(o instanceof TipoFacturaVenta)) {
            return false;
        }
        TipoFacturaVenta tipoFacturaVenta = (TipoFacturaVenta) o;

        if (IdTipoFacturaVenta != tipoFacturaVenta.IdTipoFacturaVenta) {
            return false;
        }
        if (NombreTipoFacturaVenta != null ? !NombreTipoFacturaVenta.equals(tipoFacturaVenta.NombreTipoFacturaVenta) : tipoFacturaVenta.NombreTipoFacturaVenta != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + (this.NombreTipoFacturaVenta != null ? this.NombreTipoFacturaVenta.hashCode() : 0);
        hash = 89 * hash + this.IdTipoFacturaVenta;
        return hash;
    }

    /**
     * @return the DiscriminaIva
     */
    public boolean isDiscriminaIva() {
        return DiscriminaIva;
    }

    /**
     * @param DiscriminaIva the DiscriminaIva to set
     */
    public void setDiscriminaIva(boolean DiscriminaIva) {
        this.DiscriminaIva = DiscriminaIva;
    }
}
