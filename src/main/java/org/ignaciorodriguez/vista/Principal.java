package org.ignaciorodriguez.vista;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.table.DefaultTableModel;
import javax.swing.AbstractAction;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.ActionMap;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import org.ignaciorodriguez.modelo.Conexion;
import org.ignaciorodriguez.modelo.Consultas;
import org.ignaciorodriguez.modelo.Usuario;
import org.ignaciorodriguez.repository.ClienteRepository;
import org.ignaciorodriguez.repository.MuestraRepository;
import org.ignaciorodriguez.repository.ResultadoRepository;
import org.ignaciorodriguez.repository.UsuarioRepository;

public class Principal extends JFrame {

    Consultas consultas = Consultas.getInstancia();
    UsuarioRepository usuarioRepository = new UsuarioRepository();
    MuestraRepository muestraRepository = new MuestraRepository();
    public DefaultTableModel modeloTabla = muestraRepository.llenarTabla();
    int fila2;
    public static int id;
    String rutaRespaldo = consultas.recuperarRutas("Respaldo");
    boolean ip = true, ph, anulado, borrar;
    public boolean actualizar = true;
    DefaultTableCellRenderer defaultRender;
    Map<String, String> map = muestraRepository.recuperarIdentificaciones();
    DialogoBuscar n;
    int actualizacion = -1;
    ClienteRepository clienteRepository = new ClienteRepository();
    ResultadoRepository resultadoRepository = new ResultadoRepository();
    private static final Logger logger = Logger.getLogger(Principal.class.getName());

