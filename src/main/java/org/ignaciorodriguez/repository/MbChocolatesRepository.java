package org.ignaciorodriguez.repository;

import org.ignaciorodriguez.modelo.MbChocolates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MbChocolatesRepository extends JpaRepository<MbChocolates, Long> {

}
