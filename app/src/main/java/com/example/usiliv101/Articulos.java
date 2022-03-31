package com.example.usiliv101;

public class Articulos {

    public String titulo, pasos, materiales, autor;
    public Integer id;

    public Articulos(){}
    public Articulos(String titulo, String pasos, String materiales, String autor, Integer id) {
        this.titulo = titulo;
        this.pasos = pasos;
        this.materiales = materiales;
        this.autor=autor;
        this.id = id;
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
