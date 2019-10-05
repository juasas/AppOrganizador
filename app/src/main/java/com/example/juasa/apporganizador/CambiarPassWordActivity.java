package com.example.juasa.apporganizador;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.juasa.apporganizador.base_de_datos.Controlador_base_datos;
import com.example.juasa.apporganizador.datos.Datos;

public class CambiarPassWordActivity extends AppCompatActivity {

    private Controlador_base_datos controlador;;
    private EditText cajaPass, cajaRepitePass;
    private Intent intento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambiar_pass_word);
        getSupportActionBar().hide();
        inicializar();
    }

    public void inicializar() {
        controlador = new Controlador_base_datos(this);
        cajaPass = (EditText) findViewById(R.id.cambiar_pass_caja_pass);
        cajaRepitePass = (EditText) findViewById(R.id.cambiar_pass_caja_repite_pass);
    }

    public void aceptar(View view) {

        String pass, repitePass, mensaje;

        pass = cajaPass.getText().toString();
        repitePass = cajaRepitePass.getText().toString();

        //Se comprueba si los campos de nuevo usuario están vacios

        if (pass.isEmpty() && (repitePass.isEmpty())) {
            mensaje = "Los campos PASSWORD y REPITE PASSWORD están vacios";
            mostrar(mensaje);
        } else {
            if (pass.isEmpty()) {
                mensaje = "El campo PASSWORD está vacio";
                mostrar(mensaje);
            } else {
                if (repitePass.isEmpty()) {
                    mensaje = "El campo REPITE PASSWORD está vacio";
                    mostrar(mensaje);
                } else {
                    if (pass.equals(repitePass)) {
                        String passEncriptada = Encriptacion.encriptarPass(pass);
                        controlador.actualizarPassword(passEncriptada);
                        mensaje = "Contraseña cambiada con éxito";
                        mostrar(mensaje);
                        intento = new Intent(this, MenuUsuarioGeneralActivity.class);
                        startActivity(intento);
                    } else {
                        mensaje = "No coinciden las contraseñas introducidas";
                        mostrar(mensaje);
                        resetear(view);
                    }
                }
            }
        }
    }

    public void resetear(View view){
        cajaPass.setText("");
        cajaRepitePass.setText("");
    }

    public void volver (View view){
        Intent intento = new Intent(this, MenuUsuarioGeneralActivity.class);
        startActivity(intento);
    }

    public void ayuda(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.estiloCuadrodDialogo))
                .setTitle("AYUDA")
                .setMessage("Puede CAMBIAR su Contraseña de Usuario para acceder a la " +
                            "Aplicación. Para ello, INTRODUZCA la Nueva Contraseña, CONFIRMÁNDOLA " +
                            "después y PULSE sobre el botón de Aceptar. Puede CANCELAR el proceso, " +
                            "si PULSA el botón VOLVER")
                .setPositiveButton("Aceptar", null);
        AlertDialog dialog = builder.create();
        dialog.show();}

    public void mostrar (String mensaje){
        Toast toast = Toast.makeText(this, mensaje, Toast.LENGTH_LONG);
        toast.show();
    }
}

