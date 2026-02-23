package com.agendalc.agendalc.services.mappers;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.agendalc.agendalc.config.AppProperties;
import com.agendalc.agendalc.dto.BloqueHorarioResponse;
import com.agendalc.agendalc.dto.CitaDto;
import com.agendalc.agendalc.dto.CitaRequest;
import com.agendalc.agendalc.dto.PersonaResponse;
import com.agendalc.agendalc.dto.SolicitudAsociadaDto;
import com.agendalc.agendalc.dto.TramiteResponse;
import com.agendalc.agendalc.entities.Agenda;
import com.agendalc.agendalc.entities.BloqueHorario;
import com.agendalc.agendalc.entities.Cita;
import com.agendalc.agendalc.entities.SaludFormulario;
import com.agendalc.agendalc.entities.Solicitud;
import com.agendalc.agendalc.repositories.AsistenciaRepository;
import com.agendalc.agendalc.services.interfaces.ApiPersonaService;

@Component
public class CitaMapper {

    private final ApiPersonaService apiPersonaService;
    private final AppProperties appProperties;
    private final AsistenciaRepository asistenciaCitaRepository;

    public CitaMapper(ApiPersonaService apiPersonaService, AppProperties appProperties, AsistenciaRepository asistenciaCitaRepository) {
        this.apiPersonaService = apiPersonaService;
        this.appProperties = appProperties;
        this.asistenciaCitaRepository = asistenciaCitaRepository;
    }

    public CitaDto toDto(Cita cita) {
        CitaDto dto = new CitaDto();
        dto.setIdCita(cita.getIdCita());
        dto.setRut(cita.getRut());

        PersonaResponse persona = apiPersonaService.getPersonaInfo(cita.getRut());
        if (persona != null) {
            dto.setDv(persona.getVrut());
            dto.setNombres(persona.getNombres());
            dto.setPaterno(persona.getPaterno());
            dto.setMaterno(persona.getMaterno());
        }

        dto.setIdAgenda(cita.getAgenda().getIdAgenda());
        dto.setFechaHora(cita.getAgenda().getFecha());
        dto.setEstadoAsistencia(isAsistente(cita.getIdCita()) ? "Asistente" : "No Asistente");


        if (cita.getSolicitud() != null) {
            Solicitud solicitud = cita.getSolicitud();
            SolicitudAsociadaDto solicitudDto = new SolicitudAsociadaDto(
                solicitud.getIdSolicitud(),
                solicitud.getEstado().name(),
                solicitud.getFechaSolicitud()
            );
            dto.setSolicitud(solicitudDto);
        }

        if (cita.getTramite() != null) {
            TramiteResponse tramiteDto = new TramiteResponse();
            tramiteDto.setIdTramite(cita.getTramite().getIdTramite());
            tramiteDto.setNombreTramite(cita.getTramite().getNombre());
            tramiteDto.setDescripcionTramite(cita.getTramite().getDescripcion());
            dto.setTramite(tramiteDto);
        }

        if (cita.getBloqueHorario() != null) {
            BloqueHorarioResponse bloqueHorarioDto = new BloqueHorarioResponse(
                cita.getBloqueHorario().getIdBloque(),
                cita.getBloqueHorario().getHoraInicio().toString(),
                cita.getBloqueHorario().getHoraFin().toString(),
                cita.getBloqueHorario().getCuposDisponibles()
            );
            dto.setBloqueHorario(bloqueHorarioDto);
        }

        return dto;
    }

    public List<CitaDto> toDtoList(List<Cita> citas) {
        return citas.stream().map(this::toDto).toList();
    }

    public Cita toEntity(CitaRequest citaRequest, Agenda agenda, BloqueHorario bloqueHorario, SaludFormulario saludFormulario, Solicitud solicitud) {
        Cita cita = new Cita();
        cita.setRut(citaRequest.getRut());
        cita.setAgenda(agenda);
        cita.setBloqueHorario(bloqueHorario);
        cita.setSaludFormulario(saludFormulario);
        cita.setSolicitud(solicitud);
        return cita;
    }

    public Map<String, Object> createVariablesCorreoCita(Cita cita, String nombres) {
        return Map.of(
                "nombres", nombres,
                "fecha", cita.getFechaAgenda().toString(),
                "hora", cita.getHoraInicioBloqueHoraio().toString(),
                "tramite", cita.nombreTramite(),
                "urlPlataforma", appProperties.getPlataformaUrl()
        );
    }

    private boolean isAsistente(Long citaId) {
        return asistenciaCitaRepository.existsByCitaIdCita(citaId);
    }
}