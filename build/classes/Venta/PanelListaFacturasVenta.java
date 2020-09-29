/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Venta;

import Empleados.Empleado;
import java.awt.Color;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import static java.awt.image.ImageObserver.WIDTH;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
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
 * @author Mauricio
 */
public class PanelListaFacturasVenta extends javax.swing.JPanel {

    /**
     * Creates new form PanelListaFacturasVenta
     */
    private ConsultaVenta consultaVenta;
    private TableRowSorter<TableModel> sorter;//para ordenar la tabla
    private int v=0,indiceModelo=0,bandera=0,id=0,idLineaFactura=0;
    private FacturaVenta facturaVenta;
    private Cliente cliente;
    private Empleado empleado;
    Date fechaFactura;
    private TipoFacturaVenta tipoFacturaVenta;
    private List <FacturaVentaLibro> facturaVentasLibros;
    private List <FacturaVenta> facturasVentas;
    private FacturaVentaLibro facturaVentaLibro;
    java.sql.Date fventa;
    private List <FacturaVentaLibro> facturavl = new ArrayList<>();
    private float subtotal = 0;
    float sub;
    private boolean control;
    private IniciarReporteVenta jasper;
    DefaultTableModel temp;
    public PanelListaFacturasVenta() {
        initComponents();
        consultaVenta = new ConsultaVenta();
        bandera=0;
        jLabel4.setVisible(false);
        jLabel7.setVisible(false);
        jLabel11.setVisible(false);
        jLabel12.setVisible(false);
        jtfCantidad.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
        btnQuitar.setEnabled(false);
        facturasVentas = consultaVenta.getAllFacturas();
        jasper= new IniciarReporteVenta();
        AccionCombo();
        CargarComboClientes();
        CargarComboEmpleados();
        CargarComboLibros();
        jDateChooser1.setDate(new Date());
        cargarDatosNuevaFactura();
        limpiaTablaLineaFactura();
        AutoCompleteDecorator.decorate(this.comboCliente);
        AutoCompleteDecorator.decorate(this.comboEmpleado);
        AutoCompleteDecorator.decorate(this.comboLibro);
    }
    
    private void AccionCombo() {
        final ItemListener changeClick = new ItemListener()
        {
            @Override
            public void itemStateChanged(ItemEvent e) 
            {   //BUSCAR POR FECHA
                //consultaVenta.eliminarFacturaVentaLibroPorIdFactura(0);
                limpiaTablaLineaFactura();
        }
        };
    }   
     
