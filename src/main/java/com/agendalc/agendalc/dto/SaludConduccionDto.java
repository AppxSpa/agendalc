package com.agendalc.agendalc.dto;

public class SaludConduccionDto {
    private boolean todoslosDias;
    private boolean algunosDiasSemana;
    private boolean algunosDiasMes;
    private boolean algunosDiasAnio;
    private boolean utilizaTrabajar;
    private boolean evalucionesMedicas;
    private boolean ciudad;
    private boolean carretera;
    private boolean ambos;
    private boolean accidentes;
    private String detalleAccidentes;

    public boolean isTodoslosDias() {
        return todoslosDias;
    }

    public void setTodoslosDias(boolean todoslosDias) {
        this.todoslosDias = todoslosDias;
    }

    public boolean isAlgunosDiasSemana() {
        return algunosDiasSemana;
    }

    public void setAlgunosDiasSemana(boolean algunosDiasSemana) {
        this.algunosDiasSemana = algunosDiasSemana;
    }

    public boolean isAlgunosDiasMes() {
        return algunosDiasMes;
    }

    public void setAlgunosDiasMes(boolean algunosDiasMes) {
        this.algunosDiasMes = algunosDiasMes;
    }

    public boolean isAlgunosDiasAnio() {
        return algunosDiasAnio;
    }

    public void setAlgunosDiasAnio(boolean algunosDiasAnio) {
        this.algunosDiasAnio = algunosDiasAnio;
    }

    public boolean isUtilizaTrabajar() {
        return utilizaTrabajar;
    }

    public void setUtilizaTrabajar(boolean utilizaTrabajar) {
        this.utilizaTrabajar = utilizaTrabajar;
    }

    public boolean isEvalucionesMedicas() {
        return evalucionesMedicas;
    }

    public void setEvalucionesMedicas(boolean evalucionesMedicas) {
        this.evalucionesMedicas = evalucionesMedicas;
    }

    public boolean isCiudad() {
        return ciudad;
    }

    public void setCiudad(boolean ciudad) {
        this.ciudad = ciudad;
    }

    public boolean isCarretera() {
        return carretera;
    }

    public void setCarretera(boolean carretera) {
        this.carretera = carretera;
    }

    public boolean isAmbos() {
        return ambos;
    }

    public void setAmbos(boolean ambos) {
        this.ambos = ambos;
    }

    public boolean isAccidentes() {
        return accidentes;
    }

    public void setAccidentes(boolean accidentes) {
        this.accidentes = accidentes;
    }

    public String getDetalleAccidentes() {
        return detalleAccidentes;
    }

    public void setDetalleAccidentes(String detalleAccidentes) {
        this.detalleAccidentes = detalleAccidentes;
    }
}
