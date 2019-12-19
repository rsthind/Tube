package com.example.firebasetest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.ViewGroup;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class TutorRequestsView extends AppCompatActivity {

    DatabaseReference database = FirebaseDatabase.getInstance().getReference("Requests");
    ViewGroup rView;
    ArrayList<ArrayList<Request>> allRequestsList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_requests_view);
    }
}
