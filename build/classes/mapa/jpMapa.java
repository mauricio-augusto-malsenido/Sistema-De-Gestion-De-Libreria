package mapa;
import Compra.ConsultaCompra;
import Empleados.Empleado;
import Empleados.ConsultaEmpleados;
import Venta.Cliente;
import Compra.proveedor;
import Venta.ConsultaVenta;
import java.awt.Color;
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
import libreria.Libro;
import libreria.autor;
import libreria.editorial;
import libreria.genero;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
/**
 *
 * @author SEBASTIAN
 */
public final class jpMapa extends javax.swing.JPanel {
private ConsultaMapa oConsultaMapa;
private ConsultaCompra oConsultaCompra;
private ConsultaEmpleados oConsultaEmpleado;
private ConsultaVenta oConsultaCliente;
private TableRowSorter<TableModel> sorter;//para ordenar la tabla
private int v=0,indiceModelo=0,bandera=0,id=0,control;
private provincia pProvincia, p;
private localidad pLocalidad, lo;
private String etiquetaDesplegable;
private List <localidad> loc;
private List <Empleado> l1;
private List <Cliente> l2;
private List <proveedor> l3;
private boolean r = false, m = false;
private boolean controlT;
    /**
     * Creates new form jpMapa
     */
//CONTROL 0 PROVINCIA
//CONTROL 1 LOCALIDAD
//BANDERA 0 NUEVO
//BANDERA 1 MODIFICAR
    public jpMapa(int b) {
        initComponents();
        control=b;
        oConsultaMapa = new ConsultaMapa();
        oConsultaCompra = new ConsultaCompra();
        oConsultaEmpleado = new ConsultaEmpleados();
        oConsultaCliente = new ConsultaVenta();
        bandera=0;
        busqueda();
        labelNombre.setVisible(false);
        jButton3.setVisible(false);
        if(control == 0)
        {
            jXTaskPane2.setTitle("Nueva Provincia");
        }
        else
        {
            jXTaskPane2.setTitle("Nueva Localidad");
        }
        jXTaskPane2.setCollapsed(true);
        
          jtableMapa.addMouseListener(new MouseAdapter() 
        {
           @Override
           public void mouseClicked(MouseEvent e) 
           {
             if(e.getClickCount()== 2){
              int fila = jtableMapa.rowAtPoint(e.getPoint());
              int columna = jtableMapa.columnAtPoint(e.getPoint());
             /*El método rowAtPoint() nos devuelve -1 si pinchamos en el JTable
              pero fuera de cualquier fila*/
              
                     if ((fila > -1) && (columna > -1))
                     {
                       v=jtableMapa.getSelectedRow();//n° fila selccionada
                       indiceModelo = jtableMapa.convertRowIndexToModel (v);//convierte el indice de la vista en el indice del modelo 
                       
                       jXTaskPane2.setTitle("Modificar  "+etiquetaDesplegable);
                       jXTaskPane2.setCollapsed(false);
                       labelNombre.setVisible(false);
                       jtfnombre.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
                       bandera=1;
                       jButton3.setVisible(true);
                       jtfbuscarProvincia.setEnabled(false);
                       jtfbuscarProvincia.setEditable(false);
                       jtableMapa.setEnabled(false);
                        if(control==0){
                       cargarCamposP(oConsultaMapa.getProvinciaPorId(getId(indiceModelo)));
                       
                       p = oConsultaMapa.getProvinciaPorId(getId(indiceModelo));
                        loc = oConsultaMapa.getAllLocalidad();
                        boolean f = false;
                        for(int i=0;i<loc.size();i++)
                       {
                           if(loc.get(i).getIdProvincia() == p.getIdProvincia())
                           {
                               f = true;
                           }
                       }
                       if(f == true)
                       {
                           jButton3.setEnabled(false);
                       }
                       else
                       {
                           jButton3.setEnabled(true);
                       }
                        } 
                       if(control==1){
                       cargarCamposL(oConsultaMapa.getLocalidadPorId(getId(indiceModelo)));
                       lo = oConsultaMapa.getLocalidadPorId(getId(indiceModelo));
                       id = lo.getIdLocalidad();
                        l1 = oConsultaEmpleado.getAllEmpleados();
                        l2 = oConsultaCliente.getAllCliente();
                        l3 = oConsultaCompra.getAllProveedor();
                        boolean f = false;
                        for(int i=0;i<l1.size();i++)
                       {
                           if(l1.get(i).getIdLocalidad() == lo.getIdLocalidad())
                           {
                               f = true;
                           }
                       }
                        for(int i=0;i<l2.size();i++)
                       {
                           if(l2.get(i).getIdLocalidad() == lo.getIdLocalidad())
                           {
                               f = true;
                           }
                       }
                        for(int i=0;i<l3.size();i++)
                       {
                           if(l3.get(i).getIdLocalidad() == lo.getIdLocalidad())
                           {
                               f = true;
                           }
                       }
                       if(f == true)
                       {
                           jButton3.setEnabled(false);
                       }
                       else
                       {
                           jButton3.setEnabled(true);
                       }
                       }
                       }
             }
           }
        });
        
        if(control==0)
        {
        jlPROV.setVisible(false);
        jlEtiqueta.setText("BUSCAR PROVINCIA");
        jcbProvincia.setVisible(false);
        etiquetaDesplegable="Provincia";
        cargarTablaP(oConsultaMapa.getAllProvincia());
        
        
        }
        else{
            jlPROV.setVisible(true);
            jlEtiqueta.setText("BUSCAR LOCALIDAD");
            jcbProvincia.setVisible(true);
            etiquetaDesplegable="localidad";
            CargarComboProvincia();
            AutoCompleteDecorator.decorate(this.jcbProvincia);
            cargarTablaL(oConsultaMapa.getAllLocalidad());
        }
        
    }
        private void busqueda(){
     
     jtfbuscarProvincia.getDocument().addDocumentListener(
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
            
            rf = RowFilter.regexFilter(jtfbuscarProvincia.getText().toUpperCase(), indiceColumnaTabla);
        } catch (java.util.regex.PatternSyntaxException e) {
        }
        sorter.setRowFilter(rf);
    }
     
