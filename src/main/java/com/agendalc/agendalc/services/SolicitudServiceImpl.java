package com.agendalc.agendalc.services;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.agendalc.agendalc.dto.CitaAsociadaDto;
import com.agendalc.agendalc.dto.DocumentosSubidosRequest;
import com.agendalc.agendalc.dto.MovimientoSolicitudRequest;
import com.agendalc.agendalc.dto.SolicitudRequest;
import com.agendalc.agendalc.dto.SolicitudResponse;
import com.agendalc.agendalc.dto.SolicitudResponseList;
import com.agendalc.agendalc.entities.Cita;
import com.agendalc.agendalc.entities.DocumentosEliminados;
import com.agendalc.agendalc.entities.DocumentosSolicitud;
import com.agendalc.agendalc.entities.DocumentosTramite;
import com.agendalc.agendalc.entities.MovimientoSolicitud;
import com.agendalc.agendalc.entities.SaludFormulario;
import com.agendalc.agendalc.entities.Solicitud;
import com.agendalc.agendalc.entities.Tramite;
import com.agendalc.agendalc.entities.MovimientoSolicitud.TipoMovimiento;
import com.agendalc.agendalc.entities.Solicitud.EstadoSolicitud;
import com.agendalc.agendalc.repositories.CitaRepository;
import com.agendalc.agendalc.repositories.DocumentoSolicitudRepository;
import com.agendalc.agendalc.repositories.DocumentosEliminadosRepository;
import com.agendalc.agendalc.repositories.DocumentosTramiteRepository;
import com.agendalc.agendalc.repositories.SaludFormularioRepository;
import com.agendalc.agendalc.repositories.SolicitudRepository;
import com.agendalc.agendalc.repositories.TramiteRepository;
import com.agendalc.agendalc.services.interfaces.ArchivoService;
import com.agendalc.agendalc.services.interfaces.MovimientoSolicitudService;
import com.agendalc.agendalc.services.interfaces.SolicitudService;
import com.agendalc.agendalc.services.mappers.SolicitudMapper;
import com.agendalc.agendalc.utils.RepositoryUtils;
import jakarta.persistence.EntityNotFoundException;

@Service
public class SolicitudServiceImpl implements SolicitudService {

    private final SolicitudRepository solicitudRepository;
    private final TramiteRepository tramiteRepository;
    private final ArchivoService archivoService;
    private final DocumentosTramiteRepository documentosTramiteRepository;
    private final MovimientoSolicitudService movimientoSolicitudService;
    private final DocumentoSolicitudRepository documentoSolicitudRepository;
    private final DocumentosEliminadosRepository documentosEliminadosRepository;
    private final SaludFormularioRepository saludFormularioRepository; // Inyectado
    private final CitaRepository citaRepository;
    private final SolicitudMapper solicitudMapper;

    public SolicitudServiceImpl(SolicitudRepository solicitudCitaRepository, ArchivoService archivoService,
            TramiteRepository tramiteRepository, DocumentosTramiteRepository documentosTramiteRepository,
            MovimientoSolicitudService movimientoSolicitudService,
            DocumentoSolicitudRepository documentoSolicitudRepository,
            DocumentosEliminadosRepository documentosEliminadosRepository,
            SaludFormularioRepository saludFormularioRepository, CitaRepository citaRepository,
            SolicitudMapper solicitudMapper) { // Añadido al constructor
        this.solicitudRepository = solicitudCitaRepository;
        this.tramiteRepository = tramiteRepository;
        this.archivoService = archivoService;
        this.documentosTramiteRepository = documentosTramiteRepository;
        this.movimientoSolicitudService = movimientoSolicitudService;
        this.documentoSolicitudRepository = documentoSolicitudRepository;
        this.documentosEliminadosRepository = documentosEliminadosRepository;
        this.saludFormularioRepository = saludFormularioRepository; // Asignado
        this.citaRepository = citaRepository;
        this.solicitudMapper = solicitudMapper;
    }

