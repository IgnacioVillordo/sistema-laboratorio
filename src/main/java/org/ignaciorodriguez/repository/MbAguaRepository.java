package org.ignaciorodriguez.repository;

import org.ignaciorodriguez.modelo.MbAgua;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MbAguaRepository extends JpaRepository<MbAgua, Long> {
    //    public boolean guardarResultadoMBAgua(Resultados r) {
//
//        try (Connection conexion = con.getConnection()) {
//            PreparedStatement ps = conexion.prepareStatement("insert into MBAgua (idmuestras, " + "germenes, coliformesTotales, coliformesFecales, escherichia, pseudomona," + " ph, cloroLibre, caracteresOrganolepticos, mohos, shigella) values (?,?,?,?,?,?,?,?,?,?,?)");
//            ps.setInt(1, r.getIdmuestras());
//            ps.setString(2, r.getGermenes());
//            ps.setString(3, r.getColiformesTotales());
//            ps.setString(4, r.getColiformesFecales());
//            ps.setString(5, r.getEscherichia());
//            ps.setString(6, r.getPseudomona());
//            ps.setDouble(7, r.getPh());
//            ps.setDouble(8, r.getClorototal());
//            ps.setString(9, r.getCaracteresOrgasnolepticos());
//            ps.setString(10, r.getMohos());
//            ps.setString(11, r.getShigella());
//            ps.executeUpdate();
//            return true;
//        } catch (Exception e) {
//            logger.severe("Error al guardar datos, " + e);
//            return false;
//        }
//    }
    //    public boolean guardarResultadoMBAguaDeRecreacion(Resultados r, int vencimiento) {
//
//        try (Connection conexion = con.getConnection()) {
//            PreparedStatement ps = conexion.prepareStatement("insert into MBAgua (idmuestras, " + "germenes, coliformesTotales, coliformesFecales, escherichia, " + "pseudomona, ph, cloroTotal, caracteresOrganolepticos, staphilococos, " + "streptococos, cloroLibre, vencimiento, shigella) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
//            ps.setInt(1, r.getIdmuestras());
//            ps.setString(2, r.getGermenes());
//            ps.setString(3, r.getColiformesTotales());
//            ps.setString(4, r.getColiformesFecales());
//            ps.setString(5, r.getEscherichia());
//            ps.setString(6, r.getPseudomona());
//            ps.setDouble(7, r.getPh());
//            ps.setDouble(8, r.getClorototal());
//            ps.setString(9, r.getCaracteresOrgasnolepticos());
//            ps.setString(10, r.getStaphilococos());
//            ps.setString(11, r.getStreptococos());
//            ps.setDouble(12, r.getCloroLibre());
//            ps.setInt(13, vencimiento);
//            ps.setString(14, r.getShigella());
//            ps.executeUpdate();
//            return true;
//        } catch (Exception e) {
//            logger.severe("Error al guardar datos, " + e);
//            return false;
//        }
//    }
//
//    public double[] recuperarPhYCloro(int id) {
//
//        double[] aux = {-1, -1, -1};
//        try (Connection conexion = con.getConnection()) {
//            PreparedStatement ps = conexion.prepareStatement("select cloroLibre, cloroTotal,ph from mbagua where idmuestras = ?");
//            ps.setInt(1, id);
//            ResultSet rs = ps.executeQuery();
//            while (rs.next()) {
//                for (int i = 0; i < aux.length; i++) {
//                    if (rs.getObject(i + 1) != null) {
//                        aux[i] = rs.getDouble(i + 1);
//                    }
//                }
//            }
//            return aux;
//        } catch (Exception e) {
//            logger.severe("Error al guardar datos, " + e);
//            return null;
//        }
//    }
//
//    public boolean checkearResultadoMBAgua(int id) {
//        try (Connection conexion = con.getConnection()) {
//            PreparedStatement ps = conexion.prepareStatement("select * from mbagua where idmuestras = ?");
//            ps.setInt(1, id);
//            ResultSet rs = ps.executeQuery();
//            if (rs.next()) {
//                return true;
//            }
//        } catch (Exception e) {
//            logger.severe("Error al checkearResultadosMBAgua, " + e);
//        }
//        return false;
//    }
//
//    public Map<String, String> recuperarResultadosMBAgua(int id) {
//
//        Map<String, String> valores = new HashMap<>();
//        boolean nulo = true;
//        try (Connection conexion = con.getConnection()) {
//            PreparedStatement ps = conexion.prepareStatement("select germenes, coliformesTotales, coliformesFecales, escherichia," + " pseudomona, caracteresOrganolepticos, fechaAnalisis, mohos, mohosLimite, shigella from " + "mbagua where idmuestras = ?");
//            ps.setInt(1, id);
//            ResultSet rs = ps.executeQuery();
//            if (rs.next()) {
//                for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
//                    if (rs.getObject(i + 1) != null) {
//                        nulo = false;
//                        valores.put(rs.getMetaData().getColumnName(i + 1), String.valueOf(rs.getObject(i + 1)));
//                    }
//                }
//                return nulo ? null : valores;
//            }
//        } catch (Exception e) {
//            logger.severe("Error al recuperar datos, " + e);
//        }
//        return null;
//    }
//
//    public Map<String, String> recuperarResultadosMBAguaCOFES(int id) {
//
//        Map<String, String> valores = new HashMap<>();
//        try (Connection conexion = con.getConnection()) {
//            PreparedStatement ps = conexion.prepareStatement("select germenes, coliformesTotales, coliformesFecales, escherichia," + " pseudomona, caracteresOrganolepticos, fechaAnalisis, shigella from " + "mbagua where idmuestras = ?");
//            ps.setInt(1, id);
//            ResultSet rs = ps.executeQuery();
//            if (rs.next()) {
//                for (int i = 0; i < ps.getMetaData().getColumnCount(); i++) {
//                    if (rs.getObject(i + 1) == null) {
//                        return null;
//                    } else {
//                        valores.put(rs.getMetaData().getColumnName(i + 1), String.valueOf(rs.getObject(i + 1)));
//                    }
//                }
//                return valores;
//            }
//        } catch (Exception e) {
//            logger.severe("Error al recuperar datos, " + e);
//        }
//        return null;
//    }
//
//    public Map<String, String> recuperarResultadosMBAguaDeRecreacion(int id) {
//
//        Map<String, String> valores = new HashMap<>();
//        try (Connection conexion = con.getConnection()) {
//            PreparedStatement ps = conexion.prepareStatement("select germenes, coliformesTotales, " + "coliformesFecales, escherichia, pseudomona, staphilococos, " + "streptococos, caracteresOrganolepticos, fechaanalisis, shigella from " + "mbagua where idmuestras = ?");
//            ps.setInt(1, id);
//            ResultSet rs = ps.executeQuery();
//            if (rs.next()) {
//                for (int i = 0; i < ps.getMetaData().getColumnCount(); i++) {
//                    valores.put(ps.getMetaData().getColumnName(i + 1), String.valueOf(rs.getObject(i + 1)));
//                }
//                return valores;
//            }
//        } catch (Exception e) {
//            logger.severe("Error al recuperar datos, " + e);
//        }
//        return null;
//    }
    //    public boolean editarMBAgua(Resultados r) {
//
//        try (Connection conexion = con.getConnection()) {
//            PreparedStatement ps = conexion.prepareStatement("UPDATE `laboratorio`.`mbagua` SET `germenes` = ? " + ", `coliformesTotales` = ? , `coliformesFecales` = ?, `escherichia` = ? , `pseudomona` = ?, " + "ph = ?, cloroLibre = ?, caracteresOrganolepticos = ?, mohos = ?, fechaAnalisis = ?, shigella = ?" + " WHERE `idmuestras` = ? ;");
//            ps.setString(1, r.getGermenes());
//            ps.setString(2, r.getColiformesTotales());
//            ps.setString(3, r.getColiformesFecales());
//            ps.setString(4, r.getEscherichia());
//            ps.setString(5, r.getPseudomona());
//            ps.setDouble(6, r.getPh());
//            ps.setDouble(7, r.getClorototal());
//            ps.setString(8, r.getCaracteresOrgasnolepticos());
//            ps.setString(9, r.getMohos());
//            ps.setDate(10, r.getFechaAnalisis());
//            ps.setString(11, r.getShigella());
//            ps.setInt(12, r.getIdmuestras());
//            ps.executeUpdate();
//            return true;
//        } catch (Exception e) {
//            logger.severe("Error al editar datos, " + e);
//            return false;
//        }
//    }
    //    public boolean guardarPhYCloro(Map m) {
//        try (Connection conexion = con.getConnection()) {
//            PreparedStatement ps = conexion.prepareStatement("insert into mbagua (ph, cloroLibre, cloroTotal,idmuestras) values (?,?,?,?)");
//            ps.setString(1, String.valueOf(m.get("ph")));
//            ps.setString(2, String.valueOf(m.get("libre")));
//            ps.setString(3, String.valueOf(m.get("total")));
//            ps.setInt(4, Integer.parseInt(String.valueOf(m.get("id"))));
//            ps.executeUpdate();
//            return true;
//        } catch (Exception e) {
//            logger.severe("Error al guardar datos, " + e);
//            return false;
//        }
//    }
//
//    public boolean editarPhYCloro(Map m) {
//        try (Connection conexion = con.getConnection()) {
//            PreparedStatement ps = conexion.prepareStatement("update mbagua set ph = ?, cloroLibre = ?, cloroTotal = ? where idmuestras = ?");
//            ps.setString(1, String.valueOf(m.get("ph")));
//            ps.setString(2, String.valueOf(m.get("libre")));
//            ps.setString(3, String.valueOf(m.get("total")));
//            ps.setInt(4, Integer.parseInt(String.valueOf(m.get("id"))));
//            ps.executeUpdate();
//            return true;
//        } catch (Exception e) {
//            logger.severe("Error al guardar datos, " + e);
//            return false;
//        }
//    }
    //    public boolean editarMBAguaDeRecreacion(Resultados r, int vencimiento) {
//
//        try (Connection conexion = con.getConnection()) {
//            PreparedStatement ps = conexion.prepareStatement("UPDATE `laboratorio`.`mbagua` SET " + "`germenes` = ?, `coliformesTotales` = ? , `coliformesFecales` = ?, " + "`escherichia` = ? , `pseudomona` = ?, ph = ?, cloroTotal = ?, " + "caracteresOrganolepticos = ?, staphilococos = ?, streptococos = ?, " + "cloroLibre = ?, vencimiento = ?, shigella = ? WHERE `idmuestras` = ? ;");
//            ps.setString(1, r.getGermenes());
//            ps.setString(2, r.getColiformesTotales());
//            ps.setString(3, r.getColiformesFecales());
//            ps.setString(4, r.getEscherichia());
//            ps.setString(5, r.getPseudomona());
//            ps.setDouble(6, r.getPh());
//            ps.setDouble(7, r.getClorototal());
//            ps.setString(8, r.getCaracteresOrgasnolepticos());
//            ps.setString(9, r.getStaphilococos());
//            ps.setString(10, r.getStreptococos());
//            ps.setDouble(11, r.getCloroLibre());
//            ps.setInt(12, vencimiento);
//            ps.setString(13, r.getShigella());
//            ps.setInt(14, r.getIdmuestras());
//            ps.executeUpdate();
//            return true;
//        } catch (Exception e) {
//            logger.severe("Error al editar datos, " + e);
//            return false;
//        }
//    }
    //    public void guardarLimiteMohos(int id, boolean selected) {
//
//
//        try (Connection conexion = con.getConnection()) {
//            PreparedStatement ps = conexion.prepareStatement("update mbagua set mohosLimite = ? where idmuestras = ?");
//            ps.setInt(1, selected ? 1 : 0);
//            ps.setInt(2, id);
//            ps.executeUpdate();
//        } catch (Exception e) {
//            logger.severe("Error al guardar datos, " + e);
//        }
//    }
//    public void guardarFechaAnalisisMBAGUA(Resultados r, int id) {
//        try (Connection conexion = con.getConnection()) {
//            PreparedStatement ps = conexion.prepareStatement("update mbagua set fechaAnalisis = ? "
//                    + "where idmuestras = ?");
//            ps.setDate(1, r.getFechaAnalisis());
//            ps.setInt(2, id);
//            ps.executeUpdate();
//        } catch (Exception e) {
//            logger.severe("Error al guardar datos, " + e);
//        }
//    }
}
