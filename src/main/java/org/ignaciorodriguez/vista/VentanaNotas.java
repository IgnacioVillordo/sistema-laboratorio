package org.ignaciorodriguez.vista;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import org.ignaciorodriguez.modelo.Consultas;
import org.ignaciorodriguez.repository.MuestraRepository;


public class VentanaNotas extends javax.swing.JDialog {

    int id;
    Consultas c = Consultas.getInstancia();
    MuestraRepository muestraRepository = new MuestraRepository();
    public VentanaNotas(java.awt.Frame parent, boolean modal, int id) {
        super(parent, modal);
        this.id = id;
        initComponents();
        setLocationRelativeTo(null);
        etiquetaTitulo.setText("NOTAS DEL ANÁLISIS " + id + ":");
        campoNotas.setText(muestraRepository.recuperarNota(id));
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        etiquetaTitulo = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        campoNotas = new javax.swing.JTextArea();
        botonCerrar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        etiquetaTitulo.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        etiquetaTitulo.setText("NOTAS:");

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setMaximumSize(new java.awt.Dimension(200, 50));
        jScrollPane1.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane1.setPreferredSize(new java.awt.Dimension(200, 50));

        campoNotas.setColumns(10);
        campoNotas.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        campoNotas.setLineWrap(true);
        campoNotas.setRows(5);
        jScrollPane1.setViewportView(campoNotas);

        botonCerrar.setText("Cerrar");
        botonCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonCerrarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(etiquetaTitulo)
                        .addGap(0, 306, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(botonCerrar)
                .addGap(15, 15, 15))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(etiquetaTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(botonCerrar)
                .addContainerGap(11, Short.MAX_VALUE))
        );

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
    }// </editor-fold>//GEN-END:initComponents

    private void botonCerrarActionPerformed(java.awt.event.ActionEvent evt) {
        String nota = campoNotas.getText();
        muestraRepository.guardarNota(id, nota);
        this.dispose();
    }

    private void formWindowClosed(java.awt.event.WindowEvent evt) {
        String nota = campoNotas.getText();
        muestraRepository.guardarNota(id, nota);
    }

    private javax.swing.JButton botonCerrar;
    private javax.swing.JTextArea campoNotas;
    private javax.swing.JLabel etiquetaTitulo;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
}
