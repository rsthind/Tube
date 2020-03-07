package com.example.firebasetest;

import java.util.ArrayList;

// TODO: make the user class keep track of it's own ID --> CRUCIAL
public class User {
    private String GTID, firstName, email, major, pw;

    private ArrayList<String> verifiedCourses;

    public User() {
        this.GTID = "0000000000";
        this.firstName = "John";
        this.email = "";
        this.pw = "";
        this.major = "No User ID";
        this.verifiedCourses = new ArrayList<>();
        // for testing purposes
        verifiedCourses.add("CS 1331");
        verifiedCourses.add("CS 1332");
    }


    public User(String GTID, String firstName, String email, String major, String password, ArrayList<String> verifiedCourses) {
        this.GTID = GTID;
        this.firstName = firstName;
        this.email = email;
        this.major = major;
        this.pw = password;
        this.verifiedCourses = verifiedCourses;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getGTID() {
        return this.GTID;
    }

    public ArrayList<String> getVerifiedCourses() {
        return this.verifiedCourses;
    }

    public String getEmail() {
        return email;
    }

    public String getMajor() {
        return major;
    }

    public String getPw() {
        return pw;
    }

}
