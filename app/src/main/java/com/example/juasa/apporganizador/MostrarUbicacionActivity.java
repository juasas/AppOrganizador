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

public class MostrarUbicacionActivity extends AppCompatActivity {
    private Controlador_base_datos controlador;
    private TextView cajaid, cajaNombre, cajaDescripcion;
    private Bundle extras;
    private String nombreUbicacion, descripcionUbicacion;
    private View view;
    private Intent intento;
    private int idUbicacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_ubicacion);
        controlador = new Controlador_base_datos(this);
        recibirParametros();
        llenarUbicacion(view);
    }

    public void recibirParametros() {
        extras = getIntent().getExtras();
        if (extras != null) {
            idUbicacion=extras.getInt("id_ubicacion");
            nombreUbicacion = extras.getString("nombre_ubicacion");
            descripcionUbicacion = extras.getString("detalle_ubicacion");}
    }
    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.menu_action_gestion, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item ){
        switch (item.getItemId()){
            case R.id.menu_action_gestion_volver_atras:
                intento = new Intent(this, UbicacionesPpalActivity.class);
                startActivity(intento);
                break;
            case R.id.menu_action_gestion_gohome:
                intento = new Intent(this, MenuPrincipalActivity.class);
                startActivity(intento);
                break;
            case R.id.menu_action_gestion_editar_elemento:
                intento = new Intent(this, EditarUbicacionActivity.class);
                intento.putExtra("nombre_ubicacion", nombreUbicacion);
                intento.putExtra("detalle_ubicacion", descripcionUbicacion);
                startActivity(intento);
                break;
            case R.id.menu_action_gestion_eliminar_elemento:
                if (controlador.numAparicionesUbicacionTablaPert(idUbicacion)== 0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.estiloCuadrodDialogo))
                            .setTitle("Confirmar eliminación")
                            .setMessage("¿Qué desea hacer?")
                            .setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {
                                    controlador.borrarUbicacion(nombreUbicacion);
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
                            .setMessage("No se permite borrar una Ubicación que tiene Pertenencias ")
                            .setPositiveButton("Aceptar", null);
                    AlertDialog dialog = builder.create();
                    dialog.show();}
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void llenarUbicacion(View view){
        cajaid = (TextView) findViewById(R.id.mostrar_ubicacion_cajaid);
        cajaNombre = (TextView) findViewById(R.id.mostrar_ubic_caja_nombre);
        cajaDescripcion = (TextView) findViewById(R.id.mostrar_ubic_caja_detalle);
        cajaNombre.setText(nombreUbicacion);
        cajaDescripcion.setText(descripcionUbicacion);
        cajaid.setText(String.valueOf(idUbicacion));
    }

    public void escribir () {
        Toast mensaje = Toast.makeText(this, "Ubicación eliminada correctamente", Toast.LENGTH_LONG);
        mensaje.show();
    }

    public void volver(){
        Intent intento = new Intent(this, UbicacionesPpalActivity.class);
        startActivity(intento);
    }

    public void ayuda(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.estiloCuadrodDialogo))
                .setTitle("AYUDA")
                .setMessage("Seleccione uno de los iconos de la barra superior de la pantalla. " +
                            "Puede: REGRESAR al listado de Ubicaciones, IR al MENÚ PRINCIPAL " +
                            "de bienvenida, EDITAR esta Ubicación o ELIMINARLA")
                .setPositiveButton("Aceptar", null);
        AlertDialog dialog = builder.create();
        dialog.show();}
}
