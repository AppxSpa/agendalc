package com.agendalc.agendalc.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agendalc.agendalc.dto.TramiteRequest;
import com.agendalc.agendalc.dto.TramiteResponse;
import com.agendalc.agendalc.entities.Tramite;
import com.agendalc.agendalc.entities.TramiteLicencia;
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

        // Update simple fields
        tramiteExistente.setNombre(request.getNombre().toUpperCase());
        tramiteExistente.setDescripcion(request.getDescripcion());
        tramiteExistente.setPideDocumentos(request.isPideDocumentos());
        tramiteExistente.setRequiereSolicitud(request.isRequiereSolicitud());
        tramiteExistente.setRequiereAgenda(request.isRequiereAgenda());
        tramiteExistente.setActivo(request.isActivo());

        // Update the collection
        tramiteExistente.getClasesLicencia().clear(); // Clear the old collection
        if (request.getClasesLicencia() != null) {
            request.getClasesLicencia().forEach(claseEnum -> {
                TramiteLicencia tl = new TramiteLicencia();
                tl.setClaseLicencia(claseEnum);
                tl.setTramite(tramiteExistente);
                tramiteExistente.getClasesLicencia().add(tl); // Add the new ones
            });
        }

        return tramiteRepository.save(tramiteExistente);
    }

    @Override
    public boolean deleteTramiteById(Long id) {
        if (tramiteRepository.existsById(id)) {
            tramiteRepository.deleteById(id);
            return true;
        }
        return false;
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
