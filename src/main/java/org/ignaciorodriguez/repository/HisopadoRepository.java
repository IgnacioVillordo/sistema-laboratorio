package org.ignaciorodriguez.repository;

import org.ignaciorodriguez.modelo.Hisopado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HisopadoRepository extends JpaRepository<Hisopado, Long> {
}
