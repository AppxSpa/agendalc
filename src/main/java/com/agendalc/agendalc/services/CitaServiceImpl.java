package com.agendalc.agendalc.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agendalc.agendalc.dto.CitaDelDiaResponseDto;
import com.agendalc.agendalc.dto.CitaDto;
import com.agendalc.agendalc.dto.CitaRequest;
import com.agendalc.agendalc.dto.DocumentoDto;
import com.agendalc.agendalc.dto.PersonaResponse;
import com.agendalc.agendalc.entities.Agenda;
import com.agendalc.agendalc.entities.BloqueHorario;
import com.agendalc.agendalc.entities.Cita;
import com.agendalc.agendalc.entities.Documento;
import com.agendalc.agendalc.entities.DocumentosTramite;
import com.agendalc.agendalc.entities.SaludFormulario;
import com.agendalc.agendalc.entities.Solicitud;
import com.agendalc.agendalc.exceptions.NotFounException;
import com.agendalc.agendalc.repositories.CitaRepository;
import com.agendalc.agendalc.repositories.DocumentoRepository;
import com.agendalc.agendalc.repositories.DocumentosTramiteRepository;
import com.agendalc.agendalc.repositories.SaludFormularioRepository;
import com.agendalc.agendalc.repositories.SolicitudRepository;
import com.agendalc.agendalc.services.interfaces.AgendaService;
import com.agendalc.agendalc.services.interfaces.ApiPersonaService;
import com.agendalc.agendalc.services.interfaces.BloqueHorarioService;
import com.agendalc.agendalc.services.interfaces.CitaService;
import com.agendalc.agendalc.services.interfaces.NotificacionService;
import com.agendalc.agendalc.services.mappers.CitaMapper;
import com.agendalc.agendalc.services.mappers.DocumentoMapper;
import com.agendalc.agendalc.utils.RepositoryUtils;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CitaServiceImpl implements CitaService {

    private final CitaRepository citaRepository;

    private final AgendaService agendaService;

    private final BloqueHorarioService bloqueHorarioService;

    private final ApiPersonaService apiPersonaService;

    private final NotificacionService notificacionService;

    private final SaludFormularioRepository saludFormularioRepository;

    private final CitaMapper citaMapper;

    private final SolicitudRepository solicitudRepository;

    private final DocumentoRepository documentoRepository;

    private final DocumentoMapper documentoMapper;

    private final DocumentosTramiteRepository documentosTramiteRepository;

    public CitaServiceImpl(CitaRepository citaRepository, AgendaService agendaService,
            BloqueHorarioService bloqueHorarioService,
            ApiPersonaService apiPersonaService,
            NotificacionService notificacionService,
            SaludFormularioRepository saludFormularioRepository,
            CitaMapper citaMapper,
            SolicitudRepository solicitudRepository,
            DocumentoRepository documentoRepository,
            DocumentoMapper documentoMapper,
            DocumentosTramiteRepository documentosTramiteRepository) {
        this.citaRepository = citaRepository;
        this.agendaService = agendaService;
        this.bloqueHorarioService = bloqueHorarioService;
        this.apiPersonaService = apiPersonaService;
        this.notificacionService = notificacionService;
        this.saludFormularioRepository = saludFormularioRepository;
        this.citaMapper = citaMapper;
        this.solicitudRepository = solicitudRepository;
        this.documentoRepository = documentoRepository;
        this.documentoMapper = documentoMapper;
        this.documentosTramiteRepository = documentosTramiteRepository;
    }

    @Transactional
    @Override
    public CitaDto createCita(CitaRequest citaRequest) {
        Agenda agenda = agendaService.findById(citaRequest.getIdAgenda());
        BloqueHorario bloqueHorario = bloqueHorarioService.findById(citaRequest.getIdBloqueHorario());

        validarCreacionCita(citaRequest, agenda, bloqueHorario);

        // Obtener entidades asociadas
        SaludFormulario saludFormulario = null;
        if (citaRequest.getIdSaludFormulario() != null) {
            saludFormulario = saludFormularioRepository.findById(citaRequest.getIdSaludFormulario())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "SaludFormulario no encontrado con id: " + citaRequest.getIdSaludFormulario()));
        }

        Solicitud solicitud = null;
        if (citaRequest.getIdSolicitud() != null) {
            solicitud = solicitudRepository.findById(citaRequest.getIdSolicitud())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Solicitud no encontrada con id: " + citaRequest.getIdSolicitud()));
        }

        // Usar el mapper para construir la entidad
        Cita cita = citaMapper.toEntity(citaRequest, agenda, bloqueHorario, saludFormulario, solicitud);

        Cita citaGuardada = citaRepository.save(cita);

        decrementarYGuardarCupos(bloqueHorario);

        notificacionService.enviarNotificacionCitaAgendada(cita);

        return citaMapper.toDto(citaGuardada);
    }

    @Override
    @Transactional
    public void adjuntarDocumentos(Long citaId, List<DocumentoDto> documentos) {
        Cita cita = findById(citaId);

        if (documentos != null && !documentos.isEmpty()) {
            for (DocumentoDto docDto : documentos) {
                Documento documento = documentoMapper.toEntity(docDto);
                documento.setCita(cita);
                documento.setOrigenId(cita.getIdCita());
                documento.setOrigenTipo("CITA");

                if (docDto.getDocumentosTramiteId() != null) {
                    DocumentosTramite documentosTramite = documentosTramiteRepository
                            .findById(docDto.getDocumentosTramiteId())
                            .orElse(null);
                    if (documentosTramite != null) {
                        documento.setDocumentosTramite(documentosTramite);
                        documento.setTramite(documentosTramite.getTramite());
                    }
                }

                documentoRepository.save(documento);
            }
        }
    }

    private void validarCreacionCita(CitaRequest citaRequest, Agenda agenda, BloqueHorario bloqueHorario) {
        if (!agenda.getBloquesHorarios().contains(bloqueHorario)) {
            throw new IllegalArgumentException("El bloque horario no pertenece a la agenda seleccionada");
        }

        if (bloqueHorario.getCuposDisponibles() <= 0) {
            throw new IllegalStateException("No hay cupos disponibles en este bloque horario");
        }

        if (apiPersonaService.getPersonaInfo(citaRequest.getRut()) == null) {
            throw new EntityNotFoundException("Persona no econtrada");
        }
    }

    private void decrementarYGuardarCupos(BloqueHorario bloqueHorario) {
        bloqueHorario.setCuposDisponibles(bloqueHorario.getCuposDisponibles() - 1);
        bloqueHorarioService.save(bloqueHorario);
    }

    @Override
    public Cita findById(Long id) {
        return RepositoryUtils.findOrThrow(citaRepository.findById(id),
                String.format("No se encontro la cita %d ", id));

    }

    @Override
    public List<CitaDto> getCitaByRut(Integer rut) {
        List<Cita> citas = citaRepository.findByRut(rut);

        if (citas.isEmpty()) {
            throw new IllegalArgumentException("No hay citas para el rut");
        }

        return citaMapper.toDtoList(citas);

    }

    @Transactional
    @Override
    public Cita updateCita(Long id, Cita citaActualizada) {
        Optional<Cita> citaOptional = citaRepository.findById(id);
        if (citaOptional.isPresent()) {
            Cita citaExistente = citaOptional.get();
            citaExistente.setRut(citaActualizada.getRut());
            citaExistente.setAgenda(citaActualizada.getAgenda());
            citaExistente.setFechaHora(citaActualizada.getFechaHora());
            return citaRepository.save(citaExistente);
        }
        return null;
    }

    @Transactional
    @Override
    public boolean deleteCitaById(Long id) {
        Optional<Cita> citaOptional = citaRepository.findById(id);
        if (citaOptional.isPresent()) {
            citaRepository.delete(citaOptional.get());
            return true;
        }
        return false;
    }

    @Override
    public List<CitaDto> getCitaBetweenDates(LocalDate fechaInicio,
            LocalDate fechaFin) {

        List<Cita> citas = citaRepository.findByAgenda_FechaBetween(fechaInicio, fechaFin);

        return citaMapper.toDtoList(citas);
    }

    @Override
    @Transactional(readOnly = true)
    public CitaDelDiaResponseDto findCitaDelDiaPorRut(Integer rut) {
        LocalDate hoy = LocalDate.now();
        Cita cita = citaRepository.findByRutAndAgenda_Fecha(rut, hoy)
                .orElseThrow(() -> new NotFounException(
                        "No se encontró una cita para el RUT " + rut + " en la fecha de hoy."));

        PersonaResponse persona = apiPersonaService.getPersonaInfo(cita.getRut());

        return new CitaDelDiaResponseDto(
                cita.getIdCita(),
                cita.getFechaAgenda(),
                cita.getHoraInicioBloqueHoraio(),
                persona,
                cita.getTramite().getIdTramite(),
                cita.getTramite().getNombre());
    }

}