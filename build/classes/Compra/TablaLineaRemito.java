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
public class TablaLineaRemito {
    
    
     ArrayList<remitoCompraLibro> lineas=new ArrayList<>();
    

    public TablaLineaRemito() {
    }

    public ArrayList<remitoCompraLibro> getLineas() {
        return lineas;
    }
    
    public int getLongitud() {
        
        int longi=lineas.size();
        
        return longi;
    
    }
    
}
