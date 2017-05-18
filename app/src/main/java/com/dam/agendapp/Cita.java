package com.dam.agendapp;

import java.util.Calendar;

public class Cita{
    private String direccion;
    private String hora;

    public Cita(String titulo,String descripcion, Boolean recordatorio, Calendar fecha,
                     String direccion, String hora){
        //super(titulo,descripcion,recordatorio, fecha);
        //this.setTipo(2);
        this.direccion = direccion;
        this.hora = hora;

    }

}
