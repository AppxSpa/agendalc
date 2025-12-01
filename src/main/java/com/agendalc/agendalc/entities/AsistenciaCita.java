package com.agendalc.agendalc.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "asistencias_cita")
public class AsistenciaCita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cita_id", unique = true, nullable = false)
    private Cita cita;

    @Column(nullable = false)
    private LocalDateTime fechaHoraAsistencia;

    // Constructors
    public AsistenciaCita() {
    }

    public AsistenciaCita(Cita cita, LocalDateTime fechaHoraAsistencia) {
        this.cita = cita;
        this.fechaHoraAsistencia = fechaHoraAsistencia;
    }

    // Getters and Setters
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

    public LocalDateTime getFechaHoraAsistencia() {
        return fechaHoraAsistencia;
    }

    public void setFechaHoraAsistencia(LocalDateTime fechaHoraAsistencia) {
        this.fechaHoraAsistencia = fechaHoraAsistencia;
    }
}
