package com.agendalc.agendalc.dto;

public class SaludMotrizDto {
    private boolean perdidaFuerza;
    private boolean perdidaExtremidades;

    public boolean isPerdidaFuerza() {
        return perdidaFuerza;
    }

    public void setPerdidaFuerza(boolean perdidaFuerza) {
        this.perdidaFuerza = perdidaFuerza;
    }

    public boolean isPerdidaExtremidades() {
        return perdidaExtremidades;
    }

    public void setPerdidaExtremidades(boolean perdidaExtremidades) {
        this.perdidaExtremidades = perdidaExtremidades;
    }
}
