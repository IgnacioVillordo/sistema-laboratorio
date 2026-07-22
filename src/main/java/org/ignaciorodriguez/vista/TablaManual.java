package org.ignaciorodriguez.vista;

import java.awt.Dimension;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import org.ignaciorodriguez.modelo.Consultas;
import org.ignaciorodriguez.repository.MuestraRepository;
import org.ignaciorodriguez.repository.ResultadoRepository;

public class TablaManual extends javax.swing.JDialog {

    boolean editar, auxEditar;
    int id;
    Consultas c = Consultas.getInstancia();
    String auxTitulo, auxConclusion, procedencia, pdf, auxObservacion;
    Point mousePoint;
    MuestraRepository muestraRepository = new MuestraRepository();
    ResultadoRepository resultadoRepository = new ResultadoRepository();

    public TablaManual(java.awt.Frame parent, boolean modal, boolean editar, int id, String procedencia, String pdf) {
        super(parent, modal);
        this.id = id;
        initComponents();
        setTitle("ID " + id + ". " + muestraRepository.obtenerProcedencia(id) + ". Informe manual");
        int width = getPreferredSize().width;
        int height = (int) (Toolkit.getDefaultToolkit().getScreenSize().height * 0.7);
        jScrollPane81.setPreferredSize(new Dimension(width, height));
        Map<String, String> map = resultadoRepository.recuperarResultadoManual(id);
        this.editar = resultadoRepository.checkearResultadoManual(id);
        this.procedencia = procedencia;
        this.pdf = pdf;
        botonMostrar.setSelected(true);
//        popupMenu.addPopupMenuListener(new PopupMenuListener() {
//            @Override
//            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
//                // Obtenemos el punto exacto en la pantalla
//                Point p = popupMenu.getLocationOnScreen();
//                System.out.println("Esquina superior izquierda en: " + p.x + ", " + p.y);
//            }
//
//            @Override
//            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
//            }
//
//            @Override
//            public void popupMenuCanceled(PopupMenuEvent e) {
//            }
//        });
        addRightClick(determinacion1);
        addRightClick(determinacion2);
        addRightClick(determinacion3);
        addRightClick(determinacion4);
        addRightClick(determinacion5);
        addRightClick(determinacion6);
        addRightClick(determinacion7);
        addRightClick(determinacion8);
        addRightClick(determinacion9);
        addRightClick(determinacion10);
        addRightClick(determinacion11);
        addRightClick(determinacion12);
        addRightClick(determinacion13);
        addRightClick(determinacion14);
        addRightClick(determinacion15);
        addRightClick(determinacion16);
        addRightClick(determinacion17);
        addRightClick(determinacion18);
        addRightClick(determinacion19);
        addRightClick(determinacion20);
        addRightClick(determinacion21);
        addRightClick(determinacion22);
        addRightClick(determinacion23);
        addRightClick(determinacion24);
        addRightClick(determinacion25);
        addRightClick(determinacion26);
        addRightClick(determinacion27);
        addRightClick(determinacion28);
        addRightClick(determinacion29);
        addRightClick(determinacion30);
        addRightClick(determinacion31);
        addRightClick(determinacion32);
        addRightClick(determinacion33);
        addRightClick(determinacion34);
        addRightClick(recuentoObtenido1);
        addRightClick(recuentoObtenido2);
        addRightClick(recuentoObtenido3);
        addRightClick(recuentoObtenido4);
        addRightClick(recuentoObtenido5);
        addRightClick(recuentoObtenido6);
        addRightClick(recuentoObtenido7);
        addRightClick(recuentoObtenido8);
        addRightClick(recuentoObtenido9);
        addRightClick(recuentoObtenido10);
        addRightClick(recuentoObtenido11);
        addRightClick(recuentoObtenido12);
        addRightClick(recuentoObtenido13);
        addRightClick(recuentoObtenido14);
        addRightClick(recuentoObtenido15);
        addRightClick(recuentoObtenido16);
        addRightClick(recuentoObtenido17);
        addRightClick(recuentoObtenido18);
        addRightClick(recuentoObtenido19);
        addRightClick(recuentoObtenido20);
        addRightClick(recuentoObtenido21);
        addRightClick(recuentoObtenido22);
        addRightClick(recuentoObtenido23);
        addRightClick(recuentoObtenido24);
        addRightClick(recuentoObtenido25);
        addRightClick(recuentoObtenido26);
        addRightClick(recuentoObtenido27);
        addRightClick(recuentoObtenido28);
        addRightClick(recuentoObtenido29);
        addRightClick(recuentoObtenido30);
        addRightClick(recuentoObtenido31);
        addRightClick(recuentoObtenido32);
        addRightClick(recuentoObtenido33);
        addRightClick(recuentoObtenido34);
        addRightClick(recuentoNormal1);
        addRightClick(recuentoNormal2);
        addRightClick(recuentoNormal3);
        addRightClick(recuentoNormal4);
        addRightClick(recuentoNormal5);
        addRightClick(recuentoNormal6);
        addRightClick(recuentoNormal7);
        addRightClick(recuentoNormal8);
        addRightClick(recuentoNormal9);
        addRightClick(recuentoNormal10);
        addRightClick(recuentoNormal11);
        addRightClick(recuentoNormal12);
        addRightClick(recuentoNormal13);
        addRightClick(recuentoNormal14);
        addRightClick(recuentoNormal15);
        addRightClick(recuentoNormal16);
        addRightClick(recuentoNormal17);
        addRightClick(recuentoNormal18);
        addRightClick(recuentoNormal19);
        addRightClick(recuentoNormal20);
        addRightClick(recuentoNormal21);
        addRightClick(recuentoNormal22);
        addRightClick(recuentoNormal23);
        addRightClick(recuentoNormal24);
        addRightClick(recuentoNormal25);
        addRightClick(recuentoNormal26);
        addRightClick(recuentoNormal27);
        addRightClick(recuentoNormal28);
        addRightClick(recuentoNormal29);
        addRightClick(recuentoNormal30);
        addRightClick(recuentoNormal31);
        addRightClick(recuentoNormal32);
        addRightClick(recuentoNormal33);
        addRightClick(recuentoNormal34);
        addRightClick(metodo1);
        addRightClick(metodo2);
        addRightClick(metodo3);
        addRightClick(metodo4);
        addRightClick(metodo5);
        addRightClick(metodo6);
        addRightClick(metodo7);
        addRightClick(metodo8);
        addRightClick(metodo9);
        addRightClick(metodo10);
        addRightClick(metodo11);
        addRightClick(metodo12);
        addRightClick(metodo13);
        addRightClick(metodo14);
        addRightClick(metodo15);
        addRightClick(metodo16);
        addRightClick(metodo17);
        addRightClick(metodo18);
        addRightClick(metodo19);
        addRightClick(metodo20);
        addRightClick(metodo21);
        addRightClick(metodo22);
        addRightClick(metodo23);
        addRightClick(metodo24);
        addRightClick(metodo25);
        addRightClick(metodo26);
        addRightClick(metodo27);
        addRightClick(metodo28);
        addRightClick(metodo29);
        addRightClick(metodo30);
        addRightClick(metodo31);
        addRightClick(metodo32);
        addRightClick(metodo33);
        addRightClick(metodo34);
        determinacion1.setComponentPopupMenu(popupMenu);
        determinacion2.setComponentPopupMenu(popupMenu);
        determinacion3.setComponentPopupMenu(popupMenu);
        determinacion4.setComponentPopupMenu(popupMenu);
        determinacion5.setComponentPopupMenu(popupMenu);
        determinacion6.setComponentPopupMenu(popupMenu);
        determinacion7.setComponentPopupMenu(popupMenu);
        determinacion8.setComponentPopupMenu(popupMenu);
        determinacion9.setComponentPopupMenu(popupMenu);
        determinacion10.setComponentPopupMenu(popupMenu);
        determinacion11.setComponentPopupMenu(popupMenu);
        determinacion12.setComponentPopupMenu(popupMenu);
        determinacion13.setComponentPopupMenu(popupMenu);
        determinacion14.setComponentPopupMenu(popupMenu);
        determinacion15.setComponentPopupMenu(popupMenu);
        determinacion16.setComponentPopupMenu(popupMenu);
        determinacion17.setComponentPopupMenu(popupMenu);
        determinacion18.setComponentPopupMenu(popupMenu);
        determinacion19.setComponentPopupMenu(popupMenu);
        determinacion20.setComponentPopupMenu(popupMenu);
        determinacion21.setComponentPopupMenu(popupMenu);
        determinacion22.setComponentPopupMenu(popupMenu);
        determinacion23.setComponentPopupMenu(popupMenu);
        determinacion24.setComponentPopupMenu(popupMenu);
        determinacion25.setComponentPopupMenu(popupMenu);
        determinacion26.setComponentPopupMenu(popupMenu);
        determinacion27.setComponentPopupMenu(popupMenu);
        determinacion28.setComponentPopupMenu(popupMenu);
        determinacion29.setComponentPopupMenu(popupMenu);
        determinacion30.setComponentPopupMenu(popupMenu);
        determinacion31.setComponentPopupMenu(popupMenu);
        determinacion32.setComponentPopupMenu(popupMenu);
        determinacion33.setComponentPopupMenu(popupMenu);
        determinacion34.setComponentPopupMenu(popupMenu);
        recuentoObtenido1.setComponentPopupMenu(popupMenu);
        recuentoObtenido2.setComponentPopupMenu(popupMenu);
        recuentoObtenido3.setComponentPopupMenu(popupMenu);
        recuentoObtenido4.setComponentPopupMenu(popupMenu);
        recuentoObtenido5.setComponentPopupMenu(popupMenu);
        recuentoObtenido6.setComponentPopupMenu(popupMenu);
        recuentoObtenido7.setComponentPopupMenu(popupMenu);
        recuentoObtenido8.setComponentPopupMenu(popupMenu);
        recuentoObtenido9.setComponentPopupMenu(popupMenu);
        recuentoObtenido10.setComponentPopupMenu(popupMenu);
        recuentoObtenido11.setComponentPopupMenu(popupMenu);
        recuentoObtenido12.setComponentPopupMenu(popupMenu);
        recuentoObtenido13.setComponentPopupMenu(popupMenu);
        recuentoObtenido14.setComponentPopupMenu(popupMenu);
        recuentoObtenido15.setComponentPopupMenu(popupMenu);
        recuentoObtenido16.setComponentPopupMenu(popupMenu);
        recuentoObtenido17.setComponentPopupMenu(popupMenu);
        recuentoObtenido18.setComponentPopupMenu(popupMenu);
        recuentoObtenido19.setComponentPopupMenu(popupMenu);
        recuentoObtenido20.setComponentPopupMenu(popupMenu);
        recuentoObtenido21.setComponentPopupMenu(popupMenu);
        recuentoObtenido22.setComponentPopupMenu(popupMenu);
        recuentoObtenido23.setComponentPopupMenu(popupMenu);
        recuentoObtenido24.setComponentPopupMenu(popupMenu);
        recuentoObtenido25.setComponentPopupMenu(popupMenu);
        recuentoObtenido26.setComponentPopupMenu(popupMenu);
        recuentoObtenido27.setComponentPopupMenu(popupMenu);
        recuentoObtenido28.setComponentPopupMenu(popupMenu);
        recuentoObtenido29.setComponentPopupMenu(popupMenu);
        recuentoObtenido30.setComponentPopupMenu(popupMenu);
        recuentoObtenido31.setComponentPopupMenu(popupMenu);
        recuentoObtenido32.setComponentPopupMenu(popupMenu);
        recuentoObtenido33.setComponentPopupMenu(popupMenu);
        recuentoObtenido34.setComponentPopupMenu(popupMenu);
        recuentoNormal1.setComponentPopupMenu(popupMenu);
        recuentoNormal2.setComponentPopupMenu(popupMenu);
        recuentoNormal3.setComponentPopupMenu(popupMenu);
        recuentoNormal4.setComponentPopupMenu(popupMenu);
        recuentoNormal5.setComponentPopupMenu(popupMenu);
        recuentoNormal6.setComponentPopupMenu(popupMenu);
        recuentoNormal7.setComponentPopupMenu(popupMenu);
        recuentoNormal8.setComponentPopupMenu(popupMenu);
        recuentoNormal9.setComponentPopupMenu(popupMenu);
        recuentoNormal10.setComponentPopupMenu(popupMenu);
        recuentoNormal11.setComponentPopupMenu(popupMenu);
        recuentoNormal12.setComponentPopupMenu(popupMenu);
        recuentoNormal13.setComponentPopupMenu(popupMenu);
        recuentoNormal14.setComponentPopupMenu(popupMenu);
        recuentoNormal15.setComponentPopupMenu(popupMenu);
        recuentoNormal16.setComponentPopupMenu(popupMenu);
        recuentoNormal17.setComponentPopupMenu(popupMenu);
        recuentoNormal18.setComponentPopupMenu(popupMenu);
        recuentoNormal19.setComponentPopupMenu(popupMenu);
        recuentoNormal20.setComponentPopupMenu(popupMenu);
        recuentoNormal21.setComponentPopupMenu(popupMenu);
        recuentoNormal22.setComponentPopupMenu(popupMenu);
        recuentoNormal23.setComponentPopupMenu(popupMenu);
        recuentoNormal24.setComponentPopupMenu(popupMenu);
        recuentoNormal25.setComponentPopupMenu(popupMenu);
        recuentoNormal26.setComponentPopupMenu(popupMenu);
        recuentoNormal27.setComponentPopupMenu(popupMenu);
        recuentoNormal28.setComponentPopupMenu(popupMenu);
        recuentoNormal29.setComponentPopupMenu(popupMenu);
        recuentoNormal30.setComponentPopupMenu(popupMenu);
        recuentoNormal31.setComponentPopupMenu(popupMenu);
        recuentoNormal32.setComponentPopupMenu(popupMenu);
        recuentoNormal33.setComponentPopupMenu(popupMenu);
        recuentoNormal34.setComponentPopupMenu(popupMenu);
        metodo1.setComponentPopupMenu(popupMenu);
        metodo2.setComponentPopupMenu(popupMenu);
        metodo3.setComponentPopupMenu(popupMenu);
        metodo4.setComponentPopupMenu(popupMenu);
        metodo5.setComponentPopupMenu(popupMenu);
        metodo6.setComponentPopupMenu(popupMenu);
        metodo7.setComponentPopupMenu(popupMenu);
        metodo8.setComponentPopupMenu(popupMenu);
        metodo9.setComponentPopupMenu(popupMenu);
        metodo10.setComponentPopupMenu(popupMenu);
        metodo11.setComponentPopupMenu(popupMenu);
        metodo12.setComponentPopupMenu(popupMenu);
        metodo13.setComponentPopupMenu(popupMenu);
        metodo14.setComponentPopupMenu(popupMenu);
        metodo15.setComponentPopupMenu(popupMenu);
        metodo16.setComponentPopupMenu(popupMenu);
        metodo17.setComponentPopupMenu(popupMenu);
        metodo18.setComponentPopupMenu(popupMenu);
        metodo19.setComponentPopupMenu(popupMenu);
        metodo20.setComponentPopupMenu(popupMenu);
        metodo21.setComponentPopupMenu(popupMenu);
        metodo22.setComponentPopupMenu(popupMenu);
        metodo23.setComponentPopupMenu(popupMenu);
        metodo24.setComponentPopupMenu(popupMenu);
        metodo25.setComponentPopupMenu(popupMenu);
        metodo26.setComponentPopupMenu(popupMenu);
        metodo27.setComponentPopupMenu(popupMenu);
        metodo28.setComponentPopupMenu(popupMenu);
        metodo29.setComponentPopupMenu(popupMenu);
        metodo30.setComponentPopupMenu(popupMenu);
        metodo31.setComponentPopupMenu(popupMenu);
        metodo32.setComponentPopupMenu(popupMenu);
        metodo33.setComponentPopupMenu(popupMenu);
        metodo34.setComponentPopupMenu(popupMenu);
        if (this.editar) {
            // DETERMINACIONES (1-34)
            determinacion1.setText(map.get("determinacion1"));
            determinacion2.setText(map.get("determinacion2"));
            determinacion3.setText(map.get("determinacion3"));
            determinacion4.setText(map.get("determinacion4"));
            determinacion5.setText(map.get("determinacion5"));
            determinacion6.setText(map.get("determinacion6"));
            determinacion7.setText(map.get("determinacion7"));
            determinacion8.setText(map.get("determinacion8"));
            determinacion9.setText(map.get("determinacion9"));
            determinacion10.setText(map.get("determinacion10"));
            determinacion11.setText(map.get("determinacion11"));
            determinacion12.setText(map.get("determinacion12"));
            determinacion13.setText(map.get("determinacion13"));
            determinacion14.setText(map.get("determinacion14"));
            determinacion15.setText(map.get("determinacion15"));
            determinacion16.setText(map.get("determinacion16"));
            determinacion17.setText(map.get("determinacion17"));
            determinacion18.setText(map.get("determinacion18"));
            determinacion19.setText(map.get("determinacion19"));
            determinacion20.setText(map.get("determinacion20"));
            determinacion21.setText(map.get("determinacion21"));
            determinacion22.setText(map.get("determinacion22"));
            determinacion23.setText(map.get("determinacion23"));
            determinacion24.setText(map.get("determinacion24"));
            determinacion25.setText(map.get("determinacion25"));
            determinacion26.setText(map.get("determinacion26"));
            determinacion27.setText(map.get("determinacion27"));
            determinacion28.setText(map.get("determinacion28"));
            determinacion29.setText(map.get("determinacion29"));
            determinacion30.setText(map.get("determinacion30"));
            determinacion31.setText(map.get("determinacion31"));
            determinacion32.setText(map.get("determinacion32"));
            determinacion33.setText(map.get("determinacion33"));
            determinacion34.setText(map.get("determinacion34"));

// RECUENTOS OBTENIDOS (1-34)
            recuentoObtenido1.setText(map.get("recuentoObtenido1"));
            recuentoObtenido2.setText(map.get("recuentoObtenido2"));
            recuentoObtenido3.setText(map.get("recuentoObtenido3"));
            recuentoObtenido4.setText(map.get("recuentoObtenido4"));
            recuentoObtenido5.setText(map.get("recuentoObtenido5"));
            recuentoObtenido6.setText(map.get("recuentoObtenido6"));
            recuentoObtenido7.setText(map.get("recuentoObtenido7"));
            recuentoObtenido8.setText(map.get("recuentoObtenido8"));
            recuentoObtenido9.setText(map.get("recuentoObtenido9"));
            recuentoObtenido10.setText(map.get("recuentoObtenido10"));
            recuentoObtenido11.setText(map.get("recuentoObtenido11"));
            recuentoObtenido12.setText(map.get("recuentoObtenido12"));
            recuentoObtenido13.setText(map.get("recuentoObtenido13"));
            recuentoObtenido14.setText(map.get("recuentoObtenido14"));
            recuentoObtenido15.setText(map.get("recuentoObtenido15"));
            recuentoObtenido16.setText(map.get("recuentoObtenido16"));
            recuentoObtenido17.setText(map.get("recuentoObtenido17"));
            recuentoObtenido18.setText(map.get("recuentoObtenido18"));
            recuentoObtenido19.setText(map.get("recuentoObtenido19"));
            recuentoObtenido20.setText(map.get("recuentoObtenido20"));
            recuentoObtenido21.setText(map.get("recuentoObtenido21"));
            recuentoObtenido22.setText(map.get("recuentoObtenido22"));
            recuentoObtenido23.setText(map.get("recuentoObtenido23"));
            recuentoObtenido24.setText(map.get("recuentoObtenido24"));
            recuentoObtenido25.setText(map.get("recuentoObtenido25"));
            recuentoObtenido26.setText(map.get("recuentoObtenido26"));
            recuentoObtenido27.setText(map.get("recuentoObtenido27"));
            recuentoObtenido28.setText(map.get("recuentoObtenido28"));
            recuentoObtenido29.setText(map.get("recuentoObtenido29"));
            recuentoObtenido30.setText(map.get("recuentoObtenido30"));
            recuentoObtenido31.setText(map.get("recuentoObtenido31"));
            recuentoObtenido32.setText(map.get("recuentoObtenido32"));
            recuentoObtenido33.setText(map.get("recuentoObtenido33"));
            recuentoObtenido34.setText(map.get("recuentoObtenido34"));

// RECUENTOS NORMALES (1-34)
            recuentoNormal1.setText(map.get("recuentoNormal1"));
            recuentoNormal2.setText(map.get("recuentoNormal2"));
            recuentoNormal3.setText(map.get("recuentoNormal3"));
            recuentoNormal4.setText(map.get("recuentoNormal4"));
            recuentoNormal5.setText(map.get("recuentoNormal5"));
            recuentoNormal6.setText(map.get("recuentoNormal6"));
            recuentoNormal7.setText(map.get("recuentoNormal7"));
            recuentoNormal8.setText(map.get("recuentoNormal8"));
            recuentoNormal9.setText(map.get("recuentoNormal9"));
            recuentoNormal10.setText(map.get("recuentoNormal10"));
            recuentoNormal11.setText(map.get("recuentoNormal11"));
            recuentoNormal12.setText(map.get("recuentoNormal12"));
            recuentoNormal13.setText(map.get("recuentoNormal13"));
            recuentoNormal14.setText(map.get("recuentoNormal14"));
            recuentoNormal15.setText(map.get("recuentoNormal15"));
            recuentoNormal16.setText(map.get("recuentoNormal16"));
            recuentoNormal17.setText(map.get("recuentoNormal17"));
            recuentoNormal18.setText(map.get("recuentoNormal18"));
            recuentoNormal19.setText(map.get("recuentoNormal19"));
            recuentoNormal20.setText(map.get("recuentoNormal20"));
            recuentoNormal21.setText(map.get("recuentoNormal21"));
            recuentoNormal22.setText(map.get("recuentoNormal22"));
            recuentoNormal23.setText(map.get("recuentoNormal23"));
            recuentoNormal24.setText(map.get("recuentoNormal24"));
            recuentoNormal25.setText(map.get("recuentoNormal25"));
            recuentoNormal26.setText(map.get("recuentoNormal26"));
            recuentoNormal27.setText(map.get("recuentoNormal27"));
            recuentoNormal28.setText(map.get("recuentoNormal28"));
            recuentoNormal29.setText(map.get("recuentoNormal29"));
            recuentoNormal30.setText(map.get("recuentoNormal30"));
            recuentoNormal31.setText(map.get("recuentoNormal31"));
            recuentoNormal32.setText(map.get("recuentoNormal32"));
            recuentoNormal33.setText(map.get("recuentoNormal33"));
            recuentoNormal34.setText(map.get("recuentoNormal34"));

// MÉTODOS (1-34)
            metodo1.setText(map.get("metodo1"));
            metodo2.setText(map.get("metodo2"));
            metodo3.setText(map.get("metodo3"));
            metodo4.setText(map.get("metodo4"));
            metodo5.setText(map.get("metodo5"));
            metodo6.setText(map.get("metodo6"));
            metodo7.setText(map.get("metodo7"));
            metodo8.setText(map.get("metodo8"));
            metodo9.setText(map.get("metodo9"));
            metodo10.setText(map.get("metodo10"));
            metodo11.setText(map.get("metodo11"));
            metodo12.setText(map.get("metodo12"));
            metodo13.setText(map.get("metodo13"));
            metodo14.setText(map.get("metodo14"));
            metodo15.setText(map.get("metodo15"));
            metodo16.setText(map.get("metodo16"));
            metodo17.setText(map.get("metodo17"));
            metodo18.setText(map.get("metodo18"));
            metodo19.setText(map.get("metodo19"));
            metodo20.setText(map.get("metodo20"));
            metodo21.setText(map.get("metodo21"));
            metodo22.setText(map.get("metodo22"));
            metodo23.setText(map.get("metodo23"));
            metodo24.setText(map.get("metodo24"));
            metodo25.setText(map.get("metodo25"));
            metodo26.setText(map.get("metodo26"));
            metodo27.setText(map.get("metodo27"));
            metodo28.setText(map.get("metodo28"));
            metodo29.setText(map.get("metodo29"));
            metodo30.setText(map.get("metodo30"));
            metodo31.setText(map.get("metodo31"));
            metodo32.setText(map.get("metodo32"));
            metodo33.setText(map.get("metodo33"));
            metodo34.setText(map.get("metodo34"));
            auxTitulo = map.get("titulo");
            auxConclusion = muestraRepository.recuperarConclusion(id);
            cajaFechaAnalisis.setDate(muestraRepository.recuperarFechaAnalisis(id));
            if (resultadoRepository.consultarMostrar(id)) {
                botonMostrar.setSelected(true);
            } else {
                botonNoMostrar.setSelected(true);
            }
            auxObservacion = muestraRepository.recuperarObservaciones(id);
            if (auxConclusion != null) {
                if (auxConclusion.contains("debido a")) {
                    radioMal.setSelected(true);
                    auxConclusion = auxConclusion.substring(125, auxConclusion.length() - 1);
                } else if (auxConclusion.equals("En las determinaciones realizadas "
                        + "la muestra cumple con el art. 982 del Código Alimentario Argentino (ley 18284).")) {
                    radioBien.setSelected(true);
                } else {
                    radioManual.setSelected(true);
                }
            }
        }
        this.pack();
        setLocationRelativeTo(null);

    }

    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        popupMenu = new javax.swing.JPopupMenu();
        itemCopiar = new javax.swing.JMenuItem();
        itemPegar = new javax.swing.JMenuItem();
        jScrollPane81 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        determinacion1 = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        recuentoObtenido1 = new javax.swing.JTextArea();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        recuentoNormal1 = new javax.swing.JTextArea();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        metodo1 = new javax.swing.JTextArea();
        jScrollPane5 = new javax.swing.JScrollPane();
        metodo2 = new javax.swing.JTextArea();
        jScrollPane6 = new javax.swing.JScrollPane();
        recuentoNormal2 = new javax.swing.JTextArea();
        jScrollPane7 = new javax.swing.JScrollPane();
        recuentoObtenido2 = new javax.swing.JTextArea();
        jScrollPane8 = new javax.swing.JScrollPane();
        determinacion2 = new javax.swing.JTextArea();
        jScrollPane9 = new javax.swing.JScrollPane();
        metodo3 = new javax.swing.JTextArea();
        jScrollPane10 = new javax.swing.JScrollPane();
        recuentoNormal3 = new javax.swing.JTextArea();
        jScrollPane11 = new javax.swing.JScrollPane();
        recuentoObtenido3 = new javax.swing.JTextArea();
        jScrollPane12 = new javax.swing.JScrollPane();
        determinacion3 = new javax.swing.JTextArea();
        jScrollPane13 = new javax.swing.JScrollPane();
        metodo4 = new javax.swing.JTextArea();
        jScrollPane14 = new javax.swing.JScrollPane();
        recuentoNormal4 = new javax.swing.JTextArea();
        jScrollPane15 = new javax.swing.JScrollPane();
        recuentoObtenido4 = new javax.swing.JTextArea();
        jScrollPane16 = new javax.swing.JScrollPane();
        determinacion4 = new javax.swing.JTextArea();
        jScrollPane17 = new javax.swing.JScrollPane();
        metodo5 = new javax.swing.JTextArea();
        jScrollPane18 = new javax.swing.JScrollPane();
        recuentoNormal5 = new javax.swing.JTextArea();
        jScrollPane19 = new javax.swing.JScrollPane();
        recuentoObtenido5 = new javax.swing.JTextArea();
        jScrollPane20 = new javax.swing.JScrollPane();
        determinacion5 = new javax.swing.JTextArea();
        jScrollPane21 = new javax.swing.JScrollPane();
        metodo6 = new javax.swing.JTextArea();
        jScrollPane22 = new javax.swing.JScrollPane();
        recuentoNormal6 = new javax.swing.JTextArea();
        jScrollPane23 = new javax.swing.JScrollPane();
        recuentoObtenido6 = new javax.swing.JTextArea();
        jScrollPane24 = new javax.swing.JScrollPane();
        determinacion6 = new javax.swing.JTextArea();
        jScrollPane25 = new javax.swing.JScrollPane();
        metodo7 = new javax.swing.JTextArea();
        jScrollPane26 = new javax.swing.JScrollPane();
        recuentoNormal7 = new javax.swing.JTextArea();
        jScrollPane27 = new javax.swing.JScrollPane();
        recuentoObtenido7 = new javax.swing.JTextArea();
        jScrollPane28 = new javax.swing.JScrollPane();
        determinacion7 = new javax.swing.JTextArea();
        jScrollPane29 = new javax.swing.JScrollPane();
        metodo8 = new javax.swing.JTextArea();
        jScrollPane30 = new javax.swing.JScrollPane();
        recuentoNormal8 = new javax.swing.JTextArea();
        jScrollPane31 = new javax.swing.JScrollPane();
        recuentoObtenido8 = new javax.swing.JTextArea();
        jScrollPane32 = new javax.swing.JScrollPane();
        determinacion8 = new javax.swing.JTextArea();
        jScrollPane33 = new javax.swing.JScrollPane();
        metodo9 = new javax.swing.JTextArea();
        jScrollPane34 = new javax.swing.JScrollPane();
        recuentoNormal9 = new javax.swing.JTextArea();
        jScrollPane35 = new javax.swing.JScrollPane();
        recuentoObtenido9 = new javax.swing.JTextArea();
        jScrollPane36 = new javax.swing.JScrollPane();
        determinacion9 = new javax.swing.JTextArea();
        jScrollPane37 = new javax.swing.JScrollPane();
        metodo10 = new javax.swing.JTextArea();
        jScrollPane38 = new javax.swing.JScrollPane();
        recuentoNormal10 = new javax.swing.JTextArea();
        jScrollPane39 = new javax.swing.JScrollPane();
        recuentoObtenido10 = new javax.swing.JTextArea();
        jScrollPane40 = new javax.swing.JScrollPane();
        determinacion10 = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();
        radioMal = new javax.swing.JRadioButton();
        radioBien = new javax.swing.JRadioButton();
        cajaFechaAnalisis = new com.toedter.calendar.JDateChooser();
        jLabel8 = new javax.swing.JLabel();
        radioManual = new javax.swing.JRadioButton();
        jScrollPane41 = new javax.swing.JScrollPane();
        metodo11 = new javax.swing.JTextArea();
        jScrollPane42 = new javax.swing.JScrollPane();
        metodo12 = new javax.swing.JTextArea();
        jScrollPane43 = new javax.swing.JScrollPane();
        recuentoNormal12 = new javax.swing.JTextArea();
        jScrollPane44 = new javax.swing.JScrollPane();
        recuentoObtenido11 = new javax.swing.JTextArea();
        jScrollPane45 = new javax.swing.JScrollPane();
        determinacion11 = new javax.swing.JTextArea();
        jScrollPane46 = new javax.swing.JScrollPane();
        metodo13 = new javax.swing.JTextArea();
        jScrollPane47 = new javax.swing.JScrollPane();
        recuentoNormal13 = new javax.swing.JTextArea();
        jScrollPane48 = new javax.swing.JScrollPane();
        recuentoObtenido12 = new javax.swing.JTextArea();
        jScrollPane49 = new javax.swing.JScrollPane();
        determinacion12 = new javax.swing.JTextArea();
        jScrollPane50 = new javax.swing.JScrollPane();
        metodo14 = new javax.swing.JTextArea();
        jScrollPane51 = new javax.swing.JScrollPane();
        recuentoNormal14 = new javax.swing.JTextArea();
        jScrollPane52 = new javax.swing.JScrollPane();
        recuentoObtenido13 = new javax.swing.JTextArea();
        jScrollPane53 = new javax.swing.JScrollPane();
        determinacion13 = new javax.swing.JTextArea();
        jScrollPane54 = new javax.swing.JScrollPane();
        metodo15 = new javax.swing.JTextArea();
        jScrollPane55 = new javax.swing.JScrollPane();
        recuentoNormal15 = new javax.swing.JTextArea();
        jScrollPane56 = new javax.swing.JScrollPane();
        recuentoObtenido14 = new javax.swing.JTextArea();
        jScrollPane57 = new javax.swing.JScrollPane();
        determinacion14 = new javax.swing.JTextArea();
        jScrollPane58 = new javax.swing.JScrollPane();
        metodo16 = new javax.swing.JTextArea();
        jScrollPane59 = new javax.swing.JScrollPane();
        recuentoNormal16 = new javax.swing.JTextArea();
        jScrollPane60 = new javax.swing.JScrollPane();
        recuentoObtenido15 = new javax.swing.JTextArea();
        jScrollPane61 = new javax.swing.JScrollPane();
        determinacion15 = new javax.swing.JTextArea();
        jScrollPane62 = new javax.swing.JScrollPane();
        metodo17 = new javax.swing.JTextArea();
        jScrollPane63 = new javax.swing.JScrollPane();
        recuentoNormal17 = new javax.swing.JTextArea();
        jScrollPane64 = new javax.swing.JScrollPane();
        recuentoObtenido16 = new javax.swing.JTextArea();
        jScrollPane65 = new javax.swing.JScrollPane();
        determinacion16 = new javax.swing.JTextArea();
        jScrollPane66 = new javax.swing.JScrollPane();
        metodo18 = new javax.swing.JTextArea();
        jScrollPane67 = new javax.swing.JScrollPane();
        recuentoNormal18 = new javax.swing.JTextArea();
        jScrollPane68 = new javax.swing.JScrollPane();
        recuentoObtenido17 = new javax.swing.JTextArea();
        jScrollPane69 = new javax.swing.JScrollPane();
        determinacion17 = new javax.swing.JTextArea();
        jScrollPane70 = new javax.swing.JScrollPane();
        metodo19 = new javax.swing.JTextArea();
        jScrollPane71 = new javax.swing.JScrollPane();
        recuentoNormal19 = new javax.swing.JTextArea();
        jScrollPane73 = new javax.swing.JScrollPane();
        determinacion18 = new javax.swing.JTextArea();
        jScrollPane76 = new javax.swing.JScrollPane();
        recuentoObtenido19 = new javax.swing.JTextArea();
        jScrollPane77 = new javax.swing.JScrollPane();
        determinacion19 = new javax.swing.JTextArea();
        jScrollPane82 = new javax.swing.JScrollPane();
        recuentoObtenido18 = new javax.swing.JTextArea();
        jScrollPane72 = new javax.swing.JScrollPane();
        recuentoNormal11 = new javax.swing.JTextArea();
        botonMostrar = new javax.swing.JRadioButton();
        botonNoMostrar = new javax.swing.JRadioButton();
        jScrollPane78 = new javax.swing.JScrollPane();
        recuentoObtenido20 = new javax.swing.JTextArea();
        jScrollPane74 = new javax.swing.JScrollPane();
        recuentoNormal20 = new javax.swing.JTextArea();
        jScrollPane79 = new javax.swing.JScrollPane();
        determinacion20 = new javax.swing.JTextArea();
        jScrollPane75 = new javax.swing.JScrollPane();
        metodo20 = new javax.swing.JTextArea();
        jScrollPane80 = new javax.swing.JScrollPane();
        recuentoObtenido21 = new javax.swing.JTextArea();
        jScrollPane83 = new javax.swing.JScrollPane();
        recuentoNormal21 = new javax.swing.JTextArea();
        jScrollPane84 = new javax.swing.JScrollPane();
        determinacion21 = new javax.swing.JTextArea();
        jScrollPane85 = new javax.swing.JScrollPane();
        metodo21 = new javax.swing.JTextArea();
        jScrollPane86 = new javax.swing.JScrollPane();
        recuentoObtenido22 = new javax.swing.JTextArea();
        jScrollPane87 = new javax.swing.JScrollPane();
        recuentoNormal22 = new javax.swing.JTextArea();
        jScrollPane88 = new javax.swing.JScrollPane();
        determinacion22 = new javax.swing.JTextArea();
        jScrollPane89 = new javax.swing.JScrollPane();
        metodo22 = new javax.swing.JTextArea();
        jScrollPane90 = new javax.swing.JScrollPane();
        recuentoObtenido23 = new javax.swing.JTextArea();
        jScrollPane91 = new javax.swing.JScrollPane();
        recuentoNormal23 = new javax.swing.JTextArea();
        jScrollPane92 = new javax.swing.JScrollPane();
        determinacion23 = new javax.swing.JTextArea();
        jScrollPane93 = new javax.swing.JScrollPane();
        metodo23 = new javax.swing.JTextArea();
        jScrollPane94 = new javax.swing.JScrollPane();
        recuentoObtenido24 = new javax.swing.JTextArea();
        jScrollPane95 = new javax.swing.JScrollPane();
        recuentoNormal24 = new javax.swing.JTextArea();
        jScrollPane96 = new javax.swing.JScrollPane();
        determinacion24 = new javax.swing.JTextArea();
        jScrollPane97 = new javax.swing.JScrollPane();
        metodo24 = new javax.swing.JTextArea();
        jScrollPane98 = new javax.swing.JScrollPane();
        recuentoObtenido25 = new javax.swing.JTextArea();
        jScrollPane99 = new javax.swing.JScrollPane();
        recuentoNormal25 = new javax.swing.JTextArea();
        jScrollPane100 = new javax.swing.JScrollPane();
        determinacion25 = new javax.swing.JTextArea();
        jScrollPane101 = new javax.swing.JScrollPane();
        metodo25 = new javax.swing.JTextArea();
        jScrollPane102 = new javax.swing.JScrollPane();
        recuentoObtenido26 = new javax.swing.JTextArea();
        jScrollPane103 = new javax.swing.JScrollPane();
        recuentoNormal26 = new javax.swing.JTextArea();
        jScrollPane104 = new javax.swing.JScrollPane();
        determinacion26 = new javax.swing.JTextArea();
        jScrollPane105 = new javax.swing.JScrollPane();
        metodo26 = new javax.swing.JTextArea();
        jScrollPane106 = new javax.swing.JScrollPane();
        recuentoObtenido27 = new javax.swing.JTextArea();
        jScrollPane107 = new javax.swing.JScrollPane();
        recuentoNormal27 = new javax.swing.JTextArea();
        jScrollPane108 = new javax.swing.JScrollPane();
        determinacion27 = new javax.swing.JTextArea();
        jScrollPane109 = new javax.swing.JScrollPane();
        metodo27 = new javax.swing.JTextArea();
        jScrollPane110 = new javax.swing.JScrollPane();
        recuentoObtenido28 = new javax.swing.JTextArea();
        jScrollPane111 = new javax.swing.JScrollPane();
        recuentoNormal28 = new javax.swing.JTextArea();
        jScrollPane112 = new javax.swing.JScrollPane();
        determinacion28 = new javax.swing.JTextArea();
        jScrollPane113 = new javax.swing.JScrollPane();
        metodo28 = new javax.swing.JTextArea();
        jScrollPane114 = new javax.swing.JScrollPane();
        recuentoObtenido29 = new javax.swing.JTextArea();
        jScrollPane115 = new javax.swing.JScrollPane();
        recuentoNormal29 = new javax.swing.JTextArea();
        jScrollPane116 = new javax.swing.JScrollPane();
        determinacion29 = new javax.swing.JTextArea();
        jScrollPane117 = new javax.swing.JScrollPane();
        metodo29 = new javax.swing.JTextArea();
        jScrollPane118 = new javax.swing.JScrollPane();
        recuentoObtenido30 = new javax.swing.JTextArea();
        jScrollPane119 = new javax.swing.JScrollPane();
        recuentoNormal30 = new javax.swing.JTextArea();
        jScrollPane120 = new javax.swing.JScrollPane();
        determinacion30 = new javax.swing.JTextArea();
        jScrollPane121 = new javax.swing.JScrollPane();
        metodo30 = new javax.swing.JTextArea();
        jScrollPane122 = new javax.swing.JScrollPane();
        recuentoObtenido31 = new javax.swing.JTextArea();
        jScrollPane123 = new javax.swing.JScrollPane();
        recuentoNormal31 = new javax.swing.JTextArea();
        jScrollPane124 = new javax.swing.JScrollPane();
        determinacion31 = new javax.swing.JTextArea();
        jScrollPane125 = new javax.swing.JScrollPane();
        metodo31 = new javax.swing.JTextArea();
        jScrollPane126 = new javax.swing.JScrollPane();
        recuentoObtenido32 = new javax.swing.JTextArea();
        jScrollPane127 = new javax.swing.JScrollPane();
        recuentoNormal32 = new javax.swing.JTextArea();
        jScrollPane128 = new javax.swing.JScrollPane();
        determinacion32 = new javax.swing.JTextArea();
        jScrollPane129 = new javax.swing.JScrollPane();
        metodo32 = new javax.swing.JTextArea();
        jScrollPane130 = new javax.swing.JScrollPane();
        recuentoObtenido33 = new javax.swing.JTextArea();
        jScrollPane131 = new javax.swing.JScrollPane();
        recuentoNormal33 = new javax.swing.JTextArea();
        jScrollPane132 = new javax.swing.JScrollPane();
        determinacion33 = new javax.swing.JTextArea();
        jScrollPane133 = new javax.swing.JScrollPane();
        metodo33 = new javax.swing.JTextArea();
        jScrollPane134 = new javax.swing.JScrollPane();
        recuentoObtenido34 = new javax.swing.JTextArea();
        jScrollPane135 = new javax.swing.JScrollPane();
        recuentoNormal34 = new javax.swing.JTextArea();
        jScrollPane136 = new javax.swing.JScrollPane();
        determinacion34 = new javax.swing.JTextArea();
        jScrollPane137 = new javax.swing.JScrollPane();
        metodo34 = new javax.swing.JTextArea();

