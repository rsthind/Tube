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
import java.util.GregorianCalendar;

<<<<<<< HEAD:AndroidTube/app/src/main/java/com/example/firebasetest/MakeRequest.java
    DatabaseReference database =
            FirebaseDatabase.getInstance().getReference(Constants.RequestsDBName);
=======
public class MakeRequest extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    DatabaseReference database = FirebaseDatabase.getInstance().getReference("Requests");//
>>>>>>> 775eeb6b29322504fc66c6289c12dda508aeac07:app/src/main/java/com/example/firebasetest/MakeRequest.java

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
                sendRequest();
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
            String ampm;
            int newhour;
            if (i > 12) {
                ampm = "PM";
                newhour = i - 12;
            } else {
                ampm = "AM";
                newhour = i;
            }

            timeSelection.setText(String.format("%d:%d %s", newhour, i1, ampm));
            dateTimeInfo[3] = i;
            dateTimeInfo[4] = i1;
        } else {
            Toast toast = Toast.makeText(this, "Please choose an acceptable time.", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

<<<<<<< HEAD:AndroidTube/app/src/main/java/com/example/firebasetest/MakeRequest.java
        if(!TextUtils.isEmpty(course_send)) { // replace this line with seeing if the time is valid
            String request_ID = database.push().getKey();
            Request req = new Request(course_send, addInfoString, new Time(), user_ID, "", request_ID);
//            database.child(request_ID).setValue(req);
            database.child(Constants.UnmatchedRequestsDBName).child(CreateUser.currUser.getUserID()).
                    child(request_ID).setValue(req);
=======
    public void sendRequest() {
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
>>>>>>> 775eeb6b29322504fc66c6289c12dda508aeac07:app/src/main/java/com/example/firebasetest/MakeRequest.java

            Toast toast = Toast.makeText(this, "Request Created", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        } else {
            Toast toast = Toast.makeText(this, "Please Enter an Acceptable Date and Time", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    private boolean isValidDateRequest(int year, int month, int day_of_month) {
        //try restricting the date selection from the GUI itself
        GregorianCalendar currentTime = new GregorianCalendar();
        GregorianCalendar enteredTime = new GregorianCalendar(year, month, day_of_month);

        long diffInSeconds = enteredTime.getTimeInMillis() - currentTime.getTimeInMillis();

        if (diffInSeconds > 604800) {
            Toast toast = Toast.makeText(this, "Cannot Reserve a Session Past 1 Week.", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return  false;
        } else if (diffInSeconds < 0){
            Toast toast = Toast.makeText(this, "Not a Valid Date Entry", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return  false;
        } else {
            return true;
        }
    }

    private boolean isValidTimeRequest(int hour, int minute) {
        GregorianCalendar now = new GregorianCalendar();

        int currTime = 60 * now.get(Calendar.HOUR) + now.get(Calendar.MINUTE);
        int requestedTime;

        if (hour > 12) {
            requestedTime = 60 * (hour - 12) + minute;
        } else {
            requestedTime = 60 * hour + minute;
        }

        if (requestedTime - currTime < 0) {
            Toast toast = Toast.makeText(this, "Invalid Time Entry", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return false;
        } else if (requestedTime - currTime < 60) {
            Toast toast = Toast.makeText(this, "Must make entry at least one hour in advance.", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return false;
        } else {
            return true;
        }

    }
}