package org.ignaciorodriguez.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ResultadoRepository extends JpaRepository<Resultado, Long> {


//    public boolean guardarResultadoBaseHelada(Map<String, Object> m) {
//
//        try (Connection conexion = con.getConnection()) {
//            PreparedStatement ps = conexion.prepareStatement("INSERT INTO `laboratorio`.`mbchocolates`" + "(`idmuestras`,`germenes`, `coliformesTotales`," + "`coliformesFecales`, `escherichia`,`mohos`, conclusion, staphilococos, salmonella) " + "VALUES (?,?,?,?,?,?,?,?,?)");
//            ps.setInt(1, (int) m.get("idmuestras"));
//            ps.setString(2, String.valueOf(m.get("germenes")));
//            ps.setString(3, String.valueOf(m.get("coliformesTotales")));
//            ps.setString(4, String.valueOf(m.get("coliformesFecales")));
//            ps.setString(5, String.valueOf(m.get("escherichia")));
//            ps.setString(6, String.valueOf(m.get("mohos")));
//            ps.setString(7, String.valueOf(String.valueOf(m.get("conclusion"))));
//            ps.setString(8, String.valueOf(m.get("staphilococos")));
//            ps.setString(9, String.valueOf(m.get("salmonella")));
//            ps.executeUpdate();
//            return true;
//        } catch (Exception e) {
//            logger.severe("Error al guardar datos, " + e);
//            return false;
//        }
//    }
//
//    public boolean editarResultadoBaseHelada(Map<String, String> m) {
//
//        try (Connection conexion = con.getConnection()) {
//            PreparedStatement ps = conexion.prepareStatement("update mbchocolates set germenes = ?, " + "coliformesTotales = ?, coliformesFecales = ?, escherichia = ?, " + "mohos = ?, conclusion = ?," + "staphilococos = ?, salmonella = ? where idmuestras = ?");
//            ps.setString(1, String.valueOf(m.get("germenes")));
//            ps.setString(2, String.valueOf(m.get("coliformesTotales")));
//            ps.setString(3, String.valueOf(m.get("coliformesFecales")));
//            ps.setString(4, String.valueOf(m.get("escherichia")));
//            ps.setString(5, String.valueOf(m.get("mohos")));
//            ps.setString(6, String.valueOf(String.valueOf(m.get("conclusion"))));
//            ps.setString(7, String.valueOf(m.get("staphilococos")));
//            ps.setString(8, String.valueOf(m.get("salmonella")));
//            ps.setInt(9, Integer.parseInt(m.get("idmuestras")));
//            ps.executeUpdate();
//            return true;
//        } catch (Exception e) {
//            logger.severe("Error al guardar datos, " + e);
//            return false;
//        }
//    }
//
//    public boolean guardarResultadoMBChocolates(Map<String, Object> m) {
//
//        try (Connection conexion = con.getConnection()) {
//            PreparedStatement ps = conexion.prepareStatement("INSERT INTO `laboratorio`.`mbchocolates`" + "(`idmuestras`,`germenes`, `coliformesTotales`," + "`coliformesFecales`, `escherichia`,`mohos`, conclusion, salmonella) " + "VALUES (?,?,?,?,?,?,?,?)");
//            ps.setInt(1, (int) m.get("idmuestras"));
//            ps.setString(2, String.valueOf(m.get("germenes")));
//            ps.setString(3, String.valueOf(m.get("coliformesTotales")));
//            ps.setString(4, String.valueOf(m.get("coliformesFecales")));
//            ps.setString(5, String.valueOf(m.get("escherichia")));
//            ps.setString(6, String.valueOf(m.get("mohos")));
//            ps.setString(7, String.valueOf(m.get("conclusion")));
//            ps.setString(8, String.valueOf(m.get("salmonella")));
//            ps.executeUpdate();
//            return true;
//        } catch (Exception e) {
//            logger.severe("Error al guardar datos, " + e);
//            return false;
//        }
//    }
//
//    public boolean editarResultadoMBChocolates(Map<String,Object> m) {
//
//        try (Connection conexion = con.getConnection()) {
//            PreparedStatement ps = conexion.prepareStatement("update mbchocolates set germenes = ?, " + "coliformesTotales = ?, coliformesFecales = ?, escherichia = ?, " + "mohos = ?, conclusion = ?, salmonella = ? where idmuestras = ?");
//            ps.setString(1, String.valueOf(m.get("germenes")));
//            ps.setString(2, String.valueOf(m.get("coliformesTotales")));
//            ps.setString(3, String.valueOf(m.get("coliformesFecales")));
//            ps.setString(4, String.valueOf(m.get("escherichia")));
//            ps.setString(5, String.valueOf(m.get("mohos")));
//            ps.setString(6, String.valueOf(m.get("conclusion")));
//            ps.setString(7, String.valueOf(m.get("salmonella")));
//            ps.setInt(8, (int) m.get("idmuestras"));
//            ps.executeUpdate();
//            return true;
//        } catch (Exception e) {
//            logger.severe("Error al editar datos, " + e);
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//

//
//    public boolean verificarVacioMuestras() {
//
//        try (Connection conexion = con.getConnection()) {
//            PreparedStatement ps = conexion.prepareStatement("select * from muestras");
//            ResultSet rs = ps.executeQuery();
//            return !rs.next();
//        } catch (Exception e) {
//            logger.severe("Error al verificar datos, " + e);
//            return true;
//        }
//    }
//


//
//    public Map<String, String> recuperarResultadosMBChocolates(int id) {
//
//        Map<String, String> aux = new HashMap<>();
//        try (Connection conexion = con.getConnection()) {
//            PreparedStatement ps = conexion.prepareStatement("select germenes,coliformesTotales," + " coliformesFecales, escherichia, mohos, salmonella" + " from mbchocolates where idmuestras = ?");
//            ps.setInt(1, id);
//            ResultSet rs = ps.executeQuery();
//            if (rs.next()) {
//                for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
//                    aux.put(rs.getMetaData().getColumnName(i + 1), rs.getObject(i + 1) == null ? "" : rs.getObject(i + 1).toString());
//                }
//                return aux;
//            }
//        } catch (Exception e) {
//            logger.severe("Error al recuperarResultadosMBChocolates, " + e);
//            return null;
//        }
//        return null;
//    }
//
//

//    public boolean guardarResultadosHisopados(Map m) {
//
//        try (Connection conexion = con.getConnection()) {
//            PreparedStatement ps = conexion.prepareStatement("INSERT INTO `laboratorio`.`hisopados` " + "(`idmuestras`, `coliformesTotales`, `coliformesFecales`, `escherichia`, " + "`germenes`,`staphilococos`, enterobacterias, salmonella, mohos, listeria, vibrio) VALUES " + "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
//            ps.setInt(1, (int) m.get("idmuestras"));
//            ps.setString(2, m.get("coliformesTotales").toString());
//            ps.setString(3, m.get("coliformesFecales").toString());
//            ps.setString(4, m.get("escherichia").toString());
//            ps.setString(5, m.get("germenes").toString());
//            ps.setString(6, m.get("staphilococos").toString());
//            ps.setString(7, m.get("enterobacterias").toString());
//            ps.setString(8, m.get("salmonella").toString());
//            ps.setString(9, m.get("mohos").toString());
//            ps.setString(10, m.get("listeria").toString());
//            ps.setString(11, m.get("vibrio").toString());
//            ps.executeUpdate();
//            return true;
//        } catch (Exception e) {
//            logger.severe("Error al guardar datos, " + e);
//            return false;
//        }
//    }
//
//    public boolean guardarResultadosHisopadosAlliance(Map m) {
//
//        try (Connection conexion = con.getConnection()) {
//            PreparedStatement ps = conexion.prepareStatement("INSERT INTO `laboratorio`.`hisopados` " + "(`idmuestras`, `coliformesTotales`, `coliformesFecales`, `escherichia`, " + "`germenes`,`staphilococos`, enterobacterias, limiteGermenes, limiteTotales)" + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
//            ps.setInt(1, (int) m.get("idmuestras"));
//            ps.setString(2, m.get("coliformesTotales").toString());
//            ps.setString(3, m.get("coliformesFecales").toString());
//            ps.setString(4, m.get("escherichia").toString());
//            ps.setString(5, m.get("germenes").toString());
//            ps.setString(6, m.get("staphilococos").toString());
//            ps.setString(7, m.get("enterobacterias").toString());
//            ps.setInt(8, Integer.parseInt(m.get("limiteGermenes").toString()));
//            ps.setInt(9, Integer.parseInt(m.get("limiteTotales").toString()));
//            ps.executeUpdate();
//            return true;
//        } catch (Exception e) {
//            logger.severe("Error al guardar datos, " + e + " en línea " + e.getStackTrace()[0].getLineNumber());
//            return false;
//        }
//    }
//
//    public boolean editarResultadosHisopados(Map m) {
//
//        try (Connection conexion = con.getConnection()) {
//            PreparedStatement ps = conexion.prepareStatement("update hisopados set coliformesTotales = ?, " + "coliformesFecales = ?, escherichia= ?, germenes= ?, " + "staphilococos = ?, enterobacterias = ?, salmonella = ?, mohos = ?, listeria = ?, vibrio = ? where idmuestras = ?");
//            ps.setString(1, m.get("coliformesTotales").toString());
//            ps.setString(2, m.get("coliformesFecales").toString());
//            ps.setString(3, m.get("escherichia").toString());
//            ps.setString(4, m.get("germenes").toString());
//            ps.setString(5, m.get("staphilococos").toString());
//            ps.setString(6, m.get("enterobacterias").toString());
//            ps.setString(7, m.get("salmonella").toString());
//            ps.setString(8, m.get("mohos").toString());
//            ps.setString(9, m.get("listeria").toString());
//            ps.setString(10, m.get("vibrio").toString());
//            ps.setInt(11, (int) m.get("idmuestras"));
//            ps.executeUpdate();
//            return true;
//        } catch (Exception e) {
//            logger.severe("Error al editar datos, " + e);
//            return false;
//        }
//    }
//
//    public boolean editarResultadosHisopadosAlliance(Map m) {
//
//        try (Connection conexion = con.getConnection()) {
//            PreparedStatement ps = conexion.prepareStatement("update hisopados set coliformesTotales = ?, " + "coliformesFecales = ?, escherichia= ?, germenes= ?, " + "staphilococos = ?, enterobacterias = ?, limiteGermenes = ?," + "limiteTotales = ? where idmuestras = ?");
//            ps.setString(1, m.get("coliformesTotales").toString());
//            ps.setString(2, m.get("coliformesFecales").toString());
//            ps.setString(3, m.get("escherichia").toString());
//            ps.setString(4, m.get("germenes").toString());
//            ps.setString(5, m.get("staphilococos").toString());
//            ps.setString(6, m.get("enterobacterias").toString());
//            ps.setInt(7, Integer.parseInt(String.valueOf(m.get("limiteGermenes"))));
//            ps.setInt(8, Integer.parseInt(String.valueOf(m.get("limiteTotales"))));
//            ps.setInt(9, (int) m.get("idmuestras"));
//            ps.executeUpdate();
//            return true;
//        } catch (Exception e) {
//            logger.severe("Error al editar datos, " + e);
//            return false;
//        }
//    }
//
//    public Map<String, String> recuperarResultadosHisopados(int id) {
//
//        Map<String, String> aux = new HashMap<>();
//        try (Connection conexion = con.getConnection()) {
//            PreparedStatement ps = conexion.prepareStatement("select germenes, coliformesTotales, " + "coliformesFecales, escherichia, staphilococos, fechaAnalisis, enterobacterias, salmonella, mohos, listeria, vibrio from " + "vistaHisopado where vistatabla_idmuestras = ?");
//            ps.setInt(1, id);
//            ResultSet rs = ps.executeQuery();
//            if (rs.next()) {
//                for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
//                    System.out.println("rs.getMetaData().getColumnName(i + 1) = " + rs.getMetaData().getColumnName(i + 1));
//                    System.out.println("rs.getObject(i + 1) = " + rs.getObject(i + 1));
//                    aux.put(rs.getMetaData().getColumnName(i + 1), rs.getObject(i + 1) == null ? "" : rs.getObject(i + 1).toString());
//                }
//                return aux;
//            }
//        } catch (Exception e) {
//            logger.severe("Error al recuperarResultadosHisopados, " + e);
//            return null;
//        }
//        return null;
//    }
//
//    public Map<String, String> recuperarResultadosHisopadosAlliance(int id) {
//
//        Map<String, String> aux = new HashMap<>();
//        try (Connection conexion = con.getConnection()) {
//            PreparedStatement ps = conexion.prepareStatement("select germenes, coliformesTotales, " + "coliformesFecales, escherichia, staphilococos, fechaAnalisis, enterobacterias," + "germenesPotencia, totalesPotencia, staphilococosPotencia, limiteGermenes, limiteTotales from " + "vistaHisopado where vistatabla_idmuestras = ?");
//            ps.setInt(1, id);
//            ResultSet rs = ps.executeQuery();
//            while (rs.next()) {
//                for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
//                    aux.put(rs.getMetaData().getColumnName(i + 1), rs.getObject(i + 1) == null ? "" : rs.getObject(i + 1).toString());
//                }
//                return aux;
//            }
//        } catch (Exception e) {
//            logger.severe("Error al recuperarResultadosHisopadosAlliance, " + e);
//            return null;
//        }
//        return null;
//    }
//
//    public Map<String, String> recuperarResultadosBaseHelada(int id) {
//
//        Map<String, String> aux = new HashMap<>();
//        try (Connection conexion = con.getConnection()) {
//            PreparedStatement ps = conexion.prepareStatement("select `germenes`, `coliformesTotales`," + "`coliformesFecales`, `escherichia`,`mohos`, conclusion, staphilococos, salmonella, fechaAnalisis from vistambchocolates where idmuestras = ?");
//            ps.setInt(1, id);
//            ResultSet rs = ps.executeQuery();
//            while (rs.next()) {
//                for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
//                    aux.put(rs.getMetaData().getColumnName(i + 1), rs.getObject(i + 1) == null ? "" : rs.getObject(i + 1).toString());
//                }
//                return aux;
//            }
//        } catch (Exception e) {
//            logger.severe("Error al recuperarResultadosBaseHelada, " + e);
//            return null;
//        }
//        return null;
//    }
//
//    public String consultarMetodoDeterminaciones(int id, String determinacion) {
//
//        String aux = "";
//        try (Connection conexion = con.getConnection()) {
//            PreparedStatement ps = conexion.prepareStatement(" select " + determinacion + "Metodo from determinaciones where idmuestras = ?");
//            ps.setInt(1, id);
//            ResultSet rs = ps.executeQuery();
//            while (rs.next()) {
//                aux = rs.getString(1);
//                return aux;
//            }
//        } catch (Exception e) {
//            logger.severe("Error al recuperarResultadosBaseHelada, " + e);
//            return null;
//        }
//        return null;
//    }
//


//    public void cambiarHisopado(int id) {
//
//        try (Connection conexion = con.getConnection()) {
//            PreparedStatement ps = conexion.prepareStatement("update hisopados set germenesPotencia = 0, " + "totalesPotencia = 0, staphilococosPotencia = 0, enterobacterias = 0 where idmuestras = ?");
//            ps.setInt(1, id);
//            ps.executeUpdate();
//        } catch (Exception e) {
//            logger.severe("Error al cambia de tipo de hisopado, " + e);
//        }
//    }
//
//    public void cambiarTipo(int id, String db) {
//
//        try (Connection conexion = con.getConnection()) {
//            PreparedStatement ps = conexion.prepareStatement("delete from " + db + " where idmuestras = ?");
//            ps.setInt(1, id);
//            ps.executeUpdate();
//        } catch (Exception e) {
//            System.err.println("Error al cambia de tipo de hisopado, " + e);
//            logger.severe("Error al cambia de tipo de hisopado, " + e);
//        }
//    }
//

//
//    public void recuperarFQAguaFQAguaCompleto(int id, List<Determinacion> determinaciones) {
//
//        try (Connection conexion = con.getConnection()) {
//            PreparedStatement ps = conexion.prepareStatement("select * from determinaciones where idmuestras = ?");
//            ps.setInt(1, id);
//            ResultSet rs = ps.executeQuery();
//            while (rs.next()) {
//                for (int i = 3; i < rs.getMetaData().getColumnCount() + 1; i++) {
//                    Object auxx = rs.getObject(i);
//                    if (auxx != null) {
//                        if (!auxx.toString().isBlank()) {
//                            for (Determinacion determinacione : determinaciones) {
//                                if (determinacione.getNombreDB().equals(rs.getMetaData().getColumnLabel(i))) {
//                                    determinacione.setActivado(true);
//                                    break;
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        } catch (Exception e) {
//            logger.severe("Error al recuperarFQAguaCompleto, " + e);
//        }
//    }
//
//    public void recuperarDatosDeterminaciones(List<Determinacion> determinaciones, int id) {
//
//        try (Connection conexion = con.getConnection()) {
//            PreparedStatement ps = conexion.prepareStatement("select * from determinaciones where idmuestras = ?");
//            ps.setInt(1, id);
//            ResultSet rs = ps.executeQuery();
//            while (rs.next()) {
//
//                determinaciones.forEach(d -> {
//                    try {
//                        String res = rs.getString(d.getNombreDB());
//                        d.formatearResultado(res == null || res.trim().isEmpty() ? "-1" : res);
//                    } catch (SQLException ex) {
//                        System.getLogger(ResultadoRepository.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
//                    }
//                });
//            }
//        } catch (Exception e) {
//            logger.severe("Error al recuperarDatosDeterminaciones, " + e.getStackTrace()[0]);
//        }
//    }
//
//    public void recuperarDatosDeterminacionesGenerar(List<Determinacion> determinaciones, int id) {
//
//        try (Connection conexion = con.getConnection()) {
//            PreparedStatement ps = conexion.prepareStatement("select * from determinaciones where idmuestras = ?");
//            ps.setInt(1, id);
//            ResultSet rs = ps.executeQuery();
//            while (rs.next()) {
//
//                determinaciones.forEach(d -> {
//                    try {
//                        String res = rs.getString(d.getNombreDB());
//                        d.formatearResultadoGenerar(res == null ? "-1" : res);
//                    } catch (SQLException ex) {
//                        System.getLogger(ResultadoRepository.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
//                    }
//                });
//            }
//        } catch (Exception e) {
//            logger.severe("Error al recuperarDatosDeterminaciones, " + e);
//        }
//    }
//
//    public void recuperarMetodosDeterminaciones(List<Determinacion> determinaciones, int id) {
//
//        try (Connection conexion = con.getConnection()) {
//            PreparedStatement ps = conexion.prepareStatement("select * from determinacionesMetodo where idmuestras = ?");//Cambiar al agregar
//            ps.setInt(1, id);
//            ResultSet rs = ps.executeQuery();
//            while (rs.next()) {
//                determinaciones.forEach(d -> {
//                    try {
//                        d.setMetodo(rs.getString(d.getNombreDB() + "Metodo"));
//                    } catch (SQLException ex) {
//                        System.getLogger(ResultadoRepository.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
//                    }
//                });
//            }
//        } catch (Exception e) {
//
//            logger.severe("Error al recuperarMetodosDeterminaciones, " + e);
//            StackTraceElement stackTraceElement = Arrays.stream(e.getStackTrace()).filter(ste -> ste.getClassName().equals(this.getClass().getName())).collect(Collectors.toList()).get(0);
//        }
//    }
//
//    public boolean blankearDeterminaciones(String query, int id, int cont) {
//
//        try (Connection conexion = con.getConnection()) {
//            PreparedStatement ps = conexion.prepareStatement(query);
//            for (int i = 0; i < cont; i++) {
//                ps.setString(i + 1, null);
//            }
//            ps.setInt(cont + 1, id);
//            ps.executeUpdate();
//            return true;
//        } catch (Exception e) {
//            logger.severe("Error al blankearDeterminaciones, " + e);
//        } finally {
//            try (Connection conexion = con.getConnection()) {
//                conexion.close();
//            } catch (Exception e) {
//                System.err.println("Error al recuperar FQCompleto, " + e);
//            }
//        }
//        return false;
//    }
//
//    public boolean borrarDeterminaciones(int id) {
//
//        try (Connection conexion = con.getConnection()) {
//            conexion.setAutoCommit(false);
//            PreparedStatement ps1 = conexion.prepareStatement("delete from determinaciones where idmuestras = ?");
//            PreparedStatement ps2 = conexion.prepareStatement("delete from determinacionesMetodo where idmuestras = ?");
//            ps1.setInt(1, id);
//            ps2.setInt(1, id);
//            ps1.execute();
//            ps2.execute();
//            conexion.commit();
//
//            return true;
//        } catch (Exception e) {
//            logger.severe("Error al blankearDeterminaciones, " + e);
//        }
//        return false;
//    }
//
//    public boolean guardarDeterminaciones(List<Determinacion> resultados, int id) {
//
//
//        String queryDet = "insert into determinaciones (";
//        for (Determinacion resultado : resultados) {
//            queryDet += resultado.getNombreDB() + ", ";
//        }
//        queryDet += "idmuestras) values (";
//        for (int i = 0; i < resultados.size(); i++) {
//            queryDet += "?, ";
//        }
//        queryDet += "?)";
//
//        StringBuilder queryMetodo = new StringBuilder("insert into determinacionesMetodo (");
//        for (Determinacion resultado : resultados) {
//            queryMetodo.append(resultado.getNombreDB()).append("Metodo, ");
//        }
//        queryMetodo.append("idmuestras) values (");
//        queryMetodo.repeat("?, ", resultados.size());
//        queryMetodo.append("?)");
//        try (Connection conexion = con.getConnection()) {
//            conexion.setAutoCommit(false);
//            PreparedStatement ps1 = conexion.prepareStatement(queryDet);
//            for (int i = 0; i < resultados.size(); i++) {
//                ps1.setString(i + 1, resultados.get(i).getResultado());
//            }
//            ps1.setInt(resultados.size() + 1, id);
//
//            PreparedStatement ps2 = conexion.prepareStatement(queryMetodo.toString());
//            for (int i = 0; i < resultados.size(); i++) {
//                ps2.setString(i + 1, resultados.get(i).getMetodo());
//            }
//            ps2.setInt(resultados.size() + 1, id);
//
//            ps1.execute();
//            ps2.execute();
//            conexion.commit();
//            return true;
//        } catch (Exception e) {
//            logger.severe("Error al guardar datos, " + e);
//            return false;
//        }
//    }
//

//

//
//    public boolean recuperarFQAguaCompleto(int id) {
//
//
//        try (Connection conexion = con.getConnection()) {
//            PreparedStatement ps = conexion.prepareStatement("select * from determinaciones where idmuestras = ?");
//            ps.setInt(1, id);
//            ResultSet rs = ps.executeQuery();
//            return rs.next();
//        } catch (Exception e) {
//            logger.severe("Error al guardar datos, " + e);
//        }
//        return false;
//    }
//
//


//    public boolean guardarDeterminacionesAHacerFQAlimentosAgua(String[] listaDb, int id, boolean update) {
//        String sql = buildDeterminacionesQuery(listaDb, update);
//
//        try (Connection conexion = con.getConnection();
//             PreparedStatement ps = conexion.prepareStatement(sql)) {
//
//            for (int i = 0; i < listaDb.length; i++) {
//                ps.setString(i + 1, "");
//            }
//            ps.setInt(listaDb.length + 1, id);
//            return ps.executeUpdate() > 0;
//
//        } catch (SQLException e) {
//            logger.severe("Error al guardar determinaciones: " + e.getMessage());
//            return false;
//        }
//    }
//
//    private String buildDeterminacionesQuery(String[] listaDb, boolean update) {
//        StringBuilder sql;
//        if (update) {
//            sql = new StringBuilder("UPDATE determinaciones SET ");
//            for (String col : listaDb) {
//                sql.append(col).append(" = ?, ");
//            }
//            sql.setLength(sql.length() - 2);
//            sql.append(" WHERE idmuestras = ?");
//        } else {
//            sql = new StringBuilder("INSERT INTO determinaciones (");
//            for (String col : listaDb) {
//                sql.append(col).append(", ");
//            }
//            sql.append("idmuestras) VALUES (");
//            sql.repeat("?, ", listaDb.length);
//            sql.append("?)");
//        }
//        return sql.toString();
//    }
//
//    public boolean checkearPDF(int id, String db) {
//        try (Connection conexion = con.getConnection()) {
//            if (db.contains("nutricional")) {
//                PreparedStatement ps = conexion.prepareStatement("SELECT `calorias`,`kjul`,`carbohidratos`, `proteinas`,`grasasTotales`,`grasasSaturadas`," + "`grasasTrans`,`GrasasMonoinsaturadas`,`GrasasPoliinsaturadas`,`Colesterol`,`fibraAlimentaria`,`sodio`," + "`VDCalorias`,`VDCarbohidratos`,`VDProteinas`,`VDGrasasTotales`,`VDGrasasSaturadas`,`VDGrasasMonoinsaturadas`," + "`VDGrasasPoliinsaturadas`,`VDColesterol`,`VDGrasasTrans`,`VDFibraAlimentaria`,`VDSodio`,`porcion`," + "`unidad`,`azucares`,`VDAzucares`,`almidon`,`VDAlmidon`,`PorcionesPorEnvase`,`azucaresAnadidos`," + "`VDAzucaresAnadidos` FROM `laboratorio`.`tablanutricional` where idmuestras = ?");
//                ps.setInt(1, id);
//            } else if (db.contains("mbagua")) {
//                PreparedStatement ps = conexion.prepareStatement("select coliformesTotales from " + db + " where idmuestras = " + id);
//                ResultSet rs = ps.executeQuery();
//                return rs.next() && rs.getObject(1) != null;
//            } else {
//                PreparedStatement ps = conexion.prepareStatement("select * from " + db + " where idmuestras = " + id);
//                ResultSet rs = ps.executeQuery();
//                int cantidad = rs.getMetaData().getColumnCount();
//                boolean existe = false;
//                if (rs.next()) {
//                    for (int i = 2; i < cantidad; i++) {
//                        existe = existe || (rs.getObject(i + 1) != null && !rs.getObject(i + 1).toString().trim().isEmpty());
//                    }
//                    return existe;
//                }
//            }
//        } catch (SQLException e) {
//            JOptionPane.showMessageDialog(null, "Error al recuperar pdf, " + e);
//            StringWriter sw = new StringWriter();
//            PrintWriter pw = new PrintWriter(sw);
//            e.printStackTrace(pw);
//        }
//        return false;
//    }
//

}
