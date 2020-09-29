/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Venta;


import Empleados.Empleado;
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
 * @author Mauricio
 */
public class ConsultaVenta {
    
    private static final String URL = "jdbc:sqlserver://;database=libreria;integratedSecurity=true;";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Mm-1991";
    private Connection connection = null;
    private int id;
    private PreparedStatement seleccionarTodasFacturasVenta = null;
    private PreparedStatement seleccionarTodasFacturasVenta_Libro = null;
    private PreparedStatement seleccionarTodasFacturasVentaPorFecha = null;
    private PreparedStatement seleccionarTodasFacturasVentaPorCliente = null;
    private PreparedStatement seleccionarFacturaVenta = null;
    private PreparedStatement seleccionarFacturaVentaLibro = null;
    private PreparedStatement seleccionarTodosTipoFacturaVenta = null;
    private PreparedStatement seleccionarTipoFacturaVenta = null;
    private PreparedStatement seleccionarTodosTipoCliente = null;
    private PreparedStatement seleccionarTipoCliente = null;
    private PreparedStatement seleccionarTodosClientes = null;
    private PreparedStatement seleccionarUnClientes = null;
    private PreparedStatement existeCliente = null;
    private PreparedStatement existeTipoCliente = null;
    private PreparedStatement existeTipoFacturaVenta = null;
    private PreparedStatement seleccionarTodosEmpleados = null;
    private PreparedStatement seleccionarUnEmpleados = null;
    private PreparedStatement seleccionarUnLibroPorID = null;
    private PreparedStatement seleccionarUnLibroPorNombre = null;
    private PreparedStatement seleccionarTodosLibros = null;
    private PreparedStatement insertarNuevaFacturaVenta = null;
    private PreparedStatement insertarNuevaFacturaVenta_Libro = null;
    private PreparedStatement insertarTipoFacturaVenta = null;
    private PreparedStatement insertarTipoCliente = null;
    private PreparedStatement insertarCliente = null;
    private PreparedStatement eliminarFacturaVenta_Libro = null;
    private PreparedStatement eliminarTipoFacturaVenta = null;
    private PreparedStatement eliminarTipoCliente = null;
    private PreparedStatement eliminarCliente = null;
    private PreparedStatement modificarFacturaVenta = null;
    private PreparedStatement modificarTipoFacturaVenta = null;
    private PreparedStatement modificarTipoCliente = null;
    private PreparedStatement modificarLibroStock = null;
    private PreparedStatement modificarCliente = null;
    
    private PreparedStatement select = null;
    
    //CONSULTAS
    private String ultimaFactura= "SELECT MAX(IdFacturaVenta) AS id FROM libreria.facturaVenta;";
    private String seleccionarTodas = "select * "
            + " from libreria.FacturaVenta";
    
    private String seleccionarTodasFacturasVentaLibro = "select * from libreria.FacturaVenta_Libro where IdFacturaVenta = " + "?";
    
    private String seleccionarTodasFacturasVentaLibroPorLibro = "select * from libreria.FacturaVenta_Libro where IdLibro = " + "?";
    
    private String seleccionarFacturaVentaLibroPorId = "select * "
            + " from libreria.FacturaVenta_Libro"
            + " where "
            + " IdLibro = " + "?";
    
    private String seleccionarTiposFacturasVentas = "select * from libreria.TipoFacturaVenta";
    
    private String seleccionarTipoFacturaVentaPorId = "select * "
            + " from libreria.TipoFacturaVenta AS tfv"
            + " where "
            + " tfv.IdTipoFacturaVenta =" + "?";
    
    private String existeTipoDeFacturaVenta = "select * from libreria.TipoFacturaVenta where NombreTipoFacturaVenta= "+"?";
    
    private String seleccionarTiposClientes = "select * from libreria.TipoCliente";
    
    private String seleccionarTipoClientePorId = "select * "
            + " from libreria.TipoCliente AS tc"
            + " where "
            + " tc.IdTipoCliente =" + "?";
    
    private String seleccionarTiposClientesPorTipoFactura = "select * "
            + " from libreria.TipoCliente AS tc"
            + " where "
            + " tc.IdTipoFacturaVenta =" + "?";
    
    private String existeTipoDeCliente = "select * from libreria.TipoCliente where NombreTipoCliente= "+"?";
    
    private String seleccionarTodasPorFecha;
    
    private String seleccionarTodasPorCliente="select * "
            + " from libreria.FacturaVenta AS fv "
            + " where "
            + " fv.IdCliente =" + "?";
    
    private String buscarFacturaVenta = "select * "
            + " from libreria.FacturaVenta"
            + " where "
            + " IdFacturaVenta= " + "?";
    
    private String seleccionarClientes=" select * from libreria.Cliente";
    
    private String seleccionarClientePorId = " select * from libreria.Cliente where IdCliente= "+"?";
    
    private String seleccionarClientePorNombre = " select * from libreria.Cliente where NombreCliente != "+"?"+" AND EstadoCliente = "+"?";
    
    private String seleccionarClientePorEstado = " select * from libreria.Cliente where EstadoCliente = "+"?";
    
    private String seleccionarClientePorTipoCliente = " select * from libreria.Cliente where IdTipoCliente = "+"?";
    
    private String existeClientePorDNI = "select * from libreria.Cliente where DniCliente= "+"?";
    
    private String existeClientePorCUIT = "select * from libreria.Cliente where CuitCliente= "+"?";
    
    private String seleccionarEmpleados=" select * from libreria.Empleado where EstadoEmpleado = "+"?";
    
    private String seleccionarEmpleadoPorId = " select * from libreria.Empleado where IdEmpleado= "+"?";
    
    private String seleccionarLibros=" select * from libreria.Libro where EstadoLibro = "+"?";
    
    private String seleccionarLibroPorId = " select * from libreria.Libro where IdLibro= "+"?";
    
    private String seleccionarLibroPorNombre = " select * from libreria.Libro where TituloLibro= "+"?";
    
    //ALTAS
    private String insertarFacturaVenta = "INSERT INTO libreria.FacturaVenta"
            + "(FechaFacturaVenta,BrutoFacturaVenta,IvaFacturaVenta,TotalFacturaVenta,Anulada,IdCliente,IdEmpleado)"
            + " VALUES(?,?,?,?,?,?,?)";
    
    private String insertarFacturaVentaLibro = "INSERT INTO libreria.FacturaVenta_Libro"
            + "(Cantidad,Precio,Subtotal,IdFacturaVenta,IdLibro)"
            + " VALUES(?,?,?,?,?)";
    
    private String insertarTipoFactura = "INSERT INTO libreria.TipoFacturaVenta"
            + "(NombreTipoFacturaVenta,DiscriminaIva)"
            + " VALUES(?,?)";
    
