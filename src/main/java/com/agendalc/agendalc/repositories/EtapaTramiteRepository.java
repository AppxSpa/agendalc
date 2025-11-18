package com.agendalc.agendalc.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.agendalc.agendalc.entities.EtapaTramite;


public interface EtapaTramiteRepository extends JpaRepository<EtapaTramite, Long> {
    List<EtapaTramite> findByTramiteIdTramiteOrderByOrdenAsc(Long tramiteId);
}