        itemCopiar.setText("Copiar");
        itemCopiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemCopiarActionPerformed(evt);
            }
        });
        popupMenu.add(itemCopiar);

        itemPegar.setText("Pegar");
        itemPegar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemPegarActionPerformed(evt);
            }
        });
        popupMenu.add(itemPegar);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setLayout(new java.awt.GridBagLayout());

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setMaximumSize(new java.awt.Dimension(200, 50));
        jScrollPane1.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane1.setPreferredSize(new java.awt.Dimension(200, 35));

        determinacion1.setColumns(10);
        determinacion1.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        determinacion1.setLineWrap(true);
        determinacion1.setRows(5);
        jScrollPane1.setViewportView(determinacion1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(0, 30, 0, 0);
        jPanel1.add(jScrollPane1, gridBagConstraints);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("DETERMINACIONES");
        jLabel2.setMaximumSize(new java.awt.Dimension(200, 25));
        jLabel2.setMinimumSize(new java.awt.Dimension(200, 25));
        jLabel2.setPreferredSize(new java.awt.Dimension(200, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        jPanel1.add(jLabel2, gridBagConstraints);

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("RESULTADO OBTENIDO");
        jLabel3.setMaximumSize(new java.awt.Dimension(200, 25));
        jLabel3.setMinimumSize(new java.awt.Dimension(200, 25));
        jLabel3.setPreferredSize(new java.awt.Dimension(200, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        jPanel1.add(jLabel3, gridBagConstraints);

        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane2.setMaximumSize(new java.awt.Dimension(200, 50));
        jScrollPane2.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane2.setPreferredSize(new java.awt.Dimension(200, 35));

        recuentoObtenido1.setColumns(10);
        recuentoObtenido1.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        recuentoObtenido1.setLineWrap(true);
        recuentoObtenido1.setRows(5);
        jScrollPane2.setViewportView(recuentoObtenido1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        jPanel1.add(jScrollPane2, gridBagConstraints);

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("RESULTADO NORMAL");
        jLabel4.setMaximumSize(new java.awt.Dimension(200, 25));
        jLabel4.setMinimumSize(new java.awt.Dimension(200, 25));
        jLabel4.setPreferredSize(new java.awt.Dimension(200, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        jPanel1.add(jLabel4, gridBagConstraints);

        jScrollPane3.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane3.setMaximumSize(new java.awt.Dimension(200, 50));
        jScrollPane3.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane3.setPreferredSize(new java.awt.Dimension(200, 35));

        recuentoNormal1.setColumns(10);
        recuentoNormal1.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        recuentoNormal1.setLineWrap(true);
        recuentoNormal1.setRows(5);
        jScrollPane3.setViewportView(recuentoNormal1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        jPanel1.add(jScrollPane3, gridBagConstraints);

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("MÉTODO");
        jLabel5.setMaximumSize(new java.awt.Dimension(200, 25));
        jLabel5.setMinimumSize(new java.awt.Dimension(200, 25));
        jLabel5.setPreferredSize(new java.awt.Dimension(200, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        jPanel1.add(jLabel5, gridBagConstraints);

        jScrollPane4.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane4.setMaximumSize(new java.awt.Dimension(200, 50));
        jScrollPane4.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane4.setPreferredSize(new java.awt.Dimension(200, 35));

        metodo1.setColumns(10);
        metodo1.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        metodo1.setLineWrap(true);
        metodo1.setRows(5);
        jScrollPane4.setViewportView(metodo1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 30);
        jPanel1.add(jScrollPane4, gridBagConstraints);

        jScrollPane5.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane5.setMaximumSize(new java.awt.Dimension(200, 50));
        jScrollPane5.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane5.setPreferredSize(new java.awt.Dimension(200, 35));

        metodo2.setColumns(10);
        metodo2.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        metodo2.setLineWrap(true);
        metodo2.setRows(5);
        jScrollPane5.setViewportView(metodo2);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 30);
        jPanel1.add(jScrollPane5, gridBagConstraints);

        jScrollPane6.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane6.setMaximumSize(new java.awt.Dimension(200, 50));
        jScrollPane6.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane6.setPreferredSize(new java.awt.Dimension(200, 35));

        recuentoNormal2.setColumns(10);
        recuentoNormal2.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        recuentoNormal2.setLineWrap(true);
        recuentoNormal2.setRows(5);
        jScrollPane6.setViewportView(recuentoNormal2);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        jPanel1.add(jScrollPane6, gridBagConstraints);

        jScrollPane7.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane7.setMaximumSize(new java.awt.Dimension(200, 50));
        jScrollPane7.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane7.setPreferredSize(new java.awt.Dimension(200, 35));

        recuentoObtenido2.setColumns(10);
        recuentoObtenido2.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        recuentoObtenido2.setLineWrap(true);
        recuentoObtenido2.setRows(5);
        jScrollPane7.setViewportView(recuentoObtenido2);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        jPanel1.add(jScrollPane7, gridBagConstraints);

        jScrollPane8.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane8.setMaximumSize(new java.awt.Dimension(200, 50));
        jScrollPane8.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane8.setPreferredSize(new java.awt.Dimension(200, 35));

        determinacion2.setColumns(10);
        determinacion2.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        determinacion2.setLineWrap(true);
        determinacion2.setRows(5);
        jScrollPane8.setViewportView(determinacion2);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(0, 30, 0, 0);
        jPanel1.add(jScrollPane8, gridBagConstraints);

        jScrollPane9.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane9.setMaximumSize(new java.awt.Dimension(200, 50));
        jScrollPane9.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane9.setPreferredSize(new java.awt.Dimension(200, 35));

        metodo3.setColumns(10);
        metodo3.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        metodo3.setLineWrap(true);
        metodo3.setRows(5);
        jScrollPane9.setViewportView(metodo3);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 30);
        jPanel1.add(jScrollPane9, gridBagConstraints);

        jScrollPane10.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane10.setMaximumSize(new java.awt.Dimension(200, 50));
        jScrollPane10.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane10.setPreferredSize(new java.awt.Dimension(200, 35));

        recuentoNormal3.setColumns(10);
        recuentoNormal3.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        recuentoNormal3.setLineWrap(true);
        recuentoNormal3.setRows(5);
        jScrollPane10.setViewportView(recuentoNormal3);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        jPanel1.add(jScrollPane10, gridBagConstraints);

        jScrollPane11.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane11.setMaximumSize(new java.awt.Dimension(200, 50));
        jScrollPane11.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane11.setPreferredSize(new java.awt.Dimension(200, 35));

        recuentoObtenido3.setColumns(10);
        recuentoObtenido3.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        recuentoObtenido3.setLineWrap(true);
        recuentoObtenido3.setRows(5);
        jScrollPane11.setViewportView(recuentoObtenido3);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        jPanel1.add(jScrollPane11, gridBagConstraints);

        jScrollPane12.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane12.setMaximumSize(new java.awt.Dimension(200, 50));
        jScrollPane12.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane12.setPreferredSize(new java.awt.Dimension(200, 35));

        determinacion3.setColumns(10);
        determinacion3.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        determinacion3.setLineWrap(true);
        determinacion3.setRows(5);
        jScrollPane12.setViewportView(determinacion3);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.insets = new java.awt.Insets(0, 30, 0, 0);
        jPanel1.add(jScrollPane12, gridBagConstraints);

        jScrollPane13.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane13.setMaximumSize(new java.awt.Dimension(200, 50));
        jScrollPane13.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane13.setPreferredSize(new java.awt.Dimension(200, 35));

        metodo4.setColumns(10);
        metodo4.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        metodo4.setLineWrap(true);
        metodo4.setRows(5);
        jScrollPane13.setViewportView(metodo4);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 30);
        jPanel1.add(jScrollPane13, gridBagConstraints);

        jScrollPane14.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane14.setMaximumSize(new java.awt.Dimension(200, 50));
        jScrollPane14.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane14.setPreferredSize(new java.awt.Dimension(200, 35));

        recuentoNormal4.setColumns(10);
        recuentoNormal4.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        recuentoNormal4.setLineWrap(true);
        recuentoNormal4.setRows(5);
        jScrollPane14.setViewportView(recuentoNormal4);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 5;
        jPanel1.add(jScrollPane14, gridBagConstraints);

        jScrollPane15.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane15.setMaximumSize(new java.awt.Dimension(200, 50));
        jScrollPane15.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane15.setPreferredSize(new java.awt.Dimension(200, 35));

        recuentoObtenido4.setColumns(10);
        recuentoObtenido4.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        recuentoObtenido4.setLineWrap(true);
        recuentoObtenido4.setRows(5);
        jScrollPane15.setViewportView(recuentoObtenido4);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        jPanel1.add(jScrollPane15, gridBagConstraints);

        jScrollPane16.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane16.setMaximumSize(new java.awt.Dimension(200, 50));
        jScrollPane16.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane16.setPreferredSize(new java.awt.Dimension(200, 35));

        determinacion4.setColumns(10);
        determinacion4.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        determinacion4.setLineWrap(true);
        determinacion4.setRows(5);
        jScrollPane16.setViewportView(determinacion4);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.insets = new java.awt.Insets(0, 30, 0, 0);
        jPanel1.add(jScrollPane16, gridBagConstraints);

        jScrollPane17.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane17.setMaximumSize(new java.awt.Dimension(200, 50));
        jScrollPane17.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane17.setPreferredSize(new java.awt.Dimension(200, 35));

        metodo5.setColumns(10);
        metodo5.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        metodo5.setLineWrap(true);
        metodo5.setRows(5);
        jScrollPane17.setViewportView(metodo5);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 30);
        jPanel1.add(jScrollPane17, gridBagConstraints);

        jScrollPane18.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane18.setMaximumSize(new java.awt.Dimension(200, 50));
        jScrollPane18.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane18.setPreferredSize(new java.awt.Dimension(200, 35));

        recuentoNormal5.setColumns(10);
        recuentoNormal5.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        recuentoNormal5.setLineWrap(true);
        recuentoNormal5.setRows(5);
        jScrollPane18.setViewportView(recuentoNormal5);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 6;
        jPanel1.add(jScrollPane18, gridBagConstraints);

        jScrollPane19.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane19.setMaximumSize(new java.awt.Dimension(200, 50));
        jScrollPane19.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane19.setPreferredSize(new java.awt.Dimension(200, 35));

        recuentoObtenido5.setColumns(10);
        recuentoObtenido5.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        recuentoObtenido5.setLineWrap(true);
        recuentoObtenido5.setRows(5);
        jScrollPane19.setViewportView(recuentoObtenido5);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        jPanel1.add(jScrollPane19, gridBagConstraints);

        jScrollPane20.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane20.setMaximumSize(new java.awt.Dimension(200, 50));
        jScrollPane20.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane20.setPreferredSize(new java.awt.Dimension(200, 35));

        determinacion5.setColumns(10);
        determinacion5.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        determinacion5.setLineWrap(true);
        determinacion5.setRows(5);
        jScrollPane20.setViewportView(determinacion5);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.insets = new java.awt.Insets(0, 30, 0, 0);
        jPanel1.add(jScrollPane20, gridBagConstraints);

        jScrollPane21.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane21.setMaximumSize(new java.awt.Dimension(200, 50));
        jScrollPane21.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane21.setPreferredSize(new java.awt.Dimension(200, 35));

        metodo6.setColumns(10);
        metodo6.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        metodo6.setLineWrap(true);
        metodo6.setRows(5);
        jScrollPane21.setViewportView(metodo6);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 30);
        jPanel1.add(jScrollPane21, gridBagConstraints);

        jScrollPane22.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane22.setMaximumSize(new java.awt.Dimension(200, 50));
        jScrollPane22.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane22.setPreferredSize(new java.awt.Dimension(200, 35));

        recuentoNormal6.setColumns(10);
        recuentoNormal6.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        recuentoNormal6.setLineWrap(true);
        recuentoNormal6.setRows(5);
        jScrollPane22.setViewportView(recuentoNormal6);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 7;
        jPanel1.add(jScrollPane22, gridBagConstraints);

        jScrollPane23.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane23.setMaximumSize(new java.awt.Dimension(200, 50));
        jScrollPane23.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane23.setPreferredSize(new java.awt.Dimension(200, 35));

        recuentoObtenido6.setColumns(10);
        recuentoObtenido6.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        recuentoObtenido6.setLineWrap(true);
        recuentoObtenido6.setRows(5);
        jScrollPane23.setViewportView(recuentoObtenido6);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        jPanel1.add(jScrollPane23, gridBagConstraints);

        jScrollPane24.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane24.setMaximumSize(new java.awt.Dimension(200, 50));
        jScrollPane24.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane24.setPreferredSize(new java.awt.Dimension(200, 35));

        determinacion6.setColumns(10);
        determinacion6.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        determinacion6.setLineWrap(true);
        determinacion6.setRows(5);
        jScrollPane24.setViewportView(determinacion6);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.insets = new java.awt.Insets(0, 30, 0, 0);
        jPanel1.add(jScrollPane24, gridBagConstraints);

        jScrollPane25.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane25.setMaximumSize(new java.awt.Dimension(200, 50));
        jScrollPane25.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane25.setPreferredSize(new java.awt.Dimension(200, 35));

        metodo7.setColumns(10);
        metodo7.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        metodo7.setLineWrap(true);
        metodo7.setRows(5);
        jScrollPane25.setViewportView(metodo7);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 30);
        jPanel1.add(jScrollPane25, gridBagConstraints);

        jScrollPane26.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane26.setMaximumSize(new java.awt.Dimension(200, 50));
        jScrollPane26.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane26.setPreferredSize(new java.awt.Dimension(200, 35));

        recuentoNormal7.setColumns(10);
        recuentoNormal7.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        recuentoNormal7.setLineWrap(true);
        recuentoNormal7.setRows(5);
        jScrollPane26.setViewportView(recuentoNormal7);
        recuentoNormal7.setWrapStyleWord(true);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 8;
        jPanel1.add(jScrollPane26, gridBagConstraints);

        jScrollPane27.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane27.setMaximumSize(new java.awt.Dimension(200, 50));
        jScrollPane27.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane27.setPreferredSize(new java.awt.Dimension(200, 35));

        recuentoObtenido7.setColumns(10);
        recuentoObtenido7.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        recuentoObtenido7.setLineWrap(true);
        recuentoObtenido7.setRows(5);
        jScrollPane27.setViewportView(recuentoObtenido7);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        jPanel1.add(jScrollPane27, gridBagConstraints);

        jScrollPane28.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane28.setMaximumSize(new java.awt.Dimension(200, 50));
        jScrollPane28.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane28.setPreferredSize(new java.awt.Dimension(200, 35));

        determinacion7.setColumns(10);
        determinacion7.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        determinacion7.setLineWrap(true);
        determinacion7.setRows(5);
        jScrollPane28.setViewportView(determinacion7);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.insets = new java.awt.Insets(0, 30, 0, 0);
        jPanel1.add(jScrollPane28, gridBagConstraints);

        jScrollPane29.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane29.setMaximumSize(new java.awt.Dimension(200, 50));
        jScrollPane29.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane29.setPreferredSize(new java.awt.Dimension(200, 35));

        metodo8.setColumns(10);
        metodo8.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        metodo8.setLineWrap(true);
        metodo8.setRows(5);
        jScrollPane29.setViewportView(metodo8);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 30);
        jPanel1.add(jScrollPane29, gridBagConstraints);

        jScrollPane30.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane30.setMaximumSize(new java.awt.Dimension(200, 50));
        jScrollPane30.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane30.setPreferredSize(new java.awt.Dimension(200, 35));

        recuentoNormal8.setColumns(10);
        recuentoNormal8.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        recuentoNormal8.setLineWrap(true);
        recuentoNormal8.setRows(5);
        jScrollPane30.setViewportView(recuentoNormal8);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 9;
        jPanel1.add(jScrollPane30, gridBagConstraints);

        jScrollPane31.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane31.setMaximumSize(new java.awt.Dimension(200, 50));
        jScrollPane31.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane31.setPreferredSize(new java.awt.Dimension(200, 35));

        recuentoObtenido8.setColumns(10);
        recuentoObtenido8.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        recuentoObtenido8.setLineWrap(true);
        recuentoObtenido8.setRows(5);
        jScrollPane31.setViewportView(recuentoObtenido8);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 9;
        jPanel1.add(jScrollPane31, gridBagConstraints);

        jScrollPane32.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane32.setMaximumSize(new java.awt.Dimension(200, 50));
        jScrollPane32.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane32.setPreferredSize(new java.awt.Dimension(200, 35));

        determinacion8.setColumns(10);
        determinacion8.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        determinacion8.setLineWrap(true);
        determinacion8.setRows(5);
        jScrollPane32.setViewportView(determinacion8);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.insets = new java.awt.Insets(0, 30, 0, 0);
        jPanel1.add(jScrollPane32, gridBagConstraints);

        jScrollPane33.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane33.setMaximumSize(new java.awt.Dimension(200, 50));
        jScrollPane33.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane33.setPreferredSize(new java.awt.Dimension(200, 35));

        metodo9.setColumns(10);
        metodo9.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        metodo9.setLineWrap(true);
        metodo9.setRows(5);
        jScrollPane33.setViewportView(metodo9);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 30);
        jPanel1.add(jScrollPane33, gridBagConstraints);

        jScrollPane34.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane34.setMaximumSize(new java.awt.Dimension(200, 50));
        jScrollPane34.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane34.setPreferredSize(new java.awt.Dimension(200, 35));

        recuentoNormal9.setColumns(10);
        recuentoNormal9.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        recuentoNormal9.setLineWrap(true);
        recuentoNormal9.setRows(5);
        jScrollPane34.setViewportView(recuentoNormal9);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 10;
        jPanel1.add(jScrollPane34, gridBagConstraints);

        jScrollPane35.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane35.setMaximumSize(new java.awt.Dimension(200, 50));
        jScrollPane35.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane35.setPreferredSize(new java.awt.Dimension(200, 35));

        recuentoObtenido9.setColumns(10);
        recuentoObtenido9.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        recuentoObtenido9.setLineWrap(true);
        recuentoObtenido9.setRows(5);
        jScrollPane35.setViewportView(recuentoObtenido9);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 10;
        jPanel1.add(jScrollPane35, gridBagConstraints);

        jScrollPane36.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane36.setMaximumSize(new java.awt.Dimension(200, 50));
        jScrollPane36.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane36.setPreferredSize(new java.awt.Dimension(200, 35));

        determinacion9.setColumns(10);
        determinacion9.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        determinacion9.setLineWrap(true);
        determinacion9.setRows(5);
        jScrollPane36.setViewportView(determinacion9);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.insets = new java.awt.Insets(0, 30, 0, 0);
        jPanel1.add(jScrollPane36, gridBagConstraints);

        jScrollPane37.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane37.setMaximumSize(new java.awt.Dimension(200, 50));
        jScrollPane37.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane37.setPreferredSize(new java.awt.Dimension(200, 35));

        metodo10.setColumns(10);
        metodo10.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        metodo10.setLineWrap(true);
        metodo10.setRows(5);
        jScrollPane37.setViewportView(metodo10);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 30);
        jPanel1.add(jScrollPane37, gridBagConstraints);

        jScrollPane38.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane38.setMaximumSize(new java.awt.Dimension(200, 50));
        jScrollPane38.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane38.setPreferredSize(new java.awt.Dimension(200, 35));

        recuentoNormal10.setColumns(10);
        recuentoNormal10.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        recuentoNormal10.setLineWrap(true);
        recuentoNormal10.setRows(5);
        jScrollPane38.setViewportView(recuentoNormal10);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 11;
        jPanel1.add(jScrollPane38, gridBagConstraints);

        jScrollPane39.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane39.setMaximumSize(new java.awt.Dimension(200, 50));
        jScrollPane39.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane39.setPreferredSize(new java.awt.Dimension(200, 35));

        recuentoObtenido10.setColumns(10);
        recuentoObtenido10.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        recuentoObtenido10.setLineWrap(true);
        recuentoObtenido10.setRows(5);
        jScrollPane39.setViewportView(recuentoObtenido10);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 11;
        jPanel1.add(jScrollPane39, gridBagConstraints);

        jScrollPane40.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane40.setMaximumSize(new java.awt.Dimension(200, 50));
        jScrollPane40.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane40.setPreferredSize(new java.awt.Dimension(200, 35));

        determinacion10.setColumns(10);
        determinacion10.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        determinacion10.setLineWrap(true);
        determinacion10.setRows(5);
        jScrollPane40.setViewportView(determinacion10);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.insets = new java.awt.Insets(0, 30, 0, 0);
        jPanel1.add(jScrollPane40, gridBagConstraints);

        jButton1.setText("Generar main.resources.reporte");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 38;
        gridBagConstraints.insets = new java.awt.Insets(20, 0, 0, 0);
        jPanel1.add(jButton1, gridBagConstraints);

        buttonGroup1.add(radioMal);
        radioMal.setText("Conclusión mal");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 36;
        jPanel1.add(radioMal, gridBagConstraints);

        buttonGroup1.add(radioBien);
        radioBien.setText("Conclusión bien");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 36;
        jPanel1.add(radioBien, gridBagConstraints);

        cajaFechaAnalisis.setDateFormatString("dd-MM-yyyy");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(20, 10, 0, 0);
        jPanel1.add(cajaFechaAnalisis, gridBagConstraints);

        jLabel8.setText("Fecha de analisis:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(20, 0, 0, 10);
        jPanel1.add(jLabel8, gridBagConstraints);

        buttonGroup1.add(radioManual);
        radioManual.setText("Conclusión manual");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 36;
        jPanel1.add(radioManual, gridBagConstraints);

        jScrollPane41.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane41.setMaximumSize(new java.awt.Dimension(200, 50));
        jScrollPane41.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane41.setPreferredSize(new java.awt.Dimension(200, 35));

        metodo11.setColumns(10);
        metodo11.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        metodo11.setLineWrap(true);
        metodo11.setRows(5);
        jScrollPane41.setViewportView(metodo11);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 30);
        jPanel1.add(jScrollPane41, gridBagConstraints);

        jScrollPane42.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane42.setMaximumSize(new java.awt.Dimension(200, 50));
        jScrollPane42.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane42.setPreferredSize(new java.awt.Dimension(200, 35));

        metodo12.setColumns(10);
        metodo12.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        metodo12.setLineWrap(true);
        metodo12.setRows(5);
        jScrollPane42.setViewportView(metodo12);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 30);
        jPanel1.add(jScrollPane42, gridBagConstraints);

        jScrollPane43.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane43.setMaximumSize(new java.awt.Dimension(200, 50));
        jScrollPane43.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane43.setPreferredSize(new java.awt.Dimension(200, 35));

        recuentoNormal12.setColumns(10);
        recuentoNormal12.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        recuentoNormal12.setLineWrap(true);
        recuentoNormal12.setRows(5);
        jScrollPane43.setViewportView(recuentoNormal12);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 13;
        jPanel1.add(jScrollPane43, gridBagConstraints);

        jScrollPane44.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane44.setMaximumSize(new java.awt.Dimension(200, 50));
        jScrollPane44.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane44.setPreferredSize(new java.awt.Dimension(200, 35));

        recuentoObtenido11.setColumns(10);
        recuentoObtenido11.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        recuentoObtenido11.setLineWrap(true);
        recuentoObtenido11.setRows(5);
        jScrollPane44.setViewportView(recuentoObtenido11);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 12;
        jPanel1.add(jScrollPane44, gridBagConstraints);

        jScrollPane45.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane45.setMaximumSize(new java.awt.Dimension(200, 50));
        jScrollPane45.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane45.setPreferredSize(new java.awt.Dimension(200, 35));

        determinacion11.setColumns(10);
        determinacion11.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        determinacion11.setLineWrap(true);
        determinacion11.setRows(5);
        jScrollPane45.setViewportView(determinacion11);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.insets = new java.awt.Insets(0, 30, 0, 0);
        jPanel1.add(jScrollPane45, gridBagConstraints);

        jScrollPane46.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane46.setMaximumSize(new java.awt.Dimension(200, 50));
        jScrollPane46.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane46.setPreferredSize(new java.awt.Dimension(200, 35));

        metodo13.setColumns(10);
        metodo13.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        metodo13.setLineWrap(true);
        metodo13.setRows(5);
        jScrollPane46.setViewportView(metodo13);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 30);
        jPanel1.add(jScrollPane46, gridBagConstraints);

        jScrollPane47.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane47.setMaximumSize(new java.awt.Dimension(200, 50));
        jScrollPane47.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane47.setPreferredSize(new java.awt.Dimension(200, 35));

        recuentoNormal13.setColumns(10);
        recuentoNormal13.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        recuentoNormal13.setLineWrap(true);
        recuentoNormal13.setRows(5);
        jScrollPane47.setViewportView(recuentoNormal13);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 14;
        jPanel1.add(jScrollPane47, gridBagConstraints);

        jScrollPane48.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane48.setMaximumSize(new java.awt.Dimension(200, 50));
        jScrollPane48.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane48.setPreferredSize(new java.awt.Dimension(200, 35));

        recuentoObtenido12.setColumns(10);
        recuentoObtenido12.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        recuentoObtenido12.setLineWrap(true);
        recuentoObtenido12.setRows(5);
        jScrollPane48.setViewportView(recuentoObtenido12);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 13;
        jPanel1.add(jScrollPane48, gridBagConstraints);

        jScrollPane49.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane49.setMaximumSize(new java.awt.Dimension(200, 50));
        jScrollPane49.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane49.setPreferredSize(new java.awt.Dimension(200, 35));

        determinacion12.setColumns(10);
        determinacion12.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        determinacion12.setLineWrap(true);
        determinacion12.setRows(5);
        jScrollPane49.setViewportView(determinacion12);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.insets = new java.awt.Insets(0, 30, 0, 0);
        jPanel1.add(jScrollPane49, gridBagConstraints);

        jScrollPane50.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane50.setMaximumSize(new java.awt.Dimension(200, 50));
        jScrollPane50.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane50.setPreferredSize(new java.awt.Dimension(200, 35));

        metodo14.setColumns(10);
        metodo14.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        metodo14.setLineWrap(true);
        metodo14.setRows(5);
        jScrollPane50.setViewportView(metodo14);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 15;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 30);
        jPanel1.add(jScrollPane50, gridBagConstraints);

        jScrollPane51.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane51.setMaximumSize(new java.awt.Dimension(200, 50));
        jScrollPane51.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane51.setPreferredSize(new java.awt.Dimension(200, 35));

        recuentoNormal14.setColumns(10);
        recuentoNormal14.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        recuentoNormal14.setLineWrap(true);
        recuentoNormal14.setRows(5);
        jScrollPane51.setViewportView(recuentoNormal14);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 15;
        jPanel1.add(jScrollPane51, gridBagConstraints);

        jScrollPane52.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane52.setMaximumSize(new java.awt.Dimension(200, 50));
        jScrollPane52.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane52.setPreferredSize(new java.awt.Dimension(200, 35));

        recuentoObtenido13.setColumns(10);
        recuentoObtenido13.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        recuentoObtenido13.setLineWrap(true);
        recuentoObtenido13.setRows(5);
        jScrollPane52.setViewportView(recuentoObtenido13);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 14;
        jPanel1.add(jScrollPane52, gridBagConstraints);

        jScrollPane53.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane53.setMaximumSize(new java.awt.Dimension(200, 50));
        jScrollPane53.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane53.setPreferredSize(new java.awt.Dimension(200, 35));

        determinacion13.setColumns(10);
        determinacion13.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        determinacion13.setLineWrap(true);
        determinacion13.setRows(5);
        jScrollPane53.setViewportView(determinacion13);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.insets = new java.awt.Insets(0, 30, 0, 0);
        jPanel1.add(jScrollPane53, gridBagConstraints);

        jScrollPane54.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane54.setMaximumSize(new java.awt.Dimension(200, 50));
        jScrollPane54.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane54.setPreferredSize(new java.awt.Dimension(200, 35));

        metodo15.setColumns(10);
        metodo15.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        metodo15.setLineWrap(true);
        metodo15.setRows(5);
        jScrollPane54.setViewportView(metodo15);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 16;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 30);
        jPanel1.add(jScrollPane54, gridBagConstraints);

        jScrollPane55.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane55.setMaximumSize(new java.awt.Dimension(200, 50));
        jScrollPane55.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane55.setPreferredSize(new java.awt.Dimension(200, 35));

        recuentoNormal15.setColumns(10);
        recuentoNormal15.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        recuentoNormal15.setLineWrap(true);
        recuentoNormal15.setRows(5);
        jScrollPane55.setViewportView(recuentoNormal15);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 16;
        jPanel1.add(jScrollPane55, gridBagConstraints);

        jScrollPane56.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane56.setMaximumSize(new java.awt.Dimension(200, 50));
        jScrollPane56.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane56.setPreferredSize(new java.awt.Dimension(200, 35));

        recuentoObtenido14.setColumns(10);
        recuentoObtenido14.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        recuentoObtenido14.setLineWrap(true);
        recuentoObtenido14.setRows(5);
        jScrollPane56.setViewportView(recuentoObtenido14);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 15;
        jPanel1.add(jScrollPane56, gridBagConstraints);

        jScrollPane57.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane57.setMaximumSize(new java.awt.Dimension(200, 50));
        jScrollPane57.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane57.setPreferredSize(new java.awt.Dimension(200, 35));

        determinacion14.setColumns(10);
        determinacion14.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        determinacion14.setLineWrap(true);
        determinacion14.setRows(5);
        jScrollPane57.setViewportView(determinacion14);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 15;
        gridBagConstraints.insets = new java.awt.Insets(0, 30, 0, 0);
        jPanel1.add(jScrollPane57, gridBagConstraints);

        jScrollPane58.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane58.setMaximumSize(new java.awt.Dimension(200, 50));
        jScrollPane58.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane58.setPreferredSize(new java.awt.Dimension(200, 35));

        metodo16.setColumns(10);
        metodo16.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        metodo16.setLineWrap(true);
        metodo16.setRows(5);
        jScrollPane58.setViewportView(metodo16);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 17;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 30);
        jPanel1.add(jScrollPane58, gridBagConstraints);

        jScrollPane59.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane59.setMaximumSize(new java.awt.Dimension(200, 50));
        jScrollPane59.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane59.setPreferredSize(new java.awt.Dimension(200, 35));

        recuentoNormal16.setColumns(10);
        recuentoNormal16.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        recuentoNormal16.setLineWrap(true);
        recuentoNormal16.setRows(5);
        jScrollPane59.setViewportView(recuentoNormal16);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 17;
        jPanel1.add(jScrollPane59, gridBagConstraints);

        jScrollPane60.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane60.setMaximumSize(new java.awt.Dimension(200, 50));
        jScrollPane60.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane60.setPreferredSize(new java.awt.Dimension(200, 35));

        recuentoObtenido15.setColumns(10);
        recuentoObtenido15.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        recuentoObtenido15.setLineWrap(true);
        recuentoObtenido15.setRows(5);
        jScrollPane60.setViewportView(recuentoObtenido15);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 16;
        jPanel1.add(jScrollPane60, gridBagConstraints);

        jScrollPane61.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane61.setMaximumSize(new java.awt.Dimension(200, 50));
        jScrollPane61.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane61.setPreferredSize(new java.awt.Dimension(200, 35));

        determinacion15.setColumns(10);
        determinacion15.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        determinacion15.setLineWrap(true);
        determinacion15.setRows(5);
        jScrollPane61.setViewportView(determinacion15);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 16;
        gridBagConstraints.insets = new java.awt.Insets(0, 30, 0, 0);
        jPanel1.add(jScrollPane61, gridBagConstraints);

        jScrollPane62.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane62.setMaximumSize(new java.awt.Dimension(200, 50));
        jScrollPane62.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane62.setPreferredSize(new java.awt.Dimension(200, 35));

        metodo17.setColumns(10);
        metodo17.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        metodo17.setLineWrap(true);
        metodo17.setRows(5);
        jScrollPane62.setViewportView(metodo17);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 18;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 30);
        jPanel1.add(jScrollPane62, gridBagConstraints);

        jScrollPane63.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane63.setMaximumSize(new java.awt.Dimension(200, 50));
        jScrollPane63.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane63.setPreferredSize(new java.awt.Dimension(200, 35));

        recuentoNormal17.setColumns(10);
        recuentoNormal17.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        recuentoNormal17.setLineWrap(true);
        recuentoNormal17.setRows(5);
        jScrollPane63.setViewportView(recuentoNormal17);
        recuentoNormal7.setWrapStyleWord(true);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 18;
        jPanel1.add(jScrollPane63, gridBagConstraints);

        jScrollPane64.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane64.setMaximumSize(new java.awt.Dimension(200, 50));
        jScrollPane64.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane64.setPreferredSize(new java.awt.Dimension(200, 35));

        recuentoObtenido16.setColumns(10);
        recuentoObtenido16.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        recuentoObtenido16.setLineWrap(true);
        recuentoObtenido16.setRows(5);
        jScrollPane64.setViewportView(recuentoObtenido16);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 17;
        jPanel1.add(jScrollPane64, gridBagConstraints);

        jScrollPane65.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane65.setMaximumSize(new java.awt.Dimension(200, 50));
        jScrollPane65.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane65.setPreferredSize(new java.awt.Dimension(200, 35));

        determinacion16.setColumns(10);
        determinacion16.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        determinacion16.setLineWrap(true);
        determinacion16.setRows(5);
        jScrollPane65.setViewportView(determinacion16);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 17;
        gridBagConstraints.insets = new java.awt.Insets(0, 30, 0, 0);
        jPanel1.add(jScrollPane65, gridBagConstraints);

        jScrollPane66.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane66.setMaximumSize(new java.awt.Dimension(200, 50));
        jScrollPane66.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane66.setPreferredSize(new java.awt.Dimension(200, 35));

        metodo18.setColumns(10);
        metodo18.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        metodo18.setLineWrap(true);
        metodo18.setRows(5);
        jScrollPane66.setViewportView(metodo18);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 19;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 30);
        jPanel1.add(jScrollPane66, gridBagConstraints);

        jScrollPane67.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane67.setMaximumSize(new java.awt.Dimension(200, 50));
        jScrollPane67.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane67.setPreferredSize(new java.awt.Dimension(200, 35));

        recuentoNormal18.setColumns(10);
        recuentoNormal18.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        recuentoNormal18.setLineWrap(true);
        recuentoNormal18.setRows(5);
        jScrollPane67.setViewportView(recuentoNormal18);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 19;
        jPanel1.add(jScrollPane67, gridBagConstraints);

        jScrollPane68.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane68.setMaximumSize(new java.awt.Dimension(200, 50));
        jScrollPane68.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane68.setPreferredSize(new java.awt.Dimension(200, 35));

        recuentoObtenido17.setColumns(10);
        recuentoObtenido17.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        recuentoObtenido17.setLineWrap(true);
        recuentoObtenido17.setRows(5);
        jScrollPane68.setViewportView(recuentoObtenido17);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 18;
        jPanel1.add(jScrollPane68, gridBagConstraints);

        jScrollPane69.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane69.setMaximumSize(new java.awt.Dimension(200, 50));
        jScrollPane69.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane69.setPreferredSize(new java.awt.Dimension(200, 35));

        determinacion17.setColumns(10);
        determinacion17.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        determinacion17.setLineWrap(true);
        determinacion17.setRows(5);
        jScrollPane69.setViewportView(determinacion17);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 18;
        gridBagConstraints.insets = new java.awt.Insets(0, 30, 0, 0);
        jPanel1.add(jScrollPane69, gridBagConstraints);

        jScrollPane70.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane70.setMaximumSize(new java.awt.Dimension(200, 50));
        jScrollPane70.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane70.setPreferredSize(new java.awt.Dimension(200, 35));

        metodo19.setColumns(10);
        metodo19.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        metodo19.setLineWrap(true);
        metodo19.setRows(5);
        jScrollPane70.setViewportView(metodo19);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 20;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 30);
        jPanel1.add(jScrollPane70, gridBagConstraints);

        jScrollPane71.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane71.setMaximumSize(new java.awt.Dimension(200, 50));
        jScrollPane71.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane71.setPreferredSize(new java.awt.Dimension(200, 35));

        recuentoNormal19.setColumns(10);
        recuentoNormal19.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        recuentoNormal19.setLineWrap(true);
        recuentoNormal19.setRows(5);
        jScrollPane71.setViewportView(recuentoNormal19);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 20;
        jPanel1.add(jScrollPane71, gridBagConstraints);

        jScrollPane73.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane73.setMaximumSize(new java.awt.Dimension(200, 50));
        jScrollPane73.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane73.setPreferredSize(new java.awt.Dimension(200, 35));

        determinacion18.setColumns(10);
        determinacion18.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        determinacion18.setLineWrap(true);
        determinacion18.setRows(5);
        jScrollPane73.setViewportView(determinacion18);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 19;
        gridBagConstraints.insets = new java.awt.Insets(0, 30, 0, 0);
        jPanel1.add(jScrollPane73, gridBagConstraints);

        jScrollPane76.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane76.setMaximumSize(new java.awt.Dimension(200, 50));
        jScrollPane76.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane76.setPreferredSize(new java.awt.Dimension(200, 35));

        recuentoObtenido19.setColumns(10);
        recuentoObtenido19.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        recuentoObtenido19.setLineWrap(true);
        recuentoObtenido19.setRows(5);
        jScrollPane76.setViewportView(recuentoObtenido19);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 20;
        jPanel1.add(jScrollPane76, gridBagConstraints);

        jScrollPane77.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane77.setMaximumSize(new java.awt.Dimension(200, 50));
        jScrollPane77.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane77.setPreferredSize(new java.awt.Dimension(200, 35));

        determinacion19.setColumns(10);
        determinacion19.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        determinacion19.setLineWrap(true);
        determinacion19.setRows(5);
        jScrollPane77.setViewportView(determinacion19);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 20;
        gridBagConstraints.insets = new java.awt.Insets(0, 30, 0, 0);
        jPanel1.add(jScrollPane77, gridBagConstraints);

        jScrollPane82.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane82.setMaximumSize(new java.awt.Dimension(200, 50));
        jScrollPane82.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane82.setPreferredSize(new java.awt.Dimension(200, 35));

        recuentoObtenido18.setColumns(10);
        recuentoObtenido18.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        recuentoObtenido18.setLineWrap(true);
        recuentoObtenido18.setRows(5);
        jScrollPane82.setViewportView(recuentoObtenido18);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 19;
        jPanel1.add(jScrollPane82, gridBagConstraints);

        jScrollPane72.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane72.setMaximumSize(new java.awt.Dimension(200, 50));
        jScrollPane72.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane72.setPreferredSize(new java.awt.Dimension(200, 35));

        recuentoNormal11.setColumns(10);
        recuentoNormal11.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        recuentoNormal11.setLineWrap(true);
        recuentoNormal11.setRows(5);
        jScrollPane72.setViewportView(recuentoNormal11);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 12;
        jPanel1.add(jScrollPane72, gridBagConstraints);

        botonMostrar.setActionCommand("true");
        buttonGroup2.add(botonMostrar);
        botonMostrar.setText("Mostrar fecha de elaboración y lote");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 37;
        gridBagConstraints.gridwidth = 2;
        jPanel1.add(botonMostrar, gridBagConstraints);

        botonNoMostrar.setActionCommand("false");
        buttonGroup2.add(botonNoMostrar);
        botonNoMostrar.setText("NO nostrar fecha de elaboración y lote");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 37;
        gridBagConstraints.gridwidth = 2;
        jPanel1.add(botonNoMostrar, gridBagConstraints);

        jScrollPane78.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane78.setMaximumSize(new java.awt.Dimension(200, 50));
        jScrollPane78.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane78.setPreferredSize(new java.awt.Dimension(200, 35));

        recuentoObtenido20.setColumns(10);
        recuentoObtenido20.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        recuentoObtenido20.setLineWrap(true);
        recuentoObtenido20.setRows(5);
        jScrollPane78.setViewportView(recuentoObtenido20);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 21;
        jPanel1.add(jScrollPane78, gridBagConstraints);

        jScrollPane74.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane74.setMaximumSize(new java.awt.Dimension(200, 50));
        jScrollPane74.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane74.setPreferredSize(new java.awt.Dimension(200, 35));

        recuentoNormal20.setColumns(10);
        recuentoNormal20.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        recuentoNormal20.setLineWrap(true);
        recuentoNormal20.setRows(5);
        jScrollPane74.setViewportView(recuentoNormal20);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 21;
        jPanel1.add(jScrollPane74, gridBagConstraints);

        jScrollPane79.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane79.setMaximumSize(new java.awt.Dimension(200, 50));
        jScrollPane79.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane79.setPreferredSize(new java.awt.Dimension(200, 35));

        determinacion20.setColumns(10);
        determinacion20.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        determinacion20.setLineWrap(true);
        determinacion20.setRows(5);
        jScrollPane79.setViewportView(determinacion20);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 21;
        gridBagConstraints.insets = new java.awt.Insets(0, 30, 0, 0);
        jPanel1.add(jScrollPane79, gridBagConstraints);

        jScrollPane75.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane75.setMaximumSize(new java.awt.Dimension(200, 50));
        jScrollPane75.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane75.setPreferredSize(new java.awt.Dimension(200, 35));

        metodo20.setColumns(10);
        metodo20.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        metodo20.setLineWrap(true);
        metodo20.setRows(5);
        jScrollPane75.setViewportView(metodo20);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 21;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 30);
        jPanel1.add(jScrollPane75, gridBagConstraints);

        jScrollPane80.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane80.setMaximumSize(new java.awt.Dimension(200, 50));
        jScrollPane80.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane80.setPreferredSize(new java.awt.Dimension(200, 35));

        recuentoObtenido21.setColumns(10);
        recuentoObtenido21.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        recuentoObtenido21.setLineWrap(true);
        recuentoObtenido21.setRows(5);
        jScrollPane80.setViewportView(recuentoObtenido21);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 22;
        jPanel1.add(jScrollPane80, gridBagConstraints);

        jScrollPane83.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane83.setMaximumSize(new java.awt.Dimension(200, 50));
        jScrollPane83.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane83.setPreferredSize(new java.awt.Dimension(200, 35));

        recuentoNormal21.setColumns(10);
        recuentoNormal21.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        recuentoNormal21.setLineWrap(true);
        recuentoNormal21.setRows(5);
        jScrollPane83.setViewportView(recuentoNormal21);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 22;
        jPanel1.add(jScrollPane83, gridBagConstraints);

        jScrollPane84.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane84.setMaximumSize(new java.awt.Dimension(200, 50));
        jScrollPane84.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane84.setPreferredSize(new java.awt.Dimension(200, 35));

        determinacion21.setColumns(10);
        determinacion21.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        determinacion21.setLineWrap(true);
        determinacion21.setRows(5);
        jScrollPane84.setViewportView(determinacion21);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 22;
        gridBagConstraints.insets = new java.awt.Insets(0, 30, 0, 0);
        jPanel1.add(jScrollPane84, gridBagConstraints);

        jScrollPane85.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane85.setMaximumSize(new java.awt.Dimension(200, 50));
        jScrollPane85.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane85.setPreferredSize(new java.awt.Dimension(200, 35));

        metodo21.setColumns(10);
        metodo21.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        metodo21.setLineWrap(true);
        metodo21.setRows(5);
        jScrollPane85.setViewportView(metodo21);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 22;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 30);
        jPanel1.add(jScrollPane85, gridBagConstraints);

        jScrollPane86.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane86.setMaximumSize(new java.awt.Dimension(200, 50));
        jScrollPane86.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane86.setPreferredSize(new java.awt.Dimension(200, 35));

        recuentoObtenido22.setColumns(10);
        recuentoObtenido22.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        recuentoObtenido22.setLineWrap(true);
        recuentoObtenido22.setRows(5);
        jScrollPane86.setViewportView(recuentoObtenido22);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 23;
        jPanel1.add(jScrollPane86, gridBagConstraints);

        jScrollPane87.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane87.setMaximumSize(new java.awt.Dimension(200, 50));
        jScrollPane87.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane87.setPreferredSize(new java.awt.Dimension(200, 35));

        recuentoNormal22.setColumns(10);
        recuentoNormal22.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        recuentoNormal22.setLineWrap(true);
        recuentoNormal22.setRows(5);
        jScrollPane87.setViewportView(recuentoNormal22);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 23;
        jPanel1.add(jScrollPane87, gridBagConstraints);

        jScrollPane88.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane88.setMaximumSize(new java.awt.Dimension(200, 50));
        jScrollPane88.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane88.setPreferredSize(new java.awt.Dimension(200, 35));

        determinacion22.setColumns(10);
        determinacion22.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        determinacion22.setLineWrap(true);
        determinacion22.setRows(5);
        jScrollPane88.setViewportView(determinacion22);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 23;
        gridBagConstraints.insets = new java.awt.Insets(0, 30, 0, 0);
        jPanel1.add(jScrollPane88, gridBagConstraints);

        jScrollPane89.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane89.setMaximumSize(new java.awt.Dimension(200, 50));
        jScrollPane89.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane89.setPreferredSize(new java.awt.Dimension(200, 35));

        metodo22.setColumns(10);
        metodo22.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        metodo22.setLineWrap(true);
        metodo22.setRows(5);
        jScrollPane89.setViewportView(metodo22);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 23;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 30);
        jPanel1.add(jScrollPane89, gridBagConstraints);

        jScrollPane90.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane90.setMaximumSize(new java.awt.Dimension(200, 50));
        jScrollPane90.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane90.setPreferredSize(new java.awt.Dimension(200, 35));

        recuentoObtenido23.setColumns(10);
        recuentoObtenido23.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        recuentoObtenido23.setLineWrap(true);
        recuentoObtenido23.setRows(5);
        jScrollPane90.setViewportView(recuentoObtenido23);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 24;
        jPanel1.add(jScrollPane90, gridBagConstraints);

        jScrollPane91.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane91.setMaximumSize(new java.awt.Dimension(200, 50));
        jScrollPane91.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane91.setPreferredSize(new java.awt.Dimension(200, 35));

        recuentoNormal23.setColumns(10);
        recuentoNormal23.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        recuentoNormal23.setLineWrap(true);
        recuentoNormal23.setRows(5);
        jScrollPane91.setViewportView(recuentoNormal23);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 24;
        jPanel1.add(jScrollPane91, gridBagConstraints);

        jScrollPane92.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane92.setMaximumSize(new java.awt.Dimension(200, 50));
        jScrollPane92.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane92.setPreferredSize(new java.awt.Dimension(200, 35));

        determinacion23.setColumns(10);
        determinacion23.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        determinacion23.setLineWrap(true);
        determinacion23.setRows(5);
        jScrollPane92.setViewportView(determinacion23);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 24;
        gridBagConstraints.insets = new java.awt.Insets(0, 30, 0, 0);
        jPanel1.add(jScrollPane92, gridBagConstraints);

        jScrollPane93.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane93.setMaximumSize(new java.awt.Dimension(200, 50));
        jScrollPane93.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane93.setPreferredSize(new java.awt.Dimension(200, 35));

        metodo23.setColumns(10);
        metodo23.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        metodo23.setLineWrap(true);
        metodo23.setRows(5);
        jScrollPane93.setViewportView(metodo23);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 24;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 30);
        jPanel1.add(jScrollPane93, gridBagConstraints);

        jScrollPane94.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane94.setMaximumSize(new java.awt.Dimension(200, 50));
        jScrollPane94.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane94.setPreferredSize(new java.awt.Dimension(200, 35));

        recuentoObtenido24.setColumns(10);
        recuentoObtenido24.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        recuentoObtenido24.setLineWrap(true);
        recuentoObtenido24.setRows(5);
        jScrollPane94.setViewportView(recuentoObtenido24);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 25;
        jPanel1.add(jScrollPane94, gridBagConstraints);

        jScrollPane95.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane95.setMaximumSize(new java.awt.Dimension(200, 50));
        jScrollPane95.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane95.setPreferredSize(new java.awt.Dimension(200, 35));

        recuentoNormal24.setColumns(10);
        recuentoNormal24.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        recuentoNormal24.setLineWrap(true);
        recuentoNormal24.setRows(5);
        jScrollPane95.setViewportView(recuentoNormal24);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 25;
        jPanel1.add(jScrollPane95, gridBagConstraints);

        jScrollPane96.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane96.setMaximumSize(new java.awt.Dimension(200, 50));
        jScrollPane96.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane96.setPreferredSize(new java.awt.Dimension(200, 35));

        determinacion24.setColumns(10);
        determinacion24.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        determinacion24.setLineWrap(true);
        determinacion24.setRows(5);
        jScrollPane96.setViewportView(determinacion24);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 25;
        gridBagConstraints.insets = new java.awt.Insets(0, 30, 0, 0);
        jPanel1.add(jScrollPane96, gridBagConstraints);

        jScrollPane97.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane97.setMaximumSize(new java.awt.Dimension(200, 50));
        jScrollPane97.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane97.setPreferredSize(new java.awt.Dimension(200, 35));

        metodo24.setColumns(10);
        metodo24.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        metodo24.setLineWrap(true);
        metodo24.setRows(5);
        jScrollPane97.setViewportView(metodo24);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 25;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 30);
        jPanel1.add(jScrollPane97, gridBagConstraints);

        jScrollPane98.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane98.setMaximumSize(new java.awt.Dimension(200, 50));
        jScrollPane98.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane98.setPreferredSize(new java.awt.Dimension(200, 35));

        recuentoObtenido25.setColumns(10);
        recuentoObtenido25.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        recuentoObtenido25.setLineWrap(true);
        recuentoObtenido25.setRows(5);
        jScrollPane98.setViewportView(recuentoObtenido25);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 26;
        jPanel1.add(jScrollPane98, gridBagConstraints);

        jScrollPane99.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane99.setMaximumSize(new java.awt.Dimension(200, 50));
        jScrollPane99.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane99.setPreferredSize(new java.awt.Dimension(200, 35));

        recuentoNormal25.setColumns(10);
        recuentoNormal25.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        recuentoNormal25.setLineWrap(true);
        recuentoNormal25.setRows(5);
        jScrollPane99.setViewportView(recuentoNormal25);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 26;
        jPanel1.add(jScrollPane99, gridBagConstraints);

        jScrollPane100.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane100.setMaximumSize(new java.awt.Dimension(200, 50));
        jScrollPane100.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane100.setPreferredSize(new java.awt.Dimension(200, 35));

        determinacion25.setColumns(10);
        determinacion25.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        determinacion25.setLineWrap(true);
        determinacion25.setRows(5);
        jScrollPane100.setViewportView(determinacion25);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 26;
        gridBagConstraints.insets = new java.awt.Insets(0, 30, 0, 0);
        jPanel1.add(jScrollPane100, gridBagConstraints);

        jScrollPane101.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane101.setMaximumSize(new java.awt.Dimension(200, 50));
        jScrollPane101.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane101.setPreferredSize(new java.awt.Dimension(200, 35));

        metodo25.setColumns(10);
        metodo25.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        metodo25.setLineWrap(true);
        metodo25.setRows(5);
        jScrollPane101.setViewportView(metodo25);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 26;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 30);
        jPanel1.add(jScrollPane101, gridBagConstraints);

        jScrollPane102.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane102.setMaximumSize(new java.awt.Dimension(200, 50));
        jScrollPane102.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane102.setPreferredSize(new java.awt.Dimension(200, 35));

        recuentoObtenido26.setColumns(10);
        recuentoObtenido26.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        recuentoObtenido26.setLineWrap(true);
        recuentoObtenido26.setRows(5);
        jScrollPane102.setViewportView(recuentoObtenido26);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 27;
        jPanel1.add(jScrollPane102, gridBagConstraints);

        jScrollPane103.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane103.setMaximumSize(new java.awt.Dimension(200, 50));
        jScrollPane103.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane103.setPreferredSize(new java.awt.Dimension(200, 35));

        recuentoNormal26.setColumns(10);
        recuentoNormal26.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        recuentoNormal26.setLineWrap(true);
        recuentoNormal26.setRows(5);
        jScrollPane103.setViewportView(recuentoNormal26);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 27;
        jPanel1.add(jScrollPane103, gridBagConstraints);

        jScrollPane104.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane104.setMaximumSize(new java.awt.Dimension(200, 50));
        jScrollPane104.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane104.setPreferredSize(new java.awt.Dimension(200, 35));

        determinacion26.setColumns(10);
        determinacion26.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        determinacion26.setLineWrap(true);
        determinacion26.setRows(5);
        jScrollPane104.setViewportView(determinacion26);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 27;
        gridBagConstraints.insets = new java.awt.Insets(0, 30, 0, 0);
        jPanel1.add(jScrollPane104, gridBagConstraints);

        jScrollPane105.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane105.setMaximumSize(new java.awt.Dimension(200, 50));
        jScrollPane105.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane105.setPreferredSize(new java.awt.Dimension(200, 35));

        metodo26.setColumns(10);
        metodo26.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        metodo26.setLineWrap(true);
        metodo26.setRows(5);
        jScrollPane105.setViewportView(metodo26);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 27;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 30);
        jPanel1.add(jScrollPane105, gridBagConstraints);

        jScrollPane106.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane106.setMaximumSize(new java.awt.Dimension(200, 50));
        jScrollPane106.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane106.setPreferredSize(new java.awt.Dimension(200, 35));

        recuentoObtenido27.setColumns(10);
        recuentoObtenido27.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        recuentoObtenido27.setLineWrap(true);
        recuentoObtenido27.setRows(5);
        jScrollPane106.setViewportView(recuentoObtenido27);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 28;
        jPanel1.add(jScrollPane106, gridBagConstraints);

        jScrollPane107.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane107.setMaximumSize(new java.awt.Dimension(200, 50));
        jScrollPane107.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane107.setPreferredSize(new java.awt.Dimension(200, 35));

        recuentoNormal27.setColumns(10);
        recuentoNormal27.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        recuentoNormal27.setLineWrap(true);
        recuentoNormal27.setRows(5);
        jScrollPane107.setViewportView(recuentoNormal27);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 28;
        jPanel1.add(jScrollPane107, gridBagConstraints);

        jScrollPane108.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane108.setMaximumSize(new java.awt.Dimension(200, 50));
        jScrollPane108.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane108.setPreferredSize(new java.awt.Dimension(200, 35));

        determinacion27.setColumns(10);
        determinacion27.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        determinacion27.setLineWrap(true);
        determinacion27.setRows(5);
        jScrollPane108.setViewportView(determinacion27);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 28;
        gridBagConstraints.insets = new java.awt.Insets(0, 30, 0, 0);
        jPanel1.add(jScrollPane108, gridBagConstraints);

        jScrollPane109.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane109.setMaximumSize(new java.awt.Dimension(200, 50));
        jScrollPane109.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane109.setPreferredSize(new java.awt.Dimension(200, 35));

        metodo27.setColumns(10);
        metodo27.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        metodo27.setLineWrap(true);
        metodo27.setRows(5);
        jScrollPane109.setViewportView(metodo27);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 28;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 30);
        jPanel1.add(jScrollPane109, gridBagConstraints);

        jScrollPane110.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane110.setMaximumSize(new java.awt.Dimension(200, 50));
        jScrollPane110.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane110.setPreferredSize(new java.awt.Dimension(200, 35));

        recuentoObtenido28.setColumns(10);
        recuentoObtenido28.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        recuentoObtenido28.setLineWrap(true);
        recuentoObtenido28.setRows(5);
        jScrollPane110.setViewportView(recuentoObtenido28);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 29;
        jPanel1.add(jScrollPane110, gridBagConstraints);

        jScrollPane111.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane111.setMaximumSize(new java.awt.Dimension(200, 50));
        jScrollPane111.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane111.setPreferredSize(new java.awt.Dimension(200, 35));

        recuentoNormal28.setColumns(10);
        recuentoNormal28.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        recuentoNormal28.setLineWrap(true);
        recuentoNormal28.setRows(5);
        jScrollPane111.setViewportView(recuentoNormal28);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 29;
        jPanel1.add(jScrollPane111, gridBagConstraints);

        jScrollPane112.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane112.setMaximumSize(new java.awt.Dimension(200, 50));
        jScrollPane112.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane112.setPreferredSize(new java.awt.Dimension(200, 35));

        determinacion28.setColumns(10);
        determinacion28.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        determinacion28.setLineWrap(true);
        determinacion28.setRows(5);
        jScrollPane112.setViewportView(determinacion28);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 29;
        gridBagConstraints.insets = new java.awt.Insets(0, 30, 0, 0);
        jPanel1.add(jScrollPane112, gridBagConstraints);

        jScrollPane113.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane113.setMaximumSize(new java.awt.Dimension(200, 50));
        jScrollPane113.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane113.setPreferredSize(new java.awt.Dimension(200, 35));

        metodo28.setColumns(10);
        metodo28.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        metodo28.setLineWrap(true);
        metodo28.setRows(5);
        jScrollPane113.setViewportView(metodo28);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 29;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 30);
        jPanel1.add(jScrollPane113, gridBagConstraints);

        jScrollPane114.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane114.setMaximumSize(new java.awt.Dimension(200, 50));
        jScrollPane114.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane114.setPreferredSize(new java.awt.Dimension(200, 35));

        recuentoObtenido29.setColumns(10);
        recuentoObtenido29.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        recuentoObtenido29.setLineWrap(true);
        recuentoObtenido29.setRows(5);
        jScrollPane114.setViewportView(recuentoObtenido29);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 30;
        jPanel1.add(jScrollPane114, gridBagConstraints);

        jScrollPane115.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane115.setMaximumSize(new java.awt.Dimension(200, 50));
        jScrollPane115.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane115.setPreferredSize(new java.awt.Dimension(200, 35));

        recuentoNormal29.setColumns(10);
        recuentoNormal29.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        recuentoNormal29.setLineWrap(true);
        recuentoNormal29.setRows(5);
        jScrollPane115.setViewportView(recuentoNormal29);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 30;
        jPanel1.add(jScrollPane115, gridBagConstraints);

        jScrollPane116.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane116.setMaximumSize(new java.awt.Dimension(200, 50));
        jScrollPane116.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane116.setPreferredSize(new java.awt.Dimension(200, 35));

        determinacion29.setColumns(10);
        determinacion29.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        determinacion29.setLineWrap(true);
        determinacion29.setRows(5);
        jScrollPane116.setViewportView(determinacion29);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 30;
        gridBagConstraints.insets = new java.awt.Insets(0, 30, 0, 0);
        jPanel1.add(jScrollPane116, gridBagConstraints);

        jScrollPane117.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane117.setMaximumSize(new java.awt.Dimension(200, 50));
        jScrollPane117.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane117.setPreferredSize(new java.awt.Dimension(200, 35));

        metodo29.setColumns(10);
        metodo29.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        metodo29.setLineWrap(true);
        metodo29.setRows(5);
        jScrollPane117.setViewportView(metodo29);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 30;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 30);
        jPanel1.add(jScrollPane117, gridBagConstraints);

        jScrollPane118.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane118.setMaximumSize(new java.awt.Dimension(200, 50));
        jScrollPane118.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane118.setPreferredSize(new java.awt.Dimension(200, 35));

        recuentoObtenido30.setColumns(10);
        recuentoObtenido30.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        recuentoObtenido30.setLineWrap(true);
        recuentoObtenido30.setRows(5);
        jScrollPane118.setViewportView(recuentoObtenido30);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 31;
        jPanel1.add(jScrollPane118, gridBagConstraints);

        jScrollPane119.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane119.setMaximumSize(new java.awt.Dimension(200, 50));
        jScrollPane119.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane119.setPreferredSize(new java.awt.Dimension(200, 35));

        recuentoNormal30.setColumns(10);
        recuentoNormal30.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        recuentoNormal30.setLineWrap(true);
        recuentoNormal30.setRows(5);
        jScrollPane119.setViewportView(recuentoNormal30);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 31;
        jPanel1.add(jScrollPane119, gridBagConstraints);

        jScrollPane120.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane120.setMaximumSize(new java.awt.Dimension(200, 50));
        jScrollPane120.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane120.setPreferredSize(new java.awt.Dimension(200, 35));

        determinacion30.setColumns(10);
        determinacion30.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        determinacion30.setLineWrap(true);
        determinacion30.setRows(5);
        jScrollPane120.setViewportView(determinacion30);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 31;
        gridBagConstraints.insets = new java.awt.Insets(0, 30, 0, 0);
        jPanel1.add(jScrollPane120, gridBagConstraints);

        jScrollPane121.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane121.setMaximumSize(new java.awt.Dimension(200, 50));
        jScrollPane121.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane121.setPreferredSize(new java.awt.Dimension(200, 35));

        metodo30.setColumns(10);
        metodo30.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        metodo30.setLineWrap(true);
        metodo30.setRows(5);
        jScrollPane121.setViewportView(metodo30);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 31;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 30);
        jPanel1.add(jScrollPane121, gridBagConstraints);

        jScrollPane122.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane122.setMaximumSize(new java.awt.Dimension(200, 50));
        jScrollPane122.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane122.setPreferredSize(new java.awt.Dimension(200, 35));

        recuentoObtenido31.setColumns(10);
        recuentoObtenido31.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        recuentoObtenido31.setLineWrap(true);
        recuentoObtenido31.setRows(5);
        jScrollPane122.setViewportView(recuentoObtenido31);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 32;
        jPanel1.add(jScrollPane122, gridBagConstraints);

        jScrollPane123.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane123.setMaximumSize(new java.awt.Dimension(200, 50));
        jScrollPane123.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane123.setPreferredSize(new java.awt.Dimension(200, 35));

        recuentoNormal31.setColumns(10);
        recuentoNormal31.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        recuentoNormal31.setLineWrap(true);
        recuentoNormal31.setRows(5);
        jScrollPane123.setViewportView(recuentoNormal31);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 32;
        jPanel1.add(jScrollPane123, gridBagConstraints);

        jScrollPane124.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane124.setMaximumSize(new java.awt.Dimension(200, 50));
        jScrollPane124.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane124.setPreferredSize(new java.awt.Dimension(200, 35));

        determinacion31.setColumns(10);
        determinacion31.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        determinacion31.setLineWrap(true);
        determinacion31.setRows(5);
        jScrollPane124.setViewportView(determinacion31);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 32;
        gridBagConstraints.insets = new java.awt.Insets(0, 30, 0, 0);
        jPanel1.add(jScrollPane124, gridBagConstraints);

        jScrollPane125.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane125.setMaximumSize(new java.awt.Dimension(200, 50));
        jScrollPane125.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane125.setPreferredSize(new java.awt.Dimension(200, 35));

        metodo31.setColumns(10);
        metodo31.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        metodo31.setLineWrap(true);
        metodo31.setRows(5);
        jScrollPane125.setViewportView(metodo31);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 32;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 30);
        jPanel1.add(jScrollPane125, gridBagConstraints);

        jScrollPane126.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane126.setMaximumSize(new java.awt.Dimension(200, 50));
        jScrollPane126.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane126.setPreferredSize(new java.awt.Dimension(200, 35));

        recuentoObtenido32.setColumns(10);
        recuentoObtenido32.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        recuentoObtenido32.setLineWrap(true);
        recuentoObtenido32.setRows(5);
        jScrollPane126.setViewportView(recuentoObtenido32);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 33;
        jPanel1.add(jScrollPane126, gridBagConstraints);

        jScrollPane127.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane127.setMaximumSize(new java.awt.Dimension(200, 50));
        jScrollPane127.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane127.setPreferredSize(new java.awt.Dimension(200, 35));

        recuentoNormal32.setColumns(10);
        recuentoNormal32.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        recuentoNormal32.setLineWrap(true);
        recuentoNormal32.setRows(5);
        jScrollPane127.setViewportView(recuentoNormal32);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 33;
        jPanel1.add(jScrollPane127, gridBagConstraints);

        jScrollPane128.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane128.setMaximumSize(new java.awt.Dimension(200, 50));
        jScrollPane128.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane128.setPreferredSize(new java.awt.Dimension(200, 35));

        determinacion32.setColumns(10);
        determinacion32.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        determinacion32.setLineWrap(true);
        determinacion32.setRows(5);
        jScrollPane128.setViewportView(determinacion32);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 33;
        gridBagConstraints.insets = new java.awt.Insets(0, 30, 0, 0);
        jPanel1.add(jScrollPane128, gridBagConstraints);

        jScrollPane129.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane129.setMaximumSize(new java.awt.Dimension(200, 50));
        jScrollPane129.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane129.setPreferredSize(new java.awt.Dimension(200, 35));

        metodo32.setColumns(10);
        metodo32.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        metodo32.setLineWrap(true);
        metodo32.setRows(5);
        jScrollPane129.setViewportView(metodo32);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 33;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 30);
        jPanel1.add(jScrollPane129, gridBagConstraints);

        jScrollPane130.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane130.setMaximumSize(new java.awt.Dimension(200, 50));
        jScrollPane130.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane130.setPreferredSize(new java.awt.Dimension(200, 35));

        recuentoObtenido33.setColumns(10);
        recuentoObtenido33.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        recuentoObtenido33.setLineWrap(true);
        recuentoObtenido33.setRows(5);
        jScrollPane130.setViewportView(recuentoObtenido33);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 34;
        jPanel1.add(jScrollPane130, gridBagConstraints);

        jScrollPane131.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane131.setMaximumSize(new java.awt.Dimension(200, 50));
        jScrollPane131.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane131.setPreferredSize(new java.awt.Dimension(200, 35));

        recuentoNormal33.setColumns(10);
        recuentoNormal33.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        recuentoNormal33.setLineWrap(true);
        recuentoNormal33.setRows(5);
        jScrollPane131.setViewportView(recuentoNormal33);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 34;
        jPanel1.add(jScrollPane131, gridBagConstraints);

        jScrollPane132.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane132.setMaximumSize(new java.awt.Dimension(200, 50));
        jScrollPane132.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane132.setPreferredSize(new java.awt.Dimension(200, 35));

        determinacion33.setColumns(10);
        determinacion33.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        determinacion33.setLineWrap(true);
        determinacion33.setRows(5);
        jScrollPane132.setViewportView(determinacion33);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 34;
        gridBagConstraints.insets = new java.awt.Insets(0, 30, 0, 0);
        jPanel1.add(jScrollPane132, gridBagConstraints);

        jScrollPane133.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane133.setMaximumSize(new java.awt.Dimension(200, 50));
        jScrollPane133.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane133.setPreferredSize(new java.awt.Dimension(200, 35));

        metodo33.setColumns(10);
        metodo33.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        metodo33.setLineWrap(true);
        metodo33.setRows(5);
        jScrollPane133.setViewportView(metodo33);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 34;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 30);
        jPanel1.add(jScrollPane133, gridBagConstraints);

        jScrollPane134.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane134.setMaximumSize(new java.awt.Dimension(200, 50));
        jScrollPane134.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane134.setPreferredSize(new java.awt.Dimension(200, 35));

        recuentoObtenido34.setColumns(10);
        recuentoObtenido34.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        recuentoObtenido34.setLineWrap(true);
        recuentoObtenido34.setRows(5);
        jScrollPane134.setViewportView(recuentoObtenido34);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 35;
        jPanel1.add(jScrollPane134, gridBagConstraints);

        jScrollPane135.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane135.setMaximumSize(new java.awt.Dimension(200, 50));
        jScrollPane135.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane135.setPreferredSize(new java.awt.Dimension(200, 35));

        recuentoNormal34.setColumns(10);
        recuentoNormal34.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        recuentoNormal34.setLineWrap(true);
        recuentoNormal34.setRows(5);
        jScrollPane135.setViewportView(recuentoNormal34);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 35;
        jPanel1.add(jScrollPane135, gridBagConstraints);

        jScrollPane136.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane136.setMaximumSize(new java.awt.Dimension(200, 50));
        jScrollPane136.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane136.setPreferredSize(new java.awt.Dimension(200, 35));

        determinacion34.setColumns(10);
        determinacion34.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        determinacion34.setLineWrap(true);
        determinacion34.setRows(5);
        jScrollPane136.setViewportView(determinacion34);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 35;
        gridBagConstraints.insets = new java.awt.Insets(0, 30, 0, 0);
        jPanel1.add(jScrollPane136, gridBagConstraints);

        jScrollPane137.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane137.setMaximumSize(new java.awt.Dimension(200, 50));
        jScrollPane137.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane137.setPreferredSize(new java.awt.Dimension(200, 35));

        metodo34.setColumns(10);
        metodo34.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        metodo34.setLineWrap(true);
        metodo34.setRows(5);
        jScrollPane137.setViewportView(metodo34);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 35;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 30);
        jPanel1.add(jScrollPane137, gridBagConstraints);

        jScrollPane81.setViewportView(jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane81)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane81)
        );

        jScrollPane81.getVerticalScrollBar().setUnitIncrement(16);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        Map m = new HashMap();
        // DETERMINACIONES (1-34)
        m.put("determinacion1", determinacion1.getText());
        m.put("determinacion2", determinacion2.getText());
        m.put("determinacion3", determinacion3.getText());
        m.put("determinacion4", determinacion4.getText());
        m.put("determinacion5", determinacion5.getText());
        m.put("determinacion6", determinacion6.getText());
        m.put("determinacion7", determinacion7.getText());
        m.put("determinacion8", determinacion8.getText());
        m.put("determinacion9", determinacion9.getText());
        m.put("determinacion10", determinacion10.getText());
        m.put("determinacion11", determinacion11.getText());
        m.put("determinacion12", determinacion12.getText());
        m.put("determinacion13", determinacion13.getText());
        m.put("determinacion14", determinacion14.getText());
        m.put("determinacion15", determinacion15.getText());
        m.put("determinacion16", determinacion16.getText());
        m.put("determinacion17", determinacion17.getText());
        m.put("determinacion18", determinacion18.getText());
        m.put("determinacion19", determinacion19.getText());
        m.put("determinacion20", determinacion20.getText());
        m.put("determinacion21", determinacion21.getText());
        m.put("determinacion22", determinacion22.getText());
        m.put("determinacion23", determinacion23.getText());
        m.put("determinacion24", determinacion24.getText());
        m.put("determinacion25", determinacion25.getText());
        m.put("determinacion26", determinacion26.getText());
        m.put("determinacion27", determinacion27.getText());
        m.put("determinacion28", determinacion28.getText());
        m.put("determinacion29", determinacion29.getText());
        m.put("determinacion30", determinacion30.getText());
        m.put("determinacion31", determinacion31.getText());
        m.put("determinacion32", determinacion32.getText());
        m.put("determinacion33", determinacion33.getText());
        m.put("determinacion34", determinacion34.getText());

