package com.example.yumfit.ui;

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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignupActivity extends AppCompatActivity {

    EditText nameET, emailET, passwordET, confirmPasswordET;
    Button nextBtn;
    ProgressBar progressBar;
    Intent intent;

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        initialiseViews();
        mAuth = FirebaseAuth.getInstance();
        intent = new Intent();

        nextBtn.setOnClickListener(v->{

            if(nameET.getText().toString().equals("") ||emailET.getText().toString().equals("") ||passwordET.getText().toString().equals("") ||confirmPasswordET.getText().toString().equals("") ){
                Toast.makeText(this, "all fields are required", Toast.LENGTH_SHORT).show();
            }else if(!passwordET.getText().toString().equals(confirmPasswordET.getText().toString())){
                Toast.makeText(this, "password not Match Confirm Password", Toast.LENGTH_SHORT).show();
            }else{
                nextBtn.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                String email, password;
                email = String.valueOf(emailET.getText());
                password = String.valueOf(passwordET.getText());

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    Toast.makeText(SignupActivity.this, "Authentication Successful", Toast.LENGTH_SHORT).show();
                                    intent.setClass(SignupActivity.this,Home2Activity.class);
                                    startActivity(intent);
                                    // Sign in success, update UI with the signed-in user's information
                                    //FirebaseUser user = mAuth.getCurrentUser();
                                    //updateUI(user);
                                } else {
                                    nextBtn.setVisibility(View.VISIBLE);
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(SignupActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                    //updateUI(null);
                                }
                            }
                        });
            }
        });
    }

    private void initialiseViews(){
        nameET = findViewById(R.id.nameEditText);
        emailET = findViewById(R.id.emailEditText);
        passwordET = findViewById(R.id.passwordEditTextinSignIn);
        confirmPasswordET = findViewById(R.id.confirmPasswordEditText);
        nextBtn = findViewById(R.id.resetBtn);
        progressBar = findViewById(R.id.progressBarInPasswordReset);
        progressBar.setVisibility(View.GONE);
    }


}