package com.example.yumfit.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.yumfit.R;

public class SignupActivity extends AppCompatActivity {

    EditText nameET, emailET, passwordET, confirmPasswordET;
    Button nextBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        initialiseViews();
        nextBtn.setOnClickListener(v->{
            if(nameET.getText().toString().equals("") ||emailET.getText().toString().equals("") ||passwordET.getText().toString().equals("") ||confirmPasswordET.getText().toString().equals("") ){
                Toast.makeText(this, "all fields are required", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "sign up done", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void initialiseViews(){
        nameET = findViewById(R.id.nameEditText);
        emailET = findViewById(R.id.emailEditText);
        passwordET = findViewById(R.id.passwordEditText);
        confirmPasswordET = findViewById(R.id.confirmPasswordEditText);
        nextBtn = findViewById(R.id.nextBtn);
    }

    private void disableButton(){
        nextBtn.setEnabled(false);
        nextBtn.setBackgroundColor(Color.GRAY);
    }
    private void enableButton(){
        nextBtn.setEnabled(true);
        nextBtn.setBackgroundColor(Color.BLACK);
    }
}