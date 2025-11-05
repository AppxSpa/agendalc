package com.agendalc.agendalc.dto;

public class SaludOtorrinoDto {
    private boolean usarAudifonos;
    private boolean operacionOido;
    private boolean mareos;

    public boolean isUsarAudifonos() {
        return usarAudifonos;
    }

    public void setUsarAudifonos(boolean usarAudifonos) {
        this.usarAudifonos = usarAudifonos;
    }

    public boolean isOperacionOido() {
        return operacionOido;
    }

    public void setOperacionOido(boolean operacionOido) {
        this.operacionOido = operacionOido;
    }

    public boolean isMareos() {
        return mareos;
    }

    public void setMareos(boolean mareos) {
        this.mareos = mareos;
    }
}
