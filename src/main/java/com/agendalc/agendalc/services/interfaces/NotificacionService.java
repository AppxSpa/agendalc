package com.agendalc.agendalc.services.interfaces;

import com.agendalc.agendalc.entities.Cita;
import com.agendalc.agendalc.entities.Solicitud;

public interface NotificacionService {
    void enviarNotificacionSolicitudCreada(Solicitud solicitud);
    void enviarNotificacionSolicitudAprobada(Solicitud solicitud);
    void enviarNotificacionCitaAgendada(Cita cita);
}
