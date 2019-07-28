package com.example.juasa.apporganizador;

import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.juasa.apporganizador.base_de_datos.Controlador_base_datos;
import com.example.juasa.apporganizador.datos.Datos;

public class PertenenciasPpalActivity extends AppCompatActivity {

    private Controlador_base_datos controlador;
    private PertenenciasCursorAdapter pertenenciasCursorAdapter;
    private MaletaCursorAdapter maletaCursorAdapter;
    private ListView listaPertenencias;
    private String nombrePertenencia, parametroBuscado, busqueda;
    private Pertenencia pertenencia;
    private Intent intento;
    private TextView pertenenciaCajaTexto;
    private View padre;
    private Bundle extras;
    private FloatingActionButton fab;
    private Cursor cursorCompleto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pertenencias_ppal);
        controlador = new Controlador_base_datos(this);
        listaPertenencias = findViewById(R.id.pert_ppal_lista_pertenencias);
        fab = findViewById(R.id.pert_ppal_fab);
        fab.hide();
        recibirParametros();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_action_pert_busqueda, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_action_pert_busqueda_volver_atras:
                intento = new Intent(this, MenuPertenenciasGeneralActivity.class);
                startActivity(intento);
                break;
            case R.id.menu_action_pert_busqueda_gohome:
                intento = new Intent(this, MenuPrincipalActivity.class);
                startActivity(intento);
                break;
            case R.id.menu_action_pert_busqueda_buscar_cat:
                Toast mensaje = Toast.makeText(this, "Funcionalidad disponible proximámente.", Toast.LENGTH_LONG);
                mensaje.show();
                //Toast mensaje = Toast.makeText(this, "Funcionalidad disponible proximámente.", Toast.LENGTH_LONG);
                //mensaje.show();
                break;
            case R.id.menu_action_pert_busqueda_buscar_nombre:
                Toast mensaje1 = Toast.makeText(this, "Funcionalidad disponible proximámente.", Toast.LENGTH_LONG);
                mensaje1.show();
                //intento = new Intent(this, BuscarPedirNombreActivity.class);
                //startActivity(intento);
                break;
            case R.id.menu_action_pert_busqueda_buscar_color:
                Toast mensaje2 = Toast.makeText(this, "Funcionalidad disponible proximámente.", Toast.LENGTH_LONG);
                mensaje2.show();
                //intento = new Intent(this, BuscarPedirColorActivity.class);
                //startActivity(intento);
                break;
            case R.id.menu_action_pert_busqueda_buscar_ubic:
                Toast mensaje3 = Toast.makeText(this, "Funcionalidad disponible proximámente.", Toast.LENGTH_LONG);
                mensaje3.show();
                //intento = new Intent(this, BuscarPedirUbicacionActivity.class);
                //startActivity(intento);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void recibirParametros() {
        extras = getIntent().getExtras();
        if (extras != null) {
            busqueda = extras.getString("busqueda");
            parametroBuscado = extras.getString("parametro_buscado");
            switch (busqueda) {
                case "General":
                    fab.show();
                    actualizarUI();
                    break;
                case "Por categoria":
                    parametroBuscado = extras.getString("parametro_buscado");
                    if (controlador.numRegistrosTabla(controlador.TABLA_PERTENENCIAS) == 0) {
                        listaPertenencias.setAdapter(null);
                        Toast mensaje = Toast.makeText(this, "No existe ninguna ubicación. Por favor, cree una", Toast.LENGTH_LONG);
                        mensaje.show();
                    } else {
                        pertenenciasCursorAdapter = new PertenenciasCursorAdapter(this, controlador.obtenerPertenenciasPorCat(parametroBuscado));
                        listaPertenencias.setAdapter(pertenenciasCursorAdapter);
                    }
                    break;
                case "Por nombre":
                    parametroBuscado = extras.getString("parametro_buscado");
                    if (controlador.numRegistrosTabla(controlador.TABLA_PERTENENCIAS) == 0) {
                        listaPertenencias.setAdapter(null);
                        Toast mensaje = Toast.makeText(this, "No existe ninguna ubicación. Por favor, cree una", Toast.LENGTH_LONG);
                        mensaje.show();
                    } else {
                        pertenenciasCursorAdapter = new PertenenciasCursorAdapter(this, controlador.obtenerPertenenciasPorNombre(parametroBuscado));
                        listaPertenencias.setAdapter(pertenenciasCursorAdapter);
                    }
                    break;
                case "Por color":
                    parametroBuscado = extras.getString("parametro_buscado");
                    if (controlador.numRegistrosTabla(controlador.TABLA_PERTENENCIAS) == 0) {
                        listaPertenencias.setAdapter(null);
                        Toast mensaje = Toast.makeText(this, "No existe ninguna ubicación. Por favor, cree una", Toast.LENGTH_LONG);
                        mensaje.show();
                    } else {
                        pertenenciasCursorAdapter = new PertenenciasCursorAdapter(this, controlador.obtenerPertenenciasPorColor(parametroBuscado));
                        listaPertenencias.setAdapter(pertenenciasCursorAdapter);
                    }
                    break;
                case "Por ubicación":
                    parametroBuscado = extras.getString("parametro_buscado");
                    if (controlador.numRegistrosTabla(controlador.TABLA_PERTENENCIAS) == 0) {
                        listaPertenencias.setAdapter(null);
                        Toast mensaje = Toast.makeText(this, "No existe ninguna ubicación. Por favor, cree una", Toast.LENGTH_LONG);
                        mensaje.show();
                    } else {
                        pertenenciasCursorAdapter = new PertenenciasCursorAdapter(this, controlador.obtenerPertenenciasPorUbic(parametroBuscado));
                        listaPertenencias.setAdapter(pertenenciasCursorAdapter);
                    }
                    break;
            }
        }
    }

    public void actualizarUI() {
        if (controlador.numRegistrosTabla(controlador.TABLA_PERTENENCIAS) == 0) {
            listaPertenencias.setAdapter(null);
            Datos.numeroMaleta=0;
            Toast mensaje = Toast.makeText(this, "No existe ninguna pertenencia. Por favor, cree una", Toast.LENGTH_LONG);
            mensaje.show();
        } else {
            //Este cursor contiene todos las pertenencias
            cursorCompleto = controlador.obtenerCabecerasPedidos();
            pertenenciasCursorAdapter = new PertenenciasCursorAdapter(this, cursorCompleto);
            listaPertenencias.setAdapter(pertenenciasCursorAdapter);
        }
    }

    public void mostrarElemento(View view) {
        //Se obtiene el padre de la ubicación mostrada
        padre = (View) view.getParent();
        pertenenciaCajaTexto = (TextView) padre.findViewById(R.id.elementos_lista_nombre_elemento);
        nombrePertenencia = pertenenciaCajaTexto.getText().toString();
        pertenencia = controlador.obtenerPertenenciaPorNombre(nombrePertenencia);
        if (pertenencia != null) {
            intento = new Intent(this, MostrarPertenenciaActivity.class);
            intento.putExtra("id_pert", pertenencia.getIdPertenencia());
            intento.putExtra("nombre_pert", pertenencia.getNombrePertenencia());
            intento.putExtra("detalle_pert", pertenencia.getDetallePertenencia());
            intento.putExtra("categoria_pert", pertenencia.getNombreCategoriaPertenencia());
            intento.putExtra("ubicacion_pert", pertenencia.getNombreUbicacionPertenencia());
            intento.putExtra("foto_pert", pertenencia.getFotoPertenencia());
            startActivity(intento);
        }
    }

    public void crearNuevaPertenencia(View view) {
        Intent intento = new Intent(this, CrearPertenenciaActivity.class);
        startActivity(intento);
    }

    public void ayuda(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.estiloCuadrodDialogo))
                .setTitle("AYUDA")
                .setMessage("Para CREAR una Pertenencia, pulse  el botón azul redondo en parte " +
                            "inferior derecha de la pantalla. Si SELECCIONA una Pertenencia ya " +
                            "creada, puede VER sus detalles, EDITARLA o ELIMINARLA.")
                .setPositiveButton("Aceptar", null);
        AlertDialog dialog = builder.create();
        dialog.show();}
}