package com.upc.app_comida.ui.consulta;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.upc.app_comida.Planes_Activity;
import com.upc.app_comida.R;
import com.upc.app_comida.entidades.Consulta;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class Consul_Respo_Fragment extends Fragment {

    EditText txt_pregunta_c,txt_respuesta_n;
    TextView txv_nutricionista,txv_consulta_c;
    String pregunta,respuesta;
    String Usuario,tipo_usuario;
    ImageView imgresponder;
    Button btn_grabar_consulta,btn_grabar_respuesta,btn_nuevo_consulta;

    int condi;
    public Consul_Respo_Fragment() {
        // Required empty public constructor
    }

    public static Consul_Respo_Fragment newInstance(String param1, String param2) {
        Consul_Respo_Fragment fragment = new Consul_Respo_Fragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_consul__respo_, container, false);
        txt_pregunta_c=view.findViewById(R.id.txt_consulta_cli);
        txt_respuesta_n=view.findViewById(R.id.txt_respuesta_nutri);
        btn_grabar_consulta=view.findViewById(R.id.btn_grabar_consulta);
        btn_grabar_respuesta=view.findViewById(R.id.btn_responder_consula);
        txv_nutricionista=view.findViewById(R.id.txv_nutricionista);
        txv_consulta_c=view.findViewById(R.id.txv_consulta_c);
        //Configuramos para que no sea visible eel boton
        //txt_pregunta_c.setText(Consulta.);
        recuperarPreferencias();
        if (tipo_usuario=="Cliente"){
            btn_grabar_respuesta.setVisibility(View.GONE);
            txt_respuesta_n.setVisibility(View.GONE);
            txv_nutricionista.setVisibility(View.GONE);
        }else{
            btn_grabar_consulta.setVisibility(View.GONE);
            txt_pregunta_c.setVisibility(View.GONE);
            txv_consulta_c.setVisibility(View.GONE);
        }
        //btn_grabar_consulta.setVisibility(View.GONE);
        btn_grabar_consulta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pregunta=txt_pregunta_c.getText().toString();

                String id_usuario=Usuario;
                if (!pregunta.isEmpty()){
                    pv_grabar_consulta("https://upcrestapi.azurewebsites.net/Usuarios/"+id_usuario+"/Consultas");
                    Navigation.findNavController(view).navigate(R.id.nav_consultas);
                }else   {
                    Toast.makeText(getContext(), "Debe de ingresar su consulta", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_grabar_respuesta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                respuesta=txt_respuesta_n.getText().toString();
                int id_consulta=3;
                String id_usuario=Usuario;
                if (!respuesta.isEmpty()){
                    pv_responder("https://upcrestapi.azurewebsites.net/Usuarios/"+id_usuario+"/Consultas");
                    Navigation.findNavController(view).navigate(R.id.nav_consultas);
                }else
                {
                    Toast.makeText(getContext(), "Debe de ingresar su respuesta", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }
    //Metodo para responder una consulta
    private void pv_responder(String url){
        try {
            RequestQueue requestQueue=Volley.newRequestQueue(getContext());
            JSONObject jsonBody=new JSONObject();
            jsonBody.put("id",2);
            jsonBody.put("pregunta","");
            jsonBody.put("respuesta",respuesta);
            jsonBody.put("estado","Respondido");
            final  String mRequestBody=jsonBody.toString();
            StringRequest stringRequest=new StringRequest(Request.Method.PUT, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(getContext(), "Respuesta Registrado Correctamente", Toast.LENGTH_SHORT).show();
                    //Log.d("prueba","correcto" +jsonBody.toString());
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    NetworkResponse response = error.networkResponse;
                    if (error instanceof ServerError && response != null){
                        try {
                            String res = new String(response.data,
                                    HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                            Toast.makeText(getContext(), res, Toast.LENGTH_SHORT).show();
                        } catch (UnsupportedEncodingException e1) {
                            // Couldn't properly decode data to string
                            e1.printStackTrace();
                        }
                    }
                }
            }){
                @Override
                public String getBodyContentType() {return "application/json; charset=utf-8";}

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


    private void pv_grabar_consulta(String url){
            try {
                RequestQueue requestQueue=Volley.newRequestQueue(getContext());
                JSONObject jsonBody=new JSONObject();
                jsonBody.put("id",4);
                jsonBody.put("pregunta",pregunta);
                jsonBody.put("respuesta","");
                jsonBody.put("estado","Pendiente");
                final  String mRequestBody=jsonBody.toString();
                StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getContext(), "Consulta Registrado Correctamente", Toast.LENGTH_SHORT).show();
                        Log.d("prueba","correcto" +jsonBody.toString());
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        NetworkResponse response = error.networkResponse;
                        if (error instanceof ServerError && response != null){
                            try {
                                String res = new String(response.data,
                                        HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                                Toast.makeText(getContext(), res, Toast.LENGTH_SHORT).show();
                            } catch (UnsupportedEncodingException e1) {
                                // Couldn't properly decode data to string
                                e1.printStackTrace();
                            }
                        }
                    }
                }){
                    @Override
                    public String getBodyContentType() {return "application/json; charset=utf-8";}

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

    //Meotod para recuperar dichas preferencias
    private void  recuperarPreferencias()
    {
        SharedPreferences preferences=this.getActivity().getSharedPreferences("preferencias", Context.MODE_PRIVATE);
        Usuario=preferences.getString("usuario","ejemplo@dominio.com");
        tipo_usuario=preferences.getString("tipo_usuario","ejemplo@dominio.com");
    }
}