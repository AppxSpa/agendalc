package com.agendalc.agendalc.dto;

import java.util.List;

public class SolicitudRequest {

    private Long idTramite;
    private Integer rut;
    private Long idSaludFormulario;
    private Long idCita;
    private List<DocumentosSubidosRequest> documentos;
    private List<String> clases; // Campo simplificado

    public SolicitudRequest() {
    }

    public SolicitudRequest(Long idTramite, Integer rut, List<DocumentosSubidosRequest> documentos, List<String> clases) {
        this.idTramite = idTramite;
        this.rut = rut;
        this.documentos = documentos;
        this.clases = clases;
    }

    // Getters y Setters

    public Long getIdTramite() {
        return idTramite;
    }

    public void setIdTramite(Long idTramite) {
        this.idTramite = idTramite;
    }

    public Integer getRut() {
        return rut;
    }

    public void setRut(Integer rut) {
        this.rut = rut;
    }

    public Long getIdSaludFormulario() {
        return idSaludFormulario;
    }

    public void setIdSaludFormulario(Long idSaludFormulario) {
        this.idSaludFormulario = idSaludFormulario;
    }

    public Long getIdCita() {
        return idCita;
    }

    public void setIdCita(Long idCita) {
        this.idCita = idCita;
    }

    public List<DocumentosSubidosRequest> getDocumentos() {
        return documentos;
    }

    public void setDocumentos(List<DocumentosSubidosRequest> documentos) {
        this.documentos = documentos;
    }

    public List<String> getClases() {
        return clases;
    }

    public void setClases(List<String> clases) {
        this.clases = clases;
    }
}
