package Compra;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import libreria.Libro;

/**
 *
 * @author SEBASTIAN
 */
public class ConsultaCompra {
    private static final String URL = "jdbc:sqlserver://;database=libreria;integratedSecurity=true;";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Mm-1991";
    private Connection connection = null;
    private PreparedStatement select = null;
    private PreparedStatement insert = null;
    private PreparedStatement update = null;
    private PreparedStatement delete = null;
    
    
    //----------------SELECT------------------
    //REMITO
    private String RemitoPorId = "select * from libreria.remitoCompra where IdRemitoCompra="+"?";
    private String RemitoPorIdFac = "select * from libreria.remitoCompra where IdPedidoCompra="+"?";
    private String RemitoPorIdPro = "select * from libreria.remitoCompra where IdProveedor="+"?"+" and idFacturaCompra IS NULL" ;
    private String ultimoRemito= "SELECT MAX(IdRemitoCompra) AS id FROM libreria.remitoCompra;";
    //LINEA REMITO
    private String LineaRemitoPorId = "select * from libreria.remitoCompra_libro where IdRemitoCompra="+"?";
    private String UnaLineaRemitoPorId = "select * from libreria.remitoCompra_libro where IdRemitoCompra_Libro="+"?";
   //PROOVEDOR_LIBRO
    private String LineaLp = "select * from libreria.libroProveedor where idLibro="+"?";
    private String UnaLineaLpId = "select * from libreria.libroProveedor where IdLibroProveedor="+"?";
    private String seleccionarLibros=" select * from libreria.Libro where EstadoLibro = "+"?";
    //PEDIDOS
    private String TodosPedidos = "select * from libreria.pedidoCompra";
    private String TodasPedPorFecha;
    private String PedidoPorId = "select * from libreria.pedidoCompra where IdPedidoCompra="+"?";
    private String ultimoPedido= "SELECT MAX(IdPedidoCompra) AS id FROM libreria.pedidoCompra;";
    private String ControlCantReci="select rcl.IdLibroCompra,sum(rcl.CantidadRecibidaCompra) total from libreria.remitocompra as rc" 
    +" inner join libreria.pedidocompra as p ON p.IdPedidoCompra=rc.IdPedidoCompra"
    +" inner join libreria.remitocompra_libro as rcl ON rcl.IdRemitoCompra = rc.IdRemitoCompra"
    +" where p.IdPedidoCompra="+"?"
    +" group by IdLibroCompra";
    //LINEAPEDIDO
    private String LineaPedidoPorId = "select * from libreria.lineaPedido where IdPedidoCompra= "+"?";
    
    //PROVEEDORES
    private String TodosProveedores = "select * from libreria.proveedor order by NombreProveedor";
    private String TodosProveedoresHab = "select * from libreria.proveedor where EstadoProveedor= 1 order by NombreProveedor";
    private String ProveedorPorId = "select * from libreria.proveedor where IdProveedor= "+"?";
    private String ProveedorPorNombre = "select * from libreria.proveedor where NombreProveedor= "+"?";
    private String idULP= "SELECT MAX(IdLibroProveedor) AS id FROM libreria.libroProveedor;";
    private String ultimoProv= "SELECT MAX(IdProveedor) AS id FROM libreria.proveedor;";
    
    //-------------AlTAS-----------------------
    private String nuevoProveedor = "INSERT INTO libreria.proveedor (CuitProveedor, NombreProveedor, DireccionProveedor, TelProveedor, IdLocalidadProveedor)" + " VALUES(?,?,?,?,?)";
    private String nuevoRemito = "INSERT INTO libreria.remitocompra (NroRemitoCompra,FechaRemitoCompra,FechaRecepcionCompra,IdProveedor,IdPedidoCompra)" + " VALUES(?,?,?,?,?)";
    private String nuevoRemitoSF = "INSERT INTO libreria.remitocompra (NroRemitoCompra,FechaRemitoCompra,FechaRecepcionCompra,IdProveedor,IdPedidoCompra)" + " VALUES(?,?,?,?,NULL)";
    private String nuevoLineaRemito = "INSERT INTO libreria.remitocompra_libro (CantidadRemitoCompra,CantidadRecibidaCompra,IdRemitoCompra,IdLibroCompra)" + " VALUES(?,?,?,?)";
    private String nuevoLp = "INSERT INTO libreria.libroProveedor (IdLibro,IdProveedor)" + " VALUES(?,?)";
    private String nuevoPedido = "INSERT INTO libreria.pedidocompra (FechaPedido,IdProveedorCompra,EstadoPedido,TotalPedidoCompra)" + " VALUES(?,?,?,?)";
    private String nuevaLineaPedido = "INSERT INTO libreria.lineaPedido (CantidadPedido,CantidadRecibida,CostoLineaPedido,IdLibroCompra,IdPedidoCompra)" + " VALUES(?,?,?,?,?)";
  
    //-------------MODIFICACIONES--------------
   private String modProveedor ="update libreria.proveedor set "+"CuitProveedor=" +"?"+","+"NombreProveedor=" +"?"+","+"DireccionProveedor=" +"?"+","+"TelProveedor=" +"?"+","+"IdLocalidadProveedor=" +"?"+","+"EstadoProveedor=" +"?"+" where IdProveedor = "+"?";
   private String modRemito ="update libreria.remitocompra set "+
            "NroRemitoCompra=" +"?"+","+" FechaRemitoCompra="+"?"+","+"FechaRecepcionCompra="+"?"+","+" IdProveedor="+"?"+","+"IdPedidoCompra="+"?"+" where IdRemitoCompra= "+"?";
   private String modLineaRemito ="update libreria.remitocompra_libro set "+"CantidadRemitoCompra=" +"?"+","+"CantidadRecibidaCompra="+"?"+","+"IdRemitoCompra="+"?"+","+"IdLibroCompra="+"?"+" where IdRemitoCompra_Libro = "+"?";
   private String modIdFcRemito ="update libreria.remitocompra set "+"IdFacturaCompra="+"?"+" where IdRemitoCompra= "+"?";
   private String modLp ="update libreria.libroProveedor set "+"IdLibro=" +"?"+","+"IdProveedor="+"?"+" where IdLibroProveedor = "+"?";
   private String modLineaPedido ="update libreria.lineaPedido set "+" CantidadRecibida="+"?"+" where IdLineaPedido= "+"?"; 
   private String modPedido ="update libreria.pedidocompra set "+"FechaPedido=" +"?"+","+" IdProveedorCompra="+"?"+","+" EstadoPedido="+"?"+" where IdPedidoCompra= "+"?";
 
