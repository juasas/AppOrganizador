package com.example.juasa.apporganizador;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

    public class MaletaCursorAdapter extends CursorAdapter {

        private ImageView cajaIcono;
        private TextView cajaNombre, cajaCategoria, cajaUbicacion;
        private CheckBox cajaCheck;
        private String icono, nombre, categoria, ubicacion;

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
            cajaCheck = (CheckBox) view.findViewById(R.id.elementos_lista_maleta_check_elemento);
            //icono = cursor.getString(cursor.getColumnIndex("FOTO_PERT"));
            //if (icono.equals(null)){

            //      } else {
            cajaIcono.setImageResource(R.mipmap.avatarcasa1);//}
            nombre = cursor.getString(cursor.getColumnIndex("NOMBRE_PERT"));
            categoria = cursor.getString(cursor.getColumnIndex("NOMBRE_CATEGORIA"));
            ubicacion = cursor.getString(cursor.getColumnIndex("NOMBRE_UBICACION"));

            cajaNombre.setText(nombre);
            cajaCategoria.setText(categoria);
            cajaUbicacion.setText(ubicacion);
        }
    }
