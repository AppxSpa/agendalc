package com.agendalc.agendalc.services.mappers.saludmappers;

import org.springframework.stereotype.Component;

import com.agendalc.agendalc.dto.SaludFormularioDto;
import com.agendalc.agendalc.entities.SaludFormulario;
import com.agendalc.agendalc.entities.SaludProfesion;

@Component
public class ProfesionMapper {

     public void mapProfesion(SaludFormularioDto dto, SaludFormulario f) {
        if (dto.getProfesion() == null)
            return;
        SaludProfesion pr = new SaludProfesion();
        pr.setNombre(dto.getProfesion().getNombre());
        pr.setFormulario(f);
        f.setProfesion(pr);
    }

}
