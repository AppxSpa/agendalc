package com.agendalc.agendalc.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity
public class Cita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCita;

    @Column(nullable = false)
    private Integer rut;

    @ManyToOne
    @JoinColumn(name = "id_agenda", nullable = false)
    private Agenda agenda;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_bloque_horario", nullable = false)
    private BloqueHorario bloqueHorario;

    @OneToOne
    @JoinColumn(name = "id_salud_formulario", nullable = true)
    private SaludFormulario saludFormulario;

    @OneToOne
    @JoinColumn(name = "id_solicitud", nullable = true)
    private Solicitud solicitud;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime fechaHora;

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

    public Agenda getAgenda() {
        return agenda;
    }

    public void setAgenda(Agenda agenda) {
        this.agenda = agenda;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public BloqueHorario getBloqueHorario() {
        return bloqueHorario;
    }

    public void setBloqueHorario(BloqueHorario bloqueHorario) {
        this.bloqueHorario = bloqueHorario;
    }

    public LocalDate getFechaAgenda() {
        return agenda.getFecha();
    }

    public Long getIdBloqueHorario() {
        return this.bloqueHorario.getIdBloque();
    }

    public LocalTime getHoraInicioBloqueHoraio() {
        return this.bloqueHorario.getHoraInicio();

    }

    public LocalTime getHoraFinBloqueHoraio() {
        return this.bloqueHorario.getHoraFin();

    }

    public String nombreTramite() {
        return this.agenda.getTramite().getNombre();
    }

    public Tramite getTramite() {
        return this.agenda.getTramite();
    }

    public SaludFormulario getSaludFormulario() {
        return saludFormulario;
    }

        public void setSaludFormulario(SaludFormulario saludFormulario) {

            this.saludFormulario = saludFormulario;

        }

    

        public Solicitud getSolicitud() {

            return solicitud;

        }

    

        public void setSolicitud(Solicitud solicitud) {

            this.solicitud = solicitud;

        }

    }
