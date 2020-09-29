package Venta;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
public final class TipoCli extends javax.swing.JPanel {
private ConsultaVenta consultaVenta;
private TableRowSorter<TableModel> sorter;//para ordenar la tabla
private int v=0,indiceModelo=0,bandera=0,id=0;
private boolean r = false;
private boolean e = false;
private String ntc = "";
//private List<autor> lista;
private TipoCliente tc;


//BANDERA 0 NUEVO
//BANDERA 1 MODIFICAR

    /**
     * Creates new form TipoFactura
     */
     public TipoCli() {
        initComponents();
        jLabel2.setVisible(false);
        jLabel3.setVisible(false);
        jtfnombre.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
        bandera=0;
        final String Nombre ="";
        consultaVenta = new ConsultaVenta();
        jXTaskPane2.setCollapsed(true);
        jButton1.setEnabled(false);
        cargarComboTipoFacturaVenta();
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
              jLabel3.setVisible(false);
              jtfnombre.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
              jButton1.setEnabled(true);
             /*El método rowAtPoint() nos devuelve -1 si pinchamos en el JTable
              pero fuera de cualquier fila*/
                     if ((fila > -1) && (columna > -1))
                     {
                       v=jtableAutores.getSelectedRow();//n° fila selccionada
                       indiceModelo = jtableAutores.convertRowIndexToModel (v);//convierte el indice de la vista en el indice del modelo 
                       jXTaskPane2.setCollapsed(false);
                       jXTaskPane2.setTitle("Modificar Tipo De Cliente");
                       bandera=1;
                       tc = consultaVenta.getTipoClientePorId(getIdTipoCliente(indiceModelo));
                       ntc = tc.getNombreTipoCliente();
                       jtfnombre.setText(tc.getNombreTipoCliente());
                       TipoFacturaVenta tfv = consultaVenta.getTipoFacturaVentaPorId(tc.isIdTipoFacturaVenta());
                       jcbtfv.setSelectedItem(tfv);
                     }
             }
           }
        });
        
       cargarTabla(consultaVenta.getAllTipoCliente());
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
     
     private int getIdTipoCliente(int im){
        String[] fila= new String[1];//almaceno los valores del registro seleccionado en el string "fila"
        fila[0]=""+jtableAutores.getModel().getValueAt(im, 0);
        
        int idRep=Integer.parseInt(fila[0]);
        
        return  idRep;
    }
     
     public void cargarTabla(List<TipoCliente> tc){
    
        String[] columnNames = {"Id","Nombre Del Tipo","Tipo De Factura"};
        int[] anchos = {40,100,20};
        
        Object[][] data = new Object[tc.size()][columnNames.length];
         
        
         for (int i = 0; i < tc.size(); i++) {
            TipoFacturaVenta tfv = consultaVenta.getTipoFacturaVentaPorId(tc.get(i).isIdTipoFacturaVenta());
            data[i][0] = tc.get(i).getIdTipoCliente();
            data[i][1] = tc.get(i).getNombreTipoCliente();
            data[i][2] = tfv.getNombreTipoFacturaVenta();
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
     
     private TipoCliente capturarCampos(){
        TipoFacturaVenta tfv= (TipoFacturaVenta) jcbtfv.getSelectedItem();
        tc= new TipoCliente(id,jtfnombre.getText().toUpperCase(),tfv.getIdTipoFacturaVenta());
        return  tc;
        }
     
     private void cargarComboTipoFacturaVenta(){
        
     List<TipoFacturaVenta> lista = consultaVenta.getAllTipoFacturaVenta();

        if(lista.isEmpty())
        {
            jLabel3.setText("Ningún tipo de factura creado o seleccionado");
            jLabel3.setVisible(true);
            jtfnombre.setEditable(false);
            jtfnombre.setEnabled(false);
        }
        else
        {
            for (int i = 0; i < lista.size(); i++) {
       
            TipoFacturaVenta tfv=new TipoFacturaVenta(lista.get(i).getIdTipoFacturaVenta(), lista.get(i).getNombreTipoFacturaVenta(), lista.get(i).isDiscriminaIva());
            jcbtfv.addItem(tfv);
            TipoFacturaVenta objeto=(TipoFacturaVenta) jcbtfv.getItemAt(1);

            }
        }
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
        jcbtfv = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

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
        jLabel1.setText("Buscar Tipo Cliente");

        jXTaskPane2.setTitle("Nuevo Tipo De Cliente");

        jPanel4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jtfnombre.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtfnombreFocusLost(evt);
            }
        });
        jtfnombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtfnombreKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtfnombreKeyTyped(evt);
            }
        });

        jLabel14.setText("Nombre Del Tipo De Cliente");

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

        jLabel15.setText("Tipo De Factura Venta");

        jLabel2.setForeground(new java.awt.Color(255, 0, 0));
        jLabel2.setText("jLabel2");

        jLabel3.setForeground(new java.awt.Color(255, 0, 0));
        jLabel3.setText("jLabel3");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jtfnombre, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(29, 29, 29)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel15)
                            .addComponent(jcbtfv, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 130, Short.MAX_VALUE)
                        .addComponent(btnguardar1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancelar1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton1)
                        .addGap(20, 20, 20))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addGap(1, 1, 1)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(btnguardar1)
                                .addComponent(btnCancelar1)
                                .addComponent(jButton1))
                            .addComponent(jtfnombre, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addGap(1, 1, 1)
                        .addComponent(jcbtfv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        jXTaskPane2.getContentPane().add(jPanel4);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(161, 161, 161)
                        .addComponent(jTextFieldBuscarAutor, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1)
                            .addComponent(jXTaskPane2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(22, 22, 22)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(652, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextFieldBuscarAutor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jXTaskPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLabel1)
                    .addContainerGap(283, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnguardar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnguardar1ActionPerformed
       boolean d = false;
       jLabel2.setVisible(false);
       jLabel3.setVisible(false);
       jtfnombre.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
       if(jcbtfv.getSelectedItem() != null)
       {
           if(jtfnombre.getText().isEmpty()|| (consultaVenta.existeTipoCliente(jtfnombre.getText()) != null && bandera == 0 || r == true))
       {
           if(jtfnombre.getText().isEmpty())
           {
               jtfnombre.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
               jLabel2.setText("Debe ingresar un nombre");
               jLabel2.setVisible(true);
               d = true;
           }
           if(d == false && (consultaVenta.existeTipoCliente(jtfnombre.getText()) != null && bandera == 0 || r == true))
           {
               jtfnombre.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
               jLabel2.setText("Nombre existente");
               jLabel2.setVisible(true);
           }
       }
       else
       {
           if(bandera==1){
            tc = consultaVenta.getTipoClientePorId(getIdTipoCliente(indiceModelo));
            TipoFacturaVenta tfv= (TipoFacturaVenta) jcbtfv.getSelectedItem();
            tc.setNombreTipoCliente(jtfnombre.getText().toUpperCase());
            tc.setIdTipoFacturaVenta(tfv.getIdTipoFacturaVenta());
            consultaVenta.modificarTipoCliente(tc);
            cargarTabla(consultaVenta.getAllTipoCliente());
            JOptionPane.showMessageDialog(this,"Tipo De Cliente Modificado");
            jButton1.setEnabled(false);
            bandera=0;
            }
           else{
            consultaVenta.agregarTipoCliente(capturarCampos());
            JOptionPane.showMessageDialog(this,"Tipo De Cliente Agregado");
            cargarTabla(consultaVenta.getAllTipoCliente());
            bandera=0; 
            }
        jXTaskPane2.setCollapsed(true);
        jXTaskPane2.setTitle("Nuevo Tipo De Cliente");
        jButton1.setEnabled(false);
        jcbtfv.setSelectedIndex(0);
        jtfnombre.setText("");
       }
       }
       else
       {
           jLabel3.setText("Ningun tipo de factura creado o seleccionado");
           jLabel3.setVisible(true);
       }
    }//GEN-LAST:event_btnguardar1ActionPerformed

    private void btnCancelar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelar1ActionPerformed
        int respuesta=JOptionPane.showConfirmDialog(null, "¿Confirma la cancelación? \n Los datos no seran guardados","Advertencia", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            //confirmamos la eliminacion
            if(respuesta == 0)
            {
                jXTaskPane2.setCollapsed(true);
                jXTaskPane2.setTitle("Nuevo Tipo De Cliente");
                jtfnombre.setText("");
                bandera = 0;
                r = false;
                jLabel2.setVisible(false);
                jLabel3.setVisible(false);
                jButton1.setEnabled(false);
                jtfnombre.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
                cargarTabla(consultaVenta.getAllTipoCliente());
                if(jcbtfv.getSelectedItem() != null)
                {
                    jcbtfv.setSelectedIndex(0);
                }
            }
    }//GEN-LAST:event_btnCancelar1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        v=jtableAutores.getSelectedRow();//n° fila selccionada
            indiceModelo = jtableAutores.convertRowIndexToModel (v);//convierte el indice de la vista en el indice del modelo
            tc = consultaVenta.getTipoClientePorId(getIdTipoCliente(indiceModelo));
            List<Cliente> lista = consultaVenta.getAllCliente3(tc.getIdTipoCliente());
            if(lista.isEmpty())
            {
                int respuesta=JOptionPane.showConfirmDialog(null, "¿Realmente desea quitar este tipo de cliente de la lista?","Advertencia", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                //confirmamos la eliminacion
                if(respuesta == 0)
                {
                consultaVenta.eliminarTipoCliente(tc.getIdTipoCliente());
                cargarTabla(consultaVenta.getAllTipoCliente());
                bandera=0;
                id = id + 1;
                }
                else
                {
                    cargarTabla(consultaVenta.getAllTipoCliente());
                    bandera=0;
                }
            }
            else
            {
                JOptionPane.showMessageDialog(null, "No se puede eliminar un tipo de cliente asociado a un cliente", "Error", WIDTH);
                cargarTabla(consultaVenta.getAllTipoCliente());
                bandera=0;
            }
            jXTaskPane2.setCollapsed(true);
            jXTaskPane2.setTitle("Nuevo Tipo De Cliente");
            r = false;
            jLabel2.setVisible(false);
            jLabel3.setVisible(false);
            jtfnombre.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
            jButton1.setEnabled(false);
            jcbtfv.setSelectedIndex(0);
            jtfnombre.setText("");
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jtfnombreKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfnombreKeyReleased

    }//GEN-LAST:event_jtfnombreKeyReleased

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
            if(consultaVenta.existeTipoCliente(jtfnombre.getText()) != null && bandera == 1 && e == true)
            {  
                 r = true;
                 jtfnombre.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
                 jLabel2.setText("Nombre existente");
                 jLabel2.setVisible(true);
            }
            if(jtfnombre.getText().toUpperCase().contains(ntc))
            {
            r = false;
            jtfnombre.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
            jLabel2.setVisible(false);
            }
        }
            if(consultaVenta.existeTipoCliente(jtfnombre.getText()) != null && bandera == 0 && b == false)
            {
                 jtfnombre.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
                 jLabel2.setText("Nombre existente");
                 jLabel2.setVisible(true);
            }
    }//GEN-LAST:event_jtfnombreFocusLost

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar1;
    private javax.swing.JButton btnguardar1;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextFieldBuscarAutor;
    private org.jdesktop.swingx.JXTaskPane jXTaskPane2;
    private javax.swing.JComboBox jcbtfv;
    private javax.swing.JTable jtableAutores;
    private javax.swing.JTextField jtfnombre;
    // End of variables declaration//GEN-END:variables
}
