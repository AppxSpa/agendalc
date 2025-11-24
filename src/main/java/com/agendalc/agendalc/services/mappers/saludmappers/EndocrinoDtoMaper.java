package com.agendalc.agendalc.services.mappers.saludmappers;

import org.springframework.stereotype.Component;

import com.agendalc.agendalc.dto.SaludEndocrinoDto;
import com.agendalc.agendalc.entities.SaludEndocrino;

@Component
public class EndocrinoDtoMaper {

    public SaludEndocrinoDto toEndocrinoDto(SaludEndocrino e) {
        if (e == null)
            return null;
        SaludEndocrinoDto ed = new SaludEndocrinoDto();
        ed.setDiabetes(e.isDiabetes());
        return ed;
    }

}
