package mapa;

/**
 *
 * @author SEBASTIAN
 */
public class localidad {
    private int IdLocalidad;
    private String NombreLocalidad;
    private int IdProvincia;

    public localidad(int IdLocalidad, String NombreLocalidad, int IdProvincia) {
        this.IdLocalidad = IdLocalidad;
        this.NombreLocalidad = NombreLocalidad;
        this.IdProvincia = IdProvincia;
    }

    public int getIdProvincia() {
        return IdProvincia;
    }

    public void setIdProvincia(int IdProvincia) {
        this.IdProvincia = IdProvincia;
    }

    public int getIdLocalidad() {
        return IdLocalidad;
    }

    public void setIdLocalidad(int IdLocalidad) {
        this.IdLocalidad = IdLocalidad;
    }

    public String getNombreLocalidad() {
        return NombreLocalidad;
    }

    public void setNombreLocalidad(String NombreLocalidad) {
        this.NombreLocalidad = NombreLocalidad;
    }
            
    
      @Override
  public String toString()
    {
     return NombreLocalidad;}
   
    @Override  
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (this == o) {
            return true;
        }
        if (!(o instanceof localidad)) {
            return false;
        }
        localidad empleado = (localidad) o;

        if (IdLocalidad != empleado.IdLocalidad) {
            return false;
        }
        if (NombreLocalidad != null ? !NombreLocalidad.equals(empleado.NombreLocalidad) : empleado.NombreLocalidad != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + (this.NombreLocalidad != null ? this.NombreLocalidad.hashCode() : 0);
        hash = 89 * hash + this.IdLocalidad;
        return hash;
    }
    


    
}
