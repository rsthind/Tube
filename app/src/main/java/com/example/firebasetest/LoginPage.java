package com.example.firebasetest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginPage extends AppCompatActivity {
    private EditText email;
    private EditText password;
    private Button signIn;
    private Button notRegistered;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        firebaseAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        signIn = findViewById(R.id.signInButton);
        notRegistered = findViewById(R.id.notRegistered);

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser != null) {
                    Toast.makeText(LoginPage.this, "You are logged in", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(LoginPage.this, UserOrTutor.class);
                    startActivity(i);
                } else {
                    Toast.makeText(LoginPage.this, "Please Login", Toast.LENGTH_SHORT).show();
                }
            }
        };

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String e_mail = email.getText().toString();
                String pwd = password.getText().toString();

                if (e_mail.isEmpty() && pwd.isEmpty()) {
                    Toast.makeText(LoginPage.this, "Please enter your email and password", Toast.LENGTH_SHORT).show();
                } else if(e_mail.isEmpty()) {
                    email.setError("Please enter your email");
                    email.requestFocus();
                } else if (pwd.isEmpty()) {
                    password.setError("Please enter your password");
                    password.requestFocus();
                } else if (!(e_mail.isEmpty() && pwd.isEmpty())) {
                    firebaseAuth.signInWithEmailAndPassword(e_mail, pwd).addOnCompleteListener(LoginPage.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()) {
                                Toast.makeText(LoginPage.this, "Incorrect Email or Password", Toast.LENGTH_SHORT).show();
                            } else {
                                Intent toHome = new Intent(LoginPage.this, UserOrTutor.class);
                                startActivity(toHome);
                            }
                        }
                    });
                } else {
                    Toast.makeText(LoginPage.this, "Error Occured", Toast.LENGTH_SHORT).show();
                }
            }
        });


        notRegistered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginPage.this, RegistrationActivity.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }
}
