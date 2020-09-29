/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Compra;

import java.util.ArrayList;

/**
 *
 * @author SEBASTIAN
 */
public class TablaLibroProveedor {
       private ArrayList<LineaProveedorLibro> lineas=new ArrayList<>();
    
    public TablaLibroProveedor() {
    }

    public ArrayList<LineaProveedorLibro> getLineas() {
        return lineas;
    }

    public void setLineas(ArrayList<LineaProveedorLibro> lineas) {
        this.lineas = lineas;
    }

       public int getLongitud() {
        
        int longi=lineas.size();
        
        return longi;
    
    }

}
