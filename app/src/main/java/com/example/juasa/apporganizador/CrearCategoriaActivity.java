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

public class CrearCategoriaActivity extends AppCompatActivity {

    private Controlador_base_datos controlador;
    private EditText cajaNombreCat, cajaDetalleCat;
    private String nombreCategoria, detalleCategoria, mensaje;
    private Intent intento;
    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_categoria);
        inicializar();
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
                Intent intento = new Intent(this, MenuPertenenciasGeneralActivity.class);
                startActivity(intento);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void inicializar() {
        cajaNombreCat = (EditText) findViewById(R.id.crear_cat_caja_nombre);
        cajaDetalleCat = (EditText) findViewById(R.id.crear_cat_caja_detalle);
        controlador = new Controlador_base_datos(this);
    }

    public void crearNuevaCategoria(View view) {

        nombreCategoria = cajaNombreCat.getText().toString().toUpperCase();
        detalleCategoria = cajaDetalleCat.getText().toString();

        //Se comprueba si el campo obligatorio de nueva categoria está vacío

        if (nombreCategoria.isEmpty()) {
            mensaje = "El campo obligatorio NOMBRE no puede estar vacío";
            mostrarMensaje(mensaje);
        } else {{
            if (!controlador.existe(controlador.TABLA_CATEGORIAS, "NOMBRE_CATEGORIA", nombreCategoria)) {
                controlador.anadirCategoria(nombreCategoria, detalleCategoria);
                mensaje = "Categoría almacenada correctamente";
                mostrarMensaje(mensaje);
                intento = new Intent(this, CategoriasPpalActivity.class);
                startActivity(intento);
            } else {
                mensaje = "No se puede usar ese nombre. La Categoría ya existe";
                mostrarMensaje(mensaje);
                }
        }}
    }

    public void resetear (View view){
        cajaNombreCat.setText("");
        cajaDetalleCat.setText("");
    }

    public void ayuda(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.estiloCuadrodDialogo))
                .setTitle("AYUDA")
                .setMessage("Introduzca los valores que quiera dar a esta Categoría y " +
                        "pulse  el botón azul redondo en parte inferior derecha de la pantalla " +
                        "para aceptar. También puede REGRESAR al listado de Categorías o IR al" +
                        "Menú Principal de bienvenida")
                .setPositiveButton("Aceptar", null);
        AlertDialog dialog = builder.create();
        dialog.show();}

    public void mostrarMensaje (String mensaje){
        toast = Toast.makeText(this, mensaje, Toast.LENGTH_LONG);
        toast.show();
    }
}
