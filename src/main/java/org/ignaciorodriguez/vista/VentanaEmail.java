package org.ignaciorodriguez.vista;

import com.sun.mail.smtp.SMTPTransport;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
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
import org.ignaciorodriguez.modelo.Usuario;

/**
 *
 * @author Nacho
 */
public class VentanaEmail extends javax.swing.JDialog {

    Consultas c = Consultas.getInstancia();
    String email;
    int id;
    String pdf;
    boolean editar = false;
    Frame parent;
    private static final Dotenv dotenv = Dotenv.load();
    public VentanaEmail(java.awt.Frame parent, boolean modal, int id, String pdf) {
        super(parent, modal);
        this.parent = parent;
        this.id = id;
        Date hora = Date.from(Instant.now());
        this.pdf = pdf;
        initComponents();
        if (hora.getHours() < 12) {
            cajaCuerpo.setText("Buenos días, adjunto informes.\n"
                    + "Saludos cordiales.");
        }
        setLocationRelativeTo(null);
        email = c.recuperarEmail(id);
        cajaPara.setText(email);
        etiquetaAdjunto.setText("<html>Archivo adjunto: " + pdf.substring(pdf.indexOf("Inf. ")) + "</html>");
        System.setProperty("javax.net.ssl.trustStore", "src\\vista\\keystore.jks");
        System.setProperty("javax.net.ssl.trustStorePassword", "laboratorioav");
    }

