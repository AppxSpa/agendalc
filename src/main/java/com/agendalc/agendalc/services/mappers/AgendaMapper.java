package com.agendalc.agendalc.services.mappers;

import java.util.List;

import org.springframework.stereotype.Component;

import com.agendalc.agendalc.dto.AgendaResponse;
import com.agendalc.agendalc.dto.BloqueHorarioResponse;
import com.agendalc.agendalc.dto.DocumentosTramiteResponse;
import com.agendalc.agendalc.dto.TramiteResponse;
import com.agendalc.agendalc.entities.Agenda;

@Component
public class AgendaMapper {

    public List<AgendaResponse> toAgendaResponseList(List<Agenda> agendas) {
        return agendas.stream().map(agenda -> {
            AgendaResponse agendaResponse = new AgendaResponse();
            agendaResponse.setIdAgenda(agenda.getIdAgenda());
            agendaResponse.setFechaAgenda(agenda.getFecha());

            TramiteResponse tramiteResponse = new TramiteResponse();
            tramiteResponse.setIdTramite(agenda.getIdTramite());
            tramiteResponse.setNombreTramite(agenda.getNombreTramite());

            // Mapear documentos requeridos
            List<DocumentosTramiteResponse> documentosRequeridos = agenda.getTramite().getDocumentosRequeridos()
                    .stream().map(documento -> {
                        DocumentosTramiteResponse dto = new DocumentosTramiteResponse();
                        dto.setId(documento.getIdDocumento());
                        dto.setNombre(documento.getNombreDocumento());
                        return dto;
                    }).toList();

            tramiteResponse.setDocumentosRequeridos(documentosRequeridos);

            // Mapear bloques horarios
            List<BloqueHorarioResponse> bloquesHorarios = agenda.getBloquesHorarios().stream().map(bloque -> {
                BloqueHorarioResponse dto = new BloqueHorarioResponse();
                dto.setId(bloque.getIdBloque());
                dto.setHoraFin(bloque.getHoraFin().toString());
                dto.setHoraInicio(bloque.getHoraInicio().toString());
                dto.setCuposDisponibles(bloque.getCuposDisponibles());
                return dto;
            }).toList();

            agendaResponse.setTramite(tramiteResponse);
            agendaResponse.setBloqueHorario(bloquesHorarios);

            return agendaResponse;
        }).toList();
    }

}
