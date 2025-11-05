package com.agendalc.agendalc.dto;

public class SaludSituacionLaboralDto {
    public enum SituacionLaboral {
        ACTIVO, DESEMPLEADO, JUBILADO, ESTUDIANTE, HOGAR, PENSIONADO
    }

    private SituacionLaboral situacionLaboral;

    public SituacionLaboral getSituacionLaboral() {
        return situacionLaboral;
    }

    public void setSituacionLaboral(SituacionLaboral situacionLaboral) {
        this.situacionLaboral = situacionLaboral;
    }
}
