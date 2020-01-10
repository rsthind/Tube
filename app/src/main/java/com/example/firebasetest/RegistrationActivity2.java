package com.example.firebasetest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.MultiAutoCompleteTextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class RegistrationActivity2 extends AppCompatActivity {
    private Button create_account;
    private final String[] CLASSES = {"CS1331", "MATH1554", "ENGL1102", "INTA1200", "ECON2106", "CS2701"};
    private MultiAutoCompleteTextView class_input;

    private FirebaseDatabase db;
    private DatabaseReference dbref;

    private String sGTID;
    private String sFname;
    private String sEmail;
    private String sMajor;
    private String sPassword;
    private ArrayList<String> sCourses_List = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration2);
        create_account = findViewById(R.id.create_account);
        class_input = findViewById(R.id.mactv);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, CLASSES);
        class_input.setAdapter(adapter);
        class_input.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

        create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addStudents();
                class_input.setText("");
                startActivity(new Intent(RegistrationActivity2.this, UserOrTutor.class));
            }
        });
    }
    //sends user to data to database
    public void addStudents() {
        sGTID = getIntent().getStringExtra("gtid");
        sFname = getIntent().getStringExtra("fname");
        sEmail = getIntent().getStringExtra("email");
        sMajor = getIntent().getStringExtra("major");
        sPassword = getIntent().getStringExtra("password");
        sCourses_List.add(class_input.getText().toString().trim());

        dbref = FirebaseDatabase.getInstance().getReference("users");
        String id = dbref.push().getKey();
        User add = new User(sGTID, sFname, sEmail, sMajor, sPassword, sCourses_List);
        dbref.child(id).setValue(add);
    }
}
