package com.agendalc.agendalc.services.mappers.saludmappers;

import org.springframework.stereotype.Component;

import com.agendalc.agendalc.dto.SaludCardioDto;
import com.agendalc.agendalc.entities.SaludCardio;

@Component
public class CardioDtoMapper {
    
    public SaludCardioDto toCardioDto(SaludCardio c) {
        if (c == null)
            return null;
        com.agendalc.agendalc.dto.SaludCardioDto cd = new com.agendalc.agendalc.dto.SaludCardioDto();
        cd.setMarcapasos(c.isMarcapasos());
        cd.setBypass(c.isBypass());
        cd.setInsuficienciaCardiaca(c.isInsuficienciaCardiaca());
        cd.setAnginas(c.isAnginas());
        cd.setPalpitaciones(c.isPalpitaciones());
        cd.setValvula(c.isValvula());
        cd.setHipertension(c.isHipertension());
        cd.setInfarto(c.isInfarto());
        cd.setArritmias(c.isArritmias());
        cd.setDolorPecho(c.isDolorPecho());
        return cd;
    }

}
