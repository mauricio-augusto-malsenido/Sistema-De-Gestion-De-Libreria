package Empleados;

import libreria.*;

/**
 *
 * @author SEBASTIAN
 */
public class EstadoCivilEmpleado {

    private int IdEstadoCivilEmpleado;
    private String NombreEstadoCivilEmpleado;

    public EstadoCivilEmpleado() {
    }

    public EstadoCivilEmpleado(int IdEstadoCivilEmpleado, String NombreEstadoCivilEmpleado) {
        this.IdEstadoCivilEmpleado = IdEstadoCivilEmpleado;
        this.NombreEstadoCivilEmpleado = NombreEstadoCivilEmpleado;
    }

    public int getIdEstadoCivilEmpleado() {
        return IdEstadoCivilEmpleado;
    }

    public void setIdEstadoCivilEmpleado(int IdEstadoCivilEmpleado) {
        this.IdEstadoCivilEmpleado = IdEstadoCivilEmpleado;
    }

    public String getNombreEstadoCivilEmpleado() {
        return NombreEstadoCivilEmpleado;
    }

    public void setNombreEstadoCivilEmpleado(String NombreEstadoCivilEmpleado) {
        this.NombreEstadoCivilEmpleado = NombreEstadoCivilEmpleado;
    }
      @Override
  public String toString()
    {
     return NombreEstadoCivilEmpleado;}
   
    @Override  
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (this == o) {
            return true;
        }
        if (!(o instanceof autor)) {
            return false;
        }
        EstadoCivilEmpleado empleado = (EstadoCivilEmpleado) o;

        if (IdEstadoCivilEmpleado != empleado.IdEstadoCivilEmpleado) {
            return false;
        }
        if (NombreEstadoCivilEmpleado != null ? !NombreEstadoCivilEmpleado.equals(empleado.NombreEstadoCivilEmpleado) : empleado.NombreEstadoCivilEmpleado != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + (this.NombreEstadoCivilEmpleado != null ? this.NombreEstadoCivilEmpleado.hashCode() : 0);
        hash = 89 * hash + this.IdEstadoCivilEmpleado;
        return hash;    
        
    
        }
    
}