    public VentanaEmail(java.awt.Frame parent, boolean modal, String pdf, String destinatario, String remitente, String cuerpo) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(null);
        cajaPara.setText(destinatario);
        comboEmail.setSelectedItem(remitente);
        etiquetaAdjunto.setText("Archivo adjunto: " + pdf.substring(pdf.indexOf("Inf. ")));
        this.pdf = pdf;
        etiquetaFirma.setText("<html><b>" + cuerpo.substring(cuerpo.indexOf("<b>")));
        cajaCuerpo.setText(cuerpo.substring(cuerpo.indexOf("<html>") + 6, cuerpo.indexOf("<br><b>")).replaceAll("<br>", "\n"));
        cajaPara.setEditable(false);
        comboEmail.setEnabled(false);
        cajaCuerpo.setEditable(false);
        cajaAsunto.setEditable(false);
        editar = true;
        jButton1.setVisible(false);
    }

    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        comboEmail = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        cajaAsunto = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        cajaCuerpo = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();
        etiquetaFirma = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        etiquetaAdjunto = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        cajaPara = new javax.swing.JTextArea();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setLayout(new java.awt.GridBagLayout());

        comboEmail.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "avillordo@laboratorioav.com.ar", "entregaresultados@laboratorioav.com.ar", "info@laboratorioav.com.ar", "administracion@laboratorioav.com.ar" }));
        comboEmail.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comboEmailItemStateChanged(evt);
            }
        });
        comboEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboEmailActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 0);
        jPanel1.add(comboEmail, gridBagConstraints);

        jLabel1.setText("Asunto:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 10, 10);
        jPanel1.add(jLabel1, gridBagConstraints);

        cajaAsunto.setText("Informes análisis");
        cajaAsunto.setMaximumSize(new java.awt.Dimension(450, 22));
        cajaAsunto.setMinimumSize(new java.awt.Dimension(450, 22));
        cajaAsunto.setPreferredSize(new java.awt.Dimension(450, 22));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 55, 10, 10);
        jPanel1.add(cajaAsunto, gridBagConstraints);

        jScrollPane1.setMaximumSize(new java.awt.Dimension(500, 150));
        jScrollPane1.setMinimumSize(new java.awt.Dimension(500, 150));
        jScrollPane1.setPreferredSize(new java.awt.Dimension(500, 150));

        cajaCuerpo.setColumns(20);
        cajaCuerpo.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        cajaCuerpo.setLineWrap(true);
        cajaCuerpo.setRows(5);
        cajaCuerpo.setText("Buenas tardes, adjunto informes.\nSaludos cordiales.");
        cajaCuerpo.setMaximumSize(new java.awt.Dimension(500, 150));
        cajaCuerpo.setMinimumSize(new java.awt.Dimension(500, 150));
        cajaCuerpo.setPreferredSize(new java.awt.Dimension(500, 150));
        jScrollPane1.setViewportView(cajaCuerpo);
        jScrollPane1.setBorder(cajaAsunto.getBorder());

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

        etiquetaFirma.setText("<html><b>Lic. Alejandra Villordo<br>Laboratorio Bromatológico Alejandra Villordo<br>John O'Connor 594 - Bariloche (R.N.)<br>Tel: 0294-4426008- +5492944571401<br>info@laboratorioav.com.ar</b></html>");
        etiquetaFirma.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                etiquetaFirmaMousePressed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 10, 10);
        jPanel1.add(etiquetaFirma, gridBagConstraints);

        jLabel2.setText("Para:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel1.add(jLabel2, gridBagConstraints);

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

        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane2.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane2.setMaximumSize(new java.awt.Dimension(455, 42));
        jScrollPane2.setMinimumSize(new java.awt.Dimension(455, 42));
        jScrollPane2.setPreferredSize(new java.awt.Dimension(455, 42));

        cajaPara.setColumns(60);
        cajaPara.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        cajaPara.setLineWrap(true);
        cajaPara.setRows(2);
        cajaPara.setWrapStyleWord(true);
        cajaPara.setMaximumSize(new java.awt.Dimension(455, 42));
        cajaPara.setMinimumSize(new java.awt.Dimension(455, 42));
        jScrollPane2.setViewportView(cajaPara);
        jScrollPane2.setBorder(cajaAsunto.getBorder());

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 55, 0, 10);
        jPanel1.add(jScrollPane2, gridBagConstraints);

        jLabel3.setFont(new java.awt.Font("Dialog", 0, 10)); // NOI18N
        jLabel3.setText("Para múltiples destinatarios, separar los emails con coma.");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 10, 0);
        jPanel1.add(jLabel3, gridBagConstraints);

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
    }

    private void comboEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboEmailActionPerformed
        // TODO add your handling code here:
    }

    private void comboEmailItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboEmailItemStateChanged

        String aux = evt.getItem().toString();
        switch (aux) {
            case "avillordo@laboratorioav.com.ar":
                etiquetaFirma.setText("<html><b>Lic. Alejandra Villordo<br>Laboratorio "
                        + "Bromatológico Alejandra Villordo<br>John O'Connor 594 - "
                        + "Bariloche (R.N.)<br>Tel: 0294-4426008- +5492944571401<br>"
                        + "info@laboratorioav.com.ar</b></html>");
                break;
            case "entregaresultados@laboratorioav.com.ar":
                etiquetaFirma.setText("<html><b>Laboratorio "
                        + "Bromatológico Alejandra Villordo<br>John O'Connor 594 - "
                        + "Bariloche (R.N.)<br>Tel: 0294-4426008- +5492944571401<br>"
                        + "info@laboratorioav.com.ar</b></html>");
                break;
            case "info@laboratorioav.com.ar":
                etiquetaFirma.setText("<html><b>Mariana Rosales<br>Laboratorio "
                        + "Bromatológico Alejandra Villordo<br>John O'Connor 594 - "
                        + "Bariloche (R.N.)<br>Tel: 0294-4426008- +5492944571401<br>"
                        + "info@laboratorioav.com.ar</b></html>");
                break;
            case "administracion@laboratorioav.com.ar":
                etiquetaFirma.setText("<html><b>Patricia Atenas<br>Laboratorio "
                        + "Bromatológico Alejandra Villordo<br>John O'Connor 594 - "
                        + "Bariloche (R.N.)<br>Tel: 0294-4426008- +5492944571401<br>"
                        + "info@laboratorioav.com.ar</b></html>");
                break;
        }
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Cursor cursor = new Cursor(Cursor.WAIT_CURSOR);
        this.setCursor(cursor);
        enviarEmail();
        this.dispose();
    }

    private void etiquetaFirmaMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_etiquetaFirmaMousePressed
        if (!editar) {
            String auxFirma = etiquetaFirma.getText();
            int idbr = auxFirma.indexOf("<br>");
            String usuario = JOptionPane.showInputDialog("Ingrese su nombre");
            etiquetaFirma.setText("<html><b>" + usuario + auxFirma.substring(idbr));
        }

    }

    private void etiquetaAdjuntoMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_etiquetaAdjuntoMousePressed
        System.out.println("Antes pdf");
        File archivo = new File(pdf);
        System.out.println("archivo.getAbsolutePath() = " + archivo.getAbsolutePath());
        try {
            Desktop.getDesktop().open(archivo);
        } catch (IOException ex) {
            Logger.getLogger(VentanaEmail.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception e) {
        }
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                VentanaEmail dialog = new VentanaEmail(new javax.swing.JFrame(), true, -1, "");
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


    private javax.swing.JTextField cajaAsunto;
    private javax.swing.JTextArea cajaCuerpo;
    private javax.swing.JTextArea cajaPara;
    private javax.swing.JComboBox<String> comboEmail;
    private javax.swing.JLabel etiquetaAdjunto;
    private javax.swing.JLabel etiquetaFirma;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;

    private void enviarEmail() {
        String to = cajaPara.getText().replace(" ", "");

        String from = comboEmail.getSelectedItem().toString();
        Properties props = System.getProperties();

        props.put("mail.smtps.host", dotenv.get("EMAIL_HOST"));
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", dotenv.get("PORT"));
        props.put("mail.smtp.user", from);
        props.put("mail.smtp.password", dotenv.get("EMAIL_PASS"));
        props.put("mail.smtp.socketFactory.port", dotenv.get("PORT"));
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.quitwait", "false");
        System.setProperty("mail.mime.encodeparameters", "false");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.protocols", "TLSv1.3");
        // Get the Session object.// and pass username and password
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, dotenv.get("EMAIL_PASS"));
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));
            // Set Subject: header field
            message.setSubject(cajaAsunto.getText());

            String cuerpo = cajaCuerpo.getText().replace("\n", "<br>");

            MimeBodyPart textBodyPart = new MimeBodyPart();
            textBodyPart.setContent("<html>" + cuerpo + "<br>" + etiquetaFirma.getText().substring(6), "text/html");
            Multipart multipart = new MimeMultipart();
            BodyPart messageBodyPart = new MimeBodyPart();
            System.out.println("despues source");
            DataSource source = new FileDataSource(pdf);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(pdf.substring(pdf.indexOf("Inf. ")));
            multipart.addBodyPart(textBodyPart);
            multipart.addBodyPart(messageBodyPart);
            message.setContent(multipart);

            // Send message
            SMTPTransport t = (SMTPTransport) session.getTransport("smtps");

