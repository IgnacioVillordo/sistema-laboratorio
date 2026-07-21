package org.ignaciorodriguez.repository;

import groovyjarjarantlr.preprocessor.Preprocessor;
import org.ignaciorodriguez.modelo.Conexion;
import org.ignaciorodriguez.modelo.Muestra;
import org.ignaciorodriguez.modelo.Resultados;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.logging.Logger;

public class MuestraRepository {

    private static final Logger logger = Logger.getLogger(MuestraRepository.class.getName());
    Conexion con = new Conexion();

    public boolean agregarMuestra(Muestra m) {
        Date fa = m.getFechaAnalisis();
        Date fv = m.getFechaVencimiento();
        Date fe = m.getFechaElaboracion();
        java.sql.Date def = new java.sql.Date(-789, 10, 11); //se crea una fecha por defecto (1111-11-11) para cuando no se introduce alguna fecha
        try (Connection conexion = con.getConnection()) {
            PreparedStatement ps = conexion.prepareStatement("insert into muestras (idcliente,solicitante,"
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
            logger.severe("Error al agregar muestra, " + e);
            return false;
        }
    }

    public boolean editarMuestra(Muestra m) {
        Date fa = m.getFechaAnalisis();
        Date fv = m.getFechaVencimiento();
        Date fe = m.getFechaElaboracion();
        java.sql.Date def = new java.sql.Date(-789, 10, 11);
        try (Connection conexion = con.getConnection()) {
            PreparedStatement ps = conexion.prepareStatement("update muestras set solicitante = ?, "
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
            logger.severe("Error al editar muestra, " + e);
            return false;
        }
    }

    public boolean eliminarMuestra(int id) {
        try (Connection conexion = con.getConnection()) {
            PreparedStatement ps = conexion.prepareStatement("DELETE FROM `laboratorio`.`muestras` WHERE (`idmuestras` = ?)");
            ps.setInt(1, id);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            logger.severe("Error al eliminar muestra, " + e);
            return false;
        }
    }

    public Muestra obtenerMuestra(int id) {
        Muestra m = new Muestra();
        try (Connection conexion = con.getConnection()) {
            PreparedStatement ps = conexion.prepareStatement("SELECT `solicitante`, `procedencia`, "
                    + "`numeroEstablecimiento`, `fechaMuestreo`, `realizadoPor`, "
                    + "`precioTotal`, `pago`, `factura`, `tipo`, `lote`, "
                    + "`identificacion`, `fechaElaboracion`, `lugarMuestreo`, "
                    + "`fechaVencimiento`, aguatipo, idcliente FROM `vistaeditar` where idmuestras = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
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
            logger.severe("Error al obtener muestra, " + e);
            return null;
        }
    }

    public int obtenerIdMuestra() {
        try (Connection conexion = con.getConnection()) {
            PreparedStatement ps = conexion.prepareStatement("select idmuestras from laboratorio.muestras ORDER BY idmuestras DESC LIMIT 1");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("idmuestras");
                return id;
            }
        } catch (Exception e) {
            logger.severe("Error al obtener id, " + e);
            return 0;
        }
        return 0;
    }

    public int recuperarIdMuestrasSiguiente() {
        try (Connection conexion = con.getConnection()) {
            PreparedStatement ps = conexion.prepareStatement("select idmuestras from muestras order by idmuestras desc limit 1");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int aux = rs.getInt("idmuestras");
                return aux + 1;
            }
        } catch (Exception e) {
            logger.severe("Error al recuperar id, " + e);
            return -1;
        }
        return -1;
    }

    public String obtenerProcedencia(int id) {
        String procedencia = "";
        try (Connection conexion = con.getConnection()) {
            PreparedStatement ps = conexion.prepareStatement("select procedencia from vistaTabla2 where idmuestras = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                procedencia = rs.getString(1);
            }
            return procedencia;
        } catch (Exception e) {
            logger.severe("Error al obtener procedencia, " + e);
            return null;
        }
    }

    public ArrayList<Object> autocompletar() {
        ArrayList<Object> arreglo = new ArrayList<>();
        try (Connection conexion = con.getConnection()) {
            PreparedStatement ps = conexion.prepareStatement("select distinct procedencia from vistaTabla");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                arreglo.add(rs.getString("procedencia"));
            }
            return arreglo;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error, " + e);
            return null;
        }
    }

