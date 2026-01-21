package com.agendalc.agendalc.dto;

import java.util.List;
import java.util.Set;
import com.agendalc.agendalc.entities.enums.ClaseLicencia;

public class TramiteResponse {

    private Long idTramite;
    private String nombreTramite;
    private String descripcionTramite;
    private boolean pideDocumentos;
    private boolean requiereSolicitud;
    private List<DocumentosTramiteResponse> documentosRequeridos;
    private Set<ClaseLicencia> clasesLicencia;
    private boolean requiereAgenda;
    private boolean activo;

    public boolean isRequiereAgenda() {
        return requiereAgenda;
    }

    public void setRequiereAgenda(boolean requiereAgenda) {
        this.requiereAgenda = requiereAgenda;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public Set<ClaseLicencia> getClasesLicencia() {
        return clasesLicencia;
    }

    public void setClasesLicencia(Set<ClaseLicencia> clasesLicencia) {
        this.clasesLicencia = clasesLicencia;
    }

    public boolean isPideDocumentos() {
        return pideDocumentos;
    }

    public void setPideDocumentos(boolean pideDocumentos) {
        this.pideDocumentos = pideDocumentos;
    }

    public boolean isRequiereSolicitud() {
        return requiereSolicitud;
    }

    public void setRequiereSolicitud(boolean requiereSolicitud) {
        this.requiereSolicitud = requiereSolicitud;
    }

    public Long getIdTramite() {
        return idTramite;
    }

    public void setIdTramite(Long idTramite) {
        this.idTramite = idTramite;
    }

    public String getNombreTramite() {
        return nombreTramite;
    }

    public void setNombreTramite(String nombreTramite) {
        this.nombreTramite = nombreTramite;
    }

    public String getDescripcionTramite() {
        return descripcionTramite;
    }

    public void setDescripcionTramite(String descripcionTramite) {
        this.descripcionTramite = descripcionTramite;
    }

    public List<DocumentosTramiteResponse> getDocumentosRequeridos() {
        return documentosRequeridos;
    }

    public void setDocumentosRequeridos(List<DocumentosTramiteResponse> documentosRequeridos) {
        this.documentosRequeridos = documentosRequeridos;
    }

}
