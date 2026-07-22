package org.ignaciorodriguez.vista;

import java.awt.Color;
import java.awt.Component;
import java.awt.Point;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.border.MatteBorder;
import javax.swing.plaf.basic.BasicComboBoxUI;
import org.ignaciorodriguez.modelo.Consultas;
import org.ignaciorodriguez.modelo.Resultados;
import org.ignaciorodriguez.repository.MuestraRepository;
import org.ignaciorodriguez.repository.ResultadoRepository;
import org.ignaciorodriguez.repository.VencimientoRepository;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.util.Map;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JComboBox;
import javax.swing.KeyStroke;
import javax.swing.JComponent;
import javax.swing.JTextField;

public class TablaMBAguaRecreacion extends javax.swing.JDialog {

    int id;
    String procedencia, pdf, auxCaracteres;
    Consultas c = Consultas.getInstancia();
    double[] ph;
    boolean editar, ingresoPh = false, activarGermenes = true, activarTotales = true,
            activarFecales = true, activarEscherichia = true, activarPseudomona = true,
            activarStaphilococos = true, activarStreptococos = true, activarShigella = true;
    MuestraRepository muestraRepository = new MuestraRepository();
    ResultadoRepository resultadoRepository = new ResultadoRepository();
    VencimientoRepository vencimientoRepository = new VencimientoRepository();

