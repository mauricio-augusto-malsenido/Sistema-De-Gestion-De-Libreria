package libreria;

/**
 *
 * @author SEBASTIAN
 */
public class genero {

    private int IdGenero;
    private String NombreGenero;
    private Boolean EstadoGenero;

    public genero(int IdGenero, String NombreGenero, Boolean EstadoGenero) {
        this.IdGenero = IdGenero;
        this.NombreGenero = NombreGenero;
        this.EstadoGenero = EstadoGenero;
    }
    
    public genero(int IdGenero, String NombreGenero) {
        this.IdGenero = IdGenero;
        this.NombreGenero = NombreGenero;
    }

    public String getNombreGenero() {
        return NombreGenero;
    }

    public void setNombreGenero(String NombreGenero) {
        this.NombreGenero = NombreGenero;
    }

    public int getIdGenero() {
        return IdGenero;
    }

    public void setIdGenero(int IdGenero) {
        this.IdGenero = IdGenero;
    }
    
       @Override
  public String toString()
    {
     return NombreGenero;}
   
    @Override  
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (this == o) {
            return true;
        }
        if (!(o instanceof genero)) {
            return false;
        }
        genero empleado = (genero) o;

        if (IdGenero != empleado.IdGenero) {
            return false;
        }
        if (NombreGenero != null ? !NombreGenero.equals(empleado.NombreGenero) : empleado.NombreGenero != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + (this.NombreGenero != null ? this.NombreGenero.hashCode() : 0);
        hash = 89 * hash + this.IdGenero;
        return hash;
    }

    public Boolean getEstadoGenero() {
        return EstadoGenero;
    }

    public void setEstadoGenero(Boolean EstadoGenero) {
        this.EstadoGenero = EstadoGenero;
    }
    
    
}