// RECUENTOS OBTENIDOS (1-34)
        m.put("recuentoObtenido1", recuentoObtenido1.getText());
        m.put("recuentoObtenido2", recuentoObtenido2.getText());
        m.put("recuentoObtenido3", recuentoObtenido3.getText());
        m.put("recuentoObtenido4", recuentoObtenido4.getText());
        m.put("recuentoObtenido5", recuentoObtenido5.getText());
        m.put("recuentoObtenido6", recuentoObtenido6.getText());
        m.put("recuentoObtenido7", recuentoObtenido7.getText());
        m.put("recuentoObtenido8", recuentoObtenido8.getText());
        m.put("recuentoObtenido9", recuentoObtenido9.getText());
        m.put("recuentoObtenido10", recuentoObtenido10.getText());
        m.put("recuentoObtenido11", recuentoObtenido11.getText());
        m.put("recuentoObtenido12", recuentoObtenido12.getText());
        m.put("recuentoObtenido13", recuentoObtenido13.getText());
        m.put("recuentoObtenido14", recuentoObtenido14.getText());
        m.put("recuentoObtenido15", recuentoObtenido15.getText());
        m.put("recuentoObtenido16", recuentoObtenido16.getText());
        m.put("recuentoObtenido17", recuentoObtenido17.getText());
        m.put("recuentoObtenido18", recuentoObtenido18.getText());
        m.put("recuentoObtenido19", recuentoObtenido19.getText());
        m.put("recuentoObtenido20", recuentoObtenido20.getText());
        m.put("recuentoObtenido21", recuentoObtenido21.getText());
        m.put("recuentoObtenido22", recuentoObtenido22.getText());
        m.put("recuentoObtenido23", recuentoObtenido23.getText());
        m.put("recuentoObtenido24", recuentoObtenido24.getText());
        m.put("recuentoObtenido25", recuentoObtenido25.getText());
        m.put("recuentoObtenido26", recuentoObtenido26.getText());
        m.put("recuentoObtenido27", recuentoObtenido27.getText());
        m.put("recuentoObtenido28", recuentoObtenido28.getText());
        m.put("recuentoObtenido29", recuentoObtenido29.getText());
        m.put("recuentoObtenido30", recuentoObtenido30.getText());
        m.put("recuentoObtenido31", recuentoObtenido31.getText());
        m.put("recuentoObtenido32", recuentoObtenido32.getText());
        m.put("recuentoObtenido33", recuentoObtenido33.getText());
        m.put("recuentoObtenido34", recuentoObtenido34.getText());

