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
public class BuscarPedirNombreActivity extends AppCompatActivity {

    private Controlador_base_datos controlador;
    private EditText cajaNombreBuscado;
    private String nombreBuscado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_pedir_nombre);
        controlador = new Controlador_base_datos(this);
        cajaNombreBuscado = findViewById(R.id.buscar_pedir_nombre_caja_nombre);
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
                Intent intento = new Intent(this, PertenenciasPpalActivity.class);
                intento.putExtra("busqueda", "General");
                startActivity(intento);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void buscar (View view){
        nombreBuscado = cajaNombreBuscado.getText().toString().toUpperCase();
        if (nombreBuscado.isEmpty()) {
            Toast mensaje = Toast.makeText(this, "Campo vacio", Toast.LENGTH_LONG);
            mensaje.show();
        } else{
            Intent intento = new Intent(this, PertenenciasPpalActivity.class);
            intento.putExtra("busqueda", "Por nombre");
            intento.putExtra("parametro_buscado", nombreBuscado);
            startActivity(intento);}
    }

    public void ayuda(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.estiloCuadrodDialogo))
                .setTitle("AYUDA")
                .setMessage("Si desea buscar una Pertenencia por Nombre, escriba el nombre deseado. " +
                        "A continuación pulse en BUSCAR")
                .setPositiveButton("Aceptar", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
