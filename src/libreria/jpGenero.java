package libreria;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author SEBASTIAN
 */
public final class jpGenero extends javax.swing.JPanel {
private consultasLibreria oConsultaLibreria;
private TableRowSorter<TableModel> sorter;//para ordenar la tabla
private int v=0,indiceModelo=0,bandera=0,id,longitud,longitud2;
private List <Libro> l;
private genero pGenero, pg;
private boolean r = false;
private boolean controLabel,controlN,controlT,estadoGenero;

    /**
     * Creates new form jpGenero
     */
    public jpGenero() {
        initComponents();
        bandera=0;
        oConsultaLibreria = new consultasLibreria();
        labelEdit.setVisible(false);
        btnEliminar.setEnabled(false);
        jXTaskPane2.setCollapsed(true);
        
           jtableGenero.addMouseListener(new MouseAdapter() 
        {
           @Override
           public void mouseClicked(MouseEvent e) 
           {
             if(e.getClickCount()== 2){
              int fila = jtableGenero.rowAtPoint(e.getPoint());
              int columna = jtableGenero.columnAtPoint(e.getPoint());
             /*El método rowAtPoint() nos devuelve -1 si pinchamos en el JTable
              pero fuera de cualquier fila*/
              
                     if ((fila > -1) && (columna > -1))
                     {
                       v=jtableGenero.getSelectedRow();//n° fila selccionada
                       indiceModelo = jtableGenero.convertRowIndexToModel (v);//convierte el indice de la vista en el indice del modelo 
                       
                       jXTaskPane2.setTitle("Modificar Autor");
                       bandera=1;
                       cargarCampos(oConsultaLibreria.getGeneroPorId(getIdGenero(indiceModelo)));
                       btnEliminar.setEnabled(true);
                       labelEdit.setVisible(false);
                       jtfnombre.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
                       jtableGenero.setEnabled(false);
                       jXTaskPane2.setCollapsed(false);
                       pg = oConsultaLibreria.getGeneroPorId(getIdGenero(indiceModelo));
                       l = oConsultaLibreria.getAllLibro();
                       boolean f = false;
                       for(int i=0;i<l.size();i++)
                       {
                           if(l.get(i).getIdGenero() == pg.getIdGenero())
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
                     }
             }
           }
        });
        
       cargarTabla(oConsultaLibreria.getAllGeneroHab());
    }

     public void cargarTabla(List<genero> l){
    
        String[] columnNames = {"Id","Nombre_Genero"};
        int[] anchos = {40,100};
        
        Object[][] data = new Object[l.size()][columnNames.length];
         
        
         for (int i = 0; i < l.size(); i++) {
            data[i][0] = l.get(i).getIdGenero();
            data[i][1] = l.get(i).getNombreGenero();
        }
       DefaultTableModel dftm = new DefaultTableModel(data, columnNames)
                {
		//metodo para que las celdas del jtable sean no-editables	
                    @Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
       jtableGenero.setModel(dftm);
       for(int i = 0; i < jtableGenero.getColumnCount(); i++) {

        jtableGenero.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);

        }
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        for(int i=0;i<jtableGenero.getColumnCount();i++)
        {
            jtableGenero.getColumnModel().getColumn(i).setCellRenderer(tcr);
        }
        sorter = new TableRowSorter<TableModel>(dftm);
        jtableGenero.setRowSorter(sorter);
        jtableGenero.getRowSorter().toggleSortOrder(0);
        jtableGenero.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    
    }
     
     private int getIdGenero(int im){
        String[] fila= new String[1];//almaceno los valores del registro seleccionado en el string "fila"
        fila[0]=""+jtableGenero.getModel().getValueAt(im, 0);
        
        int idRep=Integer.parseInt(fila[0]);
        
        return  idRep;
    }
     
     private  void cargarCampos(genero Gen){
        id=Gen.getIdGenero();
        jtfnombre.setText(""+Gen.getNombreGenero());
        estadoGenero=Gen.getEstadoGenero();
        System.out.println("ESTADO DE GENERO:"+estadoGenero);
        System.out.println("ID GENERO:"+id);
        System.out.println("GENERO:"+Gen.getNombreGenero());
    }
     
     private genero capturarCampos(){
        pGenero= new genero(id,jtfnombre.getText().toUpperCase(),estadoGenero);
        return  pGenero;
        }
     
     public boolean comprobarNulosN(){
                if(jtfnombre.getText().equals(""))
                    {controlN=true;
                    labelEdit.setVisible(true);
                    labelEdit.setText("Debe ingresar un nombre de genero");
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

        jXTaskPane2 = new org.jdesktop.swingx.JXTaskPane();
        jPanel4 = new javax.swing.JPanel();
        jtfnombre = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        btnguardar1 = new javax.swing.JButton();
        btnCancelar1 = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        labelEdit = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtableGenero = new javax.swing.JTable();

        jXTaskPane2.setTitle("Nuevo Genero");

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

        jLabel14.setText("NOMBRE GENERO");

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
        labelEdit.setText("jLabel1");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jtfnombre, javax.swing.GroupLayout.DEFAULT_SIZE, 236, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(btnguardar1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancelar1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnEliminar)
                        .addGap(143, 143, 143))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(labelEdit)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtfnombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnguardar1)
                    .addComponent(btnCancelar1)
                    .addComponent(btnEliminar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(labelEdit)
                .addContainerGap(36, Short.MAX_VALUE))
        );

