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

public class EditarCategoriaActivity extends AppCompatActivity {
    private Controlador_base_datos controlador;
    private EditText cajaNombre, cajaDetalle;
    private View view;
    private Bundle extras;
    private String nombreCategoria, detalleCategoria, nombreAntiguo;
    private Categoria categoria;
    private Intent intento;
    private Toast toast;

    public void inicializar (){
        controlador = new Controlador_base_datos(this);
        cajaNombre = (EditText) findViewById(R.id.editar_cat_caja_nombre);
        cajaDetalle = (EditText) findViewById(R.id.editar_cat_caja_detalle);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_categoria);
        recibirParametros();
        inicializar();
        llenarCategoria();
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
                Intent intento = new Intent(this, CategoriasPpalActivity.class);
                startActivity(intento);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void recibirParametros() {
        extras = getIntent().getExtras();
        if (extras != null) {
            nombreCategoria = extras.getString("nombre_categoria");
            nombreAntiguo = nombreCategoria;
            detalleCategoria = extras.getString("detalle_categoria");
        }
    }

    public void llenarCategoria(){
        cajaNombre.setText(nombreCategoria);
        cajaDetalle.setText(detalleCategoria);
    }

    public void guardar(View view){
        String mensaje;
        categoria = new Categoria();
        categoria.setNombreCategoria(cajaNombre.getText().toString().toUpperCase());
        categoria.setDescripcionCategoria(cajaDetalle.getText().toString());

        if (categoria.getNombreCategoria().isEmpty()) {
            mensaje = "El campo obligatorio NOMBRE no puede estar vacío";
            mostrarMensaje(mensaje);
        } else {
            int numApariciones = controlador.numRegistrosIguales(Controlador_base_datos.TABLA_CATEGORIAS,"NOMBRE_CATEGORIA", categoria.getNombreCategoria());
            if ((numApariciones == 0) || ((numApariciones != 0) && (categoria.getNombreCategoria().equals(nombreAntiguo)))) {
                controlador.actualizarCategoria(categoria, nombreAntiguo);
                mensaje = "Categoria editada y almacenada correctamente";
                mostrarMensaje(mensaje);
                intento = new Intent(this, CategoriasPpalActivity.class);
                startActivity(intento);
            } else {
                mensaje = "Nombre de la categoria no válido, Categoria ya existe";
                mostrarMensaje(mensaje);}
        }
    }

    public void resetear (View view){
        cajaNombre.setText("");
        cajaDetalle.setText("");
    }

    public void ayuda(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.estiloCuadrodDialogo))
                .setTitle("AYUDA")
                .setMessage("Introduzca los nuevos valores que quiera dar a esta Categoría y " +
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



