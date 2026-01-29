package com.agendalc.agendalc.dto;

public class ResumenDarioResponse {

    private Integer citados;
    private Integer asisetentes;

    

    public ResumenDarioResponse(Integer citados, Integer asisetentes) {
        this.citados = citados;
        this.asisetentes = asisetentes;
    }

    public Integer getCitados() {
        return citados;
    }

    public void setCitados(Integer citados) {
        this.citados = citados;
    }

    public Integer getAsisetentes() {
        return asisetentes;
    }

    public void setAsisetentes(Integer asisetentes) {
        this.asisetentes = asisetentes;
    }

}
