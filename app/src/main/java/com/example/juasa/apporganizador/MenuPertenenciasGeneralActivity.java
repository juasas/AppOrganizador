package com.example.juasa.apporganizador;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.juasa.apporganizador.base_de_datos.Controlador_base_datos;
import com.example.juasa.apporganizador.datos.Datos;

public class MenuPertenenciasGeneralActivity extends AppCompatActivity {

    private Intent intento;
    private Controlador_base_datos controlador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_pertenencias_general);
        controlador = new Controlador_base_datos(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_action_secundario, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_action_secundario_volver_atras:
                intento = new Intent(this, MenuPrincipalActivity.class);
                startActivity(intento);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void entrarUbicacionesPpal (View view){
        intento = new Intent(this, UbicacionesPpalActivity.class);
        startActivity(intento);
    }

    public void entrarCategoriasPpal (View view){
        intento = new Intent(this, CategoriasPpalActivity.class);
        startActivity(intento);
    }

    public void entrarPertenenciasPpal (View view){
        if ((controlador.numRegistrosTabla(controlador.TABLA_CATEGORIAS) == 0) && ((controlador.numRegistrosTabla(controlador.TABLA_UBICACIONES) == 0))){
            AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.estiloCuadrodDialogo))
                    .setTitle("AVISO")
                    .setMessage("No hay ninguna UBICACIÓN ni ninguna CATEGORÍA creada. Por favor " +
                                "cree una de cada para poder crear PERTENENCIAS")
                    .setPositiveButton("Aceptar", null);
            AlertDialog dialog = builder.create();
            dialog.show();
        } else {
            if (controlador.numRegistrosTabla(controlador.TABLA_CATEGORIAS) == 0){
                AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.estiloCuadrodDialogo))
                .setTitle("AVISO")
                .setMessage("No hay ninguna CATEGORÍA creada. Por favor " +
                            "cree una de cada para poder crear PERTENENCIAS")
                .setPositiveButton("Aceptar", null);
                AlertDialog dialog = builder.create();
                dialog.show();
            } else {
                if (controlador.numRegistrosTabla(controlador.TABLA_UBICACIONES) == 0){
                    AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.estiloCuadrodDialogo))
                            .setTitle("AVISO")
                            .setMessage("No hay ninguna UBICACIÓN creada. Por favor " +
                                        "cree una de cada para poder crear PERTENENCIAS")
                            .setPositiveButton("Aceptar", null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else {
                    intento = new Intent(this, PertenenciasPpalActivity.class);
                    intento.putExtra("busqueda", "General");
                    intento.putExtra("parametro_buscado", "");
                    startActivity(intento);
                }
            }
        }
    }

    public void entrarMaletaPpal(View view) {
        if ((controlador.numRegistrosTabla(controlador.TABLA_PERTENENCIAS) == 0)){
            Datos.numeroMaleta = 0;
            AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.estiloCuadrodDialogo))
                    .setTitle("AVISO")
                    .setMessage("No existe ninguna PERTENENCIA creada. Para poder usar esta " +
                            "funcionalidad debe crear al menos una")
                    .setPositiveButton("Aceptar", null);
            AlertDialog dialog = builder.create();
            dialog.show();
        } else {
            intento = new Intent(this, MaletaPpalActivity.class);
            startActivity(intento);}
    }

    public void ayuda(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.estiloCuadrodDialogo))
                .setTitle("AYUDA")
                .setMessage ("Cree primero al menos una UBICACIÓN, a continuación, al menos " +
                        "una CATEGORÍA y por último PERTENENCIAS. " +
                        "No se permite usar la opción MALETA hasta que no se tenga alguna " +
                        "PERTENENCIA creada")
                .setPositiveButton("Aceptar", null);
        AlertDialog dialog = builder.create();
        dialog.show();}
}

