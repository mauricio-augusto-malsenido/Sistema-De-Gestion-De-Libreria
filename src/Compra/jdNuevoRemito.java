package Compra;

import java.awt.Color;
import static java.awt.image.ImageObserver.WIDTH;
import java.util.Date;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import libreria.consultasLibreria;
/**
 *
 * @author SEBASTIAN
 */
public class jdNuevoRemito extends javax.swing.JDialog {

private ConsultaCompra oConsultaCompra;
private consultasLibreria oConsultaLibreria;
private remitoCompra pRemito,rc;
private int bandera,idPedidoCompra,IdRemito,id,idProveeedor,longitud;
private Date dateFremito;
private Date dateFrecepcion;
private java.sql.Date fremito,frecepcion;
private boolean control,controlES,controlFR,controlFRE,controlNR;
private TablaLineaRemito tblr=new TablaLineaRemito();
//BANDERA 0 NUEVO 
//BANDERA 1 MODIFICACION 
 public jdNuevoRemito(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
}
 
 
    public jdNuevoRemito(java.awt.Frame parent, boolean modal,int b,int idFc, int idRC) {
        super(parent, modal);
        initComponents();
        oConsultaLibreria = new consultasLibreria();
        oConsultaCompra= new ConsultaCompra();
        dateFremito=new Date(00, 0, 1);
        dateFrecepcion=new Date(00, 0, 1);
        bandera=b;
        labelNumRemito.setVisible(false);
        
        if(bandera==0){
        btnRegistrarRemito.setText("Guardar");
        btnSalirRemito.setText("Cancelar");
        idPedidoCompra=idFc;
        idProveeedor=oConsultaCompra.getPedidoPorId(idPedidoCompra).getIdProveedor();
        jtfNroPedido.setText(""+idPedidoCompra);
        jtfProveedor.setText(oConsultaCompra.getProveedorPorId(idProveeedor).getNombreProveedor());
        cargarTabla2(oConsultaCompra.getAllLineaPedido(idPedidoCompra));
        }
        if(bandera==1){
        jtfNroRd.setEditable(false);
        btnSalirRemito.setText("Cancelar");
        btnRegistrarRemito.setText("Guardar");
        IdRemito=idRC;
        idPedidoCompra=idFc;
        jtfNroPedido.setText(""+idPedidoCompra);
        rc=oConsultaCompra.getRemitoPorId(idRC);
        cargarCamposRemito(rc);
        cargarTabla(oConsultaCompra.getAllRemitoPorId(IdRemito));
        }
    }
    
    private void cargarTabla2(List<LineaPedido> li) {
        List<LineaPedido> lista =li;
        String[] columnNames = {"Id_Libro","Titulo","Cantidad_Remito","Cantidad_Recibida"};
        int[] anchos = {20,100,20,20};
        
        Object[][] data = new Object[lista.size()][columnNames.length];
         
        
         for (int i = 0; i < lista.size(); i++) {
            data[i][0] = lista.get(i).getIdLibro();
            data[i][1] = oConsultaLibreria.getLibroPorId(lista.get(i).getIdLibro()).getTitulo();
            data[i][2] = lista.get(i).getCantidad()- lista.get(i).getCantRecibida();
            data[i][3] = 0;
        }
       DefaultTableModel dftm = new DefaultTableModel(data, columnNames)
                {
		//metodo para que las celdas del jtable sean no-editables	
                    @Override
			public boolean isCellEditable(int row, int column) {
                            if (column==2 || column == 3)
                            return true;		
                            else
                            return false;
			}
		};
       jtableLineaRemito.setModel(dftm);
       for(int i = 0; i < jtableLineaRemito.getColumnCount(); i++) {
        jtableLineaRemito.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
    }
  
    private void cargarTabla(List<remitoCompraLibro> li) {
        List<remitoCompraLibro> lista =li;
        
        String[] columnNames = {"Id","Cod_Libro","Titulo","Cantidad Remito","Cantidad Recibida"};
        int[] anchos = {40,40,100,40,40};
        
        
        Object[][] data = new Object[lista.size()][columnNames.length];
         
        
         for (int i = 0; i < lista.size(); i++) {
            data[i][0] = lista.get(i).getIdRemitoCompraLibro();
            data[i][1] = lista.get(i).getIdLibro();
            data[i][2] = oConsultaLibreria.getLibroPorId(lista.get(i).getIdLibro()).getTitulo();
            data[i][3] = lista.get(i).getCantidadRemito();
            data[i][4] = lista.get(i).getCantidadRecibida();
         }
       DefaultTableModel dftm = new DefaultTableModel(data, columnNames)
                {
		//metodo para que las celdas del jtable sean no-editables	
                    @Override
			public boolean isCellEditable(int row, int column) {
                         if (column == 3 || column == 4)
                            return true;		
                            else
                            return false;
                       }
		};
       jtableLineaRemito.setModel(dftm);
       for(int i = 0; i < jtableLineaRemito.getColumnCount(); i++) {

        jtableLineaRemito.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);

        }
    }

