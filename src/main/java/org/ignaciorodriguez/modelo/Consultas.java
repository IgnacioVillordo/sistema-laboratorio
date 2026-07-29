package org.ignaciorodriguez.modelo;

import com.mysql.cj.util.StringUtils;
import org.apache.poi.xddf.usermodel.chart.*;
import org.ignaciorodriguez.Main;
import org.ignaciorodriguez.repository.ClienteRepository;
import org.ignaciorodriguez.service.ArchivoService;
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

public class Consultas extends Conexion {

    public final static int NO_ACTUALIZAR = 0;
    public final static int ACTUALIZAR = 1;
    public final static int ERROR_ACTUALIZAR = -1;
    private static Consultas instancia;
    public String caracteres;
    public boolean email = false;
    Conexion con = new Conexion();
    PreparedStatement ps = null;
    ResultSet rs = null;
    ArchivoService archivoService = new ArchivoService();
    ClienteRepository clienteRepository = new ClienteRepository(con);

    private Consultas() {
    }

    public static Consultas getInstancia() {
        if (instancia == null) {
            instancia = new Consultas();
        }
        return instancia;
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
            String ruta = archivoService.recuperarRutas("Reportes") + org.ignaciorodriguez.utils.SeparatorUtils.s + tipo + " " + clienteRepository.recuperarProcedencia(idcliente) + " desde " + formatter.format(desdeSql) + " hasta " + formatter.format(hastaSql) + ".xlsx";
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
            return aux.equals(Main.VERSION) ? NO_ACTUALIZAR : ACTUALIZAR;
        } catch (MalformedURLException ex) {
            Logger.getLogger(Consultas.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Consultas.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -25962;
    }

}
