
package com.agendalc.agendalc.dto;

import java.time.LocalDateTime;

public class DocumentoDto {

    private Long id;
    private String rutPersona;
    private Long tramiteId;
    private String nombreTramite;
    private Long documentosTramiteId;
    private String nombreDocumento;
    private String nombreArchivo;
    private String pathStorage;
    private String tipoMime;
    private LocalDateTime fechaCarga;
    private Long origenId;
    private String origenTipo;

    public DocumentoDto() {
    }

    private DocumentoDto(Builder builder) {
        this.id = builder.id;
        this.rutPersona = builder.rutPersona;
        this.tramiteId = builder.tramiteId;
        this.nombreTramite = builder.nombreTramite;
        this.documentosTramiteId = builder.documentosTramiteId;
        this.nombreDocumento = builder.nombreDocumento;
        this.nombreArchivo = builder.nombreArchivo;
        this.pathStorage = builder.pathStorage;
        this.tipoMime = builder.tipoMime;
        this.fechaCarga = builder.fechaCarga;
        this.origenId = builder.origenId;
        this.origenTipo = builder.origenTipo;
    }

    public static Builder builder() {
        return new Builder();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRutPersona() {
        return rutPersona;
    }

    public void setRutPersona(String rutPersona) {
        this.rutPersona = rutPersona;
    }

    public Long getTramiteId() {
        return tramiteId;
    }

    public void setTramiteId(Long tramiteId) {
        this.tramiteId = tramiteId;
    }

    public String getNombreTramite() {
        return nombreTramite;
    }

    public void setNombreTramite(String nombreTramite) {
        this.nombreTramite = nombreTramite;
    }

    public Long getDocumentosTramiteId() {
        return documentosTramiteId;
    }

    public void setDocumentosTramiteId(Long documentosTramiteId) {
        this.documentosTramiteId = documentosTramiteId;
    }

    public String getNombreDocumento() {
        return nombreDocumento;
    }

    public void setNombreDocumento(String nombreDocumento) {
        this.nombreDocumento = nombreDocumento;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public String getPathStorage() {
        return pathStorage;
    }

    public void setPathStorage(String pathStorage) {
        this.pathStorage = pathStorage;
    }

    public String getTipoMime() {
        return tipoMime;
    }

    public void setTipoMime(String tipoMime) {
        this.tipoMime = tipoMime;
    }

    public LocalDateTime getFechaCarga() {
        return fechaCarga;
    }

    public void setFechaCarga(LocalDateTime fechaCarga) {
        this.fechaCarga = fechaCarga;
    }

    public Long getOrigenId() {
        return origenId;
    }

    public void setOrigenId(Long origenId) {
        this.origenId = origenId;
    }

    public String getOrigenTipo() {
        return origenTipo;
    }

    public void setOrigenTipo(String origenTipo) {
        this.origenTipo = origenTipo;
    }

    public static class Builder {
        private Long id;
        private String rutPersona;
        private Long tramiteId;
        private String nombreTramite;
        private Long documentosTramiteId;
        private String nombreDocumento;
        private String nombreArchivo;
        private String pathStorage;
        private String tipoMime;
        private LocalDateTime fechaCarga;
        private Long origenId;
        private String origenTipo;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder rutPersona(String rutPersona) {
            this.rutPersona = rutPersona;
            return this;
        }

        public Builder tramiteId(Long tramiteId) {
            this.tramiteId = tramiteId;
            return this;
        }

        public Builder nombreTramite(String nombreTramite) {
            this.nombreTramite = nombreTramite;
            return this;
        }

        public Builder documentosTramiteId(Long documentosTramiteId) {
            this.documentosTramiteId = documentosTramiteId;
            return this;
        }

        public Builder nombreDocumento(String nombreDocumento) {
            this.nombreDocumento = nombreDocumento;
            return this;
        }

        public Builder nombreArchivo(String nombreArchivo) {
            this.nombreArchivo = nombreArchivo;
            return this;
        }

        public Builder pathStorage(String pathStorage) {
            this.pathStorage = pathStorage;
            return this;
        }

        public Builder tipoMime(String tipoMime) {
            this.tipoMime = tipoMime;
            return this;
        }

        public Builder fechaCarga(LocalDateTime fechaCarga) {
            this.fechaCarga = fechaCarga;
            return this;
        }

        public Builder origenId(Long origenId) {
            this.origenId = origenId;
            return this;
        }

        public Builder origenTipo(String origenTipo) {
            this.origenTipo = origenTipo;
            return this;
        }

        public DocumentoDto build() {
            return new DocumentoDto(this);
        }
    }
}
