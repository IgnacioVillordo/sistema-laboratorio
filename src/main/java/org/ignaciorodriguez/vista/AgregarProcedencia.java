package org.ignaciorodriguez.vista;

import org.ignaciorodriguez.modelo.Cliente;
import org.ignaciorodriguez.modelo.Consultas;
import org.ignaciorodriguez.repository.ClienteRepository;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

public class AgregarProcedencia extends javax.swing.JDialog {

    Consultas consultas = Consultas.getInstancia();
    Principal p = new Principal();
    int id;
    String[] datos = new String[7];
    boolean editar = false, muestra = false;
    ClienteRepository clienteRepository = new ClienteRepository();

    public AgregarProcedencia(java.awt.Frame parent, boolean modal, boolean editar, int id, boolean muestra) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(null);
        ImageIcon icon = new ImageIcon("src\\vista\\icono.png");
        this.editar = editar;
        this.muestra = muestra;
        this.setIconImage(icon.getImage());
        this.id = id;
        if (editar) {
            botonAgregar.setText("Editar cliente");
            datos = clienteRepository.recuperarDatosCliente(id);
            id = Integer.parseInt(datos[0]);
            if (datos[1].equals("")) {
                botonEmpresa.doClick();
            } else {
                cajaEmpresa.setText(datos[1]);
            }
            if (datos[2].equals("")) {
                botonNombre.doClick();
            } else {
                cajaNombre.setText(datos[2]);
            }
            cajaDireccion.setText(datos[3]);
            cajaCiudad.setText(datos[4]);
            cajaTelefono.setText(datos[5]);
            cajaEmail.setText(datos[6]);
            cajaCuit.setText(datos[7]);
        }
    }

    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        cajaEmpresa = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        cajaNombre = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        cajaDireccion = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        cajaCiudad = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        cajaTelefono = new javax.swing.JTextField();
        cajaEmail = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        botonAgregar = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        botonEmpresa = new javax.swing.JToggleButton();
        jPanel4 = new javax.swing.JPanel();
        botonNombre = new javax.swing.JToggleButton();
        jLabel8 = new javax.swing.JLabel();
        cajaCuit = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setLayout(new java.awt.GridBagLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel1.setText("Empresa:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 20, 20);
        jPanel1.add(jLabel1, gridBagConstraints);

        cajaEmpresa.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        cajaEmpresa.setMaximumSize(new java.awt.Dimension(201, 20));
        cajaEmpresa.setMinimumSize(new java.awt.Dimension(201, 20));
        cajaEmpresa.setPreferredSize(new java.awt.Dimension(201, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 20, 20, 20);
        jPanel1.add(cajaEmpresa, gridBagConstraints);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel2.setText("Nombre:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 20, 20);
        jPanel1.add(jLabel2, gridBagConstraints);

        cajaNombre.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        cajaNombre.setMaximumSize(new java.awt.Dimension(201, 20));
        cajaNombre.setMinimumSize(new java.awt.Dimension(201, 20));
        cajaNombre.setPreferredSize(new java.awt.Dimension(201, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 20, 20, 20);
        jPanel1.add(cajaNombre, gridBagConstraints);

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel3.setText("Dirección:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 20, 20);
        jPanel1.add(jLabel3, gridBagConstraints);

        cajaDireccion.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        cajaDireccion.setMaximumSize(new java.awt.Dimension(201, 20));
        cajaDireccion.setMinimumSize(new java.awt.Dimension(201, 20));
        cajaDireccion.setPreferredSize(new java.awt.Dimension(201, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 20, 20, 20);
        jPanel1.add(cajaDireccion, gridBagConstraints);

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel4.setText("Ciudad:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 20, 20);
        jPanel1.add(jLabel4, gridBagConstraints);

        cajaCiudad.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        cajaCiudad.setMaximumSize(new java.awt.Dimension(201, 20));
        cajaCiudad.setMinimumSize(new java.awt.Dimension(201, 20));
        cajaCiudad.setPreferredSize(new java.awt.Dimension(201, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 20, 20, 20);
        jPanel1.add(cajaCiudad, gridBagConstraints);

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel5.setText("Teléfono:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 20, 20);
        jPanel1.add(jLabel5, gridBagConstraints);

        cajaTelefono.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        cajaTelefono.setMaximumSize(new java.awt.Dimension(201, 20));
        cajaTelefono.setMinimumSize(new java.awt.Dimension(201, 20));
        cajaTelefono.setPreferredSize(new java.awt.Dimension(201, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 20, 20, 20);
        jPanel1.add(cajaTelefono, gridBagConstraints);

        cajaEmail.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        cajaEmail.setMaximumSize(new java.awt.Dimension(201, 20));
        cajaEmail.setMinimumSize(new java.awt.Dimension(201, 20));
        cajaEmail.setPreferredSize(new java.awt.Dimension(201, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 20, 20, 20);
        jPanel1.add(cajaEmail, gridBagConstraints);

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel6.setText("E-mail:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 20, 20);
        jPanel1.add(jLabel6, gridBagConstraints);

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel7.setText("Ingrese los datos del nuevo cliente:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets = new java.awt.Insets(10, 20, 10, 20);
        jPanel1.add(jLabel7, gridBagConstraints);

        botonAgregar.setText("Agregar cliente");
        botonAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonAgregarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 2;
        jPanel1.add(botonAgregar, gridBagConstraints);
        InputMap im = jPanel1.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false), "Apretar");

        ActionMap ap = jPanel1.getActionMap();
        ap.put("Apretar", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e){
                botonAgregar.doClick();
            }
        });

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel3.setPreferredSize(new java.awt.Dimension(15, 15));
        jPanel3.setLayout(new java.awt.GridBagLayout());

        botonEmpresa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/blanco10.jpg"))); // NOI18N
        botonEmpresa.setContentAreaFilled(false);
        botonEmpresa.setMargin(new java.awt.Insets(0, 0, 0, 0));
        botonEmpresa.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/x.png"))); // NOI18N
        botonEmpresa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonEmpresaActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jPanel3.add(botonEmpresa, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 20, 0);
        jPanel1.add(jPanel3, gridBagConstraints);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel4.setPreferredSize(new java.awt.Dimension(15, 15));
        jPanel4.setLayout(new java.awt.GridBagLayout());

        botonNombre.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/blanco10.jpg"))); // NOI18N
        botonNombre.setContentAreaFilled(false);
        botonNombre.setMargin(new java.awt.Insets(0, 0, 0, 0));
        botonNombre.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/x.png"))); // NOI18N
        botonNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonNombreActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jPanel4.add(botonNombre, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 20, 0);
        jPanel1.add(jPanel4, gridBagConstraints);

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel8.setText("C.U.I.T.");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 20, 20);
        jPanel1.add(jLabel8, gridBagConstraints);

        cajaCuit.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        cajaCuit.setMaximumSize(new java.awt.Dimension(201, 20));
        cajaCuit.setMinimumSize(new java.awt.Dimension(201, 20));
        cajaCuit.setPreferredSize(new java.awt.Dimension(201, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 20, 20, 20);
        jPanel1.add(cajaCuit, gridBagConstraints);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 423, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 467, Short.MAX_VALUE)
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

    private void botonEmpresaActionPerformed(java.awt.event.ActionEvent evt) {
        if (cajaEmpresa.isEnabled()) {
            cajaEmpresa.setEnabled(false);
            botonNombre.setEnabled(false);
        } else {
            cajaEmpresa.setEnabled(true);
            botonNombre.setEnabled(true);
        }
    }

    private void botonNombreActionPerformed(java.awt.event.ActionEvent evt) {
        if (cajaNombre.isEnabled()) {
            cajaNombre.setEnabled(false);
            botonEmpresa.setEnabled(false);
        } else {
            cajaNombre.setEnabled(true);
            botonEmpresa.setEnabled(true);
        }
    }

    private void botonAgregarActionPerformed(java.awt.event.ActionEvent evt) {
        System.out.println(cajaCiudad.getWidth());
        if (editar) {
            Cliente c = new Cliente();
            if (cajaEmpresa.isEnabled()) {
                c.setEmpresa(cajaEmpresa.getText());
            }
            if (cajaNombre.isEnabled()) {
                c.setNombre(cajaNombre.getText());
            }
            c.setDireccion(cajaDireccion.getText());
            c.setCiudad(cajaCiudad.getText());
            c.setTelefono(cajaTelefono.getText());
            c.setEmail(cajaEmail.getText());
            c.setCuit(cajaCuit.getText());
            if (clienteRepository.editarCliente(c, id)) {
                JOptionPane.showMessageDialog(null, "Cliente editado con éxito.");
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Error al editar cliente.");
            }
        } else {
            Cliente c = new Cliente();
            if (cajaEmpresa.isEnabled()) {
                c.setEmpresa(cajaEmpresa.getText());
            }
            if (cajaNombre.isEnabled()) {
                c.setNombre(cajaNombre.getText());
            }
            c.setDireccion(cajaDireccion.getText());
            c.setCiudad(cajaCiudad.getText());
            c.setTelefono(cajaTelefono.getText());
            c.setEmail(cajaEmail.getText());
            c.setCuit(cajaCuit.getText());
            if (clienteRepository.agregarCliente(c)) {
                JOptionPane.showMessageDialog(null, "Cliente ingresado con éxito.");
                dispose();
                if (muestra) {
                    AgregarMuestra am = new AgregarMuestra(p, true);
                    am.setVisible(true);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Error al ingresar cliente.");

            }
        }
    }

    private javax.swing.JButton botonAgregar;
    private javax.swing.JToggleButton botonEmpresa;
    private javax.swing.JToggleButton botonNombre;
    private javax.swing.JTextField cajaCiudad;
    private javax.swing.JTextField cajaCuit;
    private javax.swing.JTextField cajaDireccion;
    private javax.swing.JTextField cajaEmail;
    private javax.swing.JTextField cajaEmpresa;
    private javax.swing.JTextField cajaNombre;
    private javax.swing.JTextField cajaTelefono;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
}
