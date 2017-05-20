package com.dam.agendapp;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class Notificacion extends AppCompatActivity {
    private EditText t4, t3,t5,t6,t7;
    private BaseDeDatos admin;
    private SQLiteDatabase bd;
    private ContentValues registro;
    private EditText tvDisplayDate;
    private DatePicker dpResult;
    private Button btnChangeDate;
    // date
    private int year;
    private int month;
    private int day;

    static final int DATE_DIALOG_ID = 999;
    // hora
    private int minute;
    private int hour;
    private TimePicker timePicker;
    private TextView textViewTime;
    private Button button;
    private static final int TIME_DIALOG_ID = 998;
    Calendar calendario = Calendar.getInstance();
    int hora, min,dia,mes,ano,hora11;
    String cadenaF, cadenaH,fecha_sistema,hora_sistema;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notificacion_layout);
        admin = new BaseDeDatos(this);
        bd = admin.getWritableDatabase();
        dia = calendario.get(Calendar.DAY_OF_MONTH);
        mes = calendario.get(Calendar.MONTH)+1;
        ano = calendario.get(Calendar.YEAR);
        hora = calendario.get(Calendar.HOUR_OF_DAY);
        min = calendario.get(Calendar.MINUTE);
        fecha_sistema=mes+"-"+dia+"-"+ano+" ";
        hora_sistema = hora+":"+min;
        setCurrentDateOnView();
        addListenerOnButton();
        // hora
        setCurrentTimeOnView();

        Intent intent = getIntent();
        t3 = (EditText) findViewById(R.id.editText21);
        t3.setText(intent.getStringExtra("Titulo"));
        t5= (EditText) findViewById(R.id.editText22);
        t5.setText(intent.getStringExtra("Descripcion"));
        t6= (EditText) findViewById(R.id.editText23);
        t7= (EditText) findViewById(R.id.editText24);
        servicio();
    }
    public void servicio() {
        Intent intent = new Intent(getApplicationContext(), RecibeNotificacion.class);
        final PendingIntent pIntent = PendingIntent.getBroadcast(this, RecibeNotificacion.REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        long firstMillis = System.currentTimeMillis(); //first run of alarm is immediate // aranca la palicacion
        int intervalMillis = 1 * 3 * 1000; //3 segundos
        AlarmManager alarm = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP, firstMillis, intervalMillis, pIntent);
    }


    public void llenar111(View view) {
        BaseDeDatos admin = new BaseDeDatos(this);
        SQLiteDatabase bd = admin.getReadableDatabase();
        bd = admin.getWritableDatabase();
        registro = new ContentValues();
        registro.put("encabezado", t3.getText().toString());
        registro.put("mensaje", t5.getText().toString());//nombre del campo
        registro.put("fecha", t6.getText().toString());
        registro.put("hora", t7.getText().toString());
        bd.insert("alarma", null, registro);//nombre de la tabla
        bd.close();
        t3.setText("");
        t5.setText("");
        t6.setText("");
        t7.setText("");
        Toast.makeText(this, "Notificación registrada", Toast.LENGTH_LONG).show();
        finish();
    }
    public void setCurrentTimeOnView() {

        textViewTime = (EditText) findViewById(R.id.editText24);

        timePicker = (TimePicker) findViewById(R.id.timePicker);
        final Calendar c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);
        timePicker.setCurrentHour(hour);
        timePicker.setCurrentMinute(minute);



    }

    public void addListenerOnButton() {

        btnChangeDate = (Button) findViewById(R.id.button28);
        btnChangeDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showDialog(DATE_DIALOG_ID);

            }

        });
        // hora

        button = (Button) findViewById(R.id.button29);
        button.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                showDialog(TIME_DIALOG_ID);

            }

        });

    }
    // hora

    public void setCurrentDateOnView() {

        tvDisplayDate = (EditText) findViewById(R.id.editText23);
        dpResult = (DatePicker) findViewById(R.id.datePicker);

        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        // set current date into textview
        tvDisplayDate.setText(new StringBuilder()
                // Month is 0 based, just add 1
                .append(month + 1).append("-").append(day).append("-")
                .append(year).append(" "));

        // set current date into datepicker
        dpResult.init(year, month, day, null);

    }



    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                // set date picker as current date
                return new DatePickerDialog(this, datePickerListener,
                        year, month,day);
            case TIME_DIALOG_ID:



                return new TimePickerDialog(this, timePickerListener, hour, minute,false);

        }
        return null;
    }
    // hora
    private TimePickerDialog.OnTimeSetListener timePickerListener =  new TimePickerDialog.OnTimeSetListener() {

        public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {

            hour = selectedHour;

            minute = selectedMinute;
            textViewTime.setText(new StringBuilder().append(padding_str(hour)).append(":").append(padding_str(minute)));
            timePicker.setCurrentHour(hour);
            timePicker.setCurrentMinute(minute);

        }



    };


    private static String padding_str(int c) {

        if (c >= 10)
            return String.valueOf(c);

        else

            return "0" + String.valueOf(c);
    }

    // hora

    private DatePickerDialog.OnDateSetListener datePickerListener
            = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            year = selectedYear;
            month = selectedMonth;
            day = selectedDay;

            // set selected date into textview
            tvDisplayDate.setText(new StringBuilder().append(month + 1)
                    .append("-").append(day).append("-").append(year)
                    .append(" "));

            // set selected date into datepicker also
            dpResult.init(year, month, day, null);

        }
    };

}

