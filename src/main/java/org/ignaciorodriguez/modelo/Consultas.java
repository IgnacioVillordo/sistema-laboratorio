package org.ignaciorodriguez.modelo;

import com.mysql.cj.util.StringUtils;
import org.apache.poi.xddf.usermodel.chart.*;
import org.ignaciorodriguez.vista.Inicial;
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
    String vencimientoMb = "INSERT INTO `laboratorio`.`vencimientos` "
            + "(`fechaVencimiento`,idmuestras) VALUES (date_add(?,interval 6 month),?)";
    String vencimientoFq = "INSERT INTO `laboratorio`.`vencimientos` "
            + "(`fechaVencimiento`,idmuestras) VALUES (date_add(?,interval 12 month),?)";
    String recuperarFQ = "select acidez, acidosorbico, alcalinidad, alcohol, aluminio, amonio, amoniacos, "
            + "antimonio, aroma, arsenico, asbesto, aspecto, azucares, azucaresReductores, azucaresInvertidos, "
            + "bario, bicarbonatos, boro, cadmiototal, calcio, caracteristicas, carbonatos, cenizas, "
            + "cenizasInsolublesAcido, cenizasInsolublesAgua, cianuros, cloroactivo, clororesidual, clorototal, "
            + "cloruros, cobalto, cobre, colesterol, color, colorantesartificiales, colorantesnaturales, colorantes, "
            + "conductividad, cromohexavalente, "
            + "dbo, detergentes, dqo, dureza, estano, extracto, fenoles, fluoruros, fluor, fosforototal, "
            + "gliadinas, gluten, gradofermentacion, gradosbrix, grasa, hidrocarburos, hierro, humedad, "
            + "magnesio, manganeso, materiagrasa, mercuriototal, molibdeno, nitratos, nitritos, nitrogenoamoniacal, "
            + "nitrogenototal, niquel , observacionmicroscopica, olor, organoclorados, oxigenodisuelto, "
            + "ph, plata, plomo, porcentajecloruro, potasio, propionato, relacion, residuo105, "
            + "residuo180, residuoseco, sabor, selenio, silicatos, sodio, solidostotales, solidosnograsos, "
            + "solidos2horas, solidos10minutos, solidossuspendidostotales, solidossuspendidosvolatiles, "
            + "sulfatos, sulfuros, sustancias, sustanciaseteretilico, turbidez, vanadio, zinc from determinaciones where idmuestras = ?"; //cambiar al agregar
    DefaultTableModel modeloTablaProcedencia = new DefaultTableModel();
    DefaultTableModel modeloTabla = new DefaultTableModel();
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

    public boolean insertarUsuario(Usuario usuario) { //método para insertar usuario en la base de datos, se insertan con un rol de empleado
        Connection conexion = con.getConnection();

        try {
            ps = conexion.prepareStatement("insert into usuarios (nombre,contrasena,tipoUsuario) values (?,?,2)");
            ps.setString(1, usuario.getUsuario());
            ps.setString(2, usuario.getContraseña());

            int resultado = ps.executeUpdate();

            if (resultado > 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al insertar usuario, " + ex);
            return false;
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error al insertar usuario, " + e);
            }
        }
    }

    public DefaultComboBoxModel recuperarUsuarios() { //se traen los usuarios de la base de datos
        Connection conexion = con.getConnection();
        DefaultComboBoxModel modelo = new DefaultComboBoxModel();
        modelo.removeAllElements();
        try {
            ps = conexion.prepareStatement("select nombre from usuarios");
            rs = ps.executeQuery();
            while (rs.next()) {
                modelo.addElement(rs.getString("nombre"));
            }
            return modelo;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al insertar usuario, " + e);
            return null;
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error al recuperar usuarios, " + e);
            }
        }
    }

    public boolean eliminarUsuario(Usuario usuario) { //se elimina el usuario seleccionado de la base de datos
        Connection conexion = con.getConnection();
        try {
            ps = conexion.prepareStatement("DELETE FROM `laboratorio`.`usuarios` WHERE nombre = ?");
            ps.setString(1, usuario.getUsuario());
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al insertar usuario, " + e);
            return false;
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error al eliminar usuario, " + e);
            }
        }
    }

    public boolean modificarUsuario(Usuario usuario) {//se modifica la contraseña del usuario seleccionado
        Connection conexion = con.getConnection();
        try {
            ps = conexion.prepareStatement("UPDATE `laboratorio`.`usuarios` SET `contrasena` = ? WHERE nombre = ?");
            ps.setString(1, usuario.getContraseña());
            ps.setString(2, usuario.getUsuario());
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al modificar usuario, " + e);
            return false;
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error al modificar usuario, " + e);
            }
        }
    }

    public int obtenerIdMuestra() { //se trae el ultimo main.resources.reporte de la base de datos
        Connection conexion = con.getConnection();
        try {
            ps = conexion.prepareStatement("select idmuestras from laboratorio.muestras ORDER BY idmuestras DESC LIMIT 1");
            rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("idmuestras");
                return id;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al obtener id, " + e);
            return 0;
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error al obtener id, " + e);
            }
        }
        return 0;
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

    public boolean consultarFechaIngresada(int id) {
        Connection conexion = con.getConnection();
        try {
            ps = conexion.prepareStatement("select fechaElaboracion, fechaVencimiento from muestras where idmuestras = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                if (rs.getDate("fechaElaboracion") != null && rs.getDate("fechaVencimiento") != null) {
                    return true;
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al consultar usuario, " + e);
            return false;
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
        return false;
    }

    public boolean agregarMuestra(Muestra m) {
        Connection conexion = con.getConnection();
        Date fa = m.getFechaAnalisis();
        Date fv = m.getFechaVencimiento();
        Date fe = m.getFechaElaboracion();
        java.sql.Date def = new java.sql.Date(-789, 10, 11); //se crea una fecha por defecto (1111-11-11) para cuando no se introduce alguna fecha
        try {
            ps = conexion.prepareStatement("insert into muestras (idcliente,solicitante,"
                    + "numeroEstablecimiento,fechaMuestreo,realizadoPor,"
                    + "loteAlimento,identificacion,tipo,fechaVencimiento,idmuestras,fechaElaboracion, lugarMuestreo) values "
                    + "(?,?,?,?,?,?,?,?,?,?,?,?)");
            ps.setInt(1, m.getIdcliente());
            ps.setString(2, m.getSolicitante());
            ps.setString(3, m.getNumeroEstablecimiento());
            ps.setDate(4, m.getFechaMuestreo());
            ps.setString(5, m.getRealizadoPor());
            ps.setString(6, m.getLote());
            if (m.getIdentificacion().equals("")) {
                ps.setObject(7, "-");
            } else {
                ps.setString(7, m.getIdentificacion());
            }
            ps.setString(8, m.getTipo());
            if (fv != null) {
                ps.setDate(9, m.getFechaVencimiento());
            } else {
                ps.setDate(9, def);
            }
            ps.setInt(10, m.getId());
            if (fe != null) {
                ps.setDate(11, m.getFechaElaboracion());
            } else {
                ps.setDate(11, def);
            }
            ps.setString(12, m.getLugarMuestreo().isBlank() ? "-" : m.getLugarMuestreo());
            ps.executeUpdate();
            return true;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al agregar muestra, " + e);
            return false;
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error,cacacascasc " + e);
            }
        }
    }

    public void agregarTipoAgua(int id, String tipo) {
        Connection conexion = con.getConnection();
        try {
            ps = conexion.prepareStatement("update muestras set aguaTipo = ? where idmuestras = ?");
            ps.setString(1, tipo);
            ps.setInt(2, id);
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

    public String recuperarTipoAgua(int id) {
        Connection conexion = con.getConnection();
        try {
            ps = conexion.prepareStatement("select aguaTipo from muestras where idmuestras = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                String aux = rs.getString("aguaTipo");
                return aux;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al recuperar datos, " + e);
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
        return null;
    }

    public void agregarAdministracion(int id, double precio, int pago, int factura) {
        Connection conexion = con.getConnection();
        try {
            ps = conexion.prepareStatement("INSERT INTO administracion "
                    + "(idmuestras,precioTotal,pago,factura,entrada,borrado)VALUES "
                    + "(?,?,?,?,current_timestamp(),0)");
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
            ps = conexion.prepareStatement("update administracion set precioTotal"
                    + " = ?, pago = ?, factura = ?, borrado = 0 where idmuestras = ?");
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
    }

    public ArrayList<Object> autocompletar() { //método para autocompletar el campo de busqueda en la pantalla principal
        ArrayList<Object> arreglo = new ArrayList<>();
        Connection conexion = con.getConnection();
        try {
            ps = conexion.prepareStatement("select distinct procedencia from vistaTabla");
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

    public boolean eliminarMuestra(int id) {
        Connection conexion = con.getConnection();
        try {
            ps = conexion.prepareStatement("DELETE FROM `laboratorio`.`muestras` WHERE (`idmuestras` = ?)");
            ps.setInt(1, id);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar muestra, " + e);
            return false;
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
    }

    public String obtenerProcedencia(int id) { //se obtiene la procedencia mediante el id de la muestra
        Connection conexion = con.getConnection();
        String procedencia = "";
        try {
            ps = conexion.prepareStatement("select procedencia from vistaTabla2 where idmuestras = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                procedencia = rs.getString(1);
            }
            return procedencia;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al obtener procedencia, " + e);
            return null;
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
    }

    public boolean agregarVencimiento(Resultados r) { //se agega el vencimiento, si es mb se agregan 6 meses y fq 12 meses
        Connection conexion = con.getConnection();
        int id = obtenerIdMuestra();
        try {
            if (r.getTipo().startsWith("Microbiológico")) {
                ps = conexion.prepareStatement(vencimientoMb);
                ps.setDate(1, r.getFechaAnalisis());
                ps.setInt(2, r.getIdmuestras());
            } else if (r.getTipo().startsWith("Físico químico")) {
                ps = conexion.prepareStatement(vencimientoFq);
                ps.setDate(1, r.getFechaAnalisis());
                ps.setInt(2, r.getIdmuestras());
            }
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al agregar vencimiento, " + e);
            return false;
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
    }

    public DefaultTableModel obtenerVencimientos(Date desde, Date hasta, boolean actualizar) {//se obtienen los datos para la tabla de vencimientos
        Connection conexion = con.getConnection();
        Object fila[] = new Object[5];
        boolean aviso = false;
        modeloVencimientos.setColumnCount(0);
        if (modeloVencimientos.getColumnCount() == 0) {
            modeloVencimientos.addColumn("ID");
            modeloVencimientos.addColumn("Procedencia");
            modeloVencimientos.addColumn("Solicitante");
            modeloVencimientos.addColumn("Fecha de vencimiento");
            modeloVencimientos.addColumn("Avisado");
        }
        modeloVencimientos.setRowCount(0);
        java.sql.Date desdeSql = new java.sql.Date(desde.getTime());
        java.sql.Date hastaSql = new java.sql.Date(hasta.getTime());
        try {
            if (actualizar) {
                ps = conexion.prepareStatement("select * from vistavencimientos where"
                        + " fechaVencimiento between ? and ? order by fechaVencimiento asc"); // se recuperan los datos de la base de datos de los vencimeintos de todo el mes
            } else {
                ps = conexion.prepareStatement("select * from vistavencimientos where"
                        + " fechaVencimiento between first_day(?) and last_day(?) order by fechaVencimiento asc"); // se recuperan los datos de la base de datos de los vencimeintos de todo el mes
            }
            ps.setDate(1, desdeSql);
            ps.setDate(2, hastaSql);
            rs = ps.executeQuery();
            while (rs.next()) {
                for (int i = 0; i < 5; i++) {
                    switch (i) {
                        case 0: {
                            fila[i] = String.valueOf(rs.getInt("idmuestras"));
                            break;
                        }
                        case 3: {
                            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy"); //se formatea de forma tal de que sea más entendible por el usuario
                            fila[i] = formatter.format(rs.getDate("fechaVencimiento"));
                            break;
                        }
                        case 4: {
                            if (rs.getInt("aviso") == 0) {
                                aviso = false;
                                fila[i] = aviso;
                            } else {
                                aviso = true;
                                fila[i] = aviso;
                            }
                            break;
                        }
                        default: {
                            fila[i] = rs.getString(i + 1);
                            break;
                        }
                    }
                }
                modeloVencimientos.addRow(fila);
            }
            return modeloVencimientos;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al obtener vencimientos, " + e);
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
        return null;
    }

    public DefaultTableModel obtenerVencimientosInformes(Date desde, Date hasta) {//se obtienen los datos para la tabla de vencimientos
        Connection conexion = con.getConnection();
        Object fila[] = new Object[4];
        boolean aviso = false;
        modeloTabla.setColumnCount(0);
        if (modeloTabla.getColumnCount() == 0) {
            modeloVencimientos.addColumn("ID");
            modeloVencimientos.addColumn("Procedencia");
            modeloVencimientos.addColumn("Vencimiento");
            modeloVencimientos.addColumn("Tipo");
            modeloVencimientos.addColumn("Seleccionar");
        }
        java.sql.Date desdeSql = new java.sql.Date(desde.getTime());
        java.sql.Date hastaSql = new java.sql.Date(hasta.getTime());
        try {
            ps = conexion.prepareStatement("select idmuestras, procedencia, fechaVencimiento, tipo from vistavencimientos where aviso = 0 and fechaVencimiento between ? and ?");
            ps.setDate(1, desdeSql);
            ps.setDate(2, hastaSql);
            rs = ps.executeQuery();
            while (rs.next()) {
                for (int i = 0; i < 4; i++) {
                    switch (i) {
                        case 0: {
                            fila[i] = String.valueOf(rs.getInt("idmuestras"));
                            break;
                        }
                        case 2: {
                            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy"); //se formatea de forma tal de que sea más entendible por el usuario
                            fila[i] = formatter.format(rs.getDate("fechaVencimiento"));
                            break;
                        }
                        case 4: {
                            fila[i] = false;
                            break;
                        }
                        default: {
                            fila[i] = rs.getString(i + 1);
                            break;
                        }
                    }
                }
                modeloVencimientos.addRow(fila);
            }
            return modeloVencimientos;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al obtener vencimientos, " + e);
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
        return null;
    }

    public boolean borrarAnalisis(int id, int sino) {
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
            ps = conexion.prepareStatement("select idmuestras, procedencia, solicitante,"
                    + " fechaMuestreo, fechaAnalisis, tipo from vistaborrados");
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
    }

    public DefaultTableModel llenarTabla() { // se obtienen todos los datos para llenar la tabla de muestras
        String[] fila = new String[11];
        Connection conexion = con.getConnection();
        modeloTabla.setColumnCount(0);
        if (modeloTabla.getColumnCount() == 0) {
            modeloTabla.addColumn("ID");
            modeloTabla.addColumn("Procedencia");
            modeloTabla.addColumn("Solicitante");
            modeloTabla.addColumn("N° establecimiento");
            modeloTabla.addColumn("Muestreo");
            modeloTabla.addColumn("Análisis");
            modeloTabla.addColumn("Realizado por");
            modeloTabla.addColumn("Fecha entrada");
            modeloTabla.addColumn("Pago");
            modeloTabla.addColumn("Factura ");
            modeloTabla.addColumn("Tipo de análisis");
        }
        try {
            modeloTabla.setRowCount(0);
            ps = conexion.prepareStatement("select * from vistatabla");
            rs = ps.executeQuery();
            while (rs.next()) {
                for (int i = 0; i < fila.length; i++) {
                    switch (i) {
                        case 0: {
                            fila[i] = String.format("%05d", rs.getObject(i + 1));
                            break;
                        }
                        case 4: {
                            if (rs.getDate("fechaMuestreo").toString().equals("1111-11-11")) {
                                fila[i] = "-";
                            } else {
                                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                                fila[i] = formatter.format(rs.getDate("fechaMuestreo"));
                            }
                            break;
                        }
                        case 5: {
                            if (rs.getObject("fechaAnalisis").toString().equals("1111-11-11")) {
                                fila[i] = "-";
                            } else {
                                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                                fila[i] = formatter.format(rs.getDate("fechaAnalisis"));
                            }
                            break;
                        }
                        case 7: {
                            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                            fila[i] = formatter.format(rs.getDate("entrada"));
                            break;
                        }
                        case 8:
                            if (rs.getObject(i + 1).equals(1)) {
                                fila[i] = "Si";//si el dato es 1 se pone "si" y si es 0 se pone "no"
                            } else if (rs.getObject(i + 1).equals(0)) {
                                fila[i] = "No";
                            }
                            break;
                        case 9:
                            if (rs.getObject(i + 1).equals(1)) {
                                fila[i] = "Si";
                            } else if (rs.getObject(i + 1).equals(0)) {
                                fila[i] = "No";
                            }
                            break;
                        default:
                            fila[i] = String.valueOf(rs.getObject(i + 1));
                            break;
                    }
                }
                modeloTabla.addRow(fila); // se agrega un renglon al org.ignaciorodriguez.modelo de la tabla
            }
            return modeloTabla; //se devuelve un org.ignaciorodriguez.modelo de tabla
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al llenarTabla, " + e);
            return null;
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
    }

    public DefaultTableModel buscarTabla(String parametro, String valor) { // se obtienen todos los datos para llenar la tabla de muestras
        if (parametro == "-1" || valor == "-1") {
            return llenarTabla();
        } else {
            String[] fila = new String[13];
            Connection conexion = con.getConnection();
            modeloTabla.setColumnCount(0);
            if (modeloTabla.getColumnCount() == 0) {
                modeloTabla.addColumn("ID");
                modeloTabla.addColumn("Procedencia");
                modeloTabla.addColumn("Solicitante");
                modeloTabla.addColumn("N° establecimiento");
                modeloTabla.addColumn("Muestreo");
                modeloTabla.addColumn("Análisis");
                modeloTabla.addColumn("Realizado por");
                modeloTabla.addColumn("Coste total");
                modeloTabla.addColumn("Pago");
                modeloTabla.addColumn("Factura ");
                modeloTabla.addColumn("Tipo de análisis");
                modeloTabla.addColumn("Identificaciones");
                modeloTabla.addColumn("Estado");
            }
            try {
                modeloTabla.setRowCount(0);
                if (parametro == "procedencia" || parametro == "solicitante") {
                    ps = conexion.prepareStatement("select * from vistabusqueda where " + parametro + " like ?");
                    ps.setString(1, "%" + valor + "%");
                } else if (parametro == "fechaAnalisis" || parametro == "fechaMuestreo") {
                    ps = conexion.prepareStatement("select * from vistabusqueda where " + parametro + " between " + valor);
                } else {
                    ps = conexion.prepareStatement("select * from vistabusqueda where " + parametro + " = " + valor);
                }
                rs = ps.executeQuery();
                while (rs.next()) {
                    for (int i = 0; i < fila.length; i++) {
                        switch (i) {
                            case 0: {
                                fila[i] = String.format("%05d", rs.getObject(i + 1));
                                break;
                            }
                            case 4: {
                                if (rs.getDate("fechaMuestreo").toString().equals("1111-11-11")) {
                                    fila[i] = "-";
                                } else {
                                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                                    fila[i] = formatter.format(rs.getDate("fechaMuestreo"));
                                }
                                break;
                            }
                            case 5: {
                                if (rs.getObject("fechaAnalisis").toString().equals("1111-11-11")) {
                                    fila[i] = "-";
                                } else {
                                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                                    fila[i] = formatter.format(rs.getDate("fechaAnalisis"));
                                }
                                break;
                            }
                            case 7: {
                                if (rs.getDouble("precioTotal") == -1) {
                                    fila[i] = "-";
                                } else {
                                    fila[i] = String.valueOf(rs.getDouble(i + 1));
                                }
                                break;
                            }
                            case 8:
                                if (rs.getObject(i + 1).equals(1)) {
                                    fila[i] = "Si";//si el dato es 1 se pone "si" y si es 0 se pone "no"
                                } else if (rs.getObject(i + 1).equals(0)) {
                                    fila[i] = "No";
                                }
                                break;
                            case 9:
                                if (rs.getObject(i + 1).equals(1)) {
                                    fila[i] = "Si";
                                } else if (rs.getObject(i + 1).equals(0)) {
                                    fila[i] = "No";
                                }
                                break;
                            default:
                                fila[i] = String.valueOf(rs.getObject(i + 1));
                                break;
                        }
                    }
                    modeloTabla.addRow(fila); // se agrega un renglon al org.ignaciorodriguez.modelo de la tabla
                }
                return modeloTabla; //se devuelve un org.ignaciorodriguez.modelo de tabla
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error al llenarTabla, " + e);
                return null;
            } finally {
                try {
                    conexion.close();
                } catch (Exception e) {
                    System.err.println("Error, " + e);
                }
            }
        }
    }

    public boolean agregarCliente(boolean nombre, boolean empresa, Cliente c) {
        Connection conexion = con.getConnection();
        if (nombre == true && empresa == true) {
            try {
                ps = conexion.prepareStatement("insert into datos_cliente (empresa,nombre,"
                        + "direccion,ciudad,telefono,email,cuit) values (?,?,?,?,?,?,?)");
                ps.setString(1, c.getEmpresa());
                ps.setString(2, c.getNombre());
                if (c.getDireccion().equals("")) {
                    ps.setString(3, " ");
                } else {

                    ps.setString(3, c.getDireccion());
                }
                if (c.getCiudad().equals("")) {
                    ps.setString(4, " ");
                } else {

                    ps.setString(4, c.getCiudad());
                }
                if (c.getTelefono().equals("")) {
                    ps.setString(5, " ");
                } else {

                    ps.setString(5, c.getTelefono());
                }
                if (c.getEmail().equals("")) {
                    ps.setString(6, " ");
                } else {

                    ps.setString(6, c.getEmail());
                }
                if (c.getCuit().equals("")) {
                    ps.setString(7, "");
                } else {
                    ps.setString(7, c.getCuit());
                }
                ps.executeUpdate();
                return true;
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error al agregar cliente, " + e);
                return false;
            } finally {
                try {
                    conexion.close();
                } catch (Exception e) {
                    System.err.println("Error " + e);
                }
            }
        } else if (nombre == true && empresa == false) {
            try {
                ps = conexion.prepareStatement("insert into datos_cliente (nombre,"
                        + "direccion,ciudad,telefono,email,cuit) values (?,?,?,?,?,?)");
                ps.setString(1, c.getNombre());
                if (c.getDireccion().equals("")) {
                    ps.setString(2, " ");
                } else {
                    ps.setString(2, c.getDireccion());
                }
                if (c.getCiudad().equals("")) {
                } else {
                    ps.setString(3, c.getCiudad());
                }
                if (c.getTelefono().equals("")) {
                    ps.setString(4, " ");
                } else {
                    ps.setString(4, c.getTelefono());
                }
                if (c.getEmail().equals("")) {
                    ps.setString(5, " ");
                } else {
                    ps.setString(5, c.getEmail());
                }
                if (c.getCuit().equals("")) {
                    ps.setString(6, "");
                } else {
                    ps.setString(6, c.getCuit());
                }
                ps.executeUpdate();
                return true;
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error al agregar cliente, " + e);
                return false;
            } finally {
                try {
                    conexion.close();
                } catch (Exception e) {
                    System.err.println("Error " + e);
                }
            }
        } else if (nombre == false && empresa == true) {
            try {
                ps = conexion.prepareStatement("insert into datos_cliente (empresa,"
                        + "direccion,ciudad,telefono,email,cuit) values (?,?,?,?,?,?)");
                ps.setString(1, c.getEmpresa());
                if (c.getDireccion().equals("")) {
                    ps.setString(2, " ");
                } else {

                    ps.setString(2, c.getDireccion());
                }
                if (c.getCiudad().equals("")) {
                    ps.setString(3, " ");
                } else {

                    ps.setString(3, c.getCiudad());
                }
                if (c.getTelefono().equals("")) {
                    ps.setString(4, " ");
                } else {

                    ps.setString(4, c.getTelefono());
                }
                if (c.getEmail().equals("")) {
                    ps.setString(5, " ");
                } else {

                    ps.setString(5, c.getEmail());
                }
                if (c.getCuit().equals("")) {
                    ps.setString(6, "");
                } else {
                    ps.setString(6, c.getCuit());
                }
                ps.executeUpdate();
                return true;
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error al agregar cliente, " + e);
                return false;
            } finally {
                try {
                    conexion.close();
                } catch (Exception e) {
                    System.err.println("Error " + e);
                }
            }
        }
        return false;
    }

    public ArrayList<Object> autocompletarProcedencia() { //método para autocompletar procedencias
        ArrayList<Object> arreglo = new ArrayList<>();
        Connection conexion = con.getConnection();
        try {
            ps = conexion.prepareStatement("select procedencia from vistaProcedencia");
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

    public DefaultTableModel tablaProcedencia() {// se traen de la base de datos las procedencias para la tabla de clientes
        Connection conexion = con.getConnection();
        modeloTablaProcedencia.addColumn("Cliente");
        modeloTablaProcedencia.addColumn("Teléfono");
        modeloTablaProcedencia.addColumn("Email");
        String fila[] = new String[3];
        try {
            ps = conexion.prepareStatement("select procedencia, telefono, email from vistaprocedencia where anulado = 0");
            rs = ps.executeQuery();
            while (rs.next()) {
                fila[0] = rs.getString("procedencia");
                fila[1] = rs.getString("telefono");
                fila[2] = rs.getString("email");
                modeloTablaProcedencia.addRow(fila);
            }
            return modeloTablaProcedencia;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al procedencia, " + e);
            return null;
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
    }

    public DefaultTableModel tablaProcedenciaBorrados() {// se traen de la base de datos las procedencias para la tabla de clientes
        Connection conexion = con.getConnection();
        modeloTablaProcedencia.addColumn("Cliente");
        modeloTablaProcedencia.addColumn("Teléfono");
        modeloTablaProcedencia.addColumn("Email");
        String fila[] = new String[3];
        try {
            ps = conexion.prepareStatement("select procedencia, telefono, email from vistaprocedencia where anulado = 1");
            rs = ps.executeQuery();
            while (rs.next()) {
                fila[0] = rs.getString("procedencia");
                fila[1] = rs.getString("telefono");
                fila[2] = rs.getString("email");
                modeloTablaProcedencia.addRow(fila);
            }
            return modeloTablaProcedencia;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al procedencia, " + e);
            return null;
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
    }

    public boolean recuperarClienteBorrado(int id) {// se traen de la base de datos las procedencias para la tabla de clientes
        Connection conexion = con.getConnection();
        try {
            ps = conexion.prepareStatement("update datos_cliente set anulado = 0 where idcliente = ?");
            ps.setInt(1, id);
            ps.executeUpdate();
            return true;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al recuperar cliente borrado, " + e);
            return false;
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
    }

    public DefaultTableModel tablaProcedenciaDespues() { //método para actualizar a tabla de clientes
        Connection conexion = con.getConnection();
        String fila[] = new String[1];
        modeloTablaProcedencia.setRowCount(0);
        try {
            ps = conexion.prepareStatement("select procedencia from vistaProcedencia where anulado = 0");
            rs = ps.executeQuery();
            while (rs.next()) {
                fila[0] = rs.getString("procedencia");
                modeloTablaProcedencia.addRow(fila);
            }
            return modeloTablaProcedencia;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al tablaProcedenciaDespues, " + e);
            return null;
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
    }

    public DefaultTableModel tablaProcedenciaBorradosDespues() { //método para actualizar a tabla de clientes
        Connection conexion = con.getConnection();
        String fila[] = new String[1];
        modeloTablaProcedencia.setRowCount(0);
        try {
            ps = conexion.prepareStatement("select procedencia from vistaProcedencia where anulado = 1");
            rs = ps.executeQuery();
            while (rs.next()) {
                fila[0] = rs.getString("procedencia");
                modeloTablaProcedencia.addRow(fila);
            }
            return modeloTablaProcedencia;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al tablaProcedenciaDespues, " + e);
            return null;
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
    }

    public int obtenerIdCliente(String procedencia) { //se obtiene el id del cliente mediante la procedencia
        Connection conexion = con.getConnection();
        try {
            ps = conexion.prepareStatement("select idcliente from vistaProcedencia where "
                    + "procedencia = ?");
            ps.setString(1, procedencia);
            rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getInt("idcliente");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al obtener id de cliente, " + e);
            return -1;
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
        return -1;
    }

    public boolean editarMuestra(Muestra m) { //se actualizan los datos de la muestra
        Connection conexion = con.getConnection();
        Date fa = m.getFechaAnalisis();
        Date fv = m.getFechaVencimiento();
        Date fe = m.getFechaElaboracion();
        java.sql.Date def = new java.sql.Date(-789, 10, 11);
        try {
            ps = conexion.prepareStatement("update muestras set solicitante = ?, "
                    + "numeroEstablecimiento = ?, fechaMuestreo = ?, realizadoPor = ?, "
                    + "loteAlimento = ?, identificacion = ?, tipo = ?, fechaVencimiento = ?, "
                    + "fechaElaboracion = ?, lugarMuestreo = ?, idcliente = ? where idmuestras = ?");
            ps.setString(1, m.getSolicitante());
            ps.setString(2, m.getNumeroEstablecimiento());
            ps.setDate(3, m.getFechaMuestreo());
            ps.setString(4, m.getRealizadoPor());
            ps.setString(5, m.getLote());
            ps.setString(6, m.getIdentificacion());
            ps.setString(7, m.getTipo());
            if (fv == null) {
                ps.setDate(8, def);
            } else {
                ps.setDate(8, m.getFechaVencimiento());
            }
            if (fe == null) {
                ps.setDate(9, def);
            } else {
                ps.setDate(9, m.getFechaElaboracion());
            }
            ps.setString(10, m.getLugarMuestreo());
            ps.setInt(11, m.getIdcliente());
            ps.setInt(12, m.getId());
            ps.executeUpdate();
            return true;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al editar muestra, " + e);
            return false;
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error,cacacascasc " + e);
            }
        }
    }

    public boolean checkearVencimiento(Resultados r) {
        Connection conexion = con.getConnection();
        try {
            ps = conexion.prepareStatement("select * from vencimientos where idmuestras = ?");
            ps.setInt(1, r.getIdmuestras());
            rs = ps.executeQuery();
            while (rs.next()) {
                return true;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al recuperar muestra, " + e);
            return false;
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error,cacacascasc " + e);
            }
        }
        return false;
    }

    public boolean actualizarVencimiento(Resultados r) { // se actualiza un vencimiento
        Connection conexion = con.getConnection();
        try {
            if (r.getTipo().startsWith("Microbiológico")) {
                ps = conexion.prepareStatement("update vencimientos set fechaVencimiento = "
                        + "(date_add(?,interval 6 month)) where `idmuestras` = ?");
                ps.setDate(1, r.getFechaAnalisis());
                ps.setInt(2, r.getIdmuestras());
            } else if (r.getTipo().startsWith("Físico químico")) {
                ps = conexion.prepareStatement("update vencimientos set fechaVencimiento = "
                        + "(date_add(?,interval 12 month)) where `idmuestras` = ?");
                ps.setDate(1, r.getFechaAnalisis());
                ps.setInt(2, r.getIdmuestras());
            }
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar vencimiento, " + e);
            return false;
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
    }

    public boolean guardarDeterminacionesAHacer(String query, int num, boolean anular, int id) { // se actualiza un vencimiento
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
    }

    public boolean seleccionarVencimiento(int id, int seleccionar) { // se actualiza un vencimiento
        Connection conexion = con.getConnection();
        try {
            ps = conexion.prepareStatement("update administracion set seleccionadoVencimiento = ? where idmuestras = ?");
            ps.setInt(1, seleccionar);
            ps.setInt(2, id);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar vencimiento, " + e);
            return false;
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
    }

    public String definirCaracteres() {//se agregar los caracteres organolepticos

        TextArea textArea;
        textArea = new TextArea(null, 10, 10, SCROLLBARS_NONE);
        textArea.setFont(new Font("SegoeUI", Font.PLAIN, 12));
        int ret = JOptionPane.showConfirmDialog(null, textArea, "Introduzca los "
                + "caracteres organoléptios", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE);
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
        int ret = JOptionPane.showConfirmDialog(null, textArea, "Introduzca los "
                + "carecteres organoléptios", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE);
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

    public String consultaUsuarioNombre(String s) { // se obtiene el
        Connection conexion = con.getConnection();
        String nombre = "";
        try {
            ps = conexion.prepareStatement("select nombre from usuarios where contrasena = ?");
            ps.setString(1, s);
            rs = ps.executeQuery();
            while (rs.next()) {
                nombre = String.valueOf(rs.getObject("nombre"));
                return nombre;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al obtener nombre de usuario, " + e);
            return null;
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
        return null;
    }

    public boolean consultaExisteUsuario(String s) {
        Connection conexion = con.getConnection();
        try {
            ps = conexion.prepareStatement("select nombre, tipousuario from usuarios where contrasena = ?");
            ps.setString(1, s);
            rs = ps.executeQuery();
            while (rs.next()) {
                return true;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al consultar usuario, " + e);
            return false;
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
        return false;
    }

    public int consultaUsuarioTipo(String s) {
        Connection conexion = con.getConnection();
        int tipo = -1;
        try {
            ps = conexion.prepareStatement("select tipousuario from usuarios where contrasena = ?");
            ps.setString(1, s);
            rs = ps.executeQuery();
            while (rs.next()) {
                tipo = rs.getInt("tipousuario");
                return tipo;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al consultar tipo de usuario, " + e);
            return tipo;
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
        return tipo;
    }

    public Usuario consultaUsuario(String nombre) {
        Connection conexion = con.getConnection();
        Usuario u = new Usuario();
        try {
            ps = conexion.prepareStatement("select idusuarios, nombre, contrasena from usuarios where nombre = ?");
            ps.setString(1, nombre);
            rs = ps.executeQuery();

            if (rs.next()) {
                for (int i = 0; i < 3; i++) {
                    switch (i) {
                        case 1: {
                            u.setId(rs.getInt("idusuarios"));
                        }
                        case 2: {
                            u.setUsuario(rs.getString("nombre"));
                        }
                        case 3: {
                            u.setContraseña(rs.getString("contrasena"));
                        }
                    }
                }
            }
            return u;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al recuperar usuario, " + e);
            return null;
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
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
            ps = conexion.prepareStatement("insert into entregas (idmuestras, "
                    + "idusuario, persona, hora) values (?,?,?,current_timestamp())");
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

    public void entregado(int id) {
        Connection conexion = con.getConnection();
        try {
            ps = conexion.prepareStatement("update administracion set entregado = 1 where idmuestras = ?");
            ps.setInt(1, id);
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

    public Muestra obtenerMuestra(int id) {
        Connection conexion = con.getConnection();
        Muestra m = new Muestra();
        try {
            ps = conexion.prepareStatement("SELECT `solicitante`, `procedencia`, "
                    + "`numeroEstablecimiento`, `fechaMuestreo`, `realizadoPor`, "
                    + "`precioTotal`, `pago`, `factura`, `tipo`, `lote`, "
                    + "`identificacion`, `fechaElaboracion`, `lugarMuestreo`, "
                    + "`fechaVencimiento`, aguatipo, idcliente FROM `vistaeditar` where idmuestras = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                m.setSolicitante(rs.getString("solicitante"));
                m.setProcedencia(rs.getString("procedencia"));
                m.setNumeroEstablecimiento(rs.getString("numeroEstablecimiento"));
                m.setFechaMuestreo(rs.getDate("fechaMuestreo"));
                m.setRealizadoPor(rs.getString("realizadoPor"));
                m.setCosteTotal(rs.getDouble("precioTotal"));
                m.setPago(rs.getInt("pago"));
                m.setFactura(rs.getInt("factura"));
                m.setTipo(rs.getString("tipo"));
                m.setLote(rs.getString("lote"));
                m.setIdentificacion(rs.getString("identificacion"));
                m.setFechaElaboracion(rs.getDate("fechaElaboracion"));
                m.setLugarMuestreo(rs.getString("lugarMuestreo"));
                m.setFechaVencimiento(rs.getDate("fechaVencimiento"));
                m.setTipoAgua(rs.getString("aguatipo"));
                m.setIdcliente(rs.getInt("idcliente"));
            }
            return m;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al obtener muestra, " + e);
            return null;
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
    }

    public void marcarSeleccionados(int id) {
        Connection conexion = con.getConnection();
        try {
            ps = conexion.prepareStatement("update administracion set seleccionado = 1 "
                    + "where idmuestras = ?");
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            System.err.println("Error, " + e);
        }
    }

    public void analizado(int id) {
        Connection conexion = con.getConnection();
        try {
            ps = conexion.prepareStatement("update administracion set analizado = analizado + 1 "
                    + "where idmuestras = ?");
            ps.setInt(1, id);
            System.out.println("ps.toString() = " + ps.toString());
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

    public DefaultTableModel tablaAnalisis() {
        Connection conexion = con.getConnection();
        Object fila[] = new Object[7];
        modeloAnalisis.setRowCount(0);
        modeloAnalisis.setColumnCount(0);
        modeloAnalisis.addColumn("");
        modeloAnalisis.addColumn("ID");
        modeloAnalisis.addColumn("Procedencia");
        modeloAnalisis.addColumn("Solicitante");
        modeloAnalisis.addColumn("Fecha de muestreo");
        modeloAnalisis.addColumn("Tipo");
        modeloAnalisis.addColumn("Impreso");
        try {
            ps = conexion.prepareStatement("select m.idmuestras, p.procedencia, "
                    + "m.solicitante,m.fechaMuestreo, m.tipo, a.analizado from muestras m join "
                    + "administracion a on m.idmuestras = a.idmuestras join "
                    + "vistaprocedencia p on m.idcliente = p.idcliente order by idmuestras desc");
            rs = ps.executeQuery();
            while (rs.next()) {
                for (int i = 0; i < fila.length; i++) {
                    fila[0] = false;
                    fila[1] = rs.getObject("idmuestras");
                    fila[2] = rs.getObject("procedencia");
                    fila[3] = rs.getObject("solicitante");
                    SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                    fila[4] = df.format(rs.getDate("fechaMuestreo"));
                    fila[5] = rs.getObject("tipo");
                    int analizado = rs.getInt("analizado");
                    fila[6] = analizado > 1 ? analizado + " veces." : analizado == 1 ? "1 vez." : "0 veces.";
                }
                modeloAnalisis.addRow(fila);
            }
            return modeloAnalisis;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al obtener muestra, " + e);
            return null;
        }
    }

    public void actualizarAvisados(int id, int aviso) {
        Connection conexion = con.getConnection();
        try {
            ps = conexion.prepareStatement("update vencimientos set aviso = ? where idmuestras = ?");
            ps.setInt(1, aviso);
            ps.setInt(2, id);
            System.out.println("ps.toString() = " + ps.toString());
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

    public String obtenerProcedenciayNombre(int id) {
        Connection conexion = con.getConnection();
        try {
            ps = conexion.prepareStatement("select empresa, nombre from datos_cliente where idcliente = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                String aux = rs.getObject("empresa") + " " + rs.getObject("nombre");
                return aux;
            }
            return null;
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

    public String[] obtenerProcedenciayNombreEmail(int id) {
        Connection conexion = con.getConnection();
        String[] aux = new String[2];
        try {
            ps = conexion.prepareStatement("select empresa, nombre from datos_cliente where idcliente = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                aux[0] = rs.getObject("empresa").toString();
                aux[1] = rs.getObject("nombre").toString();
                return aux;
            }
            return null;
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

    public boolean guardarResultadoMBAgua(Resultados r) {
        Connection conexion = con.getConnection();
        try {
            ps = conexion.prepareStatement("insert into MBAgua (idmuestras, "
                    + "germenes, coliformesTotales, coliformesFecales, escherichia, pseudomona,"
                    + " ph, cloroLibre, caracteresOrganolepticos, mohos, shigella) values (?,?,?,?,?,?,?,?,?,?,?)");
            ps.setInt(1, r.getIdmuestras());
            ps.setString(2, r.getGermenes());
            ps.setString(3, r.getColiformesTotales());
            ps.setString(4, r.getColiformesFecales());
            ps.setString(5, r.getEscherichia());
            ps.setString(6, r.getPseudomona());
            ps.setDouble(7, r.getPh());
            ps.setDouble(8, r.getClorototal());
            ps.setString(9, r.getCaracteresOrgasnolepticos());
            ps.setString(10, r.getMohos());
            ps.setString(11, r.getShigella());
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

    public boolean guardarResultadoManual(Map m) {
        Connection conexion = con.getConnection();
        try {
            // Construcción dinámica de la parte VALUES (?,?,?...)
            StringBuilder values = new StringBuilder("?"); // El primero es idmuestras
            for (int i = 0; i < (34 * 4) + 2; i++) { // 34 campos * 4 categorías + titulo + mostrar
                values.append(",?");
            }

            ps = conexion.prepareStatement("INSERT INTO `laboratorio`.`manual` ("
                    + "`idmuestras`,"
                    + "`determinacion1`,`determinacion2`,`determinacion3`,`determinacion4`,`determinacion5`,`determinacion6`,`determinacion7`,`determinacion8`,`determinacion9`,`determinacion10`,"
                    + "`determinacion11`,`determinacion12`,`determinacion13`,`determinacion14`,`determinacion15`,`determinacion16`,`determinacion17`,`determinacion18`,`determinacion19`,`determinacion20`,"
                    + "`determinacion21`,`determinacion22`,`determinacion23`,`determinacion24`,`determinacion25`,`determinacion26`,`determinacion27`,`determinacion28`,`determinacion29`,`determinacion30`,"
                    + "`determinacion31`,`determinacion32`,`determinacion33`,`determinacion34`,"
                    + "`recuentoObtenido1`,`recuentoObtenido2`,`recuentoObtenido3`,`recuentoObtenido4`,`recuentoObtenido5`,`recuentoObtenido6`,`recuentoObtenido7`,`recuentoObtenido8`,`recuentoObtenido9`,`recuentoObtenido10`,"
                    + "`recuentoObtenido11`,`recuentoObtenido12`,`recuentoObtenido13`,`recuentoObtenido14`,`recuentoObtenido15`,`recuentoObtenido16`,`recuentoObtenido17`,`recuentoObtenido18`,`recuentoObtenido19`,`recuentoObtenido20`,"
                    + "`recuentoObtenido21`,`recuentoObtenido22`,`recuentoObtenido23`,`recuentoObtenido24`,`recuentoObtenido25`,`recuentoObtenido26`,`recuentoObtenido27`,`recuentoObtenido28`,`recuentoObtenido29`,`recuentoObtenido30`,"
                    + "`recuentoObtenido31`,`recuentoObtenido32`,`recuentoObtenido33`,`recuentoObtenido34`,"
                    + "`recuentoNormal1`,`recuentoNormal2`,`recuentoNormal3`,`recuentoNormal4`,`recuentoNormal5`,`recuentoNormal6`,`recuentoNormal7`,`recuentoNormal8`,`recuentoNormal9`,`recuentoNormal10`,"
                    + "`recuentoNormal11`,`recuentoNormal12`,`recuentoNormal13`,`recuentoNormal14`,`recuentoNormal15`,`recuentoNormal16`,`recuentoNormal17`,`recuentoNormal18`,`recuentoNormal19`,`recuentoNormal20`,"
                    + "`recuentoNormal21`,`recuentoNormal22`,`recuentoNormal23`,`recuentoNormal24`,`recuentoNormal25`,`recuentoNormal26`,`recuentoNormal27`,`recuentoNormal28`,`recuentoNormal29`,`recuentoNormal30`,"
                    + "`recuentoNormal31`,`recuentoNormal32`,`recuentoNormal33`,`recuentoNormal34`,"
                    + "`metodo1`,`metodo2`,`metodo3`,`metodo4`,`metodo5`,`metodo6`,`metodo7`,`metodo8`,`metodo9`,`metodo10`,"
                    + "`metodo11`,`metodo12`,`metodo13`,`metodo14`,`metodo15`,`metodo16`,`metodo17`,`metodo18`,`metodo19`,`metodo20`,"
                    + "`metodo21`,`metodo22`,`metodo23`,`metodo24`,`metodo25`,`metodo26`,`metodo27`,`metodo28`,`metodo29`,`metodo30`,"
                    + "`metodo31`,`metodo32`,`metodo33`,`metodo34`, `titulo`, `mostrar`"
                    + ") VALUES (" + values.toString() + ")");

            int index = 1;

// 1. idmuestras
            ps.setInt(index++, (int) m.get("idmuestras"));

// 2. Determinaciones 1-34
            for (int i = 1; i <= 34; i++) {
                ps.setString(index++, String.valueOf(m.get("determinacion" + i)));
            }

// 3. Recuentos Obtenidos 1-34
            for (int i = 1; i <= 34; i++) {
                ps.setString(index++, String.valueOf(m.get("recuentoObtenido" + i)));
            }

// 4. Recuentos Normales 1-34
            for (int i = 1; i <= 34; i++) {
                ps.setString(index++, String.valueOf(m.get("recuentoNormal" + i)));
            }

// 5. Metodos 1-34
            for (int i = 1; i <= 34; i++) {
                ps.setString(index++, String.valueOf(m.get("metodo" + i)));
            }

// 6. Campos finales
            ps.setString(index++, String.valueOf(m.get("titulo")));
            ps.setString(index++, String.valueOf(m.get("mostrar")));

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

    public boolean checkearResultadoManual(int id) {
        Connection conexion = con.getConnection();
        try {
            ps = conexion.prepareStatement("select * from manual where idmuestras = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                return true;
            }
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
        return false;
    }

    public Map<String, String> recuperarResultadoManual(int id) {
        Connection conexion = con.getConnection();
        Map<String, String> map = new HashMap();
        try {
            ps = conexion.prepareStatement("select * from manual where idmuestras = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                for (int i = 3; i < rs.getMetaData().getColumnCount(); i++) {
                    map.put(rs.getMetaData().getColumnName(i), rs.getString(i));
                }
            }
            return map;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al guardar datos, " + e);
            return null;
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
    }

    public boolean editarResultadoManual(Map m) {
        Connection conexion = con.getConnection();
        try {
            ps = conexion.prepareStatement("UPDATE `laboratorio`.`manual` SET "
                    + "`determinacion1` = ?,`determinacion2` = ?,`determinacion3` = ?,`determinacion4` = ?,`determinacion5` = ?,`determinacion6` = ?,`determinacion7` = ?,`determinacion8` = ?,`determinacion9` = ?,`determinacion10` = ?,"
                    + "`determinacion11` = ?,`determinacion12` = ?,`determinacion13` = ?,`determinacion14` = ?,`determinacion15` = ?,`determinacion16` = ?,`determinacion17` = ?,`determinacion18` = ?,`determinacion19` = ?,`determinacion20` = ?,"
                    + "`determinacion21` = ?,`determinacion22` = ?,`determinacion23` = ?,`determinacion24` = ?,`determinacion25` = ?,`determinacion26` = ?,`determinacion27` = ?,`determinacion28` = ?,`determinacion29` = ?,`determinacion30` = ?,"
                    + "`determinacion31` = ?,`determinacion32` = ?,`determinacion33` = ?,`determinacion34` = ?,"
                    + "`recuentoObtenido1` = ?,`recuentoObtenido2` = ?,`recuentoObtenido3` = ?,`recuentoObtenido4` = ?,`recuentoObtenido5` = ?,`recuentoObtenido6` = ?,`recuentoObtenido7` = ?,`recuentoObtenido8` = ?,`recuentoObtenido9` = ?,`recuentoObtenido10` = ?,"
                    + "`recuentoObtenido11` = ?,`recuentoObtenido12` = ?,`recuentoObtenido13` = ?,`recuentoObtenido14` = ?,`recuentoObtenido15` = ?,`recuentoObtenido16` = ?,`recuentoObtenido17` = ?,`recuentoObtenido18` = ?,`recuentoObtenido19` = ?,`recuentoObtenido20` = ?,"
                    + "`recuentoObtenido21` = ?,`recuentoObtenido22` = ?,`recuentoObtenido23` = ?,`recuentoObtenido24` = ?,`recuentoObtenido25` = ?,`recuentoObtenido26` = ?,`recuentoObtenido27` = ?,`recuentoObtenido28` = ?,`recuentoObtenido29` = ?,`recuentoObtenido30` = ?,"
                    + "`recuentoObtenido31` = ?,`recuentoObtenido32` = ?,`recuentoObtenido33` = ?,`recuentoObtenido34` = ?,"
                    + "`recuentoNormal1` = ?,`recuentoNormal2` = ?,`recuentoNormal3` = ?,`recuentoNormal4` = ?,`recuentoNormal5` = ?,`recuentoNormal6` = ?,`recuentoNormal7` = ?,`recuentoNormal8` = ?,`recuentoNormal9` = ?,`recuentoNormal10` = ?,"
                    + "`recuentoNormal11` = ?,`recuentoNormal12` = ?,`recuentoNormal13` = ?,`recuentoNormal14` = ?,`recuentoNormal15` = ?,`recuentoNormal16` = ?,`recuentoNormal17` = ?,`recuentoNormal18` = ?,`recuentoNormal19` = ?,`recuentoNormal20` = ?,"
                    + "`recuentoNormal21` = ?,`recuentoNormal22` = ?,`recuentoNormal23` = ?,`recuentoNormal24` = ?,`recuentoNormal25` = ?,`recuentoNormal26` = ?,`recuentoNormal27` = ?,`recuentoNormal28` = ?,`recuentoNormal29` = ?,`recuentoNormal30` = ?,"
                    + "`recuentoNormal31` = ?,`recuentoNormal32` = ?,`recuentoNormal33` = ?,`recuentoNormal34` = ?,"
                    + "`metodo1` = ?,`metodo2` = ?,`metodo3` = ?,`metodo4` = ?,`metodo5` = ?,`metodo6` = ?,`metodo7` = ?,`metodo8` = ?,`metodo9` = ?,`metodo10` = ?,"
                    + "`metodo11` = ?,`metodo12` = ?,`metodo13` = ?,`metodo14` = ?,`metodo15` = ?,`metodo16` = ?,`metodo17` = ?,`metodo18` = ?,`metodo19` = ?,`metodo20` = ?,"
                    + "`metodo21` = ?,`metodo22` = ?,`metodo23` = ?,`metodo24` = ?,`metodo25` = ?,`metodo26` = ?,`metodo27` = ?,`metodo28` = ?,`metodo29` = ?,`metodo30` = ?,"
                    + "`metodo31` = ?,`metodo32` = ?,`metodo33` = ?,`metodo34` = ?, titulo = ?, mostrar = ? "
                    + "WHERE `idmuestras` = ?;");

            int index = 1;
// Determinaciones 1-34
            for (int i = 1; i <= 34; i++) {
                ps.setString(index++, String.valueOf(m.get("determinacion" + i)));
            }
// Recuentos Obtenidos 1-34
            for (int i = 1; i <= 34; i++) {
                ps.setString(index++, String.valueOf(m.get("recuentoObtenido" + i)));
            }
// Recuentos Normales 1-34
            for (int i = 1; i <= 34; i++) {
                ps.setString(index++, String.valueOf(m.get("recuentoNormal" + i)));
            }
// Metodos 1-34
            for (int i = 1; i <= 34; i++) {
                ps.setString(index++, String.valueOf(m.get("metodo" + i)));
            }

// Campos finales
            ps.setString(index++, String.valueOf(m.get("titulo")));
            ps.setString(index++, String.valueOf(m.get("mostrar")));
            ps.setInt(index, (int) m.get("idmuestras"));

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

    public boolean guardarResultadoMBAguaDeRecreacion(Resultados r, int vencimiento) {
        Connection conexion = con.getConnection();
        try {
            ps = conexion.prepareStatement("insert into MBAgua (idmuestras, "
                    + "germenes, coliformesTotales, coliformesFecales, escherichia, "
                    + "pseudomona, ph, cloroTotal, caracteresOrganolepticos, staphilococos, "
                    + "streptococos, cloroLibre, vencimiento, shigella) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            ps.setInt(1, r.getIdmuestras());
            ps.setString(2, r.getGermenes());
            ps.setString(3, r.getColiformesTotales());
            ps.setString(4, r.getColiformesFecales());
            ps.setString(5, r.getEscherichia());
            ps.setString(6, r.getPseudomona());
            ps.setDouble(7, r.getPh());
            ps.setDouble(8, r.getClorototal());
            ps.setString(9, r.getCaracteresOrgasnolepticos());
            ps.setString(10, r.getStaphilococos());
            ps.setString(11, r.getStreptococos());
            ps.setDouble(12, r.getCloroLibre());
            ps.setInt(13, vencimiento);
            ps.setString(14, r.getShigella());
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

    public double[] recuperarPhYCloro(int id) {
        Connection conexion = con.getConnection();
        double[] aux = {-1, -1, -1};
        try {
            ps = conexion.prepareStatement("select cloroLibre, cloroTotal,ph from mbagua where idmuestras = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                for (int i = 0; i < aux.length; i++) {
                    if (rs.getObject(i + 1) != null) {
                        aux[i] = rs.getDouble(i + 1);
                    }
                }
            }
            return aux;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al guardar datos, " + e);
            return null;
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
    }

    public boolean checkearResultadoMBAgua(int id) {
        Connection conexion = con.getConnection();
        try {
            ps = conexion.prepareStatement("select * from mbagua where idmuestras = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                return true;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al checkearResultadosMBAgua, " + e);
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
        return false;
    }

    public Map<String, String> recuperarResultadosMBAgua(int id) {
        Connection conexion = con.getConnection();
        Map valores = new HashMap();
        boolean nulo = true;
        try {
            ps = conexion.prepareStatement("select germenes, coliformesTotales, coliformesFecales, escherichia,"
                    + " pseudomona, caracteresOrganolepticos, fechaAnalisis, mohos, mohosLimite, shigella from "
                    + "mbagua where idmuestras = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();

            while (rs.next()) {
                for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                    if (rs.getObject(i + 1) != null) {
                        nulo = false;
                        valores.put(rs.getMetaData().getColumnName(i + 1), String.valueOf(rs.getObject(i + 1)));
                    }
                }
                return nulo ? null : valores;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al recuperar datos, " + e);
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
        return null;
    }

    public Map<String, String> recuperarResultadosMBAguaCOFES(int id) {
        Connection conexion = con.getConnection();
        Map<String, String> valores = new HashMap();
        try {
            ps = conexion.prepareStatement("select germenes, coliformesTotales, coliformesFecales, escherichia,"
                    + " pseudomona, caracteresOrganolepticos, fechaAnalisis, shigella from "
                    + "mbagua where idmuestras = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                for (int i = 0; i < ps.getMetaData().getColumnCount(); i++) {
                    if (rs.getObject(i + 1) == null) {
                        return null;
                    } else {
                        valores.put(rs.getMetaData().getColumnName(i + 1),
                                String.valueOf(rs.getObject(i + 1)));
                    }
                }
                return valores;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al recuperar datos, " + e);
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
        return null;
    }

    public Map<String, String> recuperarResultadosMBAguaDeRecreacion(int id) {
        Connection conexion = con.getConnection();
        Map<String, String> valores = new HashMap();
        try {
            ps = conexion.prepareStatement("select germenes, coliformesTotales, "
                    + "coliformesFecales, escherichia, pseudomona, staphilococos, "
                    + "streptococos, caracteresOrganolepticos, fechaanalisis, shigella from "
                    + "mbagua where idmuestras = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                for (int i = 0; i < ps.getMetaData().getColumnCount(); i++) {
                    valores.put(ps.getMetaData().getColumnName(i + 1),
                            String.valueOf(rs.getObject(i + 1)));
                }
                return valores;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al recuperar datos, " + e);
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
        return null;
    }

    public Map<String, String> recuperarResultadosFQAgua(int id) {
        Connection conexion = con.getConnection();
        Map<String, String> valores = new HashMap<>();
        try {
            ps = conexion.prepareStatement("select `ph`,`cloroTotal`,"
                    + "`olor`,`color`,`turbidez`,`alcalinidad`,`durezatotal`,`conductividad`,"
                    + "`solidosDisueltos`,`hierro`,`nitratos`,`nitritos`,`sulfatos` from"
                    + " `laboratorio`.`fqagua` where idmuestras = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                    valores.put(rs.getMetaData().getColumnName(i + 1), rs.getObject(i + 1) == null ? "" : rs.getObject(i + 1).toString());
                }
                System.out.println(valores.isEmpty() + " recuperar");
                return valores;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al recuperar datos, " + e);
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
        return null;
    }

    public boolean guardarResultadoFQAgua(Resultados r) {
        Connection conexion = con.getConnection();
        try {
            ps = conexion.prepareStatement("insert into fqagua (idmuestras, ph, cloroTotal, "
                    + "olor, color, turbidez, alcalinidad, durezatotal, conductividad, "
                    + "solidosDisueltos, hierro, nitratos, nitritos, sulfatos, conclusion)"
                    + " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            ps.setInt(1, r.getIdmuestras());
            ps.setDouble(2, r.getPh());
            ps.setDouble(3, r.getClorototal());
            ps.setString(4, r.getOlor());
            ps.setString(5, r.getColor());
            ps.setString(6, r.getTurbidez());
            ps.setDouble(7, r.getAlcalinidad());
            ps.setDouble(8, r.getDurezatotal());
            ps.setDouble(9, r.getConductividad());
            ps.setDouble(10, r.getSolidosdisueltos());
            ps.setString(11, r.getHierro());
            ps.setString(12, r.getNitratos());
            ps.setString(13, r.getNitritos());
            ps.setString(14, r.getSulfatos());
            ps.setString(15, r.getConclusion());

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

    public boolean guardarTablaNutricional(Double[] valores, int id, String unidad, String porcion, String porcionesPorEnvase) {
        Connection conexion = con.getConnection();
        try {
            ps = conexion.prepareStatement("INSERT INTO laboratorio.tablanutricional "
                    + "(idmuestras,calorias,kjul,carbohidratos,proteinas,grasasTotales,"
                    + "grasasSaturadas,grasasTrans,GrasasMonoinsaturadas,GrasasPoliinsaturadas,Colesterol,"
                    + "fibraAlimentaria,sodio,VDCalorias,VDCarbohidratos,VDProteinas,"
                    + "VDGrasasTotales,VDGrasasSaturadas,VDFibraAlimentaria,VDSodio, "
                    + "porcion, unidad, azucares, almidon, vdazucares, vdalmidon, PorcionesPorEnvase, azucaresAnadidos, vdazucaresAnadidos)"
                    + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            ps.setInt(1, id); //idmuestras
            ps.setInt(2, (int) Math.round(valores[0]));//calorias
            ps.setDouble(3, valores[1]);//kjul
            ps.setDouble(4, valores[2]);//carbohidratos
            ps.setDouble(5, valores[3]);//proteinas
            ps.setDouble(6, valores[4]);//grasasTotales
            ps.setDouble(7, valores[5]);//grasasSaturadas
            ps.setDouble(8, valores[6]);//grasasTrans
            ps.setDouble(9, valores[7]);//grasasMonoinsaturadas
            ps.setDouble(10, valores[8]);//grasasPoliinsaturadas
            ps.setDouble(11, valores[9]);//colesterol
            ps.setDouble(12, valores[10]);//fibraAlimentaria
            ps.setDouble(13, valores[11]);//sodio
            ps.setDouble(14, (int) Math.round(valores[12]));//vdcalorias
            ps.setDouble(15, (int) Math.round(valores[13]));//vdcarbohidratos
            ps.setDouble(16, (int) Math.round(valores[14]));//vdproteinas
            ps.setDouble(17, (int) Math.round(valores[15]));//vdgrasasTotales
            ps.setDouble(18, (int) Math.round(valores[16]));//vdGrasassaturadas
            ps.setDouble(19, (int) Math.round(valores[21]));//vdFibraalimentaria
            ps.setDouble(20, (int) Math.round(valores[22]));//vdSodio
            ps.setString(21, porcion);//porcion
            ps.setString(22, String.valueOf(valores[24]) + unidad);//unidad
            ps.setDouble(23, valores[25]);
            ps.setDouble(24, valores[26]);
            ps.setDouble(25, valores[27]);
            ps.setDouble(26, valores[28]);
            ps.setString(27, porcionesPorEnvase);
            ps.setDouble(28, valores[29]);
            ps.setDouble(29, valores[30]);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al guardar datos, " + e);
            return false;
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error al guardar TN, " + e);
            }
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
    }

    public String[] recuperarTablaNutricional(int id) {
        Connection conexion = con.getConnection();
        String[] valores = new String[30];
        try {
            ps = conexion.prepareStatement("select calorias, carbohidratos, "
                    + "proteinas, grasasTotales, grasasSaturadas, grasasTrans, "
                    + "grasasMonoinsaturadas, grasasPoliinsaturadas, colesterol, "
                    + "fibraAlimentaria, sodio, VDCalorias, VDCarbohidratos, "
                    + "VDProteinas, VDGrasasTotales, VDGrasasMonoinsaturadas, "
                    + "VDGrasasPoliinsaturadas, VDColesterol, VDGrasasSaturadas, "
                    + "VDGrasasTrans, VDFibraAlimentaria, VDSodio, porcion, unidad, "
                    + "kjul, azucares, almidon, PorcionesPorEnvase, azucaresanadidos, vdazucaresanadidos from tablanutricional "
                    + "where idmuestras = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                for (int i = 0; i < valores.length; i++) {
                    valores[i] = String.valueOf(rs.getObject(i + 1));
                    ;
                }
                return valores;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al recuperarTablaNutricional, " + e);
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
        return null;
    }

    public boolean editarTablaNutricional(Double[] valores, int id, String unidad, String porcion, String porcionesPorEnvase) {
        Connection conexion = con.getConnection();
        try {
            ps = conexion.prepareStatement("update tablanutricional set calorias = ?,"
                    + "kjul = ?,carbohidratos = ?,proteinas = ?,grasasTotales = ?,"
                    + "grasasSaturadas = ?,grasasTrans = ?, GrasasMonoinsaturadas = ?,"
                    + " GrasasPoliinsaturadas = ?, Colesterol = ?, fibraAlimentaria = ?,"
                    + "sodio = ?,VDCalorias = ?,VDCarbohidratos = ?,VDProteinas = ?"
                    + ",VDGrasasTotales = ?,VDGrasasSaturadas = ?, VDGrasasTrans = ?, "
                    + "VDGrasasMonoinsaturadas = ?, VDGrasasPoliinsaturadas = ?, VDColesterol = ?"
                    + ",VDFibraAlimentaria = ?,VDSodio = ?, porcion = ?, unidad = ?, azucares = ?, "
                    + "almidon = ?, PorcionesPorEnvase = ?, azucaresAnadidos = ?, VDAzucaresAnadidos = ? where idmuestras = ?");
            ps.setInt(1, (int) Math.round(valores[0]));
            ps.setDouble(2, valores[1]);
            ps.setDouble(3, valores[2]);
            ps.setDouble(4, valores[3]);
            ps.setDouble(5, valores[4]);
            ps.setDouble(6, valores[5]);
            ps.setDouble(7, valores[6]);
            ps.setDouble(8, valores[7]);
            ps.setDouble(9, valores[8]);
            ps.setDouble(10, valores[9]);
            ps.setDouble(11, valores[10]);
            ps.setDouble(12, valores[11]);
            ps.setDouble(13, valores[12]);
            ps.setDouble(14, valores[13]);
            ps.setDouble(15, valores[14]);
            ps.setDouble(16, valores[15]);
            ps.setDouble(17, valores[16]);
            ps.setDouble(18, valores[17]);
            ps.setDouble(19, valores[18]);
            ps.setDouble(20, valores[19]);
            ps.setDouble(21, valores[20]);
            ps.setDouble(22, valores[21]);
            ps.setDouble(23, valores[22]);
            ps.setString(24, porcion);
            ps.setString(25, unidad);
            ps.setDouble(26, valores[25]);
            ps.setDouble(27, valores[26]);
            ps.setString(28, porcionesPorEnvase);
            ps.setDouble(29, valores[29]);
            ps.setDouble(30, valores[30]);
            ps.setInt(31, id);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al editar datos, " + e);
            return false;
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error al  TN, " + e);
            }
        }
    }

    public String[] recuperarDatosFQCompleto(int id) {
        Connection conexion = con.getConnection();
        String aux[] = new String[73];
        try {
            ps = conexion.prepareStatement(recuperarFQ);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                for (int i = 0; i < aux.length; i++) {
                    Object auxx = rs.getObject(i + 1);
                    if (i == 0) {
                    }
                    if (auxx == null) {
                        aux[i] = "-1";
                    } else {
                        aux[i] = String.valueOf(rs.getObject(i + 1));
                    }
                }
            }
            return aux;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al recuperarDatosFQCompleto, " + e);
            return null;
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error al recuperar datos FQ, " + e);
            }
        }
    }

    public boolean editarFQAgua(Resultados r) {
        Connection conexion = con.getConnection();
        try {
            ps = conexion.prepareStatement("UPDATE `laboratorio`.`fqagua` "
                    + "SET `ph` = ? , `cloroTotal` = ? , `olor` = ? , `color` = ? ,"
                    + " `turbidez` = ? , `alcalinidad` = ? , `durezatotal` = ? , "
                    + "`conductividad` = ? , `solidosDisueltos` = ? , `hierro` = ?, "
                    + "`nitratos` = ? , `nitritos` = ? , `sulfatos` = ?"
                    + "  WHERE `idmuestras` = ? ; ");
            ps.setDouble(1, r.getPh());
            ps.setDouble(2, r.getClorototal());
            ps.setString(3, r.getOlor());
            ps.setString(4, r.getColor());
            ps.setString(5, r.getTurbidez());
            ps.setDouble(6, r.getAlcalinidad());
            ps.setDouble(7, r.getDurezatotal());
            ps.setDouble(8, r.getConductividad());
            ps.setDouble(9, r.getSolidosdisueltos());
            ps.setString(10, r.getHierro());
            ps.setString(11, r.getNitratos());
            ps.setString(12, r.getNitritos());
            ps.setString(13, r.getSulfatos());
            ps.setInt(14, r.getIdmuestras());
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al editar datos, " + e);
            return false;
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
    }

    public boolean editarMBAgua(Resultados r) {
        Connection conexion = con.getConnection();
        try {
            ps = conexion.prepareStatement("UPDATE `laboratorio`.`mbagua` SET `germenes` = ? "
                    + ", `coliformesTotales` = ? , `coliformesFecales` = ?, `escherichia` = ? , `pseudomona` = ?, "
                    + "ph = ?, cloroLibre = ?, caracteresOrganolepticos = ?, mohos = ?, fechaAnalisis = ?, shigella = ?"
                    + " WHERE `idmuestras` = ? ;");
            ps.setString(1, r.getGermenes());
            ps.setString(2, r.getColiformesTotales());
            ps.setString(3, r.getColiformesFecales());
            ps.setString(4, r.getEscherichia());
            ps.setString(5, r.getPseudomona());
            ps.setDouble(6, r.getPh());
            ps.setDouble(7, r.getClorototal());
            ps.setString(8, r.getCaracteresOrgasnolepticos());
            ps.setString(9, r.getMohos());
            ps.setDate(10, r.getFechaAnalisis());
            ps.setString(11, r.getShigella());
            ps.setInt(12, r.getIdmuestras());
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al editar datos, " + e);
            return false;
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
    }

    public boolean guardarResultadosEfluentes(String[] resultados, int id) {
        Connection conexion = con.getConnection();
        try {
            ps = conexion.prepareStatement("INSERT INTO `laboratorio`.`efluentes` "
                    + "(idmuestras,ph,dqo,dbo,solidos10,solidos120,"
                    + "conclusion, hidrocarburos) values (?,?,?,?,?,?,?,?)");
            ps.setInt(1, id);
            ps.setDouble(2, Double.parseDouble(resultados[1]));
            ps.setDouble(3, Double.parseDouble(resultados[2]));
            ps.setDouble(4, Double.parseDouble(resultados[3]));
            ps.setString(5, resultados[4]);
            ps.setString(6, resultados[5]);
            ps.setString(7, resultados[6]);
            ps.setString(8, resultados[7]);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al guardar datos, " + e);
            return false;
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error al guardar efluentes, " + e);
            }
        }
    }

    public boolean editarEfluentes(String[] resultados, int id) {
        Connection conexion = con.getConnection();
        try {
            ps = conexion.prepareStatement("UPDATE `laboratorio`.`efluentes` SET `ph` = ? "
                    + ", `dqo` = ? , `dbo` = ? , `solidos10` = ? "
                    + ",`solidos120` = ?, conclusion = ?, hidrocarburos = ?  WHERE `idmuestras` = ? ;");
            ps.setDouble(1, Double.parseDouble(resultados[1]));
            ps.setDouble(2, Double.parseDouble(resultados[2]));
            ps.setDouble(3, Double.parseDouble(resultados[3]));
            ps.setString(4, resultados[4]);
            ps.setString(5, resultados[5]);
            ps.setString(6, resultados[6]);
            ps.setString(7, resultados[7]);
            ps.setInt(8, id);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al editar datos, " + e);
            return false;
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
    }

    public void generarReporteEfluentes(int id, String procedencia) {
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

    public String obtenerLugarMuestreo(int id) {
        Connection conexion = con.getConnection();
        try {
            ps = conexion.prepareStatement("select lugarMuestreo from muestras "
                    + "where idmuestras = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getString("lugarMuestreo");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al obtenerLugarMuestreo, " + e);
            return null;
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
        return null;
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

    public boolean guardarResultadoBaseHelada(Map m) {
        Connection conexion = con.getConnection();
        try {
            ps = conexion.prepareStatement("INSERT INTO `laboratorio`.`mbchocolates`"
                    + "(`idmuestras`,`germenes`, `coliformesTotales`,"
                    + "`coliformesFecales`, `escherichia`,`mohos`, conclusion, staphilococos, salmonella) "
                    + "VALUES (?,?,?,?,?,?,?,?,?)");
            ps.setInt(1, (int) m.get("idmuestras"));
            ps.setString(2, String.valueOf(m.get("germenes")));
            ps.setString(3, String.valueOf(m.get("coliformesTotales")));
            ps.setString(4, String.valueOf(m.get("coliformesFecales")));
            ps.setString(5, String.valueOf(m.get("escherichia")));
            ps.setString(6, String.valueOf(m.get("mohos")));
            ps.setString(7, String.valueOf(String.valueOf(m.get("conclusion"))));
            ps.setString(8, String.valueOf(m.get("staphilococos")));
            ps.setString(9, String.valueOf(m.get("salmonella")));
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

    public boolean editarResultadoBaseHelada(Map m) {
        Connection conexion = con.getConnection();
        try {
            ps = conexion.prepareStatement("update mbchocolates set germenes = ?, "
                    + "coliformesTotales = ?, coliformesFecales = ?, escherichia = ?, "
                    + "mohos = ?, conclusion = ?,"
                    + "staphilococos = ?, salmonella = ? where idmuestras = ?");
            ps.setString(1, String.valueOf(m.get("germenes")));
            ps.setString(2, String.valueOf(m.get("coliformesTotales")));
            ps.setString(3, String.valueOf(m.get("coliformesFecales")));
            ps.setString(4, String.valueOf(m.get("escherichia")));
            ps.setString(5, String.valueOf(m.get("mohos")));
            ps.setString(6, String.valueOf(String.valueOf(m.get("conclusion"))));
            ps.setString(7, String.valueOf(m.get("staphilococos")));
            ps.setString(8, String.valueOf(m.get("salmonella")));
            ps.setInt(9, (int) m.get("idmuestras"));
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

    public boolean guardarResultadoMBChocolates(Map m) {
        Connection conexion = con.getConnection();
        try {
            ps = conexion.prepareStatement("INSERT INTO `laboratorio`.`mbchocolates`"
                    + "(`idmuestras`,`germenes`, `coliformesTotales`,"
                    + "`coliformesFecales`, `escherichia`,`mohos`, conclusion, salmonella) "
                    + "VALUES (?,?,?,?,?,?,?,?)");
            ps.setInt(1, (int) m.get("idmuestras"));
            ps.setString(2, String.valueOf(m.get("germenes")));
            ps.setString(3, String.valueOf(m.get("coliformesTotales")));
            ps.setString(4, String.valueOf(m.get("coliformesFecales")));
            ps.setString(5, String.valueOf(m.get("escherichia")));
            ps.setString(6, String.valueOf(m.get("mohos")));
            ps.setString(7, String.valueOf(m.get("conclusion")));
            ps.setString(8, String.valueOf(m.get("salmonella")));
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

    public boolean editarResultadoMBChocolates(Map m) {
        Connection conexion = con.getConnection();
        try {
            ps = conexion.prepareStatement("update mbchocolates set germenes = ?, "
                    + "coliformesTotales = ?, coliformesFecales = ?, escherichia = ?, "
                    + "mohos = ?, conclusion = ?, salmonella = ? where idmuestras = ?");
            ps.setString(1, String.valueOf(m.get("germenes")));
            ps.setString(2, String.valueOf(m.get("coliformesTotales")));
            ps.setString(3, String.valueOf(m.get("coliformesFecales")));
            ps.setString(4, String.valueOf(m.get("escherichia")));
            ps.setString(5, String.valueOf(m.get("mohos")));
            ps.setString(6, String.valueOf(m.get("conclusion")));
            ps.setString(7, String.valueOf(m.get("salmonella")));
            ps.setInt(8, (int) m.get("idmuestras"));
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al editar datos, " + e);
            e.printStackTrace();
            return false;
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
    }

    public void guardarObservaciones(String observaciones, int id) {
        Connection conexion = con.getConnection();
        try {
            ps = conexion.prepareStatement("update muestras set observaciones = ? where idmuestras = ?");
            ps.setString(1, observaciones);
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

    public boolean editarMBAlimentos(Map m) {
        Connection conexion = con.getConnection();
        try {
            ps = conexion.prepareStatement("update mbalimentos set coliformesTotales = ?, "
                    + "coliformesFecales = ?, staphilococos = ?, salmonella = ?, mohosLevadura = ?, "
                    + "escherichia = ?, germenes = ?, coliformesTotalesMetodo = ?, "
                    + "coliformesFecalesMetodo = ?, staphilococosMetodo = ?, salmonellaMetodo = ?, mohosLevaduraMetodo = ?, "
                    + "escherichiaMetodo = ?, germenesMetodo = ?, escherichiah7 = ?,"
                    + "escherichia157 = ?, enterobacterias = ?, listeria = ?, bacillus = ?,"
                    + "perfringens = ?, sulfito = ?, campilobacter = ?, escherichiah7Metodo = ?,"
                    + "escherichia157Metodo = ?, enterobacteriasMetodo = ?, listeriaMetodo = ?, bacillusMetodo = ?,"
                    + "perfringensMetodo = ?, sulfitoMetodo = ?, campilobacterMetodo = ?, "
                    + "caracteristicas = ?, caracteristicasMetodo = ?, coliformesTotalesA30 = ?,coliformesTotalesA30Metodo = ?,"
                    + " coliformesTotalesProbables = ?,coliformesTotalesProbablesMetodo = ?, "
                    + "lactobacillus = ?, lactobacillusMetodo = ?, bacteriasLacticas = ?, bacteriasLacticasMetodo = ?,"
                    + " coliformesTotales45 = ?, coliformesTotales45Metodo = ?, vibrio = ?, vibrioMetodo = ?, vibrioCholerae = ?, vibrioCholeraeMetodo = ?,"
                    + "shigella = ?, shigellaMetodo = ? where idmuestras = ?");
            ps.setString(1, String.valueOf(m.get("coliformesTotales")));
            ps.setString(2, String.valueOf(m.get("coliformesFecales")));
            ps.setString(3, String.valueOf(m.get("staphilococos")));
            ps.setString(4, String.valueOf(m.get("salmonella")));
            ps.setString(5, String.valueOf(m.get("mohosLevaduras")));
            ps.setString(6, String.valueOf(m.get("escherichia")));
            ps.setString(7, String.valueOf(m.get("germenes")));
            ps.setString(8, String.valueOf(m.get("metodoColiformesTotales")));
            ps.setString(9, String.valueOf(m.get("metodoColiformesFecales")));
            ps.setString(10, String.valueOf(m.get("metodoStaphilococos")));
            ps.setString(11, String.valueOf(m.get("metodoSalmonella")));
            ps.setString(12, String.valueOf(m.get("metodoMohos")));
            ps.setString(13, String.valueOf(m.get("metodoEscherichia")));
            ps.setString(14, String.valueOf(m.get("metodoGermenes")));
            ps.setString(15, String.valueOf(m.get("escherichiaH7")));
            ps.setString(16, String.valueOf(m.get("escherichia157")));
            ps.setString(17, String.valueOf(m.get("enterobacterias")));
            ps.setString(18, String.valueOf(m.get("listeria")));
            ps.setString(19, String.valueOf(m.get("bacillus")));
            ps.setString(20, String.valueOf(m.get("perfringens")));
            ps.setString(21, String.valueOf(m.get("sulfito")));
            ps.setString(22, String.valueOf(m.get("campilobacter")));
            ps.setString(23, String.valueOf(m.get("metodoEscherichiaH7")));
            ps.setString(24, String.valueOf(m.get("metodoEscherichia157")));
            ps.setString(25, String.valueOf(m.get("metodoEnterobacterias")));
            ps.setString(26, String.valueOf(m.get("metodoListeria")));
            ps.setString(27, String.valueOf(m.get("metodoBacillus")));
            ps.setString(28, String.valueOf(m.get("metodoPerfringens")));
            ps.setString(29, String.valueOf(m.get("metodoSulfito")));
            ps.setString(30, String.valueOf(m.get("metodoCampilobacter")));
            ps.setString(31, String.valueOf(m.get("caracteristicas")));
            ps.setString(32, String.valueOf(m.get("metodoCaracteristicas")));
            ps.setString(33, String.valueOf(m.get("coliformesTotalesA30")));
            ps.setString(34, String.valueOf(m.get("metodoColiformesTotalesA30")));
            ps.setString(35, String.valueOf(m.get("coliformesTotalesProbables")));
            ps.setString(36, String.valueOf(m.get("metodoColiformesTotalesProbables")));
            ps.setString(37, String.valueOf(m.get("lactobacillus")));
            ps.setString(38, String.valueOf(m.get("metodoLactobacillus")));
            ps.setString(39, String.valueOf(m.get("bacteriasLacticas")));
            ps.setString(40, String.valueOf(m.get("metodoBacteriasLacticas")));
            ps.setString(41, String.valueOf(m.get("coliformesTotales45")));
            ps.setString(42, String.valueOf(m.get("metodoColiformesTotales45")));
            ps.setString(43, String.valueOf(m.get("vibrio")));
            ps.setString(44, String.valueOf(m.get("metodoVibrio")));
            ps.setString(45, String.valueOf(m.get("vibrioCholerae")));
            ps.setString(46, String.valueOf(m.get("metodoVibrioCholerae")));
            ps.setString(47, String.valueOf(m.get("shigella")));
            ps.setString(48, String.valueOf(m.get("metodoShigella")));
            ps.setInt(49, (int) m.get("idmuestras"));
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al editar datos, " + e);
            return false;
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
    }

    public boolean guardarResultadoMBAlimentos(Map m) {
        Connection conexion = con.getConnection();
        try {
            ps = conexion.prepareStatement("INSERT INTO `laboratorio`.`mbalimentos` "
                    + "(`idmuestras`, `coliformesTotales`, coliformesFecales, `staphilococos`, "
                    + "`salmonella`, `mohosLevadura`,`escherichia`, germenes,`coliformesTotalesMetodo`, "
                    + "coliformesFecalesMetodo, `staphilococosMetodo`, `salmonellaMetodo`, `mohosLevaduraMetodo`,"
                    + "`escherichiaMetodo`, germenesMetodo, `escherichiah7`, `escherichia157`, "
                    + "`enterobacterias`, `listeria`, `bacillus`, `perfringens`, "
                    + "`sulfito`, `campilobacter`, `escherichiah7Metodo`, `escherichia157Metodo`, "
                    + "`enterobacteriasMetodo`, `listeriaMetodo`, `bacillusMetodo`, "
                    + "`perfringensMetodo`, `sulfitoMetodo`, `campilobacterMetodo`, caracteristicas, caracteristicasMetodo, "
                    + "coliformesTotalesA30, coliformesTotalesA30Metodo, coliformesTotalesProbables, coliformesTotalesProbablesMetodo,"
                    + "lactobacillus, lactobacillusMetodo, bacteriasLacticas, bacteriasLacticasMetodo, coliformesTotales45, coliformesTotales45Metodo, vibrio, vibrioMetodo, vibrioCholerae, vibrioCholeraeMetodo, shigella, shigellaMetodo) "
                    + "VALUES (?,?,?,?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            ps.setInt(1, (int) m.get("idmuestras"));
            ps.setString(2, String.valueOf(m.get("coliformesTotales")));
            ps.setString(3, String.valueOf(m.get("coliformesFecales")));
            ps.setString(4, String.valueOf(m.get("staphilococos")));
            ps.setString(5, String.valueOf(m.get("salmonella")));
            ps.setString(6, m.get("mohosLevaduras").toString());
            ps.setString(7, String.valueOf(m.get("escherichia")));
            ps.setString(8, String.valueOf(m.get("germenes")));
            ps.setString(9, String.valueOf(m.get("metodoColiformesTotales")));
            ps.setString(10, String.valueOf(m.get("metodoColiformesFecales")));
            ps.setString(11, String.valueOf(m.get("metodoStaphilococos")));
            ps.setString(12, String.valueOf(m.get("metodoSalmonella")));
            ps.setString(13, String.valueOf(m.get("metodoMohos")));
            ps.setString(14, String.valueOf(m.get("metodoEscherichia")));
            ps.setString(15, String.valueOf(m.get("metodoGermenes")));
            ps.setString(16, String.valueOf(m.get("escherichiaH7")));
            ps.setString(17, String.valueOf(m.get("escherichia157")));
            ps.setString(18, String.valueOf(m.get("enterobacterias")));
            ps.setString(19, String.valueOf(m.get("listeria")));
            ps.setString(20, String.valueOf(m.get("bacillus")));
            ps.setString(21, String.valueOf(m.get("perfringens")));
            ps.setString(22, String.valueOf(m.get("sulfito")));
            ps.setString(23, String.valueOf(m.get("campilobacter")));
            ps.setString(24, String.valueOf(m.get("metodoEscherichiaH7")));
            ps.setString(25, String.valueOf(m.get("metodoEscherichia157")));
            ps.setString(26, String.valueOf(m.get("metodoEnterobacterias")));
            ps.setString(27, String.valueOf(m.get("metodoListeria")));
            ps.setString(28, String.valueOf(m.get("metodoBacillus")));
            ps.setString(29, String.valueOf(m.get("metodoPerfringens")));
            ps.setString(30, String.valueOf(m.get("metodoSulfito")));
            ps.setString(31, String.valueOf(m.get("metodoCampilobacter")));
            ps.setString(32, String.valueOf(m.get("caracteristicas")));
            ps.setString(33, String.valueOf(m.get("metodoCaracteristicas")));
            ps.setString(34, String.valueOf(m.get("coliformesTotalesA30")));
            ps.setString(35, String.valueOf(m.get("metodoColiformesTotalesA30")));
            ps.setString(36, String.valueOf(m.get("coliformesTotalesProbables")));
            ps.setString(37, String.valueOf(m.get("metodoColiformesTotalesProbables")));
            ps.setString(38, String.valueOf(m.get("lactobacillus")));
            ps.setString(39, String.valueOf(m.get("metodoLactobacillus")));
            ps.setString(40, String.valueOf(m.get("bacteriasLacticas")));
            ps.setString(41, String.valueOf(m.get("metodoBacteriasLacticas")));
            ps.setString(42, String.valueOf(m.get("coliformesTotales45")));
            ps.setString(43, String.valueOf(m.get("metodoColiformesTotales45")));
            ps.setString(44, String.valueOf(m.get("vibrio")));
            ps.setString(45, String.valueOf(m.get("metodoVibrio")));
            ps.setString(46, String.valueOf(m.get("vibrioCholerae")));
            ps.setString(47, String.valueOf(m.get("metodoVibrioCholerae")));
            ps.setString(48, String.valueOf(m.get("shigella")));
            ps.setString(49, String.valueOf(m.get("metodoShigella")));
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

    public boolean verificarVacioMuestras() {
        Connection conexion = con.getConnection();
        try {
            ps = conexion.prepareStatement("select * from muestras");
            rs = ps.executeQuery();
            while (rs.next()) {
                return false;
            }
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al verificar datos, " + e);
            return true;
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
    }

    public Map recuperarResultadosMBAlimentos(int id) {
        Connection conexion = con.getConnection();
        Map map = new HashMap();
        try {
            ps = conexion.prepareStatement("select coliformesTotales, coliformesFecales, escherichia, "
                    + "staphilococos, salmonella, mohosLevaduras, fechaAnalisis, germenes, "
                    + "coliformesTotalesMetodo, coliformesFecalesMetodo, escherichiaMetodo, "
                    + "staphilococosMetodo, salmonellaMetodo, mohosLevadurasMetodo, germenesMetodo, "
                    + "`escherichiah7`, `escherichia157`, "
                    + "`enterobacterias`, `listeria`, `bacillus`, `perfringens`, "
                    + "`sulfito`, `campilobacter`, `escherichiah7Metodo`, `escherichia157Metodo`, "
                    + "`enterobacteriasMetodo`, `listeriaMetodo`, `bacillusMetodo`, "
                    + "`perfringensMetodo`, `sulfitoMetodo`, `campilobacterMetodo`, caracteristicas, caracteristicasMetodo,"
                    + "coliformesTotalesA30, coliformesTotalesProbables,coliformesTotalesA30Metodo, coliformesTotalesProbablesMetodo,"
                    + " lactobacillus, lactobacillusMetodo, bacteriasLacticas, bacteriasLacticasMetodo, coliformesTotales45, coliformesTotales45Metodo,"
                    + "vibrio, vibrioMetodo, vibrioCholerae, vibrioCholeraeMetodo, shigella, shigellaMetodo"
                    + " from vistambalimentos where idmuestras = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                    if (rs.getObject(i + 1) == null) {
                        map.put(rs.getMetaData().getColumnName(i + 1), "");
                    } else {
                        map.put(rs.getMetaData().getColumnName(i + 1), rs.getObject(i + 1).toString());
                    }
                }
                return map;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al recuperarResultadosMBAlimentos, " + e);
            return null;
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
        return null;
    }

    public boolean guardarPhYCloro(Map m) {
        Connection conexion = con.getConnection();
        String[] aux = new String[31];
        try {
            ps = conexion.prepareStatement("insert into mbagua (ph, cloroLibre, cloroTotal,idmuestras) values (?,?,?,?)");
            ps.setString(1, String.valueOf(m.get("ph")));
            ps.setString(2, String.valueOf(m.get("libre")));
            ps.setString(3, String.valueOf(m.get("total")));
            ps.setInt(4, Integer.parseInt(String.valueOf(m.get("id"))));
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

    public boolean editarPhYCloro(Map m) {
        Connection conexion = con.getConnection();
        String[] aux = new String[31];
        try {
            ps = conexion.prepareStatement("update mbagua set ph = ?, cloroLibre = ?, cloroTotal = ? where idmuestras = ?");
            ps.setString(1, String.valueOf(m.get("ph")));
            ps.setString(2, String.valueOf(m.get("libre")));
            ps.setString(3, String.valueOf(m.get("total")));
            ps.setInt(4, Integer.parseInt(String.valueOf(m.get("id"))));
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

    public Date recuperarFechaAnalisis(int id) {
        Connection conexion = con.getConnection();
        Date d;
        try {
            ps = conexion.prepareStatement("select fechaAnalisis from muestras where idmuestras = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                d = rs.getDate("fechaAnalisis");
                return d;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al recuperarFechaAnalisis, " + e);
            return null;
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
        return null;
    }

    public Map<String, String> recuperarResultadosMBChocolates(int id) {
        Connection conexion = con.getConnection();
        Map<String, String> aux = new HashMap();
        try {
            ps = conexion.prepareStatement("select germenes,coliformesTotales,"
                    + " coliformesFecales, escherichia, mohos, salmonella"
                    + " from mbchocolates where idmuestras = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                    aux.put(rs.getMetaData().getColumnName(i + 1), rs.getObject(i + 1) == null ? "" : rs.getObject(i + 1).toString());
                }
                return aux;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al recuperarResultadosMBChocolates, " + e);
            return null;
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
        return null;
    }

    public String recuperarObservaciones(int id) {
        Connection conexion = con.getConnection();
        String o = "";
        try {
            ps = conexion.prepareStatement("select observaciones from muestras where idmuestras = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                o = rs.getString("observaciones");
            }
            return o;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al recuperarObservaciones, " + e);
            return null;
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
    }

    public Map<String, String> recuperarResultadosEfluentes(int id) {
        Connection conexion = con.getConnection();
        Map<String, String> aux = new HashMap();
        try {
            ps = conexion.prepareStatement("select ph, dqo, dbo, solidos10, solidos120, "
                    + "hidrocarburos from efluentes where idmuestras = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                    aux.put(rs.getMetaData().getColumnName(i + 1), rs.getObject(i + 1) == null ? "" : rs.getObject(i + 1).toString());
                }
                return aux;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al recuperarResultadosEfluentes, " + e);
            return null;
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
        return null;
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
            String ruta = recuperarRutas("Reportes") + org.ignaciorodriguez.utils.SeparatorUtils.s + tipo + " " + recuperarProcedencia(idcliente)
                    + " desde " + formatter.format(desdeSql)
                    + " hasta " + formatter.format(hastaSql) + ".xlsx";
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
        XDDFDataSource<String> xs = XDDFDataSourcesFactory.fromStringCellRange((XSSFSheet) sheetHidden,
                new CellRangeAddress(0, rowNumGraph - 1, colInicio, colInicio));

        XDDFNumericalDataSource<Double> ys = XDDFDataSourcesFactory.fromNumericCellRange((XSSFSheet) sheetHidden,
                new CellRangeAddress(0, rowNumGraph - 1, colInicio + 1, colInicio + 1));

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

        org.openxmlformats.schemas.drawingml.x2006.chart.CTDLbls dLbls
                = chart.getCTChart().getPlotArea().getLineChartArray(0).getSerArray(0).getDLbls();

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
                String[] parametros = {
                        "ph", "dqo", "dbo", "solidos10", "solidos120", "detergentes",
                        "grasas", "fosforo", "nitrogeno", "sustancias", "coliformesTotales",
                        "coliformesFecales", "escherichia", "conductividad", "hidrocarburos",
                        "nitratos", "cloro", "sulfuros"
                };
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
        String sql = "SELECT vistatabla_idmuestras, vistatabla_fechaMuestreo, vistatabla_porcentajeTotalCloro, "
                + "vistatabla_ph, germenes, coliformesTotales, coliformesFecales, escherichia, pseudomona, s"
                + "higella FROM vistambagua WHERE vistatabla_idmuestras in (select idmuestras from muestras where "
                + "idcliente = ? and tipo = ?) and vistatabla_fechaMuestreo BETWEEN ? AND ?";
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
        String sql = "SELECT vistatabla_idmuestras, vistatabla_fechaMuestreo, vistatabla_porcentajeTotalCloro, "
                + "vistatabla_ph, germenes, coliformesTotales, coliformesFecales, escherichia, pseudomona, mohos, s"
                + "higella FROM vistambagua WHERE vistatabla_idmuestras in (select idmuestras from muestras where "
                + "idcliente = ? and tipo = ?) and vistatabla_fechaMuestreo BETWEEN ? AND ?";
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
        String sql = "SELECT d.idmuestras, vd.fechaAnalisis, d.acidez, d.acidoCianurico, d.acidoSorbico, d.actividadAcuosa, d.alcalinidad, d.alcohol, "
                + "d.aluminio, d.amonio, d.amoniacos, d.antimonio, d.aroma, d.arsenico, d.asbesto, d.aspecto, d.azucares, d.azucaresDeducidas, "
                + "d.azucaresInvertidos, d.azucaresReductores, d.bario, d.bicarbonatos, d.boro, d.bromuro, d.cadmioTotal, d.calcio, d.caracteristicas, "
                + "d.carbonatos, d.cenizas, d.cenizasInsolublesAcido, d.cenizasInsolublesAgua, d.cianuros, d.cloroActivo, d.cloroResidual, d.cloroTotal, "
                + "d.cloruros, d.cobalto, d.cobre, d.colesterol, d.color, d.colorantesartificiales, d.colorantesnaturales, d.colorantes, d.conductividad, "
                + "d.cromoHexavalente, d.dbo, d.detergentes, d.dqo, d.dureza, d.edulcorantes, d.estano, d.extracto, d.extractoseco, d.fenoles, d.fluoruros, "
                + "d.fluor, d.fosfatos, d.fosforoTotal, d.gliadinas, d.gluten, d.gradoFermentacion, d.gradosBrix, d.grasa, d.grasasCacao, d.grasasLeche, "
                + "d.grasasyAceites, d.hidracina, d.hidrocarburos, d.hidrocarburosc6, d.hidrocarburosc6_c35, d.hidrocarburosc6_c8, d.hidrocarburosc8_c10, "
                + "d.hidrocarburosc10_c12, d.hidrocarburosc12_c16, d.hidrocarburosc16_c21, d.hidrocarburosc21_c35, d.hierro, d.humedad, d.magnesio, "
                + "d.manganeso, d.materiagrasa, d.mercurioTotal, d.molibdeno, d.nitratos, d.nitritos, d.nitrogenoAmoniacal, d.nitrogenoTotal, d.niquel, "
                + "d.observacionMicroscopica, d.olor, d.organoclorados, d.oxigenoDisuelto, d.ozono, d.peroxidoHidrogeno, d.ph, d.plata, d.plomo, "
                + "d.porcentajeCloruro, d.potasio, d.propionato, d.relacion, d.residuo105, d.residuo180, d.residuoSeco, d.sabor, d.selenio, d.silicatos, "
                + "d.sodio, d.sulfatos, d.sulfuros, d.sustancias, d.sustanciasEterEtilico, d.solidosTotales, d.solidosNoGrasosCacao, d.solidosNoGrasos, "
                + "d.solidos10Minutos, d.solidos2Horas, d.solidosSuspendidosTotales, d.solidosSuspendidosVolatiles, d.temperatura, d.turbidez, d.vanadio, "
                + "d.vibrio, d.zinc FROM determinaciones d join vistafqcompleto vd on d.idmuestras = vd.idmuestras WHERE d.idmuestras IN "
                + "(SELECT idmuestras FROM muestras WHERE idcliente = ? AND tipo = ? AND fechaAnalisis BETWEEN ? AND ?)";

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
        java.util.regex.Matcher m = java.util.regex.Pattern
                .compile("=\\s*([0-9]+(?:[.,][0-9]+)?)")
                .matcher(valor);
        if (m.find()) {
            return Double.parseDouble(m.group(1).replace(",", "."));
        }

        // Caso 2: no tiene "=" -> "<LC 5.0 mg/kg" o "texto antes <LC 5.0"
        m = java.util.regex.Pattern
                .compile("lc\\D*([0-9]+(?:[.,][0-9]+)?)", java.util.regex.Pattern.CASE_INSENSITIVE)
                .matcher(valor);
        if (m.find()) {
            return Double.parseDouble(m.group(1).replace(",", "."));
        }

        // Fallback: si no hay "lc" en absoluto, intenta extraer cualquier número de la cadena
        return extraerNumero(valor);
    }

    private void consultarMBAlimentosParaExcel(int idcliente, String tipo, java.sql.Date desdeSql, java.sql.Date hastaSql, Sheet sheet, Sheet sheetHidden, int[] rowNum, int[] rowCont, Workbook workbook) {
        Connection conexion = con.getConnection();
        String sql = "SELECT idmuestras, fechaAnalisis, germenes, coliformesTotales, coliformesFecales, escherichia, "
                + "escherichiah7, escherichia157, enterobacterias, staphilococos, mohosLevaduras, salmonella, listeria, "
                + "bacillus, perfringens, sulfito, campilobacter, coliformesTotalesA30, coliformesTotalesProbables, "
                + "caracteristicas, lactobacillus, bacteriasLacticas, coliformesTotales45, vibrio, shigella, vibrioCholerae "
                + "FROM vistambalimentos WHERE idmuestras IN (SELECT idmuestras FROM muestras WHERE idcliente = ? AND tipo = ?) "
                + "AND fechaAnalisis BETWEEN ? AND ?";

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
                return new String[]{"ID", "Fecha", " pH", "DEMANDA QUÍMICA DE OXÍGENO (DQO)", "DEMANDA BIOQUÍMICA DE OXÍGENO (DBO)",
                        "SÓLIDOS DISUELTOS SEDIMENTABLES EN 10’", "SÓLIDOS DISUELTOS SEDIMENTABLES EN 120’", "DETERGENTES", "GRASAS Y ACEITES",
                        "FÒSFORO TOTAL", "NITRÓGENO TOTAL", "SUSTANCIAS SOLUBLES EN ETER ETILICO", "COLIFORMES TOTALES",
                        "COLIFORMES FECALES", "ESCHERICHIA COLI", "CONDUCTIVIDAD", "HIDROCARBUROS TOTALES DE PETRÓLEO (IR)",
                        "NITRATOS", "CLORO", "SULFUROS"};
            case MBAGUACODIGO:
                return new String[]{"ID", "FECHA", "% Total Libre", "pH", "GERMENES AEROBIOS TOTALES", "COLIFORMES TOTALES", "COLIFORMES FECALES",
                        "ESCHERICHIA COLI", "PSEUDOMONA AERUGINOSA", "MOHOS Y LEVADURAS", "SHIGELLA"};
            case MBALIMENTOS:
                return new String[]{"ID", "FECHA", "GERMENES AEROBIOS TOTALES", "COLIFORMES TOTALES", "COLIFORMES FECALES", "ESCHERICHIA COLI",
                        "ESCHERICHIA COLI O 157 H7", "ESCHERICHIA COLI NO O 157", "ENTEROBACTERIAS", "STAPHILOCOCOS AUREUS COAGULASA (+)", "MOHOS Y LEVADURAS",
                        "SALMONELLA sp", "LISTERIA MONOCYTOGENES", "BACILLUS CEREUS", "CLOSTRIDIUM PERFRINGENS", "CLOSTRIDIUM SULFITO REDUCTORES Ó ANAEROBIOS",
                        "CAMPILOBACTER", "COLIFORMES TOTALES A 30°C", "COLIFORMES TOTALES POR NÚMERO MÁS PROBABLE", "CARACTERISTICAS ORGANOLEPTICAS", "RECUENTO DE LACTOBACILLUS",
                        "RECUENTO DE BACTERIAS LÁCTICAS", "RECUENTO DE COLIFORMES TOTALES A 45°C", "VIBRIO PARAHEMOLITYCUS", "SHIGELLA", "VIBRIO CHOLERAE"};
            case MBAGUACOFES:
                return new String[]{"ID", "FECHA", "% Total Libre", "pH", "GERMENES AEROBIOS TOTALES", "COLIFORMES TOTALES", "COLIFORMES FECALES",
                        "ESCHERICHIA COLI", "PSEUDOMONA AERUGINOSA", "SHIGELLA"};
            case FQALIMENTOS:
                return new String[]{"ID", "FECHA", "Acidez", "Ácido cianúrico", "Ácido sórbico", "Actividad acuosa", "Alcalinidad", "Alcohol", "Aluminio", "Amonio",
                        "Amoníaco", "Antimonio", "Aroma", "Arsénico", "Asbesto", "Aspecto", "Azúcares", "Azúcares deducidas de la lactosa", "Azúcares invertidos",
                        "Azúcares reductores", "Bario", "Bicarbonatos", "Boro", "Bromuro", "Cadmio total", "Calcio", "Características Organolépticas",
                        "Carbonatos", "Cenizas", "Cenizas insolubles en ácido cítrico", "Cenizas solubles en agua de las cenizas totales", "Cianuros",
                        "Cloro activo", "Cloro residual o libre", "Cloro total", "Cloruros", "Cobalto", "Cobre", "Colesterol", "Color", "Colorantes artificiales",
                        "Colorantes naturales", "Colorantes naturales y artificiales", "Conductividad", "Cromo Hexavalente", "DBO", "Detergentes", "DQO",
                        "Dureza", "Edulcorantes", "Estaño", "Extracto primitivo", "Extracto seco", "Fenoles", "Fluoruros", "Flúor", "Fosfatos", "Fósforo total",
                        "Gliadinas", "Gluten", "Grado de fermentación", "Grados Brix", "Grasa", "Grasas de cacao", "Grasas de leche", "Grasas y aceites",
                        "Hidracina", "Hidrocarburos", "HIDROCARBUROS TOTALES DE PETROLEO (C6)", "HIDROCARBUROS TOTALES DE PETROLEO (C6-C35)",
                        "HIDROCARBUROS TOTALES DE PETROLEO (C6-C8)", "HIDROCARBUROS TOTALES DE PETROLEO (C8-C10)", "HIDROCARBUROS TOTALES DE PETROLEO (C10-C12)",
                        "HIDROCARBUROS TOTALES DE PETROLEO (C12-C16)", "HIDROCARBUROS TOTALES DE PETROLEO (C16-C21)", "HIDROCARBUROS TOTALES DE PETROLEO (C21-C35)",
                        "Hierro", "Humedad", "Magnesio", "Manganeso", "Materia grasa", "Mercurio Total", "Molibdeno", "Nitratos", "Nitritos",
                        "Nitrógeno Amoniacal", "Nitrógeno total", "Níquel", "Observación microscópica", "Olor", "Organoclorados", "Oxígeno disuelto",
                        "Ozono", "Peróxido de hidrógeno", "pH", "Plata", "Plomo", "Porcentaje de cloruro de sodio", "Potasio", "Propionato de sodio",
                        "Relación Peso/Humedad", "Residuo evaporación a 105ºC", "Residuo evaporación a 180ºC", "Residuo Seco", "Sabor", "Selenio",
                        "Silicatos", "Sodio", "Sulfatos", "Sulfuros", "Sustancias extrañas", "Sustancias solubles en éter etílico", "Sólidos Disueltos totales",
                        "Sólidos no grasos de cacao", "Sólidos no grasos de leche", "Sólidos sedimentables en 10 minutos", "Sólidos sedimentables en 2 horas",
                        "Sólidos suspendidos totales", "Sólidos suspendidos volátiles", "Temperatura", "Turbidez", "Vanadio", "VIBRIO CHOLERAE", "Zinc"};
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

    public void guardarFechaAnalisis(Resultados r, int id) {
        Connection conexion = con.getConnection();
        try {
            ps = conexion.prepareStatement("update muestras set fechaAnalisis = ? "
                    + "where idmuestras = ?");
            ps.setDate(1, r.getFechaAnalisis());
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

    public void guardarFechaAnalisisMBAGUA(Resultados r, int id) {
        Connection conexion = con.getConnection();
        try {
            ps = conexion.prepareStatement("update mbagua set fechaAnalisis = ? "
                    + "where idmuestras = ?");
            ps.setDate(1, r.getFechaAnalisis());
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

    public void guardarFechaAnalisis(Map m) {
        Connection conexion = con.getConnection();
        try {
            ps = conexion.prepareStatement("update muestras set fechaAnalisis = ? "
                    + "where idmuestras = ?");
            ps.setDate(1, (java.sql.Date) m.get("fechaAnalisis"));
            ps.setInt(2, (int) m.get("idmuestras"));
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
    }

    public boolean guardarResultadosHisopados(Map m) {
        Connection conexion = con.getConnection();
        try {
            ps = conexion.prepareStatement("INSERT INTO `laboratorio`.`hisopados` "
                    + "(`idmuestras`, `coliformesTotales`, `coliformesFecales`, `escherichia`, "
                    + "`germenes`,`staphilococos`, enterobacterias, salmonella, mohos, listeria, vibrio) VALUES "
                    + "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            ps.setInt(1, (int) m.get("idmuestras"));
            ps.setString(2, m.get("coliformesTotales").toString());
            ps.setString(3, m.get("coliformesFecales").toString());
            ps.setString(4, m.get("escherichia").toString());
            ps.setString(5, m.get("germenes").toString());
            ps.setString(6, m.get("staphilococos").toString());
            ps.setString(7, m.get("enterobacterias").toString());
            ps.setString(8, m.get("salmonella").toString());
            ps.setString(9, m.get("mohos").toString());
            ps.setString(10, m.get("listeria").toString());
            ps.setString(11, m.get("vibrio").toString());
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

    public boolean guardarResultadosHisopadosAlliance(Map m) {
        Connection conexion = con.getConnection();
        try {
            ps = conexion.prepareStatement("INSERT INTO `laboratorio`.`hisopados` "
                    + "(`idmuestras`, `coliformesTotales`, `coliformesFecales`, `escherichia`, "
                    + "`germenes`,`staphilococos`, enterobacterias, limiteGermenes, limiteTotales)"
                    + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
            ps.setInt(1, (int) m.get("idmuestras"));
            ps.setString(2, m.get("coliformesTotales").toString());
            ps.setString(3, m.get("coliformesFecales").toString());
            ps.setString(4, m.get("escherichia").toString());
            ps.setString(5, m.get("germenes").toString());
            ps.setString(6, m.get("staphilococos").toString());
            ps.setString(7, m.get("enterobacterias").toString());
            ps.setInt(8, Integer.parseInt(m.get("limiteGermenes").toString()));
            ps.setInt(9, Integer.parseInt(m.get("limiteTotales").toString()));
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al guardar datos, " + e + " en línea " + e.getStackTrace()[0].getLineNumber());
            return false;
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
    }

    public boolean editarResultadosHisopados(Map m) {
        Connection conexion = con.getConnection();
        try {
            ps = conexion.prepareStatement("update hisopados set coliformesTotales = ?, "
                    + "coliformesFecales = ?, escherichia= ?, germenes= ?, "
                    + "staphilococos = ?, enterobacterias = ?, salmonella = ?, mohos = ?, listeria = ?, vibrio = ? where idmuestras = ?");
            ps.setString(1, m.get("coliformesTotales").toString());
            ps.setString(2, m.get("coliformesFecales").toString());
            ps.setString(3, m.get("escherichia").toString());
            ps.setString(4, m.get("germenes").toString());
            ps.setString(5, m.get("staphilococos").toString());
            ps.setString(6, m.get("enterobacterias").toString());
            ps.setString(7, m.get("salmonella").toString());
            ps.setString(8, m.get("mohos").toString());
            ps.setString(9, m.get("listeria").toString());
            ps.setString(10, m.get("vibrio").toString());
            ps.setInt(11, (int) m.get("idmuestras"));
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al editar datos, " + e);
            return false;
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
    }

    public boolean editarResultadosHisopadosAlliance(Map m) {
        Connection conexion = con.getConnection();
        try {
            ps = conexion.prepareStatement("update hisopados set coliformesTotales = ?, "
                    + "coliformesFecales = ?, escherichia= ?, germenes= ?, "
                    + "staphilococos = ?, enterobacterias = ?, limiteGermenes = ?,"
                    + "limiteTotales = ? where idmuestras = ?");
            ps.setString(1, m.get("coliformesTotales").toString());
            ps.setString(2, m.get("coliformesFecales").toString());
            ps.setString(3, m.get("escherichia").toString());
            ps.setString(4, m.get("germenes").toString());
            ps.setString(5, m.get("staphilococos").toString());
            ps.setString(6, m.get("enterobacterias").toString());
            ps.setInt(7, Integer.parseInt(String.valueOf(m.get("limiteGermenes"))));
            ps.setInt(8, Integer.parseInt(String.valueOf(m.get("limiteTotales"))));
            ps.setInt(9, (int) m.get("idmuestras"));
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al editar datos, " + e);
            return false;
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
    }

    public Map<String, String> recuperarResultadosHisopados(int id) {
        Connection conexion = con.getConnection();
        Map<String, String> aux = new HashMap();
        try {
            ps = conexion.prepareStatement("select germenes, coliformesTotales, "
                    + "coliformesFecales, escherichia, staphilococos, fechaAnalisis, enterobacterias, salmonella, mohos, listeria, vibrio from "
                    + "vistaHisopado where vistatabla_idmuestras = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                    System.out.println("rs.getMetaData().getColumnName(i + 1) = " + rs.getMetaData().getColumnName(i + 1));
                    System.out.println("rs.getObject(i + 1) = " + rs.getObject(i + 1));
                    aux.put(rs.getMetaData().getColumnName(i + 1), rs.getObject(i + 1) == null ? "" : rs.getObject(i + 1).toString());
                }
                return aux;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al recuperarResultadosHisopados, " + e);
            return null;
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
        return null;
    }

    public Map<String, String> recuperarResultadosHisopadosAlliance(int id) {
        Connection conexion = con.getConnection();
        Map<String, String> aux = new HashMap<>();
        try {
            ps = conexion.prepareStatement("select germenes, coliformesTotales, "
                    + "coliformesFecales, escherichia, staphilococos, fechaAnalisis, enterobacterias,"
                    + "germenesPotencia, totalesPotencia, staphilococosPotencia, limiteGermenes, limiteTotales from "
                    + "vistaHisopado where vistatabla_idmuestras = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                    aux.put(rs.getMetaData().getColumnName(i + 1), rs.getObject(i + 1) == null ? "" : rs.getObject(i + 1).toString());
                }
                return aux;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al recuperarResultadosHisopadosAlliance, " + e);
            return null;
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
        return null;
    }

    public Map<String, String> recuperarResultadosBaseHelada(int id) {
        Connection conexion = con.getConnection();
        Map<String, String> aux = new HashMap();
        try {
            ps = conexion.prepareStatement("select `germenes`, `coliformesTotales`,"
                    + "`coliformesFecales`, `escherichia`,`mohos`, conclusion, staphilococos, salmonella, fechaAnalisis from vistambchocolates where idmuestras = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                    aux.put(rs.getMetaData().getColumnName(i + 1), rs.getObject(i + 1) == null ? "" : rs.getObject(i + 1).toString());
                }
                return aux;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al recuperarResultadosBaseHelada, " + e);
            return null;
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
        return null;
    }

    public String consultarMetodoDeterminaciones(int id, String determinacion) {
        Connection conexion = con.getConnection();
        String aux = "";
        try {
            ps = conexion.prepareStatement(" select " + determinacion + "Metodo from determinaciones where idmuestras = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                aux = rs.getString(1);
                return aux;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al recuperarResultadosBaseHelada, " + e);
            return null;
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
        return null;
    }

    public void guardarConclusion(String s, int id) {
        Connection conexion = con.getConnection();
        try {
            ps = conexion.prepareStatement("update muestras set conclusion = ? "
                    + "where idmuestras = ?");
            ps.setString(1, s);
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

    public boolean checkearPDF(int id, String db) {
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

    public String recuperarConclusion(int id) {
        Connection conexion = con.getConnection();
        try {
            ps = conexion.prepareStatement("Select conclusion from muestras where idmuestras = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getString("conclusion");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al recuperar conclusion, " + e);
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
        return null;
    }

    public int obtenerIdUsuario(String nombre) {
        Connection conexion = con.getConnection();
        int aux = -1;
        try {
            ps = conexion.prepareStatement("select idusuarios from usuarios where nombre = ?");
            ps.setString(1, nombre);
            rs = ps.executeQuery();
            while (rs.next()) {
                aux = rs.getInt(1);
            }
            return aux;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al obtener usuario. " + e);
            return aux;
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
    }

    public int recuperarIdCliente(String procedencia) {
        Connection conexion = con.getConnection();
        int aux = -1;
        try {
            ps = conexion.prepareStatement("select idcliente from vistaprocedencia where procedencia = ?");
            ps.setString(1, procedencia);
            rs = ps.executeQuery();
            while (rs.next()) {
                aux = rs.getInt("idcliente");
            }
            return aux;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al recuperar id del cliente, " + e);
            return -1;
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
    }

    public boolean borrarCliente(int id) {
        Connection conexion = con.getConnection();
        try {
            ps = conexion.prepareStatement("update datos_cliente set anulado = 1 where idcliente = ?");
            ps.setInt(1, id);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al borrar cliente, " + e);
            return false;
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
    }

    public String[] recuperarDatosUsuario(int id) {
        Connection conexion = con.getConnection();
        String[] aux = new String[8];
        try {
            ps = conexion.prepareStatement("select * from datos_cliente where idcliente = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                for (int i = 0; i < aux.length; i++) {
                    if (i == 0) {
                        aux[0] = String.valueOf(rs.getInt("idcliente"));
                    } else {
                        aux[i] = rs.getString(i + 1);
                    }
                }
            }
            return aux;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al recuperar datos, " + e);
            return null;
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
    }

    public boolean editarCliente(boolean nombre, boolean empresa, Cliente c, int id) {
        Connection conexion = con.getConnection();
        if (nombre == true && empresa == true) {
            try {
                ps = conexion.prepareStatement("update datos_cliente set empresa = ?,"
                        + " nombre = ?, direccion = ?, ciudad = ?, telefono = ?,"
                        + " email = ?, cuit = ? where idcliente = ?");
                ps.setString(1, c.getEmpresa());
                ps.setString(2, c.getNombre());
                if (c.getDireccion().equals("")) {
                    ps.setString(3, " ");
                } else {

                    ps.setString(3, c.getDireccion());
                }
                if (c.getCiudad().equals("")) {
                    ps.setString(4, " ");
                } else {

                    ps.setString(4, c.getCiudad());
                }
                if (c.getTelefono().equals("")) {
                    ps.setString(5, " ");
                } else {

                    ps.setString(5, c.getTelefono());
                }
                if (c.getEmail().equals("")) {
                    ps.setString(6, " ");
                } else {

                    ps.setString(6, c.getEmail());
                }

                if (c.getCuit().equals("")) {
                    ps.setString(7, "");
                } else {
                    ps.setString(7, c.getCuit());
                }
                ps.setInt(8, id);
                ps.executeUpdate();
                return true;
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error al agregar cliente, " + e);
                return false;
            } finally {
                try {
                    conexion.close();
                } catch (Exception e) {
                    System.err.println("Error " + e);
                }
            }
        } else if (nombre == true && empresa == false) {
            try {
                ps = conexion.prepareStatement("update datos_cliente set empresa = ?,"
                        + " nombre = ?, direccion = ?, ciudad = ?, telefono = ?,"
                        + " email = ? where idcliente = ?");
                ps.setString(1, " ");
                ps.setString(2, c.getNombre());
                if (c.getDireccion().equals("")) {
                    ps.setString(3, " ");
                } else {
                    ps.setString(3, c.getDireccion());
                }
                if (c.getCiudad().equals("")) {
                } else {
                    ps.setString(4, c.getCiudad());
                }
                if (c.getTelefono().equals("")) {
                    ps.setString(5, " ");
                } else {
                    ps.setString(5, c.getTelefono());
                }
                if (c.getEmail().equals("")) {
                    ps.setString(6, " ");
                } else {
                    ps.setString(6, c.getEmail());
                }
                ps.setInt(7, id);
                ps.executeUpdate();
                return true;
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error al agregar cliente, " + e);
                return false;
            } finally {
                try {
                    conexion.close();
                } catch (Exception e) {
                    System.err.println("Error " + e);
                }
            }
        } else if (nombre == false && empresa == true) {
            try {
                ps = conexion.prepareStatement("update datos_cliente set empresa = ?,"
                        + " nombre = ?, direccion = ?, ciudad = ?, telefono = ?,"
                        + " email = ? where idcliente = ?");
                ps.setString(1, c.getEmpresa());
                ps.setString(2, " ");
                if (c.getDireccion().equals("")) {
                    ps.setString(3, " ");
                } else {
                    ps.setString(3, c.getDireccion());
                }
                if (c.getCiudad().equals("")) {
                } else {
                    ps.setString(4, c.getCiudad());
                }
                if (c.getTelefono().equals("")) {
                    ps.setString(5, " ");
                } else {
                    ps.setString(5, c.getTelefono());
                }
                if (c.getEmail().equals("")) {
                    ps.setString(6, " ");
                } else {
                    ps.setString(6, c.getEmail());
                }
                ps.setInt(7, id);
                ps.executeUpdate();
                return true;
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error al agregar cliente, " + e);
                return false;
            } finally {
                try {
                    conexion.close();
                } catch (Exception e) {
                    System.err.println("Error " + e);
                }
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
    }

    public int recuperarIdMuestrasSiguiente() {
        Connection conexion = con.getConnection();
        try {
            ps = conexion.prepareStatement("select idmuestras from muestras order by idmuestras desc limit 1");
            rs = ps.executeQuery();
            while (rs.next()) {
                int aux = rs.getInt("idmuestras");
                return aux + 1;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al recuperar id, " + e);
            return -1;
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
        return -1;
    }

    public boolean editarMBAguaDeRecreacion(Resultados r, int vencimiento) {
        Connection conexion = con.getConnection();
        try {
            ps = conexion.prepareStatement("UPDATE `laboratorio`.`mbagua` SET "
                    + "`germenes` = ?, `coliformesTotales` = ? , `coliformesFecales` = ?, "
                    + "`escherichia` = ? , `pseudomona` = ?, ph = ?, cloroTotal = ?, "
                    + "caracteresOrganolepticos = ?, staphilococos = ?, streptococos = ?, "
                    + "cloroLibre = ?, vencimiento = ?, shigella = ? WHERE `idmuestras` = ? ;");
            ps.setString(1, r.getGermenes());
            ps.setString(2, r.getColiformesTotales());
            ps.setString(3, r.getColiformesFecales());
            ps.setString(4, r.getEscherichia());
            ps.setString(5, r.getPseudomona());
            ps.setDouble(6, r.getPh());
            ps.setDouble(7, r.getClorototal());
            ps.setString(8, r.getCaracteresOrgasnolepticos());
            ps.setString(9, r.getStaphilococos());
            ps.setString(10, r.getStreptococos());
            ps.setDouble(11, r.getCloroLibre());
            ps.setInt(12, vencimiento);
            ps.setString(13, r.getShigella());
            ps.setInt(14, r.getIdmuestras());
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al editar datos, " + e);
            return false;
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
    }

    public void guardarRecomendacion(String recomendacion, int id) {
        Connection conexion = con.getConnection();
        try {
            ps = conexion.prepareStatement("update muestras set recomendacion = ?"
                    + " where idmuestras = ?");
            ps.setString(1, recomendacion);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al editar datos, " + e);
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
    }

    public String recuperarRecomendacion(int id) {
        Connection conexion = con.getConnection();
        try {
            ps = conexion.prepareStatement("select recomendacion from muestras where idmuestras = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                String aux = String.valueOf(rs.getObject(1));
                return aux;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al editar datos, " + e);
            return null;
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
        return null;
    }

    public boolean guardarResultadosEfluentesTipo(Map m) {
        Connection conexion = con.getConnection();
        try {
            ps = conexion.prepareStatement("insert into efluentes (idmuestras, ph, conductividad,"
                    + " dqo, dbo, solidos10, solidos120, detergentes, grasasAceite,"
                    + " fosforoTotal, nitrogenoTotal, sustancias, coliformesFecales, hidrocarburos, nitratos, cloro, coliformesTotales, escherichia , sulfuros) "
                    + "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            ps.setInt(1, (int) m.get("idmuestras"));
            ps.setDouble(2, (double) m.get("ph"));
            ps.setString(3, m.get("conductividad").toString());
            ps.setString(4, m.get("dqo").toString());
            ps.setString(5, m.get("dbo").toString());
            ps.setString(6, String.valueOf(m.get("solidos10")));
            ps.setString(7, String.valueOf(m.get("solidos120")));
            ps.setString(8, String.valueOf(m.get("detergentes")));
            ps.setString(9, String.valueOf(m.get("grasas")));
            ps.setString(10, String.valueOf(m.get("fosforo")));
            ps.setString(11, String.valueOf(m.get("nitrogeno")));
            ps.setString(12, String.valueOf(m.get("sustancias")));
            ps.setString(13, String.valueOf(m.get("coliformesFecales")));
            ps.setString(14, m.get("hidrocarburos").toString());
            ps.setString(15, m.get("nitratos").toString());
            ps.setString(16, m.get("cloro").toString());
            ps.setString(17, m.get("coliformesFecales").toString());
            ps.setString(18, m.get("escherichia").toString());
            ps.setString(19, m.get("sulfuros").toString());
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

    public boolean editarResultadosEfluentesTipo(Map m) {
        Connection conexion = con.getConnection();
        try {
            ps = conexion.prepareStatement("update efluentes set ph = ?, conductividad = ?,"
                    + " dqo = ?, dbo = ?, solidos10 = ?, solidos120 = ?, detergentes = ?, grasasAceite = ?, "
                    + "fosforoTotal = ?, nitrogenoTotal = ?, sustancias = ?, coliformesFecales = ?, "
                    + "hidrocarburos = ?, nitratos = ?, cloro = ?, coliformesTotales = ?, escherichia = ?, sulfuros = ? where idmuestras = ?");
            ps.setDouble(1, (double) m.get("ph"));//ph
            ps.setString(2, m.get("conductividad").toString());//conductividad
            ps.setString(3, m.get("dqo").toString());//dqo
            ps.setString(4, m.get("dbo").toString());//dbo
            ps.setString(5, String.valueOf(m.get("solidos10")));//solidos10
            ps.setString(6, String.valueOf(m.get("solidos120")));//solidos120
            ps.setString(7, String.valueOf(m.get("detergentes")));//detergentes
            ps.setString(8, String.valueOf(m.get("grasas")));//grasasAceite
            ps.setString(9, String.valueOf(m.get("fosforo")));//fosforoTotal
            ps.setString(10, String.valueOf(m.get("nitrogeno")));//nitrogenoTotal
            ps.setString(11, String.valueOf(m.get("sustancias")));//sustancias
            ps.setString(12, String.valueOf(m.get("coliformesFecales")));//coliformes
            ps.setString(13, m.get("hidrocarburos").toString());//hidrocarburos
            ps.setString(14, m.get("nitratos").toString());//hidrocarburos
            ps.setString(15, m.get("cloro").toString());//hidrocarburos
            ps.setString(16, m.get("coliformesTotales").toString());//hidrocarburos
            ps.setString(17, m.get("escherichia").toString());//hidrocarburos
            ps.setString(18, m.get("sulfuros").toString());//hidrocarburos
            ps.setInt(19, (int) m.get("idmuestras"));//idmuestras
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al guardar datos, " + e + " en línea " + e.getStackTrace()[0].getLineNumber());
            return false;
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
    }

    public Map<String, String> recuperarResultadosEfluentesTipo(int id) {
        Connection conexion = con.getConnection();
        Map<String, String> aux = new HashMap();
        try {
            ps = conexion.prepareStatement("select ph, conductividad, dqo, dbo, "
                    + "solidos10, solidos120, detergentes, grasasAceite, "
                    + "fosforoTotal, nitrogenoTotal, sustancias, coliformesFecales, hidrocarburos, nitratos, cloro, coliformesTotales, escherichia, sulfuros "
                    + "from efluentes where idmuestras = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                    aux.put(rs.getMetaData().getColumnName(i + 1), rs.getObject(i + 1) == null ? "" : rs.getObject(i + 1).toString());
                }
                return aux;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al recuperar datos, " + e);
            return null;
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
        return null;
    }

    public void generarReporteEfluentesCloaca(int id, String procedencia) {
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

    public String recuperarNota(int id) {
        Connection conexion = con.getConnection();
        String nota = "";
        try {
            ps = conexion.prepareStatement("select notas from muestras where idmuestras = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                nota = rs.getString(1);
                return nota;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al generar informe fq, " + e);
            return "";
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
        return "";
    }

    public boolean guardarNota(int id, String nota) {
        Connection conexion = con.getConnection();
        try {
            System.out.println("nota = " + nota);
            ps = conexion.prepareStatement("update muestras set notas = ? where idmuestras = ?");
            ps.setString(1, nota);
            ps.setInt(2, id);
            System.out.println(ps.toString());
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al guardar nota, " + e);
            return false;
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

    public void guardarEmail(String email, int idcliente) {
        Connection conexion = con.getConnection();
        try {
            ps = conexion.prepareStatement("update datos_cliente set email = ? where idcliente = ?");
            ps.setString(1, email.replaceAll(" ", ""));
            ps.setInt(2, idcliente);
            ps.executeUpdate();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al guardar email, " + e);
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
    }

    public int recuperarIdCliente(int id) {
        Connection conexion = con.getConnection();
        try {
            ps = conexion.prepareStatement("select idcliente from muestras where idmuestras = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al recuperar id del cliente, " + e);
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
        return -1;
    }

    public String recuperarSolicitante(int id) {
        Connection conexion = con.getConnection();
        try {
            ps = conexion.prepareStatement("select solicitante, count(solicitante)"
                    + " as mostCommon from muestras where idcliente = ? group by "
                    + "solicitante order by count(solicitante) desc");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getString(1);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al recuperar solicitante, " + e);
            return "";
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
        return "";
    }

    public Vector<String> recuperarSolicitantes(int id) {
        Connection conexion = con.getConnection();
        Vector<String> solicitantes = new Stack<>();
        try {
            ps = conexion.prepareStatement("select solicitante, count(solicitante)"
                    + " as mostCommon from muestras where idcliente = ? group by "
                    + "solicitante order by mostCommon desc");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                solicitantes.add(rs.getString("solicitante"));
            }
            return solicitantes;
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
    }

    public void cambiarHisopado(int id) {
        Connection conexion = con.getConnection();
        try {
            ps = conexion.prepareStatement("update hisopados set germenesPotencia = 0, "
                    + "totalesPotencia = 0, staphilococosPotencia = 0, enterobacterias = 0 where idmuestras = ?");
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al cambia de tipo de hisopado, " + e);
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
    }

    public void cambiarTipo(int id, String db) {
        Connection conexion = con.getConnection();
        try {
            ps = conexion.prepareStatement("delete from " + db + " where idmuestras = ?");
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            System.err.println("Error al cambia de tipo de hisopado, " + e);
            JOptionPane.showMessageDialog(null, "Error al cambia de tipo de hisopado, " + e);
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
    }

    public void esconderFechaVencimiento(int id, boolean poner) {
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

    public boolean recuperarEsconderFechaVencimiento(int id) {
        Connection conexion = con.getConnection();
        boolean aux = false;
        try {
            ps = conexion.prepareStatement("select ponerFechaVencimiento from muestras where idmuestras = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                aux = rs.getBoolean("ponerFechaVencimiento");
            }
            return aux;
        } catch (Exception e) {
            JOptionPane.showInputDialog("Error al recuperar estado de fecha de vencimiento, " + e);
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
        return false;
    }

    public String recuperarIdentificacion(int id) {
        Connection conexion = con.getConnection();
        String aux = "";
        try {
            ps = conexion.prepareStatement("select identificacion from muestras where idmuestras = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                aux = rs.getString("identificacion");
            }
            return aux;
        } catch (Exception e) {
            JOptionPane.showInputDialog("Error al recuperar identificacion, " + e);
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
        return null;
    }

    public Map<String, String> recuperarIdentificaciones() {
        Connection conexion = con.getConnection();
        String aux = "";
        Map mapa = new HashMap();
        String idmuestras;
        int cont = 0;
        try {
            ps = conexion.prepareStatement("select identificacion,idmuestras from muestras order by idmuestras desc");
            rs = ps.executeQuery();
            while (rs.next()) {
                aux = rs.getString("identificacion");
                idmuestras = String.valueOf(rs.getInt("idmuestras"));
                mapa.put(idmuestras, aux);
            }
            return mapa;
        } catch (Exception e) {
            JOptionPane.showInputDialog("Error al recuperar identificaciones, " + e);
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
        return null;
    }

    public String recuperarTipoAnalisis(int id) {
        Connection conexion = con.getConnection();
        String tipo = "";
        try {
            ps = conexion.prepareStatement("select tipo from vistaTabla where idmuestras = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                tipo = rs.getString("tipo");
            }
            return tipo;
        } catch (Exception e) {
            JOptionPane.showInputDialog("Error al recuperar tipo de analisis, " + e);
            return null;
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
    }

    public String recuperarDireccion(int id) {
        Connection conexion = con.getConnection();
        String direccion = "";
        try {
            ps = conexion.prepareStatement("select direccion from datos_cliente where idcliente = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                direccion = rs.getString("direccion");
            }
            return direccion;
        } catch (Exception e) {
            JOptionPane.showInputDialog("Error al recuperar direccion del cliente, " + e);
            return null;
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
    }

    public String recuperarCiudad(int id) {
        Connection conexion = con.getConnection();
        String ciudad = "";
        try {
            ps = conexion.prepareStatement("select ciudad from datos_cliente where idcliente = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                ciudad = rs.getString("ciudad");
            }
            return ciudad;
        } catch (Exception e) {
            JOptionPane.showInputDialog("Error al recuperar ciudad " + e);
            return null;
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
    }

    public String recuperarProcedencia(int id) {
        Connection conexion = con.getConnection();
        String procedencia = "";
        try {
            ps = conexion.prepareStatement("select procedencia from vistaanalisisenproceso where idcliente = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                procedencia = rs.getString("procedencia");
            }
            return procedencia;
        } catch (Exception e) {
            JOptionPane.showInputDialog("Error al recuperar procedencia " + e);
            return null;
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
    }

    public String recuperarTituloManual(int id) {
        Connection conexion = con.getConnection();
        String titulo = "";
        try {
            ps = conexion.prepareStatement("select titulo from manual where idmuestras = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                titulo = rs.getString("titulo");
            }
            return titulo;
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

    public DefaultTableModel recuperarUsuariosTabla() {
        Connection conexion = con.getConnection();
        DefaultTableModel modeloTabla = new DefaultTableModel();
        String[] aux = new String[2];
        modeloTabla.addColumn("Nombre de usuario");
        modeloTabla.addColumn("Tipo de usuario");
        try {
            ps = conexion.prepareStatement("select nombre, tipoUsuario from usuarios");
            rs = ps.executeQuery();
            while (rs.next()) {
                aux[0] = rs.getString("nombre");
                aux[1] = rs.getInt("tipoUsuario") == 1 ? "Administrador" : "Normal";
                modeloTabla.addRow(aux);
            }
            return modeloTabla;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al recuperar usuarios, " + e);
            return null;
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error al recuperar usuarios, " + e);
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
            ps = conexion.prepareStatement("select e.email, v.fechaVencimiento, "
                    + "v.idmuestras, m.tipo, m.realizadoPor, p.procedencia  from "
                    + "vistaemail e join vencimientos v on e.idmuestras = v.idmuestras "
                    + "join muestras m on v.idmuestras = m.idmuestras join vistaprocedencia "
                    + "p on p.idcliente = m.idcliente where v.idmuestras = ?;");
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
    }

    public List recuperarFQAguaCompleto(int id, List<Determinacion> determinaciones) {
        Connection conexion = con.getConnection();
        try {
            ps = conexion.prepareStatement("select * from determinaciones where idmuestras = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                for (int i = 3; i < rs.getMetaData().getColumnCount() + 1; i++) {
                    Object auxx = rs.getObject(i);
                    System.out.println("auxx = " + auxx);
                    if (auxx != null) {
                        if (!auxx.toString().isBlank()) {
                            for (int j = 0; j < determinaciones.size(); j++) {
                                if (determinaciones.get(j).getNombreDB().equals(rs.getMetaData().getColumnLabel(i))) {
                                    determinaciones.get(j).setActivado(true);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
            return determinaciones;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al recuperarFQAguaCompleto, " + e);
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error al recuperar FQCompleto, " + e);
            }
        }
        return null;
    }

    public List<Determinacion> recuperarDatosDeterminaciones(List<Determinacion> determinaciones, int id) {
        Connection conexion = con.getConnection();
        try {
            ps = conexion.prepareStatement("select * from determinaciones where idmuestras = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {

                determinaciones.forEach(d -> {
                    try {
                        String res = rs.getString(d.getNombreDB());
                        System.out.println("nombre = " + d.getNombreDB());
                        System.out.println("res = " + res);
                        d.formatearResultado(res == null || res.trim().isEmpty() ? "-1" : res);
                    } catch (SQLException ex) {
                        System.getLogger(Consultas.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                    }
                });
            }
            return determinaciones;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al recuperarDatosDeterminaciones, " + e.getStackTrace()[0]);
            return null;
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error al recuperar datos FQ, " + e);
            }
        }
    }

    public List<Determinacion> recuperarDatosDeterminacionesGenerar(List<Determinacion> determinaciones, int id) {
        Connection conexion = con.getConnection();
        try {
            ps = conexion.prepareStatement("select * from determinaciones where idmuestras = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {

                determinaciones.forEach(d -> {
                    try {
                        String res = rs.getString(d.getNombreDB());
                        d.formatearResultadoGenerar(res == null ? "-1" : res);
                    } catch (SQLException ex) {
                        System.getLogger(Consultas.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                    }
                });
            }
            return determinaciones;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al recuperarDatosDeterminaciones, " + e);
            return null;
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error al recuperar datos FQ, " + e);
            }
        }
    }

    public List<Determinacion> recuperarMetodosDeterminaciones(List<Determinacion> determinaciones, int id) {
        Connection conexion = con.getConnection();
        try {
            ps = conexion.prepareStatement("select * from determinacionesMetodo where idmuestras = ?");//Cambiar al agregar
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                determinaciones.forEach(d -> {
                    try {
                        d.setMetodo(rs.getString(d.getNombreDB() + "Metodo"));
                    } catch (SQLException ex) {
                        System.getLogger(Consultas.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                    }
                });
            }
            return determinaciones;
        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, "Error al recuperarMetodosDeterminaciones, " + e);
            StackTraceElement stackTraceElement = Arrays.stream(e.getStackTrace()).filter(ste -> ste.getClassName().equals(this.getClass().getName())).collect(Collectors.toList()).get(0);
            return null;
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error al recuperar datos FQ, " + e);
            }
        }
    }

    public boolean[] recuperarFQCompleto(int id) {
        Connection conexion = con.getConnection();
        boolean[] aux = new boolean[92]; //Cambiar al agregar
        try {
            ps = conexion.prepareStatement(recuperarFQ);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                for (int i = 0; i < aux.length; i++) {
                    Object o = rs.getObject(i + 1);
                    if (o == null) {
                        aux[i] = false;
                    } else {
                        aux[i] = true;
                    }
                }
            }
            return aux;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al recuperarFQCompleto, " + e);
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error al recuperar FQCompleto, " + e);
            }
        }
        return null;
    }

    public boolean blankearDeterminaciones(String query, int id, int cont) {
        Connection conexion = con.getConnection();
        try {
            ps = conexion.prepareStatement(query);
            for (int i = 0; i < cont; i++) {
                ps.setString(i + 1, null);
            }
            ps.setInt(cont + 1, id);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al blankearDeterminaciones, " + e);
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error al recuperar FQCompleto, " + e);
            }
        }
        return false;
    }

    public boolean borrarDeterminaciones(int id) {
        Connection conexion = con.getConnection();
        try {
            conexion.setAutoCommit(false);
            PreparedStatement ps1 = conexion.prepareStatement("delete from determinaciones where idmuestras = ?");
            PreparedStatement ps2 = conexion.prepareStatement("delete from determinacionesMetodo where idmuestras = ?");
            ps1.setInt(1, id);
            ps2.setInt(1, id);
            ps1.execute();
            ps2.execute();
            conexion.commit();

            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al blankearDeterminaciones, " + e);
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error al recuperar FQCompleto, " + e);
            }
        }
        return false;
    }

    public void generarReporteFQCompleto(int id, String titulo, List<Determinacion> resultados) {
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
    }

    public boolean guardarDeterminaciones(List<Determinacion> resultados, int id) {
        Connection conexion = con.getConnection();

        String queryDet = "insert into determinaciones (";
        for (int i = 0; i < resultados.size(); i++) {
            queryDet += resultados.get(i).getNombreDB() + ", ";
        }
        queryDet += "idmuestras) values (";
        for (int i = 0; i < resultados.size(); i++) {
            queryDet += "?, ";
        }
        queryDet += "?)";

        String queryMetodo = "insert into determinacionesMetodo (";
        for (int i = 0; i < resultados.size(); i++) {
            queryMetodo += resultados.get(i).getNombreDB() + "Metodo, ";
        }
        queryMetodo += "idmuestras) values (";
        for (int i = 0; i < resultados.size(); i++) {
            queryMetodo += "?, ";
        }
        queryMetodo += "?)";
        try {
            conexion.setAutoCommit(false);
            PreparedStatement ps1 = conexion.prepareStatement(queryDet);
            for (int i = 0; i < resultados.size(); i++) {
                ps1.setString(i + 1, resultados.get(i).getResultado());
            }
            ps1.setInt(resultados.size() + 1, id);

            PreparedStatement ps2 = conexion.prepareStatement(queryMetodo);
            for (int i = 0; i < resultados.size(); i++) {
                ps2.setString(i + 1, resultados.get(i).getMetodo());
            }
            ps2.setInt(resultados.size() + 1, id);

            ps1.execute();
            ps2.execute();
            conexion.commit();
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

    public boolean hayEntrega(int id) {
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
    }

    public void guardarLimiteMohos(int id, boolean selected) {
        Connection conexion = con.getConnection();

        try {
            ps = conexion.prepareStatement("update mbagua set mohosLimite = ? where idmuestras = ?");
            ps.setInt(1, selected ? 1 : 0);
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

    public void guardarMostrar(int id, String mostrar) {
        Connection conexion = con.getConnection();

        try {
            ps = conexion.prepareStatement("update manual set mostrar = ? where idmuestras = ?");
            ps.setString(1, mostrar);
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

    public int consultarActualizacion() {
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
            return aux.equals(Inicial.VERSION) ? NO_ACTUALIZAR : ACTUALIZAR;
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
    }

    public boolean recuperarFQAguaCompleto(int id) {
        Connection conexion = con.getConnection();

        try {
            ps = conexion.prepareStatement("select * from determinaciones where idmuestras = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                return true;
            }
            return false;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al guardar datos, " + e);
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
        return false;
    }

    public boolean consultarGuardar(int idCliente) {
        Connection conexion = con.getConnection();

        try {
            ps = conexion.prepareStatement("select guardar from datos_cliente where idcliente = ?");
            ps.setInt(1, idCliente);
            rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getInt("guardar") == 1 ? true : false;
            }
            return false;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al guardar datos, " + e);
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
        return false;
    }

    public boolean consultarMostrar(int idmuestras) {
        Connection conexion = con.getConnection();

        try {
            ps = conexion.prepareStatement("select mostrar from manual where idmuestras = ?");
            ps.setInt(1, idmuestras);
            rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getString("mostrar") == "true" ? true : false;
            }
            return false;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al guardar datos, " + e);
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
        return false;
    }

    public boolean guardarSolicitanteGuardar(int idcliente, boolean guardar) {
        Connection conexion = con.getConnection();

        try {
            ps = conexion.prepareStatement("update datos_cliente set guardar = ? where idcliente= ?");
            ps.setInt(1, guardar ? 1 : 0);
            ps.setInt(2, idcliente);
            System.out.println("ps.toString() = " + ps.toString());
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al guardar datos, " + e);
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
        return false;
    }

    public void guardarArregloAHacer(int id, String query) {
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
