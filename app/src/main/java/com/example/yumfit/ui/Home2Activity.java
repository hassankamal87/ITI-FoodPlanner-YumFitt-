package com.example.yumfit.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.yumfit.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Home2Activity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);
        initialiseViews();
    }

    private void initialiseViews(){

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setBackground(null);
        bottomNavigationView.getMenu().getItem(2).setEnabled(false);

    }
}