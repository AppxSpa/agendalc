package com.agendalc.agendalc.services.mappers.saludmappers;

import org.springframework.stereotype.Component;

import com.agendalc.agendalc.dto.SaludNeurologicoDto;
import com.agendalc.agendalc.entities.SaludNeurologico;

@Component
public class NeuroDtoMapper {

    public SaludNeurologicoDto toNeurologicoDto(SaludNeurologico n) {
        if (n == null)
            return null;
        SaludNeurologicoDto nd = new SaludNeurologicoDto();
        nd.setDesmayo(n.isDesmayo());
        nd.setEsclerosisMultiple(n.isEsclerosisMultiple());
        nd.setDificultadHablar(n.isDificultadHablar());
        nd.setPsiquiatrico(n.isPsiquiatrico());
        nd.setDerrameCerebral(n.isDerrameCerebral());
        nd.setEpilepsia(n.isEpilepsia());
        nd.setParkinson(n.isParkinson());
        nd.setOlvido(n.isOlvido());
        nd.setEmocional(n.isEmocional());
        return nd;
    }

}
