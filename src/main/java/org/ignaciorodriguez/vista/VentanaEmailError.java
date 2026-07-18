package org.ignaciorodriguez.vista;

import com.sun.mail.smtp.SMTPTransport;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.UIManager;

import io.github.cdimascio.dotenv.Dotenv;
import org.ignaciorodriguez.modelo.Consultas;

/**
 *
 * @author Nacho
 */
public class VentanaEmailError extends javax.swing.JDialog {

    Consultas c = Consultas.getInstancia();
    String ruta = c.recuperarRutas("Respaldo");
    File copia = new File(c.devolverCopiaSeguridad(ruta));

    public VentanaEmailError(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        Thread t = new Thread(){
            public void run(){
                crearBackup(ruta);
            }
        };
        t.start();
        initComponents();
        TextPlaceholder text = new TextPlaceholder("Describa el problema e ingrese los ID y tipos de análisis de ser necesario.", cajaCuerpo);
        setLocationRelativeTo(null);
        cajaPara.setText("ignaciorodriguezv28@gmail.com");
        etiquetaAdjunto.setText("<html>Archivo adjunto: " + copia.getName());
        System.setProperty("javax.net.ssl.trustStore", "src\\vista\\keystore.jks");
        System.setProperty("javax.net.ssl.trustStorePassword", Dotenv.load().get("KEYSTORE_PASS"));

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        cajaAsunto = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        cajaCuerpo = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        cajaPara = new javax.swing.JTextField();
        etiquetaAdjunto = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setLayout(new java.awt.GridBagLayout());

        jLabel1.setText("Asunto:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel1.add(jLabel1, gridBagConstraints);

        cajaAsunto.setText("Error en el programa");
        cajaAsunto.setMaximumSize(new java.awt.Dimension(450, 22));
        cajaAsunto.setMinimumSize(new java.awt.Dimension(450, 22));
        cajaAsunto.setPreferredSize(new java.awt.Dimension(450, 22));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 55, 10, 10);
        jPanel1.add(cajaAsunto, gridBagConstraints);

        jScrollPane1.setMaximumSize(new java.awt.Dimension(500, 150));
        jScrollPane1.setMinimumSize(new java.awt.Dimension(500, 150));
        jScrollPane1.setPreferredSize(new java.awt.Dimension(500, 150));

        cajaCuerpo.setColumns(20);
        cajaCuerpo.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        cajaCuerpo.setLineWrap(true);
        cajaCuerpo.setRows(5);
        cajaCuerpo.setMaximumSize(new java.awt.Dimension(500, 150));
        cajaCuerpo.setMinimumSize(new java.awt.Dimension(500, 150));
        cajaCuerpo.setPreferredSize(new java.awt.Dimension(500, 150));
        jScrollPane1.setViewportView(cajaCuerpo);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 10);
        jPanel1.add(jScrollPane1, gridBagConstraints);

