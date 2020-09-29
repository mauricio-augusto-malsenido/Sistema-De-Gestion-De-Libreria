package Empleados;
import libreria.*;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 *
 * @author SEBASTIAN
 */
public class ConsultaEmpleados {
    private static final String URL = "jdbc:sqlserver://;database=libreria;integratedSecurity=true;";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Mm-1991";
    private Connection connection = null;
    private PreparedStatement select = null;
    private PreparedStatement insert = null;
    private PreparedStatement update = null;
    private PreparedStatement delete = null;
    
    //-------SELECT-------
    //EMPLEADOS
    private String TodosLosEmpleados = "select * from libreria.Empleado order by NombreEmpleado";
    private String EmpleadoPorId = "select * from libreria.Empleado where IdEmpleado= "+"?";
    //CATEGORIAEMPLEADO
    private String TodasLasCategoriasEmpleado = "select * from libreria.CategoriaEmpleado order by NombreCategoriaEmpleado";
    private String CategoriaEmpleadoPorId = "select * from libreria.CategoriaEmpleado where IdCategoriaEmpleado= "+"?";
    //ESTADOCIVILEMPLEADO
    private String TodosLosEstadosCivilesEmpleado = "select * from libreria.EstadoCivilEmpleado order by NombreEstadoCivilEmpleado";
    private String EstadoCivilEmpleadoPorId = "select * from libreria.EstadoCivilEmpleado where IdEstadoCivilEmpleado= "+"?";
    
    //ALTAS
    private String nuevoEmpleado = "INSERT INTO libreria.Empleado (DniEmpleado, NombreEmpleado, DomicilioEmpleado, TelefonoEmpleado, IdLocalidad, FechaIngreso, IdCategoriaEmpleado, IdEstadoCivilEmpleado)" + " VALUES(?,?,?,?,?,?,?,?)";
    private String nuevoEstadoCivilEmpleado = "INSERT INTO libreria.EstadoCivilEmpleado (NombreEstadoCivilEmpleado)" + " VALUES(?)";
    private String nuevaCategoriaEmpleado = "INSERT INTO libreria.CategoriaEmpleado (NombreCategoriaEmpleado, SueldoBasicoCategoria)" + " VALUES(?,?)";
    
    //MODIFICACIONES
    private String modEmpleado ="update libreria.Empleado set "+" DniEmpleado=" +"?"+",NombreEmpleado="+"?"+",DomicilioEmpleado="+"?"+",TelefonoEmpleado="+"?"+",IdLocalidad="+"?"+",FechaIngreso="+"?"+",IdCategoriaEmpleado="+"?"+",IdEstadoCivilEmpleado="+"?"+" where IdEmpleado = "+"?";
    private String modEstadoCivilEmpleado ="update libreria.EstadoCivilEmpleado set "+"NombreEstadoCivilEmpleado="+"?"+" where IdEstadoCivilEmpleado = "+"?";
    private String modCategoriaEmpleado ="update libreria.CategoriaEmpleado set NombreCategoriaEmpleado="+"?"+","+"SueldoBasicoCategoria="+"?"+" where IdCategoriaEmpleado = "+"?";

    //BAJAS
    private String elimCategoriaEmpleado ="delete from libreria.CategoriaEmpleado where IdCategoriaEmpleado= " + "?";
    private String elimEmpleado ="delete from libreria.Empleado where IdEmpleado= " + "?";
    private String elimEstadoCivil ="delete from libreria.EstadoCivilEmpleado where IdEstadoCivilEmpleado = " + "?";
   
    //CONSULTAS VARIAS
    

    
    public ConsultaEmpleados() {
    //En el constructor realizo la coneccion a la base de datos
    try {
           //Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(URL);
            //connection.setSchema("ADMINI");
            System.out.println("Conexion realizada satisfactoriamente");
        } catch (Exception ex) {
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
    
    //-------------SELECT------------
    //AUTORES
    public List<Empleado> getAllEmpleados(){
    //Realiza un select a la BD y guarda todos los clientes recogidos en un array 
        
    List<Empleado> resultado= null;//Resultados de la consulta a la BD
    ResultSet res=null;
    
        try {
            select = connection.prepareStatement(TodosLosEmpleados);
            res = select.executeQuery();
            resultado = new ArrayList<>();

            while (res.next()) {
                Empleado oEmpleado = new Empleado(
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
                resultado.add(oEmpleado);//agrego al cliente al array
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
                System.out.println("se cerror la coneccion");
            } catch (SQLException sqlException) {
                System.out.println(sqlException.getMessage());
                close();
            }
        }
        return resultado;
    } 
    

    //ESTADO CIVIL
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
                System.out.println("se cerro la conexion todos los estados civiles");
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
            
                resultado = new EstadoCivilEmpleado(
                        res.getInt("IdEstadoCivilEmpleado"),
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
                System.out.println("se cerror la coneccion");
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
                System.out.println("se cerror la coneccion");
            } catch (SQLException sqlException) {
                System.out.println(sqlException.getMessage());
                close();
            }
        }
        return resultado;
    }    
    
    //ALTAS
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
            
            System.out.println(insert);
            result = insert.executeUpdate();
        } catch (SQLException sqlException) {
            System.out.println("error al insertar Empleado " + sqlException.getMessage());
            close();
        }
        
