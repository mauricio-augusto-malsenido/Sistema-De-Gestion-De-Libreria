package Empleados;

import libreria.*;

/**
 *
 * @author SEBASTIAN
 */
public class CategoriaEmpleado {

    private int IdCategoriaEmpleado;
    private String NombreCategoriaEmpleado;
    private float SueldoBasicoCategoria;

    public CategoriaEmpleado() {
    }

    public CategoriaEmpleado(int IdCategoriaEmpleado, String NombreCategoriaEmpleado, float SueldoBasicoCategoria) {
        this.IdCategoriaEmpleado = IdCategoriaEmpleado;
        this.NombreCategoriaEmpleado = NombreCategoriaEmpleado;
        this.SueldoBasicoCategoria = SueldoBasicoCategoria;
    }

    public int getIdCategoriaEmpleado() {
        return IdCategoriaEmpleado;
    }

    public void setIdCategoriaEmpleado(int IdCategoriaEmpleado) {
        this.IdCategoriaEmpleado = IdCategoriaEmpleado;
    }

    public String getNombreCategoriaEmpleado() {
        return NombreCategoriaEmpleado;
    }

    public void setNombreCategoriaEmpleado(String NombreCategoriaEmpleado) {
        this.NombreCategoriaEmpleado = NombreCategoriaEmpleado;
    }

    public float getSueldoBasicoCategoria() {
        return SueldoBasicoCategoria;
    }

    public void setSueldoBasicoCategoria(float SueldoBasicoCategoria) {
        this.SueldoBasicoCategoria = SueldoBasicoCategoria;
    }
      @Override
  public String toString()
    {
     return NombreCategoriaEmpleado;}
   
    @Override  
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (this == o) {
            return true;
        }
        if (!(o instanceof CategoriaEmpleado)) {
            return false;
        }
        CategoriaEmpleado categoriaEmpleado = (CategoriaEmpleado) o;

        if (IdCategoriaEmpleado != categoriaEmpleado.IdCategoriaEmpleado) {
            return false;
        }
        if (NombreCategoriaEmpleado != null ? !NombreCategoriaEmpleado.equals(categoriaEmpleado.NombreCategoriaEmpleado) : categoriaEmpleado.NombreCategoriaEmpleado != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + (this.NombreCategoriaEmpleado != null ? this.NombreCategoriaEmpleado.hashCode() : 0);
        hash = 89 * hash + this.IdCategoriaEmpleado;
        return hash;    
        
    
        }
     
    
}
