
package com.agendalc.agendalc.services;

import com.agendalc.agendalc.dto.DigitalizacionRequest;
import com.agendalc.agendalc.entities.Documento;
import com.agendalc.agendalc.entities.ProcesoDigitalizacion;
import com.agendalc.agendalc.entities.Tramite;
import com.agendalc.agendalc.exceptions.FileException;
import com.agendalc.agendalc.repositories.DocumentoRepository;
import com.agendalc.agendalc.repositories.ProcesoDigitalizacionRepository;
import com.agendalc.agendalc.repositories.TramiteRepository;
import com.agendalc.agendalc.services.interfaces.ArchivoService;
import com.agendalc.agendalc.services.interfaces.DigitalizacionService;
import com.agendalc.agendalc.utils.RepositoryUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

@Service
public class DigitalizacionServiceImpl implements DigitalizacionService {

    private final ProcesoDigitalizacionRepository procesoDigitalizacionRepository;
    private final DocumentoRepository documentoRepository;
    private final TramiteRepository tramiteRepository;
    private final ArchivoService archivoService;

    public DigitalizacionServiceImpl(ProcesoDigitalizacionRepository procesoDigitalizacionRepository,
            DocumentoRepository documentoRepository,
            TramiteRepository tramiteRepository,
            ArchivoService archivoService) {
        this.procesoDigitalizacionRepository = procesoDigitalizacionRepository;
        this.documentoRepository = documentoRepository;
        this.tramiteRepository = tramiteRepository;
        this.archivoService = archivoService;
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
            if (file != null && !file.isEmpty()) {
                try {
                    String nombreGuardado = archivoService.guardarArchivo(file);
                    Path rutaCompleta = archivoService.getRutaCompletaArchivo(nombreGuardado);

                    Documento documento = new Documento();
                    documento.setRutPersona(request.getRutPersona());
                    documento.setTramite(tramite);
                    // documentosTramite es nullable, se omite para la digitalización masiva
                    documento.setNombreArchivo(file.getOriginalFilename());
                    documento.setPathStorage(rutaCompleta.toString());
                    documento.setTipoMime(file.getContentType());
                    documento.setOrigenId(savedProceso.getId());
                    documento.setOrigenTipo("DIGITALIZACION");

                    documentoRepository.save(documento);

                } catch (IOException e) {
                    // Considerar una excepción personalizada y un manejo más robusto
                    throw new FileException("Error al guardar el archivo: " + file.getOriginalFilename());
                }
            }
        }
    }
}