   //--------------------BAJAS------------------------
    private String bajaRemito ="delete from libreria.remitocompra where IdRemitoCompra="+"?";
    private String bajaLineaRemito ="delete from libreria.remitocompra_libro where IdRemitoCompra_Libro="+"?";
    private String bajaLp ="delete from libreria.libroProveedor where IdLibroProveedor="+"?";
    private String bajaPedido ="delete from libreria.pedidoCompra where idPedidoCompra="+"?";
    private String bajaLineaPedido ="delete from libreria.lineaPedido where idLineaPedido="+"?";
    private String DesHabProveedor ="update libreria.proveedor set "+"EstadoProveedor=" +"?"+" where IdProveedor = "+"?";
    
   public ConsultaCompra() {
     //En el constructor realizo la coneccion a la base de datos
    try {
           //Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(URL);
            //connection.setSchema("ADMINI");
            
            System.out.println("Conexion realizada satisfactoriamente");
        } catch (SQLException ex) {
            System.out.println("error en : " + ex.getMessage());
        }
    }
    
   private void close() {//Finalizo la coneccion a la DB
        try {
            connection.close();
        } catch (SQLException sqlException) {
            sqlException.getMessage();
        }
    }
   
  
   //PEDIDOS
   public int getIdUltimoPedido(){
    int resultado=0;//Resultados de la consulta a la BD
    ResultSet res=null;
    
        try {
            select = connection.prepareStatement(ultimoPedido);
            res = select.executeQuery();
          
            while (res.next()) {
            resultado=res.getInt("id");
            }
        } 
        catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        finally {
            try {
                res.close();
                System.out.println("se cerror la coneccion");
            } catch (SQLException sqlException) {
                System.out.println(sqlException.getMessage());
                close();
            }
        }
        return resultado;
    } 
   public int agregarPedido(PedidoCompra pedido) {
        int result = 0;

        try {
            insert = connection.prepareStatement(nuevoPedido);
                        insert.setDate(1,pedido.getFechaPedido());
                        insert.setInt(2,pedido.getIdProveedor());
                        insert.setString(3,pedido.getEstado());
                        insert.setFloat(4,pedido.getTotalPedido());
            System.out.println(insert);
            result = insert.executeUpdate();
        } catch (SQLException sqlException) {
            System.out.println("error al insertar pedido " + sqlException.getMessage());
            close();
        }
        
        System.out.println(result);
        return result;
    }
   public int modificarPedido(PedidoCompra pedido){
        int result = 0;

        try {
            update = connection.prepareStatement(modPedido);
                        update.setDate(1,pedido.getFechaPedido());
                        update.setInt(2,pedido.getIdProveedor());
                        update.setString(3,pedido.getEstado());
                        update.setInt(4,pedido.getIdPedidoCompra());
                        
                        
            System.out.println(update);
            result = update.executeUpdate();
        } catch (SQLException sqlException) {
            System.out.println("error al modificar FacturaCompra" + sqlException.getMessage());
            close();
        }
        
        System.out.println(result);
        return result;
    
    
    }
   public int agregarLineaPedido(LineaPedido lp) {
        int result = 0;

        try {
            insert = connection.prepareStatement(nuevaLineaPedido);
            insert.setInt(1, lp.getCantidad());
            insert.setInt(2, lp.getCantRecibida());
            insert.setFloat(3, lp.getCostoLineaPedido());
            insert.setInt(4, lp.getIdLibro());
            insert.setInt(5,lp.getIdPedidoCompra());
            
            System.out.println(insert);
            result = insert.executeUpdate();
        } catch (SQLException sqlException) {
            System.out.println("error al insertar lInea Pedido " + sqlException.getMessage());
            close();
        }
        
        System.out.println(result);
        return result;
    }
   public int eliminaPedido(int idPedido) {
        int result = 0;

        try {
            delete = connection.prepareStatement(bajaPedido);
            delete.setInt(1, idPedido);
            result = delete.executeUpdate();
        } catch (SQLException sqlException) {
            System.out.println("error al eliminar Pedido compra" + sqlException.getMessage());
            close();
        }
        
        System.out.println(result);
        return result;
    
    }
   public int eliminaLineaPedido(int idLineaPedido) {
        int result = 0;

        try {
            delete = connection.prepareStatement(bajaLineaPedido);
            delete.setInt(1, idLineaPedido);
            result = delete.executeUpdate();
        } catch (SQLException sqlException) {
            System.out.println("error al eliminar Pedido compra" + sqlException.getMessage());
            close();
        }
        
        System.out.println(result);
        return result;
    
    }
   public int modificarLineaPedido(LineaPedido lp) {
        int result = 0;

        try {
            update = connection.prepareStatement(modLineaPedido);
            update.setInt(1, lp.getCantRecibida());
            update.setInt(2, lp.getIdLineaPedido());
           
            
            System.out.println(update);
            result = update.executeUpdate();
        } catch (SQLException sqlException) {
            System.out.println("error al mod lInea Pedido " + sqlException.getMessage());
            close();
        }
        
        System.out.println(result);
        return result;
    }
   public PedidoCompra getPedidoPorId(int id){
    //Realiza un select a la BD y guarda todos los clientes recogidos en un array 
        
    PedidoCompra resultado= null;//Resultados de la consulta a la BD
    ResultSet res=null;
    
        try {
            select = connection.prepareStatement(PedidoPorId);
            select.setInt(1,id);
            res = select.executeQuery();
           
            while (res.next()) {
            
                resultado = new PedidoCompra(
                        res.getInt("IdPedidoCompra"), 
                        res.getDate("FechaPedido"),
                        res.getInt("IdProveedorCompra"),
                        res.getString("EstadoPedido"),
                        res.getFloat("TotalPedidoCompra")
                        );
            }
        }
        catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        finally {
            try {
                res.close();
                System.out.println("se cerror la coneccion");
            } catch (SQLException sqlException) {
                System.out.println(sqlException.getMessage());
                close();
            }
        }
        return resultado;
    }
   public List<LineaPedido> getAllLineaPedido(int id){
    //Realiza un select a la BD y guarda todos las facturas de compra
        
    List<LineaPedido> resultado= null;//Resultados de la consulta a la BD
    ResultSet res=null;
    
        try {
            select = connection.prepareStatement(LineaPedidoPorId);
            select.setInt(1,id);
            res = select.executeQuery();
            resultado = new ArrayList<>();

            while (res.next()) {
                LineaPedido pfactura = new LineaPedido(
                    res.getInt("IdLineaPedido"), 
                    res.getInt("CantidadPedido"),
                    res.getInt("CantidadRecibida"),
                    res.getInt("IdLibroCompra"),
                    res.getInt("IdPedidoCompra"),
                    res.getFloat("CostoLineaPedido")
                        );
               
                resultado.add(pfactura);//agrego la factura a la lista
            }
        } 
        catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        finally {
            try {
                res.close();
                System.out.println("se cerror la coneccion");
            } catch (SQLException sqlException) {
                System.out.println(sqlException.getMessage());
//                close();
            }
        }
        return resultado;
    }
   public List<PedidoCompra> getAllPedidos(){
    //Realiza un select a la BD y guarda todos las facturas de compra
        
    List<PedidoCompra> resultado= null;//Resultados de la consulta a la BD
    ResultSet res=null;
    
        try {
            select = connection.prepareStatement(TodosPedidos);
            res = select.executeQuery();
            resultado = new ArrayList<>();

            while (res.next()) {
                PedidoCompra pfactura = new PedidoCompra(
                        res.getInt("IdPedidoCompra"), 
                        res.getDate("FechaPedido"),
                        res.getInt("IdProveedorCompra"),
                        res.getString("EstadoPedido"),
                        res.getFloat("TotalPedidoCompra")
                        );
                        
                resultado.add(pfactura);//agrego la factura a la lista
            }
        } 
        catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        finally {
            try {
                res.close();
                System.out.println("se cerror la coneccion");
            } catch (SQLException sqlException) {
                System.out.println(sqlException.getMessage());
//                close();
            }
        }
        return resultado;
    } 
   public List<PedidoCompra> getPedPorFecha(Date fde,Date fha){
    //Realiza un select a la BD y guarda todos las facturas de compra
        
    List<PedidoCompra> resultado= null;//Resultados de la consulta a la BD
    ResultSet res=null;
    TodasPedPorFecha = "select * from pedidocompra where FechaPedido between \'"+ fde+"\' AND \'"+ fha + "\'";
    
        try {
            select = connection.prepareStatement(TodasPedPorFecha );
            res = select.executeQuery();
            resultado = new ArrayList<>();

            while (res.next()) {
                PedidoCompra pfactura = new PedidoCompra(
                         res.getInt("IdPedidoCompra"), 
                        res.getDate("FechaPedido"),
                        res.getInt("IdProveedorCompra"),
                        res.getString("EstadoPedido"),
                        res.getFloat("TotalPedidoCompra"));
                        
                resultado.add(pfactura);//agrego la factura a la lista
            }
        } 
        catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        finally {
            try {
                res.close();
                System.out.println("se cerror la coneccion");
            } catch (SQLException sqlException) {
                System.out.println(sqlException.getMessage());
//                close();
            }
        }
        return resultado;
    } 
   public List<CantidadRecibida> getAllCantidadRecibida(int idPedido){
    //Realiza un select a la BD y guarda todos las facturas de compra
        
    List<CantidadRecibida> resultado= null;//Resultados de la consulta a la BD
    ResultSet res=null;
    
        try {
            select = connection.prepareStatement(ControlCantReci);
            select.setInt(1,idPedido);
            res = select.executeQuery();
            resultado = new ArrayList<>();

            while (res.next()) {
                CantidadRecibida pfactura = new CantidadRecibida(
                    res.getInt("IdLibroCompra"), 
                    res.getInt("total")
                        );
               
                resultado.add(pfactura);
            }
        } 
        catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        finally {
            try {
                res.close();
                System.out.println("se cerror la coneccion");
            } catch (SQLException sqlException) {
                System.out.println(sqlException.getMessage());
//                close();
            }
        }
        return resultado;
    }
   
