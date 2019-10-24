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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.juasa.apporganizador.base_de_datos.Controlador_base_datos;

public class UbicacionesPpalActivity extends AppCompatActivity {

    private Controlador_base_datos controlador;
    private ArrayAdapter<String> adaptador;
    private ListView listaUbicaciones;
    private String nombreUbicacion;
    private Ubicacion ubicacion;
    private Intent intento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubicaciones_ppal);
        inicializar();
        actualizarUI();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_action_ppal, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_action_ppal_volver_atras:
                intento = new Intent(this, MenuPertenenciasGeneralActivity.class);
                startActivity(intento);
                break;
            case R.id.menu_action_ppal_gohome:
                intento = new Intent(this, MenuPrincipalActivity.class);
                startActivity(intento);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void inicializar(){
        controlador = new Controlador_base_datos(this);
        listaUbicaciones = findViewById(R.id.ubic_ppal_lista_ubicaciones);
    }

    public void actualizarUI() {
        if (controlador.numRegistrosTabla(controlador.TABLA_UBICACIONES) == 0) {
            listaUbicaciones.setAdapter(null);
            Toast mensaje = Toast.makeText(this, "No existe ninguna Ubicación. Por favor, cree alguna", Toast.LENGTH_LONG);
            mensaje.show();
        } else {
            adaptador = new ArrayAdapter(this, R.layout.elementos_lista_ubic, R.id.elementos_lista_ubic_nombre_elemento, controlador.obtenerUbicaciones());
            listaUbicaciones.setAdapter(adaptador);
        }
    }

    public void mostrarElemento(View view) {
        View padre = (View) view.getParent();
        TextView ubicacionCajaTexto = (TextView) padre.findViewById(R.id.elementos_lista_ubic_nombre_elemento);
        nombreUbicacion = ubicacionCajaTexto.getText().toString();
        ubicacion = controlador.obtenerUbicacion(nombreUbicacion);
        intento = new Intent(this, MostrarUbicacionActivity.class);
        intento.putExtra("id_ubicacion", ubicacion.getIdUbicacion());
        intento.putExtra("nombre_ubicacion", ubicacion.getNombreUbicacion());
        intento.putExtra("detalle_ubicacion", ubicacion.getDescripcionUbicacion());
        startActivity(intento);
    }

    public void crearNuevaUbicacion(View view) {
        Intent intento = new Intent(this, CrearUbicacionActivity.class);
        startActivity(intento);
    }

    public void ayuda(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.estiloCuadrodDialogo))
                .setTitle("AYUDA")
                .setMessage("Para CREAR una Ubicación, pulse  el botón azul redondo situado en la parte inferior " +
                        "derecha de la pantalla. Si SELECCIONA una Ubicación ya creada, puede CONSULTAR sus detalles, " +
                        "EDITARLA o ELIMINARLA. También puede REGRESAR al Menú de Gestión de Pertenencias,  y/o " +
                        "IR al Menú Principal de bienvenida")
                .setPositiveButton("Aceptar", null);
        AlertDialog dialog = builder.create();
        dialog.show();}
}