package com.upc.app_comida;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Object_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_object_);
    }

    public void onPlanesClick(View View ){
        startActivity(new Intent(this,Planes_Activity.class));
    }
}