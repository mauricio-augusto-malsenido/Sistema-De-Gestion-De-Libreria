package Compra;

import java.sql.Date;

/**
 *
 * @author SEBASTIAN
 */
public class remitoCompra {
private int IdRemitoCompra;
private int NroRemitoProv;
private Date FechaRemitoCompra;
private Date FechaRecepcionCompra;
private int IdProveedor;
private int IdFacturaCompra;
private String EstadoRemito;

    public remitoCompra(int IdRemitoCompra, int NroRemitoProv, Date FechaRemitoCompra, Date FechaRecepcionCompra, int IdProveedor, int IdFacturaCompra, String EstadoRemito) {
        this.IdRemitoCompra = IdRemitoCompra;
        this.NroRemitoProv = NroRemitoProv;
        this.FechaRemitoCompra = FechaRemitoCompra;
        this.FechaRecepcionCompra = FechaRecepcionCompra;
        this.IdProveedor = IdProveedor;
        this.IdFacturaCompra = IdFacturaCompra;
        this.EstadoRemito = EstadoRemito;
    }

    public remitoCompra(int IdRemitoCompra, int NroRemitoProv, Date FechaRemitoCompra, Date FechaRecepcionCompra, int IdProveedor, int IdFacturaCompra) {
        this.IdRemitoCompra = IdRemitoCompra;
        this.NroRemitoProv = NroRemitoProv;
        this.FechaRemitoCompra = FechaRemitoCompra;
        this.FechaRecepcionCompra = FechaRecepcionCompra;
        this.IdProveedor = IdProveedor;
        this.IdFacturaCompra = IdFacturaCompra;
    }

    public int getIdFacturaCompra() {
        return IdFacturaCompra;
    }

    public void setIdFacturaCompra(int IdFacturaCompra) {
        this.IdFacturaCompra = IdFacturaCompra;
    }

    public int getIdRemitoCompra() {
        return IdRemitoCompra;
    }

    public void setIdRemitoCompra(int IdRemitoCompra) {
        this.IdRemitoCompra = IdRemitoCompra;
    }

    public int getNroRemitoProv() {
        return NroRemitoProv;
    }

    public void setNroRemitoProv(int NroRemitoProv) {
        this.NroRemitoProv = NroRemitoProv;
    }

    public Date getFechaRemitoCompra() {
        return FechaRemitoCompra;
    }

    public void setFechaRemitoCompra(Date FechaRemitoCompra) {
        this.FechaRemitoCompra = FechaRemitoCompra;
    }

    public Date getFechaRecepcionCompra() {
        return FechaRecepcionCompra;
    }

    public void setFechaRecepcionCompra(Date FechaRecepcionCompra) {
        this.FechaRecepcionCompra = FechaRecepcionCompra;
    }

    public int getIdProveedor() {
        return IdProveedor;
    }

    public void setIdProveedor(int IdProveedor) {
        this.IdProveedor = IdProveedor;
    }

    public String getEstadoRemito() {
        return EstadoRemito;
    }

    public void setEstadoRemito(String EstadoRemito) {
        this.EstadoRemito = EstadoRemito;
    }




}



