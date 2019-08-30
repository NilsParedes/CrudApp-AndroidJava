package com.example.crudapp.beans;

public class Producto {

    private int id;
    private String nombre;
    private int categoria;
    private int marca;
    private int umedida;
    private float precio;

    public Producto() { }

    public Producto(int id, String nombre, int categoria, int marca, int umedida, float precio) {
        this.id = id;
        this.nombre = nombre;
        this.categoria = categoria;
        this.marca = marca;
        this.umedida = umedida;
        this.precio = precio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCategoria() {
        return categoria;
    }

    public void setCategoria(int categoria) {
        this.categoria = categoria;
    }

    public int getMarca() {
        return marca;
    }

    public void setMarca(int marca) {
        this.marca = marca;
    }

    public int getUmedida() {
        return umedida;
    }

    public void setUmedida(int umedida) {
        this.umedida = umedida;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
