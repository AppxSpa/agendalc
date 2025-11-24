package com.agendalc.agendalc.services.mappers.saludmappers;

import org.springframework.stereotype.Component;

import com.agendalc.agendalc.dto.SaludOtorrinoDto;
import com.agendalc.agendalc.entities.SaludOtorrino;

@Component
public class OtorrinoDtoMapper {

    public SaludOtorrinoDto toOtorrinoDto(SaludOtorrino o) {
        if (o == null)
            return null;
        SaludOtorrinoDto od = new SaludOtorrinoDto();
        od.setUsarAudifonos(o.isUsarAudifonos());
        od.setOperacionOido(o.isOperacionOido());
        od.setMareos(o.isMareos());
        return od;
    }

}
