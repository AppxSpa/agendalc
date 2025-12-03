package com.agendalc.agendalc.services;

import java.time.LocalDate;

import com.agendalc.agendalc.dto.IndicadoresDto;
import com.agendalc.agendalc.repositories.AsistenciaCitaRepository;
import com.agendalc.agendalc.repositories.CitaRepository;
import com.agendalc.agendalc.services.interfaces.DashboardService;

public class DashboardServiceImpl implements DashboardService {

    private final CitaRepository citaRepository;
    private final AsistenciaCitaRepository asistenciaCitaRepository;

    public DashboardServiceImpl(CitaRepository citaRepository, AsistenciaCitaRepository asistenciaCitaRepository) {
        this.citaRepository = citaRepository;
        this.asistenciaCitaRepository = asistenciaCitaRepository;
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
        long totalCitas = citaRepository.countByFechaCitaBetween(fechaInicio, fechaFin);
        if (totalCitas == 0) {
            return 0.0;
        }
        long citasConAsistencia = asistenciaCitaRepository.countByCitaFechaCitaBetween(fechaInicio, fechaFin);
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
}
