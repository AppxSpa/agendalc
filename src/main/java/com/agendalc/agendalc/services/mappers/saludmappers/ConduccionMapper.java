package com.agendalc.agendalc.services.mappers.saludmappers;

import org.springframework.stereotype.Component;

import com.agendalc.agendalc.dto.SaludConduccionDto;
import com.agendalc.agendalc.dto.SaludFormularioDto;
import com.agendalc.agendalc.entities.SaludConduccion;
import com.agendalc.agendalc.entities.SaludFormulario;

@Component
public class ConduccionMapper {

      public void mapConduccion(SaludFormularioDto dto, SaludFormulario f) {
        if (dto.getConduccion() == null)
            return;
        SaludConduccion c = new SaludConduccion();
        SaludConduccionDto cd = dto.getConduccion();
        c.setTodoslosDias(cd.isTodoslosDias());
        c.setAlgunosDiasSemana(cd.isAlgunosDiasSemana());
        c.setAlgunosDiasMes(cd.isAlgunosDiasMes());
        c.setAlgunosDiasAnio(cd.isAlgunosDiasAnio());
        c.setUtilizaTrabajar(cd.isUtilizaTrabajar());
        c.setEvalucionesMedicas(cd.isEvalucionesMedicas());
        c.setCiudad(cd.isCiudad());
        c.setCarretera(cd.isCarretera());
        c.setAmbos(cd.isAmbos());
        c.setAccidentes(cd.isAccidentes());
        c.setDetalleAccidentes(cd.getDetalleAccidentes());
        c.setFormulario(f);
        f.setConduccion(c);
    }

}
