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

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginActivity extends AppCompatActivity {

    private Controlador_base_datos controlador;
    private Datos datos;
    private Entrada_Salida salida;
    private EditText cajaMail, cajaPass;
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
        cajaMail = (EditText) findViewById(R.id.login_caja_mail);
        cajaPass = (EditText) findViewById(R.id.login_caja_pass);
    }

    public void crearNuevoUsuario (View view){
        intento = new Intent(this, NuevoUsuarioActivity.class);
        startActivity(intento);}

    public void acceder(View view) {

        String mail, pass;

        Usuario usuarioBuscado;
        mail = cajaMail.getText().toString();
        pass = cajaPass.getText().toString();

        //Se comprueba si los campos de nuevo usuario están vacios

        if (mail.isEmpty() && (pass.isEmpty())) {
            Toast mensaje = Toast.makeText(this, "Los campos MAIL y PASSWORD están vacios", Toast.LENGTH_LONG);
            mensaje.show();
        } else {
            if (mail.isEmpty()) {
                Toast mensaje = Toast.makeText(this, "El campo MAIL está vacio", Toast.LENGTH_LONG);
                mensaje.show();
                } else {
                    if (pass.isEmpty()) {
                        Toast mensaje = Toast.makeText(this, "El campo PASSWORD está vacio", Toast.LENGTH_LONG);
                        mensaje.show();
                    } else {
                        if (controlador.existe(controlador.TABLA_USUARIOS, "MAIL", mail)) {
                            usuarioBuscado = controlador.obtenerUsuario(mail);

                            //Se guarda en la variable global el nombre de usuario

                            datos.usuario = usuarioBuscado.getNombre();
                            datos.mail = mail;
                            String passEncriptada= Encriptacion.encriptarPass(pass);
                            if (usuarioBuscado.getPassword().equals(passEncriptada)) {
                                if (controlador.existe(controlador.TABLA_USUARIOS, "MALETA", "T"))
                                    datos.numeroMaleta = 1;
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
        cajaMail.setText("");
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
        intento = new Intent(this, NuevoUsuarioActivity.class);
        startActivity(intento);}

    public String encriptarPass (String password){
        String passEncriptada = "";
        try {
            byte[] passByte = password.getBytes();
            MessageDigest md = MessageDigest.getInstance("SHA");
            md.update(passByte);
            byte[] resumenHash = md.digest();
            passEncriptada = new String(resumenHash);
        }catch (NoSuchAlgorithmException e) {
            Toast mensaje = Toast.makeText(this, "No se puede encriptar password", Toast.LENGTH_LONG);
            mensaje.show();
        }
        return passEncriptada;
    }
}

