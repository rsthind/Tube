package com.example.firebasetest;

import java.util.Scanner;

/**
 * @author David Kwon / TUBE
 * @version 3.0
 */

// Requests will be shown as:
 /*
 Course: CS 1331
 Time: Thursday, December 12 2019 @ 10:15 AM

 10:15 AM || Thursday, December 12 2019

 Note: the "addInfo" field is maximized at 75 characters
 */
public final class Request implements Comparable<Request> {

    private String course;
    private String addInfo;
    private Time time;
    private String userID;
    private String tutorID;
    private String requestID;

    public Request() {
        this.course = "";
        this.addInfo = "";
        this.time = new Time();
        this.userID = "";
        this.tutorID = "";
        this.requestID = "";
    }

    public Request(String course, String addInfo, int year, int month, int date,
                   int hourOfDay, int minute, String userID, String tutorID, String requestID) {
        this.course = course;
        this.time = new Time(year, month, date, hourOfDay, minute);
        this.userID = userID;
        this.tutorID = tutorID;
        this.requestID = requestID;
    }

    public Request(String course, String addInfo, Time time, String userID, String tutorID, String requestID) {
        this.course = course;
        this.addInfo = addInfo;
        this.time = time;
        this.userID = userID;
        this.tutorID = tutorID;
        this.requestID = requestID;
    }

    /*
    Requests will be compared first by class name and number: for example, CS 1331 is less than CS 1332. Moreover,
    CS 1332 is less than MATH 1554. If two requests have identical course numbers, than they are compared based on
    their time.
    */
    @Override
    public int compareTo(Request other) {
        if (this.course.equals(other.course)) {
            return this.time.compareTo(other.time);
        } else {
            Scanner scThis = new Scanner(this.course);
            Scanner scOther = new Scanner(other.course);
            String classAbrevThis = scThis.next();
            int classNumThis = Integer.parseInt(scThis.next());
            String classAbrevOther = scOther.next();
            int classNumOther = Integer.parseInt(scOther.next());
            if (classAbrevThis.equals(classAbrevOther)) {
                return classNumThis - classNumOther;
            } else {
                return classAbrevThis.compareTo(classAbrevOther);
            }
        }
    }

    public String toShortString() {
        String str = this.course + "\n";
        str += this.time.toShortString();
        return str;
    }

    public String toLongString() {
        String str = "Course: " + this.course + "\n";
        str += "Time: " + this.time.toLongString();
        return str;
    }

    public String getCourse() {
        return this.course;
    }

    public Time getTime() {
        return this.time;
    }

    public String getAddInfo() {
        return this.addInfo;
    }

    public String getUserID() {
        return this.userID;
    }

    public String getTutorID() {
        return this.tutorID;
    }

    public String getRequestID() {
        return this.requestID;
    }

    public void setTutorID(String tutorID) {
        this.tutorID = tutorID;
    }

}