package org.ignaciorodriguez.repository;

import org.ignaciorodriguez.modelo.Manual;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ManualRepository extends JpaRepository<Manual, Long> {
    @Query("SELECT m.titulo FROM Manual m WHERE m.idmuestras = :idmuestras")
    Optional<String> findTituloByIdmuestras(@Param("idmuestras") Long idmuestras);

    @Query("SELECT m.mostrar FROM Manual m WHERE m.idmuestras = :idmuestras")
    Optional<String> findMostrarByIdmuestras(@Param("idmuestras") Long idmuestras);
    //    public boolean guardarResultadoManual(Map m) {
//
//        try (Connection conexion = con.getConnection()) {
//            // Construcción dinámica de la parte VALUES (?,?,?...)
//            StringBuilder values = new StringBuilder("?"); // El primero es idmuestras
//            // 34 campos * 4 categorías + titulo + mostrar
//            values.repeat(",?", (34 * 4) + 2);
//
//            PreparedStatement ps = conexion.prepareStatement("INSERT INTO `laboratorio`.`manual` (" + "`idmuestras`," + "`determinacion1`,`determinacion2`,`determinacion3`,`determinacion4`,`determinacion5`,`determinacion6`,`determinacion7`,`determinacion8`,`determinacion9`,`determinacion10`," + "`determinacion11`,`determinacion12`,`determinacion13`,`determinacion14`,`determinacion15`,`determinacion16`,`determinacion17`,`determinacion18`,`determinacion19`,`determinacion20`," + "`determinacion21`,`determinacion22`,`determinacion23`,`determinacion24`,`determinacion25`,`determinacion26`,`determinacion27`,`determinacion28`,`determinacion29`,`determinacion30`," + "`determinacion31`,`determinacion32`,`determinacion33`,`determinacion34`," + "`recuentoObtenido1`,`recuentoObtenido2`,`recuentoObtenido3`,`recuentoObtenido4`,`recuentoObtenido5`,`recuentoObtenido6`,`recuentoObtenido7`,`recuentoObtenido8`,`recuentoObtenido9`,`recuentoObtenido10`," + "`recuentoObtenido11`,`recuentoObtenido12`,`recuentoObtenido13`,`recuentoObtenido14`,`recuentoObtenido15`,`recuentoObtenido16`,`recuentoObtenido17`,`recuentoObtenido18`,`recuentoObtenido19`,`recuentoObtenido20`," + "`recuentoObtenido21`,`recuentoObtenido22`,`recuentoObtenido23`,`recuentoObtenido24`,`recuentoObtenido25`,`recuentoObtenido26`,`recuentoObtenido27`,`recuentoObtenido28`,`recuentoObtenido29`,`recuentoObtenido30`," + "`recuentoObtenido31`,`recuentoObtenido32`,`recuentoObtenido33`,`recuentoObtenido34`," + "`recuentoNormal1`,`recuentoNormal2`,`recuentoNormal3`,`recuentoNormal4`,`recuentoNormal5`,`recuentoNormal6`,`recuentoNormal7`,`recuentoNormal8`,`recuentoNormal9`,`recuentoNormal10`," + "`recuentoNormal11`,`recuentoNormal12`,`recuentoNormal13`,`recuentoNormal14`,`recuentoNormal15`,`recuentoNormal16`,`recuentoNormal17`,`recuentoNormal18`,`recuentoNormal19`,`recuentoNormal20`," + "`recuentoNormal21`,`recuentoNormal22`,`recuentoNormal23`,`recuentoNormal24`,`recuentoNormal25`,`recuentoNormal26`,`recuentoNormal27`,`recuentoNormal28`,`recuentoNormal29`,`recuentoNormal30`," + "`recuentoNormal31`,`recuentoNormal32`,`recuentoNormal33`,`recuentoNormal34`," + "`metodo1`,`metodo2`,`metodo3`,`metodo4`,`metodo5`,`metodo6`,`metodo7`,`metodo8`,`metodo9`,`metodo10`," + "`metodo11`,`metodo12`,`metodo13`,`metodo14`,`metodo15`,`metodo16`,`metodo17`,`metodo18`,`metodo19`,`metodo20`," + "`metodo21`,`metodo22`,`metodo23`,`metodo24`,`metodo25`,`metodo26`,`metodo27`,`metodo28`,`metodo29`,`metodo30`," + "`metodo31`,`metodo32`,`metodo33`,`metodo34`, `titulo`, `mostrar`" + ") VALUES (" + values.toString() + ")");
//
//            int index = 1;
//            ps.setInt(index++, (int) m.get("idmuestras"));
//            for (int i = 1; i <= 34; i++) {
//                ps.setString(index++, String.valueOf(m.get("determinacion" + i)));
//            }
//            for (int i = 1; i <= 34; i++) {
//                ps.setString(index++, String.valueOf(m.get("recuentoObtenido" + i)));
//            }
//            for (int i = 1; i <= 34; i++) {
//                ps.setString(index++, String.valueOf(m.get("recuentoNormal" + i)));
//            }
//            for (int i = 1; i <= 34; i++) {
//                ps.setString(index++, String.valueOf(m.get("metodo" + i)));
//            }
//            ps.setString(index++, String.valueOf(m.get("titulo")));
//            ps.setString(index++, String.valueOf(m.get("mostrar")));
//            ps.executeUpdate();
//            return true;
//        } catch (Exception e) {
//            logger.severe("Error al guardar datos, " + e);
//            return false;
//        }
//    }
    //    public boolean checkearResultadoManual(int id) {
//
//        try (Connection conexion = con.getConnection()) {
//            PreparedStatement ps = conexion.prepareStatement("select * from manual where idmuestras = ?");
//            ps.setInt(1, id);
//            ResultSet rs = ps.executeQuery();
//            if (rs.next()) {
//                return true;
//            }
//        } catch (Exception e) {
//            logger.severe("Error al guardar datos, " + e);
//            return false;
//        }
//        return false;
//    }
//
//    public Map<String, String> recuperarResultadoManual(int id) {
//
//        Map<String, String> map = new HashMap<>();
//        try (Connection conexion = con.getConnection()) {
//            PreparedStatement ps = conexion.prepareStatement("select * from manual where idmuestras = ?");
//            ps.setInt(1, id);
//            ResultSet rs = ps.executeQuery();
//            while (rs.next()) {
//                for (int i = 3; i < rs.getMetaData().getColumnCount(); i++) {
//                    map.put(rs.getMetaData().getColumnName(i), rs.getString(i));
//                }
//            }
//            return map;
//        } catch (Exception e) {
//            logger.severe("Error al guardar datos, " + e);
//            return null;
//        }
//    }
//
//    public boolean editarResultadoManual(Map m) {
//        try (Connection conexion = con.getConnection()) {
//            PreparedStatement ps = conexion.prepareStatement("UPDATE `laboratorio`.`manual` SET " + "`determinacion1` = ?,`determinacion2` = ?,`determinacion3` = ?,`determinacion4` = ?,`determinacion5` = ?,`determinacion6` = ?,`determinacion7` = ?,`determinacion8` = ?,`determinacion9` = ?,`determinacion10` = ?," + "`determinacion11` = ?,`determinacion12` = ?,`determinacion13` = ?,`determinacion14` = ?,`determinacion15` = ?,`determinacion16` = ?,`determinacion17` = ?,`determinacion18` = ?,`determinacion19` = ?,`determinacion20` = ?," + "`determinacion21` = ?,`determinacion22` = ?,`determinacion23` = ?,`determinacion24` = ?,`determinacion25` = ?,`determinacion26` = ?,`determinacion27` = ?,`determinacion28` = ?,`determinacion29` = ?,`determinacion30` = ?," + "`determinacion31` = ?,`determinacion32` = ?,`determinacion33` = ?,`determinacion34` = ?," + "`recuentoObtenido1` = ?,`recuentoObtenido2` = ?,`recuentoObtenido3` = ?,`recuentoObtenido4` = ?,`recuentoObtenido5` = ?,`recuentoObtenido6` = ?,`recuentoObtenido7` = ?,`recuentoObtenido8` = ?,`recuentoObtenido9` = ?,`recuentoObtenido10` = ?," + "`recuentoObtenido11` = ?,`recuentoObtenido12` = ?,`recuentoObtenido13` = ?,`recuentoObtenido14` = ?,`recuentoObtenido15` = ?,`recuentoObtenido16` = ?,`recuentoObtenido17` = ?,`recuentoObtenido18` = ?,`recuentoObtenido19` = ?,`recuentoObtenido20` = ?," + "`recuentoObtenido21` = ?,`recuentoObtenido22` = ?,`recuentoObtenido23` = ?,`recuentoObtenido24` = ?,`recuentoObtenido25` = ?,`recuentoObtenido26` = ?,`recuentoObtenido27` = ?,`recuentoObtenido28` = ?,`recuentoObtenido29` = ?,`recuentoObtenido30` = ?," + "`recuentoObtenido31` = ?,`recuentoObtenido32` = ?,`recuentoObtenido33` = ?,`recuentoObtenido34` = ?," + "`recuentoNormal1` = ?,`recuentoNormal2` = ?,`recuentoNormal3` = ?,`recuentoNormal4` = ?,`recuentoNormal5` = ?,`recuentoNormal6` = ?,`recuentoNormal7` = ?,`recuentoNormal8` = ?,`recuentoNormal9` = ?,`recuentoNormal10` = ?," + "`recuentoNormal11` = ?,`recuentoNormal12` = ?,`recuentoNormal13` = ?,`recuentoNormal14` = ?,`recuentoNormal15` = ?,`recuentoNormal16` = ?,`recuentoNormal17` = ?,`recuentoNormal18` = ?,`recuentoNormal19` = ?,`recuentoNormal20` = ?," + "`recuentoNormal21` = ?,`recuentoNormal22` = ?,`recuentoNormal23` = ?,`recuentoNormal24` = ?,`recuentoNormal25` = ?,`recuentoNormal26` = ?,`recuentoNormal27` = ?,`recuentoNormal28` = ?,`recuentoNormal29` = ?,`recuentoNormal30` = ?," + "`recuentoNormal31` = ?,`recuentoNormal32` = ?,`recuentoNormal33` = ?,`recuentoNormal34` = ?," + "`metodo1` = ?,`metodo2` = ?,`metodo3` = ?,`metodo4` = ?,`metodo5` = ?,`metodo6` = ?,`metodo7` = ?,`metodo8` = ?,`metodo9` = ?,`metodo10` = ?," + "`metodo11` = ?,`metodo12` = ?,`metodo13` = ?,`metodo14` = ?,`metodo15` = ?,`metodo16` = ?,`metodo17` = ?,`metodo18` = ?,`metodo19` = ?,`metodo20` = ?," + "`metodo21` = ?,`metodo22` = ?,`metodo23` = ?,`metodo24` = ?,`metodo25` = ?,`metodo26` = ?,`metodo27` = ?,`metodo28` = ?,`metodo29` = ?,`metodo30` = ?," + "`metodo31` = ?,`metodo32` = ?,`metodo33` = ?,`metodo34` = ?, titulo = ?, mostrar = ? " + "WHERE `idmuestras` = ?;");
//            int index = 1;
//            for (int i = 1; i <= 34; i++) {
//                ps.setString(index++, String.valueOf(m.get("determinacion" + i)));
//            }
//            for (int i = 1; i <= 34; i++) {
//                ps.setString(index++, String.valueOf(m.get("recuentoObtenido" + i)));
//            }
//            for (int i = 1; i <= 34; i++) {
//                ps.setString(index++, String.valueOf(m.get("recuentoNormal" + i)));
//            }
//            for (int i = 1; i <= 34; i++) {
//                ps.setString(index++, String.valueOf(m.get("metodo" + i)));
//            }
//            ps.setString(index++, String.valueOf(m.get("titulo")));
//            ps.setString(index++, String.valueOf(m.get("mostrar")));
//            ps.setInt(index, (int) m.get("idmuestras"));
//
//            ps.executeUpdate();
//            return true;
//        } catch (Exception e) {
//            logger.severe("Error al guardar datos, " + e);
//            return false;
//        }
//    }
//
//
    //    public String recuperarTituloManual(int id) {
//
//        String titulo = "";
//        try (Connection conexion = con.getConnection()) {
//            PreparedStatement ps = conexion.prepareStatement("select titulo from manual where idmuestras = ?");
//            ps.setInt(1, id);
//            ResultSet rs = ps.executeQuery();
//            while (rs.next()) {
//                titulo = rs.getString("titulo");
//            }
//            return titulo;
//        } catch (Exception e) {
//            JOptionPane.showInputDialog("Error al recuperar titulo " + e);
//            return null;
//        }
//    }
    //    public void guardarMostrar(int id, String mostrar) {
//
//
//        try (Connection conexion = con.getConnection()) {
//            PreparedStatement ps = conexion.prepareStatement("update manual set mostrar = ? where idmuestras = ?");
//            ps.setString(1, mostrar);
//            ps.setInt(2, id);
//            ps.executeUpdate();
//        } catch (Exception e) {
//            logger.severe("Error al guardar datos, " + e);
//        }
//    }
}
