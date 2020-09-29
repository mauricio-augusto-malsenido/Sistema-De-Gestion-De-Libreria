package mapa;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import libreria.autor;
/**
 *
 * @author SEBASTIAN
 */
public class ConsultaMapa {
    
    private static final String URL = "jdbc:sqlserver://;database=libreria;integratedSecurity=true;";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Mm-1991";
    private Connection connection = null;
    private PreparedStatement select = null;
    private PreparedStatement insert = null;
    private PreparedStatement update = null;
    private PreparedStatement delete = null;
    
    //----------SELECT----------------
    //PROVINCIAS
    private String TodosLasProvincias = "select * from libreria.provincia order by NombreProvincia";
    private String ProvinciaPorId = "select * from libreria.provincia where IdProvincia= "+"?";
     //LOCALIDAD
    private String TodosLasLocalidads = "select * from libreria.localidad order by NombreLocalidad";
    private String LocalidadesPorId = "select * from libreria.localidad where IdLocalidad= "+"?";
    
    //----------ALTAS---------
    private String nuevaProvincia = "INSERT INTO libreria.provincia (NombreProvincia)" + " VALUES(?)";
    private String nuevaLocalidad = "INSERT INTO libreria.localidad (NombreLocalidad,IdProvincia)" + " VALUES(?,?)";
        
    //-----------MODIFICACIONES------------
    private String modProvincia ="update libreria.provincia set "+" NombreProvincia=" +"?"+"where IdProvincia = "+"?";
    private String modLocalida ="update libreria.localidad set "+"NombreLocalidad=" +"?"+","+ "IdProvincia= " +"?"+" where IdLocalidad= "+"?";
    
    private String bajaProvincia ="delete from libreria.provincia where IdProvincia= " + "?";
    private String bajaLocalidad ="delete from libreria.localidad where IdLocalidad= " + "?";
    
     public ConsultaMapa() {
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
    //PROVINCIAS
    public List<provincia> getAllProvincia(){
    //Realiza un select a la BD y guarda todos los clientes recogidos en un array 
        
    List<provincia> resultado= null;//Resultados de la consulta a la BD
    ResultSet res=null;
    
        try {
            select = connection.prepareStatement(TodosLasProvincias);
            res = select.executeQuery();
            resultado = new ArrayList<>();

            while (res.next()) {
                provincia oProvincia = new provincia(
                        res.getInt("IdProvincia"),
                        res.getString("NombreProvincia")
                        );
                resultado.add(oProvincia);//agrego al cliente al array
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
    public provincia getProvinciaPorId(int id){
    //Realiza un select a la BD y guarda todos los clientes recogidos en un array 
        
    provincia resultado= null;//Resultados de la consulta a la BD
    ResultSet res=null;
    
        try {
            select = connection.prepareStatement(ProvinciaPorId);
            select.setInt(1,id);
            res = select.executeQuery();
           
            while (res.next()) {
            
                resultado = new provincia(
                        res.getInt("IdProvincia"),
                        res.getString("NombreProvincia")
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
    //LOCALIDADES
    public List<localidad> getAllLocalidad(){
    //Realiza un select a la BD y guarda todos los clientes recogidos en un array 
        
    List<localidad> resultado= null;//Resultados de la consulta a la BD
    ResultSet res=null;
    
        try {
            select = connection.prepareStatement(TodosLasLocalidads);
            res = select.executeQuery();
            resultado = new ArrayList<>();

            while (res.next()) {
                localidad oLocalidad = new localidad(
                        res.getInt("IdLocalidad"),
                        res.getString("NombreLocalidad"),
                        res.getInt("IdProvincia")
                        );
                resultado.add(oLocalidad);//agrego al cliente al array
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
    public localidad getLocalidadPorId(int id){
    //Realiza un select a la BD y guarda todos los clientes recogidos en un array 
        
    localidad resultado= null;//Resultados de la consulta a la BD
    ResultSet res=null;
    
        try {
            select = connection.prepareStatement(LocalidadesPorId);
            select.setInt(1,id);
            res = select.executeQuery();
           
            while (res.next()) {
            
                resultado = new localidad(
                       res.getInt("IdLocalidad"),
                        res.getString("NombreLocalidad"),
                        res.getInt("IdProvincia")
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
    
       //--------ALTAS------------
   //PROVINCIAS
    public int agregarProvincia(provincia pProvincia) {
        int result = 0;

        try {
            insert = connection.prepareStatement(nuevaProvincia);
            insert.setString(1, pProvincia.getNombreProvincia());
            
            System.out.println(insert);
            result = insert.executeUpdate();
        } catch (SQLException sqlException) {
            System.out.println("error al insertar provincia " + sqlException.getMessage());
            close();
        }
        
        System.out.println(result);
        return result;
    }
   //LOCALIDADES
    public int agregarLocalidad(localidad pLocalidad) {
        int result = 0;

        try {
            insert = connection.prepareStatement(nuevaLocalidad);
            insert.setString(1, pLocalidad.getNombreLocalidad());
            insert.setInt(2, pLocalidad.getIdProvincia());
            
            System.out.println(insert);
            result = insert.executeUpdate();
        } catch (SQLException sqlException) {
            System.out.println("error al insertar localidad " + sqlException.getMessage());
            close();
        }
        
        System.out.println(result);
        return result;
    }
    
    //----------MODIFICACIONES
    //MODIFICACIONE
      public int modificarProvincia(provincia pProvincia){
        int result = 0;

        try {
            update = connection.prepareStatement(modProvincia);
            update.setString(1, pProvincia.getNombreProvincia());
            update.setInt(2, pProvincia.getIdProvincia());
            
            
            System.out.println(update);
            result = update.executeUpdate();
        } catch (SQLException sqlException) {
            System.out.println("error al modificar provincia " + sqlException.getMessage());
            close();
        }
        
        System.out.println(result);
        return result;
    
    
    }
      public int modificarLocalidad(localidad pLocalidad){
        int result = 0;

        try {
            update = connection.prepareStatement(modLocalida);
            update.setString(1, pLocalidad.getNombreLocalidad());
            update.setInt(2, pLocalidad.getIdProvincia());
            update.setInt(3, pLocalidad.getIdLocalidad());
            
            
            System.out.println(update);
            result = update.executeUpdate();
        } catch (SQLException sqlException) {
            System.out.println("error al modificar localidad " + sqlException.getMessage());
            close();
        }
        
        System.out.println(result);
        return result;
    
    
    }
    public int eliminarProvincia(int idCliente)
    {
        int result = 0;

        try {
            delete = connection.prepareStatement(bajaProvincia);
            delete.setInt(1, idCliente);
            
            result = delete.executeUpdate();
        } catch (SQLException sqlException) {
            System.out.println("error al eliminar orden " + sqlException.getMessage());
            close();
        }
        
        System.out.println(result);
        return result;
    
    }
    public int eliminarLocalidad(int idCliente)
    {
        int result = 0;

        try {
            delete = connection.prepareStatement(bajaLocalidad);
            delete.setInt(1, idCliente);
            
            result = delete.executeUpdate();
        } catch (SQLException sqlException) {
            System.out.println("error al eliminar orden " + sqlException.getMessage());
            close();
        }
        
        System.out.println(result);
        return result;
    
    }
    
}

    
    
