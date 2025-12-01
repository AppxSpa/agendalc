package com.agendalc.agendalc.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class UsuarioEtapaRequestDto {

    @NotEmpty(message = "El ID de usuario no puede estar vacío")
    private String usuarioId;

    @NotEmpty(message = "El nombre de usuario no puede estar vacío")
    private String nombreUsuario;

    @NotNull(message = "El ID de la etapa de trámite no puede ser nulo")
    private Long etapaTramiteId;

    public UsuarioEtapaRequestDto() {
    }

    public UsuarioEtapaRequestDto(String usuarioId, String nombreUsuario, Long etapaTramiteId) {
        this.usuarioId = usuarioId;
        this.nombreUsuario = nombreUsuario;
        this.etapaTramiteId = etapaTramiteId;
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
}
