package com.example.juasa.apporganizador;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.juasa.apporganizador.base_de_datos.Controlador_base_datos;
import com.example.juasa.apporganizador.datos.Datos;

public class MostrarPertenenciaActivity extends AppCompatActivity {

    private Controlador_base_datos controlador;
    private ImageView cajaFoto;
    private TextView cajaCat, cajaUbic, cajaNombre, cajaDetalle, cajaTextoFoto;
    private Bundle extras;
    private int id_pert;
    private String categoriaPert, ubicacionPert, nombrePert, detallePert, fotoPert;
    private View view;
    private Intent intento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_pertenencia);
        recibirParametros();
        inicializar();
        llenarPertenencia();
    }

    public void recibirParametros() {
        extras = getIntent().getExtras();
        if (extras != null) {
            id_pert = extras.getInt("id_pert");
            nombrePert = extras.getString("nombre_pert");
            detallePert = extras.getString("detalle_pert");
            categoriaPert = extras.getString("categoria_pert");
            ubicacionPert = extras.getString("ubicacion_pert");
            fotoPert = extras.getString("foto_pert");
        }
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.menu_action_gestion, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item ){
        String titulo, mensaje;
        switch (item.getItemId()) {
            case R.id.menu_action_gestion_volver_atras:
                volver();
                break;
            case R.id.menu_action_gestion_gohome:
                intento = new Intent(this, MenuPrincipalActivity.class);
                startActivity(intento);
                break;
            case R.id.menu_action_gestion_editar_elemento:
                Datos.pertenenciaGlobal.setIdPertenencia(id_pert);
                Datos.pertenenciaGlobal.setNombrePertenencia(nombrePert);
                Datos.nombreAntiguo = Datos.pertenenciaGlobal.getNombrePertenencia();
                Datos.pertenenciaGlobal.setDetallePertenencia(detallePert);
                Datos.pertenenciaGlobal.setNombreCategoriaPertenencia(categoriaPert);
                Datos.pertenenciaGlobal.setNombreUbicacionPertenencia(ubicacionPert);
                Datos.pertenenciaGlobal.setFotoPertenencia(fotoPert);
                intento = new Intent(this, CrearEditarPertenenciaActivity.class);
                intento.putExtra("operacion", "editar");
                startActivity(intento);
                break;
            case R.id.menu_action_gestion_eliminar_elemento:
                if (controlador.comprobarSiMaleta(nombrePert)){
                    AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.estiloCuadrodDialogo))
                            .setTitle("AVISO")
                            .setMessage("Va a eliminar una Pertenencia que está incluida en su Maleta")
                            .setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {
                                    controlador.borrarPertenencia(nombrePert);
                                    mostrar();
                                    volver();
                                }
                            })
                            .setNegativeButton("Cancelar", null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else {AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.estiloCuadrodDialogo))
                        .setTitle("Confirmar eliminación")
                        .setMessage("¿Qué desea hacer?")
                        .setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                controlador.borrarPertenencia(nombrePert);
                                mostrar();
                                volver();
                            }
                        })
                        .setNegativeButton("Cancelar", null);
                    AlertDialog dialog = builder.create();
                    dialog.show();}
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void llenarPertenencia(){
        cajaNombre.setText(nombrePert);
        cajaDetalle.setText(detallePert);
        cajaCat.setText(categoriaPert);
        cajaUbic.setText(ubicacionPert);

        if ((fotoPert == null) || (fotoPert.equals(""))){
            cajaFoto.setImageResource(R.mipmap.iconopertenencia);
            cajaTextoFoto.setText("Esta Pertenencia no tiene fotografía");
        } else {
            String rutaImagen = Datos.rutaImagenes + fotoPert;
            cajaFoto.setImageBitmap(BitmapFactory.decodeFile(rutaImagen));
            cajaTextoFoto.setText(fotoPert);
        }
    }

    public void inicializar(){
        cajaNombre = (TextView) findViewById(R.id.mostrar_pert_caja_nombre);
        cajaDetalle = (TextView) findViewById(R.id.mostrar_pert_caja_detalle);
        cajaCat = (TextView) findViewById(R.id.mostrar_pert_caja_cat);
        cajaUbic = (TextView) findViewById(R.id.mostrar_pert_caja_ubic);
        cajaFoto = (ImageView) findViewById(R.id.mostrar_pert_imagen);
        cajaTextoFoto =(TextView) findViewById(R.id.mostrar_pert_caja_textoFoto);
        controlador = new Controlador_base_datos(this);
        }

    public void mostrar () {
        Toast toast = Toast.makeText(this, "Pertenencia eliminada correctamente", Toast.LENGTH_LONG);
        toast.show();
    }

    public void volver(){
        Intent intento = new Intent(this, PertenenciasPpalActivity.class);
        intento.putExtra("busqueda", "General");
        intento.putExtra("parametro_buscado", "");
        startActivity(intento);
    }

    public void ayuda(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.estiloCuadrodDialogo))
                .setTitle("AYUDA")
                .setMessage("SELECCIONE uno de los iconos situados en la barra superior de la pantalla. " +
                        "Puede: REGRESAR al listado de Pertenencias, IR al Menú Principal " +
                        "de bienvenida, EDITAR esta Pertenencia o ELIMINARLA")
                .setPositiveButton("Aceptar", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
