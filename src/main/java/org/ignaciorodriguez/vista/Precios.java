package org.ignaciorodriguez.vista;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import org.ignaciorodriguez.modelo.Consultas;
import org.ignaciorodriguez.modelo.DTOPrecio;

public class Precios extends javax.swing.JDialog {

    Consultas consultas = Consultas.getInstancia();
    double total = 0;
    Map<Integer, DTOPrecio> map = new HashMap<>();
    JPanel panelKm;
    JLabel etiquetaKm;
    JTextField campoKm;

    public Precios(java.awt.Frame parent, boolean modal, String tipo, boolean situ) {
        super(parent, modal);
        initComponents();
        jScrollPane1.getVerticalScrollBar().setUnitIncrement(16);
        if (situ) {
            panelKm = new JPanel(new GridBagLayout());
            etiquetaKm = new JLabel("Km:");
            campoKm = new JTextField();
            campoKm.setPreferredSize(new Dimension(120, 20));
            panelKm.add(etiquetaKm);
            panelKm.add(campoKm);
            jPanel1.add(panelKm);
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 5, 20, 15);
            gbc.gridy = fila[0];
            jPanel1.add(panelKm, gbc);
            fila[0]++;
        }

        createLabel();
        int tipoAux = 0;
        switch (tipo) {
            case "Microbiológico de agua código":
                tipoAux = 7;
                break;
            case "Microbiológico de agua bidón":
                tipoAux = 9;
                break;
            case "Microbiológico de agua balnearios":
                tipoAux = 8;
                break;
            case "Microbiológico de agua COFES":
                tipoAux = 9;
                break;
            case "Microbiológico de agua de recreación":
                tipoAux = 10;
                break;
            case "Microbiológico de alimentos":
                tipoAux = 11;
                break;
            case "Microbiológico de chocolates Del Turista":
                tipoAux = 12;
                break;
            case "Efluentes":
                tipoAux = 1;
                break;
            case "Efluentes infiltración":
                tipoAux = 2;
                break;
            case "Físico químico de agua básico":
                tipoAux = 4;
                break;
            case "Físico químico de agua completo":
                tipoAux = 13;
                break;
            case "Físico químico de alimentos":
                tipoAux = 13;
                break;
            case "Físico químico genérico":
                tipoAux = 13;
                break;
            case "Hisopados":
                tipoAux = 5;
                break;
            case "Hisopados Alliance":
                tipoAux = 6;
                break;
            case "Hisopados con límites":
                tipoAux = 6;
                break;
            case "Efluentes cloaca":
                tipoAux = 2;
                break;
            case "Base helada Del Turista":
                tipoAux = 3;
                break;
        }
        map = consultas.consultarPrecios(tipoAux);
        map.forEach((i, dto) -> {
            createLabel(dto.getNombre(), String.valueOf(dto.getPrecio()), i);
        });
        JButton boton = new JButton("Cerrar");
        boton.addActionListener((e) -> {
            map.forEach((i, dto) -> {
                if (dto.isSeleccionado()) {
                    total += dto.getPrecio();
                }
            });
            this.dispose();
        });
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 5, 10, 5);
        gbc.gridy = fila[0];
        jPanel1.add(boton, gbc);
        this.setSize(new Dimension(700, Math.min((((fila[0] + 1) * 30) + 150), 600)));
        setLocationRelativeTo(null);
    }

    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jScrollPane1.setMaximumSize(new java.awt.Dimension(1000, 1000));

        jPanel1.setMaximumSize(new java.awt.Dimension(1000, 1000));
        jPanel1.setLayout(new java.awt.GridBagLayout());
        jScrollPane1.setViewportView(jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 984, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 544, Short.MAX_VALUE)
        );

        pack();
    }

    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;


    final int[] fila = {0};

    private void createLabel() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 5, 0, 5);
        gbc.gridy = fila[0];

        JPanel panelLabels = new JPanel(new GridBagLayout());
        panelLabels.setBorder(BorderFactory.createMatteBorder(fila[0] == 0 ? 1 : 1, 1, 1, 1, Color.BLACK));

        GridBagConstraints c1 = new GridBagConstraints();
        c1.gridx = 0;
        c1.fill = GridBagConstraints.HORIZONTAL;
        c1.weightx = 3;
        JLabel labelNombre = new JLabel("Determinacion");
        labelNombre.setName("Determinacion");
        labelNombre.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.BLACK));
        labelNombre.setHorizontalAlignment(SwingConstants.CENTER);
        labelNombre.setPreferredSize(new Dimension(450, 30));
        labelNombre.setMaximumSize(new Dimension(450, 30));
        labelNombre.setMinimumSize(new Dimension(450, 30));
        labelNombre.setSize(new Dimension(450, 30));
        GridBagConstraints c2 = new GridBagConstraints();
        c2.gridx = 1;
        c2.fill = GridBagConstraints.HORIZONTAL;
        c2.weightx = 1;
        JLabel labelPrecio = new JLabel("Precio");
        labelPrecio.setName("Precio");
        labelPrecio.setHorizontalAlignment(SwingConstants.CENTER);
        labelPrecio.setPreferredSize(new Dimension(150, 30));
        labelPrecio.setMaximumSize(new Dimension(150, 30));
        labelPrecio.setMinimumSize(new Dimension(150, 30));
        labelPrecio.setSize(new Dimension(150, 30));

        panelLabels.add(labelNombre, c1);
        panelLabels.add(labelPrecio, c2);
        jPanel1.add(panelLabels, gbc);

        fila[0]++;
        jPanel1.revalidate();
        jPanel1.repaint();

    }

    private void createLabel(String nombre, String precio, int idprecio) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 5, 0, 5);
        gbc.gridy = fila[0];

        JPanel panelLabels = new JPanel(new GridBagLayout());
        panelLabels.setBorder(BorderFactory.createMatteBorder(fila[0] == 0 ? 1 : 0, 1, 1, 1, Color.BLACK));
        panelLabels.setBackground(Color.pink);
        panelLabels.setName(String.valueOf(idprecio));

        GridBagConstraints c1 = new GridBagConstraints();
        c1.gridx = 0;
        c1.fill = GridBagConstraints.HORIZONTAL;
        c1.weightx = 3;
        JLabel labelNombre = new JLabel(nombre);
        labelNombre.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.BLACK));
        labelNombre.setHorizontalAlignment(SwingConstants.CENTER);
        labelNombre.setPreferredSize(new Dimension(450, 30));
        labelNombre.setMaximumSize(new Dimension(450, 30));
        labelNombre.setMinimumSize(new Dimension(450, 30));
        labelNombre.setSize(new Dimension(450, 30));

        GridBagConstraints c2 = new GridBagConstraints();
        c2.gridx = 1;
        c2.fill = GridBagConstraints.HORIZONTAL;
        c2.weightx = 1;
        JLabel labelPrecio = new JLabel(precio);
        labelPrecio.setHorizontalAlignment(SwingConstants.CENTER);
        labelPrecio.setPreferredSize(new Dimension(150, 30));
        labelPrecio.setMaximumSize(new Dimension(150, 30));
        labelPrecio.setMinimumSize(new Dimension(150, 30));
        labelPrecio.setSize(new Dimension(150, 30));

        labelPrecio.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                panelLabels.setBackground(panelLabels.getBackground() == Color.pink ? Color.white : Color.pink);
                DTOPrecio dto = map.get(idprecio);
                dto.setSeleccionado(panelLabels.getBackground() == Color.pink ? false : true);
            }
        });

        labelNombre.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                panelLabels.setBackground(panelLabels.getBackground() == Color.pink ? Color.white : Color.pink);
                DTOPrecio dto = map.get(idprecio);
                dto.setSeleccionado(panelLabels.getBackground() == Color.pink ? false : true);
            }
        });

        panelLabels.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                panelLabels.setBackground(panelLabels.getBackground() == Color.pink ? Color.white : Color.pink);
                DTOPrecio dto = map.get(idprecio);
                dto.setSeleccionado(panelLabels.getBackground() == Color.pink ? false : true);
            }
        });

        panelLabels.add(labelNombre, c1);
        panelLabels.add(labelPrecio, c2);
        jPanel1.add(panelLabels, gbc);

        fila[0]++;
        jPanel1.revalidate();
        jPanel1.repaint();
    }

    public double getPrecio() {
        if(campoKm != null){
            int q = Integer.parseInt(campoKm.getText());
            return total + ((q/10) * (q * 0.2));
        }
        return total;
    }

}
