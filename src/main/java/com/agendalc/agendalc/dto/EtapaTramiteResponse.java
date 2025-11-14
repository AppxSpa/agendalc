package com.agendalc.agendalc.dto;

public class EtapaTramiteResponse {
    private Long id;
    private String nombre;
    private Integer orden;

    public EtapaTramiteResponse() {
    }

    public EtapaTramiteResponse(Long id, String nombre, Integer orden) {
        this.id = id;
        this.nombre = nombre;
        this.orden = orden;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