// RECUENTOS NORMALES (1-34)
        m.put("recuentoNormal1", recuentoNormal1.getText());
        m.put("recuentoNormal2", recuentoNormal2.getText());
        m.put("recuentoNormal3", recuentoNormal3.getText());
        m.put("recuentoNormal4", recuentoNormal4.getText());
        m.put("recuentoNormal5", recuentoNormal5.getText());
        m.put("recuentoNormal6", recuentoNormal6.getText());
        m.put("recuentoNormal7", recuentoNormal7.getText());
        m.put("recuentoNormal8", recuentoNormal8.getText());
        m.put("recuentoNormal9", recuentoNormal9.getText());
        m.put("recuentoNormal10", recuentoNormal10.getText());
        m.put("recuentoNormal11", recuentoNormal11.getText());
        m.put("recuentoNormal12", recuentoNormal12.getText());
        m.put("recuentoNormal13", recuentoNormal13.getText());
        m.put("recuentoNormal14", recuentoNormal14.getText());
        m.put("recuentoNormal15", recuentoNormal15.getText());
        m.put("recuentoNormal16", recuentoNormal16.getText());
        m.put("recuentoNormal17", recuentoNormal17.getText());
        m.put("recuentoNormal18", recuentoNormal18.getText());
        m.put("recuentoNormal19", recuentoNormal19.getText());
        m.put("recuentoNormal20", recuentoNormal20.getText());
        m.put("recuentoNormal21", recuentoNormal21.getText());
        m.put("recuentoNormal22", recuentoNormal22.getText());
        m.put("recuentoNormal23", recuentoNormal23.getText());
        m.put("recuentoNormal24", recuentoNormal24.getText());
        m.put("recuentoNormal25", recuentoNormal25.getText());
        m.put("recuentoNormal26", recuentoNormal26.getText());
        m.put("recuentoNormal27", recuentoNormal27.getText());
        m.put("recuentoNormal28", recuentoNormal28.getText());
        m.put("recuentoNormal29", recuentoNormal29.getText());
        m.put("recuentoNormal30", recuentoNormal30.getText());
        m.put("recuentoNormal31", recuentoNormal31.getText());
        m.put("recuentoNormal32", recuentoNormal32.getText());
        m.put("recuentoNormal33", recuentoNormal33.getText());
        m.put("recuentoNormal34", recuentoNormal34.getText());

