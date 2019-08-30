package com.example.crudapp.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.crudapp.database.*;

public class ConexionSQLiteOpenHelper extends SQLiteOpenHelper {

    public ConexionSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(tablaCategoria.CREAR_TABLA);
        db.execSQL(tablaMarca.CREAR_TABLA);
        db.execSQL(tablaUMedida.CREAR_TABLA);
        db.execSQL(tablaProducto.CREAR_TABLA);

        db.execSQL("INSERT INTO umedidas(nombre) values('Kilo')");
        db.execSQL("INSERT INTO umedidas(nombre) values('Gramo')");
        db.execSQL("INSERT INTO umedidas(nombre) values('Litro')");
        db.execSQL("INSERT INTO umedidas(nombre) values('Mililitro')");

        db.execSQL("INSERT INTO marcas(nombre) values('CocaColaCompany')");
        db.execSQL("INSERT INTO marcas(nombre) values('Alicorp')");
        db.execSQL("INSERT INTO marcas(nombre) values('IncaKolaCompany')");

        db.execSQL("INSERT INTO categorias(nombre) values('Gaseosas')");
        db.execSQL("INSERT INTO categorias(nombre) values('Cereales')");

        db.execSQL("INSERT INTO productos(nombre, categoria_id, marca_id, umedida_id, precio) values('CocaCola', 1, 1, 3, 2.5)");
        db.execSQL("INSERT INTO productos(nombre, categoria_id, marca_id, umedida_id, precio) values('IncaCola', 1, 3, 3, 2.0)");
        db.execSQL("INSERT INTO productos(nombre, categoria_id, marca_id, umedida_id, precio) values('CerealBar', 2, 2, 2, 0.5)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAntigua, int versionNueva) {
        db.execSQL("DROP TABLE IF EXISTS "+ tablaCategoria.NOMBRE_TABLA);
        db.execSQL("DROP TABLE IF EXISTS "+ tablaMarca.NOMBRE_TABLA);
        db.execSQL("DROP TABLE IF EXISTS "+ tablaUMedida.NOMBRE_TABLA);
        db.execSQL("DROP TABLE IF EXISTS "+ tablaProducto.NOMBRE_TABLA);
        onCreate(db);
    }

    public void enableFK(SQLiteDatabase db){
        db.execSQL("PRAGMA foreign_keys=ON");
    }
}

