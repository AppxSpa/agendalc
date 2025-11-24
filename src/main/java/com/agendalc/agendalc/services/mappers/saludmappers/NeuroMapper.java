package com.agendalc.agendalc.services.mappers.saludmappers;

import org.springframework.stereotype.Component;

import com.agendalc.agendalc.dto.SaludFormularioDto;
import com.agendalc.agendalc.entities.SaludFormulario;
import com.agendalc.agendalc.entities.SaludNeurologico;

@Component
public class NeuroMapper {

    public void mapNeurologico(SaludFormularioDto dto, SaludFormulario f) {
        if (dto.getNeurologico() == null)
            return;
        SaludNeurologico n = new SaludNeurologico();
        n.setDesmayo(dto.getNeurologico().isDesmayo());
        n.setEsclerosisMultiple(dto.getNeurologico().isEsclerosisMultiple());
        n.setDificultadHablar(dto.getNeurologico().isDificultadHablar());
        n.setPsiquiatrico(dto.getNeurologico().isPsiquiatrico());
        n.setDerrameCerebral(dto.getNeurologico().isDerrameCerebral());
        n.setEpilepsia(dto.getNeurologico().isEpilepsia());
        n.setParkinson(dto.getNeurologico().isParkinson());
        n.setOlvido(dto.getNeurologico().isOlvido());
        n.setEmocional(dto.getNeurologico().isEmocional());
        n.setFormulario(f);
        f.setNeurologico(n);
    }

}
