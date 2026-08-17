package org.ignaciorodriguez.repository;

import org.ignaciorodriguez.modelo.VistaEfluentes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VistaEfluentesRepository extends JpaRepository<VistaEfluentes, Long> {
}
