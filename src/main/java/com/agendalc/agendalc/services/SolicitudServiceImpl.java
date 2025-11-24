package com.agendalc.agendalc.services;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
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
import com.agendalc.agendalc.entities.Documento;
import com.agendalc.agendalc.entities.DocumentosEliminados;
import com.agendalc.agendalc.entities.DocumentosTramite;
import com.agendalc.agendalc.entities.MovimientoSolicitud;
import com.agendalc.agendalc.entities.SaludFormulario;
import com.agendalc.agendalc.entities.SolcitudRechazo;
import com.agendalc.agendalc.entities.Solicitud;
import com.agendalc.agendalc.entities.Tramite;
import com.agendalc.agendalc.entities.MovimientoSolicitud.TipoMovimiento;
import com.agendalc.agendalc.entities.Solicitud.EstadoSolicitud;
import com.agendalc.agendalc.repositories.CitaRepository;
import com.agendalc.agendalc.repositories.DocumentosEliminadosRepository;
import com.agendalc.agendalc.repositories.DocumentosTramiteRepository;
import com.agendalc.agendalc.repositories.SaludFormularioRepository;
import com.agendalc.agendalc.repositories.SolicitudRechazoRepository;
import com.agendalc.agendalc.repositories.SolicitudRepository;
import com.agendalc.agendalc.repositories.TramiteRepository;
import com.agendalc.agendalc.repositories.DocumentoRepository;
import com.agendalc.agendalc.services.interfaces.ApiMailService;
import com.agendalc.agendalc.services.interfaces.ApiPersonaService;
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
    private final DocumentoRepository documentoRepository;
    private final DocumentosEliminadosRepository documentosEliminadosRepository;
    private final SaludFormularioRepository saludFormularioRepository;
    private final CitaRepository citaRepository;
    private final SolicitudMapper solicitudMapper;
    private final ApiMailService apiMailService;
    private final ApiPersonaService apiPersonaService; // Añadido
    private final SolicitudRechazoRepository solicitudRechazoRepository; // Añadido

    public SolicitudServiceImpl(SolicitudRepository solicitudCitaRepository, ArchivoService archivoService,
            TramiteRepository tramiteRepository, DocumentosTramiteRepository documentosTramiteRepository,
            MovimientoSolicitudService movimientoSolicitudService,
            DocumentosEliminadosRepository documentosEliminadosRepository,
            SaludFormularioRepository saludFormularioRepository, CitaRepository citaRepository,
            SolicitudMapper solicitudMapper, DocumentoRepository documentoRepository, ApiMailService apiMailService,
            ApiPersonaService apiPersonaService,
            SolicitudRechazoRepository solicitudRechazoRepository) { // Añadido al constructor
        this.solicitudRepository = solicitudCitaRepository;
        this.tramiteRepository = tramiteRepository;
        this.archivoService = archivoService;
        this.documentosTramiteRepository = documentosTramiteRepository;
        this.movimientoSolicitudService = movimientoSolicitudService;
        this.documentosEliminadosRepository = documentosEliminadosRepository;
        this.saludFormularioRepository = saludFormularioRepository; // Asignado
        this.citaRepository = citaRepository;
        this.solicitudMapper = solicitudMapper;
        this.documentoRepository = documentoRepository;
        this.apiMailService = apiMailService;
        this.apiPersonaService = apiPersonaService;
        this.solicitudRechazoRepository = solicitudRechazoRepository;
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
                            cita.getAgenda().getIdAgenda());
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
        solicitud.addMovimiento(primerMovimiento);

        // Primero guardamos la solicitud para obtener un ID
        Solicitud savedSolicitud = solicitudRepository.save(solicitud);

        if (request.getDocumentos() != null && !request.getDocumentos().isEmpty()) {
            for (DocumentosSubidosRequest docRequest : request.getDocumentos()) {
                MultipartFile file = docRequest.getFile();

                if (file != null && !file.isEmpty()) {
                    DocumentosTramite tipoDocumentoRequerido = documentosTramiteRepository
                            .findById(docRequest.getIdTipoDocumento())
                            .orElseThrow(() -> new EntityNotFoundException("Tipo de documento requerido con ID "
                                    + docRequest.getIdTipoDocumento() + " no encontrado."));

                    String nombreGuardado = archivoService.guardarArchivo(file);
                    Path rutaCompleta = archivoService.getRutaCompletaArchivo(nombreGuardado);

                    Documento documento = new Documento();
                    documento.setRutPersona(savedSolicitud.getRut().toString());
                    documento.setTramite(tramite);
                    documento.setDocumentosTramite(tipoDocumentoRequerido);
                    documento.setNombreArchivo(file.getOriginalFilename());
                    documento.setPathStorage(rutaCompleta.toString());
                    documento.setTipoMime(file.getContentType());
                    documento.setOrigenId(savedSolicitud.getIdSolicitud());
                    documento.setOrigenTipo("SOLICITUD");

                    documentoRepository.save(documento);
                }
            }
        }

        // Asociar la solicitud a la cita, si se proporciona idCita
        if (request.getIdCita() != null) {
            Cita cita = citaRepository.findById(request.getIdCita())
                    .orElseThrow(
                            () -> new EntityNotFoundException("Cita no encontrada con id: " + request.getIdCita()));
            cita.setSolicitud(savedSolicitud);
            citaRepository.save(cita);
        }

        Map<String, Object> variables = Map.of(
                "nombres", getNombresPersonaPorSolicitud(savedSolicitud),
                "urlPlataforma", "https://dev.appx.cl/");
        // Enviar correo de notificación
        apiMailService.sendEmail(getEmailPersonaPorSolicitud(savedSolicitud), "Solicitud creada",
                "solicitud-template", variables);

        return solicitudMapper.mapSolicitudResponse(savedSolicitud);

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

        List<Solicitud> solicitudes = solicitudRepository.findByFechaSolicitudBetween(fechaInicio, fechaFin);

        List<SolicitudResponseList> responseList = solicitudMapper.mapToSolicitudResponseList(solicitudes);

        for (SolicitudResponseList response : responseList) {
            String estadoActualStr = response.getEstadoSolicitud();
            EstadoSolicitud estadoActual = EstadoSolicitud.valueOf(estadoActualStr);

            switch (estadoActual) {
                case ASIGNADA, EN_PROCESO, OBSERVADA, PENDIENTE, DERIVADA:
                    response.setEstadoSolicitud("En Revision");
                    break;
                case RESPONDIDA, APROBADA:
                    response.setEstadoSolicitud("FINALIZADA");
                    break;
                default:
                    // No cambia el estado si no coincide con los grupos definidos
                    break;
            }
        }

        return responseList;
    }

    @Override
    @Transactional
    public void replaceFile(Long idSolicitud, Long idDocumento, MultipartFile file, String loginUsuario)
            throws IOException {

        Solicitud solicitud = getSolicitudById(idSolicitud);

        Documento docToReplace = RepositoryUtils.findOrThrow(documentoRepository.findById(idDocumento),
                String.format("Documento con id %d no encontrado.", idDocumento));

        // Verify the document belongs to the solicitud
        if (!docToReplace.getOrigenId().equals(idSolicitud) || !"SOLICITUD".equals(docToReplace.getOrigenTipo())) {
            throw new SecurityException("El documento no pertenece a la solicitud especificada.");
        }

        String oldPath = docToReplace.getPathStorage();

        String nombreGuardado = archivoService.guardarArchivo(file);
        Path rutaCompleta = archivoService.getRutaCompletaArchivo(nombreGuardado);

        solicitud.setEstado(EstadoSolicitud.RESPONDIDA);

        documentosEliminadosRepository.save(new DocumentosEliminados(oldPath, idSolicitud, idDocumento));

        docToReplace.setPathStorage(rutaCompleta.toString());
        docToReplace.setNombreArchivo(file.getOriginalFilename());
        docToReplace.setTipoMime(file.getContentType());

        documentoRepository.save(docToReplace);

        MovimientoSolicitudRequest movimiento = solicitudMapper.mapMovimientoSolicitudRequest(idSolicitud, 7,
                loginUsuario,
                null);

        movimientoSolicitudService.createMovimientoSolicitud(movimiento);

        solicitudRepository.save(solicitud);
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

        Map<String, Object> variables = Map.of(
                "nombres", getNombresPersonaPorSolicitud(solicitud),
                "idSolicitud", solicitud.getIdSolicitud(),
                "urlPlataforma", "https://dev.appx.cl/");

        apiMailService.sendEmail(getEmailPersonaPorSolicitud(solicitud), "Solicitud Aprobada",
                "solicitud-aprobada-template", variables);

        return solicitudMapper.mapSolicitudResponse(solicitud);

    }

    @Override
    public SolicitudResponse rejectSolicitu(Long idSolicitud, String loginUsuario, String motivoRechazo) {

        Solicitud solicitud = getSolicitudById(idSolicitud);
        solicitud.setEstado(EstadoSolicitud.RECHAZADA);
        MovimientoSolicitudRequest movimiento = solicitudMapper.mapMovimientoSolicitudRequest(idSolicitud, 6,
                loginUsuario,
                null);

        movimientoSolicitudService.createMovimientoSolicitud(movimiento);

        // Crear y guardar el registro de rechazo
        SolcitudRechazo solcitudRehazo = toEntitySolcitudRechazo(motivoRechazo, solicitud);
        solicitudRechazoRepository.save(solcitudRehazo);

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

    private String getEmailPersonaPorSolicitud(Solicitud solicitud) {
        Integer rut = solicitud.getRut();
        return apiPersonaService.getPersonaInfo(rut).getEmail();
    }

    private String getNombresPersonaPorSolicitud(Solicitud solicitud) {
        Integer rut = solicitud.getRut();
        return apiPersonaService.getPersonaInfo(rut).getNombres();
    }

    private SolcitudRechazo toEntitySolcitudRechazo(String motivoRechazo, Solicitud solicitud) {
        SolcitudRechazo solcitudRehazo = new SolcitudRechazo();
        solcitudRehazo.setMotivoRechazo(motivoRechazo);
        solcitudRehazo.setSolicitud(solicitud);
        return solcitudRehazo;
    }

}