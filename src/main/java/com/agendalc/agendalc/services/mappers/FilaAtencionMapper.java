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

        return new FilaAtencionResponseDto.Builder()
                .filaId(fila.getId())
                .citaId(fila.getCita().getIdCita())
                .persona(persona)
                .etapaId(fila.getEtapaTramite().getId())
                .nombreEtapa(fila.getEtapaTramite().getNombre())
                .estado(fila.getEstado())
                .usuarioAsignado(fila.getUsuarioAsignado())
                .fechaLlegada(fila.getFechaLlegada())
                .fechaInicioAtencion(fila.getFechaInicioAtencion())
                .fechaFinAtencion(fila.getFechaFinAtencion())
                .build();
    }

    public List<FilaAtencionResponseDto> toResponseDtoList(List<FilaAtencion> filas) {
        return filas.stream()
                .map(this::toResponseDto)
                .toList();
    }
}
