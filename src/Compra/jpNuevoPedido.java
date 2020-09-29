package Compra;

import Venta.BusquedaFactura;
import Venta.LineasFactura;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import static java.awt.image.ImageObserver.WIDTH;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import libreria.Libro;
import libreria.consultasLibreria;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

/**
 *
 * @author SEBASTIAN
 */
//BANDERA 0: NUEVO PEDIDO MANUAL
//BANDERA 1: NUEVO PEDIDO AUTOMATICO
public class jpNuevoPedido extends javax.swing.JPanel {
private ConsultaCompra oConsultaCompra;
private consultasLibreria oConsultaLibreria;
private int v,indiceModelo,bandera,idNuevoPedido,banderaInterna,stockTecho=20,idlibroLinea,longitud,longitud2;
private Date dateFpedido;
private java.sql.Date fpedido;
private boolean control,controlC,controlCO,controLabel=false;
private TablaLineaPedido tblp=new TablaLineaPedido();
private IniciarReporte jasper;
private ArrayList<LineaPedido> tblineaPedido;
private float totalPedido;

private DefaultTableModel dftmp= new DefaultTableModel(){
@Override
      public boolean isCellEditable(int row, int column) {
       
            return false;}};;

//private IniciarReporte jasper;


    public jpNuevoPedido(int b) {
        initComponents();
        oConsultaCompra = new ConsultaCompra();
        oConsultaLibreria= new consultasLibreria();
        tblineaPedido = new ArrayList<>();
        bandera=b;
        banderaInterna=0;
        totalPedido=0;
        CargarComboProveedores();
       
        AutoCompleteDecorator.decorate(this.jcbProveedor);
        AutoCompleteDecorator.decorate(this.jcbLibro);
        dateFpedido=new Date(00, 0, 1);
        jasper= new IniciarReporte();
        btnAceptar.setText("Registrar Pedido");
        btnAceptar.setVisible(false);
        btnCancelar.setVisible(false);
        labelCosto.setVisible(false);
        labelCantidad.setVisible(false);
        jcbEstadoPedido.setEnabled(false);
        jdateFpedido.setDate(new Date());
      
        
        if(bandera==0){
        btnCancelar.setText("Salir");    
        idNuevoPedido=oConsultaCompra.getIdUltimoPedido()+1;
        jtfNroRd.setText(""+idNuevoPedido);
        cargarTablaLineaPedido();
        jPanelLineaPedido.setVisible(false);
        btnGenerar.setVisible(true);
        labelCosto2.setVisible(false);
        jtfCosto.setVisible(false);
        AccionDobleClick();
        }
        if(bandera==1){
        btnGenerar.setVisible(true);
        idNuevoPedido=oConsultaCompra.getIdUltimoPedido()+1;
        jtfNroRd.setText(""+idNuevoPedido);
        jPanelLineaPedido.setVisible(false);
        jtableLineaPedido.setModel(dftmp);
        labelCosto2.setVisible(false);
        jtfCosto.setVisible(false);
        AccionDobleClick();
        }
    }
    
    private void cargarTabla(List<Libro> lp) {
        List<Libro> lista =lp;
        int cantidad=0;
        totalPedido=0;
        String[] columnNames = {"Cod_Libro","Titulo","Cantidad_Pedido","Precio_Unitario","SubTotal"};
        int[] anchos = {5,100,10,20,20};
        
        Object[][] data = new Object[lista.size()][columnNames.length];
        
         for (int i = 0; i < lista.size(); i++) {
            cantidad=(lista.get(i).getStockMax() - lista.get(i).getStock());
            data[i][0] = lista.get(i).getIdLibro();
            data[i][1] = lista.get(i).getTitulo();
            data[i][2] =cantidad;
            data[i][3] =lista.get(i).getCostoLibro();
            data[i][4] =(cantidad * lista.get(i).getCostoLibro());
         
           LineaPedido lnp= new LineaPedido(0,cantidad,0,lista.get(i).getIdLibro(),idNuevoPedido,lista.get(i).getCostoLibro());
           tblineaPedido.add(lnp);
           
           totalPedido= totalPedido+ (cantidad * lista.get(i).getCostoLibro());
         }
       DefaultTableModel dftm = new DefaultTableModel(data, columnNames)
                {
		//metodo para que las celdas del jtable sean no-editables	
                    @Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
       jtableLineaPedido.setModel(dftm);
       
       for(int i = 0; i < jtableLineaPedido.getColumnCount(); i++) {

        jtableLineaPedido.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);

        }
       jtfTotal.setText(""+totalPedido);
    }
    
