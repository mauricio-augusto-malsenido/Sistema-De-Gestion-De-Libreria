package libreria;

import Compra.ConsultaCompra;
import Compra.LineaProveedorLibro;
import Compra.TablaLibroProveedor;
import Compra.proveedor;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import static java.awt.image.ImageObserver.WIDTH;
import java.util.ArrayList;
import java.util.Iterator;
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
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

/**
 *
 * @author SEBASTIAN
 */
public final class jpLibro extends javax.swing.JPanel {
private consultasLibreria oConsultaLibreria;
private ConsultaCompra oConsultaCompra;
private TableRowSorter<TableModel> sorter;//para ordenar la tabla
private int v=0,indiceModelo=0,bandera=0,id=0,stockCritico=0,longitud,longitud2,idlm;
private Libro pLibro, lib;
private autor AutorLibro;
private genero GeneroLibro;
private editorial EditorialLibro;
private boolean controLabel=false,controlT,controlE,controlA,controlP,controlC,controlCO,controlV,controlR,EstadoLibro,controlCOM,controlCA,controlCE,controlCG,controlCAM;
private ArrayList<Integer> tbIdp;
private ArrayList<LineaProveedorLibro> tbLineaProveedor;
private DefaultTableModel dftmp= new DefaultTableModel(){
@Override
      public boolean isCellEditable(int row, int column) {
                return false;}};;
//BANDERA 0 NUEVO
//BANDERA 1 MODIFICACION
     public jpLibro() {
        initComponents();
        oConsultaLibreria = new consultasLibreria();
        oConsultaCompra = new ConsultaCompra();
        tbIdp= new ArrayList<>();
        tbLineaProveedor= new ArrayList<>();
        
        bandera=0;
        busqueda();
        CargarComboAutores();
        CargarComboEditoriales();
        CargarComboGeneros();
        CargarComboProveedores();
        cargarTablaPL();
        
        AutoCompleteDecorator.decorate(this.jcbAutor);
        AutoCompleteDecorator.decorate(this.jcbGenero);
        AutoCompleteDecorator.decorate(this.jcbEditorial);
        AutoCompleteDecorator.decorate(this.jcbProveedor);
        
        labelAño.setVisible(false);
        labelCantidad.setVisible(false);
        labelPrecio.setVisible(false);
        labelTitulo.setVisible(false);
        labelEdicion.setVisible(false);
        labelProveedor.setVisible(false);
        labelCosto.setVisible(false);
        labelAutor.setVisible(false);
        labelGenero.setVisible(false);
        labelEditorial.setVisible(false);
        jXTaskPane2.setCollapsed(true);
        btnDeshab.setText("Deshabilitar");
        btnDeshab.setEnabled(false);
        btnQProv.setEnabled(false);
        
          jtableLibros.addMouseListener(new MouseAdapter() 
        {
           @Override
           public void mouseClicked(MouseEvent e) 
           {
             if(e.getClickCount()== 2){
              int fila = jtableLibros.rowAtPoint(e.getPoint());
              int columna = jtableLibros.columnAtPoint(e.getPoint());
             /*El método rowAtPoint() nos devuelve -1 si pinchamos en el JTable
              pero fuera de cualquier fila*/
              
                     if ((fila > -1) && (columna > -1))
                     {
                       v=jtableLibros.getSelectedRow();//n° fila selccionada
                       indiceModelo = jtableLibros.convertRowIndexToModel (v);//convierte el indice de la vista en el indice del modelo 
                       idlm=getIdLibro(indiceModelo);
                       jXTaskPane2.setTitle("Modificar Libro");
                       jXTaskPane2.setCollapsed(false);
                       bandera=1;
                       tbLineaProveedor.clear();
                       cargarCampos(oConsultaLibreria.getLibroPorId(getIdLibro(indiceModelo)));
                       cargarTablaplbd(oConsultaCompra.getAllPlId(idlm));
                       lib = oConsultaLibreria.getLibroPorId(getIdLibro(indiceModelo));
                       btnDeshab.setEnabled(true);
                       jtableLibros.setEnabled(false);
                       jtfbuscarLibro.setEnabled(false);
                       jtfbuscarLibro.setEditable(false);
                     }
             }
           }
        });
        
        cargarTabla(oConsultaLibreria.getAllLibro());
    }
    
