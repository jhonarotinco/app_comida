package com.upc.app_comida.entidades;

public class Comida {
    private int id;
    private String nombre;
    private String categoria;
    private int calorias;

    public Comida(String nombre, String cantidad, int calorias) {
        this.nombre = nombre;
        this.categoria = cantidad;
        this.calorias = calorias;
    }

    public Comida(int id, String nombre, String cantidad, int calorias) {
        this.id = id;
        this.nombre = nombre;
        this.categoria = cantidad;
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

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String cantidad) {
        this.categoria = categoria;
    }

    public int getCalorias() {
        return calorias;
    }

    public void setCalorias(int calorias) {
        this.calorias = calorias;
    }
}
