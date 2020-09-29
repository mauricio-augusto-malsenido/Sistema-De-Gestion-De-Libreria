package Empleados;
import Compra.*;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author SEBASTIAN
 */
public class ConsultaEmpleados2 {
    private static final String URL = "jdbc:sqlserver://;database=libreria;integratedSecurity=true;";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Mm-1991";
    private Connection connection = null;
    private PreparedStatement select = null;
    private PreparedStatement insert = null;
    private PreparedStatement update = null;
    private PreparedStatement delete = null;
    
    
    //----------------SELECT------------------   
    //EMPLEADO
    private String TodosEmpleados = "select * from libreria.Empleado";
    private String TodosEmpleadosHabilitados = "select * from libreria.Empleado where EstadoEmpleado=1";
    private String EmpleadoPorId = "select * from libreria.Empleado where IdEmpleado= "+"?";

    //OTRAS TABLAS
    private String CategoriaEmpleadoPorId = "select * from libreria.CategoriaEmpleado where IdCategoriaEmpleado= "+"?";
    private String TodasLasCategoriasEmpleado = "select * from libreria.CategoriaEmpleado order by NombreCategoriaEmpleado";
    private String TodosLosEstadosCivilesEmpleado = "select * from libreria.EstadoCivilEmpleado order by NombreEstadoCivilEmpleado";
    private String EstadoCivilEmpleadoPorId = "select * from libreria.EstadoCivilEmpleado where IdEstadoCivilEmpleado= "+"?";
    private String seleccionarUsuarios= " select * from libreria.usuarios";
    
    
    //-------------AlTAS-----------------------
    //private String nuevoEmpleado = "INSERT INTO Empleado (DniEmpleado, NombreEmpleado, DomicilioEmpleado, TelefonoEmpleado, IdLocalidad, FechaIngreso, IdCategoriaEmpleado, IdEstadoCivilEmpleado, EstadoEmpleado)" + " VALUES(?,?,?,?,?,?,?,?,?)";
    private String nuevoEmpleado = "INSERT INTO libreria.Empleado (DniEmpleado, NombreEmpleado, DomicilioEmpleado, TelefonoEmpleado, IdLocalidad, FechaIngreso, IdCategoriaEmpleado, IdEstadoCivilEmpleado, IdUsuario)" + " VALUES(?,?,?,?,?,?,?,?,?)";
    private String nuevoEmpleadoSinUsuario = "INSERT INTO libreria.Empleado (DniEmpleado, NombreEmpleado, DomicilioEmpleado, TelefonoEmpleado, IdLocalidad, FechaIngreso, IdCategoriaEmpleado, IdEstadoCivilEmpleado,IdUsuario)" + " VALUES(?,?,?,?,?,?,?,?,null)";
        
