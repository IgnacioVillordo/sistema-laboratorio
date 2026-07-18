package org.ignaciorodriguez.vista;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.ignaciorodriguez.modelo.Determinacion;
import org.ignaciorodriguez.modelo.DeterminacionHumedad;

public class PruebaDeterminacion extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(PruebaDeterminacion.class.getName());
    int cont = 0;
    List<Determinacion> lista = new ArrayList<>();

    public PruebaDeterminacion() {
        initComponents();
        JButton b = new JButton();
        jPanel1.add(b);
        int width = (int) (Toolkit.getDefaultToolkit().getScreenSize().width * 0.7);
        int height = (int) (Toolkit.getDefaultToolkit().getScreenSize().height * 0.7);
        llenarPanel();
        jScrollPane1.setSize(width, height);
        jScrollPane1.setPreferredSize(new Dimension(width, height));
        jScrollPane1.setMinimumSize(new Dimension(width, height));
        jScrollPane1.setMaximumSize(new Dimension(width, height));
        this.setSize(width, height);
        this.setPreferredSize(new Dimension(width, height));
        this.setMinimumSize(new Dimension(width, height));
        this.setMaximumSize(new Dimension(width, height));
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lista.forEach((d) -> {
                    d.guardarResultado();
                    System.out.println(d.getResultado());
                });
//                d.obtenerResultado();
//                System.out.println(d.getResultado());
            }
        });

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(new java.awt.GridBagLayout());
        jScrollPane1.setViewportView(jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 16, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 16, Short.MAX_VALUE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new PruebaDeterminacion().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables

    private void agregarDeterminacion(Determinacion d) {
        HashMap<String, Component> hash = new HashMap<String, Component>();
        GridBagConstraints gridBagConstraints;
        JPanel panel = new JPanel();
        panel.setMaximumSize(new java.awt.Dimension(500, 100));
        panel.setMinimumSize(new java.awt.Dimension(500, 100));
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
        jPanel1.setSize(jPanel1.getSize().width,jPanel1.getSize().height + 100);
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
        lista.add(d);
    }

    private void agregarDeterminacionHumedad(Determinacion d) {
        HashMap<String, Component> hash = new HashMap<String, Component>();
        GridBagConstraints gridBagConstraints;
        JPanel panel = new JPanel();

        panel.setMaximumSize(new java.awt.Dimension(600, 100));
        panel.setMinimumSize(new java.awt.Dimension(600, 100));
        panel.setPreferredSize(new java.awt.Dimension(600, 100));
        panel.setLayout(new java.awt.GridBagLayout());

        JLabel etiqueta = new JLabel();
        etiqueta.setText("Humedad");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 61;
        gridBagConstraints.ipady = 83;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(1, 6, 0, 0);
        panel.add(etiqueta, gridBagConstraints);

        JPanel jPanel13 = new JPanel();
        jPanel13.setBackground(new java.awt.Color(255, 255, 255));
        jPanel13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(187, 187, 187)));
        jPanel13.setLayout(new java.awt.GridBagLayout());

        JTextField campoHumedad = new JTextField();
        campoHumedad.setBorder(null);
        campoHumedad.setMaximumSize(new java.awt.Dimension(100, 20));
        campoHumedad.setMinimumSize(new java.awt.Dimension(100, 20));
        campoHumedad.setPreferredSize(new java.awt.Dimension(100, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel13.add(campoHumedad, gridBagConstraints);

        JLabel etiquetaUnidadHumedad = new JLabel();
        etiquetaUnidadHumedad.setText("mg/l para una pieza de ");

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        jPanel13.add(etiquetaUnidadHumedad, gridBagConstraints);

        JTextField campoHumedadPieza = new JTextField();
        campoHumedadPieza.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        campoHumedadPieza.setMaximumSize(new java.awt.Dimension(100, 20));
        campoHumedadPieza.setMinimumSize(new java.awt.Dimension(100, 20));
        campoHumedadPieza.setPreferredSize(new java.awt.Dimension(100, 20));
//        campoHumedadPieza.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                campoHumedadPiezaActionPerformed(evt);
//            }
//        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel13.add(campoHumedadPieza, gridBagConstraints);

        JLabel etiquetaUnidadHumedadPieza = new JLabel();
        etiquetaUnidadHumedadPieza.setBackground(new java.awt.Color(255, 255, 255));
        etiquetaUnidadHumedadPieza.setText(" g");
        etiquetaUnidadHumedadPieza.setOpaque(true);
        etiquetaUnidadHumedadPieza.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                String aux = etiquetaUnidadHumedadPieza.getText();
                for (int i = 0; i < d.getUnidades().size(); i++) {
                    if (aux.equals(d.getUnidades().get(i))) {
                        if (i == 4) {
                            etiquetaUnidadHumedadPieza.setText(d.getUnidades().get(0) + " para una pieza de ");
                        } else {
                            etiquetaUnidadHumedadPieza.setText(d.getUnidades().get(i + 1) + " para una pieza de ");
                        }
                    }
                }
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipady = 6;

        jPanel13.add(etiquetaUnidadHumedadPieza, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 4;
        gridBagConstraints.ipady = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;

        panel.add(jPanel13, gridBagConstraints);

        JLabel jLabel2 = new JLabel();

        jLabel2.setFont(
                new java.awt.Font("Segoe UI", 0, 10));
        jLabel2.setText(
                "<html>*Para desactivar los gramos de la pieza apretar click derecho en el rectangulo a la izquierda de los gramos y luego clickear en desactivar.</html>");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.ipadx = 400;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTH;

        panel.add(jLabel2, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = cont++;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;

        jPanel1.add(panel, gridBagConstraints);
    }

    private void llenarPanel() {
        Determinacion acidez = new Determinacion("Acidez", "mg/l", null, "", "TITULACIÓN", "acidez", false, null, null);
        Determinacion acidoCianurico = new Determinacion("Ácido cianúrico", "mg/l", null, "", "MELAMINE METHOD- TURBIDITY-UNIT DOSE VIALS", "acidoCianurico", false, null, null);
        Determinacion acidoSorbico = new Determinacion("Ácido sórbico", "mg/l", null, "", "COLORIMETRICO", "acidoSorbico", false, null, null);
        Determinacion actividadAcuosa = new Determinacion("Actividad acuosa", "null", null, "", "Potenciométrica", "actividadAcuosa", false, null, null);
        Determinacion alcalinidad = new Determinacion("Alcalinidad", "mg/l", null, "", "TITULACIÓN", "alcalinidad", false, null, null);
        Determinacion alcohol = new Determinacion("Alcohol", "mg/l", null, "", "DESTILACIÓN", "alcohol", false, null, null);
        Determinacion aluminio = new Determinacion("Aluminio", "mg/l", null, "", "ENICHROME CIANINE R", "aluminio", false, null, null);
        Determinacion amonio = new Determinacion("Amonio", "mg/l", null, "", "SALOCYLATE METHOD", "amonio", false, null, null);
        Determinacion amoniaco = new Determinacion("Amoníaco", "mg/l", null, "", "SALICYLATE METHOD", "amoniacos", false, null, null);
        Determinacion antimonio = new Determinacion("Antimonio", "mg/l", null, "", "Espectrofotometría", "antimonio", false, null, null);
        Determinacion aroma = new Determinacion("Aroma", "null", null, "", "SENSORIAL", "aroma", false, null, null);
        Determinacion arsenico = new Determinacion("Arsénico", "mg/l", null, "", "ESPECTROFOTOMÉTRICO", "arsenico", false, null, null);
        Determinacion asbesto = new Determinacion("Asbesto", "mg/l", null, "", "", "asbesto", false, null, null);
        Determinacion aspecto = new Determinacion("Aspecto", "null", null, "", "SENSORIAL", "aspecto", false, null, null);
        Determinacion azucares = new Determinacion("Azúcares", "mg/l", null, "", "REFRACTOMETRÍA", "azucares", false, null, null);
        Determinacion azucaresDeducidas = new Determinacion("Azúcares deducidas de la lactosa", "mg/l", null, "", "CÁLCULO", "azucaresDeducidas", false, null, null);
        Determinacion azucaresInvertidos = new Determinacion("Azúcares invertidos", "mg/l", null, "", "Polarimetría", "azucaresInvertidos", false, null, null);
        Determinacion azucaresReductores = new Determinacion("Azúcares reductores", "mg/l", null, "", "Refractometría", "azucaresReductores", false, null, null);
        Determinacion bario = new Determinacion("Bario", "mg/l", null, "", "BARIUM CHLORIDE METHOD", "bario", false, null, null);
        Determinacion bicarbonatos = new Determinacion("Bicarbonatos", "mg/l", null, "", "TITULACIÓN", "bicarbonatos", false, null, null);
        Determinacion boro = new Determinacion("Boro", "mg/l", null, "", "AZOMETHINE-H METHOD", "boro", false, null, null);
        Determinacion bromuro = new Determinacion("Bromuro", "mg/l", null, "", "LIQUID DPD METHOD", "bromuro", false, null, null);
        Determinacion cadmioTotal = new Determinacion("Cadmio total", "mg/l", null, "", "PAN METHOD", "cadmioTotal", false, null, null);
        Determinacion calcio = new Determinacion("Calcio", "mg/l", null, "", "COLORIMETRICO", "calcio", false, null, null);
        Determinacion caracteristicas = new Determinacion("Características Organolépticas", "null", null, "", "SENSORIAL", "caracteristicas", false, null, null);
        Determinacion carbonatos = new Determinacion("Carbonatos", "mg/l", null, "", "TITULACIÓN", "carbonatos", false, null, null);
        Determinacion cenizas = new Determinacion("Cenizas", "mg/l", null, "", "GRAVIMETRICO", "cenizas", false, null, null);
        Determinacion cenizasInsolublesAcido = new Determinacion("Cenizas insolubles en ácido cítrico", "mg/l", null, "", "Gravimetría", "cenizasInsolublesAcido", false, null, null);
        Determinacion cenizasInsolublesAgua = new Determinacion("Cenizas solubles en agua de las cenizas totales", "mg/l", null, "", "Gravimetría", "cenizasInsolublesAgua", false, null, null);
        Determinacion cianuros = new Determinacion("Cianuros", "mg/l", null, "", "ESPECTROFOTOMÉTRICO", "cianuros", false, null, null);
        Determinacion cloroActivo = new Determinacion("Cloro activo", "mg/l", null, "", "FOTOMÉTRICO", "cloroActivo", false, null, null);
        Determinacion cloroResidual = new Determinacion("Cloro residual o libre", "mg/l", null, "", "FOTOMÉTRICO", "cloroResidual", false, null, null);
        Determinacion cloroTotal = new Determinacion("Cloro total", "mg/l", null, "", "FOTOMÉTRICO", "cloroTotal", false, null, null);
        Determinacion cloruros = new Determinacion("Cloruros", "mg/l", null, "", "TITULACION", "cloruros", false, null, null);
        Determinacion cobalto = new Determinacion("Cobalto", "mg/l", null, "", "ESPECTROFOTOMÉTRICO", "cobalto", false, null, null);
        Determinacion cobre = new Determinacion("Cobre", "mg/l", null, "", "BICINCHIONINI ACID", "cobre", false, null, null);
        Determinacion colesterol = new Determinacion("Colesterol", "mg/l", null, "", "CÁLCULO", "colesterol", false, null, null);
        Determinacion color = new Determinacion("Color", "null", null, "", "ESCALA PLATINO COBALTO", "color", false, null, null);
        Determinacion colorantesArtificiales = new Determinacion("Colorantes artificiales", "mg/l", null, "", "COLORIMETRICO", "colorantesartificiales", false, null, null);
        Determinacion colorantesNaturales = new Determinacion("Colorantes naturales", "mg/l", null, "", "COLORIMETRICO", "colorantesnaturales", false, null, null);
        Determinacion colorantes = new Determinacion("Colorantes naturales y artificiales", "mg/l", null, "", "COLORIMETRICO", "colorantes", false, null, null);
        Determinacion conductividad = new Determinacion("Conductividad", "mg/l", null, "", "POTENCIOMETRICO", "conductividad", false, null, null);
        Determinacion cromoHexavalente = new Determinacion("Cromo Hexavalente", "mg/l", null, "", "DIPHENIL CARBOHIDRAZINE", "cromoHexavalente", false, null, null);
        Determinacion dbo = new Determinacion("DBO", "mg/l", null, "", "DILUCIONES MULTIPLES", "dbo", false, null, null);
        Determinacion detergentes = new Determinacion("Detergentes", "mg/l", null, "", "OSN N° 26", "detergentes", false, null, null);
        Determinacion dqo = new Determinacion("DQO", "mg/l", null, "", "SM 5220 D", "dqo", false, null, null);
        Determinacion dureza = new Determinacion("Dureza", "mg/l", null, "", "TITULACIÓN", "dureza", false, null, null);
        Determinacion edulcorantes = new Determinacion("Edulcorantes", "mg/l", null, "", "COLOMÉTRICA", "edulcorantes", false, null, null);
        Determinacion estano = new Determinacion("Estaño", "mg/l", null, "", "ESPECTROFOTOMÉTRICO", "estano", false, null, null);
        Determinacion extracto = new Determinacion("Extracto primitivo", "mg/l", null, "", "CÁLCULO", "extracto", false, null, null);
        Determinacion extractoSeco = new Determinacion("Extracto seco", "mg/l", null, "", "GRAVIMETRICO", "extractoseco", false, null, null);
        Determinacion fenoles = new Determinacion("Fenoles", "mg/l", null, "", "AMINOANTIPYRINE METHOD", "fenoles", false, null, null);
        Determinacion fluoruros = new Determinacion("Fluoruros", "mg/l", null, "", "ESPECTROFOTOMÉTRICO", "fluoruros", false, null, null);
        Determinacion fluor = new Determinacion("Flúor", "mg/l", null, "", "BYPIRIDIL ", "fluor", false, null, null);
        Determinacion fosfatos = new Determinacion("Fosfatos", "mg/l", null, "", "ASCORBIC ACID REDUCTION METHOD", "fosfatos", false, null, null);
        Determinacion fosforoTotal = new Determinacion("Fósforo total", "mg/l", null, "", "SM 4500 PC Ó ASCORBIC ACID REDUCCION WITH PRESULFATE DIGESTION METHOD", "fosforoTotal", false, null, null);
        Determinacion gliadinas = new Determinacion("Gliadinas", "mg/l", null, "", "ENSAYO DE INMUNOABSORCIÓN LIGADO A ENZIMAS- ELISA SANDWICH- (ANT.  R 5)", "gliadinas", false, null, null);
        Determinacion gluten = new Determinacion("Gluten", "mg/l", null, "", "ENSAYO DE INMUNOABSORCIÓN LIGADO A ENZIMAS- ELISA SANDWICH- (ANT.  R 5)", "gluten", false, null, null);
        Determinacion gradoFermentacion = new Determinacion("Grado de fermentación", "mg/l", null, "", "CÁLCULO", "gradoFermentacion", false, null, null);
        Determinacion gradosBrix = new Determinacion("Grados Brix", "mg/l", null, "", "REFRACTOMETRIA", "gradosBrix", false, null, null);
        Determinacion grasa = new Determinacion("Grasa", "mg/l", null, "", "SOXHLET", "grasa", false, null, null);
        Determinacion grasasCacao = new Determinacion("Grasas de cacao", "mg/l", null, "", "CÁLCULO", "grasasCacao", false, null, null);
        Determinacion grasasLeche = new Determinacion("Grasas de leche", "mg/l", null, "", "CÁLCULO", "grasasLeche", false, null, null);
        Determinacion grasasyAceites = new Determinacion("Grasas y aceites", "mg/l", null, "", "SM 5520 B", "grasasyAceites", false, null, null);
        Determinacion hidracina = new Determinacion("Hidracina", "mg/l", null, "", "p-DIMETHYLABENZALDEHIDE METHOD", "hidracina", false, null, null);
        Determinacion hidrocarburos = new Determinacion("Hidrocarburos", "mg/l", null, "", "ESPECTROMETRIA INFRARROJA", "hidrocarburos", false, null, null);
        Determinacion hidrocarburosC6 = new Determinacion("HIDROCARBUROS TOTALES DE PETROLEO (C6)", "mg/l", null, "", "AMB 2569", "hidrocarburosc6", false, null, null);
        Determinacion hidrocarburosC6_C35 = new Determinacion("HIDROCARBUROS TOTALES DE PETROLEO (C6-C35)", "mg/l", null, "", "AMB 2569", "hidrocarburosc6_c35", false, null, null);
        Determinacion hidrocarburosC6_C8 = new Determinacion("HIDROCARBUROS TOTALES DE PETROLEO (C6-C8)", "mg/l", null, "", "AMB 2569", "hidrocarburosc6_c8", false, null, null);
        Determinacion hidrocarburosC8_C10 = new Determinacion("HIDROCARBUROS TOTALES DE PETROLEO (C8-C10)", "mg/l", null, "", "AMB 2569", "hidrocarburosc8_c10", false, null, null);
        Determinacion hidrocarburosC10_C12 = new Determinacion("HIDROCARBUROS TOTALES DE PETROLEO (C10-C12)", "mg/l", null, "", "AMB 2569", "hidrocarburosc10_c12", false, null, null);
        Determinacion hidrocarburosC12_C16 = new Determinacion("HIDROCARBUROS TOTALES DE PETROLEO (C12-C16)", "mg/l", null, "", "AMB 2569", "hidrocarburosc12_c16", false, null, null);
        Determinacion hidrocarburosC16_C21 = new Determinacion("HIDROCARBUROS TOTALES DE PETROLEO (C16-C21)", "mg/l", null, "", "AMB 2569", "hidrocarburosc16_c21", false, null, null);
        Determinacion hidrocarburosC21_C35 = new Determinacion("HIDROCARBUROS TOTALES DE PETROLEO (C21-C35)", "mg/l", null, "", "AMB 2569", "hidrocarburosc21_c35", false, null, null);
        Determinacion hierro = new Determinacion("Hierro", "mg/l", null, "", "BYPIRIDYL", "hierro", false, null, null);
        Determinacion humedad = new DeterminacionHumedad("Humedad", "mg/l", null, "", "GRAVIMÉTRICO", "humedad", false, null, null);
        Determinacion magnesio = new Determinacion("Magnesio", "mg/l", null, "", "ESPECTROFOTOMÉTRICO ", "magnesio", false, null, null);
        Determinacion manganeso = new Determinacion("Manganeso", "mg/l", null, "", "PAN METHOD", "manganeso", false, null, null);
        Determinacion materiaGrasa = new Determinacion("Materia grasa", "mg/l", null, "", "SOXHLET", "materiagrasa", false, null, null);
        Determinacion mercurioTotal = new Determinacion("Mercurio Total", "mg/l", null, "", "ESPECTROFOTOMÉTRICO", "mercurioTotal", false, null, null);
        Determinacion molibdeno = new Determinacion("Molibdeno", "mg/l", null, "", "THIOGLYCIOLATE METHOD", "molibdeno", false, null, null);
        Determinacion nitratos = new Determinacion("Nitratos", "mg/l", null, "", "REDUCCIÓN DE ZINC", "nitratos", false, null, null);
        Determinacion nitritos = new Determinacion("Nitritos", "mg/l", null, "", "DIOZOTIZACIÓN", "nitritos", false, null, null);
        Determinacion nitrogenoAmoniacal = new Determinacion("Nitrógeno Amoniacal", "mg/l", null, "", "SALICYLATE METHOD", "nitrogenoAmoniacal", false, null, null);
        Determinacion nitrogenoTotal = new Determinacion("Nitrógeno total", "mg/l", null, "", "SM 4500 N ", "nitrogenoTotal", false, null, null);
        Determinacion niquel = new Determinacion("Níquel", "mg/l", null, "", "ESPECTROFOTOMETRICO", "niquel", false, null, null);
        Determinacion observacionMicroscopica = new Determinacion("Observación microscópica", "null", null, "", "OBSERVACIÓN EN MICROSCOPIO", "observacionMicroscopica", false, null, null);
        Determinacion olor = new Determinacion("Olor", "mg/l", null, "", "SENSORIAL", "olor", false, null, null);
        Determinacion organoclorados = new Determinacion("Organoclorados", "mg/l", null, "", "ESPECTROFOTOMETRICO", "organoclorados", false, null, null);
        Determinacion oxigenoDisuelto = new Determinacion("Oxígeno disuelto", "mg/l", null, "", "WINKLER COLORIMETRIC METHOD", "oxigenoDisuelto", false, null, null);
        Determinacion ozono = new Determinacion("Ozono", "null", null, "", "INDIGO METHOD", "ozono", false, null, null);
        Determinacion peroxidoHidrogeno = new Determinacion("Peróxido de hidrógeno ", "null", null, "", "DPD METHOD", "peroxidoHidrogeno", false, null, null);
        Determinacion ph = new Determinacion("pH", "null", null, "", "POTENCIOMÉTRICO", "ph", false, null, null);
        Determinacion plata = new Determinacion("Plata", "mg/l", null, "", "ESPECTROFOTOMETRICO", "plata", false, null, null);
        Determinacion plomo = new Determinacion("Plomo", "mg/l", null, "", "PAR METHOD", "plomo", false, null, null);
        Determinacion porcentajeCloruro = new Determinacion("Porcentaje de cloruro de sodio", "mg/l", null, "", "TITULACIÓN", "porcentajeCloruro", false, null, null);
        Determinacion potasio = new Determinacion("Potasio", "mg/l", null, "", "ESPECTROFOTOMETRICO", "potasio", false, null, null);
        Determinacion propionato = new Determinacion("Propionato de sodio", "mg/l", null, "", "COLORIMÉTRICO", "propionato", false, null, null);
        Determinacion relacion = new Determinacion("Relación Peso/Humedad", "null", null, "", "CALCULO", "relacion", false, null, null);
        Determinacion residuo105 = new Determinacion("Residuo evaporación a 105ºC", "mg/l", null, "", "GRAVIMÉTRICO", "residuo105", false, null, null);
        Determinacion residuo180 = new Determinacion("Residuo evaporación a 180ºC", "mg/l", null, "", "GRAVIMÉTRICO", "residuo180", false, null, null);
        Determinacion residuoSeco = new Determinacion("Residuo Seco", "mg/l", null, "", "GRAVIMÉTRICO", "residuoSeco", false, null, null);
        Determinacion sabor = new Determinacion("Sabor", "mg/l", null, "", "SENSORIAL", "sabor", false, null, null);
        Determinacion selenio = new Determinacion("Selenio", "mg/l", null, "", "ESPECTROFOTOMETRICO", "selenio", false, null, null);
        Determinacion silicatos = new Determinacion("Silicatos", "mg/l", null, "", "HETEROPOLY BLUE METHOD", "silicatos", false, null, null);
        Determinacion sodio = new Determinacion("Sodio", "mg/l", null, "", "TITULACIÓN", "sodio", false, null, null);
        Determinacion sulfatos = new Determinacion("Sulfatos", "mg/l", null, "", "BARIUM CHLORIDE METHOD", "sulfatos", false, null, null);
        Determinacion sulfuros = new Determinacion("Sulfuros", "mg/l", null, "", "ESPECTROFOTOMETRICO", "sulfuros", false, null, null);
        Determinacion sustancias = new Determinacion("Sustancias extrañas", "null", null, "", "OBSERVACIÓN MICROSCÓPICA", "sustancias", false, null, null);
        Determinacion sustanciasEterEtilico = new Determinacion("Sustancias solubles en éter etílico", "mg/l", null, "", "SM 5520 B", "sustanciasEterEtilico", false, null, null);
        Determinacion solidosTotales = new Determinacion("Sólidos Disueltos totales", "mg/l", null, "", "GRAVIMETRICO", "solidosTotales", false, null, null);
        Determinacion solidosNoGrasosCacao = new Determinacion("Sólidos no grasos de cacao", "mg/l", null, "", "CÁLCULO", "solidosNoGrasosCacao", false, null, null);
        Determinacion solidosNoGrasos = new Determinacion("Sólidos no grasos de leche", "mg/l", null, "", "CALCULO", "solidosNoGrasos", false, null, null);
        Determinacion solidos10Minutos = new Determinacion("Sólidos sedimentables en 10 minutos", "mg/l", null, "", "SM 2540 F", "solidos10Minutos", false, null, null);
        Determinacion solidos2Horas = new Determinacion("Sólidos sedimentables en 2 horas", "mg/l", null, "", "SM 2540 F", "solidos2Horas", false, null, null);
        Determinacion solidosSuspendidosTotales = new Determinacion("Sólidos suspendidos totales", "mg/l", null, "", "GRAVIMETRICO", "solidosSuspendidosTotales", false, null, null);
        Determinacion solidosSuspendidosVolatiles = new Determinacion("Sólidos suspendidos volátiles", "mg/l", null, "", "SPME", "solidosSuspendidosVolatiles", false, null, null);
        Determinacion temperatura = new Determinacion("Temperatura", "mg/l", null, "", "Termómetro pinchacarne calibrado", "temperatura", false, null, null);
        Determinacion turbidez = new Determinacion("Turbidez", "mg/l", null, "", "BSORPTION METHOD", "turbidez", false, null, null);
        Determinacion vanadio = new Determinacion("Vanadio", "mg/l", null, "", "ESPECTROFOTOMETRICO", "vanadio", false, null, null);
        Determinacion zinc = new Determinacion("Zinc", "mg/l", null, "", "ZINCON METHOD", "zinc", false, null, null);
        agregarDeterminacion(acidez);
        agregarDeterminacion(acidoCianurico);
        agregarDeterminacion(acidoSorbico);
        agregarDeterminacion(actividadAcuosa);
        agregarDeterminacion(alcalinidad);
        agregarDeterminacion(alcohol);
        agregarDeterminacion(aluminio);
        agregarDeterminacion(amonio);
        agregarDeterminacion(amoniaco);
        agregarDeterminacion(antimonio);
        agregarDeterminacion(aroma);
        agregarDeterminacion(arsenico);
        agregarDeterminacion(asbesto);
        agregarDeterminacion(aspecto);
        agregarDeterminacion(azucares);
        agregarDeterminacion(azucaresDeducidas);
        agregarDeterminacion(azucaresInvertidos);
        agregarDeterminacion(azucaresReductores);
        agregarDeterminacion(bario);
        agregarDeterminacion(bicarbonatos);
        agregarDeterminacion(boro);
        agregarDeterminacion(bromuro);
        agregarDeterminacion(cadmioTotal);
        agregarDeterminacion(calcio);
        agregarDeterminacion(caracteristicas);
        agregarDeterminacion(carbonatos);
        agregarDeterminacion(cenizas);
        agregarDeterminacion(cenizasInsolublesAcido);
        agregarDeterminacion(cenizasInsolublesAgua);
        agregarDeterminacion(cianuros);
        agregarDeterminacion(cloroActivo);
        agregarDeterminacion(cloroResidual);
        agregarDeterminacion(cloroTotal);
        agregarDeterminacion(cloruros);
        agregarDeterminacion(cobalto);
        agregarDeterminacion(cobre);
        agregarDeterminacion(colesterol);
        agregarDeterminacion(color);
        agregarDeterminacion(colorantesArtificiales);
        agregarDeterminacion(colorantesNaturales);
        agregarDeterminacion(colorantes);
        agregarDeterminacion(conductividad);
        agregarDeterminacion(cromoHexavalente);
        agregarDeterminacion(dbo);
        agregarDeterminacion(detergentes);
        agregarDeterminacion(dqo);
        agregarDeterminacion(dureza);
        agregarDeterminacion(edulcorantes);
        agregarDeterminacion(estano);
        agregarDeterminacion(extracto);
        agregarDeterminacion(extractoSeco);
        agregarDeterminacion(fenoles);
        agregarDeterminacion(fluoruros);
        agregarDeterminacion(fluor);
        agregarDeterminacion(fosfatos);
        agregarDeterminacion(fosforoTotal);
        agregarDeterminacion(gliadinas);
        agregarDeterminacion(gluten);
        agregarDeterminacion(gradoFermentacion);
        agregarDeterminacion(gradosBrix);
        agregarDeterminacion(grasa);
        agregarDeterminacion(grasasCacao);
        agregarDeterminacion(grasasLeche);
        agregarDeterminacion(grasasyAceites);
        agregarDeterminacion(hidracina);
        agregarDeterminacion(hidrocarburos);
        agregarDeterminacion(hidrocarburosC6);
        agregarDeterminacion(hidrocarburosC6_C35);
        agregarDeterminacion(hidrocarburosC6_C8);
        agregarDeterminacion(hidrocarburosC8_C10);
        agregarDeterminacion(hidrocarburosC10_C12);
        agregarDeterminacion(hidrocarburosC12_C16);
        agregarDeterminacion(hidrocarburosC16_C21);
        agregarDeterminacion(hidrocarburosC21_C35);
        agregarDeterminacion(hierro);
        agregarDeterminacionHumedad(humedad);
        agregarDeterminacion(magnesio);
        agregarDeterminacion(manganeso);
        agregarDeterminacion(materiaGrasa);
        agregarDeterminacion(mercurioTotal);
        agregarDeterminacion(molibdeno);
        agregarDeterminacion(nitratos);
        agregarDeterminacion(nitritos);
        agregarDeterminacion(nitrogenoAmoniacal);
        agregarDeterminacion(nitrogenoTotal);
        agregarDeterminacion(niquel);
        agregarDeterminacion(observacionMicroscopica);
        agregarDeterminacion(olor);
        agregarDeterminacion(organoclorados);
        agregarDeterminacion(oxigenoDisuelto);
        agregarDeterminacion(ozono);
        agregarDeterminacion(peroxidoHidrogeno);
        agregarDeterminacion(ph);
        agregarDeterminacion(plata);
        agregarDeterminacion(plomo);
        agregarDeterminacion(porcentajeCloruro);
        agregarDeterminacion(potasio);
        agregarDeterminacion(propionato);
        agregarDeterminacion(relacion);
        agregarDeterminacion(residuo105);
        agregarDeterminacion(residuo180);
        agregarDeterminacion(residuoSeco);
        agregarDeterminacion(sabor);
        agregarDeterminacion(selenio);
        agregarDeterminacion(silicatos);
        agregarDeterminacion(sodio);
        agregarDeterminacion(sulfatos);
        agregarDeterminacion(sulfuros);
        agregarDeterminacion(sustancias);
        agregarDeterminacion(sustanciasEterEtilico);
        agregarDeterminacion(solidosTotales);
        agregarDeterminacion(solidosNoGrasosCacao);
        agregarDeterminacion(solidosNoGrasos);
        agregarDeterminacion(solidos10Minutos);
        agregarDeterminacion(solidos2Horas);
        agregarDeterminacion(solidosSuspendidosTotales);
        agregarDeterminacion(solidosSuspendidosVolatiles);
        agregarDeterminacion(temperatura);
        agregarDeterminacion(turbidez);
        agregarDeterminacion(vanadio);
        agregarDeterminacion(zinc);
    }
}