     public void cargarTablaLineaFactura(List<FacturaVentaLibro> fvl){
    
        //fvl = consultaVenta.getAllFacturaVentaLibro();
        float sub = 0;
        String[] columnNames = {"ID Libro","Nombre Libro","Cantidad","Precio","Subtotal"};
        int[] anchos = {15,100,20,50,40};
        
       Object[][] data = new Object[fvl.size()][columnNames.length];
         
       for (int i = 0; i < fvl.size(); i++) {
           Libro l = consultaVenta.getLibroPorId(fvl.get(i).getIdLibro());
            data[i][0] = fvl.get(i).getIdLibro();
            data[i][1] = l.getTitulo();
            data[i][2] = fvl.get(i).getCantidad();
            data[i][3] = fvl.get(i).getPrecio();
            data[i][4] = fvl.get(i).getSubtotal();
            if(jtftfv.getText().contentEquals("A"))
            {
            if(bandera==0){
                float aux;
                if(fvl.size() == 1)
                    {
                        sub = fvl.get(i).getSubtotal();
                        float bruto = sub;
                        jtfBruto.setText(bruto + "");
                        float iva = (float) (bruto * 0.21);
                        aux=(float) (Math.rint(iva*100)/100);
                        iva = aux;
                        jtfIVA.setText(iva + "");
                        float total = bruto + iva;
                        aux=(float) (Math.rint(total*100)/100);
                        total = aux;
                        jtfTotal.setText(total + "");
                    }
                    if(fvl.size() > 1)
                    {
                            sub = sub + fvl.get(i).getSubtotal();
                            float bruto = sub;
                            jtfBruto.setText(bruto + "");
                            float iva = (float) (bruto * 0.21);
                            aux=(float) (Math.rint(iva*100)/100);
                            iva = aux;
                            jtfIVA.setText(iva + "");
                            float total = bruto + iva;
                            aux=(float) (Math.rint(total*100)/100);
                            total = aux;
                            jtfTotal.setText(total + "");
                        
                    }
            }

            }
            if(jtftfv.getText().contentEquals("B"))
            {
            if(bandera==0){
                jtfBruto.setText("" + 0);
                jtfIVA.setText("" + 0);
                l= (Libro) comboLibro.getSelectedItem();
                if(fvl.size() == 1)
                    {
                        sub = fvl.get(i).getSubtotal();
                        float total = sub;
                        jtfTotal.setText(total + "");
                    }
                    if(fvl.size() > 1)
                    {
                            sub = sub + fvl.get(i).getSubtotal();
                            float total = sub;
                            jtfTotal.setText(total + "");
                        
                    }
            }

            }
            if(jtftfv.getText().contentEquals("C"))
            {
            if(bandera==0){
                jtfBruto.setText("" + 0);
                jtfIVA.setText("" + 0);
                l= (Libro) comboLibro.getSelectedItem();
                if(fvl.size() == 1)
                    {
                        sub = fvl.get(i).getSubtotal();
                        float total = sub;
                        jtfTotal.setText(total + "");
                    }
                    if(fvl.size() > 1)
                    {
                            sub = sub + fvl.get(i).getSubtotal();
                            float total = sub;
                            jtfTotal.setText(total + "");
                        
                    }
            }

            }
        }
       DefaultTableModel dftm = new DefaultTableModel(data, columnNames)
                {
		//metodo para que las celdas del jtable sean no-editables	
                    @Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
       jTableLineaFactura.setModel(dftm);

       for(int i = 0; i < jTableLineaFactura.getColumnCount(); i++) {

        jTableLineaFactura.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);

        }

        sorter = new TableRowSorter<TableModel>(dftm);
        jTableLineaFactura.setRowSorter(sorter);
        jTableLineaFactura.getRowSorter().toggleSortOrder(0);
        jTableLineaFactura.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        jtfCantidad.setText("");
   }
     
     void limpiaTablaLineaFactura(){
        try{
            temp = (DefaultTableModel) jTableLineaFactura.getModel();
            int a =temp.getRowCount()-1;
            for(int i=0; i<a; i++)
                temp.removeRow(i);
        }catch(Exception e){
            System.out.println(e);
        }
    }     
     private  void cargarDatosNuevaFactura(){
        TipoCliente tc;
        TipoFacturaVenta tf;
        facturasVentas = consultaVenta.getAllFacturas();
        Cliente cli= (Cliente) comboCliente.getSelectedItem();
        if(cli != null)
        {
            tc = consultaVenta.getTipoClientePorId(cli.getIdTipoCliente());
            tf = consultaVenta.getTipoFacturaVentaPorId(tc.isIdTipoFacturaVenta());
            jtftfv.setText(tf.getNombreTipoFacturaVenta());
            if(jtftfv.getText().contentEquals("A"))
            {
                jtfIVA.setVisible(true);
                jtfBruto.setVisible(true);
                jLabelBruto.setVisible(true);
                jLabelIVA.setVisible(true);
            }
            if(jtftfv.getText().contentEquals("B"))
            {
                jtfIVA.setVisible(false);
                jtfBruto.setVisible(false);
                jLabelBruto.setVisible(false);
                jLabelIVA.setVisible(false);
            }
        }
        int id = facturasVentas.size() + 1;
        jtfNroFactura.setText("" + id);
    }
     
     private void CargarComboClientes(){
     jLabel4.setVisible(false);
     List<Cliente> lista = consultaVenta.getAllCliente4(true);

        if(lista.isEmpty() == true)
        {
            jLabel4.setText("Ningún cliente creado o seleccionado");
            jLabel4.setVisible(true);
            jDateChooser1.setEnabled(false);
            comboEmpleado.setEnabled(false);
            comboLibro.setEnabled(false);
            jtfCantidad.setEnabled(false);
            jtfCantidad.setEditable(false);
        }
        else
        {
            for (int i = 0; i < lista.size(); i++) {
       
            Cliente cli = new Cliente(lista.get(i).getIdCliente(), lista.get(i).getDniCliente(), lista.get(i).getCuitCliente(), lista.get(i).getNombreCliente(), lista.get(i).getDireccionCliente(), lista.get(i).getTelefonoCLiente(), lista.get(i).getEmailCliente(), lista.get(i).getEstadoCliente(), lista.get(i).getIdLocalidad(), lista.get(i).getIdTipoCliente());
            comboCliente.addItem(cli);
            Cliente objeto=(Cliente) comboCliente.getItemAt(1);

            }
        }
    }
     
