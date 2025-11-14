package com.agendalc.agendalc.dto;

public class EtapaTramiteRequest {
    private String nombre;
    private Integer orden;
    private Long tramiteId;

    public EtapaTramiteRequest() {
    }

    public EtapaTramiteRequest(String nombre, Integer orden, Long tramiteId) {
        this.nombre = nombre;
        this.orden = orden;
        this.tramiteId = tramiteId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public Long getTramiteId() {
        return tramiteId;
    }

    public void setTramiteId(Long tramiteId) {
        this.tramiteId = tramiteId;
    }
}
