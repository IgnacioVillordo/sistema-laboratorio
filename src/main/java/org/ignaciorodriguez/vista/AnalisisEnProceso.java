package org.ignaciorodriguez.vista;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.ignaciorodriguez.modelo.Consultas;
import org.ignaciorodriguez.repository.ClienteRepository;
import org.ignaciorodriguez.repository.ResultadosRepository;

public class AnalisisEnProceso extends javax.swing.JDialog {

    Consultas c = Consultas.getInstancia();
    int id;
    String auxiliar;
    ClienteRepository clienteRepository = new ClienteRepository();
    ResultadosRepository resultadosRepository = new ResultadosRepository();

    public AnalisisEnProceso(java.awt.Frame parent, boolean modal, int id) {
        super(parent, modal);
        initComponents();
        this.id = id;
        int idcliente = clienteRepository.recuperarIdCliente(this.id);
        String tipo = c.recuperarTipoAnalisis(this.id);
        if (tipo.contains("Microbiológico de agua")) {
            tipo = "microbiológico de agua";
        } else if (tipo.contains("Fisicoquímico de agua")) {
            tipo = "fisicoquímico de agua";
        } else if (tipo.contains("Microbiológico de chocolates Del Turista")) {
            tipo = "microbiológico de chocolates";
        } else if (tipo.contains("Efluentes")) {
            tipo = "efluentes";
        } else if (tipo.contains("Hisopado")) {
            tipo = "hisopado";
        } else if (tipo.contains("Manual")) {
            tipo = resultadosRepository.recuperarTituloManual(id).toLowerCase();
        } else {
            tipo = tipo.toLowerCase();
        }

        Date fecha = c.recuperarEntrada(this.id);

        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Calendar cal = Calendar.getInstance();
        cal.setTime(fecha);
        if (tipo.contains("microbiológico")) {
            cal.add(Calendar.DAY_OF_MONTH, 3);
        } else {
            cal.add(Calendar.DAY_OF_MONTH, 6);
        }
        fecha = cal.getTime();

        auxiliar = "Por la presente se certifica que se está realizando el análisis "
                + tipo + " de " + clienteRepository.recuperarProcedencia(idcliente)
                + " solicitado por " + c.recuperarSolicitante(idcliente) + " sito en "
                + clienteRepository.recuperarDireccion(idcliente) + " de " + (clienteRepository.recuperarCiudad(idcliente).equals("Bariloche") ? "esta ciudad" : clienteRepository.recuperarCiudad(idcliente))
                + " bajo el número de ingreso " + this.id + ".\n"
                + "Siendo entregado el resultado el día " + df.format(fecha) + " del corriente.\n"
                + "Para ser presentado ante las autoridades que lo requieran.";
        areaInforme.setText(auxiliar);
        auxiliar = areaInforme.getText();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        botonGuardar = new javax.swing.JButton();
        botonCancelar = new javax.swing.JButton();
        ScrollareaInforme = new javax.swing.JScrollPane();
        areaInforme = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setLayout(new java.awt.GridBagLayout());

        jLabel1.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jLabel1.setText("Informe de analisis en proceso:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets = new java.awt.Insets(20, 10, 10, 10);
        jPanel1.add(jLabel1, gridBagConstraints);

        botonGuardar.setText("Continuar");
        botonGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonGuardarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 20, 10);
        jPanel1.add(botonGuardar, gridBagConstraints);

        botonCancelar.setText("Cancelar");
        botonCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonCancelarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 20, 20);
        jPanel1.add(botonCancelar, gridBagConstraints);

        ScrollareaInforme.setMaximumSize(new java.awt.Dimension(320, 120));
        ScrollareaInforme.setMinimumSize(new java.awt.Dimension(320, 120));
        ScrollareaInforme.setPreferredSize(new java.awt.Dimension(320, 120));

        areaInforme.setColumns(20);
        areaInforme.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        areaInforme.setLineWrap(true);
        areaInforme.setRows(5);
        ScrollareaInforme.setViewportView(areaInforme);
        areaInforme.setWrapStyleWord(true);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        jPanel1.add(ScrollareaInforme, gridBagConstraints);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }

    private void botonGuardarActionPerformed(java.awt.event.ActionEvent evt) {
        this.dispose();
        auxiliar = areaInforme.getText().replace("\n", "<br>");
        auxiliar = "<html>" + auxiliar + "</html>";
        c.generarReporteEnProceso(id, auxiliar);
    }

    private void botonCancelarActionPerformed(java.awt.event.ActionEvent evt) {
        this.dispose();
    }

    private javax.swing.JScrollPane ScrollareaInforme;
    private javax.swing.JTextArea areaInforme;
    private javax.swing.JButton botonCancelar;
    private javax.swing.JButton botonGuardar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
}
