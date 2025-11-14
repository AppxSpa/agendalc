package com.agendalc.agendalc.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agendalc.agendalc.dto.CitaDelDiaResponseDto;
import com.agendalc.agendalc.dto.CitaDto;
import com.agendalc.agendalc.dto.CitaRequest;
import com.agendalc.agendalc.dto.PersonaResponse;
import com.agendalc.agendalc.dto.SolicitudCitaResponse;
import com.agendalc.agendalc.entities.Agenda;
import com.agendalc.agendalc.entities.BloqueHorario;
import com.agendalc.agendalc.entities.Cita;
import com.agendalc.agendalc.entities.SaludFormulario;
import com.agendalc.agendalc.entities.Solicitud;
import com.agendalc.agendalc.exceptions.NotFounException;
import com.agendalc.agendalc.repositories.CitaRepository;
import com.agendalc.agendalc.repositories.SaludFormularioRepository;
import com.agendalc.agendalc.repositories.SolicitudRepository;
import com.agendalc.agendalc.services.interfaces.AgendaService;
import com.agendalc.agendalc.services.interfaces.ApiMailService;
import com.agendalc.agendalc.services.interfaces.ApiPersonaService;
import com.agendalc.agendalc.services.interfaces.BloqueHorarioService;
import com.agendalc.agendalc.services.interfaces.CitaService;
import com.agendalc.agendalc.utils.RepositoryUtils;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CitaServiceImpl implements CitaService {

    private final CitaRepository citaRepository;

    private final AgendaService agendaService;

    private final BloqueHorarioService bloqueHorarioService;

    private final ApiPersonaService apiPersonaService;

    private final ApiMailService apiMailService;

    private final SolicitudRepository solicitudRepository;

    private final SaludFormularioRepository saludFormularioRepository;

    public CitaServiceImpl(CitaRepository citaRepository, AgendaService agendaService,
            BloqueHorarioService bloqueHorarioService,
            ApiPersonaService apiPersonaService,
            ApiMailService apiMailService,
            SolicitudRepository solicitudRepository, SaludFormularioRepository saludFormularioRepository) {
        this.citaRepository = citaRepository;
        this.agendaService = agendaService;
        this.bloqueHorarioService = bloqueHorarioService;
        this.apiPersonaService = apiPersonaService;
        this.apiMailService = apiMailService;
        this.solicitudRepository = solicitudRepository;
        this.saludFormularioRepository = saludFormularioRepository;
    }

    @Transactional
    @Override
    public CitaDto createCita(CitaRequest citaRequest) {
        Agenda agenda = agendaService.findById(citaRequest.getIdAgenda());

        BloqueHorario bloqueHorario = bloqueHorarioService.findById(citaRequest.getIdBloqueHorario());

        if (!agenda.getBloquesHorarios().contains(bloqueHorario)) {
            throw new IllegalArgumentException("El bloque horario no pertenece a la agenda seleccionada");
        }

        if (bloqueHorario.getCuposDisponibles() <= 0) {
            throw new IllegalStateException("No hay cupos disponibles en este bloque horario");
        }

        PersonaResponse persona = apiPersonaService.getPersonaInfo(citaRequest.getRut());

        if (persona == null) {
            throw new EntityNotFoundException("Persona no econtrada");

        }

        Cita cita = new Cita();
        cita.setRut(citaRequest.getRut());
        cita.setAgenda(agenda);
        cita.setBloqueHorario(bloqueHorario);

        // Lógica para asociar SaludFormulario
        if (citaRequest.getIdSaludFormulario() != null) {
            SaludFormulario saludFormulario = saludFormularioRepository.findById(citaRequest.getIdSaludFormulario())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "SaludFormulario no encontrado con id: " + citaRequest.getIdSaludFormulario()));
            cita.setSaludFormulario(saludFormulario);
        }

        cita = citaRepository.save(cita);

        bloqueHorario.setCuposDisponibles(bloqueHorario.getCuposDisponibles() - 1);
        bloqueHorarioService.save(bloqueHorario);

        CitaDto citaDto = new CitaDto(cita);

        HashMap<String, Object> variables = createVariablesCorreoCita(cita, persona.getNombres());

        apiMailService.sendEmail(persona.getEmail(), "Agenda de hora", "cita-template", variables);

        return citaDto;
    }

    private HashMap<String, Object> createVariablesCorreoCita(Cita cita, String nombre) {

        HashMap<String, Object> variables = new HashMap<>();

        variables.put("nombre", nombre);
        variables.put("fecha", obiteneFechaCita(cita));
        variables.put("hora", obtenerHoraCita(cita));
        return variables;
    }

    private LocalDate obiteneFechaCita(Cita cita) {
        return cita.getFechaAgenda();
    }

    private LocalTime obtenerHoraCita(Cita cita) {
        return cita.getHoraInicioBloqueHoraio();
    }

    @Override
    public Cita findById(Long id) {
        return RepositoryUtils.findOrThrow(citaRepository.findById(id),
                String.format("No se encontro la cita %d ", id));

    }

    @Override
    public List<SolicitudCitaResponse> getCitaByRut(Integer rut) {
        List<Cita> citas = citaRepository.findByRut(rut);

        if (citas.isEmpty()) {
            throw new IllegalArgumentException("No hay citas para el rut");
        }

        return citas.stream()
                .map(cita -> {

                    SolicitudCitaResponse dto = new SolicitudCitaResponse();

                    Solicitud solicitud = solicitudRepository
                            .findFirstByRutAndTramiteOrderByFechaSolicitudDesc(cita.getRut(), cita.getTramite());
                    if (solicitud != null) {
                        dto.setFechaSolicitud(solicitud.getFechaSolicitud());
                    }

                    dto.setRut(cita.getRut());
                    dto.setFechaAgenda(cita.getFechaAgenda());
                    dto.setIdBloque(cita.getIdBloqueHorario());
                    dto.setHoraInicioBloque(cita.getHoraInicioBloqueHoraio());
                    dto.setHoraFinBloque(cita.getHoraFinBloqueHoraio());
                    dto.setNombreTramite(cita.nombreTramite());

                    PersonaResponse persona = apiPersonaService.getPersonaInfo(cita.getRut());

                    dto.setVrut(persona.getVrut());

                    String nombre = persona.getNombres() + " ";
                    String paterno = persona.getPaterno() + " ";
                    String materno = persona.getMaterno();

                    dto.setNombre(nombre.concat(paterno).concat(materno));

                    return dto;

                })
                .toList();
    }

    @Transactional
    @Override
    public Cita updateCita(Long id, Cita citaActualizada) {
        Optional<Cita> citaOptional = citaRepository.findById(id);
        if (citaOptional.isPresent()) {
            Cita citaExistente = citaOptional.get();
            citaExistente.setRut(citaActualizada.getRut());
            citaExistente.setAgenda(citaActualizada.getAgenda());
            citaExistente.setFechaHora(citaActualizada.getFechaHora());
            return citaRepository.save(citaExistente);
        }
        return null;
    }

    @Transactional
    @Override
    public boolean deleteCitaById(Long id) {
        Optional<Cita> citaOptional = citaRepository.findById(id);
        if (citaOptional.isPresent()) {
            citaRepository.delete(citaOptional.get());
            return true;
        }
        return false;
    }

   

    @Override
    public List<SolicitudCitaResponse> getCitaBetweenDates(LocalDate fechaInicio,
            LocalDate fechaFin) {

        LocalDateTime fechaHoraInicio = fechaInicio.atStartOfDay();
        LocalDateTime fechaHoraFin = fechaFin.atTime(LocalTime.MAX);

        List<Cita> citas = citaRepository.findByFechaHoraBetween(fechaHoraInicio, fechaHoraFin);

        return citas.stream()
                .map(cita -> {

                    SolicitudCitaResponse dto = new SolicitudCitaResponse();

                    dto.setRut(cita.getRut());
                    dto.setFechaSolicitud(cita.getFechaHora().toLocalDate());
                    dto.setFechaAgenda(cita.getFechaAgenda());
                    dto.setIdBloque(cita.getIdBloqueHorario());
                    dto.setHoraInicioBloque(cita.getHoraInicioBloqueHoraio());
                    dto.setHoraFinBloque(cita.getHoraFinBloqueHoraio());
                    dto.setNombreTramite(cita.nombreTramite());

                    PersonaResponse persona = apiPersonaService.getPersonaInfo(cita.getRut());

                    dto.setVrut(persona.getVrut());

                    String nombre = persona.getNombres() + " ";
                    String paterno = persona.getPaterno() + " ";
                    String materno = persona.getMaterno();

                    dto.setNombre(nombre.concat(paterno).concat(materno));

                    return dto;

                })
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public CitaDelDiaResponseDto findCitaDelDiaPorRut(Integer rut) {
        LocalDate hoy = LocalDate.now();
        Cita cita = citaRepository.findByRutAndAgenda_Fecha(rut, hoy)
                .orElseThrow(() -> new NotFounException("No se encontró una cita para el RUT " + rut + " en la fecha de hoy."));

        PersonaResponse persona = apiPersonaService.getPersonaInfo(cita.getRut());

        return new CitaDelDiaResponseDto(
            cita.getIdCita(),
            cita.getFechaAgenda(),
            cita.getHoraInicioBloqueHoraio(),
            persona,
            cita.getTramite().getIdTramite(),
            cita.getTramite().getNombre()
        );
    }

}