package com.agendalc.agendalc.repositories;

import com.agendalc.agendalc.entities.TramiteLicencia;
import com.agendalc.agendalc.entities.Tramite;
import com.agendalc.agendalc.entities.enums.ClaseLicencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface TramiteLicenciaRepository extends JpaRepository<TramiteLicencia, Long> {
    
    /**
     * Busca TramiteLicencias por ClaseLicencia y Tramite
     */
    Optional<TramiteLicencia> findByClaseLicenciaAndTramite(ClaseLicencia claseLicencia, Tramite tramite);
    
    /**
     * Busca todas las TramiteLicencias de un Tramite
     */
    List<TramiteLicencia> findByTramite(Tramite tramite);
}
