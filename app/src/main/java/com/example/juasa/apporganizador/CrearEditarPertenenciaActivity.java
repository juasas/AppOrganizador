package com.example.juasa.apporganizador;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.juasa.apporganizador.base_de_datos.Controlador_base_datos;
import com.example.juasa.apporganizador.datos.Datos;

import java.io.File;

public class CrearEditarPertenenciaActivity extends AppCompatActivity {
    private Controlador_base_datos controlador;
    private Entrada_Salida salida;
    private EditText cajaNombre, cajaDetalle;
    private ArrayAdapter<String> adaptadorSpinnerCategoria, adaptadorSpinnerUbicacion;
    private Spinner spinnerCategoria, spinnerUbicacion;
    private String nombrePert, detallePert, nombreUbicacionPert, nombreCategoriaPert, fotoPert, nombreAntiguoPert, operacion;
    private Intent intento, intentoCamara;
    private int idCategoria, idUbicacion;
    private TextView cajaTitulo, cajaTextoFoto;
    private static int CREAR = 1;
    private static int EDITAR = 2;
    private static final int PERMISSION_REQUEST_CAMERA = 0;
    private static final int PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE = 1;
    private ImageView imagen;
    private final static String[] PERMISOS = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA};
    private Toast toast;
    private String mensaje;
    private View view;
    private Bundle extras;
    private Pertenencia pertenencia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_editar_pertenencia);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        inicializar();
        cargarDatos();
        actualizarUI_spinners();
        llenarPertenencia();
        verOperación();
        //actualizarUI_spinners();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_action_secundario, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_action_secundario_volver_atras:
                Intent intento = new Intent(this, PertenenciasPpalActivity.class);
                intento.putExtra("busqueda", "General");
                intento.putExtra("parametro_buscado", "");
                startActivity(intento);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void inicializar() {
        controlador = new Controlador_base_datos(this);
        cajaTitulo = (TextView) findViewById(R.id.crear_pert_titulo);
        cajaNombre = (EditText) findViewById(R.id.crear_pert_caja_nombre);
        cajaDetalle = (EditText) findViewById(R.id.crear_pert_caja_detalle);
        spinnerCategoria = findViewById(R.id.crear_pert_sp_categoria);
        spinnerUbicacion = findViewById(R.id.crear_pert_sp_ubic);
        cajaTextoFoto = (TextView) findViewById(R.id.crear_pert_caja_textoFoto);
    }

    public void verOperación() {
        extras = getIntent().getExtras();
        if (extras != null) {
            operacion = extras.getString("operacion");
            switch (operacion) {
                case "crear":
                    cajaTitulo.setText("Crear Pertenencia");
                    break;
                case "editar":
                    cajaTitulo.setText("Editar Pertenencia");
                    break;
            }
        }
    }

    public void cargarDatos() {
        //id_pert = Datos.pertenenciaGlobal.getIdPertenencia();
        nombrePert = Datos.pertenenciaGlobal.getNombrePertenencia();
        nombreAntiguoPert = Datos.nombreAntiguo;
        detallePert = Datos.pertenenciaGlobal.getDetallePertenencia();
        nombreCategoriaPert = Datos.pertenenciaGlobal.getNombreCategoriaPertenencia();
        nombreUbicacionPert = Datos.pertenenciaGlobal.getNombreUbicacionPertenencia();
        fotoPert = Datos.pertenenciaGlobal.getFotoPertenencia();
    }

    public void llenarPertenencia() {
        cajaNombre.setText(nombrePert);
        cajaDetalle.setText(detallePert);
        spinnerCategoria.setSelection(obtenerPosicionItem(spinnerCategoria, nombreCategoriaPert));
        spinnerUbicacion.setSelection(obtenerPosicionItem(spinnerUbicacion, nombreUbicacionPert));

        if ((fotoPert == null) || (fotoPert.equals("")))
            cajaTextoFoto.setText("Esta pertenencia no tiene fotografía");
        else
            cajaTextoFoto.setText(fotoPert);
    }

    public void mostrar(String mensaje) {
        toast = Toast.makeText(this, mensaje, Toast.LENGTH_LONG);
        toast.show();
    }

    public void actualizarTextoFoto() {
        if ((Datos.pertenenciaGlobal.getFotoPertenencia() == null) || (Datos.pertenenciaGlobal.getFotoPertenencia().equals(""))) {
            cajaTextoFoto.setText("Esta pertenencia no tiene fotografía");
        } else {
            cajaTextoFoto.setText(Datos.pertenenciaGlobal.getFotoPertenencia());
        }
    }

    public void guardarDatos() {
        //Datos.pertenenciaGlobal.setIdPertenencia(id_pert);
        Datos.pertenenciaGlobal.setNombrePertenencia(cajaNombre.getText().toString());
        Datos.pertenenciaGlobal.setDetallePertenencia(cajaDetalle.getText().toString());
        Datos.pertenenciaGlobal.setNombreCategoriaPertenencia(spinnerCategoria.getSelectedItem().toString());
        Datos.pertenenciaGlobal.setNombreUbicacionPertenencia(spinnerUbicacion.getSelectedItem().toString());
        Datos.pertenenciaGlobal.setFotoPertenencia(fotoPert);
    }

    public void queHacer (View view){
        if (operacion.equals("crear"))
            crearNuevaPertenencia();
        else
            if (operacion.equals("editar"))
                guardarPertenencia();
    }

    public void crearNuevaPertenencia() {
        nombrePert = cajaNombre.getText().toString().toUpperCase();
        detallePert = cajaDetalle.getText().toString();
        nombreUbicacionPert = spinnerUbicacion.getSelectedItem().toString();
        nombreCategoriaPert = spinnerCategoria.getSelectedItem().toString();
        fotoPert = Datos.pertenenciaGlobal.getFotoPertenencia();
        idCategoria = controlador.obtenerIdCategoria(nombreCategoriaPert);
        idUbicacion = controlador.obtenerIdUbicacion(nombreUbicacionPert);

        if (nombrePert.isEmpty()) {
            Toast mensaje = Toast.makeText(this, "El campo obligatorio NOMBRE no puede estar vacío", Toast.LENGTH_LONG);
            mensaje.show();
        } else {
            {
                if (!controlador.existe(controlador.TABLA_PERTENENCIAS, "NOMBRE_PERT", nombrePert)) {
                    controlador.anadirPertenencia(nombrePert, detallePert, idCategoria, idUbicacion, fotoPert);
                    Toast mensaje = Toast.makeText(this, "Pertenencia almacenada correctamente", Toast.LENGTH_LONG);
                    mensaje.show();
                    intento = new Intent(this, PertenenciasPpalActivity.class);
                    intento.putExtra("busqueda", "General");
                    intento.putExtra("parametro_buscado", "");
                    startActivity(intento);
                } else {
                    Toast mensaje = Toast.makeText(this, "No se puede usar ese nombre. La pertenencia ya existe", Toast.LENGTH_LONG);
                    mensaje.show();
                }
            }
        }
    }

    public void guardarPertenencia() {
        inicializar();
        pertenencia = new Pertenencia();
        //Se guardan los valores de los spinner y se transforman en enteros

        String categoriaSp = spinnerCategoria.getSelectedItem().toString().toUpperCase();
        idCategoria = controlador.obtenerIdCategoria(categoriaSp);

        String ubicacionSp = spinnerUbicacion.getSelectedItem().toString().toUpperCase();
        idUbicacion = controlador.obtenerIdUbicacion(ubicacionSp);

        //pertenencia.setIdPertenencia(id_pert);
        pertenencia.setNombrePertenencia(cajaNombre.getText().toString().toUpperCase());
        pertenencia.setDetallePertenencia(cajaDetalle.getText().toString());
        pertenencia.setFotoPertenencia(cajaTextoFoto.getText().toString());

        //int idCat = controlador.obtenerIdCategoria(pertenencia.getCategoriaPertenencia());
        //ubic = controlador.obtenerUbicacion(pertenencia.getUbicacionPertenencia());
        if (pertenencia.getNombrePertenencia().isEmpty()) {
            Toast mensaje = Toast.makeText(this, "El campo obligatorio NOMBRE no puede estar vacío", Toast.LENGTH_LONG);
            mensaje.show();
        } else {
            int numApariciones = controlador.numRegistrosIguales(Controlador_base_datos.TABLA_PERTENENCIAS,
                    "NOMBRE_PERT", pertenencia.getNombrePertenencia());
            if ((numApariciones == 0) || ((numApariciones != 0) && (pertenencia.getNombrePertenencia().equals(nombreAntiguoPert)))) {
                controlador.actualizarPertenencia(pertenencia, idCategoria, idUbicacion, nombreAntiguoPert);
                Toast mensaje = Toast.makeText(this, "Pertenencia editada y almacenada correctamente", Toast.LENGTH_LONG);
                mensaje.show();
                Intent intento = new Intent(this, PertenenciasPpalActivity.class);
                intento.putExtra("busqueda", "General");
                intento.putExtra("parametro_buscado", "");
                startActivity(intento);
            } else {
                Toast mensaje = Toast.makeText(this, "Nombre de la pertenencia no válido, Pertenencia ya existe", Toast.LENGTH_LONG);
                mensaje.show();
            }
        }
    }

    public void resetear(View view) {
        cajaNombre.setText("");
        cajaDetalle.setText("");
    }

    public void actualizarUI_spinners() {
        if (controlador.numRegistrosTabla(Controlador_base_datos.TABLA_CATEGORIAS) == 0)
            spinnerCategoria.setAdapter(null);
        else {
            adaptadorSpinnerCategoria = new ArrayAdapter(this, R.layout.elementos_spinner, R.id.nombre_elemento, controlador.obtenerCategorias());
            spinnerCategoria.setAdapter(adaptadorSpinnerCategoria);
        }

        if (controlador.numRegistrosTabla(controlador.TABLA_UBICACIONES) == 0)
            spinnerUbicacion.setAdapter(null);
        else {
            adaptadorSpinnerUbicacion = new ArrayAdapter(this, R.layout.elementos_spinner, R.id.nombre_elemento, controlador.obtenerUbicaciones());
            spinnerUbicacion.setAdapter(adaptadorSpinnerUbicacion);
        }
    }

    public int obtenerPosicionItem(Spinner spinner, String categoriaBuscada) {
        //Creamos la variable posicion y lo inicializamos en 0
        int posicion = 0;
        //Recorre el spinner en busca del ítem que coincida con el parametro categoriaBuscada
        //que lo pasaremos posteriormente
        for (int i = 0; i < spinner.getCount(); i++) {
            //Almacena la posición del ítem que coincida con la búsqueda
            if (spinner.getItemAtPosition(i).toString().equals(categoriaBuscada)) {
                posicion = i;
            }
        }
        //Devuelve un valor entero (si encontro una coincidencia devuelve la
        // posición 0 o N, de lo contrario devuelve 0 = posición inicial)
        return posicion;
    }

    public void ayuda(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.estiloCuadrodDialogo))
                .setTitle("AYUDA")
                .setMessage("Introduzca los valores que quiera dar a esta Pertenencia y " +
                        "pulse  el botón azul redondo en parte inferior derecha de la pantalla " +
                        "para aceptar")
                .setPositiveButton("Aceptar", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void entrarMenuFoto(View view) {
        guardarDatos();
        comprobarPermisoEscritura();
    }

    public void comprobarPermisoEscritura() {
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
            comprobarPermisoCamara();
        }//no hace falta solicitar permisos
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
            continuar();
        }//no hace falta solicitar permisos}
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CAMERA: {
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
            }
            break;
            // otros bloques de 'case' para controlar otros permisos de la aplicación
            case PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE: {
                // Si la petición es cancelada, el array resultante estará vacío.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mensaje = "Permiso concedido";
                    mostrar(mensaje);
                    // El permiso ha sido concedido.
                    comprobarPermisoCamara();
                } else {
                    mensaje = "Permiso denegado";
                    mostrar(mensaje);
                    // Permiso denegado, deshabilita la funcionalidad que depende de este permiso.
                }
            }
            break;
        }

        return;
    }

    public void continuar() {
        final EditText cajaTexto = new EditText(this);
        AlertDialog builder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.estiloCuadrodDialogo))
                .setTitle("Nombre de la fotografía")
                .setMessage("Introduzca un nombre de la fotografía sin extensión")
                .setView(cajaTexto)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        Datos.pertenenciaGlobal.setFotoPertenencia(cajaTexto.getText().toString() + ".jpg");
                        File carpeta = new File(getExternalFilesDir(null), Datos.pertenenciaGlobal.getFotoPertenencia());
                        intentoCamara = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        Uri uriImagenGuardada = Uri.fromFile(carpeta);
                        intentoCamara.putExtra(MediaStore.EXTRA_OUTPUT, uriImagenGuardada);
                        if (operacion.equals("crear"))
                            startActivityForResult(intentoCamara, CREAR);
                        else
                            startActivityForResult(intentoCamara, EDITAR);
                    }
                })
                .setNegativeButton("Cancelar", null)
                .create();
        builder.show();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Comprobamos que la foto se ha realizado
        if (requestCode == 1 && resultCode == RESULT_OK) {
            intento = new Intent(this, CrearEditarPertenenciaActivity.class);
            intento.putExtra("operacion", "crear");
            startActivity(intento);
        } else {
            if (requestCode == 2 && resultCode == RESULT_OK) {
                intento = new Intent(this, CrearEditarPertenenciaActivity.class);
                intento.putExtra("operacion", "editar");
                startActivity(intento);
            }
        }
    }
}