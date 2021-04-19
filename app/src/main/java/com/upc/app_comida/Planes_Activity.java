package com.upc.app_comida;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Planes_Activity extends AppCompatActivity {
    //Datos del usuario
    EditText txt_correo,txt_contrasena,txt_nombre,txt_apellido,txt_fecha,txt_talla,txt_peso;
    String correo,contrasena,nombre,apellido,fecha,talla,peso;
    Spinner cbo_genero;
    String genero;
    //Datos del Objetivo
    RadioButton rbtn_ganar_peso,rbtn_perder_peso;
    String qstr_ganar_o_perder_peso;
    EditText txt_alergia;
    String alergia;
    NumberPicker txt_cant_kilo;
    Double cant_kilo;
    //Datos del plan
    RadioButton rbtn_plan_gratuito,rbtn_premiun;
    String plan;
    Button btn_guardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planes_);

    //Referenciamos
        //Referencias del activity Register
        txt_correo=findViewById(R.id.txt_correo);
        txt_contrasena=findViewById(R.id.txt_contrasena);
        txt_nombre=findViewById(R.id.txt_nombre);
        txt_apellido=findViewById(R.id.txt_apellido);
        txt_fecha=findViewById(R.id.txt_fecha);
        cbo_genero= (Spinner)findViewById(R.id.cbo_genero);
        txt_talla=findViewById(R.id.txt_talla);
        txt_peso=findViewById(R.id.txt_peso);
        //DAatos del formulario de Objetivos
        rbtn_ganar_peso=(RadioButton)findViewById(R.id.rbtn_ganar_peso);
        rbtn_perder_peso=(RadioButton)findViewById(R.id.rbt_perder_peso);
        txt_alergia=findViewById(R.id.txt_alergia);
        txt_cant_kilo=findViewById(R.id.txt_cant_kilo);
        //Datos del Plan
        rbtn_plan_gratuito=(RadioButton)findViewById(R.id.rbtn_gratuito);
        rbtn_premiun=(RadioButton)findViewById(R.id.rbtn_premium);

        //Referenciamos el boton de grabar
        btn_guardar=findViewById(R.id.btn_guardar);

        btn_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                correo=txt_correo.getText().toString();
                contrasena=txt_contrasena.getText().toString();
                nombre=txt_nombre.getText().toString();
                apellido=txt_apellido.getText().toString();
                fecha=txt_fecha.getText().toString();
                genero=cbo_genero.getSelectedItem().toString();
                talla=txt_talla.getText().toString();
                peso=txt_peso.getText().toString();
                alergia=txt_alergia.getText().toString();
                cant_kilo= Double.valueOf(txt_cant_kilo.getValue());
                if (rbtn_ganar_peso.isChecked())
                {
                    qstr_ganar_o_perder_peso="ganar";
                }else{
                    qstr_ganar_o_perder_peso="perder";
                }

                if (rbtn_plan_gratuito.isChecked())
                {
                    plan="gratis";
                }else
                {
                    plan="premium";
                }
                //Verificamos que no eté vacio
                if (!correo.isEmpty() || !contrasena.isEmpty() || !nombre.isEmpty() || !apellido.isEmpty() || !fecha.isEmpty() || !talla.isEmpty() || !peso.isEmpty() || !alergia.isEmpty())
                {
                    pv_guardar("https://upcrestapi.azurewebsites.net/Usuarios");
                }else
                {
                    Toast.makeText(Planes_Activity.this, "No se permiten campos vacíos ¡Verifique!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    //Metodo para ejecutar el servicio
    private void pv_guardar(String URL )
    {
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(Planes_Activity.this, "Registrado Correctamente", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Planes_Activity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>parametros=new HashMap<String,String>();
                parametros.put("id",correo);
                parametros.put("userid",correo);
                parametros.put("password",contrasena);
                parametros.put("nombres",nombre);
                parametros.put("apellidos",apellido);
                parametros.put("nacimiento",fecha);
                //parametros.put("genero",genero);//Agregar este valor en el WS
                parametros.put("talla",talla);
                parametros.put("peso",peso);
                parametros.put("deseo",qstr_ganar_o_perder_peso);//Debe de obtener peso, solo del radio button seleccionado
                parametros.put("cantidadKilos", String.valueOf(cant_kilo));
                parametros.put("cantidadSemanas","2");//falta objeto
                parametros.put("alergias",alergia);
                parametros.put("plan",txt_correo.getText().toString());
                parametros.put("rol","Usuario");//Por defecto solo se creará el ususario, no existe un activity para registrar al nutricionista
                return parametros;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}