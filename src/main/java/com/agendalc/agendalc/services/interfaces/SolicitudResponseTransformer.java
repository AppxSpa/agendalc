package com.agendalc.agendalc.services.interfaces;

import com.agendalc.agendalc.dto.SolicitudResponseList;
import com.agendalc.agendalc.entities.Solicitud;

import java.util.List;

public interface SolicitudResponseTransformer {

    List<SolicitudResponseList> transform(List<Solicitud> solicitudes);
}