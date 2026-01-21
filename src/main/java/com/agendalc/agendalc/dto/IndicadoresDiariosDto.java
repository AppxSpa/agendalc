package com.agendalc.agendalc.dto;

import java.util.List;

public class IndicadoresDiariosDto {

    private int cantidadAgendados;
    private double tasaAsistencia;
    private double tasaInasistencia;
    private List<DetalleAgendamientoContribuyente> detallesAgendamientos;

    public IndicadoresDiariosDto(int cantidadAgendados, double tasaAsistencia, double tasaInasistencia, List<DetalleAgendamientoContribuyente> detallesAgendamientos) {
        this.cantidadAgendados = cantidadAgendados;
        this.tasaAsistencia = tasaAsistencia;
        this.tasaInasistencia = tasaInasistencia;
        this.detallesAgendamientos = detallesAgendamientos;
    }

    public int getCantidadAgendados() {
        return cantidadAgendados;
    }

    public void setCantidadAgendados(int cantidadAgendados) {
        this.cantidadAgendados = cantidadAgendados;
    }

    public double getTasaAsistencia() {
        return tasaAsistencia;
    }

    public void setTasaAsistencia(double tasaAsistencia) {
        this.tasaAsistencia = tasaAsistencia;
    }

    public double getTasaInasistencia() {
        return tasaInasistencia;
    }

    public void setTasaInasistencia(double tasaInasistencia) {
        this.tasaInasistencia = tasaInasistencia;
    }

    public List<DetalleAgendamientoContribuyente> getDetallesAgendamientos() {
        return detallesAgendamientos;
    }

    public void setDetallesAgendamientos(List<DetalleAgendamientoContribuyente> detallesAgendamientos) {
        this.detallesAgendamientos = detallesAgendamientos;
    }

}
