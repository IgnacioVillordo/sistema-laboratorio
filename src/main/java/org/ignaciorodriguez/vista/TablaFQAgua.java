package org.ignaciorodriguez.vista;

import java.awt.Color;
import java.awt.Component;
import java.awt.Point;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.plaf.basic.BasicComboBoxUI;
import org.ignaciorodriguez.modelo.Consultas;
import org.ignaciorodriguez.modelo.Resultados;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.KeyStroke;
import javax.swing.JComponent;

public class TablaFQAgua extends javax.swing.JDialog {

    int id;
    Consultas c = Consultas.getInstancia();
    boolean editar, activarPh = true, activarCloro = true, activarOlor = true,
            activarColor = true, activarTurbidez = true, activarAlcalinidad = true,
            activarDureza = true, activarConductividad = true, activarSolidos = true,
            activarHierro = true, activarNitratos = true, activarNitritos = true, activarSulfatos = true;
    String procedencia, pdf, auxObservaciones, auxOlor;

    public TablaFQAgua(java.awt.Frame parent, boolean modal, int id, String procedencia, boolean editar, String pdf) {
        super(parent, modal);
        this.id = id;
        this.procedencia = procedencia;
        this.editar = editar;
        this.pdf = pdf;
        initComponents();
        setTitle("ID " + id + ". " + c.obtenerProcedencia(id) + ". Físico químico de agua");
        this.setLocationRelativeTo(null);
        if (editar == true) {
            Map<String, String> resultados = c.recuperarResultadosFQAgua(id);
            auxObservaciones = c.recuperarObservaciones(id);
            if (resultados == null) {
                this.editar = false;
            } else {
                boolean auxiliarVencimiento = c.recuperarEsconderFechaVencimiento(this.id);
                checkPoner.setSelected(auxiliarVencimiento);
                etiquetaTitulo.setText("Editar resultados del análisis");
                String aux;
                DecimalFormat df = new DecimalFormat("#.##");
                if (resultados.get("ph").contains("-2")) {
                    etiquetaPhMousePressed(click(etiquetaPh));
                } else if (resultados.get("ph").contains("-1")) {
                } else {
                    aux = df.format(Double.parseDouble(resultados.get("ph").toString().replaceAll("[^0-9?!\\.]", ""))).replace(",", ".");
                    cajapH.setText(aux);
                }
                if (resultados.get("cloroTotal").contains("-2")) {
                    etiquetaCloroMousePressed(click(etiquetaCloro));
                } else if (resultados.get("cloroTotal").contains("-1")) {
                } else {
                    aux = df.format(Double.parseDouble(resultados.get("cloroTotal").toString().replaceAll("[^0-9?!\\.]", ""))).replace(",", ".");
                    cajaCloro.setText(aux);
                }
                if (resultados.get("olor").contains("-2")) {
                    etiquetaOlorMousePressed(click(etiquetaOlor));
                } else if (resultados.get("olor").contains("-1")) {
                } else {
                    if (resultados.get("olor").equals("Sin olores extraños")) {
                        comboOlor.setSelectedIndex(0);
                    } else {
                        comboOlor.setSelectedIndex(1);
                        auxOlor = resultados.get("olor");
                    }
                }
                if (resultados.get("color").contains("-2")) {
                    etiquetaColorMousePressed(click(etiquetaColor));
                } else if (resultados.get("color").contains("-1")) {
                } else {
                    aux = df.format(Double.parseDouble(resultados.get("color").replaceAll("[^0-9?!\\.]", ""))).replace(",", ".");
                    if (resultados.get("color").startsWith("Menor de")) {
                        comboColor.setSelectedIndex(0);
                    } else {
                        comboColor.setSelectedIndex(1);
                    }
                    cajaColor.setText(aux);
                }

                if (resultados.get("turbidez").contains("-2")) {
                    etiquetaTurbidezMousePressed(click(etiquetaTurbidez));
                } else if (resultados.get("turbidez").contains("-1")) {
                } else {
                    aux = df.format(Double.parseDouble(resultados.get("turbidez").replaceAll("[^0-9?!\\.]", ""))).replace(",", ".");
                    if (resultados.get("turbidez").startsWith("Menor de")) {
                        comboTurbidez.setSelectedIndex(0);
                    } else {
                        comboTurbidez.setSelectedIndex(1);
                    }
                    cajaTurbidez.setText(aux);
                }
                if (resultados.get("alcalinidad").contains("-2")) {
                    etiquetaAlcalinidadMousePressed(click(etiquetaAlcalinidad));
                } else if (resultados.get("alcalinidad").contains("-1")) {
                } else {
                    aux = df.format(Double.parseDouble(resultados.get("alcalinidad").replaceAll("[^0-9?!\\.]", ""))).replace(",", ".");
                    cajaAlcalinidad.setText(aux);
                }

                if (resultados.get("durezatotal").contains("-2")) {
                    etiquetaDurezaMousePressed(click(etiquetaDureza));
                } else if (resultados.get("durezatotal").contains("-1")) {
                } else {
                    aux = df.format(Double.parseDouble(resultados.get("durezatotal").replaceAll("[^0-9?!\\.]", ""))).replace(",", ".");
                    cajaDurezatotal.setText(aux);
                }

                if (resultados.get("conductividad").contains("-2")) {
                    etiquetaConductividadMousePressed(click(etiquetaConductividad));
                } else if (resultados.get("conductividad").contains("-1")) {
                } else {
                    aux = df.format(Double.parseDouble(resultados.get("conductividad").toString().replaceAll("[^0-9?!\\.]", ""))).replace(",", ".");
                    cajaConductividad.setText(aux);
                }

                if (resultados.get("solidosDisueltos").contains("-2")) {
                    etiquetaSolidosMousePressed(click(etiquetaSolidos));
                } else if (resultados.get("solidosDisueltos").contains("-1")) {
                } else {
                    aux = df.format(Double.parseDouble(resultados.get("solidosDisueltos").toString().replaceAll("[^0-9?!\\.]", ""))).replace(",", ".");
                    cajaSolidos.setText(aux);
                }

                if (resultados.get("hierro").contains("-2")) {
                    etiquetaHierroMousePressed(click(etiquetaHierro));
                } else if (resultados.get("hierro").contains("-1")) {
                } else {
                    aux = resultados.get("hierro").replaceAll("[^0-9?!\\.]", "");
                    if (resultados.get("hierro").startsWith("Menor de")) {
                        comboHierro.setSelectedIndex(0);
                    } else {
                        comboHierro.setSelectedIndex(1);
                    }
                    cajaHierro.setText(aux);
                }

                if (resultados.get("nitratos").contains("-2")) {
                    etiquetaNitratosMousePressed(click(etiquetaNitratos));
                } else if (resultados.get("nitratos").contains("-1")) {
                } else {
                    aux = resultados.get("nitratos").replaceAll("[^0-9?!\\.]", "");
                    if (resultados.get("nitratos").startsWith("Menor de")) {
                        comboNitratos.setSelectedIndex(0);
                    } else {
                        comboNitratos.setSelectedIndex(1);
                    }
                    cajaNitratos.setText(aux);
                }

                if (resultados.get("nitritos").contains("-2")) {
                    etiquetaNitritosMousePressed(click(etiquetaNitritos));
                } else if (resultados.get("nitritos").contains("-1")) {
                } else {
                    aux = resultados.get("nitritos").replaceAll("[^0-9?!\\.]", "");
                    if (resultados.get("nitritos").startsWith("Menor de")) {
                        comboNitritos.setSelectedIndex(0);
                    } else {
                        comboNitritos.setSelectedIndex(1);
                    }
                    cajaNitritos.setText(aux);
                }

                if (resultados.get("sulfatos").contains("-2")) {
                    etiquetaSulfatosMousePressed(click(etiquetaSulfatos));
                } else if (resultados.get("sulfatos").contains("-1")) {
                } else {
                    aux = resultados.get("sulfatos").replaceAll("[^0-9?!\\.]", "");
                    if (resultados.get("sulfatos").startsWith("Menor de")) {
                        comboSulfatos.setSelectedIndex(0);
                    } else {
                        comboSulfatos.setSelectedIndex(1);
                    }
                    cajaSulfatos.setText(aux);
                }
            }
            cajaFechaAnalisis.setDate(c.recuperarFechaAnalisis(id));
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
        jPanel2 = new javax.swing.JPanel();
        etiquetaCloro = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        etiquetaSolidos = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        etiquetaNitritos = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        etiquetaSulfatos = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        etiquetaPh = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        etiquetaHierro = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        etiquetaOlor = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        etiquetaNitratos = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        etiquetaConductividad = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        etiquetaAlcalinidad = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        etiquetaTurbidez = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        etiquetaDureza = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        etiquetaColor = new javax.swing.JLabel();
        jPanel19 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jPanel17 = new javax.swing.JPanel();
        area012 = new javax.swing.JTextField();
        area52 = new javax.swing.JTextField();
        area132 = new javax.swing.JTextField();
        area82 = new javax.swing.JTextField();
        area43 = new javax.swing.JTextField();
        area62 = new javax.swing.JTextField();
        area63 = new javax.swing.JTextField();
        area72 = new javax.swing.JTextField();
        area123 = new javax.swing.JTextField();
        area122 = new javax.swing.JTextField();
        area83 = new javax.swing.JTextField();
        area32 = new javax.swing.JTextField();
        area113 = new javax.swing.JTextField();
        area102 = new javax.swing.JTextField();
        area133 = new javax.swing.JTextField();
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
        jPanel15 = new javax.swing.JPanel();
        cajapH = new javax.swing.JTextField();
        jPanel18 = new javax.swing.JPanel();
        cajaCloro = new javax.swing.JTextField();
        etiquetaCloroIn = new javax.swing.JLabel();
        jPanel23 = new javax.swing.JPanel();
        cajaAlcalinidad = new javax.swing.JTextField();
        etiquetaAlcalinidadIn = new javax.swing.JLabel();
        jPanel24 = new javax.swing.JPanel();
        cajaDurezatotal = new javax.swing.JTextField();
        etiquetaDurezaIn = new javax.swing.JLabel();
        jPanel25 = new javax.swing.JPanel();
        cajaConductividad = new javax.swing.JTextField();
        etiquetaConductividadIn = new javax.swing.JLabel();
        jPanel26 = new javax.swing.JPanel();
        cajaSolidos = new javax.swing.JTextField();
        etiquetaSolidosIn = new javax.swing.JLabel();
        jPanel27 = new javax.swing.JPanel();
        cajaNitratos = new javax.swing.JTextField();
        etiquetaNitratosIn = new javax.swing.JLabel();
        comboNitratos = new javax.swing.JComboBox<>();
        jPanel31 = new javax.swing.JPanel();
        cajaNitritos = new javax.swing.JTextField();
        etiquetaNitritosIn = new javax.swing.JLabel();
        comboNitritos = new javax.swing.JComboBox<>();
        jPanel32 = new javax.swing.JPanel();
        cajaSulfatos = new javax.swing.JTextField();
        etiquetaSulfatosIn = new javax.swing.JLabel();
        comboSulfatos = new javax.swing.JComboBox<>();
        jPanel33 = new javax.swing.JPanel();
        cajaColor = new javax.swing.JTextField();
        etiquetaColorIn = new javax.swing.JLabel();
        comboColor = new javax.swing.JComboBox<>();
        jPanel34 = new javax.swing.JPanel();
        cajaTurbidez = new javax.swing.JTextField();
        etiquetaTurbidezIn = new javax.swing.JLabel();
        comboTurbidez = new javax.swing.JComboBox<>();
        jPanel35 = new javax.swing.JPanel();
        cajaHierro = new javax.swing.JTextField();
        etiquetaHierroIn = new javax.swing.JLabel();
        comboHierro = new javax.swing.JComboBox<>();
        jPanel20 = new javax.swing.JPanel();
        comboOlor = new javax.swing.JComboBox<>();
        jPanel22 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        cajaFechaAnalisis = new com.toedter.calendar.JDateChooser();
        checkPoner = new javax.swing.JCheckBox();

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

        jPanel2.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 0, 1, new java.awt.Color(0, 0, 0)));
        jPanel2.setPreferredSize(new java.awt.Dimension(150, 10));
        jPanel2.setLayout(new java.awt.GridBagLayout());

