package com.agendalc.agendalc.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
@Table(name = "salud_otros")
public class SaludOtros {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "formulario_id", referencedColumnName = "id")
    private SaludFormulario formulario;

    private boolean operado;
    private String detalleOperado;
    private boolean licenciaMedica;
    private String detalleLicenciaMedica;
    private boolean otraEnfermedad;
    private String detalleEnfermedad;

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

    public boolean isOperado() {
        return operado;
    }

    public void setOperado(boolean operado) {
        this.operado = operado;
    }

    public String getDetalleOperado() {
        return detalleOperado;
    }

    public void setDetalleOperado(String detalleOperado) {
        this.detalleOperado = detalleOperado;
    }

    public boolean isLicenciaMedica() {
        return licenciaMedica;
    }

    public void setLicenciaMedica(boolean licenciaMedica) {
        this.licenciaMedica = licenciaMedica;
    }

    public String getDetalleLicenciaMedica() {
        return detalleLicenciaMedica;
    }

    public void setDetalleLicenciaMedica(String detalleLicenciaMedica) {
        this.detalleLicenciaMedica = detalleLicenciaMedica;
    }

    public boolean isOtraEnfermedad() {
        return otraEnfermedad;
    }

    public void setOtraEnfermedad(boolean otraEnfermedad) {
        this.otraEnfermedad = otraEnfermedad;
    }

    public String getDetalleEnfermedad() {
        return detalleEnfermedad;
    }

    public void setDetalleEnfermedad(String detalleEnfermedad) {
        this.detalleEnfermedad = detalleEnfermedad;
    }
}
