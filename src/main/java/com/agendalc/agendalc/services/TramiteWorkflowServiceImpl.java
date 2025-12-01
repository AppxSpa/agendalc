package com.agendalc.agendalc.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agendalc.agendalc.entities.EtapaTramite;
import com.agendalc.agendalc.entities.FilaAtencion;
import com.agendalc.agendalc.entities.enums.EstadoFila;
import com.agendalc.agendalc.repositories.FilaAtencionRepository;
import com.agendalc.agendalc.services.interfaces.TramiteWorkflowService;

@Service
public class TramiteWorkflowServiceImpl implements TramiteWorkflowService {

    private final FilaAtencionRepository filaAtencionRepository;

    public TramiteWorkflowServiceImpl(FilaAtencionRepository filaAtencionRepository) {
        this.filaAtencionRepository = filaAtencionRepository;
    }

    @Override
    @Transactional
    public void avanzarEtapa(FilaAtencion filaFinalizada) {
        moverASiguienteEtapaSiCorresponde(filaFinalizada);
    }

    private void moverASiguienteEtapaSiCorresponde(FilaAtencion filaFinalizada) {
        com.agendalc.agendalc.entities.Tramite tramiteDelFlujo = filaFinalizada.getTramite();
        if (tramiteDelFlujo == null) {
            return; // No hay trámite asociado, no hay siguiente etapa.
        }

        List<com.agendalc.agendalc.entities.TramiteEtapa> etapasDelTramite = tramiteDelFlujo.getTramiteEtapas();
        EtapaTramite etapaActual = filaFinalizada.getEtapaTramite();

        for (int i = 0; i < etapasDelTramite.size(); i++) {
            if (etapasDelTramite.get(i).getEtapaTramite().equals(etapaActual)) {
                // Si no es la última etapa, pasar a la siguiente
                if (i < etapasDelTramite.size() - 1) {
                    EtapaTramite siguienteEtapa = etapasDelTramite.get(i + 1).getEtapaTramite();
                    crearNuevaFilaSiNoExiste(filaFinalizada, tramiteDelFlujo, siguienteEtapa);
                }
                break; // Salir del bucle una vez encontrada y procesada la etapa
            }
        }
    }

    private void crearNuevaFilaSiNoExiste(FilaAtencion filaAnterior, com.agendalc.agendalc.entities.Tramite tramite,
            EtapaTramite nuevaEtapa) {
        // Evitar duplicados: solo crear la nueva fila si no existe una ya para esa
        // cita y etapa
        if (filaAtencionRepository.findByCitaAndEtapaTramite(filaAnterior.getCita(), nuevaEtapa).isEmpty()) {
            FilaAtencion nuevaFila = FilaAtencion.builder()
                    .cita(filaAnterior.getCita())
                    .tramite(tramite)
                    .etapaTramite(nuevaEtapa)
                    .estado(EstadoFila.EN_ESPERA)
                    .fechaLlegada(LocalDateTime.now())
                    .build();
            filaAtencionRepository.save(nuevaFila);
        }
    }
}
