package Compra;

/**
 *
 * @author SEBASTIAN
 */
public class remitoCompraLibro {
private int IdRemitoCompraLibro;
private int CantidadRemito;
private int CantidadRecibida;
private int IdLibro;
private int IdRemitoCompra;

    public remitoCompraLibro(int IdRemitoCompraLibro, int CantidadRemito, int CantidadRecibida, int IdLibro, int IdRemitoCompra) {
        this.IdRemitoCompraLibro = IdRemitoCompraLibro;
        this.CantidadRemito = CantidadRemito;
        this.CantidadRecibida = CantidadRecibida;
        this.IdLibro = IdLibro;
        this.IdRemitoCompra = IdRemitoCompra;
    }

    public remitoCompraLibro() {
    }

    public int getIdRemitoCompra() {
        return IdRemitoCompra;
    }

    public void setIdRemitoCompra(int IdRemitoCompra) {
        this.IdRemitoCompra = IdRemitoCompra;
    }

    public int getIdRemitoCompraLibro() {
        return IdRemitoCompraLibro;
    }

    public void setIdRemitoCompraLibro(int IdRemitoCompraLibro) {
        this.IdRemitoCompraLibro = IdRemitoCompraLibro;
    }

    public int getCantidadRemito() {
        return CantidadRemito;
    }

    public void setCantidadRemito(int CantidadRemito) {
        this.CantidadRemito = CantidadRemito;
    }

    public int getCantidadRecibida() {
        return CantidadRecibida;
    }

    public void setCantidadRecibida(int CantidadRecibida) {
        this.CantidadRecibida = CantidadRecibida;
    }

    public int getIdLibro() {
        return IdLibro;
    }

    public void setIdLibro(int IdLibro) {
        this.IdLibro = IdLibro;
    }



}
