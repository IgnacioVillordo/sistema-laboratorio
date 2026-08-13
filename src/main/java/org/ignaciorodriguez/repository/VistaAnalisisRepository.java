package org.ignaciorodriguez.repository;

import org.ignaciorodriguez.modelo.VistaAnalisis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VistaAnalisisRepository extends JpaRepository<VistaAnalisis, Long> {

    //    public DefaultTableModel tablaAnalisis() {
//        Object[] fila = new Object[7];
//        DefaultTableModel modeloAnalisis = new DefaultTableModel();
//        modeloAnalisis.addColumn("");
//        modeloAnalisis.addColumn("ID");
//        modeloAnalisis.addColumn("Procedencia");
//        modeloAnalisis.addColumn("Solicitante");
//        modeloAnalisis.addColumn("Fecha de muestreo");
//        modeloAnalisis.addColumn("Tipo");
//        modeloAnalisis.addColumn("Impreso");
//        try (Connection conexion = con.getConnection()) {
//            PreparedStatement ps = conexion.prepareStatement("select m.idmuestras, p.procedencia, " + "m.solicitante,m.fechaMuestreo, m.tipo, a.analizado from muestras m join " + "administracion a on m.idmuestras = a.idmuestras join " + "vistaprocedencia p on m.idcliente = p.idcliente order by idmuestras desc");
//            ResultSet rs = ps.executeQuery();
//            while (rs.next()) {
//                for (int i = 0; i < fila.length; i++) {
//                    fila[0] = false;
//                    fila[1] = rs.getObject("idmuestras");
//                    fila[2] = rs.getObject("procedencia");
//                    fila[3] = rs.getObject("solicitante");
//                    SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
//                    fila[4] = df.format(rs.getDate("fechaMuestreo"));
//                    fila[5] = rs.getObject("tipo");
//                    int analizado = rs.getInt("analizado");
//                    fila[6] = analizado > 1 ? analizado + " veces." : analizado == 1 ? "1 vez." : "0 veces.";
//                }
//                modeloAnalisis.addRow(fila);
//            }
//            return modeloAnalisis;
//        } catch (Exception e) {
//            logger.severe("Error al obtener muestra, " + e);
//            return null;
//        }
//    }
//
}
