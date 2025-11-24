package com.agendalc.agendalc.services.mappers.saludmappers;

import org.springframework.stereotype.Component;

import com.agendalc.agendalc.dto.SaludFormularioDto;
import com.agendalc.agendalc.entities.SaludFormulario;
import com.agendalc.agendalc.entities.SaludOftalmologico;

@Component
public class OftalmologicoMapper {

    public void mapOftalmologico(SaludFormularioDto dto, SaludFormulario f) {
        if (dto.getOftalmologico() == null)
            return;
        SaludOftalmologico o = new SaludOftalmologico();

        if (dto.getOftalmologico().getCataratas() != null) {
            o.setCataratasOjoIzquierdo(dto.getOftalmologico().getCataratas().isOjoIzquierdo());
            o.setCataratasOjoDerecho(dto.getOftalmologico().getCataratas().isOjoDerecho());
        }

        if (dto.getOftalmologico().getGlaucoma() != null) {
            o.setGlaucomaOjoIzquierdo(dto.getOftalmologico().getGlaucoma().isOjoIzquierdo());
            o.setGlaucomaOjoDerecho(dto.getOftalmologico().getGlaucoma().isOjoDerecho());
        }

        if (dto.getOftalmologico().getRetina() != null) {
            o.setRetinaOjoIzquierdo(dto.getOftalmologico().getRetina().isOjoIzquierdo());
            o.setRetinaOjoDerecho(dto.getOftalmologico().getRetina().isOjoDerecho());
        }

        o.setUsaLentes(dto.getOftalmologico().isLentesCerca() || dto.getOftalmologico().isLentesLejos());
        if (dto.getOftalmologico().isLentesCerca() && dto.getOftalmologico().isLentesLejos()) {
            o.setTipoLente("Ambos");
        } else if (dto.getOftalmologico().isLentesCerca()) {
            o.setTipoLente("Cerca");
        } else if (dto.getOftalmologico().isLentesLejos()) {
            o.setTipoLente("Lejos");
        } else {
            o.setTipoLente("Ninguno");
        }

        o.setOperacionOjos(dto.getOftalmologico().isOperacionOjos());
        o.setFormulario(f);
        f.setOftalmologico(o);
    }

}
