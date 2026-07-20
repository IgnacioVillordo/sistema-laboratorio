package org.ignaciorodriguez.vista;

import javax.swing.JOptionPane;
import org.ignaciorodriguez.repository.UsuarioRepository;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.KeyStroke;
import javax.swing.JComponent;

public class Usuarios extends javax.swing.JDialog {

    String usuario = "error";
    int tipo;
    UsuarioRepository usuarioRepository = new UsuarioRepository();

    public Usuarios(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        this.setTitle("Usuario");
        initComponents();
        ImageIcon icon = new ImageIcon("src\\vista\\icono.png");
        this.setIconImage(icon.getImage());
        setLocationRelativeTo(null);
    }

    public Usuarios(java.awt.Frame parent, boolean modal, String usuario) {
        super(parent, modal);
        this.setTitle("Usuario");
        this.setLocationRelativeTo(null);
        initComponents();
        ImageIcon icon = new ImageIcon("src\\vista\\icono.png");
        this.setIconImage(icon.getImage());
        cajaUsuario.setText(usuario);
        cajaUsuario.setEnabled(false);
    }

    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        botonAceptar = new javax.swing.JButton();
        cajaUsuario = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setLayout(new java.awt.GridBagLayout());

        jLabel1.setText("Usuario:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(20, 20, 0, 10);
        jPanel1.add(jLabel1, gridBagConstraints);

        botonAceptar.setText("Aceptar");
        botonAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonAceptarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets = new java.awt.Insets(20, 0, 20, 0);
        jPanel1.add(botonAceptar, gridBagConstraints);
        InputMap im = jPanel1.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false), "Apretar");

        ActionMap ap = jPanel1.getActionMap();
        ap.put("Apretar", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e){
                botonAceptar.doClick();
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.ipadx = 150;
        gridBagConstraints.insets = new java.awt.Insets(20, 10, 0, 20);
        jPanel1.add(cajaUsuario, gridBagConstraints);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 272, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }

    private void botonAceptarActionPerformed(java.awt.event.ActionEvent evt) {
        
        if (usuarioRepository.consultaExisteUsuario(cajaUsuario.getText())) {
            usuario = usuarioRepository.consultaUsuarioNombre(cajaUsuario.getText());
            tipo = usuarioRepository.consultaUsuarioTipo(cajaUsuario.getText());
            nombreUsuario();
            tipoUsuario();
            dispose();
        } else {
            JOptionPane.showMessageDialog(null, "El usuario no existe.");
        }

    }

    private javax.swing.JButton botonAceptar;
    private javax.swing.JPasswordField cajaUsuario;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;

    public String nombreUsuario() {
        return usuario;
    }

    public int tipoUsuario() {
        return tipo;
    }
}
