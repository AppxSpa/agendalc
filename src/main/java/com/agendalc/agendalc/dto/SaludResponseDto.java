package com.agendalc.agendalc.dto;

public class SaludResponseDto {
    private String rut;
    private String nombre;
    private String apellido;
    private int edad;
    private String diagnosticoPrincipal;
    private String situacionLaboral;
    private String jornadaLaboral;
    private String medicamentosActuales;
    private boolean tieneProblemasCardiacos;
    private boolean tieneProblemasRespiratorios;
    private String observaciones;

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getDiagnosticoPrincipal() {
        return diagnosticoPrincipal;
    }

    public void setDiagnosticoPrincipal(String diagnosticoPrincipal) {
        this.diagnosticoPrincipal = diagnosticoPrincipal;
    }

    public String getSituacionLaboral() {
        return situacionLaboral;
    }

    public void setSituacionLaboral(String situacionLaboral) {
        this.situacionLaboral = situacionLaboral;
    }

    public String getJornadaLaboral() {
        return jornadaLaboral;
    }

    public void setJornadaLaboral(String jornadaLaboral) {
        this.jornadaLaboral = jornadaLaboral;
    }

    public String getMedicamentosActuales() {
        return medicamentosActuales;
    }

    public void setMedicamentosActuales(String medicamentosActuales) {
        this.medicamentosActuales = medicamentosActuales;
    }

    public boolean isTieneProblemasCardiacos() {
        return tieneProblemasCardiacos;
    }

    public void setTieneProblemasCardiacos(boolean tieneProblemasCardiacos) {
        this.tieneProblemasCardiacos = tieneProblemasCardiacos;
    }

    public boolean isTieneProblemasRespiratorios() {
        return tieneProblemasRespiratorios;
    }

    public void setTieneProblemasRespiratorios(boolean tieneProblemasRespiratorios) {
        this.tieneProblemasRespiratorios = tieneProblemasRespiratorios;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}
