package com.example.juasa.apporganizador;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.juasa.apporganizador.base_de_datos.Controlador_base_datos;

public class BuscarPedirCategoriaActivity extends AppCompatActivity {
    private Controlador_base_datos controlador;
    private ArrayAdapter<String> adaptadorSpinnerCat;
    private Spinner spinnerCategoria;
    private String categoriaBuscada;
    private ImageView iconoAyuda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_pedir_categoria);
        controlador = new Controlador_base_datos(this);
        spinnerCategoria = findViewById(R.id.buscar_pedir_cat_spinner);
        iconoAyuda = findViewById(R.id.buscar_pedir_cat_icono_ayuda);
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
                Intent intento = new Intent(this, PertenenciasPpalActivity.class);
                intento.putExtra("busqueda", "General");
                startActivity(intento);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void actualizarSpinner() {
        if (controlador.numRegistrosTabla(Controlador_base_datos.TABLA_CATEGORIAS) == 0)
            spinnerCategoria.setAdapter(null);
        else{
            adaptadorSpinnerCat = new ArrayAdapter(this, R.layout.elementos_spinner, R.id.nombre_elemento, controlador.obtenerCategorias());
            spinnerCategoria.setAdapter(adaptadorSpinnerCat);}
    }

    public void buscar (View view){
        categoriaBuscada = spinnerCategoria.getSelectedItem().toString();
        Intent intento = new Intent(this, PertenenciasPpalActivity.class);
        intento.putExtra("busqueda", "Por categoria");
        intento.putExtra("parametro_buscado", categoriaBuscada);
        startActivity(intento);
    }

    public void ayuda(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.estiloCuadrodDialogo))
                .setTitle("AYUDA")
                .setMessage("Si desea buscar una Pertenencia por Categoría, seleccione una " +
                            "Categoría de las disponibles en la lista desplegable. " +
                            "A continuación pulse en el botón BUSCAR")
                .setPositiveButton("Aceptar", null);
        AlertDialog dialog = builder.create();
        dialog.show();}
}

