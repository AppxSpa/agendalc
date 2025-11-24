package com.agendalc.agendalc.services.mappers.saludmappers;

import org.springframework.stereotype.Component;

import com.agendalc.agendalc.dto.SaludEstudiosDto;
import com.agendalc.agendalc.entities.SaludEstudios;

@Component
public class EstudiosDtoMapper {

    public SaludEstudiosDto toEstudiosDto(SaludEstudios e) {
        if (e == null)
            return null;
        SaludEstudiosDto ed = new SaludEstudiosDto();
        if (e.getNivelEstudio() != null) {
            try {
                ed.setNivelEstudio(
                        SaludEstudiosDto.NivelEstudio.valueOf(e.getNivelEstudio().name()));
            } catch (IllegalArgumentException ex) {
                // ignore
            }
        }
        return ed;
    }

}
