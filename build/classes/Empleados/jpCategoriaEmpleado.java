package Empleados;

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
public final class jpCategoriaEmpleado extends javax.swing.JPanel {
private ConsultaEmpleados oConsultaLibreria;
private TableRowSorter<TableModel> sorter;//para ordenar la tabla
private int v=0,indiceModelo=0,bandera=0,id=0;
public String s;
private boolean r = false;
private List<Empleado> lista;
private CategoriaEmpleado pCategoriaEmpleado;


//BANDERA 0 NUEVO
//BANDERA 1 MODIFICAR

    /**
     * Creates new form jpCategoriaEmpleado
     */
     public jpCategoriaEmpleado() {
        initComponents();
        bandera=0;
        final String Nombre ="";
        oConsultaLibreria = new ConsultaEmpleados();
        busqueda();
        jXTaskPane2.setCollapsed(true);
        jBElim.setVisible(false);
        labeCategorias.setVisible(false);
        jtfCategoria.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
        labelSueldoBasico.setVisible(false);
        jtSueldoBasico.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
        
          jtableAutores.addMouseListener(new MouseAdapter() 
        {
           @Override
           public void mouseClicked(MouseEvent e) 
           {
             if(e.getClickCount()== 2){
              int fila = jtableAutores.rowAtPoint(e.getPoint());
              int columna = jtableAutores.columnAtPoint(e.getPoint());
             /*El método rowAtPoint() nos devuelve -1 si pinchamos en el JTable
              pero fuera de cualquier fila*/
              //System.out.println("Indice seleccionado : " + indiceModelo);
                     if ((fila > -1) && (columna > -1))
                     {
                       //v=jtableAutores.getSelectedRow();//n° fila selccionada
                       //indiceModelo = jtableAutores.convertRowIndexToModel(v);//convierte el indice de la vista en el indice del modelo 
                       indiceModelo = (Integer) jtableAutores.getValueAt(jtableAutores.getSelectedRow(), 0);
                       id = indiceModelo;
                       jXTaskPane2.setTitle("Modificar Categoria de Empleado");
                       jXTaskPane2.setCollapsed(false);
                       jTextFieldBuscarAutor.setEnabled(false);
                       jTextFieldBuscarAutor.setEditable(false);
                       labelSueldoBasico.setVisible(false);
                       labeCategorias.setVisible(false);
                       jBElim.setVisible(true);
                       jtableAutores.setEnabled(false);
                       jtSueldoBasico.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
                       jtfCategoria.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
                       bandera=1;
                       jtfCategoria.setText(""+(String) jtableAutores.getValueAt(jtableAutores.getSelectedRow(), 1));
                       
                       for(int i=0; i<oConsultaLibreria.getAllCategoriaEmpleado().size();i++)
                       {
                           if(oConsultaLibreria.getAllCategoriaEmpleado().get(i).getNombreCategoriaEmpleado().contentEquals(jtfCategoria.getText()))
                           {
                               pCategoriaEmpleado = oConsultaLibreria.getAllCategoriaEmpleado().get(i);
                           }
                       }
                       s = pCategoriaEmpleado.getNombreCategoriaEmpleado();
                       jtSueldoBasico.setText(""+pCategoriaEmpleado.getSueldoBasicoCategoria());
                       lista = oConsultaLibreria.getAllEmpleados();
                       boolean f = false;
                       for(int i=0;i<lista.size();i++)
                       {
                           if(lista.get(i).getIdCategoriaEmpleado() == pCategoriaEmpleado.getIdCategoriaEmpleado())
                           {
                               f = true;
                           }
                       }
                       if(f == true)
                       {
                           jBElim.setEnabled(false);
                       }
                       else
                       {
                           jBElim.setEnabled(true);
                       }
                     }
             }
           }
        });
        
       cargarTabla(oConsultaLibreria.getAllCategoriaEmpleado());
    }
    
