package com.agendalc.agendalc.services.mappers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.agendalc.agendalc.dto.DocumentosDto;
import com.agendalc.agendalc.dto.MovimientoSolicitudRequest;
import com.agendalc.agendalc.dto.MovimientosDto;
import com.agendalc.agendalc.dto.ObservacionesDto;
import com.agendalc.agendalc.dto.PersonaResponse;
import com.agendalc.agendalc.dto.SolicitudResponse;
import com.agendalc.agendalc.dto.SolicitudResponseList;
import com.agendalc.agendalc.entities.Solicitud;
import com.agendalc.agendalc.services.interfaces.ApiPersonaService;

@Component
public class SolicitudMapper {

    private final ApiPersonaService apiPersonaService;

    public SolicitudMapper(ApiPersonaService apiPersonaService) {
        this.apiPersonaService = apiPersonaService;
    }

    public List<SolicitudResponseList> mapToSolicitudResponseList(List<Solicitud> solicitudes) {
        return solicitudes.stream()
                .map(sol -> {

                    SolicitudResponseList response = new SolicitudResponseList();

                    PersonaResponse personaResponse = apiPersonaService.getPersonaInfo(sol.getRut());

                    response.setNonbre(personaResponse.getNombreCompleto());
                    response.setVrut(personaResponse.getVrut());

                    response.setIdSolicitud(sol.getIdSolicitud());
                    response.setFechaSolicitud(sol.getFechaSolicitud());
                    response.setRut(sol.getRut());
                    response.setEstadoSolicitud(sol.getEstado().name());
                    response.setIdTramite(sol.getIdTramite());
                    response.setNombreTramite(sol.getNombreTramite());

                    if (sol.getMovimientos() != null && !sol.getMovimientos().isEmpty()) {
                        response.setMovimientos(sol.getMovimientos().stream()
                                .map(mov -> {
                                    MovimientosDto movDto = new MovimientosDto();
                                    movDto.setIdMovimiento(mov.getIdMovimiento());
                                    movDto.setTipoMovimiento(mov.getTipo().name());
                                    movDto.setUsuarioResponsable(mov.getUsuarioResponsable());
                                    movDto.setFechaMovimiento(mov.getFechaMovimiento().toString());
                                    return movDto;
                                })
                                .collect(Collectors.toSet()));
                    }

                    if (sol.getObservaciones() != null && !sol.getObservaciones().isEmpty()) {
                        response.setObservaciones(sol.getObservaciones().stream().map(obs -> {
                            ObservacionesDto obsDto = new ObservacionesDto();
                            obsDto.setIdObservacion(obs.getIdObservacion());
                            obsDto.setGlosa(obs.getGlosa());
                            obsDto.setFechaObservacion(obs.getFechaObservacion().toString());
                            obsDto.setUsuarioResponsable(obs.getUsuarioResponsable());
                            obsDto.setRevisada(obs.isRevisada());
                            return obsDto;
                        }).collect(Collectors.toSet()));
                    }

                    if (sol.getDocumentosEntregados() != null && !sol.getDocumentosEntregados().isEmpty()) {
                        response.setDocumentos(sol.getDocumentosEntregados().stream()
                                .map(doc -> new DocumentosDto(doc.getIdDocumentoSolicitud(), doc.getRutaDocumento(),
                                        doc.getNombreDocumento(), doc.isAprobado()))
                                .collect(Collectors.toSet()));
                    }

                    return response;
                }).toList();
    }

    public SolicitudResponse mapSolicitudResponse(Solicitud solicitud) {
        return new SolicitudResponse(
                solicitud.getIdSolicitud(),
                solicitud.getTramite().getNombre(),
                solicitud.getTramite().getIdTramite(),
                solicitud.getRut(),
                solicitud.getEstado().toString());
    }

     public MovimientoSolicitudRequest mapMovimientoSolicitudRequest(Long idSolicitud, Integer tipMovimiento,
            String usuario, String asignadoA) {
        MovimientoSolicitudRequest movimiento = new MovimientoSolicitudRequest();
        movimiento.setIdSolicitud(idSolicitud);
        movimiento.setTipoMovimiento(tipMovimiento);
        movimiento.setLoginUsuario(usuario);
        movimiento.setAsignadoA(asignadoA);

        return movimiento;
    }

}
