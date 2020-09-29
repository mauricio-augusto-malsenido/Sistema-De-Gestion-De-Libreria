package mapa;

/**
 *
 * @author SEBASTIAN
 */
public class provincia {
    
    private int IdProvincia;
    private String NombreProvincia;

    public provincia(int IdProvincia, String NombreProvincia) {
        this.IdProvincia = IdProvincia;
        this.NombreProvincia = NombreProvincia;
    }
   

   
    public int getIdProvincia() {
        return IdProvincia;
    }

    public void setIdProvincia(int IdProvincia) {
        this.IdProvincia = IdProvincia;
    }

    public String getNombreProvincia() {
        return NombreProvincia;
    }

    public void setNombreProvincia(String NombreProvincia) {
        this.NombreProvincia = NombreProvincia;
    }
    @Override
  public String toString()
    {
     return NombreProvincia;}
   
    @Override  
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (this == o) {
            return true;
        }
        if (!(o instanceof provincia)) {
            return false;
        }
        provincia empleado = (provincia) o;

        if (IdProvincia != empleado.IdProvincia) {
            return false;
        }
        if (NombreProvincia != null ? !NombreProvincia.equals(empleado.NombreProvincia) : empleado.NombreProvincia != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + (this.NombreProvincia != null ? this.NombreProvincia.hashCode() : 0);
        hash = 89 * hash + this.IdProvincia;
        return hash;
    }
    
    
}