// MÉTODOS (1-34)
        m.put("metodo1", metodo1.getText());
        m.put("metodo2", metodo2.getText());
        m.put("metodo3", metodo3.getText());
        m.put("metodo4", metodo4.getText());
        m.put("metodo5", metodo5.getText());
        m.put("metodo6", metodo6.getText());
        m.put("metodo7", metodo7.getText());
        m.put("metodo8", metodo8.getText());
        m.put("metodo9", metodo9.getText());
        m.put("metodo10", metodo10.getText());
        m.put("metodo11", metodo11.getText());
        m.put("metodo12", metodo12.getText());
        m.put("metodo13", metodo13.getText());
        m.put("metodo14", metodo14.getText());
        m.put("metodo15", metodo15.getText());
        m.put("metodo16", metodo16.getText());
        m.put("metodo17", metodo17.getText());
        m.put("metodo18", metodo18.getText());
        m.put("metodo19", metodo19.getText());
        m.put("metodo20", metodo20.getText());
        m.put("metodo21", metodo21.getText());
        m.put("metodo22", metodo22.getText());
        m.put("metodo23", metodo23.getText());
        m.put("metodo24", metodo24.getText());
        m.put("metodo25", metodo25.getText());
        m.put("metodo26", metodo26.getText());
        m.put("metodo27", metodo27.getText());
        m.put("metodo28", metodo28.getText());
        m.put("metodo29", metodo29.getText());
        m.put("metodo30", metodo30.getText());
        m.put("metodo31", metodo31.getText());
        m.put("metodo32", metodo32.getText());
        m.put("metodo33", metodo33.getText());
        m.put("metodo34", metodo34.getText());
        m.put("idmuestras", id);
        m.put("mostrar", buttonGroup2.getSelection().getActionCommand());

        try {
            java.util.Date fm = cajaFechaAnalisis.getDate(); //obtener fecha
            Long dm = fm.getTime(); //sacar timepo
            java.sql.Date fechaAnalisis = new java.sql.Date(dm); //cast de fecha
            m.put("fechaAnalisis", fechaAnalisis);
            String observaciones;
            if (editar) {
                resultadoRepository.guardarMostrar(id, m.get("mostrar").toString());
                File rv = new File(c.recuperarRutas("Reportes") + "\\" + pdf);
                File rn = new File(c.recuperarRutas("Reportes") + "\\(BORRADO) " + pdf);
                rv.renameTo(rn);
                m.put("titulo", JOptionPane.showInputDialog("Ingrese el título del análsis", auxTitulo));
                observaciones = JOptionPane.showInputDialog("Ingrese la observación", auxObservacion);
                observaciones = observaciones.isBlank() ? "" : observaciones.trim().endsWith(".") ? observaciones : observaciones + ".";
                muestraRepository.guardarObservaciones(observaciones, id);
                muestraRepository.guardarConclusion(crearConclusion(), id);
                muestraRepository.guardarFechaAnalisis(m);
                if (resultadoRepository.editarResultadoManual(m)) {
                    this.dispose();
                    c.generarReporteManual(id, procedencia);
                }
            } else {
                c.guardarMarca(id, m.get("mostrar").toString());
                m.put("titulo", JOptionPane.showInputDialog("Ingrese el título del análsis"));
                observaciones = JOptionPane.showInputDialog("Ingrese la observación");
                observaciones = observaciones.trim().endsWith(".") ? observaciones : observaciones + ".";
                muestraRepository.guardarObservaciones(observaciones, id);
                muestraRepository.guardarConclusion(crearConclusion(), id);
                muestraRepository.guardarFechaAnalisis(m);
                if (resultadoRepository.guardarResultadoManual(m)) {
                    this.dispose();
                    c.generarReporteManual(id, procedencia);
                }
            }
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(null, "Ingrese la fecha de análisis.");
        }
    }
    private void itemCopiarActionPerformed(java.awt.event.ActionEvent evt) {
        SwingUtilities.convertPointFromScreen(mousePoint, this);
        JTextArea jta = (JTextArea) SwingUtilities.getDeepestComponentAt(this, mousePoint.x, mousePoint.y);
        Clipboard cp = Toolkit.getDefaultToolkit().getSystemClipboard();
        StringSelection data = new StringSelection(jta.getText());
        cp.setContents(data, null);
    }

    private void itemPegarActionPerformed(java.awt.event.ActionEvent evt) {
        Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable transferable = cb.getContents(null);
        SwingUtilities.convertPointFromScreen(mousePoint, this);

        JTextArea jta = (JTextArea) SwingUtilities.getDeepestComponentAt(this, mousePoint.x, mousePoint.y);
        if (transferable.isDataFlavorSupported(DataFlavor.stringFlavor)) {
            try {
                jta.setText(transferable.getTransferData(DataFlavor.stringFlavor).toString());
            } catch (UnsupportedFlavorException ex) {
                System.getLogger(TablaManual.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            } catch (IOException ex) {
                System.getLogger(TablaManual.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
        }
    }

    private javax.swing.JRadioButton botonMostrar;
    private javax.swing.JRadioButton botonNoMostrar;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private com.toedter.calendar.JDateChooser cajaFechaAnalisis;
    private javax.swing.JTextArea determinacion1;
    private javax.swing.JTextArea determinacion10;
    private javax.swing.JTextArea determinacion11;
    private javax.swing.JTextArea determinacion12;
    private javax.swing.JTextArea determinacion13;
    private javax.swing.JTextArea determinacion14;
    private javax.swing.JTextArea determinacion15;
    private javax.swing.JTextArea determinacion16;
    private javax.swing.JTextArea determinacion17;
    private javax.swing.JTextArea determinacion18;
    private javax.swing.JTextArea determinacion19;
    private javax.swing.JTextArea determinacion2;
    private javax.swing.JTextArea determinacion20;
    private javax.swing.JTextArea determinacion21;
    private javax.swing.JTextArea determinacion22;
    private javax.swing.JTextArea determinacion23;
    private javax.swing.JTextArea determinacion24;
    private javax.swing.JTextArea determinacion25;
    private javax.swing.JTextArea determinacion26;
    private javax.swing.JTextArea determinacion27;
    private javax.swing.JTextArea determinacion28;
    private javax.swing.JTextArea determinacion29;
    private javax.swing.JTextArea determinacion3;
    private javax.swing.JTextArea determinacion30;
    private javax.swing.JTextArea determinacion31;
    private javax.swing.JTextArea determinacion32;
    private javax.swing.JTextArea determinacion33;
    private javax.swing.JTextArea determinacion34;
    private javax.swing.JTextArea determinacion4;
    private javax.swing.JTextArea determinacion5;
    private javax.swing.JTextArea determinacion6;
    private javax.swing.JTextArea determinacion7;
    private javax.swing.JTextArea determinacion8;
    private javax.swing.JTextArea determinacion9;
    private javax.swing.JMenuItem itemCopiar;
    private javax.swing.JMenuItem itemPegar;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane100;
    private javax.swing.JScrollPane jScrollPane101;
    private javax.swing.JScrollPane jScrollPane102;
    private javax.swing.JScrollPane jScrollPane103;
    private javax.swing.JScrollPane jScrollPane104;
    private javax.swing.JScrollPane jScrollPane105;
    private javax.swing.JScrollPane jScrollPane106;
    private javax.swing.JScrollPane jScrollPane107;
    private javax.swing.JScrollPane jScrollPane108;
    private javax.swing.JScrollPane jScrollPane109;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane110;
    private javax.swing.JScrollPane jScrollPane111;
    private javax.swing.JScrollPane jScrollPane112;
    private javax.swing.JScrollPane jScrollPane113;
    private javax.swing.JScrollPane jScrollPane114;
    private javax.swing.JScrollPane jScrollPane115;
    private javax.swing.JScrollPane jScrollPane116;
    private javax.swing.JScrollPane jScrollPane117;
    private javax.swing.JScrollPane jScrollPane118;
    private javax.swing.JScrollPane jScrollPane119;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane120;
    private javax.swing.JScrollPane jScrollPane121;
    private javax.swing.JScrollPane jScrollPane122;
    private javax.swing.JScrollPane jScrollPane123;
    private javax.swing.JScrollPane jScrollPane124;
    private javax.swing.JScrollPane jScrollPane125;
    private javax.swing.JScrollPane jScrollPane126;
    private javax.swing.JScrollPane jScrollPane127;
    private javax.swing.JScrollPane jScrollPane128;
    private javax.swing.JScrollPane jScrollPane129;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane130;
    private javax.swing.JScrollPane jScrollPane131;
    private javax.swing.JScrollPane jScrollPane132;
    private javax.swing.JScrollPane jScrollPane133;
    private javax.swing.JScrollPane jScrollPane134;
    private javax.swing.JScrollPane jScrollPane135;
    private javax.swing.JScrollPane jScrollPane136;
    private javax.swing.JScrollPane jScrollPane137;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane15;
    private javax.swing.JScrollPane jScrollPane16;
    private javax.swing.JScrollPane jScrollPane17;
    private javax.swing.JScrollPane jScrollPane18;
    private javax.swing.JScrollPane jScrollPane19;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane20;
    private javax.swing.JScrollPane jScrollPane21;
    private javax.swing.JScrollPane jScrollPane22;
    private javax.swing.JScrollPane jScrollPane23;
    private javax.swing.JScrollPane jScrollPane24;
    private javax.swing.JScrollPane jScrollPane25;
    private javax.swing.JScrollPane jScrollPane26;
    private javax.swing.JScrollPane jScrollPane27;
    private javax.swing.JScrollPane jScrollPane28;
    private javax.swing.JScrollPane jScrollPane29;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane30;
    private javax.swing.JScrollPane jScrollPane31;
    private javax.swing.JScrollPane jScrollPane32;
    private javax.swing.JScrollPane jScrollPane33;
    private javax.swing.JScrollPane jScrollPane34;
    private javax.swing.JScrollPane jScrollPane35;
    private javax.swing.JScrollPane jScrollPane36;
    private javax.swing.JScrollPane jScrollPane37;
    private javax.swing.JScrollPane jScrollPane38;
    private javax.swing.JScrollPane jScrollPane39;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane40;
    private javax.swing.JScrollPane jScrollPane41;
    private javax.swing.JScrollPane jScrollPane42;
    private javax.swing.JScrollPane jScrollPane43;
    private javax.swing.JScrollPane jScrollPane44;
    private javax.swing.JScrollPane jScrollPane45;
    private javax.swing.JScrollPane jScrollPane46;
    private javax.swing.JScrollPane jScrollPane47;
    private javax.swing.JScrollPane jScrollPane48;
    private javax.swing.JScrollPane jScrollPane49;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane50;
    private javax.swing.JScrollPane jScrollPane51;
    private javax.swing.JScrollPane jScrollPane52;
    private javax.swing.JScrollPane jScrollPane53;
    private javax.swing.JScrollPane jScrollPane54;
    private javax.swing.JScrollPane jScrollPane55;
    private javax.swing.JScrollPane jScrollPane56;
    private javax.swing.JScrollPane jScrollPane57;
    private javax.swing.JScrollPane jScrollPane58;
    private javax.swing.JScrollPane jScrollPane59;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane60;
    private javax.swing.JScrollPane jScrollPane61;
    private javax.swing.JScrollPane jScrollPane62;
    private javax.swing.JScrollPane jScrollPane63;
    private javax.swing.JScrollPane jScrollPane64;
    private javax.swing.JScrollPane jScrollPane65;
    private javax.swing.JScrollPane jScrollPane66;
    private javax.swing.JScrollPane jScrollPane67;
    private javax.swing.JScrollPane jScrollPane68;
    private javax.swing.JScrollPane jScrollPane69;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane70;
    private javax.swing.JScrollPane jScrollPane71;
    private javax.swing.JScrollPane jScrollPane72;
    private javax.swing.JScrollPane jScrollPane73;
    private javax.swing.JScrollPane jScrollPane74;
    private javax.swing.JScrollPane jScrollPane75;
    private javax.swing.JScrollPane jScrollPane76;
    private javax.swing.JScrollPane jScrollPane77;
    private javax.swing.JScrollPane jScrollPane78;
    private javax.swing.JScrollPane jScrollPane79;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane80;
    private javax.swing.JScrollPane jScrollPane81;
    private javax.swing.JScrollPane jScrollPane82;
    private javax.swing.JScrollPane jScrollPane83;
    private javax.swing.JScrollPane jScrollPane84;
    private javax.swing.JScrollPane jScrollPane85;
    private javax.swing.JScrollPane jScrollPane86;
    private javax.swing.JScrollPane jScrollPane87;
    private javax.swing.JScrollPane jScrollPane88;
    private javax.swing.JScrollPane jScrollPane89;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JScrollPane jScrollPane90;
    private javax.swing.JScrollPane jScrollPane91;
    private javax.swing.JScrollPane jScrollPane92;
    private javax.swing.JScrollPane jScrollPane93;
    private javax.swing.JScrollPane jScrollPane94;
    private javax.swing.JScrollPane jScrollPane95;
    private javax.swing.JScrollPane jScrollPane96;
    private javax.swing.JScrollPane jScrollPane97;
    private javax.swing.JScrollPane jScrollPane98;
    private javax.swing.JScrollPane jScrollPane99;
    private javax.swing.JTextArea metodo1;
    private javax.swing.JTextArea metodo10;
    private javax.swing.JTextArea metodo11;
    private javax.swing.JTextArea metodo12;
    private javax.swing.JTextArea metodo13;
    private javax.swing.JTextArea metodo14;
    private javax.swing.JTextArea metodo15;
    private javax.swing.JTextArea metodo16;
    private javax.swing.JTextArea metodo17;
    private javax.swing.JTextArea metodo18;
    private javax.swing.JTextArea metodo19;
    private javax.swing.JTextArea metodo2;
    private javax.swing.JTextArea metodo20;
    private javax.swing.JTextArea metodo21;
    private javax.swing.JTextArea metodo22;
    private javax.swing.JTextArea metodo23;
    private javax.swing.JTextArea metodo24;
    private javax.swing.JTextArea metodo25;
    private javax.swing.JTextArea metodo26;
    private javax.swing.JTextArea metodo27;
    private javax.swing.JTextArea metodo28;
    private javax.swing.JTextArea metodo29;
    private javax.swing.JTextArea metodo3;
    private javax.swing.JTextArea metodo30;
    private javax.swing.JTextArea metodo31;
    private javax.swing.JTextArea metodo32;
    private javax.swing.JTextArea metodo33;
    private javax.swing.JTextArea metodo34;
    private javax.swing.JTextArea metodo4;
    private javax.swing.JTextArea metodo5;
    private javax.swing.JTextArea metodo6;
    private javax.swing.JTextArea metodo7;
    private javax.swing.JTextArea metodo8;
    private javax.swing.JTextArea metodo9;
    private javax.swing.JPopupMenu popupMenu;
    private javax.swing.JRadioButton radioBien;
    private javax.swing.JRadioButton radioMal;
    private javax.swing.JRadioButton radioManual;
    private javax.swing.JTextArea recuentoNormal1;
    private javax.swing.JTextArea recuentoNormal10;
    private javax.swing.JTextArea recuentoNormal11;
    private javax.swing.JTextArea recuentoNormal12;
    private javax.swing.JTextArea recuentoNormal13;
    private javax.swing.JTextArea recuentoNormal14;
    private javax.swing.JTextArea recuentoNormal15;
    private javax.swing.JTextArea recuentoNormal16;
    private javax.swing.JTextArea recuentoNormal17;
    private javax.swing.JTextArea recuentoNormal18;
    private javax.swing.JTextArea recuentoNormal19;
    private javax.swing.JTextArea recuentoNormal2;
    private javax.swing.JTextArea recuentoNormal20;
    private javax.swing.JTextArea recuentoNormal21;
    private javax.swing.JTextArea recuentoNormal22;
    private javax.swing.JTextArea recuentoNormal23;
    private javax.swing.JTextArea recuentoNormal24;
    private javax.swing.JTextArea recuentoNormal25;
    private javax.swing.JTextArea recuentoNormal26;
    private javax.swing.JTextArea recuentoNormal27;
    private javax.swing.JTextArea recuentoNormal28;
    private javax.swing.JTextArea recuentoNormal29;
    private javax.swing.JTextArea recuentoNormal3;
    private javax.swing.JTextArea recuentoNormal30;
    private javax.swing.JTextArea recuentoNormal31;
    private javax.swing.JTextArea recuentoNormal32;
    private javax.swing.JTextArea recuentoNormal33;
    private javax.swing.JTextArea recuentoNormal34;
    private javax.swing.JTextArea recuentoNormal4;
    private javax.swing.JTextArea recuentoNormal5;
    private javax.swing.JTextArea recuentoNormal6;
    private javax.swing.JTextArea recuentoNormal7;
    private javax.swing.JTextArea recuentoNormal8;
    private javax.swing.JTextArea recuentoNormal9;
    private javax.swing.JTextArea recuentoObtenido1;
    private javax.swing.JTextArea recuentoObtenido10;
    private javax.swing.JTextArea recuentoObtenido11;
    private javax.swing.JTextArea recuentoObtenido12;
    private javax.swing.JTextArea recuentoObtenido13;
    private javax.swing.JTextArea recuentoObtenido14;
    private javax.swing.JTextArea recuentoObtenido15;
    private javax.swing.JTextArea recuentoObtenido16;
    private javax.swing.JTextArea recuentoObtenido17;
    private javax.swing.JTextArea recuentoObtenido18;
    private javax.swing.JTextArea recuentoObtenido19;
    private javax.swing.JTextArea recuentoObtenido2;
    private javax.swing.JTextArea recuentoObtenido20;
    private javax.swing.JTextArea recuentoObtenido21;
    private javax.swing.JTextArea recuentoObtenido22;
    private javax.swing.JTextArea recuentoObtenido23;
    private javax.swing.JTextArea recuentoObtenido24;
    private javax.swing.JTextArea recuentoObtenido25;
    private javax.swing.JTextArea recuentoObtenido26;
    private javax.swing.JTextArea recuentoObtenido27;
    private javax.swing.JTextArea recuentoObtenido28;
    private javax.swing.JTextArea recuentoObtenido29;
    private javax.swing.JTextArea recuentoObtenido3;
    private javax.swing.JTextArea recuentoObtenido30;
    private javax.swing.JTextArea recuentoObtenido31;
    private javax.swing.JTextArea recuentoObtenido32;
    private javax.swing.JTextArea recuentoObtenido33;
    private javax.swing.JTextArea recuentoObtenido34;
    private javax.swing.JTextArea recuentoObtenido4;
    private javax.swing.JTextArea recuentoObtenido5;
    private javax.swing.JTextArea recuentoObtenido6;
    private javax.swing.JTextArea recuentoObtenido7;
    private javax.swing.JTextArea recuentoObtenido8;
    private javax.swing.JTextArea recuentoObtenido9;

    private String crearConclusion() {
        String conclusion = "";
        if (radioBien.isSelected()) {
            conclusion = "En las determinaciones realizadas la muestra cumple con el art. 982 del Código Alimentario Argentino (ley 18284)";
        } else if (radioMal.isSelected()) {
            if (this.editar) {
                conclusion = "En las determinaciones realizadas la muestra no cumple con el art. 982 del Código Alimentario Argentino (ley 18284) debido a " + JOptionPane.showInputDialog("No cumple debido a:", auxConclusion);
            } else {
                conclusion = "En las determinaciones realizadas la muestra no cumple con el art. 982 del Código Alimentario Argentino (ley 18284) debido a " + JOptionPane.showInputDialog("No cumple debido a:");
            }
        } else if (radioManual.isSelected()) {
            if (this.editar) {
                conclusion = JOptionPane.showInputDialog("Ingrese la conclusión:", muestraRepository.recuperarConclusion(id));
            } else {
                conclusion = JOptionPane.showInputDialog("Ingrese la conclusión:");
            }
        }
        conclusion = conclusion.replace("poes", "POES").replace("bpm", "BPM");
        conclusion = conclusion.isBlank() ? "" : conclusion.equals("-") ? "-" : conclusion.trim().endsWith(".") ? conclusion : conclusion + ".";
        return conclusion;
    }

    private void getMousePoint() {
        mousePoint = MouseInfo.getPointerInfo().getLocation();
    }

    private void addRightClick(javax.swing.JTextArea field) {
        field.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent e) {
                if (javax.swing.SwingUtilities.isRightMouseButton(e)) {
                    getMousePoint();
                }
            }
        });
    }

}
