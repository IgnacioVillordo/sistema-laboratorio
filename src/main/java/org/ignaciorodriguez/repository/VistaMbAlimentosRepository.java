package org.ignaciorodriguez.repository;

import org.ignaciorodriguez.modelo.VistaMbAlimentos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VistaMbAlimentosRepository extends JpaRepository<VistaMbAlimentos, Long> {

    @Query("SELECT m.observaciones FROM VistaMbAlimentos m WHERE idmuestras = :idmuestras")
    String findObservacionesByIdmuestras(@Param("idmuestras")Long idmuestras);

    @Query("SELECT m.conclusion FROM VistaMbAlimentos m WHERE idmuestras = :idmuestras")
    String findConclusionByIdmuestras(@Param("idmuestras")Long idmuestras);

    @Query("SELECT m.descripcionMuestra FROM VistaMbAlimentos m WHERE idmuestras = :idmuestras")
    String findDescripcionMuestraByIdmuestras(@Param("idmuestras")Long idmuestras);
}
