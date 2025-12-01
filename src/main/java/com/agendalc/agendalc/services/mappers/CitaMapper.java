package com.agendalc.agendalc.services.mappers;

import com.agendalc.agendalc.dto.CitaDto;
import com.agendalc.agendalc.dto.CitaRequest;
import com.agendalc.agendalc.dto.SolicitudAsociadaDto;
import com.agendalc.agendalc.dto.TramiteResponse;
import com.agendalc.agendalc.entities.Agenda;
import com.agendalc.agendalc.entities.BloqueHorario;
import com.agendalc.agendalc.entities.Cita;
import com.agendalc.agendalc.entities.SaludFormulario;
import com.agendalc.agendalc.entities.Solicitud;
import com.agendalc.agendalc.entities.Tramite;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CitaMapper {

    public Map<String, Object> createVariablesCorreoCita(Cita cita, String nombres) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("nombres", nombres);
        variables.put("nombreTramite", cita.nombreTramite());
        variables.put("fechaCita", cita.getFechaAgenda().toString());
        variables.put("horaCita", cita.getHoraInicioBloqueHoraio().toString());
        return variables;
    }

    public CitaDto toDto(Cita cita) {
        CitaDto dto = new CitaDto(cita);
        if (cita.getSolicitud() != null) {
            Solicitud solicitud = cita.getSolicitud();
            SolicitudAsociadaDto solicitudDto = new SolicitudAsociadaDto(
                    solicitud.getIdSolicitud(),
                    solicitud.getEstado().name(),
                    solicitud.getFechaSolicitud());
            dto.setSolicitud(solicitudDto);
        } else {
            dto.setSolicitud(null);
        }
        if (cita.getTramite() != null) {
            dto.setTramite(mapTramiteToDto(cita.getTramite()));
        } else {
            dto.setTramite(null);
        }
        return dto;
    }

    public List<CitaDto> toDtoList(List<Cita> citas) {
        return citas.stream()
                .map(this::toDto)
                .toList();
    }

    private TramiteResponse mapTramiteToDto(Tramite tramite) {
        TramiteResponse dto = new TramiteResponse();
        dto.setIdTramite(tramite.getIdTramite());
        dto.setNombreTramite(tramite.getNombre());
        dto.setDescripcionTramite(tramite.getDescripcion());
        dto.setPideDocumentos(tramite.isPideDocumentos());
        dto.setRequiereSolicitud(tramite.isRequiereSolicitud());
        // Mapear otros campos si es necesario
        return dto;
    }

    public Cita toEntity(CitaRequest request, Agenda agenda, BloqueHorario bloqueHorario,
            SaludFormulario saludFormulario, Solicitud solicitud) {
        Cita cita = new Cita();
        cita.setRut(request.getRut());
        cita.setAgenda(agenda);
        cita.setBloqueHorario(bloqueHorario);
        cita.setSaludFormulario(saludFormulario);
        cita.setSolicitud(solicitud);
        return cita;
    }
}