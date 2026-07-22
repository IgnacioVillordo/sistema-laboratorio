package org.ignaciorodriguez.vista;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;
import javax.swing.GroupLayout;
import javax.swing.JOptionPane;
import org.ignaciorodriguez.modelo.Consultas;
import org.ignaciorodriguez.repository.ResultadoRepository;

public class AgregarPh extends javax.swing.JDialog {

    boolean editar = false;
    int id;
    Consultas c = Consultas.getInstancia();
    ResultadoRepository resultadoRepository = new ResultadoRepository();

    public AgregarPh(java.awt.Frame parent, boolean modal, int id, String tipo) {
        super(parent, modal);
        editar = resultadoRepository.checkearResultadoMBAgua(id);
        double[] resultados = resultadoRepository.recuperarPhYCloro(id);
        DecimalFormat df = new DecimalFormat("#.##");
        if (!editar) {
            if (resultadoRepository.checkearResultadoMBAgua(id)) {
                editar = true;
                etiquetaTitulo.setText("Editar pH y cloro");
            }
        }
        this.editar = editar;
        this.id = id;
        initComponents();
        if (resultados[0] == -1) {

        } else {
            cajaLibre.setText(df.format(resultados[0]).replaceAll(",", "."));
        }
        if (resultados[1] == -1) {

        } else {
            cajaTotal.setText(df.format(resultados[1]).replaceAll(",", "."));
        }
        if (resultados[2] == -1) {
        } else {
            cajaPh.setText(df.format(resultados[2]).replaceAll(",", "."));
        }
        super.setSize(jPanel1.getWidth(), 233);
        if (tipo.endsWith("recreación")) {
            super.setSize(super.getWidth(), 300);
            etiquetaTotal.setVisible(true);
            cajaTotal.setVisible(true);
        }
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        jPanel1 = new JPanel();
        etiquetaTitulo = new JLabel();
        etiquetaTotal = new JLabel();
        etiquetaPh = new JLabel();
        etiquetaLibre = new JLabel();
        cajaLibre = new JTextField();
        cajaPh = new JTextField();
        cajaTotal = new JTextField();
        jButton1 = new JButton();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setMaximumSize(new Dimension(252, 320));
        setMinimumSize(new Dimension(251, 262));
        setPreferredSize(new Dimension(251, 262));
        setResizable(false);
        var contentPane = getContentPane();

        {
            jPanel1.setMinimumSize(new Dimension(576, 32));
            jPanel1.setLayout(new GridBagLayout());

            etiquetaTitulo.setFont(new Font(Font.DIALOG, Font.BOLD, 24));
            etiquetaTitulo.setText("Agregar pH y cloro");
            jPanel1.add(etiquetaTitulo, new GridBagConstraints(0, 0, 2, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE,
                new Insets(20, 10, 0, 10), 0, 0));

            etiquetaTotal.setText("Cloro total:");
            etiquetaTotal.setVisible(false);
            jPanel1.add(etiquetaTotal, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE,
                new Insets(20, 20, 20, 20), 0, 0));

            etiquetaPh.setText("pH:");
            jPanel1.add(etiquetaPh, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE,
                new Insets(20, 20, 20, 20), 0, 0));

            etiquetaLibre.setText("Cloro libre:");
            jPanel1.add(etiquetaLibre, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE,
                new Insets(20, 20, 20, 20), 0, 0));

            cajaLibre.setMaximumSize(new Dimension(55, 24));
            cajaLibre.setMinimumSize(new Dimension(55, 24));
            cajaLibre.setPreferredSize(new Dimension(55, 24));
            jPanel1.add(cajaLibre, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE,
                new Insets(20, 0, 20, 20), 0, 0));

            cajaPh.setMaximumSize(new Dimension(55, 24));
            cajaPh.setMinimumSize(new Dimension(55, 24));
            cajaPh.setPreferredSize(new Dimension(55, 24));
            jPanel1.add(cajaPh, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE,
                new Insets(20, 0, 20, 20), 0, 0));

            cajaTotal.setMaximumSize(new Dimension(55, 24));
            cajaTotal.setMinimumSize(new Dimension(55, 24));
            cajaTotal.setPreferredSize(new Dimension(55, 24));
            jPanel1.add(cajaTotal, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE,
                new Insets(20, 0, 20, 20), 0, 0));

            jButton1.setText("Guardar");
            jButton1.addActionListener(e -> jButton1ActionPerformed(e));
            jPanel1.add(jButton1, new GridBagConstraints(0, 4, 2, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE,
                new Insets(0, 0, 20, 0), 0, 0));
        }

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup()
                .addComponent(jPanel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        );
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup()
                .addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        );
        pack();
        setLocationRelativeTo(getOwner());
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        Map m = new HashMap();
        if (cajaTotal.isShowing()) {
            m.put("total", cajaTotal.getText());
        } else {
            m.put("total", -1);
        }
        m.put("ph", cajaPh.getText());
        m.put("libre", cajaLibre.getText());
        m.put("id", id);
        if (editar) {
            if (resultadoRepository.editarPhYCloro(m)) {
                JOptionPane.showMessageDialog(null, "Datos editados con éxito.");
                this.dispose();
            }
        } else {
            if (resultadoRepository.guardarPhYCloro(m)) {
                JOptionPane.showMessageDialog(null, "Datos ingresados con éxito.");
                this.dispose();
            }
        }
    }
    private JPanel jPanel1;
    private JLabel etiquetaTitulo;
    private JLabel etiquetaTotal;
    private JLabel etiquetaPh;
    private JLabel etiquetaLibre;
    private JTextField cajaLibre;
    private JTextField cajaPh;
    private JTextField cajaTotal;
    private JButton jButton1;
}
