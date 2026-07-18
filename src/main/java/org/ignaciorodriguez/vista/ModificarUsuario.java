package org.ignaciorodriguez.vista;

import java.awt.event.KeyListener;
import javax.swing.JOptionPane;
import org.ignaciorodriguez.modelo.Consultas;
import org.ignaciorodriguez.modelo.Usuario;
import org.ignaciorodriguez.modelo.VerContraseña;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.KeyStroke;
import javax.swing.JComponent;

public class ModificarUsuario extends javax.swing.JDialog {

    Consultas consultas = Consultas.getInstancia();
    boolean verContraseña = false;
    boolean verConfirmar = false;
    VerContraseña v = new VerContraseña();

    public ModificarUsuario(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(parent);
        ImageIcon icon = new ImageIcon("src\\vista\\icono.png");
        this.setIconImage(icon.getImage());
    }
    
    public ModificarUsuario(java.awt.Frame parent, boolean modal, String usuario) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(parent);
        ImageIcon icon = new ImageIcon("src\\vista\\icono.png");
        this.setIconImage(icon.getImage());
        comboUsuario.setSelectedItem(usuario);
        comboUsuario.setEnabled(false);
    }

    
    @SuppressWarnings("unchecked")

    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        comboUsuario = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        cajaContraseña = new javax.swing.JPasswordField();
        cajaConfirmar = new javax.swing.JPasswordField();
        jLabel3 = new javax.swing.JLabel();
        botonModificar = new javax.swing.JButton();
        botonContraseña = new javax.swing.JButton();
        botonConfirmar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setLayout(new java.awt.GridBagLayout());

        jLabel1.setText("Seleccione el usuario:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(20, 20, 20, 10);
        jPanel1.add(jLabel1, gridBagConstraints);

        comboUsuario.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        comboUsuario.setModel(consultas.recuperarUsuarios());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 24;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(20, 0, 20, 0);
        jPanel1.add(comboUsuario, gridBagConstraints);

        jLabel2.setText("Nueva contraseña:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(20, 20, 20, 10);
        jPanel1.add(jLabel2, gridBagConstraints);

        cajaContraseña.setPreferredSize(new java.awt.Dimension(111, 30));
        KeyListener eventoTeclado = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent ke) {
            }

            @Override
            public void keyPressed(KeyEvent ke) {
            }

            @Override
            public void keyReleased(KeyEvent ke) {
                if(ke.getKeyChar() == '\n'){
                    botonModificar.doClick();
                }}
            };
            cajaContraseña.addKeyListener(eventoTeclado);
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 1;
            gridBagConstraints.gridy = 1;
            gridBagConstraints.ipadx = 24;
            gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
            gridBagConstraints.insets = new java.awt.Insets(20, 0, 20, 0);
            jPanel1.add(cajaContraseña, gridBagConstraints);

            cajaConfirmar.setPreferredSize(new java.awt.Dimension(111, 30));
            KeyListener eventoTeclado1 = new KeyListener() {
                @Override
                public void keyTyped(KeyEvent ke) {
                }

                @Override
                public void keyPressed(KeyEvent ke) {
                }

                @Override
                public void keyReleased(KeyEvent ke) {
                    if(ke.getKeyChar() == '\n'){
                        botonModificar.doClick();
                    }}
                };
                cajaConfirmar.addKeyListener(eventoTeclado);
                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 1;
                gridBagConstraints.gridy = 2;
                gridBagConstraints.ipadx = 24;
                gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
                gridBagConstraints.insets = new java.awt.Insets(20, 0, 20, 0);
                jPanel1.add(cajaConfirmar, gridBagConstraints);

                jLabel3.setText("Confirmar contraseña:");
                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 0;
                gridBagConstraints.gridy = 2;
                gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
                gridBagConstraints.insets = new java.awt.Insets(20, 20, 20, 10);
                jPanel1.add(jLabel3, gridBagConstraints);

                botonModificar.setText("Modificar");
                botonModificar.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        botonModificarActionPerformed(evt);
                    }
                });
                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 0;
                gridBagConstraints.gridy = 3;
                gridBagConstraints.gridwidth = 3;
                gridBagConstraints.insets = new java.awt.Insets(10, 0, 10, 0);
                jPanel1.add(botonModificar, gridBagConstraints);
                InputMap im = jPanel1.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
                im.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false), "Apretar");

                ActionMap ap = jPanel1.getActionMap();
                ap.put("Apretar", new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e){
                        botonModificar.doClick();
                    }
                });

                botonContraseña.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/ver.png")));
                botonContraseña.setMargin(new java.awt.Insets(2, 2, 2, 2));
                botonContraseña.setMaximumSize(new java.awt.Dimension(20, 20));
                botonContraseña.setPreferredSize(new java.awt.Dimension(30, 30));
                botonContraseña.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        botonContraseñaActionPerformed(evt);
                    }
                });
                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 2;
                gridBagConstraints.gridy = 1;
                gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
                gridBagConstraints.insets = new java.awt.Insets(20, 0, 20, 20);
                jPanel1.add(botonContraseña, gridBagConstraints);

                botonConfirmar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/ver.png")));
                botonConfirmar.setMargin(new java.awt.Insets(2, 2, 2, 2));
                botonConfirmar.setPreferredSize(new java.awt.Dimension(30, 30));
                botonConfirmar.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        botonConfirmarActionPerformed(evt);
                    }
                });
                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 2;
                gridBagConstraints.gridy = 2;
                gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
                gridBagConstraints.insets = new java.awt.Insets(20, 0, 20, 20);
                jPanel1.add(botonConfirmar, gridBagConstraints);

                javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
                getContentPane().setLayout(layout);
                layout.setHorizontalGroup(
                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

    private void botonModificarActionPerformed(java.awt.event.ActionEvent evt) {
        if (cajaContraseña.getText().equals(cajaConfirmar.getText())) {
            Usuario usuario = new Usuario();
            usuario.setUsuario(String.valueOf(comboUsuario.getSelectedItem()));
            usuario.setContraseña(String.valueOf(cajaContraseña.getText()));
            if(consultas.modificarUsuario(usuario)){
                JOptionPane.showMessageDialog(null, "La contraseña se ha cambiado con éxito.");
                dispose();
            }else{
                JOptionPane.showMessageDialog(null, "Error al cambiar la contraseña.");
            }
        }else{
            if(cajaConfirmar.getText().equals("") || cajaContraseña.getText().equals("")){
                JOptionPane.showMessageDialog(null, "Rellene todos los campos.");
            }else{
                JOptionPane.showMessageDialog(null, "Las contraseñas no coinciden.");
            }
        }
    }

    private void botonContraseñaActionPerformed(java.awt.event.ActionEvent evt) {
        verContraseña = v.ocultarContraseña(cajaContraseña, verContraseña);
    }

    private void botonConfirmarActionPerformed(java.awt.event.ActionEvent evt) {
        verConfirmar = v.ocultarContraseña(cajaConfirmar, verConfirmar);
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
            java.util.logging.Logger.getLogger(ModificarUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ModificarUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ModificarUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ModificarUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }


        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ModificarUsuario dialog = new ModificarUsuario(new javax.swing.JFrame(), true);
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


    private javax.swing.JButton botonConfirmar;
    private javax.swing.JButton botonContraseña;
    private javax.swing.JButton botonModificar;
    private javax.swing.JPasswordField cajaConfirmar;
    private javax.swing.JPasswordField cajaContraseña;
    private javax.swing.JComboBox<String> comboUsuario;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;

}
