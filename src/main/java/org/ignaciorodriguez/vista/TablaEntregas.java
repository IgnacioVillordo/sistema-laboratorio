package org.ignaciorodriguez.vista;

import org.ignaciorodriguez.modelo.Consultas;
import org.ignaciorodriguez.repository.ClienteRepository;
import org.ignaciorodriguez.repository.EntregaRepository;
import org.ignaciorodriguez.repository.MuestraRepository;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TablaEntregas extends javax.swing.JDialog {

    Consultas consultas = Consultas.getInstancia();
    Frame parent;
    int fila;
    TableRowSorter sorter;
    List<? extends RowSorter.SortKey> sortKeys;
    Principal p = new Principal();
    private final ClienteRepository clienteRepository = new ClienteRepository();
    private final EntregaRepository entregaRepository = new EntregaRepository();
    private final MuestraRepository muestraRepository = new MuestraRepository();
    DefaultTableModel modeloTabla = entregaRepository.recuperarEntregas();
    int anchos[] = {38, 379, 170, 201, 107, 102, 101};
    Map<String, String> map = muestraRepository.recuperarIdentificaciones();
    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JPanel jPanel1;
    private JLabel jLabel1;
    private JScrollPane jScrollPane1;
    private JTable jTable1;
    private JPanel jPanel2;
    private JButton botonBuscar;
    private JLabel jLabel2;
    private JComboBox cajaBuscar;
    private JPopupMenu popUpTabla;
    private JMenuItem GenerarReporte;
    private JMenuItem CancelarEntrega;


    public TablaEntregas(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        this.parent = parent;
        initComponents();
        jTable1.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent mouseEvent) {
                JTable table = (JTable) mouseEvent.getSource();
                Point point = mouseEvent.getPoint();
                int row = table.rowAtPoint(point);
                if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {
                    GenerarReporte.doClick();
                }
            }
        });
        jTable1.setModel(modeloTabla);
        for (int i = 0; i < anchos.length; i++) {
            jTable1.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        autocompletar();
        this.setLocationRelativeTo(null);
        ImageIcon icon = new ImageIcon("src\\vista\\icono.png");
        this.setIconImage(icon.getImage());
        jTable1.setAutoCreateRowSorter(true);
    }


    private void initComponents() {
        jPanel1 = new JPanel();
        jLabel1 = new JLabel();
        jScrollPane1 = new JScrollPane();
        jTable1 = new javax.swing.JTable() {
            private static final long serialVersionUID = 1;

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

            public String getToolTipText(MouseEvent e) {
                String tip = null;

                java.awt.Point p = e.getPoint();
                int rowIndex = rowAtPoint(p);
                try {
                    tip = map.get(jTable1.getValueAt(rowIndex, 0));
                } catch (RuntimeException e1) {
                    //catch null pointer exception if mouse is over an empty line
                }
                return tip;
            }
        };
        jPanel2 = new JPanel();
        botonBuscar = new JButton();
        jLabel2 = new JLabel();
        cajaBuscar = new JComboBox();
        popUpTabla = new JPopupMenu();
        GenerarReporte = new JMenuItem();
        CancelarEntrega = new JMenuItem();

        //======== this ========
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setMaximumSize(new Dimension(1153, 572));
        setMinimumSize(new Dimension(1153, 572));
        setPreferredSize(new Dimension(1153, 572));
        addWindowFocusListener(new WindowAdapter() {
            @Override
            public void windowGainedFocus(WindowEvent e) {
                formWindowGainedFocus(e);
            }

            @Override
            public void windowLostFocus(WindowEvent e) {
                formWindowLostFocus(e);
            }
        });
        var contentPane = getContentPane();

        //======== jPanel1 ========
        {
            jPanel1.setLayout(new GridBagLayout());

            //---- jLabel1 ----
            jLabel1.setFont(new Font("Segoe UI", Font.BOLD, 18));
            jLabel1.setText("Entregas:");
            jPanel1.add(jLabel1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.NORTHWEST, GridBagConstraints.NONE,
                    new Insets(10, 16, 0, 0), 0, 0));

            //======== jScrollPane1 ========
            {

                //---- jTable1 ----
                jTable1.setModel(new DefaultTableModel());
                jTable1.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        jTable1MousePressed(e);
                    }
                });
                jScrollPane1.setViewportView(jTable1);
            }
            jPanel1.add(jScrollPane1, new GridBagConstraints(0, 1, 2, 1, 1.0, 1.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(20, 10, 10, 10), 400, 200));

            //======== jPanel2 ========
            {

                //---- botonBuscar ----
                botonBuscar.setIcon(new ImageIcon(getClass().getResource("/imagenes/lupa.png")));
                botonBuscar.setMargin(new Insets(2, 2, 2, 2));
                botonBuscar.addActionListener(e -> botonBuscarActionPerformed(e));

                //---- jLabel2 ----
                jLabel2.setText("Buscar:");

                GroupLayout jPanel2Layout = new GroupLayout(jPanel2);
                jPanel2.setLayout(jPanel2Layout);
                jPanel2Layout.setHorizontalGroup(
                        jPanel2Layout.createParallelGroup()
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(23, 23, 23)
                                        .addComponent(jLabel2)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(cajaBuscar, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(botonBuscar))
                );
                jPanel2Layout.setVerticalGroup(
                        jPanel2Layout.createParallelGroup()
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGroup(jPanel2Layout.createParallelGroup()
                                                .addGroup(jPanel2Layout.createSequentialGroup()
                                                        .addContainerGap()
                                                        .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                                .addComponent(jLabel2)
                                                                .addComponent(cajaBuscar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                                                .addGroup(jPanel2Layout.createSequentialGroup()
                                                        .addGap(1, 1, 1)
                                                        .addComponent(botonBuscar)))
                                        .addContainerGap())
                );
            }
            jPanel1.add(jPanel2, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.NONE,
                    new Insets(0, 0, 0, 0), 0, 0));
        }

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
                contentPaneLayout.createParallelGroup()
                        .addGroup(contentPaneLayout.createSequentialGroup()
                                .addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, 984, Short.MAX_VALUE)
                                .addGap(0, 0, 0))
        );
        contentPaneLayout.setVerticalGroup(
                contentPaneLayout.createParallelGroup()
                        .addComponent(jPanel1, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 533, Short.MAX_VALUE)
        );
        pack();
        setLocationRelativeTo(getOwner());

        //======== popUpTabla ========
        {
            GenerarReporte.setText("jMenuItem1");
            GenerarReporte.addActionListener(e -> GenerarReporteActionPerformed(e));
            GenerarReporte.setText("Ver Reporte");
            popUpTabla.add(GenerarReporte);
            CancelarEntrega.setText("Cancelar entrega");
            CancelarEntrega.addActionListener(e -> CancelarEntregaActionPerformed(e));
            popUpTabla.add(CancelarEntrega);
        }
    }

    private void GenerarReporteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GenerarReporteActionPerformed
        int id = Integer.parseInt(String.valueOf(jTable1.getValueAt(fila, 0)));
        String tipo = String.valueOf(jTable1.getValueAt(fila, 3));
        String tipo2 = "";
        String db = "";
        switch (tipo) {
            case "Microbiológico de agua código":
                tipo2 = " MB Agua ";
                db = "mbagua";
                break;
            case "Microbiológico de agua bidón":
                tipo2 = " MB Agua ";
                db = "mbagua";
                break;
            case "Microbiológico de agua balnearios":
                tipo2 = " MB Agua balnearios ";
                db = "mbagua";
                break;
            case "Microbiológico de agua COFES":
                tipo2 = " MB Agua COFES ";
                db = "mbagua";
                break;
            case "Microbiológico de agua de recreación":
                tipo2 = " MB Agua de recreación ";
                db = "mbagua";
                break;
            case "Microbiológico de alimentos":
                tipo2 = " MB Alimentos ";
                db = "mbalimentos";
                break;
            case "Microbiológico de chocolates Del Turista":
                tipo2 = " MB Chocolates ";
                db = "mbchocolates";
                break;
            case "Efluentes":
                tipo2 = " Efluentes ";
                db = "efluentes";
                break;
            case "Efluentes infiltración":
                tipo2 = " Efluentes infiltración ";
                db = "efluentes";
                break;
            case "Físico químico de agua básico":
                tipo2 = " FQ agua básico ";
                db = "fqagua";
                break;
            case "Físico químico de agua completo":
                tipo2 = " FQ agua ";
                db = "determinaciones";
                break;
            case "Físico químico de alimentos":
                tipo2 = " FQ alimentos ";
                db = "determinaciones";
                break;
            case "Físico químico genérico":
                tipo2 = " FQ ";
                db = "determinaciones";
                break;
            case "Tabla nutricional":
                tipo2 = " TN ";
                db = "tablanutricional";
                break;
            case "Hisopados":
                tipo2 = " Hisopado ";
                db = "hisopados";
                break;
            case "Hisopados Alliance":
                tipo2 = " Hisopado ";
                db = "hisopados";
                break;
            case "Hisopados con límites":
                tipo2 = " Hisopado ";

                db = "hisopados";
                break;
            case "Efluentes cloaca":
                tipo2 = " Efluentes cloaca ";
                db = "efluentes";
                break;
            case "Base helada Del Turista":
                tipo2 = " Base helada ";
                db = "mbchocolates";
                break;
            case "----------ANULADO----------":
                JOptionPane.showMessageDialog(null, "Análisis anulado.");
                tipo2 = "no";
                break;
            case "Manual":
                tipo2 = " Manual ";
                db = "manual";
                break;
        }
        boolean checkear = consultas.checkearPDF(id, db);
        if (!tipo2.equals("no")) {
            String procedencia = jTable1.getValueAt(fila, 1).toString();
            String rutaGuardado = consultas.recuperarRutas("Reportes");
            String aux = "\\Informe " + id + tipo2 + clienteRepository.obtenerProcedenciayNombre(clienteRepository.obtenerIdCliente(String.valueOf(jTable1.getValueAt(fila, 1)))); //en estas tres lineas se sacan espacios de
            String aux2 = aux.replaceAll("^\\s*", "");                                                       //principio, final y se sacan las comillas
            String aux3 = aux2.replaceAll("\\s*$", "");
            String pdf = aux3.replaceAll("\"", "");
            pdf += ".pdf";
            if (checkear) {
                Cursor cursor = new Cursor(Cursor.WAIT_CURSOR);
                this.setCursor(cursor);
                this.dispose();
                switch (tipo) {
                    case "Microbiológico de agua código":
                        consultas.generarReporteMBAgua(id, procedencia);
                        break;
                    case "Microbiológico de agua bidón":
                        consultas.generarReporteMBAgua(id, procedencia);
                        break;
                    case "Microbiológico de agua balnearios":
                        consultas.generarReporteMBAguaBalnearios(id, procedencia);
                        break;
                    case "Microbiológico de agua COFES":
                        consultas.generarReporteMBAguaCOFES(id, procedencia);
                        break;
                    case "Microbiológico de agua de recreación":
                        consultas.generarReporteMBAguaDeRecreacion(id, procedencia);
                        break;
                    case "Físico químico de agua básico":
                        consultas.generarReporteFQAgua(id, procedencia);
                        break;
                    case "Físico químico de agua completo":
                        FQAlimentos fqa = new FQAlimentos(p, true, id, true, pdf, Determinaciones.AGUA);
                        fqa.apretarBoton();
                        break;
                    case "Físico químico de alimentos":
                        FQAlimentos fqaa = new FQAlimentos(p, true, id, true, pdf, Determinaciones.ALIMENTO);
                        fqaa.apretarBoton();
                        break;
                    case "Físico químico genérico":
                        FQAlimentos fqg = new FQAlimentos(p, true, id, true, pdf, Determinaciones.GENERICO);
                        fqg.apretarBoton();
                        break;
                    case "Tabla nutricional":
                        consultas.generarReporteTN(id, procedencia);
                        break;
                    case "Efluentes":
                        consultas.generarReporteEfluentes(id, procedencia);
                        break;
                    case "Microbiológico de chocolates Del Turista":
                        consultas.generarReporteMBChocolates(id, procedencia);
                        break;
                    case "Microbiológico de alimentos":
                        consultas.generarReporteMBAlimentos(id, procedencia);
                        break;
                    case "Hisopados":
                        consultas.generarReporteHisopados(id, procedencia);
                        break;
                    case "Hisopados Alliance":
                        System.out.println("hisopado alliance");
                        consultas.generarReporteHisopadosAlliance(id, procedencia);
                        break;
                    case "Hisopados con límites":
                        consultas.generarReporteHisopadosAlliance(id, procedencia);
                        break;
                    case "Efluentes cloaca":
                        consultas.generarReporteEfluentesCloaca(id, procedencia);
                        break;
                    case "Efluentes infiltración":
                        consultas.generarReporteEfluentesInfiltracion(id, procedencia);
                        break;
                    case "Base helada Del Turista":
                        consultas.generarReporteMBBaseHelada(id, procedencia);
                        break;
                    case "Manual":
                        consultas.generarReporteManual(id, procedencia);
                        break;
                }
                cursor = new Cursor(Cursor.DEFAULT_CURSOR);
                this.setCursor(cursor);
            } else {
                JOptionPane.showMessageDialog(null, "El análisis no ha sido analizado.");
            }
        }
    }

    private void jTable1MousePressed(java.awt.event.MouseEvent evt) {
        fila = jTable1.rowAtPoint(evt.getPoint());
    }

    private void CancelarEntregaActionPerformed(java.awt.event.ActionEvent evt) {
        System.out.println(jTable1.getValueAt(fila, 0));
        int id = Integer.parseInt(jTable1.getValueAt(fila, 0).toString());
        if (entregaRepository.cancelarEntrega(id)) {
            if (entregaRepository.cancelarEntrega2(id)) {
                JOptionPane.showMessageDialog(null, "Se canceló la entrega");
            }
        }
    }

    private void formWindowGainedFocus(java.awt.event.WindowEvent evt) {
        sorter = (TableRowSorter) jTable1.getRowSorter();
        sortKeys = sorter.getSortKeys();
        modeloTabla.setRowCount(0);
        modeloTabla.setColumnCount(0);
        modeloTabla = entregaRepository.recuperarEntregas();
        for (int i = 0; i < 6; i++) {
            jTable1.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        sorter = (TableRowSorter) jTable1.getRowSorter();
        sorter.setSortKeys(sortKeys);
    }

    private void botonBuscarActionPerformed(java.awt.event.ActionEvent evt) {
//        Conexion con = new Conexion();
//        PreparedStatement ps = null;
//        java.sql.ResultSet rs = null;
//        Connection conexion = con.getConnection();
//        String[] fila2 = new String[8];
//
//        if (!cajaBuscar.getText().equals("")) {
//            try {
//                modeloTabla.setRowCount(0);
//                ps = conexion.prepareStatement("select * from vistaEntregas"
//                        + " where procedencia like ?");
//                ps.setString(1, "%" + cajaBuscar.getText() + "%");
//                rs = ps.executeQuery();
//                while (rs.next()) {
//                    for (int i = 0; i < fila2.length; i++) {
//                        if (i == 5) {
//                            String timestamp = new SimpleDateFormat("dd/MM/yyyy   HH:mm").format(rs.getTimestamp(i + 1));
//                            fila2[i] = timestamp;
//                        } else if (i == 6) {
//                            String fecha = new SimpleDateFormat("dd/MM/yyyy").format(rs.getDate("fechaAnalisis"));
//                            fila2[i] = fecha;
//                        } else if (i == 0) {
//                            fila2[i] = String.format("%05d", rs.getObject(i + 1));
//                        } else {
//                            fila2[i] = rs.getObject(i + 1).toString();
//                        }
//                    }
//                    modeloTabla.addRow(fila2); // se agrega un renglon al org.ignaciorodriguez.modelo de la tabla
//                }
//            } catch (Exception e) {
//            } finally {
//                try {
//                    conexion.close();
//                } catch (Exception e) {
//                }
//            }
//        }
//        jTable1.setModel(modeloTabla);
//        for (int i = 0; i < 6; i++) {
//            jTable1.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
//        }
    }

    private void botonBorrarActionPerformed(java.awt.event.ActionEvent evt) {
        cajaBuscar.setSelectedIndex(0);
        modeloTabla.setColumnCount(0);
        modeloTabla.setRowCount(0);
        modeloTabla = entregaRepository.recuperarEntregas();
        jTable1.setModel(modeloTabla);
        for (int i = 0; i < 6; i++) {
            jTable1.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
    }


    private void formWindowLostFocus(java.awt.event.WindowEvent evt) {
    }

    public void autocompletar() {
        ArrayList<Object> lista = entregaRepository.autocompletarEntregas();
        cajaBuscar.setModel(new DefaultComboBoxModel(lista.toArray()));
        AutoCompleteDecorator.decorate(cajaBuscar);
    }
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
