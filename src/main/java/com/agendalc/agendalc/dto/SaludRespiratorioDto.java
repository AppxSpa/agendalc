package com.agendalc.agendalc.dto;

public class SaludRespiratorioDto {
    private boolean dificultadRespirar;
    private boolean problemasDormir;
    private boolean fatiga;
    private boolean ronquidos;

    public boolean isDificultadRespirar() {
        return dificultadRespirar;
    }

    public void setDificultadRespirar(boolean dificultadRespirar) {
        this.dificultadRespirar = dificultadRespirar;
    }

    public boolean isProblemasDormir() {
        return problemasDormir;
    }

    public void setProblemasDormir(boolean problemasDormir) {
        this.problemasDormir = problemasDormir;
    }

    public boolean isFatiga() {
        return fatiga;
    }

    public void setFatiga(boolean fatiga) {
        this.fatiga = fatiga;
    }

    public boolean isRonquidos() {
        return ronquidos;
    }

    public void setRonquidos(boolean ronquidos) {
        this.ronquidos = ronquidos;
    }
}