// Connect to the server using credentials
            t.connect(dotenv.get("EMAIL_HOST"), from, dotenv.get("EMAIL_PASS"));

// Send the message
            t.sendMessage(message, message.getAllRecipients());
            copyIntoSent(session, message);
        } catch (MessagingException mex) {
            JOptionPane.showMessageDialog(null, "Error al enviar email.");
            System.err.println(mex);
        }
        if (!email.equals(cajaPara.getText())) {
            int guardar;
            if (to.contains(",")) {
                guardar = JOptionPane.showConfirmDialog(null, "Emails enviado, ¿desea guardar los emails " + cajaPara.getText().replaceAll(" ", "") + " para el cliente " + c.obtenerProcedencia(id));
            } else {
                guardar = JOptionPane.showConfirmDialog(null, "Email enviado, ¿desea guardar el email " + cajaPara.getText().replaceAll(" ", "") + " para el cliente " + c.obtenerProcedencia(id));
            }
            if (guardar == JOptionPane.YES_OPTION) {
                c.guardarEmail(cajaPara.getText(), c.obtenerIdCliente(c.obtenerProcedencia(id)));
            }
        } else {
            JOptionPane.showMessageDialog(null, "Email enviado.");
        }
        Usuarios usuarios = new Usuarios(parent, true);
        usuarios.setVisible(true); //ventana para elegir usuario visible
        String nombre = "error"; //inicio de variable nombre con el nombre del usuario
        nombre = usuarios.nombreUsuario();
        Usuario usuario = new Usuario();
        usuario.setUsuario(nombre);
        usuario.setId(c.obtenerIdUsuario(nombre));
        if (!c.hayEntrega(id)) {
            c.entregarMuestra(usuario, id);
            c.entregado(id);
        }
    }

    private void copyIntoSent(final Session session, final Message msg) throws MessagingException {
        final Store store = session.getStore("imaps");
        // Remitente
        String from = comboEmail.getSelectedItem().toString();

        store.connect(dotenv.get("EMAIL_HOST"), from, dotenv.get("EMAIL_PASS"));

        final Folder folder = (Folder) store.getFolder("Inbox").getFolder("Sent Items");
        folder.open(Folder.READ_WRITE);

        folder.appendMessages(new Message[]{msg});
    }
}
