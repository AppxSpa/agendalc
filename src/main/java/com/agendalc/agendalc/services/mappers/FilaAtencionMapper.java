package com.agendalc.agendalc.services.mappers;

import java.util.List;

import org.springframework.stereotype.Component;

import com.agendalc.agendalc.dto.FilaAtencionResponseDto;
import com.agendalc.agendalc.dto.PersonaResponse;
import com.agendalc.agendalc.entities.FilaAtencion;
import com.agendalc.agendalc.services.interfaces.ApiPersonaService;

@Component
public class FilaAtencionMapper {

    private final ApiPersonaService apiPersonaService;

    public FilaAtencionMapper(ApiPersonaService apiPersonaService) {
        this.apiPersonaService = apiPersonaService;
    }

    public FilaAtencionResponseDto toResponseDto(FilaAtencion fila) {
        PersonaResponse persona = apiPersonaService.getPersonaInfo(fila.getCita().getRut());

        return new FilaAtencionResponseDto(
                fila.getId(),
                fila.getCita().getIdCita(),
                persona,
                fila.getEtapaTramite().getId(),
                fila.getEtapaTramite().getNombre(),
                fila.getEstado(),
                fila.getUsuarioAsignado(),
                fila.getFechaLlegada(),
                fila.getFechaInicioAtencion(),
                fila.getFechaFinAtencion());
    }

    public List<FilaAtencionResponseDto> toResponseDtoList(List<FilaAtencion> filas) {
        return filas.stream()
                .map(this::toResponseDto)
                .toList();
    }
}
