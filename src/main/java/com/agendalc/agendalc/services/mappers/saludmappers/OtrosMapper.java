package com.agendalc.agendalc.services.mappers.saludmappers;

import org.springframework.stereotype.Component;

import com.agendalc.agendalc.dto.SaludFormularioDto;
import com.agendalc.agendalc.entities.SaludFormulario;
import com.agendalc.agendalc.entities.SaludOtros;

@Component
public class OtrosMapper {

    public void mapOtros(SaludFormularioDto dto, SaludFormulario f) {
        if (dto.getOtros() == null)
            return;
        SaludOtros o = new SaludOtros();

        if (dto.getOtros().getOperaciones() != null) {
            o.setOperado(dto.getOtros().getOperaciones().isOperado());
            o.setDetalleOperado(dto.getOtros().getOperaciones().getDetalle());
        }

        if (dto.getOtros().getEnfermedad() != null) {
            o.setOtraEnfermedad(dto.getOtros().getEnfermedad().isTieneEnfermedad());
            o.setDetalleEnfermedad(dto.getOtros().getEnfermedad().getDetalle());
        }

        if (dto.getOtros().getLicencias() != null) {
            o.setLicenciaMedica(dto.getOtros().getLicencias().isLicencia());
            o.setDetalleLicenciaMedica(dto.getOtros().getLicencias().getDetalle());
        }

        o.setFormulario(f);
        f.setOtros(o);
    }

}
