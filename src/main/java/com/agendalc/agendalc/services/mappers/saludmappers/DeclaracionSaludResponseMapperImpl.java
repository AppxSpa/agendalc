package com.agendalc.agendalc.services.mappers.saludmappers;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.agendalc.agendalc.dto.CitaAsociadaDto;
import com.agendalc.agendalc.dto.DeclaracionSaludResponse;
import com.agendalc.agendalc.dto.SolicitudAsociadaDto;
import com.agendalc.agendalc.entities.Cita;
import com.agendalc.agendalc.entities.SaludFormulario;
import com.agendalc.agendalc.entities.Solicitud;
import com.agendalc.agendalc.repositories.CitaRepository;
import com.agendalc.agendalc.repositories.SolicitudRepository;

@Component
public class DeclaracionSaludResponseMapperImpl implements DeclaracionSaludResponseMapper {

    private final SolicitudRepository solicitudRepository;
    private final CitaRepository citaRepository;
    private final FormularioDtoMapper formularioDtoMapper;

    public DeclaracionSaludResponseMapperImpl(SolicitudRepository solicitudRepository,
            CitaRepository citaRepository,
            FormularioDtoMapper formularioDtoMapper) {
        this.solicitudRepository = solicitudRepository;
        this.citaRepository = citaRepository;
        this.formularioDtoMapper = formularioDtoMapper;
    }

    @Override
    public DeclaracionSaludResponse toDto(SaludFormulario formulario) {
        DeclaracionSaludResponse response = new DeclaracionSaludResponse();
        response.setDeclaracion(formularioDtoMapper.mapToDto(formulario));

        // Buscar Solicitud asociada
        Optional<Solicitud> solicitudOpt = solicitudRepository.findBySaludFormulario(formulario);
        if (solicitudOpt.isPresent()) {
            Solicitud sol = solicitudOpt.get();
            response.setSolicitudAsociada(
                    new SolicitudAsociadaDto(sol.getIdSolicitud(), sol.getEstado().name(),
                            sol.getFechaSolicitud()));
        }

        // Buscar Cita asociada
        Optional<Cita> citaOpt = citaRepository.findBySaludFormulario(formulario);
        if (citaOpt.isPresent()) {
            Cita c = citaOpt.get();
            response.setCitaAsociada(
                    new CitaAsociadaDto(c.getIdCita(), c.getFechaHora(), c.getAgenda().getIdAgenda()));
        }

        return response;
    }
}
