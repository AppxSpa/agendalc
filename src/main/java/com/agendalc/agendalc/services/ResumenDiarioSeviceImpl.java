package com.agendalc.agendalc.services;

import com.agendalc.agendalc.dto.CitaDto;
import com.agendalc.agendalc.dto.ResumenDarioResponse;
import com.agendalc.agendalc.entities.Agenda;
import com.agendalc.agendalc.repositories.AgendaRepository;
import com.agendalc.agendalc.repositories.AsistenciaRepository;
import com.agendalc.agendalc.repositories.CitaRepository;
import com.agendalc.agendalc.services.interfaces.ResumenDiarioSevice;
import com.agendalc.agendalc.services.mappers.CitaMapper;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class ResumenDiarioSeviceImpl implements ResumenDiarioSevice {

    private final CitaRepository citaRepository;
    private final AsistenciaRepository asistenciaCitaRepository;
    private final CitaMapper citaMapper;
    private final AgendaRepository agendaRepository;

    public ResumenDiarioSeviceImpl(CitaRepository citaRepository, AsistenciaRepository asistenciaCitaRepository,
            CitaMapper citaMapper, AgendaRepository agendaRepository) {
        this.citaRepository = citaRepository;
        this.asistenciaCitaRepository = asistenciaCitaRepository;
        this.citaMapper = citaMapper;
        this.agendaRepository = agendaRepository;
    }

    @Override
    public ResumenDarioResponse resumendiario(LocalDate fecha) {

        int citados = citaRepository.countByFechaHoraBetween(fecha.atStartOfDay(), fecha.atTime(23, 59));

        int asistencias = asistenciaCitaRepository.countByCitaAgendaFecha(fecha);

        List<Agenda> agendas = agendaRepository.findByFecha(fecha);

        List<CitaDto> citaDtos = agendas.stream()
                .flatMap(agenda -> agenda.getCitas().stream())
                .map(citaMapper::toDto)
                .toList();

        return new ResumenDarioResponse(citados, asistencias, citaDtos);

    }

}