        System.out.println(result);
        return result;
    }
     public int agregarEstadoCivilEmpleado(EstadoCivilEmpleado pEstadoCivilEmpleado) {
        int result = 0;

        try {
            insert = connection.prepareStatement(nuevoEstadoCivilEmpleado);
            insert.setString(1, pEstadoCivilEmpleado.getNombreEstadoCivilEmpleado());
            
            System.out.println(insert);
            result = insert.executeUpdate();
        } catch (SQLException sqlException) {
            System.out.println("error al insertar Estado Civil Empleado " + sqlException.getMessage());
            close();
        }
        
        System.out.println(result);
        return result;
    }    
    
     public int agregarCategoriaEmpleado(CategoriaEmpleado pCategoriaEmpleado) {
        int result = 0;

        try {
            insert = connection.prepareStatement(nuevaCategoriaEmpleado);
            insert.setString(1, pCategoriaEmpleado.getNombreCategoriaEmpleado());
            insert.setFloat(2, pCategoriaEmpleado.getSueldoBasicoCategoria());
            
            System.out.println(insert);
            result = insert.executeUpdate();
        } catch (SQLException sqlException) {
            System.out.println("error al insertar Categoria Empleado " + sqlException.getMessage());
            close();
        }
        
        System.out.println(result);
        return result;
    }
     
     //MODIFICACIONES
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
            update.setInt(9, pEmpleado.getIdEmpleado());
            
            
            System.out.println(update);
            result = update.executeUpdate();
        } catch (SQLException sqlException) {
            System.out.println("error al modificar Empleado " + sqlException.getMessage());
            close();
        }
        
        System.out.println(result);
        return result;
    
    }
      public int modificarEstadoCivilEmpleado(EstadoCivilEmpleado pEstadoCivilEmpleado){
        int result = 0;

        try {
            update = connection.prepareStatement(modEstadoCivilEmpleado);
            update.setString(1, pEstadoCivilEmpleado.getNombreEstadoCivilEmpleado());
            update.setInt(2, pEstadoCivilEmpleado.getIdEstadoCivilEmpleado());
            
            
            System.out.println(update);
            result = update.executeUpdate();
        } catch (SQLException sqlException) {
            System.out.println("error al modificar Estado Civil " + sqlException.getMessage());
            close();
        }
        
        System.out.println(result);
        return result;
    
    }
            public int modificarCategoriaEmpleado(CategoriaEmpleado pCategoriaEmpleado){
        int result = 0;

        try {
            update = connection.prepareStatement(modCategoriaEmpleado);
            update.setString(1, pCategoriaEmpleado.getNombreCategoriaEmpleado());
            update.setFloat(2, pCategoriaEmpleado.getSueldoBasicoCategoria());
            update.setInt(3, pCategoriaEmpleado.getIdCategoriaEmpleado());
            
            
            System.out.println(update);
            result = update.executeUpdate();
        } catch (SQLException sqlException) {
            System.out.println("error al modificar Estado Civil " + sqlException.getMessage());
            close();
        }
        
        System.out.println(result);
        return result;
    
    
    }
      
    public int eliminarCategoriaEmpleado(int idCategoriaEmpleado)
    {
        int result = 0;

        try {
            delete = connection.prepareStatement(elimCategoriaEmpleado);
            delete.setInt(1, idCategoriaEmpleado);
            
            result = delete.executeUpdate();
        } catch (SQLException sqlException) {
            System.out.println("error al eliminar CategoriaEmpleado" + sqlException.getMessage());
            close();
        }
        
        System.out.println(result);
        return result;
    
    }
    public int eliminarEstadoCivil(int idEstadoCivil)
    {
        int result = 0;

        try {
            delete = connection.prepareStatement(elimEstadoCivil);
            delete.setInt(1, idEstadoCivil);
            
            result = delete.executeUpdate();
        } catch (SQLException sqlException) {
            System.out.println("error al eliminar Estado Civil" + sqlException.getMessage());
            close();
        }
        
        System.out.println(result);
        return result;
    
    }
 
    public int eliminarEmpleado(int idTipoUsuario)
    {
        int result = 0;

        try {
            delete = connection.prepareStatement(elimEmpleado);
            delete.setInt(1, idTipoUsuario);
            
            result = delete.executeUpdate();
        } catch (SQLException sqlException) {
            System.out.println("error al eliminar Empleado" + sqlException.getMessage());
            close();
        }
        
        System.out.println(result);
        return result;
    
    }
       
}