package com.agendalc.agendalc.services;

import com.agendalc.agendalc.dto.CitaAsociadaDto;
import com.agendalc.agendalc.dto.SolicitudResponseList;
import com.agendalc.agendalc.entities.Solicitud;
import com.agendalc.agendalc.repositories.CitaRepository;
import com.agendalc.agendalc.services.interfaces.SolicitudResponseTransformer;
import com.agendalc.agendalc.services.mappers.SolicitudMapper;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class SolicitudResponseTransformerImpl implements SolicitudResponseTransformer {

    private final CitaRepository citaRepository;
    private final SolicitudMapper solicitudMapper;

    public SolicitudResponseTransformerImpl(CitaRepository citaRepository, SolicitudMapper solicitudMapper) {
        this.citaRepository = citaRepository;
        this.solicitudMapper = solicitudMapper;
    }

    @Override
    public List<SolicitudResponseList> transform(List<Solicitud> solicitudes) {
        if (solicitudes == null || solicitudes.isEmpty()) {
            return Collections.emptyList();
        }

        // 1. Transformación base
        List<SolicitudResponseList> responseList = solicitudMapper.mapToSolicitudResponseList(solicitudes);

        // 2. Enriquecimiento con datos de Cita
        enrichWithCitaData(responseList, solicitudes);

        
        return responseList;
    }

    private void enrichWithCitaData(List<SolicitudResponseList> responseList, List<Solicitud> solicitudes) {
        Map<Long, SolicitudResponseList> responseMap = responseList.stream()
                .collect(Collectors.toMap(SolicitudResponseList::getIdSolicitud, Function.identity()));

        for (Solicitud solicitud : solicitudes) {
            SolicitudResponseList responseDto = responseMap.get(solicitud.getIdSolicitud());
            if (responseDto == null)
                continue;

            citaRepository.findBySolicitud(solicitud).ifPresent(cita -> {
                CitaAsociadaDto citaDto = new CitaAsociadaDto(
                        cita.getIdCita(),
                        cita.getFechaHora(),
                        cita.getAgenda() != null ? cita.getAgenda().getIdAgenda() : null);
                responseDto.setCita(citaDto);
            });
        }
    }

}