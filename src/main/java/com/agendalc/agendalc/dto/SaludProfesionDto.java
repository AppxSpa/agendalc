package com.agendalc.agendalc.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public class SaludProfesionDto {
    private String nombre;

    public SaludProfesionDto() {
    }

    @JsonCreator
    public SaludProfesionDto(String nombre) {
        this.nombre = nombre;
    }

    @JsonValue
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
