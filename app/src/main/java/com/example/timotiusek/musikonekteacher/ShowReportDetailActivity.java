package com.example.timotiusek.musikonekteacher;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.DefaultRetryPolicy;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShowReportDetailActivity extends AppCompatActivity {

    @BindView(R.id.show_date__show_report_detail_act)
    TextView showDate;

    @BindView(R.id.show_time__show_report_detail_act)
    TextView showTime;

    @BindView(R.id.show_homework__show_report_detail_act)
    TextView showHomework;

    @BindView(R.id.show_exercises__show_report_detail_act)
    TextView showExercises;

    @BindView(R.id.show_comment__show_report_detail_act)
    TextView showComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_report_detail);
        ButterKnife.bind(this);

        /**
         * todo : populate with data from the database
         */

        loadReport();

    }

    private void loadReport(){

        RequestQueue requestQueue;
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap
        final Network network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache, network);
        requestQueue.start();

        SharedPreferences sharedPreferences = getSharedPreferences("profile", Context.MODE_PRIVATE);

        String token ="";
        if(!sharedPreferences.getString("token","").equals("")) {
            token = sharedPreferences.getString("token","");
        }

        Intent incoming = getIntent();

        Bundle params = incoming.getExtras();
        String appointment_id = params.getString("appointment_id");


        String url = Connector.getURL() +"/api/v1/report/showReport?token="+token+"&appointment_id="+appointment_id;

        Log.d("ASDF",url);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("ASDF",response);

                        try {
                            JSONObject all = new JSONObject(response);
                            JSONObject res  = all.getJSONObject("data");

                            String homework = res.getString("homework");
                            String teacherRemark = res.getString("teacher_remark");
                            String practice = res.getString("practice");

                            showHomework.setText(homework);
                            showComment.setText(teacherRemark);
                            showExercises.setText(practice);

                            showDate.setText(TextFormater.formatDateSpacing(res.getString("date")));
                            showTime.setText("Jam " +res.getString("time"));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        NetworkResponse networkResponse = error.networkResponse;

                        if(networkResponse == null){

                            Toast.makeText(ShowReportDetailActivity.this, "Connection Error",Toast.LENGTH_SHORT).show();

                        }else{
                            int a = networkResponse.statusCode;
                            if(networkResponse.statusCode == 401){
                            }

                            if(networkResponse.statusCode == 500){
                                Toast.makeText(ShowReportDetailActivity.this, "ERROR",Toast.LENGTH_SHORT).show();
                            }

                            if(networkResponse.statusCode != 401){


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

//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        requestQueue.add(stringRequest);

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(stringRequest);

    }


}
