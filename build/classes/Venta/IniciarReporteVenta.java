package Venta;
import java.awt.HeadlessException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author SEBASTIAN
 */
public class IniciarReporteVenta {
    
    java.sql.Connection conn=null;

    public IniciarReporteVenta() {
     try{
           String cadena;
           cadena="jdbc:sqlserver://;database=libreria;integratedSecurity=true;";
           //Class.forName("com.mysql.jdbc.Driver");
           conn=DriverManager.getConnection(cadena);
         }
      catch(SQLException | HeadlessException ex){
            }
        }
    
    public void ejecutarReporte(int idOrden)
{
    try {
        String archivo="C:\\Users\\SEBASTIAN\\Documents\\NetBeansProjects\\libreria\\src\\reporte\\FacturaVenta.jasper";
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
                  jviewer.setTitle("FACTURA VENTA");
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
