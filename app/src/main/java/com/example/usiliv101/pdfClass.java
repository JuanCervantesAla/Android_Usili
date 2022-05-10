package com.example.usiliv101;

public class pdfClass {
    public String nombre;
    public String url;

    public pdfClass(){

    }

    public pdfClass(String nombre, String url) {
        this.nombre = nombre;
        this.url = url;
    }

    public String getNombre(){
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
