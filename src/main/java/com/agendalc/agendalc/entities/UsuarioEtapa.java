package com.agendalc.agendalc.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuario_etapa")
public class UsuarioEtapa {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String usuarioId;

    private String nombreUsuario;

    @ManyToOne
    private EtapaTramite etapaTramite;

    public UsuarioEtapa() {
    }

    public UsuarioEtapa(Long id, String usuarioId, String nombreUsuario, EtapaTramite etapaTramite) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.nombreUsuario = nombreUsuario;
        this.etapaTramite = etapaTramite;
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

    public EtapaTramite getEtapaTramite() {
        return etapaTramite;
    }

    public void setEtapaTramite(EtapaTramite etapaTramite) {
        this.etapaTramite = etapaTramite;
    }
}
