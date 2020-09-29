package Compra;


import java.awt.HeadlessException;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.Map;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author SEBASTIAN
 */
public class IniciarReporte {
    
    java.sql.Connection conn=null;

    public IniciarReporte() {
    
      try{
           String cadena;
           cadena="jdbc:mysql://localhost:3306/libreria";
           Class.forName("com.mysql.jdbc.Driver");
           conn=DriverManager.getConnection(cadena, "root","Mm-1991");
         }
      catch(ClassNotFoundException | SQLException | HeadlessException ex){
            }
        }
    
public void ejecutarReporte(int idOrden)
{
    try {
        String archivo="C:\\Users\\SEBASTIAN\\Documents\\NetBeansProjects\\libreria\\src\\reporte\\FacturaCompra.jasper";
        //String archivo="C:\\Sysgo\\report2.jasper";
        
        System.err.println("Cargando desde: "+archivo);
            if(archivo==null)
            {
                System.out.println("No se encuentra el archivo");
                //System.exit(2);
            }
         JasperReport masterReport=null;
         
         try {
            masterReport=(JasperReport) JRLoader.loadObjectFromFile(archivo);
        } catch (JRException e) {
             System.out.println("Error cargando el reporte maestro " + e.getMessage());
             //System.exit(3);
        }
                  Map parametro= new HashMap();
                  parametro.put("IdFac",idOrden);
         
         //Reporte diseñado y compilado con iReport
                  JasperPrint jasperPrint= JasperFillManager.fillReport(masterReport, parametro,conn);
                  
        //Se lanza el Viewer de jasper, no termina aplicacion al salir 
                  JasperViewer jviewer= new JasperViewer(jasperPrint,false);
                  jviewer.setTitle("PEDIDO COMPRA");
                  jviewer.setVisible(true);
         
    } catch (Exception e) {
        System.out.println("Mensaje de error: "+ e.getMessage());
    
    }
    
}

public void ejecutarReporteSueldo(int idOrden)
{
    try {
        String archivo="C:\\Users\\SEBASTIAN\\Documents\\NetBeansProjects\\libreria\\src\\reporte\\BoletaSueldo.jasper";
        //String archivo="C:\\Sysgo\\report2.jasper";
        
        System.err.println("Cargando desde: "+archivo);
            if(archivo==null)
            {
                System.out.println("No se encuentra el archivo");
                //System.exit(2);
            }
         JasperReport masterReport=null;
         
         try {
            masterReport=(JasperReport) JRLoader.loadObjectFromFile(archivo);
        } catch (JRException e) {
             System.out.println("Error cargando el reporte maestro " + e.getMessage());
             //System.exit(3);
        }
                  Map parametro= new HashMap();
                  parametro.put("IdBoleta",idOrden);
         
         //Reporte diseñado y compilado con iReport
                  JasperPrint jasperPrint= JasperFillManager.fillReport(masterReport, parametro,conn);
                  
        //Se lanza el Viewer de jasper, no termina aplicacion al salir 
                  JasperViewer jviewer= new JasperViewer(jasperPrint,false);
                  jviewer.setTitle("BOLETA SUELDO");
                  jviewer.setVisible(true);
         
    } catch (Exception e) {
        System.out.println("Mensaje de error: "+ e.getMessage());
    
    }
    
}


public void cerrar(){
    try {
        conn.close();
    } catch (SQLException ex) {
    }
}

}
