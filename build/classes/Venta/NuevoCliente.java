/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Venta;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import mapa.ConsultaMapa;
import mapa.localidad;

/**
 *
 * @author Mauricio
 */
public class NuevoCliente extends javax.swing.JDialog {

private ConsultaVenta consultaVenta;
private ConsultaMapa consultaMapa;
private Cliente cliente;
//private ConsultaOrdenServicio oConsultaOrden;
private int v=0,indiceModelo=0,bandera,id;
private boolean e = false;
private boolean t = false;
    public NuevoCliente(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        consultaVenta = new ConsultaVenta();
        consultaMapa = new ConsultaMapa();
        jtfdni.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
        jtfcuit.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
        jtfnombre.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
        jtfdir.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
        jtftel.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
        jtfemail.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
        jLabel2.setVisible(false);
        jLabel3.setVisible(false);
        jLabel4.setVisible(false);
        jLabel5.setVisible(false);
        jLabel6.setVisible(false);
        jLabel7.setVisible(false);
        jLabel8.setVisible(false);
        jLabel9.setVisible(false);
        CargarComboLocalidad();
        CargarComboTipoDeCliente();
    }

    private void CargarComboLocalidad(){
     jLabel9.setVisible(false);   
     List<localidad> lista = consultaMapa.getAllLocalidad();

        if(lista.isEmpty())
        {
            jLabel9.setText("Ninguna localidad creada o seleccionada");
            jLabel9.setVisible(true);
            jLabel8.setVisible(true);
            jtfcuit.setEditable(false);
            jtfcuit.setEnabled(false);
            jtfdni.setEditable(false);
            jtfdni.setEnabled(false);
            jtfcuit.setEditable(false);
            jtfcuit.setEnabled(false);
            jtfdni.setEditable(false);
            jtfdni.setEnabled(false);
            jtfnombre.setEditable(false);
            jtfnombre.setEnabled(false);
            jtfdir.setEditable(false);
            jtfdir.setEnabled(false);
            jtftel.setEditable(false);
            jtftel.setEnabled(false);
            jtfemail.setEditable(false);
            jtfemail.setEnabled(false);
            jcbtdc.setEnabled(false);
        }
        else
        {
            for (int i = 0; i < lista.size(); i++) {
       
            localidad l=new localidad(lista.get(i).getIdLocalidad(), lista.get(i).getNombreLocalidad(), lista.get(i).getIdProvincia());
            jcbloc.addItem(l);
            localidad objeto=(localidad) jcbloc.getItemAt(1);

            }   
        }
        
     }
    
public void limpiar()
    {
        jtfdni.setText("");
        jcbtdc.removeAllItems();
        jtfcuit.setText("");
        jtfnombre.setText("");
        jtfdir.setText("");
        jtftel.setText("");
        jtfemail.setText("");
        if(jcbtdc.getSelectedItem() != null)
        {
            jcbtdc.setSelectedIndex(0);
        }
        if(jcbloc.getSelectedItem() != null)
        {
            jcbloc.setSelectedIndex(0);
        }
    }
public void limpiar2()
    {
        jtfdni.setText("");
        jtfcuit.setText("");
        jtfnombre.setText("");
        jtfdir.setText("");
        jtftel.setText("");
        jtfemail.setText("");
        if(jcbtdc.getSelectedItem() != null)
        {
            jcbtdc.setSelectedIndex(0);
        }
        if(jcbloc.getSelectedItem() != null)
        {
            jcbloc.setSelectedIndex(0);
        }
    }
private static final String coef = "5432765432"; //coeficiente
     public boolean comprobarCUIT(String numCUIT){
            try {
            int su = 0;
            numCUIT = numCUIT.substring(0, 2) + numCUIT.substring(3, 11) + numCUIT.substring(12); 
            int lCuit = numCUIT.length();
            if (lCuit < 9) {
                numCUIT = "00000000000";
            return false;
            }
            for(int i = 1; i < 11; i++) {
                String Cd1 = coef.substring(i-1, i);
                String Cd2 = numCUIT.substring(i-1, i);
                int cf = Integer.parseInt(Cd1); //casteo...
                int ct = Integer.parseInt(Cd2); //casteo...
                su += (cf * ct);
            }
            int md = su / 11;
            int re = su - (md * 11);
            if(re > 1) {
            re = 11 - re;
            }
            String CdDv = numCUIT.substring(lCuit - 1, lCuit);
            int dv = Integer.parseInt(CdDv); //casteo...
            if(dv == re) {
            return true;
            } else {
            return false;
            }
            }
            catch (Exception e) {
            return false;
            }
     }
public boolean verificarCuit(String cuit)
    {
        boolean b = false;
        if(cuit.length() == 13)
        {
                if(cuit.charAt(2) == '-')
                {
                if(cuit.charAt(11) == '-')
                {
                    b = true;
                }
                }
            
        }
        else
        {
            if(cuit.isEmpty()) {b = true;}
        }
        return b;
    }

public boolean verificarDni(String dni)
    {
        boolean b = false;
        if((dni.length() == 8 || dni.length() == 7 || dni.length() == 9) && dni.startsWith("0") == false)
        {
                b = true;
        }
        return b;
    }
private void CargarComboTipoDeCliente(){
     jLabel8.setVisible(false);   
     List<TipoCliente> lista = consultaVenta.getAllTipoCliente();

        if(lista.isEmpty())
        {
            jLabel8.setText("Ningún tipo de cliente creado o seleccionado");
            jLabel8.setVisible(true);
            jtfcuit.setEditable(false);
            jtfcuit.setEnabled(false);
            jtfdni.setEditable(false);
            jtfdni.setEnabled(false);
            jtfcuit.setEditable(false);
            jtfcuit.setEnabled(false);
            jtfdni.setEditable(false);
            jtfdni.setEnabled(false);
            jtfnombre.setEditable(false);
            jtfnombre.setEnabled(false);
            jtfdir.setEditable(false);
            jtfdir.setEnabled(false);
            jtftel.setEditable(false);
            jtftel.setEnabled(false);
            jtfemail.setEditable(false);
            jtfemail.setEnabled(false);
            jcbloc.setEnabled(false);
        }
        else
        {
            for (int i = 0; i < lista.size(); i++) {
       
            TipoCliente tp=new TipoCliente(lista.get(i).getIdTipoCliente(), lista.get(i).getNombreTipoCliente(), lista.get(i).isIdTipoFacturaVenta());
            jcbtdc.addItem(tp);
            TipoCliente objeto=(TipoCliente) jcbtdc.getItemAt(1);

            }
        }
     }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel4 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jtfdni = new javax.swing.JTextField();
        jtfnombre = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jtftel = new javax.swing.JTextField();
        jtfemail = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jtfdir = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        btnguardar1 = new javax.swing.JButton();
        btnCancelar1 = new javax.swing.JButton();
        jcbtdc = new javax.swing.JComboBox();
        jLabel18 = new javax.swing.JLabel();
        jtfcuit = new javax.swing.JTextField();
        jcbloc = new javax.swing.JComboBox();
        jLabel21 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Nuevo Cliente");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jPanel4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel13.setText("DNI (sin puntos)");

