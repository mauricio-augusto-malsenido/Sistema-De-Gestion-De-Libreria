package libreria;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author SEBASTIAN
 */
public class consultasLibreria {
    private static final String URL = "jdbc:sqlserver://;database=libreria;integratedSecurity=true;";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Mm-1991";
    private Connection connection = null;
    private PreparedStatement select = null;
    private PreparedStatement insert = null;
    private PreparedStatement update = null;
    private PreparedStatement delete = null;
    
    //-------SELECT-------
    //AUTORES
    private String TodosLosAutores = "select * from libreria.autor order by NombreAutor";
    private String TodosLosAutoresHab = "select * from libreria.autor where EstadoAutor=1 order by NombreAutor";
    private String AutorPorId = "select * from libreria.autor where IdAutor= "+"?";
    private String idUA= "SELECT MAX(IdAutor) AS id FROM libreria.autor;";
    //EDITORIALES
    private String TodosLasEditoriales = "select * from libreria.editorial order by NombreEditorial";
    private String TodosLasEditorialesHab = "select * from libreria.editorial where EstadoEditorial= 1 order by NombreEditorial";
    private String EditorialPorId = "select * from libreria.editorial where IdEditorial= "+"?";
    private String idUE= "SELECT MAX(IdEditorial) AS id FROM libreria.editorial;";
    //GENEROS
    private String TodosLosGeneros = "select * from libreria.genero order by NombreGenero";
    private String TodosLosGenerosHab = "select * from libreria.genero where EstadoGenero= 1 order by NombreGenero";
    private String GeneroPorId = "select * from libreria.genero where IdGenero= "+"?";
    private String idUG= "SELECT MAX(IdGenero) AS id FROM libreria.genero;";
    //LIBROS
    private String TodosLosLibros = "select * from libreria.libro order by TituloLibro";
    private String TodosLosLibrosHab = "select * from libreria.libro where EstadoLibro= 1 order by TituloLibro";
    private String LibrosPorProv ="select * from libreria.libroproveedor AS lp INNER JOIN libreria.libro AS l ON lp.IdLibro = l.IdLibro "
    +" where lp.IdProveedor= "+"?"+" AND l.EstadoLibro=1 order by l.TituloLibro";
    private String LibrosPedido ="select * from libreria.libro as l inner join libreria.libroProveedor as lp"
            + " ON l.IdLibro=lp.IdLibro "
            + " where l.StockLibro <= l.StockCritico "
            + " AND lp.IdProveedor="+"?"
            + " order by l.TituloLibro";
    private String LibroPorId = "select * from libreria.libro where IdLibro= "+"?";
    private String idUL= "SELECT MAX(IdLibro) AS id FROM libreria.libro;";
   
    //-------ALTAS-------
    private String nuevoAutor = "INSERT INTO libreria.autor (NombreAutor)" + " VALUES(?)";
    private String nuevoGenero = "INSERT INTO libreria.genero (NombreGenero)" + " VALUES(?)";
    private String nuevoEditorial = "INSERT INTO libreria.editorial (NombreEditorial, TelefonoEditorial, DireccionEditorial)" + " VALUES(?,?,?)";
    private String nuevoLibro = "INSERT INTO libreria.libro"
            + "(TituloLibro, EdicionLibro, AñoLibro, PrecioLibro, StockLibro, CostoLibro, IdEditorialLibro, IdAutorLibro, IdGeneroLibro)" 
            + " VALUES(?,?,?,?,?,?,?,?,?)";
  
    //-------MODIFICACIONES-------
    private String modAutor ="update libreria.autor set "+" NombreAutor=" +"?"+",EstadoAutor="+"?"+" where IdAutor = "+"?";
    private String modGenero ="update libreria.genero set "+"NombreGenero=" +"?"+",EstadoGenero="+"?"+" where IdGenero = "+"?";
    private String modEditorial ="update libreria.editorial set "+"NombreEditorial=" +"?"+","+"TelefonoEditorial=" +"?"+","+"DireccionEditorial=" +"?"+" where IdEditorial = "+"?";
    private String modLibro ="update libreria.libro set "
            +"TituloLibro="+"?"+","+"EdicionLibro="+"?"+","+"AñoLibro="+"?"+","+"PrecioLibro="+"?"+","+"StockLibro="+"?"+","+"StockCritico="+"?"+","+"CostoLibro="+"?"+","+"IdEditorialLibro="+"?"+","+"IdAutorLibro="+"?"+","+"IdGeneroLibro="+"?"+
            " where IdLibro = "+"?";
    private String modStockLibro ="update libreria.libro set "+"StockLibro="+"?"+" where IdLibro = "+"?";
    private String modCostoLibro ="update libreria.libro set "+"CostoLibro="+"?"+" where IdLibro = "+"?";
    private String bajautor ="delete from libreria.autor where IdAutor="+"?";
    private String bajagenero ="delete from libreria.genero where IdGenero="+"?";
    private String bajaEditorial ="delete from libreria.editorial where IdEditorial="+"?";
    private String DeshabLibro ="update libreria.libro set EstadoLibro=? where IdLibro="+"?";
    private String DeshabEditorial ="update libreria.editorial set EstadoEditorial=? where IdEditorial="+"?";

