package com.agendalc.agendalc.dto;

public class DeclaracionSaludResponse {

    private SaludFormularioDto declaracion;
    private SolicitudAsociadaDto solicitudAsociada;
    private CitaAsociadaDto citaAsociada;

    // Getters y Setters
    public SaludFormularioDto getDeclaracion() {
        return declaracion;
    }

    public void setDeclaracion(SaludFormularioDto declaracion) {
        this.declaracion = declaracion;
    }

    public SolicitudAsociadaDto getSolicitudAsociada() {
        return solicitudAsociada;
    }

    public void setSolicitudAsociada(SolicitudAsociadaDto solicitudAsociada) {
        this.solicitudAsociada = solicitudAsociada;
    }

    public CitaAsociadaDto getCitaAsociada() {
        return citaAsociada;
    }

    public void setCitaAsociada(CitaAsociadaDto citaAsociada) {
        this.citaAsociada = citaAsociada;
    }
}