    private void CapturarLineaRemito(int idUR){
       IdRemito=idUR; 
       for(int i=0; i<jtableLineaRemito.getRowCount(); i++) //recorro las filas
       {
        String[] fila= new String[3];//almaceno los valores del registro seleccionado en el string "fila"
        fila[0]=""+jtableLineaRemito.getModel().getValueAt(i, 0);//ID LIBRO
        fila[1]=""+jtableLineaRemito.getModel().getValueAt(i, 2);//CANTIDAD REMITO
        fila[2]=""+jtableLineaRemito.getModel().getValueAt(i, 3);//CANTIDAD RECIBIDA
        
       remitoCompraLibro fcl= new remitoCompraLibro(
                0,//idlinea
                Integer.parseInt(fila[1]),//cANTIDAD rmeito
                Integer.parseInt(fila[2]),//cantidad recibida
                Integer.parseInt(fila[0]),//IdLibros
                IdRemito//IDREmito
                );
       tblr.getLineas().add(fcl);
       }
    }
     
     private void CapturarLineaRemitoMOD(int idUR){
       IdRemito=idUR; 
       for(int i=0; i<jtableLineaRemito.getRowCount(); i++) //recorro las filas
       {
        String[] fila= new String[4];//almaceno los valores del registro seleccionado en el string "fila"
        fila[0]=""+jtableLineaRemito.getModel().getValueAt(i, 0);//ID LINEA
        fila[1]=""+jtableLineaRemito.getModel().getValueAt(i, 1);//ID LIBRO
        fila[2]=""+jtableLineaRemito.getModel().getValueAt(i, 3);//CANTIDAD REMITO
        fila[3]=""+jtableLineaRemito.getModel().getValueAt(i, 4);//CANTIDAD RECIBIDA
        
       remitoCompraLibro fcl= new remitoCompraLibro(
                Integer.parseInt(fila[0]),//idlinea
                Integer.parseInt(fila[2]),//cANTIDAD rmeito
                Integer.parseInt(fila[3]),//cantidad recibida
                Integer.parseInt(fila[1]),//IdLibros
                IdRemito//IDREmito
                );
       tblr.getLineas().add(fcl);
       }
    }
     
     private  void cargarCamposRemito(remitoCompra remitoCompra){
        proveedor p=oConsultaCompra.getProveedorPorId(remitoCompra.getIdProveedor());
        idProveeedor=p.getIdProveedor();
        fremito=remitoCompra.getFechaRemitoCompra();
        dateFremito=new java.util.Date(fremito.getTime());
        frecepcion=remitoCompra.getFechaRecepcionCompra();
        dateFrecepcion=new java.util.Date(frecepcion.getTime());
        
        id=remitoCompra.getIdRemitoCompra();
        jtfNroRd.setText(""+remitoCompra.getNroRemitoProv());
        jtfProveedor.setText(oConsultaCompra.getProveedorPorId(p.getIdProveedor()).getNombreProveedor());
        jdateFcompra.setDate(dateFremito);
        jdateFPago.setDate(dateFrecepcion);
    }
   
     private remitoCompra capturarCampos(){

     String estado;
     if(comprobarEstadoRemito()==false){
     estado="INCOMPLETO";
     }else estado="COMPLETO";
     
        if(jdateFcompra.getDate() != null)
           {dateFremito=jdateFcompra.getDate();
           fremito=new java.sql.Date(dateFremito.getTime());
           }
                if(jdateFPago.getDate() != null)
                   {dateFrecepcion=jdateFPago.getDate();
                   frecepcion=new java.sql.Date(dateFrecepcion.getTime());
                   }else{
                        dateFrecepcion=new Date(00, 0, 1);
                        frecepcion=new java.sql.Date(dateFrecepcion.getTime());}
          
                pRemito= new remitoCompra(
                id,
                Integer.parseInt(jtfNroRd.getText()),
                fremito,
                frecepcion,
                idProveeedor,
                idPedidoCompra,
                estado
                );
        return  pRemito;
        }
      
