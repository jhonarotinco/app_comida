package com.upc.app_comida.ui.slideshow;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.upc.app_comida.R;
import com.upc.app_comida.entidades.Comida;

import java.util.ArrayList;

public class CustomAdapter2 extends RecyclerView.Adapter<CustomAdapter2.miVista> {

    private Context context;
    private ArrayList<Comida> listaComida = new ArrayList<>();

    public CustomAdapter2(Context context, ArrayList<Comida> listaComida){
        this.context = context;
        this.listaComida = listaComida;
    }

    @NonNull
    @Override
    public CustomAdapter2.miVista onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.nueva_fila2, parent,false);
        return new miVista(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter2.miVista holder, int position) {
        holder.nombre_comida2.setText(String.valueOf(listaComida.get(position).getNombre()));
        holder.categoria_comida2.setText(String.valueOf(listaComida.get(position).getCategoria()));
        holder.calorias_comida2.setText(String.valueOf(listaComida.get(position).getCalorias()));
    }

    @Override
    public int getItemCount() {
        return listaComida.size();
    }

    public class miVista extends RecyclerView.ViewHolder{

        TextView nombre_comida2, categoria_comida2, calorias_comida2;
        //LinearLayout filaLayout;
        public miVista(@NonNull View itemView) {
            super(itemView);
            nombre_comida2 = itemView.findViewById(R.id.nombre_comida2);
            categoria_comida2 = itemView.findViewById(R.id.categoria_comida2);
            calorias_comida2 = itemView.findViewById(R.id.calorias_comida2);
            //filaLayout = itemView.findViewById(R.id.filaLayout);
        }
    }
}
