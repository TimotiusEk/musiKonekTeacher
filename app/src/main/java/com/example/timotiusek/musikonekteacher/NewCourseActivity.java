package com.example.timotiusek.musikonekteacher;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import butterknife.BindView;

public class NewCourseActivity extends AppCompatActivity {

    @BindView(R.id.how_many_meetings__edit_course_act)
    Spinner howManyMeetings;

    EditText nameText;
    EditText descText;
    EditText priceText;
    String skillId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_course);

        Bundle params = getIntent().getExtras();
        params.getString("skill_id");

        nameText = (EditText) findViewById(R.id.course_name__edit_course_act);
        descText = (EditText) findViewById(R.id.course_desc_input__edit_course_act);
        priceText = (EditText) findViewById(R.id.course_price__edit_course_act);

        setTitle("New Course");

        Button button = (Button) findViewById(R.id.save_btn__add_skill_act);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createProgram();
            }
        });

    }


    void createProgram(){

        RequestQueue requestQueue;
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap
        final Network network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache, network);
        requestQueue.start();


        String url = Connector.getURL() +"/api/v1/createProgram";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Toast.makeText(NewCourseActivity.this, "Program Created",Toast.LENGTH_SHORT).show();
                        finish();


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        NetworkResponse networkResponse = error.networkResponse;

                        if(networkResponse == null){

                            Toast.makeText(NewCourseActivity.this, "Connection Error",Toast.LENGTH_SHORT).show();

                        }else{
                            int a = networkResponse.statusCode;
                            if(networkResponse.statusCode == 401){
                            }

                            if(networkResponse.statusCode == 500){
                                Toast.makeText(NewCourseActivity.this, "ERROR",Toast.LENGTH_SHORT).show();
                            }

                            if(networkResponse.statusCode != 401){


                            }

                        }



                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> reqBody = new HashMap<String, String>();


                String name = String.valueOf(nameText.getText());
                String description = String.valueOf(descText.getText());
                String price = String.valueOf(priceText.getText());

//                reqBody.put("fullname", fullname);
//                reqBody.put("username", username);

                howManyMeetings = (Spinner) findViewById(R.id.how_many_meetings__edit_course_act);

                if(howManyMeetings==null){
                    Log.d("ASDF","RIP");
                }else{
                    Log.d("ASDF","Ada kok");
                }

                String appointments = String.valueOf(howManyMeetings.getSelectedItem());


                String token = "";
                SharedPreferences sharedPreferences = getSharedPreferences("profile", Context.MODE_PRIVATE);

                if(!sharedPreferences.getString("token","").equals("")) {
                    token = sharedPreferences.getString("token","");
                }

                reqBody.put("token",token);
                reqBody.put("name",name);
                reqBody.put("description",description);
                reqBody.put("price",price);
                reqBody.put("appointments",appointments);

                Bundle params = getIntent().getExtras();

                reqBody.put("skill_id",params.getString("skill_id"));

                Log.d("ASDF","appointment is "+appointments);

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

        requestQueue.add(stringRequest);

    }
}