     private void CargarComboEmpleados(){
     jLabel7.setVisible(false);
     List<Empleado> lista = consultaVenta.getAllEmpleado(true);

        if(lista.isEmpty() == false)
        {
         for (int i = 0; i < lista.size(); i++) {
       
            //Empleado emp = new Empleado(lista.get(i).getIdEmpleado(), lista.get(i).getDniEmpleado(), lista.get(i).getNombreEmpleado(), lista.get(i).getDomicilioEmpleado(), lista.get(i).getTelefonoEmpleado(), lista.get(i).getIdLocalidad(), lista.get(i).getFechaIngreso(), lista.get(i).getIdCategoriaEmpleado(), lista.get(i).getIdEstadoCivilEmpleado());
            Empleado emp =lista.get(i);
            comboEmpleado.addItem(emp);
            Empleado objeto=(Empleado) comboEmpleado.getItemAt(1);

            }   
        }
        else
        {
            jLabel7.setText("Ningún empleado creado o seleccionado");
            jLabel7.setVisible(true);
            jDateChooser1.setEnabled(false);
            comboCliente.setEnabled(false);
            comboLibro.setEnabled(false);
            jtfCantidad.setEnabled(false);
            jtfCantidad.setEditable(false);
            btnNuevoAutor.setEnabled(false);
        }
    }
     
     private void CargarComboLibros(){
     jLabel11.setVisible(false);
     List<Libro> lista = consultaVenta.getAllLibro(true);

        if(lista.isEmpty())
        {
            jLabel11.setText("Ningún libro creado o seleccionado");
            jLabel11.setVisible(true);
            jtfCantidad.setEnabled(false);
            jtfCantidad.setEditable(false);
        }
        else
        {
         for (int i = 0; i < lista.size(); i++) {
             Libro l=lista.get(i);
            comboLibro.addItem(l);
            Libro objeto=(Libro) comboLibro.getItemAt(1);

            }   
        }
     }
     
     public boolean comprobarNulos(){
        if(jtfCantidad.getText().contentEquals("0") || jtfCantidad.getText().startsWith("0"))
        {control=true;
        jtfCantidad.setText("");
        jLabel12.setText("Cantidad Invalida");
        jLabel12.setVisible(true);
        }
        else{
        control=false;
        }
        return control;
    }
    public boolean comprobarNulosFactura(){
        if(jDateChooser1.getDate()==null)
        {control=true;
        JOptionPane.showMessageDialog(null, "Debe registrar una fecha de venta válida", "Error", WIDTH);
        }
        else{
        control=false;
        }
        return control;
    }
     
    public boolean comprobarTablaVacia(){
        if(facturavl.isEmpty())
        {control=true;
        JOptionPane.showMessageDialog(null, "Debe añadir una línea de factura describiendo el libro a vender", "Error", WIDTH);
        
        }
        else{
        control=false;
        }
        return control;
    }
    
