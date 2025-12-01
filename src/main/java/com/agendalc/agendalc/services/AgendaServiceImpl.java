package com.agendalc.agendalc.services;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agendalc.agendalc.dto.AgendaRequest;
import com.agendalc.agendalc.dto.AgendaResponse;
import com.agendalc.agendalc.entities.Agenda;
import com.agendalc.agendalc.entities.BloqueHorario;
import com.agendalc.agendalc.entities.Tramite;
import com.agendalc.agendalc.repositories.AgendaRepository;
import com.agendalc.agendalc.services.interfaces.AgendaBloqueService;
import com.agendalc.agendalc.services.interfaces.AgendaService;
import com.agendalc.agendalc.services.interfaces.BloqueHorarioService;
import com.agendalc.agendalc.services.interfaces.TramiteService;
import com.agendalc.agendalc.services.mappers.AgendaMapper;

@Service
public class AgendaServiceImpl implements AgendaService {

    private static final String MSG_AGENDA = "Agenda no encontrada con ID: ";

    private final AgendaRepository agendaRepository;
    private final BloqueHorarioService bloqueHorarioService;
    private final TramiteService tramiteService;
    private final AgendaMapper agendaMapper;
    private final AgendaBloqueService agendaBloqueService;

    public AgendaServiceImpl(AgendaRepository agendaRepository, BloqueHorarioService bloqueHorarioService,
            TramiteService tramiteService, AgendaMapper agendaMapper, AgendaBloqueService agendaBloqueService) {
        this.agendaRepository = agendaRepository;
        this.bloqueHorarioService = bloqueHorarioService;
        this.tramiteService = tramiteService;
        this.agendaMapper = agendaMapper;
        this.agendaBloqueService = agendaBloqueService;
    }

    @Transactional
    @Override
    public Agenda addOrUpdateBloquesHorario(Long idAgenda, List<BloqueHorario> bloquesHorarios) {
        return agendaBloqueService.addOrUpdateBloquesHorario(idAgenda, bloquesHorarios);
    }

    private BloqueHorario getOrCreateBloque(BloqueHorario bloqueRequest) {
        return (bloqueRequest.getIdBloque() == null)
                ? bloqueHorarioService.save(new BloqueHorario(
                        bloqueRequest.getHoraInicio(),
                        bloqueRequest.getHoraFin(),
                        bloqueRequest.getCuposDisponibles()))
                : bloqueHorarioService.findById(bloqueRequest.getIdBloque());
    }

    @Transactional
    @Override
    public Agenda createAgenda(AgendaRequest request) {
        Tramite tramite = tramiteService.getTramiteById(request.getIdTramite());

        if (request.getBloqueHorario() == null || request.getBloqueHorario().isEmpty()) {
            throw new IllegalArgumentException("La lista de bloques horarios está vacía o es nula");
        }

        Set<BloqueHorario> bloques = request.getBloqueHorario().stream()
                .map(this::getOrCreateBloque)
                .collect(Collectors.toSet());

        Agenda agenda = new Agenda();
        agenda.setTramite(tramite);
        agenda.setFecha(request.getFecha());
        agenda.setBloquesHorarios(bloques);

        return agendaRepository.save(agenda);
    }

    @Transactional
    @Override
    public boolean deleteAgendaById(Long id) {
        if (agendaRepository.existsById(id)) {
            agendaRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Transactional
    @Override
    public Agenda deleteBloqueDeAgenda(Long idAgenda, Long idBloqueHorario) {
        return agendaBloqueService.deleteBloqueDeAgenda(idAgenda, idBloqueHorario);
    }

    @Override
    public List<AgendaResponse> getAllAgendas() {
        List<Agenda> agendas = agendaRepository.findAll();

        if (agendas.isEmpty()) {
            throw new IllegalArgumentException("No se encontraron agendas");
        }

        return agendaMapper.toAgendaResponseList(agendas);

    }

    @Transactional
    @Override
    public Agenda updateAgenda(Long id, Agenda agendaActualizada) {
        if (agendaRepository.existsById(id)) {
            agendaActualizada.setIdAgenda(id);
            return agendaRepository.save(agendaActualizada);
        }
        return null;
    }

    @Transactional
    @Override
    public Agenda updateBloquesHorariosDeAgenda(Long idAgenda, List<BloqueHorario> bloquesHorarioActualizados) {
        return agendaBloqueService.updateBloquesHorariosDeAgenda(idAgenda, bloquesHorarioActualizados);
    }

    @Override
    public Agenda findById(Long id) {
        return agendaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(MSG_AGENDA + id));

    }

    @Override
    public Agenda save(Agenda agenda) {
        return agendaRepository.save(agenda);
    }

}
