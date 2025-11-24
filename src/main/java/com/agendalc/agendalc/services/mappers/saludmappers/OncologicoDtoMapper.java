package com.agendalc.agendalc.services.mappers.saludmappers;

import org.springframework.stereotype.Component;

import com.agendalc.agendalc.dto.SaludOncologicoDto;
import com.agendalc.agendalc.entities.SaludOncologico;

@Component
public class OncologicoDtoMapper {

    public SaludOncologicoDto toOncologicoDto(SaludOncologico o) {
        if (o == null)
            return null;
        SaludOncologicoDto od = new SaludOncologicoDto();
        od.setSistemaNervioso(o.isSistemaNervioso());
        od.setCabezaCuello(o.isCabezaCuello());
        od.setTorax(o.isTorax());
        od.setEndocrino(o.isEndocrino());
        od.setReproductorMasculino(o.isReproductorMasculino());
        od.setReproductorFemenino(o.isReproductorFemenino());
        od.setOseoMuscular(o.isOseoMuscular());
        od.setPiel(o.isPiel());
        od.setOjos(o.isOjos());
        od.setSistemaRespiratorio(o.isSistemaRespiratorio());
        od.setSistemaDigestivo(o.isSistemaDigestivo());
        od.setSistemaGenitourinario(o.isSistemaGenitourinario());
        od.setHematologico(o.isHematologico());
        od.setOtros(o.isOtros());
        return od;
    }

}
