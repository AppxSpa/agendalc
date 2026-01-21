package com.agendalc.agendalc.services.mappers;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.agendalc.agendalc.dto.DocumentosTramiteResponse;
import com.agendalc.agendalc.dto.TramiteRequest;
import com.agendalc.agendalc.dto.TramiteResponse;
import com.agendalc.agendalc.entities.Tramite;
import com.agendalc.agendalc.entities.TramiteLicencia;
import com.agendalc.agendalc.entities.enums.ClaseLicencia;

@Component
public class TramiteMapper {

    public List<TramiteResponse> mapToTRamiteResponseList(List<Tramite> tramites) {

        return tramites.stream().map(this::mapToTramiteResponse).toList();

    }

    public TramiteResponse mapToTramiteResponse(Tramite tramite) {
        TramiteResponse response = new TramiteResponse();
        response.setIdTramite(tramite.getIdTramite());
        response.setNombreTramite(tramite.getNombre());
        response.setDescripcionTramite(tramite.getDescripcion());
        response.setPideDocumentos(tramite.isPideDocumentos());
        response.setRequiereSolicitud(tramite.isRequiereSolicitud());
        response.setRequiereAgenda(tramite.isRequiereAgenda());
        response.setActivo(tramite.isActivo());

        List<DocumentosTramiteResponse> documentos = tramite.getDocumentosRequeridos().stream()
                .map(doc -> new DocumentosTramiteResponse(doc.getIdDocumento(), doc.getNombreDocumento()))
                .toList();
        response.setDocumentosRequeridos(documentos);

        if (tramite.getClasesLicencia() != null) {
            Set<ClaseLicencia> clases = tramite.getClasesLicencia().stream()
                    .map(TramiteLicencia::getClaseLicencia)
                    .collect(Collectors.toSet());
            response.setClasesLicencia(clases);
        }

        return response;
    }

    public Tramite convertToEntity(TramiteRequest request) {
        Tramite tramite = new Tramite();
        tramite.setNombre(request.getNombre().toUpperCase());
        tramite.setDescripcion(request.getDescripcion());
        tramite.setPideDocumentos(request.isPideDocumentos());
        tramite.setRequiereSolicitud(request.isRequiereSolicitud());
        tramite.setRequiereAgenda(request.isRequiereAgenda());
        tramite.setActivo(request.isActivo());

        if (request.getClasesLicencia() != null) {
            Set<TramiteLicencia> clasesLicencia = request.getClasesLicencia().stream()
                    .map(claseEnum -> {
                        TramiteLicencia tl = new TramiteLicencia();
                        tl.setClaseLicencia(claseEnum);
                        tl.setTramite(tramite);
                        return tl;
                    })
                    .collect(Collectors.toSet());
            tramite.setClasesLicencia(clasesLicencia);
        }

        return tramite;
    }

}
