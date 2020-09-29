package Empleados;

import java.awt.Color;
import libreria.*;
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
public class ABMTipoUsuario extends javax.swing.JPanel {
private ConsultaUsuario consultaUsuario;
private TableRowSorter<TableModel> sorter;//para ordenar la tabla
private int v=0,indiceModelo=0,bandera=0,id=0;
public String s;
private boolean r = false;
private List<Usuario> lista;
private TipoUsuario pGenero;

    /**
     * Creates new form ABMTipoUsuario
     */
    public ABMTipoUsuario() {
        initComponents();
        bandera=0;
        consultaUsuario = new ConsultaUsuario();
        jXTaskPane2.setCollapsed(true);
        jButton1.setVisible(false);
        jLabel4.setVisible(false);
        busqueda();
        
           jtableGenero.addMouseListener(new MouseAdapter() 
        {
           @Override
           public void mouseClicked(MouseEvent e) 
           {
             if(e.getClickCount()== 2){
              int fila = jtableGenero.rowAtPoint(e.getPoint());
              int columna = jtableGenero.columnAtPoint(e.getPoint());
              s = jtfnombre.getText();
              System.out.println("OUUUU "+s);
             /*El método rowAtPoint() nos devuelve -1 si pinchamos en el JTable
              pero fuera de cualquier fila*/
              
                     if ((fila > -1) && (columna > -1))
                     {
                       v=jtableGenero.getSelectedRow();//n° fila selccionada
                       indiceModelo = jtableGenero.convertRowIndexToModel (v);//convierte el indice de la vista en el indice del modelo 
                       
                       jXTaskPane2.setTitle("Modificar Tipo De Usuario");
                       jXTaskPane2.setCollapsed(false);
                       jTextFieldBuscarAutor.setEnabled(false);
                       jTextFieldBuscarAutor.setEditable(false);
                       jLabel4.setVisible(false);
                       jtfnombre.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
                       jButton1.setVisible(true);
                       bandera=1;
                       pGenero = consultaUsuario.getTipoUsuarioPorId(getIdTipoUsuario(indiceModelo));
                       s = pGenero.getNombreTipoUsuario();
                       cargarCampos(pGenero);
                       lista = consultaUsuario.getAllUsuario();
                       boolean f = false;
                       for(int i=0;i<lista.size();i++)
                       {
                           if(lista.get(i).getIdTipoUsuario() == pGenero.getIdTipoUsuario())
                           {
                               f = true;
                           }
                       }
                       if(f == true)
                       {
                           jButton1.setEnabled(false);
                       }
                       else
                       {
                           jButton1.setEnabled(true);
                       }
                       jtableGenero.setEnabled(false);
                     }
             }
           }
        });
        
       cargarTabla(consultaUsuario.getAllTipoUsuario());
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
     
     public void cargarTabla(List<TipoUsuario> l){
    
        String[] columnNames = {"Id","Nombre Tipo"};
        int[] anchos = {40,100};
        
        Object[][] data = new Object[l.size()][columnNames.length];
         
        
         for (int i = 0; i < l.size(); i++) {
            data[i][0] = l.get(i).getIdTipoUsuario();
            data[i][1] = l.get(i).getNombreTipoUsuario();
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
     
     private int getIdTipoUsuario(int im){
        String[] fila= new String[1];//almaceno los valores del registro seleccionado en el string "fila"
        fila[0]=""+jtableGenero.getModel().getValueAt(im, 0);
        
        int idRep=Integer.parseInt(fila[0]);
        
        return  idRep;
    }
     
     private  void cargarCampos(TipoUsuario tu){
        id=tu.getIdTipoUsuario();
        jtfnombre.setText(tu.getNombreTipoUsuario());
    }
     
     private TipoUsuario capturarCampos(){
        pGenero= new TipoUsuario(id,jtfnombre.getText().toUpperCase());
        return  pGenero;
        }
     
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jXTaskPane2 = new org.jdesktop.swingx.JXTaskPane();
        jPanel4 = new javax.swing.JPanel();
        jtfnombre = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        btnguardar1 = new javax.swing.JButton();
        btnCancelar1 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jTextFieldBuscarAutor = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtableGenero = new javax.swing.JTable();

        jXTaskPane2.setTitle("Nuevo Tipo Usuario");

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

        jLabel14.setText("NOMBRE TIPO");

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

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 0, 0));
        jLabel4.setText("jLabel2");

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
                        .addComponent(jtfnombre, javax.swing.GroupLayout.DEFAULT_SIZE, 325, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(btnguardar1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancelar1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton1)
                        .addGap(143, 143, 143))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel14)
                .addGap(1, 1, 1)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtfnombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnguardar1)
                    .addComponent(btnCancelar1)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addContainerGap())
        );

        jXTaskPane2.getContentPane().add(jPanel4);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setText("Buscar Tipo Usuario");

        jtableGenero.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jtableGenero);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(107, 107, 107)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(jTextFieldBuscarAutor, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jXTaskPane2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldBuscarAutor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE)
                .addGap(19, 19, 19)
                .addComponent(jXTaskPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnguardar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnguardar1ActionPerformed
       jtfnombre.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
       jLabel4.setVisible(false);
       if(jtfnombre.getText().isEmpty() == false && r == false){
           if(bandera==1){
                consultaUsuario.modificarTipoUsuario(capturarCampos());
                cargarTabla(consultaUsuario.getAllTipoUsuario());
                JOptionPane.showMessageDialog(this,"Tipo De Usuario Modificado");
                bandera=0;
            }
            else{
                consultaUsuario.agregarTipoUsuario(capturarCampos());
                JOptionPane.showMessageDialog(this,"Tipo De Usuario Agregado");
                cargarTabla(consultaUsuario.getAllTipoUsuario());
                bandera=0; 
            }
            jXTaskPane2.setCollapsed(true);
            jXTaskPane2.setTitle("Nuevo Tipo De Usuario");
            jtableGenero.setEnabled(true);
            jtfnombre.setText("");
            jButton1.setVisible(false);
            jtableGenero.setEnabled(true);
            jTextFieldBuscarAutor.setEnabled(true);
            jTextFieldBuscarAutor.setEditable(true);
       }
       else
       {
                if(r == true)
                {
                    jtfnombre.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
                    jLabel4.setText("Nombre existente");
                    jLabel4.setVisible(true);
                }
                else
                {
                    jtfnombre.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
                    jLabel4.setText("Debe ingresar un nombre");
                    jLabel4.setVisible(true);
                }
       }
    }//GEN-LAST:event_btnguardar1ActionPerformed

    private void btnCancelar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelar1ActionPerformed
       int respuesta=JOptionPane.showConfirmDialog(null, "¿Confirma la cancelación? \n Los datos no seran guardados","Advertencia", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            //confirmamos la eliminacion
            if(respuesta == 0)
            {
                jtfnombre.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
                jLabel4.setVisible(false);
                jXTaskPane2.setCollapsed(true);
                jXTaskPane2.setTitle("Nuevo Tipo De Usuario");
                jtfnombre.setText("");
                jTextFieldBuscarAutor.setEnabled(true);
                jTextFieldBuscarAutor.setEditable(true);
                jtableGenero.setEnabled(true);
                bandera = 0;
                jButton1.setVisible(false);
                cargarTabla(consultaUsuario.getAllTipoUsuario());
                r = false;
                bandera = 0;
            }
    }//GEN-LAST:event_btnCancelar1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        v=jtableGenero.getSelectedRow();//n° fila selccionada
            indiceModelo = jtableGenero.convertRowIndexToModel (v);//convierte el indice de la vista en el indice del modelo
            pGenero = consultaUsuario.getTipoUsuarioPorId(getIdTipoUsuario(indiceModelo));
            int respuesta=JOptionPane.showConfirmDialog(null, "¿Realmente desea quitar este tipo de usuario de la lista?","Advertencia", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            //confirmamos la eliminacion
            if(respuesta == 0)
            {
                consultaUsuario.eliminarTipoUsuario(pGenero.getIdTipoUsuario());
                cargarTabla(consultaUsuario.getAllTipoUsuario());
                jXTaskPane2.setCollapsed(true);
                jXTaskPane2.setTitle("Nuevo Tipo De Usuario");
                jtfnombre.setText("");
                jButton1.setVisible(false);
                jtableGenero.setEnabled(true);
                jTextFieldBuscarAutor.setEnabled(true);
                jTextFieldBuscarAutor.setEditable(true);
                r = false;
                bandera = 0;
                jtfnombre.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
                jLabel4.setVisible(false);
            }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jtfnombreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfnombreKeyTyped
        jtfnombre.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
        jLabel4.setVisible(false);
        char c=evt.getKeyChar();
        
          if(Character.isDigit(c)) {
              getToolkit().beep();
              
              evt.consume();
              
              jtfnombre.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
              jLabel4.setText("Solo puede ingresar texto");
              jLabel4.setVisible(true);
              
          }
        if(jtfnombre.getText().length() == 45) {
              getToolkit().beep();
              
              evt.consume();
              
              jtfnombre.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
              jLabel4.setText("Solo puede ingresar hasta 45 caracteres");
              jLabel4.setVisible(true);
              
          }
    }//GEN-LAST:event_jtfnombreKeyTyped

    private void jtfnombreFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtfnombreFocusLost
        jtfnombre.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
        jLabel4.setVisible(false);
        String y = jtfnombre.getText();
        System.out.println("holllllaaaaa "+y);
        boolean b = false;
        r = false;
        if(jtfnombre.getText().isEmpty())
        {
                    jtfnombre.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
                    jLabel4.setText("Debe ingresar un nombre");
                    jLabel4.setVisible(true);
                    b = true;
        }
        else
        {
            for(int i=0;i<consultaUsuario.getAllTipoUsuario().size();i++)
            {
                if(consultaUsuario.getAllTipoUsuario().get(i).getNombreTipoUsuario().contentEquals(jtfnombre.getText().toUpperCase()) && bandera == 0)
                {
                    jtfnombre.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
                    jLabel4.setText("Nombre existente");
                    jLabel4.setVisible(true);
                    r = true;
                }
                else
                {
                    if(consultaUsuario.getAllTipoUsuario().get(i).getNombreTipoUsuario().contentEquals(y.toUpperCase()) && bandera == 1)
                    {
                    System.out.println("HOLLLAAAAA "+s);
                    jtfnombre.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
                    jLabel4.setText("Nombre existente");
                    jLabel4.setVisible(true);
                    r = true;
                    }
                }
            }
            if((jtfnombre.getText().toUpperCase().contentEquals(s)) && bandera == 1)
                {
                    jLabel4.setVisible(false);
                    jtfnombre.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
                    r = false;
                }
        }
    }//GEN-LAST:event_jtfnombreFocusLost

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar1;
    private javax.swing.JButton btnguardar1;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextFieldBuscarAutor;
    private org.jdesktop.swingx.JXTaskPane jXTaskPane2;
    private javax.swing.JTable jtableGenero;
    private javax.swing.JTextField jtfnombre;
    // End of variables declaration//GEN-END:variables
}
