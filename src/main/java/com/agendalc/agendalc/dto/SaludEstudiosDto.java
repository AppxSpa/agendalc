package com.agendalc.agendalc.dto;

public class SaludEstudiosDto {
    public enum NivelEstudio {
        BASICA_INCOMPLETA, BASICA_COMPLETA, MEDIA, SUPERIOR
    }

    private NivelEstudio nivelEstudio;

    public NivelEstudio getNivelEstudio() {
        return nivelEstudio;
    }

    public void setNivelEstudio(NivelEstudio nivelEstudio) {
        this.nivelEstudio = nivelEstudio;
    }
}
