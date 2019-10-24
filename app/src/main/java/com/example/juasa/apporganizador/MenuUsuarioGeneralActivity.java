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
import android.widget.TextView;
import android.widget.Toast;

import com.example.juasa.apporganizador.base_de_datos.Controlador_base_datos;
import com.example.juasa.apporganizador.datos.Datos;

public class MenuUsuarioGeneralActivity extends AppCompatActivity {
    private Controlador_base_datos controlador;
    private Intent intento;
    private String mensaje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_usuario_general);
        inicializar();
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
                Intent intento = new Intent(this, MenuPrincipalActivity.class);
                startActivity(intento);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    public void inicializar (){
        controlador = new Controlador_base_datos(this);
    }

    public void entrarEstadisticas(View view) {
        intento = new Intent(this, MostrarEstadisticaActivity.class);
        startActivity(intento);
    }

    public void entrarCambiarPass(View view) {
        intento = new Intent(this, CambiarPassWordActivity.class);
        startActivity(intento);
    }

    public void entrarBorrarUsuario(View view) {
        mensaje = "Todos los datos de usuario eliminados correctamente";
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.estiloCuadrodDialogo))
                .setTitle("Confirmar eliminación")
                .setMessage("Se va a eliminar todos los datos del Usuario actual (Usuario, " +
                        "Ubicaciones, Categorías, Pertenencias y Maleta). Esta acción no podrá " +
                        "deshacerse")
                .setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        controlador.borrarTabla(controlador.TABLA_USUARIOS);
                        controlador.borrarTabla(controlador.TABLA_UBICACIONES);
                        controlador.borrarTabla(controlador.TABLA_CATEGORIAS);
                        controlador.borrarTabla(controlador.TABLA_PERTENENCIAS);
                        Datos.numeroUsuarios = 0;
                        mostrarMensaje(mensaje);
                        startActivity(new Intent(getBaseContext(), LoginActivity.class)
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
                        finish();
                    }
                })
                .setNegativeButton("Cancelar", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void entrarBorrarTablasUsuario(View view) {
        mensaje = "Todos las pertenencias de usuario eliminadas correctamente";
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.estiloCuadrodDialogo))
                .setTitle("Confirmar eliminación")
                .setMessage("Se va a eliminar todas sus Pertenencias (Ubicaciones, Categorias, " +
                            "Pertenencias y Maleta. Esta acción no podrá deshacerse")
                .setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        controlador.borrarTabla(controlador.TABLA_UBICACIONES);
                        controlador.borrarTabla(controlador.TABLA_CATEGORIAS);
                        controlador.borrarTabla(controlador.TABLA_PERTENENCIAS);
                        Datos.numeroUsuarios = 0;
                        mostrarMensaje(mensaje);
                    }
                })
                .setNegativeButton("Cancelar", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void ayuda(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.estiloCuadrodDialogo))
                .setTitle("AYUDA")
                .setMessage("Puede ACCEDER a sus Estadísticas, CAMBIAR su Contraseña de acceso, " +
                            "BORRAR todos sus datos de Usuario (datos de Usuario, Ubicaciones, " +
                            "Categorías y Pertenencias) o " +
                            "BORRAR todas sus Pertenecias (Ubicaciones, Categorías y Pertenencias")
                .setPositiveButton("Aceptar", null);
        AlertDialog dialog = builder.create();
        dialog.show();}

    public void mostrarMensaje (String mensaje) {
        Toast toast = Toast.makeText(this, mensaje, Toast.LENGTH_LONG);
        toast.show();
        }
}