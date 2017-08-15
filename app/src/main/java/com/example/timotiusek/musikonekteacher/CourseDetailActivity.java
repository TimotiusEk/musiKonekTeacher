package com.example.timotiusek.musikonekteacher;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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
import com.example.timotiusek.musikonekteacher.CustomClass.Course;
import com.example.timotiusek.musikonekteacher.Helper.Connector;
import com.example.timotiusek.musikonekteacher.Helper.TextFormater;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CourseDetailActivity extends AppCompatActivity {
    @BindView(R.id.toolbar__course_detail_act)
    Toolbar toolbar;

    String courseID = "";
    Bundle params;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);
        ButterKnife.bind(this);

        params = getIntent().getExtras();

        courseID = params.getString("id");

        toolbar.setTitle(params.getString("name"));

        TextView appointmentView = (TextView) findViewById(R.id.how_many_meetings__course_detail_act);
        appointmentView.setText(params.getString("appointments"));

        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CourseDetailActivity.super.onBackPressed();
                }
            });
        }

        loadDetails();
    }

    @OnClick(R.id.edit_course_btn__course_detail_act)
    void editCourse(){
        /**
         * send data
         */
        Intent intent = new Intent(this, EditCourseActivity.class);

        intent.putExtras(params);

        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadDetails();
    }

    void loadDetails(){
        SharedPreferences sharedPreferences = getSharedPreferences("profile", Context.MODE_PRIVATE);

        String token ="";

        if(!sharedPreferences.getString("token","").equals("")) {
            token = sharedPreferences.getString("token","");
        }


        RequestQueue requestQueue;
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap
        final Network network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache, network);
        requestQueue.start();
        String url = Connector.getURL() +"/api/v1/programDetail?token="+token+"&program_id="+courseID;


        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject res = new JSONObject(response);
                            Log.d("ASDF",res.toString());
                            JSONObject jo = res.getJSONObject("data");


                            String description = jo.getString("description");
                            int price = jo.getInt("price");

                            TextView priceView = (TextView) findViewById(R.id.course_price__course_detail_act);
                            priceView.setText("Rp "+ TextFormater.priceFormatter(price));

                            TextView descriptionView = (TextView) findViewById(R.id.course_description__course_detail_act);
                            descriptionView.setText(description);

                            toolbar.setTitle(jo.getString("name"));

                            TextView appointmentView = (TextView) findViewById(R.id.how_many_meetings__course_detail_act);
                            appointmentView.setText(jo.getString("appointments")+" pertemuan");

                            params.putString("description",description);
                            params.putString("name",jo.getString("name"));
                            params.putString("appointments",jo.getString("appointments"));
                            params.putInt("price", price);

                            Log.d("price","to send "+price);

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

                            Toast.makeText(CourseDetailActivity.this, "Connection Error",Toast.LENGTH_SHORT).show();

                        }else{
                            int a = networkResponse.statusCode;
                            if(networkResponse.statusCode == 403){
                                Toast.makeText(CourseDetailActivity.this, "TOKEN INVALID, PLEASE RE LOG",Toast.LENGTH_SHORT).show();

                            }

                            if(networkResponse.statusCode == 500){
                                Toast.makeText(CourseDetailActivity.this, "INVALID CREDENTIALS",Toast.LENGTH_SHORT).show();
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
