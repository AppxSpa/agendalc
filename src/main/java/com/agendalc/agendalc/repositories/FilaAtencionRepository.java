package com.agendalc.agendalc.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import com.agendalc.agendalc.entities.Cita;
import com.agendalc.agendalc.entities.EtapaTramite;
import com.agendalc.agendalc.entities.FilaAtencion;
import com.agendalc.agendalc.entities.enums.EstadoFila;

import jakarta.persistence.LockModeType;

public interface FilaAtencionRepository extends JpaRepository<FilaAtencion, Long> {

    /**
     * Busca las entradas de la fila para una etapa específica y un estado,
     * ordenadas por la fecha de llegada para un procesamiento FIFO.
     * 
     * @param etapaTramite La etapa del trámite.
     * @param estado       El estado de la fila (ej. EN_ESPERA).
     * @return Una lista de entradas de la fila.
     */
    List<FilaAtencion> findByEtapaTramiteAndEstadoOrderByFechaLlegadaAsc(EtapaTramite etapaTramite, EstadoFila estado);

    /**
     * Busca si una cita ya tiene una entrada en una etapa específica.
     * 
     * @param cita         La cita a buscar.
     * @param etapaTramite La etapa del trámite.
     * @return Un Optional que contiene la FilaAtencion si existe.
     */
    Optional<FilaAtencion> findByCitaAndEtapaTramite(Cita cita, EtapaTramite etapaTramite);

    /**
     * Busca las atenciones activas para un usuario específico.
     * 
     * @param usuarioId El ID del usuario.
     * @param estado    El estado de la fila (normalmente EN_ATENCION).
     * @return Una lista de atenciones activas para el usuario.
     */
    List<FilaAtencion> findByUsuarioAsignadoAndEstado(String usuarioId, EstadoFila estado);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<FilaAtencion> findFirstByEtapaTramiteAndEstadoOrderByFechaLlegadaAsc(EtapaTramite etapaTramite,
            EstadoFila estado);
}
