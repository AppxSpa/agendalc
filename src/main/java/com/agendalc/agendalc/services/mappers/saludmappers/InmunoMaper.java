package com.agendalc.agendalc.services.mappers.saludmappers;

import org.springframework.stereotype.Component;

import com.agendalc.agendalc.dto.SaludFormularioDto;
import com.agendalc.agendalc.entities.SaludFormulario;
import com.agendalc.agendalc.entities.SaludInmunologico;

@Component
public class InmunoMaper {

     public void mapInmunologico(SaludFormularioDto dto, SaludFormulario f) {
        if (dto.getInmunologico() == null)
            return;
        SaludInmunologico i = new SaludInmunologico();
        i.setHigado(dto.getInmunologico().isHigado());
        i.setRenal(dto.getInmunologico().isRenal());
        i.setFormulario(f);
        f.setInmunologico(i);
    }

}
