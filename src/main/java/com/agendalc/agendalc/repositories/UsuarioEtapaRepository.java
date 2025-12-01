package com.agendalc.agendalc.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.agendalc.agendalc.entities.UsuarioEtapa;

public interface UsuarioEtapaRepository extends JpaRepository<UsuarioEtapa, Long> {

    List<UsuarioEtapa> findByUsuarioId(String usuarioId);
}