     private void busqueda(){
     
     jtfbuscarLibro.getDocument().addDocumentListener(
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
        int indiceColumnaTabla=1;
        try {
            
          rf = RowFilter.regexFilter(jtfbuscarLibro.getText().toUpperCase(), indiceColumnaTabla);
        } catch (java.util.regex.PatternSyntaxException e) {
        }
        sorter.setRowFilter(rf);
    }
     
     public void cargarTabla(List<Libro> l){
    
        String[] columnNames = {"Id","Titulo","Autor","Edicion","Año","Editorial","Stock","ESTADO"};
        int[] anchos = {2,100,100,10,20,40,40,10};
        
        Object[][] data = new Object[l.size()][columnNames.length];
         
       for (int i = 0; i < l.size(); i++) {
        
            data[i][0] = l.get(i).getIdLibro();
            data[i][1] = l.get(i).getTitulo().toUpperCase();
            data[i][2] = oConsultaLibreria.getAutorPorId(l.get(i).getIdAutor()).getNombreAutor().toUpperCase();
            data[i][3] = l.get(i).getEdicion();
            data[i][4] = l.get(i).getAño();
            data[i][5] = oConsultaLibreria.getEditorialPorId(l.get(i).getIdEditorial()).getNombreEditorial().toUpperCase();
            data[i][6] = l.get(i).getStock();
            
            if(l.get(i).getEstadoLibro()== true){data[i][7]="HABILITADO";}
            else{data[i][7] = "DESHABILITADO";}
        }
       DefaultTableModel dftm = new DefaultTableModel(data, columnNames)
                {
		//metodo para que las celdas del jtable sean no-editables	
                    @Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
       jtableLibros.setModel(dftm);
       for(int i = 0; i < jtableLibros.getColumnCount(); i++) {

        jtableLibros.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);

        }
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        for(int i=0;i<jtableLibros.getColumnCount();i++)
        {
            jtableLibros.getColumnModel().getColumn(i).setCellRenderer(tcr);
        }
       sorter = new TableRowSorter<TableModel>(dftm);
        jtableLibros.setRowSorter(sorter);
        jtableLibros.getRowSorter().toggleSortOrder(0);
        jtableLibros.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        jtfnombre.setText("");
        jtfcantidad.setText("");
        jtfprecio.setText("");
        jtfedicion.setText("");
    }
     private void cargarTablaPL(){
       if(bandera==0){dftmp.setColumnCount(0);
        dftmp.setNumRows(0);
        dftmp.addColumn("Proveedor");
        }
       else{
       dftmp.setColumnCount(0);
        dftmp.setNumRows(0);
        dftmp.addColumn("Id");
        dftmp.addColumn("Proveedor");
        }
       if(!tbLineaProveedor.isEmpty())btnQProv.setEnabled(true);else btnQProv.setEnabled(false);
       Iterator<LineaProveedorLibro> iter = tbLineaProveedor.iterator();
       while(iter.hasNext()){
           LineaProveedorLibro lp= iter.next();
            Object[] fila=new Object[2];
            fila[0]=oConsultaCompra.getProveedorPorId(lp.getIdProveedor()).getNombreProveedor();
            fila[1]=lp.getId();
            dftmp.addRow(fila);
        }
            
        jtableProveedores.setModel(dftmp);
    }
     public void cargarTablaplbd(List<LineaProveedorLibro> l){
        String[] columnNames = {"Id","Proveedor"};
        int[] anchos = {2,50};
        
       Object[][] data = new Object[l.size()][columnNames.length];
         
       for (int i = 0; i < l.size(); i++) {
        data[i][1] =oConsultaCompra.getProveedorPorId(l.get(i).getIdProveedor());
        data[i][0] =l.get(i).getId();
        LineaProveedorLibro lip= new LineaProveedorLibro(l.get(i).getId(), idlm, l.get(i).getIdProveedor());
        tbLineaProveedor.add(lip);
       }
       DefaultTableModel dftm = new DefaultTableModel(data, columnNames)
                {
	              @Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
       jtableProveedores.setModel(dftm);
       for(int i = 0; i < jtableProveedores.getColumnCount(); i++) {
       jtableProveedores.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
       if(l.size()>=1)btnQProv.setEnabled(true);else btnQProv.setEnabled(false);
     }
     private int getIdLibro(int im){
        String[] fila= new String[1];//almaceno los valores del registro seleccionado en el string "fila"
        fila[0]=""+jtableLibros.getModel().getValueAt(im, 0);
        
        int idRep=Integer.parseInt(fila[0]);
        
        return  idRep;
    }
     private int getIdProv(int im){
        String[] fila= new String[1];//almaceno los valores del registro seleccionado en el string "fila"
        fila[0]=""+jtableProveedores.getModel().getValueAt(im,0);
        int idRep=Integer.parseInt(fila[0]);
       // int idRep=oConsultaCompra.getProveedorPorNombre(fila[0]).getIdProveedor();
        return  idRep;
    }
     
     private  void cargarCampos(Libro libro){
        if(libro.getEstadoLibro()==true)btnDeshab.setText("Deshabilitar");
        else{btnDeshab.setText("Habilitar");}
        
        AutorLibro=oConsultaLibreria.getAutorPorId(libro.getIdAutor());
        GeneroLibro=oConsultaLibreria.getGeneroPorId(libro.getIdGenero());
        EditorialLibro=oConsultaLibreria.getEditorialPorId(libro.getIdEditorial());
        
            
        id=libro.getIdLibro();
        stockCritico=libro.getStockCritico();
        EstadoLibro=libro.getEstadoLibro();
        
        jtfnombre.setText(libro.getTitulo());
        jtfedicion.setText(libro.getEdicion());
        jtfcantidad.setText(""+libro.getStock());
        jtfprecio.setText(""+libro.getPrecio());
        jtfAño.setText(""+libro.getAño());
        jcbAutor.setSelectedItem(AutorLibro);
        jcbEditorial.setSelectedItem(EditorialLibro);
        jcbGenero.setSelectedItem(GeneroLibro);
        jtfCosto.setText(""+libro.getCostoLibro());
    }
     private Libro capturarCampos(){
        autor au= (autor) jcbAutor.getSelectedItem();
        genero ge= (genero) jcbGenero.getSelectedItem();
        editorial ed= (editorial) jcbEditorial.getSelectedItem();
        pLibro= new Libro(
                id,
                jtfnombre.getText().toUpperCase(),
                jtfedicion.getText().toUpperCase(),
                Integer.parseInt(jtfAño.getText()),
                Float.parseFloat(jtfprecio.getText()),
                Integer.parseInt(jtfcantidad.getText()),
                stockCritico,
                ed.getIdEditorial(),
                au.getIdAutor(),
                ge.getIdGenero(),
                EstadoLibro,
                Float.parseFloat(jtfCosto.getText()),
                0);
        return  pLibro;
        }
     private void limpiarCampos(){
                    jtfnombre.setText("");
                    jtfedicion.setText(""); 
                    jtfAño.setText("");
                    jtfprecio.setText("");
                    jtfCosto.setText("");
                    jtfcantidad.setText("");
                    labelTitulo.setVisible(false);
                    jtfnombre.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
                    labelCantidad.setVisible(false);
                    jtfcantidad.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
                    labelPrecio.setVisible(false);
                    jtfprecio.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
                    labelEdicion.setVisible(false);
                    jtfedicion.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
                    labelTitulo.setVisible(false);
                    jtfnombre.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
                    labelEditorial.setVisible(false);
                    labelAutor.setVisible(false);
                    labelGenero.setVisible(false);
                    labelProveedor.setVisible(false);
                    
                    jcbAutor.setSelectedIndex(0);
                    jcbGenero.setSelectedIndex(0);
                    jcbEditorial.setSelectedIndex(0);
                    
                    btnDeshab.setEnabled(false);
     }
     
     private void CargarComboProveedores(){
         jcbProveedor.addItem("");
         List<proveedor> lista = oConsultaCompra.getAllProveedorHab();
        for (int i = 0; i < lista.size(); i++) {
            proveedor emc= lista.get(i);
            jcbProveedor.addItem(emc);
            proveedor objeto=(proveedor) jcbProveedor.getItemAt(2);
        }
    }
     private void CargarComboAutores(){
     jcbAutor.addItem("");
     List<autor> lista = oConsultaLibreria.getAllAutorHab();
        for (int i = 0; i < lista.size(); i++) {
            //autor emc=new autor(lista.get(i).getIdAutor(), lista.get(i).getNombreAutor());
            autor emc=lista.get(i);
            jcbAutor.addItem(emc);
            autor objeto=(autor) jcbAutor.getItemAt(1);
          }
    }
     private void CargarComboGeneros(){
     jcbGenero.addItem("");
     List<genero> lista = oConsultaLibreria.getAllGeneroHab();
       for (int i = 0; i < lista.size(); i++) {
            genero ge=lista.get(i);
            jcbGenero.addItem(ge);
            genero objeto=(genero) jcbGenero.getItemAt(1);
       }
        
	
    }
     private void CargarComboEditoriales(){
     jcbEditorial.addItem("");
         List<editorial> lista = oConsultaLibreria.getAllEditorialHab();
            for (int i = 0; i < lista.size(); i++) {
            editorial ed=lista.get(i);
            jcbEditorial.addItem(ed);
            editorial objeto=(editorial) jcbEditorial.getItemAt(1);
    }}
     
     public boolean comprobarNulosT(){
                   if(jtfnombre.getText().equals(""))
                    {
                    labelTitulo.setVisible(true);
                    labelTitulo.setText("Debe ingresar un titulo de libro");
                    jtfnombre.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
                    controlT=true;
                    }
                    else{
                    controlT=false;
                    }
                    
                    return controlT;
    }
     public boolean comprobarNulosE(){
                    if(jtfedicion.getText().equals(""))
                    {
                    labelEdicion.setVisible(true);
                    labelEdicion.setText("Debe ingresar un número de edicion");
                    jtfedicion.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
                    controlE=true;
                    }
                    else{
                    controlE=false;
                    }
                    
                    return controlE;
    }
     public boolean comprobarNulosA(){
                if(jtfAño.getText().equals(""))
                    {
                    labelAño.setVisible(true);
                    labelAño.setText("Debe ingresar un año");
                    jtfAño.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
                    controlA=true;
                    }
                    else{
                    controlA=false;
                    }
                    
                    return controlA;
    }
     public boolean comprobarNulosP(){
                if(jtfprecio.getText().equals(""))
                    {
                    labelPrecio.setVisible(true);
                    labelPrecio.setText("Debe ingresar un precio");
                    jtfprecio.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
                    controlP=true;
                    }
                    else{
                    controlP=false;
                    }
                    
                    return controlP;
    }
     public boolean comprobarNulosC(){
                if(jtfcantidad.getText().equals(""))
                    {
                    labelCantidad.setVisible(true);
                    labelCantidad.setText("Debe ingresar una cantidad");
                    jtfcantidad.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
                    controlC=true;
                    }
                    else{
                    controlC=false;
                    }
                    
                    return controlC;
    }
     public boolean comprobarNulosCO(){
                if(jtfCosto.getText().equals(""))
                    {
                    labelCosto.setVisible(true);
                    labelCosto.setText("Debe ingresar costo");
                    jtfCosto.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
                    controlCO=true;
                    }
                    else{
                    controlCO=false;
                    }
                    
                    return controlCO;
    }
     public boolean comprobarTablaNula(){
        if(bandera==0){
            if(tbLineaProveedor.isEmpty())
                //if(tblp.getLineas().isEmpty())
               {controlV=true;
               labelProveedor.setVisible(true);
               labelProveedor.setText("Debe ingresar un proveedor");
               }
               else{
               controlV=false;
               }
               }
                else{
                 controlV=false;
                 jtableProveedores.setModel(dftmp);
                }
        return controlV;
    }
     public boolean comprobarrepetidos(int id){
        
        for(int i=0; i< tbLineaProveedor.size();i++){
            if(tbLineaProveedor.get(i).getIdProveedor()== id){
            controlR=true;
            JOptionPane.showMessageDialog(null, "Proveedor repetido", "ADVERTENCIA", WIDTH);
            break;
            }
            else{controlR=false;}
        }
        
        return controlR;
    }
     
     
     public boolean comprobarCombosAutores(){
         if(jcbAutor.getSelectedIndex()== 0 || jcbAutor.getSelectedIndex()== -1)
         {labelAutor.setVisible(true);
             labelAutor.setText("Debe seleccionar un autor");
             controlCA=true;}
         else controlCA=false;
             
        return controlCA;
    }
     public boolean comprobarCombosGeneros(){
         if(jcbGenero.getSelectedIndex()==0 || jcbGenero.getSelectedIndex()==-1)
         {  labelGenero.setVisible(true);
             labelGenero.setText("Debe seleccionar un genero");
             controlCG=true;}
         else controlCG=false;
             
        return controlCG;
    }
     public boolean comprobarCombosEditorial(){
         if(jcbEditorial.getSelectedIndex()== 0 || jcbEditorial.getSelectedIndex()== -1)
         {   labelEditorial.setVisible(true);
             labelEditorial.setText("Debe seleccionar un editorial");
             controlCE=true;}
         else {controlCE=false;}
        return controlCE;
    }
     public boolean comprobarExistencia(){
         boolean control = false;
         Libro l = this.capturarCampos();
         for(int i=0; i<oConsultaLibreria.getAllLibro().size();i++)
         {
             String nombre = oConsultaLibreria.getAllLibro().get(i).getTitulo();
             String edicion = oConsultaLibreria.getAllLibro().get(i).getEdicion();
             autor Autor = oConsultaLibreria.getAutorPorId(oConsultaLibreria.getAllLibro().get(i).getIdAutor());
             if(l.getTitulo().contentEquals(nombre) && l.getEdicion().contentEquals(edicion) && l.getIdAutor() == Autor.getIdAutor() && bandera == 0)
             {
                 control = true;
             }
             if(l.getTitulo().contentEquals(nombre) && l.getEdicion().contentEquals(edicion) && l.getIdAutor() == Autor.getIdAutor() && bandera == 1)
             {
                 control = true;
             }
         }
         if((bandera == 1) && (l.getTitulo().contentEquals(lib.getTitulo()) && l.getEdicion().contentEquals(lib.getEdicion()) && l.getIdAutor() == lib.getIdAutor()))
         {
             control = false;
         }
         return control;
     }
     public boolean comprobarCombos(){
     if(comprobarCombosAutores()!=false || comprobarCombosGeneros()!=false || comprobarCombosEditorial()!=false)
        {
         controlCOM=true;
        }
        else{controlCOM=false;} 
         
        
     return controlCOM;
    }
     
     
     @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jtfbuscarLibro = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtableLibros = new javax.swing.JTable();
        jXTaskPane2 = new org.jdesktop.swingx.JXTaskPane();
        jPanel4 = new javax.swing.JPanel();
        jtfnombre = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        btnguardar1 = new javax.swing.JButton();
        btnCancelar1 = new javax.swing.JButton();
        btnDeshab = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jtfedicion = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jcbEditorial = new org.jdesktop.swingx.JXComboBox();
        jcbAutor = new org.jdesktop.swingx.JXComboBox();
        jLabel6 = new javax.swing.JLabel();
        jcbGenero = new org.jdesktop.swingx.JXComboBox();
        jLabel7 = new javax.swing.JLabel();
        jtfprecio = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jtfcantidad = new javax.swing.JTextField();
        jtfAño = new javax.swing.JTextField();
        labelPrecio = new javax.swing.JLabel();
        labelCantidad = new javax.swing.JLabel();
        labelAño = new javax.swing.JLabel();
        btnNuevaEditorial = new javax.swing.JButton();
        btnNuevoAutor = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        labelTitulo = new javax.swing.JLabel();
        labelEdicion = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        btnAProv = new javax.swing.JButton();
        btnQProv = new javax.swing.JButton();
        jcbProveedor = new org.jdesktop.swingx.JXComboBox();
        jScrollPane4 = new javax.swing.JScrollPane();
        jtableProveedores = new javax.swing.JTable();
        labelProveedor = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jtfCosto = new javax.swing.JTextField();
        labelCosto = new javax.swing.JLabel();
        labelAutor = new javax.swing.JLabel();
        labelGenero = new javax.swing.JLabel();
        labelEditorial = new javax.swing.JLabel();

        jLabel1.setText("Buscar Libro");

        jtableLibros.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "id", "Titulo", "Edicion", "Año", "Editorial", "Stock"
            }
        ));
        jScrollPane1.setViewportView(jtableLibros);