    @Override
    public List<SolicitudResponseList> getSolicitudes(int year) {
        List<Solicitud> solicitudes = solicitudRepository
                .findByFechaSolicitudYearWithMovimientosOrdered(year);

        return solicitudMapper.mapToSolicitudResponseList(solicitudes).stream()
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

        MovimientoSolicitudRequest movimiento = solicitudMapper.mapMovimientoSolicitudRequest(idSolicitud, 6,
                loginUsuario,
                null);

        movimientoSolicitudService.createMovimientoSolicitud(movimiento);

        solicitud.setEstado(EstadoSolicitud.FINALIZADA);
        solicitudRepository.save(solicitud);
    }

    @Override
    public List<SolicitudResponseList> getSolicitudesByRut(Integer rut) {
        List<Solicitud> solicitudes = solicitudRepository.findByRut(rut);
        List<SolicitudResponseList> responseList = solicitudMapper.mapToSolicitudResponseList(solicitudes);

        // Create a map for quick lookup
        Map<Long, SolicitudResponseList> responseMap = new HashMap<>();
        for (SolicitudResponseList res : responseList) {
            responseMap.put(res.getIdSolicitud(), res);
        }

        for (Solicitud solicitud : solicitudes) {
            citaRepository.findBySolicitud(solicitud).ifPresent(cita -> {
                SolicitudResponseList dto = responseMap.get(solicitud.getIdSolicitud());
                if (dto != null) {
                    CitaAsociadaDto citaDto = new CitaAsociadaDto(
                        cita.getIdCita(),
                        cita.getFechaHora(),
                        cita.getAgenda().getIdAgenda()
                    );
                    dto.setCita(citaDto);
                }
            });
        }

        return responseList;
    }

