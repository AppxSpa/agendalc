package com.agendalc.agendalc.dto;

public class SaludJornadaDto {
    public enum TipoJornada {
        DIURNA, TURNO
    }

    public enum SubtipoJornada {
        FIJO, ROTATIVO
    }

    private TipoJornada tipoJornada;
    private SubtipoJornada subtipoJornada;

    public TipoJornada getTipoJornada() {
        return tipoJornada;
    }

    public void setTipoJornada(TipoJornada tipoJornada) {
        this.tipoJornada = tipoJornada;
    }

    public SubtipoJornada getSubtipoJornada() {
        return subtipoJornada;
    }

    public void setSubtipoJornada(SubtipoJornada subtipoJornada) {
        this.subtipoJornada = subtipoJornada;
    }
}
