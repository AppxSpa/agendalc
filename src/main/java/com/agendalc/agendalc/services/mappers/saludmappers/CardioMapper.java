package com.agendalc.agendalc.services.mappers.saludmappers;

import org.springframework.stereotype.Component;

import com.agendalc.agendalc.dto.SaludFormularioDto;
import com.agendalc.agendalc.entities.SaludCardio;
import com.agendalc.agendalc.entities.SaludFormulario;

@Component
public class CardioMapper {

    public void mapCardio(SaludFormularioDto dto, SaludFormulario f) {
        if (dto.getCardio() == null)
            return;
        SaludCardio c = new SaludCardio();
        c.setMarcapasos(dto.getCardio().isMarcapasos());
        c.setBypass(dto.getCardio().isBypass());
        c.setInsuficienciaCardiaca(dto.getCardio().isInsuficienciaCardiaca());
        c.setAnginas(dto.getCardio().isAnginas());
        c.setPalpitaciones(dto.getCardio().isPalpitaciones());
        c.setValvula(dto.getCardio().isValvula());
        c.setHipertension(dto.getCardio().isHipertension());
        c.setInfarto(dto.getCardio().isInfarto());
        c.setArritmias(dto.getCardio().isArritmias());
        c.setDolorPecho(dto.getCardio().isDolorPecho());
        c.setFormulario(f);
        f.setCardio(c);
    }

}
