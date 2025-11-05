package com.agendalc.agendalc.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
@Table(name = "salud_cardio")
public class SaludCardio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "formulario_id", referencedColumnName = "id")
    private SaludFormulario formulario;

    private boolean marcapasos;
    private boolean bypass;
    private boolean insuficienciaCardiaca;
    private boolean anginas;
    private boolean palpitaciones;
    private boolean valvula;
    private boolean hipertension;
    private boolean infarto;
    private boolean arritmias;
    private boolean dolorPecho;

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

    public boolean isMarcapasos() {
        return marcapasos;
    }

    public void setMarcapasos(boolean marcapasos) {
        this.marcapasos = marcapasos;
    }

    public boolean isBypass() {
        return bypass;
    }

    public void setBypass(boolean bypass) {
        this.bypass = bypass;
    }

    public boolean isInsuficienciaCardiaca() {
        return insuficienciaCardiaca;
    }

    public void setInsuficienciaCardiaca(boolean insuficienciaCardiaca) {
        this.insuficienciaCardiaca = insuficienciaCardiaca;
    }

    public boolean isAnginas() {
        return anginas;
    }

    public void setAnginas(boolean anginas) {
        this.anginas = anginas;
    }

    public boolean isPalpitaciones() {
        return palpitaciones;
    }

    public void setPalpitaciones(boolean palpitaciones) {
        this.palpitaciones = palpitaciones;
    }

    public boolean isValvula() {
        return valvula;
    }

    public void setValvula(boolean valvula) {
        this.valvula = valvula;
    }

    public boolean isHipertension() {
        return hipertension;
    }

    public void setHipertension(boolean hipertension) {
        this.hipertension = hipertension;
    }

    public boolean isInfarto() {
        return infarto;
    }

    public void setInfarto(boolean infarto) {
        this.infarto = infarto;
    }

    public boolean isArritmias() {
        return arritmias;
    }

    public void setArritmias(boolean arritmias) {
        this.arritmias = arritmias;
    }

    public boolean isDolorPecho() {
        return dolorPecho;
    }

    public void setDolorPecho(boolean dolorPecho) {
        this.dolorPecho = dolorPecho;
    }

}