    private void cargarTablaLineaPedido(){
        totalPedido=0;
        dftmp.setColumnCount(0);
        dftmp.setNumRows(0);
        dftmp.addColumn("Cod_Libro");
        dftmp.addColumn("Titulo");
        dftmp.addColumn("Cantidad_Pedido");
        dftmp.addColumn("Costo_Unitario");
        dftmp.addColumn("SubTotal");
        Iterator<LineaPedido> iter = tblineaPedido.iterator();
        while(iter.hasNext()){
            LineaPedido lp= iter.next();
            Object[] fila=new Object[5];
            fila[0]=lp.getIdLibro();
            fila[1]=oConsultaLibreria.getLibroPorId(lp.getIdLibro()).getTitulo();
            fila[2]=lp.getCantidad();
            fila[3]=lp.getCostoLineaPedido();
            fila[4]=(lp.getCostoLineaPedido() * lp.getCantidad());
            dftmp.addRow(fila);
            totalPedido=totalPedido + (lp.getCostoLineaPedido() * lp.getCantidad());
        }
        jtableLineaPedido.setModel(dftmp);
        jtfTotal.setText(""+totalPedido);
    }
    
    private void CargarComboProveedores(){
        
        List<proveedor> lista = oConsultaCompra.getAllProveedorHab();

        for (int i = 0; i < lista.size(); i++) {
       
            proveedor emc=new proveedor(
                    lista.get(i).getIdProveedor(),
                    lista.get(i).getNombreProveedor(),
                    lista.get(i).getDireccionProveedor(),
                    lista.get(i).getCuitProveedor(),
                    lista.get(i).getTelProveedor(),
                    lista.get(i).getIdLocalidad());
            
            jcbProveedor.addItem(emc);
            proveedor objeto=(proveedor) jcbProveedor.getItemAt(2);

        }
        
	
    }
    
    private void CargarComboLibros(int id){
        
        List<Libro> lista = oConsultaLibreria.getAllLibroProv(id);

        for (int i = 0; i < lista.size(); i++) {
       
            Libro emc= lista.get(i);
            jcbLibro.addItem(emc);
            Libro objeto=(Libro) jcbLibro.getItemAt(2);

        }
        
	
    }
     
    private PedidoCompra capturarCampos(){
     proveedor p= (proveedor) jcbProveedor.getSelectedItem();
     
        if(jdateFpedido.getDate() != null)
           {dateFpedido=jdateFpedido.getDate();
           fpedido=new java.sql.Date(dateFpedido.getTime());
           }
           
        //PedidoCompra pc= new PedidoCompra(idNuevoPedido, fpedido, p.getIdProveedor());
        PedidoCompra pc= new PedidoCompra(idNuevoPedido, fpedido, p.getIdProveedor(),jcbEstadoPedido.getSelectedItem().toString(), Float.parseFloat(jtfTotal.getText()));
        return  pc;
        }
    
    private void cargarLineaPedido(LineaPedido linea){
    idlibroLinea=linea.getIdLibro();
    jtfCosto.setVisible(true);
    labelCosto2.setVisible(true);
    btnAñadir.setText("Guardar");
    btnQuitar.setText("Cancelar");
    jcbLibro.setSelectedItem(oConsultaLibreria.getLibroPorId(linea.getIdLibro()));
    jcbLibro.setEnabled(false);
    jtfCosto.setText(""+linea.getCostoLineaPedido());
    jtfcantidad.setText(""+linea.getCantidad());
    }
     
