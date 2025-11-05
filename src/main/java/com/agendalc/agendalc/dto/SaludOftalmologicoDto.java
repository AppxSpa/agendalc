package com.agendalc.agendalc.dto;

public class SaludOftalmologicoDto {

    private CataratasDto cataratas;
    private GlaucomaDto glaucoma;
    private RetinaDto retina;

    private boolean lentesCerca;
    private boolean lentesLejos;
    private boolean operacionOjos;

    public CataratasDto getCataratas() {
        return cataratas;
    }

    public void setCataratas(CataratasDto cataratas) {
        this.cataratas = cataratas;
    }

    public GlaucomaDto getGlaucoma() {
        return glaucoma;
    }

    public void setGlaucoma(GlaucomaDto glaucoma) {
        this.glaucoma = glaucoma;
    }

    public RetinaDto getRetina() {
        return retina;
    }

    public void setRetina(RetinaDto retina) {
        this.retina = retina;
    }

    public boolean isLentesCerca() {
        return lentesCerca;
    }

    public void setLentesCerca(boolean lentesCerca) {
        this.lentesCerca = lentesCerca;
    }

    public boolean isLentesLejos() {
        return lentesLejos;
    }

    public void setLentesLejos(boolean lentesLejos) {
        this.lentesLejos = lentesLejos;
    }

    public boolean isOperacionOjos() {
        return operacionOjos;
    }

    public void setOperacionOjos(boolean operacionOjos) {
        this.operacionOjos = operacionOjos;
    }

}
