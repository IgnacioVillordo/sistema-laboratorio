package org.ignaciorodriguez.repository;

import org.ignaciorodriguez.modelo.Conexion;
import org.ignaciorodriguez.modelo.Consultas;
import org.ignaciorodriguez.modelo.Determinacion;
import org.ignaciorodriguez.modelo.Resultados;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class ResultadoRepository {

    Conexion con = new Conexion();
    private static final Logger logger = Logger.getLogger(ResultadoRepository.class.getName());

    public boolean guardarResultadoMBAgua(Resultados r) {
        
        try (Connection conexion = con.getConnection()) {
            PreparedStatement ps = conexion.prepareStatement("insert into MBAgua (idmuestras, " + "germenes, coliformesTotales, coliformesFecales, escherichia, pseudomona," + " ph, cloroLibre, caracteresOrganolepticos, mohos, shigella) values (?,?,?,?,?,?,?,?,?,?,?)");
            ps.setInt(1, r.getIdmuestras());
            ps.setString(2, r.getGermenes());
            ps.setString(3, r.getColiformesTotales());
            ps.setString(4, r.getColiformesFecales());
            ps.setString(5, r.getEscherichia());
            ps.setString(6, r.getPseudomona());
            ps.setDouble(7, r.getPh());
            ps.setDouble(8, r.getClorototal());
            ps.setString(9, r.getCaracteresOrgasnolepticos());
            ps.setString(10, r.getMohos());
            ps.setString(11, r.getShigella());
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            logger.severe("Error al guardar datos, " + e);
            return false;
        } finally {
            try (Connection conexion = con.getConnection()) {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
    }

    public boolean guardarResultadoManual(Map m) {
        
        try (Connection conexion = con.getConnection()) {
            // Construcción dinámica de la parte VALUES (?,?,?...)
            StringBuilder values = new StringBuilder("?"); // El primero es idmuestras
            for (int i = 0; i < (34 * 4) + 2; i++) { // 34 campos * 4 categorías + titulo + mostrar
                values.append(",?");
            }

            PreparedStatement ps = conexion.prepareStatement("INSERT INTO `laboratorio`.`manual` (" + "`idmuestras`," + "`determinacion1`,`determinacion2`,`determinacion3`,`determinacion4`,`determinacion5`,`determinacion6`,`determinacion7`,`determinacion8`,`determinacion9`,`determinacion10`," + "`determinacion11`,`determinacion12`,`determinacion13`,`determinacion14`,`determinacion15`,`determinacion16`,`determinacion17`,`determinacion18`,`determinacion19`,`determinacion20`," + "`determinacion21`,`determinacion22`,`determinacion23`,`determinacion24`,`determinacion25`,`determinacion26`,`determinacion27`,`determinacion28`,`determinacion29`,`determinacion30`," + "`determinacion31`,`determinacion32`,`determinacion33`,`determinacion34`," + "`recuentoObtenido1`,`recuentoObtenido2`,`recuentoObtenido3`,`recuentoObtenido4`,`recuentoObtenido5`,`recuentoObtenido6`,`recuentoObtenido7`,`recuentoObtenido8`,`recuentoObtenido9`,`recuentoObtenido10`," + "`recuentoObtenido11`,`recuentoObtenido12`,`recuentoObtenido13`,`recuentoObtenido14`,`recuentoObtenido15`,`recuentoObtenido16`,`recuentoObtenido17`,`recuentoObtenido18`,`recuentoObtenido19`,`recuentoObtenido20`," + "`recuentoObtenido21`,`recuentoObtenido22`,`recuentoObtenido23`,`recuentoObtenido24`,`recuentoObtenido25`,`recuentoObtenido26`,`recuentoObtenido27`,`recuentoObtenido28`,`recuentoObtenido29`,`recuentoObtenido30`," + "`recuentoObtenido31`,`recuentoObtenido32`,`recuentoObtenido33`,`recuentoObtenido34`," + "`recuentoNormal1`,`recuentoNormal2`,`recuentoNormal3`,`recuentoNormal4`,`recuentoNormal5`,`recuentoNormal6`,`recuentoNormal7`,`recuentoNormal8`,`recuentoNormal9`,`recuentoNormal10`," + "`recuentoNormal11`,`recuentoNormal12`,`recuentoNormal13`,`recuentoNormal14`,`recuentoNormal15`,`recuentoNormal16`,`recuentoNormal17`,`recuentoNormal18`,`recuentoNormal19`,`recuentoNormal20`," + "`recuentoNormal21`,`recuentoNormal22`,`recuentoNormal23`,`recuentoNormal24`,`recuentoNormal25`,`recuentoNormal26`,`recuentoNormal27`,`recuentoNormal28`,`recuentoNormal29`,`recuentoNormal30`," + "`recuentoNormal31`,`recuentoNormal32`,`recuentoNormal33`,`recuentoNormal34`," + "`metodo1`,`metodo2`,`metodo3`,`metodo4`,`metodo5`,`metodo6`,`metodo7`,`metodo8`,`metodo9`,`metodo10`," + "`metodo11`,`metodo12`,`metodo13`,`metodo14`,`metodo15`,`metodo16`,`metodo17`,`metodo18`,`metodo19`,`metodo20`," + "`metodo21`,`metodo22`,`metodo23`,`metodo24`,`metodo25`,`metodo26`,`metodo27`,`metodo28`,`metodo29`,`metodo30`," + "`metodo31`,`metodo32`,`metodo33`,`metodo34`, `titulo`, `mostrar`" + ") VALUES (" + values.toString() + ")");

            int index = 1;
            ps.setInt(index++, (int) m.get("idmuestras"));
            for (int i = 1; i <= 34; i++) {
                ps.setString(index++, String.valueOf(m.get("determinacion" + i)));
            }
            for (int i = 1; i <= 34; i++) {
                ps.setString(index++, String.valueOf(m.get("recuentoObtenido" + i)));
            }
            for (int i = 1; i <= 34; i++) {
                ps.setString(index++, String.valueOf(m.get("recuentoNormal" + i)));
            }
            for (int i = 1; i <= 34; i++) {
                ps.setString(index++, String.valueOf(m.get("metodo" + i)));
            }
            ps.setString(index++, String.valueOf(m.get("titulo")));
            ps.setString(index++, String.valueOf(m.get("mostrar")));
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            logger.severe("Error al guardar datos, " + e);
            return false;
        } finally {
            try (Connection conexion = con.getConnection()) {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
    }

    public boolean checkearResultadoManual(int id) {
        
        try (Connection conexion = con.getConnection()) {
            PreparedStatement ps = conexion.prepareStatement("select * from manual where idmuestras = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return true;
            }
        } catch (Exception e) {
            logger.severe("Error al guardar datos, " + e);
            return false;
        } finally {
            try (Connection conexion = con.getConnection()) {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
        return false;
    }

    public Map<String, String> recuperarResultadoManual(int id) {
        
        Map<String, String> map = new HashMap();
        try (Connection conexion = con.getConnection()) {
            PreparedStatement ps = conexion.prepareStatement("select * from manual where idmuestras = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                for (int i = 3; i < rs.getMetaData().getColumnCount(); i++) {
                    map.put(rs.getMetaData().getColumnName(i), rs.getString(i));
                }
            }
            return map;
        } catch (Exception e) {
            logger.severe("Error al guardar datos, " + e);
            return null;
        } finally {
            try (Connection conexion = con.getConnection()) {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
    }

    public boolean editarResultadoManual(Map m) {
        
        try (Connection conexion = con.getConnection()) {
            PreparedStatement ps = conexion.prepareStatement("UPDATE `laboratorio`.`manual` SET " + "`determinacion1` = ?,`determinacion2` = ?,`determinacion3` = ?,`determinacion4` = ?,`determinacion5` = ?,`determinacion6` = ?,`determinacion7` = ?,`determinacion8` = ?,`determinacion9` = ?,`determinacion10` = ?," + "`determinacion11` = ?,`determinacion12` = ?,`determinacion13` = ?,`determinacion14` = ?,`determinacion15` = ?,`determinacion16` = ?,`determinacion17` = ?,`determinacion18` = ?,`determinacion19` = ?,`determinacion20` = ?," + "`determinacion21` = ?,`determinacion22` = ?,`determinacion23` = ?,`determinacion24` = ?,`determinacion25` = ?,`determinacion26` = ?,`determinacion27` = ?,`determinacion28` = ?,`determinacion29` = ?,`determinacion30` = ?," + "`determinacion31` = ?,`determinacion32` = ?,`determinacion33` = ?,`determinacion34` = ?," + "`recuentoObtenido1` = ?,`recuentoObtenido2` = ?,`recuentoObtenido3` = ?,`recuentoObtenido4` = ?,`recuentoObtenido5` = ?,`recuentoObtenido6` = ?,`recuentoObtenido7` = ?,`recuentoObtenido8` = ?,`recuentoObtenido9` = ?,`recuentoObtenido10` = ?," + "`recuentoObtenido11` = ?,`recuentoObtenido12` = ?,`recuentoObtenido13` = ?,`recuentoObtenido14` = ?,`recuentoObtenido15` = ?,`recuentoObtenido16` = ?,`recuentoObtenido17` = ?,`recuentoObtenido18` = ?,`recuentoObtenido19` = ?,`recuentoObtenido20` = ?," + "`recuentoObtenido21` = ?,`recuentoObtenido22` = ?,`recuentoObtenido23` = ?,`recuentoObtenido24` = ?,`recuentoObtenido25` = ?,`recuentoObtenido26` = ?,`recuentoObtenido27` = ?,`recuentoObtenido28` = ?,`recuentoObtenido29` = ?,`recuentoObtenido30` = ?," + "`recuentoObtenido31` = ?,`recuentoObtenido32` = ?,`recuentoObtenido33` = ?,`recuentoObtenido34` = ?," + "`recuentoNormal1` = ?,`recuentoNormal2` = ?,`recuentoNormal3` = ?,`recuentoNormal4` = ?,`recuentoNormal5` = ?,`recuentoNormal6` = ?,`recuentoNormal7` = ?,`recuentoNormal8` = ?,`recuentoNormal9` = ?,`recuentoNormal10` = ?," + "`recuentoNormal11` = ?,`recuentoNormal12` = ?,`recuentoNormal13` = ?,`recuentoNormal14` = ?,`recuentoNormal15` = ?,`recuentoNormal16` = ?,`recuentoNormal17` = ?,`recuentoNormal18` = ?,`recuentoNormal19` = ?,`recuentoNormal20` = ?," + "`recuentoNormal21` = ?,`recuentoNormal22` = ?,`recuentoNormal23` = ?,`recuentoNormal24` = ?,`recuentoNormal25` = ?,`recuentoNormal26` = ?,`recuentoNormal27` = ?,`recuentoNormal28` = ?,`recuentoNormal29` = ?,`recuentoNormal30` = ?," + "`recuentoNormal31` = ?,`recuentoNormal32` = ?,`recuentoNormal33` = ?,`recuentoNormal34` = ?," + "`metodo1` = ?,`metodo2` = ?,`metodo3` = ?,`metodo4` = ?,`metodo5` = ?,`metodo6` = ?,`metodo7` = ?,`metodo8` = ?,`metodo9` = ?,`metodo10` = ?," + "`metodo11` = ?,`metodo12` = ?,`metodo13` = ?,`metodo14` = ?,`metodo15` = ?,`metodo16` = ?,`metodo17` = ?,`metodo18` = ?,`metodo19` = ?,`metodo20` = ?," + "`metodo21` = ?,`metodo22` = ?,`metodo23` = ?,`metodo24` = ?,`metodo25` = ?,`metodo26` = ?,`metodo27` = ?,`metodo28` = ?,`metodo29` = ?,`metodo30` = ?," + "`metodo31` = ?,`metodo32` = ?,`metodo33` = ?,`metodo34` = ?, titulo = ?, mostrar = ? " + "WHERE `idmuestras` = ?;");
            int index = 1;
            for (int i = 1; i <= 34; i++) {
                ps.setString(index++, String.valueOf(m.get("determinacion" + i)));
            }
            for (int i = 1; i <= 34; i++) {
                ps.setString(index++, String.valueOf(m.get("recuentoObtenido" + i)));
            }
            for (int i = 1; i <= 34; i++) {
                ps.setString(index++, String.valueOf(m.get("recuentoNormal" + i)));
            }
            for (int i = 1; i <= 34; i++) {
                ps.setString(index++, String.valueOf(m.get("metodo" + i)));
            }
            ps.setString(index++, String.valueOf(m.get("titulo")));
            ps.setString(index++, String.valueOf(m.get("mostrar")));
            ps.setInt(index, (int) m.get("idmuestras"));

            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            logger.severe("Error al guardar datos, " + e);
            return false;
        } finally {
            try (Connection conexion = con.getConnection()) {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
    }

    public boolean guardarResultadoMBAguaDeRecreacion(Resultados r, int vencimiento) {
        
        try (Connection conexion = con.getConnection()) {
            PreparedStatement ps = conexion.prepareStatement("insert into MBAgua (idmuestras, " + "germenes, coliformesTotales, coliformesFecales, escherichia, " + "pseudomona, ph, cloroTotal, caracteresOrganolepticos, staphilococos, " + "streptococos, cloroLibre, vencimiento, shigella) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            ps.setInt(1, r.getIdmuestras());
            ps.setString(2, r.getGermenes());
            ps.setString(3, r.getColiformesTotales());
            ps.setString(4, r.getColiformesFecales());
            ps.setString(5, r.getEscherichia());
            ps.setString(6, r.getPseudomona());
            ps.setDouble(7, r.getPh());
            ps.setDouble(8, r.getClorototal());
            ps.setString(9, r.getCaracteresOrgasnolepticos());
            ps.setString(10, r.getStaphilococos());
            ps.setString(11, r.getStreptococos());
            ps.setDouble(12, r.getCloroLibre());
            ps.setInt(13, vencimiento);
            ps.setString(14, r.getShigella());
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            logger.severe("Error al guardar datos, " + e);
            return false;
        } finally {
            try (Connection conexion = con.getConnection()) {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
    }

    public double[] recuperarPhYCloro(int id) {
        
        double[] aux = {-1, -1, -1};
        try (Connection conexion = con.getConnection()) {
            PreparedStatement ps = conexion.prepareStatement("select cloroLibre, cloroTotal,ph from mbagua where idmuestras = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                for (int i = 0; i < aux.length; i++) {
                    if (rs.getObject(i + 1) != null) {
                        aux[i] = rs.getDouble(i + 1);
                    }
                }
            }
            return aux;
        } catch (Exception e) {
            logger.severe("Error al guardar datos, " + e);
            return null;
        } finally {
            try (Connection conexion = con.getConnection()) {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
    }

    public boolean checkearResultadoMBAgua(int id) {
        
        try (Connection conexion = con.getConnection()) {
            PreparedStatement ps = conexion.prepareStatement("select * from mbagua where idmuestras = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return true;
            }
        } catch (Exception e) {
            logger.severe("Error al checkearResultadosMBAgua, " + e);
        } finally {
            try (Connection conexion = con.getConnection()) {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
        return false;
    }

    public Map<String, String> recuperarResultadosMBAgua(int id) {
        
        Map valores = new HashMap();
        boolean nulo = true;
        try (Connection conexion = con.getConnection()) {
            PreparedStatement ps = conexion.prepareStatement("select germenes, coliformesTotales, coliformesFecales, escherichia," + " pseudomona, caracteresOrganolepticos, fechaAnalisis, mohos, mohosLimite, shigella from " + "mbagua where idmuestras = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                    if (rs.getObject(i + 1) != null) {
                        nulo = false;
                        valores.put(rs.getMetaData().getColumnName(i + 1), String.valueOf(rs.getObject(i + 1)));
                    }
                }
                return nulo ? null : valores;
            }
        } catch (Exception e) {
            logger.severe("Error al recuperar datos, " + e);
        } finally {
            try (Connection conexion = con.getConnection()) {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
        return null;
    }

    public Map<String, String> recuperarResultadosMBAguaCOFES(int id) {
        
        Map<String, String> valores = new HashMap();
        try (Connection conexion = con.getConnection()) {
            PreparedStatement ps = conexion.prepareStatement("select germenes, coliformesTotales, coliformesFecales, escherichia," + " pseudomona, caracteresOrganolepticos, fechaAnalisis, shigella from " + "mbagua where idmuestras = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                for (int i = 0; i < ps.getMetaData().getColumnCount(); i++) {
                    if (rs.getObject(i + 1) == null) {
                        return null;
                    } else {
                        valores.put(rs.getMetaData().getColumnName(i + 1), String.valueOf(rs.getObject(i + 1)));
                    }
                }
                return valores;
            }
        } catch (Exception e) {
            logger.severe("Error al recuperar datos, " + e);
        } finally {
            try (Connection conexion = con.getConnection()) {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
        return null;
    }

    public Map<String, String> recuperarResultadosMBAguaDeRecreacion(int id) {
        
        Map<String, String> valores = new HashMap();
        try (Connection conexion = con.getConnection()) {
            PreparedStatement ps = conexion.prepareStatement("select germenes, coliformesTotales, " + "coliformesFecales, escherichia, pseudomona, staphilococos, " + "streptococos, caracteresOrganolepticos, fechaanalisis, shigella from " + "mbagua where idmuestras = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                for (int i = 0; i < ps.getMetaData().getColumnCount(); i++) {
                    valores.put(ps.getMetaData().getColumnName(i + 1), String.valueOf(rs.getObject(i + 1)));
                }
                return valores;
            }
        } catch (Exception e) {
            logger.severe("Error al recuperar datos, " + e);
        } finally {
            try (Connection conexion = con.getConnection()) {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
        return null;
    }

    public Map<String, String> recuperarResultadosFQAgua(int id) {
        
        Map<String, String> valores = new HashMap<>();
        try (Connection conexion = con.getConnection()) {
            PreparedStatement ps = conexion.prepareStatement("select `ph`,`cloroTotal`," + "`olor`,`color`,`turbidez`,`alcalinidad`,`durezatotal`,`conductividad`," + "`solidosDisueltos`,`hierro`,`nitratos`,`nitritos`,`sulfatos` from" + " `laboratorio`.`fqagua` where idmuestras = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                    valores.put(rs.getMetaData().getColumnName(i + 1), rs.getObject(i + 1) == null ? "" : rs.getObject(i + 1).toString());
                }
                System.out.println(valores.isEmpty() + " recuperar");
                return valores;
            }
        } catch (Exception e) {
            logger.severe("Error al recuperar datos, " + e);
        } finally {
            try (Connection conexion = con.getConnection()) {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
        return null;
    }

    public boolean guardarResultadoFQAgua(Resultados r) {
        
        try (Connection conexion = con.getConnection()) {
            PreparedStatement ps = conexion.prepareStatement("insert into fqagua (idmuestras, ph, cloroTotal, " + "olor, color, turbidez, alcalinidad, durezatotal, conductividad, " + "solidosDisueltos, hierro, nitratos, nitritos, sulfatos, conclusion)" + " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            ps.setInt(1, r.getIdmuestras());
            ps.setDouble(2, r.getPh());
            ps.setDouble(3, r.getClorototal());
            ps.setString(4, r.getOlor());
            ps.setString(5, r.getColor());
            ps.setString(6, r.getTurbidez());
            ps.setDouble(7, r.getAlcalinidad());
            ps.setDouble(8, r.getDurezatotal());
            ps.setDouble(9, r.getConductividad());
            ps.setDouble(10, r.getSolidosdisueltos());
            ps.setString(11, r.getHierro());
            ps.setString(12, r.getNitratos());
            ps.setString(13, r.getNitritos());
            ps.setString(14, r.getSulfatos());
            ps.setString(15, r.getConclusion());

            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            logger.severe("Error al guardar datos, " + e);
            return false;
        } finally {
            try (Connection conexion = con.getConnection()) {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
    }

    public String[] recuperarTablaNutricional(int id) {
        
        String[] valores = new String[30];
        try (Connection conexion = con.getConnection()) {
            PreparedStatement ps = conexion.prepareStatement("select calorias, carbohidratos, " + "proteinas, grasasTotales, grasasSaturadas, grasasTrans, " + "grasasMonoinsaturadas, grasasPoliinsaturadas, colesterol, " + "fibraAlimentaria, sodio, VDCalorias, VDCarbohidratos, " + "VDProteinas, VDGrasasTotales, VDGrasasMonoinsaturadas, " + "VDGrasasPoliinsaturadas, VDColesterol, VDGrasasSaturadas, " + "VDGrasasTrans, VDFibraAlimentaria, VDSodio, porcion, unidad, " + "kjul, azucares, almidon, PorcionesPorEnvase, azucaresanadidos, vdazucaresanadidos from tablanutricional " + "where idmuestras = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                for (int i = 0; i < valores.length; i++) {
                    valores[i] = String.valueOf(rs.getObject(i + 1));
                    ;
                }
                return valores;
            }
        } catch (Exception e) {
            logger.severe("Error al recuperarTablaNutricional, " + e);
        } finally {
            try (Connection conexion = con.getConnection()) {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
        return null;
    }

    public boolean editarTablaNutricional(Double[] valores, int id, String unidad, String porcion, String porcionesPorEnvase) {
        
        try (Connection conexion = con.getConnection()) {
            PreparedStatement ps = conexion.prepareStatement("update tablanutricional set calorias = ?," + "kjul = ?,carbohidratos = ?,proteinas = ?,grasasTotales = ?," + "grasasSaturadas = ?,grasasTrans = ?, GrasasMonoinsaturadas = ?," + " GrasasPoliinsaturadas = ?, Colesterol = ?, fibraAlimentaria = ?," + "sodio = ?,VDCalorias = ?,VDCarbohidratos = ?,VDProteinas = ?" + ",VDGrasasTotales = ?,VDGrasasSaturadas = ?, VDGrasasTrans = ?, " + "VDGrasasMonoinsaturadas = ?, VDGrasasPoliinsaturadas = ?, VDColesterol = ?" + ",VDFibraAlimentaria = ?,VDSodio = ?, porcion = ?, unidad = ?, azucares = ?, " + "almidon = ?, PorcionesPorEnvase = ?, azucaresAnadidos = ?, VDAzucaresAnadidos = ? where idmuestras = ?");
            ps.setInt(1, (int) Math.round(valores[0]));
            ps.setDouble(2, valores[1]);
            ps.setDouble(3, valores[2]);
            ps.setDouble(4, valores[3]);
            ps.setDouble(5, valores[4]);
            ps.setDouble(6, valores[5]);
            ps.setDouble(7, valores[6]);
            ps.setDouble(8, valores[7]);
            ps.setDouble(9, valores[8]);
            ps.setDouble(10, valores[9]);
            ps.setDouble(11, valores[10]);
            ps.setDouble(12, valores[11]);
            ps.setDouble(13, valores[12]);
            ps.setDouble(14, valores[13]);
            ps.setDouble(15, valores[14]);
            ps.setDouble(16, valores[15]);
            ps.setDouble(17, valores[16]);
            ps.setDouble(18, valores[17]);
            ps.setDouble(19, valores[18]);
            ps.setDouble(20, valores[19]);
            ps.setDouble(21, valores[20]);
            ps.setDouble(22, valores[21]);
            ps.setDouble(23, valores[22]);
            ps.setString(24, porcion);
            ps.setString(25, unidad);
            ps.setDouble(26, valores[25]);
            ps.setDouble(27, valores[26]);
            ps.setString(28, porcionesPorEnvase);
            ps.setDouble(29, valores[29]);
            ps.setDouble(30, valores[30]);
            ps.setInt(31, id);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            logger.severe("Error al editar datos, " + e);
            return false;
        } finally {
            try (Connection conexion = con.getConnection()) {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error al  TN, " + e);
            }
        }
    }

    public boolean editarFQAgua(Resultados r) {
        
        try (Connection conexion = con.getConnection()) {
            PreparedStatement ps = conexion.prepareStatement("UPDATE `laboratorio`.`fqagua` " + "SET `ph` = ? , `cloroTotal` = ? , `olor` = ? , `color` = ? ," + " `turbidez` = ? , `alcalinidad` = ? , `durezatotal` = ? , " + "`conductividad` = ? , `solidosDisueltos` = ? , `hierro` = ?, " + "`nitratos` = ? , `nitritos` = ? , `sulfatos` = ?" + "  WHERE `idmuestras` = ? ; ");
            ps.setDouble(1, r.getPh());
            ps.setDouble(2, r.getClorototal());
            ps.setString(3, r.getOlor());
            ps.setString(4, r.getColor());
            ps.setString(5, r.getTurbidez());
            ps.setDouble(6, r.getAlcalinidad());
            ps.setDouble(7, r.getDurezatotal());
            ps.setDouble(8, r.getConductividad());
            ps.setDouble(9, r.getSolidosdisueltos());
            ps.setString(10, r.getHierro());
            ps.setString(11, r.getNitratos());
            ps.setString(12, r.getNitritos());
            ps.setString(13, r.getSulfatos());
            ps.setInt(14, r.getIdmuestras());
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            logger.severe("Error al editar datos, " + e);
            return false;
        } finally {
            try (Connection conexion = con.getConnection()) {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
    }

    public boolean editarMBAgua(Resultados r) {
        
        try (Connection conexion = con.getConnection()) {
            PreparedStatement ps = conexion.prepareStatement("UPDATE `laboratorio`.`mbagua` SET `germenes` = ? " + ", `coliformesTotales` = ? , `coliformesFecales` = ?, `escherichia` = ? , `pseudomona` = ?, " + "ph = ?, cloroLibre = ?, caracteresOrganolepticos = ?, mohos = ?, fechaAnalisis = ?, shigella = ?" + " WHERE `idmuestras` = ? ;");
            ps.setString(1, r.getGermenes());
            ps.setString(2, r.getColiformesTotales());
            ps.setString(3, r.getColiformesFecales());
            ps.setString(4, r.getEscherichia());
            ps.setString(5, r.getPseudomona());
            ps.setDouble(6, r.getPh());
            ps.setDouble(7, r.getClorototal());
            ps.setString(8, r.getCaracteresOrgasnolepticos());
            ps.setString(9, r.getMohos());
            ps.setDate(10, r.getFechaAnalisis());
            ps.setString(11, r.getShigella());
            ps.setInt(12, r.getIdmuestras());
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            logger.severe("Error al editar datos, " + e);
            return false;
        } finally {
            try (Connection conexion = con.getConnection()) {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
    }

    public boolean guardarResultadosEfluentes(String[] resultados, int id) {
        
        try (Connection conexion = con.getConnection()) {
            PreparedStatement ps = conexion.prepareStatement("INSERT INTO `laboratorio`.`efluentes` " + "(idmuestras,ph,dqo,dbo,solidos10,solidos120," + "conclusion, hidrocarburos) values (?,?,?,?,?,?,?,?)");
            ps.setInt(1, id);
            ps.setDouble(2, Double.parseDouble(resultados[1]));
            ps.setDouble(3, Double.parseDouble(resultados[2]));
            ps.setDouble(4, Double.parseDouble(resultados[3]));
            ps.setString(5, resultados[4]);
            ps.setString(6, resultados[5]);
            ps.setString(7, resultados[6]);
            ps.setString(8, resultados[7]);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            logger.severe("Error al guardar datos, " + e);
            return false;
        } finally {
            try (Connection conexion = con.getConnection()) {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error al guardar efluentes, " + e);
            }
        }
    }

    public boolean editarEfluentes(String[] resultados, int id) {
        
        try (Connection conexion = con.getConnection()) {
            PreparedStatement ps = conexion.prepareStatement("UPDATE `laboratorio`.`efluentes` SET `ph` = ? " + ", `dqo` = ? , `dbo` = ? , `solidos10` = ? " + ",`solidos120` = ?, conclusion = ?, hidrocarburos = ?  WHERE `idmuestras` = ? ;");
            ps.setDouble(1, Double.parseDouble(resultados[1]));
            ps.setDouble(2, Double.parseDouble(resultados[2]));
            ps.setDouble(3, Double.parseDouble(resultados[3]));
            ps.setString(4, resultados[4]);
            ps.setString(5, resultados[5]);
            ps.setString(6, resultados[6]);
            ps.setString(7, resultados[7]);
            ps.setInt(8, id);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            logger.severe("Error al editar datos, " + e);
            return false;
        } finally {
            try (Connection conexion = con.getConnection()) {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
    }

    public boolean guardarResultadoBaseHelada(Map m) {
        
        try (Connection conexion = con.getConnection()) {
            PreparedStatement ps = conexion.prepareStatement("INSERT INTO `laboratorio`.`mbchocolates`" + "(`idmuestras`,`germenes`, `coliformesTotales`," + "`coliformesFecales`, `escherichia`,`mohos`, conclusion, staphilococos, salmonella) " + "VALUES (?,?,?,?,?,?,?,?,?)");
            ps.setInt(1, (int) m.get("idmuestras"));
            ps.setString(2, String.valueOf(m.get("germenes")));
            ps.setString(3, String.valueOf(m.get("coliformesTotales")));
            ps.setString(4, String.valueOf(m.get("coliformesFecales")));
            ps.setString(5, String.valueOf(m.get("escherichia")));
            ps.setString(6, String.valueOf(m.get("mohos")));
            ps.setString(7, String.valueOf(String.valueOf(m.get("conclusion"))));
            ps.setString(8, String.valueOf(m.get("staphilococos")));
            ps.setString(9, String.valueOf(m.get("salmonella")));
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            logger.severe("Error al guardar datos, " + e);
            return false;
        } finally {
            try (Connection conexion = con.getConnection()) {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
    }

    public boolean editarResultadoBaseHelada(Map m) {
        
        try (Connection conexion = con.getConnection()) {
            PreparedStatement ps = conexion.prepareStatement("update mbchocolates set germenes = ?, " + "coliformesTotales = ?, coliformesFecales = ?, escherichia = ?, " + "mohos = ?, conclusion = ?," + "staphilococos = ?, salmonella = ? where idmuestras = ?");
            ps.setString(1, String.valueOf(m.get("germenes")));
            ps.setString(2, String.valueOf(m.get("coliformesTotales")));
            ps.setString(3, String.valueOf(m.get("coliformesFecales")));
            ps.setString(4, String.valueOf(m.get("escherichia")));
            ps.setString(5, String.valueOf(m.get("mohos")));
            ps.setString(6, String.valueOf(String.valueOf(m.get("conclusion"))));
            ps.setString(7, String.valueOf(m.get("staphilococos")));
            ps.setString(8, String.valueOf(m.get("salmonella")));
            ps.setInt(9, (int) m.get("idmuestras"));
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            logger.severe("Error al guardar datos, " + e);
            return false;
        } finally {
            try (Connection conexion = con.getConnection()) {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
    }

    public boolean guardarResultadoMBChocolates(Map m) {
        
        try (Connection conexion = con.getConnection()) {
            PreparedStatement ps = conexion.prepareStatement("INSERT INTO `laboratorio`.`mbchocolates`" + "(`idmuestras`,`germenes`, `coliformesTotales`," + "`coliformesFecales`, `escherichia`,`mohos`, conclusion, salmonella) " + "VALUES (?,?,?,?,?,?,?,?)");
            ps.setInt(1, (int) m.get("idmuestras"));
            ps.setString(2, String.valueOf(m.get("germenes")));
            ps.setString(3, String.valueOf(m.get("coliformesTotales")));
            ps.setString(4, String.valueOf(m.get("coliformesFecales")));
            ps.setString(5, String.valueOf(m.get("escherichia")));
            ps.setString(6, String.valueOf(m.get("mohos")));
            ps.setString(7, String.valueOf(m.get("conclusion")));
            ps.setString(8, String.valueOf(m.get("salmonella")));
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            logger.severe("Error al guardar datos, " + e);
            return false;
        } finally {
            try (Connection conexion = con.getConnection()) {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
    }

    public boolean editarResultadoMBChocolates(Map m) {
        
        try (Connection conexion = con.getConnection()) {
            PreparedStatement ps = conexion.prepareStatement("update mbchocolates set germenes = ?, " + "coliformesTotales = ?, coliformesFecales = ?, escherichia = ?, " + "mohos = ?, conclusion = ?, salmonella = ? where idmuestras = ?");
            ps.setString(1, String.valueOf(m.get("germenes")));
            ps.setString(2, String.valueOf(m.get("coliformesTotales")));
            ps.setString(3, String.valueOf(m.get("coliformesFecales")));
            ps.setString(4, String.valueOf(m.get("escherichia")));
            ps.setString(5, String.valueOf(m.get("mohos")));
            ps.setString(6, String.valueOf(m.get("conclusion")));
            ps.setString(7, String.valueOf(m.get("salmonella")));
            ps.setInt(8, (int) m.get("idmuestras"));
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            logger.severe("Error al editar datos, " + e);
            e.printStackTrace();
            return false;
        } finally {
            try (Connection conexion = con.getConnection()) {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
    }


    public boolean editarMBAlimentos(Map m) {
        
        try (Connection conexion = con.getConnection()) {
            PreparedStatement ps = conexion.prepareStatement("update mbalimentos set coliformesTotales = ?, " + "coliformesFecales = ?, staphilococos = ?, salmonella = ?, mohosLevadura = ?, " + "escherichia = ?, germenes = ?, coliformesTotalesMetodo = ?, " + "coliformesFecalesMetodo = ?, staphilococosMetodo = ?, salmonellaMetodo = ?, mohosLevaduraMetodo = ?, " + "escherichiaMetodo = ?, germenesMetodo = ?, escherichiah7 = ?," + "escherichia157 = ?, enterobacterias = ?, listeria = ?, bacillus = ?," + "perfringens = ?, sulfito = ?, campilobacter = ?, escherichiah7Metodo = ?," + "escherichia157Metodo = ?, enterobacteriasMetodo = ?, listeriaMetodo = ?, bacillusMetodo = ?," + "perfringensMetodo = ?, sulfitoMetodo = ?, campilobacterMetodo = ?, " + "caracteristicas = ?, caracteristicasMetodo = ?, coliformesTotalesA30 = ?,coliformesTotalesA30Metodo = ?," + " coliformesTotalesProbables = ?,coliformesTotalesProbablesMetodo = ?, " + "lactobacillus = ?, lactobacillusMetodo = ?, bacteriasLacticas = ?, bacteriasLacticasMetodo = ?," + " coliformesTotales45 = ?, coliformesTotales45Metodo = ?, vibrio = ?, vibrioMetodo = ?, vibrioCholerae = ?, vibrioCholeraeMetodo = ?," + "shigella = ?, shigellaMetodo = ? where idmuestras = ?");
            ps.setString(1, String.valueOf(m.get("coliformesTotales")));
            ps.setString(2, String.valueOf(m.get("coliformesFecales")));
            ps.setString(3, String.valueOf(m.get("staphilococos")));
            ps.setString(4, String.valueOf(m.get("salmonella")));
            ps.setString(5, String.valueOf(m.get("mohosLevaduras")));
            ps.setString(6, String.valueOf(m.get("escherichia")));
            ps.setString(7, String.valueOf(m.get("germenes")));
            ps.setString(8, String.valueOf(m.get("metodoColiformesTotales")));
            ps.setString(9, String.valueOf(m.get("metodoColiformesFecales")));
            ps.setString(10, String.valueOf(m.get("metodoStaphilococos")));
            ps.setString(11, String.valueOf(m.get("metodoSalmonella")));
            ps.setString(12, String.valueOf(m.get("metodoMohos")));
            ps.setString(13, String.valueOf(m.get("metodoEscherichia")));
            ps.setString(14, String.valueOf(m.get("metodoGermenes")));
            ps.setString(15, String.valueOf(m.get("escherichiaH7")));
            ps.setString(16, String.valueOf(m.get("escherichia157")));
            ps.setString(17, String.valueOf(m.get("enterobacterias")));
            ps.setString(18, String.valueOf(m.get("listeria")));
            ps.setString(19, String.valueOf(m.get("bacillus")));
            ps.setString(20, String.valueOf(m.get("perfringens")));
            ps.setString(21, String.valueOf(m.get("sulfito")));
            ps.setString(22, String.valueOf(m.get("campilobacter")));
            ps.setString(23, String.valueOf(m.get("metodoEscherichiaH7")));
            ps.setString(24, String.valueOf(m.get("metodoEscherichia157")));
            ps.setString(25, String.valueOf(m.get("metodoEnterobacterias")));
            ps.setString(26, String.valueOf(m.get("metodoListeria")));
            ps.setString(27, String.valueOf(m.get("metodoBacillus")));
            ps.setString(28, String.valueOf(m.get("metodoPerfringens")));
            ps.setString(29, String.valueOf(m.get("metodoSulfito")));
            ps.setString(30, String.valueOf(m.get("metodoCampilobacter")));
            ps.setString(31, String.valueOf(m.get("caracteristicas")));
            ps.setString(32, String.valueOf(m.get("metodoCaracteristicas")));
            ps.setString(33, String.valueOf(m.get("coliformesTotalesA30")));
            ps.setString(34, String.valueOf(m.get("metodoColiformesTotalesA30")));
            ps.setString(35, String.valueOf(m.get("coliformesTotalesProbables")));
            ps.setString(36, String.valueOf(m.get("metodoColiformesTotalesProbables")));
            ps.setString(37, String.valueOf(m.get("lactobacillus")));
            ps.setString(38, String.valueOf(m.get("metodoLactobacillus")));
            ps.setString(39, String.valueOf(m.get("bacteriasLacticas")));
            ps.setString(40, String.valueOf(m.get("metodoBacteriasLacticas")));
            ps.setString(41, String.valueOf(m.get("coliformesTotales45")));
            ps.setString(42, String.valueOf(m.get("metodoColiformesTotales45")));
            ps.setString(43, String.valueOf(m.get("vibrio")));
            ps.setString(44, String.valueOf(m.get("metodoVibrio")));
            ps.setString(45, String.valueOf(m.get("vibrioCholerae")));
            ps.setString(46, String.valueOf(m.get("metodoVibrioCholerae")));
            ps.setString(47, String.valueOf(m.get("shigella")));
            ps.setString(48, String.valueOf(m.get("metodoShigella")));
            ps.setInt(49, (int) m.get("idmuestras"));
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            logger.severe("Error al editar datos, " + e);
            return false;
        } finally {
            try (Connection conexion = con.getConnection()) {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
    }

    public boolean guardarResultadoMBAlimentos(Map m) {
        
        try (Connection conexion = con.getConnection()) {
            PreparedStatement ps = conexion.prepareStatement("INSERT INTO `laboratorio`.`mbalimentos` " + "(`idmuestras`, `coliformesTotales`, coliformesFecales, `staphilococos`, " + "`salmonella`, `mohosLevadura`,`escherichia`, germenes,`coliformesTotalesMetodo`, " + "coliformesFecalesMetodo, `staphilococosMetodo`, `salmonellaMetodo`, `mohosLevaduraMetodo`," + "`escherichiaMetodo`, germenesMetodo, `escherichiah7`, `escherichia157`, " + "`enterobacterias`, `listeria`, `bacillus`, `perfringens`, " + "`sulfito`, `campilobacter`, `escherichiah7Metodo`, `escherichia157Metodo`, " + "`enterobacteriasMetodo`, `listeriaMetodo`, `bacillusMetodo`, " + "`perfringensMetodo`, `sulfitoMetodo`, `campilobacterMetodo`, caracteristicas, caracteristicasMetodo, " + "coliformesTotalesA30, coliformesTotalesA30Metodo, coliformesTotalesProbables, coliformesTotalesProbablesMetodo," + "lactobacillus, lactobacillusMetodo, bacteriasLacticas, bacteriasLacticasMetodo, coliformesTotales45, coliformesTotales45Metodo, vibrio, vibrioMetodo, vibrioCholerae, vibrioCholeraeMetodo, shigella, shigellaMetodo) " + "VALUES (?,?,?,?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            ps.setInt(1, (int) m.get("idmuestras"));
            ps.setString(2, String.valueOf(m.get("coliformesTotales")));
            ps.setString(3, String.valueOf(m.get("coliformesFecales")));
            ps.setString(4, String.valueOf(m.get("staphilococos")));
            ps.setString(5, String.valueOf(m.get("salmonella")));
            ps.setString(6, m.get("mohosLevaduras").toString());
            ps.setString(7, String.valueOf(m.get("escherichia")));
            ps.setString(8, String.valueOf(m.get("germenes")));
            ps.setString(9, String.valueOf(m.get("metodoColiformesTotales")));
            ps.setString(10, String.valueOf(m.get("metodoColiformesFecales")));
            ps.setString(11, String.valueOf(m.get("metodoStaphilococos")));
            ps.setString(12, String.valueOf(m.get("metodoSalmonella")));
            ps.setString(13, String.valueOf(m.get("metodoMohos")));
            ps.setString(14, String.valueOf(m.get("metodoEscherichia")));
            ps.setString(15, String.valueOf(m.get("metodoGermenes")));
            ps.setString(16, String.valueOf(m.get("escherichiaH7")));
            ps.setString(17, String.valueOf(m.get("escherichia157")));
            ps.setString(18, String.valueOf(m.get("enterobacterias")));
            ps.setString(19, String.valueOf(m.get("listeria")));
            ps.setString(20, String.valueOf(m.get("bacillus")));
            ps.setString(21, String.valueOf(m.get("perfringens")));
            ps.setString(22, String.valueOf(m.get("sulfito")));
            ps.setString(23, String.valueOf(m.get("campilobacter")));
            ps.setString(24, String.valueOf(m.get("metodoEscherichiaH7")));
            ps.setString(25, String.valueOf(m.get("metodoEscherichia157")));
            ps.setString(26, String.valueOf(m.get("metodoEnterobacterias")));
            ps.setString(27, String.valueOf(m.get("metodoListeria")));
            ps.setString(28, String.valueOf(m.get("metodoBacillus")));
            ps.setString(29, String.valueOf(m.get("metodoPerfringens")));
            ps.setString(30, String.valueOf(m.get("metodoSulfito")));
            ps.setString(31, String.valueOf(m.get("metodoCampilobacter")));
            ps.setString(32, String.valueOf(m.get("caracteristicas")));
            ps.setString(33, String.valueOf(m.get("metodoCaracteristicas")));
            ps.setString(34, String.valueOf(m.get("coliformesTotalesA30")));
            ps.setString(35, String.valueOf(m.get("metodoColiformesTotalesA30")));
            ps.setString(36, String.valueOf(m.get("coliformesTotalesProbables")));
            ps.setString(37, String.valueOf(m.get("metodoColiformesTotalesProbables")));
            ps.setString(38, String.valueOf(m.get("lactobacillus")));
            ps.setString(39, String.valueOf(m.get("metodoLactobacillus")));
            ps.setString(40, String.valueOf(m.get("bacteriasLacticas")));
            ps.setString(41, String.valueOf(m.get("metodoBacteriasLacticas")));
            ps.setString(42, String.valueOf(m.get("coliformesTotales45")));
            ps.setString(43, String.valueOf(m.get("metodoColiformesTotales45")));
            ps.setString(44, String.valueOf(m.get("vibrio")));
            ps.setString(45, String.valueOf(m.get("metodoVibrio")));
            ps.setString(46, String.valueOf(m.get("vibrioCholerae")));
            ps.setString(47, String.valueOf(m.get("metodoVibrioCholerae")));
            ps.setString(48, String.valueOf(m.get("shigella")));
            ps.setString(49, String.valueOf(m.get("metodoShigella")));
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            logger.severe("Error al guardar datos, " + e);
            return false;
        } finally {
            try (Connection conexion = con.getConnection()) {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
    }

    public boolean verificarVacioMuestras() {
        
        try (Connection conexion = con.getConnection()) {
            PreparedStatement ps = conexion.prepareStatement("select * from muestras");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return false;
            }
            return true;
        } catch (Exception e) {
            logger.severe("Error al verificar datos, " + e);
            return true;
        } finally {
            try (Connection conexion = con.getConnection()) {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
    }

    public Map recuperarResultadosMBAlimentos(int id) {
        
        Map map = new HashMap();
        try (Connection conexion = con.getConnection()) {
            PreparedStatement ps = conexion.prepareStatement("select coliformesTotales, coliformesFecales, escherichia, " + "staphilococos, salmonella, mohosLevaduras, fechaAnalisis, germenes, " + "coliformesTotalesMetodo, coliformesFecalesMetodo, escherichiaMetodo, " + "staphilococosMetodo, salmonellaMetodo, mohosLevadurasMetodo, germenesMetodo, " + "`escherichiah7`, `escherichia157`, " + "`enterobacterias`, `listeria`, `bacillus`, `perfringens`, " + "`sulfito`, `campilobacter`, `escherichiah7Metodo`, `escherichia157Metodo`, " + "`enterobacteriasMetodo`, `listeriaMetodo`, `bacillusMetodo`, " + "`perfringensMetodo`, `sulfitoMetodo`, `campilobacterMetodo`, caracteristicas, caracteristicasMetodo," + "coliformesTotalesA30, coliformesTotalesProbables,coliformesTotalesA30Metodo, coliformesTotalesProbablesMetodo," + " lactobacillus, lactobacillusMetodo, bacteriasLacticas, bacteriasLacticasMetodo, coliformesTotales45, coliformesTotales45Metodo," + "vibrio, vibrioMetodo, vibrioCholerae, vibrioCholeraeMetodo, shigella, shigellaMetodo" + " from vistambalimentos where idmuestras = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                    if (rs.getObject(i + 1) == null) {
                        map.put(rs.getMetaData().getColumnName(i + 1), "");
                    } else {
                        map.put(rs.getMetaData().getColumnName(i + 1), rs.getObject(i + 1).toString());
                    }
                }
                return map;
            }
        } catch (Exception e) {
            logger.severe("Error al recuperarResultadosMBAlimentos, " + e);
            return null;
        } finally {
            try (Connection conexion = con.getConnection()) {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
        return null;
    }

    public boolean guardarPhYCloro(Map m) {
        
        String[] aux = new String[31];
        try (Connection conexion = con.getConnection()) {
            PreparedStatement ps = conexion.prepareStatement("insert into mbagua (ph, cloroLibre, cloroTotal,idmuestras) values (?,?,?,?)");
            ps.setString(1, String.valueOf(m.get("ph")));
            ps.setString(2, String.valueOf(m.get("libre")));
            ps.setString(3, String.valueOf(m.get("total")));
            ps.setInt(4, Integer.parseInt(String.valueOf(m.get("id"))));
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            logger.severe("Error al guardar datos, " + e);
            return false;
        } finally {
            try (Connection conexion = con.getConnection()) {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
    }

    public boolean editarPhYCloro(Map m) {
        
        String[] aux = new String[31];
        try (Connection conexion = con.getConnection()) {
            PreparedStatement ps = conexion.prepareStatement("update mbagua set ph = ?, cloroLibre = ?, cloroTotal = ? where idmuestras = ?");
            ps.setString(1, String.valueOf(m.get("ph")));
            ps.setString(2, String.valueOf(m.get("libre")));
            ps.setString(3, String.valueOf(m.get("total")));
            ps.setInt(4, Integer.parseInt(String.valueOf(m.get("id"))));
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            logger.severe("Error al guardar datos, " + e);
            return false;
        } finally {
            try (Connection conexion = con.getConnection()) {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
    }

    public Map<String, String> recuperarResultadosMBChocolates(int id) {
        
        Map<String, String> aux = new HashMap();
        try (Connection conexion = con.getConnection()) {
            PreparedStatement ps = conexion.prepareStatement("select germenes,coliformesTotales," + " coliformesFecales, escherichia, mohos, salmonella" + " from mbchocolates where idmuestras = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                    aux.put(rs.getMetaData().getColumnName(i + 1), rs.getObject(i + 1) == null ? "" : rs.getObject(i + 1).toString());
                }
                return aux;
            }
        } catch (Exception e) {
            logger.severe("Error al recuperarResultadosMBChocolates, " + e);
            return null;
        } finally {
            try (Connection conexion = con.getConnection()) {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
        return null;
    }


    public Map<String, String> recuperarResultadosEfluentes(int id) {
        
        Map<String, String> aux = new HashMap();
        try (Connection conexion = con.getConnection()) {
            PreparedStatement ps = conexion.prepareStatement("select ph, dqo, dbo, solidos10, solidos120, " + "hidrocarburos from efluentes where idmuestras = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                    aux.put(rs.getMetaData().getColumnName(i + 1), rs.getObject(i + 1) == null ? "" : rs.getObject(i + 1).toString());
                }
                return aux;
            }
        } catch (Exception e) {
            logger.severe("Error al recuperarResultadosEfluentes, " + e);
            return null;
        } finally {
            try (Connection conexion = con.getConnection()) {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
        return null;
    }

    public boolean guardarResultadosHisopados(Map m) {
        
        try (Connection conexion = con.getConnection()) {
            PreparedStatement ps = conexion.prepareStatement("INSERT INTO `laboratorio`.`hisopados` " + "(`idmuestras`, `coliformesTotales`, `coliformesFecales`, `escherichia`, " + "`germenes`,`staphilococos`, enterobacterias, salmonella, mohos, listeria, vibrio) VALUES " + "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            ps.setInt(1, (int) m.get("idmuestras"));
            ps.setString(2, m.get("coliformesTotales").toString());
            ps.setString(3, m.get("coliformesFecales").toString());
            ps.setString(4, m.get("escherichia").toString());
            ps.setString(5, m.get("germenes").toString());
            ps.setString(6, m.get("staphilococos").toString());
            ps.setString(7, m.get("enterobacterias").toString());
            ps.setString(8, m.get("salmonella").toString());
            ps.setString(9, m.get("mohos").toString());
            ps.setString(10, m.get("listeria").toString());
            ps.setString(11, m.get("vibrio").toString());
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            logger.severe("Error al guardar datos, " + e);
            return false;
        } finally {
            try (Connection conexion = con.getConnection()) {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
    }

    public boolean guardarResultadosHisopadosAlliance(Map m) {
        
        try (Connection conexion = con.getConnection()) {
            PreparedStatement ps = conexion.prepareStatement("INSERT INTO `laboratorio`.`hisopados` " + "(`idmuestras`, `coliformesTotales`, `coliformesFecales`, `escherichia`, " + "`germenes`,`staphilococos`, enterobacterias, limiteGermenes, limiteTotales)" + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
            ps.setInt(1, (int) m.get("idmuestras"));
            ps.setString(2, m.get("coliformesTotales").toString());
            ps.setString(3, m.get("coliformesFecales").toString());
            ps.setString(4, m.get("escherichia").toString());
            ps.setString(5, m.get("germenes").toString());
            ps.setString(6, m.get("staphilococos").toString());
            ps.setString(7, m.get("enterobacterias").toString());
            ps.setInt(8, Integer.parseInt(m.get("limiteGermenes").toString()));
            ps.setInt(9, Integer.parseInt(m.get("limiteTotales").toString()));
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            logger.severe("Error al guardar datos, " + e + " en línea " + e.getStackTrace()[0].getLineNumber());
            return false;
        } finally {
            try (Connection conexion = con.getConnection()) {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
    }

    public boolean editarResultadosHisopados(Map m) {
        
        try (Connection conexion = con.getConnection()) {
            PreparedStatement ps = conexion.prepareStatement("update hisopados set coliformesTotales = ?, " + "coliformesFecales = ?, escherichia= ?, germenes= ?, " + "staphilococos = ?, enterobacterias = ?, salmonella = ?, mohos = ?, listeria = ?, vibrio = ? where idmuestras = ?");
            ps.setString(1, m.get("coliformesTotales").toString());
            ps.setString(2, m.get("coliformesFecales").toString());
            ps.setString(3, m.get("escherichia").toString());
            ps.setString(4, m.get("germenes").toString());
            ps.setString(5, m.get("staphilococos").toString());
            ps.setString(6, m.get("enterobacterias").toString());
            ps.setString(7, m.get("salmonella").toString());
            ps.setString(8, m.get("mohos").toString());
            ps.setString(9, m.get("listeria").toString());
            ps.setString(10, m.get("vibrio").toString());
            ps.setInt(11, (int) m.get("idmuestras"));
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            logger.severe("Error al editar datos, " + e);
            return false;
        } finally {
            try (Connection conexion = con.getConnection()) {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
    }

    public boolean editarResultadosHisopadosAlliance(Map m) {
        
        try (Connection conexion = con.getConnection()) {
            PreparedStatement ps = conexion.prepareStatement("update hisopados set coliformesTotales = ?, " + "coliformesFecales = ?, escherichia= ?, germenes= ?, " + "staphilococos = ?, enterobacterias = ?, limiteGermenes = ?," + "limiteTotales = ? where idmuestras = ?");
            ps.setString(1, m.get("coliformesTotales").toString());
            ps.setString(2, m.get("coliformesFecales").toString());
            ps.setString(3, m.get("escherichia").toString());
            ps.setString(4, m.get("germenes").toString());
            ps.setString(5, m.get("staphilococos").toString());
            ps.setString(6, m.get("enterobacterias").toString());
            ps.setInt(7, Integer.parseInt(String.valueOf(m.get("limiteGermenes"))));
            ps.setInt(8, Integer.parseInt(String.valueOf(m.get("limiteTotales"))));
            ps.setInt(9, (int) m.get("idmuestras"));
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            logger.severe("Error al editar datos, " + e);
            return false;
        } finally {
            try (Connection conexion = con.getConnection()) {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
    }

    public Map<String, String> recuperarResultadosHisopados(int id) {
        
        Map<String, String> aux = new HashMap();
        try (Connection conexion = con.getConnection()) {
            PreparedStatement ps = conexion.prepareStatement("select germenes, coliformesTotales, " + "coliformesFecales, escherichia, staphilococos, fechaAnalisis, enterobacterias, salmonella, mohos, listeria, vibrio from " + "vistaHisopado where vistatabla_idmuestras = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                    System.out.println("rs.getMetaData().getColumnName(i + 1) = " + rs.getMetaData().getColumnName(i + 1));
                    System.out.println("rs.getObject(i + 1) = " + rs.getObject(i + 1));
                    aux.put(rs.getMetaData().getColumnName(i + 1), rs.getObject(i + 1) == null ? "" : rs.getObject(i + 1).toString());
                }
                return aux;
            }
        } catch (Exception e) {
            logger.severe("Error al recuperarResultadosHisopados, " + e);
            return null;
        } finally {
            try (Connection conexion = con.getConnection()) {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
        return null;
    }

    public Map<String, String> recuperarResultadosHisopadosAlliance(int id) {
        
        Map<String, String> aux = new HashMap<>();
        try (Connection conexion = con.getConnection()) {
            PreparedStatement ps = conexion.prepareStatement("select germenes, coliformesTotales, " + "coliformesFecales, escherichia, staphilococos, fechaAnalisis, enterobacterias," + "germenesPotencia, totalesPotencia, staphilococosPotencia, limiteGermenes, limiteTotales from " + "vistaHisopado where vistatabla_idmuestras = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                    aux.put(rs.getMetaData().getColumnName(i + 1), rs.getObject(i + 1) == null ? "" : rs.getObject(i + 1).toString());
                }
                return aux;
            }
        } catch (Exception e) {
            logger.severe("Error al recuperarResultadosHisopadosAlliance, " + e);
            return null;
        } finally {
            try (Connection conexion = con.getConnection()) {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
        return null;
    }

    public Map<String, String> recuperarResultadosBaseHelada(int id) {
        
        Map<String, String> aux = new HashMap();
        try (Connection conexion = con.getConnection()) {
            PreparedStatement ps = conexion.prepareStatement("select `germenes`, `coliformesTotales`," + "`coliformesFecales`, `escherichia`,`mohos`, conclusion, staphilococos, salmonella, fechaAnalisis from vistambchocolates where idmuestras = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                    aux.put(rs.getMetaData().getColumnName(i + 1), rs.getObject(i + 1) == null ? "" : rs.getObject(i + 1).toString());
                }
                return aux;
            }
        } catch (Exception e) {
            logger.severe("Error al recuperarResultadosBaseHelada, " + e);
            return null;
        } finally {
            try (Connection conexion = con.getConnection()) {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
        return null;
    }

    public String consultarMetodoDeterminaciones(int id, String determinacion) {
        
        String aux = "";
        try (Connection conexion = con.getConnection()) {
            PreparedStatement ps = conexion.prepareStatement(" select " + determinacion + "Metodo from determinaciones where idmuestras = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                aux = rs.getString(1);
                return aux;
            }
        } catch (Exception e) {
            logger.severe("Error al recuperarResultadosBaseHelada, " + e);
            return null;
        } finally {
            try (Connection conexion = con.getConnection()) {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
        return null;
    }

    public boolean editarMBAguaDeRecreacion(Resultados r, int vencimiento) {
        
        try (Connection conexion = con.getConnection()) {
            PreparedStatement ps = conexion.prepareStatement("UPDATE `laboratorio`.`mbagua` SET " + "`germenes` = ?, `coliformesTotales` = ? , `coliformesFecales` = ?, " + "`escherichia` = ? , `pseudomona` = ?, ph = ?, cloroTotal = ?, " + "caracteresOrganolepticos = ?, staphilococos = ?, streptococos = ?, " + "cloroLibre = ?, vencimiento = ?, shigella = ? WHERE `idmuestras` = ? ;");
            ps.setString(1, r.getGermenes());
            ps.setString(2, r.getColiformesTotales());
            ps.setString(3, r.getColiformesFecales());
            ps.setString(4, r.getEscherichia());
            ps.setString(5, r.getPseudomona());
            ps.setDouble(6, r.getPh());
            ps.setDouble(7, r.getClorototal());
            ps.setString(8, r.getCaracteresOrgasnolepticos());
            ps.setString(9, r.getStaphilococos());
            ps.setString(10, r.getStreptococos());
            ps.setDouble(11, r.getCloroLibre());
            ps.setInt(12, vencimiento);
            ps.setString(13, r.getShigella());
            ps.setInt(14, r.getIdmuestras());
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            logger.severe("Error al editar datos, " + e);
            return false;
        } finally {
            try (Connection conexion = con.getConnection()) {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
    }


    public boolean guardarResultadosEfluentesTipo(Map m) {
        
        try (Connection conexion = con.getConnection()) {
            PreparedStatement ps = conexion.prepareStatement("insert into efluentes (idmuestras, ph, conductividad," + " dqo, dbo, solidos10, solidos120, detergentes, grasasAceite," + " fosforoTotal, nitrogenoTotal, sustancias, coliformesFecales, hidrocarburos, nitratos, cloro, coliformesTotales, escherichia , sulfuros) " + "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            ps.setInt(1, (int) m.get("idmuestras"));
            ps.setDouble(2, (double) m.get("ph"));
            ps.setString(3, m.get("conductividad").toString());
            ps.setString(4, m.get("dqo").toString());
            ps.setString(5, m.get("dbo").toString());
            ps.setString(6, String.valueOf(m.get("solidos10")));
            ps.setString(7, String.valueOf(m.get("solidos120")));
            ps.setString(8, String.valueOf(m.get("detergentes")));
            ps.setString(9, String.valueOf(m.get("grasas")));
            ps.setString(10, String.valueOf(m.get("fosforo")));
            ps.setString(11, String.valueOf(m.get("nitrogeno")));
            ps.setString(12, String.valueOf(m.get("sustancias")));
            ps.setString(13, String.valueOf(m.get("coliformesFecales")));
            ps.setString(14, m.get("hidrocarburos").toString());
            ps.setString(15, m.get("nitratos").toString());
            ps.setString(16, m.get("cloro").toString());
            ps.setString(17, m.get("coliformesFecales").toString());
            ps.setString(18, m.get("escherichia").toString());
            ps.setString(19, m.get("sulfuros").toString());
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            logger.severe("Error al guardar datos, " + e);
            return false;
        } finally {
            try (Connection conexion = con.getConnection()) {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
    }

    public boolean editarResultadosEfluentesTipo(Map m) {
        
        try (Connection conexion = con.getConnection()) {
            PreparedStatement ps = conexion.prepareStatement("update efluentes set ph = ?, conductividad = ?," + " dqo = ?, dbo = ?, solidos10 = ?, solidos120 = ?, detergentes = ?, grasasAceite = ?, " + "fosforoTotal = ?, nitrogenoTotal = ?, sustancias = ?, coliformesFecales = ?, " + "hidrocarburos = ?, nitratos = ?, cloro = ?, coliformesTotales = ?, escherichia = ?, sulfuros = ? where idmuestras = ?");
            ps.setDouble(1, (double) m.get("ph"));//ph
            ps.setString(2, m.get("conductividad").toString());//conductividad
            ps.setString(3, m.get("dqo").toString());//dqo
            ps.setString(4, m.get("dbo").toString());//dbo
            ps.setString(5, String.valueOf(m.get("solidos10")));//solidos10
            ps.setString(6, String.valueOf(m.get("solidos120")));//solidos120
            ps.setString(7, String.valueOf(m.get("detergentes")));//detergentes
            ps.setString(8, String.valueOf(m.get("grasas")));//grasasAceite
            ps.setString(9, String.valueOf(m.get("fosforo")));//fosforoTotal
            ps.setString(10, String.valueOf(m.get("nitrogeno")));//nitrogenoTotal
            ps.setString(11, String.valueOf(m.get("sustancias")));//sustancias
            ps.setString(12, String.valueOf(m.get("coliformesFecales")));//coliformes
            ps.setString(13, m.get("hidrocarburos").toString());//hidrocarburos
            ps.setString(14, m.get("nitratos").toString());//hidrocarburos
            ps.setString(15, m.get("cloro").toString());//hidrocarburos
            ps.setString(16, m.get("coliformesTotales").toString());//hidrocarburos
            ps.setString(17, m.get("escherichia").toString());//hidrocarburos
            ps.setString(18, m.get("sulfuros").toString());//hidrocarburos
            ps.setInt(19, (int) m.get("idmuestras"));//idmuestras
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            logger.severe("Error al guardar datos, " + e + " en línea " + e.getStackTrace()[0].getLineNumber());
            return false;
        } finally {
            try (Connection conexion = con.getConnection()) {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
    }

    public Map<String, String> recuperarResultadosEfluentesTipo(int id) {
        
        Map<String, String> aux = new HashMap();
        try (Connection conexion = con.getConnection()) {
            PreparedStatement ps = conexion.prepareStatement("select ph, conductividad, dqo, dbo, " + "solidos10, solidos120, detergentes, grasasAceite, " + "fosforoTotal, nitrogenoTotal, sustancias, coliformesFecales, hidrocarburos, nitratos, cloro, coliformesTotales, escherichia, sulfuros " + "from efluentes where idmuestras = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                    aux.put(rs.getMetaData().getColumnName(i + 1), rs.getObject(i + 1) == null ? "" : rs.getObject(i + 1).toString());
                }
                return aux;
            }
        } catch (Exception e) {
            logger.severe("Error al recuperar datos, " + e);
            return null;
        } finally {
            try (Connection conexion = con.getConnection()) {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
        return null;
    }

    public void cambiarHisopado(int id) {
        
        try (Connection conexion = con.getConnection()) {
            PreparedStatement ps = conexion.prepareStatement("update hisopados set germenesPotencia = 0, " + "totalesPotencia = 0, staphilococosPotencia = 0, enterobacterias = 0 where idmuestras = ?");
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            logger.severe("Error al cambia de tipo de hisopado, " + e);
        } finally {
            try (Connection conexion = con.getConnection()) {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
    }

    public void cambiarTipo(int id, String db) {
        
        try (Connection conexion = con.getConnection()) {
            PreparedStatement ps = conexion.prepareStatement("delete from " + db + " where idmuestras = ?");
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            System.err.println("Error al cambia de tipo de hisopado, " + e);
            logger.severe("Error al cambia de tipo de hisopado, " + e);
        } finally {
            try (Connection conexion = con.getConnection()) {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
    }

    public String recuperarTituloManual(int id) {
        
        String titulo = "";
        try (Connection conexion = con.getConnection()) {
            PreparedStatement ps = conexion.prepareStatement("select titulo from manual where idmuestras = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                titulo = rs.getString("titulo");
            }
            return titulo;
        } catch (Exception e) {
            JOptionPane.showInputDialog("Error al recuperar titulo " + e);
            return null;
        } finally {
            try (Connection conexion = con.getConnection()) {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
    }

    public List recuperarFQAguaCompleto(int id, List<Determinacion> determinaciones) {
        
        try (Connection conexion = con.getConnection()) {
            PreparedStatement ps = conexion.prepareStatement("select * from determinaciones where idmuestras = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                for (int i = 3; i < rs.getMetaData().getColumnCount() + 1; i++) {
                    Object auxx = rs.getObject(i);
                    System.out.println("auxx = " + auxx);
                    if (auxx != null) {
                        if (!auxx.toString().isBlank()) {
                            for (int j = 0; j < determinaciones.size(); j++) {
                                if (determinaciones.get(j).getNombreDB().equals(rs.getMetaData().getColumnLabel(i))) {
                                    determinaciones.get(j).setActivado(true);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
            return determinaciones;
        } catch (Exception e) {
            logger.severe("Error al recuperarFQAguaCompleto, " + e);
        } finally {
            try (Connection conexion = con.getConnection()) {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error al recuperar FQCompleto, " + e);
            }
        }
        return null;
    }

    public List<Determinacion> recuperarDatosDeterminaciones(List<Determinacion> determinaciones, int id) {
        
        try (Connection conexion = con.getConnection()) {
            PreparedStatement ps = conexion.prepareStatement("select * from determinaciones where idmuestras = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                determinaciones.forEach(d -> {
                    try  {
                        String res = rs.getString(d.getNombreDB());
                        d.formatearResultado(res == null || res.trim().isEmpty() ? "-1" : res);
                    } catch (SQLException ex) {
                        System.getLogger(Consultas.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                    }
                });
            }
            return determinaciones;
        } catch (Exception e) {
            logger.severe("Error al recuperarDatosDeterminaciones, " + e.getStackTrace()[0]);
            return null;
        } finally {
            try (Connection conexion = con.getConnection()) {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error al recuperar datos FQ, " + e);
            }
        }
    }

    public List<Determinacion> recuperarDatosDeterminacionesGenerar(List<Determinacion> determinaciones, int id) {
        
        try (Connection conexion = con.getConnection()) {
            PreparedStatement ps = conexion.prepareStatement("select * from determinaciones where idmuestras = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                determinaciones.forEach(d -> {
                    try {
                        String res = rs.getString(d.getNombreDB());
                        d.formatearResultadoGenerar(res == null ? "-1" : res);
                    } catch (SQLException ex) {
                        System.getLogger(Consultas.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                    }
                });
            }
            return determinaciones;
        } catch (Exception e) {
            logger.severe("Error al recuperarDatosDeterminaciones, " + e);
            return null;
        } finally {
            try (Connection conexion = con.getConnection()) {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error al recuperar datos FQ, " + e);
            }
        }
    }

    public List<Determinacion> recuperarMetodosDeterminaciones(List<Determinacion> determinaciones, int id) {
        
        try (Connection conexion = con.getConnection()) {
            PreparedStatement ps = conexion.prepareStatement("select * from determinacionesMetodo where idmuestras = ?");//Cambiar al agregar
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                determinaciones.forEach(d -> {
                    try {
                        d.setMetodo(rs.getString(d.getNombreDB() + "Metodo"));
                    } catch (SQLException ex) {
                        System.getLogger(Consultas.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                    }
                });
            }
            return determinaciones;
        } catch (Exception e) {

            logger.severe("Error al recuperarMetodosDeterminaciones, " + e);
            StackTraceElement stackTraceElement = Arrays.stream(e.getStackTrace()).filter(ste -> ste.getClassName().equals(this.getClass().getName())).collect(Collectors.toList()).get(0);
            return null;
        } finally {
            try (Connection conexion = con.getConnection()) {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error al recuperar datos FQ, " + e);
            }
        }
    }

    public boolean blankearDeterminaciones(String query, int id, int cont) {
        
        try (Connection conexion = con.getConnection()) {
            PreparedStatement ps = conexion.prepareStatement(query);
            for (int i = 0; i < cont; i++) {
                ps.setString(i + 1, null);
            }
            ps.setInt(cont + 1, id);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            logger.severe("Error al blankearDeterminaciones, " + e);
        } finally {
            try (Connection conexion = con.getConnection()) {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error al recuperar FQCompleto, " + e);
            }
        }
        return false;
    }

    public boolean borrarDeterminaciones(int id) {
        
        try (Connection conexion = con.getConnection()) {
            conexion.setAutoCommit(false);
            PreparedStatement ps1 = conexion.prepareStatement("delete from determinaciones where idmuestras = ?");
            PreparedStatement ps2 = conexion.prepareStatement("delete from determinacionesMetodo where idmuestras = ?");
            ps1.setInt(1, id);
            ps2.setInt(1, id);
            ps1.execute();
            ps2.execute();
            conexion.commit();

            return true;
        } catch (Exception e) {
            logger.severe("Error al blankearDeterminaciones, " + e);
        } finally {
            try (Connection conexion = con.getConnection()) {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error al recuperar FQCompleto, " + e);
            }
        }
        return false;
    }

    public boolean guardarDeterminaciones(List<Determinacion> resultados, int id) {
        

        String queryDet = "insert into determinaciones (";
        for (int i = 0; i < resultados.size(); i++) {
            queryDet += resultados.get(i).getNombreDB() + ", ";
        }
        queryDet += "idmuestras) values (";
        for (int i = 0; i < resultados.size(); i++) {
            queryDet += "?, ";
        }
        queryDet += "?)";

        String queryMetodo = "insert into determinacionesMetodo (";
        for (int i = 0; i < resultados.size(); i++) {
            queryMetodo += resultados.get(i).getNombreDB() + "Metodo, ";
        }
        queryMetodo += "idmuestras) values (";
        for (int i = 0; i < resultados.size(); i++) {
            queryMetodo += "?, ";
        }
        queryMetodo += "?)";
        try (Connection conexion = con.getConnection()) {
            conexion.setAutoCommit(false);
            PreparedStatement ps1 = conexion.prepareStatement(queryDet);
            for (int i = 0; i < resultados.size(); i++) {
                ps1.setString(i + 1, resultados.get(i).getResultado());
            }
            ps1.setInt(resultados.size() + 1, id);

            PreparedStatement ps2 = conexion.prepareStatement(queryMetodo);
            for (int i = 0; i < resultados.size(); i++) {
                ps2.setString(i + 1, resultados.get(i).getMetodo());
            }
            ps2.setInt(resultados.size() + 1, id);

            ps1.execute();
            ps2.execute();
            conexion.commit();
            return true;
        } catch (Exception e) {
            logger.severe("Error al guardar datos, " + e);
            return false;
        } finally {
            try (Connection conexion = con.getConnection()) {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
    }

    public void guardarLimiteMohos(int id, boolean selected) {
        

        try (Connection conexion = con.getConnection()) {
            PreparedStatement ps = conexion.prepareStatement("update mbagua set mohosLimite = ? where idmuestras = ?");
            ps.setInt(1, selected ? 1 : 0);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (Exception e) {
            logger.severe("Error al guardar datos, " + e);
        } finally {
            try (Connection conexion = con.getConnection()) {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
    }

    public void guardarMostrar(int id, String mostrar) {
        

        try (Connection conexion = con.getConnection()) {
            PreparedStatement ps = conexion.prepareStatement("update manual set mostrar = ? where idmuestras = ?");
            ps.setString(1, mostrar);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (Exception e) {
            logger.severe("Error al guardar datos, " + e);
        } finally {
            try (Connection conexion = con.getConnection()) {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
    }

    public boolean recuperarFQAguaCompleto(int id) {
        

        try (Connection conexion = con.getConnection()) {
            PreparedStatement ps = conexion.prepareStatement("select * from determinaciones where idmuestras = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return true;
            }
            return false;
        } catch (Exception e) {
            logger.severe("Error al guardar datos, " + e);
        } finally {
            try (Connection conexion = con.getConnection()) {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
        return false;
    }


    public boolean consultarMostrar(int idmuestras) {
        

        try (Connection conexion = con.getConnection()) {
            PreparedStatement ps = conexion.prepareStatement("select mostrar from manual where idmuestras = ?");
            ps.setInt(1, idmuestras);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getString("mostrar") == "true" ? true : false;
            }
            return false;
        } catch (Exception e) {
            logger.severe("Error al guardar datos, " + e);
        } finally {
            try (Connection conexion = con.getConnection()) {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
        return false;
    }

    public boolean guardarTablaNutricional(Double[] valores, int id, String unidad, String porcion, String porcionesPorEnvase) {
        try (Connection conexion = con.getConnection()) {
            PreparedStatement ps = conexion.prepareStatement("INSERT INTO laboratorio.tablanutricional " + "(idmuestras,calorias,kjul,carbohidratos,proteinas,grasasTotales," + "grasasSaturadas,grasasTrans,GrasasMonoinsaturadas,GrasasPoliinsaturadas,Colesterol," + "fibraAlimentaria,sodio,VDCalorias,VDCarbohidratos,VDProteinas," + "VDGrasasTotales,VDGrasasSaturadas,VDFibraAlimentaria,VDSodio, " + "porcion, unidad, azucares, almidon, vdazucares, vdalmidon, PorcionesPorEnvase, azucaresAnadidos, vdazucaresAnadidos)" + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            ps.setInt(1, id); //idmuestras
            ps.setInt(2, (int) Math.round(valores[0]));//calorias
            ps.setDouble(3, valores[1]);//kjul
            ps.setDouble(4, valores[2]);//carbohidratos
            ps.setDouble(5, valores[3]);//proteinas
            ps.setDouble(6, valores[4]);//grasasTotales
            ps.setDouble(7, valores[5]);//grasasSaturadas
            ps.setDouble(8, valores[6]);//grasasTrans
            ps.setDouble(9, valores[7]);//grasasMonoinsaturadas
            ps.setDouble(10, valores[8]);//grasasPoliinsaturadas
            ps.setDouble(11, valores[9]);//colesterol
            ps.setDouble(12, valores[10]);//fibraAlimentaria
            ps.setDouble(13, valores[11]);//sodio
            ps.setDouble(14, (int) Math.round(valores[12]));//vdcalorias
            ps.setDouble(15, (int) Math.round(valores[13]));//vdcarbohidratos
            ps.setDouble(16, (int) Math.round(valores[14]));//vdproteinas
            ps.setDouble(17, (int) Math.round(valores[15]));//vdgrasasTotales
            ps.setDouble(18, (int) Math.round(valores[16]));//vdGrasassaturadas
            ps.setDouble(19, (int) Math.round(valores[21]));//vdFibraalimentaria
            ps.setDouble(20, (int) Math.round(valores[22]));//vdSodio
            ps.setString(21, porcion);//porcion
            ps.setString(22, String.valueOf(valores[24]) + unidad);//unidad
            ps.setDouble(23, valores[25]);
            ps.setDouble(24, valores[26]);
            ps.setDouble(25, valores[27]);
            ps.setDouble(26, valores[28]);
            ps.setString(27, porcionesPorEnvase);
            ps.setDouble(28, valores[29]);
            ps.setDouble(29, valores[30]);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            logger.severe("Error al guardar datos, " + e);
            return false;
        }
    }
}
