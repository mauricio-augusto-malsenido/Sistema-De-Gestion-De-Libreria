
package Empleados;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import static java.awt.image.ImageObserver.WIDTH;
import java.util.Date;
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
import mapa.ConsultaMapa;
import mapa.localidad;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

/**
 *
 * @author SEBASTIAN
 */
public final class jpEmpleados extends javax.swing.JPanel {
private ConsultaEmpleados2 oConsultaBoleta;
private ConsultaUsuario oConsultaUsuario;
private ConsultaMapa oConsultaMapa;
private TableRowSorter<TableModel> sorter;//para ordenar la tabla
private int v=0,indiceModelo=0,bandera=0,id=0,control,longitud,longitud2,idusuario;
private Date dateFing;
private java.sql.Date fing;
private Empleado pEmpleado;
private localidad pLocalidad;
private EstadoCivilEmpleado pEstadoCivil;
private CategoriaEmpleado pCategoria;
private Usuario pUsuario;
private boolean controLabel=false,controlN,controlC,controlT,e=true;
/**
     * Creates new form jpProveedores
     */
//BANDERA 0 NUEVO
//BANDERA 1 MODIFICACIONC 
//CONTROL 0 ABM
//CONTROL 1 ALTAS
     public jpEmpleados(int c) {
        initComponents();
        control=c;
        oConsultaBoleta = new ConsultaEmpleados2();
        oConsultaMapa = new ConsultaMapa();
        oConsultaUsuario = new ConsultaUsuario();
        bandera=0;
        CargarComboCategoria();;
        CargarComboLocalidades();
        CargarComboEstadoCivil();
        CargarComboUsuarios();
        AutoCompleteDecorator.decorate(this.jcbLocalidad);
        AutoCompleteDecorator.decorate(this.jcbCategoria);
        AutoCompleteDecorator.decorate(this.jcbEstadoCivil);
        modoNuevoEmpleado();
        if(control==1){
        jLBuscar.setVisible(false);
        jtfbuscarCliente.setVisible(false);
        jScrollPane1.setVisible(false);
        jdateFing.getDateEditor().setEnabled(false);
        }if(control==0){
        busqueda();
          jtableEmpleados.addMouseListener(new MouseAdapter() 
        {
           @Override
           public void mouseClicked(MouseEvent e) 
           {
             if(e.getClickCount()== 2){
              int fila = jtableEmpleados.rowAtPoint(e.getPoint());
              int columna = jtableEmpleados.columnAtPoint(e.getPoint());
             /*El método rowAtPoint() nos devuelve -1 si pinchamos en el JTable
              pero fuera de cualquier fila*/
              
                     if ((fila > -1) && (columna > -1))
                     {
                       v=jtableEmpleados.getSelectedRow();//n° fila selccionada
                       indiceModelo = jtableEmpleados.convertRowIndexToModel (v);//convierte el indice de la vista en el indice del modelo 
                       jXTPanelDesplegable.setTitle("Modificar Empleado");
                       jXTPanelDesplegable.setCollapsed(false);
                       jBHabilitar.setVisible(true);
                       jCkHabilitados.setEnabled(false);
                       jtfbuscarCliente.setEnabled(false);
                       jtfbuscarCliente.setEditable(false);
                       jtableEmpleados.setEnabled(false);
                       bandera=1;
                       cargarCampos(oConsultaBoleta.getEmpleadoPorId(getIdEmpleado(indiceModelo)));
                       
                     }
             }
           }
        });
        
        cargarTabla();
        }
        
    }
    
