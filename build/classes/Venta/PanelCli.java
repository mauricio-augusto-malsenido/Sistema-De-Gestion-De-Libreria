package Venta;
import Venta.Cliente;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javax.swing.text.MaskFormatter;
import mapa.ConsultaMapa;
import mapa.localidad;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

/**
 *
 * @author SEBASTIAN
 */
public class PanelCli extends javax.swing.JPanel {
private ConsultaVenta consultaVenta;
private ConsultaMapa consultaMapa;
private Cliente cliente;
//private ConsultaOrdenServicio oConsultaOrden;
private TableRowSorter<TableModel> sorter;//para ordenar la tabla
private int v=0,indiceModelo=0,bandera,id;
private boolean r = false;
private boolean s = false;
private boolean e = false;
private boolean t = false;
private String cc = "";
private String dc = "";

public PanelCli() {
        initComponents();
        
        //instancia el objeto y se conecta con la BD
        consultaVenta = new ConsultaVenta();
        consultaMapa = new ConsultaMapa();
        jXTaskPane2.setCollapsed(true);
        jButton1.setEnabled(false);
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
        AutoCompleteDecorator.decorate(this.jcbloc);
        AutoCompleteDecorator.decorate(this.jcbtdc);
        jTextFieldBuscarCliente.requestFocus();
        //Al escribir o borrar en el jtextfield el metodo addDocumentListener 
        //llama al metodo filtrar
        jTextFieldBuscarCliente.getDocument().addDocumentListener(
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
        
        //metodo para capturar doble click sobre jtable   
        jtableClientes.addMouseListener(new MouseAdapter() 
        {
           @Override
           public void mouseClicked(MouseEvent e) 
           {
             if(e.getClickCount()== 2){
              int fila = jtableClientes.rowAtPoint(e.getPoint());
              int columna = jtableClientes.columnAtPoint(e.getPoint());
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
              jButton1.setEnabled(true);
              jcbvista.setEnabled(false);
              jcbbuscar.setEnabled(false);
              jTextFieldBuscarCliente.setEditable(false);
              jTextFieldBuscarCliente.setEnabled(false);
             /*El método rowAtPoint() nos devuelve -1 si pinchamos en el JTable
              pero fuera de cualquier fila*/
              
                     if ((fila > -1) && (columna > -1))
                     {
                       String dni;
                       v=jtableClientes.getSelectedRow();//n° fila selccionada
                       indiceModelo = jtableClientes.convertRowIndexToModel (v);//convierte el indice de la vista en el indice del modelo 
                       jXTaskPane2.setCollapsed(false);
                       jtableClientes.setEnabled(false);
                       jXTaskPane2.setTitle("Modificar Cliente");
                       bandera=1;
                       cliente = consultaVenta.getClientePorId(getIdCliente(indiceModelo));
                       List<FacturaVenta> fv = consultaVenta.getAllFacturaVentaPorCliente(cliente.getIdCliente());
                       if(fv.isEmpty() == false)
                       {
                           jcbtdc.setEnabled(false);
                           jButton1.setText("Deshabilitar");
                       }
                       else
                       {
                           jcbtdc.setEnabled(true);
                           jButton1.setText("Eliminar");
                       }
                       if(cliente.getEstadoCliente() == false)
                       {
                           jcbtdc.setEnabled(false);
                           jButton1.setText("Habilitar");
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
                           btnguardar1.setEnabled(false);
                       }
                       cc = cliente.getCuitCliente();
                       if(cliente.getDniCliente() == 0)
                       {
                           dni = "";
                       }
                       else
                       {
                           dni = cliente.getDniCliente() + "";
                       }
                       dc = dni;
                       jtfdni.setText(dni);
                       jtfcuit.setText(cliente.getCuitCliente());
                       jtfnombre.setText(cliente.getNombreCliente());
                       jtfdir.setText(cliente.getDireccionCliente());
                       jtftel.setText(cliente.getTelefonoCLiente());
                       jtfemail.setText(cliente.getEmailCliente());
                       localidad loc = consultaMapa.getLocalidadPorId(cliente.getIdLocalidad());
                       jcbloc.setSelectedItem(loc);
                       TipoCliente tc = consultaVenta.getTipoClientePorId(cliente.getIdTipoCliente());
                       jcbtdc.setSelectedItem(tc);
                     }
             }
           }
        });
        
        cargarTabla();
        
    }

private void filtrar() {
        RowFilter<TableModel, Object> rf = null;
        int indiceColumnaTabla = 0;
        if(jcbbuscar.getSelectedItem().toString().contentEquals("NOMBRE"))
        {
            indiceColumnaTabla = 3;
        }
        if(jcbbuscar.getSelectedItem().toString().contentEquals("CUIT"))
        {
            indiceColumnaTabla = 2;
        }
        if(jcbbuscar.getSelectedItem().toString().contentEquals("DNI"))
        {
            indiceColumnaTabla = 1;
        }
        try {
            rf = RowFilter.regexFilter(jTextFieldBuscarCliente.getText().toUpperCase(), indiceColumnaTabla);
        } catch (java.util.regex.PatternSyntaxException e) {
        }
        sorter.setRowFilter(rf);
    }

private void cargarTabla() {
        //Crea una lista de clientes y la carga instanciando al metodo getAllCliente();
        List<Cliente> lista;
        if(jcbvista.getSelectedItem().toString().contentEquals("HABILITADOS"))
        {
            lista = consultaVenta.getAllCliente2("CONSUMIDOR FINAL",true);
        }
        else
        {
            lista = consultaVenta.getAllCliente2("CONSUMIDOR FINAL",false);
        }
        String[] columnNames = {"Id", "DNI","CUIT", "Nombre", "Direccion","Localidad","Tipo"};
        int[] anchos = {5,30,45,100,50,120,120};
        Object[][] data = new Object[lista.size()][columnNames.length];

        for (int i = 0; i < lista.size(); i++) {
            
            String dni;
            localidad l = consultaMapa.getLocalidadPorId(lista.get(i).getIdLocalidad());
            TipoCliente tc = consultaVenta.getTipoClientePorId(lista.get(i).getIdTipoCliente());
            if(lista.get(i).getDniCliente() == 0)
            {
                dni = "";
            }
            else
            {
                dni = "" + lista.get(i).getDniCliente();
            }
            data[i][0] = lista.get(i).getIdCliente();
            data[i][1] = dni;
            data[i][2] = lista.get(i).getCuitCliente();
            data[i][3] = lista.get(i).getNombreCliente();
            data[i][4] = lista.get(i).getDireccionCliente();
            data[i][5] = l.getNombreLocalidad();
            data[i][6] = tc.getNombreTipoCliente();
            
        }
        
       DefaultTableModel dftm = new DefaultTableModel(data, columnNames)
                {
		//metodo para que las celdas del jtable sean no-editables	
                    @Override
			
                        public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
       jtableClientes.setModel(dftm);
       
        for(int i=0;i<jtableClientes.getColumnCount();i++)
        {
            jtableClientes.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        for(int i=0;i<jtableClientes.getColumnCount();i++)
        {
            jtableClientes.getColumnModel().getColumn(i).setCellRenderer(tcr);
        }
        sorter = new TableRowSorter<TableModel>(dftm);
        jtableClientes.setRowSorter(sorter);
        jtableClientes.getRowSorter().toggleSortOrder(0);
        jtableClientes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }
    
private int getFila(int im){
        
    int idcli;
        String[] fila= new String[1];//almaceno los valores del registro seleccionado en el string "fila"
        fila[0]=""+jtableClientes.getModel().getValueAt(im, 0);
                
        //Cliente ec=new Cliente(Integer.parseInt(fila[0]),Integer.parseInt(fila[1]), fila[2], fila[3],Integer.parseInt(fila[4]), fila[5],fila[6]);
        
        idcli=Integer.parseInt(fila[0]);
        return  idcli;
    }
private int getIdCliente(int im){
        String[] fila= new String[1];//almaceno los valores del registro seleccionado en el string "fila"
        fila[0]=""+jtableClientes.getModel().getValueAt(im, 0);
        
        int idRep=Integer.parseInt(fila[0]);
        
        return  idRep;
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
        jtfnombre.setEnabled(true);
        jtfnombre.setEditable(true);
        jtfdir.setEnabled(true);
        jtfdir.setEditable(true);
        jtftel.setEnabled(true);
        jtftel.setEditable(true);
        jtfemail.setEnabled(true);
        jtfemail.setEditable(true);
        jcbtdc.setEnabled(true);
        jcbloc.setEnabled(true);
        jcbvista.setEnabled(true);
        jcbbuscar.setEnabled(true);
        jButton1.setEnabled(false);
        jButton1.setText("Eliminar");
        btnguardar1.setEnabled(true);
        jTextFieldBuscarCliente.setEditable(true);
        jTextFieldBuscarCliente.setEnabled(true);
        jtableClientes.setEnabled(true);
        jtfdni.setText("");
        jtfcuit.setText("");
        jtfnombre.setText("");
        jtfdir.setText("");
        jtftel.setText("");
        jtfemail.setText("");
        jTextFieldBuscarCliente.setText("");
        jcbbuscar.setSelectedIndex(0);
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
            jcbvista.setEnabled(false);
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
            jcbvista.setEnabled(false);
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

        jPopupMenuListaCliente = new javax.swing.JPopupMenu();
        jMenuItemModificar = new javax.swing.JMenuItem();
        jMenuItemEliminar = new javax.swing.JMenuItem();
        jpmCli = new javax.swing.JPopupMenu();
        jmiAbrir = new javax.swing.JMenuItem();
        jmiElimR = new javax.swing.JMenuItem();
        jLabel1 = new javax.swing.JLabel();
        jTextFieldBuscarCliente = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtableClientes = new javax.swing.JTable();
        jXTaskPane2 = new org.jdesktop.swingx.JXTaskPane();
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
        jButton1 = new javax.swing.JButton();
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
        jcbvista = new javax.swing.JComboBox();
        jcbbuscar = new javax.swing.JComboBox();
        jLabel10 = new javax.swing.JLabel();

        jMenuItemModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/abrir.png"))); // NOI18N
        jMenuItemModificar.setText("Abrir Cliente");
        jMenuItemModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemModificarActionPerformed(evt);
            }
        });
        jPopupMenuListaCliente.add(jMenuItemModificar);

        jMenuItemEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/eliminar.png"))); // NOI18N
        jMenuItemEliminar.setText("Eliminar Cliente");
        jMenuItemEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemEliminarActionPerformed(evt);
            }
        });
        jPopupMenuListaCliente.add(jMenuItemEliminar);

        jmiAbrir.setText("Abrir");
        jmiAbrir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiAbrirActionPerformed(evt);
            }
        });
        jpmCli.add(jmiAbrir);

        jmiElimR.setText("Eliminar");
        jmiElimR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiElimRActionPerformed(evt);
            }
        });
        jpmCli.add(jmiElimR);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setText("Buscar Cliente:");

        jTextFieldBuscarCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldBuscarClienteKeyTyped(evt);
            }
        });

        jtableClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Codigo Cliente", "Razon Social", "Telefono", "Celular"
            }
        ));
        jtableClientes.setComponentPopupMenu(jpmCli);
        jScrollPane1.setViewportView(jtableClientes);

        jXTaskPane2.setTitle("Nuevo Cliente");
        jXTaskPane2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jXTaskPane2MouseClicked(evt);
            }
        });

        jPanel4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel13.setText("DNI (sin puntos)");

        jtfdni.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtfdniFocusLost(evt);
            }
        });
        jtfdni.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtfdniActionPerformed(evt);
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

        jLabel15.setText("TELEFONO (Opcional)");

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

        jLabel16.setText("EMAIL (Opcional)");

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

        jButton1.setText("Eliminar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
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
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jcbtdc, 0, 209, Short.MAX_VALUE)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jLabel8)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jtfdni, javax.swing.GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE)
                            .addComponent(jLabel2)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(23, 23, 23)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE))
                            .addComponent(jtfcuit, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jtfnombre, javax.swing.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE)
                            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(btnguardar1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancelar1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton1))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jcbloc, 0, 208, Short.MAX_VALUE)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jLabel9)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jtfdir, javax.swing.GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE)
                            .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jtftel, javax.swing.GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jtfemail, javax.swing.GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(12, 12, 12)))
                .addGap(24, 24, 24))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addGap(2, 2, 2)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jtfdni, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jtfcuit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel3)))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel14)
                                    .addComponent(jLabel18))
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
                                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel7)
                                        .addComponent(jLabel6))))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel16)
                                    .addComponent(jLabel15))
                                .addGap(1, 1, 1)
                                .addComponent(jtfemail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(42, 42, 42))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel20)
                                .addGap(22, 22, 22))
                            .addComponent(jcbtdc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnguardar1)
                            .addComponent(btnCancelar1)
                            .addComponent(jButton1)))))
        );

        jXTaskPane2.getContentPane().add(jPanel4);

        jcbvista.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "HABILITADOS", "DESHABILITADOS" }));
        jcbvista.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbvistaActionPerformed(evt);
            }
        });

        jcbbuscar.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "NOMBRE", "DNI", "CUIT" }));
        jcbbuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbbuscarActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel10.setText("Ver solo:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jcbbuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(jTextFieldBuscarCliente)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jcbvista, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jXTaskPane2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane1))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextFieldBuscarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcbvista, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcbbuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jXTaskPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItemModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemModificarActionPerformed

    }//GEN-LAST:event_jMenuItemModificarActionPerformed

    private void jMenuItemEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemEliminarActionPerformed

    }//GEN-LAST:event_jMenuItemEliminarActionPerformed

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
        if(jtfcuit.getText().isEmpty() == false && (consultaVenta.existeClientePorCUIT(jtfcuit.getText()) != null && bandera == 1 && e == true))
        {  
            r = true;
        }
        else
        {
            System.out.println("HOOOOOOOOOOOOOOOLAAAAAAAAAAAA");
            r = false;
        }
        if(cc != null && jtfcuit.getText().contentEquals(cc))
        {
            r = false;
        }
        if(jtfdni.getText().isEmpty() == false && (consultaVenta.existeClientePorDNI(Integer.parseInt(jtfdni.getText())) != null && bandera == 1 && e == true))
        {
            s = true;
        }
        else
        {
            s = false;
        }
        if(jtfdni.getText().contentEquals(dc))
        {
            s = false;
        }
        if(jcbtdc.getSelectedItem() != null && jcbloc.getSelectedItem() != null)
        {
            if(jcbtdc.getSelectedItem().toString().contentEquals("RESPONSABLE INSCRIPTO")||jcbtdc.getSelectedItem().toString().contentEquals("RESPONSABLE NO INSCRIPTO")||jcbtdc.getSelectedItem().toString().contentEquals("MONOTRIBUTISTA"))
        {    
        boolean b = this.verificarCuit(jtfcuit.getText());
        boolean c = this.comprobarCUIT(jtfcuit.getText());
        if(b == false|| (c==false && t==false) || jtfnombre.getText().isEmpty() || jtfdir.getText().isEmpty() || (consultaVenta.existeClientePorCUIT(jtfcuit.getText()) != null && bandera == 0 || r == true))
        {
            System.out.println("NOLA " +t+ " " +c+ " " +r);
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
            if(t == true && d == false && (consultaVenta.existeClientePorCUIT(jtfcuit.getText()) != null && bandera == 0 || r == true))
            {
                 System.out.println(""+consultaVenta.existeClientePorCUIT(jtfcuit.getText()));
                 jtfcuit.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
                 jLabel3.setText("CUIT existente");
                 jLabel3.setVisible(true);
            }
        }
        else
        {
            if(bandera==1)
        {
            int dni = 0;
            System.out.println("NOLA " +t+ " " +c+ " " +r);
            System.out.println("HOLA");
            cliente = consultaVenta.getClientePorId(getIdCliente(indiceModelo));
            localidad loc= (localidad) jcbloc.getSelectedItem();
            TipoCliente tc= (TipoCliente) jcbtdc.getSelectedItem();
            cliente.setCuitCliente(jtfcuit.getText());
            cliente.setDni(dni);
            cliente.setNombreCliente(jtfnombre.getText().toUpperCase());
            cliente.setDireccionCliente(jtfdir.getText().toUpperCase());
            cliente.setTelefonoCLiente(jtftel.getText());
            cliente.setEmailCliente(jtfemail.getText());
            cliente.setIdLocalidad(loc.getIdLocalidad());
            cliente.setIdTipoCliente(tc.getIdTipoCliente());
            consultaVenta.modificarCliente(cliente);
            JOptionPane.showMessageDialog(this,"Cliente Modificado");
            cargarTabla();
            bandera=0;
            j = true;
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
                cargarTabla();
                bandera=0;
                j = true;
             
        }
        jButton1.setText("Eliminar");
        jButton1.setEnabled(false);
        jcbtdc.setEnabled(true);
        jXTaskPane2.setCollapsed(true);
        jXTaskPane2.setTitle("Nuevo Cliente");
        limpiar2();
        }
        }
        if(j == false && jcbtdc.getSelectedItem().toString().contentEquals("EXENTO"))
        {
            System.err.println("Se repite de nuevo");
            boolean c = this.verificarDni(jtfdni.getText());
            if(c == false || jtfnombre.getText().isEmpty() || jtfdir.getText().isEmpty() || (consultaVenta.existeClientePorDNI(Integer.parseInt(jtfdni.getText())) != null && bandera == 0 || s == true))
        {
            if(c==false)
            {
                if(jtfdni.getText().isEmpty())
                {
                    
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
            if(d == false && c == true && (consultaVenta.existeClientePorDNI(Integer.parseInt(jtfdni.getText())) != null && bandera == 0 || s == true))
            {
                 jtfdni.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
                 jLabel2.setText("DNI existente");
                 jLabel2.setVisible(true);
            }
        }
        else
        {
            if(bandera==1)
        {
            String cuit = "";
            cliente = consultaVenta.getClientePorId(getIdCliente(indiceModelo));
            localidad loc= (localidad) jcbloc.getSelectedItem();
            TipoCliente tc= (TipoCliente) jcbtdc.getSelectedItem();
            cliente.setDni(Integer.parseInt(jtfdni.getText()));
            cliente.setCuitCliente(cuit);
            cliente.setNombreCliente(jtfnombre.getText().toUpperCase());
            cliente.setDireccionCliente(jtfdir.getText().toUpperCase());
            cliente.setTelefonoCLiente(jtftel.getText());
            cliente.setEmailCliente(jtfemail.getText());
            cliente.setIdLocalidad(loc.getIdLocalidad());
            cliente.setIdTipoCliente(tc.getIdTipoCliente());
            consultaVenta.modificarCliente(cliente);
            JOptionPane.showMessageDialog(this,"Cliente Modificado");
            cargarTabla();
            bandera=0;
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
            cargarTabla();
            bandera=0;
        }
        jButton1.setText("Eliminar");
        jButton1.setEnabled(false);
        jcbtdc.setEnabled(true);
        jXTaskPane2.setCollapsed(true);
        jXTaskPane2.setTitle("Nuevo Cliente");
        jcbtdc.setEnabled(true);
        limpiar2();
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
        if(jcbvista.getSelectedItem().toString().contentEquals("HABILITADOS") || jXTaskPane2.getTitle().contentEquals("Nuevo Cliente"))
        {
            int respuesta=JOptionPane.showConfirmDialog(null, "¿Confirma la cancelación? \n Los datos no seran guardados","Advertencia", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            //confirmamos la eliminacion
            if(respuesta == 0)
            {
                cargarTabla();
                jcbtdc.setEnabled(true);
                jButton1.setText("Eliminar");
                jButton1.setEnabled(false);
                jXTaskPane2.setCollapsed(true);
                jXTaskPane2.setTitle("Nuevo Cliente");
                limpiar2();
                bandera = 0;
                //this.removeAll();
                //this.repaint();
                //this.revalidate();
            }
        }
        else
        {
                cargarTabla();
                jXTaskPane2.setCollapsed(true);
                jXTaskPane2.setTitle("Nuevo Cliente");
                limpiar2();
                bandera = 0;
                //this.removeAll();
                //this.repaint();
                //this.revalidate();
        }
    }//GEN-LAST:event_btnCancelar1ActionPerformed

    private void jmiAbrirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiAbrirActionPerformed

    }//GEN-LAST:event_jmiAbrirActionPerformed

    private void jmiElimRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiElimRActionPerformed

    }//GEN-LAST:event_jmiElimRActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
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
        v=jtableClientes.getSelectedRow();//n° fila selccionada
            indiceModelo = jtableClientes.convertRowIndexToModel (v);//convierte el indice de la vista en el indice del modelo
            cliente = consultaVenta.getClientePorId(getIdCliente(indiceModelo));
            if(jButton1.getText().contentEquals("Eliminar"))
            {
                int respuesta=JOptionPane.showConfirmDialog(null, "¿Realmente desea quitar este cliente de la lista?","Advertencia", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                //confirmamos la eliminacion
                if(respuesta == 0)
                {
                    consultaVenta.eliminarCliente(cliente.getIdCliente());
                    cargarTabla();
                    bandera=0;
                    id = id + 1;
                    jXTaskPane2.setCollapsed(true);
                    jXTaskPane2.setTitle("Nuevo Cliente");
                    limpiar2();
                    btnguardar1.setEnabled(true);
                    jButton1.setEnabled(false);
                }
            }
            if(jButton1.getText().contentEquals("Deshabilitar"))
            {
                int respuesta=JOptionPane.showConfirmDialog(null, "¿Realmente desea deshabilitar este cliente?","Advertencia", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                //confirmamos la eliminacion
                if(respuesta == 0)
                {
                    cliente.setEstadoCliente(false);
                    consultaVenta.modificarCliente2(cliente);
                    jcbtdc.setEnabled(true);
                    jButton1.setText("Eliminar");
                    cargarTabla();
                    bandera=0;
                    jXTaskPane2.setCollapsed(true);
                    jXTaskPane2.setTitle("Nuevo Cliente");
                    limpiar2();
                    btnguardar1.setEnabled(true);
                    jButton1.setEnabled(false);
                }
            }
            if(jButton1.getText().contentEquals("Habilitar"))
            {
                int respuesta=JOptionPane.showConfirmDialog(null, "¿Realmente desea habilitar este cliente?","Advertencia", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                //confirmamos la eliminacion
                if(respuesta == 0)
                {
                    cliente.setEstadoCliente(true);
                    consultaVenta.modificarCliente2(cliente);
                    jcbtdc.setEnabled(true);
                    jtfnombre.setEditable(true);
                    jtfnombre.setEnabled(true);
                    jtfdir.setEditable(true);
                    jtfdir.setEnabled(true);
                    jtftel.setEditable(true);
                    jtftel.setEnabled(true);
                    jtfemail.setEditable(true);
                    jtfemail.setEnabled(true);
                    jcbloc.setEnabled(true);
                    btnguardar1.setEnabled(true);
                    jButton1.setText("Eliminar");
                    jButton1.setEnabled(false);
                    cargarTabla();
                    bandera=0;
                    jXTaskPane2.setCollapsed(true);
                    jXTaskPane2.setTitle("Nuevo Cliente");
                    limpiar2();
                }
            }
    }//GEN-LAST:event_jButton1ActionPerformed

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

    private void jtfdirKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfdirKeyTyped
        jtfdir.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
        jLabel5.setVisible(false);
        char c=evt.getKeyChar();
            
        
          if(Character.isLetterOrDigit(c) == false && c != '\b' && c != '.' && c != ' ' && c != '-') {
              getToolkit().beep();
              
              evt.consume();
              
              jtfdir.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
              jLabel5.setText("Debe ingresar números , letras , . o - ");
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

    private void jtfnombreKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfnombreKeyReleased

    }//GEN-LAST:event_jtfnombreKeyReleased

    private void jtfnombreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfnombreKeyPressed

    }//GEN-LAST:event_jtfnombreKeyPressed

    private void jtfdirKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfdirKeyReleased

    }//GEN-LAST:event_jtfdirKeyReleased

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
        if(jcbvista.getSelectedItem().toString().contentEquals("HABILITADOS") && jcbtdc.getSelectedItem() != null && jcbloc.getSelectedItem() != null)
        {
            if(jcbtdc.getSelectedItem().toString().contentEquals("RESPONSABLE INSCRIPTO"))
        {
            jtfcuit.setEnabled(true);
            jtfcuit.setEditable(true);
            jtfdni.setEnabled(false);
            jtfdni.setEditable(false);
            jtfdni.setText("");
        }
        if(jcbtdc.getSelectedItem().toString().contentEquals("RESPONSABLE NO INSCRIPTO"))
        {
            jtfcuit.setEnabled(true);
            jtfcuit.setEditable(true);
            jtfdni.setEnabled(false);
            jtfdni.setEditable(false);
            jtfdni.setText("");
        }
        if(jcbtdc.getSelectedItem().toString().contentEquals("MONOTRIBUTISTA"))
        {
            jtfcuit.setEnabled(true);
            jtfcuit.setEditable(true);
            jtfdni.setEnabled(false);
            jtfdni.setEditable(false);
            jtfdni.setText("");
        }
        if(jcbtdc.getSelectedItem().toString().contentEquals("EXENTO"))
        {
            jtfcuit.setEnabled(false);
            jtfcuit.setEditable(false);
            jtfcuit.setText("");
            jtfdni.setEnabled(true);
            jtfdni.setEditable(true);
        }
        }
        if(jXTaskPane2.getTitle().contentEquals("Nuevo Cliente") && jcbtdc.getSelectedItem() != null && jcbloc.getSelectedItem() != null)
        {
            if(jcbtdc.getSelectedItem().toString().contentEquals("RESPONSABLE INSCRIPTO"))
        {
            jtfcuit.setEnabled(true);
            jtfcuit.setEditable(true);
            jtfdni.setEnabled(false);
            jtfdni.setEditable(false);
            jtfdni.setText("");
        }
        if(jcbtdc.getSelectedItem().toString().contentEquals("RESPONSABLE NO INSCRIPTO"))
        {
            jtfcuit.setEnabled(true);
            jtfcuit.setEditable(true);
            jtfdni.setEnabled(false);
            jtfdni.setEditable(false);
            jtfdni.setText("");
        }
        if(jcbtdc.getSelectedItem().toString().contentEquals("MONOTRIBUTISTA"))
        {
            jtfcuit.setEnabled(true);
            jtfcuit.setEditable(true);
            jtfdni.setEnabled(false);
            jtfdni.setEditable(false);
            jtfdni.setText("");
        }
        if(jcbtdc.getSelectedItem().toString().contentEquals("EXENTO"))
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
        else
        {
            if(consultaVenta.existeClientePorCUIT(jtfcuit.getText()) != null && bandera == 1 && e == true)
            {  
                 r = true;
                 t = true;
                 jtfcuit.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
                 jLabel3.setText("CUIT existente");
                 jLabel3.setVisible(true);
            }
            if(jtfcuit.getText().contentEquals(cc))
            {
                System.out.println("POR ACA");
            r = false;
            jtfcuit.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
            jLabel3.setVisible(false);
            }
        }
            if(consultaVenta.existeClientePorCUIT(jtfcuit.getText()) != null && bandera == 0 && b == false)
            {
                 t = true;
                 jtfcuit.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
                 jLabel3.setText("CUIT existente");
                 jLabel3.setVisible(true);
            }
            if(c==false && f==false && t==false && d==true && b==false)
            {
 
                 jtfcuit.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
                 jLabel3.setText("El CUIT ingresado es inválido");
                 jLabel3.setVisible(true);
                 
            }
            System.out.println("r" +r);
            
    }//GEN-LAST:event_jtfcuitFocusLost

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
        else
        {
            if(consultaVenta.existeClientePorDNI(Integer.parseInt(jtfdni.getText())) != null && bandera == 1 && e == true)
            {
                 s = true;
                 jtfdni.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
                 jLabel2.setText("DNI existente");
                 jLabel2.setVisible(true);
            }
            if(jtfdni.getText().contentEquals(dc))
            {
            s = false;
            jtfdni.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
            jLabel2.setVisible(false);
            }
        }
            if(b == false && (consultaVenta.existeClientePorDNI(Integer.parseInt(jtfdni.getText())) != null && bandera == 0))
            {
                 jtfdni.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
                 jLabel2.setText("DNI existente");
                 jLabel2.setVisible(true);
            }
    }//GEN-LAST:event_jtfdniFocusLost

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

    private void jtftelFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtftelFocusLost

    }//GEN-LAST:event_jtftelFocusLost

    private void jcbvistaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbvistaActionPerformed
        jXTaskPane2.setCollapsed(true);
        jXTaskPane2.setTitle("Nuevo Cliente");
        limpiar2();
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
        jcbtdc.setEnabled(true);
        jtfnombre.setEditable(true);
        jtfnombre.setEnabled(true);
        jtfdir.setEditable(true);
        jtfdir.setEnabled(true);
        jtftel.setEditable(true);
        jtftel.setEnabled(true);
        jtfemail.setEditable(true);
        jtfemail.setEnabled(true);
        jcbloc.setEnabled(true);
        btnguardar1.setEnabled(true);
        jButton1.setText("Eliminar");
        jButton1.setEnabled(false);
        if(jcbvista.getSelectedItem().toString().contentEquals("HABILITADOS"))
        {
            cargarTabla();
        }
        if(jcbvista.getSelectedItem().toString().contentEquals("DESHABILITADOS"))
        {
            cargarTabla();
        }   
        
    }//GEN-LAST:event_jcbvistaActionPerformed

    private void jXTaskPane2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jXTaskPane2MouseClicked
        if(jXTaskPane2.getTitle().contentEquals("Nuevo Cliente") && jcbtdc.getSelectedItem() != null && jcbloc.getSelectedItem() != null)
        {
            if(jcbtdc.getSelectedItem().toString().contentEquals("RESPONSABLE INSCRIPTO"))
        {
            jtfcuit.setEnabled(true);
            jtfcuit.setEditable(true);
            jtfdni.setEnabled(false);
            jtfdni.setEditable(false);
            jtfdni.setText("");
        }
        if(jcbtdc.getSelectedItem().toString().contentEquals("RESPONSABLE NO INSCRIPTO"))
        {
            jtfcuit.setEnabled(true);
            jtfcuit.setEditable(true);
            jtfdni.setEnabled(false);
            jtfdni.setEditable(false);
            jtfdni.setText("");
        }
        if(jcbtdc.getSelectedItem().toString().contentEquals("MONOTRIBUTISTA"))
        {
            jtfcuit.setEnabled(true);
            jtfcuit.setEditable(true);
            jtfdni.setEnabled(false);
            jtfdni.setEditable(false);
            jtfdni.setText("");
        }
        if(jcbtdc.getSelectedItem().toString().contentEquals("EXENTO"))
        {
            jtfcuit.setEnabled(false);
            jtfcuit.setEditable(false);
            jtfcuit.setText("");
            jtfdni.setEnabled(true);
            jtfdni.setEditable(true);
        }
        }
    }//GEN-LAST:event_jXTaskPane2MouseClicked

    private void jtfdniActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtfdniActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtfdniActionPerformed

    private void jcbbuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbbuscarActionPerformed
        jTextFieldBuscarCliente.setText("");
    }//GEN-LAST:event_jcbbuscarActionPerformed

    private void jTextFieldBuscarClienteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldBuscarClienteKeyTyped

    }//GEN-LAST:event_jTextFieldBuscarClienteKeyTyped

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar1;
    private javax.swing.JButton btnguardar1;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
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
    private javax.swing.JMenuItem jMenuItemEliminar;
    private javax.swing.JMenuItem jMenuItemModificar;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPopupMenu jPopupMenuListaCliente;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextFieldBuscarCliente;
    private org.jdesktop.swingx.JXTaskPane jXTaskPane2;
    private javax.swing.JComboBox jcbbuscar;
    private javax.swing.JComboBox jcbloc;
    private javax.swing.JComboBox jcbtdc;
    private javax.swing.JComboBox jcbvista;
    private javax.swing.JMenuItem jmiAbrir;
    private javax.swing.JMenuItem jmiElimR;
    private javax.swing.JPopupMenu jpmCli;
    private javax.swing.JTable jtableClientes;
    private javax.swing.JTextField jtfcuit;
    private javax.swing.JTextField jtfdir;
    private javax.swing.JTextField jtfdni;
    private javax.swing.JTextField jtfemail;
    private javax.swing.JTextField jtfnombre;
    private javax.swing.JTextField jtftel;
    // End of variables declaration//GEN-END:variables
}
