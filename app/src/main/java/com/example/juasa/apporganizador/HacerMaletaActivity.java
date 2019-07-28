package com.example.juasa.apporganizador;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.juasa.apporganizador.base_de_datos.Controlador_base_datos;
import com.example.juasa.apporganizador.datos.Datos;

public class HacerMaletaActivity extends AppCompatActivity {
    private Datos datos;
    private Controlador_base_datos controlador;
    private MaletaCursorAdapter maletaCursorAdapter;
    private PertenenciasCursorAdapter pertenenciasCursorAdapter;
    private ListView listaPertenencias;
    private String nombrePertenencia, tipo;
    private Intent intento;
    private TextView pertenenciaCajaTexto;
    private TextView cajaSubtitulo1;
    private FloatingActionButton fab;
    private CheckBox checkBox;
    private View padre;
    private Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hacer_maleta);
        controlador = new Controlador_base_datos(this);
        listaPertenencias = (ListView) findViewById(R.id.hacer_maleta_lista_pertenencias);
        cajaSubtitulo1 = findViewById(R.id.hacer_maleta_subtitulo);
        fab = findViewById(R.id.hacer_maleta_fab);
        recibirParametros();
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
                intento = new Intent(this, MaletaPpalActivity.class);
                startActivity(intento);
                break;
            case R.id.menu_action_ppal_gohome:
                intento = new Intent(this, MenuPrincipalActivity.class);
                startActivity(intento);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void recibirParametros() {
        extras = getIntent().getExtras();
        if (extras != null) {
            tipo = extras.getString("tipo");
            switch (tipo) {
                case "Hacer maleta":
                    actualizarUI();
                    break;
                case "Mostrar maleta":
                    fab.hide();
                    if (controlador.numRegistrosTabla(controlador.TABLA_PERTENENCIAS) == 0) {
                        listaPertenencias.setAdapter(null);
                        Toast mensaje = Toast.makeText(this, "No existe ninguna Ubicación. Por favor, cree una", Toast.LENGTH_LONG);
                        mensaje.show();
                    } else {
                        pertenenciasCursorAdapter = new PertenenciasCursorAdapter(this, controlador.obtenerPertenenciasPorMaleta());
                        listaPertenencias.setAdapter(pertenenciasCursorAdapter);

                    }
                    break;
            }
        }
    }

    public void actualizarUI() {
        if (controlador.numRegistrosTabla(controlador.TABLA_PERTENENCIAS) == 0) {
            listaPertenencias.setAdapter(null);
            Toast mensaje = Toast.makeText(this, "No existe ninguna Pertenencia", Toast.LENGTH_LONG);
            mensaje.show();
        } else {
            maletaCursorAdapter = new MaletaCursorAdapter(this, controlador.obtenerPertenencias());
            listaPertenencias.setAdapter(maletaCursorAdapter);
        }
    }

    public void confirmarHacerMaleta(View view) {
        int count = listaPertenencias.getAdapter().getCount();

        for (int i = 0; i < count; i++) {
            ViewGroup row = (ViewGroup) listaPertenencias.getChildAt(i);
            checkBox = (CheckBox) row.findViewById(R.id.elementos_lista_maleta_check_elemento);

            if (checkBox.isChecked()) {
                padre = (View) checkBox.getParent();
                pertenenciaCajaTexto = (TextView) padre.findViewById(R.id.elementos_lista_maleta_nombre_elemento);
                nombrePertenencia = pertenenciaCajaTexto.getText().toString();
                controlador.actualizarCampoHacerMaleta(nombrePertenencia);
            }
        }
        datos.numeroMaleta = 1;
        Intent intento = new Intent(this, MaletaPpalActivity.class);
        startActivity(intento);
    }
}