     public void cargarTablaP(List<provincia> l){
    
        String[] columnNames = {"Id","Provincia"};
        int[] anchos = {40,150};
        
        Object[][] data = new Object[l.size()][columnNames.length];
         
       for (int i = 0; i < l.size(); i++) {
        
            data[i][0] = l.get(i).getIdProvincia();
            data[i][1] = l.get(i).getNombreProvincia();
        }
       DefaultTableModel dftm = new DefaultTableModel(data, columnNames)
                {
		//metodo para que las celdas del jtable sean no-editables	
                    @Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
       jtableMapa.setModel(dftm);
       for(int i = 0; i < jtableMapa.getColumnCount(); i++) {

        jtableMapa.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);

        }
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        for(int i=0;i<jtableMapa.getColumnCount();i++)
        {
            jtableMapa.getColumnModel().getColumn(i).setCellRenderer(tcr);
        }
        sorter = new TableRowSorter<TableModel>(dftm);
        jtableMapa.setRowSorter(sorter);
        jtableMapa.getRowSorter().toggleSortOrder(0);
        jtableMapa.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        jtfnombre.setText("");
       
    }
     
     public void cargarTablaL(List<localidad> l){
    
        String[] columnNames = {"Id","Localidad","Provincia"};
        int[] anchos = {40,150,150};
        
        Object[][] data = new Object[l.size()][columnNames.length];
         
       for (int i = 0; i < l.size(); i++) {
        String nombrProv=oConsultaMapa.getProvinciaPorId(l.get(i).getIdProvincia()).getNombreProvincia();
            data[i][0] = l.get(i).getIdLocalidad();
            data[i][1] = l.get(i).getNombreLocalidad();
            data[i][2] = nombrProv;
        }
       DefaultTableModel dftm = new DefaultTableModel(data, columnNames)
                {
		//metodo para que las celdas del jtable sean no-editables	
                    @Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
       jtableMapa.setModel(dftm);
       for(int i = 0; i < jtableMapa.getColumnCount(); i++) {

        jtableMapa.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);

        }
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        for(int i=0;i<jtableMapa.getColumnCount();i++)
        {
            jtableMapa.getColumnModel().getColumn(i).setCellRenderer(tcr);
        }
        sorter = new TableRowSorter<TableModel>(dftm);
        jtableMapa.setRowSorter(sorter);
        jtableMapa.getRowSorter().toggleSortOrder(0);
        jtableMapa.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        jtfnombre.setText("");
       
    }
     
