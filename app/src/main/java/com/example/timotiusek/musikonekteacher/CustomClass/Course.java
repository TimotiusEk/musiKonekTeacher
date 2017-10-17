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
import com.example.timotiusek.musikonekteacher.ViewOrderActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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

    public static void rejectThisCourse(final Activity activity, final int id, final String why) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences("profile", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");

        RequestQueue requestQueue;
        Cache cache = new DiskBasedCache(activity.getCacheDir(), 1024 * 1024); // 1MB cap
        final Network network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache, network);
        requestQueue.start();
        String url = Connector.getURL() + "/api/v1/course/teacherRejectRequest";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ArrayList<Appointment> output = new ArrayList<>();
                        try {
                            JSONObject res = new JSONObject(response);
                            Log.d("DEBUG", res.toString());
                            if(res.getBoolean("success")) {
                                Toast.makeText(activity, "Request Ditolak", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(activity, "Tolak Request Gagal", Toast.LENGTH_LONG).show();
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
            protected Map<String,String> getParams(){
                Map<String,String> reqBody = new HashMap<String, String>();
                reqBody.put("token", activity.getSharedPreferences("profile", Context.MODE_PRIVATE).getString("token", ""));
                reqBody.put("course_id", id+"");
                reqBody.put("reason", why);

                return reqBody;
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                return super.parseNetworkResponse(response);
            }
        };
        requestQueue.add(stringRequest);
    }

    public static void acknowledgeThisCourse(final Activity activity, final int id) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences("profile", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");

        RequestQueue requestQueue;
        Cache cache = new DiskBasedCache(activity.getCacheDir(), 1024 * 1024); // 1MB cap
        final Network network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache, network);
        requestQueue.start();
        String url = Connector.getURL() + "/api/v1/course/teacherAcknowledgeRequest";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ArrayList<Appointment> output = new ArrayList<>();
                        try {
                            JSONObject res = new JSONObject(response);
                            Log.d("DEBUG", res.toString());
                            if(res.getBoolean("success")) {
                                Toast.makeText(activity, "Request Diterima", Toast.LENGTH_LONG).show();
                                if(activity instanceof ViewOrderActivity) {
                                    ((ViewOrderActivity) activity).onCourseAccepted();
                                }
                            } else {
                                Toast.makeText(activity, "Terima Request Gagal", Toast.LENGTH_LONG).show();
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
            protected Map<String,String> getParams(){
                Map<String,String> reqBody = new HashMap<String, String>();
                reqBody.put("token", activity.getSharedPreferences("profile", Context.MODE_PRIVATE).getString("token", ""));
                reqBody.put("course_id", id+"");

                return reqBody;
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                return super.parseNetworkResponse(response);
            }
        };
        requestQueue.add(stringRequest);
    }

    public static void acceptThisCourse(final Activity activity, final int id, final JSONArray appointmentId, final JSONArray time) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences("profile", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");

        RequestQueue requestQueue;
        Cache cache = new DiskBasedCache(activity.getCacheDir(), 1024 * 1024); // 1MB cap
        final Network network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache, network);
        requestQueue.start();
        String url = Connector.getURL() + "/api/v1/course/teacherAcknowledgeRequest";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ArrayList<Appointment> output = new ArrayList<>();
                        try {
                            JSONObject res = new JSONObject(response);
                            Log.d("DEBUG", res.toString());
                            if(res.getBoolean("success")) {
                                Toast.makeText(activity, "Request Diterima", Toast.LENGTH_LONG).show();
                                if(activity instanceof AcceptedStudentInfoActivity) {
                                    ((AcceptedStudentInfoActivity) activity).onCourseAccepted();
                                }
                            } else {
                                Toast.makeText(activity, "Terima Request Gagal", Toast.LENGTH_LONG).show();
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
            protected Map<String,String> getParams(){
                Map<String,String> reqBody = new HashMap<String, String>();
                reqBody.put("token", activity.getSharedPreferences("profile", Context.MODE_PRIVATE).getString("token", ""));
                reqBody.put("course_id", id+"");
                reqBody.put("appointment_id", appointmentId.toString());
                reqBody.put("appointment_time", time.toString());

                return reqBody;
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                return super.parseNetworkResponse(response);
            }
        };
        requestQueue.add(stringRequest);
    }

}
