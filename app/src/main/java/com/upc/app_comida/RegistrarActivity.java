package com.upc.app_comida;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class RegistrarActivity extends AppCompatActivity {
    TextView mtv;
    Button mbtn;
    Calendar cale;
    DatePickerDialog dtp;
    Button btn_siguiente;
    //Declaramos los valores para enviar al segundo formulario
    EditText txt_correo,txt_contrasena,txt_nombre,txt_apellido,txt_fecha_nac,txt_talla,txt_peso;
    String correo,contrasena,nombre,apellido,fecha_nac,talla,peso,genero;
    Spinner cbo_genero;
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

        pv_enviar_datos();
    }

    /*public void onObjectClick(View View ){
        startActivity(new Intent(this,Object_Activity.class));
    }*/

    private void pv_enviar_datos(){
        txt_correo=findViewById(R.id.txt_correo);
        txt_contrasena=findViewById(R.id.txt_contrasena);
        txt_nombre=findViewById(R.id.txt_nombre);
        txt_apellido=findViewById(R.id.txt_apellido);
        txt_fecha_nac=findViewById(R.id.txt_fecha);
        cbo_genero=(Spinner)findViewById(R.id.cbo_genero);
        txt_talla=findViewById(R.id.txt_talla);
        txt_peso=findViewById(R.id.txt_peso);
        btn_siguiente=findViewById(R.id.btn_siguiente);
        btn_siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                correo=txt_correo.getText().toString();
                contrasena=txt_contrasena.getText().toString();
                nombre=txt_nombre.getText().toString();
                apellido=txt_apellido.getText().toString();
                fecha_nac=txt_fecha_nac.getText().toString();
                genero=cbo_genero.getSelectedItem().toString();
                talla=txt_talla.getText().toString();
                peso=txt_peso.getText().toString();
                Intent intent=new Intent(RegistrarActivity.this,Object_Activity.class);
                //Enviamos los datos al siguiente formulario para juntar con los demás
                intent.putExtra("correo",correo);
                intent.putExtra("contrasena",contrasena);
                intent.putExtra("nombre",nombre);
                intent.putExtra("apellido",apellido);
                intent.putExtra("fecha_nac",fecha_nac);
                intent.putExtra("genero",genero);
                intent.putExtra("talla",talla);
                intent.putExtra("peso",peso);
                startActivity(intent);
            }
        });
    }
}