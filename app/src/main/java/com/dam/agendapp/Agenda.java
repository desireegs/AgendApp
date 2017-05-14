package com.dam.agendapp;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.Calendar;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class Agenda extends AppCompatActivity {

    String meses[] = {"ENERO", "FEBRERO", "MARZO", "ABRIL", "MAYO", "JUNIO", "JULIO", "AGOSTO", "SEPTIEMBRE",
            "OCTUBRE", "NOVIEMBRE", "DICIEMBRE"};
    String dias_semana[] = {"","DOMINGO","LUNES", "MARTES", "MIÉRCOLES", "JUEVES", "VIERNES","SÁBADO"};

    Calendar fecha;

    TextView mes;
    TextView semana;
    TextView dia;
    //ScrollView scroll;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agenda_layout);
        fecha = Calendar.getInstance();

        mes = (TextView)findViewById(R.id.mes);
        semana = (TextView)findViewById(R.id.semana);
        dia = (TextView)findViewById(R.id.dia);
        //scroll = (ScrollView) findViewById(R.id.scroll) ;

        mes.setText(getMes(fecha));
        semana.setText(getDiaSemana(fecha));
        dia.setText(getDia(fecha));

        /*scroll.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.d("TAG","Click largo");
                onCreateDialog(savedInstanceState);
                return true;
            }
        });*/



    }

    public Dialog onCreateDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.pick_tipo)
                .setItems(R.array.tipos, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        irEvento(which);
                    }
                });
        return builder.create();
    }

    private String getMes(Calendar fecha){
        return meses[fecha.get(Calendar.MONTH)];
    }

    private String getDiaSemana(Calendar fecha){
        return dias_semana[fecha.get(Calendar.DAY_OF_WEEK)];
    }

    private String getDia(Calendar fecha){
        return Integer.toString(fecha.get(Calendar.DAY_OF_MONTH));
    }

    private void refrescar(){
        mes.setText(getMes(fecha));
        semana.setText(getDiaSemana(fecha));
        dia.setText(getDia(fecha));
    }

    public void irSiguiente(View view){
        int a = fecha.get(Calendar.YEAR);
        int m = fecha.get(Calendar.MONTH);
        int d = fecha.get(Calendar.DAY_OF_MONTH);
        if(d == fecha.getActualMaximum(Calendar.DAY_OF_MONTH)) {
            d = 1;
            if (m == 12) {
                m = 1;
                a++;
            }else
                m++;
        }else
            d++;

        fecha.set(a,m,d);
        refrescar();
    }

    public void irAnterior(View view){
        int a = fecha.get(Calendar.YEAR);
        int m = fecha.get(Calendar.MONTH);
        int d = fecha.get(Calendar.DAY_OF_MONTH);
        if(d == 1)  {
            if (m == 1) {
                d=31;
                m=12;
                a--;
            }else {
                Calendar aux = Calendar.getInstance();
                m--;
                aux.set(Calendar.MONTH,m);
                d = aux.getActualMaximum(Calendar.DAY_OF_MONTH);
            }
        }else
            d--;

        fecha.set(a,m,d);
        refrescar();

    }

    public void irNuevoEvento(View view){
        Dialog dialogo = onCreateDialog();
        dialogo.show();
    }

    public void irEvento(int tipo){
        Intent intent = new Intent(this,Evento.class);
        intent.putExtra(EXTRA_MESSAGE,tipo);
        startActivity(intent);
    }



}
