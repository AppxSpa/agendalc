package com.agendalc.agendalc.dto;

public class UsuarioEtapaResponseDto {
    
    private Long id;
    private String usuarioId;
    private String nombreUsuario;
    private Long etapaTramiteId;
    private String nombreEtapa;

    public UsuarioEtapaResponseDto() {
    }

    public UsuarioEtapaResponseDto(Long id, String usuarioId, String nombreUsuario, Long etapaTramiteId, String nombreEtapa) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.nombreUsuario = nombreUsuario;
        this.etapaTramiteId = etapaTramiteId;
        this.nombreEtapa = nombreEtapa;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public Long getEtapaTramiteId() {
        return etapaTramiteId;
    }

    public void setEtapaTramiteId(Long etapaTramiteId) {
        this.etapaTramiteId = etapaTramiteId;
    }

    public String getNombreEtapa() {
        return nombreEtapa;
    }

    public void setNombreEtapa(String nombreEtapa) {
        this.nombreEtapa = nombreEtapa;
    }
}