    private String insertarTipoCli = "INSERT INTO libreria.TipoCliente"
            + "(NombreTipoCliente,IdTipoFacturaVenta)"
            + " VALUES(?,?)";
    
    private String insertarCli = "INSERT INTO libreria.Cliente"
            + "(DniCliente,CuitCliente,NombreCliente,DireccionCliente,TelefonoCliente,EmailCliente,EstadoCliente,IdLocalidadCliente,IdTipoCliente)"
            + " VALUES(?,?,?,?,?,?,?,?,?)";
    //BAJAS
    private String bajaFacturaVentaLibro ="delete from libreria.FacturaVenta_Libro where IdLibro= " + "?";
    private String bajaTipoFacturaVenta ="delete from libreria.TipoFacturaVenta where IdTipoFacturaVenta= " + "?";
    private String bajaTipoCliente ="delete from libreria.TipoCliente where IdTipoCliente= " + "?";
    private String bajaCliente ="delete from libreria.Cliente where IdCliente= " + "?";
        
    //MODIFICACIONES
    private String modFacturaVenta ="update libreria.FacturaVenta set "
            +" Anulada= " + "?"
            +" where IdFacturaVenta = "+"?";
    private String modTipoFacturaVenta ="update libreria.TipoFacturaVenta set "+"NombreTipoFacturaVenta=" +"?"+","+"DiscriminaIva=" +"?"+" where IdTipoFacturaVenta = "+"?";
    private String modTipoCliente ="update libreria.TipoCliente set "+"NombreTipoCliente=" +"?"+","+"IdTipoFacturaVenta=" +"?"+" where IdTipoCliente = "+"?";
    private String modCliente ="update libreria.Cliente set "+"DniCliente=" +"?"+","+"CuitCliente="+"?"+","+"NombreCliente="+"?"+","+"DireccionCliente="+"?"+","+"TelefonoCliente="+"?"+","+"EmailCliente="+"?"+","+"IdLocalidadCliente=" +"?"+","+"IdTipoCliente=" +"?"+" where IdCliente= "+"?";
    private String modClienteEstado ="update libreria.Cliente set "+"EstadoCliente=" +"?"+" where IdCliente= "+"?";
    private String modLibroStock ="update libreria.Libro set "
            +"StockLibro = " + "?"
            +" where IdLibro = "+"?";
    
