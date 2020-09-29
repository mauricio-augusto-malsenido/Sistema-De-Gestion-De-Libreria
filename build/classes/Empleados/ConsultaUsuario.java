/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Empleados;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mauricio
 */
public class ConsultaUsuario {
    private static final String URL = "jdbc:sqlserver://;database=libreria;integratedSecurity=true;";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Mm-1991";
    private Connection connection = null;
    private int id;
    private PreparedStatement seleccionarTodosUsuarios = null;
    private PreparedStatement seleccionarTodosTiposUsuarios = null;
    private PreparedStatement seleccionarUnTipoUsuario = null;
    private PreparedStatement seleccionarUnUsuario = null;
    private PreparedStatement seleccionarElUsuario = null;
    private PreparedStatement insertarUsuario = null;
    private PreparedStatement insertarTipoUsuario = null;
    private PreparedStatement eliminarUsuario = null;
    private PreparedStatement eliminarTipoUsuario = null;
    private PreparedStatement modificarUsuario = null;
    private PreparedStatement modificarTipoUsuario = null;  
    private String seleccionarUsuarios= " select * from libreria.usuarios";
    private String seleccionarTiposUsuarios= " select * from libreria.TipoUsuario";
    private String seleccionarUsuarioPorId = " select * from libreria.Usuarios where IdUsuario= "+"?";
    private String seleccionarUsuarioPorUsuario = " select * from libreria.Usuarios where Usuario="+"?";
    private String seleccionarTipoUsuarioPorId = " SELECT idusuario, usuario, contraseña, usuarios.idtipousuario,nombretipousuario FROM libreria.usuarios INNER join libreria.tipousuario ON libreria.tipousuario.idtipousuario = libreria.usuarios.idtipousuario where Usuario= "+"?";
    private String seleccionarTiposUsuarioPorId = " select * from libreria.TipoUsuario where IdTipoUsuario= "+"?";
    private String insertarUser = "INSERT INTO libreria.Usuarios"
            + "(Usuario,Contraseña,IdTipoUsuario)"
            + " VALUES(?,?,?)";
    private String insertarTipoUser = "INSERT INTO libreria.TipoUsuario"
            + "(NombreTipoUsuario)"
            + " VALUES(?)";
    private String bajaUsuario ="delete from libreria.Usuarios where IdUsuario= " + "?";
    private String bajaTipoUsuario ="delete from libreria.TipoUsuario where IdTipoUsuario= " + "?";
    private String modUsuario ="update libreria.Usuarios set "+"Usuario=" +"?"+","+"Contraseña=" +"?"+","+"IdTipoUsuario=" +"?"+" where IdUsuario = "+"?";
    private String modUsuarioNomTipo ="update libreria.Usuarios set "+"Usuario="+"?"+","+"IdTipoUsuario=" +"?"+" where IdUsuario = "+"?";
    private String modTipoUsuario ="update libreria.TipoUsuario set "+"NombreTipoUsuario=" +"?"+" where IdTipoUsuario = "+"?";
    
