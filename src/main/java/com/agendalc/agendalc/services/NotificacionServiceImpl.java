package com.agendalc.agendalc.services;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.agendalc.agendalc.config.AppProperties;
import com.agendalc.agendalc.dto.PersonaResponse;
import com.agendalc.agendalc.entities.Cita;
import com.agendalc.agendalc.entities.Solicitud;
import com.agendalc.agendalc.services.interfaces.ApiMailService;
import com.agendalc.agendalc.services.interfaces.ApiPersonaService;
import com.agendalc.agendalc.services.interfaces.NotificacionService;
import com.agendalc.agendalc.services.mappers.CitaMapper;

@Service
public class NotificacionServiceImpl implements NotificacionService {

    private final ApiMailService apiMailService;
    private final ApiPersonaService apiPersonaService;
    private final AppProperties appProperties;
    private final CitaMapper citaMapper;

    public NotificacionServiceImpl(ApiMailService apiMailService, ApiPersonaService apiPersonaService,
            AppProperties appProperties, CitaMapper citaMapper) {
        this.apiMailService = apiMailService;
        this.apiPersonaService = apiPersonaService;
        this.appProperties = appProperties;
        this.citaMapper = citaMapper;
    }

    @Override
    public void enviarNotificacionSolicitudCreada(Solicitud solicitud) {
        Map<String, Object> variables = Map.of(
                "nombres", getNombresPersonaPorSolicitud(solicitud),
                "urlPlataforma", appProperties.getPlataformaUrl());
        apiMailService.sendEmail(getEmailPersonaPorSolicitud(solicitud), "Solicitud creada",
                "solicitud-template", variables);
    }

    @Override
    public void enviarNotificacionSolicitudAprobada(Solicitud solicitud) {
        Map<String, Object> variables = Map.of(
                "nombres", getNombresPersonaPorSolicitud(solicitud),
                "idSolicitud", solicitud.getIdSolicitud(),
                "urlPlataforma", appProperties.getPlataformaUrl());

        apiMailService.sendEmail(getEmailPersonaPorSolicitud(solicitud), "Solicitud Aprobada",
                "solicitud-aprobada-template", variables);
    }

    @Override
    public void enviarNotificacionCitaAgendada(Cita cita) {
        PersonaResponse persona = apiPersonaService.getPersonaInfo(cita.getRut());
        Map<String, Object> variables = citaMapper.createVariablesCorreoCita(cita, persona.getNombres());
        apiMailService.sendEmail(persona.getEmail(), "Agenda de hora", "cita-template", variables);
    }

    private String getEmailPersonaPorSolicitud(Solicitud solicitud) {
        Integer rut = solicitud.getRut();
        return apiPersonaService.getPersonaInfo(rut).getEmail();
    }

    private String getNombresPersonaPorSolicitud(Solicitud solicitud) {
        Integer rut = solicitud.getRut();
        return apiPersonaService.getPersonaInfo(rut).getNombres();
    }
}
