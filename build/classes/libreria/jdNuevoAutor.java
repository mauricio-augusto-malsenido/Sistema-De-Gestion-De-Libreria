package libreria;
import java.awt.Color;
import java.awt.event.KeyEvent;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;

/**
 *
 * @author SEBASTIAN
 */
public class jdNuevoAutor extends javax.swing.JDialog {
private consultasLibreria oConsultaLibreria;
private int id=0,longitud,longitud2;
private autor pAutor;
private boolean controLabel,controlN;

public jdNuevoAutor(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        oConsultaLibreria = new consultasLibreria();
        labelEdit.setVisible(false);
    }
 
    private autor capturarCampos(){
        pAutor= new autor(id,jtfnombre.getText().toUpperCase());
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

        jPanel4 = new javax.swing.JPanel();
        jtfnombre = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        btnguardar1 = new javax.swing.JButton();
        btnCancelar1 = new javax.swing.JButton();
        labelEdit = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Nuevo autor");
        setResizable(false);

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
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(labelEdit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jtfnombre, javax.swing.GroupLayout.PREFERRED_SIZE, 406, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, Short.MAX_VALUE)))
                        .addComponent(btnguardar1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancelar1)
                        .addGap(112, 112, 112))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel14)
                .addGap(1, 1, 1)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtfnombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnguardar1)
                    .addComponent(btnCancelar1))
                .addGap(9, 9, 9)
                .addComponent(labelEdit)
                .addContainerGap(30, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 664, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 652, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 139, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jtfnombreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfnombreKeyTyped
        char car = evt.getKeyChar();
        longitud=jtfnombre.getText().length();

        if(controlN!=false){
            labelEdit.setVisible(false);
            jtfnombre.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
            controlN=false;
        }

        if(controLabel!=true){

            if((car<'a' || car>'z') && (car<'A' || car>'Z') && car!=KeyEvent.VK_BACK_SPACE && car!=KeyEvent.VK_ENTER && car!=KeyEvent.VK_SPACE){
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
            labelEdit.setVisible(true);
            labelEdit.setText("Alcanzo la longitud maxima de 45 caracteres");
        }

    }//GEN-LAST:event_jtfnombreKeyTyped

    private void btnguardar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnguardar1ActionPerformed

        if( comprobarNulosN()== false && controLabel!=true ){
            oConsultaLibreria.agregarAutor(capturarCampos());
                JOptionPane.showMessageDialog(this,"Autor Agregado");
                this.dispose();
        }

    }//GEN-LAST:event_btnguardar1ActionPerformed

    private void btnCancelar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelar1ActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCancelar1ActionPerformed

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
            java.util.logging.Logger.getLogger(jdNuevoAutor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(jdNuevoAutor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(jdNuevoAutor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(jdNuevoAutor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                jdNuevoAutor dialog = new jdNuevoAutor(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnCancelar1;
    private javax.swing.JButton btnguardar1;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JTextField jtfnombre;
    private javax.swing.JLabel labelEdit;
    // End of variables declaration//GEN-END:variables
}
