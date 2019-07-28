package com.example.juasa.apporganizador;

public class Pertenencia {

    private int idPertenencia;
    private String categoriaPertenencia, nombrePertenencia, detallePertenencia,
                   nombreCategoriaPertenencia, nombreUbicacionPertenencia, fotoPertenencia;

    public Pertenencia (){
    }

    public Pertenencia (int idPert, String nombrePert, String detallePert, String CatPert, String UbicPert, String fotoPert){
        this.idPertenencia = idPert;
        this.nombrePertenencia = nombrePert;
        this.detallePertenencia = detallePert;
        this.nombreCategoriaPertenencia = CatPert;
        this.nombreUbicacionPertenencia = UbicPert;
        this.fotoPertenencia = fotoPert;
    }

    public int getIdPertenencia() {
        return idPertenencia;
    }
    public void setIdPertenencia(int idPert) {
        this.idPertenencia = idPert;
    }

    public String getNombrePertenencia() {
        return nombrePertenencia;
    }
    public void setNombrePertenencia(String nombrePert) {
        this.nombrePertenencia = nombrePert;
    }

    public String getDetallePertenencia() {
        return detallePertenencia;
    }
    public void setDetallePertenencia(String detallePert) {
        this.detallePertenencia = detallePert;
    }

    public String getNombreCategoriaPertenencia() {
        return nombreCategoriaPertenencia;
    }
    public void setNombreCategoriaPertenencia(String CatPert) {
        this.nombreCategoriaPertenencia = CatPert;
    }

    public String getNombreUbicacionPertenencia() {
        return nombreUbicacionPertenencia;
    }

    public void setNombreUbicacionPertenencia(String UbicPert) {
        this.nombreUbicacionPertenencia = UbicPert;
    }

    public String getFotoPertenencia() {
        return fotoPertenencia;
    }
    public void setFotoPertenencia(String fotoPert) {
        this.fotoPertenencia = fotoPert;
    }
}



