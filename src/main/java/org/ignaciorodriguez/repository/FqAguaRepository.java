package org.ignaciorodriguez.repository;

import org.ignaciorodriguez.modelo.FqAgua;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FqAguaRepository extends JpaRepository<FqAgua, Long> {

}
