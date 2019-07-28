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
    private TextView cajaid, cajaNombreCat, cajaDetalleCat;
    private Bundle extras;
    private String nombreCategoria, detalleCategoria;
    private View view;
    private Intent intento;
    private int idCategoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_categoria);
        controlador = new Controlador_base_datos(this);
        recibirParametros();
        llenarCategoria(view);
    }

    public void recibirParametros() {
        extras = getIntent().getExtras();
        if (extras != null) {
            idCategoria = extras.getInt("id_categoria");
            nombreCategoria = extras.getString("nombre_categoria");
            detalleCategoria = extras.getString("detalle_categoria");}
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
                    .setMessage("¿Qué desea hacer?")
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
                            .setMessage("No se permite borrar una categoría que tiene pertenencias ")
                            .setPositiveButton("Aceptar", null);
                    AlertDialog dialog = builder.create();
                    dialog.show();}
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void llenarCategoria(View view){
        cajaid = (TextView) findViewById(R.id.mostrar_categoria_cajaid);
        cajaNombreCat = (TextView) findViewById(R.id.mostrar_cat_caja_nombre);
        cajaDetalleCat = (TextView) findViewById(R.id.mostrar_cat_caja_detalle);
        cajaNombreCat.setText(nombreCategoria);
        cajaDetalleCat.setText(detalleCategoria);
        cajaid.setText(String.valueOf(idCategoria));
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
                .setMessage("Seleccione uno de los iconos de la barra superior de la pantalla. " +
                        "Puede: REGRESAR al listado de Categorías, IR al MENÚ PRINCIPAL " +
                        "de bienvenida, EDITAR esta Categoría o ELIMINARLA")
                .setPositiveButton("Aceptar", null);
        AlertDialog dialog = builder.create();
        dialog.show();}
}


