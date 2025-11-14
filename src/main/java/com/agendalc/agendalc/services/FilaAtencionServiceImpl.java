package com.agendalc.agendalc.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agendalc.agendalc.dto.FilaAtencionResponseDto;
import com.agendalc.agendalc.dto.PersonaResponse;
import com.agendalc.agendalc.entities.Cita;
import com.agendalc.agendalc.entities.EtapaTramite;
import com.agendalc.agendalc.entities.FilaAtencion;
import com.agendalc.agendalc.entities.UsuarioEtapa;
import com.agendalc.agendalc.entities.enums.EstadoFila;
import com.agendalc.agendalc.exceptions.NotFounException;
import com.agendalc.agendalc.repositories.CitaRepository;
import com.agendalc.agendalc.repositories.EtapaTramiteRepository;
import com.agendalc.agendalc.repositories.FilaAtencionRepository;
import com.agendalc.agendalc.repositories.UsuarioEtapaRepository;
import com.agendalc.agendalc.services.interfaces.ApiPersonaService;
import com.agendalc.agendalc.services.interfaces.FilaAtencionService;

@Service
public class FilaAtencionServiceImpl implements FilaAtencionService {

    private final FilaAtencionRepository filaAtencionRepository;
    private final CitaRepository citaRepository;
    private final EtapaTramiteRepository etapaTramiteRepository;
    private final UsuarioEtapaRepository usuarioEtapaRepository;
    private final ApiPersonaService apiPersonaService;

    public FilaAtencionServiceImpl(FilaAtencionRepository filaAtencionRepository, CitaRepository citaRepository,
            EtapaTramiteRepository etapaTramiteRepository, UsuarioEtapaRepository usuarioEtapaRepository,
            ApiPersonaService apiPersonaService) {
        this.filaAtencionRepository = filaAtencionRepository;
        this.citaRepository = citaRepository;
        this.etapaTramiteRepository = etapaTramiteRepository;
        this.usuarioEtapaRepository = usuarioEtapaRepository;
        this.apiPersonaService = apiPersonaService;
    }

