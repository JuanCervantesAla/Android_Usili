package com.example.usiliv101;

import java.io.Serializable;

public class Articulos implements Serializable {

    public String titulo, pasos, materiales, autor,id, enlace,mayorTrece;


    public Articulos(){}
    public Articulos(String titulo, String pasos, String materiales, String autor, String id,String enlace,String mayorTrece) {
        this.titulo = titulo;
        this.pasos = pasos;
        this.materiales = materiales;
        this.autor=autor;
        this.id = id;
        this.enlace=enlace;
        this.mayorTrece=mayorTrece;
    }

    public String getEnlace() {
        return enlace;
    }

    public void setEnlace(String enlace) {
        this.enlace = enlace;
    }

    public String getId() {
        return id;
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

    public String getMayorTrece() {
        return mayorTrece;
    }

    public void setMayorTrece(String mayorTrece) {
        this.mayorTrece = mayorTrece;
    }
}
