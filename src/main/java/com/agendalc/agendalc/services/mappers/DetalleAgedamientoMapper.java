package com.agendalc.agendalc.services.mappers;

import java.time.LocalTime;
import java.util.List;

import org.springframework.stereotype.Component;

import com.agendalc.agendalc.dto.DetalleAgendamientoContribuyente;
import com.agendalc.agendalc.dto.PersonaResponse;
import com.agendalc.agendalc.entities.Cita;
import com.agendalc.agendalc.repositories.AsistenciaCitaRepository;
import com.agendalc.agendalc.services.interfaces.ApiPersonaService;

@Component
public class DetalleAgedamientoMapper {

    private final AsistenciaCitaRepository asistenciaCitaRepository;

    private final ApiPersonaService apiPersonaService;

    public DetalleAgedamientoMapper(ApiPersonaService apiPersonaService,
            AsistenciaCitaRepository asistenciaCitaRepository) {
        this.apiPersonaService = apiPersonaService;
        this.asistenciaCitaRepository = asistenciaCitaRepository;
    }

    public List<DetalleAgendamientoContribuyente> detalleAgendamientoContribuyente(List<Cita> citas) {

        return citas.stream().map(cita -> {
            DetalleAgendamientoContribuyente detalle = new DetalleAgendamientoContribuyente();

            PersonaResponse persona = apiPersonaService.getPersonaInfo(cita.getRut());

            detalle.setRut(persona.getRutCompleto());
            detalle.setNombre(persona.getNombres());
            detalle.setApellidoPaterno(persona.getPaterno());
            detalle.setApellidoMaterno(persona.getMaterno());
            detalle.setTipoTramte(cita.nombreTramite());
            detalle.setHoraCita(formatHora(cita.getFechaHora().toLocalTime()));
            detalle.setAsistencia(determinarAsistencia(cita)); 
            return detalle;
        }).toList();
    }

    private String formatHora(LocalTime hora) {
        return hora.toString().substring(0, 5);
    }

    private boolean determinarAsistencia(Cita cita) {
        return asistenciaCitaRepository.existsByCitaIdCita(cita.getIdCita());
    }

}
