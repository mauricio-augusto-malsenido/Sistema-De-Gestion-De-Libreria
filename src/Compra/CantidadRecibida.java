package Compra;

/**
 *
 * @author SEBASTIAN
 */
public class CantidadRecibida {
 private int IdLibro;
 private int CantidadRecibida;

    public CantidadRecibida(int IdLibro, int CantidadRecibida) {
        this.IdLibro = IdLibro;
        this.CantidadRecibida = CantidadRecibida;
    }

    public int getIdLibro() {
        return IdLibro;
    }

    public void setIdLibro(int IdLibro) {
        this.IdLibro = IdLibro;
    }

    public int getCantidadRecibida() {
        return CantidadRecibida;
    }

    public void setCantidadRecibida(int CantidadRecibida) {
        this.CantidadRecibida = CantidadRecibida;
    }
 
    
}
