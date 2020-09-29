package Compra;
/**
 *
 * @author SEBASTIAN
 */
public class LineaPedido {
    
    private int idLineaPedido;
    private int Cantidad;
    private int CantRecibida;
    private int IdLibro;
    private int IdPedidoCompra;    
    private Float CostoLineaPedido;    

    public LineaPedido(int idLineaPedido, int Cantidad, int CantRecibida, int IdLibro, int IdPedidoCompra, Float CostoLineaPedido) {
        this.idLineaPedido = idLineaPedido;
        this.Cantidad = Cantidad;
        this.CantRecibida = CantRecibida;
        this.IdLibro = IdLibro;
        this.IdPedidoCompra = IdPedidoCompra;
        this.CostoLineaPedido = CostoLineaPedido;
    }

    public LineaPedido() {
    }

    public LineaPedido(int idLineaPedido, int Cantidad, int CantRecibida, int IdLibro, int IdPedidoCompra) {
        this.idLineaPedido = idLineaPedido;
        this.Cantidad = Cantidad;
        this.CantRecibida = CantRecibida;
        this.IdLibro = IdLibro;
        this.IdPedidoCompra = IdPedidoCompra;
    }
    
    
    public LineaPedido(int idLineaPedido, int Cantidad, int IdLibro, int IdPedidoCompra) {
        this.idLineaPedido = idLineaPedido;
        this.Cantidad = Cantidad;
        this.IdLibro = IdLibro;
        this.IdPedidoCompra = IdPedidoCompra;
    }
    

    public int getIdLineaPedido() {
        return idLineaPedido;
    }

    public void setIdLineaPedido(int idLineaPedido) {
        this.idLineaPedido = idLineaPedido;
    }

    public int getCantidad() {
        return Cantidad;
    }

    public void setCantidad(int Cantidad) {
        this.Cantidad = Cantidad;
    }

    public int getIdLibro() {
        return IdLibro;
    }

    public void setIdLibro(int IdLibro) {
        this.IdLibro = IdLibro;
    }

    public int getIdPedidoCompra() {
        return IdPedidoCompra;
    }

    public void setIdPedidoCompra(int IdPedidoCompra) {
        this.IdPedidoCompra = IdPedidoCompra;
    }

    public int getCantRecibida() {
        return CantRecibida;
    }

    public void setCantRecibida(int CantRecibida) {
        this.CantRecibida = CantRecibida;
    }

    public Float getCostoLineaPedido() {
        return CostoLineaPedido;
    }

    public void setCostoLineaPedido(Float CostoLineaPedido) {
        this.CostoLineaPedido = CostoLineaPedido;
    }
    
}
