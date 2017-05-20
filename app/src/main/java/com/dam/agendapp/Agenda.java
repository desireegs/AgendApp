package com.dam.agendapp;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class Agenda extends AppCompatActivity {
    private final static int LISTA = 0;

    String meses[] = {"ENERO", "FEBRERO", "MARZO", "ABRIL", "MAYO", "JUNIO", "JULIO", "AGOSTO", "SEPTIEMBRE",
            "OCTUBRE", "NOVIEMBRE", "DICIEMBRE"};
    String dias_semana[] = {"","DOMINGO","LUNES", "MARTES", "MIÉRCOLES", "JUEVES", "VIERNES","SÁBADO"};

    Calendar fecha;

    TextView mes;
    TextView semana;
    TextView dia;
    private ListView lista;

    private BaseDeDatos bd;



    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agenda_layout);
        fecha = Calendar.getInstance();

        mes = (TextView)findViewById(R.id.mes);
        semana = (TextView)findViewById(R.id.semana);
        dia = (TextView)findViewById(R.id.dia);

        bd = new BaseDeDatos(getApplicationContext());
        refrescar();



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
        //Refrescamos el dia
        mes.setText(getMes(fecha));
        semana.setText(getDiaSemana(fecha));
        dia.setText(getDia(fecha));



        //Refrescamos la lista de tareas
        ArrayList<Tarea> datos = bd.recuperaLista(fecha);


            lista = (ListView) findViewById(R.id.lista);
            lista.setAdapter(new Lista_adaptador(this, R.layout.lista_layout, datos) {
                @Override
                public void onEntrada(Object entrada, View view) {
                    if (entrada != null) {



                        if(((Tarea) entrada).getTipo() == 0){

                            TextView texto_titulo = (TextView) view.findViewById(R.id.textView_titulo);
                            if (texto_titulo != null)
                                texto_titulo.setText(((Tarea) entrada).getTitulo());
                        }

                        else if(((Tarea) entrada).getTipo() == 1) {
                            TextView texto_titulo = (TextView) view.findViewById(R.id.textView_titulo);
                            if (texto_titulo != null)
                                texto_titulo.setText(((Tarea) entrada).getTitulo());

                            TextView texto_tel = (TextView) view.findViewById(R.id.textView_telefono);
                            texto_tel.setVisibility(View.VISIBLE);
                            if (texto_tel != null)
                                texto_tel.setText(((Tarea) entrada).getTelefono());

                            TextView texto_email = (TextView) view.findViewById(R.id.textView_email);
                            texto_email.setVisibility(View.VISIBLE);

                            if (texto_email != null)
                                texto_email.setText(((Tarea) entrada).getEmail());

                        }else if(((Tarea) entrada).getTipo() == 2){
                            TextView texto_titulo = (TextView) view.findViewById(R.id.textView_titulo);
                            if (texto_titulo != null)
                                texto_titulo.setText(((Tarea) entrada).getTitulo());

                            TextView texto_hora = (TextView) view.findViewById(R.id.textView_horaCita);
                            texto_hora.setVisibility(View.VISIBLE);

                            if (texto_hora != null)
                                texto_hora.setText(((Tarea) entrada).getHoraCita());

                        }

                        TextView texto_ID = (TextView) view.findViewById(R.id.textView_ID);
                        if (texto_ID != null)
                            texto_ID.setText(Integer.toString(((Tarea) entrada).getId()));
                        CheckBox c = (CheckBox) view.findViewById(R.id.check_completada);
                        c.setTag(((Tarea) entrada).getId());
                        c.setChecked(((Tarea) entrada).getCompletada());
                    }
                }
            });

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                TextView textoID = (TextView) view.findViewById(R.id.textView_ID);
                Tarea t = bd.recuperaTarea(textoID.getText().toString());
                Intent intent = new Intent(getApplicationContext(), Info.class);
                intent.putExtra("Tarea", t);
                startActivityForResult(intent,LISTA);

            }
        });





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
        intent.putExtra("fecha",fecha);
        startActivityForResult(intent, LISTA);
    }

    public void limpiar(View view){
        Boolean res= bd.borrarLista();
        if(res)
            Toast.makeText(getApplicationContext(),
                    "Tareas borradas", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(getApplicationContext(),
                    "No se ha podido eliminar las tareas" ,   Toast.LENGTH_LONG).show();
        refrescar();
    }

    public void tareaCompletada(View view){

        CheckBox c = (CheckBox) view;
        Boolean completada = c.isChecked();
        int id = (Integer) c.getTag();
        bd.updateCompletado(completada, id);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == LISTA) {

            if(resultCode == RESULT_OK){
               refrescar();
            }
            if (resultCode == RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }



}
