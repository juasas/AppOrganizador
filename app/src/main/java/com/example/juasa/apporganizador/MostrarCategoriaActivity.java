package com.example.juasa.apporganizador;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.juasa.apporganizador.base_de_datos.Controlador_base_datos;

public class MostrarCategoriaActivity extends AppCompatActivity {
    private Controlador_base_datos controlador;
    private TextView cajaNombreCat, cajaDetalleCat;
    private Bundle extras;
    private String nombreCategoria, detalleCategoria;
    private Intent intento;
    private int idCategoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_categoria);
        inicializar();
        recibirParametros();
        llenarCategoria();
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.menu_action_gestion, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item ){
        switch (item.getItemId()) {
            case R.id.menu_action_gestion_volver_atras:
                intento = new Intent(this, CategoriasPpalActivity.class);
                startActivity(intento);
                break;
            case R.id.menu_action_gestion_gohome:
                intento = new Intent(this, MenuPrincipalActivity.class);
                startActivity(intento);
                break;
            case R.id.menu_action_gestion_editar_elemento:
                intento = new Intent(this, EditarCategoriaActivity.class);
                intento.putExtra("nombre_categoria", nombreCategoria);
                intento.putExtra("detalle_categoria", detalleCategoria);
                startActivity(intento);
                break;
            case R.id.menu_action_gestion_eliminar_elemento:
                if (controlador.numAparicionesCategoriaTablaPert(idCategoria) == 0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.estiloCuadrodDialogo))
                    .setTitle("Confirmar eliminación")
                    .setMessage("¿Qué desea hacer?. Si selecciona Eliminar, esta acción no puede deshacerse")
                    .setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            controlador.borrarCategoria(nombreCategoria);
                            escribir();
                            volver();
                        }
                    })
                    .setNegativeButton("Cancelar", null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.estiloCuadrodDialogo))
                            .setTitle("¡ATENCIÓN!")
                            .setMessage("No se permite borrar una Categoría que contiene Pertenencias")
                            .setPositiveButton("Aceptar", null);
                    AlertDialog dialog = builder.create();
                    dialog.show();}
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void inicializar (){
        controlador = new Controlador_base_datos(this);
        cajaNombreCat = (TextView) findViewById(R.id.mostrar_cat_caja_nombre);
        cajaDetalleCat = (TextView) findViewById(R.id.mostrar_cat_caja_detalle);
    }

    public void recibirParametros() {
        extras = getIntent().getExtras();
        if (extras != null) {
            idCategoria = extras.getInt("id_categoria");
            nombreCategoria = extras.getString("nombre_categoria");
            detalleCategoria = extras.getString("detalle_categoria");}
    }

    public void llenarCategoria(){
        cajaNombreCat.setText(nombreCategoria);
        cajaDetalleCat.setText(detalleCategoria);
    }

    public void escribir () {
        Toast mensaje = Toast.makeText(this, "Categoría eliminada correctamente", Toast.LENGTH_LONG);
        mensaje.show();
    }

    public void volver(){
        Intent intento = new Intent(this, CategoriasPpalActivity.class);
        startActivity(intento);
    }

    public void ayuda(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.estiloCuadrodDialogo))
                .setTitle("AYUDA")
                .setMessage("SELECCIONE uno de los iconos situados en la barra superior de la pantalla. " +
                        "Puede: REGRESAR al listado de Categorías, IR al Menú Principal " +
                        "de bienvenida, EDITAR esta Categoría o ELIMINARLA")
                .setPositiveButton("Aceptar", null);
        AlertDialog dialog = builder.create();
        dialog.show();}
}


