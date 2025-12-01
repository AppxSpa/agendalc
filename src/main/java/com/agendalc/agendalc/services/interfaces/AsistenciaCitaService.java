package com.agendalc.agendalc.services.interfaces;

import com.agendalc.agendalc.entities.AsistenciaCita;

public interface AsistenciaCitaService {
    /**
     * Registra la asistencia para una cita específica.
     * @param citaId El ID de la cita a la que se va a registrar la asistencia.
     * @return La entidad AsistenciaCita creada.
     */
    AsistenciaCita registrarAsistencia(Long citaId);
}