     private void busqueda(){
     
     jtfbuscarCliente.getDocument().addDocumentListener(
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
        int indiceColumnaTabla=2;
        try {
            
            rf = RowFilter.regexFilter(jtfbuscarCliente.getText().toUpperCase(), indiceColumnaTabla);
        } catch (java.util.regex.PatternSyntaxException e) {
        }
        sorter.setRowFilter(rf);
    }
     
     public void cargarTabla(){
        Boolean estado = jCkHabilitados.isSelected();
        List<Empleado> l = oConsultaBoleta.getAllEmpleado();
        
        String[] columnNames = {"ID","DNI","Nombre","Direccion","Localidad","Categoria","Estado","Usuario"};
        int[] anchos = {15,40,100,170,130,50,40,15};
        if(estado)
             l= oConsultaBoleta.getAllEmpleado();
        else
             l= oConsultaBoleta.getAllEmpleadosHabilitados();

               
        Object[][] data = new Object[l.size()][columnNames.length];
         
       for (int i = 0; i < l.size(); i++) {

           //String categoria=oConsultaSueldo.getCategoriaEmpleadoPorId(l.get(i).getIdCategoriaEmpleado()).getNombreCategoriaEmpleado();
            String localidad=oConsultaMapa.getLocalidadPorId(l.get(i).getIdLocalidad()).getNombreLocalidad();
            String usuario="";
            if(oConsultaUsuario.getUsuarioPorId(l.get(i).getIdUsuario())!=null)
                usuario=oConsultaUsuario.getUsuarioPorId(l.get(i).getIdUsuario()).getUsuario();
            else
                usuario ="Sin Asignar";
            data[i][0] = l.get(i).getIdEmpleado();
            data[i][1] = l.get(i).getDniEmpleado();
            data[i][2] = l.get(i).getNombreEmpleado();
            data[i][3] = l.get(i).getDomicilioEmpleado();
            data[i][4] = localidad;
            data[i][5] = oConsultaBoleta.getCategoriaEmpleadoPorId(l.get(i).getIdCategoriaEmpleado()).getNombreCategoriaEmpleado();
            if (l.get(i).getEstadoEmpleado())
                data[i][6] = "Habilitado";
            else
                data[i][6] = "Inhabilitado";
            data[i][7] = usuario;
                
        }
       DefaultTableModel dftm = new DefaultTableModel(data, columnNames)
                {
		//metodo para que las celdas del jtable sean no-editables	
                    @Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
       jtableEmpleados.setModel(dftm);
       for(int i = 0; i < jtableEmpleados.getColumnCount(); i++) {

        jtableEmpleados.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);

        }
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        for(int i=0;i<jtableEmpleados.getColumnCount();i++)
        {
            jtableEmpleados.getColumnModel().getColumn(i).setCellRenderer(tcr);
        }
        sorter = new TableRowSorter<TableModel>(dftm);
        jtableEmpleados.setRowSorter(sorter);
        jtableEmpleados.getRowSorter().toggleSortOrder(0);
        jtableEmpleados.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        jtfnombre.setText("");
        jtfDomicilio.setText("");
        jtfTel.setText("");
        jtfDNI.setText("");
    }
     
     private int getIdEmpleado(int im){
        String[] fila= new String[1];//almaceno los valores del registro seleccionado en el string "fila"
        fila[0]=""+jtableEmpleados.getModel().getValueAt(im, 0);
        
        int idRep=Integer.parseInt(fila[0]);
        
        return  idRep;
    }
     
     private  void cargarCampos(Empleado Empleado){
         System.out.println("Id Usuario:"+Empleado.getIdUsuario());
         
         pLocalidad=oConsultaMapa.getLocalidadPorId(Empleado.getIdLocalidad());
        pEstadoCivil=oConsultaBoleta.getEstadoCivilEmpleadoPorId(Empleado.getIdEstadoCivilEmpleado());
        pCategoria=oConsultaBoleta.getCategoriaEmpleadoPorId(Empleado.getIdCategoriaEmpleado());
        pUsuario= oConsultaUsuario.getUsuarioPorId(Empleado.getIdUsuario());
        
        
        
         
        
        id=Empleado.getIdEmpleado();
        jtfnombre.setText(Empleado.getNombreEmpleado());
        jtfDNI.setText(""+Empleado.getDniEmpleado());
        jtfDomicilio.setText(Empleado.getDomicilioEmpleado());
        jtfTel.setText(Empleado.getTelefonoEmpleado());
        jcbLocalidad.setSelectedItem(pLocalidad);
        jcbEstadoCivil.setSelectedItem(pEstadoCivil);
        jcbCategoria.setSelectedItem(pCategoria);
        
        if(pUsuario==null)
        jcbUsuario.setSelectedIndex(0);
        else{ jcbUsuario.setSelectedItem(pUsuario);
            System.out.println("NOMBRE Usuario:"+pUsuario.getUsuario());}
        
        jdateFing.setDate(Empleado.getFechaIngreso());
        if ((Boolean)Empleado.getEstadoEmpleado()==true){
            jBHabilitar.setText("Inhabilitar");
        }
        else
        {
            jBHabilitar.setText("Habilitar");
        }
    }
     private Empleado capturarCampos(){
     Boolean estado=true;
    // int idusuario = 0;
     pEmpleado=null;
     localidad l= (localidad) jcbLocalidad.getSelectedItem();
     CategoriaEmpleado cat = (CategoriaEmpleado) jcbCategoria.getSelectedItem();
     EstadoCivilEmpleado estciv = (EstadoCivilEmpleado) jcbEstadoCivil.getSelectedItem();
     Usuario us = (Usuario) jcbUsuario.getSelectedItem();
     System.out.println("Usuario combo:"+us.getUsuario());
        if(jdateFing.getDate() != null)
           {dateFing=jdateFing.getDate();
           fing=new java.sql.Date(dateFing.getTime());
           }
         if (jBHabilitar.getText().contains("Inhabilitar"))
             estado = true;//Si el boton dice habilitar entonces se debe guardar como deshabilitado
         else
             estado= false;
         
         if(us.getUsuario() != "Sin Asignar"){
             idusuario = us.getIdUsuario();
         }
             
             System.out.println("ID USUARIO:"+us.getIdUsuario());
             pEmpleado= new Empleado(
                        id,
                        jtfDNI.getText().toUpperCase(),
                        jtfnombre.getText().toUpperCase(),
                        jtfDomicilio.getText().toUpperCase(),
                        jtfTel.getText(),
                        l.getIdLocalidad(),
                        fing,
                        cat.getIdCategoriaEmpleado(),
                        estciv.getIdEstadoCivilEmpleado(),
                        us.getIdUsuario(),
                        estado
                        );
        return  pEmpleado;
     }
     private void CargarComboEstadoCivil(){
        
     List<EstadoCivilEmpleado> lista = oConsultaBoleta.getAllEstadoCivilEmpleado();
       if(lista != null){
        for (int i = 0; i < lista.size(); i++) {
       
            EstadoCivilEmpleado esc=new EstadoCivilEmpleado(
                    lista.get(i).getIdEstadoCivilEmpleado(),
                    lista.get(i).getNombreEstadoCivilEmpleado());
            jcbEstadoCivil.addItem(esc);
            EstadoCivilEmpleado objeto=(EstadoCivilEmpleado) jcbEstadoCivil.getItemAt(1);

        }
        jcbEstadoCivil.setSelectedIndex(0);
       }
       else
       {
           labelEstadoCivil.setText("No existen Estados Civiles");
           labelEstadoCivil.setVisible(true);
           jcbEstadoCivil.setEnabled(true);
       }
  }
     private void CargarComboUsuarios(){
        
     if(oConsultaBoleta.getAllUsuario()!=null){
        //System.out.println("hay usuarios");
        List<Usuario> lista = oConsultaBoleta.getAllUsuario();
        Usuario us = new Usuario(0,"Sin Asignar","",0);
        jcbUsuario.addItem(us);
        for (int i = 0; i < lista.size(); i++) {
       
            Usuario usuario=new Usuario(
                    lista.get(i).getIdUsuario(),
                    lista.get(i).getUsuario());
            jcbUsuario.addItem(usuario);
            Usuario objeto=(Usuario) jcbUsuario.getItemAt(1);

        }
        //jcbUsuario.setSelectedIndex(0);
       }

  }
     private void CargarComboCategoria(){
        
     List<CategoriaEmpleado> lista = oConsultaBoleta.getAllCategoriaEmpleado();

       if(lista != null){
        for (int i = 0; i < lista.size(); i++) {
       
            CategoriaEmpleado emc=new CategoriaEmpleado(
                    lista.get(i).getIdCategoriaEmpleado(),
                    lista.get(i).getNombreCategoriaEmpleado(),
                    lista.get(i).getSueldoBasicoCategoria()
                    );
            jcbCategoria.addItem(emc);
            CategoriaEmpleado objeto=(CategoriaEmpleado) jcbCategoria.getItemAt(1);
            }
       jcbCategoria.setSelectedIndex(0);
        }
       else
       {
       labelCategoria.setText("No existen Categorias");
       labelCategoria.setVisible(true);
       jcbCategoria.setEnabled(false);
       } 
  }
     private void CargarComboLocalidades(){
        
     List<localidad> lista = oConsultaMapa.getAllLocalidad();
      if(lista != null)
      {
        for (int i = 0; i < lista.size(); i++) {
            localidad emc=new localidad(lista.get(i).getIdLocalidad(),
                                    lista.get(i).getNombreLocalidad(),
                                    lista.get(i).getIdProvincia());
            jcbLocalidad.addItem(emc);
            localidad objeto=(localidad) jcbLocalidad.getItemAt(1);
        }
        jcbLocalidad.setSelectedIndex(0);	
      }
      else
      {
          labelLocalidad.setText("No existen localidades");
          labelLocalidad.setVisible(true);
          jcbLocalidad.setEnabled(false);
      }
    }
     
     public boolean comprobarNulosN(){
        if(jtfnombre.getText().equals(""))
        {
            controlN=true;
            labelNombre.setVisible(true);
            labelNombre.setText("Debe ingresar un nombre");
            jtfnombre.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
        }
        else{
            controlN=false;
            labelNombre.setVisible(false);
            jtfnombre.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.BLACK));
        }
                    
