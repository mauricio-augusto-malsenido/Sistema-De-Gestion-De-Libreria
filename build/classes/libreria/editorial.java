package libreria;

/**
 *
 * @author SEBASTIAN
 */
public class editorial {
    private int IdEditorial;
    private String NombreEditorial;
    private String TelefonoEditorial;
    private String DireccionEditorial;
    private Boolean EstadoEditorial;

    public editorial(int IdEditorial, String NombreEditorial, String TelefonoEditorial, String DireccionEditorial, Boolean EstadoEditorial) {
        this.IdEditorial = IdEditorial;
        this.NombreEditorial = NombreEditorial;
        this.TelefonoEditorial = TelefonoEditorial;
        this.DireccionEditorial = DireccionEditorial;
        this.EstadoEditorial = EstadoEditorial;
    }
    

    public editorial(int IdEditorial, String NombreEditorial, String TelefonoEditorial, String DireccionEditorial) {
        this.IdEditorial = IdEditorial;
        this.NombreEditorial = NombreEditorial;
        this.TelefonoEditorial = TelefonoEditorial;
        this.DireccionEditorial = DireccionEditorial;
    }

    public String getDireccionEditorial() {
        return DireccionEditorial;
    }

    public void setDireccionEditorial(String DireccionEditorial) {
        this.DireccionEditorial = DireccionEditorial;
    }

    public int getIdEditorial() {
        return IdEditorial;
    }

    public void setIdEditorial(int IdEditorial) {
        this.IdEditorial = IdEditorial;
    }

    public String getNombreEditorial() {
        return NombreEditorial;
    }

    public void setNombreEditorial(String NombreEditorial) {
        this.NombreEditorial = NombreEditorial;
    }

    public String getTelefonoEditorial() {
        return TelefonoEditorial;
    }

    public void setTelefonoEditorial(String TelefonoEditorial) {
        this.TelefonoEditorial = TelefonoEditorial;
    }
    
    @Override
  public String toString()
    {
     return NombreEditorial;}
   
    @Override  
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (this == o) {
            return true;
        }
        if (!(o instanceof editorial)) {
            return false;
        }
        editorial empleado = (editorial) o;

        if (IdEditorial != empleado.IdEditorial) {
            return false;
        }
        if (NombreEditorial != null ? !NombreEditorial.equals(empleado.NombreEditorial) : empleado.NombreEditorial != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + (this.NombreEditorial != null ? this.NombreEditorial.hashCode() : 0);
        hash = 89 * hash + this.IdEditorial;
        return hash;
    }

    public Boolean getEstadoEditorial() {
        return EstadoEditorial;
    }

    public void setEstadoEditorial(Boolean EstadoEditorial) {
        this.EstadoEditorial = EstadoEditorial;
    }
    
    
}
