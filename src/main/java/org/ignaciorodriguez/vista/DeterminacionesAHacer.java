package org.ignaciorodriguez.vista;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.swing.JOptionPane;
import org.ignaciorodriguez.modelo.Consultas;


public class DeterminacionesAHacer extends javax.swing.JDialog {

    private Vector<String> determinacionesDisponibles = new Vector<>();
    private Vector<String> determinacionesAHacer = new Vector<>();
    private Map original = new HashMap();
    Consultas consultas = Consultas.getInstancia();

    private int limiteDeterminaciones = 0;
    private int id;
    private boolean editar;


    String[][] mbAguaCodigo = {{"GERMENES AEROBIOS TOTALES", "germenes"},
    {"COLIFORMES TOTALES", "coliformesTotales"},
    {"COLIFORMES FECALES", "coliformesFecales"},
    {"ESCHERICHIA COLI", "escherichia"},
    {"PSEUDOMONA AEUROGINOSA", "pseudomona"},
    {"MOHOS Y LEVADURAS", "mohos"},
    {"db", "mbagua"}
    };

    String[][] mbAguaBalnearios = {
        {"RECUENTO COLIFORMES TOTALES", "coliformesTotales"},
        {"RECUENTO ESCHERICHIA COLI", "escherichia"},
        {"db", "mbagua"}
    };

    String[][] mbAguaBidon = {{"GERMENES AEROBIOS TOTALES", "germenes"},
    {"COLIFORMES TOTALES", "coliformesTotales"},
    {"COLIFORMES FECALES", "coliformesFecales"},
    {"ESCHERICHIA COLI", "escherichia"},
    {"PSEUDOMONA AEUROGINOSA", "pseudomona"},
    {"db", "mbagua"}
    };

    String[][] mbAguaCOFES = {{"GERMENES AEROBIOS TOTALES", "germenes"},
    {"COLIFORMES TOTALES", "coliformesTotales"},
    {"COLIFORMES FECALES", "coliformesFecales"},
    {"ESCHERICHIA COLI", "escherichia"},
    {"PSEUDOMONA AEUROGINOSA", "pseudomona"},
    {"db", "mbagua"}
    };

    String[][] mbAguaRecreacion = {{"GERMENES AEROBIOS TOTALES", "germenes"},
    {"COLIFORMES TOTALES", "coliformesTotales"},
    {"COLIFORMES FECALES", "coliformesFecales"},
    {"ESCHERICHIA COLI", "escherichia"},
    {"PSEUDOMONA AEUROGINOSA", "pseudomona"},
    {"STAPHILOCOCOS AUREUS COAGULASA (+)", "staphilococos"},
    {"STREPTOCOCOS FECALIS", "streptococos"},
    {"db", "mbagua"}
    };

    String[][] mbAlimentos = {{"GERMENES AEROBIOS TOTALES", "germenes"},
    {"COLIFORMES TOTALES", "coliformesTotales"},
    {"COLIFORMES FECALES", "coliformesFecales"},
    {"ESCHERICHIA COLI", "escherichia"},
    {"ESCHERICHIA COLI O 157 H7", "escherichiah7"},
    {"ESCHERICHIA COLI NO O 157", "escherichia157"},
    {"ENTEROBACTERIAS", "enterobacterias"},
    {"STAPHILOCOCOS AUREUS COAGULASA (+)", "staphilococos"},
    {"MOHOS Y LEVADURAS", "mohosLevadura"},
    {"SALMONELLA sp", "salmonella"},
    {"LISTERIA MONOCYTOGENES", "listeria"},
    {"BACILLUS CEREUS", "bacillus"},
    {"CLOSTRIDIUM PERFRINGENS", "perfringens"},
    {"CLOSTRIDIUM SULFITO REDUCTORES Ó ANAEROBIOS", "sulfito"},
    {"CAMPILOBACTER", "campilobacter"},
    {"COLIFORMES TOTALES A 30°C", "coliformesTotalesA30"},
    {"COLIFORMES TOTALES POR NÚMERO MÁS PROBABLE", "coliformesTotalesProbables"},
    {"CARACTERÍSTICAS ORGANOLÉPTICAS", "caracteristicas"},
    {"db", "mbalimentos"}
    };