    @Override
    @Transactional
    public FilaAtencionResponseDto iniciarProceso(Long citaId) {
        Cita cita = citaRepository.findById(citaId)
                .orElseThrow(() -> new NotFounException("Cita no encontrada con ID: " + citaId));

        if (cita.getTramite() == null) {
            throw new IllegalStateException("La cita no tiene un trámite asociado para iniciar el proceso.");
        }
        Long tramiteId = cita.getTramite().getIdTramite();

        EtapaTramite primeraEtapa = etapaTramiteRepository.findByTramite_IdTramiteAndOrden(tramiteId, 1)
                .orElseThrow(
                        () -> new NotFounException("No se encontró la primera etapa para el trámite ID: " + tramiteId));

        FilaAtencion nuevaFila = FilaAtencion.builder()
                .cita(cita)
                .etapaTramite(primeraEtapa)
                .estado(EstadoFila.EN_ESPERA)
                .fechaLlegada(LocalDateTime.now())
                .build();

        FilaAtencion savedFila = filaAtencionRepository.save(nuevaFila);
        return toResponseDto(savedFila);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FilaAtencionResponseDto> verFilaPorEtapa(Long etapaId) {
        EtapaTramite etapa = etapaTramiteRepository.findById(etapaId)
                .orElseThrow(() -> new NotFounException("Etapa no encontrada con ID: " + etapaId));

        List<FilaAtencion> filas = filaAtencionRepository.findByEtapaTramiteAndEstadoOrderByFechaLlegadaAsc(etapa,
                EstadoFila.EN_ESPERA);

        return filas.stream().map(this::toResponseDto).toList();
    }

    @Override
    @Transactional
    public FilaAtencionResponseDto llamarSiguiente(Long etapaId) {
        String usuarioId = getCurrentUserId();

        if (!filaAtencionRepository.findByUsuarioAsignadoAndEstado(usuarioId, EstadoFila.EN_ATENCION).isEmpty()) {
            throw new IllegalStateException("El usuario ya tiene una atención activa.");
        }

        EtapaTramite etapa = etapaTramiteRepository.findById(etapaId)
                .orElseThrow(() -> new NotFounException("Etapa no encontrada con ID: " + etapaId));

        FilaAtencion proximoEnFila = filaAtencionRepository
                .findFirstByEtapaTramiteAndEstadoOrderByFechaLlegadaAsc(etapa, EstadoFila.EN_ESPERA)
                .orElseThrow(() -> new NotFounException("No hay nadie en espera en esta etapa."));

        proximoEnFila.setEstado(EstadoFila.EN_ATENCION);
        proximoEnFila.setUsuarioAsignado(usuarioId);
        proximoEnFila.setFechaInicioAtencion(LocalDateTime.now());

        FilaAtencion savedFila = filaAtencionRepository.save(proximoEnFila);
        return toResponseDto(savedFila);
    }

    @Override
    @Transactional
    public FilaAtencionResponseDto finalizarAtencion(Long filaAtencionId) {
        String usuarioId = getCurrentUserId();
        FilaAtencion filaActual = filaAtencionRepository.findById(filaAtencionId)
                .orElseThrow(() -> new NotFounException("Fila de atención no encontrada con ID: " + filaAtencionId));

        if (!filaActual.getEstado().equals(EstadoFila.EN_ATENCION)) {
            throw new IllegalStateException("Esta atención no está activa.");
        }
        if (!filaActual.getUsuarioAsignado().equals(usuarioId)) {
            throw new IllegalStateException("Esta atención está asignada a otro usuario.");
        }

        filaActual.setEstado(EstadoFila.FINALIZADO);
        filaActual.setFechaFinAtencion(LocalDateTime.now());
        FilaAtencion filaFinalizada = filaAtencionRepository.save(filaActual);

        // Lógica para mover a la siguiente etapa
        EtapaTramite etapaActual = filaFinalizada.getEtapaTramite();
        Long tramiteId = etapaActual.getTramite().getIdTramite();
        int ordenActual = etapaActual.getOrden();

        etapaTramiteRepository.findByTramite_IdTramiteAndOrden(tramiteId, ordenActual + 1).ifPresent(siguienteEtapa -> {
            // Evitar duplicados: solo crear la nueva fila si no existe una ya para esa cita y etapa
            if (filaAtencionRepository.findByCitaAndEtapaTramite(filaFinalizada.getCita(), siguienteEtapa).isEmpty()) {
                FilaAtencion nuevaFila = FilaAtencion.builder()
                        .cita(filaFinalizada.getCita())
                        .etapaTramite(siguienteEtapa)
                        .estado(EstadoFila.EN_ESPERA)
                        .fechaLlegada(LocalDateTime.now())
                        .build();
                filaAtencionRepository.save(nuevaFila);
            }
        });

        return toResponseDto(filaFinalizada);
    }

    @Override
    @Transactional(readOnly = true)
    public FilaAtencionResponseDto verAtencionActual() {
        String usuarioId = getCurrentUserId();
        return filaAtencionRepository.findByUsuarioAsignadoAndEstado(usuarioId, EstadoFila.EN_ATENCION)
                .stream().findFirst().map(this::toResponseDto).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FilaAtencionResponseDto> verMisFilasDeEspera() {
        String usuarioId = getCurrentUserId();
        List<UsuarioEtapa> misEtapas = usuarioEtapaRepository.findByUsuarioId(usuarioId);

        return misEtapas.stream()
                .map(UsuarioEtapa::getEtapaTramite)
                .flatMap(etapa -> filaAtencionRepository
                        .findByEtapaTramiteAndEstadoOrderByFechaLlegadaAsc(etapa, EstadoFila.EN_ESPERA)
                        .stream()
                        .map(this::toResponseDto))
                .toList();
    }

    private FilaAtencionResponseDto toResponseDto(FilaAtencion fila) {
        // Obtenemos los datos de la persona desde el servicio de API de personas
        PersonaResponse persona = apiPersonaService.getPersonaInfo(fila.getCita().getRut());

        return new FilaAtencionResponseDto(
                fila.getId(),
                fila.getCita().getIdCita(),
                persona,
                fila.getEtapaTramite().getId(),
                fila.getEtapaTramite().getNombre(),
                fila.getEstado(),
                fila.getUsuarioAsignado(),
                fila.getFechaLlegada(),
                fila.getFechaInicioAtencion(),
                fila.getFechaFinAtencion());
    }

    private String getCurrentUserId() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @Override
    @Transactional
    public FilaAtencionResponseDto llamarPersona(Long filaId) {
        String usuarioId = getCurrentUserId();

        if (!filaAtencionRepository.findByUsuarioAsignadoAndEstado(usuarioId, EstadoFila.EN_ATENCION).isEmpty()) {
            throw new IllegalStateException("El usuario ya tiene una atención activa.");
        }

        FilaAtencion personaEnFila = filaAtencionRepository.findById(filaId)
                .orElseThrow(() -> new NotFounException("Persona no encontrada en la fila con ID: " + filaId));

        if (personaEnFila.getEstado() != EstadoFila.EN_ESPERA) {
            throw new IllegalStateException("Esta persona no está en espera. Estado actual: " + personaEnFila.getEstado());
        }

        personaEnFila.setEstado(EstadoFila.EN_ATENCION);
        personaEnFila.setUsuarioAsignado(usuarioId);
        personaEnFila.setFechaInicioAtencion(LocalDateTime.now());

        FilaAtencion savedFila = filaAtencionRepository.save(personaEnFila);
        return toResponseDto(savedFila);
    }
}
