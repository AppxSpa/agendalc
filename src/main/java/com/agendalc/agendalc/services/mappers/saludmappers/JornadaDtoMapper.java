package com.agendalc.agendalc.services.mappers.saludmappers;

import org.springframework.stereotype.Component;

import com.agendalc.agendalc.dto.SaludJornadaDto;
import com.agendalc.agendalc.entities.SaludJornada;

@Component
public class JornadaDtoMapper {

    public SaludJornadaDto toJornadaDto(SaludJornada j) {
        if (j == null)
            return null;
        SaludJornadaDto jd = new SaludJornadaDto();
        if (j.getTipoJornada() != null) {
            try {
                jd.setTipoJornada(
                        SaludJornadaDto.TipoJornada.valueOf(j.getTipoJornada().name()));
            } catch (IllegalArgumentException ex) {
                // ignore
            }
        }
        if (j.getSubtipoJornada() != null) {
            try {
                jd.setSubtipoJornada(SaludJornadaDto.SubtipoJornada
                        .valueOf(j.getSubtipoJornada().name()));
            } catch (IllegalArgumentException ex) {
                // ignore
            }
        }
        return jd;
    }

}
