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

public class CrearUbicacionActivity extends AppCompatActivity {
    private Controlador_base_datos controlador;
    private EditText cajaNombreUbic, cajaDetalleUbic;
    private String nombreUbicacion, detalleUbicacion, mensaje;
    private Intent intento;
    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_ubicacion);
        inicializar();
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
                intento = new Intent(this, UbicacionesPpalActivity.class);
                startActivity(intento);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void inicializar() {
        cajaNombreUbic = (EditText) findViewById(R.id.crear_ubic_caja_nombre);
        cajaDetalleUbic = (EditText) findViewById(R.id.crear_ubic_caja_detalle);
        controlador = new Controlador_base_datos(this);
    }

    public void crearNuevaUbicacion(View view) {

        nombreUbicacion = cajaNombreUbic.getText().toString().toUpperCase();
        detalleUbicacion = cajaDetalleUbic.getText().toString();

        //Se comprueba si los campos de nueva ubicación están vacios

        if (nombreUbicacion.isEmpty()) {
            mensaje = "El campo obligatorio NOMBRE no puede estar vacío";
            mostrarMensaje(mensaje);
        } else {
            {
                if (!controlador.existe(Controlador_base_datos.TABLA_UBICACIONES, "NOMBRE_UBICACION", nombreUbicacion)) {
                    controlador.anadirUbicacion(nombreUbicacion, detalleUbicacion);
                    mensaje = "Ubicación almacenada correctamente";
                    mostrarMensaje(mensaje);
                    intento = new Intent(this, UbicacionesPpalActivity.class);
                    startActivity(intento);
                } else {
                    mensaje = "No se puede usar ese nombre. La Ubicación ya existe";
                    mostrarMensaje(mensaje);
                }
            }
        }
    }

    public void resetear(View view) {
        cajaNombreUbic.setText("");
        cajaDetalleUbic.setText("");
    }

    public void ayuda(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.estiloCuadrodDialogo))
                .setTitle("AYUDA")
                .setMessage("Introduzca los valores que quiera dar a esta Ubicación y " +
                        "pulse  el botón azul redondo en parte inferior derecha de la pantalla " +
                        "para aceptar")
                .setPositiveButton("Aceptar", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void mostrarMensaje(String mensaje) {
        toast = Toast.makeText(this, mensaje, Toast.LENGTH_LONG);
        toast.show();
    }
}
