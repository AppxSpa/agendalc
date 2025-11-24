package com.agendalc.agendalc.services.mappers.saludmappers;

import org.springframework.stereotype.Component;

import com.agendalc.agendalc.dto.SaludFormularioDto;
import com.agendalc.agendalc.entities.SaludFormulario;
import com.agendalc.agendalc.entities.SaludMotriz;

@Component
public class MotrizMapper {

    public void mapMotriz(SaludFormularioDto dto, SaludFormulario f) {
        if (dto.getMotriz() == null)
            return;
        SaludMotriz m = new SaludMotriz();
        m.setPerdidaFuerza(dto.getMotriz().isPerdidaFuerza());
        m.setPerdidaExtremidades(dto.getMotriz().isPerdidaExtremidades());
        m.setFormulario(f);
        f.setMotriz(m);
    }

}