    public void limpiar()
    {
        facturavl = new ArrayList<>();
        this.cargarTablaLineaFactura(facturavl);
        jtfBruto.setText("");
        jtfIVA.setText("");
        jtfTotal.setText("");
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel4 = new javax.swing.JPanel();
        btnguardar1 = new javax.swing.JButton();
        btnCancelar1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jtfNroFactura = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        comboCliente = new org.jdesktop.swingx.JXComboBox();
        jLabel6 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        btnAgregar = new javax.swing.JButton();
        btnQuitar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableLineaFactura = new javax.swing.JTable();
        jtfTotal = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabelIVA = new javax.swing.JLabel();
        jtfIVA = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        comboEmpleado = new org.jdesktop.swingx.JXComboBox();
        jLabel2 = new javax.swing.JLabel();
        jtfCantidad = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        comboLibro = new org.jdesktop.swingx.JXComboBox();
        jtfBruto = new javax.swing.JTextField();
        jLabelBruto = new javax.swing.JLabel();
        jtftfv = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        btnNuevoAutor = new javax.swing.JButton();

        jPanel4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        btnguardar1.setText("Emitir");
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

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setText("N°Factura");

        jtfNroFactura.setEditable(false);
        jtfNroFactura.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jtfNroFactura.setEnabled(false);

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel5.setText("Cliente");

        comboCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboClienteActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel6.setText("Tipo");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setText("Fecha Venta");

        jDateChooser1.setEnabled(false);
        jDateChooser1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jDateChooser1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jDateChooser1MouseEntered(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jDateChooser1MousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jDateChooser1MouseReleased(evt);
            }
        });
        jDateChooser1.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentMoved(java.awt.event.ComponentEvent evt) {
                jDateChooser1ComponentMoved(evt);
            }
        });
        jDateChooser1.addContainerListener(new java.awt.event.ContainerAdapter() {
            public void componentAdded(java.awt.event.ContainerEvent evt) {
                jDateChooser1ComponentAdded(evt);
            }
        });
        jDateChooser1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jDateChooser1FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jDateChooser1FocusLost(evt);
            }
        });
        jDateChooser1.addHierarchyListener(new java.awt.event.HierarchyListener() {
            public void hierarchyChanged(java.awt.event.HierarchyEvent evt) {
                jDateChooser1HierarchyChanged(evt);
            }
        });
        jDateChooser1.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                jDateChooser1InputMethodTextChanged(evt);
            }
        });
        jDateChooser1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jDateChooser1KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jDateChooser1KeyTyped(evt);
            }
        });

        btnAgregar.setText("Añadir Libro");
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });

        btnQuitar.setText("Quitar Libro");
        btnQuitar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuitarActionPerformed(evt);
            }
        });

        jTableLineaFactura.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID Libro", "Cantidad", "Precio", "Subtotal"
            }
        ));
        jScrollPane2.setViewportView(jTableLineaFactura);

        jtfTotal.setEditable(false);

        jLabel8.setText("Total");

        jLabelIVA.setText("IVA");

        jtfIVA.setEditable(false);

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel9.setText("Empleado");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setText("Cantidad");

        jtfCantidad.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jtfCantidad.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtfCantidadFocusLost(evt);
            }
        });
        jtfCantidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtfCantidadKeyTyped(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel10.setText("Libro");

        comboLibro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboLibroActionPerformed(evt);
            }
        });

        jtfBruto.setEditable(false);

        jLabelBruto.setText("Bruto");

        jtftfv.setEditable(false);
        jtftfv.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 0, 0));
        jLabel4.setText("jLabel4");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 0, 0));
        jLabel7.setText("jLabel7");

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 0, 0));
        jLabel11.setText("jLabel11");

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 0, 0));
        jLabel12.setText("jLabel12");

        btnNuevoAutor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/anadir-bala-icono-4686-32.png"))); // NOI18N
        btnNuevoAutor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoAutorActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(btnguardar1, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnCancelar1)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabelBruto)
                                .addGap(18, 18, 18)
                                .addComponent(jtfBruto, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabelIVA)))
                        .addGap(18, 18, 18)
                        .addComponent(jtfIVA, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel8)
                        .addGap(18, 18, 18)
                        .addComponent(jtfTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jtftfv, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jtfNroFactura, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(comboCliente, javax.swing.GroupLayout.DEFAULT_SIZE, 234, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnNuevoAutor, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(jLabel9))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(comboEmpleado, javax.swing.GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(comboLibro, javax.swing.GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel2))
                            .addComponent(jLabel11))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jtfCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 155, Short.MAX_VALUE)
                                .addComponent(btnAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnQuitar))))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                        .addGap(168, 168, 168)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel5)
                        .addComponent(comboCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel9)
                        .addComponent(comboEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(jtfNroFactura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnNuevoAutor, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(jLabel6)
                        .addComponent(jtftfv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAgregar)
                    .addComponent(btnQuitar)
                    .addComponent(jLabel10)
                    .addComponent(comboLibro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jtfCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(2, 2, 2)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jLabel12))
                .addGap(2, 2, 2)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtfTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(jLabelIVA)
                    .addComponent(jtfIVA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelBruto)
                    .addComponent(jtfBruto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnguardar1)
                    .addComponent(btnCancelar1))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnQuitarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuitarActionPerformed
        jLabel12.setVisible(false);
        jtfCantidad.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
        if(jtftfv.getText().contentEquals("A"))
        {
            v=jTableLineaFactura.getSelectedRow();//n° fila selccionada
            if(v != -1)
            {
                indiceModelo = jTableLineaFactura.convertRowIndexToModel (v);//convierte el indice de la vista en el indice del modelo
                float aux;
                int respuesta=JOptionPane.showConfirmDialog(null, "¿Realmente desea quitar el libro de la lista?","Advertencia", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                //confirmamos la eliminacion
                if(respuesta == 0)
                {
                if(bandera==0){
                    facturavl.remove(indiceModelo);
                    this.cargarTablaLineaFactura(facturavl);
                }
                }
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Debe seleccionar la linea de factura a quitar", "Error", WIDTH);
            }
        }
        if(jtftfv.getText().contentEquals("B"))
        {
            v=jTableLineaFactura.getSelectedRow();//n° fila selccionada
            if(v != -1)
            {
                 indiceModelo = jTableLineaFactura.convertRowIndexToModel (v);//convierte el indice de la vista en el indice del modelo
                float aux;
                int respuesta=JOptionPane.showConfirmDialog(null, "¿Realmente desea quitar el libro de la lista?","Advertencia", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                //confirmamos la eliminacion
                if(respuesta == 0)
                {
                facturavl.remove(indiceModelo);
                this.cargarTablaLineaFactura(facturavl);
                }
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Debe seleccionar la linea de factura a quitar", "Error", WIDTH);
            }
        }
        if(jtftfv.getText().contentEquals("C"))
        {
            v=jTableLineaFactura.getSelectedRow();//n° fila selccionada
            if(v != -1)
            {
                 indiceModelo = jTableLineaFactura.convertRowIndexToModel (v);//convierte el indice de la vista en el indice del modelo
                float aux;
                int respuesta=JOptionPane.showConfirmDialog(null, "¿Realmente desea quitar el libro de la lista?","Advertencia", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                //confirmamos la eliminacion
                if(respuesta == 0)
                {
                facturavl.remove(indiceModelo);
                this.cargarTablaLineaFactura(facturavl);
                }
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Debe seleccionar la linea de factura a quitar", "Error", WIDTH);
            }
        }
        if(facturavl.isEmpty())
        {
            float total = 0;
            float bruto = 0;
            float iva = 0;
            jtfTotal.setText(""+total);
            jtfIVA.setText(""+iva);
            jtfBruto.setText(""+bruto);
            btnQuitar.setEnabled(false);
        }
    }//GEN-LAST:event_btnQuitarActionPerformed

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        jLabel12.setVisible(false);
        jtfCantidad.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
        boolean ul = false;
        if(comboLibro.getSelectedItem() != null && comboCliente.getSelectedItem() != null && comboEmpleado.getSelectedItem() != null)
        {
         if(jtfCantidad.getText().isEmpty() == false && jtfCantidad.getText().contentEquals("0") == false && jtfCantidad.getText().startsWith("0") == false)
        {
            if(jtftfv.getText().contentEquals("A"))
            {
            if(bandera==0){
                Libro l= (Libro) comboLibro.getSelectedItem();
                for(int i=0; i<facturavl.size();i++)
                {
                    if(facturavl.get(i).getIdLibro() == l.getIdLibro())
                    {
                        ul = true;
                    }
                }
                if(ul==false)
                {
                    int cantidad= Integer.parseInt(jtfCantidad.getText());
                    if(cantidad<=l.getStock())
                    {
                        int idFactura = Integer.parseInt(jtfNroFactura.getText());
                    float p = (float) (l.getPrecio() / (1.21));
                    float aux=(float) (Math.rint(p*100)/100);
                    p=aux;
                    sub = p * cantidad;
                    aux=(float) (Math.rint(sub*100)/100);
                    sub = aux;
                    facturaVentaLibro = new FacturaVentaLibro(cantidad,p,sub,idFactura,l.getIdLibro());

                    if(facturaVentaLibro.getCantidad()!=0){
                        facturavl.add(facturaVentaLibro);
                        //calculos();
                    }
                    this.cargarTablaLineaFactura(facturavl);
                    btnQuitar.setEnabled(true);
                    comboLibro.setSelectedIndex(0);
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null, "Stock insuficiente", "Error", WIDTH);
                    }
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "El libro ya esta agregado", "Error", WIDTH);
                }
            }

            }
            if(jtftfv.getText().contentEquals("B"))
            {
            if(bandera==0){
                jtfBruto.setText("" + 0);
                jtfIVA.setText("" + 0);
                Libro l= (Libro) comboLibro.getSelectedItem();
                for(int i=0; i<facturavl.size();i++)
                {
                    if(facturavl.get(i).getIdLibro() == l.getIdLibro())
                    {
                        ul = true;
                    }
                }
                if(ul==false)
                {
                    int cantidad= Integer.parseInt(jtfCantidad.getText());
                    if(cantidad<=l.getStock())
                    {
                         int idFactura = Integer.parseInt(jtfNroFactura.getText());
                    float p = l.getPrecio();
                    sub = p * cantidad;
                    float aux=(float) (Math.rint(sub*100)/100);
                    sub = aux;

                    facturaVentaLibro = new FacturaVentaLibro(cantidad,p,sub,idFactura,l.getIdLibro());

                    if(facturaVentaLibro.getCantidad()!=0){
                        facturavl.add(facturaVentaLibro);
                        //calculos();
                    }
                    
                    this.cargarTablaLineaFactura(facturavl);
                    btnQuitar.setEnabled(true);
                    comboLibro.setSelectedIndex(0);
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null, "Stock insuficiente", "Error", WIDTH);
                    }
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "El libro ya esta agregado", "Error", WIDTH);
                }

            }

            }
            if(jtftfv.getText().contentEquals("C"))
            {
            if(bandera==0){
                jtfBruto.setText("" + 0);
                jtfIVA.setText("" + 0);
                Libro l= (Libro) comboLibro.getSelectedItem();
                for(int i=0; i<facturavl.size();i++)
                {
                    if(facturavl.get(i).getIdLibro() == l.getIdLibro())
                    {
                        ul = true;
                    }
                }
                if(ul==false)
                {
                    int cantidad= Integer.parseInt(jtfCantidad.getText());
                    if(cantidad<=l.getStock())
                    {
                    int idFactura = Integer.parseInt(jtfNroFactura.getText());
                    float p = l.getPrecio();
                    sub = p * cantidad;
                    float aux=(float) (Math.rint(sub*100)/100);
                    sub = aux;

                    facturaVentaLibro = new FacturaVentaLibro(cantidad,p,sub,idFactura,l.getIdLibro());

                    if(facturaVentaLibro.getCantidad()!=0){
                        facturavl.add(facturaVentaLibro);
                        //calculos();
                    }
                    
                    this.cargarTablaLineaFactura(facturavl);
                    btnQuitar.setEnabled(true);
                    comboLibro.setSelectedIndex(0);
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null, "Stock insuficiente", "Error", WIDTH);
                    }
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "El libro ya esta agregado", "Error", WIDTH);
                }

            }

            }
        }
        else
        {
            jtfCantidad.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
            if(jtfCantidad.getText().isEmpty())
            {
                jLabel12.setText("Debe ingresar una cantidad");
                jLabel12.setVisible(true);
            }
            if(jtfCantidad.getText().contentEquals("0") || jtfCantidad.getText().startsWith("0"))
            {
                jLabel12.setText("La cantidad no puede ser ni puede comenzar con 0");
                jLabel12.setVisible(true);
                jtfCantidad.setText("");
            }
        }   
        }
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void btnCancelar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelar1ActionPerformed
        int respuesta=JOptionPane.showConfirmDialog(null, "¿Confirma la cancelación? \n Los datos no seran guardados","Advertencia", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            //confirmamos la eliminacion
            if(respuesta == 0)
            {
                this.removeAll();
                this.repaint();
                this.revalidate();
            }
    }//GEN-LAST:event_btnCancelar1ActionPerformed

    private void btnguardar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnguardar1ActionPerformed
        jLabel12.setVisible(false);
        jtfCantidad.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
        if(comboCliente.getSelectedItem() != null && comboEmpleado.getSelectedItem() != null)
        {
         if(bandera==0){

            if(comprobarNulosFactura()==false && comprobarTablaVacia()==false){

                int idFactura = Integer.parseInt(jtfNroFactura.getText());
                int stock;
                Cliente cli= (Cliente) comboCliente.getSelectedItem();
                Empleado emp= (Empleado) comboEmpleado.getSelectedItem();
                TipoCliente tc = consultaVenta.getTipoClientePorId(cli.getIdTipoCliente());
                TipoFacturaVenta tfv = consultaVenta.getTipoFacturaVentaPorId(tc.isIdTipoFacturaVenta());

                if(jDateChooser1.getDate() != null)
                {fechaFactura=jDateChooser1.getDate();
                    fventa = new java.sql.Date(fechaFactura.getTime());
                    System.out.println("Valor: "+fventa);
                }
                else{
                    fechaFactura=new Date(00, 0, 1);
                    fventa=new java.sql.Date(fechaFactura.getTime());
                }
                facturaVenta = new FacturaVenta(idFactura,fventa,Float.parseFloat(jtfBruto.getText()),Float.parseFloat(jtfIVA.getText()),Float.parseFloat(jtfTotal.getText()),false,cli.getIdCliente(),emp.getIdEmpleado());
                System.out.println("Valor: "+facturaVenta.getFechaFacturaVenta());
                consultaVenta.agregarFacturaVenta(facturaVenta);

                for (int i = 0; i < facturavl.size(); i++) {
                    consultaVenta.agregarFacturaVentaLibro(facturavl.get(i));
                    System.out.println(facturavl.get(i).getIdLibro());
                    Libro l = consultaVenta.getLibroPorId(facturavl.get(i).getIdLibro());
                    stock = l.getStock() - facturavl.get(i).getCantidad();
                    consultaVenta.modificarLibroStock(l.getIdLibro(), stock);
                }
                
                
                int idF=consultaVenta.getIdUltimaFactura();
                
                JOptionPane.showMessageDialog(this,"Factura Registrada \n Emitiendo reporte...");
                System.out.println(""+idF);
                
                //jasper.ejecutarReporte(idF);
                List lista = new ArrayList<>();
        
                for(int i=0;i<jTableLineaFactura.getRowCount();i++)
                {
                    LineasFactura facturas = new LineasFactura(jTableLineaFactura.getValueAt(i, 1).toString(),jTableLineaFactura.getValueAt(i, 2).toString(),jTableLineaFactura.getValueAt(i, 3).toString(),jTableLineaFactura.getValueAt(i, 4).toString());
                    lista.add(facturas);
                    
                }
            try {
            JasperReport reporte = (JasperReport) JRLoader.loadObject("ReporteFacturaVenta.jasper");
            Map parametro = new HashMap();
            parametro.put("idFactura", jtfNroFactura.getText());
            parametro.put("empleado", comboEmpleado.getSelectedItem().toString());
            parametro.put("tipoFactura", jtftfv.getText());
            parametro.put("cliente", comboCliente.getSelectedItem().toString());
            parametro.put("tipoCliente", tc.getNombreTipoCliente());
            parametro.put("bruto", jtfBruto.getText());
            parametro.put("iva", jtfIVA.getText());
            parametro.put("total", jtfTotal.getText());
            JasperPrint jprint = JasperFillManager.fillReport(reporte, parametro, new JRBeanCollectionDataSource(lista));
            JasperViewer jviewer = new JasperViewer(jprint,false);
            jviewer.show();
            
        } catch (JRException ex) {
            Logger.getLogger(BusquedaFactura.class.getName()).log(Level.SEVERE, null, ex);
        }
            
                facturasVentas = consultaVenta.getAllFacturas();
                jtfNroFactura.setText("" + (id+1));
                jtftfv.setText("");
                limpiar();    
                this.removeAll();
                this.repaint();
                this.revalidate();
            }//FIN IF COMPROBAR NULOS
        }   
        }
    }//GEN-LAST:event_btnguardar1ActionPerformed

    private void comboClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboClienteActionPerformed
        this.cargarDatosNuevaFactura();
        jtfCantidad.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
        jLabel12.setVisible(false);
        btnQuitar.setEnabled(false);
        limpiar();
    }//GEN-LAST:event_comboClienteActionPerformed

    private void jtfCantidadKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfCantidadKeyTyped
        jtfCantidad.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
        jLabel12.setVisible(false);
        int longitud = jtfCantidad.getText().length();
        char c=evt.getKeyChar();
            
        
          if(Character.isDigit(c) == false && c != '\b') {
              getToolkit().beep();
              
              evt.consume();
              
              jtfCantidad.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
              jLabel12.setText("Debe ingresar números");
              jLabel12.setVisible(true);
              
          }
          if(longitud == 0 && c == '0')
          {
              getToolkit().beep();
              
              evt.consume();
              
              jtfCantidad.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
              jLabel12.setText("No debe ser ni empezar con 0");
              jLabel12.setVisible(true);
          }
    }//GEN-LAST:event_jtfCantidadKeyTyped

    private void jtfCantidadFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtfCantidadFocusLost
        jtfCantidad.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
        jLabel12.setVisible(false);
        if(jtfCantidad.getText().isEmpty())
        {
                jtfCantidad.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
                jLabel12.setText("Debe ingresar una cantidad");
                jLabel12.setVisible(true);
        }
        if(jtfCantidad.getText().contentEquals("0") || jtfCantidad.getText().startsWith("0"))
        {
                jtfCantidad.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
                jLabel12.setText("La cantidad no puede ser ni puede comenzar con 0");
                jLabel12.setVisible(true);
        }
    }//GEN-LAST:event_jtfCantidadFocusLost

    private void jDateChooser1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jDateChooser1KeyTyped

    }//GEN-LAST:event_jDateChooser1KeyTyped

    private void jDateChooser1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jDateChooser1FocusLost

    }//GEN-LAST:event_jDateChooser1FocusLost

    private void jDateChooser1ComponentAdded(java.awt.event.ContainerEvent evt) {//GEN-FIRST:event_jDateChooser1ComponentAdded

    }//GEN-LAST:event_jDateChooser1ComponentAdded

    private void jDateChooser1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jDateChooser1MouseClicked

    }//GEN-LAST:event_jDateChooser1MouseClicked

    private void jDateChooser1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jDateChooser1MousePressed

    }//GEN-LAST:event_jDateChooser1MousePressed

    private void jDateChooser1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jDateChooser1MouseReleased

    }//GEN-LAST:event_jDateChooser1MouseReleased

    private void jDateChooser1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jDateChooser1FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_jDateChooser1FocusGained

    private void jDateChooser1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jDateChooser1MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jDateChooser1MouseEntered

    private void jDateChooser1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jDateChooser1KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jDateChooser1KeyReleased

    private void jDateChooser1ComponentMoved(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jDateChooser1ComponentMoved

    }//GEN-LAST:event_jDateChooser1ComponentMoved

    private void jDateChooser1HierarchyChanged(java.awt.event.HierarchyEvent evt) {//GEN-FIRST:event_jDateChooser1HierarchyChanged

    }//GEN-LAST:event_jDateChooser1HierarchyChanged

    private void jDateChooser1InputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_jDateChooser1InputMethodTextChanged

    }//GEN-LAST:event_jDateChooser1InputMethodTextChanged

    private void btnNuevoAutorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoAutorActionPerformed
        NuevoCliente nc= new NuevoCliente(null, true);
        nc.setLocationRelativeTo(this);
        nc.setVisible(true);
        nc.setLocationRelativeTo(null);
        comboCliente.removeAllItems();
        this.CargarComboClientes();
    }//GEN-LAST:event_btnNuevoAutorActionPerformed

    private void comboLibroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboLibroActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboLibroActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnCancelar1;
    private javax.swing.JButton btnNuevoAutor;
    private javax.swing.JButton btnQuitar;
    private javax.swing.JButton btnguardar1;
    private org.jdesktop.swingx.JXComboBox comboCliente;
    private org.jdesktop.swingx.JXComboBox comboEmpleado;
    private org.jdesktop.swingx.JXComboBox comboLibro;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelBruto;
    private javax.swing.JLabel jLabelIVA;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTableLineaFactura;
    private javax.swing.JTextField jtfBruto;
    private javax.swing.JTextField jtfCantidad;
    private javax.swing.JTextField jtfIVA;
    private javax.swing.JTextField jtfNroFactura;
    private javax.swing.JTextField jtfTotal;
    private javax.swing.JTextField jtftfv;
    // End of variables declaration//GEN-END:variables
}
