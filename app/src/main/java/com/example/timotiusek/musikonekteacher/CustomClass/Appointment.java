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
import com.example.timotiusek.musikonekteacher.Helper.TextFormater;
import com.example.timotiusek.musikonekteacher.R;
import com.example.timotiusek.musikonekteacher.ViewOrderActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by USER on 27/09/2017.
 */

public class Appointment {

    private String appointmentId;
    private String appointmentTime;
    private String attendanceTeacher;
    private String attendanceStudent;

    public Appointment(String appointmentId, String appointmentTime, String attendanceTeacher, String attendanceStudent) {
        this.appointmentId = appointmentId;
        this.appointmentTime = appointmentTime;
        this.attendanceTeacher = attendanceTeacher;
        this.attendanceStudent = attendanceStudent;
    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(String appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public String getAttendanceTeacher() {
        return attendanceTeacher;
    }

    public void setAttendanceTeacher(String attendanceTeacher) {
        this.attendanceTeacher = attendanceTeacher;
    }

    public String getAttendanceStudent() {
        return attendanceStudent;
    }

    public void setAttendanceStudent(String attendanceStudent) {
        this.attendanceStudent = attendanceStudent;
    }

    public static void getAppointmentByCourseId(final Activity activity, int courseId) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences("profile", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");

        RequestQueue requestQueue;
        Cache cache = new DiskBasedCache(activity.getCacheDir(), 1024 * 1024); // 1MB cap
        final Network network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache, network);
        requestQueue.start();
        String url = Connector.getURL() + "/api/v1/appointment/getAppointmentsByCourseId?token=" + token +
                                          "&course_id=" + courseId;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ArrayList<Appointment> output = new ArrayList<>();
                        try {
                            JSONObject res = new JSONObject(response);
                            Log.d("DEBUG", res.toString());
                            JSONArray arr = res.getJSONArray("data");
                            for(int i = 0; i < arr.length(); i++) {
                                String appointmentId     = ((JSONObject) arr.get(i)).getString("appointment_id");
                                String appointmentTime   = ((JSONObject) arr.get(i)).getString("appointment_time");
                                String attendanceTeacher = ((JSONObject) arr.get(i)).getString("attendance_teacher");
                                String attendanceStudent = ((JSONObject) arr.get(i)).getString("attendance_student");
                                output.add(new Appointment(appointmentId, appointmentTime, attendanceTeacher, attendanceStudent));
                            }
                            if(activity instanceof ViewOrderActivity) {
                                ((ViewOrderActivity) activity).onDataReady(output);
                            }
                            if(activity instanceof AcceptedStudentInfoActivity) {
                                ((AcceptedStudentInfoActivity) activity).onDataReady(output);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        NetworkResponse networkResponse = error.networkResponse;
                        if (networkResponse == null) {
                            Toast.makeText(activity, "Connection Error", Toast.LENGTH_SHORT).show();
                        } else {
                            if (networkResponse.statusCode == 403) {
                                Toast.makeText(activity, "TOKEN INVALID, PLEASE RE LOG", Toast.LENGTH_SHORT).show();
                            } else if (networkResponse.statusCode == 500) {
                                Toast.makeText(activity, "INVALID CREDENTIALS", Toast.LENGTH_SHORT).show();
                            } else if (networkResponse.statusCode != 401) {
                                Log.d("ASDF", "SHIT");
                            }
                        }
                    }
                }) {

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                return super.parseNetworkResponse(response);
            }
        };
        requestQueue.add(stringRequest);
    }
}
