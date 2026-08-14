package org.ignaciorodriguez.repository;

import org.ignaciorodriguez.modelo.MbAlimentos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MbAlimentosRepository extends JpaRepository<MbAlimentos, Long> {

}