    public TablaMBAguaRecreacion(java.awt.Frame parent, boolean modal, int id, String procedencia, boolean editar, String pdf) {
        super(parent, modal);
        this.procedencia = procedencia;
        this.id = id;
        this.editar = editar;
        this.pdf = pdf.substring(1, pdf.length());
        initComponents();
        setTitle("ID " + id + ". " + muestraRepository.obtenerProcedencia(id) + ". Microbiológico de agua recreación");
        if (procedencia.contains("Municipio de Dina Huapi")) {
            checkDinaHuapi.setSelected(true);
        }
        ph = resultadoRepository.recuperarPhYCloro(id);
        DecimalFormat df = new DecimalFormat("#.##");
        if (ph != null) {
            if (ph[2] != -1) {
                cajaPh.setText(df.format(ph[2]).replaceAll(",", "."));
                this.ingresoPh = true;
            }
            if (ph[1] != -1) {
                cajaCloroTotal.setText(df.format(ph[1]).replaceAll(",", "."));
                this.ingresoPh = true;
            }
            if (ph[0] != -1) {
                cajaCloroLibre.setText(df.format(ph[0]).replaceAll(",", "."));
                this.ingresoPh = true;
            }
        }
        if (this.editar == true) {
            Map<String, String> resultados = resultadoRepository.recuperarResultadosMBAguaDeRecreacion(id);
            if (resultados == null) {
                if (!this.ingresoPh) {
                    this.editar = false;
                }
            } else {
                etiquetaTitulo.setText("Editar resultados del análisis");

                if (resultados.get("germenes").equals("-2")) {
                    etiquetaDeterminacionesGermenesMousePressed(click(etiquetaDeterminacionesGermenes));
                } else if (resultados.get("germenes").contains("-1")) {
                } else {

                    if (!resultados.get("germenes").equals("null")) {
                        campoGermenes.setText(resultados.get("germenes").substring(0, resultados.get("germenes").length() - 9).replaceAll("[^0-9?!\\.]", ""));
                    }
                }

                if (resultados.get("coliformesTotales").equals("-2")) {
                    etiquetaDeterminacionesTotalesMousePressed(click(etiquetaDeterminacionesTotales));
                } else if (resultados.get("coliformesTotales").contains("-1")) {
                } else {
                    if (!resultados.get("coliformesTotales").equals("null")) {
                        campoColiformesTotales.setText(resultados.get("coliformesTotales").substring(0, resultados.get("coliformesTotales").length() - 9).replaceAll("[^0-9?!\\.]", ""));
                        if (resultados.get("coliformesTotales").toLowerCase().startsWith("menor a")) {
                            comboColiformesTotales.setSelectedIndex(0);
                        } else if (resultados.get("coliformesTotales").toLowerCase().startsWith("mayor a")) {
                            comboColiformesTotales.setSelectedIndex(1);
                        } else {
                            comboColiformesTotales.setSelectedIndex(2);
                        }
                    }
                }

                if (resultados.get("coliformesFecales").equals("-2")) {
                    etiquetaDeterminacionesFecalesMousePressed(click(etiquetaDeterminacionesFecales));
                } else if (resultados.get("coliformesFecales").contains("-1")) {
                } else {
                    if (!resultados.get("coliformesFecales").equals("null")) {
                        if (resultados.get("coliformesFecales").toLowerCase().startsWith("menor a")) {
                            comboColiformesFecales.setSelectedIndex(0);
                        } else if (resultados.get("coliformesFecales").toLowerCase().startsWith("mayor a")) {
                            comboColiformesFecales.setSelectedIndex(1);
                        } else {
                            comboColiformesFecales.setSelectedIndex(2);
                        }
                        campoColiformesFecales.setText(resultados.get("coliformesFecales").substring(0, resultados.get("coliformesFecales").length() - 9).replaceAll("[^0-9?!\\.]", ""));
                    }
                }

                if (resultados.get("escherichia").equals("-2")) {
                    etiquetaDeterminacionesEscherichiaMousePressed(click(etiquetaDeterminacionesEscherichia));
                } else if (resultados.get("escherichia").contains("-1")) {
                } else {
                    if (!resultados.get("escherichia").equals("null")) {
                        if (resultados.get("escherichia").toLowerCase().startsWith("ausencia")) {
                            comboEscherichia.setSelectedIndex(0);
                        } else if (resultados.get("escherichia").toLowerCase().startsWith("presencia")) {
                            comboEscherichia.setSelectedIndex(1);
                        }
                    }
                }

                if (resultados.get("pseudomona").equals("-2")) {
                    etiquetaDeterminacionesPseudomonaMousePressed(click(etiquetaDeterminacionesPseudomona));
                } else if (resultados.get("pseudomona").contains("-1")) {
                } else {
                    if (!resultados.get("pseudomona").equals("null")) {
                        if (resultados.get("pseudomona").toLowerCase().startsWith("ausencia")) {
                            comboPseudomona.setSelectedIndex(0);
                        } else if (resultados.get("pseudomona").toLowerCase().startsWith("presencia")) {
                            comboPseudomona.setSelectedIndex(1);
                        }
                    }
                }

                if (resultados.get("staphilococos").equals("-2")) {
                    etiquetaDeterminacionesStaphilococosMousePressed(click(etiquetaDeterminacionesStaphilococos));
                } else if (resultados.get("staphilococos").contains("-1")) {
                } else {
                    if (!resultados.get("staphilococos").equals("null")) {
                        if (resultados.get("staphilococos").toLowerCase().startsWith("ausencia")) {
                            comboStaphilococos.setSelectedIndex(0);
                        } else if (resultados.get("staphilococos").toLowerCase().startsWith("presencia")) {
                            comboStaphilococos.setSelectedIndex(1);
                        }
                    }
                }

                if (resultados.get("streptococos").equals("-2")) {
                    etiquetaDeterminacionesStreptococosMousePressed(click(etiquetaDeterminacionesStreptococos));
                } else if (resultados.get("streptococos").contains("-1")) {
                } else {
                    if (!resultados.get("streptococos").equals("null")) {

                        if (resultados.get("streptococos").toLowerCase().startsWith("menor a")) {
                            comboStreptococos.setSelectedIndex(0);
                        } else if (resultados.get("streptococos").toLowerCase().startsWith("mayor a")) {
                            comboStreptococos.setSelectedIndex(1);
                        } else {
                            comboStreptococos.setSelectedIndex(2);
                        }
                        campoStreptococos.setText(resultados.get("streptococos").substring(0, resultados.get("streptococos").length() - 9).replaceAll("[^0-9?!\\.]", ""));
                    }
                }

                if (resultados.get("caracteresOrganolepticos").toLowerCase().startsWith("normales")) {
                    comboCaracteres.setSelectedItem(0);
                } else if (resultados.get("caracteresOrganolepticos").contains("-1")) {
                } else {
                    comboCaracteres.setSelectedIndex(1);
                    auxCaracteres = resultados.get("caracteresOrganolepticos");
                }

                if (resultados.get("shigella").equals("-2")) {
                    etiquetaDeterminacionesShigellaMousePressed(click(etiquetaDeterminacionesShigella));
                } else if (resultados.get("shigella").contains("-1")) {
                } else {
                    if (!resultados.get("shigella").equals("null")) {
                        if (resultados.get("shigella").toLowerCase().startsWith("ausencia")) {
                            comboShigella.setSelectedIndex(0);
                        } else if (resultados.get("shigella").toLowerCase().startsWith("presencia")) {
                            comboShigella.setSelectedIndex(1);
                        }
                    }
                }

                if (!resultados.get("fechaAnalisis").equals("null")) {
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
        jLabel3 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        etiquetaDeterminacionesStaphilococos = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        etiquetaDeterminacionesPseudomona = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        etiquetaDeterminacionesGermenes = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        etiquetaDeterminacionesEscherichia = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        area12 = new javax.swing.JTextArea();
        jScrollPane6 = new javax.swing.JScrollPane();
        area22 = new javax.swing.JTextArea();
        jScrollPane7 = new javax.swing.JScrollPane();
        area32 = new javax.swing.JTextArea();
        jScrollPane8 = new javax.swing.JScrollPane();
        area42 = new javax.swing.JTextArea();
        jScrollPane9 = new javax.swing.JScrollPane();
        area13 = new javax.swing.JTextArea();
        jScrollPane10 = new javax.swing.JScrollPane();
        area23 = new javax.swing.JTextArea();
        jScrollPane11 = new javax.swing.JScrollPane();
        area33 = new javax.swing.JTextArea();
        jScrollPane12 = new javax.swing.JScrollPane();
        area43 = new javax.swing.JTextArea();
        botonGenerar = new javax.swing.JButton();
        checkObtenido = new javax.swing.JCheckBox();
        etiquetaTitulo = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        etiquetaPh = new javax.swing.JLabel();
        cajaCloroTotal = new javax.swing.JTextField();
        cajaFechaAnalisis = new com.toedter.calendar.JDateChooser();
        cajaPh = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        comboCaracteres = new javax.swing.JComboBox<>();
        jLabel15 = new javax.swing.JLabel();
        cajaCloroLibre = new javax.swing.JTextField();
        checkVencimiento = new javax.swing.JCheckBox();
        jScrollPane13 = new javax.swing.JScrollPane();
        area44 = new javax.swing.JTextArea();
        jScrollPane14 = new javax.swing.JScrollPane();
        area45 = new javax.swing.JTextArea();
        jPanel13 = new javax.swing.JPanel();
        etiquetaDeterminacionesStreptococos = new javax.swing.JLabel();
        jScrollPane15 = new javax.swing.JScrollPane();
        area46 = new javax.swing.JTextArea();
        jScrollPane16 = new javax.swing.JScrollPane();
        area47 = new javax.swing.JTextArea();
        jPanel15 = new javax.swing.JPanel();
        etiquetaDeterminacionesFecales = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        etiquetaDeterminacionesTotales = new javax.swing.JLabel();
        jScrollPane17 = new javax.swing.JScrollPane();
        area24 = new javax.swing.JTextArea();
        jScrollPane18 = new javax.swing.JScrollPane();
        area25 = new javax.swing.JTextArea();
        jPanel14 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        comboStaphilococos = new javax.swing.JComboBox<>();
        etiquetaStaphilococos = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        comboStreptococos = new javax.swing.JComboBox<>();
        etiquetaStreptococos = new javax.swing.JLabel();
        campoStreptococos = new javax.swing.JTextField();
        jPanel17 = new javax.swing.JPanel();
        comboColiformesTotales = new javax.swing.JComboBox<>();
        etiquetaColiformesTotales = new javax.swing.JLabel();
        campoColiformesTotales = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        etiquetaGermenes = new javax.swing.JLabel();
        campoGermenes = new javax.swing.JTextField();
        jPanel9 = new javax.swing.JPanel();
        comboPseudomona = new javax.swing.JComboBox<>();
        etiquetaPseudomona = new javax.swing.JLabel();
        jPanel18 = new javax.swing.JPanel();
        comboColiformesFecales = new javax.swing.JComboBox<>();
        etiquetaColiformesFecales = new javax.swing.JLabel();
        campoColiformesFecales = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        comboEscherichia = new javax.swing.JComboBox<>();
        etiquetaEscherichia = new javax.swing.JLabel();
        jPanel19 = new javax.swing.JPanel();
        comboShigella = new javax.swing.JComboBox<>();
        etiquetaShigella = new javax.swing.JLabel();
        checkDinaHuapi = new javax.swing.JCheckBox();
        jPanel20 = new javax.swing.JPanel();
        etiquetaDeterminacionesShigella = new javax.swing.JLabel();
        jScrollPane19 = new javax.swing.JScrollPane();
        area26 = new javax.swing.JTextArea();
        jScrollPane20 = new javax.swing.JScrollPane();
        area27 = new javax.swing.JTextArea();

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
        gridBagConstraints.ipady = 20;
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
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.ipady = 20;
        gridBagConstraints.insets = new java.awt.Insets(20, 20, 0, 0);
        jPanel1.add(jLabel2, gridBagConstraints);
        jLabel2.setBorder(new MatteBorder(1,1,0,1, Color.black));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("<html>RECUENTO NORMAL</html>");
        jLabel3.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        jLabel3.setFocusable(false);
        jLabel3.setMinimumSize(new java.awt.Dimension(150, 30));
        jLabel3.setOpaque(true);
        jLabel3.setPreferredSize(new java.awt.Dimension(150, 10));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.ipady = 20;
        gridBagConstraints.insets = new java.awt.Insets(20, 0, 0, 0);
        jPanel1.add(jLabel3, gridBagConstraints);

        jPanel2.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 0, 1, new java.awt.Color(0, 0, 0)));
        jPanel2.setFocusable(false);
        jPanel2.setPreferredSize(new java.awt.Dimension(150, 10));
        jPanel2.setLayout(new java.awt.GridBagLayout());

        etiquetaDeterminacionesStaphilococos.setBackground(new java.awt.Color(0, 0, 0));
        etiquetaDeterminacionesStaphilococos.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        etiquetaDeterminacionesStaphilococos.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        etiquetaDeterminacionesStaphilococos.setText("<html>STAPHILOCOCCOS</html>");
        etiquetaDeterminacionesStaphilococos.setToolTipText("");
        etiquetaDeterminacionesStaphilococos.setMaximumSize(new java.awt.Dimension(170, 40));
        etiquetaDeterminacionesStaphilococos.setMinimumSize(new java.awt.Dimension(170, 40));
        etiquetaDeterminacionesStaphilococos.setPreferredSize(new java.awt.Dimension(170, 40));
        etiquetaDeterminacionesStaphilococos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                etiquetaDeterminacionesStaphilococosMousePressed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        jPanel2.add(etiquetaDeterminacionesStaphilococos, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.ipady = 30;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        jPanel1.add(jPanel2, gridBagConstraints);

        jPanel6.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 0, 1, new java.awt.Color(0, 0, 0)));
        jPanel6.setFocusable(false);
        jPanel6.setPreferredSize(new java.awt.Dimension(150, 10));
        jPanel6.setLayout(new java.awt.GridBagLayout());

        etiquetaDeterminacionesPseudomona.setBackground(new java.awt.Color(0, 0, 0));
        etiquetaDeterminacionesPseudomona.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        etiquetaDeterminacionesPseudomona.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        etiquetaDeterminacionesPseudomona.setText("<html>PSEUDOMONA AERUGINOSA</html>");
        etiquetaDeterminacionesPseudomona.setToolTipText("");
        etiquetaDeterminacionesPseudomona.setFocusable(false);
        etiquetaDeterminacionesPseudomona.setMaximumSize(new java.awt.Dimension(170, 40));
        etiquetaDeterminacionesPseudomona.setMinimumSize(new java.awt.Dimension(170, 40));
        etiquetaDeterminacionesPseudomona.setPreferredSize(new java.awt.Dimension(170, 40));
        etiquetaDeterminacionesPseudomona.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                etiquetaDeterminacionesPseudomonaMousePressed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        jPanel6.add(etiquetaDeterminacionesPseudomona, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.ipady = 30;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        jPanel1.add(jPanel6, gridBagConstraints);

        jPanel7.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 0, 1, new java.awt.Color(0, 0, 0)));
        jPanel7.setFocusable(false);
        jPanel7.setPreferredSize(new java.awt.Dimension(150, 10));
        jPanel7.setLayout(new java.awt.GridBagLayout());

