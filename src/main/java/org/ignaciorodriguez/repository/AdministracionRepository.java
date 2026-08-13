package org.ignaciorodriguez.repository;

import org.ignaciorodriguez.modelo.Administracion;
import org.ignaciorodriguez.modelo.Conexion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@Repository
public interface AdministracionRepository extends JpaRepository<Administracion, Long> {

    //    public boolean cancelarEntrega2(int id) {
//        Connection conexion = con.getConnection();
//        try {
//            PreparedStatement ps = conexion.prepareStatement("update administracion set entregado = ? where idmuestras = ?");
//            ps.setInt(1, 0);
//            ps.setInt(2, id);
//            ps.executeUpdate();
//            return true;
//        } catch (Exception e) {
//            logger.severe("Error al cancelar la entrega, " + e);
//            return false;
//        }
//    }

//    public boolean seleccionarVencimiento(int id, int seleccionar) {
//        try (Connection conexion = con.getConnection()) {
//            PreparedStatement ps = conexion.prepareStatement("update administracion set seleccionadoVencimiento = ? where idmuestras = ?");
//            ps.setInt(1, seleccionar);
//            ps.setInt(2, id);
//            ps.executeUpdate();
//            return true;
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, "Error al actualizar vencimiento, " + e);
//            return false;
//        } finally {
//            try (Connection conexion = con.getConnection()) {
//                conexion.close();
//            } catch (Exception e) {
//                System.err.println("Error, " + e);
//            }
//        }
//    }
//
//    public void agregarAdministracion(int id, double precio, int pago, int factura) {
//        try (Connection conexion = con.getConnection()) {
//            PreparedStatement ps = conexion.prepareStatement("INSERT INTO administracion " + "(idmuestras,precioTotal,pago,factura,entrada,borrado)VALUES " + "(?,?,?,?,current_timestamp(),0)");
//            ps.setInt(1, id);
//            ps.setDouble(2, precio);
//            ps.setInt(3, pago);
//            ps.setInt(4, factura);
//            ps.executeUpdate();
//        } catch (Exception e) {
//            logger.severe("Error al agregar datos, " + e);
//        } finally {
//            try (Connection conexion = con.getConnection()) {
//                conexion.close();
//            } catch (Exception e) {
//                logger.severe("Error, " + e);
//            }
//        }
//    }
//
//    public void editarAdministracion(int id, double precio, int pago, int factura) {
//        try (Connection conexion = con.getConnection()) {
//            PreparedStatement ps = conexion.prepareStatement("update administracion set precioTotal" + " = ?, pago = ?, factura = ?, borrado = 0 where idmuestras = ?");
//            ps.setDouble(1, precio);
//            ps.setInt(2, pago);
//            ps.setInt(3, factura);
//            ps.setInt(4, id);
//            ps.executeUpdate();
//        } catch (Exception e) {
//            logger.severe("Error al agregar datos, " + e);
//        } finally {
//            try (Connection conexion = con.getConnection()) {
//                conexion.close();
//            } catch (Exception e) {
//                logger.severe("Error, " + e);
//            }
//        }
//    }
//
//    public boolean borrarAnalisis(int id, int sino) {
//        try (Connection conexion = con.getConnection()) {
//            PreparedStatement ps = conexion.prepareStatement("update administracion set borrado = ? where idmuestras = ?");
//            ps.setInt(1, sino);
//            ps.setInt(2, id);
//            ps.executeUpdate();
//            return true;
//        } catch (Exception e) {
//            logger.severe("Error al borrar analisis, " + e);
//            return false;
//        } finally {
//            try (Connection conexion = con.getConnection()) {
//                conexion.close();
//            } catch (Exception e) {
//                logger.severe("Error, " + e);
//            }
//        }
//    }
//
//    public Date recuperarEntrada(int id) {
//        Date analisis = null;
//        try (Connection conexion = con.getConnection()) {
//            PreparedStatement ps = conexion.prepareStatement("select entrada from administracion where idmuestras = ?");
//            ps.setInt(1, id);
//            ResultSet rs = ps.executeQuery();
//            while (rs.next()) {
//                analisis = rs.getDate("entrada");
//            }
//            return analisis;
//        } catch (Exception e) {
//            JOptionPane.showInputDialog("Error al recuperar titulo " + e);
//            return null;
//        } finally {
//            try (Connection conexion = con.getConnection()) {
//                conexion.close();
//            } catch (Exception e) {
//                logger.severe("Error, " + e);
//            }
//        }
//    }
//
    @Query(value = "SELECT SUM(precioTotal) as ganancias, DATE(entrada) as fecha FROM administracion WHERE DATE(entrada) BETWEEN :desde AND :hasta GROUP BY fecha", nativeQuery = true)
    List<Administracion> findGananciaBetweenFechas(@Param("desde")LocalDate desde,
                                                   @Param("hasta")LocalDate hasta);
