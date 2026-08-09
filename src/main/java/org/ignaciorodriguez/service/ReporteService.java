package org.ignaciorodriguez.service;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.checkerframework.checker.units.qual.A;
import org.ignaciorodriguez.modelo.Conexion;
import org.ignaciorodriguez.modelo.Determinacion;
import org.ignaciorodriguez.repository.ClienteRepository;
import org.ignaciorodriguez.vista.VentanaEmail;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.util.*;
import java.util.List;

public class ReporteService {

    private final Conexion con;
    private final ClienteRepository clienteRepository;
    private final ArchivoService archivoService = new ArchivoService();

    public ReporteService(Conexion con) {
        this.con = con;
        clienteRepository = new ClienteRepository(con);
    }

    public void generarReporte(String archivoJasper, int id, String prefijoNombre, String procedencia){
        try {
            JasperReport reporte = null;
            String ruta = Objects.requireNonNull(getClass().getResource("/reporte/" + archivoJasper)).getPath();
            reporte = (JasperReport) JRLoader.loadObjectFromFile(ruta);//se toma el archivo .jasper para generar el main.resources.reporte
            Map<String, Object> mapa = new HashMap<>();
            mapa.put("id", id); //se pasa el id al main.resources.reporte
            mapa.put("laboratorio", getClass().getResourceAsStream("/imagenes/laboratorio.png"));
            mapa.put("firma", getClass().getResourceAsStream("/imagenes/firma.png"));
            JasperPrint imprimirReporte = JasperFillManager.fillReport(reporte, mapa, new Conexion().getConnection());            //llena los campos del main.resources.reporte
            JasperViewer vistaReporte = new JasperViewer(imprimirReporte, false);                           //crea el visor del main.resources.reporte
            String[] nombre = clienteRepository.obtenerProcedenciayNombreEmail(clienteRepository.obtenerIdCliente(procedencia));
            String nombreBase; //en estas tres lineas se sacan espacios de
            if (nombre[0].contains("/") || nombre[0].contains("" + org.ignaciorodriguez.utils.SeparatorUtils.s + "") || nombre[0].contains(":") || nombre[0].contains("*") || nombre[0].contains("?") || nombre[0].contains("\"") || nombre[0].contains("<") || nombre[0].contains(">") || nombre[0].contains(">") || nombre[1].contains("/") || nombre[1].contains("" + org.ignaciorodriguez.utils.SeparatorUtils.s + "") || nombre[1].contains(":") || nombre[1].contains("*") || nombre[1].contains("?") || nombre[1].contains("\"") || nombre[1].contains("<") || nombre[1].contains(">") || nombre[1].contains(">")) {
                nombre[0] = nombre[0].replaceAll("[/" + org.ignaciorodriguez.utils.SeparatorUtils.s + "" + org.ignaciorodriguez.utils.SeparatorUtils.s + ":*?\"<>|]", "_");
                nombre[1] = nombre[1].replaceAll("[/" + org.ignaciorodriguez.utils.SeparatorUtils.s + "" + org.ignaciorodriguez.utils.SeparatorUtils.s + ":*?\"<>|]", "_");
            }
            if (nombre[1].isBlank()) {
                nombreBase = "" + org.ignaciorodriguez.utils.SeparatorUtils.s + "Inf. " + id + " " + prefijoNombre + " " + nombre[0];
            } else {
                nombreBase = "" + org.ignaciorodriguez.utils.SeparatorUtils.s + "Inf. " + id + " " + prefijoNombre + " " + nombre[1];
            }
            String nombrePdfSinEspaciosInicio = nombreBase.replaceAll("^" + org.ignaciorodriguez.utils.SeparatorUtils.s + "s*", "");                                                       //principio, final y se sacan las comillas
            String nombrePdfSinEspacios = nombrePdfSinEspaciosInicio.replaceAll("" + org.ignaciorodriguez.utils.SeparatorUtils.s + "s*$", "");
            String nombrePdf = nombrePdfSinEspacios.replace("\"", "");
            nombrePdf += ".pdf";                                                                                  //se agrega la extensión .pdf
            String rutaGuardado = archivoService.recuperarRutas("Reportes");
            final String pdfEmail = rutaGuardado + nombrePdf;
            JasperExportManager.exportReportToPdfFile(imprimirReporte, pdfEmail);
            vistaReporte.setDefaultCloseOperation(JasperViewer.DO_NOTHING_ON_CLOSE);
            vistaReporte.setExtendedState(Frame.MAXIMIZED_BOTH);
            ImageIcon icon = new ImageIcon("src" + org.ignaciorodriguez.utils.SeparatorUtils.s + "vista" + org.ignaciorodriguez.utils.SeparatorUtils.s + "icono.png");
            vistaReporte.setIconImage(icon.getImage());
            vistaReporte.setVisible(true);
            // TODO: migrar a vista
            vistaReporte.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    int email = JOptionPane.showConfirmDialog(null, "¿Enviar email?", "Email", JOptionPane.YES_NO_OPTION);
                    ;
                    if (JOptionPane.YES_OPTION == email) {
                        VentanaEmail vEmail = new VentanaEmail(null, true, id, pdfEmail);
                        vEmail.setVisible(true);
                    }
                }
            });
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al generar main.resources.reporte MB agua, " + e);
            System.err.println(e.getStackTrace()[0]);
        }
    }

    public void generarReporteFQCompleto(String archivoJasper, int id, String prefijoNombre,
                                         String procedencia, String titulo,
                                         List<Determinacion> resultados) {
        try {
            JasperReport reporte = (JasperReport) JRLoader.loadObjectFromFile(
                    Objects.requireNonNull(getClass().getResource("/reporte/" + archivoJasper)).getPath());

            Map<String, Object> mapa = new HashMap<>();
            mapa.put("id", id);
            mapa.put("titulo", titulo);
            mapa.put("laboratorio", getClass().getResourceAsStream("/imagenes/laboratorio.png"));
            mapa.put("firma", getClass().getResourceAsStream("/imagenes/firma.png"));
            for (int i = 0; i < resultados.size(); i++) {
                String p = String.valueOf(i + 1);
                mapa.put(p, resultados.get(i).getNombre().toUpperCase());
                mapa.put(p + "b", resultados.get(i).getResultado());
                mapa.put(p + "c", resultados.get(i).getMetodo());
            }

            String nombrePdfTipo = titulo.contains("AGUA") ? " FQ agua " : " FQ Alimentos ";

            JasperPrint imprimirReporte = JasperFillManager.fillReport(reporte, mapa, con.getConnection());
            JasperViewer vistaReporte = new JasperViewer(imprimirReporte, false);

            String[] nombre = clienteRepository.obtenerProcedenciayNombreEmail(
                    clienteRepository.obtenerIdCliente(procedencia));
            if (nombre[0].contains("/") || nombre[0].contains("" + org.ignaciorodriguez.utils.SeparatorUtils.s + "") || nombre[0].contains(":") || nombre[0].contains("*") || nombre[0].contains("?") || nombre[0].contains("\"") || nombre[0].contains("<") || nombre[0].contains(">") || nombre[1].contains("/") || nombre[1].contains("" + org.ignaciorodriguez.utils.SeparatorUtils.s + "") || nombre[1].contains(":") || nombre[1].contains("*") || nombre[1].contains("?") || nombre[1].contains("\"") || nombre[1].contains("<") || nombre[1].contains(">")) {
                nombre[0] = nombre[0].replaceAll("[/" + org.ignaciorodriguez.utils.SeparatorUtils.s + "" + org.ignaciorodriguez.utils.SeparatorUtils.s + ":*?\"<>|]", "_");
                nombre[1] = nombre[1].replaceAll("[/" + org.ignaciorodriguez.utils.SeparatorUtils.s + "" + org.ignaciorodriguez.utils.SeparatorUtils.s + ":*?\"<>|]", "_");
            }

            String nombreBase = org.ignaciorodriguez.utils.SeparatorUtils.s + "Inf. " + id + nombrePdfTipo +
                    (nombre[1] == null || nombre[1].isBlank() ? nombre[0] : nombre[1]);
            String nombreSinEspaciosIinicio = nombreBase.replaceAll("^" + org.ignaciorodriguez.utils.SeparatorUtils.s + "s*", "");
            String nombreSinEspacios = nombreSinEspaciosIinicio.replaceAll("" + org.ignaciorodriguez.utils.SeparatorUtils.s + "s*$", "");
            String nombrePdf = nombreSinEspacios.replace("\"", "") + ".pdf";

            String rutaGuardado = archivoService.recuperarRutas("Reportes");
            final String pdfEmail = rutaGuardado + nombrePdf;

            JasperExportManager.exportReportToPdfFile(imprimirReporte, pdfEmail);

            vistaReporte.setDefaultCloseOperation(JasperViewer.DO_NOTHING_ON_CLOSE);
            vistaReporte.setExtendedState(Frame.MAXIMIZED_BOTH);
            vistaReporte.setIconImage(new ImageIcon("src" + org.ignaciorodriguez.utils.SeparatorUtils.s +
                    "vista" + org.ignaciorodriguez.utils.SeparatorUtils.s +
                    "icono.png").getImage());
            vistaReporte.setVisible(true);

            // TODO: migrar a vista
            vistaReporte.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    int email = JOptionPane.showConfirmDialog(null, "¿Enviar email?", "Email", JOptionPane.YES_NO_OPTION);
                    if (JOptionPane.YES_OPTION == email) {
                        new VentanaEmail(null, true, id, pdfEmail).setVisible(true);
                    }
                }
            });

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al generar informe FQ completo, " + e);
        }
    }

    public void generarReporteMuestras(List<Integer> ids) {
        try {
            JasperReport reporte = (JasperReport) JRLoader.loadObjectFromFile(
                    Objects.requireNonNull(getClass().getResource("/reporte/informeMuestras.jasper")).getPath());

            Map<String, Object> mapa = new HashMap<>();
            mapa.put("id", ids);

            JasperPrint imprimirReporte = JasperFillManager.fillReport(reporte, mapa, con.getConnection());
            JasperViewer vistaReporte = new JasperViewer(imprimirReporte, false);

            vistaReporte.setDefaultCloseOperation(JasperViewer.DO_NOTHING_ON_CLOSE);
            vistaReporte.setExtendedState(Frame.MAXIMIZED_BOTH);
            vistaReporte.setIconImage(new ImageIcon("src" + org.ignaciorodriguez.utils.SeparatorUtils.s +
                    "vista" + org.ignaciorodriguez.utils.SeparatorUtils.s +
                    "icono.png").getImage());
            vistaReporte.setVisible(true);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al generar informe de muestras, " + e);
        }
    }

    public boolean generarReporteVencimientos(Date desde, Date hasta) {
        try {
            JasperReport reporte = (JasperReport) JRLoader.loadObjectFromFile(
                    Objects.requireNonNull(getClass().getResource("/reporte/informeVencimientos.jasper")).getPath());

            Map<String, Object> mapa = new HashMap<>();
            mapa.put("desde", new java.sql.Date(desde.getTime()));
            mapa.put("hasta", new java.sql.Date(hasta.getTime()));

            JasperPrint imprimirReporte = JasperFillManager.fillReport(reporte, mapa, con.getConnection());
            JasperViewer vistaReporte = new JasperViewer(imprimirReporte, false);

            vistaReporte.setDefaultCloseOperation(JasperViewer.DO_NOTHING_ON_CLOSE);
            vistaReporte.setExtendedState(Frame.MAXIMIZED_BOTH);
            vistaReporte.setIconImage(new ImageIcon("src" + org.ignaciorodriguez.utils.SeparatorUtils.s +
                    "vista" + org.ignaciorodriguez.utils.SeparatorUtils.s +
                    "icono.png").getImage());
            vistaReporte.setVisible(true);
            return true;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al generar informe de vencimientos, " + e);
            return false;
        }
    }

    public void generarReporteEnProceso(int id, String texto) {
        try {
            JasperReport reporte = (JasperReport) JRLoader.loadObjectFromFile(
                    Objects.requireNonNull(getClass().getResource("/reporte/reporteEnProceso.jasper")).getPath());

            Map<String, Object> mapa = new HashMap<>();
            mapa.put("texto", texto);
            mapa.put("id", id);
            mapa.put("laboratorio", getClass().getResourceAsStream("/imagenes/laboratorio.png"));
            mapa.put("firma", getClass().getResourceAsStream("/imagenes/firma.png"));

            JasperPrint imprimirReporte = JasperFillManager.fillReport(reporte, mapa, con.getConnection());
            JasperViewer vistaReporte = new JasperViewer(imprimirReporte, false);

            String nombreBase = org.ignaciorodriguez.utils.SeparatorUtils.s + "Informe en proceso " + id;
            String nombreSinEspaciosInicio = nombreBase.replaceAll("^" + org.ignaciorodriguez.utils.SeparatorUtils.s + "s*", "");
            String nombreSinEspacios = nombreSinEspaciosInicio.replaceAll("" + org.ignaciorodriguez.utils.SeparatorUtils.s + "s*$", "");
            String nombrePdf = nombreSinEspacios.replace("\"", "") + ".pdf";

            String rutaGuardado = archivoService.recuperarRutas("Reportes");
            final String pdfEmail = rutaGuardado + nombrePdf;

            JasperExportManager.exportReportToPdfFile(imprimirReporte, pdfEmail);

            vistaReporte.setDefaultCloseOperation(JasperViewer.DO_NOTHING_ON_CLOSE);
            vistaReporte.setExtendedState(Frame.MAXIMIZED_BOTH);
            vistaReporte.setIconImage(new ImageIcon("src" + org.ignaciorodriguez.utils.SeparatorUtils.s +
                    "vista" + org.ignaciorodriguez.utils.SeparatorUtils.s +
                    "icono.png").getImage());
            vistaReporte.setVisible(true);

            // TODO: migrar a vista
            vistaReporte.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    int email = JOptionPane.showConfirmDialog(null, "¿Enviar email?", "Email", JOptionPane.YES_NO_OPTION);
                    if (JOptionPane.YES_OPTION == email) {
                        new VentanaEmail(null, true, id, pdfEmail).setVisible(true);
                    }
                }
            });

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al generar reporte en proceso, " + e);
        }
    }
}
