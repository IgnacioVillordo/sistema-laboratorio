package org.ignaciorodriguez.vista;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import javax.swing.table.DefaultTableModel;
import org.ignaciorodriguez.modelo.Consultas;
import org.ignaciorodriguez.repository.UsuarioRepository;

/**
 *
 * @author Nacho
 */
public class VerUsuarios extends javax.swing.JDialog {

    UsuarioRepository c = new UsuarioRepository();
    DefaultTableModel modeloTabla = c.recuperarUsuariosTabla();
    Principal p = new Principal();
    int fila2;

    public VerUsuarios(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        tablaDatos.setModel(modeloTabla);
        setLocationRelativeTo(null);
    }


    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPopupMenu1 = new javax.swing.JPopupMenu();
        itemEliminar = new javax.swing.JMenuItem();
        itemModificar = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaDatos = new javax.swing.JTable();

        itemEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/eliminar usuario.png"))); // NOI18N
        itemEliminar.setText("Eliminar usuario");
        itemEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemEliminarActionPerformed(evt);
            }
        });
        jPopupMenu1.add(itemEliminar);

        itemModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/modificar usuario.png"))); // NOI18N
        itemModificar.setText("Modificar usuario");
        itemModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemModificarActionPerformed(evt);
            }
        });
        jPopupMenu1.add(itemModificar);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
                formWindowGainedFocus(evt);
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
            }
        });

        jPanel1.setLayout(new java.awt.GridBagLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel1.setText("Usuarios");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(20, 20, 10, 0);
        jPanel1.add(jLabel1, gridBagConstraints);

        jScrollPane1.setMinimumSize(new java.awt.Dimension(453, 403));
        jScrollPane1.setPreferredSize(new java.awt.Dimension(453, 203));

        tablaDatos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tablaDatos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tablaDatosMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(tablaDatos);
        tablaDatos.setComponentPopupMenu(jPopupMenu1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(10, 20, 20, 20);
        jPanel1.add(jScrollPane1, gridBagConstraints);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        InputMap im = jPanel1.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap ap = jPanel1.getActionMap();
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false), "Cerrar");
        ap.put("Cerrar", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e){
                dispose();
            }
        });

        pack();
    }

    private void itemEliminarActionPerformed(java.awt.event.ActionEvent evt) {
        String nombre = (String) tablaDatos.getValueAt(fila2, 0);
        EliminarUsuario eliminar = new EliminarUsuario(p, true, nombre);
        eliminar.setVisible(true);
    }

    private void itemModificarActionPerformed(java.awt.event.ActionEvent evt) {
        String nombre = (String) tablaDatos.getValueAt(fila2, 0);
        ModificarUsuario modificar = new ModificarUsuario(p, true, nombre);
        modificar.setVisible(true);
    }

    private void tablaDatosMousePressed(java.awt.event.MouseEvent evt) {
        fila2 = tablaDatos.rowAtPoint(evt.getPoint());
    }

    private void formWindowGainedFocus(java.awt.event.WindowEvent evt) {
        modeloTabla.setRowCount(0);
        modeloTabla.setColumnCount(0);
        modeloTabla = c.recuperarUsuariosTabla();
        tablaDatos.setModel(modeloTabla);
    }

    public javax.swing.JMenuItem itemEliminar;
    public javax.swing.JMenuItem itemModificar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tablaDatos;
}
