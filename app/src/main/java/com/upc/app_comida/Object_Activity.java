package com.upc.app_comida;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.travijuu.numberpicker.library.NumberPicker;

public class Object_Activity extends AppCompatActivity {
    //Declaramos variables para recepcionar losa daros enviamos del  activity Registrar
    String correo,contrasena,nombre,apellido,fecha_nac,talla,peso,genero;

    //Datos del formulario actual
    EditText txt_alergia;
    RadioButton rbtn_ganar_peso,rbtn_perder_peso;
    String qstr_objetivo,alergia;
    NumberPicker txt_cant_kilo;
    String cant_kilo;
    Button btn_siguiente_objet;

    //Datos del  Plan
    String qstr_plan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_object_);
        recibirDatos();
        pv_enviar_datos();
    }
    private  void  recibirDatos(){
        Bundle extras=getIntent().getExtras();
        correo=extras.getString("correo");
        contrasena=extras.getString("contrasena");
        nombre=extras.getString("nombre");
        apellido=extras.getString("apellido");
        fecha_nac=extras.getString("fecha_nac");
        talla=extras.getString("talla");
        peso=extras.getString("peso");
        genero=extras.getString("genero");
    }

    //Metodo para enviar al formulario final
    private  void pv_enviar_datos(){
        rbtn_ganar_peso=findViewById(R.id.rbtn_ganar_peso);
        rbtn_perder_peso=findViewById(R.id.rbtn_perder_peso);
        txt_cant_kilo=(NumberPicker) findViewById(R.id.txt_cant_kilo);
        txt_alergia=findViewById(R.id.txt_alergia);
        btn_siguiente_objet=findViewById(R.id.btn_siguiente_object);
        btn_siguiente_objet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rbtn_ganar_peso.isChecked()){
                    qstr_objetivo="ganar peso";
                }else{
                    qstr_objetivo="perder peso";
                }
                cant_kilo= String.valueOf(txt_cant_kilo.getValue());
                alergia=txt_alergia.getText().toString();
                Intent intent=new Intent(Object_Activity.this,Planes_Activity.class);
                intent.putExtra("qstr_objetivo",qstr_objetivo);
                intent.putExtra("cant_kilo",cant_kilo);
                intent.putExtra("alergia",alergia);
                //Al igual enviamos los datos del activity anterior
                // correo,contrasena,nombre,apellido,fecha_nac,talla,peso,genero;
                intent.putExtra("correo",correo);
                intent.putExtra("contrasena",contrasena);
                intent.putExtra("nombre",nombre);
                intent.putExtra("apellido",apellido);
                intent.putExtra("fecha_nac",fecha_nac);
                intent.putExtra("talla",talla);
                intent.putExtra("peso",peso);
                intent.putExtra("genero",genero);
                startActivity(intent);
            }
        });
    }
    //Metodo para enviar los datos
    //public void onPlanesClick(View View ){
      //  startActivity(new Intent(this,Planes_Activity.class));
    //}
}