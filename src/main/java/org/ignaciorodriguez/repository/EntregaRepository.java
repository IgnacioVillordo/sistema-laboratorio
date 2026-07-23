package org.ignaciorodriguez.repository;

import org.ignaciorodriguez.modelo.Conexion;
import org.ignaciorodriguez.modelo.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Logger;

public class EntregaRepository {

    Conexion con = new Conexion();
    private final static Logger logger = Logger.getLogger(EntregaRepository.class.getName());

    public boolean entregarMuestra(Usuario usuario, int idmuestra) {
        try (Connection conexion = con.getConnection()) {
            PreparedStatement ps = conexion.prepareStatement("insert into entregas (idmuestras, " + "idusuario, persona, hora) values (?,?,?,current_timestamp())");
            ps.setInt(1, idmuestra);
            ps.setInt(2, usuario.getId());
            ps.setString(3, usuario.getUsuario());
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            logger.severe("Error al entregar muestra, " + e);
            return false;
        }
    }

    public DefaultTableModel recuperarEntregas() {
        DefaultTableModel modeloEntregas = new DefaultTableModel();
        modeloEntregas.addColumn("ID");
        modeloEntregas.addColumn("Procedencia");
        modeloEntregas.addColumn("Solicitante");
        modeloEntregas.addColumn("Tipo de análisis");
        modeloEntregas.addColumn("Entregó");
        modeloEntregas.addColumn("Hora");
        modeloEntregas.addColumn("Fecha de análisis");
        Object[] fila = new Object[7];
        try (Connection conexion = con.getConnection()) {
            PreparedStatement ps = conexion.prepareStatement("select * from vistaEntregas");
            ResultSet rs = ps.executeQuery();

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
            logger.severe("Error al obtener entragas, " + e);
            return null;
        }
    }

    public boolean cancelarEntrega(int id) {
        try (Connection conexion = con.getConnection()) {
            PreparedStatement ps = conexion.prepareStatement("delete from entregas where idmuestras = ?");
            ps.setInt(1, id);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            logger.severe("Error al cancelar la entrega, " + e);
            return false;
        }
    }

    public boolean cancelarEntrega2(int id) {
        Connection conexion = con.getConnection();
        try {
            PreparedStatement ps = conexion.prepareStatement("update administracion set entregado = ? where idmuestras = ?");
            ps.setInt(1, 0);
            ps.setInt(2, id);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            logger.severe("Error al cancelar la entrega, " + e);
            return false;
        }
    }

    public ArrayList<Object> autocompletarEntregas() {
        ArrayList<Object> arreglo = new ArrayList<>();
        Connection conexion = con.getConnection();
        try {
            PreparedStatement ps = conexion.prepareStatement("select distinct procedencia from vistaEntregas");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                arreglo.add(rs.getString("procedencia"));
            }
            return arreglo;
        } catch (Exception e) {
            logger.severe("Error, " + e);
            return null;
        }
    }

    public boolean hayEntrega(int id) {
        try (Connection conexion = con.getConnection()) {
            PreparedStatement ps = conexion.prepareStatement("select * from entregas where idmuestras = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
            logger.severe("Error al guardar datos, " + e);
            return false;
        }
    }
}
