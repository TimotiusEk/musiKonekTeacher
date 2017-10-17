package com.example.timotiusek.musikonekteacher.CustomClass;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.example.timotiusek.musikonekteacher.AcceptedStudentInfoActivity;
import com.example.timotiusek.musikonekteacher.Helper.Connector;
import com.example.timotiusek.musikonekteacher.SetScheduleActivity;
import com.example.timotiusek.musikonekteacher.ViewOrderActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

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