    public consultasLibreria() {
    //En el constructor realizo la coneccion a la base de datos
    try {
           //Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(URL);
            //connection.setSchema("ADMINI");
            System.out.println("Conexion realizada satisfactoriamente");
            System.out.println("LIBRERIA");
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
    public List<autor> getAllAutor(){
    //Realiza un select a la BD y guarda todos los clientes recogidos en un array 
        
    List<autor> resultado= null;//Resultados de la consulta a la BD
    ResultSet res=null;
    
        try {
            select = connection.prepareStatement(TodosLosAutores);
            res = select.executeQuery();
            resultado = new ArrayList<>();

            while (res.next()) {
                autor oAutor = new autor(
                        res.getInt("IdAutor"),
                        res.getString("NombreAutor"),
                        res.getBoolean("EstadoAutor")
                        );
                resultado.add(oAutor);//agrego al cliente al array
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
    public List<autor> getAllAutorHab(){
    //Realiza un select a la BD y guarda todos los clientes recogidos en un array 
        
    List<autor> resultado= null;//Resultados de la consulta a la BD
    ResultSet res=null;
    
        try {
            select = connection.prepareStatement(TodosLosAutoresHab);
            res = select.executeQuery();
            resultado = new ArrayList<>();

            while (res.next()) {
                autor oAutor = new autor(
                        res.getInt("IdAutor"),
                        res.getString("NombreAutor"),
                        res.getBoolean("EstadoAutor")
                        );
                resultado.add(oAutor);//agrego al cliente al array
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
    public autor getAutorPorId(int id){
    //Realiza un select a la BD y guarda todos los clientes recogidos en un array 
        
    autor resultado= null;//Resultados de la consulta a la BD
    ResultSet res=null;
    
        try {
            select = connection.prepareStatement(AutorPorId);
            select.setInt(1,id);
            res = select.executeQuery();
           
            while (res.next()) {
            
                resultado = new autor(
                        res.getInt("IdAutor"),
                        res.getString("NombreAutor"),
                        res.getBoolean("EstadoAutor")
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
    public int getIdUltimaAutor(){
    //Realiza un select a la BD y guarda todos los clientes recogidos en un array 
        
    int resultado=0;//Resultados de la consulta a la BD
    ResultSet res=null;
    
        try {
            select = connection.prepareStatement(idUA);
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
    //EDITORIALES
    public List<editorial> getAllEditorial(){
    //Realiza un select a la BD y guarda todos los clientes recogidos en un array 
        
    List<editorial> resultado= null;//Resultados de la consulta a la BD
    ResultSet res=null;
    
        try {
            select = connection.prepareStatement(TodosLasEditoriales);
            res = select.executeQuery();
            resultado = new ArrayList<>();

            while (res.next()) {
                editorial oEditorial = new editorial(
                        res.getInt("IdEditorial"),
                        res.getString("NombreEditorial"),
                        res.getString("TelefonoEditorial"),
                        res.getString("DireccionEditorial"),
                        res.getBoolean("EstadoEditorial")
                        );
                resultado.add(oEditorial);
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
    public List<editorial> getAllEditorialHab(){
    //Realiza un select a la BD y guarda todos los clientes recogidos en un array 
        
    List<editorial> resultado= null;//Resultados de la consulta a la BD
    ResultSet res=null;
    
        try {
            select = connection.prepareStatement(TodosLasEditorialesHab);
            res = select.executeQuery();
            resultado = new ArrayList<>();

            while (res.next()) {
                editorial oEditorial = new editorial(
                        res.getInt("IdEditorial"),
                        res.getString("NombreEditorial"),
                        res.getString("TelefonoEditorial"),
                        res.getString("DireccionEditorial"),
                        res.getBoolean("EstadoEditorial")
                        );
                resultado.add(oEditorial);
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
    public editorial getEditorialPorId(int id){
    //Realiza un select a la BD y guarda todos los clientes recogidos en un array 
        
    editorial resultado= null;//Resultados de la consulta a la BD
    ResultSet res=null;
    
        try {
            select = connection.prepareStatement(EditorialPorId);
            select.setInt(1,id);
            res = select.executeQuery();
             while (res.next()) {
            
            resultado =  new editorial(
                         res.getInt("IdEditorial"),
                        res.getString("NombreEditorial"),
                        res.getString("TelefonoEditorial"),
                        res.getString("DireccionEditorial"),
                        res.getBoolean("EstadoEditorial")
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
    public int getIdUltimaEditorial(){
    //Realiza un select a la BD y guarda todos los clientes recogidos en un array 
        
    int resultado=0;//Resultados de la consulta a la BD
    ResultSet res=null;
    
        try {
            select = connection.prepareStatement(idUE);
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
    //GENERO
    public List<genero> getAllGenero(){
    //Realiza un select a la BD y guarda todos los clientes recogidos en un array 
        
    List<genero> resultado= null;//Resultados de la consulta a la BD
    ResultSet res=null;
    
        try {
            select = connection.prepareStatement(TodosLosGeneros);
            res = select.executeQuery();
            resultado = new ArrayList<>();

            while (res.next()) {
                genero oGenero = new genero(
                        res.getInt("IdGenero"),
                        res.getString("NombreGenero"),
                        res.getBoolean("EstadoGenero")
                        );
                resultado.add(oGenero);//agrego al cliente al array
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
    public List<genero> getAllGeneroHab(){
    //Realiza un select a la BD y guarda todos los clientes recogidos en un array 
        
    List<genero> resultado= null;//Resultados de la consulta a la BD
    ResultSet res=null;
    
        try {
            select = connection.prepareStatement(TodosLosGenerosHab);
            res = select.executeQuery();
            resultado = new ArrayList<>();

            while (res.next()) {
                genero oGenero = new genero(
                        res.getInt("IdGenero"),
                        res.getString("NombreGenero"),
                        res.getBoolean("EstadoGenero")
                        );
                resultado.add(oGenero);//agrego al cliente al array
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
    public genero getGeneroPorId(int id){
    //Realiza un select a la BD y guarda todos los clientes recogidos en un array 
        
    genero resultado= null;//Resultados de la consulta a la BD
    ResultSet res=null;
    
        try {
            select = connection.prepareStatement(GeneroPorId);
             select.setInt(1,id);
            res = select.executeQuery();
            while (res.next()) {
            resultado = new genero(
                        res.getInt("IdGenero"),
                        res.getString("NombreGenero"),
                        res.getBoolean("EstadoGenero")
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
    public int getIdUltimaGenero(){
    //Realiza un select a la BD y guarda todos los clientes recogidos en un array 
        
    int resultado=0;//Resultados de la consulta a la BD
    ResultSet res=null;
    
        try {
            select = connection.prepareStatement(idUG);
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
    //LIBROS
    public List<Libro> getAllLibro(){
    //Realiza un select a la BD y guarda todos los clientes recogidos en un array 
        
    List<Libro> resultado= null;//Resultados de la consulta a la BD
    ResultSet res=null;
    
        try {
            select = connection.prepareStatement(TodosLosLibros);
            res = select.executeQuery();
            resultado = new ArrayList<>();

            while (res.next()) {
                
                Libro oLibro = new Libro(
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
                resultado.add(oLibro);//agrego al cliente al array
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
    public List<Libro> getAllLibroHab(){
    //Realiza un select a la BD y guarda todos los clientes recogidos en un array 
        
    List<Libro> resultado= null;//Resultados de la consulta a la BD
    ResultSet res=null;
    
        try {
            select = connection.prepareStatement(TodosLosLibrosHab);
            res = select.executeQuery();
            resultado = new ArrayList<>();

            while (res.next()) {
                Libro oLibro = new Libro(
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
                resultado.add(oLibro);//agrego al cliente al array
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
    public List<Libro> getAllLibroProv(int id){
    //Realiza un select a la BD y guarda todos los clientes recogidos en un array 
        
    List<Libro> resultado= null;//Resultados de la consulta a la BD
    ResultSet res=null;
    
        try {
            select = connection.prepareStatement(LibrosPorProv);
            select.setInt(1,id);
            res = select.executeQuery();
            resultado = new ArrayList<>();

            while (res.next()) {
                Libro oLibro = new Libro(
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
                resultado.add(oLibro);//agrego al cliente al array
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
    public Libro getLibroPorId(int id){
    //Realiza un select a la BD y guarda todos los clientes recogidos en un array 
        
    Libro resultado= null;//Resultados de la consulta a la BD
    ResultSet res=null;
    
        try {
            select = connection.prepareStatement(LibroPorId);
            select.setInt(1,id);
            res = select.executeQuery();
            while (res.next()) {
            resultado = new Libro(
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
    public List<Libro> getLibrosPedido(int IdProv){
    //Realiza un select a la BD y guarda todos los clientes recogidos en un array 
        
    List<Libro> resultado= null;//Resultados de la consulta a la BD
    ResultSet res=null;
    
        try {
            select = connection.prepareStatement(LibrosPedido);
            select.setInt(1,IdProv);
            res = select.executeQuery();
            resultado = new ArrayList<>();

            while (res.next()) {
                Libro oLibro = new Libro(
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
                resultado.add(oLibro);//agrego al cliente al array
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
    public int getIdUltimaLibro(){
    //Realiza un select a la BD y guarda todos los clientes recogidos en un array 
        
    int resultado=0;//Resultados de la consulta a la BD
    ResultSet res=null;
    
        try {
            select = connection.prepareStatement(idUL);
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
    
    ///-------------ALTAS------------
     public int agregarAutor(autor pAutor) {
        int result = 0;

        try {
            insert = connection.prepareStatement(nuevoAutor);
            insert.setString(1, pAutor.getNombreAutor());
            
            System.out.println(insert);
            result = insert.executeUpdate();
        } catch (SQLException sqlException) {
            System.out.println("error al insertar autor " + sqlException.getMessage());
            close();
        }
        
        System.out.println(result);
        return result;
    }
     public int agregarEditorial(editorial pEditorial) {
        int result = 0;

        try {
            insert = connection.prepareStatement(nuevoEditorial);
            insert.setString(1, pEditorial.getNombreEditorial());
            insert.setString(2, pEditorial.getTelefonoEditorial());
            insert.setString(3, pEditorial.getDireccionEditorial());
            
            
            System.out.println(insert);
            result = insert.executeUpdate();
        } catch (SQLException sqlException) {
            System.out.println("error al insertar editorial " + sqlException.getMessage());
            close();
        }
        
        System.out.println(result);
        return result;
    }
     public int agregarGenero(genero pGenero) {
        int result = 0;

        try {
            insert = connection.prepareStatement(nuevoGenero);
            insert.setString(1, pGenero.getNombreGenero());
            
            System.out.println(insert);
            result = insert.executeUpdate();
        } catch (SQLException sqlException) {
            System.out.println("error al insertar genero " + sqlException.getMessage());
            close();
        }
        
        System.out.println(result);
        return result;
    }
     public int agregarLibro(Libro pLibro) {
        int result = 0;

        try {
            insert = connection.prepareStatement(nuevoLibro);
            insert.setString(1, pLibro.getTitulo());
            insert.setString(2, pLibro.getEdicion());
            insert.setInt(3, pLibro.getAño());
            insert.setFloat(4, pLibro.getPrecio());
            insert.setInt(5, pLibro.getStock());
            insert.setFloat(6, pLibro.getCostoLibro());
            insert.setInt(7, pLibro.getIdEditorial());
            insert.setInt(8, pLibro.getIdAutor());
            insert.setInt(9, pLibro.getIdGenero());
            
            
            System.out.println(insert);
            result = insert.executeUpdate();
        } catch (SQLException sqlException) {
            System.out.println("error al insertar libro " + sqlException.getMessage());
            close();
        }
        
        System.out.println(result);
        return result;
    }
    
     //-------------MODIFICACIONES------------
      public int modificarAutor(autor pAutor){
        int result = 0;

        try {
            update = connection.prepareStatement(modAutor);
            update.setString(1, pAutor.getNombreAutor());
            update.setBoolean(2, pAutor.getEstadoAutor());
            update.setInt(3, pAutor.getIdAutor());
            
            
            System.out.println(update);
            result = update.executeUpdate();
        } catch (SQLException sqlException) {
            System.out.println("error al modificar Libro " + sqlException.getMessage());
            close();
        }
        
        System.out.println(result);
        return result;
    
    
    }
      public int modificarEditorial(editorial pEditorial){
        int result = 0;

        try {
            update = connection.prepareStatement(modEditorial);
            update.setString(1, pEditorial.getNombreEditorial());
            update.setString(2, pEditorial.getTelefonoEditorial());
            update.setString(3, pEditorial.getDireccionEditorial());
            update.setInt(4, pEditorial.getIdEditorial());
            
            
            System.out.println(update);
            result = update.executeUpdate();
        } catch (SQLException sqlException) {
            System.out.println("error al modificar editorial " + sqlException.getMessage());
            close();
        }
        
        System.out.println(result);
        return result;
    
    
    }
      public int modificarGenero(genero pGenero){
        int result = 0;

        try {
            update = connection.prepareStatement(modGenero);
            update.setString(1, pGenero.getNombreGenero());
            update.setBoolean(2, pGenero.getEstadoGenero());
            update.setInt(3, pGenero.getIdGenero());
            
            
            System.out.println(update);
            result = update.executeUpdate();
        } catch (SQLException sqlException) {
            System.out.println("error al modificar Libro " + sqlException.getMessage());
            close();
        }
        
        System.out.println(result);
        return result;
    
    
    }
      public int modificarLibro(Libro pLibro){
        int result = 0;

        try {
            update = connection.prepareStatement(modLibro);
            update.setString(1, pLibro.getTitulo());
            update.setString(2, pLibro.getEdicion());
            update.setInt(3, pLibro.getAño());
            update.setFloat(4, pLibro.getPrecio());
            update.setInt(5, pLibro.getStock());
            update.setInt(6, pLibro.getStockCritico());
            update.setFloat(7, pLibro.getCostoLibro());
            update.setInt(8, pLibro.getIdEditorial());
            update.setInt(9, pLibro.getIdAutor());
            update.setInt(10, pLibro.getIdGenero());
            update.setInt(11, pLibro.getIdLibro());
            
            System.out.println(update);
            result = update.executeUpdate();
        } catch (SQLException sqlException) {
            System.out.println("error al modificar Libro " + sqlException.getMessage());
            close();
        }
        
        System.out.println(result);
        return result;
    
    
    }
      public int modificarStockLibro(int cantidad,int id){
        int result = 0;
        try {
            update = connection.prepareStatement(modStockLibro);
            update.setInt(1, cantidad);
            update.setInt(2, id);
            
            
            System.out.println(update);
            result = update.executeUpdate();
        } catch (SQLException sqlException) {
            System.out.println("error al modificar STOCK Libro " + sqlException.getMessage());
            close();
        }
        
        System.out.println(result);
        return result;
    
    
    }
      public int modificarCostoLibro(float costo,int id){
        int result = 0;
        try {
            update = connection.prepareStatement(modCostoLibro);
            update.setFloat(1, costo);
            update.setInt(2, id);
            
            
            System.out.println(update);
            result = update.executeUpdate();
        } catch (SQLException sqlException) {
            System.out.println("error al modificar STOCK Libro " + sqlException.getMessage());
            close();
        }
        
        System.out.println(result);
        return result;
    
    
    }
    
      //-------------BAJAS------------
      public int eliminaAutor(int idRemito) {
        int result = 0;

        try {
            delete = connection.prepareStatement(bajautor);
            delete.setInt(1, idRemito);
            result = delete.executeUpdate();
        } catch (SQLException sqlException) {
            System.out.println("error al eliminar autor" + sqlException.getMessage());
            close();
        }

        System.out.println(result);
        return result;

    }
      public int eliminaGenero(int idRemito) {
        int result = 0;

        try {
            delete = connection.prepareStatement(bajagenero);
            delete.setInt(1, idRemito);
            result = delete.executeUpdate();
        } catch (SQLException sqlException) {
            System.out.println("error al eliminar autor" + sqlException.getMessage());
            close();
        }

        System.out.println(result);
        return result;

    }
      public int eliminaEditorial(int idRemito) {
        int result = 0;

        try {
            delete = connection.prepareStatement(bajaEditorial);
            delete.setInt(1, idRemito);
            result = delete.executeUpdate();
        } catch (SQLException sqlException) {
            System.out.println("error al eliminar editorial" + sqlException.getMessage());
            close();
        }
        
        System.out.println(result);
        return result;
    
    }
      public int deshabilitarLibro(Libro l) {
        int result = 0;

        try {
            update = connection.prepareStatement(DeshabLibro);
            update.setBoolean(1, l.getEstadoLibro());
            update.setInt(2, l.getIdLibro());
            result = update.executeUpdate();
        } catch (SQLException sqlException) {
            System.out.println("error al eliminar autor" + sqlException.getMessage());
            close();
        }

        System.out.println(result);
        return result;

    }
      public int deshabilitarEditorial(editorial l) {
        int result = 0;

        try {
            update = connection.prepareStatement(DeshabEditorial);
            update.setBoolean(1, l.getEstadoEditorial());
            update.setInt(2, l.getIdEditorial());
            result = update.executeUpdate();
        } catch (SQLException sqlException) {
            System.out.println("error al eliminar autor" + sqlException.getMessage());
            close();
        }

        System.out.println(result);
        return result;

    }
}
