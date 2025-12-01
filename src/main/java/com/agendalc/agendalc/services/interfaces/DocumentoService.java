
package com.agendalc.agendalc.services.interfaces;

import com.agendalc.agendalc.dto.DocumentoDto;
import com.agendalc.agendalc.entities.ProcesoDigitalizacion;
import com.agendalc.agendalc.entities.Tramite;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface DocumentoService {
    List<DocumentoDto> findByRut(String rut);

    void crearYGuardarDocumento(MultipartFile file, ProcesoDigitalizacion proceso, Tramite tramite, String rutPersona);
}
