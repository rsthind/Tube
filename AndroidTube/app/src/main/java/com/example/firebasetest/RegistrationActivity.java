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
import com.google.firebase.auth.FirebaseAuthException;

public class RegistrationActivity extends AppCompatActivity {
    private EditText GTID;
    private EditText fname;
    private EditText email;
    private EditText major;
    private EditText password;
    private Button send;
    private Button already_registered;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        GTID = findViewById(R.id.GTID);
        fname = findViewById(R.id.firstname);
        email = findViewById(R.id.email);
        major = findViewById(R.id.major);
        password = findViewById(R.id.password);
        send = findViewById(R.id.sendbutton);
        already_registered = findViewById(R.id.already_Registered);
        firebaseAuth = FirebaseAuth.getInstance();


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user_email = email.getText().toString().trim();
                String user_pw = password.getText().toString().trim();

                if (validate()) {
                    firebaseAuth.createUserWithEmailAndPassword(user_email, user_pw).addOnCompleteListener(RegistrationActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(RegistrationActivity.this,"Registration Successful.", Toast.LENGTH_SHORT).show();

                                Intent part2 = new Intent(getBaseContext(), RegistrationActivity2.class);
                                part2.putExtra("gtid", GTID.getText().toString().trim());
                                part2.putExtra("fname", fname.getText().toString().trim());
                                part2.putExtra("email", email.getText().toString().trim());
                                part2.putExtra("major", major.getText().toString().trim());
                                part2.putExtra("password", password.getText().toString().trim());
                                startActivity(part2);
                                startActivity(new Intent(RegistrationActivity.this, RegistrationActivity2.class));
                            } else {
                                FirebaseAuthException e = (FirebaseAuthException)task.getException();
                                Toast.makeText(RegistrationActivity.this,"Registration Failed. " + e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }

            }
        });

        //sends the user back to the first log in after they have created their account
        already_registered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegistrationActivity.this, UserOrTutor.class));
            }
        });
    }

    public boolean validate() {
        String gtid = GTID.getText().toString().trim();
        String f_name = fname.getText().toString().trim();
        String e_mail = email.getText().toString().trim();
        String Major = major.getText().toString().trim();
        String pw = password.getText().toString().trim();

        if (gtid.isEmpty() || f_name.isEmpty() || e_mail.isEmpty() || Major.isEmpty() || pw.isEmpty()) {
            Toast.makeText(RegistrationActivity.this,"Please Fill Out All Fields", Toast.LENGTH_LONG).show();
            return false;
        } else {
            return true;
        }
    }
}
