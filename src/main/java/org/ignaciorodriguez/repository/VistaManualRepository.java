package org.ignaciorodriguez.repository;

import org.ignaciorodriguez.modelo.VistaManual;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VistaManualRepository extends JpaRepository<VistaManual, Long> {

    @Query("SELECT m.observaciones FROM VistaManual m WHERE idmuestras = :idmuestras")
    String findObservacionesByIdmuestras(@Param("idmuestras")Long idmuestras);

    @Query("SELECT m.conclusion FROM VistaManual m WHERE idmuestras = :idmuestras")
    String findConclusionByIdmuestras(@Param("idmuestras")Long idmuestras);
}
