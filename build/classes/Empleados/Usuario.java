/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Empleados;

/**
 *
 * @author Mauricio
 */
public class Usuario {
    private int IdUsuario;
    private String usuario;
    private String Contraseña;
    private int IdTipoUsuario;

    public Usuario() {
    }

    public Usuario(int IdUsuario, String usuario, String Contraseña, int IdTipoUsuario) {
        this.IdUsuario = IdUsuario;
        this.usuario = usuario;
        this.Contraseña = Contraseña;
        this.IdTipoUsuario = IdTipoUsuario;
    }

    public Usuario(int IdUsuario, String usuario) {
        this.IdUsuario = IdUsuario;
        this.usuario = usuario;
    }

    /**
     * @return the IdUusario
     */
    public int getIdUsuario() {
        return IdUsuario;
    }

    /**
     * @param IdUsuario the IdUusario to set
     */
    public void setIdUsuario(int IdUsuario) {
        this.IdUsuario = IdUsuario;
    }

    /**
     * @return the usuario
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    /**
     * @return the Contraseña
     */
    public String getContraseña() {
        return Contraseña;
    }

    /**
     * @param Contraseña the Contraseña to set
     */
    public void setContraseña(String Contraseña) {
        this.Contraseña = Contraseña;
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
    public String toString()
    {
     return usuario;}
   
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (this == o) {
            return true;
        }
        if (!(o instanceof Usuario)) {
            return false;
        }
        Usuario cliente = (Usuario) o;

        if (IdUsuario != cliente.getIdUsuario()) {
            return false;
        }
        if (usuario != null ? !usuario.equals(cliente.usuario) : cliente.usuario != null) {
            return false;
        }

        return true;
    }

    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + (this.usuario != null ? this.usuario.hashCode() : 0);
        hash = 89 * hash + this.getIdUsuario();
        return hash;
    }
}
