package com.example.juasa.apporganizador.base_de_datos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.os.Build;

import com.example.juasa.apporganizador.Pertenencia;
import com.example.juasa.apporganizador.Categoria;
import com.example.juasa.apporganizador.Ubicacion;
import com.example.juasa.apporganizador.Usuario;
import com.example.juasa.apporganizador.datos.Datos;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Controlador_base_datos extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String NOMBRE_BD = "miBD";
    public static final String TABLA_USUARIOS = "USUARIO";
    public static final String TABLA_UBICACIONES = "MIS_UBICACIONES";
    public static final String TABLA_CATEGORIAS = "MIS_CATEGORIAS";
    public static final String TABLA_PERTENENCIAS = "MIS_PERTENENCIAS";

    private static final String CABECERA_JOIN_UBIC_CAT_PERT = TABLA_PERTENENCIAS +
            " INNER JOIN " + TABLA_CATEGORIAS +
            " ON " + TABLA_PERTENENCIAS + ".ID_CATEGORIA_PERT = " + TABLA_CATEGORIAS + ".ID_CATEGORIA " +
            "INNER JOIN " + TABLA_UBICACIONES +
            " ON " + TABLA_PERTENENCIAS + ".ID_UBICACION_PERT = " + TABLA_UBICACIONES + ".ID_UBICACION";

    private final String[] CabeceraColumnasJoin = new String[]{
            TABLA_PERTENENCIAS + "._id, " + TABLA_PERTENENCIAS + ".ID_PERT, " +
                    TABLA_PERTENENCIAS + ".NOMBRE_PERT, " + TABLA_PERTENENCIAS + ".DETALLE_PERT, " +
                    TABLA_CATEGORIAS + ".NOMBRE_CATEGORIA, " + TABLA_UBICACIONES + ".NOMBRE_UBICACION"
    };

    private String clausulaWhere = " WHERE " + TABLA_CATEGORIAS + ".ID_CATEGORIA = " +
            TABLA_PERTENENCIAS + ".ID_CATEGORIA_PERT AND " +
            TABLA_PERTENENCIAS + ".ID_UBICACION_PERT = " + TABLA_UBICACIONES + ".ID_UBICACION";

    private String clausulaWhereBuscar = " WHERE " + TABLA_CATEGORIAS + ".ID_CATEGORIA = " +
            TABLA_PERTENENCIAS + ".ID_CATEGORIA_PERT AND " +
            TABLA_PERTENENCIAS + ".ID_UBICACION_PERT = " + TABLA_UBICACIONES + ".ID_UBICACION" +
            " AND " + TABLA_PERTENENCIAS + ".NOMBRE_PERT ";

    private String cabeceraDatos = TABLA_PERTENENCIAS + "._id, " + TABLA_PERTENENCIAS + ".ID_PERT, " +
            TABLA_PERTENENCIAS + ".NOMBRE_PERT, " + TABLA_PERTENENCIAS + ".DETALLE_PERT, " +
            TABLA_CATEGORIAS + ".NOMBRE_CATEGORIA, " + TABLA_UBICACIONES + ".NOMBRE_UBICACION ";

    public Controlador_base_datos(Context context) {
        super(context, NOMBRE_BD, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLA_USUARIOS + " (MAIL VARCHAR PRIMARY KEY, NOMBRE_USUARIO TEXT NOT NULL, PASSWORD TEXT, FECHA_ALTA DATA, MALETA TEXT)");
        db.execSQL("CREATE TABLE " + TABLA_UBICACIONES + " (ID_UBICACION INTEGER PRIMARY KEY AUTOINCREMENT, NOMBRE_UBICACION TEXT NOT NULL, DESCRIPCION_UBICACION TEXT)");
        db.execSQL("CREATE TABLE " + TABLA_CATEGORIAS + " (ID_CATEGORIA INTEGER PRIMARY KEY AUTOINCREMENT, NOMBRE_CATEGORIA TEXT NOT NULL, DESCRIPCION_CATEGORIA TEXT)");
        db.execSQL("CREATE TABLE " + TABLA_PERTENENCIAS + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, ID_PERT INTEGER, NOMBRE_PERT TEXT NOT NULL, DETALLE_PERT TEXT, " +
                "ID_CATEGORIA_PERT INTEGER NOT NULL, ID_UBICACION_PERT INTEGER NOT NULL, FOTO_PERT TEXT, MALETA_PERT TEXT, " +
                "FOREIGN KEY (ID_CATEGORIA_PERT) REFERENCES " + TABLA_CATEGORIAS + "(ID_CATEGORIA) ON DELETE CASCADE ON UPDATE CASCADE," +
                "FOREIGN KEY (ID_UBICACION_PERT) REFERENCES " + TABLA_UBICACIONES + "(ID_UBICACION) ON DELETE CASCADE ON UPDATE CASCADE)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                db.setForeignKeyConstraintsEnabled(true);
            } else {
                db.execSQL("PRAGMA foreign_keys=ON");
            }
        }
    }

    public Cursor obtenerCabecerasPedidos() {
        SQLiteDatabase db = this.getReadableDatabase();

        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();

        builder.setTables(CABECERA_JOIN_UBIC_CAT_PERT);

        return builder.query(db, CabeceraColumnasJoin, null, null, null, null, null);
    }

    //Devuelve si existe un valor en la tabla de la base de datos

    public Boolean existe(String nombreTabla, String campo, String valor) {

        boolean existe = false;
        String query = "SELECT * FROM " + nombreTabla + " WHERE " + campo + " = '" + valor + "'";
        //Abrir la bd

        SQLiteDatabase db = this.getReadableDatabase();

        //Consulta

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst())
            existe = true;
        db.close();
        cursor.close();
        return existe;
    }

    public void anadirUsuario(String mail, String nombre, String pass) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String date = sdf.format(new Date());
        ContentValues registro = new ContentValues();
        registro.put("MAIL", mail);
        registro.put("NOMBRE_USUARIO", nombre);
        registro.put("PASSWORD", pass);
        registro.put("FECHA_ALTA", date);
        registro.put("MALETA", "F");

        //Abrir base de datos
        SQLiteDatabase db = this.getWritableDatabase();

        db.insert(TABLA_USUARIOS, null, registro);
        db.close();
    }

    public void anadirUbicacion(String nombre, String descripcion) {
        ContentValues registro = new ContentValues();
        registro.put("NOMBRE_UBICACION", nombre);
        registro.put("DESCRIPCION_UBICACION", descripcion);

        //Abrir base de datos
        SQLiteDatabase db = this.getWritableDatabase();

        db.insert(TABLA_UBICACIONES, null, registro);
        db.close();
    }

    public void anadirCategoria(String nombre, String descripcion) {
        ContentValues registro = new ContentValues();
        registro.put("NOMBRE_CATEGORIA", nombre);
        registro.put("DESCRIPCION_CATEGORIA", descripcion);

        //Abrir base de datos
        SQLiteDatabase db = this.getWritableDatabase();

        db.insert(TABLA_CATEGORIAS, null, registro);
        db.close();
    }

    public void anadirPertenencia(String nombrePert, String detallePert, int idCatPert, int idUbicPert, String fotoPert) {
        ContentValues registro = new ContentValues();
        registro.put("ID_PERT", numRegistrosTabla(TABLA_PERTENENCIAS) + 1);
        registro.put("NOMBRE_PERT", nombrePert);
        registro.put("DETALLE_PERT", detallePert);
        registro.put("ID_CATEGORIA_PERT", idCatPert);
        registro.put("ID_UBICACION_PERT", idUbicPert);
        registro.put("FOTO_PERT", fotoPert);
        registro.put("MALETA_PERT", "F");

        //Abrir base de datos
        SQLiteDatabase db = this.getWritableDatabase();

        db.insertOrThrow(TABLA_PERTENENCIAS, null, registro);

        db.close();
    }

    public Usuario obtenerUsuario(String mail) {

        //Abrir la bd

        SQLiteDatabase db = this.getReadableDatabase();

        //Consulta

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLA_USUARIOS + " WHERE MAIL =\"" + mail + "\"", null);

        Usuario usuario = new Usuario();

        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            usuario.setMail(cursor.getString(0));
            usuario.setNombre(cursor.getString(1));
            usuario.setPassword(cursor.getString(2));
        } else
            usuario = null;
        db.close();
        cursor.close();
        return usuario;
    }

    public Ubicacion obtenerUbicacion(String nombreUbic) {

        //Abrir la bd

        SQLiteDatabase db = this.getReadableDatabase();

        //Consulta

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLA_UBICACIONES + " WHERE NOMBRE_UBICACION = '" + nombreUbic + "'", null);

        Ubicacion ubicacion = new Ubicacion();

        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            ubicacion.setIdUbicacion(cursor.getInt(0));
            ubicacion.setNombreUbicacion(cursor.getString(1));
            ubicacion.setDescripcionUbicacion(cursor.getString(2));
        } else
            ubicacion = null;
        db.close();
        cursor.close();
        return ubicacion;
    }

    public Categoria obtenerCategoria(String nombreCat) {

        //Abrir la bd

        SQLiteDatabase db = this.getReadableDatabase();

        //Consulta

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLA_CATEGORIAS + " WHERE NOMBRE_CATEGORIA = '" + nombreCat + "'", null);

        Categoria cat = new Categoria();

        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            cat.setIdCategoria(cursor.getInt(0));
            cat.setNombreCategoria(cursor.getString(1));
            cat.setDescripcionCategoria(cursor.getString(2));
        } else
            cat = null;
        db.close();
        cursor.close();
        return cat;
    }

    public String[] obtenerUbicaciones() {
        //Abrir
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor;
        int numReg;
        String[] nombresUbicaciones;
        //Consulta
        cursor = db.rawQuery("SELECT * FROM " + TABLA_UBICACIONES, null);

        numReg = cursor.getCount();
        if (numReg == 0) {
            db.close();
            return null;
        } else {
            nombresUbicaciones = new String[numReg];
            cursor.moveToFirst();
            for (int i = 0; i < numReg; i++) {
                nombresUbicaciones[i] = cursor.getString(1);
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();
        return nombresUbicaciones;
    }

    public String[] obtenerCategorias() {
        //Abrir
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor;
        int numReg;
        String[] nombresCategorias;
        //Consulta
        cursor = db.rawQuery("SELECT * FROM " + TABLA_CATEGORIAS, null);

        numReg = cursor.getCount();
        if (numReg == 0) {
            db.close();
            return null;
        } else {
            nombresCategorias = new String[numReg];
            cursor.moveToFirst();
            for (int i = 0; i < numReg; i++) {
                nombresCategorias[i] = cursor.getString(1);
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();
        return nombresCategorias;
    }

    public String[] obtenerIDTodasPertenencias() {
        //Abrir
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor;
        int numReg;
        String[] pertenencias;
        //Consulta

        cursor = db.rawQuery("SELECT * FROM " + TABLA_PERTENENCIAS, null);

        numReg = cursor.getCount();
        if (numReg == 0) {
            db.close();
            pertenencias = null;
        } else {
            pertenencias = new String[numReg];
            cursor.moveToFirst();
            for (int i = 0; i < numReg; i++) {
                pertenencias[i] = cursor.getString(0);
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();
        return pertenencias;
    }

    public Pertenencia obtenerPertenenciaPorNombre(String nombrePertenencia) {
        Pertenencia pertenencia = null;
        String querySQL = "SELECT " + cabeceraDatos + "FROM " + TABLA_PERTENENCIAS + ", " +
                TABLA_CATEGORIAS + ", " + TABLA_UBICACIONES + clausulaWhereBuscar +
                " = '" + nombrePertenencia + "'";

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(querySQL, null);
        int numReg = cursor.getCount();

        if (numReg != 0) {
            cursor.moveToFirst();
            pertenencia = new Pertenencia();
            pertenencia.setIdPertenencia(cursor.getInt(1));
            pertenencia.setNombrePertenencia(cursor.getString(2));
            pertenencia.setDetallePertenencia(cursor.getString(3));
            pertenencia.setNombreCategoriaPertenencia(cursor.getString(4));
            pertenencia.setNombreUbicacionPertenencia(cursor.getString(5));

            //pertenencia.setFotoPertenencia(cursor.getString(5));
        }
        cursor.close();
        return pertenencia;
    }

    public Cursor obtenerPertenencias(String ordenado) {

        SQLiteDatabase db = this.getReadableDatabase();

        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();

        builder.setTables(CABECERA_JOIN_UBIC_CAT_PERT);

        return builder.query(db, CabeceraColumnasJoin, null, null, null, null, ordenado);
    }

    public int numRegistrosTabla(String nombreTabla) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + nombreTabla, null);
        return cursor.getCount();
    }

    public void borrarUbicacion(String nombreUbicacion) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLA_UBICACIONES, "NOMBRE_UBICACION =?", new String[]{nombreUbicacion});
        db.close();
    }

    public void borrarCategoria(String nombreCategoria) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLA_CATEGORIAS, "NOMBRE_CATEGORIA = ?", new String[]{nombreCategoria});
        db.close();
    }

    public void borrarPertenencia(String nombrePertenencia) {
        SQLiteDatabase db = this.getWritableDatabase();
        //db.delete(TABLA_PERTENENCIAS, "NOMBRE_PERT = ?", new String[]{nombrePertenencia});
        String whereClause = String.format("%s=?", "NOMBRE_PERT");
        String[] whereArgs = {nombrePertenencia};
        db.delete(TABLA_PERTENENCIAS, whereClause, whereArgs);
        db.close();
    }

    public void actualizarPassword(String nuevaPass) {
        //Abrir la bd

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues registro = new ContentValues();
        registro.put("PASSWORD", nuevaPass);

        db.update(TABLA_USUARIOS, registro, null, null);

        db.close();
    }

    public void actualizarUbicacion(Ubicacion ubicacion, String nombreAntiguo) {
        //Abrir la bd

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues registro = new ContentValues();
        registro.put("NOMBRE_UBICACION", ubicacion.getNombreUbicacion());
        registro.put("DESCRIPCION_UBICACION", ubicacion.getDescripcionUbicacion());

        db.update(TABLA_UBICACIONES, registro, "NOMBRE_UBICACION = ?", new String[]{nombreAntiguo});

        db.close();
    }

    public void actualizarCategoria(Categoria categoria, String nombreAntiguo) {
        //Abrir la bd

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues registro = new ContentValues();
        registro.put("NOMBRE_CATEGORIA", categoria.getNombreCategoria());
        registro.put("DESCRIPCION_CATEGORIA", categoria.getDescripcionCategoria());

        db.update(TABLA_CATEGORIAS, registro, "NOMBRE_CATEGORIA = ?", new String[]{nombreAntiguo});

        db.close();
    }

    public void actualizarPertenencia(Pertenencia pertenencia, int iDcat,
                                      int iDubic, String nombreAntiguo) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues registro = new ContentValues();

        registro.put("ID_PERT", pertenencia.getIdPertenencia());
        registro.put("NOMBRE_PERT", pertenencia.getNombrePertenencia());
        registro.put("DETALLE_PERT", pertenencia.getDetallePertenencia());
        registro.put("ID_CATEGORIA_PERT", iDcat);
        registro.put("ID_UBICACION_PERT", iDubic);
        registro.put("FOTO_PERT", "");
        String whereClause = String.format("%s=?", "NOMBRE_PERT");
        String[] whereArgs = {nombreAntiguo};
        db.update(TABLA_PERTENENCIAS, registro, whereClause, whereArgs);
        db.close();
    }

    public Cursor listadoPertenencias(String campoBuscado, String tipoBuscado) {

        SQLiteDatabase db = this.getReadableDatabase();

        String seleccion = String.format("%s=?", tipoBuscado);
        String[] argumentos = {campoBuscado};

        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();

        builder.setTables(CABECERA_JOIN_UBIC_CAT_PERT);

        return builder.query(db, CabeceraColumnasJoin, seleccion, argumentos, null, null, null);
    }

    public int numRegistrosIguales(String nombre_tabla, String nombreCampo, String valor) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + nombre_tabla + " WHERE " + nombreCampo + " = '" + valor + "'", null);
        return cursor.getCount();
    }

    public void actualizarUsuarioMaleta(String mail, String valor) {
        //Abrir la bd

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues registro = new ContentValues();

        //registro.put("MALETA_PERT", "T");
        db.execSQL("UPDATE " + TABLA_USUARIOS + " SET MALETA = '" + valor + "'" + " WHERE MAIL = '" + mail + "'");
        //db.update(TABLA_PERTENENCIAS, registro,"NOMBRE_PERT = ?", new String[] {nombrePertenencia});
        db.close();
    }

    public void actualizarCampoHacerMaleta(String nombrePertenencia) {
        //Abrir la bd

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues registro = new ContentValues();

        //registro.put("MALETA_PERT", "T");
        db.execSQL("UPDATE " + TABLA_PERTENENCIAS + " SET MALETA_PERT = 'T'" + "WHERE NOMBRE_PERT = '" + nombrePertenencia + "'");
        //db.update(TABLA_PERTENENCIAS, registro,"NOMBRE_PERT = ?", new String[] {nombrePertenencia});
        db.close();
    }

    public void actualizarCampoDeshacerMaleta() {
        //Abrir la bd

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues registro = new ContentValues();

        //registro.put("MALETA_PERT", "F");
        db.execSQL("UPDATE " + TABLA_PERTENENCIAS + " SET MALETA_PERT = 'F'");
        //db.update(TABLA_PERTENENCIAS, registro,null, null);
        db.close();
    }

    public Cursor obtenerPertenenciasPorMaleta() {
        SQLiteDatabase db = this.getReadableDatabase();

        //Consulta
        String querySQL = "SELECT " + cabeceraDatos + "FROM " + TABLA_PERTENENCIAS + ", " +
                TABLA_CATEGORIAS + ", " + TABLA_UBICACIONES + clausulaWhere +
                " AND MALETA_PERT = 'T'";
        return db.rawQuery(querySQL, null);
    }

    public int numAparicionesCategoriaTablaPert(int valor) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM MIS_PERTENENCIAS" + " WHERE ID_CATEGORIA_PERT = " + valor, null);
        return cursor.getCount();
    }

    public int numAparicionesUbicacionTablaPert(int valor) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM MIS_PERTENENCIAS" + " WHERE ID_UBICACION_PERT = " + valor, null);
        return cursor.getCount();
    }

    public int obtenerIdCategoria(String nombreCat) {
        int idCategoria = 0;
        //Abrir la bd

        SQLiteDatabase db = this.getReadableDatabase();

        //Consulta

        Cursor cursor = db.rawQuery("SELECT * FROM MIS_CATEGORIAS WHERE NOMBRE_CATEGORIA = '" + nombreCat + "'", null);

        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            idCategoria = cursor.getInt(0);
        } else
            idCategoria = 0;
        db.close();
        cursor.close();
        return idCategoria;
    }

    public int obtenerIdUbicacion(String nombreUbic) {
        int idUbicacion = 0;
        //Abrir la bd

        SQLiteDatabase db = this.getReadableDatabase();

        //Consulta

        Cursor cursor = db.rawQuery("SELECT * FROM MIS_UBICACIONES WHERE NOMBRE_UBICACION = '" + nombreUbic + "'", null);

        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            idUbicacion = Integer.valueOf(cursor.getInt(0));
        } else
            idUbicacion = 0;
        db.close();
        cursor.close();
        return idUbicacion;
    }

    public boolean comprobarSiMaleta(String nombrePert) {
        String query, campoMaleta;
        Boolean estaTrue = false;
        SQLiteDatabase db = this.getReadableDatabase();
        query = "SELECT MALETA_PERT FROM " + TABLA_PERTENENCIAS + " WHERE NOMBRE_PERT = '" + nombrePert + "'";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            campoMaleta = (cursor.getString(0));
            if (campoMaleta.equals("T"))
                estaTrue = true;
        }
        return estaTrue;
    }

    public int estadisticas(String tabla) {
        String query;
        int num;
        SQLiteDatabase db = this.getReadableDatabase();
        query = "SELECT * FROM " + tabla;
        Cursor cursor = db.rawQuery(query, null);
        return cursor.getCount();
    }

    public String obtenerFecha() {
        String query, campoFecha = "";
        SQLiteDatabase db = this.getReadableDatabase();
        query = "SELECT FECHA_ALTA FROM " + TABLA_USUARIOS;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            campoFecha = (cursor.getString(0));
        }
        return campoFecha;
    }

    public void borrarTabla(String tabla){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(tabla, null, null);
    }
}

