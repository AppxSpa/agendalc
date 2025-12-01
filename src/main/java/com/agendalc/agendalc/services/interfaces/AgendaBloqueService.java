package com.agendalc.agendalc.services.interfaces;

import java.util.List;

import com.agendalc.agendalc.entities.Agenda;
import com.agendalc.agendalc.entities.BloqueHorario;

public interface AgendaBloqueService {

    Agenda addOrUpdateBloquesHorario(Long idAgenda, List<BloqueHorario> bloquesHorarios);

    Agenda deleteBloqueDeAgenda(Long idAgenda, Long idBloqueHorario);

    Agenda updateBloquesHorariosDeAgenda(Long idAgenda, List<BloqueHorario> bloquesHorarioActualizados);

}
