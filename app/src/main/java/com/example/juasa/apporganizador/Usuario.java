package com.example.juasa.apporganizador;

public class Usuario {

    private String identificador, nombre, password;

    public Usuario (){
    }

    public Usuario (String identificadorUsuario, String nombreUsuario, String passUsuario){
        this.identificador = identificadorUsuario;
        this.nombre = nombreUsuario;
        this.password = passUsuario;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificadorUsu) {
        this.identificador = identificadorUsu;
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
