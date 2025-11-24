package com.agendalc.agendalc.services.interfaces;

import com.agendalc.agendalc.dto.DeclaracionSaludResponse;
import com.agendalc.agendalc.dto.SaludFormularioDto;

import java.util.List;
import java.util.Optional;

public interface SaludService {

    SaludFormularioDto saveFormulario(SaludFormularioDto dto);

    Optional<SaludFormularioDto> findByRut(String rut);

    List<DeclaracionSaludResponse> findDeclaracionesByRut(String rut);

}
