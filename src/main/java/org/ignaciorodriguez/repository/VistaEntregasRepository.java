package org.ignaciorodriguez.repository;

import org.ignaciorodriguez.modelo.VistaEntregas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VistaEntregasRepository extends JpaRepository<VistaEntregas, Long> {
}
