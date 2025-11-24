package com.agendalc.agendalc.services.mappers.saludmappers;

import org.springframework.stereotype.Component;

import com.agendalc.agendalc.dto.SaludFirmaDto;

@Component
public class FirmaDtoMapper {

    public SaludFirmaDto toFirmaDto(String imagen) {
        if (imagen == null || imagen.isEmpty())
            return null;
        SaludFirmaDto fd = new SaludFirmaDto();
        fd.setImagen(imagen);
        return fd;
    }

}