    public DefaultTableModel llenarTabla() { // se obtienen todos los datos para llenar la tabla de muestras
        String[] fila = new String[11];
        DefaultTableModel modeloTabla = new DefaultTableModel();
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
        try (Connection conexion = con.getConnection()) {
            modeloTabla.setRowCount(0);
            PreparedStatement ps = conexion.prepareStatement("select * from vistatabla");
            ResultSet rs = ps.executeQuery();
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
                modeloTabla.addRow(fila);
            }
            return modeloTabla;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al llenarTabla, " + e);
            return null;
        }
    }

    public DefaultTableModel buscarTabla(String parametro, String valor) {
        if (parametro == "-1" || valor == "-1") {
            return llenarTabla();
        } else {
            DefaultTableModel modeloTabla = new DefaultTableModel();
            String[] fila = new String[13];
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
            try (Connection conexion = con.getConnection()) {
                modeloTabla.setRowCount(0);
                PreparedStatement ps;
                if (parametro == "procedencia" || parametro == "solicitante") {
                    ps = conexion.prepareStatement("select * from vistabusqueda where " + parametro + " like ?");
                    ps.setString(1, "%" + valor + "%");
                } else if (parametro == "fechaAnalisis" || parametro == "fechaMuestreo") {
                    ps = conexion.prepareStatement("select * from vistabusqueda where " + parametro + " between " + valor);
                } else {
                    ps = conexion.prepareStatement("select * from vistabusqueda where " + parametro + " = " + valor);
                }
                ResultSet rs = ps.executeQuery();
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
                    modeloTabla.addRow(fila);
                }
                return modeloTabla;
            } catch (Exception e) {
                logger.severe("Error al llenarTabla, " + e);
                return null;
            }
        }
    }

    public void guardarFechaAnalisis(Resultados r, int id) {
        try (Connection conexion = con.getConnection()) {
            PreparedStatement ps = conexion.prepareStatement("update muestras set fechaAnalisis = ? "
                    + "where idmuestras = ?");
            ps.setDate(1, r.getFechaAnalisis());
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (Exception e) {
            logger.severe("Error al guardar datos, " + e);
        }
    }

    public void guardarFechaAnalisisMBAGUA(Resultados r, int id) {
        try (Connection conexion = con.getConnection()) {
            PreparedStatement ps = conexion.prepareStatement("update mbagua set fechaAnalisis = ? "
                    + "where idmuestras = ?");
            ps.setDate(1, r.getFechaAnalisis());
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (Exception e) {
            logger.severe("Error al guardar datos, " + e);
        }
    }

    public void guardarFechaAnalisis(Map m) {
        try (Connection conexion = con.getConnection()) {
            PreparedStatement ps = conexion.prepareStatement("update muestras set fechaAnalisis = ? "
                    + "where idmuestras = ?");
            ps.setDate(1, (java.sql.Date) m.get("fechaAnalisis"));
            ps.setInt(2, (int) m.get("idmuestras"));
            ps.executeUpdate();
        } catch (Exception e) {
            logger.severe("Error al guardar datos, " + e);
        }
    }

    public void guardarObservaciones(String observaciones, int id) {
        try (Connection conexion = con.getConnection()) {
            PreparedStatement ps = conexion.prepareStatement("update muestras set observaciones = ? where idmuestras = ?");
            ps.setString(1, observaciones);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (Exception e) {
            logger.severe("Error al guardar datos, " + e);
        }
    }

    public void guardarConclusion(String s, int id) {
        try (Connection conexion = con.getConnection()) {
            PreparedStatement ps = conexion.prepareStatement("update muestras set conclusion = ? "
                    + "where idmuestras = ?");
            ps.setString(1, s);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (Exception e) {
            logger.severe("Error al guardar datos, " + e);
        }
    }

    public String recuperarObservaciones(int id) {
        String o = "";
        try (Connection conexion = con.getConnection()) {
            PreparedStatement ps = conexion.prepareStatement("select observaciones from muestras where idmuestras = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                o = rs.getString("observaciones");
            }
            return o;
        } catch (Exception e) {
            logger.severe("Error al recuperarObservaciones, " + e);
            return null;
        }
    }

    public String recuperarConclusion(int id) {
        try (Connection conexion = con.getConnection()) {
            PreparedStatement ps = conexion.prepareStatement("Select conclusion from muestras where idmuestras = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getString("conclusion");
            }
        } catch (Exception e) {
            logger.severe("Error al recuperar conclusion, " + e);
        }
        return null;
    }

    public void guardarRecomendacion(String recomendacion, int id) {
        try (Connection conexion = con.getConnection()) {
            PreparedStatement ps = conexion.prepareStatement("update muestras set recomendacion = ?"
                    + " where idmuestras = ?");
            ps.setString(1, recomendacion);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (Exception e) {
            logger.severe("Error al editar datos, " + e);
        }
    }

    public String recuperarRecomendacion(int id) {
        try (Connection conexion = con.getConnection()) {
            PreparedStatement ps = conexion.prepareStatement("select recomendacion from muestras where idmuestras = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String aux = String.valueOf(rs.getObject(1));
                return aux;
            }
        } catch (Exception e) {
            logger.severe("Error al editar datos, " + e);
            return null;
        }
        return null;
    }

    public void analizado(int id) {
        try (Connection conexion = con.getConnection()) {
            PreparedStatement ps = conexion.prepareStatement("update administracion set analizado = analizado + 1 "
                    + "where idmuestras = ?");
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            logger.severe("Error al actualizar analizado, " + e);
        }
    }

    public void marcarSeleccionados(int id) {
        try (Connection conexion = con.getConnection()) {
            PreparedStatement ps = conexion.prepareStatement("update administracion set seleccionado = 1 "
                    + "where idmuestras = ?");
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            System.err.println("Error, " + e);
        }
    }

    public void entregado(int id) {
        try (Connection conexion = con.getConnection()) {
            PreparedStatement ps = conexion.prepareStatement("update administracion set entregado = 1 where idmuestras = ?");
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            logger.severe("Error al actualizar entregado, " + e);
        }
    }
}
