package com.example.juasa.apporganizador;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.juasa.apporganizador.base_de_datos.Controlador_base_datos;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class NuevoUsuarioActivity extends AppCompatActivity {
    private Controlador_base_datos controlador;
    private Entrada_Salida salida;
    private EditText cajaMail, cajaNombre, cajaPass, cajaPass2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_usuario);
        controlador = new Controlador_base_datos(this);
        getSupportActionBar().hide();
        inicializar();
    }

    public void inicializar() {
        cajaMail = (EditText) findViewById(R.id.nuevo_usuario_caja_mail);
        cajaNombre = (EditText) findViewById(R.id.nuevo_usuario_caja_nombre);
        cajaPass = (EditText) findViewById(R.id.nuevo_usuario_caja_pass);
        cajaPass2 = (EditText) findViewById(R.id.nuevo_usuario_caja_repite_pass);
    }

    public void aceptar(View view) {

        String mail, nombre, pass, pass2;
        Intent intento;

        mail = cajaMail.getText().toString();
        nombre = cajaNombre.getText().toString().toUpperCase();
        pass = cajaPass.getText().toString();
        pass2 = cajaPass2.getText().toString();

        //Se comprueba si los campos de nuevo usuario están vacios
        if (mail.isEmpty() && nombre.isEmpty() && pass.isEmpty() && pass2.isEmpty()) {
            Toast mensaje = Toast.makeText(this, "Todos los campos están vacios", Toast.LENGTH_LONG);
            mensaje.show();
        } else {
            if (mail.isEmpty()) {
                    Toast mensaje = Toast.makeText(this, "El campo IDENTIFICADOR está vacio", Toast.LENGTH_LONG);
                    mensaje.show();
            } else {
                if (nombre.isEmpty()) {
                        Toast mensaje = Toast.makeText(this, "El campo NOMBRE está vacio", Toast.LENGTH_LONG);
                        mensaje.show();
                } else {
                    if (pass.isEmpty()) {
                        Toast mensaje = Toast.makeText(this, "El campo PASSWORD está vacio", Toast.LENGTH_LONG);
                        mensaje.show();
                    } else {
                        if (pass2.isEmpty()) {
                            Toast mensaje = Toast.makeText(this, "El campo CONFIRMAR PASSWORD está vacio", Toast.LENGTH_LONG);
                            mensaje.show();
                        } else {
                            if (!pass.equalsIgnoreCase(pass2)) {
                                Toast mensaje = Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_LONG);
                                mensaje.show();
                            } else {
                                if (!controlador.existe(controlador.TABLA_USUARIOS, "MAIL", mail)) {
                                    String passEncriptada= Encriptacion.encriptarPass(pass);
                                    controlador.anadirUsuario(mail, nombre, passEncriptada);
                                    Toast mensaje = Toast.makeText(this, "Usuario almacenado correctamente", Toast.LENGTH_LONG);
                                    mensaje.show();
                                    intento = new Intent(this, LoginActivity.class);
                                    startActivity(intento);
                                } else {
                                    Toast mensaje = Toast.makeText(this, "El Usuario introducido ya existe en la Base de Datos", Toast.LENGTH_LONG);
                                    mensaje.show();
                                    resetear(view);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void resetear (View view){
        cajaMail.setText("");
        cajaNombre.setText("");
        cajaPass.setText("");
        cajaPass2.setText("");
    }

    public void volver (View view){
        Intent intento = new Intent(this, LoginActivity.class);
        startActivity(intento);
    }
}
