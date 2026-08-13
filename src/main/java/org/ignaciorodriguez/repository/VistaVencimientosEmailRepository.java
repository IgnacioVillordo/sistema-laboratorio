package org.ignaciorodriguez.repository;

import org.ignaciorodriguez.modelo.VistaVencimientosEmail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VistaVencimientosEmailRepository extends JpaRepository<VistaVencimientosEmail, Long> {
}
