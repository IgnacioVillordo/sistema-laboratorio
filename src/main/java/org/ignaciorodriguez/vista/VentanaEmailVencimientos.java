package org.ignaciorodriguez.vista;

import com.sun.mail.smtp.SMTPTransport;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.UIManager;

import io.github.cdimascio.dotenv.Dotenv;
import org.ignaciorodriguez.modelo.Consultas;
import org.ignaciorodriguez.repository.MuestraRepository;

public class VentanaEmailVencimientos extends javax.swing.JDialog {

    Consultas c = Consultas.getInstancia();
    String email, vencimiento, tipo;
    int id;
    boolean editar = false;
    MuestraRepository muestraRepository = new MuestraRepository();

    public VentanaEmailVencimientos(java.awt.Frame parent, boolean modal, int id) {
        super(parent, modal);
        this.id = id;
        Date hora = Date.from(Instant.now());
        initComponents();
        String texto = "";
        if (hora.getHours() < 12) {
            texto = "Buenos días, ";
        } else {
            texto = "Buenas tardes, ";
        }
        setLocationRelativeTo(null);
        String[] aux = c.recuperarEmailYVencimiento(id);
        SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat df2 = new SimpleDateFormat("dd/MM/yyyy");
        try {
            vencimiento = df2.format(df1.parse(aux[1]));
        } catch (ParseException ex) {
            Logger.getLogger(VentanaEmailVencimientos.class.getName()).log(Level.SEVERE, null, ex);
        }
        email = aux[0];
        cajaPara.setText(email);
        tipo = (aux[3].contains("Microbiológico de agua") ? "microbiológico de agua"
                : aux[3].contains("Físico químico de agua") ? "físico químico de agua"
                : aux[3].contains("Efluentes") ? "efluentes"
                : aux[3].contains("Hisopados") ? "hisopados"
                : aux[3].contains("Microbiológico de chocolates") ? "microbiológico de chocolates"
                : aux[3].contains("Base helada") ? "de base helada"
                : aux[3].contains("Microbiológico de alimentos") ? "microbiológico de alimentos" : "");
        boolean cliente = aux[4].equals("Cliente");
        texto += "nos comunicamos desde el \"Laboratorio Bromatológico Alejandra Villordo\" para darle aviso que en fecha "
                + vencimiento + " vence el análisis " + tipo + " de procedencia " + (aux[5].trim().endsWith(".") ? aux[5].substring(0, aux[5].length()-1) : aux[5]) + (cliente ? ". Puede pasar por el laboratorio sito en John O´Connor 594 "
                        + "de lunes a viernes de 9 a 17 hs, sábados de 9 a 12 hs para retirar los envases para la toma de muestra sin cargo o coordinar día y horario para toma de muestra por parte de personal del laboratorio."
                        : ". Solicitamos responder el presente mail para poder coordinar el muestreo correspondiente.") + "\nSin otro particular, saludo atte.\n";
        cajaCuerpo.setText(texto);
        System.setProperty("javax.net.ssl.trustStore", "src\\vista\\keystore.jks");
        System.setProperty("javax.net.ssl.trustStorePassword", "laboratorioav");
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
        cajaPara = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setLayout(new java.awt.GridBagLayout());

        comboEmail.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "avillordo@laboratorioav.com.ar", "mriechert@laboratorioav.com.ar", "info@laboratorioav.com.ar", "sruozi@laboratorioav.com.ar", "administracion@laboratorioav.com.ar" }));
        comboEmail.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comboEmailItemStateChanged(evt);
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
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel1.add(jLabel1, gridBagConstraints);

        cajaAsunto.setText("Vencimiento Analisis");
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
        cajaCuerpo.setLineWrap(true);
        cajaCuerpo.setRows(5);
        cajaCuerpo.setText("Buenas tardes, adjunto informes.\nSaludos cordiales.");
        cajaCuerpo.setWrapStyleWord(true);
        cajaCuerpo.setMaximumSize(new java.awt.Dimension(500, 150));
        cajaCuerpo.setMinimumSize(new java.awt.Dimension(500, 150));
        cajaCuerpo.setPreferredSize(new java.awt.Dimension(500, 150));
        jScrollPane1.setViewportView(cajaCuerpo);
        cajaCuerpo.setFont(new Font("Segoe UI", Font.PLAIN, 12));

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 525, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

    private void comboEmailItemStateChanged(java.awt.event.ItemEvent evt) {

        String aux = evt.getItem().toString();
        switch (aux) {
            case "avillordo@laboratorioav.com.ar":
                etiquetaFirma.setText("<html><b>Lic. Alejandra Villordo<br>Laboratorio "
                        + "Bromatológico Alejandra Villordo<br>John O'Connor 594 - "
                        + "Bariloche (R.N.)<br>Tel: 0294-4426008- +5492944571401<br>"
                        + "info@laboratorioav.com.ar</b></html>");
                break;
            case "mriechert@laboratorioav.com.ar":
                etiquetaFirma.setText("<html><b>Téc. Melisa Riechert<br>Laboratorio "
                        + "Bromatológico Alejandra Villordo<br>John O'Connor 594 - "
                        + "Bariloche (R.N.)<br>Tel: 0294-4426008- +5492944571401<br>"
                        + "info@laboratorioav.com.ar</b></html>");
                break;
            case "sruozi@laboratorioav.com.ar":
                etiquetaFirma.setText("<html><b>Aux. Vet. Sofía Ruozi<br>Laboratorio "
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

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        Cursor cursor = new Cursor(Cursor.WAIT_CURSOR);
        this.setCursor(cursor);
        enviarEmail();
        this.dispose();
    }

    private void etiquetaFirmaMousePressed(java.awt.event.MouseEvent evt) {
        if (!editar) {
            String auxFirma = etiquetaFirma.getText();
            int idbr = auxFirma.indexOf("<br>");
            String usuario = JOptionPane.showInputDialog("Ingrese su nombre");
            etiquetaFirma.setText("<html><b>" + usuario + auxFirma.substring(idbr));
        }

    }

    private javax.swing.JTextField cajaAsunto;
    private javax.swing.JTextArea cajaCuerpo;
    private javax.swing.JTextField cajaPara;
    private javax.swing.JComboBox<String> comboEmail;
    private javax.swing.JLabel etiquetaFirma;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;

    private void enviarEmail() {
        // Destinatario
        String to = cajaPara.getText();

        // Remitente
        String from = comboEmail.getSelectedItem().toString();

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

            String cuerpo = cajaCuerpo.getText().replace("\n", "<br>");

            message.setContent("<html>" + cuerpo + "<br>" + etiquetaFirma.getText().substring(6), "text/html; charset=utf-8");

            // Send message
            SMTPTransport t = (SMTPTransport) session.getTransport("smtps");

// Connect to the server using credentials
            t.connect(Dotenv.load().get("EMAIL_HOST"), from, Dotenv.load().get("EMAIL_PASS"));

// Send the message
            t.sendMessage(message, message.getAllRecipients());
            if (!email.equals(cajaPara.getText())) {
                int guardar = JOptionPane.showConfirmDialog(null, "Email enviado, ¿desea guardar el email " + cajaPara.getText().replaceAll(" ", "") + " para el cliente " + muestraRepository.obtenerProcedencia(id));
                if (guardar == JOptionPane.YES_OPTION) {
                    c.guardarEmail(cajaPara.getText(), c.obtenerIdCliente(muestraRepository.obtenerProcedencia(id)));
                }
                copyIntoSent(session, message);
            } else {
                JOptionPane.showMessageDialog(null, "Email enviado.");
                copyIntoSent(session, message);
            }

            Consultas c = Consultas.getInstancia();
            c.actualizarAvisados(id, 1);
        } catch (MessagingException mex) {
            JOptionPane.showMessageDialog(null, "Error al enviar email.");
            System.err.println(mex);
        }
    }

    private void copyIntoSent(final Session session, final Message msg) throws MessagingException {
        final Store store = session.getStore("imaps");
        // Remitente
        String from = comboEmail.getSelectedItem().toString();

        store.connect(Dotenv.load().get("EMAIL_HOST"), from, Dotenv.load().get("EMAIL_PASS"));

        final Folder folder = (Folder) store.getFolder("Inbox").getFolder("Sent Items");
        folder.open(Folder.READ_WRITE);

        folder.appendMessages(new Message[]{msg});
    }
}
