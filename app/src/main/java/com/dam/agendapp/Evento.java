package com.dam.agendapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class Evento extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        int tipo = intent.getIntExtra(EXTRA_MESSAGE, 0);
        if(tipo == 0)
            tipoTarea();
        else if(tipo == 1)
            tipoContactar();
        else if (tipo == 2)
            tipoCita();
    }

    private void tipoTarea(){
        setContentView(R.layout.tarea_layout);
    }
    private void tipoContactar(){
        setContentView(R.layout.contactar_layout);
    }
    private void tipoCita(){
        setContentView(R.layout.cita_layout);
    }

    public void programar_evento() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
