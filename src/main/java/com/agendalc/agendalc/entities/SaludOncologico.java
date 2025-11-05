package com.agendalc.agendalc.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
@Table(name = "salud_oncologico")
public class SaludOncologico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "formulario_id", referencedColumnName = "id")
    private SaludFormulario formulario;

    private boolean sistemaNervioso;
    private boolean cabezaCuello;
    private boolean torax;
    private boolean endocrino;
    private boolean reproductorMasculino;
    private boolean reproductorFemenino;
    private boolean oseoMuscular;
    private boolean piel;
    private boolean ojos;
    private boolean sistemaRespiratorio;
    private boolean sistemaDigestivo;
    private boolean sistemaGenitourinario;
    private boolean hematologico;
    private boolean otros;

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

    public boolean isSistemaNervioso() {
        return sistemaNervioso;
    }

    public void setSistemaNervioso(boolean sistemaNervioso) {
        this.sistemaNervioso = sistemaNervioso;
    }

    public boolean isCabezaCuello() {
        return cabezaCuello;
    }

    public void setCabezaCuello(boolean cabezaCuello) {
        this.cabezaCuello = cabezaCuello;
    }

    public boolean isTorax() {
        return torax;
    }

    public void setTorax(boolean torax) {
        this.torax = torax;
    }

    public boolean isEndocrino() {
        return endocrino;
    }

    public void setEndocrino(boolean endocrino) {
        this.endocrino = endocrino;
    }

    public boolean isReproductorMasculino() {
        return reproductorMasculino;
    }

    public void setReproductorMasculino(boolean reproductorMasculino) {
        this.reproductorMasculino = reproductorMasculino;
    }

    public boolean isReproductorFemenino() {
        return reproductorFemenino;
    }

    public void setReproductorFemenino(boolean reproductorFemenino) {
        this.reproductorFemenino = reproductorFemenino;
    }

    public boolean isOseoMuscular() {
        return oseoMuscular;
    }

    public void setOseoMuscular(boolean oseoMuscular) {
        this.oseoMuscular = oseoMuscular;
    }

    public boolean isPiel() {
        return piel;
    }

    public void setPiel(boolean piel) {
        this.piel = piel;
    }

    public boolean isOjos() {
        return ojos;
    }

    public void setOjos(boolean ojos) {
        this.ojos = ojos;
    }

    public boolean isSistemaRespiratorio() {
        return sistemaRespiratorio;
    }

    public void setSistemaRespiratorio(boolean sistemaRespiratorio) {
        this.sistemaRespiratorio = sistemaRespiratorio;
    }

    public boolean isSistemaDigestivo() {
        return sistemaDigestivo;
    }

    public void setSistemaDigestivo(boolean sistemaDigestivo) {
        this.sistemaDigestivo = sistemaDigestivo;
    }

    public boolean isSistemaGenitourinario() {
        return sistemaGenitourinario;
    }

    public void setSistemaGenitourinario(boolean sistemaGenitourinario) {
        this.sistemaGenitourinario = sistemaGenitourinario;
    }

    public boolean isHematologico() {
        return hematologico;
    }

    public void setHematologico(boolean hematologico) {
        this.hematologico = hematologico;
    }

    public boolean isOtros() {
        return otros;
    }

    public void setOtros(boolean otros) {
        this.otros = otros;
    }

}
