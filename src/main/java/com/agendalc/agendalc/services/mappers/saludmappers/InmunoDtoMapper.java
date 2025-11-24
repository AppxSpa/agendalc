package com.agendalc.agendalc.services.mappers.saludmappers;

import org.springframework.stereotype.Component;

import com.agendalc.agendalc.dto.SaludInmunologicoDto;
import com.agendalc.agendalc.entities.SaludInmunologico;

@Component
public class InmunoDtoMapper {

    public SaludInmunologicoDto toInmunologicoDto(SaludInmunologico i) {
        if (i == null)
            return null;
        SaludInmunologicoDto id = new SaludInmunologicoDto();
        id.setHigado(i.isHigado());
        id.setRenal(i.isRenal());
        return id;
    }

}
