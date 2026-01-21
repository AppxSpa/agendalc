package com.agendalc.agendalc.services.interfaces;

import java.util.List;

import com.agendalc.agendalc.dto.TramiteRequest;
import com.agendalc.agendalc.dto.TramiteResponse;
import com.agendalc.agendalc.entities.Tramite;

public interface TramiteService {

    Tramite createTramite(TramiteRequest request);

    List<TramiteResponse> getAllTramites();

    Tramite getTramiteById(Long id);

    Tramite updateTramite(Long id, TramiteRequest request);

    boolean deleteTramiteById(Long id);

    void deleteDocumentosRequeridos(Long tramiteId, List<Long> documentoIds);

}
