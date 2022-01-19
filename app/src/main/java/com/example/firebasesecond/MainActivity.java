package com.example.firebasesecond;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    private TextView text;
    TextView sRegister;
    private EditText sEmail,sPass;
    private TextView sForgot,appName;
    private Button sLogin;
    private ProgressBar progressBar;

    public void GoToRegister(View view){
        Intent intent = new Intent(getApplicationContext(), Register.class);
        startActivity(intent);
    }

    public void userLogin(View view){
        String email=sEmail.getText().toString().trim();
        String pass=sPass.getText().toString().trim();

        if(email.isEmpty()){
            sEmail.setError("Email is required!");
            sEmail.requestFocus();
            return;
        }
        if(pass.isEmpty()){
            sPass.setError("Password is required!");
            sPass.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            sEmail.setError("Please Enter valid Email!");
            sEmail.requestFocus();
            return;
        }
        if(pass.length()<6){
            sPass.setError("Min password length should be 6 characters.");
            sPass.requestFocus();
            return;
        }
        progressBar.setVisibility(view.VISIBLE);

        mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
                    if(user.isEmailVerified()){
                        startActivity(new Intent(MainActivity.this,ProfileActivity.class));
                    }
                    else{
                        user.sendEmailVerification();
                        Toast.makeText(MainActivity.this,"Check your email to verify account",Toast.LENGTH_LONG).show();
                    }

                }
                else{
                    Toast.makeText(MainActivity.this,"Failed to Login! Please check your credentials",Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth=FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sRegister = findViewById(R.id.sRegister);
        sLogin = findViewById(R.id.sLogin);
        sEmail =findViewById(R.id.sEmail);
        sPass=findViewById(R.id.sPass);
        sForgot=findViewById(R.id.sForgot);
        progressBar=findViewById(R.id.progressBar);

    }
    public void ForgettingPass(View view){
        Intent intent = new Intent(getApplicationContext(), ForgotPass.class);
        startActivity(intent);
    }
}