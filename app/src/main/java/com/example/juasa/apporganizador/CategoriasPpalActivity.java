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

public class CategoriasPpalActivity extends AppCompatActivity {

    private Controlador_base_datos controlador;
    private ArrayAdapter<String> adaptador;
    private ListView listaCategorias;
    private String nombreCategoria;
    private Categoria categoria;
    private Intent intento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorias_ppal);
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
        listaCategorias = (ListView) findViewById(R.id.categorias_ppal_lista_categorias);
    }

    public void actualizarUI() {
        if (controlador.numRegistrosTabla(controlador.TABLA_CATEGORIAS) == 0) {
            listaCategorias.setAdapter(null);
            Toast mensaje = Toast.makeText(this, "No existe ninguna Categoría. Por favor, cree alguna", Toast.LENGTH_LONG);
            mensaje.show();
        } else {
            adaptador = new ArrayAdapter(this, R.layout.elementos_lista_cat, R.id.elemento_lista_cat_nombre_elemento, controlador.obtenerCategorias());
            listaCategorias.setAdapter(adaptador);
        }
    }

    public void mostrarElemento(View view) {
        View padre = (View) view.getParent();
        TextView categoriaCajaTexto = (TextView) padre.findViewById(R.id.elemento_lista_cat_nombre_elemento);
        nombreCategoria = categoriaCajaTexto.getText().toString();
        categoria = controlador.obtenerCategoria(nombreCategoria);
        intento = new Intent(this, MostrarCategoriaActivity.class);
        intento.putExtra("id_categoria", categoria.getIdCategoria());
        intento.putExtra("nombre_categoria", categoria.getNombreCategoria());
        intento.putExtra("detalle_categoria", categoria.getDescripcionCategoria());
        startActivity(intento);
    }

    public void crearNuevaCategoria(View view) {
        Intent intento = new Intent(this, CrearCategoriaActivity.class);
        startActivity(intento);
    }

    public void ayuda(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.estiloCuadrodDialogo))
                .setTitle("AYUDA")
                .setMessage("Para CREAR una Categoría, pulse el botón azul redondo situado en la parte inferior " +
                        "derecha de la pantalla. Si SELECCIONA una Categoría ya creada, puede CONSULTAR sus detalles, " +
                        "EDITARLA o ELIMINARLA. También puede REGRESAR al Menú de Gestión de Pertenencias,  " +
                        "y/o IR al Menú Principal de bienvenida")
                .setPositiveButton("Aceptar", null);
        AlertDialog dialog = builder.create();
        dialog.show();}
}