        etiquetaDeterminacionesGermenes.setBackground(new java.awt.Color(0, 0, 0));
        etiquetaDeterminacionesGermenes.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        etiquetaDeterminacionesGermenes.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        etiquetaDeterminacionesGermenes.setText("<html>       GERMENES AEROBIOS TOTALES</html>");
        etiquetaDeterminacionesGermenes.setToolTipText("");
        etiquetaDeterminacionesGermenes.setMaximumSize(new java.awt.Dimension(170, 40));
        etiquetaDeterminacionesGermenes.setMinimumSize(new java.awt.Dimension(170, 40));
        etiquetaDeterminacionesGermenes.setPreferredSize(new java.awt.Dimension(170, 40));
        etiquetaDeterminacionesGermenes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                etiquetaDeterminacionesGermenesMousePressed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        jPanel7.add(etiquetaDeterminacionesGermenes, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.ipady = 30;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        jPanel1.add(jPanel7, gridBagConstraints);

        jPanel8.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 0, 1, new java.awt.Color(0, 0, 0)));
        jPanel8.setFocusable(false);
        jPanel8.setPreferredSize(new java.awt.Dimension(150, 10));
        jPanel8.setLayout(new java.awt.GridBagLayout());

        etiquetaDeterminacionesEscherichia.setBackground(new java.awt.Color(0, 0, 0));
        etiquetaDeterminacionesEscherichia.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        etiquetaDeterminacionesEscherichia.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        etiquetaDeterminacionesEscherichia.setText("<html>ESCHERICHIA COLI</html>");
        etiquetaDeterminacionesEscherichia.setToolTipText("");
        etiquetaDeterminacionesEscherichia.setFocusable(false);
        etiquetaDeterminacionesEscherichia.setMaximumSize(new java.awt.Dimension(170, 40));
        etiquetaDeterminacionesEscherichia.setMinimumSize(new java.awt.Dimension(170, 40));
        etiquetaDeterminacionesEscherichia.setPreferredSize(new java.awt.Dimension(170, 40));
        etiquetaDeterminacionesEscherichia.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                etiquetaDeterminacionesEscherichiaMousePressed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        jPanel8.add(etiquetaDeterminacionesEscherichia, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.ipady = 30;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        jPanel1.add(jPanel8, gridBagConstraints);

        jScrollPane2.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane2.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane2.setFocusable(false);
        jScrollPane2.setMinimumSize(new java.awt.Dimension(150, 60));
        jScrollPane2.setPreferredSize(new java.awt.Dimension(150, 60));

        area12.setEditable(false);
        area12.setColumns(20);
        area12.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        area12.setLineWrap(true);
        area12.setRows(5);
        area12.setText("Menor de 500 UFC/ml");
        area12.setToolTipText("");
        area12.setWrapStyleWord(true);
        area12.setAutoscrolls(false);
        area12.setBorder(null);
        area12.setFocusable(false);
        area12.setMargin(new java.awt.Insets(7, 7, 7, 7));
        jScrollPane2.setViewportView(area12);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.ipady = -20;
        jPanel1.add(jScrollPane2, gridBagConstraints);

        jScrollPane6.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        jScrollPane6.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane6.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane6.setFocusable(false);
        jScrollPane6.setMinimumSize(new java.awt.Dimension(150, 60));
        jScrollPane6.setPreferredSize(new java.awt.Dimension(150, 60));

        area22.setEditable(false);
        area22.setColumns(20);
        area22.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        area22.setLineWrap(true);
        area22.setRows(5);
        area22.setText("Menor a 3 NMP/100ml");
        area22.setWrapStyleWord(true);
        area22.setBorder(null);
        area22.setFocusable(false);
        area22.setMargin(new java.awt.Insets(7, 7, 7, 7));
        jScrollPane6.setViewportView(area22);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.ipady = -20;
        jPanel1.add(jScrollPane6, gridBagConstraints);

        jScrollPane7.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        jScrollPane7.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane7.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane7.setFocusable(false);
        jScrollPane7.setMinimumSize(new java.awt.Dimension(150, 60));
        jScrollPane7.setPreferredSize(new java.awt.Dimension(150, 60));

        area32.setEditable(false);
        area32.setColumns(20);
        area32.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        area32.setLineWrap(true);
        area32.setRows(5);
        area32.setText("Ausencia en 100 ml");
        area32.setWrapStyleWord(true);
        area32.setBorder(null);
        area32.setFocusable(false);
        area32.setMargin(new java.awt.Insets(7, 7, 7, 7));
        jScrollPane7.setViewportView(area32);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.ipady = -20;
        jPanel1.add(jScrollPane7, gridBagConstraints);

        jScrollPane8.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        jScrollPane8.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane8.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane8.setFocusable(false);
        jScrollPane8.setMinimumSize(new java.awt.Dimension(150, 60));
        jScrollPane8.setPreferredSize(new java.awt.Dimension(150, 60));

        area42.setEditable(false);
        area42.setColumns(20);
        area42.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        area42.setLineWrap(true);
        area42.setRows(5);
        area42.setText("Ausencia en 100 ml");
        area42.setWrapStyleWord(true);
        area42.setBorder(null);
        area42.setFocusable(false);
        jScrollPane8.setViewportView(area42);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.ipady = -20;
        jPanel1.add(jScrollPane8, gridBagConstraints);

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
        area13.setText("SMEW 22 nd. Edition APHA (2012) 9215 B");
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
        gridBagConstraints.ipady = -20;
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
        area23.setText("ISO 16266:2006");
        area23.setWrapStyleWord(true);
        area23.setBorder(null);
        area23.setFocusable(false);
        area23.setMargin(new java.awt.Insets(7, 7, 7, 7));
        jScrollPane10.setViewportView(area23);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.ipady = -20;
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
        gridBagConstraints.ipady = -20;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 20);
        jPanel1.add(jScrollPane11, gridBagConstraints);

        jScrollPane12.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        jScrollPane12.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane12.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane12.setFocusable(false);
        jScrollPane12.setMinimumSize(new java.awt.Dimension(150, 60));
        jScrollPane12.setPreferredSize(new java.awt.Dimension(150, 60));

        area43.setEditable(false);
        area43.setColumns(20);
        area43.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        area43.setLineWrap(true);
        area43.setRows(5);
        area43.setText("ISO 16266:2006");
        area43.setWrapStyleWord(true);
        area43.setBorder(null);
        area43.setFocusable(false);
        area43.setMargin(new java.awt.Insets(7, 7, 7, 7));
        jScrollPane12.setViewportView(area43);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.ipady = -20;
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
        gridBagConstraints.gridy = 11;
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

        checkObtenido.setSelected(true);
        checkObtenido.setText("Resultados predeterminado");
        checkObtenido.setToolTipText("");
        checkObtenido.setFocusable(false);
        checkObtenido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkObtenidoActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 11;
        jPanel1.add(checkObtenido, gridBagConstraints);

        etiquetaTitulo.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        etiquetaTitulo.setText("Agregar resultados de análisis");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        jPanel1.add(etiquetaTitulo, gridBagConstraints);

        jPanel11.setLayout(new java.awt.GridBagLayout());

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("% total de cloro");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        jPanel11.add(jLabel7, gridBagConstraints);

        jLabel8.setText("Fecha de analisis:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(20, 0, 0, 0);
        jPanel11.add(jLabel8, gridBagConstraints);

        etiquetaPh.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        etiquetaPh.setText("pH:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        jPanel11.add(etiquetaPh, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.ipadx = 120;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 120, 0, 0);
        jPanel11.add(cajaCloroTotal, gridBagConstraints);

        cajaFechaAnalisis.setDateFormatString("dd-MM-yyyy");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(20, 0, 0, 0);
        jPanel11.add(cajaFechaAnalisis, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 120;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 40, 0, 20);
        jPanel11.add(cajaPh, gridBagConstraints);

        jPanel5.setLayout(new java.awt.GridBagLayout());

        jLabel6.setText("<html>Caracteres organolepticos:</html>");
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
        jPanel11.add(jPanel5, gridBagConstraints);

        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("% libre de cloro");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        jPanel11.add(jLabel15, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.ipadx = 120;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 120, 0, 0);
        jPanel11.add(cajaCloroLibre, gridBagConstraints);

        checkVencimiento.setText("Poner vencimiento");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        jPanel11.add(checkVencimiento, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
        jPanel1.add(jPanel11, gridBagConstraints);

        jScrollPane13.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        jScrollPane13.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane13.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane13.setFocusable(false);
        jScrollPane13.setMinimumSize(new java.awt.Dimension(150, 60));
        jScrollPane13.setPreferredSize(new java.awt.Dimension(150, 60));

        area44.setEditable(false);
        area44.setColumns(20);
        area44.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        area44.setLineWrap(true);
        area44.setRows(5);
        area44.setText("Ausencia en 100 ml");
        area44.setWrapStyleWord(true);
        area44.setBorder(null);
        area44.setFocusable(false);
        jScrollPane13.setViewportView(area44);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.ipady = -20;
        jPanel1.add(jScrollPane13, gridBagConstraints);

        jScrollPane14.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        jScrollPane14.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane14.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane14.setFocusable(false);
        jScrollPane14.setMinimumSize(new java.awt.Dimension(150, 60));
        jScrollPane14.setPreferredSize(new java.awt.Dimension(150, 60));

        area45.setEditable(false);
        area45.setColumns(20);
        area45.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        area45.setLineWrap(true);
        area45.setRows(5);
        area45.setText("ISO 16266:2006");
        area45.setWrapStyleWord(true);
        area45.setBorder(null);
        area45.setFocusable(false);
        area45.setMargin(new java.awt.Insets(7, 7, 7, 7));
        jScrollPane14.setViewportView(area45);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.ipady = -20;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 20);
        jPanel1.add(jScrollPane14, gridBagConstraints);

        jPanel13.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 0, 1, new java.awt.Color(0, 0, 0)));
        jPanel13.setFocusable(false);
        jPanel13.setPreferredSize(new java.awt.Dimension(150, 10));
        jPanel13.setLayout(new java.awt.GridBagLayout());

        etiquetaDeterminacionesStreptococos.setBackground(new java.awt.Color(0, 0, 0));
        etiquetaDeterminacionesStreptococos.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        etiquetaDeterminacionesStreptococos.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        etiquetaDeterminacionesStreptococos.setText("<html>ENTEROCOCCUS </html>");
        etiquetaDeterminacionesStreptococos.setToolTipText("");
        etiquetaDeterminacionesStreptococos.setFocusable(false);
        etiquetaDeterminacionesStreptococos.setMaximumSize(new java.awt.Dimension(170, 40));
        etiquetaDeterminacionesStreptococos.setMinimumSize(new java.awt.Dimension(170, 40));
        etiquetaDeterminacionesStreptococos.setPreferredSize(new java.awt.Dimension(170, 40));
        etiquetaDeterminacionesStreptococos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                etiquetaDeterminacionesStreptococosMousePressed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        jPanel13.add(etiquetaDeterminacionesStreptococos, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.ipady = 30;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        jPanel1.add(jPanel13, gridBagConstraints);

        jScrollPane15.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        jScrollPane15.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane15.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane15.setFocusable(false);
        jScrollPane15.setMinimumSize(new java.awt.Dimension(150, 60));
        jScrollPane15.setPreferredSize(new java.awt.Dimension(150, 60));

        area46.setEditable(false);
        area46.setColumns(20);
        area46.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        area46.setLineWrap(true);
        area46.setRows(5);
        area46.setText("Menor 1.1 NMP/100ml");
        area46.setWrapStyleWord(true);
        area46.setBorder(null);
        area46.setFocusable(false);
        jScrollPane15.setViewportView(area46);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.ipady = -20;
        jPanel1.add(jScrollPane15, gridBagConstraints);

        jScrollPane16.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        jScrollPane16.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane16.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane16.setFocusable(false);
        jScrollPane16.setMinimumSize(new java.awt.Dimension(150, 60));
        jScrollPane16.setPreferredSize(new java.awt.Dimension(150, 60));

        area47.setEditable(false);
        area47.setColumns(20);
        area47.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        area47.setLineWrap(true);
        area47.setRows(5);
        area47.setText("SMEWW 22nd. Edition. APHA (2012) 9221 B");
        area47.setWrapStyleWord(true);
        area47.setBorder(null);
        area47.setFocusable(false);
        area47.setMargin(new java.awt.Insets(7, 7, 7, 7));
        jScrollPane16.setViewportView(area47);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.ipady = -20;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 20);
        jPanel1.add(jScrollPane16, gridBagConstraints);

        jPanel15.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 0, 1, new java.awt.Color(0, 0, 0)));
        jPanel15.setFocusable(false);
        jPanel15.setPreferredSize(new java.awt.Dimension(150, 10));
        jPanel15.setLayout(new java.awt.GridBagLayout());

        etiquetaDeterminacionesFecales.setBackground(new java.awt.Color(0, 0, 0));
        etiquetaDeterminacionesFecales.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        etiquetaDeterminacionesFecales.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        etiquetaDeterminacionesFecales.setText("<html>COLIFORMES FECALES</html>");
        etiquetaDeterminacionesFecales.setToolTipText("");
        etiquetaDeterminacionesFecales.setFocusable(false);
        etiquetaDeterminacionesFecales.setMaximumSize(new java.awt.Dimension(170, 40));
        etiquetaDeterminacionesFecales.setMinimumSize(new java.awt.Dimension(170, 40));
        etiquetaDeterminacionesFecales.setPreferredSize(new java.awt.Dimension(170, 40));
        etiquetaDeterminacionesFecales.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                etiquetaDeterminacionesFecalesMousePressed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        jPanel15.add(etiquetaDeterminacionesFecales, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.ipady = 30;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        jPanel1.add(jPanel15, gridBagConstraints);

        jPanel16.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 0, 1, new java.awt.Color(0, 0, 0)));
        jPanel16.setFocusable(false);
        jPanel16.setPreferredSize(new java.awt.Dimension(150, 10));
        jPanel16.setLayout(new java.awt.GridBagLayout());

        etiquetaDeterminacionesTotales.setBackground(new java.awt.Color(0, 0, 0));
        etiquetaDeterminacionesTotales.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        etiquetaDeterminacionesTotales.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        etiquetaDeterminacionesTotales.setText("<html>COLIFORMES TOTALES</html>");
        etiquetaDeterminacionesTotales.setToolTipText("");
        etiquetaDeterminacionesTotales.setMaximumSize(new java.awt.Dimension(170, 40));
        etiquetaDeterminacionesTotales.setMinimumSize(new java.awt.Dimension(170, 40));
        etiquetaDeterminacionesTotales.setPreferredSize(new java.awt.Dimension(170, 40));
        etiquetaDeterminacionesTotales.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                etiquetaDeterminacionesTotalesMousePressed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        jPanel16.add(etiquetaDeterminacionesTotales, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.ipady = 30;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        jPanel1.add(jPanel16, gridBagConstraints);

        jScrollPane17.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        jScrollPane17.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane17.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane17.setFocusable(false);
        jScrollPane17.setMinimumSize(new java.awt.Dimension(150, 60));
        jScrollPane17.setPreferredSize(new java.awt.Dimension(150, 60));

        area24.setEditable(false);
        area24.setColumns(20);
        area24.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        area24.setLineWrap(true);
        area24.setRows(5);
        area24.setText("Menor a 1.1 NMP/100ml");
        area24.setWrapStyleWord(true);
        area24.setBorder(null);
        area24.setFocusable(false);
        area24.setMargin(new java.awt.Insets(7, 7, 7, 7));
        jScrollPane17.setViewportView(area24);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.ipady = -20;
        jPanel1.add(jScrollPane17, gridBagConstraints);

        jScrollPane18.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        jScrollPane18.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane18.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane18.setFocusable(false);
        jScrollPane18.setMinimumSize(new java.awt.Dimension(150, 60));
        jScrollPane18.setPreferredSize(new java.awt.Dimension(150, 60));

        area25.setEditable(false);
        area25.setColumns(20);
        area25.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        area25.setLineWrap(true);
        area25.setRows(5);
        area25.setText("SMEWW 22nd. Edition. APHA (2012) 9221 B");
        area25.setWrapStyleWord(true);
        area25.setBorder(null);
        area25.setFocusable(false);
        area25.setMargin(new java.awt.Insets(7, 7, 7, 7));
        jScrollPane18.setViewportView(area25);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.ipady = -20;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 20);
        jPanel1.add(jScrollPane18, gridBagConstraints);

        jPanel14.setMaximumSize(new java.awt.Dimension(229, 279));
        jPanel14.setLayout(new java.awt.GridBagLayout());

        jPanel12.setFocusable(false);
        jPanel12.setMinimumSize(new java.awt.Dimension(188, 1));
        jPanel12.setPreferredSize(new java.awt.Dimension(176, 61));
        jPanel12.setLayout(new java.awt.GridBagLayout());

        comboStaphilococos.setBackground(new java.awt.Color(204, 204, 204));
        comboStaphilococos.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ausencia", "Presencia" }));
        comboStaphilococos.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 0, new java.awt.Color(0, 0, 0)));
        comboStaphilococos.setMaximumSize(new java.awt.Dimension(70, 20));
        comboStaphilococos.setMinimumSize(new java.awt.Dimension(70, 20));
        comboStaphilococos.setNextFocusableComponent(comboPseudomona);
        comboStaphilococos.setPreferredSize(new java.awt.Dimension(70, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 40;
        gridBagConstraints.ipady = 20;
        jPanel12.add(comboStaphilococos, gridBagConstraints);
        comboStaphilococos.setUI(new BasicComboBoxUI() {
            protected JButton createArrowButton() {
                return new JButton() {
                    public int getWidth() {
                        return 0;
                    }
                };
            }
        });

        etiquetaStaphilococos.setBackground(new java.awt.Color(255, 255, 255));
        etiquetaStaphilococos.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        etiquetaStaphilococos.setText("en 100 ml");
        etiquetaStaphilococos.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        etiquetaStaphilococos.setFocusable(false);
        etiquetaStaphilococos.setOpaque(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 14;
        gridBagConstraints.ipady = 24;
        jPanel12.add(etiquetaStaphilococos, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.ipady = -20;
        jPanel14.add(jPanel12, gridBagConstraints);

        jPanel4.setFocusable(false);
        jPanel4.setLayout(new java.awt.GridBagLayout());

        comboStreptococos.setBackground(new java.awt.Color(204, 204, 204));
        comboStreptococos.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Menor a", "Mayor a", "" }));
        comboStreptococos.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 0, new java.awt.Color(0, 0, 0)));
        comboStreptococos.setMaximumSize(new java.awt.Dimension(57, 21));
        comboStreptococos.setMinimumSize(new java.awt.Dimension(57, 21));
        comboStreptococos.setNextFocusableComponent(campoStreptococos);
        comboStreptococos.setPreferredSize(new java.awt.Dimension(57, 21));
        comboStreptococos.setUI(new BasicComboBoxUI() {
            protected JButton createArrowButton() {
                return new JButton() {
                    public int getWidth() {
                        return 0;
                    }
                };
            }
        });
        comboStreptococos.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comboStreptococosItemStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.ipady = 19;
        jPanel4.add(comboStreptococos, gridBagConstraints);

        etiquetaStreptococos.setBackground(new java.awt.Color(255, 255, 255));
        etiquetaStreptococos.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        etiquetaStreptococos.setText("NMP/100 ml");
        etiquetaStreptococos.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        etiquetaStreptococos.setFocusable(false);
        etiquetaStreptococos.setOpaque(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 16;
        gridBagConstraints.ipady = 24;
        jPanel4.add(etiquetaStreptococos, gridBagConstraints);

        campoStreptococos.setText("3");
        campoStreptococos.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 0, new java.awt.Color(0, 0, 0)));
        campoStreptococos.setMargin(new java.awt.Insets(20, 20, 20, 20));
        campoStreptococos.setMaximumSize(new java.awt.Dimension(31, 40));
        campoStreptococos.setMinimumSize(new java.awt.Dimension(31, 40));
        campoStreptococos.setNextFocusableComponent(comboPseudomona);
        campoStreptococos.setPreferredSize(new java.awt.Dimension(31, 40));
        campoStreptococos.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                campoStreptococosCaretUpdate(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        jPanel4.add(campoStreptococos, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        jPanel14.add(jPanel4, gridBagConstraints);

        jPanel17.setFocusable(false);
        jPanel17.setLayout(new java.awt.GridBagLayout());

        comboColiformesTotales.setBackground(new java.awt.Color(204, 204, 204));
        comboColiformesTotales.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Menor a", "Mayor a", " " }));
        comboColiformesTotales.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 0, new java.awt.Color(0, 0, 0)));
        comboColiformesTotales.setMaximumSize(new java.awt.Dimension(57, 21));
        comboColiformesTotales.setMinimumSize(new java.awt.Dimension(57, 21));
        comboColiformesTotales.setNextFocusableComponent(campoStreptococos);
        comboColiformesTotales.setPreferredSize(new java.awt.Dimension(57, 21));
        comboColiformesTotales.setUI(new BasicComboBoxUI() {
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
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.ipady = 19;
        jPanel17.add(comboColiformesTotales, gridBagConstraints);

        etiquetaColiformesTotales.setBackground(new java.awt.Color(255, 255, 255));
        etiquetaColiformesTotales.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        etiquetaColiformesTotales.setText("NMP/100 ml");
        etiquetaColiformesTotales.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        etiquetaColiformesTotales.setFocusable(false);
        etiquetaColiformesTotales.setOpaque(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 16;
        gridBagConstraints.ipady = 24;
        jPanel17.add(etiquetaColiformesTotales, gridBagConstraints);

        campoColiformesTotales.setText("1.1");
        campoColiformesTotales.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 0, new java.awt.Color(0, 0, 0)));
        campoColiformesTotales.setMargin(new java.awt.Insets(20, 20, 20, 20));
        campoColiformesTotales.setMaximumSize(new java.awt.Dimension(31, 40));
        campoColiformesTotales.setMinimumSize(new java.awt.Dimension(31, 40));
        campoColiformesTotales.setNextFocusableComponent(comboPseudomona);
        campoColiformesTotales.setPreferredSize(new java.awt.Dimension(31, 40));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        jPanel17.add(campoColiformesTotales, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        jPanel14.add(jPanel17, gridBagConstraints);

        jPanel3.setFocusable(false);
        jPanel3.setLayout(new java.awt.GridBagLayout());

        etiquetaGermenes.setBackground(new java.awt.Color(255, 255, 255));
        etiquetaGermenes.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        etiquetaGermenes.setText("UFC/100 ml");
        etiquetaGermenes.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        etiquetaGermenes.setFocusable(false);
        etiquetaGermenes.setOpaque(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 22;
        gridBagConstraints.ipady = 24;
        jPanel3.add(etiquetaGermenes, gridBagConstraints);

        campoGermenes.setText("500");
        campoGermenes.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 0, new java.awt.Color(0, 0, 0)));
        campoGermenes.setMaximumSize(new java.awt.Dimension(93, 39));
        campoGermenes.setMinimumSize(new java.awt.Dimension(93, 39));
        campoGermenes.setNextFocusableComponent(comboStreptococos);
        campoGermenes.setPreferredSize(new java.awt.Dimension(93, 39));
        campoGermenes.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                campoGermenesCaretUpdate(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        jPanel3.add(campoGermenes, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        jPanel14.add(jPanel3, gridBagConstraints);

        jPanel9.setFocusable(false);
        jPanel9.setMinimumSize(new java.awt.Dimension(188, 1));
        jPanel9.setPreferredSize(new java.awt.Dimension(176, 61));
        jPanel9.setLayout(new java.awt.GridBagLayout());

        comboPseudomona.setBackground(new java.awt.Color(204, 204, 204));
        comboPseudomona.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ausencia", "Presencia" }));
        comboPseudomona.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 0, new java.awt.Color(0, 0, 0)));
        comboPseudomona.setMaximumSize(new java.awt.Dimension(70, 20));
        comboPseudomona.setMinimumSize(new java.awt.Dimension(70, 20));
        comboPseudomona.setNextFocusableComponent(comboPseudomona);
        comboPseudomona.setPreferredSize(new java.awt.Dimension(70, 20));
        comboPseudomona.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comboPseudomonaItemStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 40;
        gridBagConstraints.ipady = 20;
        jPanel9.add(comboPseudomona, gridBagConstraints);
        comboPseudomona.setUI(new BasicComboBoxUI() {
            protected JButton createArrowButton() {
                return new JButton() {
                    public int getWidth() {
                        return 0;
                    }
                };
            }
        });

        etiquetaPseudomona.setBackground(new java.awt.Color(255, 255, 255));
        etiquetaPseudomona.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        etiquetaPseudomona.setText("en 100 ml");
        etiquetaPseudomona.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        etiquetaPseudomona.setFocusable(false);
        etiquetaPseudomona.setOpaque(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 14;
        gridBagConstraints.ipady = 24;
        jPanel9.add(etiquetaPseudomona, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.ipady = -20;
        jPanel14.add(jPanel9, gridBagConstraints);

        jPanel18.setFocusable(false);
        jPanel18.setLayout(new java.awt.GridBagLayout());

        comboColiformesFecales.setBackground(new java.awt.Color(204, 204, 204));
        comboColiformesFecales.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Menor a", "Mayor a", " " }));
        comboColiformesFecales.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 0, new java.awt.Color(0, 0, 0)));
        comboColiformesFecales.setMaximumSize(new java.awt.Dimension(57, 21));
        comboColiformesFecales.setMinimumSize(new java.awt.Dimension(57, 21));
        comboColiformesFecales.setNextFocusableComponent(campoStreptococos);
        comboColiformesFecales.setPreferredSize(new java.awt.Dimension(57, 21));
        comboColiformesFecales.setUI(new BasicComboBoxUI() {
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
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.ipady = 19;
        jPanel18.add(comboColiformesFecales, gridBagConstraints);

        etiquetaColiformesFecales.setBackground(new java.awt.Color(255, 255, 255));
        etiquetaColiformesFecales.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        etiquetaColiformesFecales.setText("NMP/100 ml");
        etiquetaColiformesFecales.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        etiquetaColiformesFecales.setFocusable(false);
        etiquetaColiformesFecales.setOpaque(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 16;
        gridBagConstraints.ipady = 24;
        jPanel18.add(etiquetaColiformesFecales, gridBagConstraints);

        campoColiformesFecales.setText("1.1");
        campoColiformesFecales.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 0, new java.awt.Color(0, 0, 0)));
        campoColiformesFecales.setMargin(new java.awt.Insets(20, 20, 20, 20));
        campoColiformesFecales.setMaximumSize(new java.awt.Dimension(31, 40));
        campoColiformesFecales.setMinimumSize(new java.awt.Dimension(31, 40));
        campoColiformesFecales.setNextFocusableComponent(comboPseudomona);
        campoColiformesFecales.setPreferredSize(new java.awt.Dimension(31, 40));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        jPanel18.add(campoColiformesFecales, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        jPanel14.add(jPanel18, gridBagConstraints);

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
        gridBagConstraints.ipadx = 21;
        gridBagConstraints.ipady = 20;
        gridBagConstraints.insets = new java.awt.Insets(20, 0, 0, 0);
        jPanel14.add(jLabel4, gridBagConstraints);

        jPanel10.setFocusable(false);
        jPanel10.setLayout(new java.awt.GridBagLayout());

        comboEscherichia.setBackground(new java.awt.Color(204, 204, 204));
        comboEscherichia.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ausencia", "Presencia" }));
        comboEscherichia.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 0, new java.awt.Color(0, 0, 0)));
        comboEscherichia.setMaximumSize(new java.awt.Dimension(70, 20));
        comboEscherichia.setMinimumSize(new java.awt.Dimension(70, 20));
        comboEscherichia.setNextFocusableComponent(comboPseudomona);
        comboEscherichia.setPreferredSize(new java.awt.Dimension(70, 20));
        comboEscherichia.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comboEscherichiaItemStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 40;
        gridBagConstraints.ipady = 20;
        jPanel10.add(comboEscherichia, gridBagConstraints);
        comboEscherichia.setUI(new BasicComboBoxUI() {
            protected JButton createArrowButton() {
                return new JButton() {
                    public int getWidth() {
                        return 0;
                    }
                };
            }
        });

        etiquetaEscherichia.setBackground(new java.awt.Color(255, 255, 255));
        etiquetaEscherichia.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        etiquetaEscherichia.setText("en 100 ml");
        etiquetaEscherichia.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        etiquetaEscherichia.setFocusable(false);
        etiquetaEscherichia.setOpaque(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 14;
        gridBagConstraints.ipady = 24;
        jPanel10.add(etiquetaEscherichia, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        jPanel14.add(jPanel10, gridBagConstraints);

        jPanel19.setFocusable(false);
        jPanel19.setMinimumSize(new java.awt.Dimension(188, 1));
        jPanel19.setPreferredSize(new java.awt.Dimension(176, 61));
        jPanel19.setLayout(new java.awt.GridBagLayout());

        comboShigella.setBackground(new java.awt.Color(204, 204, 204));
        comboShigella.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ausencia", "Presencia" }));
        comboShigella.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        comboShigella.setMaximumSize(new java.awt.Dimension(70, 20));
        comboShigella.setMinimumSize(new java.awt.Dimension(70, 20));
        comboShigella.setNextFocusableComponent(comboPseudomona);
        comboShigella.setPreferredSize(new java.awt.Dimension(70, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 40;
        gridBagConstraints.ipady = 20;
        jPanel19.add(comboShigella, gridBagConstraints);
        comboShigella.setUI(new BasicComboBoxUI() {
            protected JButton createArrowButton() {
                return new JButton() {
                    public int getWidth() {
                        return 0;
                    }
                };
            }
        });

        etiquetaShigella.setBackground(new java.awt.Color(255, 255, 255));
        etiquetaShigella.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        etiquetaShigella.setText("en 100 ml");
        etiquetaShigella.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 1, new java.awt.Color(0, 0, 0)));
        etiquetaShigella.setFocusable(false);
        etiquetaShigella.setOpaque(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 14;
        gridBagConstraints.ipady = 24;
        jPanel19.add(etiquetaShigella, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.ipady = -20;
        jPanel14.add(jPanel19, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridheight = 9;
        jPanel1.add(jPanel14, gridBagConstraints);

        checkDinaHuapi.setText("Conclusión Dina Huapi");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 11;
        jPanel1.add(checkDinaHuapi, gridBagConstraints);

        jPanel20.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel20.setFocusable(false);
        jPanel20.setPreferredSize(new java.awt.Dimension(150, 10));
        jPanel20.setLayout(new java.awt.GridBagLayout());

        etiquetaDeterminacionesShigella.setBackground(new java.awt.Color(0, 0, 0));
        etiquetaDeterminacionesShigella.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        etiquetaDeterminacionesShigella.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        etiquetaDeterminacionesShigella.setText("<html>SHIGELLA</html>");
        etiquetaDeterminacionesShigella.setToolTipText("");
        etiquetaDeterminacionesShigella.setFocusable(false);
        etiquetaDeterminacionesShigella.setMaximumSize(new java.awt.Dimension(170, 40));
        etiquetaDeterminacionesShigella.setMinimumSize(new java.awt.Dimension(170, 40));
        etiquetaDeterminacionesShigella.setPreferredSize(new java.awt.Dimension(170, 40));
        etiquetaDeterminacionesShigella.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                etiquetaDeterminacionesShigellaMousePressed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        jPanel20.add(etiquetaDeterminacionesShigella, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.ipady = 30;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        jPanel1.add(jPanel20, gridBagConstraints);

        jScrollPane19.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 1, 1, new java.awt.Color(0, 0, 0)));
        jScrollPane19.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane19.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane19.setFocusable(false);
        jScrollPane19.setMinimumSize(new java.awt.Dimension(150, 60));
        jScrollPane19.setPreferredSize(new java.awt.Dimension(150, 60));

        area26.setEditable(false);
        area26.setColumns(20);
        area26.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        area26.setLineWrap(true);
        area26.setRows(5);
        area26.setText("Ausencia en 100 ml");
        area26.setWrapStyleWord(true);
        area26.setBorder(null);
        area26.setFocusable(false);
        area26.setMargin(new java.awt.Insets(7, 7, 7, 7));
        jScrollPane19.setViewportView(area26);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.ipady = -20;
        jPanel1.add(jScrollPane19, gridBagConstraints);

        jScrollPane20.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 1, 1, new java.awt.Color(0, 0, 0)));
        jScrollPane20.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane20.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane20.setFocusable(false);
        jScrollPane20.setMinimumSize(new java.awt.Dimension(150, 60));
        jScrollPane20.setPreferredSize(new java.awt.Dimension(150, 60));

        area27.setEditable(false);
        area27.setColumns(20);
        area27.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        area27.setLineWrap(true);
        area27.setRows(5);
        area27.setText("ISO 21567:2005");
        area27.setWrapStyleWord(true);
        area27.setBorder(null);
        area27.setFocusable(false);
        area27.setMargin(new java.awt.Insets(7, 7, 7, 7));
        jScrollPane20.setViewportView(area27);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.ipady = -20;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 20);
        jPanel1.add(jScrollPane20, gridBagConstraints);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 791, Short.MAX_VALUE)
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

    private void checkObtenidoActionPerformed(java.awt.event.ActionEvent evt) {
        if (checkObtenido.isSelected()) {
            campoGermenes.setText("500");
            comboStreptococos.setSelectedItem("Menor a");
            campoStreptococos.setText("3");
            comboColiformesTotales.setSelectedItem("Menor a");
            campoColiformesTotales.setText("3");
            comboColiformesFecales.setSelectedItem("Menor a");
            campoColiformesFecales.setText("3");
            comboPseudomona.setSelectedItem("Ausencia");
            comboEscherichia.setSelectedItem("Ausencia");
            comboStaphilococos.setSelectedItem("Ausencia");
        } else {
        }
    }

    private void campoGermenesCaretUpdate(javax.swing.event.CaretEvent evt) {
        tildarCheck();
    }

    private void campoStreptococosCaretUpdate(javax.swing.event.CaretEvent evt) {
        tildarCheck();
    }

    private void comboStreptococosItemStateChanged(java.awt.event.ItemEvent evt) {
        tildarCheck();
    }

    private void comboEscherichiaItemStateChanged(java.awt.event.ItemEvent evt) {
        tildarCheck();
    }

    private void comboPseudomonaItemStateChanged(java.awt.event.ItemEvent evt) {
        tildarCheck();
    }


    private void etiquetaDeterminacionesGermenesMousePressed(java.awt.event.MouseEvent evt) {
        activarGermenes = !activarGermenes;
        campoGermenes.setEnabled(activarGermenes);
        area12.setEnabled(activarGermenes);
        area13.setEnabled(activarGermenes);
        etiquetaGermenes.setEnabled(activarGermenes);
        if (activarGermenes) {
            jPanel7.setBackground(new Color(240, 240, 240));
        } else {
            jPanel7.setBackground(new Color(240, 100, 100));
        }
    }

    private void etiquetaDeterminacionesTotalesMousePressed(java.awt.event.MouseEvent evt) {
        activarTotales = !activarTotales;
        campoColiformesTotales.setEnabled(activarTotales);
        area24.setEnabled(activarTotales);
        area25.setEnabled(activarTotales);
        etiquetaColiformesTotales.setEnabled(activarTotales);
        comboColiformesTotales.setEnabled(activarTotales);
        if (activarTotales) {
            jPanel16.setBackground(new Color(240, 240, 240));
        } else {
            jPanel16.setBackground(new Color(240, 100, 100));
        }
    }

    private void etiquetaDeterminacionesFecalesMousePressed(java.awt.event.MouseEvent evt) {
        activarFecales = !activarFecales;
        campoColiformesFecales.setEnabled(activarFecales);
        area46.setEnabled(activarFecales);
        area47.setEnabled(activarFecales);
        etiquetaColiformesFecales.setEnabled(activarFecales);
        comboColiformesFecales.setEnabled(activarFecales);
        if (activarFecales) {
            jPanel15.setBackground(new Color(240, 240, 240));
        } else {
            jPanel15.setBackground(new Color(240, 100, 100));
        }
    }

    private void etiquetaDeterminacionesEscherichiaMousePressed(java.awt.event.MouseEvent evt) {
        activarEscherichia = !activarEscherichia;
        area32.setEnabled(activarEscherichia);
        area33.setEnabled(activarEscherichia);
        etiquetaEscherichia.setEnabled(activarEscherichia);
        comboEscherichia.setEnabled(activarEscherichia);
        if (activarEscherichia) {
            jPanel8.setBackground(new Color(240, 240, 240));
        } else {
            jPanel8.setBackground(new Color(240, 100, 100));
        }
    }

    private void etiquetaDeterminacionesPseudomonaMousePressed(java.awt.event.MouseEvent evt) {
        activarPseudomona = !activarPseudomona;
        area42.setEnabled(activarPseudomona);
        area43.setEnabled(activarPseudomona);
        etiquetaPseudomona.setEnabled(activarPseudomona);
        comboPseudomona.setEnabled(activarPseudomona);
        if (activarPseudomona) {
            jPanel6.setBackground(new Color(240, 240, 240));
        } else {
            jPanel6.setBackground(new Color(240, 100, 100));
        }
    }

    private void etiquetaDeterminacionesStaphilococosMousePressed(java.awt.event.MouseEvent evt) {
        activarStaphilococos = !activarStaphilococos;
        area44.setEnabled(activarStaphilococos);
        area45.setEnabled(activarStaphilococos);
        etiquetaStaphilococos.setEnabled(activarStaphilococos);
        comboStaphilococos.setEnabled(activarStaphilococos);
        if (activarStaphilococos) {
            jPanel2.setBackground(new Color(240, 240, 240));
        } else {
            jPanel2.setBackground(new Color(240, 100, 100));
        }
    }

    private void etiquetaDeterminacionesStreptococosMousePressed(java.awt.event.MouseEvent evt) {
        activarStreptococos = !activarStreptococos;
        campoStreptococos.setEnabled(activarStreptococos);
        area22.setEnabled(activarStreptococos);
        area23.setEnabled(activarStreptococos);
        etiquetaStreptococos.setEnabled(activarStreptococos);
        comboStreptococos.setEnabled(activarStreptococos);
        if (activarStreptococos) {
            jPanel13.setBackground(new Color(240, 240, 240));
        } else {
            jPanel13.setBackground(new Color(240, 100, 100));
        }
    }

    private void etiquetaDeterminacionesShigellaMousePressed(java.awt.event.MouseEvent evt) {
        activarShigella = !activarShigella;
        area26.setEnabled(activarShigella);
        area27.setEnabled(activarShigella);
        etiquetaShigella.setEnabled(activarShigella);
        comboShigella.setEnabled(activarShigella);
        if (activarShigella) {
            jPanel20.setBackground(new Color(240, 240, 240));
        } else {
            jPanel20.setBackground(new Color(240, 100, 100));
        }
    }

    private javax.swing.JTextArea area12;
    private javax.swing.JTextArea area13;
    private javax.swing.JTextArea area22;
    private javax.swing.JTextArea area23;
    private javax.swing.JTextArea area24;
    private javax.swing.JTextArea area25;
    private javax.swing.JTextArea area26;
    private javax.swing.JTextArea area27;
    private javax.swing.JTextArea area32;
    private javax.swing.JTextArea area33;
    private javax.swing.JTextArea area42;
    private javax.swing.JTextArea area43;
    private javax.swing.JTextArea area44;
    private javax.swing.JTextArea area45;
    private javax.swing.JTextArea area46;
    private javax.swing.JTextArea area47;
    private javax.swing.JButton botonGenerar;
    private javax.swing.JTextField cajaCloroLibre;
    private javax.swing.JTextField cajaCloroTotal;
    private com.toedter.calendar.JDateChooser cajaFechaAnalisis;
    private javax.swing.JTextField cajaPh;
    private javax.swing.JTextField campoColiformesFecales;
    private javax.swing.JTextField campoColiformesTotales;
    private javax.swing.JTextField campoGermenes;
    private javax.swing.JTextField campoStreptococos;
    private javax.swing.JCheckBox checkDinaHuapi;
    private javax.swing.JCheckBox checkObtenido;
    private javax.swing.JCheckBox checkVencimiento;
    private javax.swing.JComboBox<String> comboCaracteres;
    private javax.swing.JComboBox<String> comboColiformesFecales;
    private javax.swing.JComboBox<String> comboColiformesTotales;
    private javax.swing.JComboBox<String> comboEscherichia;
    private javax.swing.JComboBox<String> comboPseudomona;
    private javax.swing.JComboBox<String> comboShigella;
    private javax.swing.JComboBox<String> comboStaphilococos;
    private javax.swing.JComboBox<String> comboStreptococos;
    private javax.swing.JLabel etiquetaColiformesFecales;
    private javax.swing.JLabel etiquetaColiformesTotales;
    private javax.swing.JLabel etiquetaDeterminacionesEscherichia;
    private javax.swing.JLabel etiquetaDeterminacionesFecales;
    private javax.swing.JLabel etiquetaDeterminacionesGermenes;
    private javax.swing.JLabel etiquetaDeterminacionesPseudomona;
    private javax.swing.JLabel etiquetaDeterminacionesShigella;
    private javax.swing.JLabel etiquetaDeterminacionesStaphilococos;
    private javax.swing.JLabel etiquetaDeterminacionesStreptococos;
    private javax.swing.JLabel etiquetaDeterminacionesTotales;
    private javax.swing.JLabel etiquetaEscherichia;
    private javax.swing.JLabel etiquetaGermenes;
    private javax.swing.JLabel etiquetaPh;
    private javax.swing.JLabel etiquetaPseudomona;
    private javax.swing.JLabel etiquetaShigella;
    private javax.swing.JLabel etiquetaStaphilococos;
    private javax.swing.JLabel etiquetaStreptococos;
    private javax.swing.JLabel etiquetaTitulo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane15;
    private javax.swing.JScrollPane jScrollPane16;
    private javax.swing.JScrollPane jScrollPane17;
    private javax.swing.JScrollPane jScrollPane18;
    private javax.swing.JScrollPane jScrollPane19;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane20;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    // End of variables declaration//GEN-END:variables

    public void tildarCheck() {
        if (campoGermenes.getText().equals("500") && campoStreptococos.getText().equals("3")
                && comboPseudomona.getSelectedItem().equals("Ausencia")
                && comboEscherichia.getSelectedItem().equals("Ausencia")
                && String.valueOf(comboStreptococos.getSelectedItem()).equals("Menor a")) {
            checkObtenido.setSelected(true);
        } else {
            checkObtenido.setSelected(false);
        }
    }

    public void completarReporte() {
        Resultados r = new Resultados();
        String germenes = "-2";
        if (campoGermenes.isEnabled()) {
            germenes = campoGermenes.getText().toUpperCase() + " " + etiquetaGermenes.getText();
        }

        String coliformesTotales = "-2";
        if (campoColiformesTotales.isEnabled()) {
            coliformesTotales = comboColiformesTotales.getSelectedItem().toString().toUpperCase()
                    + " " + campoColiformesTotales.getText().toUpperCase() + " " + etiquetaColiformesTotales.getText();
        }

        String coliformesFecales = "-2";
        if (campoColiformesFecales.isEnabled()) {
            coliformesFecales = comboColiformesFecales.getSelectedItem().toString().toUpperCase()
                    + " " + campoColiformesFecales.getText().toUpperCase() + " " + etiquetaColiformesFecales.getText();
        }

        String escherichia = "-2";
        if (comboEscherichia.isEnabled()) {
            escherichia = String.valueOf(comboEscherichia.getSelectedItem()).toUpperCase() + " " + etiquetaEscherichia.getText();
        }
        String pseudomona = "-2";
        if (comboPseudomona.isEnabled()) {
            pseudomona = String.valueOf(comboPseudomona.getSelectedItem()).toUpperCase() + " " + etiquetaPseudomona.getText();
        }

        String shigella = "-2";
        if (comboShigella.isEnabled()) {
            shigella = String.valueOf(comboShigella.getSelectedItem()).toUpperCase() + " " + etiquetaShigella.getText();
        }

        String ph = String.valueOf(cajaPh.getText());
        String cloro = String.valueOf(cajaCloroTotal.getText());
        String caracteres = String.valueOf(comboCaracteres.getSelectedItem().toString());
        String idmuestra = String.valueOf(id);
        String[] valores = {idmuestra, germenes, coliformesTotales, coliformesFecales, escherichia, pseudomona, "", ph, cloro, caracteres, "", shigella};
        r.setResultadoMB(valores);
        r.setCloroLibre(Double.parseDouble(cajaCloroLibre.getText()));
        String staphilococos = "-2";
        if (comboStaphilococos.isEnabled()) {
            staphilococos = comboStaphilococos.getSelectedItem().toString().toUpperCase() + " " + etiquetaStaphilococos.getText();
        }
        r.setStaphilococos(staphilococos);

        String streptococos = "-2";
        if (campoStreptococos.isEnabled()) {
            streptococos = comboStreptococos.getSelectedItem().toString().toUpperCase() + " " + campoStreptococos.getText() + " "
                    + etiquetaStreptococos.getText();
        }
        r.setStreptococos(streptococos);
        r.setTipo("Microbiológico de agua");
        java.sql.Date fechaAnalisis = null;
        try {
            java.util.Date fm = cajaFechaAnalisis.getDate(); //obtener fecha
            Long dm = fm.getTime(); //sacar timepo
            fechaAnalisis = new java.sql.Date(dm); //cast de fecha
            r.setFechaAnalisis(fechaAnalisis);
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
            muestraRepository.guardarObservaciones(valores[6], id);
            String conclusion = crearConclusion();

            muestraRepository.guardarConclusion(conclusion, id);

            int vencimiento = -1;
            if (checkVencimiento.isSelected()) {
                vencimiento = 1;
            } else {
                vencimiento = 0;
            }
            if (comboCaracteres.getSelectedItem().equals("Normales")) {
                caracteres = "Normales";
            } else {
                if (editar) {
                    caracteres = JOptionPane.showInputDialog("Digite los caracteres organolepticos: ", auxCaracteres);
                } else {
                    caracteres = JOptionPane.showInputDialog("Digite los caracteres organolepticos: ");
                }
            }
            r.setCaracteresOrgasnolepticos(caracteres);
            if (editar) {
                File rv = new File(c.recuperarRutas("Reportes") + "\\" + pdf);
                File rn = new File(c.recuperarRutas("Reportes") + "\\(BORRADO) " + pdf);
                rv.renameTo(rn);
                if (resultadoRepository.editarMBAguaDeRecreacion(r, vencimiento)) {
                    muestraRepository.guardarFechaAnalisisMBAGUA(r, id);
                    muestraRepository.guardarFechaAnalisis(r, id);
                    if (vencimientoRepository.checkearVencimiento(r)) {
                        vencimientoRepository.actualizarVencimiento(r);
                    } else {
                        vencimientoRepository.agregarVencimiento(r);
                    }
                    this.dispose();
                    c.generarReporteMBAguaDeRecreacion(id, procedencia);
                }

            } else {
                if (resultadoRepository.guardarResultadoMBAguaDeRecreacion(r, vencimiento)) {
                    muestraRepository.guardarFechaAnalisisMBAGUA(r, id);
                    muestraRepository.guardarFechaAnalisis(r, id);
                    if (vencimientoRepository.checkearVencimiento(r)) {
                        vencimientoRepository.actualizarVencimiento(r);
                    } else {
                        vencimientoRepository.agregarVencimiento(r);
                    }
                    muestraRepository.guardarObservaciones(valores[6], id);
                    this.dispose();
                    c.generarReporteMBAguaDeRecreacion(id, procedencia);
                }
            }
        } catch (java.lang.NullPointerException e) {
            JOptionPane.showMessageDialog(null, "Ingrese la fecha de análisis");
        }

    }

    private boolean esValido(JTextField campo, JComboBox<String> combo, double limite) {
        double valor = Double.parseDouble(campo.getText());
        String condicion = combo.getSelectedItem().toString();

        if (condicion.equals("Menor a")) {
            return valor <= limite;
        } else if (condicion.equals("Mayor a")) {
            return valor < limite; // Falso si es mayor o igual al límite
        }
        return valor < limite; // Lógica por defecto
    }

    public String crearConclusion() {
        boolean germenes = Integer.parseInt(campoGermenes.getText()) < 500;
        boolean coliformesTotales = esValido(campoColiformesTotales, comboColiformesTotales, 1.1);
        boolean coliformesFecales = esValido(campoColiformesFecales, comboColiformesFecales, 1.1);
        boolean streptococos = esValido(campoStreptococos, comboStreptococos, 3.0);

//        if (Double.parseDouble(campoColiformesTotales.getText()) == 1.1 && comboColiformesTotales.getSelectedItem().equals("Menor a")) {
//            coliformesTotales = true;
//        } else if (Double.parseDouble(campoColiformesTotales.getText()) >= 1.1 && comboColiformesTotales.getSelectedItem().equals("Mayor a")) {
//            coliformesTotales = false;
//        }
//        if (Double.parseDouble(campoColiformesFecales.getText()) == 1.1 && comboColiformesFecales.getSelectedItem().equals("Menor a")) {
//            coliformesFecales = true;
//        } else if (Double.parseDouble(campoColiformesFecales.getText()) >= 1.1 && comboColiformesFecales.getSelectedItem().equals("Mayor a")) {
//            coliformesFecales = false;
//        }
//        if (Double.parseDouble(campoStreptococos.getText()) == 3 && comboStreptococos.getSelectedItem().equals("Menor a")) {
//            streptococos = true;
//        } else if (Double.parseDouble(campoStreptococos.getText()) >= 3 && comboStreptococos.getSelectedItem().equals("Mayor a")) {
//            streptococos = false;
//        }
        boolean escherichia = !(comboEscherichia.getSelectedItem().toString().equals("Presencia"));
        boolean pseudomona = !(comboPseudomona.getSelectedItem().toString().equals("Presencia"));
        boolean staphilococos = !(comboStaphilococos.getSelectedItem().toString().equals("Presencia"));
        boolean shigella = !(comboShigella.getSelectedItem().toString().equals("Presencia"));
        String conclusion = "";
        boolean aux = germenes && coliformesTotales && coliformesFecales && escherichia && pseudomona && staphilococos && streptococos && shigella;
        if (aux) {
            conclusion = "En los parámetros analizados, la muestra analizada cumple"
                    + " con las especificaciones microbiológicas estipuladas por la "
                    + "Res. 4638 Ministerio Salud de la provincia de Rio Negro. ";
        } else {
            conclusion = "Dado el recuento de";
            if (!germenes) {
                conclusion += " Germenes aerobios totales,";
            }
            if (!coliformesTotales) {
                conclusion += " Coliformes totales,";
            }
            if (!coliformesFecales) {
                conclusion += " Coliformes fecales,";
            }
            if (!escherichia) {
                conclusion += " Escherichia coli,";
            }
            if (!pseudomona) {
                conclusion += " Pseudomona aeruginosa,";
            }
            if (!staphilococos) {
                conclusion += " Staphilococos aureus coagulasa (+),";
            }
            if (!streptococos) {
                conclusion += " Streptococos fecalis,";
            }
            if (!shigella) {
                conclusion += " Shigella,";
            }
            conclusion = conclusion.substring(0, conclusion.length() - 1);
            conclusion += " la muestra analizada no cumple con las especificaciones "
                    + "microbiológicas estipuladas por la Res. 4638 Ministerio Salud de la provincia de Rio Negro";
        }
        conclusion = conclusion.isBlank() ? "" : conclusion.trim().endsWith(".") ? conclusion : conclusion + ".";
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
