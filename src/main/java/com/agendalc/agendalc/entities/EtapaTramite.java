package com.agendalc.agendalc.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "etapa_tramite")
public class EtapaTramite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "id_tramite")
    private Tramite tramite;

    private String nombre;
    private int orden;

    @OneToMany(mappedBy = "etapaTramite")
    @JsonIgnore
    private List<UsuarioEtapa> usuariosEtapa;

    public EtapaTramite() {
    }

    public EtapaTramite(Tramite tramite, String nombre, int orden) {
        this.tramite = tramite;
        this.nombre = nombre;
        this.orden = orden;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Tramite getTramite() {
        return tramite;
    }

    public void setTramite(Tramite tramite) {
        this.tramite = tramite;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }

    public List<UsuarioEtapa> getUsuariosEtapa() {
        return usuariosEtapa;
    }

    public void setUsuariosEtapa(List<UsuarioEtapa> usuariosEtapa) {
        this.usuariosEtapa = usuariosEtapa;
    }
}