   //REMITOS
   public int getIdUltimoRemito(){
    int resultado=0;//Resultados de la consulta a la BD
    ResultSet res=null;
    
        try {
            select = connection.prepareStatement(ultimoRemito);
            res = select.executeQuery();
          
            while (res.next()) {
            resultado=res.getInt("id");
            }
        } 
        catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        finally {
            try {
                res.close();
                System.out.println("se cerror la coneccion");
            } catch (SQLException sqlException) {
                System.out.println(sqlException.getMessage());
                close();
            }
        }
        return resultado;
    } 
   public remitoCompra getRemitoPorId(int id){
    //Realiza un select a la BD y guarda todos los clientes recogidos en un array 
        
    remitoCompra resultado= null;//Resultados de la consulta a la BD
    ResultSet res=null;
    
        try {
            select = connection.prepareStatement(RemitoPorId);
            select.setInt(1,id);
            res = select.executeQuery();
           
            while (res.next()) {
                resultado = new remitoCompra(
                        res.getInt("IdRemitoCompra"), 
                        res.getInt("NroRemitoCompra"),
                        res.getDate("FechaRemitoCompra"),
                        res.getDate("FechaRecepcionCompra"),
                        res.getInt("IdProveedor"),
                        res.getInt("IdPedidoCompra")
                        );
            }
        }
        catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        finally {
            try {
                res.close();
                System.out.println("se cerror la coneccion");
            } catch (SQLException sqlException) {
                System.out.println(sqlException.getMessage());
                close();
            }
        }
        return resultado;
    }
   public List<remitoCompra> getAllRemitoPorIdFactura(int id){
    //Realiza un select a la BD y guarda todos las facturas de compra
        
    List<remitoCompra> resultado= null;//Resultados de la consulta a la BD
    ResultSet res=null;
    
        try {
            select = connection.prepareStatement(RemitoPorIdFac);
            select.setInt(1,id);
            res = select.executeQuery();
            resultado = new ArrayList<>();

            while (res.next()) {
                remitoCompra pfactura = new remitoCompra(
                    res.getInt("IdRemitoCompra"), 
                        res.getInt("NroRemitoCompra"),
                        res.getDate("FechaRemitoCompra"),
                        res.getDate("FechaRecepcionCompra"),
                        res.getInt("IdProveedor"),
                        res.getInt("IdPedidoCompra")
                        );
                resultado.add(pfactura);//agrego la factura a la lista
            }
        } 
        catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        finally {
            try {
                res.close();
                System.out.println("se cerror la coneccion");
            } catch (SQLException sqlException) {
                System.out.println(sqlException.getMessage());
//                close();
            }
        }
        return resultado;
    } 
   public List<remitoCompra> getAllRemitoPorIdProveedor(int id){
    //Realiza un select a la BD y guarda todos las facturas de compra
        
    List<remitoCompra> resultado= null;//Resultados de la consulta a la BD
    ResultSet res=null;
    
        try {
            select = connection.prepareStatement(RemitoPorIdPro);
            select.setInt(1,id);
            res = select.executeQuery();
            resultado = new ArrayList<>();

            while (res.next()) {
                remitoCompra pfactura = new remitoCompra(
                    res.getInt("IdRemitoCompra"), 
                        res.getInt("NroRemitoCompra"),
                        res.getDate("FechaRemitoCompra"),
                        res.getDate("FechaRecepcionCompra"),
                        res.getInt("IdProveedor"),
                        res.getInt("IdPedidoCompra")
                        );
                resultado.add(pfactura);//agrego la factura a la lista
            }
        } 
        catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        finally {
            try {
                res.close();
                System.out.println("se cerror la coneccion");
            } catch (SQLException sqlException) {
                System.out.println(sqlException.getMessage());
//                close();
            }
        }
        return resultado;
    } 
   public int agregarRemito(remitoCompra pRemito) {
        int result = 0;

        try {
            insert = connection.prepareStatement(nuevoRemito);
                        insert.setInt(1,pRemito.getNroRemitoProv());
                        insert.setDate(2,pRemito.getFechaRemitoCompra());
                        insert.setDate(3,pRemito.getFechaRecepcionCompra());
                        insert.setInt(4,pRemito.getIdProveedor());
                        insert.setInt(5,pRemito.getIdFacturaCompra());
                    
            System.out.println(insert);
            result = insert.executeUpdate();
        } catch (SQLException sqlException) {
            System.out.println("error al insertar remito " + sqlException.getMessage());
            close();
        }
        
        System.out.println(result);
        return result;
    }
   public int agregarRemitoSinFactura(remitoCompra pRemito) {
        int result = 0;

        try {
            insert = connection.prepareStatement(nuevoRemitoSF);
                        insert.setInt(1,pRemito.getNroRemitoProv());
                        insert.setDate(2,pRemito.getFechaRemitoCompra());
                        insert.setDate(3,pRemito.getFechaRecepcionCompra());
                        insert.setInt(4,pRemito.getIdProveedor());
                        
                    
            System.out.println(insert);
            result = insert.executeUpdate();
        } catch (SQLException sqlException) {
            System.out.println("error al insertar remito " + sqlException.getMessage());
            close();
        }
        
        System.out.println(result);
        return result;
    }
   public int modificarRemito(remitoCompra pRemito){
        int result = 0;

        try {
            update = connection.prepareStatement(modRemito);
                       update.setInt(1,pRemito.getNroRemitoProv());
                        update.setDate(2,pRemito.getFechaRemitoCompra());
                        update.setDate(3,pRemito.getFechaRecepcionCompra());
                        update.setInt(4,pRemito.getIdProveedor());
                        update.setInt(5,pRemito.getIdFacturaCompra());
                        update.setInt(6,pRemito.getIdRemitoCompra());
                        
            System.out.println(update);
            result = update.executeUpdate();
        } catch (SQLException sqlException) {
            System.out.println("error al modificar remitoCompra" + sqlException.getMessage());
            close();
        }
        
        System.out.println(result);
        return result;
    
    
    }
   public int modificarIdFacRemito(int idRemito,int idFactura){
        int result = 0;

        try {
            update = connection.prepareStatement(modIdFcRemito);
                       update.setInt(1,idFactura);
                       update.setInt(2,idRemito);
                        
            System.out.println(update);
            result = update.executeUpdate();
        } catch (SQLException sqlException) {
            System.out.println("error al modificar remitoCompra" + sqlException.getMessage());
            close();
        }
        
        System.out.println(result);
        return result;
    
    
    }
   public int eliminarRemito(int idRemito) {
        int result = 0;

        try {
            delete = connection.prepareStatement(bajaRemito);
            delete.setInt(1, idRemito);
            result = delete.executeUpdate();
        } catch (SQLException sqlException) {
            System.out.println("error al eliminar remito compra" + sqlException.getMessage());
            close();
        }
        
        System.out.println(result);
        return result;
    
    }
   
