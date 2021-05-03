package com.upc.app_comida.ui.alimentos;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
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
import com.upc.app_comida.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;


public class AlimentosFragment extends Fragment {

    EditText txtNombreComida, txtCalorias, txtCategoria;
    Button btnGuardarAlimento;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_alimentos, container, false);
        txtNombreComida = view.findViewById(R.id.txtNombreComida);
        txtCalorias = view.findViewById(R.id.txtCalorias);
        txtCategoria = view.findViewById(R.id.txtCategoria);
        btnGuardarAlimento = view.findViewById(R.id.btnGuardarAlimento);
        btnGuardarAlimento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registrarAlimento();
            }
        });
        return view;
    }

    private void registrarAlimento(){
        try {
            String url = "https://upcrestapi.azurewebsites.net/api/Karmu/Alimentos";
            RequestQueue requestQueue= Volley.newRequestQueue(getContext());
            JSONObject jsonBody=new JSONObject();
            jsonBody.put("id","7");
            jsonBody.put("alimentoid", "7");
            jsonBody.put("titulo", txtNombreComida.getText().toString());
            jsonBody.put("distribucionEnergetica","");
            jsonBody.put("tipo", txtCategoria.getText().toString());
            jsonBody.put("calorias", Integer.valueOf(txtCalorias.getText().toString()));
            final  String mRequestBody=jsonBody.toString();
            StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(getContext(), "Se registró correctamente", Toast.LENGTH_SHORT).show();
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
                            System.out.println(res);
                        } catch (UnsupportedEncodingException e1) {
                            // Couldn't properly decode data to string
                            e1.printStackTrace();
                            System.out.println(e1);
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

}