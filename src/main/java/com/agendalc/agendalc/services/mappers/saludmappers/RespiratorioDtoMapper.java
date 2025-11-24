package com.agendalc.agendalc.services.mappers.saludmappers;

import org.springframework.stereotype.Component;

import com.agendalc.agendalc.dto.SaludRespiratorioDto;
import com.agendalc.agendalc.entities.SaludRespiratorio;

@Component
public class RespiratorioDtoMapper {

    public SaludRespiratorioDto toRespiratorioDto(SaludRespiratorio r) {
        if (r == null)
            return null;
        SaludRespiratorioDto rd = new SaludRespiratorioDto();
        rd.setDificultadRespirar(r.isDificultadRespirar());
        rd.setProblemasDormir(r.isProblemasDormir());
        rd.setFatiga(r.isFatiga());
        rd.setRonquidos(r.isRonquidos());
        return rd;
    }

}
