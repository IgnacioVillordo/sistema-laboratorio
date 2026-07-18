package org.ignaciorodriguez.vista;

import java.awt.event.ItemEvent;
import javax.swing.DefaultListModel;
import org.ignaciorodriguez.modelo.Consultas;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.KeyStroke;
import javax.swing.JComponent;
import org.ignaciorodriguez.modelo.CustomListModel;
import org.ignaciorodriguez.modelo.Determinacion;
import org.ignaciorodriguez.modelo.DeterminacionHumedad;
import org.ignaciorodriguez.utils.ListUtils;

public class FQAlimentos extends javax.swing.JDialog {

    DefaultListModel modelo = new DefaultListModel();
    DefaultComboBoxModel modeloCombo = new DefaultComboBoxModel<>();
    Principal p = new Principal();
    int id;
    boolean editar = false, auxiliar;
    Consultas consultas = Consultas.getInstancia();
    String pdf;
    int alimentos;
    List<Determinacion> determinaciones = new LinkedList<>();

    public FQAlimentos(java.awt.Frame parent, boolean modal, int id, boolean editar, String pdf, int alimentos) {
        super(parent, modal);
        inicializarLista();
        initComponents();
        comboDeterminaciones.setModel(modeloCombo);
        setTitle("ID. " + id + ". " + "Informe físico quimico " + (alimentos == 0 ? "de alimentos" : alimentos == 1 ? "de agua" : "genérico") + ". " + consultas.obtenerProcedencia(id));
        this.id = id;
        this.editar = editar;
        this.pdf = pdf;
        this.alimentos = alimentos;

        modelo = new CustomListModel<>();
        listaDeterminaciones.setModel(modelo);
        consultas.recuperarFQAguaCompleto(id, determinaciones);
        for (int i = 0; i < determinaciones.size(); i++) {
            if (i == 0) {
                auxiliar = determinaciones.get(i).isActivado();
            } else {
                auxiliar = auxiliar || determinaciones.get(i).isActivado();
            }
        }
        this.editar = auxiliar;
        if (this.editar) {
            for (int i = 0; i < determinaciones.size(); i++) {
                if (determinaciones.get(i).isActivado()) {
                    comboDeterminaciones.setSelectedIndex(i);
                }
            }
        }
        this.pdf = pdf.substring(1);

        setLocationRelativeTo(null);
        ImageIcon icon = new ImageIcon("src\\vista\\icono.png");
        this.setIconImage(icon.getImage());

        jLabel1.setText("<html>Determinaciones cargadas = " + comboDeterminaciones.getItemCount() + "</html>");

    }