     public boolean comprobarNulos(){
        if(jdateFcompra.getDate()==null)
        {controlFR=true;
        JOptionPane.showMessageDialog(null, "Debe registrar la fecha de remito", "ADVERTENCIA", WIDTH);
        }
        else{
        controlFR=false;
        }
        return controlFR;
    }
     
     public boolean comprobarFechaRecepcionRemito(){
        if(jdateFPago.getDate()==null)
        {controlFRE=true;
        JOptionPane.showMessageDialog(null, "Debe registrar la fecha de recepcion", "ADVERTENCIA", WIDTH);
        }
        else{
        controlFRE=false;
        }
        return controlFRE;
    }
     
     public boolean comprobarTablaVacia(){
        if(tblr.getLineas().isEmpty())
        {control=true;
        JOptionPane.showMessageDialog(null, "Debe Registrar al menos un Libro", "ADVERTENCIA", WIDTH);
        
        }
        else{
        control=false;
        }
        return control;
    }
     
     public boolean comprobarNRCVacia(){
        if(jtfNroRd.getText().equals(""))
        {controlNR=true;
        jtfNroRd.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
        labelNumRemito.setText("Debe ingresar numero de remito");
        labelNumRemito.setVisible(true);
        }
        else{
        controlNR=false;
        }
        return controlNR;
    }
     
     public boolean comprobarEstadoRemito(){
         for (int i = 0; i < tblr.getLongitud(); i++) {
             if(tblr.getLineas().get(i).getCantidadRemito()==tblr.getLineas().get(i).getCantidadRecibida())
             {
             controlES=true;
             }
             else{
             controlES=false;
             }
         }
        return controlES;
    }
     
     
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jtfNroRd = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jdateFcompra = new com.toedter.calendar.JDateChooser();
        jLabel2 = new javax.swing.JLabel();
        jdateFPago = new com.toedter.calendar.JDateChooser();
        jLabel8 = new javax.swing.JLabel();
        jtfNroPedido = new javax.swing.JTextField();
        jtfProveedor = new javax.swing.JTextField();
        labelNumRemito = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtableLineaRemito = new javax.swing.JTable();
        btnRegistrarRemito = new javax.swing.JButton();
        btnSalirRemito = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("REMITO");
        setResizable(false);

        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setText("N°Remito");

