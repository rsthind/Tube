package com.example.firebasetest;

import java.util.ArrayList;

// TODO: make the user class keep track of it's own ID --> CRUCIAL
public class User {

    private String firstName;
    private String lastName;
    private String GTID;
    private String password;
    private String userID;
    private ArrayList<String> verifiedCourses;

    public User() {
        this.firstName = "John";
        this.lastName = "Doe";
        this.GTID = "0000000000";
        this.password = "";
        this.userID = "No User ID";
        this.verifiedCourses = new ArrayList<String>();
        // for testing purposes
        verifiedCourses.add("CS 1331");
        verifiedCourses.add("CS 2050");
    }

    public User(String firstName, String lastName, String GTID, String password, String userID, ArrayList<String> verifiedCourses) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.GTID = GTID;
        this.password = password;
        this.userID = userID;
        this.verifiedCourses = verifiedCourses;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getGTID() {
        return this.GTID;
    }

    public String getPassword() {
        return this.password;
    }

    public ArrayList<String> getVerifiedCourses() {
        return this.verifiedCourses;
    }

    public String getUserID() {
        return this.userID;
    }
}
