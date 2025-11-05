package com.agendalc.agendalc.dto;

public class SaludNeurologicoDto {
    private boolean desmayo;
    private boolean esclerosisMultiple;
    private boolean dificultadHablar;
    private boolean psiquiatrico;
    private boolean derrameCerebral;
    private boolean epilepsia;
    private boolean parkinson;
    private boolean olvido;
    private boolean emocional;

    public boolean isDesmayo() {
        return desmayo;
    }

    public void setDesmayo(boolean desmayo) {
        this.desmayo = desmayo;
    }

    public boolean isEsclerosisMultiple() {
        return esclerosisMultiple;
    }

    public void setEsclerosisMultiple(boolean esclerosisMultiple) {
        this.esclerosisMultiple = esclerosisMultiple;
    }

    public boolean isDificultadHablar() {
        return dificultadHablar;
    }

    public void setDificultadHablar(boolean dificultadHablar) {
        this.dificultadHablar = dificultadHablar;
    }

    public boolean isPsiquiatrico() {
        return psiquiatrico;
    }

    public void setPsiquiatrico(boolean psiquiatrico) {
        this.psiquiatrico = psiquiatrico;
    }

    public boolean isDerrameCerebral() {
        return derrameCerebral;
    }

    public void setDerrameCerebral(boolean derrameCerebral) {
        this.derrameCerebral = derrameCerebral;
    }

    public boolean isEpilepsia() {
        return epilepsia;
    }

    public void setEpilepsia(boolean epilepsia) {
        this.epilepsia = epilepsia;
    }

    public boolean isParkinson() {
        return parkinson;
    }

    public void setParkinson(boolean parkinson) {
        this.parkinson = parkinson;
    }

    public boolean isOlvido() {
        return olvido;
    }

    public void setOlvido(boolean olvido) {
        this.olvido = olvido;
    }

    public boolean isEmocional() {
        return emocional;
    }

    public void setEmocional(boolean emocional) {
        this.emocional = emocional;
    }
}
