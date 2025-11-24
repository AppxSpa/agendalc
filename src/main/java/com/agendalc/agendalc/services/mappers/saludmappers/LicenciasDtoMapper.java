package com.agendalc.agendalc.services.mappers.saludmappers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.agendalc.agendalc.dto.SaludLicenciasDto;
import com.agendalc.agendalc.entities.SaludLicenciasOtorgadas;

@Component
public class LicenciasDtoMapper {

    public List<SaludLicenciasDto> toLicenciasDto(
            List<SaludLicenciasOtorgadas> list) {
        if (list == null || list.isEmpty())
            return new ArrayList<>();

        List<SaludLicenciasDto> licenciasDtos = new ArrayList<>();
        for (SaludLicenciasOtorgadas l : list) {
            SaludLicenciasDto ld = new SaludLicenciasDto();
            if (l.getTiposLicencias() != null) {
                ld.setTipoLicencia(l.getTiposLicencias());
            }
            ld.setProfesional(l.isProfesional());
            ld.setOtras(l.isOtras());
            ld.setLeyAntigua(l.isLeyAntigua());
            ld.setNoProfesional(l.isNoProfesional());
            ld.setEspecial(l.isEspecial());
            licenciasDtos.add(ld);
        }

        return licenciasDtos;
    }

}
