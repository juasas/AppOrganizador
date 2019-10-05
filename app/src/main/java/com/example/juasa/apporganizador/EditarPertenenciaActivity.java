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
import com.example.juasa.apporganizador.datos.Datos;

public class EditarPertenenciaActivity extends AppCompatActivity {

    private Controlador_base_datos controlador;
    private EditText cajaNombre, cajaDetalle, cajaTextoFoto;
    private Bundle extras;
    private int id_pert, idCategoria, idUbicacion;
    private String nombrePert, detallePert, nombreUbicacionPert, nombreCategoriaPert, nombreAntiguo, fotoPert;
    private View view;
    private Pertenencia pertenencia;
    private Spinner spinnerCategoria, spinnerUbicacion;
    private ArrayAdapter<String> adaptadorSpinnerCategoria, adaptadorspinnerUbicacion;
    private Intent intento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_pertenencia);

        //recibirParametros();
        inicializar();
        cargarDatos();
        actualizarUI_spinners();
        llenarPertenencia();
        //actualizarTextoFoto();
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
        cajaNombre = (EditText) findViewById(R.id.editar_pert_caja_nombre);
        cajaDetalle = (EditText) findViewById(R.id.editar_pert_caja_detalle);
        spinnerCategoria = findViewById(R.id.editar_pert_sp_categoria);
        spinnerUbicacion = findViewById(R.id.editar_pert_sp_ubic);
        cajaTextoFoto = (EditText) findViewById(R.id.editar_pert_caja_textoFoto);
    }

    public void cargarDatos (){
        id_pert = Datos.pertenenciaGlobal.getIdPertenencia();
        nombrePert = Datos.pertenenciaGlobal.getNombrePertenencia();
        nombreAntiguo = nombrePert;
        detallePert = Datos.pertenenciaGlobal.getDetallePertenencia();
        nombreCategoriaPert = Datos.pertenenciaGlobal.getNombreCategoriaPertenencia();
        nombreUbicacionPert = Datos.pertenenciaGlobal.getNombreUbicacionPertenencia();
        fotoPert = Datos.pertenenciaGlobal.getFotoPertenencia();
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
            fotoPert = extras.getString("foto_pert");
            if (((fotoPert == null) || (fotoPert.equals(""))) && (Datos.nombreFoto.equals(""))) {
                cajaTextoFoto.setText("Esta pertenencia no tiene fotografía");
            } else {
                if ((fotoPert == null) || (fotoPert.equals(""))) {
                    cajaTextoFoto.setText("Esta pertenencia no tiene fotografía");
                } else {
                    if ((fotoPert != null) || (!fotoPert.equals(""))) {
                        cajaTextoFoto.setText(fotoPert);
                    } else {
                        if (!Datos.nombreFoto.equals(""))
                            cajaTextoFoto.setText(Datos.nombreFoto);
                    }
                }
            }
        }
    }

    public void llenarPertenencia() {
            cajaNombre.setText(nombrePert);
            cajaDetalle.setText(detallePert);
            spinnerCategoria.setSelection(obtenerPosicionItem(spinnerCategoria, nombreCategoriaPert));
            spinnerUbicacion.setSelection(obtenerPosicionItem(spinnerUbicacion, nombreUbicacionPert));

            if ((fotoPert == null) || (fotoPert.equals("")))
                cajaTextoFoto.setText("Esta pertenencia no tiene fotografía");
            else
                cajaTextoFoto.setText(fotoPert);
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
        pertenencia.setFotoPertenencia(cajaTextoFoto.getText().toString());

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

    public void resetear(View view) {
        inicializar();
        cajaNombre.setText("");
        cajaDetalle.setText("");
        cajaTextoFoto.setText("");
    }

    public void actualizarUI_spinners() {
        adaptadorSpinnerCategoria = new ArrayAdapter(this, R.layout.elementos_spinner_ubic, R.id.nombre_elemento, controlador.obtenerCategorias());
        spinnerCategoria.setAdapter(adaptadorSpinnerCategoria);

        adaptadorspinnerUbicacion = new ArrayAdapter(this, R.layout.elementos_spinner_ubic, R.id.nombre_elemento, controlador.obtenerUbicaciones());
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
        dialog.show();
    }

    public void entrarMenuFoto(View view) {
        guardarDatos();
        intento = new Intent(this, FotografiaPpalActivity.class);
        startActivity(intento);
    }

    public void actualizarTextoFoto (){
        if ((Datos.nombreFoto.equals(""))){
            cajaTextoFoto.setText("Esta pertenencia no tiene fotografía");
        } else {
            cajaTextoFoto.setText(Datos.nombreFoto);
        }
    }

    private void guardarDatos(){
        Datos.pertenenciaGlobal.setIdPertenencia(id_pert);
        Datos.pertenenciaGlobal.setNombrePertenencia(nombrePert);
        Datos.pertenenciaGlobal.setDetallePertenencia(detallePert);
        Datos.pertenenciaGlobal.setNombreCategoriaPertenencia(nombreCategoriaPert);
        Datos.pertenenciaGlobal.setNombreUbicacionPertenencia(nombreUbicacionPert);
        Datos.pertenenciaGlobal.setFotoPertenencia(fotoPert);

    }
}
