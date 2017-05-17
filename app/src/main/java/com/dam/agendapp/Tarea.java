package com.dam.agendapp;

import java.util.Calendar;

public class Tarea {

    private int tipo;
    private String titulo;
    private String descripcion;
    private Boolean recordatorio;
    Calendar fecha;

    public Tarea(String titulo,String descripcion, Boolean recordatorio, Calendar fecha){
        this.tipo = 0;
        this.fecha = fecha;
        setTitulo(titulo);
        setDescripcion(descripcion);
        setRecordatorio(recordatorio);
    }

    public int getTipo(){
        return tipo;
    }

    public void setTipo(int tipo){
        this.tipo = tipo;
    }

    public String getTitulo(){
        return titulo;
    }

    public void setTitulo(String titulo){
        this.titulo = titulo;
    }

    public String getDescripcion(){
        return descripcion;
    }

    public void setDescripcion(String descripcion){
        this.descripcion = descripcion;
    }

    public Boolean getRecordatorio(){
        return recordatorio;
    }

    public void setRecordatorio(Boolean recordatorio){
        this.recordatorio = recordatorio;
    }


}