        jButton1.setText("Enviar email");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 10);
        jPanel1.add(jButton1, gridBagConstraints);

        jLabel2.setText("Para:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel1.add(jLabel2, gridBagConstraints);

        cajaPara.setEditable(false);
        cajaPara.setBackground(Color.WHITE);
        cajaPara.setMaximumSize(new java.awt.Dimension(450, 22));
        cajaPara.setMinimumSize(new java.awt.Dimension(450, 22));
        cajaPara.setPreferredSize(new java.awt.Dimension(450, 22));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 55, 10, 10);
        jPanel1.add(cajaPara, gridBagConstraints);

        etiquetaAdjunto.setText("Archivo adjunto: ");
        etiquetaAdjunto.setMaximumSize(new java.awt.Dimension(500, 40));
        etiquetaAdjunto.setMinimumSize(new java.awt.Dimension(500, 40));
        etiquetaAdjunto.setPreferredSize(new java.awt.Dimension(500, 40));
        etiquetaAdjunto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                etiquetaAdjuntoMousePressed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 10);
        jPanel1.add(etiquetaAdjunto, gridBagConstraints);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 584, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Cursor cursor = new Cursor(Cursor.WAIT_CURSOR);
        this.setCursor(cursor);
        this.dispose();
        new MyThread().start();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void etiquetaAdjuntoMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_etiquetaAdjuntoMousePressed

    }//GEN-LAST:event_etiquetaAdjuntoMousePressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception e) {
        }
        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                VentanaEmailError dialog = new VentanaEmailError(new javax.swing.JFrame(), true);
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
    private javax.swing.JTextField cajaAsunto;
    private javax.swing.JTextArea cajaCuerpo;
    private javax.swing.JTextField cajaPara;
    private javax.swing.JLabel etiquetaAdjunto;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables

    public class MyThread extends Thread {

        @Override
        public void run() {
            Thread t = new Thread() {
                @Override
                public void run() {
                    String to = cajaPara.getText();

                    // Remitente
                    String from = "info@laboratorioav.com.ar";

                    // Get system properties
                    Properties props = System.getProperties();

                    // configurando servidro
                    props.put("mail.smtps.host", Dotenv.load().get("EMAIL_HOST"));
                    props.put("mail.smtp.auth", "true");
                    props.put("mail.smtp.port", "465");
                    props.put("mail.smtp.user", from);
                    props.put("mail.smtp.password", Dotenv.load().get("EMAIL_PASS"));
                    props.put("mail.smtp.starttls.enable", "true");
                    props.put("mail.smtp.socketFactory.port", "465");
                    props.put("mail.smtp.socketFactory.class",
                            "javax.net.ssl.SSLSocketFactory");
                    props.put("mail.smtp.socketFactory.fallback", "false");
                    props.setProperty("mail.smtp.quitwait", "false");

                    // Get the Session object.// and pass username and password
                    Session session = Session.getInstance(props, null);

                    try {
                        // Create a default MimeMessage object.
                        MimeMessage message = new MimeMessage(session);

                        // Set From: header field of the header.
                        message.setFrom(new InternetAddress(from));

                        // Set To: header field of the header.
                        message.setRecipients(Message.RecipientType.TO,
                                InternetAddress.parse(to));

                        // Set Subject: header field
                        message.setSubject(cajaAsunto.getText());

                        MimeBodyPart textBodyPart = new MimeBodyPart();
                        textBodyPart.setText(cajaCuerpo.getText() + "\n" + Inicial.VERSION);
                        Multipart multipart = new MimeMultipart();
                        BodyPart messageBodyPart = new MimeBodyPart();
                        DataSource source = new FileDataSource(copia);
                        messageBodyPart.setDataHandler(new DataHandler(source));
                        messageBodyPart.setFileName(copia.getAbsolutePath().substring(copia.getAbsolutePath().indexOf("respaldo")));
                        multipart.addBodyPart(textBodyPart);
                        multipart.addBodyPart(messageBodyPart);
                        message.setContent(multipart);

                        // Send message
                        SMTPTransport transport = (SMTPTransport) session.getTransport("smtps");

// Connect to the server using credentials
                        transport.connect(Dotenv.load().get("EMAIL_HOST"), from, Dotenv.load().get("EMAIL_PASS"));

// Send the message
                        transport.sendMessage(message, message.getAllRecipients());
                        JOptionPane.showMessageDialog(null, "Email enviado.");
                        copyIntoSent(session, message);
                    } catch (MessagingException mex) {
                        JOptionPane.showMessageDialog(null, "Error al enviar email.");
                        System.err.println(mex);
                    }
                }
            };
            t.run();

        }

        private void copyIntoSent(final Session session, final Message msg) throws MessagingException {
            final Store store = session.getStore("imaps");
            // Remitente
            String from = "info@laboratorioav.com.ar";
            store.connect(Dotenv.load().get("EMAIL_HOST"), from, Dotenv.load().get("EMAIL_PASS"));

            final Folder folder = (Folder) store.getFolder("Inbox").getFolder("Sent Items");
            folder.open(Folder.READ_WRITE);

            folder.appendMessages(new Message[]{msg});
        }

    }

    public boolean crearBackup(String ruta) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDateTime ahora = LocalDateTime.now();
        String fecha = dtf.format(ahora);
        String nombreSQL = "\\respaldo_" + fecha + ".sql";
        try {
            String cmd = c.recuperarRutas("MySQL") + "\\mysqldump -u root -p1234 laboratorio --routines -r " + ruta + nombreSQL;
            Runtime.getRuntime().exec(cmd);
            return true;
        } catch (Exception ex) {
            System.out.println("Error, " + ex);
            return false;
        }
    }

}
