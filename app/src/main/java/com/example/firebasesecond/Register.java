package com.example.firebasesecond;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity{
    TextView textView;
    EditText rName, rAge, rPass, rEmail;
    ProgressBar progressBar2;
    Button RegisterButton;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        textView = findViewById(R.id.sRegister);


        rName = findViewById(R.id.rName);
        rAge = findViewById(R.id.rAge);
        rPass = findViewById(R.id.rPass);
        rEmail = findViewById(R.id.rEmail);

        progressBar2 = findViewById(R.id.progressBar2);
        RegisterButton = findViewById(R.id.RegisterButton);
    }

    public void GoToPrevious(View view){
        finish();
    }
    public void RegisterUser(View view){
        String email = rEmail.getText().toString().trim();
        String pass = rPass.getText().toString().trim();
        String name = rName.getText().toString().trim();
        String age = rAge.getText().toString().trim();

        if(name.isEmpty()){
            rName.setError("Name is required!");
            rName.requestFocus();
            return;
        }
        if(age.isEmpty()){
            rAge.setError("Age is required!");
            rAge.requestFocus();
            return;
        }if(email.isEmpty()){
            rEmail.setError("Email is required!");
            rEmail.requestFocus();
            return;
        }
        if(pass.isEmpty()){
            rPass.setError("Password is required!");
            rPass.requestFocus();
            return;
        }
        if(pass.length()<6){
            rPass.setError("Min password length should be 6 characters.");
            rPass.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            rEmail.setError("Please Provide valid Email Address!");
            rEmail.requestFocus();
            return;
        }
        progressBar2.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email,pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Users user=new Users(name,age,email);
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(Register.this,"User has been registered successfully!",Toast.LENGTH_LONG).show();
                                        progressBar2.setVisibility(View.GONE);
                                    }
                                    else{
                                        Toast.makeText(Register.this,"Failed to register! Try again!",Toast.LENGTH_LONG).show();
                                        progressBar2.setVisibility(View.GONE);
                                    }
                                }
                            });
                        }
                        else{
                            Toast.makeText(Register.this,"Failed to register!",Toast.LENGTH_LONG).show();
                            progressBar2.setVisibility(View.GONE);
                        }
                    }
                });
    }
}
