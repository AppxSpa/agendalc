package com.agendalc.agendalc.services.mappers.saludmappers;

import org.springframework.stereotype.Component;

import com.agendalc.agendalc.dto.SaludFormularioDto;
import com.agendalc.agendalc.entities.SaludFormulario;
import com.agendalc.agendalc.entities.SaludRespiratorio;

@Component
public class RespiratorioMapper {

     public void mapRespiratorio(SaludFormularioDto dto, SaludFormulario f) {
        if (dto.getRespiratorio() == null)
            return;
        SaludRespiratorio r = new SaludRespiratorio();
        r.setDificultadRespirar(dto.getRespiratorio().isDificultadRespirar());
        r.setProblemasDormir(dto.getRespiratorio().isProblemasDormir());
        r.setFatiga(dto.getRespiratorio().isFatiga());
        r.setRonquidos(dto.getRespiratorio().isRonquidos());
        r.setFormulario(f);
        f.setRespiratorio(r);
    }

}
