package com.example.firebasetest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Calendar;

public class MakeRequest extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    //DatabaseReference database = FirebaseDatabase.getInstance().getReference("Requests");//

    EditText course;
    TextInputLayout addInfo;
    Button submit;
    Button viewReqs;
    Button goBack;
    TextView dateSelection;
    TextView timeSelection;
    Button selectDate;
    Button selectTime;
    int[] dateTimeInfo = new int[5];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_request);

        course = findViewById(R.id.courseText);
        addInfo = findViewById(R.id.textInputLayout);
        submit = findViewById(R.id.submitButton);
        viewReqs = findViewById(R.id.viewRequestsButton);
        dateSelection = findViewById(R.id.dateSelection);
        goBack = findViewById(R.id.goback);
        selectDate = findViewById(R.id.selectDate);
        selectTime = findViewById(R.id.selectTime);
        timeSelection = findViewById(R.id.timeSelection);

        selectDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(),"date");
            }
        });

        selectTime.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "time");
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //sendRequest();
                Intent intent = new Intent(view.getContext(), UserRequestsView.class);
                startActivityForResult(intent, 0);
            }
        });

        viewReqs.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), UserRequestsView.class);
                startActivityForResult(intent, 0);
            }
        });

        goBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), UserOrTutor.class);
                startActivityForResult(intent, 0);
            }
        });
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, i);
        c.set(Calendar.MONTH, i1);
        c.set(Calendar.DAY_OF_MONTH, i2);

        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
            //must parse thru this string to get important info
        if (isValidDateRequest(i, i1, i2)) {
            dateSelection.setText(currentDateString);
            dateTimeInfo[0] = i;
            dateTimeInfo[1] = i1;
            dateTimeInfo[2] = i2;
        } else {
            Toast toast = Toast.makeText(this, "You can only request a tutor session within a week of today.", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {
        if (isValidTimeRequest(i, i1)) {
            timeSelection.setText(String.format("%d:%d %s", i, i1, "am/pm"));
            dateTimeInfo[3] = i;
            dateTimeInfo[4] = i1;
            //need to ask david about the am/pm situation
        } else {
            Toast toast = Toast.makeText(this, "Please choose an acceptable time.", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    /**public void sendRequest() {
        String course_text = course.getText().toString().trim();
        String user_ID = CreateUser.currUser.getGTID();
        String addInfoText = addInfo.getEditText().toString().trim();
        String selectedDate = dateSelection.getText().toString().trim();
        String selectedTime = timeSelection.getText().toString().trim();

        if (!TextUtils.isEmpty(course_text) && !TextUtils.isEmpty(selectedDate) && !TextUtils.isEmpty(selectedTime)) {
            String request_ID = database.push().getKey();
            Time approvedTime = new Time(dateTimeInfo[0], dateTimeInfo[1], dateTimeInfo[2], dateTimeInfo[3], dateTimeInfo[4]);
            Request req = new Request(course_text, addInfoText, approvedTime, user_ID, "", request_ID);
            database.child(request_ID).setValue(req);

            Toast toast = Toast.makeText(this, "Request Created", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        } else {
            Toast toast = Toast.makeText(this, "Please Enter an Acceptable Date and Time", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }**/

    private boolean isValidDateRequest(int year, int month, int day_of_month) {
        //decide if the date request is within the acceptable bounds
        return true;
    }

    private boolean isValidTimeRequest(int hour, int minute) {
        //decide if the date request is within the acceptable bounds
        return true;
    }
}
