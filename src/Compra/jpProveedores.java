package Compra;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import mapa.ConsultaMapa;
import mapa.localidad;
import mapa.provincia;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

/**
 *
 * @author SEBASTIAN
 */
public final class jpProveedores extends javax.swing.JPanel {
private ConsultaCompra oConsultaCompra;
private ConsultaMapa oConsultaMapa;
private TableRowSorter<TableModel> sorter;//para ordenar la tabla
private int v=0,indiceModelo=0,bandera=0,id=0,control,longitud,longitud2;
private proveedor pProveedor, p;
private localidad pLocalidad;
private boolean controLabel=false,controlN,controlC,controlT,controlL,estadoPro;
/**
     * Creates new form jpProveedores
     */
//BANDERA 0 NUEVO
//BANDERA 1 MODIFICACIONC 
//CONTROL 0 ABM
//CONTROL 1 ALTAS
     public jpProveedores(int c) {
        initComponents();
        control=c;
        oConsultaCompra = new ConsultaCompra();
        oConsultaMapa = new ConsultaMapa();
        bandera=0;
        CargarComboLocalidades();
        AutoCompleteDecorator.decorate(this.jcbLocalidad);
        labelNombre.setVisible(false);
        labelCuit.setVisible(false);
        labelTel.setVisible(false);
        labelDir.setVisible(false);
        labelLocalidad.setVisible(false);
        btnDesha.setText("Deshabilitar");
        btnDesha.setEnabled(false);
        jXTaskPane2.setCollapsed(true);
        if(control==1){
        jLabel1.setVisible(false);
        jtfbuscarProveedor.setVisible(false);
        jScrollPane1.setVisible(false);
        }if(control==0){
        busqueda();
          jtableProveedores.addMouseListener(new MouseAdapter() 
        {
           @Override
           public void mouseClicked(MouseEvent e) 
           {
             if(e.getClickCount()== 2){
              int fila = jtableProveedores.rowAtPoint(e.getPoint());
              int columna = jtableProveedores.columnAtPoint(e.getPoint());
             /*El método rowAtPoint() nos devuelve -1 si pinchamos en el JTable
              pero fuera de cualquier fila*/
              
                     if ((fila > -1) && (columna > -1))
                     {
                       v=jtableProveedores.getSelectedRow();//n° fila selccionada
                       indiceModelo = jtableProveedores.convertRowIndexToModel (v);//convierte el indice de la vista en el indice del modelo 
                       
                       jXTaskPane2.setTitle("Modificar Proveedor");
                       jXTaskPane2.setCollapsed(false);
                       jtfbuscarProveedor.setEnabled(false);
                       jtfbuscarProveedor.setEditable(false);
                       bandera=1;
                       cargarCampos(oConsultaCompra.getProveedorPorId(getIdProveedor(indiceModelo)));
                       p = oConsultaCompra.getProveedorPorId(getIdProveedor(indiceModelo));
                       jtableProveedores.setEnabled(false);
                     }
             }
           }
        });
        
        cargarTabla(oConsultaCompra.getAllProveedor());
        }
        
    }
    
     private void busqueda(){
     
     jtfbuscarProveedor.getDocument().addDocumentListener(
                new DocumentListener() {
                    @Override
                    public void changedUpdate(DocumentEvent e) {
                        filtrar();
                    }
                    @Override
                    public void insertUpdate(DocumentEvent e) {
                        filtrar();
                    }
                    @Override
                    public void removeUpdate(DocumentEvent e) {
                        filtrar();
                    }
                });
    }
     
     private void filtrar() {
        RowFilter<TableModel, Object> rf = null;
        int indiceColumnaTabla=1;
        try {
            
            rf = RowFilter.regexFilter(jtfbuscarProveedor.getText().toUpperCase(), indiceColumnaTabla);
        } catch (java.util.regex.PatternSyntaxException e) {
        }
        sorter.setRowFilter(rf);
    }
     
