package com.agendalc.agendalc.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.agendalc.agendalc.entities.SaludFormulario;

public interface SaludFormularioRepository extends JpaRepository<SaludFormulario, Long> {
    Optional<SaludFormulario> findByRut(Integer rut);
    List<SaludFormulario> findByRutOrderByFechaFormularioDesc(Integer rut);
}
