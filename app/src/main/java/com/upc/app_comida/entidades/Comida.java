package com.upc.app_comida.entidades;

public class Comida {
    private int id;
    private String nombre;
    private String cantidad;
    private int calorias;

    public Comida(String nombre, String cantidad, int calorias) {
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.calorias = calorias;
    }

    public Comida(int id, String nombre, String cantidad, int calorias) {
        this.id = id;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.calorias = calorias;
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

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public int getCalorias() {
        return calorias;
    }

    public void setCalorias(int calorias) {
        this.calorias = calorias;
    }
}
