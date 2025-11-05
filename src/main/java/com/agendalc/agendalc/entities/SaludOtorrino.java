package com.agendalc.agendalc.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.OneToOne;
import jakarta.persistence.JoinColumn;

@Entity
@Table(name = "salud_otorrino")
public class SaludOtorrino {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "formulario_id", referencedColumnName = "id")
    private SaludFormulario formulario;

    private boolean usarAudifonos;
    private boolean operacionOido;
    private boolean mareos;

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

    public boolean isUsarAudifonos() {
        return usarAudifonos;
    }

    public void setUsarAudifonos(boolean usarAudifonos) {
        this.usarAudifonos = usarAudifonos;
    }

    public boolean isOperacionOido() {
        return operacionOido;
    }

    public void setOperacionOido(boolean operacionOido) {
        this.operacionOido = operacionOido;
    }

    public boolean isMareos() {
        return mareos;
    }

    public void setMareos(boolean mareos) {
        this.mareos = mareos;
    }

}
