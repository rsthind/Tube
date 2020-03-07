package com.example.firebasetest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class UserOrTutor extends AppCompatActivity {

    Button userButton;
    Button tutorButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_or_tutor);

        userButton = (Button) findViewById(R.id.userButton);
        tutorButton = (Button) findViewById(R.id.tutorButton);

        userButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(v.getContext(), MakeRequest.class);
                startActivityForResult(myIntent, 0);
            }
        });

        tutorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(v.getContext(), TutorRequestsView.class);
                startActivityForResult(myIntent, 0);
            }
        });
    }
}
