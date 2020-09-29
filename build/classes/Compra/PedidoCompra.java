package Compra;

import java.sql.Date;

/**
 *
 * @author SEBASTIAN
 */
public class PedidoCompra {
    
    private int IdPedidoCompra;
    private Date FechaPedido;
    private int IdProveedor;
    private String Estado;
    private float totalPedido;

    public PedidoCompra(int IdPedidoCompra, Date FechaPedido, int IdProveedor, String Estado, float totalPedido) {
        this.IdPedidoCompra = IdPedidoCompra;
        this.FechaPedido = FechaPedido;
        this.IdProveedor = IdProveedor;
        this.Estado = Estado;
        this.totalPedido = totalPedido;
    }
    
    public PedidoCompra() {
    }

    public PedidoCompra(int IdPedidoCompra, Date FechaPedido, int IdProveedor, String Estado) {
        this.IdPedidoCompra = IdPedidoCompra;
        this.FechaPedido = FechaPedido;
        this.IdProveedor = IdProveedor;
        this.Estado = Estado;
    }
    
    

    public PedidoCompra(int IdPedidoCompra, Date FechaPedido, int IdProveedor) {
        this.IdPedidoCompra = IdPedidoCompra;
        this.FechaPedido = FechaPedido;
        this.IdProveedor = IdProveedor;
    }

    public int getIdPedidoCompra() {
        return IdPedidoCompra;
    }

    public void setIdPedidoCompra(int IdPedidoCompra) {
        this.IdPedidoCompra = IdPedidoCompra;
    }

    public Date getFechaPedido() {
        return FechaPedido;
    }

    public void setFechaPedido(Date FechaPedido) {
        this.FechaPedido = FechaPedido;
    }

    public int getIdProveedor() {
        return IdProveedor;
    }

    public void setIdProveedor(int IdProveedor) {
        this.IdProveedor = IdProveedor;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String Estado) {
        this.Estado = Estado;
    }

    public float getTotalPedido() {
        return totalPedido;
    }

    public void setTotalPedido(float totalPedido) {
        this.totalPedido = totalPedido;
    }

}