   public ConsultaVenta() {
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
   public int getIdUltimaFactura(){
    int resultado=0;//Resultados de la consulta a la BD
    ResultSet res=null;
    
        try {
            select = getConnection().prepareStatement(ultimaFactura);
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
   public List<FacturaVenta> getAllFacturas(){
    //Realiza un select a la BD y guarda todos las facturas de compra
        
    List<FacturaVenta> resultado= null;//Resultados de la consulta a la BD
    ResultSet res=null;
    
        try {
            seleccionarTodasFacturasVenta = getConnection().prepareStatement(seleccionarTodas);
            res = seleccionarTodasFacturasVenta.executeQuery();
            resultado = new ArrayList<>();

            while (res.next()) {
                FacturaVenta pfactura = new FacturaVenta(
                        res.getInt("IdFacturaVenta"), 
                        res.getDate("FechaFacturaVenta"),
                        res.getFloat("BrutoFacturaVenta"),
                        res.getFloat("IvaFacturaVenta"),
                        res.getFloat("TotalFacturaVenta"),
                        res.getBoolean("Anulada"),
                        res.getInt("IdCliente"),
                        res.getInt("IdEmpleado"));
                        
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
                close();
            }
        }
        return resultado;
    } 
   
 
   public List<FacturaVentaLibro> getAllFacturaVentaLibro(int idFactura){
    //Realiza un select a la BD y guarda todos los clientes recogidos en un array 
        
    List<FacturaVentaLibro> resultado= null;//Resultados de la consulta a la BD
    ResultSet res=null;
    
        try {
            seleccionarTodasFacturasVenta_Libro = getConnection().prepareStatement(seleccionarTodasFacturasVentaLibro);
            seleccionarTodasFacturasVenta_Libro.setInt(1, idFactura);
            res = seleccionarTodasFacturasVenta_Libro.executeQuery();
            resultado = new ArrayList<>();
            while (res.next()) {
                FacturaVentaLibro pVentaLibro = new FacturaVentaLibro(res.getInt("Cantidad"),res.getFloat("Precio"),res.getFloat("Subtotal"),res.getInt("IdFacturaVenta"),res.getInt("IdLibro"));
                        
                         
                        
                resultado.add(pVentaLibro);//agrego al cliente al array
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
   
   public List<FacturaVentaLibro> getAllFacturaVentaLibro2(int idLibro){
    //Realiza un select a la BD y guarda todos los clientes recogidos en un array 
        
    List<FacturaVentaLibro> resultado= null;//Resultados de la consulta a la BD
    ResultSet res=null;
    
        try {
            seleccionarTodasFacturasVenta_Libro = getConnection().prepareStatement(seleccionarTodasFacturasVentaLibroPorLibro);
            seleccionarTodasFacturasVenta_Libro.setInt(1, idLibro);
            res = seleccionarTodasFacturasVenta_Libro.executeQuery();
            resultado = new ArrayList<>();
            while (res.next()) {
                FacturaVentaLibro pVentaLibro = new FacturaVentaLibro(res.getInt("Cantidad"),res.getFloat("Precio"),res.getFloat("Subtotal"),res.getInt("IdFacturaVenta"),res.getInt("IdLibro"));
                        
                         
                        
                resultado.add(pVentaLibro);//agrego al cliente al array
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
  public List<FacturaVenta> getAllFacturaVentaPorLibro(Libro lib){
    //Realiza un select a la BD y guarda todos los clientes recogidos en un array
    List<FacturaVenta> resultado= null;//Resultados de la consulta a la BD
    ResultSet res=null;
    
    seleccionarTodasPorFecha = "select * "
            + " from FacturaVenta as f, FacturaVenta_Libro as fl"
            + " where "
            + " fl.IdLibro = " + "?" + " AND"
            + " f.IdFacturaVenta = fl.IdFacturaVenta";
        try {
            seleccionarTodasFacturasVentaPorFecha = getConnection().prepareStatement(seleccionarTodasPorFecha);
            System.out.println(seleccionarTodasPorFecha);
            seleccionarTodasFacturasVentaPorFecha.setInt(1,lib.getIdLibro());
            //seleccionarTodasFacturasVentaPorFecha.setDate(2,fha);
            res = seleccionarTodasFacturasVentaPorFecha.executeQuery();
            resultado = new ArrayList<>();

            while (res.next()) {
               FacturaVenta pFacturaVenta = new FacturaVenta(
                        res.getInt("IdFacturaVenta"), 
                        res.getDate("FechaFacturaVenta"),
                        res.getFloat("BrutoFacturaVenta"),
                        res.getFloat("IvaFacturaVenta"),
                        res.getFloat("TotalFacturaVenta"),
                        res.getBoolean("Anulada"),
                        res.getInt("IdCliente"),
                        res.getInt("IdEmpleado"));
                resultado.add(pFacturaVenta);//agrego al cliente al array
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
  
  public List<FacturaVenta> getAllFacturaVentaPorCliente(Cliente cli){
    //Realiza un select a la BD y guarda todos los clientes recogidos en un array 
        
    List<FacturaVenta> resultado= null;//Resultados de la consulta a la BD
    ResultSet res=null;
    seleccionarTodasPorFecha = "select * "
            + " from FacturaVenta"
            + " where "
            + " IdCliente = " + "?";
        try {
            seleccionarTodasFacturasVentaPorFecha = getConnection().prepareStatement(seleccionarTodasPorFecha);
            System.out.println(seleccionarTodasPorFecha);
            seleccionarTodasFacturasVentaPorFecha.setInt(1,cli.getIdCliente());
            //seleccionarTodasFacturasVentaPorFecha.setDate(2,fha);
            res = seleccionarTodasFacturasVentaPorFecha.executeQuery();
            resultado = new ArrayList<>();

            while (res.next()) {
               FacturaVenta pFacturaVenta = new FacturaVenta(
                        res.getInt("IdFacturaVenta"), 
                        res.getDate("FechaFacturaVenta"),
                        res.getFloat("BrutoFacturaVenta"),
                        res.getFloat("IvaFacturaVenta"),
                        res.getFloat("TotalFacturaVenta"),
                        res.getBoolean("Anulada"),
                        res.getInt("IdCliente"),
                        res.getInt("IdEmpleado"));
                resultado.add(pFacturaVenta);//agrego al cliente al array
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
  
  public List<FacturaVenta> getAllFacturaVentaPorEmpleado(Empleado cli){
    //Realiza un select a la BD y guarda todos los clientes recogidos en un array 
        
    List<FacturaVenta> resultado= null;//Resultados de la consulta a la BD
    ResultSet res=null;
    seleccionarTodasPorFecha = "select * "
            + " from FacturaVenta"
            + " where "
            + " IdEmpleado = " + "?";
        try {
            seleccionarTodasFacturasVentaPorFecha = getConnection().prepareStatement(seleccionarTodasPorFecha);
            System.out.println(seleccionarTodasPorFecha);
            seleccionarTodasFacturasVentaPorFecha.setInt(1,cli.getIdEmpleado());
            //seleccionarTodasFacturasVentaPorFecha.setDate(2,fha);
            res = seleccionarTodasFacturasVentaPorFecha.executeQuery();
            resultado = new ArrayList<>();

            while (res.next()) {
               FacturaVenta pFacturaVenta = new FacturaVenta(
                        res.getInt("IdFacturaVenta"), 
                        res.getDate("FechaFacturaVenta"),
                        res.getFloat("BrutoFacturaVenta"),
                        res.getFloat("IvaFacturaVenta"),
                        res.getFloat("TotalFacturaVenta"),
                        res.getBoolean("Anulada"),
                        res.getInt("IdCliente"),
                        res.getInt("IdEmpleado"));
                resultado.add(pFacturaVenta);//agrego al cliente al array
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
  
  public List<FacturaVenta> getAllFacturaVentaPorFechayLibro(Libro lib, Date fde, Date fha){
    //Realiza un select a la BD y guarda todos los clientes recogidos en un array
    List<FacturaVenta> resultado= null;//Resultados de la consulta a la BD
    ResultSet res=null;
    
    seleccionarTodasPorFecha = "select * "
            + " from FacturaVenta as f, FacturaVenta_Libro as fl"
            + " where "
            + " fl.IdLibro = " + "?" + " AND"
            + " f.FechaFacturaVenta between \'" + fde + "\' AND \'" + fha + "\'" + " AND"
            + " f.IdFacturaVenta = fl.IdFacturaVenta";
        try {
            seleccionarTodasFacturasVentaPorFecha = getConnection().prepareStatement(seleccionarTodasPorFecha);
            System.out.println(seleccionarTodasPorFecha);
            seleccionarTodasFacturasVentaPorFecha.setInt(1,lib.getIdLibro());
            //seleccionarTodasFacturasVentaPorFecha.setDate(2,fha);
            res = seleccionarTodasFacturasVentaPorFecha.executeQuery();
            resultado = new ArrayList<>();

            while (res.next()) {
               FacturaVenta pFacturaVenta = new FacturaVenta(
                        res.getInt("IdFacturaVenta"), 
                        res.getDate("FechaFacturaVenta"),
                        res.getFloat("BrutoFacturaVenta"),
                        res.getFloat("IvaFacturaVenta"),
                        res.getFloat("TotalFacturaVenta"),
                        res.getBoolean("Anulada"),
                        res.getInt("IdCliente"),
                        res.getInt("IdEmpleado"));
                resultado.add(pFacturaVenta);//agrego al cliente al array
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
  public List<FacturaVenta> getAllFacturaVentaPorFechayCliente(Cliente cli, Date fde, Date fha){
    //Realiza un select a la BD y guarda todos los clientes recogidos en un array 
        
    List<FacturaVenta> resultado= null;//Resultados de la consulta a la BD
    ResultSet res=null;
    seleccionarTodasPorFecha = "select * "
            + " from FacturaVenta"
            + " where "
            + " IdCliente = " + "?" + " AND"
            + " FechaFacturaVenta between \'" + fde + "\' AND \'" + fha + "\'";
        try {
            seleccionarTodasFacturasVentaPorFecha = getConnection().prepareStatement(seleccionarTodasPorFecha);
            System.out.println(seleccionarTodasPorFecha);
            seleccionarTodasFacturasVentaPorFecha.setInt(1,cli.getIdCliente());
            //seleccionarTodasFacturasVentaPorFecha.setDate(2,fha);
            res = seleccionarTodasFacturasVentaPorFecha.executeQuery();
            resultado = new ArrayList<>();

            while (res.next()) {
               FacturaVenta pFacturaVenta = new FacturaVenta(
                        res.getInt("IdFacturaVenta"), 
                        res.getDate("FechaFacturaVenta"),
                        res.getFloat("BrutoFacturaVenta"),
                        res.getFloat("IvaFacturaVenta"),
                        res.getFloat("TotalFacturaVenta"),
                        res.getBoolean("Anulada"),
                        res.getInt("IdCliente"),
                        res.getInt("IdEmpleado"));
                resultado.add(pFacturaVenta);//agrego al cliente al array
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
  
  public List<FacturaVenta> getAllFacturaVentaPorFechayEmpleado(Empleado cli, Date fde, Date fha){
    //Realiza un select a la BD y guarda todos los clientes recogidos en un array 
        
    List<FacturaVenta> resultado= null;//Resultados de la consulta a la BD
    ResultSet res=null;
    seleccionarTodasPorFecha = "select * "
            + " from FacturaVenta"
            + " where "
            + " IdEmpleado = " + "?" + " AND"
            + " FechaFacturaVenta between \'" + fde + "\' AND \'" + fha + "\'";
        try {
            seleccionarTodasFacturasVentaPorFecha = getConnection().prepareStatement(seleccionarTodasPorFecha);
            System.out.println(seleccionarTodasPorFecha);
            seleccionarTodasFacturasVentaPorFecha.setInt(1,cli.getIdEmpleado());
            //seleccionarTodasFacturasVentaPorFecha.setDate(2,fha);
            res = seleccionarTodasFacturasVentaPorFecha.executeQuery();
            resultado = new ArrayList<>();

            while (res.next()) {
               FacturaVenta pFacturaVenta = new FacturaVenta(
                        res.getInt("IdFacturaVenta"), 
                        res.getDate("FechaFacturaVenta"),
                        res.getFloat("BrutoFacturaVenta"),
                        res.getFloat("IvaFacturaVenta"),
                        res.getFloat("TotalFacturaVenta"),
                        res.getBoolean("Anulada"),
                        res.getInt("IdCliente"),
                        res.getInt("IdEmpleado"));
                resultado.add(pFacturaVenta);//agrego al cliente al array
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
  
   public List<FacturaVenta> getAllFacturaVentaPorCliente(int idCliente){
    //Realiza un select a la BD y guarda todos los clientes recogidos en un array 
        
    List<FacturaVenta> resultado= null;//Resultados de la consulta a la BD
    ResultSet res=null;
    
        try {
            seleccionarTodasFacturasVentaPorCliente = getConnection().prepareStatement(seleccionarTodasPorCliente);
            seleccionarTodasFacturasVentaPorCliente.setInt(1,idCliente);
            res = seleccionarTodasFacturasVentaPorCliente.executeQuery();
            
            resultado = new ArrayList<>();

            while (res.next()) {
               FacturaVenta pFacturaVenta = new FacturaVenta(
                        res.getInt("IdFacturaVenta"), 
                        res.getDate("FechaFacturaVenta"),
                        res.getFloat("BrutoFacturaVenta"),
                        res.getFloat("IvaFacturaVenta"),
                        res.getFloat("TotalFacturaVenta"),
                        res.getBoolean("Anulada"),
                        res.getInt("IdCliente"),
                        res.getInt("IdEmpleado"));
                resultado.add(pFacturaVenta);//agrego al cliente al array
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
   
   public FacturaVenta getFactura(int idFacturaVenta){
    //Realiza un select a la BD y guarda todos las facturas de compra
        
    FacturaVenta resultado= null;//Resultados de la consulta a la BD
    ResultSet res=null;
    int idc=idFacturaVenta;
    System.out.println("HOLAAAAA:"+idc);
        try {
            seleccionarFacturaVenta = getConnection().prepareStatement(buscarFacturaVenta);
            seleccionarFacturaVenta.setInt(1,idc);
            res = seleccionarFacturaVenta.executeQuery();
            System.out.println(seleccionarFacturaVenta);
            
            while (res.next()) {
               resultado = new FacturaVenta(
                        res.getInt("IdFacturaVenta"), 
                        res.getDate("FechaFacturaVenta"),
                        res.getFloat("BrutoFacturaVenta"),
                        res.getFloat("IvaFacturaVenta"),
                        res.getFloat("TotalFacturaVenta"),
                        res.getBoolean("Anulada"),
                        res.getInt("IdCliente"),
                        res.getInt("IdEmpleado"));
            }
            System.out.println(""+resultado);
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
   
   public List<Empleado> getAllEmpleado(boolean estadoEmpleado){
    //Realiza un select a la BD y guarda todos los clientes recogidos en un array 
        
    List<Empleado> resultado= null;//Resultados de la consulta a la BD
    ResultSet res=null;
    
        try {
            seleccionarTodosEmpleados = getConnection().prepareStatement(seleccionarEmpleados);
            seleccionarTodosEmpleados.setBoolean(1, estadoEmpleado);
            res = seleccionarTodosEmpleados.executeQuery();
            resultado = new ArrayList<>();

            while (res.next()) {

                Empleado empleado = new Empleado(
                        res.getInt("IdEmpleado"),
                        res.getString("DniEmpleado"),
                        res.getString("NombreEmpleado"),
                        res.getString("DomicilioEmpleado"),
                        res.getString("TelefonoEmpleado"),
                        res.getInt("IdLocalidad"),
                        res.getDate("FechaIngreso"),
                        res.getInt("IdCategoriaEmpleado"),
                        res.getInt("IdEstadoCivilEmpleado"),
                        res.getBoolean("EstadoEmpleado")
                        );
                        
                resultado.add(empleado);//agrego el empleado al array
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
   
   public List<Cliente> getAllCliente(){
    //Realiza un select a la BD y guarda todos los clientes recogidos en un array 
        
    List<Cliente> resultado= null;//Resultados de la consulta a la BD
    ResultSet res=null;
    
        try {
            seleccionarTodosClientes = getConnection().prepareStatement(seleccionarClientes);
            res = seleccionarTodosClientes.executeQuery();
            resultado = new ArrayList<>();

            while (res.next()) {

                Cliente cliente = new Cliente(
                        res.getInt("IdCliente"),
                        res.getInt("DniCliente"),
                        res.getString("CuitCliente"),
                        res.getString("NombreCliente"),
                        res.getString("DireccionCliente"),
                        res.getString("TelefonoCliente"),
                        res.getString("EmailCliente"),
                        res.getBoolean("EstadoCliente"),
                        res.getInt("IdLocalidadCliente"),
                        res.getInt("IdTipoCliente")
                        );
                        
                resultado.add(cliente);//agrego el cliente al array
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
   
   public List<Cliente> getAllCliente2(String nombreCliente, boolean estadoCliente){
    //Realiza un select a la BD y guarda todos los clientes recogidos en un array 
        
    List<Cliente> resultado= null;//Resultados de la consulta a la BD
    ResultSet res=null;
    
        try {
            seleccionarTodosClientes = getConnection().prepareStatement(seleccionarClientePorNombre);
            seleccionarTodosClientes.setString(1, nombreCliente);
            seleccionarTodosClientes.setBoolean(2, estadoCliente);
            res = seleccionarTodosClientes.executeQuery();
            resultado = new ArrayList<>();

            while (res.next()) {

                Cliente cliente = new Cliente(
                        res.getInt("IdCliente"),
                        res.getInt("DniCliente"),
                        res.getString("CuitCliente"),
                        res.getString("NombreCliente"),
                        res.getString("DireccionCliente"),
                        res.getString("TelefonoCliente"),
                        res.getString("EmailCliente"),
                        res.getBoolean("EstadoCliente"),
                        res.getInt("IdLocalidadCliente"),
                        res.getInt("IdTipoCliente")
                        );
                        
                resultado.add(cliente);//agrego el cliente al array
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
   
   public List<Cliente> getAllCliente3(int idTipoCliente){
    //Realiza un select a la BD y guarda todos los clientes recogidos en un array 
        
    List<Cliente> resultado= null;//Resultados de la consulta a la BD
    ResultSet res=null;
    
        try {
            seleccionarTodosClientes = getConnection().prepareStatement(seleccionarClientePorTipoCliente);
            seleccionarTodosClientes.setInt(1, idTipoCliente);
            res = seleccionarTodosClientes.executeQuery();
            resultado = new ArrayList<>();

            while (res.next()) {

                Cliente cliente = new Cliente(
                        res.getInt("IdCliente"),
                        res.getInt("DniCliente"),
                        res.getString("CuitCliente"),
                        res.getString("NombreCliente"),
                        res.getString("DireccionCliente"),
                        res.getString("TelefonoCliente"),
                        res.getString("EmailCliente"),
                        res.getBoolean("EstadoCliente"),
                        res.getInt("IdLocalidadCliente"),
                        res.getInt("IdTipoCliente")
                        );
                        
                resultado.add(cliente);//agrego el cliente al array
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
   
   public List<Cliente> getAllCliente4(boolean estadoCliente){
    //Realiza un select a la BD y guarda todos los clientes recogidos en un array 
        
    List<Cliente> resultado= null;//Resultados de la consulta a la BD
    ResultSet res=null;
    
        try {
            seleccionarTodosClientes = getConnection().prepareStatement(seleccionarClientePorEstado);
            seleccionarTodosClientes.setBoolean(1, estadoCliente);
            res = seleccionarTodosClientes.executeQuery();
            resultado = new ArrayList<>();

            while (res.next()) {

                Cliente cliente = new Cliente(
                        res.getInt("IdCliente"),
                        res.getInt("DniCliente"),
                        res.getString("CuitCliente"),
                        res.getString("NombreCliente"),
                        res.getString("DireccionCliente"),
                        res.getString("TelefonoCliente"),
                        res.getString("EmailCliente"),
                        res.getBoolean("EstadoCliente"),
                        res.getInt("IdLocalidadCliente"),
                        res.getInt("IdTipoCliente")
                        );
                        
                resultado.add(cliente);//agrego el cliente al array
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
   
   public List<Libro> getAllLibro(boolean estadoLibro){
    //Realiza un select a la BD y guarda todos los clientes recogidos en un array 
        
    List<Libro> resultado= null;//Resultados de la consulta a la BD
    ResultSet res=null;
    
        try {
            seleccionarTodosLibros = getConnection().prepareStatement(seleccionarLibros);
            seleccionarTodosLibros.setBoolean(1, estadoLibro);
            res = seleccionarTodosLibros.executeQuery();
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
   
    public List<TipoFacturaVenta> getAllTipoFacturaVenta(){
    //Realiza un select a la BD y guarda todos los clientes recogidos en un array 
        
    List<TipoFacturaVenta> resultado= null;//Resultados de la consulta a la BD
    ResultSet res=null;
    
        try {
            seleccionarTodosTipoFacturaVenta = getConnection().prepareStatement(seleccionarTiposFacturasVentas);
            res = seleccionarTodosTipoFacturaVenta.executeQuery();
            resultado = new ArrayList<>();

            while (res.next()) {

                TipoFacturaVenta tipoFacturaVenta = new TipoFacturaVenta(
                        res.getInt("IdTipoFacturaVenta"),
                        res.getString("NombreTipoFacturaVenta"),
                        res.getBoolean("DiscriminaIva"));
                        
                resultado.add(tipoFacturaVenta);//agrego el tipo de factura al array
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
   
   private void close() {//Finalizo la coneccion a la DB
        try {
            getConnection().close();
        } catch (SQLException sqlException) {
            sqlException.getMessage();
        }
    }
   
   public List<TipoCliente> getAllTipoCliente(){
    //Realiza un select a la BD y guarda todos los clientes recogidos en un array 
        
    List<TipoCliente> resultado= null;//Resultados de la consulta a la BD
    ResultSet res=null;
    
        try {
            seleccionarTodosTipoCliente = getConnection().prepareStatement(seleccionarTiposClientes);
            res = seleccionarTodosTipoCliente.executeQuery();
            resultado = new ArrayList<>();

            while (res.next()) {

                TipoCliente tipoCliente = new TipoCliente(
                        res.getInt("IdTipoCliente"),
                        res.getString("NombreTipoCliente"),
                        res.getInt("IdTipoFacturaVenta"));
                        
                resultado.add(tipoCliente);//agrego el tipo de factura al array
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
   
   public List<TipoCliente> getAllTipoCliente2(int idTipoFacturaVenta){
    //Realiza un select a la BD y guarda todos los clientes recogidos en un array 
        
    List<TipoCliente> resultado= null;//Resultados de la consulta a la BD
    ResultSet res=null;
    
        try {
            seleccionarTodosTipoCliente = getConnection().prepareStatement(seleccionarTiposClientesPorTipoFactura);
            seleccionarTodosTipoCliente.setInt(1, idTipoFacturaVenta);
            res = seleccionarTodosTipoCliente.executeQuery();
            resultado = new ArrayList<>();

            while (res.next()) {

                TipoCliente tipoCliente = new TipoCliente(
                        res.getInt("IdTipoCliente"),
                        res.getString("NombreTipoCliente"),
                        res.getInt("IdTipoFacturaVenta"));
                        
                resultado.add(tipoCliente);//agrego el tipo de factura al array
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
    
   public int agregarFacturaVenta(FacturaVenta pFactura) {
        int result = 0;
        id = pFactura.getIdFacturaVenta();
        //System.out.println(fventa);
        try {
            insertarNuevaFacturaVenta = getConnection().prepareStatement(insertarFacturaVenta);
            
            //insertarNuevaFacturaVenta.setInt(1,pFactura.getIdFacturaVenta());
            insertarNuevaFacturaVenta.setDate(1,pFactura.getFechaFacturaVenta());
            insertarNuevaFacturaVenta.setFloat(2,pFactura.getBrutoFacturaVenta());
            insertarNuevaFacturaVenta.setFloat(3,pFactura.getIvaFacturaVenta());
            insertarNuevaFacturaVenta.setFloat(4,pFactura.getTotalFacturaVenta());
            insertarNuevaFacturaVenta.setBoolean(5,pFactura.isAnulada());
            insertarNuevaFacturaVenta.setInt(6,pFactura.getIdCliente());
            insertarNuevaFacturaVenta.setInt(7,pFactura.getIdEmpleado());
            
            System.out.println(insertarNuevaFacturaVenta);
            result = insertarNuevaFacturaVenta.executeUpdate();
        } catch (SQLException sqlException) {
            System.out.println("error al insertar factura " + sqlException.getMessage());
            close();
        }
        
        System.out.println(result);
        return result;
    }
   
   public int agregarFacturaVentaLibro(FacturaVentaLibro pFacturaLibro) {
        int result = 0;
        System.out.println(pFacturaLibro.getIdFacturaVenta());

        try {
            insertarNuevaFacturaVenta_Libro = getConnection().prepareStatement(insertarFacturaVentaLibro);
            System.out.println(pFacturaLibro.getIdFacturaVenta());
            insertarNuevaFacturaVenta_Libro.setInt(1,pFacturaLibro.getCantidad());
            insertarNuevaFacturaVenta_Libro.setFloat(2,pFacturaLibro.getPrecio());
            insertarNuevaFacturaVenta_Libro.setFloat(3,pFacturaLibro.getSubtotal());
            insertarNuevaFacturaVenta_Libro.setInt(4,pFacturaLibro.getIdFacturaVenta());
            insertarNuevaFacturaVenta_Libro.setInt(5,pFacturaLibro.getIdLibro());
            
            System.out.println(insertarNuevaFacturaVenta_Libro);
            result = insertarNuevaFacturaVenta_Libro.executeUpdate();
        } catch (SQLException sqlException) {
            System.out.println("error al insertar linea " + sqlException.getMessage());
            close();
        }
        
        System.out.println(result);
        return result;
    }
   
   public int agregarTipoFacturaVenta(TipoFacturaVenta tfv) {
        int result = 0;

        try {
            insertarTipoFacturaVenta = getConnection().prepareStatement(insertarTipoFactura);
            
            insertarTipoFacturaVenta.setString(1,tfv.getNombreTipoFacturaVenta());
            insertarTipoFacturaVenta.setBoolean(2,tfv.isDiscriminaIva());
            
            System.out.println(insertarTipoFacturaVenta);
            result = insertarTipoFacturaVenta.executeUpdate();
        } catch (SQLException sqlException) {
            System.out.println("error al insertar linea " + sqlException.getMessage());
            close();
        }
        
        System.out.println(result);
        return result;
    }
   
   public int agregarTipoCliente(TipoCliente tc) {
        int result = 0;

        try {
            insertarTipoCliente = getConnection().prepareStatement(insertarTipoCli);
            
            insertarTipoCliente.setString(1,tc.getNombreTipoCliente());
            insertarTipoCliente.setInt(2,tc.isIdTipoFacturaVenta());
            
            System.out.println(insertarTipoCliente);
            result = insertarTipoCliente.executeUpdate();
        } catch (SQLException sqlException) {
            System.out.println("error al insertar linea " + sqlException.getMessage());
            close();
        }
        
        System.out.println(result);
        return result;
    }
   
   public int agregarCliente(Cliente cliente) {
        int result = 0;
        //System.out.println(fventa);
        try {
            insertarCliente = getConnection().prepareStatement(insertarCli);
            
            //insertarNuevaFacturaVenta.setInt(1,pFactura.getIdFacturaVenta());
            insertarCliente.setInt(1,cliente.getDniCliente());
            insertarCliente.setString(2,cliente.getCuitCliente());
            insertarCliente.setString(3,cliente.getNombreCliente());
            insertarCliente.setString(4,cliente.getDireccionCliente());
            insertarCliente.setString(5,cliente.getTelefonoCLiente());
            insertarCliente.setString(6,cliente.getEmailCliente());
            insertarCliente.setBoolean(7,cliente.getEstadoCliente());
            insertarCliente.setInt(8,cliente.getIdLocalidad());
            insertarCliente.setInt(9,cliente.getIdTipoCliente());
            
            System.out.println(insertarCliente);
            result = insertarCliente.executeUpdate();
        } catch (SQLException sqlException) {
            System.out.println("error al insertar factura " + sqlException.getMessage());
            close();
        }
        
        System.out.println(result);
        return result;
    }
   
   public int eliminarFacturaVentaLibro(int idLibro)
    {
        int result = 0;

        try {
            eliminarFacturaVenta_Libro = getConnection().prepareStatement(bajaFacturaVentaLibro);
            eliminarFacturaVenta_Libro.setInt(1, idLibro);
            
            result = eliminarFacturaVenta_Libro.executeUpdate();
        } catch (SQLException sqlException) {
            System.out.println("error al eliminar orden " + sqlException.getMessage());
            close();
        }
        
        System.out.println(result);
        return result;
    
    }
   public int eliminarTipoFacturaVenta(int idTipoFacturaVenta)
    {
        int result = 0;

        try {
            eliminarTipoFacturaVenta = getConnection().prepareStatement(bajaTipoFacturaVenta);
            eliminarTipoFacturaVenta.setInt(1, idTipoFacturaVenta);
            
            result = eliminarTipoFacturaVenta.executeUpdate();
        } catch (SQLException sqlException) {
            System.out.println("error al eliminar orden " + sqlException.getMessage());
            close();
        }
        
        System.out.println(result);
        return result;
    
    }
   
   public int eliminarTipoCliente(int idTipoCliente)
    {
        int result = 0;

        try {
            eliminarTipoCliente = getConnection().prepareStatement(bajaTipoCliente);
            eliminarTipoCliente.setInt(1, idTipoCliente);
            
            result = eliminarTipoCliente.executeUpdate();
        } catch (SQLException sqlException) {
            System.out.println("error al eliminar orden " + sqlException.getMessage());
            close();
        }
        
        System.out.println(result);
        return result;
    
    }
   
   public int eliminarCliente(int idCliente)
    {
        int result = 0;

        try {
            eliminarCliente = getConnection().prepareStatement(bajaCliente);
            eliminarCliente.setInt(1, idCliente);
            
            result = eliminarCliente.executeUpdate();
        } catch (SQLException sqlException) {
            System.out.println("error al eliminar orden " + sqlException.getMessage());
            close();
        }
        
        System.out.println(result);
        return result;
    
    }
  
   public int modificarFacturaVenta(FacturaVenta pFacturaVenta)
    {
        int result = 0;

        try {
            modificarFacturaVenta = getConnection().prepareStatement(modFacturaVenta);
            modificarFacturaVenta.setInt(1,1);
            modificarFacturaVenta.setInt(2,pFacturaVenta.getIdFacturaVenta());
            
            
            
            System.out.println(modificarFacturaVenta);
            result = modificarFacturaVenta.executeUpdate();
            
        } catch (SQLException sqlException) {
            System.out.println("error al modificar orden " + sqlException.getMessage());
            close();
        }
        
        
        
        return result;
    
    
    }
   
   public int modificarLibroStock(int id,int stock)
    {
        int result = 0;

        try {
            modificarLibroStock = getConnection().prepareStatement(modLibroStock);
            modificarLibroStock.setInt(1,stock);
            modificarLibroStock.setInt(2,id);
            
            
            
            System.out.println(modificarLibroStock);
            result = modificarLibroStock.executeUpdate();
            
        } catch (SQLException sqlException) {
            System.out.println("error al modificar orden " + sqlException.getMessage());
            close();
        }
        
        
        
        return result;
    
    
    }
   
   public int modificarCliente(Cliente cliente)
    {
        int result = 0;

        try {
            modificarCliente = getConnection().prepareStatement(modCliente);
            modificarCliente.setInt(1,cliente.getDniCliente());
            modificarCliente.setString(2,cliente.getCuitCliente());
            modificarCliente.setString(3,cliente.getNombreCliente());
            modificarCliente.setString(4,cliente.getDireccionCliente());
            modificarCliente.setString(5,cliente.getTelefonoCLiente());
            modificarCliente.setString(6,cliente.getEmailCliente());
            modificarCliente.setInt(7,cliente.getIdLocalidad());
            modificarCliente.setInt(8,cliente.getIdTipoCliente());
            modificarCliente.setInt(9,cliente.getIdCliente());
            
            
            System.out.println(modificarCliente);
            result = modificarCliente.executeUpdate();
            
        } catch (SQLException sqlException) {
            System.out.println("error al modificar orden " + sqlException.getMessage());
            close();
        }
        
        
        
        return result;
    
    
    }
   
   public int modificarCliente2(Cliente cliente)
    {
        int result = 0;

        try {
            modificarCliente = getConnection().prepareStatement(modClienteEstado);
            modificarCliente.setBoolean(1,cliente.getEstadoCliente());
            modificarCliente.setInt(2,cliente.getIdCliente());
            
            
            System.out.println(modificarCliente);
            result = modificarCliente.executeUpdate();
            
        } catch (SQLException sqlException) {
            System.out.println("error al modificar orden " + sqlException.getMessage());
            close();
        }
        
        
        
        return result;
    
    
    }
   
   public int modificarTipoFacturaVenta(TipoFacturaVenta tfv)
    {
        int result = 0;

        try {
            modificarTipoFacturaVenta = getConnection().prepareStatement(modTipoFacturaVenta);
            modificarTipoFacturaVenta.setString(1,tfv.getNombreTipoFacturaVenta());
            modificarTipoFacturaVenta.setBoolean(2,tfv.isDiscriminaIva());
            modificarTipoFacturaVenta.setInt(3,tfv.getIdTipoFacturaVenta());
            
            
            
            System.out.println(modificarTipoFacturaVenta);
            result = modificarTipoFacturaVenta.executeUpdate();
            
        } catch (SQLException sqlException) {
            System.out.println("error al modificar orden " + sqlException.getMessage());
            close();
        }
        
        
        
        return result;
    
    
    }
   
   public int modificarTipoCliente(TipoCliente tc)
    {
        int result = 0;

        try {
            modificarTipoCliente = getConnection().prepareStatement(modTipoCliente);
            modificarTipoCliente.setString(1,tc.getNombreTipoCliente());
            modificarTipoCliente.setInt(2,tc.isIdTipoFacturaVenta());
            modificarTipoCliente.setInt(3,tc.getIdTipoCliente());
            
            
            
            System.out.println(modificarTipoCliente);
            result = modificarTipoCliente.executeUpdate();
            
        } catch (SQLException sqlException) {
            System.out.println("error al modificar orden " + sqlException.getMessage());
            close();
        }
        
        
        
        return result;
    
    
    }
   
   public Cliente getClientePorId(int id){
    //Realiza un select a la BD y guarda todos los clientes recogidos en un array 
        
    Cliente oCliente=null;
    ResultSet res=null;
    int idc=id;
    
        try {
            seleccionarUnClientes = getConnection().prepareStatement(seleccionarClientePorId);
            seleccionarUnClientes.setInt(1,idc);
            res = seleccionarUnClientes.executeQuery();
            

            while (res.next()) {
                      oCliente = new Cliente(
                        res.getInt("IdCliente"),
                        res.getInt("DniCliente"),
                        res.getString("CuitCliente"),
                        res.getString("NombreCliente"),
                        res.getString("DireccionCliente"),
                        res.getString("TelefonoCliente"),
                        res.getString("EmailCliente"),
                        res.getBoolean("EstadoCliente"),
                        res.getInt("IdLocalidadCliente"),
                        res.getInt("IdTipoCliente")
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
        return oCliente;
    }
   
   public Cliente existeClientePorDNI(int dni){
    //Realiza un select a la BD y guarda todos los clientes recogidos en un array 
        
    Cliente oCliente=null;
    ResultSet res=null;
    
        try {
            existeCliente = getConnection().prepareStatement(existeClientePorDNI);
            existeCliente.setInt(1,dni);
            res = existeCliente.executeQuery();
            

            while (res.next()) {
                      oCliente = new Cliente(
                        res.getInt("IdCliente"),
                        res.getInt("DniCliente"),
                        res.getString("CuitCliente"),
                        res.getString("NombreCliente"),
                        res.getString("DireccionCliente"),
                        res.getString("TelefonoCliente"),
                        res.getString("EmailCliente"),
                        res.getBoolean("EstadoCliente"),
                        res.getInt("IdLocalidadCliente"),
                        res.getInt("IdTipoCliente")
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
        return oCliente;
    }
   
   public Cliente existeClientePorCUIT(String cuit){
    //Realiza un select a la BD y guarda todos los clientes recogidos en un array 
        
    Cliente oCliente=null;
    ResultSet res=null;
    
        try {
            existeCliente = getConnection().prepareStatement(existeClientePorCUIT);
            existeCliente.setString(1,cuit);
            res = existeCliente.executeQuery();
            

            while (res.next()) {
                      oCliente = new Cliente(
                        res.getInt("IdCliente"),
                        res.getInt("DniCliente"),
                        res.getString("CuitCliente"),
                        res.getString("NombreCliente"),
                        res.getString("DireccionCliente"),
                        res.getString("TelefonoCliente"),
                        res.getString("EmailCliente"),
                        res.getBoolean("EstadoCliente"),
                        res.getInt("IdLocalidadCliente"),
                        res.getInt("IdTipoCliente")
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
        return oCliente;
    }
   
   public TipoCliente existeTipoCliente(String nombreTipoCliente){
    //Realiza un select a la BD y guarda todos los clientes recogidos en un array 
        
    TipoCliente tipoCliente=null;
    ResultSet res=null;
    
        try {
            existeTipoCliente = getConnection().prepareStatement(existeTipoDeCliente);
            existeTipoCliente.setString(1,nombreTipoCliente);
            res = existeTipoCliente.executeQuery();
            

            while (res.next()) {
                        tipoCliente = new TipoCliente(
                        res.getInt("IdTipoCliente"),
                        res.getString("NombreTipoCliente"),
                        res.getInt("IdTipoFacturaVenta"));
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
        return tipoCliente;
    }
   
   public TipoFacturaVenta existeTipoFacturaVenta(String nombreTipoFacturaVenta){
    //Realiza un select a la BD y guarda todos los clientes recogidos en un array 
        
    TipoFacturaVenta tipoFacturaVenta=null;
    ResultSet res=null;
    
        try {
            existeTipoFacturaVenta = getConnection().prepareStatement(existeTipoDeFacturaVenta);
            existeTipoFacturaVenta.setString(1,nombreTipoFacturaVenta);
            res = existeTipoFacturaVenta.executeQuery();
            

            while (res.next()) {
                        tipoFacturaVenta = new TipoFacturaVenta(
                        res.getInt("IdTipoFacturaVenta"),
                        res.getString("NombreTipoFacturaVenta"),
                        res.getBoolean("DiscriminaIva"));
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
        return tipoFacturaVenta;
    }
   
   public Empleado getEmpleadoPorId(int id){
    //Realiza un select a la BD y guarda todos los clientes recogidos en un array 
        
    Empleado empleado=null;
    ResultSet res=null;
    int ide=id;
    
        try {
            seleccionarUnEmpleados = getConnection().prepareStatement(seleccionarEmpleadoPorId);
            seleccionarUnEmpleados.setInt(1,ide);
            res = seleccionarUnEmpleados.executeQuery();
            

            while (res.next()) {
                      empleado = new Empleado(
                        res.getInt("IdEmpleado"),
                        res.getString("DniEmpleado"),
                        res.getString("NombreEmpleado"),
                        res.getString("DomicilioEmpleado"),
                        res.getString("TelefonoEmpleado"),
                        res.getInt("IdLocalidad"),
                        res.getDate("FechaIngreso"),
                        res.getInt("IdCategoriaEmpleado"),
                        res.getInt("IdEstadoCivilEmpleado"),
                        res.getBoolean("EstadoEmpleado")      );
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
        return empleado;
    }
   
   public Libro getLibroPorId(int id){
    //Realiza un select a la BD y guarda todos los clientes recogidos en un array 
        
    Libro libro=null;
    ResultSet res=null;
    int ide=id;
    
        try {
            seleccionarUnLibroPorID = getConnection().prepareStatement(seleccionarLibroPorId);
            seleccionarUnLibroPorID.setInt(1,ide);
            res = seleccionarUnLibroPorID.executeQuery();
            

            while (res.next()) {
                      libro = new Libro(
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
                        res.getInt("StockMax")           );
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
        return libro;
    }
   
   public Libro getLibroPorNombre(String titulo){
    //Realiza un select a la BD y guarda todos los clientes recogidos en un array 
        
    Libro libro=null;
    ResultSet res=null;
    String ide=titulo;
    
        try {
            seleccionarUnLibroPorNombre = getConnection().prepareStatement(seleccionarLibroPorNombre);
            seleccionarUnLibroPorNombre.setString(1,ide);
            res = seleccionarUnLibroPorNombre.executeQuery();
            

            while (res.next()) {
                      libro = new Libro(
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
                        res.getInt("StockMax")           );
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
        return libro;
    }
   
   public TipoFacturaVenta getTipoFacturaVentaPorId(int id){
    //Realiza un select a la BD y guarda todos los clientes recogidos en un array 
        
    TipoFacturaVenta tipoFacturaVenta=null;
    ResultSet res=null;
    int idtfv=id;
    
        try {
            seleccionarTipoFacturaVenta = getConnection().prepareStatement(seleccionarTipoFacturaVentaPorId);
            seleccionarTipoFacturaVenta.setInt(1,idtfv);
            res = seleccionarTipoFacturaVenta.executeQuery();
            

            while (res.next()) {
                        tipoFacturaVenta = new TipoFacturaVenta(
                        res.getInt("IdTipoFacturaVenta"),
                        res.getString("NombreTipoFacturaVenta"),
                        res.getBoolean("DiscriminaIva"));
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
        return tipoFacturaVenta;
    }
   
   public TipoCliente getTipoClientePorId(int id){
    //Realiza un select a la BD y guarda todos los clientes recogidos en un array 
        
    TipoCliente tipoCliente=null;
    ResultSet res=null;
    int idtc=id;
    
        try {
            seleccionarTipoCliente = getConnection().prepareStatement(seleccionarTipoClientePorId);
            seleccionarTipoCliente.setInt(1,idtc);
            res = seleccionarTipoCliente.executeQuery();
            

            while (res.next()) {
                        tipoCliente = new TipoCliente(
                        res.getInt("IdTipoCliente"),
                        res.getString("NombreTipoCliente"),
                        res.getInt("IdTipoFacturaVenta"));
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
        return tipoCliente;
    }
   
   public FacturaVentaLibro getFacturaVentaLibroPorId(int id){
    //Realiza un select a la BD y guarda todos los clientes recogidos en un array 
        
    FacturaVentaLibro facturaVentaLibro=null;
    ResultSet res=null;
    int idtfv=id;
    
        try {
            seleccionarFacturaVentaLibro = getConnection().prepareStatement(seleccionarFacturaVentaLibroPorId);
            seleccionarFacturaVentaLibro.setInt(1,idtfv);
            res = seleccionarFacturaVentaLibro.executeQuery();
            

            while (res.next()) {
                        facturaVentaLibro = new FacturaVentaLibro(
                        res.getInt("Cantidad"),
                        res.getFloat("Precio"),
                        res.getFloat("Subtotal"),
                        res.getInt("IdFacturaVenta"),
                        res.getInt("IdLibro"));
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
        return facturaVentaLibro;
    }

    /**
     * @return the connection
     */
    public Connection getConnection() {
        return connection;
    }

    /**
     * @param connection the connection to set
     */
    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}