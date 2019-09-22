package com.example.juasa.apporganizador;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.juasa.apporganizador.datos.Datos;

import java.io.File;

public class FotografiaPpalActivity extends AppCompatActivity {

    private Intent intentoCamara, intento;
    private static int TOMAR_FOTO = 1;
    private static int SELECCIONAR_FOTO=2;
    private static final int PERMISSION_REQUEST_CAMERA = 0;
    private static final int PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE = 1;
    private RadioButton rb_camara, rb_galeria;
    private ImageView imagen;
    private final static String [] PERMISOS = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA};
    private Toast toast;
    private String mensaje;
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fotografia_ppal);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        inicializar();
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
                intento = new Intent(this, CrearPertenenciaActivity.class);
                startActivity(intento);
                break;
            case R.id.menu_action_ppal_gohome:
                intento = new Intent(this, MenuPrincipalActivity.class);
                startActivity(intento);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void inicializar(){
        rb_camara = findViewById(R.id.fotografia_ppal_camara);
        rb_galeria = findViewById(R.id.fotografia_ppal_galeria);
        imagen=findViewById(R.id.fotoimagen);
    }

    public void mostrar (String mensaje){
        toast= Toast.makeText(this, mensaje, Toast.LENGTH_LONG);
        toast.show();
    }

    public void comprobarPermisoEscritura(View view) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {// Marshmallow+
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                // Permission is not granted
                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    // Show an explanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE);
                } else {
                    // No explanation needed; request the permission
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE);

                    // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                    // app-defined int constant. The callback method gets the
                    // result of the request.
                }
            } else {
                // Permission has already been granted
                mensaje = "Ya se posee el permiso de escritura";
                mostrar(mensaje);
                comprobarPermisoCamara();
            }

        } else {
            mensaje = "Versión API que no requiere este permiso de escritura";
            mostrar(mensaje);
            comprobarPermisoCamara();}//no hace falta solicitar permisos
    }

    public void comprobarPermisoCamara() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {// Marshmallow+
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                // Permission is not granted
                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.CAMERA)) {
                    // Show an explanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.CAMERA},
                            PERMISSION_REQUEST_CAMERA);
                } else {
                    // No explanation needed; request the permission
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.CAMERA},
                            PERMISSION_REQUEST_CAMERA);

                    // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                    // app-defined int constant. The callback method gets the
                    // result of the request.
                }
            } else {
                // Permission has already been granted
                mensaje = "Ya se posee el permiso de CÁMARA";
                mostrar(mensaje);
                continuar();
            }
        } else {
            mensaje = "Versión API que no requiere este permiso de CÁMARA";
            mostrar(mensaje);
            continuar();}//no hace falta solicitar permisos}
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CAMERA : {
                // Si la petición es cancelada, el array resultante estará vacío.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // El permiso ha sido concedido.
                    mensaje = "No ha concedido Permiso para acceder a la CÁMARA. Debe concederlo";
                    mostrar(mensaje);
                    continuar();
                } else {
                    // Permiso denegado, deshabilita la funcionalidad que depende de este permiso.
                    mensaje = "Permiso concedido";
                    mostrar(mensaje);

                }
            }break;
            // otros bloques de 'case' para controlar otros permisos de la aplicación
            case PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE : {
                // Si la petición es cancelada, el array resultante estará vacío.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mensaje = "Permiso concedido";
                    mostrar(mensaje);
                    // El permiso ha sido concedido.
                    comprobarPermisoCamara();
                } else {
                    mensaje ="Permiso denegado";
                    mostrar(mensaje);
                    // Permiso denegado, deshabilita la funcionalidad que depende de este permiso.
                }
            }break;
        }

        return;
    }

    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    public void continuar () {
        //Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //startActivityForResult(i,0);
        //ocultar();
        final EditText cajaTexto = new EditText(this);
        AlertDialog builder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.estiloCuadrodDialogo))
                .setTitle("Nombre de la foto")
                .setMessage("Introduzca un nombre de la foto sin extensión")
                .setView(cajaTexto)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        Datos.nombreFoto = cajaTexto.getText().toString() + ".jpg";
                        File carpeta = new File(getExternalFilesDir(null), Datos.nombreFoto);
                        intentoCamara = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        Uri uriImagenGuardada = Uri.fromFile(carpeta);
                        intentoCamara.putExtra(MediaStore.EXTRA_OUTPUT, uriImagenGuardada);
                        startActivityForResult(intentoCamara,TOMAR_FOTO);
                    }
                })
                .setNegativeButton("Cancelar", null)
                .create();
        builder.show();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Comprobamos que la foto se ha realizado
        if (requestCode == 1 && resultCode == RESULT_OK) {
            //Creamos un bitmap con la imagen recientemente
            //almacenada en la memoria
            File imgFile = new File(getExternalFilesDir(null)+"/"+Datos.nombreFoto);
            if(imgFile.exists()){
                Bitmap bMap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                imagen.setImageBitmap(bMap);
            }
            //Bitmap bMap = BitmapFactory.decodeFile(
                    //getExternalFilesDir(null)+"/"+Datos.nombreFoto;

            //Añadimos el bitmap al imageView para
            //mostrarlo por pantalla



            //intento = new Intent(this, CrearPertenenciaActivity.class);
            //startActivity(intento);
            //} else {
            //  Toast mensaje = Toast.makeText(this, "Antes de tomar la foto debe escribir un nombre " +
            //        "para la pertenecia", Toast.LENGTH_LONG);
            //mensaje.show();
        }
    }

}