   //LINEA REMITO
   
   public remitoCompraLibro getLineaRemitoPorId(int id){
    //Realiza un select a la BD y guarda todos los clientes recogidos en un array 
        
    remitoCompraLibro resultado= null;//Resultados de la consulta a la BD
    ResultSet res=null;
    
        try {
            select = connection.prepareStatement(UnaLineaRemitoPorId);
            select.setInt(1,id);
            res = select.executeQuery();

            while (res.next()) {
                resultado = new remitoCompraLibro(
                        res.getInt("IdRemitoCompra_Libro"), 
                        res.getInt("CantidadRemitoCompra"),
                        res.getInt("CantidadRecibidaCompra"),
                        res.getInt("IdLibroCompra"),
                        res.getInt("IdRemitoCompra")
                        );
            }
        }
        catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        finally {
            try {
                res.close();
                System.out.println("se cerror la coneccion");
            } catch (SQLException sqlException) {
                System.out.println(sqlException.getMessage());
                close();
            }
        }
        return resultado;
    }
   public List<remitoCompraLibro> getAllRemitoPorId(int id){
    //Realiza un select a la BD y guarda todos las facturas de compra
        
    List<remitoCompraLibro> resultado= null;//Resultados de la consulta a la BD
    ResultSet res=null;
    
        try {
            select = connection.prepareStatement(LineaRemitoPorId);
            select.setInt(1,id);
            res = select.executeQuery();
            resultado = new ArrayList<>();

            while (res.next()) {
                remitoCompraLibro pfactura = new remitoCompraLibro(
                        res.getInt("IdRemitoCompra_Libro"), 
                        res.getInt("CantidadRemitoCompra"),
                        res.getInt("CantidadRecibidaCompra"),
                        res.getInt("IdLibroCompra"),
                        res.getInt("IdRemitoCompra")
                        );
                resultado.add(pfactura);//agrego la factura a la lista
            }
        } 
        catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        finally {
            try {
                res.close();
                System.out.println("se cerror la coneccion");
            } catch (SQLException sqlException) {
                System.out.println(sqlException.getMessage());
//                close();
            }
        }
        return resultado;
    } 
   public int agregarLineaRemito(remitoCompraLibro pRemito) {
        int result = 0;

        try {
            insert = connection.prepareStatement(nuevoLineaRemito);
                        insert.setInt(1,pRemito.getCantidadRemito());
                        insert.setInt(2,pRemito.getCantidadRecibida());
                        insert.setInt(3,pRemito.getIdRemitoCompra());
                        insert.setInt(4,pRemito.getIdLibro());
                      
            System.out.println(insert);
            result = insert.executeUpdate();
        } catch (SQLException sqlException) {
            System.out.println("error al insertar linea remito " + sqlException.getMessage());
            close();
        }
        
        System.out.println(result);
        return result;
    }
   public int modificarLineaRemito(remitoCompraLibro pRemito){
        int result = 0;
       System.out.println("CANTIDAD:"+pRemito.getCantidadRemito());
        try {
            update = connection.prepareStatement(modLineaRemito);
                        update.setInt(1,pRemito.getCantidadRemito());
                        update.setInt(2,pRemito.getCantidadRecibida());
                        update.setInt(3,pRemito.getIdRemitoCompra());
                        update.setInt(4,pRemito.getIdLibro());
                        update.setInt(5,pRemito.getIdRemitoCompraLibro());
                        
                        
            System.out.println(update);
            result = update.executeUpdate();
        } catch (SQLException sqlException) {
            System.out.println("error al modificar Linea remitoCompra" + sqlException.getMessage());
            close();
        }
        
        System.out.println(result);
        return result;
    
    
    }
   public int eliminaLinearRemito(int idRemito) {
        int result = 0;

        try {
            delete = connection.prepareStatement(bajaLineaRemito);
            delete.setInt(1, idRemito);
            result = delete.executeUpdate();
        } catch (SQLException sqlException) {
            System.out.println("error al eliminar Linea remito compra" + sqlException.getMessage());
            close();
        }
        
        System.out.println(result);
        return result;
    
    }
   
