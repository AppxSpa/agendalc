package com.agendalc.agendalc.dto;

import java.time.LocalDate;

public class CitaDto {
    private Long idCita;
    private Integer rut;
    private String dv;
    private String nombres;
    private String paterno;
    private String materno;

    public String getDv() {
        return dv;
    }

    public void setDv(String dv) {
        this.dv = dv;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getPaterno() {
        return paterno;
    }

    public void setPaterno(String paterno) {
        this.paterno = paterno;
    }

    public String getMaterno() {
        return materno;
    }

    public void setMaterno(String materno) {
        this.materno = materno;
    }

    private Long idAgenda;
    private LocalDate fechaHora;
    private SolicitudAsociadaDto solicitud;
    private TramiteResponse tramite;
    private BloqueHorarioResponse bloqueHorario;

    public Long getIdCita() {
        return idCita;
    }

    public void setIdCita(Long idCita) {
        this.idCita = idCita;
    }

    public Integer getRut() {
        return rut;
    }

    public void setRut(Integer rut) {
        this.rut = rut;
    }

    public Long getIdAgenda() {
        return idAgenda;
    }

    public void setIdAgenda(Long idAgenda) {
        this.idAgenda = idAgenda;
    }

    public LocalDate getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDate fechaHora) {

        this.fechaHora = fechaHora;

    }

    public SolicitudAsociadaDto getSolicitud() {

        return solicitud;

    }

    public void setSolicitud(SolicitudAsociadaDto solicitud) {

        this.solicitud = solicitud;

    }

    public TramiteResponse getTramite() {
        return tramite;
    }

    public void setTramite(TramiteResponse tramite) {
        this.tramite = tramite;
    }

    public BloqueHorarioResponse getBloqueHorario() {
        return bloqueHorario;
    }

    public void setBloqueHorario(BloqueHorarioResponse bloqueHorario) {
        this.bloqueHorario = bloqueHorario;
    }

}
