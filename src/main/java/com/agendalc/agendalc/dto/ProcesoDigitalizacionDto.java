
package com.agendalc.agendalc.dto;

import java.time.LocalDateTime;

public class ProcesoDigitalizacionDto {

    private Long id;
    private String rutPersona;
    private Long tramiteId;
    private LocalDateTime fechaProceso;
    private String usuarioResponsable;
    private String observaciones;

    public ProcesoDigitalizacionDto() {
    }

    public ProcesoDigitalizacionDto(Long id, String rutPersona, Long tramiteId, LocalDateTime fechaProceso, String usuarioResponsable, String observaciones) {
        this.id = id;
        this.rutPersona = rutPersona;
        this.tramiteId = tramiteId;
        this.fechaProceso = fechaProceso;
        this.usuarioResponsable = usuarioResponsable;
        this.observaciones = observaciones;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRutPersona() {
        return rutPersona;
    }

    public void setRutPersona(String rutPersona) {
        this.rutPersona = rutPersona;
    }

    public Long getTramiteId() {
        return tramiteId;
    }

    public void setTramiteId(Long tramiteId) {
        this.tramiteId = tramiteId;
    }

    public LocalDateTime getFechaProceso() {
        return fechaProceso;
    }

    public void setFechaProceso(LocalDateTime fechaProceso) {
        this.fechaProceso = fechaProceso;
    }

    public String getUsuarioResponsable() {
        return usuarioResponsable;
    }

    public void setUsuarioResponsable(String usuarioResponsable) {
        this.usuarioResponsable = usuarioResponsable;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}