   //LINEA LIBRO_PROVEEDOR
   
   public LineaProveedorLibro getLineaPlId(int id){
       LineaProveedorLibro resultado= null;
       ResultSet res=null;
    
        try {
            select = connection.prepareStatement(UnaLineaLpId);
            select.setInt(1,id);
            res = select.executeQuery();

            while (res.next()) {
                resultado = new LineaProveedorLibro(
                        res.getInt("IdLibroProveedor"), 
                        res.getInt("IdLibro"),
                        res.getInt("IdProveedor")
                        );
            }
        }
        catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        finally {
            try {
                res.close();
                System.out.println("se cerror la coneccion");
            } catch (SQLException sqlException) {
                System.out.println(sqlException.getMessage());
                close();
            }
        }
        return resultado;
    }
   public List<LineaProveedorLibro> getAllPlId(int id){
    //Realiza un select a la BD y guarda todos las facturas de compra
        
    List<LineaProveedorLibro> resultado= null;//Resultados de la consulta a la BD
    ResultSet res=null;
    
        try {
            select = connection.prepareStatement(LineaLp);
            select.setInt(1,id);
            res = select.executeQuery();
            resultado = new ArrayList<>();

            while (res.next()) {
                LineaProveedorLibro pfactura = new LineaProveedorLibro(
                        res.getInt("IdLibroProveedor"), 
                        res.getInt("IdLibro"),
                        res.getInt("Idproveedor")
                        );
                resultado.add(pfactura);//agrego la factura a la lista
            }
        } 
        catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        finally {
            try {
                res.close();
                System.out.println("se cerror la coneccion");
            } catch (SQLException sqlException) {
                System.out.println(sqlException.getMessage());
//                close();
            }
        }
        return resultado;
    } 
   public int agregarLp(LineaProveedorLibro pRemito) {
        int result = 0;

        try {
            insert = connection.prepareStatement(nuevoLp);
                        insert.setInt(1,pRemito.getIdLibro());
                        insert.setInt(2,pRemito.getIdProveedor());
                       
            System.out.println(insert);
            result = insert.executeUpdate();
        } catch (SQLException sqlException) {
            System.out.println("error al insertar Prov_Libro " + sqlException.getMessage());
            close();
        }
        
        System.out.println(result);
        return result;
    }
   public int modificarLp(LineaProveedorLibro pRemito){
        int result = 0;
        try {
            update = connection.prepareStatement(modLp);
                        update.setInt(1,pRemito.getIdLibro());
                        update.setInt(2,pRemito.getIdProveedor());
                        update.setInt(3,pRemito.getId());
            System.out.println(update);
            result = update.executeUpdate();
        } catch (SQLException sqlException) {
            System.out.println("error al modificar Libro_Prov" + sqlException.getMessage());
            close();
        }
        
        System.out.println(result);
        return result;
    
    
    }
   public int eliminaLp(int idRemito) {
        int result = 0;

        try {
            delete = connection.prepareStatement(bajaLp);
            delete.setInt(1, idRemito);
            result = delete.executeUpdate();
            System.out.println(delete);
        } catch (SQLException sqlException) {
            System.out.println("error al eliminar Libro_Prov" + sqlException.getMessage());
            close();
        }
        
        System.out.println(result);
        return result;
    
    }
   public int getIdUltimaLP(){
    //Realiza un select a la BD y guarda todos los clientes recogidos en un array 
        
    int resultado=0;//Resultados de la consulta a la BD
    ResultSet res=null;
    
        try {
            select = connection.prepareStatement(idULP);
            res = select.executeQuery();
          
            while (res.next()) {
            resultado=res.getInt("id");
            }
        } 
        catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        finally {
            try {
                res.close();
                System.out.println("se cerror la coneccion");
            } catch (SQLException sqlException) {
                System.out.println(sqlException.getMessage());
                close();
            }
        }
        return resultado;
    } 
   
