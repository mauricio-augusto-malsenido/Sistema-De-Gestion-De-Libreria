package Compra;


import Venta.BusquedaFactura;
import Venta.ListaFacturas;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import static java.awt.image.ImageObserver.WIDTH;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import libreria.Libro;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

/**
 *
 * @author SEBASTIAN
 */
public class jpListaPedidos extends javax.swing.JPanel {
private ConsultaCompra oConsultaCompra;
private TableRowSorter<TableModel> sorter;
private int v=0,indiceModelo=0, bandera;
private java.util.Date dateDes,dateHas;
private java.sql.Date fedes,fehas;
private float subtotal = 0;
private boolean r = false;
private List <PedidoCompra> facturasVentas;

    public jpListaPedidos() {
        initComponents();
        jTfBuscarFactura.setVisible(false);
    }

    public jpListaPedidos(int b) {
        initComponents();
         bandera=b;
         jTfBuscarFactura.setVisible(false);
        oConsultaCompra = new ConsultaCompra();
        CargarComboLibros();
        AutoCompleteDecorator.decorate(this.comboLibro1);
        
        if(bandera==1){
        List<PedidoCompra> lst = oConsultaCompra.getAllPedidos();
        cargarTabla(lst);
        AccionDobleClick(1);
        }
        limpiar();
        calcular();
        TextfieldBuscar();
    }
    
    private void filtrar() {
        RowFilter<TableModel, Object> rf = null;
        int indiceColumnaTabla = 0;
        
        
            switch (jcbbuscar.getSelectedIndex()) {
            case 0: indiceColumnaTabla = 0;break;//por IdPedido
            case 1:indiceColumnaTabla = 1;break;//Proveedor
            //case 2: indiceColumnaTabla = 7;break;//por estado
        
        }
       
        try {
            
            String buscarTexto=jTfBuscarFactura.getText().toUpperCase();
            rf = RowFilter.regexFilter(buscarTexto, indiceColumnaTabla);
            
        } 
        catch (java.util.regex.PatternSyntaxException e) {
        }
        sorter.setRowFilter(rf);
        
        }
    
