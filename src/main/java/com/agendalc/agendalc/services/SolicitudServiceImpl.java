package com.agendalc.agendalc.services;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;
import java.util.EnumSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.agendalc.agendalc.dto.MovimientoSolicitudRequest;
import com.agendalc.agendalc.dto.SolicitudRequest;
import com.agendalc.agendalc.dto.SolicitudResponse;
import com.agendalc.agendalc.dto.SolicitudResponseList;
import com.agendalc.agendalc.entities.MovimientoSolicitud;
import com.agendalc.agendalc.entities.SaludFormulario;
import com.agendalc.agendalc.entities.SolcitudRechazo;
import com.agendalc.agendalc.entities.Solicitud;
import com.agendalc.agendalc.entities.Tramite;
import com.agendalc.agendalc.entities.TramiteLicencia;
import com.agendalc.agendalc.entities.MovimientoSolicitud.TipoMovimiento;
import com.agendalc.agendalc.entities.Solicitud.EstadoSolicitud;
import com.agendalc.agendalc.entities.enums.ClaseLicencia;
import com.agendalc.agendalc.repositories.SaludFormularioRepository;
import com.agendalc.agendalc.repositories.SolicitudRechazoRepository;
import com.agendalc.agendalc.repositories.SolicitudRepository;
import com.agendalc.agendalc.repositories.TramiteRepository;
import com.agendalc.agendalc.repositories.TramiteLicenciaRepository;
import com.agendalc.agendalc.services.interfaces.MovimientoSolicitudService;
import com.agendalc.agendalc.services.interfaces.NotificacionService;
import com.agendalc.agendalc.services.interfaces.SolicitudDocumentoService;
import com.agendalc.agendalc.services.interfaces.SolicitudService;
import com.agendalc.agendalc.services.interfaces.SolicitudResponseTransformer;
import com.agendalc.agendalc.services.mappers.SolicitudMapper;
import com.agendalc.agendalc.utils.RepositoryUtils;

@Service
public class SolicitudServiceImpl implements SolicitudService {

    private static final Logger logger = LoggerFactory.getLogger(SolicitudServiceImpl.class);

    private final SolicitudRepository solicitudRepository;
    private final TramiteRepository tramiteRepository;
    private final MovimientoSolicitudService movimientoSolicitudService;
    private final SaludFormularioRepository saludFormularioRepository;
    private final SolicitudMapper solicitudMapper;
    private final NotificacionService notificacionService;
    private final SolicitudRechazoRepository solicitudRechazoRepository;
    private final SolicitudResponseTransformer responseTransformer;
    private final SolicitudDocumentoService solicitudDocumentoService;
    private final TramiteLicenciaRepository tramiteLicenciaRepository;

    public SolicitudServiceImpl(SolicitudRepository solicitudCitaRepository,
            TramiteRepository tramiteRepository,
            MovimientoSolicitudService movimientoSolicitudService,
            SaludFormularioRepository saludFormularioRepository, 
            SolicitudMapper solicitudMapper, NotificacionService notificacionService,
            SolicitudRechazoRepository solicitudRechazoRepository,
            SolicitudResponseTransformer responseTransformer,
            SolicitudDocumentoService solicitudDocumentoService,
            TramiteLicenciaRepository tramiteLicenciaRepository) {
        this.solicitudRepository = solicitudCitaRepository;
        this.tramiteRepository = tramiteRepository;
        this.movimientoSolicitudService = movimientoSolicitudService;
        this.saludFormularioRepository = saludFormularioRepository;
        this.solicitudMapper = solicitudMapper;
        this.notificacionService = notificacionService;
        this.solicitudRechazoRepository = solicitudRechazoRepository;
        this.responseTransformer = responseTransformer;
        this.solicitudDocumentoService = solicitudDocumentoService;
        this.tramiteLicenciaRepository = tramiteLicenciaRepository;
    }

    @Override
    public List<SolicitudResponseList> getSolicitudes(int year) {
        List<Solicitud> solicitudes = solicitudRepository
                .findByFechaSolicitudYearWithMovimientosOrdered(year);

        return responseTransformer.transform(solicitudes);
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
        logger.info("Iniciando creación de solicitud para RUT: {}", request.getRut());
        
        Tramite tramite = getTramiteById(request.getIdTramite());
        Solicitud solicitud = solicitudMapper.toEntity(request, tramite);

        asociarSaludFormulario(solicitud, request.getIdSaludFormulario());

        MovimientoSolicitud primerMovimiento = firstMovement(solicitud, TipoMovimiento.CREACION);
        solicitud.addMovimiento(primerMovimiento);

        // Guardar la solicitud para obtener un ID
        Solicitud savedSolicitud = solicitudRepository.save(solicitud);
        solicitudRepository.flush();
        logger.info("Solicitud guardada con ID: {}", savedSolicitud.getIdSolicitud());

        // Asociar licencias por ID
        asociarTramiteLicenciasPorId(savedSolicitud, request.getIdTramiteLicencias());

        // Asociar clases de licencia
        asociarClasesLicencia(savedSolicitud, request.getClases(), tramite);

        try {
            notificacionService.enviarNotificacionSolicitudCreada(savedSolicitud);
        } catch (Exception e) {
            logger.warn("Error al enviar la notificación por correo para la solicitud creada con ID: {}. La solicitud se creó igualmente.", savedSolicitud.getIdSolicitud(), e);
        }
        
        logger.info("Solicitud creada exitosamente con ID: {}", savedSolicitud.getIdSolicitud());

        return solicitudMapper.mapSolicitudResponse(savedSolicitud);
    }

