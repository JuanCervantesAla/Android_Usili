package com.example.usiliv101;

import java.io.Serializable;

public class Articulos implements Serializable {

    public String titulo, pasos, materiales, autor,id, enlace,mayorTrece,enlace2,enlace3,pdf,video,key;


    public Articulos(){}
    public Articulos(String titulo, String pasos, String materiales, String autor, String id,String enlace,String mayorTrece,String enlace2,String enlace3,String pdf,String video,String key) {
        this.titulo = titulo;
        this.pasos = pasos;
        this.materiales = materiales;
        this.autor=autor;
        this.id = id;
        this.enlace=enlace;
        this.enlace2=enlace2;
        this.enlace3 = enlace3;
        this.mayorTrece=mayorTrece;
        this.pdf = pdf;
        this.video = video;
        this.key = key;

    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEnlace2() {
        return enlace2;
    }

    public void setEnlace2(String enlace2) {
        this.enlace2 = enlace2;
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

    public String getEnlace3() {
        return enlace3;
    }

    public void setEnlace3(String enlace3) {
        this.enlace3 = enlace3;
    }

    public String getPdf() {
        return pdf;
    }

    public void setPdf(String pdf) {
        this.pdf = pdf;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
