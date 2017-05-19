package com.dam.agendapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

public class Info extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        Tarea t = (Tarea) intent.getExtras().getSerializable("Tarea");

        int tipo = t.getTipo();
        EditText txtTitulo = (EditText) findViewById(R.id.txtTitulo);
        EditText txtDescripcion = (EditText) findViewById(R.id.txtDescripcion);

        if(tipo == 0) {
            setContentView(R.layout.tarea_layout);
        }

        else if(tipo == 1) {
            setContentView(R.layout.contactar_layout);
            EditText txtTelefono = (EditText) findViewById(R.id.txtTelefono);
            txtTelefono.setText(t.getTelefono());
            EditText txtEmail = (EditText) findViewById(R.id.txtEmail);
            txtEmail.setText(t.getEmail());
        }

        else if(tipo == 2) {
            setContentView(R.layout.cita_layout);
            EditText txtDireccion = (EditText) findViewById(R.id.txtDireccion);
            txtDireccion.setText(t.getDireccion());
            EditText txtHora = (EditText) findViewById(R.id.txtHora);
            txtHora.setText(t.getHoraCita());
        }

        txtTitulo.setText(t.getTitulo());
        txtDescripcion.setText(t.getDescripcion());
    }
}
