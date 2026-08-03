package org.ignaciorodriguez.service;

import com.mysql.cj.util.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xddf.usermodel.chart.*;
import org.apache.poi.xssf.usermodel.*;
import org.ignaciorodriguez.modelo.Conexion;
import org.ignaciorodriguez.modelo.Tipo;
import org.ignaciorodriguez.repository.ClienteRepository;

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
import java.util.logging.Logger;

public class ExcelService {

    private final Logger logger = Logger.getLogger(ExcelService.class.getName());
    ArchivoService archivoService = new ArchivoService();
    Conexion con = new Conexion();
    ClienteRepository clienteRepository = new ClienteRepository(con);

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
                logger.severe("Error al crear excel, " + e);
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

//        ctChart.addNewShowDLblsOverMax().setVal(false);
        chart.plot(data);
    }

    private void consultarEfluentesParaExcel(int idcliente, String tipo, java.sql.Date desdeSql, java.sql.Date hastaSql, Sheet sheet, Sheet sheetHidden, int[] rowNum, int[] rowNumGraph, int[] rowCont) {
        String sql = "SELECT * FROM vistaefluentes WHERE idmuestras in (select idmuestras from muestras where idcliente = ? and tipo = ?) and fechaMuestreo BETWEEN ? AND ?";
        for (int i = 0; i < rowCont.length; i++) {
            rowCont[i] = 0;
        }
        try (Connection conexion = con.getConnection()) {
            // 1. Preparar la consulta
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, idcliente);
            ps.setString(2, tipo);
            ps.setDate(3, desdeSql);
            ps.setDate(4, hastaSql);
            ResultSet rs = ps.executeQuery();
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
            logger.severe("Error en la consulta SQL: " + e.getMessage());
        }
    }

    private void consultarMBAguaCofesParaExcel(int idcliente, String tipo, java.sql.Date desdeSql, java.sql.Date hastaSql, Sheet sheet, Sheet sheetHidden, int[] rowNum, int[] rowNumGraph, int[] rowCont, Workbook workbook) {
        String sql = "SELECT vistatabla_idmuestras, vistatabla_fechaMuestreo, vistatabla_porcentajeTotalCloro, " + "vistatabla_ph, germenes, coliformesTotales, coliformesFecales, escherichia, pseudomona, s" + "higella FROM vistambagua WHERE vistatabla_idmuestras in (select idmuestras from muestras where " + "idcliente = ? and tipo = ?) and vistatabla_fechaMuestreo BETWEEN ? AND ?";
        for (int i = 0; i < rowCont.length; i++) {
            rowCont[i] = 0;
        }
        CellStyle estiloPresencia = workbook.createCellStyle();
        DataFormat format = workbook.createDataFormat();
        estiloPresencia.setDataFormat(format.getFormat("[=1]\"Presencia\";[=0]\"Ausencia\";\"N/A\""));
        try (Connection conexion = con.getConnection()) {
            // 1. Preparar la consulta
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, idcliente);
            ps.setString(2, tipo);
            ps.setDate(3, desdeSql);
            ps.setDate(4, hastaSql);
            ResultSet rs = ps.executeQuery();
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
            logger.severe("Error en la consulta SQL: " + e.getMessage());
        }
    }

    private void consultarMBAguaRecreacionParaExcel(int idcliente, String tipo, java.sql.Date desdeSql, java.sql.Date hastaSql, Sheet sheet, Sheet sheetHidden, int[] rowNum, int[] rowNumGraph, int[] rowCont, Workbook workbook) {
        String sql = "SELECT vistatabla_idmuestras, vistatabla_fechaMuestreo, germenes, coliformesTotales, " + "coliformesFecales, escherichia, pseudomona, staphilococos, streptococos, shigella FROM vistambagua " + "WHERE vistatabla_idmuestras in (select idmuestras from muestras where idcliente = ? and tipo = ?) " + "and vistatabla_fechaMuestreo BETWEEN ? AND ?";
        for (int i = 0; i < rowCont.length; i++) {
            rowCont[i] = 0;
        }
        CellStyle estiloPresencia = workbook.createCellStyle();
        DataFormat format = workbook.createDataFormat();
        estiloPresencia.setDataFormat(format.getFormat("[=1]\"Presencia\";[=0]\"Ausencia\";\"N/A\""));
        try (Connection conexion = con.getConnection()) {
            // 1. Preparar la consulta
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, idcliente);
            ps.setString(2, tipo);
            ps.setDate(3, desdeSql);
            ps.setDate(4, hastaSql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Row row = sheet.createRow(rowNum[0]++);
                String idmuestras = String.valueOf(rs.getInt("vistatabla_idmuestras"));
                row.createCell(0).setCellValue(idmuestras);
                String fechaMuestreo = rs.getDate("vistatabla_fechaMuestreo").toString();
                row.createCell(1).setCellValue(formatearEntradaExcel(fechaMuestreo));
                String germenes = rs.getString("germenes");
                row.createCell(2).setCellValue(formatearEntradaExcel(germenes));
                String coliformesTotales = rs.getString("coliformesTotales");
                row.createCell(3).setCellValue(formatearEntradaExcel(coliformesTotales));
                String coliformesFecales = rs.getString("coliformesFecales");
                row.createCell(4).setCellValue(formatearEntradaExcel(coliformesFecales));
                String escherichia = rs.getString("escherichia");
                row.createCell(5).setCellValue(formatearEntradaExcel(escherichia));
                String pseudomona = rs.getString("pseudomona");
                row.createCell(6).setCellValue(formatearEntradaExcel(pseudomona));
                String staphilococos = rs.getString("staphilococos");
                row.createCell(7).setCellValue(formatearEntradaExcel(staphilococos));
                String streptococos = rs.getString("streptococos");
                row.createCell(8).setCellValue(formatearEntradaExcel(streptococos));
                String shigella = rs.getString("shigella");
                row.createCell(9).setCellValue(formatearEntradaExcel(shigella));

                Double germenesGraph = extraerNumero(germenes);
                if (germenesGraph != null && !Double.isNaN(germenesGraph) && germenesGraph >= 0) {
                    int filaActual = rowCont[0];
                    Row rowGraph = sheetHidden.getRow(filaActual);
                    if (rowGraph == null) {
                        rowGraph = sheetHidden.createRow(filaActual);
                    }
                    rowGraph.createCell(0 * 2).setCellValue(procesarCeldaGrafico(idmuestras));
                    rowGraph.createCell(0 * 2 + 1).setCellValue(germenesGraph);
                    rowCont[0]++;
                }
                Double coliformesTotalesGraph = extraerNumero(coliformesTotales);
                if (coliformesTotalesGraph != null && !Double.isNaN(coliformesTotalesGraph) && coliformesTotalesGraph >= 0) {
                    int filaActual = rowCont[1];
                    Row rowGraph = sheetHidden.getRow(filaActual);
                    if (rowGraph == null) {
                        rowGraph = sheetHidden.createRow(filaActual);
                    }
                    rowGraph.createCell(1 * 2).setCellValue(procesarCeldaGrafico(idmuestras));
                    rowGraph.createCell(1 * 2 + 1).setCellValue(coliformesTotalesGraph);
                    rowCont[1]++;
                }
                Double coliformesFecalesGraph = extraerNumero(coliformesFecales);
                if (coliformesFecalesGraph != null && !Double.isNaN(coliformesFecalesGraph) && coliformesFecalesGraph >= 0) {
                    int filaActual = rowCont[2];
                    Row rowGraph = sheetHidden.getRow(filaActual);
                    if (rowGraph == null) {
                        rowGraph = sheetHidden.createRow(filaActual);
                    }
                    rowGraph.createCell(2 * 2).setCellValue(procesarCeldaGrafico(idmuestras));
                    rowGraph.createCell(2 * 2 + 1).setCellValue(coliformesFecalesGraph);
                    rowCont[2]++;
                }
                if (escherichia.toLowerCase().contains("ausencia") || escherichia.toLowerCase().contains("presencia")) {
                    int filaActual = rowCont[3];
                    Row rowGraph = sheetHidden.getRow(filaActual);
                    if (rowGraph == null) {
                        rowGraph = sheetHidden.createRow(filaActual);
                    }
                    rowGraph.createCell(3 * 2).setCellValue(procesarCeldaGrafico(idmuestras));
                    rowGraph.createCell(3 * 2 + 1).setCellValue(extraerAusenciaPresencia(escherichia));
                    rowGraph.getCell(3 * 2 + 1).setCellStyle(estiloPresencia);
                    rowCont[3]++;
                }
                if (pseudomona.toLowerCase().contains("ausencia") || pseudomona.toLowerCase().contains("presencia")) {
                    int filaActual = rowCont[4];
                    Row rowGraph = sheetHidden.getRow(filaActual);
                    if (rowGraph == null) {
                        rowGraph = sheetHidden.createRow(filaActual);
                    }
                    rowGraph.createCell(4 * 2).setCellValue(procesarCeldaGrafico(idmuestras));
                    rowGraph.createCell(4 * 2 + 1).setCellValue(extraerAusenciaPresencia(pseudomona));
                    rowGraph.getCell(4 * 2 + 1).setCellStyle(estiloPresencia);
                    rowCont[4]++;
                }
                Double staphilococosGraph = extraerNumero(staphilococos);
                if (staphilococosGraph != null && !Double.isNaN(staphilococosGraph) && staphilococosGraph >= 0) {
                    int filaActual = rowCont[5];
                    Row rowGraph = sheetHidden.getRow(filaActual);
                    if (rowGraph == null) {
                        rowGraph = sheetHidden.createRow(filaActual);
                    }
                    rowGraph.createCell(5 * 2).setCellValue(procesarCeldaGrafico(idmuestras));
                    rowGraph.createCell(5 * 2 + 1).setCellValue(staphilococosGraph);
                    rowCont[5]++;
                }
                Double streptococosGraph = extraerNumero(streptococos);
                if (streptococosGraph != null && !Double.isNaN(streptococosGraph) && streptococosGraph >= 0) {
                    int filaActual = rowCont[6];
                    Row rowGraph = sheetHidden.getRow(filaActual);
                    if (rowGraph == null) {
                        rowGraph = sheetHidden.createRow(filaActual);
                    }
                    rowGraph.createCell(6 * 2).setCellValue(procesarCeldaGrafico(idmuestras));
                    rowGraph.createCell(6 * 2 + 1).setCellValue(streptococosGraph);
                    rowCont[6]++;
                }
                Double shigellaGraph = extraerNumero(shigella);
                if (shigellaGraph != null && !Double.isNaN(shigellaGraph) && shigellaGraph >= 0) {
                    int filaActual = rowCont[7];
                    Row rowGraph = sheetHidden.getRow(filaActual);
                    if (rowGraph == null) {
                        rowGraph = sheetHidden.createRow(filaActual);
                    }
                    rowGraph.createCell(7 * 2).setCellValue(procesarCeldaGrafico(idmuestras));
                    rowGraph.createCell(7 * 2 + 1).setCellValue(shigellaGraph);
                    rowCont[7]++;
                }
            }
        } catch (SQLException e) {
            logger.severe("Error en la consulta SQL: " + e.getMessage());
        }
    }

    private void consultarMBAguaCodigoParaExcel(int idcliente, String tipo, java.sql.Date desdeSql, java.sql.Date hastaSql, Sheet sheet, Sheet sheetHidden, int[] rowNum, int[] rowNumGraph, int[] rowCont, Workbook workbook) {
        String sql = "SELECT vistatabla_idmuestras, vistatabla_fechaMuestreo, vistatabla_porcentajeTotalCloro, " + "vistatabla_ph, germenes, coliformesTotales, coliformesFecales, escherichia, pseudomona, mohos, s" + "higella FROM vistambagua WHERE vistatabla_idmuestras in (select idmuestras from muestras where " + "idcliente = ? and tipo = ?) and vistatabla_fechaMuestreo BETWEEN ? AND ?";
        for (int i = 0; i < rowCont.length; i++) {
            rowCont[i] = 0;
        }
        CellStyle estiloPresencia = workbook.createCellStyle();
        DataFormat format = workbook.createDataFormat();
        estiloPresencia.setDataFormat(format.getFormat("[=1]\"Presencia\";[=0]\"Ausencia\";\"N/A\""));
        try (Connection conexion = con.getConnection()) {
            // 1. Preparar la consulta
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, idcliente);
            ps.setString(2, tipo);
            ps.setDate(3, desdeSql);
            ps.setDate(4, hastaSql);
            ResultSet rs = ps.executeQuery();
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
            logger.severe("Error en la consulta SQL: " + e.getMessage());
        }
    }

    private void consultarMBAguaBalnearioParaExcel(int idcliente, String tipo, java.sql.Date desdeSql, java.sql.Date hastaSql, Sheet sheet, Sheet sheetHidden, int[] rowNum, int[] rowNumGraph, int[] rowCont, Workbook workbook) {
        String sql = "SELECT vistatabla_idmuestras, vistatabla_fechaMuestreo, coliformesTotales, escherichia, " + "shigella FROM vistambagua WHERE vistatabla_idmuestras in (select idmuestras from muestras where " + "idcliente = ? and tipo = ?) and vistatabla_fechaMuestreo BETWEEN ? AND ?";
        for (int i = 0; i < rowCont.length; i++) {
            rowCont[i] = 0;
        }
        CellStyle estiloPresencia = workbook.createCellStyle();
        DataFormat format = workbook.createDataFormat();
        estiloPresencia.setDataFormat(format.getFormat("[=1]\"Presencia\";[=0]\"Ausencia\";\"N/A\""));
        try (Connection conexion = con.getConnection()) {
            // 1. Preparar la consulta
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, idcliente);
            ps.setString(2, tipo);
            ps.setDate(3, desdeSql);
            ps.setDate(4, hastaSql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Row row = sheet.createRow(rowNum[0]++);
                String idmuestras = String.valueOf(rs.getInt("vistatabla_idmuestras"));
                row.createCell(0).setCellValue(idmuestras);
                String fechaMuestreo = rs.getDate("vistatabla_fechaMuestreo").toString();
                row.createCell(1).setCellValue(formatearEntradaExcel(fechaMuestreo));
                String coliformesTotales = rs.getString("coliformesTotales");
                row.createCell(2).setCellValue(formatearEntradaExcel(coliformesTotales));
                String escherichia = rs.getString("escherichia");
                row.createCell(3).setCellValue(formatearEntradaExcel(escherichia));
                String shigella = rs.getString("shigella");
                row.createCell(4).setCellValue(formatearEntradaExcel(shigella));

                Double coliformesTotalesGraph = extraerNumero(coliformesTotales);
                if (coliformesTotalesGraph != null && !Double.isNaN(coliformesTotalesGraph) && coliformesTotalesGraph >= 0) {
                    int filaActual = rowCont[0];
                    Row rowGraph = sheetHidden.getRow(filaActual);
                    if (rowGraph == null) {
                        rowGraph = sheetHidden.createRow(filaActual);
                    }
                    rowGraph.createCell(0 * 2).setCellValue(procesarCeldaGrafico(idmuestras));
                    rowGraph.createCell(0 * 2 + 1).setCellValue(coliformesTotalesGraph);
                    rowCont[0]++;
                }
                if (escherichia.toLowerCase().contains("ausencia") || escherichia.toLowerCase().contains("presencia")) {
                    int filaActual = rowCont[1];
                    Row rowGraph = sheetHidden.getRow(filaActual);
                    if (rowGraph == null) {
                        rowGraph = sheetHidden.createRow(filaActual);
                    }
                    rowGraph.createCell(1 * 2).setCellValue(procesarCeldaGrafico(idmuestras));
                    rowGraph.createCell(1 * 2 + 1).setCellValue(extraerAusenciaPresencia(escherichia));
                    rowGraph.getCell(1 * 2 + 1).setCellStyle(estiloPresencia);
                    rowCont[1]++;
                }
                Double shigellaGraph = extraerNumero(shigella);
                if (shigellaGraph != null && !Double.isNaN(shigellaGraph) && shigellaGraph >= 0) {
                    int filaActual = rowCont[2];
                    Row rowGraph = sheetHidden.getRow(filaActual);
                    if (rowGraph == null) {
                        rowGraph = sheetHidden.createRow(filaActual);
                    }
                    rowGraph.createCell(2 * 2).setCellValue(procesarCeldaGrafico(idmuestras));
                    rowGraph.createCell(2 * 2 + 1).setCellValue(shigellaGraph);
                    rowCont[2]++;
                }
            }
        } catch (SQLException e) {
            logger.severe("Error en la consulta SQL: " + e.getMessage());
        }
    }

    private void consultarHisopadosParaExcel(int idcliente, String tipo, java.sql.Date desdeSql, java.sql.Date hastaSql, Sheet sheet, Sheet sheetHidden, int[] rowNum, int[] rowCont, Workbook workbook) {
        String sql = "SELECT vistatabla_idmuestras, vistatabla_fechaMuestreo, germenes, coliformesTotales, " + "coliformesFecales, escherichia, staphilococos, enterobacterias, salmonella, listeria, mohos, " + "vibrio FROM vistahisopado WHERE vistatabla_idmuestras in (select idmuestras from muestras where " + "idcliente = ? and tipo = ?) and vistatabla_fechaMuestreo BETWEEN ? AND ?";
        for (int i = 0; i < rowCont.length; i++) {
            rowCont[i] = 0;
        }
        CellStyle estiloPresencia = workbook.createCellStyle();
        DataFormat format = workbook.createDataFormat();
        estiloPresencia.setDataFormat(format.getFormat("[=1]\"Presencia\";[=0]\"Ausencia\";\"N/A\""));
        try (Connection conexion = con.getConnection()) {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, idcliente);
            ps.setString(2, tipo);
            ps.setDate(3, desdeSql);
            ps.setDate(4, hastaSql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Row row = sheet.createRow(rowNum[0]++);
                String idmuestras = String.valueOf(rs.getInt("vistatabla_idmuestras"));
                System.out.println("idmuestras = " + idmuestras);
                row.createCell(0).setCellValue(idmuestras);
                String fechaMuestreo = rs.getDate("vistatabla_fechaMuestreo").toString();
                row.createCell(1).setCellValue(formatearEntradaExcel(fechaMuestreo));
                String germenes = rs.getString("germenes");
                row.createCell(2).setCellValue(formatearEntradaExcel(germenes));
                String coliformesTotales = rs.getString("coliformesTotales");
                row.createCell(3).setCellValue(formatearEntradaExcel(coliformesTotales));
                String coliformesFecales = rs.getString("coliformesFecales");
                row.createCell(4).setCellValue(formatearEntradaExcel(coliformesFecales));
                String escherichia = rs.getString("escherichia");
                row.createCell(5).setCellValue(formatearEntradaExcel(escherichia));
                String staphilococos = rs.getString("staphilococos");
                row.createCell(6).setCellValue(formatearEntradaExcel(staphilococos));
                String enterobacterias = rs.getString("enterobacterias");
                row.createCell(7).setCellValue(formatearEntradaExcel(enterobacterias));
                String salmonella = rs.getString("salmonella");
                row.createCell(8).setCellValue(formatearEntradaExcel(salmonella));
                String listeria = rs.getString("listeria");
                row.createCell(9).setCellValue(formatearEntradaExcel(listeria));
                String mohos = rs.getString("mohos");
                row.createCell(10).setCellValue(formatearEntradaExcel(mohos));
                String vibrio = rs.getString("vibrio");
                row.createCell(11).setCellValue(formatearEntradaExcel(vibrio));

                Double germenesGraph = extraerNumero(germenes);
                if (germenesGraph != null && !Double.isNaN(germenesGraph) && germenesGraph >= 0) {
                    int filaActual = rowCont[0];
                    Row rowGraph = sheetHidden.getRow(filaActual);
                    if (rowGraph == null) {
                        rowGraph = sheetHidden.createRow(filaActual);
                    }
                    rowGraph.createCell(0 * 2).setCellValue(procesarCeldaGrafico(idmuestras));
                    rowGraph.createCell(0 * 2 + 1).setCellValue(germenesGraph);
                    rowCont[0]++;
                }
                Double coliformesTotalesGraph = extraerNumero(coliformesTotales);
                if (coliformesTotalesGraph != null && !Double.isNaN(coliformesTotalesGraph) && coliformesTotalesGraph >= 0) {
                    int filaActual = rowCont[1];
                    Row rowGraph = sheetHidden.getRow(filaActual);
                    if (rowGraph == null) {
                        rowGraph = sheetHidden.createRow(filaActual);
                    }
                    rowGraph.createCell(1 * 2).setCellValue(procesarCeldaGrafico(idmuestras));
                    rowGraph.createCell(1 * 2 + 1).setCellValue(coliformesTotalesGraph);
                    rowCont[1]++;
                }
                if (coliformesFecales.toLowerCase().contains("ausencia") || coliformesFecales.toLowerCase().contains("presencia")) {
                    int filaActual = rowCont[2];
                    Row rowGraph = sheetHidden.getRow(filaActual);
                    if (rowGraph == null) {
                        rowGraph = sheetHidden.createRow(filaActual);
                    }
                    rowGraph.createCell(2 * 2).setCellValue(procesarCeldaGrafico(idmuestras));
                    rowGraph.createCell(2 * 2 + 1).setCellValue(extraerAusenciaPresencia(coliformesFecales));
                    rowGraph.getCell(2 * 2 + 1).setCellStyle(estiloPresencia);
                    rowCont[2]++;
                }
                if (escherichia.toLowerCase().contains("ausencia") || escherichia.toLowerCase().contains("presencia")) {
                    int filaActual = rowCont[3];
                    Row rowGraph = sheetHidden.getRow(filaActual);
                    if (rowGraph == null) {
                        rowGraph = sheetHidden.createRow(filaActual);
                    }
                    rowGraph.createCell(3 * 2).setCellValue(procesarCeldaGrafico(idmuestras));
                    rowGraph.createCell(3 * 2 + 1).setCellValue(extraerAusenciaPresencia(escherichia));
                    rowGraph.getCell(3 * 2 + 1).setCellStyle(estiloPresencia);
                    rowCont[3]++;
                }
                Double staphilococosGraph = extraerNumero(staphilococos);
                if (staphilococosGraph != null && !Double.isNaN(staphilococosGraph) && staphilococosGraph >= 0) {
                    int filaActual = rowCont[4];
                    Row rowGraph = sheetHidden.getRow(filaActual);
                    if (rowGraph == null) {
                        rowGraph = sheetHidden.createRow(filaActual);
                    }
                    rowGraph.createCell(4 * 2).setCellValue(procesarCeldaGrafico(idmuestras));
                    rowGraph.createCell(4 * 2 + 1).setCellValue(staphilococosGraph);
                    rowCont[4]++;
                }
                Double enterobacteriasGraph = extraerNumero(enterobacterias);
                if (enterobacteriasGraph != null && !Double.isNaN(enterobacteriasGraph) && enterobacteriasGraph >= 0) {
                    int filaActual = rowCont[5];
                    Row rowGraph = sheetHidden.getRow(filaActual);
                    if (rowGraph == null) {
                        rowGraph = sheetHidden.createRow(filaActual);
                    }
                    rowGraph.createCell(5 * 2).setCellValue(procesarCeldaGrafico(idmuestras));
                    rowGraph.createCell(5 * 2 + 1).setCellValue(enterobacteriasGraph);
                    rowCont[5]++;
                }
                Double salmonellaGraph = extraerNumero(salmonella);
                if (salmonellaGraph != null && !Double.isNaN(salmonellaGraph) && salmonellaGraph >= 0) {
                    int filaActual = rowCont[6];
                    Row rowGraph = sheetHidden.getRow(filaActual);
                    if (rowGraph == null) {
                        rowGraph = sheetHidden.createRow(filaActual);
                    }
                    rowGraph.createCell(6 * 2).setCellValue(procesarCeldaGrafico(idmuestras));
                    rowGraph.createCell(6 * 2 + 1).setCellValue(salmonellaGraph);
                    rowCont[6]++;
                }
                if (listeria.toLowerCase().contains("ausencia") || listeria.toLowerCase().contains("presencia")) {
                    int filaActual = rowCont[7];
                    Row rowGraph = sheetHidden.getRow(filaActual);
                    if (rowGraph == null) {
                        rowGraph = sheetHidden.createRow(filaActual);
                    }
                    rowGraph.createCell(7 * 2).setCellValue(procesarCeldaGrafico(idmuestras));
                    rowGraph.createCell(7 * 2 + 1).setCellValue(extraerAusenciaPresencia(listeria));
                    rowGraph.getCell(7 * 2 + 1).setCellStyle(estiloPresencia);
                    rowCont[7]++;
                } else {
                    Double listeriaGraph = extraerNumero(listeria);
                    if (listeriaGraph != null && !Double.isNaN(listeriaGraph) && listeriaGraph >= 0) {
                        int filaActual = rowCont[7];
                        Row rowGraph = sheetHidden.getRow(filaActual);
                        if (rowGraph == null) {
                            rowGraph = sheetHidden.createRow(filaActual);
                        }
                        rowGraph.createCell(7 * 2).setCellValue(procesarCeldaGrafico(idmuestras));
                        rowGraph.createCell(7 * 2 + 1).setCellValue(listeriaGraph);
                        rowCont[7]++;
                    }
                }
                Double mohosGraph = extraerNumero(mohos);
                if (mohosGraph != null && !Double.isNaN(mohosGraph) && mohosGraph >= 0) {
                    int filaActual = rowCont[8];
                    Row rowGraph = sheetHidden.getRow(filaActual);
                    if (rowGraph == null) {
                        rowGraph = sheetHidden.createRow(filaActual);
                    }
                    rowGraph.createCell(8 * 2).setCellValue(procesarCeldaGrafico(idmuestras));
                    rowGraph.createCell(8 * 2 + 1).setCellValue(mohosGraph);
                    rowCont[8]++;
                }
                Double vibrioGraph = extraerNumero(vibrio);
                if (vibrioGraph != null && !Double.isNaN(vibrioGraph) && vibrioGraph >= 0) {
                    int filaActual = rowCont[9];
                    Row rowGraph = sheetHidden.getRow(filaActual);
                    if (rowGraph == null) {
                        rowGraph = sheetHidden.createRow(filaActual);
                    }
                    rowGraph.createCell(9 * 2).setCellValue(procesarCeldaGrafico(idmuestras));
                    rowGraph.createCell(9 * 2 + 1).setCellValue(vibrioGraph);
                    rowCont[9]++;
                }
            }
        } catch (SQLException e) {
            logger.severe("Error en la consulta SQL: " + e.getMessage());
        }
    }

    private void consultarHisopadosLimitesParaExcel(int idcliente, String tipo, java.sql.Date desdeSql, java.sql.Date hastaSql, Sheet sheet, Sheet sheetHidden, int[] rowNum, int[] rowCont, Workbook workbook) {
        String sql = "SELECT vistatabla_idmuestras, vistatabla_fechaMuestreo, germenes, coliformesTotales, " + "coliformesFecales, escherichia, staphilococos, enterobacterias FROM vistahisopado WHERE " + "vistatabla_idmuestras in (select idmuestras from muestras where idcliente = ? and tipo = ?) " + "and vistatabla_fechaMuestreo BETWEEN ? AND ?";
        for (int i = 0; i < rowCont.length; i++) {
            rowCont[i] = 0;
        }
        CellStyle estiloPresencia = workbook.createCellStyle();
        DataFormat format = workbook.createDataFormat();
        estiloPresencia.setDataFormat(format.getFormat("[=1]\"Presencia\";[=0]\"Ausencia\";\"N/A\""));
        try (Connection conexion = con.getConnection()) {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, idcliente);
            ps.setString(2, tipo);
            ps.setDate(3, desdeSql);
            ps.setDate(4, hastaSql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Row row = sheet.createRow(rowNum[0]++);
                String idmuestras = String.valueOf(rs.getInt("vistatabla_idmuestras"));
                row.createCell(0).setCellValue(idmuestras);
                String fechaMuestreo = rs.getDate("vistatabla_fechaMuestreo").toString();
                row.createCell(1).setCellValue(formatearEntradaExcel(fechaMuestreo));
                String germenes = rs.getString("germenes");
                row.createCell(2).setCellValue(formatearEntradaExcel(germenes));
                String coliformesTotales = rs.getString("coliformesTotales");
                row.createCell(3).setCellValue(formatearEntradaExcel(coliformesTotales));
                String coliformesFecales = rs.getString("coliformesFecales");
                row.createCell(4).setCellValue(formatearEntradaExcel(coliformesFecales));
                String escherichia = rs.getString("escherichia");
                row.createCell(5).setCellValue(formatearEntradaExcel(escherichia));
                String staphilococos = rs.getString("staphilococos");
                row.createCell(6).setCellValue(formatearEntradaExcel(staphilococos));
                String enterobacterias = rs.getString("enterobacterias");
                row.createCell(7).setCellValue(formatearEntradaExcel(enterobacterias));

                Double germenesGraph = extraerNumero(germenes);
                if (germenesGraph != null && !Double.isNaN(germenesGraph) && germenesGraph >= 0) {
                    int filaActual = rowCont[0];
                    Row rowGraph = sheetHidden.getRow(filaActual);
                    if (rowGraph == null) {
                        rowGraph = sheetHidden.createRow(filaActual);
                    }
                    rowGraph.createCell(0 * 2).setCellValue(procesarCeldaGrafico(idmuestras));
                    rowGraph.createCell(0 * 2 + 1).setCellValue(germenesGraph);
                    rowCont[0]++;
                }
                Double coliformesTotalesGraph = extraerNumero(coliformesTotales);
                if (coliformesTotalesGraph != null && !Double.isNaN(coliformesTotalesGraph) && coliformesTotalesGraph >= 0) {
                    int filaActual = rowCont[1];
                    Row rowGraph = sheetHidden.getRow(filaActual);
                    if (rowGraph == null) {
                        rowGraph = sheetHidden.createRow(filaActual);
                    }
                    rowGraph.createCell(1 * 2).setCellValue(procesarCeldaGrafico(idmuestras));
                    rowGraph.createCell(1 * 2 + 1).setCellValue(coliformesTotalesGraph);
                    rowCont[1]++;
                }
                if (coliformesFecales.toLowerCase().contains("ausencia") || coliformesFecales.toLowerCase().contains("presencia")) {
                    int filaActual = rowCont[2];
                    Row rowGraph = sheetHidden.getRow(filaActual);
                    if (rowGraph == null) {
                        rowGraph = sheetHidden.createRow(filaActual);
                    }
                    rowGraph.createCell(2 * 2).setCellValue(procesarCeldaGrafico(idmuestras));
                    rowGraph.createCell(2 * 2 + 1).setCellValue(extraerAusenciaPresencia(coliformesFecales));
                    rowGraph.getCell(2 * 2 + 1).setCellStyle(estiloPresencia);
                    rowCont[2]++;
                }
                if (escherichia.toLowerCase().contains("ausencia") || escherichia.toLowerCase().contains("presencia")) {
                    int filaActual = rowCont[3];
                    Row rowGraph = sheetHidden.getRow(filaActual);
                    if (rowGraph == null) {
                        rowGraph = sheetHidden.createRow(filaActual);
                    }
                    rowGraph.createCell(3 * 2).setCellValue(procesarCeldaGrafico(idmuestras));
                    rowGraph.createCell(3 * 2 + 1).setCellValue(extraerAusenciaPresencia(escherichia));
                    rowGraph.getCell(3 * 2 + 1).setCellStyle(estiloPresencia);
                    rowCont[3]++;
                }
                Double staphilococosGraph = extraerNumero(staphilococos);
                if (staphilococosGraph != null && !Double.isNaN(staphilococosGraph) && staphilococosGraph >= 0) {
                    int filaActual = rowCont[4];
                    Row rowGraph = sheetHidden.getRow(filaActual);
                    if (rowGraph == null) {
                        rowGraph = sheetHidden.createRow(filaActual);
                    }
                    rowGraph.createCell(4 * 2).setCellValue(procesarCeldaGrafico(idmuestras));
                    rowGraph.createCell(4 * 2 + 1).setCellValue(staphilococosGraph);
                    rowCont[4]++;
                }
                Double enterobacteriasGraph = extraerNumero(enterobacterias);
                if (enterobacteriasGraph != null && !Double.isNaN(enterobacteriasGraph) && enterobacteriasGraph >= 0) {
                    int filaActual = rowCont[5];
                    Row rowGraph = sheetHidden.getRow(filaActual);
                    if (rowGraph == null) {
                        rowGraph = sheetHidden.createRow(filaActual);
                    }
                    rowGraph.createCell(5 * 2).setCellValue(procesarCeldaGrafico(idmuestras));
                    rowGraph.createCell(5 * 2 + 1).setCellValue(enterobacteriasGraph);
                    rowCont[5]++;
                }
            }
        } catch (SQLException e) {
            logger.severe("Error en la consulta SQL: " + e.getMessage());
        }
    }

    private void consultarDeterminacionesParaExcel(int idcliente, String tipo, java.sql.Date desdeSql, java.sql.Date hastaSql, Sheet sheet, Sheet sheetHidden, int[] rowNum, int[] rowCont, Workbook workbook) {
        String sql = "SELECT d.idmuestras, vd.fechaAnalisis, d.acidez, d.acidoCianurico, d.acidoSorbico, d.actividadAcuosa, d.alcalinidad, d.alcohol, " + "d.aluminio, d.amonio, d.amoniacos, d.antimonio, d.aroma, d.arsenico, d.asbesto, d.aspecto, d.azucares, d.azucaresDeducidas, " + "d.azucaresInvertidos, d.azucaresReductores, d.bario, d.bicarbonatos, d.boro, d.bromuro, d.cadmioTotal, d.calcio, d.caracteristicas, " + "d.carbonatos, d.cenizas, d.cenizasInsolublesAcido, d.cenizasInsolublesAgua, d.cianuros, d.cloroActivo, d.cloroResidual, d.cloroTotal, " + "d.cloruros, d.cobalto, d.cobre, d.colesterol, d.color, d.colorantesartificiales, d.colorantesnaturales, d.colorantes, d.conductividad, " + "d.cromoHexavalente, d.dbo, d.detergentes, d.dqo, d.dureza, d.edulcorantes, d.estano, d.extracto, d.extractoseco, d.fenoles, d.fluoruros, " + "d.fluor, d.fosfatos, d.fosforoTotal, d.gliadinas, d.gluten, d.gradoFermentacion, d.gradosBrix, d.grasa, d.grasasCacao, d.grasasLeche, " + "d.grasasyAceites, d.hidracina, d.hidrocarburos, d.hidrocarburosc6, d.hidrocarburosc6_c35, d.hidrocarburosc6_c8, d.hidrocarburosc8_c10, " + "d.hidrocarburosc10_c12, d.hidrocarburosc12_c16, d.hidrocarburosc16_c21, d.hidrocarburosc21_c35, d.hierro, d.humedad, d.magnesio, " + "d.manganeso, d.materiagrasa, d.mercurioTotal, d.molibdeno, d.nitratos, d.nitritos, d.nitrogenoAmoniacal, d.nitrogenoTotal, d.niquel, " + "d.observacionMicroscopica, d.olor, d.organoclorados, d.oxigenoDisuelto, d.ozono, d.peroxidoHidrogeno, d.ph, d.plata, d.plomo, " + "d.porcentajeCloruro, d.potasio, d.propionato, d.relacion, d.residuo105, d.residuo180, d.residuoSeco, d.sabor, d.selenio, d.silicatos, " + "d.sodio, d.sulfatos, d.sulfuros, d.sustancias, d.sustanciasEterEtilico, d.solidosTotales, d.solidosNoGrasosCacao, d.solidosNoGrasos, " + "d.solidos10Minutos, d.solidos2Horas, d.solidosSuspendidosTotales, d.solidosSuspendidosVolatiles, d.temperatura, d.turbidez, d.vanadio, " + "d.vibrio, d.zinc FROM determinaciones d join vistafqcompleto vd on d.idmuestras = vd.idmuestras WHERE d.idmuestras IN " + "(SELECT idmuestras FROM muestras WHERE idcliente = ? AND tipo = ? AND fechaAnalisis BETWEEN ? AND ?)";

        // Reiniciar contadores
        for (int i = 0; i < rowCont.length; i++) {
            rowCont[i] = 0;
        }

        try (Connection conexion = con.getConnection()) {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, idcliente);
            ps.setString(2, tipo);
            ps.setDate(3, desdeSql);
            ps.setDate(4, hastaSql);

            ResultSet rs = ps.executeQuery();
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
        } catch (SQLException e) {
            logger.severe("Error en Determinaciones: " + e.getMessage());
        }
    }

    private void consultarFQAguaParaExcel(int idcliente, String tipo, java.sql.Date desdeSql, java.sql.Date hastaSql, Sheet sheet, Sheet sheetHidden, int[] rowNum, int[] rowCont, Workbook workbook) {
        String sql = "SELECT vistatabla_idmuestras, vistatabla_fechaMuestreo, ph, porcentajeTotalCloro, olor, color, " + "turbidez, alcalinidad, durezatotal, conductividad, solidosDisueltos, hierro, nitrato, nitritos, " + "sulfatos FROM vistafqagua WHERE vistatabla_idmuestras in (select idmuestras from muestras where " + "idcliente = ? and tipo = ?) and vistatabla_fechaMuestreo BETWEEN ? AND ?";
        for (int i = 0; i < rowCont.length; i++) {
            rowCont[i] = 0;
        }
        try (Connection conexion = con.getConnection()) {
            // 1. Preparar la consulta
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, idcliente);
            ps.setString(2, tipo);
            ps.setDate(3, desdeSql);
            ps.setDate(4, hastaSql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Row row = sheet.createRow(rowNum[0]++);
                String idmuestras = String.valueOf(rs.getInt("vistatabla_idmuestras"));
                row.createCell(0).setCellValue(idmuestras);
                String fechaMuestreo = rs.getDate("vistatabla_fechaMuestreo").toString();
                row.createCell(1).setCellValue(formatearEntradaExcel(fechaMuestreo));
                String ph = rs.getString("ph");
                row.createCell(2).setCellValue(formatearEntradaExcel(ph));
                String cloroTotal = rs.getString("porcentajeTotalCloro");
                row.createCell(3).setCellValue(formatearEntradaExcel(cloroTotal));
                String olor = rs.getString("olor");
                row.createCell(4).setCellValue(formatearEntradaExcel(olor));
                String color = rs.getString("color");
                row.createCell(5).setCellValue(formatearEntradaExcel(color));
                String turbidez = rs.getString("turbidez");
                row.createCell(6).setCellValue(formatearEntradaExcel(turbidez));
                String alcalinidad = rs.getString("alcalinidad");
                row.createCell(7).setCellValue(formatearEntradaExcel(alcalinidad));
                String durezatotal = rs.getString("durezatotal");
                row.createCell(8).setCellValue(formatearEntradaExcel(durezatotal));
                String conductividad = rs.getString("conductividad");
                row.createCell(9).setCellValue(formatearEntradaExcel(conductividad));
                String solidosDisueltos = rs.getString("solidosDisueltos");
                row.createCell(10).setCellValue(formatearEntradaExcel(solidosDisueltos));
                String hierro = rs.getString("hierro");
                row.createCell(11).setCellValue(formatearEntradaExcel(hierro));
                String nitrato = rs.getString("nitrato");
                row.createCell(12).setCellValue(formatearEntradaExcel(nitrato));
                String nitritos = rs.getString("nitritos");
                row.createCell(13).setCellValue(formatearEntradaExcel(nitritos));
                String sulfatos = rs.getString("sulfatos");
                row.createCell(14).setCellValue(formatearEntradaExcel(sulfatos));

                // olor (índice 2) queda sin graficar: es texto descriptivo, no numérico
                Double phGraph = extraerNumero(ph);
                if (phGraph != null && !Double.isNaN(phGraph) && phGraph >= 0) {
                    agregarCeldaGrafico(sheetHidden, rowCont, 0, idmuestras, phGraph);
                }
                Double cloroGraph = extraerNumero(cloroTotal);
                if (cloroGraph != null && !Double.isNaN(cloroGraph) && cloroGraph >= 0) {
                    agregarCeldaGrafico(sheetHidden, rowCont, 1, idmuestras, cloroGraph);
                }
                Double colorGraph = extraerNumero(color);
                if (colorGraph != null && !Double.isNaN(colorGraph) && colorGraph >= 0) {
                    agregarCeldaGrafico(sheetHidden, rowCont, 3, idmuestras, colorGraph);
                }
                Double turbidezGraph = extraerNumero(turbidez);
                if (turbidezGraph != null && !Double.isNaN(turbidezGraph) && turbidezGraph >= 0) {
                    agregarCeldaGrafico(sheetHidden, rowCont, 4, idmuestras, turbidezGraph);
                }
                Double alcalinidadGraph = extraerNumero(alcalinidad);
                if (alcalinidadGraph != null && !Double.isNaN(alcalinidadGraph) && alcalinidadGraph >= 0) {
                    agregarCeldaGrafico(sheetHidden, rowCont, 5, idmuestras, alcalinidadGraph);
                }
                Double durezaGraph = extraerNumero(durezatotal);
                if (durezaGraph != null && !Double.isNaN(durezaGraph) && durezaGraph >= 0) {
                    agregarCeldaGrafico(sheetHidden, rowCont, 6, idmuestras, durezaGraph);
                }
                Double conductividadGraph = extraerNumero(conductividad);
                if (conductividadGraph != null && !Double.isNaN(conductividadGraph) && conductividadGraph >= 0) {
                    agregarCeldaGrafico(sheetHidden, rowCont, 7, idmuestras, conductividadGraph);
                }
                Double solidosGraph = extraerNumero(solidosDisueltos);
                if (solidosGraph != null && !Double.isNaN(solidosGraph) && solidosGraph >= 0) {
                    agregarCeldaGrafico(sheetHidden, rowCont, 8, idmuestras, solidosGraph);
                }
                Double hierroGraph = extraerNumero(hierro);
                if (hierroGraph != null && !Double.isNaN(hierroGraph) && hierroGraph >= 0) {
                    agregarCeldaGrafico(sheetHidden, rowCont, 9, idmuestras, hierroGraph);
                }
                Double nitratoGraph = extraerNumero(nitrato);
                if (nitratoGraph != null && !Double.isNaN(nitratoGraph) && nitratoGraph >= 0) {
                    agregarCeldaGrafico(sheetHidden, rowCont, 10, idmuestras, nitratoGraph);
                }
                Double nitritosGraph = extraerNumero(nitritos);
                if (nitritosGraph != null && !Double.isNaN(nitritosGraph) && nitritosGraph >= 0) {
                    agregarCeldaGrafico(sheetHidden, rowCont, 11, idmuestras, nitritosGraph);
                }
                Double sulfatosGraph = extraerNumero(sulfatos);
                if (sulfatosGraph != null && !Double.isNaN(sulfatosGraph) && sulfatosGraph >= 0) {
                    agregarCeldaGrafico(sheetHidden, rowCont, 12, idmuestras, sulfatosGraph);
                }
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
        CellStyle estiloPresencia = workbook.createCellStyle();
        DataFormat format = workbook.createDataFormat();
        estiloPresencia.setDataFormat(format.getFormat("[=1]\"Presencia\";[=0]\"Ausencia\";\"N/A\""));
        try (Connection conexion = con.getConnection()) {
            // 1. Preparar la consulta
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, idcliente);
            ps.setString(2, tipo);
            ps.setDate(3, desdeSql);
            ps.setDate(4, hastaSql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Row row = sheet.createRow(rowNum[0]++);
                String idmuestras = String.valueOf(rs.getInt("idmuestras"));
                row.createCell(0).setCellValue(idmuestras);
                String fechaMuestreo = rs.getDate("fechaMuestreo").toString();
                row.createCell(1).setCellValue(formatearEntradaExcel(fechaMuestreo));
                String germenes = rs.getString("germenes");
                row.createCell(2).setCellValue(formatearEntradaExcel(germenes));
                String coliformesTotales = rs.getString("coliformesTotales");
                row.createCell(3).setCellValue(formatearEntradaExcel(coliformesTotales));
                String coliformesFecales = rs.getString("coliformesFecales");
                row.createCell(4).setCellValue(formatearEntradaExcel(coliformesFecales));
                String escherichia = rs.getString("escherichia");
                row.createCell(5).setCellValue(formatearEntradaExcel(escherichia));
                String staphilococos = rs.getString("staphilococos");
                row.createCell(6).setCellValue(formatearEntradaExcel(staphilococos));
                String mohos = rs.getString("mohos");
                row.createCell(7).setCellValue(formatearEntradaExcel(mohos));
                String salmonella = rs.getString("salmonella");
                row.createCell(8).setCellValue(formatearEntradaExcel(salmonella));

                Double germenesGraph = extraerNumero(germenes);
                if (germenesGraph != null && !Double.isNaN(germenesGraph) && germenesGraph >= 0) {
                    agregarCeldaGrafico(sheetHidden, rowCont, 0, idmuestras, germenesGraph);
                }
                Double coliformesTotalesGraph = extraerNumero(coliformesTotales);
                if (coliformesTotalesGraph != null && !Double.isNaN(coliformesTotalesGraph) && coliformesTotalesGraph >= 0) {
                    agregarCeldaGrafico(sheetHidden, rowCont, 1, idmuestras, coliformesTotalesGraph);
                }
                Double coliformesFecalesGraph = extraerNumero(coliformesFecales);
                if (coliformesFecalesGraph != null && !Double.isNaN(coliformesFecalesGraph) && coliformesFecalesGraph >= 0) {
                    agregarCeldaGrafico(sheetHidden, rowCont, 2, idmuestras, coliformesFecalesGraph);
                }
                if (escherichia != null && (escherichia.toLowerCase().contains("ausencia") || escherichia.toLowerCase().contains("presencia"))) {
                    Row rowGraph = obtenerOCrearFila(sheetHidden, rowCont[3]);
                    rowGraph.createCell(3 * 2).setCellValue(procesarCeldaGrafico(idmuestras));
                    Cell cellVal = rowGraph.createCell(3 * 2 + 1);
                    cellVal.setCellValue(Double.parseDouble(extraerAusenciaPresencia(escherichia)));
                    cellVal.setCellStyle(estiloPresencia);
                    rowCont[3]++;
                }
                Double staphilococosGraph = extraerNumero(staphilococos);
                if (staphilococosGraph != null && !Double.isNaN(staphilococosGraph) && staphilococosGraph >= 0) {
                    agregarCeldaGrafico(sheetHidden, rowCont, 4, idmuestras, staphilococosGraph);
                }
                Double mohosGraph = extraerNumero(mohos);
                if (mohosGraph != null && !Double.isNaN(mohosGraph) && mohosGraph >= 0) {
                    agregarCeldaGrafico(sheetHidden, rowCont, 5, idmuestras, mohosGraph);
                }
                if (salmonella != null && (salmonella.toLowerCase().contains("ausencia") || salmonella.toLowerCase().contains("presencia"))) {
                    Row rowGraph = obtenerOCrearFila(sheetHidden, rowCont[6]);
                    rowGraph.createCell(6 * 2).setCellValue(procesarCeldaGrafico(idmuestras));
                    Cell cellVal = rowGraph.createCell(6 * 2 + 1);
                    cellVal.setCellValue(Double.parseDouble(extraerAusenciaPresencia(salmonella)));
                    cellVal.setCellStyle(estiloPresencia);
                    rowCont[6]++;
                }
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

    private void agregarCeldaGrafico(Sheet sheetHidden, int[] rowCont, int idx, String idmuestras, double valor) {
        int filaActual = rowCont[idx];
        Row rowGraph = sheetHidden.getRow(filaActual);
        if (rowGraph == null) {
            rowGraph = sheetHidden.createRow(filaActual);
        }
        rowGraph.createCell(idx * 2).setCellValue(procesarCeldaGrafico(idmuestras));
        rowGraph.createCell(idx * 2 + 1).setCellValue(valor);
        rowCont[idx]++;
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

        try (Connection conexion = con.getConnection()) {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, idcliente);
            ps.setString(2, tipo);
            ps.setDate(3, desdeSql);
            ps.setDate(4, hastaSql);

            ResultSet rs = ps.executeQuery();
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
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Row row = sheet.createRow(rowNum[0]++);
                String[] parametros = {"ph", "conductividad", "dqo", "dbo", "solidos10", "solidos120", "detergentes", "grasas", "fosforo", "nitrogeno", "sustancias", "coliformesTotales", "coliformesFecales", "escherichia", "hidrocarburos", "nitratos", "cloro", "sulfuros"};
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
            logger.severe("Error en la consulta SQL: " + e.getMessage());
        }
    }

    private void consultarBaseHeladaParaExcel(int idcliente, String tipo, java.sql.Date desdeSql, java.sql.Date hastaSql, Sheet sheet, Sheet sheetHidden, int[] rowNum, int[] rowCont, Workbook workbook) {
        String sql = "SELECT idmuestras, fechaMuestreo, germenes, coliformesTotales, coliformesFecales, escherichia, " + "mohos, staphilococos, salmonella FROM vistambchocolates WHERE idmuestras in (select idmuestras from " + "muestras where idcliente = ? and tipo = ?) and fechaMuestreo BETWEEN ? AND ?";
        for (int i = 0; i < rowCont.length; i++) {
            rowCont[i] = 0;
        }
        CellStyle estiloPresencia = workbook.createCellStyle();
        DataFormat format = workbook.createDataFormat();
        estiloPresencia.setDataFormat(format.getFormat("[=1]\"Presencia\";[=0]\"Ausencia\";\"N/A\""));
        try (Connection conexion = con.getConnection()) {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, idcliente);
            ps.setString(2, tipo);
            ps.setDate(3, desdeSql);
            ps.setDate(4, hastaSql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Row row = sheet.createRow(rowNum[0]++);
                String idmuestras = String.valueOf(rs.getInt("idmuestras"));
                row.createCell(0).setCellValue(idmuestras);
                String fechaMuestreo = rs.getDate("fechaMuestreo").toString();
                row.createCell(1).setCellValue(formatearEntradaExcel(fechaMuestreo));
                String germenes = rs.getString("germenes");
                row.createCell(2).setCellValue(formatearEntradaExcel(germenes));
                String coliformesTotales = rs.getString("coliformesTotales");
                row.createCell(3).setCellValue(formatearEntradaExcel(coliformesTotales));
                String coliformesFecales = rs.getString("coliformesFecales");
                row.createCell(4).setCellValue(formatearEntradaExcel(coliformesFecales));
                String escherichia = rs.getString("escherichia");
                row.createCell(5).setCellValue(formatearEntradaExcel(escherichia));
                String mohos = rs.getString("mohos");
                row.createCell(6).setCellValue(formatearEntradaExcel(mohos));
                String staphilococos = rs.getString("staphilococos");
                row.createCell(7).setCellValue(formatearEntradaExcel(staphilococos));
                String salmonella = rs.getString("salmonella");
                row.createCell(8).setCellValue(formatearEntradaExcel(salmonella));

                Double germenesGraph = extraerNumero(germenes);
                if (germenesGraph != null && !Double.isNaN(germenesGraph) && germenesGraph >= 0) {
                    agregarCeldaGrafico(sheetHidden, rowCont, 0, idmuestras, germenesGraph);
                }
                Double coliformesTotalesGraph = extraerNumero(coliformesTotales);
                if (coliformesTotalesGraph != null && !Double.isNaN(coliformesTotalesGraph) && coliformesTotalesGraph >= 0) {
                    agregarCeldaGrafico(sheetHidden, rowCont, 1, idmuestras, coliformesTotalesGraph);
                }
                Double coliformesFecalesGraph = extraerNumero(coliformesFecales);
                if (coliformesFecalesGraph != null && !Double.isNaN(coliformesFecalesGraph) && coliformesFecalesGraph >= 0) {
                    agregarCeldaGrafico(sheetHidden, rowCont, 2, idmuestras, coliformesFecalesGraph);
                }
                if (escherichia != null && (escherichia.toLowerCase().contains("ausencia") || escherichia.toLowerCase().contains("presencia"))) {
                    Row rowGraph = obtenerOCrearFila(sheetHidden, rowCont[3]);
                    rowGraph.createCell(3 * 2).setCellValue(procesarCeldaGrafico(idmuestras));
                    Cell cellVal = rowGraph.createCell(3 * 2 + 1);
                    cellVal.setCellValue(Double.parseDouble(extraerAusenciaPresencia(escherichia)));
                    cellVal.setCellStyle(estiloPresencia);
                    rowCont[3]++;
                }
                Double mohosGraph = extraerNumero(mohos);
                if (mohosGraph != null && !Double.isNaN(mohosGraph) && mohosGraph >= 0) {
                    agregarCeldaGrafico(sheetHidden, rowCont, 4, idmuestras, mohosGraph);
                }
                Double staphilococosGraph = extraerNumero(staphilococos);
                if (staphilococosGraph != null && !Double.isNaN(staphilococosGraph) && staphilococosGraph >= 0) {
                    agregarCeldaGrafico(sheetHidden, rowCont, 5, idmuestras, staphilococosGraph);
                }
                if (salmonella != null && (salmonella.toLowerCase().contains("ausencia") || salmonella.toLowerCase().contains("presencia"))) {
                    Row rowGraph = obtenerOCrearFila(sheetHidden, rowCont[6]);
                    rowGraph.createCell(6 * 2).setCellValue(procesarCeldaGrafico(idmuestras));
                    Cell cellVal = rowGraph.createCell(6 * 2 + 1);
                    cellVal.setCellValue(Double.parseDouble(extraerAusenciaPresencia(salmonella)));
                    cellVal.setCellStyle(estiloPresencia);
                    rowCont[6]++;
                }
            }
        } catch (SQLException e) {
            logger.severe("Error en la consulta SQL: " + e.getMessage());
        }
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
            return Double.parseDouble(texto.toLowerCase().replace("Mayor a ", "").trim().replaceAll("[^0-9.]", ""));
        }
        String soloNumeros = "";
        if (texto.contains(" ")) {
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
}
