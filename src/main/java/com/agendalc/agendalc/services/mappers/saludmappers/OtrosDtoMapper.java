package com.agendalc.agendalc.services.mappers.saludmappers;

import org.springframework.stereotype.Component;

import com.agendalc.agendalc.dto.SaludOtrosDto;
import com.agendalc.agendalc.entities.SaludOtros;

@Component
public class OtrosDtoMapper {

    public SaludOtrosDto toOtrosDto(SaludOtros o) {
        if (o == null)
            return null;
        SaludOtrosDto od = new SaludOtrosDto();

        SaludOtrosDto.OperadoDto opDto = new SaludOtrosDto.OperadoDto();
        opDto.setOperado(o.isOperado());
        opDto.setDetalle(o.getDetalleOperado());
        od.setOperaciones(opDto);

        SaludOtrosDto.EnfermedadDto enfDto = new SaludOtrosDto.EnfermedadDto();
        enfDto.setTieneEnfermedad(o.isOtraEnfermedad());
        enfDto.setDetalle(o.getDetalleEnfermedad());
        od.setEnfermedad(enfDto);

        SaludOtrosDto.LicenciaMedicaDto licDto = new SaludOtrosDto.LicenciaMedicaDto();
        licDto.setLicencia(o.isLicenciaMedica());
        licDto.setDetalle(o.getDetalleLicenciaMedica());
        od.setLicencias(licDto);

        return od;
    }

}
