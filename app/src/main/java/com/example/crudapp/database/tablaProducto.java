package com.example.crudapp.database;

public class tablaProducto {

    public static final String NOMBRE_TABLA="productos";
    public static final String CAMPO_ID="id";
    public static final String CAMPO_NOMBRE="nombre";
    public static final String CAMPO_CATEGORIA="categoria_id";
    public static final String CAMPO_MARCA="marca_id";
    public static final String CAMPO_UMEDIDA="umedida_id";
    public static final String CAMPO_PRECIO="precio";

    public static final String CREAR_TABLA="CREATE TABLE " +
            ""+NOMBRE_TABLA+" ("+CAMPO_ID+
            " INTEGER PRIMARY KEY AUTOINCREMENT, "+CAMPO_NOMBRE+" TEXT,"+CAMPO_PRECIO+" FLOAT,"+CAMPO_CATEGORIA+" INTEGER NOT NULL,"+CAMPO_MARCA+" INTEGER NOT NULL,"+CAMPO_UMEDIDA+" INTEGER NOT NULL"
            +", FOREIGN KEY("+CAMPO_CATEGORIA+") REFERENCES categorias(id) ON DELETE RESTRICT, FOREIGN KEY("+CAMPO_MARCA+") REFERENCES marcas(id) ON DELETE RESTRICT, FOREIGN KEY("+CAMPO_UMEDIDA+") REFERENCES umedidas(id) ON DELETE RESTRICT);";
}
