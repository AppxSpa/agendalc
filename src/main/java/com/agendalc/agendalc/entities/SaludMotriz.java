package com.agendalc.agendalc.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
@Table(name = "salud_motriz")
public class SaludMotriz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "formulario_id", referencedColumnName = "id")
    private SaludFormulario formulario;

    private boolean perdidaFuerza;
    private boolean perdidaExtremidades;

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

    public boolean isPerdidaFuerza() {
        return perdidaFuerza;
    }

    public void setPerdidaFuerza(boolean perdidaFuerza) {
        this.perdidaFuerza = perdidaFuerza;
    }

    public boolean isPerdidaExtremidades() {
        return perdidaExtremidades;
    }

    public void setPerdidaExtremidades(boolean perdidaExtremidades) {
        this.perdidaExtremidades = perdidaExtremidades;
    }

}
