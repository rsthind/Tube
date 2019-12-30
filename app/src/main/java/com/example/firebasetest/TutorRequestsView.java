package com.example.firebasetest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TutorRequestsView extends AppCompatActivity {

    DatabaseReference database = FirebaseDatabase.getInstance().getReference("Requests");
    ViewGroup rView;
    ArrayList<ArrayList<Request>> allRequestsList;
    HashMap<String, ArrayList<Request>> allRequestsMap;
    ArrayList<Request> requestsList;
    ArrayList<String> verifiedCourses;
    LinearLayout addInfoSection;
    TextView addInfoView;
    Button shrinkButton;
    Button matchButton;
    Button viewAppointmentsButton;

    Request selectedRequest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_requests_view);

        rView = (ViewGroup) findViewById(R.id.requestsList);
        requestsList = new ArrayList<Request>();
        verifiedCourses = CreateUser.currUser.getVerifiedCourses();
        allRequestsMap = new HashMap<>();
        for (String s : verifiedCourses) {
            allRequestsMap.put(s, new ArrayList<Request>());
        }
        shrinkButton = (Button) findViewById(R.id.shrinkButton);
        matchButton = (Button) findViewById(R.id.matchButton);
        addInfoSection = (LinearLayout) findViewById(R.id.addInfoSection);
        addInfoView = (TextView) findViewById(R.id.addInfoView);
        addInfoView.setText(showButtonText(new Request()));
        viewAppointmentsButton = (Button) findViewById(R.id.viewAppointmentsButton);

        /*
        Shrink button: makes the addInfoSection invisible
         */
        shrinkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addInfoSection.setVisibility(View.INVISIBLE);
            }
        });

        /*
        Match button: sets the tutorID field of the selected request to the current user's ID
         */
        matchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedRequest.setTutorID(CreateUser.currUser.getUserID());
                database.child(selectedRequest.getRequestID()).setValue(selectedRequest);
                addInfoSection.setVisibility(View.INVISIBLE);
            }
        });

        /*
        View Appointments button: makes an intent to the view appointments class
         */
        viewAppointmentsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(v.getContext(), MakeRequest.class);
                startActivityForResult(myIntent, 0);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // AGH JUST UGLY BRUTE FORCE SOLUTION:((((
                requestsList = new ArrayList<Request>();
                for (DataSnapshot each : dataSnapshot.getChildren()) {
                    addToMapIfAble(each.getValue(Request.class));
                    insertionSortAddIfAble(requestsList, each.getValue(Request.class));
                }
                changeText();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    /**
     * This method updates the scrollView, assigning a button to each request in requestsList and
     * linking that button to the addInfoSection that pops up at the bottom.
     */
    private void changeText() {
        rView.removeAllViews();
        for (Request each : requestsList) {
            Button eachButton = new Button(this);
            linkButton(eachButton, each);
            rView.addView(eachButton);
        }
    }

    /**
     * This method is used when adding the Requests from the database so that the instance RequestsList
     * is always sorted
     * @param r the request to be added
     */
    private void insertionSortAdd(List<Request> l, Request r) {
        if (l.size() == 0) {
            l.add(r);
        } else {
            int addIndex = l.size();
            for (int i = l.size() - 1; i >= 0 && r.compareTo(l.get(i)) < 0; i--) {
                addIndex = i;
            }
            l.add(addIndex, r);
        }
    }

    /**
     * This method is similar to insertionSortAdd but only adds the request if its course is part
     * of the verifiedCourses list
     * @param r the request to be added
     */
    private void insertionSortAddIfAble(List<Request> l, Request r) {
        if (verifiedCourses.contains(r.getCourse())) {
            if (l.size() == 0) {
                l.add(r);
            } else {
                int addIndex = l.size();
                for (int i = l.size() - 1; i >= 0 && r.compareTo(l.get(i)) < 0; i--) {
                    addIndex = i;
                }
                l.add(addIndex, r);
            }
        }
    }

    /**
     * This method adds a request to the corresponding course's ArrayList in the allRequestsMap
     * @param r the request to be added
     */
    private void addToMapIfAble(Request r) {
        if (verifiedCourses.contains(r.getCourse())) {
            insertionSortAdd(allRequestsMap.get(r.getCourse()), r);
        }
    }

    /**
     * This method links a button so that upon clicking it it will set the additional information
     * section to display the info associated with @param r
     * @param b the button to be set
     * @param r the request to associate with the button
     */
    public void linkButton(Button b, final Request r) {
        b.setText(r.toShortString());
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addInfoView.setText(showButtonText(r));
                addInfoSection.setVisibility(View.VISIBLE);
                selectedRequest = r;
            }
        });
    }

    /**
     * This method will return all the associated information (long handed info) of the @param req.
     * This method is used in the linkButton method
     * @param req the request whose information will be returned
     * @return the associated long-hand information of the request
     */
    // TODO: for the tutor, include the name of the user who sent it?
    public String showButtonText(Request req) {
        // Temporary addition of the user ID --> the User ID should actually be the user's name or
        // GTID
        String match = (req.getTutorID().equals("")) ? "No match yet" : req.getTutorID();

        String formattedString = "User ID: " + req.getUserID() + "\n";
        formattedString += "Match ID: " + match + "\n";
        formattedString += req.toLongString();
        formattedString += ("\nAdditional Information: " + req.getAddInfo());
        return formattedString;
    }

}
