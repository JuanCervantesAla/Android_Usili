package com.example.usiliv101;

public class trabajador {
    String nombre,telefono,email,eleccion,descripcion,zona,foto,diferencia=" ";

    public trabajador(){

    }

    public trabajador(String nombre,String telefono, String eleccion, String zona, String foto,String descripcion,String email){
        this.nombre=nombre;
        this.telefono = telefono;
        this.eleccion = eleccion;
        this.zona = zona;
        this.foto = foto;
        this.descripcion = descripcion;
        this.email=email;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEleccion() {
        return eleccion;
    }

    public void setEleccion(String eleccion) {
        this.eleccion = eleccion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getDiferencia() {
        return diferencia;
    }

    public void setDiferencia(String diferencia) {
        this.diferencia = diferencia;
    }
}
