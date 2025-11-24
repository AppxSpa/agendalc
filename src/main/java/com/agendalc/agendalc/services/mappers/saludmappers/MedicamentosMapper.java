package com.agendalc.agendalc.services.mappers.saludmappers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.agendalc.agendalc.dto.SaludFormularioDto;
import com.agendalc.agendalc.dto.SaludMedicamentoDto;
import com.agendalc.agendalc.entities.SaludFormulario;
import com.agendalc.agendalc.entities.SaludMedicamentos;

@Component
public class MedicamentosMapper {

    public void mapMedicamentos(SaludFormularioDto dto, SaludFormulario f) {
        if (dto.getMedicamentos() == null || dto.getMedicamentos().isEmpty())
            return;
        List<SaludMedicamentos> meds = new ArrayList<>();
        for (SaludMedicamentoDto md : dto.getMedicamentos()) {
            SaludMedicamentos m = new SaludMedicamentos();
            m.setNombreMedicamento(md.getNombreMedicamento());
            m.setDosis(md.getDosis());
            m.setPorque(md.getMotivo());
            m.setFormulario(f);
            meds.add(m);
        }
        f.setMedicamentos(meds);
    }

}
