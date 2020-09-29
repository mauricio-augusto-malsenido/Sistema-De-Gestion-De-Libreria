package libreria;

/**
 *
 * @author SEBASTIAN
 */
public class autor {

    private int IdAutor;
    private String NombreAutor;
    private Boolean EstadoAutor;

    public autor(int IdAutor, String NombreAutor, Boolean EstadoAutor) {
        this.IdAutor = IdAutor;
        this.NombreAutor = NombreAutor;
        this.EstadoAutor = EstadoAutor;
    }
    

    public autor(int IdAutor, String NombreAutor) {
        this.IdAutor = IdAutor;
        this.NombreAutor = NombreAutor;
        
    }

    public autor() {
    }

    
    public int getIdAutor() {
        return IdAutor;
    }

    public void setIdAutor(int IdAutor) {
        this.IdAutor = IdAutor;
    }

    public String getNombreAutor() {
        return NombreAutor;
    }

    public void setNombreAutor(String NombreAutor) {
        this.NombreAutor = NombreAutor;
    }
    
      @Override
  public String toString()
    {
     return NombreAutor;}
   
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
        autor empleado = (autor) o;

        if (IdAutor != empleado.IdAutor) {
            return false;
        }
        if (NombreAutor != null ? !NombreAutor.equals(empleado.NombreAutor) : empleado.NombreAutor != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + (this.NombreAutor != null ? this.NombreAutor.hashCode() : 0);
        hash = 89 * hash + this.IdAutor;
        return hash;
    }

    public Boolean getEstadoAutor() {
        return EstadoAutor;
    }

    public void setEstadoAutor(Boolean EstadoAutor) {
        this.EstadoAutor = EstadoAutor;
    }
    
}
