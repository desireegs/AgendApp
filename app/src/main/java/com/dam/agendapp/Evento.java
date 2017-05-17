package com.dam.agendapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;

import java.util.Calendar;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class Evento extends AppCompatActivity {


    private Button recordatorio;
    private Button bguardar;

    private BaseDeDatos db;
    private int tipo;
    private Calendar fecha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        tipo = intent.getIntExtra(EXTRA_MESSAGE, 0);

        if(tipo == 0)
            tipoTarea();
        else if(tipo == 1)
            tipoContactar();
        else if (tipo == 2)
            tipoCita();

        recordatorio = (Button) findViewById(R.id.butRecordatorio);
        bguardar = (Button) findViewById(R.id.butGuardar);

        recordatorio.setOnClickListener(new View.OnClickListener(){
            public  void onClick(View v){
                programar_evento();
                                                      }
            });

        bguardar.setOnClickListener(new View.OnClickListener(){
            public  void onClick(View v){
                guardar();
            }
        });
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
        Intent intent = new Intent(this, Notificacion.class);
        startActivity(intent);
    }

    public void guardar(){
        db = new BaseDeDatos(getApplicationContext());

        EditText titulo = (EditText) findViewById(R.id.txtTitulo);
        EditText descripcion = (EditText) findViewById(R.id.txtDescripcion);

        if(tipo == 0){
            db.insertarLista(tipo, fecha, titulo.getText().toString(), descripcion.getText().toString(),
                    null, null, null, null);
        }else if(tipo == 1) {
            EditText telefono = (EditText) findViewById(R.id.txtTelefono);
            EditText email = (EditText) findViewById(R.id.txtEmail);

            db.insertarLista(tipo, fecha, titulo.getText().toString(), descripcion.getText().toString(),
                    telefono.getText().toString(), email.getText().toString(), null, null);
        }
        else if(tipo == 2){
            EditText direccion = (EditText) findViewById(R.id.txtDireccion);
            EditText horaCita = (EditText) findViewById(R.id.txtHora);


            db.insertarLista(tipo, fecha, titulo.getText().toString(), descripcion.getText().toString(),
                    null, null, direccion.getText().toString(),
                    horaCita.getText().toString());
        }


        finish();

    }
}
