package com.agendalc.agendalc.dto;

import java.util.List;

public class IniciarProcesosRequest {

    private List<Long> tramiteIds;

    public List<Long> getTramiteIds() {
        return tramiteIds;
    }

    public void setTramiteIds(List<Long> tramiteIds) {
        this.tramiteIds = tramiteIds;
    }
}
