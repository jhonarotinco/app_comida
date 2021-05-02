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
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;

public class Login_Activity extends AppCompatActivity {
    EditText txt_usuario,txt_contrasena;
    Button btn_ingresar;
    String usuario,contrasena;
    RadioButton rbtn_cliente,rbtn_nutricionista;
    String tipo_usuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            txt_usuario=findViewById(R.id.txt_usuario);
            txt_contrasena=findViewById(R.id.txt_contrasena);
            rbtn_cliente=findViewById(R.id.rbtn_cliente);
            rbtn_nutricionista=findViewById(R.id.rbtn_nutricionista);
            btn_ingresar=findViewById(R.id.btn_ingresar);
            recuperarPreferencias();

            btn_ingresar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    usuario=txt_usuario.getText().toString();
                    contrasena=txt_contrasena.getText().toString();
                    if (rbtn_cliente.isChecked()){
                        tipo_usuario="Cliente";
                    }else if (rbtn_nutricionista.isChecked()){
                        tipo_usuario="Nutricionista";
                    }

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

        try {
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("userid",usuario);
            jsonBody.put("password",contrasena);
            final String mRequestBody = jsonBody.toString();

            StringRequest stringRequest=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    if (!response.isEmpty()){
                        guardarPreferencias();
                        if (tipo_usuario=="Cliente"){
                            Intent intent=new Intent(getApplicationContext(),NavigationActivity.class);
                            startActivity(intent);
                        }else{
                            Intent intent=new Intent(getApplicationContext(),NavigationActivity.class);
                            startActivity(intent);
                        }

                    }else{
                        Toast.makeText(Login_Activity.this, "Usuario o contraseña Incorrecto", Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    NetworkResponse response = error.networkResponse;
                    if (error instanceof ServerError && response != null){
                        try {
                            String res = new String(response.data,
                                    HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                            Toast.makeText(Login_Activity.this, res, Toast.LENGTH_SHORT).show();
                        } catch (UnsupportedEncodingException e1) {
                            // Couldn't properly decode data to string
                            e1.printStackTrace();
                        }
                    }
                }
            }) {
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
            /*@Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros=new HashMap<String,String>();
                parametros.put("userid",usuario);
                parametros.put("password",contrasena);
                return parametros;
            }*/
            };
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //Metodo para guardar preferencias, cuando inicia sesion la proxima vez debe de autocompletarse
    private void guardarPreferencias()
    {
        SharedPreferences preferences=getSharedPreferences("preferencias", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString("usuario",usuario);
        editor.putString("contrasena",contrasena);
        editor.putString("tipo_usuario",tipo_usuario);
        editor.putBoolean("Sesion",true);
        editor.apply();
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