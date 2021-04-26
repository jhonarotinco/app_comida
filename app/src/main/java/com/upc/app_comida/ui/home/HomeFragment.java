package com.upc.app_comida.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.upc.app_comida.NavigationActivity;
import com.upc.app_comida.R;
import com.upc.app_comida.entidades.Comida;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {


    RecyclerView recyclerView;

    ArrayList<Comida> listaComidas = new ArrayList<>();
    CustomAdapter customAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = view.findViewById(R.id.recyclerListaComidas);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mostrarComidas();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //mostrarComidas();
    }


    private void mostrarComidas(){
        String url = "https://upcrestapi.azurewebsites.net/Clientes";
        StringRequest peticion = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i=0; i < jsonArray.length(); i++){
                        JSONObject objeto = jsonArray.getJSONObject(i);
                        Comida comida = new Comida(objeto.getString("nombres"), objeto.getString("apellidos"),objeto.getInt("talla"));
                        JSONObject miplan = objeto.getJSONObject("miPlan");
                        if (!miplan.isNull("alimentos")) {
                            JSONArray alimentos = miplan.getJSONArray("alimentos");
                            for (int j = 0; j < alimentos.length(); j++) {
                                JSONObject alimento = alimentos.getJSONObject(j);
                                System.out.println(alimento.getString("titulo"));
                            }
                        }
                        listaComidas.add(comida);
                    }
                    customAdapter = new CustomAdapter(getContext(),listaComidas);
                    recyclerView.setAdapter(customAdapter);
                } catch (JSONException e) {
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    System.out.println(e);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        /*String url = "https://upcrestapi.azurewebsites.net/api/Karmu/Usuarios/"+idUsuario;
        StringRequest peticion = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i=0; i < jsonArray.length(); i++){
                        JSONObject objeto = jsonArray.getJSONObject(i);
                        Comida comida = new Comida(objeto.getString("nombres"), objeto.getString("apellidos"),objeto.getInt("talla"));
                        JSONObject miplan = objeto.getJSONObject("miPlan");
                        if (!miplan.isNull("alimentos")) {
                            JSONArray alimentos = miplan.getJSONArray("alimentos");
                            for (int j = 0; j < alimentos.length(); j++) {
                                JSONObject alimento = alimentos.getJSONObject(j);
                                System.out.println(alimento.getString("titulo"));
                            }
                        }
                        listaComidas.add(comida);
                    }
                    customAdapter = new CustomAdapter(getContext(),listaComidas);
                    recyclerView.setAdapter(customAdapter);
                } catch (JSONException e) {
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    System.out.println(e);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });*/

        RequestQueue cola = Volley.newRequestQueue(getContext());
        cola.add(peticion);
    }
}