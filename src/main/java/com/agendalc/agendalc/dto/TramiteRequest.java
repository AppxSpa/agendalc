package com.agendalc.agendalc.dto;

import java.util.Set;
import com.agendalc.agendalc.entities.enums.ClaseLicencia;

public class TramiteRequest {

    private String nombre;
    private String descripcion;
    private boolean requiereSolicitud;
    private boolean pideDocumentos;
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

    public boolean isRequiereSolicitud() {
        return requiereSolicitud;
    }

    public void setRequiereSolicitud(boolean requiereSolicitud) {
        this.requiereSolicitud = requiereSolicitud;
    }

    public boolean isPideDocumentos() {
        return pideDocumentos;
    }

    public void setPideDocumentos(boolean pideDocumentos) {
        this.pideDocumentos = pideDocumentos;
    }

    public TramiteRequest() {
    }

    public TramiteRequest(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

}
