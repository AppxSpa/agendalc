package com.agendalc.agendalc.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
@Table(name = "salud_licencias_otorgadas")
public class SaludLicenciasOtorgadas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_formulario")
    private SaludFormulario formulario;

    private String tiposLicencias;

    private boolean profesional;

    private boolean otras;

    private boolean leyAntigua;

    private boolean noProfesional;

    private boolean especial;

    public boolean isNoProfesional() {
        return noProfesional;
    }

    public void setNoProfesional(boolean noProfesional) {
        this.noProfesional = noProfesional;
    }

    public boolean isEspecial() {
        return especial;
    }

    public void setEspecial(boolean especial) {
        this.especial = especial;
    }

    public boolean isLeyAntigua() {
        return leyAntigua;
    }

    public void setLeyAntigua(boolean leyAntigua) {
        this.leyAntigua = leyAntigua;
    }

    public boolean isProfesional() {
        return profesional;
    }

    public void setProfesional(boolean profesional) {
        this.profesional = profesional;
    }

    public boolean isOtras() {
        return otras;
    }

    public void setOtras(boolean otras) {
        this.otras = otras;
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

    public String getTiposLicencias() {
        return tiposLicencias;
    }

    public void setTiposLicencias(String tiposLicencias) {
        this.tiposLicencias = tiposLicencias;
    }

}
