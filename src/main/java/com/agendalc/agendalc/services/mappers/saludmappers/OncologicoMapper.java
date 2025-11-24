package com.agendalc.agendalc.services.mappers.saludmappers;

import org.springframework.stereotype.Component;

import com.agendalc.agendalc.dto.SaludFormularioDto;
import com.agendalc.agendalc.entities.SaludFormulario;
import com.agendalc.agendalc.entities.SaludOncologico;

@Component
public class OncologicoMapper {

    public void mapOncologico(SaludFormularioDto dto, SaludFormulario f) {
        if (dto.getOncologico() == null)
            return;
        SaludOncologico o = new SaludOncologico();
        o.setSistemaNervioso(dto.getOncologico().isSistemaNervioso());
        o.setCabezaCuello(dto.getOncologico().isCabezaCuello());
        o.setTorax(dto.getOncologico().isTorax());
        o.setEndocrino(dto.getOncologico().isEndocrino());
        o.setReproductorMasculino(dto.getOncologico().isReproductorMasculino());
        o.setReproductorFemenino(dto.getOncologico().isReproductorFemenino());
        o.setOseoMuscular(dto.getOncologico().isOseoMuscular());
        o.setPiel(dto.getOncologico().isPiel());
        o.setOjos(dto.getOncologico().isOjos());
        o.setSistemaRespiratorio(dto.getOncologico().isSistemaRespiratorio());
        o.setSistemaDigestivo(dto.getOncologico().isSistemaDigestivo());
        o.setSistemaGenitourinario(dto.getOncologico().isSistemaGenitourinario());
        o.setHematologico(dto.getOncologico().isHematologico());
        o.setOtros(dto.getOncologico().isOtros());
        o.setFormulario(f);
        f.setOncologico(o);
    }

}
