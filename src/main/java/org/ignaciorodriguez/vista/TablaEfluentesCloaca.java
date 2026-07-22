package org.ignaciorodriguez.vista;

import java.awt.Color;
import java.awt.Component;
import java.awt.Point;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.plaf.basic.BasicComboBoxUI;
import org.ignaciorodriguez.modelo.Consultas;
import org.ignaciorodriguez.repository.MuestraRepository;
import org.ignaciorodriguez.repository.ResultadoRepository;

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
import javax.swing.KeyStroke;
import javax.swing.JComponent;

public class TablaEfluentesCloaca extends javax.swing.JDialog {

    int id;
    Consultas c = Consultas.getInstancia();
    boolean editar, activarPh = true, activarConductividad = true, activarDQO = true, activarDBO = true,
            activarSolidos10 = true, activarSolidos120 = true, activarDetergentes = true,
            activarGrasas = true, activarFosforo = true, activarNitrogeno = true, activarSustancias = true,
            activarColiformes = true, activarHidrocarburos = true, activarNitratos = true, activarCloro = true,
            activarEscherichia = true, activarColiformesTotales = true, activarSulfuros = true;
    String procedencia, pdf, auxObservaciones, auxConclusion, tipo;
    MuestraRepository muestraRepository = new MuestraRepository();
    ResultadoRepository resultadoRepository = new ResultadoRepository();

    public TablaEfluentesCloaca(java.awt.Frame parent, boolean modal, int id, String procedencia, boolean editar, String pdf, String tipo) {
        super(parent, modal);
        this.id = id;
        this.editar = editar;
        this.pdf = pdf;
        this.tipo = tipo;
        this.procedencia = procedencia;
        initComponents();
        setTitle("ID " + id + ". " + muestraRepository.obtenerProcedencia(id) + ". Efluentes cloaca");
        this.setLocationRelativeTo(null);
        if (editar == true) {
            Map<String, String> resultados = resultadoRepository.recuperarResultadosEfluentesTipo(id);
            if (resultados == null) {
                this.editar = false;
            } else {
                auxObservaciones = muestraRepository.recuperarObservaciones(id);
                auxConclusion = muestraRepository.recuperarConclusion(id);
                if (auxConclusion == null) {
                } else {
                    checkConclusion.setSelected(!auxConclusion.isEmpty());
                }
                etiquetaTitulo.setText("Editar resultados del análisis");
                String aux = "";
                if (resultados.get("ph").contains("-2")) {
                    etiquetaPhMousePressed(click(etiquetaPh));
                } else if (resultados.get("ph").contains("-1")) {
                } else {
                    aux = resultados.get("ph").toString().replaceAll("[^0-9?!\\.]", "");
                    cajapH.setText(String.valueOf(new DecimalFormat("#.##").format(Double.parseDouble(aux)).replaceAll(",", ".")));
                }

                if (resultados.get("conductividad").contains("-2")) {
                    etiquetaConductividadMousePressed(click(etiquetaConductividad));
                } else if (resultados.get("conductividad").contains("-1")) {
                } else {
                    if (resultados.get("conductividad").startsWith("Menor")) {
                        comboConductividad.setSelectedIndex(0);
                    } else if (resultados.get("conductividad").startsWith("Mayor")) {
                        comboConductividad.setSelectedIndex(1);
                    } else {
                        comboConductividad.setSelectedIndex(2);
                    }
                    aux = resultados.get("conductividad").toString().replaceAll("[^0-9?!\\.]", "").replaceAll(",", ".");
                    cajaConductividad.setText(aux.replaceAll(",", "."));
                }
                if (resultados.get("dqo").contains("-2")) {
                    etiquetaDQOMousePressed(click(etiquetaDQO));
                } else if (resultados.get("dqo").contains("-1")) {
                } else {
                    if (resultados.get("dqo").startsWith("Menor")) {
                        comboDQO.setSelectedIndex(0);
                    } else if (resultados.get("dqo").startsWith("Mayor")) {
                        comboDQO.setSelectedIndex(1);
                    } else {
                        comboDQO.setSelectedIndex(2);
                    }
                    aux = resultados.get("dqo").toString().replaceAll("[^0-9?!\\.]", "").replaceAll(",", ".");
                    cajaDQO.setText(aux.replaceAll(",", "."));
                }

                if (resultados.get("dbo").contains("-2")) {
                    etiquetaDBOMousePressed(click(etiquetaDBO));
                } else if (resultados.get("dbo").contains("-1")) {
                } else {
                    if (resultados.get("dbo").startsWith("Menor")) {
                        comboDBO.setSelectedIndex(0);
                    } else if (resultados.get("dbo").startsWith("Mayor")) {
                        comboDBO.setSelectedIndex(1);
                    } else {
                        comboDBO.setSelectedIndex(2);
                    }
                    aux = resultados.get("dbo").toString().replaceAll("[^0-9?!\\.]", "").replaceAll(",", ".");
                    cajaDBO.setText(aux.replaceAll(",", "."));
                }

                if (resultados.get("solidos10").contains("-2")) {
                    etiquetaSolidos10MousePressed(click(etiquetaSolidos10));
                } else if (resultados.get("solidos10").contains("-1")) {
                } else {
                    if (resultados.get("solidos10").startsWith("Menor")) {
                        comboSolidos10.setSelectedIndex(0);
                    } else if (resultados.get("solidos10").startsWith("Mayor")) {
                        comboSolidos10.setSelectedIndex(1);
                    } else {
                        comboSolidos10.setSelectedIndex(2);
                    }
                    aux = resultados.get("solidos10").toString().replaceAll("[^0-9?!\\.]", "").replaceAll(",", ".");
                    cajaSolidos10.setText(aux.replaceAll(",", "."));
                }

                if (resultados.get("solidos120").contains("-2")) {
                    etiquetaSolidos120MousePressed(click(etiquetaSolidos120));
                } else if (resultados.get("solidos120").contains("-1")) {
                } else {
                    if (resultados.get("solidos120").startsWith("Menor")) {
                        comboSolidos120.setSelectedIndex(0);
                    } else if (resultados.get("solidos120").startsWith("Mayor")) {
                        comboSolidos120.setSelectedIndex(1);
                    } else {
                        comboSolidos120.setSelectedIndex(2);
                    }
                    aux = resultados.get("solidos120").toString().replaceAll("[^0-9?!\\.]", "").replaceAll(",", ".");
                    cajaSolidos120.setText(aux.replaceAll(",", "."));
                }

                if (resultados.get("detergentes").contains("-2")) {
                    etiquetaDetergentesMousePressed(click(etiquetaDetergentes));
                } else if (resultados.get("detergentes").contains("-1")) {
                } else {
                    if (resultados.get("detergentes").startsWith("Menor")) {
                        comboDetergentes.setSelectedIndex(0);
                    } else if (resultados.get("detergentes").startsWith("Mayor")) {
                        comboDetergentes.setSelectedIndex(1);
                    } else {
                        comboDetergentes.setSelectedIndex(2);
                    }
                    aux = resultados.get("detergentes").toString().replaceAll("[^0-9?!\\.]", "").replaceAll(",", ".");
                    cajaDetergentes.setText(aux.replaceAll(",", "."));
                }

                if (resultados.get("grasasAceite").contains("-2")) {
                    etiquetaGrasasMousePressed(click(etiquetaGrasas));
                } else if (resultados.get("grasasAceite").contains("-1")) {
                } else {
                    if (resultados.get("grasasAceite").startsWith("Menor")) {
                        comboGrasas.setSelectedIndex(0);
                    } else if (resultados.get("grasasAceite").startsWith("Mayor")) {
                        comboGrasas.setSelectedIndex(1);
                    } else {
                        comboGrasas.setSelectedIndex(2);
                    }
                    aux = resultados.get("grasasAceite").toString().replaceAll("[^0-9?!\\.]", "").replaceAll(",", ".");
                    cajaGrasas.setText(aux.replaceAll(",", "."));
                }

                if (resultados.get("fosforoTotal").contains("-2")) {
                    etiquetaFosforoMousePressed(click(etiquetaFosforo));
                } else if (resultados.get("fosforoTotal").contains("-1")) {
                } else {
                    if (resultados.get("fosforoTotal").startsWith("Menor")) {
                        comboFosforo.setSelectedIndex(0);
                    } else if (resultados.get("fosforoTotal").startsWith("Mayor")) {
                        comboFosforo.setSelectedIndex(1);
                    } else {
                        comboFosforo.setSelectedIndex(2);
                    }
                    aux = resultados.get("fosforoTotal").toString().replaceAll("[^0-9?!\\.]", "").replaceAll(",", ".");
                    cajaFosforo.setText(aux.replaceAll(",", "."));
                }
                if (resultados.get("nitrogenoTotal").contains("-2")) {
                    etiquetaNitrogenoMousePressed(click(etiquetaNitrogeno));
                } else if (resultados.get("nitrogenoTotal").contains("-1")) {
                } else {
                    if (resultados.get("nitrogenoTotal").startsWith("Menor")) {
                        comboNitrogeno.setSelectedIndex(0);
                    } else if (resultados.get("nitrogenoTotal").startsWith("Mayor")) {
                        comboNitrogeno.setSelectedIndex(1);
                    } else {
                        comboNitrogeno.setSelectedIndex(2);
                    }
                    aux = resultados.get("nitrogenoTotal").toString().replaceAll("[^0-9?!\\.]", "").replaceAll(",", ".");
                    cajaNitrogeno.setText(aux.replaceAll(",", "."));
                }

                if (resultados.get("sustancias").contains("-2")) {
                    etiquetaSustanciasMousePressed(click(etiquetaSustancias));
                } else if (resultados.get("sustancias").contains("-1")) {
                } else {
                    if (resultados.get("sustancias").startsWith("Menor")) {
                        comboSustancias.setSelectedIndex(0);
                    } else if (resultados.get("sustancias").startsWith("Mayor")) {
                        comboSustancias.setSelectedIndex(1);
                    } else {
                        comboSustancias.setSelectedIndex(2);
                    }
                    aux = resultados.get("sustancias").toString().replaceAll("[^0-9?!\\.]", "").replaceAll(",", ".");
                    cajaSustancias.setText(aux.replaceAll(",", "."));
                }

                if (resultados.get("coliformesFecales").contains("-2")) {
                    etiquetaColiformesFecalesMousePressed(click(etiquetaColiformesFecales));
                } else if (resultados.get("coliformesFecales").contains("-1")) {
                } else {
                    if (resultados.get("coliformesFecales").startsWith("Menor")) {
                        comboColiformesFecales.setSelectedIndex(0);
                    } else if (resultados.get("coliformesFecales").startsWith("Mayor")) {
                        comboColiformesFecales.setSelectedIndex(1);
                    } else {
                        comboColiformesFecales.setSelectedIndex(2);
                    }
                    aux = resultados.get("coliformesFecales").toString().replaceAll("[^0-9?!\\.]", "").replaceAll(",", ".");
                    cajaColiformesFecales.setText(aux.replaceAll(",", "."));
                }
                if (resultados.get("hidrocarburos").contains("-2")) {
                    etiquetaHidrocarburosMousePressed(click(etiquetaHidrocarburos));
                } else if (resultados.get("hidrocarburos").contains("-1")) {
                } else {
                    if (resultados.get("hidrocarburos").startsWith("Menor")) {
                        comboHidrocarburos.setSelectedIndex(0);
                    } else if (resultados.get("hidrocarburos").startsWith("Mayor")) {
                        comboHidrocarburos.setSelectedIndex(1);
                    } else {
                        comboHidrocarburos.setSelectedIndex(2);
                    }
                    aux = resultados.get("hidrocarburos").toString().replaceAll("[^0-9?!\\.]", "").replaceAll(",", ".");
                    cajaHidrocarburos.setText(aux.replaceAll(",", "."));
                }

                if (resultados.get("nitratos").contains("-2")) {
                    etiquetaNitratosMousePressed(click(etiquetaNitratos));
                } else if (resultados.get("nitratos").contains("-1")) {
                } else {
                    if (resultados.get("nitratos").startsWith("Menor")) {
                        comboNitratos.setSelectedIndex(0);
                    } else if (resultados.get("nitratos").startsWith("Mayor")) {
                        comboNitratos.setSelectedIndex(1);
                    } else {
                        comboNitratos.setSelectedIndex(2);
                    }
                    aux = resultados.get("nitratos").toString().replaceAll("[^0-9?!\\.]", "").replaceAll(",", ".");
                    cajaNitratos.setText(aux.replaceAll(",", "."));
                }

                if (resultados.get("cloro").contains("-2")) {
                    etiquetaCloroMousePressed(click(etiquetaCloro));
                } else if (resultados.get("cloro").contains("-1")) {
                } else {
                    if (resultados.get("cloro").startsWith("Menor")) {
                        comboCloro.setSelectedIndex(0);
                    } else if (resultados.get("cloro").startsWith("Mayor")) {
                        comboCloro.setSelectedIndex(1);
                    } else {
                        comboCloro.setSelectedIndex(2);
                    }
                    aux = resultados.get("cloro").toString().replaceAll("[^0-9?!\\.]", "").replaceAll(",", ".");
                    cajaCloro.setText(aux.replaceAll(",", "."));
                }

                if (resultados.get("coliformesTotales").contains("-2")) {
                    etiquetaColiformesTotalesMousePressed(click(etiquetaColiformesTotales));
                } else if (resultados.get("coliformesTotales").contains("-1")) {
                } else {
                    if (resultados.get("coliformesTotales").startsWith("Menor")) {
                        comboColiformesTotales.setSelectedIndex(0);
                    } else if (resultados.get("coliformesTotales").startsWith("Mayor")) {
                        comboColiformesTotales.setSelectedIndex(1);
                    } else {
                        comboColiformesTotales.setSelectedIndex(2);
                    }
                    aux = resultados.get("coliformesTotales").toString().replaceAll("[^0-9?!\\.]", "").replaceAll(",", ".");
                    cajaColiformesTotales.setText(aux.replaceAll(",", "."));
                }

                if (resultados.get("escherichia").contains("-2")) {
                    etiquetaEscherichiaMousePressed(click(etiquetaEscherichia));
                } else if (resultados.get("escherichia").contains("-1")) {
                } else {
                    if (resultados.get("escherichia").startsWith("Menor")) {
                        comboEscherichia.setSelectedIndex(0);
                    } else if (resultados.get("escherichia").startsWith("Mayor")) {
                        comboEscherichia.setSelectedIndex(1);
                    } else {
                        comboEscherichia.setSelectedIndex(2);
                    }
                    aux = resultados.get("escherichia").toString().substring(0, resultados.get("escherichia").indexOf("100 ml")).replaceAll("[^0-9?!\\.]", "").replaceAll(",", ".");
                    cajaEscherichia.setText(aux.replaceAll(",", "."));
                }

                if (!resultados.get("sulfuros").equals("")) {
                    if (resultados.get("sulfuros").contains("-2")) {
                        etiquetaSulfurosMousePressed(click(etiquetaSulfuros));
                    } else if (resultados.get("sulfuros").contains("-1")) {
                    } else {
                        if (resultados.get("sulfuros").startsWith("Menor")) {
                            comboSulfuros.setSelectedIndex(0);
                        } else if (resultados.get("sulfuros").startsWith("Mayor")) {
                            comboSulfuros.setSelectedIndex(1);
                        } else {
                            comboSulfuros.setSelectedIndex(2);
                        }
                        aux = resultados.get("sulfuros").toString().replaceAll("[^0-9?!\\.]", "").replaceAll(",", ".");
                        cajaSulfuros.setText(aux.replaceAll(",", "."));
                    }
                } else {
                    etiquetaSulfurosMousePressed(click(etiquetaSulfuros));
                }
            }
        }
        ImageIcon icon = new ImageIcon("src\\vista\\icono.png");
        this.setIconImage(icon.getImage());
    }


    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        botonGenerar = new javax.swing.JButton();
        etiquetaTitulo = new javax.swing.JLabel();
        jPanel21 = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        jPanel19 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        etiquetaPh = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        etiquetaNitrogeno = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        etiquetaDQO = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        etiquetaColiformesFecales = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        etiquetaFosforo = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        etiquetaConductividad = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        etiquetaSustancias = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        etiquetaGrasas = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        etiquetaSolidos120 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        etiquetaSolidos10 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        etiquetaDetergentes = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        etiquetaDBO = new javax.swing.JLabel();
        jPanel23 = new javax.swing.JPanel();
        etiquetaHidrocarburos = new javax.swing.JLabel();
        jPanel24 = new javax.swing.JPanel();
        etiquetaNitratos = new javax.swing.JLabel();
        jPanel25 = new javax.swing.JPanel();
        etiquetaCloro = new javax.swing.JLabel();
        jPanel18 = new javax.swing.JPanel();
        etiquetaColiformesTotales = new javax.swing.JLabel();
        jPanel20 = new javax.swing.JPanel();
        etiquetaEscherichia = new javax.swing.JLabel();
        jPanel26 = new javax.swing.JPanel();
        etiquetaSulfuros = new javax.swing.JLabel();
        jPanel17 = new javax.swing.JPanel();
        area012 = new javax.swing.JTextField();
        area52 = new javax.swing.JTextField();
        area82 = new javax.swing.JTextField();
        area43 = new javax.swing.JTextField();
        area62 = new javax.swing.JTextField();
        area63 = new javax.swing.JTextField();
        area72 = new javax.swing.JTextField();
        area83 = new javax.swing.JTextField();
        area32 = new javax.swing.JTextField();
        area113 = new javax.swing.JTextField();
        area102 = new javax.swing.JTextField();
        area013 = new javax.swing.JTextField();
        area73 = new javax.swing.JTextField();
        area33 = new javax.swing.JTextField();
        area112 = new javax.swing.JTextField();
        area93 = new javax.swing.JTextField();
        area92 = new javax.swing.JTextField();
        area103 = new javax.swing.JTextField();
        area23 = new javax.swing.JTextField();
        area53 = new javax.swing.JTextField();
        area42 = new javax.swing.JTextField();
        area22 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        area132 = new javax.swing.JTextField();
        area133 = new javax.swing.JTextField();
        area122 = new javax.swing.JTextField();
        area123 = new javax.swing.JTextField();
        jPanel15 = new javax.swing.JPanel();
        cajapH = new javax.swing.JTextField();
        jPanel42 = new javax.swing.JPanel();
        cajaConductividad = new javax.swing.JTextField();
        etiquetaConductividadIn = new javax.swing.JLabel();
        comboConductividad = new javax.swing.JComboBox<>();
        jPanel41 = new javax.swing.JPanel();
        cajaDQO = new javax.swing.JTextField();
        etiquetaDQOIn = new javax.swing.JLabel();
        comboDQO = new javax.swing.JComboBox<>();
        jPanel40 = new javax.swing.JPanel();
        cajaDBO = new javax.swing.JTextField();
        etiquetaDBOIn = new javax.swing.JLabel();
        comboDBO = new javax.swing.JComboBox<>();
        jPanel39 = new javax.swing.JPanel();
        cajaSolidos10 = new javax.swing.JTextField();
        etiquetaSolidos10In = new javax.swing.JLabel();
        comboSolidos10 = new javax.swing.JComboBox<>();
        jPanel34 = new javax.swing.JPanel();
        cajaSolidos120 = new javax.swing.JTextField();
        etiquetaSolidos120In = new javax.swing.JLabel();
        comboSolidos120 = new javax.swing.JComboBox<>();
        jPanel36 = new javax.swing.JPanel();
        cajaDetergentes = new javax.swing.JTextField();
        etiquetaDetergentesIn = new javax.swing.JLabel();
        comboDetergentes = new javax.swing.JComboBox<>();
        jPanel38 = new javax.swing.JPanel();
        cajaFosforo = new javax.swing.JTextField();
        etiquetaFosforoIn = new javax.swing.JLabel();
        comboFosforo = new javax.swing.JComboBox<>();
        jPanel37 = new javax.swing.JPanel();
        cajaGrasas = new javax.swing.JTextField();
        etiquetaGrasasIn = new javax.swing.JLabel();
        comboGrasas = new javax.swing.JComboBox<>();
        jPanel35 = new javax.swing.JPanel();
        cajaNitrogeno = new javax.swing.JTextField();
        etiquetaNitrogenoIn = new javax.swing.JLabel();
        comboNitrogeno = new javax.swing.JComboBox<>();
        jPanel27 = new javax.swing.JPanel();
        cajaSustancias = new javax.swing.JTextField();
        etiquetaSustanciasIn = new javax.swing.JLabel();
        comboSustancias = new javax.swing.JComboBox<>();
        jPanel31 = new javax.swing.JPanel();
        cajaColiformesFecales = new javax.swing.JTextField();
        etiquetaColiformesFecalesIn = new javax.swing.JLabel();
        comboColiformesFecales = new javax.swing.JComboBox<>();
        jPanel32 = new javax.swing.JPanel();
        cajaHidrocarburos = new javax.swing.JTextField();
        etiquetaHidrocarburosIn = new javax.swing.JLabel();
        comboHidrocarburos = new javax.swing.JComboBox<>();
        jPanel33 = new javax.swing.JPanel();
        cajaNitratos = new javax.swing.JTextField();
        etiquetaNitratosIn = new javax.swing.JLabel();
        comboNitratos = new javax.swing.JComboBox<>();
        jPanel43 = new javax.swing.JPanel();
        cajaCloro = new javax.swing.JTextField();
        etiquetaCloroIn = new javax.swing.JLabel();
        comboCloro = new javax.swing.JComboBox<>();
        area134 = new javax.swing.JTextField();
        area135 = new javax.swing.JTextField();
        area136 = new javax.swing.JTextField();
        area137 = new javax.swing.JTextField();
        jPanel44 = new javax.swing.JPanel();
        cajaColiformesTotales = new javax.swing.JTextField();
        etiquetaColiformesTotalesIn = new javax.swing.JLabel();
        comboColiformesTotales = new javax.swing.JComboBox<>();
        area124 = new javax.swing.JTextField();
        area125 = new javax.swing.JTextField();
        jPanel45 = new javax.swing.JPanel();
        cajaEscherichia = new javax.swing.JTextField();
        etiquetaEscherichiaIn = new javax.swing.JLabel();
        comboEscherichia = new javax.swing.JComboBox<>();
        area126 = new javax.swing.JTextField();
        area127 = new javax.swing.JTextField();
        jPanel46 = new javax.swing.JPanel();
        cajaSulfuros = new javax.swing.JTextField();
        etiquetaSulfurosIn = new javax.swing.JLabel();
        comboSulfuros = new javax.swing.JComboBox<>();
        area138 = new javax.swing.JTextField();
        area139 = new javax.swing.JTextField();
        jPanel22 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        cajaFechaAnalisis = new com.toedter.calendar.JDateChooser();
        checkConclusion = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jPanel1.setPreferredSize(new java.awt.Dimension(730, 450));
        jPanel1.setLayout(new java.awt.GridBagLayout());