    @SuppressWarnings("unchecked")

    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        comboDeterminaciones = new javax.swing.JComboBox<>();
        botonDeterminaciones = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        listaDeterminaciones = new javax.swing.JList<>(modelo);
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        determinaciones.forEach(determinacion -> {
            determinacion.setActivado(false);
            modeloCombo.addElement(determinacion.getNombre());
        });
        comboDeterminaciones.setModel(modeloCombo);
        comboDeterminaciones.setEditable(true);
        comboDeterminaciones.setSelectedItem("Elija las determinaciones a analizar");
        comboDeterminaciones.setEditable(false);
        comboDeterminaciones.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comboDeterminacionesItemStateChanged(evt);
            }
        });
        comboDeterminaciones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboDeterminacionesActionPerformed(evt);
            }
        });
        comboDeterminaciones.setSelectedItem("Elija las determinaciones a analizar");

        botonDeterminaciones.setText("Guardar determinaciones");
        botonDeterminaciones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonDeterminacionesActionPerformed(evt);
            }
        });

        listaDeterminaciones.setModel(modelo);
        listaDeterminaciones.setToolTipText("");
        listaDeterminaciones.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                listaDeterminacionesMousePressed(evt);
            }
        });
        jScrollPane3.setViewportView(listaDeterminaciones);

        jLabel1.setText("Cantidad de determinaciones cargadas = ");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3)
                    .addComponent(comboDeterminaciones, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(botonDeterminaciones)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(comboDeterminaciones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 265, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botonDeterminaciones)
                    .addComponent(jLabel1))
                .addGap(12, 12, 12))
        );

        InputMap im = jPanel1.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false), "Apretar");

        ActionMap ap = jPanel1.getActionMap();
        ap.put("Apretar", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e){
                botonDeterminaciones.doClick();
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

    private void botonDeterminacionesActionPerformed(java.awt.event.ActionEvent evt) {
        List<Determinacion> aux = new ArrayList<>();





        for (int i = 0; i < determinaciones.size(); i++) {
            ListUtils.addIfcondition(determinaciones.get(i), aux, determinaciones.get(i).isActivado());
        }
        this.dispose();
        Determinaciones d = new Determinaciones(p, true, aux, id, editar, pdf, alimentos);
        d.setVisible(true);
    }

    private void listaDeterminacionesMousePressed(java.awt.event.MouseEvent evt) {

        int row = listaDeterminaciones.getSelectedIndex();
        if (row == -1) {
        } else {
            try {
                if (modelo.getSize() == 0) {
                    modelo.removeAllElements();
                } else {
                    modelo.removeElementAt(row);
                    determinaciones.get(comboDeterminaciones.getSelectedIndex()).setActivado(false);
                    listaDeterminaciones.updateUI();
                }
            } catch (ArrayIndexOutOfBoundsException e) {
            }
        }
    }

    private void comboDeterminacionesActionPerformed(java.awt.event.ActionEvent evt) {

    }

    private void comboDeterminacionesItemStateChanged(java.awt.event.ItemEvent evt) {
        if (evt.getStateChange() == ItemEvent.SELECTED) {



            modelo.addElement(determinaciones.get(comboDeterminaciones.getSelectedIndex()));
            listaDeterminaciones.updateUI();
            determinaciones.get(comboDeterminaciones.getSelectedIndex()).setActivado(true);
        }
    }

    public static void main(String args[]) {
        

        
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FQAlimentos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FQAlimentos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FQAlimentos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FQAlimentos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }









        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                FQAlimentos dialog = new FQAlimentos(new javax.swing.JFrame(), true, -1, false, "", -1);
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


    private javax.swing.JButton botonDeterminaciones;
    private javax.swing.JComboBox<String> comboDeterminaciones;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JList<String> listaDeterminaciones;


    public void apretarBoton() {
        List<Determinacion> aux = new ArrayList<>();
        for (int i = 0; i < determinaciones.size(); i++) {
            ListUtils.addIfcondition(determinaciones.get(i), aux, determinaciones.get(i).isActivado());
        }
        Determinaciones d = new Determinaciones(p, true, aux, id, editar, pdf, alimentos);
        d.apretarBoton();
    }

    public void inicializarLista() {
        Determinacion acidez = new Determinacion("Acidez", "mg/l", null, "", "TITULACIÓN", "acidez", false, null, null);
        Determinacion acidoCianurico = new Determinacion("Ácido cianúrico", "mg/l", null, "", "MELAMINE METHOD- TURBIDITY-UNIT DOSE VIALS", "acidoCianurico", false, null, null);
        Determinacion acidoSorbico = new Determinacion("Ácido sórbico", "mg/l", null, "", "COLORIMETRICO", "acidoSorbico", false, null, null);
        Determinacion actividadAcuosa = new Determinacion("Actividad acuosa", null, null, "", "Potenciométrica", "actividadAcuosa", false, null, null);
        Determinacion alcalinidad = new Determinacion("Alcalinidad", "mg/l", null, "", "TITULACIÓN", "alcalinidad", false, null, null);
        Determinacion alcohol = new Determinacion("Alcohol", "mg/100g", null, "", "DESTILACIÓN", "alcohol", false, new String[]{"mg/100g"}, null);
        Determinacion aluminio = new Determinacion("Aluminio", "mg/l", null, "", "ENICHROME CIANINE R", "aluminio", false, null, null);
        Determinacion amonio = new Determinacion("Amonio", "mg/l", null, "", "SALOCYLATE METHOD", "amonio", false, null, null);
        Determinacion amoniaco = new Determinacion("Amoníaco", "mg/l", null, "", "SALICYLATE METHOD", "amoniacos", false, null, null);
        Determinacion antimonio = new Determinacion("Antimonio", "mg/l", null, "", "Espectrofotometría", "antimonio", false, null, null);
        Determinacion aroma = new Determinacion("Aroma", null, null, "", "SENSORIAL", "aroma", false, null, null);
        Determinacion arsenico = new Determinacion("Arsénico", "mg/l", null, "", "ESPECTROFOTOMÉTRICO", "arsenico", false, null, null);
        Determinacion asbesto = new Determinacion("Asbesto", "mg/l", null, "", "", "asbesto", false, null, null);
        Determinacion aspecto = new Determinacion("Aspecto", null, null, "", "SENSORIAL", "aspecto", false, null, null);
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
        Determinacion caracteristicas = new Determinacion("Características Organolépticas", null, null, "", "SENSORIAL", "caracteristicas", false, null, null);
        Determinacion carbonatos = new Determinacion("Carbonatos", "mg/l", null, "", "TITULACIÓN", "carbonatos", false, null, null);
        Determinacion cenizas = new Determinacion("Cenizas", "mg/l", null, "", "GRAVIMETRICO", "cenizas", false, null, null);
        Determinacion cenizasInsolublesAcido = new Determinacion("Cenizas insolubles en ácido cítrico", "mg/l", null, "", "Gravimetría", "cenizasInsolublesAcido", false, null, null);
        Determinacion cenizasInsolublesAgua = new Determinacion("Cenizas solubles en agua de las cenizas totales", "mg/l", null, "", "Gravimetría", "cenizasInsolublesAgua", false, null, null);
        Determinacion cianuros = new Determinacion("Cianuros", "mg/l", null, "", "ESPECTROFOTOMÉTRICO", "cianuros", false, null, null);
        Determinacion cloroActivo = new Determinacion("Cloro activo", "p.p.m.", null, "", "FOTOMÉTRICO", "cloroActivo", false, new String[]{"p.p.m."}, null);
        Determinacion cloroResidual = new Determinacion("Cloro residual o libre", "p.p.m.", null, "", "FOTOMÉTRICO", "cloroResidual", false, new String[]{"p.p.m."}, null);
        Determinacion cloroTotal = new Determinacion("Cloro total", "p.p.m.", null, "", "FOTOMÉTRICO", "cloroTotal", false, new String[]{"p.p.m."}, null);
        Determinacion cloruros = new Determinacion("Cloruros", "mg/l", null, "", "TITULACION", "cloruros", false, null, null);
        Determinacion cobalto = new Determinacion("Cobalto", "mg/l", null, "", "ESPECTROFOTOMÉTRICO", "cobalto", false, null, null);
        Determinacion cobre = new Determinacion("Cobre", "mg/l", null, "", "BICINCHIONINI ACID", "cobre", false, null, null);
        Determinacion colesterol = new Determinacion("Colesterol", "mg/l", null, "", "CÁLCULO", "colesterol", false, null, null);
        Determinacion color = new Determinacion("Color", null, null, "", "ESCALA PLATINO COBALTO", "color", false, null, null);
        Determinacion colorantesArtificiales = new Determinacion("Colorantes artificiales", "mg/l", null, "", "COLORIMETRICO", "colorantesartificiales", false, null, null);
        Determinacion colorantesNaturales = new Determinacion("Colorantes naturales", "mg/l", null, "", "COLORIMETRICO", "colorantesnaturales", false, null, null);
        Determinacion colorantes = new Determinacion("Colorantes naturales y artificiales", "mg/l", null, "", "COLORIMETRICO", "colorantes", false, null, null);
        Determinacion conductividad = new Determinacion("Conductividad", "us/cm", null, "", "POTENCIOMETRICO", "conductividad", false, new String[]{"us/cm"}, null);
        Determinacion cromoHexavalente = new Determinacion("Cromo Hexavalente", "mg/l", null, "", "DIPHENIL CARBOHIDRAZINE", "cromoHexavalente", false, null, null);
        Determinacion dbo = new Determinacion("DBO", "mg/l", null, "", "DILUCIONES MULTIPLES", "dbo", false, null, null);
        Determinacion detergentes = new Determinacion("Detergentes", "mg/l", null, "", "OSN N° 26", "detergentes", false, null, null);
        Determinacion dqo = new Determinacion("DQO", "mg/l", null, "", "SM 5220 D", "dqo", false, null, null);
        Determinacion dureza = new Determinacion("Dureza", "mg/l", null, "", "TITULACIÓN", "dureza", false, null, null);
        Determinacion edulcorantes = new Determinacion("Edulcorantes", "mg/l", null, "", "COLOMÉTRICA", "edulcorantes", false, null, null);
        Determinacion estano = new Determinacion("Estaño", "mg/l", null, "", "ESPECTROFOTOMÉTRICO", "estano", false, null, null);
        Determinacion extracto = new Determinacion("Extracto primitivo", "%", null, "", "CÁLCULO", "extracto", false, new String[]{"%"}, null);
        Determinacion extractoSeco = new Determinacion("Extracto seco", "mg/l", null, "", "GRAVIMETRICO", "extractoseco", false, null, null);
        Determinacion fenoles = new Determinacion("Fenoles", "mg/l", null, "", "AMINOANTIPYRINE METHOD", "fenoles", false, null, null);
        Determinacion fluoruros = new Determinacion("Fluoruros", "mg/l", null, "", "ESPECTROFOTOMÉTRICO", "fluoruros", false, null, null);
        Determinacion fluor = new Determinacion("Flúor", "mg/l", null, "", "BYPIRIDIL ", "fluor", false, null, null);
        Determinacion fosfatos = new Determinacion("Fosfatos", "mg/l", null, "", "ASCORBIC ACID REDUCTION METHOD", "fosfatos", false, null, null);
        Determinacion fosforoTotal = new Determinacion("Fósforo total", "mg/l", null, "", "SM 4500 PC Ó ASCORBIC ACID REDUCCION WITH PRESULFATE DIGESTION METHOD", "fosforoTotal", false, null, null);
        Determinacion gliadinas = new Determinacion("Gliadinas", "mg/Kg", null, "", "ENSAYO DE INMUNOABSORCIÓN LIGADO A ENZIMAS- ELISA SANDWICH- (ANT.  R 5)", "gliadinas", false, new String[]{"mg/Kg"}, null);
        Determinacion gluten = new Determinacion("Gluten", "mg/Kg", null, "", "ENSAYO DE INMUNOABSORCIÓN LIGADO A ENZIMAS- ELISA SANDWICH- (ANT.  R 5)", "gluten", false, new String[]{"mg/Kg"}, null);
        Determinacion gradoFermentacion = new Determinacion("Grado de fermentación", "%", null, "", "CÁLCULO", "gradoFermentacion", false, new String[]{"%"}, null);
        Determinacion gradosBrix = new Determinacion("Grados Brix", "°", null, "", "REFRACTOMETRIA", "gradosBrix", false, new String[]{"°"}, null);
        Determinacion grasa = new Determinacion("Grasa", "mg/l", null, "", "SOXHLET", "grasa", false, null, null);
        Determinacion grasasCacao = new Determinacion("Grasas de cacao", "mg/l", null, "", "CÁLCULO", "grasasCacao", false, null, null);
        Determinacion grasasLeche = new Determinacion("Grasas de leche", "mg/l", null, "", "CÁLCULO", "grasasLeche", false, null, null);
        Determinacion grasasyAceites = new Determinacion("Grasas y aceites", "mg/l", null, "", "SM 5520 B", "grasasyAceites", false, null, null);
        Determinacion hidracina = new Determinacion("Hidracina", "mg/l", null, "", "p-DIMETHYLABENZALDEHIDE METHOD", "hidracina", false, null, null);
        Determinacion hidrocarburos = new Determinacion("Hidrocarburos", "mg/l", null, "", "ESPECTROMETRIA INFRARROJA", "hidrocarburos", false, null, null);
        Determinacion hidrocarburosC6 = new Determinacion("HIDROCARBUROS TOTALES DE PETROLEO (C6)", "μg/l", null, "", "AMB 2569", "hidrocarburosc6", false, new String[]{"μg/l", "μg/kg"}, null);
        Determinacion hidrocarburosC6_C35 = new Determinacion("HIDROCARBUROS TOTALES DE PETROLEO (C6-C35)", "μg/l", null, "", "AMB 2569", "hidrocarburosc6_c35", false, new String[]{"μg/l", "μg/kg"}, null);
        Determinacion hidrocarburosC6_C8 = new Determinacion("HIDROCARBUROS TOTALES DE PETROLEO (C6-C8)", "μg/l", null, "", "AMB 2569", "hidrocarburosc6_c8", false, new String[]{"μg/l", "μg/kg"}, null);
        Determinacion hidrocarburosC8_C10 = new Determinacion("HIDROCARBUROS TOTALES DE PETROLEO (C8-C10)", "μg/l", null, "", "AMB 2569", "hidrocarburosc8_c10", false, new String[]{"μg/l", "μg/kg"}, null);
        Determinacion hidrocarburosC10_C12 = new Determinacion("HIDROCARBUROS TOTALES DE PETROLEO (C10-C12)", "μg/l", null, "", "AMB 2569", "hidrocarburosc10_c12", false, new String[]{"μg/l", "μg/kg"}, null);
        Determinacion hidrocarburosC12_C16 = new Determinacion("HIDROCARBUROS TOTALES DE PETROLEO (C12-C16)", "μg/l", null, "", "AMB 2569", "hidrocarburosc12_c16", false, new String[]{"μg/l", "μg/kg"}, null);
        Determinacion hidrocarburosC16_C21 = new Determinacion("HIDROCARBUROS TOTALES DE PETROLEO (C16-C21)", "μg/l", null, "", "AMB 2569", "hidrocarburosc16_c21", false, new String[]{"μg/l", "μg/kg"}, null);
        Determinacion hidrocarburosC21_C35 = new Determinacion("HIDROCARBUROS TOTALES DE PETROLEO (C21-C35)", "μg/l", null, "", "AMB 2569", "hidrocarburosc21_c35", false, new String[]{"μg/l", "μg/kg"}, null);
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
        Determinacion nitrogenoBasicoVolatil = new Determinacion("Nitrógeno Básico Volátil", "mg/l", null, "", "AOAC 999.01", "nitrogenoBasicoVolatil", false, null, null);
        Determinacion nitrogenoTotal = new Determinacion("Nitrógeno total", "mg/l", null, "", "SM 4500 N ", "nitrogenoTotal", false, null, null);
        Determinacion niquel = new Determinacion("Níquel", "mg/l", null, "", "ESPECTROFOTOMETRICO", "niquel", false, null, null);
        Determinacion observacionMicroscopica = new Determinacion("Observación microscópica", null, null, "", "OBSERVACIÓN EN MICROSCOPIO", "observacionMicroscopica", false, null, null);
        Determinacion olor = new Determinacion("Olor", "mg/l", null, "", "SENSORIAL", "olor", false, null, null);
        Determinacion organoclorados = new Determinacion("Organoclorados", "mg/l", null, "", "ESPECTROFOTOMETRICO", "organoclorados", false, null, null);
        Determinacion oxigenoDisuelto = new Determinacion("Oxígeno disuelto", "mg/l", null, "", "WINKLER COLORIMETRIC METHOD", "oxigenoDisuelto", false, null, null);
        Determinacion ozono = new Determinacion("Ozono", null, null, "", "INDIGO METHOD", "ozono", false, null, null);
        Determinacion peroxidoHidrogeno = new Determinacion("Peróxido de hidrógeno", null, null, "", "DPD METHOD", "peroxidoHidrogeno", false, null, null);
        Determinacion ph = new Determinacion("pH", null, null, "", "POTENCIOMÉTRICO", "ph", false, null, null);
        Determinacion plata = new Determinacion("Plata", "mg/l", null, "", "ESPECTROFOTOMETRICO", "plata", false, null, null);
        Determinacion plomo = new Determinacion("Plomo", "mg/l", null, "", "PAR METHOD", "plomo", false, null, null);
        Determinacion porcentajeCloruro = new Determinacion("Porcentaje de cloruro de sodio", "mg/l", null, "", "TITULACIÓN", "porcentajeCloruro", false, null, null);
        Determinacion potasio = new Determinacion("Potasio", "mg/l", null, "", "ESPECTROFOTOMETRICO", "potasio", false, null, null);
        Determinacion propionato = new Determinacion("Propionato de sodio", "mg/l", null, "", "COLORIMÉTRICO", "propionato", false, null, null);
        Determinacion relacion = new Determinacion("Relación Peso/Humedad", null, null, "", "CALCULO", "relacion", false, null, null);
        Determinacion residuo105 = new Determinacion("Residuo evaporación a 105ºC", "mg/l", null, "", "GRAVIMÉTRICO", "residuo105", false, null, null);
        Determinacion residuo180 = new Determinacion("Residuo evaporación a 180ºC", "mg/l", null, "", "GRAVIMÉTRICO", "residuo180", false, null, null);
        Determinacion residuoSeco = new Determinacion("Residuo Seco", "mg/l", null, "", "GRAVIMÉTRICO", "residuoSeco", false, null, null);
        Determinacion sabor = new Determinacion("Sabor", "mg/l", null, "", "SENSORIAL", "sabor", false, null, null);
        Determinacion selenio = new Determinacion("Selenio", "mg/l", null, "", "ESPECTROFOTOMETRICO", "selenio", false, null, null);
        Determinacion silicatos = new Determinacion("Silicatos", "mg/l", null, "", "HETEROPOLY BLUE METHOD", "silicatos", false, null, null);
        Determinacion sodio = new Determinacion("Sodio", "mg/l", null, "", "TITULACIÓN", "sodio", false, null, null);
        Determinacion sulfatos = new Determinacion("Sulfatos", "mg/l", null, "", "BARIUM CHLORIDE METHOD", "sulfatos", false, null, null);
        Determinacion sulfuros = new Determinacion("Sulfuros", "mg/l", null, "", "ESPECTROFOTOMETRICO", "sulfuros", false, null, null);
        Determinacion sustancias = new Determinacion("Sustancias extrañas", null, null, "", "OBSERVACIÓN MICROSCÓPICA", "sustancias", false, null, null);
        Determinacion sustanciasEterEtilico = new Determinacion("Sustancias solubles en éter etílico", "mg/l", null, "", "SM 5520 B", "sustanciasEterEtilico", false, null, null);
        Determinacion solidosTotales = new Determinacion("Sólidos Disueltos totales", "mg/l", null, "", "GRAVIMETRICO", "solidosTotales", false, null, null);
        Determinacion solidosNoGrasosCacao = new Determinacion("Sólidos no grasos de cacao", "mg/l", null, "", "CÁLCULO", "solidosNoGrasosCacao", false, null, null);
        Determinacion solidosNoGrasos = new Determinacion("Sólidos no grasos de leche", "mg/l", null, "", "CALCULO", "solidosNoGrasos", false, null, null);
        Determinacion solidos10Minutos = new Determinacion("Sólidos sedimentables en 10 minutos", "mg/l", null, "", "SM 2540 F", "solidos10Minutos", false, null, null);
        Determinacion solidos2Horas = new Determinacion("Sólidos sedimentables en 2 horas", "mg/l", null, "", "SM 2540 F", "solidos2Horas", false, null, null);
        Determinacion solidosSuspendidosTotales = new Determinacion("Sólidos suspendidos totales", "mg/l", null, "", "GRAVIMETRICO", "solidosSuspendidosTotales", false, null, null);
        Determinacion solidosSuspendidosVolatiles = new Determinacion("Sólidos suspendidos volátiles", "mg/l", null, "", "SPME", "solidosSuspendidosVolatiles", false, null, null);
        Determinacion temperatura = new Determinacion("Temperatura", "°C", null, "", "Termómetro pinchacarne calibrado", "temperatura", false, new String[]{"°C"}, null);
        Determinacion turbidez = new Determinacion("Turbidez", "NTU", null, "", "BSORPTION METHOD", "turbidez", false, new String[]{"NTU"}, null);
        Determinacion vanadio = new Determinacion("Vanadio", "mg/l", null, "", "ESPECTROFOTOMETRICO", "vanadio", false, null, null);
        Determinacion vibrio = new Determinacion("VIBRIO CHOLERAE", "UFC", null, "", "Cultivo en medio TCBS, previo enriquecimiento", "vibrio", false, new String[]{"UFC"}, null);
        Determinacion zinc = new Determinacion("Zinc", "mg/l", null, "", "ZINCON METHOD", "zinc", false, null, null);
        determinaciones.add(acidez);
        determinaciones.add(acidoCianurico);
        determinaciones.add(acidoSorbico);
        determinaciones.add(actividadAcuosa);
        determinaciones.add(alcalinidad);
        determinaciones.add(alcohol);
        determinaciones.add(aluminio);
        determinaciones.add(amonio);
        determinaciones.add(amoniaco);
        determinaciones.add(antimonio);
        determinaciones.add(aroma);
        determinaciones.add(arsenico);
        determinaciones.add(asbesto);
        determinaciones.add(aspecto);
        determinaciones.add(azucares);
        determinaciones.add(azucaresDeducidas);
        determinaciones.add(azucaresInvertidos);
        determinaciones.add(azucaresReductores);
        determinaciones.add(bario);
        determinaciones.add(bicarbonatos);
        determinaciones.add(boro);
        determinaciones.add(bromuro);
        determinaciones.add(cadmioTotal);
        determinaciones.add(calcio);
        determinaciones.add(caracteristicas);
        determinaciones.add(carbonatos);
        determinaciones.add(cenizas);
        determinaciones.add(cenizasInsolublesAcido);
        determinaciones.add(cenizasInsolublesAgua);
        determinaciones.add(cianuros);
        determinaciones.add(cloroActivo);
        determinaciones.add(cloroResidual);
        determinaciones.add(cloroTotal);
        determinaciones.add(cloruros);
        determinaciones.add(cobalto);
        determinaciones.add(cobre);
        determinaciones.add(colesterol);
        determinaciones.add(color);
        determinaciones.add(colorantesArtificiales);
        determinaciones.add(colorantesNaturales);
        determinaciones.add(colorantes);
        determinaciones.add(conductividad);
        determinaciones.add(cromoHexavalente);
        determinaciones.add(dbo);
        determinaciones.add(detergentes);
        determinaciones.add(dqo);
        determinaciones.add(dureza);
        determinaciones.add(edulcorantes);
        determinaciones.add(estano);
        determinaciones.add(extracto);
        determinaciones.add(extractoSeco);
        determinaciones.add(fenoles);
        determinaciones.add(fluoruros);
        determinaciones.add(fluor);
        determinaciones.add(fosfatos);
        determinaciones.add(fosforoTotal);
        determinaciones.add(gliadinas);
        determinaciones.add(gluten);
        determinaciones.add(gradoFermentacion);
        determinaciones.add(gradosBrix);
        determinaciones.add(grasa);
        determinaciones.add(grasasCacao);
        determinaciones.add(grasasLeche);
        determinaciones.add(grasasyAceites);
        determinaciones.add(hidracina);
        determinaciones.add(hidrocarburos);
        determinaciones.add(hidrocarburosC6);
        determinaciones.add(hidrocarburosC6_C35);
        determinaciones.add(hidrocarburosC6_C8);
        determinaciones.add(hidrocarburosC8_C10);
        determinaciones.add(hidrocarburosC10_C12);
        determinaciones.add(hidrocarburosC12_C16);
        determinaciones.add(hidrocarburosC16_C21);
        determinaciones.add(hidrocarburosC21_C35);
        determinaciones.add(hierro);
        determinaciones.add(humedad);
        determinaciones.add(magnesio);
        determinaciones.add(manganeso);
        determinaciones.add(materiaGrasa);
        determinaciones.add(mercurioTotal);
        determinaciones.add(molibdeno);
        determinaciones.add(nitratos);
        determinaciones.add(nitritos);
        determinaciones.add(nitrogenoAmoniacal);
        determinaciones.add(nitrogenoBasicoVolatil);
        determinaciones.add(nitrogenoTotal);
        determinaciones.add(niquel);
        determinaciones.add(observacionMicroscopica);
        determinaciones.add(olor);
        determinaciones.add(organoclorados);
        determinaciones.add(oxigenoDisuelto);
        determinaciones.add(ozono);
        determinaciones.add(peroxidoHidrogeno);
        determinaciones.add(ph);
        determinaciones.add(plata);
        determinaciones.add(plomo);
        determinaciones.add(porcentajeCloruro);
        determinaciones.add(potasio);
        determinaciones.add(propionato);
        determinaciones.add(relacion);
        determinaciones.add(residuo105);
        determinaciones.add(residuo180);
        determinaciones.add(residuoSeco);
        determinaciones.add(sabor);
        determinaciones.add(selenio);
        determinaciones.add(silicatos);
        determinaciones.add(sodio);
        determinaciones.add(sulfatos);
        determinaciones.add(sulfuros);
        determinaciones.add(sustancias);
        determinaciones.add(sustanciasEterEtilico);
        determinaciones.add(solidosTotales);
        determinaciones.add(solidosNoGrasosCacao);
        determinaciones.add(solidosNoGrasos);
        determinaciones.add(solidos10Minutos);
        determinaciones.add(solidos2Horas);
        determinaciones.add(solidosSuspendidosTotales);
        determinaciones.add(solidosSuspendidosVolatiles);
        determinaciones.add(temperatura);
        determinaciones.add(turbidez);
        determinaciones.add(vanadio);
        determinaciones.add(vibrio);
        determinaciones.add(zinc);
    }
}
