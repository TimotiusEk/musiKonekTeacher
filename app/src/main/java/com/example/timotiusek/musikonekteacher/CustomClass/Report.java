package com.example.timotiusek.musikonekteacher.CustomClass;

/**
 * Created by TimotiusEk on 8/3/2017.
 */

public class Report {
    private String title;
    private String date;

    public Report(String title, String date) {
        this.title = title;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
