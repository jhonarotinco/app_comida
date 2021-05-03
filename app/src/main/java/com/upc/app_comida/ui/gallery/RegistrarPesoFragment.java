package com.upc.app_comida.ui.gallery;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
import com.upc.app_comida.Login_Activity;
import com.upc.app_comida.NavigationActivity;
import com.upc.app_comida.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;


public class RegistrarPesoFragment extends Fragment {

    String Usuario, peso;
    EditText edit_peso;
    Button btn_registrar_peso;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_registrar_peso, container, false);
        recuperarPreferencias();
        edit_peso = view.findViewById(R.id.txt_peso);
        btn_registrar_peso = view.findViewById(R.id.btn_registrar_peso_rest);
        btn_registrar_peso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registrarPeso();
            }
        });
        return view;
    }

    private void  recuperarPreferencias(){
        SharedPreferences preferences=this.getActivity().getSharedPreferences("preferencias", Context.MODE_PRIVATE);
        Usuario=preferences.getString("usuario","ejemplo@dominio.com");
    }

    private void registrarPeso(){
        String idUsuario_request = Usuario.replaceAll("@", "%40");
        Integer peso = Integer.valueOf(edit_peso.getText().toString());
        String url = "https://upcrestapi.azurewebsites.net/Usuarios/"+idUsuario_request+"/Historial";
        try {
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("fecha", "3/05/2021");
            jsonBody.put("peso", peso);
            final String mRequestBody = jsonBody.toString();

            StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(getContext(), "El Peso se registró correctamente", Toast.LENGTH_SHORT).show();
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
            };
            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
            requestQueue.add(stringRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}