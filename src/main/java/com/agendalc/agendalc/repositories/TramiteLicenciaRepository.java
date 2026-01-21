package com.agendalc.agendalc.repositories;

import com.agendalc.agendalc.entities.TramiteLicencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TramiteLicenciaRepository extends JpaRepository<TramiteLicencia, Long> {
}
