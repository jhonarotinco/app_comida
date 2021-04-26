package com.upc.app_comida.ui.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.upc.app_comida.R;
import com.upc.app_comida.entidades.Comida;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.miVista> {

    private Context context;
    private ArrayList<Comida> listaComida = new ArrayList<>();

    public CustomAdapter(Context context, ArrayList<Comida> listaComida){
        this.context = context;
        this.listaComida = listaComida;
    }

    @NonNull
    @Override
    public CustomAdapter.miVista onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.nueva_fila, parent,false);
        return new miVista(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.miVista holder, int position) {
        holder.nombre_comida.setText(String.valueOf(listaComida.get(position).getNombre()));
        holder.categoria_comida.setText(String.valueOf(listaComida.get(position).getCategoria()));
        holder.calorias_comida.setText(String.valueOf(listaComida.get(position).getCalorias()));
    }

    @Override
    public int getItemCount() {
        return listaComida.size();
    }

    public class miVista extends RecyclerView.ViewHolder{

        TextView nombre_comida, categoria_comida, calorias_comida;
        //LinearLayout filaLayout;
        public miVista(@NonNull View itemView) {
            super(itemView);
            nombre_comida = itemView.findViewById(R.id.nombre_comida);
            categoria_comida = itemView.findViewById(R.id.categoria_comida);
            calorias_comida = itemView.findViewById(R.id.calorias_comida);
            //filaLayout = itemView.findViewById(R.id.filaLayout);
        }
    }
}
