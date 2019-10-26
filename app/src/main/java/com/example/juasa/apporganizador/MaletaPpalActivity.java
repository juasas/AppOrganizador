package com.example.juasa.apporganizador;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.juasa.apporganizador.base_de_datos.Controlador_base_datos;
import com.example.juasa.apporganizador.datos.Datos;

public class MaletaPpalActivity extends AppCompatActivity {
    private Intent intento;
    private Controlador_base_datos controlador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maleta_ppal);
        controlador = new Controlador_base_datos(this);
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

    public void entrarHacerMaleta (View view) {
        if (Datos.numeroMaleta == 1) {
            escribirCuadrodialogo("Ya tiene una maleta creada");
        } else {
            intento = new Intent(this, HacerMaletaActivity.class);
            startActivity(intento);
        }
    }

    public void deshacerMaleta (View view){
        if (Datos.numeroMaleta == 0){
            escribirCuadrodialogo("No tiene la Maleta hecha. Por favor hágala");
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.estiloCuadrodDialogo))
                    .setTitle("Confirmar deshacer Maleta")
                    .setMessage("¿Qué desea hacer?. Si selecciona Eliminar, esta acción no puede deshacerse")
                    .setPositiveButton("Deshacer", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            controlador.actualizarCampoDeshacerMaleta();
                            Datos.numeroMaleta = 0;
                            escribirToast();
                        }
                    })
                    .setNegativeButton("Cancelar", null);
            AlertDialog dialog = builder.create();
            dialog.show();}
    }

    public void verMaleta (View view){
        if (Datos.numeroMaleta == 0) {
            escribirCuadrodialogo("No tiene la Maleta hecha. Por favor hágala primero");
        } else {
            Cursor cursor = controlador.obtenerPertenenciasPorMaleta();
            if (cursor.getCount() == 0) {
                escribirCuadrodialogo("Se han borrado todas las Pertenencias de la Maleta "+
                        "pero no se ha deshecho la Maleta. Para poder seguir usando esta funcionalidad " +
                        "se tiene que proceder a deshacer. Pulse ACEPTAR para continuar");
                Datos.numeroMaleta = 0;
            } else {
                intento = new Intent(this, VerMaletaActivity.class);
                startActivity(intento);
            }
        }
    }

    public void escribirToast () {
        Toast mensaje = Toast.makeText(this, "Maleta deshecha correctamente", Toast.LENGTH_LONG);
        mensaje.show();
    }

    public void escribirCuadrodialogo(String mensaje) {
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.estiloCuadrodDialogo))
                .setTitle("AVISO")
                .setMessage(mensaje)
                .setPositiveButton("Aceptar", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void ayuda(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.estiloCuadrodDialogo))
                .setTitle("AYUDA")
                .setMessage ("HAGA primero la Maleta. Una vez hecha, podrá VER su contenido o " +
                             "DESHACERLA. No se permite DESHACER la Maleta si aún no está creada." +
                             "Ni tanpoco ver su contenido si aún no ha sido hecha")
                .setPositiveButton("Aceptar", null);
        AlertDialog dialog = builder.create();
        dialog.show();}
}
