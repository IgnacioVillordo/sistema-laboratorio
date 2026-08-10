package org.ignaciorodriguez.repository;

import org.ignaciorodriguez.modelo.VistaBorrado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VistaBorradoRepository extends JpaRepository<VistaBorrado, Long> {
}
