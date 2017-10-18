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
import com.example.timotiusek.musikonekteacher.CustomClass.Order;
import com.example.timotiusek.musikonekteacher.CustomClass.OrderAdapter;
import com.example.timotiusek.musikonekteacher.CustomClass.Student;
import com.example.timotiusek.musikonekteacher.CustomClass.StudentAdapter;
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
public class  StudentListFragment extends Fragment {

    @BindView(R.id.students_lv__student_list_fra) ListView listView;

    private String status = "";
    private ArrayList<Student> filteredStudents;
    private MainActivity ma;
    private StudentAdapter studentAdapter;

    public StudentListFragment() {
        // Required empty public constructor
    }

    public StudentListFragment(String status){
        this.status = status;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_student_list, container, false);
        ButterKnife.bind(this, v);
        ma = (MainActivity) getActivity();
//        notFilteredStudents.add(new Student(R.drawable.avatar, "Kursus Angkung Pemula", "Paket 3 kali pertemuan","Joealan Kacang", "GRADUATED"));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /**
                 * todo : send data
                 */
                Intent intent = new Intent(ma, StudentListActivity.class);

                Bundle extras = new Bundle();
                extras.putString("courseID", filteredStudents.get(position).getCourseID());
                extras.putString("courseName", filteredStudents.get(position).getCourseName());
                extras.putString("coursePackage", filteredStudents.get(position).getCoursePackage());
                extras.putString("studentName",filteredStudents.get(position).getStudentName());

                intent.putExtras(extras);

                startActivity(intent);
            }
        });
        filteredStudents = new ArrayList<>();
        studentAdapter = new StudentAdapter(filteredStudents, ma);
        populateStudents();
        return v;
    }

    private void populateStudents(){
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
        String url = Connector.getURL() +"/api/v1/teacher/getStudentList?token="+token+"&status="+status;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject res = new JSONObject(response);
                            Log.d("ASDF",res.toString());
                            JSONArray arr = res.getJSONArray("data");

                            for(int i=0;i<arr.length();i++){
                                JSONObject jo =  arr.getJSONObject(i);

                                String name = jo.getString("name");
                                String student_name = jo.getString("student_name");
                                String appointment = jo.getString("appointments");
                                String course = jo.getString("course_id");
                                //String status = jo.getString("status_name");

                                filteredStudents.add(
                                        new Student(R.drawable.avatar, name, TextFormater.format(Integer.valueOf(appointment)),
                                                student_name, status, course));
                            }

                            if(studentAdapter == null){
                                Log.d("ASDF","student adapter is null");
                            }
                            if(listView == null){
                                Log.d("ASDF","list view is null");
                            }
                            listView.setAdapter(studentAdapter);
                            studentAdapter.notifyDataSetChanged();
//                            Log.d("ASDF","ELEH" + res.toString());
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

                        } else {
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
