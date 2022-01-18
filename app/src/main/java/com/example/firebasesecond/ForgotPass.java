package com.example.firebasesecond;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPass extends AppCompatActivity {

    private EditText fEmail;
    private Button fReset;
    private ProgressBar progressBar3;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);

        fEmail = findViewById(R.id.fEmail);
        fReset = findViewById(R.id.fReset);
        progressBar3 = findViewById(R.id.progressBar3);

        auth=FirebaseAuth.getInstance();
    }
    public void Reseting(View view){
        resetPassword();
    }
    public void resetPassword() {
        String email = fEmail.getText().toString().trim();
        if (email.isEmpty()) {
            fEmail.setError("Email is required!");
            fEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            fEmail.setError("Please Provide Valid Email!");
            fEmail.requestFocus();
        }
        progressBar3.setVisibility(View.VISIBLE);

        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(ForgotPass.this, "Check your email to reset your password", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(ForgotPass.this, "Try again! Something Gone Wrong.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}