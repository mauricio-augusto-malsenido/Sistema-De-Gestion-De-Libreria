package Empleados;

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
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;


/**
 *
 * @author SEBASTIAN
 */
public final class jpEstadoCivil extends javax.swing.JPanel {
private ConsultaEmpleados oConsultaLibreria;
private TableRowSorter<TableModel> sorter;//para ordenar la tabla
private int v=0,indiceModelo=0,bandera=0,id=0;
public String s;
private boolean r = false;
private List<Empleado> lista;
private EstadoCivilEmpleado pEstadoCivilEmpleado;


//BANDERA 0 NUEVO
//BANDERA 1 MODIFICAR

    /**
     * Creates new form jpEstadoCivilEmpleado
     */
     public jpEstadoCivil() {
        initComponents();
        bandera=0;
        final String Nombre ="";
        oConsultaLibreria = new ConsultaEmpleados();
        jXTaskPane2.setCollapsed(true);
        jBElim.setVisible(false);
        jLabel2.setVisible(false);
        busqueda();
        
          jtableEstadoCivil.addMouseListener(new MouseAdapter() 
        {
           @Override
           public void mouseClicked(MouseEvent e) 
           {
             if(e.getClickCount()== 2){
              int fila = jtableEstadoCivil.rowAtPoint(e.getPoint());
              int columna = jtableEstadoCivil.columnAtPoint(e.getPoint());
             /*El método rowAtPoint() nos devuelve -1 si pinchamos en el JTable
              pero fuera de cualquier fila*/
              //System.out.println("Indice seleccionado : " + indiceModelo);
                     if ((fila > -1) && (columna > -1))
                     {
                       indiceModelo = (Integer) jtableEstadoCivil.getValueAt(jtableEstadoCivil.getSelectedRow(), 0);
                       id = indiceModelo;
                       jXTaskPane2.setTitle("Modificar Estado Civil");
                       jXTaskPane2.setCollapsed(false);
                       jTextFieldBuscarAutor.setEnabled(false);
                       jTextFieldBuscarAutor.setEditable(false);
                       jLabel2.setVisible(false);
                       jtfnombre.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
                       jBElim.setVisible(true);
                       jtableEstadoCivil.setEnabled(false);
                       bandera=1;
                       jtfnombre.setText(""+(String) jtableEstadoCivil.getValueAt(jtableEstadoCivil.getSelectedRow(), 1));
                       s = jtfnombre.getText();
                       lista = oConsultaLibreria.getAllEmpleados();
                       boolean f = false;
                       EstadoCivilEmpleado a = oConsultaLibreria.getEstadoCivilEmpleadoPorId(getIdEstadoCivil(indiceModelo));
                       for(int i=0;i<lista.size();i++)
                       {
                           if(lista.get(i).getIdEstadoCivilEmpleado() == a.getIdEstadoCivilEmpleado())
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
        
       cargarTabla(oConsultaLibreria.getAllEstadoCivilEmpleado());
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
     
     public void cargarTabla(List<EstadoCivilEmpleado> l){
    
        String[] columnNames = {"Id","Estado Civil"};
        int[] anchos = {40,100};
        
        Object[][] data = new Object[l.size()][columnNames.length];
         
        
         for (int i = 0; i < l.size(); i++) {
            data[i][0] = l.get(i).getIdEstadoCivilEmpleado();
            data[i][1] = l.get(i).getNombreEstadoCivilEmpleado();
        }
       DefaultTableModel dftm = new DefaultTableModel(data, columnNames)
                {
		//metodo para que las celdas del jtable sean no-editables	
                    @Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
       jtableEstadoCivil.setModel(dftm);
       for(int i = 0; i < jtableEstadoCivil.getColumnCount(); i++) {

        jtableEstadoCivil.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);

        }
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        for(int i=0;i<jtableEstadoCivil.getColumnCount();i++)
        {
            jtableEstadoCivil.getColumnModel().getColumn(i).setCellRenderer(tcr);
        }
        sorter = new TableRowSorter<TableModel>(dftm);
        jtableEstadoCivil.setRowSorter(sorter);
        jtableEstadoCivil.getRowSorter().toggleSortOrder(0);
        jtableEstadoCivil.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    
    }
     
     private int getIdEstadoCivil(int im){
        String[] fila= new String[1];//almaceno los valores del registro seleccionado en el string "fila"
        fila[0]=""+jtableEstadoCivil.getModel().getValueAt(im, 0);
        
        int idRep=Integer.parseInt(fila[0]);
        
        return  idRep;
    }
     

     private EstadoCivilEmpleado capturarCampos(){
        pEstadoCivilEmpleado= new EstadoCivilEmpleado(id,jtfnombre.getText().toUpperCase());
        return  pEstadoCivilEmpleado;
        }
     
     
   
  
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jtableEstadoCivil = new javax.swing.JTable();
        jTextFieldBuscarAutor = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jXTaskPane2 = new org.jdesktop.swingx.JXTaskPane();
        jPanel4 = new javax.swing.JPanel();
        jtfnombre = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        btnguardar2 = new javax.swing.JButton();
        btnCancelar2 = new javax.swing.JButton();
        jBElim = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        jtableEstadoCivil.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jtableEstadoCivil);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setText("Buscar Estado Civil");

        jXTaskPane2.setTitle("Nuevo Estado Civil");

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

        jLabel14.setText("ESTADO CIVIL");

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

        jLabel2.setForeground(new java.awt.Color(255, 0, 0));
        jLabel2.setText("jLabel2");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jtfnombre, javax.swing.GroupLayout.DEFAULT_SIZE, 266, Short.MAX_VALUE)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(18, 18, 18)
                        .addComponent(btnguardar2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnCancelar2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jBElim)
                        .addGap(107, 107, 107))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel14)
                .addGap(1, 1, 1)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnguardar2)
                        .addComponent(btnCancelar2)
                        .addComponent(jBElim))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jtfnombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)))
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
                    .addComponent(jScrollPane1))
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(22, 22, 22)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jTextFieldBuscarAutor, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(370, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jXTaskPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(jTextFieldBuscarAutor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addContainerGap(278, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnguardar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnguardar2ActionPerformed
       jtfnombre.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
       jLabel2.setVisible(false);
       if(jtfnombre.getText().isEmpty() == false && r == false){
       if(bandera==1){
       oConsultaLibreria.modificarEstadoCivilEmpleado(capturarCampos());
       cargarTabla(oConsultaLibreria.getAllEstadoCivilEmpleado());
       JOptionPane.showMessageDialog(this,"Estado Civil Modificado");
       bandera=0;
       }
       else{
       oConsultaLibreria.agregarEstadoCivilEmpleado(capturarCampos());
       JOptionPane.showMessageDialog(this,"Estado Civil Agregado");
       cargarTabla(oConsultaLibreria.getAllEstadoCivilEmpleado());
       bandera=0; 
       }
       jXTaskPane2.setCollapsed(true);
       jXTaskPane2.setTitle("Nuevo Estado Civil");
       jtfnombre.setText("");
       jBElim.setVisible(false);
       jTextFieldBuscarAutor.setEnabled(true);
       jTextFieldBuscarAutor.setEditable(true);
       jtableEstadoCivil.setEnabled(true);
       }
       else
       {
                if(r == true)
                {
                    jtfnombre.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
                    jLabel2.setText("Nombre existente");
                    jLabel2.setVisible(true);
                }
                else
                {
                    jtfnombre.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
                    jLabel2.setText("Debe ingresar un nombre");
                    jLabel2.setVisible(true);
                }
       }
    }//GEN-LAST:event_btnguardar2ActionPerformed

    private void btnCancelar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelar2ActionPerformed
       int respuesta=JOptionPane.showConfirmDialog(null, "¿Confirma la cancelación? \n Los datos no seran guardados","Advertencia", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            //confirmamos la eliminacion
            if(respuesta == 0)
            {
                jtfnombre.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
                jLabel2.setVisible(false);
                jXTaskPane2.setCollapsed(true);
                jXTaskPane2.setTitle("Nuevo Estado Civil");
                jtfnombre.setText("");
                jTextFieldBuscarAutor.setEnabled(true);
                jTextFieldBuscarAutor.setEditable(true);
                jtableEstadoCivil.setEnabled(true);
                 jBElim.setVisible(false);
                cargarTabla(oConsultaLibreria.getAllEstadoCivilEmpleado());
                r = false;
                bandera = 0;
            }
    }//GEN-LAST:event_btnCancelar2ActionPerformed

    private void jBElimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBElimActionPerformed
       v=jtableEstadoCivil.getSelectedRow();//n° fila selccionada
            indiceModelo = jtableEstadoCivil.convertRowIndexToModel (v);//convierte el indice de la vista en el indice del modelo
            pEstadoCivilEmpleado = oConsultaLibreria.getEstadoCivilEmpleadoPorId(getIdEstadoCivil(indiceModelo));
            int respuesta=JOptionPane.showConfirmDialog(null, "¿Realmente desea quitar este estado civil de la lista?","Advertencia", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            //confirmamos la eliminacion
            if(respuesta == 0)
            {
                oConsultaLibreria.eliminarEstadoCivil(pEstadoCivilEmpleado.getIdEstadoCivilEmpleado());
                cargarTabla(oConsultaLibreria.getAllEstadoCivilEmpleado());
                jXTaskPane2.setCollapsed(true);
                jXTaskPane2.setTitle("Nuevo Tipo De Usuario");
                jtfnombre.setText("");
                jTextFieldBuscarAutor.setEnabled(true);
                jTextFieldBuscarAutor.setEditable(true);
                jtableEstadoCivil.setEnabled(true);
                 jBElim.setVisible(false);
                 bandera = 0;
                r = false;
                jtfnombre.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
                jLabel2.setVisible(false);
            }
    }//GEN-LAST:event_jBElimActionPerformed

    private void jtfnombreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfnombreKeyTyped
        jLabel2.setVisible(false);
        jtfnombre.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
        char c=evt.getKeyChar();
        
          if(Character.isDigit(c)) {
              getToolkit().beep();
              evt.consume();
              
              jtfnombre.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
              jLabel2.setText("Solo puede ingresar texto");
              jLabel2.setVisible(true);
              
          }
          if(jtfnombre.getText().length() == 45) {
              getToolkit().beep();
              
              evt.consume();
              
              jtfnombre.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
              jLabel2.setText("Solo puede ingresar hasta 45 caracteres");
              jLabel2.setVisible(true);
              
          }
    }//GEN-LAST:event_jtfnombreKeyTyped

    private void jtfnombreFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtfnombreFocusLost
        jtfnombre.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
        jLabel2.setVisible(false);
        String y = jtfnombre.getText();
        System.out.println("holllllaaaaa "+y);
        boolean b = false;
        r = false;
        if(jtfnombre.getText().isEmpty())
        {
                    jtfnombre.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
                    jLabel2.setText("Debe ingresar un nombre");
                    jLabel2.setVisible(true);
                    b = true;
        }
        else
        {
            for(int i=0;i<oConsultaLibreria.getAllEstadoCivilEmpleado().size();i++)
            {
                if(oConsultaLibreria.getAllEstadoCivilEmpleado().get(i).getNombreEstadoCivilEmpleado().contentEquals(jtfnombre.getText().toUpperCase()) && bandera == 0)
                {
                    jtfnombre.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
                    jLabel2.setText("Nombre existente");
                    jLabel2.setVisible(true);
                    r = true;
                }
                else
                {
                    if(oConsultaLibreria.getAllEstadoCivilEmpleado().get(i).getNombreEstadoCivilEmpleado().contentEquals(y.toUpperCase()) && bandera == 1)
                    {
                    System.out.println("HOLLLAAAAA "+s);
                    jtfnombre.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
                    jLabel2.setText("Nombre existente");
                    jLabel2.setVisible(true);
                    r = true;
                    }
                }
            }
        }
            if((jtfnombre.getText().toUpperCase().contentEquals(s)) && bandera == 1)
                {
                    jLabel2.setVisible(false);
                    jtfnombre.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
                    r = false;
                }
    }//GEN-LAST:event_jtfnombreFocusLost

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar2;
    private javax.swing.JButton btnguardar2;
    private javax.swing.JButton jBElim;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextFieldBuscarAutor;
    private org.jdesktop.swingx.JXTaskPane jXTaskPane2;
    private javax.swing.JTable jtableEstadoCivil;
    private javax.swing.JTextField jtfnombre;
    // End of variables declaration//GEN-END:variables
}
