package com.upc.app_comida;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;

public class Login_Activity extends AppCompatActivity {
    EditText txt_usuario,txt_contrasena;
    Button btn_ingresar;
    String usuario,contrasena;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            txt_usuario=findViewById(R.id.txt_usuario);
            txt_contrasena=findViewById(R.id.txt_contrasena);
            btn_ingresar=findViewById(R.id.btn_ingresar);
            recuperarPreferencias();

            btn_ingresar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    usuario=txt_usuario.getText().toString();
                    contrasena=txt_contrasena.getText().toString();

                    if (!usuario.isEmpty() && !contrasena.isEmpty())
                    {
                        validar_usuario("https://upcrestapi.azurewebsites.net/api/Karmu/authenticate");
                    }else
                    {
                        Toast.makeText(Login_Activity.this, "No se permiten campos vacíos", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }

    private void validar_usuario(String URL)
    {
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (!response.isEmpty()){
                    guardarPreferencias();
                    Intent intent=new Intent(getApplicationContext(),NavigationActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(Login_Activity.this, "Usuario o contraseña Incorrecto", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Login_Activity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros=new HashMap<String,String>();
                parametros.put("usuario",usuario);
                parametros.put("contrasena",contrasena);
                return parametros;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    //Metodo para guardar preferencias, cuando inicia sesion la proxima vez debe de autocompletarse
    private void guardarPreferencias()
    {
        SharedPreferences preferences=getSharedPreferences("preferencias", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString("usuario",usuario);
        editor.putString("contrasena",contrasena);
        editor.putBoolean("Sesion",true);
        editor.commit();
    }

    //Meotod para recuperar dichas preferencias
    private void  recuperarPreferencias()
    {
        SharedPreferences preferences=getSharedPreferences("preferencias",Context.MODE_PRIVATE);
        txt_usuario.setText(preferences.getString("usuario","ejemplo@dominio.com"));
        txt_contrasena.setText(preferences.getString("contrasena","123456"));
    }
    public void onLoginClick(View View ){
        startActivity(new Intent(this,NavigationActivity.class));
    }

    public void onRegisterClick(View View ){
        startActivity(new Intent(this,RegistrarActivity.class));
    }
}