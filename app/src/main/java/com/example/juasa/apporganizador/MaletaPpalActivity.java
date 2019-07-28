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
import android.widget.Button;
import android.widget.Toast;

import com.example.juasa.apporganizador.base_de_datos.Controlador_base_datos;
import com.example.juasa.apporganizador.datos.Datos;

public class MaletaPpalActivity extends AppCompatActivity {
    private Intent intento;
    private Controlador_base_datos controlador;
    private Datos datos;
    private Button botonHacerMaleta, botonDeshacerMaleta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maleta_ppal);
        controlador = new Controlador_base_datos(this);
        botonHacerMaleta = (Button) findViewById(R.id.maleta_ppal_boton_entrar_hacer_maleta);
        botonDeshacerMaleta = (Button) findViewById(R.id.maleta_ppal_boton_deshacer_maleta);
        //actualizarBotonesMaleta();
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

    public void actualizarBotonesMaleta(){
        if (datos.numeroMaleta == 0) {
            botonHacerMaleta.setEnabled(true);
            botonDeshacerMaleta.setEnabled(false);
        } else {//ya hay una maleta hecha
            botonHacerMaleta.setEnabled(false);
            botonDeshacerMaleta.setEnabled(true);}
    }

    public void entrarHacerMaleta (View view) {
        //AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.estiloCuadrodDialogo))
        //      .setTitle("AVISO")
        //    .setMessage("Gracias por su interés, funcionalidad disponible próximamente.")
        //  .setPositiveButton("Aceptar", null);
        //AlertDialog dialog = builder.create();
        //dialog.show();
        if (datos.numeroMaleta == 1) {
            escribirCuadrodialoogo("Ya tiene una maleta creada");
        } else {
            intento = new Intent(this, HacerMaletaActivity.class);
            intento.putExtra("tipo", "Hacer maleta");
            startActivity(intento);
        }
    }

    public void deshacerMaleta (View view){
        if (datos.numeroMaleta == 0){
            escribirCuadrodialoogo("No tiene la Maleta hecha. Por favor hágala");
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.estiloCuadrodDialogo))
                    .setTitle("Confirmar deshacer Maleta")
                    .setMessage("¿Qué desea hacer?")
                    .setPositiveButton("Deshacer", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            controlador.actualizarCampoDeshacerMaleta();
                            datos.numeroMaleta = 0;
                            escribirToast();
                        }
                    })
                    .setNegativeButton("Cancelar", null);
            AlertDialog dialog = builder.create();
            dialog.show();}

        //actualizarBotonesMaleta();
    }

    public void verMaleta (View view){
        //AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.estiloCuadrodDialogo))
          //      .setTitle("AVISO")
            //    .setMessage("Gracias por su interés, maleta vacía, funcionalidad disponible próximamente.")
              //  .setPositiveButton("Aceptar", null);
        //AlertDialog dialog = builder.create();
        //dialog.show();
        if (datos.numeroMaleta == 0) {
            escribirCuadrodialoogo("No tiene la Maleta hecha. Por favor hágala primero");
        } else {
            intento = new Intent(this, HacerMaletaActivity.class);
            intento.putExtra("tipo", "Mostrar maleta");
            startActivity(intento);}
    }

    public void escribirToast () {
        Toast mensaje = Toast.makeText(this, "Maleta deshecha correctamente", Toast.LENGTH_LONG);
        mensaje.show();
    }

    public void escribirCuadrodialoogo(String mensaje) {
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
