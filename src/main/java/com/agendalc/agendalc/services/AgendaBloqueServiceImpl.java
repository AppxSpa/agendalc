package com.agendalc.agendalc.services;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agendalc.agendalc.entities.Agenda;
import com.agendalc.agendalc.entities.BloqueHorario;
import com.agendalc.agendalc.repositories.AgendaRepository;
import com.agendalc.agendalc.services.interfaces.AgendaBloqueService;
import com.agendalc.agendalc.services.interfaces.BloqueHorarioService;

@Service
public class AgendaBloqueServiceImpl implements AgendaBloqueService {

    private static final String MSG_AGENDA = "Agenda no encontrada con ID: ";
    private static final String MSG_BLOQUE = "Bloque horario no encontrado ";

    private final AgendaRepository agendaRepository;
    private final BloqueHorarioService bloqueHorarioService;

    public AgendaBloqueServiceImpl(AgendaRepository agendaRepository, BloqueHorarioService bloqueHorarioService) {
        this.agendaRepository = agendaRepository;
        this.bloqueHorarioService = bloqueHorarioService;
    }

    @Transactional
    @Override
    public Agenda addOrUpdateBloquesHorario(Long idAgenda, List<BloqueHorario> bloquesHorarios) {
        Agenda agenda = findAgendaById(idAgenda);

        if (bloquesHorarios == null || bloquesHorarios.isEmpty()) {
            throw new IllegalArgumentException("La lista de bloques horarios está vacía o es nula");
        }

        Set<BloqueHorario> bloquesActuales = agenda.getBloquesHorarios();

        Set<BloqueHorario> bloquesActualizados = bloquesHorarios.stream()
                .map(this::saveOrUpdateBloque)
                .collect(Collectors.toSet());

        bloquesActuales.addAll(bloquesActualizados);

        return agendaRepository.save(agenda);
    }

    @Transactional
    @Override
    public Agenda deleteBloqueDeAgenda(Long idAgenda, Long idBloqueHorario) {
        Agenda agenda = findAgendaById(idAgenda);

        BloqueHorario bloqueHorario = bloqueHorarioService.findById(idBloqueHorario);

        boolean removed = agenda.getBloquesHorarios().removeIf(bh -> bh.getIdBloque().equals(idBloqueHorario));

        if (!removed) {
            throw new IllegalArgumentException("El bloque horario no está asociado con esta agenda");
        }

        bloqueHorarioService.delete(bloqueHorario);

        return agendaRepository.save(agenda);
    }

    @Transactional
    @Override
    public Agenda updateBloquesHorariosDeAgenda(Long idAgenda, List<BloqueHorario> bloquesHorarioActualizados) {
        Agenda agenda = findAgendaById(idAgenda);

        if (bloquesHorarioActualizados == null || bloquesHorarioActualizados.isEmpty()) {
            throw new IllegalArgumentException("La lista de bloques horarios está vacía");
        }

        Map<Long, BloqueHorario> bloquesMap = bloqueHorarioService.findAllById(
                bloquesHorarioActualizados.stream()
                        .map(BloqueHorario::getIdBloque)
                        .toList());

        for (BloqueHorario nuevoBloqueHorario : bloquesHorarioActualizados) {
            BloqueHorario bloqueHorario = bloquesMap.get(nuevoBloqueHorario.getIdBloque());

            if (bloqueHorario == null) {
                throw new IllegalArgumentException(MSG_BLOQUE + nuevoBloqueHorario.getIdBloque());
            }

            if (!agenda.getBloquesHorarios().contains(bloqueHorario)) {
                throw new IllegalArgumentException(
                        MSG_BLOQUE + nuevoBloqueHorario.getIdBloque() + " no está asociado con esta agenda");
            }

            // Actualizar valores
            bloqueHorario.setHoraInicio(nuevoBloqueHorario.getHoraInicio());
            bloqueHorario.setHoraFin(nuevoBloqueHorario.getHoraFin());
            bloqueHorario.setCuposDisponibles(nuevoBloqueHorario.getCuposDisponibles());
        }

        // Guardar todos los bloques en una sola operación
        bloqueHorarioService.saveAll(bloquesMap.values());

        return agendaRepository.save(agenda);
    }

    private Agenda findAgendaById(Long id) {
        return agendaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(MSG_AGENDA + id));
    }

    private BloqueHorario saveOrUpdateBloque(BloqueHorario bloqueRequest) {
        if (bloqueRequest.getIdBloque() == null) {
            return bloqueHorarioService.save(new BloqueHorario(
                    bloqueRequest.getHoraInicio(),
                    bloqueRequest.getHoraFin(),
                    bloqueRequest.getCuposDisponibles()));
        } else {
            BloqueHorario bloqueExistente = bloqueHorarioService.findById(bloqueRequest.getIdBloque());

            bloqueExistente.setHoraInicio(bloqueRequest.getHoraInicio());
            bloqueExistente.setHoraFin(bloqueRequest.getHoraFin());
            bloqueExistente.setCuposDisponibles(bloqueRequest.getCuposDisponibles());

            return bloqueHorarioService.save(bloqueExistente);
        }
    }
}
