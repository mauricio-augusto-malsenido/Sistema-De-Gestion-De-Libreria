package Empleados;

import java.sql.Date;
import libreria.*;

/**
 *
 * @author SEBASTIAN
 */
public class Empleado {

    private int IdEmpleado;
    private String DniEmpleado;
    private String NombreEmpleado;
    private String DomicilioEmpleado;
    private String TelefonoEmpleado;
    private int IdLocalidad;
    private Date FechaIngreso;
    private int IdCategoriaEmpleado;
    private int IdEstadoCivilEmpleado;
    private int IdUsuario;
    private Boolean EstadoEmpleado;

    public Empleado(int IdEmpleado, String DniEmpleado, String NombreEmpleado, String DomicilioEmpleado, String TelefonoEmpleado, int IdLocalidad, Date FechaIngreso, int IdCategoriaEmpleado, int IdEstadoCivilEmpleado, int IdUsuario, Boolean EstadoEmpleado) {
        this.IdEmpleado = IdEmpleado;
        this.DniEmpleado = DniEmpleado;
        this.NombreEmpleado = NombreEmpleado;
        this.DomicilioEmpleado = DomicilioEmpleado;
        this.TelefonoEmpleado = TelefonoEmpleado;
        this.IdLocalidad = IdLocalidad;
        this.FechaIngreso = FechaIngreso;
        this.IdCategoriaEmpleado = IdCategoriaEmpleado;
        this.IdEstadoCivilEmpleado = IdEstadoCivilEmpleado;
        this.IdUsuario = IdUsuario;
        this.EstadoEmpleado = EstadoEmpleado;
    }

    public Empleado(int IdEmpleado, String DniEmpleado, String NombreEmpleado, String DomicilioEmpleado, String TelefonoEmpleado, int IdLocalidad, Date FechaIngreso, int IdCategoriaEmpleado, int IdEstadoCivilEmpleado, Boolean EstadoEmpleado) {
        this.IdEmpleado = IdEmpleado;
        this.DniEmpleado = DniEmpleado;
        this.NombreEmpleado = NombreEmpleado;
        this.DomicilioEmpleado = DomicilioEmpleado;
        this.TelefonoEmpleado = TelefonoEmpleado;
        this.IdLocalidad = IdLocalidad;
        this.FechaIngreso = FechaIngreso;
        this.IdCategoriaEmpleado = IdCategoriaEmpleado;
        this.IdEstadoCivilEmpleado = IdEstadoCivilEmpleado;
        this.EstadoEmpleado = EstadoEmpleado;
    }



    public Empleado(int IdEmpleado, String DniEmpleado, String NombreEmpleado) {
        this.IdEmpleado = IdEmpleado;
        this.DniEmpleado = DniEmpleado;
        this.NombreEmpleado = NombreEmpleado;
    }

    public Empleado(int IdEmpleado, String NombreEmpleado) {
        this.IdEmpleado = IdEmpleado;
        this.NombreEmpleado = NombreEmpleado;
    }

    public Empleado(int IdEmpleado, String NombreEmpleado, String DomicilioEmpleado, int IdLocalidad) {
        this.IdEmpleado = IdEmpleado;
        this.NombreEmpleado = NombreEmpleado;
        this.DomicilioEmpleado = DomicilioEmpleado;
        this.IdLocalidad = IdLocalidad;
    }
    
    public Empleado() {
    }

    public int getIdEmpleado() {
        return IdEmpleado;
    }

    public void setIdEmpleado(int IdEmpleado) {
        this.IdEmpleado = IdEmpleado;
    }

    public String getDniEmpleado() {
        return DniEmpleado;
    }

    public void setDniEmpleado(String DniEmpleado) {
        this.DniEmpleado = DniEmpleado;
    }

    public String getNombreEmpleado() {
        return NombreEmpleado;
    }

    public void setNombreEmpleado(String NombreEmpleado) {
        this.NombreEmpleado = NombreEmpleado;
    }

    public String getDomicilioEmpleado() {
        return DomicilioEmpleado;
    }

    public void setDomicilioEmpleado(String DomicilioEmpleado) {
        this.DomicilioEmpleado = DomicilioEmpleado;
    }

    public String getTelefonoEmpleado() {
        return TelefonoEmpleado;
    }

    public void setTelefonoEmpleado(String TelefonoEmpleado) {
        this.TelefonoEmpleado = TelefonoEmpleado;
    }

    public int getIdLocalidad() {
        return IdLocalidad;
    }

    public void setIdLocalidad(int IdLocalidad) {
        this.IdLocalidad = IdLocalidad;
    }

    public Date getFechaIngreso() {
        return FechaIngreso;
    }

    public void setFechaIngreso(Date FechaIngreso) {
        this.FechaIngreso = FechaIngreso;
    }

    public int getIdCategoriaEmpleado() {
        return IdCategoriaEmpleado;
    }

    public void setIdCategoriaEmpleado(int IdCategoriaEmpleado) {
        this.IdCategoriaEmpleado = IdCategoriaEmpleado;
    }

    public int getIdEstadoCivilEmpleado() {
        return IdEstadoCivilEmpleado;
    }

    public void setIdEstadoCivilEmpleado(int IdEstadoCivilEmpleado) {
        this.IdEstadoCivilEmpleado = IdEstadoCivilEmpleado;
    }

    public int getIdUsuario() {
        return IdUsuario;
    }

    public void setIdUsuario(int IdUsuario) {
        this.IdUsuario = IdUsuario;
    }

    public Boolean getEstadoEmpleado() {
        return EstadoEmpleado;
    }

    public void setEstadoEmpleado(Boolean EstadoEmpleado) {
        this.EstadoEmpleado = EstadoEmpleado;
    }

      @Override
  public String toString()
    {
     return NombreEmpleado;}
   
    @Override  
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (this == o) {
            return true;
        }
        if (!(o instanceof Empleado)) {
            return false;
        }
        Empleado empleado = (Empleado) o;

        if (IdEmpleado != empleado.IdEmpleado) {
            return false;
        }
        if (NombreEmpleado != null ? !NombreEmpleado.equals(empleado.NombreEmpleado) : empleado.NombreEmpleado != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + (this.NombreEmpleado != null ? this.NombreEmpleado.hashCode() : 0);
        hash = 89 * hash + this.IdEmpleado;
        return hash;    
        
    
        }
    
    
}
