package com.agendalc.agendalc.services.mappers.saludmappers;

import org.springframework.stereotype.Component;

import com.agendalc.agendalc.dto.SaludFormularioDto;
import com.agendalc.agendalc.entities.SaludEndocrino;
import com.agendalc.agendalc.entities.SaludFormulario;

@Component
public class EndocrinoMapper {

    public void mapEndocrino(SaludFormularioDto dto, SaludFormulario f) {
        if (dto.getEndocrino() == null)
            return;
        SaludEndocrino e = new SaludEndocrino();
        e.setDiabetes(dto.getEndocrino().isDiabetes());
        e.setFormulario(f);
        f.setEndocrino(e);
    }

}
