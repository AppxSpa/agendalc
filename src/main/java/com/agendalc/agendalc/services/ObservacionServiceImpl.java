package com.agendalc.agendalc.services;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agendalc.agendalc.dto.ObservacionRequest;
import com.agendalc.agendalc.entities.MovimientoSolicitud;
import com.agendalc.agendalc.entities.ObservacionSolicitud;
import com.agendalc.agendalc.entities.Solicitud;
import com.agendalc.agendalc.entities.Solicitud.EstadoSolicitud;
import com.agendalc.agendalc.repositories.DocumentoRepository;
import com.agendalc.agendalc.repositories.ObservacionSolicitudRepository;
import com.agendalc.agendalc.repositories.SolicitudRepository;
import com.agendalc.agendalc.services.interfaces.ApiMailService;
import com.agendalc.agendalc.services.interfaces.ApiPersonaService;
import com.agendalc.agendalc.services.interfaces.ObservacionSolicitudService;
import com.agendalc.agendalc.utils.RepositoryUtils;

@Service
public class ObservacionServiceImpl implements ObservacionSolicitudService {

    private final SolicitudRepository solicitudRepository;

    private final ObservacionSolicitudRepository observacionSolicitudRepository;
    private final DocumentoRepository documentoRepository;
    private final ApiMailService apiMailService;
    private final ApiPersonaService apiPersonaService;

    public ObservacionServiceImpl(SolicitudRepository solicitudRepository,
            ObservacionSolicitudRepository observacionSolicitudRepository,
            DocumentoRepository documentoRepository,
            ApiMailService apiMailService,
            ApiPersonaService apiPersonaService) {
        this.solicitudRepository = solicitudRepository;
        this.observacionSolicitudRepository = observacionSolicitudRepository;
        this.documentoRepository = documentoRepository;
        this.apiMailService = apiMailService;
        this.apiPersonaService = apiPersonaService;
    }

    @Override
    @Transactional
    public void createObservacion(ObservacionRequest request) {
        Solicitud solicitud = getSolicitud(request.getIdSolicitud());

        ObservacionSolicitud observacion = new ObservacionSolicitud(solicitud, request.getObservacion(),
                request.getLoginUsuario());

        MovimientoSolicitud movimiento = new MovimientoSolicitud(solicitud,
                MovimientoSolicitud.TipoMovimiento.OBSERVACION_AGREGADA, request.getLoginUsuario(),
                request.getLoginUsuario());

        request.getDocumentosAprobados().forEach((id, aprobado) ->

        documentoRepository.findById(id)
                .ifPresent(doc -> {
                    doc.setAprobado(aprobado);
                    documentoRepository.save(doc);
                })

        );

        solicitud.addObservacion(observacion);
        solicitud.addMovimiento(movimiento);
        changeStateSolicitud(solicitud);

        solicitudRepository.save(solicitud);

        Map<String, Object> variables = Map.of(
                "nombres", getNombresPersonaPorSolicitud(solicitud),
                "idSolicitud", solicitud.getIdSolicitud(),
                "observacion", request.getObservacion(),
                "urlPlataforma", "https://dev.appx.cl/");

        // Enviar correo de notificación
        apiMailService.sendEmail(getEmailPersonaPorSolicitud(solicitud), "Observación agregada",
                "obs-solicitud-template", variables);

    }

    private Solicitud getSolicitud(Long idSolicitud) {
        return RepositoryUtils.findOrThrow(solicitudRepository.findById(idSolicitud),
                String.format("No se encontró la solicitud %d", idSolicitud));

    }

    private Solicitud changeStateSolicitud(Solicitud solicitud) {
        solicitud.setEstado(EstadoSolicitud.OBSERVADA);
        return solicitud;
    }

    @Override
    public void changeCkeckObservacion(Long idObservacion) {
        ObservacionSolicitud observacion = getObservacion(idObservacion);

        observacion.setRevisada(true);

        observacionSolicitudRepository.save(observacion);
    }

    private ObservacionSolicitud getObservacion(Long idObservacion) {

        return RepositoryUtils.findOrThrow(observacionSolicitudRepository.findById(idObservacion),
                String.format("No se encontró la observacion %d", idObservacion));

    }

    private String getEmailPersonaPorSolicitud(Solicitud solicitud) {
        Integer rut = solicitud.getRut();
        return apiPersonaService.getPersonaInfo(rut).getEmail();
    }

    private String getNombresPersonaPorSolicitud(Solicitud solicitud) {
        Integer rut = solicitud.getRut();
        return apiPersonaService.getPersonaInfo(rut).getNombres();
    }

}
