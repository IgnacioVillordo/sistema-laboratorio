package org.ignaciorodriguez.vista;

import java.awt.Color;
import java.awt.Component;
import java.awt.Frame;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.border.MatteBorder;
import javax.swing.plaf.basic.BasicComboBoxUI;
import org.ignaciorodriguez.modelo.Consultas;
import org.ignaciorodriguez.repository.MuestraRepository;
import org.ignaciorodriguez.repository.ResultadosRepository;

public class TablaHisopadosAlliance extends javax.swing.JDialog {

    int id;
    String procedencia, pdf, auxObservaciones;
    Consultas c = Consultas.getInstancia();
    boolean editar, activarGermens = true, activarTotales = true, activarFecales = true,
            activarEscherichia = true, activarEnterobacterias = true, activarStaphilococos = true;
    Frame parent;
    MuestraRepository muestraRepository = new MuestraRepository();
    ResultadosRepository resultadosRepository = new ResultadosRepository();

    public TablaHisopadosAlliance(java.awt.Frame parent, boolean modal, int id, String procedencia,
                                  boolean editar, String pdf) {
        super(parent, modal);
        this.parent = parent;
        this.procedencia = procedencia;
        this.id = id;
        this.editar = editar;
        this.pdf = pdf;
        initComponents();
        setTitle("ID " + id + ". " + muestraRepository.obtenerProcedencia(id) + ". Hisopado sin límites");
        setLocationRelativeTo(null);
        if (editar == true) {
            Map<String, String> resultados = resultadosRepository.recuperarResultadosHisopadosAlliance(id);
            if (resultados == null) {
                this.editar = false;
            } else {
                String aux = "";
                DecimalFormat df = new DecimalFormat("#.##");

                if (resultados.get("germenes").toLowerCase().contains("menor")) {
                    checkGermenes.doClick();
                    if (resultados.get("germenes").toLowerCase().endsWith("cm2")) {
                        comboGermenesIn.setSelectedIndex(1);
                    } else {
                        comboGermenesIn.setSelectedIndex(0);
                    }
                } else if (resultados.get("germenes").contains("-1")) {
                } else if (resultados.get("germenes").contains("-2")) {
                    etiquetaGermenesMousePressed(click(etiquetaGermenes));
                } else {
                    aux = resultados.get("germenes").replaceAll("[^0-9?!\\.]", "");
                    cajaGermenes.setText(df.format(Double.parseDouble(aux)).replace(",", "."));
                    System.out.println(resultados.get("germenes").toLowerCase().endsWith("cm2"));
                    if (resultados.get("germenes").toLowerCase().endsWith("cm2")) {
                        comboGermenesIn.setSelectedIndex(1);
                    } else {
                        comboGermenesIn.setSelectedIndex(0);
                    }
                }

                if (resultados.get("coliformesTotales").toLowerCase().contains("menor")) {
                    checkColiformes.doClick();
                    if (resultados.get("coliformesTotales").toLowerCase().endsWith("cm2")) {
                        comboTotalesIn.setSelectedIndex(1);
                    } else {
                        comboTotalesIn.setSelectedIndex(0);
                    }
                } else if (resultados.get("coliformesTotales").contains("-1")) {
                } else if (resultados.get("coliformesTotales").contains("-2")) {
                    etiquetaTotalesMousePressed(click(etiquetaTotales));
                } else {
                    aux = resultados.get("coliformesTotales").replaceAll("[^0-9?!\\.]", "");
                    cajaTotales.setText(df.format(Double.parseDouble(aux)).replace(",", "."));
                    if (resultados.get("coliformesTotales").toLowerCase().endsWith("cm2")) {
                        comboTotalesIn.setSelectedIndex(1);
                    } else {
                        comboTotalesIn.setSelectedIndex(0);
                    }
                }

                if (resultados.get("coliformesFecales").startsWith("Ausencia")) {
                    comboFecales.setSelectedIndex(0);
                } else if (resultados.get("coliformesFecales").contains("-1")) {
                } else if (resultados.get("coliformesFecales").contains("-2")) {
                    etiquetaFecalesMousePressed(click(etiquetaFecales));
                } else {
                    comboFecales.setSelectedItem(resultados.get("coliformesFecales"));
                }

                if (resultados.get("escherichia").startsWith("Ausencia")) {
                    comboEscherichia.setSelectedIndex(0);
                } else if (resultados.get("escherichia").contains("-1")) {
                } else if (resultados.get("escherichia").contains("-2")) {
                    etiquetaEscherichiaMousePressed(click(etiquetaEscherichia));
                } else {
                    comboEscherichia.setSelectedIndex(1);
                }

                if (resultados.get("staphilococos").toLowerCase().contains("menor")) {
                    checkStaphilococos.doClick();
                    if (resultados.get("staphilococos").toLowerCase().endsWith("cm2")) {
                        comboStaphilococosIn.setSelectedIndex(1);
                    } else {
                        comboStaphilococosIn.setSelectedIndex(0);
                    }
                } else if (resultados.get("staphilococos").contains("-1")) {
                } else if (resultados.get("staphilococos").contains("-2")) {
                    etiquetaStaphilococosMousePressed(click(etiquetaStaphilococos));
                } else {
                    aux = resultados.get("staphilococos").replaceAll("[^0-9?!\\.]", "");
                    cajaStaphilococos.setText(df.format(Double.parseDouble(aux)).replace(",", "."));
                    if (resultados.get("staphilococos").toLowerCase().endsWith("cm2")) {
                        comboStaphilococosIn.setSelectedIndex(1);
                    } else {
                        comboStaphilococosIn.setSelectedIndex(0);
                    }
                }

                if (resultados.get("enterobacterias").contains("-2")) {
                    etiquetaEnterobacteriasMousePressed(click(etiquetaEnterobacterias));
                } else if (resultados.get("enterobacterias").contains("-1")) {
                } else {
                    comboEnterobacterias.setSelectedItem(resultados.get("enterobacterias"));
                }

                java.sql.Date fecha = java.sql.Date.valueOf(resultados.get("fechaAnalisis"));
                java.util.Date utilFecha = null;
                try {
                    utilFecha = new java.util.Date(fecha.getTime());
                } catch (Exception e) {
                    System.err.println("error, " + e);
                }
                cajaFechaAnalisis.setDate(utilFecha);
                auxObservaciones = muestraRepository.recuperarObservaciones(id);
                if (resultados.get("limiteGermenes").equals("400")) {
                    Limite400.setSelected(true);
                } else if (resultados.get("limiteGermenes").equals("3000")) {
                    Limite3000.setSelected(true);
                }

                if (resultados.get("limiteTotales").equals("10")) {
                    Limite10.setSelected(true);
                } else if (resultados.get("limiteTotales").equals("200")) {
                    Limite200.setSelected(true);
                } else if (resultados.get("limiteTotales").equals("50")) {
                    Limite50.setSelected(true);
                }
            }
        }

        ImageIcon icon = new ImageIcon("src\\vista\\icono.png");
        this.setIconImage(icon.getImage());
    }


    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        grupoGermenes = new javax.swing.ButtonGroup();
        grupoTotales = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        etiquetaTotales = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        etiquetaStaphilococos = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        etiquetaGermenes = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        etiquetaFecales = new javax.swing.JLabel();
        jScrollPane9 = new javax.swing.JScrollPane();
        area13 = new javax.swing.JTextArea();
        jScrollPane10 = new javax.swing.JScrollPane();
        area23 = new javax.swing.JTextArea();
        jScrollPane11 = new javax.swing.JScrollPane();
        area33 = new javax.swing.JTextArea();
        jScrollPane12 = new javax.swing.JScrollPane();
        area63 = new javax.swing.JTextArea();
        botonGenerar = new javax.swing.JButton();
        etiquetaTitulo = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        etiquetaEscherichia = new javax.swing.JLabel();
        jScrollPane14 = new javax.swing.JScrollPane();
        area43 = new javax.swing.JTextArea();
        jLabel3 = new javax.swing.JLabel();
        cajaFechaAnalisis = new com.toedter.calendar.JDateChooser();
        jPanel9 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        comboFecales = new javax.swing.JComboBox<>();
        jPanel12 = new javax.swing.JPanel();
        comboEscherichia = new javax.swing.JComboBox<>();
        jPanel16 = new javax.swing.JPanel();
        comboEnterobacterias = new javax.swing.JComboBox<>();
        jPanel3 = new javax.swing.JPanel();
        cajaGermenes = new javax.swing.JTextField();
        etiquetaMenorGermenes = new javax.swing.JLabel();
        comboGermenesIn = new javax.swing.JComboBox<>();
        jPanel17 = new javax.swing.JPanel();
        cajaTotales = new javax.swing.JTextField();
        etiquetaMenorTotales = new javax.swing.JLabel();
        comboTotalesIn = new javax.swing.JComboBox<>();
        jPanel20 = new javax.swing.JPanel();
        cajaStaphilococos = new javax.swing.JTextField();
        etiquetaMenorStaphilococos = new javax.swing.JLabel();
        comboStaphilococosIn = new javax.swing.JComboBox<>();
        checkGermenes = new javax.swing.JCheckBox();
        checkColiformes = new javax.swing.JCheckBox();
        jPanel14 = new javax.swing.JPanel();
        etiquetaEnterobacterias = new javax.swing.JLabel();
        jScrollPane13 = new javax.swing.JScrollPane();
        area53 = new javax.swing.JTextArea();
        checkStaphilococos = new javax.swing.JCheckBox();
        jPanel18 = new javax.swing.JPanel();
        etiquetaLimiteColiformes = new javax.swing.JLabel();
        Limite10 = new javax.swing.JRadioButton();
        Limite200 = new javax.swing.JRadioButton();
        Limite50 = new javax.swing.JRadioButton();
        jPanel19 = new javax.swing.JPanel();
        etiquetaLimiteGermenes = new javax.swing.JLabel();
        Limite400 = new javax.swing.JRadioButton();
        Limite3000 = new javax.swing.JRadioButton();

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
        jLabel2.setMinimumSize(new java.awt.Dimension(150, 30));
        jLabel2.setOpaque(true);
        jLabel2.setPreferredSize(new java.awt.Dimension(150, 10));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.ipady = 40;
        gridBagConstraints.insets = new java.awt.Insets(20, 20, 0, 0);
        jPanel1.add(jLabel2, gridBagConstraints);
        jLabel2.setBorder(new MatteBorder(1,1,0,1, Color.black));

