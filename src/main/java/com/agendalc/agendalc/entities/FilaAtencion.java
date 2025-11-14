package com.agendalc.agendalc.entities;

import java.time.LocalDateTime;

import com.agendalc.agendalc.entities.enums.EstadoFila;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "fila_atencion")
public class FilaAtencion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Cita cita;

    @ManyToOne
    private EtapaTramite etapaTramite;

    @Enumerated(EnumType.STRING)
    private EstadoFila estado;

    private String usuarioAsignado;

    private LocalDateTime fechaLlegada;

    private LocalDateTime fechaInicioAtencion;

    private LocalDateTime fechaFinAtencion;

    public FilaAtencion() {
    }

    private FilaAtencion(Builder builder) {
        this.id = builder.id;
        this.cita = builder.cita;
        this.etapaTramite = builder.etapaTramite;
        this.estado = builder.estado;
        this.usuarioAsignado = builder.usuarioAsignado;
        this.fechaLlegada = builder.fechaLlegada;
        this.fechaInicioAtencion = builder.fechaInicioAtencion;
        this.fechaFinAtencion = builder.fechaFinAtencion;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cita getCita() {
        return cita;
    }

    public void setCita(Cita cita) {
        this.cita = cita;
    }

    public EtapaTramite getEtapaTramite() {
        return etapaTramite;
    }

    public void setEtapaTramite(EtapaTramite etapaTramite) {
        this.etapaTramite = etapaTramite;
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
        private Long id;
        private Cita cita;
        private EtapaTramite etapaTramite;
        private EstadoFila estado;
        private String usuarioAsignado;
        private LocalDateTime fechaLlegada;
        private LocalDateTime fechaInicioAtencion;
        private LocalDateTime fechaFinAtencion;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder cita(Cita cita) {
            this.cita = cita;
            return this;
        }

        public Builder etapaTramite(EtapaTramite etapaTramite) {
            this.etapaTramite = etapaTramite;
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

        public FilaAtencion build() {
            return new FilaAtencion(this);
        }
    }
}
