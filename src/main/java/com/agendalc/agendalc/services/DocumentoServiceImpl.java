
package com.agendalc.agendalc.services;

import com.agendalc.agendalc.dto.DocumentoDto;
import com.agendalc.agendalc.entities.Documento;
import com.agendalc.agendalc.repositories.DocumentoRepository;
import com.agendalc.agendalc.services.interfaces.DocumentoService;
import com.agendalc.agendalc.services.mappers.DocumentoMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentoServiceImpl implements DocumentoService {

    private final DocumentoRepository documentoRepository;
    private final DocumentoMapper documentoMapper;

    public DocumentoServiceImpl(DocumentoRepository documentoRepository, DocumentoMapper documentoMapper) {
        this.documentoRepository = documentoRepository;
        this.documentoMapper = documentoMapper;
    }

    @Override
    public List<DocumentoDto> findByRut(String rut) {
        List<Documento> documentos = documentoRepository.findByRutPersona(rut);
        return documentoMapper.toDtoList(documentos);
    }
}
