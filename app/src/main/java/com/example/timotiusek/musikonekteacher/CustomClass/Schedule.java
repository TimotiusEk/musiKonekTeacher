package com.example.timotiusek.musikonekteacher.CustomClass;

/**
 * Created by TimotiusEk on 8/3/2017.
 */

public class Schedule {
    private String whichMeeting;
    private String date;

    public Schedule(String whichMeeting, String date) {
        this.whichMeeting = whichMeeting;
        this.date = date;
    }

    public String getWhichMeeting() {
        return whichMeeting;
    }

    public void setWhichMeeting(String whichMeeting) {
        this.whichMeeting = whichMeeting;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
