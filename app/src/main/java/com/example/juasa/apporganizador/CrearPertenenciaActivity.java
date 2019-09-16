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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.juasa.apporganizador.base_de_datos.Controlador_base_datos;

public class CrearPertenenciaActivity extends AppCompatActivity {
    private Controlador_base_datos controlador;
    private Entrada_Salida salida;
    private EditText cajaNombre, cajaDetalle;
    private ArrayAdapter<String> adaptadorSpinnerCategoria, adaptadorSpinnerUbicacion;
    private Spinner spinnerCategoria, spinnerUbicacion;
    private String categoriaPertenencia, nombrePertenencia, detallePertenencia, ubicacionPertenencia, fotoPertenencia;
    private Intent intento;
    private int idCategoria, idUbicacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_pertenencia);
        inicializar();
        actualizarUI_spinners();
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
                Intent intento = new Intent(this, PertenenciasPpalActivity.class);
                intento.putExtra("busqueda", "General");
                intento.putExtra("parametro_buscado", "");
                startActivity(intento);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void inicializar() {
        controlador = new Controlador_base_datos(this);
        spinnerCategoria = findViewById(R.id.crear_pert_sp_categoria);
        spinnerUbicacion = findViewById(R.id.crear_pert_sp_ubic);
        cajaNombre = (EditText) findViewById(R.id.crear_pert_caja_nombre);
        cajaDetalle = (EditText) findViewById(R.id.crear_pert_caja_detalle);
    }

    public void crearNuevaPertenencia(View view) {
        nombrePertenencia = cajaNombre.getText().toString().toUpperCase();
        detallePertenencia = cajaDetalle.getText().toString().toUpperCase();
        categoriaPertenencia = spinnerCategoria.getSelectedItem().toString().toUpperCase();

        idCategoria = controlador.obtenerIdCategoria(categoriaPertenencia);
        ubicacionPertenencia = spinnerUbicacion.getSelectedItem().toString().toUpperCase();
        idUbicacion = controlador.obtenerIdUbicacion(ubicacionPertenencia);

        if (nombrePertenencia.isEmpty()) {
            Toast mensaje = Toast.makeText(this, "El campo obligatorio NOMBRE no puede estar vacío", Toast.LENGTH_LONG);
            mensaje.show();
        } else {{
            if (!controlador.existe(controlador.TABLA_PERTENENCIAS, "NOMBRE_PERT", nombrePertenencia)) {
                controlador.anadirPertenencia(nombrePertenencia, detallePertenencia, idCategoria, idUbicacion, fotoPertenencia);
                Toast mensaje = Toast.makeText(this, "Pertenencia almacenada correctamente", Toast.LENGTH_LONG);
                mensaje.show();
                intento = new Intent(this, PertenenciasPpalActivity.class);
                intento.putExtra("busqueda", "General");
                intento.putExtra("parametro_buscado", "");
                startActivity(intento);
            } else {
                Toast mensaje = Toast.makeText(this, "No se puede usar ese nombre. La pertenencia ya existe", Toast.LENGTH_LONG);
                mensaje.show(); }
            }}
    }

    public void resetear(View view) {
        //crear_pertenencia_cajaTipo.setText("");
        cajaNombre.setText("");
        cajaDetalle.setText("");
        //crear_pertenencia_cajaUbic.setText("");
    }

    public void actualizarUI_spinners() {
        if (controlador.numRegistrosTabla(Controlador_base_datos.TABLA_CATEGORIAS) == 0)
            spinnerCategoria.setAdapter(null);
        else {
            adaptadorSpinnerCategoria = new ArrayAdapter(this, R.layout.elementos_spinner, R.id.nombre_elemento, controlador.obtenerCategorias());
            spinnerCategoria.setAdapter(adaptadorSpinnerCategoria);}

        if (controlador.numRegistrosTabla(controlador.TABLA_UBICACIONES) == 0)
            spinnerUbicacion.setAdapter(null);
        else{
            adaptadorSpinnerUbicacion = new ArrayAdapter(this, R.layout.elementos_spinner, R.id.nombre_elemento, controlador.obtenerUbicaciones());
            spinnerUbicacion.setAdapter(adaptadorSpinnerUbicacion);}
    }

    public void ayuda(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.estiloCuadrodDialogo))
                .setTitle("AYUDA")
                .setMessage("Introduzca los valores que quiera dar a esta Pertenencia y " +
                        "pulse  el botón azul redondo en parte inferior derecha de la pantalla " +
                        "para aceptar")
                .setPositiveButton("Aceptar", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void entrarMenuFoto(View view){
        intento = new Intent(this, FotografiaPpalActivity.class);
        startActivity(intento);
    }
}

