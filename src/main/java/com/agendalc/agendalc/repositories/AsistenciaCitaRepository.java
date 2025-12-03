package com.agendalc.agendalc.repositories;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.agendalc.agendalc.entities.AsistenciaCita;

@Repository
public interface AsistenciaCitaRepository extends JpaRepository<AsistenciaCita, Long> {

    boolean existsByCitaIdCita(Long citaId);

    long countByCitaFechaCitaBetween(LocalDate fechaInicio, LocalDate fechaFin);

}
