package libreria;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
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
public final class jpAutor extends javax.swing.JPanel {
private consultasLibreria oConsultaLibreria;
private TableRowSorter<TableModel> sorter;//para ordenar la tabla
private int v=0,indiceModelo=0,bandera=0,id,longitud,longitud2;
public String s;
private boolean r = false;
private autor pAutor, a;
private List <autor> facturasV;
private List <Libro> l;
private boolean controLabel,controlN,estadoAutor;

//BANDERA 0 NUEVO
//BANDERA 1 MODIFICAR

     public jpAutor() {
        initComponents();
        bandera=0;
        oConsultaLibreria = new consultasLibreria();
        busqueda();
        labelEdit.setVisible(false);
        btnEliminar.setEnabled(false);
        jXTaskPane2.setCollapsed(true);
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
              
                     if ((fila > -1) && (columna > -1))
                     {
                       v=jtableAutores.getSelectedRow();//n° fila selccionada
                       indiceModelo = jtableAutores.convertRowIndexToModel (v);//convierte el indice de la vista en el indice del modelo 
                       jXTaskPane2.setTitle("Modificar Autor");
                       jXTaskPane2.setCollapsed(false);
                       jTextFieldBuscarAutor.setEnabled(false);
                       jTextFieldBuscarAutor.setEditable(false);
                       labelEdit.setVisible(false);
                       jtfnombre.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
                       bandera=1;
                       cargarCampos(oConsultaLibreria.getAutorPorId(getIdAutor(indiceModelo)));
                       a = oConsultaLibreria.getAutorPorId(getIdAutor(indiceModelo));
                       l = oConsultaLibreria.getAllLibro();
                       boolean f = false;
                       for(int i=0;i<l.size();i++)
                       {
                           if(l.get(i).getIdAutor() == a.getIdAutor())
                           {
                               f = true;
                           }
                       }
                       if(f == true)
                       {
                           btnEliminar.setEnabled(false);
                       }
                       else
                       {
                           btnEliminar.setEnabled(true);
                       }
                       jtableAutores.setEnabled(false);
                     }
             }
           }
        });
        
       cargarTabla(oConsultaLibreria.getAllAutor());
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
     
     public void cargarTabla(List<autor> l){
        String[] columnNames = {"Id","Nombre_Autor"};
        int[] anchos = {40,100};
        Object[][] data = new Object[l.size()][columnNames.length];
         
        
         for (int i = 0; i < l.size(); i++) {
            data[i][0] = l.get(i).getIdAutor();
            data[i][1] = l.get(i).getNombreAutor();
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
     
     private  void cargarCampos(autor Autor){
        id=Autor.getIdAutor();
        jtfnombre.setText(""+Autor.getNombreAutor());
        estadoAutor=Autor.getEstadoAutor();
    }
     
     private autor capturarCampos(){
        pAutor= new autor(id,jtfnombre.getText().toUpperCase(),estadoAutor);
        return  pAutor;
        }
     
    public boolean comprobarNulosN(){
                if(jtfnombre.getText().equals(""))
                    {controlN=true;
                    labelEdit.setVisible(true);
                    labelEdit.setText("Debe ingresar un nombre de autor");
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
        btnEliminar = new javax.swing.JButton();
        labelEdit = new javax.swing.JLabel();

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
        jLabel1.setText("Buscar Autor");

        jXTaskPane2.setTitle("Nuevo Autor");

        jPanel4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jtfnombre.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(0, 0, 0), null));
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

        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/eliminar_1.png"))); // NOI18N
        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        labelEdit.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        labelEdit.setForeground(new java.awt.Color(204, 0, 0));
        labelEdit.setText("jLabel2");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(labelEdit, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jtfnombre, javax.swing.GroupLayout.DEFAULT_SIZE, 236, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addComponent(btnguardar1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancelar1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnEliminar)
                        .addGap(143, 143, 143))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel14)
                .addGap(1, 1, 1)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnguardar1)
                        .addComponent(btnCancelar1)
                        .addComponent(btnEliminar))
                    .addComponent(jtfnombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelEdit)
                .addContainerGap(30, Short.MAX_VALUE))
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
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jTextFieldBuscarAutor, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(405, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 113, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jXTaskPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(jTextFieldBuscarAutor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addContainerGap(306, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnguardar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnguardar1ActionPerformed
       
     if( comprobarNulosN()== false && controLabel!=true){      
       if(r == false){
       if(bandera==1){
       oConsultaLibreria.modificarAutor(capturarCampos());
       cargarTabla(oConsultaLibreria.getAllAutor());
       JOptionPane.showMessageDialog(this,"Autor Modificado");
       btnEliminar.setEnabled(false);
       jtableAutores.setEnabled(true);
       jTextFieldBuscarAutor.setEnabled(true);
       jTextFieldBuscarAutor.setEditable(true);
       bandera=0;
       id=0;
       estadoAutor=false;
       }
       else{
       oConsultaLibreria.agregarAutor(capturarCampos());
       JOptionPane.showMessageDialog(this,"Autor Agregado");
       cargarTabla(oConsultaLibreria.getAllAutor());
       jTextFieldBuscarAutor.setEnabled(true);
       jTextFieldBuscarAutor.setEditable(true);
       bandera=0; 
       }
       jXTaskPane2.setTitle("Nuevo Autor");
       jXTaskPane2.setCollapsed(true);
       jtfnombre.setText("");
       }
       else
       {
                if(r == true)
                {
                    jtfnombre.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
                    labelEdit.setText("Nombre existente");
                    labelEdit.setVisible(true);
                }
       }
     }    
    }//GEN-LAST:event_btnguardar1ActionPerformed

    private void btnCancelar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelar1ActionPerformed
       int respuesta=JOptionPane.showConfirmDialog(null, "¿Confirma la cancelación? \n Los datos no seran guardados","Advertencia", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            //confirmamos la eliminacion
            if(respuesta == 0)
            {
                jXTaskPane2.setTitle("Nuevo Autor");
                jXTaskPane2.setCollapsed(true);
                jtfnombre.setText("");
                labelEdit.setVisible(false);
                jtfnombre.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
                cargarTabla(oConsultaLibreria.getAllAutor());
                btnEliminar.setEnabled(false);
                jTextFieldBuscarAutor.setEnabled(true);
                jTextFieldBuscarAutor.setEditable(true);
                jtableAutores.setEnabled(true);
                jtfnombre.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
                labelEdit.setVisible(false);
                bandera=0;
                r = false;
                id=0;
                estadoAutor=false;
            }
            
    }//GEN-LAST:event_btnCancelar1ActionPerformed
    
    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        
        int respuesta=JOptionPane.showConfirmDialog(null, "¿Realmente desea eliminar el autor?","Advertencia", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if(respuesta == 0)
        {
        autor a=capturarCampos();
        oConsultaLibreria.eliminaAutor(a.getIdAutor());
        jXTaskPane2.setTitle("Nuevo Autor");
        jXTaskPane2.setCollapsed(true);
        jtfnombre.setText("");
        cargarTabla(oConsultaLibreria.getAllAutor());
        btnEliminar.setEnabled(false);
        jtableAutores.setEnabled(true);
        jTextFieldBuscarAutor.setEnabled(true);
        jTextFieldBuscarAutor.setEditable(true);
        r = false;
        bandera=0;
        id=0;
        estadoAutor=false;
        jtfnombre.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
        labelEdit.setVisible(false);
        }
        
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void jtfnombreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfnombreKeyTyped
         char car = evt.getKeyChar();
         longitud=jtfnombre.getText().length();
        
           if(controlN!=false){
           labelEdit.setVisible(false);
           jtfnombre.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
           controlN=false;
           }
               
            if(controLabel!=true){
           
                if(Character.isDigit(car)){
                getToolkit().beep();
                evt.consume();
                labelEdit.setVisible(true);
                labelEdit.setText("En este campo solo se deben ingresar letras");
                jtfnombre.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
                controLabel=true;
                longitud2=jtfnombre.getText().length();
                }
                else{
                labelEdit.setVisible(false);
                jtfnombre.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
                controLabel=false;
                }
            }
            else{
                evt.consume();    
                    if(longitud-1 < longitud2){
                    labelEdit.setVisible(false);
                    jtfnombre.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
                    controLabel=false;
                }
               
            }
            
            if(longitud > 44){
                getToolkit().beep();
                evt.consume();
                labelEdit.setVisible(true);
                labelEdit.setText("Alcanzo la longitud maxima de 45 caracteres");
            }
        

    }//GEN-LAST:event_jtfnombreKeyTyped

    private void jtfnombreFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtfnombreFocusLost
        jtfnombre.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
        labelEdit.setVisible(false);
        String y = jtfnombre.getText();
        System.out.println("holllllaaaaa "+y);
        boolean b = false;
        r = false;
        if(jtfnombre.getText().isEmpty())
        {
                    jtfnombre.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
                    labelEdit.setText("Debe ingresar un nombre");
                    labelEdit.setVisible(true);
                    b = true;
        }
        else
        {
            for(int i=0;i<oConsultaLibreria.getAllAutorHab().size();i++)
            {
                if(oConsultaLibreria.getAllAutorHab().get(i).getNombreAutor().contentEquals(jtfnombre.getText().toUpperCase()) && bandera == 0)
                {
                    jtfnombre.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
                    labelEdit.setText("Nombre existente");
                    labelEdit.setVisible(true);
                    r = true;
                }
                else
                {
                    if(oConsultaLibreria.getAllAutorHab().get(i).getNombreAutor().contentEquals(y.toUpperCase()) && bandera == 1)
                    {
                    System.out.println("HOLLLAAAAA "+s);
                    jtfnombre.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
                    labelEdit.setText("Nombre existente");
                    labelEdit.setVisible(true);
                    r = true;
                    }
                }
            }
        }
            if((bandera == 1) && ((jtfnombre.getText().toUpperCase().contentEquals(a.getNombreAutor()))))
                {
                    labelEdit.setVisible(false);
                    jtfnombre.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
                    r = false;
                }
    }//GEN-LAST:event_jtfnombreFocusLost

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar1;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnguardar1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextFieldBuscarAutor;
    private org.jdesktop.swingx.JXTaskPane jXTaskPane2;
    private javax.swing.JTable jtableAutores;
    private javax.swing.JTextField jtfnombre;
    private javax.swing.JLabel labelEdit;
    // End of variables declaration//GEN-END:variables
}
