package com.example.yumfit.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yumfit.R;

public class MainActivity extends AppCompatActivity {

    TextView appNameTextView;
    ImageView logoIV;
    Animation scaleChanger;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appNameTextView = findViewById(R.id.appNameTextView);
        logoIV = findViewById(R.id.logoImageView);

        scaleChanger = AnimationUtils.loadAnimation(this, R.anim.scale_changer);

        logoIV.startAnimation(scaleChanger);
        appNameTextView.startAnimation(scaleChanger);
        
        appNameTextView.postOnAnimationDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        }, 4000);
    }
}