package org.ignaciorodriguez.repository;

import jakarta.persistence.Id;
import org.ignaciorodriguez.modelo.VistaMbAgua;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VistaMbAguaRepository extends JpaRepository<VistaMbAgua, Long> {

    @Query("SELECT m.observaciones FROM VistaMbAgua m WHERE vistatabla_idmuestras = :idmuestras")
    String findObservacionesByIdmuestras(@Param("idmuestras")Long idmuestras);

    @Query("SELECT m.conclusion FROM VistaMbAgua m WHERE vistatabla_idmuestras = :idmuestras")
    String findConclusionByIdmuestras(@Param("idmuestras")Long idmuestras);

    @Query("SELECT m.vencimiento FROM VistaMbAgua m WHERE vistatabla_idmuestras = :idmuestras")
    Boolean findVencimientoByIdmuestras(@Param("idmuestras") Long idmuestras);
    @Query("SELECT m.ponerFechaVencimiento FROM VistaMbAgua m WHERE vistatabla_idmuestras = :idmuestras")
    Boolean findPonerFechaVencimientoByIdmuestras(@Param("idmuestras") Long idmuestras);
}
