package com.dam.agendapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class Evento extends AppCompatActivity {


    private Button recordatorio;
    private Button bguardar;

    private BaseDeDatos bd;
    private int tipo;
    private Calendar fecha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        tipo = intent.getIntExtra(EXTRA_MESSAGE, 0);
        fecha = (Calendar) intent.getSerializableExtra("fecha");

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

        EditText titulo = (EditText) findViewById(R.id.txtTitulo);
        EditText descripcion = (EditText) findViewById(R.id.txtDescripcion);
        intent.putExtra("Titulo", titulo.getText().toString());
        intent.putExtra("Descripcion", descripcion.getText().toString());

        startActivity(intent);
    }

    public void guardar(){
        bd = new BaseDeDatos(getApplicationContext());

        EditText titulo = (EditText) findViewById(R.id.txtTitulo);
        EditText descripcion = (EditText) findViewById(R.id.txtDescripcion);
        Boolean res = false;

        if(tipo == 0){
            Log.d("TAG","tipo 0");
            res=bd.insertarLista(tipo, fecha, titulo.getText().toString(), descripcion.getText().toString(),
                    null, null, null, null);
        }else if(tipo == 1) {
            Log.d("TAG","tipo 1");
            EditText telefono = (EditText) findViewById(R.id.txtTelefono);
            EditText email = (EditText) findViewById(R.id.txtEmail);
            Log.d("TAG",email.getText().toString());
            Log.d("TAG",telefono.getText().toString());

            res = bd.insertarLista(tipo, fecha, titulo.getText().toString(), descripcion.getText().toString(),
                    telefono.getText().toString(), email.getText().toString(), null,null );
        }
        else if(tipo == 2){
            Log.d("TAG","tipo 2");
            EditText direccion = (EditText) findViewById(R.id.txtDireccion);
            EditText horaCita = (EditText) findViewById(R.id.txtHora);


            res =bd.insertarLista(tipo, fecha, titulo.getText().toString(), descripcion.getText().toString(),
                    null, null, direccion.getText().toString(),
                    horaCita.getText().toString());


        }

        if(res)
            Toast.makeText(getApplicationContext(),
                    "Evento añadido correctamente", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(getApplicationContext(),
                    "No se ha podido guardar el evento" ,   Toast.LENGTH_LONG).show();

        Intent returnintent = new Intent();
        setResult(RESULT_OK, returnintent);
        finish();

    }
}