   //PROVEEDORES
   public List<proveedor> getAllProveedor(){
    //Realiza un select a la BD y guarda todos las facturas de compra
        
    List<proveedor> resultado= null;//Resultados de la consulta a la BD
    ResultSet res=null;
    
        try {
            select = connection.prepareStatement(TodosProveedores);
            res = select.executeQuery();
            resultado = new ArrayList<>();

            while (res.next()) {
                proveedor pProveedor = new proveedor(
                        res.getInt("IdProveedor"), 
                        res.getString("NombreProveedor"),
                        res.getString("DireccionProveedor"),
                        res.getString("CuitProveedor"), 
                        res.getInt("IdLocalidadProveedor"),
                        res.getString("TelProveedor"),
                        res.getBoolean("EstadoProveedor")
                        );
                        
                resultado.add(pProveedor);//agrego la factura a la lista
            }
        } 
        catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        finally {
            try {
                res.close();
                System.out.println("se cerror la coneccion");
            } catch (SQLException sqlException) {
                System.out.println(sqlException.getMessage());
//                close();
            }
        }
        return resultado;
    } 
   public List<proveedor> getAllProveedorHab(){
    //Realiza un select a la BD y guarda todos las facturas de compra
        
    List<proveedor> resultado= null;//Resultados de la consulta a la BD
    ResultSet res=null;
    
        try {
            select = connection.prepareStatement(TodosProveedoresHab);
            res = select.executeQuery();
            resultado = new ArrayList<>();

            while (res.next()) {
                proveedor pProveedor = new proveedor(
                        res.getInt("IdProveedor"), 
                        res.getString("NombreProveedor"),
                        res.getString("DireccionProveedor"),
                        res.getString("CuitProveedor"), 
                        res.getInt("IdLocalidadProveedor"),
                        res.getString("TelProveedor"),
                        res.getBoolean("EstadoProveedor")
                        );
                        
                resultado.add(pProveedor);//agrego la factura a la lista
            }
        } 
        catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        finally {
            try {
                res.close();
                System.out.println("se cerror la coneccion");
            } catch (SQLException sqlException) {
                System.out.println(sqlException.getMessage());
//                close();
            }
        }
        return resultado;
    } 
   public proveedor getProveedorPorId(int id){
    //Realiza un select a la BD y guarda todos los clientes recogidos en un array 
        
    proveedor resultado= null;//Resultados de la consulta a la BD
    ResultSet res=null;
    
        try {
            select = connection.prepareStatement(ProveedorPorId);
            select.setInt(1,id);
            res = select.executeQuery();
           
            while (res.next()) {
            
                resultado = new proveedor(
                        res.getInt("IdProveedor"), 
                        res.getString("NombreProveedor"),
                        res.getString("DireccionProveedor"),
                        res.getString("CuitProveedor"), 
                        res.getInt("IdLocalidadProveedor"),
                        res.getString("TelProveedor"),
                        res.getBoolean("EstadoProveedor")
                        );
            }
        }
        catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        finally {
            try {
                res.close();
                System.out.println("se cerror la coneccion");
            } catch (SQLException sqlException) {
                System.out.println(sqlException.getMessage());
                close();
            }
        }
        return resultado;
    }
   public proveedor getProveedorPorNombre(String id){
    //Realiza un select a la BD y guarda todos los clientes recogidos en un array 
        
    proveedor resultado= null;//Resultados de la consulta a la BD
    ResultSet res=null;
    
        try {
            select = connection.prepareStatement(ProveedorPorNombre);
            select.setString(1,id);
            res = select.executeQuery();
           
            while (res.next()) {
            
                resultado = new proveedor(
                        res.getInt("IdProveedor"), 
                        res.getString("NombreProveedor"),
                        res.getString("DireccionProveedor"),
                        res.getString("CuitProveedor"), 
                        res.getInt("IdLocalidadProveedor"),
                        res.getString("TelProveedor"),
                        res.getBoolean("EstadoProveedor")
                        );
            }
        }
        catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        finally {
            try {
                res.close();
                System.out.println("se cerror la coneccion");
            } catch (SQLException sqlException) {
                System.out.println(sqlException.getMessage());
                close();
            }
        }
        return resultado;
    }
   public int modificarProveedor(proveedor pProveedor){
        int result = 0;

        try {
            update = connection.prepareStatement(modProveedor);
            
            update.setString(1, pProveedor.getCuitProveedor());
            update.setString(2, pProveedor.getNombreProveedor());
            update.setString(3, pProveedor.getDireccionProveedor());
            update.setString(4, pProveedor.getTelProveedor());
            update.setInt(5, pProveedor.getIdLocalidad());
            update.setBoolean(6, pProveedor.getEstadoProveedor());
            update.setInt(7, pProveedor.getIdProveedor());
            
            
            System.out.println(update);
            result = update.executeUpdate();
        } catch (SQLException sqlException) {
            System.out.println("error al modificar proveedor" + sqlException.getMessage());
            close();
        }
        
        System.out.println(result);
        return result;
    
    
    }
   public int agregarProveedor(proveedor pProveedor) {
        int result = 0;

        try {
            insert = connection.prepareStatement(nuevoProveedor);
            insert.setString(1, pProveedor.getCuitProveedor());
            insert.setString(2, pProveedor.getNombreProveedor());
            insert.setString(3, pProveedor.getDireccionProveedor());
            insert.setString(4, pProveedor.getTelProveedor());
            insert.setInt(5, pProveedor.getIdLocalidad());
            
            
            System.out.println(insert);
            result = insert.executeUpdate();
        } catch (SQLException sqlException) {
            System.out.println("error al insertar proveedor " + sqlException.getMessage());
            close();
        }
        
        System.out.println(result);
        return result;
    }
   public int getIdUltimaProv(){
    int resultado=0;//Resultados de la consulta a la BD
    ResultSet res=null;
    
        try {
            select = connection.prepareStatement(ultimoProv);
            res = select.executeQuery();
          
            while (res.next()) {
            resultado=res.getInt("id");
            }
        } 
        catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        finally {
            try {
                res.close();
                System.out.println("se cerror la coneccion");
            } catch (SQLException sqlException) {
                System.out.println(sqlException.getMessage());
                close();
            }
        }
        return resultado;
    } 
   public int deshabilitarProveedor(proveedor pProveedor){
        int result = 0;

        try {
            update = connection.prepareStatement(DesHabProveedor);
            
            update.setBoolean(1, pProveedor.getEstadoProveedor());
            update.setInt(2, pProveedor.getIdProveedor());
            
            
            System.out.println(update);
            result = update.executeUpdate();
        } catch (SQLException sqlException) {
            System.out.println("error al deshabilitar proveedor" + sqlException.getMessage());
            close();
        }
        
        System.out.println(result);
        return result;
    
    
    }
    
