package org.ignaciorodriguez.vista;
import java.awt.Color;
import java.awt.Component;
import java.awt.Point;
import java.io.File;
import javax.swing.JOptionPane;
import javax.swing.border.MatteBorder;
import org.ignaciorodriguez.modelo.Consultas;
import org.ignaciorodriguez.modelo.Resultados;
import org.ignaciorodriguez.repository.MuestraRepository;
import org.ignaciorodriguez.repository.ResultadoRepository;
import org.ignaciorodriguez.repository.VencimientoRepository;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Map;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.KeyStroke;
import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicComboBoxUI;

public class TablaMBAguaBalnearios extends javax.swing.JDialog {

    int id;
    String procedencia, pdf, auxCaracteres;
    Consultas c = Consultas.getInstancia();
    boolean editar, activarColiformes = true, activarEscherichia = true, activarShigella = true;
    MuestraRepository muestraRepository = new MuestraRepository();
    ResultadoRepository resultadoRepository = new ResultadoRepository();
    VencimientoRepository vencimientoRepository = new VencimientoRepository();

    public TablaMBAguaBalnearios(java.awt.Frame parent, boolean modal, int id, String procedencia, boolean editar, String pdf) {
        super(parent, modal);
        this.procedencia = procedencia;
        this.id = id;
        this.editar = editar;
        this.pdf = pdf.substring(1, pdf.length());
        initComponents();
        setTitle("ID " + id + ". " + muestraRepository.obtenerProcedencia(id) + ". Microbiológico de agua código balnearios");
        if (procedencia.contains("Municipio de Dina Huapi")) {
            checkDinaHuapi.setSelected(true);
        }
        if (editar == true) {
            Map<String, String> resultados = resultadoRepository.recuperarResultadosMBAgua(id);
            if (resultados == null) {
                this.editar = false;
            } else {
                etiquetaTitulo.setText("Editar resultados del análisis");
                if (resultados.get("coliformesTotales").equals("-2")) {
                    etiquetaDeterminacionesColiformesMousePressed(click(etiquetaDeterminacionesColiformes));
                } else if (resultados.get("coliformesTotales").contains("-1")) {
                } else {
                    campoColiformesTotales.setText(resultados.get("coliformesTotales").substring(0, resultados.get("coliformesTotales").length() - 9).replaceAll("[^0-9?!\\.]", ""));
                }

                if (resultados.get("escherichia").equals("-2")) {
                    etiquetaDeterminacionesEscherichiaMousePressed(click(etiquetaDeterminacionesEscherichia));
                } else if (resultados.get("escherichia").contains("-1")) {
                } else {
                    campoEscherichia.setText(resultados.get("escherichia").substring(0, resultados.get("escherichia").length() - 9).replaceAll("[^0-9?!\\.]", ""));
                }
                
                if(resultados.get("shigella").equals("-2")){
                    jLabel14MousePressed(click(jLabel4));
                } else {
                    if(resultados.get("shigella").toLowerCase().startsWith("ausencia")){
                        comboShigella.setSelectedIndex(0);
                    } else {
                        comboShigella.setSelectedIndex(1);
                    }
                }

                if (resultados.get("caracteresOrganolepticos").startsWith("Normales")) {
                    comboCaracteres.setSelectedItem(0);
                } else if (resultados.get("caracteresOrganolepticos").contains("-1")) {
                } else {
                    comboCaracteres.setSelectedIndex(1);
                    auxCaracteres = resultados.get("caracteresOrganolepticos");
                }
                java.sql.Date fecha = java.sql.Date.valueOf(resultados.get("fechaAnalisis"));
                java.util.Date utilFecha = null;
                try {
                    utilFecha = new java.util.Date(fecha.getTime());
                } catch (Exception e) {
                    System.err.println("error, " + e);
                }
                cajaFechaAnalisis.setDate(utilFecha);
            }
        }
        ImageIcon icon = new ImageIcon("src\\vista\\icono.png");
        this.setIconImage(icon.getImage());
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane11 = new javax.swing.JScrollPane();
        area33 = new javax.swing.JTextArea();
        botonGenerar = new javax.swing.JButton();
        etiquetaTitulo = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        cajaFechaAnalisis = new com.toedter.calendar.JDateChooser();
        jPanel5 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        comboCaracteres = new javax.swing.JComboBox<>();
        jPanel12 = new javax.swing.JPanel();
        etiquetaDeterminacionesColiformes = new javax.swing.JLabel();
        jScrollPane13 = new javax.swing.JScrollPane();
        area24 = new javax.swing.JTextArea();
        jPanel13 = new javax.swing.JPanel();
        etiquetaColiformesTotales = new javax.swing.JLabel();
        campoColiformesTotales = new javax.swing.JTextField();
        jPanel14 = new javax.swing.JPanel();
        etiquetaEscherichia = new javax.swing.JLabel();
        campoEscherichia = new javax.swing.JTextField();
        jPanel15 = new javax.swing.JPanel();
        etiquetaDeterminacionesEscherichia = new javax.swing.JLabel();
        checkDinaHuapi = new javax.swing.JCheckBox();
        jPanel17 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        etiquetaShigella = new javax.swing.JLabel();
        comboShigella = new javax.swing.JComboBox<>();
        jScrollPane18 = new javax.swing.JScrollPane();
        area37 = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setFocusable(false);
        jPanel1.setLayout(new java.awt.GridBagLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("<html>MÉTODO</html>");
        jLabel1.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        jLabel1.setFocusable(false);
        jLabel1.setMinimumSize(new java.awt.Dimension(150, 30));
        jLabel1.setOpaque(true);
        jLabel1.setPreferredSize(new java.awt.Dimension(150, 10));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.ipady = 40;
        gridBagConstraints.insets = new java.awt.Insets(20, 0, 0, 20);
        jPanel1.add(jLabel1, gridBagConstraints);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("<html>DETERMINACIONES</html>");
        jLabel2.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 0, 1, new java.awt.Color(0, 0, 0)));
        jLabel2.setFocusable(false);
        jLabel2.setMaximumSize(new java.awt.Dimension(180, 10));
        jLabel2.setMinimumSize(new java.awt.Dimension(180, 10));
        jLabel2.setOpaque(true);
        jLabel2.setPreferredSize(new java.awt.Dimension(180, 10));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.ipady = 40;
        gridBagConstraints.insets = new java.awt.Insets(20, 20, 0, 0);
        jPanel1.add(jLabel2, gridBagConstraints);
        jLabel2.setBorder(new MatteBorder(1,1,0,1, Color.black));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("<html>RECUENTO OBTENIDO</html>");
        jLabel4.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        jLabel4.setFocusable(false);
        jLabel4.setMinimumSize(new java.awt.Dimension(150, 30));
        jLabel4.setOpaque(true);
        jLabel4.setPreferredSize(new java.awt.Dimension(150, 10));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 29;
        gridBagConstraints.ipady = 40;
        gridBagConstraints.insets = new java.awt.Insets(20, 0, 0, 0);
        jPanel1.add(jLabel4, gridBagConstraints);

