package com.upc.app_comida.ui.consulta;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.upc.app_comida.R;
import com.upc.app_comida.entidades.Consulta;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ConsultasFragment extends Fragment {
    ArrayList<Consulta>listaconsultas=new ArrayList<>();
    RecyclerView recyclerView;
    ConsultaAdapter consultaAdapter;
    Button btn_nueva_consulta;
    String Usuario,tipo_usuario;

    public ConsultasFragment() {
        // Required empty public constructor
    }
    public static ConsultasFragment newInstance(String param1, String param2) {
        ConsultasFragment fragment = new ConsultasFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_consultas, container, false);
        recyclerView=view.findViewById(R.id.recyclerListaconsulta);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        btn_nueva_consulta=view.findViewById(R.id.btn_nueva_consulta);
        recuperarPreferencias();
        if (tipo_usuario=="Nutricionista"){
            btn_nueva_consulta.setVisibility(view.GONE);
        }
        listar_consultas();
        btn_nueva_consulta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.nav_consul_respon_frag);
            }
        });
        return  view;
    }

    private  void listar_consultas(){
        String url="";
        String usu=Usuario;
        url="https://upcrestapi.azurewebsites.net/api/Karmu/Usuarios/"+usu;
        StringRequest peticion=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonArra=new JSONObject(response);
                    JSONArray consultas=jsonArra.getJSONArray("consultas");
                    for (int i=0; i < consultas.length();i++){
                     JSONObject objeto=consultas.getJSONObject(i);
                     Consulta consulta=new Consulta(objeto.getString("pregunta"),objeto.getString("respuesta"));
                     listaconsultas.add(consulta);
                    }
                    consultaAdapter=new ConsultaAdapter(getContext(),listaconsultas);
                    recyclerView.setAdapter(consultaAdapter);
                }catch (JSONException e){
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    System.out.println(e);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(),error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue cola= Volley.newRequestQueue(getContext());
        cola.add(peticion);
    }

    //Meotod para recuperar dichas preferencias
    private void  recuperarPreferencias()
    {
        SharedPreferences preferences=this.getActivity().getSharedPreferences("preferencias",Context.MODE_PRIVATE);
        Usuario=preferences.getString("usuario","ejemplo@dominio.com");
        tipo_usuario=preferences.getString("tipo_usuario","ejemplo@dominio.com");
    }
}