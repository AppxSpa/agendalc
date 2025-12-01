package com.agendalc.agendalc.services.mappers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.agendalc.agendalc.dto.DocumentosDto;
import com.agendalc.agendalc.dto.MovimientoSolicitudRequest;
import com.agendalc.agendalc.dto.MovimientosDto;
import com.agendalc.agendalc.dto.ObservacionesDto;
import com.agendalc.agendalc.dto.PersonaResponse;
import com.agendalc.agendalc.dto.RechazoDto;
import com.agendalc.agendalc.dto.SolicitudRequest;
import com.agendalc.agendalc.dto.SolicitudResponse;
import com.agendalc.agendalc.dto.SolicitudResponseList;
import com.agendalc.agendalc.entities.Documento;
import com.agendalc.agendalc.entities.SolcitudRechazo;
import com.agendalc.agendalc.entities.Tramite;
import com.agendalc.agendalc.entities.MovimientoSolicitud;
import com.agendalc.agendalc.entities.ObservacionSolicitud;
import com.agendalc.agendalc.entities.Solicitud;
import com.agendalc.agendalc.repositories.DocumentoRepository;
import com.agendalc.agendalc.services.interfaces.ApiPersonaService;

@Component
public class SolicitudMapper {

    private final ApiPersonaService apiPersonaService;
    private final DocumentoRepository documentoRepository;

    public SolicitudMapper(ApiPersonaService apiPersonaService, DocumentoRepository documentoRepository) {
        this.apiPersonaService = apiPersonaService;
        this.documentoRepository = documentoRepository;
    }

    public List<SolicitudResponseList> mapToSolicitudResponseList(List<Solicitud> solicitudes) {
        return solicitudes.stream()
                .map(this::solicitudToSolicitudResponseList)
                .toList();
    }

    private SolicitudResponseList solicitudToSolicitudResponseList(Solicitud sol) {
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

        setMovimientos(sol, response);
        setRechazo(sol, response);
        setObservaciones(sol, response);
        setDocumentos(sol, response);

        return response;
    }

    private void setMovimientos(Solicitud sol, SolicitudResponseList response) {
        if (sol.getMovimientos() != null && !sol.getMovimientos().isEmpty()) {
            response.setMovimientos(sol.getMovimientos().stream()
                    .map(this::movimientoToDto)
                    .collect(Collectors.toSet()));
        }
    }

    private MovimientosDto movimientoToDto(MovimientoSolicitud mov) {
        MovimientosDto movDto = new MovimientosDto();
        movDto.setIdMovimiento(mov.getIdMovimiento());
        movDto.setTipoMovimiento(mov.getTipo().name());
        movDto.setUsuarioResponsable(mov.getUsuarioResponsable());
        movDto.setFechaMovimiento(mov.getFechaMovimiento().toString());
        return movDto;
    }

    private void setRechazo(Solicitud sol, SolicitudResponseList response) {
        if (sol.getEstado() == Solicitud.EstadoSolicitud.RECHAZADA && sol.getSolcitudRehazo() != null) {
            RechazoDto rechazoDto = new RechazoDto();
            rechazoDto.setMotivoRechazo(sol.getMotivoRechazo());
            response.setRechazo(rechazoDto);
        }
    }

    private void setObservaciones(Solicitud sol, SolicitudResponseList response) {
        if (sol.getObservaciones() != null && !sol.getObservaciones().isEmpty()) {
            response.setObservaciones(sol.getObservaciones().stream()
                    .map(this::observacionToDto)
                    .collect(Collectors.toSet()));
        }
    }

    private ObservacionesDto observacionToDto(ObservacionSolicitud obs) {
        ObservacionesDto obsDto = new ObservacionesDto();
        obsDto.setIdObservacion(obs.getIdObservacion());
        obsDto.setGlosa(obs.getGlosa());
        obsDto.setFechaObservacion(obs.getFechaObservacion().toString());
        obsDto.setUsuarioResponsable(obs.getUsuarioResponsable());
        obsDto.setRevisada(obs.isRevisada());
        return obsDto;
    }

    private void setDocumentos(Solicitud sol, SolicitudResponseList response) {
        List<Documento> documentos = documentoRepository.findByOrigenIdAndOrigenTipo(sol.getIdSolicitud(), "SOLICITUD");
        if (documentos != null && !documentos.isEmpty()) {
            response.setDocumentos(documentos.stream()
                    .map(this::documentoToDto)
                    .collect(Collectors.toSet()));
        }
    }

    private DocumentosDto documentoToDto(Documento doc) {
        String nombreDocumento = doc.getDocumentosTramite() != null ? doc.getDocumentosTramite().getNombreDocumento()
                : doc.getNombreArchivo();
        return new DocumentosDto(doc.getId(), doc.getPathStorage(), nombreDocumento, doc.isAprobado());
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

    public Solicitud toEntity(SolicitudRequest request, Tramite tramite) {
        Solicitud solicitud = new Solicitud();
        solicitud.setRut(request.getRut());
        solicitud.setTramite(tramite);
        return solicitud;
    }

    public SolcitudRechazo toRechazoEntity(String motivoRechazo, Solicitud solicitud) {
        SolcitudRechazo solcitudRehazo = new SolcitudRechazo();
        solcitudRehazo.setMotivoRechazo(motivoRechazo);
        solcitudRehazo.setSolicitud(solicitud);
        return solcitudRehazo;
    }

}