    public ConsultaUsuario() {
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
    public List<Usuario> getAllUsuario(){
    //Realiza un select a la BD y guarda todos los clientes recogidos en un array 
     List<Usuario> resultado= null;//Resultados de la consulta a la BD
     ResultSet res=null;
    
        try {
            seleccionarTodosUsuarios = connection.prepareStatement(seleccionarUsuarios);
            res = seleccionarTodosUsuarios.executeQuery();
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
    
    public List<TipoUsuario> getAllTipoUsuario(){
    //Realiza un select a la BD y guarda todos los clientes recogidos en un array 
     List<TipoUsuario> resultado= null;//Resultados de la consulta a la BD
     ResultSet res=null;
    
        try {
            seleccionarTodosTiposUsuarios = connection.prepareStatement(seleccionarTiposUsuarios);
            res = seleccionarTodosTiposUsuarios.executeQuery();
            System.out.println(seleccionarTodosTiposUsuarios);
            resultado = new ArrayList<>();

            while (res.next()) {

                TipoUsuario tipoUsuario = new TipoUsuario(
                        res.getInt("IdTipoUsuario"),
                        res.getString("NombreTipoUsuario")
                        );
                        
                resultado.add(tipoUsuario);//agrego el empleado al array
            }
        } 
        catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        finally {
            try {
                System.out.println("hola"+resultado);
                res.close();
                System.out.println("se cerro la conexion todos tipos de usuario");
            } catch (SQLException sqlException) {
                System.out.println(sqlException.getMessage());
                close();
            }
        }
        return resultado;
    }
    public Usuario getUsuarioPorId(int id){
    //Realiza un select a la BD y guarda todos los clientes recogidos en un array 
    Usuario empleado=null;
    ResultSet res=null;
    int ide=id;
        try {
            seleccionarUnUsuario = connection.prepareStatement(seleccionarUsuarioPorId);
            seleccionarUnUsuario.setInt(1,ide);
            res = seleccionarUnUsuario.executeQuery();
            while (res.next()) {
                      empleado = new Usuario(
                       res.getInt("IdUsuario"),
                        res.getString("Usuario"),
                        res.getString("Contraseña"),
                        res.getInt("IdTipoUsuario"));
            }
        } 
        catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        finally {
            try {
                res.close();
                System.out.println("se cerro la conexion usuario por id");
            } catch (SQLException sqlException) {
                System.out.println(sqlException.getMessage());
                close();
            }
        }
        return empleado;
    }
    public Usuario getUsuarioPorUsuario(String usuario){
    //Realiza un select a la BD y guarda todos los clientes recogidos en un array 
        
    Usuario empleado=null;
    ResultSet res=null;
    String nombre= usuario;
    
        try {
            seleccionarElUsuario = connection.prepareStatement(seleccionarUsuarioPorUsuario);
            seleccionarElUsuario.setString(1,nombre);
            res = seleccionarElUsuario.executeQuery();
            

            while (res.next()) {
                      empleado = new Usuario(
                       res.getInt("IdUsuario"),
                        res.getString("Usuario"),
                        res.getString("Contraseña"),
                        res.getInt("IdTipoUsuario")
                              );
            }
        } 
        catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        finally {
            try {
                res.close();
                System.out.println("se cerro la conexion usuario por usuario");
            } catch (SQLException sqlException) {
                System.out.println(sqlException.getMessage());
                close();
            }
        }
        return empleado;
    }
    public Usuario getUsuarioPorUsuarioDev(String usuario){
    //Realiza un select a la BD y guarda todos los clientes recogidos en un array 
        
    Usuario empleado=null;
    ResultSet res=null;
    String nombre= usuario;
    
        try {
            seleccionarElUsuario = connection.prepareStatement(seleccionarUsuarioPorUsuario);
            seleccionarElUsuario.setString(1,nombre);
            res = seleccionarElUsuario.executeQuery();
            

            while (res.next()) {
                      empleado = new Usuario(
                       res.getInt("IdUsuario"),
                        res.getString("Usuario"),
                        res.getString("Contraseña"),
                        res.getInt("IdTipoUsuario")
                              );
            }
        } 
        catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        finally {
            try {
                res.close();
                System.out.println("se cerro la conexion usuario por usuario");
            } catch (SQLException sqlException) {
                System.out.println(sqlException.getMessage());
                close();
            }
        }
        return empleado;
    }
    public TipoUsuario getTiposUsuarioPorId(int id){
    //Realiza un select a la BD y guarda todos los clientes recogidos en un array 
        
    TipoUsuario empleado=null;
    ResultSet res=null;
    int ide=id;
    
        try {
            seleccionarTodosTiposUsuarios = connection.prepareStatement(seleccionarTiposUsuarioPorId);
            seleccionarTodosTiposUsuarios.setInt(1,ide);
            res = seleccionarTodosTiposUsuarios.executeQuery();
            

            while (res.next()) {
                      empleado = new TipoUsuario(
                        res.getInt("IdTipoUsuario"),
                        res.getString("NombreTipoUsuario"));
            }
        } 
        catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        finally {
            try {
                res.close();
                System.out.println("se cerro la conexion");
            } catch (SQLException sqlException) {
                System.out.println(sqlException.getMessage());
                close();
            }
        }
        return empleado;
    }
    public TipoUsuario getTipoUsuarioPorId(int id){
    //Realiza un select a la BD y guarda todos los clientes recogidos en un array 
    TipoUsuario empleado=null;
    ResultSet res=null;
    int ide=id;
    
        try {
            seleccionarTodosTiposUsuarios = connection.prepareStatement(seleccionarTiposUsuarioPorId);
            seleccionarTodosTiposUsuarios.setInt(1,ide);
            res = seleccionarTodosTiposUsuarios.executeQuery();
            

            while (res.next()) {
                      empleado = new TipoUsuario(
                        res.getInt("IdTipoUsuario"),
                        res.getString("NombreTipoUsuario"));
            }
        } 
        catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        finally {
            try {
                res.close();
                System.out.println("se cerro la conexion tipo usuario por id");
            } catch (SQLException sqlException) {
                System.out.println(sqlException.getMessage());
                close();
            }
        }
        return empleado;
    }

    public int agregarUsuario(Usuario u) {
        int result = 0;

        try {
            insertarUsuario = connection.prepareStatement(insertarUser);
            
            insertarUsuario.setString(1,u.getUsuario());
            insertarUsuario.setString(2,u.getContraseña());
            insertarUsuario.setInt(3,u.getIdTipoUsuario());
            
            System.out.println(insertarUsuario);
            result = insertarUsuario.executeUpdate();
        } catch (SQLException sqlException) {
            System.out.println("error al insertar usuario " + sqlException.getMessage());
            close();
        }
        
        System.out.println(result);
        return result;
    }
    public int agregarTipoUsuario(TipoUsuario tu) {
        int result = 0;

        try {
            insertarTipoUsuario = connection.prepareStatement(insertarTipoUser);
            
            insertarTipoUsuario.setString(1,tu.getNombreTipoUsuario());
            
            System.out.println(insertarTipoUsuario);
            result = insertarTipoUsuario.executeUpdate();
        } catch (SQLException sqlException) {
            System.out.println("error al insertar tipo de usuario " + sqlException.getMessage());
            close();
        }
        
        System.out.println(result);
        return result;
    }
    public int eliminarUsuario(int idUsuario){
        int result = 0;

        try {
            eliminarUsuario = connection.prepareStatement(bajaUsuario);
            eliminarUsuario.setInt(1, idUsuario);
            
            result = eliminarUsuario.executeUpdate();
        } catch (SQLException sqlException) {
            System.out.println("error al eliminar usuario " + sqlException.getMessage());
            close();
        }
        
        System.out.println(result);
        return result;
    
    }
    public int eliminarTipoUsuario(int idTipoUsuario){
        int result = 0;

        try {
            eliminarTipoUsuario = connection.prepareStatement(bajaTipoUsuario);
            eliminarTipoUsuario.setInt(1, idTipoUsuario);
            
            result = eliminarTipoUsuario.executeUpdate();
        } catch (SQLException sqlException) {
            System.out.println("error al eliminar tipo de usuario " + sqlException.getMessage());
            close();
        }
        
        System.out.println(result);
        return result;
    
    }
    public int modificarUsuario(Usuario u){
        int result = 0;

        try {
            modificarUsuario = connection.prepareStatement(modUsuario);
            modificarUsuario.setString(1,u.getUsuario());
            modificarUsuario.setString(2,u.getContraseña());
            modificarUsuario.setInt(3,u.getIdTipoUsuario());
            modificarUsuario.setInt(4,u.getIdUsuario());
            
            
            
            System.out.println("Modificado usuario");
            result = modificarUsuario.executeUpdate();
            
        } catch (SQLException sqlException) {
            System.out.println("error al modificar usuario " + sqlException.getMessage());
            close();
        }
        return result;
    }
    public int modificarUsuarioNomTipo(Usuario u){
        int result = 0;

        try {
            modificarUsuario = connection.prepareStatement(modUsuarioNomTipo);
            modificarUsuario.setString(1,u.getUsuario());
            modificarUsuario.setInt(2,u.getIdTipoUsuario());
            modificarUsuario.setInt(3,u.getIdUsuario());
            System.out.println("usuario modificado solo nombre y tipo");
            result = modificarUsuario.executeUpdate();
            
        } catch (SQLException sqlException) {
            System.out.println("error al modificar usuario solo nombre y tipo" + sqlException.getMessage());
            close();
        }
        
        
        
        return result;
    
    
    }
    public int modificarTipoUsuario(TipoUsuario tu){
        int result = 0;

        try {
            modificarTipoUsuario = connection.prepareStatement(modTipoUsuario);
            modificarTipoUsuario.setString(1,tu.getNombreTipoUsuario());
            modificarTipoUsuario.setInt(2,tu.getIdTipoUsuario());
            
            
            
            System.out.println(modificarTipoUsuario);
            result = modificarTipoUsuario.executeUpdate();
            
        } catch (SQLException sqlException) {
            System.out.println("error al modificar tipo usuario " + sqlException.getMessage());
            close();
        }
        
        
        
        return result;
    
    
    }
    public boolean comprobarUsuarioExiste(String usuario){
        boolean b=true;
            ConsultaUsuario consultaUsuario = new ConsultaUsuario();
            Usuario user;
            user = consultaUsuario.getUsuarioPorUsuario(usuario);
            if (user == null || usuario.contentEquals(user.getUsuario()) == false)
                b=false;
        
        return b;
    }
    public boolean comprobarContraseña(String contraseña){
        boolean b=false,mayuscula=false,longitud=false,numero=false;
        int i;
        for (i=0;i<contraseña.length(); i++)
        {
            if (Character.isUpperCase(contraseña.charAt(i)) ){
                mayuscula = true;
            } 
            if (Character.isDigit(contraseña.charAt(i))){
                numero = true;
            }
            
        }
        if (contraseña.length()>=6)
        {
            longitud = true;
        }
        if (mayuscula && longitud && numero)
            b=true;
        System.out.println("Comprobacion de seguridad de contraseñas");
        
        return b;
    }
    
    private void close() {//Finalizo la coneccion a la DB
        try {
            connection.close();
        } catch (SQLException sqlException) {
            sqlException.getMessage();
        }
    }
}

