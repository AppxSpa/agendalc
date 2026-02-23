package com.agendalc.agendalc.repositories;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.agendalc.agendalc.entities.BloqueHorario;
import com.agendalc.agendalc.entities.Cita;
import com.agendalc.agendalc.entities.SaludFormulario;
import com.agendalc.agendalc.entities.Solicitud;
import com.agendalc.agendalc.entities.Tramite;

public interface CitaRepository extends JpaRepository<Cita, Long> {

    // Métodos para DashboardServiceImpl
    @Query("SELECT COUNT(c) FROM Cita c WHERE c.agenda.fecha BETWEEN :fechaInicio AND :fechaFin")
    Integer countCitadasAgendadas(@Param("fechaInicio") LocalDate fechaInicio, @Param("fechaFin") LocalDate fechaFin);

    @Query("SELECT AVG(b.cuposTotales - b.cuposDisponibles) FROM BloqueHorario b JOIN b.agendas a WHERE a.fecha BETWEEN :fechaInicio AND :fechaFin")
    Double calculateOcupacionDia(@Param("fechaInicio") LocalDate fechaInicio, @Param("fechaFin") LocalDate fechaFin);

    @Query("SELECT t.nombre FROM Cita c JOIN c.agenda.tramite t WHERE c.agenda.fecha BETWEEN :fechaInicio AND :fechaFin GROUP BY t.nombre ORDER BY COUNT(t.nombre) DESC")
    String findTramiteMasSolicitado(@Param("fechaInicio") LocalDate fechaInicio, @Param("fechaFin") LocalDate fechaFin);

    @Query("SELECT slo.tiposLicencias FROM Cita c JOIN c.solicitud s JOIN s.saludFormulario sf JOIN sf.licenciasOtorgadas slo WHERE c.agenda.fecha BETWEEN :fechaInicio AND :fechaFin GROUP BY slo.tiposLicencias ORDER BY COUNT(slo.tiposLicencias) DESC")
    String findClaseMasSolicitada(@Param("fechaInicio") LocalDate fechaInicio, @Param("fechaFin") LocalDate fechaFin);

    // Métodos para CitaServiceImpl
    List<Cita> findByRut(Integer rut);

    List<Cita> findByFechaHoraBetween(LocalDateTime fechaHoraInicio, LocalDateTime fechaHoraFin);

    Optional<Cita> findByRutAndAgenda_Fecha(Integer rut, LocalDate fecha);

    List<Cita> findByAgenda_FechaBetween(LocalDate fechaInicio, LocalDate fechaFin);

    // Métodos descubiertos por el compilador de Maven
    Optional<Cita> findBySolicitud(Solicitud solicitud);

    Optional<Cita> findBySaludFormulario(SaludFormulario saludFormulario);

    int countByFechaHoraBetween(
            LocalDateTime inicio,
            LocalDateTime fin);

    List<Cita> findByFechaHoraBetweenAndAgendaTramite(LocalDateTime fechaHoraInicio, LocalDateTime fechaHoraFin, Tramite tramite);

    int countByBloqueHorario(BloqueHorario bloqueHorario);

    
}