   public List<Libro> getAllLibro(boolean estadoLibro){
    //Realiza un select a la BD y guarda todos los clientes recogidos en un array 
        
    List<Libro> resultado= null;//Resultados de la consulta a la BD
    ResultSet res=null;
    
        try {
            select = connection.prepareStatement(seleccionarLibros);
            select.setBoolean(1, estadoLibro);
            res = select.executeQuery();
            resultado = new ArrayList<>();

            while (res.next()) {

                Libro libro = new Libro(
                          res.getInt("IdLibro"),
                        res.getString("TituloLibro"),
                        res.getString("EdicionLibro"),
                        res.getInt("AñoLibro"),
                        res.getFloat("PrecioLibro"),
                        res.getInt("StockLibro"),
                        res.getInt("StockCritico"),
                        res.getInt("IdEditorialLibro"),
                        res.getInt("IdAutorLibro"),
                        res.getInt("IdGeneroLibro"),
                        res.getBoolean("EstadoLibro"),
                        res.getFloat("CostoLibro"),
                        res.getInt("StockMax")           
                        );
                        
                resultado.add(libro);//agrego el cliente al array
            }
        } 
        catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        finally {
            try {
                res.close();
                System.out.println("se cerror la coneccion");
            } catch (SQLException sqlException) {
                System.out.println(sqlException.getMessage());
                close();
            }
        }
        return resultado;
    }
   
   public List<PedidoCompra> getAllPedidoPorLibro(Libro lib){
    //Realiza un select a la BD y guarda todos los clientes recogidos en un array
    List<PedidoCompra> resultado= null;//Resultados de la consulta a la BD
    ResultSet res=null;
    
    TodasPedPorFecha = "select * "
            + " from PedidoCompra as p, LineaPedido as lp"
            + " where "
            + " lp.IdLibroCompra = " + "?" + " AND"
            + " p.IdPedidoCompra = lp.IdPedidoCompra";
        try {
            select = connection.prepareStatement(TodasPedPorFecha );
            select.setInt(1,lib.getIdLibro());
            res = select.executeQuery();
            resultado = new ArrayList<>();

            while (res.next()) {
                PedidoCompra pfactura = new PedidoCompra(
                         res.getInt("IdPedidoCompra"), 
                        res.getDate("FechaPedido"),
                        res.getInt("IdProveedorCompra"),
                        res.getString("EstadoPedido"),
                        res.getFloat("TotalPedidoCompra"));
                        
                resultado.add(pfactura);//agrego la factura a la lista
            }
        } 
        catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        finally {
            try {
                res.close();
                System.out.println("se cerror la coneccion");
            } catch (SQLException sqlException) {
                System.out.println(sqlException.getMessage());
//                close();
            }
        }
        return resultado;
    }
  
  public List<PedidoCompra> getAllPedidoPorProveedor(proveedor cli){
    //Realiza un select a la BD y guarda todos los clientes recogidos en un array 
        
    List<PedidoCompra> resultado= null;//Resultados de la consulta a la BD
    ResultSet res=null;
    TodasPedPorFecha = "select * "
            + " from PedidoCompra"
            + " where "
            + " IdProveedorCompra = " + "?";
        try {
            select = connection.prepareStatement(TodasPedPorFecha );
            select.setInt(1,cli.getIdProveedor());
            res = select.executeQuery();
            resultado = new ArrayList<>();

            while (res.next()) {
                PedidoCompra pfactura = new PedidoCompra(
                         res.getInt("IdPedidoCompra"), 
                        res.getDate("FechaPedido"),
                        res.getInt("IdProveedorCompra"),
                        res.getString("EstadoPedido"),
                        res.getFloat("TotalPedidoCompra"));
                        
                resultado.add(pfactura);//agrego la factura a la lista
            }
        } 
        catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        finally {
            try {
                res.close();
                System.out.println("se cerror la coneccion");
            } catch (SQLException sqlException) {
                System.out.println(sqlException.getMessage());
//                close();
            }
        }
        return resultado;
    }
  
