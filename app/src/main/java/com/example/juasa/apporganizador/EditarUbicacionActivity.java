package com.example.juasa.apporganizador;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.juasa.apporganizador.base_de_datos.Controlador_base_datos;

public class EditarUbicacionActivity extends AppCompatActivity {
    private Controlador_base_datos controlador;
    private EditText cajaNombre, cajaDetalle;
    private View view;
    private Bundle extras;
    private String nombreUbicacion, detalleUbicacion, nombreAntiguo, mensaje;
    private Ubicacion ubicacion;
    private Intent intento;
    private Toast toast;

    public void inicializar (){
        cajaNombre = (EditText) findViewById(R.id.editar_ubic_caja_nombre);
        cajaDetalle = (EditText) findViewById(R.id.editar_ubic_caja_detalle);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_ubicacion);
        controlador = new Controlador_base_datos(this);
        recibirParametros();
        inicializar();
        llenarUbicacion();
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
                Intent intento = new Intent(this, UbicacionesPpalActivity.class);
                startActivity(intento);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void recibirParametros() {
        extras = getIntent().getExtras();
        if (extras != null) {
            nombreUbicacion = extras.getString("nombre_ubicacion");
            nombreAntiguo = nombreUbicacion;
            detalleUbicacion = extras.getString("detalle_ubicacion");
        }
    }

    public void llenarUbicacion(){
        cajaNombre.setText(nombreUbicacion);
        cajaDetalle.setText(detalleUbicacion);
    }

    public void guardar(View view){
        inicializar();
        ubicacion = new Ubicacion();
        ubicacion.setNombreUbicacion(cajaNombre.getText().toString().toUpperCase());
        ubicacion.setDescripcionUbicacion(cajaDetalle.getText().toString());

        if (ubicacion.getNombreUbicacion().isEmpty()) {
            mensaje = "El campo obligatorio NOMBRE no puede estar vacío";
            mostrarMensaje(mensaje);
        } else {
            int numApariciones = controlador.numRegistrosIguales(Controlador_base_datos.TABLA_UBICACIONES,"NOMBRE_UBICACION", ubicacion.getNombreUbicacion());
            if ((numApariciones == 0) || ((numApariciones != 0) && (ubicacion.getNombreUbicacion().equals(nombreAntiguo)))) {
                controlador.actualizarUbicacion(ubicacion, nombreAntiguo);
                mensaje = "Ubicación editada y almacenada correctamente";
                mostrarMensaje(mensaje);
                intento = new Intent(this, UbicacionesPpalActivity.class);
                startActivity(intento);
            } else {
                mensaje = "Nombre de la Ubicación no válido, Ubicación ya existe";
                mostrarMensaje(mensaje);}
        }
    }

    public void resetear (View view){
        inicializar();
        cajaNombre.setText("");
        cajaDetalle.setText("");
    }

    public void ayuda(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.estiloCuadrodDialogo))
                .setTitle("AYUDA")
                .setMessage("Introduzca los nuevos valores que quiera dar a esta Ubicación y " +
                            "pulse  el botón azul redondo en parte inferior derecha de la pantalla " +
                            "para aceptar")
                .setPositiveButton("Aceptar", null);
        AlertDialog dialog = builder.create();
        dialog.show();}

    public void mostrarMensaje(String mensaje) {
        toast = Toast.makeText(this, mensaje, Toast.LENGTH_LONG);
        toast.show();
    }
}


