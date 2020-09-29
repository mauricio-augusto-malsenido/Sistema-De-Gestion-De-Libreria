package libreria;

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

/**
 *
 * @author SEBASTIAN
 */
public final class jpEditorial extends javax.swing.JPanel {
    
private consultasLibreria oConsultaLibreria;
private TableRowSorter<TableModel> sorter;//para ordenar la tabla
private int v=0,indiceModelo=0,bandera=0,id,longitud,longitud2;
private editorial pEditorial, pe;
private boolean r = false;
private boolean controLabel,controlN,controlT,controlD,estadoEditorial;
    
    public jpEditorial() {
        initComponents();
        bandera=0;
        oConsultaLibreria = new consultasLibreria();
        busqueda();
        labelEdit.setVisible(false);
        labelDir.setVisible(false);
        labelTel.setVisible(false);
        jXTaskPane2.setCollapsed(true);
        btnDeshabilitar.setEnabled(false);
        
           jtableEditoriales.addMouseListener(new MouseAdapter() 
        {
           @Override
           public void mouseClicked(MouseEvent e) 
           {
             if(e.getClickCount()== 2){
              int fila = jtableEditoriales.rowAtPoint(e.getPoint());
              int columna = jtableEditoriales.columnAtPoint(e.getPoint());
             /*El método rowAtPoint() nos devuelve -1 si pinchamos en el JTable
              pero fuera de cualquier fila*/
              
                     if ((fila > -1) && (columna > -1))
                     {
                       v=jtableEditoriales.getSelectedRow();//n° fila selccionada
                       indiceModelo = jtableEditoriales.convertRowIndexToModel (v);//convierte el indice de la vista en el indice del modelo 
                       jXTaskPane2.setCollapsed(false);
                       jXTaskPane2.setTitle("Modificar Autor");
                       bandera=1;
                       cargarCampos(oConsultaLibreria.getEditorialPorId(getIdEditorial(indiceModelo)));
                       pe = oConsultaLibreria.getEditorialPorId(getIdEditorial(indiceModelo));
                       btnDeshabilitar.setEnabled(true);
                       jtfBuscarEditorial.setEnabled(false);
                       jtfBuscarEditorial.setEditable(false);
                       labelTel.setVisible(false);
                       jtfTel.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
                       labelDir.setVisible(false);
                       jtfdir.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
                       labelEdit.setVisible(false);
                       jtfnombre.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
                       jtableEditoriales.setEnabled(false);
                     }
             }
           }
        });
        
       cargarTabla(oConsultaLibreria.getAllEditorial());
        
    }
    
    private void busqueda(){
     
     jtfBuscarEditorial.getDocument().addDocumentListener(
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
            
            rf = RowFilter.regexFilter(jtfBuscarEditorial.getText().toUpperCase(), indiceColumnaTabla);
        } catch (java.util.regex.PatternSyntaxException e) {
        }
        sorter.setRowFilter(rf);
    }
     
    public void cargarTabla(List<editorial> l){
    
        String[] columnNames = {"Id","Nombre_Editorial","Telefono","Direccion","Estado"};
        int[] anchos = {5,100,50,150,20};
        
        Object[][] data = new Object[l.size()][columnNames.length];
         
        
         for (int i = 0; i < l.size(); i++) {
            data[i][0] = l.get(i).getIdEditorial();
            data[i][1] = l.get(i).getNombreEditorial();
            data[i][2] = l.get(i).getTelefonoEditorial();
            data[i][3] = l.get(i).getDireccionEditorial();
            if(l.get(i).getEstadoEditorial()==false)
            data[i][4] = "DESHABILITADO";
            else data[i][4] = "HABILITADO";
        }
       DefaultTableModel dftm = new DefaultTableModel(data, columnNames)
                {
		//metodo para que las celdas del jtable sean no-editables	
                    @Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
       jtableEditoriales.setModel(dftm);
       for(int i = 0; i < jtableEditoriales.getColumnCount(); i++) {

        jtableEditoriales.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);

        }
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        for(int i=0;i<jtableEditoriales.getColumnCount();i++)
        {
            jtableEditoriales.getColumnModel().getColumn(i).setCellRenderer(tcr);
        }
        sorter = new TableRowSorter<TableModel>(dftm);
        jtableEditoriales.setRowSorter(sorter);
        jtableEditoriales.getRowSorter().toggleSortOrder(0);
        jtableEditoriales.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    
    }
     
