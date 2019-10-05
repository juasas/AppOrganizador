package com.example.juasa.apporganizador;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.juasa.apporganizador.base_de_datos.Controlador_base_datos;
import com.example.juasa.apporganizador.datos.Datos;

public class MenuPrincipalActivity extends AppCompatActivity {
    private Controlador_base_datos controlador;
    private Datos datos;
    private TextView cajaTitulo;
    private Intent intento;
    private Button botonEntrarPertenencias, botonEntrarMaleta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);
        getSupportActionBar().hide();
        inicializar();

        //if (controlador.numRegistrosTabla(controlador.TABLA_UBICACIONES) == 0){
          //  botonEntrarMaleta.setEnabled(false);
            //botonEntrarPertenencias.setEnabled(false);}

        //if (controlador.numRegistrosTabla(controlador.TABLA_CATEGORIAS) == 0){
          //  botonEntrarMaleta.setEnabled(false);
            //botonEntrarPertenencias.setEnabled(false);}

            //Saludo al usuario

        cajaTitulo.setText("Bienvenido, " + datos.usuario);
    }

    public void inicializar (){
        controlador = new Controlador_base_datos(this);
        cajaTitulo = (TextView) findViewById(R.id.menu_ppal_titulo);
    }

    public void entrarPertenenciasPpal(View view) {
        intento = new Intent(this, MenuPertenenciasGeneralActivity.class);
        startActivity(intento);
    }

    public void entrarUsuarioPpal(View view) {
        intento = new Intent(this, MenuUsuarioGeneralActivity.class);
        startActivity(intento);
    }

    public void cerrarSesion(View view) {
        if (datos.numeroMaleta == 0)
            controlador.actualizarUsuarioMaleta(datos.mail, "F");
        else controlador.actualizarUsuarioMaleta(datos.mail, "T");
            //Escribir en BD si ya existe una maleta

        //Intent intent = new Intent(Intent.ACTION_MAIN);
        //intent.addCategory(Intent.CATEGORY_HOME);

        //System.exit(0);
        //finish();

        startActivity(new Intent(getBaseContext(), LoginActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
        finish();
    }

    public void ayuda(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.estiloCuadrodDialogo))
                .setTitle("AYUDA")
                .setMessage("Puede ACCEDER a la Gestión de sus Ubicaciones, Categorías y/o Pertenencias, ACCEDER a la Gestión de " +
                        "sus Opciones de Usuario o CERRAR Sesión")
                .setPositiveButton("Aceptar", null);
        AlertDialog dialog = builder.create();
        dialog.show();}
}

