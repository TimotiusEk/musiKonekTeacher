package com.example.timotiusek.musikonekteacher.CustomClass;

/**
 * Created by TimotiusEk on 8/4/2017.
 */

public class Course {
    private String name;
    private String howManyMeetings;

    public Course(String name, String howManyMeetings) {
        this.name = name;
        this.howManyMeetings = howManyMeetings;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHowManyMeetings() {
        return howManyMeetings;
    }

    public void setHowManyMeetings(String howManyMeetings) {
        this.howManyMeetings = howManyMeetings;
    }
}
