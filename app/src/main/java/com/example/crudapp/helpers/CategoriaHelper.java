package com.example.crudapp.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.crudapp.beans.Categoria;
import com.example.crudapp.database.tablaCategoria;

import java.util.ArrayList;

public class CategoriaHelper {

    private static final String DB_NAME = "database";

    private Context context;
    private ConexionSQLiteOpenHelper conn;

    public CategoriaHelper(Context context) {
        this.context = context;
        conn = new ConexionSQLiteOpenHelper(context,"database", null,1);
    }

    public ArrayList<Categoria> listar() {
        SQLiteDatabase db=conn.getReadableDatabase();

        ArrayList<Categoria> listaCategoria = new ArrayList<Categoria>();

        Categoria categoria= null;
        Cursor cursor=db.rawQuery("SELECT * FROM "+ tablaCategoria.NOMBRE_TABLA,null);

        while (cursor.moveToNext()){
            categoria=new Categoria();
            categoria.setId(cursor.getInt(0));
            categoria.setNombre(cursor.getString(1));

            listaCategoria.add(categoria);
        }

        return listaCategoria;
    }

    public void insertar(Categoria categoria) {

        SQLiteDatabase db=conn.getWritableDatabase();

        ContentValues values=new ContentValues();

        //values.put(Utilidades.CAMPO_ID,producto.getId());
        values.put(tablaCategoria.CAMPO_NOMBRE,categoria.getNombre());

        Long idResultante=db.insert(tablaCategoria.NOMBRE_TABLA,tablaCategoria.CAMPO_ID,values);

        db.close();
    }

    public Categoria consultar(int id) {
        SQLiteDatabase db=conn.getReadableDatabase();
        String[] parametros={String.valueOf(id)};
        String[] campos={tablaCategoria.CAMPO_ID, tablaCategoria.CAMPO_NOMBRE};

        try {
            Cursor cursor =db.query(tablaCategoria.NOMBRE_TABLA,campos,tablaCategoria.CAMPO_ID+"=?",parametros,null,null,null);
            cursor.moveToFirst();

            Categoria categoria = new Categoria();
            categoria.setId(cursor.getInt(0));
            categoria.setNombre(cursor.getString(1));

            cursor.close();

            return categoria;
        }catch (Exception e){
            return null;
        }


    }

    public void actualizar(Categoria categoria) {
        SQLiteDatabase db = conn.getWritableDatabase();

        String[] parametros={String.valueOf(categoria.getId())};

        ContentValues values = new ContentValues();

        values.put(tablaCategoria.CAMPO_ID,categoria.getId());
        values.put(tablaCategoria.CAMPO_NOMBRE,categoria.getNombre());

        db.update(tablaCategoria.NOMBRE_TABLA, values, tablaCategoria.CAMPO_ID + " = ?",parametros);
        db.close();
    }

    public boolean eliminar(int id) {

        try{

            SQLiteDatabase db = conn.getWritableDatabase();
            conn.enableFK(db);

            String[] parametros={String.valueOf(id)};
            db.delete(tablaCategoria.NOMBRE_TABLA, tablaCategoria.CAMPO_ID + " = ?",parametros);
            db.close();

            return true;

        }catch (Exception e){
            return false;
        }
    }
}
