package com.example.crudapp.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.crudapp.beans.Producto;
import com.example.crudapp.database.tablaProducto;

import java.util.ArrayList;

public class ProductoHelper {

    private static final String DB_NAME = "database";

    private Context context;
    private ConexionSQLiteOpenHelper conn;

    public ProductoHelper(Context context) {
        this.context = context;
        conn = new ConexionSQLiteOpenHelper(context,"database", null,1);
    }

    public ArrayList<Producto> listar() {
        SQLiteDatabase db=conn.getReadableDatabase();

        ArrayList<Producto> listaProducto = new ArrayList<Producto>();

        Producto producto= null;
        Cursor cursor=db.rawQuery("SELECT * FROM "+ tablaProducto.NOMBRE_TABLA,null);

        while (cursor.moveToNext()){
            producto=new Producto();
            producto.setId(cursor.getInt(0));
            producto.setNombre(cursor.getString(1));
            producto.setCategoria(cursor.getInt(2));
            producto.setMarca(cursor.getInt(3));
            producto.setUmedida(cursor.getInt(4));
            producto.setPrecio(cursor.getFloat(5));

            listaProducto.add(producto);
        }

        return listaProducto;
    }

    public void insertar(Producto producto) {

        SQLiteDatabase db=conn.getWritableDatabase();

        ContentValues values=new ContentValues();

        //values.put(Utilidades.CAMPO_ID,producto.getId());
        values.put(tablaProducto.CAMPO_NOMBRE,producto.getNombre());
        values.put(tablaProducto.CAMPO_CATEGORIA,producto.getCategoria());
        values.put(tablaProducto.CAMPO_MARCA,producto.getMarca());
        values.put(tablaProducto.CAMPO_UMEDIDA,producto.getUmedida());
        values.put(tablaProducto.CAMPO_PRECIO,producto.getPrecio());

        Long idResultante=db.insert(tablaProducto.NOMBRE_TABLA,tablaProducto.CAMPO_ID,values);

        db.close();
    }

    public Producto consultar(int id) {
        SQLiteDatabase db=conn.getReadableDatabase();
        String[] parametros={String.valueOf(id)};
        String[] campos={tablaProducto.CAMPO_ID, tablaProducto.CAMPO_NOMBRE, tablaProducto.CAMPO_CATEGORIA, tablaProducto.CAMPO_MARCA, tablaProducto.CAMPO_UMEDIDA, tablaProducto.CAMPO_PRECIO};

        try {
            Cursor cursor =db.query(tablaProducto.NOMBRE_TABLA,campos,tablaProducto.CAMPO_ID+"=?",parametros,null,null,null);
            cursor.moveToFirst();

            Producto producto = new Producto();
            producto.setId(cursor.getInt(0));
            producto.setNombre(cursor.getString(1));
            producto.setCategoria(cursor.getInt(2));
            producto.setMarca(cursor.getInt(3));
            producto.setUmedida(cursor.getInt(4));
            producto.setPrecio(cursor.getFloat(5));

            cursor.close();

            return producto;
        }catch (Exception e){
            return null;
        }


    }

    public void actualizar(Producto producto) {
        SQLiteDatabase db = conn.getWritableDatabase();

        String[] parametros={String.valueOf(producto.getId())};

        ContentValues values = new ContentValues();

        values.put(tablaProducto.CAMPO_ID,producto.getId());
        values.put(tablaProducto.CAMPO_NOMBRE,producto.getNombre());
        values.put(tablaProducto.CAMPO_CATEGORIA,producto.getCategoria());
        values.put(tablaProducto.CAMPO_MARCA,producto.getMarca());
        values.put(tablaProducto.CAMPO_UMEDIDA,producto.getUmedida());
        values.put(tablaProducto.CAMPO_PRECIO,producto.getPrecio());

        db.update(tablaProducto.NOMBRE_TABLA, values, tablaProducto.CAMPO_ID + " = ?",parametros);
        db.close();
    }

    public void eliminar(int id) {
        SQLiteDatabase db = conn.getWritableDatabase();
        String[] parametros={String.valueOf(id)};
        db.delete(tablaProducto.NOMBRE_TABLA, tablaProducto.CAMPO_ID + " = ?",parametros);
        db.close();
    }
}
