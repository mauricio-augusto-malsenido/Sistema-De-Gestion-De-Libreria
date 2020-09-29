/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Venta;

import Empleados.Empleado;
import especiales.Render_CheckBox;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import static java.awt.image.ImageObserver.WIDTH;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
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
 * @author sergio
 */
public class BusquedaFactura extends javax.swing.JPanel {

    private ConsultaVenta consultaVenta;
    private TableRowSorter<TableModel> sorter;//para ordenar la tabla
    private int v=0,indiceModelo=0,bandera=0,id=0,idLineaFactura=0;
    private FacturaVenta facturaVenta;
    private Cliente cliente;
    private Empleado empleado;
    Date fechaFactura;
    Date dateDes;
    Date dateHas;
    java.sql.Date fedes;
    java.sql.Date fehas;
    private List <FacturaVentaLibro> facturaVentasLibros;
    private List <FacturaVenta> facturasVentas;
    private List <FacturaVenta> facturasV;
    java.sql.Date fventa;
    private List <FacturaVentaLibro> facturavl = new ArrayList<>();
    private float subtotal = 0, total;
    float sub;
    private boolean control;
    private boolean r = false;
    DefaultTableModel temp;
    public BusquedaFactura() {
        initComponents();
        consultaVenta = new ConsultaVenta();
        bandera=0;
        jLabel10.setVisible(false);
        jLabel2.setVisible(false);
        comboLibro1.setVisible(true);
        jtfCantidad.setVisible(false);
        btnAgregar.setVisible(false);
        btnQuitar.setVisible(false);
        jbAnular.setEnabled(false);
        jbbuscar.setVisible(true);
        jButton1.setVisible(true);
        jDateChooser3.setVisible(true);
        jDateChooser4.setVisible(true);
        jLabel7.setVisible(true);
        jLabel11.setVisible(true);
        jXTaskPane2.setCollapsed(true);
        jTfBuscarFactura.setVisible(false);
        CargarComboLibros();
        AutoCompleteDecorator.decorate(this.comboLibro1);
        limpiar();
        busqueda();
        facturasVentas = consultaVenta.getAllFacturas();
        jTableFacturas.addMouseListener(new MouseAdapter() 
        {
           @Override
           public void mouseClicked(MouseEvent e) 
           {
             if(e.getClickCount()== 2){
              int fila = jTableFacturas.rowAtPoint(e.getPoint());
              int columna = jTableFacturas.columnAtPoint(e.getPoint());
              jTfBuscarFactura.setEnabled(false);
              jTfBuscarFactura.setEditable(false);
              jDateChooser3.setEnabled(false);
              jDateChooser4.setEnabled(false);
              comboLibro1.setEnabled(false);
              jCkHabilitados.setEnabled(false);
              jButton2.setEnabled(false);
              jcbbuscar.setEnabled(false);
              jbbuscar.setEnabled(false);
              jButton1.setEnabled(false);
              jTableFacturas.setEnabled(false);
             /*El método rowAtPoint() nos devuelve -1 si pinchamos en el JTable
              pero fuera de cualquier fila*/
              
                     if ((fila > -1) && (columna > -1))
                     {
                       v=jTableFacturas.getSelectedRow();//n° fila selccionada
                       indiceModelo = jTableFacturas.convertRowIndexToModel (v);//convierte el indice de la vista en el indice del modelo 
                       jXTaskPane2.setCollapsed(false);
                       bandera=1;
                       facturaVenta = consultaVenta.getFactura(getIdFactura(indiceModelo));
                       if(facturaVenta.isAnulada() == true)
                       {
                           jbAnular.setEnabled(false);
                       }
                       else
                       {
                           jbAnular.setEnabled(true);
                       }
                       cargarDatosFactura(facturaVenta);
                     }
             }
           }
        });
        
        cargarTablaFactura(consultaVenta.getAllFacturas());
        calcular();
    } 
    public void cargarTablaFactura(List<FacturaVenta> fv){
    
        Boolean estado = jCkHabilitados.isSelected();
        facturasV = new ArrayList<>();
        String[] columnNames = {"Nº Factura","Fecha","Total","Anulada","Cliente","Tipo","Empleado"};
        int[] anchos = {40,100,100,150,100,90};
        String anulada;
        
         
       for (int i = 0; i < fv.size(); i++) {
           if(fv.get(i).isAnulada()== false)
           {
               facturasV.add(fv.get(i));
           }
       }
       if(estado){
           Object[][] data = new Object[fv.size()][columnNames.length];
           for (int i = 0; i < fv.size(); i++) {
           if(fv.get(i).isAnulada()== false)
           {
               anulada = "No";
           }
           else
           {
               anulada = "Si";
           }
           
        String nombreCliente=consultaVenta.getClientePorId(fv.get(i).getIdCliente()).getNombreCliente();
        int idtdc = consultaVenta.getClientePorId(fv.get(i).getIdCliente()).getIdTipoCliente();
        int idTipoFactura = consultaVenta.getTipoClientePorId(idtdc).isIdTipoFacturaVenta();
        String nombreTipoFactura = consultaVenta.getTipoFacturaVentaPorId(idTipoFactura).getNombreTipoFacturaVenta();
        String nombreEmpleado=consultaVenta.getEmpleadoPorId(fv.get(i).getIdEmpleado()).getNombreEmpleado();
        String nombreTipoFacturaVenta= nombreTipoFactura;
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String fe1 = df.format(fv.get(i).getFechaFacturaVenta());
            data[i][0] = fv.get(i).getIdFacturaVenta();
            data[i][1] = fe1;
            data[i][2] = fv.get(i).getTotalFacturaVenta();
            data[i][3] = anulada;
            data[i][4] = nombreCliente;
            data[i][5] = nombreTipoFacturaVenta;
            data[i][6] = nombreEmpleado;
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
       jTableFacturas.setModel(dftm);
       DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        for(int i=0;i<jTableFacturas.getColumnCount();i++)
        {
            jTableFacturas.getColumnModel().getColumn(i).setCellRenderer(tcr);
        }
        //sorter = new TableRowSorter<TableModel>(dftm);
        //jTableFacturas.setRowSorter(sorter);
        //jTableFacturas.getRowSorter().toggleSortOrder(0);
        jTableFacturas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
       }
       else{
           Object[][] data = new Object[facturasV.size()][columnNames.length];
           for (int i = 0; i < facturasV.size(); i++) {
           if(facturasV.get(i).isAnulada()== false)
           {
               anulada = "No";
           }
           else
           {
               anulada = "Si";
           }
           
        String nombreCliente=consultaVenta.getClientePorId(facturasV.get(i).getIdCliente()).getNombreCliente();
        int idtdc = consultaVenta.getClientePorId(facturasV.get(i).getIdCliente()).getIdTipoCliente();
        int idTipoFactura = consultaVenta.getTipoClientePorId(idtdc).isIdTipoFacturaVenta();
        String nombreTipoFactura = consultaVenta.getTipoFacturaVentaPorId(idTipoFactura).getNombreTipoFacturaVenta();
        String nombreEmpleado=consultaVenta.getEmpleadoPorId(facturasV.get(i).getIdEmpleado()).getNombreEmpleado();
        String nombreTipoFacturaVenta= nombreTipoFactura;
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String fe1 = df.format(facturasV.get(i).getFechaFacturaVenta());
            data[i][0] = facturasV.get(i).getIdFacturaVenta();
            data[i][1] = fe1;
            data[i][2] = facturasV.get(i).getTotalFacturaVenta();
            data[i][3] = anulada;
            data[i][4] = nombreCliente;
            data[i][5] = nombreTipoFacturaVenta;
            data[i][6] = nombreEmpleado;
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
        
        jTableFacturas.setModel(dftm);
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        for(int i=0;i<jTableFacturas.getColumnCount();i++)
        {
            jTableFacturas.getColumnModel().getColumn(i).setCellRenderer(tcr);
        }
        //sorter = new TableRowSorter<TableModel>(dftm);
        //jTableFacturas.setRowSorter(sorter);
        //jTableFacturas.getRowSorter().toggleSortOrder(0);
        jTableFacturas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
       }
   }
    
   private void busqueda(){
     
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
   
   private void filtrar() {
        RowFilter<TableModel, Object> rf = null;
        int indiceColumnaTabla = 1;
        
        if(jcbbuscar.getSelectedItem().toString().contentEquals("CLIENTE"))
        {
            indiceColumnaTabla=4;
        }
        try {
            
            rf = RowFilter.regexFilter(jTfBuscarFactura.getText().toUpperCase(), indiceColumnaTabla);
        } catch (java.util.regex.PatternSyntaxException e) {
        }
        sorter.setRowFilter(rf);
        calcular();
    }
   
   public void cargarTablaLineaFactura(List<FacturaVentaLibro> fvl){
    
        //fvl = consultaVenta.getAllFacturaVentaLibro();
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
        //jtfTotal.setText("");
   }
   
   private int getIdFactura(int im){
        String[] fila= new String[1];//almaceno los valores del registro seleccionado en el string "fila"
        fila[0]=""+jTableFacturas.getModel().getValueAt(im, 0);
        
        int idRep=Integer.parseInt(fila[0]);
        
        return  idRep;
    }
     
        private  void cargarDatosFactura(FacturaVenta facturav){
        int idFactura = facturav.getIdFacturaVenta();
        jtfNroFactura.setText("" +idFactura);
        Cliente cl = consultaVenta.getClientePorId(facturav.getIdCliente());
        comboCliente.addItem(cl);
        Empleado emp = consultaVenta.getEmpleadoPorId(facturav.getIdEmpleado());
        comboEmpleado.addItem(emp);
        int idtdc = cl.getIdTipoCliente();
        int idTipoFactura = consultaVenta.getTipoClientePorId(idtdc).isIdTipoFacturaVenta();
        TipoFacturaVenta tfv = consultaVenta.getTipoFacturaVentaPorId(idTipoFactura);
        jtftfv.setText(tfv.getNombreTipoFacturaVenta());
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
        if(jtftfv.getText().contentEquals("C"))
        {
                jtfIVA.setVisible(false);
                jtfBruto.setVisible(false);
                jLabelBruto.setVisible(false);
                jLabelIVA.setVisible(false);
        }
        jDateChooser1.setDate(facturav.getFechaFacturaVenta());
        jtfBruto.setText(facturav.getBrutoFacturaVenta() + "");
        jtfIVA.setText(facturav.getIvaFacturaVenta() + "");
        jtfTotal.setText(facturav.getTotalFacturaVenta() + "");
        facturavl = consultaVenta.getAllFacturaVentaLibro(idFactura);
        this.cargarTablaLineaFactura(facturavl);
    }
        
        public boolean comprobarNulos(){
        if(Integer.parseInt(jtfCantidad.getText())==0)
        {control=true;
        JOptionPane.showMessageDialog(null, "Cantidad Incorrecta", "ADVERTENCIA", WIDTH);
        }
        else{
        control=false;
        }
        return control;
    }
    public boolean comprobarNulosFactura(){
        if(jDateChooser1.getDate()==null)
        {control=true;
        JOptionPane.showMessageDialog(null, "Debe registrar la fecha Compra", "ADVERTENCIA", WIDTH);
        
        }
        else{
        control=false;
        }
        return control;
    }
     
    public boolean comprobarTablaVacia(){
        if(facturavl.isEmpty())
        {control=true;
        JOptionPane.showMessageDialog(null, "No se han registrado libros", "ADVERTENCIA", WIDTH);
        
        }
        else{
        control=false;
        }
        return control;
    }
    
    private void CargarComboLibros(){
    comboLibro1.removeAllItems();
    if(jcbbuscar.getSelectedItem().toString().contentEquals("LIBRO"))
    {
        List<Libro> lista = consultaVenta.getAllLibro(true);

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
    if(jcbbuscar.getSelectedItem().toString().contentEquals("CLIENTE"))
    {
        List<Cliente> lista = consultaVenta.getAllCliente();

        if(lista.isEmpty())
        {
            comboLibro1.addItem("Ningun cliente cargado");
        }
        else
        {
         for (int i = 0; i < lista.size(); i++) {
            Cliente l=lista.get(i);
            comboLibro1.addItem(l);
            Cliente objeto=(Cliente) comboLibro1.getItemAt(1);

            }   
        }
    }
    if(jcbbuscar.getSelectedItem().toString().contentEquals("EMPLEADO"))
    {
        List<Empleado> lista = consultaVenta.getAllEmpleado(true);

        if(lista.isEmpty())
        {
            comboLibro1.addItem("Ningun empleado cargado");
        }
        else
        {
         for (int i = 0; i < lista.size(); i++) {
            Empleado l=lista.get(i);
            comboLibro1.addItem(l);
            Empleado objeto=(Empleado) comboLibro1.getItemAt(1);

            }   
        }
    }
     }
    
    private void calcular()
    {
        subtotal = 0;
        for(int i = 0; i < jTableFacturas.getRowCount(); i++) {
            
            if(String.valueOf(jTableFacturas.getValueAt(i, 3)).contains("No"))
            {
                subtotal = subtotal + Float.parseFloat(String.valueOf(jTableFacturas.getValueAt(i, 2)));
            }
        }
       jtfImporte.setText(""+subtotal);
    }
    
    public void limpiarTablaLineaFactura(){
        try {
            DefaultTableModel modelo=(DefaultTableModel) jTableLineaFactura.getModel();
            int filas=jTableLineaFactura.getRowCount();
            for (int i = 0;filas>i; i++) {
                modelo.removeRow(0);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al limpiar la tabla.");
        }
    }
    
    public void limpiar()
    {
        jtfNroFactura.setText("");
        jtftfv.setText("");
        comboCliente.removeAllItems();
        comboEmpleado.removeAllItems();
        jDateChooser1.setDate(null);
        limpiarTablaLineaFactura();
        jTfBuscarFactura.setEnabled(true);
        jTfBuscarFactura.setEditable(true);
        jDateChooser3.setEnabled(true);
        jDateChooser4.setEnabled(true);
        jButton2.setEnabled(true);
        comboLibro1.setEnabled(true);
        jCkHabilitados.setEnabled(true);
        jcbbuscar.setEnabled(true);
        jbbuscar.setEnabled(true);
        jButton1.setEnabled(true);
        jbAnular.setEnabled(false);
        jTableFacturas.setEnabled(true);
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

        jPanelBusqueda = new javax.swing.JPanel();
        jTfBuscarFactura = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jcbbuscar = new javax.swing.JComboBox();
        jDateChooser3 = new com.toedter.calendar.JDateChooser();
        jLabel7 = new javax.swing.JLabel();
        jDateChooser4 = new com.toedter.calendar.JDateChooser();
        jLabel11 = new javax.swing.JLabel();
        jbbuscar = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        comboLibro1 = new org.jdesktop.swingx.JXComboBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableFacturas = new javax.swing.JTable();
        jXTaskPane2 = new org.jdesktop.swingx.JXTaskPane();
        jPanel4 = new javax.swing.JPanel();
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
        jbAnular = new javax.swing.JButton();
        jtftfv = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jtfImporte = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jCkHabilitados = new javax.swing.JCheckBox();

        jPanelBusqueda.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.black, java.awt.Color.black, java.awt.Color.black, java.awt.Color.black));

        jTfBuscarFactura.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTfBuscarFacturaKeyTyped(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setText("Buscar:");

        jcbbuscar.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "CLIENTE", "EMPLEADO", "LIBRO" }));
        jcbbuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbbuscarActionPerformed(evt);
            }
        });

        jDateChooser3.setPreferredSize(new java.awt.Dimension(95, 15));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setText("Desde:");

        jDateChooser4.setPreferredSize(new java.awt.Dimension(95, 17));

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel11.setText("Hasta:");

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

        comboLibro1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboLibro1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelBusquedaLayout = new javax.swing.GroupLayout(jPanelBusqueda);
        jPanelBusqueda.setLayout(jPanelBusquedaLayout);
        jPanelBusquedaLayout.setHorizontalGroup(
            jPanelBusquedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelBusquedaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcbbuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTfBuscarFactura, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(comboLibro1, javax.swing.GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jDateChooser3, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jDateChooser4, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbbuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addGap(10, 10, 10))
        );
        jPanelBusquedaLayout.setVerticalGroup(
            jPanelBusquedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelBusquedaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelBusquedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbbuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jDateChooser3, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanelBusquedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTfBuscarFactura, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(comboLibro1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelBusquedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jcbbuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel4))
                    .addComponent(jDateChooser4, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(jLabel7))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTableFacturas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Nro Factura", "Fecha", "Total", "Anulada", "Cliente", "Tipo", "Empleado"
            }
        ));
        jScrollPane1.setViewportView(jTableFacturas);

        jXTaskPane2.setTitle("Anular Factura");

        jPanel4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

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

        comboCliente.setEnabled(false);

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel6.setText("Tipo");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setText("Fecha Venta");

        jDateChooser1.setEnabled(false);

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

        comboEmpleado.setEnabled(false);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setText("Cantidad");

        jtfCantidad.setEditable(false);
        jtfCantidad.setText("1");
        jtfCantidad.setDisabledTextColor(new java.awt.Color(0, 0, 0));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel10.setText("Libro");

        comboLibro.setEnabled(false);

        jtfBruto.setEditable(false);

        jLabelBruto.setText("Bruto");

        jbAnular.setText("Anular");
        jbAnular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbAnularActionPerformed(evt);
            }
        });

        jtftfv.setEditable(false);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(178, 178, 178)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabelBruto)
                        .addGap(18, 18, 18)
                        .addComponent(jtfBruto, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabelIVA)
                        .addGap(18, 18, 18)
                        .addComponent(jtfIVA, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel8)
                        .addGap(18, 18, 18)
                        .addComponent(jtfTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(comboLibro, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jtfCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 397, Short.MAX_VALUE)
                                .addComponent(btnAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnQuitar))
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
                                .addComponent(comboCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(comboEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jbAnular, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnCancelar1, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel5)
                        .addComponent(comboCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel9)
                        .addComponent(comboEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(jtfNroFactura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(jLabel6)
                        .addComponent(jtftfv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAgregar)
                    .addComponent(btnQuitar)
                    .addComponent(jLabel10)
                    .addComponent(comboLibro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jtfCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                    .addComponent(btnCancelar1)
                    .addComponent(jbAnular))
                .addContainerGap())
        );

        jXTaskPane2.getContentPane().add(jPanel4);

        jLabel12.setText("Importe Total $");

        jtfImporte.setEditable(false);

        jButton2.setText("Generar Reporte De Ventas");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jCkHabilitados.setText("Todos/Solo No Anuladas");
        jCkHabilitados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCkHabilitadosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jXTaskPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtfImporte, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jCkHabilitados)))
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanelBusqueda, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(74, 74, 74)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtfImporte, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jCkHabilitados))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jXTaskPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanelBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(591, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelar1ActionPerformed
        jXTaskPane2.setCollapsed(true);
        dateDes=jDateChooser3.getDate();
        dateHas=jDateChooser4.getDate();
        facturasVentas = new ArrayList<>();
        if(r == true)
        {
        if(dateDes != null && dateHas != null)
        {
        if(jcbbuscar.getSelectedItem().toString().contentEquals("LIBRO"))
        {
            fedes=new java.sql.Date(dateDes.getTime());
            fehas=new java.sql.Date(dateHas.getTime());
            Libro libro= (Libro) comboLibro1.getSelectedItem();
            facturasVentas = consultaVenta.getAllFacturaVentaPorFechayLibro(libro,fedes, fehas);
            cargarTablaFactura(facturasVentas);
            calcular();
        }
        if(jcbbuscar.getSelectedItem().toString().contentEquals("CLIENTE"))
        {
            fedes=new java.sql.Date(dateDes.getTime());
            fehas=new java.sql.Date(dateHas.getTime());
            Cliente cli= (Cliente) comboLibro1.getSelectedItem();
            facturasVentas = consultaVenta.getAllFacturaVentaPorFechayCliente(cli,fedes, fehas);
            cargarTablaFactura(facturasVentas);
            calcular();
        }
        if(jcbbuscar.getSelectedItem().toString().contentEquals("EMPLEADO"))
        {
            fedes=new java.sql.Date(dateDes.getTime());
            fehas=new java.sql.Date(dateHas.getTime());
            Empleado cli= (Empleado) comboLibro1.getSelectedItem();
            facturasVentas = consultaVenta.getAllFacturaVentaPorFechayEmpleado(cli,fedes, fehas);
            cargarTablaFactura(facturasVentas);
            calcular();
        }
        }
        else
        {
            if(dateDes == null && dateHas == null)
            {
                if(jcbbuscar.getSelectedItem().toString().contentEquals("CLIENTE"))
                {
                    Cliente cli= (Cliente) comboLibro1.getSelectedItem();
                    facturasVentas = consultaVenta.getAllFacturaVentaPorCliente(cli);
                    cargarTablaFactura(facturasVentas);
                    calcular();
                }
                if(jcbbuscar.getSelectedItem().toString().contentEquals("EMPLEADO"))
                {
                    Empleado cli= (Empleado) comboLibro1.getSelectedItem();
                    facturasVentas = consultaVenta.getAllFacturaVentaPorEmpleado(cli);
                    cargarTablaFactura(facturasVentas);
                    calcular();
                }
                if(jcbbuscar.getSelectedItem().toString().contentEquals("LIBRO"))
                {
                    Libro libro= (Libro) comboLibro1.getSelectedItem();
                    facturasVentas = consultaVenta.getAllFacturaVentaPorLibro(libro);
                    cargarTablaFactura(facturasVentas);
                    calcular();
                }
            }
        }
        }
        else
        {
            facturasVentas = consultaVenta.getAllFacturas();
            cargarTablaFactura(facturasVentas);
            calcular();
        }
        limpiar();
    }//GEN-LAST:event_btnCancelar1ActionPerformed

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void btnQuitarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuitarActionPerformed

    }//GEN-LAST:event_btnQuitarActionPerformed

    private void jbAnularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbAnularActionPerformed
        int respuesta=JOptionPane.showConfirmDialog(null, "¿Realmente desea anular esta factura? \n Esta acción es irreversible","Advertencia", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

         //confirmamos la eliminacion
         if(respuesta == 0)
         {
                    facturavl = consultaVenta.getAllFacturaVentaLibro(facturaVenta.getIdFacturaVenta());
                    int stock;
                    for (int i = 0; i < facturavl.size(); i++) {
                    Libro l = consultaVenta.getLibroPorId(facturavl.get(i).getIdLibro());
                    stock = l.getStock() + facturavl.get(i).getCantidad();
                    consultaVenta.modificarLibroStock(l.getIdLibro(), stock);
                    }
                    consultaVenta.modificarFacturaVenta(facturaVenta);
                    dateDes=jDateChooser3.getDate();
                    dateHas=jDateChooser4.getDate();
                    facturasVentas = new ArrayList<>();
                    if(r == true)
                    {
                    if(dateDes != null && dateHas != null)
                    {
                        if(jcbbuscar.getSelectedItem().toString().contentEquals("LIBRO"))
                        {
                            fedes=new java.sql.Date(dateDes.getTime());
                            fehas=new java.sql.Date(dateHas.getTime());
                            Libro libro= (Libro) comboLibro1.getSelectedItem();
                            facturasVentas = consultaVenta.getAllFacturaVentaPorFechayLibro(libro,fedes, fehas);
                            cargarTablaFactura(facturasVentas);
                            calcular();
                        }
                        if(jcbbuscar.getSelectedItem().toString().contentEquals("CLIENTE"))
                        {
                            fedes=new java.sql.Date(dateDes.getTime());
                            fehas=new java.sql.Date(dateHas.getTime());
                            Cliente cli= (Cliente) comboLibro1.getSelectedItem();
                            facturasVentas = consultaVenta.getAllFacturaVentaPorFechayCliente(cli,fedes, fehas);
                            cargarTablaFactura(facturasVentas);
                            calcular();
                        }
                        if(jcbbuscar.getSelectedItem().toString().contentEquals("EMPLEADO"))
                        {
                            fedes=new java.sql.Date(dateDes.getTime());
                            fehas=new java.sql.Date(dateHas.getTime());
                            Empleado cli= (Empleado) comboLibro1.getSelectedItem();
                            facturasVentas = consultaVenta.getAllFacturaVentaPorFechayEmpleado(cli,fedes, fehas);
                            cargarTablaFactura(facturasVentas);
                            calcular();
                        }
                    }
                    else
                    {
                        if(dateDes == null && dateHas == null)
                        {
                            if(jcbbuscar.getSelectedItem().toString().contentEquals("CLIENTE"))
                            {
                                Cliente cli= (Cliente) comboLibro1.getSelectedItem();
                                facturasVentas = consultaVenta.getAllFacturaVentaPorCliente(cli);
                                cargarTablaFactura(facturasVentas);
                                calcular();
                            }
                            if(jcbbuscar.getSelectedItem().toString().contentEquals("EMPLEADO"))
                            {
                                Empleado cli= (Empleado) comboLibro1.getSelectedItem();
                                facturasVentas = consultaVenta.getAllFacturaVentaPorEmpleado(cli);
                                cargarTablaFactura(facturasVentas);
                                calcular();
                            }
                            if(jcbbuscar.getSelectedItem().toString().contentEquals("LIBRO"))
                            {
                                Libro libro= (Libro) comboLibro1.getSelectedItem();
                                facturasVentas = consultaVenta.getAllFacturaVentaPorLibro(libro);
                                cargarTablaFactura(facturasVentas);
                                calcular();
                            }
                        }
                    }
                    }
                    else
                    {
                        facturasVentas = consultaVenta.getAllFacturas();
                        cargarTablaFactura(facturasVentas);
                        calcular();
                    }
                    limpiar();
                    jXTaskPane2.setCollapsed(true);
         }
    }//GEN-LAST:event_jbAnularActionPerformed

    private void jcbbuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbbuscarActionPerformed
        if(jcbbuscar.getSelectedItem().toString().contentEquals("CLIENTE"))
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
        if(jcbbuscar.getSelectedItem().toString().contentEquals("EMPLEADO"))
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
            facturasVentas = consultaVenta.getAllFacturaVentaPorFechayLibro(libro,fedes, fehas);
            cargarTablaFactura(facturasVentas);
            calcular();
        }
        if(jcbbuscar.getSelectedItem().toString().contentEquals("CLIENTE"))
        {
            fedes=new java.sql.Date(dateDes.getTime());
            fehas=new java.sql.Date(dateHas.getTime());
            Cliente cli= (Cliente) comboLibro1.getSelectedItem();
            facturasVentas = consultaVenta.getAllFacturaVentaPorFechayCliente(cli,fedes, fehas);
            cargarTablaFactura(facturasVentas);
            calcular();
        }
        if(jcbbuscar.getSelectedItem().toString().contentEquals("EMPLEADO"))
        {
            fedes=new java.sql.Date(dateDes.getTime());
            fehas=new java.sql.Date(dateHas.getTime());
            Empleado cli= (Empleado) comboLibro1.getSelectedItem();
            facturasVentas = consultaVenta.getAllFacturaVentaPorFechayEmpleado(cli,fedes, fehas);
            cargarTablaFactura(facturasVentas);
            calcular();
        }
        }
        else
        {
            if(dateDes == null && dateHas == null)
            {
                if(jcbbuscar.getSelectedItem().toString().contentEquals("CLIENTE"))
                {
                    Cliente cli= (Cliente) comboLibro1.getSelectedItem();
                    facturasVentas = consultaVenta.getAllFacturaVentaPorCliente(cli);
                    cargarTablaFactura(facturasVentas);
                    calcular();
                }
                if(jcbbuscar.getSelectedItem().toString().contentEquals("EMPLEADO"))
                {
                    Empleado cli= (Empleado) comboLibro1.getSelectedItem();
                    facturasVentas = consultaVenta.getAllFacturaVentaPorEmpleado(cli);
                    cargarTablaFactura(facturasVentas);
                    calcular();
                }
                if(jcbbuscar.getSelectedItem().toString().contentEquals("LIBRO"))
                {
                    Libro libro= (Libro) comboLibro1.getSelectedItem();
                    facturasVentas = consultaVenta.getAllFacturaVentaPorLibro(libro);
                    cargarTablaFactura(facturasVentas);
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
        facturasVentas = consultaVenta.getAllFacturas();
        cargarTablaFactura(facturasVentas);
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
        
        for(int i=0;i<jTableFacturas.getRowCount();i++)
        {
            ListaFacturas facturas = new ListaFacturas(jTableFacturas.getValueAt(i, 0).toString(),jTableFacturas.getValueAt(i, 1).toString(),jTableFacturas.getValueAt(i, 4).toString(),jTableFacturas.getValueAt(i, 6).toString(),jTableFacturas.getValueAt(i, 2).toString());
            lista.add(facturas);
            if(i == 0)
            {
                try {
                    fd = df.parse(jTableFacturas.getValueAt(i, 1).toString());
                } catch (ParseException ex) {
                    Logger.getLogger(BusquedaFactura.class.getName()).log(Level.SEVERE, null, ex);
                }
                fh = fd;
            }
            else
            {
                try {
                    fe = df.parse(jTableFacturas.getValueAt(i, 1).toString());
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
            JasperReport reporte = (JasperReport) JRLoader.loadObject("ReporteVentas.jasper");
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

    private void comboLibro1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboLibro1ActionPerformed
        /*if(jcbbuscar.getSelectedItem().toString().contentEquals("CLIENTE"))
        {
            Cliente cli= (Cliente) comboLibro1.getSelectedItem();
            facturasVentas = consultaVenta.getAllFacturaVentaPorCliente(cli);
            cargarTablaFactura(facturasVentas);
            calcular();
        }
        if(jcbbuscar.getSelectedItem().toString().contentEquals("LIBRO"))
        {
            Libro libro= (Libro) comboLibro1.getSelectedItem();
            facturasVentas = consultaVenta.getAllFacturaVentaPorLibro(libro);
            cargarTablaFactura(facturasVentas);
            calcular();
        }*/
    }//GEN-LAST:event_comboLibro1ActionPerformed

    private void jTfBuscarFacturaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTfBuscarFacturaKeyTyped
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
    }//GEN-LAST:event_jTfBuscarFacturaKeyTyped

    private void jCkHabilitadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCkHabilitadosActionPerformed
        Boolean estado = jCkHabilitados.isSelected();
        if(estado)
        {
            jButton2.setEnabled(false);
        }
        else
        {
            jButton2.setEnabled(true);
        }
        dateDes=jDateChooser3.getDate();
        dateHas=jDateChooser4.getDate();
        facturasVentas = new ArrayList<>();
        if(r == true)
        {
        if(dateDes != null && dateHas != null)
        {
        if(jcbbuscar.getSelectedItem().toString().contentEquals("LIBRO"))
        {
            fedes=new java.sql.Date(dateDes.getTime());
            fehas=new java.sql.Date(dateHas.getTime());
            Libro libro= (Libro) comboLibro1.getSelectedItem();
            facturasVentas = consultaVenta.getAllFacturaVentaPorFechayLibro(libro,fedes, fehas);
            cargarTablaFactura(facturasVentas);
            calcular();
        }
        if(jcbbuscar.getSelectedItem().toString().contentEquals("CLIENTE"))
        {
            fedes=new java.sql.Date(dateDes.getTime());
            fehas=new java.sql.Date(dateHas.getTime());
            Cliente cli= (Cliente) comboLibro1.getSelectedItem();
            facturasVentas = consultaVenta.getAllFacturaVentaPorFechayCliente(cli,fedes, fehas);
            cargarTablaFactura(facturasVentas);
            calcular();
        }
        if(jcbbuscar.getSelectedItem().toString().contentEquals("EMPLEADO"))
        {
            fedes=new java.sql.Date(dateDes.getTime());
            fehas=new java.sql.Date(dateHas.getTime());
            Empleado cli= (Empleado) comboLibro1.getSelectedItem();
            facturasVentas = consultaVenta.getAllFacturaVentaPorFechayEmpleado(cli,fedes, fehas);
            cargarTablaFactura(facturasVentas);
            calcular();
        }
        }
        else
        {
            if(dateDes == null && dateHas == null)
            {
                if(jcbbuscar.getSelectedItem().toString().contentEquals("CLIENTE"))
                {
                    Cliente cli= (Cliente) comboLibro1.getSelectedItem();
                    facturasVentas = consultaVenta.getAllFacturaVentaPorCliente(cli);
                    cargarTablaFactura(facturasVentas);
                    calcular();
                }
                if(jcbbuscar.getSelectedItem().toString().contentEquals("EMPLEADO"))
                {
                    Empleado cli= (Empleado) comboLibro1.getSelectedItem();
                    facturasVentas = consultaVenta.getAllFacturaVentaPorEmpleado(cli);
                    cargarTablaFactura(facturasVentas);
                    calcular();
                }
                if(jcbbuscar.getSelectedItem().toString().contentEquals("LIBRO"))
                {
                    Libro libro= (Libro) comboLibro1.getSelectedItem();
                    facturasVentas = consultaVenta.getAllFacturaVentaPorLibro(libro);
                    cargarTablaFactura(facturasVentas);
                    calcular();
                }
            }
        }
        }
        else
        {
            facturasVentas = consultaVenta.getAllFacturas();
            cargarTablaFactura(facturasVentas);
            calcular();
        }
    }//GEN-LAST:event_jCkHabilitadosActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnCancelar1;
    private javax.swing.JButton btnQuitar;
    private org.jdesktop.swingx.JXComboBox comboCliente;
    private org.jdesktop.swingx.JXComboBox comboEmpleado;
    private org.jdesktop.swingx.JXComboBox comboLibro;
    private org.jdesktop.swingx.JXComboBox comboLibro1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JCheckBox jCkHabilitados;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private com.toedter.calendar.JDateChooser jDateChooser3;
    private com.toedter.calendar.JDateChooser jDateChooser4;
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
    private javax.swing.JPanel jPanelBusqueda;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTableFacturas;
    private javax.swing.JTable jTableLineaFactura;
    private javax.swing.JTextField jTfBuscarFactura;
    private org.jdesktop.swingx.JXTaskPane jXTaskPane2;
    private javax.swing.JButton jbAnular;
    private javax.swing.JButton jbbuscar;
    private javax.swing.JComboBox jcbbuscar;
    private javax.swing.JTextField jtfBruto;
    private javax.swing.JTextField jtfCantidad;
    private javax.swing.JTextField jtfIVA;
    private javax.swing.JTextField jtfImporte;
    private javax.swing.JTextField jtfNroFactura;
    private javax.swing.JTextField jtfTotal;
    private javax.swing.JTextField jtftfv;
    // End of variables declaration//GEN-END:variables
}
