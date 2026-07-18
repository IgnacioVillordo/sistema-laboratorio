package org.ignaciorodriguez.vista;

import java.awt.Color;
import java.awt.Component;
import java.awt.Point;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.border.MatteBorder;
import javax.swing.plaf.basic.BasicComboBoxUI;
import org.ignaciorodriguez.modelo.Consultas;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.Map;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.KeyStroke;
import javax.swing.JComponent;

public class TablaEfluentes extends javax.swing.JDialog {

    int id;
    String procedencia, pdf, lugarMuestreo, auxObservaciones, auxConclusion;
    Consultas c = Consultas.getInstancia();
    boolean editar, activarPh = true, activarDQO = true, activarDBO = true,
            activarSolidos10 = true, activarSolidos120 = true, activarHidrocarburos = true;

    public TablaEfluentes(java.awt.Frame parent, boolean modal, int id, String procedencia,
                          boolean editar, String pdf, String lugarMuestreo) {
        super(parent, modal);
        this.procedencia = procedencia;
        this.id = id;
        this.lugarMuestreo = lugarMuestreo;
        this.editar = editar;
        this.pdf = pdf;
        initComponents();
        setTitle("ID " + id + ". " + c.obtenerProcedencia(id) + ". Efluentes");
        if (editar == true) {
            Map<String, String> resultados = c.recuperarResultadosEfluentes(id);
            Locale currentLocale = Locale.getDefault();
            DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(currentLocale);
            otherSymbols.setDecimalSeparator('.');
            otherSymbols.setGroupingSeparator(',');
            DecimalFormat df = new DecimalFormat("#.##", otherSymbols);
            if (resultados == null) {
                this.editar = false;
            } else {
                auxConclusion = c.recuperarConclusion(id);
                if (auxConclusion == null) {

                } else {
                    checkConclusion.setSelected(!auxConclusion.isBlank());
                }

                if (resultados.get("ph").contains("-2")) {
                    etiquetaPhMousePressed(click(etiquetaPh));
                } else if (resultados.get("ph").contains("-1")) {
                } else {
                    cajaPh.setText(df.format(Double.parseDouble(resultados.get("ph"))));
                }
                
                if (resultados.get("dqo").contains("-2")) {
                    etiquetaDQOMousePressed(click(etiquetaDQO));
                } else if (resultados.get("dqo").contains("-1")) {
                } else {
                    cajaDQO.setText(df.format(Double.parseDouble(resultados.get("dqo"))));
                }
                
                if (resultados.get("dbo").contains("-2")) {
                    etiquetaDBOMousePressed(click(etiquetaDBO));
                } else if (resultados.get("dbo").contains("-1")) {
                } else {
                    cajaDBO.setText(df.format(Double.parseDouble(resultados.get("dbo"))));
                }
                if (resultados.get("solidos10").contains("-2")) {
                    etiquetaSolidos10MousePressed(click(etiquetaSolidos10));
                } else if (resultados.get("solidos10").contains("-1")) {
                } else {
                    if (resultados.get("solidos10").startsWith("Menor a")) {
                        comboSolidos20.setSelectedItem("Menor a");
                    } else if (resultados.get("solidos10").startsWith("Mayor a")) {
                        comboSolidos20.setSelectedItem("Mayor a");
                    } else {
                        comboSolidos20.setSelectedIndex(comboSolidos20.getItemCount() - 1);
                    }
                    String aux = resultados.get("solidos10").toString().replaceAll("[^0-9?!\\.]", "");
                    cajaSolidos20.setText(df.format(Double.parseDouble(aux)));
                }

                if (resultados.get("solidos120").contains("-2")) {
                    etiquetaSolidos120MousePressed(click(etiquetaSolidos10));
                } else if (resultados.get("solidos120").contains("-1")) {
                } else {
                    if (resultados.get("solidos120").startsWith("Menor a")) {
                        comboSolidos120.setSelectedItem("Menor a");
                    } else if (resultados.get("solidos120").startsWith("Mayor a")) {
                        comboSolidos120.setSelectedItem("Mayor a");
                    } else {
                        comboSolidos120.setSelectedIndex(comboSolidos120.getItemCount() - 1);
                    }
                    String aux = resultados.get("solidos120").toString().replaceAll("[^0-9?!\\.]", "");
                    cajaSolidos120.setText(df.format(Double.parseDouble(aux)));
                }
                if (resultados.get("hidrocarburos").contains("-2")) {
                    etiquetaHidrocarburosMousePressed(click(etiquetaHidrocarburos));
                } else {
                    if (resultados.get("hidrocarburos").startsWith("Menor a")) {
                        comboHidrocarburos.setSelectedItem("Menor a");
                    } else if (resultados.get("hidrocarburos").startsWith("Mayor a")) {
                        comboHidrocarburos.setSelectedItem("Mayor a");
                    } else {
                        comboHidrocarburos.setSelectedIndex(comboHidrocarburos.getItemCount() - 1);
                    }
                    String aux = resultados.get("hidrocarburos").toString().replaceAll("[^0-9?!\\.]", "");
                    cajaHidrocarburos.setText(df.format(Double.parseDouble(aux)));
                }
            }

        }
        ImageIcon icon = new ImageIcon("src\\vista\\icono.png");
        this.setIconImage(icon.getImage());
        this.setLocationRelativeTo(null);
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
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        panelDQO = new javax.swing.JPanel();
        etiquetaDQO = new javax.swing.JLabel();
        panelSolidos120 = new javax.swing.JPanel();
        etiquetaSolidos120 = new javax.swing.JLabel();
        panelPH = new javax.swing.JPanel();
        etiquetaPh = new javax.swing.JLabel();
        panelDBO = new javax.swing.JPanel();
        etiquetaDBO = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        area12 = new javax.swing.JTextArea();
        jScrollPane6 = new javax.swing.JScrollPane();
        area22 = new javax.swing.JTextArea();
        jScrollPane7 = new javax.swing.JScrollPane();
        area32 = new javax.swing.JTextArea();
        jScrollPane8 = new javax.swing.JScrollPane();
        area52 = new javax.swing.JTextArea();
        jScrollPane9 = new javax.swing.JScrollPane();
        area13 = new javax.swing.JTextArea();
        jScrollPane10 = new javax.swing.JScrollPane();
        area23 = new javax.swing.JTextArea();
        jScrollPane11 = new javax.swing.JScrollPane();
        area33 = new javax.swing.JTextArea();
        jScrollPane12 = new javax.swing.JScrollPane();
        area53 = new javax.swing.JTextArea();
        botonGenerar = new javax.swing.JButton();
        panelSolidos10In = new javax.swing.JPanel();
        comboSolidos20 = new javax.swing.JComboBox<>();
        etiquetaSolidos10In = new javax.swing.JLabel();
        cajaSolidos20 = new javax.swing.JTextField();
        etiquetaTitulo = new javax.swing.JLabel();
        panelSolidos10 = new javax.swing.JPanel();
        etiquetaSolidos10 = new javax.swing.JLabel();
        jScrollPane13 = new javax.swing.JScrollPane();
        area42 = new javax.swing.JTextArea();
        jScrollPane14 = new javax.swing.JScrollPane();
        area43 = new javax.swing.JTextArea();
        panelPhIn = new javax.swing.JPanel();
        cajaPh = new javax.swing.JTextField();
        panelSolidos120In = new javax.swing.JPanel();
        comboSolidos120 = new javax.swing.JComboBox<>();
        etiquetaSolidos120In = new javax.swing.JLabel();
        cajaSolidos120 = new javax.swing.JTextField();
        panelDQOIn = new javax.swing.JPanel();
        cajaDQO = new javax.swing.JTextField();
        etiquetaDQOIn = new javax.swing.JLabel();
        panelDBOIn = new javax.swing.JPanel();
        cajaDBO = new javax.swing.JTextField();
        etiquetaDBOIn = new javax.swing.JLabel();
        checkConclusion = new javax.swing.JCheckBox();
        jScrollPane15 = new javax.swing.JScrollPane();
        area62 = new javax.swing.JTextArea();
        panelHidrocarburos = new javax.swing.JPanel();
        etiquetaHidrocarburos = new javax.swing.JLabel();
        jScrollPane16 = new javax.swing.JScrollPane();
        area63 = new javax.swing.JTextArea();
        panelHidrocarburosIn = new javax.swing.JPanel();
        comboHidrocarburos = new javax.swing.JComboBox<>();
        etiquetaHidrocarburosIn = new javax.swing.JLabel();
        cajaHidrocarburos = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

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
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.ipady = 40;
        gridBagConstraints.insets = new java.awt.Insets(20, 20, 0, 0);
        jPanel1.add(jLabel2, gridBagConstraints);
        jLabel2.setBorder(new MatteBorder(1,1,0,1, Color.black));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("<html>VALOR NORMAL</html>");
        jLabel3.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        jLabel3.setFocusable(false);
        jLabel3.setMinimumSize(new java.awt.Dimension(150, 30));
        jLabel3.setOpaque(true);
        jLabel3.setPreferredSize(new java.awt.Dimension(150, 10));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.ipady = 40;
        gridBagConstraints.insets = new java.awt.Insets(20, 0, 0, 0);
        jPanel1.add(jLabel3, gridBagConstraints);

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("<html>VALOR OBTENIDO</html>");
        jLabel4.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        jLabel4.setFocusable(false);
        jLabel4.setMinimumSize(new java.awt.Dimension(150, 30));
        jLabel4.setOpaque(true);
        jLabel4.setPreferredSize(new java.awt.Dimension(150, 10));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 22;
        gridBagConstraints.ipady = 40;
        gridBagConstraints.insets = new java.awt.Insets(20, 0, 0, 0);
        jPanel1.add(jLabel4, gridBagConstraints);

        panelDQO.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 0, 1, new java.awt.Color(0, 0, 0)));
        panelDQO.setFocusable(false);
        panelDQO.setPreferredSize(new java.awt.Dimension(150, 10));
        panelDQO.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                panelDQOMousePressed(evt);
            }
        });
        panelDQO.setLayout(new java.awt.GridBagLayout());

        etiquetaDQO.setBackground(new java.awt.Color(0, 0, 0));
        etiquetaDQO.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        etiquetaDQO.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        etiquetaDQO.setText("<html>DEMANDA QUÍMICA DE OXÍGENO (DQO)</html>");
        etiquetaDQO.setToolTipText("");
        etiquetaDQO.setMaximumSize(new java.awt.Dimension(165, 60));
        etiquetaDQO.setMinimumSize(new java.awt.Dimension(165, 60));
        etiquetaDQO.setPreferredSize(new java.awt.Dimension(165, 60));
        etiquetaDQO.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                etiquetaDQOMousePressed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        panelDQO.add(etiquetaDQO, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.ipady = 50;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        jPanel1.add(panelDQO, gridBagConstraints);

        panelSolidos120.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 1, 1, 1, new java.awt.Color(0, 0, 0)));
        panelSolidos120.setFocusable(false);
        panelSolidos120.setPreferredSize(new java.awt.Dimension(150, 10));
        panelSolidos120.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                panelSolidos120MousePressed(evt);
            }
        });
        panelSolidos120.setLayout(new java.awt.GridBagLayout());

        etiquetaSolidos120.setBackground(new java.awt.Color(0, 0, 0));
        etiquetaSolidos120.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        etiquetaSolidos120.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        etiquetaSolidos120.setText("<html>SÓLIDOS DISUELTOS SEDIMENTABLES EN 120’</html>");
        etiquetaSolidos120.setToolTipText("");
        etiquetaSolidos120.setFocusable(false);
        etiquetaSolidos120.setMaximumSize(new java.awt.Dimension(165, 60));
        etiquetaSolidos120.setMinimumSize(new java.awt.Dimension(165, 60));
        etiquetaSolidos120.setPreferredSize(new java.awt.Dimension(165, 60));
        etiquetaSolidos120.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                etiquetaSolidos120MousePressed(evt);
            }
        });
        panelSolidos120.add(etiquetaSolidos120, new java.awt.GridBagConstraints());

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.ipady = 50;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        jPanel1.add(panelSolidos120, gridBagConstraints);

        panelPH.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 0, 1, new java.awt.Color(0, 0, 0)));
        panelPH.setFocusable(false);
        panelPH.setPreferredSize(new java.awt.Dimension(150, 10));
        panelPH.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                panelPHMousePressed(evt);
            }
        });
        panelPH.setLayout(new java.awt.GridBagLayout());

        etiquetaPh.setBackground(new java.awt.Color(0, 0, 0));
        etiquetaPh.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        etiquetaPh.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        etiquetaPh.setText("pH");
        etiquetaPh.setToolTipText("");
        etiquetaPh.setMaximumSize(new java.awt.Dimension(165, 60));
        etiquetaPh.setMinimumSize(new java.awt.Dimension(165, 60));
        etiquetaPh.setName(""); // NOI18N
        etiquetaPh.setPreferredSize(new java.awt.Dimension(165, 60));
        etiquetaPh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                etiquetaPhMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                etiquetaPhMousePressed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        panelPH.add(etiquetaPh, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.ipady = 50;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        jPanel1.add(panelPH, gridBagConstraints);

        panelDBO.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 0, 1, new java.awt.Color(0, 0, 0)));
        panelDBO.setFocusable(false);
        panelDBO.setPreferredSize(new java.awt.Dimension(150, 10));
        panelDBO.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                panelDBOMousePressed(evt);
            }
        });
        panelDBO.setLayout(new java.awt.GridBagLayout());

        etiquetaDBO.setBackground(new java.awt.Color(0, 0, 0));
        etiquetaDBO.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        etiquetaDBO.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        etiquetaDBO.setText("<html>DEMANDA BIOQUÍMICA DE OXÍGENO (DBO)</html>");
        etiquetaDBO.setToolTipText("");
        etiquetaDBO.setFocusable(false);
        etiquetaDBO.setMaximumSize(new java.awt.Dimension(165, 60));
        etiquetaDBO.setMinimumSize(new java.awt.Dimension(165, 60));
        etiquetaDBO.setPreferredSize(new java.awt.Dimension(165, 60));
        etiquetaDBO.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                etiquetaDBOMousePressed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        panelDBO.add(etiquetaDBO, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.ipady = 50;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        jPanel1.add(panelDBO, gridBagConstraints);

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
        area12.setText("6-9\n");
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
        area22.setText("500 mg/l");
        area22.setWrapStyleWord(true);
        area22.setBorder(null);
        area22.setFocusable(false);
        area22.setMargin(new java.awt.Insets(7, 7, 7, 7));
        jScrollPane6.setViewportView(area22);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
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
        area32.setText("200 mg/l");
        area32.setWrapStyleWord(true);
        area32.setBorder(null);
        area32.setFocusable(false);
        area32.setMargin(new java.awt.Insets(7, 7, 7, 7));
        jScrollPane7.setViewportView(area32);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.ipadx = 20;
        jPanel1.add(jScrollPane7, gridBagConstraints);

        jScrollPane8.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 1, new java.awt.Color(0, 0, 0)));
        jScrollPane8.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane8.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane8.setFocusable(false);
        jScrollPane8.setMinimumSize(new java.awt.Dimension(150, 60));
        jScrollPane8.setPreferredSize(new java.awt.Dimension(150, 60));

        area52.setEditable(false);
        area52.setColumns(20);
        area52.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        area52.setLineWrap(true);
        area52.setRows(5);
        area52.setText("1 mg/l");
        area52.setWrapStyleWord(true);
        area52.setBorder(null);
        area52.setFocusable(false);
        jScrollPane8.setViewportView(area52);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
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
        area13.setText("POTENCIOMETRICO");
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
        area23.setText("SM 5220 D");
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
        area33.setText("DILUCIONES MÚLTIPLES");
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

        area53.setEditable(false);
        area53.setColumns(20);
        area53.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        area53.setLineWrap(true);
        area53.setRows(5);
        area53.setText("SM 2540 F");
        area53.setWrapStyleWord(true);
        area53.setBorder(null);
        area53.setFocusable(false);
        area53.setMargin(new java.awt.Insets(7, 7, 7, 7));
        jScrollPane12.setViewportView(area53);

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

        panelSolidos10In.setFocusable(false);
        panelSolidos10In.setMaximumSize(new java.awt.Dimension(172, 62));
        panelSolidos10In.setMinimumSize(new java.awt.Dimension(172, 62));
        panelSolidos10In.setLayout(new java.awt.GridBagLayout());

        comboSolidos20.setBackground(new java.awt.Color(204, 204, 204));
        comboSolidos20.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Menor a", "Mayor a", "" }));
        comboSolidos20.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        comboSolidos20.setPreferredSize(new java.awt.Dimension(50, 21));
        comboSolidos20.setUI(new BasicComboBoxUI() {
            protected JButton createArrowButton() {
                return new JButton() {
                    public int getWidth() {
                        return 0;
                    }
                };
            }
        });
        comboSolidos20.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comboSolidos20ItemStateChanged(evt);
            }
        });
        comboSolidos20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboSolidos20ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.ipady = 39;
        panelSolidos10In.add(comboSolidos20, gridBagConstraints);

        etiquetaSolidos10In.setBackground(new java.awt.Color(255, 255, 255));
        etiquetaSolidos10In.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        etiquetaSolidos10In.setText("mg/l");
        etiquetaSolidos10In.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 1, 1, new java.awt.Color(0, 0, 0)));
        etiquetaSolidos10In.setFocusable(false);
        etiquetaSolidos10In.setOpaque(true);
        etiquetaSolidos10In.setPreferredSize(new java.awt.Dimension(36, 16));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 39;
        gridBagConstraints.ipady = 44;
        panelSolidos10In.add(etiquetaSolidos10In, gridBagConstraints);

        cajaSolidos20.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        cajaSolidos20.setMargin(new java.awt.Insets(20, 20, 20, 20));
        cajaSolidos20.setMaximumSize(new java.awt.Dimension(37, 60));
        cajaSolidos20.setMinimumSize(new java.awt.Dimension(37, 60));
        cajaSolidos20.setName(""); // NOI18N
        cajaSolidos20.setPreferredSize(new java.awt.Dimension(37, 60));
        cajaSolidos20.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                cajaSolidos20CaretUpdate(evt);
            }
        });
        cajaSolidos20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cajaSolidos20ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        panelSolidos10In.add(cajaSolidos20, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        jPanel1.add(panelSolidos10In, gridBagConstraints);
        panelSolidos10In.setSize(panelSolidos120In.getSize());

        etiquetaTitulo.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        etiquetaTitulo.setText("Agregar resultados de análisis");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(20, 20, 0, 0);
        jPanel1.add(etiquetaTitulo, gridBagConstraints);

        panelSolidos10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelSolidos10.setFocusable(false);
        panelSolidos10.setPreferredSize(new java.awt.Dimension(150, 10));
        panelSolidos10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                panelSolidos10MousePressed(evt);
            }
        });
        panelSolidos10.setLayout(new java.awt.GridBagLayout());

        etiquetaSolidos10.setBackground(new java.awt.Color(0, 0, 0));
        etiquetaSolidos10.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        etiquetaSolidos10.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        etiquetaSolidos10.setText("<html>SÓLIDOS DISUELTOS SEDIMENTABLES EN 10’</html>");
        etiquetaSolidos10.setToolTipText("");
        etiquetaSolidos10.setFocusable(false);
        etiquetaSolidos10.setMaximumSize(new java.awt.Dimension(165, 60));
        etiquetaSolidos10.setMinimumSize(new java.awt.Dimension(165, 60));
        etiquetaSolidos10.setPreferredSize(new java.awt.Dimension(165, 60));
        etiquetaSolidos10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                etiquetaSolidos10MousePressed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        panelSolidos10.add(etiquetaSolidos10, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.ipady = 50;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        jPanel1.add(panelSolidos10, gridBagConstraints);

        jScrollPane13.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 1, 1, new java.awt.Color(0, 0, 0)));
        jScrollPane13.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane13.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane13.setFocusable(false);
        jScrollPane13.setMinimumSize(new java.awt.Dimension(150, 60));
        jScrollPane13.setPreferredSize(new java.awt.Dimension(150, 60));

        area42.setEditable(false);
        area42.setColumns(20);
        area42.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        area42.setLineWrap(true);
        area42.setRows(5);
        area42.setText("0,5 mg/l");
        area42.setWrapStyleWord(true);
        area42.setBorder(null);
        area42.setFocusable(false);
        jScrollPane13.setViewportView(area42);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.ipadx = 20;
        jPanel1.add(jScrollPane13, gridBagConstraints);

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
        area43.setText("SM 2540 F");
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

        panelPhIn.setBackground(new java.awt.Color(255, 255, 255));
        panelPhIn.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        panelPhIn.setMinimumSize(new java.awt.Dimension(172, 60));
        panelPhIn.setPreferredSize(new java.awt.Dimension(172, 60));
        panelPhIn.setLayout(new java.awt.GridBagLayout());

        cajaPh.setBorder(null);
        cajaPh.setMaximumSize(new java.awt.Dimension(120, 50));
        cajaPh.setMinimumSize(new java.awt.Dimension(120, 50));
        cajaPh.setPreferredSize(new java.awt.Dimension(120, 50));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        panelPhIn.add(cajaPh, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        jPanel1.add(panelPhIn, gridBagConstraints);
        panelPhIn.setSize(panelSolidos120In.getSize());

        panelSolidos120In.setFocusable(false);
        panelSolidos120In.setMaximumSize(new java.awt.Dimension(172, 62));
        panelSolidos120In.setMinimumSize(new java.awt.Dimension(172, 62));
        panelSolidos120In.setLayout(new java.awt.GridBagLayout());

        comboSolidos120.setBackground(new java.awt.Color(204, 204, 204));
        comboSolidos120.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Menor a", "Mayor a", "" }));
        comboSolidos120.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        comboSolidos120.setPreferredSize(new java.awt.Dimension(50, 21));
        comboSolidos120.setUI(new BasicComboBoxUI() {
            protected JButton createArrowButton() {
                return new JButton() {
                    public int getWidth() {
                        return 0;
                    }
                };
            }
        });
        comboSolidos120.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comboSolidos120ItemStateChanged(evt);
            }
        });
        comboSolidos120.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboSolidos120ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.ipady = 39;
        panelSolidos120In.add(comboSolidos120, gridBagConstraints);

        etiquetaSolidos120In.setBackground(new java.awt.Color(255, 255, 255));
        etiquetaSolidos120In.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        etiquetaSolidos120In.setText("mg/l");
        etiquetaSolidos120In.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 1, new java.awt.Color(0, 0, 0)));
        etiquetaSolidos120In.setFocusable(false);
        etiquetaSolidos120In.setOpaque(true);
        etiquetaSolidos120In.setPreferredSize(new java.awt.Dimension(36, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 39;
        gridBagConstraints.ipady = 45;
        panelSolidos120In.add(etiquetaSolidos120In, gridBagConstraints);

        cajaSolidos120.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        cajaSolidos120.setMargin(new java.awt.Insets(20, 20, 20, 20));
        cajaSolidos120.setMaximumSize(new java.awt.Dimension(37, 60));
        cajaSolidos120.setMinimumSize(new java.awt.Dimension(37, 60));
        cajaSolidos120.setPreferredSize(new java.awt.Dimension(37, 60));
        cajaSolidos120.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                cajaSolidos120CaretUpdate(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        panelSolidos120In.add(cajaSolidos120, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        jPanel1.add(panelSolidos120In, gridBagConstraints);

        panelDQOIn.setBackground(new java.awt.Color(255, 255, 255));
        panelDQOIn.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        panelDQOIn.setMinimumSize(new java.awt.Dimension(172, 60));
        panelDQOIn.setPreferredSize(new java.awt.Dimension(172, 60));
        panelDQOIn.setLayout(new java.awt.GridBagLayout());

        cajaDQO.setBorder(null);
        cajaDQO.setMaximumSize(new java.awt.Dimension(120, 50));
        cajaDQO.setMinimumSize(new java.awt.Dimension(120, 50));
        cajaDQO.setPreferredSize(new java.awt.Dimension(120, 50));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        panelDQOIn.add(cajaDQO, gridBagConstraints);

        etiquetaDQOIn.setText("mg/l");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        panelDQOIn.add(etiquetaDQOIn, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        jPanel1.add(panelDQOIn, gridBagConstraints);
        panelDQOIn.setSize(panelSolidos120In.getSize());

        panelDBOIn.setBackground(new java.awt.Color(255, 255, 255));
        panelDBOIn.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        panelDBOIn.setMinimumSize(new java.awt.Dimension(172, 60));
        panelDBOIn.setPreferredSize(new java.awt.Dimension(172, 60));
        panelDBOIn.setLayout(new java.awt.GridBagLayout());

        cajaDBO.setBorder(null);
        cajaDBO.setMaximumSize(new java.awt.Dimension(120, 50));
        cajaDBO.setMinimumSize(new java.awt.Dimension(120, 50));
        cajaDBO.setPreferredSize(new java.awt.Dimension(120, 50));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        panelDBOIn.add(cajaDBO, gridBagConstraints);

        etiquetaDBOIn.setText("mg/l");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        panelDBOIn.add(etiquetaDBOIn, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        jPanel1.add(panelDBOIn, gridBagConstraints);
        panelSolidos10In.setSize(panelSolidos120In.getSize());

        checkConclusion.setText("Crear conclusion");
        checkConclusion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkConclusionActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 9;
        jPanel1.add(checkConclusion, gridBagConstraints);

        jScrollPane15.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 1, new java.awt.Color(0, 0, 0)));
        jScrollPane15.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane15.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane15.setFocusable(false);
        jScrollPane15.setMinimumSize(new java.awt.Dimension(150, 60));
        jScrollPane15.setPreferredSize(new java.awt.Dimension(150, 60));

        area62.setEditable(false);
        area62.setColumns(20);
        area62.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        area62.setLineWrap(true);
        area62.setRows(5);
        area62.setText("1 mg/l");
        area62.setWrapStyleWord(true);
        area62.setBorder(null);
        area62.setFocusable(false);
        jScrollPane15.setViewportView(area62);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.ipadx = 20;
        jPanel1.add(jScrollPane15, gridBagConstraints);

        panelHidrocarburos.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 1, 1, 1, new java.awt.Color(0, 0, 0)));
        panelHidrocarburos.setFocusable(false);
        panelHidrocarburos.setPreferredSize(new java.awt.Dimension(150, 10));
        panelHidrocarburos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                panelHidrocarburosMousePressed(evt);
            }
        });
        panelHidrocarburos.setLayout(new java.awt.GridBagLayout());

        etiquetaHidrocarburos.setBackground(new java.awt.Color(0, 0, 0));
        etiquetaHidrocarburos.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        etiquetaHidrocarburos.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        etiquetaHidrocarburos.setText("<html>HIDROCARBUROS TOTALES DE PETRÓLEO (IR)</html>");
        etiquetaHidrocarburos.setToolTipText("");
        etiquetaHidrocarburos.setFocusable(false);
        etiquetaHidrocarburos.setMaximumSize(new java.awt.Dimension(165, 60));
        etiquetaHidrocarburos.setMinimumSize(new java.awt.Dimension(165, 60));
        etiquetaHidrocarburos.setPreferredSize(new java.awt.Dimension(165, 60));
        etiquetaHidrocarburos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                etiquetaHidrocarburosMousePressed(evt);
            }
        });
        panelHidrocarburos.add(etiquetaHidrocarburos, new java.awt.GridBagConstraints());

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.ipady = 50;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        jPanel1.add(panelHidrocarburos, gridBagConstraints);

        jScrollPane16.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 1, new java.awt.Color(0, 0, 0)));
        jScrollPane16.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane16.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane16.setFocusable(false);
        jScrollPane16.setMinimumSize(new java.awt.Dimension(150, 60));
        jScrollPane16.setPreferredSize(new java.awt.Dimension(150, 60));

        area63.setEditable(false);
        area63.setColumns(20);
        area63.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        area63.setLineWrap(true);
        area63.setRows(5);
        area63.setText("AMB 2613");
        area63.setWrapStyleWord(true);
        area63.setBorder(null);
        area63.setFocusable(false);
        area63.setMargin(new java.awt.Insets(7, 7, 7, 7));
        jScrollPane16.setViewportView(area63);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 20);
        jPanel1.add(jScrollPane16, gridBagConstraints);

        panelHidrocarburosIn.setFocusable(false);
        panelHidrocarburosIn.setMaximumSize(new java.awt.Dimension(172, 62));
        panelHidrocarburosIn.setMinimumSize(new java.awt.Dimension(172, 62));
        panelHidrocarburosIn.setLayout(new java.awt.GridBagLayout());

        comboHidrocarburos.setBackground(new java.awt.Color(204, 204, 204));
        comboHidrocarburos.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Menor a", "Mayor a", "" }));
        comboHidrocarburos.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        comboHidrocarburos.setPreferredSize(new java.awt.Dimension(50, 21));
        comboHidrocarburos.setUI(new BasicComboBoxUI() {
            protected JButton createArrowButton() {
                return new JButton() {
                    public int getWidth() {
                        return 0;
                    }
                };
            }
        });
        comboHidrocarburos.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comboHidrocarburosItemStateChanged(evt);
            }
        });
        comboHidrocarburos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboHidrocarburosActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.ipady = 39;
        panelHidrocarburosIn.add(comboHidrocarburos, gridBagConstraints);

        etiquetaHidrocarburosIn.setBackground(new java.awt.Color(255, 255, 255));
        etiquetaHidrocarburosIn.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        etiquetaHidrocarburosIn.setText("mg/l");
        etiquetaHidrocarburosIn.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 1, new java.awt.Color(0, 0, 0)));
        etiquetaHidrocarburosIn.setFocusable(false);
        etiquetaHidrocarburosIn.setOpaque(true);
        etiquetaHidrocarburosIn.setPreferredSize(new java.awt.Dimension(36, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 39;
        gridBagConstraints.ipady = 45;
        panelHidrocarburosIn.add(etiquetaHidrocarburosIn, gridBagConstraints);

        cajaHidrocarburos.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        cajaHidrocarburos.setMargin(new java.awt.Insets(20, 20, 20, 20));
        cajaHidrocarburos.setMaximumSize(new java.awt.Dimension(37, 60));
        cajaHidrocarburos.setMinimumSize(new java.awt.Dimension(37, 60));
        cajaHidrocarburos.setPreferredSize(new java.awt.Dimension(37, 60));
        cajaHidrocarburos.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                cajaHidrocarburosCaretUpdate(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        panelHidrocarburosIn.add(cajaHidrocarburos, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        jPanel1.add(panelHidrocarburosIn, gridBagConstraints);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 834, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 537, Short.MAX_VALUE)
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
        String observaciones = "";
        observaciones = JOptionPane.showInputDialog("Ingrese las observaciones:", c.recuperarObservaciones(id));
        observaciones = observaciones.isBlank() ? "" : observaciones.trim().endsWith(".") ? observaciones : observaciones + ".";
        String ph = cajaPh.getText(), dqo = cajaDQO.getText(), dbo = cajaDBO.getText(),
                solidos20 = comboSolidos20.getSelectedItem().toString() + " " + cajaSolidos20.getText(),
                solidos120 = comboSolidos120.getSelectedItem().toString() + " " + cajaSolidos120.getText(),
                hidrocarburos = comboHidrocarburos.getSelectedItem().toString() + " " + cajaHidrocarburos.getText() + " " + etiquetaHidrocarburosIn.getText();

        if (!cajaPh.isEnabled()) {
            ph = "-2";
        }

        if (!cajaDQO.isEnabled()) {
            dqo = "-2";
        }

        if (!cajaDBO.isEnabled()) {
            dbo = "-2";
        }

        if (!cajaSolidos20.isEnabled()) {
            solidos20 = "-2";
        }

        if (!cajaSolidos120.isEnabled()) {
            solidos120 = "-2";
        }

        if (!cajaHidrocarburos.isEnabled()) {
            hidrocarburos = "-2";
        }

        String[] resultados = {String.valueOf(id), ph, dqo, dbo, solidos20, solidos120, crearConclusion(), hidrocarburos};
        if (editar) {
            File rv = new File(c.recuperarRutas("Reportes") + "\\" + pdf);
            File rn = new File(c.recuperarRutas("Reportes") + "\\(BORRADO) " + pdf);
            rv.renameTo(rn);
            if (c.editarEfluentes(resultados, id)) {
                if (checkConclusion.isSelected()) {
                    c.guardarConclusion(resultados[6], id);
                } else {
                    c.guardarConclusion(null, id);
                }
                c.guardarObservaciones(observaciones, id);
                this.dispose();
                c.generarReporteEfluentes(id, procedencia);
            }
        } else {
            if (c.guardarResultadosEfluentes(resultados, id)) {
                if (checkConclusion.isSelected()) {
                    c.guardarConclusion(resultados[6], id);
                } else {
                    c.guardarConclusion(null, id);
                }
                observaciones = JOptionPane.showInputDialog("Ingrese las observaciones:");
                observaciones = observaciones.trim().endsWith(".") ? observaciones : observaciones + ".";
                c.guardarObservaciones(observaciones, id);
                this.dispose();
                c.generarReporteEfluentes(id, procedencia);

            }
        }
    }//GEN-LAST:event_botonGenerarActionPerformed

    private void cajaSolidos20CaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_cajaSolidos20CaretUpdate
    }//GEN-LAST:event_cajaSolidos20CaretUpdate

    private void cajaSolidos120CaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_cajaSolidos120CaretUpdate
        // TODO add your handling code here:
    }//GEN-LAST:event_cajaSolidos120CaretUpdate

    private void cajaSolidos20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cajaSolidos20ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cajaSolidos20ActionPerformed

    private void checkConclusionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkConclusionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_checkConclusionActionPerformed

    private void etiquetaPhMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_etiquetaPhMousePressed
        activarPh = !activarPh;
        cajaPh.setEnabled(activarPh);
        area12.setEnabled(activarPh);
        area13.setEnabled(activarPh);
        if (activarPh) {
            panelPH.setBackground(new Color(240, 240, 240));
            panelPhIn.setBackground(new Color(255, 255, 255));
        } else {
            panelPH.setBackground(new Color(240, 100, 100));
            panelPhIn.setBackground(new Color(240, 240, 240));
        }
    }//GEN-LAST:event_etiquetaPhMousePressed

    private void etiquetaDQOMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_etiquetaDQOMousePressed
        activarDQO = !activarDQO;
        cajaDQO.setEnabled(activarDQO);
        area22.setEnabled(activarDQO);
        area23.setEnabled(activarDQO);
        etiquetaDQOIn.setEnabled(activarDQO);
        if (activarDQO) {
            panelDQO.setBackground(new Color(240, 240, 240));
            panelDQOIn.setBackground(new Color(255, 255, 255));
        } else {
            panelDQO.setBackground(new Color(240, 100, 100));
            panelDQOIn.setBackground(new Color(240, 240, 240));
        }
    }//GEN-LAST:event_etiquetaDQOMousePressed

    private void etiquetaDBOMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_etiquetaDBOMousePressed
        activarDBO = !activarDBO;
        cajaDBO.setEnabled(activarDBO);
        area32.setEnabled(activarDBO);
        area33.setEnabled(activarDBO);
        etiquetaDBOIn.setEnabled(activarDBO);
        if (activarDBO) {
            panelDBO.setBackground(new Color(240, 240, 240));
            panelDBOIn.setBackground(new Color(255, 255, 255));
        } else {
            panelDBO.setBackground(new Color(240, 100, 100));
            panelDBOIn.setBackground(new Color(240, 240, 240));
        }
    }//GEN-LAST:event_etiquetaDBOMousePressed

    private void etiquetaSolidos10MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_etiquetaSolidos10MousePressed
        activarSolidos10 = !activarSolidos10;
        cajaSolidos20.setEnabled(activarSolidos10);
        area42.setEnabled(activarSolidos10);
        area43.setEnabled(activarSolidos10);
        comboSolidos20.setEnabled(activarSolidos10);
        etiquetaSolidos10In.setEnabled(activarSolidos10);
        if (activarSolidos10) {
            panelSolidos10.setBackground(new Color(240, 240, 240));
            panelSolidos10In.setBackground(new Color(255, 255, 255));
        } else {
            panelSolidos10.setBackground(new Color(240, 100, 100));
            panelSolidos10In.setBackground(new Color(240, 240, 240));
        }
    }//GEN-LAST:event_etiquetaSolidos10MousePressed

    private void etiquetaSolidos120MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_etiquetaSolidos120MousePressed
        activarSolidos120 = !activarSolidos120;
        cajaSolidos120.setEnabled(activarSolidos120);
        area52.setEnabled(activarSolidos120);
        area53.setEnabled(activarSolidos120);
        comboSolidos120.setEnabled(activarSolidos120);
        etiquetaSolidos120In.setEnabled(activarSolidos120);
        if (activarSolidos120) {
            panelSolidos120.setBackground(new Color(240, 240, 240));
            panelSolidos120In.setBackground(new Color(255, 255, 255));
        } else {
            panelSolidos120.setBackground(new Color(240, 100, 100));
            panelSolidos120In.setBackground(new Color(240, 240, 240));
        }
    }//GEN-LAST:event_etiquetaSolidos120MousePressed

    private void etiquetaPhMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_etiquetaPhMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_etiquetaPhMouseClicked

    private void panelPHMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelPHMousePressed
        activarPh = !activarPh;
        cajaPh.setEnabled(activarPh);
        area12.setEnabled(activarPh);
        area13.setEnabled(activarPh);
        if (activarPh) {
            panelPH.setBackground(new Color(240, 240, 240));
            panelPhIn.setBackground(new Color(255, 255, 255));
        } else {
            panelPH.setBackground(new Color(240, 100, 100));
            panelPhIn.setBackground(new Color(240, 240, 240));
        }
    }//GEN-LAST:event_panelPHMousePressed

    private void panelDQOMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelDQOMousePressed
        activarDQO = !activarDQO;
        cajaDQO.setEnabled(activarDQO);
        area22.setEnabled(activarDQO);
        area23.setEnabled(activarDQO);
        etiquetaDQOIn.setEnabled(activarDQO);
        if (activarDQO) {
            panelDQO.setBackground(new Color(240, 240, 240));
            panelDQOIn.setBackground(new Color(255, 255, 255));
        } else {
            panelDQO.setBackground(new Color(240, 100, 100));
            panelDQOIn.setBackground(new Color(240, 240, 240));
        }
    }//GEN-LAST:event_panelDQOMousePressed

    private void panelDBOMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelDBOMousePressed
        activarDBO = !activarDBO;
        cajaDBO.setEnabled(activarDBO);
        area32.setEnabled(activarDBO);
        area33.setEnabled(activarDBO);
        etiquetaDBOIn.setEnabled(activarDBO);
        if (activarDBO) {
            panelDBO.setBackground(new Color(240, 240, 240));
            panelDBOIn.setBackground(new Color(255, 255, 255));
        } else {
            panelDBO.setBackground(new Color(240, 100, 100));
            panelDBOIn.setBackground(new Color(240, 240, 240));
        }
    }//GEN-LAST:event_panelDBOMousePressed

    private void panelSolidos10MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelSolidos10MousePressed
        activarSolidos10 = !activarSolidos10;
        cajaSolidos20.setEnabled(activarSolidos10);
        area42.setEnabled(activarSolidos10);
        area43.setEnabled(activarSolidos10);
        comboSolidos20.setEnabled(activarSolidos10);
        etiquetaSolidos10In.setEnabled(activarSolidos10);
        if (activarSolidos10) {
            panelSolidos10.setBackground(new Color(240, 240, 240));
            panelSolidos10In.setBackground(new Color(255, 255, 255));
        } else {
            panelSolidos10.setBackground(new Color(240, 100, 100));
            panelSolidos10In.setBackground(new Color(240, 240, 240));
        }
    }//GEN-LAST:event_panelSolidos10MousePressed

    private void panelSolidos120MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelSolidos120MousePressed
        activarSolidos120 = !activarSolidos120;
        cajaSolidos120.setEnabled(activarSolidos120);
        area52.setEnabled(activarSolidos120);
        area53.setEnabled(activarSolidos120);
        comboSolidos120.setEnabled(activarSolidos120);
        etiquetaSolidos120In.setEnabled(activarSolidos120);
        if (activarSolidos120) {
            panelSolidos120.setBackground(new Color(240, 240, 240));
            panelSolidos120In.setBackground(new Color(255, 255, 255));
        } else {
            panelSolidos120.setBackground(new Color(240, 100, 100));
            panelSolidos120In.setBackground(new Color(240, 240, 240));
        }
    }//GEN-LAST:event_panelSolidos120MousePressed

    private void etiquetaHidrocarburosMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_etiquetaHidrocarburosMousePressed
        activarHidrocarburos = !activarHidrocarburos;
        cajaHidrocarburos.setEnabled(activarHidrocarburos);
        comboHidrocarburos.setEnabled(activarHidrocarburos);
        area62.setEnabled(activarHidrocarburos);
        area63.setEnabled(activarHidrocarburos);
        etiquetaHidrocarburosIn.setEnabled(activarHidrocarburos);
        if (activarHidrocarburos) {
            panelHidrocarburos.setBackground(new Color(240, 240, 240));
            panelHidrocarburosIn.setBackground(new Color(255, 255, 255));
        } else {
            panelHidrocarburos.setBackground(new Color(240, 100, 100));
            panelHidrocarburosIn.setBackground(new Color(240, 240, 240));
        }
    }//GEN-LAST:event_etiquetaHidrocarburosMousePressed

    private void panelHidrocarburosMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelHidrocarburosMousePressed
        activarHidrocarburos = !activarHidrocarburos;
        cajaHidrocarburos.setEnabled(activarHidrocarburos);
        area62.setEnabled(activarHidrocarburos);
        area63.setEnabled(activarHidrocarburos);
        etiquetaHidrocarburosIn.setEnabled(activarHidrocarburos);
        if (activarHidrocarburos) {
            panelHidrocarburos.setBackground(new Color(240, 240, 240));
            panelHidrocarburosIn.setBackground(new Color(255, 255, 255));
        } else {
            panelHidrocarburos.setBackground(new Color(240, 100, 100));
            panelHidrocarburosIn.setBackground(new Color(240, 240, 240));
        }
    }//GEN-LAST:event_panelHidrocarburosMousePressed

    private void comboSolidos120ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboSolidos120ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboSolidos120ActionPerformed

    private void comboSolidos120ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboSolidos120ItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_comboSolidos120ItemStateChanged

    private void comboSolidos20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboSolidos20ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboSolidos20ActionPerformed

    private void comboSolidos20ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboSolidos20ItemStateChanged

    }//GEN-LAST:event_comboSolidos20ItemStateChanged

    private void comboHidrocarburosItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboHidrocarburosItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_comboHidrocarburosItemStateChanged

    private void comboHidrocarburosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboHidrocarburosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboHidrocarburosActionPerformed

    private void cajaHidrocarburosCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_cajaHidrocarburosCaretUpdate
        // TODO add your handling code here:
    }//GEN-LAST:event_cajaHidrocarburosCaretUpdate

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
            java.util.logging.Logger.getLogger(TablaEfluentes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TablaEfluentes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TablaEfluentes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TablaEfluentes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                TablaEfluentes dialog = new TablaEfluentes(new javax.swing.JFrame(), true, -1, null, false, null, null);
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
    private javax.swing.JTextArea area12;
    private javax.swing.JTextArea area13;
    private javax.swing.JTextArea area22;
    private javax.swing.JTextArea area23;
    private javax.swing.JTextArea area32;
    private javax.swing.JTextArea area33;
    private javax.swing.JTextArea area42;
    private javax.swing.JTextArea area43;
    private javax.swing.JTextArea area52;
    private javax.swing.JTextArea area53;
    private javax.swing.JTextArea area62;
    private javax.swing.JTextArea area63;
    private javax.swing.JButton botonGenerar;
    private javax.swing.JTextField cajaDBO;
    private javax.swing.JTextField cajaDQO;
    private javax.swing.JTextField cajaHidrocarburos;
    private javax.swing.JTextField cajaPh;
    private javax.swing.JTextField cajaSolidos120;
    private javax.swing.JTextField cajaSolidos20;
    private javax.swing.JCheckBox checkConclusion;
    private javax.swing.JComboBox<String> comboHidrocarburos;
    private javax.swing.JComboBox<String> comboSolidos120;
    private javax.swing.JComboBox<String> comboSolidos20;
    private javax.swing.JLabel etiquetaDBO;
    private javax.swing.JLabel etiquetaDBOIn;
    private javax.swing.JLabel etiquetaDQO;
    private javax.swing.JLabel etiquetaDQOIn;
    private javax.swing.JLabel etiquetaHidrocarburos;
    private javax.swing.JLabel etiquetaHidrocarburosIn;
    private javax.swing.JLabel etiquetaPh;
    private javax.swing.JLabel etiquetaSolidos10;
    private javax.swing.JLabel etiquetaSolidos10In;
    private javax.swing.JLabel etiquetaSolidos120;
    private javax.swing.JLabel etiquetaSolidos120In;
    private javax.swing.JLabel etiquetaTitulo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane15;
    private javax.swing.JScrollPane jScrollPane16;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JPanel panelDBO;
    private javax.swing.JPanel panelDBOIn;
    private javax.swing.JPanel panelDQO;
    private javax.swing.JPanel panelDQOIn;
    private javax.swing.JPanel panelHidrocarburos;
    private javax.swing.JPanel panelHidrocarburosIn;
    private javax.swing.JPanel panelPH;
    private javax.swing.JPanel panelPhIn;
    private javax.swing.JPanel panelSolidos10;
    private javax.swing.JPanel panelSolidos10In;
    private javax.swing.JPanel panelSolidos120;
    private javax.swing.JPanel panelSolidos120In;
    // End of variables declaration//GEN-END:variables

    public String crearConclusion() {
        boolean ph;
        if (cajaPh.isEnabled()) {
            ph = Double.parseDouble(cajaPh.getText()) > 6 && Double.parseDouble(cajaPh.getText()) < 9;
        } else {
            ph = false;
        }
        boolean dqo;
        if (cajaDQO.isEnabled()) {
            dqo = Integer.parseInt(cajaDQO.getText()) < 500;
        } else {
            dqo = false;
        }
        boolean dbo;
        if (cajaDBO.isEnabled()) {
            dbo = Integer.parseInt(cajaDBO.getText()) < 200;
        } else {
            dbo = false;
        }
        boolean solidos20;
        if (cajaSolidos20.isEnabled()) {
            if (comboSolidos20.getSelectedItem().toString().equals("Mayor a") || Double.parseDouble(cajaSolidos20.getText()) > 0.5) {
                solidos20 = false;
            } else if (comboSolidos20.getSelectedItem().toString().equals("Menor a") || Double.parseDouble(cajaSolidos20.getText()) >= 0.5) {
                solidos20 = true;
            } else {
                solidos20 = Double.parseDouble(cajaSolidos20.getText()) < 0.5;
            }
        } else {
            solidos20 = false;
        }
        boolean solidos120;
        if (cajaSolidos120.isEnabled()) {
            if (comboSolidos120.getSelectedItem().toString().equals("Mayor a") || Double.parseDouble(cajaSolidos120.getText()) > 1) {
                solidos120 = false;
            } else if (comboSolidos120.getSelectedItem().toString().equals("Menor a") || Double.parseDouble(cajaSolidos120.getText()) >= 1) {
                solidos120 = true;
            } else {
                solidos120 = Double.parseDouble(cajaSolidos120.getText()) < 1;
            }
        } else {
            solidos120 = false;
        }
        boolean hidrocarburos;
        if (cajaHidrocarburos.isEnabled()) {
            if (comboHidrocarburos.getSelectedItem().toString().equals("Mayor a") || Double.parseDouble(cajaHidrocarburos.getText()) > 1) {
                hidrocarburos = false;
            } else if (comboHidrocarburos.getSelectedItem().toString().equals("Menor a") || Double.parseDouble(cajaHidrocarburos.getText()) >= 1) {
                hidrocarburos = true;
            } else {
                hidrocarburos = Double.parseDouble(cajaHidrocarburos.getText()) < 1;
            }
        } else {
            hidrocarburos = false;
        }
        String conclusion = "";
        String recomendacion = "";
        if (ph && dqo && dbo && solidos20 && solidos120 && hidrocarburos) {
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
            if (!solidos20) {
                conclusion += " Sólidos disueltos sedimentables en 10 minutos,";

            }
            if (!solidos120) {
                conclusion += " Sólidos disueltos sedimentables en 120 minutos,";

            }
            if (!hidrocarburos) {
                conclusion += " Hidrocarburos totales de petróleo (IR),";
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
