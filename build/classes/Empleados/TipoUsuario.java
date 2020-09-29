/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Empleados;

/**
 *
 * @author sergio
 */
public class TipoUsuario {
    private int IdTipoUsuario;
    private String NombreTipoUsuario;

    public TipoUsuario(int IdTipoUsuario, String NombreTipoUsuario) {
        this.IdTipoUsuario = IdTipoUsuario;
        this.NombreTipoUsuario = NombreTipoUsuario;
    }

    /**
     * @return the IdTipoUsuario
     */
    public int getIdTipoUsuario() {
        return IdTipoUsuario;
    }

    /**
     * @param IdTipoUsuario the IdTipoUsuario to set
     */
    public void setIdTipoUsuario(int IdTipoUsuario) {
        this.IdTipoUsuario = IdTipoUsuario;
    }

    /**
     * @return the NombreTipoUsuario
     */
    public String getNombreTipoUsuario() {
        return NombreTipoUsuario;
    }

    /**
     * @param NombreTipoUsuario the NombreTipoUsuario to set
     */
    public void setUsuario(String NombreTipoUsuario) {
        this.NombreTipoUsuario = NombreTipoUsuario;
    }
    public String toString()
    {
     return NombreTipoUsuario;}
   
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (this == o) {
            return true;
        }
        if (!(o instanceof TipoUsuario)) {
            return false;
        }
        TipoUsuario cliente = (TipoUsuario) o;

        if (IdTipoUsuario != cliente.getIdTipoUsuario()) {
            return false;
        }
        if (NombreTipoUsuario != null ? !NombreTipoUsuario.equals(cliente.NombreTipoUsuario) : cliente.NombreTipoUsuario != null) {
            return false;
        }

        return true;
    }

    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + (this.NombreTipoUsuario != null ? this.NombreTipoUsuario.hashCode() : 0);
        hash = 89 * hash + this.getIdTipoUsuario();
        return hash;
    }
}