    private int getIdEditorial(int im){
        String[] fila= new String[1];//almaceno los valores del registro seleccionado en el string "fila"
        fila[0]=""+jtableEditoriales.getModel().getValueAt(im, 0);
        
        int idRep=Integer.parseInt(fila[0]);
        
        return  idRep;
    }
     
    private  void cargarCampos(editorial Edi){
        id=Edi.getIdEditorial();
        estadoEditorial=Edi.getEstadoEditorial();
        jtfnombre.setText(""+Edi.getNombreEditorial());
        jtfTel.setText(Edi.getTelefonoEditorial());
        jtfdir.setText(Edi.getDireccionEditorial());
        if(estadoEditorial==false)btnDeshabilitar.setText("Habilitar");
        else btnDeshabilitar.setText("Deshabilitar");
    }
     
    private editorial capturarCampos(){
    pEditorial= new editorial(id,jtfnombre.getText().toUpperCase(),jtfTel.getText(),jtfdir.getText().toUpperCase(),estadoEditorial);
    return  pEditorial;
    }
     public boolean comprobarExistencia(){
         editorial ed = capturarCampos();
         for(int i=0;i<oConsultaLibreria.getAllEditorial().size();i++)
         {
             String nombre = oConsultaLibreria.getAllEditorial().get(i).getNombreEditorial();
             String tel = oConsultaLibreria.getAllEditorial().get(i).getTelefonoEditorial();
             String dir = oConsultaLibreria.getAllEditorial().get(i).getDireccionEditorial();
             if(ed.getNombreEditorial().contentEquals(nombre) && ed.getTelefonoEditorial().contentEquals(tel) && ed.getDireccionEditorial().contentEquals(dir)){
                 r = true;
             }
         }
         if((bandera == 1) && (ed.getNombreEditorial().contentEquals(pe.getNombreEditorial()) && ed.getTelefonoEditorial().contentEquals(pe.getTelefonoEditorial()) && ed.getDireccionEditorial().contentEquals(pe.getDireccionEditorial()))){
                 r = false;
             }
         return r;
     }
     public boolean comprobarNulosD(){
                   if(jtfdir.getText().equals(""))
                    {controlD=true;
                    labelDir.setVisible(true);
                    labelDir.setText("Debe ingresar un dirección");
                    jtfdir.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
                    }
                    else{
                    controlD=false;
                    }
                    
                    return controlD;
    }
     public boolean comprobarNulosT(){
                    if(jtfTel.getText().equals(""))
                    {controlT=true;
                    labelTel.setVisible(true);
                    labelTel.setText("Debe ingresar un número de telefono");
                    jtfTel.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
                    }
                    else{
                    controlT=false;
                    }
                    
                    return controlT;
    }
     public boolean comprobarNulosN(){
                if(jtfnombre.getText().equals(""))
                    {controlN=true;
                    labelEdit.setVisible(true);
                    labelEdit.setText("Debe ingresar un nombre");
                    jtfnombre.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
                    }
                    else{
                    controlN=false;
                    }
                    
                    return controlN;
    }
     
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jtfBuscarEditorial = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtableEditoriales = new javax.swing.JTable();
        jXTaskPane2 = new org.jdesktop.swingx.JXTaskPane();
        jPanel4 = new javax.swing.JPanel();
        jtfnombre = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        btnguardar1 = new javax.swing.JButton();
        btnCancelar1 = new javax.swing.JButton();
        btnDeshabilitar = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jtfTel = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jtfdir = new javax.swing.JTextField();
        labelEdit = new javax.swing.JLabel();
        labelTel = new javax.swing.JLabel();
        labelDir = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setText("Buscar Editorial");