        jXTaskPane2.getContentPane().add(jPanel4);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Generos"));

        jtableGenero.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "idGenero", "Nombre Genero"
            }
        ));
        jScrollPane1.setViewportView(jtableGenero);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 193, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jXTaskPane2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(11, 11, 11)
                .addComponent(jXTaskPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnguardar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnguardar1ActionPerformed
 if( comprobarNulosN()== false && controLabel!=true ){      
     if(r == false){
     if(bandera==1){
       oConsultaLibreria.modificarGenero(capturarCampos());
       cargarTabla(oConsultaLibreria.getAllGeneroHab());
       JOptionPane.showMessageDialog(this,"Genero Modificado");
       btnEliminar.setEnabled(false);
       jtableGenero.setEnabled(true);
       bandera=0;
       }
       else{
       oConsultaLibreria.agregarGenero(capturarCampos());
       JOptionPane.showMessageDialog(this,"Genero Agregado");
       cargarTabla(oConsultaLibreria.getAllGeneroHab());
       bandera=0; 
       }
       jXTaskPane2.setTitle("Nuevo genero");
       jXTaskPane2.setCollapsed(true);
       jtfnombre.setText("");
       labelEdit.setVisible(false);
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
                jXTaskPane2.setCollapsed(true);
                jXTaskPane2.setTitle("Nuevo Genero");
                jtfnombre.setText("");
                labelEdit.setVisible(false);
                jtfnombre.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
                cargarTabla(oConsultaLibreria.getAllGeneroHab());
                btnEliminar.setEnabled(false);
                jtableGenero.setEnabled(true);
                bandera=0;
                id=0;
                estadoGenero=false;
                r = false;
            }
       
    }//GEN-LAST:event_btnCancelar1ActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
    int respuesta=JOptionPane.showConfirmDialog(null, "¿Realmente desea eliminar este genero?","Advertencia", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if(respuesta == 0)
        {
          genero g=capturarCampos();
          oConsultaLibreria.eliminaGenero(g.getIdGenero());
          cargarTabla(oConsultaLibreria.getAllGeneroHab());
          jXTaskPane2.setTitle("Nuevo Genero");
          jXTaskPane2.setCollapsed(true);
          jtfnombre.setText("");
          btnEliminar.setEnabled(false);
          jtableGenero.setEnabled(true);
          bandera=0;
          id=0;
          estadoGenero=false;
          labelEdit.setVisible(false);}
          jtfnombre.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
          r = false;
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void jtfnombreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfnombreKeyTyped
        char car = evt.getKeyChar();
        longitud=jtfnombre.getText().length();
        
        if(controlT!=false){
           labelEdit.setVisible(false);
           jtfnombre.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
           controlT=false;
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
                evt.consume();
                getToolkit().beep();
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
            for(int i=0;i<oConsultaLibreria.getAllGenero().size();i++)
            {
                if(oConsultaLibreria.getAllGenero().get(i).getNombreGenero().contentEquals(jtfnombre.getText().toUpperCase()) && bandera == 0)
                {
                    jtfnombre.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
                    labelEdit.setText("Nombre existente");
                    labelEdit.setVisible(true);
                    r = true;
                }
                else
                {
                    if(oConsultaLibreria.getAllGenero().get(i).getNombreGenero().contentEquals(y.toUpperCase()) && bandera == 1)
                    {
                    jtfnombre.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
                    labelEdit.setText("Nombre existente");
                    labelEdit.setVisible(true);
                    r = true;
                    }
                }
            }
        }
            if((bandera == 1) && ((jtfnombre.getText().toUpperCase().contentEquals(pg.getNombreGenero()))))
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
    private javax.swing.JLabel jLabel14;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private org.jdesktop.swingx.JXTaskPane jXTaskPane2;
    private javax.swing.JTable jtableGenero;
    private javax.swing.JTextField jtfnombre;
    private javax.swing.JLabel labelEdit;
    // End of variables declaration//GEN-END:variables
}
