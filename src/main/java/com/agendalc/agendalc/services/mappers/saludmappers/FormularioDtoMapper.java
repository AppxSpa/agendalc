package com.agendalc.agendalc.services.mappers.saludmappers;

import org.springframework.stereotype.Component;

import com.agendalc.agendalc.dto.SaludFormularioDto;
import com.agendalc.agendalc.entities.SaludFormulario;

@Component
public class FormularioDtoMapper {

    private final SaludMappers saludMappers;

    public FormularioDtoMapper(SaludMappers saludMappers) {
        this.saludMappers = saludMappers;
    }

    public SaludFormularioDto mapToDto(SaludFormulario f) {
        SaludFormularioDto dto = new SaludFormularioDto();
        dto.setRut(f.getRut());
        if (f.getTramite() != null) {
            dto.setIdTramite(f.getTramite().getIdTramite());
        }
        dto.setIdDeclaracion(f.getId());
        dto.setPersonales(saludMappers.personalesDtoMapper.toPersonalesDto(f.getPersonales()));
        dto.setLicenciasOtorgadas(saludMappers.licenciasDtoMapper.toLicenciasDto(f.getLicenciasOtorgadas()));
        dto.setEstudios(saludMappers.estudiosDtoMapper.toEstudiosDto(f.getEstudios()));
        dto.setSituacionLaboral(saludMappers.laboralDtoMapper.toSituacionLaboralDto(f.getSituacionLaboral()));
        dto.setProfesion(saludMappers.profesionDtoMapper.toProfesionDto(f.getProfesion()));
        dto.setJornada(saludMappers.jornadaDtoMapper.toJornadaDto(f.getJornada()));
        dto.setCardio(saludMappers.cardioDtoMapper.toCardioDto(f.getCardio()));
        dto.setOftalmologico(saludMappers.oftalmologicoDtoMapper.toOftalmologicoDto(f.getOftalmologico()));
        dto.setOtorrino(saludMappers.otorrinoDtoMapper.toOtorrinoDto(f.getOtorrino()));
        dto.setNeurologico(saludMappers.neuroDtoMapper.toNeurologicoDto(f.getNeurologico()));
        dto.setMotriz(saludMappers.motrizDtoMapper.toMotrizDto(f.getMotriz()));
        dto.setRespiratorio(saludMappers.respiratorioDtoMapper.toRespiratorioDto(f.getRespiratorio()));
        dto.setEndocrino(saludMappers.endocrinoDtoMaper.toEndocrinoDto(f.getEndocrino()));
        dto.setOncologico(saludMappers.oncologicoDtoMapper.toOncologicoDto(f.getOncologico()));
        dto.setInmunologico(saludMappers.inmunoDtoMapper.toInmunologicoDto(f.getInmunologico()));
        dto.setOtros(saludMappers.otrosDtoMapper.toOtrosDto(f.getOtros()));
        dto.setMedicamentos(saludMappers.medicamentosDtoMapper.toMedicamentosDto(f.getMedicamentos()));
        dto.setConduccion(saludMappers.conduccionDtoMapper.toConduccionDto(f.getConduccion()));
        // El DTO de formulario almacena la firma como String (base64). Obtener la imagen directamente.
        dto.setFirma(f.getFirma() != null ? f.getFirma().getImagen() : null);

        return dto;
    }

}
