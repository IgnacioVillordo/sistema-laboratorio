package org.ignaciorodriguez.vista;


import com.jidesoft.swing.AutoCompletionComboBox;
import com.toedter.calendar.JDateChooser;
import org.ignaciorodriguez.modelo.Conexion;
import org.ignaciorodriguez.modelo.Consultas;
import org.ignaciorodriguez.modelo.Muestra;
import org.ignaciorodriguez.repository.ClienteRepository;
import org.ignaciorodriguez.repository.MuestraRepository;
import org.ignaciorodriguez.repository.ResultadoRepository;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.Vector;


public class AgregarMuestra extends javax.swing.JDialog {

    public JDateChooser cajaMuestreo;
    public JTextField cajaHabilitacion;
    public JTextField cajaSolicitante;
    ResultadoRepository resultadoRepository = new ResultadoRepository();
    MuestraRepository muestraRepository = new MuestraRepository();
    ClienteRepository clienteRepository = new ClienteRepository();
    Consultas c = Consultas.getInstancia();
    int id, pago = 0, factura = 0;
    boolean editar = true, primero = true, delturista = false;
    Muestra m = new Muestra();
    String auxTipo;
    String auxId = "";
    Vector<String> solicitantes;
    private boolean existeSolicitante = true;
    private int cont;
    private Frame p;
    private JPanel jPanel1;
    private JLabel etiquetaAgregar;
    private JLabel etiquetaProcedencia;
    private JLabel etiquetaSolicitante;
    private JLabel etiquetaHabilitacion;
    private JLabel etiquetaMuestreo;
    private JLabel etiquetaRealizado;
    private JButton botonAgregar;
    private JComboBox<String> comboRealizado;
    private JLabel etiquetaElaboracion;
    private JLabel etiquetaPago;
    private JCheckBox checkPago;
    private JComboBox<String> comboTipo;
    private JLabel etiquetaTipo;
    private JComboBox<String> comboProcedencia;
    private JLabel etiquetaLote;
    private JTextField cajaLote;
    private JLabel EtiquetaIdMuestra;
    private JTextField cajaIdMuestra;
    private JLabel etiquetaFactura;
    private JCheckBox checkFactura;
    private JLabel etiquetaVencimiento;
    private JDateChooser cajaVencimiento;
    private JLabel etiquetaLugar;
    private JTextField cajaLugar;
    private JLabel etiquetaCosto;
    private JTextField cajaCoste;
    private JDateChooser cajaElaboracion;
    private JLabel etiquetaTipoAgua;
    private JComboBox<String> comboTipoAgua;
    private JLabel etiquetaMarca;
    private JTextField cajaMarca;
    private JPanel jPanel2;
    private JCheckBox checkGuardar;
    private JComboBox<String> comboSolicitante;
    public AgregarMuestra(java.awt.Frame parent, boolean modal, boolean editar, int id) {
        super(parent, modal);
        this.id = id;
        p = parent;
        solicitantes = muestraRepository.recuperarSolicitantes(m.getId());
        initComponents();
        java.sql.Date def = new java.sql.Date(-789, 10, 11);
        llenarComboBox();
        etiquetaAgregar.setText("Editar análisis");
        m = muestraRepository.obtenerMuestra(id);
        cajaHabilitacion.setText(m.getNumeroEstablecimiento());
        cajaMuestreo.setDate(m.getFechaMuestreo());
        comboRealizado.setSelectedItem(m.getRealizadoPor());
        cajaCoste.setText(String.valueOf(m.getCosteTotal()));
        if (m.getPago() == 1) {
            checkPago.setSelected(true);
        }
        if (m.getFactura() == 1) {
            checkFactura.setSelected(true);
        }
        comboTipo.setSelectedItem(m.getTipo());
        if (m.getTipo().equals("Tabla nutricional")) {
            cajaMarca.setText(resultadoRepository.recuperarMarca(id));
        }
        this.auxTipo = m.getTipo();
        cajaLote.setText(m.getLote());
        cajaIdMuestra.setText(m.getIdentificacion());
        if (m.getFechaElaboracion() != null) {
            if (!m.getFechaElaboracion().equals(def)) {
                cajaElaboracion.setDate(m.getFechaElaboracion());
            }
        }
        cajaLugar.setText(m.getLugarMuestreo());
        if (m.getFechaVencimiento() != null) {
            if (!m.getFechaVencimiento().equals(def)) {
                cajaVencimiento.setDate(m.getFechaVencimiento());
            }
        }
        comboTipoAgua.setSelectedItem(m.getTipoAgua());
        this.setLocationRelativeTo(null);
        ImageIcon icon = new ImageIcon("src\\vista\\icono.png");
        botonAgregar.setText("Editar análisis");
        AutoCompleteDecorator.decorate(comboTipoAgua);
        comboSolicitante.setVisible(false);
        this.setIconImage(icon.getImage());
        comboProcedencia.setSelectedItem(m.getProcedencia());
        auxId = m.getProcedencia();

        if (clienteRepository.consultarGuardar(m.getIdcliente())) {
            comboSolicitante.setVisible(true);
            cajaSolicitante.setVisible(false);
            AutoCompleteDecorator.decorate(comboSolicitante);
            comboSolicitante.setModel(new DefaultComboBoxModel(muestraRepository.recuperarSolicitantes(clienteRepository.recuperarIdCliente(comboProcedencia.getSelectedItem().toString()))));
            comboSolicitante.setSelectedItem(m.getSolicitante());
            checkGuardar.setSelected(true);
        }
    }
    public AgregarMuestra(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        id = -1;
        initComponents();
        this.setLocationRelativeTo(null);
        llenarComboBox();
    }

