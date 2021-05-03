package com.upc.app_comida.ui.contacto;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.upc.app_comida.R;

import java.net.URI;


public class ContactoFragment extends Fragment {

    Button btnLlamar;
    TextView txtNombre, txtCorreo, txtTelefono;
    DatosNutricionista datos = new DatosNutricionista();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contacto, container, false);
        txtNombre = view.findViewById(R.id.txt_nutricionista_nombre);
        txtCorreo = view.findViewById(R.id.txt_nutricionista_correo);
        txtTelefono = view.findViewById(R.id.txt_nutricionista_telefono);
        txtNombre.setText(datos.getNombre());
        txtCorreo.setText(datos.getCorreo());
        txtTelefono.setText(datos.getTelefono());
        btnLlamar = view.findViewById(R.id.btn_llamar);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        llamar();
        super.onViewCreated(view, savedInstanceState);
    }

    private void llamar(){
        btnLlamar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri numero = Uri.parse("tel:"+datos.getTelefono());
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(numero);
                startActivity(intent);
            }
        });
    }
}