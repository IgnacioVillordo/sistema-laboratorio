package org.ignaciorodriguez.repository;

import org.ignaciorodriguez.modelo.VistaBusqueda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VistaBusquedaRepository extends JpaRepository<VistaBusqueda, Long> {
    //    public DefaultTableModel buscarTabla(String parametro, String valor) {
//        if (Objects.equals(parametro, "-1") || Objects.equals(valor, "-1")) {
//            return llenarTabla();
//        } else {
//            DefaultTableModel modeloTabla = new DefaultTableModel();
//            String[] fila = new String[13];
//            modeloTabla.addColumn("ID");
//            modeloTabla.addColumn("Procedencia");
//            modeloTabla.addColumn("Solicitante");
//            modeloTabla.addColumn("N° establecimiento");
//            modeloTabla.addColumn("Muestreo");
//            modeloTabla.addColumn("Análisis");
//            modeloTabla.addColumn("Realizado por");
//            modeloTabla.addColumn("Coste total");
//            modeloTabla.addColumn("Pago");
//            modeloTabla.addColumn("Factura ");
//            modeloTabla.addColumn("Tipo de análisis");
//            modeloTabla.addColumn("Identificaciones");
//            modeloTabla.addColumn("Estado");
//            try (Connection conexion = con.getConnection()) {
//                modeloTabla.setRowCount(0);
//                PreparedStatement ps;
//                if (Objects.equals(parametro, "procedencia") || Objects.equals(parametro, "solicitante")) {
//                    ps = conexion.prepareStatement("select * from vistabusqueda where " + parametro + " like ?");
//                    ps.setString(1, "%" + valor + "%");
//                } else if (Objects.equals(parametro, "fechaAnalisis") || Objects.equals(parametro, "fechaMuestreo")) {
//                    ps = conexion.prepareStatement("select * from vistabusqueda where " + parametro + " between " + valor);
//                } else {
//                    ps = conexion.prepareStatement("select * from vistabusqueda where " + parametro + " = " + valor);
//                }
//                ResultSet rs = ps.executeQuery();
//                while (rs.next()) {
//                    for (int i = 0; i < fila.length; i++) {
//                        switch (i) {
//                            case 0: {
//                                fila[i] = String.format("%05d", rs.getObject(i + 1));
//                                break;
//                            }
//                            case 4: {
//                                if (rs.getDate("fechaMuestreo").toString().equals("1111-11-11")) {
//                                    fila[i] = "-";
//                                } else {
//                                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
//                                    fila[i] = formatter.format(rs.getDate("fechaMuestreo"));
//                                }
//                                break;
//                            }
//                            case 5: {
//                                if (rs.getObject("fechaAnalisis").toString().equals("1111-11-11")) {
//                                    fila[i] = "-";
//                                } else {
//                                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
//                                    fila[i] = formatter.format(rs.getDate("fechaAnalisis"));
//                                }
//                                break;
//                            }
//                            case 7: {
//                                if (rs.getDouble("precioTotal") == -1) {
//                                    fila[i] = "-";
//                                } else {
//                                    fila[i] = String.valueOf(rs.getDouble(i + 1));
//                                }
//                                break;
//                            }
//                            case 8:
//                                if (rs.getObject(i + 1).equals(1)) {
//                                    fila[i] = "Si";//si el dato es 1 se pone "si" y si es 0 se pone "no"
//                                } else if (rs.getObject(i + 1).equals(0)) {
//                                    fila[i] = "No";
//                                }
//                                break;
//                            case 9:
//                                if (rs.getObject(i + 1).equals(1)) {
//                                    fila[i] = "Si";
//                                } else if (rs.getObject(i + 1).equals(0)) {
//                                    fila[i] = "No";
//                                }
//                                break;
//                            default:
//                                fila[i] = String.valueOf(rs.getObject(i + 1));
//                                break;
//                        }
//                    }
//                    modeloTabla.addRow(fila);
//                }
//                return modeloTabla;
//            } catch (Exception e) {
//                logger.severe("Error al llenarTabla, " + e);
//                return null;
//            }
//        }
//    }
//
}
