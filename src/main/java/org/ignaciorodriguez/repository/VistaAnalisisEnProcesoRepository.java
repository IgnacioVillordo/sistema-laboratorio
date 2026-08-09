package org.ignaciorodriguez.repository;

import org.ignaciorodriguez.modelo.VistaProcedencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VistaAnalisisEnProcesoRepository extends JpaRepository<VistaProcedencia, Long> {
}