        jtfdni.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtfdniFocusLost(evt);
            }
        });
        jtfdni.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtfdniKeyTyped(evt);
            }
        });

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

        jLabel14.setText("NOMBRE");

        jLabel15.setText("TELEFONO");

        jtftel.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtftelFocusLost(evt);
            }
        });
        jtftel.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtftelKeyTyped(evt);
            }
        });

        jtfemail.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtfemailKeyTyped(evt);
            }
        });

        jLabel16.setText("EMAIL");

        jtfdir.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtfdirFocusLost(evt);
            }
        });
        jtfdir.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtfdirKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtfdirKeyTyped(evt);
            }
        });

        jLabel17.setText("DIRECCION");

        jLabel20.setText("TIPO DE CLIENTE");

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

        jcbtdc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbtdcActionPerformed(evt);
            }
        });

        jLabel18.setText("CUIT");

        jtfcuit.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtfcuitFocusLost(evt);
            }
        });
        jtfcuit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtfcuitKeyTyped(evt);
            }
        });

        jLabel21.setText("LOCALIDAD");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 0, 0));
        jLabel2.setText("jLabel2");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 0, 0));
        jLabel3.setText("jLabel2");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 0, 0));
        jLabel4.setText("jLabel2");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 0, 0));
        jLabel5.setText("jLabel2");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 0, 0));
        jLabel6.setText("jLabel2");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 0, 0));
        jLabel7.setText("jLabel2");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 0, 0));
        jLabel8.setText("jLabel8");

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 0, 0));
        jLabel9.setText("jLabel9");

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
                        .addComponent(btnCancelar1))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jcbloc, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jLabel9)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jtfdir, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6)
                            .addComponent(jtftel, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jtfemail, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7)
                            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jcbtdc, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jLabel8)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jtfdni, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(23, 23, 23)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jtfcuit, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jtfnombre, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addComponent(jLabel13)
                                        .addGap(2, 2, 2)
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jtfdni, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jtfcuit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(jLabel18))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel3)))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel14)
                                .addGap(2, 2, 2)
                                .addComponent(jtfnombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel4)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(1, 1, 1)
                                .addComponent(jcbloc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel9))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel17)
                                .addGap(1, 1, 1)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jtftel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jtfdir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel7)
                                        .addComponent(jLabel6))))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel16)
                                    .addComponent(jLabel15))
                                .addGap(1, 1, 1)
                                .addComponent(jtfemail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(42, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel20)
                                .addGap(22, 22, 22))
                            .addComponent(jcbtdc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnguardar1)
                            .addComponent(btnCancelar1)))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jtfdniFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtfdniFocusLost
        jtfdni.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
        jLabel2.setVisible(false);
        boolean b = false;
        if(jtfdni.getText().isEmpty())
        {
            jtfdni.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
            jLabel2.setText("Debe ingresar un n° de DNI");
            jLabel2.setVisible(true);
            b = true;
        }
        if(b == false && (consultaVenta.existeClientePorDNI(Integer.parseInt(jtfdni.getText())) != null && bandera == 0))
        {
            jtfdni.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
            jLabel2.setText("DNI existente");
            jLabel2.setVisible(true);
        }
    }//GEN-LAST:event_jtfdniFocusLost

    private void jtfdniKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfdniKeyTyped
        jtfdni.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
        jLabel2.setVisible(false);
        char c=evt.getKeyChar();
        String dni = jtfdni.getText();
        e = true;    
        int longitud=jtfdni.getText().length();
        if(longitud<8){
          if(Character.isDigit(c) == false && c != '\b') {
              getToolkit().beep();
              
              evt.consume();
              
              jtfdni.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
              jLabel2.setText("Debe ingresar números");
              jLabel2.setVisible(true);
              
          }
          if(longitud == 0 && c == '0')
          {
              getToolkit().beep();
              
              evt.consume();
              
              jtfdni.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
              jLabel2.setText("No debe empezar con 0");
              jLabel2.setVisible(true);
          }
        }
        else
        {
              jtfdni.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
              jLabel2.setText("Longitud máxima alcanzada");
              jLabel2.setVisible(true);
              getToolkit().beep();
              evt.consume();

        }
    }//GEN-LAST:event_jtfdniKeyTyped

    private void jtfnombreFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtfnombreFocusLost
        jtfnombre.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
        jLabel4.setVisible(false);
        if(jtfnombre.getText().isEmpty())
        {
            jtfnombre.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
            jLabel4.setText("Debe ingresar un nombre");
            jLabel4.setVisible(true);
        }
    }//GEN-LAST:event_jtfnombreFocusLost

    private void jtfnombreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfnombreKeyPressed

    }//GEN-LAST:event_jtfnombreKeyPressed

    private void jtfnombreKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfnombreKeyReleased

    }//GEN-LAST:event_jtfnombreKeyReleased

    private void jtfnombreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfnombreKeyTyped
        jtfnombre.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
        jLabel4.setVisible(false);
          if(jtfnombre.getText().length() == 45) {
              getToolkit().beep();
              
              evt.consume();
              
              jtfnombre.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
              jLabel4.setText("Solo puede ingresar hasta 45 caracteres");
              jLabel4.setVisible(true);
              
          }
    }//GEN-LAST:event_jtfnombreKeyTyped

    private void jtftelFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtftelFocusLost

    }//GEN-LAST:event_jtftelFocusLost

    private void jtftelKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtftelKeyTyped
        jtftel.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
        jLabel6.setVisible(false);
        char c=evt.getKeyChar();
            
        
          if(Character.isDigit(c) == false && c != '\b' && c != '-') {
              getToolkit().beep();
              
              evt.consume();
              
              jtftel.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
              jLabel6.setText("Debe ingresar números o guiones");
              jLabel6.setVisible(true);
              
          }
        if(jtftel.getText().length() == 20) {
              getToolkit().beep();
              
              evt.consume();
              
              jtftel.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
              jLabel6.setText("Solo puede ingresar hasta 20 numeros");
              jLabel6.setVisible(true);
              
          }
    }//GEN-LAST:event_jtftelKeyTyped

    private void jtfemailKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfemailKeyTyped
        jtfemail.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
        jLabel7.setVisible(false);
        if(jtfemail.getText().length() == 45) {
            getToolkit().beep();

            evt.consume();

            jtfemail.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
            jLabel7.setText("Solo se puede ingresar hasta 45 caracteres");
            jLabel7.setVisible(true);

        }
    }//GEN-LAST:event_jtfemailKeyTyped

    private void jtfdirFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtfdirFocusLost
        jtfdir.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
        jLabel5.setVisible(false);
        if(jtfdir.getText().isEmpty())
        {
            jtfdir.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
            jLabel5.setText("Debe ingresar una direccion");
            jLabel5.setVisible(true);
        }
    }//GEN-LAST:event_jtfdirFocusLost

    private void jtfdirKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfdirKeyReleased

    }//GEN-LAST:event_jtfdirKeyReleased

    private void jtfdirKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfdirKeyTyped
        jtfdir.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
        jLabel5.setVisible(false);
        char c=evt.getKeyChar();
            
        
          if(Character.isLetterOrDigit(c) == false && c != '\b' && c != '.' && c != ' ' && c != '-') {
              getToolkit().beep();
              
              evt.consume();
              
              jtfdir.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
              jLabel5.setText("Debe ingresar números, letras o puntos");
              jLabel5.setVisible(true);
              
          }
        if(jtfdir.getText().length() == 45) {
              getToolkit().beep();
              
              evt.consume();
              
              jtfdir.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
              jLabel5.setText("Solo puede ingresar hasta 45 caracteres");
              jLabel5.setVisible(true);
              
          }
    }//GEN-LAST:event_jtfdirKeyTyped

    private void btnguardar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnguardar1ActionPerformed
        boolean d = false;
        boolean f = false;
        boolean j = false;
        jLabel2.setVisible(false);
        jLabel3.setVisible(false);
        jLabel4.setVisible(false);
        jLabel5.setVisible(false);
        jLabel6.setVisible(false);
        jLabel7.setVisible(false);
        jLabel8.setVisible(false);
        jLabel9.setVisible(false);
        jtfdni.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
        jtfcuit.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
        jtfnombre.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
        jtfdir.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
        jtftel.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
        jtfemail.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
        if(jcbtdc.getSelectedItem() != null && jcbloc.getSelectedItem() != null)
        {
            if(jcbtdc.getSelectedItem().toString().contentEquals("RESPONSABLE INSCRIPTO")||jcbtdc.getSelectedItem().toString().contentEquals("RESPONSABLE NO INSCRIPTO")||jcbtdc.getSelectedItem().toString().contentEquals("MONOTRIBUTISTA"))
        {    
        boolean b = this.verificarCuit(jtfcuit.getText());
        boolean c = this.comprobarCUIT(jtfcuit.getText());
        if(b == false|| (c==false && t==false) || jtfnombre.getText().isEmpty() || jtfdir.getText().isEmpty() || (consultaVenta.existeClientePorCUIT(jtfcuit.getText()) != null && bandera == 0))
        {
            if(jtfcuit.getText().isEmpty())
             {
                    jtfcuit.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
                    jLabel3.setText("Debe ingresar un N° de CUIT");
                    jLabel3.setVisible(true);
                    d = true;
                    f = true;
             }
            if(b==false)
            {
 
                 jtfcuit.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
                 jLabel3.setText("El CUIT debe tener el formato indicado");
                 jLabel3.setVisible(true);
                 jtfcuit.setText("");
                 d = true;
                 f = true;
            }
            if(jtfnombre.getText().isEmpty())
            {
                jtfnombre.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
                jLabel4.setText("Debe ingresar un nombre");
                jLabel4.setVisible(true);
            }
            if(jtfdir.getText().isEmpty())
            {
                jtfdir.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
                jLabel5.setText("Debe ingresar una dirección");
                jLabel5.setVisible(true);
            }
            if(c==false && f==false && t==false)
            {
 
                 jtfcuit.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
                 jLabel3.setText("El CUIT ingresado es inválido");
                 jLabel3.setVisible(true);
                 d = true;
                 
            }
            if(t == true && d == false && (consultaVenta.existeClientePorCUIT(jtfcuit.getText()) != null && bandera == 0))
            {
                 System.out.println(""+consultaVenta.existeClientePorCUIT(jtfcuit.getText()));
                 jtfcuit.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
                 jLabel3.setText("CUIT existente");
                 jLabel3.setVisible(true);
            }
        }
        else
        {
                System.out.println("HOLA");
                String cuit = jtfcuit.getText();
                String nombre = jtfnombre.getText().toUpperCase();
                String direccion = jtfdir.getText().toUpperCase();
                String telefono = jtftel.getText();
                String email =jtfemail.getText();
                localidad loc= (localidad) jcbloc.getSelectedItem();
                TipoCliente tc= (TipoCliente) jcbtdc.getSelectedItem();
                cliente = new Cliente(id,0,cuit,nombre,direccion,telefono,email,true,loc.getIdLocalidad(),tc.getIdTipoCliente());
                consultaVenta.agregarCliente(cliente);
                JOptionPane.showMessageDialog(this,"Cliente Agregado");
                bandera=0;
                j = true;
                limpiar2();
                this.dispose();
        }  
        }
        if(j == false && jcbtdc.getSelectedItem().toString().contentEquals("EXENTO"))
        {
            
            boolean c = this.verificarDni(jtfdni.getText());
            if(c == false || jtfnombre.getText().isEmpty() || jtfdir.getText().isEmpty() || (consultaVenta.existeClientePorDNI(Integer.parseInt(jtfdni.getText())) != null && bandera == 0))
        {
            
            if(c==false)
            {
                if(jtfdni.getText().isEmpty())
                {
                    System.err.println("Se repite de nuevo");
                    jtfdni.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
                    jLabel2.setText("Debe ingresar un n° de DNI");
                    jLabel2.setVisible(true);
                    d = true;
                }
                else
                {
                    if(jtfdni.getText().startsWith("0"))
                    {
                            jtfdni.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
                            jLabel2.setText("El DNI debe comenzar con 0");
                            jLabel2.setVisible(true);
                            jtfdni.setText("");
                    }
                    else
                    {
                            jtfdni.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
                            jLabel2.setText("El DNI debe tener 7 u 8 digitos");
                            jLabel2.setVisible(true);
                            jtfdni.setText("");
                    }   
                }
            }
            if(jtfnombre.getText().isEmpty())
            {
                jtfnombre.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
                jLabel4.setText("Debe ingresar un nombre");
                jLabel4.setVisible(true);
            }
            if(jtfdir.getText().isEmpty())
            {
                jtfdir.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
                jLabel5.setText("Debe ingresar una dirección");
                jLabel5.setVisible(true);
            }
            if(d == false && c == true && (consultaVenta.existeClientePorDNI(Integer.parseInt(jtfdni.getText())) != null && bandera == 0))
            {
                 jtfdni.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
                 jLabel2.setText("DNI existente");
                 jLabel2.setVisible(true);
            }
        }
        else
        {
            int dni = Integer.parseInt(jtfdni.getText());
            String nombre = jtfnombre.getText().toUpperCase();
            String direccion = jtfdir.getText().toUpperCase();
            String telefono = jtftel.getText();
            String email =jtfemail.getText();
            localidad loc= (localidad) jcbloc.getSelectedItem();
            TipoCliente tc= (TipoCliente) jcbtdc.getSelectedItem();
            cliente = new Cliente(id,dni,"",nombre,direccion,telefono,email,true,loc.getIdLocalidad(),tc.getIdTipoCliente());
            consultaVenta.agregarCliente(cliente);
            JOptionPane.showMessageDialog(this,"Cliente Agregado");
            bandera=0;
            limpiar2();
            this.dispose();
        }  
        }
        }
        else
        {
            if(jcbloc.getSelectedItem() == null)
            {
                jLabel9.setText("Ninguna localidad creada o seleccionada");
                jLabel9.setVisible(true);
            }
            if(jcbtdc.getSelectedItem() == null)
            {
                jLabel8.setText("Ningun tipo de cliente creado o seleccionado");
                jLabel8.setVisible(true);
            }
        }
        
    }//GEN-LAST:event_btnguardar1ActionPerformed

    private void btnCancelar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelar1ActionPerformed
        int respuesta=JOptionPane.showConfirmDialog(null, "¿Confirma la cancelación? \n Los datos no seran guardados","Advertencia", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            //confirmamos la eliminacion
            if(respuesta == 0)
            {
                this.dispose();
            }
    }//GEN-LAST:event_btnCancelar1ActionPerformed

    private void jcbtdcActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbtdcActionPerformed
        jLabel2.setVisible(false);
        jLabel3.setVisible(false);
        jLabel4.setVisible(false);
        jLabel5.setVisible(false);
        jLabel6.setVisible(false);
        jLabel7.setVisible(false);
        jtfdni.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
        jtfcuit.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
        jtfnombre.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
        jtfdir.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
        jtftel.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
        jtfemail.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
        if(jcbtdc.getSelectedItem() != null && jcbloc.getSelectedItem() != null)
        {
            if(jcbtdc.getSelectedItem().toString().contains("RESPONSABLE INSCRIPTO"))
            {
                jtfcuit.setEnabled(true);
                jtfcuit.setEditable(true);
                jtfdni.setEnabled(false);
                jtfdni.setEditable(false);
                jtfdni.setText("");
            }
            if(jcbtdc.getSelectedItem().toString().contains("RESPONSABLE NO INSCRIPTO"))
            {
                jtfcuit.setEnabled(true);
                jtfcuit.setEditable(true);
                jtfdni.setEnabled(false);
                jtfdni.setEditable(false);
                jtfdni.setText("");
            }
            if(jcbtdc.getSelectedItem().toString().contains("MONOTRIBUTISTA"))
            {
                jtfcuit.setEnabled(true);
                jtfcuit.setEditable(true);
                jtfdni.setEnabled(false);
                jtfdni.setEditable(false);
                jtfdni.setText("");
            }
            if(jcbtdc.getSelectedItem().toString().contains("EXENTO"))
            {
                jtfcuit.setEnabled(false);
                jtfcuit.setEditable(false);
                jtfcuit.setText("");
                jtfdni.setEnabled(true);
                jtfdni.setEditable(true);
            }
        }
    }//GEN-LAST:event_jcbtdcActionPerformed

    private void jtfcuitFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtfcuitFocusLost
        jtfcuit.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
        jLabel3.setVisible(false);
        t = false;
        boolean b = false;
        boolean f = false;
        boolean c = this.comprobarCUIT(jtfcuit.getText());
        boolean d = this.verificarCuit(jtfcuit.getText());
        String cuit = jtfcuit.getText();
        if(cuit.length() == 11 && cuit.charAt(2) != '-' && cuit.charAt(10) != '-')
        {
            String g = cuit.substring(0,2);
            String h = g.concat("-");
            cuit = h + cuit.substring(2, 11);
            g = cuit.substring(0,11);
            h = g.concat("-");
            cuit = h + cuit.substring(11, 12);
            jtfcuit.setText(cuit);
        }
        if(cuit.length() == 12 && cuit.charAt(2) != '-')
        {
            String g = cuit.substring(0,2);
            String h = g.concat("-");
            cuit = h + cuit.substring(2, 12);
            jtfcuit.setText(cuit);
        }
        if(cuit.length() == 12 && cuit.charAt(11) != '-')
        {
            String g = cuit.substring(0,11);
            String h = g.concat("-");
            cuit = h + cuit.substring(11, 12);
            jtfcuit.setText(cuit);
        }
        if(jtfcuit.getText().isEmpty())
        {
                    jtfcuit.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
                    jLabel3.setText("Debe ingresar un N° de CUIT");
                    jLabel3.setVisible(true);
                    b = true;
        }
            if(consultaVenta.existeClientePorCUIT(jtfcuit.getText()) != null && bandera == 0 && b == false)
            {
                 t = true;
                 jtfcuit.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
                 jLabel3.setText("CUIT existente");
                 jLabel3.setVisible(true);
            }
            if(c==false && f==false && t==false && d==true)
            {
 
                 jtfcuit.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
                 jLabel3.setText("El CUIT ingresado es inválido");
                 jLabel3.setVisible(true);
                 
            }
    }//GEN-LAST:event_jtfcuitFocusLost

    private void jtfcuitKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfcuitKeyTyped
       jtfcuit.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
        jLabel3.setVisible(false);
        e = true;    
        char car = evt.getKeyChar();
        boolean controletiqueta=false,controlcaracter=true;
        int longitud=jtfcuit.getText().length();
           if(controlcaracter!=false){
                jLabel3.setVisible(false);
                jtfcuit.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
                controlcaracter=false;
            }
           if(longitud <13) 
           {
               if(controletiqueta!=true){
                if((longitud==2||longitud==11)&& car!=KeyEvent.VK_BACK_SPACE)
                    jtfcuit.setText(jtfcuit.getText()+'-');
                    
                if(Character.isDigit(car) == false && car != '\b') {
                    getToolkit().beep();
              
                    evt.consume();
              
                    jtfcuit.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
                    jLabel3.setText("Debe ingresar números");
                    jLabel3.setVisible(true);
              
                }
                else{
                jLabel3.setVisible(false);
                jtfcuit.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
                controletiqueta=false;
                }
            }
           }
           else
           {
                jLabel3.setVisible(true);
                jLabel3.setText("Longitud máxima alcanzada");
                controletiqueta=true;
                getToolkit().beep();
                evt.consume();
           }

    }//GEN-LAST:event_jtfcuitKeyTyped

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
            java.util.logging.Logger.getLogger(NuevoCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NuevoCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NuevoCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NuevoCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                NuevoCliente dialog = new NuevoCliente(new javax.swing.JFrame(), true);
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
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JComboBox jcbloc;
    private javax.swing.JComboBox jcbtdc;
    private javax.swing.JTextField jtfcuit;
    private javax.swing.JTextField jtfdir;
    private javax.swing.JTextField jtfdni;
    private javax.swing.JTextField jtfemail;
    private javax.swing.JTextField jtfnombre;
    private javax.swing.JTextField jtftel;
    // End of variables declaration//GEN-END:variables
}
