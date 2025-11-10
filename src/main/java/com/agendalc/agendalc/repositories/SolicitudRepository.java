package com.agendalc.agendalc.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.agendalc.agendalc.entities.SaludFormulario;
import com.agendalc.agendalc.entities.Solicitud;
import com.agendalc.agendalc.entities.Tramite;

public interface SolicitudRepository extends JpaRepository<Solicitud, Long> {
    List<Solicitud> findByEstado(Solicitud.EstadoSolicitud estado);

    List<Solicitud> findByRut(Integer rut);

    Optional<Solicitud> findBySaludFormulario(SaludFormulario saludFormulario);

    List<Solicitud> findByAsignadoA(String rut);

    @Query("SELECT s FROM Solicitud s WHERE FUNCTION('YEAR', s.fechaSolicitud) = :year")
    List<Solicitud> findByFechaSolcitudYear(int year);

    @Query("SELECT s FROM Solicitud s LEFT JOIN FETCH s.movimientos WHERE YEAR(s.fechaSolicitud) = :year ORDER BY s.fechaSolicitud DESC")
    List<Solicitud> findByFechaSolicitudYearWithMovimientosOrdered(@Param("year") int year);

    List<Solicitud> findByFechaSolicitudBetweenAndEstado(LocalDate fechaInicio, LocalDate fechaFin,
            Solicitud.EstadoSolicitud estado);

    List<Solicitud> findByFechaSolicitudBetween(LocalDate fechaInicio, LocalDate fechaFin);

    Solicitud findFirstByRutOrderByFechaSolicitudDesc(Integer rut);

    Solicitud findFirstByRutAndTramiteOrderByFechaSolicitudDesc(Integer rut, Tramite tramite);

}