        jXTaskPane2.setTitle("Nuevo Libro");

        jPanel4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel4.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jtfnombre.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(0, 0, 0), null));
        jtfnombre.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jtfnombreFocusGained(evt);
            }
        });
        jtfnombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtfnombreActionPerformed(evt);
            }
        });
        jtfnombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtfnombreKeyTyped(evt);
            }
        });

        jLabel14.setText("TITULO **");

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

        btnDeshab.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/eliminar_1.png"))); // NOI18N
        btnDeshab.setText("Deshabilitar");
        btnDeshab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeshabActionPerformed(evt);
            }
        });

        jLabel2.setText("EDICION **");

        jtfedicion.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(0, 0, 0), null));
        jtfedicion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtfedicionKeyTyped(evt);
            }
        });

        jLabel3.setText("AUTOR **");

        jLabel4.setText("AÑO **");

        jLabel5.setText("EDITORIAL **");

        jcbEditorial.setToolTipText("");
        jcbEditorial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbEditorialActionPerformed(evt);
            }
        });

        jcbAutor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbAutorActionPerformed(evt);
            }
        });

        jLabel6.setText("GENERO **");

        jcbGenero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbGeneroActionPerformed(evt);
            }
        });

        jLabel7.setText("PRECIO **");

        jtfprecio.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(0, 0, 0), null));
        jtfprecio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtfprecioKeyTyped(evt);
            }
        });

        jLabel8.setText("CANTIDAD **");

        jtfcantidad.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(0, 0, 0), null));
        jtfcantidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtfcantidadKeyTyped(evt);
            }
        });

        jtfAño.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(0, 0, 0), null));
        jtfAño.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtfAñoKeyTyped(evt);
            }
        });

        labelPrecio.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        labelPrecio.setForeground(new java.awt.Color(204, 0, 51));
        labelPrecio.setText("jLabel9");

        labelCantidad.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        labelCantidad.setForeground(new java.awt.Color(204, 0, 51));
        labelCantidad.setText("jLabel9");

        labelAño.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        labelAño.setForeground(new java.awt.Color(204, 0, 51));
        labelAño.setText("jLabel9");

        btnNuevaEditorial.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/anadir-bala-icono-4686-32.png"))); // NOI18N
        btnNuevaEditorial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevaEditorialActionPerformed(evt);
            }
        });

        btnNuevoAutor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/anadir-bala-icono-4686-32.png"))); // NOI18N
        btnNuevoAutor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoAutorActionPerformed(evt);
            }
        });

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/anadir-bala-icono-4686-32.png"))); // NOI18N
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        labelTitulo.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        labelTitulo.setForeground(new java.awt.Color(204, 0, 0));
        labelTitulo.setText("jLabel9");

        labelEdicion.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        labelEdicion.setForeground(new java.awt.Color(204, 0, 0));
        labelEdicion.setText("jLabel10");

        jLabel9.setText("** Campos obligatorios");

        jLabel10.setText("PROVEEDOR**");

        btnAProv.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/flecha-azul-hacia-abajo-icono-7343-16.png"))); // NOI18N
        btnAProv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAProvActionPerformed(evt);
            }
        });

        btnQProv.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/eliminar_1.png"))); // NOI18N
        btnQProv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQProvActionPerformed(evt);
            }
        });

        jtableProveedores.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null},
                {null},
                {null},
                {null}
            },
            new String [] {
                "a"
            }
        ));
        jScrollPane4.setViewportView(jtableProveedores);

        labelProveedor.setForeground(new java.awt.Color(204, 0, 51));
        labelProveedor.setText("jLabel11");

        jLabel11.setText("COSTO**");

        jtfCosto.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(0, 0, 0), null));
        jtfCosto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtfCostoKeyTyped(evt);
            }
        });

        labelCosto.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        labelCosto.setForeground(new java.awt.Color(204, 0, 51));
        labelCosto.setText("jLabel9");

        labelAutor.setForeground(new java.awt.Color(204, 0, 0));
        labelAutor.setText("jLabel12");

        labelGenero.setForeground(new java.awt.Color(204, 0, 0));
        labelGenero.setText("jLabel13");

        labelEditorial.setForeground(new java.awt.Color(204, 0, 0));
        labelEditorial.setText("jLabel15");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(btnAProv, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jcbProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(labelProveedor, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(666, 666, 666))
                        .addGroup(jPanel4Layout.createSequentialGroup()
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(labelTitulo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jtfnombre, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel4Layout.createSequentialGroup()
                                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jtfedicion))
                                    .addGap(6, 6, 6))
                                .addComponent(labelEdicion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel4)
                                .addComponent(jtfAño, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(labelAño, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jtfprecio, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(labelPrecio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGap(12, 12, 12)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel4Layout.createSequentialGroup()
                                    .addComponent(labelCosto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGap(47, 47, 47))
                                .addGroup(jPanel4Layout.createSequentialGroup()
                                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel11)
                                        .addComponent(jtfCosto, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(18, 18, Short.MAX_VALUE)))
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jtfcantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(labelCantidad, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGap(476, 476, 476))
                        .addGroup(jPanel4Layout.createSequentialGroup()
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel4Layout.createSequentialGroup()
                                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jcbAutor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(btnNuevoAutor, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel4Layout.createSequentialGroup()
                                    .addComponent(labelAutor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGap(88, 88, 88)))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel6)
                                .addComponent(jcbGenero, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(labelGenero, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel5)
                                .addGroup(jPanel4Layout.createSequentialGroup()
                                    .addComponent(labelEditorial, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGap(34, 34, 34))
                                .addComponent(jcbEditorial, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btnNuevaEditorial, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(406, 406, 406)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnQProv)
                            .addComponent(btnguardar1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancelar1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnDeshab)
                        .addGap(96, 96, 96)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(258, 258, 258))))
        );

        jPanel4Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnAProv, btnNuevaEditorial});

        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addGap(6, 6, 6)
                        .addComponent(jtfnombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelTitulo))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(jtfcantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(32, 32, 32)
                        .addComponent(labelCantidad))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtfCosto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelCosto))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtfprecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelPrecio))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(3, 3, 3)
                        .addComponent(jtfAño, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelAño))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(3, 3, 3)
                        .addComponent(jtfedicion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelEdicion)))
                .addGap(12, 12, 12)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jcbAutor, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnNuevoAutor, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelAutor)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jcbGenero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelGenero))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnNuevaEditorial, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(jcbEditorial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelEditorial)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jcbProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnAProv, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnQProv, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(34, 34, 34))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelProveedor)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnguardar1)
                    .addComponent(btnCancelar1)
                    .addComponent(btnDeshab)
                    .addComponent(jLabel9))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jtfAño, jtfCosto, jtfcantidad, jtfedicion, jtfnombre, jtfprecio});

        jXTaskPane2.getContentPane().add(jPanel4);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jtfbuscarLibro, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jXTaskPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 760, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jtfbuscarLibro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jXTaskPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnguardar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnguardar1ActionPerformed
        if( comprobarNulosT()== false && comprobarNulosE()== false 
             && comprobarNulosA()== false && comprobarNulosP()== false
             && comprobarNulosCO()== false  && comprobarNulosC()== false
             && comprobarTablaNula()== false  && comprobarCombos()==false
             && controLabel!=true ){        
                if(comprobarExistencia() == false){
                    if(bandera==1){
                    oConsultaLibreria.modificarLibro(capturarCampos());
                    cargarTabla(oConsultaLibreria.getAllLibro());
                    JOptionPane.showMessageDialog(this,"Libro Modificado");
                    tbLineaProveedor.clear();
                    cargarTablaPL();
                    btnDeshab.setEnabled(true);
                    bandera=0;
                    id=0;
                    EstadoLibro=false;
                    jtableLibros.setEnabled(true);
                    }
                    else{
                    oConsultaLibreria.agregarLibro(capturarCampos());
                    int idUL= oConsultaLibreria.getIdUltimaLibro();
                        for (int i = 0; i <tbLineaProveedor.size(); i++) {
                        //for (int i = 0; i <tblp.getLongitud(); i++) {
                         LineaProveedorLibro lp=new LineaProveedorLibro(0, idUL, tbLineaProveedor.get(i).getIdProveedor());
                         oConsultaCompra.agregarLp(lp);}
                    JOptionPane.showMessageDialog(this,"Libro Agregado");
                    cargarTabla(oConsultaLibreria.getAllLibro());
                    tbLineaProveedor.clear();
                    bandera=0; 
                    cargarTablaPL();
                    }
                    jXTaskPane2.setTitle("Nuevo Libro");
                    jXTaskPane2.setCollapsed(true);
                    jtfbuscarLibro.setEnabled(true);
                    jtfbuscarLibro.setEditable(true);
                    limpiarCampos();
                }
                else{
                    JOptionPane.showMessageDialog(null, "El libro que intenta guardar ya existe", "Error", WIDTH);
                }
        }
    }//GEN-LAST:event_btnguardar1ActionPerformed

    private void btnCancelar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelar1ActionPerformed
        int respuesta=JOptionPane.showConfirmDialog(null, "Se perderan todos los cambios realizados \n¿Desea Salir?","Advertencia", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if(respuesta == 0)
                {
                    limpiarCampos();
                    if(bandera==1){
                    for(int i=0; i<tbIdp.size();i++){
                    oConsultaCompra.eliminaLp(tbIdp.get(i));
                    }tbIdp.clear();}
                    tbLineaProveedor.clear();
                    cargarTablaPL();
                    btnDeshab.setEnabled(false);
                    jXTaskPane2.setTitle("Nuevo Libro");
                    jXTaskPane2.setCollapsed(true);
                    jtfbuscarLibro.setEnabled(true);
                    jtfbuscarLibro.setEditable(true);
                    id=0;
                    EstadoLibro=false;
                    bandera=0;
                    jtableLibros.setEnabled(true);
                }        
        
    }//GEN-LAST:event_btnCancelar1ActionPerformed

    private void jtfAñoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfAñoKeyTyped
            char car = evt.getKeyChar();
            longitud=jtfAño.getText().length();
               if(controlA!=false){
                labelAño.setVisible(false);
                jtfAño.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
                controlT=false;
                }
            if(longitud < 4 && controLabel!=true){
           
                if((car<'0' || car>'9')&& car!=KeyEvent.VK_BACK_SPACE && car!=KeyEvent.VK_ENTER){
                labelAño.setVisible(true);
                labelAño.setText("Solo se deben ingresar numeros");
                jtfAño.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
                controLabel=true;
                longitud2=jtfAño.getText().length();
                }
                else{
                labelAño.setVisible(false);
                jtfAño.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
                controLabel=false;
                }
            }
            else{
                evt.consume();    
                    if(longitud-1 < longitud2){
                    labelAño.setVisible(false);
                    jtfAño.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
                    controLabel=false;
                }
                
            }
    }//GEN-LAST:event_jtfAñoKeyTyped

    private void jtfcantidadKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfcantidadKeyTyped
            char car = evt.getKeyChar();
            longitud=jtfcantidad.getText().length();
               if(controlC!=false){
                labelCantidad.setVisible(false);
                jtfcantidad.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
                controlC=false;
                }
            if(longitud < 5 && controLabel!=true){
           
                if((car<'0' || car>'9')&& car!=KeyEvent.VK_BACK_SPACE && car!=KeyEvent.VK_ENTER){
                labelCantidad.setVisible(true);
                labelCantidad.setText("Solo se deben ingresar numeros");
                jtfcantidad.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
                controLabel=true;
                longitud2=jtfcantidad.getText().length();
                }
                else{
                labelCantidad.setVisible(false);
                jtfcantidad.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
                controLabel=false;
                }
            }
            else{
                evt.consume();    
                    if(longitud-1 < longitud2){
                    labelCantidad.setVisible(false);
                    jtfcantidad.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
                    controLabel=false;
                }
                
            }
            
    }//GEN-LAST:event_jtfcantidadKeyTyped

    private void jtfprecioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfprecioKeyTyped
        char car = evt.getKeyChar();
            longitud=jtfprecio.getText().length();
               
            if(controlP!=false){
                labelPrecio.setVisible(false);
                jtfprecio.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
                controlP=false;
                }
            if(longitud < 7 && controLabel!=true){
           
                if((car<'0' || car>'9')&& car!='.'&& car!=KeyEvent.VK_BACK_SPACE && car!=KeyEvent.VK_ENTER){
                labelPrecio.setVisible(true);
                labelPrecio.setText("Solo se deben ingresar numeros");
                jtfprecio.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
                controLabel=true;
                longitud2=jtfprecio.getText().length();
                }
                else{
                labelPrecio.setVisible(false);
                jtfprecio.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
                controLabel=false;
                }
            }
            else{
                evt.consume();    
                    if(longitud-1 < longitud2){
                    labelPrecio.setVisible(false);
                    jtfprecio.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
                    controLabel=false;
                }
                
            }
      
    }//GEN-LAST:event_jtfprecioKeyTyped

    private void jtfnombreFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtfnombreFocusGained
      
    }//GEN-LAST:event_jtfnombreFocusGained

    private void btnNuevaEditorialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevaEditorialActionPerformed
        int idp=oConsultaLibreria.getIdUltimaEditorial();
        jdNuevaEditorial ne= new jdNuevaEditorial(null, true);
        ne.setVisible(true);
        ne.setLocationRelativeTo(null);
        if(idp!=oConsultaLibreria.getIdUltimaEditorial()){
            jcbEditorial.removeAllItems();
            CargarComboEditoriales();
        jcbEditorial.setSelectedItem(oConsultaLibreria.getEditorialPorId(oConsultaLibreria.getIdUltimaEditorial()));
        }
       
    }//GEN-LAST:event_btnNuevaEditorialActionPerformed

    private void btnNuevoAutorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoAutorActionPerformed
        int idp=oConsultaLibreria.getIdUltimaAutor();
        jdNuevoAutor na= new jdNuevoAutor(null, true);
        na.setVisible(true);
        na.setLocationRelativeTo(null);
        if(idp!=oConsultaLibreria.getIdUltimaAutor()){
        jcbAutor.removeAllItems();
        CargarComboAutores();
        jcbAutor.setSelectedItem(oConsultaLibreria.getAutorPorId(oConsultaLibreria.getIdUltimaAutor()));
        }
    }//GEN-LAST:event_btnNuevoAutorActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        int idp=oConsultaLibreria.getIdUltimaGenero();
        jdNuevoGenero ng= new jdNuevoGenero(null, true);
        ng.setVisible(true);
        ng.setLocationRelativeTo(null);
        if(idp!=oConsultaLibreria.getIdUltimaGenero()){
        jcbGenero.removeAllItems();
        CargarComboGeneros();
        jcbGenero.setSelectedItem(oConsultaLibreria.getGeneroPorId(oConsultaLibreria.getIdUltimaGenero()));
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jtfnombreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfnombreKeyTyped
         char car = evt.getKeyChar();
         longitud=jtfnombre.getText().length();
            if(controlT!=false){
           labelTitulo.setVisible(false);
           jtfnombre.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
           controlT=false;
            }
            if(longitud >= 44)
            {evt.consume(); labelTitulo.setText("Longitud maxima 45 caracteres");labelTitulo.setVisible(true);}
            else{labelTitulo.setVisible(false);}
            
        
    }//GEN-LAST:event_jtfnombreKeyTyped

    private void jtfedicionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfedicionKeyTyped
        char car = evt.getKeyChar();
        longitud=jtfedicion.getText().length();
            if(controlE!=false){
           labelEdicion.setVisible(false);
           jtfedicion.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
           controlE=false;
            }
            
            if(longitud >= 44)
            {evt.consume(); labelEdicion.setText("Longitud maxima 45 caracteres");labelEdicion.setVisible(true);}
            else{labelEdicion.setVisible(false);}
        
    }//GEN-LAST:event_jtfedicionKeyTyped

    private void btnAProvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAProvActionPerformed
        if(controlV!=false){
           labelProveedor.setVisible(false);
           controlV=false;
        }
        proveedor p= (proveedor) jcbProveedor.getSelectedItem();
        if(bandera==0){
        if(comprobarrepetidos(p.getIdProveedor())== false){
            LineaProveedorLibro lp= new LineaProveedorLibro(0, idlm, p.getIdProveedor());
            tbLineaProveedor.add(lp);
            cargarTablaPL();}
        }
        else{
            if(comprobarrepetidos(p.getIdProveedor())== false){
            LineaProveedorLibro lp= new LineaProveedorLibro(0, idlm, p.getIdProveedor());
            oConsultaCompra.agregarLp(lp);
            tbIdp.add(oConsultaCompra.getIdUltimaLP());
            cargarTablaplbd(oConsultaCompra.getAllPlId(idlm));
        }
        }
    }//GEN-LAST:event_btnAProvActionPerformed

    private void btnQProvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQProvActionPerformed
        v=jtableProveedores.getSelectedRow();//n° fila selccionada
        indiceModelo = jtableProveedores.convertRowIndexToModel (v);//convierte el indice de la vista en el indice del modelo 
        if(bandera==0){
            int respuesta=JOptionPane.showConfirmDialog(null, "¿Realmente desea quitar el proveedor?","Advertencia", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if(respuesta == 0)
                {
                    tbLineaProveedor.remove(v);
                    cargarTablaPL();
                }
        }
        else{
            int respuesta=JOptionPane.showConfirmDialog(null, "¿Realmente desea quitar el proveedor?","Advertencia", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if(respuesta == 0)
                {
                  oConsultaCompra.eliminaLp(getIdProv(indiceModelo));
                  tbLineaProveedor.clear();
                  cargarTablaplbd(oConsultaCompra.getAllPlId(idlm));
                  
                }
        }
    }//GEN-LAST:event_btnQProvActionPerformed

    private void jtfCostoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfCostoKeyTyped
      char car = evt.getKeyChar();
            longitud=jtfCosto.getText().length();
               
            if(controlCO!=false){
                labelCosto.setVisible(false);
                jtfCosto.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
                controlCO=false;
                }
            if(longitud < 7 && controLabel!=true){
           
                if((car<'0' || car>'9')&& car!='.'&& car!=KeyEvent.VK_BACK_SPACE && car!=KeyEvent.VK_ENTER){
                labelCosto.setVisible(true);
                labelCosto.setText("Solo se deben ingresar numeros");
                jtfCosto.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
                controLabel=true;
                longitud2=jtfCosto.getText().length();
                }
                else{
                labelCosto.setVisible(false);
                jtfCosto.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
                controLabel=false;
                }
            }
            else{
                evt.consume();    
                    if(longitud-1 < longitud2){
                    labelCosto.setVisible(false);
                    jtfCosto.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
                    controLabel=false;
                }
                
            }
        
        
    }//GEN-LAST:event_jtfCostoKeyTyped

    private void btnDeshabActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeshabActionPerformed
        Libro pro=capturarCampos();
        if(pro.getEstadoLibro()== true)
        {
            int respuesta=JOptionPane.showConfirmDialog(null, "¿Realmente desea deshabilitar este libro?","Advertencia", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if(respuesta == 0)
            {
            pro.setEstadoLibro(false);
            oConsultaLibreria.deshabilitarLibro(pro);
            cargarTabla(oConsultaLibreria.getAllLibro());
            btnDeshab.setEnabled(false);
            btnDeshab.setText("Habilitar");
            bandera=0;
            id=0;
            EstadoLibro=false;
            jtableLibros.setEnabled(true);
            limpiarCampos();
            jXTaskPane2.setTitle("Nuevo Libro");
            jXTaskPane2.setCollapsed(true);
            jtfbuscarLibro.setEnabled(true);
            jtfbuscarLibro.setEditable(true);
            }
        }else{
            int respuesta=JOptionPane.showConfirmDialog(null, "¿Realmente desea habilitar este libro?","Advertencia", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if(respuesta == 0)
            {
            pro.setEstadoLibro(true);
            oConsultaLibreria.deshabilitarLibro(pro);
            cargarTabla(oConsultaLibreria.getAllLibro());
            btnDeshab.setEnabled(false);
            btnDeshab.setText("Deshabilitar");
            bandera=0;
            id=0;
            EstadoLibro=false;
            jtableLibros.setEnabled(true);
            limpiarCampos();
            jXTaskPane2.setTitle("Nuevo Libro");
            jXTaskPane2.setCollapsed(true);
            jtfbuscarLibro.setEnabled(true);
            jtfbuscarLibro.setEditable(true);
        }
        }
        
    }//GEN-LAST:event_btnDeshabActionPerformed

    private void jcbEditorialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbEditorialActionPerformed
        if(jcbEditorial.getSelectedIndex()!= 0)
        {labelEditorial.setVisible(false);controlCE=false;}    
    }//GEN-LAST:event_jcbEditorialActionPerformed

    private void jcbAutorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbAutorActionPerformed
        if(jcbAutor.getSelectedIndex()!= 0)
        {labelAutor.setVisible(false);controlCA=false;}    
    }//GEN-LAST:event_jcbAutorActionPerformed

    private void jcbGeneroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbGeneroActionPerformed
       if(jcbGenero.getSelectedIndex()!= 0)
        {labelGenero.setVisible(false);controlCG=false;}    
    }//GEN-LAST:event_jcbGeneroActionPerformed

    private void jtfnombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtfnombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtfnombreActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAProv;
    private javax.swing.JButton btnCancelar1;
    private javax.swing.JButton btnDeshab;
    private javax.swing.JButton btnNuevaEditorial;
    private javax.swing.JButton btnNuevoAutor;
    private javax.swing.JButton btnQProv;
    private javax.swing.JButton btnguardar1;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane4;
    private org.jdesktop.swingx.JXTaskPane jXTaskPane2;
    private org.jdesktop.swingx.JXComboBox jcbAutor;
    private org.jdesktop.swingx.JXComboBox jcbEditorial;
    private org.jdesktop.swingx.JXComboBox jcbGenero;
    private org.jdesktop.swingx.JXComboBox jcbProveedor;
    private javax.swing.JTable jtableLibros;
    private javax.swing.JTable jtableProveedores;
    private javax.swing.JTextField jtfAño;
    private javax.swing.JTextField jtfCosto;
    private javax.swing.JTextField jtfbuscarLibro;
    private javax.swing.JTextField jtfcantidad;
    private javax.swing.JTextField jtfedicion;
    private javax.swing.JTextField jtfnombre;
    private javax.swing.JTextField jtfprecio;
    private javax.swing.JLabel labelAutor;
    private javax.swing.JLabel labelAño;
    private javax.swing.JLabel labelCantidad;
    private javax.swing.JLabel labelCosto;
    private javax.swing.JLabel labelEdicion;
    private javax.swing.JLabel labelEditorial;
    private javax.swing.JLabel labelGenero;
    private javax.swing.JLabel labelPrecio;
    private javax.swing.JLabel labelProveedor;
    private javax.swing.JLabel labelTitulo;
    // End of variables declaration//GEN-END:variables
}