    String[][] mbChocolates = {{"GERMENES AEROBIOS TOTALES", "germenes"},
    {"COLIFORMES TOTALES", "coliformesTotales"},
    {"COLIFORMES FECALES", "coliformesFecales"},
    {"ESCHERICHIA COLI", "escherichia"},
    {"MOHOS Y LEVADURAS", "mohos"},
    {"STAPHILOCOCOS AUREUS COAGULASA (+)", "staphilococos"},
    {"SALMONELLA sp", "salmonella"},
    {"db", "mbchocolates"}
    };

    String[][] baseHelada = {{"GERMENES AEROBIOS TOTALES", "germenes"},
    {"COLIFORMES TOTALES", "coliformesTotales"},
    {"COLIFORMES FECALES", "coliformesFecales"},
    {"ESCHERICHIA COLI", "escherichia"},
    {"MOHOS Y LEVADURAS", "mohos"},
    {"SALMONELLA sp", "salmonella"},
    {"STAPHILOCOCOS AUREUS COAGULOSA (+)", "staphilococos"},
    {"db", "mbchocolates"}
    };

    String[][] efluentes = {{"pH", "ph"},
    {"DEMANDA QUÍMICA DE OXÍGENO (DQO)", "dqo"},
    {"DEMANDA BIOQUÍMICA DE OXÍGENO (DBO)", "dbo"},
    {"SÓLIDOS DISUELTOS SEDIMENTABLES EN 10’", "solidos10"},
    {"SÓLIDOS DISUELTOS SEDIMENTABLES EN 120’", "solidos120"},
    {"HIDROCARBUROS TOTALES DE PETRÓLEO (IR)", "hidrocarburos"},
    {"db", "efluentes"}
    };

    String[][] efluentesCloaca = {{"pH", "ph"},
    {"CONDUCTIVIDAD", "conductividad"},
    {"DEMANDA QUÍMICA DE OXÍGENO (DQO)", "dqo"},
    {"DEMANDA BIOQUÍMICA DE OXÍGENO (DBO)", "dbo"},
    {"SÓLIDOS DISUELTOS SEDIMENTABLES EN 10’", "solidos10"},
    {"SÓLIDOS DISUELTOS SEDIMENTABLES EN 120’", "solidos120"},
    {"DETERGENTES", "detergentes"},
    {"GRASAS Y ACEITES", "grasasAceite"},
    {"FÓSFORO TOTAL", "fosforoTotal"},
    {"NITRÓGENO TOTAL", "nitrogenoTotal"},
    {"SUSTANCIAS SOLUBLES EN ETER ETÍLICO", "sustancias"},
    {"COLIFORMES TOTALES", "coliformesTotales"},
    {"COLIFORMES FECALES", "coliformesFecales"},
    {"ESCHERICHIA COLI", "escherichia"},
    {"HIDROCARBUROS TOTALES DE PETRÓLEO (IR)", "hidrocarburos"},
    {"NITRATOS", "nitratos"},
    {"CLORO", "cloro"},
    {"db", "efluentes"}
    };

    String[][] efluentesInfiltracion = {{"pH", "ph"},
    {"CONDUCTIVIDAD", "conductividad"},
    {"DEMANDA QUÍMICA DE OXÍGENO (DQO)", "dqo"},
    {"DEMANDA BIOQUÍMICA DE OXÍGENO (DBO)", "dbo"},
    {"SÓLIDOS DISUELTOS SEDIMENTABLES EN 10’", "solidos10"},
    {"SÓLIDOS DISUELTOS SEDIMENTABLES EN 120’", "solidos120"},
    {"DETERGENTES", "detergentes"},
    {"GRASAS Y ACEITES", "grasasAceite"},
    {"FÓSFORO TOTAL", "fosforoTotal"},
    {"NITRÓGENO TOTAL", "nitrogenoTotal"},
    {"SUSTANCIAS SOLUBLES EN ETER ETÍLICO", "sustancias"},
    {"COLIFORMES TOTALES", "coliformesTotales"},
    {"COLIFORMES FECALES", "coliformesFecales"},
    {"ESCHERICHIA COLI", "escherichia"},
    {"HIDROCARBUROS TOTALES DE PETRÓLEO (IR)", "hidrocarburos"},
    {"NITRATOS", "nitratos"},
    {"CLORO", "cloro"},
    {"db", "efluentes"}
    };

