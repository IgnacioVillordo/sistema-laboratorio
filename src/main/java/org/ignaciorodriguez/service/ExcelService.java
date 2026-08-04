package org.ignaciorodriguez.service;

import com.mysql.cj.util.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xddf.usermodel.chart.*;
import org.apache.poi.xssf.usermodel.*;
import org.ignaciorodriguez.modelo.Conexion;
import org.ignaciorodriguez.modelo.Tipo;
import org.ignaciorodriguez.repository.ClienteRepository;
import org.ignaciorodriguez.utils.ExcelUtils;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

public class ExcelService {

    private final Logger logger = Logger.getLogger(ExcelService.class.getName());
    ArchivoService archivoService = new ArchivoService();
    Conexion con = new Conexion();
    ClienteRepository clienteRepository = new ClienteRepository(con);

    public void exportarExcelTradicional(Date desde, Date hasta, int idcliente, String tipo, Tipo t) {

        try (Workbook workbook = new XSSFWorkbook()) {
            FileOutputStream fileOut = null;
            java.sql.Date desdeSql = new java.sql.Date(desde.getTime());
            java.sql.Date hastaSql = new java.sql.Date(hasta.getTime());
            Sheet sheet = workbook.createSheet("Datos");
            Sheet sheetGraph = workbook.createSheet("Graficos");
            Sheet sheetHidden = workbook.createSheet("Hidden");
            workbook.setSheetHidden(workbook.getSheetIndex("Hidden"), true);
            String[] columnas = obtenerColumnas(t);
            int[] rowNum = {1};
            int[] rowNumGraph = {1};
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < Objects.requireNonNull(columnas).length; i++) {
                headerRow.createCell(i).setCellValue(columnas[i]);
            }
            int[] rowCont = new int[columnas.length - 2];
            switch (t) {
                case EFLUENTES:
                    consultarEfluentesParaExcel(idcliente, tipo, desdeSql, hastaSql, sheet, sheetHidden, rowNum, rowNumGraph, rowCont);
                    break;
                case MBAGUACODIGO:
                    consultarMBAguaCodigoParaExcel(idcliente, tipo, desdeSql, hastaSql, sheet, sheetHidden, rowNum, rowNumGraph, rowCont, workbook);
                    break;
                case MBAGUACOFES, MBAGUABIDON:
                    consultarMBAguaCofesParaExcel(idcliente, tipo, desdeSql, hastaSql, sheet, sheetHidden, rowNum, rowNumGraph, rowCont, workbook);
                    break;
                case MBALIMENTOS:
                    consultarMBAlimentosParaExcel(idcliente, tipo, desdeSql, hastaSql, sheet, sheetHidden, rowNum, rowCont, workbook);
                    break;
                case FQALIMENTOS, FQAGUACOMPLETO, FQGENERICO:
                    consultarDeterminacionesParaExcel(idcliente, tipo, desdeSql, hastaSql, sheet, sheetHidden, rowNum, rowCont, workbook);
                    break;
                case HISOPADOS:
                    consultarHisopadosParaExcel(idcliente, tipo, desdeSql, hastaSql, sheet, sheetHidden, rowNum, rowCont, workbook);
                    break;
                case FQAGUA:
                    consultarFQAguaParaExcel(idcliente, tipo, desdeSql, hastaSql, sheet, sheetHidden, rowNum, rowCont, workbook);
                    break;
                case MBCHOCOLATES:
                    consultarMBChocolatesParaExcel(idcliente, tipo, desdeSql, hastaSql, sheet, sheetHidden, rowNum, rowCont, workbook);
                    break;
                case HISOPADOALLIANCE, HISOPADOLIMITES:
                    consultarHisopadosLimitesParaExcel(idcliente, tipo, desdeSql, hastaSql, sheet, sheetHidden, rowNum, rowCont, workbook);
                    break;
                case MBAGUABALNEARIOS:
                    consultarMBAguaBalnearioParaExcel(idcliente, tipo, desdeSql, hastaSql, sheet, sheetHidden, rowNum, rowNumGraph, rowCont, workbook);
                    break;
                case MBAGUARECREACION:
                    consultarMBAguaRecreacionParaExcel(idcliente, tipo, desdeSql, hastaSql, sheet, sheetHidden, rowNum, rowNumGraph, rowCont, workbook);
                    break;
                case EFLUENTESCLOACA, EFLUENTESINFILTRACION:
                    consultarEfluentesTipoParaExcel(idcliente, tipo, desdeSql, hastaSql, sheet, sheetHidden, rowNum, rowNumGraph, rowCont);
                    break;
                case BASEHELADA:
                    consultarBaseHeladaParaExcel(idcliente, tipo, desdeSql, hastaSql, sheet, sheetHidden, rowNum, rowCont, workbook);
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
            String ruta = archivoService.recuperarRutas("Reportes") + org.ignaciorodriguez.utils.SeparatorUtils.s + tipo + " " + clienteRepository.recuperarProcedencia(idcliente) + " desde " + formatter.format(desdeSql) + " hasta " + formatter.format(hastaSql) + ".xlsx";
            fileOut = new FileOutputStream(ruta);
            workbook.write(fileOut);
            Desktop.getDesktop().open(new File(ruta));
        } catch (IOException e) {
            System.err.println("Error al escribir el archivo: " + e.getMessage());
        }

    }

    private void dibujarGrafico(Sheet sheetGraph, Sheet sheetHidden, int rowNumGraph, String det, int cont, int colInicio) {

        XSSFDrawing drawing = (XSSFDrawing) sheetGraph.createDrawingPatriarch();

        int filaInicio = cont * 22;
        int filaFin = filaInicio + 20;

        XSSFClientAnchor anchor = drawing.createAnchor(0, 0, 0, 0, 1, filaInicio, Math.max(15, 3 + Math.round(rowNumGraph * 0.7f)), filaFin);

        XSSFChart chart = drawing.createChart(anchor);

        XDDFCategoryAxis bottomAxis = chart.createCategoryAxis(AxisPosition.BOTTOM);
        XDDFValueAxis leftAxis = chart.createValueAxis(AxisPosition.LEFT);
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
        chart.plot(data);
    }

