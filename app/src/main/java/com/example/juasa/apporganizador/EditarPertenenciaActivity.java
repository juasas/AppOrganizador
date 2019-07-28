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
import android.widget.TextView;
import android.widget.Toast;

import com.example.juasa.apporganizador.base_de_datos.Controlador_base_datos;

public class EditarPertenenciaActivity extends AppCompatActivity {

    private Controlador_base_datos controlador;
    private EditText cajaNombre, cajaDetalle;
    private Bundle extras;
    private int id_pert, idCategoria, idUbicacion;
    private String nombreCategoriaPert, nombrePert, detallePert, nombreUbicacionPert, nombreAntiguo;
    //private String foto_pert;
    private View view;
    private Pertenencia pertenencia;
    private Spinner spinnerCategoria, spinnerUbicacion;
    private ArrayAdapter<String> adaptadorSpinnerCategoria, adaptadorspinnerUbicacion;
    private Intent intento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_pertenencia);
        controlador = new Controlador_base_datos(this);
        recibirParametros();
        inicializar();
        actualizarUI_spinners();
        llenarPertenencia();
    }

        public void inicializar (){
            cajaNombre = (EditText) findViewById(R.id.editar_pert_caja_nombre);
            cajaDetalle = (EditText) findViewById(R.id.editar_pert_caja_detalle);
            spinnerCategoria = findViewById(R.id.editar_pert_sp_categoria);
            spinnerUbicacion = findViewById(R.id.editar_pert_sp_ubic);
        }

    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.menu_action_secundario, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item ) {
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

    public void recibirParametros() {
        extras = getIntent().getExtras();
        if (extras != null) {
            id_pert = extras.getInt("id_pert");
            nombrePert = extras.getString("nombre_pert");
            nombreAntiguo = nombrePert;
            detallePert = extras.getString("detalle_pert");
            nombreCategoriaPert = extras.getString("categoria_pert");
            nombreUbicacionPert = extras.getString("ubicacion_pert");
            //foto_pert = extras.getString("foto_desc");
        }
    }

    public void llenarPertenencia(){
        cajaNombre.setText(nombrePert);
        cajaDetalle.setText(detallePert);
        spinnerCategoria.setSelection(obtenerPosicionItem(spinnerCategoria, nombreCategoriaPert));
        spinnerUbicacion.setSelection(obtenerPosicionItem(spinnerUbicacion, nombreUbicacionPert));
    }

    public void guardarPertenencia(View view) {
        inicializar();
        pertenencia = new Pertenencia();
        //Se guardan los valores de los spinner y se transforman en enteros

        String categoriaSp = spinnerCategoria.getSelectedItem().toString().toUpperCase();
        idCategoria = controlador.obtenerIdCategoria(categoriaSp);

        String ubicacionSp = spinnerUbicacion.getSelectedItem().toString().toUpperCase();
        idUbicacion = controlador.obtenerIdUbicacion(ubicacionSp);

        pertenencia.setIdPertenencia(id_pert);
        pertenencia.setNombrePertenencia(cajaNombre.getText().toString().toUpperCase());
        pertenencia.setDetallePertenencia(cajaDetalle.getText().toString());

        //int idCat = controlador.obtenerIdCategoria(pertenencia.getCategoriaPertenencia());
        //ubic = controlador.obtenerUbicacion(pertenencia.getUbicacionPertenencia());
        if (pertenencia.getNombrePertenencia().isEmpty()) {
            Toast mensaje = Toast.makeText(this, "El campo obligatorio NOMBRE no puede estar vacío", Toast.LENGTH_LONG);
            mensaje.show();
        } else {
            int numApariciones = controlador.numRegistrosIguales(Controlador_base_datos.TABLA_PERTENENCIAS,
                                                    "NOMBRE_PERT", pertenencia.getNombrePertenencia());
            if ((numApariciones == 0) || ((numApariciones != 0) && (pertenencia.getNombrePertenencia().equals(nombreAntiguo)))) {
                controlador.actualizarPertenencia(pertenencia, idCategoria, idUbicacion, nombreAntiguo);
                Toast mensaje = Toast.makeText(this, "Pertenencia editada y almacenada correctamente", Toast.LENGTH_LONG);
                mensaje.show();
                Intent intento = new Intent(this, PertenenciasPpalActivity.class);
                intento.putExtra("busqueda", "General");
                intento.putExtra("parametro_buscado", "");
                startActivity(intento);
            } else {
                Toast mensaje = Toast.makeText(this, "Nombre de la pertenencia no válido, Pertenencia ya existe", Toast.LENGTH_LONG);
                mensaje.show();
            }
        }
    }
    public void resetear (View view){
        inicializar();
        cajaNombre.setText("");
        cajaDetalle.setText("");
    }

    public void actualizarUI_spinners() {
        adaptadorSpinnerCategoria = new ArrayAdapter(this, R.layout.elementos_spinner, R.id.nombre_elemento, controlador.obtenerCategorias());
        spinnerCategoria.setAdapter(adaptadorSpinnerCategoria);

        adaptadorspinnerUbicacion = new ArrayAdapter(this, R.layout.elementos_spinner, R.id.nombre_elemento, controlador.obtenerUbicaciones());
        spinnerUbicacion.setAdapter(adaptadorspinnerUbicacion);
    }

    public int obtenerPosicionItem(Spinner spinner, String categoriaBuscada) {
        //Creamos la variable posicion y lo inicializamos en 0
        int posicion = 0;
        //Recorre el spinner en busca del ítem que coincida con el parametro categoriaBuscada
        //que lo pasaremos posteriormente
        for (int i = 0; i < spinner.getCount(); i++) {
            //Almacena la posición del ítem que coincida con la búsqueda
            if (spinner.getItemAtPosition(i).toString().equals(categoriaBuscada)) {
                posicion = i;
            }
        }
        //Devuelve un valor entero (si encontro una coincidencia devuelve la
        // posición 0 o N, de lo contrario devuelve 0 = posición inicial)
        return posicion;
    }

    public void ayuda(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.estiloCuadrodDialogo))
                .setTitle("AYUDA")
                .setMessage("Introduzca los nuevos valores que quiera dar a esta Pertenencia y " +
                            "pulse  el botón azul redondo en parte inferior derecha de la pantalla " +
                            "para aceptar")
                .setPositiveButton("Aceptar", null);
        AlertDialog dialog = builder.create();
        dialog.show();}
}
