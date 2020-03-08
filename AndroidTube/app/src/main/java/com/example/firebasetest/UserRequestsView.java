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

    DatabaseReference database = FirebaseDatabase.getInstance().
            getReference(Constants.RequestsDBName);

    LinearLayout unmatchedRequestsView;
    LinearLayout matchedRequestsView;
    LinearLayout addInfoSection;

    ArrayList<Request> unmatchedRequestsList;
    ArrayList<Request> matchedRequestsList;

    Button makeRequestButton;
    Button cancelButton;
    Button shrinkButton;

    TextView addInfoView;
    Request selectedRequest;

    /**
     * Instantiates class variables and sets buttons
     * @param savedInstanceState some instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_requests_view);

        makeRequestButton = (Button) findViewById(R.id.backToMR);
        unmatchedRequestsList = new ArrayList<Request>();
        matchedRequestsList = new ArrayList<Request>();
        unmatchedRequestsView = (LinearLayout) findViewById(R.id.allRequestsList);
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

    /**
     * Sets up the even listeners on both the Matched and Unmatched Requests databases
     * Changes the respective lists accordingly
     */
    @Override
    protected void onStart() {
        super.onStart();

        database.child(Constants.UnmatchedRequestsDBName).child(CreateUser.currUser.getUserID()).
                addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                unmatchedRequestsList =  new ArrayList<>();
                for (DataSnapshot each : dataSnapshot.getChildren()) {
                    insertionSortAdd(unmatchedRequestsList, each.getValue(Request.class));
                }
                changeUnmatchedText();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        database.child(Constants.MatchedRequestsDBName).child(CreateUser.currUser.getUserID()).
                addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                matchedRequestsList =  new ArrayList<>();
                for (DataSnapshot each : dataSnapshot.getChildren()) {
                    insertionSortAdd(matchedRequestsList, each.getValue(Request.class));
                }
                changeMatchedText();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    /**
     * This method updates the unmatchedRequestsView and matchedRequestsView, assigning a button to each
     * request in requestsList and linking that button to the addInfoSection that pops up at the bottom.
     */
    private void changeText() {
        unmatchedRequestsView.removeAllViews();
        for (Request each : unmatchedRequestsList) {
            Button eachButton = new Button(this);
            linkButton(eachButton, each);
            unmatchedRequestsView.addView(eachButton);
        }
        matchedRequestsView.removeAllViews();
        for (Request each : matchedRequestsList) {
            Button eachButton = new Button(this);
            linkButton(eachButton, each);
            matchedRequestsView.addView(eachButton);
        }
    }

    /**
     * This method updates the matchedRequestsView, assigning a button to each request in
     * matchedRequestsList and linking that button to the addInfoSection
     */
    private void changeMatchedText() {
        matchedRequestsView.removeAllViews();
        for (Request each : matchedRequestsList) {
            Button eachButton = new Button(this);
            linkButton(eachButton, each);
            matchedRequestsView.addView(eachButton);
        }
    }

    /**
     * This method updates the unmatchedRequestsView, assigning a button to each request in
     * unmatchedRequestsList and linking that button to the addInfoSection
     */
    private void changeUnmatchedText() {
        unmatchedRequestsView.removeAllViews();
        for (Request each : unmatchedRequestsList) {
            Button eachButton = new Button(this);
            linkButton(eachButton, each);
            unmatchedRequestsView.addView(eachButton);
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
     * This method adds a request r to unmatchedRequestsList and matchedRequestsList, delegating to the
     * insertionSortAdd method, only if the userID of r is equal to the userID of currUser
     * @param r the request to be added
     */
    private void processRequest(Request r) {
<<<<<<< HEAD:AndroidTube/app/src/main/java/com/example/firebasetest/UserRequestsView.java
<<<<<<< HEAD:AndroidTube/app/src/main/java/com/example/firebasetest/UserRequestsView.java
        if (CreateUser.currUser.getUserID().equals(r.getUserID())) {
            insertionSortAdd(unmatchedRequestsList, r);
=======
=======
>>>>>>> 775eeb6b29322504fc66c6289c12dda508aeac07:app/src/main/java/com/example/firebasetest/UserRequestsView.java
        if (CreateUser.currUser.getGTID().equals(r.getUserID())) {
            insertionSortAdd(allRequestsList, r);
>>>>>>> 775eeb6b29322504fc66c6289c12dda508aeac07:app/src/main/java/com/example/firebasetest/UserRequestsView.java
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