        jPanel2.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 0, 1, new java.awt.Color(0, 0, 0)));
        jPanel2.setFocusable(false);
        jPanel2.setMaximumSize(new java.awt.Dimension(170, 60));
        jPanel2.setMinimumSize(new java.awt.Dimension(170, 60));
        jPanel2.setPreferredSize(new java.awt.Dimension(170, 60));
        jPanel2.setLayout(new java.awt.GridBagLayout());

        etiquetaTotales.setBackground(new java.awt.Color(0, 0, 0));
        etiquetaTotales.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        etiquetaTotales.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        etiquetaTotales.setText("<html>COLIFORMES TOTALES</html>");
        etiquetaTotales.setToolTipText("");
        etiquetaTotales.setFocusable(false);
        etiquetaTotales.setMaximumSize(new java.awt.Dimension(170, 60));
        etiquetaTotales.setMinimumSize(new java.awt.Dimension(170, 60));
        etiquetaTotales.setPreferredSize(new java.awt.Dimension(140, 60));
        etiquetaTotales.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                etiquetaTotalesMousePressed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        jPanel2.add(etiquetaTotales, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        jPanel1.add(jPanel2, gridBagConstraints);

        jPanel6.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 1, 1, 1, new java.awt.Color(0, 0, 0)));
        jPanel6.setFocusable(false);
        jPanel6.setMaximumSize(new java.awt.Dimension(170, 60));
        jPanel6.setMinimumSize(new java.awt.Dimension(170, 60));
        jPanel6.setPreferredSize(new java.awt.Dimension(170, 60));
        jPanel6.setLayout(new java.awt.GridBagLayout());

        etiquetaStaphilococos.setBackground(new java.awt.Color(0, 0, 0));
        etiquetaStaphilococos.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        etiquetaStaphilococos.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        etiquetaStaphilococos.setText("<html><body style=\"text-align: center\">STAPHILOCOCOS AUREUS COAGULOSA (+)</body></html>");
        etiquetaStaphilococos.setToolTipText("");
        etiquetaStaphilococos.setFocusable(false);
        etiquetaStaphilococos.setMaximumSize(new java.awt.Dimension(170, 60));
        etiquetaStaphilococos.setMinimumSize(new java.awt.Dimension(170, 60));
        etiquetaStaphilococos.setPreferredSize(new java.awt.Dimension(170, 60));
        etiquetaStaphilococos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                etiquetaStaphilococosMousePressed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        jPanel6.add(etiquetaStaphilococos, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        jPanel1.add(jPanel6, gridBagConstraints);

        jPanel7.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 0, 1, new java.awt.Color(0, 0, 0)));
        jPanel7.setFocusable(false);
        jPanel7.setMaximumSize(new java.awt.Dimension(170, 60));
        jPanel7.setMinimumSize(new java.awt.Dimension(170, 60));
        jPanel7.setPreferredSize(new java.awt.Dimension(170, 60));
        jPanel7.setLayout(new java.awt.GridBagLayout());

        etiquetaGermenes.setBackground(new java.awt.Color(0, 0, 0));
        etiquetaGermenes.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        etiquetaGermenes.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        etiquetaGermenes.setText("<html><body style=\"text-align: center\">GERMENES AEROBIOS TOTALES</body></html>");
        etiquetaGermenes.setToolTipText("");
        etiquetaGermenes.setFocusable(false);
        etiquetaGermenes.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        etiquetaGermenes.setMaximumSize(new java.awt.Dimension(170, 60));
        etiquetaGermenes.setMinimumSize(new java.awt.Dimension(170, 60));
        etiquetaGermenes.setPreferredSize(new java.awt.Dimension(170, 60));
        etiquetaGermenes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                etiquetaGermenesMousePressed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        jPanel7.add(etiquetaGermenes, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        jPanel1.add(jPanel7, gridBagConstraints);

        jPanel8.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 0, 1, new java.awt.Color(0, 0, 0)));
        jPanel8.setFocusable(false);
        jPanel8.setMaximumSize(new java.awt.Dimension(170, 60));
        jPanel8.setMinimumSize(new java.awt.Dimension(170, 60));
        jPanel8.setPreferredSize(new java.awt.Dimension(170, 60));
        jPanel8.setLayout(new java.awt.GridBagLayout());

        etiquetaFecales.setBackground(new java.awt.Color(0, 0, 0));
        etiquetaFecales.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        etiquetaFecales.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        etiquetaFecales.setText("<html>COLIFORMES FECALES</html>");
        etiquetaFecales.setToolTipText("");
        etiquetaFecales.setFocusable(false);
        etiquetaFecales.setMaximumSize(new java.awt.Dimension(170, 60));
        etiquetaFecales.setMinimumSize(new java.awt.Dimension(170, 60));
        etiquetaFecales.setPreferredSize(new java.awt.Dimension(140, 60));
        etiquetaFecales.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                etiquetaFecalesMousePressed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        jPanel8.add(etiquetaFecales, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        jPanel1.add(jPanel8, gridBagConstraints);

        jScrollPane9.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        jScrollPane9.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane9.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane9.setFocusable(false);
        jScrollPane9.setMinimumSize(new java.awt.Dimension(150, 60));
        jScrollPane9.setPreferredSize(new java.awt.Dimension(150, 60));

        area13.setEditable(false);
        area13.setColumns(20);
        area13.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        area13.setLineWrap(true);
        area13.setRows(5);
        area13.setText("ISO 4833-1:2013");
        area13.setToolTipText("");
        area13.setWrapStyleWord(true);
        area13.setAutoscrolls(false);
        area13.setBorder(null);
        area13.setFocusable(false);
        area13.setMargin(new java.awt.Insets(7, 7, 7, 7));
        jScrollPane9.setViewportView(area13);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 20);
        jPanel1.add(jScrollPane9, gridBagConstraints);

        jScrollPane10.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        jScrollPane10.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane10.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane10.setFocusable(false);
        jScrollPane10.setMinimumSize(new java.awt.Dimension(150, 60));
        jScrollPane10.setPreferredSize(new java.awt.Dimension(150, 60));

        area23.setEditable(false);
        area23.setColumns(20);
        area23.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        area23.setLineWrap(true);
        area23.setRows(5);
        area23.setText("ISO 4238-2006");
        area23.setWrapStyleWord(true);
        area23.setBorder(null);
        area23.setFocusable(false);
        area23.setMargin(new java.awt.Insets(7, 7, 7, 7));
        jScrollPane10.setViewportView(area23);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 20);
        jPanel1.add(jScrollPane10, gridBagConstraints);

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
        area33.setText("ISO 4238-2006");
        area33.setWrapStyleWord(true);
        area33.setBorder(null);
        area33.setFocusable(false);
        area33.setMargin(new java.awt.Insets(7, 7, 7, 7));
        jScrollPane11.setViewportView(area33);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 20);
        jPanel1.add(jScrollPane11, gridBagConstraints);

        jScrollPane12.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 1, new java.awt.Color(0, 0, 0)));
        jScrollPane12.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane12.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane12.setFocusable(false);
        jScrollPane12.setMinimumSize(new java.awt.Dimension(150, 60));
        jScrollPane12.setPreferredSize(new java.awt.Dimension(150, 60));

        area63.setEditable(false);
        area63.setColumns(20);
        area63.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        area63.setLineWrap(true);
        area63.setRows(5);
        area63.setText("ISO 6888-1:1999/ Amd. 1:2003");
        area63.setWrapStyleWord(true);
        area63.setBorder(null);
        area63.setFocusable(false);
        area63.setMargin(new java.awt.Insets(7, 7, 7, 7));
        jScrollPane12.setViewportView(area63);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 20);
        jPanel1.add(jScrollPane12, gridBagConstraints);

        botonGenerar.setText("Generar informe");
        botonGenerar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonGenerarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 9;
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
        etiquetaTitulo.setFocusable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(20, 20, 0, 0);
        jPanel1.add(etiquetaTitulo, gridBagConstraints);

        jPanel11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel11.setFocusable(false);
        jPanel11.setMaximumSize(new java.awt.Dimension(170, 60));
        jPanel11.setMinimumSize(new java.awt.Dimension(170, 60));
        jPanel11.setPreferredSize(new java.awt.Dimension(170, 60));
        jPanel11.setLayout(new java.awt.GridBagLayout());

        etiquetaEscherichia.setBackground(new java.awt.Color(0, 0, 0));
        etiquetaEscherichia.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        etiquetaEscherichia.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        etiquetaEscherichia.setText("<html>ESCHERICHIA COLI</html>");
        etiquetaEscherichia.setToolTipText("");
        etiquetaEscherichia.setFocusable(false);
        etiquetaEscherichia.setMaximumSize(new java.awt.Dimension(170, 60));
        etiquetaEscherichia.setMinimumSize(new java.awt.Dimension(170, 60));
        etiquetaEscherichia.setPreferredSize(new java.awt.Dimension(140, 60));
        etiquetaEscherichia.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                etiquetaEscherichiaMousePressed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        jPanel11.add(etiquetaEscherichia, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        jPanel1.add(jPanel11, gridBagConstraints);

        jScrollPane14.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 1, 1, new java.awt.Color(0, 0, 0)));
        jScrollPane14.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane14.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane14.setFocusable(false);
        jScrollPane14.setMinimumSize(new java.awt.Dimension(150, 60));
        jScrollPane14.setPreferredSize(new java.awt.Dimension(150, 60));

        area43.setEditable(false);
        area43.setColumns(20);
        area43.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        area43.setLineWrap(true);
        area43.setRows(5);
        area43.setText("SMEWW 22nd. Edition. APHA (2012) 9221 B");
        area43.setWrapStyleWord(true);
        area43.setBorder(null);
        area43.setFocusable(false);
        area43.setMargin(new java.awt.Insets(7, 7, 7, 7));
        jScrollPane14.setViewportView(area43);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 20);
        jPanel1.add(jScrollPane14, gridBagConstraints);

        jLabel3.setText("Fecha de análisis");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
        jPanel1.add(jLabel3, gridBagConstraints);

        cajaFechaAnalisis.setDateFormatString("dd-MM-yyyy");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 50;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 0);
        jPanel1.add(cajaFechaAnalisis, gridBagConstraints);

        jPanel9.setLayout(new java.awt.GridBagLayout());

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("<html>RECUENTO OBTENIDO</html>");
        jLabel4.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        jLabel4.setFocusable(false);
        jLabel4.setMinimumSize(new java.awt.Dimension(150, 30));
        jLabel4.setOpaque(true);
        jLabel4.setPreferredSize(new java.awt.Dimension(150, 10));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 22;
        gridBagConstraints.ipady = 40;
        gridBagConstraints.insets = new java.awt.Insets(20, 0, 0, 0);
        jPanel9.add(jLabel4, gridBagConstraints);

        jPanel10.setFocusable(false);
        jPanel10.setLayout(new java.awt.GridBagLayout());

        comboFecales.setBackground(new java.awt.Color(204, 204, 204));
        comboFecales.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ausencia", "Presencia" }));
        comboFecales.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        comboFecales.setNextFocusableComponent(cajaGermenes);
        comboFecales.setPreferredSize(new java.awt.Dimension(70, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 102;
        gridBagConstraints.ipady = 40;
        jPanel10.add(comboFecales, gridBagConstraints);
        comboFecales.setUI(new BasicComboBoxUI() {
            protected JButton createArrowButton() {
                return new JButton() {
                    public int getWidth() {
                        return 0;
                    }
                };
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        jPanel9.add(jPanel10, gridBagConstraints);

        jPanel12.setFocusable(false);
        jPanel12.setLayout(new java.awt.GridBagLayout());

        comboEscherichia.setBackground(new java.awt.Color(204, 204, 204));
        comboEscherichia.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ausencia", "Presencia" }));
        comboEscherichia.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 1, 1, new java.awt.Color(0, 0, 0)));
        comboEscherichia.setNextFocusableComponent(cajaGermenes);
        comboEscherichia.setPreferredSize(new java.awt.Dimension(70, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 102;
        gridBagConstraints.ipady = 40;
        jPanel12.add(comboEscherichia, gridBagConstraints);
        comboEscherichia.setUI(new BasicComboBoxUI() {
            protected JButton createArrowButton() {
                return new JButton() {
                    public int getWidth() {
                        return 0;
                    }
                };
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        jPanel9.add(jPanel12, gridBagConstraints);

        jPanel16.setFocusable(false);
        jPanel16.setLayout(new java.awt.GridBagLayout());

        comboEnterobacterias.setBackground(new java.awt.Color(204, 204, 204));
        comboEnterobacterias.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ausencia", "Presencia" }));
        comboEnterobacterias.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 1, new java.awt.Color(0, 0, 0)));
        comboEnterobacterias.setNextFocusableComponent(cajaGermenes);
        comboEnterobacterias.setPreferredSize(new java.awt.Dimension(70, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 102;
        gridBagConstraints.ipady = 40;
        jPanel16.add(comboEnterobacterias, gridBagConstraints);
        comboEnterobacterias.setUI(new BasicComboBoxUI() {
            protected JButton createArrowButton() {
                return new JButton() {
                    public int getWidth() {
                        return 0;
                    }
                };
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        jPanel9.add(jPanel16, gridBagConstraints);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        jPanel3.setForeground(new java.awt.Color(255, 255, 255));
        jPanel3.setFocusable(false);
        jPanel3.setPreferredSize(new java.awt.Dimension(172, 60));
        jPanel3.setLayout(new java.awt.GridBagLayout());

        cajaGermenes.setBackground(new java.awt.Color(255, 255, 255));
        cajaGermenes.setBorder(null);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 60;
        gridBagConstraints.ipady = 40;
        jPanel3.add(cajaGermenes, gridBagConstraints);

        etiquetaMenorGermenes.setText("MENOR A 10");
        etiquetaMenorGermenes.setFocusable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        jPanel3.add(etiquetaMenorGermenes, gridBagConstraints);
        etiquetaMenorGermenes.setVisible(false);

        comboGermenesIn.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "UFC", "UFC/cm2" }));
        comboGermenesIn.setBorder(null);
        comboGermenesIn.setMaximumSize(new java.awt.Dimension(64, 16));
        comboGermenesIn.setMinimumSize(new java.awt.Dimension(64, 16));
        comboGermenesIn.setPreferredSize(new java.awt.Dimension(64, 16));
        comboGermenesIn.setUI(new BasicComboBoxUI() {
            protected JButton createArrowButton() {
                return new JButton() {
                    public int getWidth() {
                        return 0;
                    }
                };
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipady = 40;
        jPanel3.add(comboGermenesIn, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        jPanel9.add(jPanel3, gridBagConstraints);

        jPanel17.setBackground(new java.awt.Color(255, 255, 255));
        jPanel17.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        jPanel17.setForeground(new java.awt.Color(255, 255, 255));
        jPanel17.setFocusable(false);
        jPanel17.setPreferredSize(new java.awt.Dimension(172, 60));
        jPanel17.setLayout(new java.awt.GridBagLayout());

        cajaTotales.setBackground(new java.awt.Color(255, 255, 255));
        cajaTotales.setBorder(null);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 60;
        gridBagConstraints.ipady = 40;
        jPanel17.add(cajaTotales, gridBagConstraints);

        etiquetaMenorTotales.setText("MENOR A 10");
        etiquetaMenorTotales.setFocusable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        jPanel17.add(etiquetaMenorTotales, gridBagConstraints);
        etiquetaMenorTotales.setVisible(false);

        comboTotalesIn.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "UFC", "UFC/cm2" }));
        comboTotalesIn.setBorder(null);
        comboTotalesIn.setMaximumSize(new java.awt.Dimension(64, 16));
        comboTotalesIn.setMinimumSize(new java.awt.Dimension(64, 16));
        comboTotalesIn.setPreferredSize(new java.awt.Dimension(64, 16));
        comboTotalesIn.setUI(new BasicComboBoxUI() {
            protected JButton createArrowButton() {
                return new JButton() {
                    public int getWidth() {
                        return 0;
                    }
                };
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipady = 40;
        jPanel17.add(comboTotalesIn, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        jPanel9.add(jPanel17, gridBagConstraints);

        jPanel20.setBackground(new java.awt.Color(255, 255, 255));
        jPanel20.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        jPanel20.setForeground(new java.awt.Color(255, 255, 255));
        jPanel20.setFocusable(false);
        jPanel20.setPreferredSize(new java.awt.Dimension(172, 60));
        jPanel20.setLayout(new java.awt.GridBagLayout());

        cajaStaphilococos.setBackground(new java.awt.Color(255, 255, 255));
        cajaStaphilococos.setBorder(null);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 60;
        gridBagConstraints.ipady = 40;
        jPanel20.add(cajaStaphilococos, gridBagConstraints);

        etiquetaMenorStaphilococos.setText("MENOR A 10");
        etiquetaMenorStaphilococos.setFocusable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        jPanel20.add(etiquetaMenorStaphilococos, gridBagConstraints);
        etiquetaMenorTotales.setVisible(false);

        comboStaphilococosIn.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "UFC", "UFC/cm2" }));
        comboStaphilococosIn.setBorder(null);
        comboStaphilococosIn.setMaximumSize(new java.awt.Dimension(64, 16));
        comboStaphilococosIn.setMinimumSize(new java.awt.Dimension(64, 16));
        comboStaphilococosIn.setPreferredSize(new java.awt.Dimension(64, 16));
        comboStaphilococosIn.setUI(new BasicComboBoxUI() {
            protected JButton createArrowButton() {
                return new JButton() {
                    public int getWidth() {
                        return 0;
                    }
                };
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipady = 40;
        jPanel20.add(comboStaphilococosIn, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        jPanel9.add(jPanel20, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridheight = 7;
        jPanel1.add(jPanel9, gridBagConstraints);

        checkGermenes.setText("MENOR A 10");
        checkGermenes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkGermenesActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        jPanel1.add(checkGermenes, gridBagConstraints);

        checkColiformes.setText("MENOR A 10");
        checkColiformes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkColiformesActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        jPanel1.add(checkColiformes, gridBagConstraints);

        jPanel14.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 1, 1, 1, new java.awt.Color(0, 0, 0)));
        jPanel14.setFocusable(false);
        jPanel14.setMaximumSize(new java.awt.Dimension(170, 60));
        jPanel14.setMinimumSize(new java.awt.Dimension(170, 60));
        jPanel14.setPreferredSize(new java.awt.Dimension(170, 60));
        jPanel14.setLayout(new java.awt.GridBagLayout());

        etiquetaEnterobacterias.setBackground(new java.awt.Color(0, 0, 0));
        etiquetaEnterobacterias.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        etiquetaEnterobacterias.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        etiquetaEnterobacterias.setText("<html>ENTEROBACTERIAS</html>");
        etiquetaEnterobacterias.setToolTipText("");
        etiquetaEnterobacterias.setFocusable(false);
        etiquetaEnterobacterias.setMaximumSize(new java.awt.Dimension(170, 60));
        etiquetaEnterobacterias.setMinimumSize(new java.awt.Dimension(170, 60));
        etiquetaEnterobacterias.setPreferredSize(new java.awt.Dimension(140, 60));
        etiquetaEnterobacterias.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                etiquetaEnterobacteriasMousePressed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        jPanel14.add(etiquetaEnterobacterias, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        jPanel1.add(jPanel14, gridBagConstraints);

        jScrollPane13.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 1, new java.awt.Color(0, 0, 0)));
        jScrollPane13.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane13.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane13.setFocusable(false);
        jScrollPane13.setMinimumSize(new java.awt.Dimension(150, 60));
        jScrollPane13.setPreferredSize(new java.awt.Dimension(150, 60));

        area53.setEditable(false);
        area53.setColumns(20);
        area53.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        area53.setLineWrap(true);
        area53.setRows(5);
        area53.setText("ISO 10272-2:2017");
        area53.setWrapStyleWord(true);
        area53.setBorder(null);
        area53.setFocusable(false);
        area53.setMargin(new java.awt.Insets(7, 7, 7, 7));
        jScrollPane13.setViewportView(area53);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 20);
        jPanel1.add(jScrollPane13, gridBagConstraints);

        checkStaphilococos.setText("MENOR A 10");
        checkStaphilococos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkStaphilococosActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        jPanel1.add(checkStaphilococos, gridBagConstraints);

        jPanel18.setFocusable(false);
        jPanel18.setLayout(new java.awt.GridBagLayout());

        etiquetaLimiteColiformes.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        etiquetaLimiteColiformes.setText("Límite");
        etiquetaLimiteColiformes.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        jPanel18.add(etiquetaLimiteColiformes, gridBagConstraints);

        Limite10.setText("10");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        jPanel18.add(Limite10, gridBagConstraints);
        grupoTotales.add(Limite10);

        Limite200.setText("200");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        jPanel18.add(Limite200, gridBagConstraints);
        grupoTotales.add(Limite200);

        Limite50.setText("50");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        jPanel18.add(Limite50, gridBagConstraints);
        grupoTotales.add(Limite50);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 4;
        jPanel1.add(jPanel18, gridBagConstraints);

        jPanel19.setFocusable(false);
        jPanel19.setLayout(new java.awt.GridBagLayout());

        etiquetaLimiteGermenes.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        etiquetaLimiteGermenes.setText("Límite");
        etiquetaLimiteGermenes.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        etiquetaLimiteGermenes.setMaximumSize(new java.awt.Dimension(10000, 10000));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanel19.add(etiquetaLimiteGermenes, gridBagConstraints);

        Limite400.setText("400");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        jPanel19.add(Limite400, gridBagConstraints);
        grupoGermenes.add(Limite400);

        Limite3000.setText("3000");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        jPanel19.add(Limite3000, gridBagConstraints);
        grupoGermenes.add(Limite3000);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 3;
        jPanel1.add(jPanel19, gridBagConstraints);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1030, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 635, Short.MAX_VALUE)
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
        Map m = new HashMap();
        m.put("idmuestras", id);

        if (cajaGermenes.isEnabled()) {
            if (checkGermenes.isSelected()) {
                m.put("germenes", "MENOR A 10 " + comboGermenesIn.getSelectedItem());
            } else {
                m.put("germenes", cajaGermenes.getText() + " " + comboGermenesIn.getSelectedItem());
            }
        } else {
            m.put("germenes", "-2");
        }

        if (cajaTotales.isEnabled()) {
            if (checkColiformes.isSelected()) {
                m.put("coliformesTotales", "MENOR A 10 " + comboGermenesIn.getSelectedItem());
            } else {
                m.put("coliformesTotales", cajaTotales.getText() + " " + comboTotalesIn.getSelectedItem());
            }
        } else {
            m.put("coliformesTotales", "-2");
        }

        if (comboFecales.isEnabled()) {
            m.put("coliformesFecales", comboFecales.getSelectedItem().toString());
        } else {
            m.put("coliformesFecales", "-2");
        }

        if (comboEscherichia.isEnabled()) {
            m.put("escherichia", comboEscherichia.getSelectedItem().toString());
        } else {
            m.put("escherichia", "-2");
        }

        if (cajaStaphilococos.isEnabled()) {
            if (checkStaphilococos.isSelected()) {
                m.put("staphilococos", "MENOR A 10 " + comboStaphilococosIn.getSelectedItem());
            } else {
                m.put("staphilococos", cajaStaphilococos.getText() + " " + comboStaphilococosIn.getSelectedItem());
            }
        } else {
            m.put("staphilococos", "-2");
        }

        if (comboEnterobacterias.isEnabled()) {
            m.put("enterobacterias", comboEnterobacterias.getSelectedItem().toString());
        } else {
            m.put("enterobacterias", "-2");
        }

        m.put("procedencia", procedencia);
        boolean germenesActivo = false, totalesActivo = false;
        String limite = "Debe seleccionar un limite en";

        if (cajaGermenes.isEnabled()) {
            if (Limite3000.isSelected() || Limite400.isSelected()) {
            } else {
                limite += " germenes y";
                germenesActivo = true;
            }
        }
        if (cajaTotales.isEnabled()) {
            if (Limite10.isSelected() || Limite200.isSelected() || Limite50.isSelected()) {
            } else {
                limite += " coliformes totales";
                totalesActivo = true;
            }
        }
        if (limite.endsWith("y")) {
            limite.substring(0, limite.length() - 1);
        }
        if (germenesActivo || totalesActivo) {
            JOptionPane.showMessageDialog(null, limite);
        } else {
            int limiteGermenes = Limite3000.isSelected() ? 3000 : Limite400.isSelected() ? 400 : -1;
            int limiteTotales = Limite10.isSelected() ? 10 : Limite200.isSelected() ? 200 : Limite50.isSelected() ? 50 : -1;
            m.put("limiteGermenes", limiteGermenes);
            m.put("limiteTotales", limiteTotales);
            try {
                java.util.Date fm = cajaFechaAnalisis.getDate(); //obtener fecha
                Long dm = fm.getTime(); //sacar timepo
                java.sql.Date fechaAnalisis = new java.sql.Date(dm); //cast de fecha
                m.put("fechaAnalisis", fechaAnalisis);
                String observaciones = "";
                if (editar) {
                    observaciones = JOptionPane.showInputDialog("Digite la observación:", auxObservaciones);
                } else {
                    observaciones = JOptionPane.showInputDialog("Digite la observación:");
                }
                observaciones = observaciones.isBlank() ? "" : observaciones.trim().endsWith(".") ? observaciones : observaciones + ".";
                m.put("observaciones", observaciones);

                muestraRepository.guardarConclusion(crearConclusion(), id);
                muestraRepository.guardarFechaAnalisis(m);
                if (editar) {
                    File rv = new File(c.recuperarRutas("Reportes") + "\\" + pdf);
                    File rn = new File(c.recuperarRutas("Reportes") + "\\(BORRADO) " + pdf);
                    rv.renameTo(rn);
                    if (resultadosRepository.editarResultadosHisopadosAlliance(m)) {
                        muestraRepository.guardarObservaciones(observaciones, id);
                        this.dispose();
                        c.generarReporteHisopadosAlliance(id, procedencia);
                    }
                } else {
                    if (resultadosRepository.guardarResultadosHisopadosAlliance(m)) {
                        muestraRepository.guardarObservaciones(observaciones, id);
                        this.dispose();

                        c.generarReporteHisopadosAlliance(id, procedencia);
                    }
                }
            } catch (NullPointerException e) {
                JOptionPane.showMessageDialog(null, "Ingrese la fecha de análisis.");
            }
        }


    }

    private void checkGermenesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkGermenesActionPerformed
        if (checkGermenes.isSelected()) {
            etiquetaMenorGermenes.setVisible(true);
            cajaGermenes.setVisible(false);
        } else {
            etiquetaMenorGermenes.setVisible(false);
            cajaGermenes.setVisible(true);
        }
    }

    private void checkColiformesActionPerformed(java.awt.event.ActionEvent evt) {
        if (checkColiformes.isSelected()) {
            etiquetaMenorTotales.setVisible(true);
            cajaTotales.setVisible(false);
        } else {
            etiquetaMenorTotales.setVisible(false);
            cajaTotales.setVisible(true);
        }
    }

    private void checkStaphilococosActionPerformed(java.awt.event.ActionEvent evt) {
        if (checkStaphilococos.isSelected()) {
            etiquetaMenorStaphilococos.setVisible(true);
            cajaStaphilococos.setVisible(false);
        } else {
            etiquetaMenorStaphilococos.setVisible(false);
            cajaStaphilococos.setVisible(true);
        }
    }

    private void etiquetaGermenesMousePressed(java.awt.event.MouseEvent evt) {
        activarGermens = !activarGermens;
        cajaGermenes.setEnabled(activarGermens);
        comboGermenesIn.setEnabled(activarGermens);
        etiquetaMenorGermenes.setEnabled(activarGermens);
        area13.setEnabled(activarGermens);
        Limite400.setEnabled(activarGermens);
        Limite3000.setEnabled(activarGermens);
        etiquetaLimiteGermenes.setEnabled(activarGermens);
        checkGermenes.setEnabled(activarGermens);
        if (activarGermens) {
            jPanel7.setBackground(new Color(240, 240, 240));
        } else {
            jPanel7.setBackground(new Color(240, 100, 100));
        }
    }

    private void etiquetaTotalesMousePressed(java.awt.event.MouseEvent evt) {
        activarTotales = !activarTotales;
        cajaTotales.setEnabled(activarTotales);
        comboTotalesIn.setEnabled(activarTotales);
        etiquetaMenorTotales.setEnabled(activarTotales);
        area23.setEnabled(activarTotales);
        Limite10.setEnabled(activarTotales);
        Limite50.setEnabled(activarTotales);
        Limite200.setEnabled(activarTotales);
        etiquetaLimiteColiformes.setEnabled(activarTotales);
        checkColiformes.setEnabled(activarTotales);
        if (activarTotales) {
            jPanel2.setBackground(new Color(240, 240, 240));
        } else {
            jPanel2.setBackground(new Color(240, 100, 100));
        }
    }

    private void etiquetaFecalesMousePressed(java.awt.event.MouseEvent evt) {
        activarFecales = !activarFecales;
        comboFecales.setEnabled(activarFecales);
        area33.setEnabled(activarFecales);
        if (activarFecales) {
            jPanel8.setBackground(new Color(240, 240, 240));
        } else {
            jPanel8.setBackground(new Color(240, 100, 100));
        }
    }

    private void etiquetaEscherichiaMousePressed(java.awt.event.MouseEvent evt) {
        activarEscherichia = !activarEscherichia;
        comboEscherichia.setEnabled(activarEscherichia);
        area43.setEnabled(activarEscherichia);
        if (activarEscherichia) {
            jPanel11.setBackground(new Color(240, 240, 240));
        } else {
            jPanel11.setBackground(new Color(240, 100, 100));
        }
    }

    private void etiquetaEnterobacteriasMousePressed(java.awt.event.MouseEvent evt) {
        activarEnterobacterias = !activarEnterobacterias;
        comboEnterobacterias.setEnabled(activarEnterobacterias);
        area53.setEnabled(activarEnterobacterias);
        if (activarEnterobacterias) {
            jPanel14.setBackground(new Color(240, 240, 240));
        } else {
            jPanel14.setBackground(new Color(240, 100, 100));
        }
    }

    private void etiquetaStaphilococosMousePressed(java.awt.event.MouseEvent evt) {
        activarStaphilococos = !activarStaphilococos;
        cajaStaphilococos.setEnabled(activarStaphilococos);
        comboStaphilococosIn.setEnabled(activarStaphilococos);
        etiquetaMenorStaphilococos.setEnabled(activarStaphilococos);
        area63.setEnabled(activarStaphilococos);
        checkStaphilococos.setEnabled(activarStaphilococos);
        if (activarStaphilococos) {
            jPanel6.setBackground(new Color(240, 240, 240));
        } else {
            jPanel6.setBackground(new Color(240, 100, 100));
        }
    }

    private javax.swing.JRadioButton Limite10;
    private javax.swing.JRadioButton Limite200;
    private javax.swing.JRadioButton Limite3000;
    private javax.swing.JRadioButton Limite400;
    private javax.swing.JRadioButton Limite50;
    private javax.swing.JTextArea area13;
    private javax.swing.JTextArea area23;
    private javax.swing.JTextArea area33;
    private javax.swing.JTextArea area43;
    private javax.swing.JTextArea area53;
    private javax.swing.JTextArea area63;
    private javax.swing.JButton botonGenerar;
    private com.toedter.calendar.JDateChooser cajaFechaAnalisis;
    private javax.swing.JTextField cajaGermenes;
    private javax.swing.JTextField cajaStaphilococos;
    private javax.swing.JTextField cajaTotales;
    private javax.swing.JCheckBox checkColiformes;
    private javax.swing.JCheckBox checkGermenes;
    private javax.swing.JCheckBox checkStaphilococos;
    private javax.swing.JComboBox<String> comboEnterobacterias;
    private javax.swing.JComboBox<String> comboEscherichia;
    private javax.swing.JComboBox<String> comboFecales;
    private javax.swing.JComboBox<String> comboGermenesIn;
    private javax.swing.JComboBox<String> comboStaphilococosIn;
    private javax.swing.JComboBox<String> comboTotalesIn;
    private javax.swing.JLabel etiquetaEnterobacterias;
    private javax.swing.JLabel etiquetaEscherichia;
    private javax.swing.JLabel etiquetaFecales;
    private javax.swing.JLabel etiquetaGermenes;
    private javax.swing.JLabel etiquetaLimiteColiformes;
    private javax.swing.JLabel etiquetaLimiteGermenes;
    private javax.swing.JLabel etiquetaMenorGermenes;
    private javax.swing.JLabel etiquetaMenorStaphilococos;
    private javax.swing.JLabel etiquetaMenorTotales;
    private javax.swing.JLabel etiquetaStaphilococos;
    private javax.swing.JLabel etiquetaTitulo;
    private javax.swing.JLabel etiquetaTotales;
    private javax.swing.ButtonGroup grupoGermenes;
    private javax.swing.ButtonGroup grupoTotales;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane9;


    private String crearConclusion() {
        String conclusion = "Dado el recuento de";
        int limiteGermenes = -1;
        int limiteColiformesTotales = -1;
        limiteGermenes = Limite400.isSelected() ? 400 : Limite3000.isSelected() ? 3000 : 0;
        limiteColiformesTotales = Limite10.isSelected() ? 10 : Limite200.isSelected() ? 200 : 0;

        boolean germenes = true;
        if (cajaGermenes.isEnabled()) {
            if (checkGermenes.isSelected()) {
                germenes = true;
            } else {
                double valorGermenesAnalisis = Double.parseDouble(cajaGermenes.getText());
                germenes = valorGermenesAnalisis < limiteGermenes;
            }
        }

        boolean totales = true;

        if (cajaTotales.isEnabled()) {
            if (checkColiformes.isSelected()) {
                totales = true;
            } else {
                double valorTotalesAnalisis = Double.parseDouble(cajaTotales.getText());
                totales = valorTotalesAnalisis < limiteColiformesTotales;
            }
        }

        boolean fecales = true;
        if (comboFecales.isEnabled()) {
            fecales = comboFecales.getSelectedItem().toString().equals("Ausencia");
        }

        boolean escherichia = true;
        if (comboEscherichia.isEnabled()) {
            escherichia = comboEscherichia.getSelectedItem().toString().equals("Ausencia");
        }

        boolean staphilococos = true;

        if (cajaStaphilococos.isEnabled()) {
            if (checkStaphilococos.isSelected()) {
                staphilococos = true;
            } else {
                double valorStaphilococosAnalisis = Double.parseDouble(cajaStaphilococos.getText());
                double valorStaphilococosNormal = 10;
                staphilococos = valorStaphilococosAnalisis < valorStaphilococosNormal;
            }
        }

        boolean enterobacterias = true;
        if (comboEnterobacterias.isEnabled()) {
            enterobacterias = comboEnterobacterias.getSelectedItem().toString().equals("Ausencia");
        }

        if (germenes && totales && fecales && escherichia && staphilococos && enterobacterias) {
            conclusion = "La muestra analizada cumple con los limites especificados por el cliente.";
        } else {
            if (!germenes) {
                conclusion += " Germenes aerobios totales,";
            }
            if (!totales) {
                conclusion += " Coliformes totales,";
            }
            if (!fecales) {
                conclusion += " Coliformes fecales,";
            }

            if (!escherichia) {
                conclusion += " Escherichia coli,";
            }

            if (!staphilococos) {
                conclusion += " Staphilococos aureus coagulasa (+),";
            }

            if (!enterobacterias) {
                conclusion += " Enterobacterias,";
            }
            conclusion = conclusion.substring(0, conclusion.length() - 1);
            conclusion += " la muestra analizada no cumple con los limites especificados por el cliente";
        }
        conclusion = conclusion.replace("poes", "POES").replace("bpm", "BPM");
        conclusion = conclusion.isBlank() ? "" : conclusion.equals("-") ? "" : conclusion.trim().endsWith(".") ? conclusion : conclusion + ".";
        return conclusion;
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
