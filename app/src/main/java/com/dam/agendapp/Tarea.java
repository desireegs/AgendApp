package com.dam.agendapp;

import java.util.Calendar;

public class Tarea {

    private int id;
    private int tipo;
    private String titulo;
    private String descripcion;
    private String telefono;
    private String email;
    private String direccion;
    private String hora;
    private int recordatorio;
    Calendar fecha;

    public Tarea(int id, int tipo, int recordatorio, String titulo,String descripcion,
                 String telefono, String email ,String direccion, String hora, Calendar fecha){
        this.id = id;
        this.tipo = tipo;
        this.fecha = fecha;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.recordatorio = recordatorio;
        this.direccion = direccion;
        this.hora = hora;
        this.telefono = telefono;
        this.email = email;
    }

    public int getId(){
        return id;
    }

    public int getTipo(){
        return tipo;
    }


    public String getTitulo(){
        return titulo;
    }

    public String getEmail(){
        return email;
    }

    public String getTelefono(){
        return telefono;
    }

    public String getHoraCita(){
        return hora;
    }

    public String getDescripcion(){
        return descripcion;
    }

    public int getRecordatorio(){
        return recordatorio;
    }

    public void setRecordatorio(int recordatorio){
        this.recordatorio = recordatorio;
    }


}
