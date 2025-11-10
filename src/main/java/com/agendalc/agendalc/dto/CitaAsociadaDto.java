package com.agendalc.agendalc.dto;

import java.time.LocalDateTime;

public class CitaAsociadaDto {
    private Long idCita;
    private LocalDateTime fechaHora;
    private Long idAgenda;

    // Constructor, Getters y Setters
    public CitaAsociadaDto(Long idCita, LocalDateTime fechaHora, Long idAgenda) {
        this.idCita = idCita;
        this.fechaHora = fechaHora;
        this.idAgenda = idAgenda;
    }

    public Long getIdCita() {
        return idCita;
    }

    public void setIdCita(Long idCita) {
        this.idCita = idCita;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public Long getIdAgenda() {
        return idAgenda;
    }

    public void setIdAgenda(Long idAgenda) {
        this.idAgenda = idAgenda;
    }
}
