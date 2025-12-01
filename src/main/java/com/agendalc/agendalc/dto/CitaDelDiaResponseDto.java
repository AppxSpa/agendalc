package com.agendalc.agendalc.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class CitaDelDiaResponseDto {

    private Long citaId;
    private LocalDate fecha;
    private LocalTime horaInicio;
    private PersonaResponse persona;
    private Long tramiteId;
    private String tramiteNombre;

    public CitaDelDiaResponseDto() {
    }

    public CitaDelDiaResponseDto(Long citaId, LocalDate fecha, LocalTime horaInicio, PersonaResponse persona,
            Long tramiteId, String tramiteNombre) {
        this.citaId = citaId;
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.persona = persona;
        this.tramiteId = tramiteId;
        this.tramiteNombre = tramiteNombre;
    }

    // Getters y Setters
    public Long getCitaId() {
        return citaId;
    }

    public void setCitaId(Long citaId) {
        this.citaId = citaId;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public PersonaResponse getPersona() {
        return persona;
    }

    public void setPersona(PersonaResponse persona) {
        this.persona = persona;
    }

    public Long getTramiteId() {
        return tramiteId;
    }

    public void setTramiteId(Long tramiteId) {
        this.tramiteId = tramiteId;
    }

    public String getTramiteNombre() {
        return tramiteNombre;
    }

    public void setTramiteNombre(String tramiteNombre) {
        this.tramiteNombre = tramiteNombre;
    }
}
