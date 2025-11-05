package com.agendalc.agendalc.dto;

public class SaludOtrosDto {
    private OperadoDto operaciones;
    private EnfermedadDto enfermedad;
    private LicenciaMedicaDto otraEnfermedad;

    public static class OperadoDto {
        private boolean operado;
        private String detalle;

        public boolean isOperado() {
            return operado;
        }

        public void setOperado(boolean operado) {
            this.operado = operado;
        }

        public String getDetalle() {
            return detalle;
        }

        public void setDetalle(String detalle) {
            this.detalle = detalle;
        }
    }

    public static class EnfermedadDto {

        private boolean tieneEnfermedad;
        private String detalle;

        public boolean isTieneEnfermedad() {
            return tieneEnfermedad;
        }

        public void setTieneEnfermedad(boolean tieneEnfermedad) {
            this.tieneEnfermedad = tieneEnfermedad;
        }

        public String getDetalle() {
            return detalle;
        }

        public void setDetalle(String detalle) {
            this.detalle = detalle;
        }

    }

    public static class LicenciaMedicaDto {
        private boolean licencia;
        private String detalle;

        public boolean isLicencia() {
            return licencia;
        }

        public void setLicencia(boolean licencia) {
            this.licencia = licencia;
        }

        public String getDetalle() {
            return detalle;
        }

        public void setDetalle(String detalle) {
            this.detalle = detalle;
        }
    }

    public OperadoDto getOperaciones() {
        return operaciones;
    }

    public void setOperaciones(OperadoDto operaciones) {
        this.operaciones = operaciones;
    }

    public EnfermedadDto getEnfermedad() {
        return enfermedad;
    }

    public void setEnfermedad(EnfermedadDto enfermedad) {
        this.enfermedad = enfermedad;
    }

    public LicenciaMedicaDto getOtraEnfermedad() {
        return otraEnfermedad;
    }

    public void setOtraEnfermedad(LicenciaMedicaDto otraEnfermedad) {
        this.otraEnfermedad = otraEnfermedad;
    }
}
