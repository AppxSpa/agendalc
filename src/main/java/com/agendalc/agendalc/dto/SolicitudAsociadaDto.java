package com.agendalc.agendalc.dto;

import java.time.LocalDate;

public class SolicitudAsociadaDto {
    private Long idSolicitud;
    private String estado;
    private LocalDate fechaSolicitud;

    // Constructor, Getters y Setters
    public SolicitudAsociadaDto(Long idSolicitud, String estado, LocalDate fechaSolicitud) {
        this.idSolicitud = idSolicitud;
        this.estado = estado;
        this.fechaSolicitud = fechaSolicitud;
    }

    public Long getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(Long idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDate getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(LocalDate fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }
}