    private void TextfieldBuscar(){
     
     jTfBuscarFactura.getDocument().addDocumentListener(
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
    
    private void AccionCombo() {
        final ItemListener changeClick = new ItemListener()
        {
            @Override
            public void itemStateChanged(ItemEvent e) 
            {   //BUSCAR POR FECHA
                
                    if(jcbbuscar.getSelectedIndex()==2 )
                        {
                        //jpBuscarPorFEcha.setVisible(true);
                        jpBuscar.setVisible(false);
                        //btnBuscarPorFEcha.setVisible(true);
                        cargarTabla(oConsultaCompra.getAllPedidos());
                         }
                        else{
                        //jpBuscarPorFEcha.setVisible(false);
                        jpBuscar.setVisible(true);
                        //btnBuscarPorFEcha.setVisible(false);
                        jTfBuscarFactura.setText("");
                        cargarTabla(oConsultaCompra.getAllPedidos());
                        }
   
        }
        };
        this.jcbbuscar.addItemListener(changeClick);
    } 
     
    //BUSCAR
    private void cargarTabla(List<PedidoCompra> lm) {
        List<PedidoCompra> lista =lm;
        String[] columnNames = {"N° Pedido","Proveedor","Fecha","Total","Estado"};
        Object[][] data = new Object[lista.size()][columnNames.length];
        int[] anchos = {10,50,60,30,30};
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        
            for (int i = 0; i < lista.size(); i++) {
           
            data[i][0] = lista.get(i).getIdPedidoCompra();
            data[i][1] = oConsultaCompra.getProveedorPorId(lista.get(i).getIdProveedor()).getNombreProveedor().toUpperCase();
            String f1 = df.format(lista.get(i).getFechaPedido());
            data[i][2] = f1;
            data[i][3] = lista.get(i).getTotalPedido();
            data[i][4] = lista.get(i).getEstado();
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
       
       jTablePedidos.setModel(dftm);
       
       for(int i = 0; i < jTablePedidos.getColumnCount(); i++) {

        jTablePedidos.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);

        }
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        for(int i=0;i<jTablePedidos.getColumnCount();i++)
        {
            jTablePedidos.getColumnModel().getColumn(i).setCellRenderer(tcr);
        }

       // sorter = new TableRowSorter<TableModel>(dftm);
        //jTablePedidos.setRowSorter(sorter);
        jTablePedidos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }
    
    private int getIdPedidoFila( int im){
        String[] fila= new String[1];//almaceno los valores del registro seleccionado en el string "fila"
        fila[0]=""+jTablePedidos.getModel().getValueAt(im, 0);
        
        int idFac=Integer.parseInt(fila[0]);
        
        System.out.println(idFac);
        return  idFac;
    }
    
    public static String getFechaActual(java.sql.Date ahora) {
        SimpleDateFormat formateador = new SimpleDateFormat("dd-MM-yyyy");
        return formateador.format(ahora);
    }
    
    private void AccionDobleClick(final int ban){
     jTablePedidos.addMouseListener(new MouseAdapter() 
        {
           @Override
           public void mouseClicked(MouseEvent e) 
           {
             if(e.getClickCount()==2){
              int fila = jTablePedidos.rowAtPoint(e.getPoint());
              int columna = jTablePedidos.columnAtPoint(e.getPoint());
                     if ((fila > -1) && (columna > -1))
                     {
                        v=jTablePedidos.getSelectedRow();
                        indiceModelo = jTablePedidos.convertRowIndexToModel (v);
                        jdPedido jda= new jdPedido(null, true,getIdPedidoFila(indiceModelo), 1);
                        jda.setVisible(true);
                        jda.setLocationRelativeTo(jPanelBase);
                        dateDes=jDateChooser3.getDate();
                        dateHas=jDateChooser4.getDate();
                        facturasVentas = new ArrayList<>();
                        if(r==true){
                        if(dateDes != null && dateHas != null)
                        {
                        if(jcbbuscar.getSelectedItem().toString().contentEquals("LIBRO"))
                        {
                            fedes=new java.sql.Date(dateDes.getTime());
                            fehas=new java.sql.Date(dateHas.getTime());
                            Libro libro= (Libro) comboLibro1.getSelectedItem();
                            facturasVentas = oConsultaCompra.getAllPedidoPorFechayLibro(libro,fedes, fehas);
                            cargarTabla(facturasVentas);
                            calcular();
                        }
                        if(jcbbuscar.getSelectedItem().toString().contentEquals("ESTADO"))
                        {
                            fedes=new java.sql.Date(dateDes.getTime());
                            fehas=new java.sql.Date(dateHas.getTime());
                            String estado= comboLibro1.getSelectedItem().toString();
                            facturasVentas = oConsultaCompra.getAllPedidoPorFechayEstado(estado,fedes, fehas);
                            cargarTabla(facturasVentas);
                            calcular();
                        }
                        if(jcbbuscar.getSelectedItem().toString().contentEquals("PROVEEDOR"))
                        {
                            fedes=new java.sql.Date(dateDes.getTime());
                            fehas=new java.sql.Date(dateHas.getTime());
                            proveedor cli= (proveedor) comboLibro1.getSelectedItem();
                            facturasVentas = oConsultaCompra.getAllPedidoPorFechayProveedor(cli,fedes, fehas);
                            cargarTabla(facturasVentas);
                            calcular();
                        }
                        }
                        else
                        {
                            if(dateDes == null && dateHas == null)
                            {
                            if(jcbbuscar.getSelectedItem().toString().contentEquals("PROVEEDOR"))
                            {
                                proveedor cli= (proveedor) comboLibro1.getSelectedItem();
                                facturasVentas = oConsultaCompra.getAllPedidoPorProveedor(cli);
                                cargarTabla(facturasVentas);
                                calcular();
                            }
                            if(jcbbuscar.getSelectedItem().toString().contentEquals("ESTADO"))
                            {
                                String estado= comboLibro1.getSelectedItem().toString();
                                facturasVentas = oConsultaCompra.getAllPedidoPorEstado(estado);
                                cargarTabla(facturasVentas);
                                calcular();
                            }
                            if(jcbbuscar.getSelectedItem().toString().contentEquals("LIBRO"))
                            {
                                Libro libro= (Libro) comboLibro1.getSelectedItem();
                                facturasVentas = oConsultaCompra.getAllPedidoPorLibro(libro);
                                cargarTabla(facturasVentas);
                                calcular();
                            }
                        }
                        }
                        }
                        else
                        {
                        cargarTabla(oConsultaCompra.getAllPedidos());
                        calcular();
                        }
                     }
             }
           }
        });
    
    }
    private void CargarComboLibros(){
    comboLibro1.removeAllItems();
    if(jcbbuscar.getSelectedItem().toString().contentEquals("LIBRO"))
    {
        List<Libro> lista = oConsultaCompra.getAllLibro(true);

        if(lista.isEmpty())
        {
            comboLibro1.addItem("Ningun libro cargado");
        }
        else
        {
         for (int i = 0; i < lista.size(); i++) {
             Libro l=lista.get(i);
            comboLibro1.addItem(l);
            Libro objeto=(Libro) comboLibro1.getItemAt(1);

            }   
        }
    }
    if(jcbbuscar.getSelectedItem().toString().contentEquals("PROVEEDOR"))
    {
        List<proveedor> lista = oConsultaCompra.getAllProveedorHab();

        if(lista.isEmpty())
        {
            comboLibro1.addItem("Ningun proveedor cargado");
        }
        else
        {
         for (int i = 0; i < lista.size(); i++) {
            proveedor l=lista.get(i);
            comboLibro1.addItem(l);
            proveedor objeto=(proveedor) comboLibro1.getItemAt(1);

            }   
        }
    }
    if(jcbbuscar.getSelectedItem().toString().contentEquals("ESTADO"))
    {
        comboLibro1.addItem("ESPERA");
        comboLibro1.addItem("COMPLETO");
        comboLibro1.addItem("INCOMPLETO");
    }
     }
    
    private void calcular()
    {
        subtotal = 0;
        for(int i = 0; i < jTablePedidos.getRowCount(); i++) {
            
            subtotal = subtotal + Float.parseFloat(String.valueOf(jTablePedidos.getValueAt(i, 3)));
            
        }
       jtfImporte.setText(""+subtotal);
    }
    
    public void limpiar()
    {
        jTfBuscarFactura.setEnabled(true);
        jTfBuscarFactura.setEditable(true);
        jDateChooser3.setEnabled(true);
        jDateChooser4.setEnabled(true);
        comboLibro1.setEnabled(true);
        jcbbuscar.setEnabled(true);
        jbbuscar.setEnabled(true);
        jButton1.setEnabled(true);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jpmenu = new javax.swing.JPopupMenu();
        jmiEliminar = new javax.swing.JMenuItem();
        jPanelBase = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTablePedidos = new javax.swing.JTable();
        jPanelBusqueda = new javax.swing.JPanel();
        jpBuscar = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jcbbuscar = new javax.swing.JComboBox();
        jTfBuscarFactura = new javax.swing.JTextField();
        comboLibro1 = new org.jdesktop.swingx.JXComboBox();
        jLabel7 = new javax.swing.JLabel();
        jDateChooser3 = new com.toedter.calendar.JDateChooser();
        jLabel11 = new javax.swing.JLabel();
        jDateChooser4 = new com.toedter.calendar.JDateChooser();
        jbbuscar = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jLabelTotalOrdenes = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jtfImporte = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();

        jmiEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/eliminar.png"))); // NOI18N
        jmiEliminar.setText("Eliminar");
        jmiEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiEliminarActionPerformed(evt);
            }
        });
        jpmenu.add(jmiEliminar);