     public void cargarTabla(List<proveedor> l){
    
        String[] columnNames = {"Id","Nombre","CUIT","Direccion","Tel","Localidad","Provincia","Estado"};
        int[] anchos = {20,100,100,150,100,150,100,50};
        
        Object[][] data = new Object[l.size()][columnNames.length];
         
       for (int i = 0; i < l.size(); i++) {
        String localidadProve=oConsultaMapa.getLocalidadPorId(l.get(i).getIdLocalidad()).getNombreLocalidad();
        String provinciaProve=oConsultaMapa.getProvinciaPorId(oConsultaMapa.getLocalidadPorId(l.get(i).getIdLocalidad()).getIdProvincia()).getNombreProvincia();
        
            data[i][0] = l.get(i).getIdProveedor();
            data[i][1] = l.get(i).getNombreProveedor();
            data[i][2] = l.get(i).getCuitProveedor();
            data[i][3] = l.get(i).getDireccionProveedor();
            data[i][4] = l.get(i).getTelProveedor();
            data[i][5] = localidadProve;
            data[i][6] = provinciaProve;
            
            if(l.get(i).getEstadoProveedor()==false)
                data[i][7] ="DESHABILITADO";
            else data[i][7] ="HABILITADO";
        }
       DefaultTableModel dftm = new DefaultTableModel(data, columnNames)
                {
		//metodo para que las celdas del jtable sean no-editables	
                    @Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
       jtableProveedores.setModel(dftm);
       for(int i = 0; i < jtableProveedores.getColumnCount(); i++) {
        jtableProveedores.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);

        }
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        for(int i=0;i<jtableProveedores.getColumnCount();i++)
        {
            jtableProveedores.getColumnModel().getColumn(i).setCellRenderer(tcr);
        }
        sorter = new TableRowSorter<TableModel>(dftm);
        jtableProveedores.setRowSorter(sorter);
        jtableProveedores.getRowSorter().toggleSortOrder(0);
        jtableProveedores.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        jtfnombre.setText("");
        jtfDir.setText("");
        jtfTel.setText("");
        jtfCuit.setText("");
    }
     
     private int getIdProveedor(int im){
        String[] fila= new String[1];//almaceno los valores del registro seleccionado en el string "fila"
        fila[0]=""+jtableProveedores.getModel().getValueAt(im, 0);
        
        int idRep=Integer.parseInt(fila[0]);
        
        return  idRep;
    }
     
     private  void cargarCampos(proveedor proveedor){
        btnDesha.setEnabled(true);
        pLocalidad=oConsultaMapa.getLocalidadPorId(proveedor.getIdLocalidad());
        estadoPro=proveedor.getEstadoProveedor();
        if(estadoPro==false){btnDesha.setText("Habilitar");}else{ btnDesha.setText("Deshabilitar");}
        id=proveedor.getIdProveedor();
        jtfnombre.setText(proveedor.getNombreProveedor());
        jtfCuit.setText(""+proveedor.getCuitProveedor());
        jtfDir.setText(proveedor.getDireccionProveedor());
        jtfTel.setText(proveedor.getTelProveedor());
        jcbLocalidad.setSelectedItem(pLocalidad); }
     
     private proveedor capturarCampos(){
     localidad l= (localidad) jcbLocalidad.getSelectedItem();
        
        pProveedor= new proveedor(
                id,
                jtfnombre.getText().toUpperCase(),
                jtfDir.getText().toUpperCase(),
                jtfCuit.getText().toUpperCase(),
                l.getIdLocalidad(),
                jtfTel.getText(),
                estadoPro
                );
                
        
        return  pProveedor;
        }
     
     private void CargarComboLocalidades(){
        jcbLocalidad.addItem("");
         List<localidad> lista = oConsultaMapa.getAllLocalidad();
        for (int i = 0; i < lista.size(); i++) {
            localidad emc=lista.get(i);
            jcbLocalidad.addItem(emc);
            localidad objeto=(localidad) jcbLocalidad.getItemAt(1);
         }
    }
     
