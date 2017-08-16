package com.example.timotiusek.musikonekteacher;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
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
import com.example.timotiusek.musikonekteacher.Helper.Connector;
import com.example.timotiusek.musikonekteacher.Helper.TextFormater;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class EarningDetailActivity extends AppCompatActivity {

    String courseID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earning_detail);
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar__earning_detail_act);
        /**
         * Set Course Name Here
         **/

        Bundle params = getIntent().getExtras();


        toolbar.setTitle(params.getString("name"));

        TextView studentNameView = (TextView) findViewById(R.id.student_name__earning_detail_act);
        studentNameView.setText(params.getString("student_name"));

        courseID = params.getString("courseID");

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        loadDetails();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                return true;
        }
        return (super.onOptionsItemSelected(menuItem));
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
        String url = Connector.getURL() +"/api/v1/course/courseDetailWithEarning?token="+token+"&course_id="+courseID;


        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject res = new JSONObject(response);
                            Log.d("ASDF",res.toString());
                            JSONObject jo = res.getJSONObject("data");


                            String description = jo.getString("description");

                            TextView descriptionView = (TextView) findViewById(R.id.course_description__earning_detail_act);
                            descriptionView.setText(description);

                            TextView appointmentView = (TextView) findViewById(R.id.meeting_progress__earning_detail_act);
                            appointmentView.setText(jo.getInt("fullfilled")+" dari "+jo.getInt("appointments")+" pertemuan");

                            TextView totalEarningView = (TextView) findViewById(R.id.total_earning__earning_detail_act);
                            totalEarningView.setText("Rp." + TextFormater.priceFormatter(jo.getInt("price")));

                            TextView progressEarningView = (TextView) findViewById(R.id.earning_progress__earning_detail_act);
                            progressEarningView.setText("Rp." + TextFormater.priceFormatter(jo.getDouble("fullfilled") / jo.getDouble("appointments") * jo.getDouble("price")));


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

                            Toast.makeText(EarningDetailActivity.this, "Connection Error",Toast.LENGTH_SHORT).show();

                        }else{
                            int a = networkResponse.statusCode;
                            if(networkResponse.statusCode == 403){
                                Toast.makeText(EarningDetailActivity.this, "TOKEN INVALID, PLEASE RE LOG",Toast.LENGTH_SHORT).show();

                            }

                            if(networkResponse.statusCode == 500){
                                Toast.makeText(EarningDetailActivity.this, "INVALID CREDENTIALS",Toast.LENGTH_SHORT).show();
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
