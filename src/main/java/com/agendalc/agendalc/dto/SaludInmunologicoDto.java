package com.agendalc.agendalc.dto;

public class SaludInmunologicoDto {
    private boolean higado;
    private boolean renal;

    public boolean isHigado() {
        return higado;
    }

    public void setHigado(boolean higado) {
        this.higado = higado;
    }

    public boolean isRenal() {
        return renal;
    }

    public void setRenal(boolean renal) {
        this.renal = renal;
    }
}
