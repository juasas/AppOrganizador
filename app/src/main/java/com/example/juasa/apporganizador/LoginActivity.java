package com.example.juasa.apporganizador;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.juasa.apporganizador.base_de_datos.Controlador_base_datos;
import com.example.juasa.apporganizador.datos.Datos;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginActivity extends AppCompatActivity {

    private Controlador_base_datos controlador;
    private EditText cajaId, cajaPass;
    private TextView textoNuevoUsusario;
    private Intent intento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        inicializar();
    }

    public void inicializar() {
        controlador = new Controlador_base_datos(this);
        cajaId = (EditText) findViewById(R.id.login_caja_identificador);
        cajaPass = (EditText) findViewById(R.id.login_caja_pass);
        textoNuevoUsusario = (TextView) findViewById(R.id.login_nuevo_usuario);
        Datos.numeroUsuarios = controlador.numRegistrosTabla(controlador.TABLA_USUARIOS);
    }

    public void crearNuevoUsuario (View view) {
        if (Datos.numeroUsuarios == 1){
            textoNuevoUsusario.setEnabled(false);
            AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.estiloCuadrodDialogo))
                    .setTitle("AVISO")
                    .setMessage("No puede CREAR más de un Usuario. Ya existe un Usuario creado. " +
                                "Para poder CREAR un nuevo Usuario, debe antes borrar el ya " +
                                "existente (diríjase a Gestión de Ususario/Borrar sus Datos)")
                    .setPositiveButton("Aceptar", null);
            AlertDialog dialog = builder.create();
            dialog.show();
        } else {
            intento = new Intent(this, NuevoUsuarioActivity.class);
            startActivity(intento);
        }
    }

    public void acceder(View view) {

        String identificador, pass;

        Usuario usuarioBuscado;
        identificador = cajaId.getText().toString();
        pass = cajaPass.getText().toString();

        //Se comprueba si los campos de nuevo usuario están vacios

        if (identificador.isEmpty() && (pass.isEmpty())) {
            Toast mensaje = Toast.makeText(this, "Los campos MAIL y PASSWORD están vacios", Toast.LENGTH_LONG);
            mensaje.show();
        } else {
            if (identificador.isEmpty()) {
                Toast mensaje = Toast.makeText(this, "El campo MAIL está vacio", Toast.LENGTH_LONG);
                mensaje.show();
                } else {
                    if (pass.isEmpty()) {
                        Toast mensaje = Toast.makeText(this, "El campo PASSWORD está vacio", Toast.LENGTH_LONG);
                        mensaje.show();
                    } else {
                        if (controlador.existe(controlador.TABLA_USUARIOS, "IDENTIFICADOR", identificador)) {
                            usuarioBuscado = controlador.obtenerUsuario(identificador);

                            //Se guarda en la variable global el nombre de usuario

                            Datos.usuario = usuarioBuscado.getNombre();
                            Datos.identificador = identificador;
                            String passEncriptada= Encriptacion.encriptarPass(pass);
                            if (usuarioBuscado.getPassword().equals(passEncriptada)) {
                                if (controlador.existe(controlador.TABLA_USUARIOS, "MALETA", "T"))
                                    Datos.numeroMaleta = 1;
                                intento = new Intent(this, MenuPrincipalActivity.class);
                                startActivity(intento);
                                finish();
                            } else {// No coinciden las credenciales
                                Toast mensaje = Toast.makeText(this, "Las credenciales introducidas no son válidas", Toast.LENGTH_LONG);
                                mensaje.show();
                                borrarCajas(view);
                            }

                        } else {
                            Toast mensaje = Toast.makeText(this, "No existe el usuario en la Base de Datos", Toast.LENGTH_LONG);
                            mensaje.show();
                            borrarCajas(view);
                        }
                    }
                }
            }
    }

    public void borrarCajas(View view){
        cajaId.setText("");
        cajaPass.setText(""); }

    public void ayuda(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.estiloCuadrodDialogo))
                .setTitle("AYUDA")
                .setMessage("Si ya es usuario, introduzca su identificador y su contraseña. " +
                            "Si aún no lo es, seleccione CREAR NUEVO USUARIO para crear uno nuevo")
                .setPositiveButton("Aceptar", null);
        AlertDialog dialog = builder.create();
        dialog.show();}

    public void cerrarApp (View view){
        super.onBackPressed();
        finishAffinity();
        System.exit(0);
    }
}

