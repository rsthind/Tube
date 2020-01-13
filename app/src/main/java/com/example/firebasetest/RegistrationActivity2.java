package com.example.firebasetest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class RegistrationActivity2 extends AppCompatActivity {
    private Button create_account;
    private Button add_class;
    private FirebaseDatabase db;
    private AutoCompleteTextView actv;
    private DatabaseReference dbref;
    private String sGTID;
    private String sFname;
    private String sEmail;
    private String sMajor;
    private String sPassword;
    private ArrayList<String> courses_list = new ArrayList<>();
    private ListView listView;
    private final String[] CLASSES = {"CS1331", "MATH1554", "ENGL1102","INTA1200","ECON2106", "CS2701"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration2);

        create_account = findViewById(R.id.create_account);
        add_class = findViewById(R.id.add_class);
        actv = findViewById(R.id.course_actv);
        listView = findViewById(R.id.lv);

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, CLASSES);
        actv.setAdapter(adapter);

        add_class.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input = actv.getText().toString();

                //check to make sure entered item is only part of entered list
                boolean isValid = false;
                for (int a = 0; a < adapter.getCount();a++) {
                    String temp = adapter.getItem(a);
                    if (input.compareTo(temp) == 0) {
                        isValid = true;
                        break;
                    }
                }

                if (isValid && notRepeat(input)) {
                    courses_list.add(input);
                    updateList();
                    actv.setText("");
                } else {
                    Toast.makeText(RegistrationActivity2.this,"Please Enter a Valid Course ID",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addStudents();
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

        dbref = FirebaseDatabase.getInstance().getReference("users");
        String id = dbref.push().getKey();
        User add = new User(sGTID, sFname, sEmail, sMajor, sPassword, courses_list);
        dbref.child(id).setValue(add);
    }

    public void updateList() {
        ArrayAdapter<String> list_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                courses_list);
        listView.setAdapter(list_adapter);
    }

    public boolean notRepeat(String input) {
        for (int a = 0; a < courses_list.size(); a++) {
            if (input.compareTo(courses_list.get(a)) == 0) {
                Toast.makeText(RegistrationActivity2.this,"Cannot Repeat Course Entry",
                        Toast.LENGTH_SHORT).show();
                actv.setText("");
                return false;
            }
        }
        return true;
    }
}
