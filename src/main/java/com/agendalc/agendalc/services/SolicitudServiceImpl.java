package com.agendalc.agendalc.services;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.agendalc.agendalc.dto.MovimientoSolicitudRequest;
import com.agendalc.agendalc.dto.SolicitudRequest;
import com.agendalc.agendalc.dto.SolicitudResponse;
import com.agendalc.agendalc.dto.SolicitudResponseList;
import com.agendalc.agendalc.entities.Cita;
import com.agendalc.agendalc.entities.MovimientoSolicitud;
import com.agendalc.agendalc.entities.SaludFormulario;
import com.agendalc.agendalc.entities.SolcitudRechazo;
import com.agendalc.agendalc.entities.Solicitud;
import com.agendalc.agendalc.entities.Tramite;
import com.agendalc.agendalc.entities.MovimientoSolicitud.TipoMovimiento;
import com.agendalc.agendalc.entities.Solicitud.EstadoSolicitud;
import com.agendalc.agendalc.repositories.CitaRepository;
import com.agendalc.agendalc.repositories.SaludFormularioRepository;
import com.agendalc.agendalc.repositories.SolicitudRechazoRepository;
import com.agendalc.agendalc.repositories.SolicitudRepository;
import com.agendalc.agendalc.repositories.TramiteRepository;
import com.agendalc.agendalc.services.interfaces.MovimientoSolicitudService;
import com.agendalc.agendalc.services.interfaces.NotificacionService;
import com.agendalc.agendalc.services.interfaces.SolicitudDocumentoService;
import com.agendalc.agendalc.services.interfaces.SolicitudService;
import com.agendalc.agendalc.services.interfaces.SolicitudResponseTransformer;
import com.agendalc.agendalc.services.mappers.SolicitudMapper;
import com.agendalc.agendalc.utils.RepositoryUtils;

@Service
public class SolicitudServiceImpl implements SolicitudService {

    private final SolicitudRepository solicitudRepository;
    private final TramiteRepository tramiteRepository;
    private final MovimientoSolicitudService movimientoSolicitudService;
    private final SaludFormularioRepository saludFormularioRepository;
    private final CitaRepository citaRepository;
    private final SolicitudMapper solicitudMapper;
    private final NotificacionService notificacionService;
    private final SolicitudRechazoRepository solicitudRechazoRepository;
    private final SolicitudResponseTransformer responseTransformer;
    private final SolicitudDocumentoService solicitudDocumentoService;

    public SolicitudServiceImpl(SolicitudRepository solicitudCitaRepository,
            TramiteRepository tramiteRepository,
            MovimientoSolicitudService movimientoSolicitudService,
            SaludFormularioRepository saludFormularioRepository, CitaRepository citaRepository,
            SolicitudMapper solicitudMapper, NotificacionService notificacionService,
            SolicitudRechazoRepository solicitudRechazoRepository,
            SolicitudResponseTransformer responseTransformer,
            SolicitudDocumentoService solicitudDocumentoService) {
        this.solicitudRepository = solicitudCitaRepository;
        this.tramiteRepository = tramiteRepository;
        this.movimientoSolicitudService = movimientoSolicitudService;
        this.saludFormularioRepository = saludFormularioRepository;
        this.citaRepository = citaRepository;
        this.solicitudMapper = solicitudMapper;
        this.notificacionService = notificacionService;
        this.solicitudRechazoRepository = solicitudRechazoRepository;
        this.responseTransformer = responseTransformer;
        this.solicitudDocumentoService = solicitudDocumentoService;
    }

    @Override
    public List<SolicitudResponseList> getSolicitudes(int year) {
        List<Solicitud> solicitudes = solicitudRepository
                .findByFechaSolicitudYearWithMovimientosOrdered(year);

        return responseTransformer.transform(solicitudes).stream()
                .filter(sol -> sol.getEstadoSolicitud().equals(EstadoSolicitud.PENDIENTE.toString()))
                .toList();
    }

