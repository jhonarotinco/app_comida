package com.upc.app_comida.ui.gallery;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.upc.app_comida.R;
import com.upc.app_comida.entidades.Comida;
import com.upc.app_comida.ui.home.CustomAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GalleryFragment extends Fragment {

    String Usuario;
    ListView lista_pesos;
    Button btnRegistrarPeso;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);
        lista_pesos = view.findViewById(R.id.lista_pesos);
        recuperarPreferencias();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        listarPesos();
        btnRegistrarPeso = view.findViewById(R.id.btn_registrar_peso);
        btnRegistrarPeso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Esta es la forma de referenciar un fragmento
                Navigation.findNavController(view).navigate(R.id.nav_registrar_peso);
            }
        });
        super.onViewCreated(view, savedInstanceState);
    }

    private void  recuperarPreferencias()
    {
        SharedPreferences preferences=this.getActivity().getSharedPreferences("preferencias", Context.MODE_PRIVATE);
        Usuario=preferences.getString("usuario","ejemplo@dominio.com");
    }

    private void listarPesos(){
        String idUsuario_request = Usuario;
        String url = "https://upcrestapi.azurewebsites.net/api/Karmu/Usuarios/"+idUsuario_request+"/historial";
        StringRequest peticion = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    List<String> items = new ArrayList<>();
                    //if (!jsonArray.isNull("alimentos")){
                        for (int i=0; i < jsonArray.length(); i++){
                            JSONObject objeto = jsonArray.getJSONObject(i);
                            items.add(objeto.getString("fecha") + ": " + objeto.getString("peso") + " Kg");
                        }
                        ArrayAdapter<String> adaptador = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1,items);
                        lista_pesos.setAdapter(adaptador);
                    //}else{

                    //}
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