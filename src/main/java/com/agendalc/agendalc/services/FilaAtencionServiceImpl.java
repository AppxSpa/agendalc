package com.agendalc.agendalc.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agendalc.agendalc.dto.FilaAtencionResponseDto;
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
import com.agendalc.agendalc.services.interfaces.AuthenticationService;
import com.agendalc.agendalc.services.interfaces.FilaAtencionService;
import com.agendalc.agendalc.services.interfaces.TramiteWorkflowService;
import com.agendalc.agendalc.services.mappers.FilaAtencionMapper;

@Service
public class FilaAtencionServiceImpl implements FilaAtencionService {

    private final FilaAtencionRepository filaAtencionRepository;
    private final CitaRepository citaRepository;
    private final EtapaTramiteRepository etapaTramiteRepository;
    private final UsuarioEtapaRepository usuarioEtapaRepository;
    private final com.agendalc.agendalc.repositories.TramiteRepository tramiteRepository;
    private final FilaAtencionMapper filaAtencionMapper;
    private final TramiteWorkflowService tramiteWorkflowService;
    private final AuthenticationService authenticationService;

    public FilaAtencionServiceImpl(FilaAtencionRepository filaAtencionRepository, CitaRepository citaRepository,
            EtapaTramiteRepository etapaTramiteRepository, UsuarioEtapaRepository usuarioEtapaRepository,
            com.agendalc.agendalc.repositories.TramiteRepository tramiteRepository,
            FilaAtencionMapper filaAtencionMapper, TramiteWorkflowService tramiteWorkflowService,
            AuthenticationService authenticationService) {
        this.filaAtencionRepository = filaAtencionRepository;
        this.citaRepository = citaRepository;
        this.etapaTramiteRepository = etapaTramiteRepository;
        this.usuarioEtapaRepository = usuarioEtapaRepository;
        this.tramiteRepository = tramiteRepository;
        this.filaAtencionMapper = filaAtencionMapper;
        this.tramiteWorkflowService = tramiteWorkflowService;
        this.authenticationService = authenticationService;
    }

