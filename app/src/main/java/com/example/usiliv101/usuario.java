package com.example.usiliv101;

public class usuario {

        public String correo,edad;

        public usuario(){

        }

        public usuario(String correo, String edad){
            this.correo = correo;
            this.edad = edad;
        }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }
        /*
    public String email;
    public String password;

    public usuario(){}
    public usuario(String email, String password) {
        this.email = email;
        this.password = password;
    }

     */
}
