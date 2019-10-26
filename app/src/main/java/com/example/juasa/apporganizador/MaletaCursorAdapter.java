package com.example.juasa.apporganizador;

import android.content.Context;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.juasa.apporganizador.datos.Datos;

public class MaletaCursorAdapter extends CursorAdapter {

        private ImageView cajaIcono;
        private TextView cajaNombre, cajaCategoria, cajaUbicacion;
        private String imagen, nombre, categoria, ubicacion;

        public MaletaCursorAdapter(Context context, Cursor c) {
            super(context, c, 0);
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
            LayoutInflater inflater = LayoutInflater.from(context);
            return inflater.inflate(R.layout.elementos_lista_maleta, viewGroup, false);
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            cajaIcono = (ImageView) view.findViewById(R.id.elementos_lista_maleta_icono_elemento);
            cajaNombre = (TextView) view.findViewById(R.id.elementos_lista_maleta_nombre_elemento);
            cajaCategoria = (TextView) view.findViewById(R.id.elemtos_lista_maleta_cat_elemento);
            cajaUbicacion = (TextView) view.findViewById(R.id.elementos_lista_maleta_ubic_elemento);

            nombre = cursor.getString(cursor.getColumnIndex("NOMBRE_PERT"));
            categoria = cursor.getString(cursor.getColumnIndex("NOMBRE_CATEGORIA"));
            ubicacion = cursor.getString(cursor.getColumnIndex("NOMBRE_UBICACION"));
            imagen = cursor.getString(cursor.getColumnIndex("FOTO_PERT"));

            if ((imagen == null) || (imagen.equals(""))){
                cajaIcono.setImageResource(R.mipmap.iconopertenencia);
            } else {
                String rutaImagen = Datos.rutaImagenes + imagen;
                cajaIcono.setImageBitmap(BitmapFactory.decodeFile(rutaImagen));
            }
            cajaNombre.setText(nombre);
            cajaCategoria.setText(categoria);
            cajaUbicacion.setText(ubicacion);
        }
    }
