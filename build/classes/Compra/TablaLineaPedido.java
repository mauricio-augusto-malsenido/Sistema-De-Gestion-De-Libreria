package Compra;

import java.util.ArrayList;

/**
 *
 * @author SEBASTIAN
 */
public class TablaLineaPedido {
    
     ArrayList<LineaPedido> lineas=new ArrayList<>();
    

    public TablaLineaPedido() {
    }

    public ArrayList<LineaPedido> getLineas() {
        return lineas;
    }
    
    public int getLongitud() {
        
        int longi=lineas.size();
        
        return longi;
    
    }
}
