package com.example.firebasetest;

import androidx.appcompat.app.AppCompatActivity;
// Firebase imports:
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class CreateUser extends AppCompatActivity {

    DatabaseReference database = FirebaseDatabase.getInstance().getReference("Users");

    static User currUser = new User();
    EditText firstName;
    EditText lastName;
    EditText GTIDText;
    EditText password;
    EditText verifiedCoursesText;
    Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firstName = (EditText) findViewById(R.id.firstnameText);
        lastName = (EditText) findViewById(R.id.lastnameText);
        GTIDText = (EditText) findViewById(R.id.GTIDText);
        password = (EditText) findViewById(R.id.passwordText);
        verifiedCoursesText = (EditText) findViewById(R.id.verifiedCourses);
        submitButton = (Button) findViewById(R.id.submitButton);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUser();
                Intent myIntent = new Intent(v.getContext(), UserOrTutor.class);
                startActivityForResult(myIntent, 0);
            }
        });

    }

    private void addUser() {
        String fname = firstName.getText().toString().trim();
        String lname = lastName.getText().toString().trim();
        String GTID = GTIDText.getText().toString();
        String pass = password.getText().toString();

        // this will definitely have to change
        ArrayList<String> verifiedCourses = new ArrayList<>();
        verifiedCourses.add(verifiedCoursesText.getText().toString());

        if (!TextUtils.isEmpty(fname) && !TextUtils.isEmpty(lname) && !TextUtils.isEmpty(GTID) && !TextUtils.isEmpty(pass)) {
            String userID = database.push().getKey();
            User user = new User(fname, lname, GTID, pass, userID, verifiedCourses);
            database.child(userID).setValue(user);

            // assign static variables
            currUser = user;

            // make notification
            Toast.makeText(this, "User Added", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Failure", Toast.LENGTH_LONG).show();
        }

    }
}
