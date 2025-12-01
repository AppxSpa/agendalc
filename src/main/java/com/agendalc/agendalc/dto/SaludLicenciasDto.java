package com.agendalc.agendalc.dto;

public class SaludLicenciasDto {

    private String tipoLicencia;
    private boolean profesional;
    private boolean otras;
    private boolean leyAntigua;
    private boolean noProfesional;
    private boolean especial;

    public boolean isNoProfesional() {
        return noProfesional;
    }

    public void setNoProfesional(boolean noProfesional) {
        this.noProfesional = noProfesional;
    }

    public boolean isEspecial() {
        return especial;
    }

    public void setEspecial(boolean especial) {
        this.especial = especial;
    }

    public String getTipoLicencia() {
        return tipoLicencia;
    }

    public void setTipoLicencia(String tipoLicencia) {
        this.tipoLicencia = tipoLicencia;
    }

    public boolean isProfesional() {
        return profesional;
    }

    public void setProfesional(boolean profesional) {
        this.profesional = profesional;
    }

    public boolean isOtras() {
        return otras;
    }

    public void setOtras(boolean otras) {
        this.otras = otras;
    }

    public boolean isLeyAntigua() {
        return leyAntigua;
    }

    public void setLeyAntigua(boolean leyAntigua) {
        this.leyAntigua = leyAntigua;
    }

}
