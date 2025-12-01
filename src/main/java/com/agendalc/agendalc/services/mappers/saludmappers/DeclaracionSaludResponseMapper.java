package com.agendalc.agendalc.services.mappers.saludmappers;

import com.agendalc.agendalc.dto.DeclaracionSaludResponse;
import com.agendalc.agendalc.entities.SaludFormulario;

public interface DeclaracionSaludResponseMapper {
    DeclaracionSaludResponse toDto(SaludFormulario formulario);
}
