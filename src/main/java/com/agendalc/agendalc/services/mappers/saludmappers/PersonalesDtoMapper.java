package com.agendalc.agendalc.services.mappers.saludmappers;

import org.springframework.stereotype.Component;

import com.agendalc.agendalc.dto.SaludPersonalesDto;
import com.agendalc.agendalc.entities.SaludPersonales;

@Component
public class PersonalesDtoMapper {

    public SaludPersonalesDto toPersonalesDto(SaludPersonales p) {
        if (p == null)
            return null;
        SaludPersonalesDto pd = new SaludPersonalesDto();
        pd.setEdad(p.getEdad());
        pd.setAltura(p.getAltura());
        pd.setPeso(p.getPeso());
        if (p.getGenero() != null) {
            try {
                pd.setGenero(SaludPersonalesDto.Genero.valueOf(p.getGenero().name()));
            } catch (IllegalArgumentException ex) {
                // ignore
            }
        }
        return pd;
    }

}
