package com.example.juasa.apporganizador;

public class Usuario {

    private String mail, nombre, password;

    public Usuario (){
    }

    public Usuario (String mailUsu, String nombreUsu, String passUsu){
        this.mail = mailUsu;
        this.nombre = nombreUsu;
        this.password = passUsu;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mailUsu) {
        this.mail = mailUsu;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombreUsu) {
        this.nombre = nombreUsu;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String passUsu) {
        this.password = passUsu;
    }
}
