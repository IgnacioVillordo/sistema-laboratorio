package org.ignaciorodriguez.vista;

import javax.swing.table.DefaultTableModel;
import org.ignaciorodriguez.modelo.Consultas;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Calendar;
import java.util.Date;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.KeyStroke;
import javax.swing.JComponent;

public class Vencimiento extends javax.swing.JDialog {

    Consultas consultas = Consultas.getInstancia();
    java.awt.Frame p = new java.awt.Frame();
    int row;
    DefaultTableModel modeloTabla = new DefaultTableModel() {
        @Override
        public Class getColumnClass(int columnIndex) {
            Class clazz = String.class;
            switch (columnIndex) {
                case 0: {
                    clazz = Boolean.class;
                }
            }
            return clazz;
        }
    };
    Date desde = null, hasta = null;

    public Vencimiento(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        p = parent;
        Calendar agregarMes = Calendar.getInstance();
        agregarMes.add(Calendar.MONTH, 1);
        desde = agregarMes.getTime();
        agregarMes.add(Calendar.MONTH, 1);
        hasta = agregarMes.getTime();
        initComponents();
        tablaVencimientos.setAutoCreateRowSorter(true);
        tablaVencimientos.setComponentPopupMenu(jPopupMenu1);
        modeloTabla = consultas.obtenerVencimientos(desde, hasta, false);
        tablaVencimientos.setModel(modeloTabla);
        int anchos[] = {30, 220, 140, 91};
        for (int ancho = 0; ancho < 4; ancho++) {
            tablaVencimientos.getColumnModel().getColumn(ancho).setPreferredWidth(anchos[ancho]);
        }

        cajaDesde.setDate(desde);
        cajaHasta.setDate(hasta);
        this.setLocationRelativeTo(null);
        ImageIcon icon = new ImageIcon("src\\vista\\icono.png");
        this.setIconImage(icon.getImage());
        tablaVencimientos.setRowSelectionAllowed(true);
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPopupMenu1 = new javax.swing.JPopupMenu();
        botonEmail = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaVencimientos = new javax.swing.JTable(){
            @Override
            public boolean isCellEditable(int row, int column) {
                switch(column){
                    case 4: {
                        return true;
                    }
                    default:{
                        return false;
                    }
                }
            }
        };
        jLabel1 = new javax.swing.JLabel();
        cajaDesde = new com.toedter.calendar.JDateChooser();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        cajaHasta = new com.toedter.calendar.JDateChooser();
        botonBuscar = new javax.swing.JButton();

        botonEmail.setText("Enviar email");
        botonEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonEmailActionPerformed(evt);
            }
        });
        jPopupMenu1.add(botonEmail);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
                formWindowGainedFocus(evt);
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
            }
        });
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        jPanel1.setLayout(new java.awt.GridBagLayout());

        tablaVencimientos.setModel(modeloTabla);
        tablaVencimientos.setToolTipText("");
        tablaVencimientos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaVencimientosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tablaVencimientos);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 635;
        gridBagConstraints.ipady = 186;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(27, 6, 6, 6);
        jPanel1.add(jScrollPane1, gridBagConstraints);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel1.setText("Vencimientos:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 0);
        jPanel1.add(jLabel1, gridBagConstraints);

        cajaDesde.setDateFormatString("dd/MM/yyyy");
        cajaDesde.setMaximumSize(new java.awt.Dimension(88, 24));
        cajaDesde.setMinimumSize(new java.awt.Dimension(88, 24));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 62;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(9, 12, 0, 0);
        jPanel1.add(cajaDesde, gridBagConstraints);

        jLabel2.setText("Desde:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(13, 6, 0, 0);
        jPanel1.add(jLabel2, gridBagConstraints);

        jLabel3.setText("Hasta:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(15, 12, 0, 0);
        jPanel1.add(jLabel3, gridBagConstraints);

        cajaHasta.setToolTipText("");
        cajaHasta.setDateFormatString("dd/MM/yyyy");
        cajaHasta.setMaximumSize(new java.awt.Dimension(88, 24));
        cajaHasta.setMinimumSize(new java.awt.Dimension(88, 24));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 62;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(9, 18, 0, 0);
        jPanel1.add(cajaHasta, gridBagConstraints);

        botonBuscar.setText("Buscar");
        botonBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonBuscarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(8, 45, 0, 0);
        jPanel1.add(botonBuscar, gridBagConstraints);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 497, Short.MAX_VALUE)
        );

        InputMap im = jPanel1.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false), "Cerrar");

        ActionMap ap = jPanel1.getActionMap();
        ap.put("Cerrar", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e){
                dispose();
            }
        });

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        int id, aviso;
        for (int i = 0; i < tablaVencimientos.getRowCount(); i++) {
            if (tablaVencimientos.getValueAt(i, 4).equals(false)) {
                aviso = 0;
            } else {
                aviso = 1;
            }
            id = (int) tablaVencimientos.getValueAt(i, 0);
            consultas.actualizarAvisados(id, aviso);
        }
    }//GEN-LAST:event_formWindowClosed

    private void botonBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonBuscarActionPerformed
        desde = cajaDesde.getDate();
        hasta = cajaHasta.getDate();
        modeloTabla.setRowCount(0);
        modeloTabla = consultas.obtenerVencimientos(desde, hasta, true);
        Object[] aux = {"NO SE ENCONTRARON VENCIMIENTOS ENTRE LAS FECHAS ESPECIFICADAS"};
        if (modeloTabla.getRowCount() == 0) {
            modeloTabla.setColumnCount(0);
            modeloTabla.addColumn("");
            modeloTabla.addRow(aux);
        }
        int anchos[] = {30, 220, 140, 91};
        for (int ancho = 0; ancho < 4; ancho++) {
            tablaVencimientos.getColumnModel().getColumn(ancho).setPreferredWidth(anchos[ancho]);
        }
    }//GEN-LAST:event_botonBuscarActionPerformed

    private void tablaVencimientosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaVencimientosMouseClicked
        row = tablaVencimientos.rowAtPoint(evt.getPoint());
    }//GEN-LAST:event_tablaVencimientosMouseClicked

    private void botonEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonEmailActionPerformed
        int id = Integer.parseInt(tablaVencimientos.getValueAt(row, 0).toString());
        ventanaEmailVencimientos v = new ventanaEmailVencimientos(p, true, id);
        v.setVisible(true);
    }//GEN-LAST:event_botonEmailActionPerformed

    private void formWindowGainedFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowGainedFocus
        modeloTabla = consultas.obtenerVencimientos(desde, hasta, false);
        tablaVencimientos.setModel(modeloTabla);
        int anchos[] = {38, 379, 170, 201, 107};
        for (int ancho = 0; ancho < anchos.length; ancho++) {
            tablaVencimientos.getColumnModel().getColumn(ancho).setPreferredWidth(anchos[ancho]);
        }
    }//GEN-LAST:event_formWindowGainedFocus

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
            java.util.logging.Logger.getLogger(Vencimiento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Vencimiento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Vencimiento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Vencimiento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Vencimiento dialog = new Vencimiento(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton botonBuscar;
    private javax.swing.JMenuItem botonEmail;
    private com.toedter.calendar.JDateChooser cajaDesde;
    private com.toedter.calendar.JDateChooser cajaHasta;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JTable tablaVencimientos;
    // End of variables declaration//GEN-END:variables
}
