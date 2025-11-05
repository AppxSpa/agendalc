package com.agendalc.agendalc.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.OneToOne;
import jakarta.persistence.JoinColumn;

@Entity
@Table(name = "salud_jornada")
public class SaludJornada {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "id_formulario")
    private SaludFormulario formulario;

    private TipoJornada tipoJornada;
    private SubtipoJornada subtipoJornada;

    public enum TipoJornada {
        DIURNA,
        TURNO
    }

    public enum SubtipoJornada {
        FIJO,
        ROTATIVO
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SaludFormulario getFormulario() {
        return formulario;
    }

    public void setFormulario(SaludFormulario formulario) {
        this.formulario = formulario;
    }

    public TipoJornada getTipoJornada() {
        return tipoJornada;
    }

    public void setTipoJornada(TipoJornada tipoJornada) {
        this.tipoJornada = tipoJornada;
    }

    public SubtipoJornada getSubtipoJornada() {
        return subtipoJornada;
    }

    public void setSubtipoJornada(SubtipoJornada subtipoJornada) {
        this.subtipoJornada = subtipoJornada;
    }

}
