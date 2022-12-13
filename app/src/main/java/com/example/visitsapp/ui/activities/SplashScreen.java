package com.example.visitsapp.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.visitsapp.ui.MainActivity;
import com.example.visitsapp.R;
import com.example.visitsapp.utils.SharedPrefrences;

public class SplashScreen extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        startFadeOutAnimation();
    }

    public void startFadeOutAnimation() {
        ImageView fadeOutImage = findViewById(R.id.iv_logo);
        Animation startFadeOutAnimation
                = AnimationUtils.loadAnimation(
                getApplicationContext(),
                R.anim.fade_out_animation);
        fadeOutImage.startAnimation(startFadeOutAnimation);

        startFadeOutAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                if( SharedPrefrences.getInstance().getIsLogin() == true){
                    Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    startActivity(new Intent(new Intent(SplashScreen.this,Login.class)));
                    overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out);
                    finish();
                }


            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}