    @Override
    @Transactional
    public List<FilaAtencionResponseDto> iniciarProceso(Long citaId,
            com.agendalc.agendalc.dto.IniciarProcesosRequest request) {
        Cita cita = citaRepository.findById(citaId)
                .orElseThrow(() -> new NotFounException("Cita no encontrada con ID: " + citaId));

        if (request.getTramiteIds() == null || request.getTramiteIds().isEmpty()) {
            throw new IllegalArgumentException("La lista de IDs de trámites no puede ser nula o vacía.");
        }

        List<FilaAtencion> nuevasFilas = new java.util.ArrayList<>();

        for (Long tramiteId : request.getTramiteIds()) {
            com.agendalc.agendalc.entities.Tramite tramite = tramiteRepository.findById(tramiteId)
                    .orElseThrow(() -> new NotFounException("Trámite no encontrado con ID: " + tramiteId));

            EtapaTramite primeraEtapa = tramite.getTramiteEtapas().stream()
                    .findFirst() // La lista ya está ordenada por "orden"
                    .map(com.agendalc.agendalc.entities.TramiteEtapa::getEtapaTramite)
                    .orElseThrow(
                            () -> new NotFounException(
                                    "El trámite ID: " + tramiteId + " no tiene etapas configuradas."));

            // Evitar duplicados: no crear una fila si ya existe para esa cita y etapa
            if (filaAtencionRepository.findByCitaAndEtapaTramite(cita, primeraEtapa).isEmpty()) {
                FilaAtencion nuevaFila = FilaAtencion.builder()
                        .cita(cita)
                        .tramite(tramite)
                        .etapaTramite(primeraEtapa)
                        .estado(EstadoFila.EN_ESPERA)
                        .fechaLlegada(LocalDateTime.now())
                        .build();
                nuevasFilas.add(nuevaFila);
            }
        }

        List<FilaAtencion> savedFilas = filaAtencionRepository.saveAll(nuevasFilas);
        return filaAtencionMapper.toResponseDtoList(savedFilas);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FilaAtencionResponseDto> verFilaPorEtapa(Long etapaId) {
        EtapaTramite etapa = findEtapaOrThrow(etapaId);
        List<FilaAtencion> filas = filaAtencionRepository.findByEtapaTramiteAndEstadoOrderByFechaLlegadaAsc(etapa,
                EstadoFila.EN_ESPERA);
        return filaAtencionMapper.toResponseDtoList(filas);
    }

    @Override
    @Transactional
    public FilaAtencionResponseDto llamarSiguiente(Long etapaId) {
        String usuarioId = authenticationService.getCurrentUserId();
        validarUsuarioSinAtencionActiva(usuarioId);

        EtapaTramite etapa = findEtapaOrThrow(etapaId);

        FilaAtencion proximoEnFila = filaAtencionRepository
                .findFirstByEtapaTramiteAndEstadoOrderByFechaLlegadaAsc(etapa, EstadoFila.EN_ESPERA)
                .orElseThrow(() -> new NotFounException("No hay nadie en espera en esta etapa."));

        proximoEnFila.setEstado(EstadoFila.EN_ATENCION);
        proximoEnFila.setUsuarioAsignado(usuarioId);
        proximoEnFila.setFechaInicioAtencion(LocalDateTime.now());

        FilaAtencion savedFila = filaAtencionRepository.save(proximoEnFila);
        return filaAtencionMapper.toResponseDto(savedFila);
    }

    @Override
    @Transactional
    public FilaAtencionResponseDto finalizarAtencion(Long filaAtencionId) {
        String usuarioId = authenticationService.getCurrentUserId();
        FilaAtencion filaActual = findFilaOrThrow(filaAtencionId);

        if (!filaActual.getEstado().equals(EstadoFila.EN_ATENCION)) {
            throw new IllegalStateException("Esta atención no está activa.");
        }
        if (!filaActual.getUsuarioAsignado().equals(usuarioId)) {
            throw new IllegalStateException("Esta atención está asignada a otro usuario.");
        }

        filaActual.setFechaFinAtencion(LocalDateTime.now());
        filaActual.setEstado(EstadoFila.EN_ATENCION);
        FilaAtencion filaFinalizada = filaAtencionRepository.save(filaActual);

        tramiteWorkflowService.avanzarEtapa(filaFinalizada);

        return filaAtencionMapper.toResponseDto(filaFinalizada);
    }

    @Override
    @Transactional(readOnly = true)
    public FilaAtencionResponseDto verAtencionActual() {
        String usuarioId = authenticationService.getCurrentUserId();
        return filaAtencionRepository.findByUsuarioAsignadoAndEstado(usuarioId, EstadoFila.EN_ATENCION)
                .stream().findFirst().map(filaAtencionMapper::toResponseDto).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FilaAtencionResponseDto> verMisFilasDeEspera() {
        String usuarioId = authenticationService.getCurrentUserId();
        List<UsuarioEtapa> misEtapas = usuarioEtapaRepository.findByUsuarioId(usuarioId);

        return misEtapas.stream()
                .map(UsuarioEtapa::getEtapaTramite)
                .flatMap(etapa -> filaAtencionRepository
                        .findByEtapaTramiteAndEstadoOrderByFechaLlegadaAsc(etapa, EstadoFila.EN_ESPERA)
                        .stream()
                        .map(filaAtencionMapper::toResponseDto))
                .toList();
    }

    @Override
    @Transactional
    public FilaAtencionResponseDto llamarPersona(Long filaId) {
        String usuarioId = authenticationService.getCurrentUserId();
        validarUsuarioSinAtencionActiva(usuarioId);

        FilaAtencion personaEnFila = findFilaOrThrow(filaId);

        if (personaEnFila.getEstado() != EstadoFila.EN_ESPERA) {
            throw new IllegalStateException(
                    "Esta persona no está en espera. Estado actual: " + personaEnFila.getEstado());
        }

        personaEnFila.setEstado(EstadoFila.EN_ATENCION);
        personaEnFila.setUsuarioAsignado(usuarioId);
        personaEnFila.setFechaInicioAtencion(LocalDateTime.now());

        FilaAtencion savedFila = filaAtencionRepository.save(personaEnFila);
        return filaAtencionMapper.toResponseDto(savedFila);
    }

    private void validarUsuarioSinAtencionActiva(String usuarioId) {
        if (!filaAtencionRepository.findByUsuarioAsignadoAndEstado(usuarioId, EstadoFila.EN_ATENCION).isEmpty()) {
            throw new IllegalStateException("El usuario ya tiene una atención activa.");
        }
    }

    private EtapaTramite findEtapaOrThrow(Long etapaId) {
        return etapaTramiteRepository.findById(etapaId)
                .orElseThrow(() -> new NotFounException("Etapa no encontrada con ID: " + etapaId));
    }

    private FilaAtencion findFilaOrThrow(Long filaId) {
        return filaAtencionRepository.findById(filaId)
                .orElseThrow(() -> new NotFounException("Fila de atención no encontrada con ID: " + filaId));
    }
}
