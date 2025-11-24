package com.agendalc.agendalc.entities;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "tramite_etapa")
public class TramiteEtapa {

    @EmbeddedId
    private TramiteEtapaId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("tramiteId")
    private Tramite tramite;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("etapaTramiteId")
    private EtapaTramite etapaTramite;

    @Column(name = "orden")
    private int orden;

   

    // Getters and Setters
    public TramiteEtapaId getId() {
        return id;
    }

    public void setId(TramiteEtapaId id) {
        this.id = id;
    }

    public Tramite getTramite() {
        return tramite;
    }

    public void setTramite(Tramite tramite) {
        this.tramite = tramite;
    }

    public EtapaTramite getEtapaTramite() {
        return etapaTramite;
    }

    public void setEtapaTramite(EtapaTramite etapaTramite) {
        this.etapaTramite = etapaTramite;
    }

    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }
}