    @Override
    @Transactional
    public SolicitudResponse createSolicitud(SolicitudRequest request) throws IOException {
        Tramite tramite = getTramiteById(request.getIdTramite());

        Solicitud solicitud = new Solicitud();
        solicitud.setRut(request.getRut());
        solicitud.setTramite(tramite);

        // Lógica para asociar SaludFormulario
        if (request.getIdSaludFormulario() != null) {
            SaludFormulario saludFormulario = saludFormularioRepository.findById(request.getIdSaludFormulario())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "SaludFormulario no encontrado con id: " + request.getIdSaludFormulario()));
            solicitud.setSaludFormulario(saludFormulario);
        }

        MovimientoSolicitud primerMovimiento = firstMovement(solicitud, TipoMovimiento.CREACION);

        if (request.getDocumentos() != null && !request.getDocumentos().isEmpty()) {
            if (solicitud.getDocumentosEntregados() == null) {
                solicitud.setDocumentosEntregados(new ArrayList<>());
            }

            for (DocumentosSubidosRequest docRequest : request.getDocumentos()) {
                MultipartFile file = docRequest.getFile();

                if (file != null && !file.isEmpty()) {
                    DocumentosTramite tipoDocumentoRequerido = documentosTramiteRepository
                            .findById(docRequest.getIdTipoDocumento())
                            .orElseThrow(() -> new EntityNotFoundException("Tipo de documento requerido con ID "
                                    + docRequest.getIdTipoDocumento() + " no encontrado."));

                    String nombreGuardado = archivoService.guardarArchivo(file);
                    Path rutaCompleta = archivoService.getRutaCompletaArchivo(nombreGuardado);

                    DocumentosSolicitud documento = new DocumentosSolicitud(
                            solicitud,
                            tipoDocumentoRequerido,
                            rutaCompleta.toString());

                    solicitud.getDocumentosEntregados().add(documento);
                    documento.setSolicitud(solicitud);
                }
            }
        }

        solicitud.addMovimiento(primerMovimiento);

        solicitud = solicitudRepository.save(solicitud);

        // Asociar la solicitud a la cita, si se proporciona idCita
        if (request.getIdCita() != null) {
            Cita cita = citaRepository.findById(request.getIdCita())
                    .orElseThrow(
                            () -> new EntityNotFoundException("Cita no encontrada con id: " + request.getIdCita()));
            cita.setSolicitud(solicitud);
            citaRepository.save(cita);
        }

        return solicitudMapper.mapSolicitudResponse(solicitud);

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

        return solicitudMapper.mapToSolicitudResponseList(solicitudes);

    }

    private Solicitud getSolicitudById(Long idSolicitud) {

        return RepositoryUtils.findOrThrow(solicitudRepository.findById(idSolicitud),
                String.format("No se encontró la solicitud %d", idSolicitud));
    }

    @Override
    public List<SolicitudResponseList> getSolicitudesBetweenDatesAndState(LocalDate fechaInicio, LocalDate fechaFin,
            EstadoSolicitud estadoSolicitud) {

        List<Solicitud> solicitudes = solicitudRepository.findByFechaSolicitudBetween(fechaInicio, fechaFin).stream()
                .filter(s -> EstadoSolicitud.PENDIENTE.equals(estadoSolicitud)
                        ? EstadoSolicitud.PENDIENTE.equals(s.getEstado())
                        : !EstadoSolicitud.PENDIENTE.equals(s.getEstado()))
                .toList();

        return solicitudMapper.mapToSolicitudResponseList(solicitudes);
    }

    @Override
    @Transactional
    public void replaceFile(Long idSolicitud, Long idTipo, MultipartFile file, String loginUsuario) throws IOException {

        Solicitud solicitud = getSolicitudById(idSolicitud);

        DocumentosSolicitud docReplace = RepositoryUtils.findOrThrow(documentoSolicitudRepository.findById(idTipo),
                String.format("Documento tipo %d no encontrado para la solicitud", idTipo));

        DocumentosTramite tipoDocumentoRequerido = getDocumentosTramiteById(
                docReplace.getDocumentosTramite().getIdDocumento());

        String nombreGuardado = archivoService.guardarArchivo(file);
        Path rutaCompleta = archivoService.getRutaCompletaArchivo(nombreGuardado);

        solicitud.setEstado(EstadoSolicitud.RESPONDIDA);

        documentosEliminadosRepository.save(new DocumentosEliminados(rutaCompleta.toString(), idSolicitud, idTipo));

        documentoSolicitudRepository.deleteByIdDocumentoSolicitudAndSolicitud(idTipo, solicitud);

        DocumentosSolicitud documento = new DocumentosSolicitud(
                solicitud,
                tipoDocumentoRequerido,
                rutaCompleta.toString());

        solicitud.getDocumentosEntregados().add(documento);

        documento.setSolicitud(solicitud);

        MovimientoSolicitudRequest movimiento = solicitudMapper.mapMovimientoSolicitudRequest(idSolicitud, 7,
                loginUsuario,
                null);

        movimientoSolicitudService.createMovimientoSolicitud(movimiento);

        solicitudRepository.save(solicitud);

    }

    private DocumentosTramite getDocumentosTramiteById(Long idTipo) {
        return documentosTramiteRepository.findById(idTipo)
                .orElseThrow(() -> new EntityNotFoundException("Tipo de documento requerido con ID "
                        + idTipo + " no encontrado."));
    }

    @Override
    public SolicitudResponse aprobeSolicitud(Long idSolicitud, String loginUsuario) {

        Solicitud solicitud = getSolicitudById(idSolicitud);

        solicitud.setEstado(EstadoSolicitud.APROBADA);

        MovimientoSolicitudRequest movimiento = solicitudMapper.mapMovimientoSolicitudRequest(idSolicitud, 6,
                loginUsuario,
                null);

        movimientoSolicitudService.createMovimientoSolicitud(movimiento);

        solicitud = solicitudRepository.save(solicitud);

        return solicitudMapper.mapSolicitudResponse(solicitud);

    }

    @Override
    public SolicitudResponse rejectSolicitu(Long idSolicitud, String loginUsuario) {

        Solicitud solicitud = getSolicitudById(idSolicitud);
        solicitud.setEstado(EstadoSolicitud.RECHAZADA);
        MovimientoSolicitudRequest movimiento = solicitudMapper.mapMovimientoSolicitudRequest(idSolicitud, 6,
                loginUsuario,
                null);

        movimientoSolicitudService.createMovimientoSolicitud(movimiento);

        solicitud = solicitudRepository.save(solicitud);

        return solicitudMapper.mapSolicitudResponse(solicitud);
    }

    @Override
    public void delteSolicitud(Long idSolicitud) {

        if (!solicitudRepository.findById(idSolicitud).isPresent()) {
            throw new EntityNotFoundException("Solicitud no encontrada con ID: " + idSolicitud);

        }

        solicitudRepository.deleteById(idSolicitud);

    }

}