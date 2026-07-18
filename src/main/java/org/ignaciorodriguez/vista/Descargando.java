package org.ignaciorodriguez.vista;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ignaciorodriguez.modelo.Consultas;
import org.apache.commons.io.FileUtils;

public class Descargando extends javax.swing.JDialog {

    Consultas c = Consultas.getInstancia();
    String ruta = c.recuperarRutas("Reportes");
    public Descargando(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(null);
        barra.setValue(0);
        barra.setStringPainted(true);
        botonAbrir.setVisible(false);
        File carpeta = new File(ruta + "\\Instalador");
        if(carpeta.exists()){
            ruta = carpeta.getAbsolutePath();
        } else {
            if(carpeta.mkdir()){
                ruta = carpeta.getAbsolutePath();
            }
        }
        
        try {
            
            URL url = new URL("http://138.36.236.245/Instalador.exe");
            URLConnection urlconn = url.openConnection();
            File archivo = new File(ruta + "\\instalador.exe");
            if(archivo.exists()){
                archivo.delete();
            }
            new Thread(() -> {
                try {
                    Timer timer = new Timer();
                    timer.schedule(new TimerTask() {
                        public void run() {
                            Long Percentageused = (long) ((float) archivo.length() / urlconn.getContentLength() * 100);
                            barra.setValue(Integer.parseInt(Percentageused.toString()));
                            if(Percentageused == 100){
                                timer.cancel();
                            }
                        }
                    }, 0, 200);
                    FileUtils.copyURLToFile(url, archivo);
                    jLabel1.setText("Descarga terminada.");
                    botonAbrir.setVisible(true);
                } catch (IOException ex) {
                    Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
                }
            }).start();

        } catch (MalformedURLException ex) {
            System.err.println("error " + ex);
        } catch (IOException ex) {
            System.err.println("error " + ex);
        }
    }

    @SuppressWarnings("unchecked")

    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        barra = new javax.swing.JProgressBar();
        botonAbrir = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setLayout(new java.awt.GridBagLayout());

        jLabel1.setText("Descargando actualización");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(20, 10, 15, 10);
        jPanel1.add(jLabel1, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 163;
        gridBagConstraints.ipady = 14;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 20, 10);
        jPanel1.add(barra, gridBagConstraints);

        botonAbrir.setText("Instalar actualización");
        botonAbrir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonAbrirActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 15, 10);
        jPanel1.add(botonAbrir, gridBagConstraints);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 271, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }

    private void botonAbrirActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            this.dispose();
            Runtime.getRuntime().exec("explorer.exe /select," + ruta + "\\instalador.exe");
            System.exit(0);
        } catch (IOException ex) {
            Logger.getLogger(Descargando.class.getName()).log(Level.SEVERE, null, ex);
        }
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
            java.util.logging.Logger.getLogger(Descargando.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Descargando.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Descargando.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Descargando.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Descargando dialog = new Descargando(new javax.swing.JFrame(), true);
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

    private javax.swing.JProgressBar barra;
    private javax.swing.JButton botonAbrir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
}
