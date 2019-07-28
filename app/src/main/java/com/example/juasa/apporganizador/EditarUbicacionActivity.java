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
    private Bundle extras;
    private static String nombreAntiguo;
    private String nombreUbicacion, detalleUbicacion;
    private View view;
    private Ubicacion ubicacion;
    private Intent intento;

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
        llenarUbicacion(view);
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

    public void llenarUbicacion(View view){
        cajaNombre.setText(nombreUbicacion);
        cajaDetalle.setText(detalleUbicacion);
    }

    public void guardar(View view){
        inicializar();
        ubicacion = new Ubicacion();
        ubicacion.setNombreUbicacion(cajaNombre.getText().toString().toUpperCase());
        ubicacion.setDescripcionUbicacion(cajaDetalle.getText().toString());

        if (ubicacion.getNombreUbicacion().isEmpty()) {
            Toast mensaje = Toast.makeText(this, "El campo obligatorio NOMBRE no puede estar vacío", Toast.LENGTH_LONG);
            mensaje.show();
        } else {
            int numApariciones = controlador.numRegistrosIguales(Controlador_base_datos.TABLA_UBICACIONES,"NOMBRE_UBICACION", ubicacion.getNombreUbicacion());
            if ((numApariciones == 0) || ((numApariciones != 0) && (ubicacion.getNombreUbicacion().equals(nombreAntiguo)))) {
                controlador.actualizarUbicacion(ubicacion, nombreAntiguo);
                Toast mensaje = Toast.makeText(this, "Ubicación editada y almacenada correctamente", Toast.LENGTH_LONG);
                mensaje.show();
                intento = new Intent(this, UbicacionesPpalActivity.class);
                startActivity(intento);
            } else {
                Toast mensaje = Toast.makeText(this, "Nombre de la Ubicación no válido, Ubicación ya existe", Toast.LENGTH_LONG);
                mensaje.show();}
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
}


