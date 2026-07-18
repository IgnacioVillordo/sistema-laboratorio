package org.ignaciorodriguez.vista;

import com.google.common.base.CharMatcher;
import java.awt.Color;
import java.awt.Component;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.border.MatteBorder;
import javax.swing.plaf.basic.BasicComboBoxUI;
import org.ignaciorodriguez.modelo.Consultas;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Date;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.KeyStroke;
import javax.swing.JComponent;

public class TablaMBChocolates extends javax.swing.JDialog {

    int id;
    String procedencia, pdf, auxObservaciones;
    Consultas c = Consultas.getInstancia();
    boolean editar, activarGermenes = true, activarTotales = true, activarFecales = true,
            activarMohos = true, activarEscherichia = true, activarSalmonella = true;

    public TablaMBChocolates(java.awt.Frame parent, boolean modal, int id, String procedencia,
                             boolean editar, String pdf) {
        super(parent, modal);
        this.procedencia = procedencia;
        this.id = id;
        this.editar = editar;
        this.pdf = pdf;
        initComponents();
        setTitle("ID " + id + ". " + c.obtenerProcedencia(id) + ". Microbiológico de chocolates");
        setLocationRelativeTo(null);
        panelGermenesMousePressed(click(panelGermenes));
        if (editar) {
            Map<String, String> resultados = c.recuperarResultadosMBChocolates(id);
            Date fecha = c.recuperarFechaAnalisis(id);
            Date fechaAux = new Date(6, 10, -789);
            if (fecha.equals(fechaAux)) {
                cajaFechaAnalisis.setDate(fecha);

            } else {
                cajaFechaAnalisis.setDate(fecha);
            }
            if (resultados == null) {
                this.editar = false;

            } else {
                if (resultados.get("germenes").toUpperCase().contains("MENOR A ")) {
                    panelGermenesMousePressed(click(panelGermenes));
                    checkGermenes.doClick();
                } else if (resultados.get("germenes").equals("-2")) {

                } else {
                    panelGermenesMousePressed(click(panelGermenes));
                    cajaGermenes.setText(CharMatcher.inRange('0', '9').retainFrom(resultados.get("germenes")));
                }
                if (resultados.get("coliformesTotales").toUpperCase().contains("MENOR A ")) {
                    checkColiformesTotales.doClick();
                } else if (resultados.get("coliformesTotales").contains("-1")) {
                } else if (resultados.get("coliformesTotales").equals("-2")) {
                    panelTotalesMousePressed(click(panelTotales));
                } else {
                    cajaColiformesTotales.setText(CharMatcher.inRange('0', '9').retainFrom(resultados.get("coliformesTotales")));
                }
                if (resultados.get("coliformesFecales").toUpperCase().contains("MENOR A ")) {
                    checkColiformesFecales.doClick();
                } else if (resultados.get("coliformesFecales").contains("-1")) {
                } else if (resultados.get("coliformesFecales").equals("-2")) {
                    panelFecalesMousePressed(click(panelFecales));
                } else {
                    cajaColiformesFecales.setText(CharMatcher.inRange('0', '9').retainFrom(resultados.get("coliformesFecales")));
                }
                if (resultados.get("escherichia").startsWith("Presencia")) {
                    comboEscherichia.setSelectedIndex(1);
                } else if (resultados.get("escherichia").contains("-1")) {
                } else if (resultados.get("escherichia").equals("-2")) {
                    panelEscherichiaMousePressed(click(panelEscherichia));
                } else {
                    comboEscherichia.setSelectedIndex(0);
                }
                if (resultados.get("mohos").toUpperCase().contains("MENOR A")) {
                    checkMohos.doClick();
                } else if (resultados.get("mohos").contains("-1")) {
                } else if (resultados.get("mohos").equals("-2")) {
                    panelMohosMousePressed(click(panelMohos));
                } else {
                    cajaMoho.setText(CharMatcher.inRange('0', '9').retainFrom(resultados.get("mohos")));
                }
                if (resultados.get("salmonella").startsWith("Presencia")) {
                    comboSalmonella.setSelectedIndex(1);
                    if (resultados.get("salmonella").contains("en 10 g")) {
                        comboUnidadSalmonella.setSelectedIndex(0);
                    } else {
                        comboUnidadSalmonella.setSelectedIndex(1);                        
                    }
                } else if (resultados.get("salmonella").contains("-1")) {
                } else if (resultados.get("salmonella").equals("-2") || resultados.get("salmonella").equals("null")) {
                    panelSalmonellaMousePressed(click(panelSalmonella));
                } else {
                    comboSalmonella.setSelectedIndex(0);
                    if (resultados.get("salmonella").contains("en 10 g")) {
                        comboUnidadSalmonella.setSelectedIndex(0);
                    } else {
                        comboUnidadSalmonella.setSelectedIndex(1);
                    }
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
        jLabel4 = new javax.swing.JLabel();
        panelTotales = new javax.swing.JPanel();
        etiquetaTotales = new javax.swing.JLabel();
        panelMohos = new javax.swing.JPanel();
        etiquetaMoho = new javax.swing.JLabel();
        panelGermenes = new javax.swing.JPanel();
        etiquetaGermenes = new javax.swing.JLabel();
        panelFecales = new javax.swing.JPanel();
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
        panelEscherichia = new javax.swing.JPanel();
        etiquetaEscherichia = new javax.swing.JLabel();
        jScrollPane14 = new javax.swing.JScrollPane();
        area45 = new javax.swing.JTextArea();
        jPanel10 = new javax.swing.JPanel();
        comboEscherichia = new javax.swing.JComboBox<>();
        etiquetaComboEscherichia = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        cajaGermenes = new javax.swing.JTextField();
        etiquetaUnidadGermenes = new javax.swing.JLabel();
        etiquetaMenorGermenes = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        cajaColiformesTotales = new javax.swing.JTextField();
        etiquetaUnidadColiformesTotales = new javax.swing.JLabel();
        etiquetaMenorColiformesTotales = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        cajaColiformesFecales = new javax.swing.JTextField();
        etiquetaUnidadColiformesFecales = new javax.swing.JLabel();
        etiquetaMenorColiformesFecales = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        cajaMoho = new javax.swing.JTextField();
        etiquetaUnidadMohos = new javax.swing.JLabel();
        etiquetaMenorMohos = new javax.swing.JLabel();
        checkGermenes = new javax.swing.JCheckBox();
        checkColiformesTotales = new javax.swing.JCheckBox();
        checkColiformesFecales = new javax.swing.JCheckBox();
        checkMohos = new javax.swing.JCheckBox();
        cajaFechaAnalisis = new com.toedter.calendar.JDateChooser();
        jLabel3 = new javax.swing.JLabel();
        panelSalmonella = new javax.swing.JPanel();
        etiquetaSalmonella = new javax.swing.JLabel();
        jScrollPane13 = new javax.swing.JScrollPane();
        area44 = new javax.swing.JTextArea();
        jPanel11 = new javax.swing.JPanel();
        comboSalmonella = new javax.swing.JComboBox<>();
        comboUnidadSalmonella = new javax.swing.JComboBox<>();

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
        gridBagConstraints.ipadx = 22;
        gridBagConstraints.ipady = 40;
        gridBagConstraints.insets = new java.awt.Insets(20, 0, 0, 0);
        jPanel1.add(jLabel4, gridBagConstraints);

        panelTotales.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 0, 1, new java.awt.Color(0, 0, 0)));
        panelTotales.setFocusable(false);
        panelTotales.setPreferredSize(new java.awt.Dimension(150, 10));
        panelTotales.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                panelTotalesMousePressed(evt);
            }
        });
        panelTotales.setLayout(new java.awt.GridBagLayout());

        etiquetaTotales.setBackground(new java.awt.Color(0, 0, 0));
        etiquetaTotales.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        etiquetaTotales.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        etiquetaTotales.setText("<html>COLIFORMES TOTALES</html>");
        etiquetaTotales.setToolTipText("");
        etiquetaTotales.setMaximumSize(new java.awt.Dimension(170, 60));
        etiquetaTotales.setMinimumSize(new java.awt.Dimension(170, 60));
        etiquetaTotales.setPreferredSize(new java.awt.Dimension(170, 60));
        etiquetaTotales.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                etiquetaTotalesMousePressed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        panelTotales.add(etiquetaTotales, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.ipady = 50;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        jPanel1.add(panelTotales, gridBagConstraints);

        panelMohos.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 1, 0, 1, new java.awt.Color(0, 0, 0)));
        panelMohos.setFocusable(false);
        panelMohos.setPreferredSize(new java.awt.Dimension(150, 10));
        panelMohos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                panelMohosMousePressed(evt);
            }
        });
        panelMohos.setLayout(new java.awt.GridBagLayout());

        etiquetaMoho.setBackground(new java.awt.Color(0, 0, 0));
        etiquetaMoho.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        etiquetaMoho.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        etiquetaMoho.setText("<html>MOHOS Y LEVADURAS</html>");
        etiquetaMoho.setToolTipText("");
        etiquetaMoho.setFocusable(false);
        etiquetaMoho.setMaximumSize(new java.awt.Dimension(170, 60));
        etiquetaMoho.setMinimumSize(new java.awt.Dimension(170, 60));
        etiquetaMoho.setPreferredSize(new java.awt.Dimension(170, 60));
        etiquetaMoho.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                etiquetaMohoMousePressed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        panelMohos.add(etiquetaMoho, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.ipady = 50;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        jPanel1.add(panelMohos, gridBagConstraints);

        panelGermenes.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 0, 1, new java.awt.Color(0, 0, 0)));
        panelGermenes.setFocusable(false);
        panelGermenes.setPreferredSize(new java.awt.Dimension(150, 10));
        panelGermenes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                panelGermenesMousePressed(evt);
            }
        });
        panelGermenes.setLayout(new java.awt.GridBagLayout());

        etiquetaGermenes.setBackground(new java.awt.Color(0, 0, 0));
        etiquetaGermenes.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        etiquetaGermenes.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        etiquetaGermenes.setText("<html>       GERMENES AEROBIOS TOTALES</html>");
        etiquetaGermenes.setToolTipText("");
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
        panelGermenes.add(etiquetaGermenes, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.ipady = 50;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        jPanel1.add(panelGermenes, gridBagConstraints);

        panelFecales.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 0, 1, new java.awt.Color(0, 0, 0)));
        panelFecales.setFocusable(false);
        panelFecales.setPreferredSize(new java.awt.Dimension(150, 10));
        panelFecales.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                panelFecalesMousePressed(evt);
            }
        });
        panelFecales.setLayout(new java.awt.GridBagLayout());

        etiquetaFecales.setBackground(new java.awt.Color(0, 0, 0));
        etiquetaFecales.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        etiquetaFecales.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        etiquetaFecales.setText("<html>COLIFORMES FECALES</html>");
        etiquetaFecales.setToolTipText("");
        etiquetaFecales.setMaximumSize(new java.awt.Dimension(170, 60));
        etiquetaFecales.setMinimumSize(new java.awt.Dimension(170, 60));
        etiquetaFecales.setPreferredSize(new java.awt.Dimension(170, 60));
        etiquetaFecales.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                etiquetaFecalesMousePressed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        panelFecales.add(etiquetaFecales, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.ipady = 50;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        jPanel1.add(panelFecales, gridBagConstraints);

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
        area23.setText("ISO 4832-2066");
        area23.setWrapStyleWord(true);
        area23.setBorder(null);
        area23.setFocusable(false);
        area23.setMargin(new java.awt.Insets(7, 7, 7, 7));
        jScrollPane10.setViewportView(area23);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
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
        area33.setText("SMEWW 22nd. Edition. APHA (2012) 9221 B");
        area33.setWrapStyleWord(true);
        area33.setBorder(null);
        area33.setFocusable(false);
        area33.setMargin(new java.awt.Insets(7, 7, 7, 7));
        jScrollPane11.setViewportView(area33);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
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
        area43.setText("ISO 21527-2:2008");
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
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(20, 20, 0, 0);
        jPanel1.add(etiquetaTitulo, gridBagConstraints);

        panelEscherichia.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelEscherichia.setFocusable(false);
        panelEscherichia.setPreferredSize(new java.awt.Dimension(150, 10));
        panelEscherichia.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                panelEscherichiaMousePressed(evt);
            }
        });
        panelEscherichia.setLayout(new java.awt.GridBagLayout());

        etiquetaEscherichia.setBackground(new java.awt.Color(0, 0, 0));
        etiquetaEscherichia.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        etiquetaEscherichia.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        etiquetaEscherichia.setText("<html>ESCHERICHIA COLI</html>");
        etiquetaEscherichia.setToolTipText("");
        etiquetaEscherichia.setFocusable(false);
        etiquetaEscherichia.setMaximumSize(new java.awt.Dimension(170, 60));
        etiquetaEscherichia.setMinimumSize(new java.awt.Dimension(170, 60));
        etiquetaEscherichia.setPreferredSize(new java.awt.Dimension(170, 60));
        etiquetaEscherichia.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                etiquetaEscherichiaMousePressed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        panelEscherichia.add(etiquetaEscherichia, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.ipady = 50;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        jPanel1.add(panelEscherichia, gridBagConstraints);

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
        area45.setText("SMEWW 22nd. Edition. APHA (2012) 9221 B");
        area45.setWrapStyleWord(true);
        area45.setBorder(null);
        area45.setFocusable(false);
        area45.setMargin(new java.awt.Insets(7, 7, 7, 7));
        jScrollPane14.setViewportView(area45);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 20);
        jPanel1.add(jScrollPane14, gridBagConstraints);

        jPanel10.setFocusable(false);
        jPanel10.setLayout(new java.awt.GridBagLayout());

        comboEscherichia.setBackground(new java.awt.Color(204, 204, 204));
        comboEscherichia.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ausencia", "Presencia" }));
        comboEscherichia.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        comboEscherichia.setPreferredSize(new java.awt.Dimension(80, 20));
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

        etiquetaComboEscherichia.setBackground(new java.awt.Color(255, 255, 255));
        etiquetaComboEscherichia.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        etiquetaComboEscherichia.setText("en 10 g");
        etiquetaComboEscherichia.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 1, 1, new java.awt.Color(0, 0, 0)));
        etiquetaComboEscherichia.setFocusable(false);
        etiquetaComboEscherichia.setOpaque(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 15;
        gridBagConstraints.ipady = 44;
        jPanel10.add(etiquetaComboEscherichia, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 6;
        jPanel1.add(jPanel10, gridBagConstraints);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        jPanel3.setForeground(new java.awt.Color(255, 255, 255));
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

        etiquetaUnidadGermenes.setText("UFC/g");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        jPanel3.add(etiquetaUnidadGermenes, gridBagConstraints);

        etiquetaMenorGermenes.setText("MENOR A 10 UFC/g");
        jPanel3.add(etiquetaMenorGermenes, new java.awt.GridBagConstraints());
        etiquetaMenorGermenes.setVisible(false);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        jPanel1.add(jPanel3, gridBagConstraints);

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        jPanel9.setForeground(new java.awt.Color(255, 255, 255));
        jPanel9.setPreferredSize(new java.awt.Dimension(172, 60));
        jPanel9.setLayout(new java.awt.GridBagLayout());

        cajaColiformesTotales.setBackground(new java.awt.Color(255, 255, 255));
        cajaColiformesTotales.setBorder(null);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 60;
        gridBagConstraints.ipady = 40;
        jPanel9.add(cajaColiformesTotales, gridBagConstraints);

        etiquetaUnidadColiformesTotales.setText("UFC/g");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        jPanel9.add(etiquetaUnidadColiformesTotales, gridBagConstraints);

        etiquetaMenorColiformesTotales.setText("MENOR A 10 UFC/g");
        jPanel9.add(etiquetaMenorColiformesTotales, new java.awt.GridBagConstraints());
        etiquetaMenorColiformesTotales.setVisible(false);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        jPanel1.add(jPanel9, gridBagConstraints);

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));
        jPanel13.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        jPanel13.setForeground(new java.awt.Color(255, 255, 255));
        jPanel13.setPreferredSize(new java.awt.Dimension(172, 60));
        jPanel13.setLayout(new java.awt.GridBagLayout());

        cajaColiformesFecales.setBackground(new java.awt.Color(255, 255, 255));
        cajaColiformesFecales.setBorder(null);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 60;
        gridBagConstraints.ipady = 40;
        jPanel13.add(cajaColiformesFecales, gridBagConstraints);

        etiquetaUnidadColiformesFecales.setText("UFC/g");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        jPanel13.add(etiquetaUnidadColiformesFecales, gridBagConstraints);

        etiquetaMenorColiformesFecales.setText("MENOR A 1 UFC/g");
        jPanel13.add(etiquetaMenorColiformesFecales, new java.awt.GridBagConstraints());
        etiquetaMenorColiformesFecales.setVisible(false);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 5;
        jPanel1.add(jPanel13, gridBagConstraints);

        jPanel14.setBackground(new java.awt.Color(255, 255, 255));
        jPanel14.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        jPanel14.setForeground(new java.awt.Color(255, 255, 255));
        jPanel14.setPreferredSize(new java.awt.Dimension(172, 60));
        jPanel14.setLayout(new java.awt.GridBagLayout());

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
        jPanel14.add(cajaMoho, gridBagConstraints);

        etiquetaUnidadMohos.setText("UFC/g");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        jPanel14.add(etiquetaUnidadMohos, gridBagConstraints);

        etiquetaMenorMohos.setText("MENOR A 10 UFC/g");
        jPanel14.add(etiquetaMenorMohos, new java.awt.GridBagConstraints());
        etiquetaMenorMohos.setVisible(false);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 7;
        jPanel1.add(jPanel14, gridBagConstraints);

        checkGermenes.setText("MENOR A");
        checkGermenes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkGermenesActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        jPanel1.add(checkGermenes, gridBagConstraints);

        checkColiformesTotales.setText("MENOR A");
        checkColiformesTotales.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkColiformesTotalesActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        jPanel1.add(checkColiformesTotales, gridBagConstraints);

        checkColiformesFecales.setText("MENOR A");
        checkColiformesFecales.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkColiformesFecalesActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        jPanel1.add(checkColiformesFecales, gridBagConstraints);

        checkMohos.setText("MENOR A");
        checkMohos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkMohosActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        jPanel1.add(checkMohos, gridBagConstraints);

        cajaFechaAnalisis.setDateFormatString("dd-MM-yyyy");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(20, 0, 0, 0);
        jPanel1.add(cajaFechaAnalisis, gridBagConstraints);

        jLabel3.setText("Fecha de análisis:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(20, 0, 0, 20);
        jPanel1.add(jLabel3, gridBagConstraints);

        panelSalmonella.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelSalmonella.setFocusable(false);
        panelSalmonella.setPreferredSize(new java.awt.Dimension(150, 10));
        panelSalmonella.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                panelSalmonellaMousePressed(evt);
            }
        });
        panelSalmonella.setLayout(new java.awt.GridBagLayout());

        etiquetaSalmonella.setBackground(new java.awt.Color(0, 0, 0));
        etiquetaSalmonella.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        etiquetaSalmonella.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        etiquetaSalmonella.setText("<html>SALMONELA sp</html>");
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
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        panelSalmonella.add(etiquetaSalmonella, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.ipady = 50;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        jPanel1.add(panelSalmonella, gridBagConstraints);

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
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 20);
        jPanel1.add(jScrollPane13, gridBagConstraints);

        jPanel11.setFocusable(false);
        jPanel11.setLayout(new java.awt.GridBagLayout());

        comboSalmonella.setBackground(new java.awt.Color(204, 204, 204));
        comboSalmonella.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ausencia", "Presencia" }));
        comboSalmonella.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        comboSalmonella.setPreferredSize(new java.awt.Dimension(80, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 40;
        gridBagConstraints.ipady = 40;
        jPanel11.add(comboSalmonella, gridBagConstraints);
        comboSalmonella.setUI(new BasicComboBoxUI() {
            protected JButton createArrowButton() {
                return new JButton() {
                    public int getWidth() {
                        return 0;
                    }
                };
            }
        });

        comboUnidadSalmonella.setBackground(new java.awt.Color(204, 204, 204));
        comboUnidadSalmonella.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "en 10 g", "en 25 g" }));
        comboUnidadSalmonella.setSelectedIndex(1);
        comboUnidadSalmonella.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 1, 1, new java.awt.Color(0, 0, 0)));
        comboUnidadSalmonella.setMaximumSize(new java.awt.Dimension(52, 60));
        comboUnidadSalmonella.setMinimumSize(new java.awt.Dimension(52, 60));
        comboUnidadSalmonella.setPreferredSize(new java.awt.Dimension(52, 60));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        jPanel11.add(comboUnidadSalmonella, gridBagConstraints);
        comboUnidadSalmonella.setUI(new BasicComboBoxUI() {
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
        gridBagConstraints.gridy = 8;
        jPanel1.add(jPanel11, gridBagConstraints);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 899, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 602, Short.MAX_VALUE)
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
        m.put("idmuestras", id);
        if (checkGermenes.isSelected()) {
            m.put("germenes", etiquetaMenorGermenes.getText());
        } else if (!activarGermenes) {
            m.put("germenes", "-2");
        } else {
            System.out.println("etiquetaUnidadGermenes.getText() = " + etiquetaUnidadGermenes.getText());
            m.put("germenes", cajaGermenes.getText() + " " + etiquetaUnidadGermenes.getText());
        }
        if (checkColiformesTotales.isSelected()) {
            m.put("coliformesTotales", etiquetaMenorColiformesTotales.getText());
        } else if (!activarTotales) {
            m.put("coliformesTotales", "-2");
        } else {
            m.put("coliformesTotales", cajaColiformesTotales.getText() + " " + etiquetaUnidadColiformesTotales.getText());

        }
        if (checkColiformesFecales.isSelected()) {
            m.put("coliformesFecales", etiquetaMenorColiformesFecales.getText());
        } else if (!activarFecales) {
            m.put("coliformesFecales", "-2");
        } else {
            m.put("coliformesFecales", cajaColiformesFecales.getText() + " " + etiquetaUnidadColiformesFecales.getText());

        }
        if (!activarMohos) {
            m.put("mohos", -2);
        } else if (checkMohos.isSelected()) {
            m.put("mohos", etiquetaMenorMohos.getText());
        } else {
            m.put("mohos", cajaMoho.getText() + " " + etiquetaUnidadMohos.getText());

        }

        if (activarEscherichia) {
            m.put("escherichia", String.valueOf(comboEscherichia.getSelectedItem()).toUpperCase() + " " + etiquetaComboEscherichia.getText().substring(0, etiquetaComboEscherichia.getText().length() - 1).toUpperCase() + "g");
        } else {
            m.put("escherichia", "-2");
        }

        if (activarSalmonella) {
            m.put("salmonella", String.valueOf(comboSalmonella.getSelectedItem()).toUpperCase() + " " + comboUnidadSalmonella.getSelectedItem().toString().substring(0, comboUnidadSalmonella.getSelectedItem().toString().length() - 1).toUpperCase() + "g");
        } else {
            m.put("salmonella", "-2");
        }
        String observaciones = "";
        m.put("procedencia", procedencia);
        m.put("conclusion", crearConclusion());
        try {
            java.util.Date fm = cajaFechaAnalisis.getDate(); //obtener fecha
            Long dm = fm.getTime(); //sacar timepo
            java.sql.Date fechaAnalisis = new java.sql.Date(dm); //cast de fecha
            m.put("fechaAnalisis", fechaAnalisis);
            c.guardarFechaAnalisis(m);
            if (editar) {
                observaciones = JOptionPane.showInputDialog("Digite la observación:", auxObservaciones);
            } else {
                observaciones = JOptionPane.showInputDialog("Digite la observación:");
            }
            observaciones = observaciones.isBlank() ? "" : observaciones.trim().endsWith(".") ? observaciones : observaciones + ".";
            m.put("observaciones", observaciones);
            if (editar) {
                File rv = new File(c.recuperarRutas("Reportes") + "\\" + pdf);
                File rn = new File(c.recuperarRutas("Reportes") + "\\(BORRADO) " + pdf);
                rv.renameTo(rn);
                if (c.editarResultadoMBChocolates(m)) {
                    c.guardarObservaciones(observaciones, id);
                    c.guardarConclusion(String.valueOf(m.get("conclusion")), id);
                    this.dispose();
                    c.generarReporteMBChocolates(id, procedencia);
                }
            } else {
                if (c.guardarResultadoMBChocolates(m)) {
                    c.guardarObservaciones(observaciones, id);
                    c.guardarConclusion(String.valueOf(m.get("conclusion")), id);
                    this.dispose();
                    c.generarReporteMBChocolates(id, procedencia);
                }
            }
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(null, "Ingrese la fecha de análisis.");
        }
    }//GEN-LAST:event_botonGenerarActionPerformed

    private void checkGermenesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkGermenesActionPerformed
        if (checkGermenes.isSelected()) {
            cajaGermenes.setVisible(false);
            etiquetaUnidadGermenes.setVisible(false);
            etiquetaMenorGermenes.setVisible(true);
        } else {
            cajaGermenes.setVisible(true);
            etiquetaUnidadGermenes.setVisible(true);
            etiquetaMenorGermenes.setVisible(false);
        }
    }//GEN-LAST:event_checkGermenesActionPerformed

    private void checkMohosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkMohosActionPerformed
        if (checkMohos.isSelected()) {
            cajaMoho.setVisible(false);
            etiquetaUnidadMohos.setVisible(false);
            etiquetaMenorMohos.setVisible(true);
        } else {
            cajaMoho.setVisible(true);
            etiquetaUnidadMohos.setVisible(true);
            etiquetaMenorMohos.setVisible(false);
        }
    }//GEN-LAST:event_checkMohosActionPerformed

    private void checkColiformesTotalesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkColiformesTotalesActionPerformed
        if (checkColiformesTotales.isSelected()) {
            cajaColiformesTotales.setVisible(false);
            etiquetaUnidadColiformesTotales.setVisible(false);
            etiquetaMenorColiformesTotales.setVisible(true);

        } else {
            cajaColiformesTotales.setVisible(true);
            etiquetaUnidadColiformesTotales.setVisible(true);
            etiquetaMenorColiformesTotales.setVisible(false);
        }
    }//GEN-LAST:event_checkColiformesTotalesActionPerformed

    private void cajaMohoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cajaMohoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cajaMohoActionPerformed

    private void checkColiformesFecalesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkColiformesFecalesActionPerformed
        if (checkColiformesFecales.isSelected()) {
            cajaColiformesFecales.setVisible(false);
            etiquetaUnidadColiformesFecales.setVisible(false);
            etiquetaMenorColiformesFecales.setVisible(true);
        } else {
            cajaColiformesFecales.setVisible(true);
            etiquetaUnidadColiformesFecales.setVisible(true);
            etiquetaMenorColiformesFecales.setVisible(false);
        }
    }//GEN-LAST:event_checkColiformesFecalesActionPerformed

    private void etiquetaGermenesMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_etiquetaGermenesMousePressed
        activarGermenes = !activarGermenes;
        cajaGermenes.setEnabled(activarGermenes);
        area13.setEnabled(activarGermenes);
        etiquetaUnidadGermenes.setEnabled(activarGermenes);
        checkGermenes.setEnabled(activarGermenes);
        if (activarGermenes) {
            panelGermenes.setBackground(new Color(240, 240, 240));
        } else {
            panelGermenes.setBackground(new Color(240, 100, 100));
        }
    }//GEN-LAST:event_etiquetaGermenesMousePressed

    private void etiquetaTotalesMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_etiquetaTotalesMousePressed
        activarTotales = !activarTotales;
        cajaColiformesTotales.setEnabled(activarTotales);
        area23.setEnabled(activarTotales);
        etiquetaUnidadColiformesTotales.setEnabled(activarTotales);
        checkColiformesTotales.setEnabled(activarTotales);
        if (activarTotales) {
            panelTotales.setBackground(new Color(240, 240, 240));
        } else {
            panelTotales.setBackground(new Color(240, 100, 100));
        }
    }//GEN-LAST:event_etiquetaTotalesMousePressed

    private void etiquetaFecalesMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_etiquetaFecalesMousePressed
        activarFecales = !activarFecales;
        cajaColiformesFecales.setEnabled(activarFecales);
        area33.setEnabled(activarFecales);
        etiquetaUnidadColiformesFecales.setEnabled(activarFecales);
        checkColiformesFecales.setEnabled(activarFecales);
        if (activarFecales) {
            panelFecales.setBackground(new Color(240, 240, 240));
        } else {
            panelFecales.setBackground(new Color(240, 100, 100));
        }
    }//GEN-LAST:event_etiquetaFecalesMousePressed

    private void etiquetaEscherichiaMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_etiquetaEscherichiaMousePressed
        activarEscherichia = !activarEscherichia;
        area45.setEnabled(activarEscherichia);
        comboEscherichia.setEnabled(activarEscherichia);
        etiquetaComboEscherichia.setEnabled(activarEscherichia);
        if (activarEscherichia) {
            panelEscherichia.setBackground(new Color(240, 240, 240));
        } else {
            panelEscherichia.setBackground(new Color(240, 100, 100));
        }
    }//GEN-LAST:event_etiquetaEscherichiaMousePressed

    private void etiquetaMohoMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_etiquetaMohoMousePressed
        activarMohos = !activarMohos;
        cajaMoho.setEnabled(activarMohos);
        area43.setEnabled(activarMohos);
        etiquetaUnidadMohos.setEnabled(activarMohos);
        checkMohos.setEnabled(activarMohos);
        if (activarMohos) {
            panelMohos.setBackground(new Color(240, 240, 240));
        } else {
            panelMohos.setBackground(new Color(240, 100, 100));
        }
    }//GEN-LAST:event_etiquetaMohoMousePressed

    private void panelGermenesMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelGermenesMousePressed
        activarGermenes = !activarGermenes;
        cajaGermenes.setEnabled(activarGermenes);
        area13.setEnabled(activarGermenes);
        etiquetaUnidadGermenes.setEnabled(activarGermenes);
        checkGermenes.setEnabled(activarGermenes);
        if (activarGermenes) {
            panelGermenes.setBackground(new Color(240, 240, 240));
        } else {
            panelGermenes.setBackground(new Color(240, 100, 100));
        }
    }//GEN-LAST:event_panelGermenesMousePressed

    private void panelTotalesMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelTotalesMousePressed
        activarTotales = !activarTotales;
        cajaColiformesTotales.setEnabled(activarTotales);
        area23.setEnabled(activarTotales);
        etiquetaUnidadColiformesTotales.setEnabled(activarTotales);
        checkColiformesTotales.setEnabled(activarTotales);
        if (activarTotales) {
            panelTotales.setBackground(new Color(240, 240, 240));
        } else {
            panelTotales.setBackground(new Color(240, 100, 100));
        }
    }//GEN-LAST:event_panelTotalesMousePressed

    private void panelFecalesMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelFecalesMousePressed
        activarFecales = !activarFecales;
        cajaColiformesFecales.setEnabled(activarFecales);
        area33.setEnabled(activarFecales);
        etiquetaUnidadColiformesFecales.setEnabled(activarFecales);
        checkColiformesFecales.setEnabled(activarFecales);
        if (activarFecales) {
            panelFecales.setBackground(new Color(240, 240, 240));
        } else {
            panelFecales.setBackground(new Color(240, 100, 100));
        }
    }//GEN-LAST:event_panelFecalesMousePressed

    private void panelEscherichiaMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelEscherichiaMousePressed
        activarEscherichia = !activarEscherichia;
        area45.setEnabled(activarEscherichia);
        comboEscherichia.setEnabled(activarEscherichia);
        etiquetaComboEscherichia.setEnabled(activarEscherichia);
        if (activarEscherichia) {
            panelEscherichia.setBackground(new Color(240, 240, 240));
        } else {
            panelEscherichia.setBackground(new Color(240, 100, 100));
        }
    }//GEN-LAST:event_panelEscherichiaMousePressed

    private void panelMohosMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelMohosMousePressed
        activarMohos = !activarMohos;
        cajaMoho.setEnabled(activarMohos);
        area43.setEnabled(activarMohos);
        etiquetaUnidadMohos.setEnabled(activarMohos);
        checkMohos.setEnabled(activarMohos);
        if (activarMohos) {
            panelMohos.setBackground(new Color(240, 240, 240));
        } else {
            panelMohos.setBackground(new Color(240, 100, 100));
        }
    }//GEN-LAST:event_panelMohosMousePressed

    private void etiquetaSalmonellaMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_etiquetaSalmonellaMousePressed
        activarSalmonella = !activarSalmonella;
        comboSalmonella.setEnabled(activarSalmonella);
        area44.setEnabled(activarSalmonella);
        comboUnidadSalmonella.setEnabled(activarSalmonella);
        if (activarSalmonella) {
            panelSalmonella.setBackground(new Color(240, 240, 240));
        } else {
            panelSalmonella.setBackground(new Color(240, 100, 100));
        }
    }//GEN-LAST:event_etiquetaSalmonellaMousePressed

    private void panelSalmonellaMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelSalmonellaMousePressed
        activarSalmonella = !activarSalmonella;
        comboSalmonella.setEnabled(activarSalmonella);
        area44.setEnabled(activarSalmonella);
        comboUnidadSalmonella.setEnabled(activarSalmonella);
        if (activarSalmonella) {
            panelSalmonella.setBackground(new Color(240, 240, 240));
        } else {
            panelSalmonella.setBackground(new Color(240, 100, 100));
        }
    }//GEN-LAST:event_panelSalmonellaMousePressed

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
            java.util.logging.Logger.getLogger(TablaMBChocolates.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TablaMBChocolates.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TablaMBChocolates.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TablaMBChocolates.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
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
                TablaMBChocolates dialog = new TablaMBChocolates(new javax.swing.JFrame(), true, -1, null, false, null);
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
    private javax.swing.JButton botonGenerar;
    private javax.swing.JTextField cajaColiformesFecales;
    private javax.swing.JTextField cajaColiformesTotales;
    private com.toedter.calendar.JDateChooser cajaFechaAnalisis;
    private javax.swing.JTextField cajaGermenes;
    private javax.swing.JTextField cajaMoho;
    private javax.swing.JCheckBox checkColiformesFecales;
    private javax.swing.JCheckBox checkColiformesTotales;
    private javax.swing.JCheckBox checkGermenes;
    private javax.swing.JCheckBox checkMohos;
    private javax.swing.JComboBox<String> comboEscherichia;
    private javax.swing.JComboBox<String> comboSalmonella;
    private javax.swing.JComboBox<String> comboUnidadSalmonella;
    private javax.swing.JLabel etiquetaComboEscherichia;
    private javax.swing.JLabel etiquetaEscherichia;
    private javax.swing.JLabel etiquetaFecales;
    private javax.swing.JLabel etiquetaGermenes;
    private javax.swing.JLabel etiquetaMenorColiformesFecales;
    private javax.swing.JLabel etiquetaMenorColiformesTotales;
    private javax.swing.JLabel etiquetaMenorGermenes;
    private javax.swing.JLabel etiquetaMenorMohos;
    private javax.swing.JLabel etiquetaMoho;
    private javax.swing.JLabel etiquetaSalmonella;
    private javax.swing.JLabel etiquetaTitulo;
    private javax.swing.JLabel etiquetaTotales;
    private javax.swing.JLabel etiquetaUnidadColiformesFecales;
    private javax.swing.JLabel etiquetaUnidadColiformesTotales;
    private javax.swing.JLabel etiquetaUnidadGermenes;
    private javax.swing.JLabel etiquetaUnidadMohos;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JPanel panelEscherichia;
    private javax.swing.JPanel panelFecales;
    private javax.swing.JPanel panelGermenes;
    private javax.swing.JPanel panelMohos;
    private javax.swing.JPanel panelSalmonella;
    private javax.swing.JPanel panelTotales;
    // End of variables declaration//GEN-END:variables

    private String crearConclusion() {
        boolean germenes;
        if (checkGermenes.isSelected()) {
            germenes = true;
        } else if (activarGermenes) {
            double valorGermenesAnalisis = Double.parseDouble(cajaGermenes.getText());
            double valorGermenesNormal = 2.5 * Math.pow(10, 3);
            germenes = valorGermenesAnalisis <= valorGermenesNormal;
        } else {
            germenes = true;
        }

        boolean coliformesTotales;
        if (checkColiformesTotales.isSelected()) {
            coliformesTotales = true;
        } else if (activarTotales) {
            double valorColiformesTotalesAnalisis = Double.parseDouble(cajaColiformesTotales.getText());
            double valorColiformesTotalesNormal = 10;
            coliformesTotales = valorColiformesTotalesAnalisis < valorColiformesTotalesNormal;
        } else {
            coliformesTotales = true;
        }

        boolean coliformesFecales;
        if (checkColiformesFecales.isSelected()) {
            coliformesFecales = true;
        } else if (activarFecales) {
            double valorColiformesFecalesAnalisis = Double.parseDouble(cajaColiformesFecales.getText());
            double valorColiformesFecalesNormal = 10;
            coliformesFecales = valorColiformesFecalesAnalisis < valorColiformesFecalesNormal;
        } else {
            coliformesFecales = true;
        }

        boolean escherichia;
        if (activarEscherichia) {
            escherichia = comboEscherichia.getSelectedItem().toString().equals("Ausencia");
        } else {
            escherichia = true;
        }

        boolean mohos;
        if (checkMohos.isSelected()) {
            mohos = true;
        } else if (activarMohos) {
            double valorMohosAnalisis = Double.parseDouble(cajaMoho.getText());
            double valorMohosNormales = 100;
            mohos = valorMohosAnalisis < valorMohosNormales;
        } else {
            mohos = true;
        }
        
        boolean salmonella;
        if(activarSalmonella){
            salmonella = comboSalmonella.getSelectedItem().toString().equals("Ausencia");
        } else {
            salmonella = true;
        }
        String conclusion = "";
        if (germenes && coliformesTotales && coliformesFecales && escherichia && mohos && salmonella) {
            conclusion = "La muestra analizada cumple con los limites especificados por el elaborador.";
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
            if (!mohos) {
                conclusion += " Mohos y Levaduras,";
            }
            if (!salmonella){
                conclusion += " Salmonella sp,";
            }
            conclusion = conclusion.substring(0, conclusion.length() - 1);
            conclusion += " la muestra analizada no cumple con los limites especificados por el elaborador";
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
