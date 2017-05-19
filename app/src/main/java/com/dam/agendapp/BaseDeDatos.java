package com.dam.agendapp;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.CalendarContract;
import android.util.Log;
import android.widget.Button;

import java.util.ArrayList;
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
                "fecha date,recordatorio integer,titulo text, descripcion text, telefono text, " +
                "email text, direccion text, horaCita text, completada integer)");



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAnte, int versionNue) {
        db.execSQL("drop table if exists alarma" );
        db.execSQL(" create table alarma( idal integer primary key autoincrement,encabezado text, mensaje text,fecha date, hora time)");

        db.execSQL("drop table if exists lista" );
        db.execSQL(" create table lista( idlis integer primary key autoincrement,tipo integer, " +
                "fecha date,recordatorio integer,titulo text, descripcion text, telefono text, " +
                "email text, direccion text, horaCita text, , completada integer)");

    }

    public boolean insertarLista(int tipo, Calendar fecha, String titulo, String descripcion,
                                String telefono, String email, String direccion, String horaCita){

        ContentValues valores = new ContentValues();
        valores.put("tipo", tipo);
        valores.put("titulo", titulo);
        valores.put("descripcion", descripcion);
        valores.put("fecha", fechaToString(fecha));

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

    //Devuelve una lista con las tareas que corresponden a la fecha dada en el Calendar
    public ArrayList<Tarea> recuperaLista(Calendar f) {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<Tarea> lista = new ArrayList<Tarea>();
        String fecha = fechaToString(f);
        String consulta = "select * from lista where fecha=?";

        Cursor c = db.rawQuery(consulta,new String[] {fecha});
        c.moveToFirst();

        if(c.moveToFirst()) {
            do {

                Tarea t = new Tarea(c.getInt(0), c.getInt(1), c.getInt(3), c.getString(4), c.getString(5),
                        c.getString(6), c.getString(7), c.getString(8), c.getString(9), fechaToCalendar(c.getString(2)));

                lista.add(t);


            } while (c.moveToNext());
        }

        db.close();
        c.close();

        return lista;
    }

    public boolean  borrarTarea(int id) {
        SQLiteDatabase db = getWritableDatabase();
        long salida=0;
        if (db != null) {
            salida=db.delete("lista", "idlis=" + id, null);
        }
        db.close();
        return(salida>0);
    }

    public Boolean  borrarLista(Calendar f) {

        SQLiteDatabase db = getWritableDatabase();

        int salida=0;
        if (db != null) {
            String fecha = fechaToString(f);
            Log.d("TAG", fecha);
            salida = db.delete("lista", "titulo=", null);
        }
        db.close();
        return (salida > 0);
    }



    //Convierte la fecha de un tipo calendar al formato YYYY-MM-DD
    private String fechaToString(Calendar f){
        String mes;
        String dia;

        int a = f.get(Calendar.YEAR);
        int m = f.get(Calendar.MONTH)+1;
        int d = f.get(Calendar.DAY_OF_MONTH);

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

        return a+"-"+ mes+"-"+ dia;
    }

    //Convierte el formato YYYY-MM-DD a Calendar
    private Calendar fechaToCalendar(String f){
        Calendar fecha = Calendar.getInstance();

        fecha.set(Calendar.YEAR,Integer.parseInt(f.substring(0,4)));
        fecha.set(Calendar.MONTH,Integer.parseInt(f.substring(5,7)+1));
        fecha.set(Calendar.DAY_OF_MONTH,Integer.parseInt(f.substring(8)));

        return  fecha;
    }
}
