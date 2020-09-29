package Empleados;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import static java.awt.image.ImageObserver.WIDTH;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import mapa.ConsultaMapa;
import mapa.localidad;
import org.apache.commons.codec.digest.DigestUtils;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

/**
 *
 * @author SEBASTIAN
 */

public final class jpUsuario extends javax.swing.JPanel {
private ConsultaUsuario oConsultaUsuarios;
private ConsultaEmpleados2 oConsultaLibreria;
private ConsultaMapa oConsultaMapa;
private localidad pLocalidad;
private CategoriaEmpleado pCategoria;
private EstadoCivilEmpleado pEstadoCivilEmpleado;
private List<Usuario> users = new ArrayList<>();
private List<TipoUsuario> lista = new ArrayList<>();private Date dateFing;
private List<Empleado> lista2;
private boolean control;
private TableRowSorter<TableModel> sorter;//para ordenar la tabla
private int v=0,indiceModelo=0,bandera=0,id=0,control2;
/**
     * Creates new form jpProveedores
     */
//BANDERA 0 NUEVO
//BANDERA 1 MODIFICACIONC 
//CONTROL 0 ABM
//CONTROL 1 ALTAS
     public jpUsuario() {
        initComponents();
        
        oConsultaUsuarios = new ConsultaUsuario();
        oConsultaLibreria = new ConsultaEmpleados2();
        bandera=0;
        AutoCompleteDecorator.decorate(this.jcbTipoUsuario);
        CargarComboTipoUsuario();
        //oculto carteles de error
        modoNuevoUsuario();
        blanquearMensajes();
        jtfNombreUsuario.requestFocus();
        if(control2==1){
        jLabelBuscar.setVisible(false);
        jtfbuscarUsuario.setVisible(false);
        jScrollPane1.setVisible(false);
        jXTaskPane2.setCollapsed(true);
        }if(control2==0){
        busqueda();
          jtableUsuarios.addMouseListener(new MouseAdapter() 
        {
           @Override
           public void mouseClicked(MouseEvent e) 
           {
             if(e.getClickCount()== 2){
              int fila = jtableUsuarios.rowAtPoint(e.getPoint());
              int columna = jtableUsuarios.columnAtPoint(e.getPoint());
              jtfbuscarUsuario.setEnabled(false);
              jtfbuscarUsuario.setEditable(false);
              jtContraseñaRepetir.setEnabled(false);
              jtContraseñaNueva.setEnabled(false);
              jtContraseñaRepetir.setEditable(false);
              jtContraseñaNueva.setEditable(false);
              jtContraseñaRepetir.setText("");
              jtContraseñaNueva.setText("");
              blanquearMensajes();
             /*El método rowAtPoint() nos devuelve -1 si pinchamos en el JTable
              pero fuera de cualquier fila*/
              
                     if ((fila > -1) && (columna > -1))
                     {
                       v=jtableUsuarios.getSelectedRow();//n° fila selccionada
                       indiceModelo = jtableUsuarios.convertRowIndexToModel (v);//convierte el indice de la vista en el indice del modelo 
                       id = (Integer) jtableUsuarios.getValueAt(jtableUsuarios.getSelectedRow(), 0);
                       jtableUsuarios.setEnabled(false);
                       bandera=1;
                       lista2 = oConsultaLibreria.getAllEmpleado();
                       boolean f = false;
                       Usuario a = oConsultaUsuarios.getUsuarioPorId(getIdUsuario(indiceModelo));
                       jBEliminar.setVisible(true);
                       modoModificaUsuario();
                       for(int i=0;i<lista2.size();i++)
                       {
                           System.out.println("HOOOOOLLLLLLAAAAAAAAA "+lista2.get(i).getIdUsuario());
                           if(lista2.get(i).getIdUsuario() == a.getIdUsuario())
                           {
                               f = true;
                               System.out.println("HOOOOOLLLLLLAAAAAAAAA "+a.getIdUsuario()+" "+lista2.get(i).getIdUsuario());
                           }
                       }
                       if(f == true)
                       {
                           
                           jBEliminar.setEnabled(false);
                       }
                       else
                       {
                           jBEliminar.setEnabled(true);
                       }
                       cargarCampos(oConsultaUsuarios.getUsuarioPorId(getIdUsuario(indiceModelo)));
                     }
             }
           }
        });
        
        cargarTabla(oConsultaUsuarios.getAllUsuario());
        }
        
    }

    
     private void busqueda(){
     
     jtfbuscarUsuario.getDocument().addDocumentListener(
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
            
            rf = RowFilter.regexFilter(jtfbuscarUsuario.getText().toUpperCase(), indiceColumnaTabla);
        } catch (java.util.regex.PatternSyntaxException e) {
        }
        sorter.setRowFilter(rf);
    }
     public void cargarTabla(List<Usuario> l){
    
        String[] columnNames = {"ID","Nombre","Tipo Usuario"};
        int[] anchos = {15,100,100};
        
               
        Object[][] data = new Object[l.size()][columnNames.length];
         
       for (int i = 0; i < l.size(); i++) {
           //String categoria=oConsultaSueldo.getCategoriaEmpleadoPorId(l.get(i).getIdCategoriaEmpleado()).getNombreCategoriaEmpleado();
            data[i][0] = l.get(i).getIdUsuario();
            data[i][1] = l.get(i).getUsuario();
            data[i][2] = oConsultaUsuarios.getTipoUsuarioPorId((Integer)l.get(i).getIdTipoUsuario()).getNombreTipoUsuario();
        }
       DefaultTableModel dftm = new DefaultTableModel(data, columnNames)
                {
		//metodo para que las celdas del jtable sean no-editables	
                    @Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
       jtableUsuarios.setModel(dftm);
       for(int i = 0; i < jtableUsuarios.getColumnCount(); i++) {

        jtableUsuarios.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);

        }
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        for(int i=0;i<jtableUsuarios.getColumnCount();i++)
        {
            jtableUsuarios.getColumnModel().getColumn(i).setCellRenderer(tcr);
        }
        sorter = new TableRowSorter<TableModel>(dftm);
        jtableUsuarios.setRowSorter(sorter);
        jtableUsuarios.getRowSorter().toggleSortOrder(0);
        jtableUsuarios.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jtfbuscarUsuario.setText("");
    }
     private int getIdUsuario(int im){
        String[] fila= new String[1];//almaceno los valores del registro seleccionado en el string "fila"
        fila[0]=""+jtableUsuarios.getModel().getValueAt(im, 0);
        
        int idRep=Integer.parseInt(fila[0]);
        
        return  idRep;
    }
     private  void cargarCampos(Usuario usuario){
        id=usuario.getIdUsuario();
        jtfNombreUsuario.setText(usuario.getUsuario());
        TipoUsuario tipousuario = oConsultaUsuarios.getTipoUsuarioPorId(usuario.getIdTipoUsuario());
        jcbTipoUsuario.setSelectedItem(tipousuario);
        jtfNombreUsuario.transferFocus();
    }
     private Usuario capturarCampos(){

        String contraseña ;
        contraseña= DigestUtils.md5Hex(jtContraseñaNueva.getText());
        TipoUsuario tu= (TipoUsuario) jcbTipoUsuario.getSelectedItem();
        int idtipousuario = tu.getIdTipoUsuario();
        Usuario pUsuario= new Usuario(id,jtfNombreUsuario.getText(),contraseña,idtipousuario);
        return  pUsuario;
        }
   private void CargarComboTipoUsuario(){
        
     lista = oConsultaUsuarios.getAllTipoUsuario();
     if(lista != null){
        for (int i = 0; i < lista.size(); i++) {
       
            TipoUsuario tfv=new TipoUsuario(
                    lista.get(i).getIdTipoUsuario(),
                    lista.get(i).getNombreTipoUsuario());
            jcbTipoUsuario.addItem(tfv);
            TipoUsuario objeto=(TipoUsuario) jcbTipoUsuario.getItemAt(1);
        }
        Labeltipousuario.setVisible(true);
       }
        else
        {
            Labeltipousuario.setText("no existen tipos de usuario");
            Labeltipousuario.setVisible(true);
            jcbTipoUsuario.setEnabled(false);
        }
 } 
   public boolean comprobarNuloNombre(){
        if(jtfNombreUsuario==null){
            control=true;
            LabelNombreUsuario.setText("Debe escribir un nombre");
            LabelNombreUsuario.setVisible(true);
            jtfNombreUsuario.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
            jtfNombreUsuario.transferFocus();
        }
        else{
        control=false;
        }
      return control;
    }
   public boolean comprobarContraseñaActual(){
        Usuario users = (Usuario)oConsultaUsuarios.getUsuarioPorId(id);
        String contraseñaAnterior;
        contraseñaAnterior= DigestUtils.md5Hex(jtContraseñaAnterior.getText());
        if(users.getContraseña().compareTo(contraseñaAnterior) == 0)
        {
            control=true;
        }
        else
        {
            control=false;
            LabelContraseñaActual.setText("Contraseña incorrecta");
            LabelContraseñaActual.setVisible(true);
            jtContraseñaAnterior.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
            jtContraseñaAnterior.setText("");
            //jtContraseñaAnterior.transferFocus();
         }
        return control;
  }
   public boolean comprobarContraseñaNueva(){
        String contraseñaNueva,contraseñaRepetir;
        contraseñaNueva= jtContraseñaNueva.getText();
        contraseñaRepetir = jtContraseñaRepetir.getText();
        if(contraseñaNueva.compareTo(contraseñaRepetir) == 0)
        {
            control=true;
        }
        else
        {
            control=false;
            LabelContraseñaNueva.setText("Contraseñas no coiciden");
            LabelContraseñaNueva.setVisible(true);
            jtContraseñaNueva.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
            jtContraseñaNueva.setText("");
            jtContraseñaRepetir.setText("");
            jtContraseñaNueva.transferFocus();
         }
        return control;
  }
   public boolean comprobarContraseñaCambio(){
        String contraseñaNueva,contraseñaRepetir;
        contraseñaNueva= jtContraseñaNueva.getText();
        contraseñaRepetir = jtContraseñaRepetir.getText();
        if(contraseñaNueva.compareTo(contraseñaRepetir) == 0)
        {
            control=true;
        }
        else
        {
            control=false;
            LabelContraseñaNueva.setText("Contraseñas no coiciden");
            LabelContraseñaNueva.setVisible(true);
            jtContraseñaNueva.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
            jtContraseñaNueva.setText("");
            jtContraseñaRepetir.setText("");
            //jtContraseñaNueva.transferFocus();
         }
        return control;
  }

        
        @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabelBuscar = new javax.swing.JLabel();
        jtfbuscarUsuario = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtableUsuarios = new javax.swing.JTable();
        jXTaskPane2 = new org.jdesktop.swingx.JXTaskPane();
        jPanel4 = new javax.swing.JPanel();
        jtfNombreUsuario = new javax.swing.JTextField();
        jLabelNombreUsuario = new javax.swing.JLabel();
        jLabelContraseñaActual = new javax.swing.JLabel();
        jLabelContraseñaNueva = new javax.swing.JLabel();
        jBEliminar = new javax.swing.JButton();
        jBGuardar = new javax.swing.JButton();
        btnCancelar1 = new javax.swing.JButton();
        jLabelContraseñaRepetir = new javax.swing.JLabel();
        jcbTipoUsuario = new org.jdesktop.swingx.JXComboBox();
        LOCALIDAD2 = new javax.swing.JLabel();
        Labeltipousuario = new javax.swing.JLabel();
        LabelNombreUsuario = new javax.swing.JLabel();
        LabelContraseñaActual = new javax.swing.JLabel();
        LabelContraseñaNueva = new javax.swing.JLabel();
        LabelContraseñaRepetir = new javax.swing.JLabel();
        jtContraseñaNueva = new javax.swing.JPasswordField();
        jtContraseñaRepetir = new javax.swing.JPasswordField();
        jtContraseñaAnterior = new javax.swing.JPasswordField();

