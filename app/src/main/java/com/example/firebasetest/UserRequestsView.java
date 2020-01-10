package com.example.firebasetest;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class UserRequestsView extends AppCompatActivity {

    DatabaseReference database = FirebaseDatabase.getInstance().getReference("Requests");
    LinearLayout allRequestsView;
    LinearLayout matchedRequestsView;
    ArrayList<Request> allRequestsList; // potential idea --> put this variable in the new class that
    // has all the static variables so that it's already stored and you don't need to load it
    // every time
    ArrayList<Request> matchedRequestsList;
    Button makeRequestButton;
    Button cancelButton;
    Button shrinkButton;
    LinearLayout addInfoSection;
    TextView addInfoView;

    Request selectedRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_requests_view);

        makeRequestButton = (Button) findViewById(R.id.backToMR);
        allRequestsList = new ArrayList<Request>();
        matchedRequestsList = new ArrayList<Request>();
        allRequestsView = (LinearLayout) findViewById(R.id.allRequestsList);
        matchedRequestsView = (LinearLayout) findViewById(R.id.matchedRequestsList);
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
        makeRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(v.getContext(), MakeRequest.class);
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
                database.child(selectedRequest.getRequestID()).removeValue();
                addInfoSection.setVisibility(View.INVISIBLE);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int count = 0;
                int i;
                for (DataSnapshot each : dataSnapshot.getChildren()) {
                    count++;
                }
                /*
                if (count > requestsList.size()) {
                    i = 1;
                    for (DataSnapshot each : dataSnapshot.getChildren()) {
                        if (i++ > requestsList.size()) {
                            Request req = each.getValue(Request.class);
                            insertionSortAdd(req);
                        }
                    }
                } else if (count < requestsList.size()){
                    requestsList = new ArrayList<Request>();
                    // NOTE: this is extremely inefficient (i think?)
                    for (DataSnapshot each : dataSnapshot.getChildren()) {
                        Request req = each.getValue(Request.class);
                        insertionSortAdd(req);
                    }
                }*/

                // AGH JUST UGLY BRUTE FORCE SOLUTION:((((
                allRequestsList = new ArrayList<Request>();
                matchedRequestsList = new ArrayList<Request>();
                for (DataSnapshot each : dataSnapshot.getChildren()) {
                    processRequest(each.getValue(Request.class));
                }
                changeText();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    /**
     * This method updates the allRequestsView and matchedRequestsView, assigning a button to each
     * request in requestsList and linking that button to the addInfoSection that pops up at the bottom.
     */
    private void changeText() {
        allRequestsView.removeAllViews();
        matchedRequestsView.removeAllViews();
        for (Request each : allRequestsList) {
            Button eachButton = new Button(this);
            linkButton(eachButton, each);
            allRequestsView.addView(eachButton);
        }
        for (Request each : matchedRequestsList) {
            Button eachButton = new Button(this);
            linkButton(eachButton, each);
            matchedRequestsView.addView(eachButton);
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
     * This method adds a request r to allRequestsList and matchedRequestsList, delegating to the
     * insertionSortAdd method, only if the userID of r is equal to the userID of currUser
     * @param r the request to be added
     */
    private void processRequest(Request r) {
        if (CreateUser.currUser.getGTID().equals(r.getUserID())) {
            insertionSortAdd(allRequestsList, r);
            if (r.getTutorID().length() != 0) {
                insertionSortAdd(matchedRequestsList, r);
            }
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

        String formattedString = "User ID: " + req.getUserID();
        formattedString += "\t\t || \t\tMatch ID: " + match + "\n";
        formattedString += req.toLongString();
        formattedString += ("\nAdditional Information: " + req.getAddInfo());
        return formattedString;
    }
}