    @Transactional
    @Override
    public void assignOrDerivateSolicitud(Long idSolicitud, String loginUsuario, String asignadoA, int tipo) {
        Solicitud solicitud = getSolicitudById(idSolicitud);

        solicitud.setEstado(EstadoSolicitud.ASIGNADA);
        solicitud.setAsignadoA(asignadoA);

        MovimientoSolicitudRequest movimiento = solicitudMapper.mapMovimientoSolicitudRequest(idSolicitud, tipo,
                loginUsuario,
                asignadoA);

        movimientoSolicitudService.createMovimientoSolicitud(movimiento);

        solicitudRepository.save(solicitud);
    }

    @Transactional
    @Override
    public void finishSolicitudById(Long idSolicitud, String loginUsuario) {
        Solicitud solicitud = getSolicitudById(idSolicitud);

        actualizarEstadoYCrearMovimiento(solicitud, EstadoSolicitud.FINALIZADA, loginUsuario,
                TipoMovimiento.FINALIZACION);

        solicitudRepository.save(solicitud);
    }

    @Override
    public List<SolicitudResponseList> getSolicitudesByRut(Integer rut) {
        List<Solicitud> solicitudes = solicitudRepository.findByRut(rut);
        return responseTransformer.transform(solicitudes);
    }

    @Override
    @Transactional
    public SolicitudResponse createSolicitud(SolicitudRequest request) throws IOException {
        Tramite tramite = getTramiteById(request.getIdTramite());
        Solicitud solicitud = solicitudMapper.toEntity(request, tramite);

        asociarSaludFormulario(solicitud, request.getIdSaludFormulario());

        MovimientoSolicitud primerMovimiento = firstMovement(solicitud, TipoMovimiento.CREACION);
        solicitud.addMovimiento(primerMovimiento);

        Solicitud savedSolicitud = solicitudRepository.save(solicitud);

        if (request.getDocumentos() != null && !request.getDocumentos().isEmpty()) {
            solicitudDocumentoService.guardarDocumentosNuevos(savedSolicitud, tramite, request.getDocumentos());
        }

        asociarCita(savedSolicitud, request.getIdCita());

        notificacionService.enviarNotificacionSolicitudCreada(savedSolicitud);

        return solicitudMapper.mapSolicitudResponse(savedSolicitud);
    }

    private void asociarSaludFormulario(Solicitud solicitud, Long idSaludFormulario) {
        if (idSaludFormulario != null) {
            SaludFormulario saludFormulario = RepositoryUtils.findOrThrow(
                    saludFormularioRepository.findById(idSaludFormulario),
                    "SaludFormulario no encontrado con id: " + idSaludFormulario);
            solicitud.setSaludFormulario(saludFormulario);
        }
    }

    private void asociarCita(Solicitud solicitud, Long idCita) {
        if (idCita != null) {
            Cita cita = RepositoryUtils.findOrThrow(citaRepository.findById(idCita),
                    "Cita no encontrada con id: " + idCita);
            cita.setSolicitud(solicitud);
            citaRepository.save(cita);
        }
    }

    private Tramite getTramiteById(Long idTramite) {
        return RepositoryUtils.findOrThrow(tramiteRepository.findById(idTramite),
                String.format("No se encontró el tramite %d", idTramite));
    }

    private MovimientoSolicitud firstMovement(Solicitud solicitud, TipoMovimiento tipo) {
        return new MovimientoSolicitud(
                solicitud,
                tipo,
                null,
                null);
    }

    @Override
    public List<SolicitudResponseList> getSolicitudesByRutFunc(Integer rut) {
        List<Solicitud> solicitudes = solicitudRepository.findByAsignadoA(rut.toString());
        return responseTransformer.transform(solicitudes);
    }

    private Solicitud getSolicitudById(Long idSolicitud) {
        return RepositoryUtils.findOrThrow(solicitudRepository.findById(idSolicitud),
                String.format("No se encontró la solicitud %d", idSolicitud));
    }

    @Override
    public List<SolicitudResponseList> getSolicitudesBetweenDatesAndState(LocalDate fechaInicio, LocalDate fechaFin,
            EstadoSolicitud estadoSolicitud) {
        List<Solicitud> solicitudes = solicitudRepository.findByFechaSolicitudBetween(fechaInicio, fechaFin);

        solicitudes = filtrarPorEstadoSolicitud(estadoSolicitud, solicitudes);

        return responseTransformer.transform(solicitudes);
    }