    private void initComponents() {
        jPanel1 = new JPanel();
        etiquetaAgregar = new JLabel();
        etiquetaProcedencia = new JLabel();
        etiquetaSolicitante = new JLabel();
        etiquetaHabilitacion = new JLabel();
        etiquetaMuestreo = new JLabel();
        etiquetaRealizado = new JLabel();
        botonAgregar = new JButton();
        comboRealizado = new JComboBox<>();
        cajaMuestreo = new JDateChooser();
        etiquetaElaboracion = new JLabel();
        etiquetaPago = new JLabel();
        checkPago = new JCheckBox();
        comboTipo = new JComboBox<>();
        etiquetaTipo = new JLabel();
        comboProcedencia = new AutoCompletionComboBox();
        etiquetaLote = new JLabel();
        cajaLote = new JTextField();
        EtiquetaIdMuestra = new JLabel();
        cajaIdMuestra = new JTextField();
        etiquetaFactura = new JLabel();
        checkFactura = new JCheckBox();
        etiquetaVencimiento = new JLabel();
        cajaVencimiento = new JDateChooser();
        etiquetaLugar = new JLabel();
        cajaLugar = new JTextField();
        etiquetaCosto = new JLabel();
        cajaCoste = new JTextField();
        cajaElaboracion = new JDateChooser();
        etiquetaTipoAgua = new JLabel();
        comboTipoAgua = new JComboBox<>();
        etiquetaMarca = new JLabel();
        cajaMarca = new JTextField();
        cajaHabilitacion = new JTextField();
        jPanel2 = new JPanel();
        cajaSolicitante = new JTextField();
        checkGuardar = new JCheckBox();
        comboSolicitante = new JComboBox<>();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        var contentPane = getContentPane();

        {
            jPanel1.setPreferredSize(new Dimension(571, 620));
            jPanel1.setLayout(new GridBagLayout());

            etiquetaAgregar.setFont(new Font("Segoe UI", Font.BOLD, 18));
            etiquetaAgregar.setHorizontalAlignment(SwingConstants.CENTER);
            etiquetaAgregar.setText("Agregar analisis");
            jPanel1.add(etiquetaAgregar, new GridBagConstraints(0, 0, 4, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.NONE,
                    new Insets(26, 85, 0, 0), 264, 0));

            etiquetaProcedencia.setText("Procedencia:");
            jPanel1.add(etiquetaProcedencia, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                    GridBagConstraints.EAST, GridBagConstraints.NONE,
                    new Insets(20, 20, 0, 0), 0, 0));

            etiquetaSolicitante.setText("Solicitante:");
            jPanel1.add(etiquetaSolicitante, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0,
                    GridBagConstraints.EAST, GridBagConstraints.NONE,
                    new Insets(20, 20, 0, 0), 0, 0));