        jPanelBase.setLayout(new java.awt.CardLayout());

        jTablePedidos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "id", "Proveedor", "Fecha_Pedido"
            }
        ));
        jTablePedidos.setComponentPopupMenu(jpmenu);
        jScrollPane1.setViewportView(jTablePedidos);

        jPanelBusqueda.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.black, java.awt.Color.black, java.awt.Color.black, java.awt.Color.black));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setText("Buscar:");

        jcbbuscar.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "PROVEEDOR", "ESTADO", "LIBRO" }));
        jcbbuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbbuscarActionPerformed(evt);
            }
        });

        comboLibro1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboLibro1ActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setText("Desde:");

        jDateChooser3.setPreferredSize(new java.awt.Dimension(95, 15));

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel11.setText("Hasta:");

        jDateChooser4.setPreferredSize(new java.awt.Dimension(95, 17));

        jbbuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/lupa.png"))); // NOI18N
        jbbuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbbuscarActionPerformed(evt);
            }
        });

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/actualizar.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpBuscarLayout = new javax.swing.GroupLayout(jpBuscar);
        jpBuscar.setLayout(jpBuscarLayout);
        jpBuscarLayout.setHorizontalGroup(
            jpBuscarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpBuscarLayout.createSequentialGroup()
                .addGap(64, 64, 64)
                .addComponent(jcbbuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jpBuscarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jpBuscarLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(150, 150, 150)
                    .addComponent(jTfBuscarFactura, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(comboLibro1, javax.swing.GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jDateChooser3, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jDateChooser4, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jbbuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jButton1)
                    .addContainerGap()))
        );
        jpBuscarLayout.setVerticalGroup(
            jpBuscarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpBuscarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jcbbuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jpBuscarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jpBuscarLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jpBuscarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jbbuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jDateChooser3, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(comboLibro1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jpBuscarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jTfBuscarFactura, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jDateChooser4, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel11)
                        .addComponent(jLabel7))
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout jPanelBusquedaLayout = new javax.swing.GroupLayout(jPanelBusqueda);
        jPanelBusqueda.setLayout(jPanelBusquedaLayout);
        jPanelBusquedaLayout.setHorizontalGroup(
            jPanelBusquedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelBusquedaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jpBuscar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelBusquedaLayout.setVerticalGroup(
            jPanelBusquedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jButton2.setText("Generar Reporte De Compras");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jtfImporte.setEditable(false);

        jLabel12.setText("Importe Total $");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanelBusqueda, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 982, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelTotalOrdenes, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton2)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jtfImporte, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanelBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(jtfImporte, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelTotalOrdenes, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7))
        );

        jPanelBase.add(jPanel1, "card2");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelBase, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelBase, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jmiEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiEliminarActionPerformed
         v=jTablePedidos.getSelectedRow();
         indiceModelo = jTablePedidos.convertRowIndexToModel (v);
         
         if(oConsultaCompra.getAllRemitoPorIdFactura(getIdPedidoFila(indiceModelo)).isEmpty())
         {
             int respuesta=JOptionPane.showConfirmDialog(null, "Se perderan todos los datos de este pedido \n¿Desea eliminarlo?","Advertencia", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if(respuesta == 0)
                {
                    ArrayList<LineaPedido>lineape=(ArrayList<LineaPedido>) oConsultaCompra.getAllLineaPedido(getIdPedidoFila(indiceModelo));
                    for(int i=0; i<lineape.size();i++)
                    {
                        oConsultaCompra.eliminaLineaPedido(lineape.get(i).getIdLineaPedido());
                    }
                    
                    oConsultaCompra.eliminaPedido(getIdPedidoFila(indiceModelo));
                    cargarTabla(oConsultaCompra.getAllPedidos());
                }
         }
         else{
              JOptionPane.showMessageDialog(this,"No se puede eliminar este pedido");
         
         }
        
    }//GEN-LAST:event_jmiEliminarActionPerformed

    private void jcbbuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbbuscarActionPerformed
        if(jcbbuscar.getSelectedItem().toString().contentEquals("PROVEEDOR"))
        {
            CargarComboLibros();
            comboLibro1.setSelectedIndex(0);
            jTfBuscarFactura.setVisible(false);
            comboLibro1.setVisible(true);
            jbbuscar.setVisible(true);
            jButton1.setVisible(true);
            jDateChooser3.setVisible(true);
            jDateChooser4.setVisible(true);
            jLabel7.setVisible(true);
            jLabel11.setVisible(true);
        }
        if(jcbbuscar.getSelectedItem().toString().contentEquals("ESTADO"))
        {
            CargarComboLibros();
            comboLibro1.setSelectedIndex(0);
            jTfBuscarFactura.setVisible(false);
            comboLibro1.setVisible(true);
            jbbuscar.setVisible(true);
            jButton1.setVisible(true);
            jDateChooser3.setVisible(true);
            jDateChooser4.setVisible(true);
            jLabel7.setVisible(true);
            jLabel11.setVisible(true);
        }
        if(jcbbuscar.getSelectedItem().toString().contentEquals("LIBRO"))
        {
            CargarComboLibros();
            comboLibro1.setSelectedIndex(0);
            jTfBuscarFactura.setVisible(false);
            comboLibro1.setVisible(true);
            jbbuscar.setVisible(true);
            jButton1.setVisible(true);
            jDateChooser3.setVisible(true);
            jDateChooser4.setVisible(true);
            jLabel7.setVisible(true);
            jLabel11.setVisible(true);
        }
    }//GEN-LAST:event_jcbbuscarActionPerformed

    private void comboLibro1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboLibro1ActionPerformed
        /*if(jcbbuscar.getSelectedItem().toString().contentEquals("PROVEEDOR"))
        {
            proveedor cli= (proveedor) comboLibro1.getSelectedItem();
            facturasVentas = oConsultaCompra.getAllFacturaVentaPorproveedor(cli);
            cargarTabla(facturasVentas);
            calcular();
        }
        if(jcbbuscar.getSelectedItem().toString().contentEquals("LIBRO"))
        {
            Libro libro= (Libro) comboLibro1.getSelectedItem();
            facturasVentas = oConsultaCompra.getAllFacturaVentaPorLibro(libro);
            cargarTabla(facturasVentas);
            calcular();
        }*/
    }//GEN-LAST:event_comboLibro1ActionPerformed

    private void jbbuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbbuscarActionPerformed
        r = true;
        dateDes=jDateChooser3.getDate();
        dateHas=jDateChooser4.getDate();
        facturasVentas = new ArrayList<>();
        if(dateDes != null && dateHas != null)
        {
            if(jcbbuscar.getSelectedItem().toString().contentEquals("LIBRO"))
            {
                fedes=new java.sql.Date(dateDes.getTime());
                fehas=new java.sql.Date(dateHas.getTime());
                Libro libro= (Libro) comboLibro1.getSelectedItem();
                facturasVentas = oConsultaCompra.getAllPedidoPorFechayLibro(libro,fedes, fehas);
                cargarTabla(facturasVentas);
                calcular();
            }
            if(jcbbuscar.getSelectedItem().toString().contentEquals("PROVEEDOR"))
            {
                fedes=new java.sql.Date(dateDes.getTime());
                fehas=new java.sql.Date(dateHas.getTime());
                proveedor cli= (proveedor) comboLibro1.getSelectedItem();
                facturasVentas = oConsultaCompra.getAllPedidoPorFechayProveedor(cli,fedes, fehas);
                cargarTabla(facturasVentas);
                calcular();
            }
            if(jcbbuscar.getSelectedItem().toString().contentEquals("ESTADO"))
            {
                fedes=new java.sql.Date(dateDes.getTime());
                fehas=new java.sql.Date(dateHas.getTime());
                String estado= comboLibro1.getSelectedItem().toString();
                facturasVentas = oConsultaCompra.getAllPedidoPorFechayEstado(estado,fedes, fehas);
                cargarTabla(facturasVentas);
                calcular();
            }
        }
        else
        {
            if(dateDes == null && dateHas == null)
            {
                if(jcbbuscar.getSelectedItem().toString().contentEquals("PROVEEDOR"))
                {
                    proveedor cli= (proveedor) comboLibro1.getSelectedItem();
                    facturasVentas = oConsultaCompra.getAllPedidoPorProveedor(cli);
                    cargarTabla(facturasVentas);
                    calcular();
                }
                if(jcbbuscar.getSelectedItem().toString().contentEquals("ESTADO"))
                {
                    String estado= comboLibro1.getSelectedItem().toString();
                    facturasVentas = oConsultaCompra.getAllPedidoPorEstado(estado);
                    cargarTabla(facturasVentas);
                    calcular();
                }
                if(jcbbuscar.getSelectedItem().toString().contentEquals("LIBRO"))
                {
                    Libro libro= (Libro) comboLibro1.getSelectedItem();
                    facturasVentas = oConsultaCompra.getAllPedidoPorLibro(libro);
                    cargarTabla(facturasVentas);
                    calcular();
                }
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Debe seleccionar un rango de fechas válido para la búsqueda", "Error", WIDTH);
            }
        }
    }//GEN-LAST:event_jbbuscarActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        r = false;
        facturasVentas = oConsultaCompra.getAllPedidos();
        cargarTabla(facturasVentas);
        calcular();
        jcbbuscar.setSelectedIndex(0);
        CargarComboLibros();
        jTfBuscarFactura.setText("");
        jDateChooser3.setDate(null);
        jDateChooser4.setDate(null);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        List lista = new ArrayList<>();
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        java.util.Date fd = null;
        java.util.Date fh = null;
        java.util.Date fe = null;
        
        for(int i=0;i<jTablePedidos.getRowCount();i++)
        {
            ListaPedidos facturas = new ListaPedidos(jTablePedidos.getValueAt(i, 0).toString(),jTablePedidos.getValueAt(i, 2).toString(),jTablePedidos.getValueAt(i, 1).toString(),jTablePedidos.getValueAt(i, 4).toString(),jTablePedidos.getValueAt(i, 3).toString());
            lista.add(facturas);
            if(i == 0)
            {
                try {
                    fd = df.parse(jTablePedidos.getValueAt(i, 2).toString());
                } catch (ParseException ex) {
                    Logger.getLogger(BusquedaFactura.class.getName()).log(Level.SEVERE, null, ex);
                }
                fh = fd;
            }
            else
            {
                try {
                    fe = df.parse(jTablePedidos.getValueAt(i, 2).toString());
                } catch (ParseException ex) {
                    Logger.getLogger(BusquedaFactura.class.getName()).log(Level.SEVERE, null, ex);
                }
                if(fh.compareTo(fe) < 0)
                {
                    fh = fe;
                }
            }
        }
        try {
            JasperReport reporte = (JasperReport) JRLoader.loadObject("ReporteCompras.jasper");
            Map parametro = new HashMap();
            String fe1 = df.format(fd);
            String fe2 = df.format(fh);
            parametro.put("fecha1", fe1);
            parametro.put("fecha2", fe2);
            parametro.put("importe", jtfImporte.getText());
            JasperPrint jprint = JasperFillManager.fillReport(reporte, parametro, new JRBeanCollectionDataSource(lista));
            JasperViewer jviewer = new JasperViewer(jprint,false);
            jviewer.show();
            
        } catch (JRException ex) {
            Logger.getLogger(BusquedaFactura.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.jdesktop.swingx.JXComboBox comboLibro1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private com.toedter.calendar.JDateChooser jDateChooser3;
    private com.toedter.calendar.JDateChooser jDateChooser4;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabelTotalOrdenes;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanelBase;
    private javax.swing.JPanel jPanelBusqueda;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTablePedidos;
    private javax.swing.JTextField jTfBuscarFactura;
    private javax.swing.JButton jbbuscar;
    private javax.swing.JComboBox jcbbuscar;
    private javax.swing.JMenuItem jmiEliminar;
    private javax.swing.JPanel jpBuscar;
    private javax.swing.JPopupMenu jpmenu;
    private javax.swing.JTextField jtfImporte;
    // End of variables declaration//GEN-END:variables
}
