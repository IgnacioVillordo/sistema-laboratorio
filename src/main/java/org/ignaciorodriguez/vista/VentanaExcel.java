/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package org.ignaciorodriguez.vista;

import com.jidesoft.swing.AutoCompletionComboBox;
import org.ignaciorodriguez.modelo.Conexion;
import org.ignaciorodriguez.modelo.Tipo;
import org.ignaciorodriguez.repository.ClienteRepository;
import org.ignaciorodriguez.service.ExcelService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.Vector;
import javax.swing.JOptionPane;

public class VentanaExcel extends javax.swing.JDialog {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(VentanaExcel.class.getName());
    Conexion con = new Conexion();
    ClienteRepository clienteRepository = new ClienteRepository(con);

    Vector<String> solicitantes;

    public VentanaExcel(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        llenarComboBox();
        setLocationRelativeTo(null);
//        AutoCompleteDecorator.decorate(comboProcedencia);
    }

    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        comboTipo = new javax.swing.JComboBox<>();
        cajaDesde = new com.toedter.calendar.JDateChooser();
        cajaHasta = new com.toedter.calendar.JDateChooser();
        comboProcedencia = new AutoCompletionComboBox();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setPreferredSize(new java.awt.Dimension(400, 500));
        jPanel1.setLayout(new java.awt.GridBagLayout());

        jLabel1.setText("Desde:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(20, 10, 20, 0);
        jPanel1.add(jLabel1, gridBagConstraints);

        jLabel2.setText("Hasta");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 20, 0);
        jPanel1.add(jLabel2, gridBagConstraints);

        jLabel3.setText("Tipo");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 20, 0);
        jPanel1.add(jLabel3, gridBagConstraints);

        jButton1.setText("Generar informe");
        jButton1.addActionListener(this::jButton1ActionPerformed);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 20, 0);
        jPanel1.add(jButton1, gridBagConstraints);

        comboTipo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Microbiológico de agua código", "Microbiológico de agua COFES", "Microbiológico de agua de recreación", "Microbiológico de agua balnearios", "Microbiológico de agua bidón", "Microbiológico de alimentos", "Hisopados", "Hisopados con límites", "Base helada Del Turista", "Microbiológico de chocolates Del Turista", "Efluentes", "Efluentes cloaca", "Efluentes infiltración", "Físico químico de agua básico", "Físico químico de agua completo", "Físico químico de alimentos", "Físico químico genérico" }));
        comboTipo.setSelectedItem("Seleccione el tipo de análisis");
        comboTipo.setEditable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 20, 0);
        jPanel1.add(comboTipo, gridBagConstraints);

        cajaDesde.setDateFormatString("dd/MM/yyyy");
        cajaDesde.setMaximumSize(new java.awt.Dimension(278, 20));
        cajaDesde.setMinimumSize(new java.awt.Dimension(278, 20));
        cajaDesde.setOpaque(false);
        cajaDesde.setPreferredSize(new java.awt.Dimension(278, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(20, 10, 0, 0);
        jPanel1.add(cajaDesde, gridBagConstraints);

        cajaHasta.setDateFormatString("dd/MM/yyyy");
        cajaHasta.setMaximumSize(new java.awt.Dimension(278, 20));
        cajaHasta.setMinimumSize(new java.awt.Dimension(278, 20));
        cajaHasta.setOpaque(false);
        cajaHasta.setPreferredSize(new java.awt.Dimension(278, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 20, 0);
        jPanel1.add(cajaHasta, gridBagConstraints);

        comboProcedencia.setEditable(true);
        comboProcedencia.setSelectedItem("Seleccione la procedencia");
        comboProcedencia.setMaximumSize(new java.awt.Dimension(276, 24));
        comboProcedencia.setMinimumSize(new java.awt.Dimension(276, 24));
        comboProcedencia.setPreferredSize(new java.awt.Dimension(276, 24));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 20, 0);
        jPanel1.add(comboProcedencia, gridBagConstraints);

        jLabel4.setText("Procedencia");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 20, 0);
        jPanel1.add(jLabel4, gridBagConstraints);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }



    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        Date fechaInicio = cajaDesde.getDate();
        Date fechaFin = cajaHasta.getDate();

        ExcelService excelService = new ExcelService();
        excelService.exportarExcelTradicional(fechaInicio, fechaFin, clienteRepository.recuperarIdCliente(comboProcedencia.getSelectedItem().toString()), comboTipo.getSelectedItem().toString(), getTipoEnum());
//        this.dispose();
    }

    public com.toedter.calendar.JDateChooser cajaDesde;
    public com.toedter.calendar.JDateChooser cajaHasta;
    private javax.swing.JComboBox<String> comboProcedencia;
    private javax.swing.JComboBox<String> comboTipo;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;

    public void llenarComboBox() {
        Conexion con = new Conexion();
        Connection conexion = con.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conexion.prepareStatement("select procedencia from vistaProcedencia order by procedencia asc");
            rs = ps.executeQuery();
            while (rs.next()) {
                comboProcedencia.addItem(String.valueOf(rs.getString("procedencia")));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al recuperar procedencias, " + e);
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
    }

    private Tipo getTipoEnum() {
        String tipo = comboTipo.getSelectedItem().toString();
        System.out.println("tipo = " + tipo);

        if ("Microbiológico de agua código".equals(tipo)) {
            return Tipo.MBAGUACODIGO;
        }
        if ("Microbiológico de alimentos".equals(tipo)) {
            return Tipo.MBALIMENTOS;
        }
        if ("Efluentes infiltración".equals(tipo)) {
            return Tipo.EFLUENTESINFILTRACION;
        }

        if ("Efluentes cloaca".equals(tipo)) {
            return Tipo.EFLUENTESCLOACA;
        }
        if ("Microbiológico de agua COFES".equals(tipo)) {
            return Tipo.MBAGUACOFES;
        }

        if ("Base helada Del Turista".equals(tipo)) {
            return Tipo.BASEHELADA;
        }

        if ("Físico químico de alimentos".equals(tipo)) {
            return Tipo.FQALIMENTOS;
        }
        if ("Físico químico de agua completo".equals(tipo)) {
            return Tipo.FQAGUACOMPLETO;
        }
        if ("Físico químico genérico".equals(tipo)) {
            return Tipo.FQGENERICO;
        }
        if ("Hisopados".equals(tipo)) {
            return Tipo.HISOPADOS;
        }
        if ("Físico químico de agua básico".equals(tipo)) {
            return Tipo.FQAGUA;
        }
        if ("Microbiológico de chocolates Del Turista".equals(tipo)) {
            return Tipo.MBCHOCOLATES;
        }
        if ("Hisopado con límites".equals(tipo) || "Hisopado Alliance".equals(tipo)) {
            return Tipo.HISOPADOLIMITES;
        }
        if ("Microbiológico de agua balnearios".equals(tipo)) {
            return Tipo.MBAGUABALNEARIOS;
        }
        if ("Microbiológico de agua recreación".equals(tipo)) {
            return Tipo.MBAGUARECREACION;
        }
        return null;
    }
}
