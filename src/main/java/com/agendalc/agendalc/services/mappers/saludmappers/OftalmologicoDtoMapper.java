package com.agendalc.agendalc.services.mappers.saludmappers;

import org.springframework.stereotype.Component;

import com.agendalc.agendalc.dto.CataratasDto;
import com.agendalc.agendalc.dto.GlaucomaDto;
import com.agendalc.agendalc.dto.RetinaDto;
import com.agendalc.agendalc.dto.SaludOftalmologicoDto;
import com.agendalc.agendalc.entities.SaludOftalmologico;

@Component
public class OftalmologicoDtoMapper {

    public SaludOftalmologicoDto toOftalmologicoDto(SaludOftalmologico o) {
        if (o == null)
            return null;
        SaludOftalmologicoDto od = new SaludOftalmologicoDto();

        CataratasDto cataratasDto = new CataratasDto();
        cataratasDto.setOjoIzquierdo(o.isCataratasOjoIzquierdo());
        cataratasDto.setOjoDerecho(o.isCataratasOjoDerecho());
        od.setCataratas(cataratasDto);

        GlaucomaDto glaucomaDto = new GlaucomaDto();
        glaucomaDto.setOjoIzquierdo(o.isGlaucomaOjoIzquierdo());
        glaucomaDto.setOjoDerecho(o.isGlaucomaOjoDerecho());
        od.setGlaucoma(glaucomaDto);

        RetinaDto retinaDto = new RetinaDto();
        retinaDto.setOjoIzquierdo(o.isRetinaOjoIzquierdo());
        retinaDto.setOjoDerecho(o.isRetinaOjoDerecho());
        od.setRetina(retinaDto);

        if (o.getTipoLente() != null) {
            if (o.getTipoLente().equals("Ambos")) {
                od.setLentesCerca(true);
                od.setLentesLejos(true);
            } else if (o.getTipoLente().equals("Cerca")) {
                od.setLentesCerca(true);
            } else if (o.getTipoLente().equals("Lejos")) {
                od.setLentesLejos(true);
            }
        }

        od.setOperacionOjos(o.isOperacionOjos());
        return od;
    }

}
