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
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.util.Map;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.KeyStroke;
import javax.swing.JComponent;

public class TablaMBAgua extends javax.swing.JDialog {

    int id;
    String procedencia, pdf, auxCaracteres;
    Consultas c = Consultas.getInstancia();
    boolean editar, ingresoPh, activarGermenes = true, activarTotales = true,
            activarFecales = true, activarEscherichia = true, activarPseudomona = true, activarMohos = true, activarShigella = true;
    double[] ph;
    private String aux;
    org.slf4j.Logger log;

    public TablaMBAgua(java.awt.Frame parent, boolean modal, int id, String procedencia, boolean editar, String pdf) {
        super(parent, modal);
        this.procedencia = procedencia;
        this.id = id;
        this.editar = editar;
        this.pdf = pdf.substring(1, pdf.length());
        initComponents();
        setTitle("ID " + id + ". " + c.obtenerProcedencia(id) + ". Microbiológico de agua código");
        jLabel13MousePressed(click(jLabel13));
        ph = c.recuperarPhYCloro(id);
        DecimalFormat df = new DecimalFormat("#.##");
        if (ph != null) {
            if (ph[2] != -1) {
                cajaPh.setText(df.format(ph[2]).replaceAll(",", "."));
                this.ingresoPh = true;
            }
            if (ph[0] != -1) {
                cajaCloro.setText(df.format(ph[0]).replaceAll(",", "."));
                this.ingresoPh = true;
            }
        }
        if (this.editar == true) {
            Map<String, String> resultados = c.recuperarResultadosMBAgua(id);
            if (resultados == null) {
                if (!this.ingresoPh) {
                    this.editar = false;
                }
            } else {
                etiquetaTitulo.setText("Editar resultados del análisis");
                if (!resultados.get("germenes").contains("null")) {
                    if (resultados.get("germenes").contains("-2")) {
                        jLabel10MousePressed(click(jLabel10));
                    } else {
                        aux = resultados.get("germenes").replaceAll("[^0-9?!\\.]", "");
                        campoGermenes.setText(aux.substring(0, aux.length() - 3));
                    }
                }

                if (!resultados.get("coliformesTotales").contains("null")) {
                    if (resultados.get("coliformesTotales").contains("-2")) {
                        jLabel12MousePressed(click(jLabel12));
                    } else if (resultados.get("coliformesTotales").contains("-1")) {
                    } else {
                        if (resultados.get("coliformesTotales").toUpperCase().startsWith("MENOR A")) {
                            comboColiformesTotales.setSelectedIndex(0);
                        } else if (resultados.get("coliformesTotales").contains("-1")) {
                        } else if (resultados.get("coliformesTotales").toUpperCase().startsWith("MAYOR A")) {
                            comboColiformesTotales.setSelectedIndex(1);
                        } else {
                            comboColiformesTotales.setSelectedIndex(2);
                        }
                        campoColiformesTotales.setText(resultados.get("coliformesTotales").substring(0, resultados.get("coliformesTotales").length() - 9).replaceAll("[^0-9?!\\.]", ""));
                    }
                }

                if (!resultados.get("coliformesFecales").contains("null")) {
                    if (resultados.get("coliformesFecales").contains("-2")) {
                        jLabel5MousePressed(click(jLabel5));
                    } else if (resultados.get("coliformesFecales").contains("-1")) {
                    } else {
                        if (resultados.get("coliformesFecales").toUpperCase().startsWith("MENOR A")) {
                            comboColiformesFecales.setSelectedIndex(0);
                        } else if (resultados.get("coliformesFecales").contains("-1")) {
                        } else if (resultados.get("coliformesFecales").toUpperCase().startsWith("MAYOR A")) {
                            comboColiformesFecales.setSelectedIndex(1);
                        } else {
                            comboColiformesFecales.setSelectedIndex(2);
                        }
                        aux = resultados.get("coliformesFecales").replaceAll("[^0-9?!\\.]", "");
                        campoColiformesFecales.setText(aux.substring(0, aux.length() - 3));
                    }
                }

                if (!resultados.get("escherichia").contains("null")) {
                    if (resultados.get("escherichia").contains("-2")) {
                        jLabel11MousePressed(click(jLabel11));
                    } else if (resultados.get("escherichia").contains("-1")) {
                    } else {
                        if (resultados.get("escherichia").toLowerCase().startsWith("ausencia")) {
                            comboEscherichia.setSelectedIndex(0);
                        } else if (resultados.get("escherichia").toLowerCase().startsWith("presencia")) {
                            comboEscherichia.setSelectedIndex(1);
                        }
                    }
                }

                if (!resultados.get("pseudomona").contains("null")) {
                    if (resultados.get("pseudomona").contains("-2")) {
                        jLabel9MousePressed(click(jLabel9));
                    } else if (resultados.get("pseudomona").contains("-1")) {
                    } else {
                        if (resultados.get("pseudomona").toLowerCase().startsWith("ausencia")) {
                            comboPseudomona.setSelectedIndex(0);
                        } else if (resultados.get("pseudomona").toLowerCase().startsWith("presencia")) {
                            comboPseudomona.setSelectedIndex(1);
                        }
                    }
                }

                if (!("" + resultados.get("caracteresOrganolepticos")).equals("null")) {
                    if (resultados.get("caracteresOrganolepticos").startsWith("Normales")) {
                        comboCaracteres.setSelectedItem(0);
                    } else if (resultados.get("caracteresOrganolepticos").contains("-1")) {
                    } else {
                        comboCaracteres.setSelectedIndex(1);
                        auxCaracteres = resultados.get("caracteresOrganolepticos");
                    }
                }

                if (!resultados.get("mohos").equals("null")) {
                    if (!resultados.get("mohos").equals("-2")) {
                        jLabel13MousePressed(click(jLabel13));
                        if (resultados.get("mohos").toLowerCase().startsWith("menor a")) {
                            comboMohos.setSelectedIndex(0);
                        } else if (resultados.get("mohos").toLowerCase().startsWith("mayor a")) {
                            comboMohos.setSelectedIndex(1);
                        } else {
                            comboMohos.setSelectedIndex(2);
                        }
                        aux = resultados.get("mohos").replaceAll("[^0-9?!\\.]", "");
                        campoMohos.setText(aux.substring(0, aux.length() - 3));
                    } else if (resultados.get("mohos").contains("-1")) {
                    }
                }



                if (!("" + resultados.get("mohosLimite")).contains("null")) {
                    if (resultados.get("mohosLimite").equals("1")) {
                        checkLimiteMohos.setSelected(true);
                    } else {
                        checkLimiteMohos.setSelected(false);
                    }
                }
                if (!("" + resultados.get("fechaAnalisis")).contains("null")) {
                    java.sql.Date fecha = java.sql.Date.valueOf(resultados.get("fechaAnalisis"));
                    java.util.Date utilFecha = null;
                    try {
                        utilFecha = new java.util.Date(fecha.getTime());
                    } catch (Exception e) {
                        System.err.println("error, " + e);
                    }
                    cajaFechaAnalisis.setDate(utilFecha);
                }

                if (!(resultados.get("shigella") == null) && !(resultados.get("shigella").contains("null"))) {
                    if (resultados.get("shigella").contains("-2")) {
                        jLabel14MousePressed(click(jLabel9));
                    } else if (resultados.get("shigella").contains("-1")) {
                    } else {
                        if (resultados.get("shigella").toLowerCase().startsWith("ausencia")) {
                            comboShigella.setSelectedIndex(0);
                        } else if (resultados.get("shigella").toLowerCase().startsWith("presencia")) {
                            comboShigella.setSelectedIndex(1);
                        }
                    }
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
        jLabel4 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
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
        jPanel3 = new javax.swing.JPanel();
        etiquetaGermenes = new javax.swing.JLabel();
        campoGermenes = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        comboColiformesFecales = new javax.swing.JComboBox<>();
        etiquetaColiformesFecales = new javax.swing.JLabel();
        campoColiformesFecales = new javax.swing.JTextField();
        jPanel9 = new javax.swing.JPanel();
        etiquetaPseudomona = new javax.swing.JLabel();
        comboPseudomona = new javax.swing.JComboBox<>();
        jPanel10 = new javax.swing.JPanel();
        comboEscherichia = new javax.swing.JComboBox<>();
        etiquetaEscherichia = new javax.swing.JLabel();
        etiquetaTitulo = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        etiquetaPh = new javax.swing.JLabel();
        cajaCloro = new javax.swing.JTextField();
        cajaFechaAnalisis = new com.toedter.calendar.JDateChooser();
        cajaPh = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        comboCaracteres = new javax.swing.JComboBox<>();
        checkPonerVencimiento = new javax.swing.JCheckBox();
        jPanel12 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jScrollPane13 = new javax.swing.JScrollPane();
        area24 = new javax.swing.JTextArea();
        jScrollPane14 = new javax.swing.JScrollPane();
        area25 = new javax.swing.JTextArea();
        jPanel13 = new javax.swing.JPanel();
        comboColiformesTotales = new javax.swing.JComboBox<>();
        etiquetaColiformesTotales = new javax.swing.JLabel();
        campoColiformesTotales = new javax.swing.JTextField();
        jScrollPane15 = new javax.swing.JScrollPane();
        area34 = new javax.swing.JTextArea();
        jPanel15 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jScrollPane16 = new javax.swing.JScrollPane();
        area35 = new javax.swing.JTextArea();
        jPanel16 = new javax.swing.JPanel();
        comboMohos = new javax.swing.JComboBox<>();
        etiquetaMohos = new javax.swing.JLabel();
        campoMohos = new javax.swing.JTextField();
        checkLimiteMohos = new javax.swing.JCheckBox();
        jPanel17 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jScrollPane17 = new javax.swing.JScrollPane();
        area36 = new javax.swing.JTextArea();
        jScrollPane18 = new javax.swing.JScrollPane();
        area37 = new javax.swing.JTextArea();
        jPanel14 = new javax.swing.JPanel();
        etiquetaShigella = new javax.swing.JLabel();
        comboShigella = new javax.swing.JComboBox<>();

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
        gridBagConstraints.gridx = 4;
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

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("<html>RECUENTO NORMAL</html>");
        jLabel3.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        jLabel3.setFocusable(false);
        jLabel3.setMinimumSize(new java.awt.Dimension(150, 30));
        jLabel3.setOpaque(true);
        jLabel3.setPreferredSize(new java.awt.Dimension(150, 10));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.ipady = 40;
        gridBagConstraints.insets = new java.awt.Insets(20, 0, 0, 0);
        jPanel1.add(jLabel3, gridBagConstraints);

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("<html>RECUENTO OBTENIDO</html>");
        jLabel4.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        jLabel4.setFocusable(false);
        jLabel4.setMinimumSize(new java.awt.Dimension(150, 30));
        jLabel4.setOpaque(true);
        jLabel4.setPreferredSize(new java.awt.Dimension(150, 10));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 29;
        gridBagConstraints.ipady = 40;
        gridBagConstraints.insets = new java.awt.Insets(20, 0, 0, 0);
        jPanel1.add(jLabel4, gridBagConstraints);

        jPanel2.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 0, 1, new java.awt.Color(0, 0, 0)));
        jPanel2.setFocusable(false);
        jPanel2.setPreferredSize(new java.awt.Dimension(150, 10));
        jPanel2.setLayout(new java.awt.GridBagLayout());

        jLabel5.setBackground(new java.awt.Color(0, 0, 0));
        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel5.setText("<html>COLIFORMES FECALES</html>");
        jLabel5.setToolTipText("");
        jLabel5.setMaximumSize(new java.awt.Dimension(170, 60));
        jLabel5.setMinimumSize(new java.awt.Dimension(170, 60));
        jLabel5.setPreferredSize(new java.awt.Dimension(170, 60));
        jLabel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel5MousePressed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        jPanel2.add(jLabel5, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.ipady = 50;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        jPanel1.add(jPanel2, gridBagConstraints);

        jPanel6.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 0, 1, new java.awt.Color(0, 0, 0)));
        jPanel6.setFocusable(false);
        jPanel6.setPreferredSize(new java.awt.Dimension(150, 10));
        jPanel6.setLayout(new java.awt.GridBagLayout());

        jLabel9.setBackground(new java.awt.Color(0, 0, 0));
        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel9.setText("<html>PSEUDOMONA AERUGINOSA</html>");
        jLabel9.setToolTipText("");
        jLabel9.setFocusable(false);
        jLabel9.setMaximumSize(new java.awt.Dimension(170, 60));
        jLabel9.setMinimumSize(new java.awt.Dimension(170, 60));
        jLabel9.setPreferredSize(new java.awt.Dimension(170, 60));
        jLabel9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel9MousePressed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        jPanel6.add(jLabel9, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.ipady = 50;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        jPanel1.add(jPanel6, gridBagConstraints);

        jPanel7.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 0, 1, new java.awt.Color(0, 0, 0)));
        jPanel7.setFocusable(false);
        jPanel7.setPreferredSize(new java.awt.Dimension(150, 10));
        jPanel7.setLayout(new java.awt.GridBagLayout());

