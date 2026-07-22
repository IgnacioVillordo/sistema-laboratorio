package org.ignaciorodriguez.modelo;

import com.mysql.cj.util.StringUtils;
import org.apache.poi.xddf.usermodel.chart.*;
import org.ignaciorodriguez.Main;
import org.ignaciorodriguez.vista.Principal;
import org.ignaciorodriguez.vista.VentanaEmail;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static java.awt.TextArea.SCROLLBARS_NONE;

public class Consultas extends Conexion {

    public final static int NO_ACTUALIZAR = 0;
    public final static int ACTUALIZAR = 1;
    public final static int ERROR_ACTUALIZAR = -1;
    public static int tipousuario;
    private static Consultas instancia;
    public String caracteres;
    public boolean email = false;
    int contadorCreado = 0;
    Conexion con = new Conexion();
    PreparedStatement ps = null;
    ResultSet rs = null;
    String nombreUsuario = null;
    //Principal p = new Principal();
    String vencimientoMb = "INSERT INTO `laboratorio`.`vencimientos` " + "(`fechaVencimiento`,idmuestras) VALUES (date_add(?,interval 6 month),?)";
    String vencimientoFq = "INSERT INTO `laboratorio`.`vencimientos` " + "(`fechaVencimiento`,idmuestras) VALUES (date_add(?,interval 12 month),?)";

    DefaultTableModel modeloEntregas = new DefaultTableModel();
    DefaultTableModel modeloAnalisis = new DefaultTableModel() {
        @Override
        public Class getColumnClass(int columnIndex) {
            switch (columnIndex) {
                case 0: {
                    return Boolean.class;
                }
                default: {
                    return String.class;
                }
            }
        }
    };
    DefaultTableModel modeloVencimientos = new DefaultTableModel() {
        @Override
        public Class getColumnClass(int columnIndex) {
            Class clazz = String.class;
            switch (columnIndex) {
                case 4: {
                    clazz = Boolean.class;
                }
            }
            return clazz;
        }
    };
    BufferedWriter bw = null;
    File archivo = new File("src" + org.ignaciorodriguez.utils.SeparatorUtils.s + "vista" + org.ignaciorodriguez.utils.SeparatorUtils.s + "actualizacion.txt");
    DefaultTableModel modeloGanancias = new DefaultTableModel();

    private Consultas() {
    }