     private int getId(int im){
        String[] fila= new String[1];//almaceno los valores del registro seleccionado en el string "fila"
        fila[0]=""+jtableMapa.getModel().getValueAt(im, 0);
        
        int idRep=Integer.parseInt(fila[0]);
        
        return  idRep;
    }
     
     private  void cargarCamposP(provincia pProvincia){
         
        id=pProvincia.getIdProvincia();
        jtfnombre.setText(pProvincia.getNombreProvincia());
     
    }
    
     private  void cargarCamposL(localidad pLocalidad){
       pProvincia=oConsultaMapa.getProvinciaPorId(pLocalidad.getIdProvincia());
       id=pLocalidad.getIdProvincia();
       jtfnombre.setText(pLocalidad.getNombreLocalidad());
       jcbProvincia.setSelectedItem(pProvincia);
    }
     
     private provincia capturarCamposP(){
        
        pProvincia= new provincia(
                id,
                jtfnombre.getText().toUpperCase()
                );
                
        
        return  pProvincia;
        }
     
     private localidad capturarCamposL(){
        provincia pr= (provincia) jcbProvincia.getSelectedItem();
        
        pLocalidad= new localidad(
                id,
                jtfnombre.getText().toUpperCase(),
                pr.getIdProvincia()
                );
                
        
        return  pLocalidad;
        }
     
     private void CargarComboProvincia(){
        
     List<provincia> lista = oConsultaMapa.getAllProvincia();

        
        for (int i = 0; i < lista.size(); i++) {
       
            provincia pr=new provincia(lista.get(i).getIdProvincia(), lista.get(i).getNombreProvincia());
            jcbProvincia.addItem(pr);
            provincia objeto=(provincia) jcbProvincia.getItemAt(1);

        }
        
	
    }
     public boolean comprobarExistencia(){
         localidad loca = capturarCamposL();
         boolean u = false;
         for(int i=0;i<oConsultaMapa.getAllLocalidad().size();i++)
         {
             String nombre = oConsultaMapa.getAllLocalidad().get(i).getNombreLocalidad();
             provincia tel = oConsultaMapa.getProvinciaPorId(oConsultaMapa.getAllLocalidad().get(i).getIdProvincia());
             if(loca.getNombreLocalidad().contentEquals(nombre) && loca.getIdProvincia() == tel.getIdProvincia()){
                 u = true;
             }
         }
         if((bandera == 1) && (loca.getNombreLocalidad().contentEquals(lo.getNombreLocalidad()) && loca.getIdProvincia() == lo.getIdProvincia())){
                 u = false;
             }
         return u;
     }
       public boolean comprobarNulos(){
                   if(jtfnombre.getText().equals(""))
                    {
                    labelNombre.setVisible(true);
                    labelNombre.setText("Debe ingresar un nombre");
                    jtfnombre.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
                    controlT=true;
                    }
                    else{
                    controlT=false;
                    }
                    
                    return controlT;
    }
    
       @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jtfbuscar = new javax.swing.JTextField();
        rbCod = new javax.swing.JRadioButton();
        rbDesc = new javax.swing.JRadioButton();
        rbMedida = new javax.swing.JRadioButton();
        jButton1 = new javax.swing.JButton();
        jpBusqueda = new javax.swing.JPanel();
        jlEtiqueta = new javax.swing.JLabel();
        jtfbuscarProvincia = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtableMapa = new javax.swing.JTable();
        jXTaskPane2 = new org.jdesktop.swingx.JXTaskPane();
        jPanel4 = new javax.swing.JPanel();
        jtfnombre = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        btnguardar1 = new javax.swing.JButton();
        btnCancelar1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jcbProvincia = new org.jdesktop.swingx.JXComboBox();
        jlPROV = new javax.swing.JLabel();
        labelNombre = new javax.swing.JLabel();

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setText("Buscar");

