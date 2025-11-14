package com.agendalc.agendalc.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.agendalc.agendalc.entities.EtapaTramite;

import java.util.Optional;

public interface EtapaTramiteRepository extends JpaRepository<EtapaTramite, Long> {
    List<EtapaTramite> findByTramiteIdTramiteOrderByOrdenAsc(Long tramiteId);

    Optional<EtapaTramite> findByTramite_IdTramiteAndOrden(Long tramiteId, int orden);
}
