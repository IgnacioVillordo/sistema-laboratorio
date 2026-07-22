package org.ignaciorodriguez.vista;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.border.MatteBorder;
import javax.swing.plaf.basic.BasicComboBoxUI;
import org.ignaciorodriguez.modelo.Consultas;
import org.ignaciorodriguez.repository.MuestraRepository;
import org.ignaciorodriguez.repository.ResultadosRepository;

public class TablaMBAlimentos extends javax.swing.JDialog {

    int id;
    String procedencia, pdf, auxObservaciones, auxConclusion, auxGermenes, auxFecales,
            auxTotales, auxStaphilococos, auxEnterobacterias, auxBacillus, auxPerfringens,
            auxSulfito, auxCampilobacter;
    int auxObservacionesCombo, auxConclusionCombo, auxGermenesCombo, auxFecalesCombo,
            auxTotalesCombo, auxStaphilococosCombo, auxEnterobacteriasCombo, auxBacillusCombo, auxPerfringensCombo,
            auxSulfitoCombo, auxCampilobacterCombo, auxVibrioCombo;
    Consultas c = Consultas.getInstancia();
    boolean editar, activarGermenes = true, activarFecales = true, activarTotales = true,
            activarStaphilococos = true, activarEscherichia = true, activarSalmonella = true,
            activarMohos = true, activarEscherichiaH7 = true, activarEscherichia157 = true,
            activarEnterobacterias = true, activarListeria = true, activarBacillus = true,
            activarPerfringens = true, activarSulfito = true, activarCampilobacter = true,
            activarCaracteristicas = true, activarColiformesTotalesA30 = true, activarColiformesTotalesProbables = true,
            activarLactobacillus = true, activarBacteriasLacticas = true, activarColiformesTotales45 = true, activarVibrio = true,
            activarShigella = true, activarVibrioCholerae = true;
    private int auxColiformesTotalesA30Combo;
    private String auxColiformesTotalesA30;
    private int auxColiformesTotalesProbablesCombo;
    private String auxColiformesTotalesProbables;
    private int auxLactobacillusCombo;
    private String auxLactobacillus;
    private int auxBacteriasLacticasCombo;
    private String auxBacteriasLacticas;
    private int auxColiformesTotales45Combo;
    private String auxColiformesTotales45;
    private int auxListeriaCombo;
    private String auxListeria;
    private String auxVibrio;
    private String auxVibrioCholerae;
    private int auxVibrioCholeraeCombo;
    MuestraRepository muestraRepository = new MuestraRepository();
    ResultadosRepository resultadosRepository = new ResultadosRepository();