    public boolean comprobarFechaNula(){
        if(jdateFpedido.getDate()==null)
        {control=true;
        JOptionPane.showMessageDialog(null, "Debe registrar la fecha de Pedido", "ADVERTENCIA", WIDTH);
        
        }
        else{
        control=false;
        }
        return control;
    }
     
    public boolean comprobarTablaVacia(){
        if(tblineaPedido.isEmpty())
        {control=true;
        JOptionPane.showMessageDialog(null, "Debe añadir por lo menos un libro", "ADVERTENCIA", WIDTH);
        
        }
        else{
        control=false;
        }
        return control;
    }
    
    public boolean comprobarrepetidos(int id){
        
        for(int i=0; i< tblineaPedido.size();i++){
            if(tblineaPedido.get(i).getIdLibro()==id){
            control=true;
            JOptionPane.showMessageDialog(null, "El libro ya ha sido añadido", "ADVERTENCIA", WIDTH);
            break;
            }
             else{control=false;}
        }
        
        return control;
    }
    
    private void AccionDobleClick(){
     jtableLineaPedido.addMouseListener(new MouseAdapter() 
        {
           @Override
           public void mouseClicked(MouseEvent e) 
           {
             if(e.getClickCount()==2){
              int fila = jtableLineaPedido.rowAtPoint(e.getPoint());
              int columna = jtableLineaPedido.columnAtPoint(e.getPoint());
                     if ((fila > -1) && (columna > -1))
                     {
                        v=jtableLineaPedido.getSelectedRow();
                        indiceModelo = jtableLineaPedido.convertRowIndexToModel (v);
                        
                            for(int i =0; i<tblineaPedido.size(); i++)
                            {
                                if(tblineaPedido.get(i).getIdLibro() == getIdLibroLineaPedido(indiceModelo)){
                                cargarLineaPedido(tblineaPedido.get(i));
                                banderaInterna=1;
                                jtableLineaPedido.setEnabled(false);
                                }
                            }
                        
                       
                     }
             }
           }
        });
    
    }
    
     private int getIdLibroLineaPedido( int im){
        String[] fila= new String[1];//almaceno los valores del registro seleccionado en el string "fila"
        fila[0]=""+jtableLineaPedido.getModel().getValueAt(im, 0);
        
        int idFac=Integer.parseInt(fila[0]);
        
        System.out.println(idFac);
        return  idFac;
    }
     
  /*public boolean comprobarNulosC(){
                if(jtfcantidad.getText().equals(""))
                    {controlC=true;
                    labelCantidad.setVisible(true);
                    labelCantidad.setText("Debe ingresar una cantidad");
                    jtfcantidad.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
                    }
                    else{
                    controlC=false;
                    }
                    
                    return controlC;
    }*/
     public boolean comprobarNulosCO(){
                if(jtfCosto.getText().equals(""))
                    {controlCO=true;
                    labelCosto2.setVisible(true);
                    labelCosto2.setText("Debe ingresar costo");
                    jtfCosto.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
                    }
                    else{
                    controlCO=false;
                    }
                    
                    return controlCO;
    }
    
    
  
   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelCabeceraPedido = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jtfNroRd = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        btnVerProv = new javax.swing.JButton();
        btnNuevoProv = new javax.swing.JButton();
        jdateFpedido = new com.toedter.calendar.JDateChooser();
        jcbProveedor = new org.jdesktop.swingx.JXComboBox();
        jLabel2 = new javax.swing.JLabel();
        jcbEstadoPedido = new javax.swing.JComboBox();
        btnGenerar = new javax.swing.JButton();
        jPanelLineaPedido = new javax.swing.JPanel();
        btnAñadir = new javax.swing.JButton();
        btnQuitar = new javax.swing.JButton();
        jcbLibro = new org.jdesktop.swingx.JXComboBox();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jtfcantidad = new javax.swing.JTextField();
        labelCosto2 = new javax.swing.JLabel();
        jtfCosto = new javax.swing.JTextField();
        labelCosto = new javax.swing.JLabel();
        labelCantidad = new javax.swing.JLabel();
        btnAceptar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jPanelTabla = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtableLineaPedido = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        jtfTotal = new javax.swing.JTextField();

        jPanelCabeceraPedido.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setText("N° Pedido");

