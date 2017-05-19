package com.dam.agendapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class Info extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detalle_layout);
        Log.d("TAG","En clase info");

        Intent intent = getIntent();
        Tarea t = (Tarea) intent.getExtras().getSerializable("Tarea");

        Log.d("TAG","Tarea recuperada del intent");

        int tipo = t.getTipo();

        EditText txtTitulo = (EditText) findViewById(R.id.txtTitulo);
        EditText txtDescripcion = (EditText) findViewById(R.id.txtDescripcion);
        txtTitulo.setText(t.getTitulo());
        txtDescripcion.setText(t.getDescripcion());

        if(tipo == 1) {
            EditText txtTelefono = (EditText) findViewById(R.id.txtTelefono);
            txtTelefono.setVisibility(View.VISIBLE);
            txtTelefono.setText(t.getTelefono());
            EditText txtEmail = (EditText) findViewById(R.id.txtEmail);
            txtEmail.setVisibility(View.VISIBLE);
            txtEmail.setText(t.getEmail());

        }

        else if(tipo == 2) {
            EditText txtDireccion = (EditText) findViewById(R.id.txtDireccion);
            txtDireccion.setText(t.getDireccion());
            txtDireccion.setVisibility(View.VISIBLE);
            EditText txtHora = (EditText) findViewById(R.id.txtHora);
            txtHora.setText(t.getHoraCita());
            txtHora.setVisibility(View.VISIBLE);

        }




    }
}
