package com.upc.app_comida.ui.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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
    String tipo_usuario,Usuario;

    ArrayList<Comida> listaComidas = new ArrayList<>();
    CustomAdapter customAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = view.findViewById(R.id.recyclerListaComidas);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recuperarPreferencias();
        mostrarComidas();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void  recuperarPreferencias()
    {
        SharedPreferences preferences=this.getActivity().getSharedPreferences("preferencias", Context.MODE_PRIVATE);
        Usuario=preferences.getString("usuario","ejemplo@dominio.com");
        tipo_usuario=preferences.getString("tipo_usuario","ejemplo@dominio.com");
    }

    private void mostrarComidas(){
        String idUsuario_request = Usuario;
        String url = "https://upcrestapi.azurewebsites.net/api/Karmu/Usuarios/" + idUsuario_request + "/plan";
        StringRequest peticion = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonArray = new JSONObject(response);
                    if (!jsonArray.isNull("alimentos")){
                        JSONArray alimentos = jsonArray.getJSONArray("alimentos");
                        for (int i=0; i < alimentos.length(); i++){
                            JSONObject objeto = alimentos.getJSONObject(i);
                            Comida comida = new Comida(objeto.getString("titulo"), objeto.getString("tipo"), objeto.getInt("calorias"));
                            listaComidas.add(comida);
                        }
                        customAdapter = new CustomAdapter(getContext(),listaComidas);
                        recyclerView.setAdapter(customAdapter);
                    }else{
                        Toast.makeText(getContext(), "No tiene alimentos", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    System.out.println(e);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue cola = Volley.newRequestQueue(getContext());
        cola.add(peticion);
    }
}