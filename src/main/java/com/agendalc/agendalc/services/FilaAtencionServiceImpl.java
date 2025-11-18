package com.agendalc.agendalc.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
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
import com.agendalc.agendalc.services.interfaces.FilaAtencionService;
import com.agendalc.agendalc.services.mappers.FilaAtencionMapper;

@Service
public class FilaAtencionServiceImpl implements FilaAtencionService {

    private final FilaAtencionRepository filaAtencionRepository;
    private final CitaRepository citaRepository;
    private final EtapaTramiteRepository etapaTramiteRepository;
    private final UsuarioEtapaRepository usuarioEtapaRepository;
    private final com.agendalc.agendalc.repositories.TramiteRepository tramiteRepository;
    private final FilaAtencionMapper filaAtencionMapper;

    public FilaAtencionServiceImpl(FilaAtencionRepository filaAtencionRepository, CitaRepository citaRepository,
            EtapaTramiteRepository etapaTramiteRepository, UsuarioEtapaRepository usuarioEtapaRepository,
            com.agendalc.agendalc.repositories.TramiteRepository tramiteRepository, FilaAtencionMapper filaAtencionMapper) {
        this.filaAtencionRepository = filaAtencionRepository;
        this.citaRepository = citaRepository;
        this.etapaTramiteRepository = etapaTramiteRepository;
        this.usuarioEtapaRepository = usuarioEtapaRepository;
        this.tramiteRepository = tramiteRepository;
        this.filaAtencionMapper = filaAtencionMapper;
    }

    @Override
    @Transactional
    public List<FilaAtencionResponseDto> iniciarProceso(Long citaId, com.agendalc.agendalc.dto.IniciarProcesosRequest request) {
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
                    .orElseThrow(() -> new NotFounException("El trámite ID: " + tramiteId + " no tiene etapas configuradas."));

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
        EtapaTramite etapa = etapaTramiteRepository.findById(etapaId)
                .orElseThrow(() -> new NotFounException("Etapa no encontrada con ID: " + etapaId));

        List<FilaAtencion> filas = filaAtencionRepository.findByEtapaTramiteAndEstadoOrderByFechaLlegadaAsc(etapa,
                EstadoFila.EN_ESPERA);

        return filaAtencionMapper.toResponseDtoList(filas);
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
        return filaAtencionMapper.toResponseDto(savedFila);
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

        filaActual.setFechaFinAtencion(LocalDateTime.now());
        filaActual.setEstado(EstadoFila.EN_ATENCION);
        FilaAtencion filaFinalizada = filaAtencionRepository.save(filaActual);

        moverASiguienteEtapaSiCorresponde(filaFinalizada);

        return filaAtencionMapper.toResponseDto(filaFinalizada);
    }

    private void moverASiguienteEtapaSiCorresponde(FilaAtencion filaFinalizada) {
        com.agendalc.agendalc.entities.Tramite tramiteDelFlujo = filaFinalizada.getTramite();
        if (tramiteDelFlujo == null) {
            return; // No hay trámite asociado, no hay siguiente etapa.
        }

        List<com.agendalc.agendalc.entities.TramiteEtapa> etapasDelTramite = tramiteDelFlujo.getTramiteEtapas();
        EtapaTramite etapaActual = filaFinalizada.getEtapaTramite();

        for (int i = 0; i < etapasDelTramite.size(); i++) {
            if (etapasDelTramite.get(i).getEtapaTramite().equals(etapaActual)) {
                // Si no es la última etapa, pasar a la siguiente
                if (i < etapasDelTramite.size() - 1) {
                    EtapaTramite siguienteEtapa = etapasDelTramite.get(i + 1).getEtapaTramite();
                    crearNuevaFilaSiNoExiste(filaFinalizada, tramiteDelFlujo, siguienteEtapa);
                }
                break; // Salir del bucle una vez encontrada y procesada la etapa
            }
        }
    }

    private void crearNuevaFilaSiNoExiste(FilaAtencion filaAnterior, com.agendalc.agendalc.entities.Tramite tramite, EtapaTramite nuevaEtapa) {
        // Evitar duplicados: solo crear la nueva fila si no existe una ya para esa cita y etapa
        if (filaAtencionRepository.findByCitaAndEtapaTramite(filaAnterior.getCita(), nuevaEtapa).isEmpty()) {
            FilaAtencion nuevaFila = FilaAtencion.builder()
                    .cita(filaAnterior.getCita())
                    .tramite(tramite)
                    .etapaTramite(nuevaEtapa)
                    .estado(EstadoFila.EN_ESPERA)
                    .fechaLlegada(LocalDateTime.now())
                    .build();
            filaAtencionRepository.save(nuevaFila);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public FilaAtencionResponseDto verAtencionActual() {
        String usuarioId = getCurrentUserId();
        return filaAtencionRepository.findByUsuarioAsignadoAndEstado(usuarioId, EstadoFila.EN_ATENCION)
                .stream().findFirst().map(filaAtencionMapper::toResponseDto).orElse(null);
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
                        .map(filaAtencionMapper::toResponseDto))
                .toList();
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
        return filaAtencionMapper.toResponseDto(savedFila);
    }
}
