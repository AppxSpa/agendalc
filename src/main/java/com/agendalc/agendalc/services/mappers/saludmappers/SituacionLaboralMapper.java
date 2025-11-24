package com.agendalc.agendalc.services.mappers.saludmappers;

import org.springframework.stereotype.Component;

import com.agendalc.agendalc.dto.SaludFormularioDto;
import com.agendalc.agendalc.entities.SaludFormulario;
import com.agendalc.agendalc.entities.SaludSituacionLaboral;

@Component
public class SituacionLaboralMapper {

     public void mapSituacionLaboral(SaludFormularioDto dto, SaludFormulario f) {
        if (dto.getSituacionLaboral() == null)
            return;
        SaludSituacionLaboral s = new SaludSituacionLaboral();
        try {
            if (dto.getSituacionLaboral().getSituacionLaboral() != null) {
                s.setSituacionLaboral(SaludSituacionLaboral.SituacionLaboral
                        .valueOf(dto.getSituacionLaboral().getSituacionLaboral().name()));
            }
        } catch (Exception ex) {
            // ignore invalid enum values
        }
        s.setFormulario(f);
        f.setSituacionLaboral(s);
    }


}
