package Venta;

/**
 *
 * @author SEBASTIAN
 */
public class Cliente {
    
    private int IdCliente;
    private int DniCliente;
    private String CuitCliente;
    private String NombreCliente;
    private String DireccionCliente;
    private String TelefonoCLiente;
    private String EmailCliente;
    private boolean EstadoCliente;
    private int IdLocalidad;
    private int IdTipoCliente;

    public Cliente(int IdCliente, int DniCliente, String CuitCliente, String NombreCliente, String DireccionCliente, String TelefonoCLiente, String EmailCliente, boolean EstadoCliente, int IdLocalidad, int IdTipoCliente) {
        this.IdCliente = IdCliente;
        this.DniCliente = DniCliente;
        this.CuitCliente = CuitCliente;
        this.NombreCliente = NombreCliente;
        this.DireccionCliente = DireccionCliente;
        this.TelefonoCLiente = TelefonoCLiente;
        this.EmailCliente = EmailCliente;
        this.EstadoCliente = EstadoCliente;
        this.IdLocalidad = IdLocalidad;
        this.IdTipoCliente = IdTipoCliente;
    }

    public String getNombreCliente() {
        return NombreCliente;
    }

    public void setNombreCliente(String NombreCliente) {
        this.NombreCliente = NombreCliente;
    }

    public String getDireccionCliente() {
        return DireccionCliente;
    }

    public void setDireccionCliente(String direccionCliente) {
        this.DireccionCliente = direccionCliente;
    }

    public int getIdLocalidad() {
        return IdLocalidad;
    }

    public void setIdLocalidad(int IdLocalidad) {
        this.IdLocalidad = IdLocalidad;
    }

    public String getTelefonoCLiente() {
        return TelefonoCLiente;
    }

    public void setTelefonoCLiente(String TelefonoCLiente) {
        this.TelefonoCLiente = TelefonoCLiente;
    }

    public String getEmailCliente() {
        return EmailCliente;
    }

    public void setEmailCliente(String EmailCliente) {
        this.EmailCliente = EmailCliente;
    }

    public int getIdCliente() {
        return IdCliente;
    }

    public void setIdCliente(int IdCliente) {
        this.IdCliente = IdCliente;
    }
    public String toString()
    {
     return NombreCliente;}
   
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (this == o) {
            return true;
        }
        if (!(o instanceof Cliente)) {
            return false;
        }
        Cliente cliente = (Cliente) o;

        if (IdCliente != cliente.getIdCliente()) {
            return false;
        }
        if (NombreCliente != null ? !NombreCliente.equals(cliente.NombreCliente) : cliente.NombreCliente != null) {
            return false;
        }

        return true;
    }

    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + (this.NombreCliente != null ? this.NombreCliente.hashCode() : 0);
        hash = 89 * hash + this.IdCliente;
        return hash;
    }

    /**
     * @return the Dni
     */
    public int getDniCliente() {
        return DniCliente;
    }

    /**
     * @param Dni the Dni to set
     */
    public void setDni(int DniCliente) {
        this.setDniCliente(DniCliente);
    }

    /**
     * @return the Cuit
     */
    public String getCuitCliente() {
        return CuitCliente;
    }

    /**
     * @param Cuit the Cuit to set
     */
    public void setCuitCliente(String CuitCliente) {
        this.CuitCliente = CuitCliente;
    }

    /**
     * @return the IdTipoCliente
     */
    public int getIdTipoCliente() {
        return IdTipoCliente;
    }

    /**
     * @param IdTipoCliente the IdTipoCliente to set
     */
    public void setIdTipoCliente(int IdTipoCliente) {
        this.IdTipoCliente = IdTipoCliente;
    }

    /**
     * @param DniCliente the DniCliente to set
     */
    public void setDniCliente(int DniCliente) {
        this.DniCliente = DniCliente;
    }

    /**
     * @return the EstadoCliente
     */
    public boolean getEstadoCliente() {
        return EstadoCliente;
    }

    /**
     * @param EstadoCliente the EstadoCliente to set
     */
    public void setEstadoCliente(boolean EstadoCliente) {
        this.EstadoCliente = EstadoCliente;
    }

    
}
