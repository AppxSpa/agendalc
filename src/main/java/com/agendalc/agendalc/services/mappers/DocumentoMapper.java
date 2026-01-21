
package com.agendalc.agendalc.services.mappers;

import com.agendalc.agendalc.dto.DocumentoDto;
import com.agendalc.agendalc.entities.Documento;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class DocumentoMapper {

    public DocumentoDto toDto(Documento documento) {
        if (documento == null) {
            return null;
        }

        DocumentoDto.Builder builder = DocumentoDto.builder()
                .id(documento.getId())
                .rutPersona(documento.getRutPersona())
                .nombreArchivo(documento.getNombreArchivo())
                .pathStorage(documento.getPathStorage())
                .tipoMime(documento.getTipoMime())
                .fechaCarga(documento.getFechaCarga())
                .origenId(documento.getOrigenId())
                .origenTipo(documento.getOrigenTipo());

        if (documento.getTramite() != null) {
            builder.tramiteId(documento.getTramite().getIdTramite())
                    .nombreTramite(documento.getTramite().getNombre());
        }

        if (documento.getDocumentosTramite() != null) {
            builder.documentosTramiteId(documento.getDocumentosTramite().getIdDocumento())
                    .nombreDocumento(documento.getDocumentosTramite().getNombreDocumento());
        }

        return builder.build();
    }

    public Documento toEntity(DocumentoDto dto) {
        if (dto == null) {
            return null;
        }

        Documento documento = new Documento();
        documento.setId(dto.getId());
        documento.setRutPersona(dto.getRutPersona());
        documento.setNombreArchivo(dto.getNombreArchivo());
        documento.setPathStorage(dto.getPathStorage());
        documento.setTipoMime(dto.getTipoMime());
        documento.setFechaCarga(dto.getFechaCarga());
        documento.setOrigenId(dto.getOrigenId());
        documento.setOrigenTipo(dto.getOrigenTipo());

        // Tramite and DocumentosTramite would need to be set from repositories
        // based on their IDs (dto.getTramiteId(), dto.getDocumentosTramiteId())
        // This mapping is simplified and might need adjustment in the service layer.

        return documento;
    }

    public List<DocumentoDto> toDtoList(List<Documento> documentos) {
        if (documentos == null) {
            return Collections.emptyList();
        }
        return documentos.stream()
                .map(this::toDto)
                .toList();
    }
}
