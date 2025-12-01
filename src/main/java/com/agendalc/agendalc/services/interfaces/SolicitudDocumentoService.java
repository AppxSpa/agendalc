package com.agendalc.agendalc.services.interfaces;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.agendalc.agendalc.dto.DocumentosSubidosRequest;
import com.agendalc.agendalc.entities.Solicitud;
import com.agendalc.agendalc.entities.Tramite;

public interface SolicitudDocumentoService {

    void guardarDocumentosNuevos(Solicitud solicitud, Tramite tramite, List<DocumentosSubidosRequest> documentos)
            throws IOException;

    void reemplazarDocumento(Long idSolicitud, Long idDocumento, MultipartFile file) throws IOException;

}
