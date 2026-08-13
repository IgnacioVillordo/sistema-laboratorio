package org.ignaciorodriguez.repository;

import org.ignaciorodriguez.modelo.VistaMuestrasActivas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VistaMuestrasActivasRepository extends JpaRepository<VistaMuestrasActivas, Long> {

//    public String obtenerProcedencia(int id) {
//        String procedencia = "";
//        try (Connection conexion = con.getConnection()) {
//            PreparedStatement ps = conexion.prepareStatement("select procedencia from vistaTabla2 where idmuestras = ?");
//            ps.setInt(1, id);
//            ResultSet rs = ps.executeQuery();
//            if (rs.next()) {
//                procedencia = rs.getString(1);
//            }
//            return procedencia;
//        } catch (Exception e) {
//            logger.severe("Error al obtener procedencia, " + e);
//            return null;
//        }
//    }
}