      return controlN;
    }
     public boolean comprobarNulosF(){
        if(jdateFing.getDate()==null)
        {
            controlN=true;
            labelFechaIngreso.setVisible(true);
            labelFechaIngreso.setText("Debe ingresar una Fecha");
            jdateFing.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
        }
        else{
            controlN=false;
            labelFechaIngreso.setVisible(false);
            jdateFing.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
        }
      return controlN;
    }
     public boolean comprobarNulosC(){
                    if(jtfDNI.getText().isEmpty())
                    {controlC=true;
                    labelDNI.setVisible(true);
                    labelDNI.setText("Debe ingresar un número de DNI");
                    jtfDNI.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
                    }
                    else{
                    controlC=false;
                    labelDNI.setVisible(false);
                    jtfDNI.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));

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
                    labelTel.setVisible(false);
                    jtfTel.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
                    }
                    
                    return controlT;
    }
     private void modoNuevoEmpleado(){
            jtfnombre.setText("");
            jtfDNI.setText("");
            jtfTel.setText("");
            jtfDomicilio.setText("");
            jdateFing.setDate(null);
            jcbLocalidad.setSelectedIndex(0);
            jcbEstadoCivil.setSelectedIndex(0);
            jcbCategoria.setSelectedIndex(0);
            jcbUsuario.setSelectedIndex(0);
            jXTPanelDesplegable.setTitle("Nuevo Empleado");
            jXTPanelDesplegable.setCollapsed(true);
            jtfbuscarCliente.setEnabled(true);
            jtfbuscarCliente.setEditable(true);
            jCkHabilitados.setEnabled(true);
            jBHabilitar.setVisible(false);
            jtableEmpleados.setEnabled(true);
            jBHabilitar.setText("Inhabilitar");
            labelDNI.setVisible(false);
            blanquearLabels();
     }
     private void blanquearLabels(){
            labelNombre.setVisible(false);
            jtfnombre.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
            labelDNI.setVisible(false);
            jtfDNI.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
            labelTel.setVisible(false);
            jtfTel.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
            labelLocalidad.setVisible(false);
            labelDomicilio.setVisible(false);
            jtfDomicilio.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
            labelCategoria.setVisible(false);
            labelEstadoCivil.setVisible(false);
            labelFechaIngreso.setVisible(false);
     }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLBuscar = new javax.swing.JLabel();
        jtfbuscarCliente = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtableEmpleados = new javax.swing.JTable();
        jXTPanelDesplegable = new org.jdesktop.swingx.JXTaskPane();
        jPanel4 = new javax.swing.JPanel();
        jtfnombre = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        btnguardar1 = new javax.swing.JButton();
        btnCancelar1 = new javax.swing.JButton();
        jBHabilitar = new javax.swing.JButton();
        jLDNI = new javax.swing.JLabel();
        jtfDNI = new javax.swing.JTextField();
        jLTelefono = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jtfDomicilio = new javax.swing.JTextField();
        jtfTel = new javax.swing.JTextField();
        jcbLocalidad = new org.jdesktop.swingx.JXComboBox();
        LOCALIDAD = new javax.swing.JLabel();
        labelNombre = new javax.swing.JLabel();
        labelTel = new javax.swing.JLabel();
        labelLocalidad = new javax.swing.JLabel();
        jcbEstadoCivil = new org.jdesktop.swingx.JXComboBox();
        LOCALIDAD1 = new javax.swing.JLabel();
        labelEstadoCivil = new javax.swing.JLabel();
        jdateFing = new com.toedter.calendar.JDateChooser();
        jLabel4 = new javax.swing.JLabel();
        jcbCategoria = new org.jdesktop.swingx.JXComboBox();
        labelCategoria = new javax.swing.JLabel();
        LOCALIDAD2 = new javax.swing.JLabel();
        labelFechaIngreso = new javax.swing.JLabel();
        jcbUsuario = new org.jdesktop.swingx.JXComboBox();
        LOCALIDAD3 = new javax.swing.JLabel();
        labelDNI = new javax.swing.JLabel();
        labelDomicilio = new javax.swing.JLabel();
        jCkHabilitados = new javax.swing.JCheckBox();

        jLBuscar.setText("Buscar Empleado");

        jtableEmpleados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "id", "Nombre", "CUIT", "Telefono", "Direccion", "Localidad"
            }
        ));
        jScrollPane1.setViewportView(jtableEmpleados);

        jXTPanelDesplegable.setTitle("Nuevo Empleado");

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

        jBHabilitar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/eliminar_1.png"))); // NOI18N
        jBHabilitar.setText("Inhabilitar");
        jBHabilitar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBHabilitarActionPerformed(evt);
            }
        });

        jLDNI.setText("DNI");

        jtfDNI.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(0, 0, 0), null));
        jtfDNI.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtfDNIFocusLost(evt);
            }
        });
        jtfDNI.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtfDNIKeyTyped(evt);
            }
        });

        jLTelefono.setText("TELEFONO ");

        jLabel8.setText("DOMICILIO");

        jtfDomicilio.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(0, 0, 0), null));
        jtfDomicilio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtfDomicilioKeyTyped(evt);
            }
        });

        jtfTel.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(0, 0, 0), null));
        jtfTel.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtfTelFocusLost(evt);
            }
        });
        jtfTel.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtfTelKeyTyped(evt);
            }
        });

        LOCALIDAD.setText("LOCALIDAD");

        labelNombre.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        labelNombre.setForeground(new java.awt.Color(204, 51, 0));
        labelNombre.setText("jLabel3");

        labelTel.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        labelTel.setForeground(new java.awt.Color(204, 51, 0));
        labelTel.setText("jLabel6");

        labelLocalidad.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        labelLocalidad.setForeground(new java.awt.Color(204, 51, 0));
        labelLocalidad.setText("jLabel7");

        LOCALIDAD1.setText("ESTADO CIVIL");

        labelEstadoCivil.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        labelEstadoCivil.setForeground(new java.awt.Color(204, 51, 0));
        labelEstadoCivil.setText("jLabel7");

        jdateFing.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jdateFingFocusLost(evt);
            }
        });

        jLabel4.setText("FECHA INGRESO");

        labelCategoria.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        labelCategoria.setForeground(new java.awt.Color(204, 51, 0));
        labelCategoria.setText("jLabel7");

        LOCALIDAD2.setText("CATEGORIA");

        labelFechaIngreso.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        labelFechaIngreso.setForeground(new java.awt.Color(204, 51, 0));
        labelFechaIngreso.setText("jLabel7");

        LOCALIDAD3.setText("USUARIO");

        labelDNI.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        labelDNI.setForeground(new java.awt.Color(204, 51, 0));
        labelDNI.setText("jLabel7");

        labelDomicilio.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        labelDomicilio.setForeground(new java.awt.Color(204, 51, 0));
        labelDomicilio.setText("jLabel7");

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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jBHabilitar)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtfnombre, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelDNI, javax.swing.GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLDNI)
                                    .addComponent(jtfDNI, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelDomicilio, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jtfDomicilio, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel8))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLTelefono)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addComponent(labelTel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(14, 14, 14))
                                    .addComponent(jtfTel, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(38, 38, 38))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(LOCALIDAD)
                            .addComponent(labelLocalidad, javax.swing.GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
                            .addComponent(jcbLocalidad, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(LOCALIDAD1)
                            .addComponent(jcbEstadoCivil, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelEstadoCivil, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jdateFing, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(labelFechaIngreso, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(7, 7, 7))
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jcbCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(LOCALIDAD2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(labelCategoria, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jcbUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(LOCALIDAD3, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(jLDNI)
                            .addComponent(jLabel8))
                        .addGap(2, 2, 2)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jtfnombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtfDNI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLTelefono)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jtfDomicilio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtfTel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(4, 4, 4)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelTel)
                    .addComponent(labelDomicilio)
                    .addComponent(labelDNI)
                    .addComponent(labelNombre))
                .addGap(32, 32, 32)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jdateFing, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(LOCALIDAD)
                                    .addComponent(LOCALIDAD1))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jcbLocalidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jcbEstadoCivil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jcbCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jcbUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(labelEstadoCivil)
                            .addComponent(labelLocalidad)
                            .addComponent(labelFechaIngreso)
                            .addComponent(labelCategoria)))
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(LOCALIDAD2)
                        .addComponent(LOCALIDAD3)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnguardar1)
                    .addComponent(btnCancelar1)
                    .addComponent(jBHabilitar))
                .addContainerGap())
        );

        jXTPanelDesplegable.getContentPane().add(jPanel4);

        jCkHabilitados.setSelected(true);
        jCkHabilitados.setText("Todos/Solo Habilitados");
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
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLBuscar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jtfbuscarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(37, 37, 37)
                        .addComponent(jCkHabilitados))
                    .addComponent(jScrollPane1)
                    .addComponent(jXTPanelDesplegable, javax.swing.GroupLayout.PREFERRED_SIZE, 724, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLBuscar)
                    .addComponent(jtfbuscarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCkHabilitados))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jXTPanelDesplegable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jtfTelKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfTelKeyTyped
        char car = evt.getKeyChar();
        longitud=jtfTel.getText().length();
        if(controlT!=false){
            labelTel.setVisible(false);
            jtfTel.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
            controlT=false;
        }
        if(longitud < 15 && controLabel!=true){

            if((car<'0' || car>'9')&& car!=KeyEvent.VK_BACK_SPACE && car!=KeyEvent.VK_ENTER && car!='-'){
                labelTel.setVisible(true);
                labelTel.setText("Solo se deben ingresar numeros");
                jtfTel.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
                controLabel=true;
                longitud2=jtfTel.getText().length();
                jtfTel.setText("");
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
    private void jtfDNIKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfDNIKeyTyped
        char car = evt.getKeyChar();
        longitud=jtfDNI.getText().length();
        labelDNI.setVisible(false);
        jtfDNI.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
        if(controlC!=false){
            labelDNI.setVisible(false);
            jtfDNI.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
            controlC=false;
        }

        if(longitud < 12 && controLabel!=true){

            if((car<'0' || car>'9')&& car!=KeyEvent.VK_BACK_SPACE && car!=KeyEvent.VK_ENTER){
                labelDNI.setVisible(true);
                labelDNI.setText("Solo se permiten numeros");
                jtfDNI.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
                controLabel=true;
                longitud2=jtfDNI.getText().length();
                jtfDNI.setText("");
            }
            else{
                labelDNI.setVisible(false);
                jtfDNI.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
                controLabel=false;
            }
        }
        else{
            evt.consume();
            if(longitud-1 < longitud2){
                labelDNI.setVisible(false);
                jtfDNI.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
                controLabel=false;
            }
        }
    }//GEN-LAST:event_jtfDNIKeyTyped
    private void jBHabilitarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBHabilitarActionPerformed
        // TODO add your handling code here:
        Boolean estado = (Boolean) oConsultaBoleta.getEmpleadoPorId(id).getEstadoEmpleado();
        if(estado==true)
        {
            jBHabilitar.setText("Habilitar");
        }
        else{
            jBHabilitar.setText("Inhabilitar");
        }

    }//GEN-LAST:event_jBHabilitarActionPerformed

    private void btnCancelar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelar1ActionPerformed
        int respuesta=JOptionPane.showConfirmDialog(null, "¿Confirma la cancelación? \n Los datos no seran guardados","Advertencia", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            //confirmamos la eliminacion
            if(respuesta == 0)
            {
                modoNuevoEmpleado();
                bandera = 0;
            }
    }//GEN-LAST:event_btnCancelar1ActionPerformed

    private void btnguardar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnguardar1ActionPerformed
        Usuario us = (Usuario)jcbUsuario.getSelectedItem();
        if( comprobarNulosN()== false && comprobarNulosC()== false
            && comprobarNulosT()== false && comprobarNulosF()== false && controLabel!=true ){
            if(bandera==1){//Modo modificacion
                if(us.getUsuario() != "Sin Asignar")
                    oConsultaBoleta.modificarEmpleado(capturarCampos());
                else
                    oConsultaBoleta.modificarEmpleadoSinUsuario(capturarCampos());
                JOptionPane.showMessageDialog(this,"Empleado Modificado");
                cargarTabla();
                bandera=0;
                jdateFing.setDate(new Date());
                jcbLocalidad.setSelectedIndex(0);	
                jcbCategoria.setSelectedIndex(0);
                jcbEstadoCivil.setSelectedIndex(0);
            }
            else{//modo creacion
                if(jcbUsuario.getSelectedIndex()!=0){
                    oConsultaBoleta.agregarEmpleado(capturarCampos());
                    
                }
                else{
                    oConsultaBoleta.agregarEmpleadoSinUsuario(capturarCampos());
                }
                JOptionPane.showMessageDialog(this,"Empleado Agregado");
                cargarTabla();
                bandera=0;
                jdateFing.setDate(new Date());
                jcbLocalidad.setSelectedIndex(0);	
                jcbCategoria.setSelectedIndex(0);
                jcbEstadoCivil.setSelectedIndex(0);
            }
            modoNuevoEmpleado();
        }
        else
        {
            if(controLabel==true)
            {
                JOptionPane.showMessageDialog(null, "Debe ingresar valores válidos en los campos DNI, Telefono y Fecha", "Error", WIDTH);
            }
        }

    }//GEN-LAST:event_btnguardar1ActionPerformed

    private void jtfnombreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfnombreKeyTyped
        char c=evt.getKeyChar();
        e = true;
        labelNombre.setVisible(false);
        jtfnombre.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
          if(Character.isDigit(c)) {
              getToolkit().beep();
              evt.consume();
              jtfnombre.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
              labelNombre.setText("Solo puede ingresar texto");
              labelNombre.setVisible(true);
          }
          if(jtfnombre.getText().length() == 45) {
              getToolkit().beep();
              evt.consume();
              jtfnombre.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
              labelNombre.setText("Maximo 45 caracteres");
              labelNombre.setVisible(true);
          }
    }//GEN-LAST:event_jtfnombreKeyTyped

    private void jCkHabilitadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCkHabilitadosActionPerformed
        // TODO add your handling code here:
        cargarTabla();
    }//GEN-LAST:event_jCkHabilitadosActionPerformed

    private void jtfDomicilioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfDomicilioKeyTyped
        // TODO add your handling code here:
        char c=evt.getKeyChar();
        e = true;
        labelDomicilio.setVisible(false);
        jtfDomicilio.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
          
          if(jtfDomicilio.getText().length() == 45) {
              getToolkit().beep();
              evt.consume();
              jtfDomicilio.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
              labelDomicilio.setText("Maximo 45 caracteres");
              labelDomicilio.setVisible(true);
          }
    }//GEN-LAST:event_jtfDomicilioKeyTyped

    private void jtfnombreFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtfnombreFocusLost
        //this.comprobarNulosN();
    }//GEN-LAST:event_jtfnombreFocusLost

    private void jtfDNIFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtfDNIFocusLost
        //this.comprobarNulosC();
    }//GEN-LAST:event_jtfDNIFocusLost

    private void jtfTelFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtfTelFocusLost
        //this.comprobarNulosT();
        
    }//GEN-LAST:event_jtfTelFocusLost

    private void jdateFingFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jdateFingFocusLost
        this.comprobarNulosF();
    }//GEN-LAST:event_jdateFingFocusLost

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel LOCALIDAD;
    private javax.swing.JLabel LOCALIDAD1;
    private javax.swing.JLabel LOCALIDAD2;
    private javax.swing.JLabel LOCALIDAD3;
    private javax.swing.JButton btnCancelar1;
    private javax.swing.JButton btnguardar1;
    private javax.swing.JButton jBHabilitar;
    private javax.swing.JCheckBox jCkHabilitados;
    private javax.swing.JLabel jLBuscar;
    private javax.swing.JLabel jLDNI;
    private javax.swing.JLabel jLTelefono;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private org.jdesktop.swingx.JXTaskPane jXTPanelDesplegable;
    private org.jdesktop.swingx.JXComboBox jcbCategoria;
    private org.jdesktop.swingx.JXComboBox jcbEstadoCivil;
    private org.jdesktop.swingx.JXComboBox jcbLocalidad;
    private org.jdesktop.swingx.JXComboBox jcbUsuario;
    private com.toedter.calendar.JDateChooser jdateFing;
    private javax.swing.JTable jtableEmpleados;
    private javax.swing.JTextField jtfDNI;
    private javax.swing.JTextField jtfDomicilio;
    private javax.swing.JTextField jtfTel;
    private javax.swing.JTextField jtfbuscarCliente;
    private javax.swing.JTextField jtfnombre;
    private javax.swing.JLabel labelCategoria;
    private javax.swing.JLabel labelDNI;
    private javax.swing.JLabel labelDomicilio;
    private javax.swing.JLabel labelEstadoCivil;
    private javax.swing.JLabel labelFechaIngreso;
    private javax.swing.JLabel labelLocalidad;
    private javax.swing.JLabel labelNombre;
    private javax.swing.JLabel labelTel;
    // End of variables declaration//GEN-END:variables
}
