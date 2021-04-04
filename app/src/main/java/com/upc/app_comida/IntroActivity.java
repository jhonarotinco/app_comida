package com.upc.app_comida;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.airbnb.lottie.LottieAnimationView;

public class IntroActivity extends AppCompatActivity {

    private final int duracion_splsh=3000;
    Animation topaim,bottonanim;
    ImageView image1,image2,image3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_intro);

        /*topaim= AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottonanim= AnimationUtils.loadAnimation(this,R.anim.bottomanimation);*/

        /*image1=findViewById(R.id.img1);
        image2=findViewById(R.id.img2);
        image3=findViewById(R.id.img3);


        image2.setAnimation(topaim);
        image3.setAnimation(topaim);*/

        new Handler().postDelayed(new Runnable() {
            public void run() {
                Intent intent=new Intent(IntroActivity.this,Login_Activity.class);
                startActivity(intent);
                finish();
            };
        },duracion_splsh);
    }
}