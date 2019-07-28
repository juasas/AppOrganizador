package com.example.juasa.apporganizador;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.juasa.apporganizador.base_de_datos.Controlador_base_datos;

public class BuscarPedirUbicacionActivity extends AppCompatActivity {
    private Controlador_base_datos controlador;
    private ArrayAdapter<String> adaptadorSpinnerUbic;
    private Spinner spinnerUbicacion;
    private String ubicacionBuscada;
    private Intent intento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_pedir_ubicacion);
        controlador = new Controlador_base_datos(this);
        spinnerUbicacion = findViewById(R.id.pedir_ubic_spinner);
        actualizarSpinner();
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        getMenuInflater().inflate(R.menu.menu_action_secundario, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item ) {
        switch (item.getItemId()) {
            case R.id.menu_action_secundario_volver_atras:
                intento = new Intent(this, PertenenciasPpalActivity.class);
                intento.putExtra("busqueda", "General");
                intento.putExtra("parametro_buscado", "");
                startActivity(intento);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void actualizarSpinner() {
        if (controlador.numRegistrosTabla(controlador.TABLA_UBICACIONES) == 0)
            spinnerUbicacion.setAdapter(null);
        else{
            adaptadorSpinnerUbic = new ArrayAdapter(this, R.layout.elementos_spinner, R.id.nombre_elemento, controlador.obtenerUbicaciones());
            spinnerUbicacion.setAdapter(adaptadorSpinnerUbic);}
    }

    public void buscar (View view){
        ubicacionBuscada = spinnerUbicacion.getSelectedItem().toString();
        intento = new Intent(this, PertenenciasPpalActivity.class);
        intento.putExtra("busqueda", "Por ubicación");
        intento.putExtra("parametro_buscado", ubicacionBuscada);
        startActivity(intento);
    }
}

