package Venta;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import static java.awt.image.ImageObserver.WIDTH;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author SEBASTIAN
 */
public final class TipoFactura extends javax.swing.JPanel {
private ConsultaVenta consultaVenta;
private String texto = "";
private TableRowSorter<TableModel> sorter;//para ordenar la tabla
private int v=0,indiceModelo=0,bandera=0,id=0;
private boolean r = false;
private boolean e = false;
private String ntfv = "";
//private List<autor> lista;
private TipoFacturaVenta tfv;


//BANDERA 0 NUEVO
//BANDERA 1 MODIFICAR

    /**
     * Creates new form TipoFactura
     */
     public TipoFactura() {
        initComponents();
        jLabel2.setVisible(false);
        jtfnombre.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
        bandera=0;
        final String Nombre ="";
        consultaVenta = new ConsultaVenta();
        jXTaskPane2.setCollapsed(true);
        jButton1.setEnabled(false);
        busqueda();
        
          jtableAutores.addMouseListener(new MouseAdapter() 
        {
           @Override
           public void mouseClicked(MouseEvent e) 
           {
             if(e.getClickCount()== 2){
              int fila = jtableAutores.rowAtPoint(e.getPoint());
              int columna = jtableAutores.columnAtPoint(e.getPoint());
              jLabel2.setVisible(false);
              jtfnombre.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
              jButton1.setEnabled(true);
             /*El método rowAtPoint() nos devuelve -1 si pinchamos en el JTable
              pero fuera de cualquier fila*/
                     if ((fila > -1) && (columna > -1))
                     {
                       v=jtableAutores.getSelectedRow();//n° fila selccionada
                       indiceModelo = jtableAutores.convertRowIndexToModel (v);//convierte el indice de la vista en el indice del modelo 
                       jXTaskPane2.setCollapsed(false);
                       jXTaskPane2.setTitle("Modificar Tipo Factura Venta");
                       bandera=1;
                       tfv = consultaVenta.getTipoFacturaVentaPorId(getIdTipoFactura(indiceModelo));
                       ntfv = tfv.getNombreTipoFacturaVenta();
                       jtfnombre.setText(tfv.getNombreTipoFacturaVenta());
                       if(tfv.isDiscriminaIva() == true)
                       {
                           comboDiscriminaIva.setSelectedItem("Si");
                       }
                       else
                       {
                           comboDiscriminaIva.setSelectedItem("No");
                       }
                     }
             }
           }
        });
        
       cargarTabla(consultaVenta.getAllTipoFacturaVenta());
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
     
     private int getIdTipoFactura(int im){
        String[] fila= new String[1];//almaceno los valores del registro seleccionado en el string "fila"
        fila[0]=""+jtableAutores.getModel().getValueAt(im, 0);
        
        int idRep=Integer.parseInt(fila[0]);
        
        return  idRep;
    }
     
     public void cargarTabla(List<TipoFacturaVenta> tfv){
    
        String[] columnNames = {"Id","Nombre Del Tipo","Discrima IVA"};
        String discrimina;
        int[] anchos = {40,100,20};
        
        Object[][] data = new Object[tfv.size()][columnNames.length];
         
        
         for (int i = 0; i < tfv.size(); i++) {
             if(tfv.get(i).isDiscriminaIva() == true)
             {
                 discrimina = "Si";
             }
             else
             {
                 discrimina = "No";
             }
            data[i][0] = tfv.get(i).getIdTipoFacturaVenta();
            data[i][1] = tfv.get(i).getNombreTipoFacturaVenta();
            data[i][2] = discrimina;
        }
       DefaultTableModel dftm = new DefaultTableModel(data, columnNames)
                {
		//metodo para que las celdas del jtable sean no-editables	
                    @Override
			public Class getColumnClass(int columna) {

				if (columna == 1)

					return Integer.class; //Le dice al modelo que la primera columna es de tipo integer

				return String.class; //Si no, es String

			}
                        public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
       jtableAutores.setModel(dftm);
       for(int i = 0; i < jtableAutores.getColumnCount(); i++) {

        jtableAutores.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);

        }

        sorter = new TableRowSorter<TableModel>(dftm);
        jtableAutores.setRowSorter(sorter);
        //jtableAutores.getRowSorter().toggleSortOrder(0);
        jtableAutores.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    
    }
     
     private TipoFacturaVenta capturarCampos(){
         boolean discrimina;
         if(comboDiscriminaIva.getSelectedItem().toString() == "Si")
         {
             discrimina = true;
         }
         else
         {
             discrimina = false;
         }
        tfv= new TipoFacturaVenta(id,jtfnombre.getText().toUpperCase(),discrimina);
        return  tfv;
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
        jtfnombre = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        btnguardar1 = new javax.swing.JButton();
        btnCancelar1 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        comboDiscriminaIva = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();

        jtableAutores.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "ID", "TIPO DE BOLETA"
            }
        ));
        jScrollPane1.setViewportView(jtableAutores);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setText("Buscar Tipo Factura");

        jXTaskPane2.setTitle("Nuevo Tipo De Factura Venta");

        jPanel4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jtfnombre.setToolTipText("");
        jtfnombre.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtfnombreFocusLost(evt);
            }
        });
        jtfnombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtfnombreKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtfnombreKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtfnombreKeyTyped(evt);
            }
        });

        jLabel14.setText("Nombre Del Tipo De Factura Venta");

        btnguardar1.setText("Guardar");
        btnguardar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnguardar1ActionPerformed(evt);
            }
        });

        btnCancelar1.setText("Cancelar");
        btnCancelar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelar1ActionPerformed(evt);
            }
        });

        jButton1.setText("Eliminar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel15.setText("Discrimina Iva");

        comboDiscriminaIva.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Si", "No" }));
        comboDiscriminaIva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboDiscriminaIvaActionPerformed(evt);
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
                    .addComponent(jLabel2)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jtfnombre, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(27, 27, 27)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(comboDiscriminaIva, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 142, Short.MAX_VALUE)
                .addComponent(btnguardar1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCancelar1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1)
                .addGap(20, 20, 20))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addGap(1, 1, 1)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnguardar1)
                            .addComponent(btnCancelar1)
                            .addComponent(jButton1)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jtfnombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel15)
                                .addGap(1, 1, 1)
                                .addComponent(comboDiscriminaIva, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2)))
                .addGap(23, 23, 23))
        );

        jXTaskPane2.getContentPane().add(jPanel4);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jXTaskPane2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addGap(17, 17, 17))
            .addGroup(layout.createSequentialGroup()
                .addGap(161, 161, 161)
                .addComponent(jTextFieldBuscarAutor, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(22, 22, 22)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(628, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextFieldBuscarAutor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jXTaskPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLabel1)
                    .addContainerGap(298, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnguardar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnguardar1ActionPerformed
       boolean d = false;
       jLabel2.setVisible(false);
       jtfnombre.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
       if(jtfnombre.getText().isEmpty()|| (consultaVenta.existeTipoFacturaVenta(jtfnombre.getText()) != null && bandera == 0 || r == true))
       {
           if(jtfnombre.getText().isEmpty())
           {
               jtfnombre.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
               jLabel2.setText("Debe ingresar un nombre");
               jLabel2.setVisible(true);
               d = true;
           }
           if(d == false && (consultaVenta.existeTipoFacturaVenta(jtfnombre.getText()) != null && bandera == 0 || r == true))
           {
               jtfnombre.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
               jLabel2.setText("Nombre existente");
               jLabel2.setVisible(true);
           }
       }
       else
       {
        if(bandera==1){
        boolean discrimina;
        tfv = consultaVenta.getTipoFacturaVentaPorId(getIdTipoFactura(indiceModelo));
         if(comboDiscriminaIva.getSelectedItem().toString() == "Si")
         {
             discrimina = true;
         }
         else
         {
             discrimina = false;
         }
         tfv.setNombreTipoFacturaVenta(jtfnombre.getText().toUpperCase());
         tfv.setDiscriminaIva(discrimina);
       consultaVenta.modificarTipoFacturaVenta(tfv);
       cargarTabla(consultaVenta.getAllTipoFacturaVenta());
       JOptionPane.showMessageDialog(this,"Tipo De Factura Modificado");
       bandera=0;
       }
       else{
       consultaVenta.agregarTipoFacturaVenta(capturarCampos());
       JOptionPane.showMessageDialog(this,"Tipo De Factura Agregado");
       cargarTabla(consultaVenta.getAllTipoFacturaVenta());
       bandera=0; 
       }
       jXTaskPane2.setCollapsed(true);
       jXTaskPane2.setTitle("Nuevo Tipo De Factura");
       jButton1.setEnabled(false);
       comboDiscriminaIva.setSelectedIndex(0);
       jtfnombre.setText("");   
       }
       
    }//GEN-LAST:event_btnguardar1ActionPerformed

    private void btnCancelar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelar1ActionPerformed
        int respuesta=JOptionPane.showConfirmDialog(null, "¿Confirma la cancelación? \n Los datos no seran guardados","Advertencia", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            //confirmamos la eliminacion
            if(respuesta == 0)
            {
                jXTaskPane2.setCollapsed(true);
                jXTaskPane2.setTitle("Nuevo Tipo De Factura");
                jtfnombre.setText("");
                comboDiscriminaIva.setSelectedIndex(0);
                this.removeAll();
                this.repaint();
                this.revalidate();
            }
    }//GEN-LAST:event_btnCancelar1ActionPerformed

    private void comboDiscriminaIvaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboDiscriminaIvaActionPerformed

    }//GEN-LAST:event_comboDiscriminaIvaActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        v=jtableAutores.getSelectedRow();//n° fila selccionada
            indiceModelo = jtableAutores.convertRowIndexToModel (v);//convierte el indice de la vista en el indice del modelo
            tfv = consultaVenta.getTipoFacturaVentaPorId(getIdTipoFactura(indiceModelo));
            List<TipoCliente> lista = consultaVenta.getAllTipoCliente2(tfv.getIdTipoFacturaVenta());
            if(lista.isEmpty())
            {
                int respuesta=JOptionPane.showConfirmDialog(null, "¿Realmente desea quitar este tipo de factura de la lista?","Advertencia", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                //confirmamos la eliminacion
                if(respuesta == 0)
                {
                consultaVenta.eliminarTipoFacturaVenta(tfv.getIdTipoFacturaVenta());
                cargarTabla(consultaVenta.getAllTipoFacturaVenta());
                bandera=0;
                id = id + 1;
                }
                else
                {
                    cargarTabla(consultaVenta.getAllTipoFacturaVenta());
                    bandera=0;
                }
            }
            else
            {
                JOptionPane.showMessageDialog(null, "No se puede eliminar un tipo de factura asociado a un tipo de cliente", "Error", WIDTH);
                cargarTabla(consultaVenta.getAllTipoFacturaVenta());
                bandera=0;
            }
            jXTaskPane2.setCollapsed(true);
            jXTaskPane2.setTitle("Nuevo Tipo De Factura");
            jButton1.setEnabled(false);
            comboDiscriminaIva.setSelectedIndex(0);
            jtfnombre.setText("");
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jtfnombreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfnombreKeyTyped
        jLabel2.setVisible(false);
        jtfnombre.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
        char c=evt.getKeyChar();
        e = true;
        
          if(Character.isDigit(c)) {
              getToolkit().beep();
              
              evt.consume();
              
              jtfnombre.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
              jLabel2.setText("Solo puede ingresar texto");
              jLabel2.setVisible(true);
              
          }
          if(jtfnombre.getText().length() == 1) {
              getToolkit().beep();
              
              evt.consume();
              
              jtfnombre.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
              jLabel2.setText("Solo se puede ingresar una letra en este campo");
              jLabel2.setVisible(true);
              
          }
          
    }//GEN-LAST:event_jtfnombreKeyTyped

    private void jtfnombreKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfnombreKeyReleased
        
    }//GEN-LAST:event_jtfnombreKeyReleased
        
    private void jtfnombreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfnombreKeyPressed
        
    }//GEN-LAST:event_jtfnombreKeyPressed

    private void jtfnombreFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtfnombreFocusLost
        jtfnombre.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
        jLabel2.setVisible(false);
        boolean b = false;
        if(jtfnombre.getText().isEmpty())
        {
                    jtfnombre.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
                    jLabel2.setText("Debe ingresar un nombre");
                    jLabel2.setVisible(true);
                    b = true;
        }
        else
        {
            if(consultaVenta.existeTipoFacturaVenta(jtfnombre.getText()) != null && bandera == 1 && e == true)
            {  
                 r = true;
                 jtfnombre.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
                 jLabel2.setText("Nombre existente");
                 jLabel2.setVisible(true);
            }
            if(jtfnombre.getText().toUpperCase().contains(ntfv))
            {
            r = false;
            jtfnombre.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
            jLabel2.setVisible(false);
            }
        }
            if(consultaVenta.existeTipoFacturaVenta(jtfnombre.getText()) != null && bandera == 0 && b == false)
            {
                 jtfnombre.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
                 jLabel2.setText("Nombre existente");
                 jLabel2.setVisible(true);
            }
    }//GEN-LAST:event_jtfnombreFocusLost

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar1;
    private javax.swing.JButton btnguardar1;
    private javax.swing.JComboBox comboDiscriminaIva;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextFieldBuscarAutor;
    private org.jdesktop.swingx.JXTaskPane jXTaskPane2;
    private javax.swing.JTable jtableAutores;
    private javax.swing.JTextField jtfnombre;
    // End of variables declaration//GEN-END:variables
}
