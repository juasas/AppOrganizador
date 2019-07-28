package com.example.juasa.apporganizador;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class Entrada_Salida extends AppCompatActivity {

    public void mostrar_mensaje (View view, String cadena){
        Toast mensaje = Toast.makeText(this, cadena, Toast.LENGTH_LONG);
        mensaje.show();
    }
}
