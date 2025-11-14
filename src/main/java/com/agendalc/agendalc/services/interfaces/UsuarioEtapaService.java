package com.agendalc.agendalc.services.interfaces;

import java.util.List;

import com.agendalc.agendalc.dto.UsuarioEtapaRequestDto;
import com.agendalc.agendalc.dto.UsuarioEtapaResponseDto;

public interface UsuarioEtapaService {
    
    /**
     * Devuelve todas las asignaciones de usuario-etapa.
     * @return Lista de DTOs de respuesta.
     */
    List<UsuarioEtapaResponseDto> findAll();

    /**
     * Busca una asignación por su ID.
     * @param id El ID de la asignación.
     * @return El DTO de respuesta.
     */
    UsuarioEtapaResponseDto findById(Long id);

    /**
     * Busca todas las asignaciones para un ID de usuario.
     * @param usuarioId El ID del usuario.
     * @return Lista de DTOs de respuesta.
     */
    List<UsuarioEtapaResponseDto> findByUsuarioId(String usuarioId);

    /**
     * Crea o actualiza una asignación de usuario-etapa.
     * @param requestDto El DTO con los datos de la petición.
     * @return El DTO de la asignación guardada.
     */
    UsuarioEtapaResponseDto save(UsuarioEtapaRequestDto requestDto);

    /**
     * Elimina una asignación por su ID.
     * @param id El ID de la asignación a eliminar.
     */
    void delete(Long id);
}
