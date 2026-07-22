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
import org.ignaciorodriguez.repository.ResultadoRepository;

public class TablaHisopados extends javax.swing.JDialog {

    int id;
    String procedencia, pdf, auxObservaciones;
    Consultas c = Consultas.getInstancia();
    boolean editar, activarGermens = true, activarTotales = true, activarFecales = true,
            activarEscherichia = true, activarEnterobacterias = true, activarStaphilococos = true,
            activarSalmonella = true, activarMohos = true, activarListeria = true, activarVibrio = true;
    boolean[] auxBool = new boolean[9];
    Frame parent;
    private String auxRecomendacion = "";
    MuestraRepository muestraRepository = new MuestraRepository();
    ResultadoRepository resultadoRepository = new ResultadoRepository();

    public TablaHisopados(java.awt.Frame parent, boolean modal, int id, String procedencia,
                          boolean editar, String pdf) {
        super(parent, modal);
        this.parent = parent;
        this.procedencia = procedencia;
        this.id = id;
        this.editar = editar;
        this.pdf = pdf;
        initComponents();
        setLocationRelativeTo(null);
        if (editar == true) {
            Map<String, String> resultados = resultadoRepository.recuperarResultadosHisopados(id);
            if (resultados == null) {
                this.editar = false;
            } else {
                String aux = "";
                DecimalFormat df = new DecimalFormat("#.##");
                if (!resultados.get("germenes").contains("null")) {
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
                        cajaGermenes.setText(df.format(Double.parseDouble(aux)));
                        if (resultados.get("germenes").toLowerCase().endsWith("cm2")) {
                            comboGermenesIn.setSelectedIndex(1);
                        } else {
                            comboGermenesIn.setSelectedIndex(0);
                        }
                    }
                }

                if (!resultados.get("coliformesTotales").contains("null")) {
                    if (resultados.get("coliformesTotales").toLowerCase().contains("menor")) {
                        checkColiformes.doClick();
                        if (resultados.get("coliformesTotales").toLowerCase().endsWith("cm2")) {
                            comboColiformesIn.setSelectedIndex(1);
                        } else {
                            comboColiformesIn.setSelectedIndex(0);
                        }
                    } else if (resultados.get("coliformesTotales").contains("-1")) {
                    } else if (resultados.get("coliformesTotales").contains("-2")) {
                        etiquetaTotalesMousePressed(click(etiquetaTotales));
                    } else {
                        aux = resultados.get("coliformesTotales").replaceAll("[^0-9?!\\.]", "");
                        cajaColiformes.setText(df.format(Double.parseDouble(aux)));
                        if (resultados.get("coliformesTotales").toLowerCase().endsWith("cm2")) {
                            comboColiformesIn.setSelectedIndex(1);
                        } else {
                            comboColiformesIn.setSelectedIndex(0);
                        }
                    }
                }

                if (!resultados.get("coliformesFecales").contains("null")) {
                    if (resultados.get("coliformesFecales").startsWith("Ausencia")) {
                        comboColiformes.setSelectedIndex(0);
                    } else if (resultados.get("coliformesFecales").contains("-1")) {
                    } else if (resultados.get("coliformesFecales").contains("-2")) {
                        etiquetaFecalesMousePressed(click(etiquetaFecales));
                    } else {
                        comboColiformes.setSelectedIndex(1);
                    }
                }

                if (!resultados.get("escherichia").contains("null")) {
                    if (resultados.get("escherichia").startsWith("Ausencia")) {
                        comboEscherichia.setSelectedIndex(0);
                    } else if (resultados.get("escherichia").contains("-1")) {
                    } else if (resultados.get("escherichia").contains("-2")) {
                        etiquetaEscherichiaMousePressed(click(etiquetaEscherichia));
                    } else {
                        comboEscherichia.setSelectedIndex(1);
                    }
                }
                
                if (!resultados.get("staphilococos").contains("null")) {
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
                        if (resultados.get("staphilococos").toLowerCase().contains("menor")) {
                            comboStaphilococos.setSelectedIndex(0);
                        } else {
                            comboStaphilococos.setSelectedIndex(1);
                        }
                        aux = resultados.get("staphilococos").replaceAll("[^0-9?!\\.]", "");
                        cajaStaphilococos.setText(df.format(Double.parseDouble(aux)));
                        if (resultados.get("staphilococos").toLowerCase().endsWith("cm2")) {
                            comboStaphilococosIn.setSelectedIndex(1);
                        } else {
                            comboStaphilococosIn.setSelectedIndex(0);
                        }
                    }
                }

                if (!resultados.get("enterobacterias").contains("null")) {
                    if (resultados.get("enterobacterias").toLowerCase().contains("menor")) {
                        checkEnterobacterias.doClick();
                        if (resultados.get("enterobacterias").toLowerCase().endsWith("cm2")) {
                            comboEnterobacteriasIn.setSelectedIndex(1);
                        } else {
                            comboEnterobacteriasIn.setSelectedIndex(0);
                        }
                    } else if (resultados.get("enterobacterias").contains("-1")) {
                    } else if (resultados.get("enterobacterias").contains("-2")) {
                        etiquetaEnterobacteriasMousePressed(click(etiquetaEnterobacterias));
                    } else {
                        if (resultados.get("enterobacterias").toLowerCase().contains("menor")) {
                            comboEnterobacterias.setSelectedIndex(0);
                        } else {
                            comboEnterobacterias.setSelectedIndex(1);
                        }
                        aux = resultados.get("enterobacterias").replaceAll("[^0-9?!\\.]", "");
                        cajaEnterobacterias.setText(df.format(Double.parseDouble(aux)));
                        if (resultados.get("enterobacterias").toLowerCase().endsWith("cm2")) {
                            comboEnterobacteriasIn.setSelectedIndex(1);
                        } else {
                            comboEnterobacteriasIn.setSelectedIndex(0);
                        }
                    }
                }

                if (!resultados.get("salmonella").contains("null")) {
                    if (resultados.get("salmonella").contains("-2")) {
                        etiquetaSalmonellaMousePressed(click(etiquetaSalmonella));
                    } else if (resultados.get("salmonella").contains("-1")) {
                    } else {
                        if (resultados.get("salmonella").contains("Ausencia")) {
                            comboSalmonella.setSelectedIndex(0);
                            String auxSalmonella = resultados.get("salmonella").substring("Ausencia".length()).trim();
                            cajaSalmonella.setText(auxSalmonella);
                        } else {
                            comboSalmonella.setSelectedIndex(1);
                            String auxSalmonella = resultados.get("salmonella").substring("Presencia".length()).trim();
                            cajaSalmonella.setText(auxSalmonella);
                        }
                    }
                }

                if (!resultados.get("mohos").contains("null")) {
                    if (resultados.get("mohos").contains("-2")) {
                        etiquetaMohosMousePressed(click(etiquetaMohos));
                    }
                    if (resultados.get("mohos").toUpperCase().contains("MENOR A")) {
                        checkMohos.doClick();
                        if (resultados.get("mohos").toLowerCase().endsWith("cm2")) {
                            comboMohosIn.setSelectedIndex(1);
                        } else {
                            comboMohosIn.setSelectedIndex(0);
                        }
                    } else {
                        aux = resultados.get("mohos").replaceAll("[^0-9?!\\.]", "");
                        cajaMohos.setText(df.format(Double.parseDouble(aux)));
                        if (resultados.get("mohos").toLowerCase().endsWith("cm2")) {
                            comboMohosIn.setSelectedIndex(1);
                        } else {
                            comboMohosIn.setSelectedIndex(0);
                        }
                    }
                }
                if (!resultados.get("listeria").contains("null")) {
                    if (resultados.get("listeria").contains("-2")) {
                        etiquetaListeriaMousePressed(click(etiquetaListeria));
                    } else if (resultados.get("listeria").contains("-1")) {
                    } else {
                        if (resultados.get("listeria").toUpperCase().contains("AUSENCIA")) {
                            comboListeria.setSelectedIndex(0);
                        } else if (resultados.get("listeria").toUpperCase().contains("PRESENCIA")) {
                            comboListeria.setSelectedIndex(1);
                        }
                        if (resultados.get("listeria").toUpperCase().endsWith("UFC")) {
                            comboListeriaIn.setSelectedIndex(0);
                        } else if (resultados.get("listeria").toUpperCase().endsWith("UFC/CM2")) {
                            comboListeriaIn.setSelectedIndex(1);
                        } else if (resultados.get("listeria").toUpperCase().endsWith("CM2")) {
                            comboListeriaIn.setSelectedIndex(2);
                        } else {
                            comboListeriaIn.setSelectedIndex(3);
                        }
                    }
                    try {
                        if (resultados.get("listeria").toUpperCase().endsWith("2") && resultados.get("listeria").length() > 1) {
                            aux = resultados.get("listeria").substring(0, resultados.get("listeria").length() - 1).replaceAll("[^0-9?!\\.]", "");
                        } else {
                            aux = resultados.get("listeria").replaceAll("[^0-9?!\\.]", "");
                        }
                        cajaListeria.setText(df.format(Double.parseDouble(aux)));
                    } catch (java.lang.NumberFormatException e) {

                    }

                }
                
                if (!resultados.get("vibrio").contains("null")) {
                    if (resultados.get("vibrio").toLowerCase().contains("menor")) {
                        checkVibrio.doClick();
                        if (resultados.get("vibrio").toLowerCase().endsWith("cm2")) {
                            comboVibrioIn.setSelectedIndex(1);
                        } else {
                            comboVibrioIn.setSelectedIndex(0);
                        }
                    } else if (resultados.get("vibrio").contains("-1")) {
                    } else if (resultados.get("vibrio").contains("-2")) {
                        etiquetaVibrioMousePressed(click(etiquetaVibrio));
                    } else {
                        if (resultados.get("vibrio").toLowerCase().contains("menor")) {
                            comboVibrio.setSelectedIndex(0);
                        } else {
                            comboVibrio.setSelectedIndex(1);
                        }
                        aux = resultados.get("vibrio").replaceAll("[^0-9?!\\.]", "");
                        cajaVibrio.setText(df.format(Double.parseDouble(aux)));
                        if (resultados.get("vibrio").toLowerCase().endsWith("cm2")) {
                            comboVibrioIn.setSelectedIndex(1);
                        } else {
                            comboVibrioIn.setSelectedIndex(0);
                        }
                    }
                }

                if (!resultados.get("fechaAnalisis").contains("null")) {
                    java.sql.Date fecha = java.sql.Date.valueOf(resultados.get("fechaAnalisis"));
                    java.util.Date utilFecha = null;
                    try {
                        utilFecha = new java.util.Date(fecha.getTime());
                    } catch (Exception e) {
                        System.err.println("error, " + e);
                    }
                    cajaFechaAnalisis.setDate(utilFecha);
                }
                auxObservaciones = muestraRepository.recuperarObservaciones(id);

                String auxConclusion = muestraRepository.recuperarConclusion(id);
                if (auxConclusion != null) {
                    checkConclusion.doClick();
                }
                for (int i = 0; i < auxBool.length; i++) {
                    auxBool[i] = false;
                }
                if (auxConclusion != null) {
                    if (auxConclusion.contains("Se recomienda")) {
                        auxRecomendacion = auxConclusion.substring(auxConclusion.indexOf("Se recomienda ") + "Se recomienda ".length());
                        auxRecomendacion = auxRecomendacion.endsWith(".") ? auxRecomendacion.substring(0, auxRecomendacion.length() - 1) : auxRecomendacion;
                    }
                    if (auxConclusion.contains("Germenes aerobios totales") || auxConclusion.contains("germenes aerobios totales")) {
                        auxBool[0] = true;
                    }
                    if (auxConclusion.contains("Coliformes totales") || auxConclusion.contains("coliformes totales")) {
                        auxBool[1] = true;
                    }
                    if (auxConclusion.contains("Coliformes fecales") || auxConclusion.contains("coliformes fecales")) {
                        auxBool[2] = true;
                    }
                    if (auxConclusion.contains("Escherichia coli") || auxConclusion.contains("escherichia coli")) {
                        auxBool[3] = true;
                    }
                    if (auxConclusion.contains("Staphilococos") || auxConclusion.contains("staphilococos")) {
                        auxBool[4] = true;
                    }
                    if (auxConclusion.contains("Enterobacterias") || auxConclusion.contains("enterobacterias")) {
                        auxBool[5] = true;
                    }
                    if (auxConclusion.contains("Salmonella") || auxConclusion.contains("salmonella")) {
                        auxBool[6] = true;
                    }
                    if (auxConclusion.contains("Mohos") || auxConclusion.contains("mohos")) {
                        auxBool[7] = true;
                    }
                    if (auxConclusion.contains("Listeria") || auxConclusion.contains("listeria")) {
                        auxBool[8] = true;
                    }
                    if (auxConclusion.contains("Vibrio") || auxConclusion.contains("vibrio")) {
                        auxBool[9] = true;
                    }

                }

            }
        }

        ImageIcon icon = new ImageIcon("src\\vista\\icono.png");
        this.setIconImage(icon.getImage());
    }
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jScrollPane1 = new javax.swing.JScrollPane();
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
        jPanel4 = new javax.swing.JPanel();
        comboStaphilococos = new javax.swing.JComboBox<>();
        cajaStaphilococos = new javax.swing.JTextField();
        comboStaphilococosIn = new javax.swing.JComboBox<>();
        jPanel10 = new javax.swing.JPanel();
        comboColiformes = new javax.swing.JComboBox<>();
        jPanel3 = new javax.swing.JPanel();
        cajaGermenes = new javax.swing.JTextField();
        etiquetaMenorGermenes = new javax.swing.JLabel();
        comboGermenesIn = new javax.swing.JComboBox<>();
        jPanel13 = new javax.swing.JPanel();
        cajaColiformes = new javax.swing.JTextField();
        etiquetaMenorColiformes = new javax.swing.JLabel();
        comboColiformesIn = new javax.swing.JComboBox<>();
        jPanel12 = new javax.swing.JPanel();
        comboEscherichia = new javax.swing.JComboBox<>();
        jPanel5 = new javax.swing.JPanel();
        comboEnterobacterias = new javax.swing.JComboBox<>();
        cajaEnterobacterias = new javax.swing.JTextField();
        comboEnterobacteriasIn = new javax.swing.JComboBox<>();
        jPanel16 = new javax.swing.JPanel();
        comboSalmonella = new javax.swing.JComboBox<>();
        cajaSalmonella = new javax.swing.JTextField();
        jPanel17 = new javax.swing.JPanel();
        cajaMohos = new javax.swing.JTextField();
        etiquetaMenorMohos = new javax.swing.JLabel();
        comboMohosIn = new javax.swing.JComboBox<>();
        jPanel20 = new javax.swing.JPanel();
        comboListeria = new javax.swing.JComboBox<>();
        cajaListeria = new javax.swing.JTextField();
        comboListeriaIn = new javax.swing.JComboBox<>();
        jPanel23 = new javax.swing.JPanel();
        comboVibrio = new javax.swing.JComboBox<>();
        cajaVibrio = new javax.swing.JTextField();
        comboVibrioIn = new javax.swing.JComboBox<>();
        checkConclusion = new javax.swing.JCheckBox();
        checkGermenes = new javax.swing.JCheckBox();
        checkColiformes = new javax.swing.JCheckBox();
        jPanel14 = new javax.swing.JPanel();
        etiquetaEnterobacterias = new javax.swing.JLabel();
        jScrollPane13 = new javax.swing.JScrollPane();
        area53 = new javax.swing.JTextArea();
        checkStaphilococos = new javax.swing.JCheckBox();
        checkEnterobacterias = new javax.swing.JCheckBox();
        jPanel15 = new javax.swing.JPanel();
        etiquetaSalmonella = new javax.swing.JLabel();
        jScrollPane15 = new javax.swing.JScrollPane();
        area64 = new javax.swing.JTextArea();
        jPanel18 = new javax.swing.JPanel();
        etiquetaMohos = new javax.swing.JLabel();
        jScrollPane16 = new javax.swing.JScrollPane();
        area65 = new javax.swing.JTextArea();
        checkVibrio = new javax.swing.JCheckBox();
        jPanel19 = new javax.swing.JPanel();
        etiquetaListeria = new javax.swing.JLabel();
        jScrollPane17 = new javax.swing.JScrollPane();
        area66 = new javax.swing.JTextArea();
        checkListeria = new javax.swing.JCheckBox();
        jPanel21 = new javax.swing.JPanel();
        etiquetaVibrio = new javax.swing.JLabel();
        jScrollPane18 = new javax.swing.JScrollPane();
        area67 = new javax.swing.JTextArea();
        checkMohos = new javax.swing.JCheckBox();

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
        gridBagConstraints.gridy = 8;
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
        gridBagConstraints.gridy = 8;
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
        gridBagConstraints.gridy = 13;
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
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
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

        jPanel4.setFocusable(false);
        jPanel4.setMaximumSize(new java.awt.Dimension(171, 60));
        jPanel4.setMinimumSize(new java.awt.Dimension(171, 60));
        jPanel4.setPreferredSize(new java.awt.Dimension(171, 60));
        jPanel4.setLayout(new java.awt.GridBagLayout());

        comboStaphilococos.setBackground(new java.awt.Color(204, 204, 204));
        comboStaphilococos.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Menor a", " " }));
        comboStaphilococos.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 0, 0, new java.awt.Color(0, 0, 0)));
        comboStaphilococos.setMaximumSize(new java.awt.Dimension(60, 60));
        comboStaphilococos.setMinimumSize(new java.awt.Dimension(60, 60));
        comboStaphilococos.setNextFocusableComponent(cajaStaphilococos);
        comboStaphilococos.setPreferredSize(new java.awt.Dimension(60, 60));
        comboStaphilococos.setUI(new BasicComboBoxUI() {
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
        jPanel4.add(comboStaphilococos, gridBagConstraints);

        cajaStaphilococos.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 0, 0, new java.awt.Color(0, 0, 0)));
        cajaStaphilococos.setMargin(new java.awt.Insets(20, 20, 20, 20));
        cajaStaphilococos.setMaximumSize(new java.awt.Dimension(37, 60));
        cajaStaphilococos.setMinimumSize(new java.awt.Dimension(37, 60));
        cajaStaphilococos.setName(""); // NOI18N
        cajaStaphilococos.setNextFocusableComponent(comboColiformes);
        cajaStaphilococos.setPreferredSize(new java.awt.Dimension(37, 60));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        jPanel4.add(cajaStaphilococos, gridBagConstraints);

        comboStaphilococosIn.setBackground(new java.awt.Color(204, 204, 204));
        comboStaphilococosIn.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "UFC", "UFC/cm2" }));
        comboStaphilococosIn.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        comboStaphilococosIn.setMaximumSize(new java.awt.Dimension(74, 60));
        comboStaphilococosIn.setMinimumSize(new java.awt.Dimension(74, 60));
        comboStaphilococosIn.setPreferredSize(new java.awt.Dimension(74, 60));
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
        jPanel4.add(comboStaphilococosIn, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        jPanel9.add(jPanel4, gridBagConstraints);

        jPanel10.setFocusable(false);
        jPanel10.setLayout(new java.awt.GridBagLayout());

        comboColiformes.setBackground(new java.awt.Color(204, 204, 204));
        comboColiformes.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ausencia", "Presencia" }));
        comboColiformes.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        comboColiformes.setNextFocusableComponent(cajaGermenes);
        comboColiformes.setPreferredSize(new java.awt.Dimension(70, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 102;
        gridBagConstraints.ipady = 40;
        jPanel10.add(comboColiformes, gridBagConstraints);
        comboColiformes.setUI(new BasicComboBoxUI() {
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

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        jPanel3.setForeground(new java.awt.Color(255, 255, 255));
        jPanel3.setFocusable(false);
        jPanel3.setMaximumSize(new java.awt.Dimension(172, 60));
        jPanel3.setMinimumSize(new java.awt.Dimension(172, 60));
        jPanel3.setPreferredSize(new java.awt.Dimension(172, 60));
        jPanel3.setLayout(new java.awt.GridBagLayout());

        cajaGermenes.setBackground(new java.awt.Color(255, 255, 255));
        cajaGermenes.setBorder(null);
        cajaGermenes.setMaximumSize(new java.awt.Dimension(120, 60));
        cajaGermenes.setMinimumSize(new java.awt.Dimension(120, 60));
        cajaGermenes.setPreferredSize(new java.awt.Dimension(120, 60));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jPanel3.add(cajaGermenes, gridBagConstraints);

        etiquetaMenorGermenes.setText("MENOR A 10");
        etiquetaMenorGermenes.setFocusable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        jPanel3.add(etiquetaMenorGermenes, gridBagConstraints);
        etiquetaMenorGermenes.setVisible(false);

        comboGermenesIn.setBackground(new java.awt.Color(204, 204, 204));
        comboGermenesIn.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "UFC", "UFC/cm2" }));
        comboGermenesIn.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 1, new java.awt.Color(0, 0, 0)));
        comboGermenesIn.setMaximumSize(new java.awt.Dimension(52, 60));
        comboGermenesIn.setMinimumSize(new java.awt.Dimension(52, 60));
        comboGermenesIn.setPreferredSize(new java.awt.Dimension(52, 60));
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
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        jPanel3.add(comboGermenesIn, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        jPanel9.add(jPanel3, gridBagConstraints);

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));
        jPanel13.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        jPanel13.setForeground(new java.awt.Color(255, 255, 255));
        jPanel13.setFocusable(false);
        jPanel13.setMaximumSize(new java.awt.Dimension(172, 60));
        jPanel13.setMinimumSize(new java.awt.Dimension(172, 60));
        jPanel13.setPreferredSize(new java.awt.Dimension(172, 60));
        jPanel13.setLayout(new java.awt.GridBagLayout());

        cajaColiformes.setBackground(new java.awt.Color(255, 255, 255));
        cajaColiformes.setBorder(null);
        cajaColiformes.setMaximumSize(new java.awt.Dimension(120, 60));
        cajaColiformes.setMinimumSize(new java.awt.Dimension(120, 60));
        cajaColiformes.setPreferredSize(new java.awt.Dimension(120, 60));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jPanel13.add(cajaColiformes, gridBagConstraints);

        etiquetaMenorColiformes.setText("MENOR A 10");
        etiquetaMenorColiformes.setFocusable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        jPanel13.add(etiquetaMenorColiformes, gridBagConstraints);
        etiquetaMenorColiformes.setVisible(false);

        comboColiformesIn.setBackground(new java.awt.Color(204, 204, 204));
        comboColiformesIn.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "UFC", "UFC/cm2" }));
        comboColiformesIn.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 1, new java.awt.Color(0, 0, 0)));
        comboColiformesIn.setMaximumSize(new java.awt.Dimension(52, 60));
        comboColiformesIn.setMinimumSize(new java.awt.Dimension(52, 60));
        comboColiformesIn.setPreferredSize(new java.awt.Dimension(52, 60));
        comboColiformesIn.setUI(new BasicComboBoxUI() {
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
        gridBagConstraints.gridy = 0;
        jPanel13.add(comboColiformesIn, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        jPanel9.add(jPanel13, gridBagConstraints);

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

        jPanel5.setFocusable(false);
        jPanel5.setMaximumSize(new java.awt.Dimension(171, 60));
        jPanel5.setMinimumSize(new java.awt.Dimension(171, 60));
        jPanel5.setPreferredSize(new java.awt.Dimension(171, 60));
        jPanel5.setLayout(new java.awt.GridBagLayout());

        comboEnterobacterias.setBackground(new java.awt.Color(204, 204, 204));
        comboEnterobacterias.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Menor a", " " }));
        comboEnterobacterias.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        comboEnterobacterias.setMaximumSize(new java.awt.Dimension(60, 60));
        comboEnterobacterias.setMinimumSize(new java.awt.Dimension(60, 60));
        comboEnterobacterias.setNextFocusableComponent(cajaStaphilococos);
        comboEnterobacterias.setPreferredSize(new java.awt.Dimension(60, 60));
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
        gridBagConstraints.gridy = 0;
        jPanel5.add(comboEnterobacterias, gridBagConstraints);

        cajaEnterobacterias.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        cajaEnterobacterias.setMargin(new java.awt.Insets(20, 20, 20, 20));
        cajaEnterobacterias.setMaximumSize(new java.awt.Dimension(37, 60));
        cajaEnterobacterias.setMinimumSize(new java.awt.Dimension(37, 60));
        cajaEnterobacterias.setName(""); // NOI18N
        cajaEnterobacterias.setNextFocusableComponent(comboColiformes);
        cajaEnterobacterias.setPreferredSize(new java.awt.Dimension(37, 60));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        jPanel5.add(cajaEnterobacterias, gridBagConstraints);

        comboEnterobacteriasIn.setBackground(new java.awt.Color(204, 204, 204));
        comboEnterobacteriasIn.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "UFC", "UFC/cm2" }));
        comboEnterobacteriasIn.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 1, new java.awt.Color(0, 0, 0)));
        comboEnterobacteriasIn.setMaximumSize(new java.awt.Dimension(74, 60));
        comboEnterobacteriasIn.setMinimumSize(new java.awt.Dimension(74, 60));
        comboEnterobacteriasIn.setPreferredSize(new java.awt.Dimension(74, 60));
        comboEnterobacteriasIn.setUI(new BasicComboBoxUI() {
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
        jPanel5.add(comboEnterobacteriasIn, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        jPanel9.add(jPanel5, gridBagConstraints);

        jPanel16.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 1, 1, new java.awt.Color(0, 0, 0)));
        jPanel16.setFocusable(false);
        jPanel16.setMaximumSize(new java.awt.Dimension(172, 60));
        jPanel16.setMinimumSize(new java.awt.Dimension(172, 60));
        jPanel16.setPreferredSize(new java.awt.Dimension(172, 60));
        jPanel16.setLayout(new java.awt.GridBagLayout());

        comboSalmonella.setBackground(new java.awt.Color(204, 204, 204));
        comboSalmonella.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ausencia", "Presencia" }));
        comboSalmonella.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 0, 0, new java.awt.Color(0, 0, 0)));
        comboSalmonella.setMaximumSize(new java.awt.Dimension(132, 58));
        comboSalmonella.setMinimumSize(new java.awt.Dimension(132, 58));
        comboSalmonella.setName(""); // NOI18N
        comboSalmonella.setNextFocusableComponent(cajaGermenes);
        comboSalmonella.setPreferredSize(new java.awt.Dimension(132, 58));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jPanel16.add(comboSalmonella, gridBagConstraints);
        comboSalmonella.setUI(new BasicComboBoxUI() {
            protected JButton createArrowButton() {
                return new JButton() {
                    public int getWidth() {
                        return 0;
                    }
                };
            }
        });

        cajaSalmonella.setMaximumSize(new java.awt.Dimension(40, 58));
        cajaSalmonella.setMinimumSize(new java.awt.Dimension(40, 58));
        cajaSalmonella.setName(""); // NOI18N
        cajaSalmonella.setPreferredSize(new java.awt.Dimension(40, 58));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        jPanel16.add(cajaSalmonella, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        jPanel9.add(jPanel16, gridBagConstraints);

        jPanel17.setBackground(new java.awt.Color(255, 255, 255));
        jPanel17.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 1, new java.awt.Color(0, 0, 0)));
        jPanel17.setForeground(new java.awt.Color(255, 255, 255));
        jPanel17.setFocusable(false);
        jPanel17.setPreferredSize(new java.awt.Dimension(172, 60));
        jPanel17.setLayout(new java.awt.GridBagLayout());

        cajaMohos.setBackground(new java.awt.Color(255, 255, 255));
        cajaMohos.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        cajaMohos.setMaximumSize(new java.awt.Dimension(97, 60));
        cajaMohos.setMinimumSize(new java.awt.Dimension(97, 60));
        cajaMohos.setPreferredSize(new java.awt.Dimension(97, 60));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jPanel17.add(cajaMohos, gridBagConstraints);

        etiquetaMenorMohos.setText("MENOR A 10");
        etiquetaMenorMohos.setFocusable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        jPanel17.add(etiquetaMenorMohos, gridBagConstraints);
        etiquetaMenorMohos.setVisible(false);

        comboMohosIn.setBackground(new java.awt.Color(204, 204, 204));
        comboMohosIn.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "UFC", "UFC/cm2" }));
        comboMohosIn.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 1, new java.awt.Color(0, 0, 0)));
        comboMohosIn.setMaximumSize(new java.awt.Dimension(74, 60));
        comboMohosIn.setMinimumSize(new java.awt.Dimension(74, 60));
        comboMohosIn.setPreferredSize(new java.awt.Dimension(74, 60));
        comboMohosIn.setUI(new BasicComboBoxUI() {
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
        gridBagConstraints.gridy = 0;
        jPanel17.add(comboMohosIn, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 11;
        jPanel9.add(jPanel17, gridBagConstraints);

        jPanel20.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 1, new java.awt.Color(0, 0, 0)));
        jPanel20.setFocusable(false);
        jPanel20.setMaximumSize(new java.awt.Dimension(171, 60));
        jPanel20.setMinimumSize(new java.awt.Dimension(171, 60));
        jPanel20.setPreferredSize(new java.awt.Dimension(171, 60));
        jPanel20.setLayout(new java.awt.GridBagLayout());

        comboListeria.setBackground(new java.awt.Color(204, 204, 204));
        comboListeria.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ausencia en", "Presencia en", " " }));
        comboListeria.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        comboListeria.setMaximumSize(new java.awt.Dimension(70, 60));
        comboListeria.setMinimumSize(new java.awt.Dimension(70, 60));
        comboListeria.setNextFocusableComponent(cajaGermenes);
        comboListeria.setPreferredSize(new java.awt.Dimension(70, 60));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jPanel20.add(comboListeria, gridBagConstraints);
        comboListeria.setUI(new BasicComboBoxUI() {
            protected JButton createArrowButton() {
                return new JButton() {
                    public int getWidth() {
                        return 0;
                    }
                };
            }
        });

        cajaListeria.setBackground(new java.awt.Color(255, 255, 255));
        cajaListeria.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        cajaListeria.setMaximumSize(new java.awt.Dimension(37, 60));
        cajaListeria.setMinimumSize(new java.awt.Dimension(37, 60));
        cajaListeria.setName(""); // NOI18N
        cajaListeria.setPreferredSize(new java.awt.Dimension(37, 60));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        jPanel20.add(cajaListeria, gridBagConstraints);

        comboListeriaIn.setBackground(new java.awt.Color(204, 204, 204));
        comboListeriaIn.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "UFC", "UFC/cm2", "cm2", "" }));
        comboListeriaIn.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 1, new java.awt.Color(0, 0, 0)));
        comboListeriaIn.setMaximumSize(new java.awt.Dimension(64, 60));
        comboListeriaIn.setMinimumSize(new java.awt.Dimension(64, 60));
        comboListeriaIn.setPreferredSize(new java.awt.Dimension(64, 60));
        comboListeriaIn.setUI(new BasicComboBoxUI() {
            protected JButton createArrowButton() {
                return new JButton() {
                    public int getWidth() {
                        return 0;
                    }
                };
            }
        });
        comboListeriaIn.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comboListeriaInItemStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        jPanel20.add(comboListeriaIn, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        jPanel9.add(jPanel20, gridBagConstraints);

        jPanel23.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 1, new java.awt.Color(0, 0, 0)));
        jPanel23.setFocusable(false);
        jPanel23.setMaximumSize(new java.awt.Dimension(171, 60));
        jPanel23.setMinimumSize(new java.awt.Dimension(171, 60));
        jPanel23.setPreferredSize(new java.awt.Dimension(171, 60));
        jPanel23.setLayout(new java.awt.GridBagLayout());

        comboVibrio.setBackground(new java.awt.Color(204, 204, 204));
        comboVibrio.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Menor a", "Mayor a", "" }));
        comboVibrio.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        comboVibrio.setMaximumSize(new java.awt.Dimension(70, 60));
        comboVibrio.setMinimumSize(new java.awt.Dimension(70, 60));
        comboVibrio.setNextFocusableComponent(cajaGermenes);
        comboVibrio.setPreferredSize(new java.awt.Dimension(70, 60));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jPanel23.add(comboVibrio, gridBagConstraints);
        comboVibrio.setUI(new BasicComboBoxUI() {
            protected JButton createArrowButton() {
                return new JButton() {
                    public int getWidth() {
                        return 0;
                    }
                };
            }
        });

        cajaVibrio.setBackground(new java.awt.Color(255, 255, 255));
        cajaVibrio.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        cajaVibrio.setMaximumSize(new java.awt.Dimension(37, 60));
        cajaVibrio.setMinimumSize(new java.awt.Dimension(37, 60));
        cajaVibrio.setName(""); // NOI18N
        cajaVibrio.setPreferredSize(new java.awt.Dimension(37, 60));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        jPanel23.add(cajaVibrio, gridBagConstraints);

        comboVibrioIn.setBackground(new java.awt.Color(204, 204, 204));
        comboVibrioIn.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "UFC", "UFC/cm2", "cm2", "" }));
        comboVibrioIn.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 1, new java.awt.Color(0, 0, 0)));
        comboVibrioIn.setMaximumSize(new java.awt.Dimension(64, 60));
        comboVibrioIn.setMinimumSize(new java.awt.Dimension(64, 60));
        comboVibrioIn.setPreferredSize(new java.awt.Dimension(64, 60));
        comboVibrioIn.setUI(new BasicComboBoxUI() {
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
        jPanel23.add(comboVibrioIn, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        jPanel9.add(jPanel23, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridheight = 11;
        jPanel1.add(jPanel9, gridBagConstraints);

        checkConclusion.setText("Agregar conclusion");;
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 13;
        jPanel1.add(checkConclusion, gridBagConstraints);

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
        gridBagConstraints.gridy = 7;
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
        gridBagConstraints.gridy = 7;
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
        gridBagConstraints.gridy = 8;
        jPanel1.add(checkStaphilococos, gridBagConstraints);

        checkEnterobacterias.setText("MENOR A 10");
        checkEnterobacterias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkEnterobacteriasActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        jPanel1.add(checkEnterobacterias, gridBagConstraints);

        jPanel15.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 1, 1, 1, new java.awt.Color(0, 0, 0)));
        jPanel15.setFocusable(false);
        jPanel15.setMaximumSize(new java.awt.Dimension(170, 60));
        jPanel15.setMinimumSize(new java.awt.Dimension(170, 60));
        jPanel15.setPreferredSize(new java.awt.Dimension(170, 60));
        jPanel15.setLayout(new java.awt.GridBagLayout());

        etiquetaSalmonella.setBackground(new java.awt.Color(0, 0, 0));
        etiquetaSalmonella.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        etiquetaSalmonella.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        etiquetaSalmonella.setText("<html>SALMONELLA sp</html>");
        etiquetaSalmonella.setToolTipText("");
        etiquetaSalmonella.setFocusable(false);
        etiquetaSalmonella.setMaximumSize(new java.awt.Dimension(170, 60));
        etiquetaSalmonella.setMinimumSize(new java.awt.Dimension(170, 60));
        etiquetaSalmonella.setPreferredSize(new java.awt.Dimension(170, 60));
        etiquetaSalmonella.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                etiquetaSalmonellaMousePressed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        jPanel15.add(etiquetaSalmonella, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        jPanel1.add(jPanel15, gridBagConstraints);

        jScrollPane15.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 1, new java.awt.Color(0, 0, 0)));
        jScrollPane15.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane15.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane15.setFocusable(false);
        jScrollPane15.setMinimumSize(new java.awt.Dimension(150, 60));
        jScrollPane15.setPreferredSize(new java.awt.Dimension(150, 60));

        area64.setEditable(false);
        area64.setColumns(20);
        area64.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        area64.setLineWrap(true);
        area64.setRows(5);
        area64.setText("ISO 6579:2002");
        area64.setWrapStyleWord(true);
        area64.setBorder(null);
        area64.setFocusable(false);
        area64.setMargin(new java.awt.Insets(7, 7, 7, 7));
        jScrollPane15.setViewportView(area64);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 20);
        jPanel1.add(jScrollPane15, gridBagConstraints);

        jPanel18.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 1, 1, 1, new java.awt.Color(0, 0, 0)));
        jPanel18.setFocusable(false);
        jPanel18.setMaximumSize(new java.awt.Dimension(170, 60));
        jPanel18.setMinimumSize(new java.awt.Dimension(170, 60));
        jPanel18.setPreferredSize(new java.awt.Dimension(170, 60));
        jPanel18.setLayout(new java.awt.GridBagLayout());

        etiquetaMohos.setBackground(new java.awt.Color(0, 0, 0));
        etiquetaMohos.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        etiquetaMohos.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        etiquetaMohos.setText("<html>MOHOS Y LEVADURAS</html>");
        etiquetaMohos.setToolTipText("");
        etiquetaMohos.setFocusable(false);
        etiquetaMohos.setMaximumSize(new java.awt.Dimension(170, 60));
        etiquetaMohos.setMinimumSize(new java.awt.Dimension(170, 60));
        etiquetaMohos.setPreferredSize(new java.awt.Dimension(170, 60));
        etiquetaMohos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                etiquetaMohosMousePressed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        jPanel18.add(etiquetaMohos, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        jPanel1.add(jPanel18, gridBagConstraints);

        jScrollPane16.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 1, new java.awt.Color(0, 0, 0)));
        jScrollPane16.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane16.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane16.setFocusable(false);
        jScrollPane16.setMinimumSize(new java.awt.Dimension(150, 60));
        jScrollPane16.setPreferredSize(new java.awt.Dimension(150, 60));

        area65.setEditable(false);
        area65.setColumns(20);
        area65.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        area65.setLineWrap(true);
        area65.setRows(5);
        area65.setText("ISO 21527-2:2008");
        area65.setWrapStyleWord(true);
        area65.setBorder(null);
        area65.setFocusable(false);
        area65.setMargin(new java.awt.Insets(7, 7, 7, 7));
        jScrollPane16.setViewportView(area65);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 20);
        jPanel1.add(jScrollPane16, gridBagConstraints);

        checkVibrio.setText("MENOR A 10");
        checkVibrio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkVibrioActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 11;
        jPanel1.add(checkVibrio, gridBagConstraints);

        jPanel19.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 1, 1, 1, new java.awt.Color(0, 0, 0)));
        jPanel19.setFocusable(false);
        jPanel19.setMaximumSize(new java.awt.Dimension(170, 60));
        jPanel19.setMinimumSize(new java.awt.Dimension(170, 60));
        jPanel19.setPreferredSize(new java.awt.Dimension(170, 60));
        jPanel19.setLayout(new java.awt.GridBagLayout());

        etiquetaListeria.setBackground(new java.awt.Color(0, 0, 0));
        etiquetaListeria.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        etiquetaListeria.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        etiquetaListeria.setText("<html>LISTERIA MONOCYTOGENES</html>");
        etiquetaListeria.setToolTipText("");
        etiquetaListeria.setFocusable(false);
        etiquetaListeria.setMaximumSize(new java.awt.Dimension(170, 60));
        etiquetaListeria.setMinimumSize(new java.awt.Dimension(170, 60));
        etiquetaListeria.setPreferredSize(new java.awt.Dimension(170, 60));
        etiquetaListeria.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                etiquetaListeriaMousePressed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        jPanel19.add(etiquetaListeria, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        jPanel1.add(jPanel19, gridBagConstraints);

        jScrollPane17.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 1, new java.awt.Color(0, 0, 0)));
        jScrollPane17.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane17.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane17.setFocusable(false);
        jScrollPane17.setMinimumSize(new java.awt.Dimension(150, 60));
        jScrollPane17.setPreferredSize(new java.awt.Dimension(150, 60));

        area66.setEditable(false);
        area66.setColumns(20);
        area66.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        area66.setLineWrap(true);
        area66.setRows(5);
        area66.setText("Cultivo en medio selectivo Oxford, preenriquecimiento en UVM");
        area66.setWrapStyleWord(true);
        area66.setBorder(null);
        area66.setFocusable(false);
        area66.setMargin(new java.awt.Insets(7, 7, 7, 7));
        jScrollPane17.setViewportView(area66);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 20);
        jPanel1.add(jScrollPane17, gridBagConstraints);

        checkListeria.setText("MENOR A 10");
        checkListeria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkListeriaActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        jPanel1.add(checkListeria, gridBagConstraints);

        jPanel21.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 1, 1, 1, new java.awt.Color(0, 0, 0)));
        jPanel21.setFocusable(false);
        jPanel21.setMaximumSize(new java.awt.Dimension(170, 60));
        jPanel21.setMinimumSize(new java.awt.Dimension(170, 60));
        jPanel21.setPreferredSize(new java.awt.Dimension(170, 60));
        jPanel21.setLayout(new java.awt.GridBagLayout());

        etiquetaVibrio.setBackground(new java.awt.Color(0, 0, 0));
        etiquetaVibrio.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        etiquetaVibrio.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        etiquetaVibrio.setText("<html>VIBRIO CHOLERAE</html>");
        etiquetaVibrio.setToolTipText("");
        etiquetaVibrio.setFocusable(false);
        etiquetaVibrio.setMaximumSize(new java.awt.Dimension(170, 60));
        etiquetaVibrio.setMinimumSize(new java.awt.Dimension(170, 60));
        etiquetaVibrio.setPreferredSize(new java.awt.Dimension(170, 60));
        etiquetaVibrio.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                etiquetaVibrioMousePressed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        jPanel21.add(etiquetaVibrio, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        jPanel1.add(jPanel21, gridBagConstraints);

        jScrollPane18.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 1, new java.awt.Color(0, 0, 0)));
        jScrollPane18.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane18.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane18.setFocusable(false);
        jScrollPane18.setMinimumSize(new java.awt.Dimension(150, 60));
        jScrollPane18.setPreferredSize(new java.awt.Dimension(150, 60));

        area67.setEditable(false);
        area67.setColumns(20);
        area67.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        area67.setLineWrap(true);
        area67.setRows(5);
        area67.setText("Cultivo en medio TCBS, previo enriquecimiento\n");
        area67.setWrapStyleWord(true);
        area67.setBorder(null);
        area67.setFocusable(false);
        area67.setMargin(new java.awt.Insets(7, 7, 7, 7));
        jScrollPane18.setViewportView(area67);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 20);
        jPanel1.add(jScrollPane18, gridBagConstraints);

        checkMohos.setText("MENOR A 10");
        checkMohos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkMohosActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 12;
        jPanel1.add(checkMohos, gridBagConstraints);

        jScrollPane1.setViewportView(jPanel1);
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false), "Cerrar");
        ap.put("Cerrar", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e){
                dispose();
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 663, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 580, Short.MAX_VALUE)
        );

        jScrollPane1.getVerticalScrollBar().setUnitIncrement(16);

        pack();
    }

    private void botonGenerarActionPerformed(java.awt.event.ActionEvent evt) {
        Map m = new HashMap();

        if (checkGermenes.isSelected()) {
            m.put("germenes", "MENOR A 10" + " " + comboGermenesIn.getSelectedItem());
        } else if (!cajaGermenes.isEnabled()) {
            m.put("germenes", "-2");
        } else {
            if (cajaGermenes.getText().isEmpty() || cajaGermenes.getText().isBlank()) {
                m.put("germenes", "-2");
            } else {
                m.put("germenes", cajaGermenes.getText() + " " + comboGermenesIn.getSelectedItem());
            }
        }
        if (checkColiformes.isSelected()) {
            m.put("coliformesTotales", "MENOR A 10" + " " + comboColiformesIn.getSelectedItem());
        } else if (!cajaColiformes.isEnabled()) {
            m.put("coliformesTotales", "-2");
        } else {
            if (cajaColiformes.getText().isBlank() || cajaColiformes.getText().isEmpty()) {
                m.put("coliformesTotales", "-2");
            } else {
                m.put("coliformesTotales", cajaColiformes.getText() + " " + comboColiformesIn.getSelectedItem());
            }
        }
        m.put("idmuestras", id);
        if (!comboColiformes.isEnabled()) {
            m.put("coliformesFecales", "-2");
        } else {
            m.put("coliformesFecales", comboColiformes.getSelectedItem().toString());
        }

        if (!comboEscherichia.isEnabled()) {
            m.put("escherichia", "-2");
        } else {
            m.put("escherichia", comboEscherichia.getSelectedItem().toString());
        }

        if (!cajaStaphilococos.isEnabled()) {
            m.put("staphilococos", "-2");
        } else {
            if (cajaStaphilococos.getText().isEmpty() || cajaStaphilococos.getText().isBlank()) {
                m.put("staphilococos", "-2");
            } else {
                m.put("staphilococos", comboStaphilococos.getSelectedItem().toString() + " "
                        + cajaStaphilococos.getText() + " " + comboStaphilococosIn.getSelectedItem());
            }
        }

        if (!cajaEnterobacterias.isEnabled()) {
            m.put("enterobacterias", "-2");
        } else {

            if (cajaEnterobacterias.getText().isEmpty() || cajaEnterobacterias.getText().isBlank()) {
                m.put("enterobacterias", "-2");
            } else {
                m.put("enterobacterias", comboEnterobacterias.getSelectedItem().toString() + " "
                        + cajaEnterobacterias.getText() + " " + comboEnterobacteriasIn.getSelectedItem());
            }
        }

        if (!comboSalmonella.isEnabled()) {
            m.put("salmonella", "-2");
        } else {
            String auxSalmonella = cajaSalmonella.getText().isBlank() ? "" : (" " + cajaSalmonella.getText());
            m.put("salmonella", comboSalmonella.getSelectedItem().toString() + auxSalmonella);
        }

        if (checkMohos.isSelected()) {
            m.put("mohos", "MENOR A 10 " + comboMohosIn.getSelectedItem());
        } else if (!cajaMohos.isEnabled()) {
            m.put("mohos", "-2");
        } else {
            if (cajaMohos.getText().isBlank() || cajaMohos.getText().isEmpty()) {
                m.put("mohos", "-2");
            } else {
                m.put("mohos", cajaMohos.getText() + " " + comboMohosIn.getSelectedItem());
            }
        }

        if (checkListeria.isSelected()) {
            m.put("listeria", "MENOR A 10 " + comboListeriaIn.getSelectedItem());
        } else if (!comboListeria.isEnabled()) {
            m.put("listeria", "-2");
        } else {
            String listeria = "";
            listeria = (comboListeria.getSelectedItem().toString().toUpperCase() + " " + cajaListeria.getText() + " " + comboListeriaIn.getSelectedItem().toString()).trim();
            m.put("listeria", listeria);
        }
        
        if (checkVibrio.isSelected()) {
            m.put("vibrio", "MENOR A 10 " + comboVibrioIn.getSelectedItem());
        } else if (!comboVibrio.isEnabled()) {
            m.put("vibrio", "-2");
        } else {
            String vibrio = "";
            vibrio = (comboVibrio.getSelectedItem().toString().toUpperCase() + " " + cajaVibrio.getText() + " " + comboVibrioIn.getSelectedItem().toString()).trim();
            m.put("vibrio", vibrio);
        }

        m.put("procedencia", procedencia);

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
            if (checkConclusion.isSelected()) {
                muestraRepository.guardarConclusion(crearConclusion(), id);
            } else {
                muestraRepository.guardarConclusion(null, id);
            }
            muestraRepository.guardarFechaAnalisis(m);
            if (editar) {
                File rv = new File(c.recuperarRutas("Reportes") + "\\" + pdf);
                File rn = new File(c.recuperarRutas("Reportes") + "\\(BORRADO) " + pdf);
                rv.renameTo(rn);
                if (resultadoRepository.editarResultadosHisopados(m)) {
                    muestraRepository.guardarObservaciones(observaciones, id);
                    this.dispose();
                    c.generarReporteHisopados(id, procedencia);
                }
            } else {
                if (resultadoRepository.guardarResultadosHisopados(m)) {
                    muestraRepository.guardarObservaciones(observaciones, id);
                    this.dispose();

                    c.generarReporteHisopados(id, procedencia);
                }
            }
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(null, "Ingrese la fecha de análisis.");
        }
    }

    private void checkGermenesActionPerformed(java.awt.event.ActionEvent evt) {
        if (checkGermenes.isSelected()) {
            etiquetaMenorGermenes.setVisible(true);
            cajaGermenes.setVisible(false);
//            comboGermenesIn.setVisible(false);
        } else {
            etiquetaMenorGermenes.setVisible(false);
            cajaGermenes.setVisible(true);
//            comboGermenesIn.setVisible(true);
        }
    }

    private void checkColiformesActionPerformed(java.awt.event.ActionEvent evt) {
        if (checkColiformes.isSelected()) {
            etiquetaMenorColiformes.setVisible(true);
            cajaColiformes.setVisible(false);
//            comboColiformesIn.setVisible(false);
        } else {
            etiquetaMenorColiformes.setVisible(false);
            cajaColiformes.setVisible(true);
//            comboColiformesIn.setVisible(true);
        }
    }

    private void checkStaphilococosActionPerformed(java.awt.event.ActionEvent evt) {
        if (checkStaphilococos.isSelected()) {
            comboStaphilococos.setSelectedIndex(0);
            cajaStaphilococos.setText("10");
        }
    }

    private void checkEnterobacteriasActionPerformed(java.awt.event.ActionEvent evt) {
        if (checkEnterobacterias.isSelected()) {
            comboEnterobacterias.setSelectedIndex(0);
            cajaEnterobacterias.setText("10");
        }
    }

    private void etiquetaGermenesMousePressed(java.awt.event.MouseEvent evt) {
        activarGermens = !activarGermens;
        cajaGermenes.setEnabled(activarGermens);
        comboGermenesIn.setEnabled(activarGermens);
        etiquetaMenorGermenes.setEnabled(activarGermens);
        area13.setEnabled(activarGermens);
        if (activarGermens) {
            jPanel7.setBackground(new Color(240, 240, 240));
        } else {
            jPanel7.setBackground(new Color(240, 100, 100));
        }
    }

    private void etiquetaTotalesMousePressed(java.awt.event.MouseEvent evt) {
        activarTotales = !activarTotales;
        cajaColiformes.setEnabled(activarTotales);
        comboColiformesIn.setEnabled(activarTotales);
        etiquetaMenorColiformes.setEnabled(activarTotales);
        area23.setEnabled(activarTotales);
        if (activarTotales) {
            jPanel2.setBackground(new Color(240, 240, 240));
        } else {
            jPanel2.setBackground(new Color(240, 100, 100));
        }
    }

    private void etiquetaFecalesMousePressed(java.awt.event.MouseEvent evt) {
        activarFecales = !activarFecales;
        comboColiformes.setEnabled(activarFecales);
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
        cajaEnterobacterias.setEnabled(activarEnterobacterias);
        comboEnterobacteriasIn.setEnabled(activarEnterobacterias);
        if (activarEnterobacterias) {
            jPanel14.setBackground(new Color(240, 240, 240));
        } else {
            jPanel14.setBackground(new Color(240, 100, 100));
        }
    }

    private void etiquetaStaphilococosMousePressed(java.awt.event.MouseEvent evt) {
        System.out.println("activarStaphilococos = " + activarStaphilococos);
        activarStaphilococos = !activarStaphilococos;
        comboStaphilococos.setEnabled(activarStaphilococos);
        cajaStaphilococos.setEnabled(activarStaphilococos);
        comboStaphilococosIn.setEnabled(activarStaphilococos);
        area63.setEnabled(activarStaphilococos);
        if (activarStaphilococos) {
            jPanel6.setBackground(new Color(240, 240, 240));
        } else {
            jPanel6.setBackground(new Color(240, 100, 100));
        }
    }

    private void etiquetaSalmonellaMousePressed(java.awt.event.MouseEvent evt) {
        activarSalmonella = !activarSalmonella;
        comboSalmonella.setEnabled(activarSalmonella);
        area64.setEnabled(activarSalmonella);
        if (activarSalmonella) {
            jPanel15.setBackground(new Color(240, 240, 240));
        } else {
            jPanel15.setBackground(new Color(240, 100, 100));
        }
    }

    private void etiquetaMohosMousePressed(java.awt.event.MouseEvent evt) {
        activarMohos = !activarMohos;
        cajaMohos.setEnabled(activarMohos);
        comboMohosIn.setEnabled(activarMohos);
        etiquetaMenorMohos.setEnabled(activarMohos);
        area65.setEnabled(activarMohos);
        if (activarMohos) {
            jPanel18.setBackground(new Color(240, 240, 240));
        } else {
            jPanel18.setBackground(new Color(240, 100, 100));
        }
    }

    private void checkVibrioActionPerformed(java.awt.event.ActionEvent evt) {
        if (checkVibrio.isSelected()) {
            cajaVibrio.setVisible(true);
            comboVibrio.setSelectedItem("Menor a");
            cajaVibrio.setText("10");
        }
    }

    private void etiquetaListeriaMousePressed(java.awt.event.MouseEvent evt) {
        activarListeria = !activarListeria;
        comboListeria.setEnabled(activarListeria);
        comboListeriaIn.setEnabled(activarListeria);
        area66.setEnabled(activarListeria);
        if (activarListeria) {
            jPanel19.setBackground(new Color(240, 240, 240));
        } else {
            jPanel19.setBackground(new Color(240, 100, 100));
        }
    }

    private void checkListeriaActionPerformed(java.awt.event.ActionEvent evt) {
        if (checkListeria.isSelected()) {
            cajaListeria.setVisible(true);
            comboListeria.setSelectedItem("Menor a");
            cajaListeria.setText("10");
        }
    }

    private void comboListeriaInItemStateChanged(java.awt.event.ItemEvent evt) {
//        if (evt.getStateChange() == ItemEvent.SELECTED) {
//            if (evt.getItem().toString().isBlank()) {
//                comboListeria.setSize(new Dimension(107, 60));
//                comboListeria.setPreferredSize(new Dimension(107, 60));
//                comboListeria.setMinimumSize(new Dimension(107, 60));
//                comboListeria.setMaximumSize(new Dimension(107, 60));
//                cajaListeria.setVisible(false);
//                checkListeria.setEnabled(false);
//                comboListeria.setModel(new DefaultComboBoxModel<>(new String[]{"Presencia", "Ausencia"}));
//                MatteBorder border = new MatteBorder(0, 1, 1, 1, Color.BLACK);
//                comboListeriaIn.setBorder(border);
//            } else {
//                comboListeria.setSize(new Dimension(70, 60));
//                comboListeria.setPreferredSize(new Dimension(70, 60));
//                comboListeria.setMinimumSize(new Dimension(70, 60));
//                comboListeria.setMaximumSize(new Dimension(70, 60));
//                cajaListeria.setVisible(true);
//                checkListeria.setEnabled(true);
//                comboListeria.setModel(new DefaultComboBoxModel<>(new String[]{"Presencia", "Ausencia", "Menor a", "Mayor a", ""}));
//                MatteBorder border = new MatteBorder(0, 0, 1, 1, Color.BLACK);
//                comboListeriaIn.setBorder(border);
//            }
//            if (evt.getItem().toString().equals("cm2")) {
//                comboListeria.setModel(new DefaultComboBoxModel<>(new String[]{"Presencia en", "Ausencia en"}));
//            }
//        }
//        jPanel20.updateUI();
    }

    private void etiquetaVibrioMousePressed(java.awt.event.MouseEvent evt) {
        System.out.println("activarVibrio = " + activarVibrio);
        activarVibrio = !activarVibrio;
        comboVibrio.setEnabled(activarVibrio);
        comboVibrioIn.setEnabled(activarVibrio);
        area67.setEnabled(activarVibrio);
        if (activarVibrio) {
            jPanel21.setBackground(new Color(240, 240, 240));
        } else {
            jPanel21.setBackground(new Color(240, 100, 100));
        }
    }

    private void checkMohosActionPerformed(java.awt.event.ActionEvent evt) {
        if (checkMohos.isSelected()) {
            etiquetaMenorMohos.setVisible(true);
            cajaMohos.setVisible(false);
//            comboMohosIn.setVisible(false);
        } else {
            etiquetaMenorMohos.setVisible(false);
            cajaMohos.setVisible(true);
//            comboMohosIn.setVisible(true);
        }
    }


    private javax.swing.JTextArea area13;
    private javax.swing.JTextArea area23;
    private javax.swing.JTextArea area33;
    private javax.swing.JTextArea area43;
    private javax.swing.JTextArea area53;
    private javax.swing.JTextArea area63;
    private javax.swing.JTextArea area64;
    private javax.swing.JTextArea area65;
    private javax.swing.JTextArea area66;
    private javax.swing.JTextArea area67;
    private javax.swing.JButton botonGenerar;
    private javax.swing.JTextField cajaColiformes;
    private javax.swing.JTextField cajaEnterobacterias;
    private com.toedter.calendar.JDateChooser cajaFechaAnalisis;
    private javax.swing.JTextField cajaGermenes;
    private javax.swing.JTextField cajaListeria;
    private javax.swing.JTextField cajaMohos;
    private javax.swing.JTextField cajaSalmonella;
    private javax.swing.JTextField cajaStaphilococos;
    private javax.swing.JTextField cajaVibrio;
    private javax.swing.JCheckBox checkColiformes;
    private javax.swing.JCheckBox checkConclusion;
    private javax.swing.JCheckBox checkEnterobacterias;
    private javax.swing.JCheckBox checkGermenes;
    private javax.swing.JCheckBox checkListeria;
    private javax.swing.JCheckBox checkMohos;
    private javax.swing.JCheckBox checkStaphilococos;
    private javax.swing.JCheckBox checkVibrio;
    private javax.swing.JComboBox<String> comboColiformes;
    private javax.swing.JComboBox<String> comboColiformesIn;
    private javax.swing.JComboBox<String> comboEnterobacterias;
    private javax.swing.JComboBox<String> comboEnterobacteriasIn;
    private javax.swing.JComboBox<String> comboEscherichia;
    private javax.swing.JComboBox<String> comboGermenesIn;
    private javax.swing.JComboBox<String> comboListeria;
    private javax.swing.JComboBox<String> comboListeriaIn;
    private javax.swing.JComboBox<String> comboMohosIn;
    private javax.swing.JComboBox<String> comboSalmonella;
    private javax.swing.JComboBox<String> comboStaphilococos;
    private javax.swing.JComboBox<String> comboStaphilococosIn;
    private javax.swing.JComboBox<String> comboVibrio;
    private javax.swing.JComboBox<String> comboVibrioIn;
    private javax.swing.JLabel etiquetaEnterobacterias;
    private javax.swing.JLabel etiquetaEscherichia;
    private javax.swing.JLabel etiquetaFecales;
    private javax.swing.JLabel etiquetaGermenes;
    private javax.swing.JLabel etiquetaListeria;
    private javax.swing.JLabel etiquetaMenorColiformes;
    private javax.swing.JLabel etiquetaMenorGermenes;
    private javax.swing.JLabel etiquetaMenorMohos;
    private javax.swing.JLabel etiquetaMohos;
    private javax.swing.JLabel etiquetaSalmonella;
    private javax.swing.JLabel etiquetaStaphilococos;
    private javax.swing.JLabel etiquetaTitulo;
    private javax.swing.JLabel etiquetaTotales;
    private javax.swing.JLabel etiquetaVibrio;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
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
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane15;
    private javax.swing.JScrollPane jScrollPane16;
    private javax.swing.JScrollPane jScrollPane17;
    private javax.swing.JScrollPane jScrollPane18;
    private javax.swing.JScrollPane jScrollPane9;

    private String crearConclusion() {
        ConclusionHisopados ch = new ConclusionHisopados(parent, true);
        boolean con = ch.devolverConclusion();
        String conclusion = "";
        if (con) {
            return "En la muestra analizada no se ha detectado presencia de bacterias patógenas.";
        } else {
            PatogenosHisopado ph = new PatogenosHisopado(parent, true, auxBool);
            String patogenos = ph.devolverPatogenos();
            String recomendacion = "";
            if (auxRecomendacion.isBlank()) {
                recomendacion = JOptionPane.showInputDialog("Ingrese las recomendaciones:");
            } else {
                recomendacion = JOptionPane.showInputDialog("Ingrese las recomendaciones:", auxRecomendacion);
            }

            if (recomendacion.isBlank()) {
                conclusion = "En la muestra analizada se ha detectado la presencia de " + patogenos + ".";
            } else {
                conclusion = "En la muestra analizada se ha detectado la presencia de " + patogenos + ". Se recomienda "
                        + recomendacion.toLowerCase();
                conclusion = conclusion.isBlank() ? "" : conclusion.equals("-") ? "" : conclusion.trim().endsWith(".") ? conclusion : conclusion + ".";
            }
            conclusion = conclusion.replace("poes", "POES").replace("bpm", "BPM");
            return conclusion;
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