     public boolean comprobarNulosN(){
                   if(jtfnombre.getText().equals(""))
                    {controlN=true;
                    labelNombre.setVisible(true);
                    labelNombre.setText("Debe ingresar un nombre");
                    jtfnombre.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
                    }
                    else{
                    controlN=false;
                    }
                    
                    return controlN;
    }
     public boolean comprobarNulosC(){
                    if(jtfCuit.getText().equals(""))
                    {controlC=true;
                    labelCuit.setVisible(true);
                    labelCuit.setText("Debe ingresar un número de CUIT");
                    jtfCuit.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
                    }
                    else{
                    controlC=false;
                    }
                    
                    return controlC;
    }
     public boolean comprobarNulosT(){
                if(jtfTel.getText().equals(""))
                    {controlT=true;
                    labelTel.setVisible(true);
                    labelTel.setText("Debe ingresar un telefono");
                    jtfTel.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
                    }
                    else{
                    controlT=false;
                    }
                    
                    return controlT;
    }
     public boolean comprobarNulosL(){
            if(jcbLocalidad.getSelectedIndex()== 0 || jcbLocalidad.getSelectedIndex()== -1)
         {   labelLocalidad.setVisible(true);
             labelLocalidad.setText("Debe seleccionar una localidad");
             controlL=true;}
         else {controlL=false;}
        return controlL;
     
    }
     public boolean comprobarExistencia(){
         proveedor loca = capturarCampos();
         boolean u = false;
         for(int i=0;i<oConsultaCompra.getAllProveedor().size();i++)
         {
             String nombre = oConsultaCompra.getAllProveedor().get(i).getNombreProveedor();
             String tel = oConsultaCompra.getAllProveedor().get(i).getTelProveedor();
             String cuit = oConsultaCompra.getAllProveedor().get(i).getCuitProveedor();
             String dir = oConsultaCompra.getAllProveedor().get(i).getDireccionProveedor();
             localidad lo = oConsultaMapa.getLocalidadPorId(oConsultaCompra.getAllProveedor().get(i).getIdLocalidad());
             if((loca.getCuitProveedor().contentEquals(cuit)) || (loca.getNombreProveedor().contentEquals(nombre) && loca.getTelProveedor().contentEquals(tel) && loca.getCuitProveedor().contentEquals(cuit) && loca.getDireccionProveedor().contentEquals(dir) && loca.getIdLocalidad() == lo.getIdLocalidad())){
                 u = true;
             }
         }
         if((bandera == 1) && (loca.getCuitProveedor().contentEquals(p.getCuitProveedor())) || (loca.getNombreProveedor().contentEquals(p.getNombreProveedor()) && loca.getCuitProveedor().contentEquals(p.getCuitProveedor()) && loca.getTelProveedor().contentEquals(p.getTelProveedor()) && loca.getDireccionProveedor().contentEquals(p.getDireccionProveedor()) && loca.getIdLocalidad() == p.getIdLocalidad())){
                 u = false;
             }
         return u;
     }
     
     public void limpiar(){
        jtfnombre.setText("");
        jtfCuit.setText("");
        jtfTel.setText("");
        jtfDir.setText(""); 
        labelNombre.setVisible(false);
        jtfnombre.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
        labelCuit.setVisible(false);
        jtfCuit.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
        labelTel.setVisible(false);
        jtfTel.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
        labelLocalidad.setVisible(false);
        labelDir.setVisible(false);
        jtfDir.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
        id=0;
        bandera=0;
        estadoPro=false;
        jcbLocalidad.setSelectedIndex(0);
        jtableProveedores.setEnabled(true);
        btnDesha.setEnabled(false);
        btnDesha.setText("Deshabilitar");
        jXTaskPane2.setTitle("Nuevo Proveedor");
        jXTaskPane2.setCollapsed(true);
        jtfbuscarProveedor.setEnabled(true);
        jtfbuscarProveedor.setEditable(true);
      }
 
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jtfbuscarProveedor = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtableProveedores = new javax.swing.JTable();
        jXTaskPane2 = new org.jdesktop.swingx.JXTaskPane();
        jPanel4 = new javax.swing.JPanel();
        jtfnombre = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        btnguardar1 = new javax.swing.JButton();
        btnCancelar1 = new javax.swing.JButton();
        btnDesha = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jtfCuit = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jtfDir = new javax.swing.JTextField();
        jtfTel = new javax.swing.JTextField();
        jcbLocalidad = new org.jdesktop.swingx.JXComboBox();
        LOCALIDAD = new javax.swing.JLabel();
        labelNombre = new javax.swing.JLabel();
        labelCuit = new javax.swing.JLabel();
        labelTel = new javax.swing.JLabel();
        labelLocalidad = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        labelDir = new javax.swing.JLabel();

