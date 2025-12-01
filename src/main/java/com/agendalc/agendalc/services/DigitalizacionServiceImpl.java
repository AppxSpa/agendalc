
package com.agendalc.agendalc.services;

import com.agendalc.agendalc.dto.DigitalizacionRequest;
import com.agendalc.agendalc.entities.ProcesoDigitalizacion;
import com.agendalc.agendalc.entities.Tramite;
import com.agendalc.agendalc.repositories.ProcesoDigitalizacionRepository;
import com.agendalc.agendalc.repositories.TramiteRepository;
import com.agendalc.agendalc.services.interfaces.DigitalizacionService;
import com.agendalc.agendalc.services.interfaces.DocumentoService;
import com.agendalc.agendalc.utils.RepositoryUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class DigitalizacionServiceImpl implements DigitalizacionService {

    private final ProcesoDigitalizacionRepository procesoDigitalizacionRepository;
    private final TramiteRepository tramiteRepository;
    private final DocumentoService documentoService;

    public DigitalizacionServiceImpl(ProcesoDigitalizacionRepository procesoDigitalizacionRepository,
            TramiteRepository tramiteRepository,
            DocumentoService documentoService) {
        this.procesoDigitalizacionRepository = procesoDigitalizacionRepository;
        this.tramiteRepository = tramiteRepository;
        this.documentoService = documentoService;
    }

    @Override
    @Transactional
    public void guardarDocumentosDigitalizados(DigitalizacionRequest request, List<MultipartFile> files) {
        Tramite tramite = RepositoryUtils.findOrThrow(tramiteRepository.findById(request.getTramiteId()),
                "Tramite no encontrado con id: " + request.getTramiteId());

        ProcesoDigitalizacion proceso = new ProcesoDigitalizacion();
        proceso.setRutPersona(request.getRutPersona());
        proceso.setTramite(tramite);
        proceso.setUsuarioResponsable(request.getUsuarioResponsable());
        proceso.setObservaciones(request.getObservaciones());

        ProcesoDigitalizacion savedProceso = procesoDigitalizacionRepository.save(proceso);

        for (MultipartFile file : files) {
            documentoService.crearYGuardarDocumento(file, savedProceso, tramite, request.getRutPersona());
        }
    }
}
