package com.upc.app_comida;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class Planes_Activity extends AppCompatActivity {
    String correo,contrasena,nombre,apellido,fecha_nac,genero,talla,peso;
    String qstr_objetivo,alergia;
    String cant_kilo;
    RadioButton rbtn_gratuito,rbtn_premium;
    String qstr_plan;
    Button btn_grabar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planes_);
        //Referenciamos los objetos
        recibirDatos();
        rbtn_gratuito=findViewById(R.id.rbtn_gratuito);
        rbtn_premium=findViewById(R.id.rbtn_premium);
        btn_grabar=findViewById(R.id.btn_guardar);
        btn_grabar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rbtn_gratuito.isChecked()){
                    qstr_plan="Plan gratuito";
                }else{
                    qstr_plan="Plan premium";
                }

                if(!qstr_plan.isEmpty()){
                    pv_grabar("https://upcrestapi.azurewebsites.net/Usuarios");
                }else{
                    Toast.makeText(Planes_Activity.this, "Debe seleccionar un plan", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    void pv_grabar(String URL){
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            JSONObject jsonBody =new JSONObject();
            //final org.json.JSONObject jsonObject=new org.json.JSONObject();

            jsonBody .put("id",correo);
            jsonBody .put("userid",correo);
            jsonBody .put("nombres",nombre);
            jsonBody .put("apellidos",apellido);
            jsonBody .put("password",contrasena);
            jsonBody .put("rol","Usuario");
            jsonBody .put("talla",Integer.parseInt( talla));//talla
            jsonBody .put("peso",Integer.parseInt(peso));//peso
            jsonBody .put("nacimiento","30 de abril");//fecha_nac
            jsonBody .put("deseo",qstr_objetivo);
            jsonBody .put("cantidadKilos", Integer.parseInt(cant_kilo));//cant_kilo
            jsonBody .put("cantidadSemanas",2);//agregar en el activity
            jsonBody .put("alergias",alergia);
            jsonBody .put("plan",qstr_plan);
            //jsonObject.put("",genero); Agregar en el WS
            final String mRequestBody=jsonBody .toString();
            StringRequest stringRequest=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(Planes_Activity.this, "Registrado Correctamente", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(Planes_Activity.this,NavigationActivity.class);
                    startActivity(intent);
                    //Log.d("test","satisfactorio:"+response);
                    Log.d("test2","satisfactorio:"+jsonBody.toString());
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    NetworkResponse response = error.networkResponse;
                    if (error instanceof ServerError && response != null){
                        try {
                            String res = new String(response.data,
                                    HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                            Toast.makeText(Planes_Activity.this, res, Toast.LENGTH_SHORT).show();
                        } catch (UnsupportedEncodingException e1) {
                            // Couldn't properly decode data to string
                            e1.printStackTrace();
                        }
                    }
                }
            })
            {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return mRequestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                        return null;
                    }
                }

                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    String responseString = "";
                    if (response != null) {
                        responseString = String.valueOf(response.statusCode);
                    }
                    return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                }
            };
            requestQueue.add(stringRequest);
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    private  void  recibirDatos(){
        Bundle extras=getIntent().getExtras();
        //recibimos losa datos del formulario usuario
        correo=extras.getString("correo");
        contrasena=extras.getString("contrasena");
        nombre=extras.getString("nombre");
        apellido=extras.getString("apellido");
        fecha_nac=extras.getString("fecha_nac");
        talla=extras.getString("talla");
        peso=extras.getString("peso");
        genero=extras.getString("genero");
        //Recibimos datos del activity objetivos
        qstr_objetivo=extras.getString("qstr_objetivo");
        alergia=extras.getString("alergia");
        cant_kilo= extras.getString("cant_kilo");
    }
}