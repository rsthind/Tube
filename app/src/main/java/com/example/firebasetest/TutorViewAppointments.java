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
import java.util.List;

public class TutorViewAppointments extends AppCompatActivity {

    DatabaseReference database = FirebaseDatabase.getInstance().
            getReference(Constants.RequestsDBName);

    ViewGroup rView;
    LinearLayout addInfoSection;
    TextView addInfoView;

    ArrayList<Request> requestsList;

    Button viewRequests;
    Button cancelButton;
    Button shrinkButton;

    Request selectedRequest;

    /**
     * Instantiates class variables and sets buttons
     * @param savedInstanceState some instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_view_appointments);

        requestsList = new ArrayList<Request>();
        viewRequests = (Button) findViewById(R.id.viewRequestsButton);
        rView = (ViewGroup) findViewById(R.id.allRequestsList);
        cancelButton = (Button) findViewById(R.id.cancelButton);
        shrinkButton = (Button) findViewById(R.id.shrinkButton);
        addInfoSection = (LinearLayout) findViewById(R.id.addInfoSection);
        addInfoView = (TextView) findViewById(R.id.addInfoView);
        addInfoView.setText(showButtonText(new Request()));

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
        Make Request button: all it does it sent an intent to go back to the MakeRequest activity
         */
        viewRequests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(v.getContext(), TutorRequestsView.class);
                startActivityForResult(myIntent, 0);
            }
        });

        /*
        Cancel button: clears the addInfoView, deletes request from database, addInfoSection becomes
        invisible again.
         */
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addInfoView.setText("");
                // note: this doesn't actually get rid of the request itself, it only gets rid of
                // its spot in the cloud, so the request is still in requestsList

                database.child(Constants.MatchedRequestsDBName).child(selectedRequest.
                        getUserID()).child(selectedRequest.getRequestID()).removeValue();

                database.child(Constants.UnmatchedRequestsDBName).child(selectedRequest.
                        getUserID()).child(selectedRequest.getRequestID()).setValue(selectedRequest);

                addInfoSection.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        database.child(Constants.MatchedRequestsDBName).
                addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                requestsList = new ArrayList<>();
                for (DataSnapshot user : dataSnapshot.getChildren()) {
                    for (DataSnapshot userReq : user.getChildren()) {
                        addRequest(requestsList, userReq.getValue(Request.class));
                    }
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
    private static void insertionSortAdd(List<Request> l, Request r) {
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
     * of the verifiedCourses list.
     * userID
     * @param r the request to be added
     */
    private void addRequest(List<Request> l, Request r) {
        if (CreateUser.currUser.getUserID().equals(r.getTutorID())) {
            insertionSortAdd(l, r);
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
