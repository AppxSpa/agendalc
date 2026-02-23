package com.agendalc.agendalc.services;

import com.agendalc.agendalc.dto.CitaDto;
import com.agendalc.agendalc.dto.ResumenDarioResponse;
import com.agendalc.agendalc.entities.Cita;
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

    public ResumenDiarioSeviceImpl(CitaRepository citaRepository, AsistenciaRepository asistenciaCitaRepository,
            CitaMapper citaMapper) {
        this.citaRepository = citaRepository;
        this.asistenciaCitaRepository = asistenciaCitaRepository;
        this.citaMapper = citaMapper;
    }

    @Override
    public ResumenDarioResponse resumendiario(LocalDate fecha) {

        int citados = citaRepository.countByFechaHoraBetween(fecha.atStartOfDay(), fecha.atTime(23, 59));

        int asistencias = asistenciaCitaRepository.countByCitaAgendaFecha(fecha);

        List<Cita> citas = citaRepository.findByFechaHoraBetween(fecha.atStartOfDay(), fecha.atTime(23, 59));

        List<CitaDto> citaDtos = citaMapper.toDtoList(citas);

        return new ResumenDarioResponse(citados, asistencias, citaDtos);

    }

}