    public static Consultas getInstancia() {
        if (instancia == null) {
            instancia = new Consultas();
        }
        return instancia;
    }
    public boolean seleccionarVencimiento(int id, int seleccionar) {
        try (Connection conexion = con.getConnection()) {
            PreparedStatement ps = conexion.prepareStatement("update administracion set seleccionadoVencimiento = ? where idmuestras = ?");
            ps.setInt(1, seleccionar);
            ps.setInt(2, id);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar vencimiento, " + e);
            return false;
        } finally {
            try (Connection conexion = con.getConnection()) {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
    }

    public void generarReporteMBAgua(int id, String procedencia) {
        Connection conexion = con.getConnection();
        Principal p = new Principal();
        try {
            JasperReport reporte = null;
            String ruta = getClass().getResource("/reporte/reporteMBAgua.jasper").getPath();
            reporte = (JasperReport) JRLoader.loadObjectFromFile(ruta);//se toma el archivo .jasper para generar el main.resources.reporte
            Map mapa = new HashMap();
            mapa.put("id", id); //se pasa el id al main.resources.reporte
            imagenesMap(mapa);
            JasperPrint imprimirReporte = JasperFillManager.fillReport(reporte, mapa, conexion);            //llena los campos del main.resources.reporte
            JasperViewer vistaReporte = new JasperViewer(imprimirReporte, false);                           //crea el visor del main.resources.reporte
            String[] nombre = obtenerProcedenciayNombreEmail(obtenerIdCliente(procedencia));
            String aux; //en estas tres lineas se sacan espacios de
            if (nombre[0].contains("/") || nombre[0].contains("" + org.ignaciorodriguez.utils.SeparatorUtils.s + "") || nombre[0].contains(":") || nombre[0].contains("*") || nombre[0].contains("?") || nombre[0].contains("\"") || nombre[0].contains("<") || nombre[0].contains(">") || nombre[0].contains(">") || nombre[1].contains("/") || nombre[1].contains("" + org.ignaciorodriguez.utils.SeparatorUtils.s + "") || nombre[1].contains(":") || nombre[1].contains("*") || nombre[1].contains("?") || nombre[1].contains("\"") || nombre[1].contains("<") || nombre[1].contains(">") || nombre[1].contains(">")) {
                nombre[0] = nombre[0].replaceAll("[/" + org.ignaciorodriguez.utils.SeparatorUtils.s + "" + org.ignaciorodriguez.utils.SeparatorUtils.s + ":*?\"<>|]", "_");
                nombre[1] = nombre[1].replaceAll("[/" + org.ignaciorodriguez.utils.SeparatorUtils.s + "" + org.ignaciorodriguez.utils.SeparatorUtils.s + ":*?\"<>|]", "_");
            }
            if (nombre[1].isBlank() || nombre[1] == null) {
                aux = "" + org.ignaciorodriguez.utils.SeparatorUtils.s + "Inf. " + id + " MB Agua " + nombre[0];
            } else {
                aux = "" + org.ignaciorodriguez.utils.SeparatorUtils.s + "Inf. " + id + " MB Agua " + nombre[1];
            }
            String aux2 = aux.replaceAll("^" + org.ignaciorodriguez.utils.SeparatorUtils.s + "s*", "");                                                       //principio, final y se sacan las comillas
            String aux3 = aux2.replaceAll("" + org.ignaciorodriguez.utils.SeparatorUtils.s + "s*$", "");
            String pdf = aux3.replaceAll("\"", "");
            pdf += ".pdf";                                                                                  //se agrega la extensión .pdf
            String rutaGuardado = recuperarRutas("Reportes");
            var pdfEmail = rutaGuardado + pdf;
            JasperExportManager.exportReportToPdfFile(imprimirReporte, pdfEmail);
            vistaReporte.setDefaultCloseOperation(JasperViewer.DO_NOTHING_ON_CLOSE);
            vistaReporte.setExtendedState(vistaReporte.MAXIMIZED_BOTH);
            ImageIcon icon = new ImageIcon("src" + org.ignaciorodriguez.utils.SeparatorUtils.s + "vista" + org.ignaciorodriguez.utils.SeparatorUtils.s + "icono.png");
            vistaReporte.setIconImage(icon.getImage());
            vistaReporte.setVisible(true);
            vistaReporte.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    int email = JOptionPane.showConfirmDialog(null, "¿Enviar email?", "Email", JOptionPane.YES_NO_OPTION);
                    ;
                    if (JOptionPane.YES_OPTION == email) {
                        String[] nombre = obtenerProcedenciayNombreEmail(obtenerIdCliente(procedencia));
                        String aux; //en estas tres lineas se sacan espacios de
                        if (nombre[0].contains("/") || nombre[0].contains("" + org.ignaciorodriguez.utils.SeparatorUtils.s + "") || nombre[0].contains(":") || nombre[0].contains("*") || nombre[0].contains("?") || nombre[0].contains("\"") || nombre[0].contains("<") || nombre[0].contains(">") || nombre[0].contains(">") || nombre[1].contains("/") || nombre[1].contains("" + org.ignaciorodriguez.utils.SeparatorUtils.s + "") || nombre[1].contains(":") || nombre[1].contains("*") || nombre[1].contains("?") || nombre[1].contains("\"") || nombre[1].contains("<") || nombre[1].contains(">") || nombre[1].contains(">")) {
                            nombre[0] = nombre[0].replaceAll("[/" + org.ignaciorodriguez.utils.SeparatorUtils.s + "" + org.ignaciorodriguez.utils.SeparatorUtils.s + ":*?\"<>|]", "_");
                            nombre[1] = nombre[1].replaceAll("[/" + org.ignaciorodriguez.utils.SeparatorUtils.s + "" + org.ignaciorodriguez.utils.SeparatorUtils.s + ":*?\"<>|]", "_");
                        }
                        if (nombre[1].isBlank() || nombre[1] == null) {
                            aux = "" + org.ignaciorodriguez.utils.SeparatorUtils.s + "Inf. " + id + " MB Agua " + nombre[0];
                        } else {
                            aux = "" + org.ignaciorodriguez.utils.SeparatorUtils.s + "Inf. " + id + " MB Agua " + nombre[1];
                        }
                        String aux2 = aux.replaceAll("^" + org.ignaciorodriguez.utils.SeparatorUtils.s + "s*", "");                                                       //principio, final y se sacan las comillas
                        String aux3 = aux2.replaceAll("" + org.ignaciorodriguez.utils.SeparatorUtils.s + "s*$", "");
                        String pdf = aux3.replaceAll("\"", "");
                        pdf += ".pdf";
                        String pdfEmail = rutaGuardado + pdf;
                        VentanaEmail vEmail = new VentanaEmail(p, true, id, pdfEmail);
                        vEmail.setVisible(true);
                    }
                }
            });
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al generar main.resources.reporte MB agua, " + e);
            System.err.println(e.getStackTrace()[0]);
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error al generar main.resources.reporte MB agua, " + e);
                System.err.println(e.getStackTrace()[0]);
            }
        }
    }

    public void generarReporteMBAguaBidon(int id, String procedencia) {
        Connection conexion = con.getConnection();
        Principal p = new Principal();
        try {
            JasperReport reporte = null;
            String ruta = getClass().getResource("/reporte/reporteMBAguaBidon.jasper").getPath();
            reporte = (JasperReport) JRLoader.loadObjectFromFile(ruta);//se toma el archivo .jasper para generar el main.resources.reporte
            Map mapa = new HashMap();
            mapa.put("id", id); //se pasa el id al main.resources.reporte
            imagenesMap(mapa);
            JasperPrint imprimirReporte = JasperFillManager.fillReport(reporte, mapa, conexion);            //llena los campos del main.resources.reporte
            JasperViewer vistaReporte = new JasperViewer(imprimirReporte, false);                           //crea el visor del main.resources.reporte
            String[] nombre = obtenerProcedenciayNombreEmail(obtenerIdCliente(procedencia));
            String aux; //en estas tres lineas se sacan espacios de
            if (nombre[0].contains("/") || nombre[0].contains("" + org.ignaciorodriguez.utils.SeparatorUtils.s + "") || nombre[0].contains(":") || nombre[0].contains("*") || nombre[0].contains("?") || nombre[0].contains("\"") || nombre[0].contains("<") || nombre[0].contains(">") || nombre[0].contains(">") || nombre[1].contains("/") || nombre[1].contains("" + org.ignaciorodriguez.utils.SeparatorUtils.s + "") || nombre[1].contains(":") || nombre[1].contains("*") || nombre[1].contains("?") || nombre[1].contains("\"") || nombre[1].contains("<") || nombre[1].contains(">") || nombre[1].contains(">")) {
                nombre[0] = nombre[0].replaceAll("[/" + org.ignaciorodriguez.utils.SeparatorUtils.s + "" + org.ignaciorodriguez.utils.SeparatorUtils.s + ":*?\"<>|]", "_");
                nombre[1] = nombre[1].replaceAll("[/" + org.ignaciorodriguez.utils.SeparatorUtils.s + "" + org.ignaciorodriguez.utils.SeparatorUtils.s + ":*?\"<>|]", "_");
            }
            if (nombre[1].isBlank() || nombre[1] == null) {
                aux = "" + org.ignaciorodriguez.utils.SeparatorUtils.s + "Inf. " + id + " MB Agua " + nombre[0];
            } else {
                aux = "" + org.ignaciorodriguez.utils.SeparatorUtils.s + "Inf. " + id + " MB Agua " + nombre[1];
            }
            String aux2 = aux.replaceAll("^" + org.ignaciorodriguez.utils.SeparatorUtils.s + "s*", "");                                                       //principio, final y se sacan las comillas
            String aux3 = aux2.replaceAll("" + org.ignaciorodriguez.utils.SeparatorUtils.s + "s*$", "");
            String pdf = aux3.replaceAll("\"", "");
            pdf += ".pdf";                                                                                  //se agrega la extensión .pdf
            String rutaGuardado = recuperarRutas("Reportes");
            JasperExportManager.exportReportToPdfFile(imprimirReporte, rutaGuardado + pdf);
            vistaReporte.setDefaultCloseOperation(JasperViewer.DO_NOTHING_ON_CLOSE);
            vistaReporte.setExtendedState(vistaReporte.MAXIMIZED_BOTH);
            ImageIcon icon = new ImageIcon("src" + org.ignaciorodriguez.utils.SeparatorUtils.s + "vista" + org.ignaciorodriguez.utils.SeparatorUtils.s + "icono.png");
            vistaReporte.setIconImage(icon.getImage());
            vistaReporte.setVisible(true);
            vistaReporte.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    int email = JOptionPane.showConfirmDialog(null, "¿Enviar email?", "Email", JOptionPane.YES_NO_OPTION);
                    ;
                    if (JOptionPane.YES_OPTION == email) {
                        String[] nombre = obtenerProcedenciayNombreEmail(obtenerIdCliente(procedencia));
                        String aux; //en estas tres lineas se sacan espacios de
                        if (nombre[0].contains("/") || nombre[0].contains("" + org.ignaciorodriguez.utils.SeparatorUtils.s + "") || nombre[0].contains(":") || nombre[0].contains("*") || nombre[0].contains("?") || nombre[0].contains("\"") || nombre[0].contains("<") || nombre[0].contains(">") || nombre[0].contains(">") || nombre[1].contains("/") || nombre[1].contains("" + org.ignaciorodriguez.utils.SeparatorUtils.s + "") || nombre[1].contains(":") || nombre[1].contains("*") || nombre[1].contains("?") || nombre[1].contains("\"") || nombre[1].contains("<") || nombre[1].contains(">") || nombre[1].contains(">")) {
                            nombre[0] = nombre[0].replaceAll("[/" + org.ignaciorodriguez.utils.SeparatorUtils.s + "" + org.ignaciorodriguez.utils.SeparatorUtils.s + ":*?\"<>|]", "_");
                            nombre[1] = nombre[1].replaceAll("[/" + org.ignaciorodriguez.utils.SeparatorUtils.s + "" + org.ignaciorodriguez.utils.SeparatorUtils.s + ":*?\"<>|]", "_");
                        }
                        if (nombre[1].isBlank() || nombre[1] == null) {
                            aux = "" + org.ignaciorodriguez.utils.SeparatorUtils.s + "Inf. " + id + " MB Agua " + nombre[0];
                        } else {
                            aux = "" + org.ignaciorodriguez.utils.SeparatorUtils.s + "Inf. " + id + " MB Agua " + nombre[1];
                        }
                        String aux2 = aux.replaceAll("^" + org.ignaciorodriguez.utils.SeparatorUtils.s + "s*", "");                                                       //principio, final y se sacan las comillas
                        String aux3 = aux2.replaceAll("" + org.ignaciorodriguez.utils.SeparatorUtils.s + "s*$", "");
                        String pdf = aux3.replaceAll("\"", "");
                        pdf += ".pdf";
                        String pdfEmail = rutaGuardado + pdf;
                        VentanaEmail vEmail = new VentanaEmail(p, true, id, pdfEmail);
                        vEmail.setVisible(true);
                    }
                }
            });
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al generar main.resources.reporte MB agua, " + e);
            System.err.println(e.getStackTrace()[0]);

        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error al generar main.resources.reporte MB agua, " + e);
                System.err.println(e.getStackTrace()[0]);

            }
        }
    }

    public void generarReporteManual(int id, String procedencia) {
        Connection conexion = con.getConnection();
        Principal p = new Principal();
        try {
            JasperReport reporte = null;
            String ruta = getClass().getResource("/reporte/reporteManual.jasper").getPath();
            reporte = (JasperReport) JRLoader.loadObjectFromFile(ruta);//se toma el archivo .jasper para generar el main.resources.reporte
            Map mapa = new HashMap();
            mapa.put("id", id); //se pasa el id al main.resources.reporte
            imagenesMap(mapa);
            JasperPrint imprimirReporte = JasperFillManager.fillReport(reporte, mapa, conexion);            //llena los campos del main.resources.reporte
            JasperViewer vistaReporte = new JasperViewer(imprimirReporte, false);                           //crea el visor del main.resources.reporte
            String[] nombre = obtenerProcedenciayNombreEmail(obtenerIdCliente(procedencia));
            String aux; //en estas tres lineas se sacan espacios de
            String titulo = recuperarTituloManual(id);
            if (nombre[0].contains("/") || nombre[0].contains("" + org.ignaciorodriguez.utils.SeparatorUtils.s + "") || nombre[0].contains(":") || nombre[0].contains("*") || nombre[0].contains("?") || nombre[0].contains("\"") || nombre[0].contains("<") || nombre[0].contains(">") || nombre[0].contains(">") || nombre[1].contains("/") || nombre[1].contains("" + org.ignaciorodriguez.utils.SeparatorUtils.s + "") || nombre[1].contains(":") || nombre[1].contains("*") || nombre[1].contains("?") || nombre[1].contains("\"") || nombre[1].contains("<") || nombre[1].contains(">") || nombre[1].contains(">")) {
                nombre[0] = nombre[0].replaceAll("[/" + org.ignaciorodriguez.utils.SeparatorUtils.s + "" + org.ignaciorodriguez.utils.SeparatorUtils.s + ":*?\"<>|]", "_");
                nombre[1] = nombre[1].replaceAll("[/" + org.ignaciorodriguez.utils.SeparatorUtils.s + "" + org.ignaciorodriguez.utils.SeparatorUtils.s + ":*?\"<>|]", "_");
            }
            if (nombre[1].isBlank() || nombre[1] == null) {
                aux = "" + org.ignaciorodriguez.utils.SeparatorUtils.s + "Inf. " + id + " " + titulo + " " + nombre[0];
            } else {
                aux = "" + org.ignaciorodriguez.utils.SeparatorUtils.s + "Inf. " + id + " " + titulo + " " + nombre[1];
            }
            String aux2 = aux.replaceAll("^" + org.ignaciorodriguez.utils.SeparatorUtils.s + "s*", "");                                                       //principio, final y se sacan las comillas
            String aux3 = aux2.replaceAll("" + org.ignaciorodriguez.utils.SeparatorUtils.s + "s*$", "");
            String pdf = aux3.replaceAll("\"", "");
            pdf += ".pdf";                                                                                  //se agrega la extensión .pdf
            String rutaGuardado = recuperarRutas("Reportes");
            JasperExportManager.exportReportToPdfFile(imprimirReporte, rutaGuardado + pdf);
            vistaReporte.setDefaultCloseOperation(JasperViewer.DO_NOTHING_ON_CLOSE);
            vistaReporte.setExtendedState(vistaReporte.MAXIMIZED_BOTH);
            ImageIcon icon = new ImageIcon("src" + org.ignaciorodriguez.utils.SeparatorUtils.s + "vista" + org.ignaciorodriguez.utils.SeparatorUtils.s + "icono.png");
            vistaReporte.setIconImage(icon.getImage());
            vistaReporte.setVisible(true);
            vistaReporte.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    int email = JOptionPane.showConfirmDialog(null, "¿Enviar email?", "Email", JOptionPane.YES_NO_OPTION);
                    ;
                    if (JOptionPane.YES_OPTION == email) {
                        String[] nombre = obtenerProcedenciayNombreEmail(obtenerIdCliente(procedencia));
                        String aux; //en estas tres lineas se sacan espacios de
                        String titulo = recuperarTituloManual(id);
                        if (nombre[0].contains("/") || nombre[0].contains("" + org.ignaciorodriguez.utils.SeparatorUtils.s + "") || nombre[0].contains(":") || nombre[0].contains("*") || nombre[0].contains("?") || nombre[0].contains("\"") || nombre[0].contains("<") || nombre[0].contains(">") || nombre[0].contains(">") || nombre[1].contains("/") || nombre[1].contains("" + org.ignaciorodriguez.utils.SeparatorUtils.s + "") || nombre[1].contains(":") || nombre[1].contains("*") || nombre[1].contains("?") || nombre[1].contains("\"") || nombre[1].contains("<") || nombre[1].contains(">") || nombre[1].contains(">")) {
                            nombre[0] = nombre[0].replaceAll("[/" + org.ignaciorodriguez.utils.SeparatorUtils.s + "" + org.ignaciorodriguez.utils.SeparatorUtils.s + ":*?\"<>|]", "_");
                            nombre[1] = nombre[1].replaceAll("[/" + org.ignaciorodriguez.utils.SeparatorUtils.s + "" + org.ignaciorodriguez.utils.SeparatorUtils.s + ":*?\"<>|]", "_");
                        }
                        if (nombre[1].isBlank() || nombre[1] == null) {
                            aux = "" + org.ignaciorodriguez.utils.SeparatorUtils.s + "Inf. " + id + " " + titulo + " " + nombre[0];
                        } else {
                            aux = "" + org.ignaciorodriguez.utils.SeparatorUtils.s + "Inf. " + id + " " + titulo + " " + nombre[1];
                        }
                        String aux2 = aux.replaceAll("^" + org.ignaciorodriguez.utils.SeparatorUtils.s + "s*", "");                                                       //principio, final y se sacan las comillas
                        String aux3 = aux2.replaceAll("" + org.ignaciorodriguez.utils.SeparatorUtils.s + "s*$", "");
                        String pdf = aux3.replaceAll("\"", "");
                        pdf += ".pdf";
                        String pdfEmail = rutaGuardado + pdf;
                        VentanaEmail vEmail = new VentanaEmail(p, true, id, pdfEmail);
                        vEmail.setVisible(true);
                    }
                }
            });
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al generar main.resources.reporte MB agua, " + e);
            System.err.println(e.getStackTrace()[0]);
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error al generar main.resources.reporte MB agua, " + e);
                System.err.println(e.getStackTrace()[0]);
            }
        }
    }

    public void generarReporteMBAguaBalnearios(int id, String procedencia) {
        Connection conexion = con.getConnection();
        Principal p = new Principal();
        try {
            JasperReport reporte = null;
            String ruta = getClass().getResource("/reporte/reporteMBAguaBalnearios.jasper").getPath();
            reporte = (JasperReport) JRLoader.loadObjectFromFile(ruta);//se toma el archivo .jasper para generar el main.resources.reporte
            Map mapa = new HashMap();
            mapa.put("id", id); //se pasa el id al main.resources.reporte
            imagenesMap(mapa);
            JasperPrint imprimirReporte = JasperFillManager.fillReport(reporte, mapa, conexion);            //llena los campos del main.resources.reporte
            JasperViewer vistaReporte = new JasperViewer(imprimirReporte, false);                           //crea el visor del main.resources.reporte
            String[] nombre = obtenerProcedenciayNombreEmail(obtenerIdCliente(procedencia));
            String aux; //en estas tres lineas se sacan espacios de
            if (nombre[0].contains("/") || nombre[0].contains("" + org.ignaciorodriguez.utils.SeparatorUtils.s + "") || nombre[0].contains(":") || nombre[0].contains("*") || nombre[0].contains("?") || nombre[0].contains("\"") || nombre[0].contains("<") || nombre[0].contains(">") || nombre[0].contains(">") || nombre[1].contains("/") || nombre[1].contains("" + org.ignaciorodriguez.utils.SeparatorUtils.s + "") || nombre[1].contains(":") || nombre[1].contains("*") || nombre[1].contains("?") || nombre[1].contains("\"") || nombre[1].contains("<") || nombre[1].contains(">") || nombre[1].contains(">")) {
                nombre[0] = nombre[0].replaceAll("[/" + org.ignaciorodriguez.utils.SeparatorUtils.s + "" + org.ignaciorodriguez.utils.SeparatorUtils.s + ":*?\"<>|]", "_");
                nombre[1] = nombre[1].replaceAll("[/" + org.ignaciorodriguez.utils.SeparatorUtils.s + "" + org.ignaciorodriguez.utils.SeparatorUtils.s + ":*?\"<>|]", "_");
            }
            if (nombre[1].isBlank() || nombre[1] == null) {
                aux = "" + org.ignaciorodriguez.utils.SeparatorUtils.s + "Inf. " + id + " MB Agua balnearios " + nombre[0];
            } else {
                aux = "" + org.ignaciorodriguez.utils.SeparatorUtils.s + "Inf. " + id + " MB Agua balnearios " + nombre[1];
            }
            String aux2 = aux.replaceAll("^" + org.ignaciorodriguez.utils.SeparatorUtils.s + "s*", "");                                                       //principio, final y se sacan las comillas
            String aux3 = aux2.replaceAll("" + org.ignaciorodriguez.utils.SeparatorUtils.s + "s*$", "");
            String pdf = aux3.replaceAll("\"", "");
            pdf += ".pdf";                                                                                  //se agrega la extensión .pdf
            String rutaGuardado = recuperarRutas("Reportes");
            JasperExportManager.exportReportToPdfFile(imprimirReporte, rutaGuardado + pdf);
            vistaReporte.setDefaultCloseOperation(JasperViewer.DO_NOTHING_ON_CLOSE);
            vistaReporte.setExtendedState(vistaReporte.MAXIMIZED_BOTH);
            ImageIcon icon = new ImageIcon("src" + org.ignaciorodriguez.utils.SeparatorUtils.s + "vista" + org.ignaciorodriguez.utils.SeparatorUtils.s + "icono.png");
            vistaReporte.setIconImage(icon.getImage());
            vistaReporte.setVisible(true);
            vistaReporte.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    int email = JOptionPane.showConfirmDialog(null, "¿Enviar email?", "Email", JOptionPane.YES_NO_OPTION);
                    ;
                    if (JOptionPane.YES_OPTION == email) {
                        String[] nombre = obtenerProcedenciayNombreEmail(obtenerIdCliente(procedencia));
                        String aux; //en estas tres lineas se sacan espacios de
                        if (nombre[0].contains("/") || nombre[0].contains("" + org.ignaciorodriguez.utils.SeparatorUtils.s + "") || nombre[0].contains(":") || nombre[0].contains("*") || nombre[0].contains("?") || nombre[0].contains("\"") || nombre[0].contains("<") || nombre[0].contains(">") || nombre[0].contains(">") || nombre[1].contains("/") || nombre[1].contains("" + org.ignaciorodriguez.utils.SeparatorUtils.s + "") || nombre[1].contains(":") || nombre[1].contains("*") || nombre[1].contains("?") || nombre[1].contains("\"") || nombre[1].contains("<") || nombre[1].contains(">") || nombre[1].contains(">")) {
                            nombre[0] = nombre[0].replaceAll("[/" + org.ignaciorodriguez.utils.SeparatorUtils.s + "" + org.ignaciorodriguez.utils.SeparatorUtils.s + ":*?\"<>|]", "_");
                            nombre[1] = nombre[1].replaceAll("[/" + org.ignaciorodriguez.utils.SeparatorUtils.s + "" + org.ignaciorodriguez.utils.SeparatorUtils.s + ":*?\"<>|]", "_");
                        }
                        if (nombre[1].isBlank() || nombre[1] == null) {
                            aux = "" + org.ignaciorodriguez.utils.SeparatorUtils.s + "Inf. " + id + " MB Agua balnearios " + nombre[0];
                        } else {
                            aux = "" + org.ignaciorodriguez.utils.SeparatorUtils.s + "Inf. " + id + " MB Agua balnearios " + nombre[1];
                        }
                        String aux2 = aux.replaceAll("^" + org.ignaciorodriguez.utils.SeparatorUtils.s + "s*", "");                                                       //principio, final y se sacan las comillas
                        String aux3 = aux2.replaceAll("" + org.ignaciorodriguez.utils.SeparatorUtils.s + "s*$", "");
                        String pdf = aux3.replaceAll("\"", "");
                        pdf += ".pdf";
                        String pdfEmail = rutaGuardado + pdf;
                        VentanaEmail vEmail = new VentanaEmail(p, true, id, pdfEmail);
                        vEmail.setVisible(true);
                    }
                }
            });
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al generar main.resources.reporte MB agua balnearios, " + e);
            System.err.println(e.getStackTrace()[0]);
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error al generar main.resources.reporte MB agua, " + e);
                System.err.println(e.getStackTrace()[0]);
            }
        }
    }

    public void generarReporteFQAgua(int id, String procedencia) {
        Connection conexion = con.getConnection();
        Principal p = new Principal();
        try {
            JasperReport reporte = null;
            String ruta = String.valueOf(getClass().getResource("reporteFQAgua.jasper"));
            reporte = (JasperReport) JRLoader.loadObjectFromFile(ruta);//se toma el archivo .jasper para generar el reporte
            Map mapa = new HashMap();
            mapa.put("id", id); //se pasa el id al main.resources.reporte
            imagenesMap(mapa);
            JasperPrint imprimirReporte = JasperFillManager.fillReport(reporte, mapa, conexion);            //llena los campos del main.resources.reporte
            JasperViewer vistaReporte = new JasperViewer(imprimirReporte, false);                           //crea el visor del main.resources.reporte
            String[] nombre = obtenerProcedenciayNombreEmail(obtenerIdCliente(procedencia));
            String aux; //en estas tres lineas se sacan espacios de
            if (nombre[0].contains("/") || nombre[0].contains("" + org.ignaciorodriguez.utils.SeparatorUtils.s + "") || nombre[0].contains(":") || nombre[0].contains("*") || nombre[0].contains("?") || nombre[0].contains("\"") || nombre[0].contains("<") || nombre[0].contains(">") || nombre[0].contains(">") || nombre[1].contains("/") || nombre[1].contains("" + org.ignaciorodriguez.utils.SeparatorUtils.s + "") || nombre[1].contains(":") || nombre[1].contains("*") || nombre[1].contains("?") || nombre[1].contains("\"") || nombre[1].contains("<") || nombre[1].contains(">") || nombre[1].contains(">")) {
                nombre[0] = nombre[0].replaceAll("[/" + org.ignaciorodriguez.utils.SeparatorUtils.s + "" + org.ignaciorodriguez.utils.SeparatorUtils.s + ":*?\"<>|]", "_");
                nombre[1] = nombre[1].replaceAll("[/" + org.ignaciorodriguez.utils.SeparatorUtils.s + "" + org.ignaciorodriguez.utils.SeparatorUtils.s + ":*?\"<>|]", "_");
            }
            if (nombre[1].isBlank() || nombre[1] == null) {
                aux = "" + org.ignaciorodriguez.utils.SeparatorUtils.s + "Inf. " + id + " FQ Agua básico " + nombre[0];
            } else {
                aux = "" + org.ignaciorodriguez.utils.SeparatorUtils.s + "Inf. " + id + " FQ Agua básico " + nombre[1];
            }
            String aux2 = aux.replaceAll("^" + org.ignaciorodriguez.utils.SeparatorUtils.s + "s*", "");                                                       //principio, final y se sacan las comillas
            String aux3 = aux2.replaceAll("" + org.ignaciorodriguez.utils.SeparatorUtils.s + "s*$", "");
            String pdf = aux3.replaceAll("\"", "");
            pdf += ".pdf";
            String rutaGuardado = recuperarRutas("Reportes");
            JasperExportManager.exportReportToPdfFile(imprimirReporte, rutaGuardado + pdf);
            vistaReporte.setDefaultCloseOperation(JasperViewer.DO_NOTHING_ON_CLOSE);
            vistaReporte.setExtendedState(vistaReporte.MAXIMIZED_BOTH);
            ImageIcon icon = new ImageIcon("src" + org.ignaciorodriguez.utils.SeparatorUtils.s + "vista" + org.ignaciorodriguez.utils.SeparatorUtils.s + "icono.png");
            vistaReporte.setIconImage(icon.getImage());
            vistaReporte.setVisible(true);
            vistaReporte.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    int email = JOptionPane.showConfirmDialog(null, "¿Enviar email?", "Email", JOptionPane.YES_NO_OPTION);
                    ;
                    if (JOptionPane.YES_OPTION == email) {
                        String[] nombre = obtenerProcedenciayNombreEmail(obtenerIdCliente(procedencia));
                        String aux; //en estas tres lineas se sacan espacios de
                        if (nombre[0].contains("/") || nombre[0].contains("" + org.ignaciorodriguez.utils.SeparatorUtils.s + "") || nombre[0].contains(":") || nombre[0].contains("*") || nombre[0].contains("?") || nombre[0].contains("\"") || nombre[0].contains("<") || nombre[0].contains(">") || nombre[0].contains(">") || nombre[1].contains("/") || nombre[1].contains("" + org.ignaciorodriguez.utils.SeparatorUtils.s + "") || nombre[1].contains(":") || nombre[1].contains("*") || nombre[1].contains("?") || nombre[1].contains("\"") || nombre[1].contains("<") || nombre[1].contains(">") || nombre[1].contains(">")) {
                            nombre[0] = nombre[0].replaceAll("[/" + org.ignaciorodriguez.utils.SeparatorUtils.s + "" + org.ignaciorodriguez.utils.SeparatorUtils.s + ":*?\"<>|]", "_");
                            nombre[1] = nombre[1].replaceAll("[/" + org.ignaciorodriguez.utils.SeparatorUtils.s + "" + org.ignaciorodriguez.utils.SeparatorUtils.s + ":*?\"<>|]", "_");
                        }
                        if (nombre[1].isBlank() || nombre[1] == null) {
                            aux = "" + org.ignaciorodriguez.utils.SeparatorUtils.s + "Inf. " + id + " FQ Agua básico " + nombre[0];
                        } else {
                            aux = "" + org.ignaciorodriguez.utils.SeparatorUtils.s + "Inf. " + id + " FQ Agua básico " + nombre[1];
                        }
                        String aux2 = aux.replaceAll("^" + org.ignaciorodriguez.utils.SeparatorUtils.s + "s*", "");                                                       //principio, final y se sacan las comillas
                        String aux3 = aux2.replaceAll("" + org.ignaciorodriguez.utils.SeparatorUtils.s + "s*$", "");
                        String pdf = aux3.replaceAll("\"", "");
                        pdf += ".pdf";
                        String pdfEmail = rutaGuardado + pdf;
                        VentanaEmail vEmail = new VentanaEmail(p, true, id, pdfEmail);
                        vEmail.setVisible(true);
                    }
                }
            });
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al generar main.resources.reporte FQ agua, " + e);
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
    }



    public void agregarAdministracion(int id, double precio, int pago, int factura) {
        Connection conexion = con.getConnection();
        try {
            ps = conexion.prepareStatement("INSERT INTO administracion " + "(idmuestras,precioTotal,pago,factura,entrada,borrado)VALUES " + "(?,?,?,?,current_timestamp(),0)");
            ps.setInt(1, id);
            ps.setDouble(2, precio);
            ps.setInt(3, pago);
            ps.setInt(4, factura);
            ps.executeUpdate();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al agregar datos, " + e);
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
    }

    public void editarAdministracion(int id, double precio, int pago, int factura) {
        Connection conexion = con.getConnection();
        try {
            ps = conexion.prepareStatement("update administracion set precioTotal" + " = ?, pago = ?, factura = ?, borrado = 0 where idmuestras = ?");
            ps.setDouble(1, precio);
            ps.setInt(2, pago);
            ps.setInt(3, factura);
            ps.setInt(4, id);
            ps.executeUpdate();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al agregar datos, " + e);
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
    }    public boolean borrarAnalisis(int id, int sino) {
        Connection conexion = con.getConnection();
        try {
            ps = conexion.prepareStatement("update administracion set borrado = ? where idmuestras = ?");
            ps.setInt(1, sino);
            ps.setInt(2, id);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al borrar analisis, " + e);
            return false;
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
    }

    public DefaultTableModel recuperarBorrados() {
        String[] fila = new String[6];
        Connection conexion = con.getConnection();
        if (modeloTabla.getColumnCount() == 0) {
            modeloTabla.addColumn("ID");
            modeloTabla.addColumn("Procedencia");
            modeloTabla.addColumn("Solicitante");
            modeloTabla.addColumn("Muestreo");
            modeloTabla.addColumn("Análisis");
            modeloTabla.addColumn("Tipo de análisis");
        }
        try {
            modeloTabla.setRowCount(0);
            ps = conexion.prepareStatement("select idmuestras, procedencia, solicitante," + " fechaMuestreo, fechaAnalisis, tipo from vistaborrados");
            rs = ps.executeQuery();
            while (rs.next()) {
                for (int i = 0; i < 6; i++) {
                    switch (i) {
                        case 0: {
                            fila[i] = String.format("%05d", rs.getObject(i + 1));
                            break;
                        }
                        case 3: {
                            if (rs.getDate("fechaMuestreo").toString().equals("1111-11-11")) {
                                fila[i] = "-";
                            } else {
                                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                                fila[i] = formatter.format(rs.getDate("fechaMuestreo"));
                            }
                            break;
                        }
                        case 4: {
                            if (rs.getObject("fechaAnalisis").toString().equals("1111-11-11")) {
                                fila[i] = "-";
                            } else {
                                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                                fila[i] = formatter.format(rs.getDate("fechaAnalisis"));
                            }
                            break;
                        }
                        default:
                            fila[i] = String.valueOf(rs.getObject(i + 1));
                            break;
                    }
                }
                modeloTabla.addRow(fila); // se agrega un renglon al org.ignaciorodriguez.modelo de la tabla
            }
            return modeloTabla; //se devuelve un org.ignaciorodriguez.modelo de tabla
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al recuperarBorrados, " + e);
            return null;
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
    }    public boolean guardarDeterminacionesAHacer(String query, int num, boolean anular, int id) { // se actualiza un vencimiento
        Connection conexion = con.getConnection();
        try {
            ps = conexion.prepareStatement(query);
            for (int i = 0; i < num; i++) {
                ps.setDouble(i + 1, (anular ? -2 : -1));
            }
            ps.setInt(num + 1, id);
            System.out.println(ps.toString());
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al " + (anular ? "anular" : "guardar") + " determinaciones, " + e);
            return false;
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
    }public String definirCaracteres() {//se agregar los caracteres organolepticos

        TextArea textArea;
        textArea = new TextArea(null, 10, 10, SCROLLBARS_NONE);
        textArea.setFont(new Font("SegoeUI", Font.PLAIN, 12));
        int ret = JOptionPane.showConfirmDialog(null, textArea, "Introduzca los " + "caracteres organoléptios", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (ret == 0) {
            String retorno = textArea.getText();
            return retorno;
        }
        return null;
    }

    public String definirCaracteres(String carecteres) { //se editan los caracteres organolepticos
        TextArea textArea;
        textArea = new TextArea(null, 10, 10, SCROLLBARS_NONE);
        textArea.setFont(new Font("SegoeUI", Font.PLAIN, 12));
        textArea.append(carecteres);
        int ret = JOptionPane.showConfirmDialog(null, textArea, "Introduzca los " + "carecteres organoléptios", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (ret == JOptionPane.YES_OPTION) {
            String retorno = textArea.getText();
            return retorno;
        } else {
            return carecteres;
        }
    }

    public String obtenerHablitacion(String s) { // se obtiene el numero de habilitación mediante la procedencia
        String numero = null;
        Connection conexion = con.getConnection();
        try {
            ps = conexion.prepareStatement("select numeroEstablecimiento from vistaTabla where procedencia = ?");
            ps.setString(1, s);
            rs = ps.executeQuery();
            while (rs.next()) {
                numero = rs.getString("numeroEstablecimiento");
                return numero;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al obtener numero de habilitacion, " + e);
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
        return numero;
    }

    public void generarReporteMuestras(List id) { //generar main.resources.reporte de salida del análisis
        Connection conexion = con.getConnection();
        try {
            JasperReport reporte = null;
            Map map = new HashMap();
            id.forEach((t) -> {
                System.out.println("t = " + t);
            });
            System.out.println("id = " + id.toString());
            map.put("id", id);
            String ruta = getClass().getResource("/reporte/informeMuestras.jasper").getPath();
            reporte = (JasperReport) JRLoader.loadObjectFromFile(ruta);//se toma el archivo .jasper para generar el main.resources.reporte
            JasperPrint imprimirReporte = JasperFillManager.fillReport(reporte, map, conexion);            //llena los campos del main.resources.reporte
            JasperViewer vistaReporte = new JasperViewer(imprimirReporte, false);                           //crea el visor del main.resources.reporte
            vistaReporte.setVisible(true);
            vistaReporte.setDefaultCloseOperation(JasperViewer.DO_NOTHING_ON_CLOSE);
            vistaReporte.setExtendedState(vistaReporte.MAXIMIZED_BOTH);
            ImageIcon icon = new ImageIcon("src" + org.ignaciorodriguez.utils.SeparatorUtils.s + "vista" + org.ignaciorodriguez.utils.SeparatorUtils.s + "icono.png");
            vistaReporte.setIconImage(icon.getImage());
            vistaReporte.setVisible(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al generar informe de muestras, " + e);
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
    }

    public boolean generarReporteVencimientos(Date desde, Date hasta) { //generar main.resources.reporte de salida del análisis
        Connection conexion = con.getConnection();
        try {
            JasperReport reporte = null;
            String ruta = getClass().getResource("/reporte/informeVencimientos.jasper").getPath();
            reporte = (JasperReport) JRLoader.loadObjectFromFile(ruta);//se toma el archivo .jasper para generar el main.resources.reporte
            Map map = new HashMap();
            map.put("desde", new java.sql.Date(desde.getTime()));
            map.put("hasta", new java.sql.Date(hasta.getTime()));
            JasperPrint imprimirReporte = JasperFillManager.fillReport(reporte, map, conexion);            //llena los campos del main.resources.reporte
            JasperViewer vistaReporte = new JasperViewer(imprimirReporte, false);                           //crea el visor del main.resources.reporte
            vistaReporte.setVisible(true);
            vistaReporte.setDefaultCloseOperation(JasperViewer.DO_NOTHING_ON_CLOSE);
            vistaReporte.setExtendedState(vistaReporte.MAXIMIZED_BOTH);
            ImageIcon icon = new ImageIcon("src" + org.ignaciorodriguez.utils.SeparatorUtils.s + "vista" + org.ignaciorodriguez.utils.SeparatorUtils.s + "icono.png");
            vistaReporte.setIconImage(icon.getImage());
            vistaReporte.setVisible(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al generar informe de muestras, " + e);
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
        return false;
    }

    public boolean entregarMuestra(Usuario usuario, int idmuestra) {
        Connection conexion = con.getConnection();
        try {
            ps = conexion.prepareStatement("insert into entregas (idmuestras, " + "idusuario, persona, hora) values (?,?,?,current_timestamp())");
            ps.setInt(1, idmuestra);
            ps.setInt(2, usuario.getId());
            ps.setString(3, usuario.getUsuario());
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al entregar muestra, " + e);
            return false;
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
    }

    public DefaultTableModel recuperarEntregas() {
        Connection conexion = con.getConnection();
        modeloEntregas.addColumn("ID");
        modeloEntregas.addColumn("Procedencia");
        modeloEntregas.addColumn("Solicitante");
        modeloEntregas.addColumn("Tipo de análisis");
        modeloEntregas.addColumn("Entregó");
        modeloEntregas.addColumn("Hora");
        modeloEntregas.addColumn("Fecha de análisis");
        Object fila[] = new Object[7];
        try {
            ps = conexion.prepareStatement("select * from vistaEntregas");
            rs = ps.executeQuery();

            while (rs.next()) {
                for (int i = 0; i < fila.length; i++) {
                    if (i == 5) {
                        String timestamp = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(rs.getTimestamp(i + 1));
                        fila[i] = timestamp;
                    } else if (i == 6) {
                        String fecha = new SimpleDateFormat("dd/MM/yyyy").format(rs.getDate("fechaAnalisis"));
                        if (fecha.equals("11/11/1111")) {
                            fecha = "-";
                        }
                        fila[i] = fecha;
                    } else if (i == 0) {
                        fila[i] = String.format("%05d", rs.getObject(i + 1));
                    } else {
                        fila[i] = rs.getObject(i + 1);
                    }
                }
                modeloEntregas.addRow(fila);
            }
            return modeloEntregas;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al obtener entragas, " + e);
            return null;
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
    }

    public String recuperarMarca(int id) {
        Connection conexion = con.getConnection();
        try {
            ps = conexion.prepareStatement("select marca from tablanutricional where idmuestras = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getString("marca");
            }
        } catch (Exception e) {
            System.err.println("Error, " + e);
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
        return null;
    }

    public void guardarMarca(int id, String marca) {
        Connection conexion = con.getConnection();
        try {
            ps = conexion.prepareStatement("insert into tablanutricional (idmuestras, marca) values (?, ?) on duplicate key update marca = ?");
            ps.setInt(1, id);
            ps.setString(2, marca);
            ps.setString(3, marca);
            ps.executeUpdate();
        } catch (Exception e) {
            System.err.println("Error, " + e);
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
    }

    public boolean checkearRutas(String nombre) {
        Connection conexion = con.getConnection();
        try {
            ps = conexion.prepareStatement("select nombre from rutas");
            rs = ps.executeQuery();
            while (rs.next()) {
                int i = 1;
                if (rs.getString(i).equals(nombre)) {
                    return true;
                }
                i++;
            }
            return false;
        } catch (Exception e) {
            System.err.println("Error, " + e);
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
        return false;
    }

    public String recuperarMySQL() {
        Connection conexion = con.getConnection();
        try {
            ps = conexion.prepareStatement("select ruta from rutas where nombre = 'MySQL'");
            rs = ps.executeQuery();
            while (rs.next()) {
                return (String) rs.getObject(1);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al recuperar ruta, " + e);
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
        return null;
    }

    public String recuperarRutas(String nombre) {
        String ruta = null;
        File archivo = null;
        try {
            switch (nombre) {
                case "Reportes":
                    archivo = new File(getClass().getResource("/rutaRespaldo.txt").toURI());
                    break;
                case "Respaldo":
                    archivo = new File(getClass().getResource("/rutaRespaldo.txt").toURI());
                    break;
                case "MySQL":
                    return recuperarMySQL();
            }
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(archivo));
            ruta = br.readLine();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ruta;
    }

    public String devolverCopiaSeguridad(String ruta) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDateTime ahora = LocalDateTime.now();
        String fecha = dtf.format(ahora);
        return ruta + "" + org.ignaciorodriguez.utils.SeparatorUtils.s + "respaldo_" + fecha + ".sql";

    }

    public boolean guardarRutas(String nombre, String ruta) {
        File archivo = null;
        if (nombre.equals("Reportes")) {
            archivo = new File("src" + org.ignaciorodriguez.utils.SeparatorUtils.s + "vista" + org.ignaciorodriguez.utils.SeparatorUtils.s + "rutaDocumentos.txt");
        } else if (nombre.equals("Respaldo")) {
            archivo = new File("src" + org.ignaciorodriguez.utils.SeparatorUtils.s + "vista" + org.ignaciorodriguez.utils.SeparatorUtils.s + "rutaRespaldo.txt");
        }

        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(archivo));
            bw.write(ruta);
            bw.flush();
            return true;
        } catch (IOException ex1) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex1);
            return false;
        }
    }

    

    public void generarReporteTN(int id, String procedencia) {
        Connection conexion = con.getConnection();
        Principal p = new Principal();
        try {
            JasperReport reporte = null;
            String ruta = getClass().getResource("/reporte/reportetablanutricional.jasper").getPath();
            reporte = (JasperReport) JRLoader.loadObjectFromFile(ruta);//se toma el archivo .jasper para generar el main.resources.reporte
            Map mapa = new HashMap();
            mapa.put("id", id); //se pasa el id al main.resources.reporte
            imagenesMap(mapa);
            JasperPrint imprimirReporte = JasperFillManager.fillReport(reporte, mapa, conexion);            //llena los campos del main.resources.reporte
            JasperViewer vistaReporte = new JasperViewer(imprimirReporte, false);                           //crea el visor del main.resources.reporte
            String[] nombre = obtenerProcedenciayNombreEmail(obtenerIdCliente(procedencia));
            String aux; //en estas tres lineas se sacan espacios de
            if (nombre[0].contains("/") || nombre[0].contains("" + org.ignaciorodriguez.utils.SeparatorUtils.s + "") || nombre[0].contains(":") || nombre[0].contains("*") || nombre[0].contains("?") || nombre[0].contains("\"") || nombre[0].contains("<") || nombre[0].contains(">") || nombre[0].contains(">") || nombre[1].contains("/") || nombre[1].contains("" + org.ignaciorodriguez.utils.SeparatorUtils.s + "") || nombre[1].contains(":") || nombre[1].contains("*") || nombre[1].contains("?") || nombre[1].contains("\"") || nombre[1].contains("<") || nombre[1].contains(">") || nombre[1].contains(">")) {
                nombre[0] = nombre[0].replaceAll("[/" + org.ignaciorodriguez.utils.SeparatorUtils.s + "" + org.ignaciorodriguez.utils.SeparatorUtils.s + ":*?\"<>|]", "_");
                nombre[1] = nombre[1].replaceAll("[/" + org.ignaciorodriguez.utils.SeparatorUtils.s + "" + org.ignaciorodriguez.utils.SeparatorUtils.s + ":*?\"<>|]", "_");
            }
            if (nombre[1].isBlank() || nombre[1] == null) {
                aux = "" + org.ignaciorodriguez.utils.SeparatorUtils.s + "Inf. " + id + " TN " + nombre[0];
            } else {
                aux = "" + org.ignaciorodriguez.utils.SeparatorUtils.s + "Inf. " + id + " TN " + nombre[1];
            }
            String aux2 = aux.replaceAll("^" + org.ignaciorodriguez.utils.SeparatorUtils.s + "s*", "");                                                       //principio, final y se sacan las comillas
            String aux3 = aux2.replaceAll("" + org.ignaciorodriguez.utils.SeparatorUtils.s + "s*$", "");
            String pdf = aux3.replaceAll("\"", "");
            pdf += ".pdf";                                                                                  //se agrega la extensión .pdf
            String rutaGuardado = recuperarRutas("Reportes");
            JasperExportManager.exportReportToPdfFile(imprimirReporte, rutaGuardado + pdf);
            vistaReporte.setDefaultCloseOperation(JasperViewer.DO_NOTHING_ON_CLOSE);
            vistaReporte.setExtendedState(vistaReporte.MAXIMIZED_BOTH);
            ImageIcon icon = new ImageIcon("src" + org.ignaciorodriguez.utils.SeparatorUtils.s + "vista" + org.ignaciorodriguez.utils.SeparatorUtils.s + "icono.png");
            vistaReporte.setIconImage(icon.getImage());
            vistaReporte.setVisible(true);
            vistaReporte.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    int email = JOptionPane.showConfirmDialog(null, "¿Enviar email?", "Email", JOptionPane.YES_NO_OPTION);
                    ;
                    if (JOptionPane.YES_OPTION == email) {
                        String[] nombre = obtenerProcedenciayNombreEmail(obtenerIdCliente(procedencia));
                        String aux; //en estas tres lineas se sacan espacios de
                        if (nombre[0].contains("/") || nombre[0].contains("" + org.ignaciorodriguez.utils.SeparatorUtils.s + "") || nombre[0].contains(":") || nombre[0].contains("*") || nombre[0].contains("?") || nombre[0].contains("\"") || nombre[0].contains("<") || nombre[0].contains(">") || nombre[0].contains(">") || nombre[1].contains("/") || nombre[1].contains("" + org.ignaciorodriguez.utils.SeparatorUtils.s + "") || nombre[1].contains(":") || nombre[1].contains("*") || nombre[1].contains("?") || nombre[1].contains("\"") || nombre[1].contains("<") || nombre[1].contains(">") || nombre[1].contains(">")) {
                            nombre[0] = nombre[0].replaceAll("[/" + org.ignaciorodriguez.utils.SeparatorUtils.s + "" + org.ignaciorodriguez.utils.SeparatorUtils.s + ":*?\"<>|]", "_");
                            nombre[1] = nombre[1].replaceAll("[/" + org.ignaciorodriguez.utils.SeparatorUtils.s + "" + org.ignaciorodriguez.utils.SeparatorUtils.s + ":*?\"<>|]", "_");
                        }
                        if (nombre[1].isBlank() || nombre[1] == null) {
                            aux = "" + org.ignaciorodriguez.utils.SeparatorUtils.s + "Inf. " + id + " TN " + nombre[0];
                        } else {
                            aux = "" + org.ignaciorodriguez.utils.SeparatorUtils.s + "Inf. " + id + " TN " + nombre[1];
                        }
                        String aux2 = aux.replaceAll("^" + org.ignaciorodriguez.utils.SeparatorUtils.s + "s*", "");                                                       //principio, final y se sacan las comillas
                        String aux3 = aux2.replaceAll("" + org.ignaciorodriguez.utils.SeparatorUtils.s + "s*$", "");
                        String pdf = aux3.replaceAll("\"", "");
                        pdf += ".pdf";
                        String pdfEmail = rutaGuardado + pdf;
                        VentanaEmail vEmail = new VentanaEmail(p, true, id, pdfEmail);
                        vEmail.setVisible(true);
                    }
                }
            });
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al generar informe tn, " + e);
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
    }

    public Map imagenesMap(Map m) {
        InputStream firmaStream = getClass().getResourceAsStream("/imagenes/firma.png");
        InputStream laboStream = getClass().getResourceAsStream("/imagenes/laboratorio.png");
        m.put("laboratorio", laboStream);
        m.put("firma", firmaStream);
        return m;
    }public void generarReporteEfluentes(int id, String procedencia) {
        Connection conexion = con.getConnection();
        Principal p = new Principal();
        try {
            JasperReport reporte = null;
            String ruta = getClass().getResource("/reporte/reporteEfluentes.jasper").getPath();
            reporte = (JasperReport) JRLoader.loadObjectFromFile(ruta);//se toma el archivo .jasper para generar el main.resources.reporte
            Map mapa = new HashMap();
            mapa.put("id", id); //se pasa el id al main.resources.reporte
            imagenesMap(mapa);

            JasperPrint imprimirReporte = JasperFillManager.fillReport(reporte, mapa, conexion);            //llena los campos del main.resources.reporte
            JasperViewer vistaReporte = new JasperViewer(imprimirReporte, false);                           //crea el visor del main.resources.reporte
            System.out.println("procedencia = " + procedencia);
            System.out.println("obtenerIdCliente(procedencia) = " + obtenerIdCliente(procedencia));
            System.out.println("obtenerProcedenciaYNombreEmail(obtenerIdCliente(procedencia)) = " + obtenerProcedenciayNombreEmail(obtenerIdCliente(procedencia)));
            String[] nombre = obtenerProcedenciayNombreEmail(obtenerIdCliente(procedencia));
            String aux; //en estas tres lineas se sacan espacios de
            if (nombre[0].contains("/") || nombre[0].contains("" + org.ignaciorodriguez.utils.SeparatorUtils.s + "") || nombre[0].contains(":") || nombre[0].contains("*") || nombre[0].contains("?") || nombre[0].contains("\"") || nombre[0].contains("<") || nombre[0].contains(">") || nombre[0].contains(">") || nombre[1].contains("/") || nombre[1].contains("" + org.ignaciorodriguez.utils.SeparatorUtils.s + "") || nombre[1].contains(":") || nombre[1].contains("*") || nombre[1].contains("?") || nombre[1].contains("\"") || nombre[1].contains("<") || nombre[1].contains(">") || nombre[1].contains(">")) {
                nombre[0] = nombre[0].replaceAll("[/" + org.ignaciorodriguez.utils.SeparatorUtils.s + "" + org.ignaciorodriguez.utils.SeparatorUtils.s + ":*?\"<>|]", "_");
                nombre[1] = nombre[1].replaceAll("[/" + org.ignaciorodriguez.utils.SeparatorUtils.s + "" + org.ignaciorodriguez.utils.SeparatorUtils.s + ":*?\"<>|]", "_");
            }
            if (nombre[1].isBlank() || nombre[1] == null) {
                aux = "" + org.ignaciorodriguez.utils.SeparatorUtils.s + "Inf. " + id + " Efluentes " + nombre[0];
            } else {
                aux = "" + org.ignaciorodriguez.utils.SeparatorUtils.s + "Inf. " + id + " Efluentes " + nombre[1];
            }
            String aux2 = aux.replaceAll("^" + org.ignaciorodriguez.utils.SeparatorUtils.s + "s*", "");                                                       //principio, final y se sacan las comillas
            String aux3 = aux2.replaceAll("" + org.ignaciorodriguez.utils.SeparatorUtils.s + "s*$", "");
            String pdf = aux3.replaceAll("\"", "");
            pdf += ".pdf";                                                                                  //se agrega la extensión .pdf
            String rutaGuardado = recuperarRutas("Reportes");
            JasperExportManager.exportReportToPdfFile(imprimirReporte, rutaGuardado + pdf);
            vistaReporte.setDefaultCloseOperation(JasperViewer.DO_NOTHING_ON_CLOSE);
            vistaReporte.setExtendedState(vistaReporte.MAXIMIZED_BOTH);
            ImageIcon icon = new ImageIcon("src" + org.ignaciorodriguez.utils.SeparatorUtils.s + "vista" + org.ignaciorodriguez.utils.SeparatorUtils.s + "icono.png");
            vistaReporte.setIconImage(icon.getImage());
            vistaReporte.setVisible(true);
            vistaReporte.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    int email = JOptionPane.showConfirmDialog(null, "¿Enviar email?", "Email", JOptionPane.YES_NO_OPTION);
                    ;
                    if (JOptionPane.YES_OPTION == email) {
                        String[] nombre = obtenerProcedenciayNombreEmail(obtenerIdCliente(procedencia));
                        String aux; //en estas tres lineas se sacan espacios de
                        if (nombre[0].contains("/") || nombre[0].contains("" + org.ignaciorodriguez.utils.SeparatorUtils.s + "") || nombre[0].contains(":") || nombre[0].contains("*") || nombre[0].contains("?") || nombre[0].contains("\"") || nombre[0].contains("<") || nombre[0].contains(">") || nombre[0].contains(">") || nombre[1].contains("/") || nombre[1].contains("" + org.ignaciorodriguez.utils.SeparatorUtils.s + "") || nombre[1].contains(":") || nombre[1].contains("*") || nombre[1].contains("?") || nombre[1].contains("\"") || nombre[1].contains("<") || nombre[1].contains(">") || nombre[1].contains(">")) {
                            nombre[0] = nombre[0].replaceAll("[/" + org.ignaciorodriguez.utils.SeparatorUtils.s + "" + org.ignaciorodriguez.utils.SeparatorUtils.s + ":*?\"<>|]", "_");
                            nombre[1] = nombre[1].replaceAll("[/" + org.ignaciorodriguez.utils.SeparatorUtils.s + "" + org.ignaciorodriguez.utils.SeparatorUtils.s + ":*?\"<>|]", "_");
                        }
                        if (nombre[1].isBlank() || nombre[1] == null) {
                            aux = "" + org.ignaciorodriguez.utils.SeparatorUtils.s + "Inf. " + id + " Efluentes " + nombre[0];
                        } else {
                            aux = "" + org.ignaciorodriguez.utils.SeparatorUtils.s + "Inf. " + id + " Efluentes " + nombre[1];
                        }
                        String aux2 = aux.replaceAll("^" + org.ignaciorodriguez.utils.SeparatorUtils.s + "s*", "");                                                       //principio, final y se sacan las comillas
                        String aux3 = aux2.replaceAll("" + org.ignaciorodriguez.utils.SeparatorUtils.s + "s*$", "");
                        String pdf = aux3.replaceAll("\"", "");
                        pdf += ".pdf";
                        String pdfEmail = rutaGuardado + pdf;
                        VentanaEmail vEmail = new VentanaEmail(p, true, id, pdfEmail);
                        vEmail.setVisible(true);
                    }
                }
            });
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al generar informe, " + e);
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
    }



    public void generarReporteMBAlimentos(int id, String procedencia) {
        Connection conexion = con.getConnection();
        Map m = new HashMap();
        Principal p = new Principal();
        try {
            JasperReport reporte = null;
            String ruta = getClass().getResource("/reporte/reporteMBAlimentos.jasper").getPath();
            reporte = (JasperReport) JRLoader.loadObjectFromFile(ruta);//se toma el archivo .jasper para generar el main.resources.reporte
            Map mapa = new HashMap();
            imagenesMap(mapa);
            mapa.put("id", id);
            JasperPrint imprimirReporte = JasperFillManager.fillReport(reporte, mapa, conexion);            //llena los campos del main.resources.reporte
            JasperViewer vistaReporte = new JasperViewer(imprimirReporte, false);                           //crea el visor del main.resources.reporte
            String[] nombre = obtenerProcedenciayNombreEmail(obtenerIdCliente(procedencia));
            String aux; //en estas tres lineas se sacan espacios de
            if (nombre[0].contains("/") || nombre[0].contains("" + org.ignaciorodriguez.utils.SeparatorUtils.s + "") || nombre[0].contains(":") || nombre[0].contains("*") || nombre[0].contains("?") || nombre[0].contains("\"") || nombre[0].contains("<") || nombre[0].contains(">") || nombre[0].contains(">") || nombre[1].contains("/") || nombre[1].contains("" + org.ignaciorodriguez.utils.SeparatorUtils.s + "") || nombre[1].contains(":") || nombre[1].contains("*") || nombre[1].contains("?") || nombre[1].contains("\"") || nombre[1].contains("<") || nombre[1].contains(">") || nombre[1].contains(">")) {
                nombre[0] = nombre[0].replaceAll("[/" + org.ignaciorodriguez.utils.SeparatorUtils.s + "" + org.ignaciorodriguez.utils.SeparatorUtils.s + ":*?\"<>|]", "_");
                nombre[1] = nombre[1].replaceAll("[/" + org.ignaciorodriguez.utils.SeparatorUtils.s + "" + org.ignaciorodriguez.utils.SeparatorUtils.s + ":*?\"<>|]", "_");
            }
            if (nombre[1].isBlank() || nombre[1] == null) {
                aux = "" + org.ignaciorodriguez.utils.SeparatorUtils.s + "Inf. " + id + " MB Alimentos " + nombre[0];
            } else {
                aux = "" + org.ignaciorodriguez.utils.SeparatorUtils.s + "Inf. " + id + " MB Alimentos " + nombre[1];
            }
            String aux2 = aux.replaceAll("^" + org.ignaciorodriguez.utils.SeparatorUtils.s + "s*", "");                                                       //principio, final y se sacan las comillas
            String aux3 = aux2.replaceAll("" + org.ignaciorodriguez.utils.SeparatorUtils.s + "s*$", "");
            String pdf = aux3.replaceAll("\"", "");
            pdf += ".pdf";
            String rutaGuardado = recuperarRutas("Reportes");
            JasperExportManager.exportReportToPdfFile(imprimirReporte, rutaGuardado + pdf);
            vistaReporte.setDefaultCloseOperation(JasperViewer.DO_NOTHING_ON_CLOSE);
            vistaReporte.setExtendedState(vistaReporte.MAXIMIZED_BOTH);
            ImageIcon icon = new ImageIcon("src" + org.ignaciorodriguez.utils.SeparatorUtils.s + "vista" + org.ignaciorodriguez.utils.SeparatorUtils.s + "icono.png");
            vistaReporte.setIconImage(icon.getImage());
            vistaReporte.setVisible(true);
            vistaReporte.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    int email = JOptionPane.showConfirmDialog(null, "¿Enviar email?", "Email", JOptionPane.YES_NO_OPTION);
                    ;
                    if (JOptionPane.YES_OPTION == email) {
                        String[] nombre = obtenerProcedenciayNombreEmail(obtenerIdCliente(procedencia));
                        String aux; //en estas tres lineas se sacan espacios de
                        if (nombre[0].contains("/") || nombre[0].contains("" + org.ignaciorodriguez.utils.SeparatorUtils.s + "") || nombre[0].contains(":") || nombre[0].contains("*") || nombre[0].contains("?") || nombre[0].contains("\"") || nombre[0].contains("<") || nombre[0].contains(">") || nombre[0].contains(">") || nombre[1].contains("/") || nombre[1].contains("" + org.ignaciorodriguez.utils.SeparatorUtils.s + "") || nombre[1].contains(":") || nombre[1].contains("*") || nombre[1].contains("?") || nombre[1].contains("\"") || nombre[1].contains("<") || nombre[1].contains(">") || nombre[1].contains(">")) {
                            nombre[0] = nombre[0].replaceAll("[/" + org.ignaciorodriguez.utils.SeparatorUtils.s + "" + org.ignaciorodriguez.utils.SeparatorUtils.s + ":*?\"<>|]", "_");
                            nombre[1] = nombre[1].replaceAll("[/" + org.ignaciorodriguez.utils.SeparatorUtils.s + "" + org.ignaciorodriguez.utils.SeparatorUtils.s + ":*?\"<>|]", "_");
                        }
                        if (nombre[1].isBlank() || nombre[1] == null) {
                            aux = "" + org.ignaciorodriguez.utils.SeparatorUtils.s + "Inf. " + id + " MB Alimentos " + nombre[0];
                        } else {
                            aux = "" + org.ignaciorodriguez.utils.SeparatorUtils.s + "Inf. " + id + " MB Alimentos " + nombre[1];
                        }
                        String aux2 = aux.replaceAll("^" + org.ignaciorodriguez.utils.SeparatorUtils.s + "s*", "");                                                       //principio, final y se sacan las comillas
                        String aux3 = aux2.replaceAll("" + org.ignaciorodriguez.utils.SeparatorUtils.s + "s*$", "");
                        String pdf = aux3.replaceAll("\"", "");
                        pdf += ".pdf";
                        String pdfEmail = rutaGuardado + pdf;
                        VentanaEmail vEmail = new VentanaEmail(p, true, id, pdfEmail);
                        vEmail.setVisible(true);
                    }
                }
            });
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al generar informe, " + e);
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
    }

    public void generarReporteMBChocolates(int id, String procedencia) {
        Connection conexion = con.getConnection();
        Principal p = new Principal();
        Map m = new HashMap();
        try {
            JasperReport reporte = null;
            String ruta = getClass().getResource("/reporte/reporteMBChocolates.jasper").getPath();
            reporte = (JasperReport) JRLoader.loadObjectFromFile(ruta);//se toma el archivo .jasper para generar el main.resources.reporte
            Map mapa = new HashMap();
            mapa.put("id", id);
            imagenesMap(mapa);
            JasperPrint imprimirReporte = JasperFillManager.fillReport(reporte, mapa, conexion);            //llena los campos del main.resources.reporte
            JasperViewer vistaReporte = new JasperViewer(imprimirReporte, false);                           //crea el visor del main.resources.reporte
            String[] nombre = obtenerProcedenciayNombreEmail(obtenerIdCliente(procedencia));
            String aux; //en estas tres lineas se sacan espacios de
            if (nombre[0].contains("/") || nombre[0].contains("" + org.ignaciorodriguez.utils.SeparatorUtils.s + "") || nombre[0].contains(":") || nombre[0].contains("*") || nombre[0].contains("?") || nombre[0].contains("\"") || nombre[0].contains("<") || nombre[0].contains(">") || nombre[0].contains(">") || nombre[1].contains("/") || nombre[1].contains("" + org.ignaciorodriguez.utils.SeparatorUtils.s + "") || nombre[1].contains(":") || nombre[1].contains("*") || nombre[1].contains("?") || nombre[1].contains("\"") || nombre[1].contains("<") || nombre[1].contains(">") || nombre[1].contains(">")) {
                nombre[0] = nombre[0].replaceAll("[/" + org.ignaciorodriguez.utils.SeparatorUtils.s + "" + org.ignaciorodriguez.utils.SeparatorUtils.s + ":*?\"<>|]", "_");
                nombre[1] = nombre[1].replaceAll("[/" + org.ignaciorodriguez.utils.SeparatorUtils.s + "" + org.ignaciorodriguez.utils.SeparatorUtils.s + ":*?\"<>|]", "_");
            }
            if (nombre[1].isBlank() || nombre[1] == null) {
                aux = "" + org.ignaciorodriguez.utils.SeparatorUtils.s + "Inf. " + id + " MB Chocolates " + nombre[0];
            } else {
                aux = "" + org.ignaciorodriguez.utils.SeparatorUtils.s + "Inf. " + id + " MB Chocolates " + nombre[1];
            }
            String aux2 = aux.replaceAll("^" + org.ignaciorodriguez.utils.SeparatorUtils.s + "s*", "");                                                       //principio, final y se sacan las comillas
            String aux3 = aux2.replaceAll("" + org.ignaciorodriguez.utils.SeparatorUtils.s + "s*$", "");
            String pdf = aux3.replaceAll("\"", "");
            pdf += ".pdf";                                                                                  //se agrega la extensión .pdf
            String rutaGuardado = recuperarRutas("Reportes");
            JasperExportManager.exportReportToPdfFile(imprimirReporte, rutaGuardado + pdf);
            vistaReporte.setDefaultCloseOperation(JasperViewer.DO_NOTHING_ON_CLOSE);
            vistaReporte.setExtendedState(vistaReporte.MAXIMIZED_BOTH);
            ImageIcon icon = new ImageIcon("src" + org.ignaciorodriguez.utils.SeparatorUtils.s + "vista" + org.ignaciorodriguez.utils.SeparatorUtils.s + "icono.png");
            vistaReporte.setIconImage(icon.getImage());
            vistaReporte.setVisible(true);
            vistaReporte.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    int email = JOptionPane.showConfirmDialog(null, "¿Enviar email?", "Email", JOptionPane.YES_NO_OPTION);
                    ;
                    if (JOptionPane.YES_OPTION == email) {
                        String[] nombre = obtenerProcedenciayNombreEmail(obtenerIdCliente(procedencia));
                        String aux; //en estas tres lineas se sacan espacios de
                        if (nombre[0].contains("/") || nombre[0].contains("" + org.ignaciorodriguez.utils.SeparatorUtils.s + "") || nombre[0].contains(":") || nombre[0].contains("*") || nombre[0].contains("?") || nombre[0].contains("\"") || nombre[0].contains("<") || nombre[0].contains(">") || nombre[0].contains(">") || nombre[1].contains("/") || nombre[1].contains("" + org.ignaciorodriguez.utils.SeparatorUtils.s + "") || nombre[1].contains(":") || nombre[1].contains("*") || nombre[1].contains("?") || nombre[1].contains("\"") || nombre[1].contains("<") || nombre[1].contains(">") || nombre[1].contains(">")) {
                            nombre[0] = nombre[0].replaceAll("[/" + org.ignaciorodriguez.utils.SeparatorUtils.s + "" + org.ignaciorodriguez.utils.SeparatorUtils.s + ":*?\"<>|]", "_");
                            nombre[1] = nombre[1].replaceAll("[/" + org.ignaciorodriguez.utils.SeparatorUtils.s + "" + org.ignaciorodriguez.utils.SeparatorUtils.s + ":*?\"<>|]", "_");
                        }
                        if (nombre[1].isBlank() || nombre[1] == null) {
                            aux = "" + org.ignaciorodriguez.utils.SeparatorUtils.s + "Inf. " + id + " MB Chocolates " + nombre[0];
                        } else {
                            aux = "" + org.ignaciorodriguez.utils.SeparatorUtils.s + "Inf. " + id + " MB Chocolates " + nombre[1];
                        }
                        String aux2 = aux.replaceAll("^" + org.ignaciorodriguez.utils.SeparatorUtils.s + "s*", "");                                                       //principio, final y se sacan las comillas
                        String aux3 = aux2.replaceAll("" + org.ignaciorodriguez.utils.SeparatorUtils.s + "s*$", "");
                        String pdf = aux3.replaceAll("\"", "");
                        pdf += ".pdf";
                        String pdfEmail = rutaGuardado + pdf;
                        VentanaEmail vEmail = new VentanaEmail(p, true, id, pdfEmail);
                        vEmail.setVisible(true);
                    }
                }
            });
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al generar informe, " + e);
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
    }

    public void generarReporteMBBaseHelada(int id, String procedencia) {
        Connection conexion = con.getConnection();
        Map m = new HashMap();
        Principal p = new Principal();
        try {
            JasperReport reporte = null;
            String ruta = getClass().getResource("/reporte/reporteBaseHelada.jasper").getPath();
            reporte = (JasperReport) JRLoader.loadObjectFromFile(ruta);//se toma el archivo .jasper para generar el main.resources.reporte
            Map mapa = new HashMap();
            mapa.put("id", id);
            imagenesMap(mapa);
            JasperPrint imprimirReporte = JasperFillManager.fillReport(reporte, mapa, conexion);            //llena los campos del main.resources.reporte
            JasperViewer vistaReporte = new JasperViewer(imprimirReporte, false);                           //crea el visor del main.resources.reporte
            String[] nombre = obtenerProcedenciayNombreEmail(obtenerIdCliente(procedencia));
            String aux; //en estas tres lineas se sacan espacios de
            if (nombre[0].contains("/") || nombre[0].contains("" + org.ignaciorodriguez.utils.SeparatorUtils.s + "") || nombre[0].contains(":") || nombre[0].contains("*") || nombre[0].contains("?") || nombre[0].contains("\"") || nombre[0].contains("<") || nombre[0].contains(">") || nombre[0].contains(">") || nombre[1].contains("/") || nombre[1].contains("" + org.ignaciorodriguez.utils.SeparatorUtils.s + "") || nombre[1].contains(":") || nombre[1].contains("*") || nombre[1].contains("?") || nombre[1].contains("\"") || nombre[1].contains("<") || nombre[1].contains(">") || nombre[1].contains(">")) {
                nombre[0] = nombre[0].replaceAll("[/" + org.ignaciorodriguez.utils.SeparatorUtils.s + "" + org.ignaciorodriguez.utils.SeparatorUtils.s + ":*?\"<>|]", "_");
                nombre[1] = nombre[1].replaceAll("[/" + org.ignaciorodriguez.utils.SeparatorUtils.s + "" + org.ignaciorodriguez.utils.SeparatorUtils.s + ":*?\"<>|]", "_");
            }
            if (nombre[1].isBlank() || nombre[1] == null) {
                aux = "" + org.ignaciorodriguez.utils.SeparatorUtils.s + "Inf. " + id + " Base Helada " + nombre[0];
            } else {
                aux = "" + org.ignaciorodriguez.utils.SeparatorUtils.s + "Inf. " + id + " Base Helada " + nombre[1];
            }
            String aux2 = aux.replaceAll("^" + org.ignaciorodriguez.utils.SeparatorUtils.s + "s*", "");                                                       //principio, final y se sacan las comillas
            String aux3 = aux2.replaceAll("" + org.ignaciorodriguez.utils.SeparatorUtils.s + "s*$", "");
            String pdf = aux3.replaceAll("\"", "");
            pdf += ".pdf";                                                                                  //se agrega la extensión .pdf
            String rutaGuardado = recuperarRutas("Reportes");
            JasperExportManager.exportReportToPdfFile(imprimirReporte, rutaGuardado + pdf);
            vistaReporte.setDefaultCloseOperation(JasperViewer.DO_NOTHING_ON_CLOSE);
            vistaReporte.setExtendedState(vistaReporte.MAXIMIZED_BOTH);
            ImageIcon icon = new ImageIcon("src" + org.ignaciorodriguez.utils.SeparatorUtils.s + "vista" + org.ignaciorodriguez.utils.SeparatorUtils.s + "icono.png");
            vistaReporte.setIconImage(icon.getImage());
            vistaReporte.setVisible(true);
            vistaReporte.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    int email = JOptionPane.showConfirmDialog(null, "¿Enviar email?", "Email", JOptionPane.YES_NO_OPTION);
                    ;
                    if (JOptionPane.YES_OPTION == email) {
                        String[] nombre = obtenerProcedenciayNombreEmail(obtenerIdCliente(procedencia));
                        String aux; //en estas tres lineas se sacan espacios de
                        if (nombre[0].contains("/") || nombre[0].contains("" + org.ignaciorodriguez.utils.SeparatorUtils.s + "") || nombre[0].contains(":") || nombre[0].contains("*") || nombre[0].contains("?") || nombre[0].contains("\"") || nombre[0].contains("<") || nombre[0].contains(">") || nombre[0].contains(">") || nombre[1].contains("/") || nombre[1].contains("" + org.ignaciorodriguez.utils.SeparatorUtils.s + "") || nombre[1].contains(":") || nombre[1].contains("*") || nombre[1].contains("?") || nombre[1].contains("\"") || nombre[1].contains("<") || nombre[1].contains(">") || nombre[1].contains(">")) {
                            nombre[0] = nombre[0].replaceAll("[/" + org.ignaciorodriguez.utils.SeparatorUtils.s + "" + org.ignaciorodriguez.utils.SeparatorUtils.s + ":*?\"<>|]", "_");
                            nombre[1] = nombre[1].replaceAll("[/" + org.ignaciorodriguez.utils.SeparatorUtils.s + "" + org.ignaciorodriguez.utils.SeparatorUtils.s + ":*?\"<>|]", "_");
                        }
                        if (nombre[1].isBlank() || nombre[1] == null) {
                            aux = "" + org.ignaciorodriguez.utils.SeparatorUtils.s + "Inf. " + id + " Base Helada " + nombre[0];
                        } else {
                            aux = "" + org.ignaciorodriguez.utils.SeparatorUtils.s + "Inf. " + id + " Base Helada " + nombre[1];
                        }
                        String aux2 = aux.replaceAll("^" + org.ignaciorodriguez.utils.SeparatorUtils.s + "s*", "");                                                       //principio, final y se sacan las comillas
                        String aux3 = aux2.replaceAll("" + org.ignaciorodriguez.utils.SeparatorUtils.s + "s*$", "");
                        String pdf = aux3.replaceAll("\"", "");
                        pdf += ".pdf";
                        String pdfEmail = rutaGuardado + pdf;
                        VentanaEmail vEmail = new VentanaEmail(p, true, id, pdfEmail);
                        vEmail.setVisible(true);
                    }
                }
            });
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al generar informe, " + e);
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
    }

    public void exportarExcelTradicional(Date desde, Date hasta, int idcliente, String tipo, Tipo t) {
        Workbook workbook = new XSSFWorkbook();
        FileOutputStream fileOut = null;
        java.sql.Date desdeSql = new java.sql.Date(desde.getTime());
        java.sql.Date hastaSql = new java.sql.Date(hasta.getTime());
        Sheet sheet = workbook.createSheet("Datos");
        Sheet sheetGraph = workbook.createSheet("Graficos");
        Sheet sheetHidden = workbook.createSheet("Hidden");
        workbook.setSheetHidden(workbook.getSheetIndex("Hidden"), true);
        String[] columnas = obtenerColumnas(t);
        int rowNum[] = {1};
        int rowNumGraph[] = {1};
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < columnas.length; i++) {
            headerRow.createCell(i).setCellValue(columnas[i]);
        }
        int[] rowCont = new int[columnas.length - 2];
        System.out.println("t = " + t);
        switch (t) {
            case EFLUENTES:
                consultarEfluentesParaExcel(idcliente, tipo, desdeSql, hastaSql, sheet, sheetHidden, rowNum, rowNumGraph, rowCont);
                break;
            case MBAGUACODIGO:
                consultarMBAguaCodigoParaExcel(idcliente, tipo, desdeSql, hastaSql, sheet, sheetHidden, rowNum, rowNumGraph, rowCont, workbook);
                break;
            case MBAGUACOFES:
                consultarMBAguaCofesParaExcel(idcliente, tipo, desdeSql, hastaSql, sheet, sheetHidden, rowNum, rowNumGraph, rowCont, workbook);
                break;
            case MBALIMENTOS:
                consultarMBAlimentosParaExcel(idcliente, tipo, desdeSql, hastaSql, sheet, sheetHidden, rowNum, rowCont, workbook);
                break;
            case FQALIMENTOS:
                consultarDeterminacionesParaExcel(idcliente, tipo, desdeSql, hastaSql, sheet, sheetHidden, rowNum, rowCont, workbook);
                break;
            default:
                throw new AssertionError();
        }
        int graficoIndex = 0;
        for (int i = 0; i < rowCont.length; i++) {
            if (rowCont[i] > 0) {
                int columnaBase = i * 2; // esto se queda igual, es la posición de los datos en "Hidden"
                dibujarGrafico(sheetGraph, sheetHidden, rowCont[i], columnas[i + 2], graficoIndex, columnaBase);
                graficoIndex++;
            }
        }
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        try {
            String ruta = recuperarRutas("Reportes") + org.ignaciorodriguez.utils.SeparatorUtils.s + tipo + " " + recuperarProcedencia(idcliente) + " desde " + formatter.format(desdeSql) + " hasta " + formatter.format(hastaSql) + ".xlsx";
            fileOut = new FileOutputStream(ruta);
            workbook.write(fileOut);
            Desktop.getDesktop().open(new File(ruta));
        } catch (IOException e) {
            System.err.println("Error al escribir el archivo: " + e.getMessage());
        } finally {
            try {
                workbook.close();
                fileOut.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private void dibujarGrafico(Sheet sheetGraph, Sheet sheetHidden, int rowNumGraph, String det, int cont, int colInicio) {

        XSSFDrawing drawing = (XSSFDrawing) sheetGraph.createDrawingPatriarch();

        int filaInicio = cont * 22;
        int filaFin = filaInicio + 20;

        XSSFClientAnchor anchor = drawing.createAnchor(0, 0, 0, 0, 1, filaInicio, Math.max(15, 3 + Math.round(rowNumGraph * 0.7f)), filaFin);

        XSSFChart chart = drawing.createChart(anchor);

        XDDFCategoryAxis bottomAxis = chart.createCategoryAxis(AxisPosition.BOTTOM);
        org.apache.poi.xddf.usermodel.chart.XDDFValueAxis leftAxis = chart.createValueAxis(AxisPosition.LEFT);
        leftAxis.setCrossBetween(AxisCrossBetween.BETWEEN);
        XDDFLineChartData data = (XDDFLineChartData) chart.createData(ChartTypes.LINE, bottomAxis, leftAxis);
        XDDFDataSource<String> xs = XDDFDataSourcesFactory.fromStringCellRange((XSSFSheet) sheetHidden, new CellRangeAddress(0, rowNumGraph - 1, colInicio, colInicio));

        XDDFNumericalDataSource<Double> ys = XDDFDataSourcesFactory.fromNumericCellRange((XSSFSheet) sheetHidden, new CellRangeAddress(0, rowNumGraph - 1, colInicio + 1, colInicio + 1));

        XDDFLineChartData.Series series = (XDDFLineChartData.Series) data.addSeries(xs, ys);
        series.setSmooth(true);
        series.setMarkerStyle(MarkerStyle.CIRCLE);
        series.setTitle("Nivel de " + det, null);

        chart.setTitleText("Seguimiento de " + det + "\n* Valores en 0 corresponden a resultados < límite");
        chart.setTitleOverlay(false);
        bottomAxis.setTitle("ID de Muestra");
        leftAxis.setTitle("Valor " + det);

        if (!chart.getCTChart().getPlotArea().getLineChartArray(0).getSerArray(0).isSetDLbls()) {
            chart.getCTChart().getPlotArea().getLineChartArray(0).getSerArray(0).addNewDLbls();
        }

        org.openxmlformats.schemas.drawingml.x2006.chart.CTDLbls dLbls = chart.getCTChart().getPlotArea().getLineChartArray(0).getSerArray(0).getDLbls();

        dLbls.addNewShowVal().setVal(true);
        dLbls.addNewShowCatName().setVal(false);
        dLbls.addNewShowSerName().setVal(false);
        dLbls.addNewDLblPos().setVal(org.openxmlformats.schemas.drawingml.x2006.chart.STDLblPos.T);
        org.openxmlformats.schemas.drawingml.x2006.chart.CTChart ctChart = chart.getCTChart();

        if (ctChart.isSetDispBlanksAs()) {
            ctChart.getDispBlanksAs().setVal(org.openxmlformats.schemas.drawingml.x2006.chart.STDispBlanksAs.GAP);
        } else {
            ctChart.addNewDispBlanksAs().setVal(org.openxmlformats.schemas.drawingml.x2006.chart.STDispBlanksAs.GAP);
        }

//        ctChart.addNewShowDLblsOverMax().setVal(false);
        chart.plot(data);
    }

    private void consultarEfluentesParaExcel(int idcliente, String tipo, java.sql.Date desdeSql, java.sql.Date hastaSql, Sheet sheet, Sheet sheetHidden, int[] rowNum, int[] rowNumGraph, int[] rowCont) {
        Connection conexion = con.getConnection();
        String sql = "SELECT * FROM vistaefluentes WHERE idmuestras in (select idmuestras from muestras where idcliente = ? and tipo = ?) and fechaMuestreo BETWEEN ? AND ?";
        for (int i = 0; i < rowCont.length; i++) {
            rowCont[i] = 0;
        }
        try {
            // 1. Preparar la consulta
            ps = conexion.prepareStatement(sql);
            ps.setInt(1, idcliente);
            ps.setString(2, tipo);
            ps.setDate(3, desdeSql);
            ps.setDate(4, hastaSql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Row row = sheet.createRow(rowNum[0]++);
                String[] parametros = {"ph", "dqo", "dbo", "solidos10", "solidos120", "detergentes", "grasas", "fosforo", "nitrogeno", "sustancias", "coliformesTotales", "coliformesFecales", "escherichia", "conductividad", "hidrocarburos", "nitratos", "cloro", "sulfuros"};
                String idmuestras = String.valueOf(rs.getInt("idmuestras"));
                row.createCell(0).setCellValue(idmuestras);
                row.createCell(1).setCellValue(formatearEntradaExcel(rs.getDate("fechaMuestreo").toString()));
                for (int i = 0; i < parametros.length; i++) {
                    String nombreCol = parametros[i];
                    String valorRaw = rs.getString(nombreCol);

                    row.createCell(i + 2).setCellValue(formatearEntradaExcel(valorRaw));

                    if (i < rowCont.length) {
                        Double valorNum = extraerNumero(valorRaw);

                        if (valorNum != null && !Double.isNaN(valorNum) && valorNum >= 0) {
                            int filaActual = rowCont[i];

                            Row rowGraph = sheetHidden.getRow(filaActual);
                            if (rowGraph == null) {
                                rowGraph = sheetHidden.createRow(filaActual);
                            }

                            int colID = i * 2;
                            int colVal = i * 2 + 1;

                            rowGraph.createCell(colID).setCellValue(procesarCeldaGrafico(idmuestras));
                            rowGraph.createCell(colVal).setCellValue(valorNum);

                            rowCont[i]++;
                        }
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error en la consulta SQL: " + e.getMessage());
        } finally {
            try {
                rs.close();
                ps.close();
            } catch (Exception e) {
                System.err.println("Error al cerrar recursos: " + e.getMessage());
            }
        }
    }

    private void consultarMBAguaCofesParaExcel(int idcliente, String tipo, java.sql.Date desdeSql, java.sql.Date hastaSql, Sheet sheet, Sheet sheetHidden, int[] rowNum, int[] rowNumGraph, int[] rowCont, Workbook workbook) {
        Connection conexion = con.getConnection();
        String sql = "SELECT vistatabla_idmuestras, vistatabla_fechaMuestreo, vistatabla_porcentajeTotalCloro, " + "vistatabla_ph, germenes, coliformesTotales, coliformesFecales, escherichia, pseudomona, s" + "higella FROM vistambagua WHERE vistatabla_idmuestras in (select idmuestras from muestras where " + "idcliente = ? and tipo = ?) and vistatabla_fechaMuestreo BETWEEN ? AND ?";
        for (int i = 0; i < rowCont.length; i++) {
            rowCont[i] = 0;
        }
        CellStyle estiloPresencia = workbook.createCellStyle();
        DataFormat format = workbook.createDataFormat();
        estiloPresencia.setDataFormat(format.getFormat("[=1]\"Presencia\";[=0]\"Ausencia\";\"N/A\""));
        try {
            // 1. Preparar la consulta
            ps = conexion.prepareStatement(sql);
            ps.setInt(1, idcliente);
            ps.setString(2, tipo);
            ps.setDate(3, desdeSql);
            ps.setDate(4, hastaSql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Row row = sheet.createRow(rowNum[0]++);
                String idmuestras = String.valueOf(rs.getInt("vistatabla_idmuestras"));
                row.createCell(0).setCellValue(idmuestras);
                String fechaMuestreo = rs.getDate("vistatabla_fechaMuestreo").toString();
                row.createCell(1).setCellValue(formatearEntradaExcel(fechaMuestreo));
                String cloro = rs.getString("vistatabla_porcentajeTotalCloro");
                row.createCell(2).setCellValue(formatearEntradaExcel(cloro));
                String ph = rs.getString("vistatabla_ph");
                row.createCell(3).setCellValue(formatearEntradaExcel(ph));
                String germenes = rs.getString("germenes");
                row.createCell(4).setCellValue(formatearEntradaExcel(germenes));
                String coliformesTotales = rs.getString("coliformesTotales");
                row.createCell(5).setCellValue(formatearEntradaExcel(coliformesTotales));
                String coliformesFecales = rs.getString("coliformesFecales");
                row.createCell(6).setCellValue(formatearEntradaExcel(coliformesFecales));
                String escherichia = rs.getString("escherichia");
                row.createCell(7).setCellValue(formatearEntradaExcel(escherichia));
                String pseudomona = rs.getString("pseudomona");
                row.createCell(8).setCellValue(formatearEntradaExcel(pseudomona));
                String shigella = rs.getString("shigella");
                row.createCell(9).setCellValue(formatearEntradaExcel(shigella));

                Double cloroGraph = extraerNumero(cloro);
                if (cloroGraph != null && !Double.isNaN(cloroGraph) && cloroGraph >= 0) {
                    int filaActual = rowCont[0];
                    Row rowGraph = sheetHidden.getRow(filaActual);
                    if (rowGraph == null) {
                        rowGraph = sheetHidden.createRow(filaActual);
                    }
                    rowGraph.createCell(0 * 2).setCellValue(procesarCeldaGrafico(idmuestras));
                    rowGraph.createCell(0 * 2 + 1).setCellValue(cloroGraph);
                    rowCont[0]++;
                }
                Double phGraph = extraerNumero(ph);
                if (phGraph != null && !Double.isNaN(phGraph) && phGraph >= 0) {
                    int filaActual = rowCont[1];
                    Row rowGraph = sheetHidden.getRow(filaActual);
                    if (rowGraph == null) {
                        rowGraph = sheetHidden.createRow(filaActual);
                    }
                    rowGraph.createCell(1 * 2).setCellValue(procesarCeldaGrafico(idmuestras));
                    rowGraph.createCell(1 * 2 + 1).setCellValue(phGraph);
                    rowCont[1]++;
                }
                Double germenesGraph = extraerNumero(germenes);
                if (germenesGraph != null && !Double.isNaN(germenesGraph) && germenesGraph >= 0) {
                    int filaActual = rowCont[2];
                    Row rowGraph = sheetHidden.getRow(filaActual);
                    if (rowGraph == null) {
                        rowGraph = sheetHidden.createRow(filaActual);
                    }
                    rowGraph.createCell(2 * 2).setCellValue(procesarCeldaGrafico(idmuestras));
                    rowGraph.createCell(2 * 2 + 1).setCellValue(germenesGraph);
                    rowCont[2]++;
                }
                Double coliformesTotalesGraph = extraerNumero(coliformesTotales);
                if (coliformesTotalesGraph != null && !Double.isNaN(coliformesTotalesGraph) && coliformesTotalesGraph >= 0) {
                    int filaActual = rowCont[3];
                    Row rowGraph = sheetHidden.getRow(filaActual);
                    if (rowGraph == null) {
                        rowGraph = sheetHidden.createRow(filaActual);
                    }
                    rowGraph.createCell(3 * 2).setCellValue(procesarCeldaGrafico(idmuestras));
                    rowGraph.createCell(3 * 2 + 1).setCellValue(coliformesTotalesGraph);
                    rowCont[3]++;
                }
                Double coliformesFecalesGraph = extraerNumero(coliformesFecales);
                if (coliformesFecalesGraph != null && !Double.isNaN(coliformesFecalesGraph) && coliformesFecalesGraph >= 0) {
                    int filaActual = rowCont[4];
                    Row rowGraph = sheetHidden.getRow(filaActual);
                    if (rowGraph == null) {
                        rowGraph = sheetHidden.createRow(filaActual);
                    }
                    rowGraph.createCell(4 * 2).setCellValue(procesarCeldaGrafico(idmuestras));
                    rowGraph.createCell(4 * 2 + 1).setCellValue(coliformesFecalesGraph);
                    rowCont[4]++;
                }
                if (escherichia.toLowerCase().contains("ausencia") || escherichia.toLowerCase().contains("presencia")) {
                    int filaActual = rowCont[5];
                    Row rowGraph = sheetHidden.getRow(filaActual);
                    if (rowGraph == null) {
                        rowGraph = sheetHidden.createRow(filaActual);
                    }
                    rowGraph.createCell(5 * 2).setCellValue(procesarCeldaGrafico(idmuestras));
                    rowGraph.createCell(5 * 2 + 1).setCellValue(extraerAusenciaPresencia(escherichia));
                    rowGraph.getCell(5 * 2 + 1).setCellStyle(estiloPresencia);
                    rowCont[5]++;
                }
                if (pseudomona.toLowerCase().contains("ausencia") || pseudomona.toLowerCase().contains("presencia")) {
                    int filaActual = rowCont[6];
                    Row rowGraph = sheetHidden.getRow(filaActual);
                    if (rowGraph == null) {
                        rowGraph = sheetHidden.createRow(filaActual);
                    }
                    rowGraph.createCell(6 * 2).setCellValue(procesarCeldaGrafico(idmuestras));
                    rowGraph.createCell(6 * 2 + 1).setCellValue(extraerAusenciaPresencia(pseudomona));
                    rowGraph.getCell(6 * 2 + 1).setCellStyle(estiloPresencia);
                    rowCont[6]++;
                }
                if (shigella == null) {
                    shigella = "-2";
                }
                if (shigella.toLowerCase().contains("ausencia") || shigella.toLowerCase().contains("presencia")) {
                    int filaActual = rowCont[7];
                    Row rowGraph = sheetHidden.getRow(filaActual);
                    if (rowGraph == null) {
                        rowGraph = sheetHidden.createRow(filaActual);
                    }
                    rowGraph.createCell(7 * 2).setCellValue(procesarCeldaGrafico(idmuestras));
                    rowGraph.createCell(7 * 2 + 1).setCellValue(extraerAusenciaPresencia(shigella));
                    rowGraph.getCell(7 * 2 + 1).setCellStyle(estiloPresencia);
                    rowCont[7]++;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error en la consulta SQL: " + e.getMessage());
        } finally {
            try {
                rs.close();
                ps.close();
            } catch (Exception e) {
                System.err.println("Error al cerrar recursos: " + e.getMessage());
            }
        }
    }

    private void consultarMBAguaCodigoParaExcel(int idcliente, String tipo, java.sql.Date desdeSql, java.sql.Date hastaSql, Sheet sheet, Sheet sheetHidden, int[] rowNum, int[] rowNumGraph, int[] rowCont, Workbook workbook) {
        Connection conexion = con.getConnection();
        String sql = "SELECT vistatabla_idmuestras, vistatabla_fechaMuestreo, vistatabla_porcentajeTotalCloro, " + "vistatabla_ph, germenes, coliformesTotales, coliformesFecales, escherichia, pseudomona, mohos, s" + "higella FROM vistambagua WHERE vistatabla_idmuestras in (select idmuestras from muestras where " + "idcliente = ? and tipo = ?) and vistatabla_fechaMuestreo BETWEEN ? AND ?";
        for (int i = 0; i < rowCont.length; i++) {
            rowCont[i] = 0;
        }
        CellStyle estiloPresencia = workbook.createCellStyle();
        DataFormat format = workbook.createDataFormat();
        estiloPresencia.setDataFormat(format.getFormat("[=1]\"Presencia\";[=0]\"Ausencia\";\"N/A\""));
        try {
            // 1. Preparar la consulta
            ps = conexion.prepareStatement(sql);
            ps.setInt(1, idcliente);
            ps.setString(2, tipo);
            ps.setDate(3, desdeSql);
            ps.setDate(4, hastaSql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Row row = sheet.createRow(rowNum[0]++);
                String idmuestras = String.valueOf(rs.getInt("vistatabla_idmuestras"));
                row.createCell(0).setCellValue(idmuestras);
                String fechaMuestreo = rs.getDate("vistatabla_fechaMuestreo").toString();
                row.createCell(1).setCellValue(formatearEntradaExcel(fechaMuestreo));
                String cloro = rs.getString("vistatabla_porcentajeTotalCloro");
                row.createCell(2).setCellValue(formatearEntradaExcel(cloro));
                String ph = rs.getString("vistatabla_ph");
                row.createCell(3).setCellValue(formatearEntradaExcel(ph));
                String germenes = rs.getString("germenes");
                row.createCell(4).setCellValue(formatearEntradaExcel(germenes));
                String coliformesTotales = rs.getString("coliformesTotales");
                row.createCell(5).setCellValue(formatearEntradaExcel(coliformesTotales));
                String coliformesFecales = rs.getString("coliformesFecales");
                row.createCell(6).setCellValue(formatearEntradaExcel(coliformesFecales));
                String escherichia = rs.getString("escherichia");
                row.createCell(7).setCellValue(formatearEntradaExcel(escherichia));
                String pseudomona = rs.getString("pseudomona");
                row.createCell(8).setCellValue(formatearEntradaExcel(pseudomona));
                String mohos = rs.getString("mohos");
                row.createCell(9).setCellValue(formatearEntradaExcel(mohos));
                String shigella = rs.getString("shigella");
                row.createCell(10).setCellValue(formatearEntradaExcel(shigella));

                Double cloroGraph = extraerNumero(cloro);
                if (cloroGraph != null && !Double.isNaN(cloroGraph) && cloroGraph >= 0) {
                    int filaActual = rowCont[0];
                    Row rowGraph = sheetHidden.getRow(filaActual);
                    if (rowGraph == null) {
                        rowGraph = sheetHidden.createRow(filaActual);
                    }
                    rowGraph.createCell(0 * 2).setCellValue(procesarCeldaGrafico(idmuestras));
                    rowGraph.createCell(0 * 2 + 1).setCellValue(cloroGraph);
                    rowCont[0]++;
                }
                Double phGraph = extraerNumero(ph);
                if (phGraph != null && !Double.isNaN(phGraph) && phGraph >= 0) {
                    int filaActual = rowCont[1];
                    Row rowGraph = sheetHidden.getRow(filaActual);
                    if (rowGraph == null) {
                        rowGraph = sheetHidden.createRow(filaActual);
                    }
                    rowGraph.createCell(1 * 2).setCellValue(procesarCeldaGrafico(idmuestras));
                    rowGraph.createCell(1 * 2 + 1).setCellValue(phGraph);
                    rowCont[1]++;
                }
                Double germenesGraph = extraerNumero(germenes);
                if (germenesGraph != null && !Double.isNaN(germenesGraph) && germenesGraph >= 0) {
                    int filaActual = rowCont[2];
                    Row rowGraph = sheetHidden.getRow(filaActual);
                    if (rowGraph == null) {
                        rowGraph = sheetHidden.createRow(filaActual);
                    }
                    rowGraph.createCell(2 * 2).setCellValue(procesarCeldaGrafico(idmuestras));
                    rowGraph.createCell(2 * 2 + 1).setCellValue(germenesGraph);
                    rowCont[2]++;
                }
                Double coliformesTotalesGraph = extraerNumero(coliformesTotales);
                if (coliformesTotalesGraph != null && !Double.isNaN(coliformesTotalesGraph) && coliformesTotalesGraph >= 0) {
                    int filaActual = rowCont[3];
                    Row rowGraph = sheetHidden.getRow(filaActual);
                    if (rowGraph == null) {
                        rowGraph = sheetHidden.createRow(filaActual);
                    }
                    rowGraph.createCell(3 * 2).setCellValue(procesarCeldaGrafico(idmuestras));
                    rowGraph.createCell(3 * 2 + 1).setCellValue(coliformesTotalesGraph);
                    rowCont[3]++;
                }
                Double coliformesFecalesGraph = extraerNumero(coliformesFecales);
                if (coliformesFecalesGraph != null && !Double.isNaN(coliformesFecalesGraph) && coliformesFecalesGraph >= 0) {
                    int filaActual = rowCont[4];
                    Row rowGraph = sheetHidden.getRow(filaActual);
                    if (rowGraph == null) {
                        rowGraph = sheetHidden.createRow(filaActual);
                    }
                    rowGraph.createCell(4 * 2).setCellValue(procesarCeldaGrafico(idmuestras));
                    rowGraph.createCell(4 * 2 + 1).setCellValue(coliformesFecalesGraph);
                    rowCont[4]++;
                }
                if (escherichia.toLowerCase().contains("ausencia") || escherichia.toLowerCase().contains("presencia")) {
                    int filaActual = rowCont[5];
                    Row rowGraph = sheetHidden.getRow(filaActual);
                    if (rowGraph == null) {
                        rowGraph = sheetHidden.createRow(filaActual);
                    }
                    rowGraph.createCell(5 * 2).setCellValue(procesarCeldaGrafico(idmuestras));
                    rowGraph.createCell(5 * 2 + 1).setCellValue(extraerAusenciaPresencia(escherichia));
                    rowGraph.getCell(5 * 2 + 1).setCellStyle(estiloPresencia);
                    rowCont[5]++;
                }
                if (pseudomona.toLowerCase().contains("ausencia") || pseudomona.toLowerCase().contains("presencia")) {
                    int filaActual = rowCont[6];
                    Row rowGraph = sheetHidden.getRow(filaActual);
                    if (rowGraph == null) {
                        rowGraph = sheetHidden.createRow(filaActual);
                    }
                    rowGraph.createCell(6 * 2).setCellValue(procesarCeldaGrafico(idmuestras));
                    rowGraph.createCell(6 * 2 + 1).setCellValue(extraerAusenciaPresencia(pseudomona));
                    rowGraph.getCell(6 * 2 + 1).setCellStyle(estiloPresencia);
                    rowCont[6]++;
                }
                Double mohosGraph = extraerNumero(mohos);
                if (mohosGraph != null && !Double.isNaN(mohosGraph) && mohosGraph >= 0) {
                    int filaActual = rowCont[7];
                    Row rowGraph = sheetHidden.getRow(filaActual);
                    if (rowGraph == null) {
                        rowGraph = sheetHidden.createRow(filaActual);
                    }
                    rowGraph.createCell(7 * 2).setCellValue(procesarCeldaGrafico(idmuestras));
                    rowGraph.createCell(7 * 2 + 1).setCellValue(mohosGraph);
                    rowCont[7]++;
                }
                Double shigellaGraph = extraerNumero(shigella);
                if (shigellaGraph != null && !Double.isNaN(shigellaGraph) && shigellaGraph >= 0) {
                    int filaActual = rowCont[8];
                    Row rowGraph = sheetHidden.getRow(filaActual);
                    if (rowGraph == null) {
                        rowGraph = sheetHidden.createRow(filaActual);
                    }
                    rowGraph.createCell(8 * 2).setCellValue(procesarCeldaGrafico(idmuestras));
                    rowGraph.createCell(8 * 2 + 1).setCellValue(shigellaGraph);
                    rowCont[8]++;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error en la consulta SQL: " + e.getMessage());
        } finally {
            try {
                rs.close();
                ps.close();
            } catch (Exception e) {
                System.err.println("Error al cerrar recursos: " + e.getMessage());
            }
        }
    }

    private void consultarDeterminacionesParaExcel(int idcliente, String tipo, java.sql.Date desdeSql, java.sql.Date hastaSql, Sheet sheet, Sheet sheetHidden, int[] rowNum, int[] rowCont, Workbook workbook) {
        Connection conexion = con.getConnection();
        String sql = "SELECT d.idmuestras, vd.fechaAnalisis, d.acidez, d.acidoCianurico, d.acidoSorbico, d.actividadAcuosa, d.alcalinidad, d.alcohol, " + "d.aluminio, d.amonio, d.amoniacos, d.antimonio, d.aroma, d.arsenico, d.asbesto, d.aspecto, d.azucares, d.azucaresDeducidas, " + "d.azucaresInvertidos, d.azucaresReductores, d.bario, d.bicarbonatos, d.boro, d.bromuro, d.cadmioTotal, d.calcio, d.caracteristicas, " + "d.carbonatos, d.cenizas, d.cenizasInsolublesAcido, d.cenizasInsolublesAgua, d.cianuros, d.cloroActivo, d.cloroResidual, d.cloroTotal, " + "d.cloruros, d.cobalto, d.cobre, d.colesterol, d.color, d.colorantesartificiales, d.colorantesnaturales, d.colorantes, d.conductividad, " + "d.cromoHexavalente, d.dbo, d.detergentes, d.dqo, d.dureza, d.edulcorantes, d.estano, d.extracto, d.extractoseco, d.fenoles, d.fluoruros, " + "d.fluor, d.fosfatos, d.fosforoTotal, d.gliadinas, d.gluten, d.gradoFermentacion, d.gradosBrix, d.grasa, d.grasasCacao, d.grasasLeche, " + "d.grasasyAceites, d.hidracina, d.hidrocarburos, d.hidrocarburosc6, d.hidrocarburosc6_c35, d.hidrocarburosc6_c8, d.hidrocarburosc8_c10, " + "d.hidrocarburosc10_c12, d.hidrocarburosc12_c16, d.hidrocarburosc16_c21, d.hidrocarburosc21_c35, d.hierro, d.humedad, d.magnesio, " + "d.manganeso, d.materiagrasa, d.mercurioTotal, d.molibdeno, d.nitratos, d.nitritos, d.nitrogenoAmoniacal, d.nitrogenoTotal, d.niquel, " + "d.observacionMicroscopica, d.olor, d.organoclorados, d.oxigenoDisuelto, d.ozono, d.peroxidoHidrogeno, d.ph, d.plata, d.plomo, " + "d.porcentajeCloruro, d.potasio, d.propionato, d.relacion, d.residuo105, d.residuo180, d.residuoSeco, d.sabor, d.selenio, d.silicatos, " + "d.sodio, d.sulfatos, d.sulfuros, d.sustancias, d.sustanciasEterEtilico, d.solidosTotales, d.solidosNoGrasosCacao, d.solidosNoGrasos, " + "d.solidos10Minutos, d.solidos2Horas, d.solidosSuspendidosTotales, d.solidosSuspendidosVolatiles, d.temperatura, d.turbidez, d.vanadio, " + "d.vibrio, d.zinc FROM determinaciones d join vistafqcompleto vd on d.idmuestras = vd.idmuestras WHERE d.idmuestras IN " + "(SELECT idmuestras FROM muestras WHERE idcliente = ? AND tipo = ? AND fechaAnalisis BETWEEN ? AND ?)";

        // Reiniciar contadores
        for (int i = 0; i < rowCont.length; i++) {
            rowCont[i] = 0;
        }

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, idcliente);
            ps.setString(2, tipo);
            ps.setDate(3, desdeSql);
            ps.setDate(4, hastaSql);

            try (ResultSet rs = ps.executeQuery()) {
                var rsmd = rs.getMetaData();
                int columnCount = rsmd.getColumnCount();

                while (rs.next()) {
                    Row row = sheet.createRow(rowNum[0]++);
                    String idmuestras = String.valueOf(rs.getInt("idmuestras"));

                    for (int i = 1; i <= columnCount; i++) {
                        String valor = rs.getString(i);
                        row.createCell(i - 1).setCellValue(formatearEntradaExcel(valor));
                    }

                    for (int i = 3; i <= columnCount; i++) {
                        String valor = rs.getString(i);
                        if (valor == null) {
                            continue;
                        }

                        int graphIdx = i - 3; // Índice para rowCont (arranca en la primera determinación real)
                        Double valorNum = null;
                        String valorLower = valor.toLowerCase();

                        if (valorLower.contains("ausencia") || valorLower.contains("presencia")) {
                            valorNum = Double.parseDouble(extraerAusenciaPresencia(valor));
                        } else if (valorLower.contains("lc")) {
                            valorNum = extraerValorLC(valor);
                        } else {
                            valorNum = extraerNumero(valor);
                        }

                        if (valorNum != null && !Double.isNaN(valorNum)) {
                            int filaActual = rowCont[graphIdx];
                            Row rowGraph = sheetHidden.getRow(filaActual);
                            if (rowGraph == null) {
                                rowGraph = sheetHidden.createRow(filaActual);
                            }

                            Cell cellId = rowGraph.createCell(graphIdx * 2);
                            cellId.setCellValue(procesarCeldaGrafico(idmuestras));

                            Cell cellVal = rowGraph.createCell(graphIdx * 2 + 1);
                            cellVal.setCellValue(valorNum);

                            rowCont[graphIdx]++;
                        }
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error en Determinaciones: " + e.getMessage());
        }
    }

    private Double extraerValorLC(String valor) {
        System.out.println("valor = " + valor);

        // Caso 1: tiene "=" -> "< LC = 5.0" o "<LC=5.0"
        java.util.regex.Matcher m = java.util.regex.Pattern.compile("=\\s*([0-9]+(?:[.,][0-9]+)?)").matcher(valor);
        if (m.find()) {
            return Double.parseDouble(m.group(1).replace(",", "."));
        }

        // Caso 2: no tiene "=" -> "<LC 5.0 mg/kg" o "texto antes <LC 5.0"
        m = java.util.regex.Pattern.compile("lc\\D*([0-9]+(?:[.,][0-9]+)?)", java.util.regex.Pattern.CASE_INSENSITIVE).matcher(valor);
        if (m.find()) {
            return Double.parseDouble(m.group(1).replace(",", "."));
        }

        // Fallback: si no hay "lc" en absoluto, intenta extraer cualquier número de la cadena
        return extraerNumero(valor);
    }

    private void consultarMBAlimentosParaExcel(int idcliente, String tipo, java.sql.Date desdeSql, java.sql.Date hastaSql, Sheet sheet, Sheet sheetHidden, int[] rowNum, int[] rowCont, Workbook workbook) {
        Connection conexion = con.getConnection();
        String sql = "SELECT idmuestras, fechaAnalisis, germenes, coliformesTotales, coliformesFecales, escherichia, " + "escherichiah7, escherichia157, enterobacterias, staphilococos, mohosLevaduras, salmonella, listeria, " + "bacillus, perfringens, sulfito, campilobacter, coliformesTotalesA30, coliformesTotalesProbables, " + "caracteristicas, lactobacillus, bacteriasLacticas, coliformesTotales45, vibrio, shigella, vibrioCholerae " + "FROM vistambalimentos WHERE idmuestras IN (SELECT idmuestras FROM muestras WHERE idcliente = ? AND tipo = ?) " + "AND fechaAnalisis BETWEEN ? AND ?";

        // Columnas que se procesan como presencia/ausencia
        List<String> columnasPresencia = Arrays.asList("escherichia", "escherichiah7", "escherichia157", "salmonella", "shigella");

        // Reiniciar contadores
        for (int i = 0; i < rowCont.length; i++) {
            rowCont[i] = 0;
        }

        CellStyle estiloPresencia = workbook.createCellStyle();
        DataFormat format = workbook.createDataFormat();
        estiloPresencia.setDataFormat(format.getFormat("[=1]\"Presencia\";[=0]\"Ausencia\";\"N/A\""));

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, idcliente);
            ps.setString(2, tipo);
            ps.setDate(3, desdeSql);
            ps.setDate(4, hastaSql);

            try (ResultSet rs = ps.executeQuery()) {
                var rsmd = rs.getMetaData();
                int columnCount = rsmd.getColumnCount();

                while (rs.next()) {
                    Row row = sheet.createRow(rowNum[0]++);
                    String idmuestras = String.valueOf(rs.getInt("idmuestras"));

                    for (int i = 1; i <= columnCount; i++) {
                        String valor = rs.getString(i);
                        row.createCell(i - 1).setCellValue(formatearEntradaExcel(valor));
                    }

                    for (int i = 3; i <= columnCount; i++) {
                        String colName = rsmd.getColumnName(i);
                        String valor = rs.getString(i);
                        if (valor == null) {
                            continue;
                        }

                        int graphIdx = i - 3; // Índice para rowCont
                        Double valorNum = null;
                        System.out.println("colName = " + colName);
                        boolean esP_A = columnasPresencia.contains(colName.toLowerCase());

                        if ((valor.toLowerCase().contains("ausencia") || valor.toLowerCase().contains("presencia"))) {
                            valorNum = Double.parseDouble(extraerAusenciaPresencia(valor));
                        } else {
                            valorNum = extraerNumero(valor);
                        }

                        if (valorNum != null && !Double.isNaN(valorNum)) {
                            int filaActual = rowCont[graphIdx];
                            Row rowGraph = sheetHidden.getRow(filaActual);
                            if (rowGraph == null) {
                                rowGraph = sheetHidden.createRow(filaActual);
                            }

                            Cell cellId = rowGraph.createCell(graphIdx * 2);
                            cellId.setCellValue(procesarCeldaGrafico(idmuestras));

                            Cell cellVal = rowGraph.createCell(graphIdx * 2 + 1);
                            cellVal.setCellValue(valorNum);

                            if (esP_A) {
                                int num = (int) valorNum.doubleValue();
                                cellVal.setCellValue(num);
                                cellVal.setCellStyle(estiloPresencia);
                            }

                            rowCont[graphIdx]++;
                        }
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error en MB Alimentos: " + e.getMessage());
        }
    }

    private String[] obtenerColumnas(Tipo c) {
        switch (c) {
            case EFLUENTES:
                return new String[]{"ID", "Fecha", " pH", "DEMANDA QUÍMICA DE OXÍGENO (DQO)", "DEMANDA BIOQUÍMICA DE OXÍGENO (DBO)", "SÓLIDOS DISUELTOS SEDIMENTABLES EN 10’", "SÓLIDOS DISUELTOS SEDIMENTABLES EN 120’", "DETERGENTES", "GRASAS Y ACEITES", "FÒSFORO TOTAL", "NITRÓGENO TOTAL", "SUSTANCIAS SOLUBLES EN ETER ETILICO", "COLIFORMES TOTALES", "COLIFORMES FECALES", "ESCHERICHIA COLI", "CONDUCTIVIDAD", "HIDROCARBUROS TOTALES DE PETRÓLEO (IR)", "NITRATOS", "CLORO", "SULFUROS"};
            case MBAGUACODIGO:
                return new String[]{"ID", "FECHA", "% Total Libre", "pH", "GERMENES AEROBIOS TOTALES", "COLIFORMES TOTALES", "COLIFORMES FECALES", "ESCHERICHIA COLI", "PSEUDOMONA AERUGINOSA", "MOHOS Y LEVADURAS", "SHIGELLA"};
            case MBALIMENTOS:
                return new String[]{"ID", "FECHA", "GERMENES AEROBIOS TOTALES", "COLIFORMES TOTALES", "COLIFORMES FECALES", "ESCHERICHIA COLI", "ESCHERICHIA COLI O 157 H7", "ESCHERICHIA COLI NO O 157", "ENTEROBACTERIAS", "STAPHILOCOCOS AUREUS COAGULASA (+)", "MOHOS Y LEVADURAS", "SALMONELLA sp", "LISTERIA MONOCYTOGENES", "BACILLUS CEREUS", "CLOSTRIDIUM PERFRINGENS", "CLOSTRIDIUM SULFITO REDUCTORES Ó ANAEROBIOS", "CAMPILOBACTER", "COLIFORMES TOTALES A 30°C", "COLIFORMES TOTALES POR NÚMERO MÁS PROBABLE", "CARACTERISTICAS ORGANOLEPTICAS", "RECUENTO DE LACTOBACILLUS", "RECUENTO DE BACTERIAS LÁCTICAS", "RECUENTO DE COLIFORMES TOTALES A 45°C", "VIBRIO PARAHEMOLITYCUS", "SHIGELLA", "VIBRIO CHOLERAE"};
            case MBAGUACOFES:
                return new String[]{"ID", "FECHA", "% Total Libre", "pH", "GERMENES AEROBIOS TOTALES", "COLIFORMES TOTALES", "COLIFORMES FECALES", "ESCHERICHIA COLI", "PSEUDOMONA AERUGINOSA", "SHIGELLA"};
            case FQALIMENTOS:
                return new String[]{"ID", "FECHA", "Acidez", "Ácido cianúrico", "Ácido sórbico", "Actividad acuosa", "Alcalinidad", "Alcohol", "Aluminio", "Amonio", "Amoníaco", "Antimonio", "Aroma", "Arsénico", "Asbesto", "Aspecto", "Azúcares", "Azúcares deducidas de la lactosa", "Azúcares invertidos", "Azúcares reductores", "Bario", "Bicarbonatos", "Boro", "Bromuro", "Cadmio total", "Calcio", "Características Organolépticas", "Carbonatos", "Cenizas", "Cenizas insolubles en ácido cítrico", "Cenizas solubles en agua de las cenizas totales", "Cianuros", "Cloro activo", "Cloro residual o libre", "Cloro total", "Cloruros", "Cobalto", "Cobre", "Colesterol", "Color", "Colorantes artificiales", "Colorantes naturales", "Colorantes naturales y artificiales", "Conductividad", "Cromo Hexavalente", "DBO", "Detergentes", "DQO", "Dureza", "Edulcorantes", "Estaño", "Extracto primitivo", "Extracto seco", "Fenoles", "Fluoruros", "Flúor", "Fosfatos", "Fósforo total", "Gliadinas", "Gluten", "Grado de fermentación", "Grados Brix", "Grasa", "Grasas de cacao", "Grasas de leche", "Grasas y aceites", "Hidracina", "Hidrocarburos", "HIDROCARBUROS TOTALES DE PETROLEO (C6)", "HIDROCARBUROS TOTALES DE PETROLEO (C6-C35)", "HIDROCARBUROS TOTALES DE PETROLEO (C6-C8)", "HIDROCARBUROS TOTALES DE PETROLEO (C8-C10)", "HIDROCARBUROS TOTALES DE PETROLEO (C10-C12)", "HIDROCARBUROS TOTALES DE PETROLEO (C12-C16)", "HIDROCARBUROS TOTALES DE PETROLEO (C16-C21)", "HIDROCARBUROS TOTALES DE PETROLEO (C21-C35)", "Hierro", "Humedad", "Magnesio", "Manganeso", "Materia grasa", "Mercurio Total", "Molibdeno", "Nitratos", "Nitritos", "Nitrógeno Amoniacal", "Nitrógeno total", "Níquel", "Observación microscópica", "Olor", "Organoclorados", "Oxígeno disuelto", "Ozono", "Peróxido de hidrógeno", "pH", "Plata", "Plomo", "Porcentaje de cloruro de sodio", "Potasio", "Propionato de sodio", "Relación Peso/Humedad", "Residuo evaporación a 105ºC", "Residuo evaporación a 180ºC", "Residuo Seco", "Sabor", "Selenio", "Silicatos", "Sodio", "Sulfatos", "Sulfuros", "Sustancias extrañas", "Sustancias solubles en éter etílico", "Sólidos Disueltos totales", "Sólidos no grasos de cacao", "Sólidos no grasos de leche", "Sólidos sedimentables en 10 minutos", "Sólidos sedimentables en 2 horas", "Sólidos suspendidos totales", "Sólidos suspendidos volátiles", "Temperatura", "Turbidez", "Vanadio", "VIBRIO CHOLERAE", "Zinc"};
        }

        return null;
    }

    public double extraerNumero(String texto) {
        if (texto != null) {
            texto = texto.trim();
        }
        if (texto == null || texto.isEmpty() || texto.contains("-2") || texto.contains("-1")) {
            return Double.NaN;
        }

        if (StringUtils.startsWithIgnoreCase(texto, "menor")) {
            return 0;
        }
        if (texto.toLowerCase().contains("mayor a")) {
            System.out.println("texto = " + texto);
            System.out.println("texto.toLowerCase().replace(\"Mayor a \", \"\").trim() = " + texto.toLowerCase().replace("Mayor a ", "").trim());
            return Double.parseDouble(texto.toLowerCase().replace("Mayor a ", "").trim().replaceAll("[^0-9.]", ""));
        }
        String soloNumeros = "";
        if (texto.contains(" ")) {
            System.out.println("texto antes double " + texto.trim().substring(0, texto.indexOf(" ")));
            Double ret = Double.parseDouble(texto.substring(0, texto.trim().indexOf(" ")).replaceAll("[^0-9.]", ""));
            return ret;
        }
        soloNumeros = texto.replaceAll("[^0-9.]", "");
        try {
            return Double.parseDouble(soloNumeros);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    private String procesarCeldaGrafico(String valor) {
        return valor.replaceAll("[^0-9.]", "");

    }

    private String extraerAusenciaPresencia(String aux) {
        if (aux.toLowerCase().contains("presencia")) {
            return "1";
        }
        return "0";
    }

    private String formatearEntradaExcel(String aux) {
        if (aux == null || aux.contains("-2") || aux.isBlank() || aux.contains("-1")) {
            return "N/A";
        }
        return aux;
    }

    public void generarReporteHisopados(int id, String procedencia) {
        Connection conexion = con.getConnection();
        Map m = new HashMap();
        Principal p = new Principal();
        try {
            JasperReport reporte = null;
            String ruta = getClass().getResource("/reporte/reporteHisopado.jasper").getPath();
            reporte = (JasperReport) JRLoader.loadObjectFromFile(ruta);//se toma el archivo .jasper para generar el main.resources.reporte
            Map mapa = new HashMap();
            imagenesMap(mapa);
            mapa.put("id", id);
            JasperPrint imprimirReporte = JasperFillManager.fillReport(reporte, mapa, conexion);            //llena los campos del main.resources.reporte
            JasperViewer vistaReporte = new JasperViewer(imprimirReporte, false);                           //crea el visor del main.resources.reporte
            String[] nombre = obtenerProcedenciayNombreEmail(obtenerIdCliente(procedencia));
            String aux; //en estas tres lineas se sacan espacios de
            if (nombre[0].contains("/") || nombre[0].contains("" + org.ignaciorodriguez.utils.SeparatorUtils.s + "") || nombre[0].contains(":") || nombre[0].contains("*") || nombre[0].contains("?") || nombre[0].contains("\"") || nombre[0].contains("<") || nombre[0].contains(">") || nombre[0].contains(">") || nombre[1].contains("/") || nombre[1].contains("" + org.ignaciorodriguez.utils.SeparatorUtils.s + "") || nombre[1].contains(":") || nombre[1].contains("*") || nombre[1].contains("?") || nombre[1].contains("\"") || nombre[1].contains("<") || nombre[1].contains(">") || nombre[1].contains(">")) {
                nombre[0] = nombre[0].replaceAll("[/" + org.ignaciorodriguez.utils.SeparatorUtils.s + "" + org.ignaciorodriguez.utils.SeparatorUtils.s + ":*?\"<>|]", "_");
                nombre[1] = nombre[1].replaceAll("[/" + org.ignaciorodriguez.utils.SeparatorUtils.s + "" + org.ignaciorodriguez.utils.SeparatorUtils.s + ":*?\"<>|]", "_");
            }
            if (nombre[1].isBlank() || nombre[1] == null) {
                aux = "" + org.ignaciorodriguez.utils.SeparatorUtils.s + "Inf. " + id + " Hisopado " + nombre[0];
            } else {
                aux = "" + org.ignaciorodriguez.utils.SeparatorUtils.s + "Inf. " + id + " Hisopado " + nombre[1];
            }
            String aux2 = aux.replaceAll("^" + org.ignaciorodriguez.utils.SeparatorUtils.s + "s*", "");                                                       //principio, final y se sacan las comillas
            String aux3 = aux2.replaceAll("" + org.ignaciorodriguez.utils.SeparatorUtils.s + "s*$", "");
            String pdf = aux3.replaceAll("\"", "");
            pdf += ".pdf";                                                                                  //se agrega la extensión .pdf
            String rutaGuardado = recuperarRutas("Reportes");
            JasperExportManager.exportReportToPdfFile(imprimirReporte, rutaGuardado + pdf);
            vistaReporte.setDefaultCloseOperation(JasperViewer.DO_NOTHING_ON_CLOSE);
            vistaReporte.setExtendedState(vistaReporte.MAXIMIZED_BOTH);
            ImageIcon icon = new ImageIcon("src" + org.ignaciorodriguez.utils.SeparatorUtils.s + "vista" + org.ignaciorodriguez.utils.SeparatorUtils.s + "icono.png");
            vistaReporte.setIconImage(icon.getImage());
            vistaReporte.setVisible(true);
            vistaReporte.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    int email = JOptionPane.showConfirmDialog(null, "¿Enviar email?", "Email", JOptionPane.YES_NO_OPTION);
                    ;
                    if (JOptionPane.YES_OPTION == email) {
                        String[] nombre = obtenerProcedenciayNombreEmail(obtenerIdCliente(procedencia));
                        String aux; //en estas tres lineas se sacan espacios de
                        if (nombre[0].contains("/") || nombre[0].contains("" + org.ignaciorodriguez.utils.SeparatorUtils.s + "") || nombre[0].contains(":") || nombre[0].contains("*") || nombre[0].contains("?") || nombre[0].contains("\"") || nombre[0].contains("<") || nombre[0].contains(">") || nombre[0].contains(">") || nombre[1].contains("/") || nombre[1].contains("" + org.ignaciorodriguez.utils.SeparatorUtils.s + "") || nombre[1].contains(":") || nombre[1].contains("*") || nombre[1].contains("?") || nombre[1].contains("\"") || nombre[1].contains("<") || nombre[1].contains(">") || nombre[1].contains(">")) {
                            nombre[0] = nombre[0].replaceAll("[/" + org.ignaciorodriguez.utils.SeparatorUtils.s + "" + org.ignaciorodriguez.utils.SeparatorUtils.s + ":*?\"<>|]", "_");
                            nombre[1] = nombre[1].replaceAll("[/" + org.ignaciorodriguez.utils.SeparatorUtils.s + "" + org.ignaciorodriguez.utils.SeparatorUtils.s + ":*?\"<>|]", "_");
                        }
                        if (nombre[1].isBlank() || nombre[1] == null) {
                            aux = "" + org.ignaciorodriguez.utils.SeparatorUtils.s + "Inf. " + id + " Hisopado " + nombre[0];
                        } else {
                            aux = "" + org.ignaciorodriguez.utils.SeparatorUtils.s + "Inf. " + id + " Hisopado " + nombre[1];
                        }
                        String aux2 = aux.replaceAll("^" + org.ignaciorodriguez.utils.SeparatorUtils.s + "s*", "");                                                       //principio, final y se sacan las comillas
                        String aux3 = aux2.replaceAll("" + org.ignaciorodriguez.utils.SeparatorUtils.s + "s*$", "");
                        String pdf = aux3.replaceAll("\"", "");
                        pdf += ".pdf";

                        String pdfEmail = rutaGuardado + pdf;
                        VentanaEmail vEmail = new VentanaEmail(p, true, id, pdfEmail);
                        vEmail.setVisible(true);
                    }
                }
            });
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al generar informe, " + e);
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
    }

    public void generarReporteHisopadosAlliance(int id, String procedencia) {
        Connection conexion = con.getConnection();
        Map m = new HashMap();
        Principal p = new Principal();
        try {
            JasperReport reporte = null;
            String ruta = getClass().getResource("/reporte/reporteHisopadoAlliance.jasper").getPath();
            reporte = (JasperReport) JRLoader.loadObjectFromFile(ruta);//se toma el archivo .jasper para generar el main.resources.reporte
            Map mapa = new HashMap();
            imagenesMap(mapa);
            mapa.put("id", id);
            JasperPrint imprimirReporte = JasperFillManager.fillReport(reporte, mapa, conexion);            //llena los campos del main.resources.reporte
            JasperViewer vistaReporte = new JasperViewer(imprimirReporte, false);                           //crea el visor del main.resources.reporte
            String[] nombre = obtenerProcedenciayNombreEmail(obtenerIdCliente(procedencia));
            String aux; //en estas tres lineas se sacan espacios de
            if (nombre[0].contains("/") || nombre[0].contains("" + org.ignaciorodriguez.utils.SeparatorUtils.s + "") || nombre[0].contains(":") || nombre[0].contains("*") || nombre[0].contains("?") || nombre[0].contains("\"") || nombre[0].contains("<") || nombre[0].contains(">") || nombre[0].contains(">") || nombre[1].contains("/") || nombre[1].contains("" + org.ignaciorodriguez.utils.SeparatorUtils.s + "") || nombre[1].contains(":") || nombre[1].contains("*") || nombre[1].contains("?") || nombre[1].contains("\"") || nombre[1].contains("<") || nombre[1].contains(">") || nombre[1].contains(">")) {
                nombre[0] = nombre[0].replaceAll("[/" + org.ignaciorodriguez.utils.SeparatorUtils.s + "" + org.ignaciorodriguez.utils.SeparatorUtils.s + ":*?\"<>|]", "_");
                nombre[1] = nombre[1].replaceAll("[/" + org.ignaciorodriguez.utils.SeparatorUtils.s + "" + org.ignaciorodriguez.utils.SeparatorUtils.s + ":*?\"<>|]", "_");
            }
            if (nombre[1].isBlank() || nombre[1] == null) {
                aux = "" + org.ignaciorodriguez.utils.SeparatorUtils.s + "Inf. " + id + " Hisopado " + nombre[0];
            } else {
                aux = "" + org.ignaciorodriguez.utils.SeparatorUtils.s + "Inf. " + id + " Hisopado " + nombre[1];
            }
            String aux2 = aux.replaceAll("^" + org.ignaciorodriguez.utils.SeparatorUtils.s + "s*", "");                                                       //principio, final y se sacan las comillas
            String aux3 = aux2.replaceAll("" + org.ignaciorodriguez.utils.SeparatorUtils.s + "s*$", "");
            String pdf = aux3.replaceAll("\"", "");
            pdf += ".pdf";                                                                                  //se agrega la extensión .pdf
            String rutaGuardado = recuperarRutas("Reportes");
            JasperExportManager.exportReportToPdfFile(imprimirReporte, rutaGuardado + pdf);
            vistaReporte.setDefaultCloseOperation(JasperViewer.DO_NOTHING_ON_CLOSE);
            vistaReporte.setExtendedState(vistaReporte.MAXIMIZED_BOTH);
            ImageIcon icon = new ImageIcon("src" + org.ignaciorodriguez.utils.SeparatorUtils.s + "vista" + org.ignaciorodriguez.utils.SeparatorUtils.s + "icono.png");
            vistaReporte.setIconImage(icon.getImage());
            vistaReporte.setVisible(true);
            vistaReporte.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    int email = JOptionPane.showConfirmDialog(null, "¿Enviar email?", "Email", JOptionPane.YES_NO_OPTION);
                    ;
                    if (JOptionPane.YES_OPTION == email) {
                        String[] nombre = obtenerProcedenciayNombreEmail(obtenerIdCliente(procedencia));
                        String aux; //en estas tres lineas se sacan espacios de
                        if (nombre[0].contains("/") || nombre[0].contains("" + org.ignaciorodriguez.utils.SeparatorUtils.s + "") || nombre[0].contains(":") || nombre[0].contains("*") || nombre[0].contains("?") || nombre[0].contains("\"") || nombre[0].contains("<") || nombre[0].contains(">") || nombre[0].contains(">") || nombre[1].contains("/") || nombre[1].contains("" + org.ignaciorodriguez.utils.SeparatorUtils.s + "") || nombre[1].contains(":") || nombre[1].contains("*") || nombre[1].contains("?") || nombre[1].contains("\"") || nombre[1].contains("<") || nombre[1].contains(">") || nombre[1].contains(">")) {
                            nombre[0] = nombre[0].replaceAll("[/" + org.ignaciorodriguez.utils.SeparatorUtils.s + "" + org.ignaciorodriguez.utils.SeparatorUtils.s + ":*?\"<>|]", "_");
                            nombre[1] = nombre[1].replaceAll("[/" + org.ignaciorodriguez.utils.SeparatorUtils.s + "" + org.ignaciorodriguez.utils.SeparatorUtils.s + ":*?\"<>|]", "_");
                        }
                        if (nombre[1].isBlank() || nombre[1] == null) {
                            aux = "" + org.ignaciorodriguez.utils.SeparatorUtils.s + "Inf. " + id + " Hisopado " + nombre[0];
                        } else {
                            aux = "" + org.ignaciorodriguez.utils.SeparatorUtils.s + "Inf. " + id + " Hisopado " + nombre[1];
                        }
                        String aux2 = aux.replaceAll("^" + org.ignaciorodriguez.utils.SeparatorUtils.s + "s*", "");                                                       //principio, final y se sacan las comillas
                        String aux3 = aux2.replaceAll("" + org.ignaciorodriguez.utils.SeparatorUtils.s + "s*$", "");
                        String pdf = aux3.replaceAll("\"", "");
                        pdf += ".pdf";
                        String pdfEmail = rutaGuardado + pdf;
                        VentanaEmail vEmail = new VentanaEmail(p, true, id, pdfEmail);
                        vEmail.setVisible(true);
                    }
                }
            });
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al generar informe, " + e);
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
    }    public boolean checkearPDF(int id, String db) {
        Connection conexion = con.getConnection();
        try {
            if (db.contains("nutricional")) {
                ps = conexion.prepareStatement("SELECT `tablanutricional`.`calorias`,`tablanutricional`.`kjul`,`tablanutricional`.`carbohidratos`,`tablanutricional`.`proteinas`,`tablanutricional`.`grasasTotales`,`tablanutricional`.`grasasSaturadas`,`tablanutricional`.`grasasTrans`,`tablanutricional`.`GrasasMonoinsaturadas`,`tablanutricional`.`GrasasPoliinsaturadas`,`tablanutricional`.`Colesterol`,`tablanutricional`.`fibraAlimentaria`,`tablanutricional`.`sodio`,`tablanutricional`.`VDCalorias`,`tablanutricional`.`VDCarbohidratos`,`tablanutricional`.`VDProteinas`,`tablanutricional`.`VDGrasasTotales`,`tablanutricional`.`VDGrasasSaturadas`,`tablanutricional`.`VDGrasasMonoinsaturadas`,`tablanutricional`.`VDGrasasPoliinsaturadas`,`tablanutricional`.`VDColesterol`,`tablanutricional`.`VDGrasasTrans`,`tablanutricional`.`VDFibraAlimentaria`,`tablanutricional`.`VDSodio`,`tablanutricional`.`porcion`,`tablanutricional`.`unidad`,`tablanutricional`.`azucares`,`tablanutricional`.`VDAzucares`,`tablanutricional`.`almidon`,`tablanutricional`.`VDAlmidon`,`tablanutricional`.`PorcionesPorEnvase`,`tablanutricional`.`azucaresAnadidos`,`tablanutricional`.`VDAzucaresAnadidos` FROM `laboratorio`.`tablanutricional` where idmuestras = ?");
                ps.setInt(1, id);
                rs = ps.executeQuery();
                while (rs.next()) {
                    if (rs.getObject(1) == null) {
                        return false;
                    } else {
                        return true;
                    }
                }
                return false;
            } else if (db.contains("mbagua")) {
                ps = conexion.prepareStatement("select coliformesTotales from " + db + " where idmuestras = " + id);
                rs = ps.executeQuery();
                while (rs.next()) {
                    if (rs.getObject(1) == null) {
                        return false;
                    } else {
                        return true;
                    }
                }
            } else {
                ps = conexion.prepareStatement("select * from " + db + " where idmuestras = " + id);
                rs = ps.executeQuery();
                int cantidad = rs.getMetaData().getColumnCount();
                boolean existe = false;
                while (rs.next()) {

                    for (int i = 2; i < cantidad; i++) {
                        existe = existe || (rs.getObject(i + 1) == null ? false : !rs.getObject(i + 1).toString().trim().isEmpty());
                    }
                    return existe;
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al recuperar pdf, " + e);
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
        return false;
    }

    public void generarReporteMBAguaDeRecreacion(int id, String procedencia) {
        Connection conexion = con.getConnection();
        Principal p = new Principal();
        try {
            JasperReport reporte = null;
            String ruta = getClass().getResource("/reporte/reporteMBAguadeRecreacion.jasper").getPath();
            reporte = (JasperReport) JRLoader.loadObjectFromFile(ruta);//se toma el archivo .jasper para generar el main.resources.reporte
            Map mapa = new HashMap();
            mapa.put("id", id); //se pasa el id al main.resources.reporte
            imagenesMap(mapa);
            JasperPrint imprimirReporte = JasperFillManager.fillReport(reporte, mapa, conexion);            //llena los campos del main.resources.reporte
            JasperViewer vistaReporte = new JasperViewer(imprimirReporte, false);                           //crea el visor del main.resources.reporte
            String[] nombre = obtenerProcedenciayNombreEmail(obtenerIdCliente(procedencia));
            String aux; //en estas tres lineas se sacan espacios de
            if (nombre[0].contains("/") || nombre[0].contains("" + org.ignaciorodriguez.utils.SeparatorUtils.s + "") || nombre[0].contains(":") || nombre[0].contains("*") || nombre[0].contains("?") || nombre[0].contains("\"") || nombre[0].contains("<") || nombre[0].contains(">") || nombre[0].contains(">") || nombre[1].contains("/") || nombre[1].contains("" + org.ignaciorodriguez.utils.SeparatorUtils.s + "") || nombre[1].contains(":") || nombre[1].contains("*") || nombre[1].contains("?") || nombre[1].contains("\"") || nombre[1].contains("<") || nombre[1].contains(">") || nombre[1].contains(">")) {
                nombre[0] = nombre[0].replaceAll("[/" + org.ignaciorodriguez.utils.SeparatorUtils.s + "" + org.ignaciorodriguez.utils.SeparatorUtils.s + ":*?\"<>|]", "_");
                nombre[1] = nombre[1].replaceAll("[/" + org.ignaciorodriguez.utils.SeparatorUtils.s + "" + org.ignaciorodriguez.utils.SeparatorUtils.s + ":*?\"<>|]", "_");
            }
            if (nombre[1].isBlank() || nombre[1] == null) {
                aux = "" + org.ignaciorodriguez.utils.SeparatorUtils.s + "Inf. " + id + " MB Agua de recreación " + nombre[0];
            } else {
                aux = "" + org.ignaciorodriguez.utils.SeparatorUtils.s + "Inf. " + id + " MB Agua de recreación " + nombre[1];
            }
            String aux2 = aux.replaceAll("^" + org.ignaciorodriguez.utils.SeparatorUtils.s + "s*", "");                                                       //principio, final y se sacan las comillas
            String aux3 = aux2.replaceAll("" + org.ignaciorodriguez.utils.SeparatorUtils.s + "s*$", "");
            String pdf = aux3.replaceAll("\"", "");
            pdf += ".pdf";                                                                                  //se agrega la extensión .pdf
            String rutaGuardado = recuperarRutas("Reportes");
            JasperExportManager.exportReportToPdfFile(imprimirReporte, rutaGuardado + pdf);
            vistaReporte.setDefaultCloseOperation(JasperViewer.DO_NOTHING_ON_CLOSE);
            vistaReporte.setExtendedState(vistaReporte.MAXIMIZED_BOTH);
            ImageIcon icon = new ImageIcon("src" + org.ignaciorodriguez.utils.SeparatorUtils.s + "vista" + org.ignaciorodriguez.utils.SeparatorUtils.s + "icono.png");
            vistaReporte.setIconImage(icon.getImage());
            vistaReporte.setVisible(true);
            vistaReporte.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    int email = JOptionPane.showConfirmDialog(null, "¿Enviar email?", "Email", JOptionPane.YES_NO_OPTION);
                    ;
                    if (JOptionPane.YES_OPTION == email) {
                        String[] nombre = obtenerProcedenciayNombreEmail(obtenerIdCliente(procedencia));
                        String aux; //en estas tres lineas se sacan espacios de
                        if (nombre[0].contains("/") || nombre[0].contains("" + org.ignaciorodriguez.utils.SeparatorUtils.s + "") || nombre[0].contains(":") || nombre[0].contains("*") || nombre[0].contains("?") || nombre[0].contains("\"") || nombre[0].contains("<") || nombre[0].contains(">") || nombre[0].contains(">") || nombre[1].contains("/") || nombre[1].contains("" + org.ignaciorodriguez.utils.SeparatorUtils.s + "") || nombre[1].contains(":") || nombre[1].contains("*") || nombre[1].contains("?") || nombre[1].contains("\"") || nombre[1].contains("<") || nombre[1].contains(">") || nombre[1].contains(">")) {
                            nombre[0] = nombre[0].replaceAll("[/" + org.ignaciorodriguez.utils.SeparatorUtils.s + "" + org.ignaciorodriguez.utils.SeparatorUtils.s + ":*?\"<>|]", "_");
                            nombre[1] = nombre[1].replaceAll("[/" + org.ignaciorodriguez.utils.SeparatorUtils.s + "" + org.ignaciorodriguez.utils.SeparatorUtils.s + ":*?\"<>|]", "_");
                        }
                        if (nombre[1].isBlank() || nombre[1] == null) {
                            aux = "" + org.ignaciorodriguez.utils.SeparatorUtils.s + "Inf. " + id + " MB Agua de recreación " + nombre[0];
                        } else {
                            aux = "" + org.ignaciorodriguez.utils.SeparatorUtils.s + "Inf. " + id + " MB Agua de recreación " + nombre[1];
                        }
                        String aux2 = aux.replaceAll("^" + org.ignaciorodriguez.utils.SeparatorUtils.s + "s*", "");                                                       //principio, final y se sacan las comillas
                        String aux3 = aux2.replaceAll("" + org.ignaciorodriguez.utils.SeparatorUtils.s + "s*$", "");
                        String pdf = aux3.replaceAll("\"", "");
                        pdf += ".pdf";
                        String pdfEmail = rutaGuardado + pdf;
                        VentanaEmail vEmail = new VentanaEmail(p, true, id, pdfEmail);
                        vEmail.setVisible(true);
                    }
                }
            });
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al generar main.resources.reporte MB agua de recreacion, " + e);
            System.err.println(e.getStackTrace()[0]);
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error al generar main.resources.reporte MB agua de recreacion, " + e);
                System.err.println(e.getStackTrace()[0]);
            }
        }
    }

    public void generarReporteMBAguaCOFES(int id, String procedencia) {
        Connection conexion = con.getConnection();
        Principal p = new Principal();
        try {
            JasperReport reporte = null;
            String ruta = getClass().getResource("/reporte/reporteMBAguaCOFES.jasper").getPath();
            reporte = (JasperReport) JRLoader.loadObjectFromFile(ruta);//se toma el archivo .jasper para generar el main.resources.reporte
            Map mapa = new HashMap();
            mapa.put("id", id); //se pasa el id al main.resources.reporte
            imagenesMap(mapa);
            JasperPrint imprimirReporte = JasperFillManager.fillReport(reporte, mapa, conexion);            //llena los campos del main.resources.reporte
            JasperViewer vistaReporte = new JasperViewer(imprimirReporte, false);                           //crea el visor del main.resources.reporte
            String[] nombre = obtenerProcedenciayNombreEmail(obtenerIdCliente(procedencia));
            String aux; //en estas tres lineas se sacan espacios de
            if (nombre[0].contains("/") || nombre[0].contains("" + org.ignaciorodriguez.utils.SeparatorUtils.s + "") || nombre[0].contains(":") || nombre[0].contains("*") || nombre[0].contains("?") || nombre[0].contains("\"") || nombre[0].contains("<") || nombre[0].contains(">") || nombre[0].contains(">") || nombre[1].contains("/") || nombre[1].contains("" + org.ignaciorodriguez.utils.SeparatorUtils.s + "") || nombre[1].contains(":") || nombre[1].contains("*") || nombre[1].contains("?") || nombre[1].contains("\"") || nombre[1].contains("<") || nombre[1].contains(">") || nombre[1].contains(">")) {
                nombre[0] = nombre[0].replaceAll("[/" + org.ignaciorodriguez.utils.SeparatorUtils.s + "" + org.ignaciorodriguez.utils.SeparatorUtils.s + ":*?\"<>|]", "_");
                nombre[1] = nombre[1].replaceAll("[/" + org.ignaciorodriguez.utils.SeparatorUtils.s + "" + org.ignaciorodriguez.utils.SeparatorUtils.s + ":*?\"<>|]", "_");
            }
            if (nombre[1].isBlank() || nombre[1] == null) {
                aux = "" + org.ignaciorodriguez.utils.SeparatorUtils.s + "Inf. " + id + " MB Agua COFES " + nombre[0];
            } else {
                aux = "" + org.ignaciorodriguez.utils.SeparatorUtils.s + "Inf. " + id + " MB Agua COFES " + nombre[1];
            }
            String aux2 = aux.replaceAll("^" + org.ignaciorodriguez.utils.SeparatorUtils.s + "s*", "");                                                       //principio, final y se sacan las comillas
            String aux3 = aux2.replaceAll("" + org.ignaciorodriguez.utils.SeparatorUtils.s + "s*$", "");
            String pdf = aux3.replaceAll("\"", "");
            pdf += ".pdf";                                                                                  //se agrega la extensión .pdf
            String rutaGuardado = recuperarRutas("Reportes");
            JasperExportManager.exportReportToPdfFile(imprimirReporte, rutaGuardado + pdf);
            vistaReporte.setDefaultCloseOperation(JasperViewer.DO_NOTHING_ON_CLOSE);
            vistaReporte.setExtendedState(vistaReporte.MAXIMIZED_BOTH);
            ImageIcon icon = new ImageIcon("src" + org.ignaciorodriguez.utils.SeparatorUtils.s + "vista" + org.ignaciorodriguez.utils.SeparatorUtils.s + "icono.png");
            vistaReporte.setIconImage(icon.getImage());
            vistaReporte.setVisible(true);
            vistaReporte.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    int email = JOptionPane.showConfirmDialog(null, "¿Enviar email?", "Email", JOptionPane.YES_NO_OPTION);
                    ;
                    if (JOptionPane.YES_OPTION == email) {
                        String[] nombre = obtenerProcedenciayNombreEmail(obtenerIdCliente(procedencia));
                        String aux; //en estas tres lineas se sacan espacios de
                        if (nombre[0].contains("/") || nombre[0].contains("" + org.ignaciorodriguez.utils.SeparatorUtils.s + "") || nombre[0].contains(":") || nombre[0].contains("*") || nombre[0].contains("?") || nombre[0].contains("\"") || nombre[0].contains("<") || nombre[0].contains(">") || nombre[0].contains(">") || nombre[1].contains("/") || nombre[1].contains("" + org.ignaciorodriguez.utils.SeparatorUtils.s + "") || nombre[1].contains(":") || nombre[1].contains("*") || nombre[1].contains("?") || nombre[1].contains("\"") || nombre[1].contains("<") || nombre[1].contains(">") || nombre[1].contains(">")) {
                            nombre[0] = nombre[0].replaceAll("[/" + org.ignaciorodriguez.utils.SeparatorUtils.s + "" + org.ignaciorodriguez.utils.SeparatorUtils.s + ":*?\"<>|]", "_");
                            nombre[1] = nombre[1].replaceAll("[/" + org.ignaciorodriguez.utils.SeparatorUtils.s + "" + org.ignaciorodriguez.utils.SeparatorUtils.s + ":*?\"<>|]", "_");
                        }
                        if (nombre[1].isBlank() || nombre[1] == null) {
                            aux = "" + org.ignaciorodriguez.utils.SeparatorUtils.s + "Inf. " + id + " MB Agua COFES " + nombre[0];
                        } else {
                            aux = "" + org.ignaciorodriguez.utils.SeparatorUtils.s + "Inf. " + id + " MB Agua COFES " + nombre[1];
                        }
                        String aux2 = aux.replaceAll("^" + org.ignaciorodriguez.utils.SeparatorUtils.s + "s*", "");                                                       //principio, final y se sacan las comillas
                        String aux3 = aux2.replaceAll("" + org.ignaciorodriguez.utils.SeparatorUtils.s + "s*$", "");
                        String pdf = aux3.replaceAll("\"", "");
                        pdf += ".pdf";
                        String pdfEmail = rutaGuardado + pdf;
                        VentanaEmail vEmail = new VentanaEmail(p, true, id, pdfEmail);
                        vEmail.setVisible(true);
                    }
                }
            });
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al generar main.resources.reporte MB agua, " + e);
            System.err.println(e.getStackTrace()[0]);
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error al generar main.resources.reporte MB agua, " + e);
                System.err.println(e.getStackTrace()[0]);
            }
        }
    }    public void generarReporteEfluentesCloaca(int id, String procedencia) {
        Connection conexion = con.getConnection();
        Principal p = new Principal();
        try {
            JasperReport reporte = null;
            String ruta = getClass().getResource("/reporte/reporteEfluentesCloaca.jasper").getPath();
            reporte = (JasperReport) JRLoader.loadObjectFromFile(ruta);//se toma el archivo .jasper para generar el main.resources.reporte
            Map mapa = new HashMap();
            mapa.put("id", id); //se pasa el id al main.resources.reporte
            imagenesMap(mapa);
            JasperPrint imprimirReporte = JasperFillManager.fillReport(reporte, mapa, conexion);            //llena los campos del main.resources.reporte
            JasperViewer vistaReporte = new JasperViewer(imprimirReporte, false);                           //crea el visor del main.resources.reporte
            String[] nombre = obtenerProcedenciayNombreEmail(obtenerIdCliente(procedencia));
            String aux; //en estas tres lineas se sacan espacios de
            if (nombre[0].contains("/") || nombre[0].contains("" + org.ignaciorodriguez.utils.SeparatorUtils.s + "") || nombre[0].contains(":") || nombre[0].contains("*") || nombre[0].contains("?") || nombre[0].contains("\"") || nombre[0].contains("<") || nombre[0].contains(">") || nombre[0].contains(">") || nombre[1].contains("/") || nombre[1].contains("" + org.ignaciorodriguez.utils.SeparatorUtils.s + "") || nombre[1].contains(":") || nombre[1].contains("*") || nombre[1].contains("?") || nombre[1].contains("\"") || nombre[1].contains("<") || nombre[1].contains(">") || nombre[1].contains(">")) {
                nombre[0] = nombre[0].replaceAll("[/" + org.ignaciorodriguez.utils.SeparatorUtils.s + "" + org.ignaciorodriguez.utils.SeparatorUtils.s + ":*?\"<>|]", "_");
                nombre[1] = nombre[1].replaceAll("[/" + org.ignaciorodriguez.utils.SeparatorUtils.s + "" + org.ignaciorodriguez.utils.SeparatorUtils.s + ":*?\"<>|]", "_");
            }
            if (nombre[1].isBlank() || nombre[1] == null) {
                aux = "" + org.ignaciorodriguez.utils.SeparatorUtils.s + "Inf. " + id + " Efluentes cloaca " + nombre[0];
            } else {
                aux = "" + org.ignaciorodriguez.utils.SeparatorUtils.s + "Inf. " + id + " Efluentes cloaca " + nombre[1];
            }
            String aux2 = aux.replaceAll("^" + org.ignaciorodriguez.utils.SeparatorUtils.s + "s*", "");                                                       //principio, final y se sacan las comillas
            String aux3 = aux2.replaceAll("" + org.ignaciorodriguez.utils.SeparatorUtils.s + "s*$", "");
            String pdf = aux3.replaceAll("\"", "");
            pdf += ".pdf";                                                                                  //se agrega la extensión .pdf
            String rutaGuardado = recuperarRutas("Reportes");
            JasperExportManager.exportReportToPdfFile(imprimirReporte, rutaGuardado + pdf);
            vistaReporte.setDefaultCloseOperation(JasperViewer.DO_NOTHING_ON_CLOSE);
            vistaReporte.setExtendedState(vistaReporte.MAXIMIZED_BOTH);
            ImageIcon icon = new ImageIcon("src" + org.ignaciorodriguez.utils.SeparatorUtils.s + "vista" + org.ignaciorodriguez.utils.SeparatorUtils.s + "icono.png");
            vistaReporte.setIconImage(icon.getImage());
            vistaReporte.setVisible(true);
            vistaReporte.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    int email = JOptionPane.showConfirmDialog(null, "¿Enviar email?", "Email", JOptionPane.YES_NO_OPTION);
                    ;
                    if (JOptionPane.YES_OPTION == email) {
                        String[] nombre = obtenerProcedenciayNombreEmail(obtenerIdCliente(procedencia));
                        String aux; //en estas tres lineas se sacan espacios de
                        if (nombre[0].contains("/") || nombre[0].contains("" + org.ignaciorodriguez.utils.SeparatorUtils.s + "") || nombre[0].contains(":") || nombre[0].contains("*") || nombre[0].contains("?") || nombre[0].contains("\"") || nombre[0].contains("<") || nombre[0].contains(">") || nombre[0].contains(">") || nombre[1].contains("/") || nombre[1].contains("" + org.ignaciorodriguez.utils.SeparatorUtils.s + "") || nombre[1].contains(":") || nombre[1].contains("*") || nombre[1].contains("?") || nombre[1].contains("\"") || nombre[1].contains("<") || nombre[1].contains(">") || nombre[1].contains(">")) {
                            nombre[0] = nombre[0].replaceAll("[/" + org.ignaciorodriguez.utils.SeparatorUtils.s + "" + org.ignaciorodriguez.utils.SeparatorUtils.s + ":*?\"<>|]", "_");
                            nombre[1] = nombre[1].replaceAll("[/" + org.ignaciorodriguez.utils.SeparatorUtils.s + "" + org.ignaciorodriguez.utils.SeparatorUtils.s + ":*?\"<>|]", "_");
                        }
                        if (nombre[1].isBlank() || nombre[1] == null) {
                            aux = "" + org.ignaciorodriguez.utils.SeparatorUtils.s + "Inf. " + id + " Efluentes cloaca " + nombre[0];
                        } else {
                            aux = "" + org.ignaciorodriguez.utils.SeparatorUtils.s + "Inf. " + id + " Efluentes cloaca " + nombre[1];
                        }
                        String aux2 = aux.replaceAll("^" + org.ignaciorodriguez.utils.SeparatorUtils.s + "s*", "");                                                       //principio, final y se sacan las comillas
                        String aux3 = aux2.replaceAll("" + org.ignaciorodriguez.utils.SeparatorUtils.s + "s*$", "");
                        String pdf = aux3.replaceAll("\"", "");
                        pdf += ".pdf";
                        String pdfEmail = rutaGuardado + pdf;
                        VentanaEmail vEmail = new VentanaEmail(p, true, id, pdfEmail);
                        vEmail.setVisible(true);
                    }
                }
            });
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al generar informe, " + e);
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
    }

    public void generarReporteEfluentesInfiltracion(int id, String procedencia) {
        Connection conexion = con.getConnection();
        Principal p = new Principal();
        try {
            JasperReport reporte = null;
            String ruta = getClass().getResource("/reporte/reporteEfluentesInfiltracion.jasper").getPath();
            File archivo = new File(ruta);
            reporte = (JasperReport) JRLoader.loadObjectFromFile(ruta);//se toma el archivo .jasper para generar el main.resources.reporte
            Map mapa = new HashMap();
            mapa.put("id", id); //se pasa el id al main.resources.reporte
            imagenesMap(mapa);
            JasperPrint imprimirReporte = JasperFillManager.fillReport(reporte, mapa, conexion);            //llena los campos del main.resources.reporte
            JasperViewer vistaReporte = new JasperViewer(imprimirReporte, false);                           //crea el visor del main.resources.reporte
            String[] nombre = obtenerProcedenciayNombreEmail(obtenerIdCliente(procedencia));
            String aux; //en estas tres lineas se sacan espacios de
            if (nombre[0].contains("/") || nombre[0].contains("" + org.ignaciorodriguez.utils.SeparatorUtils.s + "") || nombre[0].contains(":") || nombre[0].contains("*") || nombre[0].contains("?") || nombre[0].contains("\"") || nombre[0].contains("<") || nombre[0].contains(">") || nombre[0].contains(">") || nombre[1].contains("/") || nombre[1].contains("" + org.ignaciorodriguez.utils.SeparatorUtils.s + "") || nombre[1].contains(":") || nombre[1].contains("*") || nombre[1].contains("?") || nombre[1].contains("\"") || nombre[1].contains("<") || nombre[1].contains(">") || nombre[1].contains(">")) {
                nombre[0] = nombre[0].replaceAll("[/" + org.ignaciorodriguez.utils.SeparatorUtils.s + "" + org.ignaciorodriguez.utils.SeparatorUtils.s + ":*?\"<>|]", "_");
                nombre[1] = nombre[1].replaceAll("[/" + org.ignaciorodriguez.utils.SeparatorUtils.s + "" + org.ignaciorodriguez.utils.SeparatorUtils.s + ":*?\"<>|]", "_");
            }
            if (nombre[1].isBlank() || nombre[1] == null) {
                aux = "" + org.ignaciorodriguez.utils.SeparatorUtils.s + "Inf. " + id + " Efluentes infiltración " + nombre[0];
            } else {
                aux = "" + org.ignaciorodriguez.utils.SeparatorUtils.s + "Inf. " + id + " Efluentes infiltración " + nombre[1];
            }
            String aux2 = aux.replaceAll("^" + org.ignaciorodriguez.utils.SeparatorUtils.s + "s*", "");                                                       //principio, final y se sacan las comillas
            String aux3 = aux2.replaceAll("" + org.ignaciorodriguez.utils.SeparatorUtils.s + "s*$", "");
            String pdf = aux3.replaceAll("\"", "");
            pdf += ".pdf";                                                                                  //se agrega la extensión .pdf
            String rutaGuardado = recuperarRutas("Reportes");
            JasperExportManager.exportReportToPdfFile(imprimirReporte, rutaGuardado + pdf);
            vistaReporte.setDefaultCloseOperation(JasperViewer.DO_NOTHING_ON_CLOSE);
            vistaReporte.setExtendedState(vistaReporte.MAXIMIZED_BOTH);
            ImageIcon icon = new ImageIcon("src" + org.ignaciorodriguez.utils.SeparatorUtils.s + "vista" + org.ignaciorodriguez.utils.SeparatorUtils.s + "icono.png");
            vistaReporte.setIconImage(icon.getImage());
            vistaReporte.setVisible(true);
            vistaReporte.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    int email = JOptionPane.showConfirmDialog(null, "¿Enviar email?", "Email", JOptionPane.YES_NO_OPTION);
                    ;
                    if (JOptionPane.YES_OPTION == email) {
                        String[] nombre = obtenerProcedenciayNombreEmail(obtenerIdCliente(procedencia));
                        String aux; //en estas tres lineas se sacan espacios de
                        if (nombre[0].contains("/") || nombre[0].contains("" + org.ignaciorodriguez.utils.SeparatorUtils.s + "") || nombre[0].contains(":") || nombre[0].contains("*") || nombre[0].contains("?") || nombre[0].contains("\"") || nombre[0].contains("<") || nombre[0].contains(">") || nombre[0].contains(">") || nombre[1].contains("/") || nombre[1].contains("" + org.ignaciorodriguez.utils.SeparatorUtils.s + "") || nombre[1].contains(":") || nombre[1].contains("*") || nombre[1].contains("?") || nombre[1].contains("\"") || nombre[1].contains("<") || nombre[1].contains(">") || nombre[1].contains(">")) {
                            nombre[0] = nombre[0].replaceAll("[/" + org.ignaciorodriguez.utils.SeparatorUtils.s + "" + org.ignaciorodriguez.utils.SeparatorUtils.s + ":*?\"<>|]", "_");
                            nombre[1] = nombre[1].replaceAll("[/" + org.ignaciorodriguez.utils.SeparatorUtils.s + "" + org.ignaciorodriguez.utils.SeparatorUtils.s + ":*?\"<>|]", "_");
                        }
                        if (nombre[1].isBlank() || nombre[1] == null) {
                            aux = "" + org.ignaciorodriguez.utils.SeparatorUtils.s + "Inf. " + id + " Efluentes infiltración " + nombre[0];
                        } else {
                            aux = "" + org.ignaciorodriguez.utils.SeparatorUtils.s + "Inf. " + id + " Efluentes infiltración " + nombre[1];
                        }
                        String aux2 = aux.replaceAll("^" + org.ignaciorodriguez.utils.SeparatorUtils.s + "s*", "");                                                       //principio, final y se sacan las comillas
                        String aux3 = aux2.replaceAll("" + org.ignaciorodriguez.utils.SeparatorUtils.s + "s*$", "");
                        String pdf = aux3.replaceAll("\"", "");
                        pdf += ".pdf";
                        String pdfEmail = rutaGuardado + pdf;
                        VentanaEmail vEmail = new VentanaEmail(p, true, id, pdfEmail);
                        vEmail.setVisible(true);
                    }
                }
            });
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al generar informe, " + e + " en línea " + e.getStackTrace()[0].getLineNumber());
            System.err.println(e.getStackTrace()[0].getLineNumber());
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
    }



    public String recuperarEmail(int id) {
        Connection conexion = con.getConnection();
        String aux;
        try {
            ps = conexion.prepareStatement("select email from vistaemail where idmuestras = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                aux = rs.getString(1);
                return aux;
            }
        } catch (NullPointerException e) {
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error recuperar email, " + e);
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error al recueperar email, " + e + " in line " + e.getStackTrace()[0].getLineNumber());
            }
        }
        return "";
    }





    public String[] recuperarDatosEmail(int id) {
        Connection conexion = con.getConnection();
        String[] aux = new String[4];
        try {
            ps = conexion.prepareStatement("select destinatario, remitente, cuerpo, archivo from emails where idemails = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                for (int i = 0; i < aux.length; i++) {
                    aux[i] = String.valueOf(rs.getObject(i + 1));
                }
            }
            return aux;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al recuperar solicitante, " + e);
            return null;
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
    }

    public DefaultTableModel recuperarEmailsEnviados() {
        Connection conexion = con.getConnection();
        DefaultTableModel emails = new DefaultTableModel();
        emails.addColumn("Hora");
        emails.addColumn("Procedencia");
        emails.addColumn("Destinatario");
        emails.addColumn("cuerpo");
        emails.addColumn("archivo");
        emails.addColumn("remitente");
        String[] aux = new String[6];
        try {
            ps = conexion.prepareStatement("select destinatario, hora, cuerpo, archivo, remitente, procedencia from emails order by idemails desc");
            rs = ps.executeQuery();
            while (rs.next()) {
                aux[0] = new SimpleDateFormat("dd/MM/yyyy   HH:mm").format(rs.getTimestamp("hora"));
                aux[1] = rs.getString("procedencia");
                aux[2] = rs.getString("destinatario").replaceAll(" ", "");
                aux[3] = rs.getString("cuerpo");
                aux[4] = rs.getString("archivo");
                aux[5] = rs.getString("remitente");
                emails.addRow(aux);
            }
            return emails;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al recuperar emails enviados, " + e);
            return null;
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
    }

    public void guardarEmailEnviado(String destinatario, String cuerpo, String archivo, String remitente, String procedencia) {
        Connection conexion = con.getConnection();
        try {
            ps = conexion.prepareStatement("insert into emails (destinatario, cuerpo, archivo, hora, remitente, procedencia) values (?,?,?,current_timestamp(),?,?)");
            ps.setString(1, destinatario);
            ps.setString(2, cuerpo);
            ps.setString(3, archivo);
            ps.setString(4, remitente);
            ps.setString(5, procedencia);
            ps.executeUpdate();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al guardar email enviado, " + e);
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
    }public void esconderFechaVencimiento(int id, boolean poner) {
        Connection conexion = con.getConnection();
        try {
            ps = conexion.prepareStatement("update muestras set ponerFechaVencimiento = ? where idmuestras = ?");
            int aux;
            if (poner) {
                aux = 1;
            } else {
                aux = 0;
            }
            ps.setInt(1, aux);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (Exception e) {
            JOptionPane.showInputDialog("Error al esconder fecha de vencimiento, " + e);
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
    }



        public Date recuperarEntrada(int id) {
        Connection conexion = con.getConnection();
        Date analisis = null;
        try {
            ps = conexion.prepareStatement("select entrada from administracion where idmuestras = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                analisis = rs.getDate("entrada");
            }
            return analisis;
        } catch (Exception e) {
            JOptionPane.showInputDialog("Error al recuperar titulo " + e);
            return null;
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
    }

    public void generarReporteEnProceso(int id, String texto) {
        Connection conexion = con.getConnection();
        Principal p = new Principal();
        String texto1 = texto;
        try {
            JasperReport reporte = null;
            String ruta = getClass().getResource("/reporte/reporteEnProceso.jasper").getPath();
            reporte = (JasperReport) JRLoader.loadObjectFromFile(ruta);//se toma el archivo .jasper para generar el main.resources.reporte
            Map mapa = new HashMap();
            mapa.put("texto", texto1);
            mapa.put("id", id);
            imagenesMap(mapa);
            JasperPrint imprimirReporte = JasperFillManager.fillReport(reporte, mapa, conexion);            //llena los campos del main.resources.reporte
            JasperViewer vistaReporte = new JasperViewer(imprimirReporte, false);                           //crea el visor del main.resources.reporte
            String aux = "" + org.ignaciorodriguez.utils.SeparatorUtils.s + "Informe en proceso " + id; //en estas tres lineas se sacan espacios de
            String aux2 = aux.replaceAll("^" + org.ignaciorodriguez.utils.SeparatorUtils.s + "s*", "");                                                       //principio, final y se sacan las comillas
            String aux3 = aux2.replaceAll("" + org.ignaciorodriguez.utils.SeparatorUtils.s + "s*$", "");
            String pdf = aux3.replaceAll("\"", "");
            pdf += ".pdf";                                                                                  //se agrega la extensión .pdf
            String rutaGuardado = recuperarRutas("Reportes");
            var pdfEmail = rutaGuardado + pdf;
            JasperExportManager.exportReportToPdfFile(imprimirReporte, rutaGuardado + pdf);
            vistaReporte.setDefaultCloseOperation(JasperViewer.DO_NOTHING_ON_CLOSE);
            vistaReporte.setExtendedState(vistaReporte.MAXIMIZED_BOTH);
            ImageIcon icon = new ImageIcon("src" + org.ignaciorodriguez.utils.SeparatorUtils.s + "vista" + org.ignaciorodriguez.utils.SeparatorUtils.s + "icono.png");
            vistaReporte.setIconImage(icon.getImage());
            vistaReporte.setVisible(true);
            vistaReporte.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    int email = JOptionPane.showConfirmDialog(null, "¿Enviar email?", "Email", JOptionPane.YES_NO_OPTION);
                    ;
                    if (JOptionPane.YES_OPTION == email) {
                        VentanaEmail vEmail = new VentanaEmail(p, true, id, pdfEmail);
                        vEmail.setVisible(true);
                    }
                }
            });
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al generar main.resources.reporte MB agua, " + e);
            System.err.println(e.getStackTrace()[0]);
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error al generar main.resources.reporte MB agua, " + e);
                System.err.println(e.getStackTrace()[0]);
            }
        }
    }

    public boolean cancelarEntrega(int id) {
        Connection conexion = con.getConnection();
        try {
            ps = conexion.prepareStatement("delete from entregas where idmuestras = ?");
            ps.setInt(1, id);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al cancelar la entrega, " + e);
            return false;
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
    }

    public boolean cancelarEntrega2(int id) {
        Connection conexion = con.getConnection();
        try {
            ps = conexion.prepareStatement("update administracion set entregado = ? where idmuestras = ?");
            ps.setInt(1, 0);
            ps.setInt(2, id);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al cancelar la entrega, " + e);
            return false;
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
    }

    public ArrayList<Object> autocompletarEntregas() { //método para autocompletar el campo de busqueda en la pantalla principal
        ArrayList<Object> arreglo = new ArrayList<>();
        Connection conexion = con.getConnection();
        try {
            ps = conexion.prepareStatement("select distinct procedencia from vistaEntregas");
            rs = ps.executeQuery();
            while (rs.next()) {
                arreglo.add(rs.getString("procedencia"));
            }
            return arreglo;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error, " + e);
            return null;
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
    }

    public String[] recuperarEmailYVencimiento(int id) {
        Connection conexion = con.getConnection();
        String[] aux = new String[6];
        try {
            ps = conexion.prepareStatement("select e.email, v.fechaVencimiento, " + "v.idmuestras, m.tipo, m.realizadoPor, p.procedencia  from " + "vistaemail e join vencimientos v on e.idmuestras = v.idmuestras " + "join muestras m on v.idmuestras = m.idmuestras join vistaprocedencia " + "p on p.idcliente = m.idcliente where v.idmuestras = ?;");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                for (int i = 0; i < aux.length; i++) {
                    aux[i] = rs.getObject(i + 1).toString();
                }
                return aux;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error, " + e);
            return null;
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
        return null;
    }public void generarReporteFQCompleto(int id, String titulo, List<Determinacion> resultados) {
        Connection conexion = con.getConnection();
        Principal pr = new Principal();
        resultados.forEach(System.out::println);
        try {
            JasperReport reporte = null;
            String ruta = getClass().getResource("/reporte/reporteFQCompleto.jasper").getPath();
            System.out.println("ruta = " + ruta);
            reporte = (JasperReport) JRLoader.loadObjectFromFile(ruta);//se toma el archivo .jasper para generar el main.resources.reporte
            Map mapa = new HashMap();
            mapa.put("id", id); //se pasa el id al main.resources.reporte
            for (int i = 0; i < resultados.size(); i++) {
                String p = String.valueOf((i + 1));
                String sp = p + "b";
                String spc = p + "c";
                mapa.put(sp, resultados.get(i).getResultado());
                mapa.put(p, resultados.get(i).getNombre().toUpperCase());
                mapa.put(spc, resultados.get(i).getMetodo());
            }
            mapa.put("titulo", titulo);
            imagenesMap(mapa);
            String nombrePdf = "";
            if (titulo.contains("AGUA")) {
                nombrePdf = " FQ agua ";
            } else {
                nombrePdf = " FQ Alimentos ";
            }
            final String nombreemail = nombrePdf;
            JasperPrint imprimirReporte = JasperFillManager.fillReport(reporte, mapa, conexion);            //llena los campos del main.resources.reporte
            JasperViewer vistaReporte = new JasperViewer(imprimirReporte, false);                           //crea el visor del main.resources.reporte
            String[] nombre = obtenerProcedenciayNombreEmail(obtenerIdCliente(obtenerProcedencia(id)));
            String aux; //en estas tres lineas se sacan espacios de
            if (nombre[0].contains("/") || nombre[0].contains("" + org.ignaciorodriguez.utils.SeparatorUtils.s + "") || nombre[0].contains(":") || nombre[0].contains("*") || nombre[0].contains("?") || nombre[0].contains("\"") || nombre[0].contains("<") || nombre[0].contains(">") || nombre[0].contains(">") || nombre[1].contains("/") || nombre[1].contains("" + org.ignaciorodriguez.utils.SeparatorUtils.s + "") || nombre[1].contains(":") || nombre[1].contains("*") || nombre[1].contains("?") || nombre[1].contains("\"") || nombre[1].contains("<") || nombre[1].contains(">") || nombre[1].contains(">")) {
                nombre[0] = nombre[0].replaceAll("[/" + org.ignaciorodriguez.utils.SeparatorUtils.s + "" + org.ignaciorodriguez.utils.SeparatorUtils.s + ":*?\"<>|]", "_");
                nombre[1] = nombre[1].replaceAll("[/" + org.ignaciorodriguez.utils.SeparatorUtils.s + "" + org.ignaciorodriguez.utils.SeparatorUtils.s + ":*?\"<>|]", "_");
            }
            if (nombre[1].isBlank() || nombre[1] == null) {
                aux = "" + org.ignaciorodriguez.utils.SeparatorUtils.s + "Inf. " + id + " " + nombreemail + " " + nombre[0];
            } else {
                aux = "" + org.ignaciorodriguez.utils.SeparatorUtils.s + "Inf. " + id + " " + nombreemail + " " + nombre[1];
            }
            String aux2 = aux.replaceAll("^" + org.ignaciorodriguez.utils.SeparatorUtils.s + "s*", "");                                                       //principio, final y se sacan las comillas
            String aux3 = aux2.replaceAll("" + org.ignaciorodriguez.utils.SeparatorUtils.s + "s*$", "");
            String pdf = aux3.replaceAll("\"", "");
            pdf += ".pdf";
            String rutaGuardado = recuperarRutas("Reportes");
            JasperExportManager.exportReportToPdfFile(imprimirReporte, rutaGuardado + pdf);
            vistaReporte.setDefaultCloseOperation(JasperViewer.DO_NOTHING_ON_CLOSE);
            vistaReporte.setExtendedState(vistaReporte.MAXIMIZED_BOTH);
            ImageIcon icon = new ImageIcon("src" + org.ignaciorodriguez.utils.SeparatorUtils.s + "vista" + org.ignaciorodriguez.utils.SeparatorUtils.s + "icono.png");
            vistaReporte.setIconImage(icon.getImage());
            vistaReporte.setVisible(true);
            vistaReporte.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    int email = JOptionPane.showConfirmDialog(null, "¿Enviar email?", "Email", JOptionPane.YES_NO_OPTION);
                    ;
                    if (JOptionPane.YES_OPTION == email) {
                        String[] nombre = obtenerProcedenciayNombreEmail(obtenerIdCliente(obtenerProcedencia(id)));
                        String aux; //en estas tres lineas se sacan espacios de
                        if (nombre[0].contains("/") || nombre[0].contains("" + org.ignaciorodriguez.utils.SeparatorUtils.s + "") || nombre[0].contains(":") || nombre[0].contains("*") || nombre[0].contains("?") || nombre[0].contains("\"") || nombre[0].contains("<") || nombre[0].contains(">") || nombre[0].contains(">") || nombre[1].contains("/") || nombre[1].contains("" + org.ignaciorodriguez.utils.SeparatorUtils.s + "") || nombre[1].contains(":") || nombre[1].contains("*") || nombre[1].contains("?") || nombre[1].contains("\"") || nombre[1].contains("<") || nombre[1].contains(">") || nombre[1].contains(">")) {
                            nombre[0] = nombre[0].replaceAll("[/" + org.ignaciorodriguez.utils.SeparatorUtils.s + "" + org.ignaciorodriguez.utils.SeparatorUtils.s + ":*?\"<>|]", "_");
                            nombre[1] = nombre[1].replaceAll("[/" + org.ignaciorodriguez.utils.SeparatorUtils.s + "" + org.ignaciorodriguez.utils.SeparatorUtils.s + ":*?\"<>|]", "_");
                        }
                        if (nombre[1].isBlank() || nombre[1] == null) {
                            aux = "" + org.ignaciorodriguez.utils.SeparatorUtils.s + "Inf. " + id + " " + nombreemail + " " + nombre[0];
                        } else {
                            aux = "" + org.ignaciorodriguez.utils.SeparatorUtils.s + "Inf. " + id + " " + nombreemail + " " + nombre[1];
                        }
                        String aux2 = aux.replaceAll("^" + org.ignaciorodriguez.utils.SeparatorUtils.s + "s*", "");                                                       //principio, final y se sacan las comillas
                        String aux3 = aux2.replaceAll("" + org.ignaciorodriguez.utils.SeparatorUtils.s + "s*$", "");
                        String pdf = aux3.replaceAll("\"", "");
                        pdf += ".pdf";
                        String pdfEmail = rutaGuardado + pdf;
                        VentanaEmail vEmail = new VentanaEmail(pr, true, id, pdfEmail);
                        vEmail.setVisible(true);
                    }
                }
            });
        } catch (Exception e) {
            System.err.println("ERROR: " + e);
            JOptionPane.showMessageDialog(null, "Error al generar informe fq, " + e);
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
    }public boolean hayEntrega(int id) {
        Connection conexion = con.getConnection();

        try {
            ps = conexion.prepareStatement("select * from entregas where idmuestras = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                return true;
            }
            return false;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al guardar datos, " + e);
            return false;
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
    }public int consultarActualizacion() {
        String aux = "";
        try {
            URL url = new URL("http://138.36.236.245/actualizar.php");
            String agent = "Applet";
            String query = "query=";
            String type = "application/x-www-form-urlencoded";
            HttpURLConnection conexion = null;
            conexion = (HttpURLConnection) url.openConnection();
            conexion.setConnectTimeout(3000);
            conexion.setDoInput(true);
            conexion.setDoOutput(true);
            conexion.setRequestMethod("POST");
            conexion.setRequestProperty("User-Agent", agent);
            conexion.setRequestProperty("Content-Type", type);
            conexion.setRequestProperty("Content-Length", "" + query.length());

            OutputStream out;
            try {
                out = conexion.getOutputStream();
            } catch (SocketTimeoutException ex) {
                return ERROR_ACTUALIZAR;
            } catch (ConnectException ex) {
                return ERROR_ACTUALIZAR;
            }
            out.write(query.getBytes());
            BufferedReader in = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
            aux = in.readLine();
            in.close();
            return aux.equals(Main.VERSION) ? NO_ACTUALIZAR : ACTUALIZAR;
        } catch (MalformedURLException ex) {
            Logger.getLogger(Consultas.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Consultas.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -25962;
    }

    public boolean guardarDeterminacionesAHacerFQAlimentosAgua(String[] listaDb, int id, boolean update) {
        Connection conexion = con.getConnection();
        if (update) {
            String aux = "update determinaciones set ";
            for (int i = 0; i < listaDb.length; i++) {
                aux += listaDb[i] + " = ?, ";
            }
            aux = aux.substring(0, aux.length() - 2) + " where idmuestras = ?";
            try {
                ps = conexion.prepareStatement(aux);
                for (int i = 0; i < listaDb.length; i++) {
                    ps.setString(i + 1, "");
                }
                ps.setInt(listaDb.length + 1, id);
            } catch (SQLException ex) {
                Logger.getLogger(Consultas.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            String aux = "insert into determinaciones (";
            for (int i = 0; i < listaDb.length; i++) {
                aux += listaDb[i] + ", ";
            }
            aux += "idmuestras) values (";
            for (int i = 0; i < listaDb.length; i++) {
                aux += "?, ";
            }
            aux += "?)";
            try {
                ps = conexion.prepareStatement(aux);
                for (int i = 0; i < listaDb.length; i++) {
                    ps.setString(i + 1, "");
                }
                ps.setInt(listaDb.length + 1, id);
            } catch (SQLException ex) {
                Logger.getLogger(Consultas.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        try {
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al guardar datos, " + e);
            return false;
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
    }

    public boolean guardarDeterminacionesAHacer(String[] listaDb, int id, boolean update, String db) {
        Connection conexion = con.getConnection();
        if (update) {
            String aux = "update" + db + " set ";
            for (int i = 0; i < listaDb.length; i++) {
                aux += listaDb[i] + " = ?, ";
            }
            aux = aux.substring(0, aux.length() - 2) + " where idmuestras = ?";
            try {
                ps = conexion.prepareStatement(aux);
                for (int i = 0; i < listaDb.length; i++) {
                    ps.setString(i + 1, "");
                }
                ps.setInt(listaDb.length + 1, id);
            } catch (SQLException ex) {
                Logger.getLogger(Consultas.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            String aux = "insert into determinaciones (";
            for (int i = 0; i < listaDb.length; i++) {
                aux += listaDb[i] + ", ";
            }
            aux += "idmuestras) values (";
            for (int i = 0; i < listaDb.length; i++) {
                aux += "?, ";
            }
            aux += "?)";
            try {
                ps = conexion.prepareStatement(aux);
                for (int i = 0; i < listaDb.length; i++) {
                    ps.setString(i + 1, "");
                }
                ps.setInt(listaDb.length + 1, id);
            } catch (SQLException ex) {
                Logger.getLogger(Consultas.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        try {
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al guardar datos, " + e);
            return false;
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
    }    public void guardarArregloAHacer(int id, String query) {
        Connection conexion = con.getConnection();

        try {
            ps = conexion.prepareStatement("update muestras set aHacer = ? where idmuestras= ?");
            ps.setString(1, query);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al guardar datos, " + e);
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
    }

    public DefaultTableModel obtenerGanancias(Date desde, Date hasta) {//se obtienen los datos para la tabla de vencimientos
        Connection conexion = con.getConnection();
        double total = 0;
        Object fila[] = new Object[2];
        modeloGanancias.setColumnCount(0);
        DecimalFormat formato = new DecimalFormat("#.##");
        if (modeloGanancias.getColumnCount() == 0) {
            modeloGanancias.addColumn("Fecha");
            modeloGanancias.addColumn("Ganancias");
        }
        modeloGanancias.setRowCount(0);
        java.sql.Date desdeSql = new java.sql.Date(desde.getTime());
        java.sql.Date hastaSql = new java.sql.Date(hasta.getTime());
        try {
            ps = conexion.prepareStatement("select sum(precioTotal) as ganancias, date(entrada) as fecha from administracion where date(entrada) between ? and ? group by fecha;"); // se recuperan los datos de la base de datos de los vencimeintos de todo el mes
            ps.setDate(1, desdeSql);
            ps.setDate(2, hastaSql);
            System.out.println(ps.toString());
            rs = ps.executeQuery();
            while (rs.next()) {
                fila[0] = rs.getDate("fecha");
                fila[1] = formato.format(rs.getDouble("ganancias"));
                total += Double.parseDouble(fila[1].toString());
                modeloGanancias.addRow(fila);
            }
            fila[0] = "Total:";
            fila[1] = "$ " + formato.format(total);
            modeloGanancias.addRow(fila);
            return modeloGanancias;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al obtener ganancias, " + e);
            System.err.println(e.getStackTrace().toString());
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
        return null;
    }

    public void guardarPrecio(String determinacion, double precio) {
        Connection conexion = con.getConnection();
        try {
            ps = conexion.prepareStatement("update precios set precio = ? where determinacion = ?");
            ps.setDouble(1, precio);
            ps.setString(2, determinacion);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al guardar datos, " + e);
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
    }

    public Map<Integer, DTOPrecio> consultarPrecios(int tipo) {
        Connection conexion = con.getConnection();
        Map<Integer, DTOPrecio> map = new HashMap<>();
        try {
            ps = conexion.prepareStatement("select p.determinacion, p.precio, p.idprecios from precios p join tipo t on p.idprecios = t.determinacion where t.tipo = ?");
            ps.setInt(1, tipo);
            rs = ps.executeQuery();
            while (rs.next()) {
                map.put(rs.getInt("idprecios"), new DTOPrecio(rs.getString("determinacion"), rs.getDouble("precio"), rs.getInt("idprecios")));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al guardar datos, " + e);
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
        return map;
    }

}
