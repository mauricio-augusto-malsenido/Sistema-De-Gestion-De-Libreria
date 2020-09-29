package Compra;

import static java.awt.Component.TOP_ALIGNMENT;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import static java.awt.image.ImageObserver.WIDTH;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import libreria.consultasLibreria;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

/**
 *
 * @author SEBASTIAN
 */
public class jdPedido extends javax.swing.JDialog {
private ConsultaCompra oConsultaCompra;
private consultasLibreria oConsultaLibreria;
private int v,indiceModelo,bandera,idNuevoPedido;
private Date dateFpedido;
private java.sql.Date fpedido;
private boolean control,controlbtnrc=true;
private PedidoCompra pc= new PedidoCompra();
private List<remitoCompraLibro> TablaLineaRemito;
private proveedor ProveedorCompra;
private ArrayList<remitoCompra> tbr= new ArrayList<>();
private ArrayList<CantidadRecibida> acc= new ArrayList<>();
private ArrayList<LineaPedido> alp= new ArrayList<>();
private String estadoInicial,estadoFinal;
//private IniciarReporte jasper;
    
public jdPedido(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }
public jdPedido(java.awt.Frame parent, boolean modal,int idpedido,int b) {
        super(parent, modal);
        initComponents();
        
        oConsultaCompra = new ConsultaCompra();
        oConsultaLibreria= new consultasLibreria();
        bandera=b;
        CargarComboProveedores();
        AutoCompleteDecorator.decorate(this.jcbProveedor);
        dateFpedido=new Date(00, 0, 1);
         
        if(bandera==1){
        jtfNroRd.setEditable(false);
        btnAceptar.setText("Guardar");
        btnCancelar.setText("Cancelar");
        jcbProveedor.setEnabled(false);
        jdateFpedido.setEnabled(false);
        idNuevoPedido=idpedido;
        pc=oConsultaCompra.getPedidoPorId(idNuevoPedido);
        cargarCampos(pc);
        cargarTabla(oConsultaCompra.getAllLineaPedido(idNuevoPedido));
        cargarTablaRemitos(oConsultaCompra.getAllRemitoPorIdFactura(idNuevoPedido));
        AccionDobleClick(1);
        }
        
    }


private void cargarTabla(List<LineaPedido> li) {
        List<LineaPedido> lista =li;
        String[] columnNames = {"Id","Cod_Libro","Titulo","Cantidad_Pedido","CostoUnitario","Subtotal","Cantidad_Recibida"};
        int[] anchos = {5,5,100,20,20,20,20};
        
        Object[][] data = new Object[lista.size()][columnNames.length];
         
        
         for (int i = 0; i < lista.size(); i++) {
            data[i][0] = lista.get(i).getIdLineaPedido();
            data[i][1] = lista.get(i).getIdLibro();
            data[i][2] = oConsultaLibreria.getLibroPorId(lista.get(i).getIdLibro()).getTitulo();
            data[i][3] = lista.get(i).getCantidad();
            data[i][4] = lista.get(i).getCostoLineaPedido();
            data[i][5] = lista.get(i).getCostoLineaPedido() * lista.get(i).getCantidad();
            data[i][6] = lista.get(i).getCantRecibida();
            
            if(lista.get(i).getCantidad() != lista.get(i).getCantRecibida()){
                    if(lista.get(i).getCantidad() > lista.get(i).getCantRecibida()){
                        controlbtnrc=false;jComboBox1.setSelectedIndex(2);
                    }
                    if(lista.get(i).getCantidad() <= lista.get(i).getCantRecibida())    
                    {
                     controlbtnrc=true;jComboBox1.setSelectedIndex(1);
                    }
                }
        }
       DefaultTableModel dftm = new DefaultTableModel(data, columnNames)
                {
		//metodo para que las celdas del jtable sean no-editables	
                    @Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
       jtableLibros.setModel(dftm);
       for(int i = 0; i < jtableLibros.getColumnCount(); i++) {

        jtableLibros.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);

        }
       comprobarCanPeCanRe();
    }
private void cargarTablaRemitos(List<remitoCompra> li) {
        List<remitoCompra> lista =li;
        if(li.isEmpty()){btnEliminar.setEnabled(false);jComboBox1.setSelectedIndex(0);}else{btnEliminar.setEnabled(true);}
         String[] columnNames = {"Id","N°Remito","Fecha Remito","Fecha Recibida"};
        int[] anchos = {40,40,100,100};
        
        Object[][] data = new Object[lista.size()][columnNames.length];
         
         for (int i = 0; i < lista.size(); i++) {
            data[i][0] = lista.get(i).getIdRemitoCompra();
            data[i][1] = lista.get(i).getNroRemitoProv();
            data[i][2] = lista.get(i).getFechaRemitoCompra();
            data[i][3] = lista.get(i).getFechaRecepcionCompra();
            
            
        }
       DefaultTableModel dftm = new DefaultTableModel(data, columnNames)
                {
		//metodo para que las celdas del jtable sean no-editables	
                    @Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
       jtableRemitos.setModel(dftm);
       for(int i = 0; i < jtableRemitos.getColumnCount(); i++) {

        jtableRemitos.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);

        }
 
    }
private int getIdRemito(int im){
        String[] fila= new String[1];//almaceno los valores del registro seleccionado en el string "fila"
        fila[0]=""+jtableRemitos.getModel().getValueAt(im, 0);
        
        int codLi=Integer.parseInt(fila[0]);
        
        return  codLi;
    }
private int getIdLineaRemito(int im){
        String[] fila= new String[1];//almaceno los valores del registro seleccionado en el string "fila"
        fila[0]=""+jtableRemitos.getModel().getValueAt(im, 0);
        
        int codLi=Integer.parseInt(fila[0]);
        
        return  codLi;
    }
private PedidoCompra capturarCampos(){
     proveedor p= (proveedor) jcbProveedor.getSelectedItem();
     estadoFinal=(String) jComboBox1.getSelectedItem();
        if(jdateFpedido.getDate() != null)
           {dateFpedido=jdateFpedido.getDate();
           fpedido=new java.sql.Date(dateFpedido.getTime());
           }
           
        PedidoCompra pc= new PedidoCompra(idNuevoPedido, fpedido, p.getIdProveedor(),jComboBox1.getSelectedItem().toString(), Float.parseFloat(jtfTotal.getText()));
        return  pc;
        }
private  void cargarCampos(PedidoCompra pedido){
       
        proveedor p=oConsultaCompra.getProveedorPorId(pedido.getIdProveedor());
        fpedido=pedido.getFechaPedido();
        dateFpedido=new java.util.Date(fpedido.getTime());
        estadoInicial=pedido.getEstado();
        jdateFpedido.setDate(dateFpedido);
        idNuevoPedido=pedido.getIdPedidoCompra();
        jtfNroRd.setText(""+pedido.getIdPedidoCompra());
        jcbProveedor.setSelectedItem(p);
        jtfTotal.setText(""+pedido.getTotalPedido());
        
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
private void AccionDobleClick(final int ban){
     jtableRemitos.addMouseListener(new MouseAdapter() 
        {
           @Override
           public void mouseClicked(MouseEvent e) 
           {
             if(e.getClickCount()==2){
              int fila = jtableRemitos.rowAtPoint(e.getPoint());
              int columna = jtableRemitos.columnAtPoint(e.getPoint());
                     if ((fila > -1) && (columna > -1))
                     {
                        v=jtableRemitos.getSelectedRow();
                        indiceModelo = jtableRemitos.convertRowIndexToModel (v);
                        jdNuevoRemito jdnr=new jdNuevoRemito(null,true, 1, idNuevoPedido, getIdLineaRemito(indiceModelo));
                        jdnr.setVisible(true);
                        jdnr.setLocationRelativeTo(null);
                        cargarTablaRemitos(oConsultaCompra.getAllRemitoPorIdFactura(idNuevoPedido)); 
                        CargarLrLp();
                     }
             }
           }
        });
    
    }
public boolean comprobarFechaNula(){
        if(jdateFpedido.getDate()==null)
        {control=true;
        JOptionPane.showMessageDialog(null, "Debe registrar la fecha Pedido", "ADVERTENCIA", WIDTH);
        
        }
        else{
        control=false;
        }
        return control;
    }
public void comprobarCanPeCanRe(){
        if(controlbtnrc==true)
        {btnRemito.setEnabled(false);
        jComboBox1.setSelectedIndex(1);}
        else{btnRemito.setEnabled(true);}
    }
private void CargarLrLp(){
acc=(ArrayList<CantidadRecibida>)oConsultaCompra.getAllCantidadRecibida(idNuevoPedido);
alp=(ArrayList<LineaPedido>) oConsultaCompra.getAllLineaPedido(idNuevoPedido);

if(acc.isEmpty()){
        for(int i=0; i<alp.size(); i++)
        {
                alp.get(i).setCantRecibida(0);
                oConsultaCompra.modificarLineaPedido(alp.get(i));
        }
        cargarTabla(oConsultaCompra.getAllLineaPedido(idNuevoPedido));
        
}
else{
        for(int i=0; i<alp.size(); i++)
            {

                    for(int j=0; j<acc.size(); j++)
                    {
                      if(alp.get(i).getIdLibro()==acc.get(j).getIdLibro())
                      {

                        alp.get(i).setCantRecibida(acc.get(j).getCantidadRecibida());
                        oConsultaCompra.modificarLineaPedido(alp.get(i));
                      }
                  }
            }
            cargarTabla(oConsultaCompra.getAllLineaPedido(idNuevoPedido));
    }
}

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jtfNroRd = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jdateFpedido = new com.toedter.calendar.JDateChooser();
        jcbProveedor = new org.jdesktop.swingx.JXComboBox();
        jComboBox1 = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        btnVerProv1 = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanelDetalle = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtableLibros = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jtfTotal = new javax.swing.JTextField();
        jPanelRemito = new javax.swing.JPanel();
        btnRemito = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtableRemitos = new javax.swing.JTable();
        btnEliminar = new javax.swing.JButton();
        btnAceptar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Pedido");
        setResizable(false);

        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setText("N° Pedido");

        jtfNroRd.setEditable(false);
        jtfNroRd.setDisabledTextColor(new java.awt.Color(0, 0, 0));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setText("Fecha Pedido");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel5.setText("Proveedor");

        jcbProveedor.setEditable(true);

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "ESPERA", "COMPLETO", "INCOMPLETO" }));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setText("Estado");

        btnVerProv1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/encontrar-los-prismaticos-de-busqueda-icono-9145-32.png"))); // NOI18N
        btnVerProv1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerProv1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jtfNroRd, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jdateFpedido, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jcbProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnVerProv1, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(jtfNroRd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jdateFpedido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel3)))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel5)
                        .addComponent(jcbProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnVerProv1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanelDetalle.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        jtableLibros.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Cod_Libro", "Titulo", "Cantidad_Pedido", "Cantidad_Recibida"
            }
        ));
        jScrollPane1.setViewportView(jtableLibros);

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel4.setText("TOTAL ");

        jtfTotal.setEditable(false);
        jtfTotal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtfTotalActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelDetalleLayout = new javax.swing.GroupLayout(jPanelDetalle);
        jPanelDetalle.setLayout(jPanelDetalleLayout);
        jPanelDetalleLayout.setHorizontalGroup(
            jPanelDetalleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelDetalleLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelDetalleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 646, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelDetalleLayout.createSequentialGroup()
                        .addGap(0, 522, Short.MAX_VALUE)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jtfTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanelDetalleLayout.setVerticalGroup(
            jPanelDetalleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelDetalleLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 401, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelDetalleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtfTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addContainerGap())
        );

        jTabbedPane1.addTab("PEDIDO", new javax.swing.ImageIcon(getClass().getResource("/recursos/detalleFac.png")), jPanelDetalle); // NOI18N

        btnRemito.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/anadir-bala-icono-4686-16.png"))); // NOI18N
        btnRemito.setText("Nuevo Remito");
        btnRemito.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemitoActionPerformed(evt);
            }
        });

        jtableRemitos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "id", "N° Remito", "Fecha Remito", "Fecha Recepcion"
            }
        ));
        jScrollPane2.setViewportView(jtableRemitos);

        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/eliminar_1.png"))); // NOI18N
        btnEliminar.setText("Eliminar Remito");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelRemitoLayout = new javax.swing.GroupLayout(jPanelRemito);
        jPanelRemito.setLayout(jPanelRemitoLayout);
        jPanelRemitoLayout.setHorizontalGroup(
            jPanelRemitoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelRemitoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelRemitoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelRemitoLayout.createSequentialGroup()
                        .addComponent(btnRemito)
                        .addGap(18, 18, 18)
                        .addComponent(btnEliminar)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 650, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanelRemitoLayout.setVerticalGroup(
            jPanelRemitoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelRemitoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelRemitoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnRemito)
                    .addComponent(btnEliminar))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 401, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("REMITOS", new javax.swing.ImageIcon(getClass().getResource("/recursos/tarea.png")), jPanelRemito); // NOI18N

        btnAceptar.setText("Aceptar");
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnAceptar)
                        .addGap(18, 18, 18)
                        .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnAceptar, btnCancelar});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 544, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAceptar)
                    .addComponent(btnCancelar))
                .addGap(10, 10, 10))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnAceptar, btnCancelar});

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed
         if(bandera==1){
            oConsultaCompra.modificarPedido(capturarCampos());
            this.dispose();
        }
    }//GEN-LAST:event_btnAceptarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
       if(estadoInicial.equals(estadoFinal))
       {this.dispose();}
       else{
            oConsultaCompra.modificarPedido(capturarCampos());
            this.dispose();
           }
        
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnRemitoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemitoActionPerformed
        jdNuevoRemito nr=new jdNuevoRemito(null,true, 0, idNuevoPedido,0);
        nr.setVisible(true);
        nr.setLocationRelativeTo(null);
        cargarTablaRemitos(oConsultaCompra.getAllRemitoPorIdFactura(idNuevoPedido));
        controlbtnrc=true;
        CargarLrLp();
        //MODIFICACION DE COSTOS DE LIBROS CUANDO EXISTE UN REMITO REGISTRADO
        if(oConsultaCompra.getAllRemitoPorIdFactura(idNuevoPedido).size()==1){
            alp=(ArrayList<LineaPedido>) oConsultaCompra.getAllLineaPedido(idNuevoPedido);

            for(int i=0; i<alp.size(); i++)
            {
            oConsultaLibreria.modificarCostoLibro(alp.get(i).getCostoLineaPedido(), alp.get(i).getIdLibro());
            }
        }
    }//GEN-LAST:event_btnRemitoActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        v=jtableRemitos.getSelectedRow();//n° fila selccionada
        if(v != -1)
        {
        indiceModelo = jtableRemitos.convertRowIndexToModel (v);//convierte el indice de la vista en el indice del modelo
        int idRemitoE=getIdRemito(indiceModelo);
        
        int respuesta=JOptionPane.showConfirmDialog(null, "¿Realmente desea quitar el remito de la lista?","Advertencia", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if(respuesta == 0)
        {

            TablaLineaRemito=oConsultaCompra.getAllRemitoPorId(idRemitoE);
            for (int i = 0; i < TablaLineaRemito.size(); i++) {
                int stockactual=oConsultaLibreria.getLibroPorId(TablaLineaRemito.get(i).getIdLibro()).getStock();
                System.out.println("ACTUAL:"+stockactual);
                int descontar=TablaLineaRemito.get(i).getCantidadRecibida();
                System.out.println("DESCONTAR:"+descontar);
                int stocknuevo=stockactual-descontar;
                System.out.println("NUEVO:"+stocknuevo);
                oConsultaCompra.eliminaLinearRemito(TablaLineaRemito.get(i).getIdRemitoCompraLibro());
                oConsultaLibreria.modificarStockLibro(stocknuevo, TablaLineaRemito.get(i).getIdLibro());
            }

            oConsultaCompra.eliminarRemito(idRemitoE);
            cargarTablaRemitos(oConsultaCompra.getAllRemitoPorIdFactura(idNuevoPedido));
            controlbtnrc=true;
            CargarLrLp();
            
            //comprobarCanPeCanRe();
        }
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Debe seleccionar el remito a eliminar", "Error", WIDTH);
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnVerProv1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerProv1ActionPerformed
        proveedor em= (proveedor) jcbProveedor.getSelectedItem();
        JOptionPane.showMessageDialog(this, em.MostarProveedor(),"Proveedor", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_btnVerProv1ActionPerformed

    private void jtfTotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtfTotalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtfTotalActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(jdPedido.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(jdPedido.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(jdPedido.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(jdPedido.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                jdPedido dialog = new jdPedido(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnRemito;
    private javax.swing.JButton btnVerProv1;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanelDetalle;
    private javax.swing.JPanel jPanelRemito;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private org.jdesktop.swingx.JXComboBox jcbProveedor;
    private com.toedter.calendar.JDateChooser jdateFpedido;
    private javax.swing.JTable jtableLibros;
    private javax.swing.JTable jtableRemitos;
    private javax.swing.JTextField jtfNroRd;
    private javax.swing.JTextField jtfTotal;
    // End of variables declaration//GEN-END:variables
}
