package com.upc.app_comida.ui.cliente;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.upc.app_comida.ui.home.CustomAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.*;

public class ClientesFragment extends Fragment {
    ArrayList<Cliente> listaclientes=new ArrayList<>();
    RecyclerView recyclerView;
    ClienteAdapter clienteAdapter;
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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_clientes, container, false);
        recyclerView=view.findViewById(R.id.recyclerListacliente);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        listar_cliente();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

    }

    private void listar_cliente(){
        String url="";
        url="https://upcrestapi.azurewebsites.net/Clientes";
        StringRequest peticion=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray=new JSONArray(response);
                    for (int i=0;i<jsonArray.length();i++){
                     JSONObject objeto=jsonArray.getJSONObject(i);
                     Cliente cliente=new Cliente(objeto.getString("userid"),objeto.getString("nombres"),objeto.getString("deseo"));
                        listaclientes.add(cliente);
                    }

                    clienteAdapter = new ClienteAdapter(getContext(),listaclientes);
                    recyclerView.setAdapter(clienteAdapter);
                }catch (Exception e){
                    Toast.makeText(getContext(), e.getMessage(), LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.getMessage(), LENGTH_SHORT).show();
            }
        });
        RequestQueue cola= Volley.newRequestQueue(getContext());
        cola.add(peticion);
    }
}