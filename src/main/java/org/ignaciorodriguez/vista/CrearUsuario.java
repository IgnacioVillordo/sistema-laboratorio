package org.ignaciorodriguez.vista;

import java.awt.Dimension;
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

public class CrearUsuario extends javax.swing.JDialog {

    boolean verContraseña = false;
    boolean verConfirmar = false;
    VerContraseña v = new VerContraseña();
    public CrearUsuario(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(null);
        ImageIcon icon = new ImageIcon("src\\vista\\icono.png");
        this.setIconImage(icon.getImage());
    }

    @SuppressWarnings("unchecked")

    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        botonCrear = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        botonContraseña = new javax.swing.JButton();
        cajaContraseña = new javax.swing.JPasswordField();
        jPanel3 = new javax.swing.JPanel();
        cajaConfirmar = new javax.swing.JPasswordField();
        botonConfirmar = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        cajaUsuario = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jPanel1.setPreferredSize(new java.awt.Dimension(341, 420));
        jPanel1.setLayout(new java.awt.GridBagLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 24));
        jLabel1.setText("Crear usuario");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.ipady = 40;
        gridBagConstraints.insets = new java.awt.Insets(30, 15, 10, 17);
        jPanel1.add(jLabel1, gridBagConstraints);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14));
        jLabel2.setText("Nombre:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(20, 20, 20, 20);
        jPanel1.add(jLabel2, gridBagConstraints);

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14));
        jLabel3.setText("Código:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(20, 20, 20, 20);
        jPanel1.add(jLabel3, gridBagConstraints);

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14));
        jLabel4.setText("Confirmar código:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(20, 20, 20, 20);
        jPanel1.add(jLabel4, gridBagConstraints);

        botonCrear.setFont(new java.awt.Font("Segoe UI", 0, 14));
        botonCrear.setText("Crear usuario");
        botonCrear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonCrearActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 30, 0);
        jPanel1.add(botonCrear, gridBagConstraints);
        InputMap im = jPanel1.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false), "Apretar");

        ActionMap ap = jPanel1.getActionMap();
        ap.put("Apretar", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e){
                botonCrear.doClick();
            }
        });

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.setLayout(new java.awt.GridBagLayout());

        botonContraseña.setBackground(new java.awt.Color(255, 255, 255));
        botonContraseña.setForeground(new java.awt.Color(255, 255, 255));
        botonContraseña.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/ver.png")));
        botonContraseña.setBorder(null);
        botonContraseña.setBorderPainted(false);
        botonContraseña.setContentAreaFilled(false);
        botonContraseña.setMargin(new java.awt.Insets(0, 0, 0, 0));
        botonContraseña.setMaximumSize(new java.awt.Dimension(20, 20));
        botonContraseña.setPreferredSize(new java.awt.Dimension(30, 30));
        botonContraseña.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                botonContraseñaMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                botonContraseñaMouseReleased(evt);
            }
        });
        botonContraseña.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonContraseñaActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel2.add(botonContraseña, gridBagConstraints);

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
                    botonCrear.doClick();
                }}
            };
            cajaContraseña.addKeyListener(eventoTeclado1);
            cajaContraseña.setBorder(null);
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 0;
            gridBagConstraints.ipadx = 120;
            jPanel2.add(cajaContraseña, gridBagConstraints);

            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 1;
            gridBagConstraints.gridy = 2;
            gridBagConstraints.gridwidth = 2;
            gridBagConstraints.insets = new java.awt.Insets(20, 0, 20, 20);
            jPanel1.add(jPanel2, gridBagConstraints);

            jPanel3.setBackground(new java.awt.Color(255, 255, 255));
            jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
            jPanel3.setLayout(new java.awt.GridBagLayout());

            KeyListener eventoTeclado3 = new KeyListener() {
                @Override
                public void keyTyped(KeyEvent ke) {
                }

                @Override
                public void keyPressed(KeyEvent ke) {
                }

                @Override
                public void keyReleased(KeyEvent ke) {
                    if(ke.getKeyChar() == '\n'){
                        botonCrear.doClick();
                    }}
                };
                cajaConfirmar.addKeyListener(eventoTeclado3);
                cajaConfirmar.setBorder(null);
                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 0;
                gridBagConstraints.gridy = 0;
                gridBagConstraints.ipadx = 120;
                jPanel3.add(cajaConfirmar, gridBagConstraints);

                botonConfirmar.setBackground(new java.awt.Color(255, 255, 255));
                botonConfirmar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/ver.png")));
                botonConfirmar.setBorder(null);
                botonConfirmar.setBorderPainted(false);
                botonConfirmar.setContentAreaFilled(false);
                botonConfirmar.setMargin(new java.awt.Insets(0, 0, 0, 0));
                botonConfirmar.setPreferredSize(new java.awt.Dimension(30, 30));
                botonConfirmar.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        botonConfirmarActionPerformed(evt);
                    }
                });
                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 1;
                gridBagConstraints.gridy = 0;
                gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
                jPanel3.add(botonConfirmar, gridBagConstraints);

                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 1;
                gridBagConstraints.gridy = 3;
                gridBagConstraints.gridwidth = 2;
                gridBagConstraints.insets = new java.awt.Insets(20, 0, 20, 20);
                jPanel1.add(jPanel3, gridBagConstraints);

                jPanel4.setBackground(new java.awt.Color(255, 255, 255));
                jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());
                jPanel4.setMinimumSize(new java.awt.Dimension(141, 21));
                jPanel4.setPreferredSize(new java.awt.Dimension(154, 34));
                jPanel4.setLayout(new java.awt.GridBagLayout());

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
                            botonCrear.doClick();
                        }}
                    };
                    cajaUsuario.addKeyListener(eventoTeclado);
                    cajaUsuario.setBorder(null);
                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 0;
                    gridBagConstraints.ipadx = 137;
                    jPanel4.add(cajaUsuario, gridBagConstraints);
                    Dimension caja = cajaContraseña.getSize();
                    cajaUsuario.setSize(caja);

                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 1;
                    gridBagConstraints.gridy = 1;
                    gridBagConstraints.gridwidth = 2;
                    gridBagConstraints.insets = new java.awt.Insets(20, 0, 20, 20);
                    jPanel1.add(jPanel4, gridBagConstraints);

                    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
                    getContentPane().setLayout(layout);
                    layout.setHorizontalGroup(
                        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 409, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 409, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                    );
                    layout.setVerticalGroup(
                        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 341, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 341, Short.MAX_VALUE))
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

    private void botonCrearActionPerformed(java.awt.event.ActionEvent evt) {
        Usuario usuario = new Usuario();
        Consultas consultas = Consultas.getInstancia();
        if (cajaUsuario.getText().equals("") || cajaConfirmar.getText().equals("") || cajaContraseña.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Rellene todos los campos");
        } else {
            if (cajaContraseña.getText().equals(cajaConfirmar.getText())) {
                usuario.setUsuario(cajaUsuario.getText());
                usuario.setContraseña(cajaContraseña.getText());

                if (consultas.insertarUsuario(usuario)) {
                    JOptionPane.showMessageDialog(null, "El usuario se registro correctamente.");
                } else {
                    JOptionPane.showMessageDialog(null, "Error al registrar usuario.");
                }
            } else{
                JOptionPane.showMessageDialog(null, "Las contraseñas no coinciden.");
            }
        }
        this.dispose();
    }

    private void botonContraseñaMousePressed(java.awt.event.MouseEvent evt) {

    }

    private void botonContraseñaMouseReleased(java.awt.event.MouseEvent evt) {

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
            java.util.logging.Logger.getLogger(CrearUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CrearUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CrearUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CrearUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }


        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                CrearUsuario dialog = new CrearUsuario(new javax.swing.JFrame(), true);
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
    public javax.swing.JButton botonCrear;
    public javax.swing.JPasswordField cajaConfirmar;
    public javax.swing.JPasswordField cajaContraseña;
    public javax.swing.JTextField cajaUsuario;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;

}
