/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Compra;

/**
 *
 * @author SEBASTIAN
 */
public class LineaProveedorLibro {
    
    private int id;
    private int idLibro;
    private int idProveedor;

    public LineaProveedorLibro() {
    }

    
    public LineaProveedorLibro(int id, int idLibro, int idProveedor) {
        this.id = id;
        this.idLibro = idLibro;
        this.idProveedor = idProveedor;
    }

    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdLibro() {
        return idLibro;
    }

    public void setIdLibro(int idLibro) {
        this.idLibro = idLibro;
    }
    
}
