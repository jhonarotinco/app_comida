package com.upc.app_comida.ui.cliente;

import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.upc.app_comida.R;
import com.upc.app_comida.RegistrarActivity;
import com.upc.app_comida.entidades.Cliente;

import java.util.ArrayList;

public class ClienteAdapter extends RecyclerView.Adapter<ClienteAdapter.mivista> {

    private Context context;
    private ArrayList<Cliente> listacliente=new ArrayList<>();

    public ClienteAdapter(Context context, ArrayList<Cliente> listacliente){
        this.context=context;
        this.listacliente=listacliente;
    }

    @NonNull
    @Override
    public ClienteAdapter.mivista onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.fila_cliente,parent,false);
        return new mivista(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClienteAdapter.mivista holder, int position) {
        holder.correo.setText(String.valueOf(listacliente.get(position).getCorreo()));
        holder.nombre.setText(String.valueOf(listacliente.get(position).getNombre()));
        holder.objetivo.setText(String.valueOf(listacliente.get(position).getObjetivo()));
        holder.imgReceta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, RegistrarActivity.class);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listacliente.size();
    }

    public class mivista extends RecyclerView.ViewHolder{
        TextView correo,nombre,objetivo;
        ImageView imgReceta;
        ImageView imgGrafico;
        public mivista(@NonNull View itemView) {
            super(itemView);
            correo=itemView.findViewById(R.id.txt_correo_v);
            nombre=itemView.findViewById(R.id.txt_nombre_v);
            objetivo=itemView.findViewById(R.id.txt_objetivo_v);
            imgReceta=itemView.findViewById(R.id.imgReceta);
            imgReceta=itemView.findViewById(R.id.imgGrafico);
        }
    }
}
