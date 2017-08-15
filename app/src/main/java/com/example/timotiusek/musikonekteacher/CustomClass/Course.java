package com.example.timotiusek.musikonekteacher.CustomClass;

/**
 * Created by TimotiusEk on 8/4/2017.
 */

public class Course {
    private String name;
    private String howManyMeetings;
    private String id;

    public Course(String name, String howManyMeetings, String id) {
        this.name = name;
        this.howManyMeetings = howManyMeetings;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
