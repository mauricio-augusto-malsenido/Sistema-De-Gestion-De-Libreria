package libreria;

/**
 *
 * @author SEBASTIAN
 */
public class Libro {

    private int IdLibro;
    private String Titulo;
    private String edicion;
    private int año;
    private float precio;
    private int stock;
    private int stockCritico;
    private int IdEditorial;
    private int IdAutor;
    private int IdGenero;
    private Boolean EstadoLibro;
    private float CostoLibro;
    private int stockMax;

    public Libro(int IdLibro, String Titulo, String edicion, int año, float precio, int stock, int stockCritico, int IdEditorial, int IdAutor, int IdGenero, Boolean EstadoLibro, float CostoLibro, int stockMax) {
        this.IdLibro = IdLibro;
        this.Titulo = Titulo;
        this.edicion = edicion;
        this.año = año;
        this.precio = precio;
        this.stock = stock;
        this.stockCritico = stockCritico;
        this.IdEditorial = IdEditorial;
        this.IdAutor = IdAutor;
        this.IdGenero = IdGenero;
        this.EstadoLibro = EstadoLibro;
        this.CostoLibro = CostoLibro;
        this.stockMax = stockMax;
    }

    public Libro() {
    }
    
    public int getIdGenero() {
        return IdGenero;
    }

    public void setIdGenero(int IdGenero) {
        this.IdGenero = IdGenero;
    }

    public int getIdLibro() {
        return IdLibro;
    }

    public void setIdLibro(int IdLibro) {
        this.IdLibro = IdLibro;
    }

    public String getTitulo() {
        return Titulo;
    }

    public void setTitulo(String Titulo) {
        this.Titulo = Titulo;
    }

    public String getEdicion() {
        return edicion;
    }

    public void setEdicion(String edicion) {
        this.edicion = edicion;
    }

    public int getAño() {
        return año;
    }

    public void setAño(int año) {
        this.año = año;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getIdEditorial() {
        return IdEditorial;
    }

    public void setIdEditorial(int IdEditorial) {
        this.IdEditorial = IdEditorial;
    }

    public int getIdAutor() {
        return IdAutor;
    }

    public void setIdAutor(int IdAutor) {
        this.IdAutor = IdAutor;
    }
    
     public int getStockCritico() {
        return stockCritico;
    }

    public void setStockCritico(int stockCritico) {
        this.stockCritico = stockCritico;
    }

    public Boolean getEstadoLibro() {
        return EstadoLibro;
    }

    public void setEstadoLibro(Boolean EstadoLibro) {
        this.EstadoLibro = EstadoLibro;
    }

    public float getCostoLibro() {
        return CostoLibro;
    }

    public void setCostoLibro(float CostoLibro) {
        this.CostoLibro = CostoLibro;
    }

    public int getStockMax() {
        return stockMax;
    }

    public void setStockMax(int stockMax) {
        this.stockMax = stockMax;
    }
    
      @Override
  public String toString()
    {
     return Titulo;}
   
    @Override  
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (this == o) {
            return true;
        }
        if (!(o instanceof Libro)) {
            return false;
        }
        Libro empleado = (Libro) o;

        if (IdLibro != empleado.IdLibro) {
            return false;
        }
        if (Titulo != null ? !Titulo.equals(empleado.Titulo) : empleado.Titulo != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + (this.Titulo != null ? this.Titulo.hashCode() : 0);
        hash = 89 * hash + this.IdLibro;
        return hash;
    }
}
