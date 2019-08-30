package com.example.crudapp.database;

public class tablaUMedida {

    public static final String NOMBRE_TABLA="umedidas";
    public static final String CAMPO_ID="id";
    public static final String CAMPO_NOMBRE="nombre";

    public static final String CREAR_TABLA="CREATE TABLE " +
            ""+NOMBRE_TABLA+" ("+CAMPO_ID+
            " INTEGER PRIMARY KEY AUTOINCREMENT, "+CAMPO_NOMBRE+" TEXT)";

}
