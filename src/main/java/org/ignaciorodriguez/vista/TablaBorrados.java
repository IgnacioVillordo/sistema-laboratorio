package org.ignaciorodriguez.vista;

import org.ignaciorodriguez.modelo.Consultas;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.KeyStroke;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class TablaBorrados extends javax.swing.JDialog {

    Consultas consultas = Consultas.getInstancia();
    int fila;
    DefaultTableModel modeloTabla = consultas.recuperarBorrados();

    public TablaBorrados(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(null);
        ImageIcon icon = new ImageIcon("src\\vista\\icono.png");
        this.setIconImage(icon.getImage());
        tablaDatos.setAutoCreateRowSorter(true);
    }

    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPopupMenu1 = new javax.swing.JPopupMenu();
        itemRecuperar = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaDatos = new javax.swing.JTable(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        itemRecuperar.setText("jMenuItem1");
        itemRecuperar.setText("Recuperar muestra");
        itemRecuperar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemRecuperarActionPerformed(evt);
            }
        });
        jPopupMenu1.add(itemRecuperar);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setLayout(new java.awt.GridBagLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("Anulados:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 16, 0, 0);
        jPanel1.add(jLabel1, gridBagConstraints);

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPane1.setToolTipText("");
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        tablaDatos.setModel(modeloTabla);
        tablaDatos.setSelectionBackground(new java.awt.Color(212, 236, 108));
        tablaDatos.setSelectionForeground(new java.awt.Color(0, 0, 0));
        tablaDatos.getTableHeader().setReorderingAllowed(false);
        tablaDatos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tablaDatosMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(tablaDatos);
        tablaDatos.setComponentPopupMenu(jPopupMenu1);
        int anchos[] = {50, 200, 200, 100, 100, 141};
        for (int i = 0; i < 6; i++) {
            tablaDatos.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
        jPanel1.add(jScrollPane1, gridBagConstraints);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 810, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 463, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
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
    }

    private void tablaDatosMousePressed(java.awt.event.MouseEvent evt) {
        fila = tablaDatos.rowAtPoint(evt.getPoint());
    }

    private void itemRecuperarActionPerformed(java.awt.event.ActionEvent evt) {
        int id = Integer.parseInt((String) tablaDatos.getValueAt(fila, 0));
        if (consultas.borrarAnalisis(id, 0)) {
            JOptionPane.showMessageDialog(null, "Se recupero la muestra.");
            consultas.recuperarBorrados();
        } else {
            JOptionPane.showMessageDialog(null, "Error al recuperar la muestra.");
        }
    }

    private javax.swing.JMenuItem itemRecuperar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JTable tablaDatos;
}
