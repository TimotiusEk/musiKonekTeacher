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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditCourseActivity extends AppCompatActivity {
    @BindView(R.id.how_many_meetings__edit_course_act) Spinner howManyMeetings;

    EditText nameText;
    EditText descText;
    EditText priceText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_course);
        ButterKnife.bind(this);
        getSupportActionBar().setTitle("Ubah Kursus");

        Bundle params = getIntent().getExtras();

        nameText = (EditText) findViewById(R.id.course_name__edit_course_act);
        nameText.setText(params.getString("name"));



        howManyMeetings.setSelection(getIndex(howManyMeetings, params.getString("appointments").replaceAll("[^0-9]", "")));
//        Log.d("ASDF",params.getString("appointments").replaceAll("[^0-9]", "")); // returns 123);


        descText = (EditText) findViewById(R.id.course_desc_input__edit_course_act);
        descText.setText(params.getString("description"));

        priceText = (EditText) findViewById(R.id.course_price__edit_course_act);
        priceText.setText(String.valueOf(params.getInt("price")));
        Log.d("ASDF","price is "+params.getInt("price"));

        Button submitButton = (Button) findViewById(R.id.save_btn__add_skill_act);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateProgram();
            }
        });

    }


    //to get index of the spinner based on string value
    private int getIndex(Spinner spinner, String myString)
    {
        int index = 0;

        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                index = i;
                break;
            }
        }
        return index;
    }

    void updateProgram(){

        RequestQueue requestQueue;
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap
        final Network network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache, network);
        requestQueue.start();


        String url = Connector.getURL() +"/api/v1/program/update";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Toast.makeText(EditCourseActivity.this, "Update Successful",Toast.LENGTH_SHORT).show();
                        finish();


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        NetworkResponse networkResponse = error.networkResponse;

                        if(networkResponse == null){

                            Toast.makeText(EditCourseActivity.this, "Connection Error",Toast.LENGTH_SHORT).show();

                        }else{
                            int a = networkResponse.statusCode;
                            if(networkResponse.statusCode == 401){
                            }

                            if(networkResponse.statusCode == 500){
                                Toast.makeText(EditCourseActivity.this, "ERROR",Toast.LENGTH_SHORT).show();
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

                reqBody.put("program_id",params.getString("id"));

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
