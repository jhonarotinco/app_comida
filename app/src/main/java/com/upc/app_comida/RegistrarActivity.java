package com.upc.app_comida;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

public class RegistrarActivity extends AppCompatActivity {
    TextView mtv;
    Button mbtn;
    Calendar cale;
    DatePickerDialog dtp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

        mtv=(TextView)findViewById(R.id.txt_fecha);
        mbtn=(Button)findViewById(R.id.btn_fecha);

        mbtn.setOnClickListener(new View.OnClickListener(){
            @Override
           public void onClick(View view){
               cale=Calendar.getInstance();
               int day= cale.get(Calendar.DAY_OF_MONTH);
               int mont= cale.get(Calendar.MONTH);
               int year= cale.get(Calendar.YEAR);
               dtp=new DatePickerDialog(RegistrarActivity.this,new DatePickerDialog.OnDateSetListener(){
                   @Override
                   public void onDateSet(DatePicker datePicker,int myear,int mmont,int mday){
                        mtv.setText(mday+"/"+(mmont+1)+"/"+myear);
                   }
               },day,mont,year);
               dtp.show();
           }
        });
    }
}