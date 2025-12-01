package com.agendalc.agendalc.services.interfaces;

import com.agendalc.agendalc.entities.FilaAtencion;

public interface TramiteWorkflowService {
    void avanzarEtapa(FilaAtencion filaFinalizada);
}
