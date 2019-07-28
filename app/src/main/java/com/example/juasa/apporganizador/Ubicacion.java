package com.example.juasa.apporganizador;

public class Ubicacion {

    private int idUbicacion;
    private String nombreUbicacion, descripcionUbicacion;

    public Ubicacion (){
    }

    public Ubicacion (int idUbic, String nombreUbic, String descripcionUbic){
        this.idUbicacion = idUbic;
        this.nombreUbicacion = nombreUbic;
        this.descripcionUbicacion = descripcionUbic;
    }

    public int getIdUbicacion() {
        return idUbicacion;
    }

    public void setIdUbicacion(int idUbic) {
        this.idUbicacion = idUbic;
    }

    public String getNombreUbicacion() {
        return nombreUbicacion;
    }

    public void setNombreUbicacion(String nombreUbic) {
        this.nombreUbicacion = nombreUbic;
    }

    public String getDescripcionUbicacion() {
        return descripcionUbicacion;
    }

    public void setDescripcionUbicacion(String descripcionUbic) {
        this.descripcionUbicacion = descripcionUbic;
    }
}