    //-------------MODIFICACIONES--------------
    String modEmpleado ="update libreria.Empleado set "+" DniEmpleado=" +"?"+",NombreEmpleado="+"?"+",DomicilioEmpleado="+"?"+",TelefonoEmpleado="+"?"+",IdLocalidad="+"?"+",FechaIngreso="+"?"+",IdCategoriaEmpleado="+"?"+",IdEstadoCivilEmpleado="+"?"+",IdUsuario="+"?"+",EstadoEmpleado="+"?"+" where IdEmpleado = "+"?";
    String modEmpleadoSinUsuario ="update libreria.Empleado set "+" DniEmpleado=" +"?"+",NombreEmpleado="+"?"+",DomicilioEmpleado="+"?"+",TelefonoEmpleado="+"?"+",IdLocalidad="+"?"+",FechaIngreso="+"?"+",IdCategoriaEmpleado="+"?"+",IdEstadoCivilEmpleado="+"?"+",EstadoEmpleado="+"?"+",IdUsuario=null"+" where IdEmpleado = "+"?";
    
//CONEXION    
   public ConsultaEmpleados2() {
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
   

//EMPLEADO
   public List<Empleado> getAllEmpleado(){
    //Realiza un select a la BD y guarda todos las boletas de sueldo
        
    List<Empleado> resultado= null;//Resultados de la consulta a la BD
    ResultSet res=null;
    
        try {
            select = connection.prepareStatement(TodosEmpleados);
            res = select.executeQuery();
            resultado = new ArrayList<>();

            while (res.next()) {
                Empleado pEmpleado = new Empleado(
                        res.getInt("IdEmpleado"),
                        res.getString("DniEmpleado"),
                        res.getString("NombreEmpleado"),
                        res.getString("DomicilioEmpleado"),
                        res.getString("TelefonoEmpleado"),
                        res.getInt("IdLocalidad"),
                        res.getDate("FechaIngreso"),
                        res.getInt("IdCategoriaEmpleado"),
                        res.getInt("IdEstadoCivilEmpleado"),
                        res.getInt("IdUsuario"),
                        res.getBoolean("EstadoEmpleado")
                        );
                        
                resultado.add(pEmpleado);//agrego la boleta a la lista
            }
        } 
        catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        finally {
            try {
                res.close();
                System.out.println("se cerro la conexion Todos Empleados");
            } catch (SQLException sqlException) {
                System.out.println(sqlException.getMessage());
//                close();
            }
        }
        return resultado;
    } 
   public List<Empleado> getAllEmpleadosHabilitados(){
    //Realiza un select a la BD y guarda todos las boletas de sueldo
        
    List<Empleado> resultado= null;//Resultados de la consulta a la BD
    ResultSet res=null;
    
        try {
            select = connection.prepareStatement(TodosEmpleadosHabilitados);
            res = select.executeQuery();
            resultado = new ArrayList<>();

            while (res.next()) {
                Empleado pEmpleado = new Empleado(
                        res.getInt("IdEmpleado"),
                        res.getString("DniEmpleado"),
                        res.getString("NombreEmpleado"),
                        res.getString("DomicilioEmpleado"),
                        res.getString("TelefonoEmpleado"),
                        res.getInt("IdLocalidad"),
                        res.getDate("FechaIngreso"),
                        res.getInt("IdCategoriaEmpleado"),
                        res.getInt("IdEstadoCivilEmpleado"),
                        res.getInt("IdUsuario"),
                        res.getBoolean("EstadoEmpleado")
                        );
                        
                resultado.add(pEmpleado);//agrego la boleta a la lista
            }
        } 
        catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        finally {
            try {
                res.close();
                System.out.println("se cerror la conexion Todos Empleados");
            } catch (SQLException sqlException) {
                System.out.println(sqlException.getMessage());
//                close();
            }
        }
        return resultado;
    } 
   public int agregarEmpleado(Empleado pEmpleado) {
        int result = 0;

        try {
            insert = connection.prepareStatement(nuevoEmpleado);
            insert.setString(1, pEmpleado.getDniEmpleado());
            insert.setString(2, pEmpleado.getNombreEmpleado());
            insert.setString(3, pEmpleado.getDomicilioEmpleado());
            insert.setString(4, pEmpleado.getTelefonoEmpleado());
            insert.setInt(5, pEmpleado.getIdLocalidad());
            insert.setDate(6, pEmpleado.getFechaIngreso());
            insert.setInt(7, pEmpleado.getIdCategoriaEmpleado());
            insert.setInt(8, pEmpleado.getIdEstadoCivilEmpleado());
            insert.setInt(9, pEmpleado.getIdUsuario());
            //insert.setBoolean(8, pEmpleado.getEstadoEmpleado());
            
            System.out.println(insert);
            result = insert.executeUpdate();
        } catch (SQLException sqlException) {
            System.out.println("error al insertar Empleado " + sqlException.getMessage());
            close();
        }
        
        System.out.println(result);
        return result;
    }
   public int agregarEmpleadoSinUsuario(Empleado pEmpleado) {
        int result = 0;

        try {
            insert = connection.prepareStatement(nuevoEmpleadoSinUsuario);
            insert.setString(1, pEmpleado.getDniEmpleado());
            insert.setString(2, pEmpleado.getNombreEmpleado());
            insert.setString(3, pEmpleado.getDomicilioEmpleado());
            insert.setString(4, pEmpleado.getTelefonoEmpleado());
            insert.setInt(5, pEmpleado.getIdLocalidad());
            insert.setDate(6, pEmpleado.getFechaIngreso());
            insert.setInt(7, pEmpleado.getIdCategoriaEmpleado());
            insert.setInt(8, pEmpleado.getIdEstadoCivilEmpleado());
            //insert.setBoolean(8, pEmpleado.getEstadoEmpleado());
            
            System.out.println(insert);
            result = insert.executeUpdate();
        } catch (SQLException sqlException) {
            System.out.println("error al insertar Empleado " + sqlException.getMessage());
            close();
        }
        
        System.out.println(result);
        return result;
    }
   public int modificarEmpleado(Empleado pEmpleado){
        int result = 0;
        
        try {
            update = connection.prepareStatement(modEmpleado);
            
            update.setString(1, pEmpleado.getDniEmpleado());
            update.setString(2, pEmpleado.getNombreEmpleado());
            update.setString(3, pEmpleado.getDomicilioEmpleado());
            update.setString(4, pEmpleado.getTelefonoEmpleado());
            update.setInt(5, pEmpleado.getIdLocalidad());
            update.setDate(6, pEmpleado.getFechaIngreso());
            update.setInt(7, pEmpleado.getIdCategoriaEmpleado());
            update.setInt(8, pEmpleado.getIdEstadoCivilEmpleado());
            update.setInt(9, pEmpleado.getIdUsuario());
            update.setBoolean(10, pEmpleado.getEstadoEmpleado());
            update.setInt(11, pEmpleado.getIdEmpleado());
            
            
            System.out.println(update);
            result = update.executeUpdate();
        } catch (SQLException sqlException) {
            System.out.println("error al modificar Empleado" + sqlException.getMessage());
            close();
        }
        
        System.out.println(result);
        return result;
    
    
    }
   public int modificarEmpleadoSinUsuario(Empleado pEmpleado){
        int result = 0;
        
        try {
            update = connection.prepareStatement(modEmpleadoSinUsuario);
            
            update.setString(1, pEmpleado.getDniEmpleado());
            update.setString(2, pEmpleado.getNombreEmpleado());
            update.setString(3, pEmpleado.getDomicilioEmpleado());
            update.setString(4, pEmpleado.getTelefonoEmpleado());
            update.setInt(5, pEmpleado.getIdLocalidad());
            update.setDate(6, pEmpleado.getFechaIngreso());
            update.setInt(7, pEmpleado.getIdCategoriaEmpleado());
            update.setInt(8, pEmpleado.getIdEstadoCivilEmpleado());
            update.setBoolean(9, pEmpleado.getEstadoEmpleado());
            update.setInt(10, pEmpleado.getIdEmpleado());
            
            System.out.println(update);
            result = update.executeUpdate();
        } catch (SQLException sqlException) {
            System.out.println("error al modificar Empleado" + sqlException.getMessage());
            close();
        }
        
        System.out.println(result);
        return result;
    
    
    }
   public Empleado getEmpleadoPorId(int id){
    //Realiza un select a la BD y guarda todos los clientes recogidos en un array 
        
    Empleado resultado= null;//Resultados de la consulta a la BD
    ResultSet res=null;
    
        try {
            select = connection.prepareStatement(EmpleadoPorId);
            select.setInt(1,id);
            res = select.executeQuery();
           
            while (res.next()) {
            
                resultado = new Empleado(
                        res.getInt("IdEmpleado"),
                        res.getString("DniEmpleado"),
                        res.getString("NombreEmpleado"),
                        res.getString("DomicilioEmpleado"),
                        res.getString("TelefonoEmpleado"),
                        res.getInt("IdLocalidad"),
                        res.getDate("FechaIngreso"),
                        res.getInt("IdCategoriaEmpleado"),
                        res.getInt("IdEstadoCivilEmpleado"),
                        res.getInt("IdUsuario"),
                        res.getBoolean("EstadoEmpleado")
                        );
            }
        }
        catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        finally {
            try {
                res.close();
                System.out.println("se cerro la conexion Empleado ID");
            } catch (SQLException sqlException) {
                System.out.println(sqlException.getMessage());
                close();
            }
        }
        return resultado;
    }
   public CategoriaEmpleado getCategoriaEmpleadoPorId(int id){
    //Realiza un select a la BD y guarda todos los clientes recogidos en un array 
        
        
    CategoriaEmpleado resultado= null;//Resultados de la consulta a la BD
    ResultSet res=null;
    
        try {
            select = connection.prepareStatement(CategoriaEmpleadoPorId);
            select.setInt(1,id);
            res = select.executeQuery();
           
            while (res.next()) {
            
                resultado = new CategoriaEmpleado(
                        res.getInt("IdCategoriaEmpleado"),
                        res.getString("NombreCategoriaEmpleado"),
                        res.getFloat("SueldoBasicoCategoria")
                        );
            }
        }
        catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        finally {
            try {
                res.close();
                System.out.println("se cerro la conexion Categoria Empleado Id");
            } catch (SQLException sqlException) {
                System.out.println(sqlException.getMessage());
                close();
            }
        }
        return resultado;
    }     
   
//OTRAS
   public List<CategoriaEmpleado> getAllCategoriaEmpleado(){
    //Realiza un select a la BD y guarda todos los clientes recogidos en un array 
        
    List<CategoriaEmpleado> resultado= null;//Resultados de la consulta a la BD
    ResultSet res=null;
    
        try {
            select = connection.prepareStatement(TodasLasCategoriasEmpleado);
            res = select.executeQuery();
            resultado = new ArrayList<>();

            while (res.next()) {
                CategoriaEmpleado oCategoriaEmpleado = new CategoriaEmpleado(
                        res.getInt("IdCategoriaEmpleado"),
                        res.getString("NombreCategoriaEmpleado"),
                        res.getFloat("SueldoBasicoCategoria")
                        );
                resultado.add(oCategoriaEmpleado);//agrego al cliente al array
            }
        } 
        catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        finally {
            try {
                res.close();
                System.out.println("se cerro la conexion Todas Categorias");
            } catch (SQLException sqlException) {
                System.out.println(sqlException.getMessage());
                close();
            }
        }
        return resultado;
    }
   public List<EstadoCivilEmpleado> getAllEstadoCivilEmpleado(){
    //Realiza un select a la BD y guarda todos los clientes recogidos en un array 
        
    List<EstadoCivilEmpleado> resultado= null;//Resultados de la consulta a la BD
    ResultSet res=null;
    
        try {
            select = connection.prepareStatement(TodosLosEstadosCivilesEmpleado);
            res = select.executeQuery();
            resultado = new ArrayList<>();

            while (res.next()) {
                EstadoCivilEmpleado oEstadoCivilEmpleado = new EstadoCivilEmpleado(
                        res.getInt("IdEstadoCivilEmpleado"),
                        res.getString("NombreEstadoCivilEmpleado")
                        );
                resultado.add(oEstadoCivilEmpleado);//agrego al cliente al array
            }
        } 
        catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        finally {
            try {
                res.close();
                System.out.println("se cerror la conexion");
            } catch (SQLException sqlException) {
                System.out.println(sqlException.getMessage());
                close();
            }
        }
        return resultado;
    }
   public EstadoCivilEmpleado getEstadoCivilEmpleadoPorId(int id){
    //Realiza un select a la BD y guarda todos los clientes recogidos en un array 
    EstadoCivilEmpleado resultado= null;//Resultados de la consulta a la BD
    ResultSet res=null;
        try {
            select = connection.prepareStatement(EstadoCivilEmpleadoPorId);
            select.setInt(1,id);
            res = select.executeQuery();
            while (res.next()) {
                resultado = new EstadoCivilEmpleado(res.getInt("IdEstadoCivilEmpleado"),
                        res.getString("NombreEstadoCivilEmpleado")
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
   public List<Usuario> getAllUsuario(){
    //Realiza un select a la BD y guarda todos los clientes recogidos en un array 
     List<Usuario> resultado= null;//Resultados de la consulta a la BD
     ResultSet res=null;
    
        try {
            select = connection.prepareStatement(seleccionarUsuarios);
            res = select.executeQuery();
            //System.out.println(seleccionarTodosUsuarios);
            resultado = new ArrayList<>();

            while (res.next()) {

                Usuario user = new Usuario(
                        res.getInt("IdUsuario"),
                        res.getString("Usuario"),
                        res.getString("Contraseña"),
                        res.getInt("IdTipoUsuario")
                        );
                        
                resultado.add(user);//agrego el empleado al array
            }
        } 
        catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        finally {
            try {
                res.close();
                System.out.println("se cerro la conexion todos los usuarios");
            } catch (SQLException sqlException) {
                System.out.println(sqlException.getMessage());
                close();
            }
        }
        return resultado;
    }    
}
  