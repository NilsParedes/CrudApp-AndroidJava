package com.example.crudapp.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.crudapp.beans.UMedida;
import com.example.crudapp.database.tablaUMedida;

import java.util.ArrayList;

public class UMedidaHelper {

    private static final String DB_NAME = "database";

    private Context context;
    private ConexionSQLiteOpenHelper conn;

    public UMedidaHelper(Context context) {
        this.context = context;
        conn = new ConexionSQLiteOpenHelper(context,"database", null,1);
    }

    public ArrayList<UMedida> listar() {
        SQLiteDatabase db=conn.getReadableDatabase();

        ArrayList<UMedida> listaUMedida = new ArrayList<UMedida>();

        UMedida uMedida= null;
        Cursor cursor=db.rawQuery("SELECT * FROM "+ tablaUMedida.NOMBRE_TABLA,null);

        while (cursor.moveToNext()){
            uMedida=new UMedida();
            uMedida.setId(cursor.getInt(0));
            uMedida.setNombre(cursor.getString(1));

            listaUMedida.add(uMedida);
        }

        return listaUMedida;
    }

    public void insertar(UMedida uMedida) {

        SQLiteDatabase db=conn.getWritableDatabase();

        ContentValues values=new ContentValues();

        //values.put(Utilidades.CAMPO_ID,producto.getId());
        values.put(tablaUMedida.CAMPO_NOMBRE,uMedida.getNombre());

        Long idResultante=db.insert(tablaUMedida.NOMBRE_TABLA,tablaUMedida.CAMPO_ID,values);

        db.close();
    }

    public UMedida consultar(int id) {
        SQLiteDatabase db=conn.getReadableDatabase();
        String[] parametros={String.valueOf(id)};
        String[] campos={tablaUMedida.CAMPO_ID, tablaUMedida.CAMPO_NOMBRE};

        try {
            Cursor cursor =db.query(tablaUMedida.NOMBRE_TABLA,campos,tablaUMedida.CAMPO_ID+"=?",parametros,null,null,null);
            cursor.moveToFirst();

            UMedida uMedida = new UMedida();
            uMedida.setId(cursor.getInt(0));
            uMedida.setNombre(cursor.getString(1));

            cursor.close();

            return uMedida;
        }catch (Exception e){
            return null;
        }


    }

    public void actualizar(UMedida uMedida) {
        SQLiteDatabase db = conn.getWritableDatabase();

        String[] parametros={String.valueOf(uMedida.getId())};

        ContentValues values = new ContentValues();

        values.put(tablaUMedida.CAMPO_ID,uMedida.getId());
        values.put(tablaUMedida.CAMPO_NOMBRE,uMedida.getNombre());

        db.update(tablaUMedida.NOMBRE_TABLA, values, tablaUMedida.CAMPO_ID + " = ?",parametros);
        db.close();
    }

    public boolean eliminar(int id) {

        try{

            SQLiteDatabase db = conn.getWritableDatabase();
            conn.enableFK(db);

            String[] parametros={String.valueOf(id)};
            db.delete(tablaUMedida.NOMBRE_TABLA, tablaUMedida.CAMPO_ID + " = ?",parametros);
            db.close();

            return true;

        }catch (Exception e){
            return false;
        }
    }
}
