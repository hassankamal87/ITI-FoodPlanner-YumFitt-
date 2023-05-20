package com.example.yumfit.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.yumfit.R;

public class RegisterActivity extends AppCompatActivity {

    Button gmailBtn, faceBookBtn, signUpEmailBtn;
    TextView logInTV;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initialiseViews();
        intent = new Intent();


        gmailBtn.setOnClickListener(v -> {

        });
        faceBookBtn.setOnClickListener(v->{

        });

        signUpEmailBtn.setOnClickListener(v -> {
            intent.setClass(RegisterActivity.this, SignupActivity.class);
            startActivity(intent);
        });

        logInTV.setOnClickListener(v->{
            intent.setClass(RegisterActivity.this, SignInActivity.class);
            startActivity(intent);
        });



    }

    private void initialiseViews(){
        gmailBtn = findViewById(R.id.googleBtn);
        faceBookBtn = findViewById(R.id.faceBookBtn);
        signUpEmailBtn = findViewById(R.id.emailBtn);
        logInTV = findViewById(R.id.logInTV);
        logInTV.setPaintFlags(logInTV.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
    }
}