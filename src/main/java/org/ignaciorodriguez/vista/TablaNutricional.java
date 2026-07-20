package org.ignaciorodriguez.vista;

import java.io.File;
import javax.swing.JOptionPane;
import org.ignaciorodriguez.modelo.Consultas;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.KeyStroke;
import javax.swing.JComponent;

public class TablaNutricional extends javax.swing.JDialog {

    int id = -1, auxSaturadas;
    Consultas consultas = Consultas.getInstancia();
    boolean editar = false;
    String pdf, auxObservaciones, unidad = "";

    public TablaNutricional(java.awt.Frame parent, boolean modal, int id, boolean editar, String pdf) {
        super(parent, modal);
        this.id = id;
        this.editar = editar;
        this.pdf = pdf.substring(1);
        initComponents();
        if (editar) {
            String[] datos = consultas.recuperarTablaNutricional(id);
            if (datos == null) {
                this.editar = false;
            } else {
                DecimalFormat aux3 = new DecimalFormat("0.#");
                DecimalFormat df = new DecimalFormat("#.##");
                double valor;
                if (datos[0] != "null") {
                    if (Double.parseDouble(datos[0]) == -1.0) {
                        campoCalorias.setText("-");
                    } else {
                        valor = Double.parseDouble(datos[0]);
                        campoCalorias.setText(df.format(valor).replace(',', '.'));
                    }
                }
                if (datos[1] != "null") {
                    if (Double.parseDouble(datos[1]) == -1.0) {
                        campoCarbohidratos.setText("-");
                    } else {
                        valor = Double.parseDouble(datos[1]);
                        campoCarbohidratos.setText(df.format(valor).replace(',', '.'));
                    }
                }
                if (datos[2] != "null") {
                    if (Double.parseDouble(datos[2]) == -1.0) {
                        campoProteinas.setText("-");
                    } else {
                        valor = Double.parseDouble(datos[2]);
                        campoProteinas.setText(df.format(valor).replace(',', '.'));
                    }
                }
                if (datos[3] != "null") {
                    if (Double.parseDouble(datos[3]) == -1.0) {
                        campoGrasasTotales.setText("-");
                    } else {
                        valor = Double.parseDouble(datos[3]);
                        campoGrasasTotales.setText(df.format(valor).replace(',', '.'));
                    }
                }
                if (datos[4] != "null") {
                    if (Double.parseDouble(datos[4]) == -1.0) {
                        campoGrasasSaturadas.setText("-");
                    } else {
                        valor = Double.parseDouble(datos[4]);
                        campoGrasasSaturadas.setText(df.format(valor).replace(',', '.'));
                    }
                }
                if (datos[5] != "null") {
                    if (Double.parseDouble(datos[5]) == -1.0) {
                        campoGrasasTrans.setText("-");
                    } else {
                        valor = Double.parseDouble(datos[5]);
                        campoGrasasTrans.setText(df.format(valor).replace(',', '.'));
                    }
                }
                if (datos[6] != "null") {
                    if (Double.parseDouble(datos[6]) == -1.0) {
                        campoGrasasMonoinsaturadas.setText("-");
                    } else {
                        valor = Double.parseDouble(datos[6]);
                        campoGrasasMonoinsaturadas.setText(df.format(valor).replace(',', '.'));
                    }
                }
                if (datos[7] != "null") {
                    if (Double.parseDouble(datos[7]) == -1.0) {
                        campoGrasasPoliinsaturadas.setText("-");
                    } else {
                        valor = Double.parseDouble(datos[7]);
                        campoGrasasPoliinsaturadas.setText(df.format(valor).replace(',', '.'));
                    }
                }
                if (datos[8] != "null") {
                    if (Double.parseDouble(datos[8]) == -1.0) {
                        campoColesterol.setText("-");
                    } else {
                        valor = Double.parseDouble(datos[8]);
                        campoColesterol.setText(df.format(valor).replace(',', '.'));
                    }
                }
                if (datos[9] != "null") {
                    if (Double.parseDouble(datos[9]) == -1.0) {
                        campoFibraAlimentaria.setText("-");
                    } else {
                        valor = Double.parseDouble(datos[9]);
                        campoFibraAlimentaria.setText(df.format(valor).replace(',', '.'));
                    }
                }
                if (datos[10] != "null") {
                    if (Double.parseDouble(datos[10]) == -1.0) {
                        campoSodio.setText("-");
                    } else {
                        valor = Double.parseDouble(datos[10]);
                        campoSodio.setText(df.format(valor).replace(',', '.'));
                    }
                }
                if (datos[11] != "null") {
                    if (Double.parseDouble(datos[11]) == -1.0) {
                        campoVDCalorias.setText("-");
                    } else {
                        campoVDCalorias.setText(aux3.format(Double.parseDouble(datos[11])));
                    }
                }
                if (datos[12] != "null") {
                    if (Double.parseDouble(datos[12]) == -1.0) {
                        campoVDCarbohidratos.setText("-");
                    } else {
                        campoVDCarbohidratos.setText(aux3.format(Double.parseDouble(datos[12])));
                    }
                }
                if (datos[13] != "null") {
                    if (Double.parseDouble(datos[13]) == -1.0) {
                        campoVDProteinas.setText("-");
                    } else {
                        campoVDProteinas.setText(aux3.format(Double.parseDouble(datos[13])));
                    }
                }
                if (datos[14] != "null") {
                    if (Double.parseDouble(datos[14]) == -1.0) {
                        campoVDGrasasTotales.setText("-");
                    } else {
                        campoVDGrasasTotales.setText(aux3.format(Double.parseDouble(datos[14])));
                    }
                }

//            campoVDGrasasMonoinsaturadas.setText(aux3.format(Double.parseDouble(datos[15])));
//            campoVDGrasasPoliinsaturadas.setText(aux3.format(Double.parseDouble(datos[16])));
//            campoVDColesterol.setText(aux3.format(Double.parseDouble(datos[17])));
                if (datos[18] != "null") {
                    if (Double.parseDouble(datos[18]) == -1.0) {
                        campoVDGrasasSaturadas.setText("-");
                    } else {
                        campoVDGrasasSaturadas.setText(aux3.format(Double.parseDouble(datos[18])));
                    }
                }

//            campoVDGrasasTrans.setText(aux3.format(Double.parseDouble(datos[19])));
                if (datos[20] != "null") {
                    if (Double.parseDouble(datos[20]) == -1.0) {
                        campoVDFibraAlimentaria.setText("-");
                    } else {
                        campoVDFibraAlimentaria.setText(aux3.format(Double.parseDouble(datos[20])));
                    }
                }
                if (datos[21] != "null") {
                    if (Double.parseDouble(datos[21]) == -1.0) {
                        campoVDSodio.setText("-");
                    } else {
                        campoVDSodio.setText(aux3.format(Double.parseDouble(datos[21])));
                    }
                }
                if (datos[22] != "null") {
                    campoPorcion.setText(datos[22]);
                }

                auxObservaciones = consultas.recuperarObservaciones(id);
                if (datos[23] != "null") {
                    campoUnidad.setText(datos[23]);
                }

                auxObservaciones = consultas.recuperarObservaciones(id);
                etiquetaKjul.setText(datos[24].equals("null") ? "" : datos[24]);
                System.out.println("datos[25] = " + datos[25]);
                if (datos[25] != "null") {
                    if (Double.parseDouble(datos[25]) == -1.0) {
                        campoAzucares.setText("-");
                    } else {
                        valor = Double.parseDouble(datos[25]);
                        campoAzucares.setText(aux3.format(valor).replace(",", "."));
                    }
                }
                if (datos[26] != "null") {
                    if (Double.parseDouble(datos[26]) == -1.0) {
                        campoAlmidon.setText("-");
                    } else {
                        valor = Double.parseDouble(datos[26]);
                        campoAlmidon.setText(aux3.format(valor));
                    }
                }

                if (datos[27] != "null") {
                    campoPorcionesPorEnvase.setText(datos[27]);
                }

                if (datos[28] != "null") {
                    if (Double.parseDouble(datos[28]) == -1.0) {
                        campoAzucaresAnadidos.setText("-");
                    } else {
                        valor = Double.parseDouble(datos[28]);
                        campoAzucaresAnadidos.setText(aux3.format(valor).replace(',', '.'));
                    }
                }

                if (datos[29] != "null") {
                    if (Double.parseDouble(datos[29]) == -1.0) {
                        campoVDAzucaresAnadidos.setText("-");
                    } else {
                        valor = Double.parseDouble(datos[29]);
                        campoVDAzucaresAnadidos.setText(aux3.format(valor).replace(',', '.'));
                    }
                }

            }

        }
        ImageIcon icon = new ImageIcon("src\\vista\\icono.png");
        this.setIconImage(icon.getImage());
        setLocationRelativeTo(null);
    }


    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        campoCalorias = new javax.swing.JTextField();
        etiquetaUnidadAlcohol = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        etiquetaKjul = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        campoCarbohidratos = new javax.swing.JTextField();
        etiquetaUnidadAlcohol1 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        campoProteinas = new javax.swing.JTextField();
        etiquetaUnidadAlcohol2 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        campoGrasasTotales = new javax.swing.JTextField();
        etiquetaUnidadAlcohol3 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        campoGrasasSaturadas = new javax.swing.JTextField();
        etiquetaUnidadAlcohol4 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        campoGrasasTrans = new javax.swing.JTextField();
        etiquetaUnidadAlcohol5 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        campoSodio = new javax.swing.JTextField();
        etiquetaUnidadAlcohol6 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        campoVDCalorias = new javax.swing.JTextField();
        etiquetaUnidadAlcohol7 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        campoVDCarbohidratos = new javax.swing.JTextField();
        etiquetaUnidadAlcohol8 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        campoVDProteinas = new javax.swing.JTextField();
        etiquetaUnidadAlcohol9 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        campoVDGrasasTotales = new javax.swing.JTextField();
        etiquetaUnidadAlcohol10 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        campoVDGrasasSaturadas = new javax.swing.JTextField();
        etiquetaUnidadAlcohol11 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        campoVDGrasasTrans = new javax.swing.JTextField();
        etiquetaUnidadAlcohol12 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        campoVDSodio = new javax.swing.JTextField();
        etiquetaUnidadAlcohol13 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        botonAgregar = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();
        jPanel17 = new javax.swing.JPanel();
        campoFibraAlimentaria = new javax.swing.JTextField();
        etiquetaUnidadAlcohol14 = new javax.swing.JLabel();
        jPanel18 = new javax.swing.JPanel();
        campoVDFibraAlimentaria = new javax.swing.JTextField();
        etiquetaUnidadAlcohol15 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        campoPorcion = new javax.swing.JTextField();
        jPanel19 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        campoUnidad = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jPanel20 = new javax.swing.JPanel();
        campoGrasasMonoinsaturadas = new javax.swing.JTextField();
        etiquetaUnidadAlcohol16 = new javax.swing.JLabel();
        jPanel21 = new javax.swing.JPanel();
        campoVDGrasasMonoinsaturadas = new javax.swing.JTextField();
        etiquetaUnidadAlcohol17 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jPanel22 = new javax.swing.JPanel();
        campoGrasasPoliinsaturadas = new javax.swing.JTextField();
        etiquetaUnidadAlcohol18 = new javax.swing.JLabel();
        jPanel23 = new javax.swing.JPanel();
        campoVDGrasasPoliinsaturadas = new javax.swing.JTextField();
        etiquetaUnidadAlcohol19 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jPanel24 = new javax.swing.JPanel();
        campoColesterol = new javax.swing.JTextField();
        etiquetaUnidadAlcohol20 = new javax.swing.JLabel();
        jPanel25 = new javax.swing.JPanel();
        campoVDColesterol = new javax.swing.JTextField();
        etiquetaUnidadAlcohol21 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jPanel26 = new javax.swing.JPanel();
        campoAzucares = new javax.swing.JTextField();
        etiquetaUnidadAlcohol22 = new javax.swing.JLabel();
        jPanel27 = new javax.swing.JPanel();
        campoVDAzucares = new javax.swing.JTextField();
        etiquetaUnidadAlcohol23 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jPanel28 = new javax.swing.JPanel();
        campoAlmidon = new javax.swing.JTextField();
        etiquetaUnidadAlcohol24 = new javax.swing.JLabel();
        jPanel29 = new javax.swing.JPanel();
        campoVDAlmidon = new javax.swing.JTextField();
        etiquetaUnidadAlcohol25 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jPanel30 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        campoPorcionesPorEnvase = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        jPanel31 = new javax.swing.JPanel();
        campoAzucaresAnadidos = new javax.swing.JTextField();
        etiquetaUnidadAlcohol26 = new javax.swing.JLabel();
        jPanel32 = new javax.swing.JPanel();
        campoVDAzucaresAnadidos = new javax.swing.JTextField();
        etiquetaUnidadAlcohol27 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jPanel1.setLayout(new java.awt.GridBagLayout());

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Valor Energético");
        jLabel4.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.lightGray));
        jLabel4.setMaximumSize(new java.awt.Dimension(145, 24));
        jLabel4.setMinimumSize(new java.awt.Dimension(145, 24));
        jLabel4.setOpaque(true);
        jLabel4.setPreferredSize(new java.awt.Dimension(145, 24));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        jPanel1.add(jLabel4, gridBagConstraints);

        jLabel5.setBackground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Carbohidratos");
        jLabel5.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.lightGray));
        jLabel5.setMaximumSize(new java.awt.Dimension(145, 24));
        jLabel5.setMinimumSize(new java.awt.Dimension(145, 24));
        jLabel5.setOpaque(true);
        jLabel5.setPreferredSize(new java.awt.Dimension(145, 24));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        jPanel1.add(jLabel5, gridBagConstraints);

        jLabel6.setBackground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Proteínas");
        jLabel6.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.lightGray));
        jLabel6.setMaximumSize(new java.awt.Dimension(145, 24));
        jLabel6.setMinimumSize(new java.awt.Dimension(145, 24));
        jLabel6.setOpaque(true);
        jLabel6.setPreferredSize(new java.awt.Dimension(145, 24));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        jPanel1.add(jLabel6, gridBagConstraints);

        jLabel7.setBackground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Grasas totales");
        jLabel7.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.lightGray));
        jLabel7.setMaximumSize(new java.awt.Dimension(145, 24));
        jLabel7.setMinimumSize(new java.awt.Dimension(145, 24));
        jLabel7.setOpaque(true);
        jLabel7.setPreferredSize(new java.awt.Dimension(145, 24));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        jPanel1.add(jLabel7, gridBagConstraints);

        jLabel8.setBackground(new java.awt.Color(255, 255, 255));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Fibra alimentaria");
        jLabel8.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.lightGray));
        jLabel8.setMaximumSize(new java.awt.Dimension(145, 24));
        jLabel8.setMinimumSize(new java.awt.Dimension(145, 24));
        jLabel8.setOpaque(true);
        jLabel8.setPreferredSize(new java.awt.Dimension(145, 24));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 13;
        jPanel1.add(jLabel8, gridBagConstraints);

        jLabel9.setBackground(new java.awt.Color(255, 255, 255));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Grasas trans");
        jLabel9.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.lightGray));
        jLabel9.setMaximumSize(new java.awt.Dimension(145, 24));
        jLabel9.setMinimumSize(new java.awt.Dimension(145, 24));
        jLabel9.setOpaque(true);
        jLabel9.setPreferredSize(new java.awt.Dimension(145, 24));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        jPanel1.add(jLabel9, gridBagConstraints);

        jLabel10.setBackground(new java.awt.Color(255, 255, 255));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Sodio");
        jLabel10.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.lightGray));
        jLabel10.setMaximumSize(new java.awt.Dimension(145, 24));
        jLabel10.setMinimumSize(new java.awt.Dimension(145, 24));
        jLabel10.setOpaque(true);
        jLabel10.setPreferredSize(new java.awt.Dimension(145, 24));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 14;
        jPanel1.add(jLabel10, gridBagConstraints);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(187, 187, 187)));
        jPanel3.setMinimumSize(new java.awt.Dimension(160, 24));
        jPanel3.setPreferredSize(new java.awt.Dimension(160, 24));
        jPanel3.setLayout(new java.awt.GridBagLayout());

        campoCalorias.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        campoCalorias.setAutoscrolls(false);
        campoCalorias.setBorder(null);
        campoCalorias.setPreferredSize(new java.awt.Dimension(75, 24));
        campoCalorias.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                campoCaloriasKeyReleased(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 50;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel3.add(campoCalorias, gridBagConstraints);
        InputMap im = jPanel1.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_ALL_CANDIDATES, 0, false), "Apretar");

        ActionMap ap = jPanel1.getActionMap();
        ap.put("Apretar", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e){
                int kjul = Integer.parseInt(campoCalorias.getText());
                int aux = kjul/2;
                etiquetaKjul.setText(String.valueOf(aux));
            }
        });

        etiquetaUnidadAlcohol.setText("Kjul");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        jPanel3.add(etiquetaUnidadAlcohol, gridBagConstraints);

        jLabel18.setText("kcal:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jPanel3.add(jLabel18, gridBagConstraints);

        etiquetaKjul.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        etiquetaKjul.setText("0");
        etiquetaKjul.setMaximumSize(new java.awt.Dimension(28, 14));
        etiquetaKjul.setMinimumSize(new java.awt.Dimension(28, 14));
        etiquetaKjul.setPreferredSize(new java.awt.Dimension(28, 14));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 30;
        jPanel3.add(etiquetaKjul, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        jPanel1.add(jPanel3, gridBagConstraints);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(187, 187, 187)));
        jPanel4.setMinimumSize(new java.awt.Dimension(160, 24));
        jPanel4.setPreferredSize(new java.awt.Dimension(160, 24));
        jPanel4.setLayout(new java.awt.GridBagLayout());

        campoCarbohidratos.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        campoCarbohidratos.setBorder(null);
        campoCarbohidratos.setMaximumSize(new java.awt.Dimension(80, 20));
        campoCarbohidratos.setMinimumSize(new java.awt.Dimension(80, 20));
        campoCarbohidratos.setPreferredSize(new java.awt.Dimension(80, 20));
        campoCarbohidratos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                campoCarbohidratosKeyReleased(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 66;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel4.add(campoCarbohidratos, gridBagConstraints);

        etiquetaUnidadAlcohol1.setText("g");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        jPanel4.add(etiquetaUnidadAlcohol1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        jPanel1.add(jPanel4, gridBagConstraints);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(187, 187, 187)));
        jPanel5.setMinimumSize(new java.awt.Dimension(160, 24));
        jPanel5.setPreferredSize(new java.awt.Dimension(160, 24));
        jPanel5.setLayout(new java.awt.GridBagLayout());

        campoProteinas.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        campoProteinas.setBorder(null);
        campoProteinas.setMaximumSize(new java.awt.Dimension(80, 20));
        campoProteinas.setMinimumSize(new java.awt.Dimension(80, 20));
        campoProteinas.setPreferredSize(new java.awt.Dimension(80, 20));
        campoProteinas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                campoProteinasKeyReleased(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 66;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel5.add(campoProteinas, gridBagConstraints);

        etiquetaUnidadAlcohol2.setText("g");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        jPanel5.add(etiquetaUnidadAlcohol2, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        jPanel1.add(jPanel5, gridBagConstraints);

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(187, 187, 187)));
        jPanel6.setMinimumSize(new java.awt.Dimension(160, 24));
        jPanel6.setPreferredSize(new java.awt.Dimension(160, 24));
        jPanel6.setLayout(new java.awt.GridBagLayout());

        campoGrasasTotales.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        campoGrasasTotales.setBorder(null);
        campoGrasasTotales.setMaximumSize(new java.awt.Dimension(80, 20));
        campoGrasasTotales.setMinimumSize(new java.awt.Dimension(80, 20));
        campoGrasasTotales.setPreferredSize(new java.awt.Dimension(80, 20));
        campoGrasasTotales.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                campoGrasasTotalesKeyReleased(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 66;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel6.add(campoGrasasTotales, gridBagConstraints);

        etiquetaUnidadAlcohol3.setText("g");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        jPanel6.add(etiquetaUnidadAlcohol3, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        jPanel1.add(jPanel6, gridBagConstraints);

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(187, 187, 187)));
        jPanel7.setMinimumSize(new java.awt.Dimension(160, 24));
        jPanel7.setPreferredSize(new java.awt.Dimension(160, 24));
        jPanel7.setLayout(new java.awt.GridBagLayout());

        campoGrasasSaturadas.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        campoGrasasSaturadas.setBorder(null);
        campoGrasasSaturadas.setMaximumSize(new java.awt.Dimension(80, 20));
        campoGrasasSaturadas.setMinimumSize(new java.awt.Dimension(80, 20));
        campoGrasasSaturadas.setPreferredSize(new java.awt.Dimension(80, 20));
        
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 66;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel7.add(campoGrasasSaturadas, gridBagConstraints);

        etiquetaUnidadAlcohol4.setText("g");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        jPanel7.add(etiquetaUnidadAlcohol4, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        jPanel1.add(jPanel7, gridBagConstraints);

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(187, 187, 187)));
        jPanel8.setMinimumSize(new java.awt.Dimension(160, 24));
        jPanel8.setPreferredSize(new java.awt.Dimension(160, 24));
        jPanel8.setLayout(new java.awt.GridBagLayout());

        campoGrasasTrans.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        campoGrasasTrans.setBorder(null);
        campoGrasasTrans.setMaximumSize(new java.awt.Dimension(80, 20));
        campoGrasasTrans.setMinimumSize(new java.awt.Dimension(80, 20));
        campoGrasasTrans.setPreferredSize(new java.awt.Dimension(80, 20));
        
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 66;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel8.add(campoGrasasTrans, gridBagConstraints);

        etiquetaUnidadAlcohol5.setText("g");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        jPanel8.add(etiquetaUnidadAlcohol5, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 9;
        jPanel1.add(jPanel8, gridBagConstraints);

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(187, 187, 187)));
        jPanel9.setMinimumSize(new java.awt.Dimension(160, 24));
        jPanel9.setPreferredSize(new java.awt.Dimension(160, 24));
        jPanel9.setLayout(new java.awt.GridBagLayout());

        campoSodio.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        campoSodio.setBorder(null);
        campoSodio.setMaximumSize(new java.awt.Dimension(80, 20));
        campoSodio.setMinimumSize(new java.awt.Dimension(80, 20));
        campoSodio.setPreferredSize(new java.awt.Dimension(80, 20));
        
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 55;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel9.add(campoSodio, gridBagConstraints);

        etiquetaUnidadAlcohol6.setText("mg");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        jPanel9.add(etiquetaUnidadAlcohol6, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 14;
        jPanel1.add(jPanel9, gridBagConstraints);

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));
        jPanel10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(187, 187, 187)));
        jPanel10.setMinimumSize(new java.awt.Dimension(160, 24));
        jPanel10.setPreferredSize(new java.awt.Dimension(160, 24));
        jPanel10.setLayout(new java.awt.GridBagLayout());

        campoVDCalorias.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        campoVDCalorias.setBorder(null);
        campoVDCalorias.setPreferredSize(new java.awt.Dimension(75, 24));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 66;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel10.add(campoVDCalorias, gridBagConstraints);

        etiquetaUnidadAlcohol7.setText("VD*)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        jPanel10.add(etiquetaUnidadAlcohol7, gridBagConstraints);

        jLabel11.setText("(");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jPanel10.add(jLabel11, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        jPanel1.add(jPanel10, gridBagConstraints);

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));
        jPanel11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(187, 187, 187)));
        jPanel11.setMinimumSize(new java.awt.Dimension(160, 24));
        jPanel11.setPreferredSize(new java.awt.Dimension(160, 24));
        jPanel11.setLayout(new java.awt.GridBagLayout());

        campoVDCarbohidratos.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        campoVDCarbohidratos.setBorder(null);
        campoVDCarbohidratos.setPreferredSize(new java.awt.Dimension(75, 24));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 66;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel11.add(campoVDCarbohidratos, gridBagConstraints);

        etiquetaUnidadAlcohol8.setText("VD*)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        jPanel11.add(etiquetaUnidadAlcohol8, gridBagConstraints);

        jLabel12.setText("(");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jPanel11.add(jLabel12, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        jPanel1.add(jPanel11, gridBagConstraints);

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));
        jPanel12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(187, 187, 187)));
        jPanel12.setMinimumSize(new java.awt.Dimension(160, 24));
        jPanel12.setPreferredSize(new java.awt.Dimension(160, 24));
        jPanel12.setLayout(new java.awt.GridBagLayout());

        campoVDProteinas.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        campoVDProteinas.setBorder(null);
        campoVDProteinas.setPreferredSize(new java.awt.Dimension(75, 24));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 66;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel12.add(campoVDProteinas, gridBagConstraints);

        etiquetaUnidadAlcohol9.setText("VD*)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        jPanel12.add(etiquetaUnidadAlcohol9, gridBagConstraints);

        jLabel13.setText("(");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jPanel12.add(jLabel13, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 6;
        jPanel1.add(jPanel12, gridBagConstraints);

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));
        jPanel13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(187, 187, 187)));
        jPanel13.setMinimumSize(new java.awt.Dimension(160, 24));
        jPanel13.setPreferredSize(new java.awt.Dimension(160, 24));
        jPanel13.setLayout(new java.awt.GridBagLayout());

        campoVDGrasasTotales.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        campoVDGrasasTotales.setBorder(null);
        campoVDGrasasTotales.setPreferredSize(new java.awt.Dimension(75, 24));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 66;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel13.add(campoVDGrasasTotales, gridBagConstraints);

        etiquetaUnidadAlcohol10.setText("VD*)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        jPanel13.add(etiquetaUnidadAlcohol10, gridBagConstraints);

        jLabel14.setText("(");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jPanel13.add(jLabel14, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 7;
        jPanel1.add(jPanel13, gridBagConstraints);

        jPanel14.setBackground(new java.awt.Color(255, 255, 255));
        jPanel14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(187, 187, 187)));
        jPanel14.setMinimumSize(new java.awt.Dimension(160, 24));
        jPanel14.setPreferredSize(new java.awt.Dimension(160, 24));
        jPanel14.setLayout(new java.awt.GridBagLayout());

        campoVDGrasasSaturadas.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        campoVDGrasasSaturadas.setBorder(null);
        campoVDGrasasSaturadas.setPreferredSize(new java.awt.Dimension(75, 24));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 66;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel14.add(campoVDGrasasSaturadas, gridBagConstraints);

        etiquetaUnidadAlcohol11.setText("VD*)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        jPanel14.add(etiquetaUnidadAlcohol11, gridBagConstraints);

        jLabel15.setText("(");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jPanel14.add(jLabel15, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 8;
        jPanel1.add(jPanel14, gridBagConstraints);

        jPanel15.setBackground(new java.awt.Color(255, 255, 255));
        jPanel15.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(187, 187, 187)));
        jPanel15.setMinimumSize(new java.awt.Dimension(160, 24));
        jPanel15.setPreferredSize(new java.awt.Dimension(160, 24));
        jPanel15.setLayout(new java.awt.GridBagLayout());

        campoVDGrasasTrans.setEditable(false);
        campoVDGrasasTrans.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        campoVDGrasasTrans.setText("-");
        campoVDGrasasTrans.setBorder(null);
        campoVDGrasasTrans.setPreferredSize(new java.awt.Dimension(75, 24));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 66;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel15.add(campoVDGrasasTrans, gridBagConstraints);

        etiquetaUnidadAlcohol12.setText("VD*)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        jPanel15.add(etiquetaUnidadAlcohol12, gridBagConstraints);

        jLabel16.setText("(");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jPanel15.add(jLabel16, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 9;
        jPanel1.add(jPanel15, gridBagConstraints);

        jPanel16.setBackground(new java.awt.Color(255, 255, 255));
        jPanel16.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(187, 187, 187)));
        jPanel16.setMinimumSize(new java.awt.Dimension(160, 24));
        jPanel16.setPreferredSize(new java.awt.Dimension(160, 24));
        jPanel16.setLayout(new java.awt.GridBagLayout());

        campoVDSodio.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        campoVDSodio.setBorder(null);
        campoVDSodio.setPreferredSize(new java.awt.Dimension(75, 24));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 66;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel16.add(campoVDSodio, gridBagConstraints);

        etiquetaUnidadAlcohol13.setText("VD*)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        jPanel16.add(etiquetaUnidadAlcohol13, gridBagConstraints);

        jLabel17.setText("(");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jPanel16.add(jLabel17, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 14;
        jPanel1.add(jPanel16, gridBagConstraints);

        botonAgregar.setText("Generar Reporte");
        botonAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonAgregarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 15;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.insets = new java.awt.Insets(20, 0, 0, 0);
        jPanel1.add(botonAgregar, gridBagConstraints);
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false), "Apretar2");

        ap.put("Apretar2", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e){
                botonAgregar.doClick();
            }
        });

        jLabel19.setBackground(new java.awt.Color(255, 255, 255));
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel19.setText("Grasas saturadas");
        jLabel19.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.lightGray));
        jLabel19.setMaximumSize(new java.awt.Dimension(145, 24));
        jLabel19.setMinimumSize(new java.awt.Dimension(145, 24));
        jLabel19.setOpaque(true);
        jLabel19.setPreferredSize(new java.awt.Dimension(145, 24));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        jPanel1.add(jLabel19, gridBagConstraints);

        jPanel17.setBackground(new java.awt.Color(255, 255, 255));
        jPanel17.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(187, 187, 187)));
        jPanel17.setMinimumSize(new java.awt.Dimension(160, 24));
        jPanel17.setPreferredSize(new java.awt.Dimension(160, 24));
        jPanel17.setLayout(new java.awt.GridBagLayout());

        campoFibraAlimentaria.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        campoFibraAlimentaria.setBorder(null);
        campoFibraAlimentaria.setMaximumSize(new java.awt.Dimension(80, 20));
        campoFibraAlimentaria.setMinimumSize(new java.awt.Dimension(80, 20));
        campoFibraAlimentaria.setPreferredSize(new java.awt.Dimension(80, 20));
        campoFibraAlimentaria.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                campoFibraAlimentariaKeyReleased(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 66;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel17.add(campoFibraAlimentaria, gridBagConstraints);

        etiquetaUnidadAlcohol14.setText("g");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        jPanel17.add(etiquetaUnidadAlcohol14, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 13;
        jPanel1.add(jPanel17, gridBagConstraints);

        jPanel18.setBackground(new java.awt.Color(255, 255, 255));
        jPanel18.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(187, 187, 187)));
        jPanel18.setMinimumSize(new java.awt.Dimension(160, 24));
        jPanel18.setPreferredSize(new java.awt.Dimension(160, 24));
        jPanel18.setLayout(new java.awt.GridBagLayout());

        campoVDFibraAlimentaria.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        campoVDFibraAlimentaria.setBorder(null);
        campoVDFibraAlimentaria.setPreferredSize(new java.awt.Dimension(75, 24));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 66;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel18.add(campoVDFibraAlimentaria, gridBagConstraints);

        etiquetaUnidadAlcohol15.setText("VD*)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        jPanel18.add(etiquetaUnidadAlcohol15, gridBagConstraints);

        jLabel20.setText("(");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jPanel18.add(jLabel20, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 13;
        jPanel1.add(jPanel18, gridBagConstraints);

        jPanel2.setLayout(new java.awt.GridBagLayout());

        jLabel1.setText("Porcion");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jPanel2.add(jLabel1, gridBagConstraints);

        campoPorcion.setMaximumSize(new java.awt.Dimension(50, 20));
        campoPorcion.setMinimumSize(new java.awt.Dimension(50, 20));
        campoPorcion.setPreferredSize(new java.awt.Dimension(50, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        jPanel2.add(campoPorcion, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        jPanel1.add(jPanel2, gridBagConstraints);

        jPanel19.setLayout(new java.awt.GridBagLayout());

        jLabel2.setText("Unidades");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jPanel19.add(jLabel2, gridBagConstraints);

        campoUnidad.setMaximumSize(new java.awt.Dimension(100, 20));
        campoUnidad.setMinimumSize(new java.awt.Dimension(100, 20));
        campoUnidad.setPreferredSize(new java.awt.Dimension(100, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        jPanel19.add(campoUnidad, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        jPanel1.add(jPanel19, gridBagConstraints);

        jLabel21.setBackground(new java.awt.Color(255, 255, 255));
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("Grasas Monoinsaturadas");
        jLabel21.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.lightGray));
        jLabel21.setMaximumSize(new java.awt.Dimension(145, 24));
        jLabel21.setMinimumSize(new java.awt.Dimension(145, 24));
        jLabel21.setOpaque(true);
        jLabel21.setPreferredSize(new java.awt.Dimension(145, 24));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        jPanel1.add(jLabel21, gridBagConstraints);

        jPanel20.setBackground(new java.awt.Color(255, 255, 255));
        jPanel20.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(187, 187, 187)));
        jPanel20.setMinimumSize(new java.awt.Dimension(160, 24));
        jPanel20.setPreferredSize(new java.awt.Dimension(160, 24));
        jPanel20.setLayout(new java.awt.GridBagLayout());

        campoGrasasMonoinsaturadas.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        campoGrasasMonoinsaturadas.setBorder(null);
        campoGrasasMonoinsaturadas.setMaximumSize(new java.awt.Dimension(80, 20));
        campoGrasasMonoinsaturadas.setMinimumSize(new java.awt.Dimension(80, 20));
        campoGrasasMonoinsaturadas.setPreferredSize(new java.awt.Dimension(80, 20));
        
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 66;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel20.add(campoGrasasMonoinsaturadas, gridBagConstraints);

        etiquetaUnidadAlcohol16.setText("g");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        jPanel20.add(etiquetaUnidadAlcohol16, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 10;
        jPanel1.add(jPanel20, gridBagConstraints);

        jPanel21.setBackground(new java.awt.Color(255, 255, 255));
        jPanel21.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(187, 187, 187)));
        jPanel21.setMinimumSize(new java.awt.Dimension(160, 24));
        jPanel21.setPreferredSize(new java.awt.Dimension(160, 24));
        jPanel21.setLayout(new java.awt.GridBagLayout());

        campoVDGrasasMonoinsaturadas.setEditable(false);
        campoVDGrasasMonoinsaturadas.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        campoVDGrasasMonoinsaturadas.setText("-");
        campoVDGrasasMonoinsaturadas.setBorder(null);
        campoVDGrasasMonoinsaturadas.setPreferredSize(new java.awt.Dimension(75, 24));
        
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 66;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel21.add(campoVDGrasasMonoinsaturadas, gridBagConstraints);

        etiquetaUnidadAlcohol17.setText("VD*)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        jPanel21.add(etiquetaUnidadAlcohol17, gridBagConstraints);

        jLabel22.setText("(");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jPanel21.add(jLabel22, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 10;
        jPanel1.add(jPanel21, gridBagConstraints);

        jLabel23.setBackground(new java.awt.Color(255, 255, 255));
        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel23.setText("Grasas Poliinsaturadas");
        jLabel23.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.lightGray));
        jLabel23.setMaximumSize(new java.awt.Dimension(145, 24));
        jLabel23.setMinimumSize(new java.awt.Dimension(145, 24));
        jLabel23.setOpaque(true);
        jLabel23.setPreferredSize(new java.awt.Dimension(145, 24));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 11;
        jPanel1.add(jLabel23, gridBagConstraints);

        jPanel22.setBackground(new java.awt.Color(255, 255, 255));
        jPanel22.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(187, 187, 187)));
        jPanel22.setMinimumSize(new java.awt.Dimension(160, 24));
        jPanel22.setPreferredSize(new java.awt.Dimension(160, 24));
        jPanel22.setLayout(new java.awt.GridBagLayout());

        campoGrasasPoliinsaturadas.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        campoGrasasPoliinsaturadas.setBorder(null);
        campoGrasasPoliinsaturadas.setMaximumSize(new java.awt.Dimension(80, 20));
        campoGrasasPoliinsaturadas.setMinimumSize(new java.awt.Dimension(80, 20));
        campoGrasasPoliinsaturadas.setPreferredSize(new java.awt.Dimension(80, 20));
        
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 66;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel22.add(campoGrasasPoliinsaturadas, gridBagConstraints);

        etiquetaUnidadAlcohol18.setText("g");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        jPanel22.add(etiquetaUnidadAlcohol18, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 11;
        jPanel1.add(jPanel22, gridBagConstraints);

        jPanel23.setBackground(new java.awt.Color(255, 255, 255));
        jPanel23.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(187, 187, 187)));
        jPanel23.setMinimumSize(new java.awt.Dimension(160, 24));
        jPanel23.setPreferredSize(new java.awt.Dimension(160, 24));
        jPanel23.setLayout(new java.awt.GridBagLayout());

        campoVDGrasasPoliinsaturadas.setEditable(false);
        campoVDGrasasPoliinsaturadas.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        campoVDGrasasPoliinsaturadas.setText("-");
        campoVDGrasasPoliinsaturadas.setBorder(null);
        campoVDGrasasPoliinsaturadas.setPreferredSize(new java.awt.Dimension(75, 24));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 66;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel23.add(campoVDGrasasPoliinsaturadas, gridBagConstraints);

        etiquetaUnidadAlcohol19.setText("VD*)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        jPanel23.add(etiquetaUnidadAlcohol19, gridBagConstraints);

        jLabel24.setText("(");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jPanel23.add(jLabel24, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 11;
        jPanel1.add(jPanel23, gridBagConstraints);

        jLabel25.setBackground(new java.awt.Color(255, 255, 255));
        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel25.setText("Colesterol");
        jLabel25.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.lightGray));
        jLabel25.setMaximumSize(new java.awt.Dimension(145, 24));
        jLabel25.setMinimumSize(new java.awt.Dimension(145, 24));
        jLabel25.setOpaque(true);
        jLabel25.setPreferredSize(new java.awt.Dimension(145, 24));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 12;
        jPanel1.add(jLabel25, gridBagConstraints);

        jPanel24.setBackground(new java.awt.Color(255, 255, 255));
        jPanel24.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(187, 187, 187)));
        jPanel24.setMinimumSize(new java.awt.Dimension(160, 24));
        jPanel24.setPreferredSize(new java.awt.Dimension(160, 24));
        jPanel24.setLayout(new java.awt.GridBagLayout());

        campoColesterol.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        campoColesterol.setBorder(null);
        campoColesterol.setMaximumSize(new java.awt.Dimension(80, 20));
        campoColesterol.setMinimumSize(new java.awt.Dimension(80, 20));
        campoColesterol.setPreferredSize(new java.awt.Dimension(80, 20));
        
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 66;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel24.add(campoColesterol, gridBagConstraints);

        etiquetaUnidadAlcohol20.setText("g");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        jPanel24.add(etiquetaUnidadAlcohol20, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 12;
        jPanel1.add(jPanel24, gridBagConstraints);

        jPanel25.setBackground(new java.awt.Color(255, 255, 255));
        jPanel25.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(187, 187, 187)));
        jPanel25.setMinimumSize(new java.awt.Dimension(160, 24));
        jPanel25.setPreferredSize(new java.awt.Dimension(160, 24));
        jPanel25.setLayout(new java.awt.GridBagLayout());

        campoVDColesterol.setEditable(false);
        campoVDColesterol.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        campoVDColesterol.setText("-");
        campoVDColesterol.setBorder(null);
        campoVDColesterol.setPreferredSize(new java.awt.Dimension(75, 24));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 66;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel25.add(campoVDColesterol, gridBagConstraints);

        etiquetaUnidadAlcohol21.setText("VD*)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        jPanel25.add(etiquetaUnidadAlcohol21, gridBagConstraints);

        jLabel26.setText("(");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jPanel25.add(jLabel26, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 12;
        jPanel1.add(jPanel25, gridBagConstraints);

        jLabel27.setBackground(new java.awt.Color(255, 255, 255));
        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel27.setText("Azúcares");
        jLabel27.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.lightGray));
        jLabel27.setMaximumSize(new java.awt.Dimension(145, 24));
        jLabel27.setMinimumSize(new java.awt.Dimension(145, 24));
        jLabel27.setOpaque(true);
        jLabel27.setPreferredSize(new java.awt.Dimension(145, 24));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        jPanel1.add(jLabel27, gridBagConstraints);

        jPanel26.setBackground(new java.awt.Color(255, 255, 255));
        jPanel26.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(187, 187, 187)));
        jPanel26.setMinimumSize(new java.awt.Dimension(160, 24));
        jPanel26.setPreferredSize(new java.awt.Dimension(160, 24));
        jPanel26.setLayout(new java.awt.GridBagLayout());

        campoAzucares.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        campoAzucares.setBorder(null);
        campoAzucares.setMaximumSize(new java.awt.Dimension(80, 20));
        campoAzucares.setMinimumSize(new java.awt.Dimension(80, 20));
        campoAzucares.setPreferredSize(new java.awt.Dimension(80, 20));
        
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 66;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel26.add(campoAzucares, gridBagConstraints);

        etiquetaUnidadAlcohol22.setText("g");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        jPanel26.add(etiquetaUnidadAlcohol22, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        jPanel1.add(jPanel26, gridBagConstraints);

        jPanel27.setBackground(new java.awt.Color(255, 255, 255));
        jPanel27.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(187, 187, 187)));
        jPanel27.setMinimumSize(new java.awt.Dimension(160, 24));
        jPanel27.setPreferredSize(new java.awt.Dimension(160, 24));
        jPanel27.setLayout(new java.awt.GridBagLayout());

        campoVDAzucares.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        campoVDAzucares.setText("-");
        campoVDAzucares.setBorder(null);
        campoVDAzucares.setEnabled(false);
        campoVDAzucares.setPreferredSize(new java.awt.Dimension(75, 24));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 66;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel27.add(campoVDAzucares, gridBagConstraints);

        etiquetaUnidadAlcohol23.setText("VD*)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        jPanel27.add(etiquetaUnidadAlcohol23, gridBagConstraints);

        jLabel28.setText("(");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jPanel27.add(jLabel28, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        jPanel1.add(jPanel27, gridBagConstraints);

        jLabel29.setBackground(new java.awt.Color(255, 255, 255));
        jLabel29.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel29.setText(" Almidón");
        jLabel29.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.lightGray));
        jLabel29.setMaximumSize(new java.awt.Dimension(145, 24));
        jLabel29.setMinimumSize(new java.awt.Dimension(145, 24));
        jLabel29.setOpaque(true);
        jLabel29.setPreferredSize(new java.awt.Dimension(145, 24));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        jPanel1.add(jLabel29, gridBagConstraints);

        jPanel28.setBackground(new java.awt.Color(255, 255, 255));
        jPanel28.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(187, 187, 187)));
        jPanel28.setMinimumSize(new java.awt.Dimension(160, 24));
        jPanel28.setPreferredSize(new java.awt.Dimension(160, 24));
        jPanel28.setLayout(new java.awt.GridBagLayout());

        campoAlmidon.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        campoAlmidon.setBorder(null);
        campoAlmidon.setMaximumSize(new java.awt.Dimension(80, 20));
        campoAlmidon.setMinimumSize(new java.awt.Dimension(80, 20));
        campoAlmidon.setPreferredSize(new java.awt.Dimension(80, 20));
        
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 66;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel28.add(campoAlmidon, gridBagConstraints);

        etiquetaUnidadAlcohol24.setText("g");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        jPanel28.add(etiquetaUnidadAlcohol24, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        jPanel1.add(jPanel28, gridBagConstraints);

        jPanel29.setBackground(new java.awt.Color(255, 255, 255));
        jPanel29.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(187, 187, 187)));
        jPanel29.setMinimumSize(new java.awt.Dimension(160, 24));
        jPanel29.setPreferredSize(new java.awt.Dimension(160, 24));
        jPanel29.setLayout(new java.awt.GridBagLayout());

        campoVDAlmidon.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        campoVDAlmidon.setText("-");
        campoVDAlmidon.setBorder(null);
        campoVDAlmidon.setEnabled(false);
        campoVDAlmidon.setPreferredSize(new java.awt.Dimension(75, 24));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 66;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel29.add(campoVDAlmidon, gridBagConstraints);

        etiquetaUnidadAlcohol25.setText("VD*)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        jPanel29.add(etiquetaUnidadAlcohol25, gridBagConstraints);

        jLabel30.setText("(");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jPanel29.add(jLabel30, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 5;
        jPanel1.add(jPanel29, gridBagConstraints);

        jPanel30.setLayout(new java.awt.GridBagLayout());

        jLabel3.setText("Porciones por envase:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jPanel30.add(jLabel3, gridBagConstraints);

        campoPorcionesPorEnvase.setMaximumSize(new java.awt.Dimension(50, 20));
        campoPorcionesPorEnvase.setMinimumSize(new java.awt.Dimension(50, 20));
        campoPorcionesPorEnvase.setPreferredSize(new java.awt.Dimension(50, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        jPanel30.add(campoPorcionesPorEnvase, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 10, 0);
        jPanel1.add(jPanel30, gridBagConstraints);

        jLabel31.setBackground(new java.awt.Color(255, 255, 255));
        jLabel31.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel31.setText("Azúcares añadidos");
        jLabel31.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.lightGray));
        jLabel31.setMaximumSize(new java.awt.Dimension(145, 24));
        jLabel31.setMinimumSize(new java.awt.Dimension(145, 24));
        jLabel31.setOpaque(true);
        jLabel31.setPreferredSize(new java.awt.Dimension(145, 24));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        jPanel1.add(jLabel31, gridBagConstraints);

        jPanel31.setBackground(new java.awt.Color(255, 255, 255));
        jPanel31.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(187, 187, 187)));
        jPanel31.setMinimumSize(new java.awt.Dimension(160, 24));
        jPanel31.setPreferredSize(new java.awt.Dimension(160, 24));
        jPanel31.setLayout(new java.awt.GridBagLayout());

        campoAzucaresAnadidos.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        campoAzucaresAnadidos.setBorder(null);
        campoAzucaresAnadidos.setMaximumSize(new java.awt.Dimension(80, 20));
        campoAzucaresAnadidos.setMinimumSize(new java.awt.Dimension(80, 20));
        campoAzucaresAnadidos.setPreferredSize(new java.awt.Dimension(80, 20));
        
        campoAzucaresAnadidos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                campoAzucaresAnadidosKeyReleased(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 66;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel31.add(campoAzucaresAnadidos, gridBagConstraints);

        etiquetaUnidadAlcohol26.setText("g");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        jPanel31.add(etiquetaUnidadAlcohol26, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        jPanel1.add(jPanel31, gridBagConstraints);

        jPanel32.setBackground(new java.awt.Color(255, 255, 255));
        jPanel32.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(187, 187, 187)));
        jPanel32.setMinimumSize(new java.awt.Dimension(160, 24));
        jPanel32.setPreferredSize(new java.awt.Dimension(160, 24));
        jPanel32.setLayout(new java.awt.GridBagLayout());

        campoVDAzucaresAnadidos.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        campoVDAzucaresAnadidos.setBorder(null);
        campoVDAzucaresAnadidos.setPreferredSize(new java.awt.Dimension(75, 24));
        
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 66;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel32.add(campoVDAzucaresAnadidos, gridBagConstraints);

        etiquetaUnidadAlcohol27.setText("VD*)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        jPanel32.add(etiquetaUnidadAlcohol27, gridBagConstraints);

        jLabel32.setText("(");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jPanel32.add(jLabel32, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        jPanel1.add(jPanel32, gridBagConstraints);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 572, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false), "Cerrar");
        ap.put("Cerrar", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e){
                dispose();
            }
        });

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botonAgregarActionPerformed(java.awt.event.ActionEvent evt) {

        Double[] valores = guardarValores();
        String porcion = campoPorcion.getText();
        String observaciones = "Los datos de ingredientes y sus respectivos pesos han sido aportados por el elaborador.";

//        observaciones = JOptionPane.showInputDialog("Ingrese la observación:", auxObservaciones);
//        observaciones = observaciones.isBlank()? "" : observaciones.trim().endsWith(".") ? observaciones : observaciones + ".";
        consultas.guardarObservaciones(observaciones, id);
        unidad = campoUnidad.getText();
        String porcionesEnvase = campoPorcionesPorEnvase.getText();
        if (editar) {
            File rv = new File(consultas.recuperarRutas("Reportes") + "\\" + pdf);
            File rn = new File(consultas.recuperarRutas("Reportes") + "\\(BORRADO) " + pdf);
            rv.renameTo(rn);
            if (consultas.editarTablaNutricional(valores, id, unidad, porcion, porcionesEnvase)) {
                this.dispose();
                consultas.generarReporteTN(id, consultas.obtenerProcedencia(id));
            }
        } else {
            if (consultas.guardarTablaNutricional(valores, id, unidad, porcion, porcionesEnvase)) {
                this.dispose();
                consultas.generarReporteTN(id, consultas.obtenerProcedencia(id));
            }
        }


    }

    private void campoCaloriasKeyReleased(java.awt.event.KeyEvent evt) {
        try {
            int kjul = Integer.parseInt(campoCalorias.getText());
            Double aux2 = kjul * 4.184;
            DecimalFormat df = new DecimalFormat("#.##");
            String conv = df.format(aux2).replace(',', '.');
            etiquetaKjul.setText(conv);
            double aux = Double.parseDouble(campoCalorias.getText());
            aux = aux * 100 / 2000;
            campoVDCalorias.setText(String.valueOf(Math.round(aux)));
        } catch (Exception e) {
        }
    }

    private void campoCarbohidratosKeyReleased(java.awt.event.KeyEvent evt) {
        try {
            double aux = Double.parseDouble(campoCarbohidratos.getText());
            aux = aux * 100 / 300;
            campoVDCarbohidratos.setText(String.valueOf(Math.round(aux)));
        } catch (Exception e) {
        }
    }

    private void campoProteinasKeyReleased(java.awt.event.KeyEvent evt) {
        try {
            double aux = Double.parseDouble(campoProteinas.getText());
            aux = aux * 100 / 75;
            campoVDProteinas.setText(String.valueOf(Math.round(aux)));
        } catch (Exception e) {
        }
    }

    private void campoGrasasTotalesKeyReleased(java.awt.event.KeyEvent evt) {
        try {
            double aux = Double.parseDouble(campoGrasasTotales.getText());
            aux = aux * 100 / 55;
            campoVDGrasasTotales.setText(String.valueOf(Math.round(aux)));
        } catch (Exception e) {
        }
    }

    private void campoGrasasSaturadasKeyReleased(java.awt.event.KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_PERIOD) {
        } else {
            try {
                double aux = Double.parseDouble(campoGrasasSaturadas.getText());
                aux = aux * 100 / 22;
                campoVDGrasasSaturadas.setText(String.valueOf(Math.round(aux)));
            } catch (Exception e) {
            }
        }
    }

    private void campoFibraAlimentariaKeyReleased(java.awt.event.KeyEvent evt) {
        try {
            double aux = Double.parseDouble(campoFibraAlimentaria.getText());
            aux = aux * 100 / 25;
            campoVDFibraAlimentaria.setText(String.valueOf(Math.round(aux)));
        } catch (Exception e) {
        }
    }

    private void campoSodioKeyReleased(java.awt.event.KeyEvent evt) {
        try {
            double aux = Double.parseDouble(campoSodio.getText());
            aux = aux * 100 / 2400;
            campoVDSodio.setText(String.valueOf(Math.round(aux)));
        } catch (Exception e) {
        }
    }

    private void campoAzucaresAnadidosKeyReleased(java.awt.event.KeyEvent evt) {
//        try {
//            double cal = Double.parseDouble(campoCalorias.getText());
//            double azucares = Double.parseDouble(campoAzucaresAnadidos.getText());
//            double vd = azucares * 400 / cal;
//            campoVDAzucaresAnadidos.setText(String.valueOf(Math.round(vd)));
//        } catch (Exception e) {
//        }
    }

    private javax.swing.JButton botonAgregar;
    private javax.swing.JTextField campoAlmidon;
    private javax.swing.JTextField campoAzucares;
    private javax.swing.JTextField campoAzucaresAnadidos;
    private javax.swing.JTextField campoCalorias;
    private javax.swing.JTextField campoCarbohidratos;
    private javax.swing.JTextField campoColesterol;
    private javax.swing.JTextField campoFibraAlimentaria;
    private javax.swing.JTextField campoGrasasMonoinsaturadas;
    private javax.swing.JTextField campoGrasasPoliinsaturadas;
    private javax.swing.JTextField campoGrasasSaturadas;
    private javax.swing.JTextField campoGrasasTotales;
    private javax.swing.JTextField campoGrasasTrans;
    private javax.swing.JTextField campoPorcion;
    private javax.swing.JTextField campoPorcionesPorEnvase;
    private javax.swing.JTextField campoProteinas;
    private javax.swing.JTextField campoSodio;
    private javax.swing.JTextField campoUnidad;
    private javax.swing.JTextField campoVDAlmidon;
    private javax.swing.JTextField campoVDAzucares;
    private javax.swing.JTextField campoVDAzucaresAnadidos;
    private javax.swing.JTextField campoVDCalorias;
    private javax.swing.JTextField campoVDCarbohidratos;
    private javax.swing.JTextField campoVDColesterol;
    private javax.swing.JTextField campoVDFibraAlimentaria;
    private javax.swing.JTextField campoVDGrasasMonoinsaturadas;
    private javax.swing.JTextField campoVDGrasasPoliinsaturadas;
    private javax.swing.JTextField campoVDGrasasSaturadas;
    private javax.swing.JTextField campoVDGrasasTotales;
    private javax.swing.JTextField campoVDGrasasTrans;
    private javax.swing.JTextField campoVDProteinas;
    private javax.swing.JTextField campoVDSodio;
    private javax.swing.JLabel etiquetaKjul;
    private javax.swing.JLabel etiquetaUnidadAlcohol;
    private javax.swing.JLabel etiquetaUnidadAlcohol1;
    private javax.swing.JLabel etiquetaUnidadAlcohol10;
    private javax.swing.JLabel etiquetaUnidadAlcohol11;
    private javax.swing.JLabel etiquetaUnidadAlcohol12;
    private javax.swing.JLabel etiquetaUnidadAlcohol13;
    private javax.swing.JLabel etiquetaUnidadAlcohol14;
    private javax.swing.JLabel etiquetaUnidadAlcohol15;
    private javax.swing.JLabel etiquetaUnidadAlcohol16;
    private javax.swing.JLabel etiquetaUnidadAlcohol17;
    private javax.swing.JLabel etiquetaUnidadAlcohol18;
    private javax.swing.JLabel etiquetaUnidadAlcohol19;
    private javax.swing.JLabel etiquetaUnidadAlcohol2;
    private javax.swing.JLabel etiquetaUnidadAlcohol20;
    private javax.swing.JLabel etiquetaUnidadAlcohol21;
    private javax.swing.JLabel etiquetaUnidadAlcohol22;
    private javax.swing.JLabel etiquetaUnidadAlcohol23;
    private javax.swing.JLabel etiquetaUnidadAlcohol24;
    private javax.swing.JLabel etiquetaUnidadAlcohol25;
    private javax.swing.JLabel etiquetaUnidadAlcohol26;
    private javax.swing.JLabel etiquetaUnidadAlcohol27;
    private javax.swing.JLabel etiquetaUnidadAlcohol3;
    private javax.swing.JLabel etiquetaUnidadAlcohol4;
    private javax.swing.JLabel etiquetaUnidadAlcohol5;
    private javax.swing.JLabel etiquetaUnidadAlcohol6;
    private javax.swing.JLabel etiquetaUnidadAlcohol7;
    private javax.swing.JLabel etiquetaUnidadAlcohol8;
    private javax.swing.JLabel etiquetaUnidadAlcohol9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel28;
    private javax.swing.JPanel jPanel29;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel30;
    private javax.swing.JPanel jPanel31;
    private javax.swing.JPanel jPanel32;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    // End of variables declaration//GEN-END:variables
    public Double[] guardarValores() {
        try {
            Double[] valores = new Double[31];
            valores[0] = Double.parseDouble(campoCalorias.getText());
            String aux = etiquetaKjul.getText().replaceAll(",", ".");
            valores[1] = Double.parseDouble(aux);
            if (campoCarbohidratos.getText().equals("-") || campoCarbohidratos.getText().isBlank()) {
                valores[2] = -1.0;
            } else {
                String auxValor = campoCarbohidratos.getText().replace(",", ".");
                valores[2] = Double.parseDouble(auxValor);
            }
            if (campoProteinas.getText().equals("-") || campoProteinas.getText().isBlank()) {
                valores[3] = -1.0;
            } else {
                String auxValor = campoProteinas.getText().replace(",", ".");
                valores[3] = Double.parseDouble(auxValor);
            }
            if (campoGrasasTotales.getText().equals("-") || campoGrasasTotales.getText().isBlank()) {
                valores[4] = -1.0;
            } else {
                String auxValor = campoGrasasTotales.getText().replace(",", ".");
                valores[4] = Double.parseDouble(auxValor);
            }
            if (campoGrasasSaturadas.getText().equals("-") || campoGrasasSaturadas.getText().isBlank()) {
                valores[5] = -1.0;
            } else {
                String auxValor = campoGrasasSaturadas.getText().replace(",", ".");
                valores[5] = Double.parseDouble(auxValor);
            }
            if (campoGrasasTrans.getText().equals("-") || campoGrasasTrans.getText().isBlank()) {
                valores[6] = -1.0;
            } else {
                String auxValor = campoGrasasTrans.getText().replace(",", ".");
                valores[6] = Double.parseDouble(auxValor);
            }
            if (campoGrasasMonoinsaturadas.getText().equals("-") || campoGrasasMonoinsaturadas.getText().isBlank()) {
                valores[7] = -1.0;
            } else {
                String auxValor = campoGrasasMonoinsaturadas.getText().replace(",", ".");
                valores[7] = Double.parseDouble(auxValor);
            }
            if (campoGrasasPoliinsaturadas.getText().equals("-") || campoGrasasPoliinsaturadas.getText().isBlank()) {
                valores[8] = -1.0;
            } else {
                String auxValor = campoGrasasPoliinsaturadas.getText().replace(",", ".");
                valores[8] = Double.parseDouble(auxValor);
            }
            if (campoColesterol.getText().equals("-") || campoColesterol.getText().isBlank()) {
                valores[9] = -1.0;
            } else {
                String auxValor = campoColesterol.getText().replace(",", ".");
                valores[9] = Double.parseDouble(auxValor);
            }
            if (campoFibraAlimentaria.getText().equals("-") || campoFibraAlimentaria.getText().isBlank()) {
                valores[10] = -1.0;
            } else {
                String auxValor = campoFibraAlimentaria.getText().replace(",", ".");
                valores[10] = Double.parseDouble(auxValor);
            }
            if (campoSodio.getText().equals("-") || campoSodio.getText().isBlank()) {
                valores[11] = -1.0;
            } else {
                String auxValor = campoSodio.getText().replace(",", ".");
                valores[11] = Double.parseDouble(auxValor);
            }
            if (campoVDCalorias.getText().isBlank() || campoVDCalorias.getText().equals("-")) {
                valores[12] = -1.0;
            } else {

                valores[12] = Double.parseDouble(campoVDCalorias.getText());
            }
            if (campoVDCarbohidratos.getText().isBlank() || campoVDCarbohidratos.getText().equals("-")) {
                valores[13] = -1.0;
            } else {
                valores[13] = Double.parseDouble(campoVDCarbohidratos.getText());
            }
            if (campoVDProteinas.getText().isBlank() || campoVDProteinas.getText().equals("-")) {
                valores[14] = -1.0;
            } else {
                valores[14] = Double.parseDouble(campoVDProteinas.getText());
            }

            if (campoVDGrasasTotales.getText().isBlank() || campoVDGrasasTotales.getText().equals("-")) {
                valores[15] = -1.0;
            } else {

                valores[15] = Double.parseDouble(campoVDGrasasTotales.getText());
            }

            if (campoVDGrasasSaturadas.getText().isBlank() || campoVDGrasasSaturadas.getText().equals("-")) {
                valores[16] = -1.0;
            } else {

                valores[16] = Double.parseDouble(campoVDGrasasSaturadas.getText());
            }
            valores[17] = 0.0;
            valores[18] = 0.0;
            valores[19] = 0.0;
            valores[20] = 0.0;
            if (campoVDFibraAlimentaria.getText().isBlank() || campoVDFibraAlimentaria.getText().equals("-")) {
                valores[21] = -1.0;
            } else {

                valores[21] = Double.parseDouble(campoVDFibraAlimentaria.getText());
            }
            if (campoVDSodio.getText().isBlank() || campoVDSodio.getText().equals("-")) {
                valores[22] = -1.0;
            } else {

                valores[22] = Double.parseDouble(campoVDSodio.getText());
            }
            valores[24] = 0.0;
            if (campoAzucares.getText().equals("-") || campoAzucares.getText().isBlank()) {
                valores[25] = -1.0;
            } else {
                valores[25] = Double.parseDouble(campoAzucares.getText());
            }
            if (campoAlmidon.getText().equals("-") || campoAlmidon.getText().isBlank()) {
                valores[26] = -1.0;
            } else {
                valores[26] = Double.parseDouble(campoAlmidon.getText());
            }
            valores[27] = 0.0;
            valores[28] = 0.0;

            if (campoAzucaresAnadidos.getText().equals("-") || campoAzucaresAnadidos.getText().isBlank()) {
                valores[29] = -1.0;
            } else {
                valores[29] = Double.parseDouble(campoAzucaresAnadidos.getText());
            }
            if (campoVDAzucaresAnadidos.getText().isBlank() || campoVDAzucaresAnadidos.getText().equals("-")) {
                valores[30] = -1.0;
            } else {

                valores[30] = Double.parseDouble(campoVDAzucaresAnadidos.getText());
            }
            return valores;
        } catch (java.lang.NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Asegurese de haber rellenado todos los campos.");
        }
        return null;
    }
}
