package com.example.timotiusek.musikonekteacher.CustomClass;

import android.content.Context;
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
import com.example.timotiusek.musikonekteacher.Helper.Connector;
import com.example.timotiusek.musikonekteacher.WeeklyScheduleFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by MOTI on 04/07/2017.
 */

public class ScheduleController {

    /**
     * Use these variables to detect wether a teacher is unavailable, available or already assigned to an appointment
     * Used to avoid a hard coded int to make a comparison
     */
    public static final int UNAVAILABLE = 0;
    public static final int AVAILABLE = 1;
    public static final int OCCUPIED = 2;
    /**
     * This field can be used outside the class to generate the JSONObject with a correct key
     * Call ScheduleController.days[x]
     */
    public static final String[] days =
            new String[] {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};

    private int[] schedule = null;
    private JSONObject appointment = null;
    private class AppointmentModel {
        public int appointment_id;
        public int course_id;
        public int status_id;
        public Timestamp appointment_time;
        public boolean attendance_teacher;
        public boolean attendance_student;
    }

    private void setSchedule(int[] schedule) {
        this.schedule = schedule;
    }

    private void setAppointment(JSONObject appointment) {
        this.appointment = appointment;
    }

    private void getTeacherAppointment(final WeeklyScheduleFragment activity, final Context context) {
        RequestQueue requestQueue;
        Cache cache = new DiskBasedCache(context.getCacheDir(), 1024 * 1024); // 1MB cap
        final Network network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache, network);
        requestQueue.start();

        String token = context.getSharedPreferences("profile", Context.MODE_PRIVATE).getString("token", "");
        String url = Connector.getURL() +"/api/v1/appointment/getTeacherAppointmentThisWeek?token=" + token;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    private Date parse(String timestamp) {
                        DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                        Date date = null;
                        try {
                            date = format.parse(timestamp);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        return date;
                    }

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject responseJSON = new JSONObject(response);
                            JSONArray data = responseJSON.optJSONArray("data");

                            JSONObject appointment = new JSONObject();
                            Date[][] time = new Date[data.length()][2];
                            ArrayList<String>[] appointmentList = (ArrayList<String>[]) new ArrayList[days.length];
                            for(int i = 0; i < appointmentList.length; i++) {
                                appointmentList[i] = new ArrayList<>();
                            }

                            JSONObject additionalData = new JSONObject();
                            ArrayList<String>[] addDataList = (ArrayList<String>[]) new ArrayList[days.length];
                            for(int i = 0; i < addDataList.length; i++) {
                                addDataList[i] = new ArrayList<>();
                            }

                            for(int i = 0; i < data.length(); i++) {
                                JSONObject row = (JSONObject) data.get(i);
                                time[i][0] = parse(row.getString("time_start"));
                                time[i][1] = parse(row.getString("time_end"));
                                String dayOfWeek = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(time[i][0]);
                                for(int j = 0; j < days.length; j++) {
                                    if(dayOfWeek.equals(days[j])) {
                                        appointmentList[j].add(MagicBox.dateStartEndFormatter(time[i][0], time[i][1]));

                                        JSONObject bundle = new JSONObject();
                                        bundle.put("student_name", row.getString("student_name"));
                                        bundle.put("program_name", row.getString("program_name"));
                                        addDataList[j].add(bundle.toString());
                                        break;
                                    }
                                }
                            }

                            for(int i = 0; i < days.length; i++) {
                                JSONArray appointmentForTheDay = new JSONArray();
                                for(int j = 0; j < appointmentList[i].size(); j++) {
                                    appointmentForTheDay.put(appointmentList[i].get(j));
                                }
                                appointment.put(days[i], appointmentForTheDay);

                                JSONArray additionalDataForTheDay = new JSONArray();
                                for(int j = 0; j < addDataList[i].size(); j++) {
                                    additionalDataForTheDay.put(addDataList[i].get(j));
                                }
                                additionalData.put(days[i], additionalDataForTheDay);
                            }
                            setAppointment(appointment);

                            Log.d("DEBUG", ScheduleController.this.appointment.toString());
                            activity.onDataReady(MagicBox.decodeDataFromServer(schedule, ScheduleController.this.appointment),
                                                 additionalData);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if(error.networkResponse == null) {
                            Toast.makeText(context, "Connection Error",Toast.LENGTH_SHORT).show();
                        } else if(error.networkResponse.statusCode == 403) {
                            Toast.makeText(context, "TOKEN INVALID, PLEASE RE-LOG",Toast.LENGTH_SHORT).show();
                        } else if(error.networkResponse.statusCode == 500) {
                            Toast.makeText(context, "INVALID CREDENTIALS",Toast.LENGTH_SHORT).show();
                        } else if(error.networkResponse.statusCode != 401) {
                            Log.d("DEBUG","Error 401");
                        } else {
                            Toast.makeText(context, "Unknown error: " + error.networkResponse.statusCode, Toast.LENGTH_SHORT).show();
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

    private void getSchedule(final WeeklyScheduleFragment activity, final Context context) {
        RequestQueue requestQueue;
        Cache cache = new DiskBasedCache(context.getCacheDir(), 1024 * 1024); // 1MB cap
        final Network network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache, network);
        requestQueue.start();

        String token = context.getSharedPreferences("profile", Context.MODE_PRIVATE).getString("token", "");
        String url = Connector.getURL() +"/api/v1/schedule/getTeacherLatestSchedule?token=" + token;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject responseJSON = new JSONObject(response);
                            JSONArray data = responseJSON.optJSONArray("data");
                            if(data.length() == 0) {
                                Toast.makeText(context, "You have no active schedule", Toast.LENGTH_LONG).show();
                                return;
                            } else {
                                responseJSON = (JSONObject) data.get(0);
                            }
                            int[] schedule = new int[7];
                            for(int i = 0; i < days.length; i++) {
                                if(responseJSON.has(days[i].toLowerCase())) {
                                    schedule[i] = responseJSON.getInt(days[i].toLowerCase());
                                }
                            }
                            setSchedule(schedule);
                            getTeacherAppointment(activity, activity.getContext());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if(error.networkResponse == null) {
                            Toast.makeText(context, "Connection Error",Toast.LENGTH_SHORT).show();
                        } else if(error.networkResponse.statusCode == 403) {
                            Toast.makeText(context, "TOKEN INVALID, PLEASE RE-LOG",Toast.LENGTH_SHORT).show();
                        } else if(error.networkResponse.statusCode == 500) {
                            Toast.makeText(context, "INVALID CREDENTIALS",Toast.LENGTH_SHORT).show();
                        } else if(error.networkResponse.statusCode != 401) {
                            Log.d("DEBUG","Error 401");
                        } else {
                            Toast.makeText(context, "Unknown error: " + error.networkResponse.statusCode, Toast.LENGTH_SHORT).show();
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

    public void getDataAsync(final WeeklyScheduleFragment activity) {
        getSchedule(activity, activity.getContext());
    }
}
