package com.agendalc.agendalc.services.interfaces;

import java.time.LocalDate;
import java.util.List;

import com.agendalc.agendalc.dto.DetalleAgendamientoContribuyente;
import com.agendalc.agendalc.dto.IndicadoresDiariosDto;
import com.agendalc.agendalc.dto.IndicadoresDto;

public interface DashboardService {

    IndicadoresDto obtenerIndicadoresDashboard(LocalDate fechaInicio, LocalDate fechaFin);

    IndicadoresDiariosDto obtenerIndicadoresDiarios(LocalDate fecha, Long idTipotramite);

    List<DetalleAgendamientoContribuyente> obtenerCitadosDeldia(LocalDate fecha);

}