        etiquetaCloro.setBackground(new java.awt.Color(0, 0, 0));
        etiquetaCloro.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        etiquetaCloro.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        etiquetaCloro.setText(" CLORO LIBRE");
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
        gridBagConstraints.ipadx = 30;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        jPanel2.add(etiquetaCloro, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 30;
        gridBagConstraints.ipady = 13;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        jPanel16.add(jPanel2, gridBagConstraints);

        jPanel12.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 0, 1, new java.awt.Color(0, 0, 0)));
        jPanel12.setPreferredSize(new java.awt.Dimension(150, 10));
        jPanel12.setLayout(new java.awt.GridBagLayout());

        etiquetaSolidos.setBackground(new java.awt.Color(0, 0, 0));
        etiquetaSolidos.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        etiquetaSolidos.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        etiquetaSolidos.setText(" SÓLIDOS DISUELTOS TOTALES");
        etiquetaSolidos.setToolTipText("");
        etiquetaSolidos.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        etiquetaSolidos.setMinimumSize(new java.awt.Dimension(150, 30));
        etiquetaSolidos.setPreferredSize(new java.awt.Dimension(175, 10));
        etiquetaSolidos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                etiquetaSolidosMousePressed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 30;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        jPanel12.add(etiquetaSolidos, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.ipadx = 30;
        gridBagConstraints.ipady = 13;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        jPanel16.add(jPanel12, gridBagConstraints);

        jPanel14.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 0, 1, new java.awt.Color(0, 0, 0)));
        jPanel14.setPreferredSize(new java.awt.Dimension(150, 10));
        jPanel14.setLayout(new java.awt.GridBagLayout());

        etiquetaNitritos.setBackground(new java.awt.Color(0, 0, 0));
        etiquetaNitritos.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        etiquetaNitritos.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        etiquetaNitritos.setText(" NITRITOS");
        etiquetaNitritos.setToolTipText("");
        etiquetaNitritos.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        etiquetaNitritos.setMinimumSize(new java.awt.Dimension(150, 30));
        etiquetaNitritos.setPreferredSize(new java.awt.Dimension(175, 10));
        etiquetaNitritos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                etiquetaNitritosMousePressed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 30;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        jPanel14.add(etiquetaNitritos, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.ipadx = 30;
        gridBagConstraints.ipady = 13;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        jPanel16.add(jPanel14, gridBagConstraints);

        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel6.setPreferredSize(new java.awt.Dimension(150, 10));
        jPanel6.setLayout(new java.awt.GridBagLayout());

        etiquetaSulfatos.setBackground(new java.awt.Color(0, 0, 0));
        etiquetaSulfatos.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        etiquetaSulfatos.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        etiquetaSulfatos.setText(" SULFATOS");
        etiquetaSulfatos.setToolTipText("");
        etiquetaSulfatos.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        etiquetaSulfatos.setMinimumSize(new java.awt.Dimension(150, 30));
        etiquetaSulfatos.setPreferredSize(new java.awt.Dimension(175, 10));
        etiquetaSulfatos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                etiquetaSulfatosMousePressed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 30;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        jPanel6.add(etiquetaSulfatos, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.ipadx = 30;
        gridBagConstraints.ipady = 13;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        jPanel16.add(jPanel6, gridBagConstraints);

        jPanel7.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 0, 1, new java.awt.Color(0, 0, 0)));
        jPanel7.setPreferredSize(new java.awt.Dimension(150, 10));
        jPanel7.setLayout(new java.awt.GridBagLayout());

        etiquetaPh.setBackground(new java.awt.Color(0, 0, 0));
        etiquetaPh.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
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
        gridBagConstraints.ipadx = 30;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        jPanel7.add(etiquetaPh, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 30;
        gridBagConstraints.ipady = 13;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        jPanel16.add(jPanel7, gridBagConstraints);

        jPanel4.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 0, 1, new java.awt.Color(0, 0, 0)));
        jPanel4.setPreferredSize(new java.awt.Dimension(150, 10));
        jPanel4.setLayout(new java.awt.GridBagLayout());

        etiquetaHierro.setBackground(new java.awt.Color(0, 0, 0));
        etiquetaHierro.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        etiquetaHierro.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        etiquetaHierro.setText(" HIERRO");
        etiquetaHierro.setToolTipText("");
        etiquetaHierro.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        etiquetaHierro.setMinimumSize(new java.awt.Dimension(150, 30));
        etiquetaHierro.setPreferredSize(new java.awt.Dimension(175, 10));
        etiquetaHierro.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                etiquetaHierroMousePressed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 30;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        jPanel4.add(etiquetaHierro, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.ipadx = 30;
        gridBagConstraints.ipady = 13;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        jPanel16.add(jPanel4, gridBagConstraints);

        jPanel8.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 0, 1, new java.awt.Color(0, 0, 0)));
        jPanel8.setPreferredSize(new java.awt.Dimension(150, 10));
        jPanel8.setLayout(new java.awt.GridBagLayout());

        etiquetaOlor.setBackground(new java.awt.Color(0, 0, 0));
        etiquetaOlor.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        etiquetaOlor.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        etiquetaOlor.setText(" OLOR");
        etiquetaOlor.setToolTipText("");
        etiquetaOlor.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        etiquetaOlor.setMinimumSize(new java.awt.Dimension(150, 30));
        etiquetaOlor.setPreferredSize(new java.awt.Dimension(175, 10));
        etiquetaOlor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                etiquetaOlorMousePressed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 30;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        jPanel8.add(etiquetaOlor, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.ipadx = 30;
        gridBagConstraints.ipady = 13;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        jPanel16.add(jPanel8, gridBagConstraints);

        jPanel13.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 0, 1, new java.awt.Color(0, 0, 0)));
        jPanel13.setPreferredSize(new java.awt.Dimension(150, 10));
        jPanel13.setLayout(new java.awt.GridBagLayout());

        etiquetaNitratos.setBackground(new java.awt.Color(0, 0, 0));
        etiquetaNitratos.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        etiquetaNitratos.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        etiquetaNitratos.setText(" NITRATOS");
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
        gridBagConstraints.ipadx = 30;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        jPanel13.add(etiquetaNitratos, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.ipadx = 30;
        gridBagConstraints.ipady = 13;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        jPanel16.add(jPanel13, gridBagConstraints);

        jPanel11.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 0, 1, new java.awt.Color(0, 0, 0)));
        jPanel11.setPreferredSize(new java.awt.Dimension(150, 10));
        jPanel11.setLayout(new java.awt.GridBagLayout());

        etiquetaConductividad.setBackground(new java.awt.Color(0, 0, 0));
        etiquetaConductividad.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        etiquetaConductividad.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        etiquetaConductividad.setText(" CONDUCTIVIDAD");
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
        gridBagConstraints.ipadx = 30;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        jPanel11.add(etiquetaConductividad, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.ipadx = 30;
        gridBagConstraints.ipady = 13;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        jPanel16.add(jPanel11, gridBagConstraints);

        jPanel9.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 0, 1, new java.awt.Color(0, 0, 0)));
        jPanel9.setPreferredSize(new java.awt.Dimension(150, 10));
        jPanel9.setLayout(new java.awt.GridBagLayout());

        etiquetaAlcalinidad.setBackground(new java.awt.Color(0, 0, 0));
        etiquetaAlcalinidad.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        etiquetaAlcalinidad.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        etiquetaAlcalinidad.setText(" ALCALINIDAD");
        etiquetaAlcalinidad.setToolTipText("");
        etiquetaAlcalinidad.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        etiquetaAlcalinidad.setMinimumSize(new java.awt.Dimension(150, 30));
        etiquetaAlcalinidad.setPreferredSize(new java.awt.Dimension(175, 10));
        etiquetaAlcalinidad.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                etiquetaAlcalinidadMousePressed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 30;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        jPanel9.add(etiquetaAlcalinidad, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.ipadx = 30;
        gridBagConstraints.ipady = 13;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        jPanel16.add(jPanel9, gridBagConstraints);

        jPanel5.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 0, 1, new java.awt.Color(0, 0, 0)));
        jPanel5.setPreferredSize(new java.awt.Dimension(150, 10));
        jPanel5.setLayout(new java.awt.GridBagLayout());

        etiquetaTurbidez.setBackground(new java.awt.Color(0, 0, 0));
        etiquetaTurbidez.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        etiquetaTurbidez.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        etiquetaTurbidez.setText(" TURBIDEZ");
        etiquetaTurbidez.setToolTipText("");
        etiquetaTurbidez.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        etiquetaTurbidez.setMinimumSize(new java.awt.Dimension(150, 30));
        etiquetaTurbidez.setPreferredSize(new java.awt.Dimension(175, 10));
        etiquetaTurbidez.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                etiquetaTurbidezMousePressed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 30;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        jPanel5.add(etiquetaTurbidez, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.ipadx = 30;
        gridBagConstraints.ipady = 13;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        jPanel16.add(jPanel5, gridBagConstraints);

        jPanel10.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 0, 1, new java.awt.Color(0, 0, 0)));
        jPanel10.setPreferredSize(new java.awt.Dimension(150, 10));
        jPanel10.setLayout(new java.awt.GridBagLayout());

        etiquetaDureza.setBackground(new java.awt.Color(0, 0, 0));
        etiquetaDureza.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        etiquetaDureza.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        etiquetaDureza.setText(" DUREZA TOTAL");
        etiquetaDureza.setToolTipText("");
        etiquetaDureza.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        etiquetaDureza.setMinimumSize(new java.awt.Dimension(150, 30));
        etiquetaDureza.setPreferredSize(new java.awt.Dimension(175, 10));
        etiquetaDureza.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                etiquetaDurezaMousePressed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 30;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        jPanel10.add(etiquetaDureza, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.ipadx = 30;
        gridBagConstraints.ipady = 13;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        jPanel16.add(jPanel10, gridBagConstraints);

        jPanel3.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 0, 1, new java.awt.Color(0, 0, 0)));
        jPanel3.setPreferredSize(new java.awt.Dimension(150, 10));
        jPanel3.setLayout(new java.awt.GridBagLayout());

        etiquetaColor.setBackground(new java.awt.Color(0, 0, 0));
        etiquetaColor.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        etiquetaColor.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        etiquetaColor.setText(" COLOR");
        etiquetaColor.setToolTipText("");
        etiquetaColor.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        etiquetaColor.setMinimumSize(new java.awt.Dimension(150, 30));
        etiquetaColor.setPreferredSize(new java.awt.Dimension(175, 10));
        etiquetaColor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                etiquetaColorMousePressed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 30;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        jPanel3.add(etiquetaColor, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.ipadx = 30;
        gridBagConstraints.ipady = 13;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        jPanel16.add(jPanel3, gridBagConstraints);

        jPanel19.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 0, 1, new java.awt.Color(0, 0, 0)));
        jPanel19.setPreferredSize(new java.awt.Dimension(150, 10));
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
        gridBagConstraints.ipadx = 30;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        jPanel19.add(jLabel18, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 30;
        gridBagConstraints.ipady = 13;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        jPanel16.add(jPanel19, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jPanel21.add(jPanel16, gridBagConstraints);

        jPanel17.setLayout(new java.awt.GridBagLayout());

        area012.setEditable(false);
        area012.setBackground(new java.awt.Color(255, 255, 255));
        area012.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        area012.setText("6.5 - 8.5");
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
        area52.setText("Máx. 3 N T U");
        area52.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        area52.setPreferredSize(new java.awt.Dimension(1, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.ipadx = 150;
        gridBagConstraints.ipady = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel17.add(area52, gridBagConstraints);

        area132.setEditable(false);
        area132.setBackground(new java.awt.Color(255, 255, 255));
        area132.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        area132.setText("Máx 400 mg/l");
        area132.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 1, 1, new java.awt.Color(0, 0, 0)));
        area132.setPreferredSize(new java.awt.Dimension(1, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.ipadx = 150;
        gridBagConstraints.ipady = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel17.add(area132, gridBagConstraints);

        area82.setEditable(false);
        area82.setBackground(new java.awt.Color(255, 255, 255));
        area82.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        area82.setText("< 10.100 uS/cm");
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
        area43.setText("ESC. PLATINO COBALTO");
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
        area62.setText("-");
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
        area63.setText("COLORIMETRICO");
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
        area72.setText("Máx. 400 mg/l");
        area72.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        area72.setPreferredSize(new java.awt.Dimension(1, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.ipadx = 150;
        gridBagConstraints.ipady = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel17.add(area72, gridBagConstraints);

        area123.setEditable(false);
        area123.setBackground(new java.awt.Color(255, 255, 255));
        area123.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        area123.setText("DIOZOTIZATION");
        area123.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        area123.setPreferredSize(new java.awt.Dimension(1, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.ipadx = 150;
        gridBagConstraints.ipady = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel17.add(area123, gridBagConstraints);

        area122.setEditable(false);
        area122.setBackground(new java.awt.Color(255, 255, 255));
        area122.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        area122.setText("Máx 0,10 mg/l");
        area122.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        area122.setPreferredSize(new java.awt.Dimension(1, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.ipadx = 150;
        gridBagConstraints.ipady = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel17.add(area122, gridBagConstraints);

        area83.setEditable(false);
        area83.setBackground(new java.awt.Color(255, 255, 255));
        area83.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        area83.setText("POTENCIOMETRICO");
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
        area32.setText("Sin olores extraños");
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
        area113.setText("REDUCCIÓN ZINC");
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
        area102.setText("Máx 0,30 mg/l");
        area102.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        area102.setPreferredSize(new java.awt.Dimension(1, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.ipadx = 150;
        gridBagConstraints.ipady = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel17.add(area102, gridBagConstraints);

        area133.setEditable(false);
        area133.setBackground(new java.awt.Color(255, 255, 255));
        area133.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        area133.setText("CLORURO BARIO");
        area133.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 1, 1, new java.awt.Color(0, 0, 0)));
        area133.setPreferredSize(new java.awt.Dimension(1, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.ipadx = 150;
        gridBagConstraints.ipady = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel17.add(area133, gridBagConstraints);

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
        area73.setText("COLORIMETRICO");
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
        area112.setText("Máx 45 mg/l");
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
        area93.setText("POTENCIOMETRICO");
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
        area92.setText("Máx. 1500 mg/l");
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
        area103.setText("BIPYRIDYL");
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
        area23.setText("FOTOMETRICO");
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
        area53.setText("ABSORCIÓN");
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
        area42.setText("Máx. 5 Pt-Co");
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
        jLabel4.setText("<html>RECUENTO OBTENIDO</html>");
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
        jLabel3.setText("<html>RECUENTO NORMAL</html>");
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

        jPanel15.setBackground(new java.awt.Color(255, 255, 255));
        jPanel15.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        jPanel15.setPreferredSize(new java.awt.Dimension(180, 23));
        jPanel15.setLayout(new java.awt.GridBagLayout());

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

        jPanel18.setBackground(new java.awt.Color(255, 255, 255));
        jPanel18.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        jPanel18.setPreferredSize(new java.awt.Dimension(180, 23));
        jPanel18.setLayout(new java.awt.GridBagLayout());

        cajaCloro.setAlignmentX(0.0F);
        cajaCloro.setAlignmentY(0.0F);
        cajaCloro.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 0, 0, new java.awt.Color(0, 0, 0)));
        cajaCloro.setMaximumSize(new java.awt.Dimension(180, 23));
        cajaCloro.setMinimumSize(new java.awt.Dimension(138, 23));
        cajaCloro.setName(""); // NOI18N
        cajaCloro.setPreferredSize(new java.awt.Dimension(138, 23));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel18.add(cajaCloro, gridBagConstraints);

        etiquetaCloroIn.setText("p.p.m.");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        jPanel18.add(etiquetaCloroIn, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        jPanel17.add(jPanel18, gridBagConstraints);

        jPanel23.setBackground(new java.awt.Color(255, 255, 255));
        jPanel23.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        jPanel23.setMinimumSize(new java.awt.Dimension(12, 40));
        jPanel23.setPreferredSize(new java.awt.Dimension(180, 23));
        jPanel23.setLayout(new java.awt.GridBagLayout());

        cajaAlcalinidad.setAlignmentX(0.0F);
        cajaAlcalinidad.setAlignmentY(0.0F);
        cajaAlcalinidad.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 0, 0, new java.awt.Color(0, 0, 0)));
        cajaAlcalinidad.setMaximumSize(new java.awt.Dimension(180, 23));
        cajaAlcalinidad.setMinimumSize(new java.awt.Dimension(145, 23));
        cajaAlcalinidad.setName(""); // NOI18N
        cajaAlcalinidad.setPreferredSize(new java.awt.Dimension(180, 23));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel23.add(cajaAlcalinidad, gridBagConstraints);

        etiquetaAlcalinidadIn.setText("mg/l");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        jPanel23.add(etiquetaAlcalinidadIn, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        jPanel17.add(jPanel23, gridBagConstraints);

        jPanel24.setBackground(new java.awt.Color(255, 255, 255));
        jPanel24.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        jPanel24.setPreferredSize(new java.awt.Dimension(180, 23));
        jPanel24.setLayout(new java.awt.GridBagLayout());

        cajaDurezatotal.setAlignmentX(0.0F);
        cajaDurezatotal.setAlignmentY(0.0F);
        cajaDurezatotal.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 0, 0, new java.awt.Color(0, 0, 0)));
        cajaDurezatotal.setMaximumSize(new java.awt.Dimension(180, 23));
        cajaDurezatotal.setMinimumSize(new java.awt.Dimension(145, 23));
        cajaDurezatotal.setName(""); // NOI18N
        cajaDurezatotal.setPreferredSize(new java.awt.Dimension(145, 23));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel24.add(cajaDurezatotal, gridBagConstraints);

        etiquetaDurezaIn.setText("mg/l");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        jPanel24.add(etiquetaDurezaIn, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        jPanel17.add(jPanel24, gridBagConstraints);

        jPanel25.setBackground(new java.awt.Color(255, 255, 255));
        jPanel25.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        jPanel25.setPreferredSize(new java.awt.Dimension(180, 23));
        jPanel25.setLayout(new java.awt.GridBagLayout());

        cajaConductividad.setAlignmentX(0.0F);
        cajaConductividad.setAlignmentY(0.0F);
        cajaConductividad.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 0, 0, new java.awt.Color(0, 0, 0)));
        cajaConductividad.setMaximumSize(new java.awt.Dimension(180, 23));
        cajaConductividad.setMinimumSize(new java.awt.Dimension(136, 23));
        cajaConductividad.setName(""); // NOI18N
        cajaConductividad.setPreferredSize(new java.awt.Dimension(140, 23));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel25.add(cajaConductividad, gridBagConstraints);

        etiquetaConductividadIn.setText("uS/cm");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        jPanel25.add(etiquetaConductividadIn, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        jPanel17.add(jPanel25, gridBagConstraints);

        jPanel26.setBackground(new java.awt.Color(255, 255, 255));
        jPanel26.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        jPanel26.setPreferredSize(new java.awt.Dimension(180, 23));
        jPanel26.setLayout(new java.awt.GridBagLayout());

        cajaSolidos.setAlignmentX(0.0F);
        cajaSolidos.setAlignmentY(0.0F);
        cajaSolidos.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 0, 0, new java.awt.Color(0, 0, 0)));
        cajaSolidos.setMaximumSize(new java.awt.Dimension(180, 23));
        cajaSolidos.setMinimumSize(new java.awt.Dimension(145, 23));
        cajaSolidos.setName(""); // NOI18N
        cajaSolidos.setPreferredSize(new java.awt.Dimension(145, 23));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel26.add(cajaSolidos, gridBagConstraints);

        etiquetaSolidosIn.setText("mg/l");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        jPanel26.add(etiquetaSolidosIn, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        jPanel17.add(jPanel26, gridBagConstraints);

        jPanel27.setBackground(new java.awt.Color(255, 255, 255));
        jPanel27.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        jPanel27.setLayout(new java.awt.GridBagLayout());

        cajaNitratos.setAlignmentX(0.0F);
        cajaNitratos.setAlignmentY(0.0F);
        cajaNitratos.setBorder(null);
        cajaNitratos.setMaximumSize(new java.awt.Dimension(180, 23));
        cajaNitratos.setMinimumSize(new java.awt.Dimension(10, 23));
        cajaNitratos.setName(""); // NOI18N
        cajaNitratos.setPreferredSize(new java.awt.Dimension(90, 23));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipady = -1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel27.add(cajaNitratos, gridBagConstraints);

        etiquetaNitratosIn.setText("mg/l");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipady = -1;
        gridBagConstraints.insets = new java.awt.Insets(0, 2, 0, 2);
        jPanel27.add(etiquetaNitratosIn, gridBagConstraints);

        comboNitratos.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Menor de", " " }));
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
        gridBagConstraints.ipady = 4;
        jPanel27.add(comboNitratos, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 11;
        jPanel17.add(jPanel27, gridBagConstraints);

        jPanel31.setBackground(new java.awt.Color(255, 255, 255));
        jPanel31.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        jPanel31.setLayout(new java.awt.GridBagLayout());

        cajaNitritos.setAlignmentX(0.0F);
        cajaNitritos.setAlignmentY(0.0F);
        cajaNitritos.setBorder(null);
        cajaNitritos.setMaximumSize(new java.awt.Dimension(180, 23));
        cajaNitritos.setMinimumSize(new java.awt.Dimension(10, 23));
        cajaNitritos.setName(""); // NOI18N
        cajaNitritos.setPreferredSize(new java.awt.Dimension(90, 23));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipady = -1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel31.add(cajaNitritos, gridBagConstraints);

        etiquetaNitritosIn.setText("mg/l");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipady = -1;
        gridBagConstraints.insets = new java.awt.Insets(0, 2, 0, 2);
        jPanel31.add(etiquetaNitritosIn, gridBagConstraints);

        comboNitritos.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Menor de", "" }));
        comboNitritos.setUI(new BasicComboBoxUI() {
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
        gridBagConstraints.ipady = 4;
        jPanel31.add(comboNitritos, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 12;
        jPanel17.add(jPanel31, gridBagConstraints);

        jPanel32.setBackground(new java.awt.Color(255, 255, 255));
        jPanel32.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 1, 1, new java.awt.Color(0, 0, 0)));
        jPanel32.setLayout(new java.awt.GridBagLayout());

        cajaSulfatos.setAlignmentX(0.0F);
        cajaSulfatos.setAlignmentY(0.0F);
        cajaSulfatos.setBorder(null);
        cajaSulfatos.setMaximumSize(new java.awt.Dimension(180, 23));
        cajaSulfatos.setMinimumSize(new java.awt.Dimension(10, 23));
        cajaSulfatos.setName(""); // NOI18N
        cajaSulfatos.setPreferredSize(new java.awt.Dimension(90, 23));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipady = -2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel32.add(cajaSulfatos, gridBagConstraints);

        etiquetaSulfatosIn.setText("mg/l");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipady = -2;
        gridBagConstraints.insets = new java.awt.Insets(0, 2, 0, 2);
        jPanel32.add(etiquetaSulfatosIn, gridBagConstraints);

        comboSulfatos.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Menor de", "" }));
        comboSulfatos.setUI(new BasicComboBoxUI() {
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
        gridBagConstraints.ipady = 3;
        jPanel32.add(comboSulfatos, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 13;
        jPanel17.add(jPanel32, gridBagConstraints);

        jPanel33.setBackground(new java.awt.Color(255, 255, 255));
        jPanel33.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        jPanel33.setLayout(new java.awt.GridBagLayout());

        cajaColor.setAlignmentX(0.0F);
        cajaColor.setAlignmentY(0.0F);
        cajaColor.setBorder(null);
        cajaColor.setMaximumSize(new java.awt.Dimension(180, 23));
        cajaColor.setMinimumSize(new java.awt.Dimension(10, 23));
        cajaColor.setName(""); // NOI18N
        cajaColor.setPreferredSize(new java.awt.Dimension(83, 23));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipady = -1;
        jPanel33.add(cajaColor, gridBagConstraints);

        etiquetaColorIn.setText("Pt-Co");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipady = -1;
        gridBagConstraints.insets = new java.awt.Insets(0, 2, 0, 2);
        jPanel33.add(etiquetaColorIn, gridBagConstraints);

        comboColor.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Menor de", "" }));
        comboColor.setUI(new BasicComboBoxUI() {
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
        gridBagConstraints.ipady = 4;
        jPanel33.add(comboColor, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        jPanel17.add(jPanel33, gridBagConstraints);

        jPanel34.setBackground(new java.awt.Color(255, 255, 255));
        jPanel34.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        jPanel34.setLayout(new java.awt.GridBagLayout());

        cajaTurbidez.setAlignmentX(0.0F);
        cajaTurbidez.setAlignmentY(0.0F);
        cajaTurbidez.setBorder(null);
        cajaTurbidez.setMaximumSize(new java.awt.Dimension(180, 23));
        cajaTurbidez.setMinimumSize(new java.awt.Dimension(10, 23));
        cajaTurbidez.setName(""); // NOI18N
        cajaTurbidez.setPreferredSize(new java.awt.Dimension(90, 23));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipady = -1;
        jPanel34.add(cajaTurbidez, gridBagConstraints);

        etiquetaTurbidezIn.setText("NTU");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipady = -1;
        gridBagConstraints.insets = new java.awt.Insets(0, 2, 0, 2);
        jPanel34.add(etiquetaTurbidezIn, gridBagConstraints);

        comboTurbidez.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Menor de", "" }));
        comboTurbidez.setUI(new BasicComboBoxUI() {
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
        gridBagConstraints.ipady = 4;
        jPanel34.add(comboTurbidez, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        jPanel17.add(jPanel34, gridBagConstraints);

        jPanel35.setBackground(new java.awt.Color(255, 255, 255));
        jPanel35.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        jPanel35.setLayout(new java.awt.GridBagLayout());

        cajaHierro.setAlignmentX(0.0F);
        cajaHierro.setAlignmentY(0.0F);
        cajaHierro.setBorder(null);
        cajaHierro.setMaximumSize(new java.awt.Dimension(180, 23));
        cajaHierro.setMinimumSize(new java.awt.Dimension(10, 23));
        cajaHierro.setName(""); // NOI18N
        cajaHierro.setPreferredSize(new java.awt.Dimension(90, 23));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipady = -1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel35.add(cajaHierro, gridBagConstraints);

        etiquetaHierroIn.setText("mg/l");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipady = -1;
        gridBagConstraints.insets = new java.awt.Insets(0, 2, 0, 2);
        jPanel35.add(etiquetaHierroIn, gridBagConstraints);

        comboHierro.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Menor de", "" }));
        comboHierro.setUI(new BasicComboBoxUI() {
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
        gridBagConstraints.ipady = 4;
        jPanel35.add(comboHierro, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        jPanel17.add(jPanel35, gridBagConstraints);

        jPanel20.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        jPanel20.setPreferredSize(new java.awt.Dimension(180, 23));
        jPanel20.setLayout(new java.awt.GridBagLayout());

        comboOlor.setBackground(new java.awt.Color(255, 255, 255));
        comboOlor.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Sin olores extraños", "Otros" }));
        comboOlor.setBorder(null);
        comboOlor.setMinimumSize(new java.awt.Dimension(179, 20));
        comboOlor.setName(""); // NOI18N
        comboOlor.setPreferredSize(new java.awt.Dimension(179, 20));
        comboOlor.setUI(new BasicComboBoxUI() {
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
        gridBagConstraints.ipady = 2;
        jPanel20.add(comboOlor, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        jPanel17.add(jPanel20, gridBagConstraints);

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
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 20);
        jPanel22.add(jLabel19, gridBagConstraints);

        cajaFechaAnalisis.setDateFormatString("dd-MM-yyyy");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
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

        checkPoner.setSelected(true);
        checkPoner.setText("Poner fecha de vencimiento");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        jPanel1.add(checkPoner, gridBagConstraints);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 926, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 489, Short.MAX_VALUE)
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
        String ph, cloro, olor, color, turbidez, alcalinidad, durezaTotal, conductividad,
                solidos, hierro, nitratos, nitritos, sulfatos;
        if (cajapH.isEnabled()) {
            ph = cajapH.getText();
        } else {
            ph = "-2";
        }

        if (cajaCloro.isEnabled()) {
            cloro = cajaCloro.getText();
        } else {
            cloro = "-2";
        }

        if (comboOlor.isEnabled()) {
            olor = comboOlor.getSelectedItem().toString();
        } else {
            olor = "-2";
        }

        if (cajaColor.isEnabled()) {
            color = comboColor.getSelectedItem().toString() + " " + cajaColor.getText();
        } else {
            color = "-2";
        }

        if (cajaTurbidez.isEnabled()) {
            turbidez = comboTurbidez.getSelectedItem().toString() + " " + cajaTurbidez.getText();
        } else {
            turbidez = "-2";
        }

        if (cajaAlcalinidad.isEnabled()) {
            alcalinidad = cajaAlcalinidad.getText();
        } else {
            alcalinidad = "-2";
        }

        if (cajaDurezatotal.isEnabled()) {
            durezaTotal = cajaDurezatotal.getText();
        } else {
            durezaTotal = "-2";
        }

        if (cajaConductividad.isEnabled()) {
            conductividad = cajaConductividad.getText();
        } else {
            conductividad = "-2";
        }

        if (cajaSolidos.isEnabled()) {
            solidos = cajaSolidos.getText();
        } else {
            solidos = "-2";
        }

        if (cajaHierro.isEnabled()) {
            hierro = comboHierro.getSelectedItem().toString() + " " + cajaHierro.getText();
        } else {
            hierro = "-2";
        }

        if (cajaNitratos.isEnabled()) {
            nitratos = comboNitratos.getSelectedItem().toString() + " " + cajaNitratos.getText();
        } else {
            nitratos = "-2";
        }

        if (cajaNitritos.isEnabled()) {
            nitritos = comboNitritos.getSelectedItem().toString() + " " + cajaNitritos.getText();
        } else {
            nitritos = "-2";
        }

        if (cajaSulfatos.isEnabled()) {
            sulfatos = comboSulfatos.getSelectedItem().toString() + " " + cajaSulfatos.getText();
        } else {
            sulfatos = "-2";
        }
        String valores[] = {String.valueOf(id), ph, cloro, olor, color, turbidez, alcalinidad,
            durezaTotal, conductividad, solidos, hierro, nitratos, nitritos, sulfatos, "", ""};
        Map m = new HashMap();
        java.util.Date fm = cajaFechaAnalisis.getDate(); //obtener fecha
        Long dm = fm.getTime(); //sacar timepo
        java.sql.Date fechaAnalisis = new java.sql.Date(dm); //cast de fecha
        m.put("fechaAnalisis", fechaAnalisis);
        m.put("idmuestras", id);
        c.guardarFechaAnalisis(m);
        if (editar) {
            String conclusion = crearConclusion();
            valores[14] = conclusion;
            Resultados r = new Resultados();
            valores[15] = JOptionPane.showInputDialog("Digite la observacion:", auxObservaciones);
            valores[15] = valores[15].isBlank() ? "" : valores[15].trim().endsWith(".") ? valores[15] : valores[15] + ".";
            if (olor.equals("Otros")) {
                olor = JOptionPane.showInputDialog(null, "Ingrese los datos de olor:", auxOlor);
                valores[3] = olor;
            }
            r.setResultadoFQ(valores);
            r.setFechaAnalisis(fechaAnalisis);
            r.setTipo("Físico químico de agua básico");
            r.setFechaAnalisis(fechaAnalisis);
            File rv = new File(c.recuperarRutas("Reportes") + "\\" + pdf);
            File rn = new File(c.recuperarRutas("Reportes") + "\\(BORRADO) " + pdf);
            rv.renameTo(rn);
            if (c.editarFQAgua(r)) {
                c.guardarObservaciones(valores[15], id);
                c.actualizarVencimiento(r);
                File tn = new File(c.recuperarRutas("Reportes") + "\\" + pdf);
                File tnnuevo = new File(c.recuperarRutas("Reportes") + "\\(BORRADO) " + pdf);
                tn.renameTo(tnnuevo);
                c.guardarConclusion(conclusion, id);
                c.esconderFechaVencimiento(id, checkPoner.isSelected());
                this.dispose();
                c.generarReporteFQAgua(id, procedencia);
            }
        } else {
            String conclusion = crearConclusion();
            Resultados r = new Resultados();
            valores[14] = conclusion;
            valores[15] = JOptionPane.showInputDialog("Digite la observacion:");
            valores[15] = valores[15].trim().endsWith(".") ? valores[15] : valores[15] + ".";
            if (olor.equals("Otros")) {
                olor = JOptionPane.showInputDialog(null, "Ingrese los datos de olor:");
                valores[3] = olor;
            }
            r.setResultadoFQ(valores);
            r.setTipo("Físico químico de agua básico");
            r.setIdmuestras(id);
            r.setFechaAnalisis(fechaAnalisis);
            if (c.guardarResultadoFQAgua(r)) {
                c.agregarVencimiento(r);
                c.guardarObservaciones(valores[15], id);
                c.guardarConclusion(conclusion, id);
                c.esconderFechaVencimiento(id, checkPoner.isSelected());
                this.dispose();
                c.generarReporteFQAgua(id, procedencia);
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

    private void etiquetaCloroMousePressed(java.awt.event.MouseEvent evt) {
        activarCloro = !activarCloro;
        cajaCloro.setEnabled(activarCloro);
        etiquetaCloroIn.setEnabled(activarCloro);
        area22.setEnabled(activarCloro);
        area23.setEnabled(activarCloro);
        if (activarCloro) {
            jPanel2.setBackground(new Color(240, 240, 240));
            jPanel18.setBackground(new Color(255, 255, 255));
        } else {
            jPanel2.setBackground(new Color(240, 100, 100));
            jPanel18.setBackground(new Color(240, 240, 240));
        }
    }

    private void etiquetaOlorMousePressed(java.awt.event.MouseEvent evt) {
        activarOlor = !activarOlor;
        comboOlor.setEnabled(activarOlor);
        area32.setEnabled(activarOlor);
        area33.setEnabled(activarOlor);
        if (activarOlor) {
            jPanel8.setBackground(new Color(240, 240, 240));
            jPanel20.setBackground(new Color(255, 255, 255));
        } else {
            jPanel8.setBackground(new Color(240, 100, 100));
            jPanel20.setBackground(new Color(240, 240, 240));
        }
    }

    private void etiquetaColorMousePressed(java.awt.event.MouseEvent evt) {
        activarColor = !activarColor;
        comboColor.setEnabled(activarColor);
        cajaColor.setEnabled(activarColor);
        etiquetaColorIn.setEnabled(activarColor);
        area42.setEnabled(activarColor);
        area43.setEnabled(activarColor);
        if (activarColor) {
            jPanel3.setBackground(new Color(240, 240, 240));
            jPanel33.setBackground(new Color(255, 255, 255));
        } else {
            jPanel3.setBackground(new Color(240, 100, 100));
            jPanel33.setBackground(new Color(240, 240, 240));
        }
    }

    private void etiquetaTurbidezMousePressed(java.awt.event.MouseEvent evt) {
        activarTurbidez = !activarTurbidez;
        comboTurbidez.setEnabled(activarTurbidez);
        cajaTurbidez.setEnabled(activarTurbidez);
        etiquetaTurbidezIn.setEnabled(activarTurbidez);
        area52.setEnabled(activarTurbidez);
        area53.setEnabled(activarTurbidez);
        if (activarTurbidez) {
            jPanel5.setBackground(new Color(240, 240, 240));
            jPanel34.setBackground(new Color(255, 255, 255));
        } else {
            jPanel5.setBackground(new Color(240, 100, 100));
            jPanel34.setBackground(new Color(240, 240, 240));
        }
    }

    private void etiquetaAlcalinidadMousePressed(java.awt.event.MouseEvent evt) {
        activarAlcalinidad = !activarAlcalinidad;
        cajaAlcalinidad.setEnabled(activarAlcalinidad);
        etiquetaAlcalinidadIn.setEnabled(activarAlcalinidad);
        area62.setEnabled(activarAlcalinidad);
        area63.setEnabled(activarAlcalinidad);
        if (activarAlcalinidad) {
            jPanel9.setBackground(new Color(240, 240, 240));
            jPanel23.setBackground(new Color(255, 255, 255));
        } else {
            jPanel9.setBackground(new Color(240, 100, 100));
            jPanel23.setBackground(new Color(240, 240, 240));
        }
    }

    private void etiquetaDurezaMousePressed(java.awt.event.MouseEvent evt) {
        activarDureza = !activarDureza;
        cajaDurezatotal.setEnabled(activarDureza);
        etiquetaDurezaIn.setEnabled(activarDureza);
        area72.setEnabled(activarDureza);
        area73.setEnabled(activarDureza);
        if (activarDureza) {
            jPanel10.setBackground(new Color(240, 240, 240));
            jPanel24.setBackground(new Color(255, 255, 255));
        } else {
            jPanel10.setBackground(new Color(240, 100, 100));
            jPanel24.setBackground(new Color(240, 240, 240));
        }
    }

    private void etiquetaConductividadMousePressed(java.awt.event.MouseEvent evt) {
        activarConductividad = !activarConductividad;
        cajaConductividad.setEnabled(activarConductividad);
        etiquetaConductividadIn.setEnabled(activarConductividad);
        area82.setEnabled(activarConductividad);
        area83.setEnabled(activarConductividad);
        if (activarConductividad) {
            jPanel11.setBackground(new Color(240, 240, 240));
            jPanel25.setBackground(new Color(255, 255, 255));
        } else {
            jPanel11.setBackground(new Color(240, 100, 100));
            jPanel25.setBackground(new Color(240, 240, 240));
        }
    }

    private void etiquetaSolidosMousePressed(java.awt.event.MouseEvent evt) {
        activarSolidos = !activarSolidos;
        cajaSolidos.setEnabled(activarSolidos);
        etiquetaSolidosIn.setEnabled(activarSolidos);
        area92.setEnabled(activarSolidos);
        area93.setEnabled(activarSolidos);
        if (activarSolidos) {
            jPanel12.setBackground(new Color(240, 240, 240));
            jPanel26.setBackground(new Color(255, 255, 255));
        } else {
            jPanel12.setBackground(new Color(240, 100, 100));
            jPanel26.setBackground(new Color(240, 240, 240));
        }
    }

    private void etiquetaHierroMousePressed(java.awt.event.MouseEvent evt) {
        activarHierro = !activarHierro;
        comboHierro.setEnabled(activarHierro);
        cajaHierro.setEnabled(activarHierro);
        etiquetaHierroIn.setEnabled(activarHierro);
        area102.setEnabled(activarHierro);
        area103.setEnabled(activarHierro);
        if (activarHierro) {
            jPanel4.setBackground(new Color(240, 240, 240));
            jPanel35.setBackground(new Color(255, 255, 255));
        } else {
            jPanel4.setBackground(new Color(240, 100, 100));
            jPanel35.setBackground(new Color(240, 240, 240));
        }
    }

    private void etiquetaNitratosMousePressed(java.awt.event.MouseEvent evt) {
        activarNitratos = !activarNitratos;
        comboNitratos.setEnabled(activarNitratos);
        cajaNitratos.setEnabled(activarNitratos);
        etiquetaNitratosIn.setEnabled(activarNitratos);
        area112.setEnabled(activarNitratos);
        area113.setEnabled(activarNitratos);
        if (activarNitratos) {
            jPanel13.setBackground(new Color(240, 240, 240));
            jPanel27.setBackground(new Color(255, 255, 255));
        } else {
            jPanel13.setBackground(new Color(240, 100, 100));
            jPanel27.setBackground(new Color(240, 240, 240));
        }
    }//GEN-LAST:event_etiquetaNitratosMousePressed

    private void etiquetaNitritosMousePressed(java.awt.event.MouseEvent evt) {
        activarNitritos = !activarNitritos;
        comboNitritos.setEnabled(activarNitritos);
        cajaNitritos.setEnabled(activarNitritos);
        etiquetaNitritosIn.setEnabled(activarNitritos);
        area122.setEnabled(activarNitritos);
        area123.setEnabled(activarNitritos);
        if (activarNitritos) {
            jPanel14.setBackground(new Color(240, 240, 240));
            jPanel31.setBackground(new Color(255, 255, 255));
        } else {
            jPanel14.setBackground(new Color(240, 100, 100));
            jPanel31.setBackground(new Color(240, 240, 240));
        }
    }

    private void etiquetaSulfatosMousePressed(java.awt.event.MouseEvent evt) {
        activarSulfatos = !activarSulfatos;
        comboSulfatos.setEnabled(activarSulfatos);
        cajaSulfatos.setEnabled(activarSulfatos);
        etiquetaSulfatosIn.setEnabled(activarSulfatos);
        area132.setEnabled(activarSulfatos);
        area133.setEnabled(activarSulfatos);
        if (activarSulfatos) {
            jPanel6.setBackground(new Color(240, 240, 240));
            jPanel32.setBackground(new Color(255, 255, 255));
        } else {
            jPanel6.setBackground(new Color(240, 100, 100));
            jPanel32.setBackground(new Color(240, 240, 240));
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
    private javax.swing.JTextField area132;
    private javax.swing.JTextField area133;
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
    private javax.swing.JTextField cajaAlcalinidad;
    private javax.swing.JTextField cajaCloro;
    private javax.swing.JTextField cajaColor;
    private javax.swing.JTextField cajaConductividad;
    private javax.swing.JTextField cajaDurezatotal;
    private com.toedter.calendar.JDateChooser cajaFechaAnalisis;
    private javax.swing.JTextField cajaHierro;
    private javax.swing.JTextField cajaNitratos;
    private javax.swing.JTextField cajaNitritos;
    private javax.swing.JTextField cajaSolidos;
    private javax.swing.JTextField cajaSulfatos;
    private javax.swing.JTextField cajaTurbidez;
    private javax.swing.JTextField cajapH;
    private javax.swing.JCheckBox checkPoner;
    private javax.swing.JComboBox<String> comboColor;
    private javax.swing.JComboBox<String> comboHierro;
    private javax.swing.JComboBox<String> comboNitratos;
    private javax.swing.JComboBox<String> comboNitritos;
    private javax.swing.JComboBox<String> comboOlor;
    private javax.swing.JComboBox<String> comboSulfatos;
    private javax.swing.JComboBox<String> comboTurbidez;
    private javax.swing.JLabel etiquetaAlcalinidad;
    private javax.swing.JLabel etiquetaAlcalinidadIn;
    private javax.swing.JLabel etiquetaCloro;
    private javax.swing.JLabel etiquetaCloroIn;
    private javax.swing.JLabel etiquetaColor;
    private javax.swing.JLabel etiquetaColorIn;
    private javax.swing.JLabel etiquetaConductividad;
    private javax.swing.JLabel etiquetaConductividadIn;
    private javax.swing.JLabel etiquetaDureza;
    private javax.swing.JLabel etiquetaDurezaIn;
    private javax.swing.JLabel etiquetaHierro;
    private javax.swing.JLabel etiquetaHierroIn;
    private javax.swing.JLabel etiquetaNitratos;
    private javax.swing.JLabel etiquetaNitratosIn;
    private javax.swing.JLabel etiquetaNitritos;
    private javax.swing.JLabel etiquetaNitritosIn;
    private javax.swing.JLabel etiquetaOlor;
    private javax.swing.JLabel etiquetaPh;
    private javax.swing.JLabel etiquetaSolidos;
    private javax.swing.JLabel etiquetaSolidosIn;
    private javax.swing.JLabel etiquetaSulfatos;
    private javax.swing.JLabel etiquetaSulfatosIn;
    private javax.swing.JLabel etiquetaTitulo;
    private javax.swing.JLabel etiquetaTurbidez;
    private javax.swing.JLabel etiquetaTurbidezIn;
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
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;


    private String crearConclusion() {
        String conclusion;
        boolean ph = false;
        if (cajapH.isEnabled()) {
            ph = !(Double.parseDouble(cajapH.getText()) >= 6.5 && Double.parseDouble(cajapH.getText()) <= 8.5);
        }
        boolean olor = false;
        if (comboOlor.isEnabled()) {
            olor = !(comboOlor.getSelectedItem().toString().equals("Sin olores extraños"));
        }

        boolean color = false;
        if (cajaColor.isEnabled()) {
            color = !(Double.parseDouble(cajaColor.getText()) <= 5);
        }

        boolean turbidez = false;
        if (cajaTurbidez.isEnabled()) {
            turbidez = !(Double.parseDouble(cajaTurbidez.getText()) <= 3);
        }

        boolean durezaTotal = false;
        if (cajaDurezatotal.isEnabled()) {
            durezaTotal = !(Double.parseDouble(cajaDurezatotal.getText()) < 400);
        }

        boolean conductividad = false;
        if (cajaConductividad.isEnabled()) {
            conductividad = !(Double.parseDouble(cajaConductividad.getText()) < 10000);
        }

        boolean solidos = false;
        if (cajaSolidos.isEnabled()) {
            solidos = !(Double.parseDouble(cajaSolidos.getText()) < 1500);
        }

        boolean hierro = false;
        if (cajaHierro.isEnabled()) {
            hierro = !(Double.parseDouble(cajaHierro.getText()) <= 0.3);
        }

        boolean nitratos = false;
        if (cajaNitratos.isEnabled()) {
            nitratos = !(Double.parseDouble(cajaNitratos.getText()) <= 45);
        }

        boolean nitritos = false;
        if (cajaNitritos.isEnabled()) {
            nitritos = !(Double.parseDouble(cajaNitritos.getText()) <= 0.1);
        }

        boolean sulfatos = false;
        if (cajaSulfatos.isEnabled()) {
            sulfatos = !(Double.parseDouble(cajaSulfatos.getText()) <= 400);
        }
        if (!ph && !olor && !color && !turbidez && !durezaTotal && !conductividad && !solidos
                && !hierro && !nitratos && !nitritos && !sulfatos) {
            conclusion = "En los parámetros analizados, la muestra cumple con las especificaciones físico químicas "
                    + "estipuladas por el art. 982 del Código Alimentario Argentino (Ley 18.284).";
        } else {
            conclusion = "Dado el valor de";
            if (ph) {
                conclusion += " pH,";
            }
            if (olor) {
                conclusion += " olor,";
            }
            if (color) {
                conclusion += " color,";
            }
            if (turbidez) {
                conclusion += " turbidez,";
            }
            if (durezaTotal) {
                conclusion += " dureza total,";
            }
            if (conductividad) {
                conclusion += " conductividad,";
            }
            if (solidos) {
                conclusion += " sólidos disueltos totales,";
            }
            if (hierro) {
                conclusion += " hierro,";
            }
            if (nitratos) {
                conclusion += " nitratos,";
            }
            if (nitritos) {
                conclusion += " nitritos,";
            }
            if (sulfatos) {
                conclusion += " sulfatos,";
            }
            conclusion = conclusion.substring(0, conclusion.length() - 1);
            conclusion += " la muestra no cumple con las especificaciones físico químicas"
                    + " estipuladas por el art. 982 del Código Alimentario Argentino (Ley 18284)";
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
