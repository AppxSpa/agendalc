package com.agendalc.agendalc.dto;

import java.time.LocalDate;
import java.util.List;

public class SaludFormularioDto {
    private Integer rut;
    private LocalDate fechaFormulario;
    private Long idTramite;
    private Long idDeclaracion;

    private SaludPersonalesDto personales;
    private List<SaludLicenciasDto> licenciasOtorgadas;
    private SaludEstudiosDto estudios;
    private SaludSituacionLaboralDto situacionLaboral;
    private SaludProfesionDto profesion;
    private SaludJornadaDto jornada;
    private SaludCardioDto cardio;
    private SaludOftalmologicoDto oftalmologico;
    private SaludOtorrinoDto otorrino;
    private SaludNeurologicoDto neurologico;
    private SaludMotrizDto motriz;
    private SaludRespiratorioDto respiratorio;
    private SaludEndocrinoDto endocrino;
    private SaludOncologicoDto oncologico;
    private SaludInmunologicoDto inmunologico;
    private SaludOtrosDto otros;
    private List<SaludMedicamentoDto> medicamentos;
    private SaludConduccionDto conduccion;
    private String firma;

    public SaludPersonalesDto getPersonales() {
        return personales;
    }

    public void setPersonales(SaludPersonalesDto personales) {
        this.personales = personales;
    }

    public List<SaludLicenciasDto> getLicenciasOtorgadas() {
        return licenciasOtorgadas;
    }

    public void setLicenciasOtorgadas(List<SaludLicenciasDto> licenciasOtorgadas) {
        this.licenciasOtorgadas = licenciasOtorgadas;
    }

    public SaludEstudiosDto getEstudios() {
        return estudios;
    }

    public void setEstudios(SaludEstudiosDto estudios) {
        this.estudios = estudios;
    }

    public SaludSituacionLaboralDto getSituacionLaboral() {
        return situacionLaboral;
    }

    public void setSituacionLaboral(SaludSituacionLaboralDto situacionLaboral) {
        this.situacionLaboral = situacionLaboral;
    }

    public SaludProfesionDto getProfesion() {
        return profesion;
    }

    public void setProfesion(SaludProfesionDto profesion) {
        this.profesion = profesion;
    }

    public SaludJornadaDto getJornada() {
        return jornada;
    }

    public void setJornada(SaludJornadaDto jornada) {
        this.jornada = jornada;
    }

    public SaludCardioDto getCardio() {
        return cardio;
    }

    public void setCardio(SaludCardioDto cardio) {
        this.cardio = cardio;
    }

    public SaludOftalmologicoDto getOftalmologico() {
        return oftalmologico;
    }

    public void setOftalmologico(SaludOftalmologicoDto oftalmologico) {
        this.oftalmologico = oftalmologico;
    }

    public SaludOtorrinoDto getOtorrino() {
        return otorrino;
    }

    public void setOtorrino(SaludOtorrinoDto otorrino) {
        this.otorrino = otorrino;
    }

    public SaludNeurologicoDto getNeurologico() {
        return neurologico;
    }

    public void setNeurologico(SaludNeurologicoDto neurologico) {
        this.neurologico = neurologico;
    }

    public SaludMotrizDto getMotriz() {
        return motriz;
    }

    public void setMotriz(SaludMotrizDto motriz) {
        this.motriz = motriz;
    }

    public SaludRespiratorioDto getRespiratorio() {
        return respiratorio;
    }

    public void setRespiratorio(SaludRespiratorioDto respiratorio) {
        this.respiratorio = respiratorio;
    }

    public SaludEndocrinoDto getEndocrino() {
        return endocrino;
    }

    public void setEndocrino(SaludEndocrinoDto endocrino) {
        this.endocrino = endocrino;
    }

    public SaludOncologicoDto getOncologico() {
        return oncologico;
    }

    public void setOncologico(SaludOncologicoDto oncologico) {
        this.oncologico = oncologico;
    }

    public SaludInmunologicoDto getInmunologico() {
        return inmunologico;
    }

    public void setInmunologico(SaludInmunologicoDto inmunologico) {
        this.inmunologico = inmunologico;
    }

    public SaludOtrosDto getOtros() {
        return otros;
    }

    public void setOtros(SaludOtrosDto otros) {
        this.otros = otros;
    }

    public java.util.List<SaludMedicamentoDto> getMedicamentos() {
        return medicamentos;
    }

    public void setMedicamentos(java.util.List<SaludMedicamentoDto> medicamentos) {
        this.medicamentos = medicamentos;
    }

    public SaludConduccionDto getConduccion() {
        return conduccion;
    }

    public void setConduccion(SaludConduccionDto conduccion) {
        this.conduccion = conduccion;
    }

    public LocalDate getFechaFormulario() {
        return fechaFormulario;
    }

    public void setFechaFormulario(LocalDate fechaFormulario) {
        this.fechaFormulario = fechaFormulario;
    }

    public Integer getRut() {
        return rut;
    }

    public void setRut(Integer rut) {
        this.rut = rut;
    }

    public Long getIdTramite() {
        return idTramite;
    }

    public void setIdTramite(Long idTramite) {
        this.idTramite = idTramite;
    }

    public Long getIdDeclaracion() {
        return idDeclaracion;
    }

    public void setIdDeclaracion(Long idDeclaracion) {
        this.idDeclaracion = idDeclaracion;
    }

    public String getFirma() {
        return firma;
    }

    public void setFirma(String firma) {
        this.firma = firma;
    }
}
