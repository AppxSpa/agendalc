package com.agendalc.agendalc.services.interfaces;

import java.util.List;

import com.agendalc.agendalc.dto.EtapaTramiteRequest;
import com.agendalc.agendalc.dto.EtapaTramiteResponse;

public interface EtapaTramiteService {
    List<EtapaTramiteResponse> findByTramiteId(Long tramiteId);
    List<EtapaTramiteResponse> create(List<EtapaTramiteRequest> request);
    EtapaTramiteResponse update(Long id, EtapaTramiteRequest request);
    void delete(Long id);
}