        jLabelBuscar.setText("BUSCAR USUARIO");

        jtableUsuarios.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jtableUsuarios);

        jXTaskPane2.setTitle("Nuevo Usuario");

        jPanel4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jtfNombreUsuario.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtfNombreUsuarioFocusLost(evt);
            }
        });
        jtfNombreUsuario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtfNombreUsuarioKeyTyped(evt);
            }
        });

        jLabelNombreUsuario.setText("NOMBRE DE USUARIO");

        jLabelContraseñaActual.setText("CONTRASEÑA ACTUAL");

        jLabelContraseñaNueva.setText("NUEVA CONTRASEÑA");

        jBEliminar.setText("Eliminar");
        jBEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBEliminarActionPerformed(evt);
            }
        });

        jBGuardar.setText("Guardar");
        jBGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBGuardarActionPerformed(evt);
            }
        });

        btnCancelar1.setText("Cancelar");
        btnCancelar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelar1ActionPerformed(evt);
            }
        });

        jLabelContraseñaRepetir.setText("REPETIR CONTRASEÑA");

        jcbTipoUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbTipoUsuarioActionPerformed(evt);
            }
        });

        LOCALIDAD2.setText("TIPO DE USUARIO");

        Labeltipousuario.setForeground(new java.awt.Color(204, 0, 0));
        Labeltipousuario.setText("Impaga");

        LabelNombreUsuario.setForeground(new java.awt.Color(204, 0, 0));
        LabelNombreUsuario.setText("Impaga");

        LabelContraseñaActual.setForeground(new java.awt.Color(204, 0, 0));
        LabelContraseñaActual.setText("Impaga");

        LabelContraseñaNueva.setForeground(new java.awt.Color(204, 0, 0));
        LabelContraseñaNueva.setText("Impaga");

        LabelContraseñaRepetir.setForeground(new java.awt.Color(204, 0, 0));
        LabelContraseñaRepetir.setText("Impaga");

        jtContraseñaNueva.setEditable(false);
        jtContraseñaNueva.setEnabled(false);
        jtContraseñaNueva.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtContraseñaNuevaFocusLost(evt);
            }
        });
        jtContraseñaNueva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtContraseñaNuevaActionPerformed(evt);
            }
        });
        jtContraseñaNueva.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtContraseñaNuevaKeyTyped(evt);
            }
        });

        jtContraseñaRepetir.setEditable(false);
        jtContraseñaRepetir.setEnabled(false);
        jtContraseñaRepetir.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtContraseñaRepetirFocusLost(evt);
            }
        });
        jtContraseñaRepetir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtContraseñaRepetirActionPerformed(evt);
            }
        });
        jtContraseñaRepetir.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtContraseñaRepetirKeyTyped(evt);
            }
        });

        jtContraseñaAnterior.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtContraseñaAnteriorFocusLost(evt);
            }
        });
        jtContraseñaAnterior.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtContraseñaAnteriorActionPerformed(evt);
            }
        });
        jtContraseñaAnterior.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtContraseñaAnteriorKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(LabelNombreUsuario, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jtfNombreUsuario, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelNombreUsuario, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabelContraseñaActual, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addGap(3, 3, 3)
                                        .addComponent(jtContraseñaAnterior))
                                    .addComponent(LabelContraseñaActual, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(5, 5, 5)))
                        .addGap(12, 12, 12)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(LabelContraseñaNueva, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(LabelContraseñaRepetir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(29, 29, 29))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabelContraseñaNueva)
                                    .addComponent(jtContraseñaNueva))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabelContraseñaRepetir, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jtContraseñaRepetir))))
                        .addGap(177, 177, 177))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jcbTipoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(Labeltipousuario, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jBGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnCancelar1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jBEliminar))
                            .addComponent(LOCALIDAD2, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jtfNombreUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jtContraseñaAnterior, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jtContraseñaNueva, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabelContraseñaNueva)
                                .addGap(26, 26, 26)))
                        .addGroup(jPanel4Layout.createSequentialGroup()
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabelNombreUsuario)
                                .addComponent(jLabelContraseñaActual))
                            .addGap(26, 26, 26)))
                    .addComponent(jtContraseñaRepetir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabelContraseñaRepetir)
                        .addGap(26, 26, 26)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LabelNombreUsuario)
                    .addComponent(LabelContraseñaActual)
                    .addComponent(LabelContraseñaNueva)
                    .addComponent(LabelContraseñaRepetir))
                .addGap(4, 4, 4)
                .addComponent(LOCALIDAD2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jBGuardar)
                        .addComponent(btnCancelar1)
                        .addComponent(jBEliminar))
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jcbTipoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(Labeltipousuario)))
                .addGap(38, 38, 38))
        );

        jXTaskPane2.getContentPane().add(jPanel4);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jXTaskPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 771, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabelBuscar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jtfbuscarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelBuscar)
                    .addComponent(jtfbuscarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 209, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jXTaskPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jBGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBGuardarActionPerformed
       String contraseñaNueva,contraseñadb,contraseñaAnterior,contraseñaRepetir,nombreUsuario;
       contraseñaNueva= DigestUtils.md5Hex(jtContraseñaNueva.getText());
       contraseñaAnterior= DigestUtils.md5Hex(jtContraseñaAnterior.getText());

       if(bandera==1)
       {//bandera 1 implica modificacion
           if(jtfNombreUsuario.getText().isEmpty() == false)
           {
            if(oConsultaUsuarios.comprobarUsuarioExiste(jtfNombreUsuario.getText()) && (oConsultaUsuarios.getUsuarioPorUsuario(jtfNombreUsuario.getText()).getIdUsuario()!=id))
            {//en caso de modificacion y creacion no puede existir, por lo que se evalua al principio
                //JOptionPane.showMessageDialog(this,"Usuario Existe");
                LabelNombreUsuario.setText("Usuario existente");
                LabelNombreUsuario.setVisible(true);
                jtfNombreUsuario.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
                jtfNombreUsuario.requestFocus();
                jtfNombreUsuario.setSelectionStart(0); 
                jtfNombreUsuario.setSelectionEnd(jtfNombreUsuario.getText().length()); 
            }
           else{//Si el usuario no existe entonce puedo proseguir
              contraseñadb = oConsultaUsuarios.getUsuarioPorId(id).getContraseña();//contraseña obtenida de base de datos
                contraseñaRepetir= DigestUtils.md5Hex(jtContraseñaRepetir.getText());//contraseña que se evaluara y se guardara
                if(jtContraseñaRepetir.getText().isEmpty()&& jtContraseñaNueva.getText().isEmpty() && jtContraseñaAnterior.getText().isEmpty())
                 {//si todas las contraseñas estan vacias, solo guardo el nombre y el tipo sin chequear contraseñas
                 oConsultaUsuarios.modificarUsuarioNomTipo(capturarCampos());
                 JOptionPane.showMessageDialog(this,"Usuario Modificado");
                 bandera=0;
                 modoNuevoUsuario();
             }
                else
                {//si alguna no es vacia implica que estoy queriendo cambiarla
                    if(contraseñaAnterior.compareTo(contraseñadb) == 0)
                    {//chequeo que la contraseña de la db sea correcta
                     if(contraseñaNueva.compareTo(contraseñaRepetir)==0)
                     {//si las nuevas contraseñas son iguales
                         if (oConsultaUsuarios.comprobarContraseña(jtContraseñaNueva.getText()))
                         {//si la contraseña nueva cumple con requisitos de seguridad entonces recien guardo
                                 oConsultaUsuarios.modificarUsuario(capturarCampos());
                                 JOptionPane.showMessageDialog(this,"Usuario Modificado");
                                 bandera=0;
                                 modoNuevoUsuario();
                         }//fin si contraseña nueva cumple con requisitos
                         else
                         {
                            JOptionPane.showMessageDialog(this,"La contraseña nueva no cumple con los requisitos de seguridad minimos. (Longitud mayor a 5, contener minimo una mayuscula y contener minimo un numero)");
                            jtContraseñaRepetir.setText("");
                            jtContraseñaNueva.setText("");
                            jtContraseñaNueva.requestFocus();
                         }//fin else contraseña no cumple con requisitos
                     }//fin if contraseñas nuevas correctas
                     else
                     {
                         //JOptionPane.showMessageDialog(null, "Las contraseñas nueva y su repeticion no coinciden", "Error", WIDTH);
                         LabelContraseñaRepetir.setText("No coinciden contraseñas");
                         LabelContraseñaRepetir.setVisible(true);
                         jtContraseñaRepetir.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
                         jtContraseñaNueva.setText("");
                         jtContraseñaRepetir.setText("");
                         jtContraseñaNueva.requestFocus();
                     }
                }//fin contraseña actual correcta
                else
                {//si la contraseña en la db no coincide error y blanqueo las contraseñas y me posiciono en anterior
                     //JOptionPane.showMessageDialog(null, "Debe ingresar su contraseña actual de forma correcta", "Error", WIDTH);
                     jLabelContraseñaActual.setText("Contraseña incorrecta");
                     jLabelContraseñaActual.setVisible(true);
                     jtContraseñaAnterior.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
                     jtContraseñaAnterior.requestFocus();
                     jtContraseñaAnterior.setText("");
                }//fin contraseña incorrecta
            }//fin contraseñas no estan en blanco
          }//Fin usuario no existe
       }
       else
       {
            LabelNombreUsuario.setText("Debe escribir un nombre");
            LabelNombreUsuario.setVisible(true);
            jtfNombreUsuario.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
            jtfNombreUsuario.requestFocus();
       }
       }//Fin modo modificacion
       
       else
       {//caso creacion
       //modo creacion
         if(jtfNombreUsuario.getText().isEmpty() == false)
           {
          if(oConsultaUsuarios.comprobarUsuarioExiste(jtfNombreUsuario.getText()) && (oConsultaUsuarios.getUsuarioPorUsuario(jtfNombreUsuario.getText()).getIdUsuario()!=id))
          {//en caso de modificacion y creacion no puede existir, por lo que se evalua al principio
             //JOptionPane.showMessageDialog(this,"Usuario Existe");
             LabelNombreUsuario.setText("Usuario existente");
             LabelNombreUsuario.setVisible(true);
             jtfNombreUsuario.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
             jtfNombreUsuario.requestFocus();
             jtfNombreUsuario.setSelectionStart(0); 
             jtfNombreUsuario.setSelectionEnd(jtfNombreUsuario.getText().length()); 
          }
         else{//Si el usuario no existe entonce puedo proseguir
           String contraseña="";
           if (oConsultaUsuarios.comprobarContraseña(jtContraseñaAnterior.getText()))
           {//comprobar si cumple con reglas de seguridad
                if (jtContraseñaAnterior.getText().equals(jtContraseñaNueva.getText())) 
                {//si ya existe error y no hago nada
                    contraseña= DigestUtils.md5Hex(jtContraseñaAnterior.getText());
                    TipoUsuario tu= (TipoUsuario) jcbTipoUsuario.getSelectedItem();
                    Usuario user = new Usuario(0,jtfNombreUsuario.getText(),contraseña,tu.getIdTipoUsuario());
                    oConsultaUsuarios.agregarUsuario(user);
                    JOptionPane.showMessageDialog(this,"Usuario Agregado");
                    modoNuevoUsuario();
                }
                else
                {//Si las contraseñas son correctas, si cumple con requisitos de seguridad y el usuario no existe creo el usuario
                    LabelContraseñaRepetir.setText("No coinciden contraseñas");
                    LabelContraseñaRepetir.setVisible(true);
                    jtContraseñaNueva.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
                    jtContraseñaAnterior.setText("");
                    jtContraseñaNueva.setText("");
                    jtContraseñaAnterior.requestFocus();
                }//fin else usuario no existe    
             }
             else
             {
                JOptionPane.showMessageDialog(this,"La contraseña no cumple con los requisitos de seguridad minimos.\n (Longitud mayor a 5, contener minimo una mayuscula y contener minimo un numero)");
                jtContraseñaAnterior.setText("");
                jtContraseñaNueva.setText("");
                jtContraseñaAnterior.requestFocus();
             }
        }//fin else usuario existe
          }
       else
       {
            LabelNombreUsuario.setText("Debe escribir un nombre");
            LabelNombreUsuario.setVisible(true);
            jtfNombreUsuario.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
            jtfNombreUsuario.requestFocus();
       }
       }//fin else bandera mod crea
       cargarTabla(oConsultaUsuarios.getAllUsuario());
       
    }//GEN-LAST:event_jBGuardarActionPerformed
    private void modoNuevoUsuario(){
        jLabelContraseñaActual.setText("NUEVA CONTRASEÑA");
        jtContraseñaAnterior.setText("");
        jLabelContraseñaNueva.setText("REPETIR CONTRASEÑA");
        jtContraseñaNueva.setText("");
        jLabelContraseñaRepetir.setVisible(false);
        jtContraseñaRepetir.setText("");
        jtContraseñaRepetir.setVisible(false);
        jXTaskPane2.setCollapsed(true);
        jXTaskPane2.setTitle("Nuevo Usuario");
        jtfbuscarUsuario.setEnabled(true);
        jtfbuscarUsuario.setEditable(true);
        jtableUsuarios.setEnabled(true);
        jtfNombreUsuario.setText("");
        jBGuardar.setText("Agregar");
        jBEliminar.setVisible(false);
        
        blanquearMensajes();
        System.out.println("modo nuevo usuario");
   }
    private void modoModificaUsuario(){
        jLabelContraseñaActual.setText("CONTRASEÑA ACTUAL");
        jtContraseñaAnterior.setText("");
        jLabelContraseñaNueva.setText("CONTRASEÑA NUEVA");
        jtContraseñaNueva.setText("");
        jLabelContraseñaRepetir.setVisible(true);
        jtContraseñaRepetir.setVisible(true);
        jLabelContraseñaRepetir.setText("REPETIR CONTRASEÑA");
        jtContraseñaRepetir.setText("");
        jXTaskPane2.setCollapsed(false);
        jXTaskPane2.setTitle("Modificar Usuario");
        jtfNombreUsuario.setText("");
        jBGuardar.setText("Modificar");
        blanquearMensajes();
        jcbTipoUsuario.setSelectedIndex(0);
        System.out.println("modo modificar usuario");
    }
    private void blanquearMensajes(){
        LabelNombreUsuario.setVisible(false);
        LabelContraseñaActual.setVisible(false);
        LabelContraseñaNueva.setVisible(false);
        LabelContraseñaRepetir.setVisible(false);
        Labeltipousuario.setVisible(false);
        System.out.println("blanqueados labels");
        jtfNombreUsuario.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
        jtContraseñaAnterior.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
        jtContraseñaNueva.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
        jtContraseñaRepetir.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));

}
    private void btnCancelar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelar1ActionPerformed
       int respuesta=JOptionPane.showConfirmDialog(null, "¿Confirma la cancelación? \n Los datos no seran guardados","Advertencia", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            //confirmamos la eliminacion
            if(respuesta == 0)
            {
                jXTaskPane2.setCollapsed(true);
                jXTaskPane2.setTitle("Nuevo Usuario");
                modoNuevoUsuario();
                bandera=0;
            } 
    }//GEN-LAST:event_btnCancelar1ActionPerformed

    private void jBEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBEliminarActionPerformed
        v=jtableUsuarios.getSelectedRow();//n° fila selccionada
        Usuario tfv = new Usuario();
            indiceModelo = jtableUsuarios.convertRowIndexToModel (v);//convierte el indice de la vista en el indice del modelo
            tfv = oConsultaUsuarios.getUsuarioPorId(getIdUsuario(indiceModelo));
            int respuesta=JOptionPane.showConfirmDialog(null, "¿Realmente desea quitar este usuario de la lista?","Advertencia", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            //confirmamos la eliminacion
            if(respuesta == 0)
            {
                oConsultaUsuarios.eliminarUsuario(tfv.getIdUsuario());
                modoNuevoUsuario();
                bandera = 0;
                cargarTabla(oConsultaUsuarios.getAllUsuario());
            }
            
    }//GEN-LAST:event_jBEliminarActionPerformed

    private void jcbTipoUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbTipoUsuarioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jcbTipoUsuarioActionPerformed

    private void jtfNombreUsuarioFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtfNombreUsuarioFocusLost
        // TODO add your handling code here:
        blanquearMensajes();
    }//GEN-LAST:event_jtfNombreUsuarioFocusLost

    private void jtContraseñaNuevaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtContraseñaNuevaActionPerformed
        blanquearMensajes();
    }//GEN-LAST:event_jtContraseñaNuevaActionPerformed

    private void jtfNombreUsuarioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfNombreUsuarioKeyTyped
        // TODO add your handling code here:
        if(jtfNombreUsuario.getText().length() == 20) {
              getToolkit().beep();
              evt.consume();
              jtfNombreUsuario.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
              LabelNombreUsuario.setText("Longitud maxima 20 caracteres");
              LabelNombreUsuario.setVisible(true);
          }
        else
            blanquearMensajes();
    }//GEN-LAST:event_jtfNombreUsuarioKeyTyped

    private void jtContraseñaRepetirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtContraseñaRepetirActionPerformed
        blanquearMensajes();
    }//GEN-LAST:event_jtContraseñaRepetirActionPerformed

    private void jtContraseñaRepetirFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtContraseñaRepetirFocusLost
        blanquearMensajes();
    }//GEN-LAST:event_jtContraseñaRepetirFocusLost

    private void jtContraseñaNuevaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtContraseñaNuevaFocusLost
        blanquearMensajes();
        if(jtContraseñaNueva.getText().length() == 0)
        {
            jtContraseñaRepetir.setEnabled(false);
            jtContraseñaRepetir.setEditable(false);
            jtContraseñaRepetir.setText("");
        }
        else
        {
            jtContraseñaRepetir.setEnabled(true);
            jtContraseñaRepetir.setEditable(true);
        }
    }//GEN-LAST:event_jtContraseñaNuevaFocusLost

    private void jtContraseñaAnteriorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtContraseñaAnteriorActionPerformed
        blanquearMensajes();
    }//GEN-LAST:event_jtContraseñaAnteriorActionPerformed

    private void jtContraseñaAnteriorFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtContraseñaAnteriorFocusLost
        blanquearMensajes();
        if(jtContraseñaAnterior.getText().isEmpty())
        {
            jtContraseñaNueva.setEnabled(false);
            jtContraseñaNueva.setEditable(false);
            jtContraseñaNueva.setText("");
            jtContraseñaRepetir.setEnabled(false);
            jtContraseñaRepetir.setEditable(false);
            jtContraseñaRepetir.setText("");
        }
        else
        {
            jtContraseñaNueva.setEnabled(true);
            jtContraseñaNueva.setEditable(true);
        }
    }//GEN-LAST:event_jtContraseñaAnteriorFocusLost

    private void jtContraseñaNuevaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtContraseñaNuevaKeyTyped
        blanquearMensajes();
        char c=evt.getKeyChar();
        String contraseñaNue = jtContraseñaNueva.getText();
        int longitud = contraseñaNue.length()+1;
        if(jtContraseñaAnterior.getText().length() == 45) {
              getToolkit().beep();
              evt.consume();
              JOptionPane.showMessageDialog(null, "Longitud maxima 45 caracteres", "Error", WIDTH);
          }
        if(longitud == 0)
        {
            jtContraseñaRepetir.setEnabled(false);
            jtContraseñaRepetir.setEditable(false);
            jtContraseñaRepetir.setText("");
        }
        if(longitud >= 1 && c != '\b')
        {
            jtContraseñaRepetir.setEnabled(true);
            jtContraseñaRepetir.setEditable(true);
        }
        if(longitud == 1 && c == '\b')
        {
            jtContraseñaRepetir.setEnabled(false);
            jtContraseñaRepetir.setEditable(false);
            jtContraseñaRepetir.setText("");
        }
    }//GEN-LAST:event_jtContraseñaNuevaKeyTyped

    private void jtContraseñaRepetirKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtContraseñaRepetirKeyTyped
        if(jtContraseñaAnterior.getText().length() == 45) {
              getToolkit().beep();
              evt.consume();
              JOptionPane.showMessageDialog(null, "Longitud maxima 45 caracteres", "Error", WIDTH);
          }
        blanquearMensajes();
    }//GEN-LAST:event_jtContraseñaRepetirKeyTyped

    private void jtContraseñaAnteriorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtContraseñaAnteriorKeyTyped
        blanquearMensajes();
        char c=evt.getKeyChar();
        String contraseñaAnt = jtContraseñaAnterior.getText();
        int longitud = contraseñaAnt.length()+1;
        if(jtContraseñaAnterior.getText().length() == 45) {
              getToolkit().beep();
              evt.consume();
              JOptionPane.showMessageDialog(null, "Longitud maxima 45 caracteres", "Error", WIDTH);
          }
        else
            blanquearMensajes();
        if(longitud == 0)
        {
            jtContraseñaNueva.setEnabled(false);
            jtContraseñaNueva.setEditable(false);
            jtContraseñaNueva.setText("");
            jtContraseñaRepetir.setEnabled(false);
            jtContraseñaRepetir.setEditable(false);
            jtContraseñaRepetir.setText("");
        }
        if(longitud >= 1 && c != '\b')
        {
            jtContraseñaNueva.setEnabled(true);
            jtContraseñaNueva.setEditable(true);
        }
        if(longitud == 1 && c == '\b')
        {
            jtContraseñaNueva.setEnabled(false);
            jtContraseñaNueva.setEditable(false);
            jtContraseñaNueva.setText("");
            jtContraseñaRepetir.setEnabled(false);
            jtContraseñaRepetir.setEditable(false);
            jtContraseñaRepetir.setText("");
        }
    }//GEN-LAST:event_jtContraseñaAnteriorKeyTyped

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel LOCALIDAD2;
    private javax.swing.JLabel LabelContraseñaActual;
    private javax.swing.JLabel LabelContraseñaNueva;
    private javax.swing.JLabel LabelContraseñaRepetir;
    private javax.swing.JLabel LabelNombreUsuario;
    private javax.swing.JLabel Labeltipousuario;
    private javax.swing.JButton btnCancelar1;
    private javax.swing.JButton jBEliminar;
    private javax.swing.JButton jBGuardar;
    private javax.swing.JLabel jLabelBuscar;
    private javax.swing.JLabel jLabelContraseñaActual;
    private javax.swing.JLabel jLabelContraseñaNueva;
    private javax.swing.JLabel jLabelContraseñaRepetir;
    private javax.swing.JLabel jLabelNombreUsuario;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private org.jdesktop.swingx.JXTaskPane jXTaskPane2;
    private org.jdesktop.swingx.JXComboBox jcbTipoUsuario;
    private javax.swing.JPasswordField jtContraseñaAnterior;
    private javax.swing.JPasswordField jtContraseñaNueva;
    private javax.swing.JPasswordField jtContraseñaRepetir;
    private javax.swing.JTable jtableUsuarios;
    private javax.swing.JTextField jtfNombreUsuario;
    private javax.swing.JTextField jtfbuscarUsuario;
    // End of variables declaration//GEN-END:variables
}
