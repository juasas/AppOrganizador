package com.example.juasa.apporganizador;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.juasa.apporganizador.base_de_datos.Controlador_base_datos;
import com.example.juasa.apporganizador.datos.Datos;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class NuevoUsuarioActivity extends AppCompatActivity {
    private Controlador_base_datos controlador;
    private EditText cajaId, cajaNombre, cajaPass, cajaPass2;
    private Toast toast;
    private String mensaje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_usuario);
        controlador = new Controlador_base_datos(this);
        getSupportActionBar().hide();
        inicializar();
    }

    public void inicializar() {
        cajaId = (EditText) findViewById(R.id.nuevo_usuario_caja_mail);
        cajaNombre = (EditText) findViewById(R.id.nuevo_usuario_caja_nombre);
        cajaPass = (EditText) findViewById(R.id.nuevo_usuario_caja_pass);
        cajaPass2 = (EditText) findViewById(R.id.nuevo_usuario_caja_repite_pass);
    }

    public void aceptar(View view) {

        String identificador, nombre, pass, pass2;
        Intent intento;

        identificador = cajaId.getText().toString();
        nombre = cajaNombre.getText().toString().toUpperCase();
        pass = cajaPass.getText().toString();
        pass2 = cajaPass2.getText().toString();

        //Se comprueba si los campos de nuevo usuario están vacios
        if (identificador.isEmpty() && nombre.isEmpty() && pass.isEmpty() && pass2.isEmpty()) {
            mensaje = "Todos los campos están vacios";
            mostrarMensaje(mensaje);
        } else {
            if (identificador.isEmpty()) {
                mensaje = "El campo IDENTIFICADOR está vacio";
                mostrarMensaje(mensaje);
            } else {
                if (nombre.isEmpty()) {
                    mensaje = "El campo NOMBRE está vacio";
                    mostrarMensaje(mensaje);
                } else {
                    if (pass.isEmpty()) {
                        mensaje = "El campo PASSWORD está vacio";
                        mostrarMensaje(mensaje);
                    } else {
                        if (pass2.isEmpty()) {
                            mensaje = "El campo CONFIRMAR PASSWORD está vacio";
                            mostrarMensaje(mensaje);
                        } else {
                            if (!pass.equalsIgnoreCase(pass2)) {
                                mensaje = "Las contraseñas no coinciden";
                                mostrarMensaje(mensaje);
                            } else {
                                if (!controlador.existe(controlador.TABLA_USUARIOS, "IDENTIFICADOR", identificador)) {
                                    String passEncriptada = Encriptacion.encriptarPass(pass);
                                    controlador.anadirUsuario(identificador, nombre, passEncriptada);
                                    mensaje = "Usuario almacenado correctamente";
                                    mostrarMensaje(mensaje);
                                    Datos.numeroUsuarios=1;
                                    intento = new Intent(this, LoginActivity.class);
                                    startActivity(intento);
                                } else {
                                    mensaje = "El Usuario introducido ya existe en la Base de Datos";
                                    mostrarMensaje(mensaje);
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
        cajaId.setText("");
        cajaNombre.setText("");
        cajaPass.setText("");
        cajaPass2.setText("");
    }

    public void volver (View view){
        Intent intento = new Intent(this, LoginActivity.class);
        startActivity(intento);
    }

    public void mostrarMensaje(String mensaje) {
        toast = Toast.makeText(this, mensaje, Toast.LENGTH_LONG);
        toast.show();
    }
}