    @Override
    @Transactional
    public void replaceFile(Long idSolicitud, Long idDocumento, MultipartFile file, String loginUsuario)
            throws IOException {

        Solicitud solicitud = getSolicitudById(idSolicitud);

        solicitudDocumentoService.reemplazarDocumento(idSolicitud, idDocumento, file);

        solicitud.setEstado(EstadoSolicitud.RESPONDIDA);

        registrarMovimientoReemplazo(idSolicitud, loginUsuario);

        solicitudRepository.save(solicitud);
    }

    private void registrarMovimientoReemplazo(Long idSolicitud, String loginUsuario) {
        MovimientoSolicitudRequest movimiento = solicitudMapper.mapMovimientoSolicitudRequest(idSolicitud,
                TipoMovimiento.OBSERVACION_RESPONDIDA.ordinal(),
                loginUsuario,
                null);
        movimientoSolicitudService.createMovimientoSolicitud(movimiento);
    }

    @Override
    public SolicitudResponse aprobeSolicitud(Long idSolicitud, String loginUsuario) {
        Solicitud solicitud = getSolicitudById(idSolicitud);

        actualizarEstadoYCrearMovimiento(solicitud, EstadoSolicitud.APROBADA, loginUsuario, TipoMovimiento.APROBACION);

        solicitud = solicitudRepository.save(solicitud);

        notificacionService.enviarNotificacionSolicitudAprobada(solicitud);

        return solicitudMapper.mapSolicitudResponse(solicitud);
    }

    @Override
    public SolicitudResponse rejectSolicitu(Long idSolicitud, String loginUsuario, String motivoRechazo) {
        Solicitud solicitud = getSolicitudById(idSolicitud);

        actualizarEstadoYCrearMovimiento(solicitud, EstadoSolicitud.RECHAZADA, loginUsuario, TipoMovimiento.RECHAZO);

        SolcitudRechazo solcitudRehazo = solicitudMapper.toRechazoEntity(motivoRechazo, solicitud);
        solicitudRechazoRepository.save(solcitudRehazo);

        solicitud = solicitudRepository.save(solicitud);

        return solicitudMapper.mapSolicitudResponse(solicitud);
    }

    private void actualizarEstadoYCrearMovimiento(Solicitud solicitud, EstadoSolicitud nuevoEstado, String loginUsuario,
            TipoMovimiento tipoMovimiento) {
        solicitud.setEstado(nuevoEstado);

        MovimientoSolicitudRequest movimiento = solicitudMapper.mapMovimientoSolicitudRequest(
                solicitud.getIdSolicitud(), tipoMovimiento.ordinal(),
                loginUsuario,
                null);
        movimientoSolicitudService.createMovimientoSolicitud(movimiento);
    }

    @Override
    public void delteSolicitud(Long idSolicitud) {
        Solicitud solicitud = getSolicitudById(idSolicitud);
        solicitudRepository.delete(solicitud);
    }

    private List<Solicitud> filtrarPorEstadoSolicitud(EstadoSolicitud estadoSolicitud, List<Solicitud> solicitudes) {

        String strEstado = estadoSolicitud != null ? estadoSolicitud.toString() : null;

        return switch (strEstado) {
            case "FINALIZADA" -> solicitudes.stream()
                    .filter(sol -> sol.getEstado().equals(EstadoSolicitud.APROBADA)
                            || sol.getEstado().equals(EstadoSolicitud.RECHAZADA)
                            || sol.getEstado().equals(EstadoSolicitud.FINALIZADA))
                    .toList();

            default -> solicitudes.stream()
                    .filter(sol -> !sol.getEstado().equals(EstadoSolicitud.APROBADA)
                            || !sol.getEstado().equals(EstadoSolicitud.RECHAZADA)
                            || !sol.getEstado().equals(EstadoSolicitud.FINALIZADA))
                    .toList();
        };

    }
}