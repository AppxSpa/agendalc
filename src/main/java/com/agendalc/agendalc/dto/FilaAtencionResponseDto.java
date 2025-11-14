package com.agendalc.agendalc.dto;

import java.time.LocalDateTime;

import com.agendalc.agendalc.entities.enums.EstadoFila;

public class FilaAtencionResponseDto {
    
    private Long filaId;
    private Long citaId;
    private PersonaResponse persona; // Asumo que podemos obtener los datos de la persona
    private Long etapaId;
    private String nombreEtapa;
    private EstadoFila estado;
    private String usuarioAsignado;
    private LocalDateTime fechaLlegada;
    private LocalDateTime fechaInicioAtencion;
    private LocalDateTime fechaFinAtencion;

    public FilaAtencionResponseDto() {
    }

    public FilaAtencionResponseDto(Long filaId, Long citaId, PersonaResponse persona, Long etapaId, String nombreEtapa, EstadoFila estado, String usuarioAsignado, LocalDateTime fechaLlegada, LocalDateTime fechaInicioAtencion, LocalDateTime fechaFinAtencion) {
        this.filaId = filaId;
        this.citaId = citaId;
        this.persona = persona;
        this.etapaId = etapaId;
        this.nombreEtapa = nombreEtapa;
        this.estado = estado;
        this.usuarioAsignado = usuarioAsignado;
        this.fechaLlegada = fechaLlegada;
        this.fechaInicioAtencion = fechaInicioAtencion;
        this.fechaFinAtencion = fechaFinAtencion;
    }

    public Long getFilaId() {
        return filaId;
    }

    public void setFilaId(Long filaId) {
        this.filaId = filaId;
    }

    public Long getCitaId() {
        return citaId;
    }

    public void setCitaId(Long citaId) {
        this.citaId = citaId;
    }

    public PersonaResponse getPersona() {
        return persona;
    }

    public void setPersona(PersonaResponse persona) {
        this.persona = persona;
    }

    public Long getEtapaId() {
        return etapaId;
    }

    public void setEtapaId(Long etapaId) {
        this.etapaId = etapaId;
    }

    public String getNombreEtapa() {
        return nombreEtapa;
    }

    public void setNombreEtapa(String nombreEtapa) {
        this.nombreEtapa = nombreEtapa;
    }

    public EstadoFila getEstado() {
        return estado;
    }

    public void setEstado(EstadoFila estado) {
        this.estado = estado;
    }

    public String getUsuarioAsignado() {
        return usuarioAsignado;
    }

    public void setUsuarioAsignado(String usuarioAsignado) {
        this.usuarioAsignado = usuarioAsignado;
    }

    public LocalDateTime getFechaLlegada() {
        return fechaLlegada;
    }

    public void setFechaLlegada(LocalDateTime fechaLlegada) {
        this.fechaLlegada = fechaLlegada;
    }

    public LocalDateTime getFechaInicioAtencion() {
        return fechaInicioAtencion;
    }

    public void setFechaInicioAtencion(LocalDateTime fechaInicioAtencion) {
        this.fechaInicioAtencion = fechaInicioAtencion;
    }

    public LocalDateTime getFechaFinAtencion() {
        return fechaFinAtencion;
    }

    public void setFechaFinAtencion(LocalDateTime fechaFinAtencion) {
        this.fechaFinAtencion = fechaFinAtencion;
    }
}
