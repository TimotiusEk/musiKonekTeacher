package com.example.timotiusek.musikonekteacher;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.sign_up_btn)
    void register(){
        signUpCall();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, SignInActivity.class));
    }

    void signUpCall(){

        Log.d("ASDF","Called");

        RequestQueue requestQueue;
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap
        final Network network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache, network);
        requestQueue.start();
        String url = Connector.getURL() +"/api/v1/teacher/create";
        Log.d("ASDF",url);



        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("ASDF","SUCCESS");
                        try {
                            JSONObject res = new JSONObject(response);
//                            String name = String.valueOf(res.get("name"));
                            Log.d("ASDF", "YOSAH + \n"+res.toString() );
                            //Toast.makeText(SignUpActivity.this, "login success is "+ res.getString("success"), Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SignUpActivity.this, ConfirmationScreenActivity.class));

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("ASDF","damint jason");
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        NetworkResponse networkResponse = error.networkResponse;

                        if(networkResponse == null){

                            Toast.makeText(SignUpActivity.this, "Connection Error",Toast.LENGTH_SHORT).show();

                        }else{
                            int a = networkResponse.statusCode;
                            if(networkResponse.statusCode == 401){
                            }

                            if(networkResponse.statusCode == 500){
                                Toast.makeText(SignUpActivity.this, "ERROR",Toast.LENGTH_SHORT).show();
                            }

                            if(networkResponse.statusCode != 401){


                            }

                        }



                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> reqBody = new HashMap<String, String>();

                EditText emailText = (EditText) findViewById(R.id.input_email_sign_up);
                EditText passwordText = (EditText) findViewById(R.id.input_password_sign_up);
                EditText phoneText = (EditText) findViewById(R.id.input_phone_num);

//                EditText usernameText = (EditText) findViewById(R.id.input_username_sign_up);
//                EditText fullnameText  = (EditText)  findViewById(R.id.input_fullname_signup);

                String email = emailText.getText().toString();
                String password = passwordText.getText().toString();

                try {
                    password = ShaConverter.SHA1(password);
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                String phone = phoneText.getText().toString();

//                String username = usernameText.getText().toString();
//                String fullname = fullnameText.getText().toString();

                reqBody.put("email", email);
                reqBody.put("password", password);
                reqBody.put("phone_number", phone);
                reqBody.put("username","username");
                reqBody.put("fullname","fullname");

                reqBody.put("address_lat","123");
                reqBody.put("address_lng","135");



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
