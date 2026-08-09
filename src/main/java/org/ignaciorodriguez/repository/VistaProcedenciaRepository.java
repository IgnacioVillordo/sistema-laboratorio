package org.ignaciorodriguez.repository;

import org.ignaciorodriguez.modelo.VistaProcedencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VistaProcedenciaRepository extends JpaRepository<VistaProcedencia, Long> {

    List<VistaProcedencia> findAllByBorrados(Boolean borrado);

    Optional<VistaProcedencia> findByProcedencia(String procedencia);
}