    String[][] fqAgua = {{"pH", "ph"},
    {"CLORO LIBRE", "cloroTotal"},
    {"OLOR", "olor"},
    {"COLOR", "color"},
    {"TURBIDEZ", "turbidez"},
    {"ALCALINIDAD", "alcalinidad"},
    {"DUREZA TOTAL", "durezatotal"},
    {"CONDUCTIVIDAD", "conductividad"},
    {"SÓLIDOS DISUELTOS TOTALES", "solidosDisueltos"},
    {"HIERRO", "hierro"},
    {"NITRATOS", "nitratos"},
    {"NITRITOS", "nitritos"},
    {"SULFATOS", "sulfatos"},
    {"db", "fqagua"}
    };

    String[][] hisopados = {{"GERMENES AEROBIOS TOTALES", "germenes"},
    {"COLIFORMES TOTALES", "coliformesTotales"},
    {"COLIFORMES FECALES", "coliformesFecales"},
    {"ESCHERICHIA COLI", "escherichia"},
    {"ENTEROBACTERIAS", "enterobacterias"},
    {"STAPHILOCOCOS AUREUS COAGULASA (+)", "staphilococos"},
    {"SALMONELLA sp", "salmonella"},
    {"LISTERIA MONOCYTOGENES", "listeria"},
    {"MOHOS Y LEVADURAS", "mohos"},
    {"db", "hisopados"}
    };

