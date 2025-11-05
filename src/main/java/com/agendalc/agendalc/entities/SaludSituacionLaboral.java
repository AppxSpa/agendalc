package com.agendalc.agendalc.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
@Table(name = "salud_situacion_laboral")
public class SaludSituacionLaboral {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private SituacionLaboral situacionLaboral;

    @OneToOne
    @JoinColumn(name = "id_formulario")

    private SaludFormulario formulario;

    public enum SituacionLaboral {
        ACTIVO,
        DESEMPLEADO,
        JUBILADO,
        ESTUDIANTE,
        HOGAR,
        PENSIONADO
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SituacionLaboral getSituacionLaboral() {
        return situacionLaboral;
    }

    public void setSituacionLaboral(SituacionLaboral situacionLaboral) {
        this.situacionLaboral = situacionLaboral;
    }

    public SaludFormulario getFormulario() {
        return formulario;
    }

    public void setFormulario(SaludFormulario formulario) {
        this.formulario = formulario;
    }

}
