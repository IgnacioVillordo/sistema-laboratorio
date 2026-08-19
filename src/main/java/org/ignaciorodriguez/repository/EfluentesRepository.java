package org.ignaciorodriguez.repository;

import org.ignaciorodriguez.modelo.Efluente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EfluentesRepository extends JpaRepository<Efluente, Long> {
}
