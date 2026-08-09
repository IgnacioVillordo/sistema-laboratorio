package org.ignaciorodriguez.repository;

import org.ignaciorodriguez.modelo.VistaEmail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VistaEmailRepository extends JpaRepository<VistaEmail, Long> {
}
