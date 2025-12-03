package com.agendalc.agendalc.services.interfaces;

import java.time.LocalDate;

import com.agendalc.agendalc.dto.IndicadoresDto;

public interface DashboardService {

    IndicadoresDto obtenerIndicadoresDashboard(LocalDate fechaInicio, LocalDate fechaFin);

}
