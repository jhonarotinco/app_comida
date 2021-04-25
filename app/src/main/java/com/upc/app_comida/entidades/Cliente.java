package com.upc.app_comida.entidades;

public class Cliente {
    private String id;
    private String correo;
    private String nombre;
    private String objetivo;

    public Cliente(String objetoString, String string, String df, String correo) {
        this.correo = correo;
        this.nombre = nombre;
        this.objetivo = objetivo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }
}