        rbCod.setSelected(true);
        rbCod.setText("Codigo");

        rbDesc.setText("Descripcion");

        rbMedida.setText("Medida");

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/actualizar.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(rbCod)
                .addGap(18, 18, 18)
                .addComponent(rbDesc)
                .addGap(18, 18, 18)
                .addComponent(rbMedida)
                .addGap(18, 18, 18)
                .addComponent(jtfbuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jtfbuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rbCod)
                    .addComponent(rbDesc)
                    .addComponent(rbMedida)
                    .addComponent(jButton1))
                .addGap(13, 13, 13))
        );

        jpBusqueda.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jlEtiqueta.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jlEtiqueta.setText("Buscar Provincia");

        javax.swing.GroupLayout jpBusquedaLayout = new javax.swing.GroupLayout(jpBusqueda);
        jpBusqueda.setLayout(jpBusquedaLayout);
        jpBusquedaLayout.setHorizontalGroup(
            jpBusquedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpBusquedaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlEtiqueta, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jtfbuscarProvincia, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jpBusquedaLayout.setVerticalGroup(
            jpBusquedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpBusquedaLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jpBusquedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlEtiqueta)
                    .addComponent(jtfbuscarProvincia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17))
        );

        jtableMapa.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Id", "Provincia"
            }
        ));
        jScrollPane1.setViewportView(jtableMapa);

        jXTaskPane2.setTitle("Nuevo");

        jPanel4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jtfnombre.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtfnombreFocusLost(evt);
            }
        });
        jtfnombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtfnombreKeyTyped(evt);
            }
        });

        jLabel14.setText("NOMBRE");

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

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/eliminar_1.png"))); // NOI18N
        jButton3.setText("Eliminar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jlPROV.setText("PROVINCIA");

        labelNombre.setForeground(new java.awt.Color(204, 0, 0));
        labelNombre.setText("jLabel2");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jlPROV)
                        .addGap(501, 501, 501))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(btnguardar1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancelar1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton3)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(labelNombre, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(61, 61, 61))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jtfnombre)
                                .addGap(18, 18, 18)))
                        .addComponent(jcbProvincia, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(298, 298, 298))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(jlPROV))
                .addGap(6, 6, 6)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtfnombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcbProvincia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(2, 2, 2)
                .addComponent(labelNombre)
                .addGap(2, 2, 2)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnguardar1)
                    .addComponent(btnCancelar1)
                    .addComponent(jButton3))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jXTaskPane2.getContentPane().add(jPanel4);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jXTaskPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 740, Short.MAX_VALUE)
                    .addComponent(jpBusqueda, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jpBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jXTaskPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnguardar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnguardar1ActionPerformed
        
        switch(control)
        {
            case 0:
                if(comprobarNulos()==false && m == false){
                    if(r == false){
                    if(bandera==1){
                    oConsultaMapa.modificarProvincia(capturarCamposP());
                    cargarTablaP(oConsultaMapa.getAllProvincia());
                    JOptionPane.showMessageDialog(this,"Provincia Modificada");
                    bandera=0;
                    }
                    else{
                        oConsultaMapa.agregarProvincia(capturarCamposP());
                        cargarTablaP(oConsultaMapa.getAllProvincia());
                        JOptionPane.showMessageDialog(this,"Provincia Agregada");
                        bandera=0;
                    }
                    jXTaskPane2.setTitle("Nueva Provincia");
                    jXTaskPane2.setCollapsed(true);
                    jButton3.setVisible(false);
                    jtfbuscarProvincia.setEnabled(true);
                    jtfbuscarProvincia.setEditable(true);
                    jtableMapa.setEnabled(true);
                    jtfnombre.setText("");
                    jtfnombre.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
                    labelNombre.setVisible(false);
                    }
                    else{
                        if(r == true)
                        {
                        jtfnombre.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
                        labelNombre.setText("Nombre existente");
                        labelNombre.setVisible(true);
                        }
                    }
                    break;     
                }
            case 1:
                if(comprobarNulos()==false){
                    if((control == 1) && (comprobarExistencia() == false)){
                    if(bandera==1){
                    oConsultaMapa.modificarLocalidad(capturarCamposL());
                    cargarTablaL(oConsultaMapa.getAllLocalidad());
                    JOptionPane.showMessageDialog(this,"Localidad Modificada");
                    bandera=0;
                }
                else{
                    oConsultaMapa.agregarLocalidad(capturarCamposL());
                    cargarTablaL(oConsultaMapa.getAllLocalidad());
                    JOptionPane.showMessageDialog(this,"Localidad Agregada");
                    bandera=0;
                }
                    jXTaskPane2.setTitle("Nueva Localidad");
                    jXTaskPane2.setCollapsed(true);
                    jButton3.setVisible(false);
                    jtfbuscarProvincia.setEnabled(true);
                    jtfbuscarProvincia.setEditable(true);
                    jcbProvincia.setSelectedIndex(0);
                    jtableMapa.setEnabled(true);
                    jtfnombre.setText("");
                    jtfnombre.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
                    labelNombre.setVisible(false);
                    }
                    else{
                        if((control == 1) && (comprobarExistencia() == true))
                        {
                        JOptionPane.showMessageDialog(null, "La localidad que desea guardar ya existe", "Error", WIDTH);
                        }
                    }
                    break;
                
                }
        
           }
    }//GEN-LAST:event_btnguardar1ActionPerformed

    private void btnCancelar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelar1ActionPerformed
    int respuesta=JOptionPane.showConfirmDialog(null, "¿Confirma la cancelación? \n Los datos no seran guardados","Advertencia", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            //confirmamos la eliminacion
            if(respuesta == 0)
            {
                jtfnombre.setText("");
                labelNombre.setVisible(false);
                jtfnombre.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
                if (control==0)
                {cargarTablaP(oConsultaMapa.getAllProvincia());
                jXTaskPane2.setTitle("Nueva Provincia");
                bandera=0;}
                else
                {cargarTablaL(oConsultaMapa.getAllLocalidad());
                jXTaskPane2.setTitle("Nueva Localidad");
                jcbProvincia.setSelectedIndex(0);
                bandera=0;}
                jXTaskPane2.setCollapsed(true);
                jButton3.setEnabled(false);
                jtableMapa.setEnabled(true);
                
                jtfbuscarProvincia.setEnabled(true);
                jtfbuscarProvincia.setEditable(true);
                r = false;
            }
    }//GEN-LAST:event_btnCancelar1ActionPerformed

    private void jtfnombreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfnombreKeyTyped
        int longitud = jtfnombre.getText().length();
        boolean ñ = false;
            if(controlT!=false){
           labelNombre.setVisible(false);
           jtfnombre.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
           controlT=false;
            }
            if(longitud >= 44)
            {evt.consume();getToolkit().beep(); labelNombre.setText("Longitud maxima 45 caracteres");labelNombre.setVisible(true); ñ = true;}
            else{labelNombre.setVisible(false);ñ = false;}
        char car = evt.getKeyChar();
        if(m==false){
        if(Character.isDigit(car) && control == 0){
                getToolkit().beep();
                evt.consume();
                labelNombre.setVisible(true);
                labelNombre.setText("En este campo solo se deben ingresar letras");
                jtfnombre.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
                m = true;
                }
                else{
                labelNombre.setVisible(false);
                jtfnombre.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
                m = false;
                }
        }
        else
        {
            
                jtfnombre.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
            
        }
    }//GEN-LAST:event_jtfnombreKeyTyped

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        if (control==0){
        int respuesta=JOptionPane.showConfirmDialog(null, "¿Realmente desea eliminar esta provincia?","Advertencia", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if(respuesta == 0)
        {
          provincia g=capturarCamposP();
          oConsultaMapa.eliminarProvincia(g.getIdProvincia());
          cargarTablaP(oConsultaMapa.getAllProvincia());
          jXTaskPane2.setTitle("Nueva Provincia");
          jXTaskPane2.setCollapsed(true);
          jtfnombre.setText("");
          jButton3.setVisible(false);
          jtableMapa.setEnabled(true);
          jtfbuscarProvincia.setEnabled(true);
          jtfbuscarProvincia.setEditable(true);
          
          bandera=0;
          labelNombre.setVisible(false);}
          jtfnombre.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
          r = false;
        }
        else{
           int respuesta=JOptionPane.showConfirmDialog(null, "¿Realmente desea eliminar esta localidad?","Advertencia", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if(respuesta == 0)
        {
          localidad g=capturarCamposL();
          oConsultaMapa.eliminarLocalidad(g.getIdLocalidad());
          cargarTablaL(oConsultaMapa.getAllLocalidad());
          jXTaskPane2.setTitle("Nueva Localidad");
          jXTaskPane2.setCollapsed(true);
          jtfnombre.setText("");
          jButton3.setVisible(false);
          jtableMapa.setEnabled(true);
          jtfbuscarProvincia.setEnabled(true);
          jtfbuscarProvincia.setEditable(true);
          bandera=0;
          labelNombre.setVisible(false);
          jcbProvincia.setSelectedIndex(0);
          jtfnombre.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
          r = false;
        } 
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jtfnombreFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtfnombreFocusLost
        jtfnombre.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
        labelNombre.setVisible(false);
        String y = jtfnombre.getText();
        System.out.println("holllllaaaaa "+y);
        boolean b = false;
        r = false;
        if(jtfnombre.getText().isEmpty())
        {
                    jtfnombre.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
                    labelNombre.setText("Debe ingresar un nombre");
                    labelNombre.setVisible(true);
                    b = true;
        }
        else
        {
            if(control == 0){
            for(int i=0;i<oConsultaMapa.getAllProvincia().size();i++)
            {
                if(oConsultaMapa.getAllProvincia().get(i).getNombreProvincia().contentEquals(jtfnombre.getText().toUpperCase()) && bandera == 0)
                {
                    jtfnombre.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
                    labelNombre.setText("Nombre existente");
                    labelNombre.setVisible(true);
                    r = true;
                }
                else
                {
                    if(oConsultaMapa.getAllProvincia().get(i).getNombreProvincia().contentEquals(y.toUpperCase()) && bandera == 1)
                    {
                    jtfnombre.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
                    labelNombre.setText("Nombre existente");
                    labelNombre.setVisible(true);
                    r = true;
                    }
                }
            }
        
            if((control == 0) && (bandera == 1) && ((jtfnombre.getText().toUpperCase().contentEquals(p.getNombreProvincia()))))
                {
                    labelNombre.setVisible(false);
                    jtfnombre.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
                    r = false;
                }
            }
        }
    }//GEN-LAST:event_jtfnombreFocusLost

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar1;
    private javax.swing.JButton btnguardar1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private org.jdesktop.swingx.JXTaskPane jXTaskPane2;
    private org.jdesktop.swingx.JXComboBox jcbProvincia;
    private javax.swing.JLabel jlEtiqueta;
    private javax.swing.JLabel jlPROV;
    private javax.swing.JPanel jpBusqueda;
    private javax.swing.JTable jtableMapa;
    private javax.swing.JTextField jtfbuscar;
    private javax.swing.JTextField jtfbuscarProvincia;
    private javax.swing.JTextField jtfnombre;
    private javax.swing.JLabel labelNombre;
    private javax.swing.JRadioButton rbCod;
    private javax.swing.JRadioButton rbDesc;
    private javax.swing.JRadioButton rbMedida;
    // End of variables declaration//GEN-END:variables
}
