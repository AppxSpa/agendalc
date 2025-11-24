package com.agendalc.agendalc.services.mappers.saludmappers;

import org.springframework.stereotype.Component;

import com.agendalc.agendalc.dto.SaludProfesionDto;
import com.agendalc.agendalc.entities.SaludProfesion;

@Component
public class ProfesionDtoMapper {

    public SaludProfesionDto toProfesionDto(SaludProfesion p) {
        if (p == null)
            return null;
        SaludProfesionDto pd = new SaludProfesionDto();
        pd.setNombre(p.getNombre());
        return pd;
    }

}