        jtfNroRd.setEditable(false);
        jtfNroRd.setDisabledTextColor(new java.awt.Color(0, 0, 0));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setText("Fecha Pedido");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel5.setText("Proveedor");

        btnVerProv.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/encontrar-los-prismaticos-de-busqueda-icono-9145-32.png"))); // NOI18N
        btnVerProv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerProvActionPerformed(evt);
            }
        });

        btnNuevoProv.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/anadir-bala-icono-4686-32.png"))); // NOI18N
        btnNuevoProv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoProvActionPerformed(evt);
            }
        });

        jcbProveedor.setEditable(true);
        jcbProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbProveedorActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setText("Estado");

        jcbEstadoPedido.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "ESPERA", "COMPLETO", "INCOMPLETO", " " }));

        btnGenerar.setText("Generar");
        btnGenerar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelCabeceraPedidoLayout = new javax.swing.GroupLayout(jPanelCabeceraPedido);
        jPanelCabeceraPedido.setLayout(jPanelCabeceraPedidoLayout);
        jPanelCabeceraPedidoLayout.setHorizontalGroup(
            jPanelCabeceraPedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelCabeceraPedidoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelCabeceraPedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelCabeceraPedidoLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jtfNroRd, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jdateFpedido, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2))
                    .addGroup(jPanelCabeceraPedidoLayout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jcbProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(4, 4, 4)
                        .addComponent(btnNuevoProv, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnVerProv, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelCabeceraPedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jcbEstadoPedido, 0, 93, Short.MAX_VALUE)
                    .addComponent(btnGenerar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanelCabeceraPedidoLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnNuevoProv, btnVerProv});

        jPanelCabeceraPedidoLayout.setVerticalGroup(
            jPanelCabeceraPedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelCabeceraPedidoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelCabeceraPedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelCabeceraPedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(jtfNroRd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel3))
                    .addGroup(jPanelCabeceraPedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jdateFpedido, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanelCabeceraPedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jcbEstadoPedido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))))
                .addGap(18, 18, 18)
                .addGroup(jPanelCabeceraPedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnNuevoProv, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanelCabeceraPedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel5)
                        .addComponent(btnVerProv, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jcbProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnGenerar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanelLineaPedido.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        btnAñadir.setText("Añadir");
        btnAñadir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAñadirActionPerformed(evt);
            }
        });

        btnQuitar.setText("Quitar");
        btnQuitar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuitarActionPerformed(evt);
            }
        });

        jcbLibro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbLibroActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel4.setText("Libro");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel6.setText("Cantidad");

        jtfcantidad.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(0, 0, 0), null));
        jtfcantidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtfcantidadKeyTyped(evt);
            }
        });

        labelCosto2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        labelCosto2.setText("Costo");

        jtfCosto.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.black, null));
        jtfCosto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtfCostoKeyTyped(evt);
            }
        });

        labelCosto.setForeground(new java.awt.Color(204, 0, 0));
        labelCosto.setText("jLabel8");

        labelCantidad.setForeground(new java.awt.Color(204, 0, 0));
        labelCantidad.setText("jLabel8");

        javax.swing.GroupLayout jPanelLineaPedidoLayout = new javax.swing.GroupLayout(jPanelLineaPedido);
        jPanelLineaPedido.setLayout(jPanelLineaPedidoLayout);
        jPanelLineaPedidoLayout.setHorizontalGroup(
            jPanelLineaPedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelLineaPedidoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jcbLibro, javax.swing.GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jLabel6)
                .addGap(18, 18, 18)
                .addGroup(jPanelLineaPedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelLineaPedidoLayout.createSequentialGroup()
                        .addComponent(jtfcantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(labelCosto2)
                        .addGap(9, 9, 9))
                    .addGroup(jPanelLineaPedidoLayout.createSequentialGroup()
                        .addComponent(labelCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 35, Short.MAX_VALUE)
                        .addGap(67, 67, 67)))
                .addGroup(jPanelLineaPedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelLineaPedidoLayout.createSequentialGroup()
                        .addComponent(labelCosto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelLineaPedidoLayout.createSequentialGroup()
                        .addComponent(jtfCosto, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnAñadir)
                        .addGap(18, 18, 18)
                        .addComponent(btnQuitar)
                        .addGap(29, 29, 29))))
        );
        jPanelLineaPedidoLayout.setVerticalGroup(
            jPanelLineaPedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelLineaPedidoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelLineaPedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAñadir)
                    .addComponent(btnQuitar)
                    .addComponent(jcbLibro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jtfCosto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtfcantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelCosto2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanelLineaPedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelCosto)
                    .addComponent(labelCantidad))
                .addContainerGap())
        );

        btnAceptar.setText("Guardar");
        btnAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptarActionPerformed(evt);
            }
        });

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        jPanelTabla.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jtableLineaPedido.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Cod_Libro", "Titulo", "Cantidad"
            }
        ));
        jScrollPane1.setViewportView(jtableLineaPedido);

        jLabel7.setText("TOTAL:");

        jtfTotal.setEditable(false);
        jtfTotal.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N

        javax.swing.GroupLayout jPanelTablaLayout = new javax.swing.GroupLayout(jPanelTabla);
        jPanelTabla.setLayout(jPanelTablaLayout);
        jPanelTablaLayout.setHorizontalGroup(
            jPanelTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTablaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanelTablaLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jtfTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanelTablaLayout.setVerticalGroup(
            jPanelTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTablaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 259, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jtfTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnAceptar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnCancelar)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanelTabla, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanelLineaPedido, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanelCabeceraPedido, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(12, 12, 12))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanelCabeceraPedido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(jPanelLineaPedido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelTabla, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAceptar)
                    .addComponent(btnCancelar))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnVerProvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerProvActionPerformed
      proveedor em= (proveedor) jcbProveedor.getSelectedItem();
      JOptionPane.showMessageDialog(this, em.MostarProveedor(),"Proveedor", JOptionPane.INFORMATION_MESSAGE);     
    }//GEN-LAST:event_btnVerProvActionPerformed

    private void btnNuevoProvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoProvActionPerformed
        int idp=oConsultaCompra.getIdUltimaProv();
        jdnuevoProveedor jda = new jdnuevoProveedor(null, true);
        jda.setVisible(true);
        jda.setLocationRelativeTo(null);
        if(idp!=oConsultaCompra.getIdUltimaProv()){
         jcbProveedor.removeAllItems();
         CargarComboProveedores();
        jcbProveedor.setSelectedItem(oConsultaCompra.getProveedorPorId(oConsultaCompra.getIdUltimaProv()));
        }
    }//GEN-LAST:event_btnNuevoProvActionPerformed

    private void jcbProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbProveedorActionPerformed
      jPanelLineaPedido.setVisible(false);
      jPanelTabla.setVisible(false);
      btnAceptar.setVisible(false);
      btnCancelar.setVisible(false);
      btnGenerar.setVisible(true);        
      for(int i=0; i< tblineaPedido.size();i++){
            tblineaPedido.remove(i);
            System.out.println("Se elimino");
      }
      cargarTablaLineaPedido();
      
    }//GEN-LAST:event_jcbProveedorActionPerformed

    private void btnGenerarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerarActionPerformed
        jcbLibro.removeAllItems();
        jPanelLineaPedido.setVisible(true);
        btnQuitar.setEnabled(false);
        btnAceptar.setVisible(true);
        btnCancelar.setVisible(true);
        jPanelTabla.setVisible(true);
        proveedor p=(proveedor) jcbProveedor.getSelectedItem();
        if(bandera==1){cargarTabla(oConsultaLibreria.getLibrosPedido(p.getIdProveedor()));}
        CargarComboLibros(p.getIdProveedor());
        jtfcantidad.setText("");
        if (tblineaPedido.isEmpty())
       {
           btnQuitar.setEnabled(false);
       }
        else
        {
            btnQuitar.setEnabled(true);
        }
    }//GEN-LAST:event_btnGenerarActionPerformed

    private void btnAñadirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAñadirActionPerformed
        float costo;
        Libro l= (Libro) jcbLibro.getSelectedItem();
        
        if(bandera==0 || bandera==1){
             if(banderaInterna==0){
                if(comprobarrepetidos(l.getIdLibro())!= true){
                    if(jtfcantidad.getText().isEmpty()|| jtfcantidad.getText().equals("0"))
                    {JOptionPane.showMessageDialog(this,"Cantidad incorrecta");}
                        else{
                        LineaPedido lp= new LineaPedido();
                        lp.setIdLineaPedido(0);
                        lp.setIdPedidoCompra(Integer.parseInt(jtfNroRd.getText()));
                        lp.setIdLibro(l.getIdLibro());
                        lp.setCantidad(Integer.parseInt(jtfcantidad.getText()));
                        lp.setCantRecibida(0);
                        lp.setCostoLineaPedido(l.getCostoLibro());
                        tblineaPedido.add(lp);
                        cargarTablaLineaPedido();
                        jtfcantidad.setText("");
                        } } }
              else{
                        costo=Float.parseFloat(jtfCosto.getText());
                        for(int i =0; i<tblineaPedido.size(); i++)
                          {if(tblineaPedido.get(i).getIdLibro() == idlibroLinea){
                                   tblineaPedido.get(i).setCostoLineaPedido(costo);
                                   tblineaPedido.get(i).setCantidad(Integer.parseInt(jtfcantidad.getText()));
                         }}
                        cargarTablaLineaPedido();
                        banderaInterna=0;
                        jcbLibro.setEnabled(true);
                        jtfcantidad.setText("");
                        labelCosto2.setVisible(false);
                        jtfCosto.setText("");
                        jtfCosto.setVisible(false);
                        btnAñadir.setText("Añadir");
                        btnQuitar.setText("Quitar");
                        
                  }  
        }
        btnQuitar.setEnabled(true);
        jtableLineaPedido.setEnabled(true);
    }//GEN-LAST:event_btnAñadirActionPerformed

    private void btnQuitarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuitarActionPerformed
       if(banderaInterna==0){
             v=jtableLineaPedido.getSelectedRow();
             //indiceModelo = jtableLibros.convertRowIndexToModel (v);//convierte el indice de la vista en el indice del modelo 
                if(v != -1)
                {
                int respuesta=JOptionPane.showConfirmDialog(null, "¿Realmente desea quitar el libro?","Advertencia", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if(respuesta == 0)
                {
                    tblineaPedido.remove(v);
                    cargarTablaLineaPedido();
                    control=false;
                }
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar el libro a quitar", "Error", WIDTH);
                }
                }
       else{
        banderaInterna=0;   
        jcbLibro.setEnabled(true);
        jtfcantidad.setText("");
        labelCosto2.setVisible(false);
        jtfCosto.setText("");
        jtfCosto.setVisible(false);
        btnAñadir.setText("Añadir");
        btnQuitar.setText("Quitar");
       }
       if (tblineaPedido.isEmpty())
       {
           btnQuitar.setEnabled(false);
       }
       jtableLineaPedido.setEnabled(true);
    }//GEN-LAST:event_btnQuitarActionPerformed

    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed
        
        if(comprobarTablaVacia()== false && comprobarFechaNula()== false)
        {
            oConsultaCompra.agregarPedido(capturarCampos());
            for (int i = 0; i < tblineaPedido.size(); i++) {
                oConsultaCompra.agregarLineaPedido(tblineaPedido.get(i));}
            JOptionPane.showMessageDialog(this,"Pedido Registrado \n Imprimiendo Pedido");
            List lista = new ArrayList<>();
        
                for(int i=0;i<jtableLineaPedido.getRowCount();i++)
                {
                    LineasPedido facturas = new LineasPedido(jtableLineaPedido.getValueAt(i, 1).toString(),jtableLineaPedido.getValueAt(i, 2).toString());
                    lista.add(facturas);
                    
                }
            try {
            JasperReport reporte = (JasperReport) JRLoader.loadObject("ReporteFacturaCompra.jasper");
            Map parametro = new HashMap();
            parametro.put("idFactura", jtfNroRd.getText());
            parametro.put("proveedor", jcbProveedor.getSelectedItem().toString());
            parametro.put("importe", jtfTotal.getText());
            JasperPrint jprint = JasperFillManager.fillReport(reporte, parametro, new JRBeanCollectionDataSource(lista));
            JasperViewer jviewer = new JasperViewer(jprint,false);
            jviewer.show();
            
        } catch (JRException ex) {
            Logger.getLogger(BusquedaFactura.class.getName()).log(Level.SEVERE, null, ex);
        }
            this.removeAll();this.repaint();this.revalidate();
            //jasper.ejecutarReporte(idNuevoPedido);
        }
  
    }//GEN-LAST:event_btnAceptarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        int respuesta=JOptionPane.showConfirmDialog(null, "No se registro el pedido \n¿Desea salir?","Advertencia", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if(respuesta == 0)
        {this.removeAll();this.repaint();this.revalidate();}
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void jtfcantidadKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfcantidadKeyTyped
          char car = evt.getKeyChar();
            longitud=jtfcantidad.getText().length();
               if(controlC!=false){
                labelCantidad.setVisible(false);
                jtfcantidad.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
                controlC=false;
                }
            if(longitud < 5 && controLabel!=true){
           
                if((car<'0' || car>'9')&& car!=KeyEvent.VK_BACK_SPACE && car!=KeyEvent.VK_ENTER){
                labelCantidad.setVisible(true);
                labelCantidad.setText("Solo se deben ingresar numeros");
                jtfcantidad.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
                controLabel=true;
                longitud2=jtfcantidad.getText().length();
                }
                else{
                labelCantidad.setVisible(false);
                jtfcantidad.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
                controLabel=false;
                }
            }
            else{
                evt.consume();    
                    if(longitud-1 < longitud2){
                    labelCantidad.setVisible(false);
                    jtfcantidad.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
                    controLabel=false;
                }
                
            }
    }//GEN-LAST:event_jtfcantidadKeyTyped

    private void jtfCostoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfCostoKeyTyped
        char car = evt.getKeyChar();
            longitud=jtfCosto.getText().length();
               
            if(controlCO!=false){
                labelCosto.setVisible(false);
                jtfCosto.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
                controlCO=false;
                }
            if(longitud < 7 && controLabel!=true){
           
                if((car<'0' || car>'9')&& car!=','&& car!=KeyEvent.VK_BACK_SPACE && car!=KeyEvent.VK_ENTER){
                labelCosto.setVisible(true);
                labelCosto.setText("Solo se deben ingresar numeros");
                jtfCosto.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
                controLabel=true;
                longitud2=jtfCosto.getText().length();
                }
                else{
                labelCosto.setVisible(false);
                jtfCosto.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
                controLabel=false;
                }
            }
            else{
                evt.consume();    
                    if(longitud-1 < longitud2){
                    labelCosto.setVisible(false);
                    jtfCosto.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
                    controLabel=false;
                }
                
            }
        
      
    }//GEN-LAST:event_jtfCostoKeyTyped

    private void jcbLibroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbLibroActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jcbLibroActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnAñadir;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnGenerar;
    private javax.swing.JButton btnNuevoProv;
    private javax.swing.JButton btnQuitar;
    private javax.swing.JButton btnVerProv;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanelCabeceraPedido;
    private javax.swing.JPanel jPanelLineaPedido;
    private javax.swing.JPanel jPanelTabla;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JComboBox jcbEstadoPedido;
    private org.jdesktop.swingx.JXComboBox jcbLibro;
    private org.jdesktop.swingx.JXComboBox jcbProveedor;
    private com.toedter.calendar.JDateChooser jdateFpedido;
    private javax.swing.JTable jtableLineaPedido;
    private javax.swing.JTextField jtfCosto;
    private javax.swing.JTextField jtfNroRd;
    private javax.swing.JTextField jtfTotal;
    private javax.swing.JTextField jtfcantidad;
    private javax.swing.JLabel labelCantidad;
    private javax.swing.JLabel labelCosto;
    private javax.swing.JLabel labelCosto2;
    // End of variables declaration//GEN-END:variables
}
