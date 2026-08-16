package org.ignaciorodriguez.repository;

import org.ignaciorodriguez.modelo.Hisopados;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HisopadoRepository extends JpaRepository<Hisopados, Long> {
}
