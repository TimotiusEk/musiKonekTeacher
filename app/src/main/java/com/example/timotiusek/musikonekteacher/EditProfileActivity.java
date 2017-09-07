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
import com.example.timotiusek.musikonekteacher.Helper.TextFormater;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class EditProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        getSupportActionBar().setTitle("Edit Profile");

        callGetMyProfile();

        Button saveButton = (Button) findViewById(R.id.save_btn__edit_profile_act);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callUpdateProfile();
            }
        });

    }

    public void callGetMyProfile(){

        RequestQueue requestQueue;
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap
        final Network network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache, network);
        requestQueue.start();

        String token = "";
        SharedPreferences sharedPreferences = getSharedPreferences("profile", Context.MODE_PRIVATE);

        if(!sharedPreferences.getString("token","").equals("")) {
            token = sharedPreferences.getString("token","");
        }
        String url = Connector.getURL() +"/api/v1/teacher/getProfileData?token="+token;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject res = new JSONObject(response);
//                            String name = String.valueOf(res.get("name"));

                            JSONObject data = res.getJSONObject("data");

                            String fullname = data.getString("fullname");


                            String firstname = TextFormater.firstNameSplitter(fullname);
                            String lastname = TextFormater.lastNameSplitter(fullname);

                            JSONObject location = data.getJSONObject("address");

                            String x = location.getString("x");
                            String y  = location.getString("y");

                            EditText firstnameInput = (EditText) findViewById(R.id.firstname_input__edit_profile_act);
                            firstnameInput.setText(firstname);

                            EditText lastnameInput = (EditText) findViewById(R.id.lastname_input__edit_profile_act);
                            lastnameInput.setText(lastname);

                            Spinner genderSpinner = (Spinner) findViewById(R.id.gender__edit_profile_act);

                            if(data.getString("gender").equalsIgnoreCase("male")){
                                genderSpinner.setSelection(0);
                            }else{
                                genderSpinner.setSelection(1);
                            }

                            /**
                             * todo : ini harusnya bukan fullname dan username
                             */

                            Log.d("ASDF",res.toString());


                        } catch (JSONException e) {
                            Log.d("ASDF","ELEH");
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        NetworkResponse networkResponse = error.networkResponse;

                        if(networkResponse == null){

                            Toast.makeText(EditProfileActivity.this, "Connection Error",Toast.LENGTH_SHORT).show();

                        }else{
                            int a = networkResponse.statusCode;
                            if(networkResponse.statusCode == 403){
                                Toast.makeText(EditProfileActivity.this, "TOKEN INVALID, PLEASE RE LOG",Toast.LENGTH_SHORT).show();

                            }

                            if(networkResponse.statusCode == 500){
                                Toast.makeText(EditProfileActivity.this, "INVALID CREDENTIALS",Toast.LENGTH_SHORT).show();
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

//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        requestQueue.add(stringRequest);

        requestQueue.add(stringRequest);

    }

    private void callUpdateProfile(){

        RequestQueue requestQueue;
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap
        final Network network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache, network);
        requestQueue.start();


        String url = Connector.getURL() +"/api/v1/teacher/update";

        StringRequest stringRequest = new StringRequest(Request.Method.PUT, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject res = new JSONObject(response);
//                            String name = String.valueOf(res.get("name"));
                            Log.d("ASDF", "YOSAH + \n"+res.toString() );

                            SharedPreferences sharedPreferences = getSharedPreferences("profile", Context.MODE_PRIVATE);


                            /**
                             * todo : disini ga ada username
                             */

//                            SharedPreferences.Editor editor = sharedPreferences.edit();
//                            editor.putString("username",usernameEdit.getText().toString());
//                            editor.apply();


                            finish();

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

                            Toast.makeText(EditProfileActivity.this, "Connection Error",Toast.LENGTH_SHORT).show();

                        }else{
                            int a = networkResponse.statusCode;
                            if(networkResponse.statusCode == 401){
                            }

                            if(networkResponse.statusCode == 500){
                                Toast.makeText(EditProfileActivity.this, "ERROR",Toast.LENGTH_SHORT).show();
                            }

                            if(networkResponse.statusCode != 401){


                            }

                        }



                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> reqBody = new HashMap<String, String>();



                EditText firstNameInput = (EditText) findViewById(R.id.firstname_input__edit_profile_act);

                EditText lastNameInput = (EditText) findViewById(R.id.lastname_input__edit_profile_act);

//                EditText usernameText = (EditText) findViewById(R.id.);
//                EditText fullnameText  = (EditText)  findViewById(R.id.input_fullname_signup);

//                String firstname = firstNameInput.getText().toString();
//                String lastname = lastNameInput.getText().toString();

                String fullname = firstNameInput.getText().toString() + " " + lastNameInput.getText().toString();

                Spinner genderSpinner = (Spinner) findViewById(R.id.gender__edit_profile_act);


                reqBody.put("gender", genderSpinner.getSelectedItem().toString());

                /**
                 * todo : ini harusnya bukan fullnama sama username tp first name & last name
                 */

                reqBody.put("fullname", fullname);

                String token = "";
                SharedPreferences sharedPreferences = getSharedPreferences("profile", Context.MODE_PRIVATE);

                if(!sharedPreferences.getString("token","").equals("")) {
                    token = sharedPreferences.getString("token","");
                }

                reqBody.put("token",token);

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
