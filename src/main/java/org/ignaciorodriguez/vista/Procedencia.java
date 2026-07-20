package org.ignaciorodriguez.vista;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.KeyStroke;
import javax.swing.JComponent;

public class Procedencia extends javax.swing.JDialog {

    Principal p = new Principal();
    public Procedencia(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(null);
        ImageIcon icon = new ImageIcon("src\\vista\\icono.png");
        this.setIconImage(icon.getImage());
    }

    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        botonNuevo = new javax.swing.JButton();
        botonExistente = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setLayout(new java.awt.GridBagLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel1.setText("Seleccione el tipo de cliente:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets = new java.awt.Insets(20, 10, 20, 10);
        jPanel1.add(jLabel1, gridBagConstraints);

        botonNuevo.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        botonNuevo.setText("Nuevo (F1)");
        botonNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonNuevoActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(10, 50, 10, 50);
        jPanel1.add(botonNuevo, gridBagConstraints);
        InputMap im = jPanel1.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0, false), "Apretar");

        ActionMap ap = jPanel1.getActionMap();
        ap.put("Apretar", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e){
                botonNuevo.doClick();
            }
        });

        botonExistente.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        botonExistente.setText("Existente (F2)");
        botonExistente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonExistenteActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(10, 50, 10, 50);
        jPanel1.add(botonExistente, gridBagConstraints);
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0, false), "Apretar2");

        ap.put("Apretar2", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e){
                botonExistente.doClick();
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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

    private void botonNuevoActionPerformed(java.awt.event.ActionEvent evt) {
        dispose();
        AgregarProcedencia ap = new AgregarProcedencia(p,true, false, -1, true);
        ap.setVisible(true);
    }

    private void botonExistenteActionPerformed(java.awt.event.ActionEvent evt) {
        dispose();
        AgregarMuestra am = new AgregarMuestra(p,true);
        am.setVisible(true);
    }

    private javax.swing.JButton botonExistente;
    private javax.swing.JButton botonNuevo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;// End of variables declaration//GEN-END:variables
}
