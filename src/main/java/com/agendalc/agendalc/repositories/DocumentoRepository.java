
package com.agendalc.agendalc.repositories;

import com.agendalc.agendalc.entities.Documento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DocumentoRepository extends JpaRepository<Documento, Long> {

    @Query("SELECT d FROM Documento d WHERE d.rutPersona = :rut ORDER BY d.fechaCarga DESC")
    List<Documento> findByRutPersona(@Param("rut") String rut);

    List<Documento> findByOrigenIdAndOrigenTipo(Long origenId, String origenTipo);

}
