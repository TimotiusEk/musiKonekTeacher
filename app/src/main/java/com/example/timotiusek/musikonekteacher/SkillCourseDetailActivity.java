package com.example.timotiusek.musikonekteacher;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
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
import com.example.timotiusek.musikonekteacher.CustomClass.CourseAdapter;
import com.example.timotiusek.musikonekteacher.CustomClass.Skill;
import com.example.timotiusek.musikonekteacher.Helper.Connector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SkillCourseDetailActivity extends AppCompatActivity {
    @BindView(R.id.course_related_lv__skill_course_detail_act)
    ListView courseRelatedLv;
    ArrayList<Course> courses;
    CourseAdapter courseAdapter;

    String skillId = "";
    String instrument = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skill_course_detail);
        ButterKnife.bind(this);
        getSupportActionBar().setTitle("Daftar Kursus Keahlian");

        Intent incoming = getIntent();
        Bundle params = incoming.getExtras();

        skillId = params.getString("id");
        instrument = params.getString("name");

        TextView textView = (TextView) findViewById(R.id.show_skill__skill_course_detail_act);
        textView.setText(instrument);


        courses = new ArrayList<>();
//        courses.add(new Course("Kursus Violin Dasar","12 kali pertemuan"));
//        courses.add(new Course("Kursus Violin Pop","3 kali pertemuan"));
//        courses.add(new Course("Kursus Violin Menengah","4 kali pertemuan"));
//        courses.add(new Course("Kursus Violin Dasar","12 kali pertemuan"));
//        courses.add(new Course("Kursus Violin Pop","3 kali pertemuan"));
//        courses.add(new Course("Kursus Violin Menengah","4 kali pertemuan"));

        courseAdapter = new CourseAdapter(courses, this);
        courseRelatedLv.setAdapter(courseAdapter);

        courseRelatedLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Course courseToSend = (Course) courseAdapter.getItem(position);

                /**
                 * todo : send data
                 */

                startActivity(new Intent(SkillCourseDetailActivity.this, CourseDetailActivity.class));
            }
        });
        loadProgram();
    }

    private void loadProgram(){

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
        String url = Connector.getURL() +"/api/v1/myProgram?token="+token+"&skill_id="+skillId;


        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject res = new JSONObject(response);
                            Log.d("ASDF",res.toString());
                            JSONArray arr = res.getJSONArray("data");

                            for(int i=0;i<arr.length();i++){
                                JSONObject jo =  arr.getJSONObject(i);

                                String name = jo.getString("name");
                                String appointments = jo.getString("appointments");

                                courses.add(new Course(name, appointments+" pertemuan"));
//

                            }

                            courseAdapter.notifyDataSetChanged();


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

                            Toast.makeText(SkillCourseDetailActivity.this, "Connection Error",Toast.LENGTH_SHORT).show();

                        }else{
                            int a = networkResponse.statusCode;
                            if(networkResponse.statusCode == 403){
                                Toast.makeText(SkillCourseDetailActivity.this, "TOKEN INVALID, PLEASE RE LOG",Toast.LENGTH_SHORT).show();

                            }

                            if(networkResponse.statusCode == 500){
                                Toast.makeText(SkillCourseDetailActivity.this, "INVALID CREDENTIALS",Toast.LENGTH_SHORT).show();
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
