package com.example.crudapp.database;

public class tablaCategoria {

    public static final String NOMBRE_TABLA="categorias";
    public static final String CAMPO_ID="id";
    public static final String CAMPO_NOMBRE="nombre";

    public static final String CREAR_TABLA="CREATE TABLE " +
            ""+NOMBRE_TABLA+" ("+CAMPO_ID+
            " INTEGER PRIMARY KEY AUTOINCREMENT, "+CAMPO_NOMBRE+" TEXT)";
}
