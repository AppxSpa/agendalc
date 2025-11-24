package com.agendalc.agendalc.services.mappers.saludmappers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.agendalc.agendalc.dto.SaludFormularioDto;
import com.agendalc.agendalc.entities.SaludFormulario;
import com.agendalc.agendalc.entities.SaludLicenciasOtorgadas;

@Component
public class LicenciasMapper {

     public void mapLicencias(SaludFormularioDto dto, SaludFormulario f) {
        if (dto.getLicenciasOtorgadas() == null || dto.getLicenciasOtorgadas().isEmpty())
            return;
        List<SaludLicenciasOtorgadas> licencias = new ArrayList<>();
        for (com.agendalc.agendalc.dto.SaludLicenciasDto licDto : dto.getLicenciasOtorgadas()) {
            SaludLicenciasOtorgadas l = new SaludLicenciasOtorgadas();
            if (licDto.getTipoLicencia() != null) {
                l.setTiposLicencias(licDto.getTipoLicencia());
            }
            l.setProfesional(licDto.isProfesional());
            l.setOtras(licDto.isOtras());
            l.setLeyAntigua(licDto.isLeyAntigua());
            l.setNoProfesional(licDto.isNoProfesional());
            l.setEspecial(licDto.isEspecial());
            l.setFormulario(f);
            licencias.add(l);
        }
        f.setLicenciasOtorgadas(licencias);
    }

}
