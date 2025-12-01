package com.agendalc.agendalc.services.mappers.saludmappers;

import org.springframework.stereotype.Component;

import com.agendalc.agendalc.dto.SaludFormularioDto;
import com.agendalc.agendalc.entities.SaludFormulario;
import com.agendalc.agendalc.entities.SaludOtorrino;

@Component
public class OtorrinoMapper {

    public void mapOtorrino(SaludFormularioDto dto, SaludFormulario f) {
        if (dto.getOtorrino() == null)
            return;
        SaludOtorrino o = new SaludOtorrino();
        o.setUsarAudifonos(dto.getOtorrino().isUsarAudifonos());
        o.setOperacionOido(dto.getOtorrino().isOperacionOido());
        o.setMareos(dto.getOtorrino().isMareos());
        o.setFormulario(f);
        f.setOtorrino(o);
    }

}