    String[][] hisopadosAlliance = {{"GERMENES AEROBIOS TOTALES", "germenes"},
    {"COLIFORMES TOTALES", "coliformesTotales"},
    {"COLIFORMES FECALES", "coliformesFecales"},
    {"ESCHERICHIA COLI", "escherichia"},
    {"STAPHILOCOCOS AUREUS COAGULASA (+)", "staphilococos"},
    {"ENTEROBACTERIAS", "enterobacterias"},
    {"db", "hisopados"}
    };
    String[][] fqAlimentos = {
        {"Acidez", "acidez"},
        {"Ácido cianúrico", "acidoCianurico"},
        {"Ácido sórbico", "acidoSorbico"},
        {"Alcalinidad", "acidoCianurico"},
        {"Alcohol", "alcohol"},
        {"Aluminio", "aluminio"},
        {"Amonio", "amonio"},
        {"Amoníaco", "amoniacos"},
        {"Antimonio", "antimonio"},
        {"Aroma", "aroma"},
        {"Arsénico", "arsenico"},
        {"Asbesto", "asbesto"},
        {"Aspecto", "aspecto"},
        {"Azúcares", "azucares"},
        {"Azúcares deducidas de la lactosa", "azucaresDeducidas"},
        {"Azúcares invertidos", "azucaresInvertidos"},
        {"Azúcares reductores", "azucaresReductores"},
        {"Bario", "bario"},
        {"Bicarbonatos", "bicarbonatos"},
        {"Boro", "boro"},
        {"Bromuro", "bromuro"},
        {"Cadmio total", "cadmioTotal"},
        {"Calcio", "calcio"},
        {"Características Organolépticas", "caracteristicas"},
        {"Carbonatos", "carbonatos"},
        {"Cenizas", "cenizas"},
        {"Cenizas insolubles en ácido cítrico", "cenizasInsolublesAcido"},
        {"Cenizas insolubles en agua", "cenizasInsolublesAgua"},
        {"Cianuros", "cianuros"},
        {"Cloro activo", "cloroActivo"},
        {"Cloro residual o libre", "cloroResidual"},
        {"Cloro total", "cloroTotal"},
        {"Cloruros", "cloruros"},
        {"Cobalto", "cobalto"},
        {"Cobre", "cobre"},
        {"Colesterol", "colesterol"},
        {"Color", "color"},
        {"Colorantes artificiales", "colorantesartificiales"},
        {"Colorantes naturales", "colorantesnaturales"},
        {"Colorantes naturales y artificiales", "colorantes"},
        {"Conductividad", "conductividad"},
        {"Cromo Hexavalente", "cromoHexavalente"},
        {"DBO", "dbo"},
        {"Detergentes", "detergentes"},
        {"DQO", "dbo"},
        {"Dureza", "dureza"},
        {"Edulcorantes", "edulcorantes"},
        {"Estaño", "estano"},
        {"Extracto primitivo", "extracto"},
        {"Fenoles", "fenoles"},
        {"Fluoruros", "fluoruros"},
        {"Flúor", "fluor"},
        {"Fosfatos", "fosfatos"},
        {"Fósforo total", "fosforoTotal"},
        {"Gliadinas", "gliadinas"},
        {"Gluten", "gluten"},
        {"Grado de fermentación", "gradoFermentacion"},
        {"Grados Brix", "gradosBrix"},
        {"Grasa", "grasa"},
        {"Grasas de cacao", "grasasCacao"},
        {"Grasas de leche", "grasasLeche"},
        {"Grasas y aceites", "grasasyAceites"},
        {"Hidracina", "hidracina"},
        {"Hidrocarburos", "hidrocarburos"},
        {"Hierro", "hierro"},
        {"Humedad", "humedad"},
        {"Magnesio", "magnesio"},
        {"Manganeso", "manganeso"},
        {"Materia grasa", "materiagrasa"},
        {"Mercurio Total", "mercurioTotal"},
        {"Molibdeno", "molibdeno"},
        {"Nitratos", "nitratos"},
        {"Nitritos", "nitritos"},
        {"Nitrógeno Amoniacal", "nitrogenoAmoniacal"},
        {"Nitrógeno total", "nitrogenoTotal"},
        {"Níquel", "niquel"},
        {"Observación microscópica", "observacionMicroscopica"},
        {"Olor", "olor"},
        {"Organoclorados", "organoclorados"},
        {"Oxígeno disuelto", "oxigenoDisuelto"},
        {"Ozono", "ozono"},
        {"Peróxido de hidrógeno", "peroxidoHidrogeno"},
        {"pH", "ph"},
        {"Plata", "plata"},
        {"Plomo", "plomo"},
        {"Porcentaje de cloruro de sodio", "porcentajeCloruro"},
        {"Potasio", "potasio"},
        {"Propionato de sodio", "propionato"},
        {"Relación Peso/Humedad", "relacion"},
        {"Residuo evaporación a 105ºC", "residuo105"},
        {"Residuo evaporación a 180 ºC", "residuo180"},
        {"Residuo Seco", "residuoSeco"},
        {"Sabor", "sabor"},
        {"Selenio", "selenio"},
        {"Silicatos", "silicatos"},
        {"Sodio", "sodio"},
        {"Sulfatos", "sulfatos"},
        {"Sulfuros", "sulfuros"},
        {"Sustancias extrañas", "sustancias"},
        {"Sustancias solubles en éter etílico", "sustanciasEterEtilico"},
        {"Sólidos Disueltos totales", "solidosTotales"},
        {"Sólidos no grasos de cacao", "solidosnograsoscacao"},
        {"Sólidos no grasos de leche", "solidosNoGrasos"},
        {"Sólidos sedimentables en 10 minutos", "solidos10Minutos"},
        {"Sólidos sedimentables en 2 horas", "solidos2Horas"},
        {"Sólidos suspendidos totales", "solidosSuspendidosTotales"},
        {"Sólidos suspendidos volátiles", "solidosSuspendidosVolatiles"},
        {"Turbidez", "turbidez"},
        {"Vanadio", "vanadio"},
        {"Zinc", "zinc"},
        {"db", "determinaciones"}
    };


    String tipo;
    Map<String, String> valoresGuardados;
    Map<String, String[][]> map = new HashMap();

