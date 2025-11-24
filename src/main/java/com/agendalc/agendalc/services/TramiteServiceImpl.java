package com.agendalc.agendalc.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agendalc.agendalc.dto.TramiteRequest;
import com.agendalc.agendalc.dto.TramiteResponse;
import com.agendalc.agendalc.entities.Tramite;
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
    public Tramite updateTramite(Long id, Tramite tramite) {
        if (tramiteRepository.existsById(id)) {
            tramite.setIdTramite(id);
            return tramiteRepository.save(tramite);
        }
        return null;
    }

    @Override
    public boolean deleteTramiteById(Long id) {
        if (tramiteRepository.existsById(id)) {
            tramiteRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
