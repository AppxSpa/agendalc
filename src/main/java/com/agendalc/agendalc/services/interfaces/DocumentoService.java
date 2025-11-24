
package com.agendalc.agendalc.services.interfaces;

import com.agendalc.agendalc.dto.DocumentoDto;

import java.util.List;

public interface DocumentoService {
    List<DocumentoDto> findByRut(String rut);
}
