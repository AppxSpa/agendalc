package com.agendalc.agendalc.services.mappers;

import java.util.List;

import org.springframework.stereotype.Component;

import com.agendalc.agendalc.dto.DocumentosTramiteResponse;
import com.agendalc.agendalc.dto.TramiteRequest;
import com.agendalc.agendalc.dto.TramiteResponse;
import com.agendalc.agendalc.entities.Tramite;

@Component
public class TramiteMapper {

    public List<TramiteResponse> mapToTRamiteResponseList(List<Tramite> tramites) {

        return tramites.stream().map(tramite -> {
            TramiteResponse response = new TramiteResponse();
            response.setIdTramite(tramite.getIdTramite());
            response.setNombreTramite(tramite.getNombre());
            response.setDescripcionTramite(tramite.getDescripcion());
            response.setPideDocumentos(tramite.isPideDocumentos());
            response.setRequiereSolicitud(tramite.isRequiereSolicitud());

            List<DocumentosTramiteResponse> documentos = tramite.getDocumentosRequeridos().stream()
                    .map(doc -> new DocumentosTramiteResponse(doc.getIdDocumento(), doc.getNombreDocumento()))
                    .toList();
            response.setDocumentosRequeridos(documentos);

            return response;
        }).toList();

    }

    public Tramite convertToEntity(TramiteRequest request) {
        Tramite tramite = new Tramite();
        tramite.setNombre(request.getNombre().toUpperCase());
        tramite.setDescripcion(request.getDescripcion());
        tramite.setPideDocumentos(request.isPideDocumentos());
        tramite.setRequiereSolicitud(request.isRequiereSolicitud());
        return tramite;
    }

}
