package com.agendalc.agendalc.dto;

import java.time.LocalDateTime;

import com.agendalc.agendalc.entities.enums.EstadoFila;

public class FilaAtencionResponseDto {

    private Long filaId;
    private Long citaId;
    private PersonaResponse persona;
    private Long etapaId;
    private String nombreEtapa;
    private EstadoFila estado;
    private String usuarioAsignado;
    private LocalDateTime fechaLlegada;
    private LocalDateTime fechaInicioAtencion;
    private LocalDateTime fechaFinAtencion;

    public FilaAtencionResponseDto() {
    }

    private FilaAtencionResponseDto(Builder builder) {
        this.filaId = builder.filaId;
        this.citaId = builder.citaId;
        this.persona = builder.persona;
        this.etapaId = builder.etapaId;
        this.nombreEtapa = builder.nombreEtapa;
        this.estado = builder.estado;
        this.usuarioAsignado = builder.usuarioAsignado;
        this.fechaLlegada = builder.fechaLlegada;
        this.fechaInicioAtencion = builder.fechaInicioAtencion;
        this.fechaFinAtencion = builder.fechaFinAtencion;
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

    public static class Builder {
        private Long filaId;
        private Long citaId;
        private PersonaResponse persona;
        private Long etapaId;
        private String nombreEtapa;
        private EstadoFila estado;
        private String usuarioAsignado;
        private LocalDateTime fechaLlegada;
        private LocalDateTime fechaInicioAtencion;
        private LocalDateTime fechaFinAtencion;

        public Builder filaId(Long filaId) {
            this.filaId = filaId;
            return this;
        }

        public Builder citaId(Long citaId) {
            this.citaId = citaId;
            return this;
        }

        public Builder persona(PersonaResponse persona) {
            this.persona = persona;
            return this;
        }

        public Builder etapaId(Long etapaId) {
            this.etapaId = etapaId;
            return this;
        }

        public Builder nombreEtapa(String nombreEtapa) {
            this.nombreEtapa = nombreEtapa;
            return this;
        }

        public Builder estado(EstadoFila estado) {
            this.estado = estado;
            return this;
        }

        public Builder usuarioAsignado(String usuarioAsignado) {
            this.usuarioAsignado = usuarioAsignado;
            return this;
        }

        public Builder fechaLlegada(LocalDateTime fechaLlegada) {
            this.fechaLlegada = fechaLlegada;
            return this;
        }

        public Builder fechaInicioAtencion(LocalDateTime fechaInicioAtencion) {
            this.fechaInicioAtencion = fechaInicioAtencion;
            return this;
        }

        public Builder fechaFinAtencion(LocalDateTime fechaFinAtencion) {
            this.fechaFinAtencion = fechaFinAtencion;
            return this;
        }

        public FilaAtencionResponseDto build() {
            return new FilaAtencionResponseDto(this);
        }
    }
}
