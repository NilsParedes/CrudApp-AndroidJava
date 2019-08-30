package com.example.crudapp.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.crudapp.beans.Marca;
import com.example.crudapp.database.tablaMarca;

import java.util.ArrayList;

public class MarcaHelper {

    private static final String DB_NAME = "database";

    private Context context;
    private ConexionSQLiteOpenHelper conn;

    public MarcaHelper(Context context) {
        this.context = context;
        conn = new ConexionSQLiteOpenHelper(context,"database", null,1);
    }

    public ArrayList<Marca> listar() {
        SQLiteDatabase db=conn.getReadableDatabase();

        ArrayList<Marca> listaMarca = new ArrayList<Marca>();

        Marca marca= null;
        Cursor cursor=db.rawQuery("SELECT * FROM "+ tablaMarca.NOMBRE_TABLA,null);

        while (cursor.moveToNext()){
            marca=new Marca();
            marca.setId(cursor.getInt(0));
            marca.setNombre(cursor.getString(1));

            listaMarca.add(marca);
        }

        return listaMarca;
    }

    public void insertar(Marca marca) {

        SQLiteDatabase db=conn.getWritableDatabase();

        ContentValues values=new ContentValues();

        //values.put(Utilidades.CAMPO_ID,producto.getId());
        values.put(tablaMarca.CAMPO_NOMBRE,marca.getNombre());

        Long idResultante=db.insert(tablaMarca.NOMBRE_TABLA,tablaMarca.CAMPO_ID,values);

        db.close();
    }

    public Marca consultar(int id) {
        SQLiteDatabase db=conn.getReadableDatabase();
        String[] parametros={String.valueOf(id)};
        String[] campos={tablaMarca.CAMPO_ID, tablaMarca.CAMPO_NOMBRE};

        try {
            Cursor cursor =db.query(tablaMarca.NOMBRE_TABLA,campos,tablaMarca.CAMPO_ID+"=?",parametros,null,null,null);
            cursor.moveToFirst();

            Marca marca = new Marca();
            marca.setId(cursor.getInt(0));
            marca.setNombre(cursor.getString(1));

            cursor.close();

            return marca;
        }catch (Exception e){
            return null;
        }


    }

    public void actualizar(Marca marca) {
        SQLiteDatabase db = conn.getWritableDatabase();

        String[] parametros={String.valueOf(marca.getId())};

        ContentValues values = new ContentValues();

        values.put(tablaMarca.CAMPO_ID,marca.getId());
        values.put(tablaMarca.CAMPO_NOMBRE,marca.getNombre());

        db.update(tablaMarca.NOMBRE_TABLA, values, tablaMarca.CAMPO_ID + " = ?",parametros);
        db.close();
    }

    public boolean eliminar(int id) {

        try{

            SQLiteDatabase db = conn.getWritableDatabase();
            conn.enableFK(db);
            String[] parametros={String.valueOf(id)};
            db.delete(tablaMarca.NOMBRE_TABLA, tablaMarca.CAMPO_ID + " = ?",parametros);
            db.close();

            return true;

        }catch (Exception e){
            return false;
        }

    }
}