        jScrollPane11.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        jScrollPane11.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane11.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane11.setFocusable(false);
        jScrollPane11.setMinimumSize(new java.awt.Dimension(150, 60));
        jScrollPane11.setPreferredSize(new java.awt.Dimension(150, 60));

        area33.setEditable(false);
        area33.setColumns(20);
        area33.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        area33.setLineWrap(true);
        area33.setRows(5);
        area33.setText("SMEWW 22nd. Edition. APHA (2012) 9221 F");
        area33.setWrapStyleWord(true);
        area33.setBorder(null);
        area33.setFocusable(false);
        area33.setMargin(new java.awt.Insets(7, 7, 7, 7));
        jScrollPane11.setViewportView(area33);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 20);
        jPanel1.add(jScrollPane11, gridBagConstraints);

        botonGenerar.setText("Generar informe");
        botonGenerar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonGenerarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 20, 20);
        jPanel1.add(botonGenerar, gridBagConstraints);
        InputMap im = jPanel1.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false), "Apretar");

        ActionMap ap = jPanel1.getActionMap();
        ap.put("Apretar", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e){
                botonGenerar.doClick();
            }
        });

        etiquetaTitulo.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        etiquetaTitulo.setText("Agregar resultados de análisis");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(20, 20, 0, 0);
        jPanel1.add(etiquetaTitulo, gridBagConstraints);

        jPanel11.setLayout(new java.awt.GridBagLayout());

        jLabel8.setText("Fecha de analisis:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(20, 0, 0, 130);
        jPanel11.add(jLabel8, gridBagConstraints);

        cajaFechaAnalisis.setDateFormatString("dd-MM-yyyy");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(20, 0, 0, 0);
        jPanel11.add(cajaFechaAnalisis, gridBagConstraints);

        jPanel5.setLayout(new java.awt.GridBagLayout());

        jLabel6.setText("<html>Caracteres: organolepticos</html>");
        jLabel6.setPreferredSize(new java.awt.Dimension(90, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jPanel5.add(jLabel6, gridBagConstraints);

        comboCaracteres.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Normales", "Otro" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 20);
        jPanel5.add(comboCaracteres, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 50, 0, 0);
        jPanel11.add(jPanel5, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
        jPanel1.add(jPanel11, gridBagConstraints);

        jPanel12.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 0, 1, new java.awt.Color(0, 0, 0)));
        jPanel12.setFocusable(false);
        jPanel12.setMaximumSize(new java.awt.Dimension(180, 10));
        jPanel12.setMinimumSize(new java.awt.Dimension(180, 10));
        jPanel12.setPreferredSize(new java.awt.Dimension(180, 10));
        jPanel12.setLayout(new java.awt.GridBagLayout());

        etiquetaDeterminacionesColiformes.setBackground(new java.awt.Color(0, 0, 0));
        etiquetaDeterminacionesColiformes.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        etiquetaDeterminacionesColiformes.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        etiquetaDeterminacionesColiformes.setText("<html>RECUENTO COLIFORMES TOTALES</html>");
        etiquetaDeterminacionesColiformes.setToolTipText("");
        etiquetaDeterminacionesColiformes.setMaximumSize(new java.awt.Dimension(210, 60));
        etiquetaDeterminacionesColiformes.setMinimumSize(new java.awt.Dimension(210, 60));
        etiquetaDeterminacionesColiformes.setPreferredSize(new java.awt.Dimension(210, 60));
        etiquetaDeterminacionesColiformes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                etiquetaDeterminacionesColiformesMousePressed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        jPanel12.add(etiquetaDeterminacionesColiformes, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.ipady = 50;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        jPanel1.add(jPanel12, gridBagConstraints);

        jScrollPane13.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        jScrollPane13.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane13.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane13.setFocusable(false);
        jScrollPane13.setMinimumSize(new java.awt.Dimension(150, 60));
        jScrollPane13.setPreferredSize(new java.awt.Dimension(150, 60));

        area24.setEditable(false);
        area24.setColumns(20);
        area24.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        area24.setLineWrap(true);
        area24.setRows(5);
        area24.setText("SMEWW 22nd. Edition. APHA (2012) 9221 B");
        area24.setWrapStyleWord(true);
        area24.setBorder(null);
        area24.setFocusable(false);
        area24.setMargin(new java.awt.Insets(7, 7, 7, 7));
        jScrollPane13.setViewportView(area24);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 20);
        jPanel1.add(jScrollPane13, gridBagConstraints);

        jPanel13.setFocusable(false);
        jPanel13.setLayout(new java.awt.GridBagLayout());

        etiquetaColiformesTotales.setBackground(new java.awt.Color(255, 255, 255));
        etiquetaColiformesTotales.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        etiquetaColiformesTotales.setText("NMP/100 ml");
        etiquetaColiformesTotales.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        etiquetaColiformesTotales.setFocusable(false);
        etiquetaColiformesTotales.setMaximumSize(new java.awt.Dimension(53, 17));
        etiquetaColiformesTotales.setMinimumSize(new java.awt.Dimension(53, 17));
        etiquetaColiformesTotales.setOpaque(true);
        etiquetaColiformesTotales.setPreferredSize(new java.awt.Dimension(53, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 29;
        gridBagConstraints.ipady = 43;
        jPanel13.add(etiquetaColiformesTotales, gridBagConstraints);

        campoColiformesTotales.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        campoColiformesTotales.setText("500");
        campoColiformesTotales.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 0, new java.awt.Color(0, 0, 0)));
        campoColiformesTotales.setMaximumSize(new java.awt.Dimension(83, 15));
        campoColiformesTotales.setMinimumSize(new java.awt.Dimension(83, 15));
        campoColiformesTotales.setPreferredSize(new java.awt.Dimension(83, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 13;
        gridBagConstraints.ipady = 45;
        jPanel13.add(campoColiformesTotales, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        jPanel1.add(jPanel13, gridBagConstraints);

        jPanel14.setFocusable(false);
        jPanel14.setLayout(new java.awt.GridBagLayout());

        etiquetaEscherichia.setBackground(new java.awt.Color(255, 255, 255));
        etiquetaEscherichia.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        etiquetaEscherichia.setText("NMP/100 ml");
        etiquetaEscherichia.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        etiquetaEscherichia.setFocusable(false);
        etiquetaEscherichia.setMaximumSize(new java.awt.Dimension(53, 17));
        etiquetaEscherichia.setMinimumSize(new java.awt.Dimension(53, 17));
        etiquetaEscherichia.setOpaque(true);
        etiquetaEscherichia.setPreferredSize(new java.awt.Dimension(53, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 29;
        gridBagConstraints.ipady = 43;
        jPanel14.add(etiquetaEscherichia, gridBagConstraints);

        campoEscherichia.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        campoEscherichia.setText("500");
        campoEscherichia.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 0, new java.awt.Color(0, 0, 0)));
        campoEscherichia.setMaximumSize(new java.awt.Dimension(83, 15));
        campoEscherichia.setMinimumSize(new java.awt.Dimension(83, 15));
        campoEscherichia.setPreferredSize(new java.awt.Dimension(83, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 13;
        gridBagConstraints.ipady = 45;
        jPanel14.add(campoEscherichia, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        jPanel1.add(jPanel14, gridBagConstraints);

        jPanel15.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 0, 1, new java.awt.Color(0, 0, 0)));
        jPanel15.setFocusable(false);
        jPanel15.setMaximumSize(new java.awt.Dimension(180, 10));
        jPanel15.setMinimumSize(new java.awt.Dimension(180, 10));
        jPanel15.setPreferredSize(new java.awt.Dimension(180, 10));
        jPanel15.setLayout(new java.awt.GridBagLayout());

        etiquetaDeterminacionesEscherichia.setBackground(new java.awt.Color(0, 0, 0));
        etiquetaDeterminacionesEscherichia.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        etiquetaDeterminacionesEscherichia.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        etiquetaDeterminacionesEscherichia.setText("<html>RECUENTO ECHERICHIA COLI</html>");
        etiquetaDeterminacionesEscherichia.setToolTipText("");
        etiquetaDeterminacionesEscherichia.setMaximumSize(new java.awt.Dimension(210, 60));
        etiquetaDeterminacionesEscherichia.setMinimumSize(new java.awt.Dimension(210, 60));
        etiquetaDeterminacionesEscherichia.setPreferredSize(new java.awt.Dimension(210, 60));
        etiquetaDeterminacionesEscherichia.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                etiquetaDeterminacionesEscherichiaMousePressed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        jPanel15.add(etiquetaDeterminacionesEscherichia, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.ipady = 50;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        jPanel1.add(jPanel15, gridBagConstraints);

        checkDinaHuapi.setText("Conclusión Dina Huapi");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        jPanel1.add(checkDinaHuapi, gridBagConstraints);

        jPanel17.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel17.setFocusable(false);
        jPanel17.setMaximumSize(new java.awt.Dimension(180, 10));
        jPanel17.setMinimumSize(new java.awt.Dimension(180, 10));
        jPanel17.setPreferredSize(new java.awt.Dimension(180, 10));
        jPanel17.setLayout(new java.awt.GridBagLayout());

        jLabel14.setBackground(new java.awt.Color(0, 0, 0));
        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel14.setText("<html>SHIGELLA</html>");
        jLabel14.setToolTipText("");
        jLabel14.setFocusable(false);
        jLabel14.setMaximumSize(new java.awt.Dimension(210, 60));
        jLabel14.setMinimumSize(new java.awt.Dimension(210, 60));
        jLabel14.setPreferredSize(new java.awt.Dimension(210, 60));
        jLabel14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel14MousePressed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        jPanel17.add(jLabel14, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.ipady = 50;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        jPanel1.add(jPanel17, gridBagConstraints);

        jPanel16.setFocusable(false);
        jPanel16.setMinimumSize(new java.awt.Dimension(188, 1));
        jPanel16.setLayout(new java.awt.GridBagLayout());

        etiquetaShigella.setBackground(new java.awt.Color(255, 255, 255));
        etiquetaShigella.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        etiquetaShigella.setText("en 100 ml");
        etiquetaShigella.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 1, 1, new java.awt.Color(0, 0, 0)));
        etiquetaShigella.setFocusable(false);
        etiquetaShigella.setMaximumSize(new java.awt.Dimension(55, 17));
        etiquetaShigella.setMinimumSize(new java.awt.Dimension(55, 17));
        etiquetaShigella.setOpaque(true);
        etiquetaShigella.setPreferredSize(new java.awt.Dimension(55, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 14;
        gridBagConstraints.ipady = 43;
        jPanel16.add(etiquetaShigella, gridBagConstraints);

        comboShigella.setBackground(new java.awt.Color(204, 204, 204));
        comboShigella.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ausencia", "Presencia" }));
        comboShigella.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        comboShigella.setPreferredSize(new java.awt.Dimension(70, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 40;
        gridBagConstraints.ipady = 40;
        jPanel16.add(comboShigella, gridBagConstraints);
        comboShigella.setUI(new BasicComboBoxUI() {
            protected JButton createArrowButton() {
                return new JButton() {
                    public int getWidth() {
                        return 0;
                    }
                };
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        jPanel1.add(jPanel16, gridBagConstraints);

        jScrollPane18.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 1, 1, new java.awt.Color(0, 0, 0)));
        jScrollPane18.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane18.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane18.setFocusable(false);
        jScrollPane18.setMinimumSize(new java.awt.Dimension(150, 60));
        jScrollPane18.setPreferredSize(new java.awt.Dimension(150, 60));

        area37.setEditable(false);
        area37.setColumns(20);
        area37.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        area37.setLineWrap(true);
        area37.setRows(5);
        area37.setText("ISO 21567:2005\n");
        area37.setWrapStyleWord(true);
        area37.setBorder(null);
        area37.setFocusable(false);
        area37.setMargin(new java.awt.Insets(7, 7, 7, 7));
        jScrollPane18.setViewportView(area37);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 20);
        jPanel1.add(jScrollPane18, gridBagConstraints);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 834, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false), "Cerrar");
        ap.put("Cerrar", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e){
                dispose();
            }
        });

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botonGenerarActionPerformed(java.awt.event.ActionEvent evt) {
        completarReporte();
    }
    private void etiquetaDeterminacionesColiformesMousePressed(java.awt.event.MouseEvent evt) {
        activarColiformes = !activarColiformes;
        campoColiformesTotales.setEnabled(activarColiformes);
        area24.setEnabled(activarColiformes);
        etiquetaColiformesTotales.setEnabled(activarColiformes);
        if (activarColiformes) {
            jPanel12.setBackground(new Color(240, 240, 240));
        } else {
            jPanel12.setBackground(new Color(240, 100, 100));
        }
    }

    private void etiquetaDeterminacionesEscherichiaMousePressed(java.awt.event.MouseEvent evt) {
        activarEscherichia = !activarEscherichia;
        campoEscherichia.setEnabled(activarEscherichia);
        area33.setEnabled(activarColiformes);
        etiquetaEscherichia.setEnabled(activarEscherichia);
        if (activarEscherichia) {
            jPanel15.setBackground(new Color(240, 240, 240));
        } else {
            jPanel15.setBackground(new Color(240, 100, 100));
        }
    }

    private void jLabel14MousePressed(java.awt.event.MouseEvent evt) {
        activarShigella = !activarShigella;
        area37.setEnabled(activarShigella);
        etiquetaShigella.setEnabled(activarShigella);
        comboShigella.setEnabled(activarShigella);
        if (activarShigella) {
            jPanel17.setBackground(new Color(240, 240, 240));
        } else {
            jPanel17.setBackground(new Color(240, 100, 100));
        }
    }

    private javax.swing.JTextArea area24;
    private javax.swing.JTextArea area33;
    private javax.swing.JTextArea area37;
    private javax.swing.JButton botonGenerar;
    private com.toedter.calendar.JDateChooser cajaFechaAnalisis;
    private javax.swing.JTextField campoColiformesTotales;
    private javax.swing.JTextField campoEscherichia;
    private javax.swing.JCheckBox checkDinaHuapi;
    private javax.swing.JComboBox<String> comboCaracteres;
    private javax.swing.JComboBox<String> comboShigella;
    private javax.swing.JLabel etiquetaColiformesTotales;
    private javax.swing.JLabel etiquetaDeterminacionesColiformes;
    private javax.swing.JLabel etiquetaDeterminacionesEscherichia;
    private javax.swing.JLabel etiquetaEscherichia;
    private javax.swing.JLabel etiquetaShigella;
    private javax.swing.JLabel etiquetaTitulo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane18;

    public void completarReporte() {
        Resultados r = new Resultados();
        String coliformesTotales = "-2";
        if (campoColiformesTotales.isEnabled()) {
            coliformesTotales = campoColiformesTotales.getText().toUpperCase() + " " + etiquetaColiformesTotales.getText();
        }

        String escherichia = "-2";
        if (campoEscherichia.isEnabled()) {
            escherichia = campoEscherichia.getText().toUpperCase() + " " + etiquetaEscherichia.getText();
        }
        
        String shigella = "-2";
        if(comboShigella.isEnabled()) {
            shigella = comboShigella.getSelectedItem().toString().toUpperCase() + " " + etiquetaShigella.getText();
        }
        String caracteres = "";
        String idmuestra = String.valueOf(id);
        String[] valores = {idmuestra, "", coliformesTotales, "", escherichia, "", "", String.valueOf(-1), String.valueOf(-1), caracteres, "", shigella};
        if (editar) {
            if (checkDinaHuapi.isSelected()) {
                if (muestraRepository.recuperarObservaciones(id) != null && muestraRepository.recuperarObservaciones(id).length() > 5) {
                    valores[6] = JOptionPane.showInputDialog("Digite la observacion:", "Se reciben: frasco estéril x 500 ml con tiolsulfato"
                            + "de sodio para análisis microbiológico y frasco estéril x 120 ml para determinaciones de cloro residual y pH. " + muestraRepository.recuperarObservaciones(id).substring(160));
                } else {
                    valores[6] = JOptionPane.showInputDialog("Digite la observacion:", "Se reciben: frasco estéril x 500 ml con tiolsulfato"
                            + "de sodio para análisis microbiológico y frasco estéril x 120 ml para determinaciones de cloro residual y pH. ");
                }
            } else {
                valores[6] = JOptionPane.showInputDialog("Digite la observacion:", muestraRepository.recuperarObservaciones(id));
            }
        } else {
            if (checkDinaHuapi.isSelected()) {
                valores[6] = JOptionPane.showInputDialog("Digite la observacion:", "Se reciben: frasco estéril x 500 ml con tiolsulfato"
                        + "de sodio para análisis microbiológico y frasco estéril x 120 ml para determinaciones de cloro residual y pH. ");
            } else {
                valores[6] = JOptionPane.showInputDialog("Digite la observacion:");
            }
        }
        valores[6] = valores[6].isBlank() ? "" : valores[6].trim().endsWith(".") ? valores[6] : valores[6] + ".";
        r.setTipo("Microbiológico de agua balnearios");
        try {
            java.util.Date fm = cajaFechaAnalisis.getDate(); //obtener fecha
            Long dm = fm.getTime(); //sacar timepo
            java.sql.Date fechaAnalisis = new java.sql.Date(dm); //cast de fecha
            r.setFechaAnalisis(fechaAnalisis);
            if (comboCaracteres.getSelectedItem().equals("Normales")) {
                caracteres = "Normales";
            } else {
                if (editar) {
                    caracteres = JOptionPane.showInputDialog("Digite los caracteres organolepticos: ", auxCaracteres);
                } else {
                    caracteres = JOptionPane.showInputDialog("Digite los caracteres organolepticos: ");
                }
            }

            valores[9] = caracteres;
            r.setResultadoMB(valores);
            if (editar) {
                File rv = new File(c.recuperarRutas("Reportes") + "\\" + pdf);
                File rn = new File(c.recuperarRutas("Reportes") + "\\(BORRADO) " + pdf);
                rv.renameTo(rn);
                if (resultadoRepository.editarMBAgua(r)) {
                    muestraRepository.guardarFechaAnalisis(r, id);
                    muestraRepository.guardarFechaAnalisisMBAGUA(r, id);
                    vencimientoRepository.actualizarVencimiento(r);
                    muestraRepository.guardarObservaciones(valores[6], id);
                    this.dispose();
                    c.generarReporteMBAguaBalnearios(id, procedencia);
                }

            } else {
                if (resultadoRepository.guardarResultadoMBAgua(r)) {
                    muestraRepository.guardarFechaAnalisisMBAGUA(r, id);
                    muestraRepository.guardarFechaAnalisis(r, id);
                    vencimientoRepository.agregarVencimiento(r);
                    muestraRepository.guardarObservaciones(valores[6], id);
                    this.dispose();
                    c.generarReporteMBAguaBalnearios(id, procedencia);
                }

            }
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(null, "Complete la fecha de análisis");
        }
    }

    private MouseEvent click(Component componente) {
        long time = System.currentTimeMillis();
        Point point = new Point();
        point.x = componente.getX();
        point.y = componente.getY();
        MouseEvent me = new MouseEvent(componente, MouseEvent.MOUSE_CLICKED,
                time, 0, componente.getX(), componente.getY(), point.x, point.y, 1, false, MouseEvent.BUTTON1);
        return me;
    }
}
