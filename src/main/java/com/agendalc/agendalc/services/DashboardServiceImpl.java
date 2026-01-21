package com.agendalc.agendalc.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.agendalc.agendalc.dto.DetalleAgendamientoContribuyente;
import com.agendalc.agendalc.dto.IndicadoresDiariosDto;
import com.agendalc.agendalc.dto.IndicadoresDto;
import com.agendalc.agendalc.entities.Cita;
import com.agendalc.agendalc.entities.Tramite;
import com.agendalc.agendalc.exceptions.NotFounException;
import com.agendalc.agendalc.repositories.AsistenciaCitaRepository;
import com.agendalc.agendalc.repositories.CitaRepository;
import com.agendalc.agendalc.repositories.TramiteRepository;
import com.agendalc.agendalc.services.interfaces.DashboardService;
import com.agendalc.agendalc.services.mappers.DetalleAgedamientoMapper;

@Service
public class DashboardServiceImpl implements DashboardService {

    private final CitaRepository citaRepository;
    private final AsistenciaCitaRepository asistenciaCitaRepository;
    private final DetalleAgedamientoMapper detalleAgedamientoMapper;
    private final TramiteRepository tramiteRepository;

    public DashboardServiceImpl(CitaRepository citaRepository, AsistenciaCitaRepository asistenciaCitaRepository,
            DetalleAgedamientoMapper detalleAgedamientoMapper, TramiteRepository tramiteRepository) {
        this.citaRepository = citaRepository;
        this.asistenciaCitaRepository = asistenciaCitaRepository;
        this.detalleAgedamientoMapper = detalleAgedamientoMapper;
        this.tramiteRepository = tramiteRepository;
    }

    @Override
    public IndicadoresDto obtenerIndicadoresDashboard(LocalDate fechaInicio, LocalDate fechaFin) {
        IndicadoresDto indicadores = new IndicadoresDto();

        indicadores.setCitadasAgendadas(contarCitadasAgendadas(fechaInicio, fechaFin));
        indicadores.setOcupacionActual(calcularOcupacionActual(fechaInicio, fechaFin));
        indicadores.setOcupacionDia(calcularOcupacionDia(fechaInicio, fechaFin));
        indicadores.setTramiteMasSolicitado(encontrarTramiteMasSolicitado(fechaInicio, fechaFin));
        indicadores.setClaseMasSolicitada(encontrarClaseMasSolicitada(fechaInicio, fechaFin));

        return indicadores;
    }

    private Integer contarCitadasAgendadas(LocalDate fechaInicio, LocalDate fechaFin) {
        return citaRepository.countCitadasAgendadas(fechaInicio, fechaFin);
    }

    private double calcularOcupacionActual(LocalDate fechaInicio, LocalDate fechaFin) {
        long totalCitas = citaRepository.countCitadasAgendadas(fechaInicio, fechaFin);
        if (totalCitas == 0) {
            return 0.0;
        }
        long citasConAsistencia = asistenciaCitaRepository.countByCitaAgendaFechaBetween(fechaInicio, fechaFin);
        return ((double) citasConAsistencia / totalCitas) * 100.0;
    }

    private Double calcularOcupacionDia(LocalDate fechaInicio, LocalDate fechaFin) {
        return citaRepository.calculateOcupacionDia(fechaInicio, fechaFin);
    }

    private String encontrarTramiteMasSolicitado(LocalDate fechaInicio, LocalDate fechaFin) {
        return citaRepository.findTramiteMasSolicitado(fechaInicio, fechaFin);
    }

    private String encontrarClaseMasSolicitada(LocalDate fechaInicio, LocalDate fechaFin) {
        return citaRepository.findClaseMasSolicitada(fechaInicio, fechaFin);
    }

    @Override
    public IndicadoresDiariosDto obtenerIndicadoresDiarios(LocalDate fecha, Long idTipotramite) {

        Tramite tramite = obtenerTramitePorId(idTipotramite);

        int cantidadAgendados = contarAgendados(fecha);
        int cantidadAsistencias = contarAsistencias(fecha);
        double[] tasas = calcularTasas(cantidadAgendados, cantidadAsistencias);

        List<DetalleAgendamientoContribuyente> detalles = obtenerDetallesAgendamientosPorRut(fecha, tramite);

        return new IndicadoresDiariosDto(cantidadAgendados, tasas[0], tasas[1], detalles);
    }

    private int contarAgendados(LocalDate fecha) {
        return citaRepository.countByFechaHoraBetween(obtenerHoraInicioDia(fecha), obtenerHoraFinDia(fecha));
    }

    private int contarAsistencias(LocalDate fecha) {
        return asistenciaCitaRepository.countByCitaAgendaFecha(fecha);
    }

    private double[] calcularTasas(int cantidadAgendados, int cantidadAsistencias) {
        int cantidadInasistencias = cantidadAgendados - cantidadAsistencias;
        double tasaAsistencia = cantidadAgendados == 0 ? 0.0
                : ((double) cantidadAsistencias / cantidadAgendados) * 100.0;
        double tasaInasistencia = cantidadAgendados == 0 ? 0.0
                : ((double) cantidadInasistencias / cantidadAgendados) * 100.0;
        return new double[] { tasaAsistencia, tasaInasistencia };
    }

    List<DetalleAgendamientoContribuyente> obtenerDetallesAgendamientosPorRut(LocalDate fecha, Tramite tramite) {
        List<Cita> citas = citaRepository.findByFechaHoraBetweenAndAgendaTramite(obtenerHoraInicioDia(fecha),
                obtenerHoraFinDia(fecha), tramite);

        return detalleAgedamientoMapper.detalleAgendamientoContribuyente(citas);
    }

    private LocalDateTime obtenerHoraInicioDia(LocalDate fecha) {
        return fecha.atStartOfDay();
    }

    private LocalDateTime obtenerHoraFinDia(LocalDate fecha) {
        return fecha.plusDays(1).atStartOfDay();
    }

    private Tramite obtenerTramitePorId(Long idTramite) {
        return tramiteRepository.findById(idTramite)
                .orElseThrow(() -> new NotFounException("Trámite no encontrado con ID: " + idTramite));
    }
}