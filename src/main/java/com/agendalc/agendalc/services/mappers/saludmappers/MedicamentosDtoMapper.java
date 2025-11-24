package com.agendalc.agendalc.services.mappers.saludmappers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.agendalc.agendalc.dto.SaludMedicamentoDto;
import com.agendalc.agendalc.entities.SaludMedicamentos;

@Component
public class MedicamentosDtoMapper {

    public List<SaludMedicamentoDto> toMedicamentosDto(
            List<SaludMedicamentos> list) {
        if (list == null || list.isEmpty())
            return new java.util.ArrayList<>();
        List<SaludMedicamentoDto> meds = new ArrayList<>();
        for (SaludMedicamentos m : list) {
            SaludMedicamentoDto md = new SaludMedicamentoDto();
            md.setNombreMedicamento(m.getNombreMedicamento());
            md.setDosis(m.getDosis());
            md.setMotivo(m.getPorque());
            meds.add(md);
        }
        return meds;
    }

}
