
package com.agendalc.agendalc.services;

import com.agendalc.agendalc.dto.DocumentoDto;
import com.agendalc.agendalc.entities.Documento;
import com.agendalc.agendalc.entities.ProcesoDigitalizacion;
import com.agendalc.agendalc.entities.Tramite;
import com.agendalc.agendalc.exceptions.FileException;
import com.agendalc.agendalc.repositories.DocumentoRepository;
import com.agendalc.agendalc.services.interfaces.ArchivoService;
import com.agendalc.agendalc.services.interfaces.DocumentoService;
import com.agendalc.agendalc.services.mappers.DocumentoMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

@Service
public class DocumentoServiceImpl implements DocumentoService {

    private final DocumentoRepository documentoRepository;
    private final DocumentoMapper documentoMapper;
    private final ArchivoService archivoService;

    public DocumentoServiceImpl(DocumentoRepository documentoRepository, DocumentoMapper documentoMapper,
            ArchivoService archivoService) {
        this.documentoRepository = documentoRepository;
        this.documentoMapper = documentoMapper;
        this.archivoService = archivoService;
    }

    @Override
    public List<DocumentoDto> findByRut(String rut) {
        List<Documento> documentos = documentoRepository.findByRutPersona(rut);
        return documentoMapper.toDtoList(documentos);
    }

    @Override
    public void crearYGuardarDocumento(MultipartFile file, ProcesoDigitalizacion proceso, Tramite tramite,
            String rutPersona) {
        if (file == null || file.isEmpty()) {
            return;
        }
        try {
            String nombreGuardado = archivoService.guardarArchivo(file);
            Path rutaCompleta = archivoService.getRutaCompletaArchivo(nombreGuardado);

            Documento documento = new Documento();
            documento.setRutPersona(rutPersona);
            documento.setTramite(tramite);
            // documentosTramite es nullable, se omite para la digitalización masiva
            documento.setNombreArchivo(file.getOriginalFilename());
            documento.setPathStorage(rutaCompleta.toString());
            documento.setTipoMime(file.getContentType());
            documento.setOrigenId(proceso.getId());
            documento.setOrigenTipo("DIGITALIZACION");

            documentoRepository.save(documento);

        } catch (IOException e) {
            // Considerar una excepción personalizada y un manejo más robusto
            throw new FileException("Error al guardar el archivo: " + file.getOriginalFilename());
        }
    }
}
