package com.agendalc.agendalc.services.mappers.saludmappers;

import org.springframework.stereotype.Component;

import com.agendalc.agendalc.entities.SaludFirma;
import com.agendalc.agendalc.entities.SaludFormulario;

@Component
public class FirmaMapper {

    public SaludFirma toEntity(String firmaBase64, SaludFormulario formulario) {
        if (firmaBase64 == null || formulario == null) {
            return null;
        }

        SaludFirma firma = new SaludFirma();
        firma.setImagen(firmaBase64);
        firma.setFormulario(formulario); // Asocia la firma con el formulario

        return firma;
    }
}