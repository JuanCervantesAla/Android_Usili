package com.example.usiliv101;

public class videoClass {
    String tipo, url;
    public videoClass() {

    }
    public videoClass(String tipo, String url) {
        this.tipo = tipo;
        this.url = url;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