//    public DefaultTableModel obtenerGanancias(Date desde, Date hasta) {//se obtienen los datos para la tabla de vencimientos
//        double total = 0;
//        Object fila[] = new Object[2];
//        DefaultTableModel modeloGanancias = new DefaultTableModel();
//        modeloGanancias.setColumnCount(0);
//        DecimalFormat formato = new DecimalFormat("#.##");
//        if (modeloGanancias.getColumnCount() == 0) {
//            modeloGanancias.addColumn("Fecha");
//            modeloGanancias.addColumn("Ganancias");
//        }
//        modeloGanancias.setRowCount(0);
//        java.sql.Date desdeSql = new java.sql.Date(desde.getTime());
//        java.sql.Date hastaSql = new java.sql.Date(hasta.getTime());
//        try (Connection conexion = con.getConnection()) {
//            PreparedStatement ps = conexion.prepareStatement("select sum(precioTotal) as ganancias, date(entrada) as fecha from administracion where date(entrada) between ? and ? group by fecha;"); // se recuperan los datos de la base de datos de los vencimeintos de todo el mes
//            ps.setDate(1, desdeSql);
//            ps.setDate(2, hastaSql);
//            System.out.println(ps.toString());
//            ResultSet rs = ps.executeQuery();
//            while (rs.next()) {
//                fila[0] = rs.getDate("fecha");
//                fila[1] = formato.format(rs.getDouble("ganancias"));
//                total += Double.parseDouble(fila[1].toString());
//                modeloGanancias.addRow(fila);
//            }
//            fila[0] = "Total:";
//            fila[1] = "$ " + formato.format(total);
//            modeloGanancias.addRow(fila);
//            return modeloGanancias;
//        } catch (Exception e) {
//            logger.severe("Error al obtener ganancias, " + e);
//            logger.severe(e.getStackTrace().toString());
//        } finally {
//            try (Connection conexion = con.getConnection()) {
//                conexion.close();
//            } catch (Exception e) {
//                logger.severe("Error, " + e);
//            }
//        }
//        return null;
//    }
//
//    public DefaultTableModel recuperarBorrados() {
//        String[] fila = new String[6];
//        DefaultTableModel modeloTabla = new DefaultTableModel();
//        try (Connection conexion = con.getConnection()) {
//            modeloTabla.setRowCount(0);
//            PreparedStatement ps = conexion.prepareStatement("select idmuestras, procedencia, solicitante,"
//                    + " fechaMuestreo, fechaAnalisis, tipo from vistaborrados");
//            ResultSet rs = ps.executeQuery();
//            while (rs.next()) {
//                for (int i = 0; i < 6; i++) {
//                    switch (i) {
//                        case 0: {
//                            fila[i] = String.format("%05d", rs.getObject(i + 1));
//                            break;
//                        }
//                        case 3: {
//                            if (rs.getDate("fechaMuestreo").toString().equals("1111-11-11")) {
//                                fila[i] = "-";
//                            } else {
//                                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
//                                fila[i] = formatter.format(rs.getDate("fechaMuestreo"));
//                            }
//                            break;
//                        }
//                        case 4: {
//                            if (rs.getObject("fechaAnalisis").toString().equals("1111-11-11")) {
//                                fila[i] = "-";
//                            } else {
//                                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
//                                fila[i] = formatter.format(rs.getDate("fechaAnalisis"));
//                            }
//                            break;
//                        }
//                        default:
//                            fila[i] = String.valueOf(rs.getObject(i + 1));
//                            break;
//                    }
//                }
//                modeloTabla.addRow(fila); // se agrega un renglon al modelo de la tabla
//            }
//            return modeloTabla; //se devuelve un modelo de tabla
//        } catch (Exception e) {
//            logger.severe("Error al recuperarBorrados, " + e);
//            return null;
//        }
//    }
    //    public void analizado(int id) {
//        try (Connection conexion = con.getConnection()) {
//            PreparedStatement ps = conexion.prepareStatement("update administracion set analizado = analizado + 1 "
//                    + "where idmuestras = ?");
//            ps.setInt(1, id);
//            ps.executeUpdate();
//        } catch (Exception e) {
//            logger.severe("Error al actualizar analizado, " + e);
//        }
//    }
    //    public void marcarSeleccionados(int id) {
//        try (Connection conexion = con.getConnection()) {
//            PreparedStatement ps = conexion.prepareStatement("update administracion set seleccionado = 1 "
//                    + "where idmuestras = ?");
//            ps.setInt(1, id);
//            ps.executeUpdate();
//        } catch (Exception e) {
//            System.err.println("Error, " + e);
//        }
//    }
    //    public void entregado(int id) {
//        try (Connection conexion = con.getConnection()) {
//            PreparedStatement ps = conexion.prepareStatement("update administracion set entregado = 1 where idmuestras = ?");
//            ps.setInt(1, id);
//            ps.executeUpdate();
//        } catch (Exception e) {
//            logger.severe("Error al actualizar entregado, " + e);
//        }
//    }
//
}
