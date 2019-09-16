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

    private Controlador_base_datos controlador;
    private Datos datos;
    private Entrada_Salida salida;
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

        String pass, repitePass;

        pass = cajaPass.getText().toString();
        repitePass = cajaRepitePass.getText().toString();

        //Se comprueba si los campos de nuevo usuario están vacios

        if (pass.isEmpty() && (repitePass.isEmpty())) {
            Toast mensaje = Toast.makeText(this, "Los campos PASSWORD y REPITE PASSWORD están vacios", Toast.LENGTH_LONG);
            mensaje.show();
        } else {
            if (pass.isEmpty()) {
                Toast mensaje = Toast.makeText(this, "El campo PASSWORD está vacio", Toast.LENGTH_LONG);
                mensaje.show();
            } else {
                if (repitePass.isEmpty()) {
                    Toast mensaje = Toast.makeText(this, "El campo REPITE PASSWORD está vacio", Toast.LENGTH_LONG);
                    mensaje.show();
                } else {
                    if (pass.equals(repitePass)) {
                        String passEncriptada = Encriptacion.encriptarPass(pass);
                        controlador.actualizarPassword(passEncriptada);
                        Toast mensaje = Toast.makeText(this, "Contraseña cambiada con éxito", Toast.LENGTH_LONG);
                        mensaje.show();
                        intento = new Intent(this, MenuUsuarioGeneralActivity.class);
                        startActivity(intento);
                    } else {
                        Toast mensaje = Toast.makeText(this, "No coinciden las contraseñas introducidas", Toast.LENGTH_LONG);
                        mensaje.show();
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
                .setMessage("Aquí puede cambiar su contraseña de usuario para acceder a la " +
                            "Aplicación. Para ello, introduzca la nueva contraseña, confirmándola " +
                            "después y pulse sobre el botón de ACEPTAR")
                .setPositiveButton("Aceptar", null);
        AlertDialog dialog = builder.create();
        dialog.show();}
}

