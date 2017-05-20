package com.dam.agendapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Info extends AppCompatActivity {
    Tarea t;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_layout);

        Intent intent = getIntent();
        t = (Tarea) intent.getExtras().getSerializable("Tarea");
        int tipo = t.getTipo();

        EditText ed_titulo = (EditText) findViewById(R.id.ed_titulo);
        EditText ed_descripcion = (EditText) findViewById(R.id.ed_descripcion);
        ed_titulo.setText(t.getTitulo());
        ed_descripcion.setText(t.getDescripcion());

        if(tipo == 1) {
            EditText ed_Telefono = (EditText) findViewById(R.id.ed_Telefono);
            ed_Telefono.setVisibility(View.VISIBLE);
            ed_Telefono.setText(t.getTelefono());
            EditText ed_Email = (EditText) findViewById(R.id.ed_Email);
            ed_Email.setVisibility(View.VISIBLE);
            ed_Email.setText(t.getEmail());

            TextView tv = (TextView) findViewById(R.id.tv_telefono);
            tv.setVisibility(View.VISIBLE);
            tv = (TextView) findViewById(R.id.tv_email);
            tv.setVisibility(View.VISIBLE);
        }

        else if(tipo == 2) {
            EditText ed_Direccion = (EditText) findViewById(R.id.ed_Direccion);
            ed_Direccion.setText(t.getDireccion());
            ed_Direccion.setVisibility(View.VISIBLE);
            EditText ed_Hora = (EditText) findViewById(R.id.ed_Hora);
            ed_Hora.setText(t.getHoraCita());
            ed_Hora.setVisibility(View.VISIBLE);

            TextView tv = (TextView) findViewById(R.id.tv_hora);
            tv.setVisibility(View.VISIBLE);
            tv = (TextView) findViewById(R.id.tv_direccion);
            tv.setVisibility(View.VISIBLE);

        }

    }

    public void modificar(View view){
        Log.d("TAG", "eSTAMOS EN MODIFICAR");
        EditText et = (EditText) findViewById(R.id.ed_titulo);
        Log.d("TAG", "eDIT TEXT COGIDO: " + et);
        t.setTitulo(et.getText().toString());
        Log.d("TAG", "SET TITULO");
        et = (EditText) findViewById(R.id.ed_descripcion);
        t.setDescripcion(et.getText().toString());

        if(t.getTipo() == 1){
            et = (EditText) findViewById(R.id.ed_Telefono);
            t.setTelefono(et.getText().toString());
            et = (EditText) findViewById(R.id.ed_Email);
            t.setEmail(et.getText().toString());
        }else if(t.getTipo() == 2) {
            et = (EditText) findViewById(R.id.ed_Hora);
            t.setHora(et.getText().toString());
            et = (EditText) findViewById(R.id.ed_Direccion);
            t.setDireccion(et.getText().toString());
        }

        BaseDeDatos bd = new BaseDeDatos(getApplicationContext());
        Log.d("TAG", "Vamos a modificar la tarea");
        boolean res = bd.modificarTarea(t);
        if(res)
            Toast.makeText(getApplicationContext(),
                    "Tarea Modificada", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(getApplicationContext(),
                    "No se ha podido modificar la tarea" ,   Toast.LENGTH_LONG).show();

        Intent returnintent = new Intent();
        setResult(RESULT_OK, returnintent);
        finish();
    }

}
