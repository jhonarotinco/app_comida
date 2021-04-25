package com.upc.app_comida.ui.cliente;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.upc.app_comida.R;
import com.upc.app_comida.entidades.Cliente;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.*;

public class ClientesFragment extends Fragment {
    ListView lstcliente;
    public ClientesFragment() {
        // Required empty public constructor
    }

    public static ClientesFragment newInstance(String param1, String param2) {
        ClientesFragment fragment = new ClientesFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_clientes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lstcliente=view.findViewById(R.id.lstcliente);
        listar_cliente();
    }

    public void  listar_cliente(){
        String url="";
        url="https://upcrestapi.azurewebsites.net/Clientes";
        StringRequest peticion=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ArrayList<Cliente>lista=new ArrayList<>();
                try {
                    JSONArray jsonArray=new JSONArray(response);
                    for (int i=0;i<jsonArray.length();i++){
                     JSONObject objeto=jsonArray.getJSONObject(i);
                     Cliente cliente=new Cliente(objeto.getString("id"),objeto.getString("userid"),objeto.getString("nombres"),objeto.getString("deseo"));
                     lista.add(cliente);
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        RequestQueue cola= Volley.newRequestQueue(getContext());
        cola.add(peticion);
    }
}