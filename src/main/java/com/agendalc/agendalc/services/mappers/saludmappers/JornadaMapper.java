package com.agendalc.agendalc.services.mappers.saludmappers;

import org.springframework.stereotype.Component;

import com.agendalc.agendalc.dto.SaludFormularioDto;
import com.agendalc.agendalc.entities.SaludFormulario;
import com.agendalc.agendalc.entities.SaludJornada;

@Component
public class JornadaMapper {

    public void mapJornada(SaludFormularioDto dto, SaludFormulario f) {
        if (dto.getJornada() == null)
            return;
        SaludJornada j = new SaludJornada();
        try {
            if (dto.getJornada().getTipoJornada() != null) {
                j.setTipoJornada(SaludJornada.TipoJornada.valueOf(dto.getJornada().getTipoJornada().name()));
            }
            if (dto.getJornada().getSubtipoJornada() != null) {
                j.setSubtipoJornada(SaludJornada.SubtipoJornada.valueOf(dto.getJornada().getSubtipoJornada().name()));
            }
        } catch (Exception ex) {
            // ignore invalid enum values
        }
        j.setFormulario(f);
        f.setJornada(j);
    }

}