    public DeterminacionesAHacer(java.awt.Frame parent, boolean modal, String tipo, int id, boolean editar) {
        super(parent, modal);
        this.tipo = tipo;
        this.id = id;
        this.editar = editar;
        switch (tipo) {
            case "Microbiológico de agua código":
                this.valoresGuardados = consultas.recuperarResultadosMBAgua(id);
                break;
            case "Microbiológico de agua COFES":
                this.valoresGuardados = consultas.recuperarResultadosMBAguaCOFES(id);
                break;
            case "Microbiológico de agua de recreación":
                this.valoresGuardados = consultas.recuperarResultadosMBAguaDeRecreacion(id);
                break;
            case "Microbiológico de agua balnearios":
                this.valoresGuardados = consultas.recuperarResultadosMBAgua(id);
                break;
            case "Microbiológico de agua bidón":
                this.valoresGuardados = consultas.recuperarResultadosMBAgua(id);
                break;
            case "Microbiológico de alimentos":
                this.valoresGuardados = consultas.recuperarResultadosMBAlimentos(id);
                break;
            case "Hisopados":
                this.valoresGuardados = consultas.recuperarResultadosHisopados(id);
                break;
            case "Hisopados con límites":
                this.valoresGuardados = consultas.recuperarResultadosHisopadosAlliance(id);
                break;
            case "Base helada Del Turista":
                this.valoresGuardados = consultas.recuperarResultadosBaseHelada(id);
                break;
            case "Microbiológico de chocolates Del Turista":
                this.valoresGuardados = consultas.recuperarResultadosMBChocolates(id);
                break;
            case "Efluentes":
                this.valoresGuardados = consultas.recuperarResultadosEfluentes(id);
                break;
            case "Efluentes cloaca":
                this.valoresGuardados = consultas.recuperarResultadosEfluentesTipo(id);
                break;
            case "Efluentes infiltración":
                this.valoresGuardados = consultas.recuperarResultadosEfluentesTipo(id);
                break;
            case "Físico químico de agua básico":
                this.valoresGuardados = consultas.recuperarResultadosFQAgua(id);
                break;
            case "Físico químico de agua completo":
                this.valoresGuardados = consultas.recuperarResultadosMBAguaDeRecreacion(id);
                break;
            case "Físico químico de alimentos":
                this.valoresGuardados = consultas.recuperarResultadosMBAguaDeRecreacion(id);
                break;
            case "Físico químico genérico":
                this.valoresGuardados = consultas.recuperarResultadosMBAguaDeRecreacion(id);
                break;
        }
        initComponents();
        setLocationRelativeTo(null);
        map.put("Microbiológico de agua código", mbAguaCodigo);
        map.put("Microbiológico de agua COFES", mbAguaCOFES);
        map.put("Microbiológico de agua de recreación", mbAguaRecreacion);
        map.put("Microbiológico de agua balnearios", mbAguaBalnearios);
        map.put("Microbiológico de agua bidón", mbAguaBidon);
        map.put("Microbiológico de alimentos", mbAlimentos);
        map.put("Hisopados", hisopados);
        map.put("Hisopados con límites", hisopadosAlliance);
        map.put("Base helada Del Turista", baseHelada);
        map.put("Microbiológico de chocolates Del Turista", mbChocolates);
        map.put("Efluentes", efluentes);
        map.put("Efluentes cloaca", efluentesCloaca);
        map.put("Efluentes infiltración", efluentesInfiltracion);
        map.put("Físico químico de agua básico", fqAgua);
        map.put("Físico químico de agua completo", fqAlimentos);
        map.put("Físico químico de alimentos", fqAlimentos);
        map.put("Físico químico genérico", fqAlimentos);
        llenarComboLista(map, tipo);
        String[][] determinaciones = map.get(tipo);
        if (valoresGuardados != null) {
            for (int i = 0; i < determinaciones.length - 1; i++) {
                String aux = valoresGuardados.get(determinaciones[i][1]);
                if (!aux.contains("-2")) {
                    determinacionesDisponibles.remove(determinaciones[i][0]);
                    determinacionesAHacer.add(determinaciones[i][0]);
                    Collections.sort(determinacionesDisponibles);
                    listaAHacer.updateUI();
                    listaDisponibles.updateUI();
                }
            }
        }
    }

    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        listaDisponibles = new javax.swing.JList<>();
        jButton1 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        listaAHacer = new javax.swing.JList<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        botonTodosDisponibles = new javax.swing.JButton();
        botonTodoAHacer = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setLayout(new java.awt.GridBagLayout());

        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPane1.setMaximumSize(new java.awt.Dimension(220, 90));
        jScrollPane1.setMinimumSize(new java.awt.Dimension(220, 90));
        jScrollPane1.setPreferredSize(new java.awt.Dimension(220, 90));

