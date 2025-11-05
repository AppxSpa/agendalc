package com.agendalc.agendalc.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "salud_oftalmologico")
public class SaludOftalmologico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "formulario_id", referencedColumnName = "id")
    private SaludFormulario formulario;

    private boolean ojoIzquierdo;
    private boolean ojoDerecho;
    private boolean glaucomaOjoIzquierdo;
    private boolean glaucomaOjoDerecho;
    private boolean cataratasOjoIzquierdo;
    private boolean cataratasOjoDerecho;
    private boolean retinaOjoIzquierdo;
    private boolean retinaOjoDerecho;
    private boolean usaLentes;
    private String tipoLente;


    private boolean operacionOjos;

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

    public boolean isOjoIzquierdo() {
        return ojoIzquierdo;
    }

    public void setOjoIzquierdo(boolean ojoIzquierdo) {
        this.ojoIzquierdo = ojoIzquierdo;
    }

    public boolean isOjoDerecho() {
        return ojoDerecho;
    }

    public void setOjoDerecho(boolean ojoDerecho) {
        this.ojoDerecho = ojoDerecho;
    }

    public boolean isGlaucomaOjoIzquierdo() {
        return glaucomaOjoIzquierdo;
    }

    public void setGlaucomaOjoIzquierdo(boolean glaucomaOjoIzquierdo) {
        this.glaucomaOjoIzquierdo = glaucomaOjoIzquierdo;
    }

    public boolean isGlaucomaOjoDerecho() {
        return glaucomaOjoDerecho;
    }

    public void setGlaucomaOjoDerecho(boolean glaucomaOjoDerecho) {
        this.glaucomaOjoDerecho = glaucomaOjoDerecho;
    }

    public boolean isCataratasOjoIzquierdo() {
        return cataratasOjoIzquierdo;
    }

    public void setCataratasOjoIzquierdo(boolean cataratasOjoIzquierdo) {
        this.cataratasOjoIzquierdo = cataratasOjoIzquierdo;
    }

    public boolean isCataratasOjoDerecho() {
        return cataratasOjoDerecho;
    }

    public void setCataratasOjoDerecho(boolean cataratasOjoDerecho) {
        this.cataratasOjoDerecho = cataratasOjoDerecho;
    }

    public boolean isRetinaOjoIzquierdo() {
        return retinaOjoIzquierdo;
    }

    public void setRetinaOjoIzquierdo(boolean retinaOjoIzquierdo) {
        this.retinaOjoIzquierdo = retinaOjoIzquierdo;
    }

    public boolean isRetinaOjoDerecho() {
        return retinaOjoDerecho;
    }

    public void setRetinaOjoDerecho(boolean retinaOjoDerecho) {
        this.retinaOjoDerecho = retinaOjoDerecho;
    }

    public boolean isUsaLentes() {
        return usaLentes;
    }

    public void setUsaLentes(boolean usaLentes) {
        this.usaLentes = usaLentes;
    }

    public String getTipoLente() {
        return tipoLente;
    }

    public void setTipoLente(String tipoLente) {
        this.tipoLente = tipoLente;
    }



    public boolean isOperacionOjos() {
        return operacionOjos;
    }

    public void setOperacionOjos(boolean operacionOjos) {
        this.operacionOjos = operacionOjos;
    }

}
