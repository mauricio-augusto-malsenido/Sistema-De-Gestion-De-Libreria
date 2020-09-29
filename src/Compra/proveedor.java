package Compra;

/**
 *
 * @author SEBASTIAN
 */
public class proveedor {
    
    private int IdProveedor;
    private String NombreProveedor;
    private String DireccionProveedor;
    private String CuitProveedor;
    private int IdLocalidad;
    private String TelProveedor;
    private Boolean EstadoProveedor;

    public proveedor(int IdProveedor, String NombreProveedor, String DireccionProveedor, String CuitProveedor, int IdLocalidad, String TelProveedor, Boolean EstadoProveedor) {
        this.IdProveedor = IdProveedor;
        this.NombreProveedor = NombreProveedor;
        this.DireccionProveedor = DireccionProveedor;
        this.CuitProveedor = CuitProveedor;
        this.IdLocalidad = IdLocalidad;
        this.TelProveedor = TelProveedor;
        this.EstadoProveedor = EstadoProveedor;
    }
    

    public proveedor(int IdProveedor, String NombreProveedor, String DireccionProveedor, String CuitProveedor, String TelProveedor, int IdLocalidad) {
        this.IdProveedor = IdProveedor;
        this.NombreProveedor = NombreProveedor;
        this.DireccionProveedor = DireccionProveedor;
        this.CuitProveedor = CuitProveedor;
        this.TelProveedor = TelProveedor;
        this.IdLocalidad = IdLocalidad;
    }
    
     public proveedor(int IdProveedor, String NombreProveedor, String DireccionProveedor, String CuitProveedor, int IdLocalidad) {
        this.IdProveedor = IdProveedor;
        this.NombreProveedor = NombreProveedor;
        this.DireccionProveedor = DireccionProveedor;
        this.CuitProveedor = CuitProveedor;
        this.IdLocalidad = IdLocalidad;
        
    }
    public int getIdLocalidad() {
        return IdLocalidad;
    }

    public void setIdLocalidad(int IdLocalidad) {
        this.IdLocalidad = IdLocalidad;
    }

    public int getIdProveedor() {
        return IdProveedor;
    }

    public void setIdProveedor(int IdProveedor) {
        this.IdProveedor = IdProveedor;
    }

    public String getNombreProveedor() {
        return NombreProveedor;
    }

    public void setNombreProveedor(String NombreProveedor) {
        this.NombreProveedor = NombreProveedor;
    }

    public String getDireccionProveedor() {
        return DireccionProveedor;
    }

    public void setDireccionProveedor(String DireccionProveedor) {
        this.DireccionProveedor = DireccionProveedor;
    }

    public String getCuitProveedor() {
        return CuitProveedor;
    }

    public void setCuitProveedor(String CuitProveedor) {
        this.CuitProveedor = CuitProveedor;
    }
    
     public String getTelProveedor() {
        return TelProveedor;
    }

    public void setTelProveedor(String TelProveedor) {
        this.TelProveedor = TelProveedor;
    }

     public Boolean getEstadoProveedor() {
        return EstadoProveedor;
    }

    public void setEstadoProveedor(Boolean EstadoProveedor) {
        this.EstadoProveedor = EstadoProveedor;
    }
  
     @Override
  public String toString()
    {
     return NombreProveedor;}
   
    @Override  
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (this == o) {
            return true;
        }
        if (!(o instanceof proveedor)) {
            return false;
        }
        proveedor empleado = (proveedor) o;

        if (IdProveedor != empleado.IdProveedor) {
            return false;
        }
        if (NombreProveedor != null ? !NombreProveedor.equals(empleado.NombreProveedor) : empleado.NombreProveedor != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + (this.NombreProveedor != null ? this.NombreProveedor.hashCode() : 0);
        hash = 89 * hash + this.IdProveedor;
        return hash;
    }
    
    public String MostarProveedor(){
        
        String caString="N°PROVEEDOR: "+IdProveedor+"\n"
                +"NOMBRE: "+NombreProveedor+"\n"
                +"TEL: "+TelProveedor+"\n"
                +"CUIT: "+CuitProveedor+"\n"
                +"DIRECCION: "+DireccionProveedor+"\n";
        
        return caString;
    }

   
}