        jtableEditoriales.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "id", "Nombre Editorial", "Telefono", "Direccion"
            }
        ));
        jScrollPane1.setViewportView(jtableEditoriales);

        jXTaskPane2.setTitle("Nuevo Genero");

        jPanel4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jtfnombre.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(0, 0, 0), null));
        jtfnombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtfnombreKeyTyped(evt);
            }
        });

        jLabel14.setText("NOMBRE EDITORIAL **");

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

        btnDeshabilitar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/eliminar_1.png"))); // NOI18N
        btnDeshabilitar.setText("Deshabilitar");
        btnDeshabilitar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeshabilitarActionPerformed(evt);
            }
        });

        jLabel2.setText("TELEFONO **");

        jtfTel.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(0, 0, 0), null));
        jtfTel.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtfTelKeyTyped(evt);
            }
        });

        jLabel3.setText("DRIRECCION **");

        jtfdir.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(0, 0, 0), null));
        jtfdir.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtfdirKeyTyped(evt);
            }
        });

        labelEdit.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        labelEdit.setForeground(new java.awt.Color(204, 0, 51));
        labelEdit.setText("jLabel4");

        labelTel.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        labelTel.setForeground(new java.awt.Color(204, 0, 0));
        labelTel.setText("jLabel4");

        labelDir.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        labelDir.setForeground(new java.awt.Color(204, 0, 0));
        labelDir.setText("jLabel4");

        jLabel4.setText("** Campos obligatorios");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jtfnombre)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jtfdir, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(labelEdit)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(86, 86, 86)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jtfTel, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(labelTel)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(131, 131, 131))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(labelDir)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(btnguardar1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnCancelar1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnDeshabilitar))
                            .addComponent(jLabel4))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(jLabel2))
                .addGap(2, 2, 2)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtfnombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtfTel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(2, 2, 2)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelEdit)
                    .addComponent(labelTel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jtfdir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelDir)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnguardar1)
                    .addComponent(btnCancelar1)
                    .addComponent(btnDeshabilitar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jXTaskPane2.getContentPane().add(jPanel4);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jXTaskPane2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jtfBuscarEditorial, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jtfBuscarEditorial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jXTaskPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnguardar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnguardar1ActionPerformed
    if( comprobarNulosN()== false && comprobarNulosD()== false 
             && comprobarNulosT()== false &&
             controLabel!=true ){
       if(comprobarExistencia() == false){
        if(bandera==1){
       oConsultaLibreria.modificarEditorial(capturarCampos());
       cargarTabla(oConsultaLibreria.getAllEditorial());
       JOptionPane.showMessageDialog(this,"Editorial Modificado");
       bandera=0;
       id=0;
       estadoEditorial=false;
       btnDeshabilitar.setEnabled(false);
       jtableEditoriales.setEnabled(true);
       }
       else{
       oConsultaLibreria.agregarEditorial(capturarCampos());
       JOptionPane.showMessageDialog(this,"Editorial Agregado");
       cargarTabla(oConsultaLibreria.getAllEditorial());
       bandera=0; 
       }
       jXTaskPane2.setTitle("Nuevo Editorial");
       jXTaskPane2.setCollapsed(true);
       jtfnombre.setText("");
       jtfTel.setText("");
       jtfdir.setText("");
       labelTel.setVisible(false);
       jtfBuscarEditorial.setEnabled(true);
       jtfBuscarEditorial.setEditable(true);
           jtfTel.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
               labelDir.setVisible(false);
           jtfdir.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
            labelEdit.setVisible(false);
           jtfnombre.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
       }
       else
       {
                if(comprobarExistencia() == true)
                {
                    JOptionPane.showMessageDialog(null, "La editorial que desea guardar ya existe", "Error", WIDTH);
                }
       }
       }
    
             
    }//GEN-LAST:event_btnguardar1ActionPerformed

    private void btnCancelar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelar1ActionPerformed
       int respuesta=JOptionPane.showConfirmDialog(null, "¿Confirma la cancelación? \n Los datos no seran guardados","Advertencia", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            //confirmamos la eliminacion
            if(respuesta == 0)
            {
                jXTaskPane2.setTitle("Nuevo Editorial");
                jXTaskPane2.setCollapsed(true);
                jtfnombre.setText("");
                jtfTel.setText("");
                jtfdir.setText("");
                labelEdit.setVisible(false);
                jtfnombre.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
                labelTel.setVisible(false);
                jtfTel.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
                controlT=false;
                labelDir.setVisible(false);
                jtfdir.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
                controLabel=false;
                cargarTabla(oConsultaLibreria.getAllEditorial());
                jtableEditoriales.setEnabled(true);
                btnDeshabilitar.setEnabled(false);
                jtfBuscarEditorial.setEnabled(true);
                jtfBuscarEditorial.setEditable(true);
                bandera=0;
                id=0;
                estadoEditorial=false;
                r = false;
            }
    }//GEN-LAST:event_btnCancelar1ActionPerformed

    private void btnDeshabilitarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeshabilitarActionPerformed
        editorial pro=capturarCampos();
        if(pro.getEstadoEditorial()==true){
        int respuesta=JOptionPane.showConfirmDialog(null, "¿Realmente desea deshabilitar esta editorial?","Advertencia", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if(respuesta == 0)
        {
        pro.setEstadoEditorial(false);
        oConsultaLibreria.deshabilitarEditorial(pro);
        jXTaskPane2.setTitle("Nuevo Editorial");
        jXTaskPane2.setCollapsed(true);
       jtfnombre.setText("");
       jtfTel.setText("");
       jtfdir.setText("");
       labelEdit.setVisible(false);
       jtfnombre.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
       labelTel.setVisible(false);
       jtfTel.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
       controlT=false;
       labelDir.setVisible(false);
       jtfdir.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
       controLabel=false;
       cargarTabla(oConsultaLibreria.getAllEditorial());
       jtableEditoriales.setEnabled(true);
       jtfBuscarEditorial.setEnabled(true);
       jtfBuscarEditorial.setEditable(true);
       btnDeshabilitar.setEnabled(false);
       bandera=0;
       id=0;
       estadoEditorial=false;
       r = false;
       btnDeshabilitar.setText("Deshabilitar");
        }}
        else{
        int respuesta2=JOptionPane.showConfirmDialog(null, "¿Realmente desea habilitar esta editorial?","Advertencia", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if(respuesta2 == 0)
        {
        pro.setEstadoEditorial(true);
        oConsultaLibreria.deshabilitarEditorial(pro);
        jXTaskPane2.setTitle("Nuevo Editorial");
        jXTaskPane2.setCollapsed(true);
       jtfnombre.setText("");
       jtfTel.setText("");
       jtfdir.setText("");
       labelEdit.setVisible(false);
       jtfnombre.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
       labelTel.setVisible(false);
       jtfTel.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
       controlT=false;
       labelDir.setVisible(false);
       jtfdir.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
       controLabel=false;
       cargarTabla(oConsultaLibreria.getAllEditorial());
       jtableEditoriales.setEnabled(true);
       jtfBuscarEditorial.setEnabled(true);
       jtfBuscarEditorial.setEditable(true);
       btnDeshabilitar.setEnabled(false);
       bandera=0;
       id=0;
       estadoEditorial=false;
       r = false;
       btnDeshabilitar.setText("Deshabilitar");
        }}    
        
    }//GEN-LAST:event_btnDeshabilitarActionPerformed

    private void jtfnombreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfnombreKeyTyped
       char car = evt.getKeyChar();
       longitud=jtfnombre.getText().length();
       
            if(controlN!=false){
           labelEdit.setVisible(false);
           jtfnombre.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
           controlN=false;
            }
            if(longitud > 44){
                evt.consume();
                labelEdit.setVisible(true);
                labelEdit.setText("Alcanzo la longitud maxima de 45 caracteres");
            }
        
    }//GEN-LAST:event_jtfnombreKeyTyped

    private void jtfTelKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfTelKeyTyped
    char car = evt.getKeyChar();
    longitud=jtfTel.getText().length();
        
           if(controlT!=false){
           labelTel.setVisible(false);
           jtfTel.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
           controlT=false;
           }
               
            if(longitud < 13 && controLabel!=true){
           
            if(Character.isDigit(car) == false && car != '\b' && car != ' ' && car != '-'){
                getToolkit().beep();
                evt.consume();
                labelTel.setVisible(true);
                labelTel.setText("Solo se deben ingresar números");
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
            if(longitud > 12){
                getToolkit().beep();
                evt.consume();
                labelTel.setVisible(true);
                labelTel.setText("Alcanzo la longitud maxima de 13 caracteres");
            }
            
    }//GEN-LAST:event_jtfTelKeyTyped

    private void jtfdirKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfdirKeyTyped
     char car = evt.getKeyChar();
     longitud=jtfdir.getText().length();   
            if(controlD!=false){
           labelDir.setVisible(false);
           jtfdir.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
           controlD=false;
            }
            if(longitud > 44){
                evt.consume();
                labelDir.setVisible(true);
                labelDir.setText("Alcanzo la longitud maxima de 45 caracteres");
            }else   labelDir.setVisible(false);
                
    }//GEN-LAST:event_jtfdirKeyTyped

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar1;
    private javax.swing.JButton btnDeshabilitar;
    private javax.swing.JButton btnguardar1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private org.jdesktop.swingx.JXTaskPane jXTaskPane2;
    private javax.swing.JTable jtableEditoriales;
    private javax.swing.JTextField jtfBuscarEditorial;
    private javax.swing.JTextField jtfTel;
    private javax.swing.JTextField jtfdir;
    private javax.swing.JTextField jtfnombre;
    private javax.swing.JLabel labelDir;
    private javax.swing.JLabel labelEdit;
    private javax.swing.JLabel labelTel;
    // End of variables declaration//GEN-END:variables
}