        jtfNroRd.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jtfNroRd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtfNroRdActionPerformed(evt);
            }
        });
        jtfNroRd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtfNroRdKeyTyped(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setText("Fecha Remito");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel5.setText("Proveedor");

        jdateFcompra.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jdateFcompraMouseClicked(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setText("Fecha Recepcion");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel8.setText("N° Pedido");

        jtfNroPedido.setEditable(false);

        jtfProveedor.setEditable(false);

        labelNumRemito.setForeground(new java.awt.Color(204, 0, 0));
        labelNumRemito.setText("jLabel11");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(jdateFcompra, javax.swing.GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jdateFPago, javax.swing.GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jtfNroRd, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel8)
                                .addGap(18, 18, 18)
                                .addComponent(jtfNroPedido, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel5)
                                .addGap(18, 18, 18)
                                .addComponent(jtfProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(labelNumRemito, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(475, 475, 475)))))
                .addContainerGap(143, Short.MAX_VALUE))
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jdateFPago, jdateFcompra});

        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jtfNroRd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(jtfNroPedido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(jtfProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(labelNumRemito)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jdateFPago, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jdateFcompra, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(20, 20, 20))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), "Detalle", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        jtableLineaRemito.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "id", "Cod_Libro", "Cantidad", "Cantidad Recibida"
            }
        ));
        jScrollPane1.setViewportView(jtableLineaRemito);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 849, Short.MAX_VALUE)
                .addGap(9, 9, 9))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 316, Short.MAX_VALUE)
                .addContainerGap())
        );

        btnRegistrarRemito.setText("Registrar Remito");
        btnRegistrarRemito.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarRemitoActionPerformed(evt);
            }
        });

        btnSalirRemito.setText("Salir");
        btnSalirRemito.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirRemitoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnRegistrarRemito)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnSalirRemito))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnRegistrarRemito)
                    .addComponent(btnSalirRemito))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnRegistrarRemitoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarRemitoActionPerformed
      if(bandera==0){
            if(comprobarNRCVacia()==false && comprobarNulos()==false && comprobarFechaRecepcionRemito()==false){
                    oConsultaCompra.agregarRemito(capturarCampos());
                    CapturarLineaRemito(oConsultaCompra.getIdUltimoRemito());
                   
                    for (int i = 0; i < tblr.getLongitud(); i++) {
                        int stockactual=oConsultaLibreria.getLibroPorId(tblr.getLineas().get(i).getIdLibro()).getStock();
                        System.out.println("ACTUAL:"+stockactual);
                        int recibido=tblr.getLineas().get(i).getCantidadRecibida();
                        System.out.println("RECIBIDA:"+recibido);
                        int stocknuevo=stockactual+recibido;
                        System.out.println("NUEVO:"+stocknuevo);
                        oConsultaCompra.agregarLineaRemito(tblr.getLineas().get(i));
                        oConsultaLibreria.modificarStockLibro(stocknuevo, tblr.getLineas().get(i).getIdLibro());
                    }
                    JOptionPane.showMessageDialog(null, "Remito Registrado", "ADVERTENCIA", WIDTH);
                    this.dispose();}
        }
        if(bandera==1){
            if(comprobarNRCVacia()==false && comprobarNulos()==false && comprobarFechaRecepcionRemito()==false){
             oConsultaCompra.modificarRemito(capturarCampos());
            CapturarLineaRemitoMOD(IdRemito);
            for (int i = 0; i < tblr.getLongitud(); i++) {
            int stockactual=oConsultaLibreria.getLibroPorId(tblr.getLineas().get(i).getIdLibro()).getStock();
            System.out.println("ACTUAL:"+stockactual);
            int recibido=tblr.getLineas().get(i).getCantidadRecibida();
            System.out.println("RECIBIDA:"+recibido);
            int stocknuevo=stockactual+recibido;
            System.out.println("NUEVO:"+stocknuevo);
            oConsultaCompra.modificarLineaRemito(tblr.getLineas().get(i));
            oConsultaLibreria.modificarStockLibro(stocknuevo, tblr.getLineas().get(i).getIdLibro());
            }
            JOptionPane.showMessageDialog(null, "Remito Modificado", "ADVERTENCIA", WIDTH);
            this.dispose();
            }
        }
    }//GEN-LAST:event_btnRegistrarRemitoActionPerformed

    private void btnSalirRemitoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirRemitoActionPerformed
    if(bandera==1){
        int respuesta=JOptionPane.showConfirmDialog(null, "Se perderan todos los cambios realizados \n ¿Desea Salir?","Advertencia", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if(respuesta == 0)
                    {this.dispose();}
    }
    else{
     int respuesta=JOptionPane.showConfirmDialog(null, "El remito no se ha registrado \n ¿Desea salir?","Advertencia", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
          if(respuesta == 0){this.dispose();}
     }
    }//GEN-LAST:event_btnSalirRemitoActionPerformed

    private void jdateFcompraMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jdateFcompraMouseClicked
        
    }//GEN-LAST:event_jdateFcompraMouseClicked

    private void jtfNroRdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtfNroRdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtfNroRdActionPerformed

    private void jtfNroRdKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfNroRdKeyTyped
        char car = evt.getKeyChar();
        longitud=jtfNroRd.getText().length();
            if(controlNR==false){
           labelNumRemito.setVisible(false);
           jtfNroRd.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
           controlNR=false;
            }
            if(longitud >=44){
            labelNumRemito.setText("Longitud maxima 45 caracteres");
            labelNumRemito.setVisible(true);
            }else{labelNumRemito.setVisible(false);}
            
        
    }//GEN-LAST:event_jtfNroRdKeyTyped

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
            java.util.logging.Logger.getLogger(jdNuevoRemito.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(jdNuevoRemito.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(jdNuevoRemito.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(jdNuevoRemito.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                jdNuevoRemito dialog = new jdNuevoRemito(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnRegistrarRemito;
    private javax.swing.JButton btnSalirRemito;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private com.toedter.calendar.JDateChooser jdateFPago;
    private com.toedter.calendar.JDateChooser jdateFcompra;
    private javax.swing.JTable jtableLineaRemito;
    private javax.swing.JTextField jtfNroPedido;
    private javax.swing.JTextField jtfNroRd;
    private javax.swing.JTextField jtfProveedor;
    private javax.swing.JLabel labelNumRemito;
    // End of variables declaration//GEN-END:variables
}
