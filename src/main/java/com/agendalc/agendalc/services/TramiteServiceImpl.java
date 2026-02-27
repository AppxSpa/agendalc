package com.agendalc.agendalc.services;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agendalc.agendalc.dto.TramiteRequest;
import com.agendalc.agendalc.dto.TramiteResponse;
import com.agendalc.agendalc.entities.Tramite;
import com.agendalc.agendalc.entities.TramiteLicencia;
import com.agendalc.agendalc.entities.enums.ClaseLicencia;
import com.agendalc.agendalc.exceptions.OperacionNoPermitidaException;
import com.agendalc.agendalc.repositories.TramiteRepository;
import com.agendalc.agendalc.services.interfaces.TramiteService;
import com.agendalc.agendalc.services.mappers.TramiteMapper;

@Service
public class TramiteServiceImpl implements TramiteService {

    private final TramiteRepository tramiteRepository;
    private final TramiteMapper tramiteMapper;

    public TramiteServiceImpl(TramiteRepository tramiteRepository, TramiteMapper tramiteMapper) {
        this.tramiteRepository = tramiteRepository;
        this.tramiteMapper = tramiteMapper;
    }

    @Transactional
    @Override
    public Tramite createTramite(TramiteRequest request) {

        Tramite tramite = tramiteMapper.convertToEntity(request);
        return tramiteRepository.save(tramite);
    }

    @Override
    public List<TramiteResponse> getAllTramites() {
        List<Tramite> tramites = tramiteRepository.findAll();

        return tramiteMapper.mapToTRamiteResponseList(tramites);

    }

    @Override
    public Tramite getTramiteById(Long id) {
        return tramiteRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("no existe el id"));
    }

    @Transactional
    @Override
    public Tramite updateTramite(Long id, TramiteRequest request) {
        Tramite tramiteExistente = tramiteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No existe el trámite con id: " + id));

        if (!Objects.equals(
                tramiteExistente.getNombre(), request.getNombre())) {

            tramiteExistente.setNombre(request.getNombre().toUpperCase());
        }

        if (!Objects.equals(
                tramiteExistente.getDescripcion(), request.getDescripcion())) {

            tramiteExistente.setDescripcion(request.getDescripcion());
        }

        // Booleans (sin if innecesarios)
        tramiteExistente.setActivo(request.isActivo());
        tramiteExistente.setPideDocumentos(request.isPideDocumentos());
        tramiteExistente.setRequiereSolicitud(request.isRequiereSolicitud());
        tramiteExistente.setRequiereAgenda(request.isRequiereAgenda());
        // Update the collection

        Set<ClaseLicencia> actuales = tramiteExistente.getClasesLicencia()
                .stream()
                .map(TramiteLicencia::getClaseLicencia)
                .collect(Collectors.toSet());

        Set<ClaseLicencia> nuevas = new HashSet<>(request.getClasesLicencia());

        if (!nuevas.containsAll(actuales)) {
            throw new IllegalArgumentException(
                    "No se pueden eliminar clases de licencia existentes.");
        }

        nuevas.stream()
                .filter(clase -> !actuales.contains(clase))
                .forEach(clase -> {
                    TramiteLicencia tl = new TramiteLicencia();
                    tl.setClaseLicencia(clase);
                    tl.setTramite(tramiteExistente);
                    tramiteExistente.getClasesLicencia().add(tl);
                });

        return tramiteRepository.save(tramiteExistente);
    }

    @Override
    public boolean deleteTramiteById(Long id) {
        if (!tramiteRepository.existsById(id)) {
            return false;
        }
        try {
            tramiteRepository.deleteById(id);
            return true;
        } catch (DataIntegrityViolationException e) {
            throw new OperacionNoPermitidaException(
                    "No se puede eliminar el trámite porque tiene movimientos asociados.");
        }
    }

    @Transactional
    @Override
    public void deleteDocumentosRequeridos(Long tramiteId, List<Long> documentoIds) {
        Tramite tramite = tramiteRepository.findById(tramiteId)
                .orElseThrow(() -> new IllegalArgumentException("No existe el trámite con id: " + tramiteId));

        tramite.getDocumentosRequeridos()
                .removeIf(documento -> documentoIds.contains(documento.getIdDocumento()));

        tramiteRepository.save(tramite);
    }

}