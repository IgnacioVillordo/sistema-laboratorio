package org.ignaciorodriguez.repository;

import org.ignaciorodriguez.modelo.VistaVencimientos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface VistaVencimientosRepository extends JpaRepository<VistaVencimientos, Long> {

    @Query(value = "SELECT idmuestras, procedencia, fechaVencimiento, tipo " +
            "FROM vistavencimientos " +
            "WHERE aviso = 0 AND fechaVencimiento BETWEEN :desde AND :hasta",
            nativeQuery = true)
    List<VistaVencimientos> findVencimientosEntreFechas(
            @Param("desde") LocalDate desde,
            @Param("hasta") LocalDate hasta);
}