    private void consultarEfluentesParaExcel(int idcliente, String tipo, java.sql.Date desdeSql, java.sql.Date hastaSql, Sheet sheet, Sheet sheetHidden, int[] rowNum, int[] rowNumGraph, int[] rowCont) {
        String sql = "SELECT * FROM vistaefluentes WHERE idmuestras in (select idmuestras from muestras where idcliente = ? and tipo = ?) and fechaMuestreo BETWEEN ? AND ?";
        Arrays.fill(rowCont, 0);
        try (Connection conexion = con.getConnection()) {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, idcliente);
            ps.setString(2, tipo);
            ps.setDate(3, desdeSql);
            ps.setDate(4, hastaSql);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Row row = sheet.createRow(rowNum[0]++);
                    String[] parametros = {"ph", "dqo", "dbo", "solidos10", "solidos120", "detergentes", "grasas", "fosforo", "nitrogeno", "sustancias", "coliformesTotales", "coliformesFecales", "escherichia", "conductividad", "hidrocarburos", "nitratos", "cloro", "sulfuros"};
                    String idmuestras = String.valueOf(rs.getInt("idmuestras"));
                    row.createCell(0).setCellValue(idmuestras);
                    row.createCell(1).setCellValue(ExcelUtils.formatearEntradaExcel(rs.getDate("fechaMuestreo").toString()));
                    for (int i = 0; i < parametros.length; i++) {
                        String nombreCol = parametros[i];
                        String valorRaw = rs.getString(nombreCol);

                        row.createCell(i + 2).setCellValue(ExcelUtils.formatearEntradaExcel(valorRaw));

                        if (i < rowCont.length) {
                            Double valorNum = ExcelUtils.extraerNumero(valorRaw);
                            if (!Double.isNaN(valorNum) && valorNum >= 0) {
                                agregarCeldaGrafico(sheetHidden, rowCont, i, idmuestras, valorNum);
                            }
                        }
                    }
                }
            } catch (SQLException e) {
                logger.severe("Error en la consulta SQL: " + e.getMessage());
            }
        } catch (SQLException e) {
            logger.severe("Error en la consulta SQL: " + e.getMessage());
        }
    }

    private void consultarMBAguaCofesParaExcel(int idcliente, String tipo, java.sql.Date desdeSql, java.sql.Date hastaSql, Sheet sheet, Sheet sheetHidden, int[] rowNum, int[] rowNumGraph, int[] rowCont, Workbook workbook) {
        String sql = "SELECT vistatabla_idmuestras, vistatabla_fechaMuestreo, vistatabla_porcentajeTotalCloro, " + "vistatabla_ph, germenes, coliformesTotales, coliformesFecales, escherichia, pseudomona, s" + "higella FROM vistambagua WHERE vistatabla_idmuestras in (select idmuestras from muestras where " + "idcliente = ? and tipo = ?) and vistatabla_fechaMuestreo BETWEEN ? AND ?";
        Arrays.fill(rowCont, 0);
        CellStyle estiloPresencia = crearEstiloPresencia(workbook);
        try (Connection conexion = con.getConnection()) {
            // 1. Preparar la consulta
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, idcliente);
            ps.setString(2, tipo);
            ps.setDate(3, desdeSql);
            ps.setDate(4, hastaSql);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Row row = sheet.createRow(rowNum[0]++);
                    String idmuestras = String.valueOf(rs.getInt("vistatabla_idmuestras"));
                    row.createCell(0).setCellValue(idmuestras);
                    String fechaMuestreo = rs.getDate("vistatabla_fechaMuestreo").toString();
                    row.createCell(1).setCellValue(ExcelUtils.formatearEntradaExcel(fechaMuestreo));
                    String cloro = rs.getString("vistatabla_porcentajeTotalCloro");
                    row.createCell(2).setCellValue(ExcelUtils.formatearEntradaExcel(cloro));
                    String ph = rs.getString("vistatabla_ph");
                    row.createCell(3).setCellValue(ExcelUtils.formatearEntradaExcel(ph));
                    String germenes = rs.getString("germenes");
                    row.createCell(4).setCellValue(ExcelUtils.formatearEntradaExcel(germenes));
                    String coliformesTotales = rs.getString("coliformesTotales");
                    row.createCell(5).setCellValue(ExcelUtils.formatearEntradaExcel(coliformesTotales));
                    String coliformesFecales = rs.getString("coliformesFecales");
                    row.createCell(6).setCellValue(ExcelUtils.formatearEntradaExcel(coliformesFecales));
                    String escherichia = rs.getString("escherichia");
                    row.createCell(7).setCellValue(ExcelUtils.formatearEntradaExcel(escherichia));
                    String pseudomona = rs.getString("pseudomona");
                    row.createCell(8).setCellValue(ExcelUtils.formatearEntradaExcel(pseudomona));
                    String shigella = rs.getString("shigella");
                    row.createCell(9).setCellValue(ExcelUtils.formatearEntradaExcel(shigella));

                    double cloroGraph = ExcelUtils.extraerNumero(cloro);
                    if (!Double.isNaN(cloroGraph) && cloroGraph >= 0) {
                        agregarCeldaGrafico(sheetHidden, rowCont, 0, idmuestras, cloroGraph);
                    }
                    double phGraph = ExcelUtils.extraerNumero(ph);
                    if (!Double.isNaN(phGraph) && phGraph >= 0) {
                        agregarCeldaGrafico(sheetHidden, rowCont, 1, idmuestras, phGraph);
                    }
                    double germenesGraph = ExcelUtils.extraerNumero(germenes);
                    if (!Double.isNaN(germenesGraph) && germenesGraph >= 0) {
                        agregarCeldaGrafico(sheetHidden, rowCont, 2, idmuestras, germenesGraph);
                    }
                    double coliformesTotalesGraph = ExcelUtils.extraerNumero(coliformesTotales);
                    if (!Double.isNaN(coliformesTotalesGraph) && coliformesTotalesGraph >= 0) {
                        agregarCeldaGrafico(sheetHidden, rowCont, 3, idmuestras, coliformesTotalesGraph);
                    }
                    double coliformesFecalesGraph = ExcelUtils.extraerNumero(coliformesFecales);
                    if (!Double.isNaN(coliformesFecalesGraph) && coliformesFecalesGraph >= 0) {
                        agregarCeldaGrafico(sheetHidden, rowCont, 4, idmuestras, coliformesFecalesGraph);
                    }
                    if (escherichia.toLowerCase().contains("ausencia") || escherichia.toLowerCase().contains("presencia")) {
                        agregarCeldaPresencia(sheetHidden, rowCont, 5, idmuestras, escherichia, estiloPresencia);
                    }
                    if (pseudomona.toLowerCase().contains("ausencia") || pseudomona.toLowerCase().contains("presencia")) {
                        agregarCeldaPresencia(sheetHidden, rowCont, 6, idmuestras, pseudomona, estiloPresencia);
                    }
                    if (shigella == null) {
                        shigella = "-2";
                    }
                    if (shigella.toLowerCase().contains("ausencia") || shigella.toLowerCase().contains("presencia")) {
                        agregarCeldaPresencia(sheetHidden, rowCont, 7, idmuestras, shigella, estiloPresencia);
                    }
                }
            } catch (SQLException e) {
                logger.severe("Error en la consulta SQL: " + e.getMessage());
            }
        } catch (SQLException e) {
            logger.severe("Error en la consulta SQL: " + e.getMessage());
        }
    }

    private void consultarMBAguaRecreacionParaExcel(int idcliente, String tipo, java.sql.Date desdeSql, java.sql.Date hastaSql, Sheet sheet, Sheet sheetHidden, int[] rowNum, int[] rowNumGraph, int[] rowCont, Workbook workbook) {
        String sql = "SELECT vistatabla_idmuestras, vistatabla_fechaMuestreo, germenes, coliformesTotales, " + "coliformesFecales, escherichia, pseudomona, staphilococos, streptococos, shigella FROM vistambagua " + "WHERE vistatabla_idmuestras in (select idmuestras from muestras where idcliente = ? and tipo = ?) " + "and vistatabla_fechaMuestreo BETWEEN ? AND ?";
        Arrays.fill(rowCont, 0);
        CellStyle estiloPresencia = crearEstiloPresencia(workbook);
        try (Connection conexion = con.getConnection()) {
            // 1. Preparar la consulta
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, idcliente);
            ps.setString(2, tipo);
            ps.setDate(3, desdeSql);
            ps.setDate(4, hastaSql);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Row row = sheet.createRow(rowNum[0]++);
                    String idmuestras = String.valueOf(rs.getInt("vistatabla_idmuestras"));
                    row.createCell(0).setCellValue(idmuestras);
                    String fechaMuestreo = rs.getDate("vistatabla_fechaMuestreo").toString();
                    row.createCell(1).setCellValue(ExcelUtils.formatearEntradaExcel(fechaMuestreo));
                    String germenes = rs.getString("germenes");
                    row.createCell(2).setCellValue(ExcelUtils.formatearEntradaExcel(germenes));
                    String coliformesTotales = rs.getString("coliformesTotales");
                    row.createCell(3).setCellValue(ExcelUtils.formatearEntradaExcel(coliformesTotales));
                    String coliformesFecales = rs.getString("coliformesFecales");
                    row.createCell(4).setCellValue(ExcelUtils.formatearEntradaExcel(coliformesFecales));
                    String escherichia = rs.getString("escherichia");
                    row.createCell(5).setCellValue(ExcelUtils.formatearEntradaExcel(escherichia));
                    String pseudomona = rs.getString("pseudomona");
                    row.createCell(6).setCellValue(ExcelUtils.formatearEntradaExcel(pseudomona));
                    String staphilococos = rs.getString("staphilococos");
                    row.createCell(7).setCellValue(ExcelUtils.formatearEntradaExcel(staphilococos));
                    String streptococos = rs.getString("streptococos");
                    row.createCell(8).setCellValue(ExcelUtils.formatearEntradaExcel(streptococos));
                    String shigella = rs.getString("shigella");
                    row.createCell(9).setCellValue(ExcelUtils.formatearEntradaExcel(shigella));

                    double germenesGraph = ExcelUtils.extraerNumero(germenes);
                    if (!Double.isNaN(germenesGraph) && germenesGraph >= 0) {
                        agregarCeldaGrafico(sheetHidden, rowCont, 0, idmuestras, germenesGraph);
                    }
                    double coliformesTotalesGraph = ExcelUtils.extraerNumero(coliformesTotales);
                    if (!Double.isNaN(coliformesTotalesGraph) && coliformesTotalesGraph >= 0) {
                        agregarCeldaGrafico(sheetHidden, rowCont, 1, idmuestras, coliformesTotalesGraph);
                    }
                    double coliformesFecalesGraph = ExcelUtils.extraerNumero(coliformesFecales);
                    if (!Double.isNaN(coliformesFecalesGraph) && coliformesFecalesGraph >= 0) {
                        agregarCeldaGrafico(sheetHidden, rowCont, 2, idmuestras, coliformesFecalesGraph);
                    }
                    if (escherichia.toLowerCase().contains("ausencia") || escherichia.toLowerCase().contains("presencia")) {
                        agregarCeldaPresencia(sheetHidden, rowCont, 3, idmuestras, escherichia, estiloPresencia);
                    }
                    if (pseudomona.toLowerCase().contains("ausencia") || pseudomona.toLowerCase().contains("presencia")) {
                        agregarCeldaPresencia(sheetHidden, rowCont, 4, idmuestras, pseudomona, estiloPresencia);
                    }
                    double staphilococosGraph = ExcelUtils.extraerNumero(staphilococos);
                    if (!Double.isNaN(staphilococosGraph) && staphilococosGraph >= 0) {
                        agregarCeldaGrafico(sheetHidden, rowCont, 5, idmuestras, staphilococosGraph);
                    }
                    double streptococosGraph = ExcelUtils.extraerNumero(streptococos);
                    if (!Double.isNaN(streptococosGraph) && streptococosGraph >= 0) {
                        agregarCeldaGrafico(sheetHidden, rowCont, 6, idmuestras, streptococosGraph);
                    }
                    double shigellaGraph = ExcelUtils.extraerNumero(shigella);
                    if (!Double.isNaN(shigellaGraph) && shigellaGraph >= 0) {
                        agregarCeldaGrafico(sheetHidden, rowCont, 7, idmuestras, shigellaGraph);
                    }
                }
            } catch (SQLException e) {
                logger.severe("Error en la consulta SQL: " + e.getMessage());
            }
        } catch (SQLException e) {
            logger.severe("Error en la consulta SQL: " + e.getMessage());
        }
    }

    private void consultarMBAguaCodigoParaExcel(int idcliente, String tipo, java.sql.Date desdeSql, java.sql.Date hastaSql, Sheet sheet, Sheet sheetHidden, int[] rowNum, int[] rowNumGraph, int[] rowCont, Workbook workbook) {
        String sql = "SELECT vistatabla_idmuestras, vistatabla_fechaMuestreo, vistatabla_porcentajeTotalCloro, " + "vistatabla_ph, germenes, coliformesTotales, coliformesFecales, escherichia, pseudomona, mohos, s" + "higella FROM vistambagua WHERE vistatabla_idmuestras in (select idmuestras from muestras where " + "idcliente = ? and tipo = ?) and vistatabla_fechaMuestreo BETWEEN ? AND ?";
        Arrays.fill(rowCont, 0);
        CellStyle estiloPresencia = crearEstiloPresencia(workbook);
        try (Connection conexion = con.getConnection()) {
            // 1. Preparar la consulta
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, idcliente);
            ps.setString(2, tipo);
            ps.setDate(3, desdeSql);
            ps.setDate(4, hastaSql);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Row row = sheet.createRow(rowNum[0]++);
                    String idmuestras = String.valueOf(rs.getInt("vistatabla_idmuestras"));
                    row.createCell(0).setCellValue(idmuestras);
                    String fechaMuestreo = rs.getDate("vistatabla_fechaMuestreo").toString();
                    row.createCell(1).setCellValue(ExcelUtils.formatearEntradaExcel(fechaMuestreo));
                    String cloro = rs.getString("vistatabla_porcentajeTotalCloro");
                    row.createCell(2).setCellValue(ExcelUtils.formatearEntradaExcel(cloro));
                    String ph = rs.getString("vistatabla_ph");
                    row.createCell(3).setCellValue(ExcelUtils.formatearEntradaExcel(ph));
                    String germenes = rs.getString("germenes");
                    row.createCell(4).setCellValue(ExcelUtils.formatearEntradaExcel(germenes));
                    String coliformesTotales = rs.getString("coliformesTotales");
                    row.createCell(5).setCellValue(ExcelUtils.formatearEntradaExcel(coliformesTotales));
                    String coliformesFecales = rs.getString("coliformesFecales");
                    row.createCell(6).setCellValue(ExcelUtils.formatearEntradaExcel(coliformesFecales));
                    String escherichia = rs.getString("escherichia");
                    row.createCell(7).setCellValue(ExcelUtils.formatearEntradaExcel(escherichia));
                    String pseudomona = rs.getString("pseudomona");
                    row.createCell(8).setCellValue(ExcelUtils.formatearEntradaExcel(pseudomona));
                    String mohos = rs.getString("mohos");
                    row.createCell(9).setCellValue(ExcelUtils.formatearEntradaExcel(mohos));
                    String shigella = rs.getString("shigella");
                    row.createCell(10).setCellValue(ExcelUtils.formatearEntradaExcel(shigella));
                    double cloroGraph = ExcelUtils.extraerNumero(cloro);
                    if (!Double.isNaN(cloroGraph) && cloroGraph >= 0) {
                        agregarCeldaGrafico(sheetHidden, rowCont, 0, idmuestras, cloroGraph);
                    }
                    double phGraph = ExcelUtils.extraerNumero(ph);
                    if (!Double.isNaN(phGraph) && phGraph >= 0) {
                        agregarCeldaGrafico(sheetHidden, rowCont, 1, idmuestras, phGraph);
                    }
                    double germenesGraph = ExcelUtils.extraerNumero(germenes);
                    if (!Double.isNaN(germenesGraph) && germenesGraph >= 0) {
                        agregarCeldaGrafico(sheetHidden, rowCont, 2, idmuestras, germenesGraph);
                    }
                    double coliformesTotalesGraph = ExcelUtils.extraerNumero(coliformesTotales);
                    if (!Double.isNaN(coliformesTotalesGraph) && coliformesTotalesGraph >= 0) {
                        agregarCeldaGrafico(sheetHidden, rowCont, 3, idmuestras, coliformesTotalesGraph);
                    }
                    double coliformesFecalesGraph = ExcelUtils.extraerNumero(coliformesFecales);
                    if (!Double.isNaN(coliformesFecalesGraph) && coliformesFecalesGraph >= 0) {
                        agregarCeldaGrafico(sheetHidden, rowCont, 4, idmuestras, coliformesFecalesGraph);
                    }
                    if (escherichia.toLowerCase().contains("ausencia") || escherichia.toLowerCase().contains("presencia")) {
                        agregarCeldaPresencia(sheetHidden, rowCont, 5, idmuestras, escherichia, estiloPresencia);
                    }
                    if (pseudomona.toLowerCase().contains("ausencia") || pseudomona.toLowerCase().contains("presencia")) {
                        agregarCeldaPresencia(sheetHidden, rowCont, 6, idmuestras, pseudomona, estiloPresencia);
                    }
                    double mohosGraph = ExcelUtils.extraerNumero(mohos);
                    if (!Double.isNaN(mohosGraph) && mohosGraph >= 0) {
                        agregarCeldaGrafico(sheetHidden, rowCont, 7, idmuestras, mohosGraph);
                    }
                    double shigellaGraph = ExcelUtils.extraerNumero(shigella);
                    if (!Double.isNaN(shigellaGraph) && shigellaGraph >= 0) {
                        agregarCeldaGrafico(sheetHidden, rowCont, 8, idmuestras, shigellaGraph);
                    }
                }
            } catch (SQLException e) {
                logger.severe("Error en la consulta SQL: " + e.getMessage());
            }
        } catch (SQLException e) {
            logger.severe("Error en la consulta SQL: " + e.getMessage());
        }
    }

    private void consultarMBAguaBalnearioParaExcel(int idcliente, String tipo, java.sql.Date desdeSql, java.sql.Date hastaSql, Sheet sheet, Sheet sheetHidden, int[] rowNum, int[] rowNumGraph, int[] rowCont, Workbook workbook) {
        String sql = "SELECT vistatabla_idmuestras, vistatabla_fechaMuestreo, coliformesTotales, escherichia, " + "shigella FROM vistambagua WHERE vistatabla_idmuestras in (select idmuestras from muestras where " + "idcliente = ? and tipo = ?) and vistatabla_fechaMuestreo BETWEEN ? AND ?";
        Arrays.fill(rowCont, 0);
        CellStyle estiloPresencia = crearEstiloPresencia(workbook);
        try (Connection conexion = con.getConnection()) {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, idcliente);
            ps.setString(2, tipo);
            ps.setDate(3, desdeSql);
            ps.setDate(4, hastaSql);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Row row = sheet.createRow(rowNum[0]++);
                    String idmuestras = String.valueOf(rs.getInt("vistatabla_idmuestras"));
                    row.createCell(0).setCellValue(idmuestras);
                    String fechaMuestreo = rs.getDate("vistatabla_fechaMuestreo").toString();
                    row.createCell(1).setCellValue(ExcelUtils.formatearEntradaExcel(fechaMuestreo));
                    String coliformesTotales = rs.getString("coliformesTotales");
                    row.createCell(2).setCellValue(ExcelUtils.formatearEntradaExcel(coliformesTotales));
                    String escherichia = rs.getString("escherichia");
                    row.createCell(3).setCellValue(ExcelUtils.formatearEntradaExcel(escherichia));
                    String shigella = rs.getString("shigella");
                    row.createCell(4).setCellValue(ExcelUtils.formatearEntradaExcel(shigella));

                    double coliformesTotalesGraph = ExcelUtils.extraerNumero(coliformesTotales);
                    if (!Double.isNaN(coliformesTotalesGraph) && coliformesTotalesGraph >= 0) {
                        agregarCeldaGrafico(sheetHidden, rowCont, 0, idmuestras, coliformesTotalesGraph);
                    }
                    if (escherichia.toLowerCase().contains("ausencia") || escherichia.toLowerCase().contains("presencia")) {
                        agregarCeldaPresencia(sheetHidden, rowCont, 1, idmuestras, escherichia, estiloPresencia);
                    }
                    double shigellaGraph = ExcelUtils.extraerNumero(shigella);
                    if (!Double.isNaN(shigellaGraph) && shigellaGraph >= 0) {
                        agregarCeldaGrafico(sheetHidden, rowCont, 2, idmuestras, shigellaGraph);
                    }
                }
            } catch (SQLException e) {
                logger.severe("Error en la consulta SQL: " + e.getMessage());
            }
        } catch (SQLException e) {
            logger.severe("Error en la consulta SQL: " + e.getMessage());
        }
    }

    private void consultarHisopadosParaExcel(int idcliente, String tipo, java.sql.Date desdeSql, java.sql.Date hastaSql, Sheet sheet, Sheet sheetHidden, int[] rowNum, int[] rowCont, Workbook workbook) {
        String sql = "SELECT vistatabla_idmuestras, vistatabla_fechaMuestreo, germenes, coliformesTotales, " + "coliformesFecales, escherichia, staphilococos, enterobacterias, salmonella, listeria, mohos, " + "vibrio FROM vistahisopado WHERE vistatabla_idmuestras in (select idmuestras from muestras where " + "idcliente = ? and tipo = ?) and vistatabla_fechaMuestreo BETWEEN ? AND ?";
        Arrays.fill(rowCont, 0);
        CellStyle estiloPresencia = crearEstiloPresencia(workbook);
        try (Connection conexion = con.getConnection()) {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, idcliente);
            ps.setString(2, tipo);
            ps.setDate(3, desdeSql);
            ps.setDate(4, hastaSql);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Row row = sheet.createRow(rowNum[0]++);
                    String idmuestras = String.valueOf(rs.getInt("vistatabla_idmuestras"));
                    row.createCell(0).setCellValue(idmuestras);
                    String fechaMuestreo = rs.getDate("vistatabla_fechaMuestreo").toString();
                    row.createCell(1).setCellValue(ExcelUtils.formatearEntradaExcel(fechaMuestreo));
                    String germenes = rs.getString("germenes");
                    row.createCell(2).setCellValue(ExcelUtils.formatearEntradaExcel(germenes));
                    String coliformesTotales = rs.getString("coliformesTotales");
                    row.createCell(3).setCellValue(ExcelUtils.formatearEntradaExcel(coliformesTotales));
                    String coliformesFecales = rs.getString("coliformesFecales");
                    row.createCell(4).setCellValue(ExcelUtils.formatearEntradaExcel(coliformesFecales));
                    String escherichia = rs.getString("escherichia");
                    row.createCell(5).setCellValue(ExcelUtils.formatearEntradaExcel(escherichia));
                    String staphilococos = rs.getString("staphilococos");
                    row.createCell(6).setCellValue(ExcelUtils.formatearEntradaExcel(staphilococos));
                    String enterobacterias = rs.getString("enterobacterias");
                    row.createCell(7).setCellValue(ExcelUtils.formatearEntradaExcel(enterobacterias));
                    String salmonella = rs.getString("salmonella");
                    row.createCell(8).setCellValue(ExcelUtils.formatearEntradaExcel(salmonella));
                    String listeria = rs.getString("listeria");
                    row.createCell(9).setCellValue(ExcelUtils.formatearEntradaExcel(listeria));
                    String mohos = rs.getString("mohos");
                    row.createCell(10).setCellValue(ExcelUtils.formatearEntradaExcel(mohos));
                    String vibrio = rs.getString("vibrio");
                    row.createCell(11).setCellValue(ExcelUtils.formatearEntradaExcel(vibrio));

                    double germenesGraph = ExcelUtils.extraerNumero(germenes);
                    if (!Double.isNaN(germenesGraph) && germenesGraph >= 0) {
                        agregarCeldaGrafico(sheetHidden, rowCont, 0, idmuestras, germenesGraph);
                    }
                    double coliformesTotalesGraph = ExcelUtils.extraerNumero(coliformesTotales);
                    if (!Double.isNaN(coliformesTotalesGraph) && coliformesTotalesGraph >= 0) {
                        agregarCeldaGrafico(sheetHidden, rowCont, 1, idmuestras, coliformesTotalesGraph);
                    }
                    if (coliformesFecales.toLowerCase().contains("ausencia") || coliformesFecales.toLowerCase().contains("presencia")) {
                        agregarCeldaPresencia(sheetHidden, rowCont, 2, idmuestras, coliformesFecales, estiloPresencia);
                    }
                    if (escherichia.toLowerCase().contains("ausencia") || escherichia.toLowerCase().contains("presencia")) {
                        agregarCeldaPresencia(sheetHidden, rowCont, 3, idmuestras, escherichia, estiloPresencia);
                    }
                    double staphilococosGraph = ExcelUtils.extraerNumero(staphilococos);
                    if (!Double.isNaN(staphilococosGraph) && staphilococosGraph >= 0) {
                        agregarCeldaGrafico(sheetHidden, rowCont, 4, idmuestras, staphilococosGraph);
                    }
                    double enterobacteriasGraph = ExcelUtils.extraerNumero(enterobacterias);
                    if (!Double.isNaN(enterobacteriasGraph) && enterobacteriasGraph >= 0) {
                        agregarCeldaGrafico(sheetHidden, rowCont, 5, idmuestras, enterobacteriasGraph);
                    }
                    double salmonellaGraph = ExcelUtils.extraerNumero(salmonella);
                    if (!Double.isNaN(salmonellaGraph) && salmonellaGraph >= 0) {
                        agregarCeldaGrafico(sheetHidden, rowCont, 6, idmuestras, salmonellaGraph);
                    }
                    if (listeria.toLowerCase().contains("ausencia") || listeria.toLowerCase().contains("presencia")) {
                        agregarCeldaPresencia(sheetHidden, rowCont, 7, idmuestras, listeria, estiloPresencia);
                    } else {
                        Double listeriaGraph = ExcelUtils.extraerNumero(listeria);
                        if (!Double.isNaN(listeriaGraph) && listeriaGraph >= 0) {
                            agregarCeldaGrafico(sheetHidden, rowCont, 7, idmuestras, listeriaGraph);
                        }
                    }
                    double mohosGraph = ExcelUtils.extraerNumero(mohos);
                    if (!Double.isNaN(mohosGraph) && mohosGraph >= 0) {
                        agregarCeldaGrafico(sheetHidden, rowCont, 8, idmuestras, mohosGraph);
                    }
                    double vibrioGraph = ExcelUtils.extraerNumero(vibrio);
                    if (!Double.isNaN(vibrioGraph) && vibrioGraph >= 0) {
                        agregarCeldaGrafico(sheetHidden, rowCont, 9, idmuestras, vibrioGraph);
                    }
                }
            } catch (SQLException e) {
                logger.severe("Error en la consulta SQL: " + e.getMessage());
            }
        } catch (SQLException e) {
            logger.severe("Error en la consulta SQL: " + e.getMessage());
        }
    }

    private void consultarHisopadosLimitesParaExcel(int idcliente, String tipo, java.sql.Date desdeSql, java.sql.Date hastaSql, Sheet sheet, Sheet sheetHidden, int[] rowNum, int[] rowCont, Workbook workbook) {
        String sql = "SELECT vistatabla_idmuestras, vistatabla_fechaMuestreo, germenes, coliformesTotales, " + "coliformesFecales, escherichia, staphilococos, enterobacterias FROM vistahisopado WHERE " + "vistatabla_idmuestras in (select idmuestras from muestras where idcliente = ? and tipo = ?) " + "and vistatabla_fechaMuestreo BETWEEN ? AND ?";
        Arrays.fill(rowCont, 0);
        CellStyle estiloPresencia = crearEstiloPresencia(workbook);
        try (Connection conexion = con.getConnection()) {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, idcliente);
            ps.setString(2, tipo);
            ps.setDate(3, desdeSql);
            ps.setDate(4, hastaSql);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Row row = sheet.createRow(rowNum[0]++);
                    String idmuestras = String.valueOf(rs.getInt("vistatabla_idmuestras"));
                    row.createCell(0).setCellValue(idmuestras);
                    String fechaMuestreo = rs.getDate("vistatabla_fechaMuestreo").toString();
                    row.createCell(1).setCellValue(ExcelUtils.formatearEntradaExcel(fechaMuestreo));
                    String germenes = rs.getString("germenes");
                    row.createCell(2).setCellValue(ExcelUtils.formatearEntradaExcel(germenes));
                    String coliformesTotales = rs.getString("coliformesTotales");
                    row.createCell(3).setCellValue(ExcelUtils.formatearEntradaExcel(coliformesTotales));
                    String coliformesFecales = rs.getString("coliformesFecales");
                    row.createCell(4).setCellValue(ExcelUtils.formatearEntradaExcel(coliformesFecales));
                    String escherichia = rs.getString("escherichia");
                    row.createCell(5).setCellValue(ExcelUtils.formatearEntradaExcel(escherichia));
                    String staphilococos = rs.getString("staphilococos");
                    row.createCell(6).setCellValue(ExcelUtils.formatearEntradaExcel(staphilococos));
                    String enterobacterias = rs.getString("enterobacterias");
                    row.createCell(7).setCellValue(ExcelUtils.formatearEntradaExcel(enterobacterias));

                    double germenesGraph = ExcelUtils.extraerNumero(germenes);
                    if (!Double.isNaN(germenesGraph) && germenesGraph >= 0) {
                        agregarCeldaGrafico(sheetHidden, rowCont, 0, idmuestras, germenesGraph);
                    }
                    double coliformesTotalesGraph = ExcelUtils.extraerNumero(coliformesTotales);
                    if (!Double.isNaN(coliformesTotalesGraph) && coliformesTotalesGraph >= 0) {
                        agregarCeldaGrafico(sheetHidden, rowCont, 1, idmuestras, coliformesTotalesGraph);
                    }
                    if (coliformesFecales.toLowerCase().contains("ausencia") || coliformesFecales.toLowerCase().contains("presencia")) {
                        agregarCeldaPresencia(sheetHidden, rowCont, 2, idmuestras, coliformesFecales, estiloPresencia);
                    }
                    if (escherichia.toLowerCase().contains("ausencia") || escherichia.toLowerCase().contains("presencia")) {
                        agregarCeldaPresencia(sheetHidden, rowCont, 3, idmuestras, escherichia, estiloPresencia);
                    }
                    double staphilococosGraph = ExcelUtils.extraerNumero(staphilococos);
                    if (!Double.isNaN(staphilococosGraph) && staphilococosGraph >= 0) {
                        agregarCeldaGrafico(sheetHidden, rowCont, 4, idmuestras, staphilococosGraph);
                    }
                    double enterobacteriasGraph = ExcelUtils.extraerNumero(enterobacterias);
                    if (!Double.isNaN(enterobacteriasGraph) && enterobacteriasGraph >= 0) {
                        agregarCeldaGrafico(sheetHidden, rowCont, 5, idmuestras, enterobacteriasGraph);
                    }
                }
            } catch (SQLException e) {
                logger.severe("Error en la consulta SQL: " + e.getMessage());
            }
        } catch (SQLException e) {
            logger.severe("Error en la consulta SQL: " + e.getMessage());
        }
    }

    private void consultarDeterminacionesParaExcel(int idcliente, String tipo, java.sql.Date desdeSql, java.sql.Date hastaSql, Sheet sheet, Sheet sheetHidden, int[] rowNum, int[] rowCont, Workbook workbook) {
        String sql = """
                SELECT d.idmuestras, vd.fechaAnalisis, d.acidez, d.acidoCianurico, d.acidoSorbico, d.actividadAcuosa, 
                       d.alcalinidad, d.alcohol, d.aluminio, d.amonio, d.amoniacos, d.antimonio, d.aroma, d.arsenico, 
                       d.asbesto, d.aspecto, d.azucares, d.azucaresDeducidas, d.azucaresInvertidos, d.azucaresReductores, 
                       d.bario, d.bicarbonatos, d.boro, d.bromuro, d.cadmioTotal, d.calcio, d.caracteristicas, d.carbonatos, 
                       d.cenizas, d.cenizasInsolublesAcido, d.cenizasInsolublesAgua, d.cianuros, d.cloroActivo, d.cloroResidual, 
                       d.cloroTotal, d.cloruros, d.cobalto, d.cobre, d.colesterol, d.color, d.colorantesartificiales, 
                       d.colorantesnaturales, d.colorantes, d.conductividad, d.cromoHexavalente, d.dbo, d.detergentes,
                       d.dqo, d.dureza, d.edulcorantes, d.estano, d.extracto, d.extractoseco, d.fenoles, d.fluoruros, 
                       d.fluor, d.fosfatos, d.fosforoTotal, d.gliadinas, d.gluten, d.gradoFermentacion, d.gradosBrix, 
                       d.grasa, d.grasasCacao, d.grasasLeche, d.grasasyAceites, d.hidracina, d.hidrocarburos, 
                       d.hidrocarburosc6, d.hidrocarburosc6_c35, d.hidrocarburosc6_c8, d.hidrocarburosc8_c10, d.hidrocarburosc10_c12,
                       d.hidrocarburosc12_c16, d.hidrocarburosc16_c21, d.hidrocarburosc21_c35, d.hierro, d.humedad, d.magnesio, d.manganeso, 
                       d.materiagrasa, d.mercurioTotal, d.molibdeno, d.nitratos, d.nitritos, d.nitrogenoAmoniacal, d.nitrogenoTotal, 
                       d.niquel, d.observacionMicroscopica, d.olor, d.organoclorados, d.oxigenoDisuelto, d.ozono, d.peroxidoHidrogeno,
                       d.ph, d.plata, d.plomo, d.porcentajeCloruro, d.potasio, d.propionato, d.relacion, d.residuo105, d.residuo180, 
                       d.residuoSeco, d.sabor, d.selenio, d.silicatos, d.sodio, d.sulfatos, d.sulfuros, d.sustancias, 
                       d.sustanciasEterEtilico, d.solidosTotales, d.solidosNoGrasosCacao, d.solidosNoGrasos, d.solidos10Minutos, 
                       d.solidos2Horas, d.solidosSuspendidosTotales, d.solidosSuspendidosVolatiles, d.temperatura, d.turbidez, d.vanadio, 
                       d.vibrio, d.zinc FROM determinaciones d join vistafqcompleto vd on d.idmuestras = vd.idmuestras WHERE d.idmuestras IN (SELECT idmuestras FROM muestras WHERE idcliente = ? AND tipo = ? AND fechaAnalisis BETWEEN ? AND ?)
                """;

        Arrays.fill(rowCont, 0);

        try (Connection conexion = con.getConnection()) {
            PreparedStatement ps = conexion.prepareStatement(sql);
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
                        row.createCell(i - 1).setCellValue(ExcelUtils.formatearEntradaExcel(valor));
                    }

                    for (int i = 3; i <= columnCount; i++) {
                        String valor = rs.getString(i);
                        if (valor == null) {
                            continue;
                        }
                        Double valorNum = null;
                        String valorLower = valor.toLowerCase();

                        if (valorLower.contains("ausencia") || valorLower.contains("presencia")) {
                            valorNum = Double.parseDouble(ExcelUtils.extraerAusenciaPresencia(valor));
                        } else if (valorLower.contains("lc")) {
                            valorNum = extraerValorLC(valor);
                        } else {
                            valorNum = ExcelUtils.extraerNumero(valor);
                        }

                        if (valorNum != null && !Double.isNaN(valorNum)) {
                            agregarCeldaGrafico(sheetHidden, rowCont, i, idmuestras, valorNum);
                        }
                    }
                }
            } catch (SQLException e) {
                logger.severe("Error en la consulta SQL: " + e.getMessage());
            }
        } catch (SQLException e) {
            logger.severe("Error en Determinaciones: " + e.getMessage());
        }
    }

    private void consultarFQAguaParaExcel(int idcliente, String tipo, java.sql.Date desdeSql, java.sql.Date hastaSql, Sheet sheet, Sheet sheetHidden, int[] rowNum, int[] rowCont, Workbook workbook) {
        String sql = "SELECT vistatabla_idmuestras, vistatabla_fechaMuestreo, ph, porcentajeTotalCloro, olor, color, " + "turbidez, alcalinidad, durezatotal, conductividad, solidosDisueltos, hierro, nitrato, nitritos, " + "sulfatos FROM vistafqagua WHERE vistatabla_idmuestras in (select idmuestras from muestras where " + "idcliente = ? and tipo = ?) and vistatabla_fechaMuestreo BETWEEN ? AND ?";
        Arrays.fill(rowCont, 0);
        try (Connection conexion = con.getConnection()) {
            // 1. Preparar la consulta
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, idcliente);
            ps.setString(2, tipo);
            ps.setDate(3, desdeSql);
            ps.setDate(4, hastaSql);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Row row = sheet.createRow(rowNum[0]++);
                    String idmuestras = String.valueOf(rs.getInt("vistatabla_idmuestras"));
                    row.createCell(0).setCellValue(idmuestras);
                    String fechaMuestreo = rs.getDate("vistatabla_fechaMuestreo").toString();
                    row.createCell(1).setCellValue(ExcelUtils.formatearEntradaExcel(fechaMuestreo));
                    String ph = rs.getString("ph");
                    row.createCell(2).setCellValue(ExcelUtils.formatearEntradaExcel(ph));
                    String cloroTotal = rs.getString("porcentajeTotalCloro");
                    row.createCell(3).setCellValue(ExcelUtils.formatearEntradaExcel(cloroTotal));
                    String olor = rs.getString("olor");
                    row.createCell(4).setCellValue(ExcelUtils.formatearEntradaExcel(olor));
                    String color = rs.getString("color");
                    row.createCell(5).setCellValue(ExcelUtils.formatearEntradaExcel(color));
                    String turbidez = rs.getString("turbidez");
                    row.createCell(6).setCellValue(ExcelUtils.formatearEntradaExcel(turbidez));
                    String alcalinidad = rs.getString("alcalinidad");
                    row.createCell(7).setCellValue(ExcelUtils.formatearEntradaExcel(alcalinidad));
                    String durezatotal = rs.getString("durezatotal");
                    row.createCell(8).setCellValue(ExcelUtils.formatearEntradaExcel(durezatotal));
                    String conductividad = rs.getString("conductividad");
                    row.createCell(9).setCellValue(ExcelUtils.formatearEntradaExcel(conductividad));
                    String solidosDisueltos = rs.getString("solidosDisueltos");
                    row.createCell(10).setCellValue(ExcelUtils.formatearEntradaExcel(solidosDisueltos));
                    String hierro = rs.getString("hierro");
                    row.createCell(11).setCellValue(ExcelUtils.formatearEntradaExcel(hierro));
                    String nitrato = rs.getString("nitrato");
                    row.createCell(12).setCellValue(ExcelUtils.formatearEntradaExcel(nitrato));
                    String nitritos = rs.getString("nitritos");
                    row.createCell(13).setCellValue(ExcelUtils.formatearEntradaExcel(nitritos));
                    String sulfatos = rs.getString("sulfatos");
                    row.createCell(14).setCellValue(ExcelUtils.formatearEntradaExcel(sulfatos));

                    double phGraph = ExcelUtils.extraerNumero(ph);
                    if (!Double.isNaN(phGraph) && phGraph >= 0) {
                        agregarCeldaGrafico(sheetHidden, rowCont, 0, idmuestras, phGraph);
                    }
                    double cloroGraph = ExcelUtils.extraerNumero(cloroTotal);
                    if (!Double.isNaN(cloroGraph) && cloroGraph >= 0) {
                        agregarCeldaGrafico(sheetHidden, rowCont, 1, idmuestras, cloroGraph);
                    }
                    double colorGraph = ExcelUtils.extraerNumero(color);
                    if (!Double.isNaN(colorGraph) && colorGraph >= 0) {
                        agregarCeldaGrafico(sheetHidden, rowCont, 3, idmuestras, colorGraph);
                    }
                    double turbidezGraph = ExcelUtils.extraerNumero(turbidez);
                    if (!Double.isNaN(turbidezGraph) && turbidezGraph >= 0) {
                        agregarCeldaGrafico(sheetHidden, rowCont, 4, idmuestras, turbidezGraph);
                    }
                    double alcalinidadGraph = ExcelUtils.extraerNumero(alcalinidad);
                    if (!Double.isNaN(alcalinidadGraph) && alcalinidadGraph >= 0) {
                        agregarCeldaGrafico(sheetHidden, rowCont, 5, idmuestras, alcalinidadGraph);
                    }
                    double durezaGraph = ExcelUtils.extraerNumero(durezatotal);
                    if (!Double.isNaN(durezaGraph) && durezaGraph >= 0) {
                        agregarCeldaGrafico(sheetHidden, rowCont, 6, idmuestras, durezaGraph);
                    }
                    double conductividadGraph = ExcelUtils.extraerNumero(conductividad);
                    if (!Double.isNaN(conductividadGraph) && conductividadGraph >= 0) {
                        agregarCeldaGrafico(sheetHidden, rowCont, 7, idmuestras, conductividadGraph);
                    }
                    double solidosGraph = ExcelUtils.extraerNumero(solidosDisueltos);
                    if (!Double.isNaN(solidosGraph) && solidosGraph >= 0) {
                        agregarCeldaGrafico(sheetHidden, rowCont, 8, idmuestras, solidosGraph);
                    }
                    double hierroGraph = ExcelUtils.extraerNumero(hierro);
                    if (!Double.isNaN(hierroGraph) && hierroGraph >= 0) {
                        agregarCeldaGrafico(sheetHidden, rowCont, 9, idmuestras, hierroGraph);
                    }
                    double nitratoGraph = ExcelUtils.extraerNumero(nitrato);
                    if (!Double.isNaN(nitratoGraph) && nitratoGraph >= 0) {
                        agregarCeldaGrafico(sheetHidden, rowCont, 10, idmuestras, nitratoGraph);
                    }
                    double nitritosGraph = ExcelUtils.extraerNumero(nitritos);
                    if (!Double.isNaN(nitritosGraph) && nitritosGraph >= 0) {
                        agregarCeldaGrafico(sheetHidden, rowCont, 11, idmuestras, nitritosGraph);
                    }
                    double sulfatosGraph = ExcelUtils.extraerNumero(sulfatos);
                    if (!Double.isNaN(sulfatosGraph) && sulfatosGraph >= 0) {
                        agregarCeldaGrafico(sheetHidden, rowCont, 12, idmuestras, sulfatosGraph);
                    }
                }
            } catch (SQLException e) {
                logger.severe("Error en la consulta SQL: " + e.getMessage());
            }
        } catch (SQLException e) {
            logger.severe("Error en la consulta SQL: " + e.getMessage());
        }
    }

    private void consultarMBChocolatesParaExcel(int idcliente, String tipo, java.sql.Date desdeSql, java.sql.Date hastaSql, Sheet sheet, Sheet sheetHidden, int[] rowNum, int[] rowCont, Workbook workbook) {
        String sql = "SELECT idmuestras, fechaMuestreo, germenes, coliformesTotales, coliformesFecales, escherichia, " + "staphilococos, mohos, salmonella FROM vistambchocolates WHERE idmuestras in (select idmuestras from " + "muestras where idcliente = ? and tipo = ?) and fechaMuestreo BETWEEN ? AND ?";
        for (int i = 0; i < rowCont.length; i++) {
            rowCont[i] = 0;
        }
        CellStyle estiloPresencia = crearEstiloPresencia(workbook);
        try (Connection conexion = con.getConnection()) {
            // 1. Preparar la consulta
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, idcliente);
            ps.setString(2, tipo);
            ps.setDate(3, desdeSql);
            ps.setDate(4, hastaSql);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Row row = sheet.createRow(rowNum[0]++);
                    String idmuestras = String.valueOf(rs.getInt("idmuestras"));
                    row.createCell(0).setCellValue(idmuestras);
                    String fechaMuestreo = rs.getDate("fechaMuestreo").toString();
                    row.createCell(1).setCellValue(ExcelUtils.formatearEntradaExcel(fechaMuestreo));
                    String germenes = rs.getString("germenes");
                    row.createCell(2).setCellValue(ExcelUtils.formatearEntradaExcel(germenes));
                    String coliformesTotales = rs.getString("coliformesTotales");
                    row.createCell(3).setCellValue(ExcelUtils.formatearEntradaExcel(coliformesTotales));
                    String coliformesFecales = rs.getString("coliformesFecales");
                    row.createCell(4).setCellValue(ExcelUtils.formatearEntradaExcel(coliformesFecales));
                    String escherichia = rs.getString("escherichia");
                    row.createCell(5).setCellValue(ExcelUtils.formatearEntradaExcel(escherichia));
                    String staphilococos = rs.getString("staphilococos");
                    row.createCell(6).setCellValue(ExcelUtils.formatearEntradaExcel(staphilococos));
                    String mohos = rs.getString("mohos");
                    row.createCell(7).setCellValue(ExcelUtils.formatearEntradaExcel(mohos));
                    String salmonella = rs.getString("salmonella");
                    row.createCell(8).setCellValue(ExcelUtils.formatearEntradaExcel(salmonella));

                    double germenesGraph = ExcelUtils.extraerNumero(germenes);
                    if (!Double.isNaN(germenesGraph) && germenesGraph >= 0) {
                        agregarCeldaGrafico(sheetHidden, rowCont, 0, idmuestras, germenesGraph);
                    }
                    double coliformesTotalesGraph = ExcelUtils.extraerNumero(coliformesTotales);
                    if (!Double.isNaN(coliformesTotalesGraph) && coliformesTotalesGraph >= 0) {
                        agregarCeldaGrafico(sheetHidden, rowCont, 1, idmuestras, coliformesTotalesGraph);
                    }
                    double coliformesFecalesGraph = ExcelUtils.extraerNumero(coliformesFecales);
                    if (!Double.isNaN(coliformesFecalesGraph) && coliformesFecalesGraph >= 0) {
                        agregarCeldaGrafico(sheetHidden, rowCont, 2, idmuestras, coliformesFecalesGraph);
                    }
                    if (escherichia != null && (escherichia.toLowerCase().contains("ausencia") || escherichia.toLowerCase().contains("presencia"))) {
                        agregarCeldaPresencia(sheetHidden, rowCont, 3, idmuestras, escherichia, estiloPresencia);
                    }
                    double staphilococosGraph = ExcelUtils.extraerNumero(staphilococos);
                    if (!Double.isNaN(staphilococosGraph) && staphilococosGraph >= 0) {
                        agregarCeldaGrafico(sheetHidden, rowCont, 4, idmuestras, staphilococosGraph);
                    }
                    double mohosGraph = ExcelUtils.extraerNumero(mohos);
                    if (!Double.isNaN(mohosGraph) && mohosGraph >= 0) {
                        agregarCeldaGrafico(sheetHidden, rowCont, 5, idmuestras, mohosGraph);
                    }
                    if (salmonella != null && (salmonella.toLowerCase().contains("ausencia") || salmonella.toLowerCase().contains("presencia"))) {
                        agregarCeldaPresencia(sheetHidden, rowCont, 6, idmuestras, salmonella, estiloPresencia);
                    }
                }
            } catch (SQLException e) {
                logger.severe("Error en la consulta SQL: " + e.getMessage());
            }
        } catch (SQLException e) {
            logger.severe("Error en la consulta SQL: " + e.getMessage());
        }
    }

    private void consultarMBAlimentosParaExcel(int idcliente, String tipo, java.sql.Date desdeSql, java.sql.Date hastaSql, Sheet sheet, Sheet sheetHidden, int[] rowNum, int[] rowCont, Workbook workbook) {
        String sql = "SELECT idmuestras, fechaAnalisis, germenes, coliformesTotales, coliformesFecales, escherichia, " + "escherichiah7, escherichia157, enterobacterias, staphilococos, mohosLevaduras, salmonella, listeria, " + "bacillus, perfringens, sulfito, campilobacter, coliformesTotalesA30, coliformesTotalesProbables, " + "caracteristicas, lactobacillus, bacteriasLacticas, coliformesTotales45, vibrio, shigella, vibrioCholerae " + "FROM vistambalimentos WHERE idmuestras IN (SELECT idmuestras FROM muestras WHERE idcliente = ? AND tipo = ?) " + "AND fechaAnalisis BETWEEN ? AND ?";

        List<String> columnasPresencia = Arrays.asList("escherichia", "escherichiah7", "escherichia157", "salmonella", "shigella");

        for (int i = 0; i < rowCont.length; i++) {
            rowCont[i] = 0;
        }

        CellStyle estiloPresencia = crearEstiloPresencia(workbook);

        try (Connection conexion = con.getConnection()) {
            PreparedStatement ps = conexion.prepareStatement(sql);
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
                        row.createCell(i - 1).setCellValue(ExcelUtils.formatearEntradaExcel(valor));
                    }

                    for (int i = 3; i <= columnCount; i++) {
                        String colName = rsmd.getColumnName(i);
                        String valor = rs.getString(i);
                        if (valor == null) continue;

                        int graphIdx = i - 3;
                        boolean esP_A = columnasPresencia.contains(colName.toLowerCase());

                        if (esP_A) {
                            if (valor.toLowerCase().contains("ausencia") || valor.toLowerCase().contains("presencia")) {
                                agregarCeldaPresencia(sheetHidden, rowCont, graphIdx, idmuestras, valor, estiloPresencia);
                            }
                        } else {
                            Double valorNum = ExcelUtils.extraerNumero(valor);
                            if (!Double.isNaN(valorNum)) {
                                agregarCeldaGrafico(sheetHidden, rowCont, graphIdx, idmuestras, valorNum);
                            }
                        }
                    }
                }
            } catch (SQLException e) {
                logger.severe("Error en MB Alimentos: " + e.getMessage());
            }
        } catch (SQLException e) {
            logger.severe("Error en MB Alimentos: " + e.getMessage());
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
            case MBAGUACOFES, MBAGUABIDON:
                return new String[]{"ID", "FECHA", "% Total Libre", "pH", "GERMENES AEROBIOS TOTALES", "COLIFORMES TOTALES", "COLIFORMES FECALES", "ESCHERICHIA COLI", "PSEUDOMONA AERUGINOSA", "SHIGELLA"};
            case FQALIMENTOS, FQAGUACOMPLETO, FQGENERICO:
                return new String[]{"ID", "FECHA", "Acidez", "Ácido cianúrico", "Ácido sórbico", "Actividad acuosa", "Alcalinidad", "Alcohol", "Aluminio", "Amonio", "Amoníaco", "Antimonio", "Aroma", "Arsénico", "Asbesto", "Aspecto", "Azúcares", "Azúcares deducidas de la lactosa", "Azúcares invertidos", "Azúcares reductores", "Bario", "Bicarbonatos", "Boro", "Bromuro", "Cadmio total", "Calcio", "Características Organolépticas", "Carbonatos", "Cenizas", "Cenizas insolubles en ácido cítrico", "Cenizas solubles en agua de las cenizas totales", "Cianuros", "Cloro activo", "Cloro residual o libre", "Cloro total", "Cloruros", "Cobalto", "Cobre", "Colesterol", "Color", "Colorantes artificiales", "Colorantes naturales", "Colorantes naturales y artificiales", "Conductividad", "Cromo Hexavalente", "DBO", "Detergentes", "DQO", "Dureza", "Edulcorantes", "Estaño", "Extracto primitivo", "Extracto seco", "Fenoles", "Fluoruros", "Flúor", "Fosfatos", "Fósforo total", "Gliadinas", "Gluten", "Grado de fermentación", "Grados Brix", "Grasa", "Grasas de cacao", "Grasas de leche", "Grasas y aceites", "Hidracina", "Hidrocarburos", "HIDROCARBUROS TOTALES DE PETROLEO (C6)", "HIDROCARBUROS TOTALES DE PETROLEO (C6-C35)", "HIDROCARBUROS TOTALES DE PETROLEO (C6-C8)", "HIDROCARBUROS TOTALES DE PETROLEO (C8-C10)", "HIDROCARBUROS TOTALES DE PETROLEO (C10-C12)", "HIDROCARBUROS TOTALES DE PETROLEO (C12-C16)", "HIDROCARBUROS TOTALES DE PETROLEO (C16-C21)", "HIDROCARBUROS TOTALES DE PETROLEO (C21-C35)", "Hierro", "Humedad", "Magnesio", "Manganeso", "Materia grasa", "Mercurio Total", "Molibdeno", "Nitratos", "Nitritos", "Nitrógeno Amoniacal", "Nitrógeno total", "Níquel", "Observación microscópica", "Olor", "Organoclorados", "Oxígeno disuelto", "Ozono", "Peróxido de hidrógeno", "pH", "Plata", "Plomo", "Porcentaje de cloruro de sodio", "Potasio", "Propionato de sodio", "Relación Peso/Humedad", "Residuo evaporación a 105ºC", "Residuo evaporación a 180ºC", "Residuo Seco", "Sabor", "Selenio", "Silicatos", "Sodio", "Sulfatos", "Sulfuros", "Sustancias extrañas", "Sustancias solubles en éter etílico", "Sólidos Disueltos totales", "Sólidos no grasos de cacao", "Sólidos no grasos de leche", "Sólidos sedimentables en 10 minutos", "Sólidos sedimentables en 2 horas", "Sólidos suspendidos totales", "Sólidos suspendidos volátiles", "Temperatura", "Turbidez", "Vanadio", "VIBRIO CHOLERAE", "Zinc"};
            case HISOPADOS:
                return new String[]{"ID", "FECHA", "GERMENES AEROBIOS TOTALES", "COLIFORMES TOTALES", "COLIFORMES FECALES", "ESCHERICHIA COLI", "STAPHILOCOCOS", "ENTEROBACTERIAS", "SALMONELLA", "LISTERIA", "MOHOS Y LEVADURAS", "VIBRIO"};
            case FQAGUA:
                return new String[]{"ID", "FECHA", "pH", "% CLORO TOTAL", "OLOR", "COLOR", "TURBIDEZ", "ALCALINIDAD", "DUREZA TOTAL", "CONDUCTIVIDAD", "SÓLIDOS DISUELTOS TOTALES", "HIERRO", "NITRATOS", "NITRITOS", "SULFATOS"};
            case MBCHOCOLATES:
                return new String[]{"ID", "FECHA", "GERMENES AEROBIOS TOTALES", "COLIFORMES TOTALES", "COLIFORMES FECALES", "ESCHERICHIA COLI", "STAPHILOCOCOS AUREUS COAGULASA (+)", "MOHOS Y LEVADURAS", "SALMONELLA sp"};
            case MBAGUABALNEARIOS:
                return new String[]{"ID", "FECHA", "COLIFORMES TOTALES", "ESCHERICHIA COLI", "SHIGELLA"};
            case HISOPADOALLIANCE, HISOPADOLIMITES:
                return new String[]{"ID", "FECHA", "GERMENES AEROBIOS TOTALES", "COLIFORMES TOTALES", "COLIFORMES FECALES", "ESCHERICHIA COLI", "STAPHILOCOCOS AUREUS COAGULASA (+)", "ENTEROBACTERIAS"};
            case MBAGUARECREACION:
                return new String[]{"ID", "FECHA", "GERMENES AEROBIOS TOTALES", "COLIFORMES TOTALES", "COLIFORMES FECALES", "ESCHERICHIA COLI", "PSEUDOMONA AERUGINOSA", "STAPHILOCOCOS AUREUS COAGULOSA (+)", "ENTEROCOCCUS", "SHIGELLA"};
            case BASEHELADA:
                return new String[]{"ID", "FECHA", "GERMENES AEROBIOS TOTALES", "COLIFORMES TOTALES", "COLIFORMES FECALES", "ESCHERICHIA COLI", "MOHOS Y LEVADURAS", "STAPHILOCOCOS AUREUS COAGULOSA (+)", "SALMONELLA sp"};
            case EFLUENTESINFILTRACION, EFLUENTESCLOACA:
                return new String[]{"ID", "Fecha", " pH", "CONDUCTIVIDAD", "DEMANDA QUÍMICA DE OXÍGENO (DQO)", "DEMANDA BIOQUÍMICA DE OXÍGENO (DBO)", "SÓLIDOS DISUELTOS SEDIMENTABLES EN 10’", "SÓLIDOS DISUELTOS SEDIMENTABLES EN 120’", "DETERGENTES", "GRASAS Y ACEITES", "FÒSFORO TOTAL", "NITRÓGENO TOTAL", "SUSTANCIAS SOLUBLES EN ETER ETILICO", "COLIFORMES TOTALES", "COLIFORMES FECALES", "ESCHERICHIA COLI", "HIDROCARBUROS TOTALES DE PETRÓLEO (IR)", "NITRATOS", "CLORO", "SULFUROS"};
        }

        return null;
    }

    private void consultarEfluentesTipoParaExcel(int idcliente, String tipo, java.sql.Date desdeSql, java.sql.Date hastaSql, Sheet sheet, Sheet sheetHidden, int[] rowNum, int[] rowNumGraph, int[] rowCont) {
        String sql = "SELECT * FROM vistaefluentes WHERE idmuestras in (select idmuestras from muestras where idcliente = ? and tipo = ?) and fechaMuestreo BETWEEN ? AND ?";
        Arrays.fill(rowCont, 0);
        try (Connection conexion = con.getConnection()) {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, idcliente);
            ps.setString(2, tipo);
            ps.setDate(3, desdeSql);
            ps.setDate(4, hastaSql);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Row row = sheet.createRow(rowNum[0]++);
                    String[] parametros = {"ph", "conductividad", "dqo", "dbo", "solidos10", "solidos120", "detergentes", "grasas", "fosforo", "nitrogeno", "sustancias", "coliformesTotales", "coliformesFecales", "escherichia", "hidrocarburos", "nitratos", "cloro", "sulfuros"};
                    String idmuestras = String.valueOf(rs.getInt("idmuestras"));
                    row.createCell(0).setCellValue(idmuestras);
                    row.createCell(1).setCellValue(ExcelUtils.formatearEntradaExcel(rs.getDate("fechaMuestreo").toString()));
                    for (int i = 0; i < parametros.length; i++) {
                        String nombreCol = parametros[i];
                        String valorRaw = rs.getString(nombreCol);

                        row.createCell(i + 2).setCellValue(ExcelUtils.formatearEntradaExcel(valorRaw));

                        if (i < rowCont.length) {
                            double valorNum = ExcelUtils.extraerNumero(valorRaw);

                            if (!Double.isNaN(valorNum) && valorNum >= 0) {
                                agregarCeldaGrafico(sheetHidden, rowCont, i, idmuestras, valorNum);
                            }
                        }
                    }
                }
            } catch (SQLException e) {
                logger.severe("Error en la consulta SQL: " + e.getMessage());
            }
        } catch (SQLException e) {
            logger.severe("Error en la consulta SQL: " + e.getMessage());
        }
    }

    private void consultarBaseHeladaParaExcel(int idcliente, String tipo, java.sql.Date desdeSql, java.sql.Date hastaSql, Sheet sheet, Sheet sheetHidden, int[] rowNum, int[] rowCont, Workbook workbook) {
        String sql = "SELECT idmuestras, fechaMuestreo, germenes, coliformesTotales, coliformesFecales, escherichia, " + "mohos, staphilococos, salmonella FROM vistambchocolates WHERE idmuestras in (select idmuestras from " + "muestras where idcliente = ? and tipo = ?) and fechaMuestreo BETWEEN ? AND ?";
        Arrays.fill(rowCont, 0);
        CellStyle estiloPresencia = crearEstiloPresencia(workbook);
        try (Connection conexion = con.getConnection()) {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, idcliente);
            ps.setString(2, tipo);
            ps.setDate(3, desdeSql);
            ps.setDate(4, hastaSql);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Row row = sheet.createRow(rowNum[0]++);
                    String idmuestras = String.valueOf(rs.getInt("idmuestras"));
                    row.createCell(0).setCellValue(idmuestras);
                    String fechaMuestreo = rs.getDate("fechaMuestreo").toString();
                    row.createCell(1).setCellValue(ExcelUtils.formatearEntradaExcel(fechaMuestreo));
                    String germenes = rs.getString("germenes");
                    row.createCell(2).setCellValue(ExcelUtils.formatearEntradaExcel(germenes));
                    String coliformesTotales = rs.getString("coliformesTotales");
                    row.createCell(3).setCellValue(ExcelUtils.formatearEntradaExcel(coliformesTotales));
                    String coliformesFecales = rs.getString("coliformesFecales");
                    row.createCell(4).setCellValue(ExcelUtils.formatearEntradaExcel(coliformesFecales));
                    String escherichia = rs.getString("escherichia");
                    row.createCell(5).setCellValue(ExcelUtils.formatearEntradaExcel(escherichia));
                    String mohos = rs.getString("mohos");
                    row.createCell(6).setCellValue(ExcelUtils.formatearEntradaExcel(mohos));
                    String staphilococos = rs.getString("staphilococos");
                    row.createCell(7).setCellValue(ExcelUtils.formatearEntradaExcel(staphilococos));
                    String salmonella = rs.getString("salmonella");
                    row.createCell(8).setCellValue(ExcelUtils.formatearEntradaExcel(salmonella));

                    double germenesGraph = ExcelUtils.extraerNumero(germenes);
                    if (!Double.isNaN(germenesGraph) && germenesGraph >= 0) {
                        agregarCeldaGrafico(sheetHidden, rowCont, 0, idmuestras, germenesGraph);
                    }
                    double coliformesTotalesGraph = ExcelUtils.extraerNumero(coliformesTotales);
                    if (!Double.isNaN(coliformesTotalesGraph) && coliformesTotalesGraph >= 0) {
                        agregarCeldaGrafico(sheetHidden, rowCont, 1, idmuestras, coliformesTotalesGraph);
                    }
                    double coliformesFecalesGraph = ExcelUtils.extraerNumero(coliformesFecales);
                    if (!Double.isNaN(coliformesFecalesGraph) && coliformesFecalesGraph >= 0) {
                        agregarCeldaGrafico(sheetHidden, rowCont, 2, idmuestras, coliformesFecalesGraph);
                    }
                    if (escherichia != null && (escherichia.toLowerCase().contains("ausencia") || escherichia.toLowerCase().contains("presencia"))) {
                        agregarCeldaPresencia(sheetHidden, rowCont, 3, idmuestras, escherichia, estiloPresencia);
                    }
                    double mohosGraph = ExcelUtils.extraerNumero(mohos);
                    if (!Double.isNaN(mohosGraph) && mohosGraph >= 0) {
                        agregarCeldaGrafico(sheetHidden, rowCont, 4, idmuestras, mohosGraph);
                    }
                    double staphilococosGraph = ExcelUtils.extraerNumero(staphilococos);
                    if (!Double.isNaN(staphilococosGraph) && staphilococosGraph >= 0) {
                        agregarCeldaGrafico(sheetHidden, rowCont, 5, idmuestras, staphilococosGraph);
                    }
                    if (salmonella != null && (salmonella.toLowerCase().contains("ausencia") || salmonella.toLowerCase().contains("presencia"))) {
                        agregarCeldaPresencia(sheetHidden, rowCont, 6, idmuestras, salmonella, estiloPresencia);
                    }
                }
            } catch (SQLException e) {
                logger.severe("Error en la consulta SQL, " + e);
            }
        } catch (SQLException e) {
            logger.severe("Error en la consulta SQL: " + e.getMessage());
        }
    }


    private Row obtenerOCrearFila(Sheet sheetHidden, int filaActual) {
        Row rowGraph = sheetHidden.getRow(filaActual);
        if (rowGraph == null) {
            rowGraph = sheetHidden.createRow(filaActual);
        }
        return rowGraph;
    }

    private void agregarCeldaGrafico(Sheet sheetHidden, int[] rowCont,
                                     int col, String idmuestras, double valor) {
        Row rowGraph = obtenerOCrearFila(sheetHidden, rowCont[col]);
        rowGraph.createCell(col * 2).setCellValue(ExcelUtils.procesarCeldaGrafico(idmuestras));
        rowGraph.createCell(col * 2 + 1).setCellValue(valor);
        rowCont[col]++;
    }

    private void agregarCeldaPresencia(Sheet sheetHidden, int[] rowCont,
                                       int col, String idmuestras,
                                       String valor, CellStyle estilo) {
        Row rowGraph = obtenerOCrearFila(sheetHidden, rowCont[col]);
        rowGraph.createCell(col * 2).setCellValue(ExcelUtils.procesarCeldaGrafico(idmuestras));
        Cell cellVal = rowGraph.createCell(col * 2 + 1);
        cellVal.setCellValue(Double.parseDouble(ExcelUtils.extraerAusenciaPresencia(valor)));
        cellVal.setCellStyle(estilo);
        rowCont[col]++;
    }

    private Double extraerValorLC(String valor) {
        java.util.regex.Matcher m = java.util.regex.Pattern.compile("=\\s*([0-9]+(?:[.,][0-9]+)?)").matcher(valor);
        if (m.find()) {
            return Double.parseDouble(m.group(1).replace(",", "."));
        }
        m = java.util.regex.Pattern.compile("lc\\D*([0-9]+(?:[.,][0-9]+)?)", java.util.regex.Pattern.CASE_INSENSITIVE).matcher(valor);
        if (m.find()) {
            return Double.parseDouble(m.group(1).replace(",", "."));
        }

        return ExcelUtils.extraerNumero(valor);
    }

    private CellStyle crearEstiloPresencia(Workbook workbook) {
        CellStyle estiloPresencia = workbook.createCellStyle();
        DataFormat format = workbook.createDataFormat();
        estiloPresencia.setDataFormat(format.getFormat("[=1]\"Presencia\";[=0]\"Ausencia\";\"N/A\""));
        return estiloPresencia;
    }

}
