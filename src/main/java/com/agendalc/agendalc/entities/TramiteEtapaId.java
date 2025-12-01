package com.agendalc.agendalc.entities;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class TramiteEtapaId implements Serializable {

    @Column(name = "tramite_id")
    private Long tramiteId;

    @Column(name = "etapa_tramite_id")
    private Long etapaTramiteId;

    public TramiteEtapaId() {
    }

    public TramiteEtapaId(Long tramiteId, Long etapaTramiteId) {
        this.tramiteId = tramiteId;
        this.etapaTramiteId = etapaTramiteId;
    }

    // Getters, Setters, equals, and hashCode
    public Long getTramiteId() {
        return tramiteId;
    }

    public void setTramiteId(Long tramiteId) {
        this.tramiteId = tramiteId;
    }

    public Long getEtapaTramiteId() {
        return etapaTramiteId;
    }

    public void setEtapaTramiteId(Long etapaTramiteId) {
        this.etapaTramiteId = etapaTramiteId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        TramiteEtapaId that = (TramiteEtapaId) o;
        return Objects.equals(tramiteId, that.tramiteId) &&
                Objects.equals(etapaTramiteId, that.etapaTramiteId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tramiteId, etapaTramiteId);
    }
}
