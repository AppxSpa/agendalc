package com.agendalc.agendalc.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agendalc.agendalc.dto.EtapaTramiteRequest;
import com.agendalc.agendalc.dto.EtapaTramiteResponse;
import com.agendalc.agendalc.entities.EtapaTramite;
import com.agendalc.agendalc.entities.Tramite;
import com.agendalc.agendalc.exceptions.NotFounException;
import com.agendalc.agendalc.repositories.EtapaTramiteRepository;
import com.agendalc.agendalc.repositories.TramiteRepository;
import com.agendalc.agendalc.services.interfaces.EtapaTramiteService;

@Service
public class EtapaTramiteServiceImpl implements EtapaTramiteService {

    private final EtapaTramiteRepository etapaTramiteRepository;
    private final TramiteRepository tramiteRepository;

    public EtapaTramiteServiceImpl(EtapaTramiteRepository etapaTramiteRepository, TramiteRepository tramiteRepository) {
        this.etapaTramiteRepository = etapaTramiteRepository;
        this.tramiteRepository = tramiteRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<EtapaTramiteResponse> findByTramiteId(Long tramiteId) {
        List<EtapaTramite> etapas = etapaTramiteRepository.findByTramiteIdTramiteOrderByOrdenAsc(tramiteId);
        return etapas.stream()
                .map(etapa -> new EtapaTramiteResponse(etapa.getId(), etapa.getNombre(), etapa.getOrden()))
                .toList();
    }

    @Override
    @Transactional
    public List<EtapaTramiteResponse> create(List<EtapaTramiteRequest> request) {
        return request.stream().map(req -> {
            Tramite tramite = tramiteRepository.findById(req.getTramiteId())
                    .orElseThrow(() -> new NotFounException("Tramite no encontrado"));

            EtapaTramite etapa = new EtapaTramite();
            etapa.setNombre(req.getNombre());
            etapa.setOrden(req.getOrden());
            etapa.setTramite(tramite);

            etapa = etapaTramiteRepository.save(etapa);

            return new EtapaTramiteResponse(etapa.getId(), etapa.getNombre(), etapa.getOrden());
        }).toList();
    }

    @Override
    @Transactional
    public EtapaTramiteResponse update(Long id, EtapaTramiteRequest request) {
        EtapaTramite etapa = etapaTramiteRepository.findById(id)
                .orElseThrow(() -> new NotFounException("Etapa no encontrada"));

        Tramite tramite = tramiteRepository.findById(request.getTramiteId())
                .orElseThrow(() -> new NotFounException("Tramite no encontrado"));

        etapa.setNombre(request.getNombre());
        etapa.setOrden(request.getOrden());
        etapa.setTramite(tramite);

        etapa = etapaTramiteRepository.save(etapa);

        return new EtapaTramiteResponse(etapa.getId(), etapa.getNombre(), etapa.getOrden());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        etapaTramiteRepository.deleteById(id);
    }
}