        jLabel10.setBackground(new java.awt.Color(0, 0, 0));
        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel10.setText("<html>       GERMENES AEROBIOS TOTALES</html>");
        jLabel10.setToolTipText("");
        jLabel10.setMaximumSize(new java.awt.Dimension(170, 60));
        jLabel10.setMinimumSize(new java.awt.Dimension(170, 60));
        jLabel10.setPreferredSize(new java.awt.Dimension(170, 60));
        jLabel10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel10MousePressed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        jPanel7.add(jLabel10, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.ipady = 50;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        jPanel1.add(jPanel7, gridBagConstraints);

        jPanel8.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 0, 1, new java.awt.Color(0, 0, 0)));
        jPanel8.setFocusable(false);
        jPanel8.setPreferredSize(new java.awt.Dimension(150, 10));
        jPanel8.setLayout(new java.awt.GridBagLayout());

        jLabel11.setBackground(new java.awt.Color(0, 0, 0));
        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel11.setText("<html>ESCHERICHIA COLI</html>");
        jLabel11.setToolTipText("");
        jLabel11.setFocusable(false);
        jLabel11.setMaximumSize(new java.awt.Dimension(170, 60));
        jLabel11.setMinimumSize(new java.awt.Dimension(170, 60));
        jLabel11.setPreferredSize(new java.awt.Dimension(170, 60));
        jLabel11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel11MousePressed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        jPanel8.add(jLabel11, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.ipady = 50;
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
        area12.setText("Menor de 500 UFC/100 ml");
        area12.setToolTipText("");
        area12.setWrapStyleWord(true);
        area12.setAutoscrolls(false);
        area12.setBorder(null);
        area12.setFocusable(false);
        area12.setMargin(new java.awt.Insets(7, 7, 7, 7));
        jScrollPane2.setViewportView(area12);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.ipadx = 20;
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
        area22.setText("Menor a 1.1 NMP/100 \nml");
        area22.setWrapStyleWord(true);
        area22.setBorder(null);
        area22.setFocusable(false);
        area22.setMargin(new java.awt.Insets(7, 7, 7, 7));
        jScrollPane6.setViewportView(area22);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.ipadx = 20;
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
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.ipadx = 20;
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
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.ipadx = 20;
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
        gridBagConstraints.gridx = 4;
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
        area23.setText("SMEWW 22nd. Edition. APHA (2012) 9221 B");
        area23.setWrapStyleWord(true);
        area23.setBorder(null);
        area23.setFocusable(false);
        area23.setMargin(new java.awt.Insets(7, 7, 7, 7));
        jScrollPane10.setViewportView(area23);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 5;
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
        area33.setText("SMEWW 22nd. Edition. APHA (2012) 9221 F");
        area33.setWrapStyleWord(true);
        area33.setBorder(null);
        area33.setFocusable(false);
        area33.setMargin(new java.awt.Insets(7, 7, 7, 7));
        jScrollPane11.setViewportView(area33);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.ipadx = 20;
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
        gridBagConstraints.gridx = 4;
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
        gridBagConstraints.gridx = 4;
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
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 11;
        jPanel1.add(checkObtenido, gridBagConstraints);

        jPanel3.setFocusable(false);
        jPanel3.setLayout(new java.awt.GridBagLayout());

        etiquetaGermenes.setBackground(new java.awt.Color(255, 255, 255));
        etiquetaGermenes.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        etiquetaGermenes.setText("UFC/100 ml");
        etiquetaGermenes.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        etiquetaGermenes.setFocusable(false);
        etiquetaGermenes.setMaximumSize(new java.awt.Dimension(44, 17));
        etiquetaGermenes.setMinimumSize(new java.awt.Dimension(44, 17));
        etiquetaGermenes.setOpaque(true);
        etiquetaGermenes.setPreferredSize(new java.awt.Dimension(44, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 43;
        gridBagConstraints.ipady = 44;
        jPanel3.add(etiquetaGermenes, gridBagConstraints);

        campoGermenes.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        campoGermenes.setText("500");
        campoGermenes.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 0, new java.awt.Color(0, 0, 0)));
        campoGermenes.setMaximumSize(new java.awt.Dimension(79, 15));
        campoGermenes.setMinimumSize(new java.awt.Dimension(79, 15));
        campoGermenes.setNextFocusableComponent(comboColiformesFecales);
        campoGermenes.setPreferredSize(new java.awt.Dimension(79, 15));
        campoGermenes.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                campoGermenesCaretUpdate(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 13;
        gridBagConstraints.ipady = 45;
        jPanel3.add(campoGermenes, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        jPanel1.add(jPanel3, gridBagConstraints);

        jPanel4.setFocusable(false);
        jPanel4.setLayout(new java.awt.GridBagLayout());

        comboColiformesFecales.setBackground(new java.awt.Color(204, 204, 204));
        comboColiformesFecales.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Menor a", "Mayor a", " " }));
        comboColiformesFecales.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 0, new java.awt.Color(0, 0, 0)));
        comboColiformesFecales.setNextFocusableComponent(campoColiformesFecales);
        comboColiformesFecales.setPreferredSize(new java.awt.Dimension(50, 21));
        comboColiformesFecales.setUI(new BasicComboBoxUI() {
            protected JButton createArrowButton() {
                return new JButton() {
                    public int getWidth() {
                        return 0;
                    }
                };
            }
        });
        comboColiformesFecales.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comboColiformesFecalesItemStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.ipady = 39;
        jPanel4.add(comboColiformesFecales, gridBagConstraints);

        etiquetaColiformesFecales.setBackground(new java.awt.Color(255, 255, 255));
        etiquetaColiformesFecales.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        etiquetaColiformesFecales.setText("NMP/100 ml");
        etiquetaColiformesFecales.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        etiquetaColiformesFecales.setFocusable(false);
        etiquetaColiformesFecales.setMaximumSize(new java.awt.Dimension(53, 17));
        etiquetaColiformesFecales.setMinimumSize(new java.awt.Dimension(53, 17));
        etiquetaColiformesFecales.setOpaque(true);
        etiquetaColiformesFecales.setPreferredSize(new java.awt.Dimension(53, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 29;
        gridBagConstraints.ipady = 44;
        jPanel4.add(etiquetaColiformesFecales, gridBagConstraints);

        campoColiformesFecales.setText("1.1");
        campoColiformesFecales.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 0, new java.awt.Color(0, 0, 0)));
        campoColiformesFecales.setMaximumSize(new java.awt.Dimension(37, 15));
        campoColiformesFecales.setMinimumSize(new java.awt.Dimension(37, 15));
        campoColiformesFecales.setNextFocusableComponent(comboPseudomona);
        campoColiformesFecales.setPreferredSize(new java.awt.Dimension(37, 15));
        campoColiformesFecales.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                campoColiformesFecalesCaretUpdate(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipady = 45;
        jPanel4.add(campoColiformesFecales, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 5;
        jPanel1.add(jPanel4, gridBagConstraints);

        jPanel9.setFocusable(false);
        jPanel9.setMinimumSize(new java.awt.Dimension(188, 1));
        jPanel9.setPreferredSize(new java.awt.Dimension(179, 61));
        jPanel9.setLayout(new java.awt.GridBagLayout());

        etiquetaPseudomona.setBackground(new java.awt.Color(255, 255, 255));
        etiquetaPseudomona.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        etiquetaPseudomona.setText("en 100 ml");
        etiquetaPseudomona.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        etiquetaPseudomona.setFocusable(false);
        etiquetaPseudomona.setMaximumSize(new java.awt.Dimension(55, 17));
        etiquetaPseudomona.setMinimumSize(new java.awt.Dimension(55, 17));
        etiquetaPseudomona.setOpaque(true);
        etiquetaPseudomona.setPreferredSize(new java.awt.Dimension(55, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 14;
        gridBagConstraints.ipady = 44;
        jPanel9.add(etiquetaPseudomona, gridBagConstraints);

        comboPseudomona.setBackground(new java.awt.Color(204, 204, 204));
        comboPseudomona.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ausencia", "Presencia" }));
        comboPseudomona.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 0, new java.awt.Color(0, 0, 0)));
        comboPseudomona.setNextFocusableComponent(comboPseudomona);
        comboPseudomona.setPreferredSize(new java.awt.Dimension(70, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 40;
        gridBagConstraints.ipady = 40;
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

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 7;
        jPanel1.add(jPanel9, gridBagConstraints);

        jPanel10.setFocusable(false);
        jPanel10.setMinimumSize(new java.awt.Dimension(188, 1));
        jPanel10.setLayout(new java.awt.GridBagLayout());

        comboEscherichia.setBackground(new java.awt.Color(204, 204, 204));
        comboEscherichia.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ausencia", "Presencia" }));
        comboEscherichia.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 0, new java.awt.Color(0, 0, 0)));
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
        gridBagConstraints.ipady = 40;
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
        etiquetaEscherichia.setMaximumSize(new java.awt.Dimension(55, 17));
        etiquetaEscherichia.setMinimumSize(new java.awt.Dimension(55, 17));
        etiquetaEscherichia.setOpaque(true);
        etiquetaEscherichia.setPreferredSize(new java.awt.Dimension(55, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 14;
        gridBagConstraints.ipady = 44;
        jPanel10.add(etiquetaEscherichia, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 6;
        jPanel1.add(jPanel10, gridBagConstraints);

        etiquetaTitulo.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        etiquetaTitulo.setText("Agregar resultados de análisis");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(20, 20, 0, 0);
        jPanel1.add(etiquetaTitulo, gridBagConstraints);

        jPanel11.setLayout(new java.awt.GridBagLayout());

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("% cloro libre");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
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
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.ipadx = 120;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 120, 0, 0);
        jPanel11.add(cajaCloro, gridBagConstraints);

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
        jPanel11.add(jPanel5, gridBagConstraints);

        checkPonerVencimiento.setText("Poner fecha de vencimiento");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTH;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 10);
        jPanel11.add(checkPonerVencimiento, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
        jPanel1.add(jPanel11, gridBagConstraints);

        jPanel12.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 0, 1, new java.awt.Color(0, 0, 0)));
        jPanel12.setFocusable(false);
        jPanel12.setPreferredSize(new java.awt.Dimension(150, 10));
        jPanel12.setLayout(new java.awt.GridBagLayout());

        jLabel12.setBackground(new java.awt.Color(0, 0, 0));
        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel12.setText("<html>COLIFORMES TOTALES</html>");
        jLabel12.setToolTipText("");
        jLabel12.setMaximumSize(new java.awt.Dimension(170, 60));
        jLabel12.setMinimumSize(new java.awt.Dimension(170, 60));
        jLabel12.setPreferredSize(new java.awt.Dimension(170, 60));
        jLabel12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel12MousePressed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        jPanel12.add(jLabel12, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
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
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 20);
        jPanel1.add(jScrollPane13, gridBagConstraints);

        jScrollPane14.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        jScrollPane14.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane14.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane14.setFocusable(false);
        jScrollPane14.setMinimumSize(new java.awt.Dimension(150, 60));
        jScrollPane14.setPreferredSize(new java.awt.Dimension(150, 60));

        area25.setEditable(false);
        area25.setColumns(20);
        area25.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        area25.setLineWrap(true);
        area25.setRows(5);
        area25.setText("Menor a 1.1 NMP/100 \nml");
        area25.setWrapStyleWord(true);
        area25.setBorder(null);
        area25.setFocusable(false);
        area25.setMargin(new java.awt.Insets(7, 7, 7, 7));
        jScrollPane14.setViewportView(area25);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.ipadx = 20;
        jPanel1.add(jScrollPane14, gridBagConstraints);

        jPanel13.setFocusable(false);
        jPanel13.setLayout(new java.awt.GridBagLayout());

        comboColiformesTotales.setBackground(new java.awt.Color(204, 204, 204));
        comboColiformesTotales.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Menor a", "Mayor a", " " }));
        comboColiformesTotales.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 0, new java.awt.Color(0, 0, 0)));
        comboColiformesTotales.setNextFocusableComponent(campoColiformesFecales);
        comboColiformesTotales.setPreferredSize(new java.awt.Dimension(50, 21));
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
        gridBagConstraints.ipady = 39;
        jPanel13.add(comboColiformesTotales, gridBagConstraints);

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
        gridBagConstraints.ipady = 44;
        jPanel13.add(etiquetaColiformesTotales, gridBagConstraints);

        campoColiformesTotales.setText("1.1");
        campoColiformesTotales.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 0, new java.awt.Color(0, 0, 0)));
        campoColiformesTotales.setMaximumSize(new java.awt.Dimension(37, 15));
        campoColiformesTotales.setMinimumSize(new java.awt.Dimension(37, 15));
        campoColiformesTotales.setNextFocusableComponent(comboPseudomona);
        campoColiformesTotales.setPreferredSize(new java.awt.Dimension(37, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipady = 45;
        jPanel13.add(campoColiformesTotales, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        jPanel1.add(jPanel13, gridBagConstraints);

        jScrollPane15.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        jScrollPane15.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane15.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane15.setFocusable(false);
        jScrollPane15.setMinimumSize(new java.awt.Dimension(150, 60));
        jScrollPane15.setPreferredSize(new java.awt.Dimension(150, 60));

        area34.setEditable(false);
        area34.setColumns(20);
        area34.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        area34.setLineWrap(true);
        area34.setRows(5);
        area34.setText("ISO 21527-2:2008\n");
        area34.setWrapStyleWord(true);
        area34.setBorder(null);
        area34.setFocusable(false);
        area34.setMargin(new java.awt.Insets(7, 7, 7, 7));
        jScrollPane15.setViewportView(area34);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 20);
        jPanel1.add(jScrollPane15, gridBagConstraints);

        jPanel15.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 0, 1, new java.awt.Color(0, 0, 0)));
        jPanel15.setFocusable(false);
        jPanel15.setPreferredSize(new java.awt.Dimension(150, 10));
        jPanel15.setLayout(new java.awt.GridBagLayout());

        jLabel13.setBackground(new java.awt.Color(0, 0, 0));
        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel13.setText("<html>MOHOS Y LEVADURAS</html>");
        jLabel13.setToolTipText("");
        jLabel13.setFocusable(false);
        jLabel13.setMaximumSize(new java.awt.Dimension(170, 60));
        jLabel13.setMinimumSize(new java.awt.Dimension(170, 60));
        jLabel13.setPreferredSize(new java.awt.Dimension(170, 60));
        jLabel13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel13MousePressed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        jPanel15.add(jLabel13, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.ipady = 50;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        jPanel1.add(jPanel15, gridBagConstraints);

        jScrollPane16.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        jScrollPane16.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane16.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane16.setFocusable(false);
        jScrollPane16.setMinimumSize(new java.awt.Dimension(150, 60));
        jScrollPane16.setPreferredSize(new java.awt.Dimension(150, 60));

        area35.setEditable(false);
        area35.setColumns(20);
        area35.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        area35.setLineWrap(true);
        area35.setRows(5);
        area35.setText("-");
        area35.setWrapStyleWord(true);
        area35.setBorder(null);
        area35.setFocusable(false);
        area35.setMargin(new java.awt.Insets(7, 7, 7, 7));
        jScrollPane16.setViewportView(area35);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.ipadx = 20;
        jPanel1.add(jScrollPane16, gridBagConstraints);

        jPanel16.setFocusable(false);
        jPanel16.setLayout(new java.awt.GridBagLayout());

        comboMohos.setBackground(new java.awt.Color(204, 204, 204));
        comboMohos.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Menor a", "Mayor a", " " }));
        comboMohos.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 0, new java.awt.Color(0, 0, 0)));
        comboMohos.setNextFocusableComponent(campoColiformesFecales);
        comboMohos.setPreferredSize(new java.awt.Dimension(50, 21));
        comboMohos.setUI(new BasicComboBoxUI() {
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
        gridBagConstraints.ipady = 39;
        jPanel16.add(comboMohos, gridBagConstraints);

        etiquetaMohos.setBackground(new java.awt.Color(255, 255, 255));
        etiquetaMohos.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        etiquetaMohos.setText("NMP/100 ml");
        etiquetaMohos.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        etiquetaMohos.setFocusable(false);
        etiquetaMohos.setMaximumSize(new java.awt.Dimension(53, 17));
        etiquetaMohos.setMinimumSize(new java.awt.Dimension(53, 17));
        etiquetaMohos.setOpaque(true);
        etiquetaMohos.setPreferredSize(new java.awt.Dimension(53, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 29;
        gridBagConstraints.ipady = 43;
        jPanel16.add(etiquetaMohos, gridBagConstraints);

        campoMohos.setText("1.1");
        campoMohos.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 0, new java.awt.Color(0, 0, 0)));
        campoMohos.setMaximumSize(new java.awt.Dimension(37, 15));
        campoMohos.setMinimumSize(new java.awt.Dimension(37, 15));
        campoMohos.setNextFocusableComponent(comboPseudomona);
        campoMohos.setPreferredSize(new java.awt.Dimension(37, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipady = 45;
        jPanel16.add(campoMohos, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 8;
        jPanel1.add(jPanel16, gridBagConstraints);

        checkLimiteMohos.setText("<html>Recuento por<br>encima del limite</html>");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        jPanel1.add(checkLimiteMohos, gridBagConstraints);

        jPanel17.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel17.setFocusable(false);
        jPanel17.setPreferredSize(new java.awt.Dimension(150, 10));
        jPanel17.setLayout(new java.awt.GridBagLayout());

        jLabel14.setBackground(new java.awt.Color(0, 0, 0));
        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel14.setText("<html>SHIGELLA</html>");
        jLabel14.setToolTipText("");
        jLabel14.setFocusable(false);
        jLabel14.setMaximumSize(new java.awt.Dimension(170, 60));
        jLabel14.setMinimumSize(new java.awt.Dimension(170, 60));
        jLabel14.setPreferredSize(new java.awt.Dimension(170, 60));
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
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.ipady = 50;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        jPanel1.add(jPanel17, gridBagConstraints);

        jScrollPane17.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 1, 1, new java.awt.Color(0, 0, 0)));
        jScrollPane17.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane17.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane17.setFocusable(false);
        jScrollPane17.setMinimumSize(new java.awt.Dimension(150, 60));
        jScrollPane17.setPreferredSize(new java.awt.Dimension(150, 60));

        area36.setEditable(false);
        area36.setColumns(20);
        area36.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        area36.setLineWrap(true);
        area36.setRows(5);
        area36.setText("Ausencia en 100 ml");
        area36.setWrapStyleWord(true);
        area36.setBorder(null);
        area36.setFocusable(false);
        area36.setMargin(new java.awt.Insets(7, 7, 7, 7));
        jScrollPane17.setViewportView(area36);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.ipadx = 20;
        jPanel1.add(jScrollPane17, gridBagConstraints);

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
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 20);
        jPanel1.add(jScrollPane18, gridBagConstraints);

        jPanel14.setFocusable(false);
        jPanel14.setMinimumSize(new java.awt.Dimension(188, 1));
        jPanel14.setLayout(new java.awt.GridBagLayout());

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
        jPanel14.add(etiquetaShigella, gridBagConstraints);

        comboShigella.setBackground(new java.awt.Color(204, 204, 204));
        comboShigella.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ausencia", "Presencia" }));
        comboShigella.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        comboShigella.setNextFocusableComponent(comboPseudomona);
        comboShigella.setPreferredSize(new java.awt.Dimension(70, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 40;
        gridBagConstraints.ipady = 40;
        jPanel14.add(comboShigella, gridBagConstraints);
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
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 9;
        jPanel1.add(jPanel14, gridBagConstraints);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 984, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
            comboColiformesFecales.setSelectedItem("Menor a");
            campoColiformesFecales.setText("3");
            comboColiformesTotales.setSelectedItem("Menor a");
            campoColiformesTotales.setText("3");
            comboPseudomona.setSelectedItem("Ausencia");
            comboPseudomona.setSelectedItem("Ausencia");
        } else {
        }
    }

    private void campoGermenesCaretUpdate(javax.swing.event.CaretEvent evt) {
        tildarCheck();
    }

    private void campoColiformesFecalesCaretUpdate(javax.swing.event.CaretEvent evt) {
        tildarCheck();
    }

    private void comboColiformesFecalesItemStateChanged(java.awt.event.ItemEvent evt) {
        tildarCheck();
    }

    private void comboEscherichiaItemStateChanged(java.awt.event.ItemEvent evt) {
        tildarCheck();
    }
    private void jLabel10MousePressed(java.awt.event.MouseEvent evt) {
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

    private void jLabel12MousePressed(java.awt.event.MouseEvent evt) {
        activarTotales = !activarTotales;
        campoColiformesTotales.setEnabled(activarTotales);
        area24.setEnabled(activarTotales);
        area25.setEnabled(activarTotales);
        etiquetaColiformesTotales.setEnabled(activarTotales);
        comboColiformesTotales.setEnabled(activarTotales);
        if (activarTotales) {
            jPanel12.setBackground(new Color(240, 240, 240));
        } else {
            jPanel12.setBackground(new Color(240, 100, 100));
        }
    }

    private void jLabel5MousePressed(java.awt.event.MouseEvent evt) {
        activarFecales = !activarFecales;
        campoColiformesFecales.setEnabled(activarFecales);
        area22.setEnabled(activarFecales);
        area23.setEnabled(activarFecales);
        etiquetaColiformesFecales.setEnabled(activarFecales);
        comboColiformesFecales.setEnabled(activarFecales);
        if (activarFecales) {
            jPanel2.setBackground(new Color(240, 240, 240));
        } else {
            jPanel2.setBackground(new Color(240, 100, 100));
        }
    }

    private void jLabel11MousePressed(java.awt.event.MouseEvent evt) {
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

    private void jLabel9MousePressed(java.awt.event.MouseEvent evt) {
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

    private void jLabel13MousePressed(java.awt.event.MouseEvent evt) {
        activarMohos = !activarMohos;
        campoMohos.setEnabled(activarMohos);
        area35.setEnabled(activarMohos);
        area34.setEnabled(activarMohos);
        etiquetaMohos.setEnabled(activarMohos);
        comboMohos.setEnabled(activarMohos);
        checkLimiteMohos.setEnabled(activarMohos);
        if (activarMohos) {
            jPanel15.setBackground(new Color(240, 240, 240));
        } else {
            jPanel15.setBackground(new Color(240, 100, 100));
        }
    }
    private void jLabel14MousePressed(java.awt.event.MouseEvent evt) {
        activarShigella = !activarShigella;
        area36.setEnabled(activarShigella);
        area37.setEnabled(activarShigella);
        etiquetaShigella.setEnabled(activarShigella);
        comboShigella.setEnabled(activarShigella);
        if (activarShigella) {
            jPanel17.setBackground(new Color(240, 240, 240));
        } else {
            jPanel17.setBackground(new Color(240, 100, 100));
        }
    }

    private javax.swing.JTextArea area12;
    private javax.swing.JTextArea area13;
    private javax.swing.JTextArea area22;
    private javax.swing.JTextArea area23;
    private javax.swing.JTextArea area24;
    private javax.swing.JTextArea area25;
    private javax.swing.JTextArea area32;
    private javax.swing.JTextArea area33;
    private javax.swing.JTextArea area34;
    private javax.swing.JTextArea area35;
    private javax.swing.JTextArea area36;
    private javax.swing.JTextArea area37;
    private javax.swing.JTextArea area42;
    private javax.swing.JTextArea area43;
    private javax.swing.JButton botonGenerar;
    private javax.swing.JTextField cajaCloro;
    private com.toedter.calendar.JDateChooser cajaFechaAnalisis;
    private javax.swing.JTextField cajaPh;
    private javax.swing.JTextField campoColiformesFecales;
    private javax.swing.JTextField campoColiformesTotales;
    private javax.swing.JTextField campoGermenes;
    private javax.swing.JTextField campoMohos;
    private javax.swing.JCheckBox checkLimiteMohos;
    private javax.swing.JCheckBox checkObtenido;
    private javax.swing.JCheckBox checkPonerVencimiento;
    private javax.swing.JComboBox<String> comboCaracteres;
    private javax.swing.JComboBox<String> comboColiformesFecales;
    private javax.swing.JComboBox<String> comboColiformesTotales;
    private javax.swing.JComboBox<String> comboEscherichia;
    private javax.swing.JComboBox<String> comboMohos;
    private javax.swing.JComboBox<String> comboPseudomona;
    private javax.swing.JComboBox<String> comboShigella;
    private javax.swing.JLabel etiquetaColiformesFecales;
    private javax.swing.JLabel etiquetaColiformesTotales;
    private javax.swing.JLabel etiquetaEscherichia;
    private javax.swing.JLabel etiquetaGermenes;
    private javax.swing.JLabel etiquetaMohos;
    private javax.swing.JLabel etiquetaPh;
    private javax.swing.JLabel etiquetaPseudomona;
    private javax.swing.JLabel etiquetaShigella;
    private javax.swing.JLabel etiquetaTitulo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel2;
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
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    // End of variables declaration//GEN-END:variables

    public void tildarCheck() {
        if (campoGermenes.getText().equals("500") && campoColiformesFecales.getText().equals("3")
                && comboPseudomona.getSelectedItem().equals("Ausencia")
                && comboEscherichia.getSelectedItem().equals("Ausencia")
                && String.valueOf(comboColiformesFecales.getSelectedItem()).equals("Menor a")) {
            checkObtenido.setSelected(true);
        } else {
            checkObtenido.setSelected(false);
        }
    }

    public void completarReporte() {
        try {
            java.util.Date fm = cajaFechaAnalisis.getDate(); //obtener fecha
            Long dm = fm.getTime(); //sacar timepo
        } catch (Exception e) {
            log.info("error");
        }
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
        String mohos = "-2";
        if (comboMohos.isEnabled()) {
            mohos = comboMohos.getSelectedItem().toString().toUpperCase()
                    + " " + campoMohos.getText().toUpperCase() + " " + etiquetaMohos.getText();
        }

        String shigella = "-2";
        if(comboShigella.isEnabled()){
            shigella = comboShigella.getSelectedItem().toString().toUpperCase() + " " + etiquetaShigella.getText();
        }
        String ph = String.valueOf(cajaPh.getText());
        String cloro = String.valueOf(cajaCloro.getText());
        String caracteres = "";
        String idmuestra = String.valueOf(id);
        String[] valores = {idmuestra, germenes, coliformesTotales, coliformesFecales, escherichia, pseudomona, "", ph, cloro, caracteres, mohos, shigella};
        r.setTipo("Microbiológico de agua COFES");
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
        if (editar) {
            valores[6] = JOptionPane.showInputDialog("Digite la observacion:", c.recuperarObservaciones(id));
        } else {
            valores[6] = JOptionPane.showInputDialog("Digite la observacion:");
        }
        valores[6] = valores[6].isBlank() ? "" : valores[6].trim().endsWith(".") ? valores[6] : valores[6] + ".";
        valores[9] = caracteres;
        String conclusion = crearConclusion();
        c.guardarConclusion(conclusion, id);
        r.setResultadoMB(valores);
        if (editar) {
            File rv = new File(c.recuperarRutas("Reportes") + "\\" + pdf);
            File rn = new File(c.recuperarRutas("Reportes") + "\\(BORRADO) " + pdf);
            rv.renameTo(rn);
            if (c.editarMBAgua(r)) {
                c.guardarLimiteMohos(id, checkLimiteMohos.isSelected());
                c.guardarFechaAnalisisMBAGUA(r, id);
                c.guardarFechaAnalisis(r, id);
                if (c.checkearVencimiento(r)) {
                    c.actualizarVencimiento(r);
                } else {
                    c.agregarVencimiento(r);
                }
                c.guardarObservaciones(valores[6], id);
                c.esconderFechaVencimiento(id, checkPonerVencimiento.isSelected());
                this.dispose();
                c.generarReporteMBAgua(id, procedencia);
            }

        } else {
            if (c.guardarResultadoMBAgua(r)) {
                c.guardarLimiteMohos(id, checkLimiteMohos.isSelected());
                c.guardarFechaAnalisisMBAGUA(r, id);
                c.guardarFechaAnalisis(r, id);
                if (c.checkearVencimiento(r)) {
                    c.actualizarVencimiento(r);
                } else {
                    c.agregarVencimiento(r);
                }
                c.guardarObservaciones(valores[6], id);
                c.esconderFechaVencimiento(id, checkPonerVencimiento.isSelected());
                this.dispose();
                c.generarReporteMBAgua(id, procedencia);
            }
        }
    }

    public String crearConclusion() {
        boolean germenes = true;
        if (campoGermenes.isEnabled()) {
            germenes = !(Integer.parseInt(campoGermenes.getText()) > 499);
        }

        boolean coliformesTotales = true;
        if (campoColiformesTotales.isEnabled()) {
            coliformesTotales = !(Double.parseDouble(campoColiformesTotales.getText()) >= 1.1);
            if (Double.parseDouble(campoColiformesTotales.getText()) == 1.1 && comboColiformesTotales.getSelectedItem().equals("Menor a")) {
                coliformesTotales = true;
            } else if (Double.parseDouble(campoColiformesTotales.getText()) == 1.1 && comboColiformesTotales.getSelectedItem().equals("Mayor a")) {
                coliformesTotales = false;
            }
        }
        boolean coliformesFecales = true;
        if (campoColiformesFecales.isEnabled()) {
            coliformesFecales = !(Double.parseDouble(campoColiformesFecales.getText()) >= 1.1);
            if (Double.parseDouble(campoColiformesFecales.getText()) == 1.1 && comboColiformesFecales.getSelectedItem().equals("Menor a")) {
                coliformesFecales = true;
            } else if (Double.parseDouble(campoColiformesFecales.getText()) == 1.1 && comboColiformesFecales.getSelectedItem().equals("Mayor a")) {
                coliformesFecales = false;
            }
        }

        boolean escherichia = true;
        if (comboEscherichia.isEnabled()) {
            escherichia = !(comboEscherichia.getSelectedItem().toString().equals("Presencia"));
        }

        boolean pseudomona = true;
        if (comboPseudomona.isEnabled()) {
            pseudomona = !(comboPseudomona.getSelectedItem().toString().equals("Presencia"));
        }

        boolean shigella = true;
        if (comboShigella.isEnabled()) {
            shigella = !(comboShigella.getSelectedItem().toString().equals("Presencia"));
        }

        boolean mohos = true;
        if (checkLimiteMohos.isEnabled()) {
            mohos = !checkLimiteMohos.isSelected();
        }
        String conclusion = "";
        boolean aux = germenes && coliformesTotales && coliformesFecales && escherichia && pseudomona && shigella;
        if (aux) {
            conclusion = "En los parámetros analizados, la muestra cumple con las especificaciones microbiológicas "
                    + "estipuladas por el art. 982 del Código Alimentario Argentino (Ley 18284).";
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
            if (!mohos) {
                conclusion += " Mohos y Levaduras,";
            }
            if (!shigella) {
                conclusion += " Shigella,";
            }
            conclusion = conclusion.substring(0, conclusion.length() - 1);
            String recomendacion = "";
            if (editar) {
                recomendacion = JOptionPane.showInputDialog("Ingrese la recomendacion:", c.recuperarRecomendacion(id));
            } else {
                recomendacion = JOptionPane.showInputDialog("Ingrese la recomendacion:");
            }
            if (recomendacion.endsWith(".")) {
                recomendacion = recomendacion.substring(0, recomendacion.length() - 1);
            }
            c.guardarRecomendacion(recomendacion, id);
            if (recomendacion.length() < 1) {
                conclusion += " la muestra no cumple con las especificaciones microbiológicas "
                        + "estipuladas por el art. 982 del Código Alimentario Argentino (Ley 18284).";
            } else {
                conclusion += " la muestra no cumple con las especificaciones microbiológicas "
                        + "estipuladas por el art. 982 del Código Alimentario Argentino"
                        + " (Ley 18284). Se recomienda " + recomendacion.toLowerCase() + ".";
            }

        }
        String auxiliarConclusion = JOptionPane.showInputDialog(null, "Digite la conclusión: ", conclusion);
        conclusion = conclusion.replace("poes", "POES").replace("bpm", "BPM");
        auxiliarConclusion = auxiliarConclusion.isBlank() ? "" : conclusion.equals("-") ? "" : auxiliarConclusion.trim().endsWith(".") ? auxiliarConclusion : auxiliarConclusion + ".";
        return auxiliarConclusion;

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
