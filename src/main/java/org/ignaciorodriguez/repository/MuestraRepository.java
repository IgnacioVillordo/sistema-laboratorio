package org.ignaciorodriguez.repository;

import org.ignaciorodriguez.modelo.Muestra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MuestraRepository extends JpaRepository<Muestra, Long> {

//    public boolean agregarMuestra(Muestra m) {
//        Date fv = m.getFechaVencimiento();
//        Date fe = m.getFechaElaboracion();
//        java.sql.Date def = null; //se crea una fecha por defecto (1111-11-11) para cuando no se introduce alguna fecha
//        try (Connection conexion = con.getConnection()) {
//            PreparedStatement ps = conexion.prepareStatement("insert into muestras (idcliente,solicitante,"
//                    + "numeroEstablecimiento,fechaMuestreo,realizadoPor,"
//                    + "loteAlimento,identificacion,tipo,fechaVencimiento,idmuestras,fechaElaboracion, lugarMuestreo) values "
//                    + "(?,?,?,?,?,?,?,?,?,?,?,?)");
//            ps.setInt(1, m.getIdcliente());
//            ps.setString(2, m.getSolicitante());
//            ps.setString(3, m.getNumeroEstablecimiento());
//            ps.setDate(4, m.getFechaMuestreo());
//            ps.setString(5, m.getRealizadoPor());
//            ps.setString(6, m.getLote());
//            if (m.getIdentificacion().isEmpty()) {
//                ps.setObject(7, "-");
//            } else {
//                ps.setString(7, m.getIdentificacion());
//            }
//            ps.setString(8, m.getTipo());
//            if (fv != null) {
//                ps.setDate(9, m.getFechaVencimiento());
//            } else {
//                ps.setDate(9, null);
//            }
//            ps.setInt(10, m.getId());
//            if (fe != null) {
//                ps.setDate(11, m.getFechaElaboracion());
//            } else {
//                ps.setDate(11, null);
//            }
//            ps.setString(12, m.getLugarMuestreo().isBlank() ? "-" : m.getLugarMuestreo());
//            ps.executeUpdate();
//            return true;
//
//        } catch (Exception e) {
//            logger.severe("Error al agregar muestra, " + e);
//            return false;
//        }
//    }
//
//    public boolean editarMuestra(Muestra m) {
//        Date fa = m.getFechaAnalisis();
//        Date fv = m.getFechaVencimiento();
//        Date fe = m.getFechaElaboracion();
//        java.sql.Date def = null;
//        try (Connection conexion = con.getConnection()) {
//            PreparedStatement ps = conexion.prepareStatement("update muestras set solicitante = ?, "
//                    + "numeroEstablecimiento = ?, fechaMuestreo = ?, realizadoPor = ?, "
//                    + "loteAlimento = ?, identificacion = ?, tipo = ?, fechaVencimiento = ?, "
//                    + "fechaElaboracion = ?, lugarMuestreo = ?, idcliente = ? where idmuestras = ?");
//            ps.setString(1, m.getSolicitante());
//            ps.setString(2, m.getNumeroEstablecimiento());
//            ps.setDate(3, m.getFechaMuestreo());
//            ps.setString(4, m.getRealizadoPor());
//            ps.setString(5, m.getLote());
//            ps.setString(6, m.getIdentificacion());
//            ps.setString(7, m.getTipo());
//            if (fv == null) {
//                ps.setDate(8, null);
//            } else {
//                ps.setDate(8, m.getFechaVencimiento());
//            }
//            if (fe == null) {
//                ps.setDate(9, null);
//            } else {
//                ps.setDate(9, m.getFechaElaboracion());
//            }
//            ps.setString(10, m.getLugarMuestreo());
//            ps.setInt(11, m.getIdcliente());
//            ps.setInt(12, m.getId());
//            ps.executeUpdate();
//            return true;
//
//        } catch (Exception e) {
//            logger.severe("Error al editar muestra, " + e);
//            return false;
//        }
//    }
//
//    public Muestra obtenerMuestra(int id) {
//        Muestra m = new Muestra();
//        try (Connection conexion = con.getConnection()) {
//            PreparedStatement ps = conexion.prepareStatement("SELECT `solicitante`, `procedencia`, "
//                    + "`numeroEstablecimiento`, `fechaMuestreo`, `realizadoPor`, "
//                    + "`precioTotal`, `pago`, `factura`, `tipo`, `lote`, "
//                    + "`identificacion`, `fechaElaboracion`, `lugarMuestreo`, "
//                    + "`fechaVencimiento`, aguatipo, idcliente FROM `vistaeditar` where idmuestras = ?");
//            ps.setInt(1, id);
//            ResultSet rs = ps.executeQuery();
//            if (rs.next()) {
//                m.setSolicitante(rs.getString("solicitante"));
//                m.setProcedencia(rs.getString("procedencia"));
//                m.setNumeroEstablecimiento(rs.getString("numeroEstablecimiento"));
//                m.setFechaMuestreo(rs.getDate("fechaMuestreo"));
//                m.setRealizadoPor(rs.getString("realizadoPor"));
//                m.setCosteTotal(rs.getDouble("precioTotal"));
//                m.setPago(rs.getInt("pago"));
//                m.setFactura(rs.getInt("factura"));
//                m.setTipo(rs.getString("tipo"));
//                m.setLote(rs.getString("lote"));
//                m.setIdentificacion(rs.getString("identificacion"));
//                m.setFechaElaboracion(rs.getDate("fechaElaboracion"));
//                m.setLugarMuestreo(rs.getString("lugarMuestreo"));
//                m.setFechaVencimiento(rs.getDate("fechaVencimiento"));
//                m.setTipoAgua(rs.getString("aguatipo"));
//                m.setIdcliente(rs.getInt("idcliente"));
//            }
//            return m;
//        } catch (Exception e) {
//            logger.severe("Error al obtener muestra, " + e);
//            return null;
//        }
//    }
//
//    public int obtenerIdMuestra() {
//        try (Connection conexion = con.getConnection()) {
//            PreparedStatement ps = conexion.prepareStatement("select idmuestras from laboratorio.muestras ORDER BY idmuestras DESC LIMIT 1");
//            ResultSet rs = ps.executeQuery();
//            if (rs.next()) {
//                return rs.getInt("idmuestras");
//            }
//        } catch (Exception e) {
//            logger.severe("Error al obtener id, " + e);
//            return 0;
//        }
//        return 0;
//    }
//
//    public int recuperarIdMuestrasSiguiente() {
//        try (Connection conexion = con.getConnection()) {
//            PreparedStatement ps = conexion.prepareStatement("select idmuestras from muestras order by idmuestras desc limit 1");
//            ResultSet rs = ps.executeQuery();
//            if (rs.next()) {
//                int aux = rs.getInt("idmuestras");
//                return aux + 1;
//            }
//        } catch (Exception e) {
//            logger.severe("Error al recuperar id, " + e);
//            return -1;
//        }
//        return -1;
//    }
//

//

//    public void guardarFechaAnalisis(Resultados r, int id) {
//        try (Connection conexion = con.getConnection()) {
//            PreparedStatement ps = conexion.prepareStatement("update muestras set fechaAnalisis = ? "
//                    + "where idmuestras = ?");
//            ps.setDate(1, r.getFechaAnalisis());
//            ps.setInt(2, id);
//            ps.executeUpdate();
//        } catch (Exception e) {
//            logger.severe("Error al guardar datos, " + e);
//        }
//    }
//

//
//    public void guardarFechaAnalisis(Map m) {
//        try (Connection conexion = con.getConnection()) {
//            PreparedStatement ps = conexion.prepareStatement("update muestras set fechaAnalisis = ? "
//                    + "where idmuestras = ?");
//            ps.setDate(1, (java.sql.Date) m.get("fechaAnalisis"));
//            ps.setInt(2, (int) m.get("idmuestras"));
//            ps.executeUpdate();
//        } catch (Exception e) {
//            logger.severe("Error al guardar datos, " + e);
//        }
//    }
//
//    public void guardarObservaciones(String observaciones, int id) {
//        try (Connection conexion = con.getConnection()) {
//            PreparedStatement ps = conexion.prepareStatement("update muestras set observaciones = ? where idmuestras = ?");
//            ps.setString(1, observaciones);
//            ps.setInt(2, id);
//            ps.executeUpdate();
//        } catch (Exception e) {
//            logger.severe("Error al guardar datos, " + e);
//        }
//    }
//
//    public void guardarConclusion(String s, int id) {
//        try (Connection conexion = con.getConnection()) {
//            PreparedStatement ps = conexion.prepareStatement("update muestras set conclusion = ? "
//                    + "where idmuestras = ?");
//            ps.setString(1, s);
//            ps.setInt(2, id);
//            ps.executeUpdate();
//        } catch (Exception e) {
//            logger.severe("Error al guardar datos, " + e);
//        }
//    }
//
//    public String recuperarObservaciones(int id) {
//        String o = "";
//        try (Connection conexion = con.getConnection()) {
//            PreparedStatement ps = conexion.prepareStatement("select observaciones from muestras where idmuestras = ?");
//            ps.setInt(1, id);
//            ResultSet rs = ps.executeQuery();
//            while (rs.next()) {
//                o = rs.getString("observaciones");
//            }
//            return o;
//        } catch (Exception e) {
//            logger.severe("Error al recuperarObservaciones, " + e);
//            return null;
//        }
//    }
//
//    public String recuperarConclusion(int id) {
//        try (Connection conexion = con.getConnection()) {
//            PreparedStatement ps = conexion.prepareStatement("Select conclusion from muestras where idmuestras = ?");
//            ps.setInt(1, id);
//            ResultSet rs = ps.executeQuery();
//            if (rs.next()) {
//                return rs.getString("conclusion");
//            }
//        } catch (Exception e) {
//            logger.severe("Error al recuperar conclusion, " + e);
//        }
//        return null;
//    }
//
//    public void guardarRecomendacion(String recomendacion, int id) {
//        try (Connection conexion = con.getConnection()) {
//            PreparedStatement ps = conexion.prepareStatement("update muestras set recomendacion = ?"
//                    + " where idmuestras = ?");
//            ps.setString(1, recomendacion);
//            ps.setInt(2, id);
//            ps.executeUpdate();
//        } catch (Exception e) {
//            logger.severe("Error al editar datos, " + e);
//        }
//    }
//
//    public String recuperarRecomendacion(int id) {
//        try (Connection conexion = con.getConnection()) {
//            PreparedStatement ps = conexion.prepareStatement("select recomendacion from muestras where idmuestras = ?");
//            ps.setInt(1, id);
//            ResultSet rs = ps.executeQuery();
//            if (rs.next()) {
//                return String.valueOf(rs.getObject(1));
//            }
//        } catch (Exception e) {
//            logger.severe("Error al editar datos, " + e);
//            return null;
//        }
//        return null;
//    }
//

//

    //    public String obtenerLugarMuestreo(int id) {
//        try (Connection conexion = con.getConnection()) {
//            PreparedStatement ps = conexion.prepareStatement("select lugarMuestreo from muestras " + "where idmuestras = ?");
//            ps.setInt(1, id);
//            ResultSet rs = ps.executeQuery();
//            if (rs.next()) {
//                return rs.getString("lugarMuestreo");
//            }
//        } catch (Exception e) {
//            logger.severe("Error al obtenerLugarMuestreo, " + e);
//            return null;
//        }
//        return null;
//    }
//
//    public Date recuperarFechaAnalisis(int id) {
//        Connection conexion = con.getConnection();
//        try {
//            PreparedStatement ps = conexion.prepareStatement("select fechaAnalisis from muestras where idmuestras = ?");
//            ps.setInt(1, id);
//            ResultSet rs = ps.executeQuery();
//            if (rs.next()) {
//                return rs.getDate("fechaAnalisis");
//            }
//        } catch (Exception e) {
//            logger.severe("Error al recuperarFechaAnalisis, " + e);
//            return null;
//        }
//        return null;
//    }
//
//    public String recuperarNota(int id) {
//        try (Connection conexion = con.getConnection()) {
//            PreparedStatement ps = conexion.prepareStatement("select notas from muestras where idmuestras = ?");
//            ps.setInt(1, id);
//            ResultSet rs = ps.executeQuery();
//            if (rs.next()) {
//                return rs.getString(1);
//            }
//        } catch (Exception e) {
//            logger.severe("Error al generar informe fq, " + e);
//            return "";
//        }
//        return "";
//    }
//
//    public boolean guardarNota(int id, String nota) {
//        Connection conexion = con.getConnection();
//        try {
//            PreparedStatement ps = conexion.prepareStatement("update muestras set notas = ? where idmuestras = ?");
//            ps.setString(1, nota);
//            ps.setInt(2, id);
//            int response = ps.executeUpdate();
//            return response > 0;
//        } catch (Exception e) {
//            logger.severe("Error al guardar nota, " + e);
//            return false;
//        }
//    }
//
    @Query("SELECT m.solicitante, COUNT(m.solicitante) as total " +
            "FROM Muestra m " +
            "WHERE m.idcliente = :idcliente " +
            "GROUP BY m.solicitante " +
            "ORDER BY COUNT(m.solicitante) DESC")
    List<Object[]> findSolicitantesMasComunes(@Param("idcliente") Long idcliente);
//    public Vector<String> recuperarSolicitantes(int id) {
//        Vector<String> solicitantes = new Stack<>();
//        try (Connection conexion = con.getConnection()) {
//            PreparedStatement ps = conexion.prepareStatement("select solicitante, count(solicitante)" + " as mostCommon from muestras where idcliente = ? group by " + "solicitante order by mostCommon desc");
//            ps.setInt(1, id);
//            ResultSet rs = ps.executeQuery();
//            while (rs.next()) {
//                solicitantes.add(rs.getString("solicitante"));
//            }
//            return solicitantes;
//        } catch (Exception e) {
//            logger.severe("Error al recuperar solicitante, " + e);
//            return null;
//        }
//    }
//
//    public boolean recuperarEsconderFechaVencimiento(int id) {
//        boolean aux = false;
//        try (Connection conexion = con.getConnection()) {
//            PreparedStatement ps = conexion.prepareStatement("select ponerFechaVencimiento from muestras where idmuestras = ?");
//            ps.setInt(1, id);
//            ResultSet rs = ps.executeQuery();
//            while (rs.next()) {
//                aux = rs.getBoolean("ponerFechaVencimiento");
//            }
//            return aux;
//        } catch (Exception e) {
//            JOptionPane.showInputDialog("Error al recuperar estado de fecha de vencimiento, " + e);
//        }
//        return false;
//    }
//
//    public String recuperarIdentificacion(int id) {
//        String aux = "";
//        try (Connection conexion = con.getConnection()) {
//            PreparedStatement ps = conexion.prepareStatement("select identificacion from muestras where idmuestras = ?");
//            ps.setInt(1, id);
//            ResultSet rs = ps.executeQuery();
//            while (rs.next()) {
//                aux = rs.getString("identificacion");
//            }
//            return aux;
//        } catch (Exception e) {
//            logger.severe("Error al recuperar identificacion, " + e);
//        }
//        return null;
//    }
//

    public List<Muestra> findAllByOrderByIdmuestrasDesc();
//    public Map<String, String> recuperarIdentificaciones() {
//        String aux = "";
//        Map<String, String> mapa = new HashMap<>();
//        String idmuestras;
//        try (Connection conexion = con.getConnection()) {
//            PreparedStatement ps = conexion.prepareStatement("select identificacion,idmuestras from muestras order by idmuestras desc");
//            ResultSet rs = ps.executeQuery();
//            while (rs.next()) {
//                aux = rs.getString("identificacion");
//                idmuestras = String.valueOf(rs.getInt("idmuestras"));
//                mapa.put(idmuestras, aux);
//            }
//            return mapa;
//        } catch (Exception e) {
//            JOptionPane.showInputDialog("Error al recuperar identificaciones, " + e);
//        }
//        return null;
//    }
//
//
//    public boolean consultarFechaIngresada(int id) {
//        try (Connection conexion = con.getConnection()) {
//            PreparedStatement ps = conexion.prepareStatement("select fechaElaboracion, fechaVencimiento from muestras where idmuestras = ?");
//            ps.setInt(1, id);
//            ResultSet rs = ps.executeQuery();
//            while (rs.next()) {
//                if (rs.getDate("fechaElaboracion") != null && rs.getDate("fechaVencimiento") != null) {
//                    return true;
//                }
//            }
//        } catch (Exception e) {
//            logger.severe("Error al consultar usuario, " + e);
//            return false;
//        }
//        return false;
//    }
//
//    public void agregarTipoAgua(int id, String tipo) {
//        try (Connection conexion = con.getConnection()) {
//            PreparedStatement ps = conexion.prepareStatement("update muestras set aguaTipo = ? where idmuestras = ?");
//            ps.setString(1, tipo);
//            ps.setInt(2, id);
//            ps.executeUpdate();
//        } catch (Exception e) {
//            logger.severe("Error al agregar datos, " + e);
//        }
//    }
//
//    public String recuperarTipoAgua(int id) {
//        try (Connection conexion = con.getConnection()) {
//            PreparedStatement ps = conexion.prepareStatement("select aguaTipo from muestras where idmuestras = ?");
//            ps.setInt(1, id);
//            ResultSet rs = ps.executeQuery();
//            if (rs.next()) {
//                return rs.getString("aguaTipo");
//            }
//        } catch (Exception e) {
//            logger.severe("Error al recuperar datos, " + e);
//        }
//        return null;
//    }
//


//
//

//
//    public void esconderFechaVencimiento(int id, boolean poner) {
//        try (Connection conexion = con.getConnection()) {
//            PreparedStatement ps = conexion.prepareStatement("update muestras set ponerFechaVencimiento = ? where idmuestras = ?");
//            int aux;
//            if (poner) {
//                aux = 1;
//            } else {
//                aux = 0;
//            }
//            ps.setInt(1, aux);
//            ps.setInt(2, id);
//            ps.executeUpdate();
//        } catch (Exception e) {
//            logger.severe("Error al esconder fecha de vencimiento, " + e);
//        }
//    }
//
//    public int recuperarIdCliente(int id) {
//        try (Connection conexion = con.getConnection()) {
//            PreparedStatement ps = conexion.prepareStatement("select idcliente from muestras where idmuestras = ?");
//            ps.setInt(1, id);
//            ResultSet rs = ps.executeQuery();
//            if (rs.next()) {
//                return rs.getInt(1);
//            }
//        } catch (Exception e) {
//            logger.severe("Error al recuperar id del cliente, " + e);
//        }
//        return -1;
//    }
}
