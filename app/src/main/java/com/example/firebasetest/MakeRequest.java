package com.example.firebasetest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MakeRequest extends AppCompatActivity {

    DatabaseReference database = FirebaseDatabase.getInstance().getReference("Requests");

    EditText course;
    EditText date;
    TextInputEditText addInfo;
    Button submitButton;
    Button viewRequestsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_request);

        this.course = (EditText) findViewById(R.id.courseText);
        this.date = (EditText) findViewById(R.id.dateText);
        this.addInfo = (TextInputEditText) findViewById(R.id.addInfoText);
        this.submitButton = (Button) findViewById(R.id.submitButton);
        this.viewRequestsButton = (Button) findViewById(R.id.viewRequestsButton);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequest();
                Intent myIntent = new Intent(v.getContext(), UserRequestsView.class);
                startActivityForResult(myIntent, 0);
            }
        });

        viewRequestsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(v.getContext(), UserRequestsView.class);
                startActivityForResult(myIntent, 0);
            }
        });

    }

    private void sendRequest() {
        String course_send = course.getText().toString().trim();
        String date_send = date.getText().toString().trim();
        String user_ID = CreateUser.currUser.getGTID();
        String addInfoString = addInfo.getText().toString();

        if(!TextUtils.isEmpty(course_send)) {
            String request_ID = database.push().getKey();
            Request req = new Request(course_send, addInfoString, new Time(), user_ID, "", request_ID);
            database.child(request_ID).setValue(req);

            // make notification
            Toast toast = Toast.makeText(this, "Request Created", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        } else {
            Toast toast = Toast.makeText(this, "Please Enter a Course", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }

    }


}
