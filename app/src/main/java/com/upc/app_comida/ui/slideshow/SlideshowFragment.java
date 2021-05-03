package com.upc.app_comida.ui.slideshow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class SlideshowFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<Comida> listaComidas = new ArrayList<>();
    CustomAdapter2 customAdapter;
    Button botonAgregar;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_slideshow, container, false);
        recyclerView = view.findViewById(R.id.recyclerAlimentos);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        listarComidas();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        botonAgregar = view.findViewById(R.id.btn_agregar_alimento);
        botonAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Esta es la forma de referenciar un fragmento
                Navigation.findNavController(view).navigate(R.id.nav_agregar_alimento);
            }
        });
    }

    private void listarComidas(){
        String url = "https://upcrestapi.azurewebsites.net/api/Karmu/Alimentos";
        StringRequest peticion = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    //if (!jsonArray.isNull("alimentos")){
                        for (int i=0; i < jsonArray.length(); i++){
                            JSONObject objeto = jsonArray.getJSONObject(i);
                            Comida comida = new Comida(objeto.getString("titulo"), objeto.getString("tipo"), objeto.getInt("calorias"));
                            listaComidas.add(comida);
                        }
                        customAdapter = new CustomAdapter2(getContext(),listaComidas);
                        recyclerView.setAdapter(customAdapter);
                    //}else{
                        //Toast.makeText(getContext(), "No tiene alimentos", Toast.LENGTH_SHORT).show();
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
