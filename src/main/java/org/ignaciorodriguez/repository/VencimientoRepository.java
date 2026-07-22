package org.ignaciorodriguez.repository;

import org.ignaciorodriguez.modelo.Conexion;
import org.ignaciorodriguez.modelo.Resultados;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

public class VencimientoRepository {

    private final static Logger logger = Logger.getLogger(VencimientoRepository.class.getName());
    Conexion con = new Conexion();
    MuestraRepository muestraRepository = new MuestraRepository();

    public boolean agregarVencimiento(Resultados r) { //se agega el vencimiento, si es mb se agregan 6 meses y fq 12 meses
        int id = muestraRepository.obtenerIdMuestra();
        try (Connection conexion = con.getConnection()) {
            PreparedStatement ps = null;
            if (r.getTipo().startsWith("Microbiológico")) {
                String vencimientoMb = "INSERT INTO `laboratorio`.`vencimientos` " + "(`fechaVencimiento`,idmuestras) VALUES (date_add(?,interval 6 month),?)";
                ps = conexion.prepareStatement(vencimientoMb);
                ps.setDate(1, r.getFechaAnalisis());
                ps.setInt(2, r.getIdmuestras());
            } else if (r.getTipo().startsWith("Físico químico")) {
                String vencimientoFq = "INSERT INTO `laboratorio`.`vencimientos` " + "(`fechaVencimiento`,idmuestras) VALUES (date_add(?,interval 12 month),?)";
                ps = conexion.prepareStatement(vencimientoFq);
                ps.setDate(1, r.getFechaAnalisis());
                ps.setInt(2, r.getIdmuestras());
            }

            int response = 0;
            if (ps != null) {
                ps.executeUpdate();
            }
            return response > 0;
        } catch (Exception e) {
            logger.severe("Error al agregar vencimiento, " + e);
            return false;
        }
    }

    public DefaultTableModel obtenerVencimientos(Date desde, Date hasta, boolean actualizar) {//se obtienen los datos para la tabla de vencimientos
        Object fila[] = new Object[5];
        boolean aviso;
        DefaultTableModel modeloVencimientos = new DefaultTableModel();
        modeloVencimientos.addColumn("ID");
        modeloVencimientos.addColumn("Procedencia");
        modeloVencimientos.addColumn("Solicitante");
        modeloVencimientos.addColumn("Fecha de vencimiento");
        modeloVencimientos.addColumn("Avisado");
        java.sql.Date desdeSql = new java.sql.Date(desde.getTime());
        java.sql.Date hastaSql = new java.sql.Date(hasta.getTime());
        try (Connection conexion = con.getConnection()) {
            PreparedStatement ps;
            if (actualizar) {
                ps = conexion.prepareStatement("select * from vistavencimientos where" + " fechaVencimiento between ? and ? order by fechaVencimiento asc");
            } else {
                ps = conexion.prepareStatement("select * from vistavencimientos where" + " fechaVencimiento between first_day(?) and last_day(?) order by fechaVencimiento asc"); // se recuperan los datos de la base de datos de los vencimeintos de todo el mes
            }
            ps.setDate(1, desdeSql);
            ps.setDate(2, hastaSql);
            ResultSet rs = ps.executeQuery();
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
                            aviso = rs.getInt("aviso") != 0;
                            fila[i] = aviso;
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
            logger.severe("Error al obtener vencimientos, " + e);
        }
        return null;
    }

    public DefaultTableModel obtenerVencimientosInformes(Date desde, Date hasta) {
        Object fila[] = new Object[4];
        boolean aviso = false;
        DefaultTableModel modeloVencimientos = new DefaultTableModel();
        modeloVencimientos.addColumn("ID");
        modeloVencimientos.addColumn("Procedencia");
        modeloVencimientos.addColumn("Vencimiento");
        modeloVencimientos.addColumn("Tipo");
        modeloVencimientos.addColumn("Seleccionar");
        java.sql.Date desdeSql = new java.sql.Date(desde.getTime());
        java.sql.Date hastaSql = new java.sql.Date(hasta.getTime());
        try (Connection conexion = con.getConnection()) {
            PreparedStatement ps = conexion.prepareStatement("select idmuestras, procedencia, fechaVencimiento, tipo from vistavencimientos where aviso = 0 and fechaVencimiento between ? and ?");
            ps.setDate(1, desdeSql);
            ps.setDate(2, hastaSql);
            ResultSet rs = ps.executeQuery();
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
            logger.severe("Error al obtener vencimientos, " + e);
        }
        return null;
    }

    public boolean checkearVencimiento(Resultados r) {
        try (Connection conexion = con.getConnection()) {
            PreparedStatement ps = conexion.prepareStatement("select * from vencimientos where idmuestras = ?");
            ps.setInt(1, r.getIdmuestras());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (Exception e) {
            logger.severe("Error al recuperar muestra, " + e);
            return false;
        }
        return false;
    }

    public boolean actualizarVencimiento(Resultados r) {
        try (Connection conexion = con.getConnection()) {
            PreparedStatement ps = null;
            if (r.getTipo().startsWith("Microbiológico")) {
                ps = conexion.prepareStatement("update vencimientos set fechaVencimiento = " + "(date_add(?,interval 6 month)) where `idmuestras` = ?");
                ps.setDate(1, r.getFechaAnalisis());
                ps.setInt(2, r.getIdmuestras());
            } else if (r.getTipo().startsWith("Físico químico")) {
                ps = conexion.prepareStatement("update vencimientos set fechaVencimiento = " + "(date_add(?,interval 12 month)) where `idmuestras` = ?");
                ps.setDate(1, r.getFechaAnalisis());
                ps.setInt(2, r.getIdmuestras());
            }
            int response = 0;
            if (ps != null) {
                response = ps.executeUpdate();
            }
            return response > 0;
        } catch (Exception e) {
            logger.severe("Error al actualizar vencimiento, " + e);
            return false;
        }
    }


    public void actualizarAvisados(int id, int aviso) {
        try (Connection conexion = con.getConnection()) {
            PreparedStatement ps = conexion.prepareStatement("update vencimientos set aviso = ? where idmuestras = ?");
            ps.setInt(1, aviso);
            ps.setInt(2, id);
            System.out.println("ps.toString() = " + ps.toString());
            ps.executeUpdate();
        } catch (Exception e) {
            logger.severe("Error, " + e);
        }
    }

}
