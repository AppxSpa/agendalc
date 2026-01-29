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
    private final com.agendalc.agendalc.services.mappers.saludmappers.SaludFormularioMapper saludFormularioMapper;

    public SolicitudResponseTransformerImpl(CitaRepository citaRepository, SolicitudMapper solicitudMapper, com.agendalc.agendalc.services.mappers.saludmappers.SaludFormularioMapper saludFormularioMapper) {
        this.citaRepository = citaRepository;
        this.solicitudMapper = solicitudMapper;
        this.saludFormularioMapper = saludFormularioMapper;
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

        // 3. Enriquecimiento con datos de Salud y Clases
        enrichWithExtraData(responseList, solicitudes);

        return responseList;
    }

    private void enrichWithExtraData(List<SolicitudResponseList> responseList, List<Solicitud> solicitudes) {
        Map<Long, SolicitudResponseList> responseMap = responseList.stream()
                .collect(Collectors.toMap(SolicitudResponseList::getIdSolicitud, Function.identity()));

        for (Solicitud solicitud : solicitudes) {
            SolicitudResponseList responseDto = responseMap.get(solicitud.getIdSolicitud());
            if (responseDto == null) continue;

            // Mapeo de SaludFormulario
            if (solicitud.getSaludFormulario() != null) {
                responseDto.setSaludFormularioDto(saludFormularioMapper.toDto(solicitud.getSaludFormulario()));
            }

            // Mapeo de Clases
            String clases = solicitud.getTramiteLicencias().stream()
                    .map(tl -> tl.getClaseLicencia().name())
                    .collect(Collectors.joining(", "));
            responseDto.setClases(clases);
        }
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