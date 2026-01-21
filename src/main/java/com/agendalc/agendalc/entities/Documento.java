
package com.agendalc.agendalc.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "documento")
public class Documento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "rut_persona", nullable = false)
    private String rutPersona;

    @ManyToOne
    @JoinColumn(name = "tramite_id")
    private Tramite tramite;

    @ManyToOne
    @JoinColumn(name = "documentos_tramite_id")
    private DocumentosTramite documentosTramite;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cita_id")
    private Cita cita;

    @Column(name = "nombre_archivo", nullable = false)
    private String nombreArchivo;

    @Column(name = "path_storage", nullable = false, length = 1024)
    private String pathStorage;

    @Column(name = "tipo_mime")
    private String tipoMime;

    @Column(name = "fecha_carga", nullable = false)
    private LocalDateTime fechaCarga;

    @Column(name = "origen_id", nullable = false)
    private Long origenId;

    @Column(name = "origen_tipo", nullable = false)
    private String origenTipo; // "SOLICITUD", "CITA", "DIGITALIZACION"

    @Column(name = "aprobado")
    private boolean aprobado = false;

    @PrePersist
    protected void onCreate() {
        this.fechaCarga = LocalDateTime.now();
    }

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

    public Tramite getTramite() {
        return tramite;
    }

    public void setTramite(Tramite tramite) {
        this.tramite = tramite;
    }

    public DocumentosTramite getDocumentosTramite() {
        return documentosTramite;
    }

    public void setDocumentosTramite(DocumentosTramite documentosTramite) {
        this.documentosTramite = documentosTramite;
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

    public boolean isAprobado() {
        return aprobado;
    }

    public void setAprobado(boolean aprobado) {
        this.aprobado = aprobado;
    }

    public Cita getCita() {
        return cita;
    }

    public void setCita(Cita cita) {
        this.cita = cita;
    }
}
