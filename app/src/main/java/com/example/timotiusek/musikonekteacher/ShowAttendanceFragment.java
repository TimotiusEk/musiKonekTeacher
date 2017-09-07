package com.example.timotiusek.musikonekteacher;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
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
import com.example.timotiusek.musikonekteacher.CustomClass.Attendance;
import com.example.timotiusek.musikonekteacher.CustomClass.Student;
import com.example.timotiusek.musikonekteacher.Helper.Connector;
import com.example.timotiusek.musikonekteacher.Helper.TextFormater;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShowAttendanceFragment extends Fragment {
    @BindView(R.id.attendances_lv__show_attendance_fra)
    ListView showAttendanceListView;
    ArrayList<Attendance> attendances;
    ShowAttendanceAdapter showAttendanceAdapter;
    StudentListActivity ma;
    public ShowAttendanceFragment() {
        // Required empty public constructor
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    private Student student;

    public static ShowAttendanceFragment newInstance(Student student){

        ShowAttendanceFragment sdf = new ShowAttendanceFragment();
        sdf.setStudent(student);

        return sdf;


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_show_attendance, container, false);
        ButterKnife.bind(this,view);
        ma = (StudentListActivity) getActivity();
//        ma.clearCheckedItems();


        attendances = new ArrayList<>();
//        attendances.add(new Attendance(R.drawable.avatar, student.getCourseName(), "Pertemuan 1", student.getStudentName()));
//        attendances.add(new Attendance(R.drawable.avatar, student.getCourseName(), "Pertemuan 2", student.getStudentName()));
//        attendances.add(new Attendance(R.drawable.avatar, student.getCourseName(), "Pertemuan 3", student.getStudentName()));

        showAttendanceAdapter = new ShowAttendanceAdapter(attendances, getActivity());
        showAttendanceListView.setAdapter(showAttendanceAdapter);

        showAttendanceListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("ASDF","tur or fals "+attendances.get(position).isTeacherAttendance());
                if(attendances.get(position).isTeacherAttendance().equalsIgnoreCase("true")){
                    Intent intent = new Intent(getActivity(), ShowReportDetailActivity.class);

                    Bundle extras = new Bundle();
                    extras.putString("appointment_id",attendances.get(position).getAppointmentID());

                    intent.putExtras(extras);

                    startActivity(intent);
                }else if(attendances.get(position).isTeacherAttendance().equalsIgnoreCase("false")){


                }
                else{
                    Intent intent = new Intent(getActivity(), CreateReportActivity.class);

                    Bundle extras = new Bundle();
                    extras.putString("appointment_id",attendances.get(position).getAppointmentID());

                    intent.putExtras(extras);

                    startActivity(intent);
                }
            }
        });
        // Inflate the layout for this fragment

        //populateAttendance();

        return view;
    }

    @Override
    public void onResume() {
        populateAttendance();
        super.onResume();
    }

    private void populateAttendance(){

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("profile", Context.MODE_PRIVATE);

        String token ="";

        if(!sharedPreferences.getString("token","").equals("")) {
            token = sharedPreferences.getString("token","");
        }


        RequestQueue requestQueue;
        Cache cache = new DiskBasedCache(getContext().getCacheDir(), 1024 * 1024); // 1MB cap
        final Network network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache, network);
        requestQueue.start();
        String url = Connector.getURL() +"/api/v1/appointment/getAppointments?done=true&token="+token+"&course_id="+student.getCourseID();


        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject res = new JSONObject(response);
                            Log.d("ASDF",res.toString());
                            JSONArray arr = res.getJSONArray("data");

                            for(int i=0;i<arr.length();i++){


                                attendances.clear();

                                JSONObject jo =  arr.getJSONObject(i);

                                if(jo.get("attendance_teacher").toString().equals("null")){
                                    attendances.add(new Attendance(R.drawable.avatar, student.getCourseName(), "Pertemuan "+(i+1), TextFormater.formatTime(jo.getString("appointment_time")), jo.getString("appointment_id"), "null"));

                                }else{
                                    attendances.add(new Attendance(R.drawable.avatar, student.getCourseName(), "Pertemuan "+(i+1), TextFormater.formatTime(jo.getString("appointment_time")), jo.getString("appointment_id"), String.valueOf(jo.getBoolean("attendance_teacher"))));
                                }


                            }

                            showAttendanceAdapter.notifyDataSetChanged();


                        } catch (JSONException e) {
                            Log.d("ASDF","Fail");
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        NetworkResponse networkResponse = error.networkResponse;

                        if(networkResponse == null){

                            Toast.makeText(getContext(), "Connection Error",Toast.LENGTH_SHORT).show();

                        }else{
                            int a = networkResponse.statusCode;
                            if(networkResponse.statusCode == 403){
                                Toast.makeText(getContext(), "TOKEN INVALID, PLEASE RE LOG",Toast.LENGTH_SHORT).show();

                            }

                            if(networkResponse.statusCode == 500){
                                Toast.makeText(getContext(), "INVALID CREDENTIALS",Toast.LENGTH_SHORT).show();
                            }

                            if(networkResponse.statusCode != 401){

                                Log.d("ASDF","SHIT");

                            }

                        }



                    }
                }){

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                int mStatusCode = response.statusCode;
                return super.parseNetworkResponse(response);
            }

        };

        requestQueue.add(stringRequest);
    }


}
