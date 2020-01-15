package com.example.firebasetest;

import java.util.GregorianCalendar;

/**
 * @author David Kwon / TUBE
 * @version 3.0
 */

 /*
Sample time string: Thursday, December 12th @ 5:30 PM
Feature to add later on: you can choose between this display and somethimg more numerical like
12/12/19 @ 5:30 PM

UPDATE: Time will now look like this:

10:15 AM || Thursday, December 12 2019
 */
public class Time implements Comparable<Time> {

    private int year;
    private int month;
    private int date;
    private int hourOfDay;
    private int minute;

    public Time() {
        GregorianCalendar gc = new GregorianCalendar();
        this.year = gc.get(GregorianCalendar.YEAR);
        this.month = gc.get(GregorianCalendar.MONTH) + 1;
        this.date = gc.get(GregorianCalendar.DATE);
        this.hourOfDay = gc.get(GregorianCalendar.HOUR_OF_DAY);
        this.minute = gc.get(GregorianCalendar.MINUTE);
    }

    public Time(int year, int month, int date, int hourOfDay, int minute) {
        this.year = year;
        this.month = month;
        this.date = date;
        this.hourOfDay = hourOfDay;
        this.minute = minute;
    }

    public Time (Time other) {
        this(other.year, other.month, other.date, other.hourOfDay, other.minute);
    }

    @Override
    public int compareTo(Time other) {
        GregorianCalendar gcThis = new GregorianCalendar(year, month - 1, date, hourOfDay, minute);
        GregorianCalendar gcOther = new GregorianCalendar(other.year, other.month - 1, other.date, other.hourOfDay, other.minute);
        return gcThis.compareTo(gcOther);
    }

    public String toLongString() {
        String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        String[] days = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        String currHour = Time.timeConvertion(this.hourOfDay)[0];
        String AM_PM = Time.timeConvertion(this.hourOfDay)[1];
        GregorianCalendar cal = new GregorianCalendar(year, month - 1, date, hourOfDay, minute);
        int dayOfWeek = cal.get(GregorianCalendar.DAY_OF_WEEK);
        String minuteString = (minute < 10) ? "0" + minute : "" + minute;

//        String formattedTime = days[dayOfWeek - 1] + ", " + months[month - 1] + " " + date + " " + year + " @ " + currHour + ":" + minuteString
//                + " " + AM_PM;
        String formattedTime2;
        try {
            formattedTime2 = currHour + ":" + minuteString + " " + AM_PM + " || " + days[dayOfWeek - 1] + ", " + months[month - 1] + " " +
                    date + " " + year;
        } catch (IndexOutOfBoundsException e) {
            formattedTime2 = currHour + ":" + minuteString + " " + AM_PM + " || " + days[dayOfWeek - 1] + ", " + months[month] + " " +
                    date + " " + year;
        }

        return formattedTime2;
    }

    public String toShortString() {
        String currHour = Time.timeConvertion(this.hourOfDay)[0];
        String AM_PM = Time.timeConvertion(this.hourOfDay)[1];
        String minuteString = (minute < 10) ? "0" + minute : "" + minute;
        String formattedTime = "" + month + "/" + date + "/" + year + " @ " + currHour + ":" + minuteString
                + " " + AM_PM;
        String formattedTime2 = "" + currHour + ":" + minuteString + " " + AM_PM + " || " + month + "/" +
                date + "/" + year;
        return formattedTime2;
    }

    public static String[] timeConvertion(int hourOfDay) {
        String[] args = new String[2];
        args[1] = (hourOfDay > 11) ? "PM" : "AM";
        int hour = hourOfDay % 12;
        if (hour == 0) {
            hour = 12;
        }
        args[0] = "" + hour;
        return args;
    }

    public int getYear() {
        return this.year;
    }

    public int getMonth() {
        return this.month;
    }

    public int getDate() {
        return this.date;
    }

    public int getHourOfDay() {
        return this.hourOfDay;
    }

    public int getMinute() {
        return this.minute;
    }

    public static boolean inRange(Time inputTime) {
        /*
        static values:
        year = 1
        month = 2
        dayOfMonth = 5
        hourOfDay = 11
        minute = 12
        dayOfYear = 6
         */
        GregorianCalendar currCal = new GregorianCalendar();
        GregorianCalendar inputCal = new GregorianCalendar(inputTime.year,
                inputTime.month - 1, inputTime.date, inputTime.hourOfDay, inputTime.minute);
        GregorianCalendar minusOneCal = new GregorianCalendar(currCal.get(GregorianCalendar.YEAR), currCal.get(GregorianCalendar.MONTH),
                currCal.get(GregorianCalendar.DATE), currCal.get(GregorianCalendar.HOUR_OF_DAY) - 1, currCal.get(GregorianCalendar.MINUTE));

        if (inputCal.compareTo(minusOneCal) > 0) {
            return false;
        } else if (inputCal.get(GregorianCalendar.DAY_OF_YEAR) < currCal.get(GregorianCalendar.DAY_OF_YEAR) - 7) {
            return false;
        }
        return true;
    }

}
