package Compra;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import mapa.ConsultaMapa;
import mapa.localidad;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

/**
 *
 * @author SEBASTIAN
 */
public class jdnuevoProveedor extends javax.swing.JDialog {
private ConsultaCompra oConsultaCompra;
private ConsultaMapa oConsultaMapa;
private int bandera=0,id=0,longitud,longitud2;;
private proveedor pProveedor;
private localidad pLocalidad;
private boolean controLabel=false,controlN,controlC,controlT;
    /**
     * Creates new form jdnuevoProveedor
     */
    public jdnuevoProveedor(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        oConsultaCompra = new ConsultaCompra();
        oConsultaMapa = new ConsultaMapa();
        bandera=0;
        CargarComboLocalidades();
        AutoCompleteDecorator.decorate(this.jcbLocalidad);
        labelNombre.setVisible(false);
        labelCuit.setVisible(false);
        labelTel.setVisible(false);
        labelLocalidad.setVisible(false);
        
    }
private proveedor capturarCampos(){
     localidad l= (localidad) jcbLocalidad.getSelectedItem();
        
        pProveedor= new proveedor(
                id,
                jtfnombre.getText().toUpperCase(),
                jtfDir.getText().toUpperCase(),
                jtfCuit.getText(),
                jtfTel.getText(),
                l.getIdLocalidad()
                );
                
        
        return  pProveedor;
        }
     
     private void CargarComboLocalidades(){
        
     List<localidad> lista = oConsultaMapa.getAllLocalidad();

        for (int i = 0; i < lista.size(); i++) {
       
            localidad emc=new localidad(
                    lista.get(i).getIdLocalidad(),
                    lista.get(i).getNombreLocalidad(),
                    lista.get(i).getIdProvincia());
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
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel4 = new javax.swing.JPanel();
        jtfnombre = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        btnguardar1 = new javax.swing.JButton();
        btnCancelar1 = new javax.swing.JButton();
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

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Proveedor");

        jPanel4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jtfnombre.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(0, 0, 0), null));
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

        jLabel2.setText("CUIT ");

        jtfCuit.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(0, 0, 0), null));
        jtfCuit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtfCuitKeyTyped(evt);
            }
        });

        jLabel4.setText("TELEFONO ");

        jLabel8.setText("DIRECCION ");

        jtfDir.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(0, 0, 0), null));

        jtfTel.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(0, 0, 0), null));
        jtfTel.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtfTelKeyTyped(evt);
            }
        });

        LOCALIDAD.setText("LOCALIDAD");

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

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(btnguardar1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancelar1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelNombre, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtfnombre))
                        .addGap(43, 43, 43)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(labelCuit, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel2)
                                .addComponent(jtfCuit)))
                        .addGap(32, 32, 32)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jtfTel)
                            .addComponent(labelTel, javax.swing.GroupLayout.DEFAULT_SIZE, 195, Short.MAX_VALUE))
                        .addGap(21, 21, 21))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jtfDir)
                            .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGap(65, 65, 65)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
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
                .addComponent(labelLocalidad)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnguardar1)
                    .addComponent(btnCancelar1))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 868, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(10, 10, 10)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGap(11, 11, 11)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 228, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(14, 14, 14)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGap(14, 14, 14)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jtfnombreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfnombreKeyTyped
        char car = evt.getKeyChar();
        if(controlN!=false){
            labelNombre.setVisible(false);
            jtfnombre.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
            controlN=false;
        }

    }//GEN-LAST:event_jtfnombreKeyTyped

    private void btnguardar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnguardar1ActionPerformed
        if( comprobarNulosN()== false && comprobarNulosC()== false
            && comprobarNulosT()== false && controLabel!=true ){
                oConsultaCompra.agregarProveedor(capturarCampos());
                JOptionPane.showMessageDialog(this,"Proveedor Agregado");
                this.dispose();
        }

    }//GEN-LAST:event_btnguardar1ActionPerformed

    private void btnCancelar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelar1ActionPerformed
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
        jtfDir.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
        this.dispose();
    }//GEN-LAST:event_btnCancelar1ActionPerformed

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
        if(longitud < 5 && controLabel!=true){

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

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(jdnuevoProveedor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(jdnuevoProveedor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(jdnuevoProveedor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(jdnuevoProveedor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                jdnuevoProveedor dialog = new jdnuevoProveedor(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel LOCALIDAD;
    private javax.swing.JButton btnCancelar1;
    private javax.swing.JButton btnguardar1;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel4;
    private org.jdesktop.swingx.JXComboBox jcbLocalidad;
    private javax.swing.JTextField jtfCuit;
    private javax.swing.JTextField jtfDir;
    private javax.swing.JTextField jtfTel;
    private javax.swing.JTextField jtfnombre;
    private javax.swing.JLabel labelCuit;
    private javax.swing.JLabel labelLocalidad;
    private javax.swing.JLabel labelNombre;
    private javax.swing.JLabel labelTel;
    // End of variables declaration//GEN-END:variables
}
