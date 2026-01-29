package com.agendalc.agendalc.services;

import com.agendalc.agendalc.dto.ResumenDarioResponse;
import com.agendalc.agendalc.repositories.AsistenciaRepository;
import com.agendalc.agendalc.repositories.CitaRepository;
import com.agendalc.agendalc.services.interfaces.ResumenDiarioSevice;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

@Service
public class ResumenDiarioSeviceImpl implements ResumenDiarioSevice {

    private final CitaRepository citaRepository;
    private final AsistenciaRepository asistenciaCitaRepository;


    public ResumenDiarioSeviceImpl(CitaRepository citaRepository, AsistenciaRepository asistenciaCitaRepository) {
        this.citaRepository = citaRepository;
        this.asistenciaCitaRepository = asistenciaCitaRepository;
    }

    @Override
    public ResumenDarioResponse resumendiario(LocalDate fecha) {

        int citados = citaRepository.countByFechaHoraBetween(fecha.atStartOfDay(), fecha.atTime(23, 59));

        int asistencias = asistenciaCitaRepository.countByCitaAgendaFecha(fecha);



        return new ResumenDarioResponse(citados,asistencias);

    }

}
