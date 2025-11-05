package com.agendalc.agendalc.dto;

public class SaludPersonalesDto {
    public enum Genero {
        MASCULINO, FEMENINO, OTRO
    }

    private Genero genero;
    private Integer edad;
    private double peso;
    private double altura;

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public double getAltura() {
        return altura;
    }

    public void setAltura(double altura) {
        this.altura = altura;
    }
}
