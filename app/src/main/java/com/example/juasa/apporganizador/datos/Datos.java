package com.example.juasa.apporganizador.datos;

import android.os.Environment;
import android.util.Log;

import com.example.juasa.apporganizador.Pertenencia;

import java.io.File;

public class Datos {
    public static String usuario = "";
    public static String identificador = "";
    public static int numeroMaleta = 0;
    public static int numeroUsuarios = 0;
    public static String nombreFoto = "";
    public static String nombreAntiguo = "";
    public static String rutaImagenes = "sdcard/Android/data/com.example.juasa.apporganizador/files/";
    public static Pertenencia pertenenciaGlobal = new Pertenencia();
    }