    private void asociarTramiteLicenciasPorId(Solicitud solicitud, List<Long> idTramiteLicencias) {
        if (idTramiteLicencias == null || idTramiteLicencias.isEmpty()) {
            return;
        }
        
        logger.info("Asociando {} tramiteLicencias a la solicitud {}", idTramiteLicencias.size(), solicitud.getIdSolicitud());
        
        List<TramiteLicencia> licenciasAAsociar = new ArrayList<>();
        
        for (Long idTramiteLicencia : idTramiteLicencias) {
            TramiteLicencia tramiteLicencia = tramiteLicenciaRepository.findById(idTramiteLicencia)
                    .orElseThrow(() -> {
                        logger.error("TramiteLicencia no encontrada: {}", idTramiteLicencia);
                        return new IllegalArgumentException("TramiteLicencia no encontrada: " + idTramiteLicencia);
                    });
            
            logger.debug("TramiteLicencia encontrada: id={}, clase={}", idTramiteLicencia, tramiteLicencia.getClaseLicencia());
            licenciasAAsociar.add(tramiteLicencia);
        }
        
        for (TramiteLicencia licencia : licenciasAAsociar) {
            solicitud.getTramiteLicencias().add(licencia);
            logger.debug("TramiteLicencia {} agregada al conjunto", licencia.getId());
        }
        
        logger.info("Total de licencias en memoria antes de guardar: {}", solicitud.getTramiteLicencias().size());
        
        Solicitud actualizada = solicitudRepository.save(solicitud);
        solicitudRepository.flush();
        
        logger.info("Después de flush - Licencias en BD: {}", actualizada.getTramiteLicencias().size());
        for (TramiteLicencia lic : actualizada.getTramiteLicencias()) {
            logger.debug("Licencia en BD: id={}, clase={}", lic.getId(), lic.getClaseLicencia());
        }
    }

    private void asociarClasesLicencia(Solicitud solicitud, List<String> clases, Tramite tramite) {
        if (clases == null || clases.isEmpty()) {
            return;
        }
        
        logger.info("Buscando {} clases de licencia en el enumerador", clases.size());
        
        List<TramiteLicencia> tramiteLicenciasDelTramite = tramiteLicenciaRepository.findByTramite(tramite);
        logger.debug("TramiteLicencias disponibles en tramite {}: {}", tramite.getIdTramite(), tramiteLicenciasDelTramite.size());
        
        for (String claseStr : clases) {
            procesarClaseLicencia(solicitud, claseStr, tramite, tramiteLicenciasDelTramite);
        }
        
        solicitud = solicitudRepository.save(solicitud);
        solicitudRepository.flush();
        
        logger.info("Solicitud actualizada. Total de licencias asociadas: {}", solicitud.getTramiteLicencias().size());
    }

    private void procesarClaseLicencia(Solicitud solicitud, String claseStr, Tramite tramite, List<TramiteLicencia> tramiteLicenciasDelTramite) {
        try {
            ClaseLicencia claseLicencia = ClaseLicencia.valueOf(claseStr.trim().toUpperCase());
            logger.debug("Buscando clase {} en enumerador ClaseLicencia", claseLicencia);
            
            TramiteLicencia tramiteLicenciaEncontrada = tramiteLicenciasDelTramite.stream()
                    .filter(tl -> tl.getClaseLicencia() == claseLicencia)
                    .findFirst()
                    .orElse(null);
            
            if (tramiteLicenciaEncontrada != null) {
                logger.info("Clase {} encontrada: id={}, agregando a solicitud", claseLicencia, tramiteLicenciaEncontrada.getId());
                solicitud.getTramiteLicencias().add(tramiteLicenciaEncontrada);
            } else {
                logger.warn("Clase {} no encontrada en tramite {}", claseLicencia, tramite.getIdTramite());
            }
        } catch (IllegalArgumentException e) {
            logger.error("Clase de licencia inválida: {}", claseStr);
            throw new IllegalArgumentException("Clase de licencia inválida: " + claseStr);
        }
    }

    private void asociarSaludFormulario(Solicitud solicitud, Long idSaludFormulario) {
        if (idSaludFormulario != null) {
            SaludFormulario saludFormulario = RepositoryUtils.findOrThrow(
                    saludFormularioRepository.findById(idSaludFormulario),
                    "SaludFormulario no encontrado con id: " + idSaludFormulario);
            solicitud.setSaludFormulario(saludFormulario);
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

        // Conjunto de estados que consideramos como finalizados
        EnumSet<EstadoSolicitud> finalizados = EnumSet.of(EstadoSolicitud.APROBADA,
                EstadoSolicitud.RECHAZADA,
                EstadoSolicitud.FINALIZADA);

        return switch (strEstado) {
            case "FINALIZADA" -> solicitudes.stream()
                    .filter(sol -> finalizados.contains(sol.getEstado()))
                    .toList();

            default -> solicitudes.stream()
                    // Traer el resto: todos los que NO están en el conjunto de finalizados
                    .filter(sol -> !finalizados.contains(sol.getEstado()))
                    .toList();
        };

    }
}