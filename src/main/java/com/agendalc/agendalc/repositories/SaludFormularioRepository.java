package com.agendalc.agendalc.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.agendalc.agendalc.entities.SaludFormulario;

@Repository
public interface SaludFormularioRepository extends JpaRepository<SaludFormulario, Long> {
    Optional<SaludFormulario> findByRut(Integer rut);
}