    public TablaMBAlimentos(java.awt.Frame parent, boolean modal, int id, String procedencia,
                            boolean editar, String pdf) {
        super(parent, modal);
        this.procedencia = procedencia;
        this.id = id;
        this.editar = editar;
        this.pdf = pdf;
        initComponents();
        setTitle("ID " + id + ". " + muestraRepository.obtenerProcedencia(id) + ". Microbiológico de alimentos");
        if (editar == true) {
            Map<String, String> map = resultadosRepository.recuperarResultadosMBAlimentos(id);
            if (map == null) {
                this.editar = false;
            } else {
                String aux;
                if (map.get("germenes").contains("-2")) {
                    click(etiquetaGermenes, etiquetaGermenes.getX(), etiquetaGermenes.getY());
                } else if (map.get("germenes").contains("-1")) {
                } else {
                    aux = map.get("germenes").replaceAll("[^0-9?!\\.]", "");
                    if (map.get("germenes").toLowerCase().startsWith("menor a")) {
                        comboGermenes.setSelectedIndex(0);
                        if (aux.equals("10")) {
                            checkGermenes.setSelected(true);
                        }
                    } else {
                        comboGermenes.setSelectedIndex(1);
                    }
                    cajaGermenes.setText(aux);
                    if (map.get("germenes").endsWith("g")) {
                        comboGermenesIn.setSelectedIndex(0);
                    } else {
                        comboGermenesIn.setSelectedIndex(1);
                    }
                }

                if (map.get("coliformesTotales").contains("-2")) {
                    etiquetaTotalesMouseClicked(click(etiquetaTotales));
                } else if (map.get("coliformesTotales").contains("-1")) {
                } else {
                    aux = map.get("coliformesTotales").replaceAll("[^0-9?!\\.]", "");
                    if (map.get("coliformesTotales").toLowerCase().contains("menor a")) {
                        comboColiformesTotales.setSelectedIndex(0);
                        if (aux.equals("10")) {
                            checkColiformesTotales.setSelected(true);
                        }
                    } else {
                        comboColiformesTotales.setSelectedIndex(1);
                    }
                    if (map.get("coliformesTotales").endsWith("g")) {
                        comboColiformesTotalesIn.setSelectedIndex(0);
                    } else {
                        comboColiformesTotalesIn.setSelectedIndex(1);
                    }
                    cajaColiformesTotales.setText(aux);
                }
                if (map.get("coliformesFecales").contains("-2")) {
                    click(etiquetaFecales, etiquetaFecales.getX(), etiquetaFecales.getY());
                } else if (map.get("coliformesFecales").contains("-1")) {
                } else {
                    aux = map.get("coliformesFecales").replaceAll("[^0-9?!\\.]", "");
                    if (map.get("coliformesFecales").toLowerCase().contains("menor a")) {
                        comboColiformesFecales.setSelectedIndex(0);
                        if (aux.equals("10")) {
                            checkColiformesFecales.setSelected(true);
                        }
                    } else {
                        comboColiformesFecales.setSelectedIndex(1);
                    }
                    cajaColiformesFecales.setText(aux);
                    if (map.get("coliformesFecales").endsWith("g")) {
                        comboColiformesFecalesIn.setSelectedIndex(0);
                    } else {
                        comboColiformesFecalesIn.setSelectedIndex(1);
                    }
                }
                if (map.get("escherichia").contains("-2")) {
                    click(etiquetaEscherichia, etiquetaEscherichia.getX(), etiquetaEscherichia.getY());
                } else if (map.get("escherichia").contains("-1")) {
                } else {
                    if (map.get("escherichia").toLowerCase().contains("ausencia")) {
                        comboEscherichia.setSelectedIndex(0);
                    } else {
                        comboEscherichia.setSelectedIndex(1);
                    }

                    if (map.get("escherichia").endsWith("1 g")) {
                        comboEscherichiaIn.setSelectedIndex(0);
                    } else if (map.get("escherichia").endsWith("10 g")) {
                        comboEscherichiaIn.setSelectedIndex(1);
                    } else if (map.get("escherichia").endsWith("25 g")) {
                        comboEscherichiaIn.setSelectedIndex(2);
                    } else if (map.get("escherichia").endsWith("65 g")) {
                        comboEscherichiaIn.setSelectedIndex(3);
                    } else if (map.get("escherichia").endsWith("1 ml")) {
                        comboEscherichiaIn.setSelectedIndex(4);
                    } else if (map.get("escherichia").endsWith("10 ml")) {
                        comboEscherichiaIn.setSelectedIndex(5);
                    } else if (map.get("escherichia").endsWith("25 ml")) {
                        comboEscherichiaIn.setSelectedIndex(6);
                    } else if (map.get("escherichia").endsWith("65 ml")) {
                        comboEscherichiaIn.setSelectedIndex(7);
                    }
                }
                if (map.get("escherichiah7").contains("-2")) {
                    click(etiquetaEscherichiaH7, etiquetaEscherichiaH7.getX(), etiquetaEscherichiaH7.getY());
                } else if (map.get("escherichiah7").contains("-1")) {
                } else {
                    if (map.get("escherichiah7").toLowerCase().startsWith("ausencia")) {
                        comboEscherichiaH7.setSelectedIndex(0);
                    } else {
                        comboEscherichiaH7.setSelectedIndex(1);
                    }

                    if (map.get("escherichiah7").endsWith("1 g")) {
                        comboEscherichiaH7In.setSelectedIndex(0);
                    } else if (map.get("escherichiah7").endsWith("10 g")) {
                        comboEscherichiaH7In.setSelectedIndex(1);
                    } else if (map.get("escherichiah7").endsWith("25 g")) {
                        comboEscherichiaH7In.setSelectedIndex(2);
                    } else if (map.get("escherichiah7").endsWith("65 g")) {
                        comboEscherichiaH7In.setSelectedIndex(3);
                    } else if (map.get("escherichiah7").endsWith("1 ml")) {
                        comboEscherichiaH7In.setSelectedIndex(4);
                    } else if (map.get("escherichiah7").endsWith("10 ml")) {
                        comboEscherichiaH7In.setSelectedIndex(5);
                    } else if (map.get("escherichiah7").endsWith("25 ml")) {
                        comboEscherichiaH7In.setSelectedIndex(6);
                    } else if (map.get("escherichiah7").endsWith("65 ml")) {
                        comboEscherichiaH7In.setSelectedIndex(7);
                    }
                }
                if (map.get("escherichia157").contains("-2")) {
                    click(etiquetaEscherichia157, etiquetaEscherichia157.getX(), etiquetaEscherichia157.getY());
                } else if (map.get("escherichia157").contains("-1")) {
                } else {
                    if (map.get("escherichia157").toLowerCase().startsWith("ausencia")) {
                        comboEscherichia157.setSelectedIndex(0);
                    } else {
                        comboEscherichia157.setSelectedIndex(1);
                    }
                    if (map.get("escherichia157").endsWith("1 g")) {
                        comboEscherichia157In.setSelectedIndex(0);
                    } else if (map.get("escherichia157").endsWith("10 g")) {
                        comboEscherichia157In.setSelectedIndex(1);
                    } else if (map.get("escherichia157").endsWith("25 g")) {
                        comboEscherichia157In.setSelectedIndex(2);
                    } else if (map.get("escherichia157").endsWith("65 g")) {
                        comboEscherichia157In.setSelectedIndex(3);
                    } else if (map.get("escherichia157").endsWith("1 ml")) {
                        comboEscherichia157In.setSelectedIndex(4);
                    } else if (map.get("escherichia157").endsWith("10 ml")) {
                        comboEscherichia157In.setSelectedIndex(5);
                    } else if (map.get("escherichia157").endsWith("25 ml")) {
                        comboEscherichia157In.setSelectedIndex(6);
                    } else if (map.get("escherichia157").endsWith("65 ml")) {
                        comboEscherichia157In.setSelectedIndex(7);
                    }
                }
                if (map.get("enterobacterias").contains("-2")) {
                    click(etiquetaEnterobacterias, etiquetaEnterobacterias.getX(), etiquetaEnterobacterias.getY());
                } else if (map.get("enterobacterias").contains("-1")) {
                } else {
                    aux = map.get("enterobacterias").replaceAll("[^0-9?!\\.]", "");
                    if (map.get("enterobacterias").toLowerCase().startsWith("menor a")) {
                        comboEnterobacterias.setSelectedIndex(0);
                        if (aux.equals("10")) {
                            checkEnterobacterias.setSelected(true);
                        }
                    } else {
                        comboEnterobacterias.setSelectedIndex(1);
                    }
                    cajaEnterobacterias.setText(aux);

                    if (map.get("enterobacterias").endsWith("g")) {
                        comboEnterobacteriasIn.setSelectedIndex(0);
                    } else {
                        comboEnterobacteriasIn.setSelectedIndex(1);
                    }
                }
                if (map.get("staphilococos").contains("-2")) {
                    click(etiquetaStaphilococos, etiquetaStaphilococos.getX(), etiquetaStaphilococos.getY());
                } else if (map.get("staphilococos").contains("-1")) {
                } else {
                    aux = map.get("staphilococos").replaceAll("[^0-9?!\\.]", "");
                    if (map.get("staphilococos").toLowerCase().startsWith("menor a")) {
                        comboStaphilococos.setSelectedIndex(0);
                        if (aux.equals("10")) {
                            checkStaphilococos.setSelected(true);
                        }
                    } else {
                        comboStaphilococos.setSelectedIndex(1);
                    }
                    cajaStaphilococos.setText(aux);
                    if (map.get("staphilococos").endsWith("g")) {
                        comboStaphilococosIn.setSelectedIndex(0);
                    } else {
                        comboStaphilococosIn.setSelectedIndex(1);
                    }
                }
                if (map.get("mohosLevaduras").toString().contains("-2")) {
                    click(etiquetaMohos, etiquetaMohos.getX(), etiquetaMohos.getY());
                } else if (map.get("mohosLevaduras").contains("-1")) {
                } else {
                    aux = map.get("mohosLevaduras").replaceAll("[^0-9?!\\.]", "");
                    if (map.get("mohosLevaduras").toLowerCase().startsWith("menor a")) {
                        comboMohos.setSelectedIndex(0);
                        if (aux.equals("10")) {
                            checkMohos.setSelected(true);
                        }
                    } else {
                        comboMohos.setSelectedIndex(1);
                    }
                    cajaMohos.setText(aux);
                    if (map.get("mohosLevaduras").endsWith("g")) {
                        comboMohosIn.setSelectedIndex(0);
                    } else {
                        comboMohosIn.setSelectedIndex(1);
                    }
                }
                if (map.get("salmonella").contains("-2")) {
                    click(etiquetaSalmonella, etiquetaSalmonella.getX(), etiquetaSalmonella.getY());
                } else if (map.get("salmonella").contains("-1")) {
                } else {
                    if (map.get("salmonella").toLowerCase().startsWith("ausencia")) {
                        comboSalmonella.setSelectedIndex(0);
                    } else {
                        comboSalmonella.setSelectedIndex(1);
                    }
                    if (map.get("salmonella").endsWith("10 g")) {
                        comboSalmonellaIn.setSelectedIndex(0);
                    } else if (map.get("salmonella").endsWith("25 g")) {
                        comboSalmonellaIn.setSelectedIndex(1);
                    } else if (map.get("salmonella").endsWith("65 g")) {
                        comboSalmonellaIn.setSelectedIndex(2);
                    } else if (map.get("salmonella").endsWith("10 ml")) {
                        comboSalmonellaIn.setSelectedIndex(3);
                    } else if (map.get("salmonella").endsWith("25 ml")) {
                        comboSalmonellaIn.setSelectedIndex(4);
                    } else if (map.get("salmonella").endsWith("65 ml")) {
                        comboSalmonellaIn.setSelectedIndex(5);
                    }
                }
                if (map.get("listeria").contains("-2")) {
                    click(etiquetaListeria, etiquetaListeria.getX(), etiquetaListeria.getY());
                } else if (map.get("listeria").contains("-1")) {
                } else {
                    if (map.get("listeria").toLowerCase().startsWith("ausencia")) {

                    } else {
                        if (map.get("listeria").toLowerCase().contains("Ausencia") || map.get("listeria").toLowerCase().contains("Presencia")) {
                            comboListeria.setSize(105, 30);
                            comboListeria.setPreferredSize(new Dimension(105, 30));
                            comboListeria.setMinimumSize(new Dimension(105, 30));
                            comboListeria.setMaximumSize(new Dimension(105, 30));
                            comboListeriaIn.setSize(65, 30);
                            comboListeriaIn.setPreferredSize(new Dimension(65, 30));
                            comboListeriaIn.setMinimumSize(new Dimension(65, 30));
                            comboListeriaIn.setMaximumSize(new Dimension(65, 30));
                            cajaListeria.setVisible(false);
                            if (map.get("listeria").toLowerCase().startsWith("ausencia")) {
                                comboListeria.setSelectedIndex(2);
                            } else {
                                comboListeria.setSelectedIndex(3);
                            }
                            if (map.get("listeria").endsWith("10 g")) {
                                comboListeriaIn.setSelectedIndex(2);
                            } else if (map.get("listeria").endsWith("25 g")) {
                                comboListeriaIn.setSelectedIndex(3);
                            } else if (map.get("listeria").endsWith("65 g")) {
                                comboListeriaIn.setSelectedIndex(4);
                            } else if (map.get("listeria").endsWith("10 ml")) {
                                comboListeriaIn.setSelectedIndex(5);
                            } else if (map.get("listeria").endsWith("25 ml")) {
                                comboListeriaIn.setSelectedIndex(6);
                            } else if (map.get("listeria").endsWith("65 ml")) {
                                comboListeriaIn.setSelectedIndex(7);
                            } else if (map.get("listeria").endsWith("UFC/g")) {
                                comboListeriaIn.setSelectedIndex(0);
                            } else if (map.get("listeria").endsWith("UFC/ml")) {
                                comboListeriaIn.setSelectedIndex(1);
                            }
                        }
                        aux = map.get("listeria").replaceAll("[^0-9?!\\.]", "");
                        if (map.get("listeria").toLowerCase().startsWith("menor a")) {
                            comboListeria.setSelectedIndex(0);
                            if (aux.equals("10")) {
                                checkListeria.setSelected(true);
                            }
                        } else {
                            comboListeria.setSelectedIndex(1);
                        }
                        cajaListeria.setText(aux);
                        if (map.get("listeria").trim().endsWith("g")) {
                            comboListeriaIn.setSelectedIndex(0);
                        } else {
                            comboListeriaIn.setSelectedIndex(1);
                        }

                    }
                }
                if (map.get("bacillus").contains("-2")) {
                    click(etiquetaBacillus, etiquetaBacillus.getX(), etiquetaBacillus.getY());
                } else if (map.get("bacillus").contains("-1")) {
                } else {
                    aux = map.get("bacillus").replaceAll("[^0-9?!\\.]", "");
                    if (map.get("bacillus").toLowerCase().startsWith("menor a")) {
                        comboBacillus.setSelectedIndex(0);
                        if (aux.equals("10")) {
                            checkBacillus.setSelected(true);
                        }
                    } else {
                        comboBacillus.setSelectedIndex(1);
                    }
                    cajaBacillus.setText(aux);
                    if (map.get("bacillus").trim().endsWith("g")) {
                        comboBacillusIn.setSelectedIndex(0);
                    } else {
                        comboBacillusIn.setSelectedIndex(1);
                    }

                }
                if (map.get("perfringens").contains("-2")) {
                    click(etiquetaPerfringens, etiquetaPerfringens.getX(), etiquetaPerfringens.getY());
                } else if (map.get("perfringens").contains("-1")) {
                } else {
                    aux = map.get("perfringens").replaceAll("[^0-9?!\\.]", "");
                    if (map.get("perfringens").toLowerCase().startsWith("menor a")) {
                        comboPerfringens.setSelectedIndex(0);
                        if (aux.equals("10")) {
                            checkPerfringens.setSelected(true);
                        }
                    } else {
                        comboPerfringens.setSelectedIndex(1);
                    }
                    cajaPerfringens.setText(aux);

                    if (map.get("perfringens").endsWith("g")) {
                        comboPerfringensIn.setSelectedIndex(0);
                    } else {
                        comboPerfringensIn.setSelectedIndex(1);
                    }
                }
                if (map.get("sulfito").contains("-2")) {
                    click(etiquetaSulfito, etiquetaSulfito.getX(), etiquetaSulfito.getY());
                } else if (map.get("sulfito").contains("-1")) {
                } else {
                    aux = map.get("sulfito").replaceAll("[^0-9?!\\.]", "");
                    if (map.get("sulfito").toLowerCase().startsWith("menor a")) {
                        comboSulfito.setSelectedIndex(0);
                        if (aux.equals("10")) {
                            checkSulfito.setSelected(true);
                        }
                    } else {
                        comboSulfito.setSelectedIndex(1);
                    }
                    cajaSulfito.setText(aux);

                    if (map.get("sulfito").endsWith("g")) {
                        comboSulfitoIn.setSelectedIndex(0);
                    } else {
                        comboSulfitoIn.setSelectedIndex(1);
                    }
                }
                if (map.get("campilobacter").contains("-2")) {
                    click(etiquetaCampilobacter, etiquetaCampilobacter.getX(), etiquetaCampilobacter.getY());
                } else if (map.get("campilobacter").contains("-1")) {
                } else {
                    aux = map.get("campilobacter").replaceAll("[^0-9?!\\.]", "");
                    if (map.get("campilobacter").toLowerCase().startsWith("menor a")) {
                        comboCampilobacter.setSelectedIndex(0);
                        if (aux.equals("10")) {
                            checkCampilobacter.setSelected(true);
                        }
                    } else {
                        comboCampilobacter.setSelectedIndex(1);
                    }
                    cajaCampilobacter.setText(aux);
                    if (map.get("campilobacter").endsWith("g")) {
                        comboCampilobacterIn.setSelectedIndex(0);
                    } else {
                        comboCampilobacterIn.setSelectedIndex(1);
                    }
                }
                if (map.get("coliformesTotalesA30").contains("-2")) {
                    click(etiquetaColiformesTotalesA30, etiquetaColiformesTotalesA30.getX(), etiquetaColiformesTotalesA30.getY());
                } else if (map.get("coliformesTotalesA30").contains("-1")) {
                } else {
                    aux = map.get("coliformesTotalesA30").replaceAll("[^0-9?!\\.]", "");
                    if (map.get("coliformesTotalesA30").toLowerCase().startsWith("menor a")) {
                        comboColiformesTotalesA30.setSelectedIndex(0);
                        if (aux.equals("10")) {
                            checkColiformesTotalesA30.doClick();
                        }
                    } else {
                        comboColiformesTotalesA30.setSelectedIndex(1);
                    }
                    cajaColiformesTotalesA30.setText(aux);
                    if (map.get("coliformesTotalesA30").endsWith("g")) {
                        comboColiformesTotalesA30In.setSelectedIndex(0);
                    } else {
                        comboColiformesTotalesA30In.setSelectedIndex(1);
                    }
                }
                if (map.get("coliformesTotalesProbables").contains("-2")) {
                    click(etiquetaColiformesTotalesProbable, etiquetaColiformesTotalesProbable.getX(), etiquetaColiformesTotalesProbable.getY());
                } else if (map.get("coliformesTotalesProbables").contains("-1")) {
                } else {
                    aux = map.get("coliformesTotalesProbables").replaceAll("[^0-9?!\\.]", "");
                    if (map.get("coliformesTotalesProbables").toLowerCase().startsWith("menor a")) {
                        comboColiformesTotalesProbables.setSelectedIndex(0);
                        if (aux.equals("10")) {
                            checkColiformesTotalesProbables.doClick();
                        }
                    } else {
                        comboColiformesTotalesProbables.setSelectedIndex(1);
                    }
                    cajaColiformesTotalesProbables.setText(aux);
                    if (map.get("coliformesTotalesProbables").endsWith("g")) {
                        comboColiformesTotalesProbablesIn.setSelectedIndex(0);
                    } else {
                        comboColiformesTotalesProbablesIn.setSelectedIndex(1);
                    }
                }
                if (map.get("caracteristicas").contains("-2")) {
                    click(etiquetaCaracteristicas, etiquetaCaracteristicas.getX(), etiquetaCaracteristicas.getY());
                } else if (map.get("caracteristicas").contains("-1")) {
                } else {
                    cajaCaracteristicas.setText(map.get("caracteristicas"));
                }
                if (map.get("lactobacillus").contains("-2")) {
                    click(etiquetaLactobacillus, etiquetaLactobacillus.getX(), etiquetaLactobacillus.getY());
                } else if (map.get("lactobacillus").contains("-1")) {
                } else {
                    aux = map.get("lactobacillus").replaceAll("[^0-9?!\\.]", "");
                    if (map.get("lactobacillus").toLowerCase().startsWith("menor a")) {
                        comboLactobacillus.setSelectedIndex(0);
                        if (aux.equals("10")) {
                            checkLactobacillus.doClick();
                        }
                    } else {
                        comboLactobacillus.setSelectedIndex(1);
                    }
                    cajaLactobacillus.setText(aux);
                    if (map.get("lactobacillus").endsWith("g")) {
                        comboLactobacillusIn.setSelectedIndex(0);
                    } else {
                        comboLactobacillusIn.setSelectedIndex(1);
                    }
                }
                if (map.get("bacteriasLacticas").contains("-2")) {
                    click(etiquetaBacteriasLacticas, etiquetaBacteriasLacticas.getX(), etiquetaBacteriasLacticas.getY());
                } else if (map.get("bacteriasLacticas").contains("-1")) {
                } else {
                    aux = map.get("bacteriasLacticas").replaceAll("[^0-9?!\\.]", "");
                    if (map.get("bacteriasLacticas").toLowerCase().startsWith("menor a")) {
                        comboBacteriasLacticas.setSelectedIndex(0);
                        if (aux.equals("10")) {
                            checkBacteriasLacticas.doClick();
                        }
                    } else {
                        comboBacteriasLacticas.setSelectedIndex(1);
                    }
                    cajaBacteriasLacticas.setText(aux);
                    if (map.get("bacteriasLacticas").endsWith("g")) {
                        comboBacteriasLacticasIn.setSelectedIndex(0);
                    } else {
                        comboBacteriasLacticasIn.setSelectedIndex(1);
                    }
                }
                if (map.get("coliformesTotales45").contains("-2")) {
                    click(etiquetaColiformesTotales45, etiquetaColiformesTotales45.getX(), etiquetaColiformesTotales45.getY());
                } else if (map.get("coliformesTotales45").contains("-1")) {
                } else {
                    aux = map.get("coliformesTotales45").replaceAll("[^0-9?!\\.]", "");
                    if (map.get("coliformesTotales45").toLowerCase().startsWith("menor a")) {
                        comboColiformesTotales45.setSelectedIndex(0);
                        if (aux.equals("10")) {
                            checkColiformesTotales45.doClick();
                        }
                    } else {
                        comboColiformesTotales45.setSelectedIndex(1);
                    }
                    cajaColiformesTotales45.setText(aux);
                    if (map.get("coliformesTotales45").endsWith("g")) {
                        comboColiformesTotales45In.setSelectedIndex(0);
                    } else {
                        comboColiformesTotales45In.setSelectedIndex(1);
                    }
                }

                if (map.get("vibrio").contains("-2")) {
                    click(etiquetaVibrio, etiquetaVibrio.getX(), etiquetaVibrio.getY());
                } else if (map.get("vibrio").contains("-1")) {
                } else {
                    aux = map.get("vibrio").replaceAll("[^0-9?!\\.]", "");
                    if (map.get("vibrio").toLowerCase().startsWith("menor a")) {
                        comboVibrio.setSelectedIndex(0);
                        if (aux.equals("10")) {
                            checkVibrio.doClick();
                        }
                    } else {
                        comboVibrio.setSelectedIndex(1);
                    }
                    cajaVibrio.setText(aux);
                    if (map.get("vibrio").endsWith("g")) {
                        comboVibrioIn.setSelectedIndex(0);
                    } else {
                        comboVibrioIn.setSelectedIndex(1);
                    }
                }

                if (map.get("vibrioCholerae").contains("-2")) {
                    click(etiquetaVibrioCholerae, etiquetaVibrioCholerae.getX(), etiquetaVibrioCholerae.getY());
                } else if (map.get("vibrioCholerae").contains("-1")) {
                } else {
                    aux = map.get("vibrioCholerae").replaceAll("[^0-9?!\\.]", "");
                    if (map.get("vibrioCholerae").toLowerCase().startsWith("menor a")) {
                        comboVibrioCholerae.setSelectedIndex(0);
                        if (aux.equals("10")) {
                            checkVibrioCholerae.doClick();
                        }
                    } else {
                        comboVibrioCholerae.setSelectedIndex(1);
                    }
                    cajaVibrioCholerae.setText(aux);
                    if (map.get("vibrioCholerae").endsWith("g")) {
                        comboVibrioCholeraeIn.setSelectedIndex(0);
                    } else {
                        comboVibrioCholeraeIn.setSelectedIndex(1);
                    }
                }

                if (map.get("shigella").contains("-2")) {
                    click(etiquetaShigella, etiquetaShigella.getX(), etiquetaShigella.getY());
                } else if (map.get("escherichia").contains("-1")) {
                } else {
                    if (map.get("shigella").toLowerCase().contains("ausencia")) {
                        comboShigella.setSelectedIndex(0);
                    } else {
                        comboShigella.setSelectedIndex(1);
                    }

                    if (map.get("shigella").endsWith("10 g")) {
                        comboShigellaIn.setSelectedIndex(0);
                    } else if (map.get("shigella").endsWith("25 g")) {
                        comboShigellaIn.setSelectedIndex(1);
                    } else if (map.get("shigella").endsWith("50 g")) {
                        comboShigellaIn.setSelectedIndex(2);
                    }
                }

                java.sql.Date fecha = java.sql.Date.valueOf(map.get("fechaAnalisis"));
                java.util.Date utilFecha = null;

                metodoGermenes.setSelectedItem(map.get("germenesMetodo"));
                metodoTotales.setSelectedItem(map.get("coliformesTotalesMetodo"));
                metodoFecales.setSelectedItem(map.get("coliformesFecalesMetodo"));
                metodoEscherichia.setSelectedItem(map.get("escherichiaMetodo"));
                metodoEscherichiaH7.setSelectedItem(map.get("escherichiah7Metodo"));
                metodoEscherichia157.setSelectedItem(map.get("escherichia157Metodo"));
                metodoEnterobacterias.setSelectedItem(map.get("enterobacteriasMetodo"));
                metodoStaphilococos.setSelectedItem(map.get("staphilococosMetodo"));
                metodoMohos.setSelectedItem(map.get("mohosLevadurasMetodo"));
                metodoSalmonella.setSelectedItem(map.get("salmonellaMetodo"));
                metodoListeria.setSelectedItem(map.get("listeriaMetodo"));
                metodoBacillus.setSelectedItem(map.get("bacillusMetodo"));
                metodoPerfringens.setSelectedItem(map.get("perfringensMetodo"));
                metodoSulfito.setSelectedItem(map.get("sulfitoMetodo"));
                metodoCampilobacter.setSelectedItem(map.get("campilobacterMetodo"));
                metodoCaracteristicas.setSelectedItem(map.get("caracteristicasMetodo"));
                metodoLactobacillus.setSelectedItem(map.get("lactobacillusMetodo"));
                metodoBacteriasLacticas.setSelectedItem(map.get("bacteriasLacticasMetodo"));
                metodoColiformesTotales45.setSelectedItem(map.get("coliformesTotales45Metodo"));
                metodoColiformesTotales45.setSelectedItem(map.get("vibrioMetodo"));
                metodoColiformesTotales45.setSelectedItem(map.get("shigellaMetodo"));
                try {
                    utilFecha = new java.util.Date(fecha.getTime());
                } catch (Exception e) {
                    System.err.println("error, " + e);
                }
                cajaFechaAnalisis.setDate(utilFecha);
                auxObservaciones = muestraRepository.recuperarObservaciones(id);
                auxConclusion = muestraRepository.recuperarConclusion(id);
                if (auxObservaciones != null) {
                    auxObservaciones = sacarPuntosFinales(auxObservaciones);
                    System.out.println("auxObservaciones en editar = " + auxObservaciones);
                }
                if (auxConclusion != null) {
                    auxConclusion = sacarPuntosFinales(auxConclusion);
                    checkConclusion.setSelected(!auxConclusion.isEmpty());
                }
            }
        }
        ImageIcon icon = new ImageIcon("src\\vista\\icono.png");
        this.setIconImage(icon.getImage());
        setLocationRelativeTo(null);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        panelEscherichiaH7 = new javax.swing.JPanel();
        etiquetaEscherichiaH7 = new javax.swing.JLabel();
        panelMohos = new javax.swing.JPanel();
        etiquetaMohos = new javax.swing.JLabel();
        panelFecales = new javax.swing.JPanel();
        etiquetaFecales = new javax.swing.JLabel();
        panelStaphilococos = new javax.swing.JPanel();
        etiquetaStaphilococos = new javax.swing.JLabel();
        botonGenerar = new javax.swing.JButton();
        etiquetaTitulo = new javax.swing.JLabel();
        panelSalmonella = new javax.swing.JPanel();
        etiquetaSalmonella = new javax.swing.JLabel();
        checkEnterobacterias = new javax.swing.JCheckBox();
        jLabel3 = new javax.swing.JLabel();
        cajaFechaAnalisis = new com.toedter.calendar.JDateChooser();
        jPanel9 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        comboColiformesFecales = new javax.swing.JComboBox<>();
        cajaColiformesFecales = new javax.swing.JTextField();
        comboColiformesFecalesIn = new javax.swing.JComboBox<>();
        jPanel4 = new javax.swing.JPanel();
        comboEnterobacterias = new javax.swing.JComboBox<>();
        cajaEnterobacterias = new javax.swing.JTextField();
        comboEnterobacteriasIn = new javax.swing.JComboBox<>();
        jPanel10 = new javax.swing.JPanel();
        comboEscherichia157 = new javax.swing.JComboBox<>();
        comboEscherichia157In = new javax.swing.JComboBox<>();
        jPanel14 = new javax.swing.JPanel();
        comboColiformesTotales = new javax.swing.JComboBox<>();
        cajaColiformesTotales = new javax.swing.JTextField();
        comboColiformesTotalesIn = new javax.swing.JComboBox<>();
        jPanel16 = new javax.swing.JPanel();
        comboGermenes = new javax.swing.JComboBox<>();
        cajaGermenes = new javax.swing.JTextField();
        comboGermenesIn = new javax.swing.JComboBox<>();
        jPanel11 = new javax.swing.JPanel();
        comboEscherichia = new javax.swing.JComboBox<>();
        comboEscherichiaIn = new javax.swing.JComboBox<>();
        jPanel21 = new javax.swing.JPanel();
        comboBacillus = new javax.swing.JComboBox<>();
        cajaBacillus = new javax.swing.JTextField();
        comboBacillusIn = new javax.swing.JComboBox<>();
        jPanel22 = new javax.swing.JPanel();
        comboPerfringens = new javax.swing.JComboBox<>();
        cajaPerfringens = new javax.swing.JTextField();
        comboPerfringensIn = new javax.swing.JComboBox<>();
        jPanel23 = new javax.swing.JPanel();
        comboSulfito = new javax.swing.JComboBox<>();
        cajaSulfito = new javax.swing.JTextField();
        comboSulfitoIn = new javax.swing.JComboBox<>();
        jPanel24 = new javax.swing.JPanel();
        comboCampilobacter = new javax.swing.JComboBox<>();
        cajaCampilobacter = new javax.swing.JTextField();
        comboCampilobacterIn = new javax.swing.JComboBox<>();
        jPanel13 = new javax.swing.JPanel();
        comboEscherichiaH7 = new javax.swing.JComboBox<>();
        comboEscherichiaH7In = new javax.swing.JComboBox<>();
        jPanel15 = new javax.swing.JPanel();
        comboSalmonella = new javax.swing.JComboBox<>();
        comboSalmonellaIn = new javax.swing.JComboBox<>();
        jPanel6 = new javax.swing.JPanel();
        comboStaphilococos = new javax.swing.JComboBox<>();
        cajaStaphilococos = new javax.swing.JTextField();
        comboStaphilococosIn = new javax.swing.JComboBox<>();
        jPanel25 = new javax.swing.JPanel();
        comboColiformesTotalesA30 = new javax.swing.JComboBox<>();
        cajaColiformesTotalesA30 = new javax.swing.JTextField();
        comboColiformesTotalesA30In = new javax.swing.JComboBox<>();
        jPanel26 = new javax.swing.JPanel();
        comboColiformesTotalesProbables = new javax.swing.JComboBox<>();
        cajaColiformesTotalesProbables = new javax.swing.JTextField();
        comboColiformesTotalesProbablesIn = new javax.swing.JComboBox<>();
        jPanel27 = new javax.swing.JPanel();
        cajaCaracteristicas = new javax.swing.JTextField();
        jPanel19 = new javax.swing.JPanel();
        comboLactobacillus = new javax.swing.JComboBox<>();
        cajaLactobacillus = new javax.swing.JTextField();
        comboLactobacillusIn = new javax.swing.JComboBox<>();
        jPanel20 = new javax.swing.JPanel();
        comboBacteriasLacticas = new javax.swing.JComboBox<>();
        cajaBacteriasLacticas = new javax.swing.JTextField();
        comboBacteriasLacticasIn = new javax.swing.JComboBox<>();
        jPanel29 = new javax.swing.JPanel();
        comboColiformesTotales45 = new javax.swing.JComboBox<>();
        cajaColiformesTotales45 = new javax.swing.JTextField();
        comboColiformesTotales45In = new javax.swing.JComboBox<>();
        jPanel7 = new javax.swing.JPanel();
        comboMohos = new javax.swing.JComboBox<>();
        cajaMohos = new javax.swing.JTextField();
        comboMohosIn = new javax.swing.JComboBox<>();
        jPanel30 = new javax.swing.JPanel();
        comboVibrio = new javax.swing.JComboBox<>();
        cajaVibrio = new javax.swing.JTextField();
        comboVibrioIn = new javax.swing.JComboBox<>();
        jPanel28 = new javax.swing.JPanel();
        comboShigella = new javax.swing.JComboBox<>();
        comboShigellaIn = new javax.swing.JComboBox<>();
        jPanel31 = new javax.swing.JPanel();
        comboListeria = new javax.swing.JComboBox<>();
        cajaListeria = new javax.swing.JTextField();
        comboListeriaIn = new javax.swing.JComboBox<>();
        jPanel32 = new javax.swing.JPanel();
        comboVibrioCholerae = new javax.swing.JComboBox<>();
        cajaVibrioCholerae = new javax.swing.JTextField();
        comboVibrioCholeraeIn = new javax.swing.JComboBox<>();
        checkConclusion = new javax.swing.JCheckBox();
        panelTotales = new javax.swing.JPanel();
        etiquetaTotales = new javax.swing.JLabel();
        panelGermenes = new javax.swing.JPanel();
        etiquetaGermenes = new javax.swing.JLabel();
        checkColiformesFecales = new javax.swing.JCheckBox();
        checkColiformesTotales = new javax.swing.JCheckBox();
        checkGermenes = new javax.swing.JCheckBox();
        panelEscherichia157 = new javax.swing.JPanel();
        etiquetaEscherichia157 = new javax.swing.JLabel();
        panelEnterobacterias = new javax.swing.JPanel();
        etiquetaEnterobacterias = new javax.swing.JLabel();
        panelListeria = new javax.swing.JPanel();
        etiquetaListeria = new javax.swing.JLabel();
        panelBacillus = new javax.swing.JPanel();
        etiquetaBacillus = new javax.swing.JLabel();
        panelPerfringens = new javax.swing.JPanel();
        etiquetaPerfringens = new javax.swing.JLabel();
        panelSulfito = new javax.swing.JPanel();
        etiquetaSulfito = new javax.swing.JLabel();
        panelCampilobacter = new javax.swing.JPanel();
        etiquetaCampilobacter = new javax.swing.JLabel();
        panelEscherichia = new javax.swing.JPanel();
        etiquetaEscherichia = new javax.swing.JLabel();
        metodoTotales = new javax.swing.JComboBox<>();
        metodoFecales = new javax.swing.JComboBox<>();
        metodoEscherichia = new javax.swing.JComboBox<>();
        metodoEscherichiaH7 = new javax.swing.JComboBox<>();
        metodoEscherichia157 = new javax.swing.JComboBox<>();
        metodoStaphilococos = new javax.swing.JComboBox<>();
        metodoEnterobacterias = new javax.swing.JComboBox<>();
        metodoMohos = new javax.swing.JComboBox<>();
        metodoSalmonella = new javax.swing.JComboBox<>();
        metodoListeria = new javax.swing.JComboBox<>();
        metodoBacillus = new javax.swing.JComboBox<>();
        metodoPerfringens = new javax.swing.JComboBox<>();
        metodoSulfito = new javax.swing.JComboBox<>();
        metodoCampilobacter = new javax.swing.JComboBox<>();
        metodoGermenes = new javax.swing.JComboBox<>();
        checkStaphilococos = new javax.swing.JCheckBox();
        checkMohos = new javax.swing.JCheckBox();
        checkBacillus = new javax.swing.JCheckBox();
        checkPerfringens = new javax.swing.JCheckBox();
        checkSulfito = new javax.swing.JCheckBox();
        checkCampilobacter = new javax.swing.JCheckBox();
        panelCaracteristicas = new javax.swing.JPanel();
        etiquetaCaracteristicas = new javax.swing.JLabel();
        metodoCaracteristicas = new javax.swing.JComboBox<>();
        panelColiformesTotalesA30 = new javax.swing.JPanel();
        etiquetaColiformesTotalesA30 = new javax.swing.JLabel();
        metodoColiformesTotalesA30 = new javax.swing.JComboBox<>();
        panelColiformesTotalesProbables = new javax.swing.JPanel();
        etiquetaColiformesTotalesProbable = new javax.swing.JLabel();
        metodoColiformesTotalesProbables = new javax.swing.JComboBox<>();
        panelLactobacillus = new javax.swing.JPanel();
        etiquetaLactobacillus = new javax.swing.JLabel();
        metodoLactobacillus = new javax.swing.JComboBox<>();
        metodoBacteriasLacticas = new javax.swing.JComboBox<>();
        panelBacteriasLacticas = new javax.swing.JPanel();
        etiquetaBacteriasLacticas = new javax.swing.JLabel();
        panelColiformesTotales45 = new javax.swing.JPanel();
        etiquetaColiformesTotales45 = new javax.swing.JLabel();
        metodoColiformesTotales45 = new javax.swing.JComboBox<>();
        checkColiformesTotalesA30 = new javax.swing.JCheckBox();
        checkColiformesTotalesProbables = new javax.swing.JCheckBox();
        checkLactobacillus = new javax.swing.JCheckBox();
        checkBacteriasLacticas = new javax.swing.JCheckBox();
        checkColiformesTotales45 = new javax.swing.JCheckBox();
        panelVibrio = new javax.swing.JPanel();
        etiquetaVibrio = new javax.swing.JLabel();
        metodoVibrio = new javax.swing.JComboBox<>();
        checkVibrio = new javax.swing.JCheckBox();
        panelShigella = new javax.swing.JPanel();
        etiquetaShigella = new javax.swing.JLabel();
        metodoShigella = new javax.swing.JComboBox<>();
        checkListeria = new javax.swing.JCheckBox();
        panelVibrioCholerae = new javax.swing.JPanel();
        etiquetaVibrioCholerae = new javax.swing.JLabel();
        metodoVibrioCholerae = new javax.swing.JComboBox<>();
        checkVibrioCholerae = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jScrollPane1.getVerticalScrollBar().setUnitIncrement(16);

        jPanel1.setFocusable(false);
        jPanel1.setLayout(new java.awt.GridBagLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("<html>MÉTODO</html>");
        jLabel1.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        jLabel1.setFocusable(false);
        jLabel1.setMaximumSize(new java.awt.Dimension(200, 30));
        jLabel1.setMinimumSize(new java.awt.Dimension(200, 30));
        jLabel1.setOpaque(true);
        jLabel1.setPreferredSize(new java.awt.Dimension(200, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(20, 0, 0, 20);
        jPanel1.add(jLabel1, gridBagConstraints);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("<html>DETERMINACIONES</html>");
        jLabel2.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 0, 1, new java.awt.Color(0, 0, 0)));
        jLabel2.setFocusable(false);
        jLabel2.setMaximumSize(new java.awt.Dimension(200, 30));
        jLabel2.setMinimumSize(new java.awt.Dimension(200, 30));
        jLabel2.setOpaque(true);
        jLabel2.setPreferredSize(new java.awt.Dimension(200, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(20, 20, 0, 0);
        jPanel1.add(jLabel2, gridBagConstraints);
        jLabel2.setBorder(new MatteBorder(1,1,0,1, Color.black));

        panelEscherichiaH7.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 0, 1, new java.awt.Color(0, 0, 0)));
        panelEscherichiaH7.setFocusable(false);
        panelEscherichiaH7.setMaximumSize(new java.awt.Dimension(200, 30));
        panelEscherichiaH7.setMinimumSize(new java.awt.Dimension(200, 30));
        panelEscherichiaH7.setPreferredSize(new java.awt.Dimension(200, 30));
        panelEscherichiaH7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelEscherichiaH7MouseClicked(evt);
            }
        });
        panelEscherichiaH7.setLayout(new java.awt.GridBagLayout());

        etiquetaEscherichiaH7.setBackground(new java.awt.Color(0, 0, 0));
        etiquetaEscherichiaH7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        etiquetaEscherichiaH7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        etiquetaEscherichiaH7.setText("<html>ESCHERICHIA COLI O 157 H7</html>");
        etiquetaEscherichiaH7.setToolTipText("");
        etiquetaEscherichiaH7.setFocusable(false);
        etiquetaEscherichiaH7.setMaximumSize(new java.awt.Dimension(190, 30));
        etiquetaEscherichiaH7.setMinimumSize(new java.awt.Dimension(190, 30));
        etiquetaEscherichiaH7.setPreferredSize(new java.awt.Dimension(190, 30));
        etiquetaEscherichiaH7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                etiquetaEscherichiaH7MouseClicked(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        panelEscherichiaH7.add(etiquetaEscherichiaH7, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        jPanel1.add(panelEscherichiaH7, gridBagConstraints);

        panelMohos.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 0, 1, new java.awt.Color(0, 0, 0)));
        panelMohos.setFocusable(false);
        panelMohos.setMaximumSize(new java.awt.Dimension(200, 30));
        panelMohos.setMinimumSize(new java.awt.Dimension(200, 30));
        panelMohos.setPreferredSize(new java.awt.Dimension(200, 30));
        panelMohos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelMohosMouseClicked(evt);
            }
        });
        panelMohos.setLayout(new java.awt.GridBagLayout());

        etiquetaMohos.setBackground(new java.awt.Color(0, 0, 0));
        etiquetaMohos.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        etiquetaMohos.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        etiquetaMohos.setText("<html>MOHOS Y LEVADURAS</html>");
        etiquetaMohos.setToolTipText("");
        etiquetaMohos.setFocusable(false);
        etiquetaMohos.setMaximumSize(new java.awt.Dimension(190, 30));
        etiquetaMohos.setMinimumSize(new java.awt.Dimension(190, 30));
        etiquetaMohos.setPreferredSize(new java.awt.Dimension(190, 30));
        etiquetaMohos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                etiquetaMohosMouseClicked(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        panelMohos.add(etiquetaMohos, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        jPanel1.add(panelMohos, gridBagConstraints);

        panelFecales.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 0, 1, new java.awt.Color(0, 0, 0)));
        panelFecales.setFocusable(false);
        panelFecales.setMaximumSize(new java.awt.Dimension(200, 30));
        panelFecales.setMinimumSize(new java.awt.Dimension(200, 30));
        panelFecales.setPreferredSize(new java.awt.Dimension(200, 30));
        panelFecales.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelFecalesMouseClicked(evt);
            }
        });
        panelFecales.setLayout(new java.awt.GridBagLayout());

        etiquetaFecales.setBackground(new java.awt.Color(0, 0, 0));
        etiquetaFecales.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        etiquetaFecales.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        etiquetaFecales.setText("COLIFORMES FECALES");
        etiquetaFecales.setToolTipText("");
        etiquetaFecales.setFocusable(false);
        etiquetaFecales.setMaximumSize(new java.awt.Dimension(190, 30));
        etiquetaFecales.setMinimumSize(new java.awt.Dimension(190, 30));
        etiquetaFecales.setPreferredSize(new java.awt.Dimension(190, 30));
        etiquetaFecales.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                etiquetaFecalesMouseClicked(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        panelFecales.add(etiquetaFecales, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        jPanel1.add(panelFecales, gridBagConstraints);

        panelStaphilococos.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 0, 1, new java.awt.Color(0, 0, 0)));
        panelStaphilococos.setFocusable(false);
        panelStaphilococos.setMaximumSize(new java.awt.Dimension(200, 30));
        panelStaphilococos.setMinimumSize(new java.awt.Dimension(200, 30));
        panelStaphilococos.setPreferredSize(new java.awt.Dimension(200, 30));
        panelStaphilococos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelStaphilococosMouseClicked(evt);
            }
        });
        panelStaphilococos.setLayout(new java.awt.GridBagLayout());

        etiquetaStaphilococos.setBackground(new java.awt.Color(0, 0, 0));
        etiquetaStaphilococos.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        etiquetaStaphilococos.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        etiquetaStaphilococos.setText("<html>STAPHILOCOCOS AUREUS COAGULASA (+)</html>");
        etiquetaStaphilococos.setToolTipText("");
        etiquetaStaphilococos.setFocusable(false);
        etiquetaStaphilococos.setMaximumSize(new java.awt.Dimension(190, 30));
        etiquetaStaphilococos.setMinimumSize(new java.awt.Dimension(190, 30));
        etiquetaStaphilococos.setPreferredSize(new java.awt.Dimension(190, 30));
        etiquetaStaphilococos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                etiquetaStaphilococosMouseClicked(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        panelStaphilococos.add(etiquetaStaphilococos, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        jPanel1.add(panelStaphilococos, gridBagConstraints);

        botonGenerar.setText("Generar informe");
        botonGenerar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonGenerarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 30;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 20, 20);
        jPanel1.add(botonGenerar, gridBagConstraints);
        InputMap im = jPanel1.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false), "Apretar");

        ActionMap ap = jPanel1.getActionMap();
        ap.put("Apretar", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e){
                botonGenerar.doClick();
            }
        });

        etiquetaTitulo.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        etiquetaTitulo.setText("Agregar resultados de análisis");
        etiquetaTitulo.setFocusable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(20, 20, 0, 0);
        jPanel1.add(etiquetaTitulo, gridBagConstraints);

        panelSalmonella.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 0, 1, new java.awt.Color(0, 0, 0)));
        panelSalmonella.setFocusable(false);
        panelSalmonella.setMaximumSize(new java.awt.Dimension(200, 30));
        panelSalmonella.setMinimumSize(new java.awt.Dimension(200, 30));
        panelSalmonella.setPreferredSize(new java.awt.Dimension(200, 30));
        panelSalmonella.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelSalmonellaMouseClicked(evt);
            }
        });
        panelSalmonella.setLayout(new java.awt.GridBagLayout());

        etiquetaSalmonella.setBackground(new java.awt.Color(0, 0, 0));
        etiquetaSalmonella.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        etiquetaSalmonella.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        etiquetaSalmonella.setText("<html>SALMONELLA sp</html>");
        etiquetaSalmonella.setToolTipText("");
        etiquetaSalmonella.setFocusable(false);
        etiquetaSalmonella.setMaximumSize(new java.awt.Dimension(190, 30));
        etiquetaSalmonella.setMinimumSize(new java.awt.Dimension(190, 30));
        etiquetaSalmonella.setPreferredSize(new java.awt.Dimension(190, 30));
        etiquetaSalmonella.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                etiquetaSalmonellaMouseClicked(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        panelSalmonella.add(etiquetaSalmonella, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        jPanel1.add(panelSalmonella, gridBagConstraints);

        checkEnterobacterias.setText("MENOR A 10");
        checkEnterobacterias.setNextFocusableComponent(botonGenerar);
        checkEnterobacterias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkEnterobacteriasActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        jPanel1.add(checkEnterobacterias, gridBagConstraints);

        jLabel3.setText("Fecha de análisis");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
        jPanel1.add(jLabel3, gridBagConstraints);

        cajaFechaAnalisis.setDateFormatString("dd-MM-yyyy");
        cajaFechaAnalisis.setMaxSelectableDate(new java.util.Date(253370779267000L));
        cajaFechaAnalisis.setMaximumSize(new java.awt.Dimension(120, 24));
        cajaFechaAnalisis.setMinimumSize(new java.awt.Dimension(120, 24));
        cajaFechaAnalisis.setPreferredSize(new java.awt.Dimension(120, 24));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 0);
        jPanel1.add(cajaFechaAnalisis, gridBagConstraints);

        jPanel9.setLayout(new java.awt.GridBagLayout());

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("<html>RECUENTO OBTENIDO</html>");
        jLabel4.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        jLabel4.setFocusable(false);
        jLabel4.setMaximumSize(new java.awt.Dimension(170, 30));
        jLabel4.setMinimumSize(new java.awt.Dimension(170, 30));
        jLabel4.setOpaque(true);
        jLabel4.setPreferredSize(new java.awt.Dimension(170, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(20, 0, 0, 0);
        jPanel9.add(jLabel4, gridBagConstraints);

        jPanel5.setFocusable(false);
        jPanel5.setMaximumSize(new java.awt.Dimension(170, 30));
        jPanel5.setMinimumSize(new java.awt.Dimension(170, 30));
        jPanel5.setPreferredSize(new java.awt.Dimension(170, 30));
        jPanel5.setLayout(new java.awt.GridBagLayout());

        comboColiformesFecales.setBackground(new java.awt.Color(204, 204, 204));
        comboColiformesFecales.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Menor a", " " }));
        comboColiformesFecales.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 0, new java.awt.Color(0, 0, 0)));
        comboColiformesFecales.setMaximumSize(new java.awt.Dimension(58, 30));
        comboColiformesFecales.setMinimumSize(new java.awt.Dimension(58, 30));
        comboColiformesFecales.setNextFocusableComponent(cajaColiformesFecales);
        comboColiformesFecales.setPreferredSize(new java.awt.Dimension(58, 30));
        comboColiformesFecales.setUI(new BasicComboBoxUI() {
            protected JButton createArrowButton() {
                return new JButton() {
                    public int getWidth() {
                        return 0;
                    }
                };
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jPanel5.add(comboColiformesFecales, gridBagConstraints);

        cajaColiformesFecales.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 0, new java.awt.Color(0, 0, 0)));
        cajaColiformesFecales.setMargin(new java.awt.Insets(20, 20, 20, 20));
        cajaColiformesFecales.setMaximumSize(new java.awt.Dimension(37, 45));
        cajaColiformesFecales.setMinimumSize(new java.awt.Dimension(37, 45));
        cajaColiformesFecales.setName(""); // NOI18N
        cajaColiformesFecales.setNextFocusableComponent(comboEscherichia);
        cajaColiformesFecales.setPreferredSize(new java.awt.Dimension(37, 45));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        jPanel5.add(cajaColiformesFecales, gridBagConstraints);

        comboColiformesFecalesIn.setBackground(new java.awt.Color(204, 204, 204));
        comboColiformesFecalesIn.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "UFC/g", "UFC/ml", "NMP/g", "NMP/ml" }));
        comboColiformesFecalesIn.setUI(new BasicComboBoxUI() {
            protected JButton createArrowButton() {
                return new JButton() {
                    public int getWidth() {
                        return 0;
                    }
                };
            }
        });
        comboColiformesFecalesIn.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        comboColiformesFecalesIn.setMaximumSize(new java.awt.Dimension(75, 30));
        comboColiformesFecalesIn.setMinimumSize(new java.awt.Dimension(75, 30));
        comboColiformesFecalesIn.setPreferredSize(new java.awt.Dimension(75, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        jPanel5.add(comboColiformesFecalesIn, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        jPanel9.add(jPanel5, gridBagConstraints);

        jPanel4.setFocusable(false);
        jPanel4.setMaximumSize(new java.awt.Dimension(170, 30));
        jPanel4.setMinimumSize(new java.awt.Dimension(170, 30));
        jPanel4.setPreferredSize(new java.awt.Dimension(170, 30));
        jPanel4.setLayout(new java.awt.GridBagLayout());

        comboEnterobacterias.setBackground(new java.awt.Color(204, 204, 204));
        comboEnterobacterias.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Menor a", " " }));
        comboEnterobacterias.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 0, new java.awt.Color(0, 0, 0)));
        comboEnterobacterias.setMaximumSize(new java.awt.Dimension(58, 30));
        comboEnterobacterias.setMinimumSize(new java.awt.Dimension(58, 30));
        comboEnterobacterias.setNextFocusableComponent(cajaEnterobacterias);
        comboEnterobacterias.setPreferredSize(new java.awt.Dimension(58, 30));
        comboEnterobacterias.setUI(new BasicComboBoxUI() {
            protected JButton createArrowButton() {
                return new JButton() {
                    public int getWidth() {
                        return 0;
                    }
                };
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jPanel4.add(comboEnterobacterias, gridBagConstraints);

        cajaEnterobacterias.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 0, new java.awt.Color(0, 0, 0)));
        cajaEnterobacterias.setMargin(new java.awt.Insets(20, 20, 20, 20));
        cajaEnterobacterias.setMaximumSize(new java.awt.Dimension(37, 45));
        cajaEnterobacterias.setMinimumSize(new java.awt.Dimension(37, 45));
        cajaEnterobacterias.setName(""); // NOI18N
        cajaEnterobacterias.setNextFocusableComponent(comboEscherichia157);
        cajaEnterobacterias.setPreferredSize(new java.awt.Dimension(37, 45));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        jPanel4.add(cajaEnterobacterias, gridBagConstraints);

        comboEnterobacteriasIn.setBackground(new java.awt.Color(204, 204, 204));
        comboEnterobacteriasIn.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "UFC/g", "UFC/ml" }));
        comboEnterobacteriasIn.setUI(new BasicComboBoxUI() {
            protected JButton createArrowButton() {
                return new JButton() {
                    public int getWidth() {
                        return 0;
                    }
                };
            }
        });
        comboEnterobacteriasIn.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        comboEnterobacteriasIn.setMaximumSize(new java.awt.Dimension(75, 30));
        comboEnterobacteriasIn.setMinimumSize(new java.awt.Dimension(75, 30));
        comboEnterobacteriasIn.setPreferredSize(new java.awt.Dimension(75, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        jPanel4.add(comboEnterobacteriasIn, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        jPanel9.add(jPanel4, gridBagConstraints);

        jPanel10.setFocusable(false);
        jPanel10.setMaximumSize(new java.awt.Dimension(170, 30));
        jPanel10.setMinimumSize(new java.awt.Dimension(170, 30));
        jPanel10.setPreferredSize(new java.awt.Dimension(170, 30));
        jPanel10.setLayout(new java.awt.GridBagLayout());

        comboEscherichia157.setBackground(new java.awt.Color(204, 204, 204));
        comboEscherichia157.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ausencia", "Presencia" }));
        comboEscherichia157.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        comboEscherichia157.setMaximumSize(new java.awt.Dimension(105, 30));
        comboEscherichia157.setMinimumSize(new java.awt.Dimension(105, 30));
        comboEscherichia157.setPreferredSize(new java.awt.Dimension(105, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jPanel10.add(comboEscherichia157, gridBagConstraints);
        comboEscherichia157.setUI(new BasicComboBoxUI() {
            protected JButton createArrowButton() {
                return new JButton() {
                    public int getWidth() {
                        return 0;
                    }
                };
            }
        });

        comboEscherichia157In.setBackground(new java.awt.Color(204, 204, 204));
        comboEscherichia157In.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "en 1 g", "en 10 g", "en 25 g", "en 65 g", "en 1 ml", "en 10 ml", "en 25 ml", "en 65 ml" }));
        comboEscherichia157In.setUI(new BasicComboBoxUI() {
            protected JButton createArrowButton() {
                return new JButton() {
                    public int getWidth() {
                        return 0;
                    }
                };
            }
        });
        comboEscherichia157In.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        comboEscherichia157In.setMaximumSize(new java.awt.Dimension(65, 30));
        comboEscherichia157In.setMinimumSize(new java.awt.Dimension(65, 30));
        comboEscherichia157In.setPreferredSize(new java.awt.Dimension(65, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        jPanel10.add(comboEscherichia157In, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        jPanel9.add(jPanel10, gridBagConstraints);

        jPanel14.setFocusable(false);
        jPanel14.setMaximumSize(new java.awt.Dimension(170, 30));
        jPanel14.setMinimumSize(new java.awt.Dimension(170, 30));
        jPanel14.setPreferredSize(new java.awt.Dimension(170, 30));
        jPanel14.setLayout(new java.awt.GridBagLayout());

        comboColiformesTotales.setBackground(new java.awt.Color(204, 204, 204));
        comboColiformesTotales.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Menor a", " " }));
        comboColiformesTotales.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 0, new java.awt.Color(0, 0, 0)));
        comboColiformesTotales.setMaximumSize(new java.awt.Dimension(58, 30));
        comboColiformesTotales.setMinimumSize(new java.awt.Dimension(58, 30));
        comboColiformesTotales.setNextFocusableComponent(cajaColiformesFecales);
        comboColiformesTotales.setPreferredSize(new java.awt.Dimension(58, 30));
        comboColiformesTotales.setUI(new BasicComboBoxUI() {
            protected JButton createArrowButton() {
                return new JButton() {
                    public int getWidth() {
                        return 0;
                    }
                };
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jPanel14.add(comboColiformesTotales, gridBagConstraints);

        cajaColiformesTotales.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 0, new java.awt.Color(0, 0, 0)));
        cajaColiformesTotales.setMargin(new java.awt.Insets(20, 20, 20, 20));
        cajaColiformesTotales.setMaximumSize(new java.awt.Dimension(37, 45));
        cajaColiformesTotales.setMinimumSize(new java.awt.Dimension(37, 45));
        cajaColiformesTotales.setName(""); // NOI18N
        cajaColiformesTotales.setNextFocusableComponent(comboEscherichia);
        cajaColiformesTotales.setPreferredSize(new java.awt.Dimension(37, 45));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        jPanel14.add(cajaColiformesTotales, gridBagConstraints);

        comboColiformesTotalesIn.setBackground(new java.awt.Color(204, 204, 204));
        comboColiformesTotalesIn.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "UFC/g", "UFC/ml", "NMP/g", "NMP/ml" }));
        comboColiformesTotalesIn.setUI(new BasicComboBoxUI() {
            protected JButton createArrowButton() {
                return new JButton() {
                    public int getWidth() {
                        return 0;
                    }
                };
            }
        });
        comboColiformesTotalesIn.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        comboColiformesTotalesIn.setMaximumSize(new java.awt.Dimension(75, 30));
        comboColiformesTotalesIn.setMinimumSize(new java.awt.Dimension(75, 30));
        comboColiformesTotalesIn.setPreferredSize(new java.awt.Dimension(75, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        jPanel14.add(comboColiformesTotalesIn, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        jPanel9.add(jPanel14, gridBagConstraints);

        jPanel16.setFocusable(false);
        jPanel16.setMaximumSize(new java.awt.Dimension(170, 30));
        jPanel16.setMinimumSize(new java.awt.Dimension(170, 30));
        jPanel16.setPreferredSize(new java.awt.Dimension(170, 30));
        jPanel16.setLayout(new java.awt.GridBagLayout());

        comboGermenes.setBackground(new java.awt.Color(204, 204, 204));
        comboGermenes.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Menor a", " " }));
        comboGermenes.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 0, new java.awt.Color(0, 0, 0)));
        comboGermenes.setMaximumSize(new java.awt.Dimension(58, 30));
        comboGermenes.setMinimumSize(new java.awt.Dimension(58, 30));
        comboGermenes.setNextFocusableComponent(cajaColiformesFecales);
        comboGermenes.setPreferredSize(new java.awt.Dimension(58, 30));
        comboGermenes.setUI(new BasicComboBoxUI() {
            protected JButton createArrowButton() {
                return new JButton() {
                    public int getWidth() {
                        return 0;
                    }
                };
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jPanel16.add(comboGermenes, gridBagConstraints);

        cajaGermenes.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 0, new java.awt.Color(0, 0, 0)));
        cajaGermenes.setMargin(new java.awt.Insets(20, 20, 20, 20));
        cajaGermenes.setMaximumSize(new java.awt.Dimension(37, 45));
        cajaGermenes.setMinimumSize(new java.awt.Dimension(37, 45));
        cajaGermenes.setName(""); // NOI18N
        cajaGermenes.setNextFocusableComponent(comboEscherichia);
        cajaGermenes.setPreferredSize(new java.awt.Dimension(37, 45));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        jPanel16.add(cajaGermenes, gridBagConstraints);

        comboGermenesIn.setBackground(new java.awt.Color(204, 204, 204));
        comboGermenesIn.setUI(new BasicComboBoxUI() {
            protected JButton createArrowButton() {
                return new JButton() {
                    public int getWidth() {
                        return 0;
                    }
                };
            }
        });
        comboGermenesIn.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "UFC/g", "UFC/ml" }));
        comboGermenesIn.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        comboGermenesIn.setMaximumSize(new java.awt.Dimension(75, 30));
        comboGermenesIn.setMinimumSize(new java.awt.Dimension(75, 30));
        comboGermenesIn.setPreferredSize(new java.awt.Dimension(75, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        jPanel16.add(comboGermenesIn, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        jPanel9.add(jPanel16, gridBagConstraints);

        jPanel11.setFocusable(false);
        jPanel11.setMaximumSize(new java.awt.Dimension(170, 30));
        jPanel11.setMinimumSize(new java.awt.Dimension(170, 30));
        jPanel11.setPreferredSize(new java.awt.Dimension(170, 30));
        jPanel11.setLayout(new java.awt.GridBagLayout());

        comboEscherichia.setBackground(new java.awt.Color(204, 204, 204));
        comboEscherichia.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ausencia", "Presencia" }));
        comboEscherichia.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        comboEscherichia.setMaximumSize(new java.awt.Dimension(105, 44));
        comboEscherichia.setMinimumSize(new java.awt.Dimension(105, 44));
        comboEscherichia.setPreferredSize(new java.awt.Dimension(105, 44));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jPanel11.add(comboEscherichia, gridBagConstraints);
        comboEscherichia.setUI(new BasicComboBoxUI() {
            protected JButton createArrowButton() {
                return new JButton() {
                    public int getWidth() {
                        return 0;
                    }
                };
            }
        });

        comboEscherichiaIn.setBackground(new java.awt.Color(204, 204, 204));
        comboEscherichiaIn.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "en 1 g", "en 10 g", "en 25 g", "en 65 g", "en 1 ml", "en 10 ml", "en 25 ml", "en 65 ml" }));
        comboEscherichiaIn.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        comboEscherichiaIn.setMaximumSize(new java.awt.Dimension(65, 45));
        comboEscherichiaIn.setMinimumSize(new java.awt.Dimension(65, 45));
        comboEscherichiaIn.setPreferredSize(new java.awt.Dimension(65, 45));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        jPanel11.add(comboEscherichiaIn, gridBagConstraints);
        comboEscherichiaIn.setUI(new BasicComboBoxUI() {
            protected JButton createArrowButton() {
                return new JButton() {
                    public int getWidth() {
                        return 0;
                    }
                };
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        jPanel9.add(jPanel11, gridBagConstraints);

        jPanel21.setFocusable(false);
        jPanel21.setMaximumSize(new java.awt.Dimension(170, 30));
        jPanel21.setMinimumSize(new java.awt.Dimension(170, 30));
        jPanel21.setPreferredSize(new java.awt.Dimension(170, 30));
        jPanel21.setLayout(new java.awt.GridBagLayout());

        comboBacillus.setBackground(new java.awt.Color(204, 204, 204));
        comboBacillus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Menor a", " " }));
        comboBacillus.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 0, new java.awt.Color(0, 0, 0)));
        comboBacillus.setMaximumSize(new java.awt.Dimension(58, 30));
        comboBacillus.setMinimumSize(new java.awt.Dimension(58, 30));
        comboBacillus.setNextFocusableComponent(cajaColiformesFecales);
        comboBacillus.setPreferredSize(new java.awt.Dimension(58, 30));
        comboBacillus.setUI(new BasicComboBoxUI() {
            protected JButton createArrowButton() {
                return new JButton() {
                    public int getWidth() {
                        return 0;
                    }
                };
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jPanel21.add(comboBacillus, gridBagConstraints);

        cajaBacillus.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 0, new java.awt.Color(0, 0, 0)));
        cajaBacillus.setMargin(new java.awt.Insets(20, 20, 20, 20));
        cajaBacillus.setMaximumSize(new java.awt.Dimension(37, 45));
        cajaBacillus.setMinimumSize(new java.awt.Dimension(37, 45));
        cajaBacillus.setName(""); // NOI18N
        cajaBacillus.setNextFocusableComponent(comboEscherichia);
        cajaBacillus.setPreferredSize(new java.awt.Dimension(37, 45));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        jPanel21.add(cajaBacillus, gridBagConstraints);

        comboBacillusIn.setBackground(new java.awt.Color(204, 204, 204));
        comboBacillusIn.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "UFC/g", "UFC/ml" }));
        comboBacillusIn.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        comboBacillusIn.setMaximumSize(new java.awt.Dimension(75, 30));
        comboBacillusIn.setMinimumSize(new java.awt.Dimension(75, 30));
        comboBacillusIn.setPreferredSize(new java.awt.Dimension(75, 30));
        comboBacillusIn.setUI(new BasicComboBoxUI() {
            protected JButton createArrowButton() {
                return new JButton() {
                    public int getWidth() {
                        return 0;
                    }
                };
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        jPanel21.add(comboBacillusIn, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 12;
        jPanel9.add(jPanel21, gridBagConstraints);

        jPanel22.setFocusable(false);
        jPanel22.setMaximumSize(new java.awt.Dimension(170, 30));
        jPanel22.setMinimumSize(new java.awt.Dimension(170, 30));
        jPanel22.setPreferredSize(new java.awt.Dimension(170, 30));
        jPanel22.setLayout(new java.awt.GridBagLayout());

        comboPerfringens.setBackground(new java.awt.Color(204, 204, 204));
        comboPerfringens.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Menor a", " " }));
        comboPerfringens.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 0, new java.awt.Color(0, 0, 0)));
        comboPerfringens.setMaximumSize(new java.awt.Dimension(58, 30));
        comboPerfringens.setMinimumSize(new java.awt.Dimension(58, 30));
        comboPerfringens.setNextFocusableComponent(cajaColiformesFecales);
        comboPerfringens.setPreferredSize(new java.awt.Dimension(58, 30));
        comboPerfringens.setUI(new BasicComboBoxUI() {
            protected JButton createArrowButton() {
                return new JButton() {
                    public int getWidth() {
                        return 0;
                    }
                };
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jPanel22.add(comboPerfringens, gridBagConstraints);

        cajaPerfringens.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 0, new java.awt.Color(0, 0, 0)));
        cajaPerfringens.setMargin(new java.awt.Insets(20, 20, 20, 20));
        cajaPerfringens.setMaximumSize(new java.awt.Dimension(37, 45));
        cajaPerfringens.setMinimumSize(new java.awt.Dimension(37, 45));
        cajaPerfringens.setName(""); // NOI18N
        cajaPerfringens.setNextFocusableComponent(comboEscherichia);
        cajaPerfringens.setPreferredSize(new java.awt.Dimension(37, 45));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        jPanel22.add(cajaPerfringens, gridBagConstraints);

        comboPerfringensIn.setBackground(new java.awt.Color(204, 204, 204));
        comboPerfringensIn.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "UFC/g", "UFC/ml" }));
        comboPerfringensIn.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        comboPerfringensIn.setMaximumSize(new java.awt.Dimension(75, 30));
        comboPerfringensIn.setMinimumSize(new java.awt.Dimension(75, 30));
        comboPerfringensIn.setPreferredSize(new java.awt.Dimension(75, 30));
        comboPerfringensIn.setUI(new BasicComboBoxUI() {
            protected JButton createArrowButton() {
                return new JButton() {
                    public int getWidth() {
                        return 0;
                    }
                };
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        jPanel22.add(comboPerfringensIn, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 13;
        jPanel9.add(jPanel22, gridBagConstraints);

        jPanel23.setFocusable(false);
        jPanel23.setMaximumSize(new java.awt.Dimension(170, 30));
        jPanel23.setMinimumSize(new java.awt.Dimension(170, 30));
        jPanel23.setPreferredSize(new java.awt.Dimension(170, 30));
        jPanel23.setLayout(new java.awt.GridBagLayout());

        comboSulfito.setBackground(new java.awt.Color(204, 204, 204));
        comboSulfito.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Menor a", " " }));
        comboSulfito.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 0, new java.awt.Color(0, 0, 0)));
        comboSulfito.setMaximumSize(new java.awt.Dimension(58, 30));
        comboSulfito.setMinimumSize(new java.awt.Dimension(58, 30));
        comboSulfito.setNextFocusableComponent(cajaColiformesFecales);
        comboSulfito.setPreferredSize(new java.awt.Dimension(58, 30));
        comboSulfito.setUI(new BasicComboBoxUI() {
            protected JButton createArrowButton() {
                return new JButton() {
                    public int getWidth() {
                        return 0;
                    }
                };
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jPanel23.add(comboSulfito, gridBagConstraints);

        cajaSulfito.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 0, new java.awt.Color(0, 0, 0)));
        cajaSulfito.setMargin(new java.awt.Insets(20, 20, 20, 20));
        cajaSulfito.setMaximumSize(new java.awt.Dimension(37, 45));
        cajaSulfito.setMinimumSize(new java.awt.Dimension(37, 45));
        cajaSulfito.setName(""); // NOI18N
        cajaSulfito.setNextFocusableComponent(comboEscherichia);
        cajaSulfito.setPreferredSize(new java.awt.Dimension(37, 45));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        jPanel23.add(cajaSulfito, gridBagConstraints);

        comboSulfitoIn.setBackground(new java.awt.Color(204, 204, 204));
        comboSulfitoIn.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "UFC/g", "UFC/ml" }));
        comboSulfitoIn.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        comboSulfitoIn.setMaximumSize(new java.awt.Dimension(75, 30));
        comboSulfitoIn.setMinimumSize(new java.awt.Dimension(75, 30));
        comboSulfitoIn.setPreferredSize(new java.awt.Dimension(75, 30));
        comboSulfitoIn.setUI(new BasicComboBoxUI() {
            protected JButton createArrowButton() {
                return new JButton() {
                    public int getWidth() {
                        return 0;
                    }
                };
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        jPanel23.add(comboSulfitoIn, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 14;
        jPanel9.add(jPanel23, gridBagConstraints);

        jPanel24.setFocusable(false);
        jPanel24.setMaximumSize(new java.awt.Dimension(170, 30));
        jPanel24.setMinimumSize(new java.awt.Dimension(170, 30));
        jPanel24.setPreferredSize(new java.awt.Dimension(170, 30));
        jPanel24.setLayout(new java.awt.GridBagLayout());

        comboCampilobacter.setBackground(new java.awt.Color(204, 204, 204));
        comboCampilobacter.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Menor a", " " }));
        comboCampilobacter.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 0, new java.awt.Color(0, 0, 0)));
        comboCampilobacter.setMaximumSize(new java.awt.Dimension(58, 30));
        comboCampilobacter.setMinimumSize(new java.awt.Dimension(58, 30));
        comboCampilobacter.setNextFocusableComponent(cajaColiformesFecales);
        comboCampilobacter.setPreferredSize(new java.awt.Dimension(58, 30));
        comboCampilobacter.setUI(new BasicComboBoxUI() {
            protected JButton createArrowButton() {
                return new JButton() {
                    public int getWidth() {
                        return 0;
                    }
                };
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jPanel24.add(comboCampilobacter, gridBagConstraints);

        cajaCampilobacter.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 0, new java.awt.Color(0, 0, 0)));
        cajaCampilobacter.setMargin(new java.awt.Insets(20, 20, 20, 20));
        cajaCampilobacter.setMaximumSize(new java.awt.Dimension(37, 30));
        cajaCampilobacter.setMinimumSize(new java.awt.Dimension(37, 30));
        cajaCampilobacter.setName(""); // NOI18N
        cajaCampilobacter.setNextFocusableComponent(comboEscherichia);
        cajaCampilobacter.setPreferredSize(new java.awt.Dimension(37, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        jPanel24.add(cajaCampilobacter, gridBagConstraints);

        comboCampilobacterIn.setBackground(new java.awt.Color(204, 204, 204));
        comboCampilobacterIn.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "UFC/g", "UFC/ml" }));
        comboCampilobacterIn.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        comboCampilobacterIn.setMaximumSize(new java.awt.Dimension(75, 45));
        comboCampilobacterIn.setMinimumSize(new java.awt.Dimension(75, 45));
        comboCampilobacterIn.setPreferredSize(new java.awt.Dimension(75, 30));
        comboCampilobacterIn.setUI(new BasicComboBoxUI() {
            protected JButton createArrowButton() {
                return new JButton() {
                    public int getWidth() {
                        return 0;
                    }
                };
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        jPanel24.add(comboCampilobacterIn, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 15;
        jPanel9.add(jPanel24, gridBagConstraints);

        jPanel13.setFocusable(false);
        jPanel13.setMaximumSize(new java.awt.Dimension(170, 30));
        jPanel13.setMinimumSize(new java.awt.Dimension(170, 30));
        jPanel13.setPreferredSize(new java.awt.Dimension(170, 30));
        jPanel13.setLayout(new java.awt.GridBagLayout());

        comboEscherichiaH7.setBackground(new java.awt.Color(204, 204, 204));
        comboEscherichiaH7.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ausencia", "Presencia" }));
        comboEscherichiaH7.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        comboEscherichiaH7.setMaximumSize(new java.awt.Dimension(105, 44));
        comboEscherichiaH7.setMinimumSize(new java.awt.Dimension(105, 44));
        comboEscherichiaH7.setPreferredSize(new java.awt.Dimension(105, 44));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jPanel13.add(comboEscherichiaH7, gridBagConstraints);
        comboEscherichiaH7.setUI(new BasicComboBoxUI() {
            protected JButton createArrowButton() {
                return new JButton() {
                    public int getWidth() {
                        return 0;
                    }
                };
            }
        });

        comboEscherichiaH7In.setBackground(new java.awt.Color(204, 204, 204));
        comboEscherichiaH7In.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "en 1 g", "en 10 g", "en 25 g", "en 65 g", "en 1 ml", "en 10 ml", "en 25 ml", "en 65 ml" }));
        comboEscherichiaH7In.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        comboEscherichiaH7In.setMaximumSize(new java.awt.Dimension(65, 45));
        comboEscherichiaH7In.setMinimumSize(new java.awt.Dimension(65, 45));
        comboEscherichiaH7In.setPreferredSize(new java.awt.Dimension(65, 45));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        jPanel13.add(comboEscherichiaH7In, gridBagConstraints);
        comboEscherichiaH7In.setUI(new BasicComboBoxUI() {
            protected JButton createArrowButton() {
                return new JButton() {
                    public int getWidth() {
                        return 0;
                    }
                };
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        jPanel9.add(jPanel13, gridBagConstraints);

        jPanel15.setFocusable(false);
        jPanel15.setMaximumSize(new java.awt.Dimension(170, 30));
        jPanel15.setLayout(new java.awt.GridBagLayout());

        comboSalmonella.setBackground(new java.awt.Color(204, 204, 204));
        comboSalmonella.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ausencia", "Presencia" }));
        comboSalmonella.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 0, new java.awt.Color(0, 0, 0)));
        comboSalmonella.setMaximumSize(new java.awt.Dimension(105, 30));
        comboSalmonella.setMinimumSize(new java.awt.Dimension(105, 30));
        comboSalmonella.setPreferredSize(new java.awt.Dimension(105, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jPanel15.add(comboSalmonella, gridBagConstraints);
        comboSalmonella.setUI(new BasicComboBoxUI() {
            protected JButton createArrowButton() {
                return new JButton() {
                    public int getWidth() {
                        return 0;
                    }
                };
            }
        });

        comboSalmonellaIn.setBackground(new java.awt.Color(204, 204, 204));
        comboSalmonellaIn.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "en 10 g", "en 25 g", "en 65 g", "en 10 ml", "en 25 ml", "en 65 ml" }));
        comboSalmonellaIn.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        comboSalmonellaIn.setMaximumSize(new java.awt.Dimension(65, 30));
        comboSalmonellaIn.setMinimumSize(new java.awt.Dimension(65, 30));
        comboSalmonellaIn.setPreferredSize(new java.awt.Dimension(65, 30));
        comboSalmonellaIn.setUI(new BasicComboBoxUI() {
            protected JButton createArrowButton() {
                return new JButton() {
                    public int getWidth() {
                        return 0;
                    }
                };
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        jPanel15.add(comboSalmonellaIn, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        jPanel9.add(jPanel15, gridBagConstraints);

        jPanel6.setFocusable(false);
        jPanel6.setMaximumSize(new java.awt.Dimension(170, 30));
        jPanel6.setMinimumSize(new java.awt.Dimension(170, 30));
        jPanel6.setPreferredSize(new java.awt.Dimension(170, 30));
        jPanel6.setLayout(new java.awt.GridBagLayout());

        comboStaphilococos.setBackground(new java.awt.Color(204, 204, 204));
        comboStaphilococos.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Menor a", " " }));
        comboStaphilococos.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 0, new java.awt.Color(0, 0, 0)));
        comboStaphilococos.setMaximumSize(new java.awt.Dimension(58, 30));
        comboStaphilococos.setMinimumSize(new java.awt.Dimension(58, 30));
        comboStaphilococos.setNextFocusableComponent(cajaEnterobacterias);
        comboStaphilococos.setPreferredSize(new java.awt.Dimension(58, 30));
        comboStaphilococos.setUI(new BasicComboBoxUI() {
            protected JButton createArrowButton() {
                return new JButton() {
                    public int getWidth() {
                        return 0;
                    }
                };
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jPanel6.add(comboStaphilococos, gridBagConstraints);

        cajaStaphilococos.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 0, new java.awt.Color(0, 0, 0)));
        cajaStaphilococos.setMargin(new java.awt.Insets(20, 20, 20, 20));
        cajaStaphilococos.setMaximumSize(new java.awt.Dimension(37, 45));
        cajaStaphilococos.setMinimumSize(new java.awt.Dimension(37, 45));
        cajaStaphilococos.setName(""); // NOI18N
        cajaStaphilococos.setNextFocusableComponent(comboEscherichia157);
        cajaStaphilococos.setPreferredSize(new java.awt.Dimension(37, 45));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        jPanel6.add(cajaStaphilococos, gridBagConstraints);

        comboStaphilococosIn.setBackground(new java.awt.Color(204, 204, 204));
        comboStaphilococosIn.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "UFC/g", "UFC/ml" }));
        comboStaphilococosIn.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        comboStaphilococosIn.setMaximumSize(new java.awt.Dimension(75, 30));
        comboStaphilococosIn.setMinimumSize(new java.awt.Dimension(75, 30));
        comboStaphilococosIn.setPreferredSize(new java.awt.Dimension(75, 30));
        comboStaphilococosIn.setUI(new BasicComboBoxUI() {
            protected JButton createArrowButton() {
                return new JButton() {
                    public int getWidth() {
                        return 0;
                    }
                };
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        jPanel6.add(comboStaphilococosIn, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        jPanel9.add(jPanel6, gridBagConstraints);

        jPanel25.setFocusable(false);
        jPanel25.setMaximumSize(new java.awt.Dimension(170, 30));
        jPanel25.setMinimumSize(new java.awt.Dimension(170, 30));
        jPanel25.setPreferredSize(new java.awt.Dimension(170, 30));
        jPanel25.setLayout(new java.awt.GridBagLayout());

        comboColiformesTotalesA30.setBackground(new java.awt.Color(204, 204, 204));
        comboColiformesTotalesA30.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Menor a", " " }));
        comboColiformesTotalesA30.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 0, new java.awt.Color(0, 0, 0)));
        comboColiformesTotalesA30.setMaximumSize(new java.awt.Dimension(58, 30));
        comboColiformesTotalesA30.setMinimumSize(new java.awt.Dimension(58, 30));
        comboColiformesTotalesA30.setNextFocusableComponent(cajaColiformesFecales);
        comboColiformesTotalesA30.setPreferredSize(new java.awt.Dimension(58, 30));
        comboColiformesTotalesA30.setUI(new BasicComboBoxUI() {
            protected JButton createArrowButton() {
                return new JButton() {
                    public int getWidth() {
                        return 0;
                    }
                };
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jPanel25.add(comboColiformesTotalesA30, gridBagConstraints);

        cajaColiformesTotalesA30.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 0, new java.awt.Color(0, 0, 0)));
        cajaColiformesTotalesA30.setMargin(new java.awt.Insets(20, 20, 20, 20));
        cajaColiformesTotalesA30.setMaximumSize(new java.awt.Dimension(37, 30));
        cajaColiformesTotalesA30.setMinimumSize(new java.awt.Dimension(37, 30));
        cajaColiformesTotalesA30.setName(""); // NOI18N
        cajaColiformesTotalesA30.setNextFocusableComponent(comboEscherichia);
        cajaColiformesTotalesA30.setPreferredSize(new java.awt.Dimension(37, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        jPanel25.add(cajaColiformesTotalesA30, gridBagConstraints);

        comboColiformesTotalesA30In.setBackground(new java.awt.Color(204, 204, 204));
        comboColiformesTotalesA30In.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "UFC/g", "UFC/ml" }));
        comboColiformesTotalesA30In.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        comboColiformesTotalesA30In.setMaximumSize(new java.awt.Dimension(75, 30));
        comboColiformesTotalesA30In.setMinimumSize(new java.awt.Dimension(75, 30));
        comboColiformesTotalesA30In.setPreferredSize(new java.awt.Dimension(75, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        jPanel25.add(comboColiformesTotalesA30In, gridBagConstraints);
        comboColiformesTotalesA30In.setUI(new BasicComboBoxUI() {
            protected JButton createArrowButton() {
                return new JButton() {
                    public int getWidth() {
                        return 0;
                    }
                };
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 16;
        jPanel9.add(jPanel25, gridBagConstraints);

        jPanel26.setFocusable(false);
        jPanel26.setMaximumSize(new java.awt.Dimension(170, 30));
        jPanel26.setLayout(new java.awt.GridBagLayout());

        comboColiformesTotalesProbables.setBackground(new java.awt.Color(204, 204, 204));
        comboColiformesTotalesProbables.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Menor a", " " }));
        comboColiformesTotalesProbables.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 0, new java.awt.Color(0, 0, 0)));
        comboColiformesTotalesProbables.setMaximumSize(new java.awt.Dimension(58, 30));
        comboColiformesTotalesProbables.setMinimumSize(new java.awt.Dimension(58, 30));
        comboColiformesTotalesProbables.setNextFocusableComponent(cajaColiformesFecales);
        comboColiformesTotalesProbables.setPreferredSize(new java.awt.Dimension(58, 30));
        comboColiformesTotalesProbables.setUI(new BasicComboBoxUI() {
            protected JButton createArrowButton() {
                return new JButton() {
                    public int getWidth() {
                        return 0;
                    }
                };
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jPanel26.add(comboColiformesTotalesProbables, gridBagConstraints);

        cajaColiformesTotalesProbables.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 0, new java.awt.Color(0, 0, 0)));
        cajaColiformesTotalesProbables.setMargin(new java.awt.Insets(20, 20, 20, 20));
        cajaColiformesTotalesProbables.setMaximumSize(new java.awt.Dimension(37, 30));
        cajaColiformesTotalesProbables.setMinimumSize(new java.awt.Dimension(37, 30));
        cajaColiformesTotalesProbables.setName(""); // NOI18N
        cajaColiformesTotalesProbables.setNextFocusableComponent(comboEscherichia);
        cajaColiformesTotalesProbables.setPreferredSize(new java.awt.Dimension(37, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        jPanel26.add(cajaColiformesTotalesProbables, gridBagConstraints);

        comboColiformesTotalesProbablesIn.setBackground(new java.awt.Color(204, 204, 204));
        comboColiformesTotalesProbablesIn.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "UFC/g", "UFC/ml" }));
        comboColiformesTotalesProbablesIn.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        comboColiformesTotalesProbablesIn.setMaximumSize(new java.awt.Dimension(75, 30));
        comboColiformesTotalesProbablesIn.setMinimumSize(new java.awt.Dimension(75, 30));
        comboColiformesTotalesProbablesIn.setPreferredSize(new java.awt.Dimension(75, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        jPanel26.add(comboColiformesTotalesProbablesIn, gridBagConstraints);
        comboColiformesTotalesProbablesIn.setUI(new BasicComboBoxUI() {
            protected JButton createArrowButton() {
                return new JButton() {
                    public int getWidth() {
                        return 0;
                    }
                };
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 17;
        jPanel9.add(jPanel26, gridBagConstraints);

        jPanel27.setFocusable(false);
        jPanel27.setMaximumSize(new java.awt.Dimension(170, 30));
        jPanel27.setLayout(new java.awt.GridBagLayout());

        cajaCaracteristicas.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        cajaCaracteristicas.setMargin(new java.awt.Insets(20, 20, 20, 20));
        cajaCaracteristicas.setMaximumSize(new java.awt.Dimension(170, 30));
        cajaCaracteristicas.setMinimumSize(new java.awt.Dimension(170, 30));
        cajaCaracteristicas.setName(""); // NOI18N
        cajaCaracteristicas.setNextFocusableComponent(comboEscherichia);
        cajaCaracteristicas.setPreferredSize(new java.awt.Dimension(170, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        jPanel27.add(cajaCaracteristicas, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 18;
        jPanel9.add(jPanel27, gridBagConstraints);

        jPanel19.setFocusable(false);
        jPanel19.setMaximumSize(new java.awt.Dimension(170, 30));
        jPanel19.setMinimumSize(new java.awt.Dimension(170, 30));
        jPanel19.setPreferredSize(new java.awt.Dimension(170, 30));
        jPanel19.setLayout(new java.awt.GridBagLayout());

        comboLactobacillus.setBackground(new java.awt.Color(204, 204, 204));
        comboLactobacillus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Menor a", " " }));
        comboLactobacillus.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 0, new java.awt.Color(0, 0, 0)));
        comboLactobacillus.setMaximumSize(new java.awt.Dimension(58, 30));
        comboLactobacillus.setMinimumSize(new java.awt.Dimension(58, 30));
        comboLactobacillus.setNextFocusableComponent(cajaColiformesFecales);
        comboLactobacillus.setPreferredSize(new java.awt.Dimension(58, 30));
        comboLactobacillus.setUI(new BasicComboBoxUI() {
            protected JButton createArrowButton() {
                return new JButton() {
                    public int getWidth() {
                        return 0;
                    }
                };
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jPanel19.add(comboLactobacillus, gridBagConstraints);

        cajaLactobacillus.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 0, new java.awt.Color(0, 0, 0)));
        cajaLactobacillus.setMargin(new java.awt.Insets(20, 20, 20, 20));
        cajaLactobacillus.setMaximumSize(new java.awt.Dimension(37, 30));
        cajaLactobacillus.setMinimumSize(new java.awt.Dimension(37, 30));
        cajaLactobacillus.setName(""); // NOI18N
        cajaLactobacillus.setNextFocusableComponent(comboEscherichia);
        cajaLactobacillus.setPreferredSize(new java.awt.Dimension(37, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        jPanel19.add(cajaLactobacillus, gridBagConstraints);

        comboLactobacillusIn.setBackground(new java.awt.Color(204, 204, 204));
        comboLactobacillusIn.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "UFC/g", "UFC/ml" }));
        comboLactobacillusIn.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        comboLactobacillusIn.setMaximumSize(new java.awt.Dimension(75, 30));
        comboLactobacillusIn.setMinimumSize(new java.awt.Dimension(75, 30));
        comboLactobacillusIn.setPreferredSize(new java.awt.Dimension(75, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        jPanel19.add(comboLactobacillusIn, gridBagConstraints);
        comboLactobacillusIn.setUI(new BasicComboBoxUI() {
            protected JButton createArrowButton() {
                return new JButton() {
                    public int getWidth() {
                        return 0;
                    }
                };
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 21;
        jPanel9.add(jPanel19, gridBagConstraints);

        jPanel20.setFocusable(false);
        jPanel20.setMaximumSize(new java.awt.Dimension(170, 30));
        jPanel20.setLayout(new java.awt.GridBagLayout());

        comboBacteriasLacticas.setBackground(new java.awt.Color(204, 204, 204));
        comboBacteriasLacticas.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Menor a", " " }));
        comboBacteriasLacticas.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 0, new java.awt.Color(0, 0, 0)));
        comboBacteriasLacticas.setMaximumSize(new java.awt.Dimension(58, 30));
        comboBacteriasLacticas.setMinimumSize(new java.awt.Dimension(58, 30));
        comboBacteriasLacticas.setNextFocusableComponent(cajaColiformesFecales);
        comboBacteriasLacticas.setPreferredSize(new java.awt.Dimension(58, 30));
        comboBacteriasLacticas.setUI(new BasicComboBoxUI() {
            protected JButton createArrowButton() {
                return new JButton() {
                    public int getWidth() {
                        return 0;
                    }
                };
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jPanel20.add(comboBacteriasLacticas, gridBagConstraints);

        cajaBacteriasLacticas.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 0, new java.awt.Color(0, 0, 0)));
        cajaBacteriasLacticas.setMargin(new java.awt.Insets(20, 20, 20, 20));
        cajaBacteriasLacticas.setMaximumSize(new java.awt.Dimension(37, 30));
        cajaBacteriasLacticas.setMinimumSize(new java.awt.Dimension(37, 30));
        cajaBacteriasLacticas.setName(""); // NOI18N
        cajaBacteriasLacticas.setNextFocusableComponent(comboEscherichia);
        cajaBacteriasLacticas.setPreferredSize(new java.awt.Dimension(37, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        jPanel20.add(cajaBacteriasLacticas, gridBagConstraints);

        comboBacteriasLacticasIn.setBackground(new java.awt.Color(204, 204, 204));
        comboBacteriasLacticasIn.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "UFC/g", "UFC/ml" }));
        comboBacteriasLacticasIn.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        comboBacteriasLacticasIn.setMaximumSize(new java.awt.Dimension(75, 30));
        comboBacteriasLacticasIn.setMinimumSize(new java.awt.Dimension(75, 30));
        comboBacteriasLacticasIn.setPreferredSize(new java.awt.Dimension(75, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        jPanel20.add(comboBacteriasLacticasIn, gridBagConstraints);
        comboBacteriasLacticasIn.setUI(new BasicComboBoxUI() {
            protected JButton createArrowButton() {
                return new JButton() {
                    public int getWidth() {
                        return 0;
                    }
                };
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 22;
        jPanel9.add(jPanel20, gridBagConstraints);

        jPanel29.setFocusable(false);
        jPanel29.setMaximumSize(new java.awt.Dimension(170, 30));
        jPanel29.setLayout(new java.awt.GridBagLayout());

        comboColiformesTotales45.setBackground(new java.awt.Color(204, 204, 204));
        comboColiformesTotales45.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Menor a", " " }));
        comboColiformesTotales45.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 0, new java.awt.Color(0, 0, 0)));
        comboColiformesTotales45.setMaximumSize(new java.awt.Dimension(58, 30));
        comboColiformesTotales45.setMinimumSize(new java.awt.Dimension(58, 30));
        comboColiformesTotales45.setNextFocusableComponent(cajaColiformesFecales);
        comboColiformesTotales45.setPreferredSize(new java.awt.Dimension(58, 30));
        comboColiformesTotales45.setUI(new BasicComboBoxUI() {
            protected JButton createArrowButton() {
                return new JButton() {
                    public int getWidth() {
                        return 0;
                    }
                };
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jPanel29.add(comboColiformesTotales45, gridBagConstraints);

        cajaColiformesTotales45.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 0, new java.awt.Color(0, 0, 0)));
        cajaColiformesTotales45.setMargin(new java.awt.Insets(20, 20, 20, 20));
        cajaColiformesTotales45.setMaximumSize(new java.awt.Dimension(37, 30));
        cajaColiformesTotales45.setMinimumSize(new java.awt.Dimension(37, 30));
        cajaColiformesTotales45.setName(""); // NOI18N
        cajaColiformesTotales45.setNextFocusableComponent(comboEscherichia);
        cajaColiformesTotales45.setPreferredSize(new java.awt.Dimension(37, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        jPanel29.add(cajaColiformesTotales45, gridBagConstraints);

        comboColiformesTotales45In.setBackground(new java.awt.Color(204, 204, 204));
        comboColiformesTotales45In.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "UFC/g", "UFC/ml" }));
        comboColiformesTotales45In.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        comboColiformesTotales45In.setMaximumSize(new java.awt.Dimension(75, 30));
        comboColiformesTotales45In.setMinimumSize(new java.awt.Dimension(75, 30));
        comboColiformesTotales45In.setPreferredSize(new java.awt.Dimension(75, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        jPanel29.add(comboColiformesTotales45In, gridBagConstraints);
        comboColiformesTotales45In.setUI(new BasicComboBoxUI() {
            protected JButton createArrowButton() {
                return new JButton() {
                    public int getWidth() {
                        return 0;
                    }
                };
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 24;
        jPanel9.add(jPanel29, gridBagConstraints);

        jPanel7.setFocusable(false);
        jPanel7.setMaximumSize(new java.awt.Dimension(170, 30));
        jPanel7.setMinimumSize(new java.awt.Dimension(170, 30));
        jPanel7.setPreferredSize(new java.awt.Dimension(170, 30));
        jPanel7.setLayout(new java.awt.GridBagLayout());

        comboMohos.setBackground(new java.awt.Color(204, 204, 204));
        comboMohos.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Menor a", " " }));
        comboMohos.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 0, new java.awt.Color(0, 0, 0)));
        comboMohos.setMaximumSize(new java.awt.Dimension(58, 30));
        comboMohos.setMinimumSize(new java.awt.Dimension(58, 30));
        comboMohos.setNextFocusableComponent(cajaEnterobacterias);
        comboMohos.setPreferredSize(new java.awt.Dimension(58, 30));
        comboMohos.setUI(new BasicComboBoxUI() {
            protected JButton createArrowButton() {
                return new JButton() {
                    public int getWidth() {
                        return 0;
                    }
                };
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jPanel7.add(comboMohos, gridBagConstraints);

        cajaMohos.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 0, new java.awt.Color(0, 0, 0)));
        cajaMohos.setMargin(new java.awt.Insets(20, 20, 20, 20));
        cajaMohos.setMaximumSize(new java.awt.Dimension(37, 45));
        cajaMohos.setMinimumSize(new java.awt.Dimension(37, 45));
        cajaMohos.setName(""); // NOI18N
        cajaMohos.setNextFocusableComponent(comboEscherichia157);
        cajaMohos.setPreferredSize(new java.awt.Dimension(37, 45));

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        jPanel7.add(cajaMohos, gridBagConstraints);

        comboMohosIn.setBackground(new java.awt.Color(204, 204, 204));
        comboMohosIn.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "UFC/g", "UFC/ml" }));
        comboMohosIn.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        comboMohosIn.setMaximumSize(new java.awt.Dimension(75, 30));
        comboMohosIn.setMinimumSize(new java.awt.Dimension(75, 30));
        comboMohosIn.setPreferredSize(new java.awt.Dimension(75, 30));
        comboMohosIn.setUI(new BasicComboBoxUI() {
            protected JButton createArrowButton() {
                return new JButton() {
                    public int getWidth() {
                        return 0;
                    }
                };
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        jPanel7.add(comboMohosIn, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        jPanel9.add(jPanel7, gridBagConstraints);

        jPanel30.setFocusable(false);
        jPanel30.setMaximumSize(new java.awt.Dimension(170, 30));
        jPanel30.setLayout(new java.awt.GridBagLayout());

        comboVibrio.setBackground(new java.awt.Color(204, 204, 204));
        comboVibrio.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Menor a", " " }));
        comboVibrio.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 0, new java.awt.Color(0, 0, 0)));
        comboVibrio.setMaximumSize(new java.awt.Dimension(58, 30));
        comboVibrio.setMinimumSize(new java.awt.Dimension(58, 30));
        comboVibrio.setNextFocusableComponent(cajaColiformesFecales);
        comboVibrio.setPreferredSize(new java.awt.Dimension(58, 30));
        comboVibrio.setUI(new BasicComboBoxUI() {
            protected JButton createArrowButton() {
                return new JButton() {
                    public int getWidth() {
                        return 0;
                    }
                };
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jPanel30.add(comboVibrio, gridBagConstraints);

        cajaVibrio.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 0, new java.awt.Color(0, 0, 0)));
        cajaVibrio.setMargin(new java.awt.Insets(20, 20, 20, 20));
        cajaVibrio.setMaximumSize(new java.awt.Dimension(37, 30));
        cajaVibrio.setMinimumSize(new java.awt.Dimension(37, 30));
        cajaVibrio.setName(""); // NOI18N
        cajaVibrio.setNextFocusableComponent(comboEscherichia);
        cajaVibrio.setPreferredSize(new java.awt.Dimension(37, 30));

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        jPanel30.add(cajaVibrio, gridBagConstraints);

        comboVibrioIn.setBackground(new java.awt.Color(204, 204, 204));
        comboVibrioIn.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "UFC/g", "UFC/ml" }));
        comboVibrioIn.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        comboVibrioIn.setMaximumSize(new java.awt.Dimension(75, 30));
        comboVibrioIn.setMinimumSize(new java.awt.Dimension(75, 30));
        comboVibrioIn.setPreferredSize(new java.awt.Dimension(75, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        jPanel30.add(comboVibrioIn, gridBagConstraints);
        comboVibrioIn.setUI(new BasicComboBoxUI() {
            protected JButton createArrowButton() {
                return new JButton() {
                    public int getWidth() {
                        return 0;
                    }
                };
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 25;
        jPanel9.add(jPanel30, gridBagConstraints);

        jPanel28.setFocusable(false);
        jPanel28.setMaximumSize(new java.awt.Dimension(170, 30));
        jPanel28.setLayout(new java.awt.GridBagLayout());

        comboShigella.setBackground(new java.awt.Color(204, 204, 204));
        comboShigella.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ausencia", "Presencia" }));
        comboShigella.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 1, new java.awt.Color(0, 0, 0)));
        comboShigella.setMaximumSize(new java.awt.Dimension(105, 30));
        comboShigella.setMinimumSize(new java.awt.Dimension(105, 30));
        comboShigella.setPreferredSize(new java.awt.Dimension(105, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jPanel28.add(comboShigella, gridBagConstraints);
        comboShigella.setUI(new BasicComboBoxUI() {
            protected JButton createArrowButton() {
                return new JButton() {
                    public int getWidth() {
                        return 0;
                    }
                };
            }
        });

        comboShigellaIn.setBackground(new java.awt.Color(204, 204, 204));
        comboShigellaIn.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "en 10 g", "en 25 g", "en 50 g" }));
        comboShigellaIn.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 1, new java.awt.Color(0, 0, 0)));
        comboShigellaIn.setMaximumSize(new java.awt.Dimension(65, 30));
        comboShigellaIn.setMinimumSize(new java.awt.Dimension(65, 30));
        comboShigellaIn.setPreferredSize(new java.awt.Dimension(65, 30));
        comboShigellaIn.setUI(new BasicComboBoxUI() {
            protected JButton createArrowButton() {
                return new JButton() {
                    public int getWidth() {
                        return 0;
                    }
                };
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        jPanel28.add(comboShigellaIn, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 27;
        jPanel9.add(jPanel28, gridBagConstraints);

        jPanel31.setFocusable(false);
        jPanel31.setMaximumSize(new java.awt.Dimension(170, 30));
        jPanel31.setMinimumSize(new java.awt.Dimension(170, 30));
        jPanel31.setPreferredSize(new java.awt.Dimension(170, 30));
        jPanel31.setLayout(new java.awt.GridBagLayout());

        comboListeria.setBackground(new java.awt.Color(204, 204, 204));
        comboListeria.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Menor a", "", "Ausencia", "Presencia" }));
        comboListeria.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 0, new java.awt.Color(0, 0, 0)));
        comboListeria.setMaximumSize(new java.awt.Dimension(58, 30));
        comboListeria.setMinimumSize(new java.awt.Dimension(58, 30));
        comboListeria.setNextFocusableComponent(cajaColiformesFecales);
        comboListeria.setPreferredSize(new java.awt.Dimension(58, 30));
        comboListeria.setUI(new BasicComboBoxUI() {
            protected JButton createArrowButton() {
                return new JButton() {
                    public int getWidth() {
                        return 0;
                    }
                };
            }
        });
        comboListeria.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comboListeriaItemStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        jPanel31.add(comboListeria, gridBagConstraints);

        cajaListeria.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 0, new java.awt.Color(0, 0, 0)));
        cajaListeria.setMargin(new java.awt.Insets(20, 20, 20, 20));
        cajaListeria.setMaximumSize(new java.awt.Dimension(37, 45));
        cajaListeria.setMinimumSize(new java.awt.Dimension(37, 45));
        cajaListeria.setName(""); // NOI18N
        cajaListeria.setNextFocusableComponent(comboEscherichia);
        cajaListeria.setPreferredSize(new java.awt.Dimension(37, 45));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        jPanel31.add(cajaListeria, gridBagConstraints);

        comboListeriaIn.setBackground(new java.awt.Color(204, 204, 204));
        comboListeriaIn.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "UFC/g", "UFC/ml", "en 10 g", "en 25 g", "en 65 g", "en 10 ml", "en 25 ml", "en 65 ml" }));
        comboListeriaIn.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        comboListeriaIn.setMaximumSize(new java.awt.Dimension(75, 30));
        comboListeriaIn.setMinimumSize(new java.awt.Dimension(75, 30));
        comboListeriaIn.setPreferredSize(new java.awt.Dimension(75, 30));
        comboListeriaIn.setUI(new BasicComboBoxUI() {
            protected JButton createArrowButton() {
                return new JButton() {
                    public int getWidth() {
                        return 0;
                    }
                };
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 0.3;
        jPanel31.add(comboListeriaIn, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 11;
        jPanel9.add(jPanel31, gridBagConstraints);

        jPanel32.setFocusable(false);
        jPanel32.setMaximumSize(new java.awt.Dimension(170, 30));
        jPanel32.setLayout(new java.awt.GridBagLayout());

        comboVibrioCholerae.setBackground(new java.awt.Color(204, 204, 204));
        comboVibrioCholerae.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Menor a", " " }));
        comboVibrioCholerae.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        comboVibrioCholerae.setMaximumSize(new java.awt.Dimension(58, 30));
        comboVibrioCholerae.setMinimumSize(new java.awt.Dimension(58, 30));
        comboVibrioCholerae.setNextFocusableComponent(cajaColiformesFecales);
        comboVibrioCholerae.setPreferredSize(new java.awt.Dimension(58, 30));
        comboVibrioCholerae
        .setUI(new BasicComboBoxUI() {
            protected JButton createArrowButton() {
                return new JButton() {
                    public int getWidth() {
                        return 0;
                    }
                };
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jPanel32.add(comboVibrioCholerae, gridBagConstraints);

        cajaVibrioCholerae.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        cajaVibrioCholerae.setMargin(new java.awt.Insets(20, 20, 20, 20));
        cajaVibrioCholerae.setMaximumSize(new java.awt.Dimension(37, 30));
        cajaVibrioCholerae.setMinimumSize(new java.awt.Dimension(37, 30));
        cajaVibrioCholerae.setName(""); // NOI18N
        cajaVibrioCholerae.setNextFocusableComponent(comboEscherichia);
        cajaVibrioCholerae.setPreferredSize(new java.awt.Dimension(37, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        jPanel32.add(cajaVibrioCholerae, gridBagConstraints);

        comboVibrioCholeraeIn.setBackground(new java.awt.Color(204, 204, 204));
        comboVibrioCholeraeIn.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "UFC/g", "UFC/ml" }));
        comboVibrioCholeraeIn.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 1, 1, new java.awt.Color(0, 0, 0)));
        comboVibrioCholeraeIn.setMaximumSize(new java.awt.Dimension(75, 30));
        comboVibrioCholeraeIn.setMinimumSize(new java.awt.Dimension(75, 30));
        comboVibrioCholeraeIn.setPreferredSize(new java.awt.Dimension(75, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        jPanel32.add(comboVibrioCholeraeIn, gridBagConstraints);
        comboVibrioCholeraeIn.setUI(new BasicComboBoxUI() {
            protected JButton createArrowButton() {
                return new JButton() {
                    public int getWidth() {
                        return 0;
                    }
                };
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 26;
        jPanel9.add(jPanel32, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridheight = 25;
        jPanel1.add(jPanel9, gridBagConstraints);

        checkConclusion.setText("Agregar conclusion");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 30;
        jPanel1.add(checkConclusion, gridBagConstraints);

        panelTotales.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 0, 1, new java.awt.Color(0, 0, 0)));
        panelTotales.setFocusable(false);
        panelTotales.setMaximumSize(new java.awt.Dimension(200, 30));
        panelTotales.setMinimumSize(new java.awt.Dimension(200, 30));
        panelTotales.setPreferredSize(new java.awt.Dimension(200, 30));
        panelTotales.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelTotalesMouseClicked(evt);
            }
        });
        panelTotales.setLayout(new java.awt.GridBagLayout());

        etiquetaTotales.setBackground(new java.awt.Color(0, 0, 0));
        etiquetaTotales.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        etiquetaTotales.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        etiquetaTotales.setText("COLIFORMES TOTALES");
        etiquetaTotales.setToolTipText("");
        etiquetaTotales.setFocusable(false);
        etiquetaTotales.setMaximumSize(new java.awt.Dimension(190, 30));
        etiquetaTotales.setMinimumSize(new java.awt.Dimension(190, 30));
        etiquetaTotales.setPreferredSize(new java.awt.Dimension(190, 30));
        etiquetaTotales.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                etiquetaTotalesMouseClicked(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        panelTotales.add(etiquetaTotales, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        jPanel1.add(panelTotales, gridBagConstraints);

        panelGermenes.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 0, 1, new java.awt.Color(0, 0, 0)));
        panelGermenes.setFocusable(false);
        panelGermenes.setMaximumSize(new java.awt.Dimension(200, 30));
        panelGermenes.setMinimumSize(new java.awt.Dimension(200, 30));
        panelGermenes.setPreferredSize(new java.awt.Dimension(200, 30));
        panelGermenes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelGermenesMouseClicked(evt);
            }
        });
        panelGermenes.setLayout(new java.awt.GridBagLayout());

        etiquetaGermenes.setBackground(new java.awt.Color(0, 0, 0));
        etiquetaGermenes.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        etiquetaGermenes.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        etiquetaGermenes.setText("<html>GERMENES AEROBIOS TOTALES</html>");
        etiquetaGermenes.setToolTipText("");
        etiquetaGermenes.setFocusable(false);
        etiquetaGermenes.setMaximumSize(new java.awt.Dimension(190, 30));
        etiquetaGermenes.setMinimumSize(new java.awt.Dimension(190, 30));
        etiquetaGermenes.setPreferredSize(new java.awt.Dimension(190, 30));
        etiquetaGermenes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                etiquetaGermenesMouseClicked(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        panelGermenes.add(etiquetaGermenes, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        jPanel1.add(panelGermenes, gridBagConstraints);

        checkColiformesFecales.setText("MENOR A 10");
        checkColiformesFecales.setNextFocusableComponent(botonGenerar);
        checkColiformesFecales.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkColiformesFecalesActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        jPanel1.add(checkColiformesFecales, gridBagConstraints);

        checkColiformesTotales.setText("MENOR A 10");
        checkColiformesTotales.setNextFocusableComponent(botonGenerar);
        checkColiformesTotales.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkColiformesTotalesActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        jPanel1.add(checkColiformesTotales, gridBagConstraints);

        checkGermenes.setText("MENOR A 10");
        checkGermenes.setNextFocusableComponent(botonGenerar);
        checkGermenes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkGermenesActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        jPanel1.add(checkGermenes, gridBagConstraints);

        panelEscherichia157.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 0, 1, new java.awt.Color(0, 0, 0)));
        panelEscherichia157.setFocusable(false);
        panelEscherichia157.setMaximumSize(new java.awt.Dimension(200, 30));
        panelEscherichia157.setMinimumSize(new java.awt.Dimension(200, 30));
        panelEscherichia157.setPreferredSize(new java.awt.Dimension(200, 30));
        panelEscherichia157.setLayout(new java.awt.GridBagLayout());

        etiquetaEscherichia157.setBackground(new java.awt.Color(0, 0, 0));
        etiquetaEscherichia157.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        etiquetaEscherichia157.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        etiquetaEscherichia157.setText("<html>ESCHERICHIA COLI NO O 157</html>");
        etiquetaEscherichia157.setToolTipText("");
        etiquetaEscherichia157.setFocusable(false);
        etiquetaEscherichia157.setMaximumSize(new java.awt.Dimension(190, 30));
        etiquetaEscherichia157.setMinimumSize(new java.awt.Dimension(190, 30));
        etiquetaEscherichia157.setPreferredSize(new java.awt.Dimension(190, 30));
        etiquetaEscherichia157.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                etiquetaEscherichia157MouseClicked(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        panelEscherichia157.add(etiquetaEscherichia157, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        jPanel1.add(panelEscherichia157, gridBagConstraints);

        panelEnterobacterias.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 0, 1, new java.awt.Color(0, 0, 0)));
        panelEnterobacterias.setFocusable(false);
        panelEnterobacterias.setMaximumSize(new java.awt.Dimension(200, 30));
        panelEnterobacterias.setMinimumSize(new java.awt.Dimension(200, 30));
        panelEnterobacterias.setPreferredSize(new java.awt.Dimension(200, 30));
        panelEnterobacterias.setLayout(new java.awt.GridBagLayout());

        etiquetaEnterobacterias.setBackground(new java.awt.Color(0, 0, 0));
        etiquetaEnterobacterias.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        etiquetaEnterobacterias.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        etiquetaEnterobacterias.setText("<html>ENTEROBACTERIAS</html>");
        etiquetaEnterobacterias.setToolTipText("");
        etiquetaEnterobacterias.setFocusable(false);
        etiquetaEnterobacterias.setMaximumSize(new java.awt.Dimension(190, 30));
        etiquetaEnterobacterias.setMinimumSize(new java.awt.Dimension(190, 30));
        etiquetaEnterobacterias.setPreferredSize(new java.awt.Dimension(190, 30));
        etiquetaEnterobacterias.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                etiquetaEnterobacteriasMouseClicked(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        panelEnterobacterias.add(etiquetaEnterobacterias, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        jPanel1.add(panelEnterobacterias, gridBagConstraints);

        panelListeria.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 0, 1, new java.awt.Color(0, 0, 0)));
        panelListeria.setFocusable(false);
        panelListeria.setMaximumSize(new java.awt.Dimension(200, 30));
        panelListeria.setMinimumSize(new java.awt.Dimension(200, 30));
        panelListeria.setPreferredSize(new java.awt.Dimension(200, 30));
        panelListeria.setLayout(new java.awt.GridBagLayout());

        etiquetaListeria.setBackground(new java.awt.Color(0, 0, 0));
        etiquetaListeria.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        etiquetaListeria.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        etiquetaListeria.setText("<html>LISTERIA MONOCYTOGENES</html>");
        etiquetaListeria.setToolTipText("");
        etiquetaListeria.setFocusable(false);
        etiquetaListeria.setMaximumSize(new java.awt.Dimension(190, 30));
        etiquetaListeria.setMinimumSize(new java.awt.Dimension(190, 30));
        etiquetaListeria.setPreferredSize(new java.awt.Dimension(190, 30));
        etiquetaListeria.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                etiquetaListeriaMouseClicked(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        panelListeria.add(etiquetaListeria, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        jPanel1.add(panelListeria, gridBagConstraints);

        panelBacillus.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 0, 1, new java.awt.Color(0, 0, 0)));
        panelBacillus.setFocusable(false);
        panelBacillus.setMaximumSize(new java.awt.Dimension(200, 30));
        panelBacillus.setMinimumSize(new java.awt.Dimension(200, 30));
        panelBacillus.setPreferredSize(new java.awt.Dimension(200, 30));
        panelBacillus.setLayout(new java.awt.GridBagLayout());

        etiquetaBacillus.setBackground(new java.awt.Color(0, 0, 0));
        etiquetaBacillus.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        etiquetaBacillus.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        etiquetaBacillus.setText("<html>BACILLUS CEREUS</html>");
        etiquetaBacillus.setToolTipText("");
        etiquetaBacillus.setFocusable(false);
        etiquetaBacillus.setMaximumSize(new java.awt.Dimension(190, 30));
        etiquetaBacillus.setMinimumSize(new java.awt.Dimension(190, 30));
        etiquetaBacillus.setPreferredSize(new java.awt.Dimension(190, 30));
        etiquetaBacillus.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                etiquetaBacillusMouseClicked(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        panelBacillus.add(etiquetaBacillus, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        jPanel1.add(panelBacillus, gridBagConstraints);

        panelPerfringens.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 0, 1, new java.awt.Color(0, 0, 0)));
        panelPerfringens.setFocusable(false);
        panelPerfringens.setMaximumSize(new java.awt.Dimension(200, 30));
        panelPerfringens.setMinimumSize(new java.awt.Dimension(200, 30));
        panelPerfringens.setPreferredSize(new java.awt.Dimension(200, 30));
        panelPerfringens.setLayout(new java.awt.GridBagLayout());

        etiquetaPerfringens.setBackground(new java.awt.Color(0, 0, 0));
        etiquetaPerfringens.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        etiquetaPerfringens.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        etiquetaPerfringens.setText("<html>CLOSTRIDIUM PERFRINGENS</html>");
        etiquetaPerfringens.setToolTipText("");
        etiquetaPerfringens.setFocusable(false);
        etiquetaPerfringens.setMaximumSize(new java.awt.Dimension(190, 30));
        etiquetaPerfringens.setMinimumSize(new java.awt.Dimension(190, 30));
        etiquetaPerfringens.setPreferredSize(new java.awt.Dimension(190, 30));
        etiquetaPerfringens.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                etiquetaPerfringensMouseClicked(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        panelPerfringens.add(etiquetaPerfringens, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 15;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        jPanel1.add(panelPerfringens, gridBagConstraints);

        panelSulfito.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 0, 1, new java.awt.Color(0, 0, 0)));
        panelSulfito.setFocusable(false);
        panelSulfito.setMaximumSize(new java.awt.Dimension(200, 30));
        panelSulfito.setMinimumSize(new java.awt.Dimension(200, 30));
        panelSulfito.setPreferredSize(new java.awt.Dimension(200, 30));
        panelSulfito.setLayout(new java.awt.GridBagLayout());

        etiquetaSulfito.setBackground(new java.awt.Color(0, 0, 0));
        etiquetaSulfito.setFont(new java.awt.Font("Segoe UI", 1, 8)); // NOI18N
        etiquetaSulfito.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        etiquetaSulfito.setText("<html>CLOSTRIDIUM SULFITO REDUCTORES Ó ANAEROBIOS</html>");
        etiquetaSulfito.setToolTipText("");
        etiquetaSulfito.setFocusable(false);
        etiquetaSulfito.setMaximumSize(new java.awt.Dimension(190, 30));
        etiquetaSulfito.setMinimumSize(new java.awt.Dimension(190, 30));
        etiquetaSulfito.setPreferredSize(new java.awt.Dimension(190, 30));
        etiquetaSulfito.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                etiquetaSulfitoMouseClicked(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        panelSulfito.add(etiquetaSulfito, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 16;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        jPanel1.add(panelSulfito, gridBagConstraints);

        panelCampilobacter.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 0, 1, new java.awt.Color(0, 0, 0)));
        panelCampilobacter.setFocusable(false);
        panelCampilobacter.setMaximumSize(new java.awt.Dimension(200, 30));
        panelCampilobacter.setMinimumSize(new java.awt.Dimension(200, 30));
        panelCampilobacter.setPreferredSize(new java.awt.Dimension(200, 30));
        panelCampilobacter.setLayout(new java.awt.GridBagLayout());

        etiquetaCampilobacter.setBackground(new java.awt.Color(0, 0, 0));
        etiquetaCampilobacter.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        etiquetaCampilobacter.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        etiquetaCampilobacter.setText("<html>CAMPILOBACTER</html>");
        etiquetaCampilobacter.setToolTipText("");
        etiquetaCampilobacter.setFocusable(false);
        etiquetaCampilobacter.setMaximumSize(new java.awt.Dimension(190, 30));
        etiquetaCampilobacter.setMinimumSize(new java.awt.Dimension(190, 30));
        etiquetaCampilobacter.setPreferredSize(new java.awt.Dimension(190, 30));
        etiquetaCampilobacter.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                etiquetaCampilobacterMouseClicked(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        panelCampilobacter.add(etiquetaCampilobacter, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 17;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        jPanel1.add(panelCampilobacter, gridBagConstraints);

        panelEscherichia.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 0, 1, new java.awt.Color(0, 0, 0)));
        panelEscherichia.setFocusable(false);
        panelEscherichia.setMaximumSize(new java.awt.Dimension(200, 30));
        panelEscherichia.setMinimumSize(new java.awt.Dimension(200, 30));
        panelEscherichia.setPreferredSize(new java.awt.Dimension(200, 30));
        panelEscherichia.setLayout(new java.awt.GridBagLayout());

        etiquetaEscherichia.setBackground(new java.awt.Color(0, 0, 0));
        etiquetaEscherichia.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        etiquetaEscherichia.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        etiquetaEscherichia.setText("<html>ESCHERICHIA COLI</html>");
        etiquetaEscherichia.setToolTipText("");
        etiquetaEscherichia.setFocusable(false);
        etiquetaEscherichia.setMaximumSize(new java.awt.Dimension(190, 30));
        etiquetaEscherichia.setMinimumSize(new java.awt.Dimension(190, 30));
        etiquetaEscherichia.setPreferredSize(new java.awt.Dimension(190, 30));
        etiquetaEscherichia.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                etiquetaEscherichiaMouseClicked(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        panelEscherichia.add(etiquetaEscherichia, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        jPanel1.add(panelEscherichia, gridBagConstraints);

        metodoTotales.setFont(new java.awt.Font("Dialog", 0, 9)); // NOI18N
        metodoTotales.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ISO 4832:2006" }));
        metodoTotales.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        metodoTotales.setMaximumSize(new java.awt.Dimension(200, 30));
        metodoTotales.setMinimumSize(new java.awt.Dimension(200, 30));
        metodoTotales.setPreferredSize(new java.awt.Dimension(200, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        jPanel1.add(metodoTotales, gridBagConstraints);

        metodoFecales.setFont(new java.awt.Font("Dialog", 0, 9)); // NOI18N
        metodoFecales.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SMEWW 22nd. Edition. APHA (2012) 9221 e" }));
        metodoFecales.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        metodoFecales.setMaximumSize(new java.awt.Dimension(200, 30));
        metodoFecales.setMinimumSize(new java.awt.Dimension(200, 30));
        metodoFecales.setPreferredSize(new java.awt.Dimension(200, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        jPanel1.add(metodoFecales, gridBagConstraints);

        metodoEscherichia.setFont(new java.awt.Font("Dialog", 0, 9)); // NOI18N
        metodoEscherichia.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SMEWW 22nd. Edition. APHA (2012) 9221 F" }));
        metodoEscherichia.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        metodoEscherichia.setMaximumSize(new java.awt.Dimension(200, 30));
        metodoEscherichia.setMinimumSize(new java.awt.Dimension(200, 30));
        metodoEscherichia.setPreferredSize(new java.awt.Dimension(200, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        jPanel1.add(metodoEscherichia, gridBagConstraints);

        metodoEscherichiaH7.setFont(new java.awt.Font("Dialog", 0, 9)); // NOI18N
        metodoEscherichiaH7.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "USDA-FSIS:2010", "ISO 16654:2001", "BAM-FDA:2011" }));
        metodoEscherichiaH7.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        metodoEscherichiaH7.setMaximumSize(new java.awt.Dimension(200, 30));
        metodoEscherichiaH7.setMinimumSize(new java.awt.Dimension(200, 30));
        metodoEscherichiaH7.setPreferredSize(new java.awt.Dimension(200, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        jPanel1.add(metodoEscherichiaH7, gridBagConstraints);

        metodoEscherichia157.setFont(new java.awt.Font("Dialog", 0, 9)); // NOI18N
        metodoEscherichia157.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ISO 13136: 2012", "BAM-FDA:2014" }));
        metodoEscherichia157.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        metodoEscherichia157.setMaximumSize(new java.awt.Dimension(200, 30));
        metodoEscherichia157.setMinimumSize(new java.awt.Dimension(200, 30));
        metodoEscherichia157.setPreferredSize(new java.awt.Dimension(200, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        jPanel1.add(metodoEscherichia157, gridBagConstraints);

        metodoStaphilococos.setFont(new java.awt.Font("Dialog", 0, 9)); // NOI18N
        metodoStaphilococos.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ISO 6888-1:1999 / Adm 1:2003" }));
        metodoStaphilococos.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        metodoStaphilococos.setMaximumSize(new java.awt.Dimension(200, 30));
        metodoStaphilococos.setMinimumSize(new java.awt.Dimension(200, 30));
        metodoStaphilococos.setPreferredSize(new java.awt.Dimension(200, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        jPanel1.add(metodoStaphilococos, gridBagConstraints);

        metodoEnterobacterias.setFont(new java.awt.Font("Dialog", 0, 9)); // NOI18N
        metodoEnterobacterias.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ISO 21528-2:2004", "ICMSF" }));
        metodoEnterobacterias.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        metodoEnterobacterias.setMaximumSize(new java.awt.Dimension(200, 30));
        metodoEnterobacterias.setMinimumSize(new java.awt.Dimension(200, 30));
        metodoEnterobacterias.setPreferredSize(new java.awt.Dimension(200, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        jPanel1.add(metodoEnterobacterias, gridBagConstraints);

        metodoMohos.setFont(new java.awt.Font("Dialog", 0, 9)); // NOI18N
        metodoMohos.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ISO 21527-2:2008" }));
        metodoMohos.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        metodoMohos.setMaximumSize(new java.awt.Dimension(200, 30));
        metodoMohos.setMinimumSize(new java.awt.Dimension(200, 30));
        metodoMohos.setPreferredSize(new java.awt.Dimension(200, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        jPanel1.add(metodoMohos, gridBagConstraints);

        metodoSalmonella.setFont(new java.awt.Font("Dialog", 0, 9)); // NOI18N
        metodoSalmonella.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ISO 6579:2002", "co:2004", "BAM-FDA:2011", "USDA-FSIS:2011" }));
        metodoSalmonella.setToolTipText("");
        metodoSalmonella.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        metodoSalmonella.setMaximumSize(new java.awt.Dimension(200, 30));
        metodoSalmonella.setMinimumSize(new java.awt.Dimension(200, 30));
        metodoSalmonella.setPreferredSize(new java.awt.Dimension(200, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        jPanel1.add(metodoSalmonella, gridBagConstraints);

        metodoListeria.setFont(new java.awt.Font("Dialog", 0, 9)); // NOI18N
        metodoListeria.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Cultivo en medio selectivo Oxford, preenriquecimiento en UVM" }));
        metodoListeria.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        metodoListeria.setMaximumSize(new java.awt.Dimension(200, 30));
        metodoListeria.setMinimumSize(new java.awt.Dimension(200, 30));
        metodoListeria.setPreferredSize(new java.awt.Dimension(200, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        jPanel1.add(metodoListeria, gridBagConstraints);

        metodoBacillus.setFont(new java.awt.Font("Dialog", 0, 9)); // NOI18N
        metodoBacillus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ISO 7932:2004" }));
        metodoBacillus.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        metodoBacillus.setMaximumSize(new java.awt.Dimension(200, 30));
        metodoBacillus.setMinimumSize(new java.awt.Dimension(200, 30));
        metodoBacillus.setPreferredSize(new java.awt.Dimension(200, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        jPanel1.add(metodoBacillus, gridBagConstraints);

        metodoPerfringens.setFont(new java.awt.Font("Dialog", 0, 9)); // NOI18N
        metodoPerfringens.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ISO 7937:2004" }));
        metodoPerfringens.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        metodoPerfringens.setMaximumSize(new java.awt.Dimension(200, 30));
        metodoPerfringens.setMinimumSize(new java.awt.Dimension(200, 30));
        metodoPerfringens.setPreferredSize(new java.awt.Dimension(200, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 15;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        jPanel1.add(metodoPerfringens, gridBagConstraints);

        metodoSulfito.setFont(new java.awt.Font("Dialog", 0, 9)); // NOI18N
        metodoSulfito.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ISO 15213:2003" }));
        metodoSulfito.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        metodoSulfito.setMaximumSize(new java.awt.Dimension(200, 30));
        metodoSulfito.setMinimumSize(new java.awt.Dimension(200, 30));
        metodoSulfito.setPreferredSize(new java.awt.Dimension(200, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 16;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        jPanel1.add(metodoSulfito, gridBagConstraints);

        metodoCampilobacter.setFont(new java.awt.Font("Dialog", 0, 9)); // NOI18N
        metodoCampilobacter.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ISO 10272-2:2017" }));
        metodoCampilobacter.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        metodoCampilobacter.setMaximumSize(new java.awt.Dimension(200, 30));
        metodoCampilobacter.setMinimumSize(new java.awt.Dimension(200, 30));
        metodoCampilobacter.setPreferredSize(new java.awt.Dimension(200, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 17;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        jPanel1.add(metodoCampilobacter, gridBagConstraints);

        metodoGermenes.setFont(new java.awt.Font("Dialog", 0, 9)); // NOI18N
        metodoGermenes.setMaximumRowCount(2);
        metodoGermenes.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SMEWW 22nd. Edition. APHA (2012) 9215 B" }));
        metodoGermenes.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        metodoGermenes.setMaximumSize(new java.awt.Dimension(200, 30));
        metodoGermenes.setMinimumSize(new java.awt.Dimension(200, 30));
        metodoGermenes.setPreferredSize(new java.awt.Dimension(200, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        jPanel1.add(metodoGermenes, gridBagConstraints);
        metodoGermenes.setUI(new BasicComboBoxUI() {
            protected JButton createArrowButton() {
                return new JButton() {
                    public int getWidth() {
                        return 0;
                    }
                };
            }
        });

        checkStaphilococos.setText("MENOR A 10");
        checkStaphilococos.setNextFocusableComponent(botonGenerar);
        checkStaphilococos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkStaphilococosActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        jPanel1.add(checkStaphilococos, gridBagConstraints);

        checkMohos.setText("MENOR A 10");
        checkMohos.setNextFocusableComponent(botonGenerar);
        checkMohos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkMohosActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 11;
        jPanel1.add(checkMohos, gridBagConstraints);

        checkBacillus.setText("MENOR A 10");
        checkBacillus.setNextFocusableComponent(botonGenerar);
        checkBacillus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkBacillusActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 14;
        jPanel1.add(checkBacillus, gridBagConstraints);

        checkPerfringens.setText("MENOR A 10");
        checkPerfringens.setNextFocusableComponent(botonGenerar);
        checkPerfringens.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkPerfringensActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 15;
        jPanel1.add(checkPerfringens, gridBagConstraints);

        checkSulfito.setText("MENOR A 10");
        checkSulfito.setNextFocusableComponent(botonGenerar);
        checkSulfito.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkSulfitoActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 16;
        jPanel1.add(checkSulfito, gridBagConstraints);

        checkCampilobacter.setText("MENOR A 10");
        checkCampilobacter.setNextFocusableComponent(botonGenerar);
        checkCampilobacter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkCampilobacterActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 17;
        jPanel1.add(checkCampilobacter, gridBagConstraints);

        panelCaracteristicas.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 0, 1, new java.awt.Color(0, 0, 0)));
        panelCaracteristicas.setFocusable(false);
        panelCaracteristicas.setMaximumSize(new java.awt.Dimension(200, 30));
        panelCaracteristicas.setMinimumSize(new java.awt.Dimension(200, 30));
        panelCaracteristicas.setPreferredSize(new java.awt.Dimension(200, 30));
        panelCaracteristicas.setLayout(new java.awt.GridBagLayout());

        etiquetaCaracteristicas.setBackground(new java.awt.Color(0, 0, 0));
        etiquetaCaracteristicas.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        etiquetaCaracteristicas.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        etiquetaCaracteristicas.setText("<html>CARACTERISTICAS ORGANOLEPTICAS</html>");
        etiquetaCaracteristicas.setToolTipText("");
        etiquetaCaracteristicas.setFocusable(false);
        etiquetaCaracteristicas.setMaximumSize(new java.awt.Dimension(190, 30));
        etiquetaCaracteristicas.setMinimumSize(new java.awt.Dimension(190, 30));
        etiquetaCaracteristicas.setPreferredSize(new java.awt.Dimension(190, 30));
        etiquetaCaracteristicas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                etiquetaCaracteristicasMouseClicked(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        panelCaracteristicas.add(etiquetaCaracteristicas, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 20;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        jPanel1.add(panelCaracteristicas, gridBagConstraints);

        metodoCaracteristicas.setFont(new java.awt.Font("Dialog", 0, 9)); // NOI18N
        metodoCaracteristicas.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-" }));
        metodoCaracteristicas.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        metodoCaracteristicas.setMaximumSize(new java.awt.Dimension(200, 30));
        metodoCaracteristicas.setMinimumSize(new java.awt.Dimension(200, 30));
        metodoCaracteristicas.setPreferredSize(new java.awt.Dimension(200, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 20;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        jPanel1.add(metodoCaracteristicas, gridBagConstraints);

        panelColiformesTotalesA30.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 0, 1, new java.awt.Color(0, 0, 0)));
        panelColiformesTotalesA30.setFocusable(false);
        panelColiformesTotalesA30.setMaximumSize(new java.awt.Dimension(200, 30));
        panelColiformesTotalesA30.setMinimumSize(new java.awt.Dimension(200, 30));
        panelColiformesTotalesA30.setPreferredSize(new java.awt.Dimension(200, 30));
        panelColiformesTotalesA30.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelColiformesTotalesA30MouseClicked(evt);
            }
        });
        panelColiformesTotalesA30.setLayout(new java.awt.GridBagLayout());

        etiquetaColiformesTotalesA30.setBackground(new java.awt.Color(0, 0, 0));
        etiquetaColiformesTotalesA30.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        etiquetaColiformesTotalesA30.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        etiquetaColiformesTotalesA30.setText("<html>COLIFORMES TOTALES A 30°C</html>");
        etiquetaColiformesTotalesA30.setToolTipText("");
        etiquetaColiformesTotalesA30.setFocusable(false);
        etiquetaColiformesTotalesA30.setMaximumSize(new java.awt.Dimension(190, 30));
        etiquetaColiformesTotalesA30.setMinimumSize(new java.awt.Dimension(190, 30));
        etiquetaColiformesTotalesA30.setPreferredSize(new java.awt.Dimension(190, 30));
        etiquetaColiformesTotalesA30.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                etiquetaColiformesTotalesA30MouseClicked(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        panelColiformesTotalesA30.add(etiquetaColiformesTotalesA30, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 18;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        jPanel1.add(panelColiformesTotalesA30, gridBagConstraints);

        metodoColiformesTotalesA30.setFont(new java.awt.Font("Dialog", 0, 9)); // NOI18N
        metodoColiformesTotalesA30.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Siembra en placa" }));
        metodoColiformesTotalesA30.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        metodoColiformesTotalesA30.setMaximumSize(new java.awt.Dimension(200, 30));
        metodoColiformesTotalesA30.setMinimumSize(new java.awt.Dimension(200, 30));
        metodoColiformesTotalesA30.setPreferredSize(new java.awt.Dimension(200, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 18;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        jPanel1.add(metodoColiformesTotalesA30, gridBagConstraints);

        panelColiformesTotalesProbables.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 0, 1, new java.awt.Color(0, 0, 0)));
        panelColiformesTotalesProbables.setFocusable(false);
        panelColiformesTotalesProbables.setMaximumSize(new java.awt.Dimension(200, 30));
        panelColiformesTotalesProbables.setMinimumSize(new java.awt.Dimension(200, 30));
        panelColiformesTotalesProbables.setPreferredSize(new java.awt.Dimension(200, 30));
        panelColiformesTotalesProbables.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelColiformesTotalesProbablesMouseClicked(evt);
            }
        });
        panelColiformesTotalesProbables.setLayout(new java.awt.GridBagLayout());

        etiquetaColiformesTotalesProbable.setBackground(new java.awt.Color(0, 0, 0));
        etiquetaColiformesTotalesProbable.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        etiquetaColiformesTotalesProbable.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        etiquetaColiformesTotalesProbable.setText("<html>COLIFORMES TOTALES POR NÚMERO MÁS PROBABLE</html>");
        etiquetaColiformesTotalesProbable.setToolTipText("");
        etiquetaColiformesTotalesProbable.setFocusable(false);
        etiquetaColiformesTotalesProbable.setMaximumSize(new java.awt.Dimension(190, 30));
        etiquetaColiformesTotalesProbable.setMinimumSize(new java.awt.Dimension(190, 30));
        etiquetaColiformesTotalesProbable.setPreferredSize(new java.awt.Dimension(190, 30));
        etiquetaColiformesTotalesProbable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                etiquetaColiformesTotalesProbableMouseClicked(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        panelColiformesTotalesProbables.add(etiquetaColiformesTotalesProbable, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 19;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        jPanel1.add(panelColiformesTotalesProbables, gridBagConstraints);

        metodoColiformesTotalesProbables.setFont(new java.awt.Font("Dialog", 0, 9)); // NOI18N
        metodoColiformesTotalesProbables.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Siembra en tubos con diluciones sucesivas" }));
        metodoColiformesTotalesProbables.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        metodoColiformesTotalesProbables.setMaximumSize(new java.awt.Dimension(200, 30));
        metodoColiformesTotalesProbables.setMinimumSize(new java.awt.Dimension(200, 30));
        metodoColiformesTotalesProbables.setPreferredSize(new java.awt.Dimension(200, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 19;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        jPanel1.add(metodoColiformesTotalesProbables, gridBagConstraints);

        panelLactobacillus.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 0, 1, new java.awt.Color(0, 0, 0)));
        panelLactobacillus.setFocusable(false);
        panelLactobacillus.setMaximumSize(new java.awt.Dimension(200, 30));
        panelLactobacillus.setMinimumSize(new java.awt.Dimension(200, 30));
        panelLactobacillus.setPreferredSize(new java.awt.Dimension(200, 30));
        panelLactobacillus.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelLactobacillusMouseClicked(evt);
            }
        });
        panelLactobacillus.setLayout(new java.awt.GridBagLayout());

        etiquetaLactobacillus.setBackground(new java.awt.Color(0, 0, 0));
        etiquetaLactobacillus.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        etiquetaLactobacillus.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        etiquetaLactobacillus.setText("<html>RECUENTO DE LACTOBACILLUS</html>");
        etiquetaLactobacillus.setToolTipText("");
        etiquetaLactobacillus.setFocusable(false);
        etiquetaLactobacillus.setMaximumSize(new java.awt.Dimension(190, 30));
        etiquetaLactobacillus.setMinimumSize(new java.awt.Dimension(190, 30));
        etiquetaLactobacillus.setPreferredSize(new java.awt.Dimension(190, 30));
        etiquetaLactobacillus.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                etiquetaLactobacillusMouseClicked(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        panelLactobacillus.add(etiquetaLactobacillus, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 21;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        jPanel1.add(panelLactobacillus, gridBagConstraints);

        metodoLactobacillus.setFont(new java.awt.Font("Dialog", 0, 9)); // NOI18N
        metodoLactobacillus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ISO 9232-2003" }));
        metodoLactobacillus.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        metodoLactobacillus.setMaximumSize(new java.awt.Dimension(200, 30));
        metodoLactobacillus.setMinimumSize(new java.awt.Dimension(200, 30));
        metodoLactobacillus.setPreferredSize(new java.awt.Dimension(200, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 21;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        jPanel1.add(metodoLactobacillus, gridBagConstraints);

        metodoBacteriasLacticas.setFont(new java.awt.Font("Dialog", 0, 9)); // NOI18N
        metodoBacteriasLacticas.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Fill 117A 1988" }));
        metodoBacteriasLacticas.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        metodoBacteriasLacticas.setMaximumSize(new java.awt.Dimension(200, 30));
        metodoBacteriasLacticas.setMinimumSize(new java.awt.Dimension(200, 30));
        metodoBacteriasLacticas.setPreferredSize(new java.awt.Dimension(200, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 22;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        jPanel1.add(metodoBacteriasLacticas, gridBagConstraints);

        panelBacteriasLacticas.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 0, 1, new java.awt.Color(0, 0, 0)));
        panelBacteriasLacticas.setFocusable(false);
        panelBacteriasLacticas.setMaximumSize(new java.awt.Dimension(200, 30));
        panelBacteriasLacticas.setMinimumSize(new java.awt.Dimension(200, 30));
        panelBacteriasLacticas.setPreferredSize(new java.awt.Dimension(200, 30));
        panelBacteriasLacticas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelBacteriasLacticasMouseClicked(evt);
            }
        });
        panelBacteriasLacticas.setLayout(new java.awt.GridBagLayout());

        etiquetaBacteriasLacticas.setBackground(new java.awt.Color(0, 0, 0));
        etiquetaBacteriasLacticas.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        etiquetaBacteriasLacticas.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        etiquetaBacteriasLacticas.setText("<html>RECUENTO DE BACTERIAS LÁCTICAS</html>");
        etiquetaBacteriasLacticas.setToolTipText("");
        etiquetaBacteriasLacticas.setFocusable(false);
        etiquetaBacteriasLacticas.setMaximumSize(new java.awt.Dimension(190, 30));
        etiquetaBacteriasLacticas.setMinimumSize(new java.awt.Dimension(190, 30));
        etiquetaBacteriasLacticas.setPreferredSize(new java.awt.Dimension(190, 30));
        etiquetaBacteriasLacticas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                etiquetaBacteriasLacticasMouseClicked(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        panelBacteriasLacticas.add(etiquetaBacteriasLacticas, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 22;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        jPanel1.add(panelBacteriasLacticas, gridBagConstraints);

        panelColiformesTotales45.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 0, 1, new java.awt.Color(0, 0, 0)));
        panelColiformesTotales45.setFocusable(false);
        panelColiformesTotales45.setMaximumSize(new java.awt.Dimension(200, 30));
        panelColiformesTotales45.setMinimumSize(new java.awt.Dimension(200, 30));
        panelColiformesTotales45.setPreferredSize(new java.awt.Dimension(200, 30));
        panelColiformesTotales45.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelColiformesTotales45MouseClicked(evt);
            }
        });
        panelColiformesTotales45.setLayout(new java.awt.GridBagLayout());

        etiquetaColiformesTotales45.setBackground(new java.awt.Color(0, 0, 0));
        etiquetaColiformesTotales45.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        etiquetaColiformesTotales45.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        etiquetaColiformesTotales45.setText("<html>RECUENTO DE COLIFORMES TOTALES A 45°C</html>");
        etiquetaColiformesTotales45.setToolTipText("");
        etiquetaColiformesTotales45.setFocusable(false);
        etiquetaColiformesTotales45.setMaximumSize(new java.awt.Dimension(190, 30));
        etiquetaColiformesTotales45.setMinimumSize(new java.awt.Dimension(190, 30));
        etiquetaColiformesTotales45.setPreferredSize(new java.awt.Dimension(190, 30));
        etiquetaColiformesTotales45.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                etiquetaColiformesTotales45MouseClicked(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        panelColiformesTotales45.add(etiquetaColiformesTotales45, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 23;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        jPanel1.add(panelColiformesTotales45, gridBagConstraints);

        metodoColiformesTotales45.setFont(new java.awt.Font("Dialog", 0, 9)); // NOI18N
        metodoColiformesTotales45.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "FIL 73A : 1985" }));
        metodoColiformesTotales45.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        metodoColiformesTotales45.setMaximumSize(new java.awt.Dimension(200, 30));
        metodoColiformesTotales45.setMinimumSize(new java.awt.Dimension(200, 30));
        metodoColiformesTotales45.setPreferredSize(new java.awt.Dimension(200, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 23;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        jPanel1.add(metodoColiformesTotales45, gridBagConstraints);

        checkColiformesTotalesA30.setText("MENOR A 10");
        checkColiformesTotalesA30.setNextFocusableComponent(botonGenerar);
        checkColiformesTotalesA30.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkColiformesTotalesA30ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 18;
        jPanel1.add(checkColiformesTotalesA30, gridBagConstraints);

        checkColiformesTotalesProbables.setText("MENOR A 10");
        checkColiformesTotalesProbables.setNextFocusableComponent(botonGenerar);
        checkColiformesTotalesProbables.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkColiformesTotalesProbablesActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 19;
        jPanel1.add(checkColiformesTotalesProbables, gridBagConstraints);

        checkLactobacillus.setText("MENOR A 10");
        checkLactobacillus.setNextFocusableComponent(botonGenerar);
        checkLactobacillus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkLactobacillusActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 21;
        jPanel1.add(checkLactobacillus, gridBagConstraints);

        checkBacteriasLacticas.setText("MENOR A 10");
        checkBacteriasLacticas.setNextFocusableComponent(botonGenerar);
        checkBacteriasLacticas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkBacteriasLacticasActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 22;
        jPanel1.add(checkBacteriasLacticas, gridBagConstraints);

        checkColiformesTotales45.setText("MENOR A 10");
        checkColiformesTotales45.setNextFocusableComponent(botonGenerar);
        checkColiformesTotales45.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkColiformesTotales45ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 23;
        jPanel1.add(checkColiformesTotales45, gridBagConstraints);

        panelVibrio.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 0, 1, new java.awt.Color(0, 0, 0)));
        panelVibrio.setFocusable(false);
        panelVibrio.setMaximumSize(new java.awt.Dimension(200, 30));
        panelVibrio.setMinimumSize(new java.awt.Dimension(200, 30));
        panelVibrio.setPreferredSize(new java.awt.Dimension(200, 30));
        panelVibrio.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelVibrioMouseClicked(evt);
            }
        });
        panelVibrio.setLayout(new java.awt.GridBagLayout());

        etiquetaVibrio.setBackground(new java.awt.Color(0, 0, 0));
        etiquetaVibrio.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        etiquetaVibrio.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        etiquetaVibrio.setText("<html>VIBRIO PARAHEMOLITYCUS</html>");
        etiquetaVibrio.setToolTipText("");
        etiquetaVibrio.setFocusable(false);
        etiquetaVibrio.setMaximumSize(new java.awt.Dimension(190, 30));
        etiquetaVibrio.setMinimumSize(new java.awt.Dimension(190, 30));
        etiquetaVibrio.setPreferredSize(new java.awt.Dimension(190, 30));
        etiquetaVibrio.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                etiquetaVibrioMouseClicked(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        panelVibrio.add(etiquetaVibrio, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 24;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        jPanel1.add(panelVibrio, gridBagConstraints);

        metodoVibrio.setFont(new java.awt.Font("Dialog", 0, 9)); // NOI18N
        metodoVibrio.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Cultivo en medio TCBS previo enriquecimiento" }));
        metodoVibrio.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        metodoVibrio.setMaximumSize(new java.awt.Dimension(200, 30));
        metodoVibrio.setMinimumSize(new java.awt.Dimension(200, 30));
        metodoVibrio.setPreferredSize(new java.awt.Dimension(200, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 24;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        jPanel1.add(metodoVibrio, gridBagConstraints);

        checkVibrio.setText("MENOR A 10");
        checkVibrio.setNextFocusableComponent(botonGenerar);
        checkVibrio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkVibrioActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 24;
        jPanel1.add(checkVibrio, gridBagConstraints);

        panelShigella.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelShigella.setFocusable(false);
        panelShigella.setMaximumSize(new java.awt.Dimension(200, 30));
        panelShigella.setMinimumSize(new java.awt.Dimension(200, 30));
        panelShigella.setPreferredSize(new java.awt.Dimension(200, 30));
        panelShigella.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelShigellaMouseClicked(evt);
            }
        });
        panelShigella.setLayout(new java.awt.GridBagLayout());

        etiquetaShigella.setBackground(new java.awt.Color(0, 0, 0));
        etiquetaShigella.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        etiquetaShigella.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        etiquetaShigella.setText("SHIGELLA");
        etiquetaShigella.setToolTipText("");
        etiquetaShigella.setFocusable(false);
        etiquetaShigella.setMaximumSize(new java.awt.Dimension(190, 30));
        etiquetaShigella.setMinimumSize(new java.awt.Dimension(190, 30));
        etiquetaShigella.setPreferredSize(new java.awt.Dimension(190, 30));
        etiquetaShigella.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                etiquetaShigellaMouseClicked(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        panelShigella.add(etiquetaShigella, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 26;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        jPanel1.add(panelShigella, gridBagConstraints);

        metodoShigella.setFont(new java.awt.Font("Dialog", 0, 9)); // NOI18N
        metodoShigella.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ISO 21507/2005" }));
        metodoShigella.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 1, new java.awt.Color(0, 0, 0)));
        metodoShigella.setMaximumSize(new java.awt.Dimension(200, 30));
        metodoShigella.setMinimumSize(new java.awt.Dimension(200, 30));
        metodoShigella.setPreferredSize(new java.awt.Dimension(200, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 26;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        jPanel1.add(metodoShigella, gridBagConstraints);

        checkListeria.setText("MENOR A 10");
        checkListeria.setNextFocusableComponent(botonGenerar);
        checkListeria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkListeriaActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 13;
        jPanel1.add(checkListeria, gridBagConstraints);

        panelVibrioCholerae.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 0, 1, new java.awt.Color(0, 0, 0)));
        panelVibrioCholerae.setFocusable(false);
        panelVibrioCholerae.setMaximumSize(new java.awt.Dimension(200, 30));
        panelVibrioCholerae.setMinimumSize(new java.awt.Dimension(200, 30));
        panelVibrioCholerae.setPreferredSize(new java.awt.Dimension(200, 30));
        panelVibrioCholerae.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelVibrioCholeraeMouseClicked(evt);
            }
        });
        panelVibrioCholerae.setLayout(new java.awt.GridBagLayout());

        etiquetaVibrioCholerae.setBackground(new java.awt.Color(0, 0, 0));
        etiquetaVibrioCholerae.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        etiquetaVibrioCholerae.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        etiquetaVibrioCholerae.setText("<html>VIBRIO CHOLERAE</html>");
        etiquetaVibrioCholerae.setToolTipText("");
        etiquetaVibrioCholerae.setFocusable(false);
        etiquetaVibrioCholerae.setMaximumSize(new java.awt.Dimension(190, 30));
        etiquetaVibrioCholerae.setMinimumSize(new java.awt.Dimension(190, 30));
        etiquetaVibrioCholerae.setPreferredSize(new java.awt.Dimension(190, 30));
        etiquetaVibrioCholerae.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                etiquetaVibrioCholeraeMouseClicked(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        panelVibrioCholerae.add(etiquetaVibrioCholerae, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 25;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        jPanel1.add(panelVibrioCholerae, gridBagConstraints);

        metodoVibrioCholerae.setFont(new java.awt.Font("Dialog", 0, 9)); // NOI18N
        metodoVibrioCholerae.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Cultivo en medio TCBS previo enriquecimiento" }));
        metodoVibrioCholerae.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 1, 1, new java.awt.Color(0, 0, 0)));
        metodoVibrioCholerae.setMaximumSize(new java.awt.Dimension(200, 30));
        metodoVibrioCholerae.setMinimumSize(new java.awt.Dimension(200, 30));
        metodoVibrioCholerae.setPreferredSize(new java.awt.Dimension(200, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 25;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        jPanel1.add(metodoVibrioCholerae, gridBagConstraints);

        checkVibrioCholerae.setText("MENOR A 10");
        checkVibrioCholerae.setNextFocusableComponent(botonGenerar);
        checkVibrioCholerae.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkVibrioCholeraeActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 25;
        jPanel1.add(checkVibrioCholerae, gridBagConstraints);

        jScrollPane1.setViewportView(jPanel1);
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false), "Cerrar");
        ap.put("Cerrar", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e){
                dispose();
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 796, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 660, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private String sacarPuntosFinales(String aux) {
        String auxiliar;
        try {
            auxiliar = aux.substring(0, aux.length() - 1);
            if (auxiliar.endsWith(".")) {
                auxiliar = sacarPuntosFinales(auxiliar);
            }

        } catch (StringIndexOutOfBoundsException e) {
            auxiliar = "";
        }
        return auxiliar;
    }

    private void botonGenerarActionPerformed(java.awt.event.ActionEvent evt) {
        Map m = new HashMap();
        String germenes = comboGermenes.getSelectedItem().toString().toUpperCase() + " "
                + cajaGermenes.getText().toUpperCase() + " " + comboGermenesIn.getSelectedItem().toString();
        String coliformesTotales = comboColiformesTotales.getSelectedItem().toString().toUpperCase() + " "
                + cajaColiformesTotales.getText().toUpperCase() + " " + comboColiformesTotalesIn.getSelectedItem().toString();
        String coliformesFecales = comboColiformesFecales.getSelectedItem().toString().toUpperCase() + " "
                + cajaColiformesFecales.getText().toUpperCase() + " " + comboColiformesFecalesIn.getSelectedItem().toString();
        String escherichia = comboEscherichia.getSelectedItem().toString().toUpperCase() + " "
                + comboEscherichiaIn.getSelectedItem().toString();
        String escherichiaH7 = comboEscherichiaH7.getSelectedItem().toString().toUpperCase() + " "
                + comboEscherichiaH7In.getSelectedItem().toString();
        String escherichia157 = comboEscherichia157.getSelectedItem().toString().toUpperCase() + " "
                + comboEscherichia157In.getSelectedItem().toString();
        String enterobacterias = comboEnterobacterias.getSelectedItem().toString().toUpperCase() + " "
                + cajaEnterobacterias.getText().toUpperCase() + " " + comboEnterobacteriasIn.getSelectedItem().toString();
        String staphilococos = comboStaphilococos.getSelectedItem().toString().toUpperCase() + " "
                + cajaStaphilococos.getText().toUpperCase() + " " + comboStaphilococosIn.getSelectedItem().toString();
        String mohos = comboMohos.getSelectedItem().toString().toUpperCase() + " "
                + cajaMohos.getText().toUpperCase() + " " + comboMohosIn.getSelectedItem().toString();
        String salmonella = comboSalmonella.getSelectedItem().toString().toUpperCase() + " " + comboSalmonellaIn.getSelectedItem().toString();
        String listeria;
        if (comboListeria.getSelectedItem().toString().contains("Ausencia") || comboListeria.getSelectedItem().toString().contains("Presencia")) {
            listeria = comboListeria.getSelectedItem().toString() + " " + comboListeriaIn.getSelectedItem().toString();
        } else {
            listeria = comboListeria.getSelectedItem().toString().toUpperCase() + " " + cajaListeria.getText() + " " + comboListeriaIn.getSelectedItem().toString();
        }
        String bacillus = comboBacillus.getSelectedItem().toString().toUpperCase() + " "
                + cajaBacillus.getText().toUpperCase() + " " + comboBacillusIn.getSelectedItem().toString();
        String perfringens = comboPerfringens.getSelectedItem().toString().toUpperCase() + " "
                + cajaPerfringens.getText().toUpperCase() + " " + comboPerfringensIn.getSelectedItem().toString();
        String sulfito = comboSulfito.getSelectedItem().toString().toUpperCase() + " "
                + cajaSulfito.getText().toUpperCase() + " " + comboSulfitoIn.getSelectedItem().toString();
        String campilobacter = comboCampilobacter.getSelectedItem().toString().toUpperCase() + " "
                + cajaCampilobacter.getText().toUpperCase() + " " + comboCampilobacterIn.getSelectedItem().toString();
        String caracteristicas = cajaCaracteristicas.getText().toUpperCase();
        String coliformesTotalesA30 = comboColiformesTotalesA30.getSelectedItem().toString().toUpperCase() + " "
                + cajaColiformesTotalesA30.getText().toUpperCase() + " " + comboColiformesTotalesA30In.getSelectedItem().toString();
        String coliformesTotalesProbables = comboColiformesTotalesProbables.getSelectedItem().toString().toUpperCase() + " "
                + cajaColiformesTotalesProbables.getText().toUpperCase() + " " + comboColiformesTotalesProbablesIn.getSelectedItem().toString();
        String lactobacillus = comboLactobacillus.getSelectedItem().toString().toUpperCase() + " "
                + cajaLactobacillus.getText().toUpperCase() + " " + comboLactobacillusIn.getSelectedItem().toString();
        String bacteriasLacticas = comboBacteriasLacticas.getSelectedItem().toString().toUpperCase() + " "
                + cajaBacteriasLacticas.getText().toUpperCase() + " " + comboBacteriasLacticasIn.getSelectedItem().toString();
        String coliformesTotales45 = comboColiformesTotales45.getSelectedItem().toString().toUpperCase() + " "
                + cajaColiformesTotales45.getText().toUpperCase() + " " + comboColiformesTotales45In.getSelectedItem().toString();
        String vibrio = comboVibrio.getSelectedItem().toString().toUpperCase() + " "
                + cajaVibrio.getText().toUpperCase() + " " + comboVibrioIn.getSelectedItem().toString();
        String vibrioCholerae = comboVibrioCholerae.getSelectedItem().toString().toUpperCase() + " "
                + cajaVibrioCholerae.getText().toUpperCase() + " " + comboVibrioCholeraeIn.getSelectedItem().toString();
        String shigella = comboShigella.getSelectedItem().toString().toUpperCase() + " " + comboShigellaIn.getSelectedItem().toString();
        String MetodoGermenes = metodoGermenes.getSelectedItem().toString();
        String MetodoColiformesTotales = metodoTotales.getSelectedItem().toString();
        String MetodoColiformesFecales = metodoFecales.getSelectedItem().toString();
        String MetodoEscherichia = metodoEscherichia.getSelectedItem().toString();
        String MetodoEscherichiaH7 = metodoEscherichiaH7.getSelectedItem().toString();
        String MetodoEscherichia157 = metodoEscherichia157.getSelectedItem().toString();
        String MetodoEnterobacterias = metodoEnterobacterias.getSelectedItem().toString();
        String MetodoStaphilococos = metodoStaphilococos.getSelectedItem().toString();
        String MetodoMohos = metodoMohos.getSelectedItem().toString();
        String MetodoSalmonella = metodoSalmonella.getSelectedItem().toString();
        String MetodoListeria = metodoListeria.getSelectedItem().toString();
        String MetodoBacillus = metodoBacillus.getSelectedItem().toString();
        String MetodoPerfringens = metodoPerfringens.getSelectedItem().toString();
        String MetodoSulfito = metodoSulfito.getSelectedItem().toString();
        String MetodoCampilobacter = metodoCampilobacter.getSelectedItem().toString();
        String MetodoColiformesTotalesA30 = metodoColiformesTotalesA30.getSelectedItem().toString();
        String MetodoColiformesTotalesProbables = metodoColiformesTotalesProbables.getSelectedItem().toString();
        String MetodoCaracteristicas = metodoCaracteristicas.getSelectedItem().toString();
        String MetodoLactobacillus = metodoLactobacillus.getSelectedItem().toString();
        String MetodoBacteriasLacticas = metodoBacteriasLacticas.getSelectedItem().toString();
        String MetodoColiformesTotales45 = metodoColiformesTotales45.getSelectedItem().toString();
        String MetodoShigella = metodoShigella.getSelectedItem().toString();
        String MetodoVibrio = metodoVibrio.getSelectedItem().toString();
        String MetodoVibrioCholerae = metodoVibrioCholerae.getSelectedItem().toString();
        m.put("metodoGermenes", MetodoGermenes);
        m.put("metodoColiformesTotales", MetodoColiformesTotales);
        m.put("metodoColiformesFecales", MetodoColiformesFecales);
        m.put("metodoEscherichia", MetodoEscherichia);
        m.put("metodoEscherichiaH7", MetodoEscherichiaH7);
        m.put("metodoEscherichia157", MetodoEscherichia157);
        m.put("metodoEnterobacterias", MetodoEnterobacterias);
        m.put("metodoStaphilococos", MetodoStaphilococos);
        m.put("metodoMohos", MetodoMohos);
        m.put("metodoSalmonella", MetodoSalmonella);
        m.put("metodoListeria", MetodoListeria);
        m.put("metodoBacillus", MetodoBacillus);
        m.put("metodoPerfringens", MetodoPerfringens);
        m.put("metodoSulfito", MetodoSulfito);
        m.put("metodoCampilobacter", MetodoCampilobacter);
        m.put("metodoColiformesTotalesA30", MetodoColiformesTotalesA30);
        m.put("metodoColiformesTotalesProbables", MetodoColiformesTotalesProbables);
        m.put("metodoCaracteristicas", MetodoCaracteristicas);
        m.put("metodoLactobacillus", MetodoLactobacillus);
        m.put("metodoBacteriasLacticas", MetodoBacteriasLacticas);
        m.put("metodoColiformesTotales45", MetodoColiformesTotales45);
        m.put("metodoVibrio", MetodoVibrio);
        m.put("metodoVibrioCholerae", MetodoVibrioCholerae);
        m.put("metodoShigella", MetodoShigella);
        m.put("idmuestras", id);
        if (cajaGermenes.isEnabled()) {
            m.put("germenes", germenes);
        } else {
            m.put("germenes", "-2");
        }
        if (cajaColiformesTotales.isEnabled()) {
            m.put("coliformesTotales", coliformesTotales);
        } else {
            m.put("coliformesTotales", "-2");
        }
        if (cajaColiformesFecales.isEnabled()) {
            m.put("coliformesFecales", coliformesFecales);
        } else {
            m.put("coliformesFecales", "-2");
        }
        if (comboEscherichia.isEnabled()) {
            m.put("escherichia", escherichia);
        } else {
            m.put("escherichia", "-2");
        }
        if (comboEscherichiaH7.isEnabled()) {
            m.put("escherichiaH7", escherichiaH7);
        } else {
            m.put("escherichiaH7", "-2");
        }
        if (comboEscherichia157.isEnabled()) {
            m.put("escherichia157", escherichia157);
        } else {
            m.put("escherichia157", "-2");
        }
        if (cajaEnterobacterias.isEnabled()) {
            m.put("enterobacterias", enterobacterias);
        } else {
            m.put("enterobacterias", "-2");
        }
        if (cajaStaphilococos.isEnabled()) {
            m.put("staphilococos", staphilococos);
        } else {
            m.put("staphilococos", "-2");
        }
        if (cajaMohos.isEnabled()) {
            m.put("mohosLevaduras", mohos);
        } else {
            m.put("mohosLevaduras", "-2");
        }
        if (comboSalmonella.isEnabled()) {
            m.put("salmonella", salmonella);
        } else {
            m.put("salmonella", "-2");
        }
        if (comboListeria.isEnabled()) {
            m.put("listeria", listeria);
        } else {
            m.put("listeria", "-2");
        }
        if (cajaBacillus.isEnabled()) {
            m.put("bacillus", bacillus);
        } else {
            m.put("bacillus", "-2");
        }
        if (cajaPerfringens.isEnabled()) {
            m.put("perfringens", perfringens);
        } else {
            m.put("perfringens", "-2");
        }
        if (cajaSulfito.isEnabled()) {
            m.put("sulfito", sulfito);
        } else {
            m.put("sulfito", "-2");
        }
        if (cajaCampilobacter.isEnabled()) {
            m.put("campilobacter", campilobacter);
        } else {
            m.put("campilobacter", "-2");
        }
        if (cajaCaracteristicas.isEnabled()) {
            m.put("caracteristicas", caracteristicas);
        } else {
            m.put("caracteristicas", "-2");
        }
        if (cajaColiformesTotalesA30.isEnabled()) {
            m.put("coliformesTotalesA30", coliformesTotalesA30);
        } else {
            m.put("coliformesTotalesA30", "-2");
        }
        if (cajaColiformesTotalesProbables.isEnabled()) {
            m.put("coliformesTotalesProbables", coliformesTotalesProbables);
        } else {
            m.put("coliformesTotalesProbables", "-2");
        }
        if (cajaLactobacillus.isEnabled()) {
            m.put("lactobacillus", lactobacillus);
        } else {
            m.put("lactobacillus", "-2");
        }
        if (cajaBacteriasLacticas.isEnabled()) {
            m.put("bacteriasLacticas", bacteriasLacticas);
        } else {
            m.put("bacteriasLacticas", "-2");
        }
        if (cajaColiformesTotales45.isEnabled()) {
            m.put("coliformesTotales45", coliformesTotales45);
        } else {
            m.put("coliformesTotales45", "-2");
        }
        if (cajaVibrio.isEnabled()) {
            m.put("vibrio", vibrio);
        } else {
            m.put("vibrio", "-2");
        }
        if (cajaVibrioCholerae.isEnabled()) {
            m.put("vibrioCholerae", vibrioCholerae);
        } else {
            m.put("vibrioCholerae", "-2");
        }
        if (comboShigella.isEnabled()) {
            m.put("shigella", shigella);
        } else {
            m.put("shigella", "-2");
        }
        m.put("procedencia", procedencia);
        try {
            java.util.Date fm = cajaFechaAnalisis.getDate(); //obtener fecha
            Long dm = fm.getTime(); //sacar timepo
            java.sql.Date fechaAnalisis = new java.sql.Date(dm); //cast de fecha
            m.put("fechaAnalisis", fechaAnalisis);
            String observaciones = "";
            String conclusion = "";
            if (checkConclusion.isSelected()) {
                conclusion = JOptionPane.showInputDialog("Ingrese la conclusion", auxConclusion);
                conclusion = conclusion.isBlank() ? "" : conclusion.trim().endsWith(".") ? conclusion : conclusion + ".";
            }
            muestraRepository.guardarConclusion(conclusion, id);
            if (editar) {
                observaciones = JOptionPane.showInputDialog("Digite la observación:", auxObservaciones);
            } else {
                observaciones = JOptionPane.showInputDialog("Digite la observación:");
            }
            observaciones = observaciones.isBlank() ? "" : observaciones.trim().endsWith(".") ? observaciones : observaciones + ".";
            m.put("observaciones", observaciones);
            if (editar) {
                File rv = new File(c.recuperarRutas("Reportes") + "\\" + pdf);
                File rn = new File(c.recuperarRutas("Reportes") + "\\(BORRADO) " + pdf);
                rv.renameTo(rn);
                if (resultadosRepository.editarMBAlimentos(m)) {
                    muestraRepository.guardarFechaAnalisis(m);
                    muestraRepository.guardarObservaciones(observaciones, id);
                    this.dispose();
                    c.generarReporteMBAlimentos(id, procedencia);
                }
            } else {
                if (resultadosRepository.guardarResultadoMBAlimentos(m)) {
                    muestraRepository.guardarFechaAnalisis(m);
                    muestraRepository.guardarObservaciones(observaciones, id);
                    this.dispose();
                    c.generarReporteMBAlimentos(id, procedencia);
                }
            }
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(null, "Ingrese la fecha de análisis.");
        }
    }

    private void checkEnterobacteriasActionPerformed(java.awt.event.ActionEvent evt) {
        if (checkEnterobacterias.isSelected()) {
            auxEnterobacteriasCombo = comboEnterobacterias.getSelectedIndex();
            auxEnterobacterias = cajaEnterobacterias.getText();
            comboEnterobacterias.setSelectedIndex(0);
            cajaEnterobacterias.setText("10");
        } else {
            comboEnterobacterias.setSelectedIndex(auxEnterobacteriasCombo);
            cajaEnterobacterias.setText(auxEnterobacterias);
        }
    }

    private void checkColiformesFecalesActionPerformed(java.awt.event.ActionEvent evt) {
        if (checkColiformesFecales.isSelected()) {
            auxFecalesCombo = comboColiformesFecales.getSelectedIndex();
            auxFecales = cajaColiformesFecales.getText();
            comboColiformesFecales.setSelectedIndex(0);
            cajaColiformesFecales.setText("10");
        } else {
            comboColiformesFecales.setSelectedIndex(auxFecalesCombo);
            cajaColiformesFecales.setText(auxFecales);
        }
    }

    private void checkColiformesTotalesActionPerformed(java.awt.event.ActionEvent evt) {
        if (checkColiformesTotales.isSelected()) {
            auxTotalesCombo = comboColiformesTotales.getSelectedIndex();
            auxTotales = cajaColiformesTotales.getText();
            comboColiformesTotales.setSelectedIndex(0);
            cajaColiformesTotales.setText("10");
        } else {
            comboColiformesTotales.setSelectedIndex(auxTotalesCombo);
            cajaColiformesTotales.setText(auxTotales);
        }
    }

    private void checkGermenesActionPerformed(java.awt.event.ActionEvent evt) {
        if (checkGermenes.isSelected()) {
            auxGermenesCombo = comboGermenes.getSelectedIndex();
            auxGermenes = cajaGermenes.getText();
            comboGermenes.setSelectedIndex(0);
            cajaGermenes.setText("10");
        } else {
            comboGermenes.setSelectedIndex(auxGermenesCombo);
            cajaGermenes.setText(auxGermenes);
        }
    }

    private void panelGermenesMouseClicked(java.awt.event.MouseEvent evt) {
        activarGermenes = !activarGermenes;
        cajaGermenes.setEnabled(activarGermenes);
        comboGermenes.setEnabled(activarGermenes);
        comboGermenesIn.setEnabled(activarGermenes);
        metodoGermenes.setEnabled(activarGermenes);
        if (activarGermenes) {
            panelGermenes.setBackground(new Color(240, 240, 240));
        } else {
            panelGermenes.setBackground(new Color(240, 100, 100));
        }
    }

    private void etiquetaGermenesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_etiquetaGermenesMouseClicked
        activarGermenes = !activarGermenes;
        cajaGermenes.setEnabled(activarGermenes);
        comboGermenes.setEnabled(activarGermenes);
        comboGermenesIn.setEnabled(activarGermenes);
        metodoGermenes.setEnabled(activarGermenes);
        if (activarGermenes) {
            panelGermenes.setBackground(new Color(240, 240, 240));
        } else {
            panelGermenes.setBackground(new Color(240, 100, 100));
        }
    }

    private void panelTotalesMouseClicked(java.awt.event.MouseEvent evt) {
        activarTotales = !activarTotales;
        cajaColiformesTotales.setEnabled(activarTotales);
        comboColiformesTotales.setEnabled(activarTotales);
        comboColiformesTotalesIn.setEnabled(activarTotales);
        metodoTotales.setEnabled(activarTotales);
        if (activarTotales) {
            panelTotales.setBackground(new Color(240, 240, 240));
        } else {
            panelTotales.setBackground(new Color(240, 100, 100));
        }
    }

    private void etiquetaTotalesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_etiquetaTotalesMouseClicked
        activarTotales = !activarTotales;
        cajaColiformesTotales.setEnabled(activarTotales);
        comboColiformesTotales.setEnabled(activarTotales);
        comboColiformesTotalesIn.setEnabled(activarTotales);
        metodoTotales.setEnabled(activarTotales);
        if (activarTotales) {
            panelTotales.setBackground(new Color(240, 240, 240));
        } else {
            panelTotales.setBackground(new Color(240, 100, 100));
        }
    }

    private void panelFecalesMouseClicked(java.awt.event.MouseEvent evt) {
        activarFecales = !activarFecales;
        cajaColiformesFecales.setEnabled(activarFecales);
        comboColiformesFecales.setEnabled(activarFecales);
        comboColiformesFecalesIn.setEnabled(activarFecales);
        if (activarFecales) {
            panelFecales.setBackground(new Color(240, 240, 240));
        } else {
            panelFecales.setBackground(new Color(240, 100, 100));
        }
    }

    private void etiquetaFecalesMouseClicked(java.awt.event.MouseEvent evt) {
        activarFecales = !activarFecales;
        cajaColiformesFecales.setEnabled(activarFecales);
        comboColiformesFecales.setEnabled(activarFecales);
        comboColiformesFecalesIn.setEnabled(activarFecales);
        metodoFecales.setEnabled(activarFecales);
        if (activarFecales) {
            panelFecales.setBackground(new Color(240, 240, 240));
        } else {
            panelFecales.setBackground(new Color(240, 100, 100));
        }
    }

    private void panelEscherichiaH7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelEscherichiaH7MouseClicked

    }//GEN-LAST:event_panelEscherichiaH7MouseClicked

    private void etiquetaEscherichiaH7MouseClicked(java.awt.event.MouseEvent evt) {
        activarEscherichiaH7 = !activarEscherichiaH7;
        comboEscherichiaH7.setEnabled(activarEscherichiaH7);
        comboEscherichiaH7In.setEnabled(activarEscherichiaH7);
        metodoEscherichiaH7.setEnabled(activarEscherichiaH7);
        if (activarEscherichiaH7) {
            panelEscherichiaH7.setBackground(new Color(240, 240, 240));
        } else {
            panelEscherichiaH7.setBackground(new Color(240, 100, 100));
        }
    }

    private void panelStaphilococosMouseClicked(java.awt.event.MouseEvent evt) {
        activarStaphilococos = !activarStaphilococos;
        cajaEnterobacterias.setEnabled(activarStaphilococos);
        comboEnterobacterias.setEnabled(activarStaphilococos);
        comboEnterobacteriasIn.setEnabled(activarStaphilococos);
        metodoStaphilococos.setEnabled(activarStaphilococos);
        if (activarStaphilococos) {
            panelStaphilococos.setBackground(new Color(240, 240, 240));
        } else {
            panelStaphilococos.setBackground(new Color(240, 100, 100));
        }
    }

    private void etiquetaStaphilococosMouseClicked(java.awt.event.MouseEvent evt) {
        activarStaphilococos = !activarStaphilococos;
        cajaStaphilococos.setEnabled(activarStaphilococos);
        comboStaphilococos.setEnabled(activarStaphilococos);
        comboStaphilococosIn.setEnabled(activarStaphilococos);
        metodoStaphilococos.setEnabled(activarStaphilococos);
        if (activarStaphilococos) {
            panelStaphilococos.setBackground(new Color(240, 240, 240));
        } else {
            panelStaphilococos.setBackground(new Color(240, 100, 100));
        }
    }

    private void panelSalmonellaMouseClicked(java.awt.event.MouseEvent evt) {
        activarSalmonella = !activarSalmonella;
        comboSalmonella.setEnabled(activarSalmonella);
        comboSalmonellaIn.setEnabled(activarSalmonella);
        metodoSalmonella.setEnabled(activarSalmonella);
        if (activarSalmonella) {
            panelSalmonella.setBackground(new Color(240, 240, 240));
        } else {
            panelSalmonella.setBackground(new Color(240, 100, 100));
        }
    }

    private void etiquetaSalmonellaMouseClicked(java.awt.event.MouseEvent evt) {
        activarSalmonella = !activarSalmonella;
        comboSalmonella.setEnabled(activarSalmonella);
        comboSalmonellaIn.setEnabled(activarSalmonella);
        metodoSalmonella.setEnabled(activarSalmonella);
        if (activarSalmonella) {
            panelSalmonella.setBackground(new Color(240, 240, 240));
        } else {
            panelSalmonella.setBackground(new Color(240, 100, 100));
        }
    }

    private void panelMohosMouseClicked(java.awt.event.MouseEvent evt) {
        activarMohos = !activarMohos;
        cajaMohos.setEnabled(activarMohos);
        comboMohos.setEnabled(activarMohos);
        comboMohosIn.setEnabled(activarMohos);
        metodoMohos.setEnabled(activarMohos);
        if (activarMohos) {
            panelMohos.setBackground(new Color(240, 240, 240));
        } else {
            panelMohos.setBackground(new Color(240, 100, 100));
        }
    }

    private void etiquetaMohosMouseClicked(java.awt.event.MouseEvent evt) {
        activarMohos = !activarMohos;
        cajaMohos.setEnabled(activarMohos);
        comboMohos.setEnabled(activarMohos);
        comboMohosIn.setEnabled(activarMohos);
        metodoMohos.setEnabled(activarMohos);
        if (activarMohos) {
            panelMohos.setBackground(new Color(240, 240, 240));
        } else {
            panelMohos.setBackground(new Color(240, 100, 100));
        }
    }

    private void etiquetaEscherichia157MouseClicked(java.awt.event.MouseEvent evt) {
        activarEscherichia157 = !activarEscherichia157;
        comboEscherichia157.setEnabled(activarEscherichia157);
        comboEscherichia157In.setEnabled(activarEscherichia157);
        metodoEscherichia157.setEnabled(activarEscherichia157);
        if (activarEscherichia157) {
            panelEscherichia157.setBackground(new Color(240, 240, 240));
        } else {
            panelEscherichia157.setBackground(new Color(240, 100, 100));
        }
    }

    private void etiquetaEnterobacteriasMouseClicked(java.awt.event.MouseEvent evt) {
        activarEnterobacterias = !activarEnterobacterias;
        cajaEnterobacterias.setEnabled(activarEnterobacterias);
        comboEnterobacterias.setEnabled(activarEnterobacterias);
        comboEnterobacteriasIn.setEnabled(activarEnterobacterias);
        metodoEnterobacterias.setEnabled(activarEnterobacterias);
        if (activarEnterobacterias) {
            panelEnterobacterias.setBackground(new Color(240, 240, 240));
        } else {
            panelEnterobacterias.setBackground(new Color(240, 100, 100));
        }
    }

    private void etiquetaListeriaMouseClicked(java.awt.event.MouseEvent evt) {
        activarListeria = !activarListeria;
        comboListeria.setEnabled(activarListeria);
        comboListeriaIn.setEnabled(activarListeria);
        metodoListeria.setEnabled(activarListeria);
        if (activarListeria) {
            panelListeria.setBackground(new Color(240, 240, 240));
        } else {
            panelListeria.setBackground(new Color(240, 100, 100));
        }
    }

    private void etiquetaBacillusMouseClicked(java.awt.event.MouseEvent evt) {
        activarBacillus = !activarBacillus;
        cajaBacillus.setEnabled(activarBacillus);
        comboBacillus.setEnabled(activarBacillus);
        comboBacillusIn.setEnabled(activarBacillus);
        metodoBacillus.setEnabled(activarBacillus);
        if (activarBacillus) {
            panelBacillus.setBackground(new Color(240, 240, 240));
        } else {
            panelBacillus.setBackground(new Color(240, 100, 100));
        }
    }

    private void etiquetaPerfringensMouseClicked(java.awt.event.MouseEvent evt) {
        activarPerfringens = !activarPerfringens;
        cajaPerfringens.setEnabled(activarPerfringens);
        comboPerfringensIn.setEnabled(activarPerfringens);
        comboPerfringens.setEnabled(activarPerfringens);
        metodoPerfringens.setEnabled(activarPerfringens);
        if (activarPerfringens) {
            panelPerfringens.setBackground(new Color(240, 240, 240));
        } else {
            panelPerfringens.setBackground(new Color(240, 100, 100));
        }
    }

    private void etiquetaSulfitoMouseClicked(java.awt.event.MouseEvent evt) {
        activarSulfito = !activarSulfito;
        cajaSulfito.setEnabled(activarSulfito);
        comboSulfito.setEnabled(activarSulfito);
        comboSulfitoIn.setEnabled(activarSulfito);
        metodoSulfito.setEnabled(activarSulfito);
        if (activarSulfito) {
            panelSulfito.setBackground(new Color(240, 240, 240));
        } else {
            panelSulfito.setBackground(new Color(240, 100, 100));
        }
    }

    private void etiquetaCampilobacterMouseClicked(java.awt.event.MouseEvent evt) {
        activarCampilobacter = !activarCampilobacter;
        cajaCampilobacter.setEnabled(activarCampilobacter);
        comboCampilobacter.setEnabled(activarCampilobacter);
        comboCampilobacterIn.setEnabled(activarCampilobacter);
        metodoCampilobacter.setEnabled(activarCampilobacter);
        if (activarCampilobacter) {
            panelCampilobacter.setBackground(new Color(240, 240, 240));
        } else {
            panelCampilobacter.setBackground(new Color(240, 100, 100));
        }
    }

    private void etiquetaEscherichiaMouseClicked(java.awt.event.MouseEvent evt) {
        activarEscherichia = !activarEscherichia;
        comboEscherichia.setEnabled(activarEscherichia);
        comboEscherichiaIn.setEnabled(activarEscherichia);
        metodoEscherichia.setEnabled(activarEscherichia);
        if (activarEscherichia) {
            panelEscherichia.setBackground(new Color(240, 240, 240));
        } else {
            panelEscherichia.setBackground(new Color(240, 100, 100));
        }
    }

    private void checkStaphilococosActionPerformed(java.awt.event.ActionEvent evt) {
        if (checkStaphilococos.isSelected()) {
            auxStaphilococosCombo = comboStaphilococos.getSelectedIndex();
            auxStaphilococos = cajaStaphilococos.getText();
            comboStaphilococos.setSelectedIndex(0);
            cajaStaphilococos.setText("10");
        } else {
            comboStaphilococos.setSelectedIndex(auxStaphilococosCombo);
            cajaStaphilococos.setText(auxStaphilococos);
        }
    }

    private void checkMohosActionPerformed(java.awt.event.ActionEvent evt) {
        if (checkMohos.isSelected()) {
            comboMohos.setSelectedItem("Menor a");
            cajaMohos.setText("10");
        } else {
            comboMohos.setSelectedItem("Menor a");
            cajaMohos.setText("10");
        }
    }

    private void checkBacillusActionPerformed(java.awt.event.ActionEvent evt) {
        if (checkBacillus.isSelected()) {
            auxBacillusCombo = comboBacillus.getSelectedIndex();
            auxBacillus = cajaBacillus.getText();
            comboBacillus.setSelectedIndex(0);
            cajaBacillus.setText("10");
        } else {
            comboBacillus.setSelectedIndex(auxBacillusCombo);
            cajaBacillus.setText(auxBacillus);
        }
    }

    private void checkPerfringensActionPerformed(java.awt.event.ActionEvent evt) {
        if (checkPerfringens.isSelected()) {
            auxPerfringensCombo = comboPerfringens.getSelectedIndex();
            auxPerfringens = cajaPerfringens.getText();
            comboPerfringens.setSelectedIndex(0);
            cajaPerfringens.setText("10");
        } else {
            comboPerfringens.setSelectedIndex(auxPerfringensCombo);
            cajaPerfringens.setText(auxPerfringens);
        }
    }

    private void checkSulfitoActionPerformed(java.awt.event.ActionEvent evt) {
        if (checkSulfito.isSelected()) {
            auxSulfitoCombo = comboSulfito.getSelectedIndex();
            auxSulfito = cajaSulfito.getText();
            comboSulfito.setSelectedIndex(0);
            cajaSulfito.setText("10");
        } else {
            comboSulfito.setSelectedIndex(auxSulfitoCombo);
            cajaSulfito.setText(auxSulfito);
        }
    }

    private void checkCampilobacterActionPerformed(java.awt.event.ActionEvent evt) {
        if (checkCampilobacter.isSelected()) {
            auxCampilobacterCombo = comboCampilobacter.getSelectedIndex();
            auxCampilobacter = cajaCampilobacter.getText();
            comboCampilobacter.setSelectedIndex(0);
            cajaCampilobacter.setText("10");
        } else {
            comboCampilobacter.setSelectedIndex(auxCampilobacterCombo);
            cajaCampilobacter.setText(auxCampilobacter);
        }
    }

    private void etiquetaCaracteristicasMouseClicked(java.awt.event.MouseEvent evt) {
        activarCaracteristicas = !activarCaracteristicas;
        cajaCaracteristicas.setEnabled(activarCaracteristicas);
        metodoCaracteristicas.setEnabled(activarCaracteristicas);
        if (activarCaracteristicas) {
            panelCaracteristicas.setBackground(new Color(240, 240, 240));
        } else {
            panelCaracteristicas.setBackground(new Color(240, 100, 100));
        }
    }

    private void etiquetaColiformesTotalesA30MouseClicked(java.awt.event.MouseEvent evt) {
        activarColiformesTotalesA30 = !activarColiformesTotalesA30;
        cajaColiformesTotalesA30.setEnabled(activarColiformesTotalesA30);
        metodoColiformesTotalesA30.setEnabled(activarColiformesTotalesA30);
        comboColiformesTotalesA30.setEnabled(activarColiformesTotalesA30);
        comboColiformesTotalesA30In.setEnabled(activarColiformesTotalesA30);
        if (activarColiformesTotalesA30) {
            panelColiformesTotalesA30.setBackground(new Color(240, 240, 240));
        } else {
            panelColiformesTotalesA30.setBackground(new Color(240, 100, 100));
        }
    }

    private void panelColiformesTotalesA30MouseClicked(java.awt.event.MouseEvent evt) {
        activarColiformesTotalesA30 = !activarColiformesTotalesA30;
        cajaColiformesTotalesA30.setEnabled(activarColiformesTotalesA30);
        metodoColiformesTotalesA30.setEnabled(activarColiformesTotalesA30);
        comboColiformesTotalesA30.setEnabled(activarColiformesTotalesA30);
        comboColiformesTotalesA30In.setEnabled(activarColiformesTotalesA30);
        if (activarColiformesTotalesA30) {
            panelColiformesTotalesA30.setBackground(new Color(240, 240, 240));
        } else {
            panelColiformesTotalesA30.setBackground(new Color(240, 100, 100));
        }
    }

    private void etiquetaColiformesTotalesProbableMouseClicked(java.awt.event.MouseEvent evt) {
        activarColiformesTotalesProbables = !activarColiformesTotalesProbables;
        cajaColiformesTotalesProbables.setEnabled(activarColiformesTotalesProbables);
        metodoColiformesTotalesProbables.setEnabled(activarColiformesTotalesProbables);
        comboColiformesTotalesProbables.setEnabled(activarColiformesTotalesProbables);
        comboColiformesTotalesProbablesIn.setEnabled(activarColiformesTotalesProbables);
        if (activarColiformesTotalesProbables) {
            panelColiformesTotalesProbables.setBackground(new Color(240, 240, 240));
        } else {
            panelColiformesTotalesProbables.setBackground(new Color(240, 100, 100));
        }
    }

    private void panelColiformesTotalesProbablesMouseClicked(java.awt.event.MouseEvent evt) {
        activarColiformesTotalesProbables = !activarColiformesTotalesProbables;
        cajaColiformesTotalesProbables.setEnabled(activarColiformesTotalesProbables);
        metodoColiformesTotalesProbables.setEnabled(activarColiformesTotalesProbables);
        comboColiformesTotalesProbables.setEnabled(activarColiformesTotalesProbables);
        comboColiformesTotalesProbablesIn.setEnabled(activarColiformesTotalesProbables);
        if (activarColiformesTotalesProbables) {
            panelColiformesTotalesProbables.setBackground(new Color(240, 240, 240));
        } else {
            panelColiformesTotalesProbables.setBackground(new Color(240, 100, 100));
        }
    }

    private void etiquetaLactobacillusMouseClicked(java.awt.event.MouseEvent evt) {
        activarLactobacillus = !activarLactobacillus;
        cajaLactobacillus.setEnabled(activarLactobacillus);
        comboLactobacillus.setEnabled(activarLactobacillus);
        comboLactobacillusIn.setEnabled(activarLactobacillus);
        metodoLactobacillus.setEnabled(activarLactobacillus);
        if (activarLactobacillus) {
            panelLactobacillus.setBackground(new Color(240, 240, 240));
        } else {
            panelLactobacillus.setBackground(new Color(240, 100, 100));
        }
    }

    private void panelLactobacillusMouseClicked(java.awt.event.MouseEvent evt) {
        activarLactobacillus = !activarLactobacillus;
        cajaLactobacillus.setEnabled(activarLactobacillus);
        comboLactobacillus.setEnabled(activarLactobacillus);
        comboLactobacillusIn.setEnabled(activarLactobacillus);
        metodoLactobacillus.setEnabled(activarLactobacillus);
        if (activarLactobacillus) {
            panelLactobacillus.setBackground(new Color(240, 240, 240));
        } else {
            panelLactobacillus.setBackground(new Color(240, 100, 100));
        }
    }

    private void etiquetaBacteriasLacticasMouseClicked(java.awt.event.MouseEvent evt) {
        activarBacteriasLacticas = !activarBacteriasLacticas;
        cajaBacteriasLacticas.setEnabled(activarBacteriasLacticas);
        comboBacteriasLacticas.setEnabled(activarBacteriasLacticas);
        comboBacteriasLacticasIn.setEnabled(activarBacteriasLacticas);
        metodoBacteriasLacticas.setEnabled(activarBacteriasLacticas);
        if (activarBacteriasLacticas) {
            panelBacteriasLacticas.setBackground(new Color(240, 240, 240));
        } else {
            panelBacteriasLacticas.setBackground(new Color(240, 100, 100));
        }
    }

    private void panelBacteriasLacticasMouseClicked(java.awt.event.MouseEvent evt) {
        activarBacteriasLacticas = !activarBacteriasLacticas;
        cajaBacteriasLacticas.setEnabled(activarBacteriasLacticas);
        comboBacteriasLacticas.setEnabled(activarBacteriasLacticas);
        comboBacteriasLacticasIn.setEnabled(activarBacteriasLacticas);
        metodoBacteriasLacticas.setEnabled(activarBacteriasLacticas);
        if (activarBacteriasLacticas) {
            panelBacteriasLacticas.setBackground(new Color(240, 240, 240));
        } else {
            panelBacteriasLacticas.setBackground(new Color(240, 100, 100));
        }
    }

    private void etiquetaColiformesTotales45MouseClicked(java.awt.event.MouseEvent evt) {
        activarColiformesTotales45 = !activarColiformesTotales45;
        cajaColiformesTotales45.setEnabled(activarColiformesTotales45);
        metodoColiformesTotales45.setEnabled(activarColiformesTotales45);
        comboColiformesTotales45.setEnabled(activarColiformesTotales45);
        comboColiformesTotales45In.setEnabled(activarColiformesTotales45);
        if (activarColiformesTotales45) {
            panelColiformesTotales45.setBackground(new Color(240, 240, 240));
        } else {
            panelColiformesTotales45.setBackground(new Color(240, 100, 100));
        }
    }

    private void panelColiformesTotales45MouseClicked(java.awt.event.MouseEvent evt) {
        activarColiformesTotales45 = !activarColiformesTotales45;
        cajaColiformesTotales45.setEnabled(activarColiformesTotales45);
        metodoColiformesTotales45.setEnabled(activarColiformesTotales45);
        comboColiformesTotales45.setEnabled(activarColiformesTotales45);
        comboColiformesTotales45In.setEnabled(activarColiformesTotales45);
        if (activarColiformesTotales45) {
            panelColiformesTotales45.setBackground(new Color(240, 240, 240));
        } else {
            panelColiformesTotales45.setBackground(new Color(240, 100, 100));
        }
    }

    private void checkColiformesTotalesA30ActionPerformed(java.awt.event.ActionEvent evt) {
        if (checkColiformesTotalesA30.isSelected()) {
            auxColiformesTotalesA30Combo = comboColiformesTotalesA30.getSelectedIndex();
            auxColiformesTotalesA30 = cajaColiformesTotalesA30.getText();
            comboColiformesTotalesA30.setSelectedIndex(0);
            cajaColiformesTotalesA30.setText("10");
        } else {
            comboColiformesTotalesA30.setSelectedIndex(auxColiformesTotalesA30Combo);
            cajaColiformesTotalesA30.setText(auxColiformesTotalesA30);
        }
    }

    private void checkColiformesTotalesProbablesActionPerformed(java.awt.event.ActionEvent evt) {
        if (checkColiformesTotalesProbables.isSelected()) {
            auxColiformesTotalesProbablesCombo = comboColiformesTotalesProbables.getSelectedIndex();
            auxColiformesTotalesProbables = cajaColiformesTotalesProbables.getText();
            comboColiformesTotalesProbables.setSelectedIndex(0);
            cajaColiformesTotalesProbables.setText("10");
        } else {
            comboColiformesTotalesProbables.setSelectedIndex(auxColiformesTotalesProbablesCombo);
            cajaColiformesTotalesProbables.setText(auxColiformesTotalesProbables);
        }
    }

    private void checkLactobacillusActionPerformed(java.awt.event.ActionEvent evt) {
        if (checkLactobacillus.isSelected()) {
            auxLactobacillusCombo = comboLactobacillus.getSelectedIndex();
            auxLactobacillus = cajaLactobacillus.getText();
            comboLactobacillus.setSelectedIndex(0);
            cajaLactobacillus.setText("10");
        } else {
            comboLactobacillus.setSelectedIndex(auxLactobacillusCombo);
            cajaLactobacillus.setText(auxLactobacillus);
        }
    }

    private void checkBacteriasLacticasActionPerformed(java.awt.event.ActionEvent evt) {
        if (checkBacteriasLacticas.isSelected()) {
            auxBacteriasLacticasCombo = comboBacteriasLacticas.getSelectedIndex();
            auxBacteriasLacticas = cajaBacteriasLacticas.getText();
            comboBacteriasLacticas.setSelectedIndex(0);
            cajaBacteriasLacticas.setText("10");
        } else {
            comboBacteriasLacticas.setSelectedIndex(auxBacteriasLacticasCombo);
            cajaBacteriasLacticas.setText(auxBacteriasLacticas);
        }
    }

    private void checkColiformesTotales45ActionPerformed(java.awt.event.ActionEvent evt) {
        if (checkColiformesTotales45.isSelected()) {
            auxColiformesTotales45Combo = comboColiformesTotales45.getSelectedIndex();
            auxColiformesTotales45 = cajaColiformesTotales45.getText();
            comboColiformesTotales45.setSelectedIndex(0);
            cajaColiformesTotales45.setText("10");
        } else {
            comboColiformesTotales45.setSelectedIndex(auxColiformesTotales45Combo);
            cajaColiformesTotales45.setText(auxColiformesTotales45);
        }
    }

    private void etiquetaVibrioMouseClicked(java.awt.event.MouseEvent evt) {
        activarVibrio = !activarVibrio;
        cajaVibrio.setEnabled(activarVibrio);
        metodoVibrio.setEnabled(activarVibrio);
        comboVibrio.setEnabled(activarVibrio);
        comboVibrioIn.setEnabled(activarVibrio);
        if (activarVibrio) {
            panelVibrio.setBackground(new Color(240, 240, 240));
        } else {
            panelVibrio.setBackground(new Color(240, 100, 100));
        }
    }

    private void panelVibrioMouseClicked(java.awt.event.MouseEvent evt) {
        activarVibrio = !activarVibrio;
        cajaVibrio.setEnabled(activarVibrio);
        metodoVibrio.setEnabled(activarVibrio);
        comboVibrio.setEnabled(activarVibrio);
        comboVibrioIn.setEnabled(activarVibrio);
        if (activarVibrio) {
            panelVibrio.setBackground(new Color(240, 240, 240));
        } else {
            panelVibrio.setBackground(new Color(240, 100, 100));
        }
    }

    private void checkVibrioActionPerformed(java.awt.event.ActionEvent evt) {
        if (checkVibrio.isSelected()) {
            auxVibrioCombo = comboVibrio.getSelectedIndex();
            auxVibrio = cajaVibrio.getText();
            comboVibrio.setSelectedIndex(0);
            cajaVibrio.setText("10");
        } else {
            comboVibrio.setSelectedIndex(auxVibrioCombo);
            cajaVibrio.setText(auxVibrio);
        }
    }

    private void etiquetaShigellaMouseClicked(java.awt.event.MouseEvent evt) {
        activarShigella = !activarShigella;
        comboShigella.setEnabled(activarShigella);
        comboShigellaIn.setEnabled(activarShigella);
        metodoShigella.setEnabled(activarShigella);
        if (activarShigella) {
            panelShigella.setBackground(new Color(240, 240, 240));
        } else {
            panelShigella.setBackground(new Color(240, 100, 100));
        }
    }

    private void panelShigellaMouseClicked(java.awt.event.MouseEvent evt) {
        activarShigella = !activarShigella;
        comboShigella.setEnabled(activarShigella);
        comboShigellaIn.setEnabled(activarShigella);
        metodoShigella.setEnabled(activarShigella);
        if (activarShigella) {
            panelShigella.setBackground(new Color(240, 240, 240));
        } else {
            panelShigella.setBackground(new Color(240, 100, 100));
        }
    }

    private void checkListeriaActionPerformed(java.awt.event.ActionEvent evt) {
        if (checkListeria.isSelected()) {
            auxListeriaCombo = comboListeria.getSelectedIndex();
            auxListeria = cajaListeria.getText();
            comboListeria.setSelectedIndex(0);
            cajaListeria.setText("10");
        } else {
            comboListeria.setSelectedIndex(auxListeriaCombo);
            cajaListeria.setText(auxListeria);
        }
    }

    private void comboListeriaItemStateChanged(java.awt.event.ItemEvent evt) {
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            if (evt.getItem().toString().toLowerCase().contains("presencia") || evt.getItem().toString().toLowerCase().contains("ausencia")) {
                comboListeria.setSize(105, 30);
                comboListeria.setPreferredSize(new Dimension(105, 30));
                comboListeria.setMinimumSize(new Dimension(105, 30));
                comboListeria.setMaximumSize(new Dimension(105, 30));
                comboListeriaIn.setSize(65, 30);
                comboListeriaIn.setPreferredSize(new Dimension(65, 30));
                comboListeriaIn.setMinimumSize(new Dimension(65, 30));
                comboListeriaIn.setMaximumSize(new Dimension(65, 30));
                checkListeria.setVisible(false);
                cajaListeria.setVisible(false);
            } else {
                comboListeria.setSize(58, 30);
                comboListeria.setPreferredSize(new Dimension(58, 30));
                comboListeria.setMinimumSize(new Dimension(58, 30));
                comboListeria.setMaximumSize(new Dimension(58, 30));
                comboListeriaIn.setSize(75, 30);
                comboListeriaIn.setPreferredSize(new Dimension(75, 30));
                comboListeriaIn.setMinimumSize(new Dimension(75, 30));
                comboListeriaIn.setMaximumSize(new Dimension(75, 30));
                checkListeria.setVisible(true);
                cajaListeria.setVisible(true);
            }
        }
        jPanel31.updateUI();
    }

    private void etiquetaVibrioCholeraeMouseClicked(java.awt.event.MouseEvent evt) {
        activarVibrioCholerae = !activarVibrioCholerae;
        cajaVibrioCholerae.setEnabled(activarVibrioCholerae);
        metodoVibrioCholerae.setEnabled(activarVibrioCholerae);
        comboVibrioCholerae.setEnabled(activarVibrioCholerae);
        comboVibrioCholeraeIn.setEnabled(activarVibrioCholerae);
        if (activarVibrioCholerae) {
            panelVibrioCholerae.setBackground(new Color(240, 240, 240));
        } else {
            panelVibrioCholerae.setBackground(new Color(240, 100, 100));
        }
    }

    private void panelVibrioCholeraeMouseClicked(java.awt.event.MouseEvent evt) {
        activarVibrioCholerae = !activarVibrioCholerae;
        cajaVibrioCholerae.setEnabled(activarVibrioCholerae);
        metodoVibrioCholerae.setEnabled(activarVibrioCholerae);
        comboVibrioCholerae.setEnabled(activarVibrioCholerae);
        comboVibrioCholeraeIn.setEnabled(activarVibrioCholerae);
        if (activarVibrioCholerae) {
            panelVibrioCholerae.setBackground(new Color(240, 240, 240));
        } else {
            panelVibrioCholerae.setBackground(new Color(240, 100, 100));
        }    }

    private void checkVibrioCholeraeActionPerformed(java.awt.event.ActionEvent evt) {
        if (checkVibrioCholerae.isSelected()) {
            auxVibrioCholeraeCombo = comboVibrioCholerae.getSelectedIndex();
            auxVibrioCholerae = cajaVibrioCholerae.getText();
            comboVibrioCholerae.setSelectedIndex(0);
            cajaVibrioCholerae.setText("10");
        } else {
            comboVibrioCholerae.setSelectedIndex(auxVibrioCholeraeCombo);
            cajaVibrioCholerae.setText(auxVibrioCholerae);
        }
    }

    private javax.swing.JButton botonGenerar;
    private javax.swing.JTextField cajaBacillus;
    private javax.swing.JTextField cajaBacteriasLacticas;
    private javax.swing.JTextField cajaCampilobacter;
    private javax.swing.JTextField cajaCaracteristicas;
    private javax.swing.JTextField cajaColiformesFecales;
    private javax.swing.JTextField cajaColiformesTotales;
    private javax.swing.JTextField cajaColiformesTotales45;
    private javax.swing.JTextField cajaColiformesTotalesA30;
    private javax.swing.JTextField cajaColiformesTotalesProbables;
    private javax.swing.JTextField cajaEnterobacterias;
    private com.toedter.calendar.JDateChooser cajaFechaAnalisis;
    private javax.swing.JTextField cajaGermenes;
    private javax.swing.JTextField cajaLactobacillus;
    private javax.swing.JTextField cajaListeria;
    private javax.swing.JTextField cajaMohos;
    private javax.swing.JTextField cajaPerfringens;
    private javax.swing.JTextField cajaStaphilococos;
    private javax.swing.JTextField cajaSulfito;
    private javax.swing.JTextField cajaVibrio;
    private javax.swing.JTextField cajaVibrioCholerae;
    private javax.swing.JCheckBox checkBacillus;
    private javax.swing.JCheckBox checkBacteriasLacticas;
    private javax.swing.JCheckBox checkCampilobacter;
    private javax.swing.JCheckBox checkColiformesFecales;
    private javax.swing.JCheckBox checkColiformesTotales;
    private javax.swing.JCheckBox checkColiformesTotales45;
    private javax.swing.JCheckBox checkColiformesTotalesA30;
    private javax.swing.JCheckBox checkColiformesTotalesProbables;
    private javax.swing.JCheckBox checkConclusion;
    private javax.swing.JCheckBox checkEnterobacterias;
    private javax.swing.JCheckBox checkGermenes;
    private javax.swing.JCheckBox checkLactobacillus;
    private javax.swing.JCheckBox checkListeria;
    private javax.swing.JCheckBox checkMohos;
    private javax.swing.JCheckBox checkPerfringens;
    private javax.swing.JCheckBox checkStaphilococos;
    private javax.swing.JCheckBox checkSulfito;
    private javax.swing.JCheckBox checkVibrio;
    private javax.swing.JCheckBox checkVibrioCholerae;
    private javax.swing.JComboBox<String> comboBacillus;
    private javax.swing.JComboBox<String> comboBacillusIn;
    private javax.swing.JComboBox<String> comboBacteriasLacticas;
    private javax.swing.JComboBox<String> comboBacteriasLacticasIn;
    private javax.swing.JComboBox<String> comboCampilobacter;
    private javax.swing.JComboBox<String> comboCampilobacterIn;
    private javax.swing.JComboBox<String> comboColiformesFecales;
    private javax.swing.JComboBox<String> comboColiformesFecalesIn;
    private javax.swing.JComboBox<String> comboColiformesTotales;
    private javax.swing.JComboBox<String> comboColiformesTotales45;
    private javax.swing.JComboBox<String> comboColiformesTotales45In;
    private javax.swing.JComboBox<String> comboColiformesTotalesA30;
    private javax.swing.JComboBox<String> comboColiformesTotalesA30In;
    private javax.swing.JComboBox<String> comboColiformesTotalesIn;
    private javax.swing.JComboBox<String> comboColiformesTotalesProbables;
    private javax.swing.JComboBox<String> comboColiformesTotalesProbablesIn;
    private javax.swing.JComboBox<String> comboEnterobacterias;
    private javax.swing.JComboBox<String> comboEnterobacteriasIn;
    private javax.swing.JComboBox<String> comboEscherichia;
    private javax.swing.JComboBox<String> comboEscherichia157;
    private javax.swing.JComboBox<String> comboEscherichia157In;
    private javax.swing.JComboBox<String> comboEscherichiaH7;
    private javax.swing.JComboBox<String> comboEscherichiaH7In;
    private javax.swing.JComboBox<String> comboEscherichiaIn;
    private javax.swing.JComboBox<String> comboGermenes;
    private javax.swing.JComboBox<String> comboGermenesIn;
    private javax.swing.JComboBox<String> comboLactobacillus;
    private javax.swing.JComboBox<String> comboLactobacillusIn;
    private javax.swing.JComboBox<String> comboListeria;
    private javax.swing.JComboBox<String> comboListeriaIn;
    private javax.swing.JComboBox<String> comboMohos;
    private javax.swing.JComboBox<String> comboMohosIn;
    private javax.swing.JComboBox<String> comboPerfringens;
    private javax.swing.JComboBox<String> comboPerfringensIn;
    private javax.swing.JComboBox<String> comboSalmonella;
    private javax.swing.JComboBox<String> comboSalmonellaIn;
    private javax.swing.JComboBox<String> comboShigella;
    private javax.swing.JComboBox<String> comboShigellaIn;
    private javax.swing.JComboBox<String> comboStaphilococos;
    private javax.swing.JComboBox<String> comboStaphilococosIn;
    private javax.swing.JComboBox<String> comboSulfito;
    private javax.swing.JComboBox<String> comboSulfitoIn;
    private javax.swing.JComboBox<String> comboVibrio;
    private javax.swing.JComboBox<String> comboVibrioCholerae;
    private javax.swing.JComboBox<String> comboVibrioCholeraeIn;
    private javax.swing.JComboBox<String> comboVibrioIn;
    private javax.swing.JLabel etiquetaBacillus;
    private javax.swing.JLabel etiquetaBacteriasLacticas;
    private javax.swing.JLabel etiquetaCampilobacter;
    private javax.swing.JLabel etiquetaCaracteristicas;
    private javax.swing.JLabel etiquetaColiformesTotales45;
    private javax.swing.JLabel etiquetaColiformesTotalesA30;
    private javax.swing.JLabel etiquetaColiformesTotalesProbable;
    private javax.swing.JLabel etiquetaEnterobacterias;
    private javax.swing.JLabel etiquetaEscherichia;
    private javax.swing.JLabel etiquetaEscherichia157;
    private javax.swing.JLabel etiquetaEscherichiaH7;
    private javax.swing.JLabel etiquetaFecales;
    private javax.swing.JLabel etiquetaGermenes;
    private javax.swing.JLabel etiquetaLactobacillus;
    private javax.swing.JLabel etiquetaListeria;
    private javax.swing.JLabel etiquetaMohos;
    private javax.swing.JLabel etiquetaPerfringens;
    private javax.swing.JLabel etiquetaSalmonella;
    private javax.swing.JLabel etiquetaShigella;
    private javax.swing.JLabel etiquetaStaphilococos;
    private javax.swing.JLabel etiquetaSulfito;
    private javax.swing.JLabel etiquetaTitulo;
    private javax.swing.JLabel etiquetaTotales;
    private javax.swing.JLabel etiquetaVibrio;
    private javax.swing.JLabel etiquetaVibrioCholerae;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel19;
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
    private javax.swing.JPanel jPanel30;
    private javax.swing.JPanel jPanel31;
    private javax.swing.JPanel jPanel32;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JComboBox<String> metodoBacillus;
    private javax.swing.JComboBox<String> metodoBacteriasLacticas;
    private javax.swing.JComboBox<String> metodoCampilobacter;
    private javax.swing.JComboBox<String> metodoCaracteristicas;
    private javax.swing.JComboBox<String> metodoColiformesTotales45;
    private javax.swing.JComboBox<String> metodoColiformesTotalesA30;
    private javax.swing.JComboBox<String> metodoColiformesTotalesProbables;
    private javax.swing.JComboBox<String> metodoEnterobacterias;
    private javax.swing.JComboBox<String> metodoEscherichia;
    private javax.swing.JComboBox<String> metodoEscherichia157;
    private javax.swing.JComboBox<String> metodoEscherichiaH7;
    private javax.swing.JComboBox<String> metodoFecales;
    private javax.swing.JComboBox<String> metodoGermenes;
    private javax.swing.JComboBox<String> metodoLactobacillus;
    private javax.swing.JComboBox<String> metodoListeria;
    private javax.swing.JComboBox<String> metodoMohos;
    private javax.swing.JComboBox<String> metodoPerfringens;
    private javax.swing.JComboBox<String> metodoSalmonella;
    private javax.swing.JComboBox<String> metodoShigella;
    private javax.swing.JComboBox<String> metodoStaphilococos;
    private javax.swing.JComboBox<String> metodoSulfito;
    private javax.swing.JComboBox<String> metodoTotales;
    private javax.swing.JComboBox<String> metodoVibrio;
    private javax.swing.JComboBox<String> metodoVibrioCholerae;
    private javax.swing.JPanel panelBacillus;
    private javax.swing.JPanel panelBacteriasLacticas;
    private javax.swing.JPanel panelCampilobacter;
    private javax.swing.JPanel panelCaracteristicas;
    private javax.swing.JPanel panelColiformesTotales45;
    private javax.swing.JPanel panelColiformesTotalesA30;
    private javax.swing.JPanel panelColiformesTotalesProbables;
    private javax.swing.JPanel panelEnterobacterias;
    private javax.swing.JPanel panelEscherichia;
    private javax.swing.JPanel panelEscherichia157;
    private javax.swing.JPanel panelEscherichiaH7;
    private javax.swing.JPanel panelFecales;
    private javax.swing.JPanel panelGermenes;
    private javax.swing.JPanel panelLactobacillus;
    private javax.swing.JPanel panelListeria;
    private javax.swing.JPanel panelMohos;
    private javax.swing.JPanel panelPerfringens;
    private javax.swing.JPanel panelSalmonella;
    private javax.swing.JPanel panelShigella;
    private javax.swing.JPanel panelStaphilococos;
    private javax.swing.JPanel panelSulfito;
    private javax.swing.JPanel panelTotales;
    private javax.swing.JPanel panelVibrio;
    private javax.swing.JPanel panelVibrioCholerae;


    public void click(Component target, int x, int y) {
        MouseEvent click;
        Point point;
        long time;

        point = new Point(x, y);

        SwingUtilities.convertPointToScreen(point, target);

        time = System.currentTimeMillis();

        click = new MouseEvent(target, MouseEvent.MOUSE_CLICKED, time, 0, x, y, point.x, point.y, 1, false, MouseEvent.BUTTON1);

        target.dispatchEvent(click);
    }

    private MouseEvent click(Component componente) {
        long time = System.currentTimeMillis();
        Point point = new Point();
        point.x = componente.getX();
        point.y = componente.getY();
        MouseEvent me = new MouseEvent(componente, MouseEvent.MOUSE_CLICKED,
                time, 0, componente.getX(), componente.getY(), point.x, point.y, 1, false, MouseEvent.BUTTON1);
        return me;
    }
}
