package com.agendalc.agendalc.services.mappers.saludmappers;

import org.springframework.stereotype.Component;

import com.agendalc.agendalc.dto.SaludSituacionLaboralDto;
import com.agendalc.agendalc.entities.SaludSituacionLaboral;

@Component
public class LaboralDtoMapper {

    public SaludSituacionLaboralDto toSituacionLaboralDto(SaludSituacionLaboral s) {
        if (s == null)
            return null;
        SaludSituacionLaboralDto sd = new SaludSituacionLaboralDto();
        if (s.getSituacionLaboral() != null) {
            try {
                sd.setSituacionLaboral(
                        SaludSituacionLaboralDto.SituacionLaboral.valueOf(s.getSituacionLaboral().name()));
            } catch (IllegalArgumentException ex) {
                // ignore
            }
        }
        return sd;
    }

}
