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

public class TablaBaseHelada extends javax.swing.JDialog {

    int id;
    String procedencia, pdf, auxObservaciones;
    Consultas c = Consultas.getInstancia();
    boolean editar, activarGermenes = true, activarTotales = true, activarFecales = true,
            activarEscherichia = true, activarMohos = true, activarSalmonella = true, activarStaphilococos = true;
    boolean[] auxBool = new boolean[6];
    Frame parent;

    public TablaBaseHelada(java.awt.Frame parent, boolean modal, int id, String procedencia,
                           boolean editar, String pdf) {
        super(parent, modal);
        this.parent = parent;
        this.procedencia = procedencia;
        this.id = id;
        this.editar = editar;
        this.pdf = pdf;
        initComponents();
        setTitle("ID " + id + ". " + c.obtenerProcedencia(id) + ". Base helada");
        setLocationRelativeTo(null);
        if (editar == true) {
            Map<String, String> resultados = c.recuperarResultadosBaseHelada(id);
            if (resultados == null) {
                this.editar = false;
            } else {
                String aux = "";
                DecimalFormat df = new DecimalFormat("#.##");

                if (resultados.get("germenes").contains("null")) {
                    if (resultados.get("germenes").contains("-2")) {
                        etiquetaGermenesMousePressed(click(etiquetaGermenes));
                    } else if (resultados.get("germenes").contains("-1")) {
                    } else {
                        if (resultados.get("germenes").toLowerCase().contains("menor")) {
                            checkGermenes.doClick();
                        } else {
                            aux = resultados.get("germenes").replaceAll("[^0-9?!\\.]", "");
                            cajaGermenes.setText(df.format(Double.parseDouble(aux)).replace(",", "."));
                        }
                    }
                }

                if (resultados.get("coliformesTotales").contains("null")) {
                    if (resultados.get("coliformesTotales").toUpperCase().equals("MENOR A 10 UFC/G")) {
                        checkTotales.doClick();
                    } else if (resultados.get("coliformesTotales").contains("-2")) {
                        etiquetaTotalesMousePressed(click(etiquetaTotales));
                    } else if (resultados.get("coliformesTotales").contains("-1")) {
                    } else {
                        aux = resultados.get("coliformesTotales").replaceAll("[^0-9?!\\.]", "");
                        cajaTotales.setText(df.format(Double.parseDouble(aux)));
                        if (resultados.get("coliformesTotales").toLowerCase().contains("menor a ")) {
                            comboTotales.setSelectedIndex(0);
                        } else {
                            comboTotales.setSelectedIndex(1);
                        }
                    }
                }

                if (resultados.get("coliformesFecales").contains("null")) {
                    if (resultados.get("coliformesFecales").toUpperCase().equals("MENOR A 1 UFC/G")) {
                        checkFecales.doClick();
                    } else if (resultados.get("coliformesFecales").contains("-2")) {
                        etiquetaFecalesMousePressed(click(etiquetaFecales));
                    } else if (resultados.get("coliformesFecales").contains("-1")) {
                    } else {
                        aux = resultados.get("coliformesFecales").replaceAll("[^0-9?!\\.]", "");
                        cajaFecales.setText(df.format(Double.parseDouble(aux)));
                        if (resultados.get("coliformesFecales").toLowerCase().contains("menor a ")) {
                            comboFecales.setSelectedIndex(0);
                        } else {
                            comboFecales.setSelectedIndex(1);
                        }
                    }
                }

                if (resultados.get("salmonella").contains("null")) {
                    if (resultados.get("salmonella").contains("-2")) {
                        jPanel6MousePressed(click(jPanel6));
                    } else if (resultados.get("salmonella").contains("-1")) {
                    } else {
                        if (resultados.get("salmonella").startsWith("Ausencia")) {
                            comboSalmonella.setSelectedIndex(0);
                        } else {
                            comboSalmonella.setSelectedIndex(1);
                        }
                    }
                }

                if (resultados.get("escherichia").contains("null")) {
                    if (resultados.get("escherichia").startsWith("Ausencia")) {
                        comboEscherichia.setSelectedIndex(0);
                    } else if (resultados.get("escherichia").contains("-2")) {
                        etiquetaEscherichiaMousePressed(click(etiquetaEscherichia));
                    } else if (resultados.get("escherichia").contains("-1")) {
                    } else {
                        comboEscherichia.setSelectedIndex(1);
                    }
                }

                if (resultados.get("staphilococos").contains("null")) {
                    if (resultados.get("staphilococos").contains("MENOR A 10")) {
                        checkStaphilococos.doClick();
                    } else if (resultados.get("staphilococos").contains("-2")) {
                        jPanel15MousePressed(click(jPanel15));
                    } else if (resultados.get("staphilococos").contains("-1")) {
                    } else {
                        if (resultados.get("staphilococos").startsWith("Menor a")) {
                            comboStaphilococos.setSelectedIndex(0);
                        } else {
                            comboStaphilococos.setSelectedIndex(1);
                        }
                        aux = resultados.get("staphilococos").replaceAll("[^0-9?!\\.]", "");
                        cajaStaphilococos.setText(df.format(Double.parseDouble(aux)));
                    }
                }

                if (resultados.get("mohos").contains("null")) {
                    if (resultados.get("mohos").toUpperCase().contains("MENOR A 10 UFC")) {
                        checkMohos.doClick();
                    } else if (resultados.get("mohos").contains("-2")) {
                        etiquetaMohosMousePressed(click(etiquetaMohos));
                    } else if (resultados.get("mohos").contains("-1")) {
                    } else {
                        aux = resultados.get("mohos").replaceAll("[^0-9?!\\.]", "");
                        cajaMoho.setText(String.valueOf(df.format(Double.parseDouble(aux)).replace(",", ".")));
                    }
                }

                if (resultados.get("fechaAnalisis").contains("null")) {
                    java.sql.Date fecha = java.sql.Date.valueOf(resultados.get("fechaAnalisis"));
                    java.util.Date utilFecha = null;
                    try {
                        utilFecha = new java.util.Date(fecha.getTime());
                    } catch (Exception e) {
                        System.err.println("error, " + e);
                    }
                    cajaFechaAnalisis.setDate(utilFecha);
                }
                auxObservaciones = c.recuperarObservaciones(id);
            }
        }

        ImageIcon icon = new ImageIcon("src\\vista\\icono.png");
        this.setIconImage(icon.getImage());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

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
        area43 = new javax.swing.JTextArea();
        botonGenerar = new javax.swing.JButton();
        etiquetaTitulo = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        etiquetaEscherichia = new javax.swing.JLabel();
        jScrollPane14 = new javax.swing.JScrollPane();
        area45 = new javax.swing.JTextArea();
        jLabel3 = new javax.swing.JLabel();
        cajaFechaAnalisis = new com.toedter.calendar.JDateChooser();
        jPanel9 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        comboStaphilococos = new javax.swing.JComboBox<>();
        etiquetaStaphilococosIn = new javax.swing.JLabel();
        cajaStaphilococos = new javax.swing.JTextField();
        jPanel10 = new javax.swing.JPanel();
        comboSalmonella = new javax.swing.JComboBox<>();
        jPanel12 = new javax.swing.JPanel();
        comboEscherichia = new javax.swing.JComboBox<>();
        jPanel16 = new javax.swing.JPanel();
        comboFecales = new javax.swing.JComboBox<>();
        etiquetaFecalesIn = new javax.swing.JLabel();
        cajaFecales = new javax.swing.JTextField();
        jPanel17 = new javax.swing.JPanel();
        comboTotales = new javax.swing.JComboBox<>();
        etiquetaTotalesIn = new javax.swing.JLabel();
        cajaTotales = new javax.swing.JTextField();
        jPanel18 = new javax.swing.JPanel();
        cajaMoho = new javax.swing.JTextField();
        etiquetaUnidadMohos = new javax.swing.JLabel();
        etiquetaMenorMohos = new javax.swing.JLabel();
        jPanel19 = new javax.swing.JPanel();
        cajaGermenes = new javax.swing.JTextField();
        etiquetaUnidadGermenes = new javax.swing.JLabel();
        etiquetaMenorGermenes = new javax.swing.JLabel();
        checkGermenes = new javax.swing.JCheckBox();
        checkTotales = new javax.swing.JCheckBox();
        jPanel14 = new javax.swing.JPanel();
        etiquetaMohos = new javax.swing.JLabel();
        jScrollPane13 = new javax.swing.JScrollPane();
        area44 = new javax.swing.JTextArea();
        checkStaphilococos = new javax.swing.JCheckBox();
        checkMohos = new javax.swing.JCheckBox();
        jScrollPane15 = new javax.swing.JScrollPane();
        area46 = new javax.swing.JTextArea();
        jPanel15 = new javax.swing.JPanel();
        etiquetaSalmonella = new javax.swing.JLabel();
        checkFecales = new javax.swing.JCheckBox();

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
        jPanel2.setPreferredSize(new java.awt.Dimension(150, 10));
        jPanel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jPanel2MousePressed(evt);
            }
        });
        jPanel2.setLayout(new java.awt.GridBagLayout());

        etiquetaTotales.setBackground(new java.awt.Color(0, 0, 0));
        etiquetaTotales.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        etiquetaTotales.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        etiquetaTotales.setText("<html>COLIFORMES TOTALES</html>");
        etiquetaTotales.setToolTipText("");
        etiquetaTotales.setFocusable(false);
        etiquetaTotales.setMaximumSize(new java.awt.Dimension(165, 58));
        etiquetaTotales.setMinimumSize(new java.awt.Dimension(165, 58));
        etiquetaTotales.setPreferredSize(new java.awt.Dimension(165, 58));
        etiquetaTotales.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                etiquetaTotalesMousePressed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipady = 20;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        jPanel2.add(etiquetaTotales, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.ipady = 50;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        jPanel1.add(jPanel2, gridBagConstraints);

        jPanel6.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 1, 1, 1, new java.awt.Color(0, 0, 0)));
        jPanel6.setFocusable(false);
        jPanel6.setPreferredSize(new java.awt.Dimension(150, 10));
        jPanel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jPanel6MousePressed(evt);
            }
        });
        jPanel6.setLayout(new java.awt.GridBagLayout());

        etiquetaStaphilococos.setBackground(new java.awt.Color(0, 0, 0));
        etiquetaStaphilococos.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        etiquetaStaphilococos.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        etiquetaStaphilococos.setText("<html>STAPHILOCOCOS AUREUS COAGULOSA (+)</html>");
        etiquetaStaphilococos.setToolTipText("");
        etiquetaStaphilococos.setFocusable(false);
        etiquetaStaphilococos.setMaximumSize(new java.awt.Dimension(165, 58));
        etiquetaStaphilococos.setMinimumSize(new java.awt.Dimension(165, 58));
        etiquetaStaphilococos.setPreferredSize(new java.awt.Dimension(165, 58));
        etiquetaStaphilococos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                etiquetaStaphilococosMousePressed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipady = 20;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        jPanel6.add(etiquetaStaphilococos, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.ipady = 50;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        jPanel1.add(jPanel6, gridBagConstraints);

        jPanel7.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 0, 1, new java.awt.Color(0, 0, 0)));
        jPanel7.setFocusable(false);
        jPanel7.setPreferredSize(new java.awt.Dimension(150, 10));
        jPanel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jPanel7MousePressed(evt);
            }
        });
        jPanel7.setLayout(new java.awt.GridBagLayout());

        etiquetaGermenes.setBackground(new java.awt.Color(0, 0, 0));
        etiquetaGermenes.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        etiquetaGermenes.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        etiquetaGermenes.setText("<html>GERMENES AEROBIOS TOTALES</html>");
        etiquetaGermenes.setToolTipText("");
        etiquetaGermenes.setFocusable(false);
        etiquetaGermenes.setMaximumSize(new java.awt.Dimension(165, 58));
        etiquetaGermenes.setMinimumSize(new java.awt.Dimension(165, 58));
        etiquetaGermenes.setPreferredSize(new java.awt.Dimension(165, 58));
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
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.ipady = 50;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        jPanel1.add(jPanel7, gridBagConstraints);

        jPanel8.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 0, 1, new java.awt.Color(0, 0, 0)));
        jPanel8.setFocusable(false);
        jPanel8.setPreferredSize(new java.awt.Dimension(150, 10));
        jPanel8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jPanel8MousePressed(evt);
            }
        });
        jPanel8.setLayout(new java.awt.GridBagLayout());

        etiquetaFecales.setBackground(new java.awt.Color(0, 0, 0));
        etiquetaFecales.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        etiquetaFecales.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        etiquetaFecales.setText("<html>COLIFORMES FECALES</html>");
        etiquetaFecales.setToolTipText("");
        etiquetaFecales.setFocusable(false);
        etiquetaFecales.setMaximumSize(new java.awt.Dimension(165, 58));
        etiquetaFecales.setMinimumSize(new java.awt.Dimension(165, 58));
        etiquetaFecales.setPreferredSize(new java.awt.Dimension(165, 58));
        etiquetaFecales.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                etiquetaFecalesMousePressed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipady = 20;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        jPanel8.add(etiquetaFecales, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.ipady = 50;
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
        area13.setText("SMEWW 22nd. Edition. APHA (2012) 9215 B");
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
        area23.setText(" ISO 4832-2006");
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
        area33.setText(" ISO 4832-2006");
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

        area43.setEditable(false);
        area43.setColumns(20);
        area43.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        area43.setLineWrap(true);
        area43.setRows(5);
        area43.setText("ISO 6888-1:1999/ Adm 1:2003");
        area43.setWrapStyleWord(true);
        area43.setBorder(null);
        area43.setFocusable(false);
        area43.setMargin(new java.awt.Insets(7, 7, 7, 7));
        jScrollPane12.setViewportView(area43);

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
        gridBagConstraints.gridy = 10;
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
        jPanel11.setPreferredSize(new java.awt.Dimension(150, 10));
        jPanel11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jPanel11MousePressed(evt);
            }
        });
        jPanel11.setLayout(new java.awt.GridBagLayout());

        etiquetaEscherichia.setBackground(new java.awt.Color(0, 0, 0));
        etiquetaEscherichia.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        etiquetaEscherichia.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        etiquetaEscherichia.setText("<html>ESCHERICHIA COLI</html>");
        etiquetaEscherichia.setToolTipText("");
        etiquetaEscherichia.setFocusable(false);
        etiquetaEscherichia.setMaximumSize(new java.awt.Dimension(165, 58));
        etiquetaEscherichia.setMinimumSize(new java.awt.Dimension(165, 58));
        etiquetaEscherichia.setPreferredSize(new java.awt.Dimension(165, 58));
        etiquetaEscherichia.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                etiquetaEscherichiaMousePressed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipady = 20;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        jPanel11.add(etiquetaEscherichia, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.ipady = 50;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        jPanel1.add(jPanel11, gridBagConstraints);

        jScrollPane14.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 1, 1, new java.awt.Color(0, 0, 0)));
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
        area45.setText("SMEWW 22nd. Edition. APHA (2012) 9221 F");
        area45.setWrapStyleWord(true);
        area45.setBorder(null);
        area45.setFocusable(false);
        area45.setMargin(new java.awt.Insets(7, 7, 7, 7));
        jScrollPane14.setViewportView(area45);

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
        jPanel4.setLayout(new java.awt.GridBagLayout());

        comboStaphilococos.setBackground(new java.awt.Color(204, 204, 204));
        comboStaphilococos.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Menor a", " " }));
        comboStaphilococos.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        comboStaphilococos.setNextFocusableComponent(cajaStaphilococos);
        comboStaphilococos.setPreferredSize(new java.awt.Dimension(50, 21));
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
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.ipady = 39;
        jPanel4.add(comboStaphilococos, gridBagConstraints);

        etiquetaStaphilococosIn.setBackground(new java.awt.Color(255, 255, 255));
        etiquetaStaphilococosIn.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        etiquetaStaphilococosIn.setText("UFC/g");
        etiquetaStaphilococosIn.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 1, new java.awt.Color(0, 0, 0)));
        etiquetaStaphilococosIn.setFocusable(false);
        etiquetaStaphilococosIn.setOpaque(true);
        etiquetaStaphilococosIn.setPreferredSize(new java.awt.Dimension(36, 16));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 39;
        gridBagConstraints.ipady = 44;
        jPanel4.add(etiquetaStaphilococosIn, gridBagConstraints);

        cajaStaphilococos.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        cajaStaphilococos.setMargin(new java.awt.Insets(20, 20, 20, 20));
        cajaStaphilococos.setMaximumSize(new java.awt.Dimension(1, 15));
        cajaStaphilococos.setMinimumSize(new java.awt.Dimension(1, 15));
        cajaStaphilococos.setName(""); // NOI18N
        cajaStaphilococos.setNextFocusableComponent(comboSalmonella);
        cajaStaphilococos.setPreferredSize(new java.awt.Dimension(1, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 36;
        gridBagConstraints.ipady = 45;
        jPanel4.add(cajaStaphilococos, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        jPanel9.add(jPanel4, gridBagConstraints);

        jPanel10.setFocusable(false);
        jPanel10.setLayout(new java.awt.GridBagLayout());

        comboSalmonella.setBackground(new java.awt.Color(204, 204, 204));
        comboSalmonella.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ausencia", "Presencia" }));
        comboSalmonella.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 1, new java.awt.Color(0, 0, 0)));
        comboSalmonella.setNextFocusableComponent(cajaGermenes);
        comboSalmonella.setPreferredSize(new java.awt.Dimension(70, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 102;
        gridBagConstraints.ipady = 40;
        jPanel10.add(comboSalmonella, gridBagConstraints);
        comboSalmonella.setUI(new BasicComboBoxUI() {
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
        jPanel9.add(jPanel10, gridBagConstraints);

        jPanel12.setFocusable(false);
        jPanel12.setLayout(new java.awt.GridBagLayout());

        comboEscherichia.setBackground(new java.awt.Color(204, 204, 204));
        comboEscherichia.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ausencia", "Presencia" }));
        comboEscherichia.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 1, new java.awt.Color(0, 0, 0)));
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

        comboFecales.setBackground(new java.awt.Color(204, 204, 204));
        comboFecales.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Menor a", " " }));
        comboFecales.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        comboFecales.setNextFocusableComponent(cajaStaphilococos);
        comboFecales.setPreferredSize(new java.awt.Dimension(50, 21));
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
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.ipady = 39;
        jPanel16.add(comboFecales, gridBagConstraints);

        etiquetaFecalesIn.setBackground(new java.awt.Color(255, 255, 255));
        etiquetaFecalesIn.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        etiquetaFecalesIn.setText("UFC/g");
        etiquetaFecalesIn.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 1, new java.awt.Color(0, 0, 0)));
        etiquetaFecalesIn.setFocusable(false);
        etiquetaFecalesIn.setOpaque(true);
        etiquetaFecalesIn.setPreferredSize(new java.awt.Dimension(36, 16));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 39;
        gridBagConstraints.ipady = 44;
        jPanel16.add(etiquetaFecalesIn, gridBagConstraints);

        cajaFecales.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        cajaFecales.setMargin(new java.awt.Insets(20, 20, 20, 20));
        cajaFecales.setMaximumSize(new java.awt.Dimension(1, 15));
        cajaFecales.setMinimumSize(new java.awt.Dimension(1, 15));
        cajaFecales.setName(""); // NOI18N
        cajaFecales.setNextFocusableComponent(comboSalmonella);
        cajaFecales.setPreferredSize(new java.awt.Dimension(1, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 36;
        gridBagConstraints.ipady = 45;
        jPanel16.add(cajaFecales, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        jPanel9.add(jPanel16, gridBagConstraints);

        jPanel17.setFocusable(false);
        jPanel17.setLayout(new java.awt.GridBagLayout());

        comboTotales.setBackground(new java.awt.Color(204, 204, 204));
        comboTotales.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Menor a", " " }));
        comboTotales.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        comboTotales.setNextFocusableComponent(cajaStaphilococos);
        comboTotales.setPreferredSize(new java.awt.Dimension(50, 21));
        comboTotales.setUI(new BasicComboBoxUI() {
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
        jPanel17.add(comboTotales, gridBagConstraints);

        etiquetaTotalesIn.setBackground(new java.awt.Color(255, 255, 255));
        etiquetaTotalesIn.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        etiquetaTotalesIn.setText("UFC/g");
        etiquetaTotalesIn.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 1, new java.awt.Color(0, 0, 0)));
        etiquetaTotalesIn.setFocusable(false);
        etiquetaTotalesIn.setOpaque(true);
        etiquetaTotalesIn.setPreferredSize(new java.awt.Dimension(36, 16));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 39;
        gridBagConstraints.ipady = 44;
        jPanel17.add(etiquetaTotalesIn, gridBagConstraints);

        cajaTotales.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        cajaTotales.setMargin(new java.awt.Insets(20, 20, 20, 20));
        cajaTotales.setMaximumSize(new java.awt.Dimension(1, 15));
        cajaTotales.setMinimumSize(new java.awt.Dimension(1, 15));
        cajaTotales.setName(""); // NOI18N
        cajaTotales.setNextFocusableComponent(comboSalmonella);
        cajaTotales.setPreferredSize(new java.awt.Dimension(1, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 36;
        gridBagConstraints.ipady = 45;
        jPanel17.add(cajaTotales, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        jPanel9.add(jPanel17, gridBagConstraints);

        jPanel18.setBackground(new java.awt.Color(255, 255, 255));
        jPanel18.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 1, new java.awt.Color(0, 0, 0)));
        jPanel18.setForeground(new java.awt.Color(255, 255, 255));
        jPanel18.setPreferredSize(new java.awt.Dimension(172, 60));
        jPanel18.setLayout(new java.awt.GridBagLayout());

        cajaMoho.setBackground(new java.awt.Color(255, 255, 255));
        cajaMoho.setBorder(null);
        cajaMoho.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cajaMohoActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 60;
        gridBagConstraints.ipady = 40;
        jPanel18.add(cajaMoho, gridBagConstraints);

        etiquetaUnidadMohos.setText("UFC/g");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        jPanel18.add(etiquetaUnidadMohos, gridBagConstraints);

        etiquetaMenorMohos.setText("MENOR A 10 UFC/g");
        jPanel18.add(etiquetaMenorMohos, new java.awt.GridBagConstraints());
        etiquetaMenorMohos.setVisible(false);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        jPanel9.add(jPanel18, gridBagConstraints);

        jPanel19.setBackground(new java.awt.Color(255, 255, 255));
        jPanel19.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 1, 1, new java.awt.Color(0, 0, 0)));
        jPanel19.setForeground(new java.awt.Color(255, 255, 255));
        jPanel19.setPreferredSize(new java.awt.Dimension(172, 60));
        jPanel19.setLayout(new java.awt.GridBagLayout());

        cajaGermenes.setBackground(new java.awt.Color(255, 255, 255));
        cajaGermenes.setBorder(null);
        cajaGermenes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cajaGermenesActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 60;
        gridBagConstraints.ipady = 40;
        jPanel19.add(cajaGermenes, gridBagConstraints);

        etiquetaUnidadGermenes.setText("UFC/g");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        jPanel19.add(etiquetaUnidadGermenes, gridBagConstraints);

        etiquetaMenorGermenes.setText("MENOR A 10 UFC/g");
        jPanel19.add(etiquetaMenorGermenes, new java.awt.GridBagConstraints());
        etiquetaMenorGermenes.setVisible(false);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        jPanel9.add(jPanel19, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridheight = 8;
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

        checkTotales.setText("MENOR A 10");
        checkTotales.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkTotalesActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        jPanel1.add(checkTotales, gridBagConstraints);

        jPanel14.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 1, 1, 1, new java.awt.Color(0, 0, 0)));
        jPanel14.setFocusable(false);
        jPanel14.setPreferredSize(new java.awt.Dimension(150, 10));
        jPanel14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jPanel14MousePressed(evt);
            }
        });
        jPanel14.setLayout(new java.awt.GridBagLayout());

        etiquetaMohos.setBackground(new java.awt.Color(0, 0, 0));
        etiquetaMohos.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        etiquetaMohos.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        etiquetaMohos.setText("<html>MOHOS Y LEVADURAS</html>");
        etiquetaMohos.setToolTipText("");
        etiquetaMohos.setFocusable(false);
        etiquetaMohos.setMaximumSize(new java.awt.Dimension(165, 58));
        etiquetaMohos.setMinimumSize(new java.awt.Dimension(165, 58));
        etiquetaMohos.setName(""); // NOI18N
        etiquetaMohos.setPreferredSize(new java.awt.Dimension(165, 58));
        etiquetaMohos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                etiquetaMohosMousePressed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipady = 20;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        jPanel14.add(etiquetaMohos, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.ipady = 50;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        jPanel1.add(jPanel14, gridBagConstraints);

        jScrollPane13.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 1, new java.awt.Color(0, 0, 0)));
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
        area44.setText("ISO 21527-2:2008");
        area44.setWrapStyleWord(true);
        area44.setBorder(null);
        area44.setFocusable(false);
        area44.setMargin(new java.awt.Insets(7, 7, 7, 7));
        jScrollPane13.setViewportView(area44);

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

        checkMohos.setText("MENOR A 10");
        checkMohos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkMohosActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        jPanel1.add(checkMohos, gridBagConstraints);

        jScrollPane15.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 1, new java.awt.Color(0, 0, 0)));
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
        area46.setText("ISO 6579:2002 / Cor. 1:2004 / Amd. 1:2007");
        area46.setWrapStyleWord(true);
        area46.setBorder(null);
        area46.setFocusable(false);
        area46.setMargin(new java.awt.Insets(7, 7, 7, 7));
        jScrollPane15.setViewportView(area46);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 20);
        jPanel1.add(jScrollPane15, gridBagConstraints);

        jPanel15.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 1, 1, 1, new java.awt.Color(0, 0, 0)));
        jPanel15.setFocusable(false);
        jPanel15.setPreferredSize(new java.awt.Dimension(150, 10));
        jPanel15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jPanel15MousePressed(evt);
            }
        });
        jPanel15.setLayout(new java.awt.GridBagLayout());

        etiquetaSalmonella.setBackground(new java.awt.Color(0, 0, 0));
        etiquetaSalmonella.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        etiquetaSalmonella.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        etiquetaSalmonella.setText("<html>SALMONELLA sp</html>");
        etiquetaSalmonella.setToolTipText("");
        etiquetaSalmonella.setFocusable(false);
        etiquetaSalmonella.setMaximumSize(new java.awt.Dimension(165, 58));
        etiquetaSalmonella.setMinimumSize(new java.awt.Dimension(165, 58));
        etiquetaSalmonella.setPreferredSize(new java.awt.Dimension(165, 58));
        etiquetaSalmonella.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                etiquetaSalmonellaMousePressed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipady = 20;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        jPanel15.add(etiquetaSalmonella, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.ipady = 50;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        jPanel1.add(jPanel15, gridBagConstraints);

        checkFecales.setText("MENOR A 1");
        checkFecales.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkFecalesActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        jPanel1.add(checkFecales, gridBagConstraints);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 935, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 635, Short.MAX_VALUE)
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

    private void botonGenerarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonGenerarActionPerformed
        Map m = new HashMap();
        String staphilococos = comboStaphilococos.getSelectedItem().toString().toUpperCase() + ' ' + cajaStaphilococos.getText() + ' ' + etiquetaStaphilococosIn.getText();
        String fecales = comboFecales.getSelectedItem().toString().toUpperCase() + ' ' + cajaFecales.getText() + ' ' + etiquetaFecalesIn.getText();
        String totales = comboTotales.getSelectedItem().toString().toUpperCase() + ' ' + cajaTotales.getText() + ' ' + etiquetaTotalesIn.getText();
        if (cajaGermenes.isEnabled()) {
            if (checkGermenes.isSelected()) {
                m.put("germenes", "MENOR A 10 UFC/g");
            } else {
                m.put("germenes", cajaGermenes.getText() + " " + etiquetaUnidadGermenes.getText());
            }
        } else {
            m.put("germenes", "-2");
        }
        if (cajaTotales.isEnabled()) {
            if (checkTotales.isSelected()) {
                m.put("coliformesTotales", "MENOR A 10 UFC/g");
            } else {
                m.put("coliformesTotales", totales);
            }
        } else {
            m.put("coliformesTotales", "-2");
        }
        if (cajaFecales.isEnabled()) {
            if (checkFecales.isSelected()) {
                m.put("coliformesFecales", "MENOR A 1 UFC/g");
            } else {
                m.put("coliformesFecales", fecales);
            }
        } else {
            m.put("coliformesFecales", "-2");
        }
        if (cajaMoho.isEnabled()) {
            if (checkMohos.isSelected()) {
                m.put("mohos", "MENOR A 10 UFC/g");
            } else {
                m.put("mohos", cajaMoho.getText() + " " + etiquetaUnidadMohos.getText());
            }
        } else {
            m.put("mohos", "-2");
        }
        m.put("idmuestras", id);
        if (comboEscherichia.isEnabled()) {
            m.put("escherichia", comboEscherichia.getSelectedItem().toString());
        } else {
            m.put("escherichia", "-2");
        }
        if (cajaStaphilococos.isEnabled()) {
            m.put("staphilococos", staphilococos);
        } else {
            m.put("staphilococos", "-2");
        }
        if (comboSalmonella.isEnabled()) {
            m.put("salmonella", comboSalmonella.getSelectedItem().toString());
        } else {
            m.put("salmonella", "-2");
        }
        m.put("procedencia", procedencia);
        c.guardarConclusion(crearConclusion(), id);
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
            c.guardarFechaAnalisis(m);
            if (editar) {
                File rv = new File(c.recuperarRutas("Reportes") + "\\" + pdf);
                File rn = new File(c.recuperarRutas("Reportes") + "\\(BORRADO) " + pdf);
                rv.renameTo(rn);
                if (c.editarResultadoBaseHelada(m)) {
                    c.guardarObservaciones(observaciones, id);
                    this.dispose();
                    c.generarReporteMBBaseHelada(id, procedencia);
                }
            } else {
                if (c.guardarResultadoBaseHelada(m)) {
                    c.guardarObservaciones(observaciones, id);
                    this.dispose();
                    c.generarReporteMBBaseHelada(id, procedencia);

                }
            }
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(null, "Ingrese la fecha de análisis.");
        }
    }//GEN-LAST:event_botonGenerarActionPerformed

    private void checkGermenesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkGermenesActionPerformed
        if (checkGermenes.isSelected()) {
            etiquetaMenorGermenes.setVisible(true);
            cajaGermenes.setVisible(false);
            etiquetaUnidadGermenes.setVisible(false);
        } else {
            etiquetaUnidadGermenes.setVisible(true);
            etiquetaMenorGermenes.setVisible(false);
            cajaGermenes.setVisible(true);
        }
    }//GEN-LAST:event_checkGermenesActionPerformed

    private void checkTotalesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkTotalesActionPerformed
        if (checkTotales.isSelected()) {
            comboTotales.setSelectedItem(0);
            cajaTotales.setText("10");
        }
    }//GEN-LAST:event_checkTotalesActionPerformed

    private void checkStaphilococosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkStaphilococosActionPerformed
        if (checkStaphilococos.isSelected()) {
            comboStaphilococos.setSelectedIndex(0);
            cajaStaphilococos.setText("10");
        }
    }//GEN-LAST:event_checkStaphilococosActionPerformed

    private void checkMohosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkMohosActionPerformed
        if (checkMohos.isSelected()) {
            cajaMoho.setVisible(false);
            etiquetaMenorMohos.setVisible(true);
            etiquetaUnidadMohos.setVisible(false);
        } else {
            cajaMoho.setVisible(true);
            etiquetaMenorMohos.setVisible(false);
            etiquetaUnidadMohos.setVisible(true);
        }
    }//GEN-LAST:event_checkMohosActionPerformed

    private void cajaMohoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cajaMohoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cajaMohoActionPerformed

    private void cajaGermenesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cajaGermenesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cajaGermenesActionPerformed

    private void checkFecalesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkFecalesActionPerformed
        if (checkFecales.isSelected()) {
            comboFecales.setSelectedItem(0);
            cajaFecales.setText("1");
        }
    }//GEN-LAST:event_checkFecalesActionPerformed

    private void etiquetaGermenesMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_etiquetaGermenesMousePressed
        activarGermenes = !activarGermenes;
        cajaGermenes.setEnabled(activarGermenes);
        etiquetaUnidadGermenes.setEnabled(activarGermenes);
        etiquetaMenorGermenes.setEnabled(activarGermenes);
        area13.setEnabled(activarGermenes);
        if (activarGermenes) {
            jPanel7.setBackground(new Color(240, 240, 240));
        } else {
            jPanel7.setBackground(new Color(240, 100, 100));
        }
    }//GEN-LAST:event_etiquetaGermenesMousePressed

    private void jPanel7MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel7MousePressed
        activarGermenes = !activarGermenes;
        cajaGermenes.setEnabled(activarGermenes);
        etiquetaUnidadGermenes.setEnabled(activarGermenes);
        etiquetaMenorGermenes.setEnabled(activarGermenes);
        area13.setEnabled(activarGermenes);
        if (activarGermenes) {
            jPanel7.setBackground(new Color(240, 240, 240));
        } else {
            jPanel7.setBackground(new Color(240, 100, 100));
        }
    }//GEN-LAST:event_jPanel7MousePressed

    private void etiquetaFecalesMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_etiquetaFecalesMousePressed
        activarFecales = !activarFecales;
        cajaFecales.setEnabled(activarFecales);
        etiquetaFecalesIn.setEnabled(activarFecales);
        comboFecales.setEnabled(activarFecales);
        area33.setEnabled(activarFecales);
        if (activarFecales) {
            jPanel8.setBackground(new Color(240, 240, 240));
        } else {
            jPanel8.setBackground(new Color(240, 100, 100));
        }
    }//GEN-LAST:event_etiquetaFecalesMousePressed

    private void jPanel8MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel8MousePressed
        activarFecales = !activarFecales;
        cajaFecales.setEnabled(activarFecales);
        etiquetaFecalesIn.setEnabled(activarFecales);
        comboFecales.setEnabled(activarFecales);
        area33.setEnabled(activarFecales);
        if (activarFecales) {
            jPanel8.setBackground(new Color(240, 240, 240));
        } else {
            jPanel8.setBackground(new Color(240, 100, 100));
        }
    }//GEN-LAST:event_jPanel8MousePressed

    private void etiquetaTotalesMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_etiquetaTotalesMousePressed
        activarTotales = !activarTotales;
        cajaTotales.setEnabled(activarTotales);
        etiquetaTotalesIn.setEnabled(activarTotales);
        comboTotales.setEnabled(activarTotales);
        area23.setEnabled(activarTotales);
        if (activarTotales) {
            jPanel2.setBackground(new Color(240, 240, 240));
        } else {
            jPanel2.setBackground(new Color(240, 100, 100));
        }
    }//GEN-LAST:event_etiquetaTotalesMousePressed

    private void jPanel2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel2MousePressed
        activarTotales = !activarTotales;
        cajaTotales.setEnabled(activarTotales);
        etiquetaTotalesIn.setEnabled(activarTotales);
        comboTotales.setEnabled(activarTotales);
        area23.setEnabled(activarTotales);
        if (activarTotales) {
            jPanel2.setBackground(new Color(240, 240, 240));
        } else {
            jPanel2.setBackground(new Color(240, 100, 100));
        }
    }//GEN-LAST:event_jPanel2MousePressed

    private void etiquetaEscherichiaMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_etiquetaEscherichiaMousePressed
        activarEscherichia = !activarEscherichia;
        comboEscherichia.setEnabled(activarEscherichia);
        area45.setEnabled(activarEscherichia);
        if (activarEscherichia) {
            jPanel11.setBackground(new Color(240, 240, 240));
        } else {
            jPanel11.setBackground(new Color(240, 100, 100));
        }
    }//GEN-LAST:event_etiquetaEscherichiaMousePressed

    private void jPanel11MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel11MousePressed
        activarEscherichia = !activarEscherichia;
        comboEscherichia.setEnabled(activarEscherichia);
        area45.setEnabled(activarEscherichia);
        if (activarEscherichia) {
            jPanel11.setBackground(new Color(240, 240, 240));
        } else {
            jPanel11.setBackground(new Color(240, 100, 100));
        }
    }//GEN-LAST:event_jPanel11MousePressed

    private void etiquetaMohosMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_etiquetaMohosMousePressed
        activarMohos = !activarMohos;
        cajaMoho.setEnabled(activarMohos);
        etiquetaUnidadMohos.setEnabled(activarMohos);
        etiquetaMenorMohos.setEnabled(activarMohos);
        area44.setEnabled(activarMohos);
        if (activarMohos) {
            jPanel14.setBackground(new Color(240, 240, 240));
        } else {
            jPanel14.setBackground(new Color(240, 100, 100));
        }
    }//GEN-LAST:event_etiquetaMohosMousePressed

    private void jPanel14MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel14MousePressed
        activarMohos = !activarMohos;
        cajaMoho.setEnabled(activarMohos);
        etiquetaUnidadMohos.setEnabled(activarMohos);
        etiquetaMenorMohos.setEnabled(activarMohos);
        area44.setEnabled(activarMohos);
        if (activarMohos) {
            jPanel14.setBackground(new Color(240, 240, 240));
        } else {
            jPanel14.setBackground(new Color(240, 100, 100));
        }
    }//GEN-LAST:event_jPanel14MousePressed

    private void etiquetaStaphilococosMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_etiquetaStaphilococosMousePressed
        activarStaphilococos = !activarStaphilococos;
        cajaStaphilococos.setEnabled(activarStaphilococos);
        etiquetaStaphilococosIn.setEnabled(activarStaphilococos);
        comboStaphilococos.setEnabled(activarStaphilococos);
        area43.setEnabled(activarStaphilococos);
        if (activarStaphilococos) {
            jPanel6.setBackground(new Color(240, 240, 240));
        } else {
            jPanel6.setBackground(new Color(240, 100, 100));
        }
    }//GEN-LAST:event_etiquetaStaphilococosMousePressed

    private void jPanel6MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel6MousePressed
        activarStaphilococos = !activarStaphilococos;
        cajaStaphilococos.setEnabled(activarStaphilococos);
        etiquetaStaphilococosIn.setEnabled(activarStaphilococos);
        comboStaphilococos.setEnabled(activarStaphilococos);
        area43.setEnabled(activarStaphilococos);
        if (activarStaphilococos) {
            jPanel6.setBackground(new Color(240, 240, 240));
        } else {
            jPanel6.setBackground(new Color(240, 100, 100));
        }
    }//GEN-LAST:event_jPanel6MousePressed

    private void etiquetaSalmonellaMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_etiquetaSalmonellaMousePressed
        activarSalmonella = !activarSalmonella;
        comboSalmonella.setEnabled(activarSalmonella);
        area46.setEnabled(activarSalmonella);
        if (activarSalmonella) {
            jPanel15.setBackground(new Color(240, 240, 240));
        } else {
            jPanel15.setBackground(new Color(240, 100, 100));
        }
    }//GEN-LAST:event_etiquetaSalmonellaMousePressed

    private void jPanel15MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel15MousePressed
        activarSalmonella = !activarSalmonella;
        comboSalmonella.setEnabled(activarSalmonella);
        area46.setEnabled(activarSalmonella);
        if (activarSalmonella) {
            jPanel15.setBackground(new Color(240, 240, 240));
        } else {
            jPanel15.setBackground(new Color(240, 100, 100));
        }
    }//GEN-LAST:event_jPanel15MousePressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TablaBaseHelada.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TablaBaseHelada.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TablaBaseHelada.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TablaBaseHelada.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                TablaBaseHelada dialog = new TablaBaseHelada(new javax.swing.JFrame(), true, -1, null, false, null);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea area13;
    private javax.swing.JTextArea area23;
    private javax.swing.JTextArea area33;
    private javax.swing.JTextArea area43;
    private javax.swing.JTextArea area44;
    private javax.swing.JTextArea area45;
    private javax.swing.JTextArea area46;
    private javax.swing.JButton botonGenerar;
    private javax.swing.JTextField cajaFecales;
    private com.toedter.calendar.JDateChooser cajaFechaAnalisis;
    private javax.swing.JTextField cajaGermenes;
    private javax.swing.JTextField cajaMoho;
    private javax.swing.JTextField cajaStaphilococos;
    private javax.swing.JTextField cajaTotales;
    private javax.swing.JCheckBox checkFecales;
    private javax.swing.JCheckBox checkGermenes;
    private javax.swing.JCheckBox checkMohos;
    private javax.swing.JCheckBox checkStaphilococos;
    private javax.swing.JCheckBox checkTotales;
    private javax.swing.JComboBox<String> comboEscherichia;
    private javax.swing.JComboBox<String> comboFecales;
    private javax.swing.JComboBox<String> comboSalmonella;
    private javax.swing.JComboBox<String> comboStaphilococos;
    private javax.swing.JComboBox<String> comboTotales;
    private javax.swing.JLabel etiquetaEscherichia;
    private javax.swing.JLabel etiquetaFecales;
    private javax.swing.JLabel etiquetaFecalesIn;
    private javax.swing.JLabel etiquetaGermenes;
    private javax.swing.JLabel etiquetaMenorGermenes;
    private javax.swing.JLabel etiquetaMenorMohos;
    private javax.swing.JLabel etiquetaMohos;
    private javax.swing.JLabel etiquetaSalmonella;
    private javax.swing.JLabel etiquetaStaphilococos;
    private javax.swing.JLabel etiquetaStaphilococosIn;
    private javax.swing.JLabel etiquetaTitulo;
    private javax.swing.JLabel etiquetaTotales;
    private javax.swing.JLabel etiquetaTotalesIn;
    private javax.swing.JLabel etiquetaUnidadGermenes;
    private javax.swing.JLabel etiquetaUnidadMohos;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
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
    private javax.swing.JScrollPane jScrollPane9;
    // End of variables declaration//GEN-END:variables

    private String crearConclusion() {
        boolean germenes = true;
        if (cajaGermenes.isEnabled()) {
            if (checkGermenes.isSelected()) {
                germenes = true;
            } else {
                double valorGermenesAnalisis = Double.parseDouble(cajaGermenes.getText());
                double valorGermenesNormal = 1 * Math.pow(10, 5);
                germenes = valorGermenesAnalisis < valorGermenesNormal;
            }
        }

        boolean coliformesTotales = true;
        if (cajaTotales.isEnabled()) {
            if (checkTotales.isSelected()) {
                coliformesTotales = true;
            } else {
                double valorColiformesTotalesAnalisis = Integer.parseInt(cajaTotales.getText());
                double valorColiformesTotalesNormal = 1 * Math.pow(10, 2);
                coliformesTotales = valorColiformesTotalesAnalisis < valorColiformesTotalesNormal;
                if (valorColiformesTotalesAnalisis == valorColiformesTotalesNormal || comboTotales.getSelectedItem().equals("Menor a")) {
                    coliformesTotales = true;
                }
            }
        }

        boolean coliformesFecales = true;
        if (cajaFecales.isEnabled()) {
            if (checkFecales.isSelected()) {
                coliformesFecales = true;
            } else {
                double valorColiformesFecalesAnalisis = Double.parseDouble(cajaFecales.getText());
                double valorColiformesFecalesNormal = 1;
                coliformesFecales = valorColiformesFecalesAnalisis < valorColiformesFecalesNormal;
                if (valorColiformesFecalesAnalisis == valorColiformesFecalesNormal || comboFecales.getSelectedItem().equals("Menor a")) {
                    coliformesFecales = true;
                }
            }
        }

        boolean escherichia = true;
        if (comboEscherichia.isEnabled()) {
            escherichia = comboEscherichia.getSelectedItem().toString().equals("Ausencia");
        }

        boolean mohos = true;
        if (cajaMoho.isEnabled()) {
            if (checkMohos.isSelected()) {
                mohos = true;
            } else {
                double valorMohosAnalisis = Double.parseDouble(cajaMoho.getText());
                double valorMohosNormales = 100;
                mohos = valorMohosAnalisis < valorMohosNormales;

            }
        }
        boolean staphilococos = true;
        if (cajaStaphilococos.isEnabled()) {
            if (checkStaphilococos.isSelected()) {
                staphilococos = true;
            } else {
                double valorStaphilococosAnalisis = Double.parseDouble(cajaStaphilococos.getText());
                double valorStaphilococosNormales = 1 * Math.pow(10, 2);;
                staphilococos = valorStaphilococosAnalisis < valorStaphilococosNormales;
                if (valorStaphilococosAnalisis == valorStaphilococosNormales || comboStaphilococos.getSelectedItem().equals("Menor a")) {
                    staphilococos = true;
                }
            }
        }
        boolean salmonella = true;
        if (comboSalmonella.isEnabled()) {
            salmonella = comboSalmonella.getSelectedItem().equals("Ausencia");
        }
        String conclusion = "";
        if (germenes && coliformesTotales && coliformesFecales && escherichia && mohos && staphilococos && salmonella) {
            conclusion = "En las determinaciones realizadas, la muestra analizada cumple con el art. 1078 del Código Alimentario Argentino (ley 18.284).";
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
                conclusion += " Eschericihia coli,";
            }

            if (!mohos) {
                conclusion += " Mohos y Levaduras";
            }

            if (!staphilococos) {
                conclusion += " Staphilococos aureus coagulasa (+),";
            }

            if (!salmonella) {
                conclusion += " Salmonella sp,";
            }

            conclusion = conclusion.substring(0, conclusion.length() - 1);
            conclusion += " la muestra analizada no cumple con el art. 1078 del Código Alimentario Argentino (ley 18.284)";
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
