package com.example.juasa.apporganizador;

public class Categoria {
    private int idCategoria;
    private String nombreCategoria, descripcionCategoria;

    public Categoria (){
    }

    public Categoria (int idCat, String nombreCat, String descripcionCat){
        this.idCategoria = idCat;
        this.nombreCategoria = nombreCat;
        this.descripcionCategoria = descripcionCat;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCat) {
        this.idCategoria = idCat;
    }

    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public void setNombreCategoria(String nombreCat) {
        this.nombreCategoria = nombreCat;
    }

    public String getDescripcionCategoria() {
        return descripcionCategoria;
    }

    public void setDescripcionCategoria (String descripcionCat) {
        this.descripcionCategoria = descripcionCat;
    }
}

