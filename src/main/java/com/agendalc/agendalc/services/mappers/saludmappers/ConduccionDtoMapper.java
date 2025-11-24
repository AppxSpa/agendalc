package com.agendalc.agendalc.services.mappers.saludmappers;

import org.springframework.stereotype.Component;

import com.agendalc.agendalc.dto.SaludConduccionDto;
import com.agendalc.agendalc.entities.SaludConduccion;

@Component
public class ConduccionDtoMapper {

    public SaludConduccionDto toConduccionDto(SaludConduccion c) {
        if (c == null)
            return null;
        SaludConduccionDto cd = new SaludConduccionDto();
        cd.setTodoslosDias(c.isTodoslosDias());
        cd.setAlgunosDiasSemana(c.isAlgunosDiasSemana());
        cd.setAlgunosDiasMes(c.isAlgunosDiasMes());
        cd.setAlgunosDiasAnio(c.isAlgunosDiasAnio());
        cd.setUtilizaTrabajar(c.isUtilizaTrabajar());
        cd.setEvalucionesMedicas(c.isEvalucionesMedicas());
        cd.setCiudad(c.isCiudad());
        cd.setCarretera(c.isCarretera());
        cd.setAmbos(c.isAmbos());
        cd.setAccidentes(c.isAccidentes());
        cd.setDetalleAccidentes(c.getDetalleAccidentes());
        return cd;
    }

}
