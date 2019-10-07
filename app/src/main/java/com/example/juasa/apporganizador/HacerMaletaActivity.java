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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.juasa.apporganizador.base_de_datos.Controlador_base_datos;
import com.example.juasa.apporganizador.datos.Datos;

public class HacerMaletaActivity extends AppCompatActivity {

    private Controlador_base_datos controlador;
    private MaletaCursorAdapter maletaCursorAdapter;
    private ListView listaPertenenciasMaleta;
    private String nombrePertenencia;
    private Intent intento;
    private TextView pertenenciaCajaTexto;
    private View padre;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hacer_maleta);
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

    public void inicializar() {
        controlador = new Controlador_base_datos(this);
        fab = findViewById(R.id.hacer_maleta_fab);
        listaPertenenciasMaleta = findViewById(R.id.hacer_maleta_lista_pertenencias);
    }

    public void actualizarUI() {
        if (controlador.numRegistrosTabla(controlador.TABLA_PERTENENCIAS) == 0) {
            listaPertenenciasMaleta.setAdapter(null);
            Toast mensaje = Toast.makeText(this, "No existe ninguna Pertenencia", Toast.LENGTH_LONG);
            mensaje.show();
        } else {
            maletaCursorAdapter = new MaletaCursorAdapter(this, controlador.obtenerPertenencias(null));
            listaPertenenciasMaleta.setAdapter(maletaCursorAdapter);
        }
    }

    public void confirmarHacerMaleta(View view) {
        int cuantos = listaPertenenciasMaleta.getAdapter().getCount();

        for (int i = 0; i < cuantos; i++) {

            ViewGroup row = (ViewGroup) listaPertenenciasMaleta.getChildAt(i);

            CheckBox checkBox = row.findViewById(R.id.elementos_lista_maleta_check_elemento);

            if (checkBox.isChecked()) {
                padre = (View) checkBox.getParent();
                pertenenciaCajaTexto = (TextView) padre.findViewById(R.id.elementos_lista_maleta_nombre_elemento);
                nombrePertenencia = pertenenciaCajaTexto.getText().toString();
                controlador.actualizarCampoHacerMaleta(nombrePertenencia);
            }
        }

        Datos.numeroMaleta = 1;
        Intent intento = new Intent(this, MaletaPpalActivity.class);
        startActivity(intento);
    }

    public void ayuda(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.estiloCuadrodDialogo))
                .setTitle("AYUDA")
                .setMessage("Para HACER la Maleta, seleccione de la lista de Pertenencias las " +
                        "Pertenencias ue quiera incluir en su Maleta. A continuación, pulse el " +
                        "botón azul redondo situado en la parte inferior derecha de la pantalla. " +
                        "También puede REGRESAR al Menú de Maleta y/o IR al Menú Principal de bienvenida")
                .setPositiveButton("Aceptar", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
