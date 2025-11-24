
package com.agendalc.agendalc.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "proceso_digitalizacion")
public class ProcesoDigitalizacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "rut_persona", nullable = false)
    private String rutPersona;

    @ManyToOne
    @JoinColumn(name = "tramite_id", nullable = false)
    private Tramite tramite;

    @JoinColumn(name = "fecha_proceso", nullable = false)
    private LocalDateTime fechaProceso;

    @JoinColumn(name = "usuario_responsable")
    private String usuarioResponsable;

    private String observaciones;

    @PrePersist
    protected void onCreate() {
        this.fechaProceso = LocalDateTime.now();
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

    public LocalDateTime getFechaProceso() {
        return fechaProceso;
    }

    public void setFechaProceso(LocalDateTime fechaProceso) {
        this.fechaProceso = fechaProceso;
    }

    public String getUsuarioResponsable() {
        return usuarioResponsable;
    }

    public void setUsuarioResponsable(String usuarioResponsable) {
        this.usuarioResponsable = usuarioResponsable;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}
