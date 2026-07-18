package org.ignaciorodriguez.vista;

import javax.swing.JOptionPane;


public class DialogoBuscar extends java.awt.Dialog {

    String parametro = "-1", valor = "-1";

    public DialogoBuscar(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(null);
        radioProcedencia.setSelected(true);
        labelBuscar.setText("Procedencia:");
        setTitle("Buscar:");
        buttonGroup1.add(radioFechaAnalisis);
        buttonGroup1.add(radioID);
        buttonGroup1.add(radioProcedencia);
        buttonGroup1.add(radioSolicitante);
        buttonGroup1.add(radioFechaMuestreo);
        radioFechaAnalisis.setActionCommand("fechaAnalisis");
        radioFechaMuestreo.setActionCommand("fechaMuestreo");
        radioID.setActionCommand("idmuestras");
        radioProcedencia.setActionCommand("procedencia");
        radioSolicitante.setActionCommand("solicitante");
        labelDesde.setVisible(false);
        labelHasta.setVisible(false);
        cajaDesde.setVisible(false);
        cajaHasta.setVisible(false);
    }


    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        boton = new javax.swing.JButton();
        cajaBuscar = new javax.swing.JTextField();
        labelBuscar = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        radioSolicitante = new javax.swing.JRadioButton();
        radioFechaAnalisis = new javax.swing.JRadioButton();
        radioProcedencia = new javax.swing.JRadioButton();
        jLabel1 = new javax.swing.JLabel();
        radioID = new javax.swing.JRadioButton();
        radioFechaMuestreo = new javax.swing.JRadioButton();
        jPanel3 = new javax.swing.JPanel();
        cajaDesde = new com.toedter.calendar.JDateChooser();
        labelDesde = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        labelHasta = new javax.swing.JLabel();
        cajaHasta = new com.toedter.calendar.JDateChooser();

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });

        jPanel1.setLayout(new java.awt.GridBagLayout());

        boton.setText("Buscar");
        boton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets = new java.awt.Insets(20, 0, 0, 0);
        jPanel1.add(boton, gridBagConstraints);

        cajaBuscar.setFocusTraversalPolicyProvider(true);
        cajaBuscar.setMaximumSize(new java.awt.Dimension(150, 22));
        cajaBuscar.setMinimumSize(new java.awt.Dimension(150, 22));
        cajaBuscar.setName("");
        cajaBuscar.setPreferredSize(new java.awt.Dimension(150, 22));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
        jPanel1.add(cajaBuscar, gridBagConstraints);

        labelBuscar.setText("jLabel2");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 0, 0);
        jPanel1.add(labelBuscar, gridBagConstraints);

        jPanel2.setLayout(new java.awt.GridBagLayout());

        radioSolicitante.setText("Solicitante");
        radioSolicitante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioSolicitanteActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 0, 5);
        jPanel2.add(radioSolicitante, gridBagConstraints);

        radioFechaAnalisis.setText("Fecha Análisis");
        radioFechaAnalisis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioFechaAnalisisActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 0, 5);
        jPanel2.add(radioFechaAnalisis, gridBagConstraints);

        radioProcedencia.setText("Procedencia");
        radioProcedencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioProcedenciaActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 0, 5);
        jPanel2.add(radioProcedencia, gridBagConstraints);

        jLabel1.setText("Buscar por:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 0, 5);
        jPanel2.add(jLabel1, gridBagConstraints);

        radioID.setText("ID");
        radioID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioIDActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 0, 5);
        jPanel2.add(radioID, gridBagConstraints);

        radioFechaMuestreo.setText("Fecha Muestreo");
        radioFechaMuestreo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioFechaMuestreoActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 0, 5);
        jPanel2.add(radioFechaMuestreo, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        jPanel1.add(jPanel2, gridBagConstraints);

        jPanel3.setLayout(new java.awt.GridBagLayout());

        cajaDesde.setDateFormatString("dd/MM/yyyy");
        cajaDesde.setMaximumSize(new java.awt.Dimension(200, 22));
        cajaDesde.setMinimumSize(new java.awt.Dimension(200, 22));
        cajaDesde.setPreferredSize(new java.awt.Dimension(200, 22));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(9, 50, 0, 0);
        jPanel3.add(cajaDesde, gridBagConstraints);

        labelDesde.setText("Desde:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(13, 6, 0, 0);
        jPanel3.add(labelDesde, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanel1.add(jPanel3, gridBagConstraints);

        jPanel4.setLayout(new java.awt.GridBagLayout());

        labelHasta.setText("Hasta:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(13, 6, 0, 0);
        jPanel4.add(labelHasta, gridBagConstraints);

        cajaHasta.setToolTipText("");
        cajaHasta.setDateFormatString("dd/MM/yyyy");
        cajaHasta.setMaximumSize(new java.awt.Dimension(200, 22));
        cajaHasta.setMinimumSize(new java.awt.Dimension(200, 22));
        cajaHasta.setPreferredSize(new java.awt.Dimension(200, 22));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(9, 50, 0, 0);
        jPanel4.add(cajaHasta, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanel1.add(jPanel4, gridBagConstraints);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 542, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }

    
    private void closeDialog(java.awt.event.WindowEvent evt) {
        dispose();
    }

    private void botonActionPerformed(java.awt.event.ActionEvent evt) {
        if (radioID.isSelected()) {
            if (isNumeric(cajaBuscar.getText())) {
                
                    valor = cajaBuscar.getText();
                parametro = buttonGroup1.getSelection().getActionCommand();
                dispose();
            } else {
                JOptionPane.showMessageDialog(null, "El ID tiene que ser numerico.");
            }
        } else {
            if (radioFechaAnalisis.isSelected() || radioFechaMuestreo.isSelected()) {
                java.sql.Date desdeSql = new java.sql.Date(cajaDesde.getDate().getTime());
                java.sql.Date hastaSql = new java.sql.Date(cajaHasta.getDate().getTime());
                valor = "\'" + desdeSql + "\'" + " and " + "\'" + hastaSql + "\'";
            } else {
                valor = cajaBuscar.getText();
            }
            parametro = buttonGroup1.getSelection().getActionCommand();
            dispose();
        }
    }

    private void radioIDActionPerformed(java.awt.event.ActionEvent evt) {
        labelBuscar.setText("ID:");
        esconderFechas();
    }

    private void radioProcedenciaActionPerformed(java.awt.event.ActionEvent evt) {
        labelBuscar.setText("Procedencia:");
        esconderFechas();
    }

    private void radioSolicitanteActionPerformed(java.awt.event.ActionEvent evt) {
        labelBuscar.setText("Solicitante:");
        esconderFechas();
    }

    private void radioFechaAnalisisActionPerformed(java.awt.event.ActionEvent evt) {
        labelBuscar.setVisible(false);
        labelDesde.setVisible(true);
        labelHasta.setVisible(true);
        cajaDesde.setVisible(true);
        cajaHasta.setVisible(true);
        cajaBuscar.setVisible(false);
    }

    private void radioFechaMuestreoActionPerformed(java.awt.event.ActionEvent evt) {
        labelBuscar.setVisible(false);
        labelDesde.setVisible(true);
        labelHasta.setVisible(true);
        cajaDesde.setVisible(true);
        cajaHasta.setVisible(true);
        cajaBuscar.setVisible(false);
    }

    
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                DialogoBuscar dialog = new DialogoBuscar(new java.awt.Frame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }



    private javax.swing.JButton boton;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JTextField cajaBuscar;
    private com.toedter.calendar.JDateChooser cajaDesde;
    private com.toedter.calendar.JDateChooser cajaHasta;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JLabel labelBuscar;
    private javax.swing.JLabel labelDesde;
    private javax.swing.JLabel labelHasta;
    private javax.swing.JRadioButton radioFechaAnalisis;
    private javax.swing.JRadioButton radioFechaMuestreo;
    private javax.swing.JRadioButton radioID;
    private javax.swing.JRadioButton radioProcedencia;
    private javax.swing.JRadioButton radioSolicitante;


    private void esconderFechas() {
        labelDesde.setVisible(false);
        labelHasta.setVisible(false);
        cajaDesde.setVisible(false);
        cajaHasta.setVisible(false);
        cajaBuscar.setVisible(true);
        labelBuscar.setVisible(true);
    }

    public static boolean isNumeric(String str) {
        try {
            System.out.println(str);
            Integer.parseInt(str);
            System.out.println(Integer.parseInt(str) + " 123123123");
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
