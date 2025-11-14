package com.agendalc.agendalc.repositories;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.agendalc.agendalc.entities.Cita;
import com.agendalc.agendalc.entities.SaludFormulario;

public interface CitaRepository extends JpaRepository<Cita, Long> {

    List<Cita> findByRut(Integer rut);

    Optional<Cita> findBySaludFormulario(SaludFormulario saludFormulario);

    List<Cita> findByFechaHoraBetween(LocalDateTime fechaHoraInicio, LocalDateTime fechaHoraFin);

    Optional<Cita> findByRutAndAgenda_Fecha(Integer rut, java.time.LocalDate fecha);

}