            etiquetaHabilitacion.setHorizontalAlignment(SwingConstants.RIGHT);
            etiquetaHabilitacion.setText("<html>N\u00b0 de habilitaci\u00f3n:</html>");
            etiquetaHabilitacion.setHorizontalTextPosition(SwingConstants.RIGHT);
            jPanel1.add(etiquetaHabilitacion, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0,
                    GridBagConstraints.EAST, GridBagConstraints.NONE,
                    new Insets(20, 20, 0, 0), 36, 0));

            etiquetaMuestreo.setText("Fecha de muestreo:");
            jPanel1.add(etiquetaMuestreo, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
                    GridBagConstraints.EAST, GridBagConstraints.NONE,
                    new Insets(20, 20, 0, 0), 0, 0));

            etiquetaRealizado.setText("Realizado por:");
            jPanel1.add(etiquetaRealizado, new GridBagConstraints(2, 3, 1, 1, 0.0, 0.0,
                    GridBagConstraints.EAST, GridBagConstraints.NONE,
                    new Insets(20, 20, 0, 0), 0, 0));

            botonAgregar.setText("Agregar an\u00e1lisis");
            botonAgregar.addActionListener(e -> botonAgregarActionPerformed(e));
            jPanel1.add(botonAgregar, new GridBagConstraints(0, 10, 4, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.NONE,
                    new Insets(20, 0, 0, 0), 0, 0));

            comboRealizado.setModel(new DefaultComboBoxModel<>(new String[]{
                    "Cliente",
                    "Personal del laboratorio"
            }));
            jPanel1.add(comboRealizado, new GridBagConstraints(3, 3, 1, 1, 0.0, 0.0,
                    GridBagConstraints.NORTHWEST, GridBagConstraints.NONE,
                    new Insets(20, 20, 0, 0), 118, 0));

            cajaMuestreo.setDateFormatString("dd/MM/yyyy");
            cajaMuestreo.setMaximumSize(new Dimension(278, 20));
            cajaMuestreo.setMinimumSize(new Dimension(278, 20));
            cajaMuestreo.setOpaque(false);
            cajaMuestreo.setPreferredSize(new Dimension(278, 20));
            jPanel1.add(cajaMuestreo, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,
                    GridBagConstraints.WEST, GridBagConstraints.VERTICAL,
                    new Insets(20, 20, 0, 0), 0, 0));

            etiquetaElaboracion.setText("Fecha de elaboraci\u00f3n:");
            etiquetaElaboracion.setVisible(false);
            jPanel1.add(etiquetaElaboracion, new GridBagConstraints(2, 6, 1, 1, 0.0, 0.0,
                    GridBagConstraints.EAST, GridBagConstraints.NONE,
                    new Insets(20, 20, 0, 0), 0, 0));

            etiquetaPago.setText("\u00bfPag\u00f3?");
            jPanel1.add(etiquetaPago, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0,
                    GridBagConstraints.EAST, GridBagConstraints.NONE,
                    new Insets(20, 20, 0, 0), 0, 0));
            jPanel1.add(checkPago, new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0,
                    GridBagConstraints.NORTHWEST, GridBagConstraints.NONE,
                    new Insets(20, 40, 0, 0), 0, 0));

            comboTipo.setSelectedItem("Seleccione el tipo de análisis");
            comboTipo.setModel(new DefaultComboBoxModel<>(new String[]{
                    "Microbiol\u00f3gico de agua c\u00f3digo",
                    "Microbiol\u00f3gico de agua COFES",
                    "Microbiol\u00f3gico de agua de recreaci\u00f3n",
                    "Microbiol\u00f3gico de agua balnearios",
                    "Microbiol\u00f3gico de agua bid\u00f3n",
                    "Microbiol\u00f3gico de alimentos",
                    "Manual",
                    "Hisopados",
                    "Hisopados con l\u00edmites",
                    "Base helada Del Turista",
                    "Microbiol\u00f3gico de chocolates Del Turista",
                    "Efluentes",
                    "Efluentes cloaca",
                    "Efluentes infiltraci\u00f3n",
                    "F\u00edsico qu\u00edmico de agua b\u00e1sico",
                    "F\u00edsico qu\u00edmico de agua completo",
                    "F\u00edsico qu\u00edmico de alimentos",
                    "F\u00edsico qu\u00edmico gen\u00e9rico",
                    "Tabla nutricional"
            }));
            comboTipo.addItemListener(e -> comboTipoItemStateChanged(e));
            comboTipo.addActionListener(e -> comboTipoActionPerformed(e));
            comboTipo.setEditable(false);
            jPanel1.add(comboTipo, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
                    GridBagConstraints.NORTHWEST, GridBagConstraints.NONE,
                    new Insets(20, 20, 0, 0), 40, 0));

            etiquetaTipo.setText("Tipo de an\u00e1lisis:");
            jPanel1.add(etiquetaTipo, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
                    GridBagConstraints.EAST, GridBagConstraints.NONE,
                    new Insets(20, 20, 0, 0), 0, 0));

            comboProcedencia.setEditable(true);
            comboProcedencia.setSelectedItem("Seleccione la procedencia");
            comboProcedencia.setModel(new DefaultComboBoxModel<>(new String[]{

            }));
            comboProcedencia.setMaximumSize(new Dimension(255, 20));
            comboProcedencia.setMinimumSize(new Dimension(255, 20));
            comboProcedencia.setPreferredSize(new Dimension(255, 20));
            comboProcedencia.addItemListener(e -> comboProcedenciaItemStateChanged(e));
            jPanel1.add(comboProcedencia, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
                    GridBagConstraints.NORTHWEST, GridBagConstraints.NONE,
                    new Insets(20, 20, 0, 0), 0, 0));

            etiquetaLote.setText("Lote:");
            jPanel1.add(etiquetaLote, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
                    GridBagConstraints.EAST, GridBagConstraints.NONE,
                    new Insets(20, 20, 0, 0), 0, 0));

            cajaLote.setMaximumSize(new Dimension(250, 20));
            cajaLote.setMinimumSize(new Dimension(250, 20));
            cajaLote.setPreferredSize(new Dimension(250, 20));
            jPanel1.add(cajaLote, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,
                    GridBagConstraints.NORTHWEST, GridBagConstraints.NONE,
                    new Insets(20, 20, 0, 0), 0, 0));

            EtiquetaIdMuestra.setText("Identificaci\u00f3n de la muestra:");
            jPanel1.add(EtiquetaIdMuestra, new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0,
                    GridBagConstraints.EAST, GridBagConstraints.NONE,
                    new Insets(20, 20, 0, 0), 0, 0));

            cajaIdMuestra.setMaximumSize(new Dimension(250, 20));
            cajaIdMuestra.setMinimumSize(new Dimension(250, 20));
            cajaIdMuestra.setPreferredSize(new Dimension(250, 20));
            cajaIdMuestra.addActionListener(e -> cajaIdMuestraActionPerformed(e));
            jPanel1.add(cajaIdMuestra, new GridBagConstraints(1, 5, 1, 1, 0.0, 0.0,
                    GridBagConstraints.NORTHWEST, GridBagConstraints.NONE,
                    new Insets(20, 20, 0, 0), 0, 0));

            etiquetaFactura.setText("Factura:");
            jPanel1.add(etiquetaFactura, new GridBagConstraints(2, 5, 1, 1, 0.0, 0.0,
                    GridBagConstraints.EAST, GridBagConstraints.NONE,
                    new Insets(20, 20, 0, 0), 0, 0));
            jPanel1.add(checkFactura, new GridBagConstraints(3, 5, 1, 1, 0.0, 0.0,
                    GridBagConstraints.NORTHWEST, GridBagConstraints.NONE,
                    new Insets(20, 40, 0, 0), 0, 0));

            etiquetaVencimiento.setText("Fecha de vencimiento:");
            etiquetaVencimiento.setVisible(false);
            jPanel1.add(etiquetaVencimiento, new GridBagConstraints(2, 7, 1, 1, 0.0, 0.0,
                    GridBagConstraints.EAST, GridBagConstraints.NONE,
                    new Insets(20, 20, 0, 0), 0, 0));

            cajaVencimiento.setVisible(false);
            cajaVencimiento.setDateFormatString("dd/MM/yyyy");
            cajaVencimiento.setMaximumSize(new Dimension(278, 20));
            cajaVencimiento.setMinimumSize(new Dimension(278, 20));
            cajaVencimiento.setPreferredSize(new Dimension(278, 20));
            jPanel1.add(cajaVencimiento, new GridBagConstraints(3, 7, 1, 1, 0.0, 0.0,
                    GridBagConstraints.NORTHWEST, GridBagConstraints.NONE,
                    new Insets(20, 20, 0, 0), 0, 0));

            etiquetaLugar.setText("Lugar de muestreo:");
            jPanel1.add(etiquetaLugar, new GridBagConstraints(0, 6, 1, 1, 0.0, 0.0,
                    GridBagConstraints.EAST, GridBagConstraints.NONE,
                    new Insets(20, 20, 0, 0), 0, 0));

            cajaLugar.setMaximumSize(new Dimension(250, 20));
            cajaLugar.setMinimumSize(new Dimension(250, 20));
            cajaLugar.setPreferredSize(new Dimension(250, 20));
            jPanel1.add(cajaLugar, new GridBagConstraints(1, 6, 1, 1, 0.0, 0.0,
                    GridBagConstraints.NORTHWEST, GridBagConstraints.NONE,
                    new Insets(20, 20, 0, 0), 0, 0));

            etiquetaCosto.setText("Costo Total:");
            jPanel1.add(etiquetaCosto, new GridBagConstraints(2, 4, 1, 1, 0.0, 0.0,
                    GridBagConstraints.EAST, GridBagConstraints.NONE,
                    new Insets(20, 20, 0, 0), 0, 0));

            cajaCoste.setMaximumSize(new Dimension(250, 20));
            cajaCoste.setMinimumSize(new Dimension(250, 20));
            cajaCoste.setPreferredSize(new Dimension(250, 20));
            jPanel1.add(cajaCoste, new GridBagConstraints(3, 4, 1, 1, 0.0, 0.0,
                    GridBagConstraints.NORTHWEST, GridBagConstraints.NONE,
                    new Insets(20, 20, 0, 0), 0, 0));

            cajaElaboracion.setDateFormatString("dd/MM/yyyy");
            cajaElaboracion.setMaximumSize(new Dimension(278, 20));
            cajaElaboracion.setMinimumSize(new Dimension(278, 20));
            cajaElaboracion.setPreferredSize(new Dimension(278, 20));
            cajaElaboracion.setVisible(false);
            jPanel1.add(cajaElaboracion, new GridBagConstraints(3, 6, 1, 1, 0.0, 0.0,
                    GridBagConstraints.NORTHWEST, GridBagConstraints.NONE,
                    new Insets(20, 20, 0, 0), 0, 0));

            etiquetaTipoAgua.setText("Tipo de agua:");
            etiquetaTipoAgua.setVisible(false);
            jPanel1.add(etiquetaTipoAgua, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
                    GridBagConstraints.EAST, GridBagConstraints.NONE,
                    new Insets(20, 20, 0, 0), 0, 0));

            comboTipoAgua.setEditable(true);
            comboTipoAgua.setModel(new DefaultComboBoxModel<>(new String[]{
                    "Agua de red directa",
                    "Agua de red a tanque",
                    ""
            }));
            comboTipoAgua.addItemListener(e -> comboTipoAguaItemStateChanged(e));
            comboTipoAgua.addActionListener(e -> comboTipoAguaActionPerformed(e));
            comboTipoAgua.setVisible(false);
            jPanel1.add(comboTipoAgua, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,
                    GridBagConstraints.NORTHWEST, GridBagConstraints.NONE,
                    new Insets(20, 20, 0, 0), 55, 0));

            etiquetaMarca.setText("Marca:");
            jPanel1.add(etiquetaMarca, new GridBagConstraints(0, 7, 1, 1, 0.0, 0.0,
                    GridBagConstraints.EAST, GridBagConstraints.NONE,
                    new Insets(20, 20, 0, 0), 0, 0));

            cajaMarca.setMaximumSize(new Dimension(250, 20));
            cajaMarca.setMinimumSize(new Dimension(250, 20));
            cajaMarca.setPreferredSize(new Dimension(250, 20));
            jPanel1.add(cajaMarca, new GridBagConstraints(1, 7, 1, 1, 0.0, 0.0,
                    GridBagConstraints.NORTHWEST, GridBagConstraints.NONE,
                    new Insets(20, 20, 0, 0), 0, 0));

            cajaHabilitacion.setMaximumSize(new Dimension(250, 20));
            cajaHabilitacion.setMinimumSize(new Dimension(250, 20));
            cajaHabilitacion.setPreferredSize(new Dimension(250, 20));
            jPanel1.add(cajaHabilitacion, new GridBagConstraints(3, 2, 1, 1, 0.0, 0.0,
                    GridBagConstraints.NORTHWEST, GridBagConstraints.NONE,
                    new Insets(20, 20, 0, 0), 0, 0));

            {
                jPanel2.setLayout(new GridBagLayout());

                cajaSolicitante.setMaximumSize(new Dimension(250, 20));
                cajaSolicitante.setMinimumSize(new Dimension(250, 20));
                cajaSolicitante.setPreferredSize(new Dimension(250, 20));
                jPanel2.add(cajaSolicitante, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                        GridBagConstraints.NORTHWEST, GridBagConstraints.NONE,
                        new Insets(20, 20, 0, 0), 0, 0));

                checkGuardar.setText("Guardar");
                checkGuardar.addActionListener(e -> checkGuardarActionPerformed(e));
                jPanel2.add(checkGuardar, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                        GridBagConstraints.NORTHWEST, GridBagConstraints.NONE,
                        new Insets(20, 10, 0, 0), 0, 0));

                comboSolicitante.setModel(new DefaultComboBoxModel<>(new String[]{
                        "Item 1",
                        "Item 2",
                        "Item 3",
                        "Item 4"
                }));
                comboSolicitante.setMaximumSize(new Dimension(250, 20));
                comboSolicitante.setMinimumSize(new Dimension(250, 20));
                comboSolicitante.setName("");
                comboSolicitante.setPreferredSize(new Dimension(250, 20));
                jPanel2.add(comboSolicitante, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                        GridBagConstraints.NORTHWEST, GridBagConstraints.NONE,
                        new Insets(20, 20, 0, 0), 0, 0));
            }
            jPanel1.add(jPanel2, new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.NONE,
                    new Insets(0, 0, 0, 0), 0, 0));
        }

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
                contentPaneLayout.createParallelGroup()
                        .addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, 1034, Short.MAX_VALUE)
        );
        contentPaneLayout.setVerticalGroup(
                contentPaneLayout.createParallelGroup()
                        .addComponent(jPanel1, GroupLayout.PREFERRED_SIZE, 452, GroupLayout.PREFERRED_SIZE)
        );
        pack();
        setLocationRelativeTo(getOwner());
    }

    private void botonAgregarActionPerformed(java.awt.event.ActionEvent evt) {
        Muestra nuevaMuestra = new Muestra();
        if (comboTipo.getSelectedItem().equals("Seleccione el tipo de análisis") || (cajaSolicitante.getText().equals("") && comboSolicitante.getItemCount() < 1)
                || cajaHabilitacion.getText().equals("") || cajaMuestreo.getDate().equals("")
                || cajaCoste.getText().matches("[^0-9?!\\.]")) {
            JOptionPane.showMessageDialog(null, "Rellene todos los campos correctamente");
        } else {

            Date fm = cajaMuestreo.getDate();
            Long dm = fm.getTime();
            java.sql.Date fechaMuestreo = new java.sql.Date(dm);
            Date fv = cajaVencimiento.getDate();
            java.sql.Date fechaVencimiento;
            if (fv != null) {
                Long dv = fv.getTime();
                fechaVencimiento = new java.sql.Date(dv);
                nuevaMuestra.setFechaVencimiento(fechaVencimiento);
            }
            Date fe = cajaElaboracion.getDate();
            java.sql.Date fechaElaboracion;
            if (fe != null) {
                Long de = fe.getTime();
                fechaElaboracion = new java.sql.Date(de);
                nuevaMuestra.setFechaElaboracion(fechaElaboracion);
            }
            nuevaMuestra.setProcedencia(String.valueOf(comboProcedencia.getSelectedItem()));
            nuevaMuestra.setIdcliente(clienteRepository.obtenerIdCliente(String.valueOf(comboProcedencia.getSelectedItem())));
            nuevaMuestra.setSolicitante(cajaSolicitante.isVisible() ? cajaSolicitante.getText() : comboSolicitante.getSelectedItem().toString());
            nuevaMuestra.setNumeroEstablecimiento(cajaHabilitacion.getText());
            nuevaMuestra.setFechaMuestreo(fechaMuestreo);
            nuevaMuestra.setRealizadoPor(String.valueOf(comboRealizado.getSelectedItem()));
            nuevaMuestra.setLugarMuestreo(cajaLugar.getText());
            nuevaMuestra.setTipoAgua(comboTipoAgua.getSelectedItem().toString());
            if (!cajaCoste.getText().equals("") || !cajaCoste.getText().equals("-")) {
                nuevaMuestra.setCosteTotal(Double.parseDouble(cajaCoste.getText()));
            } else {
                nuevaMuestra.setCosteTotal(-1);
            }
            nuevaMuestra.setLote(cajaLote.getText());
            String auxiliar = cajaIdMuestra.getText().trim();
            nuevaMuestra.setIdentificacion(auxiliar.endsWith(".") ? auxiliar.substring(0, auxiliar.length() - 1) : auxiliar);
            if (checkPago.isSelected()) {
                pago = 1;
            }
            nuevaMuestra.setPago(pago);
            if (checkFactura.isSelected()) {
                factura = 1;
            }
            nuevaMuestra.setFactura(factura);
            nuevaMuestra.setTipo(String.valueOf(comboTipo.getSelectedItem()));
            nuevaMuestra.setGuardar(checkGuardar.isSelected());

            try {
                if (id < 1) {
                    nuevaMuestra.setId(muestraRepository.recuperarIdMuestrasSiguiente());
                    if (muestraRepository.agregarMuestra(nuevaMuestra)) {
                        if (clienteRepository.guardarSolicitanteGuardar(nuevaMuestra.getIdcliente(), checkGuardar.isSelected())) {
                            int idaux = muestraRepository.obtenerIdMuestra();
                            if (nuevaMuestra.getTipo().contains("Tabla nutricional")) {
                                resultadoRepository.guardarMarca(idaux, cajaMarca.getText());
                            }
                            c.agregarAdministracion(idaux, Double.parseDouble(cajaCoste.getText()), pago, factura);
                            if (nuevaMuestra.getTipo().equals("Microbiológico de agua código")) {
                                muestraRepository.agregarTipoAgua(nuevaMuestra.getId(), nuevaMuestra.getTipoAgua());
                            }
                            this.dispose();

                            JOptionPane.showMessageDialog(null, "Análisis ingresado con éxito");
                        } else {
                            JOptionPane.showMessageDialog(null, "Error al ingresar el análisis. Revise los datos ingresados");
                        }
                    }
                } else {

                    nuevaMuestra.setId(id);
                    if (muestraRepository.editarMuestra(nuevaMuestra)) {
                        if (clienteRepository.guardarSolicitanteGuardar(nuevaMuestra.getIdcliente(), checkGuardar.isSelected())) {
                            if (nuevaMuestra.getTipo().contains("Tabla nutricional")) {
                                resultadoRepository.guardarMarca(id, cajaMarca.getText());
                            }
                            c.editarAdministracion(nuevaMuestra.getId(), nuevaMuestra.getCosteTotal(), nuevaMuestra.getPago(), nuevaMuestra.getFactura());

                            if (!m.getTipoAgua().isBlank()) {
                                muestraRepository.agregarTipoAgua(nuevaMuestra.getId(), "");
                            }
                            if (!auxTipo.equals(nuevaMuestra.getTipo())) {
                                String db = "";
                                switch (auxTipo) {
                                    case "Microbiológico de agua código":
                                        db = "mbagua";
                                        break;
                                    case "Microbiológico de agua bidón":
                                        db = "mbagua";
                                        break;
                                    case "Microbiológico de agua balnearios":
                                        db = "mbagua";
                                        break;
                                    case "Microbiológico de agua COFES":
                                        db = "mbagua";
                                        break;
                                    case "Microbiológico de agua de recreación":
                                        db = "mbagua";
                                        break;
                                    case "Microbiológico de alimentos":
                                        db = "mbalimentos";
                                        break;
                                    case "Microbiológico de chocolates Del Turista":
                                        db = "mbchocolates";
                                        break;
                                    case "Efluentes":
                                        db = "efluentes";
                                        break;
                                    case "Efluentes infiltración":
                                        db = "efluentes";
                                        break;
                                    case "Físico químico de agua básico":
                                        db = "fqagua";
                                        break;
                                    case "Físico químico de agua completo":
                                        db = "determinaciones";
                                        break;
                                    case "Físico químico de alimentos":
                                        db = "determinaciones";
                                        break;
                                    case "Físico químico genérico":
                                        db = "determinaciones";
                                        break;
                                    case "Tabla nutricional":
                                        db = "tablanutricional";
                                        break;
                                    case "Hisopados":
                                        db = "hisopados";
                                        break;
                                    case "Hisopados Alliance":
                                        db = "hisopados";
                                        break;
                                    case "Hisopados con límites":
                                        db = "hisopados";
                                        break;
                                    case "Efluentes cloaca":
                                        db = "efluentes";
                                        break;
                                    case "Base helada Del Turista":
                                        db = "mbchocolates";
                                        break;
                                    case "Manual":
                                        db = "manual";
                                        break;
                                }
                                if (auxTipo.equals("Hisopados") && (nuevaMuestra.getTipo().equals("Hisopados Alliance") || nuevaMuestra.getTipo().equals("Hisopados con límites"))) {
                                    resultadoRepository.cambiarHisopado(id);
                                } else {
                                    resultadoRepository.cambiarTipo(id, db);
                                }
                            }

                            if (nuevaMuestra.getTipo().equals("Microbiológico de agua código")) {
                                muestraRepository.agregarTipoAgua(nuevaMuestra.getId(), nuevaMuestra.getTipoAgua());
                            }
                            this.dispose();

                            JOptionPane.showMessageDialog(null, "Análisis editado con éxito");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Error al editar el análisis. Revise los datos ingresados");
                    }
                }
                this.dispose();
            } catch (java.lang.NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Ingrese el costo del análisis.");
            }
        }
    }

    private void comboTipoItemStateChanged(java.awt.event.ItemEvent evt) {
        boolean lugar;
        if (evt.getItem() == "Físico químico de alimentos" || evt.getItem() == "Microbiológico de alimentos" || evt.getItem() == "Manual") {
            etiquetaLote.setVisible(true);
            cajaLote.setVisible(true);
            etiquetaVencimiento.setVisible(true);
            cajaVencimiento.setVisible(true);
            etiquetaElaboracion.setVisible(true);
            cajaElaboracion.setVisible(true);
            comboTipoAgua.setVisible(false);
            etiquetaTipoAgua.setVisible(false);
            cajaLugar.setVisible(false);
            etiquetaLugar.setVisible(false);
            correrEtiqueta(etiquetaMuestreo, 0, 4);
            correrComponentes(cajaMuestreo, 1, 4);
            correrEtiqueta(etiquetaPago, 0, 5);
            correrComponentes(checkPago, 1, 5);
            correrEtiqueta(EtiquetaIdMuestra, 0, 6);
            correrComponentes(cajaIdMuestra, 1, 6);
            GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
            gridBagConstraints2.gridx = 0;
            gridBagConstraints2.gridy = 8;
            gridBagConstraints2.gridwidth = 4;
            gridBagConstraints2.insets = new java.awt.Insets(20, 0, 0, 0);
            jPanel1.add(botonAgregar, gridBagConstraints2);
            if (!primero) {
                if (delturista) {
                    auxId = comboProcedencia.getSelectedItem().toString();
                }
                comboProcedencia.removeAllItems();
                llenarComboBox();
                comboProcedencia.setSelectedItem(auxId);
            }
        } else if (evt.getItem().toString().startsWith("Efluentes")) {
            lugar = true;
            correrElementosElse(lugar);
            if (delturista) {
                auxId = comboProcedencia.getSelectedItem().toString();
            }
            comboProcedencia.removeAllItems();
            llenarComboBox();
            comboProcedencia.setSelectedItem(auxId);
        } else if (evt.getItem() == ("Microbiológico de agua código")) {
            lugar = false;
            correrElementosElse(lugar);
            etiquetaTipoAgua.setVisible(true);
            comboTipoAgua.setVisible(true);
            correrEtiqueta(etiquetaMuestreo, 0, 4);
            correrComponentes(cajaMuestreo, 1, 4);
            correrEtiqueta(etiquetaPago, 0, 5);
            correrComponentes(checkPago, 1, 5);
            correrEtiqueta(EtiquetaIdMuestra, 0, 6);
            correrComponentes(cajaIdMuestra, 1, 6);
            if (!primero) {
                if (evt.getStateChange() == ItemEvent.SELECTED) {
                    if (delturista) {
                        auxId = comboProcedencia.getSelectedItem().toString();
                    }
                }
                comboProcedencia.removeAllItems();
                llenarComboBox();
                comboProcedencia.setSelectedItem(auxId);
            }
        } else if (evt.getItem() == "Microbiológico de chocolates Del Turista") {
            etiquetaVencimiento.setVisible(true);
            cajaVencimiento.setVisible(true);
            etiquetaElaboracion.setVisible(true);
            cajaElaboracion.setVisible(true);
            GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
            gridBagConstraints2.gridx = 0;
            gridBagConstraints2.gridy = 8;
            gridBagConstraints2.gridwidth = 4;
            gridBagConstraints2.insets = new java.awt.Insets(20, 0, 0, 0);
            jPanel1.add(botonAgregar, gridBagConstraints2);
            comboProcedencia.removeAllItems();
            llenarComboBox(1);
            primero = false;
            if (editar) {
                if (m.getProcedencia().contains("Del Turista")) {
                    delturista = true;
                }
            }
        } else if (evt.getItem().toString().equals("Tabla nutricional")) {
            correrElementosElse(false);
            cajaMarca.setVisible(true);
            etiquetaMarca.setVisible(true);
        } else {
            lugar = false;
            correrElementosElse(lugar);
            if (!primero) {
                if (delturista) {
                    auxId = comboProcedencia.getSelectedItem().toString();
                }
                comboProcedencia.removeAllItems();
                llenarComboBox();
                comboProcedencia.setSelectedItem(auxId);
            }
        }
    }

    private void comboProcedenciaItemStateChanged(java.awt.event.ItemEvent evt) {
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            String procedencia = String.valueOf(comboProcedencia.getSelectedItem());
            cajaHabilitacion.setText(muestraRepository.obtenerHablitacion(procedencia));
            solicitantes = muestraRepository.recuperarSolicitantes(clienteRepository.recuperarIdCliente(comboProcedencia.getSelectedItem().toString()));
            AutoCompleteDecorator.decorate(comboSolicitante);
            comboSolicitante.setModel(new DefaultComboBoxModel(solicitantes));
            existeSolicitante = true;
            checkGuardar.setSelected(!clienteRepository.consultarGuardar(clienteRepository.recuperarIdCliente(comboProcedencia.getSelectedItem().toString())));
            checkGuardar.doClick();
            if (editar) {
                editar = false;
            }
        }
    }

    private void comboTipoActionPerformed(java.awt.event.ActionEvent evt) {
    }

    private void cajaIdMuestraActionPerformed(java.awt.event.ActionEvent evt) {
    }

    private void comboTipoAguaItemStateChanged(java.awt.event.ItemEvent evt) {
    }

    private void comboTipoAguaActionPerformed(java.awt.event.ActionEvent evt) {
    }

    private void checkGuardarActionPerformed(java.awt.event.ActionEvent evt) {
        if (checkGuardar.isSelected()) {
            if (!comboSolicitante.isVisible()) {
                comboSolicitante.setVisible(true);
                cajaSolicitante.setVisible(false);
            }
            AutoCompleteDecorator.decorate(comboSolicitante);
            comboSolicitante.setModel(new DefaultComboBoxModel(muestraRepository.recuperarSolicitantes(clienteRepository.recuperarIdCliente(comboProcedencia.getSelectedItem().toString()))));
        } else {
            comboSolicitante.setVisible(false);
            cajaSolicitante.setVisible(true);
            cajaSolicitante.setText("");
        }
    }

    public void llenarComboBox() {
        Conexion con = new Conexion();
        Connection conexion = con.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conexion.prepareStatement("select procedencia from vistaProcedencia order by procedencia asc");
            rs = ps.executeQuery();
            while (rs.next()) {
                comboProcedencia.addItem(String.valueOf(rs.getString("procedencia")));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al recuperar procedencias, " + e);
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
    }

    public void llenarComboBox(int i) {
        Conexion con = new Conexion();
        Connection conexion = con.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conexion.prepareStatement("select procedencia from vistaProcedencia where procedencia like ?");
            ps.setString(1, "%Del Turista%");
            rs = ps.executeQuery();
            while (rs.next()) {
                comboProcedencia.addItem(String.valueOf(rs.getString("procedencia")));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al recuperar procedencias, " + e);
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
    }

    public void correrEtiqueta(Component c, int x, int y) {
        java.awt.GridBagConstraints gridBagConstraints;
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = x;
        gridBagConstraints.gridy = y;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(20, 20, 0, 0);
        jPanel1.add(c, gridBagConstraints);
    }

    public void correrComponentes(Component c, int x, int y) {
        java.awt.GridBagConstraints gridBagConstraints;
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = x;
        gridBagConstraints.gridy = y;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(20, 20, 0, 0);
        jPanel1.add(c, gridBagConstraints);
    }

    public void correrElementosElse(boolean lugar) {
        etiquetaLote.setVisible(false);
        cajaLote.setVisible(false);
        etiquetaVencimiento.setVisible(false);
        cajaVencimiento.setVisible(false);
        cajaVencimiento.setDate(null);
        etiquetaLugar.setVisible(lugar);
        cajaLugar.setVisible(lugar);
        cajaElaboracion.setVisible(false);
        cajaElaboracion.setDate(null);
        etiquetaElaboracion.setVisible(false);
        etiquetaTipoAgua.setVisible(false);
        comboTipoAgua.setVisible(false);
        etiquetaMarca.setVisible(false);
        cajaMarca.setVisible(false);
        correrEtiqueta(etiquetaMuestreo, 0, 3);
        correrComponentes(cajaMuestreo, 1, 3);
        correrEtiqueta(etiquetaPago, 0, 4);
        correrComponentes(checkPago, 1, 4);
        correrEtiqueta(EtiquetaIdMuestra, 0, 5);
        correrComponentes(cajaIdMuestra, 1, 5);
        correrEtiqueta(etiquetaMarca, 0, 7);
        correrComponentes(cajaMarca, 1, 7);
        GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
        gridBagConstraints2.gridx = 0;
        gridBagConstraints2.gridy = 8;
        gridBagConstraints2.gridwidth = 4;
        gridBagConstraints2.insets = new java.awt.Insets(20, 0, 0, 0);
        jPanel1.add(botonAgregar, gridBagConstraints2);
    }
}
