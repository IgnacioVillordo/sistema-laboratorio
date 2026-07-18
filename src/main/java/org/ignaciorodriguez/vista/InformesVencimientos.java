package org.ignaciorodriguez.vista;

import javax.swing.table.DefaultTableModel;
import org.ignaciorodriguez.modelo.Consultas;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.KeyStroke;
import javax.swing.JComponent;


public class InformesVencimientos extends javax.swing.JDialog {

    int row = -1;
    Date desde = null;
    Date hasta = null;
    Consultas consultas = Consultas.getInstancia();
    DefaultTableModel modeloTabla = new DefaultTableModel() {
        @Override
        public Class getColumnClass(int columnIndex) {
            switch (columnIndex) {
                case 4: {
                    return Boolean.class;
                }
                default: {
                    return String.class;
                }
            }
        }
    };

    public InformesVencimientos(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(null);
        ImageIcon icon = new ImageIcon("src\\vista\\icono.png");
        this.setIconImage(icon.getImage());
        LocalDate agregarMes = LocalDate.now();
        if (agregarMes.getDayOfMonth() > 20) {
            desde = Date.from(Instant.from(agregarMes.withDayOfMonth(1).plusMonths(1).atStartOfDay(ZoneId.of("GMT"))));
            hasta = Date.from(Instant.from(agregarMes.withDayOfMonth(1).plusMonths(2).atStartOfDay(ZoneId.of("GMT"))));
        } else {
            desde = Date.from(Instant.from(agregarMes.withDayOfMonth(1).atStartOfDay(ZoneId.of("GMT"))));
            hasta = Date.from(Instant.from(agregarMes.withDayOfMonth(1).plusMonths(1).atStartOfDay(ZoneId.of("GMT"))));

        }
        modeloTabla = consultas.obtenerVencimientosInformes(desde, hasta);
        tablaDatos.setModel(modeloTabla);
        tablaDatos.setAutoCreateRowSorter(true);
        tablaDatos.getColumnModel().getColumn(0).setPreferredWidth(36);
        tablaDatos.getColumnModel().getColumn(1).setPreferredWidth(265);
        tablaDatos.getColumnModel().getColumn(2).setPreferredWidth(61);
        tablaDatos.getColumnModel().getColumn(3).setPreferredWidth(165);
        tablaDatos.getColumnModel().getColumn(4).setPreferredWidth(71);
        
    }

    @SuppressWarnings("unchecked")

    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaDatos = new javax.swing.JTable(){
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
        jCheckBox1 = new javax.swing.JCheckBox();
        botonGenerar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        tablaDatos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tablaDatos.getTableHeader().setReorderingAllowed(false);
        tablaDatos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tablaDatosMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(tablaDatos);

        jLabel1.setText("Seleccione los análisis a analizar:");

        jCheckBox1.setText("Todos los análisis");
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });

        botonGenerar.setText("Generar informe");
        botonGenerar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonGenerarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jLabel1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 617, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jCheckBox1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(botonGenerar)))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jCheckBox1)
                    .addComponent(botonGenerar))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        InputMap im = jPanel1.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false), "Apretar");

        ActionMap ap = jPanel1.getActionMap();
        ap.put("Apretar", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e){
                botonGenerar.doClick();
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

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {
        if (jCheckBox1.isSelected()) {
            for (int i = 0; i < tablaDatos.getModel().getRowCount(); i++) {
                tablaDatos.setValueAt(true, i, 4);
            }
        } else {
            for (int i = 0; i < tablaDatos.getModel().getRowCount(); i++) {
                tablaDatos.setValueAt(false, i, 4);
            }
        }
    }

    private void botonGenerarActionPerformed(java.awt.event.ActionEvent evt) {
        for (int i = 0; i < tablaDatos.getRowCount(); i++) {
            try {
                if ((boolean) tablaDatos.getValueAt(i, 4)) {
                    consultas.seleccionarVencimiento(Integer.parseInt(tablaDatos.getValueAt(i, 0).toString()), 1);
                }
            } catch (Exception e) {
            }
        }
        this.dispose();
        consultas.generarReporteVencimientos(desde, hasta);
        for (int i = 0; i < tablaDatos.getRowCount(); i++) {
            try {
                if ((boolean) tablaDatos.getValueAt(i, 4)) {
                    consultas.actualizarAvisados(Integer.parseInt(tablaDatos.getValueAt(i, 0).toString()), 1);
                }
            } catch (NullPointerException e) {
                System.err.println("Valor nulo");
            }
        }
    }

    private void tablaDatosMousePressed(java.awt.event.MouseEvent evt) {
        row = tablaDatos.rowAtPoint(evt.getPoint());
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
            java.util.logging.Logger.getLogger(InformesVencimientos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(InformesVencimientos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(InformesVencimientos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(InformesVencimientos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }



        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                InformesVencimientos dialog = new InformesVencimientos(new javax.swing.JFrame(), true);
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


    private javax.swing.JButton botonGenerar;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tablaDatos;

}
