package com.example.yumfit.authentication.resetpassword;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.yumfit.R;
import com.example.yumfit.authentication.signin.SignInActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class PasswordReset extends AppCompatActivity {

    EditText emailEditText;
    Button resetBtn;
    ProgressBar progressBar;
    Intent intent;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_reset);

        initialiseViews();
        mAuth = FirebaseAuth.getInstance();
        intent = new Intent();

        resetBtn.setOnClickListener(v->{
            if(emailEditText.getText().toString().equals("")){
                Toast.makeText(this, "please enter email to send Code", Toast.LENGTH_SHORT).show();
            }else{
                resetBtn.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                String email = String.valueOf(emailEditText.getText());
                mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(PasswordReset.this, "check Your Email", Toast.LENGTH_SHORT).show();
                            intent.setClass(PasswordReset.this, SignInActivity.class);
                            startActivity(intent);
                            finish();
                        }else{
                            progressBar.setVisibility(View.GONE);
                            resetBtn.setVisibility(View.VISIBLE);
                            Toast.makeText(PasswordReset.this, "this email is not exist", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    private void initialiseViews(){
        emailEditText = findViewById(R.id.emailInPasswordReset);
        resetBtn = findViewById(R.id.resetBtn);
        progressBar = findViewById(R.id.progressBarInPasswordReset);
        progressBar.setVisibility(View.GONE);
    }
}