package com.upc.app_comida;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import java.security.PublicKey;

public class Login_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        setContentView(R.layout.activity_login_);
    }

    public void onLoginClick(View View ){
        startActivity(new Intent(this,NavigationActivity.class));
    }

    public void onRegisterClick(View View ){
        startActivity(new Intent(this,RegistrarActivity.class));
    }
}