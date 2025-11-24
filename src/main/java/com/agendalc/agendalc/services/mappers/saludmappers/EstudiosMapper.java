package com.agendalc.agendalc.services.mappers.saludmappers;

import org.springframework.stereotype.Component;

import com.agendalc.agendalc.dto.SaludFormularioDto;
import com.agendalc.agendalc.entities.SaludEstudios;
import com.agendalc.agendalc.entities.SaludFormulario;

@Component
public class EstudiosMapper {

     public void mapEstudios(SaludFormularioDto dto, SaludFormulario f) {
        if (dto.getEstudios() == null)
            return;
        SaludEstudios e = new SaludEstudios();
        try {
            if (dto.getEstudios().getNivelEstudio() != null) {
                e.setNivelEstudio(SaludEstudios.NivelEstudio.valueOf(dto.getEstudios().getNivelEstudio().name()));
            }
        } catch (Exception ex) {
            // ignore invalid enum values
        }
        e.setFormulario(f);
        f.setEstudios(e);
    }


}
