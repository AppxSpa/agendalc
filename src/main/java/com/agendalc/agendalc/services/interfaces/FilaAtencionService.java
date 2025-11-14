package com.agendalc.agendalc.services.interfaces;

import java.util.List;

import com.agendalc.agendalc.dto.FilaAtencionResponseDto;

public interface FilaAtencionService {
    
    /**
     * Inicia el proceso de atención para una cita, creando la primera entrada en la fila.
     * @param citaId El ID de la cita que inicia el proceso.
     * @return El DTO de la primera entrada en la fila.
     */
    FilaAtencionResponseDto iniciarProceso(Long citaId);

    /**
     * Obtiene la lista de personas en espera para una etapa específica.
     * @param etapaId El ID de la etapa.
     * @return Una lista de DTOs de las personas en la fila.
     */
    List<FilaAtencionResponseDto> verFilaPorEtapa(Long etapaId);

    /**
     * Llama a la siguiente persona en la fila de una etapa y se la asigna al usuario actual.
     * @param etapaId El ID de la etapa de donde se llamará.
     * @return El DTO de la persona que ha sido llamada y está ahora en atención.
     */
    FilaAtencionResponseDto llamarSiguiente(Long etapaId);

    /**
     * Finaliza la atención para una entrada de la fila y, si corresponde,
     * la mueve a la siguiente etapa.
     * @param filaAtencionId El ID de la fila de atención que se está finalizando.
     * @return El DTO de la fila de atención actualizada a estado FINALIZADO.
     */
    FilaAtencionResponseDto finalizarAtencion(Long filaAtencionId);

    /**
     * Obtiene la atención activa del usuario que realiza la petición.
     * @return El DTO de la atención activa, o null si no hay ninguna.
     */
    FilaAtencionResponseDto verAtencionActual();

    /**
     * Obtiene todas las filas de espera para las etapas asignadas al usuario actual.
     * @return Una lista de DTOs de las personas en las filas correspondientes.
     */
    List<FilaAtencionResponseDto> verMisFilasDeEspera();

    /**
     * Asigna una persona específica de la fila al usuario actual para iniciar la atención.
     * @param filaId El ID de la fila de atención que se llamará.
     * @return El DTO de la persona que ha sido llamada y está ahora en atención.
     */
    FilaAtencionResponseDto llamarPersona(Long filaId);
}
