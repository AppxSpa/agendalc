package com.agendalc.agendalc.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.agendalc.agendalc.entities.UsuarioEtapa;

public interface UsuarioEtapaRepository extends JpaRepository<UsuarioEtapa, Long> {
    
    /**
     * Busca todas las etapas asignadas a un ID de usuario específico.
     * @param usuarioId El ID del usuario.
     * @return Una lista de asignaciones de UsuarioEtapa.
     */
    List<UsuarioEtapa> findByUsuarioId(String usuarioId);
}
