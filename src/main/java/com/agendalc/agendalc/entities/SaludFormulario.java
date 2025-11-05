package com.agendalc.agendalc.entities;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;

@Entity
@Table(name = "salud_formulario")
public class SaludFormulario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer rut;
    private LocalDate fechaFormulario;

    @OneToOne(mappedBy = "formulario", cascade = CascadeType.ALL)
    private SaludPersonales personales;

    @OneToMany(mappedBy = "formulario", cascade = CascadeType.ALL)
    private List<SaludLicenciasOtorgadas> licenciasOtorgadas;

    @OneToOne(mappedBy = "formulario", cascade = CascadeType.ALL)
    private SaludEstudios estudios;

    @OneToOne(mappedBy = "formulario", cascade = CascadeType.ALL)
    private SaludSituacionLaboral situacionLaboral;

    @OneToOne(mappedBy = "formulario", cascade = CascadeType.ALL)
    private SaludProfesion profesion;

    @OneToOne(mappedBy = "formulario", cascade = CascadeType.ALL)
    private SaludJornada jornada;

    @OneToOne(mappedBy = "formulario", cascade = CascadeType.ALL)
    private SaludCardio cardio;

    @OneToOne(mappedBy = "formulario", cascade = CascadeType.ALL)
    private SaludOftalmologico oftalmologico;

    @OneToOne(mappedBy = "formulario", cascade = CascadeType.ALL)
    private SaludOtorrino otorrino;

    @OneToOne(mappedBy = "formulario", cascade = CascadeType.ALL)
    private SaludNeurologico neurologico;

    @OneToOne(mappedBy = "formulario", cascade = CascadeType.ALL)
    private SaludMotriz motriz;

    @OneToOne(mappedBy = "formulario", cascade = CascadeType.ALL)
    private SaludRespiratorio respiratorio;

    @OneToOne(mappedBy = "formulario", cascade = CascadeType.ALL)
    private SaludEndocrino endocrino;

    @OneToOne(mappedBy = "formulario", cascade = CascadeType.ALL)
    private SaludOncologico oncologico;

    @OneToOne(mappedBy = "formulario", cascade = CascadeType.ALL)
    private SaludInmunologico inmunologico;

    @OneToOne(mappedBy = "formulario", cascade = CascadeType.ALL)
    private SaludOtros otros;

    @OneToMany(mappedBy = "formulario", cascade = CascadeType.ALL)
    private List<SaludMedicamentos> medicamentos;

    @OneToOne(mappedBy = "formulario", cascade = CascadeType.ALL)
    private SaludConduccion conduccion;

    public SaludCardio getCardio() {
        return cardio;
    }

    public void setCardio(SaludCardio cardio) {
        this.cardio = cardio;
    }

    public SaludOftalmologico getOftalmologico() {
        return oftalmologico;
    }

    public void setOftalmologico(SaludOftalmologico oftalmologico) {
        this.oftalmologico = oftalmologico;
    }

    public SaludOtorrino getOtorrino() {
        return otorrino;
    }

    public void setOtorrino(SaludOtorrino otorrino) {
        this.otorrino = otorrino;
    }

    public SaludNeurologico getNeurologico() {
        return neurologico;
    }

    public void setNeurologico(SaludNeurologico neurologico) {
        this.neurologico = neurologico;
    }

    public SaludMotriz getMotriz() {
        return motriz;
    }

    public void setMotriz(SaludMotriz motriz) {
        this.motriz = motriz;
    }

    public SaludRespiratorio getRespiratorio() {
        return respiratorio;
    }

    public void setRespiratorio(SaludRespiratorio respiratorio) {
        this.respiratorio = respiratorio;
    }

    public SaludEndocrino getEndocrino() {
        return endocrino;
    }

    public void setEndocrino(SaludEndocrino endocrino) {
        this.endocrino = endocrino;
    }

    public SaludOncologico getOncologico() {
        return oncologico;
    }

    public void setOncologico(SaludOncologico oncologico) {
        this.oncologico = oncologico;
    }

    public SaludInmunologico getInmunologico() {
        return inmunologico;
    }

    public void setInmunologico(SaludInmunologico inmunologico) {
        this.inmunologico = inmunologico;
    }

    public SaludOtros getOtros() {
        return otros;
    }

    public void setOtros(SaludOtros otros) {
        this.otros = otros;
    }

    public List<SaludMedicamentos> getMedicamentos() {
        return medicamentos;
    }

    public void setMedicamentos(List<SaludMedicamentos> medicamentos) {
        this.medicamentos = medicamentos;
    }

    public SaludConduccion getConduccion() {
        return conduccion;
    }

    public void setConduccion(SaludConduccion conduccion) {
        this.conduccion = conduccion;
    }

    public SaludJornada getJornada() {
        return jornada;
    }

    public void setJornada(SaludJornada jornada) {
        this.jornada = jornada;
    }

    public List<SaludLicenciasOtorgadas> getLicenciasOtorgadas() {
        return licenciasOtorgadas;
    }

    public void setLicenciasOtorgadas(List<SaludLicenciasOtorgadas> licenciasOtorgadas) {
        this.licenciasOtorgadas = licenciasOtorgadas;
    }

    public SaludEstudios getEstudios() {
        return estudios;
    }

    public void setEstudios(SaludEstudios estudios) {
        this.estudios = estudios;
    }

    public SaludSituacionLaboral getSituacionLaboral() {
        return situacionLaboral;
    }

    public void setSituacionLaboral(SaludSituacionLaboral situacionLaboral) {
        this.situacionLaboral = situacionLaboral;
    }

    public SaludProfesion getProfesion() {
        return profesion;
    }

    public void setProfesion(SaludProfesion profesion) {
        this.profesion = profesion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRut() {
        return rut;
    }

    public void setRut(Integer rut) {
        this.rut = rut;
    }

    public LocalDate getFechaFormulario() {
        return fechaFormulario;
    }

    public void setFechaFormulario(LocalDate fechaFormulario) {
        this.fechaFormulario = fechaFormulario;
    }

    public SaludPersonales getPersonales() {
        return personales;
    }

    public void setPersonales(SaludPersonales personales) {
        this.personales = personales;
    }

}
