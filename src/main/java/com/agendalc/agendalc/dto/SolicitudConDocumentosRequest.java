package com.agendalc.agendalc.dto;

import java.util.List;

/**
 * DTO para crear una solicitud con documentos y tramiteLicencias asociadas
 */
public class SolicitudConDocumentosRequest {

    private Long idTramite;
    private Integer rut;
    private Long idSaludFormulario;
    private List<String> clases;
    private List<Long> idTramiteLicencias;
    private List<Long> idDocumentosTramite; // IDs de tipos de documentos requeridos

    public SolicitudConDocumentosRequest() {
    }

    public SolicitudConDocumentosRequest(Long idTramite, Integer rut, List<String> clases,
            List<Long> idTramiteLicencias, List<Long> idDocumentosTramite) {
        this.idTramite = idTramite;
        this.rut = rut;
        this.clases = clases;
        this.idTramiteLicencias = idTramiteLicencias;
        this.idDocumentosTramite = idDocumentosTramite;
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

    public List<String> getClases() {
        return clases;
    }

    public void setClases(List<String> clases) {
        this.clases = clases;
    }

    public List<Long> getIdTramiteLicencias() {
        return idTramiteLicencias;
    }

    public void setIdTramiteLicencias(List<Long> idTramiteLicencias) {
        this.idTramiteLicencias = idTramiteLicencias;
    }

    public List<Long> getIdDocumentosTramite() {
        return idDocumentosTramite;
    }

    public void setIdDocumentosTramite(List<Long> idDocumentosTramite) {
        this.idDocumentosTramite = idDocumentosTramite;
    }
}
