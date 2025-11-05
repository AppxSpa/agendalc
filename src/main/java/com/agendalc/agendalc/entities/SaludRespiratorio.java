package com.agendalc.agendalc.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
@Table(name = "salud_respiratorio")
public class SaludRespiratorio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "formulario_id", referencedColumnName = "id")
    private SaludFormulario formulario;

    private boolean dificultadRespirar;
    private boolean problemasDormir;
    private boolean fatiga;
    private boolean ronquidos;

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

    public boolean isDificultadRespirar() {
        return dificultadRespirar;
    }

    public void setDificultadRespirar(boolean dificultadRespirar) {
        this.dificultadRespirar = dificultadRespirar;
    }

    public boolean isProblemasDormir() {
        return problemasDormir;
    }

    public void setProblemasDormir(boolean problemasDormir) {
        this.problemasDormir = problemasDormir;
    }

    public boolean isFatiga() {
        return fatiga;
    }

    public void setFatiga(boolean fatiga) {
        this.fatiga = fatiga;
    }

    public boolean isRonquidos() {
        return ronquidos;
    }

    public void setRonquidos(boolean ronquidos) {
        this.ronquidos = ronquidos;
    }

}
