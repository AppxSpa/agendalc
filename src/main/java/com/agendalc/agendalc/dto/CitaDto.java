package com.agendalc.agendalc.dto;

import java.time.LocalDate;

import com.agendalc.agendalc.entities.Cita;

public class CitaDto {
    private Long idCita;
    private Integer rut;
    private Long idAgenda;
    private LocalDate fechaHora;
    private SolicitudAsociadaDto solicitud;
    private TramiteResponse tramite;
    private BloqueHorarioResponse bloqueHorario;

    public CitaDto(Cita cita) {
        this.idCita = cita.getIdCita();
        this.rut = cita.getRut();
        this.idAgenda = cita.getAgenda().getIdAgenda();
        this.fechaHora = cita.getAgenda().getFecha();
        this.bloqueHorario = new BloqueHorarioResponse(
                cita.getBloqueHorario().getIdBloque(),
                cita.getBloqueHorario().getHoraInicio().toString(),
                cita.getBloqueHorario().getHoraFin().toString(),
                cita.getBloqueHorario().getCuposDisponibles());
    }

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
