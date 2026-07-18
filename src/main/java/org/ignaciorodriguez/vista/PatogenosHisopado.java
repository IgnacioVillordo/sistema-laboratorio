package org.ignaciorodriguez.vista;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComponent;


public class PatogenosHisopado extends javax.swing.JDialog {

    String patogenos = "";

    public PatogenosHisopado(java.awt.Frame parent, boolean modal, boolean[] editar) {
        super(parent, modal);
        initComponents();
        checkGermenes.setSelected(editar[0]);
        checkColiformesTotales.setSelected(editar[1]);
        checkColiformesFecales.setSelected(editar[2]);
        checkEscherichia.setSelected(editar[3]);
        checkStaphilococos.setSelected(editar[4]);
        checkEnterobacterias.setSelected(editar[5]);
        checkSalmonella.setSelected(editar[6]);
        this.setLocationRelativeTo(null);
        botonContinuar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (checkGermenes.isSelected()) {
                    patogenos += "Germenes aerobios totales, ";
                }
                if (checkColiformesTotales.isSelected()) {
                    patogenos += "Coliformes totales, ";
                }
                if (checkColiformesFecales.isSelected()) {
                    patogenos += "Coliformes fecales, ";
                }
                if (checkEscherichia.isSelected()) {
                    patogenos += "Escherichia coli, ";
                }
                if (checkEnterobacterias.isSelected()) {
                    patogenos += "Enterobacterias, ";
                }
                if (checkStaphilococos.isSelected()) {
                    patogenos += "Staphilococos aureus coagulasa (+), ";
                }
                if (checkSalmonella.isSelected()) {
                    patogenos += "Salmonella, ";
                }
                if (checkListeria.isSelected()) {
                    patogenos += "Listeria monocytogenes, ";
                }
                if (checkSalmonella.isSelected()) {
                    patogenos += "Mohos y Levaduras, ";
                }
                if (checkVibrio.isSelected()) {
                    patogenos += "Vibrio cholerae, ";
                }
                patogenos = patogenos.substring(0, patogenos.length() - 2);
                setVisible(false);
            }
        });
    }

    
    @SuppressWarnings("unchecked")

    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        checkGermenes = new javax.swing.JCheckBox();
        checkColiformesTotales = new javax.swing.JCheckBox();
        checkColiformesFecales = new javax.swing.JCheckBox();
        checkEscherichia = new javax.swing.JCheckBox();
        checkStaphilococos = new javax.swing.JCheckBox();
        botonContinuar = new javax.swing.JButton();
        checkEnterobacterias = new javax.swing.JCheckBox();
        checkSalmonella = new javax.swing.JCheckBox();
        checkListeria = new javax.swing.JCheckBox();
        checkMohos = new javax.swing.JCheckBox();
        checkVibrio = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setAlignmentY(JComponent.
            LEFT_ALIGNMENT);
        jPanel1.setLayout(new java.awt.GridBagLayout());

        jLabel1.setText("Patógenos:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 0);
        jPanel1.add(jLabel1, gridBagConstraints);

        checkGermenes.setText("GERMENES AEROBIOS TOTALES");
        checkGermenes.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        checkGermenes.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        checkGermenes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkGermenesActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 0);
        jPanel1.add(checkGermenes, gridBagConstraints);

        checkColiformesTotales.setText("COLIFORMES TOTALES");
        checkColiformesTotales.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        checkColiformesTotales.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 0);
        jPanel1.add(checkColiformesTotales, gridBagConstraints);

        checkColiformesFecales.setText("COLIFORMES FECALES");
        checkColiformesFecales.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        checkColiformesFecales.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 0);
        jPanel1.add(checkColiformesFecales, gridBagConstraints);

        checkEscherichia.setText("ESCHERICHIA COLI");
        checkEscherichia.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        checkEscherichia.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 0);
        jPanel1.add(checkEscherichia, gridBagConstraints);

        checkStaphilococos.setText("STAPHILOCOCOS AUREUS COAGULOSA (+)");
        checkStaphilococos.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        checkStaphilococos.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        checkStaphilococos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkStaphilococosActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 0);
        jPanel1.add(checkStaphilococos, gridBagConstraints);

        botonContinuar.setText("Continuar");
        botonContinuar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonContinuarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 10, 0);
        jPanel1.add(botonContinuar, gridBagConstraints);

        checkEnterobacterias.setText("ENTEROBACTERIAS");
        checkEnterobacterias.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        checkEnterobacterias.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        checkEnterobacterias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkEnterobacteriasActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 0);
        jPanel1.add(checkEnterobacterias, gridBagConstraints);

        checkSalmonella.setText("SALMONELLA sp");
        checkSalmonella.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        checkSalmonella.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        checkSalmonella.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkSalmonellaActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 0);
        jPanel1.add(checkSalmonella, gridBagConstraints);

        checkListeria.setText("LISTERIA MONOCYTOGENES");
        checkListeria.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        checkListeria.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        checkListeria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkListeriaActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 0);
        jPanel1.add(checkListeria, gridBagConstraints);

        checkMohos.setText("MOHOS Y LEVADURAS");
        checkMohos.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        checkMohos.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        checkMohos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkMohosActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 0);
        jPanel1.add(checkMohos, gridBagConstraints);

        checkVibrio.setText("VIBRIO CHOLERAE");
        checkVibrio.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        checkVibrio.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        checkVibrio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkVibrioActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 0);
        jPanel1.add(checkVibrio, gridBagConstraints);

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

        pack();
    }

    private void checkGermenesActionPerformed(java.awt.event.ActionEvent evt) {

    }

    private void botonContinuarActionPerformed(java.awt.event.ActionEvent evt) {

    }

    private void checkEnterobacteriasActionPerformed(java.awt.event.ActionEvent evt) {

    }

    private void checkStaphilococosActionPerformed(java.awt.event.ActionEvent evt) {

    }

    private void checkSalmonellaActionPerformed(java.awt.event.ActionEvent evt) {

    }

    private void checkListeriaActionPerformed(java.awt.event.ActionEvent evt) {

    }

    private void checkMohosActionPerformed(java.awt.event.ActionEvent evt) {

    }

    private void checkVibrioActionPerformed(java.awt.event.ActionEvent evt) {

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
            java.util.logging.Logger.getLogger(PatogenosHisopado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PatogenosHisopado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PatogenosHisopado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PatogenosHisopado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }


        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                PatogenosHisopado dialog = new PatogenosHisopado(new javax.swing.JFrame(), true, null);
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


    private javax.swing.JButton botonContinuar;
    private javax.swing.JCheckBox checkColiformesFecales;
    private javax.swing.JCheckBox checkColiformesTotales;
    private javax.swing.JCheckBox checkEnterobacterias;
    private javax.swing.JCheckBox checkEscherichia;
    private javax.swing.JCheckBox checkGermenes;
    private javax.swing.JCheckBox checkListeria;
    private javax.swing.JCheckBox checkMohos;
    private javax.swing.JCheckBox checkSalmonella;
    private javax.swing.JCheckBox checkStaphilococos;
    private javax.swing.JCheckBox checkVibrio;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;


    public String devolverPatogenos() {

        this.setVisible(true);
        return patogenos;
    }
}
