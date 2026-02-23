package com.agendalc.agendalc.dto;

import java.util.List;

public class ResumenDarioResponse {

    private Integer citados;
    private Integer asisetentes;
    private List<CitaDto> citas;

    public ResumenDarioResponse(Integer citados, Integer asisetentes, List<CitaDto> citas) {
        this.citados = citados;
        this.asisetentes = asisetentes;
        this.citas = citas;
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

    public List<CitaDto> getCitas() {
        return citas;
    }

    public void setCitas(List<CitaDto> citas) {
        this.citas = citas;
    }


    
}
