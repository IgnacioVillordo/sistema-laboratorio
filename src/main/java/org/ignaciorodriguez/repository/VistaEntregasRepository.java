package org.ignaciorodriguez.repository;

import org.ignaciorodriguez.modelo.VistaEntregas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VistaEntregasRepository extends JpaRepository<VistaEntregas, Long> {
    List<String> findDistinctProcedenciaBy();
}
