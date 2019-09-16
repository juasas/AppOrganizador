package com.example.juasa.apporganizador;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.juasa.apporganizador.base_de_datos.Controlador_base_datos;

public class MostrarEstadisticaActivity extends AppCompatActivity {

    private Controlador_base_datos controlador;
    private TextView cajaFecha, cajaTotalUbic, cajaTotalCat, cajaTotalPert;
    private Intent intento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_estadistica);
        controlador = new Controlador_base_datos(this);
        inicializar();
        llenarPertenencia();
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.menu_action_ppal, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_action_ppal_volver_atras:
                intento = new Intent(this, MenuUsuarioGeneralActivity.class);
                startActivity(intento);
                break;
            case R.id.menu_action_ppal_gohome:
                intento = new Intent(this, MenuPrincipalActivity.class);
                startActivity(intento);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void inicializar(){
        cajaFecha = (TextView) findViewById(R.id.mostrar_estad_caja_fecha);
        cajaTotalUbic = (TextView) findViewById(R.id.mostrar_estad_caja_total_ubic);
        cajaTotalCat = (TextView) findViewById(R.id.mostrar_estad_caja_total_cat);
        cajaTotalPert = (TextView) findViewById(R.id.mostrar_estad_caja_total_pert);
    }

    public void llenarPertenencia(){
        String fecha = controlador.obtenerFecha();
        int totalUbic = controlador.estadisticas(controlador.TABLA_UBICACIONES);
        int totalCat = controlador.estadisticas(controlador.TABLA_CATEGORIAS);
        int totalPert = controlador.estadisticas(controlador.TABLA_PERTENENCIAS);
        //cajaFecha.setText(nombrePert);
        cajaFecha.setText(fecha);
        cajaTotalUbic.setText(String.valueOf(totalUbic));
        cajaTotalCat.setText(String.valueOf(totalCat));
        cajaTotalPert.setText(String.valueOf(totalPert));
    }

    public void ayuda(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.estiloCuadrodDialogo))
                .setTitle("AYUDA")
                .setMessage("Seleccione uno de los iconos de la barra superior de la pantalla. " +
                        "Puede: REGRESAR al MENÚ DE USUARIO o IR al MENÚ PRINCIPAL ")
                .setPositiveButton("Aceptar", null);
        AlertDialog dialog = builder.create();
        dialog.show();}
}
