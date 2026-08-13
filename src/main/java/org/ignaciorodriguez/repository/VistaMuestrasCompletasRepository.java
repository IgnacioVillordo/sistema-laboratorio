package org.ignaciorodriguez.repository;

import org.ignaciorodriguez.modelo.VistaMuestrasCompletas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VistaMuestrasCompletasRepository extends JpaRepository<VistaMuestrasCompletas, Long> {

    //    public DefaultTableModel llenarTabla() {
//        String[] fila = new String[11];
//        DefaultTableModel modeloTabla = new DefaultTableModel();
//        modeloTabla.addColumn("ID");
//        modeloTabla.addColumn("Procedencia");
//        modeloTabla.addColumn("Solicitante");
//        modeloTabla.addColumn("N° establecimiento");
//        modeloTabla.addColumn("Muestreo");
//        modeloTabla.addColumn("Análisis");
//        modeloTabla.addColumn("Realizado por");
//        modeloTabla.addColumn("Fecha entrada");
//        modeloTabla.addColumn("Pago");
//        modeloTabla.addColumn("Factura ");
//        modeloTabla.addColumn("Tipo de análisis");
//        try (Connection conexion = con.getConnection()) {
//            modeloTabla.setRowCount(0);
//            PreparedStatement ps = conexion.prepareStatement("select * from vistatabla");
//            ResultSet rs = ps.executeQuery();
//            while (rs.next()) {
//                for (int i = 0; i < fila.length; i++) {
//                    switch (i) {
//                        case 0: {
//                            fila[i] = String.format("%05d", rs.getObject(i + 1));
//                            break;
//                        }
//                        case 4: {
//                            if (rs.getDate("fechaMuestreo").toString().equals("1111-11-11")) {
//                                fila[i] = "-";
//                            } else {
//                                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
//                                fila[i] = formatter.format(rs.getDate("fechaMuestreo"));
//                            }
//                            break;
//                        }
//                        case 5: {
//                            if (rs.getObject("fechaAnalisis").toString().equals("1111-11-11")) {
//                                fila[i] = "-";
//                            } else {
//                                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
//                                fila[i] = formatter.format(rs.getDate("fechaAnalisis"));
//                            }
//                            break;
//                        }
//                        case 7: {
//                            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
//                            fila[i] = formatter.format(rs.getDate("entrada"));
//                            break;
//                        }
//                        case 8:
//                            if (rs.getObject(i + 1).equals(1)) {
//                                fila[i] = "Si";//si el dato es 1 se pone "si" y si es 0 se pone "no"
//                            } else if (rs.getObject(i + 1).equals(0)) {
//                                fila[i] = "No";
//                            }
//                            break;
//                        case 9:
//                            if (rs.getObject(i + 1).equals(1)) {
//                                fila[i] = "Si";
//                            } else if (rs.getObject(i + 1).equals(0)) {
//                                fila[i] = "No";
//                            }
//                            break;
//                        default:
//                            fila[i] = String.valueOf(rs.getObject(i + 1));
//                            break;
//                    }
//                }
//                modeloTabla.addRow(fila);
//            }
//            return modeloTabla;
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, "Error al llenarTabla, " + e);
//            return null;
//        }
//    }
    //    public String recuperarTipoAnalisis(int id) {
//        String tipo = "";
//        try (Connection conexion = con.getConnection()) {
//            PreparedStatement ps = conexion.prepareStatement("select tipo from vistaTabla where idmuestras = ?");
//            ps.setInt(1, id);
//            ResultSet rs = ps.executeQuery();
//            while (rs.next()) {
//                tipo = rs.getString("tipo");
//            }
//            return tipo;
//        } catch (Exception e) {
//            logger.severe("Error al recuperar tipo de analisis, " + e);
//            return null;
//        }
//    }
    //    public String obtenerHablitacion(String s) {
//        String numero = null;
//        try (Connection conexion = con.getConnection()) {
//            try {
//                PreparedStatement ps = conexion.prepareStatement("select numeroEstablecimiento from vistaTabla where procedencia = ?");
//                ps.setString(1, s);
//                ResultSet rs = ps.executeQuery();
//                if (rs.next()) {
//                    numero = rs.getString("numeroEstablecimiento");
//                    return numero;
//                }
//            } catch (Exception e) {
//                JOptionPane.showMessageDialog(null, "Error al obtener numero de habilitacion, " + e);
//            }
//        } catch (Exception e) {
//            logger.severe("Error, " + e);
//        }
//        return numero;
//    }
}
