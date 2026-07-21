package org.ignaciorodriguez.vista;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.Toolkit;
import java.io.File;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.ignaciorodriguez.modelo.Consultas;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.KeyStroke;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import org.ignaciorodriguez.modelo.Resultados;
import java.util.HashMap;
import java.util.List;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;

import org.ignaciorodriguez.modelo.Determinacion;
import org.ignaciorodriguez.modelo.DeterminacionHumedad;
import org.ignaciorodriguez.repository.MuestraRepository;

public class Determinaciones extends javax.swing.JDialog {

    int id, cont = 0;
    JPanel j;
    Consultas consultas = Consultas.getInstancia();
    boolean editar = false;
    int alimentos;
    String pdf, auxObservaciones, auxConclusion = "";
    MuestraRepository muestraRepository = new MuestraRepository();
    Frame parent;
    ButtonGroup bg;
    public List<Determinacion> determinaciones = new ArrayList();
    public final static int ALIMENTO = 0;
    public final static int AGUA = 1;
    public final static int GENERICO = 2;

    public Determinaciones() {
    }

    public Determinaciones(java.awt.Frame parent, boolean modal, List<Determinacion> determinacionesFQ, int id, boolean editar, String pdf, int alimentos) {
        super(parent, modal);
        this.parent = parent;
        initComponents();
        setTitle("ID. " + id + ". " + "Informe físico quimico " + (alimentos == 0 ? "de alimentos" : alimentos == 1 ? "de agua" : "genérico") + ". " + muestraRepository.obtenerProcedencia(id));
        bg = new ButtonGroup();
        bg.add(checkConclusion);
        bg.add(checkConclusionPersonalizada);
        bg.add(checkSinConclusion);
        checkSinConclusion.setSelected(true);
        this.id = id;
        this.editar = editar;
        this.pdf = pdf;
        this.alimentos = alimentos;
        this.setIconImage(new ImageIcon("src\\vista\\icono.png").getImage());
        inicializarLista(determinacionesFQ);
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridy = cont + 1;
        gridBagConstraints.gridx = 0;
        jPanel1.add(jPanel67, gridBagConstraints);
        int width = 500;
        int height = jPanel1.getPreferredSize().height > (int) (Toolkit.getDefaultToolkit().getScreenSize().height * 0.7) ? (int) (Toolkit.getDefaultToolkit().getScreenSize().height * 0.7) : jPanel1.getPreferredSize().height + 10;
        jScrollPane1.setPreferredSize(new Dimension(width, height));
        jScrollPane1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        if (this.editar) {
            cajaFechaAnalisis.setDate(consultas.recuperarFechaAnalisis(id).toString().equals("1111-11-11") ? null : consultas.recuperarFechaAnalisis(id));
            consultas.recuperarDatosDeterminaciones(determinaciones, id);
            determinaciones.forEach(Determinacion::llenarCampos);
            auxConclusion += muestraRepository.recuperarConclusion(id);
            String oracion = auxConclusion.replaceAll("\\b\\d+\\b", "NUMERO");
            if (auxConclusion.equals("-") || auxConclusion.equals("-.")) {
                auxConclusion = "";
            }
            if (auxConclusion.isBlank()) {
                checkSinConclusion.setSelected(true);
            } else if (oracion.equals("En los parámetros analizados la muestra cumple con el art NUMERO del Código Alimentario Argentino, (Ley NUMERO)")) {
                int art = auxConclusion.indexOf("art ") + 4;
                int despuesNumero = auxConclusion.indexOf(" ", art);
                auxConclusion = auxConclusion.substring(art, despuesNumero);
                checkConclusion.setSelected(true);
            } else {
                checkConclusionPersonalizada.setSelected(true);
            }
            consultas.recuperarMetodosDeterminaciones(determinaciones, id);
            String auxiliarEtiqueta = "";

        }
        this.pack();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jScrollPane1 = new javax.swing.JScrollPane(jPanel1);
        jPanel1 = new javax.swing.JPanel();
        jPanel67 = new javax.swing.JPanel();
        botonContinuar = new javax.swing.JButton();
        cajaFechaAnalisis = new com.toedter.calendar.JDateChooser();
        jLabel1 = new javax.swing.JLabel();
        jPanel84 = new javax.swing.JPanel();
        checkConclusionPersonalizada = new javax.swing.JCheckBox();
        checkConclusion = new javax.swing.JCheckBox();
        checkSinConclusion = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setSize(new java.awt.Dimension(1000, 500));

        jScrollPane1.getVerticalScrollBar().setUnitIncrement(16);
        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        jPanel1.setLayout(new java.awt.GridBagLayout());

        jPanel67.setPreferredSize(new java.awt.Dimension(500, 100));
        jPanel67.setLayout(new java.awt.GridBagLayout());

        botonContinuar.setText("Generar main.resources.reporte");
        botonContinuar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonContinuarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 20, 0);
        jPanel67.add(botonContinuar, gridBagConstraints);
        InputMap im = jPanel1.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false), "Apretar");

        ActionMap ap = jPanel1.getActionMap();
        ap.put("Apretar", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e){
                botonContinuar.doClick();
            }
        });

        cajaFechaAnalisis.setDateFormatString("dd-MM-yyyy");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 30;
        gridBagConstraints.insets = new java.awt.Insets(10, 20, 10, 0);
        jPanel67.add(cajaFechaAnalisis, gridBagConstraints);
        long millis=System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(millis);
        cajaFechaAnalisis.setDate(date);

        jLabel1.setText("Fecha de análisis:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 10, 0);
        jPanel67.add(jLabel1, gridBagConstraints);

        jPanel84.setLayout(new java.awt.GridBagLayout());

        checkConclusionPersonalizada.setText("Agregar conclusión (Manual)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jPanel84.add(checkConclusionPersonalizada, gridBagConstraints);

        checkConclusion.setText("Agregar conclusión (Artículo)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        jPanel84.add(checkConclusion, gridBagConstraints);

        checkSinConclusion.setText("Sin conclusion");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        jPanel84.add(checkSinConclusion, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        jPanel67.add(jPanel84, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 160;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanel1.add(jPanel67, gridBagConstraints);
        jPanel67.getAccessibleContext().setAccessibleParent(jScrollPane1);

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
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );

        pack();
    }

    private void botonContinuarActionPerformed(java.awt.event.ActionEvent evt) {
        Resultados r = new Resultados();
        java.util.Date fm = null;
        Long dm = null;
        try {
            fm = cajaFechaAnalisis.getDate();
            dm = fm.getTime();
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(null, "Ingrese una fecha de analisis para continuar");
        }
        java.sql.Date fechaAnalisis = new java.sql.Date(dm);
        r.setFechaAnalisis(fechaAnalisis);
        determinaciones.forEach(determinacion -> {
            determinacion.guardarResultado();
        });
        String observaciones = "";
        determinaciones.forEach(d -> d.guardarMetodo());
        consultas.recuperarFQAguaCompleto(id, determinaciones);
        String conclusion = "";
        if (editar) {

            if (checkConclusionPersonalizada.isSelected()) {
                JTextArea ta = new JTextArea(10, 10);
                ta.setFont(new Font("Segoe UI", Font.PLAIN, 12));
                ta.setLineWrap(true);
                ta.setWrapStyleWord(true);
                ta.append(auxConclusion);
                switch (JOptionPane.showConfirmDialog(null, new JScrollPane(ta), "Ingrese la conclusión:", JOptionPane.OK_OPTION)) {
                    case JOptionPane.OK_OPTION:
                        conclusion = ta.getText();
                        break;
                }
            } else if (checkConclusion.isSelected()) {
                conclusion = crearConclusion();
            } else {
                conclusion = "";
            }
            conclusion = conclusion.isBlank() ? "" : conclusion.equals("-") ? "" : conclusion.trim().endsWith(".") ? conclusion : conclusion + ".";
            muestraRepository.guardarConclusion(conclusion, id);
            File rv = new File(consultas.recuperarRutas("Reportes") + "\\" + pdf);
            File rn = new File(consultas.recuperarRutas("Reportes") + "\\(BORRADO) " + pdf);
            rv.renameTo(rn);
            observaciones = JOptionPane.showInputDialog("Digite la observación:", auxObservaciones);
            observaciones = observaciones.isBlank() ? "" : observaciones.trim().endsWith(".") ? observaciones : observaciones + ".";
            consultas.borrarDeterminaciones(id);
            if (consultas.guardarDeterminaciones(determinaciones, id)) {
                muestraRepository.guardarObservaciones(observaciones, id);
                muestraRepository.guardarFechaAnalisis(r, id);
                this.dispose();
                if (alimentos == Determinaciones.ALIMENTO) {
                    consultas.generarReporteFQCompleto(id, "ANÁLISIS FÍSICO QUÍMICO DE ALIMENTOS", determinaciones);
                } else if (alimentos == Determinaciones.AGUA) {
                    consultas.generarReporteFQCompleto(id, "ANÁLISIS FÍSICO QUÍMICO DE AGUA", determinaciones);
                } else {
                    consultas.generarReporteFQCompleto(id, "ANÁLISIS FÍSICO QUÍMICO", determinaciones);
                }
            }

        } else {
            if (checkConclusion.isSelected()) {
                conclusion = crearConclusion();
            } else if (checkConclusionPersonalizada.isSelected()) {
                JTextArea ta = new JTextArea(10, 10);
                ta.setFont(new Font("Segoe UI", Font.PLAIN, 12));
                ta.setLineWrap(true);
                ta.setWrapStyleWord(true);
                ta.append(auxConclusion);
                switch (JOptionPane.showConfirmDialog(null, new JScrollPane(ta), "Ingrese la conclusión:", JOptionPane.OK_OPTION)) {
                    case JOptionPane.OK_OPTION:
                        conclusion = ta.getText();
                        break;
                }
            } else {
                conclusion = "";
            }
            muestraRepository.guardarConclusion(conclusion, id);
            observaciones = JOptionPane.showInputDialog("Digite la observación:");
            if (consultas.guardarDeterminaciones(determinaciones, id)) {
                muestraRepository.guardarObservaciones(observaciones, id);
                muestraRepository.guardarFechaAnalisis(r, id);
                this.dispose();
                if (alimentos == Determinaciones.ALIMENTO) {
                    consultas.generarReporteFQCompleto(id, "ANÁLISIS FÍSICO QUÍMICO DE ALIMENTOS", determinaciones);
                } else if (alimentos == Determinaciones.AGUA) {
                    consultas.generarReporteFQCompleto(id, "ANÁLISIS FÍSICO QUÍMICO DE AGUA", determinaciones);
                } else {
                    consultas.generarReporteFQCompleto(id, "ANÁLISIS FÍSICO QUÍMICO", determinaciones);
                }
            }
        }
        this.dispose();
    }

    private javax.swing.JButton botonContinuar;
    private com.toedter.calendar.JDateChooser cajaFechaAnalisis;
    private javax.swing.JCheckBox checkConclusion;
    private javax.swing.JCheckBox checkConclusionPersonalizada;
    private javax.swing.JCheckBox checkSinConclusion;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel67;
    private javax.swing.JPanel jPanel84;
    private javax.swing.JScrollPane jScrollPane1;


    public String crearConclusion() {
        String articulo = JOptionPane.showInputDialog("Número de artículo:", auxConclusion);
        String conclusion = "En los parámetros analizados la muestra cumple con "
                + "el art " + articulo + " del Código Alimentario Argentino, (Ley 18284)";
        return conclusion;
    }

    public void apretarBoton() {
        auxObservaciones = muestraRepository.recuperarObservaciones(id);
        Resultados r = new Resultados();
        java.util.Date fm = cajaFechaAnalisis.getDate();
        Long dm = fm.getTime();
        java.sql.Date fechaAnalisis = new java.sql.Date(dm);
        r.setFechaAnalisis(fechaAnalisis);
        consultas.recuperarDatosDeterminacionesGenerar(determinaciones, id);
        consultas.recuperarMetodosDeterminaciones(determinaciones, id);

        if (alimentos == Determinaciones.ALIMENTO) {
            consultas.generarReporteFQCompleto(id, "ANÁLISIS FÍSICO QUÍMICO DE ALIMENTOS", determinaciones);
        } else if (alimentos == Determinaciones.AGUA) {
            consultas.generarReporteFQCompleto(id, "ANÁLISIS FÍSICO QUÍMICO DE AGUA", determinaciones);
        } else {
            consultas.generarReporteFQCompleto(id, "ANÁLISIS FÍSICO QUÍMICO", determinaciones);
        }

    }

    public void inicializarLista(List<Determinacion> determinacionesFQ) {
        determinacionesFQ.forEach(d -> {
            agregarDeterminacion(d);
        });
    }

    private void agregarDeterminacion(Determinacion d) {
        if (d instanceof DeterminacionHumedad) {
            HashMap<String, Component> hash = new HashMap<String, Component>();
            GridBagConstraints gridBagConstraints;
            JPanel panel = new JPanel();

            panel.setPreferredSize(new java.awt.Dimension(500, 100));
            panel.setLayout(new java.awt.GridBagLayout());

            JLabel etiqueta = new JLabel();
            etiqueta.setText("Humedad");

            JPanel jPanel13 = new JPanel();
            jPanel13.setBackground(new java.awt.Color(255, 255, 255));
            jPanel13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(187, 187, 187)));
            jPanel13.setLayout(new java.awt.GridBagLayout());

            JTextField campo = new JTextField();
            campo.setBorder(null);
            campo.setMaximumSize(new java.awt.Dimension(100, 20));
            campo.setMinimumSize(new java.awt.Dimension(100, 20));
            campo.setPreferredSize(new java.awt.Dimension(100, 20));
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 0;
            gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
            jPanel13.add(campo, gridBagConstraints);
            hash.put("campo", campo);

            JLabel etiquetaUnidad = new JLabel();
            etiquetaUnidad.setText("mg/l para una pieza de ");
            etiquetaUnidad.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    String aux = etiquetaUnidad.getText().substring(0, etiquetaUnidad.getText().length() - 19);
                    for (int i = 0; i < d.getUnidades().size(); i++) {
                        if (aux.equals(d.getUnidades().get(i))) {
                            if (i == 4) {
                                etiquetaUnidad.setText(d.getUnidades().get(0) + " para una pieza de ");
                            } else {
                                etiquetaUnidad.setText(d.getUnidades().get(i + 1) + " para una pieza de ");
                            }
                        }
                    }
                }

            });
            etiquetaUnidad.setOpaque(true);
            etiquetaUnidad.setBackground(Color.LIGHT_GRAY);
            hash.put("etiquetaPieza", etiquetaUnidad);

            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 1;
            gridBagConstraints.gridy = 0;
            jPanel13.add(etiquetaUnidad, gridBagConstraints);

            JLabel etiquetaUnidadPieza = new JLabel();
            etiquetaUnidadPieza.setBackground(new java.awt.Color(255, 255, 255));
            etiquetaUnidadPieza.setText(" g");
            etiquetaUnidadPieza.setOpaque(true);
            etiquetaUnidadPieza.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mousePressed(java.awt.event.MouseEvent evt) {
                    String aux = etiquetaUnidadPieza.getText();
                    for (int i = 0; i < d.getUnidades().size(); i++) {
                        if (aux.equals(d.getUnidades().get(i))) {
                            if (i == 4) {
                                etiquetaUnidadPieza.setText(d.getUnidades().get(0) + " para una pieza de ");
                            } else {
                                etiquetaUnidadPieza.setText(d.getUnidades().get(i + 1) + " para una pieza de ");
                            }
                        }
                    }
                }
            }
            );
            hash.put("etiquetaUnidadPieza", etiquetaUnidadPieza);
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 3;
            gridBagConstraints.gridy = 0;
            gridBagConstraints.ipady = 6;

            jPanel13.add(etiquetaUnidadPieza, gridBagConstraints);

            JTextField campoHumedadPieza = new JTextField();
            campoHumedadPieza.setHorizontalAlignment(javax.swing.JTextField.CENTER);
            campoHumedadPieza.setMaximumSize(new java.awt.Dimension(100, 20));
            campoHumedadPieza.setMinimumSize(new java.awt.Dimension(100, 20));
            campoHumedadPieza.setPreferredSize(new java.awt.Dimension(100, 20));

            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 2;
            gridBagConstraints.gridy = 0;
            gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
            jPanel13.add(campoHumedadPieza, gridBagConstraints);
            hash.put("campoPieza", campoHumedadPieza);

            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 1;
            gridBagConstraints.gridy = 0;
            gridBagConstraints.ipadx = 4;
            gridBagConstraints.ipady = 8;
            gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;

            panel.add(jPanel13, gridBagConstraints);
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = cont++;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
            jPanel1.add(panel, gridBagConstraints);
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 0;
            gridBagConstraints.ipadx = 61;
            gridBagConstraints.ipady = 83;
            gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
            gridBagConstraints.insets = new java.awt.Insets(1, 6, 0, 0);
            etiqueta.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    boolean activarPieza = ((DeterminacionHumedad) d).isActivarPieza();
                    if (activarPieza) {
                        campoHumedadPieza.setEnabled(false);
                        campoHumedadPieza.setBackground(new Color(255, 210, 210));
                        etiquetaUnidad.setBackground(new Color(255, 210, 210));
                        etiquetaUnidadPieza.setBackground(new Color(255, 210, 210));
                    } else {
                        campoHumedadPieza.setEnabled(true);
                        campoHumedadPieza.setBackground(new Color(255, 255, 255));
                        etiquetaUnidad.setBackground(Color.LIGHT_GRAY);
                        etiquetaUnidadPieza.setBackground(new Color(255, 255, 255));
                    }
                        ((DeterminacionHumedad) d).setActivarPieza(!activarPieza);
                        System.out.println("((DeterminacionHumedad) d).isActivarPieza() = " + ((DeterminacionHumedad) d).isActivarPieza());
                }

            });
            panel.add(etiqueta, gridBagConstraints);
            hash.put("etiqueta", etiqueta);
            JLabel jLabel2 = new JLabel();

            jLabel2.setFont(
                    new java.awt.Font("Segoe UI", 0, 10));
            jLabel2.setText(
                    "<html>*Para desactivar los gramos de la pieza apretar en el nombre de la determinacion.</html>");
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 0;
            gridBagConstraints.gridwidth = 3;
            gridBagConstraints.ipadx = 400;
            gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTH;

            panel.add(jLabel2, gridBagConstraints);
            d.setComponentes(hash);
            determinaciones.add(d);
        } else {
            HashMap<String, Component> hash = new HashMap<String, Component>();
            GridBagConstraints gridBagConstraints;
            JPanel panel = new JPanel();
            panel.setPreferredSize(new java.awt.Dimension(500, 100));
            panel.setLayout(new java.awt.GridBagLayout());

            JLabel etiqueta = new JLabel();
            etiqueta.setText(d.getNombre());
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 0;
            gridBagConstraints.ipadx = 66;
            gridBagConstraints.ipady = 83;
            gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
            gridBagConstraints.insets = new java.awt.Insets(1, 6, 0, 0);
            panel.add(etiqueta, gridBagConstraints);
            hash.put("etiqueta", etiqueta);

            JPanel panelCampo = new JPanel();
            panelCampo.setBackground(new java.awt.Color(255, 255, 255));
            panelCampo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(187, 187, 187)));
            panelCampo.setLayout(new java.awt.GridBagLayout());

            JTextField campo = new JTextField();
            campo.setBorder(null);
            campo.setMaximumSize(new java.awt.Dimension(200, 20));
            campo.setMinimumSize(new java.awt.Dimension(200, 20));
            campo.setPreferredSize(new java.awt.Dimension(200, 20));
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 0;
            gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
            panelCampo.add(campo, gridBagConstraints);
            hash.put("campo", campo);

            JLabel etiquetaUnidad = new JLabel();
            etiquetaUnidad.setText(d.getUnidadDefault());
            etiquetaUnidad.setMaximumSize(new java.awt.Dimension(36, 16));
            etiquetaUnidad.setMinimumSize(new java.awt.Dimension(36, 16));
            etiquetaUnidad.setPreferredSize(new java.awt.Dimension(36, 16));
            etiquetaUnidad.setOpaque(true);
            etiquetaUnidad.setBackground(Color.LIGHT_GRAY);
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 1;
            gridBagConstraints.gridy = 0;
            panelCampo.add(etiquetaUnidad, gridBagConstraints);
            hash.put("etiquetaUnidad", etiquetaUnidad);

            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 1;
            gridBagConstraints.gridy = 0;
            gridBagConstraints.ipadx = 4;
            gridBagConstraints.ipady = 8;
            gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
            panel.add(panelCampo, gridBagConstraints);

            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = cont++;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
            jPanel1.add(panel, gridBagConstraints);
            panel.setVisible(true);

            d.setComponentes(hash);

            etiquetaUnidad.addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent evt) {
                    String aux = etiquetaUnidad.getText();
                    int idx = d.getUnidades().indexOf(aux);
                    try {
                        etiquetaUnidad.setText(d.getUnidades().get(idx + 1));
                    } catch (java.lang.IndexOutOfBoundsException e) {
                        etiquetaUnidad.setText(d.getUnidades().get(0));
                    }
                }
            });
            determinaciones.add(d);
        }
    }

}
