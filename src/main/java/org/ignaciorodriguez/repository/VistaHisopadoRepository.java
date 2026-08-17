package org.ignaciorodriguez.repository;

import org.ignaciorodriguez.modelo.VistaHisopado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VistaHisopadoRepository extends JpaRepository<VistaHisopado, Long> {

    @Query("SELECT h.observaciones FROM VistaHisopado h WHERE vistatabla_idmuestras = :idmuestras")
    String findObservacionesByIdmuestras(@Param("idmuestras")Long idmuestras);

    @Query("SELECT h.conclusion FROM VistaHisopado h WHERE vistatabla_idmuestras = :idmuestras")
    String findConclusionByIdmuestras(@Param("idmuestras")Long idmuestras);

}
