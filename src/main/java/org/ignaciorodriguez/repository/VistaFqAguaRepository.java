package org.ignaciorodriguez.repository;

import org.ignaciorodriguez.modelo.VistaFqAgua;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VistaFqAguaRepository extends JpaRepository<VistaFqAgua, Long> {
    @Query("SELECT f.ponerFechaVencimiento FROM VistaFqAgua f WHERE f.vistatabla_idmuestras = :idmuestras")
    Optional<String> findPonerFechaVencimientoByIdmuestras(@Param("idmuestras") Long idmuestras);

    @Query("SELECT f.conclusion FROM VistaFqAgua f WHERE f.vistatabla_idmuestras = :idmuestras")
    Optional<String> findConclusionByIdmuestras(@Param("idmuestras") Long idmuestras);

    @Query("SELECT f.observaciones FROM VistaFqAgua f WHERE f.vistatabla_idmuestras = :idmuestras")
    Optional<String> findbPservacionesByIdmuestras(@Param("idmuestras") Long idmuestras);

}
