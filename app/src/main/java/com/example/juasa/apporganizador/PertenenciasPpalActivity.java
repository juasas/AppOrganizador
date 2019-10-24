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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.juasa.apporganizador.base_de_datos.Controlador_base_datos;
import com.example.juasa.apporganizador.datos.Datos;

public class PertenenciasPpalActivity extends AppCompatActivity {

    private Controlador_base_datos controlador;
    private PertenenciasCursorAdapter pertenenciasCursorAdapter;
    private ListView listaPertenencias;
    private String nombrePertenencia, parametroBuscado, busqueda, ordenado;
    private Pertenencia pertenencia;
    private Intent intento;
    private TextView pertenenciaCajaTexto, titulo;
    private View padre;
    private Bundle extras;
    private FloatingActionButton fab;
    private ImageView iconoAyuda;
    private Cursor cursorCompleto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pertenencias_ppal);
        inicializar();
        recibirParametros();
    }

    public void inicializar(){
        controlador = new Controlador_base_datos(this);
        listaPertenencias = findViewById(R.id.pert_ppal_lista_pertenencias);
        iconoAyuda = findViewById(R.id.pert_ppal_icono_ayuda);
        iconoAyuda.setVisibility(View.INVISIBLE);
        fab = findViewById(R.id.pert_ppal_fab);
        fab.hide();
        titulo = findViewById(R.id.pert_ppal_titulo);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (controlador.numRegistrosTabla(controlador.TABLA_PERTENENCIAS) == 0) {
            getMenuInflater().inflate(R.menu.menu_action_ppal, menu);
        } else {
            if (extras != null) {
                busqueda = extras.getString("busqueda");
                if (busqueda.equals("General")) {
                    getMenuInflater().inflate(R.menu.menu_action_pert_busqueda, menu);
                } else {
                    getMenuInflater().inflate(R.menu.menu_action_secundario, menu);
                }
            }
        }
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
            case R.id.menu_action_pert_busqueda_volver_atras:
                intento = new Intent(this, MenuPertenenciasGeneralActivity.class);
                startActivity(intento);
                break;
            case R.id.menu_action_pert_busqueda_gohome:
                intento = new Intent(this, MenuPrincipalActivity.class);
                startActivity(intento);
                break;
            case R.id.menu_action_pert_busqueda_buscar_cat:
                intento = new Intent(this, BuscarPedirCategoriaActivity.class);
                startActivity(intento);
                break;
            case R.id.menu_action_pert_busqueda_buscar_nombre:
                intento = new Intent(this, BuscarPedirNombreActivity.class);
                startActivity(intento);
                break;
            case R.id.menu_action_pert_busqueda_buscar_ubic:
                intento = new Intent(this, BuscarPedirUbicacionActivity.class);
                startActivity(intento);
                break;
            case R.id.menu_action_pert_listado_alfabetico:
                fab.hide();
                ordenado = "NOMBRE_PERT";
                actualizarUI(ordenado);
                break;
            case R.id.menu_action_secundario_volver_atras:
                intento = new Intent(this, PertenenciasPpalActivity.class);
                intento.putExtra("busqueda", "General");
                intento.putExtra("parametro_buscado", "");
                startActivity(intento);
        }
        return super.onOptionsItemSelected(item);
    }

    public void recibirParametros(){
        extras = getIntent().getExtras();
        if (extras != null) {
            busqueda = extras.getString("busqueda");
            parametroBuscado = extras.getString("parametro_buscado");
            switch (busqueda) {
                case "General":
                    fab.show();
                    iconoAyuda.setVisibility(View.VISIBLE);
                    ordenado=null;
                    actualizarUI(ordenado);
                    break;
                case "Por categoria":
                    parametroBuscado = extras.getString("parametro_buscado");
                    titulo.setText("Búsqueda por Categoría");
                    if (controlador.numRegistrosTabla(controlador.TABLA_PERTENENCIAS) == 0) {
                        listaPertenencias.setAdapter(null);
                        String mensaje = "No existe ninguna Pertenencia, por favor, cree alguna";
                        mostrarMensaje(mensaje);
                    } else {
                        Cursor cursor = controlador.listadoPertenencias(parametroBuscado, "NOMBRE_CATEGORIA");
                        if (cursor.getCount() == 0){
                            String mensaje = "No existe ninguna Pertenencia según la búsqueda por la Categoría seleccionada";
                            mostrarMensaje(mensaje);
                        } else{
                                pertenenciasCursorAdapter = new PertenenciasCursorAdapter(this, controlador.listadoPertenencias(parametroBuscado, "NOMBRE_CATEGORIA"));
                                listaPertenencias.setAdapter(pertenenciasCursorAdapter);
                                iconoAyuda.setVisibility(View.INVISIBLE);
                        }
                    }
                    break;
                case "Por nombre":
                    parametroBuscado = extras.getString("parametro_buscado");
                    titulo.setText("Búsqueda por Nombre");
                    if (controlador.numRegistrosTabla(controlador.TABLA_PERTENENCIAS) == 0) {
                        listaPertenencias.setAdapter(null);
                        String mensaje = "No existe ninguna Pertenencia, por favor, cree alguna";
                        mostrarMensaje(mensaje);
                    } else {
                        Cursor cursor = controlador.listadoPertenencias(parametroBuscado, "NOMBRE_PERT");
                        if (cursor.getCount() == 0) {
                            String mensaje = "No existe ninguna Pertenencia según la búsqueda por el Nombre introducido";
                            mostrarMensaje(mensaje);
                        }else {
                            pertenenciasCursorAdapter = new PertenenciasCursorAdapter(this, controlador.listadoPertenencias(parametroBuscado, "NOMBRE_PERT"));
                            listaPertenencias.setAdapter(pertenenciasCursorAdapter);
                            iconoAyuda.setVisibility(View.INVISIBLE); }
                    }
                    break;
                case "Por ubicación":
                    parametroBuscado = extras.getString("parametro_buscado");
                    titulo.setText("Búsqueda por Ubicación");
                    if (controlador.numRegistrosTabla(controlador.TABLA_PERTENENCIAS) == 0) {
                        listaPertenencias.setAdapter(null);
                        String mensaje = "No existe ninguna Pertenencia, por favor, cree alguna";
                        mostrarMensaje(mensaje);
                    } else {
                        Cursor cursor = controlador.listadoPertenencias(parametroBuscado, "NOMBRE_UBICACION");
                        if (cursor.getCount() == 0) {
                            String mensaje = "No existe ninguna Pertenencia según la búsqueda por la Ubicación seleccionada";
                            mostrarMensaje(mensaje);
                        }else {
                        pertenenciasCursorAdapter = new PertenenciasCursorAdapter(this, controlador.listadoPertenencias(parametroBuscado, "NOMBRE_UBICACION"));
                        listaPertenencias.setAdapter(pertenenciasCursorAdapter);
                        iconoAyuda.setVisibility(View.INVISIBLE); }
                    }
                    break;
            }
        }
    }

    public void actualizarUI(String tipoOrden) {
        if (controlador.numRegistrosTabla(controlador.TABLA_PERTENENCIAS) == 0) {
            listaPertenencias.setAdapter(null);
            Datos.numeroMaleta=0;
            String mensaje = "No existe ninguna Pertenencia. Por favor, cree alguna";
            mostrarMensaje(mensaje);
        } else {
            //Este cursor contiene todos las pertenencias
            cursorCompleto = controlador.obtenerPertenencias(tipoOrden);
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
        Datos.pertenenciaGlobal.setNombrePertenencia("");
        Datos.pertenenciaGlobal.setDetallePertenencia("");
        Datos.pertenenciaGlobal.setNombreUbicacionPertenencia("");
        Datos.pertenenciaGlobal.setNombreUbicacionPertenencia("");
        Datos.pertenenciaGlobal.setFotoPertenencia("");
        Intent intento = new Intent(this, CrearEditarPertenenciaActivity.class);
        intento.putExtra("operacion", "crear");
        startActivity(intento);
    }

    public void ayuda(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.estiloCuadrodDialogo))
                .setTitle("AYUDA")
                .setMessage("Para CREAR una Pertenencia, pulse  el botón azul redondo situado en la parte inferior " +
                        "derecha de la pantalla. Si SELECCIONA una Pertenencia ya creada, puede CONSULTAR sus detalles, " +
                        "EDITARLA o ELIMINARLA. También puede REGRESAR al Menú de Gestión de Pertenencias, " +
                        "IR al Menú Principal de bienvenida o/y REALIZAR una búsqueda entre sus " +
                        "Pertenencias")
                .setPositiveButton("Aceptar", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void mostrarMensaje (String mensaje){
        Toast toast = Toast.makeText(this, mensaje, Toast.LENGTH_LONG);
        toast.show();
    }
}