package com.dam.agendapp;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.CalendarContract;
import android.widget.Button;

import java.util.Calendar;

public class BaseDeDatos extends SQLiteOpenHelper {

    public BaseDeDatos(Context context) {
        super(context, "DB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(" create table alarma( idal integer primary key autoincrement,encabezado text, " +
                "mensaje text,fecha date, hora time)");
        db.execSQL(" create table lista( idlis integer primary key autoincrement,tipo integer, " +
                "fecha date,titulo text, descripcion text" + "telefono text, email text, " +
                "direccion text, horaCita text");



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAnte, int versionNue) {
        db.execSQL("drop table if exists alarma" );
        db.execSQL(" create table alarma( idal integer primary key autoincrement,encabezado text, mensaje text,fecha date, hora time)");

        db.execSQL("drop table if exists lista" );
        db.execSQL(" create table lista( idlis integer primary key autoincrement,tipo integer, " +
                "fecha date,titulo text, descripcion text" + "telefono text, email text, " +
                "direccion text, horaCita text");

    }

    public boolean insertarLista(int tipo, Calendar fecha, String titulo, String descripcion,
                                String telefono, String email, String direccion, String horaCita){

        ContentValues valores = new ContentValues();
        valores.put("titulo", titulo);
        valores.put("descripcion", descripcion);

        String mes;
        String dia;

        int a = fecha.get(Calendar.YEAR);
        int m = fecha.get(Calendar.MONTH)+1;
        int d = fecha.get(Calendar.DAY_OF_MONTH);

        if(m < 10){
            mes = "0" + m;
        }else{
            mes = Integer.toString(m);
        }

        if(d < 10){
            dia = "0" + d;
        }else{
            dia = Integer.toString(d);
        }

        valores.put("fecha", a+"-"+ mes+"-"+ dia);

        if(tipo == 1){
            valores.put("telefono", telefono);
            valores.put("email", email);
        }else if(tipo == 2){
            valores.put("direccion", direccion);
            valores.put("horaCita", horaCita);
        }

        long salida=0;
        SQLiteDatabase db = getWritableDatabase();
        if (db != null) {
            salida=db.insert("lista", null, valores);
        }
        db.close();
        return(salida>0);


    }
}