        botonGenerar.setText("Generar main.resources.reporte");
        botonGenerar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonGenerarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(20, 0, 0, 50);
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
        etiquetaTitulo.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        etiquetaTitulo.setText("Agregar resultados de análisis");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(20, 20, 20, 0);
        jPanel1.add(etiquetaTitulo, gridBagConstraints);

        jPanel21.setLayout(new java.awt.GridBagLayout());

        jPanel16.setLayout(new java.awt.GridBagLayout());

        jPanel19.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 0, 1, new java.awt.Color(0, 0, 0)));
        jPanel19.setMaximumSize(new java.awt.Dimension(230, 23));
        jPanel19.setMinimumSize(new java.awt.Dimension(230, 23));
        jPanel19.setPreferredSize(new java.awt.Dimension(230, 23));
        jPanel19.setLayout(new java.awt.GridBagLayout());

        jLabel18.setBackground(new java.awt.Color(0, 0, 0));
        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setText("DETERMINACIONES");
        jLabel18.setToolTipText("");
        jLabel18.setMinimumSize(new java.awt.Dimension(150, 30));
        jLabel18.setPreferredSize(new java.awt.Dimension(175, 10));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 80;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        jPanel19.add(jLabel18, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        jPanel16.add(jPanel19, gridBagConstraints);

        jPanel7.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 0, 1, new java.awt.Color(0, 0, 0)));
        jPanel7.setMaximumSize(new java.awt.Dimension(230, 24));
        jPanel7.setMinimumSize(new java.awt.Dimension(230, 24));
        jPanel7.setPreferredSize(new java.awt.Dimension(230, 24));
        jPanel7.setLayout(new java.awt.GridBagLayout());

        etiquetaPh.setBackground(new java.awt.Color(0, 0, 0));
        etiquetaPh.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        etiquetaPh.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        etiquetaPh.setText(" pH");
        etiquetaPh.setToolTipText("");
        etiquetaPh.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        etiquetaPh.setMinimumSize(new java.awt.Dimension(150, 30));
        etiquetaPh.setPreferredSize(new java.awt.Dimension(175, 10));
        etiquetaPh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                etiquetaPhMousePressed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 80;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        jPanel7.add(etiquetaPh, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        jPanel16.add(jPanel7, gridBagConstraints);

        jPanel4.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 0, 1, new java.awt.Color(0, 0, 0)));
        jPanel4.setMaximumSize(new java.awt.Dimension(230, 24));
        jPanel4.setMinimumSize(new java.awt.Dimension(230, 24));
        jPanel4.setPreferredSize(new java.awt.Dimension(230, 24));
        jPanel4.setLayout(new java.awt.GridBagLayout());

        etiquetaNitrogeno.setBackground(new java.awt.Color(0, 0, 0));
        etiquetaNitrogeno.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        etiquetaNitrogeno.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        etiquetaNitrogeno.setText("NITRÓGENO TOTAL");
        etiquetaNitrogeno.setToolTipText("");
        etiquetaNitrogeno.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        etiquetaNitrogeno.setMinimumSize(new java.awt.Dimension(150, 30));
        etiquetaNitrogeno.setPreferredSize(new java.awt.Dimension(175, 10));
        etiquetaNitrogeno.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                etiquetaNitrogenoMousePressed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 80;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        jPanel4.add(etiquetaNitrogeno, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        jPanel16.add(jPanel4, gridBagConstraints);

        jPanel8.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 0, 1, new java.awt.Color(0, 0, 0)));
        jPanel8.setMaximumSize(new java.awt.Dimension(230, 24));
        jPanel8.setMinimumSize(new java.awt.Dimension(230, 24));
        jPanel8.setPreferredSize(new java.awt.Dimension(230, 24));
        jPanel8.setLayout(new java.awt.GridBagLayout());

        etiquetaDQO.setBackground(new java.awt.Color(0, 0, 0));
        etiquetaDQO.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        etiquetaDQO.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        etiquetaDQO.setText("DEMANDA QUÍMICA DE OXÍGENO (DQO)     ");
        etiquetaDQO.setToolTipText("");
        etiquetaDQO.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        etiquetaDQO.setMinimumSize(new java.awt.Dimension(150, 30));
        etiquetaDQO.setPreferredSize(new java.awt.Dimension(175, 10));
        etiquetaDQO.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                etiquetaDQOMousePressed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 80;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        jPanel8.add(etiquetaDQO, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        jPanel16.add(jPanel8, gridBagConstraints);

        jPanel14.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 0, 1, new java.awt.Color(0, 0, 0)));
        jPanel14.setMaximumSize(new java.awt.Dimension(230, 24));
        jPanel14.setMinimumSize(new java.awt.Dimension(230, 24));
        jPanel14.setPreferredSize(new java.awt.Dimension(230, 24));
        jPanel14.setLayout(new java.awt.GridBagLayout());

        etiquetaColiformesFecales.setBackground(new java.awt.Color(0, 0, 0));
        etiquetaColiformesFecales.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        etiquetaColiformesFecales.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        etiquetaColiformesFecales.setText("COLIFORMES FECALES");
        etiquetaColiformesFecales.setToolTipText("");
        etiquetaColiformesFecales.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        etiquetaColiformesFecales.setMinimumSize(new java.awt.Dimension(150, 30));
        etiquetaColiformesFecales.setPreferredSize(new java.awt.Dimension(175, 10));
        etiquetaColiformesFecales.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                etiquetaColiformesFecalesMousePressed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 79;
        gridBagConstraints.ipady = -1;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        jPanel14.add(etiquetaColiformesFecales, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        jPanel16.add(jPanel14, gridBagConstraints);

        jPanel12.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 0, 1, new java.awt.Color(0, 0, 0)));
        jPanel12.setMaximumSize(new java.awt.Dimension(230, 24));
        jPanel12.setMinimumSize(new java.awt.Dimension(230, 24));
        jPanel12.setPreferredSize(new java.awt.Dimension(230, 24));
        jPanel12.setLayout(new java.awt.GridBagLayout());

        etiquetaFosforo.setBackground(new java.awt.Color(0, 0, 0));
        etiquetaFosforo.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        etiquetaFosforo.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        etiquetaFosforo.setText("FÓSFORO TOTAL");
        etiquetaFosforo.setToolTipText("");
        etiquetaFosforo.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        etiquetaFosforo.setMinimumSize(new java.awt.Dimension(150, 30));
        etiquetaFosforo.setPreferredSize(new java.awt.Dimension(175, 10));
        etiquetaFosforo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                etiquetaFosforoMousePressed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 80;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        jPanel12.add(etiquetaFosforo, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        jPanel16.add(jPanel12, gridBagConstraints);

        jPanel2.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 0, 1, new java.awt.Color(0, 0, 0)));
        jPanel2.setMaximumSize(new java.awt.Dimension(230, 24));
        jPanel2.setMinimumSize(new java.awt.Dimension(230, 24));
        jPanel2.setPreferredSize(new java.awt.Dimension(230, 24));
        jPanel2.setLayout(new java.awt.GridBagLayout());

        etiquetaConductividad.setBackground(new java.awt.Color(0, 0, 0));
        etiquetaConductividad.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        etiquetaConductividad.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        etiquetaConductividad.setText("CONDUCTIVIDAD");
        etiquetaConductividad.setToolTipText("");
        etiquetaConductividad.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        etiquetaConductividad.setMinimumSize(new java.awt.Dimension(150, 30));
        etiquetaConductividad.setPreferredSize(new java.awt.Dimension(175, 10));
        etiquetaConductividad.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                etiquetaConductividadMousePressed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 80;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        jPanel2.add(etiquetaConductividad, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        jPanel16.add(jPanel2, gridBagConstraints);

        jPanel13.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 0, 1, new java.awt.Color(0, 0, 0)));
        jPanel13.setMaximumSize(new java.awt.Dimension(230, 24));
        jPanel13.setMinimumSize(new java.awt.Dimension(230, 24));
        jPanel13.setPreferredSize(new java.awt.Dimension(230, 24));
        jPanel13.setLayout(new java.awt.GridBagLayout());

        etiquetaSustancias.setBackground(new java.awt.Color(0, 0, 0));
        etiquetaSustancias.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        etiquetaSustancias.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        etiquetaSustancias.setText("SUSTANCIAS SOLUBLES EN ETER ETILICO");
        etiquetaSustancias.setToolTipText("");
        etiquetaSustancias.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        etiquetaSustancias.setMinimumSize(new java.awt.Dimension(150, 30));
        etiquetaSustancias.setPreferredSize(new java.awt.Dimension(175, 10));
        etiquetaSustancias.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                etiquetaSustanciasMousePressed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 80;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        jPanel13.add(etiquetaSustancias, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        jPanel16.add(jPanel13, gridBagConstraints);

        jPanel11.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 0, 1, new java.awt.Color(0, 0, 0)));
        jPanel11.setMaximumSize(new java.awt.Dimension(230, 24));
        jPanel11.setMinimumSize(new java.awt.Dimension(230, 24));
        jPanel11.setPreferredSize(new java.awt.Dimension(230, 24));
        jPanel11.setLayout(new java.awt.GridBagLayout());

        etiquetaGrasas.setBackground(new java.awt.Color(0, 0, 0));
        etiquetaGrasas.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        etiquetaGrasas.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        etiquetaGrasas.setText("GRASAS Y ACEITES");
        etiquetaGrasas.setToolTipText("");
        etiquetaGrasas.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        etiquetaGrasas.setMinimumSize(new java.awt.Dimension(150, 30));
        etiquetaGrasas.setPreferredSize(new java.awt.Dimension(175, 10));
        etiquetaGrasas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                etiquetaGrasasMousePressed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 80;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        jPanel11.add(etiquetaGrasas, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        jPanel16.add(jPanel11, gridBagConstraints);

        jPanel9.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 0, 1, new java.awt.Color(0, 0, 0)));
        jPanel9.setMaximumSize(new java.awt.Dimension(230, 24));
        jPanel9.setMinimumSize(new java.awt.Dimension(230, 24));
        jPanel9.setPreferredSize(new java.awt.Dimension(230, 24));
        jPanel9.setLayout(new java.awt.GridBagLayout());

        etiquetaSolidos120.setBackground(new java.awt.Color(0, 0, 0));
        etiquetaSolidos120.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        etiquetaSolidos120.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        etiquetaSolidos120.setText("SÓLIDOS DISUELTOS SEDIMENTABLES EN 120’");
        etiquetaSolidos120.setToolTipText("");
        etiquetaSolidos120.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        etiquetaSolidos120.setMinimumSize(new java.awt.Dimension(150, 30));
        etiquetaSolidos120.setPreferredSize(new java.awt.Dimension(175, 10));
        etiquetaSolidos120.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                etiquetaSolidos120MousePressed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 80;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        jPanel9.add(etiquetaSolidos120, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        jPanel16.add(jPanel9, gridBagConstraints);

        jPanel5.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 0, 1, new java.awt.Color(0, 0, 0)));
        jPanel5.setMaximumSize(new java.awt.Dimension(230, 24));
        jPanel5.setMinimumSize(new java.awt.Dimension(230, 24));
        jPanel5.setPreferredSize(new java.awt.Dimension(230, 24));
        jPanel5.setLayout(new java.awt.GridBagLayout());

        etiquetaSolidos10.setBackground(new java.awt.Color(0, 0, 0));
        etiquetaSolidos10.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        etiquetaSolidos10.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        etiquetaSolidos10.setText("SÓLIDOS DISUELTOS SEDIMENTABLES EN 10’");
        etiquetaSolidos10.setToolTipText("");
        etiquetaSolidos10.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        etiquetaSolidos10.setMinimumSize(new java.awt.Dimension(150, 30));
        etiquetaSolidos10.setPreferredSize(new java.awt.Dimension(175, 10));
        etiquetaSolidos10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                etiquetaSolidos10MousePressed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 80;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        jPanel5.add(etiquetaSolidos10, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        jPanel16.add(jPanel5, gridBagConstraints);

        jPanel10.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 0, 1, new java.awt.Color(0, 0, 0)));
        jPanel10.setMaximumSize(new java.awt.Dimension(230, 24));
        jPanel10.setMinimumSize(new java.awt.Dimension(230, 24));
        jPanel10.setPreferredSize(new java.awt.Dimension(230, 24));
        jPanel10.setLayout(new java.awt.GridBagLayout());

        etiquetaDetergentes.setBackground(new java.awt.Color(0, 0, 0));
        etiquetaDetergentes.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        etiquetaDetergentes.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        etiquetaDetergentes.setText("DETERGENTES");
        etiquetaDetergentes.setToolTipText("");
        etiquetaDetergentes.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        etiquetaDetergentes.setMinimumSize(new java.awt.Dimension(150, 30));
        etiquetaDetergentes.setPreferredSize(new java.awt.Dimension(175, 10));
        etiquetaDetergentes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                etiquetaDetergentesMousePressed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 80;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        jPanel10.add(etiquetaDetergentes, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        jPanel16.add(jPanel10, gridBagConstraints);

        jPanel3.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 0, 1, new java.awt.Color(0, 0, 0)));
        jPanel3.setMaximumSize(new java.awt.Dimension(230, 24));
        jPanel3.setMinimumSize(new java.awt.Dimension(230, 24));
        jPanel3.setPreferredSize(new java.awt.Dimension(230, 24));
        jPanel3.setLayout(new java.awt.GridBagLayout());

        etiquetaDBO.setBackground(new java.awt.Color(0, 0, 0));
        etiquetaDBO.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        etiquetaDBO.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        etiquetaDBO.setText("DEMANDA BIOQUÍMICA DE OXÍGENO (DBO)");
        etiquetaDBO.setToolTipText("");
        etiquetaDBO.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        etiquetaDBO.setMinimumSize(new java.awt.Dimension(150, 30));
        etiquetaDBO.setPreferredSize(new java.awt.Dimension(175, 10));
        etiquetaDBO.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                etiquetaDBOMousePressed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 80;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        jPanel3.add(etiquetaDBO, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        jPanel16.add(jPanel3, gridBagConstraints);

        jPanel23.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 0, 1, new java.awt.Color(0, 0, 0)));
        jPanel23.setMaximumSize(new java.awt.Dimension(230, 24));
        jPanel23.setMinimumSize(new java.awt.Dimension(230, 24));
        jPanel23.setPreferredSize(new java.awt.Dimension(230, 24));
        jPanel23.setLayout(new java.awt.GridBagLayout());

        etiquetaHidrocarburos.setBackground(new java.awt.Color(0, 0, 0));
        etiquetaHidrocarburos.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        etiquetaHidrocarburos.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        etiquetaHidrocarburos.setText("HIDROCARBUROS TOTALES DE PETRÓLEO (IR)");
        etiquetaHidrocarburos.setToolTipText("");
        etiquetaHidrocarburos.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        etiquetaHidrocarburos.setMinimumSize(new java.awt.Dimension(150, 30));
        etiquetaHidrocarburos.setPreferredSize(new java.awt.Dimension(175, 10));
        etiquetaHidrocarburos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                etiquetaHidrocarburosMousePressed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 79;
        gridBagConstraints.ipady = -1;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        jPanel23.add(etiquetaHidrocarburos, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 15;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        jPanel16.add(jPanel23, gridBagConstraints);

        jPanel24.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 0, 1, new java.awt.Color(0, 0, 0)));
        jPanel24.setMaximumSize(new java.awt.Dimension(230, 24));
        jPanel24.setMinimumSize(new java.awt.Dimension(230, 24));
        jPanel24.setPreferredSize(new java.awt.Dimension(230, 24));
        jPanel24.setLayout(new java.awt.GridBagLayout());

        etiquetaNitratos.setBackground(new java.awt.Color(0, 0, 0));
        etiquetaNitratos.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        etiquetaNitratos.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        etiquetaNitratos.setText("NITRATOS");
        etiquetaNitratos.setToolTipText("");
        etiquetaNitratos.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        etiquetaNitratos.setMinimumSize(new java.awt.Dimension(150, 30));
        etiquetaNitratos.setPreferredSize(new java.awt.Dimension(175, 10));
        etiquetaNitratos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                etiquetaNitratosMousePressed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 79;
        gridBagConstraints.ipady = -1;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        jPanel24.add(etiquetaNitratos, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 16;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        jPanel16.add(jPanel24, gridBagConstraints);

        jPanel25.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 0, 1, new java.awt.Color(0, 0, 0)));
        jPanel25.setMaximumSize(new java.awt.Dimension(230, 24));
        jPanel25.setMinimumSize(new java.awt.Dimension(230, 24));
        jPanel25.setPreferredSize(new java.awt.Dimension(230, 24));
        jPanel25.setLayout(new java.awt.GridBagLayout());

        etiquetaCloro.setBackground(new java.awt.Color(0, 0, 0));
        etiquetaCloro.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        etiquetaCloro.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        etiquetaCloro.setText("CLORO");
        etiquetaCloro.setToolTipText("");
        etiquetaCloro.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        etiquetaCloro.setMinimumSize(new java.awt.Dimension(150, 30));
        etiquetaCloro.setPreferredSize(new java.awt.Dimension(175, 10));
        etiquetaCloro.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                etiquetaCloroMousePressed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 79;
        gridBagConstraints.ipady = -1;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        jPanel25.add(etiquetaCloro, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 17;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        jPanel16.add(jPanel25, gridBagConstraints);

        jPanel18.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 0, 1, new java.awt.Color(0, 0, 0)));
        jPanel18.setMaximumSize(new java.awt.Dimension(230, 24));
        jPanel18.setMinimumSize(new java.awt.Dimension(230, 24));
        jPanel18.setPreferredSize(new java.awt.Dimension(230, 24));
        jPanel18.setLayout(new java.awt.GridBagLayout());

        etiquetaColiformesTotales.setBackground(new java.awt.Color(0, 0, 0));
        etiquetaColiformesTotales.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        etiquetaColiformesTotales.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        etiquetaColiformesTotales.setText("COLIFORMES TOTALES");
        etiquetaColiformesTotales.setToolTipText("");
        etiquetaColiformesTotales.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        etiquetaColiformesTotales.setMinimumSize(new java.awt.Dimension(150, 30));
        etiquetaColiformesTotales.setPreferredSize(new java.awt.Dimension(175, 10));
        etiquetaColiformesTotales.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                etiquetaColiformesTotalesMousePressed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 79;
        gridBagConstraints.ipady = -1;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        jPanel18.add(etiquetaColiformesTotales, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        jPanel16.add(jPanel18, gridBagConstraints);

        jPanel20.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 0, 1, new java.awt.Color(0, 0, 0)));
        jPanel20.setMaximumSize(new java.awt.Dimension(230, 24));
        jPanel20.setMinimumSize(new java.awt.Dimension(230, 24));
        jPanel20.setPreferredSize(new java.awt.Dimension(230, 24));
        jPanel20.setLayout(new java.awt.GridBagLayout());

        etiquetaEscherichia.setBackground(new java.awt.Color(0, 0, 0));
        etiquetaEscherichia.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        etiquetaEscherichia.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        etiquetaEscherichia.setText("ESCHERICHIA COLI");
        etiquetaEscherichia.setToolTipText("");
        etiquetaEscherichia.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        etiquetaEscherichia.setMinimumSize(new java.awt.Dimension(150, 30));
        etiquetaEscherichia.setPreferredSize(new java.awt.Dimension(175, 10));
        etiquetaEscherichia.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                etiquetaEscherichiaMousePressed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 79;
        gridBagConstraints.ipady = -1;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        jPanel20.add(etiquetaEscherichia, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        jPanel16.add(jPanel20, gridBagConstraints);

        jPanel26.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel26.setMaximumSize(new java.awt.Dimension(230, 24));
        jPanel26.setMinimumSize(new java.awt.Dimension(230, 24));
        jPanel26.setPreferredSize(new java.awt.Dimension(230, 24));
        jPanel26.setLayout(new java.awt.GridBagLayout());

        etiquetaSulfuros.setBackground(new java.awt.Color(0, 0, 0));
        etiquetaSulfuros.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        etiquetaSulfuros.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        etiquetaSulfuros.setText("SULFUROS");
        etiquetaSulfuros.setToolTipText("");
        etiquetaSulfuros.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        etiquetaSulfuros.setMinimumSize(new java.awt.Dimension(150, 30));
        etiquetaSulfuros.setPreferredSize(new java.awt.Dimension(175, 10));
        etiquetaSulfuros.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                etiquetaSulfurosMousePressed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 79;
        gridBagConstraints.ipady = -1;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        jPanel26.add(etiquetaSulfuros, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 18;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        jPanel16.add(jPanel26, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jPanel21.add(jPanel16, gridBagConstraints);

        jPanel17.setLayout(new java.awt.GridBagLayout());

        area012.setEditable(false);
        area012.setBackground(new java.awt.Color(255, 255, 255));
        area012.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        area012.setText("6 - 9");
        area012.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        area012.setPreferredSize(new java.awt.Dimension(1, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 150;
        gridBagConstraints.ipady = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel17.add(area012, gridBagConstraints);

        area52.setEditable(false);
        area52.setBackground(new java.awt.Color(255, 255, 255));
        area52.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        area52.setText("0.5 mg/l");
        area52.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        area52.setPreferredSize(new java.awt.Dimension(1, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.ipadx = 150;
        gridBagConstraints.ipady = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel17.add(area52, gridBagConstraints);

        area82.setEditable(false);
        area82.setBackground(new java.awt.Color(255, 255, 255));
        area82.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        area82.setText("50 mg/l");
        area82.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        area82.setPreferredSize(new java.awt.Dimension(1, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.ipadx = 150;
        gridBagConstraints.ipady = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel17.add(area82, gridBagConstraints);

        area43.setEditable(false);
        area43.setBackground(new java.awt.Color(255, 255, 255));
        area43.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        area43.setText("DILUCIONES MÚLTIPLES");
        area43.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        area43.setPreferredSize(new java.awt.Dimension(1, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.ipadx = 150;
        gridBagConstraints.ipady = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel17.add(area43, gridBagConstraints);

        area62.setEditable(false);
        area62.setBackground(new java.awt.Color(255, 255, 255));
        area62.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        area62.setText("1 mg/l");
        area62.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        area62.setPreferredSize(new java.awt.Dimension(1, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.ipadx = 150;
        gridBagConstraints.ipady = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel17.add(area62, gridBagConstraints);

        area63.setEditable(false);
        area63.setBackground(new java.awt.Color(255, 255, 255));
        area63.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        area63.setText("SM 2540 F");
        area63.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        area63.setPreferredSize(new java.awt.Dimension(1, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.ipadx = 150;
        gridBagConstraints.ipady = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel17.add(area63, gridBagConstraints);

        area72.setEditable(false);
        area72.setBackground(new java.awt.Color(255, 255, 255));
        area72.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        area72.setText("5 mg/l");
        area72.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        area72.setPreferredSize(new java.awt.Dimension(1, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.ipadx = 150;
        gridBagConstraints.ipady = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel17.add(area72, gridBagConstraints);

        area83.setEditable(false);
        area83.setBackground(new java.awt.Color(255, 255, 255));
        area83.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        area83.setText("EPA 1664");
        area83.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        area83.setPreferredSize(new java.awt.Dimension(1, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.ipadx = 150;
        gridBagConstraints.ipady = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel17.add(area83, gridBagConstraints);

        area32.setEditable(false);
        area32.setBackground(new java.awt.Color(255, 255, 255));
        area32.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        area32.setText("500 mg/l");
        area32.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        area32.setPreferredSize(new java.awt.Dimension(1, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.ipadx = 150;
        gridBagConstraints.ipady = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel17.add(area32, gridBagConstraints);

        area113.setEditable(false);
        area113.setBackground(new java.awt.Color(255, 255, 255));
        area113.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        area113.setText("STANDARD METHODS (14TH) E");
        area113.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        area113.setPreferredSize(new java.awt.Dimension(1, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.ipadx = 150;
        gridBagConstraints.ipady = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel17.add(area113, gridBagConstraints);

        area102.setEditable(false);
        area102.setBackground(new java.awt.Color(255, 255, 255));
        area102.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        area102.setText("100 mg/l");
        area102.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        area102.setPreferredSize(new java.awt.Dimension(1, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.ipadx = 150;
        gridBagConstraints.ipady = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel17.add(area102, gridBagConstraints);

        area013.setEditable(false);
        area013.setBackground(new java.awt.Color(255, 255, 255));
        area013.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        area013.setText("POTENCIOMETRICO");
        area013.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        area013.setPreferredSize(new java.awt.Dimension(1, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 150;
        gridBagConstraints.ipady = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel17.add(area013, gridBagConstraints);

        area73.setEditable(false);
        area73.setBackground(new java.awt.Color(255, 255, 255));
        area73.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        area73.setText("OSN N° 26");
        area73.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        area73.setPreferredSize(new java.awt.Dimension(1, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.ipadx = 150;
        gridBagConstraints.ipady = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel17.add(area73, gridBagConstraints);

        area33.setEditable(false);
        area33.setBackground(new java.awt.Color(255, 255, 255));
        area33.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        area33.setText("SM 5220 D");
        area33.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        area33.setMinimumSize(new java.awt.Dimension(64, 16));
        area33.setPreferredSize(new java.awt.Dimension(131, 16));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.ipady = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel17.add(area33, gridBagConstraints);

        area112.setEditable(false);
        area112.setBackground(new java.awt.Color(255, 255, 255));
        area112.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        area112.setText("-");
        area112.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        area112.setPreferredSize(new java.awt.Dimension(1, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.ipadx = 150;
        gridBagConstraints.ipady = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel17.add(area112, gridBagConstraints);

        area93.setEditable(false);
        area93.setBackground(new java.awt.Color(255, 255, 255));
        area93.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        area93.setText("SM 4500 PC");
        area93.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        area93.setPreferredSize(new java.awt.Dimension(1, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.ipadx = 150;
        gridBagConstraints.ipady = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel17.add(area93, gridBagConstraints);

        area92.setEditable(false);
        area92.setBackground(new java.awt.Color(255, 255, 255));
        area92.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        area92.setText("5 mg/l");
        area92.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        area92.setPreferredSize(new java.awt.Dimension(1, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.ipadx = 150;
        gridBagConstraints.ipady = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel17.add(area92, gridBagConstraints);

        area103.setEditable(false);
        area103.setBackground(new java.awt.Color(255, 255, 255));
        area103.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        area103.setText("SM 4500 N");
        area103.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        area103.setPreferredSize(new java.awt.Dimension(1, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.ipadx = 150;
        gridBagConstraints.ipady = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel17.add(area103, gridBagConstraints);

        area23.setEditable(false);
        area23.setBackground(new java.awt.Color(255, 255, 255));
        area23.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        area23.setText("POTENCIOMÉTRICO");
        area23.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        area23.setPreferredSize(new java.awt.Dimension(1, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 150;
        gridBagConstraints.ipady = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel17.add(area23, gridBagConstraints);

        area53.setEditable(false);
        area53.setBackground(new java.awt.Color(255, 255, 255));
        area53.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        area53.setText("SM 2540 F");
        area53.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        area53.setPreferredSize(new java.awt.Dimension(1, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.ipadx = 150;
        gridBagConstraints.ipady = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel17.add(area53, gridBagConstraints);

        area42.setEditable(false);
        area42.setBackground(new java.awt.Color(255, 255, 255));
        area42.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        area42.setText("200 mg/l");
        area42.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        area42.setPreferredSize(new java.awt.Dimension(1, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.ipadx = 150;
        gridBagConstraints.ipady = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel17.add(area42, gridBagConstraints);

        area22.setEditable(false);
        area22.setBackground(new java.awt.Color(255, 255, 255));
        area22.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        area22.setText("-");
        area22.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        area22.setPreferredSize(new java.awt.Dimension(1, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 150;
        gridBagConstraints.ipady = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel17.add(area22, gridBagConstraints);

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("<html>VALOR OBTENIDO</html>");
        jLabel4.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        jLabel4.setMinimumSize(new java.awt.Dimension(150, 30));
        jLabel4.setOpaque(true);
        jLabel4.setPreferredSize(new java.awt.Dimension(150, 10));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 30;
        gridBagConstraints.ipady = 13;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel17.add(jLabel4, gridBagConstraints);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("<html>MÉTODO</html>");
        jLabel1.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        jLabel1.setMinimumSize(new java.awt.Dimension(150, 30));
        jLabel1.setOpaque(true);
        jLabel1.setPreferredSize(new java.awt.Dimension(150, 10));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 1;
        gridBagConstraints.ipady = 13;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel17.add(jLabel1, gridBagConstraints);

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("<html>VALOR NORMAL</html>");
        jLabel3.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        jLabel3.setMinimumSize(new java.awt.Dimension(150, 30));
        jLabel3.setOpaque(true);
        jLabel3.setPreferredSize(new java.awt.Dimension(150, 10));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 1;
        gridBagConstraints.ipady = 13;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel17.add(jLabel3, gridBagConstraints);

        area132.setEditable(false);
        area132.setBackground(new java.awt.Color(255, 255, 255));
        area132.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        area132.setText("2 mg/l");
        area132.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        area132.setPreferredSize(new java.awt.Dimension(1, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 15;
        gridBagConstraints.ipadx = 150;
        gridBagConstraints.ipady = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel17.add(area132, gridBagConstraints);

        area133.setEditable(false);
        area133.setBackground(new java.awt.Color(255, 255, 255));
        area133.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        area133.setText("AMB 2613");
        area133.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        area133.setPreferredSize(new java.awt.Dimension(1, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 15;
        gridBagConstraints.ipadx = 150;
        gridBagConstraints.ipady = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel17.add(area133, gridBagConstraints);

        area122.setEditable(false);
        area122.setBackground(new java.awt.Color(255, 255, 255));
        area122.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        area122.setText("10⁵");
        area122.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        area122.setPreferredSize(new java.awt.Dimension(1, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.ipadx = 150;
        gridBagConstraints.ipady = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel17.add(area122, gridBagConstraints);

        area123.setEditable(false);
        area123.setBackground(new java.awt.Color(255, 255, 255));
        area123.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        area123.setText("SMEWW 22nd Edition. APHA (2012) 9221 F");
        area123.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        area123.setPreferredSize(new java.awt.Dimension(1, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.ipadx = 150;
        gridBagConstraints.ipady = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel17.add(area123, gridBagConstraints);

        jPanel15.setBackground(new java.awt.Color(255, 255, 255));
        jPanel15.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        jPanel15.setMaximumSize(new java.awt.Dimension(180, 23));
        jPanel15.setMinimumSize(new java.awt.Dimension(180, 23));
        jPanel15.setPreferredSize(new java.awt.Dimension(180, 23));
        jPanel15.setLayout(new java.awt.GridBagLayout());

        cajapH.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        cajapH.setAlignmentX(0.0F);
        cajapH.setAlignmentY(0.0F);
        cajapH.setBorder(null);
        cajapH.setMaximumSize(new java.awt.Dimension(180, 23));
        cajapH.setMinimumSize(new java.awt.Dimension(173, 23));
        cajapH.setName(""); // NOI18N
        cajapH.setPreferredSize(new java.awt.Dimension(170, 23));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel15.add(cajapH, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        jPanel17.add(jPanel15, gridBagConstraints);

        jPanel42.setBackground(new java.awt.Color(255, 255, 255));
        jPanel42.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        jPanel42.setMaximumSize(new java.awt.Dimension(180, 24));
        jPanel42.setMinimumSize(new java.awt.Dimension(180, 24));
        jPanel42.setPreferredSize(new java.awt.Dimension(180, 24));
        jPanel42.setLayout(new java.awt.GridBagLayout());

        cajaConductividad.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        cajaConductividad.setAlignmentX(0.0F);
        cajaConductividad.setAlignmentY(0.0F);
        cajaConductividad.setBorder(null);
        cajaConductividad.setMaximumSize(new java.awt.Dimension(80, 23));
        cajaConductividad.setMinimumSize(new java.awt.Dimension(80, 23));
        cajaConductividad.setName(""); // NOI18N
        cajaConductividad.setPreferredSize(new java.awt.Dimension(80, 23));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanel42.add(cajaConductividad, gridBagConstraints);

        etiquetaConductividadIn.setText("µS/ml");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipady = -1;
        gridBagConstraints.insets = new java.awt.Insets(0, 2, 0, 2);
        jPanel42.add(etiquetaConductividadIn, gridBagConstraints);

        comboConductividad.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Menor de", "Mayor de", "" }));
        comboConductividad.setUI(new BasicComboBoxUI() {
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
        jPanel42.add(comboConductividad, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        jPanel17.add(jPanel42, gridBagConstraints);

        jPanel41.setBackground(new java.awt.Color(255, 255, 255));
        jPanel41.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        jPanel41.setMaximumSize(new java.awt.Dimension(180, 24));
        jPanel41.setMinimumSize(new java.awt.Dimension(180, 24));
        jPanel41.setPreferredSize(new java.awt.Dimension(180, 24));
        jPanel41.setLayout(new java.awt.GridBagLayout());

        cajaDQO.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        cajaDQO.setAlignmentX(0.0F);
        cajaDQO.setAlignmentY(0.0F);
        cajaDQO.setBorder(null);
        cajaDQO.setMaximumSize(new java.awt.Dimension(90, 21));
        cajaDQO.setMinimumSize(new java.awt.Dimension(90, 21));
        cajaDQO.setName(""); // NOI18N
        cajaDQO.setPreferredSize(new java.awt.Dimension(90, 21));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanel41.add(cajaDQO, gridBagConstraints);

        etiquetaDQOIn.setText("mg/l");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipady = -1;
        gridBagConstraints.insets = new java.awt.Insets(0, 2, 0, 2);
        jPanel41.add(etiquetaDQOIn, gridBagConstraints);

        comboDQO.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Menor de", "Mayor de", "" }));
        comboDQO.setUI(new BasicComboBoxUI() {
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
        jPanel41.add(comboDQO, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        jPanel17.add(jPanel41, gridBagConstraints);

        jPanel40.setBackground(new java.awt.Color(255, 255, 255));
        jPanel40.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        jPanel40.setMaximumSize(new java.awt.Dimension(180, 24));
        jPanel40.setMinimumSize(new java.awt.Dimension(180, 24));
        jPanel40.setPreferredSize(new java.awt.Dimension(180, 24));
        jPanel40.setLayout(new java.awt.GridBagLayout());

        cajaDBO.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        cajaDBO.setAlignmentX(0.0F);
        cajaDBO.setAlignmentY(0.0F);
        cajaDBO.setBorder(null);
        cajaDBO.setMaximumSize(new java.awt.Dimension(90, 21));
        cajaDBO.setMinimumSize(new java.awt.Dimension(90, 21));
        cajaDBO.setName(""); // NOI18N
        cajaDBO.setPreferredSize(new java.awt.Dimension(90, 21));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanel40.add(cajaDBO, gridBagConstraints);

        etiquetaDBOIn.setText("mg/l");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipady = -1;
        gridBagConstraints.insets = new java.awt.Insets(0, 2, 0, 2);
        jPanel40.add(etiquetaDBOIn, gridBagConstraints);

        comboDBO.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Menor de", "Mayor de", "" }));
        comboDBO.setUI(new BasicComboBoxUI() {
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
        jPanel40.add(comboDBO, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        jPanel17.add(jPanel40, gridBagConstraints);

        jPanel39.setBackground(new java.awt.Color(255, 255, 255));
        jPanel39.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        jPanel39.setMaximumSize(new java.awt.Dimension(180, 24));
        jPanel39.setMinimumSize(new java.awt.Dimension(180, 24));
        jPanel39.setPreferredSize(new java.awt.Dimension(180, 24));
        jPanel39.setLayout(new java.awt.GridBagLayout());

        cajaSolidos10.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        cajaSolidos10.setAlignmentX(0.0F);
        cajaSolidos10.setAlignmentY(0.0F);
        cajaSolidos10.setBorder(null);
        cajaSolidos10.setMaximumSize(new java.awt.Dimension(90, 21));
        cajaSolidos10.setMinimumSize(new java.awt.Dimension(90, 21));
        cajaSolidos10.setName(""); // NOI18N
        cajaSolidos10.setPreferredSize(new java.awt.Dimension(90, 21));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanel39.add(cajaSolidos10, gridBagConstraints);

        etiquetaSolidos10In.setText("mg/l");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipady = -1;
        gridBagConstraints.insets = new java.awt.Insets(0, 2, 0, 2);
        jPanel39.add(etiquetaSolidos10In, gridBagConstraints);

        comboSolidos10.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Menor de", "Mayor de", "" }));
        comboSolidos10.setUI(new BasicComboBoxUI() {
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
        jPanel39.add(comboSolidos10, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        jPanel17.add(jPanel39, gridBagConstraints);

        jPanel34.setBackground(new java.awt.Color(255, 255, 255));
        jPanel34.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        jPanel34.setMaximumSize(new java.awt.Dimension(180, 24));
        jPanel34.setMinimumSize(new java.awt.Dimension(180, 24));
        jPanel34.setPreferredSize(new java.awt.Dimension(180, 24));
        jPanel34.setLayout(new java.awt.GridBagLayout());

        cajaSolidos120.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        cajaSolidos120.setAlignmentX(0.0F);
        cajaSolidos120.setAlignmentY(0.0F);
        cajaSolidos120.setBorder(null);
        cajaSolidos120.setMaximumSize(new java.awt.Dimension(90, 21));
        cajaSolidos120.setMinimumSize(new java.awt.Dimension(90, 21));
        cajaSolidos120.setName(""); // NOI18N
        cajaSolidos120.setPreferredSize(new java.awt.Dimension(90, 21));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanel34.add(cajaSolidos120, gridBagConstraints);

        etiquetaSolidos120In.setText("mg/l");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipady = -1;
        gridBagConstraints.insets = new java.awt.Insets(0, 2, 0, 2);
        jPanel34.add(etiquetaSolidos120In, gridBagConstraints);

        comboSolidos120.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Menor de", "Mayor de", "" }));
        comboSolidos120.setUI(new BasicComboBoxUI() {
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
        jPanel34.add(comboSolidos120, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        jPanel17.add(jPanel34, gridBagConstraints);

        jPanel36.setBackground(new java.awt.Color(255, 255, 255));
        jPanel36.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        jPanel36.setMaximumSize(new java.awt.Dimension(180, 24));
        jPanel36.setMinimumSize(new java.awt.Dimension(180, 24));
        jPanel36.setPreferredSize(new java.awt.Dimension(180, 24));
        jPanel36.setLayout(new java.awt.GridBagLayout());

        cajaDetergentes.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        cajaDetergentes.setAlignmentX(0.0F);
        cajaDetergentes.setAlignmentY(0.0F);
        cajaDetergentes.setBorder(null);
        cajaDetergentes.setMaximumSize(new java.awt.Dimension(90, 21));
        cajaDetergentes.setMinimumSize(new java.awt.Dimension(90, 21));
        cajaDetergentes.setName(""); // NOI18N
        cajaDetergentes.setPreferredSize(new java.awt.Dimension(90, 21));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanel36.add(cajaDetergentes, gridBagConstraints);

        etiquetaDetergentesIn.setText("mg/l");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipady = -1;
        gridBagConstraints.insets = new java.awt.Insets(0, 2, 0, 2);
        jPanel36.add(etiquetaDetergentesIn, gridBagConstraints);

        comboDetergentes.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Menor de", "Mayor de", "" }));
        comboDetergentes.setUI(new BasicComboBoxUI() {
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
        jPanel36.add(comboDetergentes, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        jPanel17.add(jPanel36, gridBagConstraints);

        jPanel38.setBackground(new java.awt.Color(255, 255, 255));
        jPanel38.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        jPanel38.setMaximumSize(new java.awt.Dimension(180, 24));
        jPanel38.setMinimumSize(new java.awt.Dimension(180, 24));
        jPanel38.setPreferredSize(new java.awt.Dimension(180, 24));
        jPanel38.setLayout(new java.awt.GridBagLayout());

        cajaFosforo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        cajaFosforo.setAlignmentX(0.0F);
        cajaFosforo.setAlignmentY(0.0F);
        cajaFosforo.setBorder(null);
        cajaFosforo.setMaximumSize(new java.awt.Dimension(90, 21));
        cajaFosforo.setMinimumSize(new java.awt.Dimension(90, 21));
        cajaFosforo.setName(""); // NOI18N
        cajaFosforo.setPreferredSize(new java.awt.Dimension(90, 21));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanel38.add(cajaFosforo, gridBagConstraints);

        etiquetaFosforoIn.setText("mg/l");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipady = -1;
        gridBagConstraints.insets = new java.awt.Insets(0, 2, 0, 2);
        jPanel38.add(etiquetaFosforoIn, gridBagConstraints);

        comboFosforo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Menor de", "Mayor de", "" }));
        comboFosforo.setUI(new BasicComboBoxUI() {
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
        jPanel38.add(comboFosforo, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        jPanel17.add(jPanel38, gridBagConstraints);

        jPanel37.setBackground(new java.awt.Color(255, 255, 255));
        jPanel37.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        jPanel37.setMaximumSize(new java.awt.Dimension(180, 24));
        jPanel37.setMinimumSize(new java.awt.Dimension(180, 24));
        jPanel37.setPreferredSize(new java.awt.Dimension(180, 24));
        jPanel37.setLayout(new java.awt.GridBagLayout());

        cajaGrasas.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        cajaGrasas.setAlignmentX(0.0F);
        cajaGrasas.setAlignmentY(0.0F);
        cajaGrasas.setBorder(null);
        cajaGrasas.setMaximumSize(new java.awt.Dimension(90, 21));
        cajaGrasas.setMinimumSize(new java.awt.Dimension(90, 21));
        cajaGrasas.setName(""); // NOI18N
        cajaGrasas.setPreferredSize(new java.awt.Dimension(90, 21));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanel37.add(cajaGrasas, gridBagConstraints);

        etiquetaGrasasIn.setText("mg/l");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipady = -1;
        gridBagConstraints.insets = new java.awt.Insets(0, 2, 0, 2);
        jPanel37.add(etiquetaGrasasIn, gridBagConstraints);

        comboGrasas.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Menor de", "Mayor de", "" }));
        comboGrasas.setUI(new BasicComboBoxUI() {
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
        jPanel37.add(comboGrasas, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        jPanel17.add(jPanel37, gridBagConstraints);

        jPanel35.setBackground(new java.awt.Color(255, 255, 255));
        jPanel35.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        jPanel35.setMaximumSize(new java.awt.Dimension(180, 24));
        jPanel35.setMinimumSize(new java.awt.Dimension(180, 24));
        jPanel35.setPreferredSize(new java.awt.Dimension(180, 24));
        jPanel35.setLayout(new java.awt.GridBagLayout());

        cajaNitrogeno.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        cajaNitrogeno.setAlignmentX(0.0F);
        cajaNitrogeno.setAlignmentY(0.0F);
        cajaNitrogeno.setBorder(null);
        cajaNitrogeno.setMaximumSize(new java.awt.Dimension(90, 21));
        cajaNitrogeno.setMinimumSize(new java.awt.Dimension(90, 21));
        cajaNitrogeno.setName(""); // NOI18N
        cajaNitrogeno.setPreferredSize(new java.awt.Dimension(90, 21));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel35.add(cajaNitrogeno, gridBagConstraints);

        etiquetaNitrogenoIn.setText("mg/l");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipady = -1;
        gridBagConstraints.insets = new java.awt.Insets(0, 2, 0, 2);
        jPanel35.add(etiquetaNitrogenoIn, gridBagConstraints);

        comboNitrogeno.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Menor de", "Mayor de", "" }));
        comboNitrogeno.setUI(new BasicComboBoxUI() {
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
        jPanel35.add(comboNitrogeno, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        jPanel17.add(jPanel35, gridBagConstraints);

        jPanel27.setBackground(new java.awt.Color(255, 255, 255));
        jPanel27.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        jPanel27.setMaximumSize(new java.awt.Dimension(180, 24));
        jPanel27.setMinimumSize(new java.awt.Dimension(180, 24));
        jPanel27.setPreferredSize(new java.awt.Dimension(180, 24));
        jPanel27.setLayout(new java.awt.GridBagLayout());

        cajaSustancias.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        cajaSustancias.setAlignmentX(0.0F);
        cajaSustancias.setAlignmentY(0.0F);
        cajaSustancias.setBorder(null);
        cajaSustancias.setMaximumSize(new java.awt.Dimension(90, 21));
        cajaSustancias.setMinimumSize(new java.awt.Dimension(90, 21));
        cajaSustancias.setName(""); // NOI18N
        cajaSustancias.setPreferredSize(new java.awt.Dimension(90, 21));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel27.add(cajaSustancias, gridBagConstraints);

        etiquetaSustanciasIn.setText("mg/l");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipady = -1;
        gridBagConstraints.insets = new java.awt.Insets(0, 2, 0, 2);
        jPanel27.add(etiquetaSustanciasIn, gridBagConstraints);

        comboSustancias.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Menor de", "Mayor de", "" }));
        comboSustancias.setUI(new BasicComboBoxUI() {
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
        jPanel27.add(comboSustancias, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 11;
        jPanel17.add(jPanel27, gridBagConstraints);

        jPanel31.setBackground(new java.awt.Color(255, 255, 255));
        jPanel31.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        jPanel31.setMaximumSize(new java.awt.Dimension(180, 24));
        jPanel31.setMinimumSize(new java.awt.Dimension(180, 24));
        jPanel31.setPreferredSize(new java.awt.Dimension(180, 24));
        jPanel31.setLayout(new java.awt.GridBagLayout());

        cajaColiformesFecales.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        cajaColiformesFecales.setAlignmentX(0.0F);
        cajaColiformesFecales.setAlignmentY(0.0F);
        cajaColiformesFecales.setBorder(null);
        cajaColiformesFecales.setMaximumSize(new java.awt.Dimension(70, 21));
        cajaColiformesFecales.setMinimumSize(new java.awt.Dimension(70, 21));
        cajaColiformesFecales.setName(""); // NOI18N
        cajaColiformesFecales.setPreferredSize(new java.awt.Dimension(70, 21));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel31.add(cajaColiformesFecales, gridBagConstraints);

        etiquetaColiformesFecalesIn.setText("NMP/ml");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipady = -1;
        gridBagConstraints.insets = new java.awt.Insets(0, 2, 0, 2);
        jPanel31.add(etiquetaColiformesFecalesIn, gridBagConstraints);

        comboColiformesFecales.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Menor de", "Mayor de", "" }));
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
        jPanel31.add(comboColiformesFecales, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 13;
        jPanel17.add(jPanel31, gridBagConstraints);

        jPanel32.setBackground(new java.awt.Color(255, 255, 255));
        jPanel32.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        jPanel32.setMaximumSize(new java.awt.Dimension(180, 24));
        jPanel32.setMinimumSize(new java.awt.Dimension(180, 24));
        jPanel32.setPreferredSize(new java.awt.Dimension(180, 24));
        jPanel32.setLayout(new java.awt.GridBagLayout());

        cajaHidrocarburos.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        cajaHidrocarburos.setAlignmentX(0.0F);
        cajaHidrocarburos.setAlignmentY(0.0F);
        cajaHidrocarburos.setBorder(null);
        cajaHidrocarburos.setMaximumSize(new java.awt.Dimension(90, 21));
        cajaHidrocarburos.setMinimumSize(new java.awt.Dimension(90, 21));
        cajaHidrocarburos.setName(""); // NOI18N
        cajaHidrocarburos.setPreferredSize(new java.awt.Dimension(90, 21));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel32.add(cajaHidrocarburos, gridBagConstraints);

        etiquetaHidrocarburosIn.setText("mg/l");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipady = -1;
        gridBagConstraints.insets = new java.awt.Insets(0, 2, 0, 2);
        jPanel32.add(etiquetaHidrocarburosIn, gridBagConstraints);

        comboHidrocarburos.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Menor de", "Mayor de", "" }));
        comboHidrocarburos.setUI(new BasicComboBoxUI() {
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
        jPanel32.add(comboHidrocarburos, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 15;
        jPanel17.add(jPanel32, gridBagConstraints);

        jPanel33.setBackground(new java.awt.Color(255, 255, 255));
        jPanel33.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        jPanel33.setMaximumSize(new java.awt.Dimension(180, 24));
        jPanel33.setMinimumSize(new java.awt.Dimension(180, 24));
        jPanel33.setPreferredSize(new java.awt.Dimension(180, 24));
        jPanel33.setLayout(new java.awt.GridBagLayout());

        cajaNitratos.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        cajaNitratos.setAlignmentX(0.0F);
        cajaNitratos.setAlignmentY(0.0F);
        cajaNitratos.setBorder(null);
        cajaNitratos.setMaximumSize(new java.awt.Dimension(90, 21));
        cajaNitratos.setMinimumSize(new java.awt.Dimension(90, 21));
        cajaNitratos.setName(""); // NOI18N
        cajaNitratos.setPreferredSize(new java.awt.Dimension(90, 21));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel33.add(cajaNitratos, gridBagConstraints);

        etiquetaNitratosIn.setText("mg/l");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipady = -1;
        gridBagConstraints.insets = new java.awt.Insets(0, 2, 0, 2);
        jPanel33.add(etiquetaNitratosIn, gridBagConstraints);

        comboNitratos.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Menor de", "Mayor de", "" }));
        comboNitratos.setUI(new BasicComboBoxUI() {
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
        jPanel33.add(comboNitratos, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 16;
        jPanel17.add(jPanel33, gridBagConstraints);

        jPanel43.setBackground(new java.awt.Color(255, 255, 255));
        jPanel43.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        jPanel43.setMaximumSize(new java.awt.Dimension(180, 24));
        jPanel43.setMinimumSize(new java.awt.Dimension(180, 24));
        jPanel43.setPreferredSize(new java.awt.Dimension(180, 24));
        jPanel43.setLayout(new java.awt.GridBagLayout());

        cajaCloro.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        cajaCloro.setAlignmentX(0.0F);
        cajaCloro.setAlignmentY(0.0F);
        cajaCloro.setBorder(null);
        cajaCloro.setMaximumSize(new java.awt.Dimension(90, 21));
        cajaCloro.setMinimumSize(new java.awt.Dimension(90, 21));
        cajaCloro.setName(""); // NOI18N
        cajaCloro.setPreferredSize(new java.awt.Dimension(90, 21));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel43.add(cajaCloro, gridBagConstraints);

        etiquetaCloroIn.setText("mg/l");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipady = -1;
        gridBagConstraints.insets = new java.awt.Insets(0, 2, 0, 2);
        jPanel43.add(etiquetaCloroIn, gridBagConstraints);

        comboCloro.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Menor de", "Mayor de", "" }));
        comboCloro.setUI(new BasicComboBoxUI() {
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
        jPanel43.add(comboCloro, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 17;
        jPanel17.add(jPanel43, gridBagConstraints);

        area134.setEditable(false);
        area134.setBackground(new java.awt.Color(255, 255, 255));
        area134.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        area134.setText("20 mg/l");
        area134.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        area134.setPreferredSize(new java.awt.Dimension(1, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 16;
        gridBagConstraints.ipadx = 150;
        gridBagConstraints.ipady = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel17.add(area134, gridBagConstraints);

        area135.setEditable(false);
        area135.setBackground(new java.awt.Color(255, 255, 255));
        area135.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        area135.setText("1 mg/l");
        area135.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        area135.setPreferredSize(new java.awt.Dimension(1, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 17;
        gridBagConstraints.ipadx = 150;
        gridBagConstraints.ipady = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel17.add(area135, gridBagConstraints);

        area136.setEditable(false);
        area136.setBackground(new java.awt.Color(255, 255, 255));
        area136.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        area136.setText("ISO 7890_3:1988");
        area136.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        area136.setPreferredSize(new java.awt.Dimension(1, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 16;
        gridBagConstraints.ipadx = 150;
        gridBagConstraints.ipady = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel17.add(area136, gridBagConstraints);

        area137.setEditable(false);
        area137.setBackground(new java.awt.Color(255, 255, 255));
        area137.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        area137.setText("FOTOMÉTRICO");
        area137.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        area137.setPreferredSize(new java.awt.Dimension(1, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 17;
        gridBagConstraints.ipadx = 150;
        gridBagConstraints.ipady = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel17.add(area137, gridBagConstraints);

        jPanel44.setBackground(new java.awt.Color(255, 255, 255));
        jPanel44.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        jPanel44.setMaximumSize(new java.awt.Dimension(180, 24));
        jPanel44.setMinimumSize(new java.awt.Dimension(180, 24));
        jPanel44.setPreferredSize(new java.awt.Dimension(180, 24));
        jPanel44.setLayout(new java.awt.GridBagLayout());

        cajaColiformesTotales.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        cajaColiformesTotales.setAlignmentX(0.0F);
        cajaColiformesTotales.setAlignmentY(0.0F);
        cajaColiformesTotales.setBorder(null);
        cajaColiformesTotales.setMaximumSize(new java.awt.Dimension(70, 21));
        cajaColiformesTotales.setMinimumSize(new java.awt.Dimension(70, 21));
        cajaColiformesTotales.setName(""); // NOI18N
        cajaColiformesTotales.setPreferredSize(new java.awt.Dimension(70, 21));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel44.add(cajaColiformesTotales, gridBagConstraints);

        etiquetaColiformesTotalesIn.setText("NMP/ml");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipady = -1;
        gridBagConstraints.insets = new java.awt.Insets(0, 2, 0, 2);
        jPanel44.add(etiquetaColiformesTotalesIn, gridBagConstraints);

        comboColiformesTotales.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Menor de", "Mayor de", "" }));
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
        jPanel44.add(comboColiformesTotales, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 12;
        jPanel17.add(jPanel44, gridBagConstraints);

        area124.setEditable(false);
        area124.setBackground(new java.awt.Color(255, 255, 255));
        area124.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        area124.setText("-");
        area124.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        area124.setPreferredSize(new java.awt.Dimension(1, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.ipadx = 150;
        gridBagConstraints.ipady = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel17.add(area124, gridBagConstraints);

        area125.setEditable(false);
        area125.setBackground(new java.awt.Color(255, 255, 255));
        area125.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        area125.setText("SMEWW 22nd Edition. APHA (2012) 9221 F");
        area125.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        area125.setPreferredSize(new java.awt.Dimension(1, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.ipadx = 150;
        gridBagConstraints.ipady = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel17.add(area125, gridBagConstraints);

        jPanel45.setBackground(new java.awt.Color(255, 255, 255));
        jPanel45.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        jPanel45.setMaximumSize(new java.awt.Dimension(180, 24));
        jPanel45.setMinimumSize(new java.awt.Dimension(180, 24));
        jPanel45.setPreferredSize(new java.awt.Dimension(180, 24));
        jPanel45.setLayout(new java.awt.GridBagLayout());

        cajaEscherichia.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        cajaEscherichia.setAlignmentX(0.0F);
        cajaEscherichia.setAlignmentY(0.0F);
        cajaEscherichia.setBorder(null);
        cajaEscherichia.setMaximumSize(new java.awt.Dimension(60, 21));
        cajaEscherichia.setMinimumSize(new java.awt.Dimension(60, 21));
        cajaEscherichia.setName(""); // NOI18N
        cajaEscherichia.setPreferredSize(new java.awt.Dimension(60, 21));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel45.add(cajaEscherichia, gridBagConstraints);

        etiquetaEscherichiaIn.setText("NMP/100 ml");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipady = -1;
        gridBagConstraints.insets = new java.awt.Insets(0, 2, 0, 2);
        jPanel45.add(etiquetaEscherichiaIn, gridBagConstraints);

        comboEscherichia.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Menor de", "Mayor de", "" }));
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
        gridBagConstraints.gridy = 0;
        jPanel45.add(comboEscherichia, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 14;
        jPanel17.add(jPanel45, gridBagConstraints);

        area126.setEditable(false);
        area126.setBackground(new java.awt.Color(255, 255, 255));
        area126.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        area126.setText("-");
        area126.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        area126.setPreferredSize(new java.awt.Dimension(1, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.ipadx = 150;
        gridBagConstraints.ipady = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel17.add(area126, gridBagConstraints);

        area127.setEditable(false);
        area127.setBackground(new java.awt.Color(255, 255, 255));
        area127.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        area127.setText("SMEWW 22nd. Edition. APHA (2012) 9221 F");
        area127.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        area127.setPreferredSize(new java.awt.Dimension(1, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.ipadx = 150;
        gridBagConstraints.ipady = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel17.add(area127, gridBagConstraints);

        jPanel46.setBackground(new java.awt.Color(255, 255, 255));
        jPanel46.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 1, 1, new java.awt.Color(0, 0, 0)));
        jPanel46.setMaximumSize(new java.awt.Dimension(180, 24));
        jPanel46.setMinimumSize(new java.awt.Dimension(180, 24));
        jPanel46.setPreferredSize(new java.awt.Dimension(180, 24));
        jPanel46.setLayout(new java.awt.GridBagLayout());

        cajaSulfuros.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        cajaSulfuros.setAlignmentX(0.0F);
        cajaSulfuros.setAlignmentY(0.0F);
        cajaSulfuros.setBorder(null);
        cajaSulfuros.setMaximumSize(new java.awt.Dimension(90, 21));
        cajaSulfuros.setMinimumSize(new java.awt.Dimension(90, 21));
        cajaSulfuros.setName(""); // NOI18N
        cajaSulfuros.setPreferredSize(new java.awt.Dimension(90, 21));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel46.add(cajaSulfuros, gridBagConstraints);

        etiquetaSulfurosIn.setText("mg/l");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipady = -1;
        gridBagConstraints.insets = new java.awt.Insets(0, 2, 0, 2);
        jPanel46.add(etiquetaSulfurosIn, gridBagConstraints);

        comboSulfuros.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Menor de", "Mayor de", "" }));
        comboSulfuros.setUI(new BasicComboBoxUI() {
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
        jPanel46.add(comboSulfuros, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 18;
        jPanel17.add(jPanel46, gridBagConstraints);

        area138.setEditable(false);
        area138.setBackground(new java.awt.Color(255, 255, 255));
        area138.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        area138.setText("1 mg/l");
        area138.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 1, 1, new java.awt.Color(0, 0, 0)));
        area138.setPreferredSize(new java.awt.Dimension(1, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 18;
        gridBagConstraints.ipadx = 150;
        gridBagConstraints.ipady = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel17.add(area138, gridBagConstraints);

        area139.setEditable(false);
        area139.setBackground(new java.awt.Color(255, 255, 255));
        area139.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        area139.setText("ESPECTROFOTOMETRICO");
        area139.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 1, 1, new java.awt.Color(0, 0, 0)));
        area139.setPreferredSize(new java.awt.Dimension(1, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 18;
        gridBagConstraints.ipadx = 150;
        gridBagConstraints.ipady = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel17.add(area139, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 20);
        jPanel21.add(jPanel17, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        jPanel1.add(jPanel21, gridBagConstraints);

        jPanel22.setLayout(new java.awt.GridBagLayout());

        jLabel19.setText("Fecha de análisis");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 20);
        jPanel22.add(jLabel19, gridBagConstraints);

        cajaFechaAnalisis.setDateFormatString("dd-MM-yyyy");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 40;
        jPanel22.add(cajaFechaAnalisis, gridBagConstraints);
        long millis=System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(millis);
        cajaFechaAnalisis.setDate(date);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        jPanel1.add(jPanel22, gridBagConstraints);

        checkConclusion.setText("Crear conclusión");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(0, 200, 0, 0);
        jPanel1.add(checkConclusion, gridBagConstraints);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 847, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 602, Short.MAX_VALUE)
                .addContainerGap())
        );

        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false), "Cerrar");
        ap.put("Cerrar", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e){
                dispose();
            }
        });

        pack();
    }

    private void botonGenerarActionPerformed(java.awt.event.ActionEvent evt) {
        double ph = -2;
        String conductividad = "-2";
        String dqo = "-2";
        String dbo = "-2";
        String solidos10 = "-2";
        String solidos120 = "-2";
        String detergentes = "-2";
        String grasas = "-2";
        String fosforo = "-2";
        String nitrogeno = "-2";
        String sustancias = "-2";
        String coliformesTotales = "-2";
        String coliformesFecales = "-2";
        String escherichia = "-2";
        String hidrocarburos = "-2";
        String nitratos = "-2";
        String cloro = "-2";
        String sulfuros = "-2";
        Map m = new HashMap<>();

        if (cajapH.isEnabled()) {
            ph = Double.parseDouble(cajapH.getText());
        }
        if (cajaConductividad.isEnabled()) {
            conductividad = comboConductividad.getSelectedItem().toString() + " " + cajaConductividad.getText() + " " + etiquetaConductividadIn.getText();
        }
        if (cajaDQO.isEnabled()) {
            dqo = comboDQO.getSelectedItem().toString() + " " + cajaDQO.getText() + " " + etiquetaDQOIn.getText();
        }
        if (cajaDBO.isEnabled()) {
            dbo = comboDBO.getSelectedItem().toString() + " " + cajaDBO.getText() + " " + etiquetaDBOIn.getText();
        }
        if (cajaSolidos10.isEnabled()) {
            solidos10 = comboSolidos10.getSelectedItem().toString() + " "
                    + cajaSolidos10.getText() + " " + etiquetaSolidos10In.getText();
        }
        if (cajaSolidos120.isEnabled()) {
            solidos120 = comboSolidos120.getSelectedItem().toString() + " "
                    + cajaSolidos120.getText() + " " + etiquetaSolidos120In.getText();
        }
        if (cajaDetergentes.isEnabled()) {
            detergentes = comboDetergentes.getSelectedItem().toString() + " "
                    + cajaDetergentes.getText() + " " + etiquetaDetergentesIn.getText();
        }
        if (cajaGrasas.isEnabled()) {
            grasas = comboGrasas.getSelectedItem().toString() + " "
                    + cajaGrasas.getText() + " " + etiquetaGrasasIn.getText();
        }
        if (cajaFosforo.isEnabled()) {
            fosforo = comboFosforo.getSelectedItem().toString() + " "
                    + cajaFosforo.getText() + " " + etiquetaFosforoIn.getText();
        }
        if (cajaNitrogeno.isEnabled()) {
            nitrogeno = comboNitrogeno.getSelectedItem().toString() + " "
                    + cajaNitrogeno.getText() + " " + etiquetaNitrogenoIn.getText();
        }

        if (cajaSustancias.isEnabled()) {
            sustancias = comboSustancias.getSelectedItem().toString() + " "
                    + cajaSustancias.getText() + " " + etiquetaSustanciasIn.getText();
        }
        if (cajaColiformesFecales.isEnabled()) {
            coliformesFecales = comboColiformesFecales.getSelectedItem().toString() + " "
                    + cajaColiformesFecales.getText() + " " + etiquetaColiformesFecalesIn.getText();
        }
        if (cajaColiformesTotales.isEnabled()) {
            coliformesTotales = comboColiformesTotales.getSelectedItem().toString() + " "
                    + cajaColiformesTotales.getText() + " " + etiquetaColiformesTotalesIn.getText();
        }
        if (cajaEscherichia.isEnabled()) {
            escherichia = comboEscherichia.getSelectedItem().toString() + " "
                    + cajaEscherichia.getText() + " " + etiquetaEscherichiaIn.getText();
        }
        if (cajaHidrocarburos.isEnabled()) {
            hidrocarburos = comboHidrocarburos.getSelectedItem().toString() + " " + cajaHidrocarburos.getText() + " " + etiquetaHidrocarburosIn.getText();
        }
        if (cajaNitratos.isEnabled()) {
            nitratos = comboNitratos.getSelectedItem().toString() + " " + cajaNitratos.getText() + " " + etiquetaNitratosIn.getText();
        }
        if (cajaCloro.isEnabled()) {
            cloro = comboCloro.getSelectedItem().toString() + " " + cajaCloro.getText() + " " + etiquetaCloroIn.getText();
        }
        if (cajaSulfuros.isEnabled()) {
            sulfuros = comboSulfuros.getSelectedItem().toString() + " " + cajaSulfuros.getText() + " " + etiquetaSulfurosIn.getText();
        }
        m.put("ph", ph);
        m.put("conductividad", conductividad);
        m.put("dqo", dqo);
        m.put("dbo", dbo);
        m.put("solidos10", solidos10);
        m.put("solidos120", solidos120);
        m.put("detergentes", detergentes);
        m.put("grasas", grasas);
        m.put("fosforo", fosforo);
        m.put("nitrogeno", nitrogeno);
        m.put("sustancias", sustancias);
        m.put("coliformesFecales", coliformesFecales);
        m.put("coliformesTotales", coliformesTotales);
        m.put("escherichia", escherichia);
        m.put("idmuestras", id);
        m.put("hidrocarburos", hidrocarburos);
        m.put("nitratos", nitratos);
        m.put("cloro", cloro);
        m.put("sulfuros", sulfuros);
        String conclusion;
        String observaciones;
        if (editar) {
            File rv = new File(c.recuperarRutas("Reportes") + "\\" + pdf);
            File rn = new File(c.recuperarRutas("Reportes") + "\\(BORRADO) " + pdf);
            rv.renameTo(rn);
            if (resultadoRepository.editarResultadosEfluentesTipo(m)) {
                if (checkConclusion.isSelected()) {
                    conclusion = crearConclusion();
                    muestraRepository.guardarConclusion(conclusion, id);
                } else {
                    muestraRepository.guardarConclusion(null, id);
                }
                observaciones = JOptionPane.showInputDialog("Ingrese las observaciones");
                observaciones = observaciones.isBlank() ? "" : observaciones.trim().endsWith(".") ? observaciones : observaciones + ".";
                muestraRepository.guardarObservaciones(observaciones, id);
                this.dispose();
                c.generarReporteEfluentesCloaca(id, procedencia);;
            }
        } else {
            if (resultadoRepository.guardarResultadosEfluentesTipo(m)) {
                if (checkConclusion.isSelected()) {
                    conclusion = crearConclusion();
                    muestraRepository.guardarConclusion(conclusion, id);
                } else {
                    muestraRepository.guardarConclusion(null, id);
                }
                observaciones = JOptionPane.showInputDialog("Ingrese las observaciones:");
                muestraRepository.guardarObservaciones(observaciones, id);
                this.dispose();
                c.generarReporteEfluentesCloaca(id, procedencia);
            }
        }
    }

    private void etiquetaPhMousePressed(java.awt.event.MouseEvent evt) {
        activarPh = !activarPh;
        cajapH.setEnabled(activarPh);
        area012.setEnabled(activarPh);
        area013.setEnabled(activarPh);
        if (activarPh) {
            jPanel7.setBackground(new Color(240, 240, 240));
            jPanel15.setBackground(new Color(255, 255, 255));
        } else {
            jPanel7.setBackground(new Color(240, 100, 100));
            jPanel15.setBackground(new Color(240, 240, 240));
        }
    }

    private void etiquetaNitrogenoMousePressed(java.awt.event.MouseEvent evt) {
        activarNitrogeno = !activarNitrogeno;
        cajaNitrogeno.setEnabled(activarNitrogeno);
        area102.setEnabled(activarNitrogeno);
        area103.setEnabled(activarNitrogeno);
        comboNitrogeno.setEnabled(activarNitrogeno);
        etiquetaNitrogenoIn.setEnabled(activarNitrogeno);
        if (activarNitrogeno) {
            jPanel4.setBackground(new Color(240, 240, 240));
            jPanel35.setBackground(new Color(255, 255, 255));
        } else {
            jPanel4.setBackground(new Color(240, 100, 100));
            jPanel35.setBackground(new Color(240, 240, 240));
        }
    }

    private void etiquetaDQOMousePressed(java.awt.event.MouseEvent evt) {
        activarDQO = !activarDQO;
        cajaDQO.setEnabled(activarDQO);
        comboDQO.setEnabled(activarDQO);
        area32.setEnabled(activarDQO);
        area33.setEnabled(activarDQO);
        etiquetaDQOIn.setEnabled(activarDQO);
        if (activarDQO) {
            jPanel8.setBackground(new Color(240, 240, 240));
            jPanel41.setBackground(new Color(255, 255, 255));
        } else {
            jPanel8.setBackground(new Color(240, 100, 100));
            jPanel41.setBackground(new Color(240, 240, 240));
        }
    }

    private void etiquetaColiformesFecalesMousePressed(java.awt.event.MouseEvent evt) {
        activarColiformes = !activarColiformes;
        cajaColiformesFecales.setEnabled(activarColiformes);
        area122.setEnabled(activarColiformes);
        area123.setEnabled(activarColiformes);
        comboColiformesFecales.setEnabled(activarColiformes);
        etiquetaColiformesFecalesIn.setEnabled(activarColiformes);
        if (activarColiformes) {
            jPanel14.setBackground(new Color(240, 240, 240));
            jPanel31.setBackground(new Color(255, 255, 255));
        } else {
            jPanel14.setBackground(new Color(240, 100, 100));
            jPanel31.setBackground(new Color(240, 240, 240));
        }
    }

    private void etiquetaFosforoMousePressed(java.awt.event.MouseEvent evt) {
        activarFosforo = !activarFosforo;
        cajaFosforo.setEnabled(activarFosforo);
        area92.setEnabled(activarFosforo);
        area93.setEnabled(activarFosforo);
        comboFosforo.setEnabled(activarFosforo);
        etiquetaFosforoIn.setEnabled(activarFosforo);
        if (activarFosforo) {
            jPanel12.setBackground(new Color(240, 240, 240));
            jPanel38.setBackground(new Color(255, 255, 255));
        } else {
            jPanel12.setBackground(new Color(240, 100, 100));
            jPanel38.setBackground(new Color(240, 240, 240));
        }
    }

    private void etiquetaConductividadMousePressed(java.awt.event.MouseEvent evt) {
        activarConductividad = !activarConductividad;
        cajaConductividad.setEnabled(activarConductividad);
        comboConductividad.setEnabled(activarConductividad);
        area22.setEnabled(activarConductividad);
        area23.setEnabled(activarConductividad);
        etiquetaConductividadIn.setEnabled(activarConductividad);
        if (activarConductividad) {
            jPanel2.setBackground(new Color(240, 240, 240));
            jPanel42.setBackground(new Color(255, 255, 255));
        } else {
            jPanel2.setBackground(new Color(240, 100, 100));
            jPanel42.setBackground(new Color(240, 240, 240));
        }
    }

    private void etiquetaSustanciasMousePressed(java.awt.event.MouseEvent evt) {
        activarSustancias = !activarSustancias;
        cajaSustancias.setEnabled(activarSustancias);
        area112.setEnabled(activarSustancias);
        area113.setEnabled(activarSustancias);
        comboSustancias.setEnabled(activarSustancias);
        etiquetaSustanciasIn.setEnabled(activarSustancias);
        if (activarSustancias) {
            jPanel13.setBackground(new Color(240, 240, 240));
            jPanel27.setBackground(new Color(255, 255, 255));
        } else {
            jPanel13.setBackground(new Color(240, 100, 100));
            jPanel27.setBackground(new Color(240, 240, 240));
        }
    }//GEN-LAST:event_etiquetaSustanciasMousePressed

    private void etiquetaGrasasMousePressed(java.awt.event.MouseEvent evt) {
        activarGrasas = !activarGrasas;
        cajaGrasas.setEnabled(activarGrasas);
        area82.setEnabled(activarGrasas);
        area83.setEnabled(activarGrasas);
        comboGrasas.setEnabled(activarGrasas);
        etiquetaGrasasIn.setEnabled(activarGrasas);
        if (activarGrasas) {
            jPanel11.setBackground(new Color(240, 240, 240));
            jPanel37.setBackground(new Color(255, 255, 255));
        } else {
            jPanel11.setBackground(new Color(240, 100, 100));
            jPanel37.setBackground(new Color(240, 240, 240));
        }
    }

    private void etiquetaSolidos120MousePressed(java.awt.event.MouseEvent evt) {
        activarSolidos120 = !activarSolidos120;
        cajaSolidos120.setEnabled(activarSolidos120);
        area62.setEnabled(activarSolidos120);
        area63.setEnabled(activarSolidos120);
        comboSolidos120.setEnabled(activarSolidos120);
        etiquetaSolidos120In.setEnabled(activarSolidos120);
        if (activarSolidos120) {
            jPanel9.setBackground(new Color(240, 240, 240));
            jPanel34.setBackground(new Color(255, 255, 255));
        } else {
            jPanel9.setBackground(new Color(240, 100, 100));
            jPanel34.setBackground(new Color(240, 240, 240));
        }
    }

    private void etiquetaSolidos10MousePressed(java.awt.event.MouseEvent evt) {
        activarSolidos10 = !activarSolidos10;
        cajaSolidos10.setEnabled(activarSolidos10);
        area52.setEnabled(activarSolidos10);
        area53.setEnabled(activarSolidos10);
        comboSolidos10.setEnabled(activarSolidos10);
        etiquetaSolidos10In.setEnabled(activarSolidos10);
        if (activarSolidos10) {
            jPanel5.setBackground(new Color(240, 240, 240));
            jPanel39.setBackground(new Color(255, 255, 255));
        } else {
            jPanel5.setBackground(new Color(240, 100, 100));
            jPanel39.setBackground(new Color(240, 240, 240));
        }
    }

    private void etiquetaDetergentesMousePressed(java.awt.event.MouseEvent evt) {
        activarDetergentes = !activarDetergentes;
        cajaDetergentes.setEnabled(activarDetergentes);
        area72.setEnabled(activarDetergentes);
        area73.setEnabled(activarDetergentes);
        comboDetergentes.setEnabled(activarDetergentes);
        etiquetaDetergentesIn.setEnabled(activarDetergentes);
        if (activarDetergentes) {
            jPanel10.setBackground(new Color(240, 240, 240));
            jPanel36.setBackground(new Color(255, 255, 255));
        } else {
            jPanel10.setBackground(new Color(240, 100, 100));
            jPanel36.setBackground(new Color(240, 240, 240));
        }
    }

    private void etiquetaDBOMousePressed(java.awt.event.MouseEvent evt) {
        activarDBO = !activarDBO;
        cajaDBO.setEnabled(activarDBO);
        comboDBO.setEnabled(activarDBO);
        area42.setEnabled(activarDBO);
        area43.setEnabled(activarDBO);
        etiquetaDBOIn.setEnabled(activarDBO);
        if (activarDBO) {
            jPanel3.setBackground(new Color(240, 240, 240));
            jPanel40.setBackground(new Color(255, 255, 255));
        } else {
            jPanel3.setBackground(new Color(240, 100, 100));
            jPanel40.setBackground(new Color(240, 240, 240));
        }
    }

    private void etiquetaHidrocarburosMousePressed(java.awt.event.MouseEvent evt) {
        activarHidrocarburos = !activarHidrocarburos;
        cajaHidrocarburos.setEnabled(activarHidrocarburos);
        comboHidrocarburos.setEnabled(activarHidrocarburos);
        area132.setEnabled(activarHidrocarburos);
        area133.setEnabled(activarHidrocarburos);
        etiquetaHidrocarburosIn.setEnabled(activarHidrocarburos);
        if (activarHidrocarburos) {
            jPanel23.setBackground(new Color(240, 240, 240));
            jPanel32.setBackground(new Color(255, 255, 255));
        } else {
            jPanel23.setBackground(new Color(240, 100, 100));
            jPanel32.setBackground(new Color(240, 240, 240));
        }
    }

    private void etiquetaNitratosMousePressed(java.awt.event.MouseEvent evt) {
        activarNitratos = !activarNitratos;
        cajaNitratos.setEnabled(activarNitratos);
        comboNitratos.setEnabled(activarNitratos);
        area134.setEnabled(activarNitratos);
        area136.setEnabled(activarNitratos);
        etiquetaNitratosIn.setEnabled(activarNitratos);
        if (activarNitratos) {
            jPanel24.setBackground(new Color(240, 240, 240));
            jPanel33.setBackground(new Color(255, 255, 255));
        } else {
            jPanel24.setBackground(new Color(240, 100, 100));
            jPanel33.setBackground(new Color(240, 240, 240));
        }
    }

    private void etiquetaCloroMousePressed(java.awt.event.MouseEvent evt) {
        activarCloro = !activarCloro;
        cajaCloro.setEnabled(activarCloro);
        comboCloro.setEnabled(activarCloro);
        area135.setEnabled(activarCloro);
        area137.setEnabled(activarCloro);
        etiquetaCloroIn.setEnabled(activarCloro);
        if (activarCloro) {
            jPanel25.setBackground(new Color(240, 240, 240));
            jPanel43.setBackground(new Color(255, 255, 255));
        } else {
            jPanel25.setBackground(new Color(240, 100, 100));
            jPanel43.setBackground(new Color(240, 240, 240));
        }
    }

    private void etiquetaColiformesTotalesMousePressed(java.awt.event.MouseEvent evt) {
        activarColiformesTotales = !activarColiformesTotales;
        cajaColiformesTotales.setEnabled(activarColiformesTotales);
        comboColiformesTotales.setEnabled(activarColiformesTotales);
        area125.setEnabled(activarColiformesTotales);
        area124.setEnabled(activarColiformesTotales);
        etiquetaColiformesTotalesIn.setEnabled(activarColiformesTotales);
        if (activarColiformesTotales) {
            jPanel18.setBackground(new Color(240, 240, 240));
            jPanel44.setBackground(new Color(255, 255, 255));
        } else {
            jPanel18.setBackground(new Color(240, 100, 100));
            jPanel44.setBackground(new Color(240, 240, 240));
        }
    }

    private void etiquetaEscherichiaMousePressed(java.awt.event.MouseEvent evt) {
        activarEscherichia = !activarEscherichia;
        cajaEscherichia.setEnabled(activarEscherichia);
        comboEscherichia.setEnabled(activarEscherichia);
        area126.setEnabled(activarEscherichia);
        area127.setEnabled(activarEscherichia);
        etiquetaEscherichiaIn.setEnabled(activarEscherichia);
        if (activarEscherichia) {
            jPanel20.setBackground(new Color(240, 240, 240));
            jPanel45.setBackground(new Color(255, 255, 255));
        } else {
            jPanel20.setBackground(new Color(240, 100, 100));
            jPanel45.setBackground(new Color(240, 240, 240));
        }
    }

    private void etiquetaSulfurosMousePressed(java.awt.event.MouseEvent evt) {
        activarSulfuros = !activarSulfuros;
        cajaSulfuros.setEnabled(activarSulfuros);
        comboSulfuros.setEnabled(activarSulfuros);
        area138.setEnabled(activarSulfuros);
        area139.setEnabled(activarSulfuros);
        etiquetaSulfurosIn.setEnabled(activarSulfuros);
        if (activarSulfuros) {
            jPanel26.setBackground(new Color(240, 240, 240));
            jPanel46.setBackground(new Color(255, 255, 255));
        } else {
            jPanel26.setBackground(new Color(240, 100, 100));
            jPanel46.setBackground(new Color(240, 240, 240));
        }
    }

    private javax.swing.JTextField area012;
    private javax.swing.JTextField area013;
    private javax.swing.JTextField area102;
    private javax.swing.JTextField area103;
    private javax.swing.JTextField area112;
    private javax.swing.JTextField area113;
    private javax.swing.JTextField area122;
    private javax.swing.JTextField area123;
    private javax.swing.JTextField area124;
    private javax.swing.JTextField area125;
    private javax.swing.JTextField area126;
    private javax.swing.JTextField area127;
    private javax.swing.JTextField area132;
    private javax.swing.JTextField area133;
    private javax.swing.JTextField area134;
    private javax.swing.JTextField area135;
    private javax.swing.JTextField area136;
    private javax.swing.JTextField area137;
    private javax.swing.JTextField area138;
    private javax.swing.JTextField area139;
    private javax.swing.JTextField area22;
    private javax.swing.JTextField area23;
    private javax.swing.JTextField area32;
    private javax.swing.JTextField area33;
    private javax.swing.JTextField area42;
    private javax.swing.JTextField area43;
    private javax.swing.JTextField area52;
    private javax.swing.JTextField area53;
    private javax.swing.JTextField area62;
    private javax.swing.JTextField area63;
    private javax.swing.JTextField area72;
    private javax.swing.JTextField area73;
    private javax.swing.JTextField area82;
    private javax.swing.JTextField area83;
    private javax.swing.JTextField area92;
    private javax.swing.JTextField area93;
    private javax.swing.JButton botonGenerar;
    private javax.swing.JTextField cajaCloro;
    private javax.swing.JTextField cajaColiformesFecales;
    private javax.swing.JTextField cajaColiformesTotales;
    private javax.swing.JTextField cajaConductividad;
    private javax.swing.JTextField cajaDBO;
    private javax.swing.JTextField cajaDQO;
    private javax.swing.JTextField cajaDetergentes;
    private javax.swing.JTextField cajaEscherichia;
    private com.toedter.calendar.JDateChooser cajaFechaAnalisis;
    private javax.swing.JTextField cajaFosforo;
    private javax.swing.JTextField cajaGrasas;
    private javax.swing.JTextField cajaHidrocarburos;
    private javax.swing.JTextField cajaNitratos;
    private javax.swing.JTextField cajaNitrogeno;
    private javax.swing.JTextField cajaSolidos10;
    private javax.swing.JTextField cajaSolidos120;
    private javax.swing.JTextField cajaSulfuros;
    private javax.swing.JTextField cajaSustancias;
    private javax.swing.JTextField cajapH;
    private javax.swing.JCheckBox checkConclusion;
    private javax.swing.JComboBox<String> comboCloro;
    private javax.swing.JComboBox<String> comboColiformesFecales;
    private javax.swing.JComboBox<String> comboColiformesTotales;
    private javax.swing.JComboBox<String> comboConductividad;
    private javax.swing.JComboBox<String> comboDBO;
    private javax.swing.JComboBox<String> comboDQO;
    private javax.swing.JComboBox<String> comboDetergentes;
    private javax.swing.JComboBox<String> comboEscherichia;
    private javax.swing.JComboBox<String> comboFosforo;
    private javax.swing.JComboBox<String> comboGrasas;
    private javax.swing.JComboBox<String> comboHidrocarburos;
    private javax.swing.JComboBox<String> comboNitratos;
    private javax.swing.JComboBox<String> comboNitrogeno;
    private javax.swing.JComboBox<String> comboSolidos10;
    private javax.swing.JComboBox<String> comboSolidos120;
    private javax.swing.JComboBox<String> comboSulfuros;
    private javax.swing.JComboBox<String> comboSustancias;
    private javax.swing.JLabel etiquetaCloro;
    private javax.swing.JLabel etiquetaCloroIn;
    private javax.swing.JLabel etiquetaColiformesFecales;
    private javax.swing.JLabel etiquetaColiformesFecalesIn;
    private javax.swing.JLabel etiquetaColiformesTotales;
    private javax.swing.JLabel etiquetaColiformesTotalesIn;
    private javax.swing.JLabel etiquetaConductividad;
    private javax.swing.JLabel etiquetaConductividadIn;
    private javax.swing.JLabel etiquetaDBO;
    private javax.swing.JLabel etiquetaDBOIn;
    private javax.swing.JLabel etiquetaDQO;
    private javax.swing.JLabel etiquetaDQOIn;
    private javax.swing.JLabel etiquetaDetergentes;
    private javax.swing.JLabel etiquetaDetergentesIn;
    private javax.swing.JLabel etiquetaEscherichia;
    private javax.swing.JLabel etiquetaEscherichiaIn;
    private javax.swing.JLabel etiquetaFosforo;
    private javax.swing.JLabel etiquetaFosforoIn;
    private javax.swing.JLabel etiquetaGrasas;
    private javax.swing.JLabel etiquetaGrasasIn;
    private javax.swing.JLabel etiquetaHidrocarburos;
    private javax.swing.JLabel etiquetaHidrocarburosIn;
    private javax.swing.JLabel etiquetaNitratos;
    private javax.swing.JLabel etiquetaNitratosIn;
    private javax.swing.JLabel etiquetaNitrogeno;
    private javax.swing.JLabel etiquetaNitrogenoIn;
    private javax.swing.JLabel etiquetaPh;
    private javax.swing.JLabel etiquetaSolidos10;
    private javax.swing.JLabel etiquetaSolidos10In;
    private javax.swing.JLabel etiquetaSolidos120;
    private javax.swing.JLabel etiquetaSolidos120In;
    private javax.swing.JLabel etiquetaSulfuros;
    private javax.swing.JLabel etiquetaSulfurosIn;
    private javax.swing.JLabel etiquetaSustancias;
    private javax.swing.JLabel etiquetaSustanciasIn;
    private javax.swing.JLabel etiquetaTitulo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
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
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel31;
    private javax.swing.JPanel jPanel32;
    private javax.swing.JPanel jPanel33;
    private javax.swing.JPanel jPanel34;
    private javax.swing.JPanel jPanel35;
    private javax.swing.JPanel jPanel36;
    private javax.swing.JPanel jPanel37;
    private javax.swing.JPanel jPanel38;
    private javax.swing.JPanel jPanel39;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel40;
    private javax.swing.JPanel jPanel41;
    private javax.swing.JPanel jPanel42;
    private javax.swing.JPanel jPanel43;
    private javax.swing.JPanel jPanel44;
    private javax.swing.JPanel jPanel45;
    private javax.swing.JPanel jPanel46;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;


    public String crearConclusion() {
        boolean ph = true;
        if (cajapH.isEnabled()) {
            ph = Double.parseDouble(cajapH.getText()) > 6 && Double.parseDouble(cajapH.getText()) < 9;
        }
        boolean dqo;
        if (cajaDQO.isEnabled()) {
            if (comboDQO.getSelectedItem().toString().equals("Mayor de") && Double.parseDouble(cajaDQO.getText()) >= 500) {
                dqo = false;
            } else if (comboDQO.getSelectedItem().toString().equals("Menor de") && Double.parseDouble(cajaDQO.getText()) <= 500) {
                dqo = true;
            } else {
                dqo = Double.parseDouble(cajaDQO.getText()) < 500;
            }
        } else {
            dqo = true;
        }
        boolean dbo;
        if (cajaDBO.isEnabled()) {
            if (comboDBO.getSelectedItem().toString().equals("Mayor de") && Double.parseDouble(cajaDBO.getText()) >= 200) {
                dbo = false;
            } else if (comboDBO.getSelectedItem().toString().equals("Menor de") && Double.parseDouble(cajaDBO.getText()) <= 200) {
                dbo = true;
            } else {
                dbo = Double.parseDouble(cajaDBO.getText()) < 200;
            }
        } else {
            dbo = true;
        }
        boolean solidos10;
        if (cajaSolidos10.isEnabled()) {
            if (comboSolidos10.getSelectedItem().toString().equals("Mayor de") && Double.parseDouble(cajaSolidos10.getText()) == 0.5) {
                solidos10 = false;
            } else if (comboSolidos10.getSelectedItem().toString().equals("Menor de") && Double.parseDouble(cajaSolidos10.getText()) == 0.5) {
                solidos10 = true;
                System.out.println("entre menor");
            } else {
                solidos10 = Double.parseDouble(cajaSolidos10.getText()) < 0.5;
            }
        } else {
            solidos10 = true;
        }
        boolean solidos120;
        if (cajaSolidos120.isEnabled()) {
            if (comboSolidos120.getSelectedItem().toString().equals("Mayor de") && Double.parseDouble(cajaSolidos120.getText()) >= 1) {
                solidos120 = false;
            } else if (comboSolidos120.getSelectedItem().toString().equals("Menor de") && Double.parseDouble(cajaSolidos120.getText()) <= 1) {
                solidos120 = true;
            } else {
                solidos120 = Double.parseDouble(cajaSolidos120.getText()) < 1;
            }
        } else {
            solidos120 = true;
        }
        boolean detergentes;
        if (cajaDetergentes.isEnabled()) {
            if (comboDetergentes.getSelectedItem().toString().equals("Mayor de") && Double.parseDouble(cajaDetergentes.getText()) >= 5) {
                detergentes = false;
            } else if (comboDetergentes.getSelectedItem().toString().equals("Menor de") && Double.parseDouble(cajaDetergentes.getText()) <= 5) {
                detergentes = true;
            } else {
                detergentes = Double.parseDouble(cajaDetergentes.getText()) < 4;
            }
        } else {
            detergentes = true;
        }
        boolean grasas;
        if (cajaGrasas.isEnabled()) {
            if (comboGrasas.getSelectedItem().toString().equals("Mayor de") && Double.parseDouble(cajaGrasas.getText()) >= 50) {
                grasas = false;
            } else if (comboGrasas.getSelectedItem().toString().equals("Menor de") && Double.parseDouble(cajaGrasas.getText()) <= 50) {
                grasas = true;
            } else {
                grasas = Double.parseDouble(cajaGrasas.getText()) < 50;
            }
        } else {
            grasas = true;
        }
        boolean fosforo;
        if (cajaFosforo.isEnabled()) {
            if (comboFosforo.getSelectedItem().toString().equals("Mayor de") && Double.parseDouble(cajaFosforo.getText()) >= 5) {
                fosforo = false;
            } else if (comboFosforo.getSelectedItem().toString().equals("Menor de") && Double.parseDouble(cajaFosforo.getText()) <= 5) {
                fosforo = true;
            } else {
                fosforo = Double.parseDouble(cajaFosforo.getText()) < 5;
            }
        } else {
            fosforo = true;
        }
        boolean nitrogeno;
        if (cajaNitrogeno.isEnabled()) {
            if (comboNitrogeno.getSelectedItem().toString().equals("Mayor de") && Double.parseDouble(cajaNitrogeno.getText()) >= 100) {
                nitrogeno = false;
            } else if (comboNitrogeno.getSelectedItem().toString().equals("Menor de") && Double.parseDouble(cajaNitrogeno.getText()) <= 100) {
                nitrogeno = true;
            } else {
                nitrogeno = Double.parseDouble(cajaNitrogeno.getText()) < 100;
            }
        } else {
            nitrogeno = true;
        }
        boolean coliformes;
        if (cajaColiformesFecales.isEnabled()) {
            if (comboColiformesFecales.getSelectedItem().toString().equals("Mayor de") && Double.parseDouble(cajaSolidos120.getText()) >= 100000) {
                coliformes = false;
            } else if (comboColiformesFecales.getSelectedItem().toString().equals("Menor de") && Double.parseDouble(cajaSolidos120.getText()) <= 100000) {
                coliformes = true;
            } else {
                coliformes = Double.parseDouble(cajaColiformesFecales.getText()) < 100000;
            }
        } else {
            coliformes = true;
        }

        boolean hidrocarburos;
        if (cajaHidrocarburos.isEnabled()) {
            if (comboHidrocarburos.getSelectedItem().toString().equals("Mayor de") && Double.parseDouble(cajaHidrocarburos.getText()) >= 2) {
                hidrocarburos = false;
            } else if (comboHidrocarburos.getSelectedItem().toString().equals("Menor de") && Double.parseDouble(cajaHidrocarburos.getText()) <= 2) {
                hidrocarburos = true;
            } else {
                hidrocarburos = Double.parseDouble(cajaHidrocarburos.getText()) < 2;
            }
        } else {
            hidrocarburos = true;
        }

        boolean sulfuros;
        if (cajaSulfuros.isEnabled()) {
            if (comboSulfuros.getSelectedItem().toString().equals("Mayor de") && Double.parseDouble(cajaSulfuros.getText()) >= 1) {
                sulfuros = false;
            } else if (comboSulfuros.getSelectedItem().toString().equals("Menor de") && Double.parseDouble(cajaSulfuros.getText()) <= 1) {
                sulfuros = true;
            } else {
                sulfuros = Double.parseDouble(cajaSulfuros.getText()) < 1;
            }
        } else {
            sulfuros = true;
        }
        String conclusion = "";
        String recomendacion = "";
        if (ph && dqo && dbo && solidos10 && solidos120 && detergentes && grasas
                && fosforo && nitrogeno && coliformes && hidrocarburos) {
            conclusion = "La muestra analizada cumple con la resolución 886 IP "
                    + "ANEXOS-LTercero Decreto 1093-2015";
        } else {
            conclusion = "Dado el valor de";
            if (!ph) {
                conclusion += " pH,";
            }
            if (!dqo) {
                conclusion += " DQO,";

            }
            if (!dbo) {
                conclusion += " DBO,";

            }
            if (!solidos10) {
                conclusion += " sólidos disueltos sedimentables en 10 minutos,";

            }
            if (!solidos120) {
                conclusion += " sólidos disueltos sedimentables en 120 minutos,";

            }
            if (!detergentes) {
                conclusion += " detergentes,";

            }
            if (!grasas) {
                conclusion += " grasas y aceites,";

            }

            if (!fosforo) {
                conclusion += " fósforo total,";

            }

            if (!nitrogeno) {
                conclusion += " nitrógeno total,";

            }

            if (!hidrocarburos) {
                conclusion += " hidrocarburos totales de petróleo,";
            }

            if (!coliformes) {
                conclusion += " Coliformes totales,";
            }
            if (!sulfuros) {
                conclusion += " sulfuros,";
            }
            conclusion = conclusion.substring(0, conclusion.length() - 1);
            conclusion += " la prueba no cumple con la resolución 886 IP ANEXOS-LTercero Decreto 1093-2015.";
            recomendacion = JOptionPane.showInputDialog("Ingrese la recomendación:");
            if (!recomendacion.equals("") || !recomendacion.isBlank()) {
                conclusion += " Se recomienda " + recomendacion.toLowerCase();
            }
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
