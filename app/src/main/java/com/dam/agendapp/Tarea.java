package com.dam.agendapp;

import java.io.Serializable;
import java.util.Calendar;

public class Tarea implements Serializable {

    private int id;
    private int tipo;
    private String titulo;
    private String descripcion;
    private String telefono;
    private String email;
    private String direccion;
    private String hora;
    private int recordatorio;
    private int completada;
    Calendar fecha;

    public Tarea(int id, int tipo, int recordatorio, String titulo,String descripcion,
                 String telefono, String email ,String direccion, String hora, Calendar fecha, int completada){
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
        this.completada = completada;
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

    public String getDireccion() {
        return direccion;
    }

    public int getRecordatorio(){
        return recordatorio;
    }

    public void setRecordatorio(int recordatorio){
        this.recordatorio = recordatorio;
    }

    public boolean getCompletada(){
        boolean c = false;
        if(completada == 1){
            c = true;
        }

        return c;
    }

    public void setTitulo(String cadena){
        this.titulo = cadena;
    }

    public void setDescripcion(String cadena){
        this.descripcion = cadena;
    }

    public void setEmail(String cadena){
        this.email = cadena;
    }

    public void setTelefono(String cadena){
        this.telefono = cadena;
    }

    public void setDireccion(String cadena){
        this.direccion = cadena;
    }

    public void setHora(String cadena){
        this.hora = cadena;
    }

}
