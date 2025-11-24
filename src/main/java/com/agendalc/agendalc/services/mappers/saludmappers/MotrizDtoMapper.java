package com.agendalc.agendalc.services.mappers.saludmappers;

import org.springframework.stereotype.Component;

import com.agendalc.agendalc.dto.SaludMotrizDto;
import com.agendalc.agendalc.entities.SaludMotriz;

@Component
public class MotrizDtoMapper {

     public SaludMotrizDto toMotrizDto(SaludMotriz m) {
        if (m == null)
            return null;
        SaludMotrizDto md = new SaludMotrizDto();
        md.setPerdidaFuerza(m.isPerdidaFuerza());
        md.setPerdidaExtremidades(m.isPerdidaExtremidades());
        return md;
    }


}
