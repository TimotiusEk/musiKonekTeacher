package com.example.timotiusek.musikonekteacher;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.example.timotiusek.musikonekteacher.Helper.ShaConverter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class CreateReportActivity extends AppCompatActivity {

    String appointment_id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Intent incoming = getIntent();

        Bundle params = incoming.getExtras();
        appointment_id = params.getString("appointment_id");

        Toast.makeText(this, "apppintment id is "+ appointment_id, Toast.LENGTH_SHORT).show();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_report);
        getSupportActionBar().setTitle("Buat Laporan");

        Button submitButton = (Button) findViewById(R.id.checkin_btn_create_report_page);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitReport();
            }
        });

    }

    private void submitReport(){



        RequestQueue requestQueue;
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap
        final Network network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache, network);
        requestQueue.start();
        String url = Connector.getURL() +"/api/v1/appointment/teachercheckin";
        Log.d("ASDF",url);



        StringRequest stringRequest = new StringRequest(Request.Method.PUT, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("ASDF","SUCCESS");
                        CreateReportActivity.this.finish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        NetworkResponse networkResponse = error.networkResponse;

                        if(networkResponse == null){

                            Toast.makeText(CreateReportActivity.this, "Connection Error",Toast.LENGTH_SHORT).show();

                        }else{
                            int a = networkResponse.statusCode;
                            if(networkResponse.statusCode == 401){
                            }

                            if(networkResponse.statusCode == 500){
                                Toast.makeText(CreateReportActivity.this, "ERROR",Toast.LENGTH_SHORT).show();
                            }

                            if(networkResponse.statusCode != 401){


                            }

                        }



                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> reqBody = new HashMap<String, String>();

                SharedPreferences sharedPreferences = getSharedPreferences("profile", Context.MODE_PRIVATE);

                String token ="";

                if(!sharedPreferences.getString("token","").equals("")) {
                    token = sharedPreferences.getString("token","");
                }

                EditText homeworkEdit = (EditText) findViewById(R.id.homework_report_page);
                EditText practiceEdit = (EditText) findViewById(R.id.exercise_create_report_page);
                EditText teacherRemarkEdit = (EditText) findViewById(R.id.comment_create_report_page);

//                EditText usernameText = (EditText) findViewById(R.id.input_username_sign_up);
//                EditText fullnameText  = (EditText)  findViewById(R.id.input_fullname_signup);

                String homework = homeworkEdit.getText().toString();
                String practice = practiceEdit.getText().toString();
                String teacherRemark = teacherRemarkEdit.getText().toString();


//                String username = usernameText.getText().toString();
//                String fullname = fullnameText.getText().toString();

                reqBody.put("homework", homework);
                reqBody.put("practice", practice);
                reqBody.put("teacher_remark", teacherRemark);
                reqBody.put("teacher_remark", teacherRemark);
                reqBody.put("token",token);
                reqBody.put("appointment_id",appointment_id);




                return checkParams(reqBody);
            }
            private Map<String, String> checkParams(Map<String, String> map){
                Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry<String, String> pairs = (Map.Entry<String, String>)it.next();
                    if(pairs.getValue()==null){
                        map.put(pairs.getKey(), "");
                    }
                }
                return map;
            }

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
