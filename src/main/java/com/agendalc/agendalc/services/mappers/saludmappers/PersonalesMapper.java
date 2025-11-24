package com.agendalc.agendalc.services.mappers.saludmappers;

import org.springframework.stereotype.Component;

import com.agendalc.agendalc.dto.SaludFormularioDto;
import com.agendalc.agendalc.entities.SaludFormulario;
import com.agendalc.agendalc.entities.SaludPersonales;

@Component
public class PersonalesMapper {

    public void mapPersonales(SaludFormularioDto dto, SaludFormulario f) {
        if (dto.getPersonales() == null)
            return;
        SaludPersonales p = new SaludPersonales();
        p.setEdad(dto.getPersonales().getEdad());
        p.setAltura(dto.getPersonales().getAltura());
        p.setPeso(dto.getPersonales().getPeso());
        try {
            if (dto.getPersonales().getGenero() != null)
                p.setGenero(SaludPersonales.Genero.valueOf(dto.getPersonales().getGenero().name()));
        } catch (Exception ex) {
            // invalid genero -> ignore
        }
        p.setFormulario(f);
        f.setPersonales(p);
    }

}