        jLabel1.setText("Buscar Proveedor");

        jtableProveedores.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "id", "Nombre", "CUIT", "Telefono", "Direccion", "Localidad"
            }
        ));
        jScrollPane1.setViewportView(jtableProveedores);

        jXTaskPane2.setTitle("Nuevo Proveedor");

        jPanel4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jtfnombre.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(0, 0, 0), null));
        jtfnombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtfnombreKeyTyped(evt);
            }
        });

        jLabel14.setText("NOMBRE**");

        btnguardar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/guardar.png"))); // NOI18N
        btnguardar1.setText("Guardar");
        btnguardar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnguardar1ActionPerformed(evt);
            }
        });

        btnCancelar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/cancelar.png"))); // NOI18N
        btnCancelar1.setText("Cancelar");
        btnCancelar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelar1ActionPerformed(evt);
            }
        });

        btnDesha.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/eliminar_1.png"))); // NOI18N
        btnDesha.setText("Deshabilitar");
        btnDesha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeshaActionPerformed(evt);
            }
        });

        jLabel2.setText("CUIT **");

        jtfCuit.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(0, 0, 0), null));
        jtfCuit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtfCuitKeyTyped(evt);
            }
        });

        jLabel4.setText("TELEFONO** ");

        jLabel8.setText("DIRECCION ");

        jtfDir.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(0, 0, 0), null));
        jtfDir.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtfDirKeyTyped(evt);
            }
        });

        jtfTel.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(0, 0, 0), null));
        jtfTel.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtfTelKeyTyped(evt);
            }
        });

        jcbLocalidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbLocalidadActionPerformed(evt);
            }
        });

        LOCALIDAD.setText("LOCALIDAD**");

        labelNombre.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        labelNombre.setForeground(new java.awt.Color(204, 51, 0));
        labelNombre.setText("jLabel3");

        labelCuit.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        labelCuit.setForeground(new java.awt.Color(204, 51, 0));
        labelCuit.setText("jLabel5");

        labelTel.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        labelTel.setForeground(new java.awt.Color(204, 51, 0));
        labelTel.setText("jLabel6");

        labelLocalidad.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        labelLocalidad.setForeground(new java.awt.Color(204, 51, 0));
        labelLocalidad.setText("jLabel7");

        jLabel3.setText("** Campos Obligatorios");

        labelDir.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        labelDir.setForeground(new java.awt.Color(204, 51, 0));
        labelDir.setText("jLabel7");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelNombre, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jtfnombre))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(labelCuit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jtfCuit))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(labelTel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jtfTel))
                        .addGap(53, 53, 53))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(btnguardar1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnCancelar1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnDesha)
                                .addGap(113, 113, 113))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(labelDir, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jtfDir)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                                        .addComponent(jLabel8)
                                        .addGap(0, 0, Short.MAX_VALUE)))
                                .addGap(65, 65, 65)))
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(LOCALIDAD)
                                    .addComponent(jcbLocalidad, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(134, 134, 134))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(labelLocalidad, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4))
                .addGap(2, 2, 2)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtfnombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtfCuit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtfTel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelCuit)
                    .addComponent(labelNombre)
                    .addComponent(labelTel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtfDir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(LOCALIDAD)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jcbLocalidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelLocalidad)
                    .addComponent(labelDir))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnguardar1)
                    .addComponent(btnCancelar1)
                    .addComponent(btnDesha)
                    .addComponent(jLabel3))
                .addContainerGap())
        );

        jXTaskPane2.getContentPane().add(jPanel4);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jtfbuscarProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jXTaskPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 674, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jtfbuscarProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jXTaskPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnguardar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnguardar1ActionPerformed
        if( comprobarNulosN()== false && comprobarNulosC()== false 
             && comprobarNulosT()== false && comprobarNulosL()== false 
                && controLabel!=true ){        
                if(comprobarExistencia() == false){
                if(bandera==1){
                    oConsultaCompra.modificarProveedor(capturarCampos());
                    JOptionPane.showMessageDialog(this,"Proveedor Modificado");
                    cargarTabla(oConsultaCompra.getAllProveedor());
                    bandera=0;
                    id=0;
                    estadoPro=false;
                    jcbLocalidad.setSelectedIndex(0);
                    limpiar();
                    jtableProveedores.setEnabled(true);
                }
                else{
                    oConsultaCompra.agregarProveedor(capturarCampos());
                    JOptionPane.showMessageDialog(this,"Proveedor Agregado");
                    cargarTabla(oConsultaCompra.getAllProveedor());
                    bandera=0;
                    limpiar();
                    jcbLocalidad.setSelectedIndex(0);
                }
                btnDesha.setEnabled(false);
                btnDesha.setText("Deshabilitar");
                jXTaskPane2.setTitle("Nuevo Proveedor");
                jXTaskPane2.setCollapsed(true);
                jtfbuscarProveedor.setEnabled(true);
                jtfbuscarProveedor.setEditable(true);
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "El proveedor o cuit que desea guardar ya existe", "Error", WIDTH);
                }
        }
        
    }//GEN-LAST:event_btnguardar1ActionPerformed

    private void btnCancelar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelar1ActionPerformed
        if(bandera==1 || bandera==0){
            int respuesta=JOptionPane.showConfirmDialog(null, "Se perderan todos los cambios realizados \n¿Desea Salir?","Advertencia", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if(respuesta == 0)
            {jtfnombre.setText("");
            jtfCuit.setText("");
            jtfTel.setText("");
            jtfDir.setText(""); 
            labelNombre.setVisible(false);
            jtfnombre.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
            labelCuit.setVisible(false);
            jtfCuit.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
            labelTel.setVisible(false);
            jtfTel.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
            labelLocalidad.setVisible(false);
            labelDir.setVisible(false);
            jtfDir.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
            id=0;
            bandera=0;
            estadoPro=false;
            jcbLocalidad.setSelectedIndex(0);
            jtableProveedores.setEnabled(true);
            jXTaskPane2.setTitle("Nuevo Proveedor");
            jXTaskPane2.setCollapsed(true);
            btnDesha.setEnabled(false);
            btnDesha.setText("Deshabilitar");
            jtfbuscarProveedor.setEnabled(true);
            jtfbuscarProveedor.setEditable(true);
            } }
    }//GEN-LAST:event_btnCancelar1ActionPerformed

    private void jtfnombreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfnombreKeyTyped
         int longitud1 = jtfnombre.getText().length();
            if(controlN!=false){
           labelNombre.setVisible(false);
           jtfnombre.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
           controlN=false;
            }
            if(longitud1 > 44){
                evt.consume();
                getToolkit().beep();
                labelNombre.setVisible(true);
                jtfnombre.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
                labelNombre.setText("Alcanzo la longitud maxima de 45 caracteres");
            }
            else
            {
                labelNombre.setVisible(false);
                jtfnombre.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
                controlN=false;
            }
        
    }//GEN-LAST:event_jtfnombreKeyTyped

    private void jtfCuitKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfCuitKeyTyped
       char car = evt.getKeyChar();
       longitud=jtfCuit.getText().length();
           if(controlC!=false){
           labelCuit.setVisible(false);
           jtfCuit.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
           controlC=false;
            }
           
           if(longitud < 12 && controLabel!=true){
           
                if((car<'0' || car>'9')&& car!=KeyEvent.VK_BACK_SPACE && car!=KeyEvent.VK_ENTER){
                labelCuit.setVisible(true);
                labelCuit.setText("Solo se deben ingresar numeros");
                jtfCuit.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
                controLabel=true;
                longitud2=jtfCuit.getText().length();
                }
                else{
                labelCuit.setVisible(false);
                jtfCuit.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
                controLabel=false;
                }
            }
            else{
                evt.consume();    
                    if(longitud-1 < longitud2){
                    labelCuit.setVisible(false);
                    jtfCuit.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
                    controLabel=false;
                }
                
            }
        
    }//GEN-LAST:event_jtfCuitKeyTyped

    private void jtfTelKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfTelKeyTyped
         char car = evt.getKeyChar();
         longitud=jtfTel.getText().length();
           if(controlT!=false){
           labelTel.setVisible(false);
           jtfTel.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
           controlT=false;
            }
            if(longitud < 15 && controLabel!=true){
           
                if((car<'0' || car>'9')&& car!=KeyEvent.VK_BACK_SPACE && car!=KeyEvent.VK_ENTER){
                labelTel.setVisible(true);
                labelTel.setText("Solo se deben ingresar numeros");
                jtfTel.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
                controLabel=true;
                longitud2=jtfTel.getText().length();
                }
                else{
                labelTel.setVisible(false);
                jtfTel.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
                controLabel=false;
                }
            }
            else{
                evt.consume();    
                    if(longitud-1 < longitud2){
                    labelTel.setVisible(false);
                    jtfTel.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
                    controLabel=false;
                }
                
            }
            
        
    }//GEN-LAST:event_jtfTelKeyTyped

    private void btnDeshaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeshaActionPerformed
        proveedor pro=capturarCampos();
        if(pro.getEstadoProveedor()==true){
            int respuesta=JOptionPane.showConfirmDialog(null, "¿Realmente desea deshabilitar este proveedor?","Advertencia", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if(respuesta == 0)
                {
                pro.setEstadoProveedor(false);
                oConsultaCompra.deshabilitarProveedor(pro);
                limpiar();
                cargarTabla(oConsultaCompra.getAllProveedor());
                }
        }
        else{
            int respuesta=JOptionPane.showConfirmDialog(null, "¿Realmente desea habilitar este proveedor?","Advertencia", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if(respuesta == 0)
                {
                pro.setEstadoProveedor(true);
                oConsultaCompra.deshabilitarProveedor(pro);
                limpiar();
                cargarTabla(oConsultaCompra.getAllProveedor());
                }
        }
        
    }//GEN-LAST:event_btnDeshaActionPerformed

    private void jcbLocalidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbLocalidadActionPerformed
         if(jcbLocalidad.getSelectedIndex()!= 0)
        {labelLocalidad.setVisible(false);controlL=false;}    
    }//GEN-LAST:event_jcbLocalidadActionPerformed

    private void jtfDirKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfDirKeyTyped
        int longitud1 = jtfDir.getText().length();
        if(longitud1 > 44){
                evt.consume();
                getToolkit().beep();
                labelDir.setVisible(true);
                jtfDir.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
                labelDir.setText("Alcanzo la longitud maxima de 45 caracteres");
            }
            else
            {
                labelDir.setVisible(false);
                jtfDir.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
                controlN=false;
            }
    }//GEN-LAST:event_jtfDirKeyTyped

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel LOCALIDAD;
    private javax.swing.JButton btnCancelar1;
    private javax.swing.JButton btnDesha;
    private javax.swing.JButton btnguardar1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private org.jdesktop.swingx.JXTaskPane jXTaskPane2;
    private org.jdesktop.swingx.JXComboBox jcbLocalidad;
    private javax.swing.JTable jtableProveedores;
    private javax.swing.JTextField jtfCuit;
    private javax.swing.JTextField jtfDir;
    private javax.swing.JTextField jtfTel;
    private javax.swing.JTextField jtfbuscarProveedor;
    private javax.swing.JTextField jtfnombre;
    private javax.swing.JLabel labelCuit;
    private javax.swing.JLabel labelDir;
    private javax.swing.JLabel labelLocalidad;
    private javax.swing.JLabel labelNombre;
    private javax.swing.JLabel labelTel;
    // End of variables declaration//GEN-END:variables
}
