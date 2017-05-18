package com.dam.agendapp;

import java.util.Calendar;

public class Contactar{
    private String nombre;
    private String telefono;
    private String email;

    public Contactar(String titulo,String descripcion, Boolean recordatorio, Calendar fecha,
                          String nombre, String telefono, String email){
        //super(titulo,descripcion,recordatorio, fecha);
        //this.setTipo(1);
        this.nombre = nombre;
        this.telefono = telefono;
        this.email = email;
    }

}
