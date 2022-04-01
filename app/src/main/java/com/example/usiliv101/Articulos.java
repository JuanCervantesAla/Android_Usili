package com.example.usiliv101;

public class Articulos {

    public String titulo, pasos, materiales, autor, enlace;
    public Integer id;

    public Articulos(){}
    public Articulos(String titulo, String pasos, String materiales, String autor, Integer id,String enalce) {
        this.titulo = titulo;
        this.pasos = pasos;
        this.materiales = materiales;
        this.autor=autor;
        this.id = id;
        this.enlace=enlace;
    }

    public String getEnlace() {
        return enlace;
    }

    public void setEnlace(String enlace) {
        this.enlace = enlace;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getPasos() {
        return pasos;
    }

    public void setPasos(String pasos) {
        this.pasos = pasos;
    }

    public String getMateriales() {
        return materiales;
    }

    public void setMateriales(String materiales) {
        this.materiales = materiales;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }
}
