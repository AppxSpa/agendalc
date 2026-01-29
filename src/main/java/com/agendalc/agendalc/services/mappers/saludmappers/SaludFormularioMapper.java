package com.agendalc.agendalc.services.mappers.saludmappers;

import org.springframework.stereotype.Component;

import com.agendalc.agendalc.dto.SaludFormularioDto;
import com.agendalc.agendalc.entities.SaludFormulario;

@Component
public class SaludFormularioMapper {

    private final SaludMappers saludMappers;

    public SaludFormularioMapper(SaludMappers saludMappers) {
        this.saludMappers = saludMappers;
    }

    public SaludFormularioDto toDto(SaludFormulario entity) {
        if (entity == null) {
            return null;
        }

        SaludFormularioDto dto = new SaludFormularioDto();
        dto.setIdDeclaracion(entity.getId());
        dto.setFechaFormulario(entity.getFechaFormulario());
        dto.setRut(entity.getRut());
        
        dto.setPersonales(saludMappers.personalesDtoMapper.toPersonalesDto(entity.getPersonales()));
        dto.setLicenciasOtorgadas(saludMappers.licenciasDtoMapper.toLicenciasDto(entity.getLicenciasOtorgadas()));
        dto.setEstudios(saludMappers.estudiosDtoMapper.toEstudiosDto(entity.getEstudios()));
        dto.setSituacionLaboral(saludMappers.laboralDtoMapper.toSituacionLaboralDto(entity.getSituacionLaboral()));
        dto.setProfesion(saludMappers.profesionDtoMapper.toProfesionDto(entity.getProfesion()));
        dto.setJornada(saludMappers.jornadaDtoMapper.toJornadaDto(entity.getJornada()));
        dto.setCardio(saludMappers.cardioDtoMapper.toCardioDto(entity.getCardio()));
        dto.setOftalmologico(saludMappers.oftalmologicoDtoMapper.toOftalmologicoDto(entity.getOftalmologico()));
        dto.setOtorrino(saludMappers.otorrinoDtoMapper.toOtorrinoDto(entity.getOtorrino()));
        dto.setNeurologico(saludMappers.neuroDtoMapper.toNeurologicoDto(entity.getNeurologico()));
        dto.setMotriz(saludMappers.motrizDtoMapper.toMotrizDto(entity.getMotriz()));
        dto.setRespiratorio(saludMappers.respiratorioDtoMapper.toRespiratorioDto(entity.getRespiratorio()));
        dto.setEndocrino(saludMappers.endocrinoDtoMaper.toEndocrinoDto(entity.getEndocrino()));
        dto.setOncologico(saludMappers.oncologicoDtoMapper.toOncologicoDto(entity.getOncologico()));
        dto.setInmunologico(saludMappers.inmunoDtoMapper.toInmunologicoDto(entity.getInmunologico()));
        dto.setOtros(saludMappers.otrosDtoMapper.toOtrosDto(entity.getOtros()));
        dto.setMedicamentos(saludMappers.medicamentosDtoMapper.toMedicamentosDto(entity.getMedicamentos()));
        dto.setConduccion(saludMappers.conduccionDtoMapper.toConduccionDto(entity.getConduccion()));
        if (entity.getFirma() != null) {
            dto.setFirma(entity.getFirma().getImagen());
        }

        return dto;
    }
}