    public Principal() {
        initComponents();
        actualizacion = consultas.consultarActualizacion();
        tablaDatos.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent mouseEvent) {
                JTable table = (JTable) mouseEvent.getSource();
                Point point = mouseEvent.getPoint();
                int row = table.rowAtPoint(point);
                if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {
                    GenerarReporte.doClick();
                }
            }
        });
        panelColores.setVisible(false);
        defaultRender = (DefaultTableCellRenderer) tablaDatos.getDefaultRenderer(Object.class);
        this.setLocationRelativeTo(null);
        this.setExtendedState(Principal.MAXIMIZED_BOTH);
        tablaDatos.setFocusable(false);
        tablaDatos.setRowSelectionAllowed(true);
        File archivo = null;
        try {
            archivo = new File(getClass().getResource("/ip.txt").toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        BufferedReader br;
        String aux = null;
        if (actualizacion == Consultas.ACTUALIZAR) {
            etiquetaActualizacion.setVisible(true);
        } else if (actualizacion == Consultas.ERROR_ACTUALIZAR) {
            etiquetaActualizacion.setText("NO SE PUDO COMPROBAR EL ESTADO DE LA ACTUALIZACIÓN. REVISE LA CONEXIÓN A INTERNET.");
            etiquetaActualizacion.setVisible(true);
        }
        try {
            br = new BufferedReader(new FileReader(archivo));
            aux = br.readLine();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (aux.equals("localhost")) {
            ip = false;
        }
        if (ip) {
            jMenu2.setVisible(false);
            itemRespaldo.setVisible(false);
        }
        ImageIcon icon = new ImageIcon(getClass().getResource("/imagenes/icono.png"));
        this.setIconImage(icon.getImage());
        tablaDatos.setAutoCreateRowSorter(true);
        setLocationRelativeTo(null);
        radioID.setSelected(true);
        labelBuscar.setText("ID:");
        buttonGroup1.add(radioFecha);
        buttonGroup1.add(radioID);
        buttonGroup1.add(radioProcedencia);
        buttonGroup1.add(radioSolicitante);
        class actualizacion extends TimerTask {

            public void run() {

                actualizacion = consultas.consultarActualizacion();
                if (actualizacion == Consultas.ACTUALIZAR) {
                    etiquetaActualizacion.setVisible(true);
                    etiquetaActualizacion.setText("ULTIMA VERSIÓN NO INSTALADA");
                } else if (actualizacion == Consultas.ERROR_ACTUALIZAR) {
                    etiquetaActualizacion.setText("NO SE PUDO COMPROBAR EL ESTADO DE LA ACTUALIZACIÓN. REVISE LA CONEXIÓN A INTERNET.");
                    etiquetaActualizacion.setVisible(true);
                } else if (actualizacion == Consultas.NO_ACTUALIZAR) {
                    etiquetaActualizacion.setVisible(false);
                }
            }
        }
        Timer timer = new Timer();
        timer.schedule(new actualizacion(), 0, 60000);
    }

    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        menuPop = new javax.swing.JPopupMenu();
        itemAgregar = new javax.swing.JMenuItem();
        itemModificar = new javax.swing.JMenuItem();
        itemEliminar = new javax.swing.JMenuItem();
        popUpTabla = new javax.swing.JPopupMenu();
        GenerarReporte = new javax.swing.JMenuItem();
        itemBorrar = new javax.swing.JMenuItem();
        itemRecuperar = new javax.swing.JMenuItem();
        itemEditar = new javax.swing.JMenuItem();
        itemEntregar = new javax.swing.JMenuItem();
        itemAgregarPh = new javax.swing.JMenuItem();
        itemNotas = new javax.swing.JMenuItem();
        itemEnProceso = new javax.swing.JMenuItem();
        itemEditarResultados = new javax.swing.JMenuItem();
        jDialog1 = new javax.swing.JDialog();
        jPanel3 = new javax.swing.JPanel();
        boton = new javax.swing.JButton();
        cajaBuscar = new javax.swing.JTextField();
        labelBuscar = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        radioSolicitante = new javax.swing.JRadioButton();
        radioFecha = new javax.swing.JRadioButton();
        radioProcedencia = new javax.swing.JRadioButton();
        jLabel1 = new javax.swing.JLabel();
        radioID = new javax.swing.JRadioButton();
        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaDatos = new javax.swing.JTable(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
            private static final long serialVersionUID = 1;

            public String getToolTipText(MouseEvent e) {
                String tip = null;
                java.awt.Point p = e.getPoint();
                int rowIndex = rowAtPoint(p);
                try {
                    tip = map.get(tablaDatos.getValueAt(rowIndex, 0));
                } catch (RuntimeException e1) {
                    logger.severe("Error, " + e1);
                }
                return tip;
            }
        };
        etiquetaInicial = new javax.swing.JLabel();
        botonBuscar = new javax.swing.JButton();
        botonBuscar1 = new javax.swing.JButton();
        panelColores = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        etiquetaActualizacion = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        botonAgregar = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        menuVencimientos = new javax.swing.JMenu();
        itemVencimientos = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        menuClientes = new javax.swing.JMenu();
        itemVerClientes = new javax.swing.JMenuItem();
        itemNuevoCliente = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        menuInforme = new javax.swing.JMenu();
        itemInforme = new javax.swing.JMenuItem();
        menuEntregas = new javax.swing.JMenu();
        itemVerEntregas = new javax.swing.JMenuItem();
        jMenu7 = new javax.swing.JMenu();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        menuitemCopia = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        itemRespaldo = new javax.swing.JMenuItem();
        itemReporte = new javax.swing.JMenuItem();
        menuEntregas1 = new javax.swing.JMenu();
        itemVerEntregas1 = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        itemVerUsuarios = new javax.swing.JMenuItem();
        jMenu5 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenu6 = new javax.swing.JMenu();
        jMenuItem4 = new javax.swing.JMenuItem();
        menuPrueba = new javax.swing.JMenu();
        itemPrueba = new javax.swing.JMenuItem();
        itemBorrarPrueba = new javax.swing.JMenuItem();
        menuExcel = new javax.swing.JMenu();
        jMenuItem7 = new javax.swing.JMenuItem();

        jMenu1.setText("jMenu1");

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1, 0));
        jMenuItem1.setText("jMenuItem1");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        itemAgregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/agregar-usuario.png"))); // NOI18N
        itemAgregar.setText("Agregar usuario");
        itemAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemAgregarActionPerformed(evt);
            }
        });
        menuPop.add(itemAgregar);

        itemModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/modificar usuario.png"))); // NOI18N
        itemModificar.setText("Modificar usuario");
        itemModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemModificarActionPerformed(evt);
            }
        });
        menuPop.add(itemModificar);

        itemEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/eliminar usuario.png"))); // NOI18N
        itemEliminar.setText("Eliminar usuario");
        itemEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemEliminarActionPerformed(evt);
            }
        });
        menuPop.add(itemEliminar);

        if(ph){
            itemAgregar.setVisible(ph);
        }

        GenerarReporte.setText("jMenuItem1");
        GenerarReporte.setText("Generar Reporte");
        GenerarReporte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GenerarReporteActionPerformed(evt);
            }
        });
        popUpTabla.add(GenerarReporte);

        itemBorrar.setText("Anular análisis");
        itemBorrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemBorrarActionPerformed(evt);
            }
        });
        popUpTabla.add(itemBorrar);

        itemRecuperar.setText("Recuperar análisis");
        itemRecuperar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemRecuperarActionPerformed(evt);
            }
        });
        popUpTabla.add(itemRecuperar);

        itemEditar.setText("jMenuItem1");
        itemEditar.setText("Editar Muestra");
        itemEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemEditarActionPerformed(evt);
            }
        });
        popUpTabla.add(itemEditar);

        itemEntregar.setText("jMenuItem2");
        itemEntregar.setText("Entregar análisis");
        itemEntregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemEntregarActionPerformed(evt);
            }
        });
        popUpTabla.add(itemEntregar);

        itemAgregarPh.setText("Agregar pH y cloro");
        itemAgregarPh.setVisible(false);
        itemAgregarPh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemAgregarPhActionPerformed(evt);
            }
        });
        popUpTabla.add(itemAgregarPh);

        itemNotas.setText("Notas");
        itemNotas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemNotasActionPerformed(evt);
            }
        });
        popUpTabla.add(itemNotas);

        itemEnProceso.setText("Ver informe de analisis en proceso");
        itemEnProceso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemEnProcesoActionPerformed(evt);
            }
        });
        popUpTabla.add(itemEnProceso);

        itemEditarResultados.setText("Editar resultados de muestra");
        itemEditarResultados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemEditarResultadosActionPerformed(evt);
            }
        });
        popUpTabla.add(itemEditarResultados);

        jPanel3.setMaximumSize(new java.awt.Dimension(385, 172));
        jPanel3.setMinimumSize(new java.awt.Dimension(385, 172));
        jPanel3.setPreferredSize(new java.awt.Dimension(385, 172));
        jPanel3.setLayout(new java.awt.GridBagLayout());

        boton.setText("Buscar");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets = new java.awt.Insets(20, 0, 0, 0);
        jPanel3.add(boton, gridBagConstraints);

        cajaBuscar.setMaximumSize(new java.awt.Dimension(150, 22));
        cajaBuscar.setMinimumSize(new java.awt.Dimension(150, 22));
        cajaBuscar.setName(""); // NOI18N
        cajaBuscar.setPreferredSize(new java.awt.Dimension(150, 22));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
        jPanel3.add(cajaBuscar, gridBagConstraints);

        labelBuscar.setText("jLabel2");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 0, 0);
        jPanel3.add(labelBuscar, gridBagConstraints);

        jPanel4.setLayout(new java.awt.GridBagLayout());

        radioSolicitante.setText("Solicitante");
        radioSolicitante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioSolicitanteActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 0, 5);
        jPanel4.add(radioSolicitante, gridBagConstraints);

        radioFecha.setText("Fecha");
        radioFecha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioFechaActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 0, 5);
        jPanel4.add(radioFecha, gridBagConstraints);

        radioProcedencia.setText("Procedencia");
        radioProcedencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioProcedenciaActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 0, 5);
        jPanel4.add(radioProcedencia, gridBagConstraints);

        jLabel1.setText("Buscar por:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 0, 5);
        jPanel4.add(jLabel1, gridBagConstraints);

        radioID.setText("ID");
        radioID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioIDActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 0, 5);
        jPanel4.add(radioID, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        jPanel3.add(jPanel4, gridBagConstraints);

        javax.swing.GroupLayout jDialog1Layout = new javax.swing.GroupLayout(jDialog1.getContentPane());
        jDialog1.getContentPane().setLayout(jDialog1Layout);
        jDialog1Layout.setHorizontalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );
        jDialog1Layout.setVerticalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
                formWindowGainedFocus(evt);
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
            }
        });
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jPanel1.setLayout(new java.awt.GridBagLayout());

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPane1.setToolTipText("");
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        tablaDatos.setModel(modeloTabla);
        tablaDatos.setSelectionBackground(new java.awt.Color(212, 236, 108));
        tablaDatos.getTableHeader().setReorderingAllowed(false);
        tablaDatos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tablaDatosMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(tablaDatos);
        tablaDatos.setComponentPopupMenu(popUpTabla);
        int anchos[] = {24, 288, 208, 97, 63, 63, 75, 69, 60 ,52, 219};
        for (int i = 0; i < 11; i++) {
            tablaDatos.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
        jPanel1.add(jScrollPane1, gridBagConstraints);

        etiquetaInicial.setBackground(new java.awt.Color(212, 236, 108));
        etiquetaInicial.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        etiquetaInicial.setForeground(new java.awt.Color(255, 255, 255));
        etiquetaInicial.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/usuario (4).png"))); // NOI18N
        etiquetaInicial.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                etiquetaInicialMouseEntered(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 20);
        jPanel1.add(etiquetaInicial, gridBagConstraints);

        botonBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/lupa.png"))); // NOI18N
        botonBuscar.setText("Buscar");
        botonBuscar.setMargin(new java.awt.Insets(2, 2, 2, 2));
        botonBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonBuscarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        jPanel1.add(botonBuscar, gridBagConstraints);

        botonBuscar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/x.png"))); // NOI18N
        botonBuscar1.setText("Limpiar busqueda");
        botonBuscar1.setMargin(new java.awt.Insets(2, 2, 2, 2));
        botonBuscar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonBuscar1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 110, 0, 0);
        jPanel1.add(botonBuscar1, gridBagConstraints);

        panelColores.setOpaque(false);
        panelColores.setLayout(new java.awt.GridBagLayout());

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("<html><span style=\"color: rgb(135, 252, 126); font-size: 3em; vertical-align: super;\">●</span></html>");
        jLabel3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel3.setPreferredSize(new java.awt.Dimension(22, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        panelColores.add(jLabel3, gridBagConstraints);

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("<html><span style=\"color: rgb(128, 126, 252); font-size: 3em; vertical-align: super;\">●</span></html>");
        jLabel4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel4.setPreferredSize(new java.awt.Dimension(22, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        panelColores.add(jLabel4, gridBagConstraints);

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("<html><span style=\"color: rgb(252, 126, 126); font-size: 3em; vertical-align: super;\">●</span></html>");
        jLabel5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel5.setPreferredSize(new java.awt.Dimension(22, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        panelColores.add(jLabel5, gridBagConstraints);

        jLabel6.setText("Entregado");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        panelColores.add(jLabel6, gridBagConstraints);

        jLabel7.setText("En tabla");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        panelColores.add(jLabel7, gridBagConstraints);

        jLabel8.setText("Anulado");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        panelColores.add(jLabel8, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jPanel1.add(panelColores, gridBagConstraints);

        etiquetaActualizacion.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        etiquetaActualizacion.setForeground(new java.awt.Color(255, 0, 51));
        etiquetaActualizacion.setText("ULTIMA VERSIÓN NO INSTALADA");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(50, 0, 0, 0);
        jPanel1.add(etiquetaActualizacion, gridBagConstraints);
        etiquetaActualizacion.setVisible(false);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(jPanel1, gridBagConstraints);
        KeyListener apretar = new KeyListener(){
            @Override
            public void keyPressed(KeyEvent e){
                if(e.getKeyCode() == KeyEvent.VK_F1){
                    System.out.println("Aprete f1");
                }
            }
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == '\177') {
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_DELETE) {
                }
            }
        };

        jPanel1.addKeyListener(apretar);

        jPanel2.setAlignmentX(0.0F);
        jPanel2.setAlignmentY(0.0F);
        jPanel2.setLayout(new java.awt.GridBagLayout());

        botonAgregar.setMnemonic('e');
        botonAgregar.setText("Agregar análisis (F1)");
        botonAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonAgregarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(0, 6, 30, 6);
        jPanel2.add(botonAgregar, gridBagConstraints);
        InputMap im = jPanel1.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0, false), "Apretar");

        ActionMap ap = jPanel1.getActionMap();
        ap.put("Apretar", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e){
                botonAgregar.doClick();
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 0.1;
        getContentPane().add(jPanel2, gridBagConstraints);

        menuVencimientos.setText("Vencimientos");

        itemVencimientos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/vencimiento.png"))); // NOI18N
        itemVencimientos.setText("Ver vencimientos");
        itemVencimientos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemVencimientosActionPerformed(evt);
            }
        });
        menuVencimientos.add(itemVencimientos);

        jMenuItem5.setText("Informe de vencimientos");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        menuVencimientos.add(jMenuItem5);

        jMenuBar1.add(menuVencimientos);

        menuClientes.setText("Clientes");

        itemVerClientes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/clientes.png"))); // NOI18N
        itemVerClientes.setText("Ver clientes");
        itemVerClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemVerClientesActionPerformed(evt);
            }
        });
        menuClientes.add(itemVerClientes);

        itemNuevoCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/agregar-cliente.png"))); // NOI18N
        itemNuevoCliente.setText("Nuevo cliente");
        itemNuevoCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemNuevoClienteActionPerformed(evt);
            }
        });
        menuClientes.add(itemNuevoCliente);

        jMenuItem2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/clientes-borrados.png"))); // NOI18N
        jMenuItem2.setText("Ver clientes eliminados");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        menuClientes.add(jMenuItem2);

        jMenuBar1.add(menuClientes);

        menuInforme.setMnemonic('c');
        menuInforme.setText("Informe");

        itemInforme.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F5, 0));
        itemInforme.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/informe.png"))); // NOI18N
        itemInforme.setText("Informe para muestras");
        itemInforme.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemInformeActionPerformed(evt);
            }
        });
        menuInforme.add(itemInforme);

        jMenuBar1.add(menuInforme);

        menuEntregas.setText("Entregas");

        itemVerEntregas.setText("Ver entregas");
        itemVerEntregas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemVerEntregasActionPerformed(evt);
            }
        });
        menuEntregas.add(itemVerEntregas);

        jMenuBar1.add(menuEntregas);

        jMenu7.setText("Ganancias");

        jMenuItem6.setText("Ver ganancias");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu7.add(jMenuItem6);

        jMenuBar1.add(jMenu7);

        jMenu2.setText("Copia de seguridad");

        menuitemCopia.setText("Hacer copia");
        menuitemCopia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuitemCopiaActionPerformed(evt);
            }
        });
        jMenu2.add(menuitemCopia);

        jMenuBar1.add(jMenu2);

        jMenu3.setText("Rutas");

        itemRespaldo.setText("Elegir carpeta para respaldo");
        itemRespaldo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemRespaldoActionPerformed(evt);
            }
        });
        jMenu3.add(itemRespaldo);

        itemReporte.setText("Elegir carpeta para guardar reportes");
        itemReporte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemReporteActionPerformed(evt);
            }
        });
        jMenu3.add(itemReporte);

        jMenuBar1.add(jMenu3);

        menuEntregas1.setText("Recuperar anulados");

        itemVerEntregas1.setText("Ver anulados");
        itemVerEntregas1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemVerEntregas1ActionPerformed(evt);
            }
        });
        menuEntregas1.add(itemVerEntregas1);

        jMenuBar1.add(menuEntregas1);

        jMenu4.setText("Usuarios");

        itemVerUsuarios.setText("Ver usuarios");
        itemVerUsuarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemVerUsuariosActionPerformed(evt);
            }
        });
        jMenu4.add(itemVerUsuarios);

        jMenuBar1.add(jMenu4);

        jMenu5.setText("Enviar error");

        jMenuItem3.setText("Enviar por email");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem3);

        jMenuBar1.add(jMenu5);

        jMenu6.setText("Actualización");

        jMenuItem4.setText("Buscar Actualización");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem4);

        jMenuBar1.add(jMenu6);

        menuPrueba.setText("Prueba");

        itemPrueba.setText("PRUEBA");
        itemPrueba.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemPruebaActionPerformed(evt);
            }
        });
        menuPrueba.add(itemPrueba);

        itemBorrarPrueba.setText("Resetear prueba");
        itemBorrarPrueba.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemBorrarPruebaActionPerformed(evt);
            }
        });
        menuPrueba.add(itemBorrarPrueba);

        jMenuBar1.add(menuPrueba);

        menuExcel.setText("Informe en Excel");

        jMenuItem7.setText("Generar informe");
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        menuExcel.add(jMenuItem7);

        jMenuBar1.add(menuExcel);

        setJMenuBar(jMenuBar1);

        pack();
    }

    private void botonAgregarActionPerformed(java.awt.event.ActionEvent evt) {
        Procedencia p = new Procedencia(this, true);
        p.setVisible(true);
    }

    private void itemVencimientosActionPerformed(java.awt.event.ActionEvent evt) {
        Vencimiento vencimiento = new Vencimiento(this, true);
        vencimiento.setVisible(true);
    }

    private void itemAgregarActionPerformed(java.awt.event.ActionEvent evt) {
        CrearUsuario crear = new CrearUsuario(this, true);
        crear.setVisible(true);
    }

    private void etiquetaInicialMouseEntered(java.awt.event.MouseEvent evt) {
        Cursor cursor = new Cursor(Cursor.HAND_CURSOR);
        etiquetaInicial.setCursor(cursor);
        etiquetaInicial.setComponentPopupMenu(menuPop);

    }

    private void itemEliminarActionPerformed(java.awt.event.ActionEvent evt) {
        EliminarUsuario eliminar = new EliminarUsuario(this, true);
        eliminar.setVisible(true);
    }

    private void itemModificarActionPerformed(java.awt.event.ActionEvent evt) {
        ModificarUsuario modificar = new ModificarUsuario(this, true);
        modificar.setVisible(true);
    }

    private void botonBuscarActionPerformed(java.awt.event.ActionEvent evt) {
        defaultRender = (DefaultTableCellRenderer) tablaDatos.getDefaultRenderer(Object.class);
        actualizar = false;
        
        n = new DialogoBuscar(this, true);
        n.setVisible(true);
        if (n.parametro != "-1" && n.valor != "-1") {
            modeloTabla = muestraRepository.buscarTabla(n.parametro, n.valor);
            if (modeloTabla.getRowCount() != 0) {
                panelColores.setVisible(true);
                int tabla = modeloTabla.getColumnCount() - 1;
                tablaDatos.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
                    @Override
                    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                        final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                        if (isSelected) {
                            System.out.println("tabla = " + tabla);
                            System.out.println("modeloTabla.getColumnCount() = " + modeloTabla.getColumnCount());
                            c.setBackground(table.getModel().getValueAt(row, tabla).toString().contains("en tabla".toUpperCase()) ? Color.LIGHT_GRAY : table.getModel().getValueAt(row, tabla).toString().contains("entregado".toUpperCase()) ? new Color(182, 181, 255) : new Color(248, 188, 188));
                        } else {
                            System.out.println("modeloTabla.getColumnCount() = " + modeloTabla.getColumnCount());
                            c.setBackground(table.getModel().getValueAt(row, tabla).toString().contains("en tabla".toUpperCase()) ? Color.white : table.getModel().getValueAt(row, tabla).toString().contains("entregado".toUpperCase()) ? new Color(128, 126, 252) : new Color(252, 126, 126));
                        }
                        return c;
                    }
                });
                int[] anchos = {24, 288, 208, 97, 63, 63, 75, 69, 60, 52, 219};
                for (int i = 0; i < 11; i++) {
                    tablaDatos.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
                }
            }
        }
    }

    private void itemVerClientesActionPerformed(java.awt.event.ActionEvent evt) {
        VerClientes vc = new VerClientes(this, true, false);
        vc.setVisible(true);
    }

    private void itemNuevoClienteActionPerformed(java.awt.event.ActionEvent evt) {
        AgregarProcedencia ap = new AgregarProcedencia(this, true, false, -1, false);
        ap.setVisible(true);
    }

    private void formWindowGainedFocus(java.awt.event.WindowEvent evt) {
        if (actualizar) {
            if (!resultadoRepository.verificarVacioMuestras()) {
                modeloTabla();
            }
        } else {
            if (!Objects.equals(n.parametro, "-1") && !Objects.equals(n.valor, "-1")) {
                modeloTabla = muestraRepository.buscarTabla(n.parametro, n.valor);
                if (modeloTabla.getRowCount() != 0) {
                    panelColores.setVisible(true);
                    int tabla = modeloTabla.getColumnCount() - 1;
                    tablaDatos.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
                        @Override
                        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                            final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                            c.setBackground(table.getModel().getValueAt(row, tabla).toString().contains("en tabla".toUpperCase()) ? new Color(135, 252, 126) : table.getModel().getValueAt(row, tabla).toString().contains("entregado".toUpperCase()) ? new Color(128, 126, 252) : new Color(252, 126, 126));
                            return c;
                        }
                    });
                    tablaDatos.getColumnModel().removeColumn(tablaDatos.getColumnModel().getColumn(tablaDatos.getColumnCount() - 1));
                    tablaDatos.getColumnModel().removeColumn(tablaDatos.getColumnModel().getColumn(tablaDatos.getColumnCount() - 1));
                    int[] anchos = {24, 288, 208, 97, 63, 63, 75, 69, 60, 52, 219};
                    for (int i = 0; i < 11; i++) {
                        tablaDatos.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
                    }
                }
            }
        }
    }

    private void itemInformeActionPerformed(java.awt.event.ActionEvent evt) {
        mostrarInformes();
    }

    private void itemVerEntregasActionPerformed(java.awt.event.ActionEvent evt) {
        TablaEntregas te = new TablaEntregas(this, true);
        te.setVisible(true);
    }

    private void tablaDatosMousePressed(java.awt.event.MouseEvent evt) {
        ph = false;
        itemAgregarPh.setVisible(ph);
        anulado = false;
        itemRecuperar.setVisible(anulado);
        borrar = true;
        itemBorrar.setVisible(borrar);
        itemEditarResultados.setVisible(borrar);
        GenerarReporte.setVisible(borrar);
        itemEntregar.setVisible(borrar);
        fila2 = tablaDatos.rowAtPoint(evt.getPoint());
        if (!tablaDatos.getValueAt(fila2, 10).toString().endsWith("balnearios")) {
            if (tablaDatos.getValueAt(fila2, 10).toString().contains("Microbiológico de agua")) {
                ph = true;
                itemAgregarPh.setVisible(ph);
            }
        }
        if (tablaDatos.getValueAt(fila2, 10).toString().contains("ANULADO")) {
            anulado = true;
            itemRecuperar.setVisible(anulado);
            borrar = false;
            itemBorrar.setVisible(borrar);
            itemEditarResultados.setVisible(borrar);
            GenerarReporte.setVisible(borrar);
            itemEntregar.setVisible(borrar);
        }
        anulado = false;
        borrar = true;
        ph = false;
    }

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {
        botonAgregar.doClick();
    }

    private void formWindowClosing(java.awt.event.WindowEvent evt) {
        if (!ip) {
            int guardar = JOptionPane.showOptionDialog(this, "¿Desea hacer una copia de seguridad?", "Copia de seguridad", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, null, null);
            if (guardar == JOptionPane.YES_OPTION) {
                crearBackup(rutaRespaldo);
                JOptionPane.showMessageDialog(null, "Copia guardada con exito.");
                System.exit(0);
            } else if (guardar == JOptionPane.NO_OPTION) {
                System.exit(0);
            } else if (guardar == -1) {
                setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            }
        }
    }

    private void itemReporteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemReporteActionPerformed
        JFileChooser elegir = new JFileChooser(consultas.recuperarRutas("Reportes"));
        elegir.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        elegir.setDialogTitle("Elegir ruta para guardar informes");
        int respuesta = elegir.showOpenDialog(this);
        if (respuesta == JFileChooser.APPROVE_OPTION) {
            File archivoElegido = elegir.getSelectedFile();
            if (consultas.guardarRutas("Reportes", archivoElegido.getPath())) {
                JOptionPane.showMessageDialog(null, "Ruta guardada con éxito.");
            } else {
                JOptionPane.showMessageDialog(null, "Error al guardar la ruta.");

            }
        }
    }

    private void itemRespaldoActionPerformed(java.awt.event.ActionEvent evt) {
        JFileChooser elegir = new JFileChooser(consultas.recuperarRutas("Respaldo"));
        elegir.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        elegir.setDialogTitle("Elegir ruta para guardar respaldo");
        int respuesta = elegir.showOpenDialog(this);
        if (respuesta == JFileChooser.APPROVE_OPTION) {
            File archivoElegido = elegir.getSelectedFile();
            if (consultas.guardarRutas("Respaldo", archivoElegido.getPath())) {
                JOptionPane.showMessageDialog(null, "Ruta guardada con éxito.");
            } else {
                JOptionPane.showMessageDialog(null, "Error al guardar la ruta.");

            }
        }
    }

    private void menuitemCopiaActionPerformed(java.awt.event.ActionEvent evt) {
        File file = new File(rutaRespaldo);
        if (crearBackup(rutaRespaldo)) {
            JOptionPane.showMessageDialog(null, "Respaldo guardado con éxito.");
        } else {
            JOptionPane.showMessageDialog(null, "Error al guardar el respaldo");
        }
    }

    private void itemVerEntregas1ActionPerformed(java.awt.event.ActionEvent evt) {
        TablaBorrados tb = new TablaBorrados(this, true);
        tb.setVisible(true);
    }

    private void itemAgregarPhActionPerformed(java.awt.event.ActionEvent evt) {
        int id = Integer.parseInt((String) tablaDatos.getValueAt(fila2, 0));
        String tipo = (String) tablaDatos.getValueAt(fila2, 10);
        AgregarPh aph = new AgregarPh(this, true, id, tipo);
        aph.setVisible(true);
    }

    private void itemEditarResultadosActionPerformed(java.awt.event.ActionEvent evt) {
        int id = Integer.parseInt((String) tablaDatos.getValueAt(fila2, 0));
        String tipo = String.valueOf(tablaDatos.getValueAt(fila2, 10));
        String tipo2 = "";
        switch (tipo) {
            case "Microbiológico de agua código":
                tipo2 = " MB Agua ";
                break;
            case "Microbiológico de agua bidón":
                tipo2 = " MB Agua ";
                break;
            case "Microbiológico de agua balnearios":
                tipo2 = " MB Agua balnearios ";
                break;
            case "Microbiológico de agua COFES":
                tipo2 = " MB Agua COFES ";
                break;
            case "Microbiológico de agua de recreación":
                tipo2 = " MB Agua de recreación ";
                break;
            case "Microbiológico de alimentos":
                tipo2 = " MB Alimentos ";
                break;
            case "Microbiológico de chocolates Del Turista":
                tipo2 = " MB Chocolates ";
                break;
            case "Efluentes":
                tipo2 = " Efluentes ";
                break;
            case "Efluentes infiltración":
                tipo2 = " Efluentes infiltración ";
                break;
            case "Físico químico de agua básico":
                tipo2 = " FQ agua básico ";
                break;
            case "Físico químico de agua completo":
                tipo2 = " FQ agua ";
                break;
            case "Físico químico de alimentos":
                tipo2 = " FQ alimentos ";
                break;
            case "Físico químico genérico":
                tipo2 = " FQ ";
                break;
            case "Tabla nutricional":
                tipo2 = " TN ";
                break;
            case "Hisopados":
                tipo2 = " Hisopado ";
                break;
            case "Hisopados Alliance":
                tipo2 = " Hisopado ";
                break;
            case "Hisopados con límites":
                tipo2 = " Hisopado ";
                break;
            case "Efluentes cloaca":
                tipo2 = " Efluentes cloaca ";
                break;
            case "Base helada Del Turista":
                tipo2 = " Base helada Del Turista ";
                break;
            case "----------ANULADO----------":
                tipo2 = "no";
                JOptionPane.showMessageDialog(null, "Análsis anulado.");
                break;
            case "Manual":
                tipo2 = " Manual ";
                break;
        }
        if (!tipo2.equals("no")) {
            String procedencia = tablaDatos.getValueAt(fila2, 1).toString();
            String rutaGuardado = consultas.recuperarRutas("Reportes");
            String aux = "Informe " + id + tipo2 + clienteRepository.obtenerProcedenciayNombre(clienteRepository.obtenerIdCliente(String.valueOf(tablaDatos.getValueAt(fila2, 1)))); //en estas tres lineas se sacan espacios de
            String aux2 = aux.replaceAll("^" + org.ignaciorodriguez.utils.SeparatorUtils.s + "s*", "");                                                       //principio, final y se sacan las comillas
            String aux3 = aux2.replaceAll("" + org.ignaciorodriguez.utils.SeparatorUtils.s + "s*$", "");
            String pdf = aux3.replaceAll("\"", "");
            pdf += ".pdf";
            switch (tipo) {
                case "Microbiológico de agua código":
                    TablaMBAgua mb = new TablaMBAgua(this, true, id, procedencia, true, pdf);
                    mb.setVisible(true);
                    break;
                case "Microbiológico de agua bidón":
                    TablaMBAguaBidon mbbidon = new TablaMBAguaBidon(this, true, id, procedencia, true, pdf);
                    mbbidon.setVisible(true);
                    break;
                case "Microbiológico de agua balnearios":
                    TablaMBAguaBalnearios mbb = new TablaMBAguaBalnearios(this, true, id, procedencia, true, pdf);
                    mbb.setVisible(true);
                    break;
                case "Microbiológico de agua COFES":
                    TablaMBAguaCOFES mbc = new TablaMBAguaCOFES(this, true, id, procedencia, true, pdf);
                    mbc.setVisible(true);
                    break;
                case "Microbiológico de agua de recreación":
                    TablaMBAguaRecreacion mbr = new TablaMBAguaRecreacion(this, true, id, procedencia, true, pdf);
                    mbr.setVisible(true);
                    break;
                case "Físico químico de agua básico":
                    TablaFQAgua fq = new TablaFQAgua(this, true, id, procedencia, true, pdf);
                    System.out.println(pdf + " " + id);
                    fq.setVisible(true);
                    break;
                case "Físico químico de agua completo":
                    FQAlimentos fqagua = new FQAlimentos(this, true, id, true, pdf, Determinaciones.AGUA);
                    fqagua.setVisible(true);
                    break;
                case "Físico químico de alimentos":
                    FQAlimentos fqalimentos = new FQAlimentos(this, true, id, true, pdf, Determinaciones.ALIMENTO);
                    fqalimentos.setVisible(true);
                    break;
                case "Físico químico genérico":
                    FQAlimentos fqgenerico = new FQAlimentos(this, true, id, true, pdf, Determinaciones.GENERICO);
                    fqgenerico.setVisible(true);
                    break;
                case "Tabla nutricional":
                    TablaNutricional tn = new TablaNutricional(this, true, id, true, pdf);
                    tn.setVisible(true);
                    break;
                case "Efluentes":
                    TablaEfluentes efluentes = new TablaEfluentes(this, true, id, clienteRepository.obtenerProcedenciayNombre(id), true, pdf, muestraRepository.obtenerLugarMuestreo(id));
                    efluentes.setVisible(true);
                    break;
                case "Microbiológico de chocolates Del Turista":
                    TablaMBChocolates mbChocolates = new TablaMBChocolates(this, true, id, procedencia, true, pdf);
                    mbChocolates.setVisible(true);
                    break;
                case "Microbiológico de alimentos":
                    TablaMBAlimentos mbAlimentos = new TablaMBAlimentos(this, true, id, procedencia, true, pdf);
                    mbAlimentos.setVisible(true);
                    break;
                case "Hisopados":
                    TablaHisopados hisopados = new TablaHisopados(this, true, id, procedencia, true, pdf);
                    hisopados.setVisible(true);
                    break;
                case "Hisopados Alliance":
                    TablaHisopadosAlliance hisopadosAlliance = new TablaHisopadosAlliance(this, true, id, procedencia, true, pdf);
                    hisopadosAlliance.setVisible(true);
                    break;
                case "Hisopados con límites":
                    TablaHisopadosAlliance hisopadosConLimites = new TablaHisopadosAlliance(this, true, id, procedencia, true, pdf);
                    hisopadosConLimites.setVisible(true);
                    break;
                case "Efluentes cloaca":
                    TablaEfluentesCloaca tc = new TablaEfluentesCloaca(this, true, id, procedencia, true, pdf, tipo);
                    tc.setVisible(true);
                    break;
                case "Base helada Del Turista":
                    TablaBaseHelada tbh = new TablaBaseHelada(this, true, id, procedencia, true, pdf);
                    tbh.setVisible(true);
                    break;
                case "Efluentes infiltración":
                    TablaEfluentesInfiltracion ti = new TablaEfluentesInfiltracion(this, true, id, procedencia, true, pdf, tipo);
                    ti.setVisible(true);
                    break;
                case "Manual":
                    TablaManual tm = new TablaManual(this, true, true, id, procedencia, pdf);
                    tm.setVisible(true);
                    break;
            }
        }
    }

    private void itemEntregarActionPerformed(java.awt.event.ActionEvent evt) {
        Usuarios usuarios = new Usuarios(this, true);
        usuarios.setVisible(true); //ventana para elegir usuario visible
        String nombre = "error"; //inicio de variable nombre con el nombre del usuario
        nombre = usuarios.nombreUsuario();
        Usuario usuario = new Usuario();
        usuario.setUsuario(nombre);
        int id = Integer.parseInt((String) tablaDatos.getValueAt(fila2, 0)); // se obtiene id de la muestra desde la tabla
        usuario.setId(usuarioRepository.obtenerIdUsuario(nombre));
        if (nombre.equals("error")) {
        } else {
            if (consultas.entregarMuestra(usuario, id)) {
                muestraRepository.entregado(id);
                JOptionPane.showMessageDialog(null, "Entrega guardada.");
            } else {
                JOptionPane.showMessageDialog(null, "Error al guardar la entrega.");
            }
        }
    }

    private void itemEditarActionPerformed(java.awt.event.ActionEvent evt) {
        int id = Integer.parseInt((String) tablaDatos.getValueAt(fila2, 0));
        System.out.println("id = " + id);
        AgregarMuestra am = new AgregarMuestra(this, true, true, id);
        am.setVisible(true);
    }

    private void itemBorrarActionPerformed(java.awt.event.ActionEvent evt) {
        int id = Integer.parseInt((String) tablaDatos.getValueAt(fila2, 0));
        System.out.println(consultas.borrarAnalisis(id, 1));
        if (consultas.borrarAnalisis(id, 1)) {
            JOptionPane.showMessageDialog(null, "Análisis borrado.");
        } else {
            JOptionPane.showMessageDialog(null, "Error al borrar análisis.");
        }
        modeloTabla();
    }

    private void GenerarReporteActionPerformed(java.awt.event.ActionEvent evt) {
        int id = Integer.parseInt((String) tablaDatos.getValueAt(fila2, 0));
        String tipo = String.valueOf(tablaDatos.getValueAt(fila2, 10));
        String tipo2 = "";
        String db = "";
        switch (tipo) {
            case "Microbiológico de agua código":
                tipo2 = " MB Agua ";
                db = "mbagua";
                break;
            case "Microbiológico de agua bidón":
                tipo2 = " MB Agua ";
                db = "mbagua";
                break;
            case "Microbiológico de agua balnearios":
                tipo2 = " MB Agua balnearios ";
                db = "mbagua";
                break;
            case "Microbiológico de agua COFES":
                tipo2 = " MB Agua COFES ";
                db = "mbagua";
                break;
            case "Microbiológico de agua de recreación":
                tipo2 = " MB Agua de recreación ";
                db = "mbagua";
                break;
            case "Microbiológico de alimentos":
                tipo2 = " MB Alimentos ";
                db = "mbalimentos";
                break;
            case "Microbiológico de chocolates Del Turista":
                tipo2 = " MB Chocolates ";
                db = "mbchocolates";
                break;
            case "Efluentes":
                tipo2 = " Efluentes ";
                db = "efluentes";
                break;
            case "Efluentes infiltración":
                tipo2 = " Efluentes infiltración ";
                db = "efluentes";
                break;
            case "Físico químico de agua básico":
                tipo2 = " FQ agua básico ";
                db = "fqagua";
                break;
            case "Físico químico de agua completo":
                tipo2 = " FQ agua ";
                db = "determinaciones";
                break;
            case "Físico químico de alimentos":
                tipo2 = " FQ alimentos ";
                db = "determinaciones";
                break;
            case "Físico químico genérico":
                tipo2 = " FQ ";
                db = "determinaciones";
                break;
            case "Tabla nutricional":
                tipo2 = " TN ";
                db = "tablanutricional";
                break;
            case "Hisopados":
                tipo2 = " Hisopado ";
                db = "hisopados";
                break;
            case "Hisopados Alliance":
                tipo2 = " Hisopado ";
                db = "hisopados";
                break;
            case "Hisopados con límites":
                tipo2 = " Hisopado ";
                db = "hisopados";
                break;
            case "Efluentes cloaca":
                tipo2 = " Efluentes cloaca ";
                db = "efluentes";
                break;
            case "Base helada Del Turista":
                tipo2 = " Base helada ";
                db = "mbchocolates";
                break;
            case "----------ANULADO----------":
                JOptionPane.showMessageDialog(null, "Análisis anulado.");
                tipo2 = "no";
                break;
            case "Manual":
                tipo2 = " Manual ";
                db = "manual";
                break;
        }
        if (!tipo2.equals("no")) {
            String procedencia = tablaDatos.getValueAt(fila2, 1).toString();
            String rutaGuardado = consultas.recuperarRutas("Reportes");
            String aux = "" + org.ignaciorodriguez.utils.SeparatorUtils.s + "Informe " + id + tipo2 + clienteRepository.obtenerProcedenciayNombre(clienteRepository.obtenerIdCliente(String.valueOf(tablaDatos.getValueAt(fila2, 1)))); //en estas tres lineas se sacan espacios de
            String aux2 = aux.replaceAll("^" + org.ignaciorodriguez.utils.SeparatorUtils.s + "s*", "");                                                       //principio, final y se sacan las comillas
            String aux3 = aux2.replaceAll("" + org.ignaciorodriguez.utils.SeparatorUtils.s + "s*$", "");
            String pdf = aux3.replaceAll("\"", "");
            pdf += ".pdf";
            if (consultas.checkearPDF(id, db)) {
                Cursor cursor = new Cursor(Cursor.WAIT_CURSOR);
                this.setCursor(cursor);
                switch (tipo) {
                    case "Microbiológico de agua código":
                        consultas.generarReporteMBAgua(id, procedencia);
                        break;
                    case "Microbiológico de agua bidón":
                        consultas.generarReporteMBAguaBidon(id, procedencia);
                        break;
                    case "Microbiológico de agua balnearios":
                        consultas.generarReporteMBAguaBalnearios(id, procedencia);
                        break;
                    case "Microbiológico de agua COFES":
                        consultas.generarReporteMBAguaCOFES(id, procedencia);
                        break;
                    case "Microbiológico de agua de recreación":
                        consultas.generarReporteMBAguaDeRecreacion(id, procedencia);
                        break;
                    case "Físico químico de agua básico":
                        consultas.generarReporteFQAgua(id, procedencia);
                        break;
                    case "Físico químico de agua completo":
                        FQAlimentos fqa = new FQAlimentos(this, true, id, true, pdf, Determinaciones.AGUA);
                        fqa.apretarBoton();
                        break;
                    case "Físico químico de alimentos":
                        FQAlimentos fqaa = new FQAlimentos(this, true, id, true, pdf, Determinaciones.ALIMENTO);
                        fqaa.apretarBoton();
                        break;
                    case "Físico químico genérico":
                        FQAlimentos fqg = new FQAlimentos(this, true, id, true, pdf, Determinaciones.GENERICO);
                        fqg.apretarBoton();
                        break;
                    case "Tabla nutricional":
                        consultas.generarReporteTN(id, procedencia);
                        break;
                    case "Efluentes":
                        consultas.generarReporteEfluentes(id, procedencia);
                        break;
                    case "Microbiológico de chocolates Del Turista":
                        consultas.generarReporteMBChocolates(id, procedencia);
                        break;
                    case "Microbiológico de alimentos":
                        consultas.generarReporteMBAlimentos(id, procedencia);
                        break;
                    case "Hisopados":
                        consultas.generarReporteHisopados(id, procedencia);
                        break;
                    case "Hisopados Alliance":
                        consultas.generarReporteHisopadosAlliance(id, procedencia);
                        break;
                    case "Hisopados con límites":
                        consultas.generarReporteHisopadosAlliance(id, procedencia);
                        break;
                    case "Efluentes cloaca":
                        consultas.generarReporteEfluentesCloaca(id, procedencia);
                        break;
                    case "Efluentes infiltración":
                        consultas.generarReporteEfluentesInfiltracion(id, procedencia);
                        break;
                    case "Base helada Del Turista":
                        consultas.generarReporteMBBaseHelada(id, procedencia);
                        break;
                    case "Manual":
                        consultas.generarReporteManual(id, procedencia);
                        break;
                }
                cursor = new Cursor(Cursor.DEFAULT_CURSOR);
                this.setCursor(cursor);
            } else {
                switch (tipo) {
                    case "Microbiológico de agua código":
                        if (resultadoRepository.checkearResultadoMBAgua(id)) {
                            WeakReference<TablaMBAgua> mb = new WeakReference<>(new TablaMBAgua(this, true, id, procedencia, true, pdf));
                            mb.get().setVisible(true);
                        } else {
                            WeakReference<TablaMBAgua> mb = new WeakReference<>(new TablaMBAgua(this, true, id, procedencia, false, pdf));
                            mb.get().setVisible(true);
                        }
                        break;
                    case "Microbiológico de agua bidón":
                        if (resultadoRepository.checkearResultadoMBAgua(id)) {
                            TablaMBAguaBidon mbbidon = new TablaMBAguaBidon(this, true, id, procedencia, true, pdf);
                            mbbidon.setVisible(true);
                        } else {
                            TablaMBAguaBidon mbbidon = new TablaMBAguaBidon(this, true, id, procedencia, false, pdf);
                            mbbidon.setVisible(true);
                        }
                        break;
                    case "Microbiológico de agua balnearios":
                        if (resultadoRepository.checkearResultadoMBAgua(id)) {
                            TablaMBAguaBalnearios mbb = new TablaMBAguaBalnearios(this, true, id, procedencia, true, pdf);
                            mbb.setVisible(true);
                        } else {
                            TablaMBAguaBalnearios mbb = new TablaMBAguaBalnearios(this, true, id, procedencia, false, pdf);
                            mbb.setVisible(true);
                        }
                        break;

                    case "Microbiológico de agua COFES":
                        if (resultadoRepository.checkearResultadoMBAgua(id)) {
                            TablaMBAguaCOFES mbc = new TablaMBAguaCOFES(this, true, id, procedencia, true, pdf);
                            mbc.setVisible(true);
                        } else {
                            TablaMBAguaCOFES mbc = new TablaMBAguaCOFES(this, true, id, procedencia, false, pdf);
                            mbc.setVisible(true);
                        }
                        break;
                    case "Microbiológico de agua de recreación":
                        if (resultadoRepository.checkearResultadoMBAgua(id)) {
                            TablaMBAguaRecreacion mbr = new TablaMBAguaRecreacion(this, true, id, procedencia, true, pdf);
                            mbr.setVisible(true);
                        } else {
                            TablaMBAguaRecreacion mbr = new TablaMBAguaRecreacion(this, true, id, procedencia, false, pdf);
                            mbr.setVisible(true);
                        }
                        break;
                    case "Físico químico de agua básico":
                        TablaFQAgua fq = new TablaFQAgua(this, true, id, procedencia, false, pdf);
                        fq.setVisible(true);
                        break;
                    case "Físico químico de agua completo":
                        FQAlimentos fqa = new FQAlimentos(this, true, id, false, pdf, Determinaciones.AGUA);
                        fqa.setVisible(true);
                        break;
                    case "Físico químico de alimentos":
                        FQAlimentos fqaa = new FQAlimentos(this, true, id, false, pdf, Determinaciones.ALIMENTO);
                        fqaa.setVisible(true);
                        break;
                    case "Físico químico genérico":
                        FQAlimentos fqg = new FQAlimentos(this, true, id, false, pdf, Determinaciones.GENERICO);
                        fqg.setVisible(true);
                        break;
                    case "Tabla nutricional":
                        if (consultas.recuperarMarca(id) != null) {
                            System.out.println("recuperar");
                            TablaNutricional tn = new TablaNutricional(this, true, id, true, pdf);
                            tn.setVisible(true);
                            break;
                        } else {
                            TablaNutricional tn = new TablaNutricional(this, true, id, false, pdf);
                            tn.setVisible(true);
                            break;
                        }
                    case "Efluentes":
                        TablaEfluentes e = new TablaEfluentes(this, true, id, procedencia,
                                false, pdf, muestraRepository.obtenerLugarMuestreo(id));
                        e.setVisible(true);
                        break;
                    case "Microbiológico de chocolates Del Turista":
                        TablaMBChocolates mbchocolates = new TablaMBChocolates(this, true, id, procedencia, false, pdf);
                        mbchocolates.setVisible(true);
                        break;
                    case "Microbiológico de alimentos":
                        TablaMBAlimentos mbAlimentos = new TablaMBAlimentos(this, true, id, procedencia, false, pdf);
                        mbAlimentos.setVisible(true);
                        break;
                    case "Hisopados":
                        TablaHisopados hisopados = new TablaHisopados(this, true, id, procedencia, false, pdf);
                        hisopados.setVisible(true);
                        break;
                    case "Hisopados Alliance":
                        TablaHisopadosAlliance hisopadosAlliance = new TablaHisopadosAlliance(this, true, id, procedencia, false, pdf);
                        hisopadosAlliance.setVisible(true);
                        break;
                    case "Hisopados con límites":
                        TablaHisopadosAlliance hisopadosConLimites = new TablaHisopadosAlliance(this, true, id, procedencia, false, pdf);
                        hisopadosConLimites.setVisible(true);
                        break;
                    case "Efluentes cloaca":
                        TablaEfluentesCloaca ec = new TablaEfluentesCloaca(this, true, id, procedencia, false, pdf, tipo);
                        ec.setVisible(true);
                        break;
                    case "Efluentes infiltración":
                        TablaEfluentesInfiltracion ei = new TablaEfluentesInfiltracion(this, true, id, procedencia, false, pdf, tipo);
                        ei.setVisible(true);
                        break;
                    case "Base helada Del Turista":
                        TablaBaseHelada tbh = new TablaBaseHelada(this, true, id, procedencia, false, pdf);
                        tbh.setVisible(true);
                        break;
                    case "Manual":
                        TablaManual tm = new TablaManual(this, true, false, id, procedencia, pdf);
                        tm.setVisible(true);
                        break;
                }
            }
        }
    }

    private void itemRecuperarActionPerformed(java.awt.event.ActionEvent evt) {
        int id = Integer.parseInt((String) tablaDatos.getValueAt(fila2, 0));
        if (consultas.borrarAnalisis(id, 0)) {
            JOptionPane.showMessageDialog(null, "Se recupero la muestra.");
            consultas.recuperarBorrados();
        } else {
            JOptionPane.showMessageDialog(null, "Error al recuperar la muestra.");
        }
    }

    private void itemNotasActionPerformed(java.awt.event.ActionEvent evt) {
        int id = Integer.parseInt((String) tablaDatos.getValueAt(fila2, 0));
        VentanaNotas v = new VentanaNotas(this, true, id);
        v.setVisible(true);
    }

    private void itemEnProcesoActionPerformed(java.awt.event.ActionEvent evt) {
        int id = Integer.parseInt((String) tablaDatos.getValueAt(fila2, 0));
        AnalisisEnProceso ap = new AnalisisEnProceso(this, true, id);
        ap.setVisible(true);
    }

    private void itemVerUsuariosActionPerformed(java.awt.event.ActionEvent evt) {
        VerUsuarios vu = new VerUsuarios(this, true);
        vu.setVisible(true);
    }

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {
        VerClientes vc = new VerClientes(this, true, true);
        vc.setVisible(true);
    }

    private void radioSolicitanteActionPerformed(java.awt.event.ActionEvent evt) {
        labelBuscar.setText("Solicitante:");
    }

    private void radioFechaActionPerformed(java.awt.event.ActionEvent evt) {
        labelBuscar.setText("Fechas:");
    }

    private void radioProcedenciaActionPerformed(java.awt.event.ActionEvent evt) {
        labelBuscar.setText("Procedencia:");
    }

    private void radioIDActionPerformed(java.awt.event.ActionEvent evt) {
        labelBuscar.setText("ID:");
    }

    private void botonBuscar1ActionPerformed(java.awt.event.ActionEvent evt) {
        modeloTabla();
        actualizar = true;
        panelColores.setVisible(false);
    }

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {
        if (actualizacion == 1) {
            JOptionPane.showMessageDialog(null, "Para enviar un error, primero se debe probar la nueva versión del programa");
        } else {
            VentanaEmailError v = new VentanaEmailError(this, true);
            v.setVisible(true);
        }
    }

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {
        if (consultas.consultarActualizacion() == 1) {
            Descargando d = new Descargando(this, true);
            d.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "No hay actualizaciones disponibles.");
        }
    }

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {
        InformesVencimientos iv = new InformesVencimientos(this, true);
        iv.setVisible(true);
    }

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {
        Usuarios u = new Usuarios(this, true);
        u.setVisible(true);
        if (u.tipoUsuario() == 1) {
            Ganancias g = new Ganancias(this, false);
            g.setVisible(true);
        } else if (u.tipoUsuario() == 2) {
            JOptionPane.showMessageDialog(null, "No posee los permisos necesarios.");
        }
    }

    private void itemPruebaActionPerformed(java.awt.event.ActionEvent evt) {
        String procedencia = tablaDatos.getValueAt(fila2, 1).toString();
        String rutaGuardado = consultas.recuperarRutas("Reportes");
        String tipo2 = " FQ agua ";
        String aux = "" + org.ignaciorodriguez.utils.SeparatorUtils.s + "Informe " + id + tipo2 + clienteRepository.obtenerProcedenciayNombre(clienteRepository.obtenerIdCliente(String.valueOf(tablaDatos.getValueAt(fila2, 1)))); //en estas tres lineas se sacan espacios de
        String aux2 = aux.replaceAll("^" + org.ignaciorodriguez.utils.SeparatorUtils.s + "s*", "");                                                       //principio, final y se sacan las comillas
        String aux3 = aux2.replaceAll("" + org.ignaciorodriguez.utils.SeparatorUtils.s + "s*$", "");
        String pdf = aux3.replaceAll("\"", "");
        pdf += ".pdf";
        FQAlimentos fqa = new FQAlimentos(this, true, 1, false, pdf, Determinaciones.AGUA);
        fqa.setVisible(true);
    }

    private void itemBorrarPruebaActionPerformed(java.awt.event.ActionEvent evt) {
        if (resultadoRepository.borrarDeterminaciones(1)) {
            JOptionPane.showMessageDialog(null, "Prueba reseteada.");
        }
    }

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {
    VentanaExcel ve = new VentanaExcel(this, true);
    ve.setVisible(true);
    }

    private javax.swing.JMenuItem GenerarReporte;
    private javax.swing.JButton boton;
    public javax.swing.JButton botonAgregar;
    private javax.swing.JButton botonBuscar;
    private javax.swing.JButton botonBuscar1;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JTextField cajaBuscar;
    private javax.swing.JLabel etiquetaActualizacion;
    private javax.swing.JLabel etiquetaInicial;
    public javax.swing.JMenuItem itemAgregar;
    private javax.swing.JMenuItem itemAgregarPh;
    private javax.swing.JMenuItem itemBorrar;
    private javax.swing.JMenuItem itemBorrarPrueba;
    private javax.swing.JMenuItem itemEditar;
    private javax.swing.JMenuItem itemEditarResultados;
    public javax.swing.JMenuItem itemEliminar;
    private javax.swing.JMenuItem itemEnProceso;
    private javax.swing.JMenuItem itemEntregar;
    private javax.swing.JMenuItem itemInforme;
    public javax.swing.JMenuItem itemModificar;
    private javax.swing.JMenuItem itemNotas;
    private javax.swing.JMenuItem itemNuevoCliente;
    private javax.swing.JMenuItem itemPrueba;
    private javax.swing.JMenuItem itemRecuperar;
    private javax.swing.JMenuItem itemReporte;
    private javax.swing.JMenuItem itemRespaldo;
    public javax.swing.JMenuItem itemVencimientos;
    private javax.swing.JMenuItem itemVerClientes;
    private javax.swing.JMenuItem itemVerEntregas;
    private javax.swing.JMenuItem itemVerEntregas1;
    private javax.swing.JMenuItem itemVerUsuarios;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenu jMenu7;
    public javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel labelBuscar;
    private javax.swing.JMenu menuClientes;
    private javax.swing.JMenu menuEntregas;
    private javax.swing.JMenu menuEntregas1;
    private javax.swing.JMenu menuExcel;
    private javax.swing.JMenu menuInforme;
    private javax.swing.JPopupMenu menuPop;
    private javax.swing.JMenu menuPrueba;
    private javax.swing.JMenu menuVencimientos;
    private javax.swing.JMenuItem menuitemCopia;
    private javax.swing.JPanel panelColores;
    private javax.swing.JPopupMenu popUpTabla;
    private javax.swing.JRadioButton radioFecha;
    private javax.swing.JRadioButton radioID;
    private javax.swing.JRadioButton radioProcedencia;
    private javax.swing.JRadioButton radioSolicitante;
    public javax.swing.JTable tablaDatos;

    public void modeloTabla() {
        modeloTabla = muestraRepository.llenarTabla();
        int anchos[] = {24, 288, 208, 97, 63, 63, 75, 69, 60, 52, 219};
        for (int i = 0; i < 11; i++) {
            tablaDatos.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        tablaDatos.setDefaultRenderer(Object.class, defaultRender);
    }

    public boolean crearBackup(String ruta) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDateTime ahora = LocalDateTime.now();
        String fecha = dtf.format(ahora);
        String nombreSQL = "" + org.ignaciorodriguez.utils.SeparatorUtils.s + "respaldo_" + fecha + ".sql";
        try {
            String cmd = "\"" + consultas.recuperarRutas("MySQL") + "" + org.ignaciorodriguez.utils.SeparatorUtils.s + "mysqldump\" -u root -p1234 laboratorio --routines -r \"" + ruta + nombreSQL + "\"";
            System.out.println(cmd);
            Runtime.getRuntime().exec(cmd);
            return true;
        } catch (Exception ex) {
            System.out.println("Error, " + ex);
            return false;
        }
    }

    private void mostrarInformes() {
        InformeMuestras im = new InformeMuestras(this, true);
        im.setVisible(true);
    }

}