        listaDisponibles.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        listaDisponibles.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listaDisponiblesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(listaDisponibles);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 20, 20, 20);
        jPanel1.add(jScrollPane1, gridBagConstraints);

        jButton1.setText("Continuar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        jPanel1.add(jButton1, gridBagConstraints);

        jScrollPane2.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPane2.setMaximumSize(new java.awt.Dimension(220, 90));
        jScrollPane2.setMinimumSize(new java.awt.Dimension(220, 90));
        jScrollPane2.setPreferredSize(new java.awt.Dimension(220, 90));

        listaAHacer.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        listaAHacer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listaAHacerMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(listaAHacer);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 20, 20, 20);
        jPanel1.add(jScrollPane2, gridBagConstraints);

        jLabel1.setText("Determinaciones disponibles");
        jLabel1.setMaximumSize(new java.awt.Dimension(160, 16));
        jLabel1.setMinimumSize(new java.awt.Dimension(160, 16));
        jLabel1.setPreferredSize(new java.awt.Dimension(160, 16));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(20, 20, 0, 10);
        jPanel1.add(jLabel1, gridBagConstraints);

        jLabel2.setText("Determinaciones a hacer o hechas");
        jLabel2.setMaximumSize(new java.awt.Dimension(160, 16));
        jLabel2.setMinimumSize(new java.awt.Dimension(160, 16));
        jLabel2.setPreferredSize(new java.awt.Dimension(160, 16));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(20, 10, 0, 20);
        jPanel1.add(jLabel2, gridBagConstraints);

        botonTodosDisponibles.setText("<<");
        botonTodosDisponibles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonTodosDisponiblesActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTH;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 50, 0);
        jPanel1.add(botonTodosDisponibles, gridBagConstraints);

        botonTodoAHacer.setText(">>");
        botonTodoAHacer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonTodoAHacerActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(50, 0, 0, 0);
        jPanel1.add(botonTodoAHacer, gridBagConstraints);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
        );

        pack();
    }

    private void listaAHacerMouseClicked(java.awt.event.MouseEvent evt) {
        String selected = listaAHacer.getSelectedValue();
        determinacionesAHacer.remove(selected);
        determinacionesDisponibles.add(selected);
        Collections.sort(determinacionesDisponibles);
        listaAHacer.updateUI();
        listaDisponibles.updateUI();
    }

    private void listaDisponiblesMouseClicked(java.awt.event.MouseEvent evt) {
        String selected = listaDisponibles.getSelectedValue();
        determinacionesDisponibles.remove(selected);
        determinacionesAHacer.add(selected);
        Collections.sort(determinacionesAHacer);
        listaDisponibles.updateUI();
        listaAHacer.updateUI();

    }

    private void botonTodoAHacerActionPerformed(java.awt.event.ActionEvent evt) {
        if (limiteDeterminaciones > listaAHacer.getModel().getSize() + 1) {
            determinacionesDisponibles.clear();
            String[][] datosAux = (String[][]) map.get(tipo);
            for (int i = 0; i < datosAux.length - 1; i++) {
                determinacionesAHacer.add(datosAux[i][0]);
            }
            Collections.sort(determinacionesAHacer);
            listaDisponibles.setListData(determinacionesDisponibles);
            listaAHacer.setListData(determinacionesAHacer);
            listaDisponibles.updateUI();
            listaAHacer.updateUI();
        }
    }

    private void botonTodosDisponiblesActionPerformed(java.awt.event.ActionEvent evt) {
        if (limiteDeterminaciones > listaDisponibles.getModel().getSize() + 1) {
            determinacionesAHacer.clear();
            String[][] datosAux = (String[][]) map.get(tipo);
            for (int i = 0; i < datosAux.length - 1; i++) {
                determinacionesDisponibles.add(datosAux[i][0]);
            }
            Collections.sort(determinacionesDisponibles);
            listaDisponibles.setListData(determinacionesDisponibles);
            listaAHacer.setListData(determinacionesAHacer);
            listaDisponibles.updateUI();
            listaAHacer.updateUI();
        }
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        String[] listaDb = new String[listaAHacer.getModel().getSize()];
        String[] listaAnular = new String[listaDisponibles.getModel().getSize()];
        for (int i = 0; i < listaDb.length; i++) {
            listaDb[i] = original.get(listaAHacer.getModel().getElementAt(i)).toString();
        }
        for (int i = 0; i < listaAnular.length; i++) {
            listaAnular[i] = original.get(listaDisponibles.getModel().getElementAt(i)).toString();
        }
        String queryGuardar = "";
        String queryAnular = "";
        int cont = 0;
        int contAnular = 0;
        if (tipo == "Físico químico de alimentos" || tipo == "Físico químico de agua completo") {
            boolean existe = consultas.recuperarFQAguaCompleto(id);
            if (existe) {
                Determinaciones determinaciones = new Determinaciones();

                consultas.recuperarFQAguaCompleto(id, determinaciones.determinaciones);
                String db = "update determinaciones set ";
                int contInt = 0;
                for (int i = 0; i < determinaciones.determinaciones.size(); i++) {
                    if (determinaciones.determinaciones.get(i).isActivado()) {
                        db += determinaciones.determinaciones.get(i).getNombreDB() + " = ?, ";
                        contInt++;
                    }
                }
                db = db.substring(0, db.length() - 2);
                db += " where idmuestras = ?";
                consultas.blankearDeterminaciones(db, id, contInt);
                if (consultas.guardarDeterminacionesAHacerFQAlimentosAgua(listaDb, id, existe)) {
                    this.dispose();
                    JOptionPane.showMessageDialog(null, "Análisis editado con éxito");
                }
            } else {
                if (consultas.guardarDeterminacionesAHacerFQAlimentosAgua(listaDb, id, existe)) {
                    this.dispose();
                    JOptionPane.showMessageDialog(null, "Análisis editado con éxito");
                }
            }
        } else {
            String[][] datosAux = (String[][]) map.get(tipo);
            String db = datosAux[datosAux.length - 1][1];
            boolean existe = valoresGuardados != null;
            if (existe) {
                queryGuardar = "update " + db + " set ";
                for (String det : listaDb) {
                    queryGuardar += det + " = ?, ";
                    cont++;
                }
                if (queryGuardar.endsWith(", ")) {
                    queryGuardar = queryGuardar.substring(0, queryGuardar.length() - ", ".length()) + " where idmuestras = ?";
                }

                queryAnular = "update " + db + " set ";
                for (String det : listaAnular) {
                    queryAnular += det + " = ?, ";
                    contAnular++;

                }
                queryAnular = queryAnular.substring(0, queryAnular.length() - ", ".length()) + " where idmuestras = ?";

            } else {
                queryGuardar = "INSERT INTO " + db + " (";
                for (String det : listaDb) {
                    queryGuardar += det + ", ";
                }
                queryGuardar += "idmuestras) VALUES (";
                for (String det : listaDb) {
                    queryGuardar += "?, ";
                    cont++;
                }
                queryGuardar = queryGuardar.substring(0, queryGuardar.length() - ", ".length()) + ", ?)";

                queryAnular = "update " + db + " set ";
                for (String det : listaAnular) {
                    queryAnular += det + " = ?, ";
                    contAnular++;

                }
                queryAnular = queryAnular.substring(0, queryAnular.length() - ", ".length()) + " where idmuestras = ?";
            }

        }
        List<String> list = new LinkedList<>();
        for (String det : listaDb) {
            list.add(det);
        }
        consultas.guardarArregloAHacer(id, list.toString());
        consultas.guardarDeterminacionesAHacer(queryGuardar, cont, false, id);
        consultas.guardarDeterminacionesAHacer(queryAnular, contAnular, true, id);
        this.dispose();
    }

    private javax.swing.JButton botonTodoAHacer;
    private javax.swing.JButton botonTodosDisponibles;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JList<String> listaAHacer;
    private javax.swing.JList<String> listaDisponibles;


    public void llenarComboLista(Map map, String tipo) {
        String[][] datosAux = (String[][]) map.get(tipo);
        for (int i = 0; i < datosAux.length - 1; i++) {
            determinacionesDisponibles.add(datosAux[i][0]);
            original.put(datosAux[i][0], datosAux[i][1]);
        }
        Collections.sort(determinacionesDisponibles);
        listaDisponibles.setListData(determinacionesDisponibles);
        listaAHacer.setListData(determinacionesAHacer);
        listaDisponibles.updateUI();
        limiteDeterminaciones = datosAux.length;
    }
}