  public List<PedidoCompra> getAllPedidoPorEstado(String estado){
    //Realiza un select a la BD y guarda todos los clientes recogidos en un array 
        
    List<PedidoCompra> resultado= null;//Resultados de la consulta a la BD
    ResultSet res=null;
    TodasPedPorFecha = "select * "
            + " from PedidoCompra"
            + " where "
            + " EstadoPedido = " + "?";
        try {
            select = connection.prepareStatement(TodasPedPorFecha );
            select.setString(1,estado);
            res = select.executeQuery();
            resultado = new ArrayList<>();

            while (res.next()) {
                PedidoCompra pfactura = new PedidoCompra(
                         res.getInt("IdPedidoCompra"), 
                        res.getDate("FechaPedido"),
                        res.getInt("IdProveedorCompra"),
                        res.getString("EstadoPedido"),
                        res.getFloat("TotalPedidoCompra"));
                        
                resultado.add(pfactura);//agrego la factura a la lista
            }
        } 
        catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        finally {
            try {
                res.close();
                System.out.println("se cerror la coneccion");
            } catch (SQLException sqlException) {
                System.out.println(sqlException.getMessage());
//                close();
            }
        }
        return resultado;
    }
  
  public List<PedidoCompra> getAllPedidoPorFechayLibro(Libro lib, Date fde, Date fha){
    //Realiza un select a la BD y guarda todos los clientes recogidos en un array
    List<PedidoCompra> resultado= null;//Resultados de la consulta a la BD
    ResultSet res=null;
    
    TodasPedPorFecha = "select * "
            + " from PedidoCompra as p, LineaPedido as lp"
            + " where "
            + " lp.IdLibroCompra = " + "?" + " AND"
            + " p.FechaPedido between \'" + fde + "\' AND \'" + fha + "\'" + " AND"
            + " p.IdPedidoCompra = lp.IdPedidoCompra";
        try {
            select = connection.prepareStatement(TodasPedPorFecha );
            select.setInt(1,lib.getIdLibro());
            res = select.executeQuery();
            resultado = new ArrayList<>();

            while (res.next()) {
                PedidoCompra pfactura = new PedidoCompra(
                         res.getInt("IdPedidoCompra"), 
                        res.getDate("FechaPedido"),
                        res.getInt("IdProveedorCompra"),
                        res.getString("EstadoPedido"),
                        res.getFloat("TotalPedidoCompra"));
                        
                resultado.add(pfactura);//agrego la factura a la lista
            }
        } 
        catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        finally {
            try {
                res.close();
                System.out.println("se cerror la coneccion");
            } catch (SQLException sqlException) {
                System.out.println(sqlException.getMessage());
//                close();
            }
        }
        return resultado;
    }
  public List<PedidoCompra> getAllPedidoPorFechayProveedor(proveedor cli, Date fde, Date fha){
    //Realiza un select a la BD y guarda todos los clientes recogidos en un array 
        
    List<PedidoCompra> resultado= null;//Resultados de la consulta a la BD
    ResultSet res=null;
    TodasPedPorFecha = "select * "
            + " from PedidoCompra"
            + " where "
            + " IdProveedorCompra = " + "?" + " AND"
            + " FechaPedido between \'" + fde + "\' AND \'" + fha + "\'";
        try {
            select = connection.prepareStatement(TodasPedPorFecha );
            select.setInt(1,cli.getIdProveedor());
            res = select.executeQuery();
            resultado = new ArrayList<>();

            while (res.next()) {
                PedidoCompra pfactura = new PedidoCompra(
                         res.getInt("IdPedidoCompra"), 
                        res.getDate("FechaPedido"),
                        res.getInt("IdProveedorCompra"),
                        res.getString("EstadoPedido"),
                        res.getFloat("TotalPedidoCompra"));
                        
                resultado.add(pfactura);//agrego la factura a la lista
            }
        } 
        catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        finally {
            try {
                res.close();
                System.out.println("se cerror la coneccion");
            } catch (SQLException sqlException) {
                System.out.println(sqlException.getMessage());
//                close();
            }
        }
        return resultado;
    }
  
  public List<PedidoCompra> getAllPedidoPorFechayEstado(String estado, Date fde, Date fha){
    //Realiza un select a la BD y guarda todos los clientes recogidos en un array 
        
    List<PedidoCompra> resultado= null;//Resultados de la consulta a la BD
    ResultSet res=null;
    TodasPedPorFecha = "select * "
            + " from PedidoCompra"
            + " where "
            + " EstadoPedido = " + "?" + " AND"
            + " FechaPedido between \'" + fde + "\' AND \'" + fha + "\'";
        try {
            select = connection.prepareStatement(TodasPedPorFecha );
            select.setString(1,estado);
            res = select.executeQuery();
            resultado = new ArrayList<>();

            while (res.next()) {
                PedidoCompra pfactura = new PedidoCompra(
                         res.getInt("IdPedidoCompra"), 
                        res.getDate("FechaPedido"),
                        res.getInt("IdProveedorCompra"),
                        res.getString("EstadoPedido"),
                        res.getFloat("TotalPedidoCompra"));
                        
                resultado.add(pfactura);//agrego la factura a la lista
            }
        } 
        catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        finally {
            try {
                res.close();
                System.out.println("se cerror la coneccion");
            } catch (SQLException sqlException) {
                System.out.println(sqlException.getMessage());
//                close();
            }
        }
        return resultado;
    }
}
