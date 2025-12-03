package com.agendalc.agendalc.dto;

public class IndicadoresDto {

    private Integer citadasAgendadas;
    private Double ocupacionActual;
    private Double ocupacionDia;
    private String tramiteMasSolicitado;
    private String claseMasSolicitada;

    public Integer getCitadasAgendadas() {
        return citadasAgendadas;
    }

    public void setCitadasAgendadas(Integer citadasAgendadas) {
        this.citadasAgendadas = citadasAgendadas;
    }

    public Double getOcupacionActual() {
        return ocupacionActual;
    }

    public void setOcupacionActual(Double ocupacionActual) {
        this.ocupacionActual = ocupacionActual;
    }

    public Double getOcupacionDia() {
        return ocupacionDia;
    }

    public void setOcupacionDia(Double ocupacionDia) {
        this.ocupacionDia = ocupacionDia;
    }

    public String getTramiteMasSolicitado() {
        return tramiteMasSolicitado;
    }

    public void setTramiteMasSolicitado(String tramiteMasSolicitado) {
        this.tramiteMasSolicitado = tramiteMasSolicitado;
    }

    public String getClaseMasSolicitada() {
        return claseMasSolicitada;
    }

    public void setClaseMasSolicitada(String claseMasSolicitada) {
        this.claseMasSolicitada = claseMasSolicitada;
    }

}
