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
import com.example.timotiusek.musikonekteacher.CustomClass.Earning;
import com.example.timotiusek.musikonekteacher.CustomClass.EarningAdapter;
import com.example.timotiusek.musikonekteacher.CustomClass.Earning;
import com.example.timotiusek.musikonekteacher.CustomClass.EarningAdapter;
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
public class EarningFragment extends Fragment {

    MainActivity ma;

    String status;
    ArrayList<Earning> notFilteredEarnings;
    ArrayList<Earning> filteredEarnings;
    @BindView(R.id.earning_lv____earning_fra)
    ListView listView;
    public EarningFragment() {
        // Required empty public constructor
    }

    public EarningFragment(String status){
        this.status = status;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_earning, container, false);
        ButterKnife.bind(this, v);

        ma = (MainActivity) getActivity();

        notFilteredEarnings = new ArrayList<>();
//        notFilteredEarnings.add(new Earning(R.drawable.avatar, "Kursus Piano Pemula", "Paket 5 kali pertemuan","Joe Biden", "EXISTING"));
//        notFilteredEarnings.add(new Earning(R.drawable.avatar, "Kursus Gitar Pemula", "Paket 2 kali pertemuan","Joe Allen", "EXISTING"));
//        notFilteredEarnings.add(new Earning(R.drawable.avatar, "Kursus Biola Pemula", "Paket 1 kali pertemuan","Joe Cole", "EXISTING"));
//        notFilteredEarnings.add(new Earning(R.drawable.avatar, "Kursus Vokal Pemula", "Paket 4 kali pertemuan","Joe shua Suherman", "FINISHED"));
//        notFilteredEarnings.add(new Earning(R.drawable.avatar, "Kursus Piano Intermediate", "Paket 12 kali pertemuan","Joe unochi", "FINISHED"));
//        notFilteredEarnings.add(new Earning(R.drawable.avatar, "Kursus Angkung Pemula", "Paket 3 kali pertemuan","Joealan Kacang", "FINISHED"));
        filteredEarnings = new ArrayList<>();

        if(status.equals("EXISTING")){
            for(Earning notFilteredEarning : notFilteredEarnings){
                if(notFilteredEarning.getStatus().equals("EXISTING")){
                    filteredEarnings.add(notFilteredEarning);
                }
            }
        } else if(status.equals("FINISHED")){
            for(Earning notFilteredEarning : notFilteredEarnings){
                if(notFilteredEarning.getStatus().equals("FINISHED")){
                    filteredEarnings.add(notFilteredEarning);
                }
            }
        }

        EarningAdapter earningAdapter = new EarningAdapter(filteredEarnings, getActivity());
        listView.setAdapter(earningAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /**
                 * todo : make bundle to send data
                 */
                Intent intent = new Intent(ma, EarningDetailActivity.class);

                Bundle extras = new Bundle();
                extras.putString("courseID", filteredEarnings.get(position).getCourseID());
                extras.putString("student_name", filteredEarnings.get(position).getStudentName());
                extras.putInt("student_image", filteredEarnings.get(position).getStudentImage());
                extras.putString("name",filteredEarnings.get(position).getCourseName());

                intent.putExtras(extras);

                startActivity(intent);
            }
        });
        // Inflate the layout for this fragment
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        populateEarnings();
    }

    void populateEarnings(){
//
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("profile", Context.MODE_PRIVATE);

        String token = "";

        if (!sharedPreferences.getString("token", "").equals("")) {
            token = sharedPreferences.getString("token", "");
        }


        RequestQueue requestQueue;
        Cache cache = new DiskBasedCache(getContext().getCacheDir(), 1024 * 1024); // 1MB cap
        final Network network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache, network);
        requestQueue.start();
        String url = Connector.getURL() + "/api/v1/course/getCourseByTeacherId?token=" + token;

        Log.d("ASDF",url);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            notFilteredEarnings.clear();
                            JSONObject res = new JSONObject(response);
                            Log.d("ASDF", res.toString());
                            JSONArray arr = res.getJSONArray("data");

                            for (int i = 0; i < arr.length(); i++) {
                                JSONObject jo = arr.getJSONObject(i);

                                String instrument = jo.getString("instrument");
                                String student_name = jo.getString("student_name");
                                String appointment = jo.getString("appointments");
                                String status = jo.getString("status_name");
                                String id = jo.getString("course_id");

                                notFilteredEarnings.add(new Earning(R.drawable.avatar, TextFormater.formatCourse(instrument), TextFormater.format(Integer.valueOf(appointment)), student_name, status, id));
//

                            }

//                            Log.d("ASDF","ELEH" + res.toString());


                        } catch (JSONException e) {
                            Log.d("ASDF", "Fail");
                            e.printStackTrace();
                        }

                        filterEarnings();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        NetworkResponse networkResponse = error.networkResponse;

                        if (networkResponse == null) {

                            Toast.makeText(ma, "Connection Error", Toast.LENGTH_SHORT).show();

                        } else {
                            int a = networkResponse.statusCode;
                            if (networkResponse.statusCode == 403) {
                                Toast.makeText(ma, "TOKEN INVALID, PLEASE RE LOG", Toast.LENGTH_SHORT).show();

                            }

                            if (networkResponse.statusCode == 500) {
                                Toast.makeText(ma, "INVALID CREDENTIALS", Toast.LENGTH_SHORT).show();
                            }

                            if (networkResponse.statusCode != 401) {

                                Log.d("ASDF", "SHIT");

                            }

                        }


                    }
                }) {

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                int mStatusCode = response.statusCode;
                return super.parseNetworkResponse(response);
            }

        };

        requestQueue.add(stringRequest);
        
    }

    void filterEarnings(){
        filteredEarnings.clear();
        for (Earning notFilteredEarning : notFilteredEarnings) {
            if (notFilteredEarning.getStatus().equalsIgnoreCase("RUNNING")) {
                filteredEarnings.add(notFilteredEarning);
            }
        }
        listView.setAdapter(new EarningAdapter(filteredEarnings, getActivity()));

    }
}