     private void busqueda(){
     
     jTextFieldBuscarAutor.getDocument().addDocumentListener(
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
            
            rf = RowFilter.regexFilter(jTextFieldBuscarAutor.getText().toUpperCase(), indiceColumnaTabla);
        } catch (java.util.regex.PatternSyntaxException e) {
        }
        sorter.setRowFilter(rf);
    }
     
     public void cargarTabla(List<CategoriaEmpleado> l){
    
        String[] columnNames = {"Id","Categoria de Empleado","Sueldo Basico"};
        int[] anchos = {20,80,40};
        
        Object[][] data = new Object[l.size()][columnNames.length];
         
        
         for (int i = 0; i < l.size(); i++) {
            data[i][0] = l.get(i).getIdCategoriaEmpleado();
            data[i][1] = l.get(i).getNombreCategoriaEmpleado();
            data[i][2] = "$"+l.get(i).getSueldoBasicoCategoria();
        }
       DefaultTableModel dftm = new DefaultTableModel(data, columnNames)
                {
		//metodo para que las celdas del jtable sean no-editables	
                    @Override
			
                        public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
       jtableAutores.setModel(dftm);
       for(int i = 0; i < jtableAutores.getColumnCount(); i++) {

        jtableAutores.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);

        }
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        for(int i=0;i<jtableAutores.getColumnCount();i++)
        {
            jtableAutores.getColumnModel().getColumn(i).setCellRenderer(tcr);
        }
        sorter = new TableRowSorter<TableModel>(dftm);
        jtableAutores.setRowSorter(sorter);
        jtableAutores.getRowSorter().toggleSortOrder(0);
        jtableAutores.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    
    }
     
     private int getIdAutor(int im){
        String[] fila= new String[1];//almaceno los valores del registro seleccionado en el string "fila"
        fila[0]=""+jtableAutores.getModel().getValueAt(im, 0);
        
        int idRep=Integer.parseInt(fila[0]);
        
        return  idRep;
    }
     
     private  void cargarCampos(CategoriaEmpleado parentezco){
    }
     
     private CategoriaEmpleado capturarCampos(){
        pCategoriaEmpleado= new CategoriaEmpleado(id,jtfCategoria.getText().toUpperCase(),Float.parseFloat(jtSueldoBasico.getText()));
        return  pCategoriaEmpleado;
        }
     
     
   
  
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jtableAutores = new javax.swing.JTable();
        jTextFieldBuscarAutor = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jXTaskPane2 = new org.jdesktop.swingx.JXTaskPane();
        jPanel4 = new javax.swing.JPanel();
        jtfCategoria = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jtSueldoBasico = new javax.swing.JTextField();
        jLSueldoBasico = new javax.swing.JLabel();
        labelSueldoBasico = new javax.swing.JLabel();
        btnguardar2 = new javax.swing.JButton();
        btnCancelar2 = new javax.swing.JButton();
        jBElim = new javax.swing.JButton();
        labeCategorias = new javax.swing.JLabel();

