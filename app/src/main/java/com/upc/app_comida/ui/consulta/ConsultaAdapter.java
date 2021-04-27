package com.upc.app_comida.ui.consulta;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.upc.app_comida.R;
import com.upc.app_comida.entidades.Consulta;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentNavigableMap;

public class ConsultaAdapter extends RecyclerView.Adapter<ConsultaAdapter.mivista> {

    private Context context;
    private ArrayList<Consulta>listaconsulta=new ArrayList<>();
    public ConsultaAdapter(Context context,ArrayList<Consulta>listaconsulta){
        this.context=context;
        this.listaconsulta=listaconsulta;
    }

    @NonNull
    @Override
    public ConsultaAdapter.mivista onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.fila_consulta,parent,false);
        return new mivista(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ConsultaAdapter.mivista holder, int position) {
        holder.consulta.setText(String.valueOf(listaconsulta.get(position).getPregunta()));
        holder.respuesta.setText(String.valueOf(listaconsulta.get(position).getRespuesta()));
        holder.img_responder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.nav_consul_respon_frag);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaconsulta.size();
    }

    public class mivista extends RecyclerView.ViewHolder {
        TextView consulta,respuesta;
        ImageView img_responder;
        public mivista(@NonNull View itemView) {
            super(itemView);
            consulta=itemView.findViewById(R.id.txt_consulta_v);
            respuesta=itemView.findViewById(R.id.txt_respuesta_v);
            img_responder=itemView.findViewById(R.id.imgresponder);
        }
    }
}
