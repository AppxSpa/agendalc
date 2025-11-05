package com.agendalc.agendalc.services.interfaces;

import java.util.Optional;

import com.agendalc.agendalc.dto.SaludFormularioDto;

public interface SaludService {
    SaludFormularioDto saveFormulario(SaludFormularioDto dto);

    Optional<SaludFormularioDto> findByRut(String rut);
}
