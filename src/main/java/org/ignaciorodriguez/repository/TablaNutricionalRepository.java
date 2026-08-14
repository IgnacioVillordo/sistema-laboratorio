package org.ignaciorodriguez.repository;

import org.ignaciorodriguez.modelo.TablaNutricional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TablaNutricionalRepository extends JpaRepository<TablaNutricional, Long> {

    @Query("SELECT m.marca FROM tablanutricional m WHERE m.idmuestras = :idmuestras")
    Optional<String> findMarcaByIdmuestras(@Param("idmuestras") Long idmuestras);
}