        jtableAutores.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "idAutor", "Nombre"
            }
        ));
        jScrollPane1.setViewportView(jtableAutores);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setText("Buscar Categoria");

        jXTaskPane2.setTitle("Nueva Categoria de Empleado");

        jPanel4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jtfCategoria.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtfCategoriaFocusLost(evt);
            }
        });
        jtfCategoria.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtfCategoriaKeyTyped(evt);
            }
        });

        jLabel14.setText("CATEGORIA");

        jtSueldoBasico.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtSueldoBasicoFocusLost(evt);
            }
        });
        jtSueldoBasico.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtSueldoBasicoKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtSueldoBasicoKeyTyped(evt);
            }
        });

        jLSueldoBasico.setText("SUELDO BASICO");

        labelSueldoBasico.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        labelSueldoBasico.setForeground(new java.awt.Color(204, 51, 0));
        labelSueldoBasico.setText("jLabel5");

        btnguardar2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/guardar.png"))); // NOI18N
        btnguardar2.setText("Guardar");
        btnguardar2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnguardar2ActionPerformed(evt);
            }
        });

        btnCancelar2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/cancelar.png"))); // NOI18N
        btnCancelar2.setText("Cancelar");
        btnCancelar2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelar2ActionPerformed(evt);
            }
        });

        jBElim.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/eliminar_1.png"))); // NOI18N
        jBElim.setText("Eliminar");
        jBElim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBElimActionPerformed(evt);
            }
        });

        labeCategorias.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        labeCategorias.setForeground(new java.awt.Color(204, 51, 0));
        labeCategorias.setText("jLabel5");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtfCategoria, javax.swing.GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE)
                    .addComponent(labeCategorias, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(10, 10, 10)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLSueldoBasico)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(labelSueldoBasico, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                                .addComponent(jtSueldoBasico, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnguardar2)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancelar2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jBElim)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(jLSueldoBasico)
                        .addGap(1, 1, 1)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jtSueldoBasico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnguardar2)
                            .addComponent(jtfCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnCancelar2)
                            .addComponent(jBElim))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(labelSueldoBasico)
                            .addComponent(labeCategorias))
                        .addContainerGap())))
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
                    .addComponent(jScrollPane1))
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(22, 22, 22)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jTextFieldBuscarAutor, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(394, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jXTaskPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(jTextFieldBuscarAutor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addContainerGap(284, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jtSueldoBasicoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtSueldoBasicoKeyPressed
        // TODO add your handling code here:
        char car = evt.getKeyChar();
        
        boolean controletiqueta=false,controlcaracter=true;
           if(controlcaracter!=false){
                labelSueldoBasico.setVisible(false);
                jtSueldoBasico.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
                controlcaracter=false;
            }
            if(controletiqueta!=true){
           
                if((car<'0' || car>'9')&& car!=KeyEvent.VK_BACK_SPACE && car!=KeyEvent.VK_ENTER && car!='.' && car!=','){
                labelSueldoBasico.setVisible(true);
                labelSueldoBasico.setText("Solo se deben ingresar numeros");
                jtSueldoBasico.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
                controletiqueta=true;
                }
                else{
                labelSueldoBasico.setVisible(false);
                jtSueldoBasico.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
                controletiqueta=false;
                }
            }
        
    }//GEN-LAST:event_jtSueldoBasicoKeyPressed
    private void blanquearLabels(){
            labeCategorias.setVisible(false);
            labelSueldoBasico.setVisible(false);
            jtfCategoria.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
            jtSueldoBasico.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
    }
    
    private void btnguardar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnguardar2ActionPerformed
        // TODO add your handling code here:
       blanquearLabels();
       if(jtfCategoria.getText().isEmpty() == false && jtSueldoBasico.getText().isEmpty() == false && r == false){
       if(bandera==1){
       oConsultaLibreria.modificarCategoriaEmpleado(capturarCampos());
       cargarTabla(oConsultaLibreria.getAllCategoriaEmpleado());
       JOptionPane.showMessageDialog(this,"Categoria de Empleado Modificado");
       bandera=0;
       }
       else{
       oConsultaLibreria.agregarCategoriaEmpleado(capturarCampos());
       JOptionPane.showMessageDialog(this,"Categoria de Empleado Agregado");
       cargarTabla(oConsultaLibreria.getAllCategoriaEmpleado());
       bandera=0; 
       }
       jXTaskPane2.setCollapsed(true);
       jXTaskPane2.setTitle("Nueva Categoria de Empleado");
       jtfCategoria.setText("");
       jtSueldoBasico.setText("");
       jBElim.setVisible(false);
       jtableAutores.setEnabled(true);
       jTextFieldBuscarAutor.setEnabled(true);
       jTextFieldBuscarAutor.setEditable(true);
       labeCategorias.setVisible(false);
       jtfCategoria.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
       labelSueldoBasico.setVisible(false);
       jtSueldoBasico.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
       }
       else
       {
                if(r == true)
                {
                    jtfCategoria.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
                    labeCategorias.setText("Nombre existente");
                    labeCategorias.setVisible(true);
                }
                else
                {
                    if(jtSueldoBasico.getText().isEmpty())
                    {
                    jtfCategoria.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
                    labelSueldoBasico.setText("Debe ingresar un sueldo básico");
                    labelSueldoBasico.setVisible(true);
                    }
                    else{
                    jtfCategoria.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
                    labeCategorias.setText("Debe ingresar un nombre");
                    labeCategorias.setVisible(true);}
                }
       }

    }//GEN-LAST:event_btnguardar2ActionPerformed

    private void btnCancelar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelar2ActionPerformed
       int respuesta=JOptionPane.showConfirmDialog(null, "¿Confirma la cancelación? \n Los datos no seran guardados","Advertencia", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            //confirmamos la eliminacion
            if(respuesta == 0)
            {
                jXTaskPane2.setCollapsed(true);
                jXTaskPane2.setTitle("Nueva Categoria de Empleado");
                jtfCategoria.setText("");
                jtSueldoBasico.setText("");
                jTextFieldBuscarAutor.setEnabled(true);
                jTextFieldBuscarAutor.setEditable(true);
                jtableAutores.setEnabled(true);
                jBElim.setVisible(false);
                cargarTabla(oConsultaLibreria.getAllCategoriaEmpleado());
                labeCategorias.setVisible(false);
                jtfCategoria.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
                labelSueldoBasico.setVisible(false);
                jtSueldoBasico.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
                r = false;
                bandera = 0;
            }
       
    }//GEN-LAST:event_btnCancelar2ActionPerformed

    private void jBElimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBElimActionPerformed
        v=jtableAutores.getSelectedRow();//n° fila selccionada
        CategoriaEmpleado tfv = new CategoriaEmpleado();
            indiceModelo = jtableAutores.convertRowIndexToModel (v);//convierte el indice de la vista en el indice del modelo
            tfv = oConsultaLibreria.getCategoriaEmpleadoPorId(getIdAutor(indiceModelo));
            int respuesta=JOptionPane.showConfirmDialog(null, "¿Realmente desea quitar esta categoria de empleado de la lista?","Advertencia", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            //confirmamos la eliminacion
            if(respuesta == 0)
            {
                oConsultaLibreria.eliminarCategoriaEmpleado(tfv.getIdCategoriaEmpleado());
                cargarTabla(oConsultaLibreria.getAllCategoriaEmpleado());
                jXTaskPane2.setCollapsed(true);
                jXTaskPane2.setTitle("Nueva Categoria de Empleado");
                jtfCategoria.setText("");
                jtSueldoBasico.setText("");
                jBElim.setVisible(false);
                jTextFieldBuscarAutor.setEnabled(true);
                jTextFieldBuscarAutor.setEditable(true);
                jtableAutores.setEnabled(true);
                r = false;
                bandera = 0;
                blanquearLabels();
            }
            
    }//GEN-LAST:event_jBElimActionPerformed

    private void jtfCategoriaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfCategoriaKeyTyped
        // TODO add your handling code here:
        labeCategorias.setVisible(false);
        jtfCategoria.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
        char c=evt.getKeyChar();
        
          if(Character.isDigit(c)) {
              getToolkit().beep();
              evt.consume();
              
              jtfCategoria.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
              labeCategorias.setText("Solo puede ingresar texto");
              labeCategorias.setVisible(true);
              
          }
        if(jtfCategoria.getText().length() == 20) {
              getToolkit().beep();
              evt.consume();
              jtfCategoria.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
              labeCategorias.setText("Longitud maxima 20 caracteres");
              labeCategorias.setVisible(true);
          }
    }//GEN-LAST:event_jtfCategoriaKeyTyped

    private void jtSueldoBasicoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtSueldoBasicoKeyTyped
        // TODO add your handling code here:
        int longitud=0,longitud2=0;        
        char car = evt.getKeyChar();
        boolean controletiqueta=false,controlcaracter=true;
        longitud=jtSueldoBasico.getText().length();
           if(controlcaracter!=false){
                labelSueldoBasico.setVisible(false);
                jtSueldoBasico.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
                controlcaracter=false;
            }
            if(controletiqueta!=true){
           
                if((car<'0' || car>'9')&& car!=KeyEvent.VK_BACK_SPACE && car!=KeyEvent.VK_ENTER && car!='.' && car!=','){
                labelSueldoBasico.setVisible(true);
                labelSueldoBasico.setText("Solo se deben ingresar numeros");
                jtSueldoBasico.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
                controletiqueta=true;
                longitud2=jtSueldoBasico.getText().length();
                getToolkit().beep();
                evt.consume();
                }
                else{
                    labelSueldoBasico.setVisible(false);
                jtSueldoBasico.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
                controletiqueta=false;
                }
            }
            else
            {
                labeCategorias.setVisible(false);
                jtfCategoria.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
                labelSueldoBasico.setVisible(false);
                jtSueldoBasico.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
            }

    }//GEN-LAST:event_jtSueldoBasicoKeyTyped

    private void jtfCategoriaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtfCategoriaFocusLost
        jtfCategoria.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
        labeCategorias.setVisible(false);
        String y = jtfCategoria.getText();
        System.out.println("holllllaaaaa "+y);
        boolean b = false;
        r = false;
        if(jtfCategoria.getText().isEmpty())
        {
                    jtfCategoria.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
                    labeCategorias.setText("Debe ingresar un nombre");
                    labeCategorias.setVisible(true);
                    b = true;
        }
        else
        {
            for(int i=0;i<oConsultaLibreria.getAllCategoriaEmpleado().size();i++)
            {
                if(oConsultaLibreria.getAllCategoriaEmpleado().get(i).getNombreCategoriaEmpleado().contentEquals(jtfCategoria.getText().toUpperCase()) && bandera == 0)
                {
                    jtfCategoria.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
                    labeCategorias.setText("Nombre existente");
                    labeCategorias.setVisible(true);
                    r = true;
                }
                else
                {
                    if(oConsultaLibreria.getAllCategoriaEmpleado().get(i).getNombreCategoriaEmpleado().contentEquals(y.toUpperCase()) && bandera == 1)
                    {
                    System.out.println("HOLLLAAAAA "+s);
                    jtfCategoria.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
                    labeCategorias.setText("Nombre existente");
                    labeCategorias.setVisible(true);
                    r = true;
                    }
                }
            }
        }
            if((jtfCategoria.getText().toUpperCase().contentEquals(s)) && bandera == 1)
                {
                    labeCategorias.setVisible(false);
                    jtfCategoria.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
                    r = false;
                }
    }//GEN-LAST:event_jtfCategoriaFocusLost

    private void jtSueldoBasicoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtSueldoBasicoFocusLost
        labelSueldoBasico.setVisible(false);
        jtSueldoBasico.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
        if(jtSueldoBasico.getText().isEmpty())
        {
                    jtSueldoBasico.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
                    labelSueldoBasico.setText("Debe ingresar un sueldo básico");
                    labelSueldoBasico.setVisible(true);
        }
    }//GEN-LAST:event_jtSueldoBasicoFocusLost

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar2;
    private javax.swing.JButton btnguardar2;
    private javax.swing.JButton jBElim;
    private javax.swing.JLabel jLSueldoBasico;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextFieldBuscarAutor;
    private org.jdesktop.swingx.JXTaskPane jXTaskPane2;
    private javax.swing.JTextField jtSueldoBasico;
    private javax.swing.JTable jtableAutores;
    private javax.swing.JTextField jtfCategoria;
    private javax.swing.JLabel labeCategorias;
    private javax.swing.JLabel labelSueldoBasico;
    // End of variables declaration//GEN-END:variables
}
