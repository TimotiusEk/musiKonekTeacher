package com.example.timotiusek.musikonekteacher;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProfileActivity extends AppCompatActivity {
    @BindView(R.id.toolbar__profile_act) android.support.v7.widget.Toolbar toolbar;
    @BindView(R.id.link_to_edit_profile__profile_act)
    ImageView linkToEditProfile;

    String fullname = "JONATHAN ";

    TextView genderText;

    CollapsingToolbarLayout ctl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);

        ctl = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar__profile_act);

        genderText = (TextView) findViewById(R.id.gender__profile_act);


        callGetMyProfile();

        toolbar.setTitle("Jonathan Simananda");
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ProfileActivity.super.onBackPressed();
                }
            });
        }

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

                            fullname = data.getString("fullname");
                            String email = data.getString("email");

                            JSONObject location = data.getJSONObject("address");

                            String x = location.getString("x");
                            String y  = location.getString("y");

//                            getSupportActionBar().setTitle("fullname");
                            ctl.setTitle(fullname);
                            Log.d("ASDF","fullname"+fullname);

                            if(data.getString("gender").equals("null") || data.getString("gender")==null){
                                genderText.setText("Unspecified");
                            }else{
                                genderText.setText(data.getString("gender"));
                            }

                            TextView emailText = (TextView) findViewById(R.id.email__profile_act);
                            emailText.setText(email);

                            TextView addressText = (TextView) findViewById(R.id.address__profile_act);
                            addressText.setText("long : "+x+"\t"+"lang : "+y);



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

                            Toast.makeText(ProfileActivity.this, "Connection Error",Toast.LENGTH_SHORT).show();

                        }else{
                            int a = networkResponse.statusCode;
                            if(networkResponse.statusCode == 403){
                                Toast.makeText(ProfileActivity.this, "TOKEN INVALID, PLEASE RE LOG",Toast.LENGTH_SHORT).show();

                            }

                            if(networkResponse.statusCode == 500){
                                Toast.makeText(ProfileActivity.this, "INVALID CREDENTIALS",Toast.LENGTH_SHORT).show();
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

    @OnClick(R.id.link_to_edit_profile__profile_act)
    void goToEditProfile(){
        startActivity(new Intent(this, EditProfileActivity.class));
    }

    @Override
    protected void onResume() {
        super.onResume();

        callGetMyProfile();

    }
}
