package com.example.juasa.apporganizador;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.juasa.apporganizador.base_de_datos.Controlador_base_datos;

public class BuscarPedirColorActivity extends AppCompatActivity {

    private Controlador_base_datos controlador;
    private EditText cajaColorBuscado;
    private String colorBuscado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_pedir_color);
        controlador = new Controlador_base_datos(this);
        cajaColorBuscado = findViewById(R.id.buscar_pedir_color_caja_nombre);
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        getMenuInflater().inflate(R.menu.menu_action_secundario, menu);
        return super.onCreateOptionsMenu(menu);}

    @Override
    public boolean onOptionsItemSelected (MenuItem item ) {
        switch (item.getItemId()) {
            case R.id.menu_action_secundario_volver_atras:
                Intent intento = new Intent(this, PertenenciasPpalActivity.class);
                intento.putExtra("busqueda", "General");
                intento.putExtra("parametro_buscado", "");
                startActivity(intento);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void buscar (View view){
        colorBuscado = cajaColorBuscado.getText().toString().toUpperCase();
        if (colorBuscado.isEmpty()) {
            Toast mensaje = Toast.makeText(this, "Campo vacio", Toast.LENGTH_LONG);
            mensaje.show();
        } else{
            Intent intento = new Intent(this, PertenenciasPpalActivity.class);
            intento.putExtra("busqueda", "Por color");
            intento.putExtra("parametro_buscado", colorBuscado);
            startActivity(intento);}
    }
}
